<%@ page language="java" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>回单交接单页面</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>

<body>
<div id="account-layout-receipthand" class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="account-buttons-receipthand" style="height: 26px;"></div>
    </div>
    <div data-options="region:'center',border:false">
        <div class="easyui-panel" data-options="fit:true" id="account-panel-receipthand"></div>
    </div>
</div>
<div id="account-dialog-receiptHandAddReceipt"></div>
<div id="account-dialog-receiptHandBillDetailExport"></div>
<script type="text/javascript">
    var customerTableColJson = $("#receipthand-table-customer").createGridColumnLoad({
        commonCol: [[
            {field: 'id', title: '编号', width: 130, hidden: true},
            {field: 'billid', title: '交接单编号', width: 130, hidden: true},
            {field: 'customerid', title: '客户编号', width: 80, sortable: true},
            {field: 'customername', title: '客户名称', width: 160, sortable: false},
            {
                field: 'billnums', title: '单据数', width: 80, sortable: true,
                editor: {
                    type: 'numberbox',
                    options: {
                        precision: 0,
                        max: 999999999999,
                        min: 0,
                        onChange: function (oldval, newval) {
                            if (newval != "" && newval != oldval) {
                                var select = $dgCustomerList.datagrid("getSelected");
                                if (select.isedit != "2") {
                                    select.isedit = "1";
                                }
                            }
                        }
                    }
                },
                styler: function (value, row, index) {
                    if (row.isedit == "2") {
                        return 'color:red;';
                    }
                }
            },
            {
                field: 'amount', title: '发货金额', resizable: true, align: 'right',
                formatter: function (value, rowData, rowIndex) {
                    return formatterMoney(value);
                },
                editor: {
                    type: 'numberbox',
                    options: {
                        precision: 2,
                        max: 999999999999,
                        onChange: function (oldval, newval) {
                            if (newval != "" && newval != oldval) {
                                var select = $dgCustomerList.datagrid("getSelected");
                                if (select.isedit != "2") {
                                    select.isedit = "1";
                                }
                            }
                        }
                    }
                },
                styler: function (value, row, index) {
                    if (row.isedit == "2") {
                        return 'color:red;';
                    }
                }
            },
            {
                field: 'collectionamount', title: '应收金额', resizable: true, align: 'right',
                formatter: function (value, rowData, rowIndex) {
                    return formatterMoney(value);
                },
                editor: {
                    type: 'numberbox',
                    options: {
                        precision: 2,
                        max: 999999999999,
                        onChange: function (oldval, newval) {
                            if (newval != "" && newval != oldval) {
                                var select = $dgCustomerList.datagrid("getSelected");
                                if (select.isedit != "2") {
                                    select.isedit = "1";
                                }
                            }
                        }
                    }
                },
                styler: function (value, row, index) {
                    if (row.isedit == "2") {
                        return 'color:red;';
                    }
                }
            },
            {field: 'remark', title: '备注', width: 120, editor: 'text'},
            {
                field: 'isreceipt', title: '是否关联回单', width: 80, hidden: true,
                formatter: function (value, rowData, rowIndex) {
                    if (value == '0')return "否";
                    if (value == '1')return "是";
                }
            }
        ]]
    });

    var billTableColJson = $("#receipthand-table-bill").createGridColumnLoad({
        frozenCol: [[{field: 'ck', checkbox: true}]],
        commonCol: [[
            {field: 'billid', title: '交接单编号', width: 130, hidden: true},
            {
                field: 'relatebillid', title: '单据编号', width: 130,
                formatter: function (value, rowData, rowIndex) {
                    if ("合计" != value && null != value) {
                        return "<a href=\"javascript:void(0);\" onclick=\"javascript:showReceiptPageFromRH(\'" + value + "\',\'" + rowData.billtype + "\')\">" + value + "</a>";
                    } else {
                        return value;
                    }
                }
            },
            {field: 'saleorderid', title: '订单编号', width: 130},
            {
                field: 'billtype', title: '单据类型', width: 90,
                formatter: function (value, rowData, rowIndex) {
                    return rowData.billtypename;
                }
            },
            {field: 'sourceid', title: '客户单号', width: 130},
            {field: 'businessdate', title: '业务日期', width: 80},
            {field: 'customerid', title: '客户编号', width: 60},
            {field: 'customername', title: '客户名称', width: 160},
            {
                field: 'pcustomerid', title: '总店', width: 60,
                formatter: function (value, rowData, rowIndex) {
                    return rowData.pcustomername;
                }
            },
            {
                field: 'customersort', title: '客户分类', width: 80,
                formatter: function (value, rowData, rowIndex) {
                    return rowData.customersortname;
                }
            },
            {
                field: 'salesarea', title: '销售区域', width: 60,
                formatter: function (value, rowData, rowIndex) {
                    return rowData.salesareaname;
                }
            },
            {
                field: 'salesdept', title: '销售部门', width: 60, hidden: true,
                formatter: function (value, rowData, rowIndex) {
                    return rowData.salesdeptname;
                }
            },
            {
                field: 'salesuser', title: '客户业务员', width: 70,
                formatter: function (value, rowData, rowIndex) {
                    return rowData.salesusername;
                }
            },
            {
                field: 'amount', title: '应收金额', resizable: true, align: 'right',
                formatter: function (value, rowData, rowIndex) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'isrecycle', title: '是否回收', width: 80, hidden: true,
                formatter: function (value, rowData, rowIndex) {
                    return rowData.isrecyclename;
                }
            },
            {field: 'recycledate', title: '回收日期', width: 80, hidden: true},
            {field: 'remark', title: '备注', width: 120}
        ]]
    });

    var page_url = "";
    var page_type = '${type}';
    var page_title = "交接单【查看】";
    var title = tabsWindowTitle('/account/receipthand/showReceiptHandListPage.do');

    function refreshPanel(id, type) {
        page_type = type;
        if (page_type == "view") {
            page_url = "account/receipthand/showReceiptHandViewPage.do?id=" + id;
        } else if (page_type == "edit") {
            page_url = "account/receipthand/showReceiptHandEditPage.do?id=" + id;
        }
        $("#account-panel-receipthand").panel({
            href: page_url,
            cache: false,
            maximized: true,
            border: false
            // title:page_title
        });
    }

    function getType(status) {
        var type = 'view';
        if (status == '2')
            type = 'edit';
        return type;
    }

    function receiptHandFormValide() {
        return $("#receiptHand-form-head").form('validate');
    }

    $(function () {
        refreshPanel('${id}', '${type}');
        //按钮
        $("#account-buttons-receipthand").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/account/receipthand/receiptHandSaveBtn.do">
                {
                    type: 'button-save',
                    id: 'button-save',
                    handler: function () {
                        if (!receiptHandFormValide()) {
                            $.messager.alert("提醒", "请输入必填项!");
                            return false;
                        }
                        //结束行编辑
                        if (!receipthand_endEditCustomer()) {
                            $("#receipthand-table-customer").datagrid('clearSelections');
                            $("#receipthand-table-customer").datagrid('endEdit', receipthand_endEditIndexCustomer);

                            receipthand_endEditIndexCustomer = undefined;
                            setCustomerFooter();
                        }
                        var id = $("#receipthand-id-baseinfo").val();
                        $.messager.confirm("提醒", "是否保存交接单？", function (r) {
                            if (r) {
                                loading("保存中...");
                                $.ajax({
                                    url: 'account/receipthand/editReceiptHand.do',
                                    dataType: 'json',
                                    type: 'post',
                                    async: false,
                                    data: receipthand_JSONs(),
                                    success: function (json) {
                                        loaded();
                                        if (json.flag) {
                                            if (top.$('#tt').tabs('exists', title)) {
                                                tabsWindow(title).$("#account-datagrid-receipthandlist").datagrid('reload');
                                            }
                                            refreshPanel(id, "edit");
                                            $.messager.alert("提醒", "保存成功!");
                                        } else {
                                            $.messager.alert("提醒", "保存失败!");
                                        }
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receipthand/receiptHandSaveAuditBtn.do">
                {
                    type: 'button-saveaudit',
                    handler: function () {
                        if (!receiptHandFormValide()) {
                            $.messager.alert("提醒", "请输入必填项!");
                            return false;
                        }
                        //结束行编辑
                        if (!receipthand_endEditCustomer()) {
                            $("#receipthand-table-customer").datagrid('clearSelections');
                            $("#receipthand-table-customer").datagrid('endEdit', receipthand_endEditIndexCustomer);

                            receipthand_endEditIndexCustomer = undefined;
                            setCustomerFooter();
                        }
                        var id = $("#receipthand-id-baseinfo").val();
                        $.messager.confirm("提醒", "是否保存并审核交接单？", function (r) {
                            if (r) {
                                loading("保存并审核中...");
                                $.ajax({
                                    url: 'account/receipthand/saveAuditReceiptHand.do',
                                    dataType: 'json',
                                    type: 'post',
                                    async: false,
                                    data: receipthand_JSONs(),
                                    success: function (json) {
                                        loaded();
                                        if (json.flag) {
                                            if (top.$('#tt').tabs('exists', title)) {
                                                tabsWindow(title).$("#account-datagrid-receipthandlist").datagrid('reload');
                                            }
                                            refreshPanel(id, "view");
                                            $.messager.alert("提醒", "保存并审核成功!");
                                        } else {
                                            $.messager.alert("提醒", "保存并审核失败!");
                                        }
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receipthand/receiptHandDeleteBtn.do">
                {
                    type: 'button-delete',
                    handler: function () {
                        var id = $("#receipthand-id-baseinfo").val();
                        $.messager.confirm("提醒", "是否确定删除交接单?", function (r) {
                            if (r) {
                                loading("删除中..");
                                $.ajax({
                                    url: 'account/receipthand/deleteReceiptHands.do?ids=' + id,
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        $.messager.alert("提醒", "删除无效" + json.invalidNum + "条记录;<br/>删除成功" + json.sucnum + "条记录;<br/>" + json.msg);
                                        if (json.sucnum > 0) {
                                            if (top.$('#tt').tabs('exists', title)) {
                                                tabsWindow(title).$("#account-datagrid-receipthandlist").datagrid('reload');
                                            }
                                            var data = $("#account-buttons-receipthand").buttonWidget("removeData", id);
                                            if (null != data) {
                                                refreshPanel(data.id, getType(data.status));
                                            } else {
                                                top.closeNowTab();
                                            }
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
                <security:authorize url="/account/receipthand/receiptHandAuditBtn.do">
                {
                    type: 'button-audit',
                    handler: function () {
                        if (!receiptHandFormValide()) {
                            $.messager.alert("提醒", "请输入必填项!");
                            return false;
                        }
                        var id = $("#receipthand-id-baseinfo").val();
                        $.messager.confirm("提醒", "是否审核交接单？", function (r) {
                            if (r) {
                                loading("审核中..");
                                $.ajax({
                                    url: 'account/receipthand/auditReceiptHands.do?ids=' + id,
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag) {
                                            $.messager.alert("提醒", "审核成功!");
                                            if (top.$('#tt').tabs('exists', title)) {
                                                tabsWindow(title).$("#account-datagrid-receipthandlist").datagrid('reload');
                                            }
                                            refreshPanel(id, 'view');
                                        } else {
                                            var msg = "审核失败!";
                                            if (json.invalidNum > 0) {
                                                msg += "<br>状态不允许审核，审核无效;";
                                            }
                                            if (json.nohandusernum > 0) {
                                                msg += "<br>未保存领单人，不予审核;";
                                            }
                                            $.messager.alert("提醒", msg);
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
                <security:authorize url="/account/receipthand/receiptHandOppauditBtn.do">
                {
                    type: 'button-oppaudit',
                    handler: function () {
                        var businessdate = $("#receipthand-businessdate-baseinfo").val();
                        var flag = isDoneOppauditBillCaseAccounting(businessdate);
                        if (!flag) {
                            $.messager.alert("提醒", "业务日期不在会计区间内或未设置会计区间,不允许反审!");
                            return false;
                        }
                        var id = $("#receipthand-id-baseinfo").val();
                        $.messager.confirm("提醒", "是否反审交接单？", function (r) {
                            if (r) {
                                loading("反审中..");
                                $.ajax({
                                    url: 'account/receipthand/oppauditReceiptHands.do?ids=' + id,
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag) {
                                            $.messager.alert("提醒", "反审无效" + json.invalidNum + "条记录;<br/>反审成功" + json.num + "条记录;");
                                            if (top.$('#tt').tabs('exists', title)) {
                                                tabsWindow(title).$("#account-datagrid-receipthandlist").datagrid('reload');
                                            }
                                            refreshPanel(id, 'edit');
                                        } else {
                                            $.messager.alert("提醒", "反审失败 " + json.msg);
                                        }
                                    },
                                    error: function () {
                                        loaded();
                                        $.messager.alert("错误", "反审出错");
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receipthand/receiptHandBackBtn.do">
                {
                    type: 'button-back',
                    handler: function (data) {
                        refreshPanel(data.id, getType(data.status));
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receipthand/receiptHandNextBtn.do">
                {
                    type: 'button-next',
                    handler: function (data) {
                        refreshPanel(data.id, getType(data.status));
                    }
                },
                </security:authorize>
                {}
            ],
            buttons: [
                {},
                <security:authorize url="/account/receipthand/receiptHandBillDetailExport.do">
                {
                    id: 'button-export-detail',
                    name: '单据明细导出',
                    iconCls: 'button-export',
                    handler: function () {
                        var id = $("#receipthand-id-baseinfo").val();
                        $("#account-dialog-receiptHandBillDetailExport").Excel('export', {
                            type: 'exportUserdefined',
                            name: '单据明细导出',
                            url: 'account/receipthand/exportReceiptHandBillDetailList.do?id=' + id
                        });
                        $("#account-dialog-receiptHandBillDetailExport").trigger("click");
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receipthand/receiptHandBillPrint.do">
                {
                    id: 'menuButton',
                    type: 'menu',
                    name: '打印',
                    iconCls: 'button-preview',
                    button: [
                        <security:authorize url="/account/receipthand/receiptHandBillPrintViewBtn.do">
                        {
                            id: 'preview-bill-button',
                            name: '单据打印预览',
                            iconCls: 'button-preview',
                            handler: function () {
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/account/receipthand/receiptHandBillPrintBtn.do">
                        {
                            id: 'print-bill-button',
                            name: '单据打印',
                            iconCls: 'button-print',
                            handler: function () {
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/account/receipthand/receiptHandDetailPrintBtn.do">
                        {
                            id: 'preview-detail-button',
                            name: '明细打印预览',
                            iconCls: 'button-preview',
                            handler: function () {
                            }
                        },
                        </security:authorize>

                        <security:authorize url="/account/receipthand/receiptHandDetailPrintBtn.do">
                        {
                            id: 'print-detail-button',
                            name: '明细打印',
                            iconCls: 'button-print',
                            handler: function () {
                            }
                        },
                        </security:authorize>
                        {}
                    ]
                },
                </security:authorize>
                {}
            ],
            model: 'bill',
            type: '${type}',
            id: '${id}',
            taburl: '/account/receipthand/showReceiptHandListPage.do',		//tab标签的url地址。
            datagrid: 'account-datagrid-receipthandlist'
        });
    });

    function showReceiptPageFromRH(billid, billtype) {
        if ("1" == billtype) {
            top.addOrUpdateTab('sales/receiptPage.do?type=view&id=' + billid, '销售发货回单查看');
        } else if ("2" == billtype) {
            top.addOrUpdateTab('sales/rejectBill.do?type=view&id=' + billid, '退货通知单查看');
        } else if ("3" == billtype) {
            top.addOrUpdateTab('account/receivable/showCustomerPushBanlaceListPage.do?id=' + billid + '&status=3', '客户应收款冲差');
        }
    }
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //单据打印
        AgReportPrint.init({
            id: "listPage-ReceiptHand-dialog-print",
            code: "account_receipthand",
            url_preview: "print/account/receiptHandPrintView.do",
            url_print: "print/account/receiptHandPrint.do",
            btnPreview: "preview-bill-button",
            btnPrint: "print-bill-button",
            single: true,
            getData: getData
        });
        //明细打印预览
        AgReportPrint.init({
            id: "listPage-ReceiptHandDetail-dialog-print",
            code: "account_receipthanddetail",
            url_preview: "print/account/receiptHandDetailPrintView.do",
            url_print: "print/account/receiptHandDetailPrint.do",
            btnPreview: "preview-detail-button",
            btnPrint: "print-detail-button",
            single: true,
            getData: getData
        });
        function getData(tableId, printParam) {
            var id = $("#receipthand-id-baseinfo").val();
            printParam.id = id;
            var printtimes = $("#receipthand-printtimes-baseinfo").numberbox('getValue');
            if (printtimes > 0)
                printParam.printIds = [id];
            return true;
        }
    });
</script>
<%--打印结束 --%>
</body>
</html>
