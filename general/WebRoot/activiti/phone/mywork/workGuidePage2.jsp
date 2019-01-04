<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>新建工作向导</title>
    <%@include file="/phone/common/include.jsp" %>
    <script type="text/javascript">

        $(function() {

            // 标题中输入含有回车换行字符时，替换成空字符
            $('#activiti-title-workGuidePage2').on('change', function() {

                var v = $(this).val();
                v = v.replace(/\r\n/g, ' ').replace(/\r/g, ' ').replace(/\n/g, ' ');
                $(this).val(v);
            });

            $('#activiti-new-workGuidePage2').off().on('click', function(e) {

                $('#activiti-form-workGuidePage2').submit();
            });
        });

    </script>
</head>
<body>

<div data-role="page" id="activiti-main-workGuidePage2">
    <div data-role="header" data-position="fixed" data-tap-toggle="false">
        <h1>新建工作</h1>
        <a href="javascript:location.href='act/phone/workGuidePage.do';" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a>
        <a href="javascript:location.href=location.href;" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-right ui-icon-refresh" style="border: 0px; background: #E9E9E9;">刷新</a>
    </div>
    <form action="act/phone/workHandlePage.do" id="activiti-form-workGuidePage2" method="post" data-ajax="false">
        <input type="hidden" name="definitionkey" value="${param.definitionkey }"/>
        <div data-role="content">
            <label>工作名称:
                <textarea cols="40" rows="18" name="title" id="activiti-title-workGuidePage2" placeholder="请填写工作名称" maxlength="60"><c:out value="${title }" /></textarea>
            </label>
            <a href="javascript:void(0);" class="ui-btn ui-corner-all" id="activiti-new-workGuidePage2">新建</a>
            <!-- <input type="submit" class="ui-btn" value="新建工作"/> -->
        </div>
    </form>
</div>
</body>
</html>
