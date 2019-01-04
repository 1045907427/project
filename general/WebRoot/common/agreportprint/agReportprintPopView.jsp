<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<meta http-equiv="pragma" content="no-cache"/>
	<meta http-equiv="cache-control" content="no-cache"/>
	<meta http-equiv="expires" content="0"/>   
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>打印预览</title>
  	<base href="<%=basePath%>"/>
  	<script type="text/javascript" src="js/jquery-1.8.0.min.js"></script>
  	<script type="text/javascript" src="js/jquery.agreportprint.js"></script>
  </head>
  
  <body>
  <div id='agreportprint-print-div'>
  	<form id="iframeViewFrom" target="iframeView" style="display:none;" method="post">
  		<div id="formDiv"></div>
  		<input type="submit" name="submit" id="submitFormBtn"/>
  	</form>
  	<iframe id="iframeView" name="iframeView" style="display:none" src="about:blank" ></iframe>
  </div>
  <input type="hidden" id="popview_urlparam" value="${urlparam}"/>
  	<script type="text/javascript" defer="defer">
  		$(document).ready(function(){
	  		var urloptions=decodeURI($("#popview_urlparam").val());
	  		
	  		var options=JSON.parse(urloptions);
	  		$.AgReportPrint("viewshow",options);
  		});
   </script>
  </body>
</html>
