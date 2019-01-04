<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>客户资金列表页面</title>
    <%@include file="/include.jsp" %>   
  </head>
  
  <body>
  	<div class="easyui-layout" data-options="fit:true,border:false">
    	<div data-options="region:'north',border:false" class="buttonBG" style="height: 28px">
    		<security:authorize url="/account/receivable/showSalesInvoiceWriteoffPageButton.do">
    		<a href="javaScript:void(0);" id="account-cancel-customerAccount" class="easyui-linkbutton button-list" iconCls="button-writeoff" plain="true" >核销</a>
    		</security:authorize>
    		<security:authorize url="/account/receivable/showCollectionOrderTransferSubmitPage.do">
    		<a href="javaScript:void(0);" id="account-transfer-customerAccount" class="easyui-linkbutton button-list" iconCls="button-transfer" plain="true" >转账</a>
    		</security:authorize>
    		<security:authorize url="/account/receivable/showSalesStatementListPage.do">
    		<a href="javaScript:void(0);" id="account-statement-customerAccount" class="easyui-linkbutton button-list" iconCls="button-view" plain="true">查看对账单</a>
    		</security:authorize>
    		<security:authorize url="/account/receivable/exportSalesStatementListPage.do">
    		<a href="javaScript:void(0);" id="account-export-customerAccount" class="easyui-linkbutton button-list" iconCls="button-export" plain="true">导出</a>
    		</security:authorize>
    	</div>
    	<div data-options="region:'center',border:true">
    		<table id="account-datagrid-customerAccountPage" data-options="border:false"></table>
    	</div>
    </div>
    <div id="account-datagrid-toolbar-customerAccountPage" style="padding-top: 0px">
    	<form action="" id="account-form-query-customerAccountPage" method="post">
    		<table class="querytable">
    			<tr>
    				<td>客&nbsp;&nbsp;&nbsp;户:</td>
    				<td><input id="account-query-customerid" type="text" name="id"/></td>
    				<td>销售区域:</td>
    				<td><input id="account-query-salesarea" type="text" name="salesarea"/></td>
    			</tr>
                <tr>
                    <td>客户业务员:</td>
                    <td><input id="account-query-salesuser" type="text" name="salesuserid"/></td>
                    <td>金额是否为0:</td>
                    <td>
                        <select name="amounttype" style="width: 160px;">
                            <option value="0">否</option>
                            <option value="1">是</option>
                        </select>
                    </td>
                    <td>
                        <a href="javaScript:void(0);" id="account-queay-customerAccount" class="button-qr">查询</a>
                        <a href="javaScript:void(0);" id="account-reload-customerAccount"  class="button-qr">重置</a>
                    </td>
                </tr>
    		</table>
    	</form>
    </div>
    <div id="account-dialog-customerAccountPage"></div>
    <div id="account-dialog-writeoff"></div>
    <div id="account-dialog-statement"></div>
    <div id="account-dialog-transfer"></div>
     <script type="text/javascript">
     	var initQueryJSON = $("#account-form-query-customerAccountPage").serializeJSON();
    	$(function(){
			$("#account-datagrid-customerAccountPage").datagrid({ 
				columns:[[
						  {field:'id',title:'编号',sortable:true,width:80},
						  {field:'name',title:'客户名称',sortable:true,width:200},
						  {field:'salesarea',title:'所属区域',sortable:true,width:100,
						  	formatter:function(value,rowData,rowIndex){
						  		return rowData.salesareaname
				        	}
						  },
						  {field:'salesuserid',title:'客户业务员',sortable:true,width:80,
						  	formatter:function(value,rowData,rowIndex){
						  		return rowData.salesusername
				        	}
						  },
						  {field:'amount',title:'余额',resizable:true,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
						  		if(null!=value){
				        			return formatterMoney(value);
				        		}else{
				        			return 0;
				        		}
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
		 		sortOrder:'asc',
		 		singleSelect:true,
		 		showFooter:true,
				toolbar:'#account-datagrid-toolbar-customerAccountPage',
				onDblClickRow:function(rowIndex, rowData){
					$("#account-dialog-customerAccountPage").dialog({
						href:"account/receivable/showCustomerCapitalLogListPage.do?id="+rowData.id,
						title:"客户资金流水列表",
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
			
			$("#account-query-customerid").widget({
				referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT_2',
    			singleSelect:true,
    			width:160,
    			onlyLeafCheck:false,
    			view:true
			});
			$("#account-query-salesarea").widget({
				referwid:'RT_T_BASE_SALES_AREA',
    			singleSelect:true,
    			width:160,
    			onlyLeafCheck:false,
    			view:true
			});
			$("#account-query-salesuser").widget({
				referwid:'RL_T_BASE_PERSONNEL_SELLER',
    			singleSelect:true,
    			width:160,
    			view:true
			});
			//回车事件
			controlQueryAndResetByKey("account-queay-customerAccount","account-reload-customerAccount");
			
			//查询
			$("#account-queay-customerAccount").click(function(){
				//把form表单的name序列化成JSON对象
	       		var queryJSON = $("#account-form-query-customerAccountPage").serializeJSON();
	       		$("#account-datagrid-customerAccountPage").datagrid({
	      			url: 'account/receivable/showCustomerAccountList.do',
	      			pageNumber:1,
					queryParams:queryJSON
	      		});
			});
			//重置
			$("#account-reload-customerAccount").click(function(){
				$("#account-query-customerid").widget("clear");
				$("#account-query-salesarea").widget("clear");
				$("#account-query-salesuser").widget("clear");
				$("#account-form-query-customerAccountPage")[0].reset();
	       		$("#account-datagrid-customerAccountPage").datagrid("loadData",[]);
			});
			
			//导出
			$("#account-export-customerAccount").Excel('export',{
				queryForm: "#account-form-query-customerAccountPage",
		 		type:'exportUserdefined',
		 		name:'客户资金余额表',
		 		url:'account/receivable/exportCustomerAccountData.do'
			});
			
			$("#account-cancel-customerAccount").click(function(){
				var row = $("#account-datagrid-customerAccountPage").datagrid("getSelected");
				if(null==row){
					$.messager.alert("提醒","请选择客户");
					return false;
				}
				$("#account-dialog-writeoff").dialog({
					href:"account/receivable/showWriteoffCollectionOrderPage.do?id="+row.id,
					title:"核销",
				    fit:true,
					maximizable:true,
					resizable:true,
				    cache: false,  
				    modal: true,
				    buttons:[{
							text:'确定',
							handler:function(){
								collectionOrderWriteOff();
							}
						}]
				});
			});
			//查看客户对账单
			$("#account-statement-customerAccount").click(function(){
				var row = $("#account-datagrid-customerAccountPage").datagrid("getSelected");
				if(null==row){
					$.messager.alert("提醒","请选择客户");
					return false;
				}
				$("#account-dialog-statement").dialog({
					href:"account/receivable/showSalesStatementListPage.do?customerid="+row.id,
					title:row.name+"对账单",
				    fit:true,
					modal:true,
					cache:false,
					maximizable:true,
					resizable:true,
				    cache: false,  
				    modal: true
				});
			});
			$("#account-transfer-customerAccount").click(function(){
				var row = $("#account-datagrid-customerAccountPage").datagrid("getSelected");
				if(null!=row){
		   			$("#account-dialog-transfer").dialog({
						href:"account/receivable/showCollectionOrderTransferSubmitPage.do?customerid="+row.id,
						title:"客户转账",
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
					$.messager.alert("提醒","请选择客户");
				}
			});
    	});
    </script>
  </body>
</html>
