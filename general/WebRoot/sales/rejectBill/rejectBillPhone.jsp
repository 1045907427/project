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
        <div id="sales-panel-rejectBill">
        </div>
    </div>
</div>
<div class="easyui-dialog" id="sales-sourceQueryDialog-rejectBill" data-options="closed:true"></div>
<div class="easyui-dialog" id="sales-sourceDialog-rejectBill" data-options="closed:true"></div>
<div class="easyui-dialog" id="sales-dialog-rejectBill" closed="true"></div>
<div id="sales-dialog-split"></div>
<script type="text/javascript">
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
                        return rowData.goodsInfo.name;
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
                <security:authorize url="/sales/rejectBillPhoneDelete.do">
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
                                                refreshPanel('sales/rejectBillViewPage.do?id=' + data.id);
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
                <security:authorize url="/sales/rejectBillSave.do">
                {
                    type: 'button-save',
                    handler: function () {
                        $.messager.confirm("提醒", "确定保存退货通知单？", function (r) {
                            if (r) {
                                var json = $("#sales-datagrid-rejectBillAddPage").datagrid('getRows');
                                $("#sales-goodsJson-rejectBillAddPage").val(JSON.stringify(json));
                                rejectBill_realSave_form_submit();
                                $("#sales-form-rejectBillAddPage").submit();
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/rejectBillPhoneBack.do">
                {
                    type: 'button-back',
                    handler: function (data) {
                        $("#sales-backid-rejectBill").val(data.id);
                        refreshPanel('sales/rejectBillEditPage.do?id=' + data.id);
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/rejectBillPhoneNext.do">
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
                <security:authorize url="/sales/auditRejectBillPhone.do">
                {
                    id: 'button-audit-phone',
                    name: '审核',
                    iconCls: 'button-audit',
                    handler: function () {
                        var id = $("#sales-backid-rejectBill").val();
                        if (id == '') {
                            return false;
                        }
                        $.messager.confirm("提醒", "确定审核该退货通知单信息？", function (r) {
                            if (r) {
                                loading("审核中..");
                                $.ajax({
                                    url: 'sales/auditRejectBillPhone.do',
                                    dataType: 'json',
                                    type: 'post',
                                    data: 'id=' + id,
                                    success: function (json) {
                                        loaded();
                                        if (json.flag) {
                                            $.messager.alert("提醒", "审核成功");
                                            //刷新手机退货申请单列表
                                            tabsWindowURL("/sales/rejectBillPhonePage.do").$("#sales-datagrid-rejectBillListPage").datagrid('reload');
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
                },
                </security:authorize>
                <security:authorize url="/sales/rejectBillPhonePrintView.do">
                {
                    id: 'button-printview-sales',
                    name: '打印预览',
                    iconCls: 'button-preview',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/rejectBillPhonePrint.do">
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
            tab: '手机退货申请单列表',
            taburl: '/sales/rejectBillPhonePage.do',
            id: '${id}',
            datagrid: 'sales-datagrid-rejectBillListPage'
        });
    });
    function refreshPanel(url) { //更新panel
        $("#sales-panel-rejectBill").panel('refresh', url);
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
                        $.messager.alert("提醒", "保存成功");
                        $("#sales-backid-rejectBill").val(json.backid);
                        if (json.type == "add") {
                            $("#sales-buttons-rejectBill").buttonWidget("addNewDataId", json.backid);
                            $("#sales-panel-rejectBill").panel('refresh', 'sales/rejectBillEditPage.do?id=' + json.backid);
                        }
                        $("#sales-panel-rejectBill").panel('refresh', 'sales/rejectBillEditPage.do?id=' + json.backid);
                    }
                    else {
                        $.messager.alert("提醒", "保存失败");
                    }
                }catch(error){
                    $.messager.alert("错误", "保存出错。请刷新后再试。");
                }finally {
                    loaded();
                }
            }
        });
    }
    function beginAddDetail() { //开始添加商品信息
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
        var goodsJson = $("#sales-goodsId-billDetailAddPage").goodsWidget('getObject');
        form.goodsInfo = goodsJson;
        var customer = $("#sales-customer-rejectBillAddPage-hidden").val();
        var rowIndex = 0;
        var rows = $wareList.datagrid('getRows');
        var updateFlag = false;
        for (var i = 0; i < rows.length; i++) {
            var rowJson = rows[i];
            if (rowJson.goodsid == goodsJson.id) {
                rowIndex = i;
                updateFlag = true;
                break;
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
            url = 'sales/rejectBillDetailEditPage.do'; //如果是修改页面，数据直接来源于datagrid中的json数据。
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

                    $("#sales-auxunitname-billDetailAddPage").html(row.auxunitname);
                    $("#sales-unitname-billDetailAddPage").html(row.unitname);
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
                    return parseInt(value) <= max;
                },
                message: '请输入小于等于' + max + '的数字!'
            }
        });
    }

    //审核退货通知单
    function isAuditRejectBill(id) {
        $.messager.confirm("提醒", "确定审核该退货通知单信息？", function (r) {
            if (r) {
                loading("审核中..");
                $.ajax({
                    url: 'sales/auditRejectBill.do',
                    dataType: 'json',
                    type: 'post',
                    data: 'id=' + id + '&type=1&check=1',
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
                            //刷新销售退货通知单列表
                            tabsWindowURL("/sales/rejectBillListPage.do").$("#sales-datagrid-rejectBillListPage").datagrid('reload');
                            //关闭当前标签页
                            top.closeNowTab();
                        }
                        else {
                            if (json.checkflag == '0') {
                                $.messager.confirm("提醒", "相同客户相同制单日期，有金额相同的退货通知单，是否继续审核？", function (r) {
                                    if (r) {
                                        loading("审核中..");
                                        $.ajax({
                                            url: 'sales/auditRejectBill.do',
                                            dataType: 'json',
                                            type: 'post',
                                            data: 'id=' + id + '&type=1',
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
                            } else {
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
            id: "rejectBillPhone-dialog-print",
            code: "sales_rejectbill_phone",
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
                printParam.billtype = 'phone';
                if (printtimes > 3)
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
