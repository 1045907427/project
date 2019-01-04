<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>付款单列表页面</title>
    <%@include file="/include.jsp" %>   
  </head>
  
  <body>
    <div id="account-buttons-payorderMergePage"></div>
    <div id="account-datagrid-toolbar-payorderPage">
    	<form action="" id="account-form-query-payorderPage" method="post">
    		<input type="hidden" name="status" value="3"/>
    		<table>
    			<tr>
    				<td>供应商:</td>
    				<td><input id="account-query-supplierid" type="text" name="supplierid" style="width: 180px;"/></td>
    				<td colspan="2">
    					<a href="javaScript:void(0);" id="account-query-payorder" class="easyui-linkbutton" iconCls="icon-search" plain="true" title="[Alt+Q]查询">查询</a>
						<a href="javaScript:void(0);" id="account-reload-payorder" class="easyui-linkbutton" iconCls="icon-reload" plain="true" title="[Alt+R]重置">重置</a>
						<a href="javaScript:void(0);" id="account-merge-payorder" class="easyui-linkbutton" iconCls="button-save" plain="true">合并</a>
    				</td>
    			</tr>
    		</table>
    	</form>
    </div>
    <div id="account-dialog-payorder-MergeSubmit"></div>
    <script type="text/javascript">
    	$(function(){
			var tableColumnListJson = $("#account-buttons-payorderMergePage").createGridColumnLoad({
				name :'t_account_purchase_payorder',
				frozenCol : [[
			    			]],
				commonCol : [[
							  {field:'ck',checkbox:true},
							  {field:'id',title:'编号',width:130,sortable:true},
							  {field:'businessdate',title:'业务日期',width:80,sortable:true},
							  {field:'supplierid',title:'供应商',width:150,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return rowData.suppliername;
					        	}
							  },
							  {field:'handlerid',title:'对方经手人',width:80,sortable:true,hidden:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return rowData.handlername;
					        	}
							  },
							  {field:'buydept',title:'采购部门',width:100,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return rowData.buydeptname;
					        	}
							  },
							  {field:'buyuser',title:'采购员',width:100,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return rowData.buyusername;
					        	}
							  },
							  {field:'amount',title:'付款金额',width:80,align:'right',sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'writeoffamount',title:'已核销金额',align:'right',width:80,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'remainderamount',title:'剩余金额',width:80,align:'right',sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'addusername',title:'制单人',width:80,sortable:true},
							  {field:'addtime',title:'制单日期',width:80,sortable:true},
							  {field:'auditusername',title:'审核人',width:80,sortable:true,hidden:true},
							  {field:'audittime',title:'审核日期',width:80,sortable:true,hidden:true},
							  {field:'stopusername',title:'中止人',width:80,hidden:true,hidden:true},
							  {field:'stoptime',title:'中止日期',width:80,hidden:true,sortable:true,hidden:true},
							  {field:'status',title:'状态',width:60,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return getSysCodeName("status",value);
					        	}
							  },
							  {field:'remark',title:'备注',width:80,sortable:true}
				             ]]
			});
			$("#account-buttons-payorderMergePage").datagrid({ 
		 		authority:tableColumnListJson,
		 		frozenColumns: tableColumnListJson.frozen,
				columns:tableColumnListJson.common,
		 		fit:true,
		 		title:"",
		 		method:'post',
		 		rownumbers:true,
		 		pagination:true,
		 		idField:'id',
		 		singleSelect:true,
				toolbar:'#account-datagrid-toolbar-payorderPage'
			}).datagrid("columnMoving");
			$("#account-query-supplierid").widget({
				name:'t_account_purchase_payorder',
				col:'supplierid',
    			singleSelect:true,
    			width:180,
    			onlyLeafCheck:false,
    			view:true
			});
			
			//回车事件
			controlQueryAndResetByKey("account-query-payorder","account-reload-payorder");
			
			//查询
			$("#account-query-payorder").click(function(){
				//把form表单的name序列化成JSON对象
	       		var queryJSON = $("#account-form-query-payorderPage").serializeJSON();
	       		$("#account-buttons-payorderMergePage").datagrid({
	       			url: 'account/payable/showPayorderList.do',
					queryParams:queryJSON
	       		});
			});
			//重置
			$("#account-reload-payorder").click(function(){
				$("#account-query-supplierid").widget("clear");
				$("#account-form-query-payorderPage")[0].reset();
	       		$("#account-buttons-payorderMergePage").datagrid("load",{});
			});
			//合并
			$("#account-merge-payorder").click(function(){
				var rows = $("#account-buttons-payorderMergePage").datagrid("getChecked");
				if(null!=rows && rows.length>1){
					var ids = "";
					for(var i=0;i<rows.length;i++){
		   				if(ids == ""){
		   					ids = rows[i].id;
		   				}else{
		   					ids += ","+rows[i].id;
		   				}
		   			}
		   			$("#account-dialog-payorder-MergeSubmit").dialog({
						href:"account/payable/showPayorderMergeSubmitPage.do?ids="+ids,
						title:"付款单合并",
					    width:700,
					    height:400,
						modal:true,
						cache:false,
						maximizable:true,
						resizable:true,
					    cache: false,  
					    modal: true,
					    buttons:[{
								text:'确定',
								handler:function(){
									mergeSubmit();
								}
							}]
					});
				}else{
					$.messager.alert("提醒","请勾选多条数据");
				}
			});
		});
    </script>
  </body>
</html>
