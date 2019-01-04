<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <%@include file="/phone/common/include.jsp"%>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="stylesheet" href="http://apps.bdimg.com/libs/bootstrap/3.2.0/css/bootstrap.min.css">
    <script src="phone/js/laypage/laypage.js"></script>

    <%--<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>--%>
    <script src="http://libs.baidu.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="phone/js/layer.js"></script>

    <style type="text/css">

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
        #report_footer_baseFinanceDrawnPage{
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
<div data-role="page" id="pagetwo">
    <form method="post"  class="form-horizontal" role="form" id="report_form_salesOrderTrackReportDataPage" data-ajax="false">
        <input type="hidden" id="sourceid" name="sourceid" value="${paramterMap.sourceid}"/>
        <input type="hidden" id="businessdate1" name="businessdate1" value="${paramterMap.businessdate1}"/>
        <input type="hidden" id="businessdate2" name="businessdate2" value="${paramterMap.businessdate2}"/>
        <input type="hidden" id="checkstatus" name="checkstatus" value="${paramterMap.checkstatus}"/>
        <input type="hidden" id="goodsid" name="goodsid" value="${paramterMap.goodsid}"/>
        <input type="hidden" id="brandid" name="brandid" value="${paramterMap.brandid}"/>
        <input type="hidden" id="customerid" name="customerid" value="${paramterMap.customerid}"/>
        <input type="hidden" id="salesuser" name="salesuser" value="${paramterMap.salesuser}"/>
        <input type="hidden" id="pcustomerid" name="pcustomerid" value="${paramterMap.pcustomerid}"/>
        <input type="hidden" id="indooruserid" name="indooruserid" value="${paramterMap.indooruserid}"/>

        <input id="report-query-page" type="hidden" name="page"/>
    </form>
    <div data-role="header" data-position="fixed">
        <a href="#pageone" data-rel="back" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a>
        <h1 align="center">订单追踪明细</h1>

        <div data-role="popup" id="myPopup" style="width: 100% ;height:90%;display: none;">
            <div>
                <ul data-role="listview" data-inset="true" id="report_footer_salesOrderTrackReportDataPage">
                </ul>
            </div>
        </div>
        <a onclick="showFooterData()" href="javascript:void(0);" class="ui-btn ui-btn-inline ui-corner-all" data-position-to="window" id="report_a_footer">显示合计</a>

    </div>
    <div id="wrapper">
        <ul id="thelist" data-role="listview" data-inset="true" style="width: 100%;">
        </ul>
    </div>
</div>


<script type="text/javascript">
    function showDataDiv(containerID,TagName){
        for(var j=nextpagenumber;j<listnumber;j++){
            var o = document.getElementById(containerID+j);
//        var o = document.getElementsByClassName(containerID);
            var tn = TagName;
            var TagList;
            var rv = "";
//        for (var j = 0; j < o.length; j++) {
            TagList = o.getElementsByTagName(tn);
//            for (var i = 0; i < TagList.length; i++) {
//                rv += TagList[i].childNodes[0].nodeValue + "\n";
//                if (TagList[i].style.display == 'none') {
//                    o.removeChild(TagList[i]);
//                    i--;
//                }
//                //其它自己的操作...
//            }
            //var length=TagList.length;
            var isadd = true;
            for (var i = 0; i < TagList.length; i++) {
                if (isadd) {
                    if(TagList[i].style.display == 'none'){
                        continue;
                    }
                    document.getElementById("left"+j).appendChild(TagList[i]);
                    isadd = false;
                    i--;
                    continue;
                }
                if (!isadd) {
                    if(TagList[i].style.display == 'none'){
                        continue;
                    }
                    document.getElementById("right"+j).appendChild(TagList[i]);
                    isadd = true;
                    i--;
                    continue;
                }
            }
//        }
        }
    }
    function searchData() {
        location.hash="pagetwo";
        $('#thelist').html('');
        $("#report_footer_salesOrderTrackReportDataPage").html('');//显示合计
        i=0;
        listnumber=0;
        nextpagenumber=0;
        setTimeout(getSearchData,"500");//延迟调用，为了让页面先跳转再显示加载数据
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
        // $('#thelist').html('');
        pullUpAction();
    }
    
    $(document).ready(function(){
        searchData();
    });

    
    var listnumber=0;
    var nextpagenumber=listnumber;
    var myScroll = null;
    var generatedCount = 0;
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


    function pullUpAction () { //上拉加载更多
        i++;
        var page = i; // 每上拉一次页码加一次 （就比如下一页下一页）
        $("#report-query-page").val(page);
        Ajax(page); // 运行ajax 把2传过去告诉后台我上拉一次后台要加一页数据（当然 这个具体传什么还得跟后台配合）
        myScroll.refresh();// <-- Simulate network congestion, remove setTimeout from production!
    }
    function Ajax(page){ // ajax后台交互
        // androidLoading();
        showLoader();
        $.ajax({
            type : "post",
            dataType : "JSON",
            url : "phone/showBuyOrderTrackReportData.do", // 你请求的地址
            data :$("#report_form_salesOrderTrackReportDataPage").serialize(),
            success : function(res){
                // androidLoaded();
                if(res.list.length){ // 如果后台传过来有数据执行如下操作 ， 没有就执行else 告诉用户没有更多内容呢
                    //加载数据。。。
                    var arr = new Array();
                    //arrayObject.push(newelement1,newelement2,....,newelementX)
                    for (var i in res.list) {
                        var arr = new Array();
                        arr.push("<div style='background-color: #87CEFA'>");arr.push("["+(listnumber+1)+"]");arr.push(res.list[i].orderid);arr.push("</div>");
                        arr.push("<div style='background-color: #87CEFA'>");arr.push('['+checkStringUndefined(res.list[i].goodsid)+']');
                        arr.push(goodsName(res.list[i].deliverytype,res.list[i].goodsname));arr.push("</div>");
                        arr.push("<li data-icon='false'  id='main-li"+listnumber+"' onclick='showOrHiddenDiv(&apos;"+listnumber+"&apos;)'>");
                        arr.push("<div id='divlist"+listnumber+"' class='report_data'>");
                        arr.push("<div class='barcode'>条形码:");
                        arr.push(checkStringUndefined(res.list[i].barcode));
                        arr.push("</div>");
                        arr.push("<div class='ordernum'>订单数量:");
                        arr.push(formatterBigNumNoLen(res.list[i].ordernum));
                        arr.push("</div>");

                        arr.push("<div class='orderamount'>订单金额:");
                        arr.push(checkNumberUndefined(res.list[i].orderamount));
                        arr.push("</div>");

                        arr.push("<div class='sendprice'>发货出库单价:");
                        arr.push(sendprice(res.list[i].sendprice));
                        arr.push("</div>");
                        arr.push("<div class='sendnum'>发货出库数量:");
                        arr.push(checkNumberUndefined(res.list[i].sendnum));
                        arr.push("</div>");

                        arr.push("<div class='salesnum'>销售数量:");
                        arr.push(formatterBigNumNoLen(res.list[i].salesnum));
                        arr.push("</div>");
                        arr.push("<div class='sendamount'>发货出库金额:");
                        arr.push(checkNumberUndefined(res.list[i].sendamount));
                        arr.push("</div>");

                        arr.push("<div class='checkprice'>验收单价:");
                        arr.push(checkPrice(res.list[i].initprice, res.list[i].checkprice));
                        arr.push("</div>");
                        arr.push("<div class='checkamount'>验收金额:");
                        arr.push(checkNumberUndefined(res.list[i].checkamount));
                        arr.push("</div>");
                        arr.push("<div class='returnnum'>直退数量:");
                        arr.push(formatterBigNumNoLen(res.list[i].returnnum));
                        arr.push("</div>");

                        arr.push("<div class='salesamount'>销售金额:");
                        arr.push(checkNumberUndefined(res.list[i].salesamount));
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
                        $('#thelist').append(arr.join(''));
//                        initRemoveDiv("divlist"+(listnumber-1),"div");
//                        showHeader(listnumber-1);//显示每条数据头
                        initDiv(listnumber-1);
                    }
                    $("#report_footer_salesOrderTrackReportDataPage").html('');
                    var arr1 = new Array();
                    for (var i in res.footer) {
                        arr1.push("<li class='footer_li'><div>订单数量:");
                        arr1.push(formatterBigNumNoLen(res.footer[i].ordernum));
                        arr1.push("</div></li>");

                        arr1.push("<li class='footer_li'><div>订单金额:");
                        arr1.push(checkNumberUndefined(res.footer[i].orderamount));
                        arr1.push("</div></li>");

                        arr1.push("<li class='footer_li'><div>发货出库数量:");
                        arr1.push(formatterBigNumNoLen(res.footer[i].sendnum));
                        arr1.push("</div></li>");

                        arr1.push("<li class='footer_li'><div>发货出库金额:");
                        arr1.push(checkNumberUndefined(res.footer[i].sendamount));
                        arr1.push("</div></li>");

                        arr1.push("<li class='footer_li'><div>直退数量:");
                        arr1.push(formatterBigNumNoLen(res.footer[i].returnnum));
                        arr1.push("</div></li>");

                        arr1.push("<li class='footer_li'><div>验收金额:");
                        arr1.push(checkNumberUndefined(res.footer[i].checkamount));
                        arr1.push("</div></li>");
                        arr1.push("<li class='footer_li'><div>销售数量:");
                        arr1.push(formatterBigNumNoLen(res.footer[i].salesnum));
                        arr1.push("</div></li>");
                        arr1.push("<li class='footer_li'><div>销售金额:");
                        arr1.push(checkNumberUndefined(res.footer[i].salesamount));
                        arr1.push("</div></li>");
                    }
                    if(arr1.length==0){
                        $("#report_a_footer").hide();
                    }
                    else{
                        $("#report_a_footer").show();
                    }
                    $('#report_footer_salesOrderTrackReportDataPage').append(arr1.join(''));
//                    $('#thelist').append(arr.join(''));
//                    initDivData();//初始化隐藏divsetColumn
//                    setColumn();//根绝col隐藏和显示div
//                    showHeader();//显示每条数据头
//                    showDataDiv("divlist","div");//将div排列成左右格式
                    nextpagenumber=listnumber;
                }else{
                    var footerHtml=$("#report_footer_salesOrderTrackReportDataPage").html();
                    footerHtml=footerHtml.replace(/\s+/g,"");//去空格
                    if(footerHtml.replace(/(^s*)|(s*$)/g, "").length ==0){
                        $("#report_a_footer").hide();
                    }
                    else{
                        $("#report_a_footer").show();
                    }
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
                hideLoader();
                //androidLoaded();
            }
        });

    }

    function showFooterData(){
        layer.open({
            content: document.getElementById("report_footer_salesOrderTrackReportDataPage").outerHTML,
            btn: ['确定']
        });
    }

    function goodsName(data,value){
        if(data=='1'){
            return "<font color='blue'>&nbsp;赠 </font>"+value;
        }else{
            return value;
        }

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
    function sendprice(value){
        if (value== undefined||value==null){
            return 0;
        }
        if(value>0){
            return formatterMoney(value);
        }else{
            return "";
        }
    }
    //验收单价
    function checkPrice(data,value){
        if (value== undefined||value==null){
            return 0;
        }
        if(parseFloat(value)>parseFloat(data)){
            return "<font color='blue' title='原价:"+formatterMoney(data)+"'>"+ formatterMoney(value)+ "</font>";
        }
        else if(parseFloat(value)<parseFloat(data)){
            return "<font color='red' title='原价:"+formatterMoney(data)+"'>"+ formatterMoney(value)+ "</font>";
        }
        else{
            return formatterMoney(value);
        }

    }
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
    //显示隐藏或显示的div。点击时先将字段隐藏或显示出来，然后按照顺序重新返回divlist，再根据隐藏或显示重新排列
    function showOrHiddenDiv(listnumber,clicknum){
        var main='main-li'+listnumber;
        var left='left'+listnumber;
        var right='right'+listnumber;
        var divlist='divlist'+listnumber;
        if($("#"+main).find(".barcode").css('display')=='none'){
            $("#"+main).find(".barcode").css('display',"block");
        } else{
            $("#"+main).find(".barcode").css('display',"none");
        }
        if($("#"+main).find(".sendprice").css('display')=='none'){
            $("#"+main).find(".sendprice").css('display',"block");
        } else{
            $("#"+main).find(".sendprice").css('display',"none");
        }
        if($("#"+main).find(".sendnum").css('display')=='none'){
            $("#"+main).find(".sendnum").css('display',"block");
        } else{
            $("#"+main).find(".sendnum").css('display',"none");
        }
        if($("#"+main).find(".salesnum").css('display')=='none'){
            $("#"+main).find(".salesnum").css('display',"block");
        } else{
            $("#"+main).find(".salesnum").css('display',"none");
        }
        if($("#"+main).find(".checkprice").css('display')=='none'){
            $("#"+main).find(".checkprice").css('display',"block");
        } else{
            $("#"+main).find(".checkprice").css('display',"none");
        }
        if($("#"+main).find(".returnnum").css('display')=='none'){
            $("#"+main).find(".returnnum").css('display',"block");

        } else{
            $("#"+main).find(".returnnum").css('display',"none");
//            addShowDiv(divlist,left,right);
//            balanceDiv(divlist,left,right,'div');//重新排列左右2侧div
        }
        addShowDiv(divlist,left,right);
        balanceDiv(divlist,left,right,'div');//重新排列左右2侧div
    }

    function balanceDiv(divlist,left,right,TagName){//根据隐藏和显示的div重新排序
        var o = document.getElementById(divlist);
        var tn = TagName;
        var TagList;
        var rv = "";
        TagList = o.getElementsByTagName(tn);
        var isadd = true;

        for (var i = 0; i < TagList.length; i++) {
            if (isadd) {//添加div到左侧
                while (TagList[0].style.display == 'none') {//如果是隐藏div，也是按照左右各一个顺序拜访
                        document.getElementById(left).appendChild(TagList[i]);
                }
                if (i >= 0) {
                    document.getElementById(left).appendChild(TagList[i]);
                    isadd = false;
                    i--;
                    continue;
                }
            }
            else if (!isadd) {//添加右侧div
                while (TagList[0].style.display == 'none') {
                        document.getElementById(right).appendChild(TagList[i]);
                }
                if(i>=0) {
                    document.getElementById(right).appendChild(TagList[i]);
                    isadd = true;
                    i--;
                    continue;
                }
            }
        }
    }
//    function addHiddenDiv(divlist,left,right){//把2侧的div按顺序放到divlist里用于重新排序
////        alert("hidden");
//        var leftnode = document.getElementById(left);
//        var rightnode = document.getElementById(right);
//        var divlist=document.getElementById(divlist);
//        var leftList = leftnode.getElementsByTagName('div');
//        var rightList = rightnode.getElementsByTagName('div');
//        while(leftList.length>0||rightList.length>0){
//            if(leftList.length>0){;
//                divlist.appendChild(leftList[0]);
//            }
//            if(rightList.length>0){
//                divlist.appendChild(rightList[0]);
//            }
//        }
//    }
    function addShowDiv(divlist,left,right){
        var leftnode = document.getElementById(left);
        var rightnode = document.getElementById(right);
        var divlist=document.getElementById(divlist);
        var leftList = leftnode.getElementsByTagName('div');
        var rightList = rightnode.getElementsByTagName('div');

        while(leftList.length>0||rightList.length>0){
            var leftnoneflag=true;;
            var rightnoneflag=true;;
            while(leftnoneflag) {
                if (leftList.length > 0) {
                    if(leftList[0].style.display!='none'){
//                        alert(leftList[0].className);
                        divlist.appendChild(leftList[0]);
                        leftnoneflag=false;
                    }
                    else{
                        divlist.appendChild(leftList[0]);
                    }
                }
                else{
                    leftnoneflag=false;
                }
            }
//            alert(leftList.length+","+rightList.length+","+leftnoneflag+","+rightnoneflag);
            while(rightnoneflag) {
                if (rightList.length > 0) {
                    if(rightList[0].style.display!='none'){
                        divlist.appendChild(rightList[0]);
                        rightnoneflag=false;
                    }
                    else{
                        divlist.appendChild(rightList[0]);
                    }
                }
                else{
                    rightnoneflag=false;
                }
            }
        }
    }

    //显示隐藏或显示的div。点击时先将字段隐藏或显示出来，然后按照顺序重新返回divlist，再根据隐藏或显示重新排列
    function initDiv(listnumber){
        var main='main-li'+listnumber;
        var left='left'+listnumber;
        var right='right'+listnumber;
        var divlist='divlist'+listnumber;
        if($("#"+main).find(".barcode").css('display')=='none'){
            $("#"+main).find(".barcode").css('display',"block");
        } else{
            $("#"+main).find(".barcode").css('display',"none");
        }
        if($("#"+main).find(".sendprice").css('display')=='none'){
            $("#"+main).find(".sendprice").css('display',"block");
        } else{
            $("#"+main).find(".sendprice").css('display',"none");
        }
        if($("#"+main).find(".sendnum").css('display')=='none'){
            $("#"+main).find(".sendnum").css('display',"block");
        } else{
            $("#"+main).find(".sendnum").css('display',"none");
        }
        if($("#"+main).find(".salesnum").css('display')=='none'){
            $("#"+main).find(".salesnum").css('display',"block");
        } else{
            $("#"+main).find(".salesnum").css('display',"none");
        }
        if($("#"+main).find(".checkprice").css('display')=='none'){
            $("#"+main).find(".checkprice").css('display',"block");
        } else{
            $("#"+main).find(".checkprice").css('display',"none");
        }
        if($("#"+main).find(".returnnum").css('display')=='none'){
            $("#"+main).find(".returnnum").css('display',"block");
        } else{
            $("#"+main).find(".returnnum").css('display',"none");
        }
        addShowDiv(divlist,left,right);
        balanceDiv(divlist,left,right,'div');//重新排列左右2侧div
    }
</script>
</body>

</html>

