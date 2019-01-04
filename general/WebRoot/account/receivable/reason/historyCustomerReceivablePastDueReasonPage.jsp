<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  	<title>历史超账期原因列表页面</title>
  	<script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>  
  </head>
  <body>
    <table id="report-datagrid-historyReceivablePastDueReason"></table>
    <script type="text/javascript">
    	$("#report-datagrid-historyReceivablePastDueReason").datagrid({
    		columns:[[
    			{field:'customerid',title:'客户编号',width:60},
				{field:'customername',title:'客户名称',width:210},
				{field:'saleamount',title:'应收款',align:'right',resizable:true,sortable:true,
				  	formatter:function(value,rowData,rowIndex){
		        		return formatterMoney(value);
		        	}
				},
				{field:'unpassamount',title:'正常期金额',align:'right',resizable:true,sortable:true,
				  	formatter:function(value,rowData,rowIndex){
		        		return formatterMoney(value);
		        	}
				},
				{field:'totalpassamount',title:'超账期金额',align:'right',resizable:true,sortable:true,
				  	formatter:function(value,rowData,rowIndex){
	        			return formatterMoney(value);
		        	},
		        	styler:function(value,rowData,rowIndex){
		        		return 'color:blue';
		        	}
				},
				{field:'overreason',title:'超账期原因',resizable:true},
				{field:'commitmentamount',title:'承诺到款金额',align:'right',resizable:true,
				  	formatter:function(value,rowData,rowIndex){
	        			if(value != "" && value != null){
	        				return formatterMoney(value);
	        			}
		        	}	
				},
				{field:'commitmentdate',title:'承诺到款日期',width:90}
    		]],
    		method:'post',
  	 		title:'',
  	 		fit:true,
  	 		rownumbers:true,
  	 		pagination:true,
  	 		pageSize:100,
  	 		singleSelect:true,
  	 		url:'account/receivable/getHistoryCustomerReceivablePastDueReasonList.do?customerid=${customerid}'
    	});
    </script>
  </body>
</html>
