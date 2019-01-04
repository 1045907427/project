<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>调账单操作页面</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>
<body>
<div id="storage-layout-adjustmentsPage" class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="storage-buttons-adjustmentsPage" style="height:26px;"></div>
    </div>
    <div data-options="region:'center',border:false">
        <div id="storage-panel-adjustmentsPage"></div>
    </div>
</div>
<input type="hidden" id="storage-hidden-billid"/>
<div id="storage-panel-relation-upper"></div>
<div id="storage-panel-sourceQueryPage"></div>
<div id="workflow-addidea-dialog-page"></div>
<script type="text/javascript">
    //根据初始的列与用户保存的列生成以及字段权限生成新的列
    var tableColJson = $("#storage-datagrid-checkListAddPage").createGridColumnLoad({
        name: 'storage_adjustments_detail',
        frozenCol: [[]],
        commonCol: [[
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
                field: 'brandid', title: '商品品牌', width: 80, aliascol: 'goodsid', hidden: true,
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
                field: 'usablenum', title: '可用量', width: 80, isShow: true, align: 'right',
                formatter: function (value, row, index) {

                    if ((row.goodsid || '') != '') {
                        return formatterBigNumNoLen(value || '0');
                    }
                }
            },
            {
                field: 'existingnum', title: '现存量', width: 80, isShow: true, align: 'right',
                formatter: function (value, row, index) {

                    if ((row.goodsid || '') != '') {
                        return formatterBigNumNoLen(value || '0');
                    }
                }
            },
            {
                field: 'adjustnum', title: '数量', width: 60, align: 'right',
                formatter: function (value, row, index) {
                    return formatterBigNumNoLen(value);
                },
                styler: function (value, row, index) {
                    if ((row.goodsid || '') != '') {
                        var status = $("#storage-adjustments-status").val();
                        if (status == '2' && parseInt(formatterBigNum(value || '0')) > parseInt(formatterBigNum(row.usablenum || '0'))) {
                            return 'background-color:#f00;';
                        }
                    }
                }
            },
            {
                field: 'price', title: '单价', width: 60, align: 'right',
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'amount', title: '金额', width: 60, align: 'right',
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
            {field: 'auxadjustnumdetail', title: '辅数量', width: 80, align: 'right'},
            {field: 'storagelocationname', title: '所属库位', width: 100, aliascol: 'storagelocationid'},
            {field: 'batchno', title: '批次号', width: 80, hidden: true},
            {field: 'produceddate', title: '生产日期', width: 80, hidden: true},
            {field: 'deadline', title: '有效截止日期', width: 80, hidden: true},
            {field: 'remark', title: '备注', width: 100}
        ]]
    });
    var page_url = "storage/adjustmentsAddPage.do?billtype=2";
    var page_type = '${type}';
    if (page_type == "view" || page_type == "handle") {
        page_url = "storage/adjustmentsViewPage.do?id=${id}";
    } else if (page_type == "edit") {
        page_url = "storage/adjustmentsEditPage.do?id=${id}";
    }

    var pageListUrl = "/storage/showLossAdjustmentsListPage.do";
    $(function () {
        $("#storage-panel-adjustmentsPage").panel({
            href: page_url,
            cache: false,
            maximized: true,
            border: false
        });
        //按钮
        $("#storage-buttons-adjustmentsPage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/storage/lossAdjustmentsAddPage.do">
                {
                    type: 'button-add',
                    handler: function () {
                        $("#storage-panel-adjustmentsPage").panel({
                            href: 'storage/adjustmentsAddPage.do?billtype=2',
                            title: '',
                            cache: false,
                            maximized: true,
                            border: false
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/addAdjustmentsHold.do">
                {
                    type: 'button-hold',
                    handler: function () {
                        var type = $("#storage-buttons-adjustmentsPage").buttonWidget("getOperType");
                        if (type == "add") {
                            //暂存
                            $("#storage-form-adjustmentsAdd").attr("action", "storage/addAdjustmentsHold.do");
                            $("#storage-form-adjustmentsAdd").submit();
                        } else if (type == "edit") {
                            //暂存
                            $("#storage-form-adjustmentsEdit").attr("action", "storage/editAdjustmentsHold.do");
                            $("#storage-form-adjustmentsEdit").submit();
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/addAdjustmentsSave.do">
                {
                    type: 'button-save',
                    handler: function () {
                        var type = $("#storage-buttons-adjustmentsPage").buttonWidget("getOperType");
                        if (type == "add") {
                            //暂存
                            $("#storage-form-adjustmentsAdd").attr("action", "storage/addAdjustmentsSave.do");
                            $("#storage-form-adjustmentsAdd").submit();
                        } else if (type == "edit") {
                            //暂存
                            $("#storage-form-adjustmentsEdit").attr("action", "storage/editAdjustmentsSave.do");
                            $("#storage-form-adjustmentsEdit").submit();
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/addAdjustmentsSaveAudit.do">
                {
                    type: 'button-saveaudit',
                    handler: function () {
                        $.messager.confirm("提醒", "确定保存并审核该调账单信息？", function (r) {
                            if (r) {
                                var type = $("#storage-buttons-adjustmentsPage").buttonWidget("getOperType");
                                $("#storage-adjustments-saveaudit").val("saveaudit");
                                if (type == "add") {
                                    //暂存
                                    $("#storage-form-adjustmentsAdd").attr("action", "storage/addAdjustmentsSave.do");
                                    $("#storage-form-adjustmentsAdd").submit();
                                } else if (type == "edit") {
                                    //暂存
                                    $("#storage-form-adjustmentsEdit").attr("action", "storage/editAdjustmentsSave.do");
                                    $("#storage-form-adjustmentsEdit").submit();
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/adjustmentsGiveUp.do">
                {
                    type: 'button-giveup',
                    handler: function () {
                        var type = $("#storage-buttons-adjustmentsPage").buttonWidget("getOperType");
                        if (type == "add") {
                            var currTitle = top.$('#tt').tabs('getSelected').panel('options').title;
                            top.closeTab(currTitle);
                        }
                        else if (type == "edit") {
                            var id = $("#storage-hidden-billid").val();
                            if (id == "") {
                                return false;
                            }
                            $("#storage-panel-adjustmentsPage").panel('refresh', 'storage/adjustmentsViewPage.do?id=' + id);
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/deleteAdjustments.do">
                {
                    type: 'button-delete',
                    handler: function () {
                        $.messager.confirm("提醒", "是否删除当前调账单？", function (r) {
                            if (r) {
                                var id = $("#storage-hidden-billid").val();
                                if (id != "") {
                                    loading("删除中..");
                                    $.ajax({
                                        url: 'storage/deleteAdjustments.do?id=' + id,
                                        type: 'post',
                                        dataType: 'json',
                                        success: function (json) {
                                            loaded();
                                            if (json.flag) {
                                                var object = $("#storage-buttons-adjustmentsPage").buttonWidget("removeData", id);
                                                $.messager.alert("提醒", "删除成功");
                                                if (null != object) {
                                                    $("#storage-panel-adjustmentsPage").panel({
                                                        href: 'storage/adjustmentsEditPage.do?id=' + object.id,
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
                                        }
                                    });
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/auditAdjustments.do">
                {
                    type: 'button-audit',
                    handler: function () {
                        $.messager.confirm("提醒", "是否审核调账单？", function (r) {
                            if (r) {
                                var id = $("#storage-hidden-billid").val();
                                if (id != "") {
                                    loading("审核中..");
                                    $.ajax({
                                        url: 'storage/auditAdjustments.do?id=' + id,
                                        type: 'post',
                                        dataType: 'json',
                                        success: function (json) {
                                            loaded();
                                            if (json.flag) {
                                                $.messager.alert("提醒", "审核成功");
                                                $("#storage-adjustments-status").val("4");
                                                $("#storage-buttons-adjustmentsPage").buttonWidget("setDataID", {
                                                    id: id,
                                                    state: '4',
                                                    type: 'view'
                                                });
                                            } else {
                                                $.messager.alert("提醒", "审核失败");
                                            }
                                        },
                                        error: function () {
                                            loaded();
                                            $.messager.alert("错误", "审核失败");
                                        }
                                    });
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/oppauditAdjustments.do">
                {
                    type: 'button-oppaudit',
                    handler: function () {
                        $.messager.confirm("提醒", "是否反审调账单？", function (r) {
                            if (r) {
                                var id = $("#storage-hidden-billid").val();
                                if (id != "") {
                                    loading("审核中..");
                                    $.ajax({
                                        url: 'storage/oppauditAdjustments.do?id=' + id,
                                        type: 'post',
                                        dataType: 'json',
                                        success: function (json) {
                                            loaded();
                                            if (json.flag) {
                                                $.messager.alert("提醒", "反审成功");
                                                $("#storage-panel-adjustmentsPage").panel({
                                                    href: 'storage/adjustmentsEditPage.do?id=' + id,
                                                    title: '',
                                                    cache: false,
                                                    maximized: true,
                                                    border: false
                                                });
                                            } else {
                                                $.messager.alert("提醒", "反审失败。" + json.msg);
                                            }
                                        },
                                        error: function () {
                                            loaded();
                                            $.messager.alert("错误", "审核失败");
                                        }
                                    });
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/adjustmentsViewBackPage.do">
                {
                    type: 'button-back',
                    handler: function (data) {
                        $("#storage-panel-adjustmentsPage").panel({
                            href: 'storage/adjustmentsEditPage.do?id=' + data.id,
                            title: '',
                            cache: false,
                            maximized: true,
                            border: false
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/adjustmentsViewNextPage.do">
                {
                    type: 'button-next',
                    handler: function (data) {
                        $("#storage-panel-adjustmentsPage").panel({
                            href: 'storage/adjustmentsEditPage.do?id=' + data.id,
                            title: '',
                            cache: false,
                            maximized: true,
                            border: false
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/adjustmentsExport.do">
                {
                    type: 'button-export',
                    attr: {
                        queryForm: "#storage-form-id-adjustmentsPage",
                        type: 'exportUserdefined',
                        name: '调账单',
                        url: 'storage/getAdjustmentListExport.do'
                    }
                },
                </security:authorize>
                {}
            ],
            buttons: [
                <security:authorize url="/storage/adjustmentsPrintViewBtn.do">
                {
                    id: 'button-printview-order',
                    name: '打印预览',
                    iconCls: 'button-preview',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/adjustmentsPrintBtn.do">
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
            layoutid: 'storage-layout-adjustmentsPage',
            model: 'bill',
            type: 'view',
            tab: '调账单列表',
            taburl: '/storage/showLossAdjustmentsListPage.do',
            id: '${id}',
            datagrid: 'storage-datagrid-lossAdjustmentsPage'
        });
    });
    //显示调账单明细添加页面
    function beginAddDetail() {
        //验证表单
        var flag = $("#storage-form-adjustmentsAdd").form('validate');
        if (flag == false) {
            $.messager.alert("提醒", '请先选择调账仓库');
            $("#storage-adjustments-storageid").focus();
            return false;
        }
        var storageid = $("#storage-adjustments-storageid").widget("getValue");
        $('<div id="storage-dialog-adjustmentsAddPage-content"></div>').appendTo('#storage-dialog-adjustmentsAddPage');
        $('#storage-dialog-adjustmentsAddPage-content').dialog({
            title: '调账单明细添加',
            width: 680,
            height: 450,
            collapsible: false,
            minimizable: false,
            maximizable: true,
            resizable: true,
            closed: true,
            cache: false,
            modal: true,
            href: 'storage/showAdjustmentsDetailAddPage.do?storageid=' + storageid,
            onLoad: function () {
                $("#storage-adjustments-goodsid").focus();
            },
            onClose: function () {
                $('#storage-dialog-adjustmentsAddPage-content').dialog("destroy");
            }
        });
        $('#storage-dialog-adjustmentsAddPage-content').dialog("open");
    }
    function beginAddOtherDetail() {
        //验证表单
        var flag = $("#storage-form-adjustmentsAdd").form('validate');
        if (flag == false) {
            $.messager.alert("提醒", '请先选择调账仓库');
            $("#storage-adjustments-storageid").focus();
            return false;
        }
        var storageid = $("#storage-adjustments-storageid").widget("getValue");
        $('<div id="storage-dialog-adjustmentsAddPage-content"></div>').appendTo('#storage-dialog-adjustmentsAddPage');
        $('#storage-dialog-adjustmentsAddPage-content').dialog({
            title: '调账单明细添加',
            width: 680,
            height: 400,
            collapsible: false,
            minimizable: false,
            maximizable: true,
            resizable: true,
            closed: true,
            cache: false,
            modal: true,
            href: 'storage/showAdjustmentsDetailOtherAddPage.do?storageid=' + storageid,
            onLoad: function () {
                $("#storage-adjustments-goodsid").focus();
            },
            onClose: function () {
                $('#storage-dialog-adjustmentsAddPage-content').dialog("destroy");
            }
        });
        $('#storage-dialog-adjustmentsAddPage-content').dialog("open");
    }
    //显示盘点单明细修改页面
    function beginEditDetail() {
        //验证表单
        var flag = $("#storage-form-adjustmentsAdd").form('validate');
        if (flag == false) {
            $.messager.alert("提醒", '请先选择调账仓库');
            $("#storage-adjustments-storageid").focus();
            return false;
        }
        var row = $("#storage-datagrid-adjustmentsAddPage").datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        if (row.goodsid == undefined) {
            beginAddDetail();
        } else {
            var storageid = $("#storage-adjustments-storageid").widget("getValue");
            var url = 'storage/showAdjustmentsDetailEditPage.do?goodsid=' + row.goodsid + '&storageid=' + storageid;
            $('<div id="storage-dialog-adjustmentsAddPage-content"></div>').appendTo('#storage-dialog-adjustmentsAddPage');
            $('#storage-dialog-adjustmentsAddPage-content').dialog({
                title: '调账单明细修改',
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
                onLoad: function () {
                    var isbatch = $("#storage-adjustments-hidden-isbatch").val();
                    if (isbatch == '1') {
                        $("#storage-adjustments-produceddate").removeClass("WdateRead");
                        $("#storage-adjustments-produceddate").addClass("Wdate");
                        $("#storage-adjustments-produceddate").removeAttr("readonly");
                        var param = null;
                        var storageid = $("#storage-adjustments-storageid").widget('getValue');
                        if (storageid != null && storageid != "") {
                            param = [{field: 'goodsid', op: 'equal', value: row.goodsid},
                                {field: 'storageid', op: 'equal', value: storageid}];
                        } else {
                            param = [{field: 'goodsid', op: 'equal', value: row.goodsid}];
                        }
                        $("#storage-adjustments-batchno").widget({
                            referwid: 'RL_T_STORAGE_BATCH_LIST',
                            param: param,
                            width: 150,
                            singleSelect: true,
                            required: true,
                            onSelect: function (obj) {
                                $("#storage-adjustments-produceddate").removeClass("Wdate");
                                $("#storage-adjustments-produceddate").addClass("WdateRead");
                                $("#storage-adjustments-produceddate").attr("readonly", "readonly");

                                $("#storage-adjustments-hidden-summarybatchid").val(obj.id);
                                $("#storage-adjustments-produceddate").val(obj.produceddate);
                                $("#storage-adjustments-deadline").val(obj.deadline);
                                $("#storage-adjustments-storagelocationid").val(obj.storagelocationid);
                                $("#storage-adjustments-storagelocationname").val(obj.storagelocationname);
                                $('#storage-adjustments-existingnum').val(obj.existingnum);
                                $('#storage-adjustments-usablenum').val(obj.usablenum);

                                computNum();
                                $("#storage-adjustments-adjustnum").textbox('textbox').focus();
                                $("#storage-adjustments-adjustnum").textbox('textbox').select();
                            },
                            onClear: function () {
                                $("#storage-adjustments-produceddate").removeClass("WdateRead");
                                $("#storage-adjustments-produceddate").addClass("Wdate");
                                $("#storage-adjustments-produceddate").removeAttr("readonly");

                                $("#storage-adjustments-hidden-summarybatchid").val("");
                                $("#storage-adjustments-produceddate").val("");
                                $("#storage-adjustments-deadline").val("");
                                $("#storage-adjustments-storagelocationid").val("");
                                $("#storage-adjustments-storagelocationname").val("");
                                $('#storage-adjustments-existingnum').val(0);
                                $('#storage-adjustments-usablenum').val(0);
                            }
                        });
                    } else {
                        $("#storage-adjustments-produceddate").removeClass("Wdate");
                        $("#storage-adjustments-produceddate").addClass("WdateRead");
                        $("#storage-adjustments-produceddate").attr("readonly", "readonly");
                        $("#storage-adjustments-batchno").addClass("no_input");
                        $("#storage-adjustments-batchno").attr("readonly", "readonly");
                    }
                    $("#storage-adjustments-adjustnum").focus();
                    $("#storage-adjustments-adjustnum").select();

                    formaterNumSubZeroAndDot();

                    $("#storage-form-adjustmentsDetailEditPage").form('validate');
                },
                onClose: function () {
                    $('#storage-dialog-adjustmentsAddPage-content').dialog("destroy");
                }
            });
            $('#storage-dialog-adjustmentsAddPage-content').dialog("open");
        }
    }
    //保存盘点单
    function addSaveDetail(goFlag) { //添加新数据确定后操作，
        var flag = $("#storage-form-adjustmentsDetailAddPage").form('validate');
        if (flag == false) {
            return false;
        }
        var form = $("#storage-form-adjustmentsDetailAddPage").serializeJSON();
        var widgetJson = $("#storage-adjustments-goodsid").storageGoodsWidget('getObject');
        var goodsInfo = null;
        if (null != widgetJson && null != widgetJson.goodsid) {
            goodsInfo = {
                id: widgetJson.goodsid,
                name: widgetJson.goodsname,
                brandName: widgetJson.brandname,
                brand: widgetJson.brand,
                itemno: widgetJson.itemno,
                model: widgetJson.model,
                barcode: widgetJson.barcode,
                boxnum: widgetJson.boxnum
            };
        } else {
            widgetJson = $("#storage-adjustments-goodsid").goodsWidget('getObject');
            goodsInfo = {
                id: widgetJson.id,
                name: widgetJson.name,
                brandName: widgetJson.brandname,
                brand: widgetJson.brand,
                itemno: widgetJson.itemno,
                model: widgetJson.model,
                barcode: widgetJson.barcode,
                boxnum: widgetJson.boxnum
            };
        }
        form.goodsInfo = goodsInfo;
        $.ajax({
            url: 'basefiles/getItemByGoodsAndStorage.do',
            data:{
                goodsid:widgetJson.id,
                storageid:$("#storage-adjustments-storageid").widget('getValue')
            },
            async: false,
            type: 'post',
            dataType: 'json',
            success: function (json) {
                form.goodsInfo.itemno=json.itemno;
            },
        });

        var rowIndex = 0;
        var rows = $("#storage-datagrid-adjustmentsAddPage").datagrid('getRows');
        var updateFlag = false;

        for (var i = 0; i < rows.length; i++) {
            var rowJson = rows[i];
            if (rowJson.summarybatchid != null && form.summarybatchid != "") {
                if (rowJson.goodsid == widgetJson.id && rowJson.summarybatchid == form.summarybatchid) {
                    rowIndex = i;
                    updateFlag = true;
                    break;
                }
            } else {
                if (rowJson.goodsid == widgetJson.id) {
                    rowIndex = i;
                    updateFlag = true;
                    break;
                }
            }
            if (rowJson.goodsid == undefined) {
                rowIndex = i;
                break;
            }
        }


        if (rowIndex == rows.length - 1) {
            $("#storage-datagrid-adjustmentsAddPage").datagrid('appendRow', {}); //如果是最后一行则添加一新行
        }
        if (updateFlag) {
            $.messager.alert("提醒", "此商品已经添加！");
            return false;
        } else {
            $("#storage-datagrid-adjustmentsAddPage").datagrid('updateRow', {index: rowIndex, row: form}); //将数据更新到列表中
        }
        if (goFlag) { //go为true确定并继续添加一条
            var storageid = $("#storage-adjustments-storageid").widget("getValue");
            $("#storage-dialog-adjustmentsAddPage-content").dialog('refresh', 'storage/showAdjustmentsDetailAddPage.do?storageid=' + storageid);
        }
        else { //否则直接关闭
            $("#storage-dialog-adjustmentsAddPage-content").dialog('destroy');
        }
        $("#storage-adjustments-storageid").widget('readonly', true);
        countTotal();

    }
    //修改保存
    function editSaveDetail(goFlag) {
        var flag = $("#storage-form-adjustmentsDetailEditPage").form('validate');
        if (flag == false) {
            return false;
        }
        var form = $("#storage-form-adjustmentsDetailEditPage").serializeJSON();
        var row = $("#storage-datagrid-adjustmentsAddPage").datagrid('getSelected');
        var rowIndex = $("#storage-datagrid-adjustmentsAddPage").datagrid('getRowIndex', row);
        form.goodsInfo = row.goodsInfo;
        $("#storage-datagrid-adjustmentsAddPage").datagrid('updateRow', {index: rowIndex, row: form}); //将数据更新到列表中
        $("#storage-dialog-adjustmentsAddPage-content").dialog('destroy');
        countTotal();
    }
    //删除明细
    function removeDetail() {
        var row = $("#storage-datagrid-adjustmentsAddPage").datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        $.messager.confirm("提醒", "确定删除该商品明细?", function (r) {
            if (r) {
                var rowIndex = $("#storage-datagrid-adjustmentsAddPage").datagrid('getRowIndex', row);
                $("#storage-datagrid-adjustmentsAddPage").datagrid('deleteRow', rowIndex);
                countTotal();
                var rows = $("#storage-datagrid-adjustmentsAddPage").datagrid('getRows');
                var index = -1;
                for (var i = 0; i < rows.length; i++) {
                    if (rows[i].goodsid != undefined) {
                        index = i;
                        break;
                    }
                }
                if (index == -1) {
                    $("#storage-adjustments-storageid").widget('readonly', false);
                }
            }
        });
    }
    //计算合计
    function countTotal() {
        var rows = $("#storage-datagrid-adjustmentsAddPage").datagrid('getRows');
        var leng = rows.length;
        var adjustnum = 0;
        var amount = 0,notaxamount=0,tax=0;
        var usablenum = 0;      // 可用量
        var existingnum = 0;    // 现存量
        for (var i = 0; i < leng; i++) {
            adjustnum += Number(rows[i].adjustnum == undefined ? 0 : rows[i].adjustnum);
            amount += Number(rows[i].amount == undefined ? 0 : rows[i].amount);
            usablenum += Number(rows[i].usablenum || '0');
            existingnum += Number(rows[i].existingnum || '0');
            notaxamount += Number(rows[i].notaxamount == undefined ? 0 : rows[i].notaxamount);
            tax += Number(rows[i].tax == undefined ? 0 : rows[i].tax);
        }
        $("#storage-datagrid-adjustmentsAddPage").datagrid('reloadFooter', [{
            goodsid: '合计',
            adjustnum: adjustnum,
            amount: amount,
            notaxamount:notaxamount,
            tax:tax,
            usablenum: usablenum,
            existingnum: existingnum
        }]);
    }
    $(function () {
        //关闭明细添加页面
        $(document).bind('keydown', 'esc', function () {
            $("#storage-adjustmentsdetial-remark").focus();
            $("#storage-dialog-adjustmentsAddPage-content").dialog("close");
        });
        $(document).bind('keydown', 'ctrl+enter', function () {
            $("#storage-adjustmentsdetial-remark").focus();
            $("#storage-savegoon-adjustmentsDetailAddPage").trigger("click");
            $("#storage-savegoon-adjustmentsDetailEditPage").trigger("click");
        });
        $(document).bind('keydown', '+', function () {
            $("#storage-adjustmentsdetial-remark").focus();
            setTimeout(function () {
                $("#storage-savegoon-adjustmentsDetailAddPage").trigger("click");
                $("#storage-savegoon-adjustmentsDetailEditPage").trigger("click");
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
            id: "adjustmentsPage-dialog-print",
            code: "storage_adjustments",
            url_preview: "print/storage/adjustmentsPrintView.do",
            url_print: "print/storage/adjustmentsPrint.do",
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
                var printtimes = $("#purchase-adjustmentsAddPage-printtimes").val() || 0;
                if (printtimes > 0)
                    printParam.printIds = [id];
                return true;
            },
            printAfterHandler: function (option, printParam) {
                tabsWindowURL(pageListUrl).$("#storage-datagrid-lossAdjustmentsPage").datagrid('reload');
            }
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
