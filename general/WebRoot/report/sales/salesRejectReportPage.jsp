<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<jsp:useBean id="today" class="java.util.Date"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>销售退货统计表</title>
    <%@include file="/include.jsp" %>
    <style type="text/css">
        .checkbox1{
            float:left;
            height: 22px;
            line-height: 22px;
        }
        .divtext{
            height:22px;
            line-height:22px;
            float:left;
            display: block;
        }
        .len200 {
            width: 200px;
        }
    </style>
</head>

<body>
<table id="report-datagrid-salesRejectReportPage"></table>
<div id="report-toolbar-salesRejectReportPage" class="buttonBG">
    <a href="javaScript:void(0);" id="report-advancedQuery-salesRejectReportPagePage" class="easyui-linkbutton" iconCls="button-commonquery" plain="true" title="[Alt+Q]查询">查询</a>
    <security:authorize url="/report/sales/exportSalesRejectReportListData.do">
        <a href="javaScript:void(0);" id="report-export-salesRejectReportPagePage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
    </security:authorize>
    <security:authorize url="/report/sales/printSalesRejectReportListData.do">
        <a href="javaScript:void(0);" id="report-print-salesRejectReportPagePage" class="easyui-linkbutton" iconCls="button-print" plain="true" title="打印">打印</a>
    </security:authorize>
</div>
<div style="display:none">
    <div id="report-dialog-salesRejectReportPage" >
        <form action="" id="report-form-salesRejectReportPage" method="post">
            <table cellpadding="2" style="margin-left:5px;margin-top: 5px;">
                <tr>
                    <td>业务日期：</td>
                    <td><input type="text" name="startdate" value="<fmt:formatDate value="${today }" pattern="yyyy-MM-01" type="date" dateStyle="long" />" style="width:87px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /> 到 <input type="text" name="enddate" class="Wdate" style="width:87px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
                    <td>客　　户：</td>
                    <td><input type="text" class="len200" name="customerid" id="report-customerid-salesRejectReportPage"/></td>
                </tr>
                <tr>
                    <td>总　　店：</td>
                    <td><input type="text" class="len200" name="pcustomerid" id="report-pcustomerid-salesRejectReportPage"/></td>
                    <td>品　　牌：</td>
                    <td><input type="text" class="len200" name="brandid" id="report-brandid-salesRejectReportPage"/></td>
                </tr>
                <tr>
                    <td>商　　品：</td>
                    <td><input type="text" class="len200" name="goodsid" id="report-goodsid-salesRejectReportPage"/></td>
                    <td>商品分类：</td>
                    <td><input type="text" class="len200" name="goodssort" id="report-goodssort-salesRejectReportPage"/></td>
                </tr>
                <tr>
                    <td>销售区域：</td>
                    <td><input type="text" class="len200" name="salesarea" id="report-salesarea-salesRejectReportPage"/></td>
                    <td>客户分类：</td>
                    <td><input type="text" class="len200" name="customersort" id="report-customersort-salesRejectReportPage"/></td>
                </tr>
                <tr>
                    <td>客户业务员：</td>
                    <td><input type="text" class="len200" name="salesuser" id="report-salesuser-salesRejectReportPage"/></td>
                    <td>品牌业务员：</td>
                    <td><input type="text" class="len200" name="branduser" id="report-branduser-salesRejectReportPage"/></td>
                </tr>
                <tr>
                    <td>销售部门：</td>
                    <td><input type="text" class="len200" name="salesdept" id="report-salesdept-salesRejectReportPage"/></td>
                    <td>供 应 商：</td>
                    <td><input type="text" class="len200" name="supplierid" id="report-supplierid-salesRejectReportPage"/></td>
                </tr>
                <tr>
                    <td>仓　　库：</td>
                    <td><input type="text" class="len200" name="storageid" id="report-storageid-salesRejectReportPage"/></td>
                    <td>司　　机：</td>
                    <td><input type="text" class="len200" name="driverid" id="report-driverid-salesRejectReportPage"/></td>
                </tr>
                <tr>
                    <td>退货类型：</td>
                    <td>
                        <select class="len200" name="sourcetype" id="report-sourcetype-salesRejectReportPage">
                            <option value=""></option>
                            <option value="1">售后退货</option>
                            <option value="2">直退退货</option>
                        </select>
                    </td>
                    <td>退货属性：</td>
                    <td>
                        <select class="len200" name="rejectcategory" id="report-rejectcategory-salesRejectReportPage">
                            <option value=""></option>
                            <c:forEach items="${rejectCategory }" var="category" varStatus="status">
                                <option value="<c:out value="${category.code }"/>"><c:out value="${category.codename }"/></option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>收货人：</td>
                    <td><input type="text" class="len200" name="storager" id="report-storager-salesRejectReportPage"/></td>
                    <td colspan="2"></td>
                </tr>

                <tr>
                    <td>小计列：</td>
                    <td colspan="3">
                        <div style="margin-top:2px">
                            <div style="line-height: 25px;margin-top: 2px;">
                                <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="customerid"/>客户</label>
                                <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="pcustomerid"/>总店</label>
                                <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="customersort"/>客户分类</label>
                                <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="salesarea"/>销售区域</label>
                                <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="salesuser"/>客户业务员</label>
                                <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="supplierid"/>供应商</label>
                                <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="sourcetype"/>退货类型</label>
                                <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="driverid"/>司机</label>
                                <br/>
                                <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="goodsid"/>商品</label>
                                <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="brandid"/>品牌</label>
                                <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="goodssort"/>商品分类</label>
                                <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="salesdept"/>销售部门</label>
                                <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="branduser"/>品牌业务员</label>
                                <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="storageid"/>仓库　</label>
                                <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="rejectcategory"/>退货属性</label>
                                <label class="divtext"><input type="checkbox" class="groupcols checkbox1" value="storager"/>收货人</label>
                                <input name="groupcols" id="report-groupcols-salesRejectReportPage" type="hidden"/>
                            </div>
                        </div>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<script type="text/javascript">

    var SR_footerobject  = null;
    var initQueryJSON = $('#report-form-salesRejectReportPage').serializeJSON();
    $(function(){
        $("#report-print-salesRejectReportPagePage").click(function () {
            var msg = "";
            printByAnalyse("销售退货统计表", "report-datagrid-salesRejectReportPage", "report/sales/printSalesRejectReportListData.do", msg);
        })
        $('#report-export-salesRejectReportPagePage').click(function(){
            var queryJSON = $('#report-form-salesRejectReportPage').serializeJSON();
            //获取排序规则
            var objecr  = $('#report-datagrid-salesRejectReportPage').datagrid('options');
            if(null != objecr.sortName && null != objecr.sortOrder ){
                queryJSON['sort'] = objecr.sortName;
                queryJSON['order'] = objecr.sortOrder;
            }
            var queryParam = JSON.stringify(queryJSON);
            var url = 'report/sales/exportSalesRejectReportListData.do?exporttype=base';
            exportByAnalyse(queryParam,'销售退货报表','report-datagrid-salesRejectReportPage',url);
        });

        $('.groupcols').click(function(){
            var cols = new Array();
            $('.groupcols').each(function(){
                if($(this).is(':checked')){

                    var v = $(this).val();
                    cols.push(v);

                    if(v == 'customerid') {
                        cols.push('pcustomerid');
                        cols.push('customersort');
                        cols.push('salesarea');
                    }

                    if(v == 'goodsid') {
                        cols.push('brandid');
                        cols.push('goodssort');
                        cols.push('supplierid');
                    }
                }
            });
            $('#report-groupcols-salesRejectReportPage').val(cols.join(','));
        });

        var tableColumnListJson = $('#report-datagrid-salesRejectReportPage').createGridColumnLoad({
            frozenCol : [[
                {field:'idok',checkbox:true,isShow:true}
            ]],
            commonCol : [[
                {field:'customerid',title:'客户编号',sortable:true,width:60},
                {field:'customername',title:'客户名称',width:210},
                {field:'pcustomerid',title:'总店编码',sortable:true,width:80,hidden:false},
                {field:'pcustomername',title:'总店名称',width:100,hidden:false},
                {field:'salesdept',title:'销售部门',sortable:true,width:80,hidden:false,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.salesdeptname;
                    }
                },
                {field:'salesuser',title:'客户业务员',sortable:true,width:70,hidden:false,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.salesusername;
                    }
                },
                {field:'branduser',title:'品牌业务员',sortable:true,width:70,hidden:false,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.brandusername;
                    }
                },
                {field:'customersort',title:'客户分类',sortable:true,width:60,hidden:false,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.customersortname;
                    }
                },
                {field:'driverid',title:'司机',sortable:true,width:80,hidden:false,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.drivername;
                    }
                },
                {field:'storageid',title:'仓库',sortable:true,width:70,hidden:false,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.storagename;
                    }
                },
                {field:'goodsid',title:'商品编码',sortable:true,width:60,hidden:false},
                {field:'goodsname',title:'商品名称',width:250,hidden:false},
                {field:'barcode',title:'条形码',width:90,hidden:false},
                {field:'brandid',title:'品牌名称',sortable:true,width:60,hidden:false,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.brandname;
                    }
                },
                {field:'goodssort',title:'商品分类',width:90,hidden:false,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.goodssortname;
                    }
                },
                {field:'salesarea',title:'销售区域',width:80,hidden:false,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.salesareaname;
                    }
                },
                {field:'supplierid',title:'供应商',width:180,hidden:false,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.suppliername;
                    }
                },
                {field:'sourcetype',title:'退货类型',width:60,hidden:false,
                    formatter:function(value,rowData,rowIndex){
                        if(value == '1') {
                            return '售后退货';
                        } else if(value == '2') {
                            return '直退退货';
                        }
                    }
                },
                {field:'rejectcategory',title:'退货属性',width:60,hidden:false,
                    formatter:function(value,rowData,rowIndex){
                        return getSysCodeName('rejectCategory', value);
                    }
                },
                {field:'storagername',title:'收货人',width:80,sortable:true,hidden:false},
                {field:'unitnum',title:'退货数量',width:60,align:'right',hidden:false,sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterBigNumNoLen(value);
                    }
                },
                {field:'totalbox',title:'退货箱数',width:60,align:'right',isShow:true,sortable:true,hidden:false,
                    formatter:function(value,rowData,rowIndex){
                        return formatterBigNumNoLen(value);
                    }
                },
                {field:'taxamount',title:'退货金额',resizable:true,sortable:true,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                }
            ]]
        });

        // 客户
        $('#report-customerid-salesRejectReportPage').widget({
            referwid:'RL_T_BASE_SALES_CUSTOMER',
            singleSelect:false,
            width:200,
            onlyLeafCheck:true
        });

        // 总店
        $('#report-pcustomerid-salesRejectReportPage').widget({
            referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
            singleSelect:false,
            width:200,
            onlyLeafCheck:true
        });

        //品牌
        $('#report-brandid-salesRejectReportPage').widget({
            referwid:'RL_T_BASE_GOODS_BRAND',
            singleSelect:false,
            width:200,
            onlyLeafCheck:true
        });

        // 商品
        $('#report-goodsid-salesRejectReportPage').widget({
            referwid:'RL_T_BASE_GOODS_INFO',
            singleSelect:false,
            width:200,
            onlyLeafCheck:true
        });

        // 商品分类
        $('#report-goodssort-salesRejectReportPage').widget({
            referwid:'RL_T_BASE_GOODS_WARESCLASS',
            singleSelect:false,
            width:200,
            onlyLeafCheck:false
        });

        // 销售区域
        $('#report-salesarea-salesRejectReportPage').widget({
            referwid:'RT_T_BASE_SALES_AREA',
            singleSelect:false,
            width:200,
            onlyLeafCheck:false
        });

        // 客户分类
        $('#report-customersort-salesRejectReportPage').widget({
            referwid:'RT_T_BASE_SALES_CUSTOMERSORT',
            singleSelect:false,
            width:200,
            onlyLeafCheck:false
        });

        //品牌业务员
        $('#report-branduser-salesRejectReportPage').widget({
            referwid:'RL_T_BASE_PERSONNEL_BRANDSELLER',
            singleSelect:false,
            width:200,
            onlyLeafCheck:true
        });

        // 客户业务员
        $('#report-salesuser-salesRejectReportPage').widget({
            referwid:'RL_T_BASE_PERSONNEL_CLIENTSELLER',
            singleSelect:false,
            width:200,
            onlyLeafCheck:false
        });

        // 销售部门
        $('#report-salesdept-salesRejectReportPage').widget({
            referwid:'RL_T_BASE_DEPARTMENT_SELLER',
            width:200,
            singleSelect:false,
            onlyLeafCheck:true
        });

        //供应商
        $('#report-supplierid-salesRejectReportPage').widget({
            referwid:'RL_T_BASE_BUY_SUPPLIER',
            singleSelect:false,
            width:200,
            onlyLeafCheck:true
        });

        //仓库
        $('#report-storageid-salesRejectReportPage').widget({
            referwid:'RL_T_BASE_STORAGE_INFO',
            singleSelect:false,
            width:200,
            onlyLeafCheck:true
        });

        //司机
        $('#report-driverid-salesRejectReportPage').widget({
            referwid:'RL_T_BASE_PERSONNEL_LOGISTICS',
            singleSelect:false,
            width:200,
            onlyLeafCheck:true
        });

        $("#report-storager-salesRejectReportPage").widget({
            referwid:'RL_T_BASE_PERSONNEL_STORAGER',
            width:200,
            col:'name',
            singleSelect:true,
        });

        $('#report-datagrid-salesRejectReportPage').datagrid({
            authority: tableColumnListJson,
            frozenColumns: tableColumnListJson.frozen,
            columns: tableColumnListJson.common,
            method: 'post',
            title: '',
            fit: true,
            rownumbers: true,
            pagination: true,
            showFooter: true,
            singleSelect: false,
            checkOnSelect: true,
            selectOnCheck: true,
            pageSize: 100,
            toolbar: '#report-toolbar-salesRejectReportPage',
            onLoadSuccess: function () {
                computeSumData('onLoadSuccess');
            },
            onCheckAll: function () {
                computeSumData('onCheckAll');
            },
            onUncheckAll: function () {
                computeSumData('onUncheckAll');
            },
            onCheck: function () {
                computeSumData('onCheck');
            },
            onUncheck: function () {
                computeSumData('onUncheck');
            }
        }).datagrid('columnMoving');

        //回车事件
        controlQueryAndResetByKey('report-advancedQuery-salesRejectReportPagePage','');

        //高级查询
        $('#report-advancedQuery-salesRejectReportPagePage').click(function(){
            $('#report-dialog-salesRejectReportPage').dialog({
                maximizable: false,
                resizable: true,
                title: '销售退货统计表查询',
                top: 30,
                width: 600,
                height: 390,
                closed: false,
                cache: false,
                modal: true,
                buttons:[
                    {
                        text:'确定',
                        handler:function(){

                            if(($('#report-groupcols-salesRejectReportPage').val() || '') == '') {
                                $('#report-groupcols-salesRejectReportPage').val('goodsid');
                            }

                            setColumn();
                            //把form表单的name序列化成JSON对象
                            var queryJSON = $('#report-form-salesRejectReportPage').serializeJSON();
                            $('#report-datagrid-salesRejectReportPage').datagrid({
                                url:'report/sales/getSalesRejectReportListData.do',
                                pageNumber:1,
                                queryParams:queryJSON
                            }).datagrid('columnMoving');

                            $('#report-dialog-salesRejectReportPage').dialog('close');
                        }
                    },
                    {
                        text:'重置',
                        handler:function(){

                            $('#report-form-salesRejectReportPage').form('reset');
                            $('.groupcols').each(function(){
                                if($(this).attr('checked')){
                                    $(this)[0].checked = false;
                                }
                            });
                            $('#report-groupcols-salesRejectReportPage').val('')

                            $('#report-customerid-salesRejectReportPage').widget('clear');
                            $('#report-pcustomerid-salesRejectReportPage').widget('clear');
                            $('#report-brandid-salesRejectReportPage').widget('clear');
                            $('#report-goodsid-salesRejectReportPage').widget('clear');
                            $('#report-goodssort-salesRejectReportPage').widget('clear');
                            $('#report-salesarea-salesRejectReportPage').widget('clear');
                            $('#report-customersort-salesRejectReportPage').widget('clear');
                            $('#report-branduser-salesRejectReportPage').widget('clear');
                            $('#report-salesuser-salesRejectReportPage').widget('clear');
                            $('#report-salesdept-salesRejectReportPage').widget('clear');
                            $('#report-supplierid-salesRejectReportPage').widget('clear');
                            $('#report-storageid-salesRejectReportPage').widget('clear');
                            $('#report-driverid-salesRejectReportPage').widget('clear');

                            setColumn();
                        }
                    }
                ],
                onClose:function(){
                }
            });
        });
    });

    function setColumn() {

        $('#report-datagrid-salesRejectReportPage').datagrid('hideColumn', 'customerid');
        $('#report-datagrid-salesRejectReportPage').datagrid('hideColumn', 'customername');
        $('#report-datagrid-salesRejectReportPage').datagrid('hideColumn', 'pcustomerid');
        $('#report-datagrid-salesRejectReportPage').datagrid('hideColumn', 'pcustomername');
        $('#report-datagrid-salesRejectReportPage').datagrid('hideColumn', 'salesdept');
        $('#report-datagrid-salesRejectReportPage').datagrid('hideColumn', 'salesuser');
        $('#report-datagrid-salesRejectReportPage').datagrid('hideColumn', 'branduser');
        $('#report-datagrid-salesRejectReportPage').datagrid('hideColumn', 'customersort');
        $('#report-datagrid-salesRejectReportPage').datagrid('hideColumn', 'driverid');
        $('#report-datagrid-salesRejectReportPage').datagrid('hideColumn', 'storageid');
        $('#report-datagrid-salesRejectReportPage').datagrid('hideColumn', 'goodsid');
        $('#report-datagrid-salesRejectReportPage').datagrid('hideColumn', 'goodsname');
        $('#report-datagrid-salesRejectReportPage').datagrid('hideColumn', 'barcode');
        $('#report-datagrid-salesRejectReportPage').datagrid('hideColumn', 'brandid');
        $('#report-datagrid-salesRejectReportPage').datagrid('hideColumn', 'goodssort');
        $('#report-datagrid-salesRejectReportPage').datagrid('hideColumn', 'salesarea');
        $('#report-datagrid-salesRejectReportPage').datagrid('hideColumn', 'supplierid');
        $('#report-datagrid-salesRejectReportPage').datagrid('hideColumn', 'sourcetype');
        $('#report-datagrid-salesRejectReportPage').datagrid('hideColumn', 'rejectcategory');
        $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'unitnum');
        $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'totalbox');
        $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'taxamount');
        $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'storagername');
        var cols = $('#report-groupcols-salesRejectReportPage').val() || '';

        if (cols != '') {

            $('#report-datagrid-salesRejectReportPage').datagrid('hideColumn', 'customerid');
            $('#report-datagrid-salesRejectReportPage').datagrid('hideColumn', 'customername');
            $('#report-datagrid-salesRejectReportPage').datagrid('hideColumn', 'pcustomerid');
            $('#report-datagrid-salesRejectReportPage').datagrid('hideColumn', 'pcustomername');
            $('#report-datagrid-salesRejectReportPage').datagrid('hideColumn', 'salesdept');
            $('#report-datagrid-salesRejectReportPage').datagrid('hideColumn', 'salesuser');
            $('#report-datagrid-salesRejectReportPage').datagrid('hideColumn', 'branduser');
            $('#report-datagrid-salesRejectReportPage').datagrid('hideColumn', 'customersort');
            $('#report-datagrid-salesRejectReportPage').datagrid('hideColumn', 'driverid');
            $('#report-datagrid-salesRejectReportPage').datagrid('hideColumn', 'storageid');
            $('#report-datagrid-salesRejectReportPage').datagrid('hideColumn', 'goodsid');
            $('#report-datagrid-salesRejectReportPage').datagrid('hideColumn', 'goodsname');
            $('#report-datagrid-salesRejectReportPage').datagrid('hideColumn', 'barcode');
            $('#report-datagrid-salesRejectReportPage').datagrid('hideColumn', 'brandid');
            $('#report-datagrid-salesRejectReportPage').datagrid('hideColumn', 'goodssort');
            $('#report-datagrid-salesRejectReportPage').datagrid('hideColumn', 'salesarea');
            $('#report-datagrid-salesRejectReportPage').datagrid('hideColumn', 'supplierid');
            $('#report-datagrid-salesRejectReportPage').datagrid('hideColumn', 'sourcetype');
            $('#report-datagrid-salesRejectReportPage').datagrid('hideColumn', 'rejectcategory');
            $('#report-datagrid-salesRejectReportPage').datagrid('hideColumn', 'storagername');

        } else {

            $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'customerid');
            $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'customername');
            $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'pcustomerid');
            $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'pcustomername');
            $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'salesdept');
            $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'salesuser');
            $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'branduser');
            $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'customersort');
            $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'driverid');
            $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'storageid');
            $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'goodsid');
            $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'goodsname');
            $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'barcode');
            $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'brandid');
            $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'goodssort');
            $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'salesarea');
            $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'supplierid');
            $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'sourcetype');
            $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'rejectcategory');
            $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'storagername');
        }
        var colarr = cols.split(',');
        for (var i in colarr) {
            var col = colarr[i];
            if (col == 'customerid') {
                $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'customerid');
                $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'customername');
//                $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'pcustomerid');
//                $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'pcustomername');
//                $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'salesuser');
//                $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'customersort');
            } else if (col == 'pcustomerid') {
                $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'pcustomerid');
                $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'pcustomername');
            } else if (col == 'salesuser') {
                $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'salesuser');
            } else if (col == 'salesarea') {
                $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'salesarea');
            } else if (col == 'salesdept') {
                $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'salesdept');
            } else if (col == 'goodsid') {
                $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'goodsid');
                $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'goodsname');
//                $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'goodssort');
//                $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'barcode');
//                $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'brandid');
            } else if (col == 'goodssort') {
                $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'goodssort');
            } else if (col == 'brandid') {
                $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'brandid');
            } else if (col == 'branduser') {
                $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'branduser');
            } else if (col == 'customersort') {
                $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'customersort');
            } else if (col == 'supplierid') {
                $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'supplierid');
            } else if (col == 'storageid') {
                $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'storageid');
            } else if (col == 'driverid') {
                $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'driverid');
            } else if (col == 'sourcetype') {
                $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'sourcetype');
            } else if (col == 'rejectcategory') {
                $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'rejectcategory');
            } else if (col == 'storager') {
                $('#report-datagrid-salesRejectReportPage').datagrid('showColumn', 'storagername');
            }
        }
    }

    function getSumTitleColname() {

        var cols = $('#report-datagrid-salesRejectReportPage').datagrid('getColumnFields');
        if(cols.length == 0) {
            return 'goodsname';
        }

//        var colname = '';
//        var cols = $('#report-groupcols-salesRejectReportPage').val() || '';
//        if (cols == '') {
//            return 'customername';
//        }
//        var colarr = cols.split(',');
        var colarr = cols;
        if (colarr[0] == 'customerid') {
            colname = 'customername';
        } else if (colarr[0] == 'pcustomerid') {
            colname = 'pcustomername';
        } else if (colarr[0] == 'salesdept') {
            colname = 'salesdeptname';
        } else if (colarr[0] == 'salesuser') {
            colname = 'salesusername';
        } else if (colarr[0] == 'branduser') {
            colname = 'brandusername';
        } else if (colarr[0] == 'customersort') {
            colname = 'customersortname';
        } else if (colarr[0] == 'driverid') {
            colname = 'driverid';
        } else if (colarr[0] == 'storageid') {
            colname = 'storageid';
        } else if (colarr[0] == 'goodsid') {
            colname = 'goodsname';
        } else if (colarr[0] == 'brandid') {
            colname = 'brandname';
        } else if (colarr[0] == 'goodssort') {
            colname = 'goodssortname';
        } else if (colarr[0] == 'salesarea') {
            colname = 'salesareaname';
        } else if (colarr[0] == 'supplierid') {
            colname = 'suppliername';
        } else if (colarr[0] == 'sourcetype') {
            colname = 'sourcetype';
        } else if (colarr[0] == 'rejectcategory') {
            colname = 'rejectcategory';
        }else if (colarr[0] == 'storager') {
            colname = 'storagername';
        }

        return colname;
    }

    function computeSumData() {

        var footer = $('#report-datagrid-salesRejectReportPage').datagrid('getFooterRows');
        if(footer == undefined) {
            return false;
        }
        var totalRow = footer[footer.length - 1];

        var checkedRows = $('#report-datagrid-salesRejectReportPage').datagrid('getChecked');
        if(checkedRows.length == 0) {
            var newFooter = {};
            newFooter[getSumTitleColname()] = '合计';
            $.extend(newFooter, totalRow);
            $('#report-datagrid-salesRejectReportPage').datagrid('reloadFooter', [newFooter]);
            return true;
        }

        var unitnum      = 0;
        var totalbox     = 0.000;
        var taxamount    = 0;
        for(var i in checkedRows) {

            var row = checkedRows[i];
            unitnum = unitnum + parseFloat(row.unitnum || 0);
            totalbox = totalbox + parseFloat(row.totalbox || 0);
            taxamount = taxamount + parseFloat(row.taxamount || 0);
        }

        var checkedRowSum = {unitnum: unitnum, totalbox: totalbox, taxamount: taxamount.toFixed(2)};
        checkedRowSum[getSumTitleColname()] = '选中合计';
        totalRow[getSumTitleColname()] = '合计';
        $('#report-datagrid-salesRejectReportPage').datagrid('reloadFooter', [checkedRowSum, totalRow]);
        return true;
    }

</script>
</body>
</html>
