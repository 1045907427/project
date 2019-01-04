<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>供应商资金列表页面</title>
    <%@include file="/include.jsp" %>   
  </head>
  
  <body>
  	<div class="easyui-layout" data-options="fit:true,border:false">
    	<div data-options="region:'north',border:false">
    		<security:authorize url="/account/payable/showPurchaseInvoiceWriteoffPageButton.do">
    		<a href="javaScript:void(0);" id="account-cancel-supplierAccount" class="easyui-linkbutton" iconCls="button-save" plain="true" title="核销">核销</a>
    		</security:authorize>
    		<security:authorize url="/account/payable/showCollectionOrderTransferSubmitPage.do">
    		<a href="javaScript:void(0);" id="account-transfer-supplierAccount" class="easyui-linkbutton" iconCls="button-add" plain="true" title="转账">转账</a>
    		</security:authorize>
    		<security:authorize url="/account/payable/showSalesStatementListPage.do">
    		<a href="javaScript:void(0);" id="account-statement-supplierAccount" class="easyui-linkbutton" iconCls="button-view" plain="true" title="查看对账单">查看对账单</a>
    		</security:authorize>
    	</div>
    	<div data-options="region:'center',border:false">
    		<table id="account-datagrid-supplierAccountPage" data-options="border:false"></table>
    	</div>
    </div>
    <div id="account-datagrid-toolbar-supplierAccountPage">
    	<form action="" id="account-form-query-supplierAccountPage" method="post">
    		<table class="querytable">
    			<tr>
    				<td>供应商:</td>
    				<td><input id="account-query-supplierid" type="text" name="id" style="width: 180px;"/></td>
    				<td>金额大于:</td>
    				<td>
    					<input id="account-query-amount" type="text" name="amount" value="0"/>
    				</td>
    				<td>
    					<a href="javaScript:void(0);" id="account-queay-supplierAccount" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="account-reload-supplierAccount" class="button-qr">重置</a>
    				</td>
    			</tr>
    		</table>
    	</form>
    </div>
    <div id="account-dialog-supplierAccountPage"></div>
    <div id="account-dialog-writeoff"></div>
    <div id="account-dialog-statement"></div>
    <div id="account-dialog-transfer"></div>
     <script type="text/javascript">
    	$(function(){
    		var queryJSON = $("#account-form-query-supplierAccountPage").serializeJSON();
			$("#account-datagrid-supplierAccountPage").datagrid({ 
				columns:[[
						  {field:'id',title:'编号',width:80},
						  {field:'suppliername',title:'供应商名称',width:200},
						  {field:'amount',title:'余额',resizable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'remark',title:'备注',width:80}
			             ]],
		 		fit:true,
		 		method:'post',
		 		rownumbers:true,
		 		pagination:true,
		 		idField:'id',
		 		sortName:'id',
		 		queryParams:queryJSON,
		 		sortOrder:'asc',
		 		showFooter:true,
		 		singleSelect:true,
				url: 'account/payable/showSupplierAccountList.do',
				toolbar:'#account-datagrid-toolbar-supplierAccountPage',
				onDblClickRow:function(rowIndex, rowData){
					$("#account-dialog-supplierAccountPage").dialog({
						href:"account/payable/showSupplierCapitalLogListPage.do?id="+rowData.id,
						title:"供应商资金流水列表",
					    closed:false,
						modal:true,
						cache:false,
						collapsible:false,
					    minimizable:false,
					    maximizable:true,
					    resizable:true,
					    width:750,
					    height:400
					});
				}
			});
			
			$("#account-query-supplierid").widget({
				referwid:'RL_T_BASE_BUY_SUPPLIER',
    			singleSelect:true,
    			width:180,
    			onlyLeafCheck:false,
    			view:true
			});
			
			//回车事件
			controlQueryAndResetByKey("account-queay-supplierAccount","account-reload-supplierAccount");
			
			//查询
			$("#account-queay-supplierAccount").click(function(){
	       		var queryJSON = $("#account-form-query-supplierAccountPage").serializeJSON();
	       		$("#account-datagrid-supplierAccountPage").datagrid("load",queryJSON);
			});
			//重置
			$("#account-reload-supplierAccount").click(function(){
				$("#account-query-supplierid").widget("clear");
				$("#account-form-query-supplierAccountPage")[0].reset();
				$("#account-query-amount").val(0);
				var queryJSON = $("#account-form-query-supplierAccountPage").serializeJSON();
	       		$("#account-datagrid-supplierAccountPage").datagrid("load",queryJSON);
			});
			//核销
			$("#account-cancel-supplierAccount").click(function(){
				var row = $("#account-datagrid-supplierAccountPage").datagrid("getSelected");
				$("#account-dialog-writeoff").dialog({
					href:"account/payable/showWriteoffPayorderPage.do?id="+row.id,
					title:"核销",
				    fit:true,
					modal:true,
					cache:false,
					maximizable:true,
					resizable:true,
				    cache: false,  
				    modal: true,
				    buttons:[{
							text:'确定',
							handler:function(){
								payorderWriteOff();
							}
						}]
				});
			});
			//查看供应商对账单
			$("#account-statement-supplierAccount").click(function(){
				var row = $("#account-datagrid-supplierAccountPage").datagrid("getSelected");
				$("#account-dialog-statement").dialog({
					href:"account/payable/showPurchaseStatementListPage.do?supplierid="+row.id,
					title:row.suppliername+"对账单",
				    fit:true,
					modal:true,
					cache:false,
					maximizable:true,
					resizable:true,
				    cache: false,  
				    modal: true
				});
			});
			//转账
			$("#account-transfer-supplierAccount").click(function(){
				var row = $("#account-datagrid-supplierAccountPage").datagrid("getSelected");
				if(null!=row){
		   			$("#account-dialog-transfer").dialog({
						href:"account/payable/showPayOrderTransferSubmitPage.do?supplierid="+row.id,
						title:"供应商转账",
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
									transferSubmit();
								}
							}]
					});
				}else{
					$.messager.alert("提醒","请选择供应商");
				}
			});
    	});
    </script>
  </body>
</html>
