<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>销售退货通知单页面</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>
<body>
<input type="hidden" id="sales-backid-rejectBill" value="${id }"/>
<input type="hidden" id="sales-parentid-rejectBill"/><!-- 参照上游单据的编码 -->
<div id="sales-layout-rejectBill" class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="sales-buttons-rejectBill" style="height:26px;"></div>
    </div>
    <div data-options="region:'center',border:false">
        <div id="sales-panel-rejectBill"></div>
        <div id="rejectBill-goods-history-price"></div>
    </div>
</div>
<div class="easyui-dialog" id="sales-sourceQueryDialog-rejectBill" data-options="closed:true"></div>
<div class="easyui-dialog" id="sales-sourceDialog-rejectBill" data-options="closed:true"></div>
<div class="easyui-dialog" id="sales-dialog-rejectBill" closed="true"></div>
<div id="sales-dialog-split"></div>
<script type="text/javascript">

    var confirmFlag = false;    // 弹出是否继续新增窗口，是否确定
    var nopromptFlag = false;       // 弹出是否继续新增窗口，是否默认该操作

    function confirmStorageWidget() {
        $("#sales-hidden-storager").widget({
            referwid: 'RL_T_BASE_PERSONNEL_STORAGER',
            width: 160,
            col: 'name',
            singleSelect: true,
            initValue: '${storager}',
        });
    }
    var rejectBill_AjaxConn = function (Data, Action) {
        var MyAjax = $.ajax({
            type: 'post',
            cache: false,
            url: Action,
            data: Data,
            async: false,
            success: function (data) {
                loaded();
            }
        })
        return MyAjax.responseText;
    }

    //判断是否加锁
    function isLockData(id, tablename) {
        var flag = false;
        $.ajax({
            url: 'system/lock/isLockData.do',
            type: 'post',
            data: {id: id, tname: tablename},
            dataType: 'json',
            async: false,
            success: function (json) {
                flag = json.flag
            }
        });
        return flag;
    }

    //历史价格查看
    function showHistoryGoodsPrice() {
        var row = $("#sales-datagrid-rejectBillAddPage").datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        var businessdate = $("#sales-businessdate-rejectBillAddPage").val();
        var customerid = $("#sales-customer-showid-hidden-dispatchBillAddPage").val();
        var customername = $("#sales-customer-rejectBillAddPage").customerWidget('getText');
        var goodsid = row.goodsid;
        var goodsname = row.goodsInfo.name;
        $("#rejectBill-goods-history-price").dialog({
            title: '客户[' + customerid + '] 商品[' + goodsid + ']' + goodsname + ' 历史价格表',
            width: 600,
            height: 400,
            closed: false,
            modal: true,
            cache: false,
            maximizable: true,
            resizable: true,
            href: 'sales/showRejectBillHistoryGoodsPricePage.do',
            queryParams: {customerid: customerid, goodsid: goodsid, businessdate: businessdate}
        });
    }

    var receipt_url = "sales/rejectBillAddPage.do";
    var receipt_type = '${type}';
    if (receipt_type == "view" || receipt_type == "show" || receipt_type == "handle") {
        receipt_url = "sales/rejectBillViewPage.do?id=${id}";
    }
    if (receipt_type == "edit") {
        receipt_url = "sales/rejectBillEditPage.do?id=${id}";
    }
    var wareListJson = $("#sales-datagrid-rejectBillAddPage").createGridColumnLoad({ //以下为商品明细datagrid字段
        name: 't_sales_rejectbill_detail',
        frozenCol: [[]],
        commonCol: [[{field: 'goodsid', title: '商品编码', width: 70, align: ' left'},
            {
                field: 'goodsname', title: '商品名称', width: 220, align: 'left', aliascol: 'goodsid',
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        if (rowData.deliverytype == '1') {
                            return '<font color="blue">&nbsp;赠 </font>' + rowData.goodsInfo.name + '';
                        } else {
                            return '' + rowData.goodsInfo.name + '';
                        }
                    } else {
                        return "";
                    }
                }
            },
            {
                field: 'barcode', title: '条形码', width: 90, align: 'left', aliascol: 'goodsid',
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        return rowData.goodsInfo.barcode;
                    } else {
                        return "";
                    }
                }
            },
            {
                field: 'itemno', title: '商品货位', width: 60, aliascol: 'goodsid',
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        return rowData.goodsInfo.itemno;
                    } else {
                        return "";
                    }
                }
            },
            {
                field: 'brandName', title: '商品品牌', width: 80, align: 'left', aliascol: 'goodsid', hidden: true,
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        return rowData.goodsInfo.brandName;
                    } else {
                        return "";
                    }
                }
            },
            {
                field: 'boxnum', title: '箱装量', aliascol: 'goodsid', width: 45, align: 'right',
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        return formatterBigNumNoLen(rowData.goodsInfo.boxnum);
                    } else {
                        return "";
                    }
                }
            },
            {field: 'unitname', title: '单位', width: 35, align: 'left'},
            {
                field: 'unitnum', title: '数量', width: 60, align: 'right',
                formatter: function (value, row, index) {
                    return formatterBigNumNoLen(value);
                }
            },
            {
                field: 'taxprice', title: '单价', width: 60, align: 'right',
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'boxprice', title: '箱价', aliascol: 'goodsid', width: 60, align: 'right',
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
                field: 'taxtype', title: '税种', width: 70, align: 'left', hidden: true,
                formatter: function (value, row, index) {
                    return row.taxtypename;
                }
            },
            {
                field: 'notaxprice', title: '未税单价', width: 80, align: 'right', hidden: true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'notaxamount', title: '未税金额', width: 80, align: 'right', hidden: true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'tax', title: '税额', width: 80, align: 'right', hidden: true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {field: 'auxnumdetail', title: '辅数量', width: 60, align: 'right'},
            {
                field: 'storageid', title: '所属仓库', width: 80, align: 'left',
                formatter: function (value, row, index) {
                    return row.storagename;
                }
            },
            {
                field: 'storagelocationid', title: '所属库位', width: 80, align: 'left', hidden: true,
                formatter: function (value, row, index) {
                    return row.storagelocationname;
                }
            },
            {field: 'batchno', title: '批次号', width: 80, align: 'left'},
            {field: 'produceddate', title: '生产日期', width: 80, align: 'left'},
            {
                field: 'rejectcategory', title: '退货属性', width: 80, align: 'left', hidden: true,
                formatter: function (value, row, index) {
                    return getSysCodeName('rejectCategory', value);
                }
            },
            {field: 'remark', title: '备注', width: 100, align: 'left'}
        ]]
    });
    $(function () {
        $("#sales-panel-rejectBill").panel({
            href: receipt_url,
            cache: false,
            maximized: true,
            border: false,
            onLoad: function () {
                if (receipt_type != "view") {
                    $("#sales-customer-rejectBillAddPage").focus();
                }
            }
        });
        //按钮
        $("#sales-buttons-rejectBill").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/sales/rejectBillAddPage.do">
                {
                    type: 'button-add',
                    handler: function () {
                        refreshPanel('sales/rejectBillAddPage.do');
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/rejectBillHold.do">
                {
                    type: 'button-hold',
                    handler: function () {
                        $.messager.confirm("提醒", "确定暂存退货通知单？", function (r) {
                            if (r) {
                                $("#sales-addType-rejectBillAddPage").val("temp");
                                var json = $("#sales-datagrid-rejectBillAddPage").datagrid('getRows');
                                $("#sales-goodsJson-rejectBillAddPage").val(JSON.stringify(json));
                                rejectBill_tempSave_form_submit();
                                $("#sales-form-rejectBillAddPage").submit();
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/rejectBillSave.do">
                {
                    type: 'button-save',
                    handler: function () {
                        var id = $("#sales-id-rejectBillAddPage").val();
                        if (id != "" && undefined != id) {
                            var flag = isLockData(id, "t_sales_rejectbill");
                            if (flag) {
                                $.messager.alert("警告", "该数据正在被其他人操作，暂不能操作！");
                                return false;
                            }
                            // //false，不允许操作，true,允许操作
                            // var ret = rejectBill_AjaxConn({id: id}, 'sales/getRejectBillOperation.do');
                            // var retjson = $.parseJSON(ret);
                            // if (!retjson.flag) {
                            //     $.messager.alert("警告", "该数据已处于审核通过状态，不能操作！");
                            //     refreshPanel("sales/rejectBillEditPage.do?id=" + id + "");
                            //     return false;
                            // }
                        }
                        $("#sales-addType-rejectBillAddPage").val("real");
                        var json = $("#sales-datagrid-rejectBillAddPage").datagrid('getRows');
                        $("#sales-goodsJson-rejectBillAddPage").val(JSON.stringify(json));
                        rejectBill_realSave_form_submit();
                        $("#sales-form-rejectBillAddPage").submit();
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/rejectBillSaveAudit.do">
                {
                    type: 'button-saveaudit',
                    handler: function () {
                        var fn = function (r) {
                            if (r) {
                                var id = $("#sales-id-rejectBillAddPage").val();
                                if (id != "" && undefined != id) {
                                    var flag = isLockData(id, "t_sales_rejectbill");
                                    if (flag) {
                                        $.messager.alert("警告", "该数据正在被其他人操作，暂不能操作！");
                                        return false;
                                    }
                                    // //false，不允许操作，true,允许操作
                                    // var ret = rejectBill_AjaxConn({id: id}, 'sales/getRejectBillOperation.do');
                                    // var retjson = $.parseJSON(ret);
                                    // if (!retjson.flag) {
                                    //     $.messager.alert("警告", "该数据已处于审核通过状态，不能操作！");
                                    //     refreshPanel("sales/rejectBillEditPage.do?id=" + id + "");
                                    //     return false;
                                    // }
                                }
                                var storager = $("#sales-hidden-storager").widget("getValue");
                                $("#sales-storager-rejectBillAddPage").val(storager);
                                $("#sales-addType-rejectBillAddPage").val("real");
                                $("#sales-saveaudit-rejectBillAddPage").val("saveaudit");
                                var json = $("#sales-datagrid-rejectBillAddPage").datagrid('getRows');
                                $("#sales-goodsJson-rejectBillAddPage").val(JSON.stringify(json));
                                rejectBill_realSave_form_submit();
                                $("#sales-form-rejectBillAddPage").submit();
                            }
                        };
                        $.messager.confirm({
                            title: '保存并审核',
                            onOpen: function () {
                                confirmStorageWidget()
                            },
                            msg: "确定保存并审核退货通知单？</br><span style=\"float: left;\">选择收货人:</span><input  id=\"sales-hidden-storager\" name=\"storager\"/>",
                            fn: fn
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/rejectBillGiveup.do">
                {
                    type: 'button-giveup',
                    handler: function () {
                        var type = $("#sales-buttons-rejectBill").buttonWidget("getOperType");
                        if (type == "add") {
                            var currTitle = top.$('#tt').tabs('getSelected').panel('options').title;
                            top.closeTab(currTitle);
                        }
                        else if (type == "edit") {
                            var id = $("#sales-backid-rejectBill").val();
                            if (id == "") {
                                return false;
                            }
                            refreshPanel('sales/rejectBillViewPage.do?id=' + id);
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/rejectBillDelete.do">
                {
                    type: 'button-delete',
                    handler: function () {
                        var id = $("#sales-backid-rejectBill").val();
                        if (id == '') {
                            return false;
                        }
                        $.messager.confirm("提醒", "确定删除该退货通知单？", function (r) {
                            if (r) {
                                loading("删除中..");
                                $.ajax({
                                    url: 'sales/deleteRejectBill.do',
                                    dataType: 'json',
                                    type: 'post',
                                    data: 'id=' + id,
                                    success: function (json) {
                                        loaded();
                                        if (json.delFlag == true) {
                                            $.messager.alert("提醒", "该信息已被其他信息引用，无法删除");
                                            return false;
                                        }
                                        if (json.flag == true) {
                                            $.messager.alert("提醒", "删除成功");
                                            var data = $("#sales-buttons-rejectBill").buttonWidget("removeData", '');
                                            if (data != null) {
                                                $("#sales-backid-rejectBill").val(data.id);
                                                refreshPanel('sales/rejectBillEditPage.do?id=' + data.id);
                                            }
                                            else {
                                                parent.closeNowTab();
                                            }
                                        }
                                        else {
                                            $.messager.alert("提醒", "删除失败。直退的销售退货通知单部分明细已被销售回单关联。不能删除");
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
                <security:authorize url="/sales/rejectBillAudit.do">
                {
                    type: 'button-audit',
                    handler: function () {
                        var id = $("#sales-backid-rejectBill").val();
                        if (id == '') {
                            return false;
                        }
                        var ret = rejectBill_AjaxConn({id: id}, 'sales/checkBuyGoodsRejectBill.do');
                        var retjson = $.parseJSON(ret);
                        if (retjson.unbuymsg != "") {
                            $.messager.confirm("提醒", retjson.unbuymsg, function (r) {
                                if (r) {
                                    isAuditRejectBill(id);
                                }
                            });
                        } else {
                            isAuditRejectBill(id);
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/rejectBillOppaudit.do">
                {
                    type: 'button-oppaudit',
                    handler: function () {
                        var id = $("#sales-backid-rejectBill").val();
                        if (id == '') {
                            return false;
                        }
                        var businessdate = $("#sales-businessdate-rejectBillAddPage").val();
                        var flag = isDoneOppauditBillCaseAccounting(businessdate);
                        if (!flag) {
                            $.messager.alert("提醒", "业务日期不在会计区间内或未设置会计区间,不允许反审!");
                            return false;
                        }
                        $.messager.confirm("提醒", "确定反审该退货通知单信息？", function (r) {
                            if (r) {
                                loading("反审中..");
                                $.ajax({
                                    url: 'sales/auditRejectBill.do',
                                    dataType: 'json',
                                    type: 'post',
                                    data: 'id=' + id + '&type=2',
                                    success: function (json) {
                                        loaded();
                                        if (json.billArg == false) {
                                            $.messager.alert("提醒", "反审失败，下游单据已生成并审核，无法反审");
                                        }
                                        else {
                                            if (json.flag == true) {
                                                $.messager.alert("提醒", "反审成功");
                                                refreshPanel('sales/rejectBillEditPage.do?id=' + id);
                                            }
                                            else {
                                                if (json.invoiceflag == false) {
                                                    $.messager.alert("提醒", "已验收或者生成销售发票或销售开票，不能反审！");
                                                } else {
                                                    $.messager.alert("提醒", "反审失败。" + json.msg);
                                                }
                                            }
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
                <security:authorize url="/sales/rejectBillWorkflow.do">
                {
                    type: 'button-workflow',
                    button: [
                        {
                            type: 'workflow-submit',
                            handler: function () {
                                $.messager.confirm("提醒", "是否提交该销售退货通知单信息到工作流?", function (r) {
                                    if (r) {
                                        var id = $("#sales-backid-rejectBill").val();
                                        if (id == "") {
                                            $.messager.alert("警告", "没有需要提交工作流的信息!");
                                            return false;
                                        }
                                        loading("提交中..");
                                        $.ajax({
                                            url: 'sales/submitRejectBillProcess.do',
                                            dataType: 'json',
                                            type: 'post',
                                            data: 'id=' + id,
                                            success: function (json) {
                                                loaded();
                                                if (json.flag == true) {
                                                    $.messager.alert("提醒", "提交成功!");
                                                    $("#sales-panel-rejectBill").panel("refresh");
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
                                if (receipt_type == "handle") {
                                    $("#sales-dialog-rejectBill").dialog({
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
                                var id = $("#sales-backid-rejectBill").val();
                                if (id == "") {
                                    return false;
                                }
                                $("#sales-dialog-rejectBill").dialog({
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
                                var id = $("#sales-backid-rejectBill").val();
                                if (id == "") {
                                    return false;
                                }
                                $("#sales-dialog-rejectBill").dialog({
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
                <security:authorize url="/sales/rejectBillBack.do">
                {
                    type: 'button-back',
                    handler: function (data) {
                        $("#sales-backid-rejectBill").val(data.id);
                        refreshPanel('sales/rejectBillEditPage.do?id=' + data.id);
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/rejectBillNext.do">
                {
                    type: 'button-next',
                    handler: function (data) {
                        $("#sales-backid-rejectBill").val(data.id);
                        refreshPanel('sales/rejectBillEditPage.do?id=' + data.id);
                    }
                },
                </security:authorize>
                {}
            ],
            buttons: [
                {},
                <security:authorize url="/sales/rejectBillPagePrintView.do">
                {
                    id: 'button-printview-sales',
                    name: '打印预览',
                    iconCls: 'button-preview',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/rejectBillPagePrint.do">
                {
                    id: 'button-print-sales',
                    name: '打印',
                    iconCls: 'button-print',
                    handler: function () {
                    }
                },
                </security:authorize>
                {}
            ],
            layoutid: 'sales-layout-rejectBill',
            model: 'bill',
            type: 'view',
            tab: '销售退货通知单列表',
            taburl: '/sales/rejectBillListPage.do',
            id: '${id}',
            datagrid: 'sales-datagrid-rejectBillListPage'
        });
    });
    function refreshPanel(url) { //更新panel
        $("#sales-panel-rejectBill").panel('refresh', url);
    }
    function rejectBill_tempSave_form_submit() { //暂存表单方法
        $("#sales-form-rejectBillAddPage").form({
            onSubmit: function () {
                loading("提交中..");
            },
            success: function (data) {
                loaded();
                var json = $.parseJSON(data);
                if (json.lock == true) {
                    $.messager.alert("提醒", "其他用户正在修改该数据，无法修改");
                    return false;
                }
                if (json.flag == true) {
                    $.messager.alert("提醒", "暂存成功");
                    $("#sales-backid-rejectBill").val(json.backid);
                    if (json.type == "add") {
                        $("#sales-buttons-rejectBill").buttonWidget("addNewDataId", json.backid);
                    }
                    $("#sales-panel-rejectBill").panel('refresh', 'sales/rejectBillViewPage.do?id=' + json.backid);
                }
                else {
                    $.messager.alert("提醒", "暂存失败");
                }
            }
        });
    }
    function rejectBill_realSave_form_submit() { //保存表单方法
        $("#sales-form-rejectBillAddPage").form({
            onSubmit: function () {
                var flag = $(this).form('validate');
                if (flag == false) {
                    return false;
                }
                loading("提交中..");
            },
            success: function (data) {
                try{
                    var json = $.parseJSON(data);
                    if (json.lock == true) {
                        $.messager.alert("提醒", "其他用户正在修改该数据，无法修改");
                        return false;
                    }
                    if (json.flag == true) {
                        if (json.auditflag) {
                            $.messager.alert("提醒", "保存审核成功");
                            $("#sales-backid-rejectBill").val(json.backid);
                            if (json.type == "add") {
                                $("#sales-buttons-rejectBill").buttonWidget("addNewDataId", json.backid);
                            }
                            $("#sales-panel-rejectBill").panel('refresh', 'sales/rejectBillEditPage.do?id=' + json.backid);

                        } else {
    //                        $.messager.alert("提醒", "保存成功");
    //                        $("#sales-backid-rejectBill").val(json.backid);
    //                        if (json.type == "add") {
    //                            $("#sales-buttons-rejectBill").buttonWidget("addNewDataId", json.backid);
    //                            $("#sales-panel-rejectBill").panel('refresh', 'sales/rejectBillEditPage.do?id=' + json.backid);
    //                        }
                            $.messager.alert("提醒", "保存成功");
                            $("#sales-backid-rejectBill").val(json.backid);
                            if (json.type == "add") {

                                var rejectbillNoPromptCookie = document.cookie.match(/rejectbillNoPrompt=(true|false)/g);
                                var rejectbillNoPrompt = rejectbillNoPromptCookie != null && rejectbillNoPromptCookie.length == 1 && rejectbillNoPromptCookie[0] == 'rejectbillNoPrompt=true';
                                var rejectbillAddFlagCookie = document.cookie.match(/rejectbillAddFlag=(true|false)/g);
                                var rejectbillAddFlag = rejectbillAddFlagCookie != null && rejectbillAddFlagCookie.length == 1 && rejectbillAddFlagCookie[0] == 'rejectbillAddFlag=true';

                                if(!rejectbillNoPrompt) {
                                    $.messager.confirm({
                                        title: '确认？',
                                        onOpen: function () {
                                        },
                                        onClose: function () {
                                            // 注意：setTimeout请勿删除
                                            setTimeout(function () {
                                                document.cookie = "rejectbillNoPrompt=" + nopromptFlag;

                                                if(confirmFlag) {
                                                    document.cookie = "rejectbillAddFlag=" + confirmFlag;
                                                    refreshPanel('sales/rejectBill.do');
                                                    return true;
                                                } else {
                                                    $("#sales-buttons-rejectBill").buttonWidget("addNewDataId", json.backid);
                                                    $("#sales-panel-rejectBill").panel('refresh', 'sales/rejectBillEditPage.do?id=' + json.backid);
                                                }

                                            }, 0);
                                        },
                                        msg: '保存成功。<br/>是否继续新增退货通知单？</br><label><input type="checkbox" onclick="javascript:nopromptFlag=!nopromptFlag;"/>勾选表示默认选择当前操作。</label>',
                                        fn: function (data) {
                                            confirmFlag = data;
                                        }
                                    });
                                } else {
                                    if(rejectbillAddFlag) {
                                        refreshPanel('sales/rejectBill.do');
                                    } else {
                                        $("#sales-buttons-rejectBill").buttonWidget("addNewDataId", json.backid);
                                        $("#sales-panel-rejectBill").panel('refresh', 'sales/rejectBillEditPage.do?id=' + json.backid);
                                    }
                                }
                            }else{
                                $("#sales-panel-rejectBill").panel('refresh', 'sales/rejectBillEditPage.do?id=' + json.backid);
                            }
                        }
                    }else{
                         $.messager.alert("提醒", "保存失败。单据可能已审核或者关闭。");
                    }
                }catch(error){
                    $.messager.alert("错误", "保存出错。请刷新后再试。");
                }finally {
                    loaded();
                }
            }
        });
    }
    function countTotal() { //计算合计
        var rows = $("#sales-datagrid-rejectBillAddPage").datagrid('getRows');
        var leng = rows.length;
        var unitnum = 0;
        var taxamount = 0;
        var notaxamount = 0;
        var tax = 0;
        for (var i = 0; i < leng; i++) {
            unitnum += Number(rows[i].unitnum == undefined ? 0 : rows[i].unitnum);
            taxamount += Number(rows[i].taxamount == undefined ? 0 : rows[i].taxamount);
            notaxamount += Number(rows[i].notaxamount == undefined ? 0 : rows[i].notaxamount);
            tax += Number(rows[i].tax == undefined ? 0 : rows[i].tax);
        }
        $("#sales-datagrid-rejectBillAddPage").datagrid('reloadFooter', [{
            goodsid: '合计',
            unitnum: unitnum,
            taxamount: taxamount,
            notaxamount: notaxamount,
            tax: tax
        }]);
    }
    function customerCheck() { //添加商品明细前确定客户已选
        var customer = $("#sales-customer-rejectBillAddPage-hidden").val();
        if (customer == '') {
            $.messager.alert("提醒", "请先选择客户再进行添加商品信息");
            $("#sales-customer-rejectBillAddPage").focus();
            return false;
        }
        else {
            return customer;
        }
    }
    //开始添加商品信息
    function beginAddDetail() {
        var customer = $("#sales-customer-rejectBillAddPage-hidden").val();
        if (customer == '') {
            $.messager.alert("提醒", "请先选择客户再进行添加商品信息");
            $("#sales-customer-rejectBillAddPage").focus();
            return false;
        }
        $('<div id="sales-dialog-rejectBillAddPage-content"></div>').appendTo('#sales-dialog-rejectBillAddPage');
        $("#sales-dialog-rejectBillAddPage-content").dialog({ //弹出新添加窗口
            title: '商品信息(添加)',
            maximizable: true,
            width: 600,
            height: 450,
            closed: true,
            modal: true,
            cache: false,
            resizable: true,
            href: 'sales/rejectBillDetailAddPage.do?cid=' + customer,
            onClose: function () {
                $('#sales-dialog-rejectBillAddPage-content').dialog("destroy");
            },
            onLoad: function () {
                $("#sales-goodsId-billDetailAddPage").focus();
            }
        });
        $("#sales-dialog-rejectBillAddPage-content").dialog("open");
    }
    function addSaveDetail(go) { //添加新数据确定后操作，
        var flag = $("#sales-form-billDetailAddPage").form('validate');
        if (flag == false) {
            return false;
        }
        var form = $("#sales-form-billDetailAddPage").serializeJSON();
        var rejectcategory = form.rejectcategory;
        var goodsJson = $("#sales-goodsId-billDetailAddPage").widget('getObject');
        form.goodsInfo = goodsJson;
        $.ajax({
            url: 'basefiles/getItemByGoodsAndStorage.do',
            data:{
                goodsid:form.goodsid,
                storageid:$("#sales-storage-rejectBillAddPage").widget('getValue')
            },
            async: false,
            type: 'post',
            dataType: 'json',
            success: function (json) {
                form.goodsInfo.itemno=json.itemno;
            },
        });
        var customer = $("#sales-customer-rejectBillAddPage-hidden").val();
        var rowIndex = 0;
        var rows = $wareList.datagrid('getRows');
        var updateFlag = false;
        var isdeliverytype = false;
        if (form.deliverytype == '1') {
            isdeliverytype = true;
        }
        for (var i = 0; i < rows.length; i++) {
            var rowJson = rows[i];
            if (!isdeliverytype && "${isRepeatRejectBillDetailGoodsid}" == "0") {
                if (rowJson.goodsid == goodsJson.id) {
                    rowIndex = i;
                    updateFlag = true;
                    break;
                }
            }
            if (rowJson.goodsid == undefined) {
                rowIndex = i;
                break;
            }
        }
        if (rowIndex == rows.length - 1) {
            $wareList.datagrid('appendRow', {}); //如果是最后一行则添加一新行
        }
        if (updateFlag) {
            $.messager.alert("提醒", "此商品已经添加！");
        } else {
            $wareList.datagrid('updateRow', {index: rowIndex, row: form}); //将数据更新到列表中
        }
        if (go) { //go为true确定并继续添加一条
            $("#sales-form-billDetailAddPage").form("clear");
            $("#sales-deliverytype-billDetailAddPage").val("0");
            $("#sales-rejectcategory-billDetailAddPage").val(rejectcategory);
        }
        else { //否则直接关闭
            $("#sales-dialog-rejectBillAddPage").dialog('close', true)
        }
        countTotal(); //添加一条商品明细计算一次合计
    }
    function beginEditDetail(rowData) { //开始修改商品信息
        var customer = $("#sales-customer-rejectBillAddPage-hidden").val();
        if (customer == '') {
            $.messager.alert("提醒", "请先选择客户再进行添加商品信息");
            $("#sales-customer-rejectBillAddPage").focus();
            return false;
        }
        var row = $wareList.datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        var url = '';
        if (row.goodsid == undefined) {
            beginAddDetail();
        }
        else {
            url = 'sales/rejectBillDetailEditPage.do?goodsid=' + row.goodsid; //如果是修改页面，数据直接来源于datagrid中的json数据。
            $('<div id="sales-dialog-rejectBillAddPage-content"></div>').appendTo('#sales-dialog-rejectBillAddPage');
            $("#sales-dialog-rejectBillAddPage-content").dialog({ //弹出修改窗口
                title: '商品信息(修改)',
                maximizable: true,
                width: 600,
                height: 450,
                closed: true,
                modal: true,
                cache: false,
                resizable: true,
                href: url,
                onClose: function () {
                    $('#sales-dialog-rejectBillAddPage-content').dialog("destroy");
                },
                onLoad: function () {
                    $("#sales-form-billDetailEditPage").form('load', row);
                    $("#sales-goodsname-billDetailAddPage").val(row.goodsInfo.name);
                    $("#sales-model-billDetailAddPage").val(row.goodsInfo.model);
                    $("#sales-brandname-billDetailAddPage").val(row.goodsInfo.brandName);
                    $("#sales-barcode-billDetailAddPage").val(row.goodsInfo.barcode);
                    $("#sales-boxnum-billDetailAddPage").val(formatterBigNumNoLen(row.goodsInfo.boxnum));
                    $("#back-taxprice").val(row.taxprice);
                    $("#sales-auxunitname-billDetailAddPage").html(row.auxunitname);
                    $("#sales-unitname-billDetailAddPage").html(row.unitname);

                    var isbatch = $("#sales-isbatch-billDetailAddPage").val();
                    if (isbatch == "1") {
                        $("#sales-produceddate-billDetailAddPage").removeClass("WdateRead");
                        $("#sales-produceddate-billDetailAddPage").addClass("Wdate");
                        $("#sales-produceddate-billDetailAddPage").removeAttr("readonly");
                        $("#sales-deadline-billDetailAddPage").removeClass("WdateRead");
                        $("#sales-deadline-billDetailAddPage").addClass("Wdate");
                        $("#sales-deadline-billDetailAddPage").removeAttr("readonly");

                        $("#sales-storage-billDetailAddPage").widget("readonly", true);
                        var param = null;
                        if (storageid != null && storageid != "") {
                            param = [{field: 'goodsid', op: 'equal', value: row.goodsid},
                                {field: 'storageid', op: 'equal', value: storageid}];
                        } else {
                            param = [{field: 'goodsid', op: 'equal', value: row.goodsid}];
                        }
                        $("#sales-batchno-billDetailAddPage").widget({
                            referwid: 'RL_T_STORAGE_BATCH_LIST',
                            param: param,
                            width: 150,
                            singleSelect: true,
                            onSelect: function (obj) {
                                $("#sales-summarybatchid-billDetailAddPage").val(obj.id);
                                $("#sales-storageid-billDetailAddPage").val(obj.storageid);
                                $("#sales-storagename-billDetailAddPage").val(obj.storagename);
                                $("#sales-storagelocationname-billDetailAddPage").val(obj.storagelocationname);
                                $("#sales-storagelocationid-billDetailAddPage").val(obj.storagelocationid);
                                $("#sales-produceddate-billDetailAddPage").val(obj.produceddate);
                                $("#sales-deadline-billDetailAddPage").val(obj.deadline);
                                $("#sales-loading-billDetailAddPage").removeClass("img-loading").html("商品编码：<font color='green'>" + obj.goodsid + "</font>&nbsp;可用量：<font color='green'>" + obj.usablenum + obj.unitname + "</font>");
                                if (obj.usablenum != null && obj.usablenum != '') {
                                    $("input[name=usablenum]").val(obj.usablenum);
                                    $("input[name=total]").val(obj.usablenum);
                                } else {
                                    $("input[name=usablenum]").val(0);
                                }
                            },
                            onClear: function () {
//                                    $("#sales-produceddate-billDetailAddPage").removeClass("Wdate");
//                                    $("#sales-produceddate-billDetailAddPage").addClass("WdateRead");
//                                    $("#sales-produceddate-billDetailAddPage").attr("readonly","readonly");
//                                    $("#sales-deadline-billDetailAddPage").removeClass("Wdate");
//                                    $("#sales-deadline-billDetailAddPage").addClass("WdateRead");
//                                    $("#sales-deadline-billDetailAddPage").attr("readonly");

                                $("#sales-summarybatchid-billDetailAddPage").val("");
                                $("#sales-storageid-billDetailAddPage").val("");
                                $("#sales-storagename-billDetailAddPage").val("");
                                $("#sales-storagelocationname-billDetailAddPage").val("");
                                $("#sales-storagelocationid-billDetailAddPage").val("");
                                $("#sales-produceddate-billDetailAddPage").val("");
                                $("#sales-deadline-billDetailAddPage").val("");
                            }
                        });
                    } else {
                        $("#sales-produceddate-billDetailAddPage").removeClass("Wdate");
                        $("#sales-produceddate-billDetailAddPage").addClass("WdateRead");
                        $("#sales-produceddate-billDetailAddPage").attr("readonly", "readonly");
                        $("#sales-deadline-billDetailAddPage").removeClass("Wdate");
                        $("#sales-deadline-billDetailAddPage").addClass("WdateRead");
                        $("#sales-deadline-billDetailAddPage").attr("readonly");

                        $("#sales-batchno-billDetailAddPage").widget("disable");
                    }

                    $("#sales-unitnum-billDetailAddPage").focus();
                    $("#sales-unitnum-billDetailAddPage").select();

                    formaterNumSubZeroAndDot();

                    $("#sales-form-billDetailEditPage").form('validate');
                }
            });
            $("#sales-dialog-rejectBillAddPage-content").dialog("open");
        }
    }
    function editSaveDetail(go) { //修改数据确定后操作，
        var flag = $("#sales-form-billDetailEditPage").form('validate');
        if (flag == false) {
            return false;
        }
        var row = $wareList.datagrid('getSelected');
        var rowIndex = $wareList.datagrid('getRowIndex', row);
        var form = $("#sales-form-billDetailEditPage").serializeJSON();
        $wareList.datagrid('updateRow', {index: rowIndex, row: form}); //将数据更新到列表中
        $("#sales-dialog-rejectBillAddPage-content").dialog('close', true)
        countTotal();
    }
    function removeDetail() {
        var row = $wareList.datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        $.messager.confirm("提醒", "确定删除该商品明细?", function (r) {
            if (r) {
                var rowIndex = $wareList.datagrid('getRowIndex', row);
                $wareList.datagrid('deleteRow', rowIndex);
                countTotal();
                var rows = $wareList.datagrid('getRows');
                var index = -1;
                for (var i = 0; i < rows.length; i++) {
                    if (rows[i].goodsid != undefined) {
                        index = i;
                        break;
                    }
                }
            }
        });
    }
    function receiptMax(max) { //只验证长度
        $.extend($.fn.validatebox.defaults.rules, {
            receiptMax: {
                validator: function (value) {
                    return value <= max;
                },
                message: '请输入小于等于' + max + '的数字!'
            }
        });
    }

    //审核退货通知单
    function isAuditRejectBill(id) {
        var fn = function (r) {
            if (r) {
                var storager = $("#sales-hidden-storager").widget("getValue");
                loading("审核中..");
                $.ajax({
                    url: 'sales/auditRejectBill.do',
                    dataType: 'json',
                    type: 'post',
                    data: 'id=' + id + '&type=1&check=1' + '&storager=' + storager,
                    success: function (json) {
                        loaded();
                        if (json.flag == true) {
                            if (json.billId == null || json.billId == "") {
                                $.messager.alert("提醒", "审核成功;");
                            }
                            else {
                                $.messager.alert("提醒", "审核并自动生成退货入库单成功，单据号为：" + json.billId);
                                $("#sales-buttons-rejectBill").buttonWidget("enableButton", "button-print-sales");
                                $("#sales-buttons-rejectBill").buttonWidget("enableButton", "button-printview-sales");
                            }
                            $("#sales-status-rejectBillAddPage").val("3");
                            $("#sales-buttons-rejectBill").buttonWidget("setDataID", {
                                id: id,
                                state: '3',
                                type: 'view'
                            });
                            //refreshPanel('sales/rejectBillViewPage.do?id='+ id);
                            //刷新列表
                            tabsWindowURL("/sales/rejectBillListPage.do").$("#sales-datagrid-rejectBillListPage").datagrid('reload');
                            //关闭当前标签页
                            top.closeNowTab();
                        }
                        else {
                            if ('0' == json.checkflag) {
                                $.messager.confirm("提醒", "相同客户相同制单日期，有金额相同的退货通知单，是否继续审核？", function (r) {
                                    if (r) {
                                        loading("审核中..");
                                        $.ajax({
                                            url: 'sales/auditRejectBill.do',
                                            dataType: 'json',
                                            type: 'post',
                                            data: 'id=' + id + '&type=1' + '&storager=' + storager,
                                            success: function (json) {
                                                loaded();
                                                if (json.flag == true) {
                                                    if (json.billId == null || json.billId == "") {
                                                        $.messager.alert("提醒", "审核成功;");
                                                    }
                                                    else {
                                                        $.messager.alert("提醒", "审核并自动生成退货入库单成功，单据号为：" + json.billId);
                                                        $("#sales-buttons-rejectBill").buttonWidget("enableButton", "button-print-sales");
                                                        $("#sales-buttons-rejectBill").buttonWidget("enableButton", "button-printview-sales");
                                                    }
                                                    $("#sales-status-rejectBillAddPage").val("3");
                                                    $("#sales-buttons-rejectBill").buttonWidget("setDataID", {
                                                        id: id,
                                                        state: '3',
                                                        type: 'view'
                                                    });
                                                    //refreshPanel('sales/rejectBillViewPage.do?id='+ id);
                                                    //刷新列表
                                                    tabsWindowURL("/sales/rejectBillListPage.do").$("#sales-datagrid-rejectBillListPage").datagrid('reload');
                                                    //关闭当前标签页
                                                    top.closeNowTab();
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
                            else if ('0' == json.checkstorage) {
                                if (null != json.msg) {
                                    $.messager.alert("错误", "审核出错,订单默认仓库为空，并且订单中商品编号为:" + json.msg + "的商品，默认仓库也为空！");
                                }
                                else {
                                    $.messager.alert("错误", "审核出错");
                                }
                            }
                            else {
                                var msg = "";
                                if (json.msg != null) {
                                    msg = "<br/>" + json.msg;
                                }
                                $.messager.alert("提醒", "审核失败。" + msg);
                            }
                        }
                    },
                    error: function () {
                        loaded();
                        $.messager.alert("错误", "审核出错");
                    }
                });
            }
        };
        $.messager.confirm({
            title: '审核',
            onOpen: function () {
                confirmStorageWidget()
            },
            msg: "确定审核该退货通知单信息？</br><span style=\"float: left;\">选择收货人:</span><input  id=\"sales-hidden-storager\" name=\"storager\"/>",
            fn: fn
        });
    }

    $(function () {
        //关闭明细添加页面
        $(document).bind('keydown', 'esc', function () {
            $("#sales-savegoon-billDetailAddPage").focus();
            $("#sales-dialog-rejectBillAddPage-content").dialog("close");
        });
        $(document).bind('keydown', 'ctrl+enter', function () {
            $("#sales-savegoon-billDetailAddPage").focus();
            $("#sales-savegoon-billDetailEditPage").focus();
            $("#sales-savegoon-billDetailAddPage").trigger("click");
            $("#sales-savegoon-billDetailEditPage").trigger("click");
        });
        $(document).bind('keypress', '+', function () {
            $("#sales-savegoon-billDetailAddPage").focus();
            $("#sales-savegoon-billDetailEditPage").focus();
            setTimeout(function () {
                $("#sales-savegoon-billDetailAddPage").trigger("click");
                $("#sales-savegoon-billDetailEditPage").trigger("click");
            }, 100);
            return false;
        });
    });
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "rejectBill-dialog-print",
            code: "sales_rejectbill",
            url_preview: "print/sales/rejectBillPrintView.do",
            url_print: "print/sales/rejectBillPrint.do",
            btnPreview: "button-printview-sales",
            btnPrint: "button-print-sales",
            printlimit: $("#sales-printlimit-rejectBillAddPage").val() || 0,
            getData: function (tableId, printParam) {
                var id = $("#sales-backid-rejectBill").val();
                if (id == "") {
                    $.messager.alert("警告", "找不到要打印的信息!");
                    return false;
                }
                var status = $("#sales-status-rejectBillAddPage").val() || "";
                var printtimes = $("#sales-printtimes-rejectBillAddPage").val() || 0;
                printParam.idarrs = id;
                if (printtimes != "0")
                    printParam.printIds = [id];
                return true;
            },
            printAfterHandler: function (option, printParam) {
                $("#sales-printtimes-rejectBillAddPage").val(printtimes + 1);
                var printlimit = $("#sales-printlimit-rejectBillAddPage").val() || -1;
                if (0 != printlimit) {
                    $("#sales-buttons-rejectBill").buttonWidget("disableMenuItem", "button-print-sales");
                }
            }
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
