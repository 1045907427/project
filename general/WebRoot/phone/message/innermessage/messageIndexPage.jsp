<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>我的短消息</title>
    <%@include file="../../common/include.jsp"%>
    <meta charset="utf-8">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/phone/js/jqm/jquery.mobile-1.4.5.min.css">
</head>
<body>

<div data-role="page" id="message-email-homepage">
	<div data-role="header" data-position="inline">
		<h1>我的短消息</h1>
	</div>
    <div data-role="content">    	
	    <ul data-role="listview" data-inset="true">
	    	<li><a href="phone/message/showInnerMessageSendPage.do" rel="external">发消息</a></li>
	      	<li><a href="phone/message/showInnerMessageReceiveListPage.do?viewflag=1" rel="external">收信箱<span class="ui-li-count">${unReadMsgCount }</span></a></li>
	      	<li><a href="phone/message/showInnerMessageSendListPage.do" rel="external">发信箱</a></li>
	      	<%--<li><a href="phone/message/showInnerMessageDropListPage.do">垃圾箱<span class="ui-li-count"><c:choose><c:when test="${emailItemCountMap.dropcount!=null }">${emailItemCountMap.dropcount }</c:when><c:otherwise>0</c:otherwise></c:choose></span></a></li> --%>
	    </ul>
    </div>
</div>


</body>
</html>
