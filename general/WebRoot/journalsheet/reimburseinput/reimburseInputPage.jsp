<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>代垫录入页面</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'north'">
    		<div class="buttonBG" id="journalsheet-button-reimburseInput"></div>
    	</div>
    	<div data-options="region:'center',border:false">
            <table id="journalsheet-table-reimburseInput"></table>
    		<div id="journalsheet-table-reimburseInputBtn" style="padding:2px;height:auto">
    			<form action="" id="reimburseInput-form-ListQuery" method="post" style="padding-left: 5px; padding-top: 2px;">
	    			<table class="querytable">
	    				<tr>
	    					<td style="padding-left: 10px;">供&nbsp;应&nbsp;商:&nbsp;</td>
	    					<td><input id="journalsheet-widget-supplierquery" name="supplierid" type="text" style="width: 320px;"/></td>
	    					<td style="padding-left: 10px;">科目名称:&nbsp;</td>
	    					<td><input id="journalsheet-widget-subjectidquery" name="subjectid" type="text" style="width: 120px;"/></td>
	    				</tr>
	    				<tr>
	    					<td style="padding-left: 10px;">业务日期:&nbsp;</td>
	    					<td><input id="begintime" name="begintime" class="Wdate" style="width:100px;height: 20px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
   								到&nbsp<input id="endtime" name="endtime" class="Wdate" style="width:100px;height: 20px;" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
	    					</td>
	    					<td style="padding-left: 10px;">所属部门:&nbsp;</td>
	    					<td><input id="journalsheet-widget-supplierdeptidquery" name="supplierdeptid" type="text" style="width: 120px;"/></td>
	    				</tr>
	    				<tr>
	    					<td style="padding-left: 10px;">品牌名称:&nbsp;</td>
	    					<td colspan="2"><input id="journalsheet-widget-brandquery" name="brandid" type="text" style="width: 120px;"/>
	    						<a href="javaScript:void(0);" id="reimburseInput-query-List" class="easyui-linkbutton" iconCls="icon-search" title="[Alt+Q]查询">查询</a>
					    		<a href="javaScript:void(0);" id="reimburseInput-query-reloadList" class="easyui-linkbutton" iconCls="icon-reload" title="[Alt+R]重置">重置</a>
				    			<span id="reimburseInput-query-advanced"></span>
	    					</td>
	    				</tr>
	    			</table>
	    		</form>
    		</div>
    		<div id="reimburseInput-dialog-operate"></div>
    	</div>
    </div>
    <script type="text/javascript">
    	var reimburseInput_AjaxConn = function (Data, Action, Str) {
    		if(null != Str && "" != Str){
    			loading(Str);
    		}
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
    	var reimburseInputListColJson=$("#journalsheet-table-reimburseInput").createGridColumnLoad({
	     	name:'t_js_reimburseinput',
	     	frozenCol:[[]],
	     	commonCol:[[
	     		{field:'businessdate',title:'业务日期',width:80,sortable:true},
				{field:'id',title:'编码',width:80,sortable:true,hidden:true},
				{field:'supplierid',title:'供应商编码',width:70,sortable:true},
				{field:'supplierName',title:'供应商名称',width:210,sortable:true,isShow:true},
				{field:'supplierdeptid',title:'所属部门',width:70,sortable:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.supplierdeptName;
					}
				},
				{field:'brandid',title:'品牌名称',width:60,sortable:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.brandName;
					}
				},
				{field:'subjectid',title:'科目名称',width:60,sortable:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.subjectName;
					}
				},
				{field:'planreimbursetype',title:'计划收回方式',width:80,sortable:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.planreimbursetypeName;
					}
				},
				{field:'planreimbursetime',title:'计划收回时间',width:85,sortable:true},
				{field:'reimbursetype',title:'收回方式',width:60,sortable:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.reimbursetypeName;
					}
				},
				{field:'planamount',title:'计划金额',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'takebackamount',title:'收回金额',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'actingmatamount',title:'代垫金额',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'remark',title:'备注',width:80,sortable:true},
				{field:'adduserid',title:'制单人编码',width:80,sortable:true,hidden:true},
				{field:'addusername',title:'制单人',width:80,sortable:true,hidden:true},
				{field:'addtime',title:'制单时间',width:130,sortable:true,hidden:true}
			]]
	     });
	     
	    //判断是否加锁
		function isLockData(id,tablename){
			var flag = false;
			$.ajax({
	            url :'system/lock/isLockData.do',
	            type:'post',
	            data:{id:id,tname:tablename},
	            dataType:'json',
	            async: false,
	            success:function(json){
	            	flag = json.flag
	            }
	        });
	        return flag;
		}
	    //加锁
	    function isDoLockData(id,tablename){
	    	var flag = false;
	    	$.ajax({   
	            url :'system/lock/isDoLockData.do',
	            type:'post',
	            data:{id:id,tname:tablename},
	            dataType:'json',
	            async: false,
	            success:function(json){
	            	flag = json.flag
	            }
	        });
	        return flag;
	    }
	    
	    function refreshLayout(title, url){
	   		$('#reimburseInput-dialog-operate').dialog({  
			    title: title,  
			    width: 520,  
			    height: 340,  
			    closed: false,  
			    cache: false,  
			    href: url,  
			    modal: true
			});
			$('#reimburseInput-dialog-operate').dialog('open');
	   	}
	   	
		//通用查询组建调用
		$("#reimburseInput-query-advanced").advancedQuery({
			//查询针对的表
	 		name:'t_js_reimburseinput',
	 		//查询针对的表格id
	 		datagrid:'journalsheet-table-reimburseInput'
		});
		
		$(function(){
			//供应商查询
		  	$("#journalsheet-widget-supplierquery").widget({
		  		width:300,
				name:'t_js_reimburseinput',
				col:'supplierid',
				singleSelect:true,
				view:true
			});
			
			//科目查询
		  	$("#journalsheet-widget-subjectidquery").widget({
		  		width:120,
				name:'t_js_reimburseinput',
				col:'subjectid',
				singleSelect:true,
				initSelectNull:true
			});
			
			//商品品牌查询
			$("#journalsheet-widget-brandquery").widget({
		  		width:120,
				name:'t_js_reimburseinput',
				col:'brandid',
				singleSelect:true
			});
			
			$("#journalsheet-widget-supplierdeptidquery").widget({
		  		width:120,
				name:'t_js_reimburseinput',
				col:'supplierdeptid',
				singleSelect:true
			});
			
			//回车事件
			controlQueryAndResetByKey("reimburseInput-query-List","reimburseInput-query-reloadList");
			
			//查询
			$("#reimburseInput-query-List").click(function(){
				//把form表单的name序列化成JSON对象
	      		var queryJSON = $("#reimburseInput-form-ListQuery").serializeJSON();
	      		//调用datagrid本身的方法把JSON对象赋给queryParams 即可进行查询
	      		$("#journalsheet-table-reimburseInput").datagrid("load",queryJSON);
			});
			
			//重置按钮
			$("#reimburseInput-query-reloadList").click(function(){
				$("#reimburseInput-form-ListQuery")[0].reset();
				$("#journalsheet-table-reimburseInput").datagrid("load",{});
				$("#journalsheet-widget-supplierquery").widget('clear');
				$("#journalsheet-widget-subjectidquery").widget('clear');
				$("#journalsheet-widget-brandquery").widget('clear');
			});
			
			$("#journalsheet-button-reimburseInput").buttonWidget({
     			//初始默认按钮 根据type对应按钮事件
				initButton:[
					{},
					<security:authorize url="/journalsheet/reimburseInput/reimburseInputAddBtn.do">
					{
						type:'button-add',//新增 
						handler:function(){
							refreshLayout('代垫录入【新增】', 'journalsheet/reimburseInput/showReimburseInputAddPage.do');
						}
					},
					</security:authorize>
					<security:authorize url="/journalsheet/reimburseInput/reimburseInputEditBtn.do">
		 			{
			 			type:'button-edit',//修改 
			 			handler:function(){
							var reimburseInput=$("#journalsheet-table-reimburseInput").datagrid('getSelected');
				  			if(reimburseInput==null){
				  				$.messager.alert("提醒","请选择相应的代垫录入!");
				  				return false;
				  			}
				  			var flag = isDoLockData(reimburseInput.id,"t_js_reimburseinput");
							if(!flag){
								$.messager.alert("警告","该数据正在被其他人操作，暂不能修改！");
								return false;
							}
			     			refreshLayout("代垫录入【修改】", 'journalsheet/reimburseInput/showReimburseInputEditPage.do?id='+reimburseInput.id);
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/journalsheet/reimburseInput/reimburseInputDelBtn.do">
		 			{
			 			type:'button-delete',//删除
			 			handler:function(){
			 				var reimburseInput=$("#journalsheet-table-reimburseInput").datagrid('getSelected');
				  			if(reimburseInput==null){
				  				$.messager.alert("提醒","请选择相应的代垫录入!");
				  				return false;
				  			}
				  			var flag = isLockData(reimburseInput.id,"t_js_reimburseinput");
							if(flag){
								$.messager.alert("警告","该数据正在被其他人操作，暂不能删除！");
								return false;
							}
				  			$.messager.confirm("提醒","是否确认删除代垫录入?",function(r){
				  				if(r){
				  					var ret = reimburseInput_AjaxConn({id:reimburseInput.id},'journalsheet/reimburseInput/deleteReimburseInput.do','删除中..');
									var retJson = $.parseJSON(ret);
									if(retJson.delFlag){
					  					$.messager.alert("提醒","该信息已被其他信息引用，无法删除！");
					  					return false;
					  				}
									if(retJson.flag){
										$("#journalsheet-table-reimburseInput").datagrid('reload');
										$("#journalsheet-table-reimburseInput").datagrid('clearSelections');
										$.messager.alert("提醒","删除成功!");
									}
									else{
										$.messager.alert("提醒","删除失败!");
									}
				  				}
				  			});
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/journalsheet/reimburseInput/reimburseInputImportBtn.do">
		 			{
			 			type:'button-import',//导入
			 			attr:{
			 				clazz: "journalSheetService", //spring中注入的类名
					 		method: "addDRReimburseInput", //插入数据库的方法
					 		tn: "t_js_reimburseinput", //表名
				            module: 'journalsheet', //模块名，
					 		pojo: "ReimburseInput", //实体类名，将和模块名组合成com.hd.agent.journalsheet.model.ReimburseInput。
							onClose: function(){ //导入成功后窗口关闭时操作，
						        var queryJSON = $("#reimburseInput-form-ListQuery").serializeJSON();
						        $("#journalsheet-table-reimburseInput").datagrid("load",queryJSON);
							}
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/journalsheet/reimburseInput/reimburseInputExportBtn.do">
		 			{
			 			type:'button-export',//导出 
			 			attr:{
							queryForm: "#reimburseInput-form-ListQuery", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
					 		tn: 't_js_reimburseinput', //表名
					 		name:'代垫录入列表'
						}
		 			},
		 			</security:authorize>
					{}
	 			],
	 			model:'base',
				type:'list',
				tname:'t_js_reimburseinput',
				id:''
     		});
     		
     		$("#journalsheet-table-reimburseInput").datagrid({ 
     			authority:reimburseInputListColJson,
	  	 		frozenColumns:[[]],
				columns:reimburseInputListColJson.common,
	  	 		fit:true,
	  	 		method:'post',
	  	 		title:'代垫录入列表',
	  	 		showFooter: true,
	  	 		rownumbers:true,
	  	 		sortName:'businessdate',
	  	 		sortOrder:'asc',
	  	 		pagination:true,
	  	 		idField:'id',
	  	 		singleSelect:true,
				pageSize:100,
				toolbar:'#journalsheet-table-reimburseInputBtn',
			    url:'journalsheet/reimburseInput/getReimburseInputListPage.do',
			    onClickRow:function(rowIndex, rowData){
			    	$("#journalsheet-button-reimburseInput").buttonWidget("setDataID", {id:rowData.id, type:'view'});
			    },
		    	onDblClickRow:function(rowIndex, rowData){
	     			refreshLayout("代垫录入【详情】", 'journalsheet/reimburseInput/showReimburseInputViewPage.do?id='+rowData.id);
		    	}
			}).datagrid("columnMoving");
		});
    </script>
  </body>
</html>
