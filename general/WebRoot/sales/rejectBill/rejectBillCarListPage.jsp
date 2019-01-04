<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>车销退货申请单列表</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center'">
        <div id="sales-queryDiv-rejectBillListPage" style="padding:0px;height:auto">
            <div class="buttonBG" id="sales-buttons-rejectBillListPage" style="height:26px;"></div>
            <form id="sales-queryForm-rejectBillListPage">
                <table class="querytable">
                    <tr>
                        <td>业务日期:</td>
                        <td class="tdinput">
                            <input type="text" name="businessdate" style="width:100px;" class="Wdate"
                                   onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/>
                            到 <input type="text" name="businessdate1" class="Wdate" style="width:100px;"
                                     onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/></td>
                        <td>编 号:</td>
                        <td class="tdinput"><input type="text" name="id" class="len150"/></td>
                        <td>销售部门：</td>
                        <td class="tdinput">
                            <select name="salesdept" id="sales-salesDept-orderListPage" style="width:130px;">
                                <option></option>
                                <c:forEach items="${salesDept}" var="list">
                                    <option value="${list.id }">${list.name }</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>客户:</td>
                        <td><input type="text" id="sales-customer-rejectBillListPage" style="width: 225px;"
                                   name="customerid"/></td>
                        <td>状 态:</td>
                        <td>
                            <select name="status" style="width:150px;">
                                <option></option>
                                <option value="2" selected="selected">保存</option>
                                <option value="3">审核通过</option>
                                <option value="4">已关闭</option>
                            </select>
                            <input type="hidden" name="source" value="7"/>
                        </td>
                    </tr>
                    <tr>
                        <td>商品档案:</td>
                        <td>
                            <input type="text" id="storage-goods-saleRejectEnterPage" name="goodsid"
                                   style="width: 225px;"/>
                        </td>
                        <td>打印状态:</td>
                        <td>
                            <select name="printsign" style="width:150px;">
                                <option></option>
                                <option value="1">未打印</option>
                                <%-- 瑞家特别
                                <option value="2">小于</option>
                                <option value="3">小于等于</option>
                                 --%>
                                <option value="4">已打印</option>
                                <%-- 瑞家特别
                                <option value="5">大于等于</option>
                                 --%>
                            </select>
                            <input type="hidden" name="queryprinttimes" value="0" style="width:80px;"/>
                        </td>
                        <td rowspan="3" colspan="2" class="tdbutton">
                            <a href="javascript:;" id="sales-queay-rejectBillListPage" class="button-qr">查询</a>
                            <a href="javaScript:;" id="sales-resetQueay-rejectBillListPage" class="button-qr">重置</a>
                        </td>
                    </tr>
                    <tr>


                    </tr>
                </table>
            </form>
        </div>
        <table id="sales-datagrid-rejectBillListPage" data-options="border:false"></table>
    </div>
</div>
<script type="text/javascript">
    var initQueryJSON = $("#sales-queryForm-rejectBillListPage").serializeJSON();
    $(function () {
        $("#sales-customer-rejectBillListPage").customerWidget({ //客户参照窗口
        });
        $("#storage-goods-saleRejectEnterPage").goodsWidget({});
        //回车事件
        controlQueryAndResetByKey("sales-queay-rejectBillListPage", "sales-resetQueay-rejectBillListPage");

        $("#sales-queay-rejectBillListPage").click(function () {
            var queryJSON = $("#sales-queryForm-rejectBillListPage").serializeJSON();
            $("#sales-datagrid-rejectBillListPage").datagrid('load', queryJSON);
        });
        $("#sales-resetQueay-rejectBillListPage").click(function () {
            $("#sales-customer-rejectBillListPage").customerWidget("clear");
            $("#storage-goods-saleRejectEnterPage").goodsWidget("clear");
            $("#sales-queryForm-rejectBillListPage").form("reset");
            var queryJSON = $("#sales-queryForm-rejectBillListPage").serializeJSON();
            $("#sales-datagrid-rejectBillListPage").datagrid('load', queryJSON);
        });

        //按钮
        $("#sales-buttons-rejectBillListPage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/sales/auditRejectBillCar.do">
                {
                    type: 'button-audit',
                    handler: function () {
                        var rows = $("#sales-datagrid-rejectBillListPage").datagrid('getChecked');
                        if (rows == null || rows.length == 0) {
                            $.messager.alert("提醒", "请选择一条记录");
                            return false;
                        }
                        var ids = "";
                        for (var i = 0; i < rows.length; i++) {
                            if (ids == "") {
                                ids = rows[i].id
                            } else {
                                ids += "," + rows[i].id
                            }
                        }
                        $.messager.confirm("提醒", "确定审核该退货通知单信息？", function (r) {
                            if (r) {
                                loading("审核中..");
                                $.ajax({
                                    url: 'sales/auditMutRejectBillCar.do',
                                    dataType: 'json',
                                    type: 'post',
                                    data: 'ids=' + ids,
                                    success: function (json) {
                                        loaded();
                                        if (json.flag) {
                                            $.messager.alert("提醒", "审核成功。" + json.msg);
                                            $("#sales-datagrid-rejectBillListPage").datagrid('reload');
                                        }
                                        else {
                                            var msg = "";
                                            if (json.msg != null) {
                                                msg = "<br/>" + json.msg;
                                            }
                                            $.messager.alert("提醒", "审核失败。" + msg);
                                        }
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
                <security:authorize url="/sales/rejectBillViewPage.do">
                {
                    type: 'button-view',
                    handler: function () {
                        var con = $("#sales-datagrid-rejectBillListPage").datagrid('getSelected');
                        if (con == null) {
                            $.messager.alert("提醒", "请选择一条记录");
                            return false;
                        }
                        top.addOrUpdateTab('sales/rejectBillCar.do?type=edit&id=' + con.id, '车销退货通知单查看');
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/rejectBillCarDelete.do">
                {
                    type: 'button-delete',
                    handler: function () {
                        var rows = $("#sales-datagrid-rejectBillListPage").datagrid('getChecked');
                        if (rows == null || rows.length == 0) {
                            $.messager.alert("提醒", "请选择一条记录");
                            return false;
                        }
                        var ids = "";
                        for (var i = 0; i < rows.length; i++) {
                            if (ids == "") {
                                ids = rows[i].id
                            } else {
                                ids += "," + rows[i].id
                            }
                        }
                        $.messager.confirm("提醒", "确定删除该车销退货通知单？", function (r) {
                            if (r) {
                                loading("删除中..");
                                $.ajax({
                                    url: 'sales/deleteRejectBillCar.do',
                                    dataType: 'json',
                                    type: 'post',
                                    data: 'ids=' + ids,
                                    success: function (json) {
                                        loaded();
                                        if (json.flag) {
                                            $.messager.alert("提醒", "删除成功。" + json.msg);
                                            $("#sales-datagrid-rejectBillListPage").datagrid('reload');
                                        }
                                        else {
                                            var msg = "";
                                            if (json.msg != null) {
                                                msg = "<br/>" + json.msg;
                                            }
                                            $.messager.alert("提醒", "删除失败。" + msg);
                                        }
                                    },
                                    error: function () {
                                        loaded();
                                        $.messager.alert("错误", "删除出错");
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/rejectBillCarPrintView.do">
                {
                    type: 'button-preview',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/rejectBillCarPrint.do">
                {
                    type: 'button-print',
                    handler: function () {
                    }
                },
                </security:authorize>
                {
                    type: 'button-commonquery',
                    attr: {
                        //查询针对的表
                        name: 't_sales_rejectbill',
                        plain: true,
                        //查询针对的表格id
                        datagrid: 'sales-datagrid-rejectBillListPage'
                    }
                },
                {}
            ],
            model: 'bill',
            type: 'list',
            tname: 't_sales_rejectbill'
        });
        var dispatchBillListJson = $("#sales-datagrid-rejectBillListPage").createGridColumnLoad({
            name: 't_sales_rejectbill',
            frozenCol: [[
                {field: 'idok', checkbox: true, isShow: true}
            ]],
            commonCol: [[{field: 'id', title: '编号', width: 120, align: 'left', sortable: true},
                {field: 'businessdate', title: '业务日期', width: 80, align: 'left', sortable: true},
                {field: 'customerid', title: '客户编码', width: 60, align: 'left', sortable: true},
                {field: 'customername', title: '客户名称', width: 100, align: 'left', isShow: true},
                {field: 'handlerid', title: '对方经手人', width: 80, align: 'left', sortable: true},
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
                    field: 'isinvoice', title: '发票状态', width: 80, align: 'left', sortable: true,
                    formatter: function (value, row, index) {
                        if (value == "0") {
                            return "未开票";
                        }
                        else if (value == "1") {
                            return "已开票";
                        }
                        else if (value == "2") {
                            return "已核销";
                        }
                        else if (value == "3") {
                            return "未开票";
                        } else if (value == "4") {
                            return "开票中";
                        } else if (value == "5") {
                            return "部分核销";
                        }
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
                        if (rowData.printtimes > 0) {
                            return "已打印";
                        } else if (rowData.printtimes == -99) {
                            return "";
                        } else {
                            return "未打印";
                        }
                    }
                },
                {
                    field: 'printtimes', title: '打印次数', align: 'center', width: 60,
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
        $("#sales-datagrid-rejectBillListPage").datagrid({
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
            toolbar: '#sales-queryDiv-rejectBillListPage',
            onDblClickRow: function (index, data) {
                top.addOrUpdateTab('sales/rejectBillCar.do?type=edit&id=' + data.id, '车销退货通知单查看');
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
    });
    function countTotalAmount() {
        var rows = $("#sales-datagrid-rejectBillListPage").datagrid('getChecked');
        var totaltaxamount = 0;
        for (var i = 0; i < rows.length; i++) {
            totaltaxamount = Number(totaltaxamount) + Number(rows[i].totaltaxamount == undefined ? 0 : rows[i].totaltaxamount);
        }
        var footerrows = [{id: '选中金额', totaltaxamount: totaltaxamount, printtimes: -99}];
        $("#sales-datagrid-rejectBillListPage").datagrid("reloadFooter", footerrows);
    }
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "rejectBillListPage-dialog-print",
            code: "sales_rejectbill",
            tableId: "sales-datagrid-rejectBillListPage",
            url_preview: "print/sales/rejectBillPrintView.do",
            url_print: "print/sales/rejectBillPrint.do",
            btnPreview: "button-preview",
            btnPrint: "button-print",
            printlimit: "${printlimit}"
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
