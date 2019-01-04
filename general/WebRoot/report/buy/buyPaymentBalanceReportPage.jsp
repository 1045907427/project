<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>采购付款差额报表</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>
</head>
<style>
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
</style>
<body>
<table id="report-datagrid-buyPaymentBalance"></table>
<div id="report-toolbar-buyPaymentBalance" style="padding: 0px">
    <div class="buttonBG">
        <security:authorize url="/report/buy/exportBuyPaymentBalanceReportData.do">
            <a href="javaScript:void(0);" id="report-buttons-buyPaymentBalancePage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
        </security:authorize>
    </div>
    <form action="" id="report-query-form-buyPaymentBalance" method="post">
        <table class="querytable">

            <tr>
                <td>业务日期:</td>
                <td><input type="text" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
                <td>采购员:</td>
                <td><input id="report-query-buyuser" type="text" name="buyuser" style="width: 130px;"/></td>
                <td>采购部门:</td>
                <td><input id="report-query-buydept" type="text" name="buydept" style="width: 130px;"/></td>
            </tr>
            <tr>
                <td>供 应 商:</td>
                <td><input id="report-query-supplier" type="text" name="supplier" style="width: 225px;"/></td>
                <td>品&nbsp;牌:</td>
                <td><input id="report-query-brand" type="text" name="brand" style="width: 210px;"/></td>
                <td colspan="2">
                    <a href="javaScript:void(0);" id="report-query-buyPaymentBalance" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="report-reload-buyPaymentBalance" class="button-qr">重置</a>
                </td>
            </tr>
            <tr>
                <td>小计列：</td>
                <td colspan="6">
                    <input type="checkbox" class="groupcols checkbox1" value="supplierid" id="report-query-supplierid"/>
                    <label class="divtext" for="report-query-supplierid">供应商</label>
                    <input type="checkbox" class="groupcols checkbox1" value="buydeptid" id="report-query-buydeptid"/>
                    <label class="divtext" for="report-query-buydeptid">采购部门</label>
                    <input type="checkbox" class="groupcols checkbox1" value="buyuserid" id="report-query-buyuserid"/>
                    <label class="divtext" for="report-query-buyuserid">采购员</label>
                    <input type="checkbox" class="groupcols checkbox1" value="brandid" id="report-query-brandid"/>
                    <label class="checkbox1" for="report-query-brandid">品牌</label>
                    <input id="report-query-groupcols" type="hidden" name="groupcols"/>
                </td>
            </tr>
        </table>
    </form>
</div>
<script type="text/javascript">
var SR_footerobject  = null;
var initQueryJSON = $("#report-query-form-buyPaymentBalance").serializeJSON();
$(function() {
    var tableColumnListJson = $("#report-datagrid-buyPaymentBalance").createGridColumnLoad({
        frozenCol: [[
            {field: 'idok', checkbox: true, isShow: true}
        ]],
        commonCol: [[
            {field: 'supplierid', title: '供应商编码', width: 70, align: 'left'},
            {field: 'suppliername', title: '供应商名称', width: 250, align: 'left', sortable: true, isShow: true},
            {field: 'buydeptid', title: '采购部门', sortable: true, width: 80,
                formatter: function (value, rowData, rowIndex) {
                    return rowData.buydeptname;
                }
            },
            {field: 'buyuserid', title: '采购员', sortable: true, width: 80,
                formatter: function (value, rowData, rowIndex) {
                    return rowData.buyusername;
                }
            },
            {field: 'brandid', title: '品牌名称', sortable: true, width: 80,
                formatter: function (value, rowData, rowIndex) {
                    return rowData.brandname;
                }
            },
            {field: 'arrivalamount', title: '进货金额', resizable: true, sortable: true, align: 'right',
                formatter: function (value, rowData, rowIndex) {
                    return formatterMoney(value);
                }
            },
            {field: 'invoiceamount', title: '开票金额', resizable: true, sortable: true, align: 'right',
                formatter: function (value, rowData, rowIndex) {
                    return formatterMoney(value);
                }
            },
            {field: 'paybalance', title: '差额', resizable: true, sortable: true, align: 'right',
                formatter: function (value, rowData, rowIndex) {
                    return formatterMoney(value);
                }
            }
        ]]
    });
    $("#report-datagrid-buyPaymentBalance").datagrid({
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
        toolbar: '#report-toolbar-buyPaymentBalance',
        onLoadSuccess: function () {
            var footerrows = $(this).datagrid('getFooterRows');
            if (null != footerrows && footerrows.length > 0) {
                SR_footerobject = footerrows[0];
            }
        },
        onCheckAll: function () {
            countTotalAmount();
        },
        onUncheckAll: function () {
            countTotalAmount();
        },
        onCheck: function () {
            countTotalAmount();
        },
        onUncheck: function () {
            countTotalAmount();
        }
    }).datagrid("columnMoving");

    $("#report-query-supplier").widget({
        referwid: 'RL_T_BASE_BUY_SUPPLIER',
        width: 225,
        onlyLeafCheck: false,
        singleSelect: false
    });
    $("#report-query-brand").widget({
        referwid: 'RL_T_BASE_GOODS_BRAND',
        width: 130,
        singleSelect: false
    });
    $("#report-query-buyuser").widget({
        referwid: 'RL_T_BASE_PERSONNEL_BUYER',
        width: 130,
        singleSelect: false
    });
    $("#report-query-buydept").widget({
        referwid: 'RL_T_BASE_DEPARTMENT_BUYER',
        width: 130,
        onlyLeafCheck: false,
        singleSelect: false
    });
    //回车事件
    controlQueryAndResetByKey("report-query-buyPaymentBalance", "report-reload-buyPaymentBalance");

    $(".groupcols").click(function () {
        var cols = "";
        $("#report-query-groupcols").val("");
        $(".groupcols").each(function () {
            if ($(this).attr("checked")) {
                if (cols == "") {
                    cols = $(this).val();
                } else {
                    cols += "," + $(this).val();
                }
                $("#report-query-groupcols").val(cols);
            }
        });
    });
    //查询
    $("#report-query-buyPaymentBalance").click(function () {
        //把form表单的name序列化成JSON对象
        setColumn(0);
        var queryJSON = $("#report-query-form-buyPaymentBalance").serializeJSON();
        $("#report-datagrid-buyPaymentBalance").datagrid({
            url: 'report/buy/showBuyPaymentBalanceReportData.do',
            pageNumber: 1,
            queryParams: queryJSON
        }).datagrid("columnMoving");
    });
    //重置
    $("#report-reload-buyPaymentBalance").click(function () {
        $("#report-query-supplier").supplierWidget("clear");
        $("#report-query-brand").widget("clear");
        $(".groupcols").each(function () {
            if ($(this).attr("checked")) {
                $(this)[0].checked = false;
            }
        });
        $("#report-query-groupcols").val("");

        $("#report-datagrid-buyPaymentBalance").datagrid('showColumn', "supplierid");
        $("#report-datagrid-buyPaymentBalance").datagrid('showColumn', "suppliername");
        $("#report-datagrid-buyPaymentBalance").datagrid('showColumn', "buyuserid");
        $("#report-datagrid-buyPaymentBalance").datagrid('showColumn', "buydeptid");

        $("#report-query-form-buyPaymentBalance").form("reset");
        $("#report-datagrid-buyPaymentBalance").datagrid('loadData', {total: 0, rows: []});
    });

    $("#report-buttons-buyPaymentBalancePage").Excel('export', {
        queryForm: "#report-query-form-buyPaymentBalance", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
        type: 'exportUserdefined',
        name: '采购付款差额报表',
        url: 'report/buy/exportBuyPaymentBalanceReportData.do'
    });
});
function setColumn(qtype) {
    if (qtype == null || typeof(qtype) == "undefined") {
        qtype = 0;
    } else if (qtype != 1) {
        type = 0;
    }
    var cols = "";
    if (qtype == 1) {
        cols = $("#report-advancedQuery-groupcols").val();
    } else {
        cols = $("#report-query-groupcols").val();
    }
    if (cols != "") {
        $("#report-datagrid-buyPaymentBalance").datagrid('hideColumn', "supplierid");
        $("#report-datagrid-buyPaymentBalance").datagrid('hideColumn', "suppliername");
        $("#report-datagrid-buyPaymentBalance").datagrid('hideColumn', "brandid");
        $("#report-datagrid-buyPaymentBalance").datagrid('hideColumn', "buyuserid");
        $("#report-datagrid-buyPaymentBalance").datagrid('hideColumn', "buydeptid");
    }
    else {
        $("#report-datagrid-buyPaymentBalance").datagrid('hideColumn', "supplierid");
        $("#report-datagrid-buyPaymentBalance").datagrid('hideColumn', "suppliername");
        $("#report-datagrid-buyPaymentBalance").datagrid('showColumn', "brandid");
        $("#report-datagrid-buyPaymentBalance").datagrid('hideColumn', "buyuserid");
        $("#report-datagrid-buyPaymentBalance").datagrid('hideColumn', "buydeptid");
    }
    var colarr = cols.split(",");
    for (var i = 0; i < colarr.length; i++) {
        var col = colarr[i];
        if (col == "supplierid") {
            $("#report-datagrid-buyPaymentBalance").datagrid('showColumn', "supplierid");
            $("#report-datagrid-buyPaymentBalance").datagrid('showColumn', "suppliername");
        } else if (col == "brandid") {
            $("#report-datagrid-buyPaymentBalance").datagrid('showColumn', "brandid");
            //$("#report-datagrid-buyPaymentBalance").datagrid('showColumn', "buydeptid");
        } else if (col == "buyuserid") {
            $("#report-datagrid-buyPaymentBalance").datagrid('showColumn', "buyuserid");
            $("#report-datagrid-buyPaymentBalance").datagrid('showColumn', "buydeptid");
        } else if (col == "buydeptid") {
            $("#report-datagrid-buyPaymentBalance").datagrid('showColumn', "buydeptid");
        }
    }
}

function countTotalAmount() {
    var rows = $("#report-datagrid-buyPaymentBalance").datagrid('getChecked');
    if (null == rows || rows.length == 0) {
        var foot = [];
        if (null != SR_footerobject) {
            foot.push(SR_footerobject);
        }
        $("#report-datagrid-buyPaymentBalance").datagrid("reloadFooter", foot);
        return false;
    }
    var arrivalamount = 0;
    var invoiceamount = 0;
    var paybalance = 0;

    for (var i = 0; i < rows.length; i++) {
        arrivalamount = Number(arrivalamount) + Number(rows[i].arrivalamount == undefined ? 0 : rows[i].arrivalamount);
        invoiceamount = Number(invoiceamount) + Number(rows[i].invoiceamount == undefined ? 0 : rows[i].invoiceamount);
        paybalance = Number(paybalance) + Number(rows[i].paybalance == undefined ? 0 : rows[i].paybalance);

    }
    var foot = [
        {suppliername: '选中合计', arrivalamount: arrivalamount, invoiceamount: invoiceamount, paybalance: paybalance
        }
    ];
    if (null != SR_footerobject) {
        foot.push(SR_footerobject);
    }
    $("#report-datagrid-buyPaymentBalance").datagrid("reloadFooter", foot);
}
</script>
</body>
</html>
