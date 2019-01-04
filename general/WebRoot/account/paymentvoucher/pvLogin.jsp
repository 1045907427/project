<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>管理系统-登录页面</title>
	<base id="basePath" href="<%=basePath%>"/>
 	<link rel="shortcut icon" type="image/x-icon" href="favicon.ico" />
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">  
	<script type="text/javascript" src="js/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="js/jquery.form.js"></script>
	<style type="text/css">
*{ font-size:12px; color:#000;}
body{ background: url(image/login2/bg.png) repeat-x;  background-color: #FFFFFF;}
.main{ background:url(image/login2/center.jpg) no-repeat; width:835px; height:409px; margin: 100px auto 0;
    position: relative; }
.box{ left: 205px;
    position: relative;
    text-align: left;
    top: 338px;}
.user {
    display: block;
    float: left;
    padding-top: 2px;
    width: 204px;
}
.pwd {
    display: block;
    float: left;
    padding-left: 25px;
    padding-top: 2px;
    width: 175px;
}
input.text {
    background-color:transparent;
    border: medium none;
    height: 22px;
    line-height: 22px;
    padding: 0 3px;
    width: 128px;
	
}
input.submit {
    background:url(image/login2/btn.png) left top no-repeat;
    border: 0 none;
    cursor: pointer;
    height: 26px;
    width: 57px;
}
input.submit:hover {
    background-position: left -26px;
}
.right {
    float: left;
    left: 20px;
    width: 57px;
}
select {
    height: 22px;
    width: 140px;
}
.msg {
    line-height: 25px;
	left: 205px;
    position: relative;
    width:409px;
    top: 380px;
	text-align:center;
}
</style>
</head>
<body>
	<form action="checkPwd.do" method="post" id="form1">
		<div class="main">
			<div class="box">
				<div class="user">
					<input id="username" class="text" type="text" value=""
						maxlength="20" name="username">
				</div>
				<div class="pwd">
					<input id="password" class="text" type="password" value=""
						name="password">
				</div>
				<div class="right">
					<input id="Loginbutton" class="submit" type="submit" value=""
						title="登录">
				</div>

			</div>
			<div class="msg">
				<span id="tip" class="warn"></span>
			</div>
		</div>
	</form>
</body>
	<script type="text/javascript">
		$(function(){
			$("#reset").click(function(){
				$(".error").hide();
			});
			$("#Loginbutton").click(function(){
				if($("#username").val()==""){
					$("#tip").html("用户名不能为空！");
					$(".error").show();
					return false;
				}
				if($("#password").val()==""){
					$("#tip").html("密码不能为空！");
					$(".error").show();
					return false;
				}
			});
			$("#form1").ajaxSend(function(){
				$("#tip").html("系统登录中...");
			});
			$("#form1").ajaxForm({
				success:function(data){
					var json = $.parseJSON(data);
					if(json.flag){
						$("#tip").html("登录成功，页面跳转中...");
						$(".error").show();
						window.location.href="<%=path%>/paymentVoucherMain.do";
						
					}else{
						if(json.msg!=null && json.msg!=""){
							$("#tip").html(json.msg);
						}else{
							$("#tip").html("用户名或密码错误...");
						}
						$(".error").show();
						$("#password").attr("value","");
					}
				}
			});
		});
	</script>
</html>
