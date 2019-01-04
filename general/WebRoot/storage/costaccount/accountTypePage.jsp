<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<html>
<head>
    <title>异常成本报表</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<div id="report-toolbar-costAccountDetailPage" style="text-align: center;padding-top: 20px;">
    成本结算方式:
    <select id="report-accounttype-costAccountDetailPage">
        <option value="2" selected>全月一次加权平均法</option>
    </select>
</div>
<script type="text/javascript">

</script>
</body>
</html>
