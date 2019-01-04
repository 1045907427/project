<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>销售发货通知单</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
    <%
        boolean showPrintOptions = false;
        boolean printOptionsOrderSeq = false;
    %>
    <security:authorize url="/storage/salesDeliveryOrderPrintOptions.do">
        <%
            showPrintOptions = true;
        %>
    </security:authorize>
    <security:authorize url="/storage/salesOrderPrintOptionsOrderSeq.do">
        <%
            printOptionsOrderSeq = true;
        %>
    </security:authorize>
</head>
<body>
<input type="hidden" id="sales-backid-dispatchBill" value="${id }"/>
<input type="hidden" id="sales-parentid-dispatchBill"/><!-- 参照上游单据的编码 -->
<div id="sales-layout-dispatchBill" class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="sales-buttons-dispatchBill" style="height:26px;"></div>
    </div>
    <div data-options="region:'center',border:false">
        <div id="sales-panel-dispatchBill">
        </div>
    </div>
</div>
<div id="sales-deployDialog-dispatchBill">
    <div id="sales-deployDialog-dispatchBill-content" style="padding: 10px;"></div>
</div>
<div style="display:none">
    <div id="sales-sourceQueryDialog-dispatchBill"></div>
    <div id="sales-sourceDialog-dispatchBill"></div>
    <div id="sales-dialog-dispatchBill"></div>
</div>
<script type="text/javascript">
    var order_url = "sales/dispatchBillAddPage.do";
    var order_type = '${type}';
    if (order_type == "view" || order_type == "show" || order_type == "handle") {
        order_url = "sales/dispatchBillViewPage.do?id=${id}";
    }
    if (order_type == "edit") {
        order_url = "sales/dispatchBillEditPage.do?id=${id}";
    }
    var dispatch_taxpricechange = "0";

    var dispatch_AjaxConn = function (Data, Action) {
        var MyAjax = $.ajax({
            type: 'post',
            cache: false,
            url: Action,
            data: Data,
            async: false,
            success: function (data) {
                loaded();
            }
        });
        return MyAjax.responseText;
    };

    var wareListJson = $("#sales-datagrid-dispatchBillAddPage").createGridColumnLoad({ //以下为商品明细datagrid字段
        name: 't_sales_dispatchbill_detail',
        frozenCol: [[]],
        commonCol: [[{field: 'goodsid', title: '商品编码', width: 70, sortable: true, align: 'left'},
            {
                field: 'goodsname', title: '商品名称', width: 250, align: 'left', aliascol: 'goodsid',
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        if (rowData.isdiscount == '1') {
                            return "（折扣）" + rowData.goodsInfo.name;
                        } else if (rowData.isdiscount == '2') {
                            return "（折扣）" + rowData.goodsInfo.name;
                        } else {
                            if (rowData.deliverytype == '1') {
                                return "<font color='blue'>&nbsp;赠 </font>" + rowData.goodsInfo.name;
                            } else {
                                return rowData.goodsInfo.name;
                            }
                        }
                    } else {
                        return "";
                    }
                }
            },
            {
                field: 'barcode', title: '条形码', width: 90, align: 'left', aliascol: 'goodsid',
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.isdiscount != '1' && rowData.goodsInfo != null) {
                        return rowData.goodsInfo.barcode;
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
                    if (rowData.isdiscount != '1' && rowData.goodsInfo != null) {
                        return formatterBigNumNoLen(rowData.goodsInfo.boxnum);
                    } else {
                        return "";
                    }
                }
            },
            {field: 'unitname', title: '单位', width: 35, align: 'left'},
            {
                field: 'usablenum', title: '可用量', width: 60, align: 'right', isShow: true, sortable: true,
                formatter: function (value, row, index) {
                    if (row.isdiscount != '1' && row.isdiscount != '2' && row.goodsInfo != null) {
                        return "<span style='cursor: pointer;' title='预计可用量:" + formatterBigNumNoLen(row.preusablenum) + "'>" + formatterBigNumNoLen(value) + "</span>";
                    } else {
                        return "";
                    }
                },
                styler: function (value, row, index) {
                    var status = $("#sales-status-dispatchBillAddPage").val();
                    if (status == "2" && Number(row.preusablenum) > 0 && Number(row.preusablenum) > Number(value) && Number(row.usablenum) < Number(row.unitnum)) {
                        return 'background-color:#9BC3FF;';
                    }
                }
            },
            {
                field: 'unitnum', title: '数量', width: 60, align: 'right', sortable: true,
                formatter: function (value, row, index) {
                    return formatterBigNumNoLen(value);
                },
                styler: function (value, row, index) {
                    var status = $("#sales-status-dispatchBillAddPage").val();
                    if (status == "2" && Number(row.usablenum) < Number(value)) {
                        return 'background-color:red;';
                    }
                }
            },
            {
                field: 'taxprice', title: '单价', width: 60, align: 'right', sortable: true,
                formatter: function (value, row, index) {
                    if (row.isdiscount != '1' && row.isdiscount != '2') {
                        return formatterMoney(value);
                    }
                }
            },
            {
                field: 'boxprice', title: '箱价', width: 60, aliascol: 'taxprice', align: 'right', sortable: true,
                formatter: function (value, row, index) {
                    if (row.isdiscount != '1' && row.isdiscount != '2') {
                        return formatterMoney(value);
                    }
                }
            },
            {
                field: 'taxamount', title: '金额', width: 60, align: 'right', sortable: true,
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
            {
                field: 'taxtype', title: '税种', width: 60, align: 'left', hidden: true,
                formatter: function (value, row, index) {
                    return row.taxtypename;
                }
            },
            {
                field: 'tax', title: '税额', width: 60, align: 'right', hidden: true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {field: 'auxnumdetail', title: '辅数量', width: 60, align: 'right'},
            {
                field: 'storageid', title: '指定仓库', width: 80, align: 'left',
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
                field: 'remark', title: '备注', width: 150, align: 'left',
                editor: {
                    type: 'validatebox'
                }
            }
        ]]
    });
    $(function () {
        $("#sales-panel-dispatchBill").panel({
            href: order_url,
            cache: false,
            maximized: true,
            border: false,
            onLoad: function () {
                $("#sales-customer-dispatchBillAddPage").focus();
            }
        });
        //按钮
        $("#sales-buttons-dispatchBill").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/sales/dispatchBillAddPage.do">
                {
                    type: 'button-add',
                    handler: function () {
                        refreshPanel('sales/dispatchBillAddPage.do');
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/dispatchBillHold.do">
                {
                    type: 'button-hold',
                    handler: function () {
                        $.messager.confirm("提醒", "确定暂存该订单信息？", function (r) {
                            if (r) {
                                $("#sales-addType-dispatchBillAddPage").val("temp");
                                var json = $("#sales-datagrid-dispatchBillAddPage").datagrid('getRows');
                                $("#sales-goodsJson-dispatchBillAddPage").val(JSON.stringify(json));
                                order_tempSave_form_submit();
                                $("#sales-form-dispatchBillAddPage").submit();
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/dispatchBillSave.do">
                {
                    type: 'button-save',
                    handler: function () {
                        $("#sales-addType-dispatchBillAddPage").val("real");
                        var rows = $("#sales-datagrid-dispatchBillAddPage").datagrid('getRows');
                        $("#sales-goodsJson-dispatchBillAddPage").val(JSON.stringify(rows));
                        for (var i = 0; i < rows.length; i++) {
                            var obj = rows[i];
                            if (obj.isdiscount == "1") {
                                for (var j = 0; j < rows.length; j++) {
                                    if (rows[j].goodsid == obj.goodsid && rows[j].isdiscount == "0" || rows[j].isdiscount == undefined) {
                                        if (rows[j].notaxprice == "0.000000" || rows[j].notaxprice == "0") {
                                            $.messager.alert("提醒", rows[j].goodsid + "商品单价为0,不允许添加商品折扣");
                                            return;
                                        } else if (rows[j].goodsid == undefined) {
                                            continue;
                                        }
                                    }
                                }
                            } else if (obj.isdiscount == "2") {
                                var brandid = obj.brandid;
                                var count = 0;
                                for (var j = 0; j < rows.length; j++) {
                                    //修改时判断
                                    if (rows[j].brandid != undefined) {
                                        if (rows[j].brandid == brandid && rows[j].notaxprice != "0.000000" && rows[j].notaxprice != "0") {
                                            ++count;
                                        }
                                    } else {//新增时判断
                                        if (rows[j].goodsInfo == undefined) {
                                            continue;
                                        } else if (rows[j].goodsInfo.brand == brandid && rows[j].notaxprice != "0.000000" && rows[j].notaxprice != "0") {
                                            ++count;
                                        }
                                    }
                                }
                                if (count == 0) {
                                    $.messager.alert("提醒", obj.goodsInfo.name + " 品牌商品单价为0,不允许添加品牌折扣");
                                    return;
                                }
                            }
                        }
                        order_realSave_form_submit();
                        $("#sales-form-dispatchBillAddPage").submit();
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/dispatchBillSaveAudit.do">
                {
                    type: 'button-saveaudit',
                    handler: function () {
                        $.messager.confirm("提醒", "确定保存并审核该销售发货通知单？", function (r) {
                            if (r) {
                                $("#sales-addType-dispatchBillAddPage").val("real");
                                $("#sales-saveaudit-dispatchBillAddPage").val("saveaudit");
                                var rows = $("#sales-datagrid-dispatchBillAddPage").datagrid('getRows');
                                $("#sales-goodsJson-dispatchBillAddPage").val(JSON.stringify(rows));
                                for (var i = 0; i < rows.length; i++) {
                                    var obj = rows[i];
                                    if (obj.isdiscount == "1") {
                                        for (var j = 0; j < rows.length; j++) {
                                            if (rows[j].goodsid == obj.goodsid && rows[j].isdiscount == "0" || rows[j].isdiscount == undefined) {
                                                if (rows[j].notaxprice == "0.000000" || rows[j].notaxprice == "0") {
                                                    $.messager.alert("提醒", rows[j].goodsid + "商品单价为0,不允许添加商品折扣");
                                                    return;
                                                } else if (rows[j].goodsid == undefined) {
                                                    continue;
                                                }
                                            }
                                        }
                                    } else if (obj.isdiscount == "2") {
                                        var brandid = obj.brandid;
                                        var count = 0;
                                        for (var j = 0; j < rows.length; ++j) {
                                            //修改时判断
                                            if (rows[j].brandid != undefined) {
                                                if (rows[j].brandid == brandid && rows[j].notaxprice != "0.000000" && rows[j].notaxprice != "0") {
                                                    ++count;
                                                }
                                            } else {//新增时判断
                                                if (rows[j].goodsInfo == undefined) {
                                                    continue;
                                                } else if (rows[j].goodsInfo.brand == brandid && rows[j].notaxprice != "0.000000" && rows[j].notaxprice != "0") {
                                                    ++count;
                                                }
                                            }
                                        }
                                        if (count == 0) {
                                            $.messager.alert("提醒", obj.goodsInfo.name + " 品牌商品单价为0,不允许添加品牌折扣");
                                            return;
                                        }
                                    }
                                }
                                order_realSave_form_submit();
                                $("#sales-form-dispatchBillAddPage").submit();
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/dispatchBillGiveup.do">
                {
                    type: 'button-giveup',
                    handler: function () {
                        var type = $("#sales-buttons-dispatchBill").buttonWidget("getOperType");
                        if (type == "add") {
                            var currTitle = top.$('#tt').tabs('getSelected').panel('options').title;
                            top.closeTab(currTitle);
                        }
                        else if (type == "edit") {
                            var id = $("#sales-backid-dispatchBill").val();
                            if (id == "") {
                                return false;
                            }
                            $("#sales-panel-dispatchBill").panel('refresh', 'sales/dispatchBillViewPage.do?id=' + id);
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/dispatchBillDelete.do">
                {
                    type: 'button-delete',
                    handler: function () {
                        var id = $("#sales-backid-dispatchBill").val();
                        if (id == '') {
                            return false;
                        }
                        $.messager.confirm("提醒", "确定删除该通知单信息？", function (r) {
                            if (r) {
                                loading("删除中..");
                                $.ajax({
                                    url: 'sales/deleteDispatchBill.do',
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
                                            var data = $("#sales-buttons-dispatchBill").buttonWidget("removeData", '');
                                            if (data != null) {
                                                $("#sales-backid-dispatchBill").val(data.id);
                                                refreshPanel('sales/dispatchBillEditPage.do?id=' + data.id);
                                            }
                                            else {
                                                parent.closeNowTab();
                                            }
                                        }
                                        else {
                                            $.messager.alert("提醒", "删除失败");
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
                <security:authorize url="/sales/dispatchBillAudit.do">
                {
                    type: 'button-audit',
                    handler: function () {
                        var id = $("#sales-backid-dispatchBill").val();
                        if (id == '') {
                            return false;
                        }
                        $.messager.confirm("提醒", "确定审核该通知单信息？", function (r) {
                            if (r) {
                                loading("审核中..");
                                $.ajax({
                                    url: 'sales/auditDispatchBill.do',
                                    dataType: 'json',
                                    type: 'post',
                                    data: 'id=' + id + '&type=1',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag == true) {
                                            if (json.billId == "" || json.billId == null) {
                                                $.messager.alert("提醒", "审核成功，" + json.msg);
                                            }
                                            else {
                                                $.messager.alert("提醒", "审核并自动生成出库单成功，单据号为：" + json.billId);
                                            }
                                            $("#sales-status-dispatchBillAddPage").val("3");
                                            $("#sales-canprint-dispatchBillAddPage").val("1");
                                            $("#sales-buttons-dispatchBill").buttonWidget("setDataID", {
                                                id: id,
                                                state: '3',
                                                type: 'view'
                                            });
                                            $("#sales-buttons-dispatchBill").buttonWidget("disableButton", 'storage-deploy-button');
                                            //$("#button-next").trigger("click");
                                            //refreshPanel('sales/dispatchBillViewPage.do?id='+ id);

                                            $("#sales-buttons-dispatchBill").buttonWidget("enableMenuItem", "button-printview-orderblank");
                                            $("#sales-buttons-dispatchBill").buttonWidget("enableMenuItem", "button-print-orderblank");
                                            $("#sales-buttons-dispatchBill").buttonWidget("enableMenuItem", "button-printview-DispatchBill");
                                            $("#sales-buttons-dispatchBill").buttonWidget("enableMenuItem", "button-print-DispatchBill");
                                            $("#sales-buttons-dispatchBill").buttonWidget("enableMenuItem", "button-printview-DeliveryOrder");
                                            $("#sales-buttons-dispatchBill").buttonWidget("enableMenuItem", "button-print-DeliveryOrder");
                                        }
                                        else {
                                            if (json.msg != null && json.msg != "") {
                                                $.messager.alert("提醒", "审核失败，" + json.msg);
                                            }
                                            else {
                                                $.messager.alert("提醒", "审核失败");
                                            }
                                        }
                                    },
                                    error: function () {
                                        loaded();
                                        $.messager.alert("提醒", "审核出错");
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/auditSupperDispatchBill.do">
                {
                    type: 'button-supperaudit',
                    handler: function () {
                        var id = $("#sales-backid-dispatchBill").val();
                        if (id == '') {
                            return false;
                        }
                        $.messager.confirm("提醒", "超级审核后，将不会根据可用量指定仓库发货，是否确定超级审核该通知单？", function (r) {
                            if (r) {
                                loading("审核中..");
                                $.ajax({
                                    url: 'sales/auditSupperDispatchBill.do',
                                    dataType: 'json',
                                    type: 'post',
                                    data: {id: id, type: '1'},
                                    success: function (json) {
                                        loaded();
                                        if (json.flag == true) {
                                            if (json.billId == "" || json.billId == null) {
                                                $.messager.alert("提醒", "审核成功，" + json.msg);
                                            }
                                            else {
                                                $.messager.alert("提醒", "审核并自动生成出库单成功，单据号为：" + json.billId);
                                            }
                                            $("#sales-status-dispatchBillAddPage").val("3");
                                            $("#sales-canprint-dispatchBillAddPage").val("1");
                                            $("#sales-buttons-dispatchBill").buttonWidget("setDataID", {
                                                id: id,
                                                state: '3',
                                                type: 'view'
                                            });
                                            $("#sales-buttons-dispatchBill").buttonWidget("disableButton", 'storage-deploy-button');

                                            $("#sales-buttons-dispatchBill").buttonWidget("enableMenuItem", "button-printview-orderblank");
                                            $("#sales-buttons-dispatchBill").buttonWidget("enableMenuItem", "button-print-orderblank");
                                            $("#sales-buttons-dispatchBill").buttonWidget("enableMenuItem", "button-printview-DispatchBill");
                                            $("#sales-buttons-dispatchBill").buttonWidget("enableMenuItem", "button-print-DispatchBill");
                                            $("#sales-buttons-dispatchBill").buttonWidget("enableMenuItem", "button-printview-DeliveryOrder");
                                            $("#sales-buttons-dispatchBill").buttonWidget("enableMenuItem", "button-print-DeliveryOrder");

                                            refreshPanel('sales/dispatchBillViewPage.do?id=' + id);
                                        }
                                        else {
                                            if (json.msg != null && json.msg != "") {
                                                $.messager.alert("提醒", "审核失败，" + json.msg);
                                            }
                                            else {
                                                $.messager.alert("提醒", "审核失败");
                                            }
                                        }
                                    },
                                    error: function () {
                                        loaded();
                                        $.messager.alert("提醒", "审核出错");
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/dispatchBillOppaudit.do">
                {
                    type: 'button-oppaudit',
                    handler: function () {
                        //var times = $("#sales-phprinttimes-dispatchBillAddPage").val();
                        //if(parseInt(times)>0){
                        //	$.messager.alert("提醒","单据已打印，不能反审！");
                        //	return false;
                        //}
                        var id = $("#sales-backid-dispatchBill").val();
                        if (id == '') {
                            return false;
                        }
                        var businessdate = $("#sales-businessdate-dispatchBillAddPage").val();
                        var flag = isDoneOppauditBillCaseAccounting(businessdate);
                        if (!flag) {
                            $.messager.alert("提醒", "业务日期不在会计区间内或未设置会计区间,不允许反审!");
                            return false;
                        }
                        var auditflag = true;
                        <security:authorize url="/sales/dispatchBillOppauditSupper.do">
                        auditflag = false;
                        </security:authorize>
                        if (auditflag) {
                            var businessdate = $("#sales-businessdate-dispatchBillAddPage").val();
                            if (businessdate != '${today}') {
                                $.messager.alert("提醒", "销售发货通知单不能反审，业务日期不是今天。需要有权限的人才能反审！");
                                return false;
                            }
                        }
                        $.messager.confirm("提醒", "确定反审通知单信息？", function (r) {
                            if (r) {
                                loading("反审中..");
                                $.ajax({
                                    url: 'sales/auditDispatchBill.do',
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
                                                refreshPanel('sales/dispatchBillEditPage.do?id=' + id);
                                            }
                                            else {
                                                var msg = "";
                                                if (undefined != json.msg) {
                                                    msg = json.msg;
                                                }
                                                $.messager.alert("提醒", "反审失败<br>" + msg);
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
                <security:authorize url="/sales/dispatchBillRelation.do">
                {
                    type: 'button-relation',
                    button: [
                        {
                            type: 'relation-upper',
                            handler: function () {
                                $("#sales-sourceQueryDialog-dispatchBill").dialog({ //查询参照上游
                                    title: '销售订单查询',
                                    width: 400,
                                    height: 260,
                                    closed: false,
                                    modal: true,
                                    cache: false,
                                    href: 'sales/dispatchBillSourceQueryPage.do',
                                    buttons: [{
                                        text: '查询',
                                        handler: function () {
                                            var queryJSON = $("#sales-form-dispatchBillSourceQueryPage").serializeJSON();
                                            $("#sales-sourceQueryDialog-dispatchBill").dialog('close', true);
                                            $("#sales-sourceDialog-dispatchBill").dialog({
                                                title: '销售订单列表',
                                                fit: true,
                                                closed: false,
                                                modal: true,
                                                cache: false,
                                                maximizable: true,
                                                resizable: true,
                                                href: 'sales/dispatchBillSourcePage.do',
                                                buttons: [
                                                    {
                                                        text: '确定',
                                                        handler: function () {
                                                            $("#sales-sourceDialog-dispatchBill").dialog('close', true);
                                                            var order = $("#sales-orderDatagrid-dispatchBillSourcePage").datagrid('getSelected');
                                                            if (order == null) {
                                                                $.messager.alert("提醒", "请选择一条订单记录");
                                                                return false;
                                                            }
                                                            $("#sales-parentid-dispatchBill").val(order.id);
                                                            refreshPanel('sales/dispatchBillReferPage.do?id=' + order.id);
                                                        }
                                                    }
                                                ],
                                                onLoad: function () {
                                                    $("#sales-orderDatagrid-dispatchBillSourcePage").datagrid({
                                                        columns: [[
                                                            {field: 'id', title: '编号', width: 100},
                                                            {field: 'businessdate', title: '业务日期', width: 100},
                                                            {
                                                                field: 'customerid',
                                                                title: '客户编码',
                                                                width: 80,
                                                                align: 'left'
                                                            },
                                                            {
                                                                field: 'customername',
                                                                title: '客户名称',
                                                                width: 100,
                                                                align: 'left'
                                                            },
                                                            {
                                                                field: 'handlerid',
                                                                title: '对方经手人',
                                                                width: 100,
                                                                align: 'left'
                                                            },
                                                            {
                                                                field: 'salesdept',
                                                                title: '销售部门',
                                                                width: 120,
                                                                align: 'left'
                                                            },
                                                            {
                                                                field: 'salesuser',
                                                                title: '客户业务员',
                                                                width: 100,
                                                                align: 'left'
                                                            },
                                                            {
                                                                field: 'addusername',
                                                                title: '制单人',
                                                                width: 100,
                                                                align: 'left'
                                                            },
                                                            {field: 'remark', title: '备注', width: 100, align: 'left'}
                                                        ]],
                                                        fit: true,
                                                        method: 'post',
                                                        rownumbers: true,
                                                        pagination: true,
                                                        idField: 'id',
                                                        singleSelect: true,
                                                        fitColumns: true,
                                                        url: 'sales/getOrderList.do',
                                                        queryParams: queryJSON,
                                                        onClickRow: function (index, data) {
                                                            $("#sales-detailDatagrid-dispatchBillSourcePage").datagrid({
                                                                url: 'sales/getDetailListByOrder.do',
                                                                queryParams: {
                                                                    id: data.id
                                                                }
                                                            });
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    }
                                    ]
                                });
                            }
                        },
                        {
                            type: 'relation-upper-view',
                            handler: function () {
                                var parentid = $("#sales-parentid-dispatchBill").val();
                                var source = $("#sales-source-dispatchBillAddPage").val();
                                if (parentid == '') {
                                    return false;
                                } else {
                                    top.addOrUpdateTab('sales/orderPage.do?type=view&id=' + parentid, '销售订单查看');
                                }
                            }
                        }
                    ]
                },
                </security:authorize>
                <security:authorize url="/sales/dispatchBillBack.do">
                {
                    type: 'button-back',
                    handler: function (data) {
                        $("#sales-backid-dispatchBill").val(data.id);
                        refreshPanel('sales/dispatchBillEditPage.do?id=' + data.id);
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/dispatchBillNext.do">
                {
                    type: 'button-next',
                    handler: function (data) {
                        $("#sales-backid-dispatchBill").val(data.id);
                        refreshPanel('sales/dispatchBillEditPage.do?id=' + data.id);
                    }
                },
                </security:authorize>
                {}
            ],
            buttons: [
                <security:authorize url="/sales/oweOrderOnDispatchBill.do">
                {
                    id: 'storage-oweorder-button',
                    name: '生成销售欠单',
                    iconCls: 'button-audit',
                    handler: function () {
                        $.messager.confirm("提醒", "是否生成销售欠单？", function (r) {
                            if (r) {
                                var id = $("#sales-backid-dispatchBill").val();
                                if (id != "") {
                                    $.ajax({
                                        url: 'sales/addOweOrderByDispatchBill.do?id=' + id,
                                        type: 'post',
                                        dataType: 'json',
                                        success: function (json) {
                                            if (json.flag) {
                                                $.messager.alert("提醒", "生成销售欠单成功!<br>销售欠单编号:" + json.msg);
                                                var url = 'sales/dispatchBill.do?type=edit&id=' + id;
                                                refreshPanel(url);
                                            }
                                            else {
                                                $.messager.alert("提醒", "生成销售欠单失败!<br>" + json.msg);
                                            }
                                        },
                                        error: function () {
                                            loaded();
                                            $.messager.alert("错误", "生成销售欠单出错!");
                                        }

                                    });
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/dispatchBillDeploy.do">
                {
                    id: 'storage-deploy-button',
                    name: '配置库存',
                    iconCls: 'button-deploy',
                    handler: function () {
                        $.messager.confirm("提醒", "是否对当前销售发货通知单配置库存？", function (r) {
                            if (r) {
                                var id = $("#sales-backid-dispatchBill").val();
                                if (id != "") {
                                    loading("配置中..");
                                    $.ajax({
                                        url: 'sales/dispatchBillDeploy.do?id=' + id,
                                        type: 'post',
                                        dataType: 'json',
                                        success: function (json) {
                                            loaded();
                                            if (json.flag) {
                                                $.messager.alert("提醒", "商品数量充足!");
                                                $("#storage-deploy-button").linkbutton('disable');
                                            } else {
                                                if (json.barcodeFlag) {
                                                    $("#sales-deployDialog-dispatchBill-content").html(json.msg);
                                                    $("#sales-deployDialog-dispatchBill").dialog({
                                                        title: '配置库存提醒信息',
                                                        width: 600,
                                                        height: 300,
                                                        closed: true,
                                                        cache: false,
                                                        modal: true,
                                                        buttons: [{
                                                            text: '追加',
                                                            handler: function () {
                                                                var gArr = [];
                                                                $('.deployStorage:checked').each(function (i) {
                                                                    var goodsid = $(this).attr("name");
                                                                    var rgoodsid = $(this).val();
                                                                    var storageid = $(this).attr("storageid");
                                                                    var gjson = {
                                                                        goodsid: goodsid,
                                                                        rgoodsid: rgoodsid,
                                                                        storageid: storageid
                                                                    };
                                                                    gArr.push(gjson);
                                                                });
                                                                var deployStr = "";
                                                                if (gArr.length > 0) {
                                                                    deployStr = JSON.stringify(gArr)
                                                                }
                                                                $("#storage-deploy-button").linkbutton('disable');
                                                                $("#sales-deployDialog-dispatchBill").dialog("close");
                                                                var url = 'sales/dispatchBillDeployPage.do?id=' + id + '&barcodeflag=1';
                                                                refreshPanelAddParam(url, {deploy: deployStr});
                                                                $.messager.alert("提醒", "配置库存成功!请确认保存后，销售发货通知单才生效！");
                                                            }
                                                        }, {
                                                            text: '替换',
                                                            handler: function () {
                                                                var gArr = [];
                                                                $('.deployStorage:checked').each(function (i) {
                                                                    var goodsid = $(this).attr("name");
                                                                    var rgoodsid = $(this).val();
                                                                    var storageid = $(this).attr("storageid");
                                                                    var gjson = {
                                                                        goodsid: goodsid,
                                                                        rgoodsid: rgoodsid,
                                                                        storageid: storageid
                                                                    };
                                                                    gArr.push(gjson);
                                                                });
                                                                var deployStr = "";
                                                                if (gArr.length > 0) {
                                                                    deployStr = JSON.stringify(gArr)
                                                                }

                                                                $("#storage-deploy-button").linkbutton('disable');
                                                                $("#sales-deployDialog-dispatchBill").dialog("close");
                                                                var url = 'sales/dispatchBillDeployPage.do?id=' + id + '&barcodeflag=2';
                                                                refreshPanelAddParam(url, {deploy: deployStr});
                                                                $.messager.alert("提醒", "配置库存成功!请确认保存后，销售发货通知单才生效！");
                                                            }
                                                        }, {
                                                            text: '直接配置',
                                                            handler: function () {
                                                                $("#storage-deploy-button").linkbutton('disable');
                                                                $("#sales-deployDialog-dispatchBill").dialog("close");
                                                                var url = 'sales/dispatchBillDeployPage.do?id=' + id;
                                                                refreshPanel(url);
                                                                $.messager.alert("提醒", "配置库存成功!请确认保存后，销售发货通知单才生效！");
                                                            }
                                                        }]
                                                    });
                                                    $("#sales-deployDialog-dispatchBill").dialog("open");
                                                } else {
                                                    $("#storage-deploy-button").linkbutton('disable');
                                                    var url = 'sales/dispatchBillDeployPage.do?id=' + id;
                                                    refreshPanel(url);
                                                    if (json.batchFlag) {
                                                        $.messager.alert("提醒", json.msg + "配置库存成功!<br/>请保存确认后，销售发货通知单才生效！");
                                                    } else {
                                                        $.messager.alert("提醒", "配置库存成功!<br/>请保存确认后，销售发货通知单才生效！");
                                                    }
                                                }
                                            }
                                        },
                                        error: function () {
                                            loaded();
                                            $.messager.alert("错误", "配置库存出错!");
                                        }
                                    });
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/dispatchBillPrintBtn.do">
                {
                    id: 'menuButton',
                    type: 'menu',
                    name: '打印',
                    iconCls: 'button-print',
                    button: [
                        <security:authorize url="/storage/salesDeliveryOrderPrintView.do">
                        {
                            id: 'button-printview-DeliveryOrder',
                            name: '库位套打预览',
                            iconCls: 'button-preview',
                            handler: function () {
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/storage/salesDeliveryOrderPrint.do">
                        {
                            id: 'button-print-DeliveryOrder',
                            name: '库位套打',
                            iconCls: 'button-print',
                            handler: function () {
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/storage/salesOrderblankPrintView.do">
                        {
                            id: 'button-printview-orderblank',
                            name: '配货打印预览',
                            iconCls: 'button-preview',
                            handler: function () {
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/storage/salesOrderblankPrint.do">
                        {
                            id: 'button-print-orderblank',
                            name: '配货打印',
                            iconCls: 'button-print',
                            handler: function () {
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/storage/salesDispatchBillPrintView.do">
                        {
                            id: 'button-printview-DispatchBill',
                            name: '订单套打预览',
                            iconCls: 'button-preview',
                            handler: function () {
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/storage/salesDispatchBillPrint.do">
                        {
                            id: 'button-print-DispatchBill',
                            name: '订单套打',
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
            layoutid: 'sales-layout-dispatchBill',
            model: 'bill',
            type: 'view',
            tab: '销售发货通知单列表',
            taburl: '/sales/dispatchBillListPage.do',
            id: '${id}',
            datagrid: 'sales-datagrid-dispatchBillListPage'
        });
        $(document).keydown(function (event) {//alert(event.keyCode);
            switch (event.keyCode) {
                case 13: //Enter
                    if (chooseNo == "dispatchBill.remark") {
                        $("input[name='dispatchBill.remark']").blur();
                        beginAddDetail();
                    }
                    if (chooseNo == "unitnum") {
                        $("input[name=auxnum]").focus();
                        return false;
                    }
                    if (chooseNo == "auxnum") {
                        $("input[name=overnum]").focus();
                        return false;
                    }
                    if (chooseNo == "overnum") {
                        if ($("input[name=taxprice]").attr("readonly") == "readonly") {
                            $("input[name=remark]").focus();
                        }
                        else {
                            $("input[name=taxprice]").focus();
                        }
                        return false;
                    }
                    if (chooseNo == "taxprice") {
                        //$("input[name=notaxprice]").focus();
                        //return false;
                        //}
                        //if(chooseNo == "notaxprice"){
                        $("input[name=remark]").focus();
                        return false;
                    }
                    if (chooseNo == "remark") {
                        $("input[name=savegoon]").click();
                        return false;
                    }
                    break;
                case 27: //Esc
                    $("#sales-remark-billDetailAddPage").focus();
                    $("#sales-dialog-dispatchBillAddPage-content").dialog('close');
                    break;
            }
        });
        $(document).bind('keydown', 'ctrl+enter', function () {
            $("#sales-remark-billDetailAddPage").focus();
            $("#sales-savegoon-billDetailAddPage").trigger("click");
        });
        $(document).bind('keydown', '+', function () {
            $("#sales-remark-billDetailAddPage").focus();
            setTimeout(function () {
                $("#sales-savegoon-billDetailAddPage").trigger("click");
            }, 100);
            return false;
        });
    });

    function refreshPanel(url) { //更新panel
        $("#sales-panel-dispatchBill").panel('refresh', url);
    }
    function refreshPanelAddParam(url, param) { //更新panel
        $("#sales-panel-dispatchBill").panel({queryParams: param, href: url});
    }
    function order_tempSave_form_submit() { //暂存表单方法
        $("#sales-form-dispatchBillAddPage").form({
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
                    $("#sales-backid-dispatchBill").val(json.backid);
                    if (json.type == "add") {
                        $("#sales-buttons-dispatchBill").buttonWidget("addNewDataId", json.backid);
                    }
                    $("#sales-panel-dispatchBill").panel('refresh', 'sales/dispatchBillViewPage.do?id=' + json.backid);
                }
                else {
                    $.messager.alert("提醒", "暂存失败");
                }
            }
        });
    }
    function order_realSave_form_submit() { //保存表单方法
        $("#sales-form-dispatchBillAddPage").form({
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
                if (json.lock == true) {
                    $.messager.alert("提醒", "其他用户正在修改该数据，无法修改");
                    return false;
                }
                if (json.flag == true) {
                    var saveaudit = $("#sales-saveaudit-dispatchBillAddPage").val();
                    if (saveaudit == "saveaudit") {
                        if (json.auditflag) {
                            if (json.closeFlag) {
                                $.messager.alert("提醒", "保存审核成功。" + json.msg);
                                refreshPanel('sales/dispatchBillViewPage.do?id=' + json.id);
                            } else {
                                $.messager.alert("提醒", "保存审核成功，生成发货单:" + json.billId);

                                refreshPanel('sales/dispatchBillViewPage.do?id=' + json.id);

                                $("#sales-form-id-dispatchBillAddPage").val(json.backid);
                                $("#sales-status-dispatchBillAddPage").val("3");
                                $("#sales-canprint-dispatchBillAddPage").val("1");

                            }
                            tabsWindowURL("/sales/dispatchBillListPage.do").$("#sales-datagrid-dispatchBillListPage").datagrid('reload');
                        } else {
                            $.messager.alert("提醒", "保存成功,审核失败。" + json.msg);
                        }
                    } else {
                        $.messager.alert("提醒", "保存成功。");
                        $("#sales-panel-dispatchBill").panel('refresh', 'sales/dispatchBillEditPage.do?id=' + json.backid);
                    }
                    $("#sales-backid-dispatchBill").val(json.backid);
                    if (json.type == "add") {
                        $("#sales-buttons-dispatchBill").buttonWidget("addNewDataId", json.backid);
                    }
                    //$("#sales-panel-dispatchBill").panel('refresh', 'sales/dispatchBillViewPage.do?id='+ json.backid);
                }
                else {
                    $.messager.alert("提醒", "保存失败.单据不存在，或者单据已经审核通过。不能保存");
                }
            }
        });
    }
    function countTotal() { //计算合计
        var rows = $("#sales-datagrid-dispatchBillAddPage").datagrid('getRows');
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
        $("#sales-datagrid-dispatchBillAddPage").datagrid('reloadFooter', [{
            goodsid: '合计',
            unitnum: unitnum,
            taxamount: taxamount,
            notaxamount: notaxamount,
            tax: tax,
            deliverytype: '5'
        }]);
    }
    function customerCheck() { //添加商品明细前确定客户已选
        var customer = $("#sales-customer-dispatchBillAddPage-hidden").val();
        if (customer == '') {
            $.messager.alert("提醒", "请先选择客户再进行添加商品信息");
            $("#sales-customer-dispatchBillAddPage").focus();
            return false;
        }
        else {
            return customer;
        }
    }
    function beginAddDetail() { //开始添加商品信息
        var customer = $("#sales-customer-dispatchBillAddPage-hidden").val();
        if (customer == '') {
            $.messager.alert("提醒", "请先选择客户再进行添加商品信息");
            $("#sales-customer-dispatchBillAddPage").focus();
            return false;
        }
        $('<div id="sales-dialog-dispatchBillAddPage-content"></div>').appendTo('#sales-dialog-dispatchBillAddPage');
        $("#sales-dialog-dispatchBillAddPage-content").dialog({ //弹出新添加窗口
            title: '商品信息添加(按ESC退出)',
            maximizable: true,
            width: 600,
            height: 450,
            closed: false,
            modal: true,
            cache: false,
            resizable: true,
            href: 'sales/billDetailAddPage.do?cid=' + customer,
            onClose: function () {
                $('#sales-dialog-dispatchBillAddPage-content').dialog("destroy");
            },
            onLoad: function () {
                $("#sales-goodsId-billDetailAddPage").focus();
            }
        });
    }
    function addSaveDetail(go) { //添加新数据确定后操作，
        var flag = $("#sales-form-billDetailAddPage").form('validate');
        if (flag == false) {
            return false;
        }
        var form = $("#sales-form-billDetailAddPage").serializeJSON();
        var goodsJson = $("#sales-goodsId-billDetailAddPage").goodsWidget('getObject');
        form.goodsInfo = goodsJson;
        var customer = $("#sales-customer-dispatchBillAddPage-hidden").val();
        if (form.overnum != 0) {
            form.auxnumdetail = form.auxnum + form.auxunitname + form.overnum + form.unitname;
        } else {
            form.auxnumdetail = form.auxnum + form.auxunitname;
        }
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
            $("input[name=deliverydate]").val(deliverydate);
        }
        else { //否则直接关闭
            $("#sales-dialog-dispatchBillAddPage-content").dialog('close', true)
        }
        $("#sales-customer-dispatchBillAddPage").goodsWidget('readonly', true);
        countTotal(); //第添加一条商品明细计算一次合计
    }
    function beginEditDetail(rowData) { //开始修改商品信息
        var customer = $("#sales-customer-dispatchBillAddPage-hidden").val();
        if (customer == '') {
            $.messager.alert("提醒", "请先选择客户再进行添加商品信息");
            $("#sales-customer-dispatchBillAddPage").focus();
            return false;
        }
        var row = $wareList.datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        row.goodsname = row.goodsInfo.name;
        row.model = row.goodsInfo.model;
        row.brandName = row.goodsInfo.brandName;
        row.barcode = row.goodsInfo.barcode;
        var url = '';
        if (row.goodsid == undefined) {
            beginAddDetail();
        }
        else {
            url = 'sales/billDetailEditPage.do?cid=' + customer + '&goodsid=' + row.goodsid; //如果是修改页面，数据直接来源于datagrid中的json数据。
            $('<div id="sales-dialog-dispatchBillAddPage-content"></div>').appendTo('#sales-dialog-dispatchBillAddPage');
            $("#sales-dialog-dispatchBillAddPage-content").dialog({ //弹出修改窗口
                title: '商品信息修改(按ESC退出)',
                maximizable: true,
                width: 600,
                height: 450,
                closed: false,
                modal: true,
                cache: false,
                resizable: true,
                href: url,
                onClose: function () {
                    $('#sales-dialog-dispatchBillAddPage-content').dialog("destroy");
                },
                onLoad: function () {
                    $("#sales-form-billDetailEditPage").form('load', row);
                    $("#sales-auxunitname-billDetailAddPage").html(row.auxunitname);
                    $("#sales-unitname-billDetailAddPage").html(row.unitname);
                    $("#sales-storage-billDetailAddPage").widget("setValue", row.storageid);
                    $("#sales-storagename-billDetailAddPage").text(row.storagename);
                    $("#sales-boxnum-billDetailAddPage").val(formatterBigNumNoLen(row.goodsInfo.boxnum));
                    if (row.total == undefined) {
                        var storageid = $("#sales-storageid-orderAddPage").widget("getValue");
                        $.getJSON("storage/getStorageSummarySumByGoodsid.do", {
                            goodsid: row.goodsid,
                            storageid: storageid,
                            summarybatchid: row.summarybatchid
                        }, function (json) {
                            if (json.storageSummary != null) {
                                $("#sales-loading-billDetailAddPage").removeClass("img-loading").html("商品编码：<font color='green'>" + row.goodsid + "</font>&nbsp;可用量：<font color='green'>" + json.storageSummary.usablenum + json.storageSummary.unitname + "</font>");
                            } else {
                                $("#sales-loading-billDetailAddPage").removeClass("img-loading").html("商品编码：<font color='green'>仓库中不存在该商品</font>");
                            }
                        });
                    }
                    else {
                        $("#sales-loading-billDetailAddPage").removeClass("img-loading").html("商品编码：<font color='green'>" + row.goodsid + "</font>&nbsp;可用量：<font color='green'>" + row.total + row.sunitname + "</font>");
                    }
                    var isbatch = $("#sales-isbatch-billDetailAddPage").val();
                    if (isbatch == "1") {
                        var storageid = $("#sales-storage-dispatchBillAddPage").widget("getValue");
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
                        $("#sales-batchno-billDetailAddPage").attr("readonly", "readonly");
                        $("#sales-batchno-billDetailAddPage").addClass("readonly");
                    }
                    $("input[name=unitnum]").focus();

                    formaterNumSubZeroAndDot();

                    //判断是否手动改过含税单价
                    var ret = dispatch_AjaxConn({
                        id: row.goodsid,
                        unitnum: row.unitnum,
                        cid: customer,
                        date: $("input[name='dispatchBill.businessdate']").val()
                    }, 'sales/getAuxUnitNumAndPrice.do');
                    var retjson = $.parseJSON(ret);
                    if (formatterDefineMoney(row.taxprice, 6) != formatterDefineMoney(retjson.taxprice, 6)) {
                        dispatch_taxpricechange = "1";
                    } else {
                        dispatch_taxpricechange = "0";
                    }

                    $("#sales-form-billDetailEditPage").form('validate');
                }
            });
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
        if (form.overnum != 0) {
            form.auxnumdetail = form.auxnum + form.auxunitname + form.overnum + form.unitname;
        } else {
            form.auxnumdetail = form.auxnum + form.auxunitname;
        }
        $wareList.datagrid('updateRow', {index: rowIndex, row: form}); //将数据更新到列表中
        $("#sales-dialog-dispatchBillAddPage-content").dialog('close', true)
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
                if (index == -1) {
                    $("#sales-customer-dispatchBillAddPage").goodsWidget('readonly', false);
                }
            }
        });
    }
    //品牌折扣添加页面
    function beginAddBrandDiscountDetail() {
        var customer = $("#sales-customer-dispatchBillAddPage-hidden").val();
        if (customer == '') {
            $.messager.alert("提醒", "请先选择客户再进行添加商品信息");
            $("#sales-customer-dispatchBillAddPage").focus();
            return false;
        }
        $('<div id="sales-dialog-dispatchBillAddPage-content"></div>').appendTo('#sales-dialog-dispatchBillAddPage');
        $('#sales-dialog-dispatchBillAddPage-content').dialog({
            title: '品牌折扣添加',
            width: 400,
            height: 250,
            collapsible: false,
            minimizable: false,
            maximizable: true,
            resizable: true,
            closed: true,
            cache: false,
            href: 'sales/billDetailBrandDiscountAddPage.do?cid=' + customer,
            onClose: function () {
                $('#sales-dialog-dispatchBillAddPage-content').dialog("destroy");
            },
            onLoad: function () {
                $("#sales-dispatchBill-brandid").focus();
            }
        });
        $('#sales-dialog-dispatchBillAddPage-content').dialog("open");
    }
    //添加品牌折扣
    function addSaveDetailBrandDiscount() {
        var flag = $("#sales-form-dispatchBillDetailBrandAddPage").form('validate');
        if (flag == false) {
            return false;
        }
        var form = $("#sales-form-dispatchBillDetailBrandAddPage").serializeJSON();
        var widgetJson = $("#sales-dispatchBill-brandid").widget('getObject');
        var object = {id: widgetJson.id, name: widgetJson.name}
        form.goodsInfo = object;
        var rowIndex = 0;
        var rows = $wareList.datagrid('getRows');
        for (var i = 0; i < rows.length; i++) {
            var rowJson = rows[i];
            if (rowJson.goodsid == widgetJson.id && (rowJson.isdiscount == '1' || rowJson.isdiscount == '2')) {
                rowIndex = i;
                break;
            }
            if (rowJson.goodsid == undefined && rowJson.brandid == undefined) {
                rowIndex = i;
                break;
            }
        }
        if (rowIndex == rows.length - 1) {
            $wareList.datagrid('appendRow', {}); //如果是最后一行则添加一新行
        }
        $wareList.datagrid('updateRow', {index: rowIndex, row: form}); //将数据更新到列表中
        $("#sales-dialog-dispatchBillAddPage-content").dialog('close');
        countTotal();
    }
    //品牌折扣修改页面
    function beginEditDetailBrandDiscount() {
        var customer = $("#sales-customer-dispatchBillAddPage-hidden").val();
        if (customer == '') {
            $.messager.alert("提醒", "请先选择客户再进行添加商品信息");
            $("#sales-customer-dispatchBillAddPage").focus();
            return false;
        }
        var row = $wareList.datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        $('<div id="sales-dialog-dispatchBillAddPage-content"></div>').appendTo('#sales-dialog-dispatchBillAddPage');
        $('#sales-dialog-dispatchBillAddPage-content').dialog({
            title: '品牌折扣修改',
            width: 400,
            height: 250,
            collapsible: false,
            minimizable: false,
            maximizable: true,
            resizable: true,
            closed: true,
            cache: false,
            href: 'sales/billDetailBrandDiscountEditPage.do',
            modal: true,
            onClose: function () {
                $('#sales-dialog-dispatchBillAddPage-content').dialog("destroy");
            },
            onLoad: function () {
                getNumberBox("sales-dispatchBill-taxamount").focus();
                getNumberBox("sales-dispatchBill-taxamount").select();
            }
        });
        $('#sales-dialog-dispatchBillAddPage-content').dialog("open");
    }
    //品牌折扣修改保存
    function editSaveDetailBrandDiscount() {
        var flag = $("#sales-form-dispatchBillDetailBrandAddPage").form('validate');
        if (flag == false) {
            return false;
        }
        var form = $("#sales-form-dispatchBillDetailBrandAddPage").serializeJSON();
        var row = $wareList.datagrid('getSelected');
        var rowIndex = $wareList.datagrid('getRowIndex', row);
        form.goodsInfo = row.goodsInfo;
        $wareList.datagrid('updateRow', {index: rowIndex, row: form}); //将数据更新到列表中
        $("#sales-dialog-dispatchBillAddPage-content").dialog('close');
        countTotal();
    }
    //折扣添加页面
    function beginAddDiscountDetail() {
        var customer = $("#sales-customer-dispatchBillAddPage-hidden").val();
        if (customer == '') {
            $.messager.alert("提醒", "请先选择客户再进行添加商品信息");
            $("#sales-customer-dispatchBillAddPage").focus();
            return false;
        }
        $('<div id="sales-dialog-dispatchBillAddPage-content"></div>').appendTo('#sales-dialog-dispatchBillAddPage');
        $('#sales-dialog-dispatchBillAddPage-content').dialog({
            title: '商品折扣添加',
            width: 680,
            height: 400,
            collapsible: false,
            minimizable: false,
            maximizable: true,
            resizable: true,
            closed: true,
            cache: false,
            href: 'sales/billDetailDiscountAddPage.do?cid=' + customer,
            onClose: function () {
                $('#sales-dialog-dispatchBillAddPage-content').dialog("destroy");
            },
            onLoad: function () {
                $("#sales-dispatchBill-goodsid").focus();
            }
        });
        $('#sales-dialog-dispatchBillAddPage-content').dialog("open");
    }
    //折扣添加
    function addSaveDetailDiscount() {
        var flag = $("#sales-form-dispatchBillDetailAddPage").form('validate');
        if (flag == false) {
            return false;
        }
        var form = $("#sales-form-dispatchBillDetailAddPage").serializeJSON();
        var widgetJson = $("#sales-dispatchBill-goodsid").goodsWidget('getObject');
        form.goodsInfo = widgetJson;
        var rowIndex = 0;
        var rows = $wareList.datagrid('getRows');
        for (var i = 0; i < rows.length; i++) {
            var rowJson = rows[i];
            if (rowJson.goodsid == undefined && rowJson.isdiscount != "2") {
                rowIndex = i;
                break;
            }
            if (rowJson.goodsid == undefined && rowJson.isdiscount == '2' && rows[i + 1].goodsid == undefined && rows[i + 1].isdiscount != '2' && rows[i + 1].goodsid == undefined) {
                rowIndex = i + 1;
                break;
            }

        }
        if (rowIndex == rows.length - 1) {
            $wareList.datagrid('appendRow', {}); //如果是最后一行则添加一新行
        }
        $wareList.datagrid('updateRow', {index: rowIndex, row: form}); //将数据更新到列表中
        $("#sales-dialog-dispatchBillAddPage-content").dialog('close');
        countTotal();
    }
    //折扣修改页面
    function beginEditDetailDiscount() {
        var customer = $("#sales-customer-dispatchBillAddPage-hidden").val();
        if (customer == '') {
            $.messager.alert("提醒", "请先选择客户再进行添加商品信息");
            $("#sales-customer-dispatchBillAddPage").focus();
            return false;
        }
        var row = $wareList.datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        $('<div id="sales-dialog-dispatchBillAddPage-content"></div>').appendTo('#sales-dialog-dispatchBillAddPage');
        $('#sales-dialog-dispatchBillAddPage-content').dialog({
            title: '商品折扣修改',
            width: 680,
            height: 400,
            collapsible: false,
            minimizable: false,
            maximizable: true,
            resizable: true,
            closed: true,
            cache: false,
            href: 'sales/billDetailDiscountEditPage.do',
            modal: true,
            onClose: function () {
                $('#sales-dialog-dispatchBillAddPage-content').dialog("destroy");
            },
            onLoad: function () {
                getNumberBox("sales-dispatchBill-taxamount").focus();
                getNumberBox("sales-dispatchBill-taxamount").select();
            }
        });
        $('#sales-dialog-dispatchBillAddPage-content').dialog("open");
    }
    //折扣修改保存
    function editSaveDetailDiscount() {
        var flag = $("#sales-form-dispatchBillDetailAddPage").form('validate');
        if (flag == false) {
            return false;
        }
        var form = $("#sales-form-dispatchBillDetailAddPage").serializeJSON();
        var row = $wareList.datagrid('getSelected');
        var rowIndex = $wareList.datagrid('getRowIndex', row);
        form.goodsInfo = row.goodsInfo;
        $wareList.datagrid('updateRow', {index: rowIndex, row: form}); //将数据更新到列表中
        $("#sales-dialog-dispatchBillAddPage-content").dialog('close');
        countTotal();
    }
    //回车跳到下一个
    var chooseNo;
    function frm_focus(val) {
        chooseNo = val;
    }
    function frm_blur(val) {
        if (val == chooseNo) {
            chooseNo = "";
        }
    }
</script>
<%--打印开始 --%>
<script type="text/javascript">
    function updateDataGridPrintimes(id, type) {
        var thepage = tabsWindowURL("/sales/dispatchBillListPage.do");
        if (thepage == null) {
            return false;
        }
        var thegrid = thepage.$("#sales-datagrid-dispatchBillListPage");
        if (thegrid == null || thegrid.size() == 0) {
            return false;
        }

        if (id == null || id == "") {
            thegrid.datagrid('reload');
            return false;
        }
        //type 1 配货
        //type 2 库位
        //type 3订单顺序
        if (type == null || type == "") {
            thegrid.datagrid('reload');
        }

        var datarow = thegrid.datagrid("getRows");
        if (null != datarow && datarow.length > 0) {
            for (var i = 0; i < datarow.length; i++) {
                if (datarow[i].billno == id) {//销售单编号
                    var obj = {}
                    if (type == 2 || type == 3) {
                        if (datarow[i].printtimes && !isNaN(datarow[i].printtimes)) {
                            datarow[i].printtimes = datarow[i].printtimes + 1;
                        } else {
                            datarow[i].printtimes = 1;
                        }
                        obj.printtimes = datarow[i].printtimes;
                    }

                    if (type == 1 || type == 3) {
                        if (datarow[i].phprinttimes && !isNaN(datarow[i].phprinttimes)) {
                            if (type == 1) {
                                datarow[i].phprinttimes = datarow[i].phprinttimes + 1;
                            }
                        } else {
                            datarow[i].phprinttimes = 1;
                        }
                        obj.phprinttimes = datarow[i].phprinttimes;
                    }

                    thegrid.datagrid('updateRow', {index: i, row: obj});
                    break;
                }
            }
        }
    }
    $(function () {
        var printLimit = $("#sales-printlimit-dispatchBillAddPage").val() || 0;

        function getDataWithAudit(tableId, printParam, isPrint) {
            var billno = $("#sales-billno-dispatchBillAddPage").val();
            if (billno == "") {
                $.messager.alert("警告", "找不到要打印的信息!");
                return false;
            }
            var status = $("#sales-status-dispatchBillAddPage").val() || "";
            var printtimes = $("#sales-printtimes-dispatchBillAddPage").val() || 0;
            var fHPrintAfterSaleOutAudit = $("#sales-fHPrintAfterSaleOutAudit-dispatchBillAddPage").val() || "0";
            if (fHPrintAfterSaleOutAudit == "1") {
                if (status != '4') {
                    $.messager.alert("提醒", "抱歉，关闭状态下才能。<br/>" + billno + "此销售发货通知单不可打印");
                    return false;
                }
            } else {
                if (!(status == '3' || status == '4')) {
                    $.messager.alert("提醒", billno + "此销售发货通知单不可打印");
                    return false;
                }
            }
            printParam.saleidarrs = billno;
            if (printtimes > 0)
                printParam.printIds = [billno];
            return true;
        }

        function getDataNoAudit(tableId,printParam,isPrint){
            var billno = $("#sales-billno-dispatchBillAddPage").val();
            if (billno == "") {
                $.messager.alert("警告", "找不到要打印的信息!");
                return false;
            }
            var status = $("#sales-status-dispatchBillAddPage").val() || "";
            if (!( status == '3' || status == '4')) {
                $.messager.alert("提醒", billno + "此销售单不可进行配货打印");
                return false;
            }
            printParam.saleidarrs = billno;
            var printtimes = $("#sales-phprinttimes-dispatchBillAddPage").val() || 0;
            if (printtimes > 0)
                printParam.printIds = [billno];
            return true;
        }

        //库位套打
        AgReportPrint.init({
            id: "listPage-DeliveryOrder-dialog-print",
            code: "storage_deliveryorder",
            url_preview: "print/sales/salesDeliveryOrderPrintView.do",
            url_print: "print/sales/salesDeliveryOrderPrint.do",
            btnPreview: "button-printview-DeliveryOrder",
            btnPrint: "button-print-DeliveryOrder",
            printlimit: "${printlimit}",
            exPrintParam: {
                printOrder: 1
            },
            getData: getDataWithAudit,
            onPrintSuccess: function (option) {
                var billno = $("#sales-billno-dispatchBillAddPage").val();
                updateDataGridPrintimes(billno, 2);//库位打印次数更新
            },
            afterPrintHandler: function (option, printParam) {
                var printtimes = $("#sales-phprinttimes-dispatchBillAddPage").val();
                $("#sales-phprinttimes-dispatchBillAddPage").val(printtimes + 1);
                if (0 != printLimit) {
                    $("#sales-buttons-dispatchBill").buttonWidget("disableMenuItem", "button-print-orderblank");
                    $("#sales-buttons-dispatchBill").buttonWidget("disableMenuItem", "button-print-DispatchBill");
                    $("#sales-buttons-dispatchBill").buttonWidget("disableMenuItem", "button-print-DeliveryOrder");
                }
            }
        });
        //订单套打
        AgReportPrint.init({
            id: "listPage-dispatchbill-dialog-print",
            code: "storage_dispatchbill",
            url_preview: "print/sales/salesDispatchBillPrintView.do",
            url_print: "print/sales/salesDispatchBillPrint.do",
            btnPreview: "button-printview-DispatchBill",
            btnPrint: "button-print-DispatchBill",
            printlimit: printLimit,
            getData: getDataWithAudit,
            onPrintSuccess: function (option) {
                var billno = $("#sales-billno-dispatchBillAddPage").val();
                updateDataGridPrintimes(billno, 3);//订单打印次数更新
            },
            afterPrintHandler: function (option, printParam) {
                var printtimes = $("#sales-phprinttimes-dispatchBillAddPage").val();
                $("#sales-phprinttimes-dispatchBillAddPage").val(printtimes + 1);
                if (0 != printLimit) {
                    $("#sales-buttons-dispatchBill").buttonWidget("disableMenuItem", "button-print-DispatchBill");
                    $("#sales-buttons-dispatchBill").buttonWidget("disableMenuItem", "button-print-DeliveryOrder");
                }
            }
        });
        //配货打印
        AgReportPrint.init({
            id: "listPage-orderblank-dialog-print",
            code: "storage_orderblank",
            url_preview: "print/sales/salesOrderblankPrintView.do",
            url_print: "print/sales/salesOrderblankPrint.do",
            btnPreview: "button-printview-orderblank",
            btnPrint: "button-print-orderblank",
            printlimit: printLimit,
            exPrintParam: {
                printOrder: 1
            },
            getData: getDataNoAudit,
            onPrintSuccess: function (option) {
                var billno = $("#sales-billno-dispatchBillAddPage").val();
                updateDataGridPrintimes(billno, 1);//配单打印次数更新
            },
            afterPrintHandler: function (option, printParam) {
                var printtimes = $("#sales-phprinttimes-dispatchBillAddPage").val();
                $("#sales-phprinttimes-dispatchBillAddPage").val(printtimes + 1);
                if (0 != printLimit) {
                    $("#sales-buttons-dispatchBill").buttonWidget("disableMenuItem", "button-print-orderblank");
                }
            }
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
