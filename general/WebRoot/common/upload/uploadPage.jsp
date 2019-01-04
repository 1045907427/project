<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>
    <%@include file="/include.jsp" %>
</head>
<body style="width:100%;height:100%;">
	<div class="fileList">
			
	</div>
	<div class="fileUpload">
		<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="508" height="310" id="uploadSWF">
		    <param name="movie" value="common/upload/Flex.swf?scheme=${scheme}&serverUrl=${serverUrl}&port=${port}&contextPath=${contextPath}" />
		    <param name="quality" value="high" />
		    <param name="bgcolor" value="#ffffff" />
		    <param name="allowScriptAccess" value="*" />
		    <param name="allowFullScreen" value="true" />
			<param name="wmode" value="transparent" />
		    <!--[if !IE]>-->
		    <object type="application/x-shockwave-flash" data="common/upload/Flex.swf?scheme=${scheme}&serverUrl=${serverUrl}&port=${port}&contextPath=${contextPath}" width="508" height="310">
		        <param name="quality" value="high" />
		        <param name="bgcolor" value="#ffffff" />
		        <param name="allowScriptAccess" value="*" />
		        <param name="allowFullScreen" value="true" />
				<param name="wmode" value="transparent" />
		    <!--<![endif]-->
		    <!--[if !IE]>-->
		    </object>
		    <!--<![endif]-->
		</object>
	</div>
</body>
</html>