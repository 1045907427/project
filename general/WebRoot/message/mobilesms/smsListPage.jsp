<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>手机短信管理</title>    
		<%@include file="/include.jsp" %>
	  	<script type="text/javascript" src="js/linq.min.js"></script> 	
		<link rel="stylesheet" href="css/icon-extend.css" type="text/css"></link>
	</head>

	<body>
		<div class="easyui-panel"  fit="true" >
			<div class="easyui-layout" fit="true">
				<div title="" data-options="region:'north'"
					style="height:35px;line-height:30px;background-color: #EFEFEF">
					<security:authorize url="/message/mobileSms/smsSendListPage.do">	
						<a href="javaScript:void(0);" id="mobileSms-btn-list-smsSendList" class="easyui-linkbutton" state="1" data-options="plain:false,iconCls:'icon-extend-sendMsg'">短信发送管理</a>
					</security:authorize>					
					<security:authorize url="/message/mobileSms/smsReceiveListPage.do">	
						<a href="javaScript:void(0);" id="mobileSms-btn-list-smsReceiveList" class="easyui-linkbutton" state="0" data-options="plain:true,iconCls:'icon-extend-recvMsg'">已接收短信查询</a>
					</security:authorize>
				</div>
				<div data-options="region:'center'">
					<div id="mobileSms-panel-list-mobileSmsPanel" class="esyui-panel" data-options="border:false"></div>
				</div>		
			</div>
		</div>
		<script type="text/javascript">	
			var msgSmsListPage_reClickPage=function(){
				if($("#mobileSms-btn-list-smsSendList").attr("state")==1){
					$("#mobileSms-btn-list-smsSendList").click();
				}else{
					$("mobileSms-btn-list-smsReceiveList").click();
				}
			}
			$(document).ready(function(){			
				$("#mobileSms-panel-list-mobileSmsPanel").panel({
					fit:true,
					title:'',
				    href:'message/mobileSms/smsSendListPage.do',
				    cache:false,
				    maximized:true
				});	
				
				$("#mobileSms-btn-list-smsSendList").click(function(){
					$(this).attr("state","1");
					$("#mobileSms-btn-list-smsReceiveList").attr("state","0");
					var url="message/mobileSms/smsSendListPage.do";
					$("#mobileSms-panel-list-mobileSmsPanel").panel({
						fit:true,
						title:'',
					    href:url,
					    cache:false,
					    maximized:true
					});
					$("#mobileSms-btn-list-smsSendList").linkbutton({plain:false});
					$("#mobileSms-btn-list-smsReceiveList").linkbutton({plain:true});
				});
				$("#mobileSms-btn-list-smsReceiveList").click(function(){	
					$(this).attr("state","1");
					$("#mobileSms-btn-list-smsSendList").attr("state","0");
					var url="message/mobileSms/smsReceiveListPage.do";				
					$("#mobileSms-panel-list-mobileSmsPanel").panel({  
						title:'',
					    href:url,
					    cache:false,
					    maximized:true
					});
					$("#mobileSms-btn-list-smsSendList").linkbutton({plain:true});
					$("#mobileSms-btn-list-smsReceiveList").linkbutton({plain:false});
				});
			});
		</script>
	</body>
</html>