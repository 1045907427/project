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
<div data-role="page" id="pageone" >
    <div data-role="header" data-position="fixed">
        <a href="javascript:location.href='phone/showPhoneReportListPage.do';" data-rel="back"  class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a>
        <h1 align="center">缺货商品统计</h1>
    </div>

    <div data-role="main" class="ui-content">
        <div style="position:absolute;width:90%;height:82%;overFlow-x:hidden;overFlow-y:scroll;">
            <form method="post"  class="form-horizontal" role="form" id="report_form_goodsOutReportPage" data-ajax="false" action="phone/showSalesOrderTrackReportData.do">
                <%--<div class="form-group">--%>
                    <%--<label class="col-sm-2 control-label" for="report_orderid_goodsOutReportPage">订单编号</label>--%>
                    <%--<div class="col-sm-10 ">--%>
                        <%--<input type="text" name="id" id="report_orderid_goodsOutReportPage" class="form-control">--%>
                    <%--</div>--%>
                <%--</div>--%>
                <%--<div class="form-group">--%>
                    <%--<label class="col-sm-2 control-label">客户单号</label>--%>
                    <%--<div class="col-sm-10" style="display: inline;" >--%>
                        <%--<input type="text" class="form-control" name="sourceid" id="report_sourceid_goodsOutReportPage">--%>
                    <%--</div>--%>
                <%--</div>--%>
                <div class="form-group">
                    <label class="col-sm-2 control-label">开始日期</label>
                    <div class="col-sm-10">
                        <input type="date" class="form-control" name="begindate" id="report_startdate_goodsOutReportPage" value="${date}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">结束日期</label>
                    <div class="col-sm-10">
                        <input class="form-control"  type="date" name="enddate" id="report_enddate_goodsOutReportPage">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">状态</label>
                    <select class="form-control" id="report_state_goodsOutReportPage"  name="issupply">
                        <option value="0" selected="selected">未补单</option>
                        <option value="1">已补单</option>
                    </select>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">商品名称</label>
                    <div class="col-sm-10">
                        <input type="text" class="required" name="goodsname" id="report_goodsname_goodsOutReportPage"  readonly="readonly" data-clear-btn="true"/>
                        <input type="hidden" name="goodsid" id="report_goodsid_goodsOutReportPage" />
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">品牌名称</label>
                    <div class="col-sm-10">
                        <input type="text" class="required" name="brandname" id="report_brandname_goodsOutReportPage"  readonly="readonly" data-clear-btn="true"/>
                        <input type="hidden" name="brandid" id="report_brandid_goodsOutReportPage"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">客户名称</label>
                    <div class="col-sm-10">
                        <input type="text" name="customername" id="report_customername_goodsOutReportPage" readonly="readonly" data-clear-btn="true"/>
                        <input type="hidden" name="customerid" id="report_customerid_goodsOutReportPage"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">总店</label>
                    <div class="col-sm-10">
                        <input type="text" class="required" name="pcustomername" id="report_pcustomername_goodsOutReportPage" value="" readonly="readonly" data-clear-btn="true"/>
                        <input type="hidden" name="pid" id="report_pcustomerid_goodsOutReportPage"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">客户业务员</label>
                    <div class="col-sm-10">
                        <input type="text" class="required" name="salesusername" id="report_salesusername_goodsOutReportPage" value="" readonly="readonly" data-clear-btn="true"/>
                        <input type="hidden" name="salesuser" id="report_salesuser_goodsOutReportPage"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">供应商</label>
                    <div class="col-sm-10">
                        <input type="text" class="required" name="suppliername" id="report_suppliername_goodsOutReportPage" value="" readonly="readonly" data-clear-btn="true"/>
                        <input type="hidden" name="supplierid" id="report_supplierid_goodsOutReportPage"/>
                    </div>
                </div>
                <%--<input id="report-query-page" type="hidden" name="page"/>--%>
            </form>
        </div>
    </div>
    <div style="position:fixed;bottom:0px;width: 100%;" id="footer">
        <div align="center">
            <a class="ui-btn ui-shadow ui-corner-all" href="javascript:void(0);"  onclick="searchData()" style="width: 30%;display: inline-block;">查询 </a>
            <a class="ui-btn ui-shadow ui-corner-all" href="javascript:void(0);"  onclick="reset()" style="width: 30%;display: inline-block;">重置 </a>
        </div>
    </div>
</div>

<script type="text/javascript">
    function searchData() {
        var querystring = $("#report_form_goodsOutReportPage").serialize();
        location.href="phone/showGoodsOutReportDataPage.do?"+querystring;
    }

    function reset(){
        //参照窗口需要手动清除
        $('#report_goodsname_goodsOutReportPage').val( '');
        $('#report_goodsname_goodsOutReportPage').change();
        $('#report_brandname_goodsOutReportPage').val( '');
        $('#report_brandname_goodsOutReportPage').change();
        $('#report_customername_goodsOutReportPage').val( '');
        $('#report_customername_goodsOutReportPage').change();
        $('#report_salesusername_goodsOutReportPage').val( '');
        $('#report_salesusername_goodsOutReportPage').change();
        $('#report_pcustomername_goodsOutReportPage').val( '');
        $('#report_pcustomername_goodsOutReportPage').change();
        $('#report_indoorusername_goodsOutReportPage').val( '');
        $('#report_indoorusername_goodsOutReportPage').change();
        document.getElementById("report_form_goodsOutReportPage").reset();
    }
    $(document).ready(function(){
        // 客户
        $('#report_customername_goodsOutReportPage').on('click', function() {
            androidWidget({
                type: 'widget',
                referwid: 'RL_T_BASE_SALES_CUSTOMER_PARENT_2',
                onSelect: 'selectCustomer',
                checkType:"1"
            });
        }).on('change', function(e) {
            var v = $(this).val();
            $(this).attr('value', v);
            $('#report_customerid_goodsOutReportPage').attr('value', (v || ':').split(':')[0]);
        });
        // 总店
        $('#report_pcustomername_goodsOutReportPage').on('click', function() {
            androidWidget({
                type: 'widget',
                referwid : 'RL_T_BASE_SALES_CUSTOMER_PARENT',
                onSelect: 'selectPcustomer',
                checkType:"1"
            });
        }).on('change', function(e) {
            var v = $(this).val();
            $(this).attr('value', v);
            $('#report_pcustomerid_goodsOutReportPage').attr('value', (v || ':').split(':')[0]);
        });
        // 商品
        $('#report_goodsname_goodsOutReportPage').on('click', function() {
            androidWidget({
                type: 'goodsWidget',
                onSelect: 'selectGood',
                checkType:"1"
            });

        }).on('change', function(e) {
            var v = $(this).val();
            $(this).attr('value', v);
            $('#report_goodsid_goodsOutReportPage').attr('value', (v || ':').split(':')[0]);
        });
        // 业务员
        $('#report_salesusername_goodsOutReportPage').on('click', function() {
            androidWidget({
                type: 'widget',
                referwid : "RL_T_BASE_PERSONNEL_CLIENTSELLER",
                checkType : "1",
                onCheck: 'selectSalesuser'
            });

        }).on('change', function(e) {
            var v = $(this).val();
            $(this).attr('value', v);
            $('#report_salesuser_goodsOutReportPage').attr('value', (v || ':').split(':')[0]);
        });
        // 供应商
        $('#report_suppliername_goodsOutReportPage').on('click', function() {
            androidWidget({
                type: 'widget',
                referwid : "RL_T_BASE_BUY_SUPPLIER",
                checkType : "1",
                onCheck: 'selectSupplierid'
            });

        }).on('change', function(e) {
            var v = $(this).val();
            $(this).attr('value', v);
            $('#report_supplierid_goodsOutReportPage').attr('value', (v || ':').split(':')[0]);
        });
        // 品牌
        $('#report_brandname_goodsOutReportPage').on('click', function() {

            androidWidget({
                type: 'widget',
                referwid: 'RL_T_BASE_GOODS_BRAND',
                checkType : "1",
                onCheck: 'selectBrand'
            });
        }).on('change', function() {
            var v = $(this).val();
            $(this).attr('value', v);
            $('#report_brandid_goodsOutReportPage').attr('value', (v || ':').split(':')[0]);
        });
    });

    //选择品牌
    function selectBrand(data){
        $('#report_brandid_goodsOutReportPage').val(data.id);
        $('#report_brandname_goodsOutReportPage').val(data.id + ':' + data.name);
//        $('#report_brandname_goodsOutReportPage').blur();
//        $('#report_brandname_goodsOutReportPage').change();
    }
    //选择供应商
    function selectSupplierid(data){
        $('#report_supplierid_goodsOutReportPage').val(data.id);
        $('#report_suppliername_goodsOutReportPage').val(data.id + ':' + data.name);
//        $('#report_indoorusername_goodsOutReportPage').blur();
//        $('#report_indoorusername_goodsOutReportPage').change();
    }

    //选择业务员
    function selectSalesuser(data){
        $('#report_salesuser_goodsOutReportPage').val(data.id);
        $('#report_salesusername_goodsOutReportPage').val(data.id + ':' + data.name);
//        $('#report_salesusername_goodsOutReportPage').blur();
//        $('#report_salesusername_goodsOutReportPage').change();
    }
    //选择总店
    function selectPcustomer(data){
        $('#report_pcustomername_goodsOutReportPage').val(data.id + ':' + data.name);
        $('#report_pcustomerid_goodsOutReportPage').val(data.id);
//        $('#report_pcustomername_goodsOutReportPage').blur();
//        $('#report_pcustomername_goodsOutReportPage').change();
    }
    //选择客户
    function selectCustomer(data){
        $('#report_customername_goodsOutReportPage').val(data.id + ':' + data.name);
        $('#report_customerid_goodsOutReportPage').val(data.id);
//        $('#report_customername_goodsOutReportPage').blur();
//        $('#report_customername_goodsOutReportPage').change();
    }
    //选择商品
    function selectGood(data){
        $('#report_goodsname_goodsOutReportPage').val(data.id + ':' + data.name);
        $('#report_goodsid_goodsOutReportPage').val(data.id);
//        $('#report_goodsname_goodsOutReportPage').blur();
//        $('#report_goodsname_goodsOutReportPage').change();
    }

</script>
</body>

</html>

