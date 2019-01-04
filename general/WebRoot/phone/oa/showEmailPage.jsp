<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
  <head>
  	<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0"/>
    <title>邮件查看</title>
    <style type="text/css">
    	body{margin:0;padding:0;font-size:14px;line-height:18px;}
    	.c{border-bottom:1px solid #dfdfdf;background:#efefef;padding:3px;}
    	.c div{clear:both;}
    	.c div label{width:60px;float:left;text-align:right;}
    </style>
  </head>
  
  <body>
    <div class="c">
    	<div><label>主题：</label>${content.title }</div>
    	<div><label>发件人：</label>${content.addusername }</div>
    	<div><label>时间：</label>${date }</div>
    </div>
    <div style="padding:5px;">${content.content }</div>
  </body>
</html>
