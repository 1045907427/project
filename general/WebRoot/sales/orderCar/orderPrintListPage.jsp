<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>零售订单打印列表</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>
<body>
<table id="report-datagrid-orderPrintListPage"></table>
<div id="report-toolbar-orderPrintListPage" style="height: auto;padding-top: 0px">
    <div class="buttonBG" style="height: auto">
        <security:authorize url="/sales/orderCarPrintView.do">
            <a href="javaScript:void(0);" id="report-printview-orderPrintListPage" class="easyui-linkbutton"
               data-options="plain:true,iconCls:'button-preview'">打印预览</a>
        </security:authorize>
        <security:authorize url="/sales/orderCarPrintView.do">
            <a href="javaScript:void(0);" id="report-print-orderPrintListPage" class="easyui-linkbutton"
               data-options="plain:true,iconCls:'button-print'">打印</a>
        </security:authorize>
        <security:authorize url="/sales/orderCarByStoragePrintView.do">
            <a href="javaScript:void(0);" id="report-byStoragePrintview-orderPrintListPage" class="easyui-linkbutton"
               data-options="plain:true,iconCls:'button-preview'">仓库汇总打印预览</a>
        </security:authorize>
        <security:authorize url="/sales/orderCarByStoragePrintView.do">
            <a href="javaScript:void(0);" id="report-byStoragePrint-orderPrintListPage" class="easyui-linkbutton"
               data-options="plain:true,iconCls:'button-print'">仓库汇总打印</a>
        </security:authorize>
    </div>
    <form action="" method="post" id="report-form-orderPrintListPage">
        <table class="querytable">

            <tr>
                <td>业务日期:</td>
                <td class="tdinput">
                    <input type="text" id="sales-orderListPage-businessdatestart" name="businessdatestart"
                           style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"
                           value="${today }"/> 到 <input type="text" id="sales-orderListPage-businessdateend"
                                                        name="businessdateend" class="Wdate" style="width:100px;"
                                                        onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/>
                </td>
                <td>客户:</td>
                <td><input type="text" id="report-orderPrintListPage-customerid" name="customerid"
                           style="width:180px;"/></td>
                <td>打印状态:</td>
                <td>
                    <select id="report-orderPrintListPage-printsign" name="printsign" style="width:120px;">
                        <option></option>
                        <option value="1">未打印</option>
                        <%-- 特别
                        <option value="2">小于</option>
                        <option value="3">小于等于</option>
                         --%>
                        <option value="4">已打印</option>
                        <%-- 特别
                        <option value="5">大于等于</option>
                         --%>
                    </select>
                    <input type="hidden" id="report-orderPrintListPage-printtimes" name="queryprinttimes" value="0"
                           style="width:60px;"/>
                </td>
            </tr>
            <tr>
                <td>打印选项:</td>
                <td class="tdinput">
                    <select id="report-orderPrintListPage-printoption" style="width:150px;">
                        <option value="1">打印所选</option>
                        <option value="2">打印所有</option>
                    </select>
                </td>
                <td>仓库:</td>
                <td><input type="text" id="report-orderPrintListPage-storageid" name="storageid" style="width:100px;"/>
                </td>
                <td colspan="2" class="tdbutton">
                    <a href="javaScript:void(0);" id="report-query-orderPrintListPage" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="report-reload-orderPrintListPage" class="button-qr">重置</a>
                </td>
            </tr>

        </table>
    </form>
</div>
<script type="text/javascript">
    var initQueryJSON = $("#report-datagrid-orderPrintListPage").serializeJSON();
    $(function () {
        var tableColumnListJson = $("#report-datagrid-orderPrintListPage").createGridColumnLoad({
            name: 'storage_saleout',
            frozenCol: [[
                {field: 'idok', checkbox: true, isShow: true}
            ]],
            commonCol: [[
                {field: 'id', title: '编号', width: 130, sortable: true},
                {field: 'saleorderid', title: '销售订单编号', width: 130, sortable: true},
                {field: 'businessdate', title: '业务日期', width: 80, sortable: true},
                {
                    field: 'storageid', title: '出库仓库', width: 80, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.storagename;
                    }
                },
                {field: 'customerid', title: '客户编码', width: 60, sortable: true},
                {field: 'customername', title: '客户名称', width: 150, isShow: true},
                {
                    field: 'salesdept', title: '销售部门', width: 80, sortable: true, hidden: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.salesdeptname;
                    }
                },
                {
                    field: 'salesuser', title: '客户业务员', width: 70, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.salesusername;
                    }
                },
                {
                    field: 'sendamount', title: '发货出库金额', width: 80, align: 'right',
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'sendnotaxamount', title: '发货出库未税金额', width: 80, align: 'right', hidden: true,
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'sourcetype', title: '来源类型', width: 90, sortable: true, hidden: true,
                    formatter: function (value, rowData, rowIndex) {
                        return getSysCodeName("saleout-sourcetype", value);
                    }
                },
                {field: 'sourceid', title: '来源编号', width: 100, sortable: true},
                {
                    field: 'status', title: '状态', width: 60, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return getSysCodeName("status", value);
                    }
                },
                {
                    field: 'indooruserid', title: '销售内勤', width: 60, sortable: true,
                    formatter: function (value, rowData, index) {
                        return rowData.indoorusername;
                    }
                },
                {field: 'duefromdate', title: '应收日期', width: 80, hidden: true, sortable: true},
                {field: 'auditusername', title: '审核人', width: 60, sortable: true},
                {field: 'audittime', title: '审核时间', width: 120, sortable: true},
                {field: 'stopusername', title: '中止人', width: 80, hidden: true, sortable: true},
                {field: 'stoptime', title: '中止时间', width: 80, hidden: true, sortable: true},
                {
                    field: 'printstate', title: '打印状态', width: 80, isShow: true,
                    formatter: function (value, rowData, index) {
                        if (rowData.printtimes > 0) {
                            return "已打印";
                        } else if (rowData.printtimes == -99) {
                            return "";
                        } else {
                            return "未打印";
                        }
                    }
                },
                {field: 'printtimes', title: '打印次数', width: 60, hidden: true},
                {field: 'remark', title: '备注', width: 80, sortable: true},
                {field: 'addusername', title: '制单人', width: 60, sortable: true},
                {field: 'addtime', title: '制单时间', width: 120, sortable: true}
            ]]
        });
        $("#report-datagrid-orderPrintListPage").datagrid({
            authority: tableColumnListJson,
            frozenColumns: tableColumnListJson.frozen,
            columns: tableColumnListJson.common,
            method: 'post',
            idField: 'id',
            title: '',
            //pageList:[10,20,30,50,100,200,300],
            fit: true,
            rownumbers: true,
            pagination: true,
            singleSelect: false,
            checkOnSelect: true,
            selectOnCheck: true,
            pageSize: 100,
            toolbar: '#report-toolbar-orderPrintListPage',
            onBeforeLoad: function () {
            }
        });
        $("#report-orderPrintListPage-customerid").customerWidget({
            singleSelect: false,
            width: '210',
            required: false
        });
        $("#report-orderPrintListPage-storageid").widget({
            referwid: 'RL_T_BASE_STORAGE_INFO',
            width: 180,
            singleSelect: true
        });

        $("#report-query-orderPrintListPage").click(function () {
            /*
             var bussinessdate=$("#report-orderPrintListPage-bussinessdate").val();
             if(null == bussinessdate || "" == bussinessdate){
             $.messager.alert("提醒","抱歉，请选择业务日期");
             return false;
             }
             */
            var queryJSON = $("#report-form-orderPrintListPage").serializeJSON();
            $("#report-datagrid-orderPrintListPage").datagrid({
                url: 'sales/showOrderCarPrintListData.do',
                pageNumber: 1,
                queryParams: queryJSON
            }).datagrid("columnMoving");
        });
        //重置
        $("#report-reload-orderPrintListPage").click(function () {
            $("#report-form-orderPrintListPage")[0].reset();
            $("#report-orderPrintListPage-customerid").customerWidget('clear');
            $("#report-orderPrintListPage-groupby").val("customer");
            $('#report-datagrid-orderPrintListPage').datagrid('loadData', {total: 0, rows: []});
        });
        //回车事件
        controlQueryAndResetByKey("report-query-orderPrintListPage", "report-reload-orderPrintListPage");
    });
    $("#report-orderPrintListPage-printsign").change(function () {
        var val = $(this).val() || "";
        var printtimes = $.trim($("#report-orderPrintListPage-printtimes").val());
        if (val == "") {
            $("#report-orderPrintListPage-printtimes").val("");
        } else if (val == 1) {
            if (printtimes == "") {
                $("#report-orderPrintListPage-printtimes").val("0");
            }
        }
    });
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        function onPrintSuccess(option) {
            var $grid = $("#report-datagrid-orderPrintListPage");
            var dataList = $grid.datagrid("getChecked");
            for (var i = 0; i < dataList.length; i++) {
                if (dataList[i].printtimes && !isNaN(dataList[i].printtimes)) {
                    dataList[i].printtimes = dataList[i].printtimes + 1;
                } else {
                    dataList[i].printtimes = 1;
                }
                var rowIndex = $grid.datagrid('getRowIndex', dataList[i].id);
                $grid.datagrid('updateRow', {index: rowIndex, row: dataList[i]});
            }
        }
        AgReportPrint.init({
            id: "listPage-ordercar-dialog-print",
            code: "sales_ordercar",
            queryForm: "report-form-orderPrintListPage",
            url_preview: "print/sales/orderCarPrintView.do",
            url_print: "print/sales/orderCarPrint.do",
            btnPreview: "report-printview-orderPrintListPage",
            btnPrint: "report-print-orderPrintListPage",
            printlimit: "${printlimit}",
            getData: function (tableId, printParam) {
                var printopt = $("#report-orderPrintListPage-printoption").val();
                if (printopt == "1") {
                    var rows = $("#report-datagrid-orderPrintListPage").datagrid('getChecked');
                    if (rows.length == 0) {
                        $.messager.alert("提醒", "请选择需要打印预览的数据！");
                        return false;
                    }
                    var saleoutidarr = [];
                    var printIds = [];
                    for (var i = 0; i < rows.length; i++) {
                        if (rows[i].id && rows[i].id != "") {
                            saleoutidarr.push(rows[i].id);
                        }
                        if(rows[i].printtimes > 0) {
                            printIds.push(rows[i].id);
                        }
                    }
                    if (saleoutidarr.length == 0) {
                        $.messager.alert("提醒", '请选择要打印的数据');
                        return false;
                    }
                    printParam.saleoutidarr = saleoutidarr.join(",");
                    printParam.printIds = printIds;
                    printParam.groupby = 'storage';
                }
                return true;
            },
            onPrintSuccess: onPrintSuccess
        });
        //仓库汇总打印
        AgReportPrint.init({
            id: "listPage-ordercar-StoragePrint-dialog-print",
            code: "sales_ordercar",
            queryForm: "report-form-orderPrintListPage",
            url_preview: "print/sales/orderCarPrintView.do",
            url_print: "print/sales/orderCarPrint.do",
            btnPreview: "report-byStoragePrintview-orderPrintListPage",
            btnPrint: "report-byStoragePrint-orderPrintListPage",
            printlimit: "${printlimit}",
            getData: function (tableId, printParam) {
                var printopt = $("#report-orderPrintListPage-printoption").val();
                if (printopt == "1") {
                    var rows = $("#report-datagrid-orderPrintListPage").datagrid('getChecked');
                    if (rows.length == 0) {
                        $.messager.alert("提醒", "请选择需要打印预览的数据！");
                        return false;
                    }
                    var saleoutidarr = [];
                    for (var i = 0; i < rows.length; i++) {
                        if (rows[i].id && rows[i].id != "") {
                            saleoutidarr.push(rows[i].id);
                        }
                    }
                    if (saleoutidarr.length == 0) {
                        $.messager.alert("提醒", '请选择要打印的数据');
                        return false;
                    }
                    printParam.saleoutidarr = saleoutidarr.join(",");
                }
                return true;
            },
            onPrintSuccess: onPrintSuccess
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>