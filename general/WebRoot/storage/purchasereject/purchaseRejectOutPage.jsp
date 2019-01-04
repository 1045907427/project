<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>采购退货出库单操作页面</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>
<body>
<div id="storage-layout-purchaseRejectOutPage" class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="storage-buttons-purchaseRejectOutPage" style="height:26px;"></div>
    </div>
    <div data-options="region:'center',border:false">
        <div id="storage-panel-purchaseRejectOutPage"></div>
    </div>
</div>
<div id="storage-panel-relation-upper"></div>
<div id="storage-panel-sourceQueryPage"></div>
<div id="workflow-addidea-dialog-page"></div>
<input type="hidden" id="storage-hidden-billid"/>
<script type="text/javascript">
    //根据初始的列与用户保存的列生成以及字段权限生成新的列
    var tableColJson = $("#storage-datagrid-purchaseRejectOutAddPage").createGridColumnLoad({
        name: 'storage_purchaseRejectOut_detail',
        frozenCol: [[]],
        commonCol: [[
            {field: 'goodsid', title: '商品编码', width: 70},
            {
                field: 'goodsname', title: '商品名称', width: 220, aliascol: 'goodsid',
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        return rowData.goodsInfo.name;
                    } else {
                        return "";
                    }
                }
            },
            {
                field: 'barcode', title: '条形码', width: 85, aliascol: 'goodsid',
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
                field: 'brandName', title: '商品品牌', width: 80, aliascol: 'goodsid', hidden: true,
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        return rowData.goodsInfo.brandName;
                    } else {
                        return "";
                    }
                }
            },
            {
                field: 'model', title: '规格型号', width: 80, aliascol: 'goodsid', hidden: true,
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        return rowData.goodsInfo.model;
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
            {field: 'unitname', title: '单位', width: 35},
            {
                field: 'unitnum', title: '数量', width: 60, align: 'right',
                formatter: function (value, row, index) {
                    return formatterBigNumNoLen(value);
                }
            },
            <c:if test="${map.taxprice == 'taxprice'}">
            {
                field: 'taxprice', title: '单价', width: 60, align: 'right',
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'boxprice', title: '箱价', width: 60, aliascol: 'taxprice', align: 'right',
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            </c:if>
            <c:if test="${map.taxamount == 'taxamount'}">
            {
                field: 'taxamount', title: '金额', width: 60, align: 'right',
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            </c:if>
            <c:if test="${map.notaxprice == 'notaxprice'}">
            {
                field: 'notaxprice', title: '未税单价', width: 60, align: 'right', hidden: true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'noboxprice', title: '未税箱价', width: 60, aliascol: 'notaxprice', align: 'right', hidden: true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            </c:if>
            <c:if test="${map.notaxamount == 'notaxamount'}">
            {
                field: 'notaxamount', title: '未税金额', width: 60, align: 'right', hidden: true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            </c:if>
            {field: 'taxtypename', title: '税种', width: 60, align: 'right', hidden: true},
            <c:if test="${map.tax == 'tax'}">
            {
                field: 'tax', title: '税额', width: 60, align: 'right', hidden: true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            </c:if>
            {field: 'auxnumdetail', title: '辅数量', width: 60, align: 'right'},
            {
                field: 'storagelocationid', title: '所属库位', width: 100,
                formatter: function (value, row, index) {
                    return row.storagelocationname;
                }
            },
            {field: 'batchno', title: '批次号', width: 80},
            {field: 'produceddate', title: '生产日期', width: 80},
            {field: 'deadline', title: '有效截止日期', width: 80},
            {field: 'remark', title: '备注', width: 100}
        ]]
    });
    var page_url = "storage/purchaseRejectOutAddPage.do";
    var page_type = '${type}';
    if (page_type == "view" || page_type == "handle") {
        page_url = "storage/purchaseRejectOutViewPage.do?id=${id}";
    } else if (page_type == "edit") {
        var flag = '${flag}';
        if (flag == "true") {
            page_url = "storage/purchaseRejectOutEditPage.do?id=${id}";
        } else {
            $.messager.alert("提醒", "该订单已不存在！");
            page_url = null;
        }
    }
    $(function () {
        $("#storage-panel-purchaseRejectOutPage").panel({
            href: page_url,
            cache: false,
            maximized: true,
            border: false
        });
        //按钮
        $("#storage-buttons-purchaseRejectOutPage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/storage/purchaseRejectOutAddPage.do">
                {
                    type: 'button-add',
                    handler: function () {
                        $("#storage-panel-purchaseRejectOutPage").panel({
                            href: 'storage/purchaseRejectOutAddPage.do',
                            title: '',
                            cache: false,
                            maximized: true,
                            border: false
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/addPurchaseRejectOutHold.do">
                {
                    type: 'button-hold',
                    handler: function () {
                        $.messager.confirm("提醒", "确定暂存该采购退货出库单信息？", function (r) {
                            if (r) {
                                var type = $("#storage-buttons-purchaseRejectOutPage").buttonWidget("getOperType");
                                if (type == "add") {
                                    //暂存
                                    $("#storage-form-purchaseRejectOutAdd").attr("action", "storage/addPurchaseRejectOutHold.do");
                                    $("#storage-form-purchaseRejectOutAdd").submit();
                                } else if (type == "edit") {
                                    //暂存
                                    $("#storage-form-purchaseRejectOutAdd").attr("action", "storage/editPurchaseRejectOutHold.do");
                                    $("#storage-form-purchaseRejectOutAdd").submit();
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/addPurchaseRejectOutSave.do">
                {
                    type: 'button-save',
                    handler: function () {
                        $.messager.confirm("提醒", "确定保存该采购退货出库单信息？", function (r) {
                            if (r) {
                                var type = $("#storage-buttons-purchaseRejectOutPage").buttonWidget("getOperType");
                                if (type == "add") {
                                    //暂存
                                    $("#storage-form-purchaseRejectOutAdd").attr("action", "storage/addPurchaseRejectOutSave.do");
                                    $("#storage-form-purchaseRejectOutAdd").submit();
                                } else if (type == "edit") {
                                    $("#storage-form-purchaseRejectOutAdd").attr("action", "storage/editPurchaseRejectOutSave.do");
                                    $("#storage-form-purchaseRejectOutAdd").submit();
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/addPurchaseRejectOutSaveAudit.do">
                {
                    type: 'button-saveaudit',
                    handler: function () {
                        $.messager.confirm("提醒", "确定保存并审核该采购退货出库单信息？", function (r) {
                            if (r) {
                                var type = $("#storage-buttons-purchaseRejectOutPage").buttonWidget("getOperType");
                                $("#storage-purchaseEnter-saveaudit").val("saveaudit");
                                if (type == "add") {
                                    //暂存
                                    $("#storage-form-purchaseRejectOutAdd").attr("action", "storage/addPurchaseRejectOutSave.do");
                                    $("#storage-form-purchaseRejectOutAdd").submit();
                                } else if (type == "edit") {
                                    $("#storage-form-purchaseRejectOutAdd").attr("action", "storage/editPurchaseRejectOutSave.do");
                                    $("#storage-form-purchaseRejectOutAdd").submit();
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/purchaseRejectOutGiveUp.do">
                {
                    type: 'button-giveup',
                    handler: function () {
                        var type = $("#storage-buttons-purchaseRejectOutPage").buttonWidget("getOperType");
                        if (type == "add") {
                            var currTitle = top.$('#tt').tabs('getSelected').panel('options').title;
                            top.closeTab(currTitle);
                        }
                        else if (type == "edit") {
                            var id = $("#storage-hidden-billid").val();
                            if (id == "") {
                                return false;
                            }
                            $("#storage-panel-purchaseRejectOutPage").panel({
                                href: 'storage/purchaseRejectOutViewPage.do?id=' + id,
                                title: '',
                                cache: false,
                                maximized: true,
                                border: false
                            });
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/deletePurchaseRejectOut.do">
                {
                    type: 'button-delete',
                    handler: function () {
                        $.messager.confirm("提醒", "是否删除当前采购退货出库单？", function (r) {
                            if (r) {
                                var id = $("#storage-hidden-billid").val();
                                if (id != "") {
                                    loading("删除中..");
                                    $.ajax({
                                        url: 'storage/deletePurchaseRejectOut.do?id=' + id,
                                        type: 'post',
                                        dataType: 'json',
                                        success: function (json) {
                                            loaded();
                                            if (json.flag) {
                                                var object = $("#storage-buttons-purchaseRejectOutPage").buttonWidget("removeData", id);
                                                $.messager.alert("提醒", "删除成功");
                                                if (null != object) {
                                                    $("#storage-panel-purchaseRejectOutPage").panel({
                                                        href: 'storage/purchaseRejectOutEditPage.do?id=' + object.id,
                                                        title: '',
                                                        cache: false,
                                                        maximized: true,
                                                        border: false
                                                    });
                                                } else {
                                                    parent.closeNowTab();
                                                }
                                            } else {
                                                $.messager.alert("提醒", "删除失败");
                                            }
                                        },
                                        error: function () {
                                            loaded();
                                            $.messager.alert("错误", "删除出错")
                                        }
                                    });
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/auditPurchaseRejectOut.do">
                {
                    type: 'button-audit',
                    handler: function () {
                        $.messager.confirm("提醒", "是否审核采购退货出库单？", function (r) {
                            if (r) {
                                var id = $("#storage-hidden-billid").val();
                                if (id != "") {
                                    loading("审核中..");
                                    $.ajax({
                                        url: 'storage/auditPurchaseRejectOut.do?id=' + id,
                                        type: 'post',
                                        dataType: 'json',
                                        success: function (json) {
                                            loaded();
                                            if (json.flag) {
                                                $.messager.alert("提醒", "审核成功");

                                                $("#storage-panel-purchaseRejectOutPage").panel('refresh', 'storage/purchaseRejectOutViewPage.do?id=' + id);

                                                //刷新列表
                                                tabsWindowURL("/storage/showPurchaseRejectOutListPage.do").$("#storage-datagrid-purchaseRejectOutPage").datagrid('reload');

                                            } else {
                                                $.messager.alert("提醒", "审核失败</br>" + json.msg);
                                            }
                                        },
                                        error: function () {
                                            loaded();
                                            $.messager.alert("错误", "审核出错");
                                        }
                                    });
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/oppauditPurchaseRejectOut.do">
                {
                    type: 'button-oppaudit',
                    handler: function () {
                        $.messager.confirm("提醒", "是否反审采购退货出库单？", function (r) {
                            if (r) {

                                var id = $("#storage-hidden-billid").val();
                                var ischeck = $("#storage-ischeck-purchaseRejectOutAddPage").val();

                                var sourcetype = $("#storage-purchaseRejectOut-sourcetype").val();
                                if (sourcetype == "2") {
                                    $.messager.alert("提醒", "抱歉,来源类型代配送的不能反审");
                                    return false;
                                }
                                if ("1" == ischeck) {
                                    $.messager.alert("提醒", "抱歉，已经验收的不能反审");
                                    return false;
                                }
                                if (id != "") {
                                    loading("反审中..");
                                    $.ajax({
                                        url: 'storage/oppauditPurchaseRejectOut.do?id=' + id,
                                        type: 'post',
                                        dataType: 'json',
                                        success: function (json) {
                                            loaded();
                                            if (json.flag) {
                                                $.messager.alert("提醒", "反审成功");
                                                $("#storage-panel-purchaseRejectOutPage").panel('refresh', 'storage/purchaseRejectOutEditPage.do?id=' + id);
                                                //刷新列表
                                                tabsWindowURL("/storage/showPurchaseRejectOutListPage.do").$("#storage-datagrid-purchaseRejectOutPage").datagrid('reload');
                                            } else {
                                                $.messager.alert("提醒", "反审失败</br>" + (null != json.msg ? json.msg : ""));
                                            }
                                        },
                                        error: function () {
                                            loaded();
                                            $.messager.alert("错误", "审核出错");
                                        }
                                    });
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/showSaleRejectOrderRelationPage.do">
                {
                    type: 'button-relation',
                    button: [
                        {},
                        <security:authorize url="/storage/showPurchaseRejectOutRelationUpperPage.do">
                        {
                            type: 'relation-upper',
                            handler: function () {
                                $("#storage-panel-relation-upper").dialog({
                                    href: "storage/showPurchaseRejectOutRelationUpperPage.do",
                                    title: "上游单据查询",
                                    closed: false,
                                    modal: true,
                                    cache: false,
                                    width: 500,
                                    height: 300,
                                    buttons: [{
                                        text: '查询',
                                        handler: function () {
                                            var queryJSON = $("#storage-form-query-dispatchBill").serializeJSON();
                                            $("#storage-panel-relation-upper").dialog('close', true);
                                            var type = $("#storage-buttons-purchaseRejectOutPage").buttonWidget("getOperType");
                                            $("#storage-panel-sourceQueryPage").dialog({
                                                title: '采购退货单列表',
                                                fit: true,
                                                closed: false,
                                                modal: true,
                                                cache: false,
                                                maximizable: true,
                                                resizable: true,
                                                href: 'storage/showPurchaseRejectOutSourceListPage.do',
                                                buttons: [{
                                                    text: '确定',
                                                    handler: function () {
                                                        var dispatchbill = $("#storage-orderDatagrid-dispatchBillSourcePage").datagrid('getSelected');
                                                        if (dispatchbill == null) {
                                                            $.messager.alert("提醒", "请选择一条订单记录");
                                                            return false;
                                                        }
                                                        $("#storage-panel-sourceQueryPage").dialog('close', true);
                                                        //生成采购退货出库单
                                                        loading("生成中..");
                                                        $.ajax({
                                                            url: 'storage/addPurchaseRejectOutByRefer.do',
                                                            type: 'post',
                                                            dataType: 'json',
                                                            data: {id: dispatchbill.id},
                                                            success: function (json) {
                                                                loaded();
                                                                if (json.flag) {
                                                                    $.messager.alert("提醒", "生成成功");
                                                                    $("#storage-panel-purchaseRejectOutPage").panel({
                                                                        href: 'storage/purchaseRejectOutViewPage.do?id=' + json.id,
                                                                        title: '',
                                                                        cache: false,
                                                                        maximized: true,
                                                                        border: false
                                                                    });
                                                                } else {
                                                                    $.messager.alert("提醒", "生成失败");
                                                                }
                                                            }
                                                        });
                                                    }
                                                }],
                                                onLoad: function () {
                                                    $("#storage-orderDatagrid-dispatchBillSourcePage").datagrid({
                                                        columns: [[
                                                            {field: 'id', title: '申请单编号', width: 120, sortable: true},
                                                            {
                                                                field: 'businessdate',
                                                                title: '业务日期',
                                                                width: 70,
                                                                sortable: true
                                                            },
                                                            {
                                                                field: 'buydeptid', title: '采购部门', width: 100,
                                                                formatter: function (value, row, index) {
                                                                    return row.buydeptname;
                                                                }
                                                            },
                                                            {field: 'addusername', title: '制单人', width: 80},
                                                            {
                                                                field: 'adddeptname',
                                                                title: '制单人部门',
                                                                width: 100,
                                                                hidden: true
                                                            },
                                                            {
                                                                field: 'modifyusername',
                                                                title: '修改人',
                                                                width: 120,
                                                                hidden: true
                                                            },
                                                            {field: 'auditusername', title: '审核人', width: 100},
                                                            {
                                                                field: 'auditdate',
                                                                title: '审核时间',
                                                                width: 100,
                                                                hidden: true,
                                                                sortable: true
                                                            },
                                                            {
                                                                field: 'status', title: '状态', width: 100,
                                                                formatter: function (value, row, index) {
                                                                    return getSysCodeName('status', value);
                                                                }
                                                            },
                                                            {field: 'remark', title: '备注', width: 180},
                                                            {
                                                                field: 'addtime',
                                                                title: '制单时间',
                                                                width: 120,
                                                                sortable: true
                                                            }
                                                        ]],
                                                        fit: true,
                                                        method: 'post',
                                                        rownumbers: true,
                                                        pagination: true,
                                                        idField: 'id',
                                                        singleSelect: true,
                                                        fitColumns: true,
                                                        url: 'purchase/returnorder/showReturnOrderPageList.do',
                                                        queryParams: queryJSON,
                                                        onClickRow: function (index, data) {
                                                            $("#storage-detailDatagrid-dispatchBillSourcePage").datagrid({
                                                                url: 'purchase/returnorder/showReturnOrderDetailReferList.do',
                                                                queryParams: {
                                                                    orderidarrs: data.id
                                                                }
                                                            });
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    }]
                                });
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/storage/showPurchaseRejectOutSourcePage.do">
                        {
                            type: 'relation-upper-view',
                            handler: function () {
                                var sourceid = $("#storage-purchaseRejectOut-sourceid").val();
                                if (null != sourceid && sourceid != "") {
                                    var basePath = $("#basePath").attr("href");
                                    top.addOrUpdateTab(basePath + 'purchase/returnorder/returnOrderPage.do?type=view&id=' + sourceid, "采购退货单");
                                }
                            }
                        },
                        </security:authorize>
                        {}
                    ]
                },
                </security:authorize>
                <security:authorize url="/storage/purchaseRejectOutWorkflow.do">
                {
                    type: 'button-workflow',
                    button: [
                        {
                            type: 'workflow-submit',
                            handler: function () {
                                $.messager.confirm("提醒", "是否提交该采购退货出库单信息到工作流?", function (r) {
                                    if (r) {
                                        var id = $("#storage-hidden-billid").val();
                                        if (id == "") {
                                            $.messager.alert("警告", "没有需要提交工作流的信息!");
                                            return false;
                                        }
                                        loading("提交中..");
                                        $.ajax({
                                            url: 'storage/submitPurchaseRejectOutProcess.do',
                                            dataType: 'json',
                                            type: 'post',
                                            data: 'id=' + id,
                                            success: function (json) {
                                                loaded();
                                                if (json.flag == true) {
                                                    $.messager.alert("提醒", "提交成功!");
                                                    $("#storage-panel-purchaseRejectOutPage").panel("refresh");
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
                                var id = $("#storage-hidden-billid").val();
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
                                var id = $("#storage-hidden-billid").val();
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
                <security:authorize url="/storage/purchaseRejectOutViewBackPage.do">
                {
                    type: 'button-back',
                    handler: function (data) {
                        $("#storage-panel-purchaseRejectOutPage").panel({
                            href: 'storage/purchaseRejectOutEditPage.do?id=' + data.id,
                            title: '',
                            cache: false,
                            maximized: true,
                            border: false
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/purchaseRejectOutViewNextPage.do">
                {
                    type: 'button-next',
                    handler: function (data) {
                        $("#storage-panel-purchaseRejectOutPage").panel({
                            href: 'storage/purchaseRejectOutEditPage.do?id=' + data.id,
                            title: '',
                            cache: false,
                            maximized: true,
                            border: false
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/purchaseRejectOutPrintView.do">
                {
                    type: 'button-preview',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/purchaseRejectOutPrint.do">
                {
                    type: 'button-print',
                    handler: function () {
                    }
                },
                </security:authorize>
                {}
            ],
            layoutid: 'storage-layout-purchaseRejectOutPage',
            model: 'bill',
            type: 'view',
            tab: '采购退货出库单列表',
            taburl: '/storage/showPurchaseRejectOutListPage.do',
            id: '${id}',
            datagrid: 'storage-datagrid-purchaseRejectOutPage'
        });
    });
    //显示采购退货出库单明细添加页面
    function beginAddDetail() {
        //验证表单
        var flag = $("#storage-form-purchaseRejectOutAdd").form('validate');
        if (flag == false) {
            return false;
        }
        var storageid = $("#storage-purchaseRejectOut-storageid").val();
        var supplierid = $("#storage-purchaseRejectOut-supplierid").supplierWidget("getValue");
        $('<div id="storage-dialog-purchaseRejectOutAddPage-content"></div>').appendTo('#storage-dialog-purchaseRejectOutAddPage');
        $('#storage-dialog-purchaseRejectOutAddPage-content').dialog({
            title: '采购退货出库单明细添加',
            width: 680,
            height: 400,
            collapsible: false,
            minimizable: false,
            maximizable: true,
            resizable: true,
            closed: true,
            cache: false,
            modal: true,
            href: 'storage/showPurchaseRejectOutDetailAddPage.do?storageid=' + storageid + "&supplierid=" + supplierid,
            onClose: function () {
                $('#storage-dialog-purchaseRejectOutAddPage-content').dialog("destroy");
            },
            onLoad: function () {
                $("#storage-purchaseRejectOut-goodsid").focus();
            }
        });
        $('#storage-dialog-purchaseRejectOutAddPage-content').dialog("open");
    }
    //显示采购退货出库单明细修改页面
    function beginEditDetail() {
        //验证表单
        var flag = $("#storage-form-purchaseRejectOutAdd").form('validate');
        if (flag == false) {
            return false;
        }
        var row = $("#storage-datagrid-purchaseRejectOutAddPage").datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        if (row.goodsid == undefined) {
            beginAddDetail();
        } else {
            $('<div id="storage-dialog-purchaseRejectOutAddPage-content"></div>').appendTo('#storage-dialog-purchaseRejectOutAddPage');
            $('#storage-dialog-purchaseRejectOutAddPage-content').dialog({
                title: '采购退货出库单明细修改',
                width: 680,
                height: 400,
                collapsible: false,
                minimizable: false,
                maximizable: true,
                resizable: true,
                closed: true,
                cache: false,
                modal: true,
                href: 'storage/showPurchaseRejectOutDetailEditPage.do',
                modal: true,
                onClose: function () {
                    $('#storage-dialog-purchaseRejectOutAddPage-content').dialog("destroy");
                },
                onLoad: function () {
                    $("#storage-purchaseRejectOut-unitnum").focus();
                    $("#storage-purchaseRejectOut-unitnum").select();

                    formaterNumSubZeroAndDot();

                    $("#storage-form-purchaseRejectOutDetailAddPage").form('validate');
                }
            });
            $('#storage-dialog-purchaseRejectOutAddPage-content').dialog("open");
        }
    }
    //保存发货单明细
    function addSaveDetail(goFlag) { //添加新数据确定后操作，
        var flag = $("#storage-form-purchaseRejectOutDetailAddPage").form('validate');
        if (flag == false) {
            return false;
        }
        var form = $("#storage-form-purchaseRejectOutDetailAddPage").serializeJSON();
        var widgetJson = $("#storage-purchaseRejectOut-goodsid").storageGoodsWidget('getObject');
        var goodsInfo = {
            id: widgetJson.goodsid, name: widgetJson.goodsname, brandName: widgetJson.brandname,
            model: widgetJson.model, barcode: widgetJson.barcode
        };
        form.goodsInfo = goodsInfo;
        $.ajax({
            url: 'basefiles/getItemByGoodsAndStorage.do',
            data:{
                goodsid:widgetJson.goodsid,
                storageid:$("#storage-purchaseRejectOut-storageid").val()
            },
            async: false,
            type: 'post',
            dataType: 'json',
            success: function (json) {
                form.goodsInfo.itemno=json.itemno;
            },
        });
        var rowIndex = 0;
        var rows = $("#storage-datagrid-purchaseRejectOutAddPage").datagrid('getRows');
        var updateFlag = false;
        for (var i = 0; i < rows.length; i++) {
            var rowJson = rows[i];
            if (rowJson.goodsid == widgetJson.goodsid) {
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
            $("#storage-datagrid-purchaseRejectOutAddPage").datagrid('appendRow', {}); //如果是最后一行则添加一新行
        }
        if (updateFlag) {
            $.messager.alert("提醒", "添加的明细商品重复，覆盖历史记录！");
        }
        $("#storage-datagrid-purchaseRejectOutAddPage").datagrid('updateRow', {index: rowIndex, row: form}); //将数据更新到列表中
        if (goFlag) { //go为true确定并继续添加一条
            otherEnterformReset();
        }
        else { //否则直接关闭
            $("#storage-dialog-purchaseRejectOutAddPage-content").dialog('destroy');
        }
        $("#storage-purchaseRejectOut-storageid").attr('disabled', "disabled");
        $("#storage-purchaseRejectOut-supplierid").supplierWidget("readonly", true);
        countTotal();

    }
    //修改保存
    function editSaveDetail(goFlag) {
        var flag = $("#storage-form-purchaseRejectOutDetailAddPage").form('validate');
        if (flag == false) {
            return false;
        }
        var form = $("#storage-form-purchaseRejectOutDetailAddPage").serializeJSON();
        var row = $("#storage-datagrid-purchaseRejectOutAddPage").datagrid('getSelected');
        var rowIndex = $("#storage-datagrid-purchaseRejectOutAddPage").datagrid('getRowIndex', row);
        form.goodsInfo = row.goodsInfo;
        $("#storage-datagrid-purchaseRejectOutAddPage").datagrid('updateRow', {index: rowIndex, row: form}); //将数据更新到列表中
        if (goFlag) { //go为true确定并继续添加一条
            var rowSelected = $("#storage-datagrid-purchaseRejectOutAddPage").datagrid("getSelected");
            var rowIndex = $("#storage-datagrid-purchaseRejectOutAddPage").datagrid("getRowIndex", rowSelected);
            var rows = $("#storage-datagrid-purchaseRejectOutAddPage").datagrid("getRows");
            var rownums = 0;
            for (var i = 0; i < rows.length; i++) {
                if (rows[i].goodsid != null) {
                    rownums++;
                }
            }
            if (rowIndex < rownums - 1) {
                rowIndex = rowIndex + 1;
                $("#storage-datagrid-purchaseRejectOutAddPage").datagrid("selectRow", rowIndex);
                $("#storage-form-purchaseRejectOutDetailAddPage").form('clear');
                //加载数据
                var object = $("#storage-datagrid-purchaseRejectOutAddPage").datagrid("getSelected");
                $("#storage-form-purchaseRejectOutDetailAddPage").form("load", object);
                $("#storage-purchaseRejectOut-goodsname").val(object.goodsInfo.name);
                $("#storage-purchaseRejectOut-goodsbrandName").val(object.goodsInfo.brandName);
                $("#storage-purchaseRejectOut-goodsmodel").val(object.goodsInfo.model);
                $("#storage-purchaseRejectOut-goodsbarcode").val(object.goodsInfo.barcode);
                $("#storage-purchaseRejectOut-unitnum").val(parseFloat(object.unitnum));
                $("#storage-purchaseRejectOut-unitnum-aux").val(parseFloat(object.auxnum));
                $("#storage-purchaseRejectOut-unitnum-unit").val(parseFloat(object.auxremainder));
                $("#storage-purchaseRejectOut-goodsunitname1").html(object.unitname);
                $("#storage-purchaseRejectOut-auxunitname1").html(object.auxunitname);

                $("#storage-purchaseRejectOut-unitnum").focus();
                $("#storage-purchaseRejectOut-unitnum").select();
                goodsNumControl();
            } else {
                $.messager.alert("提醒", "已经到最后一条了！");
                $("#storage-dialog-purchaseRejectOutAddPage-content").dialog('destroy');
            }
        }
        else { //否则直接关闭
            $("#storage-dialog-purchaseRejectOutAddPage-content").dialog('destroy');
        }
        countTotal();
    }
    //删除明细
    function removeDetail() {
        var row = $("#storage-datagrid-purchaseRejectOutAddPage").datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        $.messager.confirm("提醒", "确定删除该商品明细?", function (r) {
            if (r) {
                var rowIndex = $("#storage-datagrid-purchaseRejectOutAddPage").datagrid('getRowIndex', row);
                $("#storage-datagrid-purchaseRejectOutAddPage").datagrid('deleteRow', rowIndex);
                countTotal();
                var rows = $("#storage-datagrid-purchaseRejectOutAddPage").datagrid('getRows');
                var index = -1;
                for (var i = 0; i < rows.length; i++) {
                    if (rows[i].goodsid != undefined) {
                        index = i;
                        break;
                    }
                }
                if (index == -1) {
                    $("#storage-purchaseRejectOut-storageid").widget('enable');
                    $("#storage-purchaseRejectOut-supplierid").supplierWidget("readonly", false);
                }
            }
        });
    }
    //计算合计
    function countTotal() {
        var rows = $("#storage-datagrid-purchaseRejectOutAddPage").datagrid('getRows');
        var countNum = 0;
        var taxamount = 0;
        var notaxamount = 0;
        var tax = 0;
        for (var i = 0; i < rows.length; i++) {
            countNum = Number(countNum) + Number(rows[i].unitnum == undefined ? 0 : rows[i].unitnum);
            taxamount = Number(taxamount) + Number(rows[i].taxamount == undefined ? 0 : rows[i].taxamount);
            notaxamount = Number(notaxamount) + Number(rows[i].notaxamount == undefined ? 0 : rows[i].notaxamount);
            tax = Number(tax) + Number(rows[i].tax == undefined ? 0 : rows[i].tax);
        }
        $("#storage-datagrid-purchaseRejectOutAddPage").datagrid("reloadFooter", [{
            goodsid: '合计',
            unitnum: countNum,
            taxamount: taxamount,
            notaxamount: notaxamount,
            tax: tax
        }]);
    }
    $(function () {
        //关闭明细添加页面
        $(document).bind('keydown', 'esc', function () {
            $("#storage-purchaseRejectOut-remark").focus();
            $("#storage-dialog-purchaseRejectOutAddPage-content").dialog("close");
        });
        $(document).bind('keydown', 'ctrl+enter', function () {
            $("#storage-purchaseRejectOut-remark").focus();
            $("#storage-savegoon-purchaseRejectOutDetailAddPage").trigger("click");
            $("#storage-savegoon-purchaseRejectOutDetailEditPage").trigger("click");
            return false;
        });
        $(document).bind('keydown', '+', function () {
            $("#storage-purchaseRejectOut-remark").focus();
            setTimeout(function () {
                $("#storage-savegoon-purchaseRejectOutDetailAddPage").trigger("click");
                $("#storage-savegoon-purchaseRejectOutDetailEditPage").trigger("click");
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
            id: "purchaseRejectOutPage-dialog-print",
            code: "storage_purchasereject",
            url_preview: "print/storage/purchaseRejectOutPrintView.do",
            url_print: "print/storage/purchaseRejectOutPrint.do",
            btnPreview: "button-preview",
            btnPrint: "button-print",
            printlimit: "${printlimit}",
            getData: function (tableId, printParam) {
                var id = $("#storage-hidden-billid").val();
                if (id == "") {
                    $.messager.alert("警告", "找不到要打印的信息!");
                    return false;
                }
                var status = $("#storage-purchaseRejectOut-status-select").val() || "";
                printParam.idarrs = id;

                var printtimes = $("#storage-printtimes-purchaseRejectOutAddPage").val() || 0;
                if (printtimes > 0)
                    printParam.printIds = [id];
                return true;
            },
            printAfterHandler: function (option, printParam) {
                var printtimes = $("#storage-printtimes-purchaseRejectOutAddPage").val();
                $("#storage-printtimes-purchaseRejectOutAddPage").val(printtimes + 1);
                var printlimit = "${printlimit}";
                if (0 != printlimit && printlimit != "") {
                    $("#storage-buttons-purchaseRejectOutPage").buttonWidget("disableMenuItem", "button-print");
                }
            }
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
