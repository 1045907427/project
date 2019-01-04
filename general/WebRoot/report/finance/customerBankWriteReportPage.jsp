<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>银行回笼情况表</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>
</head>

<body>
<table id="report-datagrid-customerBankWriteReport"></table>
<div id="report-toolbar-customerBankWriteReport" style="padding: 0px">
    <div class="buttonBG">
        <security:authorize url="/report/finance/exportCustomerBankWriteReportData.do">
            <a href="javaScript:void(0);" id="report-buttons-customerBankWriteReportPage" class="easyui-linkbutton"
               iconCls="button-export" plain="true" title="导出">导出</a>
        </security:authorize>
        <security:authorize url="/report/finance/financeCustomerBankWriteReportPrintView.do">
            <a href="javaScript:void(0);" id="report-printview-customerBankWriteReportPage" class="easyui-linkbutton"
               iconCls="button-preview" plain="true" title="打印预览">打印预览</a>
        </security:authorize>
        <security:authorize url="/report/finance/financeCustomerBankWriteReportPrint.do">
            <a href="javaScript:void(0);" id="report-print-customerBankWriteReportPage" class="easyui-linkbutton"
               iconCls="button-print" plain="true" title="打印">打印</a>
        </security:authorize>
        <security:authorize url="/erpconnect/addCollectionAccountVouch.do">
            <a href="javaScript:void(0);" id="report-CollectionAccountVouch-customerBankWriteReportPage"
               class="easyui-linkbutton" iconCls="button-audit" plain="true" title="生成凭证">生成凭证</a>
        </security:authorize>
    </div>
    <form action="" id="report-query-form-customerBankWriteReport" method="post">
        <table>
            <tr>
                <td>核销日期:</td>
                <td><input type="text" id="report-customerBankWriteReport-businessdatestart" name="businessdate1"
                           value="${today }" style="width:100px;" class="Wdate"
                           onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})"/>
                    到 <input type="text" id="report-customerBankWriteReport-businessdateend" name="businessdate2"
                             value="${today }" class="Wdate" style="width:100px;"
                             onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})"/>
                </td>
                <td>客户名称:</td>
                <td colspan="3"><input type="text" id="report-customerBankWriteReport-customerid" name="customerid"
                                       style="width: 180px;"/></td>
            </tr>
            <tr>
                <td>银行名称:</td>
                <td><input type="text" id="report-customerBankWriteReport-bank" name="bank" style="width: 180px;"/></td>
                <td>统计方式:</td>
                <td>
                    <select id="report-customerBankWriteReport-grouptype" name="grouptype" style="width: 100px;">
                        <option value="1">全部</option>
                        <option value="2">银行</option>
                    </select>
                </td>
                <td colspan="2">
                    <a href="javaScript:void(0);" id="report-queay-customerBankWriteReport" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="report-reload-customerBankWriteReport" class="button-qr">重置</a>

                </td>
            </tr>
        </table>
    </form>
</div>
<div id="collectionorder-dialog-customerBankWriteReport"></div>
<div id="collectionorder-account-dialog"></div>
<script type="text/javascript">
    var SR_footerobject = null;
    var initQueryJSON = $("#report-query-form-customerBankWriteReport").serializeJSON();
    $(function () {
        $("#report-datagrid-customerBankWriteReport").datagrid({
            columns: [[
                {field: 'customerid', title: '客户编号', sortable: true, width: 60},
                {field: 'customername', title: '客户名称', width: 210},
                {field: 'pid', title: '总店编号', width: 60},
                {field: 'pname', title: '总店名称', width: 80},
                {
                    field: 'bank', title: '银行名称', width: 100, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.bankname;
                    }
                },
                {
                    field: 'amount', title: '回笼金额', resizable: true, align: 'right', sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                }
            ]],
            method: 'post',
            title: '',
            fit: true,
            rownumbers: true,
            showFooter: true,
            singleSelect: true,
            pagination: true,
            pageSize: 100,
            toolbar: '#report-toolbar-customerBankWriteReport',
            onDblClickRow: function (rowIndex, rowData) {
                var writeoffdate1 = $("#report-customerBankWriteReport-businessdatestart").val();
                var writeoffdate2 = $("#report-customerBankWriteReport-businessdateend").val();
                var grouptype = $("#report-customerBankWriteReport-grouptype").val();
                var bank = "";
                if ("2" == grouptype) {
                    bank = rowData.bank;
                }
                $('#collectionorder-dialog-customerBankWriteReport').dialog({
                    title: '【' + rowData.customername + '】对应收款单',
                    width: 800,
                    height: 400,
                    closed: false,
                    modal: true,
                    maximizable: true,
                    cache: false,
                    resizable: true,
                    href: 'account/receivable/showBankWriteReportCollectionListPage.do?customerid=' + rowData.customerid + '&writeoffdate1=' + writeoffdate1 + '&writeoffdate2=' + writeoffdate2 + '&bank=' + bank
                });
            }
        }).datagrid("columnMoving");

        $("#report-customerBankWriteReport-customerid").customerWidget({
            singleSelect: true,
            ishead: true,
            isall: true
        });
        $("#report-customerBankWriteReport-bank").widget({
            referwid: 'RL_T_BASE_FINANCE_BANK',
            width: 230,
            singleSelect: false,
            onSelect: function () {
                $("#report-customerBankWriteReport-grouptype").val("2");
            }
        });
        //回车事件
        controlQueryAndResetByKey("report-queay-customerBankWriteReport", "report-reload-customerBankWriteReport");

        //查询
        $("#report-queay-customerBankWriteReport").click(function () {
            //把form表单的name序列化成JSON对象
            var queryJSON = $("#report-query-form-customerBankWriteReport").serializeJSON();
            $("#report-datagrid-customerBankWriteReport").datagrid({
                url: 'report/finance/showCustomerBankWriteReportData.do',
                pageNumber: 1,
                queryParams: queryJSON
            });
        });
        //重置
        $("#report-reload-customerBankWriteReport").click(function () {
            $("#report-customerBankWriteReport-customerid").customerWidget("clear");
            $("#report-customerBankWriteReport-bank").widget("clear");
            $("#report-query-form-customerBankWriteReport").form("reset");
            $("#report-datagrid-customerBankWriteReport").datagrid('loadData', {total: 0, rows: [], footer: []});
        });

        $("#report-buttons-customerBankWriteReportPage").Excel('export', {
            queryForm: "#report-query-form-customerBankWriteReport", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
            type: 'exportUserdefined',
            name: '客户银行回笼情况表',
            url: 'report/finance/exportCustomerBankWriteReportData.do'
        });

        $("#report-CollectionAccountVouch-customerBankWriteReportPage").click(function () {
            var rows = $("#report-datagrid-customerBankWriteReport").datagrid('getRows');
            if (rows[0].customerid == undefined) {
                $.messager.alert("错误", "数据为空，无法生成凭证！");
                return false;
            }
            var queryJSON = $("#report-query-form-customerBankWriteReport").serializeJSON();
            var param = JSON.stringify(queryJSON);
            var grouptype = $("#report-customerBankWriteReport-grouptype").val();
            $("#collectionorder-account-dialog").dialog({
                title: '收款支付凭证（回笼）',
                width: 400,
                height: 260,
                closed: false,
                modal: true,
                cache: false,
                href: 'erpconnect/showCollectionAccountVouchPage.do?grouptype=' + grouptype,
                onLoad: function () {
                    $("#collectionAccount-param").val(param);
                }
            });
        });
    });
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "listPage-customerbankwrite-dialog-print",
            code: "finance_customerbankwrite",
            queryForm: "report-query-form-customerBankWriteReport",
            url_preview: "print/report/financeCustomerBankWriteReportPrintView.do",
            url_print: "print/report/financeCustomerBankWriteReportPrint.do",
            btnPreview: "report-printview-customerBankWriteReportPage",
            btnPrint: "report-print-customerBankWriteReportPage",
            getData: getData
        });
        function getData(tableId, printParam) {
            var businessdate1 = $("#report-customerBankWriteReport-businessdatestart").val();
            var businessdate2 = $("#report-customerBankWriteReport-businessdateend").val();
            if (businessdate1 == "" || businessdate2 == "") {
                $.messager.alert("提醒", '请输入业务日期时间段以便打印');
                return false;
            }
            return true;
        }
    });
</script>
<%--打印结束 --%>
</body>
</html>
