<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>我的工作-工作统计</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
	<div class="easyui-panel" data-options="fit:true,border:false" title="工作统计">
		<c:choose>
			<c:when test="${empty list}">
				<ul style="margin-left: 20px;">
					<li><div style="margin: 0px auto; "><font color="red" style="font-size: 13pt; font-weight: bold;">不存在相对应的工作！</font></div></li>
				</ul>
			</c:when>
			<c:otherwise>
				<ul style="margin-left: 20px;">
					<c:forEach items="${list }" var="stat">
						<li>
							<div style="margin-top: 10px;">
								<div style="font-size: 13pt; font-weight: bold;"><c:out value="${stat.name }"></c:out></div>
								<div style="width: 400px; height: 250px;">
									<iframe width="100%" height="100%" src="act/workPiePage.do?definitionkey=${stat.key }&type6=${stat.type6 }&type7=${stat.type7 }&type8=${stat.type8 }&type9=${stat.type9 }&type12=${stat.type12 }&" frameborder="0"></iframe>
								</div>
								<br/>
							</div>
						</li>
					</c:forEach>
				</ul>
			</c:otherwise>
		</c:choose>
	</div>
  </body>
</html>
