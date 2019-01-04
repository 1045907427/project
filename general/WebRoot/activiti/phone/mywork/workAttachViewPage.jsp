<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>附件(手机)</title>
    <%@include file="/phone/common/include.jsp"%>
</head>
<body>
<div data-role="page" id="attachview">
    <script type="text/javascript">
    </script>
    <style type="text/css">
    </style>
    <form id="activiti-form-workAttachViewPage" action="act/phone/addAttach">
        <input type="hidden" name="processid" id="activiti-processid-workAttachViewPage" value="${param.processid }"/>
        <input type="hidden" name="commentid" id="activiti-commentid-workAttachViewPage" value="${param.commentid }"/>
        <div data-role="header" data-position="fixed" data-tap-toggle="false">
            <h1><c:out value="${attach.oldfilename }"/></h1>
            <a href="#attach" data-rel="back" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a>
        </div>
        <div class="ui-corner-all custom-corners">
            <div class="ui-body ui-body-a">
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
    </form>
</div>
</body>
</html>
