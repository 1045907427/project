<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>借货单操作页面</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>
<body>
<div id="storage-layoutid-lendPage" class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="storage-buttons-lendPage" style="height:26px;"></div>
    </div>
    <div data-options="region:'center',border:false">
        <div id="storage-panel-lendPage"></div>
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
        {field: 'remark', title: '备注', width: 100}
    ]];

    var page_url = "storage/lendAddPage.do?billtype=1";
    var page_type = '${type}';
    if (page_type == "view" || page_type == "handle") {
        page_url = "storage/lendViewPage.do?billtype=1&id=${id}";
    } else if (page_type == "edit") {
        var flag = '${flag}';
        if (flag == "true") {
            page_url = "storage/lendEditPage.do?billtype=1&id=${id}";
        }
        else {
            $.messager.alert("提醒", "该订单已不存在！");
            page_url = null;
        }
    }
    var pageListUrl = "/storage/showLendListPage.do?billtype=1";
    $(function () {
        $("#storage-panel-lendPage").panel({
            href: page_url,
            cache: false,
            maximized: true,
            border: false
        });
        //按钮
        $("#storage-buttons-lendPage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/storage/out/lendAddPage.do">
                {
                    type: 'button-add',
                    handler: function () {
                        $("#storage-panel-lendPage").panel({
                            href: 'storage/lendAddPage.do?billtype=1',
                            title: '',
                            cache: false,
                            maximized: true,
                            border: false
                        });
                    },
                    url: '/storage/lendAddPage.do?billtype=1'
                },
                </security:authorize>
                <security:authorize url="/storage/out/addlendHold.do">
                {
                    type: 'button-hold',
                    handler: function () {
                        var type = $("#storage-buttons-lendPage").buttonWidget("getOperType");
                        if (type == "add") {
                            //暂存
                            $("#storage-form-lendAdd").attr("action", "storage/addLendHold.do");
                            $("#storage-form-lendAdd").submit();
                        } else if (type == "edit") {
                            //暂存
                            $("#storage-form-lendAdd").attr("action", "storage/editLendHold.do");
                            $("#storage-form-lendAdd").submit();
                        }
                    },
                    url: '/storage/addlendHold.do'
                },
                </security:authorize>
                <security:authorize url="/storage/out/addlendSave.do">
                {
                    type: 'button-save',
                    handler: function () {
                        var type = $("#storage-buttons-lendPage").buttonWidget("getOperType");
                        if (type == "add") {
                            //暂存
                            $("#storage-form-lendAdd").attr("action", "storage/addLendSave.do?billtype=1");
                            $("#storage-form-lendAdd").submit();
                        } else if (type == "edit") {
                            $("#storage-form-lendAdd").attr("action", "storage/editLendSave.do??billtype=1");
                            $("#storage-form-lendAdd").submit();
                        }
                    },
                    url: '/storage/addlendSave.do?billtype=1'
                },
                </security:authorize>
                <security:authorize url="/storage/out/addlendSaveAudit.do">
                {
                    type: 'button-saveaudit',
                    handler: function () {
                        $.messager.confirm("提醒", "确定保存并审核该借货单信息？", function (r) {
                            if (r) {
                                var type = $("#storage-buttons-lendPage").buttonWidget("getOperType");
                                $("#storage-purchaseEnter-saveaudit").val("saveaudit");
                                if (type == "add") {
                                    //暂存
                                    $("#storage-form-lendAdd").attr("action", "storage/addLendSave.do?billtype=1");
                                    $("#storage-form-lendAdd").submit();
                                } else if (type == "edit") {
                                    $("#storage-form-lendAdd").attr("action", "storage/editLendSave.do?billtype=1");
                                    $("#storage-form-lendAdd").submit();
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/out/lendGiveUp.do">
                {
                    type: 'button-giveup',
                    handler: function () {
                        var type = $("#storage-buttons-lendPage").buttonWidget("getOperType");
                        if (type == "add") {
                            var currTitle = top.$('#tt').tabs('getSelected').panel('options').title;
                            top.closeTab(currTitle);
                        }
                        else if (type == "edit") {
                            var id = $("#storage-hidden-billid").val();
                            if (id == "") {
                                return false;
                            }
                            $("#storage-panel-lendPage").panel({
                                href: 'storage/lendViewPage.do?billtype=1&id=' + id,
                                title: '',
                                cache: false,
                                maximized: true,
                                border: false
                            });
                        }
                    },
                    url: '/storage/lendGiveUp.do'
                },
                </security:authorize>
                <security:authorize url="/storage/out/deleteLend.do">
                {
                    type: 'button-delete',
                    handler: function () {
                        $.messager.confirm("提醒", "是否删除当前借货单？", function (r) {
                            if (r) {
                                var id = $("#storage-hidden-billid").val();
                                if (id != "") {
                                    loading("删除中..");
                                    $.ajax({
                                        url: 'storage/deleteLend.do?id=' + id,
                                        type: 'post',
                                        dataType: 'json',
                                        success: function (json) {
                                            loaded();
                                            if (json.flag) {
                                                var object = $("#storage-buttons-lendPage").buttonWidget("removeData", id);
                                                $.messager.alert("提醒", "删除成功");
                                                if (null != object) {
                                                    $("#storage-panel-lendPage").panel({
                                                        href: 'storage/lendEditPage.do?billtype=1&id=' + object.id,
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
                    url: '/storage/deleteLend.do'
                },
                </security:authorize>
                <security:authorize url="/storage/out/auditLend.do">
                {
                    type: 'button-audit',
                    handler: function () {
                        $.messager.confirm("提醒", "是否审核借货单？", function (r) {
                            if (r) {
                                var id = $("#storage-hidden-billid").val();
                                if (id != "") {
                                    loading("审核中..");
                                    $.ajax({
                                        url: 'storage/auditLend.do?id=' + id,
                                        type: 'post',
                                        dataType: 'json',
                                        success: function (json) {
                                            loaded();
                                            console.info(json);
                                            if (json.flag) {
                                                $.messager.alert("提醒", json.msg);
                                                $("#storage-lend-status-select").val("4");
                                                $("#storage-buttons-lendPage").buttonWidget("setDataID", {
                                                    id: id,
                                                    state: '4',
                                                    type: 'view'
                                                });
                                                $("#storage-panel-lendPage").panel({
                                                    href: 'storage/lendEditPage.do?billtype=1&id=' + id,
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
                    url: '/storage/auditLend.do'
                },
                </security:authorize>
                <security:authorize url="/storage/out/oppauditLend.do">
                {
                    type: 'button-oppaudit',
                    handler: function () {
                        var businessdate = $("#storage-lend-businessdate").val();
                        var flag = isDoneOppauditBillCaseAccounting(businessdate);
                        if (!flag) {
                            $.messager.alert("提醒", "业务日期不在会计区间内或未设置会计区间,不允许反审!");
                            return false;
                        }
                        $.messager.confirm("提醒", "是否反审借货单？", function (r) {
                            if (r) {
                                var id = $("#storage-hidden-billid").val();
                                if (id != "") {
                                    loading("审核中..");
                                    $.ajax({
                                        url: 'storage/oppauditLend.do?billtype=1&id=' + id,
                                        type: 'post',
                                        dataType: 'json',
                                        success: function (json) {
                                            loaded();
                                            if (json.flag) {
                                                $.messager.alert("提醒", "反审成功");
                                                $("#storage-panel-lendPage").panel({
                                                    href: 'storage/lendEditPage.do?billtype=1&id=' + id,
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
                                            $.messager.alert("错误", "反审出错");
                                        }
                                    });
                                }
                            }
                        });
                    },
                    url: '/storage/auditLend.do'
                },
                </security:authorize>
                <security:authorize url="/storage/out/lendBackViewPage.do">
                {
                    type: 'button-back',
                    handler: function (data) {
                        $("#storage-panel-lendPage").panel({
                            href: 'storage/lendEditPage.do?billtype=1&id=' + data.id,
                            title: '',
                            cache: false,
                            maximized: true,
                            border: false
                        });
                    },
                    url: '/storage/lendBackViewPage.do'
                },
                </security:authorize>
                <security:authorize url="/storage/out/lendNextViewPage.do">
                {
                    type: 'button-next',
                    handler: function (data) {
                        $("#storage-panel-lendPage").panel({
                            href: 'storage/lendEditPage.do?billtype=1&id=' + data.id,
                            title: '',
                            cache: false,
                            maximized: true,
                            border: false
                        });
                    },
                    url: '/storage/lendNextViewPage.do'
                },
                </security:authorize>
                {}
            ],
            buttons: [
                <security:authorize url="/storage/out/lendPrintViewBtn.do">
                {
                    id: 'button-printview-order',
                    name: '打印预览',
                    iconCls: 'button-preview',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/out/lendPrintBtn.do">
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
            layoutid: 'storage-layoutid-lendPage',
            model: 'bill',
            type: 'view',
            tab: '借货单列表',
            taburl: '/storage/showLendListPage.do?billtype=1',
            id: '${id}',
            datagrid: 'storage-datagrid-lendPage'
        });
    });
    //显示借货单明细添加页面
    function beginAddDetail() {
        //验证表单
        var flag = $("#storage-form-lendAdd").form('validate');
        if (flag == false) {
            return false;
        }
        var storageid = $("#storage-lend-storageid").widget("getValue");
        var customerid = $("#storage-lend-customerid").widget("getValue");
        $('<div id="storage-dialog-lendAddPage-content"></div>').appendTo('#storage-dialog-lendAddPage');
        $('#storage-dialog-lendAddPage-content').dialog({
            title: '借货单明细添加',
            width: 680,
            height: 450,
            collapsible: false,
            minimizable: false,
            maximizable: true,
            resizable: true,
            closed: true,
            cache: false,
            modal: true,
            href: 'storage/showLendDetailAddPage.do?billtype=1',
            onClose: function () {
                $('#storage-dialog-lendAddPage-content').dialog("destroy");
            },
            onLoad: function () {
                $("#storage-lend-goodsid").focus();
            }
        });
        $('#storage-dialog-lendAddPage-content').dialog("open");
    }
    //显示借货单明细修改页面
    function beginEditDetail() {
        //验证表单
        var flag = $("#storage-form-lendAdd").form('validate');
        var row = $("#storage-datagrid-lendAddPage").datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        if (row.goodsid == undefined) {
            beginAddDetail();
        } else {
            var storageInfo = $("#storage-lend-storageid").widget('getObject');
            var url = 'storage/showLendDetailEditPage.do?billtype=1&goodsid=' + row.goodsInfo.id;
            $('<div id="storage-dialog-lendAddPage-content"></div>').appendTo('#storage-dialog-lendAddPage');
            $('#storage-dialog-lendAddPage-content').dialog({
                title: '借货单明细修改',
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
                    $('#storage-dialog-lendAddPage-content').dialog("destroy");
                },
                onLoad: function () {
                    var isbatch = $("#storage-lend-isbatch").val();
                    if (isbatch == '1') {
                        var param = null;
                        var storageid = $("#storage-lend-storageid").widget('getValue');
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
                        $("#storage-lend-batchno").widget({
                            referwid: 'RL_T_STORAGE_BATCH_LIST',
                            param: param,
                            width: 150,
                            singleSelect: true,
                            required: reqFlag,
                            onSelect: function (obj) {
                                $("#storage-lend-summarybatchid").val(obj.id);
                                $("#storage-lend-storagelocationname").val(obj.storagelocationname);
                                $("#storage-lend-storagelocationid").val(obj.storagelocationid);
                                $("#storage-lend-produceddate").val(obj.produceddate);
                                $("#storage-lend-deadline").val(obj.deadline);

                                maxnum = obj.usablenum;
                                $("#storage-lend-loadInfo").html("现存量：<font color='green'>" + obj.existingnum + obj.unitname + "</font>&nbsp;可用量：<font color='green'>" + obj.usablenum + obj.unitname + "</font>");
                                computNum();
                                $("#storage-lend-unitnum").focus();
                                $("#storage-lend-unitnum").select();
                            },
                            onClear: function () {
                                $("#storage-lend-loadInfo").html("&nbsp;");
                                $("#storage-lend-summarybatchid").val("");
                                $("#storage-lend-storagelocationname").val("");
                                $("#storage-lend-storagelocationid").val("");
                                $("#storage-lend-produceddate").val("");
                                $("#storage-lend-deadline").val("");
                            }
                        });
                    } else {
                        $("#storage-lend-batchno").addClass("no_input");
                        $("#storage-lend-batchno").attr("readonly", "readonly");
                    }
                    $("#storage-lend-unitnum").focus();
                    $("#storage-lend-unitnum").select();

                    goodsNumControl();

                    formaterNumSubZeroAndDot();

                    $("#storage-form-lendDetailAddPage").form('validate');
                }
            });
            $('#storage-dialog-lendAddPage-content').dialog("open");
        }
    }
    //保存借货单明细
    function addSaveDetail(goFlag) { //添加新数据确定后操作，
        var flag = $("#storage-form-lendDetailAddPage").form('validate');
        if (flag == false) {
            return false;
        }
        var form = $("#storage-form-lendDetailAddPage").serializeJSON();
        var widgetJson = $("#storage-lend-goodsid").goodsWidget('getObject');
        var boxnum = $("#storage-lend-boxnum").val();
        var goodsInfo = {
            id: widgetJson.id, name: widgetJson.name, brandName: widgetJson.brandName,
            model: widgetJson.model, barcode: widgetJson.barcode, boxnum: boxnum
        };
        form.goodsInfo = goodsInfo;
        var rowIndex = 0;
        var rows = $("#storage-datagrid-lendAddPage").datagrid('getRows');
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
            $("#storage-datagrid-lendAddPage").datagrid('appendRow', {}); //如果是最后一行则添加一新行
        }
        $("#storage-datagrid-lendAddPage").datagrid('updateRow', {index: rowIndex, row: form}); //将数据更新到列表中
        if (updateFlag) {
            $.messager.alert("提醒", "添加的明细商品重复，覆盖历史记录！");
        }
        if (goFlag) { //go为true确定并继续添加一条
            //页面重置
            otherEnterformReset();
        }
        else { //否则直接关闭
            $("#storage-dialog-lendAddPage-content").dialog('destroy');
        }
        countTotal();
        $("#storage-lend-storageid").widget("readonly", true);
    }
    //修改保存
    function editSaveDetail(goFlag) {
        var flag = $("#storage-form-lendDetailAddPage").form('validate');
        if (flag == false) {
            return false;
        }
        var form = $("#storage-form-lendDetailAddPage").serializeJSON();
        var row = $("#storage-datagrid-lendAddPage").datagrid('getSelected');
        var rowIndex = $("#storage-datagrid-lendAddPage").datagrid('getRowIndex', row);
        form.goodsInfo = row.goodsInfo;
        $("#storage-datagrid-lendAddPage").datagrid('updateRow', {index: rowIndex, row: form}); //将数据更新到列表中
        if (goFlag) { //go为true确定并继续修改下一条
            var rowSelected = $("#storage-datagrid-lendAddPage").datagrid("getSelected");
            var rowIndex = $("#storage-datagrid-lendAddPage").datagrid("getRowIndex", rowSelected);
            var rows = $("#storage-datagrid-lendAddPage").datagrid("getRows");
            var rownums = 0;
            for (var i = 0; i < rows.length; i++) {
                if (rows[i].goodsid != null) {
                    rownums++;
                }
            }
            if (rowIndex < rownums - 1) {
                rowIndex = rowIndex + 1;
                $("#storage-datagrid-lendAddPage").datagrid("selectRow", rowIndex);
                $("#storage-dialog-lendAddPage-content").dialog('destroy');
                beginEditDetail();
            } else {
                $.messager.alert("提醒", "已经到最后一条了！");
                $("#storage-dialog-lendAddPage-content").dialog('destroy');
            }
        }
        else { //否则直接关闭
            $("#storage-dialog-lendAddPage-content").dialog('destroy');
        }
        countTotal();
    }
    //删除明细
    function removeDetail() {
        var row = $("#storage-datagrid-lendAddPage").datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        $.messager.confirm("提醒", "确定删除该商品明细?", function (r) {
            if (r) {
                var rowIndex = $("#storage-datagrid-lendAddPage").datagrid('getRowIndex', row);
                $("#storage-datagrid-lendAddPage").datagrid('deleteRow', rowIndex);
                countTotal();
                var rows = $("#storage-datagrid-lendAddPage").datagrid('getRows');
                var index = -1;
                for (var i = 0; i < rows.length; i++) {
                    if (rows[i].goodsid != undefined) {
                        index = i;
                        break;
                    }
                }
                if (index == -1) {
                    $("#storage-lend-storageid").widget("readonly", false);
                }
            }
        });
    }
    function getGoodsUsenum() {
        var storageid = $("#storage-lend-storageid").widget("getValue");
        var goodsid = $("#storage-lend-goodsid").goodsWidget("getValue");
        //控件借货的最大数量
        $.ajax({
            url: 'storage/getStorageSummarySumByGoodsid.do',
            type: 'post',
            data: {goodsid: goodsid, storageid: storageid},
            dataType: 'json',
            success: function (json) {
                if (json.storageSummary != null) {
                    maxnum = json.storageSummary.usablenum;
                    $("#storage-lend-loadInfo").html("现存量：<font color='green'>" + json.storageSummary.existingnum + json.storageSummary.unitname + "</font>&nbsp;可用量：<font color='green'>" + json.storageSummary.usablenum + json.storageSummary.unitname + "</font>");
                }
            }
        });
    }
    //计算合计
    function countTotal() {
        var rows = $("#storage-datagrid-lendAddPage").datagrid('getRows');
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
        $("#storage-datagrid-lendAddPage").datagrid("reloadFooter", [{
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
            $("#storage-lend-remark").focus();
            $("#storage-dialog-lendAddPage-content").dialog("close");
        });
        $(document).bind('keydown', 'ctrl+enter', function () {
            $("#storage-lend-remark").focus();
            $("#storage-savegoon-lendDetailEditPage").trigger("click");
            $("#storage-savegoon-lendDetailAddPage").trigger("click");
        });
        $(document).bind('keydown', '+', function () {
            $("#storage-lend-remark").focus();
            setTimeout(function () {
                $("#storage-savegoon-lendDetailEditPage").trigger("click");
                $("#storage-savegoon-lendDetailAddPage").trigger("click");
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
            code: "storage_lendout",
            url_preview: "print/storage/lendPrintView.do?billtype=1",
            url_print: "print/storage/lendPrint.do?billtype=1",
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
                var printtimes = $("#storage-lend-printtimes").val() || 0;
                if (printtimes > 0)
                    printParam.printIds = [id];
                return true;
            },
            onPrintSuccess: function (option, printParam) {
                var printtimes = $("#storage-lend-printtimes").val() || 0;
                $("#storage-lend-printtimes").val(printtimes+1);
                var pageInfo=  tabsWindowURL(pageListUrl);
                if(pageInfo!=null){
                    pageInfo.$("#storage-datagrid-lendPage").datagrid('reload');
                }
            }
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
