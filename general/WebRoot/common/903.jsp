<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>跳转到登录页面</title>
  </head>
  <body>
     <script type="text/javascript">
     	alert("账户在其它地方登录!请注意账户安全!");
     	var	url = "login.do";
    	top.location.href=url;
    </script>
  </body>
</html>
