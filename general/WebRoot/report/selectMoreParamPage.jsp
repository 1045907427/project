<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>多选页面</title>
  </head>
  
  <body>
  	<table id="report-datagrid-selectmoreparam"></table>
  	<script type="text/javascript">
  		$("#report-datagrid-selectmoreparam").datagrid({
			fit:true,
			columns:[[
				{field:'ck',checkbox:true},
		        {field:'id',title:'编码',width:80},
		     	{field:'name',title:'名称',width:300}
		    ]],
			border:false,
			rownumbers:true,
			pagination:true,
			idField:'id',
			singleSelect:false,
			url:'basefiles/getCustomerListData.do?type=${type}',
			onBeforeLoad:function(){},
			onSelect:function(rowIndex, rowData){
				$(this).datagrid('checkRow',rowIndex);
			},
			onUnselect:function(rowIndex, rowData){
				$(this).datagrid('uncheckRow',rowIndex);
			}
   		});
   		
   		function getCustomerValues(){
   			var rows = $("#report-datagrid-selectmoreparam").datagrid('getChecked');
   			var names = null,ids = null;
   			for(var i=0;i<rows.length;i++){
   				if(names == null || names == ""){
   					names = rows[i].name;
   					ids = rows[i].id;
   				}
   				else{
   					names += "," + rows[i].name;
   					ids += "," + rows[i].id;
   				}
   			}
   			if("${type}" == "customer"){
   				if(ids != null && ids != ""){
   					$("#report-customernamemore-advancedQuery").val(names);
   					$("#report-hdcustomerid-advancedQuery").val(ids);
   				}
   			}
   			else if("${type}" == "pcustomer"){
   				if(ids != null && ids != ""){
   					$("#report-pcustomernamemore-advancedQuery").val(names);
   					$("#report-hdpcustomerid-advancedQuery").val(ids);
   				}
   			}
   			$("#report-dialog-selectmoreparam").dialog('close');
   		}
	</script>
  </body>
</html>
