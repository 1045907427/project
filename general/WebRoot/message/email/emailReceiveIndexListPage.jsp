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
				<c:if test="${item.emailContent != null}">
					<div style=" line-height:15px;">
						<table width="100%" style="border-bottom:1px dashed #e0e0e0" >
							<tr>
								<td style="width:70%"><span style=" font-weight: bold;">&bull;&nbsp;</span><a href="javascript:email_showEmailDetail('${item.emailContent.id }');" class="aa" style=" <c:if test='${item.emailContent.importantflag ==1}'>color:#f00;</c:if><c:if test='${item.emailContent.importantflag ==2}'>color:#f00;font-weighgt:bold;</c:if>">${item.emailContent.title }</a></td>
								<td style="width:10%"><c:if test="${item.viewflag=='1'}"><span class="img-extend-emailnew">&nbsp;</span></c:if>&nbsp;</td>
								<td style="width:20%"> (<c:if test="${not empty(item.recvtime)}"><fmt:formatDate  value="${item.recvtime }" pattern="yyyy-MM-dd" /></c:if>)</td>
							</tr>
						</table>
					</div>
				</c:if>
			</c:forEach>
		</c:if>
	</div>
	<script type="text/javascript">	
		function email_showEmailDetail(emailid){
			if(emailid!=null && $.trim(emailid)!=""){
				var url="message/email/emailDetailPage.do?id="+emailid+"&showoper=1";
				addTab(url,'邮件详情查看');
			}
		}
	</script>
  </body>
</html>
