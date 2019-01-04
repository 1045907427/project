<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>发票关联收款单列表</title>
  </head>
  
  <body>
    <table id="account-relateCollectionOrder-list-table"></table>
   <script type="text/javascript">
   		$(function(){
   			$("#account-relateCollectionOrder-list-table").datagrid({
   				columns:[[
							  {field:'orderid',title:'收款单编号',width:125,
							  	formatter:function(value,rowData,rowIndex){
					        		return '<a href="javascript:showCollectionOrder(\''+value+'\')">'+value+'</a>';
					        	}
							  },
							  {field:'orderamount',title:'收款单金额',width:80,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'invoiceamount',title:'核销单据金额',width:80,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'relateamount',title:'关联金额',width:80,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  }
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
   		function showCollectionOrder(id){
   			top.addOrUpdateTab('account/receivable/showCollectionOrderViewPage.do?id='+ id, "收款单查看");
   		}
   </script>
  </body>
</html>
