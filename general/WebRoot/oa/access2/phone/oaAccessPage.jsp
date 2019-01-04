<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="struts"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>特价通路申请单(手机)</title>
    <script type="text/javascript">

        <%--
        var type = '${param.type }';
        var step = '${param.step }';
        var url = '';
        if(type == 'handle') {

            if(step == '01') {
                url = 'oa/hd/access/oaAccessHandlePage1.do?type=${param.type }&to=phone&processid=${param.processid }&taskid=${param.taskid }&id=${param.id }';
            } else if(step == '02') {
                url = 'oa/hd/access/oaAccessHandlePage2.do?type=${param.type }&to=phone&processid=${param.processid }&taskid=${param.taskid }&id=${param.id }';
            } else if(step == '03') {
                url = 'oa/hd/access/oaAccessHandlePage3.do?type=${param.type }&to=phone&processid=${param.processid }&taskid=${param.taskid }&id=${param.id }';
            } else if(step == 99) {
                url = 'oa/hd/access/oaAccessViewPage.do?type=${param.type }&to=phone&processid=${param.processid }&taskid=${param.taskid }&id=${param.id }'
            }
        } else {

            url = 'oa/hd/access/oaAccessViewPage.do?type=${param.type }&to=phone&processid=${param.id }&taskid=${process.taskid }&id=${process.businessid }'
        }

        location.href = url;
        --%>

    </script>
</head>
<body>

</body>
</html>
