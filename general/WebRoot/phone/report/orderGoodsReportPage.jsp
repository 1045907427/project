<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
        #report_footer_orderGoodsReportPage{
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
<div data-role="page" id="pageone" >
    <div data-role="header" data-position="fixed">
        <a href="javascript:location.href='phone/showPhoneReportListPage.do';" data-rel="back"  class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a>
        <h1 align="center">订货单报表</h1>
    </div>

    <div data-role="main" class="ui-content">
        <div style="position:absolute;width:90%;height:71%;overFlow-x:hidden;overFlow-y:scroll;">
            <form method="post"  class="form-horizontal" role="form" id="report_form_orderGoodsReportPage" data-ajax="false"  action="phone/showBaseFinanceDrawnDataPage.do">
                <div class="form-group">
                    <label class="col-sm-2 control-label">开始日期</label>
                    <div class="col-sm-10">
                        <input type="date" class="form-control" name="businessdate" id="report_startdate_orderGoodsReportPage" value="${today}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">结束日期</label>
                    <div class="col-sm-10">
                        <input class="form-control"  type="date" name="businessdate1" id="report_enddate_orderGoodsReportPage" value="${today}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">客户</label>
                    <div class="col-sm-10">
                        <input type="text" name="customername" id="report_customername_orderGoodsReportPage" readonly="readonly" data-clear-btn="true"/>
                        <input type="hidden" name="customerid" id="report_customerid_orderGoodsReportPage"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">商品</label>
                    <div class="col-sm-10">
                        <input type="text" class="required" name="goodsname" id="report_goodsname_orderGoodsReportPage"  readonly="readonly" data-clear-btn="true"/>
                        <input type="hidden" name="goodsid" id="report_goodsid_orderGoodsReportPage" />
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">销售部门</label>
                    <div class="col-sm-10">
                        <input type="text" class="required" name="salesdeptname" id="report_salesdeptname_orderGoodsReportPage" value="" readonly="readonly" data-clear-btn="true"/>
                        <input type="hidden" name="salesdept" id="report_salesdeptid_orderGoodsReportPage"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">订货单编号</label>
                    <div class="col-sm-10">
                        <input  name="ordergoodsid" id="report_ordergoodsid_orderGoodsReportPage"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">品牌</label>
                    <div class="col-sm-10">
                        <input type="text" class="required" name="brandname" id="report_brandname_orderGoodsReportPage"  readonly="readonly" data-clear-btn="true"/>
                        <input type="hidden" name="brandid" id="report_brandid_orderGoodsReportPage"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">商品分类</label>
                    <div class="col-sm-10">
                        <input type="text" class="required" name="goodssortname" id="report_goodssortname_orderGoodsReportPage" value="" readonly="readonly" data-clear-btn="true"/>
                        <input type="hidden" name="goodssort" id="report_goodssortid_orderGoodsReportPage"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">订单编号</label>
                    <div class="col-sm-10">
                        <input  name="orderid" id="report_orderid_orderGoodsReportPage"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">是否完成</label>
                    <div class="col-sm-10">
                        <select id="report_iscomplete_orderGoodsReportPage" name="iscomplete">
                            <option></option>
                            <option value="0">未完成</option>
                            <option value="1">已完成</option>
                        </select>
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
        var querystring = $("#report_form_orderGoodsReportPage").serialize();
        location.href="phone/showOrderGoodsReportDataPage.do?"+querystring;
    }
    function reset(){
        //参照窗口需要手动清除
        $('#report_customername_orderGoodsReportPage').val( '');
        $('#report_customername_orderGoodsReportPage').change();
        $('#report_brandname_orderGoodsReportPage').val( '');
        $('#report_brandname_orderGoodsReportPage').change();
        $('#report_goodsname_orderGoodsReportPage').val( '');
        $('#report_goodsname_orderGoodsReportPage').change();
        $('#report_salesdeptname_orderGoodsReportPage').val( '');
        $('#report_salesdeptname_orderGoodsReportPage').change();
        $('#report_goodssortname_orderGoodsReportPage').val( '');
        $('#report_goodssortname_orderGoodsReportPage').change();

        document.getElementById("report_form_orderGoodsReportPage").reset();
    }
    var SR_footerobject  = null;
    var initQueryJSON = $("#report-query-form-orderGoodsReportPage").serializeJSON();
    $(function(){

        // 客户
        $('#report_customername_orderGoodsReportPage').on('click', function() {
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
            $('#report_customerid_orderGoodsReportPage').attr('value', (v || ':').split(':')[0]);
        });
        // 商品
        $('#report_goodsname_orderGoodsReportPage').on('click', function() {
            androidWidget({
                type: 'goodsWidget',
                checkType : "2",
                onSelect: 'selectGood'
            });

        }).on('change', function(e) {
            var v = $(this).val();
            $(this).attr('value', v);
            $('#report_goodsid_orderGoodsReportPage').attr('value', (v || ':').split(':')[0]);
        });
        // 品牌
        $('#report_brandname_orderGoodsReportPage').on('click', function() {

            androidWidget({
                type: 'widget',
                referwid: 'RL_T_BASE_GOODS_BRAND',
                checkType : "2",
                onCheck: 'selectBrand'
            });
        }).on('change', function() {
            var v = $(this).val();
            $(this).attr('value', v);
            $('#report_brandid_orderGoodsReportPage').attr('value', (v || ':').split(':')[0]);
        });

        //销售部门
        $('#report_salesdeptname_orderGoodsReportPage').on('click', function() {

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
            $('#report_salesdeptid_orderGoodsReportPage').attr('value', (v || ':').split(':')[0]);
        });

    });
    //商品分类
    $('#report_goodssortname_orderGoodsReportPage').on('click', function() {

        androidWidget({
            type: 'widget',
            referwid: 'RL_T_BASE_GOODS_WARESCLASS',
            checkType : "2",
            onCheck: 'selectGoodsSort'
        });
    }).on('change', function() {
        var v = $(this).val();
        $(this).attr('value', v);
        $('#report_goodssortid_orderGoodsReportPage').attr('value', (v || ':').split(':')[0]);
    });

    //选择商品分类
    function selectGoodsSort(data){
        var idArr=new Array();
        var nameArr=new Array();
        for(var i=0;i < data.length;i++){
            var item=data[i];
            // if(item!=null && item.isParent=='false'){
            idArr.push(item.id);
            nameArr.push(item.name);
            //   }
        }
        $('#report_goodssortid_orderGoodsReportPage').val("");
        $('#report_goodssortname_orderGoodsReportPage').val("");
        if(idArr.length>0){
            $('#report_goodssortid_orderGoodsReportPage').val(idArr.join(','));
            $('#report_goodssortname_orderGoodsReportPage').val(nameArr.join(','));
        }
    }

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
        $('#report_brandid_orderGoodsReportPage').val("");
        $('#report_brandname_orderGoodsReportPage').val("");
        if(idArr.length>0){
            $('#report_brandid_orderGoodsReportPage').val(idArr.join(','));
            $('#report_brandname_orderGoodsReportPage').val(nameArr.join(','));
        }
    }
    
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
        $('#report_customerid_orderGoodsReportPage').val("");
        $('#report_customername_orderGoodsReportPage').val("");
        if(idArr.length>0){
            $('#report_customerid_orderGoodsReportPage').val(idArr.join(','));
            $('#report_customername_orderGoodsReportPage').val(nameArr.join(','));
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
        $('#report_goodsid_orderGoodsReportPage').val("");
        $('#report_goodsname_orderGoodsReportPage').val("");
        if(idArr.length>0){
            $('#report_goodsid_orderGoodsReportPage').val(idArr.join(','));
            $('#report_goodsname_orderGoodsReportPage').val(nameArr.join(','));
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
        $('#report_salesdeptid_orderGoodsReportPage').val("");
        $('#report_salesdeptname_orderGoodsReportPage').val("");
        if(idArr.length>0){
            $('#report_salesdeptid_orderGoodsReportPage').val(idArr.join(','));
            $('#report_salesdeptname_orderGoodsReportPage').val(nameArr.join(','));
        }
    }


</script>
</body>
</html>
