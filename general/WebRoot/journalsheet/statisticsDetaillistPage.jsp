<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>贷款明细列表页面</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
  	<form action="" id="report-query-form-expensesentering" method="post">
   		<input type="hidden" name="supplierid" value="${supplierid}"/>
		<input type="hidden" name="version" value="${version}"/>
		<input type="hidden" name="supplierdeptid" value="${supplierdeptid}"/>
		<input type="hidden" name="begintime" value="${begintime}"/>
		<input type="hidden" name="endtime" value="${endtime}"/>
   	</form>
    <table id="finance-table-expensesEntering"></table>
    <script type="text/javascript">
    	var initQueryJSON = $("#report-query-form-expensesentering").serializeJSON();
		//根据初始的列与用户保存的列生成以及字段权限生成新的列
    	var expensesEnteringListColJson=$("#finance-table-expensesEntering").createGridColumnLoad({
	     	name:'t_finance_expensesentering',
	     	frozenCol:[[]],
	     	commonCol:[[
	     		{field:'businessdate',title:'业务日期',width:80,sortable:true},
				{field:'id',title:'编码',width:80,sortable:true,hidden:true},
				{field:'supplierid',title:'供应商编码',width:80,sortable:true},
				{field:'suppliername',title:'供应商名称',width:220,sortable:true},
				{field:'supplierdeptid',title:'所属部门',width:80,sortable:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.supplierdeptName;
					}
				},
				{field:'subjectid',title:'科目编码',width:80,sortable:true,hidden:true},
				{field:'subjectname',title:'科目名称',width:80,sortable:true},
				{field:'subjectexpenses',title:'科目贷款',resizable:true,sortable:true,align:'right',
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
				{field:'adduserid',title:'添加人编码',width:80,sortable:true,hidden:true},
				{field:'addusername',title:'添加人',width:80,sortable:true,hidden:true},
				{field:'addtime',title:'添加时间',width:130,sortable:true,hidden:true}
			]]
	     });
	   	$(function(){
     		$("#finance-table-expensesEntering").datagrid({ 
     			authority:expensesEnteringListColJson,
	  	 		frozenColumns:expensesEnteringListColJson.frozen,
				columns:expensesEnteringListColJson.common,
	  	 		fit:true,
	  	 		method:'post',
	  	 		idField:'id',
	  	 		rownumbers:true,
	  	 		singleSelect:true,
	  	 		showFooter: true,
	  	 		pagination:true,
				pageSize:100,
				pageList:[10,30,50,100,200],
	  	 		url: 'journalsheet/statisticals/getStatisticsDetaillist.do',
				queryParams:initQueryJSON
			}).datagrid("columnMoving");
	   	});
    </script>
  </body>
</html>
