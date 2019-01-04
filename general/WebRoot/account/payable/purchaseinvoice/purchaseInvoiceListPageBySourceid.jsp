<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>采购发票列表页面</title>
    <%@include file="/include.jsp" %>   
  </head>
  
  <body>
    <table id="account-datagrid-purchaseInvoicePage"></table>
     <script type="text/javascript">
    	$(function(){
			var purchaseInvoiceJson = $("#account-datagrid-purchaseInvoicePage").createGridColumnLoad({
				name :'t_account_purchase_invoice',
				commonCol : [[
							  {field:'id',title:'编号',width:125,sortable:true},
							  {field:'businessdate',title:'业务日期',width:80,sortable:true},
							  {field:'supplierid',title:'供应商编码',width:70,sortable:true},
							  {field:'suppliername',title:'供应商名称',width:100,isShow:true},
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
							  {field:'buyuser',title:'采购员',width:80,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return rowData.buyusername;
					        	}
							  },
							  {field:'sourceid',title:'来源单据编号',width:130,sortable:true},
							  {field:'taxamount',title:'含税总金额',resizable:true,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'notaxamount',title:'无税总金额',resizable:true,sortable:true,align:'right',hidden:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'invoiceamount',title:'应付总金额',resizable:true,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'writeoffamount',title:'核销总金额',resizable:true,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'tailamount',title:'尾差金额',resizable:true,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'addusername',title:'制单人',width:80,sortable:true,hidden:true},
							  {field:'addtime',title:'制单时间',width:80,sortable:true,hidden:true},
							  {field:'auditusername',title:'审核人',width:80,hidden:true,sortable:true},
							  {field:'audittime',title:'审核时间',width:80,hidden:true,sortable:true},
							  {field:'stopusername',title:'中止人',width:80,hidden:true,sortable:true},
							  {field:'stoptime',title:'中止时间',width:80,hidden:true,sortable:true},
							  {field:'status',title:'状态',width:60,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return getSysCodeName("status",value);
					        	}
							  },
							  {field:'remark',title:'备注',width:80,sortable:true}
				             ]]
			});
			$("#account-datagrid-purchaseInvoicePage").datagrid({ 
		 		authority:purchaseInvoiceJson,
		 		frozenColumns: purchaseInvoiceJson.frozen,
				columns:purchaseInvoiceJson.common,
		 		fit:true,
		 		method:'post',
		 		rownumbers:true,
		 		pagination:true,
		 		idField:'id',
		 		sortName:'id',
		 		sortOrder:'desc',
		 		singleSelect:true,
		 		data:JSON.parse('${dataList}'),
				onDblClickRow:function(rowIndex, rowData){
					top.addOrUpdateTab('account/payable/showPurchaseInvoiceViewPage.do?id='+ rowData.id, "采购发票查看");
				}
			}).datagrid("columnMoving");
    	});
    </script>
  </body>
</html>
