<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>销售开票操作页面</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>
<body>
<div id="account-layout-salesInvoiceBillPage" class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="account-buttons-salesInvoiceBillPage" style="height:26px;"></div>
    </div>
    <div data-options="region:'center',border:false">
        <div id="account-panel-salesInvoiceBillPage"></div>
    </div>
</div>
<div id="account-panel-relation-upper"></div>
<div id="account-panel-sourceQueryPage"></div>
<div id="workflow-addidea-dialog-page"></div>
<div id="account-panel-salesInvoiceBillDetailPage"></div>
<div id="account-panel-customerPushBanlance-addpage"></div>
<div id="account-dialog-writeoff"></div>
<div id="account-dialog-salesInvoiceBill-detail"></div>
<input type="hidden" id="salesInvoiceBill-detail-close-detele" value="0"/>
<div style="display:none">
    <div id="account-invoice-dialog-print">
        <form action="" method="post" id="account-invoice-dialog-print-form">
            <table>
                <tr id="account-invoice-dialog-printtemplet-tr" style="display:none">
                    <td>打印模板：</td>
                    <td>
                        <select id="account-invoice-dialog-printtemplet-id" name="templetid">
                        </select>
                    </td>
                </tr>
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
    <div id="account-salesInvoicePage-htkpdialogdiv">
        <form action="" method="post" id="account-salesInvoicePage-htkp-form"
              target="account-salesInvoicePage-htkp-form-iframe">
            <table>
                <tr id="account-salesInvoicePage-htkp-form-title-tr">
                    <td>导出文件名:</td>
                    <td>
                        <input type="text" id="account-salesInvoicePage-htkp-form-title" name="title"
                               style="width: 180px"/>
                    </td>
                </tr>
                <tr>
                    <td>收款人:</td>
                    <td>
                        <input type="text" id="account-salesInvoicePage-htkp-form-receipter" name="receipterid"
                               style="width: 180px" value="${jskpDefaultReceipter}"/>
                    </td>
                </tr>
                <tr>
                    <td>复核人:</td>
                    <td>
                        <input type="text" id="account-salesInvoicePage-htkp-form-checker" name="checkerid"
                               style="width: 180px" value="${jskpDefaultChecker}"/>
                    </td>
                </tr>
                <tr id="account-salesInvoicePage-htkp-form-jsgoodsversion-tr" display="none">
                    <td>商品版本号:</td>
                    <td>
                        <input type="text" id="account-salesInvoicePage-htkp-form-jsgoodsversion" name="jsgoodsversion"
                               class="easyui-validatebox" style="width: 180px" value="${jsGoodsVersion}"/>
                    </td>
                </tr>
            </table>
            <input type="hidden" id="account-salesInvoicePage-htkp-form-exportid" name="exportid"/>
        </form>
    </div>
    <iframe style="display: none;" id="account-salesInvoicePage-htkp-form-iframe"
            name="account-salesInvoicePage-htkp-form-iframe" src="about:blank"></iframe>
    <a href="javaScript:void(0);" id="account-salesInvoiceBillPage-buttons-exportclick" style="display: none"
       title="导出">导出</a>
    <a href="javaScript:void(0);" id="salesInvoicePage-dialog-htkpexcelexportclick" style="display: none"
       title="按航天格式导出EXCEL">按航天格式导出EXCEL</a>
    <a href="javaScript:void(0);" id="salesInvoicePage-dialog-htkp-importtoxml-click" style="display: none"
       title="导入并转成航天XML格式">导入并转成航天XML格式</a>
</div>
<div id="account-invoice-dialog-rebate"></div>
<form id="report-export-form">
    <input type="hidden" id="account-hidden-billid" name="exportid"/>
</form>
<div style="display: none">
    <div id="account-dialog-showJSKPSysParamConfigOper"></div>
</div>
<div id="account-dialog-salesInvoiceBillDetailExport"></div>
<script type="text/javascript">
    //根据初始的列与用户保存的列生成以及字段权限生成新的列
    var tableColJson = $("#account-datagrid-salesInvoiceBillAddPage").createGridColumnLoad({
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
                    } else if (value == '3') {
                        return "冲差单";
                    }
                }
            },
            {field: 'jstypeid', title: '金税分类编码', width: 100,isShow:true,hidden:true,
                formatter: function (value, rowData, rowIndex) {
                    if(rowData.goodsInfo!=null && rowData.goodsInfo.jstaxsortid!=null){
                        return rowData.goodsInfo.jstaxsortid;
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
                field: 'taxamount', title: '金额', width: 60, align: 'right',
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'notaxprice', title: '未税单价', width: 60, align: 'right',
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'notaxamount', title: '未税金额', width: 60, align: 'right',
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
                field: 'tax', title: '税额', width: 60, align: 'right',
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {field: 'auxnumdetail', title: '辅数量', width: 60, align: 'right'},
            {field: 'remark', title: '备注', width: 120}
        ]]
    });
    var page_url = "";
    var page_type = '${type}';
    if (page_type == "view" || page_type == "handle") {
        page_url = "account/receivable/salesInvoiceBillViewPage.do?id=${id}";
    } else if (page_type == "edit") {
        page_url = "account/receivable/salesInvoiceBillEditPage.do?id=${id}";
    } else if (page_type == "add") {

    }
    $(function () {
        $("#account-panel-salesInvoiceBillPage").panel({
            href: page_url,
            cache: false,
            maximized: true,
            border: false
        });
        //按钮
        $("#account-buttons-salesInvoiceBillPage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/account/receivable/salesInvoiceBillAddPage.do">
                {
                    type: 'button-add',
                    handler: function () {
                        var billtype = $("#account-salesInvoiceBill-billtype").val();
                        if ("1" == billtype) {
                            top.addOrUpdateTab('account/receivable/showSalesInvoiceBillAddPage.do', "申请开票");
                        } else {
                            top.addOrUpdateTab('account/receivable/showSalesAdvanceBillAddPage.do', "申请预开票");
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receivable/editSalesInvoiceBillSave.do">
                {
                    type: 'button-save',
                    handler: function () {
                        var type = $("#account-buttons-salesInvoiceBillPage").buttonWidget("getOperType");
                        if (type == "add") {
                            $("#account-form-salesInvoiceBillAdd").attr("action", "account/receivable/addSalesInvoiceBillSave.do");
                            $("#account-form-salesInvoiceBillAdd").submit();
                        } else if (type == "edit") {
                            $("#account-form-salesInvoiceBillAdd").attr("action", "account/receivable/editSalesInvoiceBillSave.do");
                            $("#account-form-salesInvoiceBillAdd").submit();
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receivable/editSalesInvoiceBillSaveAudit.do">
                {
                    type: 'button-saveaudit',
                    handler: function () {
                        var invoiceno = $("#salesInvoiceBill-invoiceno").val();
                        var invoicecode = $("#salesInvoiceBill-invoicecode").val();
                        if (invoiceno == "" || invoicecode == "") {
                            $.messager.alert("提示", "发票号或发票代码不能为空，请填写再审核!");
                            return false;
                        }
                        $.messager.confirm("提醒", "确定保存并审核该销售开票？", function (r) {
                            if (r) {
                                $("#account-saveaudit-salesInvoiceBillDetail").val("saveaudit");
                                $("#account-form-salesInvoiceBillAdd").attr("action", "account/receivable/editSalesInvoiceBillSave.do");
                                $("#account-form-salesInvoiceBillAdd").submit();
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receivable/deleteSalesInvoiceBill.do">
                {
                    type: 'button-delete',
                    handler: function () {
                        $.messager.confirm("提醒", "是否删除当前销售开票？", function (r) {
                            if (r) {
                                var billtype = $("#account-salesInvoiceBill-billtype").val();
                                var id = $("#account-hidden-billid").val();
                                if (id != "") {
                                    loading("提交中..");
                                    $.ajax({
                                        url: 'account/receivable/deleteSalesInvoiceBill.do?id=' + id + '&billtype=' + billtype,
                                        type: 'post',
                                        dataType: 'json',
                                        success: function (json) {
                                            if (json.flag) {
                                                var object = $("#account-buttons-salesInvoiceBillPage").buttonWidget("removeData", id);
                                                $.messager.alert("提醒", "删除成功");
                                                parent.closeNowTab();
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
                <security:authorize url="/account/receivable/auditSalesInvoiceBill.do">
                {
                    type: 'button-audit',
                    handler: function () {
                        var invoiceno = $("#salesInvoiceBill-invoiceno").val();
                        var invoicecode = $("#salesInvoiceBill-invoicecode").val();
                        if (invoiceno == "" || invoicecode == "") {
                            $.messager.alert("提示", "发票号或发票代码不能为空，请填写再审核!");
                            return false;
                        }
                        $.messager.confirm("提醒", "是否审核销售开票？", function (r) {
                            if (r) {
                                var id = $("#account-hidden-billid").val();
                                if (id != "") {
                                    loading("提交中..");
                                    $.ajax({
                                        url: 'account/receivable/auditSalesInvoiceBill.do?id=' + id,
                                        type: 'post',
                                        dataType: 'json',
                                        success: function (json) {
                                            loaded();
                                            if (json.flag) {
                                                $.messager.alert("提醒", "审核成功");
                                                $("#account-salesInvoiceBill-status").val("3");
                                                $("#account-buttons-salesInvoiceBillPage").buttonWidget("setDataID", {
                                                    id: id,
                                                    state: '3',
                                                    type: 'view'
                                                });
                                                $("#account-buttons-salesInvoiceBillPage").buttonWidget("disableButton", 'button-push');
                                                $("#account-buttons-salesInvoiceBillPage").buttonWidget("disableButton", 'button-rebate');
                                                $("#account-buttons-salesInvoiceBillPage").buttonWidget("enableButton", 'button-cancel');
                                                $("#account-buttons-salesInvoiceBillPage").buttonWidget("enableButton", "button-print");
                                                $("#account-buttons-salesInvoiceBillPage").buttonWidget("enableButton", "button-preview");
                                                if (json.hasblance == 1) {
                                                    $("#account-buttons-salesInvoiceBillPage").buttonWidget("enableButton", "print-blance-button");
                                                    $("#account-buttons-salesInvoiceBillPage").buttonWidget("enableButton", "printview-blance-button");
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
                <security:authorize url="/account/receivable/oppauditSalesInvoiceBill.do">
                {
                    type: 'button-oppaudit',
                    handler: function () {
                        var businessdate = $("#account-salesInvoiceBill-businessdate").val();
                        var flag = isDoneOppauditBillCaseAccounting(businessdate);
                        if (!flag) {
                            $.messager.alert("提醒", "业务日期不在会计区间内或未设置会计区间,不允许反审!");
                            return false;
                        }
                        $.messager.confirm("提醒", "是否反审销售开票？", function (r) {
                            if (r) {
                                var id = $("#account-hidden-billid").val();
                                if (id != "") {
                                    loading("提交中..");
                                    $.ajax({
                                        url: 'account/receivable/oppauditSalesInvoiceBill.do?id=' + id,
                                        type: 'post',
                                        dataType: 'json',
                                        success: function (json) {
                                            loaded();
                                            if (json.flag) {
                                                $.messager.alert("提醒", "反审成功");
                                                $("#account-panel-salesInvoiceBillPage").panel({
                                                    href: 'account/receivable/salesInvoiceBillEditPage.do?id=' + id,
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
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receivable/showSalesInvoiceBillRelationPage.do">
                {
                    type: 'button-relation',
                    button: [
                        {},
                        <security:authorize url="/account/receivable/showSalesInvoiceBillSourcePage.do">
                        {
                            type: 'relation-upper-view',
                            handler: function () {
                                var id = $("#account-hidden-billid").val();
                                $("#account-panel-relation-upper").dialog({
                                    href: "account/receivable/showSalesInvoiceBillSourceListReferPage.do?id=" + id,
                                    title: "销售开票来源单据",
                                    fit: true,
                                    closed: false,
                                    modal: true,
                                    cache: false,
                                    maximizable: true,
                                    resizable: true,
                                    onLoad: function () {
                                        $("#salesInvoiceBill-detail-close-detele").val("0");
                                    },
                                    onClose: function () {
                                        if ($("#salesInvoiceBill-detail-close-detele").val() == "1") {
                                            top.addOrUpdateTab('account/receivable/showSalesInvoiceBillEditPage.do?id=' + id, "销售开票查看");
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
                <security:authorize url="/account/receivable/salesInvoiceBillViewBackPage.do">
                {
                    type: 'button-back',
                    handler: function (data) {
                        $("#account-panel-salesInvoiceBillPage").panel({
                            href: 'account/receivable/salesInvoiceBillEditPage.do?id=' + data.id,
                            title: '',
                            cache: false,
                            maximized: true,
                            border: false
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receivable/salesInvoiceBillViewNextPage.do">
                {
                    type: 'button-next',
                    handler: function (data) {
                        $("#account-panel-salesInvoiceBillPage").panel({
                            href: 'account/receivable/salesInvoiceBillEditPage.do?id=' + data.id,
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
                <security:authorize url="/account/receivable/salesInvoiceBillIMExportMenuBtn.do">
                {
                    id: 'imexportMenuButton',
                    type: 'menu',
                    name: '导入导出',
                    iconCls: 'button-export',
                    button: [
                        <security:authorize url="/account/receivable/exportSalesInvoiceBillList.do">
                        {
                            id: 'ieportMenuButton-id-export',
                            name: '开票清单导出',
                            iconCls: 'button-export',
                            handler: function () {
                                $("#account-salesInvoiceBillPage-buttons-exportclick").Excel('export', {
                                    queryForm: "#report-export-form",
                                    type: 'exportUserdefined',
                                    name: '销售开票清单',
                                    url: 'account/receivable/exportSalesInvoiceBillList.do'
                                });
                                $("#account-salesInvoiceBillPage-buttons-exportclick").trigger("click");
                            }
                        },
                        {
                            type: 'menu-sep'
                        },
                        </security:authorize>
                        <security:authorize url="/account/receivable/exportSalesInvoiceBillDetail.do">
                        {
                            id: 'button-export-detail',
                            name: '销售开票明细导出',
                            iconCls: 'button-export',
                            handler: function () {
                                var id = $("#account-hidden-billid").val();
                                $("#account-dialog-salesInvoiceBillDetailExport").Excel('export', {
                                    type: 'exportUserdefined',
                                    name: '销售开票明细导出',
                                    url: 'account/receivable/exportSalesInvoiceBillDetailList.do?id=' + id
                                });
                                $("#account-dialog-salesInvoiceBillDetailExport").trigger("click");
                            }
                        },
                        {
                            type: 'menu-sep'
                        },
                        </security:authorize>
                        <security:authorize url="/account/receivable/salesInvoiceBillExportForHTKPBtn.do">
                        {
                            id: 'button-export-htkp',
                            name: '航天正数开票导出',
                            iconCls: 'button-export',
                            handler: function () {
                                var id = $("#account-hidden-billid").val();
                                if (id == "") {
                                    $.messager.alert("提醒", "抱歉，未能找到相当单据");
                                    return false;
                                }
                                var status = $("#account-salesInvoiceBill-status").val();
                                if (status != '3' && status != '4') {
                                    $.messager.alert("提醒", "编号" + id + "此开票清单保存状态不可打按航天正数开票格式导出");
                                    return false;
                                }
                                $("#account-salesInvoicePage-htkp-form-exportid").val(id);
                                $("#account-salesInvoicePage-htkp-form-title-tr").show();
                                $("#account-salesInvoicePage-htkp-form-jsgoodsversion-tr").hide();
                                $("#account-salesInvoicePage-htkp-form-jsgoodsversion").validatebox({required:false});
                                $("#account-salesInvoicePage-htkp-form-title").val("航天正数开票格式_" + id);
                                $("#account-salesInvoicePage-htkp-form").attr("action", 'account/receivable/exportSalesInvoiceBillForHTKP.do');
                                var exporttimes = $("#account-salesInvoiceBill-jxexporttimes").val() || 0;
                                var onClickHandleFunc = function () {
                                    if ($("#account-salesInvoicePage-htkp-form").form("validate")) {
                                        $("#account-salesInvoicePage-htkp-form").submit();
                                        $("#account-salesInvoicePage-htkpdialogdiv").dialog("close");
                                        return true;
                                    }
                                    return false;
                                }
                                if (exporttimes > 0) {
                                    $.messager.confirm("提醒", "该单据已经导出过" + exporttimes + "次<br/>是否再次导出？", function (r) {
                                        if (r) {
                                            showHTKPExportDailog("account-salesInvoicePage-htkpdialogdiv", onClickHandleFunc);
                                        }
                                    });
                                } else {
                                    showHTKPExportDailog("account-salesInvoicePage-htkpdialogdiv", onClickHandleFunc);
                                }

                            }
                        },
                        {
                            type: 'menu-sep'
                        },
                        </security:authorize>
                        <security:authorize url="/account/receivable/salesInvoiceBillExportForHTKPHZBtn.do">
                        {
                            id: 'button-export-htkphz',
                            name: '航天红字开票导出',
                            iconCls: 'button-export',
                            handler: function () {
                                var id = $("#account-hidden-billid").val();
                                if (id == "") {
                                    $.messager.alert("提醒", "抱歉，未能找到相当单据");
                                    return false;
                                }
                                var status = $("#account-salesInvoiceBill-status").val();
                                if (status != '3' && status != '4') {
                                    $.messager.alert("提醒", "编号" + id + "此开票清单保存状态不可打按航天红字开票格式导出");
                                    return false;
                                }
                                $("#account-salesInvoicePage-htkp-form-exportid").val(id);
                                $("#account-salesInvoicePage-htkp-form-title-tr").show();
                                $("#account-salesInvoicePage-htkp-form-jsgoodsversion-tr").hide();
                                $("#account-salesInvoicePage-htkp-form-jsgoodsversion").validatebox({required:false});
                                $("#account-salesInvoicePage-htkp-form-title").val("航天红字开票格式_" + id);
                                var exporttimes = $("#account-salesInvoiceBill-jxexporttimes").val() || 0;
                                $("#account-salesInvoicePage-htkp-form").attr("action", 'account/receivable/exportSalesInvoiceBillForHTKPHZ.do');
                                var onClickHandleFunc = function () {
                                    if ($("#account-salesInvoicePage-htkp-form").form("validate")) {
                                        $("#account-salesInvoicePage-htkp-form").submit();
                                        $("#account-salesInvoicePage-htkpdialogdiv").dialog("close");
                                        return true;
                                    }
                                    return false;
                                }
                                if (exporttimes > 0) {
                                    $.messager.confirm("提醒", "该单据已经导出过" + exporttimes + "次<br/>是否再次导出？", function (r) {
                                        if (r) {
                                            showHTKPExportDailog("account-salesInvoicePage-htkpdialogdiv", "航天TXT格式导出", onClickHandleFunc);
                                        }
                                    });
                                } else {
                                    showHTKPExportDailog("account-salesInvoicePage-htkpdialogdiv", "航天TXT格式导出", onClickHandleFunc);
                                }

                            }
                        },
                        {
                            type: 'menu-sep'
                        },
                        </security:authorize>
                        <security:authorize url="/account/receivable/salesInvoiceBillExportXMLForHTKPBtn.do">
                        {
                            id: 'button-export-xml-htkp',
                            name: '航天XML正数导出',
                            iconCls: 'button-export',
                            handler: function () {
                                var id = $("#account-hidden-billid").val();
                                if (id == "") {
                                    $.messager.alert("提醒", "抱歉，未能找到相当单据");
                                    return false;
                                }
                                var status = $("#account-salesInvoiceBill-status").val();
                                if (status != '3' && status != '4') {
                                    //$.messager.alert("提醒", "编号" + id + "此开票清单保存状态不可打按航天正数开票XML格式导出");
                                    //return false;
                                }
                                $("#account-salesInvoicePage-htkp-form-exportid").val(id);
                                $("#account-salesInvoicePage-htkp-form-title-tr").show();
                                $("#account-salesInvoicePage-htkp-form-jsgoodsversion-tr").show();
                                $("#account-salesInvoicePage-htkp-form-jsgoodsversion").validatebox({required:true});
                                var title=$("#account-salesInvoiceBill-invoicecustomername").val()|| "";
                                if(title!=""){
                                    title=title+"_";
                                }
                                title=title+id+"(航天开票XML正数导入格式)";
                                $("#account-salesInvoicePage-htkp-form-title").val(title);
                                var exporttimes = $("#account-salesInvoiceBill-jxexporttimes").val() || 0;
                                var onClickHandleFunc = function () {
                                    if ($("#account-salesInvoicePage-htkp-form").form("validate")) {
                                        var formdata = $("#account-salesInvoicePage-htkp-form").serializeJSON();
                                        formdata.oldFromData = "";
                                        delete formdata.oldFromData;
                                        loading("文件导出中..");
                                        $.ajax({
                                            url: 'account/receivable/exportSalesInvoiceBillXMLForHTKP.do',
                                            data:formdata,
                                            type: 'post',
                                            dataType: 'json',
                                            success: function (json) {
                                                loaded();
                                                if (json.flag==true) {
                                                    $("#account-salesInvoiceBill-jxexporttimes").val(parseInt(exporttimes)+1);

                                                    var msg="》》销售开票正数金税XML文件生成成功《《";
                                                    if(json.datafileid != null && json.datafileid != ''){
                                                        msg =msg + "<br/><span style=\"text-align: center\"><a href=\"common/download.do?id="+json.datafileid+"\" target=\"_blank\" style='font-weight: bold'>》点击下载文件《</a></span>";
                                                    }
                                                    if(json.amountmsg && json.amountmsg != ""){
                                                        msg=msg+"<br/>"+json.amountmsg;
                                                    }
                                                    easyuiMessagerAlert({
                                                        width:500,
                                                        title:"提醒",
                                                        msg:msg,
                                                        icon:'info',
                                                        fn:function () {
                                                        	$.messager.alert("提醒", "别忘记下载文件！！");
                                                        },
                                                        onOpen:function () {
                                                            $(".messager-button").css("visibility","hidden");
                                                            setTimeout(function () {
                                                                $(".messager-button").css("visibility","hidden");
                                                            },400);
                                                        }
                                                    });
                                                } else {
                                                    var msg="开票正数金税XML文件生成失败!";
                                                    var msgkind="1";
                                                    if(json.datafileid != null && json.datafileid != ''){
                                                        msgkind="2";
                                                        msg =msg + "<br/><span style=\"text-align: center\"><a href=\"common/download.do?id="+json.datafileid+"\" target=\"_blank\" style='font-weight: bold'>》点击下载文件《</a></span>";
                                                    }
                                                    if("2"==msgkind){
                                                        easyuiMessagerAlert({
                                                            width:500,
                                                            title:"提醒",
                                                            msg:msg,
                                                            icon:'info',
                                                            onOpen:function () {
                                                                $(".messager-button").css("visibility","hidden");
                                                                setTimeout(function () {
                                                                    $(".messager-button").css("visibility","hidden");
                                                                },400);
                                                            }
                                                        });
                                                    }else {
                                                        $.messager.alert("提醒", msg);
                                                    }
                                                }
                                            },
                                            error:function(){
                                                loaded();
                                                $.messager.alert("错误","抱歉，处理异常");
                                            }
                                        });

                                        $("#account-salesInvoicePage-htkpdialogdiv").dialog("close");
                                        return true;
                                    }
                                    return false;
                                }
                                if (exporttimes > 0) {
                                    $.messager.confirm("提醒", "该单据已经导出过" + exporttimes + "次<br/>是否再次导出？", function (r) {
                                        if (r) {
                                            showHTKPExportDailog("account-salesInvoicePage-htkpdialogdiv", "航天XML格式导出", onClickHandleFunc);
                                        }
                                    });
                                } else {
                                    showHTKPExportDailog("account-salesInvoicePage-htkpdialogdiv", "航天XML格式导出", onClickHandleFunc);
                                }

                            }
                        },
                        {
                            type: 'menu-sep'
                        },
                        </security:authorize>
                        <%--
                        <security:authorize url="/account/receivable/salesInvoiceBillExportXMLForHTKPHZBtn.do">
                        {
                            id:'button-export-xml-htkphz',
                            name:'航天红字XML导出',
                            iconCls:'button-export',
                            handler: function(){
                                var id = $("#account-hidden-billid").val();
                                if(id=="") {
                                    $.messager.alert("提醒", "抱歉，未能找到相当单据");
                                    return false;
                                }
                                var status=$("#account-salesInvoiceBill-status").val();
                                if(status!='3' && status!='4'){
                                    $.messager.alert("提醒","编号"+id+"此开票清单保存状态不可打按航天红字开票XML格式导出");
                                    return false;
                                }
                                $("#account-salesInvoicePage-htkp-form-exportid").val(id);
                                $("#account-salesInvoicePage-htkp-form-title").val("航天红字开票XML格式_"+id);
                                var exporttimes=$("#account-salesInvoiceBill-jxexporttimes").val() ||0;
                                $("#account-salesInvoicePage-htkp-form").attr("action",'account/receivable/exportSalesInvoiceBillXMLForHTKPHZ.do');
                                if(exporttimes>0) {
                                    $.messager.confirm("提醒", "该单据已经导出过"+exporttimes+"次<br/>是否再次导出？", function (r) {
                                        if (r) {
                                            showHTKPExportDailog("account-salesInvoicePage-htkpdialogdiv");
                                        }
                                    });
                                }else{
                                    showHTKPExportDailog("account-salesInvoicePage-htkpdialogdiv");
                                }

                            }
                        },
                        </security:authorize>
                        --%>
                        <security:authorize url="/account/receivable/exportSalesInvoiceBillExcelForHTKPBtn.do">
                        {
                            id: 'ieportMenuButton-export-htkp-excel',
                            name: '1航天EXCEL格式导出',
                            iconCls: 'button-export',
                            handler: function () {
                                var id = $("#account-hidden-billid").val();
                                if (id == "") {
                                    $.messager.alert("提醒", "抱歉，未能找到相当单据");
                                    return false;
                                }
                                var status = $("#account-salesInvoiceBill-status").val();

                                var title=$("#account-salesInvoiceBill-invoicecustomername").val()|| "";
                                if(title!=""){
                                    title=title+"_";
                                }
                                title=title+id+"(航天开票EXCEL导出格式)";

                                $("#salesInvoicePage-dialog-htkpexcelexportclick").Excel('export', {
                                    queryForm: "#report-export-form",
                                    type: 'exportUserdefined',
                                    name: title,
                                    url: 'account/receivable/exportSalesInvoiceBillExcelForHTKP.do'
                                });
                                $("#salesInvoicePage-dialog-htkpexcelexportclick").trigger("click");
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/account/receivable/importAndCreateSalesInvoiceBillExcelForHTKPBtn.do">
                        {
                            id: 'ieportMenuButton-import-htkp-exceltoxml',
                            name: '2导入生成航天XML格式',
                            iconCls: 'button-export',
                            handler: function () {

                                var id = $("#account-hidden-billid").val();
                                if (id == "") {
                                    $.messager.alert("提醒", "抱歉，未能找到相当单据");
                                    return false;
                                }
                                $("#account-salesInvoicePage-htkp-form-exportid").val(id);
                                $("#account-salesInvoicePage-htkp-form-title-tr").show();
                                $("#account-salesInvoicePage-htkp-form-jsgoodsversion-tr").show();
                                $("#account-salesInvoicePage-htkp-form-jsgoodsversion").validatebox({required:true});
                                var title=$("#account-salesInvoiceBill-invoicecustomername").val()|| "";
                                if(title!=""){
                                    title=title+"_";
                                }
                                title=title+id+"(EXCEL数据转航天开票XML导入格式)";
                                $("#account-salesInvoicePage-htkp-form-title").val(title);
                                var onClickHandleFunc = function () {
                                    if ($("#account-salesInvoicePage-htkp-form").form("validate")) {

                                        var formparm = $("#account-salesInvoicePage-htkp-form").serializeJSON();
                                        formparm.oldFromData = "";
                                        delete formparm.oldFromData;

                                        $("#salesInvoicePage-dialog-htkp-importtoxml-click").Excel('import', {
                                            type: 'importUserdefined',
                                            importparam: '<div style=\"text-align: center;margin-top: 5px; margin-bottom: 5px;line-height:30px\"><b style=\"color:#000;\">第二步、导入生成航天XML格式</b><br/>注意：单据号和金额要与当前单据一致</div>',//参数描述
                                            version: '1',//导入页面显示哪个版本1
                                            fieldParam: formparm,
                                            importPageRequestParam: {hideExportTip: 'true'},
                                            url: 'account/receivable/importAndCreateSalesInvoiceBillExcelForHTKP.do',
                                            onClose: function () { //导入成功后窗口关闭时操作，
                                            }
                                        });
                                        $("#salesInvoicePage-dialog-htkp-importtoxml-click").trigger("click");

                                        $("#account-salesInvoicePage-htkpdialogdiv").dialog("close");
                                        return true;
                                    }
                                    return false;
                                };
                                var exporttimes = $("#account-salesInvoiceBill-jxexporttimes").val() || 0;
                                if (exporttimes > 0) {
                                    $.messager.confirm("提醒", "该单据已经生成过" + exporttimes + "次<br/>是否再次生成金税导入文件？", function (r) {
                                        if (r) {
                                            showHTKPExportDailog("account-salesInvoicePage-htkpdialogdiv", "第一步、请选择相关人员", onClickHandleFunc);
                                        }
                                    });
                                } else {
                                    showHTKPExportDailog("account-salesInvoicePage-htkpdialogdiv", "第一步、请选择相关人员", onClickHandleFunc);
                                }

                            }
                        },
                        {
                            type: 'menu-sep'
                        },
                        </security:authorize>
                        <security:authorize url="/account/receivable/updateJSKPSysParamConfigBtn.do">
                        {
                            id:'button-id-config',
                            name:'金税人员参数配置',
                            iconCls:'button-edit',
                            handler:function(){
                                $('<div id="account-dialog-showJSKPSysParamConfigOper-content"></div>').appendTo("#account-dialog-showJSKPSysParamConfigOper");
                                $('#account-dialog-showJSKPSysParamConfigOper-content').dialog({
                                    title: '金税收款人和复核人系统参数配置',
                                    //fit:true,
                                    width:480,
                                    height:250,
                                    closed: true,
                                    cache: false,
                                    href: 'account/receivable/showJSKPSysParamConfigPage.do',
                                    maximizable:true,
                                    resizable:true,
                                    modal: true,
                                    onLoad:function(){
                                    },
                                    onClose:function(){
                                        $('#account-dialog-showJSKPSysParamConfigOper-content').dialog("destroy");
                                    }
                                });
                                $('#account-dialog-showJSKPSysParamConfigOper-content').dialog('open');
                            }
                        },
                        {
                            type: 'menu-sep'
                        },
                        </security:authorize>
                        {}
                    ]
                },
                </security:authorize>
                <security:authorize url="/account/receivable/showSalesInvoiceBillDetailPage.do">
                {
                    id: 'button-viewdetail',
                    name: '查看明细',
                    iconCls: 'button-view',
                    handler: function () {
                        var invoiceid = $("#account-hidden-billid").val();
                        if (invoiceid != "") {
                            $("#account-dialog-salesInvoiceBill-detail").dialog({
                                href: "account/receivable/showSalesInvoiceBillDetailPage.do?id=" + invoiceid,
                                title: "销售开票明细列表",
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
                <security:authorize url="/account/receivable/salesInvoiceBillPreview.do">
                {
                    id: 'mbutton-preview',
                    name: '打印预览',
                    iconCls: 'button-preview',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receivable/salesInvoiceBillPrint.do">
                {
                    id: 'mbutton-print',
                    name: '打印',
                    iconCls: 'button-print',
                    handler: function () {
                    }
                },
                </security:authorize>
                {}
            ],
            layoutid: 'account-layout-salesInvoiceBillPage',
            model: 'bill',
            type: 'view',
            tab: '开票抽单列表',
            taburl: '/account/receivable/showSalesInvoiceBillListPage.do',
            id: '${id}',
            datagrid: 'account-datagrid-salesInvoiceBillPage'
        });
    });

    //显示盘点单明细修改页面
    function beginEditDetail() {
        //验证表单
        var flag = $("#account-form-salesInvoiceBillAdd").form('validate');
        if (flag == false) {
            $.messager.alert("提醒", '请先选择出库仓库');
            $("#account-salesInvoiceBill-accountid").focus();
            return false;
        }
        var row = $("#account-datagrid-salesInvoiceBillAddPage").datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        $('<div id="account-dialog-salesInvoiceBillAddPage-content"></div>').appendTo('#account-dialog-salesInvoiceBillAddPage');
        $('#account-dialog-salesInvoiceBillAddPage-content').dialog({
            title: '销售开票明细修改',
            width: 680,
            height: 400,
            collapsible: false,
            minimizable: false,
            maximizable: true,
            resizable: true,
            closed: true,
            cache: false,
            href: 'account/receivable/showsalesInvoiceBillDetailEditPage.do',
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
                $('#account-dialog-salesInvoiceBillAddPage-content').dialog("destroy");
            }
        });
        $('#account-dialog-salesInvoiceBillAddPage-content').dialog("open");
    }
    //修改保存
    function editSaveDetail(goFlag) {
        var flag = $("#account-form-salesInvoiceBillDetailAddPage").form('validate');
        if (flag == false) {
            return false;
        }
        var form = $("#account-form-salesInvoiceBillDetailAddPage").serializeJSON();
        var row = $("#account-datagrid-salesInvoiceBillAddPage").datagrid('getSelected');
        var rowIndex = $("#account-datagrid-salesInvoiceBillAddPage").datagrid('getRowIndex', row);
        form.goodsInfo = row.goodsInfo;
        $("#account-datagrid-salesInvoiceBillAddPage").datagrid('updateRow', {index: rowIndex, row: form}); //将数据更新到列表中
        $("#account-dialog-salesInvoiceBillAddPage-content").dialog('destroy');
        countTotal();
    }
    //计算合计
    function countTotal() {
        var rows = $("#account-datagrid-salesInvoiceBillAddPage").datagrid('getRows');
        var unitnum = 0;
        var taxamount = 0;
        var notaxamount = 0;
        var tax = 0;
        for (var i = 0; i < rows.length; i++) {
            if ("1" != rows[i].isrebate) {
                unitnum = unitnum.add(rows[i].unitnum == undefined ? 0 : rows[i].unitnum);
            }
            taxamount = taxamount.add(rows[i].taxamount == undefined ? 0 : rows[i].taxamount);
            notaxamount = notaxamount.add(rows[i].notaxamount == undefined ? 0 : rows[i].notaxamount);
            tax = tax.add(rows[i].tax == undefined ? 0 : rows[i].tax);
        }
        var foot = [{goodsid: '当页合计', unitnum: unitnum, taxamount: taxamount, notaxamount: notaxamount, tax: tax}];
        if (null != SIBD_footerobject) {
            foot.push(SIBD_footerobject);
        }
        $("#account-datagrid-salesInvoiceBillAddPage").datagrid("reloadFooter", foot);
    }
    //返利
    function salesInvoiceBillRebate() {
        var invoiceid = $("#account-hidden-billid").val();
        var rebateamount = $("#account-invoice-input-amount").numberbox("getValue");
        var remark = $("#account-invoice-input-remark").val();
        var subject = $("#account-customerPushBanlance-subject").widget("getValue");
        if (rebateamount <= 0) {
            $.messager.alert("提醒", "返利金额必须大于0");
            return false;
        }
        $.messager.confirm("提醒", "是否对当前销售开票进行返利？", function (r) {
            if (r) {
                loading("提交中..");
                $.ajax({
                    url: 'account/receivable/addsalesInvoiceBillRebate.do',
                    type: 'post',
                    dataType: 'json',
                    data: {id: invoiceid, rebateamount: rebateamount, remark: remark, subject: subject},
                    success: function (json) {
                        loaded();
                        if (json.flag) {
                            $.messager.alert("提醒", "返利成功");
                            $("#account-panel-salesInvoiceBillPage").panel("refresh");
                        } else {
                            $.messager.alert("提醒", "销售开票已返利或者返利失败。");
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
        var thepage = tabsWindowURL("/account/receivable/showSalesInvoiceBillListPage.do");
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
        if (AgReportPrint.isShowPrintTempletManualSelect("sales_invoicebill")) {
            $("#account-invoice-dialog-printtemplet-tr").show();
            //初始化打印对话框
            AgReportPrint.createPrintTempletSelectOption('account-invoice-dialog-printtemplet-id', 'sales_invoicebill');
        }
        //打印
        AgReportPrint.init({
            id: "account-invoice-dialog-print",
            code: "sales_invoicebill",
            url_preview: "print/account/salesInvoiceBillPrintView.do",
            url_print: "print/account/salesInvoiceBillPrint.do",
            btnPreview: "mbutton-preview",
            btnPrint: "mbutton-print",
            getData: getData,
            onPrintSuccess: function (option) {
                updateDataGridPrintimes($("#account-hidden-billid").val());
            },
            printAfterHandler: function (option, printParam) {
                var printtimes = $("#account-salesInvoice-printtimes").val();
                $("#account-salesInvoice-printtimes").val(printtimes + 1);
            }
        });
        function getData(tableId, printParam) {
            var id = $("#account-hidden-billid").val();
            if (id == "") {
                $.messager.alert("警告", "找不到要打印的信息!");
                return false;
            }
            var status = $("#account-salesInvoiceBill-status").val();
            if (status != '3' && status != '4') {
                $.messager.alert("提醒", "编号：" + id + "此开票清单不可打印");
                return false;
            }
            var rowsData = $("#account-datagrid-salesInvoiceBillAddPage").datagrid("getRows");

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
            var printtimes = $("#account-salesInvoiceBill-printtimes").val() || 0;
            if (printtimes > 0)
                printParam.printIds = [id];
            return true;
        }
    });
</script>
<%--打印结束 --%>

<script type="text/javascript">
    $(document).ready(function () {
        //关联人员列表
        $("#account-salesInvoicePage-htkp-form-receipter").widget({
            width: '180',
            required: true,
            referwid: 'RT_T_BASE_PERSONNEL',
            onlyLeafCheck: true,
            singleSelect: true,
            onChecked: function () {
                setTimeout(function () {
                    $("#account-salesInvoicePage-htkp-form-receipter").validatebox('validate');
                }, 100);
            }
        });
        //关联人员列表
        $("#account-salesInvoicePage-htkp-form-checker").widget({
            width: '180',
            required: true,
            referwid: 'RT_T_BASE_PERSONNEL',
            onlyLeafCheck: true,
            singleSelect: true,
            onChecked: function () {
                setTimeout(function () {
                    $("#account-salesInvoicePage-htkp-form-checker").validatebox('validate');
                }, 50);
            }
        });
    });
    var showHTKPExportDailog = function (dialogid, title, onClickHandleFunc) {
        title = title || "航天开票接口";
        $('#' + dialogid).dialog({
            title: title,
            width: 350,
            height: 200,
            closed: false,
            cache: false,
            modal: true,
            buttons: [
                {
                    text: '确定',
                    iconCls: 'button-ok',
                    handler: function () {
                        try {
                            var flag = true;
                            if (onClickHandleFunc != null && typeof(onClickHandleFunc) == "function") {
                                flag = onClickHandleFunc() || true;
                            }
                            return flag;
                        } catch (e) {
                            return true;
                        }
                        return false;
                    }
                },

                {
                    text: '取消',
                    iconCls: 'button-cancel',
                    handler: function () {
                        $('#' + dialogid).dialog("close");
                        return false;
                    }
                }
            ]
        });
        $('#' + dialogid).dialog("open");
    }
</script>
</body>
</html>
