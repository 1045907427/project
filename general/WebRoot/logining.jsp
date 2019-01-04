<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
   <%@include file="/include.jsp" %>  
  </head>
  
  <body>
  	<script type="text/javascript">
  		var	url = "index.do";
    	top.location.href=url;
<!--  		if($.browser.mozilla){-->
<!--	     	var	url = "index.do";-->
<!--    		top.location.href=url;-->
<!--		}else{-->
<!--			var	url = "indexing.do";-->
<!--	    	var pwindow = window.open(url,'',"toolbar=no,location=no,directories=no,menubar=no,scrollbars=no,resizable=yes,status=no,top=0,left=0,fullscreen=yes");-->
<!--	    	window.opener=null; -->
<!--			window.open('','_self'); -->
<!--			window.close(); -->
<!--		}-->
    </script>
  </body>
</html>
