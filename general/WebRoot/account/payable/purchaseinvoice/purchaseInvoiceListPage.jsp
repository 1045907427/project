<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>采购发票列表页面</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="account-buttons-purchaseInvoicePage" style="height:26px;"></div>
    </div>
    <div data-options="region:'center'">
        <table id="account-datagrid-purchaseInvoicePage" data-options="border:false"></table>
    </div>
</div>
<div id="account-datagrid-toolbar-purchaseInvoicePage">
    <form action="" id="account-form-query-purchaseInvoicePage" method="post">
        <table class="querytable">
            <tr>
                <td>业 务 日 期:</td>
                <td><input type="text" name="businessdate1" style="width:100px;" class="Wdate"
                           onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/>
                    到 <input type="text" name="businessdate2" class="Wdate" style="width:100px;"
                             onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/>
                </td>
                <td>供应商:</td>
                <td><input id="account-query-supplierid" type="text" name="supplierid" style="width: 180px;"/></td>
                <td>编号:</td>
                <td><input type="text" name="id" style="width: 165px;"/></td>
            </tr>
            <tr>
                <td>采 购 部 门:</td>
                <td><input id="account-query-buydept" type="text" name="buydept" style="width: 225px;"/></td>
                <td>发票号:</td>
                <td><input type="text" name="invoiceno" style="width:180px;"/></td>
                <td>状态:</td>
                <td><select name="isClose" style="width:165px;">
                    <option></option>
                    <option value="0" selected="selected">未审核</option>
                    <option value="3">审核通过</option>
                    <option value="4">关闭</option>
                </select></td>
            </tr>
            <tr>
                <td>来源单据编号:</td>
                <td><input type="text" name="sourceid" style="width:225px;"/></td>
                <td>打印状态:</td>
                <td>
                    <select name="printsign" style="width:180px;">
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
                    <input type="hidden" name="queryprinttimes" value="0"/>
                </td>
                <td rowspan="3" colspan="2" class="tdbutton">
                    <a href="javaScript:void(0);" id="account-queay-purchaseInvoice" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="account-reload-purchaseInvoice" class="button-qr">重置</a>
                    <span id="account-query-advanced-purchaseInvoice"></span>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="account-panel-relation-upper"></div>
<div id="account-panel-sourceQueryPage"></div>
<div id="account-dialog-purchaseInvoicePushPage"></div>
<div id="purchaseInvoicePush-account-dialog"></div>
<script type="text/javascript">
    var initQueryJSON = $("#account-form-query-purchaseInvoicePage").serializeJSON();
    var PI_footerobject = null;
    $(function () {
        //按钮
        $("#account-buttons-purchaseInvoicePage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/account/payable/purchaseInvoiceAddPage.do">
                {
                    type: 'button-add',
                    handler: function () {
                        //top.addOrUpdateTab('account/payable/showPurchaseInvoicePage.do', "采购发票新增");
                        top.addOrUpdateTab('account/payable/showPurchaseInvoiceAnotherAddPage.do', "采购发票新增");
                    }
                },
                </security:authorize>
                <security:authorize url="/account/payable/purchaseInvoiceViewPage.do">
                {
                    type: 'button-view',
                    handler: function () {
                        var con = $("#account-datagrid-purchaseInvoicePage").datagrid('getSelected');
                        if (con == null) {
                            $.messager.alert("提醒", "请选择一条记录");
                            return false;
                        }
                        top.addOrUpdateTab('account/payable/showPurchaseInvoiceEditPage.do?id=' + con.id, "采购发票查看");
                    }
                },
                </security:authorize>
                <security:authorize url="/account/payable/purchaseInvoicePreviewPage.do">
                {
                    type: 'button-preview',
                    handler: function () {
                        //orderPrintViewFunc();
                    }
                },
                </security:authorize>
                <security:authorize url="/account/payable/purchaseInvoicePrintPage.do">
                {
                    type: 'button-print',
                    handler: function () {
                        //orderPrintFunc();
                    }
                },
                </security:authorize>
                <security:authorize url="/account/payable/purchaseInvoiceExport.do">
                {
                    type: 'button-export',
                    attr: {
                        datagrid: "#account-datagrid-purchaseInvoicePage",
                        queryForm: "#account-form-query-purchaseInvoicePage", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
                        type: 'exportUserdefined',
                        url: 'account/payable/exportPurchaseInvoicePushPage.do',
                        name: '采购发票列表'
                    }
                },
                </security:authorize>
                {
                    type: 'button-commonquery',
                    attr: {
                        //查询针对的表
                        name: 't_account_purchase_invoice',
                        //查询针对的表格id
                        datagrid: 'account-datagrid-purchaseInvoicePage',
                        plain: true
                    }
                },
                {}
            ],
            buttons: [
                {},
                <security:authorize url="/account/payable/showPurchaseInvoicePushPage.do">
                {
                    id: 'purchasepush-button',
                    name: '冲差',
                    iconCls: 'icon-poorat',
                    handler: function () {
                        var con = $("#account-datagrid-purchaseInvoicePage").datagrid('getSelected');
                        if (con == null) {
                            $.messager.alert("提醒", "请选择一条记录");
                            return false;
                        }
                        if (con.status == '3' || con.status == '4') {
                            $.messager.alert("提醒", "审核通过的采购发票不能进行冲差");
                            return false;
                        }
                        $.messager.confirm("提醒", "是否对当前采购发票进行冲差?", function (r) {
                            if (r) {
                                $('#account-dialog-purchaseInvoicePushPage').dialog({
                                    title: '采购发票冲差',
                                    width: 400,
                                    height: 350,
                                    collapsible: false,
                                    minimizable: false,
                                    maximizable: true,
                                    resizable: true,
                                    closed: true,
                                    cache: false,
                                    href: 'account/payable/showPurchaseInvoicePushPage.do?id=' + con.id,
                                    modal: true,
                                    buttons: [
                                        {
                                            text: '确定',
                                            iconCls: 'button-save',
                                            plain: true,
                                            handler: function () {
                                                $.messager.confirm("提醒", "是否对采购发票应付款进行冲差？", function (r) {
                                                    if (r) {
                                                        $("#account-form-purchaseInvoicePush").submit();
                                                    }
                                                });

                                            }
                                        }
                                    ]
                                });
                                $('#account-dialog-purchaseInvoicePushPage').dialog("open");
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/erpconnect/addPurchaseInvoiceAccountVouch.do">
                {
                    id: 'purchasepush-account',
                    name: '生成凭证',
                    iconCls: 'button-audit',
                    handler: function () {
                        var rows = $("#account-datagrid-purchaseInvoicePage").datagrid('getChecked');
                        if (rows == null || rows.length == 0) {
                            $.messager.alert("提醒", "请选择至少一条记录");
                            return false;
                        }
                        var ids = "";
                        for (var i = 0; i < rows.length; i++) {
                            if (rows[i].status != '4' && rows[i].status != '3') {
                                $.messager.alert("提醒", "请选择审核通过或关闭状态数据");
                                return false;
                            }
                            if (i == 0) {
                                ids = rows[i].id;
                            } else {
                                ids += "," + rows[i].id;
                            }
                        }
                        $("#purchaseInvoicePush-account-dialog").dialog({
                            title: '采购发票冲差凭证',
                            width: 400,
                            height: 260,
                            closed: false,
                            modal: true,
                            cache: false,
                            href: 'erpconnect/showPurchasePushAccountVouchPage.do',
                            onLoad: function () {
                                $("#purchasePushAccount-ids").val(ids);
                            }
                        });
                    }
                },
                </security:authorize>
                {}
            ],
            model: 'bill',
            type: 'list',
            tname: 't_account_purchase_invoice'
        });

        var purchaseInvoiceJson = $("#account-datagrid-purchaseInvoicePage").createGridColumnLoad({
            name: 't_account_purchase_invoice',
            frozenCol: [[
                {field: 'idok', checkbox: true, isShow: true}
            ]],
            commonCol: [[
                {field: 'id', title: '编号', width: 125, sortable: true},
                {field: 'businessdate', title: '业务日期', width: 80, sortable: true},
                {field: 'invoiceno', title: '发票号', width: 65, sortable: true},
                {field: 'supplierid', title: '供应商编码', width: 70, sortable: true},
                {field: 'suppliername', title: '供应商名称', width: 100, isShow: true},
                {
                    field: 'handlerid', title: '对方经手人', width: 80, sortable: true, hidden: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.handlername;
                    }
                },
                {
                    field: 'buydept', title: '采购部门', width: 100, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.buydeptname;
                    }
                },
                {
                    field: 'buyuser', title: '采购员', width: 80, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.buyusername;
                    }
                },
                {field: 'sourceid', title: '来源单据编号', width: 130, sortable: true},
                {
                    field: 'taxamount', title: '含税总金额', resizable: true, sortable: true, align: 'right',
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'notaxamount', title: '未税总金额', resizable: true, sortable: true, align: 'right', hidden: true,
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'invoiceamount', title: '应付总金额', resizable: true, sortable: true, align: 'right',
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'writeoffamount', title: '核销总金额', resizable: true, sortable: true, align: 'right',
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'tailamount', title: '尾差金额', resizable: true, sortable: true, align: 'right',
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                {field: 'vouchertimes', title: '生成凭证次数', align: 'center', width: 80},
                {field: 'printtimes', title: '打印次数', width: 60, hidden: true},
                {field: 'printstate', title: '打印状态', width: 80, isShow: true,
                    formatter: function (value, rowData, index) {
                        if (rowData.printtimes > 0) {
                            return "已打印";
                        } else if (rowData.printtimes == null) {
                            return "";
                        } else {
                            return "未打印";
                        }
                    }
                },
                {field: 'addusername', title: '制单人', width: 80, sortable: true, hidden: true},
                {field: 'addtime', title: '制单时间', width: 80, sortable: true, hidden: true},
                {field: 'auditusername', title: '审核人', width: 80, hidden: true, sortable: true},
                {field: 'audittime', title: '审核时间', width: 80, hidden: true, sortable: true},
                {field: 'writeoffdate', title: '核销日期', width: 80, hidden: true, sortable: true},
                {field: 'stopusername', title: '中止人', width: 80, hidden: true, sortable: true},
                {field: 'stoptime', title: '中止时间', width: 80, hidden: true, sortable: true},
                {
                    field: 'status', title: '状态', width: 60, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return getSysCodeName("status", value);
                    }
                },
                {field: 'remark', title: '备注', width: 80, sortable: true}
            ]]
        });
        $("#account-datagrid-purchaseInvoicePage").datagrid({
            authority: purchaseInvoiceJson,
            frozenColumns: purchaseInvoiceJson.frozen,
            columns: purchaseInvoiceJson.common,
            fit: true,
            method: 'post',
            rownumbers: true,
            pagination: true,
            idField: 'id',
            sortName: 'businessdate',
            sortOrder: 'desc',
            url: 'account/payable/showPurchaseInvoiceList.do',
            queryParams: initQueryJSON,
            singleSelect: false,
            checkOnSelect: true,
            selectOnCheck: true,
            showFooter: true,
            pageSize: 100,
            toolbar: '#account-datagrid-toolbar-purchaseInvoicePage',
            onDblClickRow: function (rowIndex, rowData) {
                top.addOrUpdateTab('account/payable/showPurchaseInvoiceEditPage.do?id=' + rowData.id, "采购发票查看");
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
            },
            onLoadSuccess: function () {
                var footerrows = $(this).datagrid('getFooterRows');
                if (null != footerrows && footerrows.length > 0) {
                    PI_footerobject = footerrows[0];
                    countTotalAmount();
                }
            }
        }).datagrid("columnMoving");
        $("#account-query-supplierid").widget({
            name: 't_account_purchase_invoice',
            width: 180,
            col: 'supplierid',
            view: true,
            singleSelect: true
        });
        $("#account-query-buydept").widget({
            name: 't_account_purchase_invoice',
            width: 225,
            col: 'buydept',
            view: true,
            singleSelect: true
        });
        //通用查询组建调用
//			$("#account-query-advanced-purchaseInvoice").advancedQuery({
//				//查询针对的表
//		 		name:'t_account_purchase_invoice',
//		 		//查询针对的表格id
//		 		datagrid:'account-datagrid-purchaseInvoicePage',
//		 		plain:true
//			});

        //回车事件
        controlQueryAndResetByKey("account-queay-purchaseInvoice", "account-reload-purchaseInvoice");

        //查询
        $("#account-queay-purchaseInvoice").click(function () {
            //把form表单的name序列化成JSON对象
            var queryJSON = $("#account-form-query-purchaseInvoicePage").serializeJSON();
            $("#account-datagrid-purchaseInvoicePage").datagrid("load", queryJSON);
        });
        //重置
        $("#account-reload-purchaseInvoice").click(function () {
            $("#account-query-supplierid").widget("clear");
            $("#account-query-buydept").widget("clear");
            $("#account-form-query-purchaseInvoicePage")[0].reset();
            var queryJson = [];
            queryJson['isClose'] = "0";
            $("#account-datagrid-purchaseInvoicePage").datagrid("load", queryJson);
        });
    });

    //计算勾选采购发票合计
    function countTotalAmount() {
        var rows = $("#account-datagrid-purchaseInvoicePage").datagrid('getChecked');
        var taxamount = 0, notaxamount = 0, invoiceamount = 0, writeoffamount = 0, tailamount = 0;
        for (var i = 0; i < rows.length; i++) {
            taxamount = Number(taxamount) + Number(rows[i].taxamount == undefined ? 0 : rows[i].taxamount);
            notaxamount = Number(notaxamount) + Number(rows[i].notaxamount == undefined ? 0 : rows[i].notaxamount);
            invoiceamount = Number(invoiceamount) + Number(rows[i].invoiceamount == undefined ? 0 : rows[i].invoiceamount);
            writeoffamount = Number(writeoffamount) + Number(rows[i].writeoffamount == undefined ? 0 : rows[i].writeoffamount);
            tailamount = Number(tailamount) + Number(rows[i].tailamount == undefined ? 0 : rows[i].tailamount);
        }
        var footerrows = [
            {
                id: '选中金额',
                taxamount: taxamount,
                notaxamount: notaxamount,
                invoiceamount: invoiceamount,
                writeoffamount: writeoffamount,
                tailamount: tailamount
            }
        ];
        if (null != PI_footerobject) {
            footerrows.push(PI_footerobject);
        }
        $("#account-datagrid-purchaseInvoicePage").datagrid("reloadFooter", footerrows);
    }

</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "listPage-PurchaseInvoice-dialog-print",
            code: "purchase_invoice",
            tableId: "account-datagrid-purchaseInvoicePage",
            url_preview: "print/account/purchaseInvoicePrintView.do",
            url_print: "print/account/purchaseInvoicePrint.do",
            btnPreview: "button-preview",
            btnPrint: "button-print",
            printlimit:"${printlimit}",
            getData: getData
        });
        function getData(tableId, printParam) {
            var data = $("#" + tableId).datagrid('getChecked');
            if (data == null || data.length == 0) {
                $.messager.alert("提醒", "请选择一条记录");
                return false;
            }
            for (var i = 0; i < data.length; i++) {
                if (data[i].status != '3' && data[i].status != '4') {
                    $.messager.alert("提醒", data[i].id + "保存状态下不能打印");
                    return false;
                }
            }
            return data;
        }
    });
</script>
<%--打印结束 --%>
</body>
</html>
