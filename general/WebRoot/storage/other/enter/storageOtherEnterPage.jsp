<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>其他入库单操作页面</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>
<body>
<div id="storage-layout-storageOtherEnterPage" class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="storage-buttons-storageOtherEnterPage" style="height:26px;"></div>
    </div>
    <div data-options="region:'center',border:false">
        <div id="storage-panel-storageOtherEnterPage"></div>
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
        <security:authorize url="/storage/storageOtherEnterShowAmount.do">
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

    var page_url = "storage/storageOtherEnterAddPage.do";
    var page_type = '${type}';
    if (page_type == "view" || page_type == "handle") {
        page_url = "storage/storageOtherEnterViewPage.do?id=${id}";
    } else if (page_type == "edit") {
        var flag = '${flag}';
        if (flag == "true") {
            page_url = "storage/storageOtherEnterEditPage.do?id=${id}";
        } else {
            $.messager.alert("提醒", "该订单已不存在！");
            page_url = null;
        }

    }
    $(function () {
        $("#storage-panel-storageOtherEnterPage").panel({
            href: page_url,
            cache: false,
            maximized: true,
            border: false
        });
        //按钮
        $("#storage-buttons-storageOtherEnterPage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/storage/storageOtherEnterAddPage.do">
                {
                    type: 'button-add',
                    handler: function () {
                        $("#storage-panel-storageOtherEnterPage").panel({
                            href: 'storage/storageOtherEnterAddPage.do',
                            title: '',
                            cache: false,
                            maximized: true,
                            border: false
                        });
                    },
                    url: '/storage/storageOtherEnterAddPage.do'
                },
                </security:authorize>
                <security:authorize url="/storage/addstorageOtherEnterHold.do">
                {
                    type: 'button-hold',
                    handler: function () {
                        var type = $("#storage-buttons-storageOtherEnterPage").buttonWidget("getOperType");
                        if (type == "add") {
                            //暂存
                            $("#storage-form-storageOtherEnterAdd").attr("action", "storage/addStorageOtherEnterHold.do");
                            $("#storage-form-storageOtherEnterAdd").submit();
                        } else if (type == "edit") {
                            //暂存
                            $("#storage-form-storageOtherEnterAdd").attr("action", "storage/editStorageOtherEnterHold.do");
                            $("#storage-form-storageOtherEnterAdd").submit();
                        }
                    },
                    url: '/storage/addstorageOtherEnterHold.do'
                },
                </security:authorize>
                <security:authorize url="/storage/addstorageOtherEnterSave.do">
                {
                    type: 'button-save',
                    handler: function () {
                        var type = $("#storage-buttons-storageOtherEnterPage").buttonWidget("getOperType");
                        if (type == "add") {
                            //暂存
                            $("#storage-form-storageOtherEnterAdd").attr("action", "storage/addStorageOtherEnterSave.do");
                            $("#storage-form-storageOtherEnterAdd").submit();
                        } else if (type == "edit") {
                            $("#storage-form-storageOtherEnterAdd").attr("action", "storage/editStorageOtherEnterSave.do");
                            $("#storage-form-storageOtherEnterAdd").submit();
                        }
                    },
                    url: '/storage/addstorageOtherEnterSave.do'
                },
                </security:authorize>
                <security:authorize url="/storage/addstorageOtherEnterSaveAudit.do">
                {
                    type: 'button-saveaudit',
                    handler: function () {
                        $.messager.confirm("提醒", "确定保存并审核该其他入库单信息？", function (r) {
                            if (r) {
                                var type = $("#storage-buttons-storageOtherEnterPage").buttonWidget("getOperType");
                                $("#storage-purchaseEnter-saveaudit").val("saveaudit");
                                if (type == "add") {
                                    //暂存
                                    $("#storage-form-storageOtherEnterAdd").attr("action", "storage/addStorageOtherEnterSave.do");
                                    $("#storage-form-storageOtherEnterAdd").submit();
                                } else if (type == "edit") {
                                    $("#storage-form-storageOtherEnterAdd").attr("action", "storage/editStorageOtherEnterSave.do");
                                    $("#storage-form-storageOtherEnterAdd").submit();
                                }
                            }
                        });
                    },
                    url: '/storage/addstorageOtherEnterSave.do'
                },
                </security:authorize>
                <security:authorize url="/storage/storageOtherEnterGiveUp.do">
                {
                    type: 'button-giveup',
                    handler: function () {
                        var type = $("#storage-buttons-storageOtherEnterPage").buttonWidget("getOperType");
                        if (type == "add") {
                            var currTitle = top.$('#tt').tabs('getSelected').panel('options').title;
                            top.closeTab(currTitle);
                        }
                        else if (type == "edit") {
                            var id = $("#storage-hidden-billid").val();
                            if (id == "") {
                                return false;
                            }
                            $("#storage-panel-storageOtherEnterPage").panel({
                                href: 'storage/storageOtherEnterViewPage.do?id=' + id,
                                title: '',
                                cache: false,
                                maximized: true,
                                border: false
                            });
                        }
                    },
                    url: '/storage/storageOtherEnterGiveUp.do'
                },
                </security:authorize>
                <security:authorize url="/storage/deleteStorageOtherEnter.do">
                {
                    type: 'button-delete',
                    handler: function () {
                        $.messager.confirm("提醒", "是否删除当前其他入库单？", function (r) {
                            if (r) {
                                var id = $("#storage-hidden-billid").val();
                                if (id != "") {
                                    loading("删除中..");
                                    $.ajax({
                                        url: 'storage/deleteStorageOtherEnter.do?id=' + id,
                                        type: 'post',
                                        dataType: 'json',
                                        success: function (json) {
                                            loaded();
                                            if (json.flag) {
                                                var object = $("#storage-buttons-storageOtherEnterPage").buttonWidget("removeData", id);
                                                $.messager.alert("提醒", "删除成功");
                                                if (null != object) {
                                                    $("#storage-panel-storageOtherEnterPage").panel({
                                                        href: 'storage/storageOtherEnterEditPage.do?id=' + object.id,
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
                    url: '/storage/deleteStorageOtherEnter.do'
                },
                </security:authorize>
                <security:authorize url="/storage/auditStorageOtherEnter.do">
                {
                    type: 'button-audit',
                    handler: function () {
                        $.messager.confirm("提醒", "是否审核其他入库单？", function (r) {
                            if (r) {
                                var id = $("#storage-hidden-billid").val();
                                if (id != "") {
                                    loading("审核中..");
                                    $.ajax({
                                        url: 'storage/auditStorageOtherEnter.do?id=' + id,
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
                                                $("#storage-panel-storageOtherEnterPage").panel({
                                                    href: 'storage/storageOtherEnterEditPage.do?id=' + id,
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
                    url: '/storage/auditStorageOtherEnter.do'
                },
                </security:authorize>
                <security:authorize url="/storage/oppauditStorageOtherEnter.do">
                {
                    type: 'button-oppaudit',
                    handler: function () {
                        var businessdate = $("#storage-storageOtherEnter-businessdate").val();
                        var flag = isDoneOppauditBillCaseAccounting(businessdate);
                        if (!flag) {
                            $.messager.alert("提醒", "业务日期不在会计区间内或未设置会计区间,不允许反审!");
                            return false;
                        }
                        $.messager.confirm("提醒", "是否反审其他入库单？", function (r) {
                            if (r) {
                                var id = $("#storage-hidden-billid").val();
                                if (id != "") {
                                    loading("审核中..");
                                    $.ajax({
                                        url: 'storage/oppauditStorageOtherEnter.do?id=' + id,
                                        type: 'post',
                                        dataType: 'json',
                                        success: function (json) {
                                            loaded();
                                            if (json.flag) {
                                                $.messager.alert("提醒", "反审成功");
                                                $("#storage-panel-storageOtherEnterPage").panel({
                                                    href: 'storage/storageOtherEnterEditPage.do?id=' + id,
                                                    title: '',
                                                    cache: false,
                                                    maximized: true,
                                                    border: false
                                                });
                                            } else {
                                                $.messager.alert("提醒", "反审失败." + json.msg);
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
                    url: '/storage/auditStorageOtherEnter.do'
                },
                </security:authorize>
                <security:authorize url="/storage/storageOtherEnterBackViewPage.do">
                {
                    type: 'button-back',
                    handler: function (data) {
                        $("#storage-panel-storageOtherEnterPage").panel({
                            href: 'storage/storageOtherEnterEditPage.do?id=' + data.id,
                            title: '',
                            cache: false,
                            maximized: true,
                            border: false
                        });
                    },
                    url: '/storage/storageOtherEnterBackViewPage.do'
                },
                </security:authorize>
                <security:authorize url="/storage/storageOtherEnterNextViewPage.do">
                {
                    type: 'button-next',
                    handler: function (data) {
                        $("#storage-panel-storageOtherEnterPage").panel({
                            href: 'storage/storageOtherEnterEditPage.do?id=' + data.id,
                            title: '',
                            cache: false,
                            maximized: true,
                            border: false
                        });
                    },
                    url: '/storage/storageOtherEnterNextViewPage.do'
                },
                </security:authorize>
                {}
            ],
            buttons: [
                <security:authorize url="/storage/storageOtherEnterPrintViewBtn.do">
                {
                    id: 'button-printview-order',
                    name: '打印预览',
                    iconCls: 'button-preview',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/storageOtherEnterPrintBtn.do">
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
            layoutid: 'storage-layout-storageOtherEnterPage',
            model: 'bill',
            type: 'view',
            tab: '其他入库单列表',
            taburl: '/storage/showStorageOtherEnterListPage.do',
            id: '${id}',
            datagrid: 'storage-datagrid-storageOtherEnterPage'
        });
    });
    //显示其他入库单明细添加页面
    function beginAddDetail() {
        //验证表单
        var flag = $("#storage-form-storageOtherEnterAdd").form('validate');
        if (flag == false) {
            return false;
        }
        var storageid = $("#storage-storageOtherEnter-storageid").widget("getValue");
        var customerid = $("#storage-storageOtherEnter-customerid").widget("getValue");
        $('<div id="storage-dialog-storageOtherEnterAddPage-content"></div>').appendTo('#storage-dialog-storageOtherEnterAddPage');
        $('#storage-dialog-storageOtherEnterAddPage-content').dialog({
            title: '其他入库单明细添加',
            width: 680,
            height: 400,
            collapsible: false,
            minimizable: false,
            maximizable: true,
            resizable: true,
            closed: true,
            cache: false,
            modal: true,
            href: 'storage/showStorageOtherEnterDetailAddPage.do',
            onClose: function () {
                $('#storage-dialog-storageOtherEnterAddPage-content').dialog("destroy");
            },
            onLoad: function () {
                $("#storage-storageOtherEnter-goodsid").focus();
            }
        });
        $('#storage-dialog-storageOtherEnterAddPage-content').dialog("open");
    }
    //显示其他入库单成本调整类型明细添加页面
    function beginCostAddDetail() {
        //验证表单
        var flag = $("#storage-form-storageOtherEnterAdd").form('validate');
        if (flag == false) {
            return false;
        }
        var storageid = $("#storage-storageOtherEnter-storageid").widget("getValue");
        var customerid = $("#storage-storageOtherEnter-customerid").widget("getValue");
        $('<div id="storage-dialog-storageOtherEnterAddPage-content"></div>').appendTo('#storage-dialog-storageOtherEnterAddPage');
        $('#storage-dialog-storageOtherEnterAddPage-content').dialog({
            title: '其他入库单明细添加',
            width: 680,
            height: 400,
            collapsible: false,
            minimizable: false,
            maximizable: true,
            resizable: true,
            closed: true,
            cache: false,
            modal: true,
            href: 'storage/showStorageOtherEnterDetailAddPageForCost.do',
            onClose: function () {
                $('#storage-dialog-storageOtherEnterAddPage-content').dialog("destroy");
            },
            onLoad: function () {
                $("#storage-storageOtherEnter-goodsid").focus();
            }
        });
        $('#storage-dialog-storageOtherEnterAddPage-content').dialog("open");
    }
    //显示其他入库单明细修改页面
    function beginEditDetail() {
        //验证表单
        var flag = $("#storage-form-storageOtherEnterAdd").form('validate');
        var row = $("#storage-datagrid-storageOtherEnterAddPage").datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        if (row.goodsid == undefined) {
            beginAddDetail();
        } else {
            $('<div id="storage-dialog-storageOtherEnterAddPage-content"></div>').appendTo('#storage-dialog-storageOtherEnterAddPage');
            $('#storage-dialog-storageOtherEnterAddPage-content').dialog({
                title: '其他入库单明细修改',
                width: 680,
                height: 400,
                collapsible: false,
                minimizable: false,
                maximizable: true,
                resizable: true,
                closed: true,
                cache: false,
                href: 'storage/showStorageOtherEnterDetailEditPage.do',
                modal: true,
                onClose: function () {
                    $('#storage-dialog-storageOtherEnterAddPage-content').dialog("destroy");
                },
                onLoad: function () {
                    $("#storage-storageOtherEnter-unitnum").focus();
                    $("#storage-storageOtherEnter-unitnum").select();

                    formaterNumSubZeroAndDot();

                    $("#storage-form-storageOtherEnterDetailAddPage").form('validate');
                }
            });
            $('#storage-dialog-storageOtherEnterAddPage-content').dialog("open");
        }
    }
    //显示成本类型其他入库单明细修改页面
    function beginCostEditDetail() {
        //验证表单
        var flag = $("#storage-form-storageOtherEnterAdd").form('validate');
        var row = $("#storage-datagrid-storageOtherEnterAddPage").datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        if (row.goodsid == undefined) {
            beginCostAddDetail();
        } else {
            $('<div id="storage-dialog-storageOtherEnterAddPage-content"></div>').appendTo('#storage-dialog-storageOtherEnterAddPage');
            $('#storage-dialog-storageOtherEnterAddPage-content').dialog({
                title: '其他入库单明细修改',
                width: 680,
                height: 400,
                collapsible: false,
                minimizable: false,
                maximizable: true,
                resizable: true,
                closed: true,
                cache: false,
                href: 'storage/showStorageOtherEnterDetailEditPageForCost.do',
                modal: true,
                onClose: function () {
                    $('#storage-dialog-storageOtherEnterAddPage-content').dialog("destroy");
                },
                onLoad: function () {
                    $("#storage-storageOtherEnter-unitnum").focus();
                    $("#storage-storageOtherEnter-unitnum").select();

                    formaterNumSubZeroAndDot();

                    $("#storage-form-storageOtherEnterDetailAddPage").form('validate');
                }
            });
            $('#storage-dialog-storageOtherEnterAddPage-content').dialog("open");
        }
    }

    //保存其他入库单明细
    function addSaveDetail(goFlag) { //添加新数据确定后操作，
        var flag = $("#storage-form-storageOtherEnterDetailAddPage").form('validate');
        if (flag == false) {
            return false;
        }
        var form = $("#storage-form-storageOtherEnterDetailAddPage").serializeJSON();
        var widgetJson = $("#storage-storageOtherEnter-goodsid").goodsWidget('getObject');
        var goodsInfo = widgetJson;
        form.goodsInfo = goodsInfo;
        $.ajax({
            url: 'basefiles/getItemByGoodsAndStorage.do',
            data:{
                goodsid:widgetJson.id,
                storageid:$("#storage-storageOtherEnter-storageid").widget('getValue')
            },
            async: false,
            type: 'post',
            dataType: 'json',
            success: function (json) {
                form.goodsInfo.itemno=json.itemno;
            },
        });
        var rowIndex = 0;
        var rows = $("#storage-datagrid-storageOtherEnterAddPage").datagrid('getRows');
        for (var i = 0; i < rows.length; i++) {
            var rowJson = rows[i];
            if (rowJson.goodsid == undefined) {
                rowIndex = i;
                break;
            }
        }
        if (rowIndex == rows.length - 1) {
            $("#storage-datagrid-storageOtherEnterAddPage").datagrid('appendRow', {}); //如果是最后一行则添加一新行
        }
        $("#storage-datagrid-storageOtherEnterAddPage").datagrid('updateRow', {index: rowIndex, row: form}); //将数据更新到列表中
        if (goFlag) { //go为true确定并继续添加一条
            //页面重置
            otherEnterformReset();
        }
        else { //否则直接关闭
            $("#storage-dialog-storageOtherEnterAddPage-content").dialog('destroy');
        }
        $("#storage-storageOtherEnter-storageid").widget("readonly", true);
        countTotal();

    }
    //修改保存
    function editSaveDetail(goFlag) {
        var flag = $("#storage-form-storageOtherEnterDetailAddPage").form('validate');
        if (flag == false) {
            return false;
        }
        var form = $("#storage-form-storageOtherEnterDetailAddPage").serializeJSON();
        var row = $("#storage-datagrid-storageOtherEnterAddPage").datagrid('getSelected');
        var rowIndex = $("#storage-datagrid-storageOtherEnterAddPage").datagrid('getRowIndex', row);
        form.goodsInfo = row.goodsInfo;
        $("#storage-datagrid-storageOtherEnterAddPage").datagrid('updateRow', {index: rowIndex, row: form}); //将数据更新到列表中
        if (goFlag) { //go为true确定并继续修改下一条
            var rowSelected = $("#storage-datagrid-storageOtherEnterAddPage").datagrid("getSelected");
            var rowIndex = $("#storage-datagrid-storageOtherEnterAddPage").datagrid("getRowIndex", rowSelected);
            var rows = $("#storage-datagrid-storageOtherEnterAddPage").datagrid("getRows");
            var rownums = 0;
            for (var i = 0; i < rows.length; i++) {
                if (rows[i].goodsid != null) {
                    rownums++;
                }
            }
            if (rowIndex < rownums - 1) {
                rowIndex = rowIndex + 1;
                $("#storage-datagrid-storageOtherEnterAddPage").datagrid("selectRow", rowIndex);
                $("#storage-form-storageOtherEnterDetailAddPage").form('clear');
                //加载数据
                var object = $("#storage-datagrid-storageOtherEnterAddPage").datagrid("getSelected");
                $("#storage-form-storageOtherEnterDetailAddPage").form("load", object);
                $("#storage-storageOtherEnter-goodsname").val(object.goodsInfo.name);
                $("#storage-storageOtherEnter-goodsbrandName").val(object.goodsInfo.brandName);
                $("#storage-storageOtherEnter-goodsmodel").val(object.goodsInfo.model);
                $("#storage-storageOtherEnter-goodsbarcode").val(object.goodsInfo.barcode);
                $("#storage-storageOtherEnter-unitnum").val(parseFloat(object.unitnum));
                $("#storage-storageOtherEnter-unitnum-aux").val(parseFloat(object.auxnum));
                $("#storage-storageOtherEnter-unitnum-unit").val(parseFloat(object.auxremainder));
                $("#storage-storageOtherEnter-goodsunitname1").html(object.unitname);
                $("#storage-storageOtherEnter-auxunitname1").html(object.auxunitname);
                setDetailIsbatch();
                $("#storage-storageOtherEnter-unitnum").focus();
                $("#storage-storageOtherEnter-unitnum").select();
            } else {
                $.messager.alert("提醒", "已经到最后一条了！");
                $("#storage-dialog-storageOtherEnterAddPage-content").dialog('destroy');
            }
        }
        else { //否则直接关闭
            $("#storage-dialog-storageOtherEnterAddPage-content").dialog('destroy');
        }
        countTotal();
    }
    //删除明细
    function removeDetail() {
        var row = $("#storage-datagrid-storageOtherEnterAddPage").datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        $.messager.confirm("提醒", "确定删除该商品明细?", function (r) {
            if (r) {
                var rowIndex = $("#storage-datagrid-storageOtherEnterAddPage").datagrid('getRowIndex', row);
                $("#storage-datagrid-storageOtherEnterAddPage").datagrid('deleteRow', rowIndex);
                countTotal();
                var rows = $("#storage-datagrid-storageOtherEnterAddPage").datagrid('getRows');
                var index = -1;
                for (var i = 0; i < rows.length; i++) {
                    if (rows[i].goodsid != undefined) {
                        index = i;
                        break;
                    }
                }
                if (index == -1) {
                    $("#storage-storageOtherEnter-storageid").widget("readonly", false)
                }
            }
        });
    }
    //计算合计
    function countTotal() {
        var rows = $("#storage-datagrid-storageOtherEnterAddPage").datagrid('getRows');
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
        $("#storage-datagrid-storageOtherEnterAddPage").datagrid("reloadFooter", [{
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
            $("#storage-storageOtherEnter-remark").focus();
            $("#storage-dialog-storageOtherEnterAddPage-content").dialog("close");
        });
        $(document).bind('keydown', 'ctrl+enter', function () {
            $("#storage-storageOtherEnter-remark").focus();
            $("#storage-savegoon-storageOtherEnterDetailAddPage").trigger("click");
            $("#storage-savegoon-storageOtherEnterDetailEditPage").trigger("click");
        });
        $(document).bind('keydown', '+', function () {
            $("#storage-storageOtherEnter-remark").focus();
            setTimeout(function () {
                $("#storage-savegoon-storageOtherEnterDetailAddPage").trigger("click");
                $("#storage-savegoon-storageOtherEnterDetailEditPage").trigger("click");
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
            id: "otherenter-dialog-print",
            code: "storage_otherenter",
            url_preview: "print/storage/storageOtherEnterPrintView.do",
            url_print: "print/storage/storageOtherEnterPrint.do",
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
                var printtimes = $("#storage-storageOtherEnter-printtimes").val() || 0;
                if (printtimes > 0)
                    printParam.printIds = [id];
                return true;
            },
            printAfterHandler: function (option, printParam) {
                tabsWindowURL(pageListUrl).$("#storage-datagrid-storageOtherEnterPage").datagrid('reload');
            }
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
