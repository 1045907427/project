<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="struts" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>处理工作</title>
    <%@include file="/phone/common/include.jsp" %>
    <script type="text/javascript">

        var include = true;
        if(include) {

            <c:if test="${valid }">
                var url = '';
                <c:choose>
                    <c:when test="${not empty definition.formkey }">
                        url = 'act/phone/workFormPage.do';
                    </c:when>
                    <c:otherwise>
                        <c:choose>
                            <c:when test="${empty task or empty task.businessurl }">
                                url = '${definition.businessurl }';
                            </c:when>
                            <c:otherwise>
                                url = '${task.businessurl }';
                            </c:otherwise>
                        </c:choose>
                    </c:otherwise>
                </c:choose>

                if (url.indexOf('?') < 0) {
                    url = url + '?to=phone&type=handle&processid=${process.id }&id=${process.businessid }&taskid=${process.taskid }';
                } else {
                    url = url + '&to=phone&type=handle&processid=${process.id }&id=${process.businessid }&taskid=${process.taskid }';
                }

                location.href = url;
            </c:if>
        }

    </script>
</head>
<body>
    <div data-role="page" id="activiti-main-workHandlePage">
        <div data-role="header" data-position="fixed" data-tap-toggle="false">
            <h1>处理工作1[${process.id }]</h1>
            <%-- <a href="javascript:backPrev();" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a> --%>
            <a href="#" data-rel="back" data-role="button" data-icon="back" style="box-shadow: none; border: 0px; background: #E9E9E9;">返回</a>
        </div>
        <div data-role="content">
            <label id="activiti-msg-workHandlePage">该工作已经失效！</label>
        </div>
    </div>
</body>
</html>
