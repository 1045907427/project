<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>指定供应商的采购发票列表</title>
  </head>
  
  <body>
    <table id="account-datagrid-purchaseInvoiceList-supplier"></table>
    <script type="text/javascript">
    	$(function(){
    		$("#account-datagrid-purchaseInvoiceList-supplier").datagrid({ 
				columns:[[
				  {field:'ck',checkbox:true},
				  {field:'id',title:'编号',width:125,sortable:true},
				  {field:'invoiceno',title:'发票号',width:80,sortable:true},
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
				  {field:'iswriteoff',title:'核销状态',width:60,sortable:true,
				  	formatter:function(value,rowData,rowIndex){
				  		if(value=='0'){
				  			return "未核销";
				  		}else if(value=='1'){
				  			return "已核销";
				  		}
		        	}
				  },
				  {field:'status',title:'状态',width:60,sortable:true,
				  	formatter:function(value,rowData,rowIndex){
		        		return getSysCodeName("status",value);
		        	}
				  },
				  {field:'remark',title:'备注',width:80,sortable:true}
	            ]],
				fit:true,
		 		method:'post',
		 		rownumbers:true,
		 		idField:'id',
		 		singleSelect:true,
		 		checkOnSelect:true,
				selectOnCheck:true,
		 		data:JSON.parse('${dataList}'),
				onDblClickRow:function(rowIndex, rowData){
					top.addOrUpdateTab('account/payable/showPurchaseInvoiceEditPage.do?id='+ rowData.id, "采购发票修改");
				}
			});
    	});
    </script>
  </body>
</html>
