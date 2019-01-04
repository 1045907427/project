<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>发票关联收款单列表</title>
  </head>
  
  <body>
    <table id="account-relateBill-invoice-table"></table>
   <script type="text/javascript">
   		$(function(){
   			$("#account-relateBill-invoice-table").datagrid({
   				columns:[[
							  {field:'orderid',title:'收款单编号',width:125},
							  {field:'invoiceamount',title:'收款单据金额',resizable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'writeoffamount',title:'核销收款总金额',resizable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'relateamount',title:'已关联收款金额',resizable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'customerid',title:'客户编号',width:60},
							  {field:'customername',title:'客户名称',width:125},
							  {field:'businessdate',title:'业务日期',width:80},
							  {field:'writeoffdate',title:'核销日期',width:80},
							  {field:'remark',title:'备注',resizable:true}
				             ]],
				fit:true,
		 		method:'post',
		 		rownumbers:true,
		 		singleSelect: false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
		 		data:JSON.parse('${detailList}')
   			});
   		});
   </script>
  </body>
</html>
