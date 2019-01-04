<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>仓库下的品牌列表</title>
  </head>
  
  <body>
		<table id="checklist-storage-brandlist"></table>
  		<script type="text/javascript">
  			$(function(){
  				$("#checklist-storage-brandlist").datagrid({ 
					columns:[[
						 {field:'ck',checkbox:true},
						 {field:'id',title:'编号',width:80},
						 {field:'name',title:'品牌名称',width:150}
					]],
			 		fit:true,
			 		method:'post',
			 		rownumbers:true,
			 		idField:'id',
			 		singleSelect:false,
			 		checkOnSelect:true,
			 		selectOnCheck:true
				});
				$("#checklist-storage-brandlist").datagrid("loadData",JSON.parse('${detailList}'));
  			});
  		</script>
  </body>
</html>
