<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>内部短信消息管理</title>    
		<%@include file="/include.jsp" %> 	
	  	<script type="text/javascript" src="js/kindeditor/kindeditor-min.js"></script> 
	  	<script type="text/javascript" src="js/kindeditor/lang/zh_CN.js"></script> 
	  	<script type="text/javascript" src="js/linq.min.js"></script> 	
		<link rel="stylesheet" href="css/icon-extend.css" type="text/css"></link>
	</head>

	<body>
		<div class="easyui-panel"  fit="true" >
			<div class="easyui-layout" fit="true">
				<div title="" data-options="region:'north'"
					style="height:35px;line-height:30px;background-color: #EFEFEF">
					<security:authorize url="/message/innerMessage/messageReceiveListPage.do">	
						<a href="javaScript:void(0);" id="innerMessage-btn-list-msgReceiveList" class="easyui-linkbutton" data-options="plain:false,iconCls:'icon-extend-recvMsg'">已接收短信</a>
					</security:authorize>
					<security:authorize url="/message/innerMessage/messageSendListPage.do">	
						<a href="javaScript:void(0);" id="innerMessage-btn-list-msgSendList" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-extend-sendMsg'">已发送短信</a>
					</security:authorize>
				</div>
				<div data-options="region:'center'">
					<div id="innerMessage-panel-list-messageListPanel" class="esyui-panel" data-options="border:false"></div>
				</div>		
			</div>
		</div>
		<script type="text/javascript">	
			$(document).ready(function(){		
				$("#innerMessage-panel-list-messageListPanel").panel({
						fit:true,
						title:'',
					    href:'message/innerMessage/messageReceiveListPage.do',
					    cache:false,
					    maximized:true
				});
				$("#innerMessage-btn-list-msgSendList").click(function(){
					var url="message/innerMessage/messageSendListPage.do";
					$("#innerMessage-panel-list-messageListPanel").panel({
						fit:true,
						title:'',
					    href:url,
					    cache:false,
					    maximized:true
					});
					$("#innerMessage-btn-list-msgSendList").linkbutton({plain:false});
					$("#innerMessage-btn-list-msgReceiveList").linkbutton({plain:true});
				});
				$("#innerMessage-btn-list-msgReceiveList").click(function(){	
					var url="message/innerMessage/messageReceiveListPage.do";				
					$("#innerMessage-panel-list-messageListPanel").panel({  
						title:'',
					    href:url,
					    cache:false,
					    maximized:true
					});
					$("#innerMessage-btn-list-msgSendList").linkbutton({plain:true});
					$("#innerMessage-btn-list-msgReceiveList").linkbutton({plain:false});
				});

			});
		</script>
	</body>
</html>
