<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<head>
    <title>上传图片 </title>
</head>

<body>
	<div id="wrapper">
		<div class="page-body">
			<div id="post-container" class="container">
			    <div class="page-container">
					<p>${param.description }</p>
					<div id="uploader" class="wu-example">
					    <div class="queueList">
					        <div id="dndArea" class="placeholder">
					            <div id="filePicker"></div>
					        </div>
					    </div>
					    <div class="statusBar" style="display:none;">
					        <div class="progress">
					            <span class="text">0%</span>
					            <span class="percentage"></span>
					        </div><div class="info"></div>
					        <div class="btns">
					            <div id="filePicker2"></div><div class="uploadBtn">开始上传</div>
					        </div>
					    </div>
					</div>
			    </div>
			</div>
		</div> 
	</div>
    <link rel="stylesheet" type="text/css" href="js/webuploader/css/webuploader.css">
    <link rel="stylesheet" type="text/css" href="js/webuploader/css/webuploaderdemo.css">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script type="text/javascript" src="js/webuploader/html5shiv.js" charset="UTF-8"></script>
    <script type="text/javascript" src="js/webuploader/respond.min.js" charset="UTF-8"></script>
    <![endif]-->
    <script type="text/javascript" src="js/webuploader/webuploader.js" charset="UTF-8"></script>
    <script type="text/javascript" src="js/webuploader/webuploaderdemo.js" charset="UTF-8"></script>
</body>
</html>
