<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>销售退货入库单操作页面</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>
<body>
<div id="storage-layoutid-saleRejectEnterPage" class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="storage-buttons-saleRejectEnterPage" style="height:26px;"></div>
    </div>
    <div data-options="region:'center',border:false">
        <div id="storage-panel-saleRejectEnterPage"></div>
    </div>
</div>
<div id="storage-panel-relation-upper"></div>
<div id="storage-panel-sourceQueryPage"></div>
<div id="workflow-addidea-dialog-page"></div>
<input type="hidden" id="storage-hidden-billid"/>
<div style="display:none">
    <%--通用 --%>
    <div id="listPage-Order-dialog-print">
        <form action="" id="listPage-Order-dialog-print-form" method="post">
            <table>
                <tr>
                    <td>打印模板：</td>
                    <td>
                        <select id="listPage-Order-dialog-print-templet" name="templetid">
                        </select>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<script type="text/javascript">
    //根据初始的列与用户保存的列生成以及字段权限生成新的列
    var tableColJson = $("#storage-datagrid-saleRejectEnterAddPage").createGridColumnLoad({
        name: 'storage_saleRejectEnter_detail',
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
            {
                field: 'taxprice', title: '单价', width: 60, align: 'right',
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
            {field: 'taxtypename', title: '税种', width: 60, align: 'right', hidden: true},
            {
                field: 'tax', title: '税额', width: 60, align: 'right', hidden: true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
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
            {
                field: 'rejectcategory', title: '退货属性', width: 80, align: 'left', hidden: true,
                formatter: function (value, row, index) {
                    return getSysCodeName('rejectCategory', value);
                }
            },
            {field: 'remark', title: '备注', width: 100}
        ]]
    });
    var page_url = "storage/saleRejectEnterAddPage.do";
    var page_type = '${type}';
    if (page_type == "view" || page_type == "handle") {
        page_url = "storage/saleRejectEnterViewPage.do?id=${id}";
    } else if (page_type == "edit") {
        var flag = '${flag}';
        if (flag == "true") {
            page_url = "storage/saleRejectEnterEditPage.do?id=${id}";
        } else {
            $.messager.alert("提醒", "该订单已不存在！");
            page_url = null;
        }
    }
    $(function () {
        $("#storage-panel-saleRejectEnterPage").panel({
            href: page_url,
            cache: false,
            maximized: true,
            border: false,
            onLoad: function () {
                if (page_type != "view") {
                    $("#storage-saleRejectEnter-customerid").focus();
                }
            }
        });
        //按钮
        $("#storage-buttons-saleRejectEnterPage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/storage/addsaleRejectEnterHold.do">
                {
                    type: 'button-hold',
                    handler: function () {
                        $.messager.confirm("提醒", "确定暂存该销售退货入库单信息？", function (r) {
                            if (r) {
                                var type = $("#storage-buttons-saleRejectEnterPage").buttonWidget("getOperType");
                                if (type == "add") {
                                    //暂存
                                    $("#storage-form-saleRejectEnterAdd").attr("action", "storage/addSaleRejectEnterHold.do");
                                    $("#storage-form-saleRejectEnterAdd").submit();
                                } else if (type == "edit") {
                                    //暂存
                                    $("#storage-form-saleRejectEnterAdd").attr("action", "storage/editSaleRejectEnterHold.do");
                                    $("#storage-form-saleRejectEnterAdd").submit();
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/addsaleRejectEnterSave.do">
                {
                    type: 'button-save',
                    handler: function () {
                        $.messager.confirm("提醒", "确定保存该销售退货入库单信息？", function (r) {
                            if (r) {
                                var type = $("#storage-buttons-saleRejectEnterPage").buttonWidget("getOperType");
                                if (type == "add") {
                                    //暂存
                                    $("#storage-form-saleRejectEnterAdd").attr("action", "storage/addSaleRejectEnterSave.do");
                                    $("#storage-form-saleRejectEnterAdd").submit();
                                } else if (type == "edit") {
                                    $("#storage-form-saleRejectEnterAdd").attr("action", "storage/editSaleRejectEnterSave.do");
                                    $("#storage-form-saleRejectEnterAdd").submit();
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/addsaleRejectEnterSaveAudit.do">
                {
                    type: 'button-saveaudit',
                    handler: function () {
                        $.messager.confirm("提醒", "确定保存并审核该销售退货入库单信息？", function (r) {
                            if (r) {
                                $("#storage-saveaudit-saleRejectEnterDetail").val("saveaudit");
                                $("#storage-form-saleRejectEnterAdd").attr("action", "storage/editSaleRejectEnterSave.do");
                                $("#storage-form-saleRejectEnterAdd").submit();
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/saleRejectEnterGiveUp.do">
                {
                    type: 'button-giveup',
                    handler: function () {
                        var type = $("#storage-buttons-saleRejectEnterPage").buttonWidget("getOperType");
                        if (type == "add") {
                            var currTitle = top.$('#tt').tabs('getSelected').panel('options').title;
                            top.closeTab(currTitle);
                        }
                        else if (type == "edit") {
                            var id = $("#storage-hidden-billid").val();
                            if (id == "") {
                                return false;
                            }
                            $("#storage-panel-saleRejectEnterPage").panel({
                                href: 'storage/saleRejectEnterViewPage.do?id=' + id,
                                title: '',
                                cache: false,
                                maximized: true,
                                border: false
                            });
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/deleteSaleRejectEnter.do">
                {
                    type: 'button-delete',
                    handler: function () {
                        $.messager.confirm("提醒", "是否删除当前销售退货入库单？", function (r) {
                            if (r) {
                                var id = $("#storage-hidden-billid").val();
                                if (id != "") {
                                    loading("删除中..");
                                    $.ajax({
                                        url: 'storage/deleteSaleRejectEnter.do?id=' + id,
                                        type: 'post',
                                        dataType: 'json',
                                        success: function (json) {
                                            loaded();
                                            if (json.flag) {
                                                var object = $("#storage-buttons-saleRejectEnterPage").buttonWidget("removeData", id);
                                                $.messager.alert("提醒", "删除成功");
                                                if (null != object) {
                                                    $("#storage-panel-saleRejectEnterPage").panel({
                                                        href: 'storage/saleRejectEnterEditPage.do?id=' + object.id,
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
                <security:authorize url="/storage/auditSaleRejectEnter.do">
                {
                    type: 'button-audit',
                    handler: function () {
                        $.messager.confirm("提醒", "是否审核销售退货入库单？", function (r) {
                            if (r) {
                                var id = $("#storage-hidden-billid").val();
                                if (id != "") {
                                    loading("审核中..");
                                    $.ajax({
                                        url: 'storage/auditSaleRejectEnter.do?id=' + id,
                                        type: 'post',
                                        dataType: 'json',
                                        success: function (json) {
                                            loaded();
                                            if (json.flag) {
                                                $.messager.alert("提醒", "审核成功");
                                                //刷新列表
                                                tabsWindowURL("/storage/showSaleRejectEnterListPage.do").$("#storage-datagrid-saleRejectEnterPage").datagrid('reload');
                                                //关闭当前标签页
                                                top.closeNowTab();
                                                <!--$("#storage-panel-saleRejectEnterPage").panel({-->
                                                <!--	href:'storage/saleRejectEnterEditPage.do?id='+id,-->
                                                <!--	title:'',-->
                                                <!--    cache:false,-->
                                                <!--    maximized:true,-->
                                                <!--    border:false-->
                                                <!--});-->
                                                //$("#storage-saleRejectEnter-status").val(4);
                                                //$("#storage-buttons-saleRejectEnterPage").buttonWidget("setDataID",{id:id,state:'4',type:'view'});
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
                <security:authorize url="/storage/oppauditSaleRejectEnter.do">
                {
                    type: 'button-oppaudit',
                    handler: function () {
                        var businessdate = $("#storage-saleRejectEnter-businessdate").val();
                        var flag = isDoneOppauditBillCaseAccounting(businessdate);
                        if (!flag) {
                            $.messager.alert("提醒", "业务日期不在会计区间内或未设置会计区间,不允许反审!");
                            return false;
                        }
                        $.messager.confirm("提醒", "是否反审销售退货入库单？", function (r) {
                            if (r) {
                                var id = $("#storage-hidden-billid").val();
                                var auditflag = true;
                                <security:authorize url="/sales/oppauditSaleRejectEnterSupper.do">
                                auditflag = false;
                                </security:authorize>
                                if (auditflag) {
                                    var businessdate = $("#storage-saleRejectEnter-businessdate").val();
                                    if (businessdate != '${today}') {
                                        $.messager.alert("提醒", "退货入库单不能反审，业务日期不是今天。需要有权限的人才能反审！");
                                        return false;
                                    }
                                }
                                if (id != "") {
                                    loading("反审中..");
                                    $.ajax({
                                        url: 'storage/oppauditSaleRejectEnter.do?id=' + id,
                                        type: 'post',
                                        dataType: 'json',
                                        success: function (json) {
                                            loaded();
                                            if (json.flag) {
                                                $.messager.alert("提醒", "反审成功");
                                                $("#storage-panel-saleRejectEnterPage").panel({
                                                    href: 'storage/saleRejectEnterEditPage.do?id=' + id,
                                                    title: '',
                                                    cache: false,
                                                    maximized: true,
                                                    border: false
                                                });
                                                //$("#storage-saleRejectEnter-status").val(4);
                                                //$("#storage-buttons-saleRejectEnterPage").buttonWidget("setDataID",{id:id,state:'4',type:'view'});
                                            } else {
                                                $.messager.alert("提醒", "反审失败</br>" + json.msg);
                                            }
                                        },
                                        error: function () {
                                            loaded();
                                            $.messager.alert("错误", "反审出错");
                                        }
                                    });
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/showSaleRejectRelationPage.do">
                {
                    type: 'button-relation',
                    button: [
                        {},
                        <security:authorize url="/storage/showSaleRejectOrderRelationUpperPage.do">
                        {
                            type: 'relation-upper',
                            handler: function () {
                                $("#storage-panel-relation-upper").dialog({
                                    href: "storage/showSaleRejectOrderRelationUpperPage.do",
                                    title: "上游单据查询",
                                    closed: false,
                                    modal: true,
                                    cache: false,
                                    width: 500,
                                    height: 350,
                                    buttons: [{
                                        text: '查询',
                                        handler: function () {
                                            var sourcetype = $("#storage-dispatchBillid-sourcetype").val();
                                            var queryJSON = $("#storage-form-query-dispatchBill").serializeJSON();
                                            $("#storage-panel-relation-upper").dialog('close', true);
                                            var type = $("#storage-buttons-saleRejectEnterPage").buttonWidget("getOperType");
                                            var title = "";
                                            if (sourcetype == "1") {
                                                title = "销售退货通知单";
                                            } else if (sourcetype == "2") {
                                                title = "销售发货回单";
                                            }
                                            $("#storage-panel-sourceQueryPage").dialog({
                                                title: title,
                                                fit: true,
                                                closed: false,
                                                modal: true,
                                                cache: false,
                                                maximizable: true,
                                                resizable: true,
                                                href: 'storage/showSaleRejectEnterSourceListPage.do?sourcetype=' + sourcetype,
                                                buttons: [{
                                                    text: '确定',
                                                    handler: function () {
                                                        var bill = $("#storage-orderDatagrid-dispatchBillSourcePage").datagrid('getSelected');
                                                        if (bill == null) {
                                                            $.messager.alert("提醒", "请选择一条订单记录");
                                                            return false;
                                                        }
                                                        $("#storage-panel-sourceQueryPage").dialog('close', true);
                                                        //生成销售退货入库单
                                                        loading("生成中..");
                                                        $.ajax({
                                                            url: 'storage/addSaleRejectEnterByRefer.do',
                                                            type: 'post',
                                                            dataType: 'json',
                                                            data: {id: bill.id, sourcetype: sourcetype},
                                                            success: function (json) {
                                                                loaded();
                                                                if (json.flag) {
                                                                    $.messager.alert("提醒", "生成成功");
                                                                    $("#storage-panel-saleRejectEnterPage").panel({
                                                                        href: 'storage/saleRejectEnterViewPage.do?id=' + json.id,
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
                                                    var url = "";
                                                    var detailUrl = "";
                                                    //来源类型销售退货通知单
                                                    if (sourcetype == '1') {
                                                        url = 'sales/getRejectBillList.do';
                                                        detailUrl = "storage/showRejectBillDetailList.do";
                                                    } else if (sourcetype == '2') {		//来源类型销售发货回单
                                                        url = 'sales/getReceiptList.do';
                                                        detailUrl = "storage/showReceiptDetailList.do";
                                                    }
                                                    $("#storage-orderDatagrid-dispatchBillSourcePage").datagrid({
                                                        columns: [[
                                                            {field: 'id', title: '编号', width: 100},
                                                            {field: 'businessdate', title: '业务日期', width: 100},
                                                            {
                                                                field: 'customerid',
                                                                title: '客户编号',
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
                                                                width: 80,
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
                                                        url: url,
                                                        queryParams: queryJSON,
                                                        onClickRow: function (index, data) {
                                                            $("#storage-detailDatagrid-dispatchBillSourcePage").datagrid({
                                                                url: detailUrl,
                                                                queryParams: {
                                                                    id: data.id
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
                        <security:authorize url="/storage/showSaleRejectEnterSourcePage.do">
                        {
                            type: 'relation-upper-view',
                            handler: function () {
                                var sourceid = $("#storage-saleRejectEnter-sourceid").val();
                                var sourcetype = $("#storage-saleRejectEnter-sourcetype").val();
                                if (null != sourcetype && sourcetype != "") {
                                    var basePath = $("#basePath").attr("href");
                                    //if(sourcetype=='1'){
                                    top.addOrUpdateTab(basePath + 'sales/rejectBill.do?type=view&id=' + sourceid, "销售退货通知单");
                                    //}else if(sourcetype=='2'){
                                    //	top.addOrUpdateTab(basePath+'sales/receiptPage.do?type=view&id='+sourceid, "销售发货回单");
                                    //}
                                }
                            }
                        },
                        </security:authorize>
                        {}
                    ]
                },
                </security:authorize>
                <security:authorize url="/storage/saleRejectWorkflow.do">
                {
                    type: 'button-workflow',
                    button: [
                        {
                            type: 'workflow-submit',
                            handler: function () {
                                $.messager.confirm("提醒", "是否提交该销售退货入库单信息到工作流?", function (r) {
                                    if (r) {
                                        var id = $("#storage-hidden-billid").val();
                                        if (id == "") {
                                            $.messager.alert("警告", "没有需要提交工作流的信息!");
                                            return false;
                                        }
                                        loading("提交中..");
                                        $.ajax({
                                            url: 'storage/submitSaleRejectEnterProcess.do',
                                            dataType: 'json',
                                            type: 'post',
                                            data: 'id=' + id,
                                            success: function (json) {
                                                loaded();
                                                if (json.flag == true) {
                                                    $.messager.alert("提醒", "提交成功!");
                                                    $("#storage-panel-saleRejectEnterPage").panel("refresh");
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
                    ]
                },
                </security:authorize>
                <security:authorize url="/storage/saleRejectEnterBack.do">
                {
                    type: 'button-back',
                    handler: function (data) {
                        $("#storage-panel-saleRejectEnterPage").panel({
                            href: 'storage/saleRejectEnterEditPage.do?id=' + data.id,
                            title: '',
                            cache: false,
                            maximized: true,
                            border: false
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/saleRejectEnterNext.do">
                {
                    type: 'button-next',
                    handler: function (data) {
                        $("#storage-panel-saleRejectEnterPage").panel({
                            href: 'storage/saleRejectEnterEditPage.do?id=' + data.id,
                            title: '',
                            cache: false,
                            maximized: true,
                            border: false
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/saleRejectEnterPrintView.do">
                {
                    type: 'button-preview',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/saleRejectEnterPrint.do">
                {
                    type: 'button-print',
                    handler: function () {
                    }
                },
                </security:authorize>
                {}
            ],
            buttons: [
                {},
                <security:authorize url="/storage/saleRejectEnterSaveCheck.do">
                {
                    id: 'button-check',
                    name: '保存并验收',
                    iconCls: 'icon-remove',
                    handler: function () {
                        var sourcetype = $("#storage-saleRejectEnter-sourcetype").val();
                        if (sourcetype != '1') {
                            $.messager.alert("提醒", "此单不是售后退货单。不需要验收。");
                            return false;
                        }
                        $.messager.confirm("提醒", "是否验收?", function (r) {
                            if (r) {
                                $("#storage-saveaudit-saleRejectEnterDetail").val("savecheck");
                                $("#storage-form-saleRejectEnterAdd").attr("action", "storage/saleRejectEnterSaveCheck.do");
                                $("#storage-form-saleRejectEnterAdd").submit();
                            }
                        });
                    }
                },
                </security:authorize>
                {}
            ],
            layoutid: 'storage-layoutid-saleRejectEnterPage',
            model: 'bill',
            type: 'view',
            tab: '销售退货入库单列表',
            taburl: '/storage/showSaleRejectEnterListPage.do',
            id: '${id}',
            datagrid: 'storage-datagrid-saleRejectEnterPage'
        });
    });
    //显示销售退货入库单明细添加页面
    function beginAddDetail() {
        //验证表单
        var flag = $("#storage-form-saleRejectEnterAdd").form('validate');
        if (flag == false) {
            return false;
        }
        var storageid = $("#storage-saleRejectEnter-storageid").widget("getValue");
        var customerid = $("#storage-saleRejectEnter-customerid").widget("getValue");
        $('<div id="storage-dialog-saleRejectEnterAddPage-content"></div>').appendTo('#storage-dialog-saleRejectEnterAddPage');
        $('#storage-dialog-saleRejectEnterAddPage-content').dialog({
            title: '销售退货入库单明细添加',
            width: 680,
            height: 400,
            collapsible: false,
            minimizable: false,
            maximizable: true,
            resizable: true,
            closed: true,
            cache: false,
            modal: true,
            href: 'storage/showSaleRejectEnterDetailAddPage.do?storageid=' + storageid + "&customerid=" + customerid,
            onClose: function () {
                $('#storage-dialog-saleRejectEnterAddPage-content').dialog("destroy");
            },
            onLoad: function () {
                $("#storage-saleRejectEnter-goodsid").focus();
            }
        });
        $('#storage-dialog-saleRejectEnterAddPage-content').dialog("open");
    }
    //显示销售退货入库单明细修改页面
    function beginEditDetail() {
        //验证表单
        var flag = $("#storage-form-saleRejectEnterAdd").form('validate');
        var row = $("#storage-datagrid-saleRejectEnterAddPage").datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        if (row.goodsid == undefined) {
            //beginAddDetail();
        } else {
            $('<div id="storage-dialog-saleRejectEnterAddPage-content"></div>').appendTo('#storage-dialog-saleRejectEnterAddPage');
            $('#storage-dialog-saleRejectEnterAddPage-content').dialog({
                title: '销售退货入库单明细修改',
                width: 680,
                height: 400,
                collapsible: false,
                minimizable: false,
                maximizable: true,
                resizable: true,
                closed: true,
                cache: false,
                href: 'storage/showSaleRejectEnterDetailEditPage.do',
                modal: true,
                onClose: function () {
                    $('#storage-dialog-saleRejectEnterAddPage-content').dialog("destroy");
                },
                onLoad: function () {
                    $("#storage-saleRejectEnter-unitnum").focus();
                    $("#storage-saleRejectEnter-unitnum").select();

                    formaterNumSubZeroAndDot();

                    $("#storage-form-saleRejectEnterDetailAddPage").form('validate');
                }
            });
            $('#storage-dialog-saleRejectEnterAddPage-content').dialog("open");
        }
    }
    //保存发货单明细
    function addSaveDetail(goFlag) { //添加新数据确定后操作，
        var flag = $("#storage-form-saleRejectEnterDetailAddPage").form('validate');
        if (flag == false) {
            return false;
        }
        var form = $("#storage-form-saleRejectEnterDetailAddPage").serializeJSON();
        var widgetJson = $("#storage-saleRejectEnter-goodsid").goodsWidget('getObject');
        var goodsInfo = widgetJson;
        form.goodsInfo = goodsInfo;
        var rowIndex = 0;
        var rows = $("#storage-datagrid-saleRejectEnterAddPage").datagrid('getRows');
        for (var i = 0; i < rows.length; i++) {
            var rowJson = rows[i];
            if (rowJson.goodsid == undefined) {
                rowIndex = i;
                break;
            }
        }
        if (rowIndex == rows.length - 1) {
            $("#storage-datagrid-saleRejectEnterAddPage").datagrid('appendRow', {}); //如果是最后一行则添加一新行
        }
        $("#storage-datagrid-saleRejectEnterAddPage").datagrid('updateRow', {index: rowIndex, row: form}); //将数据更新到列表中
        if (goFlag) { //go为true确定并继续添加一条
            //页面重置
            otherEnterformReset();
        }
        else { //否则直接关闭
            $("#storage-dialog-saleRejectEnterAddPage-content").dialog('destroy');
        }
        $("#storage-saleRejectEnter-storageid").widget('disable');
        countTotal();

    }
    //修改保存
    function editSaveDetail(goFlag) {
        var flag = $("#storage-form-saleRejectEnterDetailAddPagee").form('validate');
        if (flag == false) {
            return false;
        }
        var form = $("#storage-form-saleRejectEnterDetailAddPage").serializeJSON();
        var row = $("#storage-datagrid-saleRejectEnterAddPage").datagrid('getSelected');
        var rowIndex = $("#storage-datagrid-saleRejectEnterAddPage").datagrid('getRowIndex', row);
        form.goodsInfo = row.goodsInfo;
        $("#storage-datagrid-saleRejectEnterAddPage").datagrid('updateRow', {index: rowIndex, row: form}); //将数据更新到列表中
        $("#storage-dialog-saleRejectEnterAddPage-content").dialog('destroy');
        countTotal();
    }
    //删除明细
    function removeDetail() {
        var row = $("#storage-datagrid-saleRejectEnterAddPage").datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        $.messager.confirm("提醒", "确定删除该商品明细?", function (r) {
            if (r) {
                var rowIndex = $("#storage-datagrid-saleRejectEnterAddPage").datagrid('getRowIndex', row);
                $("#storage-datagrid-saleRejectEnterAddPage").datagrid('deleteRow', rowIndex);
                countTotal();
                var rows = $("#storage-datagrid-saleRejectEnterAddPage").datagrid('getRows');
                var index = -1;
                for (var i = 0; i < rows.length; i++) {
                    if (rows[i].goodsid != undefined) {
                        index = i;
                        break;
                    }
                }
                if (index == -1) {
                    $("#storage-saleRejectEnter-storageid").widget('enable');
                }
            }
        });
    }
    //计算合计
    function countTotal() {
        var rows = $("#storage-datagrid-saleRejectEnterAddPage").datagrid('getRows');
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
        $("#storage-datagrid-saleRejectEnterAddPage").datagrid("reloadFooter", [{
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
            $("#storage-saleRejectEnter-remark").focus();
            $("#storage-dialog-saleRejectEnterAddPage-content").dialog("close");
        });
        $(document).bind('keydown', 'ctrl+enter', function () {
            $("#storage-saleRejectEnter-remark").focus();
            $("#storage-savegoon-saleRejectEnterDetailAddPage").trigger("click");
            $("#storage-savegoon-saleRejectEnterDetailEditPage").trigger("click");
            return false;
        });
        $(document).bind('keydown', '+', function () {
            $("#storage-saleRejectEnter-remark").focus();
            setTimeout(function () {
                $("#storage-savegoon-saleRejectEnterDetailAddPage").trigger("click");
                $("#storage-savegoon-saleRejectEnterDetailEditPage").trigger("click");
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
            id: "salerejectenter-dialog-print",
            code: "storage_salerejectenter",
            url_preview: "print/storage/saleRejectEnterPrintView.do",
            url_print: "print/storage/saleRejectEnterPrint.do",
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
                var printtimes = $("#storage-printtimes-rejectBillAddPage").val() || 0;
                if (printtimes > 0)
                    printParam.printIds = [id];
                return true;
            },
            onPrintSuccess: function (option) {
                var id = $("#storage-hidden-billid").val();
                var thepage = tabsWindowURL("/storage/showSaleRejectEnterListPage.do");
                if (thepage == null) {
                    return false;
                }
                var thegrid = thepage.$("#storage-datagrid-saleRejectEnterPage");
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
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
