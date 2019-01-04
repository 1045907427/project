<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>单据追踪报表</title>
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
        #report_footer_salesDemandBillReportPage{
            text-align: left;;
        }
        .divhead{
            background-color: #87CEFA;
        }
        .divhead :first-child{
            display: inline!important;
            margin-right: 5px;
        }
        .main{}
        .item{padding:10px 5px;line-height:20px;}
    </style>
</head>

<body>
<div data-role="page" id="pageone" >
    <div data-role="header" data-position="fixed">
        <a href="javascript:location.href='phone/showPhoneReportListPage.do';" data-rel="back"  class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a>
        <h1 align="center">单据查询</h1>
    </div>
    <div data-role="main" class="ui-content">
        <div style="position:absolute;width:90%;height:82%;overFlow-x:hidden;overFlow-y:scroll;">
            <form method="post"  class="form-horizontal" role="form" id="report_form_salesDemandBillReportPage" data-ajax="false"  action="phone/showBaseFinanceDrawnDataPage.do">
                <div class="form-group">
                    <label class="col-sm-2 control-label">开始日期</label>
                    <div class="col-sm-10">
                        <input type="date" class="form-control" name="businessdate1" id="report_startdate_salesDemandBillReportPage" value="${today}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">结束日期</label>
                    <div class="col-sm-10">
                        <input class="form-control"  type="date" name="businessdate2" id="report_enddate_salesDemandBillReportPage" value="${today}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">要货单号</label>
                    <div class="col-sm-10" style="display: inline;" >
                        <input type="text" class="form-control" name="sourceid" id="report_sourceid_salesDemandBillReportPage">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">订单单号</label>
                    <div class="col-sm-10" style="display: inline;" >
                        <input type="text" class="form-control" name="orderid" id="report_orderid_salesDemandBillReportPage">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">客户</label>
                    <div class="col-sm-10">
                        <input type="text" name="customername" id="report_customername_salesDemandBillReportPage" readonly="readonly" data-clear-btn="true"/>
                        <input type="hidden" name="customerid" id="report_customerid_salesDemandBillReportPage"/>
                    </div>
                </div>
                <%--<div class="form-group">--%>
                <%--<label class="col-sm-2 control-label">客户业务员</label>--%>
                <%--<div class="col-sm-10">--%>
                <%--<input type="text" class="required" name="salesusername" id="report_salesusername_salesDemandBillReportPage" value="" readonly="readonly" data-clear-btn="true"/>--%>
                <%--<input type="hidden" name="salesuser" id="report_salesuser_salesDemandBillReportPage"/>--%>
                <%--</div>--%>
                <%--</div>--%>
                <div class="form-group">
                    <label class="col-sm-2 control-label">品牌</label>
                    <div class="col-sm-10">
                        <input type="text" class="required" name="brandname" id="report_brandname_salesDemandBillReportPage"  readonly="readonly" data-clear-btn="true"/>
                        <input type="hidden" name="brandid" id="report_brandid_salesDemandBillReportPage"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">品牌部门</label>
                    <div class="col-sm-10">
                        <input type="text" class="required" name="branddeptname" id="report_branddeptname_salesDemandBillReportPage"  readonly="readonly" data-clear-btn="true"/>
                        <input type="hidden" name="branddept" id="report_branddeptid_salesDemandBillReportPage"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">品牌业务员</label>
                    <div class="col-sm-10">
                        <input type="text" class="required" name="brandusername" id="report_brandusername_salesDemandBillReportPage"  readonly="readonly" data-clear-btn="true"/>
                        <input type="hidden" name="branduser" id="report_branduserid_salesDemandBillReportPage"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">商品</label>
                    <div class="col-sm-10">
                        <input type="text" class="required" name="goodsname" id="report_goodsname_salesDemandBillReportPage"  readonly="readonly" data-clear-btn="true"/>
                        <input type="hidden" name="goodsid" id="report_goodsid_salesDemandBillReportPage" />
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">销售区域</label>
                    <div class="col-sm-10">
                        <input type="text" class="required" name="salesareaname" id="report_salesareaname_salesDemandBillReportPage" value="" readonly="readonly" data-clear-btn="true"/>
                        <input type="hidden" name="salesarea" id="report_salesareaid_salesDemandBillReportPage"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">销售部门</label>
                    <div class="col-sm-10">
                        <input type="text" class="required" name="salesdeptname" id="report_salesdeptname_salesDemandBillReportPage" value="" readonly="readonly" data-clear-btn="true"/>
                        <input type="hidden" name="salesdept" id="report_salesdeptid_salesDemandBillReportPage"/>
                    </div>
                </div>
                <%--<div class="form-group">--%>
                <%--<label class="col-sm-2 control-label">客户分类</label>--%>
                <%--<div class="col-sm-10">--%>
                <%--<input type="text" class="required" name="customersortname" id="report_customersortname_salesDemandBillReportPage" value="" readonly="readonly" data-clear-btn="true"/>--%>
                <%--<input type="hidden" name="customersort" id="report_customersortid_salesDemandBillReportPage"/>--%>
                <%--</div>--%>
                <%--</div>--%>
                <div class="form-group" style="display: none;">
                    <label class="col-sm-2 control-label">小计列：</label>
                    <div class="col-sm-10">
                        <select id="cols" name="cols" multiple class="form-control">
                            <option class="groupcols" value="" selected></option>
                            <option class="groupcols" value="customerid">客户</option>
                            <%--<option class="groupcols" value="customersort">客户分类</option>--%>
                            <%--<option class="groupcols" value="salesuser">客户业务员</option>--%>
                            <option class="groupcols" value="salesarea">销售区域</option>
                            <option class="groupcols" value="brandid">品牌</option>
                            <option class="groupcols" value="goodsid">商品</option>
                            <option class="groupcols" value="branddept">品牌部门</option>
                            <option class="groupcols" value="branduser">品牌业务员</option>
                            <option class="groupcols" value="salesdept">销售部门</option>
                        </select>
                        <input id="report-query-groupcols" type="hidden" name="groupcols"/>
                    </div>
                </div>
                <%--<div class="checkbox" style="float: left;">--%>
                <%--<label>--%>
                <%--<input type="checkbox" class="status" value="0"/> 未生成订单--%>
                <%--</label>--%>
                <%--</div>--%>
                <%--<div class="checkbox" style="float: left;">--%>
                <%--<label>--%>
                <%--<input type="checkbox" class="status" value="1"/> 已生成订单--%>
                <%--</label>--%>
                <%--</div>--%>
                <input id="report-query-status" type="hidden" name="status"/>
                <input id="report-query-page" type="hidden" name="page"/>
            </form>
        </div>
    </div>
    <div style="position:fixed;bottom:0px;width: 100%;" id="footer">
        <div align="center">
            <a class="ui-btn ui-shadow ui-corner-all" href="javascript:void(0);" rel="external"  onclick="searchData()" style="width: 30%;display: inline-block;">查询 </a>
            <a class="ui-btn ui-shadow ui-corner-all" href="javascript:void(0);"  onclick="reset()" style="width: 30%;display: inline-block;">重置 </a>
        </div>
    </div>
</div>
<div data-role="page" id="pagetwo">
    <div data-role="header" data-position="fixed">
        <a href="#pageone" data-rel="back" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a>
        <h1 align="center">单据查询</h1>

        <div data-role="popup" id="myPopup" style="width: 100% ;height:90%;display: none;">
            <div>
                <ul data-role="listview" data-inset="true" id="report_footer_salesDemandBillReportPage">
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
<div data-role="page" id="pagethree">
    <div data-role="header" data-position="fixed">
        <a href="#pagetwo" data-rel="back" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a>
        <h1 align="center">单据查询</h1>
    </div>
    <div data-role="main" class="ui-content" id="pagethree_main">

    </div>
</div>
<script type="text/javascript">
    $(".status").click(function(){
        var stus = "";
        $("#report-query-status").val(stus);
        $(".status").each(function(){
            if($(this).is(':checked')){
                if(stus==""){
                    stus = $(this).val();
                }else{
                    stus += ","+$(this).val();
                }
                $("#report-query-status").val(stus);
            }
        });
    });

    $("#cols").change(function(){
        var cols=$(this).val();
        var colsarr=new Array();
        colsarr=cols;
        if(colsarr.length>0){
            if(colsarr[0]=='')
                colsarr.splice(0,1);
        }
        $("#cols").val(colsarr);
    });


    function searchData() {
        location.hash="pagetwo";
        $('#thelist').html('');
        $("#report_footer_salesDemandBillReportPage").html('');
        i=0;
        listnumber=0;
        nextpagenumber=0;

        setTimeout(getSearchData,"500");//延迟调用，为了让页面先跳转再显示加载数据
    }
    function getSearchData(){
        var cols=$("#cols").val();
        $("#report-query-groupcols").val(cols);
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
    function reset(){
        //参照窗口需要手动清除
        $('#report_customername_salesDemandBillReportPage').val( '');
        $('#report_customername_salesDemandBillReportPage').change();
        $('#report_brandname_salesDemandBillReportPage').val( '');
        $('#report_brandname_salesDemandBillReportPage').change();
        $('#report_branddeptname_salesDemandBillReportPage').val( '');
        $('#report_branddeptname_salesDemandBillReportPage').change();
        $('#report_brandusername_salesDemandBillReportPage').val( '');
        $('#report_brandusername_salesDemandBillReportPage').change();
        $('#report_goodsname_salesDemandBillReportPage').val( '');
        $('#report_goodsname_salesDemandBillReportPage').change();
        $('#report_salesareaname_salesDemandBillReportPage').val( '');
        $('#report_salesareaname_salesDemandBillReportPage').change();
//        $('#report_salesusername_salesDemandBillReportPage').val( '');
//        $('#report_salesusername_salesDemandBillReportPage').change();
        $('#report_salesdeptname_salesDemandBillReportPage').val( '');
        $('#report_salesdeptname_salesDemandBillReportPage').change();
//        $('#report_customersortname_salesDemandBillReportPage').val( '');
//        $('#report_customersortname_salesDemandBillReportPage').change();

        document.getElementById("report_form_salesDemandBillReportPage").reset();
    }
    var SR_footerobject  = null;
    var initQueryJSON = $("#report-query-form-salesDemandBillReportPage").serializeJSON();
    $(function(){

        // 客户
        $('#report_customername_salesDemandBillReportPage').on('click', function() {
            androidWidget({
                type: 'widget',
                referwid: 'RL_T_BASE_SALES_CUSTOMER_PARENT_2',
                onSelect: 'selectCustomer',
                checkType : "2",
                onCheck: 'selectCustomer'
            });
        }).on('change', function(e) {
            var v = $(this).val();
            $(this).attr('value', v);
            $('#report_customerid_salesDemandBillReportPage').attr('value', (v || ':').split(':')[0]);
        });
        // 商品
        $('#report_goodsname_salesDemandBillReportPage').on('click', function() {
            androidWidget({
                type: 'goodsWidget',
                checkType : "2",
                onSelect: 'selectGood'
            });

        }).on('change', function(e) {
            var v = $(this).val();
            $(this).attr('value', v);
            $('#report_goodsid_salesDemandBillReportPage').attr('value', (v || ':').split(':')[0]);
        });
//        // 业务员
//        $('#report_salesusername_salesDemandBillReportPage').on('click', function() {
//            androidWidget({
//                type: 'widget',
//                referwid : "RL_T_BASE_PERSONNEL_CLIENTSELLER",
//                checkType : "2",
//                onSelect: 'selectSalesuser'
//            });
//
//        }).on('change', function(e) {
//            var v = $(this).val();
//            $(this).attr('value', v);
//            $('#report_salesuser_salesDemandBillReportPage').attr('value', (v || ':').split(':')[0]);
//        });
        // 品牌
        $('#report_brandname_salesDemandBillReportPage').on('click', function() {

            androidWidget({
                type: 'widget',
                referwid: 'RL_T_BASE_GOODS_BRAND',
                checkType : "2",
                onCheck: 'selectBrand'
            });
        }).on('change', function() {
            var v = $(this).val();
            $(this).attr('value', v);
            $('#report_brandid_salesDemandBillReportPage').attr('value', (v || ':').split(':')[0]);
        });
        // 品牌部门
        $('#report_branddeptname_salesDemandBillReportPage').on('click', function() {

            androidWidget({
                type: 'widget',
                referwid: 'RL_T_BASE_DEPARTMENT_BUYER',
                checkType : "2",
                onCheck: 'selectBrandDept'
            });
        }).on('change', function() {
            var v = $(this).val();
            $(this).attr('value', v);
            $('#report_branddeptid_salesDemandBillReportPage').attr('value', (v || ':').split(':')[0]);
        });
        // 品牌业务员
        $('#report_brandusername_salesDemandBillReportPage').on('click', function() {

            androidWidget({
                type: 'widget',
                referwid: 'RL_T_BASE_PERSONNEL_BRANDSELLER',
                checkType : "2",
                onCheck: 'selectBrandUser'
            });
        }).on('change', function() {
            var v = $(this).val();
            $(this).attr('value', v);
            $('#report_branduserid_salesDemandBillReportPage').attr('value', (v || ':').split(':')[0]);
        });
        //销售区域
        $('#report_salesareaname_salesDemandBillReportPage').on('click', function() {

            androidWidget({
                type: 'widget',
                referwid: 'RT_T_BASE_SALES_AREA',
                checkType : "2",
                onCheck: 'selectSalesArea'
            });
        }).on('change', function() {
            var v = $(this).val();
            $(this).attr('value', v);
            $('#report_salesareaid_salesDemandBillReportPage').attr('value', (v || ':').split(':')[0]);
        });

        //销售部门
        $('#report_salesdeptname_salesDemandBillReportPage').on('click', function() {

            androidWidget({
                type: 'widget',
                name:'t_sales_order',
                col:'salesdept',
                //referwid: 'RL_T_BASE_PERSONNEL_SUPPLIER',
                onCheck: 'selectSalesDept',
                checkType : "2",
                onlyLeafCheck:'1'
            });
        }).on('change', function() {
            var v = $(this).val();
            $(this).attr('value', v);
            $('#report_salesdeptid_salesDemandBillReportPage').attr('value', (v || ':').split(':')[0]);
        });

    });
//    //客户分类
//    $('#report_customersortname_salesDemandBillReportPage').on('click', function() {
//
//        androidWidget({
//            type: 'widget',
//            referwid: 'RT_T_BASE_SALES_CUSTOMERSORT',
//            checkType : "2",
//            onCheck: 'selectCustomerSort'
//        });
//    }).on('change', function() {
//        var v = $(this).val();
//        $(this).attr('value', v);
//        $('#report_customersortid_salesDemandBillReportPage').attr('value', (v || ':').split(':')[0]);
//    });

//    //选择客户分类
//    function selectCustomerSort(data){
//        var idArr=new Array();
//        var nameArr=new Array();
//        for(var i=0;i < data.length;i++){
//            var item=data[i];
//            // if(item!=null && item.isParent=='false'){
//            idArr.push(item.id);
//            nameArr.push(item.name);
//            //   }
//        }
//        $('#report_customersortid_salesDemandBillReportPage').val("");
//        $('#report_customersortname_salesDemandBillReportPage').val("");
//        if(idArr.length>0){
//            $('#report_customersortid_salesDemandBillReportPage').val(idArr.join(','));
//            $('#report_customersortname_salesDemandBillReportPage').val(nameArr.join(','));
//        }
//    }

    //选择品牌
    function selectBrand(data){
        var idArr=new Array();
        var nameArr=new Array();
        for(var i=0;i < data.length;i++){
            var item=data[i];
            // if(item!=null && item.isParent=='false'){
            idArr.push(item.id);
            nameArr.push(item.name);
            //   }
        }
        $('#report_brandid_salesDemandBillReportPage').val("");
        $('#report_brandname_salesDemandBillReportPage').val("");
        if(idArr.length>0){
            $('#report_brandid_salesDemandBillReportPage').val(idArr.join(','));
            $('#report_brandname_salesDemandBillReportPage').val(nameArr.join(','));
        }
    }
    //选择品牌部门
    function selectBrandDept(data){
        var idArr=new Array();
        var nameArr=new Array();
        for(var i=0;i < data.length;i++){
            var item=data[i];
            // if(item!=null && item.isParent=='false'){
            idArr.push(item.id);
            nameArr.push(item.name);
            //   }
        }
        $('#report_branddeptid_salesDemandBillReportPage').val("");
        $('#report_branddeptname_salesDemandBillReportPage').val("");
        if(idArr.length>0){
            $('#report_branddeptid_salesDemandBillReportPage').val(idArr.join(','));
            $('#report_branddeptname_salesDemandBillReportPage').val(nameArr.join(','));
        }
    }

//    //选择业务员
//    function selectSalesuser(data){
//        var idArr=new Array();
//        var nameArr=new Array();
//        for(var i=0;i < data.length;i++){
//            var item=data[i];
//            // if(item!=null && item.isParent=='false'){
//            idArr.push(item.id);
//            nameArr.push(item.name);
//            //   }
//        }
//        $('#report_salesuser_salesDemandBillReportPage').val("");
//        $('#report_salesusername_salesDemandBillReportPage').val("");
//        if(idArr.length>0){
//            $('#report_salesuser_salesDemandBillReportPage').val(idArr.join(','));
//            $('#report_salesusername_salesDemandBillReportPage').val(nameArr.join(','));
//        }
//    }
    //选择客户
    function selectCustomer(data){
        var idArr=new Array();
        var nameArr=new Array();
        for(var i=0;i < data.length;i++){
            var item=data[i];
            // if(item!=null && item.isParent=='false'){
            idArr.push(item.id);
            nameArr.push(item.name);
            //   }
        }
        $('#report_customerid_salesDemandBillReportPage').val("");
        $('#report_customername_salesDemandBillReportPage').val("");
        if(idArr.length>0){
            $('#report_customerid_salesDemandBillReportPage').val(idArr.join(','));
            $('#report_customername_salesDemandBillReportPage').val(nameArr.join(','));
        }
    }
    //选择商品
    function selectGood(data){
        var idArr=new Array();
        var nameArr=new Array();
        for(var i=0;i < data.length;i++){
            var item=data[i];
            // if(item!=null && item.isParent=='false'){
            idArr.push(item.id);
            nameArr.push(item.name);
            //   }
        }
        $('#report_goodsid_salesDemandBillReportPage').val("");
        $('#report_goodsname_salesDemandBillReportPage').val("");
        if(idArr.length>0){
            $('#report_goodsid_salesDemandBillReportPage').val(idArr.join(','));
            $('#report_goodsname_salesDemandBillReportPage').val(nameArr.join(','));
        }
    }
    //选择商品业务员
    function selectBrandUser(data){
        var idArr=new Array();
        var nameArr=new Array();
        for(var i=0;i < data.length;i++){
            var item=data[i];
            // if(item!=null && item.isParent=='false'){
            idArr.push(item.id);
            nameArr.push(item.name);
            //   }
        }
        $('#report_branduserid_salesDemandBillReportPage').val("");
        $('#report_brandusername_salesDemandBillReportPage').val("");
        if(idArr.length>0){
            $('#report_branduserid_salesDemandBillReportPage').val(idArr.join(','));
            $('#report_brandusername_salesDemandBillReportPage').val(nameArr.join(','));
        }
    }

    //选择销售区域
    function selectSalesArea(data){
        var idArr=new Array();
        var nameArr=new Array();
        for(var i=0;i < data.length;i++){
            var item=data[i];
            // if(item!=null && item.isParent=='false'){
            idArr.push(item.id);
            nameArr.push(item.name);
            //   }
        }
        $('#report_salesareaid_salesDemandBillReportPage').val("");
        $('#report_salesareaname_salesDemandBillReportPage').val("");
        if(idArr.length>0){
            $('#report_salesareaid_salesDemandBillReportPage').val(idArr.join(','));
            $('#report_salesareaname_salesDemandBillReportPage').val(nameArr.join(','));
        }
    }
    //选择销售部门
    function selectSalesDept(data){
        var idArr=new Array();
        var nameArr=new Array();
        for(var i=0;i < data.length;i++){
            var item=data[i];
            // if(item!=null && item.isParent=='false'){
            idArr.push(item.id);
            nameArr.push(item.name);
            //   }
        }
        $('#report_salesdeptid_salesDemandBillReportPage').val("");
        $('#report_salesdeptname_salesDemandBillReportPage').val("");
        if(idArr.length>0){
            $('#report_salesdeptid_salesDemandBillReportPage').val(idArr.join(','));
            $('#report_salesdeptname_salesDemandBillReportPage').val(nameArr.join(','));
        }
    }
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
            url : "phone/getSalesDemandBillReportData.do", // 你请求的地址
            data : $("#report_form_salesDemandBillReportPage").serialize(),
            success : function(res){
                if(res.list.length){ // 如果后台传过来有数据执行如下操作 ， 没有就执行else 告诉用户没有更多内容呢
                    //加载数据。。。
                    var arr = new Array();
                    for (var i in res.list) {
                        arr.push("<div class='divhead' id='divhead"+listnumber+"'>["+(listnumber+1)+"]");arr.push("</div>");
                        arr.push("<li data-icon='false'  id='main-li' onclick='showOrderTrack(&apos;"+res.list[i].demandid+"&apos;,&apos;"+res.list[i].orderid+"&apos;)'>");
                        arr.push("<div id='divlist"+listnumber+"' class='report_data'>");
                        arr.push("<div class='customerid'>客户编号:");arr.push(checkStringUndefined(res.list[i].customerid));arr.push("</div>");
                        arr.push("<div class='customername'>客户名称:");arr.push(checkStringUndefined(res.list[i].customername));arr.push("</div>");
                        arr.push("<div class='orderid'>订单编号:");arr.push(checkStringUndefined(res.list[i].orderid));arr.push("</div>");
                        arr.push("<div class='sourceid'>要货单编号:");arr.push(checkStringUndefined(res.list[i].demandid));arr.push("</div>");
                        arr.push("<div class='field01'>金额:");arr.push(checkStringUndefined(res.list[i].field01));arr.push("</div>");
                        arr.push("<div class='businessdate'>业务日期");arr.push(checkStringUndefined(res.list[i].businessdate));arr.push("</div>");
                        arr.push("<div class='addtime'>制单时间:");arr.push(checkStringUndefined(changeDateStr(res.list[i].addtime,'yyyy-MM-dd hh:mm:ss')));arr.push("</div>");
                        arr.push("</div>");
//                        arr.push("<div class='div-20' id='left"+listnumber+"'>");
//                        arr.push("</div>");
//                        arr.push("<div class='center_border'></div>");
//                        arr.push("<div class='div-20' id='right"+listnumber+"'>");
//                        arr.push("</div>");
                        arr.push("<HR class='report_hr' color=#87CEFA>");
                        arr.push("</li>");
                        listnumber++;
//                        document.getElementById("main-li").setAttribute("onclick","showaaa()");
                    }
                    $('#thelist').append(arr.join(''));
                    $("#report_footer_salesDemandBillReportPage").html('');
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
                    $('#report_footer_salesDemandBillReportPage').append(arr1.join(''));
                    initDivData();//初始化隐藏div
                    setColumn();//根绝col隐藏和显示div
                    showHeader();//显示每条数据头
//                    showDataDiv("divlist","div");//将div排列成左右格式
                    nextpagenumber=listnumber;
                }else{
                    var footerHtml=$("#report_footer_salesDemandBillReportPage").html();
                    footerHtml=footerHtml.replace(/\s+/g,"");//去空格
                    if(footerHtml.replace(/(^s*)|(s*$)/g, "").length ==0){//如果没有合计就隐藏
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
            content: document.getElementById("report_footer_salesDemandBillReportPage").outerHTML,
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


    //初始化隐藏div
    function initDivData(){
        $(".customersort").css('display', 'none');
        $(".salesarea").css('display', 'none');
        $(".salesdept").css('display', 'none');
        $(".branddept").css('display', 'none');
        $(".unitname").css('display', 'none');
        $(".auxunitname").css('display', 'none');
        $(".costprice").css('display', 'none');
        $(".taxprice").css('display', 'none');
        $(".unitnum").css('display', 'none');
    }


    //控制div的显示和隐藏
    function setColumn(){
        var cols = $("#report-query-groupcols").val();
        if(cols!=""){
            $(".customerid").css('display', 'none');
            $(".customername").css('display', 'none');
            $(".salesuser").css('display', 'none');
            $(".customersort").css('display', 'none');
            $(".salesarea").css('display', 'none');
            $(".salesdept").css('display', 'none');
            $(".goodsid").css('display', 'none');
            $(".goodsname").css('display', 'none');
            $(".barcode").css('display', 'none');
            $(".brandid").css('display', 'none');
            $(".branduser").css('display', 'none');
            $(".branddept").css('display', 'none');
        }
        else{
            $(".customerid").css('display', 'block');
            $(".customername").css('display', 'block');
            $(".salesuser").css('display', 'block');
            $(".goodsid").css('display', 'block');
            $(".goodsname").css('display', 'block');
            $(".barcode").css('display', 'block');
            $(".brandid").css('display', 'block');
            $(".branduser").css('display', 'block');
        }
        var colarr = cols.split(",");
        for(var i=0;i<colarr.length;i++){
            var col = colarr[i];
            if(col=='customerid'){
                $(".customerid").css('display', 'block');
                $(".customername").css('display', 'block');
                $(".salesuser").css('display', 'block');
                $(".customersort").css('display', 'block');
            }else if(col=="salesuser"){
                $(".salesuser").css('display', 'block');
            }else if(col=="salesarea"){
                $(".salesarea").css('display', 'block');
            }else if(col=="salesdept"){
                $(".salesdept").css('display', 'block');
            }else if(col=="goodsid"){
                $(".goodsid").css('display', 'block');
                $(".goodsname").css('display', 'block');
                $(".barcode").css('display', 'block');
                $(".brandid").css('display', 'block');
            }else if(col=="brandid"){
                $(".brandid").css('display', 'block');
                //$datagrid.datagrid('showColumn', "branddept");
            }else if(col=="branduser"){
                $(".branduser").css('display', 'block');
                //$datagrid.datagrid('showColumn', "branddept");
            }else if(col=="branddept"){
                $(".branddept").css('display', 'block');
            }else if(col=="customersort"){
                $(".customersort").css('display', 'block');
            }
        }
    }
    //显示每条数据头
    function showHeader(){
        var cols = $("#report-query-groupcols").val();
        for(var j=nextpagenumber;j<listnumber;j++){
            if(cols == ""){
                cols = "customerid";
            }
            var colarr = cols.split(",");
            if(colarr[0]=='customerid'){
                var o=document.getElementById("divlist"+j).getElementsByClassName("customerid")[0];
                var o1=document.getElementById("divlist"+j).getElementsByClassName("customername")[0];
                document.getElementById("divhead"+j).appendChild(o);
                document.getElementById("divhead"+j).appendChild(o1);
            }else if(colarr[0]=="salesuser"){
                var o1=document.getElementById("divlist"+j).getElementsByClassName("salesuser")[0];
                document.getElementById("divhead"+j).appendChild(o1);
            }else if(colarr[0]=="salesarea"){
                var o1=document.getElementById("divlist"+j).getElementsByClassName("salesarea")[0];
                document.getElementById("divhead"+j).appendChild(o1);
            }
            else if(colarr[0]=="salesdept"){
                var o1=document.getElementById("divlist"+j).getElementsByClassName("salesdept")[0];
                document.getElementById("divhead"+j).appendChild(o1);
            }
            else if(colarr[0]=="goodsid"){
                var o=document.getElementById("divlist"+j).getElementsByClassName("goodsid")[0];
                var o1=document.getElementById("divlist"+j).getElementsByClassName("goodsname")[0];
                document.getElementById("divhead"+j).appendChild(o);
                document.getElementById("divhead"+j).appendChild(o1);
            }else if(colarr[0]=="brandid"){
                var o1=document.getElementById("divlist"+j).getElementsByClassName("brandid")[0];
                document.getElementById("divhead"+j).appendChild(o1);
            }else if(colarr[0]=="branduser"){
                var o1=document.getElementById("divlist"+j).getElementsByClassName("branduser")[0];
                document.getElementById("divhead"+j).appendChild(o1);
            }else if(colarr[0]=="branddept"){
                var o1=document.getElementById("divlist"+j).getElementsByClassName("branddept")[0];
                document.getElementById("divhead"+j).appendChild(o1);
            }else if(colarr[0]=="customersort") {
                var o1=document.getElementById("divlist"+j).getElementsByClassName("customersort")[0];
                document.getElementById("divhead"+j).appendChild(o1);
            }
        }
    }
    function showOrderTrack(id,orderid){
        location.hash="pagethree";
        $('#pagethree_main').html('');
        showLoader();
        $.ajax({
            type : "post",
            dataType : "JSON",
            url : "phone/showSalesDemandBillOrderTrackReportPage.do", // 你请求的地址
            data :{
                id:id,
                orderid:orderid
            },
            success : function(res){
                if(res!=null){ // 如果后台传过来有数据执行如下操作 ， 没有就执行else 告诉用户没有更多内容呢
                    //加载数据。。。
                    var arr = new Array();
                    if(checkNull(res.id)&&!checkNull(res.orderid)){//有要货单无订单
                        arr.push("<div>未查询到单据信息</div>");
                    }
                    else if(checkNull(res.id)&&checkNull(res.orderid)){//有要货单有订单
                        if(res.status=='0'){
                            arr.push("<div>未查询到单据信息</div>");
                        }
                        if(res.status=='1'){
                            arr.push("<div class='main'>");
                            if(res.orderstatus=='3'||res.orderstatus=='4'){
                                arr.push("<div class='item' style='display: block;'>"+changeDateStr(res.orderaddtime,'yyyy-MM-dd hh:mm'));arr.push("已下单</div>");//11
                                arr.push("<div id='orderidView' style='display: block;'>要货单编号："+res.id+"</div>");
                                arr.push("<div class='item' style='display: block;'>"+changeDateStr(res.orderaudittime,'yyyy-MM-dd hh:mm'));arr.push("正在配货</div>");//1
                                arr.push("<div id='dbillView' style='display: block;'>发货通知单编号："+res.dbillid+"</div>");
                                if(res.dbillstatus=='3'||res.dbillstatus=='4'){
                                    arr.push("<div class='item' style='display: block;'>"+changeDateStr(res.dbillaudittime,'yyyy-MM-dd hh:mm'));arr.push("已配货，正在出库</div>");//1
                                    arr.push("<div id='saleoutView' style='display: block;'>发货单编号："+res.outid+"</div>");
                                    if(res.outstatus=='3'||res.outstatus=='4'){
                                        arr.push("<div class='item' style='display: block;'>"+changeDateStr(res.outaudittime,'yyyy-MM-dd hh:mm'));arr.push("已出库，正在送货</div>");//1
                                        arr.push("<div id='psmsgView' ");
                                        if(res.showpsmsg==false){
                                            arr.push("style='display: block;'")
                                        }
                                        arr.push(">配送信息："+res.psmsg+"</div>");
                                        if(res.recstatus=='3'||res.recstatus=='4'){
                                            arr.push("<div class='item' style='display: block;'>"+changeDateStr(res.recaudittime,'yyyy-MM-dd hh:mm')); arr.push("客户已签收</div>");//1
                                            arr.push("<div id='receiptView' style='display: block;'>回单编号:"+res.recid+"</div>");
                                            if(res.isinvoice=='1'){
                                                arr.push("<div style='padding:10px 5px;line-height:20px;'>"+res.invoicedate+" 已开票</div>");
                                                if(res.iswriteoff=='1'){
                                                    arr.push("<div style='padding:10px 5px;line-height:20px;'>"+res.writeoffdate+" 已核销</div>");
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            else if(res.orderstatus=='2'){
                                arr.push("<div class='item' style='display: block;'>"+changeDateStr(res.orderaddtime,'yyyy-MM-dd hh:mm'));arr.push("已下单</div>");//11
                                arr.push("<div id='orderidView' style='display: block;'>要货单编号："+res.id+"</div>");
                            }
                            arr.push("<div>")
                        }
                    }
                    else if(!checkNull(res.id)&&checkNull(res.orderid)) {//无要货单有订单
                        arr.push("<div class='main'>");
                        if(res.orderstatus=='3'||res.orderstatus=='4'){
                            arr.push("<div class='item' style='display: block;'>"+changeDateStr(res.orderaddtime,'yyyy-MM-dd hh:mm'));arr.push("已下单</div>");//11
                            arr.push("<div id='orderidView' style='display: block;'>订单编号："+res.orderid+"</div>");
                            arr.push("<div class='item' style='display: block;'>"+changeDateStr(res.orderaudittime,'yyyy-MM-dd hh:mm'));arr.push("正在配货</div>");//1
                            arr.push("<div id='dbillView' style='display: block;'>发货通知单编号："+res.dbillid+"</div>");
                            if(res.dbillstatus=='3'||res.dbillstatus=='4'){
                                arr.push("<div class='item' style='display: block;'>"+changeDateStr(res.dbillaudittime,'yyyy-MM-dd hh:mm'));arr.push("已配货，正在出库</div>");//1
                                arr.push("<div id='saleoutView' style='display: block;'>发货单编号："+res.outid+"</div>");
                                if(res.outstatus=='3'||res.outstatus=='4'){
                                    arr.push("<div class='item' style='display: block;'>"+changeDateStr(res.outaudittime,'yyyy-MM-dd hh:mm'));arr.push("已出库，正在送货</div>");//1
                                    arr.push("<div id='psmsgView' ");
                                    if(res.showpsmsg==false){
                                        arr.push("style='display: none;'")
                                    }
                                    arr.push(">配送信息："+res.psmsg+"</div>");
                                    if(res.recstatus=='3'||res.recstatus=='4'){
                                        arr.push("<div class='item' style='display: block;'>"+changeDateStr(res.recaudittime,'yyyy-MM-dd hh:mm')); arr.push("客户已签收</div>");//1
                                        arr.push("<div id='receiptView' style='display: block;'>回单编号:"+res.recid+"</div>");
                                        if(res.isinvoice=='1'){
                                            arr.push("<div style='padding:10px 5px;line-height:20px;'>"+res.invoicedate+" 已开票</div>");
                                            if(res.iswriteoff=='1'){
                                                arr.push("<div style='padding:10px 5px;line-height:20px;'>"+res.writeoffdate+" 已核销</div>");
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if(res.orderstatus=='2'){
                            arr.push("<div class='item' style='display: block;'>"+changeDateStr(res.orderaddtime,'yyyy-MM-dd hh:mm'));arr.push("已下单</div>");//11
                            arr.push("<div id='orderidView' style='display: block;'>订单编号："+res.orderid+"</div>");
                        }
                        arr.push("<div>")
                    }
                    $('#pagethree_main').append(arr.join(''));
                }
                hideLoader();
//                myScroll.refresh();
            },
            error : function(){
                hideLoader();
            }
        });
    }
    function showOrderList(){
        var target = document.getElementById("orderidView");
        if(target.style.display=="block"){
            target.style.display="none";
        }else{
            target.style.display="block";
        }
    }
    function showDbillList(){
        var target = document.getElementById("dbillView");
        if(target.style.display=="block"){
            target.style.display="none";
        }else{
            target.style.display="block";
        }
    }
    function showSaleoutList(){
        var target = document.getElementById("saleoutView");
        if(target.style.display=="block"){
            target.style.display="none";
        }else{
            target.style.display="block";
        }
    }
    function showSeceiptList(){
        var target = document.getElementById("receiptView");
        if(target.style.display=="block"){
            target.style.display="none";
        }else{
            target.style.display="block";
        }
    }
    function showPsmsgList(){
        var target = document.getElementById("psmsgView");
        if(target.style.display=="block"){
            target.style.display="none";
        }else{
            target.style.display="block";
        }
    }
    function callAndroid(name,mobile){
        window.agent.call(name,mobile);
    }
    function checkNull(str){
        if(str=='')
            return false;
        if(str==null)
            return false;
        return true;
    }
    function changeDateStr(d,datestr){
        //去掉时间字符串里的字母T
        var date=new Date(Date.parse(d.replace(/[a-zA-Z]/g,"/"))).Format(datestr);
        return date;
    }

</script>
</body>
</html>
