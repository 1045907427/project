<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>销售退货验收列表</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center'">
        <div id="sales-queryDiv-rejectBillCheckListPage" style="padding:0px;height:auto">
            <div class="buttonBG" id="sales-buttons-rejectBillCheckListPage" style="height:26px;"></div>
            <form id="sales-queryForm-rejectBillCheckListPage">
                <table class="querytable">
                    <tr>
                        <td>业务日期:</td>
                        <td class="tdinput"><input type="text" name="businessdate" style="width:100px;" class="Wdate"
                                                   onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/> 到 <input
                                type="text" name="businessdate1" class="Wdate" style="width:100px;"
                                onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/></td>
                        <td>销售部门：</td>
                        <td class="tdinput">
                            <input type="text" id="sales-salesDept-orderListPage" name="salesdept"/>
                        </td>
                        <td>销售内勤:</td>
                        <td class="tdinput"><input type="text" id="sales-indooruserid-rejectBillCheckListPage"
                                                   name="indooruserid"/></td>
                    </tr>
                    <tr>
                        <td>验收日期:</td>
                        <td class="tdinput"><input type="text" name="ysbusinessdate" style="width:100px;" class="Wdate"
                                                   onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/> 到 <input
                                type="text" name="ysbusinessdate1" class="Wdate" style="width:100px;"
                                onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/></td>
                        <td>验收状态:</td>
                        <td class="tdinput">
                            <select id="sales-isinvoice-rejectBillCheckListPage" name="isinvoice" style="width:150px;">
                                <option value="0">未验收</option>
                                <option value="3">已验收</option>
                            </select>
                        </td>
                        <td>编&nbsp;&nbsp;号:</td>
                        <td><input type="text" name="id" style="width: 140px;"/></td>
                    </tr>
                    <tr>
                        <td>商品档案:</td>
                        <td class="tdinput">
                            <input type="text" id="storage-goods-saleRejectEnterPage" name="goodsid"
                                   style="width: 225px;"/>
                        </td>
                        <td>退货类型:</td>
                        <td class="tdinput">
                            <select name="billtype" style="width:150px;">
                                <option></option>
                                <option value="1">直退退货</option>
                                <option value="2">售后退货</option>
                            </select>
                            <%--<input type="hidden" id="sales-statusarr-rejectBillCheckListPage" name="statusarr"--%>
                                   <%--value="3"/>--%>
                        </td>
                        <td>状态:</td>
                        <td class="tdinput">
                            <select name="status" style="width:140px;">
                                <option selected="selected"></option>
                                <option value="3">审核通过</option>
                                <option value="4">已关闭</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>客&nbsp;&nbsp;户:</td>
                        <td class="tdinput">
                            <input type="text" id="sales-customer-rejectBillCheckListPage" name="customerid"/>
                        </td>
                        <td>打印状态:</td>
                        <td class="tdinput">
                            <select name="ysprintsign" style="width:150px;">
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
                            <input type="hidden" name="queryysprinttimes" value="0" style="width:60px;"/>
                        </td>
                        <td colspan="2" rowspan="2" class="tdbutton" style="padding-left: 3px">
                            <a href="javascript:;" id="sales-queay-rejectBillCheckListPage" class="button-qr">查询</a>
                            <a href="javaScript:;" id="sales-resetQueay-rejectBillCheckListPage"
                               class="button-qr">重置</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <table id="sales-datagrid-rejectBillCheckListPage"></table>
    </div>
</div>
<script type="text/javascript">
    var initQueryJSON = $("#sales-queryForm-rejectBillCheckListPage").serializeJSON();
    $(function () {
        $("#sales-buttons-rejectBillCheckListPage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/sales/receipteListExport.do">
                {
                    type: 'button-export',
                    attr: {
                        datagrid: "#sales-datagrid-rejectBillCheckListPage",
                        queryForm: "#sales-queryForm-rejectBillCheckListPage", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
                        type: 'exportUserdefined',
                        name: '销售退货验收列表',
                        url: 'sales/exportRejectBillList.do'
                    }
                },
                </security:authorize>
                {
                    type: 'button-commonquery',
                    attr: {
                        name: 't_sales_rejectbill',
                        //查询针对的表格id
                        datagrid: 'sales-datagrid-rejectBillCheckListPage',
                        plain: true
                    }
                },
                {}
            ],
            buttons: [
                <security:authorize url="/sales/checkMultiRejectBill.do">
                {
                    id: 'button-mulitAudit',
                    name: '批量验收',
                    iconCls: 'button-audit',
                    handler: function () {
                        var rows = $("#sales-datagrid-rejectBillCheckListPage").datagrid('getChecked');
                        if (rows.length == 0) {
                            $.messager.alert("提醒", "请选中需要审核的记录。");
                            return false;
                        }
                        $.messager.confirm("提醒", "确定审核这些退货通知单？", function (r) {
                            if (r) {
                                var ids = "";
                                for (var i = 0; i < rows.length; i++) {
                                    ids += rows[i].id + ',';
                                }
                                loading("审核中..");
                                $.ajax({
                                    url: 'sales/checkMultiRejectBill.do',
                                    dataType: 'json',
                                    type: 'post',
                                    data: {ids: ids},
                                    success: function (json) {
                                        loaded();
                                        $.messager.alert("提醒", json.msg);
                                        $("#sales-datagrid-rejectBillCheckListPage").datagrid('reload');
                                    },
                                    error: function () {
                                        loaded();
                                        $.messager.alert("错误", "审核出错");
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/rejectBillCheckPrintView.do">
                {
                    id: 'button-printview-rejectBillCheck',
                    name: '打印预览',
                    iconCls: 'button-preview',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/rejectBillCheckPrint.do">
                {
                    id: 'button-print-rejectBillCheck',
                    name: '打印',
                    iconCls: 'button-print',
                    handler: function () {
                    }
                },
                </security:authorize>
                {}],
            model: 'bill',
            type: 'list',
            tname: 't_sales_rejectbill'
        });
        //回车事件
        controlQueryAndResetByKey("sales-queay-rejectBillCheckListPage", "sales-resetQueay-rejectBillCheckListPage");
//        $("#sales-isinvoice-rejectBillCheckListPage").change(function () {
//            var isinvoice = $(this).val() || "";
//            if (isinvoice == 3) {
//                $("#sales-statusarr-rejectBillCheckListPage").val("3,4");
//            } else {
//                $("#sales-statusarr-rejectBillCheckListPage").val("3");
//            }
//        });
        $("#sales-queay-rejectBillCheckListPage").click(function () {
//            var isinvoice = $("#sales-isinvoice-rejectBillCheckListPage").val() || "";
//            if (isinvoice == 3) {
//                $("#sales-statusarr-rejectBillCheckListPage").val("3,4");
//            }
            var queryJSON = $("#sales-queryForm-rejectBillCheckListPage").serializeJSON();
            $("#sales-datagrid-rejectBillCheckListPage").datagrid('load', queryJSON);
        });
        $("#sales-resetQueay-rejectBillCheckListPage").click(function () {
            $("#sales-statuarr-rejectBillCheckListPage").val("3");
            $("#sales-customer-rejectBillCheckListPage").widget("clear");
            $("#storage-goods-saleRejectEnterPage").goodsWidget("clear");
            $("#sales-indooruserid-rejectBillCheckListPage").widget("clear");
            $("#sales-queryForm-rejectBillCheckListPage").form("reset");
            var queryJSON = $("#sales-queryForm-rejectBillCheckListPage").serializeJSON();
            $("#sales-datagrid-rejectBillCheckListPage").datagrid('load', queryJSON);
        });
        var dispatchBillListJson = $("#sales-datagrid-rejectBillCheckListPage").createGridColumnLoad({
            name: 't_sales_rejectbill',
            frozenCol: [[
                {field: 'idok', checkbox: true, isShow: true}
            ]],
            commonCol: [[
                {field: 'id', title: '编号', width: 120, align: 'left', sortable: true},
                {field: 'businessdate', title: '业务日期', width: 80, align: 'left', sortable: true},
                {field: 'customerid', title: '客户编码', width: 60, align: 'left', sortable: true},
                {field: 'customername', title: '客户名称', width: 100, align: 'left', isShow: true},
                {field: 'headcustomername', title: '总店名称', width: 80, align: 'left', isShow: true},
                {
                    field: 'billtype', title: '退货类型', width: 60, align: 'left', sortable: true,
                    formatter: function (value, row, index) {
                        if (value == "1") {
                            return "直退";
                        } else if (value == "2") {
                            return "售后退货";
                        }
                    }
                },
                {field: 'salesdept', title: '销售部门', width: 80, align: 'left', sortable: true},
                {field: 'salesuser', title: '客户业务员', width: 80, align: 'left', sortable: true},
                {
                    field: 'driverid', title: '司机', width: 80, align: 'left', sortable: true,
                    formatter: function (value, row, index) {
                        return row.drivername;
                    }
                },
                {
                    field: 'totaltaxamount', title: '金额', width: 80, align: 'right', isShow: true,
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'status', title: '状态', width: 60, align: 'left', sortable: true,
                    formatter: function (value, row, index) {
                        return getSysCodeName('status', value);
                    }
                },
                {
                    field: 'isinvoice', title: '抽单状态', width: 80, align: 'left', sortable: true,
                    formatter: function (value, row, index) {
                        if (value == "0") {
                            return "未申请";
                        }
                        else if (value == "1") {
                            return "已申请";
                        }
                        else if (value == "2") {
                            return "已核销";
                        }
                        else if (value == "3") {
                            return "未申请";
                        } else if (value == "4") {
                            return "申请中";
                        } else if (value == "5") {
                            return "部分核销";
                        }
                    }
                },
                {
                    field: 'isinvoicebill', title: '开票状态', width: 70, align: 'left', sortable: true,
                    formatter: function (value, row, index) {
                        if (value == "0") {
                            return "未开票";
                        } else if (value == "1") {
                            return "已开票";
                        } else if (value == "3") {
                            return "未开票";
                        } else if (value == "4") {
                            return "开票中";
                        }
                    }
                },
                {field: 'checkdate', title: '验收日期', width: 70, sortable: true},
                {
                    field: 'stopuserid', title: '验收人', width: 60, sortable: true,
                    formatter: function (value, rowData, index) {
                        return rowData.stopusername;
                    }
                },
                {
                    field: 'indooruserid', title: '销售内勤', width: 60, sortable: true,
                    formatter: function (value, rowData, index) {
                        return rowData.indoorusername;
                    }
                },
                {field: 'duefromdate', title: '应收日期', width: 80, align: 'left', sortable: true, hidden: true},
                {
                    field: 'printstate', title: '打印状态', width: 80, isShow: true,
                    formatter: function (value, rowData, index) {
                        if (rowData.ysprinttimes > 0) {
                            return "已打印";
                        } else if (rowData.ysprinttimes == -99) {
                            return "";
                        } else {
                            return "未打印";
                        }
                    }
                },
                {
                    field: 'ysprinttimes', title: '打印次数', width: 60, align: 'center',
                    formatter: function (value, rowData, index) {
                        if (value == -99) {
                            return "";
                        } else {
                            return value;
                        }
                    }
                },
                {field: 'addusername', title: '制单人', width: 80, sortable: true},
                {field: 'addtime', title: '制单时间', width: 80, sortable: true, hidden: true},
                {field: 'auditusername', title: '审核人', width: 80, sortable: true, hidden: true},
                {field: 'audittime', title: '审核时间', width: 80, sortable: true, hidden: true},
                {field: 'remark', title: '备注', width: 100}
            ]]
        });
        $("#sales-datagrid-rejectBillCheckListPage").datagrid({
            authority: dispatchBillListJson,
            frozenColumns: dispatchBillListJson.frozen,
            columns: dispatchBillListJson.common,
            fit: true,
            title: "",
            method: 'post',
            rownumbers: true,
            pagination: true,
            idField: 'id',
            singleSelect: false,
            checkOnSelect: true,
            selectOnCheck: true,
            sortName: 'addtime',
            sortOrder: 'desc',
            showFooter: true,
            //fitColumns:true,
            url: 'sales/getRejectBillList.do',
            queryParams: initQueryJSON,
            toolbar: '#sales-queryDiv-rejectBillCheckListPage',
            onDblClickRow: function (index, data) {
                top.addOrUpdateTab('sales/rejectBillCheckPage.do?type=edit&id=' + data.id, '销售退货验收');
            },
            onCheck: function (rowIndex, rowData) {
                countTotalAmount();
            },
            onUncheck: function (rowIndex, rowData) {
                countTotalAmount();
            },
            onCheckAll: function (rows) {
                countTotalAmount();
            },
            onUncheckAll: function (rows) {
                countTotalAmount();
            }
        }).datagrid("columnMoving");
        $("#sales-customer-rejectBillCheckListPage").widget({ //客户参照窗口
            referwid: 'RL_T_BASE_SALES_CUSTOMER_PARENT_2',
            singleSelect: true,
            width: 225
        });
        $("#storage-goods-saleRejectEnterPage").goodsWidget({});
        $("#sales-indooruserid-rejectBillCheckListPage").widget({
            referwid: 'RL_T_BASE_PERSONNEL_INDOORSTAFF',
            width: 140,
            singleSelect: true,
            initSelectNull: true
        });
        $("#sales-salesDept-orderListPage").widget({
            name: 't_sales_rejectbill',
            col: 'salesdept',
            width: 150,
            singleSelect: true
        });

    });
    function countTotalAmount() {
        var rows = $("#sales-datagrid-rejectBillCheckListPage").datagrid('getChecked');
        var totaltaxamount = 0;
        for (var i = 0; i < rows.length; i++) {
            totaltaxamount = Number(totaltaxamount) + Number(rows[i].totaltaxamount == undefined ? 0 : rows[i].totaltaxamount);
        }
        var footerrows = [{id: '选中金额', totaltaxamount: totaltaxamount}];
        $("#sales-datagrid-rejectBillCheckListPage").datagrid("reloadFooter", footerrows);
    }
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "rejectBillCheckListPage-dialog-print",
            code: "sales_rejectbillcheck",
            url_preview: "print/sales/rejectBillCheckPrintView.do",
            url_print: "print/sales/rejectBillCheckPrint.do",
            btnPreview: "button-printview-rejectBillCheck",
            btnPrint: "button-print-rejectBillCheck",
            printlimit: "${printlimit}",
            getData: function (tableId, printParam) {
                var data = $("#sales-datagrid-rejectBillCheckListPage").datagrid("getChecked");
                if (data == null || data.length == 0) {
                    $.messager.alert("提醒", "请选择至少一条记录");
                    return false;
                }
                var idarray = [];
                var printIds = [];
                for (var i = 0; i < data.length; i++) {
                    var isinvoice = data[i].isinvoice || 0;
                    if (isinvoice != '1' && isinvoice != '2' && isinvoice != '3' && isinvoice != '4' && isinvoice != '5') {
                        $.messager.alert("提醒", "抱歉，验收状态下才能打印");
                        return false;
                    }
                    idarray.push(data[i].id);
                    if (data[i].ysprinttimes > 0) {
                        printIds.push(data[i].id);
                    }
                }
                printParam.idarrs = idarray.join(",");
                printParam.printIds = printIds;
                return true;
            },
            onPrintSuccess: function (option) {
                var $grid = $("#sales-datagrid-rejectBillCheckListPage");
                var dataList = $grid.datagrid("getChecked");
                for (var i = 0; i < dataList.length; i++) {
                    if (dataList[i].ysprinttimes && !isNaN(dataList[i].ysprinttimes)) {
                        dataList[i].ysprinttimes = dataList[i].ysprinttimes + 1;
                    } else {
                        dataList[i].ysprinttimes = 1;
                    }
                    var rowIndex = $grid.datagrid("getRowIndex", dataList[i].id);
                    $grid.datagrid('updateRow', {
                        index: rowIndex,
                        row: dataList[i]
                    });
                }
            }
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
