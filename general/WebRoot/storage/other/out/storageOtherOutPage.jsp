<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>其他出库单操作页面</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>
<body>
<div id="storage-layoutid-storageOtherOutPage" class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="storage-buttons-storageOtherOutPage" style="height:26px;"></div>
    </div>
    <div data-options="region:'center',border:false">
        <div id="storage-panel-storageOtherOutPage"></div>
    </div>
</div>
<div id="storage-panel-relation-upper"></div>
<div id="storage-panel-sourceQueryPage"></div>
<div id="workflow-addidea-dialog-page"></div>
<input type="hidden" id="storage-hidden-billid"/>
<script type="text/javascript">
    //根据初始的列与用户保存的列生成以及字段权限生成新的列
    var tableColJson = [[
        {field: 'goodsid', title: '商品编码', width: 60},
        {
            field: 'goodsname', title: '商品名称', width: 150, aliascol: 'goodsid',
            formatter: function (value, rowData, rowIndex) {
                if (rowData.goodsInfo != null) {
                    return rowData.goodsInfo.name;
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
        <security:authorize url="/storage/storageOtherOutShowAmount.do">
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
        </security:authorize>
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
        {field: 'remark', title: '备注', width: 100}
    ]];

    var page_url = "storage/storageOtherOutAddPage.do";
    var page_type = '${type}';
    if (page_type == "view" || page_type == "handle") {
        page_url = "storage/storageOtherOutViewPage.do?id=${id}";
    } else if (page_type == "edit") {
        var flag = '${flag}';
        if (flag == "true") {
            page_url = "storage/storageOtherOutEditPage.do?id=${id}";
        }
        else {
            $.messager.alert("提醒", "该订单已不存在！");
            page_url = null;
        }
    }
    $(function () {
        $("#storage-panel-storageOtherOutPage").panel({
            href: page_url,
            cache: false,
            maximized: true,
            border: false
        });
        //按钮
        $("#storage-buttons-storageOtherOutPage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/storage/storageOtherOutAddPage.do">
                {
                    type: 'button-add',
                    handler: function () {
                        $("#storage-panel-storageOtherOutPage").panel({
                            href: 'storage/storageOtherOutAddPage.do',
                            title: '',
                            cache: false,
                            maximized: true,
                            border: false
                        });
                    },
                    url: '/storage/storageOtherOutAddPage.do'
                },
                </security:authorize>
                <security:authorize url="/storage/addstorageOtherOutHold.do">
                {
                    type: 'button-hold',
                    handler: function () {
                        var type = $("#storage-buttons-storageOtherOutPage").buttonWidget("getOperType");
                        if (type == "add") {
                            //暂存
                            $("#storage-form-storageOtherOutAdd").attr("action", "storage/addStorageOtherOutHold.do");
                            $("#storage-form-storageOtherOutAdd").submit();
                        } else if (type == "edit") {
                            //暂存
                            $("#storage-form-storageOtherOutAdd").attr("action", "storage/editStorageOtherOutHold.do");
                            $("#storage-form-storageOtherOutAdd").submit();
                        }
                    },
                    url: '/storage/addstorageOtherOutHold.do'
                },
                </security:authorize>
                <security:authorize url="/storage/addstorageOtherOutSave.do">
                {
                    type: 'button-save',
                    handler: function () {
                        var type = $("#storage-buttons-storageOtherOutPage").buttonWidget("getOperType");
                        if (type == "add") {
                            //暂存
                            $("#storage-form-storageOtherOutAdd").attr("action", "storage/addStorageOtherOutSave.do");
                            $("#storage-form-storageOtherOutAdd").submit();
                        } else if (type == "edit") {
                            $("#storage-form-storageOtherOutAdd").attr("action", "storage/editStorageOtherOutSave.do");
                            $("#storage-form-storageOtherOutAdd").submit();
                        }
                    },
                    url: '/storage/addstorageOtherOutSave.do'
                },
                </security:authorize>
                <security:authorize url="/storage/addstorageOtherOutSaveAudit.do">
                {
                    type: 'button-saveaudit',
                    handler: function () {
                        $.messager.confirm("提醒", "确定保存并审核该其他出库单信息？", function (r) {
                            if (r) {
                                var type = $("#storage-buttons-storageOtherOutPage").buttonWidget("getOperType");
                                $("#storage-purchaseEnter-saveaudit").val("saveaudit");
                                if (type == "add") {
                                    //暂存
                                    $("#storage-form-storageOtherOutAdd").attr("action", "storage/addStorageOtherOutSave.do");
                                    $("#storage-form-storageOtherOutAdd").submit();
                                } else if (type == "edit") {
                                    $("#storage-form-storageOtherOutAdd").attr("action", "storage/editStorageOtherOutSave.do");
                                    $("#storage-form-storageOtherOutAdd").submit();
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/storageOtherOutGiveUp.do">
                {
                    type: 'button-giveup',
                    handler: function () {
                        var type = $("#storage-buttons-storageOtherOutPage").buttonWidget("getOperType");
                        if (type == "add") {
                            var currTitle = top.$('#tt').tabs('getSelected').panel('options').title;
                            top.closeTab(currTitle);
                        }
                        else if (type == "edit") {
                            var id = $("#storage-hidden-billid").val();
                            if (id == "") {
                                return false;
                            }
                            $("#storage-panel-storageOtherOutPage").panel({
                                href: 'storage/storageOtherOutViewPage.do?id=' + id,
                                title: '',
                                cache: false,
                                maximized: true,
                                border: false
                            });
                        }
                    },
                    url: '/storage/storageOtherOutGiveUp.do'
                },
                </security:authorize>
                <security:authorize url="/storage/deleteStorageOtherOut.do">
                {
                    type: 'button-delete',
                    handler: function () {
                        $.messager.confirm("提醒", "是否删除当前其他出库单？", function (r) {
                            if (r) {
                                var id = $("#storage-hidden-billid").val();
                                if (id != "") {
                                    loading("删除中..");
                                    $.ajax({
                                        url: 'storage/deleteStorageOtherOut.do?id=' + id,
                                        type: 'post',
                                        dataType: 'json',
                                        success: function (json) {
                                            loaded();
                                            if (json.flag) {
                                                var object = $("#storage-buttons-storageOtherOutPage").buttonWidget("removeData", id);
                                                $.messager.alert("提醒", "删除成功");
                                                if (null != object) {
                                                    $("#storage-panel-storageOtherOutPage").panel({
                                                        href: 'storage/storageOtherOutEditPage.do?id=' + object.id,
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
                    url: '/storage/deleteStorageOtherOut.do'
                },
                </security:authorize>
                <security:authorize url="/storage/auditStorageOtherOut.do">
                {
                    type: 'button-audit',
                    handler: function () {
                        $.messager.confirm("提醒", "是否审核其他出库单？", function (r) {
                            if (r) {
                                var id = $("#storage-hidden-billid").val();
                                if (id != "") {
                                    loading("审核中..");
                                    $.ajax({
                                        url: 'storage/auditStorageOtherOut.do?id=' + id,
                                        type: 'post',
                                        dataType: 'json',
                                        success: function (json) {
                                            loaded();
                                            if (json.flag) {
                                                $.messager.alert("提醒", "审核成功");
                                                $("#storage-storageOtherOut-status-select").val("4");
                                                $("#storage-buttons-storageOtherOutPage").buttonWidget("setDataID", {
                                                    id: id,
                                                    state: '4',
                                                    type: 'view'
                                                });
                                                $("#storage-panel-storageOtherOutPage").panel({
                                                    href: 'storage/storageOtherOutEditPage.do?id=' + id,
                                                    title: '',
                                                    cache: false,
                                                    maximized: true,
                                                    border: false
                                                });
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
                    url: '/storage/auditStorageOtherOut.do'
                },
                </security:authorize>
                <security:authorize url="/storage/oppauditStorageOtherOut.do">
                {
                    type: 'button-oppaudit',
                    handler: function () {
                        var businessdate = $("#storage-storageOtherOut-businessdate").val();
                        var flag = isDoneOppauditBillCaseAccounting(businessdate);
                        if (!flag) {
                            $.messager.alert("提醒", "业务日期不在会计区间内或未设置会计区间,不允许反审!");
                            return false;
                        }
                        $.messager.confirm("提醒", "是否反审其他出库单？", function (r) {
                            if (r) {
                                var id = $("#storage-hidden-billid").val();
                                if (id != "") {
                                    loading("审核中..");
                                    $.ajax({
                                        url: 'storage/oppauditStorageOtherOut.do?id=' + id,
                                        type: 'post',
                                        dataType: 'json',
                                        success: function (json) {
                                            loaded();
                                            if (json.flag) {
                                                $.messager.alert("提醒", "反审成功");
                                                $("#storage-panel-storageOtherOutPage").panel({
                                                    href: 'storage/storageOtherOutEditPage.do?id=' + id,
                                                    title: '',
                                                    cache: false,
                                                    maximized: true,
                                                    border: false
                                                });
                                            } else {
                                                $.messager.alert("提醒", "反审失败");
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
                    url: '/storage/auditStorageOtherOut.do'
                },
                </security:authorize>
                <security:authorize url="/storage/storageOtherOutBackViewPage.do">
                {
                    type: 'button-back',
                    handler: function (data) {
                        $("#storage-panel-storageOtherOutPage").panel({
                            href: 'storage/storageOtherOutEditPage.do?id=' + data.id,
                            title: '',
                            cache: false,
                            maximized: true,
                            border: false
                        });
                    },
                    url: '/storage/storageOtherOutBackViewPage.do'
                },
                </security:authorize>
                <security:authorize url="/storage/storageOtherOutNextViewPage.do">
                {
                    type: 'button-next',
                    handler: function (data) {
                        $("#storage-panel-storageOtherOutPage").panel({
                            href: 'storage/storageOtherOutEditPage.do?id=' + data.id,
                            title: '',
                            cache: false,
                            maximized: true,
                            border: false
                        });
                    },
                    url: '/storage/storageOtherOutNextViewPage.do'
                },
                </security:authorize>
                {}
            ],
            buttons: [
                <security:authorize url="/storage/storageOtherOutPrintViewBtn.do">
                {
                    id: 'button-printview-order',
                    name: '打印预览',
                    iconCls: 'button-preview',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/storageOtherOutPrintBtn.do">
                {
                    id: 'button-print-order',
                    name: '打印',
                    iconCls: 'button-print',
                    handler: function () {
                    }
                },
                </security:authorize>
                {}
            ],
            layoutid: 'storage-layoutid-storageOtherOutPage',
            model: 'bill',
            type: 'view',
            tab: '其他出库单列表',
            taburl: '/storage/showStorageOtherOutListPage.do',
            id: '${id}',
            datagrid: 'storage-datagrid-storageOtherOutPage'
        });
    });
    //显示其他出库单明细添加页面
    function beginAddDetail() {
        //验证表单
        var flag = $("#storage-form-storageOtherOutAdd").form('validate');
        if (flag == false) {
            return false;
        }
        var storageid = $("#storage-storageOtherOut-storageid").widget("getValue");
        var customerid = $("#storage-storageOtherOut-customerid").widget("getValue");
        $('<div id="storage-dialog-storageOtherOutAddPage-content"></div>').appendTo('#storage-dialog-storageOtherOutAddPage');
        $('#storage-dialog-storageOtherOutAddPage-content').dialog({
            title: '其他出库单明细添加',
            width: 680,
            height: 450,
            collapsible: false,
            minimizable: false,
            maximizable: true,
            resizable: true,
            closed: true,
            cache: false,
            modal: true,
            href: 'storage/showStorageOtherOutDetailAddPage.do',
            onClose: function () {
                $('#storage-dialog-storageOtherOutAddPage-content').dialog("destroy");
            },
            onLoad: function () {
                $("#storage-storageOtherOut-goodsid").focus();
            }
        });
        $('#storage-dialog-storageOtherOutAddPage-content').dialog("open");
    }
    //显示其他出库单明细添加页面
    function beginCostAddDetail() {
        //验证表单
        var flag = $("#storage-form-storageOtherOutAdd").form('validate');
        if (flag == false) {
            return false;
        }
        var storageid = $("#storage-storageOtherOut-storageid").widget("getValue");
        var customerid = $("#storage-storageOtherOut-customerid").widget("getValue");
        $('<div id="storage-dialog-storageOtherOutAddPage-content"></div>').appendTo('#storage-dialog-storageOtherOutAddPage');
        $('#storage-dialog-storageOtherOutAddPage-content').dialog({
            title: '其他出库单明细添加',
            width: 680,
            height: 450,
            collapsible: false,
            minimizable: false,
            maximizable: true,
            resizable: true,
            closed: true,
            cache: false,
            modal: true,
            href: 'storage/showStorageOtherOutDetailAddPageForCost.do',
            onClose: function () {
                $('#storage-dialog-storageOtherOutAddPage-content').dialog("destroy");
            },
            onLoad: function () {
                $("#storage-storageOtherOut-goodsid").focus();
            }
        });
        $('#storage-dialog-storageOtherOutAddPage-content').dialog("open");
    }
    //显示其他出库单明细修改页面
    function beginEditDetail() {
        //验证表单
        var flag = $("#storage-form-storageOtherOutAdd").form('validate');
        var row = $("#storage-datagrid-storageOtherOutAddPage").datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        if (row.goodsid == undefined) {
            beginAddDetail();
        } else {
            var storageInfo = $("#storage-storageOtherOut-storageid").widget('getObject');
            var url = 'storage/showStorageOtherOutDetailEditPage.do?goodsid=' + row.goodsid;
            $('<div id="storage-dialog-storageOtherOutAddPage-content"></div>').appendTo('#storage-dialog-storageOtherOutAddPage');
            $('#storage-dialog-storageOtherOutAddPage-content').dialog({
                title: '其他出库单明细修改',
                width: 680,
                height: 420,
                collapsible: false,
                minimizable: false,
                maximizable: true,
                resizable: true,
                closed: true,
                cache: false,
                href: url,
                modal: true,
                onClose: function () {
                    $('#storage-dialog-storageOtherOutAddPage-content').dialog("destroy");
                },
                onLoad: function () {
                    var isbatch = $("#storage-storageOtherOut-isbatch").val();
                    if (isbatch == '1') {
                        var param = null;
                        var storageid = $("#storage-storageOtherOut-storageid").widget('getValue');
                        if (storageid != null && storageid != "") {
                            param = [{field: 'goodsid', op: 'equal', value: row.goodsid},
                                {field: 'storageid', op: 'equal', value: storageid}];
                        } else {
                            param = [{field: 'goodsid', op: 'equal', value: row.goodsid}];
                        }
                        var reqFlag = false;
                        if (storageInfo.isbatch == "1") {
                            reqFlag = true;
                        }
                        $("#storage-storageOtherOut-batchno").widget({
                            referwid: 'RL_T_STORAGE_BATCH_LIST',
                            param: param,
                            width: 150,
                            singleSelect: true,
                            required: reqFlag,
                            onSelect: function (obj) {
                                $("#storage-storageOtherOut-summarybatchid").val(obj.id);
                                $("#storage-storageOtherOut-storagelocationname").val(obj.storagelocationname);
                                $("#storage-storageOtherOut-storagelocationid").val(obj.storagelocationid);
                                $("#storage-storageOtherOut-produceddate").val(obj.produceddate);
                                $("#storage-storageOtherOut-deadline").val(obj.deadline);

                                maxnum = obj.usablenum;
                                $("#storage-storageOtherOut-loadInfo").html("现存量：<font color='green'>" + obj.existingnum + obj.unitname + "</font>&nbsp;可用量：<font color='green'>" + obj.usablenum + obj.unitname + "</font>");
                                computNum();
                                $("#storage-storageOtherOut-unitnum").focus();
                                $("#storage-storageOtherOut-unitnum").select();
                            },
                            onClear: function () {
                                $("#storage-storageOtherOut-loadInfo").html("&nbsp;");
                                $("#storage-storageOtherOut-summarybatchid").val("");
                                $("#storage-storageOtherOut-storagelocationname").val("");
                                $("#storage-storageOtherOut-storagelocationid").val("");
                                $("#storage-storageOtherOut-produceddate").val("");
                                $("#storage-storageOtherOut-deadline").val("");
                            }
                        });
                    } else {
                        $("#storage-storageOtherOut-batchno").addClass("no_input");
                        $("#storage-storageOtherOut-batchno").attr("readonly", "readonly");
                    }
                    $("#storage-storageOtherOut-unitnum").focus();
                    $("#storage-storageOtherOut-unitnum").select();

                    goodsNumControl();

                    formaterNumSubZeroAndDot();

                    $("#storage-form-storageOtherOutDetailAddPage").form('validate');
                }
            });
            $('#storage-dialog-storageOtherOutAddPage-content').dialog("open");
        }
    }

    //显示其他出库单明细修改页面
    function beginCostEditDetail() {
        //验证表单
        var flag = $("#storage-form-storageOtherOutAdd").form('validate');
        var row = $("#storage-datagrid-storageOtherOutAddPage").datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        if (row.goodsid == undefined) {
            beginAddDetail();
        } else {
            var storageInfo = $("#storage-storageOtherOut-storageid").widget('getObject');
            var url = 'storage/showStorageOtherOutDetailEditPageForCost.do?goodsid=' + row.goodsid;
            $('<div id="storage-dialog-storageOtherOutAddPage-content"></div>').appendTo('#storage-dialog-storageOtherOutAddPage');
            $('#storage-dialog-storageOtherOutAddPage-content').dialog({
                title: '其他出库单明细修改',
                width: 680,
                height: 420,
                collapsible: false,
                minimizable: false,
                maximizable: true,
                resizable: true,
                closed: true,
                cache: false,
                href: url,
                modal: true,
                onClose: function () {
                    $('#storage-dialog-storageOtherOutAddPage-content').dialog("destroy");
                },
                onLoad: function () {
                    var isbatch = $("#storage-storageOtherOut-isbatch").val();
                    if (isbatch == '1') {
                        var param = null;
                        var storageid = $("#storage-storageOtherOut-storageid").widget('getValue');
                        if (storageid != null && storageid != "") {
                            param = [{field: 'goodsid', op: 'equal', value: row.goodsid},
                                {field: 'storageid', op: 'equal', value: storageid}];
                        } else {
                            param = [{field: 'goodsid', op: 'equal', value: row.goodsid}];
                        }
                        var reqFlag = false;
                        if (storageInfo.isbatch == "1") {
                            reqFlag = true;
                        }
                        $("#storage-storageOtherOut-batchno").widget({
                            referwid: 'RL_T_STORAGE_BATCH_LIST',
                            param: param,
                            width: 150,
                            singleSelect: true,
                            required: reqFlag,
                            onSelect: function (obj) {
                                $("#storage-storageOtherOut-summarybatchid").val(obj.id);
                                $("#storage-storageOtherOut-storagelocationname").val(obj.storagelocationname);
                                $("#storage-storageOtherOut-storagelocationid").val(obj.storagelocationid);
                                $("#storage-storageOtherOut-produceddate").val(obj.produceddate);
                                $("#storage-storageOtherOut-deadline").val(obj.deadline);

                                maxnum = obj.usablenum;
                                $("#storage-storageOtherOut-loadInfo").html("现存量：<font color='green'>" + obj.existingnum + obj.unitname + "</font>&nbsp;可用量：<font color='green'>" + obj.usablenum + obj.unitname + "</font>");
                                computNum();
                                $("#storage-storageOtherOut-unitnum").focus();
                                $("#storage-storageOtherOut-unitnum").select();
                            },
                            onClear: function () {
                                $("#storage-storageOtherOut-loadInfo").html("&nbsp;");
                                $("#storage-storageOtherOut-summarybatchid").val("");
                                $("#storage-storageOtherOut-storagelocationname").val("");
                                $("#storage-storageOtherOut-storagelocationid").val("");
                                $("#storage-storageOtherOut-produceddate").val("");
                                $("#storage-storageOtherOut-deadline").val("");
                            }
                        });
                    } else {
                        $("#storage-storageOtherOut-batchno").addClass("no_input");
                        $("#storage-storageOtherOut-batchno").attr("readonly", "readonly");
                    }
                    $("#storage-storageOtherOut-unitnum").focus();
                    $("#storage-storageOtherOut-unitnum").select();

                    goodsNumControl();

                    formaterNumSubZeroAndDot();

                    $("#storage-form-storageOtherOutDetailAddPage").form('validate');
                }
            });
            $('#storage-dialog-storageOtherOutAddPage-content').dialog("open");
        }
    }

    //保存其他出库单明细
    function addSaveDetail(goFlag) { //添加新数据确定后操作，
        var flag = $("#storage-form-storageOtherOutDetailAddPage").form('validate');
        if (flag == false) {
            return false;
        }
        var form = $("#storage-form-storageOtherOutDetailAddPage").serializeJSON();
        var widgetJson = $("#storage-storageOtherOut-goodsid").goodsWidget('getObject');
        var boxnum = $("#storage-storageOtherOut-boxnum").val();
        var goodsInfo = {
            id: widgetJson.id, name: widgetJson.name, brandName: widgetJson.brandName,
            model: widgetJson.model, barcode: widgetJson.barcode, boxnum: boxnum
        };
        form.goodsInfo = goodsInfo;
        $.ajax({
            url: 'basefiles/getItemByGoodsAndStorage.do',
            data:{
                goodsid:widgetJson.id,
                storageid:$("#storage-storageOtherOut-storageid").widget('getValue')
            },
            async: false,
            type: 'post',
            dataType: 'json',
            success: function (json) {
                form.goodsInfo.itemno=json.itemno;
            },
        });
        var rowIndex = 0;
        var rows = $("#storage-datagrid-storageOtherOutAddPage").datagrid('getRows');
        var updateFlag = false;
        for (var i = 0; i < rows.length; i++) {
            var rowJson = rows[i];
            if (rowJson.goodsid == widgetJson.id && rowJson.summarybatchid == form.summarybatchid && form.summarybatchid != null) {
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
            $("#storage-datagrid-storageOtherOutAddPage").datagrid('appendRow', {}); //如果是最后一行则添加一新行
        }
        $("#storage-datagrid-storageOtherOutAddPage").datagrid('updateRow', {index: rowIndex, row: form}); //将数据更新到列表中
        if (updateFlag) {
            $.messager.alert("提醒", "添加的明细商品重复，覆盖历史记录！");
        }
        if (goFlag) { //go为true确定并继续添加一条
            //页面重置
            otherEnterformReset();
        }
        else { //否则直接关闭
            $("#storage-dialog-storageOtherOutAddPage-content").dialog('destroy');
        }
        countTotal();
        $("#storage-storageOtherOut-storageid").widget("readonly", true);
    }
    //修改保存
    function editSaveDetail(goFlag) {
        var flag = $("#storage-form-storageOtherOutDetailAddPage").form('validate');
        if (flag == false) {
            return false;
        }
        var form = $("#storage-form-storageOtherOutDetailAddPage").serializeJSON();
        var row = $("#storage-datagrid-storageOtherOutAddPage").datagrid('getSelected');
        var rowIndex = $("#storage-datagrid-storageOtherOutAddPage").datagrid('getRowIndex', row);
        form.goodsInfo = row.goodsInfo;
        $("#storage-datagrid-storageOtherOutAddPage").datagrid('updateRow', {index: rowIndex, row: form}); //将数据更新到列表中
        if (goFlag) { //go为true确定并继续修改下一条
            var rowSelected = $("#storage-datagrid-storageOtherOutAddPage").datagrid("getSelected");
            var rowIndex = $("#storage-datagrid-storageOtherOutAddPage").datagrid("getRowIndex", rowSelected);
            var rows = $("#storage-datagrid-storageOtherOutAddPage").datagrid("getRows");
            var rownums = 0;
            for (var i = 0; i < rows.length; i++) {
                if (rows[i].goodsid != null) {
                    rownums++;
                }
            }
            if (rowIndex < rownums - 1) {
                rowIndex = rowIndex + 1;
                $("#storage-datagrid-storageOtherOutAddPage").datagrid("selectRow", rowIndex);
                $("#storage-dialog-storageOtherOutAddPage-content").dialog('destroy');
                beginEditDetail();
            } else {
                $.messager.alert("提醒", "已经到最后一条了！");
                $("#storage-dialog-storageOtherOutAddPage-content").dialog('destroy');
            }
        }
        else { //否则直接关闭
            $("#storage-dialog-storageOtherOutAddPage-content").dialog('destroy');
        }
        countTotal();
    }
    //删除明细
    function removeDetail() {
        var row = $("#storage-datagrid-storageOtherOutAddPage").datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        $.messager.confirm("提醒", "确定删除该商品明细?", function (r) {
            if (r) {
                var rowIndex = $("#storage-datagrid-storageOtherOutAddPage").datagrid('getRowIndex', row);
                $("#storage-datagrid-storageOtherOutAddPage").datagrid('deleteRow', rowIndex);
                countTotal();
                var rows = $("#storage-datagrid-storageOtherOutAddPage").datagrid('getRows');
                var index = -1;
                for (var i = 0; i < rows.length; i++) {
                    if (rows[i].goodsid != undefined) {
                        index = i;
                        break;
                    }
                }
                if (index == -1) {
                    $("#storage-storageOtherOut-storageid").widget("readonly", false);
                }
            }
        });
    }
    function getGoodsUsenum() {
        var storageid = $("#storage-storageOtherOut-storageid").widget("getValue");
        var goodsid = $("#storage-storageOtherOut-goodsid").goodsWidget("getValue");
        //控件其他出库的最大数量
        $.ajax({
            url: 'storage/getStorageSummarySumByGoodsid.do',
            type: 'post',
            data: {goodsid: goodsid, storageid: storageid},
            dataType: 'json',
            success: function (json) {
                if (json.storageSummary != null) {
                    maxnum = json.storageSummary.usablenum;
                    $("#storage-storageOtherOut-loadInfo").html("现存量：<font color='green'>" + json.storageSummary.existingnum + json.storageSummary.unitname + "</font>&nbsp;可用量：<font color='green'>" + json.storageSummary.usablenum + json.storageSummary.unitname + "</font>");
                }
            }
        });
    }
    //计算合计
    function countTotal() {
        var rows = $("#storage-datagrid-storageOtherOutAddPage").datagrid('getRows');
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
        $("#storage-datagrid-storageOtherOutAddPage").datagrid("reloadFooter", [{
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
            $("#storage-storageOtherOut-remark").focus();
            $("#storage-dialog-storageOtherOutAddPage-content").dialog("close");
        });
        $(document).bind('keydown', 'ctrl+enter', function () {
            $("#storage-storageOtherOut-remark").focus();
            $("#storage-savegoon-storageOtherOutDetailEditPage").trigger("click");
            $("#storage-savegoon-storageOtherOutDetailAddPage").trigger("click");
        });
        $(document).bind('keydown', '+', function () {
            $("#storage-storageOtherOut-remark").focus();
            setTimeout(function () {
                $("#storage-savegoon-storageOtherOutDetailEditPage").trigger("click");
                $("#storage-savegoon-storageOtherOutDetailAddPage").trigger("click");
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
            id: "otherout-dialog-print",
            code: "storage_otherout",
            url_preview: "print/storage/storageOtherOutPrintView.do",
            url_print: "print/storage/storageOtherOutPrint.do",
            btnPreview: "button-printview-order",
            btnPrint: "button-print-order",
            printlimit: "${printlimit}",
            getData: function (tableId, printParam) {
                var id = $("#storage-hidden-billid").val();
                if (id == "") {
                    $.messager.alert("警告", "找不到要打印的信息!");
                    return false;
                }
                printParam.idarrs = id;
                var printtimes = $("#storage-storageOtherOut-printtimes").val() || 0;
                if (printtimes > 0)
                    printParam.printIds = [id];
                return true;
            },
            printAfterHandler: function (option, printParam) {
                tabsWindowURL(pageListUrl).$("#storage-datagrid-storageOtherOutPage").datagrid('reload');
            }
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
