<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
   <%@include file="/include.jsp" %>  
  </head>
  
  <body>
  	<script type="text/javascript">
  		var x = screen.availWidth;
		var y = screen.availHeight;
		window.moveTo(0, 0);
		window.resizeTo(x, y);
     	var	url = "index.do";
    	top.location.href=url;
    </script>
  </body>
</html>
