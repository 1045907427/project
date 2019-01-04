<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>订货单报表</title>
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
        #report_footer_orderGoodsReportDataPage{
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
    <form method="post"  class="form-horizontal" role="form" id="report_form_orderGoodsReportDataPage" data-ajax="false">
        <input type="hidden" id="goodsid" name="goodsid" value="${paramterMap.goodsid}"/>
        <input type="hidden" id="salesdept" name="salesdept" value="${paramterMap.salesdept}"/>
        <input type="hidden" id="ordergoodsid" name="ordergoodsid" value="${paramterMap.ordergoodsid}"/>
        <input type="hidden" id="brandid" name="brandid" value="${paramterMap.brandid}"/>
        <input type="hidden" id="goodssort" name="goodssort" value="${paramterMap.goodssort}"/>
        <input type="hidden" id="customerid" name="customerid" value="${paramterMap.customerid}"/>
        <input type="hidden" id="businessdate" name="businessdate" value="${paramterMap.businessdate}"/>
        <input type="hidden" id="businessdate1" name="businessdate1" value="${paramterMap.businessdate1}"/>
        <input type="hidden" id="orderid" name="orderid" value="${paramterMap.orderid}"/>
        <input type="hidden" id="iscomplete" name="iscomplete" value="${paramterMap.iscomplete}"/>
        <input id="report-query-page" type="hidden" name="page"/>
    </form>
    <div data-role="header" data-position="fixed">
        <a href="#pageone" data-rel="back" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a>
        <h1 align="center">订货单报表</h1>

        <div data-role="popup" id="myPopup" style="width: 100% ;height:90%;display: none;">
            <div>
                <ul data-role="listview" data-inset="true" id="report_footer_orderGoodsReportDataPage">
                </ul>
            </div>
        </div>
        <%--<a onclick="showFooterData()" href="javascript:void(0);" class="ui-btn ui-btn-inline ui-corner-all" data-position-to="window" id="report_a_footer">显示合计</a>--%>

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
        $("#report_footer_orderGoodsReportDataPage").html('');
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
    var initQueryJSON = $("#report-query-form-orderGoodsReportDataPage").serializeJSON();
    $(function(){
        searchData();
    });
    var listnumber=0;
    var nextpagenumber=listnumber;
    //将显示的div放在左右2侧
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
            url : "phone/getOrderGoodsReportData.do", // 你请求的地址
            data : $("#report_form_orderGoodsReportDataPage").serialize(),
            success : function(res){
                if(res.list.length){ // 如果后台传过来有数据执行如下操作 ， 没有就执行else 告诉用户没有更多内容呢
                    //加载数据。。。
                    var arr = new Array();
                    for (var i in res.list) {
                        arr.push("<div class='divhead' id='divhead"+listnumber+"'>["+(listnumber+1)+"]");arr.push("</div>");
                        arr.push("<li data-icon='false'  id='main-li' onclick='showOrderGoodsDetailPage(&apos;"+res.list[i].id+"&apos;)'>");
                        arr.push("<div id='divlist"+listnumber+"' class='report_data'>");
                        arr.push("<div class='id'>单据编号:");
                        arr.push(checkStringUndefined(res.list[i].id));
                        arr.push("</div>");
                        arr.push("<div class='customerid'>客户编号:");
                        arr.push(checkStringUndefined(res.list[i].customerid));
                        arr.push("</div>");
                        arr.push("<div class='customername'>客户名称:");
                        arr.push(checkStringUndefined(res.list[i].customername));
                        arr.push("</div>");

                        arr.push("<div class='businessdate'>业务日期:");
                        arr.push(checkStringUndefined(res.list[i].businessdate));
                        arr.push("</div>");

                        arr.push("<div class='notorderunitnum'>未生成数量:");
                        arr.push(checkNumberUndefined(res.list[i].notorderunitnum));
                        arr.push("</div>");

                        arr.push("<div class='notordertaxamount'>未生成金额:");
                        arr.push(checkNumberUndefined(res.list[i].notordertaxamount));
                        arr.push("</div>");

                        arr.push("</div>");
                        arr.push("<div class='div-20' id='left"+listnumber+"'>");
                        arr.push("</div>");
                        // arr.push("<div class='center_border'></div>");
                        arr.push("<div class='div-20' id='right"+listnumber+"'>");
                        arr.push("</div>");
                        arr.push("<HR class='report_hr' color=#87CEFA>");
                        arr.push("</li>");
                        listnumber++;
                    }
                    $('#thelist').append(arr.join(''));
                    $("#report_footer_orderGoodsReportDataPage").html('');
                    var arr1 = new Array();
                    for (var i in res.footer) {
                        arr1.push("<li class='footer_li'><div class='taxamount'>金额:");arr1.push(formatterBigNumNoLen(res.footer[i].taxamount));arr1.push("</div></li>");
                    }
                   if(arr1.length==0){
                       $("#report_a_footer").hide();
                   }
                   else{
                       $("#report_a_footer").show();
                   }
                    $('#report_footer_orderGoodsReportDataPage').append(arr1.join(''));
                    // initDivData();//初始化隐藏div
                    // setColumn();//根绝col隐藏和显示div
                    showHeader();//显示每条数据头
                    // showDataDiv("divlist","div");//将div排列成左右格式
                    nextpagenumber=listnumber;
                }else{
                    var footerHtml=$("#report_footer_orderGoodsReportDataPage").html();
                    footerHtml=footerHtml.replace(/\s+/g,"");//去空格
                   // if(footerHtml.replace(/(^s*)|(s*$)/g, "").length ==0){//如果没有合计就隐藏
                   //     $("#report_a_footer").hide();
                   // }
                   // else{
                   //     $("#report_a_footer").show();
                   // }
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
            content: document.getElementById("report_footer_orderGoodsReportDataPage").outerHTML,
            btn: ['确定']
        });
    }



    //空字符显示空
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


    //显示每条数据头
    function showHeader(){
        for (var j = nextpagenumber; j < listnumber; j++) {
            var o = document.getElementById("divlist" + j).getElementsByClassName("id")[0];
            document.getElementById("divhead" + j).appendChild(o);
        }
    }

    function showOrderGoodsDetailPage(id){
        location.href="phone/showOrderGoodsDetailPage.do?ordergoodsid="+id;
    }

</script>
</body>
</html>
