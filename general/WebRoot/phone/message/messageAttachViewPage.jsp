<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
  <title>附件查看(手机)</title>
  <%@include file="/phone/common/include.jsp"%>
</head>
<body>
<div data-role="page" id="message-attachview">
  <div data-role="header" data-position="fixed" data-tap-toggle="false">
    <a href="#message-email-emailAttachListPage" data-rel="back" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a>
    <h1>附件明细查看</h1>
  </div>
  <div data-role="content">
      <div class="ui-field-contain" style="margin-bottom: 1.5em;">
        <div>附件名：</div>
        ${attach.oldfilename }
      </div>
      <c:choose>
        <c:when test="${not empty image}">
          <img src="<%=basePath %>${attach.fullpath }" style="max-width: 100%"/>
        </c:when>
        <c:when test="${text}">
          <div><c:out value="${content }"/></div>
          <div style="color: #F00;"><c:out value="${information }"/></div>
        </c:when>
        <c:otherwise>
          <div style="color: #F00;">该文件格式不支持手机查看，请在电脑上下载后查看。</div>
        </c:otherwise>
      </c:choose>
    </div>
  </div>
</body>
</html>
