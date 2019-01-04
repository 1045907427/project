<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>销售单据核对页面</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>
</head>

<body>
<div id="account-datagrid-toolbar-billcheck" style="padding: 0px">
    <div class="buttonBG">
        <security:authorize url="/report/finance/accountBillCheckExportReportBtn.do">
            <a href="javaScript:void(0);" id="account-export-billcheck" class="easyui-linkbutton"
               iconCls="button-export" plain="true">全局导出</a>
        </security:authorize>
        <security:authorize url="/report/finance/salesBillCheckPrintViewBtn.do">
            <a href="javaScript:void(0);" id="report-printview-billcheck" class="easyui-linkbutton"
               iconCls="button-preview" plain="true" title="打印预览">打印预览</a>
        </security:authorize>
        <security:authorize url="/report/finance/salesBillCheckPrintBtn.do">
            <a href="javaScript:void(0);" id="report-print-billcheck" class="easyui-linkbutton" iconCls="button-print"
               plain="true" title="打印">打印</a>
        </security:authorize>
    </div>
    <form action="" id="account-form-query-billcheck" method="post">
        <table class="querytable">
            <tr>
                <td>业务日期:</td>
                <td><input type="text" name="businessdate1" id="account-form-businessdate1" value="${today }"
                           style="width:100px;" class="Wdate"
                           onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})"/> 到 <input type="text"
                                                                                                          name="businessdate2"
                                                                                                          id="account-form-businessdate2"
                                                                                                          value="${today }"
                                                                                                          class="Wdate"
                                                                                                          style="width:100px;"
                                                                                                          onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})"/>
                </td>
                <td>客户名称:</td>
                <td><input type="text" name="customerid" id="account-customerid-billcheck"/></td>
                <td>单据数:</td>
                <td><select name="billnums" class="len150">
                    <option></option>
                    <option value="0">等于0</option>
                    <option value="1" selected="selected">不等于0</option>
                </select></td>
            </tr>
            <tr>
                <td colspan="4"></td>
                <td colspan="2">
                    <a href="javaScript:void(0);" id="account-queay-billcheck" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="account-reload-billcheck" class="button-qr">重置</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<table id="account-datagrid-billcheck"></table>
<script type="text/javascript">
    var SR_footerobject = null;
    var intiQueryJSON = $("#account-form-query-billcheck").serializeJSON();
    $(function () {
        var tableColumnListJson = $("#account-datagrid-billcheck").createGridColumnLoad({
            frozenCol: [[{field: 'idok', checkbox: true, isShow: true}]],
            commonCol: [[
                {field: 'customerid', title: '客户编码', sortable: true, width: '60'},
                {field: 'customername', title: '客户名称', width: 210},
                {field: 'pcustomerid', title: '总店编码', sortable: true, width: 60},
                {field: 'pcustomername', title: '总店名称', sortable: true, width: 60, hidden: true},
                <c:if test="${map.initsendamount == 'initsendamount'}">
                {
                    field: 'initsendamount', title: '发货单金额', resizable: true, align: 'right',
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                </c:if>
                <c:if test="${map.sendamount == 'sendamount'}">
                {
                    field: 'sendamount', title: '发货出库金额', resizable: true, align: 'right',
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                </c:if>
                <c:if test="${map.pushbalanceamount == 'pushbalanceamount'}">
                {
                    field: 'pushbalanceamount', title: '冲差金额', resizable: true, align: 'right',
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                </c:if>
                <c:if test="${map.directreturnamount == 'directreturnamount'}">
                {
                    field: 'directreturnamount', title: '直退金额', resizable: true, align: 'right',
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                </c:if>
                <c:if test="${map.checkreturnamount == 'checkreturnamount'}">
                {
                    field: 'checkreturnamount', title: '退货金额', resizable: true, sortable: true, align: 'right',
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                </c:if>
                {
                    field: 'returnamount', title: '退货合计', resizable: true, align: 'right',
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                <c:if test="${map.salesamount == 'salesamount'}">
                {
                    field: 'salesamount', title: '销售金额', resizable: true, align: 'right',
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                </c:if>
                <c:if test="${map.billnums == 'billnums'}">
                {field: 'billnums', title: '单据数', resizable: true, align: 'right'},
                </c:if>
                {
                    field: 'inputsalesamount', title: '录入销售金额', resizable: true, align: 'right',
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                {field: 'inputbillnums', title: '录入单据数', resizable: true, align: 'right'},
                {field: 'remark', title: '备注', width: '120', align: 'right'}
            ]]
        });
        $("#account-datagrid-billcheck").datagrid({
            authority: tableColumnListJson,
            frozenColumns: tableColumnListJson.frozen,
            columns: tableColumnListJson.common,
            fit: true,
            method: 'post',
            rownumbers: true,
            pagination: true,
            showFooter: true,
            singleSelect: false,
            checkOnSelect: true,
            selectOnCheck: true,
            pageSize: 100,
            rowStyler: function (index, row) {
                if (undefined != row.eqflag && !row.eqflag) {
                    return 'background-color:#DFF1D1';
                }
            },
            toolbar: '#account-datagrid-toolbar-billcheck',
            onLoadSuccess: function () {
                var footerrows = $(this).datagrid('getFooterRows');
                if (null != footerrows && footerrows.length > 0) {
                    SR_footerobject = footerrows[0];
                    countTotalAmount();
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
        //客户名称
        $("#account-customerid-billcheck").widget({
            referwid: 'RL_T_BASE_SALES_CUSTOMER',
            width: 180,
            onlyLeafCheck: false,
            singleSelect: true
        });

        //回车事件
        controlQueryAndResetByKey("account-queay-billcheck", "account-reload-billcheck");

        //查询
        $("#account-queay-billcheck").click(function () {
            var queryJSON = $("#account-form-query-billcheck").serializeJSON();
            $("#account-datagrid-billcheck").datagrid({
                url: 'account/receivable/showSalesBillCheckList.do',
                pageNumber: 1,
                queryParams: queryJSON
            }).datagrid("columnMoving");
        });
        //重置
        $("#account-reload-billcheck").click(function () {
            $("#account-form-query-billcheck")[0].reset();
            $("#account-customerid-billcheck").widget('clear');
            $("#account-datagrid-billcheck").datagrid("loadData", []);
        });

        //导出
        $("#account-export-billcheck").click(function () {
            //封装查询条件
            var objecr = $("#account-datagrid-billcheck").datagrid("options");
            var queryParam = objecr.queryParams;
            if (null != objecr.sortName && null != objecr.sortOrder) {
                queryParam["sort"] = objecr.sortName;
                queryParam["order"] = objecr.sortOrder;
            }
            var queryParam = JSON.stringify(queryParam);
            var url = "report/finance/exportSalesBillCheckReportList.do";
            exportByAnalyse(queryParam, "销售单据核对报表", 'account-datagrid-billcheck', url);
        });
    });

    function countTotalAmount() {
        var rows = $("#account-datagrid-billcheck").datagrid('getChecked');
        var salesamount = 0;
        var billnums = 0;
        var inputsalesamount = 0;
        var inputbillnums = 0;

        for (var i = 0; i < rows.length; i++) {
            salesamount = Number(salesamount) + Number(rows[i].salesamount == undefined ? 0 : rows[i].salesamount);
            billnums = Number(billnums) + Number(rows[i].billnums == undefined ? 0 : rows[i].billnums);
            inputsalesamount = Number(inputsalesamount) + Number(rows[i].inputsalesamount == undefined ? 0 : rows[i].inputsalesamount);
            inputbillnums = Number(inputbillnums) + Number(rows[i].inputbillnums == undefined ? 0 : rows[i].inputbillnums);
        }
        var foot = [{
            customername: '选中合计',
            salesamount: salesamount,
            billnums: billnums,
            inputsalesamount: inputsalesamount,
            inputbillnums: inputbillnums
        }];
        if (null != SR_footerobject) {
            foot.push(SR_footerobject);
        }
        $("#account-datagrid-billcheck").datagrid("reloadFooter", foot);
    }
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "listPage-salesbillcheck-dialog-print",
            code: "finance_salesbillcheck",
            queryForm: "account-form-query-billcheck",
            url_preview: "print/account/salesBillCheckPrintView.do",
            url_print: "print/account/salesBillCheckPrint.do",
            btnPreview: "report-printview-billcheck",
            btnPrint: "report-print-billcheck",
            getData: getData
        });
        function getData(tableId, printParam) {
            var businessdate1 = $("#account-form-businessdate1").val();
            var businessdate2 = $("#account-form-businessdate2").val();
            if (businessdate1 == "" || businessdate2 == "") {
                $.messager.alert("提醒", '请输入业务日期时间段以便打印预览');
                return false;
            }
            return true;
        }
    });
</script>
<%--打印结束 --%>
</body>
</html>
