<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <title>邮件附件列表</title>
  <%@include file="../../common/include.jsp"%>
  <meta charset="utf-8">

</head>
<body>

<div data-role="page" id="message-email-emailAttachListPage">
  <div data-role="header" data-position="fixed">
    <a href="#" data-role="button" data-rel="back" data-icon="back" style="box-shadow:none;border: 0px; background: #E9E9E9;">返回</a>
    <h2>邮件附件详情</h2>
  </div>
  <div data-role="content">
    <c:if test="${!empty(msg)}">
      <div style="margin: 10px">${msg}</div>
    </c:if>
    <ul data-role="listview" id="message-email-emailAttachListView">
      <c:forEach items="${attachDataList }" var="item">
        <li><a href="phone/message/showMessageAttachViewPage.do?id=${item.id }">${item.oldfilename}</a></li>
      </c:forEach>
    </ul>
  </div>
  <script type="text/javascript">
    function backToDetail(){
      var url = localStorage.getItem('email-detailurl') || '';
      if(url==""){
        //location.href='phone/message/showMessageIndexPage.do';
        $.mobile.changePage('phone/message/showMessageIndexPage.do', {reloadPage : true});
        return true;
      }
      //location.href=url;
      $.mobile.changePage(url, {reloadPage : false});
      return true;
    }
  </script>
</div>
</body>
</html>
