<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head>
  
  <body>
    <table id="workjob-table-list"></table>
    <div id="workjob-table-query">
    	<form action="" method="post" id="workjob-from-query">
    		编码:<input type="text" name="id" style="width: 200px;"/>
    		岗位名称:<input type="text" name="jobname" style="width: 200px;"/>
    		<a href="javaScript:void(0);" id="workjob-queay-queryList" class="easyui-linkbutton" iconCls="icon-search">查询</a>
			<a href="javaScript:void(0);" id="workjob-queay-reloadList" class="easyui-linkbutton" iconCls="icon-reload">重置</a>
			<span id="workjob-query-advanced"></span>
    	</form>
    </div>
    <script type="text/javascript">
    	$(function(){
    		var workjobCol=$("#workjob-table-list").createGridColumnLoad({
		     	name:'base_workjob',
		     	frozenCol:[[]],
		     	commonCol:[[
		     				{field:'ck',checkbox:true,width:50},
		     				{field:'id',title:'编码',width:50,sortable:true},
		     				{field:'jobname',title:'岗位名称',width:80,sortable:true},
		     				{field:'state',title:'状态',width:50,sortable:true},
		     				{field:'remark',title:'备注',width:100,sortable:true},
							{field:'adduserid',title:'建档人',width:60,hidden:true,sortable:true},
							{field:'adddeptid',title:'建档部门',width:80,hidden:true,sortable:true},
							{field:'addtime',title:'建档时间',width:115,hidden:true,sortable:true},
							{field:'modifyuserid',title:'最后修改人',width:60,hidden:true,sortable:true},
							{field:'modifytime',title:'最后修改时间',width:115,hidden:true,sortable:true},
							{field:'openuserid',title:'启用人',width:60,hidden:true,sortable:true},
							{field:'opentime',title:'启用时间',width:115,hidden:true,sortable:true},
							{field:'closeuserid',title:'禁用人',width:60,hidden:true,sortable:true},
							{field:'closetime',title:'禁用时间',width:115,hidden:true,sortable:true}
			     	]]
		     });
		    $('#workjob-table-list').datagrid({
	  			authority:workjobCol,
	  	 		frozenColumns:workjobCol.frozen,
				columns:workjobCol.common,
			    fit:true, 
				method:'post',
				rownumbers:true,
				pagination:true,
				idField:'id',
				singleSelect:false,
				toolbar:'#workjob-table-query'
				//url:'basefiles/showPersonnelList.do?belongdeptid='+treeNode.id+"&state="+treeNode.state
			}).datagrid("columnMoving");
    	});
    </script>
  </body>
</html>
