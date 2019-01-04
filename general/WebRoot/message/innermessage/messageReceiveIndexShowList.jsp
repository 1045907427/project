<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    
    <title>首页显示</title>
	
  <style type="text/css">
	a.aa{text-decoration:none;font-size:12px;}
	 a:hover.aa{ font-weight:bold;}
	</style>
  </head>
  
  <body>

  	<div style="margin:5px;">
	  	<c:if test="${msgList!=null}">
			<c:forEach var="item" items="${msgList}">
				<c:if test="${item!=null && item.msgContent!=null}">
					<div style=" line-height:15px;">
					<table width="100%" style="border-bottom:1px dashed #e0e0e0" >
						<tr>
							<td style="width:80%;overflow: hidden; ">
								<span style=" font-weight: bold;">&bull;&nbsp;</span>
								<a href="javascript:message_showMessageDetail('${item.msgid }');" class="aa" style=" ">
								
								<c:choose> 
								     <c:when test="${fn:length(item.msgContent.title) > 40}"> 
								      	<c:out value="${fn:substring(item.msgContent.title, 0, 40)}..." /> 
								     </c:when> 
								     <c:otherwise> 
								      	<c:out value="${item.msgContent.title}" /> 
								     </c:otherwise>
							    </c:choose>
								</a>
							</td>
							<td style="width:20%"> (<c:if test="${not empty(item.sendtime)}"><fmt:formatDate  value="${item.sendtime }" pattern="yyyy-MM-dd" /></c:if>)</td>
						</tr>
					</table>
					</div>
				</c:if>
			</c:forEach>
		</c:if>
	</div>
	<script type="text/javascript">
	
	function message_showMessageDetail(id){
		if(id!=null && $.trim(id)!=""){
			var url="message/innerMessage/messageDetailPage.do?id="+$.trim(id);
			addOrUpdateTab(url,'查看内部短消息');
		}
	}
	</script>
  </body>
</html>
