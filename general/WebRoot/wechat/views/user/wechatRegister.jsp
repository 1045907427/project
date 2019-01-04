<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%--<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
  <title>用户注册</title>
  <%@include file="/wechat/wechatui/include.jsp" %>
  <link rel="stylesheet" href="wechat/wechatui/af/af.ui.css" type="text/css" id="afui"/>
  <script type="text/javascript" src="wechat/wechatui/af/appframework.ui.js" charset="UTF-8"></script>

  <style type="text/css">

    .ios7 input[type="radio"]:checked:not(.toggle) + label:before, .ios7 input[type="checkbox"]:checked:not(.toggle) + label:before {
      background: #D8002D;
      color: white;
    }
    input[type="radio"] + label, input[type="checkbox"] + label {
      display: inline-block;
      width: 60%;
      float: none;
      position: relative;
      text-align: left;
      margin-top: 3px;
    }

    .ios7 .view header h1 {
      color: #FFF;
    }
    label {
      width: 27%;
      padding: 9px 10px 0px 0px;
    }
    input[type="text"],input[type="number"] {
      width: 65%;
      border-radius: 0px;
      border-width: 0px 0px 1px 0px;
    }
    .item-container {
      width: 100%;
      margin-top: 10px;
    }
    .item-container:first-child {
      margin-top: 30px;
    }
    .item-container:last-child {
      margin-top: 50px;
    }
    .submit {
      padding: 0.5rem 4rem 0.5rem 4rem;
      border: solid 0px #D8002D;
      border-radius: 5px;
      background: #D8002D;
      color: #FFF;
    }
    .submit:active {
      padding: 0.5rem 4rem 0.5rem 4rem;
      border: solid 0px #D8002D;
      border-radius: 5px;
      background: #c2002e;
    }
    .submit-container {
      width: 100%;
      height: 50px;
      padding-top: 30px;
      margin-top: 20px;
      text-align: center;
    }
    i.agreement,i.criteria {
      color: #D8002D;
      font-style: normal;
    }
    header {
      background: #D8002D !important;
    }
    a.backButton:before {
      color: #FFF;
    }

    .shareButton {
      text-overflow: ellipsis;
      white-space: nowrap;
      height: 44px;
      line-height: 44px;
      width: 30px;
      position: absolute;
      right: 0px;
      font-size: 20px;
      color: #FFF;
    }
    .view header h1{
      padding: 0px !important;
    }
    h1{
      margin-top: 0px!important;
    }
    label,input{
      color:#000000 !important;
    }

    .top{
      background: #D8002D;
      height:30px;
      text-align: center;
      line-height: 30px;
    }
    .mid{
      margin-top: 5vh;
      height: 65px;
    }
    .mid div{
      float: left;
    }
    #first,#second,#third{
      height: 50px;
      line-height: 50px;
    }
    #first{
      align: right;
      padding-left: 50px;
    }
    #second{
      margin-left: 5px;
      /*border: 2px black;*/
    }
    #third{
      width:20%;
    }
    #ckecknumber{
      height: 50px;
      border: 1px solid silver;
      color:#A0725B;
      font-size: 20px;
      width:30%;
    }
    #img{
      height: 50px;
    }
    .buttom{
      height:30px;
      text-align: center;
      margin-top: 2vh;;
    }
    #check{
      background: #D8002D;
      height: 30px;
      line-height: 30px;
      text-align: center;
      width: 120px;
    }
  </style>
</head>

<body class="ios7">

<div class="view active">
  <header style="display: none;">
    <h1></h1>
  </header>
  <%--<div class="pages">--%>
  <div data-left-drawer="left" data-title="" class="panel active" onclick="closeshade()" id="userinfo" style="z-index: 1;">
    <form action="accesscontrol/addWXSysUser.do" id="wechatRegister-form-registPage" method="post">
      <input type="hidden" name="user.invitebranchid" value="${branchid}"/>
      <header style="">
        <h1><i class="fa fa-user"></i> 用 户 注 册</h1>
        <a class="shareButton fa fa-qrcode" onclick="javascript:$.afui.loadContent('#qr', false, false, 'slide');"></a>
      </header>
      <div class="page-content">
        <div class="item-container">
          <label for="wechatRegister_input_mobile">手机号</label>
          <input name="customerUser.mobilephone" id="wechatRegister_input_mobile" type="text" placeholder="手机号" maxlength="20" autocomplete="off">
        </div>
        <div class="item-container">
          <label for="wechatRegister_input_remark">备注</label>
          <input name="customerUser.remark" id="wechatRegister_input_remark" type="text" placeholder="备注" maxlength="20" autocomplete="off">
        </div>
        <div class="item-container">
          <label for="wechatRegister_input_messagenumber">验证码</label>
          <input  id="wechatRegister_input_messagenumber" type="text"/>
          <button type="button"  id="wechatRegister_span_message" style="background-color:#888888;width:35%;bottom:50px;line-height:40px;height:40px;float:right;position: relative;right:7%;" class="btn">获取验证码</button>
        </div>

        <div>
        </div>
        <div class="submit-container">
          <a class="submit">注　　册</a>
        </div>
      </div>
    </form>


  </div>
  <div class="layui-layer-shade" id="shadeDiv" times="1" style="z-index:1000; background-color:white; opacity:1.0; filter:alpha(opacity=30);width: 80vw;height: 40vh;display: none;position: absolute;top:20vh;left: 10vh;border: 2px solid silver; color:#A0725B;">
    <div class="top">验证码</div>
    <div class="mid">
      <label style="color: black;width: 13vh;margin-right: 1vh;">验证码:</label>
      <input  id="ckecknumber" type="text"/>
      <img style="float: right;padding-right: 1vh;" id="img" src="wechat/showCheckIamge.do?temp=<%=Math.random()%>" onclick="javascript:changeImg()" />
    </div>
    <div class="buttom">
      <input type="button" id="check" onclick="sendMessage()" value="发送短信"/>
    </div>
  </div>
</div>
<script type="text/javascript">
    function  closeshade() {
        $("#shadeDiv").fadeOut("fast");
    }
    function  showshade() {
        console.log("1111")
        changeImg();
        $("#shadeDiv").fadeIn("fast");
    }
    function openWarningMsg(text){
        layer.msg(text,{
            time:2000
        });
    }

    function showLoading(){
        layer.load(1, {
            shade: [0.1,'#fff'] //0.1透明度的白色背景
        });
    }
    function closeLoading(){
        layer.closeAll();
    }
    var time=60;

    function checkRepeatPhone(mobile){
        var flag=true;
        $.ajax({
            url :'wechat/checkRepeatPhone.do?phone='+mobile,
            type:'post',
            dataType:'json',
            async: false,
            success:function(json){
                flag =  json.flag;
            }
        });
        return flag;
    }
    //手机号码验证
    function checkPhone(value){
        var re = /^1\d{10}$/
        return re.test(value);
    }
    function validate(){
        var mobile = $('#wechatRegister_input_mobile').val() || '';

        if(mobile == '') {
            openWarningMsg('请输入手机号！');
            return false;
        }
        if(!checkPhone(mobile)){
            openWarningMsg('请输入正确的手机号！');
            return false;
        }
        if(!checkRepeatPhone(mobile)){
            openWarningMsg('手机号已经注册！');
            return false;
        }
        return true;
//  $.afui.showMask("提交中...");
    }
    $(function() {
        $("#wechatRegister_span_message").click(function(){
            var mobile=$("#wechatRegister_input_mobile").val();
            var mobileflag=checkPhone(mobile);
            if(!mobileflag){
                openWarningMsg('请输入正确的手机号！');
                return false;
            }
            setTimeout("showshade()",100);
        });
        $(".submit").click(function(){
            var flag=validate();
            if(!flag){
                return false;
            }
            var messagenumber = $('#wechatRegister_input_messagenumber').val() || '';
            if(messagenumber == '') {
                openWarningMsg('请输入验证码！');
                return false;
            }
            flag=checkNumber(messagenumber);
            if(flag){
                showLoading();
                $.ajax({
                    url :'wechat/addInviteCustomerUser.do',
                    type:'post',
                    dataType:'json',
                    data:$("#wechatRegister-form-registPage").serializeJSON(),
                    async: false,
                    success:function(json){
                        closeLoading();
                        flag =  json.flag;
                        if(flag){
                            // 注册成功
                            openWarningMsg("注册成功！");
                            setTimeout(function() {
                                location.reload();
                            }, 2000);
                            $.ajax({
                                url:'wechat/clearMsgSession.do',
                                type:'post',
                                success:function(){
                                    $("#wechatRegister_input_hdmessage").val();
                                }
                            })
                        }
                        else{
                            // 注册失败
                            if(json.flag == false) {
                                openWarningMsg(json.msg || "注册失败！");
                                return false;
                            }
                        }
                        setTimeout(function() {
                            wx.closeWindow();
                        }, 2000);
                    }
                });
            }else{
                openWarningMsg('验证码错误！');
            }
//      $("#wechatRegister-form-registPage").submit();
        })
    });
    function sendMessage(){
        var flag=true;
        $.ajax({
            url :'wechat/checkImageNumber.do',
            type:'post',
            data:{num:$("#ckecknumber").val()},
            dataType:'json',
            async: false,
            success:function(data){
                flag=data.flag;
            }
        });
        if(!flag){
            openWarningMsg('请输入正确的验证码！');
            return false;
        }
        $("#wechatRegister_span_message").html("短信发送中");
        showLoading();
        $.ajax({
            url :'wechat/sendMessage.do',
            type:'post',
            data:{
                mobile:$("#wechatRegister_input_mobile").val(),type:"1"
            },
            dataType:'json',
            async: true,
            success:function(json){
                closeshade();
                closeLoading();
                flag = json.flag
                if(flag){
                    time=60;
                    $("#wechatRegister_span_message").addClass("l-btn-disabled")
                    window.setInterval("changeMessageTime()",1000);
                }
                else{
                    $("#wechatRegister_span_message").html("发送失败,重新发送");
                }
            }
        });
    }
    function changeMessageTime(){
        if(time>0){
            $("#wechatRegister_span_message").html(time+"秒后获取");
            time=time-1;
        }else{//到时间后清楚session里的参数
            $("#wechatRegister_span_message").html("重新发送");
            $("#wechatRegister_span_message").removeClass("l-btn-disabled")
        }

    }
    function checkNumber(number){
        var flag=true;
        $.ajax({
            url :'wechat/checkRegisiterNumber.do',
            type:'post',
            async: false,
            data:{
                number:number
            },
            dataType:'json',
            success:function(json){
                flag=json.flag;
            }
        });
        return flag;
    }
    function changeImg(){
        console.log("2222")
        $("#img").attr("src","wechat/showCheckIamge.do?temp="+Math.random())
    }
</script>

<div id="afui_mask" class="ui-loader" style="z-index: 20000; display: none;">
  <span class="ui-icon ui-icon-loading spin"></span>
  <h1>加载中 ...</h1>
</div>
</body>
</html>
