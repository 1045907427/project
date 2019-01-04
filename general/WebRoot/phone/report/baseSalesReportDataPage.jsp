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
        #report_footer_baseSalesReportDataPage{
            text-align: left;;
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
    <form method="post"  class="form-horizontal" role="form" id="report_form_baseSalesReportDataPage" data-ajax="false">
        <input type="hidden" id="customerid" name="customerid" value="${paramterMap.customerid}"/>
        <input type="hidden" id="brandid" name="brandid" value="${paramterMap.brandid}"/>
        <input type="hidden" id="businessdate1" name="businessdate1" value="${paramterMap.businessdate1}"/>
        <input type="hidden" id="businessdate2" name="businessdate2" value="${paramterMap.businessdate2}"/>
        <input type="hidden" id="pcustomerid" name="pcustomerid" value="${paramterMap.pcustomerid}"/>
        <input type="hidden" id="branddept" name="branddept" value="${paramterMap.branddept}"/>
        <input type="hidden" id="branduser" name="branduser" value="${paramterMap.branduser}"/>
        <input type="hidden" id="goodsid" name="goodsid" value="${paramterMap.goodsid}"/>
        <input type="hidden" id="salesarea" name="salesarea" value="${paramterMap.salesarea}"/>
        <input type="hidden" id="goodssort" name="goodssort" value="${paramterMap.goodssort}"/>
        <input type="hidden" id="goodstype" name="goodstype" value="${paramterMap.goodstype}"/>
        <input type="hidden" id="salesuser" name="salesuser" value="${paramterMap.salesuser}"/>
        <input type="hidden" id="customersort" name="customersort" value="${paramterMap.customersort}"/>
        <input type="hidden" id="supplierid" name="supplierid" value="${paramterMap.supplierid}"/>
        <input type="hidden" id="supplieruser" name="supplieruser" value="${paramterMap.supplieruser}"/>
        <input type="hidden" id="salesdept" name="salesdept" value="${paramterMap.salesdept}"/>
        <input type="hidden" id="storageid" name="storageid" value="${paramterMap.storageid}"/>

        <input type="hidden" id="groupcols" name="groupcols" value="${paramterMap.groupcols}"/>
        <input id="report-query-page" type="hidden" name="page"/>
    </form>
    <div data-role="header" data-position="fixed">
        <a href="#pageone" data-rel="back" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a>
        <h1 align="center">销售情况统计表</h1>

        <div data-role="popup" id="myPopup" style="width: 100% ;height:90%;display: none;">
            <div>
                <ul data-role="listview" data-inset="true" id="report_footer_baseSalesReportDataPage">
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
        $("#report_footer_baseSalesReportDataPage").html('');//显示合计
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
        Ajax(page); // 运行ajax 把2传过去告诉后台我上拉一次后台要加一页数据（当然 这个具体传什么还得跟后台配合）
        myScroll.refresh();// <-- Simulate network congestion, remove setTimeout from production!
    }
    function Ajax(page){ // ajax后台交互
        // androidLoading();
        showLoader();
        $.ajax({
            type : "post",
            dataType : "JSON",
            url : "phone/showBaseSalesReportData.do", // 你请求的地址
            data :$("#report_form_baseSalesReportDataPage").serialize(),
            success : function(res){
                if(res.list.length){ // 如果后台传过来有数据执行如下操作 ， 没有就执行else 告诉用户没有更多内容呢
                    //加载数据。。。
                    var arr = new Array();
                    for (var i in res.list) {
                        arr.push("<div class='divhead' id='divhead"+listnumber+"'>["+(listnumber+1)+"]");arr.push("</div>");
                        arr.push("<li data-icon='false'  id='main-li'>");
                        arr.push("<div id='divlist"+listnumber+"' class='report_data'>");
                        arr.push("<div class='customerid'>客户编号:");
                        arr.push(checkNodata(res.list[i].customerid));
                        arr.push("</div>");
                        arr.push("<div class='customername'>客户名称:");
                        arr.push(checkStringUndefined(res.list[i].customername));
                        arr.push("</div>");

                        arr.push("<div class='pcustomerid'>总店编码:");
                        arr.push(checkNodata(res.list[i].pcustomerid));
                        arr.push("</div>");
                        arr.push("<div class='pcustomername'>总店名称:");
                        arr.push(checkStringUndefined(res.list[i].pcustomername));
                        arr.push("</div>");

                        arr.push("<div class='salesuser'>客户业务员:");
                        arr.push(checkStringUndefined(res.list[i].salesusername));
                        arr.push("</div>");

                        arr.push("<div class='customersort'>客户分类:");
                        arr.push(checkStringUndefined(res.list[i].customersortname));
                        arr.push("</div>");

                        arr.push("<div class='salesarea'>销售区域:");
                        arr.push(checkStringUndefined(res.list[i].salesareaname));
                        arr.push("</div>");

                        arr.push("<div class='salesdept'>销售部门:");
                        arr.push(checkStringUndefined(res.list[i].salesdeptname));
                        arr.push("</div>");

                        arr.push("<div class='branddept'>品牌部门:");
                        arr.push(checkStringUndefined(res.list[i].branddeptname));
                        arr.push("</div>");

                        arr.push("<div class='branduser'>品牌业务员:");
                        arr.push(checkStringUndefined(res.list[i].brandusername));
                        arr.push("</div>");

                        arr.push("<div class='supplieruser'>厂家业务员:");
                        arr.push(checkStringUndefined(res.list[i].supplierusername));
                        arr.push("</div>");

                        arr.push("<div class='goodsid'>商品编码:");
                        arr.push(checkNodata(res.list[i].goodsid));
                        arr.push("</div>");
                        arr.push("<div class='goodsname'>商品名称:");
                        arr.push(checkStringUndefined(res.list[i].goodsname));
                        arr.push("</div>");

                        arr.push("<div class='goodssortname'>商品分类:");
                        arr.push(checkStringUndefined(res.list[i].goodssortname));
                        arr.push("</div>");
                        arr.push("<div class='goodstypename'>商品类型:");
                        arr.push(checkStringUndefined(res.list[i].goodstypename));
                        arr.push("</div>");
                        arr.push("<div class='barcode'>条形码:");
                        arr.push(checkStringUndefined(res.list[i].barcode));
                        arr.push("</div>");
                        arr.push("<div class='brandid'>品牌名称:");
                        arr.push(checkStringUndefined(res.list[i].brandname));
                        arr.push("</div>");

                        arr.push("<div class='supplierid'>供应商名称:");
                        arr.push(checkStringUndefined(res.list[i].suppliername));
                        arr.push("</div>");

                        arr.push("<div class='storageid'>仓库名称:");
                        arr.push(checkStringUndefined(res.list[i].storagename));
                        arr.push("</div>");
                        arr.push("<div class='unitname'>主单位:");
                        arr.push(checkStringUndefined(res.list[i].unitname));
                        arr.push("</div>");

                        arr.push("<div class='auxunitname'>辅单位:");
                        arr.push(checkStringUndefined(res.list[i].auxunitname));
                        arr.push("</div>");
                        if (res.colmap.costprice == "costprice") {
                            arr.push("<div class='costprice'>成本价:");
                            arr.push(checkNumberUndefined(res.list[i].costprice));
                            arr.push("</div>");
                        }
                        arr.push("<div class='price'>单价:");
                        arr.push(checkNumberUndefined(res.list[i].price));
                        arr.push("</div>");
                        arr.push("<div class='ordernum'>订单数量:");
                        arr.push(formatterBigNumNoLen(res.list[i].ordernum));
                        arr.push("</div>");

                        arr.push("<div class='ordertotalbox'>订单箱数:");
                        arr.push(formatterBigNumNoLen(res.list[i].ordertotalbox));
                        arr.push("</div>");
                        arr.push("<div class='orderamount'>订单金额:");
                        arr.push(checkNumberUndefined(res.list[i].orderamount));
                        arr.push("</div>");

                        arr.push("<div class='ordernotaxamount'>订单未税金额:");
                        arr.push(checkNumberUndefined(res.list[i].ordernotaxamount));
                        arr.push("</div>");

                        arr.push("<div class='initsendnum'>发货单数量:");
                        arr.push(formatterBigNumNoLen(res.list[i].initsendnum));
                        arr.push("</div>");

                        arr.push("<div class='initsendtotalbox'>发货单箱数:");
                        arr.push(formatterBigNumNoLen(res.list[i].initsendtotalbox));
                        arr.push("</div>");
                        arr.push("<div class='initsendamount'>发货单金额:");
                        arr.push(checkNumberUndefined(res.list[i].initsendamount));
                        arr.push("</div>");

                        arr.push("<div class='initsendnotaxamount'>发货单未税金额:");
                        arr.push(checkNumberUndefined(res.list[i].initsendnotaxamount));
                        arr.push("</div>");

                        arr.push("<div class='sendnum'>发货出库数量:");
                        arr.push(formatterBigNumNoLen(res.list[i].sendnum));
                        arr.push("</div>");

                        arr.push("<div class='sendtotalbox'>发货出库箱数:");
                        arr.push(formatterBigNumNoLen(res.list[i].sendtotalbox));
                        arr.push("</div>");
                        arr.push("<div class='sendamount'>发货出库金额:");
                        arr.push(checkNumberUndefined(res.list[i].sendamount));
                        arr.push("</div>");

                        arr.push("<div class='sendnotaxamount'>发货出库未税金额:");
                        arr.push(checkNumberUndefined(res.list[i].sendnotaxamount));
                        arr.push("</div>");

                        if (res.colmap.sendcostamount == "sendcostamount") {
                            arr.push("<div class='sendcostamount'>发货出库成本:");
                            arr.push(checkStringUndefined(res.list[i].sendcostamount));
                            arr.push("</div>");
                        }
                        arr.push("<div class='pushbalanceamount'>冲差金额:");
                        arr.push(checkNumberUndefined(res.list[i].pushbalanceamount));
                        arr.push("</div>");

                        arr.push("<div class='directreturnnum'>直退数量:");
                        arr.push(formatterBigNumNoLen(res.list[i].directreturnnum));
                        arr.push("</div>");

                        arr.push("<div class='directreturntotalbox'>直退箱数:");
                        arr.push(formatterBigNumNoLen(res.list[i].directreturntotalbox));
                        arr.push("</div>");
                        arr.push("<div class='directreturnamount'>直退金额:");
                        arr.push(checkNumberUndefined(res.list[i].directreturnamount));
                        arr.push("</div>");

                        arr.push("<div class='checkreturnnum'>退货数量:");
                        arr.push(formatterBigNumNoLen(res.list[i].checkreturnnum));
                        arr.push("</div>");

                        arr.push("<div class='checkreturntotalbox'>退货箱数:");
                        arr.push(formatterBigNumNoLen(res.list[i].checkreturntotalbox));
                        arr.push("</div>");
                        arr.push("<div class='checkreturnamount'>退货金额:");
                        arr.push(formatterMoney(res.list[i].checkreturnamount));
                        arr.push("</div>");

                        arr.push("<div class='checkreturnrate'>退货率:");
                        arr.push(percentData(res.list[i].checkreturnrate));
                        arr.push("</div>");
                        arr.push("<div class='returnnum'>退货总数量:");
                        arr.push(formatterBigNumNoLen(res.list[i].returnnum));
                        arr.push("</div>");

                        arr.push("<div class='returntotalbox'>退货总箱数:");
                        arr.push(formatterBigNumNoLen(res.list[i].returntotalbox));
                        arr.push("</div>");
                        arr.push("<div class='returnamount'>退货合计:");
                        arr.push(checkNumberUndefined(res.list[i].returnamount));

                        arr.push("</div>");
                        arr.push("<div class='salenum'>销售数量:");
                        arr.push(formatterBigNumNoLen(res.list[i].salenum));
                        arr.push("</div>");
                        arr.push("<div class='saletotalbox'>销售箱数:");
                        arr.push(formatterBigNumNoLen(res.list[i].saletotalbox));
                        arr.push("</div>");
                        arr.push("<div class='saleamount'>销售金额:");
                        arr.push(checkNumberUndefined(res.list[i].saleamount));
                        arr.push("</div>");

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
                    $("#report_footer_baseSalesReportDataPage").html('');
                    var arr1 = new Array();
                    for (var i in res.footer) {
                        arr1.push("<li class='footer_li'><div class='orderamount'>订单金额:");
                        arr1.push(checkNumberUndefined(res.footer[i].orderamount));
                        arr1.push("</div></li>");

                        arr1.push("<li class='footer_li'><div class='initsendamount'>发货单金额:");
                        arr1.push(checkNumberUndefined(res.footer[i].initsendamount));
                        arr1.push("</div></li>");

                        arr1.push("<li class='footer_li'><div class='sendamount'>发货出库金额:");
                        arr1.push(checkNumberUndefined(res.footer[i].sendamount));
                        arr1.push("</div></li>");

//                        arr1.push("<li class='footer_li'><div>发货出库数量:");arr1.push(checkNumberUndefined(res.footer[i].sendnum));arr1.push("</div></li>");
                        arr1.push("<li class='footer_li'><div class='pushbalanceamount'>冲差金额:");
                        arr1.push(checkNumberUndefined(res.footer[i].pushbalanceamount));
                        arr1.push("</div></li>");

                        arr1.push("<li class='footer_li'><div class='directreturnamount'>直退金额:");
                        arr1.push(checkNumberUndefined(res.footer[i].directreturnamount));
                        arr1.push("</div></li>");

                        arr1.push("<li class='footer_li'><div class='checkreturnamount'>退货金额:");
                        arr1.push(checkNumberUndefined(res.footer[i].checkreturnamount));
                        arr1.push("</div></li>");

                        arr1.push("<li class='footer_li'><div class='returnamount'>退货合计:");
                        arr1.push(checkNumberUndefined(res.footer[i].returnamount));
                        arr1.push("</div></li>");

                        arr1.push("<li class='footer_li'><div class='saletotalbox'>销售箱数:");arr1.push(checkNumberUndefined(res.footer[i].saletotalbox));arr1.push("</div></li>");
                        arr1.push("<li class='footer_li'><div class='saleamount'>销售金额:");
                        arr1.push(checkNumberUndefined(res.footer[i].saleamount));
                        arr1.push("</div></li>");

                        if (res.colmap.costamount == "costamount") {
                            arr1.push("<li class='footer_li'><div class='costamount'>成本金额:");
                            arr1.push(checkNumberUndefined(res.footer[i].costamount));
                            arr1.push("</div></li>");
                        }
                        if (res.colmap.salemarginamount == "salemarginamount") {
                            arr1.push("<li class='footer_li'><div class='salemarginamount'>销售毛利额:");
                            arr1.push(checkNumberUndefined(res.footer[i].salemarginamount));
                            arr1.push("</div></li>");
                        }
                        if (res.colmap.realrate == "realrate") {
                            arr1.push("<li class='footer_li'><div class='realrate'>实际毛利率:");
                            arr1.push(percentData(res.footer[i].realrate));
                            arr1.push("</div></li>");
                        }
                    }
                    if(arr1.length==0){
                        $("#report_a_footer").hide();
                    }
                    else{
                        $("#report_a_footer").show();
                    }
                    $('#report_footer_baseSalesReportDataPage').append(arr1.join(''));
                    //$('#thelist').append(arr.join(''));
                    initDivData();//初始化隐藏div
                    setColumn();//根绝col隐藏和显示div
                    showHeader();//显示每条数据头
                    showDataDiv("divlist","div");//将div排列成左右格式
                    nextpagenumber=listnumber;

                }else{
                    var footerHtml=$("#report_footer_baseSalesReportDataPage").html();
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
            content: document.getElementById("report_footer_baseSalesReportDataPage").outerHTML,
            btn: ['确定']
        });
    }

    function checkNodata(data){
        if("nodata" != data){
            return data;
        }else{
            return "";
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
    function percentData(value){
        if(value!=null && value!=0){
            return formatterMoney(value)+"%";
        }else{
            return "";
        }

    }


    //初始化隐藏div
    function initDivData(){
        $(".pcustomerid").css('display', 'none');
        $(".pcustomername").css('display', 'none');
        $(".salesuser").css('display', 'none');
        $(".customersort").css('display', 'none');
        $(".salesarea").css('display', 'none');
        $(".salesdept").css('display', 'none');
        $(".branddept").css('display', 'none');
        $(".branduser").css('display', 'none');
        $(".supplieruser").css('display', 'none');
        $(".goodsid").css('display', 'none');
        $(".goodsname").css('display', 'none');
        $(".goodssortname").css('display', 'none');
        $(".goodstypename").css('display', 'none');
        $(".barcode").css('display', 'none');
        $(".brandid").css('display', 'none');
        $(".supplierid").css('display', 'none');
        $(".storageid").css('display', 'none');
        $(".unitname").css('display', 'none');
        $(".auxunitname").css('display', 'none');
        $(".costprice").css('display', 'none');
        $(".price").css('display', 'none');
        $(".ordernum").css('display', 'none');
        $(".ordertotalbox").css('display', 'none');
        $(".ordernotaxamount").css('display', 'none');
        $(".initsendnum").css('display', 'none');
        $(".initsendtotalbox").css('display', 'none');
        $(".initsendnotaxamount").css('display', 'none');
        $(".sendnum").css('display', 'none');
        $(".sendtotalbox").css('display', 'none');
        $(".sendnotaxamount").css('display', 'none');
        $(".sendcostamount").css('display', 'none');
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


    function setColumn(){
        var cols = $("#groupcols").val();
        if(cols!=""){
            $(".customerid").css('display', 'none');
            $(".customername").css('display', 'none');
            $(".pcustomerid").css('display', 'none');
            $(".pcustomername").css('display', 'none');
            $(".salesuser").css('display', 'none');
            $(".customersort").css('display', 'none');
            $(".salesarea").css('display', 'none');
            $(".salesdept").css('display', 'none');
            $(".goodsid").css('display', 'none');
            $(".goodsname").css('display', 'none');
            $(".goodssortname").css('display', 'none');
            $(".goodstypename").css('display', 'none');
            $(".barcode").css('display', 'none');
            $(".brandid").css('display', 'none');
            $(".branduser").css('display', 'none');
            $(".supplieruser").css('display', 'none');
            $(".branddept").css('display', 'none');
            $(".supplierid").css('display', 'none');
            $(".storageid").css('display', 'none');
        }
        else{
            $(".customerid").css('display', 'block');
            $(".customername").css('display', 'block');
            $(".pcustomerid").css('display', 'none');
            $(".pcustomername").css('display', 'none');
            $(".salesuser").css('display', 'none');
            $(".salesarea").css('display', 'none');
            $(".salesdept").css('display', 'none');
            $(".goodsid").css('display', 'none');
            $(".goodsname").css('display', 'none');
            $(".goodssortname").css('display', 'none');
            $(".goodstypename").css('display', 'none');
            $(".barcode").css('display', 'none');
            $(".brandid").css('display', 'none');
            $(".branduser").css('display', 'none');
            $(".supplieruser").css('display', 'none');
            $(".branddept").css('display', 'none');
            $(".supplierid").css('display', 'none');
            $(".storageid").css('display', 'none');
        }
        var colarr = cols.split(",");
        for(var i=0;i<colarr.length;i++){
            var col = colarr[i];
            if(col=='customerid'){
                $(".customerid").css('display', 'block');
                $(".customername").css('display', 'block');
                $(".pcustomerid").css('display', 'block');
                $(".pcustomername").css('display', 'block');
                $(".salesuser").css('display', 'block');
                $(".customersort").css('display', 'block');
                //$("#report-datagrid-baseSalesReport").datagrid('showColumn', "salesarea");
                //$("#report-datagrid-baseSalesReport").datagrid('showColumn', "salesdept");
            }else if(col=="pcustomerid"){
                $(".pcustomerid").css('display', 'block');
                $(".pcustomername").css('display', 'block');
            }else if(col=="salesuser"){
                $(".salesuser").css('display', 'block');
                //$("#report-datagrid-baseSalesReport").datagrid('showColumn', "salesarea");
                //$("#report-datagrid-baseSalesReport").datagrid('showColumn', "salesdept");
            }else if(col=="salesarea"){
                $(".salesarea").css('display', 'block');
            }else if(col=="salesdept"){
                $(".salesdept").css('display', 'block');
            }else if(col=="goodsid"){
                $(".goodsid").css('display', 'block');
                $(".goodsname").css('display', 'block');
                $(".goodssortname").css('display', 'block');
                $(".goodstypename").css('display', 'block');
                $(".barcode").css('display', 'block');
                $(".brandid").css('display', 'block');
            }else if(col=="goodssort"){
                $(".goodssortname").css('display', 'block');
            }else if(col=="brandid"){
                $(".brandid").css('display', 'block');
                //$("#report-datagrid-baseSalesReport").datagrid('showColumn', "branddept");
            }else if(col=="branduser"){
                $(".branduser").css('display', 'block');
                //$("#report-datagrid-baseSalesReport").datagrid('showColumn', "branddept");
            }else if(col=="branddept"){
                $(".branddept").css('display', 'block');
            }else if(col=="customersort"){
                $(".customersort").css('display', 'block');
            }else if(col=="supplierid"){
                $(".supplierid").css('display', 'block');
            }else if(col=="supplieruser"){
                $(".supplieruser").css('display', 'block');
            }else if(col=="storageid"){
                $(".storageid").css('display', 'block');
            }
        }
    }
    function baseSales_retColsname(){
        var colname = "";
        var cols = $("#groupcols").val();
        if(cols == ""){
            cols = "customerid";
        }
        var colarr = cols.split(",");
        if(colarr[0]=="pcustomerid"){
            colname = "pcustomername";
        }else if(colarr[0]=='customerid'){
            colname = "customername";
        }else if(colarr[0]=="salesuser"){
            colname = "salesusername";
        }else if(colarr[0]=="salesarea"){
            colname = "salesareaname";
        }else if(colarr[0]=="salesdept"){
            colname = "salesdeptname";
        }else if(colarr[0]=="goodsid"){
            colname = "goodsname";
        }else if(colarr[0]=="goodssort"){
            colname = "goodssortname";
        }else if(colarr[0]=="brandid"){
            colname = "brandname";
        }else if(colarr[0]=="branduser"){
            colname = "brandusername";
        }else if(colarr[0]=="branddept"){
            colname = "branddeptname";
        }else if(colarr[0]=="customersort"){
            colname = "customersortname";
        }else if(colarr[0]=="supplierid"){
            colname = "suppliername";
        }else if(colarr[0]=="supplieruser"){
            colname = "supplierusername";
        }else if(colarr[0]=="storageid"){
            colname = "storagename";
        }
        return colname;
    }
    function showHeader(){
        var cols = $("#groupcols").val();
        for(var j=nextpagenumber;j<listnumber;j++){
            if(cols == ""){
                cols = "customerid";
            }
            var colarr = cols.split(",");
            if(colarr[0]=="pcustomerid"){
                var o1=document.getElementById("divlist"+j).getElementsByClassName("pcustomername")[0];
                document.getElementById("divhead"+j).appendChild(o1);
            }else if(colarr[0]=='customerid'){
                var o=document.getElementById("divlist"+j).getElementsByClassName("customerid")[0];
                var o1=document.getElementById("divlist"+j).getElementsByClassName("customername")[0];
                document.getElementById("divhead"+j).appendChild(o);
                document.getElementById("divhead"+j).appendChild(o1);
            }else if(colarr[0]=="salesuser"){
                var o=document.getElementById("divlist"+j).getElementsByClassName("salesuser")[0];
                document.getElementById("divhead"+j).appendChild(o);
            }else if(colarr[0]=="salesarea"){
                var o=document.getElementById("divlist"+j).getElementsByClassName("salesarea")[0];
                document.getElementById("divhead"+j).appendChild(o);
            }else if(colarr[0]=="salesdept"){
                var o=document.getElementById("divlist"+j).getElementsByClassName("salesdept")[0];
                document.getElementById("divhead"+j).appendChild(o);
            }else if(colarr[0]=="goodsid"){
                var o=document.getElementById("divlist"+j).getElementsByClassName("goodsid")[0];
                var o1=document.getElementById("divlist"+j).getElementsByClassName("goodsname")[0];
                document.getElementById("divhead"+j).appendChild(o);
                document.getElementById("divhead"+j).appendChild(o1);
            }else if(colarr[0]=="goodssort"){
                var o=document.getElementById("divlist"+j).getElementsByClassName("goodssortname")[0];
                document.getElementById("divhead"+j).appendChild(o);
            }else if(colarr[0]=="brandid"){
                var o=document.getElementById("divlist"+j).getElementsByClassName("brandid")[0];
                document.getElementById("divhead"+j).appendChild(o);
            }else if(colarr[0]=="branduser"){
                var o=document.getElementById("divlist"+j).getElementsByClassName("branduser")[0];
                document.getElementById("divhead"+j).appendChild(o);
            }else if(colarr[0]=="branddept"){
                var o=document.getElementById("divlist"+j).getElementsByClassName("branddept")[0];
                document.getElementById("divhead"+j).appendChild(o);
            }else if(colarr[0]=="customersort"){
                var o=document.getElementById("divlist"+j).getElementsByClassName("customersort")[0];
                document.getElementById("divhead"+j).appendChild(o);
            }else if(colarr[0]=="supplierid"){
                var o=document.getElementById("divlist"+j).getElementsByClassName("supplierid")[0];
                document.getElementById("divhead"+j).appendChild(o);
            }else if(colarr[0]=="supplieruser"){
                var o=document.getElementById("divlist"+j).getElementsByClassName("supplieruser")[0];
                document.getElementById("divhead"+j).appendChild(o);
            }else if(colarr[0]=="storageid"){
                var o=document.getElementById("divlist"+j).getElementsByClassName("storageid")[0];
                document.getElementById("divhead"+j).appendChild(o);
            }
        }
    }

</script>
</body>
</html>
