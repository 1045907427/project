<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
        #report_footer_baseSalesReportPage{
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
        <h1 align="center">销售情况统计</h1>
    </div>

    <div data-role="main" class="ui-content">
        <div style="position:absolute;width:90%;height:71%;overFlow-x:hidden;overFlow-y:scroll;">
            <form method="post"  class="form-horizontal" role="form" id="report_form_baseSalesReportPage" data-ajax="false"  action="phone/showBaseSalesReportDataPage.do">
                <div class="form-group">
                    <label class="col-sm-2 control-label">开始日期</label>
                    <div class="col-sm-10">
                        <input type="date" class="form-control" name="businessdate1" id="report_startdate_baseSalesReportPage" value="${today}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">结束日期</label>
                    <div class="col-sm-10">
                        <input class="form-control"  type="date" name="businessdate2" id="report_enddate_baseSalesReportPage" value="${today}">
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">客户</label>
                    <div class="col-sm-10">
                        <input type="text" name="customername" id="report_customername_baseSalesReportPage" readonly="readonly" data-clear-btn="true"/>
                        <input type="hidden" name="customerid" id="report_customerid_baseSalesReportPage"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">总店</label>
                    <div class="col-sm-10">
                        <input type="text" class="required" name="pcustomername" id="report_pcustomername_baseSalesReportPage" value="" readonly="readonly" data-clear-btn="true"/>
                        <input type="hidden" name="pcustomerid" id="report_pcustomerid_baseSalesReportPage"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">品牌</label>
                    <div class="col-sm-10">
                        <input type="text" class="required" name="brandname" id="report_brandname_baseSalesReportPage"  readonly="readonly" data-clear-btn="true"/>
                        <input type="hidden" name="brandid" id="report_brandid_baseSalesReportPage"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">品牌部门</label>
                    <div class="col-sm-10">
                        <input type="text" class="required" name="branddeptname" id="report_branddeptname_baseSalesReportPage"  readonly="readonly" data-clear-btn="true"/>
                        <input type="hidden" name="branddept" id="report_branddeptid_baseSalesReportPage"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">品牌业务员</label>
                    <div class="col-sm-10">
                        <input type="text" class="required" name="brandusername" id="report_brandusername_baseSalesReportPage"  readonly="readonly" data-clear-btn="true"/>
                        <input type="hidden" name="branduser" id="report_branduserid_baseSalesReportPage"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">商品</label>
                    <div class="col-sm-10">
                        <input type="text" class="required" name="goodsname" id="report_goodsname_baseSalesReportPage"  readonly="readonly" data-clear-btn="true"/>
                        <input type="hidden" name="goodsid" id="report_goodsid_baseSalesReportPage" />
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">商品分类</label>
                    <div class="col-sm-10">
                        <input type="text" class="required" name="goodssortname" id="report_goodssortname_baseSalesReportPage"  readonly="readonly" data-clear-btn="true"/>
                        <input type="hidden" name="goodssort" id="report_goodssortid_baseSalesReportPage" />
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">商品类型</label>
                    <select class="form-control" id="report_goodstype_salesOrderTrackReportPage" name="goodstype"  multiple class="form-control">
                        <option></option>
                        <c:forEach items="${goodstypeList }" var="list">
                            <option value="${list.code }">${list.codename }</option>
                        </c:forEach>
                    </select>
                    </select>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">销售区域</label>
                    <div class="col-sm-10">
                        <input type="text" class="required" name="salesareaname" id="report_salesareaname_baseSalesReportPage" value="" readonly="readonly" data-clear-btn="true"/>
                        <input type="hidden" name="salesarea" id="report_salesareaid_baseSalesReportPage"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">客户分类</label>
                    <div class="col-sm-10">
                        <input type="text" class="required" name="customersortname" id="report_customersortname_baseSalesReportPage" value="" readonly="readonly" data-clear-btn="true"/>
                        <input type="hidden" name="customersort" id="report_customersortid_baseSalesReportPage"/>
                    </div>
                </div>

                <div class="form-group">
                    <label class="col-sm-2 control-label">客户业务员</label>
                    <div class="col-sm-10">
                        <input type="text" class="required" name="salesusername" id="report_salesusername_baseSalesReportPage" value="" readonly="readonly" data-clear-btn="true"/>
                        <input type="hidden" name="salesuser" id="report_salesuser_baseSalesReportPage"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">供应商</label>
                    <div class="col-sm-10">
                        <input type="text" class="required" name="suppliername" id="report_suppliername_baseSalesReportPage" value="" readonly="readonly" data-clear-btn="true"/>
                        <input type="hidden" name="supplierid" id="report_supplierid_baseSalesReportPage"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">厂家业务员</label>
                    <div class="col-sm-10">
                        <input type="text" class="required" name="supplierusername" id="report_supplierusername_baseSalesReportPage" value="" readonly="readonly" data-clear-btn="true"/>
                        <input type="hidden" name="supplieruser" id="report_supplieruserid_baseSalesReportPage"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">销售部门</label>
                    <div class="col-sm-10">
                        <input type="text" class="required" name="salesdeptname" id="report_salesdeptname_baseSalesReportPage" value="" readonly="readonly" data-clear-btn="true"/>
                        <input type="hidden" name="salesdept" id="report_salesdeptid_baseSalesReportPage"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="col-sm-2 control-label">仓库</label>
                    <div class="col-sm-10">
                        <input type="text" class="required" name="storagename" id="report_storagename_baseSalesReportPage" value="" readonly="readonly" data-clear-btn="true"/>
                        <input type="hidden" name="storageid" id="report_storageid_baseSalesReportPage"/>
                    </div>
                </div>
                <input id="report-query-groupcols" type="hidden" name="groupcols"/>
                <input id="report-query-groupcolsnum" type="hidden" name="groupcolsnum"/>
            </form>
        </div>
    </div>
    <div style="position:fixed;bottom:0px;width: 100%;" id="footer">
        <div   class="ui-content" style=" position: relative;top: 20px;">
            <div class="form-group">
                <label class="col-sm-2 control-label">小计列：</label>
                <div class="col-sm-10">
                    <select id="cols" name="cols" multiple class="form-control">
                        <option class="groupcols"  value="" <c:if test="${fn:indexOf(collist,'0')==1}">selected</c:if>></option>
                        <option class="groupcols"  value="customerid" <c:if test="${fn:contains(collist,'1customerid')}">selected</c:if>>客户</option>
                        <option class="groupcols"  value="pcustomerid" <c:if test="${fn:contains(collist,'2pcustomerid')}">selected</c:if>>总店</option>
                        <option class="groupcols"  value="salesarea" <c:if test="${fn:contains(collist,'3salesarea')}">selected</c:if>>销售区域</option>
                        <option class="groupcols"  value="salesuser" <c:if test="${fn:contains(collist,'4salesuser')}">selected</c:if>>客户业务员</option>
                        <option class="groupcols"  value="salesdept" <c:if test="${fn:contains(collist,'5salesdept')}">selected</c:if>>销售部门</option>
                        <option class="groupcols" value="storageid"  <c:if test="${fn:contains(collist,'6storageid')}">selected</c:if>>仓库</option>
                        <option class="groupcols"  value="goodsid" <c:if test="${fn:contains(collist,'7goodsid')}">selected</c:if>>商品</option>
                        <option class="groupcols"  value="brandid" <c:if test="${fn:contains(collist,'8brandid')}">selected</c:if>>品牌</option>
                        <option class="groupcols"  value="goodssort" <c:if test="${fn:contains(collist,'9goodssort')}">selected</c:if>>商品分类</option>
                        <option class="groupcols"  value="branddept" <c:if test="${fn:contains(collist,'10branddept')}">selected</c:if>>品牌部门</option>
                        <option class="groupcols"  value="branduser" <c:if test="${fn:contains(collist,'11branduser')}">selected</c:if>>品牌业务员</option>
                        <option class="groupcols"  value="supplieruser" <c:if test="${fn:contains(collist,'12supplieruser')}">selected</c:if>>厂家业务员</option>
                        <option class="groupcols" value="supplierid" <c:if test="${fn:contains(collist,'13supplierid')}">selected</c:if>>供应商</option>
                    </select>
                </div>
            </div>
        </div>
        <div align="center">
            <a class="ui-btn ui-shadow ui-corner-all" href="javascript:void(0);"  onclick="searchData()" style="width: 30%;display: inline-block;">查询 </a>
            <a class="ui-btn ui-shadow ui-corner-all" href="javascript:void(0);"  onclick="reset()" style="width: 30%;display: inline-block;">重置 </a>
        </div>
    </div>
</div>

<script type="text/javascript">
    $("#report_goodstype_salesOrderTrackReportPage").change(function(){
        var cols=$(this).val();
        var colsarr=new Array();
        colsarr=cols;
        if(colsarr.length>0){
            if(colsarr[0]=='')
                colsarr.splice(0,1);
        }
        $("#report_goodstype_salesOrderTrackReportPage").val(colsarr);
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
    function getGroupcolData(){//查询的时候获取groupcols用来保存到cookie，用顺序+value用来区分
        var col='';
        var num='0';
        var length=document.getElementById("cols").options.length;
        for(var i=0;i<length;i++){
            var select=document.getElementById("cols").options[i].selected;
            var value=document.getElementById("cols").options[i].value;
            if(select){
                if(col==''){
                    col=value;
                    if(num=='0'){
                        num=i+value;
                    }
                    else{
                        num=num+","+i+value;
                    }
                }
                else{
                    col=col+','+value;
                    if(num=='0'){
                        num=i+value;
                    }
                    else{
                        num=num+","+i+value;
                    }
                }
            }
        }
        $("#report-query-groupcolsnum").val(num);
        return col;
    }
    function searchData() {
        var cols=getGroupcolData();
        var colsarr=cols.split(",");
        $("#cols").val(colsarr);
        var cols=$("#cols").val();
        $("#report-query-groupcols").val(cols);
        var querystring = $("#report_form_baseSalesReportPage").serialize();
        location.href="phone/showBaseSalesReportDataPage.do?"+querystring;
    }
    function reset(){
        //参照窗口需要手动清除
        $('#report_customername_baseSalesReportPage').val( '');
        $('#report_customername_baseSalesReportPage').change();
        $('#report_pcustomername_baseSalesReportPage').val( '');
        $('#report_pcustomername_baseSalesReportPage').change();
        $('#report_brandname_baseSalesReportPage').val( '');
        $('#report_brandname_baseSalesReportPage').change();
        $('#report_branddeptname_baseSalesReportPage').val( '');
        $('#report_branddeptname_baseSalesReportPage').change();
        $('#report_brandusername_baseSalesReportPage').val( '');
        $('#report_brandusername_baseSalesReportPage').change();
        $('#report_goodsname_baseSalesReportPage').val( '');
        $('#report_goodsname_baseSalesReportPage').change();
        $('#report_goodssortname_baseSalesReportPage').val( '');
        $('#report_goodssortname_baseSalesReportPage').change();
        $('#report_customersortname_baseSalesReportPage').val( '');
        $('#report_customersortname_baseSalesReportPage').change();
        $('#report_salesareaname_baseSalesReportPage').val( '');
        $('#report_salesareaname_baseSalesReportPage').change();
        $('#report_salesusername_baseSalesReportPage').val( '');
        $('#report_salesusername_baseSalesReportPage').change();
        $('#report_suppliername_baseSalesReportPage').val( '');
        $('#report_suppliername_baseSalesReportPage').change();
        $('#report_supplierusername_baseSalesReportPage').val( '');
        $('#report_supplierusername_baseSalesReportPage').change();
        $('#report_salesdeptname_baseSalesReportPage').val( '');
        $('#report_salesdeptname_baseSalesReportPage').change();
        $('#report_storagename_baseSalesReportPage').val( '');
        $('#report_storagename_baseSalesReportPage').change();
        document.getElementById("report_form_baseSalesReportPage").reset();
    }
    var SR_footerobject  = null;
    var initQueryJSON = $("#report-query-form-baseSalesReport").serializeJSON();
    $(function(){
        // 客户
        $('#report_customername_baseSalesReportPage').on('click', function() {
            androidWidget({
                type: 'widget',
                referwid: 'RL_T_BASE_SALES_CUSTOMER_PARENT_2',
                checkType : "2",
                onCheck: 'selectCustomer'
            });
        }).on('change', function(e) {
            var v = $(this).val();
            $(this).attr('value', v);
            $('#report_customerid_baseSalesReportPage').attr('value', (v || ':').split(':')[0]);
        });
        // 总店
        $('#report_pcustomername_baseSalesReportPage').on('click', function() {
            androidWidget({
                type: 'widget',
                referwid : 'RL_T_BASE_SALES_CUSTOMER_PARENT',
                checkType : "2",
                onCheck: 'selectPcustomer'
            });
        }).on('change', function(e) {
            var v = $(this).val();
            $(this).attr('value', v);
            $('#report_pcustomerid_baseSalesReportPage').attr('value', (v || ':').split(':')[0]);
        });
        // 商品
        $('#report_goodsname_baseSalesReportPage').on('click', function() {
            androidWidget({
                type: 'goodsWidget',
                checkType : "2",
                onCheck: 'selectGood'
            });

        }).on('change', function(e) {
            var v = $(this).val();
            $(this).attr('value', v);
            $('#report_goodsid_baseSalesReportPage').attr('value', (v || ':').split(':')[0]);
        });
        // 业务员
        $('#report_salesusername_baseSalesReportPage').on('click', function() {
            androidWidget({
                type: 'widget',
                referwid : "RL_T_BASE_PERSONNEL_CLIENTSELLER",
                checkType : "2",
                onCheck: 'selectSalesuser'
            });

        }).on('change', function(e) {
            var v = $(this).val();
            $(this).attr('value', v);
            $('#report_salesuser_baseSalesReportPage').attr('value', (v || ':').split(':')[0]);
        });
        // 品牌
        $('#report_brandname_baseSalesReportPage').on('click', function() {

            androidWidget({
                type: 'widget',
                referwid: 'RL_T_BASE_GOODS_BRAND',
                checkType : "2",
                onCheck: 'selectBrand'
            });
        }).on('change', function() {
            var v = $(this).val();
            $(this).attr('value', v);
            $('#report_brandid_baseSalesReportPage').attr('value', (v || ':').split(':')[0]);
        });
        // 品牌部门
        $('#report_branddeptname_baseSalesReportPage').on('click', function() {

            androidWidget({
                type: 'widget',
                referwid: 'RL_T_BASE_DEPARTMENT_BUYER',
                checkType : "2",
                onCheck: 'selectBrandDept'
            });
        }).on('change', function() {
            var v = $(this).val();
            $(this).attr('value', v);
            $('#report_branddeptid_baseSalesReportPage').attr('value', (v || ':').split(':')[0]);
        });
        // 品牌业务员
        $('#report_brandusername_baseSalesReportPage').on('click', function() {

            androidWidget({
                type: 'widget',
                referwid: 'RL_T_BASE_PERSONNEL_BRANDSELLER',
                checkType : "2",
                onCheck: 'selectBrandUser'
            });
        }).on('change', function() {
            var v = $(this).val();
            $(this).attr('value', v);
            $('#report_branduserid_baseSalesReportPage').attr('value', (v || ':').split(':')[0]);
        });
        // 商品分类
        $('#report_goodssortname_baseSalesReportPage').on('click', function() {

            androidWidget({
                type: 'widget',
                referwid: 'RL_T_BASE_GOODS_WARESCLASS',
                checkType : "2",
                onCheck: 'selectGoodsSort'
            });
        }).on('change', function() {
            var v = $(this).val();
            $(this).attr('value', v);
            $('#report_goodssortid_baseSalesReportPage').attr('value', (v || ':').split(':')[0]);
        });
        //销售区域
        $('#report_salesareaname_baseSalesReportPage').on('click', function() {

            androidWidget({
                type: 'widget',
                referwid: 'RT_T_BASE_SALES_AREA',
                checkType : "2",
                onCheck: 'selectSalesArea'
            });
        }).on('change', function() {
            var v = $(this).val();
            $(this).attr('value', v);
            $('#report_salesareaid_baseSalesReportPage').attr('value', (v || ':').split(':')[0]);
        });
        //客户分类
        $('#report_customersortname_baseSalesReportPage').on('click', function() {

            androidWidget({
                type: 'widget',
                referwid: 'RT_T_BASE_SALES_CUSTOMERSORT',
                checkType : "2",
                onCheck: 'selectCustomerSort'
            });
        }).on('change', function() {
            var v = $(this).val();
            $(this).attr('value', v);
            $('#report_customersortid_baseSalesReportPage').attr('value', (v || ':').split(':')[0]);
        });
        //供应商
        $('#report_suppliername_baseSalesReportPage').on('click', function() {

            androidWidget({
                type: 'widget',
                referwid: 'RL_T_BASE_BUY_SUPPLIER',
                checkType : "2",
                onCheck: 'selectSupplierName'
            });
        }).on('change', function() {
            var v = $(this).val();
            $(this).attr('value', v);
            $('#report_supplierid_baseSalesReportPage').attr('value', (v || ':').split(':')[0]);
        });
        //厂家业务员
        $('#report_supplierusername_baseSalesReportPage').on('click', function() {

            androidWidget({
                type: 'widget',
                referwid: 'RL_T_BASE_PERSONNEL_SUPPLIER',
                checkType : "2",
                onCheck: 'selectSupplierUser'
            });
        }).on('change', function() {
            var v = $(this).val();
            $(this).attr('value', v);
            $('#report_supplieruserid_baseSalesReportPage').attr('value', (v || ':').split(':')[0]);
        });
        //销售部门
        $('#report_salesdeptname_baseSalesReportPage').on('click', function() {

            androidWidget({
                type: 'widget',
                name:'t_sales_order',
                col:'salesdept',
                //referwid: 'RL_T_BASE_PERSONNEL_SUPPLIER',
                checkType : "2",
                onCheck: 'selectSalesDept'
            });
        }).on('change', function() {
            var v = $(this).val();
            $(this).attr('value', v);
            $('#report_salesdeptid_baseSalesReportPage').attr('value', (v || ':').split(':')[0]);
        });
        //仓库
        $('#report_storagename_baseSalesReportPage').on('click', function() {

            androidWidget({
                type: 'widget',
                name:'t_sales_order',
                col:'salesdept',
                //referwid: 'RL_T_BASE_PERSONNEL_SUPPLIER',
                checkType : "2",
                onCheck: 'selectStorage'
            });
        }).on('change', function() {
            var v = $(this).val();
            $(this).attr('value', v);
            $('#report_storageid_baseSalesReportPage').attr('value', (v || ':').split(':')[0]);
        });

    });

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
        $('#report_brandid_baseSalesReportPage').val("");
        $('#report_brandname_baseSalesReportPage').val("");
        if(idArr.length>0){
            $('#report_brandid_baseSalesReportPage').val(idArr.join(','));
            $('#report_brandname_baseSalesReportPage').val(nameArr.join(','));
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
        $('#report_branddeptid_baseSalesReportPage').val("");
        $('#report_branddeptname_baseSalesReportPage').val("");
        if(idArr.length>0){
            $('#report_branddeptid_baseSalesReportPage').val(idArr.join(','));
            $('#report_branddeptname_baseSalesReportPage').val(nameArr.join(','));
        }
    }
    
    //选择业务员
    function selectSalesuser(data){
        var idArr=new Array();
        var nameArr=new Array();
        for(var i=0;i < data.length;i++){
            var item=data[i];
            // if(item!=null && item.isParent=='false'){
            idArr.push(item.id);
            nameArr.push(item.name);
            //   }
        }
        $('#report_salesuser_baseSalesReportPage').val("");
        $('#report_salesusername_baseSalesReportPage').val("");
        if(idArr.length>0){
            $('#report_salesuser_baseSalesReportPage').val(idArr.join(','));
            $('#report_salesusername_baseSalesReportPage').val(nameArr.join(','));
        }
    }
    //选择总店
    function selectPcustomer(data){
        var idArr=new Array();
        var nameArr=new Array();
        for(var i=0;i < data.length;i++){
            var item=data[i];
            // if(item!=null && item.isParent=='false'){
            idArr.push(item.id);
            nameArr.push(item.name);
            //   }
        }
        $('#report_pcustomerid_baseSalesReportPage').val("");
        $('#report_pcustomername_baseSalesReportPage').val("");
        if(idArr.length>0){
            $('#report_pcustomerid_baseSalesReportPage').val(idArr.join(','));
            $('#report_pcustomername_baseSalesReportPage').val(nameArr.join(','));
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
        $('#report_customerid_baseSalesReportPage').val("");
        $('#report_customername_baseSalesReportPage').val("");
        if(idArr.length>0){
            $('#report_customerid_baseSalesReportPage').val(idArr.join(','));
            $('#report_customername_baseSalesReportPage').val(nameArr.join(','));
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
        $('#report_goodsid_baseSalesReportPage').val("");
        $('#report_goodsname_baseSalesReportPage').val("");
        if(idArr.length>0){
            $('#report_goodsid_baseSalesReportPage').val(idArr.join(','));
            $('#report_goodsname_baseSalesReportPage').val(nameArr.join(','));
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
        $('#report_branduserid_baseSalesReportPage').val("");
        $('#report_brandusername_baseSalesReportPage').val("");
        if(idArr.length>0){
            $('#report_branduserid_baseSalesReportPage').val(idArr.join(','));
            $('#report_brandusername_baseSalesReportPage').val(nameArr.join(','));
        }
    }
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
        $('#report_goodssortid_baseSalesReportPage').val("");
        $('#report_goodssortname_baseSalesReportPage').val("");
        if(idArr.length>0){
            $('#report_goodssortid_baseSalesReportPage').val(idArr.join(','));
            $('#report_goodssortname_baseSalesReportPage').val(nameArr.join(','));
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
        $('#report_salesareaid_baseSalesReportPage').val("");
        $('#report_salesareaname_baseSalesReportPage').val("");
        if(idArr.length>0){
            $('#report_salesareaid_baseSalesReportPage').val(idArr.join(','));
            $('#report_salesareaname_baseSalesReportPage').val(nameArr.join(','));
        }
    }
    //选择客户分类
    function selectCustomerSort(data){
        var idArr=new Array();
        var nameArr=new Array();
        for(var i=0;i < data.length;i++){
            var item=data[i];
            // if(item!=null && item.isParent=='false'){
            idArr.push(item.id);
            nameArr.push(item.name);
            //   }
        }
        $('#report_customersortid_baseSalesReportPage').val("");
        $('#report_customersortname_baseSalesReportPage').val("");
        if(idArr.length>0){
            $('#report_customersortid_baseSalesReportPage').val(idArr.join(','));
            $('#report_customersortname_baseSalesReportPage').val(nameArr.join(','));
        }
    }
    //选择供应商
    function selectSupplierName(data){
        var idArr=new Array();
        var nameArr=new Array();
        for(var i=0;i < data.length;i++){
            var item=data[i];
            // if(item!=null && item.isParent=='false'){
            idArr.push(item.id);
            nameArr.push(item.name);
            //   }
        }
        $('#report_supplierid_baseSalesReportPage').val("");
        $('#report_suppliername_baseSalesReportPage').val("");
        if(idArr.length>0){
            $('#report_supplierid_baseSalesReportPage').val(idArr.join(','));
            $('#report_suppliername_baseSalesReportPage').val(nameArr.join(','));
        }
    }
    //选择厂家业务员
    function selectSupplierUser(data){
        var idArr=new Array();
        var nameArr=new Array();
        for(var i=0;i < data.length;i++){
            var item=data[i];
            // if(item!=null && item.isParent=='false'){
            idArr.push(item.id);
            nameArr.push(item.name);
            //   }
        }
        $('#report_supplieruserid_baseSalesReportPage').val("");
        $('#report_supplierusername_baseSalesReportPage').val("");
        if(idArr.length>0){
            $('#report_supplieruserid_baseSalesReportPage').val(idArr.join(','));
            $('#report_supplierusername_baseSalesReportPage').val(nameArr.join(','));
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
        $('#report_salesdeptid_baseSalesReportPage').val("");
        $('#report_salesdeptname_baseSalesReportPage').val("");
        if(idArr.length>0){
            $('#report_salesdeptid_baseSalesReportPage').val(idArr.join(','));
            $('#report_salesdeptname_baseSalesReportPage').val(nameArr.join(','));
        }
    }
    //选择仓库
    function selectStorage(data){
        var idArr=new Array();
        var nameArr=new Array();
        for(var i=0;i < data.length;i++){
            var item=data[i];
            // if(item!=null && item.isParent=='false'){
            idArr.push(item.id);
            nameArr.push(item.name);
            //   }
        }
        $('#report_storageid_baseSalesReportPage').val("");
        $('#report_storagename_baseSalesReportPage').val("");
        if(idArr.length>0){
            $('#report_storageid_baseSalesReportPage').val(idArr.join(','));
            $('#report_storagename_baseSalesReportPage').val(nameArr.join(','));
        }
    }


</script>
</body>
</html>
