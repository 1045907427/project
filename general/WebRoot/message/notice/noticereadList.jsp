<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    
    <title>公告通知阅读人列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">

  </head>
  
  <body>
	  <div style="height:100%">	  	
		<div class="easyui-layout" data-options="fit:true">
			<div title="已阅人列表" data-options="region:'center'">
       	 		<table id="messageNotice-table-showNoticereadList"></table>
			</div>
		</div>
	  </div>
	  <div style="clear:both"></div>
	  <script type="text/javascript">
	  	$(document).ready(function(){
		  	if($.trim('${noticeid}')!=""){
		  		$("#messageNotice-table-showNoticereadList").datagrid({
		  			fit:true,
		  	 		method:'post',
		  	 		title:'',
		  	 		rownumbers:true,
		  	 		pagination:true,
					singleSelect : false,
				    url:'message/notice/showNoticereadPageList.do?noticeid=${noticeid}',  
				    columns:[[
                        {field:'recvuserdeptname',title:'阅读人所属部门',width:120},
				        {field:'recvusername',title:'阅读人',width:120},
				        {field:'receivetime',title:'阅读时间',width:120}
				    ]]
		  		});
		  	}
	  	});
	  </script>
  </body>
</html>
