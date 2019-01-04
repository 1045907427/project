<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>采购入库单操作页面</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>
<body>
<div id="storage-layout-purchaseEnterPage" class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="storage-buttons-purchaseEnterPage" style="height:26px;"></div>
    </div>
    <div data-options="region:'center',border:false">
        <div id="storage-panel-purchaseEnterPage"></div>
    </div>
</div>
<div id="storage-panel-relation-upper"></div>
<div id="storage-panel-sourceQueryPage"></div>
<div id="workflow-addidea-dialog-page"></div>
<input type="hidden" id="storage-hidden-billid"/>
<div style="display:none">
    <%--通用 --%>
    <div id="listPage-PurchaseEnter-dialog-print">
        <form action="" id="listPage-PurchaseEnter-dialog-print-form" method="post">
            <table>
                <tr>
                    <td>打印模板：</td>
                    <td>
                        <select id="listPage-PurchaseEnter-dialog-print-templet" name="templetid">
                        </select>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<script type="text/javascript">
    //根据初始的列与用户保存的列生成以及字段权限生成新的列
    var tableColJson = $("#storage-datagrid-purchaseEnterAddPage").createGridColumnLoad({
        name: 'storage_purchaseEnter_detail',
        frozenCol: [[]],
        commonCol: [[
            {field: 'goodsid', title: '商品编码', width: 70},
            {
                field: 'goodsname', title: '商品名称', width: 220, aliascol: 'goodsid',
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        if (rowData.goodstype == '1') {
                            return "<font color='blue'>&nbsp;赠 </font>" + rowData.goodsInfo.name;
                        } else {
                            return rowData.goodsInfo.name;
                        }

                    } else {
                        return "";
                    }
                }
            },
            {
                field: 'goodstype', title: '商品类型', width: 220, aliascol: 'goodsid',hidden:true,
                formatter: function (value, rowData, rowIndex) {
                    if (value == "0") {
                        return "正常商品";
                    } else if (value == "1") {
                        return "赠品";
                    }else{
                        return "";
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
            {field: 'barcode', title: '条形码', width: 90, aliascol: 'goodsid'},
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
                field: 'boxprice', title: '箱价', aliascol: 'taxprice', width: 60, align: 'right',
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
                field: 'noboxprice', title: '未税箱价', aliascol: 'notaxprice', width: 60, align: 'right',
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
                field: 'storageid', title: '入库仓库', width: 100, hidden: true,
                formatter: function (value, row, index) {
                    return row.storagename;
                }
            },
            {
                field: 'storagelocationid', title: '所属库位', width: 100,
                formatter: function (value, row, index) {
                    return row.storagelocationname;
                }
            },
            {field: 'batchno', title: '批次号', width: 80},
            {field: 'produceddate', title: '生产日期', width: 80},
            {field: 'deadline', title: '有效截止日期', width: 80, hidden: true},
            {field: 'arrivedate', title: '实际到货日期', width: 100, hidden: true},
            {field: 'remark', title: '备注', width: 100}
        ]]
    });
    var page_url = "storage/purchaseEnterAddPage.do";
    var page_type = '${type}';
    if (page_type == "view" || page_type == "handle") {
        page_url = "storage/purchaseEnterViewPage.do?id=${id}";
    } else if (page_type == "edit") {
        var flag = '${flag}';
        if (flag == "true") {
            page_url = "storage/purchaseEnterEditPage.do?id=${id}";
        }
        else {
            $.messager.alert("提醒", "该订单已不存在！");
            page_url = null;
        }
    }
    $(function () {
        $("#storage-panel-purchaseEnterPage").panel({
            href: page_url,
            cache: false,
            maximized: true,
            border: false
        });
        //按钮
        $("#storage-buttons-purchaseEnterPage").buttonWidget({
            initButton: [
                {}
                <security:authorize url="/storage/purchaseEnterAddPage.do">
                , {
                    type: 'button-add',
                    handler: function () {
                        $("#storage-panel-purchaseEnterPage").panel({
                            href: 'storage/purchaseEnterAddPage.do',
                            title: '',
                            cache: false,
                            maximized: true,
                            border: false
                        });
                    },
                    url: '/storage/purchaseEnterAddPage.do'
                }
                </security:authorize>
                <security:authorize url="/storage/addpurchaseEnterHold.do">
                , {
                    type: 'button-hold',
                    handler: function () {
                        var type = $("#storage-buttons-purchaseEnterPage").buttonWidget("getOperType");
                        if (type == "add") {
                            //暂存
                            $("#storage-form-purchaseEnterAdd").attr("action", "storage/addPurchaseEnterHold.do");
                            $("#storage-form-purchaseEnterAdd").submit();
                        } else if (type == "edit") {
                            //暂存
                            $("#storage-form-purchaseEnterAdd").attr("action", "storage/editPurchaseEnterHold.do");
                            $("#storage-form-purchaseEnterAdd").submit();
                        }
                    },
                    url: '/storage/addpurchaseEnterHold.do'
                }
                </security:authorize>
                <security:authorize url="/storage/addpurchaseEnterSave.do">
                , {
                    type: 'button-save',
                    handler: function () {
                        var type = $("#storage-buttons-purchaseEnterPage").buttonWidget("getOperType");
                        if (type == "add") {
                            //暂存
                            $("#storage-form-purchaseEnterAdd").attr("action", "storage/addPurchaseEnterSave.do");
                            $("#storage-form-purchaseEnterAdd").submit();
                        } else if (type == "edit") {
                            $("#storage-form-purchaseEnterAdd").attr("action", "storage/editPurchaseEnterSave.do");
                            $("#storage-form-purchaseEnterAdd").submit();
                        }
                    },
                    url: '/storage/addpurchaseEnterSave.do'
                }
                </security:authorize>
                <security:authorize url="/storage/addpurchaseEnterSaveAudit.do">
                , {
                    type: 'button-saveaudit',
                    handler: function () {
                        $.messager.confirm("提醒", "确定保存并审核该采购入库单信息？", function (r) {
                            if (r) {
                                var type = $("#storage-buttons-purchaseEnterPage").buttonWidget("getOperType");
                                $("#storage-purchaseEnter-saveaudit").val("saveaudit");
                                if (type == "add") {
                                    //暂存
                                    $("#storage-form-purchaseEnterAdd").attr("action", "storage/addPurchaseEnterSave.do");
                                    $("#storage-form-purchaseEnterAdd").submit();
                                } else if (type == "edit") {
//					 					$("#storage-form-purchaseEnterAdd").attr("action", "storage/editPurchaseEnterSave.do");
//					 					$("#storage-form-purchaseEnterAdd").submit();
                                    $('#storage-form-purchaseEnterAdd').form('submit', {
                                        url: 'storage/editPurchaseEnterSave.do',
                                        onSubmit: function () {
                                            var json = $("#storage-datagrid-purchaseEnterAddPage").datagrid('getRows');
                                            $("#storage-purchaseEnter-purchaseEnterDetail").val(JSON.stringify(json));
                                            var flag = $(this).form('validate');
                                            if (flag == false) {
                                                return false;
                                            }
                                            loading("提交中..");
                                        },
                                        success: function (data) {
                                            //表单提交完成后 隐藏提交等待页面
                                            loaded();
                                            var json = $.parseJSON(data);
                                            if (json.flag) {
                                                if (json.auditflag) {
                                                    var msg = "";
                                                    if (json.purchaseEnterId != null) {
                                                        msg = "采购订单未完成，自动生成新的采购入库单：" + json.purchaseEnterId;
                                                        $.messager.alert("提醒", "审核成功,生成采购进货单:" + json.downid + "." + msg);
                                                    } else {
                                                        $.messager.alert("提醒", "审核成功,生成采购进货单:" + json.downid);
                                                    }
                                                    $("#storage-purchaseEnter-status").val("3");
                                                    $("#storage-buttons-purchaseEnterPage").buttonWidget("setDataID", {
                                                        id: '${purchaseEnter.id}',
                                                        state: '3',
                                                        type: 'view'
                                                    });
                                                    //刷新列表
                                                    tabsWindowURL("/storage/showPurchaseEnterListPage.do").$("#storage-datagrid-purchaseEnterPage").datagrid('reload');
                                                    //关闭当前标签页
                                                    top.closeNowTab();
                                                } else {
                                                    if (json.msg != null) {
                                                        $.messager.alert("提醒", "保存成功." + json.msg);
                                                    } else {
                                                        $.messager.alert("提醒", "保存成功.");
                                                    }
                                                }

                                            } else {
                                                $.messager.alert("提醒", "保存失败</br>" + json.msg);
                                            }
                                        }

                                    });
                                }
                            }
                        });
                    },
                    url: '/storage/addpurchaseEnterSave.do'
                }
                </security:authorize>
                <security:authorize url="/storage/purchaseEnterGiveUp.do">
                , {
                    type: 'button-giveup',
                    handler: function () {
                        var type = $("#storage-buttons-purchaseEnterPage").buttonWidget("getOperType");
                        if (type == "add") {
                            var currTitle = top.$('#tt').tabs('getSelected').panel('options').title;
                            top.closeTab(currTitle);
                        }
                        else if (type == "edit") {
                            var id = $("#storage-hidden-billid").val();
                            if (id == "") {
                                return false;
                            }
                            $("#storage-panel-purchaseEnterPage").panel({
                                href: 'storage/purchaseEnterViewPage.do?id=' + id,
                                title: '',
                                cache: false,
                                maximized: true,
                                border: false
                            });
                        }
                    },
                    url: '/storage/purchaseEnterGiveUp.do'
                }
                </security:authorize>
                <security:authorize url="/storage/deleteAdjustments.do">
                , {
                    type: 'button-delete',
                    handler: function () {
                        $.messager.confirm("提醒", "是否删除当前采购入库单？", function (r) {
                            if (r) {
                                var id = $("#storage-hidden-billid").val();
                                if (id != "") {
                                    loading("删除中..");
                                    $.ajax({
                                        url: 'storage/deletePurchaseEnter.do?id=' + id,
                                        type: 'post',
                                        dataType: 'json',
                                        success: function (json) {
                                            loaded();
                                            if (json.flag) {
                                                var object = $("#storage-buttons-purchaseEnterPage").buttonWidget("removeData", id);
                                                $.messager.alert("提醒", "删除成功");
                                                if (null != object) {
                                                    $("#storage-panel-purchaseEnterPage").panel({
                                                        href: 'storage/purchaseEnterEditPage.do?id=' + object.id,
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
                    },
                    url: '/storage/deleteAdjustments.do'
                }
                </security:authorize>
                <security:authorize url="/storage/auditpurchaseEnter.do">
                , {
                    type: 'button-audit',
                    handler: function () {
                        $.messager.confirm("提醒", "是否审核采购入库单？", function (r) {
                            if (r) {
                                var id = $("#storage-hidden-billid").val();
                                if (id != "") {
                                    loading("审核中..");
                                    $.ajax({
                                        url: 'storage/auditPurchaseEnter.do?id=' + id,
                                        type: 'post',
                                        dataType: 'json',
                                        success: function (json) {
                                            loaded();
                                            if (json.flag) {
                                                var msg = "";
                                                if (json.purchaseEnterId != null) {
                                                    msg = "审核成功，采购订单未完成，自动生成新的采购入库单：" + json.purchaseEnterId;
                                                    $.messager.alert("提醒", "审核成功,生成采购进货单:" + json.downid + "." + msg);
                                                } else {
                                                    $.messager.alert("提醒", "审核成功,生成采购进货单:" + json.downid);
                                                }
                                                $("#storage-purchaseEnter-status").val("3");
                                                $("#storage-buttons-purchaseEnterPage").buttonWidget("setDataID", {
                                                    id: id,
                                                    state: '3',
                                                    type: 'view'
                                                });
                                                //刷新列表
                                                tabsWindowURL("/storage/showPurchaseEnterListPage.do").$("#storage-datagrid-purchaseEnterPage").datagrid('reload');
                                                //关闭当前标签页
                                                top.closeNowTab();
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
                    },
                    url: '/storage/auditpurchaseEnter.do'
                }
                </security:authorize>
                <security:authorize url="/storage/oppauditpurchaseEnter.do">
                , {
                    type: 'button-oppaudit',
                    handler: function () {
                        var businessdate = $("#storage-purchaseEnter-businessdate").val();

//                             var sourcetype=$("#storage-purchaseEnter-sourcetype").val()
//                             //来源代配送不允许反审
//                             if(sourcetype&&sourcetype=="2"){
//                             	$.messager.alert("提醒","单据来源代配送,不允许反审!");
//                                 return false;
//                             }

                        var flag = isDoneOppauditBillCaseAccounting(businessdate);
                        if (!flag) {
                            $.messager.alert("提醒", "业务日期不在会计区间内或未设置会计区间,不允许反审!");
                            return false;
                        }
                        $.messager.confirm("提醒", "是否反审采购入库单？", function (r) {
                            if (r) {
                                var id = $("#storage-hidden-billid").val();
                                if (id != "") {
                                    loading("反审中..");
                                    $.ajax({
                                        url: 'storage/oppauditPurchaseEnter.do?id=' + id,
                                        type: 'post',
                                        dataType: 'json',
                                        success: function (json) {
                                            loaded();
                                            if (json.flag) {
                                                $.messager.alert("提醒", "反审成功");
                                                $("#storage-panel-purchaseEnterPage").panel({
                                                    href: 'storage/purchaseEnterEditPage.do?id=' + id,
                                                    title: '',
                                                    cache: false,
                                                    maximized: true,
                                                    border: false
                                                });
                                            } else {
                                                if (json.oppflag == false) {
                                                    $.messager.alert("提醒", "反审失败,该采购入库单的审核时间不是今天，不能反审。只有当天审核的单据才能反审");
                                                } else {
                                                    $.messager.alert("提醒", "反审失败");
                                                }
                                            }
                                        },
                                        error: function () {
                                            loaded();
                                            $.messager.alert("错误", "反审失败，原因：可能仓库中该商品已经使用过");
                                        }
                                    });
                                }
                            }
                        });
                    },
                    url: '/storage/oppauditpurchaseEnter.do'
                }
                </security:authorize>
                <security:authorize url="/storage/purchaseOrderRelationButton.do">
                , {
                    type: 'button-relation',
                    button: [
                        <security:authorize url="/storage/showPurchaseOrderRelationUpperPage.do">
                        {
                            type: 'relation-upper',
                            handler: function () {
                                $("#storage-panel-relation-upper").dialog({
                                    href: "storage/showPurchaseOrderRelationUpperPage.do",
                                    title: "采购订单查询",
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
                                            var type = $("#storage-buttons-purchaseEnterPage").buttonWidget("getOperType");
                                            $("#storage-panel-sourceQueryPage").dialog({
                                                title: '采购订单列表',
                                                fit: true,
                                                closed: false,
                                                modal: true,
                                                cache: false,
                                                maximizable: true,
                                                resizable: true,
                                                href: 'storage/showPurchaseEnterSourceListPage.do',
                                                buttons: [{
                                                    text: '确定',
                                                    handler: function () {
                                                        var dispatchbill = $("#storage-orderDatagrid-dispatchBillSourcePage").datagrid('getSelected');
                                                        if (dispatchbill == null) {
                                                            $.messager.alert("提醒", "请选择一条订单记录");
                                                            return false;
                                                        }
                                                        $("#storage-panel-sourceQueryPage").dialog('close', true);
                                                        //生成采购入库单
                                                        loading("生成中..");
                                                        $.ajax({
                                                            url: 'storage/addPurchaseEnterByRefer.do',
                                                            type: 'post',
                                                            dataType: 'json',
                                                            data: {dispatchbillid: dispatchbill.id},
                                                            success: function (json) {
                                                                loaded();
                                                                if (json.flag) {
                                                                    $.messager.alert("提醒", "生成成功");
                                                                    $("#storage-panel-purchaseEnterPage").panel({
                                                                        href: 'storage/purchaseEnterViewPage.do?id=' + json.id,
                                                                        title: '',
                                                                        cache: false,
                                                                        maximized: true,
                                                                        border: false
                                                                    });
                                                                } else {
                                                                    $.messager.alert("提醒", json.msg);
                                                                }
                                                            }
                                                        });
                                                    }
                                                }],
                                                onLoad: function () {
                                                    $("#storage-orderDatagrid-dispatchBillSourcePage").datagrid({
                                                        columns: [[
                                                            {field: 'id', title: '编号', width: 100},
                                                            {field: 'businessdate', title: '业务日期', width: 100},
                                                            {
                                                                field: 'suppliername',
                                                                title: '供应商',
                                                                width: 100,
                                                                align: 'left'
                                                            },
                                                            {
                                                                field: 'handlername',
                                                                title: '对方经手人',
                                                                width: 100,
                                                                align: 'left'
                                                            },
                                                            {
                                                                field: 'buydeptname',
                                                                title: '采购部门',
                                                                width: 120,
                                                                align: 'left'
                                                            },
                                                            {
                                                                field: 'buyusername',
                                                                title: '采购员',
                                                                width: 100,
                                                                align: 'left'
                                                            },
                                                            {
                                                                field: 'field01',
                                                                title: '含税金额',
                                                                width: 120,
                                                                align: 'right',
                                                                formatter: function (value, row, index) {
                                                                    return formatterMoney(value);
                                                                }
                                                            },
                                                            {
                                                                field: 'field02',
                                                                title: '未税金额',
                                                                width: 120,
                                                                align: 'right',
                                                                formatter: function (value, row, index) {
                                                                    return formatterMoney(value);
                                                                }
                                                            },
                                                            {
                                                                field: 'field03',
                                                                title: '税额',
                                                                width: 120,
                                                                align: 'right',
                                                                formatter: function (value, row, index) {
                                                                    return formatterMoney(value);
                                                                }
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
                                                        url: 'purchase/buyorder/showBuyOrderForReferPageList.do',
                                                        queryParams: queryJSON,
                                                        onClickRow: function (index, data) {
                                                            $("#storage-detailDatagrid-dispatchBillSourcePage").datagrid({
                                                                url: 'purchase/buyorder/showBuyOrderDetailReferList.do',
                                                                queryParams: {
                                                                    orderid: data.id
                                                                }
                                                            });
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    }]
                                });
                            },
                            url: '/storage/showPurchaseOrderRelationUpperPage.do'
                        },
                        </security:authorize>
                        <security:authorize url="/storage/showPurchaseEnterSourcePage.do">
                        {
                            type: 'relation-upper-view',
                            handler: function () {
                                var sourceid = $("#storage-purchaseEnter-sourceid").val();
                                if (null != sourceid && sourceid != "") {
                                    var basePath = $("#basePath").attr("href");
                                    top.addOrUpdateTab(basePath + 'purchase/buyorder/buyOrderPage.do?type=view&id=' + sourceid, "采购订单");
                                }
                            },
                            url: '/storage/showPurchaseEnterSourcePage.do'
                        },
                        </security:authorize>
                        {}
                    ]
                }
                </security:authorize>
                <security:authorize url="/storage/purchaseEnterWorkflow.do">
                , {
                    type: 'button-workflow',
                    button: [
                        {
                            type: 'workflow-submit',
                            handler: function () {
                                $.messager.confirm("提醒", "是否提交该采购入库单信息到工作流?", function (r) {
                                    if (r) {
                                        var id = $("#storage-hidden-billid").val();
                                        if (id == "") {
                                            $.messager.alert("警告", "没有需要提交工作流的信息!");
                                            return false;
                                        }
                                        loading("提交中..");
                                        $.ajax({
                                            url: 'storage/submitPurchaseEnterProcess.do',
                                            dataType: 'json',
                                            type: 'post',
                                            data: 'id=' + id,
                                            success: function (json) {
                                                loaded();
                                                if (json.flag == true) {
                                                    $.messager.alert("提醒", "提交成功!");
                                                    $("#storage-panel-purchaseEnterPage").panel("refresh");
                                                }
                                                else {
                                                    $.messager.alert("提醒", "提交失败!" + json.msg);
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
                    ],
                    url: '/storage/purchaseEnterWorkflow.do'
                }
                </security:authorize>
                <security:authorize url="/storage/purchaseEnterViewPage.do">
                , {
                    type: 'button-back',
                    handler: function (data) {
                        $("#storage-panel-purchaseEnterPage").panel({
                            href: 'storage/purchaseEnterEditPage.do?id=' + data.id,
                            title: '',
                            cache: false,
                            maximized: true,
                            border: false
                        });
                    },
                    url: '/storage/purchaseEnterViewPage.do'
                }
                </security:authorize>
                <security:authorize url="/storage/purchaseEnterViewPage.do">
                , {
                    type: 'button-next',
                    handler: function (data) {
                        $("#storage-panel-purchaseEnterPage").panel({
                            href: 'storage/purchaseEnterEditPage.do?id=' + data.id,
                            title: '',
                            cache: false,
                            maximized: true,
                            border: false
                        });
                    },
                    url: '/storage/purchaseEnterViewPage.do'
                }
                </security:authorize>
                <security:authorize url="/storage/purchaseEnterPrintView.do">
                , {
                    type: 'button-preview',
                    handler: function () {
                    }
                }
                </security:authorize>
                <security:authorize url="/storage/purchaseEnterPrint.do">
                , {
                    type: 'button-print',
                    handler: function () {
                    }
                }
                </security:authorize>
            ],
            buttons: [
                <security:authorize url="/storage/exportPurchaseEnterDetailBtn.do">
                {
                    id: 'button-export-detail',
                    name: '全局明细导出',
                    iconCls: 'button-export',
                    handler: function () {
                        var id = $("#storage-hidden-billid").val();
                        var url = "storage/exportPurchaseEnterDetail.do";
                        exportByAnalyse(id, "采购入库单：" + id + "商品明细", "storage-datagrid-purchaseEnterAddPage", url);
                    }
                },
                </security:authorize>
            ],
            layoutid: 'storage-layout-purchaseEnterPage',
            model: 'bill',
            type: 'view',
            tab: '采购入库单列表',
            taburl: '/storage/showPurchaseEnterListPage.do',
            id: '${id}',
            datagrid: 'storage-datagrid-purchaseEnterPage'
        });
    });
    //显示采购入库单明细添加页面
    function beginAddDetail(type) {
        //验证表单
        var flag = $("#storage-form-purchaseEnterAdd").form('validate');
        if (flag == false) {
            $.messager.alert("提醒", '请先完善采购入库单基本信息');
            return false;
        }
        var supplierid = $("#storage-purchaseEnter-supplierid").supplierWidget("getValue");
        $('<div id="storage-dialog-purchaseEnterAddPage-content"></div>').appendTo('#storage-dialog-purchaseEnterAddPage');
        $('#storage-dialog-purchaseEnterAddPage-content').dialog({
            title: '采购入库单明细添加',
            width: 680,
            height: 450,
            collapsible: false,
            minimizable: false,
            maximizable: true,
            resizable: true,
            closed: true,
            cache: false,
            modal: true,
            href: 'storage/showPurchaseEnterDetailAddPage.do?supplierid=' + supplierid+"&type="+type,
            onClose: function () {
                $('#storage-dialog-purchaseEnterAddPage-content').dialog("destroy");
            },
            onLoad: function () {
                $("#storage-purchaseEnter-goodsid").focus();
            }
        });
        $('#storage-dialog-purchaseEnterAddPage-content').dialog("open");
    }
    //显示盘点单明细修改页面
    function beginEditDetail(row) {
        //验证表单
        var flag = $("#storage-form-purchaseEnterAdd").form('validate');
        if (flag == false) {
            $.messager.alert("提醒", '请先选择出库仓库');
            return false;
        }
        if (row.goodsid == undefined) {
            beginAddDetail();
        } else {
            var initnum = row.initnum == null ? 0 : row.initnum;
            var url = 'storage/showPurchaseEnterDetailEditPage.do?goodsid=' + row.goodsid + "&initnum=" + initnum+"&type="+row.goodstype;
            $('<div id="storage-dialog-purchaseEnterAddPage-content"></div>').appendTo('#storage-dialog-purchaseEnterAddPage');
            $('#storage-dialog-purchaseEnterAddPage-content').dialog({
                title: '采购入库单明细修改',
                width: 680,
                height: 450,
                collapsible: false,
                minimizable: false,
                maximizable: true,
                resizable: true,
                closed: true,
                cache: false,
                href: url,
                modal: true,
                onClose: function () {
                    $('#storage-dialog-purchaseEnterAddPage-content').dialog("destroy");
                },
                onLoad: function () {
                    //加载数据
                    var object = row;
                    if (object.arrivedate == null || object.arrivedate == "") {
                        object.arrivedate = "${today}";
                    }
                    if (object.field01 != null && object.field01 != "") {
                        $("#storage-purchaseEnter-loading").html("订单未入库数量:" + object.field02);
                    }
                    $("#storage-form-purchaseEnterDetailEditPage").form("load", object);
                    $("#storage-purchaseEnter-goodsname").val(object.goodsInfo.name);
                    $("#storage-purchaseEnter-goodsbrandName").val(object.goodsInfo.brandName);
                    $("#storage-purchaseEnter-boxnum").val(formatterBigNumNoLen(object.goodsInfo.boxnum));
                    $("#storage-purchaseEnter-goodsunitname1").html(object.unitname);
                    $("#storage-purchaseEnter-auxunitname1").html(object.auxunitname);
//                    $("#storage-purchaseEnter-goodstype").val(goodstype);
                    $("#storage-purchaseEnter-unitnum-aux").focus();
                    $("#storage-purchaseEnter-unitnum-aux").select();

                    formaterNumSubZeroAndDot();

                    $("#storage-form-purchaseEnterDetailEditPage").form('validate');
                }
            });
            $('#storage-dialog-purchaseEnterAddPage-content').dialog("open");
        }
    }
    //保存采购入库单明细
    function addSaveDetail(goFlag,type) { //添加新数据确定后操作，
        var flag = $("#storage-form-purchaseEnterDetailAddPage").form('validate');
        if (flag == false) {
            return false;
        }
        var form = $("#storage-form-purchaseEnterDetailAddPage").serializeJSON();
        var widgetJson = $("#storage-purchaseEnter-goodsid").goodsWidget('getObject');
        var storagelocationname = $("#storage-purchaseEnter-storagelocationid").widget("getText");
        form.goodsInfo = widgetJson;
        form.storagelocationname = storagelocationname;
        console.log(form)
        $.ajax({
            url: 'basefiles/getItemByGoodsAndStorage.do',
            data:{
                goodsid:widgetJson.id,
                storageid:$("#storage-purchaseEnter-storageid").widget('getValue')
            },
            async: false,
            type: 'post',
            dataType: 'json',
            success: function (json) {
                form.goodsInfo.itemno=json.itemno;
            },
        });
        var rowIndex = 0;
        var rows = $("#storage-datagrid-purchaseEnterAddPage").datagrid('getRows');
        for (var i = 0; i < rows.length; i++) {
            var rowJson = rows[i];
            if (rowJson.goodsid == undefined) {
                rowIndex = i;
                break;
            }
        }
        if (rowIndex == rows.length - 1) {
            $("#storage-datagrid-purchaseEnterAddPage").datagrid('appendRow', {}); //如果是最后一行则添加一新行
        }
        $("#storage-datagrid-purchaseEnterAddPage").datagrid('updateRow', {index: rowIndex, row: form}); //将数据更新到列表中
        if (goFlag) { //go为true确定并继续添加一条
            var supplierid = $("#storage-purchaseEnter-supplierid").supplierWidget("getValue");
            var url = 'storage/showPurchaseEnterDetailAddPage.do?supplierid=' + supplierid+'&type='+type;
            $("#storage-dialog-purchaseEnterAddPage-content").dialog('refresh', url);
        }
        else { //否则直接关闭
            $("#storage-dialog-purchaseEnterAddPage-content").dialog('destroy');
        }
        $("#storage-purchaseEnter-supplierid").supplierWidget('readonly', true);
        countTotal();

    }
    //修改保存
    function editSaveDetail(goFlag) {
        var flag = $("#storage-form-purchaseEnterDetailEditPage").form('validate');
        if (flag == false) {
            return false;
        }
        var form = $("#storage-form-purchaseEnterDetailEditPage").serializeJSON();
        var row = $("#storage-datagrid-purchaseEnterAddPage").datagrid('getSelected');
        var rowIndex = $("#storage-datagrid-purchaseEnterAddPage").datagrid('getRowIndex', row);
        form.goodsInfo = row.goodsInfo;
        var storagelocationname = $("#storage-purchaseEnter-storagelocationid").widget("getText");
        form.storagelocationname = storagelocationname;
        $("#storage-datagrid-purchaseEnterAddPage").datagrid('updateRow', {index: rowIndex, row: form}); //将数据更新到列表中
        $("#storage-dialog-purchaseEnterAddPage-content").dialog('destroy');
        countTotal();
    }
    //删除明细
    function removeDetail() {
        var row = $("#storage-datagrid-purchaseEnterAddPage").datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        $.messager.confirm("提醒", "确定删除该商品明细?", function (r) {
            if (r) {
                var rowIndex = $("#storage-datagrid-purchaseEnterAddPage").datagrid('getRowIndex', row);
                $("#storage-datagrid-purchaseEnterAddPage").datagrid('deleteRow', rowIndex);
                countTotal();
                var rows = $("#storage-datagrid-purchaseEnterAddPage").datagrid('getRows');
                var index = -1;
                for (var i = 0; i < rows.length; i++) {
                    if (rows[i].goodsid != undefined) {
                        index = i;
                        break;
                    }
                }
                if (index == -1) {
                    $("#storage-purchaseEnter-supplierid").supplierWidget('readonly', false);
                }
            }
        });
    }
    //计算合计
    function countTotal() {
        var rows = $("#storage-datagrid-purchaseEnterAddPage").datagrid('getRows');
        var countNum = 0;
        var taxamount = 0;
        var notaxamount = 0;
        var tax = 0;
        var auxnum = 0;
        var auxremainder = 0;
        for (var i = 0; i < rows.length; i++) {
            countNum = Number(countNum) + Number(rows[i].unitnum == undefined ? 0 : rows[i].unitnum);
            taxamount = Number(taxamount) + Number(rows[i].taxamount == undefined ? 0 : rows[i].taxamount);
            notaxamount = Number(notaxamount) + Number(rows[i].notaxamount == undefined ? 0 : rows[i].notaxamount);
            tax = Number(tax) + Number(rows[i].tax == undefined ? 0 : rows[i].tax);
            auxnum = Number(auxnum) + Number(rows[i].auxnum == undefined ? 0 : rows[i].auxnum);
            auxremainder = Number(auxremainder) + Number(rows[i].auxremainder == undefined ? 0 : rows[i].auxremainder);
        }
        var auxnumstr = "";
        if (auxnum > 0) {
            auxnumstr = formatterBigNum(auxnum) + "箱";
        }
        if (auxremainder > 0) {
            auxnumstr += formatterBigNum(auxremainder);
        }
        $("#storage-datagrid-purchaseEnterAddPage").datagrid("reloadFooter", [{
            goodsid: '合计',
            unitnum: countNum,
            taxamount: taxamount,
            notaxamount: notaxamount,
            tax: tax,
            auxnumdetail: auxnumstr
        }]);
    }
    //单条商品指定多个库位
    function editGoodsLocation() {
        var row = $("#storage-datagrid-purchaseEnterAddPage").datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        $('<div id="storage-editlocation-dialog-purchaseEnterAddPage-content"></div>').appendTo('#storage-editlocation-dialog-purchaseEnterAddPage');
        $('#storage-editlocation-dialog-purchaseEnterAddPage-content').dialog({
            title: '商品指定库位',
            width: 680,
            height: 400,
            collapsible: false,
            minimizable: false,
            maximizable: true,
            resizable: true,
            closed: true,
            cache: false,
            href: 'storage/showPurchaseEnterDetailEditLocationPage.do',
            modal: true,
            onClose: function () {
                $('#storage-editlocation-dialog-purchaseEnterAddPage-content').dialog("destroy");
            },
            onLoad: function () {
            }
        });
        $('#storage-editlocation-dialog-purchaseEnterAddPage-content').dialog("open");
    }
    $(function () {
        //关闭明细添加页面
        $(document).bind('keydown', 'esc', function () {
            $("#storage-purchaseEnter-remark").focus();
            $("#storage-dialog-purchaseEnterAddPage-content").dialog("close");
        });
        $(document).bind('keydown', 'ctrl+enter', function () {
            $("#storage-purchaseEnter-remark").focus();
            $("#storage-savegoon-purchaseEnterDetailAddPage").trigger("click");
            $("#storage-savegoon-purchaseEnterDetailEditPage").trigger("click");
            return false;
        });
        $(document).bind('keydown', '+', function () {
            $("#storage-purchaseEnter-remark").focus();
            setTimeout(function () {
                $("#storage-savegoon-purchaseEnterDetailAddPage").trigger("click");
                $("#storage-savegoon-purchaseEnterDetailEditPage").trigger("click");
            }, 100);
            return false;
        });
    });
    function updateDataGridPrintimes(id) {
        var thepage = tabsWindowURL(pageListUrl);
        if (thepage == null) {
            return false;
        }
        var thegrid = thepage.$("#storage-datagrid-purchaseEnterAddPage");
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
            id: "purchaseenter-dialog-print",
            code: "storage_purchaseenter",
            url_preview: "print/storage/purchaseEnterPrintView.do",
            url_print: "print/storage/purchaseEnterPrint.do",
            btnPreview: "button-preview",
            btnPrint: "button-print",
            printlimit: "${printlimit}",
            getData: function (tableId, printParam) {
                var id = $("#storage-hidden-billid").val();
                if (id == "") {
                    $.messager.alert("警告", "找不到要打印的信息!");
                    return false;
                }

                printParam.idarrs = id;
                var printtimes = $("#purchase-purchaseEnterAddPage-printtimes").val() || 0;
                if (printtimes > 0)
                    printParam.printIds = [id];
                return true;
            },
            onPrintSuccess: function (option) {
                updateDataGridPrintimes($("#storage-hidden-billid").val());
            },
            printAfterHandler: function (option, printParam) {
                var printtimes = $("#purchase-purchaseEnterAddPage-printtimes").val() || 0;
                $("#purchase-purchaseEnterAddPage-printtimes").val(printtimes + 1);
                var printlimit = $("#purchase-purchaseEnterAddPage-printlimit").val() || "1";
                if (0 != printlimit) {
                    $("#purchase-buttons-purchaseEnterPage").buttonWidget("disableMenuItem", "button-print");
                }
            }
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
