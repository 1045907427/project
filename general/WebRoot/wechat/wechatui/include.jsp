<%-- for Wechat Page --%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+(request.getServerPort() == 80 ? "" : ":" + request.getServerPort())+path+"/";
%>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
<meta http-equiv="pragma" content="no-cache"/>
<meta http-equiv="cache-control" content="no-cache"/>
<meta http-equiv="expires" content="0"/>
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>

<base id="basePath" href="<%=basePath%>"/>

<script type="text/javascript" src="<%=basePath%>js/jquery-1.11.2.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=basePath%>js/jquery.form.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=basePath%>js/md5.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=basePath%>js/jqueryUtils.js" charset="UTF-8"></script>


<link rel="stylesheet" type="text/css" href="wechat/wechatui/layer/skin/layer.css">
<script type="text/javascript" src="wechat/wechatui/layer/layer.js"></script>

<style type="text/css">
    * {
        font-family: 'Microsoft YaHei', '宋体', Arial, Helvetica, sans-serif;;
    }

    .required {
        color: #F00;
    }

    .list-title{
        height: 26px;
        line-height: 26px;
        padding-left: 10px;
    }
    .nodisplay {
        display: none;
    }
</style>
<style type="text/css" id="af-ui-overwrite">

    .afToastContainer.cc{
        top: 50%;
        margin-top: -72px;
        bottom: auto;
    }
    .afToast.msg{
        background: rgba(92, 92, 92, 0.9);
        border: 0;
        color: #fff;
        text-align: center;
    }
    .ios7 .view header{
        border-bottom: 0;
        background: rgba(239, 239, 239, 0.3);
        background: -moz-linear-gradient(top, #fdfdfd 0, #eff1f4 100%);
        background: -webkit-linear-gradient(top, #fdfdfd 0, #eff1f4 100%);
        background: -o-linear-gradient(top, #fdfdfd 0, #eff1f4 100%);
        background: -ms-linear-gradient(top, #fdfdfd 0, #eff1f4 100%);
        filter: progid: DXImageTransform.Microsoft.gradient(startColorstr='#FDFDFD', endColorstr='#EFF1F4', GradientType=0);
        background: linear-gradient(top, #fdfdfd 0, #eff1f4 100%);
    }

    .ios7 .view header h1{
        color: #7B7B7B;
    }

    .ios7 .view header .backButton {
        min-width: 6rem;
        text-align: left;
        /*padding-left: 0px !important;*/
        color: #7B7B7B;
        left: 0;
    }

    .ios7 .view header .backButton {
        background: #f8f8f8;
        color: #7B7B7B;
        display: block;
        position: absolute;
        line-height: 44px;
        left: 25px;
        text-overflow: ellipsis;
        font-size: 14px;
        padding: 0;
        text-shadow: none;
        background-color: transparent;
        border: none;
        border-color: transparent;
        height: 44px;
        top: auto;
        border-radius: 0;
        box-shadow: none;
        margin: 0;
        padding-left: 0;
        text-align: center;
        width: 50px;
        padding: 0 !important;
        margin: 0 !important;
    }

    .head{
        padding: 8px;
        background: #efefef;
    }

    .view header h1{
        font-weight: normal;
        font-size: 16px;
        vertical-align: middle;
        height: 44px;
        line-height: 44px;
        padding: 0;
    }

    input[type="search"] {
        border-radius: 5px;
    }
</style>

<script type="text/javascript">

    var token = $("meta[name='_csrf']").attr("content");
    var header = $("meta[name='_csrf_header']").attr("content");
    jQuery.ajaxSetup({
//        beforeSend: function(xhr){
//            if(this.type == 'POST') {
//                xhr.setRequestHeader(header, token);
//            }
//        },
        statusCode : {
            404:function(){
                console.log("404错误！");
            },
            900: function(){
                alert("用户登录数量超过系统限制！");
                top.location.href="login";
            },
            901: function(){
                alert("未登录！");
                top.location.href="login";
            },
            902 : function(){
                console.log("无权限操作！");
            },
            903 : function(){
                alert("账号在其它地方登录!请注意账号安全!");
                top.location.href="login.do";
            }
        },
        error: function(jqXHR, textStatus, errorMsg){ // 出错时默认的处理函数
            //console.log("出错了。"); //ie下会报错
            if(window.console && console.log){
                console.log("出错了。");
            }
        }
    });
</script>

<script type="text/javascript" defer="defer">


    /**
     * 数字格式化
     *
     * @param val
     * @param fixed
     * @returns {*}
     */
    function formatterMoney(val, fixed){
        if(typeof(fixed)=="undefined" || fixed==null || fixed == "" || isNaN(fixed) || fixed <0){
            fixed=2;
        }
        if(val!=null && (val!="" || val==0)){
            if(Number(val)<0){
                //return "-"+fNumber((-Number(val))+"",2);
                var newdata= Number(val).toFixed(fixed);
                if(newdata==0){
                    //return newdata.toFixed(fixed);
                    return Number(newdata).toFixed(fixed);
                }
                return newdata;
            }else if(Number(val) == 0){
                return Number(val).toFixed(fixed);
            }else{
                //return fNumber(val,2);
                return Number(val).toFixed(fixed);
            }
        }else{
            return "";
        }
    }

</script>