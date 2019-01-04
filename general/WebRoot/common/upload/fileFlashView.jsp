<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	String easyuiThemeName = "default";
	Cookie cookies[] = request.getCookies();
	if (cookies != null && cookies.length > 0) {
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("easyuiThemeName")) {
				easyuiThemeName = cookie.getValue();
				break;
			}
		}
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title></title>
	<base href="<%=basePath%>"></base>
    <script type="text/javascript" src="js/flexpager/jquery.min.js"></script>
    <script type="text/javascript" src="js/flexpager/flexpaper.js"></script>
    <script type="text/javascript" src="js/flexpager/flexpaper_handlers.js"></script>
</head>
<body>
	<div id="documentViewer" class="flexpaper_viewer" style="width:100%;height:800px;"></div>
	<script type="text/javascript">
	<c:if test="${! empty url}">
    $('#documentViewer').FlexPaperViewer(
            { config : {
                SWFFile : '${url}',
                Scale : 1.0,
                ZoomTransition : 'easeOut',
                ZoomTime : 0.5,
                ZoomInterval : 0.2,
                FitPageOnLoad : true,
                FitWidthOnLoad : true,
                FullScreenAsMaxWindow : true,
                ProgressiveLoading : false,
                MinZoomSize : 0.2,
                MaxZoomSize : 1,
                SearchMatchAll : false,
                InitViewMode : 'Portrait',
                RenderingOrder : 'flash',
                StartAtPage : '',

                ViewModeToolsVisible : true,
                ZoomToolsVisible : true,
                NavToolsVisible : true,
                CursorToolsVisible : true,
                SearchToolsVisible : true,
                WMode : 'window',
                localeChain: 'zh_CN'
            }}
    );
    </c:if>
</script>
</body>
</html>