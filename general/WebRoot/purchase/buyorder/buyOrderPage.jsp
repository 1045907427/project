<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>采购</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>
<body>
<input type="hidden" id="purchase-backid-buyOrderAddPage" value="${id }"/>
<input type="hidden" id="purchase-referPlanOrderid-buyOrderAddPage"/>
<div id="purchase-buyOrderPage-layout" class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="purchase-buttons-buyOrderPage"></div>
    </div>
    <div data-options="region:'center'">
        <div class="easyui-panel" data-options="fit:true" id="purchase-panel-buyOrderPage">
        </div>
    </div>
</div>

<div style="display:none">
    <div id="purchase-wfdialog-buyOrderPage"></div>
    <div id="purchase-buyOrderPage-dialog-planorderAnalysis"></div>

    <div id="purchase-sourceQueryDialog-buyOrder"></div>
    <div id="purchase-sourceDialog-buyOrder"></div>
    <div id="purchase-sourceViewDialog-buyOrder">
        <%--<table id="purchase-planOrderDatagrid-sourceViewDialog"></table> --%>
    </div>

    <div id="purchase-buyOrderAddPage-dialog-DetailOper"></div>
    <div id="purchase-goods-history-price"></div>
</div>
<div style="display:none">
    <form action="" id="purchase-form-buyOrderDetailExport" method="post">
        <input type="hidden" id="purchase-form-buyOrderDetailExport-id" name="id"/>
    </form>
    <a href="javaScript:void(0);" id="report-export-buyOrderListPageDetail" style="display: none">导出</a>
</div>
<script type="text/javascript">
    var buyOrder_type = '${type}';
    buyOrder_type = $.trim(buyOrder_type.toLowerCase());
    if (buyOrder_type == "") {
        buyOrder_type = 'add';
    }
    var buyOrder_url = "purchase/buyorder/buyOrderAddPage.do";
    if (buyOrder_type == "view" || buyOrder_type == "show" || buyOrder_type == "handle") {
        buyOrder_url = "purchase/buyorder/buyOrderEditPage.do?id=${id}";
    }
    if (buyOrder_type == "edit") {
        buyOrder_url = "purchase/buyorder/buyOrderEditPage.do?id=${id}";
    }
    if (buyOrder_type == "copy") {
        buyOrder_url = "purchase/buyorder/buyOrderCopyPage.do?id=${id}";
    }
    var pageListUrl = "/purchase/buyorder/buyOrderListPage.do";
    function buyOrder_tempSave_form_submit() {
        $("#purchase-form-buyOrderAddPage").form({
            onSubmit: function () {
                loading("提交中..");
            },
            success: function (data) {
                loaded();
                var json = $.parseJSON(data);
                if (json.flag == true) {
                    buyOrder_RefreshDataGrid();
                    $.messager.alert("提醒", "暂存成功");
                    $("#purchase-backid-buyOrderAddPage").val(json.backid);
                    if (json.opertype && json.opertype == "add") {
                        $("#purchase-buttons-buyOrderPage").buttonWidget("addNewDataId", json.backid);
                        $("#purchase-panel-buyOrderPage").panel('refresh', 'purchase/buyorder/buyOrderEditPage.do?id=' + json.backid);
                    } else {
                        $("#purchase-buyOrderAddPage-status").val("1");
                    }
                }
                else {
                    $.messager.alert("提醒", "暂存失败");
                }
            }
        });
    }

    //变更所有商品要求到货日期
    function changeArriveDate() {
        var arriveDate = $("#purchase-buyOrderAddPage-arrivedate").val();
        var dataRows = $("#purchase-buyOrderAddPage-buyOrdertable").datagrid('getRows');
        for (var i = 0; i < dataRows.length; i++) {
            var row = dataRows[i];
            if (row.goodsid != undefined && row.goodsid != "") {
                row.arrivedate = arriveDate;
                $("#purchase-buyOrderAddPage-buyOrdertable").datagrid('updateRow', {index: i, row: row});
            }
        }

    }

    function buyOrder_realSave_form_submit() {
        $("#purchase-form-buyOrderAddPage").form({
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
                    buyOrder_RefreshDataGrid();
                    $.messager.alert("提醒", "保存成功");
                    $("#purchase-backid-buyOrderAddPage").val(json.backid);
                    if (json.opertype && json.opertype == "add") {
                        $("#purchase-buttons-buyOrderPage").buttonWidget("addNewDataId", json.backid);
                        $("#purchase-panel-buyOrderPage").panel('refresh', 'purchase/buyorder/buyOrderEditPage.do?id=' + json.backid);
                    } else {
                        $("#purchase-buyOrderAddPage-status").val("2");
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
    function buyOrder_RefreshDataGrid() {
        try {
            tabsWindowURL(pageListUrl).$("#purchase-table-buyOrderListPage").datagrid('reload');
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
        var $dtable = $("#purchase-buyOrderAddPage-buyOrdertable");
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
    function getReferOrderidArr() {
        var idarr = new Array();
        var dataRow = $("#purchase-buyOrderAddPage-buyOrdertable").datagrid('getRows');
        if (dataRow && dataRow.length > 0) {
            for (var i = 0; i < dataRow.length; i++) {
                if (dataRow[i].billno && dataRow[i].billno != "") {
                    idarr.push(dataRow[i].billno);
                }
            }
        }
        return idarr;
    }
    function choiceReferOrderToGrid(dataRows) {
        if (dataRows == null || dataRows.length == 0) {
            return false;
        }
        for (var i = 0; i < dataRows.length; i++) {
            appendToOrderDetailGrid(dataRows[i]);
        }
    }

    function formatBuyOrderBill(datarow) {
        if (dataRows == null || dataRows.length == 0) {
            return false;
        }
    }

    function noSourceReferHandler() {
        $('<div id="purchase-sourceQueryDialog-buyOrder-content"></div>').appendTo('#purchase-sourceQueryDialog-buyOrder');
        var $buyOrderSourceQueryDialog = $("#purchase-sourceQueryDialog-buyOrder-content");
        $buyOrderSourceQueryDialog.dialog({ //查询参照上游
            title: '采购计划订单查询',
            width: 400,
            height: 260,
            closed: false,
            modal: true,
            cache: false,
            href: 'purchase/buyorder/buyOrderSourceQueryPage.do',
            buttons: [
                {

                    text: '查询',
                    handler: function () {
                        var $SourceQuery = $("#purchase-form-buyOrderSourceQueryPage");
                        var queryJSON = {};
                        if ($SourceQuery && $SourceQuery.size() > 0) {
                            queryJSON = $("#purchase-form-buyOrderSourceQueryPage").serializeJSON();
                        }
                        $buyOrderSourceQueryDialog.dialog('close', true);
                        $('<div id="purchase-sourceDialog-buyOrder-content"></div>').appendTo('#purchase-sourceDialog-buyOrder');
                        var $buyOrderSourceDialog = $("#purchase-sourceDialog-buyOrder-content");
                        $buyOrderSourceDialog.dialog({
                            title: '采购计划单列表',
                            fit: true,
                            closed: false,
                            modal: true,
                            cache: false,
                            maximizable: true,
                            resizable: true,
                            href: 'purchase/buyorder/buyOrderSourcePage.do?choicerefer=1',
                            buttons: [
                                {
                                    text: '确定',
                                    handler: function () {

                                        var $planOrderDatagrid = $("#purchase-planOrderDatagrid-buyOrderSourcePage");
                                        if ($planOrderDatagrid.size() == 0) {
                                            $buyOrderSourceDialog.dialog('close', true);
                                            return false;
                                        }
                                        var dataRow = $planOrderDatagrid.datagrid('getSelected');
                                        if (dataRow == null || dataRow.length == 0 || dataRow.id == null || dataRow.id == "") {
                                            $.messager.alert("提醒", "请选择采购计划单");
                                            return false;
                                        }
                                        $buyOrderSourceDialog.dialog('close', true);
                                        $("#purchase-panel-buyOrderPage").panel('refresh', 'purchase/buyorder/buyOrderReferAddPage.do?referid=' + dataRow.id);
                                    }
                                }
                            ],
                            onLoad: function () {
                                var $planOrderDatagrid = $("#purchase-planOrderDatagrid-buyOrderSourcePage");
                                if ($planOrderDatagrid.size() == 0) {
                                    return false;
                                }
                                $planOrderDatagrid.datagrid({
                                    columns: [[
                                        {field: 'id', title: '编号', width: 80},
                                        {field: 'businessdate', title: '业务日期', width: 50},
                                        {field: 'applydeptid', title: '申请部门编号', width: 30, align: 'left', hidden: true},
                                        {field: 'applyuserid', title: '申请人编号', width: 20, align: 'left', hidden: true},
                                        {field: 'applydeptname', title: '申请部门', width: 30, align: 'left'},
                                        {field: 'applyusername', title: '申请人', width: 20, align: 'left'},
                                        {field: 'addusername', title: '制单人', width: 20, align: 'left'},
                                        {field: 'addtime', title: '制单时间', width: 50, align: 'left'},
                                        {field: 'suppliername', title: '供应商', width: 60, align: 'left'},
                                        {field: 'buydeptname', title: '采购部门', width: 30, align: 'left'},
                                        {field: 'remark', title: '备注', width: 80, align: 'left'}
                                    ]],
                                    fit: true,
                                    method: 'post',
                                    rownumbers: true,
                                    pagination: true,
                                    idField: 'id',
                                    singleSelect: true,
                                    fitColumns: true,
                                    url: 'purchase/planorder/showPlannedOrderForReferPageList.do',
                                    queryParams: queryJSON,
                                    onClickRow: function (index, data) {
                                        var $orderDetail = $("#purchase-planOrderdetailDatagrid-buyOrderSourcePage");
                                        if ($orderDetail.size() == 0) {
                                            return false;
                                        }
                                        if (data.id == null || $.trim(data.id) == "") {
                                            return false;
                                        }
                                        $orderDetail.datagrid({
                                            url: 'purchase/planorder/showPlannedOrderDetailReferList.do',
                                            queryParams: {
                                                orderid: $.trim(data.id)
                                            }
                                        });
                                    }
                                });
                            },
                            onClose: function () {
                                $buyOrderSourceDialog.dialog("destroy");
                            }
                        });
                    }
                }
            ],
            onClose: function () {
                $buyOrderSourceQueryDialog.dialog("destroy");
            }
        });
    }

    function sourceReferHandler() {
        var billno = $("#purchase-buyOrderAddPage-referbillno").val();
        if (billno == null || $.trim(billno) == "") {
            return false;
        }
        billno = $.trim(billno);
        $("#purchase-sourceDialog-buyOrder").dialog({
            title: '采购计划单列表',
            fit: true,
            closed: false,
            modal: true,
            cache: false,
            maximizable: true,
            resizable: true,
            href: 'purchase/buyorder/buyOrderSourcePage.do',
            buttons: [
                {
                    text: '确定',
                    handler: function () {
                        var $planOrderdetailDatagrid = $("#purchase-planOrderdetailDatagrid-buyOrderSourcePage");
                        if ($planOrderdetailDatagrid.size() == 0) {
                            return false;
                        }
                        var dataRows = $planOrderdetailDatagrid.datagrid('getChecked');
                        if (dataRows == null || dataRows.length == 0) {
                            $.messager.alert("提醒", "请选择采购计划单明细记录");
                            return false;
                        }
                        choiceReferOrderToGrid(dataRows);

                        $("#purchase-sourceDialog-buyOrder").dialog('close', true);
                    }
                }
            ],
            onLoad: function () {
                var $planOrderDatagrid = $("#purchase-planOrderDatagrid-buyOrderSourcePage");
                if ($planOrderDatagrid.size() == 0) {
                    return false;
                }
                $planOrderDatagrid.datagrid({
                    columns: [[
                        {field: 'id', title: '编号', width: 100},
                        {field: 'businessdate', title: '业务日期', width: 100},
                        {field: 'applydeptid', title: '申请部门', width: 100, align: 'left'},
                        {field: 'applyuserid', title: '申请人', width: 100, align: 'left'},
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
                    url: 'purchase/planorder/showPlannedOrderPageList.do',
                    queryParams: {id: billno},
                    onClickRow: function (index, data) {
                        var $orderDetail = $("#purchase-planOrderdetailDatagrid-buyOrderSourcePage");
                        if ($orderDetail.size() == 0) {
                            return false;
                        }
                        if (data.id == null || $.trim(data.id) == "") {
                            return false;
                        }
                        $orderDetail.datagrid({
                            url: 'purchase/planorder/showBuyOrderReferDetailList.do',
                            queryParams: {
                                orderid: $.trim(data.id)
                            }
                        });
                    }
                });
            }
        });
    }


    function orderDetailAddDialog() {

        var flag = $("#purchase-form-buyOrderAddPage").form('validate');
        if (flag == false) {
            $.messager.alert("提醒", '请先完善采购订单基本信息');
            return false;
        }
        var supplierid = $("#purchase-buyOrderAddPage-supplier").supplierWidget('getValue');
        if (supplierid == null || supplierid == '') {
            $.messager.alert("提醒", "请先选择供应商档案再进行添加商品信息");
            $("#purchase-buyOrderAddPage-supplier").focus();
            return false;
        }

        var businessdate = $("#purchase-buyOrderAddPage-businessdate").val();
        if (businessdate == null) {
            $.messager.alert("提醒", "请先选择业务日期");
            $("#purchase-buyOrderAddPage-businessdate").focus();
            return false;
        }
        $('<div id="purchase-buyOrderAddPage-dialog-DetailOper-content"></div>').appendTo('#purchase-buyOrderAddPage-dialog-DetailOper');
        var $DetailOper = $("#purchase-buyOrderAddPage-dialog-DetailOper-content");
        $DetailOper.dialog({
            title: '商品信息新增(按ESC退出)',
            width: 600,
            height: 420,
            closed: true,
            cache: false,
            modal: true,
            resizable: true,
            href: "purchase/buyorder/buyOrderDetailAddPage.do?supplierid=" + supplierid + "&businessdate=" + businessdate,
            onLoad: function () {
                $("#purchase-buyOrderDetail-goodsid").focus();
            },
            onClose: function () {
                $DetailOper.dialog("destroy");
            }
        });
        $DetailOper.dialog("open");
    }
    function orderDetailEditDialog(initdata) {
        $('<div id="purchase-buyOrderAddPage-dialog-DetailOper-content"></div>').appendTo('#purchase-buyOrderAddPage-dialog-DetailOper');
        var $DetailOper = $("#purchase-buyOrderAddPage-dialog-DetailOper-content");
        $DetailOper.dialog({
            title: '商品信息修改(按ESC退出)',
            width: 600,
            height: 420,
            closed: true,
            cache: false,
            modal: true,
            resizable: true,
            href: "purchase/buyorder/buyOrderDetailEditPage.do",
            onLoad: function () {
                try {
                    if (initdata != null && initdata.goodsid != null && initdata.goodsid != "") {
                        if ($("#purchase-form-buyOrderDetailEditPage").size() > 0) {
                            if (initdata.goodsInfo) {
                                $("#purchase-buyOrderDetail-boxnum").val(formatterBigNumNoLen(initdata.goodsInfo.boxnum));
                                $("#purchase-buyOrderDetail-brand").val(initdata.goodsInfo.brandName);
                                $("#purchase-buyOrderDetail-model").val(initdata.goodsInfo.model);
                                $("#purchase-buyOrderDetail-barcode").val(initdata.goodsInfo.barcode);
                                $("#purchase-buyOrderDetail-goodsname").val(initdata.goodsInfo.name);
                            }
                            $("#purchase-form-buyOrderDetailEditPage").form('load', initdata);
                            $("#purchase-buyOrderDetail-span-auxunitname").html(initdata.auxunitname);
                            $("#purchase-buyOrderDetail-span-unitname").html(initdata.unitname);
                            $("#purchase-buyOrderDetail-goodsid").val(initdata.goodsid);
                        }
                    }
                } catch (e) {
                }

                $("#purchase-buyOrderDetail-auxnum").focus();
                $("#purchase-buyOrderDetail-auxnum").select();

                formaterNumSubZeroAndDot();

                $("#purchase-form-buyOrderDetailEditPage").form('validate');
            },
            onClose: function () {
                $DetailOper.dialog("destroy");
            }
        });
        $DetailOper.dialog("open");
    }
    function orderDetailViewDialog(initdata) {
        $('<div id="purchase-buyOrderAddPage-dialog-DetailOper-content"></div>').appendTo('#purchase-buyOrderAddPage-dialog-DetailOper');
        var $DetailOper = $("#purchase-buyOrderAddPage-dialog-DetailOper-content");
        $DetailOper.dialog({
            title: '查看',
            width: 600,
            height: 420,
            closed: true,
            cache: false,
            modal: true,
            resizable: true,
            href: "purchase/buyorder/buyOrderDetailViewPage.do",
            onLoad: function () {
                if (initdata != null && initdata.goodsid != null && initdata.goodsid != "") {
                    if ($("#purchase-form-buyOrderDetailViewPage").size() > 0) {
                        if (initdata.goodsInfo) {
                            $("#purchase-buyOrderDetail-boxnum").val(formatterBigNumNoLen(initdata.goodsInfo.boxnum));
                            $("#purchase-buyOrderDetail-brand").val(initdata.goodsInfo.brandName);
                            $("#purchase-buyOrderDetail-model").val(initdata.goodsInfo.model);
                            $("#purchase-buyOrderDetail-barcode").val(initdata.goodsInfo.barcode);
                            $("#purchase-buyOrderDetail-goodsname").val(initdata.goodsInfo.name);
                        }
                        $("#purchase-form-buyOrderDetailViewPage").form('load', initdata);
                        $("#purchase-buyOrderDetail-span-auxunitname").html(initdata.auxunitname);
                        $("#purchase-buyOrderDetail-span-unitname").html(initdata.unitname);
                        $("#purchase-buyOrderDetail-goodsid").val(initdata.goodsid);
                    }
                }

                formaterNumSubZeroAndDot();

                $("#purchase-form-buyOrderDetailViewPage").form('validate');
            },
            onClose: function () {
                $DetailOper.dialog("destroy");
            }
        });
        $DetailOper.dialog("open");
    }
    function checkAfterAddGoods(goodsid) {
        if (goodsid == null || goodsid == "") {
            return false;
        }
        var $ordertable = $("#purchase-buyOrderAddPage-buyOrdertable");
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
    function footerReCalc() {
        disableChoiceWidget();
        var $potable = $("#purchase-buyOrderAddPage-buyOrdertable");
        var data = $potable.datagrid('getRows');
        var unitnum = 0;
        var taxamount = 0;
        var notaxamount = 0;
        var tax = 0;
        var auxnum = 0;
        var auxunitname = "";
        var auxremainder = 0;
        var totalboxweight = 0;
        var totalboxvolume = 0;
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
            if (data[i].totalboxweight) {
                totalboxweight = Number(totalboxweight) + Number(data[i].totalboxweight);
            }
            if (data[i].totalboxvolume) {
                totalboxvolume = Number(totalboxvolume) + Number(data[i].totalboxvolume);
            }
        }
        unitnum = String(unitnum);
        taxamount = String(taxamount);
        notaxamount = String(notaxamount);
        tax = String(tax);
        auxnum = String(parseInt(auxnum)) + auxunitname + (auxremainder > 0 ? auxremainder : "");
        totalboxweight = String(totalboxweight);
        totalboxvolume = String(totalboxvolume);
        $potable.datagrid('reloadFooter', [
            {
                goodsid: '合计',
                unitnum: unitnum,
                taxamount: taxamount,
                notaxamount: notaxamount,
                auxnumdetail: auxnum,
                tax: tax,
                totalboxweight: totalboxweight,
                totalboxvolume: totalboxvolume
            }
        ]);
    }

    //禁用表单title
    function disableChoiceWidget() {
        var rows = $("#purchase-buyOrderAddPage-buyOrdertable").datagrid('getRows');
        var count = 0;
        for (var i = 0; i < rows.length; i++) {
            if (rows[i].goodsid && rows[i].goodsid != "") {
                count++;
            }
        }
        if (count > 0) {
            $("#purchase-buyOrderAddPage-supplier").supplierWidget("readonly", true);
        } else {
            $("#purchase-buyOrderAddPage-supplier").supplierWidget("readonly", false);
        }
    }
    function checkGoodsDetailEmpty() {
        var flag = true;
        var $ordertable = $("#purchase-buyOrderAddPage-buyOrdertable");
        var data = $ordertable.datagrid('getRows');
        for (var i = 0; i < data.length; i++) {
            if (data[i].goodsid && data[i].goodsid != "") {
                flag = false;
                break;
            }
        }
        return flag;
    }

    //历史价格查看
    function showHistoryGoodsPrice() {
        var row = $("#purchase-buyOrderAddPage-buyOrdertable").datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        var goodsid = row.goodsid;
        var goodsname = row.goodsInfo.name;

        $("#purchase-goods-history-price").dialog({
            title: '商品[' + goodsid + ']' + goodsname + ' 历史价格表',
            width: 600,
            height: 400,
            closed: false,
            modal: true,
            cache: false,
            maximizable: true,
            resizable: true,
            href: 'purchase/buyorder/showPurchaseHistoryGoodsPricePage.do',
            queryParams: {goodsid: goodsid}
        });
    }

    var tableColJson = $("#purchase-buyOrderAddPage-buyOrdertable").createGridColumnLoad({
        // name: 'purchase_buyorder_detail',
        frozenCol: [[]],
        commonCol: [[
            {field: 'id', title: '编码', width: 60, hidden: true, isShow: true},
            {field: 'goodsid', title: '商品编码', width: 80, isShow: true,sortable:true},
            {
                field: 'name', title: '商品名称', width: 220, isShow: true, aliascol: 'goodsname',
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
                    if (rowData.goodsInfo != null) {
                        return formatterBigNumNoLen(rowData.goodsInfo.boxnum);
                    } else {
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
                field: 'notaxprice', title: '未税单价', width: 80, align: 'right',sortable:true,
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
            {field: 'taxtypename', title: '税种', width: 80},
            {
                field: 'tax', title: '税额', width: 80, align: 'right',sortable:true,
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
            {field: 'auxnumdetail', title: '辅数量', width: 80, align: 'right'},
            {
                field: 'totalboxweight', title: '重量（千克）', width: 80, align: 'right',
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'totalboxvolume', title: '体积（立方米）', width: 90, align: 'right',
                formatter: function (value, row, index) {
                    if (value != undefined) {
                        return Number(value).toFixed(3);
                    }
                }
            },
            {field: 'arrivedate', title: '要求到货日期', width: 80},
            {field: 'remark', title: '备注', width: 80}
        ]]
    });

    function detailOnSortColumn(sort, order){
        var goodsInfoArr=["barcode"];
        var issort=false;
        if(sort==null || sort==""){
            return true;
        }
        var data = $("#purchase-buyOrderAddPage-buyOrdertable").datagrid("getData");
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
        $("#purchase-buyOrderAddPage-buyOrdertable").datagrid("loadData",{rows:dataArr,total:data.total});
        return true;
    };
    $(document).ready(function () {
        $("#purchase-panel-buyOrderPage").panel({
            href: buyOrder_url,
            cache: false,
            maximized: true,
            border: false
        });
        //按钮
        $("#purchase-buttons-buyOrderPage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/purchase/buyorder/buyOrderAddBtn.do">
                {
                    type: 'button-add',
                    handler: function () {
                        $("#purchase-panel-buyOrderPage").panel('refresh', 'purchase/buyorder/buyOrderAddPage.do');
                    }
                },
                </security:authorize>
                <%--				<security:authorize url="/purchase/buyorder/buyOrderEditBtn.do">--%>
                <%--				{--%>
                <%--					type:'button-edit',--%>
                <%--					handler: function(){--%>
                <%--						var id = $("#purchase-backid-buyOrderAddPage").val();--%>
                <%--						if(id == ""){--%>
                <%--							return false;--%>
                <%--						}--%>
                <%--						$("#purchase-panel-buyOrderPage").panel('refresh', 'purchase/buyorder/buyOrderEditPage.do?id='+id);--%>
                <%--					}--%>
                <%--				},--%>
                <%--				</security:authorize>--%>
                <security:authorize url="/purchase/buyorder/buyOrderTempSave.do">
                {
                    type: 'button-hold',
                    handler: function () {
                        $.messager.confirm("提醒", "确定暂存该采购订单信息？", function (r) {
                            if (r) {
                                $("#purchase-buyOrderAddPage-addType").val("temp");
                                var datarows = $("#purchase-buyOrderAddPage-buyOrdertable").datagrid('getRows');
                                if (datarows != null && datarows.length > 0) {
                                    $("#purchase-buyOrderAddPage-buyOrderDetails").val(JSON.stringify(datarows));
                                }
                                buyOrder_tempSave_form_submit();
                                $("#purchase-form-buyOrderAddPage").submit();
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/buyorder/buyOrderRealSave.do">
                {
                    type: 'button-save',
                    handler: function () {

                        if (checkGoodsDetailEmpty()) {
                            $.messager.alert("提醒", "抱歉，请填写采购订单商品信息");
                            return false;
                        } else {
                            $("#purchase-buyOrderAddPage-addType").val("real");
                            var datarows = $("#purchase-buyOrderAddPage-buyOrdertable").datagrid('getRows');
                            if (datarows != null && datarows.length > 0) {
                                $("#purchase-buyOrderAddPage-buyOrderDetails").val(JSON.stringify(datarows));
                            }
                            buyOrder_realSave_form_submit();
                            $("#purchase-form-buyOrderAddPage").submit();
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/buyorder/buyOrderGiveUpBtn.do">
                {
                    type: 'button-giveup',//放弃
                    handler: function () {
                        var $polbuttons = $("#purchase-buttons-buyOrderPage");
                        var type = $polbuttons.buttonWidget("getOperType");
                        if (type == "add") {
                            var id = $("#purchase-backid-buyOrderAddPage").val();
                            if (id == "") {
                                tabsWindowTitle(pageListUrl);
                            } else {
                                $("#purchase-panel-buyOrderPage").panel('refresh', 'purchase/buyorder/buyOrderEditPage.do?id=' + id);
                            }
                        } else if (type == "edit") {
                            var id = $("#purchase-backid-buyOrderAddPage").val();
                            if (id == "") {
                                return false;
                            }
                            var flag = isLockData(id, "t_purchase_buyorder");
                            if (!flag) {
                                $.messager.alert("警告", "该数据正在被其他人操作，暂不能修改！");
                                return false;
                            }
                            $("#purchase-panel-buyOrderPage").panel('refresh', 'purchase/buyorder/buyOrderEditPage.do?id=' + id);
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/buyorder/deleteBuyOrder.do">
                {
                    type: 'button-delete',
                    handler: function () {
                        var id = $("#purchase-backid-buyOrderAddPage").val();
                        if (id == "") {
                            return false;
                        }

                        $.messager.confirm("提醒", "是否删除该采购订单信息？", function (r) {
                            if (r) {
                                loading("删除中..");
                                $.ajax({
                                    url: 'purchase/buyorder/deleteBuyOrder.do?id=' + id,
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag) {
                                            $.messager.alert("提醒", "删除成功");
                                            var nextdata = $("#purchase-buttons-buyOrderPage").buttonWidget("removeData", '');
                                            if (nextdata && nextdata.id && nextdata.id != "") {
                                                $("#purchase-backid-buyOrderAddPage").val(nextdata.id);
                                                $("#purchase-panel-buyOrderPage").panel('refresh', 'purchase/buyorder/buyOrderEditPage.do?id=' + nextdata.id);
                                                buyOrder_RefreshDataGrid();
                                            } else {
                                                //$("#purchase-panel-buyOrderPage").panel('refresh', 'purchase/buyorder/buyOrdeAddPage.do');
                                                buyOrder_RefreshDataGrid();
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
                <security:authorize url="/purchase/buyorder/auditBuyOrder.do">
                {
                    type: 'button-audit',
                    handler: function () {
                        var id = $("#purchase-backid-buyOrderAddPage").val();
                        if (id == "") {
                            return false;
                        }

                        if (checkGoodsDetailEmpty()) {
                            $.messager.alert("提醒", "抱歉，请填写采购订单商品信息");
                            return false;
                        }

                        $.messager.confirm("提醒", "是否审核通过该采购订单信息？", function (r) {
                            if (r) {
                                loading("审核中..");
                                $.ajax({
                                    url: 'purchase/buyorder/auditBuyOrder.do?id=' + id,
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag) {
                                            $.messager.alert("提醒", "审核成功并自动生成入库单，单据编号：" + json.billid);
                                            buyOrder_RefreshDataGrid();
                                            $("#purchase-panel-buyOrderPage").panel('refresh', 'purchase/buyorder/buyOrderEditPage.do?id=' + id);
                                            //$("#purchase-buyOrderAddPage-status").val("3");
                                            //$("#purchase-buttons-buyOrderPage").buttonWidget("setDataID", {id:id, state:'3', type:'view'});
                                        } else {
                                            if (json.msg != null) {
                                                $.messager.alert("提醒", "审核失败。" + json.msg);
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
                <security:authorize url="/purchase/buyorder/oppauditBuyOrder.do">
                {
                    type: 'button-oppaudit',
                    handler: function () {
                        var id = $("#purchase-backid-buyOrderAddPage").val();
                        if (id == "") {
                            return false;
                        }
                        var businessdate = $("#purchase-buyOrderAddPage-businessdate").val();
                        var flag = isDoneOppauditBillCaseAccounting(businessdate);
                        if (!flag) {
                            $.messager.alert("提醒", "业务日期不在会计区间内或未设置会计区间,不允许反审!");
                            return false;
                        }
                        $.messager.confirm("提醒", "是否反审该采购订单信息？", function (r) {
                            if (r) {
                                loading("审核中..");
                                $.ajax({
                                    url: 'purchase/buyorder/oppauditBuyOrder.do?id=' + id,
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag) {
                                            $.messager.alert("提醒", "反审成功");
                                            buyOrder_RefreshDataGrid();
                                            $("#purchase-panel-buyOrderPage").panel('refresh', 'purchase/buyorder/buyOrderEditPage.do?id=' + id);
                                        } else {
                                            $.messager.alert("提醒", "反审失败");
                                        }
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/buyorder/buyOrderWorkflow.do">
                {
                    type: 'button-workflow',
                    button: [
                        {
                            type: 'workflow-submit',
                            handler: function () {
                                $.messager.confirm("提醒", "是否提交采购订单信息到工作流?", function (r) {
                                    if (r) {
                                        var id = $("#purchase-backid-buyOrderAddPage").val();
                                        if (id == "") {
                                            $.messager.alert("警告", "没有需要提交工作流的信息!");
                                            return false;
                                        }
                                        loading("提交中..");
                                        $.ajax({
                                            url: 'purchase/buyorder/submitBuyOrderProcess.do',
                                            dataType: 'json',
                                            type: 'post',
                                            data: 'id=' + id,
                                            success: function (json) {
                                                loaded();
                                                if (json.flag == true) {
                                                    $.messager.alert("提醒", "提交成功!");
                                                    buyOrder_RefreshDataGrid();
                                                    $("#purchase-panel-buyOrderPage").panel("refresh", 'purchase/buyorder/buyOrderEditPage.do?id=' + id);
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
                                if (buyOrder_type == "handle") {
                                    $('<div id="purchase-wfdialog-buyOrderPage-content"></div>').appendTo('#purchase-wfdialog-buyOrderPage');
                                    var $wfDialogOper = $("#purchase-wfdialog-buyOrderPage-content");
                                    $wfDialogOper.dialog({
                                        title: '填写处理意见',
                                        width: 450,
                                        height: 300,
                                        closed: false,
                                        cache: false,
                                        modal: true,
                                        href: 'workflow/commentAddPage.do?id=' + handleWork_taskId,
                                        onClose: function () {
                                            $wfDialogOper.dialog("destroy");
                                        }
                                    });
                                }
                            }
                        },
                        {
                            type: 'workflow-viewflow',
                            handler: function () {
                                var id = $("#purchase-backid-buyOrderAddPage").val();
                                if (id == "") {
                                    return false;
                                }
                                $('<div id="purchase-wfdialog-buyOrderPage-content"></div>').appendTo('#purchase-wfdialog-buyOrderPage');
                                var $wfDialogOper = $("#purchase-wfdialog-buyOrderPage-content");
                                wfDialogOper.dialog({
                                    title: '查看流程',
                                    width: 600,
                                    height: 450,
                                    closed: false,
                                    cache: false,
                                    modal: true,
                                    maximizable: true,
                                    resizable: true,
                                    href: 'workflow/commentListPage.do?id=' + id,
                                    onClose: function () {
                                        $wfDialogOper.dialog("destroy");
                                    }
                                });
                            }
                        },
                        {
                            type: 'workflow-viewflow-pic',
                            handler: function () {
                                var id = $("#purchase-backid-buyOrderAddPage").val();
                                if (id == "") {
                                    return false;
                                }
                                $('<div id="purchase-wfdialog-buyOrderPage-content"></div>').appendTo('#purchase-wfdialog-buyOrderPage');
                                var $wfDialogOper = $("#purchase-wfdialog-buyOrderPage-content");
                                wfDialogOper.dialog({
                                    title: '查看流程',
                                    width: 600,
                                    height: 450,
                                    closed: false,
                                    cache: false,
                                    modal: true,
                                    maximizable: true,
                                    resizable: true,
                                    href: 'workflow/showDiagramPage.do?id=' + id,
                                    onClose: function () {
                                        $wfDialogOper.dialog("destroy");
                                    }
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
                <security:authorize url="/purchase/buyorder/buyOrderRelation.do">
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

                                $('<div id="purchase-sourceViewDialog-buyOrder-content"></div>').appendTo('#purchase-sourceViewDialog-buyOrder');
                                var $buyOrderSourceViewDialog = $("#purchase-sourceViewDialog-buyOrder-content");
                                $buyOrderSourceViewDialog.dialog({
                                    title: '采购计划单列表',
                                    fit: true,
                                    closed: true,
                                    modal: true,
                                    cache: false,
                                    maximizable: true,
                                    resizable: true,
                                    onClose: function () {
                                        $buyOrderSourceViewDialog.dialog("destroy");
                                    }
                                });
                                $('<table id="purchase-planOrderDatagrid-sourceViewDialog"></table>').appendTo("#purchase-sourceViewDialog-buyOrder-content");
                                var $buyOrderReferOrderDatagrid = $('#purchase-planOrderDatagrid-sourceViewDialog');
                                $buyOrderReferOrderDatagrid.datagrid({
                                    columns: [[
                                        {field: 'id', title: '编号', width: 100},
                                        {field: 'businessdate', title: '业务日期', width: 100},
                                        {
                                            field: 'supplierid', title: '供应商名称', width: 100,
                                            formatter: function (value, row, index) {
                                                return row.suppliername;
                                            }
                                        },
                                        {
                                            field: 'handlerid', title: '对方联系人', width: 100,
                                            formatter: function (value, row, index) {
                                                return row.handlername;
                                            }
                                        },
                                        {field: 'applydeptid', title: '申请部门', width: 100, align: 'left'},
                                        {field: 'applyuserid', title: '申请人', width: 100, align: 'left'},
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
                                    url: 'purchase/planorder/showPlannedOrderPageList.do?idarrs=' + idarr.join(','),
                                    onDblClickRow: function (rowIndex, rowData) {
                                        if (rowData != null && rowData.id != null && rowData.id != "") {
                                            top.addOrUpdateTab('purchase/planorder/plannedOrderPage.do?type=view&id=' + rowData.id, '采购计划单');
                                        }
                                    }

                                });
                                $buyOrderSourceViewDialog.dialog('open');
                            }
                        }
                    ]
                },
                </security:authorize>
                <security:authorize url="/purchase/buyorder/buyOrderBack.do">
                {
                    type: 'button-back',
                    handler: function (data) {
                        if (data != null && data.id != null && data.id != "") {
                            $("#purchase-backid-buyOrderAddPage").val(data.id);
                            $("#purchase-panel-buyOrderPage").panel('refresh', 'purchase/buyorder/buyOrderEditPage.do?id=' + data.id);
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/buyorder/buyOrderNext.do">
                {
                    type: 'button-next',
                    handler: function (data) {
                        if (data != null && data.id != null && data.id != "") {
                            $("#purchase-backid-buyOrderAddPage").val(data.id);
                            $("#purchase-panel-buyOrderPage").panel('refresh', 'purchase/buyorder/buyOrderEditPage.do?id=' + data.id);
                        }
                    }
                },
                </security:authorize>
                {}
            ],
            buttons: [
                <security:authorize url="/purchase/buyorder/buyOrderCloseBtn.do">
                {
                    id: 'button-close-bill',
                    name: '关闭订单',
                    iconCls: 'button-close',
                    handler: function () {
                        var id = $("#purchase-backid-buyOrderAddPage").val();
                        if (id == null || id == "") {
                            $.messager.alert("提醒", "请选择相应的采购订单!");
                            return false;
                        }
                        var status = $("#purchase-buyOrderAddPage-status").val();
                        if (status != '3') {
                            $.messager.alert("提醒", "只有审核通过状态下采购订单才能关闭");
                            return false;
                        }
                        $.messager.confirm("提醒", "采购订单关闭后不能再开启，确定要关闭？", function (r) {
                            if (r) {
                                loading("关闭中..");
                                $.ajax({
                                    url: 'purchase/buyorder/closeBuyOrder.do?id=' + id,
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag) {
                                            $.messager.alert("提醒", "关闭采购订单成功");
                                            buyOrder_RefreshDataGrid();
                                            $("#purchase-panel-buyOrderPage").panel('refresh', 'purchase/buyorder/buyOrderEditPage.do?id=' + id);
                                        } else {
                                            if (json.msg) {
                                                $.messager.alert("提醒", "关闭采购订单失败!" + json.msg);
                                            } else {
                                                $.messager.alert("提醒", "关闭采购订单失败!");
                                            }
                                        }
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/buyorder/buyOrderExport.do">
                {
                    id: 'button-export-detail',
                    name: '导出明细',
                    iconCls: 'button-export',
                    handler: function () {
                        var id = $("#purchase-backid-buyOrderAddPage").val();
                        if (id == "") {
                            $.messager.alert("提醒", "抱歉，未能找到要导出的数据!");
                            return;
                        }
                        $("#purchase-form-buyOrderDetailExport-id").val(id);
                        $("#report-export-buyOrderListPageDetail").Excel('export', {
                            queryForm: "#purchase-form-buyOrderDetailExport",
                            type: 'exportUserdefined',
                            name: '采购订单明细',
                            url: 'purchase/buyorder/exportBuyOrderDetailData.do'
                        });
                        $("#report-export-buyOrderListPageDetail").trigger("click");
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/buyorder/showPlanOrderAnalysisViewPage.do">
                {
                    id: 'button-view-planorderAnalysis',
                    name: '查看采购计划分析表',
                    iconCls: 'button-view',
                    handler: function () {
                        var businessdate = $("#purchase-buyOrderAddPage-businessdate").val() || "";
                        if (businessdate == null || $.trim(businessdate) == "") {
                            $.messager.alert("提醒", "抱歉，未能找到相关商品信息!");
                            return false;
                        }
                        var supplierid = "";
                        try {
                            supplierid = $("#purchase-buyOrderAddPage-supplier").widget("getValue");
                        } catch (e) {
                        }
                        if (supplierid == null || $.trim(supplierid) == "") {
                            $.messager.alert("提醒", "抱歉，未能找到供应商信息!");
                            return false;
                        }
                        supplierid = $.trim(supplierid);
                        $('<div id="purchase-buyOrderPage-dialog-planorderAnalysis-content"></div>').appendTo('#purchase-buyOrderPage-dialog-planorderAnalysis');
                        var $buyOrderPlanAnalysisDialog = $("#purchase-buyOrderPage-dialog-planorderAnalysis-content");
                        $buyOrderPlanAnalysisDialog.dialog({
                            title: '查看相应采购计划分析表',
                            //width: 680,
                            //height: 300,
                            fit: true,
                            closed: true,
                            cache: false,
                            href: 'purchase/buyorder/showPlanOrderAnalysisViewReportPage.do?businessdate=' + $.trim(businessdate) + "&supplierid=" + supplierid,
                            maximizable: true,
                            resizable: true,
                            modal: true,
                            onLoad: function () {
                                var idarr = new Array();
                                var orderInfoArr = new Array();
                                var rowsObjectData = $("#purchase-buyOrderAddPage-buyOrdertable").datagrid("getData");
                                var rowsData = rowsObjectData.rows;

                                for (var i = 0; i < rowsData.length; i++) {
                                    if (rowsData[i].goodsid != null && $.trim(rowsData[i].goodsid) != "") {
                                        idarr.push(rowsData[i].goodsid);
                                        var orderInfo = {};
                                        orderInfo.goodsid = rowsData[i].goodsid;
                                        orderInfo.unitnum = rowsData[i].unitnum;
                                        orderInfo.taxamount = rowsData[i].taxamount;
                                        orderInfo.taxprice = rowsData[i].taxprice;
                                        orderInfo.unitid = rowsData[i].unitid;
                                        orderInfo.unitname = rowsData[i].unitname;
                                        orderInfo.auxunitid = rowsData[i].auxunitid;
                                        orderInfo.auxunitname = rowsData[i].auxunitname;
                                        orderInfo.auxnum = rowsData[i].auxnum;
                                        orderInfo.auxremainder = rowsData[i].auxremainder;

                                        orderInfoArr.push(orderInfo);
                                    }
                                }
                                try {
                                    var queryFormdata = $("#purchase-buyOrderAddPage-field08").val() || "";

                                    if ($.trim(queryFormdata) != "") {
                                        queryFormdata = JSON.parse(queryFormdata);
                                        if (queryFormdata) {
                                            poaOldFormData = queryFormdata;
                                            queryFormdata.supplierid = supplierid;
                                            $("#purchase-query-form-analysisReportView").form('load', queryFormdata);
                                        }
                                    }
                                } catch (e) {
                                }
                                var orderstatus = $("#purchase-buyOrderAddPage-status").val() || "2";
                                $("#purchase-queay-analysisReportView-orderstatus").val(orderstatus);
                                $("#purchase-queay-analysisReportView-goodsidarr").val(idarr.join(','));
                                $("#purchase-queay-analysisReportView-orderInfoarr").val(JSON.stringify(orderInfoArr));


                                initAnalysisDatagrid();
                                initStorageSummaryDategrid();
                            },
                            onClose: function () {
                                $buyOrderPlanAnalysisDialog.dialog("destroy");
                            }
                        });
                        $buyOrderPlanAnalysisDialog.dialog('open');
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/buyorder/buyOrderPrintViewBtn.do">
                {
                    id: 'button-printview-buyorder',
                    name: '打印预览',
                    iconCls: 'button-preview',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/buyorder/buyOrderPrintBtn.do">
                {
                    id: 'button-print-buyorder',
                    name: '打印',
                    iconCls: 'button-print',
                    handler: function () {
                    }
                },
                </security:authorize>
                {}
            ],
            layoutid: 'purchase-buyOrderPage-layout',
            model: 'bill',
            type: 'view',
            taburl: pageListUrl,
            datagrid: 'purchase-table-buyOrderListPage',
            id: '${id}',
            tname: 't_purchase_buyorder'
        });
        $(document).bind('keydown', 'esc', function () {
            $("#purchase-buyOrderDetail-remark").focus();
            if ($("#purchase-buyOrderAddPage-dialog-DetailOper-content").size() > 0) {
                $("#purchase-buyOrderAddPage-dialog-DetailOper-content").dialog("close");
            }
        });
        $(document).bind('keydown', 'ctrl+enter', function () {
            $("#purchase-buyOrderDetail-remark").focus();
            setTimeout(function () {
                $("#purchase-buyOrderDetailAddPage-addSaveGoOn").trigger("click");
                $("#purchase-buyOrderDetailEditPage-editSave").trigger("click");
            }, 200);
            return false;
        });
        $(document).bind('keydown', '+', function () {
            $("#purchase-buyOrderDetail-remark").focus();
            setTimeout(function () {
                $("#purchase-buyOrderDetailAddPage-addSaveGoOn").trigger("click");
                $("#purchase-buyOrderDetailEditPage-editSave").trigger("click");
            }, 200);
            return false;
        });

    });
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "listPage-buyOrder-dialog-print",
            code: "purchase_buyorder",
            url_preview: "print/purchase/buyOrderPrintView.do",
            url_print: "print/purchase/buyOrderPrint.do",
            btnPreview: "button-printview-buyorder",
            btnPrint: "button-print-buyorder",
            getData: function (tableId, printParam) {
                var id = $("#purchase-backid-buyOrderAddPage").val();
                if (id == "") {
                    $.messager.alert("警告", "找不到要打印的信息!");
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
