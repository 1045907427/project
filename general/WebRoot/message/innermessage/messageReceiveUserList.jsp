<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
  </head>
  
  <body>
	  <div style="height:100%" title="">
        <table id="innerMessage-table-messageReceiveList" ></table>
	</div>
	<script type="text/javascript">
 		$(document).ready(function(){ 			
  	 		<c:choose>
   				<c:when test="${msgContent.clocktype=='2'}">
		 			$("#innerMessage-table-messageReceiveList").datagrid({	
		 				fit:true,
			  	 		method:'post',
			  	 		title:'定时发送接收人列表',
			  	 		rownumbers:true,
			  	 		pagination:true,
			  	 		singleSelect:true,
					    url:'message/innerMessage/showMessageReceiveUserPageList.do?msgid=${msgContent.id}',  
					    columns:[[
					        {field:'recvusername',title:'接收人',width:120},
					        {field:'clocktime',title:'定时时间',width:120},
					        {field:'state',title:'状态',width:120,
					        	formatter: function(value,row,index){
					        		return "定时发送";
				        	}}
					    ]]
		 			});
	 			</c:when>
	 			<c:otherwise>
	 				$("#innerMessage-table-messageReceiveList").datagrid({	
			 				fit:true,
				  	 		method:'post',
				  	 		title:'已发送接收人列表',
				  	 		rownumbers:true,
				  	 		pagination:true,
				  	 		singleSelect:true,
					    	url:'message/innerMessage/showMessageReceiveUserPageList.do?msgid=${msgContent.id}', 
						    columns:[[
						        {field:'recvusername',title:'接收人',width:120,sortable:true},  
						        {field:'sendtime',title:'接收时间',width:120,sortable:true},  
						        {field:'viewflag',title:'阅读状态',width:120,
						        	formatter: function(value,row,index){
						        		if(value=="1"){
						        			return "未阅读";
						        		}else{
						        			return "已阅读";
						        		}
						        }},  
						        {field:'viewtime',title:'阅读时间',width:120}
						    ]]
			 			});
	 			</c:otherwise>
 			</c:choose>
 		});
 	</script>
  </body>
</html>

