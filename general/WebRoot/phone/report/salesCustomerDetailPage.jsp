<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>资金回笼情况表</title>
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
        #report_footer_salesCustomerDetailPage{
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
        <a href="javascript:history.go(-1);" data-rel="back"  class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a>
        <h1 align="center">分商品统计</h1>
            <form method="post"  class="form-horizontal" role="form" id="report_form_salesCustomerDetailPage" data-ajax="false"  action="phone/showBaseFinanceDrawnDataPage.do">
                <input type="hidden" id="customerid" name="customerid" value="${customerid}"/>
                <input type="hidden" id="customername" name="customername" value="${customername}"/>
                <input type="hidden" id="businessdate1" name="businessdate1" value="${businessdate1}"/>
                <input type="hidden" id="businessdate2" name="businessdate2" value="${businessdate2}"/>
                <input type="hidden" id="groupcols" name="groupcols" value="goodsid"/>
                <input id="report-query-page" type="hidden" name="page"/>
            </form>
        <div data-role="popup" id="myPopup" style="width: 100% ;height:90%;display: none;">
            <div>
                <ul data-role="listview" data-inset="true" id="report_footer_salesCustomerDetailPage">
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
        location.hash="pageone";
        $('#thelist').html('');
        $("#report_footer_salesCustomerDetailPage").html('');//显示合计
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

    var SR_footerobject  = null;
    $(function() {
        searchData();
    })
    var listnumber=0;
    var nextpagenumber=listnumber;
    function showDataDiv(containerID,TagName){
        for(var j=nextpagenumber;j<listnumber;j++){
            var o = document.getElementById(containerID+j);
//        var o = document.getElementsByClassName(containerID);
            var tn = TagName;
            var TagList;
            var rv = "";
//        for (var j = 0; j < o.length; j++) {
            TagList = o.getElementsByTagName(tn);
            for (var i = 0; i < TagList.length; i++) {
                rv += TagList[i].childNodes[0].nodeValue + "\n";
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
        Ajax(page); // 运行ajax 把page传过去告诉后台我上拉一次后台要加一页数据（当然 这个具体传什么还得跟后台配合）
        myScroll.refresh();
    }
    function Ajax(page){ // ajax后台交互
        // androidLoading();
        showLoader();
        $.ajax({
            type : "post",
            dataType : "JSON",
            url : "phone/showBaseSalesReportData.do", // 你请求的地址
            data : $("#report_form_salesCustomerDetailPage").serialize(),
            success : function(res){
                if(res.list.length){ // 如果后台传过来有数据执行如下操作 ， 没有就执行else 告诉用户没有更多内容呢
                    //加载数据。。。
                    var arr = new Array();
                    for (var i in res.list) {
                        arr.push("<div class='divhead' id='divhead" + listnumber + "'>[" + (listnumber + 1) + "]");
                        arr.push("</div>");
                        arr.push("<li data-icon='false'  id='main-li'> ");
                        arr.push("<div id='divlist" + listnumber + "' class='report_data'>");
                        if(res.colmap.goodsid == "goodsid") {
                            arr.push("<div class='goodsid'>商品编码:");
                            arr.push(checkStringUndefined(res.list[i].goodsid));
                            arr.push("</div>");
                            arr.push("<div class='goodsname'>商品名称:");
                            arr.push(checkStringUndefined(res.list[i].goodsname));
                            arr.push("</div>");
                        }
                        arr.push("<div class='barcode'>条形码:");
                        arr.push(checkStringUndefined(res.list[i].barcode));
                        arr.push("</div>");
                        if (res.colmap.unitname == "unitname") {
                            arr.push("<div class='unitid'>主单位:");
                            arr.push(checkStringUndefined(res.list[i].unitname));
                            arr.push("</div>");
                        }
                        if(res.colmap.branddept == "branddept") {
                            arr.push("<div class='branddept'>品牌部门:");
                            arr.push(checkStringUndefined(res.list[i].branddeptname));
                            arr.push("</div>");
                        }
                        if (res.colmap.ordernum == "ordernum") {
                            arr.push("<div class='ordernum'>订单数量:");
                            arr.push(formatterBigNumNoLen(res.list[i].ordernum));
                            arr.push("</div>");
                        }
                        arr.push("<div class='ordertotalbox'>订单箱数:");
                        arr.push(formatterBigNumNoLen(res.list[i].ordertotalbox));
                        arr.push("</div>");
                        if (res.colmap.orderamount == "orderamount") {
                            arr.push("<div class='orderamount'>订单金额:");
                            arr.push(checkNumberUndefined(res.list[i].orderamount));
                            arr.push("</div>");
                        }
                        if (res.colmap.ordernotaxamount == "ordernotaxamount") {
                            arr.push("<div class='ordernotaxamount'>订单未税金额:");
                            arr.push(checkNumberUndefined(res.list[i].ordernotaxamount));
                            arr.push("</div>");
                        }
                        if (res.colmap.initsendnum == "initsendnum") {
                            arr.push("<div class='initsendnum'>发货单数量:");
                            arr.push(formatterBigNumNoLen(res.list[i].initsendnum));
                            arr.push("</div>");
                        }
                        arr.push("<div class='initsendtotalbox'>发货单箱数:");
                        arr.push(formatterBigNumNoLen(res.list[i].initsendtotalbox));
                        arr.push("</div>");
                        if (res.colmap.initsendamount == "initsendamount") {
                            arr.push("<div class='initsendamount'>发货单金额:");
                            arr.push(checkNumberUndefined(res.list[i].initsendamount));
                            arr.push("</div>");
                        }
                        if (res.colmap.initsendnotaxamount == "initsendnotaxamount") {
                            arr.push("<div class='initsendnotaxamount'>发货单未税金额:");
                            arr.push(checkNumberUndefined(res.list[i].initsendnotaxamount));
                            arr.push("</div>");
                        }
                        if (res.colmap.sendnum == "sendnum") {
                            arr.push("<div class='sendnum'>发货出库数量:");
                            arr.push(formatterBigNumNoLen(res.list[i].sendnum));
                            arr.push("</div>");
                        }
                        arr.push("<div class='sendtotalbox'>发货出库箱数:");
                        arr.push(formatterBigNumNoLen(res.list[i].sendtotalbox));
                        arr.push("</div>");
                        if (res.colmap.sendamount == "sendamount") {
                            arr.push("<div class='sendamount'>发货出库金额:");
                            arr.push(checkNumberUndefined(res.list[i].sendamount));
                            arr.push("</div>");
                        }
                        if (res.colmap.sendnotaxamount == "sendnotaxamount") {
                            arr.push("<div class='sendnotaxamount'>发货出库未税金额:");
                            arr.push(checkNumberUndefined(res.list[i].sendnotaxamount));
                            arr.push("</div>");
                        }
                        if (res.colmap.sendcostamount == "sendcostamount") {
                            arr.push("<div class='sendcostamount'>发货出库成本:");
                            arr.push(checkStringUndefined(res.list[i].sendcostamount));
                            arr.push("</div>");
                        }
                        if (res.colmap.pushbalanceamount == "pushbalanceamount") {
                            arr.push("<div class='pushbalanceamount'>冲差金额:");
                            arr.push(checkNumberUndefined(res.list[i].pushbalanceamount));
                            arr.push("</div>");
                        }
                        if (res.colmap.directreturnnum == "directreturnnum") {
                            arr.push("<div class='directreturnnum'>直退数量:");
                            arr.push(formatterBigNumNoLen(res.list[i].directreturnnum));
                            arr.push("</div>");
                        }
                        arr.push("<div class='directreturntotalbox'>直退箱数:");
                        arr.push(formatterBigNumNoLen(res.list[i].directreturntotalbox));
                        arr.push("</div>");
                        if (res.colmap.directreturnamount == "directreturnamount") {
                            arr.push("<div class='directreturnamount'>直退金额:");
                            arr.push(checkNumberUndefined(res.list[i].directreturnamount));
                            arr.push("</div>");
                        }
                        if (res.colmap.checkreturnnum == "checkreturnnum") {
                            arr.push("<div class='checkreturnnum'>退货数量:");
                            arr.push(formatterBigNumNoLen(res.list[i].checkreturnnum));
                            arr.push("</div>");
                        }
                        arr.push("<div class='checkreturntotalbox'>退货箱数:");
                        arr.push(formatterBigNumNoLen(res.list[i].checkreturntotalbox));
                        arr.push("</div>");
                        if (res.colmap.checkreturnamount == "checkreturnamount") {
                            arr.push("<div class='checkreturnamount'>退货金额:");
                            arr.push(formatterMoney(res.list[i].checkreturnamount));
                            arr.push("</div>");
                        }
                        arr.push("<div class='checkreturnrate'>退货率:");
                        arr.push(percentData(res.list[i].checkreturnrate));
                        arr.push("</div>");
                        if (res.colmap.returnnum == "returnnum") {
                            arr.push("<div class='returnnum'>退货总数量:");
                            arr.push(formatterBigNumNoLen(res.list[i].returnnum));
                            arr.push("</div>");
                        }
                        arr.push("<div class='returntotalbox'>退货总箱数:");
                        arr.push(formatterBigNumNoLen(res.list[i].returntotalbox));
                        arr.push("</div>");
                        if (res.colmap.returnamount == "returnamount") {
                            arr.push("<div class='returnamount'>退货合计:");
                            arr.push(checkNumberUndefined(res.list[i].returnamount));
                        }
                        arr.push("</div>");
                        arr.push("<div class='salenum'>销售数量:");
                        arr.push(formatterBigNumNoLen(res.list[i].salenum));
                        arr.push("</div>");
                        arr.push("<div class='saletotalbox'>销售箱数:");
                        arr.push(formatterBigNumNoLen(res.list[i].saletotalbox));
                        arr.push("</div>");
                        if (res.colmap.salesamount == "salesamount") {
                            arr.push("<div class='saleamount'>销售金额:");
                            arr.push(checkNumberUndefined(res.list[i].saleamount));
                            arr.push("</div>");
                        }
                        arr.push("<div class='salenotaxamount'>销售无税金额:");
                        arr.push(checkNumberUndefined(res.list[i].salenotaxamount));
                        arr.push("</div>");
                        arr.push("<div class='saletax'>销售税额:");
                        arr.push(checkNumberUndefined(res.list[i].saletax));
                        arr.push("</div>");
                        if (res.colmap.costamount == "costamount") {
                            arr.push("<div class='costamount'>成本金额:");
                            arr.push(checkNumberUndefined(res.list[i].costamount));
                            arr.push("</div>");
                        }
                        if (res.colmap.salemarginamount == "salemarginamount") {
                            arr.push("<div class='salemarginamount'>销售毛利额:");
                            arr.push(checkNumberUndefined(res.list[i].salemarginamount));
                            arr.push("</div>");
                        }
                        if (res.colmap.realrate == "realrate") {
                            arr.push("<div class='realrate'>实际毛利率:");
                            arr.push(percentData(res.list[i].realrate));
                            arr.push("</div>");
                        }
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
                    $("#report_footer_salesCustomerDetailPage").html('');
                    var arr1 = new Array();
                    for (var i in res.footer) {
                        if (res.colmap.ordernum == "ordernum") {
                            arr1.push("<div class='ordernum'>订单数量:");
                            arr1.push(formatterBigNumNoLen(res.footer[i].ordernum));
                            arr1.push("</div>");
                        }
                        arr1.push("<div class='ordertotalbox'>订单箱数:");
                        arr1.push(formatterBigNumNoLen(res.footer[i].ordertotalbox));
                        arr1.push("</div>");
                        if (res.colmap.orderamount == "orderamount") {
                            arr1.push("<div class='orderamount'>订单金额:");
                            arr1.push(checkNumberUndefined(res.footer[i].orderamount));
                            arr1.push("</div>");
                        }
                        if (res.colmap.ordernotaxamount == "ordernotaxamount") {
                            arr1.push("<div class='ordernotaxamount'>订单未税金额:");
                            arr1.push(checkNumberUndefined(res.footer[i].ordernotaxamount));
                            arr1.push("</div>");
                        }
                        if (res.colmap.initsendnum == "initsendnum") {
                            arr1.push("<div class='initsendnum'>发货单数量:");
                            arr1.push(formatterBigNumNoLen(res.footer[i].initsendnum));
                            arr1.push("</div>");
                        }
                        arr1.push("<div class='initsendtotalbox'>发货单箱数:");
                        arr1.push(formatterBigNumNoLen(res.footer[i].initsendtotalbox));
                        arr1.push("</div>");
                        if (res.colmap.initsendamount == "initsendamount") {
                            arr1.push("<div class='initsendamount'>发货单金额:");
                            arr1.push(checkNumberUndefined(res.footer[i].initsendamount));
                            arr1.push("</div>");
                        }
                        if (res.colmap.initsendnotaxamount == "initsendnotaxamount") {
                            arr1.push("<div class='initsendnotaxamount'>发货单未税金额:");
                            arr1.push(checkNumberUndefined(res.footer[i].initsendnotaxamount));
                            arr1.push("</div>");
                        }
                        if (res.colmap.sendnum == "sendnum") {
                            arr1.push("<div class='sendnum'>发货出库数量:");
                            arr1.push(formatterBigNumNoLen(res.footer[i].sendnum));
                            arr1.push("</div>");
                        }
                        arr1.push("<div class='sendtotalbox'>发货出库箱数:");
                        arr1.push(formatterBigNumNoLen(res.footer[i].sendtotalbox));
                        arr1.push("</div>");
                        if (res.colmap.sendamount == "sendamount") {
                            arr1.push("<div class='sendamount'>发货出库金额:");
                            arr1.push(checkNumberUndefined(res.footer[i].sendamount));
                            arr1.push("</div>");
                        }
                        if (res.colmap.sendnotaxamount == "sendnotaxamount") {
                            arr1.push("<div class='sendnotaxamount'>发货出库未税金额:");
                            arr1.push(checkNumberUndefined(res.footer[i].sendnotaxamount));
                            arr1.push("</div>");
                        }
                        if (res.colmap.sendcostamount == "sendcostamount") {
                            arr1.push("<div class='sendcostamount'>发货出库成本:");
                            arr1.push(checkStringUndefined(res.footer[i].sendcostamount));
                            arr1.push("</div>");
                        }
                        if (res.colmap.pushbalanceamount == "pushbalanceamount") {
                            arr1.push("<div class='pushbalanceamount'>冲差金额:");
                            arr1.push(checkNumberUndefined(res.footer[i].pushbalanceamount));
                            arr1.push("</div>");
                        }
                        if (res.colmap.directreturnnum == "directreturnnum") {
                            arr1.push("<div class='directreturnnum'>直退数量:");
                            arr1.push(formatterBigNumNoLen(res.footer[i].directreturnnum));
                            arr1.push("</div>");
                        }
                        arr1.push("<div class='directreturntotalbox'>直退箱数:");
                        arr1.push(formatterBigNumNoLen(res.footer[i].directreturntotalbox));
                        arr1.push("</div>");
                        if (res.colmap.directreturnamount == "directreturnamount") {
                            arr1.push("<div class='directreturnamount'>直退金额:");
                            arr1.push(checkNumberUndefined(res.footer[i].directreturnamount));
                            arr1.push("</div>");
                        }
                        if (res.colmap.checkreturnnum == "checkreturnnum") {
                            arr1.push("<div class='checkreturnnum'>退货数量:");
                            arr1.push(formatterBigNumNoLen(res.footer[i].checkreturnnum));
                            arr1.push("</div>");
                        }
                        arr1.push("<div class='checkreturntotalbox'>退货箱数:");
                        arr1.push(formatterBigNumNoLen(res.footer[i].checkreturntotalbox));
                        arr1.push("</div>");
                        if (res.colmap.checkreturnamount == "checkreturnamount") {
                            arr1.push("<div class='checkreturnamount'>退货金额:");
                            arr1.push(formatterMoney(res.footer[i].checkreturnamount));
                            arr1.push("</div>");
                        }
                        if (res.colmap.returnnum == "returnnum") {
                            arr1.push("<div class='returnnum'>退货总数量:");
                            arr1.push(formatterBigNumNoLen(res.footer[i].returnnum));
                            arr1.push("</div>");
                        }
                        arr1.push("<div class='returntotalbox'>退货总箱数:");
                        arr1.push(formatterBigNumNoLen(res.footer[i].returntotalbox));
                        arr1.push("</div>");
                        if (res.colmap.returnamount == "returnamount") {
                            arr1.push("<div class='returnamount'>退货合计:");
                            arr1.push(checkNumberUndefined(res.footer[i].returnamount));
                        }
                        arr1.push("</div>");
                        arr1.push("<div class='salenum'>销售数量:");
                        arr1.push(formatterBigNumNoLen(res.footer[i].salenum));
                        arr1.push("</div>");
                        arr1.push("<div class='saletotalbox'>销售箱数:");
                        arr1.push(formatterBigNumNoLen(res.footer[i].saletotalbox));
                        arr1.push("</div>");
                        if (res.colmap.salesamount == "salesamount") {
                            arr1.push("<div class='saleamount'>销售金额:");
                            arr1.push(checkNumberUndefined(res.footer[i].saleamount));
                            arr1.push("</div>");
                        }
                        arr1.push("<div class='salenotaxamount'>销售无税金额:");
                        arr1.push(checkNumberUndefined(res.footer[i].salenotaxamount));
                        arr1.push("</div>");
                        arr1.push("<div class='saletax'>销售税额:");
                        arr1.push(checkNumberUndefined(res.footer[i].saletax));
                        arr1.push("</div>");
                        if (res.colmap.costamount == "costamount") {
                            arr1.push("<div class='costamount'>成本金额:");
                            arr1.push(checkNumberUndefined(res.footer[i].costamount));
                            arr1.push("</div>");
                        }
                        if (res.colmap.salemarginamount == "salemarginamount") {
                            arr1.push("<div class='salemarginamount'>销售毛利额:");
                            arr1.push(checkNumberUndefined(res.footer[i].salemarginamount));
                            arr1.push("</div>");
                        }
                    }
                    if(arr1.length==0){
                        $("#report_a_footer").hide();
                    }
                    else{
                        $("#report_a_footer").show();
                    }
                    $('#report_footer_salesCustomerDetailPage').append(arr1.join(''));
                    initDivData();//初始化隐藏div
                    showHeader();
                    showDataDiv("divlist","div");//将div排列成左右格式
                    nextpagenumber=listnumber;
                }else{
                    var footerHtml=$("#report_footer_salesCustomerDetailPage").html();
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

    //显示合计弹出框
    function showFooterData(){
        layer.open({
            content: document.getElementById("report_footer_salesCustomerDetailPage").outerHTML,
            btn: ['确定']
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
    //显示百分比数据
    function percentData(value){
        if(value!=null && value!=0){
            return formatterMoney(value)+"%";
        }else{
            return "";
        }

    }


    //初始化隐藏div
    function initDivData(){
        $(".auxnumdetail").css('display', 'none');
        $(".pcustomername").css('display', 'none');
        $(".salesarea").css('display', 'none');
        $(".salesdept").css('display', 'none');
        $(".ordernum").css('display', 'none');
        $(".ordertotalbox").css('display', 'none');
        $(".ordernotaxamount").css('display', 'none');
        $(".initsendnum").css('display', 'none');
        $(".initsendtotalbox").css('display', 'none');
        $(".initsendnotaxamount").css('display', 'none');
        $(".sendnum").css('display', 'none');
        $(".sendtotalbox").css('display', 'none');
        $(".sendnotaxamount").css('display', 'none');
        $(".directreturnnum").css('display', 'none');
        $(".directreturntotalbox").css('display', 'none');
        $(".checkreturnnum").css('display', 'none');
        $(".checkreturntotalbox").css('display', 'none');
        $(".checkreturnrate").css('display', 'none');
        $(".returnnum").css('display', 'none');
        $(".returntotalbox").css('display', 'none');
        $(".salenum").css('display', 'none');
        $(".salenotaxamount").css('display', 'none');
        $(".saletax").css('display', 'none');
    }
    //显示每条数据头
    function showHeader(){
        for(var j=nextpagenumber;j<listnumber;j++){
                var o=document.getElementById("divlist"+j).getElementsByClassName("goodsid")[0];
                var o1=document.getElementById("divlist"+j).getElementsByClassName("goodsname")[0];
                document.getElementById("divhead"+j).appendChild(o);
                document.getElementById("divhead"+j).appendChild(o1);

        }
    }
</script>
</body>
</html>
