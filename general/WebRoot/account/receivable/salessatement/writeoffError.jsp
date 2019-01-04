<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  </head>
  
  <body>
    <script type="text/javascript">
    	<c:if test="${writeoffPage==true}">
    		$("#account-dialog-writeoff-info").dialog("close");
    	</c:if>
    	<c:if test="${writeoffPage==null}">
    		$("#account-dialog-writeoff").dialog("close");
    	</c:if>
    </script>
  </body>
</html>
