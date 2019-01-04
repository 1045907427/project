<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>销售发票操作页面</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>
<body>
<div id="account-layout-salesInvoicePage" class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="account-buttons-salesInvoicePage"></div>
    </div>
    <div data-options="region:'center',border:false">
        <div id="account-panel-salesInvoicePage"></div>
    </div>
</div>
<div id="account-panel-relation-upper"></div>
<div id="account-panel-sourceQueryPage"></div>
<div id="workflow-addidea-dialog-page"></div>
<div id="account-panel-salesinvoiceDetailPage"></div>
<div id="account-panel-customerPushBanlance-addpage"></div>
<div id="account-dialog-writeoff"></div>
<div id="account-dialog-salesinvoice-detail"></div>
<input type="hidden" id="salesinvoice-detail-close-detele" value="0"/>
<div style="display:none">
    <div id="account-invoice-dialog-print">
        <form action="" method="post" id="account-invoice-dialog-print-form">
            <table>
                <tr>
                    <td>负商品是否转为折扣:</td>
                    <td>
                        <select name="neggoodstodiscount" style="width:60px">
                            <option value="0">否</option>
                            <option value="1">是</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>非商品(折扣、冲差)是否转为折扣:</td>
                    <td>
                        <select name="zkgoodstodiscount" style="width:60px">
                            <option value="0">否</option>
                            <option value="1" selected="selected">是</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>折扣合并:</td>
                    <td>
                        <select name="isShowDiscountSum" style="width:60px">
                            <option value="0">否</option>
                            <option value="1">是</option>
                        </select>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<div id="account-invoice-dialog-rebate"></div>
<form id="report-export-form">
    <input type="hidden" id="account-hidden-billid" name="exportid"/>
</form>
<script type="text/javascript">
    var salesinvoice_AjaxConn = function (Data, Action) {
        var MyAjax = $.ajax({
            type: 'post',
            cache: false,
            url: Action,
            data: Data,
            async: false
        })
        return MyAjax.responseText;
    }
    //根据初始的列与用户保存的列生成以及字段权限生成新的列
    var tableColJson = $("#account-datagrid-salesInvoiceAddPage").createGridColumnLoad({
        name: 't_account_sales_invoice_detail',
        frozenCol: [[]],
        commonCol: [[
            {field: 'sourceid', title: '单据编号', width: 130, hidden: true},
            {
                field: 'sourcetype', title: '单据类型', width: 80, hidden: true,
                formatter: function (value, row, index) {
                    if (value == '1') {
                        return "销售发货回单";
                    } else if (value == '2') {
                        return "销售退货通知单";
                    }
                }
            },
            {field: 'goodsid', title: '商品编码', width: 60},
            {field: 'goodsname', title: '商品名称', width: 220, aliascol: 'goodsid'},
            {field: 'barcode', title: '条形码', width: 85, aliascol: 'goodsid'},
            {field: 'brandname', title: '商品品牌', width: 80, aliascol: 'goodsid', hidden: true},
            {field: 'model', title: '规格型号', width: 80, aliascol: 'goodsid', hidden: true},
            {
                field: 'boxnum', title: '箱装量', width: 40, aliascol: 'goodsid', hidden: true,
                formatter: function (value, rowData, rowIndex) {
                    return formatterBigNumNoLen(value);
                }
            },
            {field: 'unitname', title: '单位', width: 35},
            {
                field: 'unitnum', title: '数量', width: 60, align: 'right',
                formatter: function (value, row, index) {
                    return formatterBigNumNoLen(value);
                }
            },
            {
                field: 'taxprice', title: '单价', width: 60, align: 'right',
                formatter: function (value, row, index) {
                    if (row.isdiscount != '1' && row.isdiscount != '2') {
                        return formatterMoney(value);
                    }
                }
            },
            {
                field: 'boxprice', title: '箱价', width: 60, align: 'right', hidden: true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'taxamount', title: '金额', width: 60, align: 'right',
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'notaxprice', title: '未税单价', width: 60, align: 'right', hidden: true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'notaxamount', title: '未税金额', width: 60, align: 'right', hidden: true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {field: 'taxtype', title: '税种类型', width: 60, hidden: true},
            {
                field: 'taxtypename', title: '税种', width: 60, aliascol: 'taxtype',
                formatter: function (value, row, index) {
                    if (row.taxtype != null && row.taxtype != "") {
                        return value;
                    }
                }
            },
            {
                field: 'tax', title: '税额', width: 60, align: 'right', hidden: true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            /**
             {field:'discountamount', title:'折扣后金额',width:80,align:'right',
                 formatter:function(value,row,index){
                     return formatterMoney(value);
                 }
             },**/
            {field: 'auxnumdetail', title: '辅数量', width: 60, align: 'right'},
            {field: 'remark', title: '备注', width: 120}
        ]]
    });
    var page_url = "";
    var page_type = '${type}';
    if (page_type == "view" || page_type == "handle") {
        page_url = "account/receivable/salesInvoiceViewPage.do?id=${id}";
    } else if (page_type == "edit") {
        page_url = "account/receivable/salesInvoiceEditPage.do?id=${id}";
    } else if (page_type == "add") {

    }
    $(function () {
        $("#account-panel-salesInvoicePage").panel({
            href: page_url,
            cache: false,
            maximized: true,
            border: false
        });
        //按钮
        $("#account-buttons-salesInvoicePage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/account/receivable/showSalesInvoiceAddPage.do">
                {
                    type: 'button-add',
                    handler: function () {
                        top.addOrUpdateTab('account/receivable/showSalesInvoiceAddPage.do', "销售核销");
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receivable/editSalesInvoiceHold.do">
                {
                    type: 'button-hold',
                    handler: function () {
                        $.messager.confirm("提醒", "确定暂存该销售发票信息？", function (r) {
                            if (r) {
                                var type = $("#account-buttons-salesInvoicePage").buttonWidget("getOperType");
                                if (type == "add") {
                                    //暂存
                                    $("#account-form-salesInvoiceAdd").attr("action", "account/receivable/addSalesInvoiceHold.do");
                                    $("#account-form-salesInvoiceAdd").submit();
                                } else if (type == "edit") {
                                    //暂存
                                    $("#account-form-salesInvoiceAdd").attr("action", "account/receivable/editSalesInvoiceHold.do");
                                    $("#account-form-salesInvoiceAdd").submit();
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receivable/editSalesInvoiceSave.do">
                {
                    type: 'button-save',
                    handler: function () {
                        var type = $("#account-buttons-salesInvoicePage").buttonWidget("getOperType");
                        if (type == "add") {
                            //暂存
                            $("#account-form-salesInvoiceAdd").attr("action", "account/receivable/addSalesInvoiceSave.do");
                            $("#account-form-salesInvoiceAdd").submit();
                        } else if (type == "edit") {
                            $("#account-form-salesInvoiceAdd").attr("action", "account/receivable/editSalesInvoiceSave.do");
                            $("#account-form-salesInvoiceAdd").submit();
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receivable/editSalesInvoiceSaveAudit.do">
                {
                    type: 'button-saveaudit',
                    handler: function () {
                        var applytype = $("#salesInvoice-select-applytype").val();
                        var invoiceno = $("#salesInvoice-invoiceno").val();
                        var invoicecode = $("#salesInvoice-invoicecode").val();
                        if (applytype == "1" && (invoiceno == "" || invoicecode == "")) {
                            $.messager.alert("提示", "发票号或发票代码不能为空，请填写再审核!");
                            return false;
                        }
                        $.messager.confirm("提醒", "确定保存并审核该销售发票？", function (r) {
                            if (r) {
                                var type = $("#account-buttons-salesInvoicePage").buttonWidget("getOperType");
                                $("#account-saveaudit-salesInvoiceDetail").val("saveaudit");
                                $("#account-form-salesInvoiceAdd").attr("action", "account/receivable/editSalesInvoiceSave.do");
                                $("#account-form-salesInvoiceAdd").submit();
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receivable/salesInvoiceGiveUp.do">
                {
                    type: 'button-giveup',
                    handler: function () {
                        var type = $("#account-buttons-salesInvoicePage").buttonWidget("getOperType");
                        if (type == "add") {
                            var currTitle = top.$('#tt').tabs('getSelected').panel('options').title;
                            top.closeTab(currTitle);
                        }
                        else if (type == "edit") {
                            var id = $("#account-hidden-billid").val();
                            if (id == "") {
                                return false;
                            }
                            $("#account-panel-salesInvoicePage").panel({
                                href: 'account/receivable/salesInvoiceViewPage.do?id=' + id,
                                title: '',
                                cache: false,
                                maximized: true,
                                border: false
                            });
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receivable/deleteSalesInvoice.do">
                {
                    type: 'button-delete',
                    handler: function () {
                        $.messager.confirm("提醒", "是否删除当前销售发票？", function (r) {
                            if (r) {
                                var id = $("#account-hidden-billid").val();
                                if (id != "") {
                                    loading("提交中..");
                                    $.ajax({
                                        url: 'account/receivable/deleteSalesInvoice.do?id=' + id,
                                        type: 'post',
                                        dataType: 'json',
                                        success: function (json) {
                                            if (json.flag) {
                                                var object = $("#account-buttons-salesInvoicePage").buttonWidget("removeData", id);
                                                $.messager.alert("提醒", "删除成功");
                                                parent.closeNowTab();
                                                //if(null!=object){
                                                //	$("#account-panel-salesInvoicePage").panel({
                                                //		href:'account/receivable/salesInvoiceViewPage.do?id='+object.id,
                                                //		title:'',
                                                //	    cache:false,
                                                //	    maximized:true,
                                                //	    border:false
                                                //	});
                                                //}else{
                                                //	parent.closeNowTab();
                                                //}
                                            } else {
                                                $.messager.alert("提醒", "删除失败");
                                            }
                                            loaded();
                                        },
                                        error: function () {
                                            loaded();
                                            $.messager.alert("错误", "删除失败");
                                        }
                                    });
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receivable/auditSalesInvoice.do">
                {
                    type: 'button-audit',
                    handler: function () {
                        var applytype = $("#salesInvoice-hidden-applytype").val();
                        var invoiceno = $("#salesInvoice-invoiceno").val();
                        var invoicecode = $("#salesInvoice-invoicecode").val();
                        if (applytype == "1" && (invoiceno == "" || invoicecode == "")) {
                            $.messager.alert("提示", "发票号或发票代码不能为空，请填写再审核!");
                            return false;
                        }
                        $.messager.confirm("提醒", "是否审核销售发票？", function (r) {
                            if (r) {
                                var id = $("#account-hidden-billid").val();
                                if (id != "") {
                                    loading("提交中..");
                                    $.ajax({
                                        url: 'account/receivable/auditSalesInvoice.do?id=' + id,
                                        type: 'post',
                                        dataType: 'json',
                                        success: function (json) {
                                            loaded();
                                            if (json.flag) {
                                                $.messager.alert("提醒", "审核成功");
                                                $("#account-salesInvoice-status").val("3");
                                                $("#account-buttons-salesInvoicePage").buttonWidget("setDataID", {
                                                    id: id,
                                                    state: '3',
                                                    type: 'view'
                                                });
                                                $("#account-buttons-salesInvoicePage").buttonWidget("disableButton", 'button-push');
                                                $("#account-buttons-salesInvoicePage").buttonWidget("disableButton", 'button-rebate');
                                                $("#account-buttons-salesInvoicePage").buttonWidget("enableButton", 'button-cancel');
                                                $("#account-buttons-salesInvoicePage").buttonWidget("enableButton", "button-print");
                                                $("#account-buttons-salesInvoicePage").buttonWidget("enableButton", "button-preview");
                                                if (json.hasblance == 1) {
                                                    $("#account-buttons-salesInvoicePage").buttonWidget("enableButton", "print-blance-button");
                                                    $("#account-buttons-salesInvoicePage").buttonWidget("enableButton", "printview-blance-button");
                                                }
                                            } else {
                                                $.messager.alert("提醒", "审核失败");
                                            }
                                        },
                                        error: function () {
                                            loaded();
                                            $.messager.alert("错误", "审核失败");
                                        }
                                    });
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receivable/oppauditSalesInvoice.do">
                {
                    type: 'button-oppaudit',
                    handler: function () {
                        var id = $("#account-hidden-billid").val();
                        var businessdate = $("#account-salesInvoice-businessdate").val();
                        var flag = isDoneOppauditBillCaseAccounting(businessdate);
                        if (!flag) {
                            $.messager.alert("提醒", "业务日期不在会计区间内或未设置会计区间,不允许反审!");
                            return false;
                        }
                        var ret = salesinvoice_AjaxConn({invoiceid: id}, 'account/receivable/getOppauditFlag.do');
                        var retjson = $.parseJSON(ret);
                        if (retjson.flag) {
                            $.messager.confirm("提醒", "是否反审销售发票？", function (r) {
                                if (r) {
                                    if (id != "") {
                                        loading("提交中..");
                                        $.ajax({
                                            url: 'account/receivable/oppauditSalesInvoice.do?id=' + id,
                                            type: 'post',
                                            dataType: 'json',
                                            success: function (json) {
                                                loaded();
                                                if (json.flag) {
                                                    $.messager.alert("提醒", "反审成功");
                                                    $("#account-panel-salesInvoicePage").panel({
                                                        href: 'account/receivable/salesInvoiceEditPage.do?id=' + id,
                                                        title: '',
                                                        cache: false,
                                                        maximized: true,
                                                        border: false
                                                    });
                                                } else {
                                                    $.messager.alert("提醒", "反审失败");
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                        } else {
                            $.messager.alert("提醒", retjson.msg);
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receivable/exportSalesInvoiceList.do">
                {
                    type: 'button-export',
                    attr: {
                        queryForm: "#report-export-form",
                        type: 'exportUserdefined',
                        name: '销售发票清单',
                        url: 'account/receivable/exportSalesInvoiceList.do'
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receivable/showSalesInvoiceRelationPage.do">
                {
                    type: 'button-relation',
                    button: [
                        {},
                        <security:authorize url="/account/receivable/showSalesInvoiceRelationUpperPage.do">
                        {
                            type: 'relation-upper',
                            handler: function () {
                                $("#account-panel-relation-upper").dialog({
                                    href: "account/receivable/showSalesInvoiceRelationUpperPage.do",
                                    title: "上游单据查询",
                                    closed: false,
                                    modal: true,
                                    cache: false,
                                    width: 500,
                                    height: 350,
                                    buttons: [{
                                        text: '查询',
                                        handler: function () {
                                            sourceQuery();
                                        }
                                    }]
                                });
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/account/receivable/showSalesInvoiceSourcePage.do">
                        {
                            type: 'relation-upper-view',
                            handler: function () {
                                var id = $("#account-hidden-billid").val();
                                $("#account-panel-relation-upper").dialog({
                                    href: "account/receivable/showSalesInvoiceSourceListReferPage.do?id=" + id,
                                    title: "销售核销来源单据",
                                    fit: true,
                                    closed: false,
                                    modal: true,
                                    cache: false,
                                    maximizable: true,
                                    resizable: true,
                                    onLoad: function () {
                                        $("#salesinvoice-detail-close-detele").val("0");
                                    },
                                    onClose: function () {
                                        if ($("#salesinvoice-detail-close-detele").val() == "1") {
                                            top.addOrUpdateTab('account/receivable/showSalesInvoiceEditPage.do?id=' + id, "销售核销查看");
                                        }
                                    }
                                });
                            }
                        },
                        </security:authorize>
                        {}
                    ]
                },
                </security:authorize>
                <security:authorize url="/account/receivable/salesInvoiceWorkflow.do">
                {
                    type: 'button-workflow',
                    button: [
                        {},
                        {
                            type: 'workflow-submit',
                            handler: function () {
                                $.messager.confirm("提醒", "是否提交该销售发票信息到工作流?", function (r) {
                                    if (r) {
                                        var id = $("#account-hidden-billid").val();
                                        if (id == "") {
                                            $.messager.alert("警告", "没有需要提交工作流的信息!");
                                            return false;
                                        }
                                        $.ajax({
                                            url: 'account/receivable/submitSalesInvoicePageProcess.do',
                                            dataType: 'json',
                                            type: 'post',
                                            data: 'id=' + id,
                                            success: function (json) {
                                                if (json.flag == true) {
                                                    $.messager.alert("提醒", "提交成功!");
                                                    $("#account-panel-salesInvoicePage").panel("refresh");
                                                }
                                                else {
                                                    $.messager.alert("提醒", "提交失败!");
                                                }
                                            }
                                        });
                                    }
                                });
                            }
                        },
                        {
                            type: 'workflow-addidea',
                            handler: function () {
                                var order_type = '${type}';
                                if (order_type == "handle") {
                                    $("#workflow-addidea-dialog-page").dialog({
                                        title: '填写处理意见',
                                        width: 450,
                                        height: 300,
                                        closed: false,
                                        cache: false,
                                        modal: true,
                                        href: 'workflow/commentAddPage.do?id=' + handleWork_taskId
                                    });
                                }
                            }
                        },
                        {
                            type: 'workflow-viewflow',
                            handler: function () {
                                var id = $("#account-hidden-billid").val();
                                if (id == "") {
                                    return false;
                                }
                                $("#workflow-addidea-dialog-page").dialog({
                                    title: '查看流程',
                                    width: 600,
                                    height: 450,
                                    closed: false,
                                    cache: false,
                                    modal: true,
                                    maximizable: true,
                                    resizable: true,
                                    href: 'workflow/commentListPage.do?id=' + id
                                });
                            }
                        },
                        {
                            type: 'workflow-viewflow-pic',
                            handler: function () {
                                var id = $("#account-hidden-billid").val();
                                if (id == "") {
                                    return false;
                                }
                                $("#workflow-addidea-dialog-page").dialog({
                                    title: '查看流程',
                                    width: 600,
                                    height: 450,
                                    closed: false,
                                    cache: false,
                                    modal: true,
                                    maximizable: true,
                                    resizable: true,
                                    href: 'workflow/showDiagramPage.do?id=' + id
                                });
                            }
                        },
                        {
                            type: 'workflow-recover',
                            handler: function () {

                            }
                        }
                    ]
                },
                </security:authorize>
                <security:authorize url="/account/receivable/salesInvoiceViewBackPage.do">
                {
                    type: 'button-back',
                    handler: function (data) {
                        $("#account-panel-salesInvoicePage").panel({
                            href: 'account/receivable/salesInvoiceEditPage.do?id=' + data.id,
                            title: '',
                            cache: false,
                            maximized: true,
                            border: false
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receivable/salesInvoiceViewNextPage.do">
                {
                    type: 'button-next',
                    handler: function (data) {
                        $("#account-panel-salesInvoicePage").panel({
                            href: 'account/receivable/salesInvoiceEditPage.do?id=' + data.id,
                            title: '',
                            cache: false,
                            maximized: true,
                            border: false
                        });
                    }
                },
                </security:authorize>
                {}
            ],
            buttons: [
                {},
                <security:authorize url="/account/receivable/showReceiptUnWriteOffPage.do">
                {
                    id: 'button-viewreceipt',
                    name: '查看回单',
                    iconCls: 'button-view',
                    handler: function () {
                        var invoiceid = $("#account-hidden-billid").val();
                        if (invoiceid != "") {
                            $("#account-dialog-writeoff").dialog({
                                href: "account/receivable/showReceiptUnWriteOffPage.do?invoiceid=" + invoiceid,
                                title: "未核销回单列表",
                                fit: true,
                                modal: true,
                                cache: false,
                                maximizable: true,
                                resizable: true,
                                cache: false,
                                modal: true
                            });
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receivable/showSalesInvoiceDetailPage.do">
                {
                    id: 'button-viewdetail',
                    name: '查看明细',
                    iconCls: 'button-view',
                    handler: function () {
                        var invoiceid = $("#account-hidden-billid").val();
                        if (invoiceid != "") {
                            $("#account-dialog-salesinvoice-detail").dialog({
                                href: "account/receivable/showSalesInvoiceDetailPage.do?id=" + invoiceid,
                                title: "销售发票明细列表",
                                fit: true,
                                modal: true,
                                cache: false,
                                maximizable: true,
                                resizable: true,
                                cache: false,
                                modal: true
                            });
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receivable/showSalesInvoiceWriteoffPage.do">
                {
                    id: 'button-cancel',
                    name: '核销',
                    iconCls: 'button-writeoff',
                    handler: function () {
                        var invoiceid = $("#account-hidden-billid").val();
                        var customerid = $("#account-salesInvoice-customerid").customerWidget("getValue");
                        if (invoiceid != "") {
                            $('<div id="account-dialog-writeoff-info-content"></div>').appendTo('#account-dialog-writeoff');
                            $("#account-dialog-writeoff-info-content").dialog({
                                href: "account/receivable/showWriteoffPage.do?customerid=" + customerid + "&invoiceid=" + invoiceid,
                                title: "核销",
                                fit: true,
                                modal: true,
                                cache: false,
                                maximizable: true,
                                resizable: true,
                                cache: false,
                                modal: true,
                                buttons: [{
                                    text: '确定',
                                    iconCls: 'button-save',
                                    handler: function () {
                                        writeOffSubmit();
                                    }
                                }],
                                onClose: function () {
                                    $('#account-dialog-writeoff-info-content').dialog("destroy");
                                }
                            });
                        }
                    }
                },
                </security:authorize>
                {
                    id: 'menuButton', type: 'menu', name: '更多', iconCls: 'button-getdown',
                    button: [
                        <security:authorize url="/account/receivable/showCustomerPushBanlanceAddPage.do">
                        {
                            id: 'button-push',
                            name: '冲差',
                            iconCls: 'icon-poorat',
                            handler: function () {
                                var id = $("#account-hidden-billid").val();
                                if (id != "") {
                                    $('#account-panel-customerPushBanlance-addpage').dialog({
                                        title: '销售发票冲差',
                                        width: 400,
                                        height: 350,
                                        collapsible: false,
                                        minimizable: false,
                                        maximizable: true,
                                        resizable: true,
                                        closed: true,
                                        cache: false,
                                        href: 'account/receivable/showSalesInvoicePushAddPage.do?id=' + id,
                                        modal: true,
                                        buttons: [
                                            {
                                                text: '确定',
                                                iconCls: 'button-save',
                                                plain: true,
                                                handler: function () {
                                                    $.messager.confirm("提醒", "是否添加该客户应收款冲差数据？", function (r) {
                                                        if (r) {
                                                            $("#account-form-customerPushBanlance-add").submit();
                                                        }
                                                    });

                                                }
                                            }
                                        ]
                                    });
                                    $('#account-panel-customerPushBanlance-addpage').dialog("open");
                                }
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/account/receivable/addSalesInvoiceRebate.do">
                        {
                            id: 'button-rebate',
                            name: '返利',
                            iconCls: 'button-oppaudit',
                            handler: function () {
                                var id = $("#account-hidden-billid").val();
                                if (id != "") {
                                    $("#account-invoice-dialog-rebate").dialog({
                                        title: '返利',
                                        width: 350,
                                        height: 300,
                                        closed: false,
                                        cache: false,
                                        modal: true,
                                        href: 'account/receivable/showSalesInvoiceRebatePage.do?id=' + id,
                                        buttons: [{
                                            text: '确定',
                                            handler: function () {
                                                salesInvoiceRebate();
                                            }
                                        }]
                                    });
                                }
                            }
                        },
                        </security:authorize>
                        <c:if test="${iswriteoff == '1'}">
                        <security:authorize url="/account/receivable/salesInvoiceUncancel.do">
                        {
                            id: 'button-uncancel',
                            name: '反核销',
                            iconCls: 'button-getdown',
                            handler: function () {
                                var flag = isDoneOppauditBillCaseAccounting("${writeoffdate}");
                                if (!flag) {
                                    $.messager.alert("提醒", "核销日期不在会计区间内或未设置会计区间,不允许反审!");
                                    return false;
                                }
                                $.messager.confirm("提醒", "是否对当前销售发票进行反核销？", function (r) {
                                    if (r) {
                                        var invoiceid = $("#account-hidden-billid").val();
                                        if (invoiceid != "") {
                                            loading("反核销中...");
                                            $.ajax({
                                                url: 'account/receivable/uncancelSalesInvoice.do',
                                                dataType: 'json',
                                                type: 'post',
                                                data: 'invoiceid=' + invoiceid,
                                                success: function (json) {
                                                    loaded();
                                                    if (json.flag) {
                                                        $.messager.alert("提醒", "反核销成功!");
                                                        $("#account-panel-salesInvoicePage").panel("refresh");
                                                    }
                                                    else {
                                                        $.messager.alert("提醒", "反核销失败!");
                                                    }
                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        },
                        </security:authorize>
                        </c:if>
                        <security:authorize url="/account/receivable/salesInvoicePreview.do">
                        {
                            id: 'mbutton-preview',
                            name: '打印预览',
                            iconCls: 'button-preview',
                            handler: function () {
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/account/receivable/salesInvoicePrint.do">
                        {
                            id: 'mbutton-print',
                            name: '打印',
                            iconCls: 'button-print',
                            handler: function () {
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/account/receivable/customerPushBanlanceByInvoicePrintView.do">
                        {
                            id: 'printview-blance-button',
                            name: '折扣预览',
                            iconCls: 'button-preview',
                            handler: function () {
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/account/receivable/customerPushBanlanceByInvoicePrint.do">
                        {
                            id: 'print-blance-button',
                            name: '折扣打印',
                            iconCls: 'button-print',
                            handler: function () {
                            }
                        },
                        </security:authorize>
                        {}
                    ]
                }
            ],
            layoutid: 'account-layout-salesInvoicePage',
            model: 'bill',
            type: 'view',
            tab: '开票抽单列表',
            taburl: '/account/receivable/showSalesInvoiceListPage.do',
            id: '${id}',
            datagrid: 'account-datagrid-salesInvoicePage'
        });
    });

    //显示盘点单明细修改页面
    function beginEditDetail() {
        //验证表单
        var flag = $("#account-form-salesInvoiceAdd").form('validate');
        if (flag == false) {
            $.messager.alert("提醒", '请先选择出库仓库');
            $("#account-salesInvoice-accountid").focus();
            return false;
        }
        var row = $("#account-datagrid-salesInvoiceAddPage").datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        $('<div id="account-dialog-salesInvoiceAddPage-content"></div>').appendTo('#account-dialog-salesInvoiceAddPage');
        $('#account-dialog-salesInvoiceAddPage-content').dialog({
            title: '销售发票明细修改',
            width: 680,
            height: 400,
            collapsible: false,
            minimizable: false,
            maximizable: true,
            resizable: true,
            closed: true,
            cache: false,
            href: 'account/receivable/showSalesInvoiceDetailEditPage.do',
            modal: true,
            buttons: [
                {
                    text: '确定',
                    iconCls: 'button-save',
                    plain: true,
                    handler: function () {
                        editSaveDetail(false);
                    }
                }
            ],
            onClose: function () {
                $('#account-dialog-salesInvoiceAddPage-content').dialog("destroy");
            }
        });
        $('#account-dialog-salesInvoiceAddPage-content').dialog("open");
    }
    //修改保存
    function editSaveDetail(goFlag) {
        var flag = $("#account-form-salesInvoiceDetailAddPage").form('validate');
        if (flag == false) {
            return false;
        }
        var form = $("#account-form-salesInvoiceDetailAddPage").serializeJSON();
        var row = $("#account-datagrid-salesInvoiceAddPage").datagrid('getSelected');
        var rowIndex = $("#account-datagrid-salesInvoiceAddPage").datagrid('getRowIndex', row);
        form.goodsInfo = row.goodsInfo;
        $("#account-datagrid-salesInvoiceAddPage").datagrid('updateRow', {index: rowIndex, row: form}); //将数据更新到列表中
        $("#account-dialog-salesInvoiceAddPage-content").dialog('destroy');
        countTotal();
    }
    //计算合计
    function countTotal() {
        var rows = $("#account-datagrid-salesInvoiceAddPage").datagrid('getRows');
        var unitnum = 0;
        var taxamount = 0;
        var notaxamount = 0;
        var discountamount = 0;
        var tax = 0;
        for (var i = 0; i < rows.length; i++) {
            if ("1" != rows[i].isrebate) {
                unitnum = unitnum.add(rows[i].unitnum == undefined ? 0 : rows[i].unitnum);
            }
            taxamount = taxamount.add(rows[i].taxamount == undefined ? 0 : rows[i].taxamount);
            notaxamount = notaxamount.add(rows[i].notaxamount == undefined ? 0 : rows[i].notaxamount);
            tax = tax.add(rows[i].tax == undefined ? 0 : rows[i].tax);
            discountamount = discountamount.add(rows[i].discountamount == undefined ? 0 : rows[i].discountamount);
        }
        var foot = [{
            goodsid: '当页合计',
            unitnum: unitnum,
            taxamount: taxamount,
            notaxamount: notaxamount,
            tax: tax,
            discountamount: discountamount
        }];
        if (null != SID_footerobject) {
            foot.push(SID_footerobject);
        }
        $("#account-datagrid-salesInvoiceAddPage").datagrid("reloadFooter", foot);
    }
    //返利
    function salesInvoiceRebate() {
        var invoiceid = $("#account-hidden-billid").val();
        var rebateamount = $("#account-invoice-input-amount").numberbox("getValue");
        var remark = $("#account-invoice-input-remark").val();
        var subject = $("#account-customerPushBanlance-subject").widget("getValue");
        if (rebateamount <= 0) {
            $.messager.alert("提醒", "返利金额必须大于0");
            return false;
        }
        $.messager.confirm("提醒", "是否对当前销售发票进行返利？", function (r) {
            if (r) {
                loading("提交中..");
                $.ajax({
                    url: 'account/receivable/addSalesInvoiceRebate.do',
                    type: 'post',
                    dataType: 'json',
                    data: {id: invoiceid, rebateamount: rebateamount, remark: remark, subject: subject},
                    success: function (json) {
                        loaded();
                        if (json.flag) {
                            $.messager.alert("提醒", "返利成功");
                            $("#account-panel-salesInvoicePage").panel("refresh");
                        } else {
                            $.messager.alert("提醒", "销售发票已返利或者返利失败。");
                        }
                        $("#account-invoice-dialog-rebate").dialog("close");
                    },
                    error: function () {
                        loaded();
                        $.messager.alert("错误", "返利失败");
                    }
                });
            }
        });
    }
</script>
<%--打印开始 --%>
<script type="text/javascript">
    function updateDataGridPrintimes(id) {
        var thepage = tabsWindowURL("/account/receivable/showSalesInvoiceListPage.do");
        if (thepage == null) {
            return false;
        }
        var thegrid = thepage.$("#account-datagrid-salesInvoicePage");
        if (thegrid == null || thegrid.size() == 0) {
            return false;
        }
        if (id == null || id == "") {
            thegrid.datagrid('reload');
            return false;
        }
        var datarow = thegrid.datagrid("getRows");
        if (null != datarow && datarow.length > 0) {
            for (var i = 0; i < datarow.length; i++) {
                if (datarow[i].id == id) {
                    if (datarow[i].printtimes && !isNaN(datarow[i].printtimes)) {
                        datarow[i].printtimes = datarow[i].printtimes + 1;
                    } else {
                        datarow[i].printtimes = 1;
                    }
                    thegrid.datagrid('updateRow', {index: i, row: {printtimes: datarow[i].printtimes}});
                    break;
                }
            }
        }
    }
    $(function () {
        var printLimit = $("#account-salesInvoice-printlimit").val() || 0;
        if (AgReportPrint.isShowPrintTempletManualSelect("sales_invoice")) {
            $("#account-invoice-dialog-printtemplet-tr").show();
            //初始化打印对话框
            AgReportPrint.createPrintTempletSelectOption('account-invoice-dialog-printtemplet-id', 'sales_invoice');
        }
        //打印
        AgReportPrint.init({
            id: "account-invoice-dialog-print",
            code: "sales_invoice",
            url_preview: "print/account/salesInvoicePrintView.do",
            url_print: "print/account/salesInvoicePrint.do",
            btnPreview: "mbutton-preview",
            btnPrint: "mbutton-print",
            printlimit: printLimit,
            getData: function (tableId, printParam) {
                var id = $("#account-hidden-billid").val();
                if (id == "") {
                    $.messager.alert("警告", "找不到要打印的信息!");
                    return false;
                }
                var status = $("#account-salesInvoice-status").val();
                if (status != '3' && status != '4') {
                    $.messager.alert("提醒", "编号：" + id + "此发票清单不可打印");
                    return false;
                }
                var rowsData = $("#account-datagrid-salesInvoiceAddPage").datagrid("getRows");
                var taxtypeErrorMsg = [];
                var taxtype = "";
                for (var i = 0; i < rowsData.length; i++) {
                    if ((rowsData[i].goodsid == null || rowsData[i].goodsid == "" )
                        && (rowsData[i].brandid == null || rowsData[i].brandid == "" )) {
                        continue;
                    }
                    var theTaxtype = rowsData[i].taxtype;
                    if (taxtype == "") {
                        if (theTaxtype == "") {
                            $.messager.alert("提醒", "选中的第一行明细税种未知");
                            return false;
                        }
                        taxtype = theTaxtype;
                    } else if (taxtype != theTaxtype) {
                        taxtypeErrorMsg.push("行：" + (i + 1) + "税种与选中的第一行不相同");
                    }
                }
                if (taxtypeErrorMsg.length > 0) {
                    $.messager.alert("提醒", "抱歉，相同税种的明细才能打印。<br/>" + taxtypeErrorMsg.join('<br/>'));
                    return false;
                }

                printParam.idarrs = id;
                var printtimes = $("#account-salesInvoice-printtimes").val() || 0;
                if (printtimes > 0)
                    printParam.printIds = [id];
                return true;
            },
            onPrintSuccess: function (option, printParam) {
                updateDataGridPrintimes($("#account-hidden-billid").val());
                var printtimes = $("#account-salesInvoice-printtimes").val();
                $("#account-salesInvoice-printtimes").val(printtimes + 1);
            },
            printAfterHandler: function (option, printParam) {
                var printtimes = $("#account-salesInvoice-printtimes").val();
                $("#account-salesInvoice-printtimes").val(printtimes + 1);
            }
        });
        //打印
        AgReportPrint.init({
            id: "account-customerpushbanlance-dialog-print",
            code: "account_customerpushbanlance",
            url_preview: "print/account/customerPushBanlanceByInvoicePrintView.do",
            url_print: "print/account/customerPushBanlanceByInvoicePrint.do",
            btnPreview: "printview-blance-button",
            btnPrint: "print-blance-button",
            printlimit: printLimit,
            getData: function (tableId, printParam) {
                var id = $("#account-hidden-billid").val();
                if (id == "") {
                    $.messager.alert("警告", "找不到要打印的信息!");
                    return false;
                }
                var status = $("#account-salesInvoice-status").val();
                if (status != '3' && status != '4') {
                    $.messager.alert("提醒", "编号：" + id + "此发票清单不可打印");
                    return false;
                }
                printParam.idarrs = id;
                return true;
            }
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
