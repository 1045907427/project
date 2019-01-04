<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>传阅人列表</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
  </head>
  
  <body>
  	<table id="fileDistrib-table-showFileDistribList"></table>
  	<script type="text/javascript">
	  	$(document).ready(function(){
		  	if($.trim('${fid}')!=""){
		  		$("#fileDistrib-table-showFileDistribList").datagrid({
		  			fit:true,
		  	 		method:'post',
		  	 		title:'',
		  	 		rownumbers:true,
		  	 		pagination:true,
					singleSelect : false,
				    url:'message/filedistrib/showFileDistribReadPageList.do?fid=${fid}',  
				    columns:[[  
				        {field:'recvusername',title:'阅读人',width:120},
				        {field:'receivetime',title:'接收时间',width:120},
				        {field:'readtime',title:'阅读时间',width:120},
				        {field:'readcount',title:'阅读次数',width:120}
				    ]]
		  		});
		  	}
	  	});
	  </script>
  </body>
</html>
