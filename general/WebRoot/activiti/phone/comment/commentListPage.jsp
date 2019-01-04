<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
    <title>审批记录(手机)</title>
    <%@include file="/phone/common/include.jsp"%>
</head>
<body>
<div data-role="page" id="attach">
    <form id="activiti-form-commentListPage">
        <div data-role="header" data-position="fixed" data-tap-toggle="false">
            <h1>审批记录[${param.processid }]</h1>
            <a href="#main" onclick="javascript:void(0);" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a>
        </div>
        <c:choose>
            <c:when test="${empty process}">
                <h2 style="text-align: center; color: #f00;">该工作不存在</h2>
            </c:when>
            <c:otherwise>
                <c:choose>
                    <c:when test="${empty comments}">
                        <h2 style="text-align: center; color: #f00;">该工作无流转信息</h2>
                    </c:when>
                    <c:otherwise>
                        <ul data-role="listview" data-inset="false" id="activiti-list-commentListPage" class="ui-listview">
                            <c:forEach items="${comments }" var="comment" varStatus="status">
                                <c:choose>
                                    <c:when test="${empty comment.taskid and comment.taskkey eq 'process_end'}">
                                        <li style="padding: 5px;">
                                            <span><c:out value="${comment.taskname}"/></span>
                                            <c:if test="${not empty comment.begintime }">
                                                <p>开始于：<c:out value="${comment.begintime }"/></p>
                                            </c:if>
                                            <c:if test="${not empty comment.endtime }">
                                                <p>结束于：<c:out value="${comment.endtime }"/></p>
                                            </c:if>
                                        </li>
                                    </c:when>
                                    <c:when test="${comment.agree eq '1' and not empty comment.endtime}">
                                        <li style="padding: 5px;">
                                            <span><c:out value="${comment.assigneename}"/> ： <c:out value="${comment.taskname}"/></span>
                                            <c:if test="${not empty comment.begintime }">
                                                <p>开始于：<c:out value="${comment.begintime }"/></p>
                                            </c:if>
                                            <c:if test="${not empty comment.endtime }">
                                                <p>结束于：<c:out value="${comment.endtime }"/></p>
                                            </c:if>
                                            <div style="font-size: 12px; white-space: normal; word-wrap:break-word; word-break:break-all;">同　意：<c:out value="${comment.comment }"/></div>
                                        </li>
                                    </c:when>
                                    <c:when test="${comment.agree eq '0' and not empty comment.endtime}">
                                        <li style="padding: 5px;">
                                            <span><c:out value="${comment.assigneename}"/> ： <c:out value="${comment.taskname}"/></span>
                                            <c:if test="${not empty comment.begintime }">
                                                <p>开始于：<c:out value="${comment.begintime }"/></p>
                                            </c:if>
                                            <c:if test="${not empty comment.endtime }">
                                                <p>结束于：<c:out value="${comment.endtime }"/></p>
                                            </c:if>
                                            <div style="color: #ff0000; font-size: 12px; white-space: normal; word-wrap:break-word; word-break:break-all;">不同意：<c:out value="${comment.comment }"/></div>
                                        </li>
                                    </c:when>
                                </c:choose>
                            </c:forEach>

                        </ul>
                    </c:otherwise>
                </c:choose>
            </c:otherwise>
        </c:choose>
    </form>
</div>
</body>
</html>
