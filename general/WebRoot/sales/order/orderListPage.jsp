<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>销售订单列表页面</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>

    <%
        boolean showPrintOptions = false;
        boolean printOptionsOrderSeq = false;
    %>
    <security:authorize url="/sales/salesDeliveryOrderPrintOptions.do">
        <%
            showPrintOptions = true;
        %>
    </security:authorize>
    <security:authorize url="/sales/salesOrderPrintOptionsOrderSeq.do">
        <%
            printOptionsOrderSeq = true;
        %>
    </security:authorize>
</head>
<body>
<div id="sales-queryDiv-orderListPage" style="height:auto;padding:0px">
    <div class="buttonBG" id="sales-buttons-orderListPage"></div>
    <form id="sales-queryForm-orderListPage" method="post">
        <table class="querytable">
            <tr>
                <td>业务日期：</td>
                <td class="tdinput"><input type="text" name="businessdate" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value=""/>
                    到<input type="text" name="businessdate1" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/></td>
                <td>编号：</td>
                <td class="tdinput"><input type="text" name="id" class="len180"/></td>
                <td>销售部门：</td>
                <td class="tdinput">
                    <input type="text" id="sales-salesDept-orderListPage" name="salesdept" style="width: 130px;"/>
                </td>
            </tr>
            <tr>
                <td>客户：</td>
                <td class="tdinput"><input type="text" id="sales-customer-orderListPage" style="width: 213px" name="customerid"/></td>
                <td>状态：</td>
                <td class="tdinput">
                    <select name="status" style="width:180px;">
                        <option></option>
                        <option value="2" selected="selected">保存</option>
                        <option value="3">审核通过</option>
                        <option value="4">已关闭</option>
                        <option value="5">作废</option>
                    </select>
                </td>
                <td>批次号：</td>
                <td class="tdinput"><input type="text" name="batchno" class="len130"/></td>
            </tr>
            <tr>
                <td>商品名称：</td>
                <td class="tdinput"><input type="text" id="sales-goodsid-orderListPage" style="width: 213px" name="goodsid"/></td>
                <td>打印状态:</td>
                <td>
                    <select id="sales-printsign-orderListPage" name="printsign" style="width:180px;">
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
                    <input type="hidden" id="sales-printtimes-orderListPage" name="queryprinttimes" value="0"/>
                </td>
                <td colspan="2" class="tdbutton">
                    <a href="javascript:void(0);" id="sales-queay-orderListPage" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="sales-resetQueay-orderListPage" class="button-qr">重置</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<table id="sales-datagrid-orderListPage" data-options="border:false"></table>
<input id="createGridColumnLoad-commonCol" hidden="true"/>

<div id="salesorder-importModel-dialog"></div>
<div style="display:none">
    <div id="salesorder-import-dialog"></div>
</div>
<script type="text/javascript">
    function isLockData(id, tname) {
        var flag = false;
        $.ajax({
            url: 'system/lock/unLockData.do',
            type: 'post',
            data: {id: id, tname: tname},
            dataType: 'json',
            async: false,
            success: function (json) {
                flag = json.flag
            }
        });
        return flag;
    }
    var initQueryJSON = $("#sales-queryForm-orderListPage").serializeJSON();
    $(function () {
        $("#sales-customer-orderListPage").customerWidget({ //客户参照窗口
            name: 't_sales_order',
            col: 'customerid',
            singleSelect: true,
            isdatasql: false,
            onlyLeafCheck: false
        });
        $("#sales-goodsid-orderListPage").goodsWidget({ //客户参照窗口
            singleSelect: true,
            isdatasql: false,
            onlyLeafCheck: false,
            isHiddenUsenum:true
        });

        //销售部门控件
        $("#sales-salesDept-orderListPage").widget({
            name: 't_sales_order',
            col: 'salesdept',
            singleSelect: true,
            width: 130,
            onlyLeafCheck: false
        });
        $("#sales-queay-orderListPage").click(function () {
            var queryJSON = $("#sales-queryForm-orderListPage").serializeJSON();
            $("#sales-datagrid-orderListPage").datagrid('load', queryJSON);
        });
        $("#sales-resetQueay-orderListPage").click(function () {
            $("#sales-customer-orderListPage").customerWidget("clear");
            $("#sales-goodsid-orderListPage").goodsWidget("clear");
            $("#sales-queryForm-orderListPage").form("reset");
            var queryJSON = $("#sales-queryForm-orderListPage").serializeJSON();
            $("#sales-datagrid-orderListPage").datagrid('load', queryJSON);
        });
        //按钮
        $("#sales-buttons-orderListPage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/sales/orderAddPage.do">
                {
                    type: 'button-add',
                    handler: function () {
                        top.addTab('sales/orderPage.do', "销售订单新增");
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/orderCopy.do">
                {
                    type: 'button-copy',
                    handler: function () {
                        var con = $("#sales-datagrid-orderListPage").datagrid('getSelected');
                        if (con == null) {
                            $.messager.alert("提醒", "请选择一条记录");
                            return false;
                        }
                        top.addOrUpdateTab('sales/orderPage.do?type=copy&id=' + con.id, "销售订单新增");
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/orderImport.do">
                {
                    type: 'button-import',
                    attr: {
                        type: 'importbill',
                        module: 'sales',
                        majorkey: 'id',
                        pojo: "ImportSalesOrder",
                        clazz: "salesOrderService", //spring中注入的类名
                        method: "addDRSalesOrder", //插入数据库的方法
                        importparam: '必填项：客户编码，商品编码，数量。<br/>选填项：单价，金额',
                        onClose: function () { //导入成功后窗口关闭时操作，
                            $("#sales-datagrid-orderListPage").datagrid('reload');	//更新列表
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/orderExport.do">
                {
                    type: 'button-export',
                    attr: {
                        datagrid: "#sales-datagrid-orderListPage",
                        queryForm: "#sales-queryForm-orderListPage", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
                        type: 'exportbill',
                        name: '销售订单列表'
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/orderMultiAudit.do">
                {
                    type: 'button-audit',
                    handler: function () {
                        var rows = $("#sales-datagrid-orderListPage").datagrid('getChecked');
                        if (rows.length == 0) {
                            $.messager.alert("提醒", "请选中需要审核的记录。");
                            return false;
                        }
                        var ids = "";
                        for (var i = 0; i < rows.length; i++) {
                                ids += rows[i].id + ',';
                        }
                        if (ids != "") {
                            loading("核对验证中..");
                            $.getJSON("sales/canAuditOrder.do", {ids: ids}, function (json) {
                                loaded();
                                if (json.flag == true) {
                                    $.messager.confirm("提醒", "确定审核这些订单？", function (r) {
                                        if (r) {
                                            loading("审核中...");
                                            $.ajax({
                                                url: 'sales/auditMultiOrder.do',
                                                dataType: 'json',
                                                type: 'post',
                                                data: {ids: ids},
                                                success: function (json) {
                                                    loaded();
                                                    if (json.flag == true) {
                                                        $.messager.alert("提醒", "审核成功：" + json.success + "&nbsp;审核失败：" + json.failure + "&nbsp;无需审核：" + json.noaudit + "<br/>" + json.msg);
                                                        $("#sales-datagrid-orderListPage").datagrid('reload');
                                                    }
                                                    else {
                                                        $.messager.alert("提醒", "审核出错");
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
                                else {
                                    $.messager.confirm("提醒", json.msg + "是否继续审核？", function (r) {
                                        if (r) {
                                            loading("审核中...");
                                            $.ajax({
                                                url: 'sales/auditMultiOrder.do',
                                                dataType: 'json',
                                                type: 'post',
                                                data: {ids: ids},
                                                success: function (json) {
                                                    loaded();
                                                    if (json.flag == true) {
                                                        $.messager.alert("提醒", "审核成功：" + json.success + "&nbsp;审核失败：" + json.failure + "&nbsp;无需审核：" + json.noaudit + "<br/>" + json.msg);
                                                        $("#sales-datagrid-orderListPage").datagrid('reload');
                                                    }
                                                    else {
                                                        $.messager.alert("提醒", "审核出错");
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
                            });
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/orderSupperAudit.do">
                {
                    type: 'button-supperaudit',
                    handler: function () {
                        var rows = $("#sales-datagrid-orderListPage").datagrid('getChecked');
                        if (rows.length == 0) {
                            $.messager.alert("提醒", "请选中需要超级审核的记录。");
                            return false;
                        }
                        var ids = "";
                        for (var i = 0; i < rows.length; i++) {
                                ids += rows[i].id + ',';
                        }
                        if (ids != "") {
                            loading("核对验证中..");
                            $.ajax({
                                url: 'sales/canAuditOrder.do',
                                dataType: 'json',
                                type: 'post',
                                data: 'ids=' + ids,
                                success: function (json) {
                                    loaded();
                                    if (json.flag == true) {
                                        $.messager.confirm("提醒", "确定超级审核该订单信息？", function (r) {
                                            if (r) {
                                                loading("超级审核中...");
                                                $.ajax({
                                                    url: 'sales/supplierAuditOrderMuti.do',
                                                    dataType: 'json',
                                                    type: 'post',
                                                    data: 'ids=' + ids + '&type=1&model=supper',
                                                    success: function (json) {
                                                        loaded();
                                                        if (json.sucids != "") {
                                                            var msg = "订单:" + json.sucids + "审核成功;" + json.msg;
                                                            $.messager.alert("提醒", msg);
                                                        } else if (json.unsucids != "") {
                                                            $.messager.alert("提醒", "订单:" + json.unsucids + "审核失败<br/>" + json.msg);
                                                        }
                                                        $("#sales-datagrid-orderListPage").datagrid('reload');
                                                    },
                                                    error: function () {
                                                        loaded();
                                                        $.messager.alert("错误", "审核出错");
                                                    }
                                                });
                                            }
                                        });
                                    }
                                    else {
                                        loaded();
                                        $.messager.confirm("提醒", json.msg + "是否继续审核？", function (r) {
                                            if (r) {
                                                loading("超级审核中...");
                                                $.ajax({
                                                    url: 'sales/supplierAuditOrderMuti.do',
                                                    dataType: 'json',
                                                    type: 'post',
                                                    data: 'ids=' + ids + '&type=1&model=supper',
                                                    success: function (json) {
                                                        loaded();
                                                        if (json.sucids != "") {
                                                            var msg = "订单:" + json.sucids + "审核成功;" + json.msg;
                                                            $.messager.alert("提醒", msg);
                                                        } else if (json.unsucids != "") {
                                                            $.messager.alert("提醒", "订单:" + json.unsucids + "审核失败<br/>" + json.msg);
                                                        }
                                                        $("#sales-datagrid-orderListPage").datagrid('reload');
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
                                error: function () {
                                    loaded();
                                    $.messager.alert("错误", "核对验证出错");
                                }
                            });
                        }
                    }
                },
                </security:authorize>
                {
                    type: 'button-commonquery',
                    attr: {
                        //查询针对的表
                        name: 't_sales_order',
                        plain: true,
                        //查询针对的表格id
                        datagrid: 'sales-datagrid-orderListPage'
                    }
                },
                {}
            ],
            buttons: [
                {},
                <security:authorize url="/sales/invalidOrderClose.do">
                {
                    id: 'button-batch-invalid',
                    name: '批量作废',
                    iconCls: 'button-delete',
                    handler: function () {
                        var rows = $("#sales-datagrid-orderListPage").datagrid('getChecked');
                        if (rows.length == 0) {
                            $.messager.alert("提醒", "请选中需要作废的记录。");
                            return false;
                        }
                        var ids = "";
                        for (var i = 0; i < rows.length; i++) {
                            ids += rows[i].id + ',';
                        }
                        $.messager.confirm("提醒", "确定作废该订单信息？", function (r) {
                            if (r) {
                                loading("作废中..");
                                $.ajax({
                                    url: 'sales/doBatchInvalidOrder.do',
                                    dataType: 'json',
                                    type: 'post',
                                    data: 'ids=' + ids,
                                    success: function (json) {
                                        loaded();
                                        var msg = "";
                                        if (json.undomsg != "") {
                                            msg = json.undomsg + "<br>";
                                        }
                                        $.messager.alert("提醒", msg + "作废成功" + json.sucnum + "条订单；<br>作废失败" + json.unsucnum + "条订单；");
                                        $("#sales-datagrid-orderListPage").datagrid('reload');
                                    },
                                    error: function () {
                                        loaded();
                                        $.messager.alert("错误", "批量作废出错");
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/showOrderAddByOrderGoodsPage.do">
                {
                    id: 'button-addorder',
                    name: '从订货单生成',
                    iconCls: 'button-add',
                    handler: function () {
                        top.addOrUpdateTab('sales/showOrderAddByOrderGoodsPage.do', "从订货单生成");
                    }
                },
                </security:authorize>

                <security:authorize url="/sales/orderModelImport.do">
                {
                    id: 'button-import-html',
                    name: '模板导入',
                    iconCls: 'button-import',
                    handler: function () {
                        uploadHtml();
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/importOrderListData.do">
                {
                    id: 'button-importModel',
                    name: '自选导入',
                    iconCls: 'button-import',
                    handler: function () {
                        $("#salesorder-importModel-dialog").dialog({
                            href: 'common/showAutoImportPage.do',
                            width: 450,
                            height: 580,
                            title: '自选导入',
                            close: false,
                            cache: false,
                            modal: true
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/exportOrderListData.do">
                {
                    id: 'button-order-export',
                    name: '订单导出',
                    iconCls: 'button-export',
                    handler: function () {
                        var queryJSON = $("#sales-queryForm-orderListPage").serializeJSON();
                        //获取排序规则
                        var objecr = $("#sales-datagrid-orderListPage").datagrid("options");
                        if (null != objecr.sortName && null != objecr.sortOrder) {
                            queryJSON["sort"] = objecr.sortName;
                            queryJSON["order"] = objecr.sortOrder;
                        }
                        var queryParam = JSON.stringify(queryJSON);
                        var url = "sales/exportOrderListData.do";
                        exportByAnalyse(queryParam, "销售订单列表", "sales-datagrid-orderListPage", url);
                    }
                },
                </security:authorize>
                {}
            ],
            model: 'bill',
            type: 'list',
            tname: 't_sales_order'
        });
        var orderListJson = $("#sales-datagrid-orderListPage").createGridColumnLoad({
            name: 't_sales_order',
            commonCol: [[
                {field: 'ck', checkbox: true},
                {field: 'id', title: '编号', width: 120, align: 'left', sortable: true},
                {field: 'businessdate', title: '业务日期', width: 80, align: 'left', sortable: true},
                {field: 'customerid', title: '客户编码', width: 60, align: 'left', sortable: true},
                {field: 'customername', title: '客户名称', width: 150, align: 'left', isShow: true},
                {field: 'handlerid', title: '对方经手人', width: 80, align: 'left'},
                {field: 'salesdept', title: '销售部门', width: 100, align: 'left', sortable: true},
                {field: 'salesuser', title: '客户业务员', width: 80, align: 'left', sortable: true},
                {
                    field: 'field01', title: '金额', width: 80, align: 'right',
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'field02', title: '未税金额', width: 80, align: 'right', hidden: true,
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'field03', title: '税额', width: 80, align: 'right', hidden: true,
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'status', title: '状态', width: 60, align: 'left', sortable: true,
                    formatter: function (value, row, index) {
                        return row.statusname;
                    }
                },
                {
                    field: 'indooruserid', title: '销售内勤', width: 80, sortable: true,
                    formatter: function (value, rowData, index) {
                        return rowData.indoorusername;
                    }
                },
                {
                    field: 'sourcetype', title: '来源类型', width: 80, sortable: true,
                    formatter: function (value, rowData, index) {
                        if (value == "0") {
                            return "普通订单";
                        } else if (value == "1") {
                            return "手机订单";
                        } else if (value == "2") {
                            return "零售车销";
                        } else if (value == "4") {
                            return "商城订单";
                        } else if (value == "3") {
                            return "销售订货单";
                        }
                    }
                },
                {field: 'sourceid', title: '来源单号/客户单号', width: 120, sortable: true},
                {
                    field: 'totalboxweight', title: '总重量(千克)', width: 80, hidden: true, align: 'right',
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'totalboxvolume', title: '总体积(立方米)', width: 100, hidden: true, align: 'right',
                    formatter: function (value, row, index) {
                        return Number(value).toFixed(3);
                    }
                },
                {field: 'addusername', title: '制单人', width: 60, sortable: true},
                {field: 'addtime', title: '制单时间', width: 130, sortable: true},
                {field: 'auditusername', title: '审核人', width: 80, sortable: true, hidden: true},
                {field: 'audittime', title: '审核时间', width: 80, sortable: true, hidden: true},
                {field: 'modifyusername', title: '修改人', width: 60, sortable: true, hidden: true},
                {field: 'modifytime', title: '修改时间', width: 130, sortable: true, hidden: true},
                {field: 'remark', title: '备注', width: 100},
                {field: 'printtimes', title: '打印次数', width: 60, hidden: true},
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
                {field: 'printdatetime', title: '打印时间', width: 130},
                {field: 'phprinttimes', title: '配货打印次数', width: 60, hidden: true},
                {
                    field: 'phprintstate', title: '配货打印状态', width: 80, isShow: true, hidden: true,
                    formatter: function (value, rowData, index) {
                        if (rowData.phprinttimes > 0) {
                            return "已打印";
                        } else if (rowData.phprinttimes == -99) {
                            return "";
                        } else {
                            return "未打印";
                        }
                    }
                    <security:authorize url="/storage/salesOrderblankForXSDPrintBtn.do">
                    , hidden: false
                    </security:authorize>
                },
                {field: 'phprintdatetime', title: '配货打印时间', width: 130,hidden:true

                    <security:authorize url="/storage/salesOrderblankForXSDPrintBtn.do">
                    ,hidden:false
                    </security:authorize>
                }
            ]]
        });

        var ListJson = JSON.stringify(orderListJson);
        $("#createGridColumnLoad-commonCol").val(ListJson);

        $("#sales-datagrid-orderListPage").datagrid({
            authority: orderListJson,
            frozenColumns: orderListJson.frozen,
            columns: orderListJson.common,
            fit: true,
            method: 'post',
            rownumbers: true,
            pagination: true,
            idField: 'id',
            singleSelect: false,
            checkOnSelect: true,
            selectOnCheck: true,
            sortName: 'addtime',
            sortOrder: 'desc',
            url: 'sales/getOrderList.do',
            queryParams: initQueryJSON,
            showFooter: true,
            toolbar: '#sales-queryDiv-orderListPage',
            onDblClickRow: function (index, data) {
                var flag = isLockData(data.id, "t_sales_order");
                if (!flag) {
                    $.messager.alert("警告", "该数据正在被其他人操作，暂不能修改！");
                    return false;
                }
                top.addOrUpdateTab('sales/orderPage.do?type=edit&id=' + data.id, '销售订单查看');
            },
            onCheck: sumOrder,
            onUncheck: sumOrder,
            onCheckAll: sumOrder,
            onUncheckAll: sumOrder
        }).datagrid("columnMoving");
    });

    $("#sales-phprintsign-orderListPage").change(function () {
        if ($(this).val() == "") {
            $("#sales-phprinttimes-orderListPage").val("");
        }
    });
    $("#sales-printsign-orderListPage").change(function () {
        if ($(this).val() == "") {
            $("#sales-printtimes-orderListPage").val("");
        }
    });

    function uploadHtml() {
        $("#salesorder-import-dialog").dialog({
            href: 'sales/salesModelPage.do',
            width: 400,
            height: 300,
            title: '模板文件上传',
            colsed: false,
            cache: false,
            modal: true
        });
    }
    
    function sumOrder() {
        var rows = $("#sales-datagrid-orderListPage").datagrid('getChecked');
        var field01 = 0;    // 金额
        var field02 = 0;    // 未税金额
        var field03 = 0;    // 税额
        var totalboxweight = 0;    // 总重量(千克)
        var totalboxvolume = 0;    // 总体积(立方米)
        for(var i in rows) {
            var row = rows[i];
            field01 = field01 + parseFloat(row.field01 || 0);
            field02 = field02 + parseFloat(row.field02 || 0);
            field03 = field03 + parseFloat(row.field03 || 0);
            totalboxweight = totalboxweight + parseFloat(row.totalboxweight || 0);
            totalboxvolume = totalboxvolume + parseFloat(row.totalboxvolume || 0);
        }

        var sumInfo = [{
            id: '选中合计',
            field01: field01,
            field02: field02,
            field03: field03,
            totalboxweight: totalboxweight,
            totalboxvolume: totalboxvolume,
            printtimes: -99,
            phprinttimes: -99
        }];

        $("#sales-datagrid-orderListPage").datagrid('reloadFooter', sumInfo);
    }
    
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        var $grid = $("#sales-datagrid-orderListPage");
        function onPrintSuccess(option) {
            var dataList = $grid.datagrid("getChecked");
            var isphprinttimes = false;
            $.each(option.code,function(i,item){
                if(item.codename == "storage_orderblank")
                    isphprinttimes = true;
            });
            for (var i = 0; i < dataList.length; i++) {
                if(isphprinttimes){
                    if (dataList[i].phprinttimes && !isNaN(dataList[i].phprinttimes)) {
                        dataList[i].phprinttimes = dataList[i].phprinttimes + 1;
                    } else {
                        dataList[i].phprinttimes = 1;
                    }
                }else {
                    if (dataList[i].printtimes && !isNaN(dataList[i].printtimes)) {
                        dataList[i].printtimes = dataList[i].printtimes + 1;
                    } else {
                        dataList[i].printtimes = 1;
                    }
                }
                var rowIndex = $grid.datagrid("getRowIndex", dataList[i].id);
                $grid.datagrid('updateRow', {index: rowIndex, row: dataList[i]});
            }
        }
        //配货单打印
        AgReportPrint.init({
            id: "orderblank-dialog-print",
            code: "storage_orderblank",
            //tableId: "sales-datagrid-orderListPage",
            url_preview: "print/sales/salesOrderblankForXSDPrintView.do",
            url_print: "print/sales/salesOrderblankForXSDPrint.do",
            btnPreview: "button-printview-orderblank",
            btnPrint: "button-print-orderblank",
            printlimit: "${printlimit}",
            getData: function (tableId, printParam) {
                var data = $grid.datagrid('getChecked');
                if (data == null || data.length == 0) {
                    $.messager.alert("提醒", "请选择至少一条记录");
                    return false;
                }
                var idarray = [];
                var printIds = [];
                for (var i = 0; i < data.length; i++) {
                    if (!( data[i].status == '3' || data[i].status == '4')) {
                        $.messager.alert("提醒", data[i].id + "此销售单不可进行配货打印");
                        return false;
                    }
                    idarray.push(data[i].id);
                    if (data[i].phprinttimes > 0) {
                        printIds.push(data[i].id);
                    }
                }
                printParam.saleidarrs = idarray.join(",");
                printParam.printIds = printIds;
                return true;
            },
            onPrintSuccess: onPrintSuccess
        });
        //销售单-按订单套打
        AgReportPrint.init({
            id: "dispatchbill-dialog-print",
            code: "storage_dispatchbill",
            //tableId: "sales-datagrid-orderListPage",
            url_preview: "print/sales/salesDispatchBillForXSDPrintView.do",
            url_print: "print/sales/salesDispatchBillForXSDPrint.do",
            btnPreview: "button-printview-DispatchBill",
            btnPrint: "button-print-DispatchBill",
            printlimit: "${printlimit}",
            getData: function (tableId, printParam) {
                var data = $grid.datagrid('getChecked');
                if (data == null || data.length == 0) {
                    $.messager.alert("提醒", "请选择至少一条记录");
                    return false;
                }
                var idarray = [];
                var printIds = [];
                var printlimit = "${printlimit}";
                var fHPrintAfterSaleOutAudit = "${fHPrintAfterSaleOutAudit}";
                for (var i = 0; i < data.length; i++) {
                    if (fHPrintAfterSaleOutAudit == '1') {
                        if (data[i].status == '4') {
                            idarray.push(data[i].id);
                        } else {
                            $.messager.alert("提醒", "抱歉，关闭状态下才能打印。<br/>" + data[i].id + "此销售订单不能进行按订单套打");
                            return false;
                        }
                    } else {
                        if (data[i].status == '3' || data[i].status == '4') {
                            idarray.push(data[i].id);
                        } else {
                            $.messager.alert("提醒", data[i].id + "此销售订单不能进行按订单套打");
                            return false;
                        }
                    }
                    if (data[i].printtimes > 0) {
                        printIds.push(data[i].id);
                    }
                }
                printParam.saleidarrs = idarray.join(",");
                printParam.printIds = printIds;
                return true;
            },
            onPrintSuccess: onPrintSuccess
        });
        //销售订单打印
        AgReportPrint.init({
            id: "order-dialog-print",
            code: "sales_order",
            //tableId: "sales-datagrid-orderListPage",
            url_preview: "print/sales/salesOrderPrintView.do",
            url_print: "print/sales/salesOrderPrint.do",
            btnPreview: "button-printview-saleorder",
            btnPrint: "button-print-saleorder",
            printlimit: "${printlimit}",
            getData: function (tableId, printParam) {
                var data = $grid.datagrid('getChecked');
                if (data == null || data.length == 0) {
                    $.messager.alert("提醒", "请选择至少一条记录");
                    return false;
                }
                var idarray = [];
                var printIds = [];
                for (var i = 0; i < data.length; i++) {
                    idarray.push(data[i].id);
                    if (data[i].printtimes > 0) {
                        printIds.push(data[i].id);
                    }
                }
                printParam.saleidarrs = idarray.join(",");
                printParam.printIds = printIds;
                return true;
            },
            onPrintSuccess: onPrintSuccess
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
