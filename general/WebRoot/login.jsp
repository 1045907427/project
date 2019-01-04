<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
<title>管理系统-登录页面</title>
    <script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
 <link rel="shortcut icon" type="image/x-icon" href="image/<%=easyuiThemeName%>/logo/logo.png" />
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">  
	<script type="text/javascript" src="js/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="js/jquery.form.js"></script>
	<style type="text/css">
	.main{ background:url(image/<%=easyuiThemeName%>/logo/login_bg.jpg) bottom no-repeat;width:100%;margin: auto;background-size:cover;}
	.logininput_user{background:url(image/<%=easyuiThemeName%>/login_input_user.png)  no-repeat;width: 190px;height: 30px;text-align: left;border: 0px;padding-left: 41px;outline:none;}
    .logininput_date{width: 190px;height: 30px;text-align: left;border: 1px solid #989898;padding-left: 41px;outline:none;}
	.logininput_pwd{background:url(image/<%=easyuiThemeName%>/login_input_pwd.png)  no-repeat;width: 190px;height: 30px;text-align: left;border: 0px;padding-left: 41px;outline:none;}
	.loginsubmit{background:url(image/<%=easyuiThemeName%>/login_submit.png) center no-repeat;width: 231px;height: 31px;text-align: center;border: 0px;cursor: pointer;outline:none;}
	.loginsubmit:HOVER{background:url(image/<%=easyuiThemeName%>/login_submit1.png) center no-repeat;width: 231px;height: 31px;text-align: center;border: 0px;outline:none;}
	.box{text-align: center; }
	.box .box-input{margin: 10px 0px 10px 0px;text-align: center;}
	.loginbottom{border-top:1px solid rgb(170,170,170);width:85%;bottom: 5px;position: absolute;left: 7.5%;text-align: center;font-size: 12px;color: rgb(60,60,60);padding: 5px 0px 10px 0px;letter-spacing: 3px;}
	.login-memory{background:url(image/<%=easyuiThemeName%>/login-memory.png) 1px no-repeat;padding: 0px 10px 0px 15px;text-decoration: none;color: rgb(0,0,0);vertical-align: middle;margin-top: 1px;}
	.login-memory1{background:url(image/<%=easyuiThemeName%>/login-memory1.png) 1px no-repeat;padding: 0px 10px 0px 15px;text-decoration: none;color: rgb(0,0,0);vertical-align: middle;margin-top: 1px;}
	</style>
</head>
<body style="margin: 0px;">
	<div class="main">
		<div class="box">
			<div class="box-input">
				<input id="username" class="logininput_user" type="text" maxlength="20"  placeholder="请在此输入用户名" autocomplete="off" value="${username }"/>
			</div>
			<div class="box-input">
				<input id="password" class="logininput_pwd" type="password"  placeholder="请在此输入密码" autocomplete="off" value="${password }"/>
			</div>
            <%--<div class="box-input" <c:if test="${IsOpenBusDate=='0'}">style="display: none"</c:if> >--%>
                <%--<input id="busdate" class="logininput_date Wdate" type="text" name="busdate"  placeholder="操作日期" autocomplete="off" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${today }"/>--%>
            <%--</div>--%>
			<div class="box-input" style="margin: -5px 0px 30px 0px;">
				<div style="font-size: 12px;color: rgb(0,0,0);margin-left: 180px;width: 230px;display:inline-block;">
					<a id="login-memory" href="javascript:void(0);" <c:if test="${ismemory=='1' }">class="login-memory1"</c:if><c:if test="${ismemory!='1' }">class="login-memory"</c:if>>记住密码</a>
				</div>
			</div>
			<div class="box-input">
				<input type="button" id="Loginbutton" class="loginsubmit"/>
			</div>
			<div class="box-input" style="margin: 16px 0px 20px 0px;">
				<div style="text-align: center;">
					<span id="tip" style="font-size: 12px;color: rgb(255,0,0);padding-left: 2px;"></span>
				</div>
			</div>
		</div>
		<form action="checkPwd.do" method="post" id="form1" autocomplete="off">
			<input type="hidden" id="username-hidden" name="username"/>
			<input type="hidden" id="password-hidden" name="password"/>
            <input type="hidden" id="busdate-hidden" name="busdate"/>
			<input type="hidden" id="ismemory-hidden" name="ismemory" value="${ismemory}"/>
		</form>
	</div>
	<div class="loginbottom" style="font-family:arial;">
        &copy;
	</div>
</body>
	<script type="text/javascript">
		function adjust(){
			var height = $(this).height();
			$(".main").height(height*0.45);
			$(".box").css({"padding-top":height*0.47+"px"});
		}
		window.onload=function(){  
		  window.onresize = adjust;  
		  adjust();  
		}
		$(function(){
			$("#username").select();
			$("#username").focus();
			$("#username").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
		   			$("#password").focus();
		   			$("#password").select();
				}
			});
			$("#password").die("password").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					$("#Loginbutton").trigger("click");
				}
			});
			$("#login-memory").click(function(){
				var classstr = $(this).attr("class");
				if(classstr=="login-memory"){
					$(this).removeClass("login-memory");
					$(this).addClass("login-memory1");
					$("#ismemory-hidden").val("1");
				}else{
					$(this).removeClass("login-memory1");
					$(this).addClass("login-memory");
					$("#ismemory-hidden").val("0");
				}
			});
			$("#Loginbutton").click(function(){
				if($("#username").val()==""){
					$("#tip").html("用户名不能为空！");
					$(".error").show();
					$("#username-hidden").val("");
					$("#password-hidden").val("");
					return false;
				}
				if($("#password").val()==""){
					$("#tip").html("密码不能为空！");
					$(".error").show();
					$("#username-hidden").val("");
					$("#password-hidden").val("");
					return false;
				}
				$("#username-hidden").val($("#username").val());
				$("#password-hidden").val($("#password").val());
                $("#busdate-hidden").val($("#busdate").val());
				$("#form1").submit();
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
						document.getElementById("form1").action = "login_safe";
						document.getElementById("form1").submit();
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
