<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>银行账户余额</title>
    <%@include file="/phone/common/include.jsp"%>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="stylesheet" href="http://apps.bdimg.com/libs/bootstrap/3.2.0/css/bootstrap.min.css">
    <script src="phone/js/laypage/laypage.js"></script>
    <script type="text/javascript" src="js/syscode.js" charset="UTF-8"></script>

    <script src="http://libs.baidu.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="phone/js/layer.js"></script>
    <script type="text/javascript" src="phone/js/iscroll.js"></script>


    <style>
        li .div-20{
            display:inline-block;
            width: 48%;
            text-align: left;
            margin-left: 2%;
            margin-top: 5px;
            /*min-height:80px;*/
            float: left;
            height: 100%;

        }

        li .div-2{
            text-align: left;
            padding:10px 10px 10px 10px;
            white-space:normal;
            margin-top: 10px;
        }
        #main-li{
            /*min-height:60px;*/
            padding-top: 0px !important;
        }
        .ui-btn{
            font-size: 12px !important;
        }
        .report_hr{
            FILTER: alpha(opacity=100,finishopacity=0,style=3);
            height: 1px;
            margin:0 0 0 0;
            width:100%;
            color:#87CEFA;
            SIZE:1;
        }
        .center_border{
            position:absolute;
            bottom: 2%;
            top:2%;
            left:50%;
            width:1px;
            height:95%;
            background:#8DB6CD;
            z-index:1;
            margin-top: 0px;
        }
        .li_div{
            width: 90%;
        }
        .div-20 div{
            word-wrap:break-word;
            word-break:break-all;
            margin-top: 2px;
        }
        .form-group{
            padding: 0px 5px 0px 5px;
        }
        .footer_li div{
            /*margin: 10px 5px 5px 0px ;*/
            padding: 10px 5px 5px 10px ;
            /*padding-left: 10px;;*/
            /*padding: 0px 5px 5px 0px !important;*/
        }
        #report_footer_bankAmountLogPage{
            text-align: left;
        }
        .divhead{
            background-color: #87CEFA;
        }
        .divhead :first-child{
            display: inline!important;
            margin-right: 5px;
        }
    </style>
</head>

<body>
<div data-role="page" id="pageone" >
    <div data-role="header" data-position="fixed">
        <a href="javascript:location.href='phone/showPhoneReportListPage.do';" data-rel="back"  class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a>
        <h1 align="center">银行账户余额</h1>
    </div>

    <div data-role="main" class="ui-content">
        <div style="width:90%;height:85%;overFlow-x:hidden;overFlow-y:scroll;">
            <form method="post"  class="form-horizontal" role="form" id="report_form_bankAmountLogPage" data-ajax="false"  action="phone/showBaseFinanceDrawnDataPage.do">
                <input type="hidden" name="bankid" value="${bankid}"/>
                <input id="report-query-page" type="hidden" name="page"/>
                <div class="form-group">
                    <label class="col-sm-2 control-label">开始日期</label>
                    <div class="col-sm-10">
                        <input type="date" class="form-control" name="businessdate1" id="report_startdate_baseFinanceDrawnPage" value="${today}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">结束日期</label>
                    <div class="col-sm-10">
                        <input class="form-control"  type="date" name="businessdate2" id="report_enddate_baseFinanceDrawnPage" value="${today}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">单据类型</label>
                    <div class="col-sm-10">
                        <select id="report_billtype_bankAmountLogPage" name="billtype">
                            <option></option>
                            <c:forEach items="${billtypeList }" var="list">
                                <option value="${list.code }">${list.codename }</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>
            </form>
            <div class="form-group">
                <div align="center">
                    <a class="ui-btn ui-shadow ui-corner-all" href="javascript:void(0);" rel="external"  onclick="searchData()" style="width: 30%;display: inline-block;">查询 </a>
                    <a class="ui-btn ui-shadow ui-corner-all" href="javascript:void(0);"  onclick="reset()" style="width: 30%;display: inline-block;">重置 </a>
                </div>
            </div>
        </div>
        <div id="wrapper" style="width:100%;" >
            <div >
                <ul id="thelist" data-role="listview" data-inset="true" style="width: 100%;">
                </ul>
            </div>

        </div>
    </div>


</div>

<script type="text/javascript">
    function searchData() {
        // location.hash="pagetwo";
        $('#thelist').html('');
        i=0;
        listnumber=0;
        nextpagenumber=0;

        setTimeout(getSearchData,"500");//延迟调用，为了让页面先跳转再显示加载数据
    }

    function reset(){
        document.getElementById("report_form_bankAmountLogPage").reset();
    }

    function getSearchData(){
        window.onscroll=function(){
            viewH =$(window).height(),//可见高度
                contentH =$(document).height(),//内容高度
                scrollTop =$(document).scrollTop();//滚动高度
            //if(contentH - viewH - scrollTop <= 100) { //到达底部100px时,加载新内容
            if(viewH+scrollTop==contentH){ //到达底部时,加载新内容
                // 这里加载数据..
                pullUpAction();
            }
        }
        pullUpAction();
    }

    // $(function() {
    //     searchData();
    // })
    var listnumber=0;
    var nextpagenumber=listnumber;
    function showDataDiv(containerID,TagName){
        for(var j=nextpagenumber;j<listnumber;j++){
            var o = document.getElementById(containerID+j);
            var tn = TagName;
            var TagList;
            var rv = "";
            TagList = o.getElementsByTagName(tn);
            for (var i = 0; i < TagList.length; i++) {
                rv += TagList[i].childNodes[0].nodeValue + "\n";
                if (TagList[i].style.display == 'none') {
                    o.removeChild(TagList[i]);
                    i--;
                }
            }
            var isadd = true;
            for (var i = 0; i < TagList.length; i++) {
                if (isadd) {
                    document.getElementById("left"+j).appendChild(TagList[i]);
                    isadd = false;
                    i--;
                    continue;
                }
                if (!isadd) {
                    document.getElementById("right"+j).appendChild(TagList[i]);
                    isadd = true;
                    i--;
                    continue;
                }
            }
//        }
        }
    }
    var myScroll = null;
    var i = 0; //初始化页码为1
    function roaded(){
        myScroll = new iScroll('wrapper', {
            //vScroll:true,
            bounce:false,
            scrollbarClass: 'myScrollbar',
            useTransition: false

        });
    }
    document.addEventListener('load', roaded, false);

    //显示加载器
    function showLoader() {
        //显示加载器.for jQuery Mobile 1.2.0
        $.mobile.loading('show', {
            text: '加载中...', //加载器中显示的文字
            textVisible: true, //是否显示文字
            theme: 'c',        //加载器主题样式a-e
            textonly: false,   //是否只显示文字
            html: ""           //要显示的html内容，如图片等
        });
    }

    //隐藏加载器.for jQuery Mobile 1.2.0
    function hideLoader()
    {
        //隐藏加载器
        $.mobile.loading('hide');
    }


    function pullUpAction () { //上拉加载更多
        i++;
        var page = i; // 每上拉一次页码加一次 （就比如下一页下一页）
        $("#report-query-page").val(page);
        Ajax(page); // 运行ajax 把page传过去告诉后台我上拉一次后台要加一页数据（当然 这个具体传什么还得跟后台配合）
        myScroll.refresh();
    }
    function Ajax(page){ // ajax后台交互
        // androidLoading();
        showLoader();
        $.ajax({
            type : "post",
            dataType : "JSON",
            url : "phone/getBankAmountLogData.do", // 你请求的地址
            data : $("#report_form_bankAmountLogPage").serialize(),
            success : function(res){
                if(res.list.length){ // 如果后台传过来有数据执行如下操作 ， 没有就执行else 告诉用户没有更多内容呢
                    //加载数据。。。
                    var arr = new Array();
                    for (var i in res.list) {
                        arr.push("<div class='divhead' id='divhead" + listnumber + "'>" + (listnumber + 1) + "");
                        arr.push("</div>");
                        arr.push("<li data-icon='false'  id='main-li'>");
                        arr.push("<div id='divlist" + listnumber + "' class='report_data'>");
                        arr.push("<div class='billtype'>单据类型:");
                        arr.push(checkStringUndefined(getSysCodeName("bankAmountOthersBilltype",res.list[i].billtype)));
                        arr.push("</div>");
                        arr.push("<div class='inamount'>借方金额(收入):");
                        arr.push(checkStringUndefined(res.list[i].inamount));
                        arr.push("</div>");

                        arr.push("<div class='outamount'>贷方金额(支出):");
                        arr.push(checkStringUndefined(res.list[i].outamount));
                        arr.push("</div>");

                        arr.push("<div class='balanceamount'>余额:");
                        arr.push(checkStringUndefined(res.list[i].balanceamount));
                        arr.push("</div>");

                        arr.push("<div class='remark'>备注:");
                        arr.push(checkStringUndefined(res.list[i].remark));
                        arr.push("</div>");

                        arr.push("<div class='addusername'>添加人:");
                        arr.push(checkStringUndefined(res.list[i].addusername));
                        arr.push("</div>");

                        arr.push("<div class='adddeptname'>添加部门:");
                        arr.push(checkStringUndefined(res.list[i].adddeptname));
                        arr.push("</div>");

                        arr.push("<div class='addtime'>添加时间:");
                        arr.push(checkStringUndefined(res.list[i].addtime).replace(/[tT]/," "));
                        arr.push("</div>");

                        arr.push("</div>");
                        arr.push("<div class='div-20' id='left"+listnumber+"'>");
                        arr.push("</div>");
                        arr.push("<div class='center_border'></div>");
                        arr.push("<div class='div-20' id='right"+listnumber+"'>");
                        arr.push("</div>");
                        arr.push("<HR class='report_hr' color=#87CEFA>");
                        arr.push("</li>");
                        listnumber++;
                    }
                    $('#thelist').append(arr.join(''));
                    var arr1 = new Array();
                    for (var i in res.footer) {
                        arr1.push("<li class='footer_li'><div class='amount'>金额:");
                        arr1.push(checkStringUndefined(res.footer[i].amount));
                        arr1.push("</div></li>");
                    }
                    if(arr1.length==0){
                        $("#report_a_footer").hide();
                    }
                    else{
                        $("#report_a_footer").show();
                    }

                    showDataDiv("divlist","div");//将div排列成左右格式
                    nextpagenumber=listnumber;
                }else{
                    layer.open({
                        content: '没有更多内容了',
                        style: 'background-color:#09C1FF; color:#fff; border:none;',
                        time: 2
                    });
                }
                hideLoader();
                myScroll.refresh();
            },
            error : function(){
                //androidLoaded();
                hideLoader();
            }
        });

    }
    //空元素显示空
    function checkNumberUndefined (value){
        if (value== undefined||value==null){
            return 0;
        }
        return formatterMoney(value);
    }
    //空元素显示空
    function checkStringUndefined (value){
        if (value== undefined){
            return ' ';
        }
        return value;
    }
    /**
     * 根据编码类型和编码获取编码名称
     */
    function getSysCodeName(type,code){
        var codeJson = top.codeJsonCache;
        console.log(top);
        if(codeJson!=null){
            var codes = codeJson[type];
            var codename = "";
            if(codes!=null){
                for(var i=0;i<codes.length;i++){
                    if(code == codes[i].code){
                        codename = codes[i].codename;
                        break;
                    }
                }
            }
            return codename;
        }else{
            return "";
        }
    }





</script>
</body>
</html>
