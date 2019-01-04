<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


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
	  	<c:if test="${dataList!=null}">
			<c:forEach var="item" items="${dataList}">
				<div style=" line-height:15px;">
				<table width="100%" style="border-bottom:1px dashed #e0e0e0" ><tr><td style="width:70%"><span style=" font-weight: bold;">&bull;&nbsp;</span><a href="javascript:notice_showNoticeDetail('${item.id }');" class="aa" style=" <c:if test='${item.istop ==1}'>color:#f00;</c:if><c:if test='${(not empty(item.tcolor)) and item.istop !=1}'>color:${item.tcolor };</c:if>">${item.title }</a></td>
				<td style="width:10%">(${item.readcount })</td><td style="width:20%"> (<c:if test="${not empty(item.publishtime)}"><fmt:formatDate  value="${item.publishtime }" pattern="yyyy-MM-dd" /></c:if>)</td></tr></table>
				</div>
			</c:forEach>
		</c:if>
	</div>
	<script type="text/javascript">
	
	function notice_showNoticeDetail(noticeid){
		if(noticeid!=null && $.trim(noticeid)!=""){
			var url="message/notice/noticeDetailPage.do?noticeid="+noticeid;
			addOrUpdateTab(url,'查看公告通知');
		}
	}
	</script>
  </body>
</html>
