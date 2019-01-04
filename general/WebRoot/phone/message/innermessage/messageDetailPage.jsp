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
<div data-role="page" id="message-innermessage-innermessageSendDetailPage">
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
		<c:choose>
			<c:when test="${param.backlistpagetip=='true' }">
				<a href="phone/message/showInnerMessageReceiveListPage.do?viewflag=0" rel="external" data-role="button" data-icon="back" style="box-shadow:none;border: 0px; background: #E9E9E9;">返回</a>
			</c:when>
			<c:otherwise>
				<a href="#" data-rel="back" data-role="button" data-icon="back" style="box-shadow:none;border: 0px; background: #E9E9E9;">返回</a>
			</c:otherwise>
		</c:choose>  		
		<h2>短消息详情</h2>		
	</div>
    <div data-role="content">   
       <div>
           	发信人：${msgContent.addusername }
       </div>
	   <hr />
       <div>
           	时间：${date }
       </div>
	   <hr />
       <div>
       		<div style="float:left;">收信人：</div>
			<div id="message-innermessage-recvusername" style="float:left;">
				<c:forEach items="${recvuserList }" var="item" begin="0" end ="2" step="1">
					<div class="bluebox" style="float:right;margin-left:0.2em;">${item.name }</div>
				</c:forEach>
			</div>
			<c:if test="${showmore==1 }">
			   	<div style="float:right;">
			   		<div id="message-innermessageSendDetailPage-showbtn" class="bluebox" style="float:right;width:2.2em;margin-left:0.2em;">更多</div>
			   	</div>
		   	</c:if>
		   	<div style="clear:both;"></div>
	   </div>
	   <div id="message-innermessage-recvuserList" style="display:none;">
	   		<c:forEach items="${recvuserList }" var="item">
				<div class="bluebox" style="float:left;margin-left:0.2em;">${item.name }</div>
			</c:forEach>
			<div style="clear:both;"></div>
	   </div>
	   <hr/>
	   <c:if test="${not empty(msgContent.url) and (param.ismsgphoneurlshow=='1' or param.ismsgphoneurlshow=='true' or empty(param.ismsgphoneurlshow)) and (msgContent.ismsgphoneurlshow=='1') }">
		   <div>
		   		<a href="${msgContent.url }" rel="external">
		   			<c:choose>
		   				<c:when test="${msgContent.msgtype == 2 }">查看公告详情</c:when>
		   				<c:when test="${msgContent.msgtype == 3 }">查看邮件详情</c:when>
		   				<c:when test="${msgContent.msgtype == 4 }">查看工作流详情</c:when>
		   				<c:when test="${msgContent.msgtype == 5 }">查看业务预警详情</c:when>
		   				<c:when test="${msgContent.msgtype == 6 }">查看单据详情</c:when>
		   				<c:otherwise>查看详情</c:otherwise>
		   			</c:choose>
		   		</a>
		   </div>
		   <hr/>
	   </c:if>
       <div style="margin-top:0.5em;"> 
           ${msgContent.content }
       </div>
	</div>
	
	<script type="text/javascript">
	$(document).ready(function(){
		$("#message-innermessageSendDetailPage-showbtn").on("tap",function(){
			if($("#message-innermessage-recvuserList").is(":hidden")){
				$("#message-innermessage-recvuserList").show();
				$("#message-innermessage-recvusername").hide();
				$("#message-innermessageSendDetailPage-showbtn").html("隐藏");
			}else{
				$("#message-innermessage-recvuserList").hide();
				$("#message-innermessage-recvusername").show();
				$("#message-innermessageSendDetailPage-showbtn").html("更多");
			}
		});
	});
	</script>
</div>

</body>
</html>