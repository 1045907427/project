<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
    <title>发送邮件详情页面</title>
    <%@include file="../../common/include.jsp"%>
    <meta charset="utf-8">
</head>
<body>
<div data-role="page" id="message-notice-noticeDetailPage">
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
				<a href="phone/message/showNoticeListPage.do?viewflag=0" rel="external" data-role="button" data-icon="back" style="box-shadow:none;border: 0px; background: #E9E9E9;">返回</a>
			</c:when>
			<c:otherwise>
				<a href="#" data-rel="back" data-role="button" data-icon="back" style="box-shadow:none;border: 0px; background: #E9E9E9;">返回</a>
			</c:otherwise>
		</c:choose>
		<h2>公告详情</h2>		
	</div>
    <div data-role="content">   
	   <div style="text-align:center;">${msgNotice.title }</div>
	   <div>
           	发布人：${msgNotice.publishdeptname } ${msgNotice.publishername }
       </div>
       <hr />
       <div>
           	发布时间：<c:if test="${not empty(msgNotice.publishtime)}"><fmt:formatDate  value="${msgNotice.publishtime}" pattern="yyyy-MM-dd HH:mm:ss" /></c:if>
       </div>
       <c:if test="${not empty(msgNotice.startdate) or not empty(msgNotice.enddate) }">
	   <hr />
       <div>
           	有效期：${msgNotice.startdate } - ${msgNotice.enddate }
       </div>
	   </c:if>
		<c:if test="${not empty(msgNotice.attach) }">
			<hr/>
			<div>
				<div style="float:left;">附　件：</div>
				<div style="float:left">
					<a href="phone/message/showNoticeAttachListPage.do?id=${msgNotice.id}">点击查看</a>
				</div>
				<div style="clear:both"></div>
			</div>
		</c:if>
		<hr/>
       <div style="margin-top:0.5em;overflow-x: auto"> 
           ${msgNotice.content }
       </div>
	</div>
</div>

</body>
</html>