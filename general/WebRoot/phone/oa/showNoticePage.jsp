<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html>
  <head>
  	<meta name="viewport" content="width=device-width,minimum-scale=1.0,maximum-scale=1.0"/>
    <title>公告通知查看</title>
    <style type="text/css">
    	.title, .user, .date, .time{width:100%;text-align:center;line-height:18px;}
    	.title{font-size:16px;}
    	.user, .date, .time, .intro{font-size:14px;}
    	.time{margin-bottom:3px;}
    	.intro{padding:3px;border:1px solid #dfdfdf;background:#f8f8f8;}
    	.content{font-size:14px;line-height:18px;margin-top:5px;}
    </style>
  </head>
  <body>
    <div>
    	<div class="title">${notice.title }</div>
    	<div class="user">${notice.adddeptname } &nbsp; ${notice.addusername }</div>
    	<div class="date">有效期：${notice.startdate } - ${notice.enddate }</div>
    	<div class="time">${date }</div>
    	<div class="intro">${notice.intro }</div>
    	<div class="content">
    		${notice.content }
    	</div>
    </div>
  </body>
</html>
