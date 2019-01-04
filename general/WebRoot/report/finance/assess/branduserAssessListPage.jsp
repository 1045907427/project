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
    		<div id="report-button-branduserAssess"></div>
    	</div>
    	<div data-options="region:'center',border:false" style="border: 0px;">
    		<div id="report-table-branduserAssessBtn">
    			<form action="" id="branduserAssess-form-ListQuery" method="post">
	    			<table cellpadding="0" cellspacing="1" border="0">
	    				<tr>
	    					<td style="padding-left: 10px;">日期:&nbsp;</td>
	    					<td><input name="businessdate1" class="Wdate" style="width:100px;" onfocus="WdatePicker({dateFmt:'yyyy-MM'})" value="${firstDay }"/>
	    						到&nbsp<input name="businessdate2" class="Wdate" style="width:100px;" onfocus="WdatePicker({dateFmt:'yyyy-MM'})" value="${today }"/>
	    					</td>
	    					<td style="padding-left: 10px;">品牌业务员:&nbsp;</td>
	    					<td><input id="reportList-widget-branduser" name="branduser" type="text" style="width: 120px;"/></td>
	    					<td colspan="2" style="padding-left: 10px;">
	    						<a href="javaScript:void(0);" id="branduserAssess-query-List" class="button-qr">查询</a>
					    		<a href="javaScript:void(0);" id="branduserAssess-query-reloadList" class="button-qr">重置</a>
				    			<span id="branduserAssess-query-advanced"></span>
	    					</td>
	    				</tr>
	    			</table>
	    		</form>
    		</div>
    		<table id="report-table-branduserAssess"></table>
    		<div id="branduserAssess-dialog-operate"></div>
    	</div>
    </div>
    <script type="text/javascript">
    	//把form表单的name序列化成JSON对象
   		var initqueryJSON = $("#branduserAssess-form-ListQuery").serializeJSON();
    	var branduserAssess_AjaxConn = function (Data, Action, Str) {
		   var MyAjax = $.ajax({
		        type: 'post',
		        cache: false,
		        url: Action,
		        data: Data,
		        async: false,
		        success:function(data){
		        	loaded();
		        }
		    })
		    return MyAjax.responseText;
		}
		//根据初始的列与用户保存的列生成以及字段权限生成新的列
    	var branduserAssessListColJson=$("#report-table-branduserAssess").createGridColumnLoad({
	     	name:'t_report_branduser_assess',
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
				{field:'wdtargetamount',title:'回笼目标金额',sortable:true,resizable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'wdbonusbasename',title:'回笼奖金基数',width:80,isShow:true,sortable:true,hidden:true},
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
				{field:'realaccomplish',title:'实绩完成',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'kpibonusamount',title:'kpi奖金金额',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'remark',title:'备注',width:100,sortable:true},
				{field:'addusername',title:'添加人',width:80,sortable:true,hidden:true},
				{field:'addtime',title:'添加时间',width:130,sortable:true,hidden:true}
			]]
	     });
	     
	     function refreshLayout(title, url){
	   		$('#branduserAssess-dialog-operate').dialog({  
			    title: title,  
			    width: 500,  
			    height: 250,  
			    closed: false,  
			    cache: false,  
			    href: url,  
			    modal: true
			});
			$('#branduserAssess-dialog-operate').dialog('open');
	   	}
	   	
	   	//通用查询组建调用
		$("#branduserAssess-query-advanced").advancedQuery({
	 		name:'t_report_branduser_assess',
	 		datagrid:'report-table-branduserAssess'
		});
	   	
	   	$(function(){
			//供应商查询
		  	$("#reportList-widget-branduser").widget({
		  		width:120,
				name:'t_report_branduser_assess',
				col:'branduser',
				singleSelect:true
			});
			
			//回车事件
			controlQueryAndResetByKey("branduserAssess-query-List","branduserAssess-query-reloadList");
			
	   		//查询
			$("#branduserAssess-query-List").click(function(){
				//把form表单的name序列化成JSON对象
	      		var queryJSON = $("#branduserAssess-form-ListQuery").serializeJSON();
	      		//调用datagrid本身的方法把JSON对象赋给queryParams 即可进行查询
	      		$("#report-table-branduserAssess").datagrid("load",queryJSON);
			});
			
			//重置按钮
			$("#branduserAssess-query-reloadList").click(function(){
				$("#reportList-widget-branduser").widget("clear");
				$("#branduserAssess-form-ListQuery")[0].reset();
				var queryJSON = $("#branduserAssess-form-ListQuery").serializeJSON();
				$("#report-table-branduserAssess").datagrid("load",queryJSON);
				
			});
	   		$("#report-button-branduserAssess").buttonWidget({
     			//初始默认按钮 根据type对应按钮事件
				initButton:[
					{},
					<security:authorize url="/report/finance/branduserAssessAddBtn.do">
					{
						type:'button-add',//新增 
						handler:function(){
							refreshLayout('品牌业务员考核【新增】', 'report/finance/showBranduserAssessAddPage.do');
						}
					},
					</security:authorize>
					<security:authorize url="/report/finance/branduserAssessEditBtn.do">
		 			{
			 			type:'button-edit',//修改 
			 			handler:function(){
							var branduserAssess=$("#report-table-branduserAssess").datagrid('getSelected');
				  			if(branduserAssess==null){
				  				$.messager.alert("提醒","请选择相应的品牌业务员考核!");
				  				return false;
				  			}
			     			refreshLayout("品牌业务员考核【修改】", 'report/finance/showBranduserAssessEditPage.do?id='+branduserAssess.id);
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/report/finance/branduserAssessDelBtn.do">
		 			{
			 			type:'button-delete',//删除
			 			handler:function(){
			 				var rows = $("#report-table-branduserAssess").datagrid('getChecked');
				  			if(rows.length == 0){
				  				$.messager.alert("提醒","请选择相应的品牌业务员考核!");
				  				return false;
				  			}
				  			var ids = "",noids = "";
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
					  					var ret = branduserAssess_AjaxConn({ids:ids},'report/finance/deleteBranduserAssess.do','删除中..');
										var retJson = $.parseJSON(ret);
										$("#report-table-branduserAssess").datagrid('reload');
										$("#report-table-branduserAssess").datagrid('clearSelections');
										$.messager.alert("提醒",""+retJson.sucnum+"条删除成功;</ br>"+retJson.unsucnum+"条删除失败;");
					  				}
					  			});
				  			}
			 			}
		 			},
		 			</security:authorize>
		 			<security:authorize url="/report/finance/branduserAssessImportBtn.do">
		 			{
			 			type:'button-import',//导入
			 			attr:{
			 				clazz: "financeFundsReturnService", //spring中注入的类名
					 		method: "addDRBranduserAssess", //插入数据库的方法
					 		tn: "t_report_branduser_assess", //表名
				            module: 'report', //模块名，
					 		pojo: "BranduserAssess", //实体类名，将和模块名组合成com.hd.agent.journalsheet.model.ExpensesEntering。
							onClose: function(){ //导入成功后窗口关闭时操作，
						         $("#report-table-branduserAssess").datagrid('reload');	//更新列表	                                                                          
							}
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/report/finance/branduserAssessExportBtn.do">
		 			{
			 			type:'button-export',//导出 
			 			attr:{
							queryForm: "#branduserAssess-form-ListQuery", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
					 		tn: 't_report_branduser_assess', //表名
					 		name:'品牌业务员考核列表'
						}
		 			},
		 			</security:authorize>
					{}
	 			],
	 			model:'base',
				type:'list',
				tname:'t_report_branduser_assess',
				id:''
     		});
     		
     		$("#report-table-branduserAssess").datagrid({ 
     			authority:branduserAssessListColJson,
	  	 		frozenColumns: branduserAssessListColJson.frozen,
				columns:branduserAssessListColJson.common,
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
				toolbar:'#report-table-branduserAssessBtn',
				pageList:[20,30,50,100,200],
			    url:'report/finance/getBranduserAssessListPage.do',
			    queryParams:initqueryJSON,
			    onClickRow:function(rowIndex, rowData){
			    	$("#report-button-branduserAssess").buttonWidget("setDataID", {id:rowData.id, type:'view'});
			    },
		    	onDblClickRow:function(rowIndex, rowData){
	     			refreshLayout("品牌业务员考核【详情】", 'report/finance/showBranduserAssessViewPage.do?id='+rowData.id);
		    	}
			}).datagrid("columnMoving");
	   	});
    </script>
  </body>
</html>
