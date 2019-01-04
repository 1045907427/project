<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>客户商品报价单报表</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>
</head>
<body>
<table id="report-datagrid-salesGoodsQuotationReport"></table>
<div id="report-toolbar-salesGoodsQuotationReport" style="padding: 0px">
    <div class="buttonBG">
        <security:authorize url="/report/sales/salesGoodsQuotationReportPrintView.do">
            <a href="javaScript:void(0);" id="report-printview-salesGoodsQuotationReport" class="easyui-linkbutton"
               data-options="plain:true,iconCls:'button-preview'">打印预览</a>
        </security:authorize>
        <security:authorize url="/report/sales/salesGoodsQuotationReportPrintView.do">
            <a href="javaScript:void(0);" id="report-print-salesGoodsQuotationReport" class="easyui-linkbutton"
               data-options="plain:true,iconCls:'button-print'">打印</a>
        </security:authorize>
        <security:authorize url="/report/sales/exportGoodsQuotationReportData.do">
            <a href="javaScript:void(0);" id="report-export-salesGoodsQuotationReportBtn" class="easyui-linkbutton"
               data-options="plain:true,iconCls:'button-export'">全局导出</a>
            <a href="javaScript:void(0);" id="report-export-salesGoodsQuotationReport" style="display: none">全局导出</a>
        </security:authorize>
        <div id="dialog-autoexport"></div>
    </div>
    <form action="" method="post" id="report-form-salesGoodsQuotationReport">
        <table class="querytable">
            <tr>
                <td>客户:</td>
                <td><input type="text" id="report-salesGoodsQuotationReport-customerid" name="customerid"
                           style="width:210px;"/></td>
                <td>价格套:</td>
                <td>
                    <select id="report-salesGoodsQuotationReport-pricecode" name="pricecode" class="len136">
                        <option value=""></option>
                        <c:forEach items="${priceList }" var="list">
                            <option value="${list.code }">${list.codename }</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>
            <tr>
                <td>品牌名称:</td>
                <td><input type="text" id="report-salesGoodsQuotationReport-brandid" name="brandid"/></td>
                <td colspan="2">
                    <a href="javaScript:void(0);" id="report-query-salesGoodsQuotationReport" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="report-reload-salesGoodsQuotationReport" class="button-qr">重置</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<script type="text/javascript">
    var initQueryJSON = $("#report-datagrid-salesGoodsQuotationReport").serializeJSON();
    $(function () {
        var tableColumnListJson = $("#report-datagrid-salesGoodsQuotationReport").createGridColumnLoad({
            frozenCol: [[
                {field: 'idok', checkbox: true, isShow: true}
            ]],
            commonCol: [[
                {field: 'goodsid', title: '商品编码', width: 60, sortable: true},
                {field: 'goodsname', title: '商品名称', width: 210, isShow: true},
                {field: 'barcode', title: '条形码', width: 90},
                {field: 'brandname', title: '品牌名称', width: 70, isShow: true},
                {
                    field: 'unitname', title: '单位', width: 40, align: 'center',
                    formatter: function (value, row, index) {
                        if (row.goodsInfo != null && row.goodsInfo.mainunitName != null) {
                            return row.goodsInfo.mainunitName;
                        }
                    }
                },
                {
                    field: 'boxnum', title: '箱装量', aliascol: 'goodsid', width: 50, align: 'right',
                    formatter: function (value, row, rowIndex) {
                        if (row.goodsInfo != null && row.goodsInfo.boxnum != null) {
                            return formatterBigNumNoLen(row.goodsInfo.boxnum);
                        }
                    }
                },
                {
                    field: 'price', title: '供价', width: 70, align: 'right',
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'boxprice', title: '箱价', width: 70, align: 'right',
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                }
            ]]
        });
        $("#report-datagrid-salesGoodsQuotationReport").datagrid({
            authority: tableColumnListJson,
            frozenColumns: tableColumnListJson.frozen,
            columns: tableColumnListJson.common,
            method: 'post',
            idField: 'goodsid',
            title: '',
            //pageList:[10,20,30,50,100,200,300],
            fit: true,
            rownumbers: true,
            pagination: true,
            showFooter: true,
            singleSelect: false,
            checkOnSelect: true,
            selectOnCheck: true,
            pageSize: 100,
            toolbar: '#report-toolbar-salesGoodsQuotationReport',
            onBeforeLoad: function () {
            }
        });
        $("#report-salesGoodsQuotationReport-customerid").customerWidget({
            //isdatasql:false
            onSelect: function (data) {
                $("#report-salesGoodsQuotationReport-pricecode").val("");
            }
        });
        $("#report-salesGoodsQuotationReport-brandid").widget({
            referwid: 'RL_T_BASE_GOODS_BRAND',
            width: 210,
            singleSelect: false
        });
        $("#report-salesGoodsQuotationReport-pricecode").change(function () {
            if ($(this).val() != "") {
                $("#report-salesGoodsQuotationReport-customerid").widget('clear');
            }
        });
        $("#report-query-salesGoodsQuotationReport").click(function () {
            var customer = $("#report-salesGoodsQuotationReport-customerid").widget('getValue');
            var pricecode = $("#report-salesGoodsQuotationReport-pricecode").val();
            if ((null == customer || "" == customer) && (null == pricecode || "" == pricecode)) {
                $.messager.alert("提醒", "抱歉，查询条件客户、价格套必填一项");
                return false;
            }
            var queryJSON = $("#report-form-salesGoodsQuotationReport").serializeJSON();
            $("#report-datagrid-salesGoodsQuotationReport").datagrid({
                url: 'report/sales/showSalesGoodsQuotationReportData.do',
                pageNumber: 1,
                queryParams: queryJSON
            }).datagrid("columnMoving");
        });
        //重置
        $("#report-reload-salesGoodsQuotationReport").click(function () {
            $("#report-salesGoodsQuotationReport-brandid").widget('clear');
            $("#report-form-salesGoodsQuotationReport")[0].reset();
            $("#report-salesGoodsQuotationReport-customerid").widget('clear');
            $("#report-salesGoodsQuotationReport-pricecode").val("");
            $('#report-datagrid-salesGoodsQuotationReport').datagrid('loadData', {total: 0, rows: []});
            $("#report-datagrid-salesGoodsQuotationReport").datagrid('clearChecked');
            $("#report-datagrid-salesGoodsQuotationReport").datagrid('clearSelections');
        });

        $("#report-export-salesGoodsQuotationReport").click(function () {
            var queryJSON = $("#report-form-salesGoodsQuotationReport").serializeJSON();
            //获取排序规则
            var objecr = $("#report-datagrid-salesGoodsQuotationReport").datagrid("options");
            if (null != objecr.sortName && null != objecr.sortOrder) {
                queryJSON["sort"] = objecr.sortName;
                queryJSON["order"] = objecr.sortOrder;
            }
            var queryParam = JSON.stringify(queryJSON);
            var url = "report/sales/exportGoodsQuotationReportData.do";
            exportByAnalyse(queryParam, "客户商品报价单报表", "report-datagrid-salesGoodsQuotationReport", url);
        });

        //回车事件
        controlQueryAndResetByKey("report-query-salesGoodsQuotationReport", "report-reload-salesGoodsQuotationReport");
        <security:authorize url="/report/sales/exportGoodsQuotationReportData.do">
        $("#report-export-salesGoodsQuotationReportBtn").click(function () {
            var customer = $("#report-salesGoodsQuotationReport-customerid").widget('getValue');
            var pricecode = $("#report-salesGoodsQuotationReport-pricecode").val();
            if ((null == customer || "" == customer) && (null == pricecode || "" == pricecode)) {
                $.messager.alert("提醒", "抱歉，查询条件客户、价格套必填一项");
                return false;
            }
            var rows = $("#report-datagrid-salesGoodsQuotationReport").datagrid('getChecked');

            //查询参数直接添加在url中
            var goodsidarr = new Array();
            if (null != rows && rows.length > 0) {
                for (var i = 0; i < rows.length; i++) {
                    if (rows[i].goodsid && rows[i].goodsid != "") {
                        goodsidarr.push(rows[i].goodsid);
                    }
                }
            }
            $("#report-export-salesGoodsQuotationReport").trigger("click");
        });
        </security:authorize>
    });
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "listPage-salesGoodsQuotation-dialog-print",
            code: "report_salesGoodsQuotation",
            queryForm: "report-form-salesGoodsQuotationReport",
            url_preview: "print/report/salesGoodsQuotationReportPrintView.do",
            url_print: "print/report/salesGoodsQuotationReportPrint.do",
            btnPreview: "report-printview-salesGoodsQuotationReport",
            btnPrint: "report-print-salesGoodsQuotationReport",
            getData: getData
        });
        function getData(tableId, printParam) {
            var customer = $("#report-salesGoodsQuotationReport-customerid").widget('getValue');
            var pricecode = $("#report-salesGoodsQuotationReport-pricecode").val();
            if ((null == customer || "" == customer) && (null == pricecode || "" == pricecode)) {
                $.messager.alert("提醒", "抱歉，查询条件客户、价格套必填一项");
                return false;
            }
            var rows = $("#report-datagrid-salesGoodsQuotationReport").datagrid('getChecked');
            if (null == rows || rows.length == 0) {
                $.messager.alert("提醒", '请选择要打印的数据');
                return false;
            }
            var goodsidarr = [];
            for (var i = 0; i < rows.length; i++) {
                if (rows[i].goodsid && rows[i].goodsid != "") {
                    goodsidarr.push(rows[i].goodsid);
                }
            }
            if (goodsidarr.length == 0) {
                $.messager.alert("提醒", '请选择要打印的数据');
                return false;
            }
            printParam.goodsidarr = goodsidarr.join(",");
            return true;
        }
    });
</script>
<%--打印结束 --%>
</body>
</html>