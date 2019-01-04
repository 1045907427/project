<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>发送邮件详情页面</title>
    <%@include file="../../common/include.jsp"%>
    <meta charset="utf-8">
</head>
<body>
<div data-role="page" id="message-email-sendDetailPage">
<style type="text/css">
	.bluebox{
		background-color:#99CCFF;
		border-radius: 4px;
		font-family: 'Microsoft Yahei';
		text-shadow:none;
		font-size:0.9em;
		padding:0.1em;
		cursor:pointer;
	}
	.aline{
		height:1px;
		width:100%;
		backgroud-color:#fff;
	}
</style>
	<div data-role="header" data-position="fixed">		
  		<a data-rel="back" data-role="button" data-icon="back" style="box-shadow:none;border: 0px; background: #E9E9E9;">返回</a>
		<h2>邮件详情</h2>		
	</div>
    <div data-role="content">   
	   <div style="text-align:center;">${emailContent.title }</div>
       <div>
           	发件人：${emailContent.addusername }
       </div>
	   <hr />
       <div>
           	时间：${date }
       </div>
	   <hr />
       <div>
       		<div style="float:left;">收件人：</div>
			<div id="message-email-recvusername" style="float:left;">
				<c:forEach items="${recvuserList }" var="item" begin="0" end ="2" step="1">
					<div class="bluebox" style="float:right;margin-left:0.2em;">${item.name }</div>
				</c:forEach>
			</div>
			<c:if test="${showmore==1 }">
		   	<div style="float:right;">
		   		<div id="message-emailSendDetailPage-showbtn" class="bluebox" style="float:right;width:2.2em;margin-left:0.2em;">更多</div>
		   	</div>
		   	</c:if>
		   	<div style="clear:both;"></div>
	   </div>
	   <div id="message-email-recvuserList" style="display:none;">
	   		<c:forEach items="${recvuserList }" var="item">
				<div class="bluebox" style="float:left;margin-left:0.2em;">${item.name }</div>
			</c:forEach>
			<div style="clear:both;"></div>
	   </div>
		<c:if test="${not empty(emailContent.attach) }">
			<hr/>
			<div>
				<div style="float:left;">附　件：</div>
				<div style="float:left">
					<a href="phone/message/showEmailAttachListPage.do?id=${emailContent.id}">点击查看</a>
				</div>
				<div style="clear:both"></div>
			</div>
		</c:if>
	   <hr/>
       <div style="margin-top:0.5em;overflow-x: auto"> 
           ${emailContent.content }
       </div>
	</div>
	
	<script type="text/javascript">
	$(document).ready(function(){
		//localStorage.setItem("email-detailurl","phone/message/showEmailSendDetailPage.do?id=${emailContent.id}");
		$("#message-emailSendDetailPage-showbtn").on("tap",function(){
			if($("#message-email-recvuserList").is(":hidden")){
				$("#message-email-recvuserList").show();
				$("#message-email-recvusername").hide();
				$("#message-emailSendDetailPage-showbtn").html("隐藏");
			}else{
				$("#message-email-recvuserList").hide();
				$("#message-email-recvusername").show();
				$("#message-emailSendDetailPage-showbtn").html("更多");
			}
		});
	});
	</script>
</div>

</body>
</html>