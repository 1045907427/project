<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>品牌业务员考核列表页面</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>  
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'north'" style="height: 30px;">
    		<div id="report-button-branduserAssessExtend"></div>
    	</div>
    	<div data-options="region:'center',border:false" style="border: 0px;">
    		<div id="report-table-branduserAssessExtendBtn">
    			<form action="" id="branduserAssessExtend-form-ListQuery" method="post">
	    			<table cellpadding="0" cellspacing="1" border="0">
	    				<tr>
	    					<td style="padding-left: 10px;">日期:&nbsp;</td>
	    					<td><input name="businessdate1" class="Wdate" style="width:100px;" onfocus="WdatePicker({dateFmt:'yyyy-MM'})" value="${firstDay }"/>
	    						到&nbsp<input name="businessdate2" class="Wdate" style="width:100px;" onfocus="WdatePicker({dateFmt:'yyyy-MM'})" value="${today }"/>
	    					</td>
	    					<td style="padding-left: 10px;">品牌业务员:&nbsp;</td>
	    					<td><input id="reportList-widget-branduser" name="branduser" type="text" style="width: 120px;"/></td>
	    					<td colspan="2" style="padding-left: 10px;">
	    						<a href="javaScript:void(0);" id="branduserAssessExtend-query-List" class="button-qr">查询</a>
					    		<a href="javaScript:void(0);" id="branduserAssessExtend-query-reloadList" class="button-qr">重置</a>
				    			<span id="branduserAssessExtend-query-advanced"></span>
	    					</td>
	    				</tr>
	    			</table>
	    		</form>
    		</div>
    		<table id="report-table-branduserAssessExtend"></table>
    		<div id="branduserAssessExtend-dialog-operate"></div>
    	</div>
    </div>
    <script type="text/javascript">
    	var report_chooseNo;
    	function frm_focus(val){
    		report_chooseNo = val;
    	}
    	function frm_blur(val){
    		if(val == report_chooseNo){
    			report_chooseNo = "";
    		}
    	}
    	
   		var initqueryJSON = $("#branduserAssessExtend-form-ListQuery").serializeJSON();
    	var branduserAssessExtend_AjaxConn = function (Data, Action) {
		   var MyAjax = $.ajax({
		        type: 'post',
		        cache: false,
		        url: Action,
		        data: Data,
		        async: false
		    })
		    return MyAjax.responseText;
		}
		//根据初始的列与用户保存的列生成以及字段权限生成新的列
    	var branduserAssessExtendListColJson=$("#report-table-branduserAssessExtend").createGridColumnLoad({
	     	name:'t_report_branduser_assess_extend',
	     	frozenCol:[[{field:'ck',title:'',width:60,checkbox:true}]],
	     	commonCol:[[
				{field:'id',title:'编码',width:80,sortable:true,hidden:true},
				{field:'businessdate',title:'日期',width:70,sortable:true},
				{field:'brandusername',title:'人员编码',width:60,sortable:true,isShow:true,hidden:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.branduser;
					}
				},
				{field:'branduser',title:'人员名称',width:80,sortable:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.brandusername;
					}
				},
				{field:'salesarea',title:'销售区域',width:80,sortable:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.salesareaname;
					}
				},
				{field:'wdtargetamount',title:'回笼指标',sortable:true,resizable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'otherwdamount',title:'其他回笼',resizable:true,sortable:true,
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'kpitargetamount',title:'kpi目标',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'kpibonusamount',title:'kpi奖金',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'wardenamount',title:'区长',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterBigNum(val);
						}
						else{
							return "0";
						}
					}
				},
				{field:'remark',title:'备注',width:100,sortable:true},
				{field:'addusername',title:'添加人',width:80,sortable:true,hidden:true},
				{field:'addtime',title:'添加时间',width:130,sortable:true,hidden:true}
			]]
	     });
	     
	     function refreshLayout(title, url){
	   		$('#branduserAssessExtend-dialog-operate').dialog({  
			    title: title,  
			    width: 500,  
			    height: 250,  
			    closed: false,  
			    cache: false,  
			    href: url,  
			    modal: true,
			    onLoad:function(data){
			    	var type = $("#report-type-page").val();
			    	if("add" == type){
			    		$("#report-widget-salesarea").focus();
			    	}else{
			    		$("#report-numberbox-wdtargetamount").focus();
			    	}
			    }
			});
			$('#branduserAssessExtend-dialog-operate').dialog('open');
	   	}
	   	
	   	//通用查询组建调用
//		$("#branduserAssessExtend-query-advanced").advancedQuery({
//	 		name:'t_report_branduser_assess_extend',
//	 		datagrid:'report-table-branduserAssessExtend'
//		});
	   	
	   	$(function(){
			//品牌业务员查询
		  	$("#reportList-widget-branduser").widget({
		  		width:120,
				referwid:'RL_T_BASE_PERSONNEL_BRANDSELLER',
				singleSelect:true,
				singleSelect:true
			});
			
			//回车事件
			controlQueryAndResetByKey("branduserAssessExtend-query-List","branduserAssessExtend-query-reloadList");
			
	   		//查询
			$("#branduserAssessExtend-query-List").click(function(){
	      		var queryJSON = $("#branduserAssessExtend-form-ListQuery").serializeJSON();
	      		$("#report-table-branduserAssessExtend").datagrid("load",queryJSON);
			});
			
			//重置按钮
			$("#branduserAssessExtend-query-reloadList").click(function(){
				$("#reportList-widget-branduser").widget("clear");
				$("#branduserAssessExtend-form-ListQuery")[0].reset();
				var queryJSON = $("#branduserAssessExtend-form-ListQuery").serializeJSON();
				$("#report-table-branduserAssessExtend").datagrid("load",queryJSON);
				
			});
	   		$("#report-button-branduserAssessExtend").buttonWidget({
     			//初始默认按钮 根据type对应按钮事件
				initButton:[
					{},
					<security:authorize url="/report/finance/branduserAssessExtendAddBtn.do">
					{
						type:'button-add',//新增 
						handler:function(){
							refreshLayout('品牌业务员考核【新增】', 'report/finance/showBranduserAssessExtendAddPage.do');
						}
					},
					</security:authorize>
					<security:authorize url="/report/finance/branduserAssessExtendEditBtn.do">
		 			{
			 			type:'button-edit',//修改 
			 			handler:function(){
							var branduserAssessExtend=$("#report-table-branduserAssessExtend").datagrid('getSelected');
				  			if(branduserAssessExtend==null){
				  				$.messager.alert("提醒","请选择相应的品牌业务员考核!");
				  				return false;
				  			}
			     			refreshLayout("品牌业务员考核【修改】", 'report/finance/showBranduserAssessExtendEditPage.do?id='+branduserAssessExtend.id);
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/report/finance/branduserAssessExtendDelBtn.do">
		 			{
			 			type:'button-delete',//删除
			 			handler:function(){
			 				var rows = $("#report-table-branduserAssessExtend").datagrid('getChecked');
				  			if(rows.length == 0){
				  				$.messager.alert("提醒","请选择相应的品牌业务员考核!");
				  				return false;
				  			}
				  			var ids = "";
				  			for(var i=0;i<rows.length;i++){
				  				if(ids == ""){
									ids = rows[i].id;
								}else{
									ids += "," + rows[i].id;
								}
				  			}
				  			if(ids != ""){
					  			$.messager.confirm("提醒","是否确认删除品牌业务员考核?",function(r){
					  				if(r){
					  					loading("提交中..");
					  					var ret = branduserAssessExtend_AjaxConn({ids:ids},'report/finance/deleteBranduserAssessExtend.do');
										var retJson = $.parseJSON(ret);
										loaded();
										$("#report-table-branduserAssessExtend").datagrid('reload');
										$("#report-table-branduserAssessExtend").datagrid('clearSelections');
										$.messager.alert("提醒",""+retJson.sucnum+"条删除成功;</ br>"+retJson.unsucnum+"条删除失败;");
					  				}
					  			});
				  			}
			 			}
		 			},
		 			</security:authorize>
		 			<security:authorize url="/report/finance/branduserAssessExtendImportBtn.do">
		 			{
			 			type:'button-import',//导入
			 			attr:{
			 				clazz: "financeFundsReturnService", //spring中注入的类名
					 		method: "addDRbranduserAssessExtend", //插入数据库的方法
					 		tn: "t_report_branduser_assess_extend", //表名
				            module: 'report', //模块名，
					 		pojo: "BranduserAssessExtend", //实体类名，将和模块名组合成com.hd.agent.journalsheet.model.ExpensesEntering。
							onClose: function(){ //导入成功后窗口关闭时操作，
						         $("#report-table-branduserAssessExtend").datagrid('reload');	//更新列表	                                                                          
							}
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/report/finance/branduserAssessExtendExportBtn.do">
		 			{
			 			type:'button-export',//导出 
			 			attr:{
							queryForm: "#branduserAssessExtend-form-ListQuery", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
					 		tn: 't_report_branduser_assess_extend', //表名
					 		name:'品牌业务员考核录入列表'
						}
		 			},
		 			</security:authorize>
                    {
                        type: 'button-commonquery',
                        attr:{
                            name:'t_report_branduser_assess_extend',
                            datagrid:'report-table-branduserAssessExtend'
                        }
                    },
					{}
	 			],
	 			model:'view',
				type:'list',
				tname:'t_report_branduser_assess_extend',
				id:''
     		});
     		
     		$("#report-table-branduserAssessExtend").datagrid({ 
     			authority:branduserAssessExtendListColJson,
	  	 		frozenColumns: branduserAssessExtendListColJson.frozen,
				columns:branduserAssessExtendListColJson.common,
	  	 		fit:true,
	  	 		method:'post',
	  	 		title:'品牌业务员考核列表',
	  	 		showFooter: true,
	  	 		rownumbers:true,
	  	 		sortName:'businessdate',
	  	 		sortOrder:'asc',
	  	 		pagination:true,
	  	 		idField:'id',
	  	 		singleSelect:false,
				pageSize:100,
				checkOnSelect:true,
				selectOnCheck:true,
				showFooter:true,
				toolbar:'#report-table-branduserAssessExtendBtn',
				pageList:[20,30,50,100,200],
			    url:'report/finance/getBranduserAssessExtendList.do',
			    queryParams:initqueryJSON,
		    	onDblClickRow:function(rowIndex, rowData){
		    		<security:authorize url="/report/finance/branduserAssessExtendEditBtn.do">
	     				refreshLayout("品牌业务员考核【修改】", 'report/finance/showBranduserAssessExtendEditPage.do?id='+rowData.id);
	     			</security:authorize>
		    	}
			}).datagrid("columnMoving");
	   	});
	   	
	   	$(document).keydown(function(event){
			switch(event.keyCode){
				case 13://Enter
					if(report_chooseNo == "wdtargetamount"){
						$("#report-numberbox-otherwdamount").focus();
							return false;
					}
					if(report_chooseNo == "otherwdamount"){
						$("#report-numberbox-kpitargetamount").focus();
							return false;
					}
					if(report_chooseNo == "kpitargetamount"){
						$("#report-numberbox-kpibonusamount").focus();
							return false;
					}
					if(report_chooseNo == "kpibonusamount"){
						$("#report-numberbox-wardenamount").focus();
							return false;
					}
					if(report_chooseNo == "wardenamount"){
						$("#report-textarea-remark").focus();
							return false;
					}
					if(report_chooseNo == "remark"){
						$("#branduserAssessExtend-saveAgain-saveMenu").click();
					}
				break;
				case 27://Esc
					$("#branduserAssessExtend-dialog-operate").dialog('close');
				break;
			}
		});
    </script>
  </body>
</html>
