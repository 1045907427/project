<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>Handler日志详情</title>
</head>
<body>
<style type="text/css">
	.tb_style{
		border-collapse: collapse;
		border: 1px solid #c9c9c9;
		width:100%;
	}
	.tb_style th,td{
		border: 1px solid #c9c9c9;
		line-height: 25px;
		padding-left: 5px;
	}
</style>
<script src="activiti/js/c.js" type="text/javascript"></script>
<link href="activiti/css/s.css" type="text/css" rel="stylesheet"></link>
<table cellpadding="0" cellspacing="0" class="tb_style">
	<c:forEach items="${params }" var="p" varStatus="status">
		<tr>
			<td>参数${status.index + 1 }</td>
			<td>
				<div id="acitivit-param${status.index + 1 }-handlerLogInfoPage" style="width: 800px;"></div>
				<script type="text/javascript">
					Process('${p}', 'acitivit-param${status.index + 1 }-handlerLogInfoPage');
				</script>
			</td>
		</tr>
	</c:forEach>
	<tr>
		<td>返回值</td>
		<td>
			<c:choose>
				<c:when test="${not empty returnmap}">
					<div id="acitivit-returnobj-handlerLogInfoPage" style="width: 800px;"></div>
					<script type="text/javascript">
						Process('${returnmap }', 'acitivit-returnobj-handlerLogInfoPage');
					</script>
				</c:when>
				<c:otherwise>
					<div id="acitivit-returnobj-handlerLogInfoPage" style="width: 800px;"><c:out value="${returnvalue }"/></div>
				</c:otherwise>
			</c:choose>
		</td>
	</tr>
</table>

<script type="text/javascript">
	<!--

	-->
</script>

</body>
</html>