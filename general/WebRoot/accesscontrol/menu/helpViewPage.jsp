<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>菜单帮助文档</title>
	<%@include file="/include.jsp" %>
      <script type="text/javascript" src="js/kindeditor/kindeditor-min.js"></script>
      <script type="text/javascript" src="js/kindeditor/keconfig.js"></script>
      <script type="text/javascript" src="js/kindeditor/lang/zh_CN.js"></script>
  </head>
  
  <body>
  <div style="text-align: center;font-size: 20px;font-weight: bold;">
      <c:if test="${title!=null}">
          功能页面：${title}
      </c:if>
  </div>
    <div>
        ${content}
    </div>
  </body>
</html>
