<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>生成SVG</title>
    <script type="text/javascript">

    </script>
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
</head>
<body>
<table cellpadding="0" cellspacing="0" class="tb_style">
    <tr>
        <th>#</th>
        <th>流程标识</th>
        <th>流程版本</th>
        <th>流程名称</th>
        <th>结果</th>
    </tr>
    <c:forEach items="${result }" var="item" varStatus="idx">
        <tr>
            <td><c:out value="${idx.index + 1}"/></td>
            <td><c:out value="${item.definition.unkey }"/></td>
            <td><c:out value="${item.definition.definitionid }"/></td>
            <td><c:out value="${item.definition.name }"/></td>
            <td>
                <c:choose>
                    <c:when test="${item.result eq '0'}"><font color="green">ALREADY EXISTED!</font></c:when>
                </c:choose>
                <c:choose>
                    <c:when test="${item.result eq '1'}"><font color="green">GENERATE SUCCEED.</font></c:when>
                </c:choose>
                <c:choose>
                    <c:when test="${item.result eq '9'}"><font color="red">GENERATE FAILED!</font></c:when>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
