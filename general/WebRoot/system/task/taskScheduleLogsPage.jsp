<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head>
  
  <body>
	<table id="taskScheduleLogs-table"></table>
	<script type="text/javascript">
		$(function(){
			$('#taskScheduleLogs-table').datagrid({ 
				columns:[[
					 {field:'taskid',title:'任务编号',width:100},
					 {field:'team',title:'任务组',width:80},
					 {field:'taskname',title:'任务名称',width:100},
					 {field:'executetime',title:'执行时间',width:120,sortable:true}
				]],
	  	 		fit:true,
	  	 		method:'post',
	  	 		rownumbers:true,
	  	 		pagination:true,
	  	 		idField:'id',
	  	 		singleSelect:true,
	  	 		sortOrder:'desc',
	  	 		sortName:'executetime',
				url:'system/task/showTaskLogsList.do?taskid=${taskid}&team=${team}'
			});
		});
	</script>
  </body>
</html>
