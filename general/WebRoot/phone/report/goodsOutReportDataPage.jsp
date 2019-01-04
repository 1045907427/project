<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>销售情况统计表</title>
    <%@include file="/phone/common/include.jsp"%>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link rel="stylesheet" href="http://apps.bdimg.com/libs/bootstrap/3.2.0/css/bootstrap.min.css">
    <script src="phone/js/laypage/laypage.js"></script>

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
        #report_footer_goodsOutReportDataPage{
            text-align: left;;
        }
        .divhead{
            background-color: #87CEFA;
        }
        .divhead :first-child{
            display: inline!important;
            margin-right: 5px;
        }
        .sendnum{
            color:red;
        }
    </style>
</head>

<body>
<div data-role="page" id="pagetwo">
    <form method="post"  class="form-horizontal" role="form" id="report_form_goodsOutReportDataPage" data-ajax="false">
        <input type="hidden" id="begindate" name="begindate" value="${paramterMap.begindate}"/>
        <input type="hidden" id="enddate" name="enddate" value="${paramterMap.enddate}"/>
        <input type="hidden" id="issupply" name="issupply" value="${paramterMap.issupply}"/>
        <input type="hidden" id="goodsid" name="goodsid" value="${paramterMap.goodsid}"/>
        <input type="hidden" id="brandid" name="brandid" value="${paramterMap.brandid}"/>
        <input type="hidden" id="customerid" name="customerid" value="${paramterMap.customerid}"/>
        <input type="hidden" id="pid" name="pid" value="${paramterMap.pid}"/>
        <input type="hidden" id="salesuser" name="salesuser" value="${paramterMap.salesuser}"/>
        <input type="hidden" id="supplierid" name="supplierid" value="${paramterMap.supplierid}"/>
        <%--<input type="hidden" id="goodstype" name="goodstype" value="${paramterMap.goodstype}"/>--%>

        <%--<input type="hidden" id="groupcols" name="groupcols" value="${paramterMap.groupcols}"/>--%>
        <input id="report-query-page" type="hidden" name="page"/>
    </form>
    <div data-role="header" data-position="fixed">
        <a href="javascript:history.go(-1);" data-rel="back" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a>
        <h1 align="center">缺货商品</h1>

        <div data-role="popup" id="myPopup" style="width: 100% ;height:90%;display: none;">
            <div>
                <ul data-role="listview" data-inset="true" id="report_footer_goodsOutReportDataPage">
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
    function searchData() {
        location.hash="pagetwo";
        $('#thelist').html('');
        $("#report_footer_goodsOutReportDataPage").html('');//显示合计
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
        pullUpAction();
    }

    var initQueryJSON = $("#report-query-form-baseSalesReport").serializeJSON();
    $(function(){
        searchData();
    });

    var listnumber=0;
    var nextpagenumber=listnumber;
    function showDataDiv(containerID,TagName){
        var o = document.getElementById("divlist"+containerID);
        var tn = TagName;
        var TagList;
        var rv = "";
//        for (var j = 0; j < o.length; j++) {
        TagList = o.getElementsByTagName(tn);
        for (var i = 0; i < TagList.length; i++) {
            if (TagList[i].style.display == 'none') {
                o.removeChild(TagList[i]);
                i--;
            }
            //其它自己的操作...
        }
        //var length=TagList.length;
        var isadd = true;
        for (var i = 0; i < TagList.length; i++) {
            if (isadd) {
                document.getElementById("left"+containerID).appendChild(TagList[i]);
                isadd = false;
                i--;
                continue;
            }
            if (!isadd) {
                document.getElementById("right"+containerID).appendChild(TagList[i]);
                isadd = true;
                i--;
                continue;
            }
        }
    }
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
        Ajax(page); // 运行ajax 把2传过去告诉后台我上拉一次后台要加一页数据（当然 这个具体传什么还得跟后台配合）
        myScroll.refresh();// <-- Simulate network congestion, remove setTimeout from production!
    }
    function Ajax(page){ // ajax后台交互
        // androidLoading();
        showLoader();
        $.ajax({
            type : "post",
            dataType : "JSON",
            url : "phone/getGoodsOutReportData.do", // 你请求的地址
            data :$("#report_form_goodsOutReportDataPage").serialize(),
            success : function(res){
                if(res.list.length){ // 如果后台传过来有数据执行如下操作 ， 没有就执行else 告诉用户没有更多内容呢
                    //加载数据。。。
//                    var arr = new Array();
                    for (var i in res.list) {
                        var arr = new Array();
                        arr.push("<div class='divhead' id='divhead"+listnumber+"'>["+(listnumber+1)+"]");arr.push("</div>");
                        arr.push("<li data-icon='false'  id='main-li"+listnumber+"' onclick='showOrHiddenDiv(&apos;"+listnumber+"&apos;)'>");
                        arr.push("<div id='divlist"+listnumber+"' class='report_data'>");
                        arr.push("<div class='orderid'>订单编号:");
                        arr.push(checkStringUndefined(res.list[i].orderid));
                        arr.push("</div>")

                        arr.push("<div class='businessdate'>订单日期:");
                        arr.push(checkStringUndefined(res.list[i].businessdate));
                        arr.push("</div>")

                        arr.push("<div class='customerid'>客户编码:");
                        arr.push(checkStringUndefined(res.list[i].customerid));
                        arr.push("</div>");

                        arr.push("<div class='goodsid'>商品编码:");
                        arr.push(checkStringUndefined(res.list[i].goodsid));
                        arr.push("</div>");

                        arr.push("<div class='customername'>客户名称:");
                        arr.push(checkStringUndefined(res.list[i].customername));
                        arr.push("</div>");

                        arr.push("<div class='goodsname'>商品名称:");
                        arr.push(checkStringUndefined(res.list[i].goodsname));
                        arr.push("</div>");

                        arr.push("<div class='brandid'>品牌名称:");
                        arr.push(checkStringUndefined(res.list[i].brandname));
                        arr.push("</div>");

                        arr.push("<div class='barcode'>条形码:");
                        arr.push(checkStringUndefined(res.list[i].barcode));
                        arr.push("</div>");

                        arr.push("<div class='fixnum'>订购数量:");
                        arr.push(formatterBigNumNoLen(res.list[i].fixnum));
                        arr.push("</div>");

                        arr.push("<div class='fixauxnumdetail'>订购辅数量:");
                        arr.push(checkStringUndefined(res.list[i].fixauxnumdetail));
                        arr.push("</div>");

                        arr.push("<div class='fixamount'>订购金额:");
                        arr.push(formatterMoney(res.list[i].fixamount));
                        arr.push("</div>");

                        arr.push("<div class='sendnum'>已发数量:");
                        arr.push(formatterBigNumNoLen(res.list[i].sendnum));
                        arr.push("</div>");

                        arr.push("<div class='sendauxnumdetail'>已发辅数量:");
                        arr.push(checkStringUndefined(res.list[i].sendauxnumdetail));
                        arr.push("</div>");

//                        arr.push("<div class='sendamount'>已发辅数量:");
//                        arr.push(formatterBigNumNoLen(res.list[i].sendamount));
//                        arr.push("</div>");

                        arr.push("<div class='sendamount'>已发金额:");
                        arr.push(formatterMoney(res.list[i].sendamount));
                        arr.push("</div>");

                        arr.push("<div class='outnum'>缺货数量:");
                        arr.push(formatterBigNumNoLen(res.list[i].outnum));
                        arr.push("</div>");

                        arr.push("<div class='outauxnumdetail'>缺货辅数量:");
                        arr.push(checkStringUndefined(res.list[i].outauxnumdetail));
                        arr.push("</div>");

                        arr.push("<div class='outamount'>缺货金额:");
                        arr.push(formatterMoney(res.list[i].outamount));
                        arr.push("</div>");

                        arr.push("<div class='issupply'>状态:");
                        if(res.list[i].issupply=='0'){
                            arr.push("未补单");
                        }else if(res.list[i].issupply=='1'){
                            arr.push("已补单");
                        }
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
//                        initDivData();//初始化隐藏div
//                        setColumn();//根绝col隐藏和显示div
                        showHeader(listnumber-1);//显示每条数据头
                        showDataDiv(listnumber-1,"div");//将div排列成左右格式
//                        initRemoveDiv("divlist"+(listnumber-1),"div");
//                        showOrHiddenDiv(listnumber-1);
                        initDiv(listnumber-1);
                    }
                    $("#report_footer_goodsOutReportDataPage").html('');
                    var arr1 = new Array();
                    for (var i in res.footer) {
                        arr1.push("<li class='footer_li'><div class='fixnum'>订购数量:");
                        arr1.push(formatterBigNumNoLen(res.footer[i].fixnum));
                        arr1.push("</div></li>");

                        arr1.push("<li class='footer_li'><div class='fixauxnumdetail'>订购辅数量:");
                        arr1.push(checkStringUndefined(res.footer[i].fixauxnumdetail));
                        arr1.push("</div></li>");

                        arr1.push("<li class='footer_li'><div class='fixamount'>订购金额:");
                        arr1.push(formatterMoney(res.footer[i].fixamount));
                        arr1.push("</div></li>");

                        arr1.push("<li class='footer_li'><div class='sendnum'>已发数量:");
                        arr1.push(formatterBigNumNoLen(res.footer[i].sendnum));
                        arr1.push("</div></li>");

                        arr1.push("<li class='footer_li'><div class='sendauxnumdetail'>已发辅数量:");
                        arr1.push(checkStringUndefined(res.footer[i].sendauxnumdetail));
                        arr1.push("</div></li>");

//                        arr1.push("<li class='footer_li'><div class='sendamount'>已发辅数量:");
//                        arr1.push(formatterBigNumNoLen(res.list[i].sendamount));
//                        arr1.push("</div></li>");

                        arr1.push("<li class='footer_li'><div class='sendamount'>已发金额:");
                        arr1.push(formatterMoney(res.footer[i].sendamount));
                        arr1.push("</div></li>");

                        arr1.push("<li class='footer_li'><div class='outnum'>缺货数量:");
                        arr1.push(formatterBigNumNoLen(res.footer[i].outnum));
                        arr1.push("</div></li>");

                        arr1.push("<li class='footer_li'><div class='outauxnumdetail'>缺货辅数量:");
                        arr1.push(checkStringUndefined(res.footer[i].outauxnumdetail));
                        arr1.push("</div></li>");

                        arr1.push("<li class='footer_li'><div class='outamount'>缺货金额:");
                        arr1.push(formatterMoney(res.footer[i].outamount));
                        arr1.push("</div></li>");
                    }
                    if(arr1.length==0){
                        $("#report_a_footer").hide();
                    }
                    else{
                        $("#report_a_footer").show();
                    }
                    $('#report_footer_goodsOutReportDataPage').append(arr1.join(''));
                    nextpagenumber=listnumber;

                }else{
                    var footerHtml=$("#report_footer_goodsOutReportDataPage").html();
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
                // androidLoaded();
                hideLoader();
                myScroll.refresh();
            },
            error : function(){
                //androidLoaded();
                hideLoader();
            }
        });

    }

    function showFooterData(){
        layer.open({
            content: document.getElementById("report_footer_goodsOutReportDataPage").outerHTML,
            btn: ['确定']
        });
    }

    //空元素显示空
    function checkNumberUndefined (value){
        if (value== undefined||value==null){
            return 0;
        }
        return value;
    }
    //空元素显示空
    function checkStringUndefined (value){
        if (value== undefined){
            return ' ';
        }
        return value;
    }
    function percentData(value){
        if(value!=null && value!=0){
            return formatterMoney(value)+"%";
        }else{
            return "";
        }

    }


    //初始化隐藏div
    function initDivData(){

    }

    function showHeader(listnumber){
        var j=listnumber;
        var o1=document.getElementById("divlist"+j).getElementsByClassName("orderid")[0];
        var o2=document.getElementById("divlist"+j).getElementsByClassName("businessdate")[0];
        document.getElementById("divhead"+j).appendChild(o1);
        document.getElementById("divhead"+j).appendChild(o2);
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
        addShowDiv(divlist,left,right);
        balanceDiv(divlist,left,right,'div');//重新排列左右2侧div
    }
</script>
</body>
</html>
