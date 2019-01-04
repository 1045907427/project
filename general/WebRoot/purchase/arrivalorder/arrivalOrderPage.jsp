<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>采购进货单</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>
<body>
<input type="hidden" id="purchase-backid-arrivalOrderAddPage" value="${id }"/>
<input type="hidden" id="purchase-referPlanOrderid-arrivalOrderAddPage"/>
<div id="purchase-arrivalOrderPage-layout" class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="purchase-buttons-arrivalOrderPage"></div>
    </div>
    <div data-options="region:'center'">
        <div class="easyui-panel" data-options="fit:true" id="purchase-panel-arrivalOrderPage">
        </div>
    </div>
</div>
<div style="display:none">
    <div id="purchase-sourceQueryDialog-arrivalOrder"></div>
    <div id="purchase-sourceDialog-arrivalOrder"></div>
    <div id="purchase-sourceViewDialog-arrivalOrder">
        <%--<table id="purchase-arrivalReferOrderDatagrid-sourceViewDialog"></table> --%>
    </div>
    <div id="purchase-wfdialog-buyOrderPage"></div>
    <div id="purchase-arrivalOrderAddPage-dialog-DetailOper"></div>
    <div id="purchase-share-dialog"></div>
</div>
<script type="text/javascript">
    var arrivalOrder_type = '${type}';
    arrivalOrder_type = $.trim(arrivalOrder_type.toLowerCase());
    if (arrivalOrder_type == "") {
        arrivalOrder_type = 'add';
    }
    var arrivalOrder_url = "purchase/arrivalorder/arrivalOrderAddPage.do";
    if (arrivalOrder_type == "view" || arrivalOrder_type == "show" || arrivalOrder_type == "handle") {
        arrivalOrder_url = "purchase/arrivalorder/arrivalOrderViewPage.do?id=${id}";
    }
    if (arrivalOrder_type == "edit") {
        arrivalOrder_url = "purchase/arrivalorder/arrivalOrderEditPage.do?id=${id}";
    }
    if (arrivalOrder_type == "copy") {
        arrivalOrder_url = "purchase/arrivalorder/arrivalOrderCopyPage.do?id=${id}";
    }
    var pageListUrl = "/purchase/arrivalorder/arrivalOrderListPage.do";
    function arrivalOrder_tempSave_form_submit() {
        $("#purchase-form-arrivalOrderAddPage").form({
            onSubmit: function () {
                loading("提交中..");
            },
            success: function (data) {
                loaded();
                var json = $.parseJSON(data);
                if (json.flag == true) {
                    arrivalOrder_RefreshDataGrid();
                    $.messager.alert("提醒", "暂存成功");
                    $("#purchase-backid-arrivalOrderAddPage").val(json.backid);
                    if (json.opertype && json.opertype == "add") {
                        $("#purchase-buttons-arrivalOrderPage").buttonWidget("addNewDataId", json.backid);
                    }
                    //$("#purchase-panel-arrivalOrderPage").panel('refresh', 'purchase/arrivalorder/arrivalOrderViewPage.do?id='+ json.backid);
                    //$("#purchase-arrivalOrderAddPage-status").val("1");
                }
                else {
                    $.messager.alert("提醒", "暂存失败");
                }
            }
        });
    }
    function arrivalOrder_realSave_form_submit() {
        $("#purchase-form-arrivalOrderAddPage").form({
            onSubmit: function () {
                var flag = $(this).form('validate');
                if (flag == false) {
                    return false;
                }
                loading("提交中..");
            },
            success: function (data) {
                loaded();
                var json = $.parseJSON(data);
                if (json.flag == true) {
                    arrivalOrder_RefreshDataGrid();
                    var saveaudit = $("#purchase-arrivalOrderAddPage-saveaudit").val();
                    if (saveaudit == "saveaudit") {
                        if (json.auditflag) {
                            $.messager.alert("提醒", "保存审核成功");
                            arrivalOrder_RefreshDataGrid();
                            $("#purchase-panel-arrivalOrderPage").panel('refresh', 'purchase/arrivalorder/arrivalOrderPage.do?type=edit&id=' + json.backid);
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
                    $("#purchase-backid-arrivalOrderAddPage").val(json.backid);
                    if (json.opertype && json.opertype == "add") {
                        $("#purchase-buttons-arrivalOrderPage").buttonWidget("addNewDataId", json.backid);
                        $("#purchase-panel-arrivalOrderPage").panel('refresh', 'purchase/arrivalorder/arrivalOrderEditPage.do?id=' + json.backid);
                    } else {
                        $("#purchase-arrivalOrderAddPage-status").val("2");
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
    function arrivalOrder_RefreshDataGrid() {
        try {
            tabsWindowURL(pageListUrl).$("#purchase-table-arrivalOrderListPage").datagrid('reload');
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
        var $dtable = $("#purchase-arrivalOrderAddPage-arrivalOrdertable");
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
    function getReferOrderidArr() {
        var idarr = new Array();
        var dataRow = $("#purchase-arrivalOrderAddPage-arrivalOrdertable").datagrid('getRows');
        if (dataRow && dataRow.length > 0) {
            for (var i = 0; i < dataRow.length; i++) {
                if (dataRow[i].billno && dataRow[i].billno != "") {
                    idarr.push(dataRow[i].billno);
                }
            }
        }
        return getUniqueArray(idarr);
    }
    function choiceReferOrderToGrid(dataRows) {
        if (dataRows == null || dataRows.length == 0) {
            return false;
        }
        for (var i = 0; i < dataRows.length; i++) {
            appendToOrderDetailGrid(dataRows[i]);
        }
    }

    function formatArrivalOrderBill(datarow) {
        if (dataRows == null || dataRows.length == 0) {
            return false;
        }
    }
    function checkAfterAddGoods(goodsid) {
        if (goodsid == null || goodsid == "") {
            return false;
        }
        var $ordertable = $("#purchase-arrivalOrderAddPage-arrivalOrdertable");
        var flag = true;
        if ($ordertable.size() > 0) {
            var data = $ordertable.datagrid('getRows');
            if (data != null && data.length > 0) {
                for (var i = 0; i < data.length; i++) {
                    if (data[i].goodsid == goodsid) {
                        flag = false;
                        break;
                    }
                }
            }
        }
        return flag;
    }

    function noSourceReferHandler() {

        $('<div id="purchase-sourceQueryDialog-arrivalOrder-content"></div>').appendTo('#purchase-sourceQueryDialog-arrivalOrder');
        var $arrivalOrderSourceQueryDialog = $("#purchase-sourceQueryDialog-arrivalOrder-content");
        $arrivalOrderSourceQueryDialog.dialog({ //查询参照上游
            title: '入库单查询',
            width: 400,
            height: 260,
            closed: false,
            modal: true,
            cache: false,
            href: 'purchase/arrivalorder/arrivalOrderSourceQueryPage.do',
            buttons: [
                {
                    text: '查询',
                    handler: function () {
                        var $SourceQuery = $("#purchase-form-arrivalOrderSourceQueryPage");
                        var queryJSON = {};
                        if ($SourceQuery && $SourceQuery.size() > 0) {
                            queryJSON = $("#purchase-form-arrivalOrderSourceQueryPage").serializeJSON();
                        }
                        $("#purchase-sourceQueryDialog-arrivalOrder-content").dialog('close', true);
                        $('<div id="purchase-sourceDialog-arrivalOrder-content"></div>').appendTo('#purchase-sourceDialog-arrivalOrder');
                        var $arrivalOrderSourceDialog = $("#purchase-sourceDialog-arrivalOrder-content");
                        $arrivalOrderSourceDialog.dialog({
                            title: '入库单列表',
                            fit: true,
                            closed: false,
                            modal: true,
                            cache: false,
                            maximizable: true,
                            resizable: true,
                            href: 'purchase/arrivalorder/arrivalOrderSourcePage.do',
                            buttons: [
                                {
                                    text: '确定',
                                    handler: function () {

                                        var $referOrderDatagrid = $("#purchase-buyOrderDatagrid-arrivalOrderSourcePage");
                                        if ($referOrderDatagrid.size() == 0) {
                                            $arrivalOrderSourceDialog.dialog('close', true);
                                            return false;
                                        }
                                        var dataRow = $referOrderDatagrid.datagrid('getSelected');
                                        if (dataRow == null || dataRow.length == 0 || dataRow.id == null || dataRow.id == "") {
                                            $.messager.alert("提醒", "请选择入库单");
                                            return false;
                                        }
                                        $arrivalOrderSourceDialog.dialog('close', true);
                                        $("#purchase-panel-arrivalOrderPage").panel('refresh', 'purchase/arrivalorder/arrivalOrderReferAddPage.do?referid=' + dataRow.id);
                                    }
                                }
                            ],
                            onLoad: function () {
                                var $referOrderDatagrid = $("#purchase-buyOrderDatagrid-arrivalOrderSourcePage");
                                if ($referOrderDatagrid.size() == 0) {
                                    return false;
                                }
                                $referOrderDatagrid.datagrid({
                                    columns: [[
                                        {field: 'id', title: '编号', width: 100},
                                        {field: 'businessdate', title: '业务日期', width: 100},
                                        {field: 'businessdate', title: '业务日期', width: 80, sortable: true},
                                        {
                                            field: 'supplierid', title: '供应商', width: 150, sortable: true,
                                            formatter: function (value, rowData, rowIndex) {
                                                return rowData.suppliername;
                                            }
                                        },
                                        {
                                            field: 'buydeptid', title: '采购部门', width: 100, sortable: true,
                                            formatter: function (value, rowData, rowIndex) {
                                                return rowData.buydeptname;
                                            }
                                        },
                                        {
                                            field: 'buyuserid', title: '采购人员', width: 80,
                                            formatter: function (value, rowData, rowIndex) {
                                                return rowData.buyusername;
                                            }
                                        },
                                        {
                                            field: 'storageid', title: '入库仓库', width: 80, sortable: true,
                                            formatter: function (value, rowData, rowIndex) {
                                                return rowData.storagename;
                                            }
                                        },
                                        {field: 'addusername', title: '制单人', width: 100, align: 'left'},
                                        {field: 'addtime', title: '制单时间', width: 100, align: 'left'},
                                        {field: 'remark', title: '备注', width: 100, align: 'left'}
                                    ]],
                                    fit: true,
                                    method: 'post',
                                    rownumbers: true,
                                    pagination: true,
                                    idField: 'id',
                                    singleSelect: true,
                                    fitColumns: true,
                                    url: 'storage/showPurchaseEnterList.do',
                                    queryParams: queryJSON,
                                    onClickRow: function (index, data) {
                                        var $orderDetail = $("#purchase-buyOrderdetailDatagrid-arrivalOrderSourcePage");
                                        if ($orderDetail.size() == 0) {
                                            return false;
                                        }
                                        if (data.id == null || $.trim(data.id) == "") {
                                            return false;
                                        }
                                        $orderDetail.datagrid({
                                            url: 'purchase/arrivalorder/showArrivalOrderDetailUpReferList.do',
                                            queryParams: {
                                                billno: $.trim(data.id)
                                            }
                                        });
                                    }
                                });
                            },
                            onClose: function () {
                                $arrivalOrderSourceDialog.dialog("destroy");
                            }
                        });
                    }
                }
            ],
            onClose: function () {
                $arrivalOrderSourceQueryDialog.dialog("destroy");
            }
        });
    }

    function orderDetailAddDialog() {

        var flag = $("#purchase-form-arrivalOrderAddPage").form('validate');
        if (flag == false) {
            $.messager.alert("提醒", '请先完善采购进货单基本信息');
            return false;
        }
        var supplierid = $("#purchase-arrivalOrderAddPage-supplier").supplierWidget('getValue');
        if (supplierid == null || supplierid == '') {
            $.messager.alert("提醒", "请先选择供应商档案再进行添加商品信息");
            $("#purchase-arrivalOrderAddPage-supplier").focus();
            return false;
        }
        var businessdate = $("#purchase-arrivalOrderAddPage-businessdate").val();
        if (businessdate == null) {
            $.messager.alert("提醒", "请先选择业务日期");
            $("#purchase-arrivalOrderAddPage-businessdate").focus();
            return false;
        }

        $('<div id="purchase-arrivalOrderAddPage-dialog-DetailOper-content"></div>').appendTo('#purchase-arrivalOrderAddPage-dialog-DetailOper');
        var $DetailOper = $("#purchase-arrivalOrderAddPage-dialog-DetailOper-content");

        $DetailOper.dialog({
            title: '商品信息新增(按ESC退出)',
            width: 600,
            height: 440,
            closed: true,
            cache: false,
            modal: true,
            resizable: true,
            href: "purchase/arrivalorder/arrivalOrderDetailAddPage.do?supplierid=" + supplierid + "&businessdate=" + businessdate,
            onLoad: function () {
                $("#purchase-arrivalOrderDetail-goodsid").focus();
            },
            onClose: function () {
                $DetailOper.dialog("destroy");
            }
        });
        $DetailOper.dialog("open");
    }
    function orderDetailEditDialog(initdata) {

        $('<div id="purchase-arrivalOrderAddPage-dialog-DetailOper-content"></div>').appendTo('#purchase-arrivalOrderAddPage-dialog-DetailOper');
        var $DetailOper = $("#purchase-arrivalOrderAddPage-dialog-DetailOper-content");
        $DetailOper.dialog({
            title: '商品信息修改(按ESC退出)',
            width: 600,
            height: 440,
            closed: true,
            cache: false,
            modal: true,
            resizable: true,
            href: "purchase/arrivalorder/arrivalOrderDetailEditPage.do",
            onLoad: function () {
                try {
                    if (initdata != null && initdata.goodsid != null && initdata.goodsid != "") {
                        if ($("#purchase-form-arrivalOrderDetailEditPage").size() > 0) {
                            if (initdata.goodsInfo) {
                                $("#purchase-form-arrivalOrderDetailEditPage").form('load', initdata.goodsInfo);
                            }

                            $("#purchase-form-arrivalOrderDetailEditPage").form('load', initdata);

                            $("#purchase-arrivalOrderDetail-span-auxunitname").html(initdata.auxunitname);
                            $("#purchase-arrivalOrderDetail-span-unitname").html(initdata.unitname);
                            $("#purchase-arrivalOrderDetail-goodsid").val(initdata.goodsid);
                        }
                    }
                } catch (e) {
                }
                $("#purchase-arrivalOrderDetail-auxnum").focus();
                $("#purchase-arrivalOrderDetail-auxnum").select();

                formaterNumSubZeroAndDot();

                $("#purchase-form-arrivalOrderSourceDetailEditPage").form('validate');
            },
            onClose: function () {
                $DetailOper.dialog("destroy");
            }
        });
        $DetailOper.dialog("open");
    }
    function orderDetailViewDialog(initdata) {
        $('<div id="purchase-arrivalOrderAddPage-dialog-DetailOper-content"></div>').appendTo('#purchase-arrivalOrderAddPage-dialog-DetailOper');
        var $DetailOper = $("#purchase-arrivalOrderAddPage-dialog-DetailOper-content");
        $DetailOper.dialog({
            title: '查看',
            width: 600,
            height: 440,
            closed: true,
            cache: false,
            modal: true,
            resizable: true,
            href: "purchase/arrivalorder/arrivalOrderDetailViewPage.do",
            onLoad: function () {
                if (initdata != null && initdata.goodsid != null && initdata.goodsid != "") {
                    if ($("#purchase-form-arrivalOrderDetailViewPage").size() > 0) {
                        if (initdata.goodsInfo) {
                            $("#purchase-form-arrivalOrderDetailViewPage").form('load', initdata.goodsInfo);
                        }
                        $("#purchase-form-arrivalOrderDetailViewPage").form('load', initdata);

                        $("#purchase-arrivalOrderDetail-span-auxunitname").html(initdata.auxunitname);
                        $("#purchase-arrivalOrderDetail-span-unitname").html(initdata.unitname);
                        $("#purchase-arrivalOrderDetail-goodsid").val(initdata.goodsid);
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
        disableChoiceWidget();
        var $potable = $("#purchase-arrivalOrderAddPage-arrivalOrdertable");
        var data = $potable.datagrid('getRows');
        if (data == null || data.length == 0) {
            $potable.datagrid('reloadFooter', [
                {goodsid: '合计', amount: '0', taxprice: '0', notaxprice: '0', notaxamount: '0', taxamount: '0', tax: '0'}
            ]);
        }
        var unitnum = 0;
        var taxamount = 0;
        var notaxamount = 0;
        var tax = 0;
        var auxnum = 0;
        var auxunitname = "";
        var auxremainder = 0;
        for (var i = 0; i < data.length; i++) {
            if (data[i].unitnum) {
                unitnum = Number(unitnum) + Number(data[i].unitnum);
            }
            if (data[i].taxamount) {
                taxamount = Number(taxamount) + Number(data[i].taxamount);
            }
            if (data[i].notaxamount) {
                notaxamount = Number(notaxamount) + Number(data[i].notaxamount);
            }
            if (data[i].tax) {
                tax = Number(tax) + Number(data[i].tax);
            }
            if (data[i].auxnum) {
                auxnum = Number(auxnum) + Number(data[i].auxnum);
            }
            if (data[i].auxremainder) {
                auxremainder = Number(auxremainder) + Number(data[i].auxremainder);
            }
            if ((auxunitname == "" || auxunitname == null) && data[i].auxunitname) {
                auxunitname = data[i].auxunitname;
            }
        }
        unitnum = String(unitnum);
        taxamount = String(taxamount);
        notaxamount = String(notaxamount);
        tax = String(tax);
        auxnum = String(parseInt(auxnum)) + auxunitname + (auxremainder > 0 ? auxremainder : "");
        $potable.datagrid('reloadFooter', [
            {
                goodsid: '合计',
                unitnum: unitnum,
                taxamount: taxamount,
                notaxamount: notaxamount,
                auxnumdetail: auxnum,
                tax: tax
            }
        ]);
    }
    function checkGoodsDetailEmpty() {
        var flag = true;
        var $ordertable = $("#purchase-arrivalOrderAddPage-arrivalOrdertable");
        var data = $ordertable.datagrid('getRows');
        for (var i = 0; i < data.length; i++) {
            if (data[i].goodsid && data[i].goodsid != "") {
                flag = false;
                break;
            }
        }
        return flag;
    }

    function beginAddArrivalOrderDiscountDetail() {
        $('<div id="purchase-arrivalOrderAddPage-dialog-DetailOper-content"></div>').appendTo('#purchase-arrivalOrderAddPage-dialog-DetailOper');
        var $DetailOper = $("#purchase-arrivalOrderAddPage-dialog-DetailOper-content");
        $DetailOper.dialog({
            title: '进货折扣添加',
            width: 300,
            height: 250,
            collapsible: false,
            minimizable: false,
            maximizable: true,
            resizable: true,
            closed: true,
            cache: false,
            href: 'purchase/arrivalorder/showArrivalOrderDiscountAddPage.do?arrivalorderid=' + encodeURIComponent($("#purchase-backid-arrivalOrderAddPage").val()),
            onClose: function () {
                $DetailOper.dialog("destroy");
            },
            onLoad: function () {
                //$("#sales-order-discount").next('span').find('input').focus()
                //getNumberBox("sales-receipt-discount").focus();
            }
        });
        $DetailOper.dialog("open");
    }

    //禁用表单title
    function disableChoiceWidget() {
        var rows = $("#purchase-arrivalOrderAddPage-arrivalOrdertable").datagrid('getRows');
        var count = 0;
        for (var i = 0; i < rows.length; i++) {
            if (rows[i].goodsid && rows[i].goodsid != "") {
                count++;
            }
        }
        if (count > 0) {
            $("#purchase-arrivalOrderAddPage-supplier").supplierWidget("readonly", true);
            $("#purchase-arrivalOrderAddPage-storage").widget("disable");
        } else {
            $("#purchase-arrivalOrderAddPage-supplier").supplierWidget("readonly", false);
            $("#purchase-arrivalOrderAddPage-storage").widget("disable");
        }
    }
    var tableColJson = $("#purchase-arrivalOrderAddPage-arrivalOrdertable").createGridColumnLoad({
//        name: 'purchase_arrivalorder_detail',
        frozenCol: [[]],
        commonCol: [[
            {field: 'id', title: '编码', width: 70, hidden: true, isShow: true},
            {field: 'goodsid', title: '商品编码', width: 80, isShow: true,sortable:true},
            {
                field: 'name', title: '商品名称', width: 220, isShow: true,
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        return rowData.goodsInfo.name;
                    } else {
                        return value;
                    }
                }
            },
            {
                field: 'spell', title: '助记符', width: 80, aliascol: 'goodsid', hidden: true, align: 'left',
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != undefined) {
                        return rowData.goodsInfo.spell;
                    } else {
                        return "";
                    }
                }
            },
            {
                field: 'barcode', title: '条形码', width: 90, isShow: true,sortable:true,
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        return rowData.goodsInfo.barcode;
                    } else {
                        return value;
                    }
                }
            },
            {
                field: 'model', title: '规格型号', width: 80, isShow: true, hidden: true,
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        return rowData.goodsInfo.model;
                    } else {
                        return value;
                    }
                }
            },
            {
                field: 'brandName', title: '商品品牌', width: 60, isShow: true, hidden: true,
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        return rowData.goodsInfo.brandName;
                    } else {
                        return value;
                    }
                }
            },
            {
                field: 'boxnum', title: '箱装量', aliascol: 'goodsid', width: 45, align: 'right',
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null && rowData.goodsInfo.boxnum != null) {
                        return formatterBigNumNoLen(rowData.goodsInfo.boxnum);
                    } else if (value != null) {
                        return formatterBigNumNoLen(value);
                    }
                    else {
                        return "";
                    }
                }
            },
            {
                field: 'unitid', title: '单位', width: 35,
                formatter: function (value, row, index) {
                    return row.unitname;
                }
            },
            {
                field: 'unitnum', title: '数量', width: 60, align: 'right',sortable:true,
                formatter: function (value, row, index) {
                    return formatterBigNumNoLen(value);
                }
            },
            {
                field: 'taxprice', title: '单价', width: 60, align: 'right',sortable:true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'boxprice', title: '箱价', width: 60, aliascol: 'taxprice', align: 'right',sortable:true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'taxamount', title: '金额', width: 60, align: 'right',sortable:true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'notaxprice', title: '未税单价', width: 60, align: 'right',sortable:true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'noboxprice', title: '未税箱价', width: 60, aliascol: 'notaxprice', align: 'right',sortable:true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'notaxamount', title: '未税金额', width: 60, align: 'right',sortable:true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'oldtaxamount', title: '分摊前金额', width: 60, align: 'right',sortable:true,hidden:true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'oldnotaxamount', title: '分摊前未税金额', width: 60, align: 'right',sortable:true,hidden:true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'oldtax', title: '分摊前税额', width: 60, align: 'right',sortable:true,hidden:true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'changeamount', title: '费用金额', width: 60, align: 'right',sortable:true,hidden:true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {field: 'taxtypename', title: '税种', width: 80, hidden: true},
            {
                field: 'tax', title: '税额', width: 60, align: 'right',sortable:true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'auxunitid', title: '辅单位', width: 60, hidden: true,
                formatter: function (value, row, index) {
                    return row.auxunitname;
                }
            },
            {field: 'auxnumdetail', title: '辅数量', width: 60, align: 'right'},
            {field: 'batchno', title: '批次号', width: 60},
            {field: 'produceddate', title: '生产日期', width: 60},
            {field: 'deadline', title: '有效截止日期', width: 80},
            {field: 'remark', title: '备注', width: 150}
        ]]
    });
    function detailOnSortColumn(sort, order){
        var goodsInfoArr=["barcode"];
        var issort=false;
        if(sort==null || sort==""){
            return true;
        }
        var data = $("#purchase-arrivalOrderAddPage-arrivalOrdertable").datagrid("getData");
        var rows = data.rows;
        var dataArr = [];
        for(var i=0;i<rows.length;i++){
            if(rows[i].goodsid!=null && rows[i].goodsid!=""){
                dataArr.push(rows[i]);
            }
        }
        dataArr.sort(function(a,b){
            var atmp=0;
            var btmp=0;
            if($.inArray(sort,goodsInfoArr)>=0){
                console.log(sort);
                console.log(goodsInfoArr);
                var aGInfo=a.goodsInfo || {};
                var bGInfo=b.goodsInfo || {};
                atmp=aGInfo[sort];
                btmp=bGInfo[sort];

            }else{
                atmp = a[sort];
                btmp = b[sort];
            }
            if(atmp==null || btmp==null){
                return -1;
            }

            if($.isNumeric(atmp)){
                if(order=="asc"){
                    return Number(atmp)>Number(btmp)?1:-1
                }else{
                    return Number(atmp)<Number(btmp)?1:-1
                }
            }else{
                if(order=="asc"){
                    return atmp>btmp?1:-1
                }else{
                    return atmp<btmp?1:-1
                }
            }
        });
        $("#purchase-arrivalOrderAddPage-arrivalOrdertable").datagrid("loadData",{rows:dataArr,total:data.total});
        return true;
    };
    $(document).ready(function () {
        $("#purchase-panel-arrivalOrderPage").panel({
            href: arrivalOrder_url,
            cache: false,
            maximized: true,
            border: false
        });
        //按钮
        $("#purchase-buttons-arrivalOrderPage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/purchase/arrivalorder/arrivalOrderAddBtn.do">
                {
                    type: 'button-add',
                    handler: function () {
                        $("#purchase-panel-arrivalOrderPage").panel('refresh', 'purchase/arrivalorder/arrivalOrderAddPage.do');
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/arrivalorder/plannedOrderTempSave.do">
                {
                    type: 'button-hold',
                    handler: function () {
                        $.messager.confirm("提醒", "确定暂存该采购进货单信息？", function (r) {
                            if (r) {
                                $("#purchase-arrivalOrderAddPage-addType").val("temp");
                                var datarows = $("#purchase-arrivalOrderAddPage-arrivalOrdertable").datagrid('getRows');
                                if (datarows != null && datarows.length > 0) {
                                    $("#purchase-arrivalOrderAddPage-arrivalOrderDetails").val(JSON.stringify(datarows));
                                }
                                arrivalOrder_tempSave_form_submit();
                                $("#purchase-form-arrivalOrderAddPage").submit();
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/arrivalorder/plannedOrderRealSave.do">
                {
                    type: 'button-save',
                    handler: function () {

                        if (checkGoodsDetailEmpty()) {
                            $.messager.alert("提醒", "抱歉，请填写采购进货单商品信息");
                            return false;
                        }
                        $.messager.confirm("提醒", "确定保存该采购进货单信息？", function (r) {
                            if (r) {
                                $("#purchase-arrivalOrderAddPage-addType").val("real");
                                var datarows = $("#purchase-arrivalOrderAddPage-arrivalOrdertable").datagrid('getRows');
                                if (datarows != null && datarows.length > 0) {
                                    $("#purchase-arrivalOrderAddPage-arrivalOrderDetails").val(JSON.stringify(datarows));
                                }
                                arrivalOrder_realSave_form_submit();
                                $("#purchase-form-arrivalOrderAddPage").submit();
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/arrivalorder/arrivalOrderSaveAudit.do">
                {
                    type: 'button-saveaudit',
                    handler: function () {
                        $.messager.confirm("提醒", "确定保存并审核进货单信息？", function (r) {
                            if (r) {
                                $("#purchase-arrivalOrderAddPage-addType").val("real");
                                $("#purchase-arrivalOrderAddPage-saveaudit").val("saveaudit");
                                var datarows = $("#purchase-arrivalOrderAddPage-arrivalOrdertable").datagrid('getRows');
                                if (datarows != null && datarows.length > 0) {
                                    $("#purchase-arrivalOrderAddPage-arrivalOrderDetails").val(JSON.stringify(datarows));
                                }
                                arrivalOrder_realSave_form_submit();
                                $("#purchase-form-arrivalOrderAddPage").submit();
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/arrivalorder/arrivalOrderGiveUpBtn.do">
                {
                    type: 'button-giveup',//放弃
                    handler: function () {
                        var $polbuttons = $("#purchase-buttons-arrivalOrderPage");
                        var type = $polbuttons.buttonWidget("getOperType");
                        if (type == "add") {
                            var id = $("#purchase-backid-arrivalOrderAddPage").val();
                            if (id == "") {
                                tabsWindowTitle(pageListUrl);
                            } else {
                                $("#purchase-panel-arrivalOrderPage").panel('refresh', 'purchase/arrivalorder/arrivalOrderViewPage.do?id=' + id);
                            }
                        } else if (type == "edit") {
                            var id = $("#purchase-backid-arrivalOrderAddPage").val();
                            if (id == "") {
                                return false;
                            }
                            var flag = isLockData(id, "t_purchase_arrivalorder");
                            if (!flag) {
                                $.messager.alert("警告", "该数据正在被其他人操作，暂不能修改！");
                                return false;
                            }
                            $("#purchase-panel-arrivalOrderPage").panel('refresh', 'purchase/arrivalorder/arrivalOrderViewPage.do?id=' + id);
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/arrivalorder/deleteArrivalOrder.do">
                {
                    type: 'button-delete',
                    handler: function () {
                        var id = $("#purchase-backid-arrivalOrderAddPage").val();
                        if (id == "") {
                            return false;
                        }

                        $.messager.confirm("提醒", "是否删除该采购进货单信息？", function (r) {
                            if (r) {
                                loading("删除中..");
                                $.ajax({
                                    url: 'purchase/arrivalorder/deleteArrivalOrder.do?id=' + id,
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag) {
                                            $.messager.alert("提醒", "删除成功");
                                            var nextdata = $("#purchase-buttons-arrivalOrderPage").buttonWidget("removeData", "");
                                            if (null != nextdata && nextdata.id && nextdata.id != "") {
                                                $("#purchase-backid-arrivalOrderAddPage").val(nextdata.id);
                                                $("#purchase-panel-arrivalOrderPage").panel('refresh', 'purchase/arrivalorder/arrivalOrderEditPage.do?id=' + nextdata.id);
                                                arrivalOrder_RefreshDataGrid();
                                            } else {
                                                $("#purchase-backid-arrivalOrderAddPage").val("");
                                                //$("#purchase-panel-arrivalOrderPage").panel('refresh', 'purchase/arrivalorder/arrivalOrderAddPage.do');
                                                arrivalOrder_RefreshDataGrid();
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
                <security:authorize url="/purchase/arrivalorder/auditArrivalOrder.do">
                {
                    type: 'button-audit',
                    handler: function () {
                        var id = $("#purchase-backid-arrivalOrderAddPage").val();
                        if (id == "") {
                            return false;
                        }
                        if (checkGoodsDetailEmpty()) {
                            $.messager.alert("提醒", "抱歉，请填写采购进货单商品信息");
                            return false;
                        }
                        $.messager.confirm("提醒", "是否审核通过该采购进货单信息？", function (r) {
                            if (r) {
                                loading("审核中..");
                                $.ajax({
                                    url: 'purchase/arrivalorder/auditArrivalOrder.do?id=' + id,
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag) {
                                            //$("#purchase-arrivalOrderAddPage-status").val("3");

                                            //$("#purchase-buttons-arrivalOrderPage").buttonWidget("setDataID", {id:id, state:'3', type:'view'});
                                            $.messager.alert("提醒", "审核成功");
                                            arrivalOrder_RefreshDataGrid();
                                            $("#purchase-panel-arrivalOrderPage").panel('refresh', 'purchase/arrivalorder/arrivalOrderEditPage.do?id=' + id);
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
                <security:authorize url="/purchase/arrivalorder/oppauditArrivalOrder.do">
                {
                    type: 'button-oppaudit',
                    handler: function () {
                        var id = $("#purchase-backid-arrivalOrderAddPage").val();
                        if (id == "") {
                            return false;
                        }
                        var businessdate = $("#purchase-arrivalOrderAddPage-businessdate").val();
                        var flag = isDoneOppauditBillCaseAccounting(businessdate);
                        if (!flag) {
                            $.messager.alert("提醒", "业务日期不在会计区间内或未设置会计区间,不允许反审!");
                            return false;
                        }
                        $.messager.confirm("提醒", "是否反审该采购进货单信息？", function (r) {
                            if (r) {
                                loading("反核中..");
                                $.ajax({
                                    url: 'purchase/arrivalorder/oppauditArrivalOrder.do?id=' + id,
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag) {
                                            //$("#purchase-arrivalOrderAddPage-status").val("2");
                                            //$("#purchase-buttons-arrivalOrderPage").buttonWidget("setDataID", {id:id, state:'2', type:'edit'});
                                            $.messager.alert("提醒", "反审成功");
                                        } else {
                                            if (json.msg) {
                                                $.messager.alert("提醒", json.msg);
                                            } else {
                                                $.messager.alert("提醒", "反审失败");
                                            }
                                        }
                                        arrivalOrder_RefreshDataGrid();
                                        $("#purchase-panel-arrivalOrderPage").panel('refresh', 'purchase/arrivalorder/arrivalOrderEditPage.do?id=' + id);
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/arrivalorder/arrivalOrderWorkflow.do">
                {
                    type: 'button-workflow',
                    button: [
                        {
                            type: 'workflow-submit',
                            handler: function () {
                                $.messager.confirm("提醒", "是否提交采购进货单信息到工作流?", function (r) {
                                    if (r) {
                                        var id = $("#purchase-backid-buyOrderAddPage").val();
                                        if (id == "") {
                                            $.messager.alert("警告", "没有需要提交工作流的信息!");
                                            return false;
                                        }
                                        loading("提交中..");
                                        $.ajax({
                                            url: 'purchase/arrivalorder/submitArrivalOrderProcess.do',
                                            dataType: 'json',
                                            type: 'post',
                                            data: 'id=' + id,
                                            success: function (json) {
                                                loaded();
                                                if (json.flag == true) {
                                                    $.messager.alert("提醒", "提交成功!");
                                                    buyOrder_RefreshDataGrid();
                                                    $("#purchase-panel-buyOrderPage").panel("refresh", 'purchase/buyorder/arrivalOrderViewPage.do?id=' + id);
                                                }
                                                else {
                                                    if (json.msg != null || json.msg != "") {
                                                        $.messager.alert("提醒", "提交失败!" + json.msg);
                                                    }
                                                    else {
                                                        $.messager.alert("提醒", "提交失败!");
                                                    }
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
                                if (arrivalOrder_type == "handle") {
                                    $("#purchase-wfdialog-arrivalOrderPage").dialog({
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
                                var id = $("#purchase-backid-arrivalOrderAddPage").val();
                                if (id == "") {
                                    return false;
                                }
                                $("#purchase-wfdialog-arrivalOrderPage").dialog({
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
                                var id = $("#purchase-backid-arrivalOrderAddPage").val();
                                if (id == "") {
                                    return false;
                                }
                                $("#purchase-wfdialog-arrivalOrderPage").dialog({
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
                <security:authorize url="/purchase/arrivalorder/arrivalOrderRelation.do">
                {
                    type: 'button-relation',
                    button: [
                        {
                            type: 'relation-upper',
                            handler: function () {
                                noSourceReferHandler();
                            }
                        },
                        {
                            type: 'relation-upper-view',
                            handler: function () {
                                var idarr = getReferOrderidArr();
                                if (idarr.length == 0) {
                                    return false;
                                }
                                $('<div id="purchase-sourceViewDialog-arrivalOrder-content"></div>').appendTo('#purchase-sourceViewDialog-arrivalOrder');
                                var $arrivalOrderSourceViewDialog = $("#purchase-sourceViewDialog-arrivalOrder-content");
                                $arrivalOrderSourceViewDialog.dialog({
                                    title: '入库单列表',
                                    fit: true,
                                    closed: true,
                                    modal: true,
                                    cache: false,
                                    maximizable: true,
                                    resizable: true,
                                    onClose: function () {
                                        $arrivalOrderSourceViewDialog.dialog("destroy");
                                    }
                                });
                                $('<table id="purchase-arrivalReferOrderDatagrid-sourceViewDialog"></table>').appendTo("#purchase-sourceViewDialog-arrivalOrder-content");
                                var $arrivalReferOrderDatagrid = $('#purchase-arrivalReferOrderDatagrid-sourceViewDialog');
                                $arrivalReferOrderDatagrid.datagrid({
                                    columns: [[
                                        {field: 'id', title: '编号', width: 100},
                                        {field: 'businessdate', title: '业务日期', width: 100},
                                        {field: 'businessdate', title: '业务日期', width: 80, sortable: true},
                                        {
                                            field: 'supplierid', title: '供应商', width: 150, sortable: true,
                                            formatter: function (value, rowData, rowIndex) {
                                                return rowData.suppliername;
                                            }
                                        },
                                        {
                                            field: 'buydeptid', title: '采购部门', width: 100, sortable: true,
                                            formatter: function (value, rowData, rowIndex) {
                                                return rowData.buydeptname;
                                            }
                                        },
                                        {
                                            field: 'buyuserid', title: '采购人员', width: 80,
                                            formatter: function (value, rowData, rowIndex) {
                                                return rowData.buyusername;
                                            }
                                        },
                                        {
                                            field: 'storageid', title: '入库仓库', width: 80, sortable: true,
                                            formatter: function (value, rowData, rowIndex) {
                                                return rowData.storagename;
                                            }
                                        },
                                        {field: 'addusername', title: '制单人', width: 100, align: 'left'},
                                        {field: 'addtime', title: '制单时间', width: 100, align: 'left'},
                                        {field: 'remark', title: '备注', width: 100, align: 'left'}
                                    ]],
                                    fit: true,
                                    method: 'post',
                                    rownumbers: true,
                                    pagination: true,
                                    idField: 'id',
                                    singleSelect: true,
                                    fitColumns: true,
                                    url: 'storage/showPurchaseEnterList.do?idarrs=' + idarr.join(','),
                                    onDblClickRow: function (rowIndex, rowData) {
                                        if (rowData != null && rowData.id != null && rowData.id != "") {
                                            var basePath = $("#basePath").attr("href");
                                            top.addOrUpdateTab(basePath + 'storage/showPurchaseEnterViewPage.do?id=' + rowData.id, "采购入库单");
                                        }
                                    }

                                });
                                $arrivalOrderSourceViewDialog.dialog('open');
                            }
                        }
                    ]
                },
                </security:authorize>
                <security:authorize url="/purchase/arrivalorder/arrivalOrderBack.do">
                {
                    type: 'button-back',
                    handler: function (data) {
                        if (data != null && data.id != null && data.id != "") {
                            $("#purchase-backid-arrivalOrderAddPage").val(data.id);
                            $("#purchase-panel-arrivalOrderPage").panel('refresh', 'purchase/arrivalorder/arrivalOrderEditPage.do?id=' + data.id);
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/arrivalorder/arrivalOrderNext.do">
                {
                    type: 'button-next',
                    handler: function (data) {
                        if (data != null && data.id != null && data.id != "") {
                            $("#purchase-backid-arrivalOrderAddPage").val(data.id);
                            $("#purchase-panel-arrivalOrderPage").panel('refresh', 'purchase/arrivalorder/arrivalOrderEditPage.do?id=' + data.id);
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/arrivalorder/arrivalOrderPrintView.do">
                {
                    type: 'button-preview',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/arrivalorder/arrivalOrderPrint.do">
                {
                    type: 'button-print',
                    handler: function () {
                    }
                },
                </security:authorize>
                {}
            ],
            buttons: [
                <security:authorize url="/purchase/arrivalorder/showArrivalPurchaseMenuPage.do">
                {
                    id: 'button-relateMenu',
                    name:'运费分摊',
                    type:'menu',
                    iconCls:'button-copy',
                    button:[
                        <security:authorize url="/purchase/arrivalorder/showArrivalPurchaseSharePage.do">
                        {
                            id: 'purchase-change',
                            name: '分摊金额',
                            iconCls: 'button-audit',
                            handler: function () {
                                $.messager.confirm("提醒", "分摊后不能修改进货单金额，是否确认分摊？", function (r) {
                                    if(r){
                                        var id = $("#purchase-backid-arrivalOrderAddPage").val();
                                        $("#purchase-share-dialog").dialog({
                                            title: '采购运费分摊',
                                            width: 400,
                                            height: 200,
                                            closed: false,
                                            modal: true,
                                            cache: false,
                                            queryParams:{id:id},
                                            href: 'purchase/arrivalorder/showArrivalPurchaseSharePage.do',
                                            onLoad: function () {

                                            }
                                        });
                                    }
                                })
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/purchase/arrivalorder/showArrivalPurchaseShareLogPage.do">
                        {
                            id: 'purchase-view',
                            name: '查看分摊记录',
                            iconCls: 'button-view',
                            handler: function () {
                                var id = $("#purchase-backid-arrivalOrderAddPage").val();
                                $("#purchase-share-dialog").dialog({
                                    title: '分摊记录',
                                    width: 550,
                                    height: 400,
                                    closed: false,
                                    modal: true,
                                    cache: false,
                                    queryParams:{id:id},
                                    href: 'purchase/arrivalorder/showArrivalPurchaseShareLogPage.do',
                                    onLoad: function () {

                                    }
                                });
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/purchase/arrivalorder/cancelArrivalPurchaseShare.do">
                        {
                            id:'purchase-cancaldispense',
                            name:'取消运费分摊',
                            iconCls:'button-delete',
                            handler: function(){
                                var id = $("#purchase-backid-arrivalOrderAddPage").val();
                                loading("取消中...");
                                $.ajax({
                                    url:'purchase/arrivalorder/cancelArrivalPurchaseShare.do?id='+id,
                                    dataType:'json',
                                    type:'post',
                                    success:function(json){
                                        loaded();
                                        if(json.flag){
                                            $.messager.alert("提醒","取消分摊成功");
                                            $("#purchase-panel-arrivalOrderPage").panel('refresh', 'purchase/arrivalorder/arrivalOrderEditPage.do?id=' + id);

                                        }else{
                                            $.messager.alert("提醒","取消分摊出错");
                                        }
                                    },
                                    error:function(){
                                        loaded();
                                        $.messager.alert("错误","取消分摊出错");
                                    }
                                });
                            }
                        },
                        </security:authorize>
                    ]
                }
                </security:authorize>
            ],
            layoutid: 'purchase-arrivalOrderPage-layout',
            model: 'bill',
            type: 'view',
            taburl: pageListUrl,
            id: '${id}',
            datagrid: 'purchase-table-arrivalOrderListPage',
            tname: 't_purchase_arrivalorder'
        });

        $(document).bind('keydown', 'esc', function () {
            $("#purchase-arrivalOrderDetail-remark").focus();
            if ($("#purchase-arrivalOrderAddPage-dialog-DetailOper-content").size() > 0) {
                $("#purchase-arrivalOrderAddPage-dialog-DetailOper-content").dialog("close");
            }
        });
        $(document).bind('keydown', 'ctrl+enter', function () {
            $("#purchase-arrivalOrderDetail-remark").focus();
            $("#purchase-arrivalOrderDetailAddPage-addSaveGoOn").focus();
            $("#purchase-arrivalOrderDetailEditPage-editSave").focus();
            setTimeout(function () {
                $("#purchase-arrivalOrderDetailAddPage-addSaveGoOn").trigger("click");
                $("#purchase-arrivalOrderDetailEditPage-editSave").trigger("click");
            }, 200);
            return false;
        });
        $(document).bind('keydown', '+', function () {
            $("#purchase-arrivalOrderDetail-remark").focus();
            $("#purchase-arrivalOrderDetailAddPage-addSaveGoOn").focus();
            $("#purchase-arrivalOrderDetailEditPage-editSave").focus();
            setTimeout(function () {
                $("#purchase-arrivalOrderDetailAddPage-addSaveGoOn").trigger("click");
                $("#purchase-arrivalOrderDetailEditPage-editSave").trigger("click");
            }, 200);
            return false;
        });
    });
    function updateDataGridPrintimes(id) {
        var thepage = tabsWindowURL(pageListUrl);
        if (thepage == null) {
            return false;
        }
        var thegrid = thepage.$("#purchase-table-arrivalOrderListPage");
        if (thegrid == null || thegrid.size() == 0) {
            thegrid.datagrid('reload');
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
            id: "listPage-ArrivalOrder-dialog-print",
            code: "purchase_arrialorder",
            url_preview: "print/purchase/arrivalOrderPrintView.do",
            url_print: "print/purchase/arrivalOrderPrint.do",
            btnPreview: "button-preview",
            btnPrint: "button-print",
            getData: function (tableId, printParam,a1,a2) {
                var id = $("#purchase-backid-arrivalOrderAddPage").val();
                if (id == "") {
                    $.messager.alert("警告", "找不到要打印的信息!");
                    return false;
                }
                a2.printlimit = $("#purchase-arrivalOrderAddPage-printlimit").val() || "1";

                var status = $("#purchase-arrivalOrderAddPage-status").val() || "";
                if (status != '3' && status != '4') {
                    $.messager.alert("提醒", "此进货单不可打印");
                    return false;
                }
                printParam.idarrs = id;
                var printtimes = $("#purchase-arrivalOrderAddPage-printtimes").val() || 0;
                if (printtimes > 0)
                    printParam.printIds = [id];
                return true;
            },
            onPrintSuccess: function (option) {
                updateDataGridPrintimes($("#purchase-backid-arrivalOrderAddPage").val());
            },
            printAfterHandler: function (option, printParam) {
                var printtimes = $("#purchase-arrivalOrderAddPage-printtimes").val();
                $("#purchase-arrivalOrderAddPage-printtimes").val(printtimes + 1);
                if (0 != printLimit) {
                    $("#purchase-buttons-arrivalOrderPage").buttonWidget("disableMenuItem", "button-print");
                }
            }
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
