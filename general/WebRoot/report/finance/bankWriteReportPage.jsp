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
<table id="report-datagrid-bankWriteReport"></table>
<div id="report-toolbar-bankWriteReport" style="padding: 0px">
    <div class="buttonBG">
        <security:authorize url="/report/finance/bankWriteReportExport.do">
            <a href="javaScript:void(0);" id="report-buttons-bankWriteReportPage" class="easyui-linkbutton"
               iconCls="button-export" plain="true" title="导出">导出</a>
        </security:authorize>
        <security:authorize url="/report/finance/financeBankWriteReportPrintView.do">
            <a href="javaScript:void(0);" id="report-printview-bankWriteReportPage" class="easyui-linkbutton"
               iconCls="button-preview" plain="true" title="打印预览">打印预览</a>
        </security:authorize>
        <security:authorize url="/report/finance/financeBankWriteReportPrint.do">
            <a href="javaScript:void(0);" id="report-print-bankWriteReportPage" class="easyui-linkbutton"
               iconCls="button-print" plain="true" title="打印">打印</a>
        </security:authorize>
    </div>
    <form action="" id="report-query-form-bankWriteReport" method="post">
        <table>
            <tr>
                <td>核销日期:</td>
                <td><input type="text" id="report-bankWriteReport-businessdatestart" name="businessdate1"
                           value="${today }" style="width:100px;" class="Wdate"
                           onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})"/>
                    到 <input type="text" id="report-bankWriteReport-businessdateend" name="businessdate2"
                             value="${today }" class="Wdate" style="width:100px;"
                             onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})"/></td>
                <td>客户名称:</td>
                <td><input type="text" id="report-bankWriteReport-customerid" name="customerid" style="width: 180px;"/>
                </td>
                <td>银行名称:</td>
                <td><input type="text" id="report-bankWriteReport-bank" name="bank" style="width: 130px;"/></td>
                <td colspan="2">
                    <a href="javaScript:void(0);" id="report-queay-bankWriteReport" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="report-reload-bankWriteReport" class="button-qr">重置</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="collectionorder-dialog-bankWriteReport"></div>
<script type="text/javascript">
    var SR_footerobject = null;
    var initQueryJSON = $("#report-query-form-bankWriteReport").serializeJSON();
    $(function () {
        $("#report-datagrid-bankWriteReport").datagrid({
            columns: [[
                {field: 'customerid', title: '客户编号', width: 60},
                {field: 'customername', title: '客户名称', width: 150},
                {field: 'pid', title: '总店编号', width: 60},
                {field: 'pname', title: '总店名称', width: 80},
                {
                    field: 'totalamount', title: '总金额', resizable: true, align: 'right',
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                }
                <c:forEach items="${bankList }" var="list">
                , {
                    field: 'amount${list.id}', title: '${list.name}', align: 'right', resizable: true, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                }
                </c:forEach>
            ]],
            method: 'post',
            title: '',
            fit: true,
            rownumbers: true,
            showFooter: true,
            singleSelect: true,
            pagination: true,
            pageSize: 100,
            toolbar: '#report-toolbar-bankWriteReport',
            onDblClickRow: function (rowIndex, rowData) {
                var writeoffdate1 = $("#report-bankWriteReport-businessdatestart").val();
                var writeoffdate2 = $("#report-bankWriteReport-businessdateend").val();
                $('#collectionorder-dialog-bankWriteReport').dialog({
                    title: '【' + rowData.customername + '】对应收款单',
                    width: 800,
                    height: 400,
                    closed: false,
                    modal: true,
                    maximizable: true,
                    cache: false,
                    resizable: true,
                    href: 'account/receivable/showBankWriteReportCollectionListPage.do?customerid=' + rowData.customerid + '&writeoffdate1=' + writeoffdate1 + '&writeoffdate2=' + writeoffdate2
                });
            }
        }).datagrid("columnMoving");

        $("#report-bankWriteReport-customerid").customerWidget({
            singleSelect: true,
            ishead: true,
            isall: true
        });
        $("#report-bankWriteReport-bank").widget({
            referwid: 'RL_T_BASE_FINANCE_BANK',
            width: 150,
            singleSelect: true
        });
        //回车事件
        controlQueryAndResetByKey("report-queay-bankWriteReport", "report-reload-bankWriteReport");

        //查询
        $("#report-queay-bankWriteReport").click(function () {
            //把form表单的name序列化成JSON对象
            var queryJSON = $("#report-query-form-bankWriteReport").serializeJSON();
            $("#report-datagrid-bankWriteReport").datagrid({
                url: 'report/finance/showBankWriteReportData.do',
                pageNumber: 1,
                queryParams: queryJSON
            });
        });
        //重置
        $("#report-reload-bankWriteReport").click(function () {
            $("#report-bankWriteReport-customerid").customerWidget("clear");
            $("#report-bankWriteReport-bank").widget("clear");
            $("#report-query-form-bankWriteReport").form("reset");
            var queryJSON = $("#report-query-form-bankWriteReport").serializeJSON();
            $("#report-datagrid-bankWriteReport").datagrid('loadData', {total: 0, rows: [], footer: []});
        });

        $("#report-buttons-bankWriteReportPage").Excel('export', {
            queryForm: "#report-query-form-bankWriteReport", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
            type: 'exportUserdefined',
            name: '客户银行回笼情况表',
            url: 'report/finance/exportBankWriteReportData.do'
        });
    });
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "listPage-finance-dialog-print",
            code: "finance_bankwrite",
            queryForm: "report-query-form-bankWriteReport",
            url_preview: "print/report/financeBankWriteReportPrintView.do",
            url_print: "print/report/financeBankWriteReportPrint.do",
            btnPreview: "report-printview-bankWriteReportPage",
            btnPrint: "report-print-bankWriteReportPage",
            getData: getData
        });
        function getData(tableId, printParam) {
            var businessdate1 = $("#report-bankWriteReport-businessdatestart").val();
            var businessdate2 = $("#report-bankWriteReport-businessdateend").val();
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
