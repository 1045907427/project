<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>交款单</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>
<body>
<input type="hidden" id="account-backid-paymentVoucherAddPage" value="${id }"/>
<input type="hidden" id="account-referPlanOrderid-paymentVoucherAddPage"/>
<div id="account-paymentVoucherPage-layout" class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="account-buttons-paymentVoucherPage" style="height:26px;"></div>
    </div>
    <div data-options="region:'center'">
        <div class="easyui-panel" data-options="fit:true" id="account-panel-paymentVoucherPage">
        </div>
    </div>
</div>
<div style="display:none">
    <div id="account-sourceQueryDialog-paymentVoucher"></div>
    <div id="account-sourceDialog-paymentVoucher"></div>
    <div id="account-sourceViewDialog-paymentVoucher">
        <table id="account-arrivalReferOrderDatagrid-sourceViewDialog"></table>
    </div>
    <div id="account-paymentVoucherAddPage-dialog-DetailOper"></div>
</div>
<script type="text/javascript">
    var theAuthorprintFlag = 0;

    <security:authorize url="/account/paymentvoucher/auditPaymentVoucher.do">
    theAuthorprintFlag = 1;
    </security:authorize>

    var paymentVoucher_type = '${type}';
    paymentVoucher_type = $.trim(paymentVoucher_type.toLowerCase());
    if (paymentVoucher_type == "") {
        paymentVoucher_type = 'add';
    }
    var paymentVoucher_url = "account/paymentvoucher/paymentVoucherAddPage.do";
    if (paymentVoucher_type == "view" || paymentVoucher_type == "show" || paymentVoucher_type == "handle") {
        paymentVoucher_url = "account/paymentvoucher/paymentVoucherViewPage.do?id=${id}";
    }
    if (paymentVoucher_type == "edit") {
        paymentVoucher_url = "account/paymentvoucher/paymentVoucherEditPage.do?id=${id}";
    }
    if (paymentVoucher_type == "copy") {
        paymentVoucher_url = "account/paymentvoucher/paymentVoucherCopyPage.do?id=${id}";
    }
    var pageListUrl = "/account/paymentvoucher/paymentVoucherListPage.do";
    function paymentVoucher_tempSave_form_submit() {
        $("#account-form-paymentVoucherAddPage").form({
            onSubmit: function () {
                loading("提交中..");
            },
            success: function (data) {
                loaded();
                var json = $.parseJSON(data);
                if (json.flag == true) {
                    paymentVoucher_RefreshDataGrid();
                    $.messager.alert("提醒", "暂存成功");
                    $("#account-backid-paymentVoucherAddPage").val(json.backid);
                    if (json.opertype && json.opertype == "add") {
                        $("#account-buttons-paymentVoucherPage").buttonWidget("addNewDataId", json.backid);
                    }
                    //$("#account-panel-paymentVoucherPage").panel('refresh', 'account/paymentvoucher/paymentVoucherViewPage.do?id='+ json.backid);
                    //$("#account-paymentVoucherAddPage-status").val("1");
                }
                else {
                    $.messager.alert("提醒", "暂存失败");
                }
            }
        });
    }
    function paymentVoucher_realSave_form_submit() {
        $("#account-form-paymentVoucherAddPage").form({
            onSubmit: function () {
                var flag = $(this).form('validate');
                if (flag == false) {
                    return false;
                }

                var collectuser = $("#account-paymentVoucherAddPage-collectuser").val();
                if (collectuser == "") {
                    $.messager.alert("提醒", "抱歉，检测到收款人为空，请联系管理员进行处理");
                    return false;
                }

                loading("提交中..");
            },
            success: function (data) {
                loaded();
                var json = $.parseJSON(data);
                if (json.flag == true) {
                    paymentVoucher_RefreshDataGrid();
                    var saveaudit = $("#account-paymentVoucherAddPage-saveaudit").val();
                    if (saveaudit == "saveaudit") {
                        if (json.auditflag) {
                            if (json.nextBillFlag) {
                                $.messager.alert("提醒", "审核并生成收款单成功");
                            } else {
                                $.messager.alert("提醒", "审核成功,但生成收款单失败");
                            }
                            paymentVoucher_RefreshDataGrid();
                            $("#account-panel-paymentVoucherPage").panel('refresh', 'account/paymentvoucher/paymentVoucherEditPage.do?id=' + json.backid);
                        } else {
                            if (json.msg) {
                                $.messager.alert("提醒", "保存成功,审核失败。" + json.msg);
                            } else {
                                $.messager.alert("提醒", "保存成功,审核失败。");
                            }
                        }
                    } else {
                        $.messager.alert("提醒", "保存成功。");
                    }
                    $("#account-backid-paymentVoucherAddPage").val(json.backid);
                    if (json.opertype && json.opertype == "add") {
                        $("#account-buttons-paymentVoucherPage").buttonWidget("addNewDataId", json.backid);
                        $("#account-panel-paymentVoucherPage").panel('refresh', 'account/paymentvoucher/paymentVoucherEditPage.do?id=' + json.backid);
                    } else {
                        $("#account-paymentVoucherAddPage-status").val("2");
                    }
                }
                else {
                    if (json.msg) {
                        $.messager.alert("提醒", "保存失败!" + json.msg);
                    } else {
                        $.messager.alert("提醒", "保存失败");
                    }
                }
            }
        });
    }
    function paymentVoucher_RefreshDataGrid() {
        try {
            tabsWindowURL(pageListUrl).$("#account-table-paymentVoucherListPage").datagrid('reload');
        } catch (e) {
        }
    }

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
    function appendToOrderDetailGrid(data) {
        var $dtable = $("#account-paymentVoucherAddPage-paymentVouchertable");
        if ($dtable.size() == 0) {
            return false;
        }
        if (data == null || data.billdetailno == null || data.billdetailno == "") {
            return false;
        }
        var dataRows = $dtable.datagrid('getRows');
        var index = 0;
        for (index = 0; index < dataRows.length; index++) {
            if (dataRows[index].billdetailno != null && dataRows[index].billdetailno == data.billdetailno) {
                //$dtable.datagrid('updateRow',{
                //  	index:index,
                //  	row:data
                //});
                break;
            } else if (dataRows[index].billdetailno == null || dataRows[index].billdetailno == "") {
                $dtable.datagrid('updateRow', {
                    index: index,
                    row: data
                });
                break;
            }
        }
        if (index == dataRows.length) {
            $dtable.datagrid('appendRow', {});
        }
    }
    function getUniqueArray(arr) {
        var a = [];
        var l = arr.length;
        for (var i = 0; i < l; i++) {
            for (var j = i + 1; j < l; j++) {
                if (arr[i] === arr[j]) j = ++i;
            }
            a.push(arr[i]);
        }
        return a;
    }
    ;
    function formatPaymentVoucherBill(datarow) {
        if (dataRows == null || dataRows.length == 0) {
            return false;
        }
    }
    function checkAfterAddCustomer(customerid) {
        if (customerid == null || customerid == "") {
            return false;
        }
        var $ordertable = $("#account-paymentVoucherAddPage-paymentVouchertable");
        var flag = true;
        if ($ordertable.size() > 0) {
            var data = $ordertable.datagrid('getRows');
            if (data != null && data.length > 0) {
                for (var i = 0; i < data.length; i++) {
                    if (data[i].customerid == customerid) {
                        flag = false;
                        break;
                    }
                }
            }
        }
        return flag;
    }

    function orderDetailAddDialog() {

        var flag = $("#account-form-paymentVoucherAddPage").form('validate');
        if (flag == false) {
            $.messager.alert("提醒", '请先完善交款单基本信息');
            return false;
        }
        var businessdate = $("#account-paymentVoucherAddPage-businessdate").val();
        if (businessdate == null) {
            $.messager.alert("提醒", "请先选择业务日期");
            $("#account-paymentVoucherAddPage-businessdate").focus();
            return false;
        }
        $('<div id="account-paymentVoucherAddPage-dialog-DetailOper-content"></div>').appendTo("#account-paymentVoucherAddPage-dialog-DetailOper");
        var $DetailOper = $("#account-paymentVoucherAddPage-dialog-DetailOper-content");
        $DetailOper.dialog({
            title: '交款单明细新增(按ESC退出)',
            width: 380,
            height: 280,
            left: ($(window).width() - 380) * 0.5 + 80,
            top: ($(window).height() - 280) * 0.5 - 30,
            closed: true,
            cache: false,
            modal: true,
            maximizable: true,
            resizable: true,
            href: "account/paymentvoucher/paymentVoucherDetailAddPage.do?businessdate=" + businessdate,
            onLoad: function () {
                // $("#account-paymentVoucherDetail-goodsid").focus();
                $("#account-paymentVoucherDetail-customerid").focus();
                $("#account-paymentVoucherDetail-customerid").select();
            },
            onClose: function () {
                $DetailOper.dialog("destroy");
            }
        });
        $DetailOper.dialog("open");
    }
    function orderDetailEditDialog(initdata) {
        $('<div id="account-paymentVoucherAddPage-dialog-DetailOper-content"></div>').appendTo("#account-paymentVoucherAddPage-dialog-DetailOper");
        var $DetailOper = $("#account-paymentVoucherAddPage-dialog-DetailOper-content");
        if (initdata == null) {
            initdata = {};
        }
        $DetailOper.dialog({
            title: '交款单明细修改(按ESC退出)',
            width: 380,
            height: 280,
            left: ($(window).width() - 380) * 0.5 + 80,
            top: ($(window).height() - 280) * 0.5 - 30,
            closed: true,
            cache: false,
            modal: true,
            maximizable: true,
            resizable: true,
            href: "account/paymentvoucher/paymentVoucherDetailEditPage.do?customerid=" + initdata.customerid,
            onLoad: function () {
                try {
                    if (initdata.customerid != null && initdata.customerid != "") {
                        if ($("#account-form-paymentVoucherDetailAddPage").size() > 0) {

                            $("#account-form-paymentVoucherDetailAddPage").form('load', initdata);

                            $("#account-paymentVoucherDetail-customerid").customerWidget("setValue", initdata.customerid);

                            $("#account-paymentVoucherDetail-customerid").customerWidget("setText", initdata.customername);
                        }
                    }
                } catch (e) {
                }
                $("#account-paymentVoucherDetail-customerid").focus();
                $("#account-paymentVoucherDetail-customerid").select();
            },
            onClose: function () {
                $DetailOper.dialog("destroy");
            }
        });
        $DetailOper.dialog("open");
    }
    function orderDetailViewDialog(initdata) {
        $('<div id="account-paymentVoucherAddPage-dialog-DetailOper-content"></div>').appendTo("#account-paymentVoucherAddPage-dialog-DetailOper");
        var $DetailOper = $("#account-paymentVoucherAddPage-dialog-DetailOper-content");
        $DetailOper.dialog({
            title: '查看',
            width: 380,
            height: 280,
            left: ($(window).width() - 380) * 0.5 + 80,
            top: ($(window).height() - 280) * 0.5 - 30,
            closed: true,
            cache: false,
            modal: true,
            maximizable: true,
            resizable: true,
            href: "account/paymentvoucher/paymentVoucherDetailViewPage.do",
            onLoad: function () {
                if (initdata != null && initdata.customerid != null && initdata.customerid != "") {
                    if ($("#account-form-paymentVoucherAddPage").size() > 0) {
                        $("#account-form-paymentVoucherDetailViewPage").form('load', initdata);
                    }
                }
            },
            onClose: function () {
                $DetailOper.dialog("destroy");
            }
        });
        $DetailOper.dialog("open");
    }
    function footerReCalc() {
        //disableChoiceWidget();
        var $potable = $("#account-paymentVoucherAddPage-paymentVouchertable");
        var data = $potable.datagrid('getRows');
        var amount = 0;
        for (var i = 0; i < data.length; i++) {
            if (data[i].amount) {
                amount = Number(amount) + Number(data[i].amount);
            }
        }
        amount = String(amount);
        $potable.datagrid('reloadFooter', [
            {customerid: '合计', amount: amount}
        ]);
    }

    function getAddRowIndex() {
        var $potable = $("#account-paymentVoucherAddPage-paymentVouchertable");
        var dataRows = $potable.datagrid('getRows');

        var rindex = 0;
        for (rindex = 0; rindex < dataRows.length; rindex++) {
            if (dataRows[rindex].customerid == null || dataRows[rindex].customerid == "") {
                break;
            }
        }
        if (rindex == dataRows.length) {
            $potable.datagrid('appendRow', {});
        }
        return rindex;
    }
    function checkCustomerDetailEmpty() {
        var flag = true;
        var $ordertable = $("#account-paymentVoucherAddPage-paymentVouchertable");
        var data = $ordertable.datagrid('getRows');
        for (var i = 0; i < data.length; i++) {
            if (data[i].customerid && data[i].customerid != "") {
                flag = false;
                break;
            }
        }
        return flag;
    }

    //禁用表单title
    function disableChoiceWidget() {
        var rows = $("#account-paymentVoucherAddPage-paymentVouchertable").datagrid('getRows');
        var count = 0;
        for (var i = 0; i < rows.length; i++) {
            if (rows[i].customerid && rows[i].customerid != "") {
                count++;
            }
        }
    }
    var tableColJson = $("#account-paymentVoucherAddPage-paymentVouchertable").createGridColumnLoad({
        name: 'account_paymentvoucher_detail',
        frozenCol: [[]],
        commonCol: [[
            {field: 'id', title: '编码', width: 70, hidden: true, isShow: true},
            {field: 'customerid', title: '客户编码', width: 80, isShow: true},
            {field: 'customername', title: '客户名称', width: 300, isShow: true},
            {
                field: 'amount', title: '金额', width: 80, align: 'right',
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {field: 'remark', title: '备注', width: 200}
        ]]
    });
    $(document).ready(function () {
        $("#account-panel-paymentVoucherPage").panel({
            href: paymentVoucher_url,
            cache: false,
            maximized: true,
            border: false
        });
        //按钮
        $("#account-buttons-paymentVoucherPage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/account/paymentvoucher/paymentVoucherAddBtn.do">
                {
                    type: 'button-add',
                    handler: function () {
                        $("#account-panel-paymentVoucherPage").panel('refresh', 'account/paymentvoucher/paymentVoucherAddPage.do');
                    }
                },
                </security:authorize>
                <security:authorize url="/account/paymentvoucher/plannedOrderTempSave.do">
                {
                    type: 'button-hold',
                    handler: function () {
                        $("#account-paymentVoucherAddPage-addType").val("temp");
                        var datarows = $("#account-paymentVoucherAddPage-paymentVouchertable").datagrid('getRows');
                        if (datarows != null && datarows.length > 0) {
                            $("#account-paymentVoucherAddPage-paymentVoucherDetails").val(JSON.stringify(datarows));
                        }
                        paymentVoucher_tempSave_form_submit();
                        $("#account-form-paymentVoucherAddPage").submit();
                    }
                },
                </security:authorize>
                <security:authorize url="/account/paymentvoucher/plannedOrderRealSave.do">
                {
                    type: 'button-save',
                    handler: function () {

                        if (checkCustomerDetailEmpty()) {
                            $.messager.alert("提醒", "抱歉，请填写交款单明细信息");
                            return false;
                        } else {
                            $.messager.confirm("提醒", "确定保存交款单信息？", function (r) {
                                if (r) {
                                    $("#account-paymentVoucherAddPage-addType").val("real");
                                    var datarows = $("#account-paymentVoucherAddPage-paymentVouchertable").datagrid('getRows');
                                    if (datarows != null && datarows.length > 0) {
                                        $("#account-paymentVoucherAddPage-paymentVoucherDetails").val(JSON.stringify(datarows));
                                    }
                                    paymentVoucher_realSave_form_submit();
                                    $("#account-form-paymentVoucherAddPage").submit();
                                }
                            });
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/account/paymentvoucher/paymentVoucherSaveAudit.do">
                {
                    type: 'button-saveaudit',
                    handler: function () {
                        $.messager.confirm("提醒", "确定保存并审核交款单信息？", function (r) {
                            if (r) {
                                $("#account-paymentVoucherAddPage-addType").val("real");
                                $("#account-paymentVoucherAddPage-saveaudit").val("saveaudit");
                                var datarows = $("#account-paymentVoucherAddPage-paymentVouchertable").datagrid('getRows');
                                if (datarows != null && datarows.length > 0) {
                                    $("#account-paymentVoucherAddPage-paymentVoucherDetails").val(JSON.stringify(datarows));
                                }
                                paymentVoucher_realSave_form_submit();
                                $("#account-form-paymentVoucherAddPage").submit();
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/account/paymentvoucher/paymentVoucherGiveUpBtn.do">
                {
                    type: 'button-giveup',//放弃
                    handler: function () {
                        var $polbuttons = $("#account-buttons-paymentVoucherPage");
                        var type = $polbuttons.buttonWidget("getOperType");
                        if (type == "add") {
                            var id = $("#account-backid-paymentVoucherAddPage").val();
                            if (id == "") {
                                tabsWindowTitle(pageListUrl);
                            } else {
                                $("#account-panel-paymentVoucherPage").panel('refresh', 'account/paymentvoucher/paymentVoucherViewPage.do?id=' + id);
                            }
                        } else if (type == "edit") {
                            var id = $("#account-backid-paymentVoucherAddPage").val();
                            if (id == "") {
                                return false;
                            }
                            var flag = isLockData(id, "t_account_paymentvoucher");
                            if (!flag) {
                                $.messager.alert("警告", "该数据正在被其他人操作，暂不能修改！");
                                return false;
                            }
                            $("#account-panel-paymentVoucherPage").panel('refresh', 'account/paymentvoucher/paymentVoucherViewPage.do?id=' + id);
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/account/paymentvoucher/deletePaymentVoucher.do">
                {
                    type: 'button-delete',
                    handler: function () {
                        var id = $("#account-backid-paymentVoucherAddPage").val();
                        if (id == "") {
                            return false;
                        }

                        $.messager.confirm("提醒", "是否删除该交款单信息？", function (r) {
                            if (r) {
                                loading("删除中..");
                                $.ajax({
                                    url: 'account/paymentvoucher/deletePaymentVoucher.do?id=' + id,
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag) {
                                            $.messager.alert("提醒", "删除成功");
                                            var nextdata = $("#account-buttons-paymentVoucherPage").buttonWidget("removeData", "");
                                            if (null != nextdata && nextdata.id && nextdata.id != "") {
                                                $("#account-backid-paymentVoucherAddPage").val(nextdata.id);
                                                $("#account-panel-paymentVoucherPage").panel('refresh', 'account/paymentvoucher/paymentVoucherEditPage.do?id=' + nextdata.id);
                                                paymentVoucher_RefreshDataGrid();
                                            } else {
                                                $("#account-backid-paymentVoucherAddPage").val("");
                                                //$("#account-panel-paymentVoucherPage").panel('refresh', 'account/paymentvoucher/paymentVoucherAddPage.do');
                                                paymentVoucher_RefreshDataGrid();
                                                parent.closeNowTab();
                                            }
                                        } else {
                                            $.messager.alert("提醒", "删除失败");
                                        }
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/account/paymentvoucher/auditPaymentVoucher.do">
                {
                    type: 'button-audit',
                    handler: function () {
                        var id = $("#account-backid-paymentVoucherAddPage").val();
                        if (id == "") {
                            return false;
                        }
                        var flag = $("#account-form-paymentVoucherAddPage").form('validate');
                        if (flag == false) {
                            $.messager.alert("提醒", '请先完善交款单基本信息');
                            return false;
                        }
                        if (checkCustomerDetailEmpty()) {
                            $.messager.alert("提醒", "抱歉，请填写交款单明细信息");
                            return false;
                        }
                        $.messager.confirm("提醒", "是否审核通过该交款单信息？", function (r) {
                            if (r) {
                                loading("审核中..");
                                $.ajax({
                                    url: 'account/paymentvoucher/auditPaymentVoucher.do?id=' + id,
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag) {
                                            //$("#account-paymentVoucherAddPage-status").val("3");

                                            //$("#account-buttons-paymentVoucherPage").buttonWidget("setDataID", {id:id, state:'3', type:'view'});
                                            if (json.nextBillFlag) {
                                                $.messager.alert("提醒", "审核并生成收款单成功");
                                            } else {
                                                $.messager.alert("提醒", "审核成功,但生成收款单失败");
                                            }
                                            paymentVoucher_RefreshDataGrid();
                                            $("#account-panel-paymentVoucherPage").panel('refresh', 'account/paymentvoucher/paymentVoucherEditPage.do?id=' + id);
                                        } else {
                                            if (json.msg) {
                                                $.messager.alert("提醒", "审核失败!" + json.msg);
                                            } else {
                                                $.messager.alert("提醒", "审核失败");
                                            }
                                        }
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/account/paymentvoucher/oppauditPaymentVoucher.do">
                {
                    type: 'button-oppaudit',
                    handler: function () {
                        var id = $("#account-backid-paymentVoucherAddPage").val();
                        if (id == "") {
                            return false;
                        }

                        $.messager.confirm("提醒", "是否反审该交款单信息？", function (r) {
                            if (r) {
                                loading("反核中..");
                                $.ajax({
                                    url: 'account/paymentvoucher/oppauditPaymentVoucher.do?id=' + id,
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag) {
                                            //$("#account-paymentVoucherAddPage-status").val("2");
                                            //$("#account-buttons-paymentVoucherPage").buttonWidget("setDataID", {id:id, state:'2', type:'edit'});
                                            $.messager.alert("提醒", "反审成功");
                                        } else {
                                            if (json.msg) {
                                                $.messager.alert("提醒", json.msg);
                                            } else {
                                                $.messager.alert("提醒", "反审失败");
                                            }
                                        }
                                        paymentVoucher_RefreshDataGrid();
                                        $("#account-panel-paymentVoucherPage").panel('refresh', 'account/paymentvoucher/paymentVoucherEditPage.do?id=' + id);
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/account/paymentvoucher/paymentVoucherBack.do">
                {
                    type: 'button-back',
                    handler: function (data) {
                        if (data != null && data.id != null && data.id != "") {
                            $("#account-backid-paymentVoucherAddPage").val(data.id);
                            $("#account-panel-paymentVoucherPage").panel('refresh', 'account/paymentvoucher/paymentVoucherEditPage.do?id=' + data.id);
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/account/paymentvoucher/paymentVoucherNext.do">
                {
                    type: 'button-next',
                    handler: function (data) {
                        if (data != null && data.id != null && data.id != "") {
                            $("#account-backid-paymentVoucherAddPage").val(data.id);
                            $("#account-panel-paymentVoucherPage").panel('refresh', 'account/paymentvoucher/paymentVoucherEditPage.do?id=' + data.id);
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/account/paymentvoucher/paymentVoucherPrintViewBtn.do">
                {
                    type: 'button-preview',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/account/paymentvoucher/paymentVoucherPrintBtn.do">
                {
                    type: 'button-print',
                    handler: function () {
                    }
                },
                </security:authorize>
                {}
            ],
            layoutid: 'account-paymentVoucherPage-layout',
            model: 'bill',
            type: 'view',
            taburl: pageListUrl,
            id: '${id}',
            datagrid: 'account-table-paymentVoucherListPage',
            tname: 't_account_paymentvoucher'
        });

        $(document).bind('keydown', 'esc', function () {
            $("#account-paymentVoucherDetail-remark").focus();
            $("#account-paymentVoucherAddPage-dialog-DetailOper-content").dialog("close");
        });
        $(document).bind('keydown', 'ctrl+enter', function () {
            $("#account-paymentVoucherDetail-remark").focus();
            var $tmpObj = $("#account-paymentVoucherDetailAddPage-addSaveGoOn");
            if ($tmpObj.size() > 0) {
                $tmpObj.focus();
                $tmpObj.trigger("click");
            }
            $tmpObj = $("#account-paymentVoucherDetailAddPage-editSave");
            if ($tmpObj.size() > 0) {
                $tmpObj.focus();
                $tmpObj.trigger("click");
            }
            return false;
        });
        $(document).bind('keydown', '+', function () {
            $("#account-paymentVoucherDetail-remark").focus();
            var $tmpObj = $("#account-paymentVoucherDetailAddPage-addSaveGoOn");
            if ($tmpObj.size() > 0) {
                $tmpObj.focus();
                $tmpObj.trigger("click");
            }
            $tmpObj = $("#account-paymentVoucherDetailAddPage-editSave");
            if ($tmpObj.size() > 0) {
                $tmpObj.focus();
                $tmpObj.trigger("click");
            }
            return false;
        });
    });
    function updateDataGridPrintimes(id) {
        var thepage = tabsWindowURL(pageListUrl);
        if (thepage == null) {
            return false;
        }
        var thegrid = thepage.$("#account-table-paymentVoucherListPage");
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
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "listPage-PaymentVoucher-dialog-print",
            code: "account_paymentvoucher",
            url_preview: "print/account/paymentVoucherPrintView.do",
            url_print: "print/account/paymentVoucherPrint.do",
            btnPreview: "button-preview",
            btnPrint: "button-print",
            getData: function (tableId, printParam) {
                var id = $("#account-backid-paymentVoucherAddPage").val();
                if (id == "") {
                    $.messager.alert("警告", "找不到要打印的信息!");
                    return false;
                }
                var status = $("#account-paymentVoucherAddPage-status").val() || "";
                if (!(status == 1 || status == 2 || theAuthorprintFlag == 1)) {
                    $.messager.alert("提醒", "此交款单不可打印");
                    return false;
                }
                printParam.idarrs = id;
                var printtimes = $("#account-paymentVoucherAddPage-printtimes").val() || 0;
                if (printtimes > 0)
                    printParam.printIds = [id];
                return true;
            },
            onPrintSuccess: function (option) {
                updateDataGridPrintimes($("#account-backid-paymentVoucherAddPage").val());
            }
        });

    });
</script>
<%--打印结束 --%>
</body>
</html>
