<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>签到管理</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
  	<input type="hidden" id="signin-backid-signinPage" value="${id }" />
    <div class="easyui-panel" data-options="fit:true,border:false">
  		<div class="easyui-layout" data-options="fit:true">
  			<div data-options="region:'center',border:false">
  				<div id="signin-div-signinPage"></div>
  			</div>
  		</div>
  	</div>
  	<div id="signin-dialog-signinPage" closed="true"></div>
    <script type="text/javascript">
    <!--

	$(function(){
	
		$("#signin-div-signinPage").panel({
			href: 'hr/signin/signinViewPage.do?id=${param.id }',
		    cache: false,
		    maximized: true,
		    border: false
		});
		
	});

	-->
    </script>
  </body>
</html>
