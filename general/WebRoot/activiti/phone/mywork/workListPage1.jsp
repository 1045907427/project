<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>我的工作</title>
    <%@include file="/phone/common/include.jsp"%>
</head>
<body>
    <div data-role="page" id="activiti-main-workListPage1">
        <div data-role="header" data-position="fixed" data-tap-toggle="false">
            <h1>我的工作</h1>
            <a href="javascript:backMain();" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a>
            <%-- <a href="javascript:location.href=location.href;" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-right ui-icon-refresh" style="border: 0px; background: #E9E9E9;">刷新</a> --%>
        </div>
        <div data-role="content">
            <a href="act/phone/workGuidePage.do" class="ui-btn ui-corner-all" rel="external">新建工作</a>
            <a href="act/phone/workListPage2.do" class="ui-btn ui-corner-all" rel="external">办理中</a>
            <a href="act/phone/workQueryPage.do" class="ui-btn ui-corner-all" rel="external">工作查询</a>
        </div>
    </div>
</body>
</html>
