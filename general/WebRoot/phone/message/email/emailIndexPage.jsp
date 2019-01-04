<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>我的邮件</title>
    <%@include file="../../common/include.jsp"%>
    <meta charset="utf-8">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/phone/js/jqm/jquery.mobile-1.4.5.min.css">
</head>
<body>

<div data-role="page" id="message-email-homepage">
	<div data-role="header" data-position="inline">
		<h1>我的邮件</h1>
	</div>
    <div data-role="content">    	
	    <ul data-role="listview" data-inset="true">
	    	<li><a href="phone/message/showEmailSendPage.do" rel="external">写邮件</a></li>
	      	<li><a href="phone/message/showEmailReceiveListPage.do?viewflag=1" rel="external">收件箱<span class="ui-li-count">${unReadEmailCount }</span></a></li>
	      	<li><a href="phone/message/showEmailSendListPage.do" rel="external">发件箱</a></li>
	    </ul>
    </div>
</div>


</body>
</html>
