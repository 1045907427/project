<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>管理系统-登录页面</title>
	<link rel="shortcut icon" type="image/x-icon" href="image/default2/logo/logo.png" />
    <script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<script type="text/javascript" src="js/jquery-1.8.0.min.js"></script>
	<script type="text/javascript" src="js/jquery.form.js"></script>
	<style type="text/css">
		.main{ background:url(image/default2/logo/login_bg.png) bottom no-repeat;width:100%;margin: auto;background-size:100% 100%;}
		.logininput_user{width: 220px;height: 30px;text-align: left;border: 0px;padding-left: 20px;outline:none;border-bottom:solid 1px #575757 }
		.logininput_pwd{width: 220px;height: 30px;text-align: left;border: 0px;padding-left: 20px;outline:none;border-bottom:solid 1px #575757 }
		.loginsubmit{background:url(image/default2/login_submit.png) center no-repeat;width: 231px;height: 31px;text-align: center;border: 0px;cursor: pointer;outline:none;}
		.loginsubmit:HOVER{background:url(image/default2/login_submit1.png) center no-repeat;width: 231px;height: 31px;text-align: center;border: 0px;outline:none;}
		.box{text-align: center; }
		.box .box-input{padding: 20px 0px 10px 0px;text-align: center;}
		.loginbottom{border-top:1px solid rgb(170,170,170);width:85%;bottom: 5px;position: absolute;left: 7.5%;text-align: center;font-size: 12px;color: rgb(60,60,60);padding: 5px 0px 10px 0px;letter-spacing: 3px;}
		.login-memory{background:url(image/default2/login-memory.png) 1px no-repeat;padding: 0px 10px 0px 15px;text-decoration: none;color: rgb(0,0,0);vertical-align: middle;margin-top: 1px;}
		.login-memory1{background:url(image/default2/login-memory1.png) 1px no-repeat;padding: 0px 10px 0px 15px;text-decoration: none;color: rgb(0,0,0);vertical-align: middle;margin-top: 1px;}
		.box1{
			/*border: solid 10px #333;*/
			margin: 0 auto;
			-moz-border-radius: 10px;      /* Gecko browsers */
			-webkit-border-radius: 10px;   /* Webkit browsers */
			border-radius-:10px;            /* W3C syntax */
			width: 342px;
			height: 400px;
			-webkit-box-shadow:0 0 10px rgba(0, 204, 204, .5);
			-moz-box-shadow:0 0 10px rgba(0, 204, 204, .5);
			box-shadow:0 0 10px rgba(34, 37, 47, 0.5);
		}
		.box1-top{
			display: block;
			background-image:url(image/default2/logo/loginbox_top.png) ;
			width: 100%;
			height: 61px;
			line-height: 61px;
			border-top-left-radius :10px;
			border-top-right-radius :10px;
			background-size:cover;
		}
		.box1-bottom{
			display: block;
			width: 100%;
			height: 339px;
			background-color:#fff;
			opacity: 0.8;
			border-bottom-left-radius :10px;
			border-bottom-right-radius :10px;
		}
	</style>
</head>
<body style="margin: 0px;" >
<div class="main">
	<div class="box">
		<div class="box1">
			<div class="box1-top">
				<div style="float: left;margin-left: 10%;vertical-align:middle;margin-top: 13px">
					<img src="image/default2/logo/loginbox-top-logo.png" alt=""/>
				</div>
				<div>
					<span style="color: #fff;font-size: 18px">管理系统</span>
				</div>
			</div>
			<div class="box1-bottom">
				<div>
					<div class="box-input" style="padding-top: 40px">
						<input id="username"  class="logininput_user" type="text" maxlength="20"  placeholder="请在此输入用户名" autocomplete="off" value="${username }"/>
					</div>
					<div class="box-input">
						<input id="password" class="logininput_pwd" type="password"  placeholder="请在此输入密码" autocomplete="off" value="${password }"/>
					</div>
                    <div class="box-input" <c:if test="${IsOpenBusDate=='0'}">style="display: none"</c:if> >
                        <%--<input id="busdate" class="logininput_user Wdate" type="text" name="busdate"  placeholder="操作日期" autocomplete="off" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${today }"/>--%>
                    </div>
					<div class="box-input" style="margin: -5px 0px 2px 0px;">
						<div style="font-size: 12px;color: rgb(0,0,0);margin-left: 140px;width: 230px;display:inline-block;">
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
</div>
</body>
<script type="text/javascript">
	function adjust(){
		var height = $(this).height();
		var width = $(this).width();
		$(".main").height(height);
		$(".main").width(width);
		$(".box").css({"padding-top":height*0.2+"px"});
		$(".box").width(width);
//		$(".box1").width(width*0.23);
//		$(".box1").height(height*0.55);
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
