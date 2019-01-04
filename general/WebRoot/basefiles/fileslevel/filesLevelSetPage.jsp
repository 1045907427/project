<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>基础档案级次定义</title>
	<%@include file="/include.jsp" %>  
  </head>
  
  <body>
  	<div class="easyui-panel" data-options="fit:true" style="padding:2px;">
      	<div class="easyui-layout" data-options="fit:true">  
            <div title="档案类型" data-options="region:'west',split:true" style="width:350px;">
		     	<table id="basefiles-filesLevel-table"></table>
		    </div>
		    <div title="档案级次定义" data-options="region:'center',split:true,border:true">
		    	<div id="basefiles-filesLevel-define" class="easyui-panel"></div>
			</div>
		  </div>
     </div>
     <script type="text/javascript">
     	$(function(){
	     	//加载权限列表
			$('#basefiles-filesLevel-table').datagrid({ 
	  	 		fit:true,
	  	 		method:'post',
	  	 		rownumbers:true,
	  	 		singleSelect:true,
	  	 		url:'common/getTableList.do?usetreelist=1',
			    columns:[[  
			    	{field:'id',title:'表名',width:100},
			        {field:'name',title:'名称',width:200}
			    ]],
			    onClickRow:function(rowIndex, rowData){
			    	var url = "basefiles/showFilesLevelPage.do?name="+rowData.id+"&type=view";
					$("#basefiles-filesLevel-define").panel({  
					    href:url,
					    cache:false,
					    maximized:true
					});
			    }
			});
		});
     </script>
  </body>
</html>
