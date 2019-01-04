<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="struts" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>查看工作</title>
    <%@include file="/phone/common/include.jsp" %>
    <script type="text/javascript">

        if(true) {

            <c:if test="${valid }">
                var url = '';
                <c:choose>
                    <c:when test="${not empty definition.formkey }">
                        url = 'act/phone/workFormPage.do';
                    </c:when>
                    <c:otherwise>
                        url = '${definition.businessurl }';
                    </c:otherwise>
                </c:choose>

                if(url.indexOf('?') < 0) {
                    url = url + '?to=phone&type=view&id=${process.businessid }&processid=${process.id }';
                } else {
                    url = url + '&to=phone&type=view&id=${process.businessid }&processid=${process.id }';
                }

                location.href = url;

            </c:if>
        }

    </script>
</head>
<body>
    <div data-role="page" id="activiti-main-workViewPage">
        <div data-role="header" data-position="fixed" data-tap-toggle="false">
            <h1>查看工作[${process.id }]</h1>
            <%-- <a href="javascript:backPrev();" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a> --%>
            <a href="#" data-rel="back" data-role="button" data-icon="back" style="box-shadow: none; border: 0px; background: #E9E9E9;">返回</a>
        </div>
        <c:choose>
            <c:when test="${valid }">
                <div data-role="content">
                    <label id="activiti-msg-workViewPage">跳转中...</label>
                </div>
            </c:when>
            <c:otherwise>
                <div data-role="content">
                    <label id="activiti-msg-workViewPage">该工作不存在，可能已被删除。</label>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>
