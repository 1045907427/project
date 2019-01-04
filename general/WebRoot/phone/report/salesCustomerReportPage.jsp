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
        #report_footer_salesCustomerReportPage{
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
        <h1 align="center">分客户销售情况统计</h1>
    </div>

    <div data-role="main" class="ui-content">
        <div style="position:absolute;width:90%;height:82%;overFlow-x:hidden;overFlow-y:scroll;">
            <form method="post"  class="form-horizontal" role="form" id="report_form_salesCustomerReportPage" data-ajax="false"  action="phone/showBaseFinanceDrawnDataPage.do">
                <div class="form-group">
                    <label class="col-sm-2 control-label">开始日期</label>
                    <div class="col-sm-10">
                        <input type="date" class="form-control" name="businessdate1" id="report_startdate_salesCustomerReportPage" value="${today}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">结束日期</label>
                    <div class="col-sm-10">
                        <input class="form-control"  type="date" name="businessdate2" id="report_enddate_salesCustomerReportPage" value="${today}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">客户</label>
                    <div class="col-sm-10">
                        <input type="text" name="customername" id="report_customername_salesCustomerReportPage" readonly="readonly" data-clear-btn="true"/>
                        <input type="hidden" name="customerid" id="report_customerid_salesCustomerReportPage"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">总店</label>
                    <div class="col-sm-10">
                        <input type="text" class="required" name="pcustomername" id="report_pcustomername_salesCustomerReportPage" value="" readonly="readonly" data-clear-btn="true"/>
                        <input type="hidden" name="pcustomerid" id="report_pcustomerid_salesCustomerReportPage"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">销售区域</label>
                    <div class="col-sm-10">
                        <input type="text" class="required" name="salesareaname" id="report_salesareaname_salesCustomerReportPage" value="" readonly="readonly" data-clear-btn="true"/>
                        <input type="hidden" name="salesarea" id="report_salesareaid_salesCustomerReportPage"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">销售部门</label>
                    <div class="col-sm-10">
                        <input type="text" class="required" name="salesdeptname" id="report_salesdeptname_salesCustomerReportPage" value="" readonly="readonly" data-clear-btn="true"/>
                        <input type="hidden" name="salesdept" id="report_salesdeptid_salesCustomerReportPage"/>
                    </div>
                </div>
                <div class="form-group" style="display: none">
                    <label class="col-sm-2 control-label">小计列：</label>
                    <div class="col-sm-10">
                        <select id="cols" name="cols" multiple class="form-control">
                            <option class="groupcols" value="" selected></option>
                            <option class="groupcols" value="customerid">客户</option>
                            <option class="groupcols" value="pcustomerid">总店</option>
                            <option class="groupcols" value="salesarea">销售区域</option>
                            <option class="groupcols" value="salesdept">销售部门</option>
                        </select>
                        <input id="report-query-groupcols" type="hidden" name="groupcols"/>
                    </div>
                </div>
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

<script type="text/javascript">
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
        setTimeout(getSearchData,"500");//延迟调用，为了让页面先跳转再显示加载数据
        var querystring = $("#report_form_salesCustomerReportPage").serialize();
        location.href="phone/showSalesCustomerReportDataPage.do?"+querystring;
    }
    function getSearchData(){
        var cols=$("#cols").val();
        $("#report-query-groupcols").val(cols);
    }
    function reset(){
        //参照窗口需要手动清除
        $('#report_customername_salesCustomerReportPage').val( '');
        $('#report_customername_salesCustomerReportPage').change();
        $('#report_pcustomername_salesCustomerReportPage').val( '');
        $('#report_pcustomername_salesCustomerReportPage').change();
        $('#report_salesareaname_salesCustomerReportPage').val( '');
        $('#report_salesareaname_salesCustomerReportPage').change();
        $('#report_salesdeptname_salesCustomerReportPage').val( '');
        $('#report_salesdeptname_salesCustomerReportPage').change();

        document.getElementById("report_form_salesCustomerReportPage").reset();
    }
    $(function(){

        // 客户
        $('#report_customername_salesCustomerReportPage').on('click', function() {
            androidWidget({
                type: 'widget',
                referwid: 'RL_T_BASE_SALES_CUSTOMER_PARENT_2',
                onSelect: 'selectCustomer',
                checkType :1
            });
        }).on('change', function(e) {
            var v = $(this).val();
            $(this).attr('value', v);
            $('#report_customerid_salesCustomerReportPage').attr('value', (v || ':').split(':')[0]);
        });
        // 总店
        $('#report_pcustomername_salesCustomerReportPage').on('click', function() {
            androidWidget({
                type: 'widget',
                referwid : 'RL_T_BASE_SALES_CUSTOMER_PARENT',
                checkType : "1",
                onSelect: 'selectPcustomer'
            });
        }).on('change', function(e) {
            var v = $(this).val();
            $(this).attr('value', v);
            $('#report_pcustomerid_salesCustomerReportPage').attr('value', (v || ':').split(':')[0]);
        });
        //销售区域
        $('#report_salesareaname_salesCustomerReportPage').on('click', function() {

            androidWidget({
                type: 'widget',
                referwid: 'RT_T_BASE_SALES_AREA',
                checkType : "1",
                onSelect: 'selectSalesArea'
            });
        }).on('change', function() {
            var v = $(this).val();
            $(this).attr('value', v);
            $('#report_salesareaid_salesCustomerReportPage').attr('value', (v || ':').split(':')[0]);
        });

        //销售部门
        $('#report_salesdeptname_salesCustomerReportPage').on('click', function() {

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
            $('#report_salesdeptid_salesCustomerReportPage').attr('value', (v || ':').split(':')[0]);
        });

    });

    //选择总店
    function selectPcustomer(data){
        $('#report_pcustomerid_salesCustomerReportPage').val(data.id);
        $('#report_pcustomername_salesCustomerReportPage').val(data.id + ':' + data.name);
//        $('#report_pcustomername_salesCustomerReportPage').blur();
//        $('#report_pcustomername_salesCustomerReportPage').change();
    }
    //选择客户
    function selectCustomer(data){
        $('#report_customerid_salesCustomerReportPage').val(data.id);
        $('#report_customername_salesCustomerReportPage').val(data.id + ':' + data.name);
//        $('#report_customername_salesCustomerReportPage').blur();
//        $('#report_customername_salesCustomerReportPage').change();
    }
    //选择销售区域
    function selectSalesArea(data){
        $('#report_salesareaid_salesCustomerReportPage').val(data.id);
        $('#report_salesareaname_salesCustomerReportPage').val(data.id + ':' + data.name);
//        $('#report_salesareaname_salesCustomerReportPage').blur();
//        $('#report_salesareaname_salesCustomerReportPage').change();
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
        $('#report_salesdeptid_salesCustomerReportPage').val("");
        $('#report_salesdeptname_salesCustomerReportPage').val("");
        if(idArr.length>0){
            $('#report_salesdeptid_salesCustomerReportPage').val(idArr.join(','));
            $('#report_salesdeptname_salesCustomerReportPage').val(nameArr.join(','));
        }
    }
</script>
</body>
</html>
