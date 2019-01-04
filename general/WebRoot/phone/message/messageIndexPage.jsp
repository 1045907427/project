<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>我的消息中心</title>
    <%@include file="../common/include.jsp"%>
    <meta charset="utf-8">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/phone/js/jqm/jquery.mobile-1.4.5.min.css">
    <script type="text/javascript">
        localStorage.setItem('url', '');
    </script>
</head>
<body>

<div data-role="page" id="message-email-homepage">
	<div data-role="header" data-position="inline">
  		<a href="javascript:backMain();" data-role="button" data-icon="back" style="box-shadow:none;border: 0px; background: #E9E9E9;">返回</a>
		<h1>我的消息中心</h1>
	</div>
    <div data-role="content">    	
	    <ul data-role="listview" data-inset="true">
	    	<li data-role="list-divider">我的短消息</li>
	    	<li><a href="phone/message/showInnerMessageSendPage.do" rel="external">发消息</a></li>
	      	<li><a href="phone/message/showInnerMessageReceiveListPage.do?viewflag=1" rel="external">收信箱<c:if test="${unReadMsgCount > 0 }"><font style="font-size:10px;">(未读${unReadMsgCount}条)</font></c:if></a></li>
	      	<li><a href="phone/message/showInnerMessageSendListPage.do" rel="external">发信箱</a></li>
	    	<li data-role="list-divider">我的邮件</li>
	    	<li><a href="phone/message/showEmailSendPage.do" rel="external">写邮件</a></li>
	      	<li><a href="phone/message/showEmailReceiveListPage.do?viewflag=1" rel="external">收件箱<c:if test="${unReadEmailCount > 0 }"><font style="font-size:10px;">(未读${unReadEmailCount}条)</font></c:if></a></li>
	      	<li><a href="phone/message/showEmailSendListPage.do" rel="external">发件箱</a></li>
	      	<%--<li><a href="phone/message/showEmailDropListPage.do">垃圾箱<span class="ui-li-count"><c:choose><c:when test="${emailItemCountMap.dropcount!=null }">${emailItemCountMap.dropcount }</c:when><c:otherwise>0</c:otherwise></c:choose></span></a></li> --%>
	      	<li data-role="list-divider">我的公告栏</li>
	      	<li><a href="phone/message/showNoticeListPage.do?viewflag=1" rel="external">未读公告<span class="ui-li-count">${unReadNoticeCount }</span></a></li>
	      	<li><a href="phone/message/showNoticeListPage.do?viewflag=0" rel="external">已读公告</a></li>
	    </ul>
    </div>
</div>


</body>
</html>
