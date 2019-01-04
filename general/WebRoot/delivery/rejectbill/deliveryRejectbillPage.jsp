<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>代配送销售退单操作</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>
<body>
<div id="delivery-layout-deliveryRejectbillPage" class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false">
        <%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
        <div class="buttonBG" id="delivery-buttons-deliveryRejectbillPage" style="height:26px;"></div>
    </div>
    <div data-options="region:'center',border:false">
        <div id="delivery-panel-deliveryRejectbillPage"></div>
    </div>
</div>
<div id="delivery-panel-relation-upper"></div>
<div id="delivery-panel-sourceQueryPage"></div>
<div id="workflow-addidea-dialog-page"></div>
<input type="hidden" id="delivery-hidden-billid"/>
<script type="text/javascript">
    //明细列表
    var tableColJson = $("#delivery-datagrid-deliveryRejectbillAddPage").createGridColumnLoad({
        frozenCol: [[]],
        commonCol: [[
            {field: 'ck', checkbox: true},
            {
                field: 'goodsid', title: '商品编号', width: 80
            },
            {
                field: 'goodsname', title: '商品名称', width: 250, aliascol: 'goodsid',
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        return rowData.goodsInfo.name;
                    } else {
                        return "";
                    }
                }
            },
            {
                field: 'goodssort', title: '商品分类', width: 80, hidden: true
            },
            {
                field: 'brandid', title: '品牌编码', width: 70, hidden: true
            },
            {
                field: 'brandname', title: '商品品牌', width: 70, aliascol: 'goodsid',
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        return rowData.goodsInfo.brandName;
                    } else {
                        return "";
                    }
                }
            },
            {
                field: 'barcode', title: '条形码', width: 100,
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        return rowData.goodsInfo.barcode;
                    } else {
                        return "";
                    }
                }
            },
            {
                field: 'boxnum', title: '箱装量', width: 50,
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        return formatterBigNumNoLen(rowData.goodsInfo.boxnum);
                    } else {
                        return "";
                    }
                }
            },
            {
                field: 'unitid', title: '主计量单位', width: 100, hidden: true
            },
            {
                field: 'unitnum', title: '数量', width: 50, align: 'right',
                formatter: function (value, row, index) {
                    return formatterBigNumNoLen(value);
                }
            },
            {
                field: 'unitname', title: '单位', width: 40, aliascol: 'goodsid',
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        return rowData.goodsInfo.mainunitName;
                    } else {
                        return "";
                    }
                }
            },
            {
                field: 'auxunitid', title: '辅计量单位编号', width: 45, aliascol: 'goodsid', hidden: true,
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        return rowData.goodsInfo.auxunitid;
                    } else {
                        return "";
                    }
                }
            },
            {field: 'auxunitname', title: '辅单位名称', width: 80, hidden: true},
            {
                field: 'auxnum', title: '辅单位数量', width: 80, align: 'right', hidden: true,
                formatter: function (value, row, index) {
                    return formatterBigNum(value);
                }
            },
            {field: 'auxnumdetail', title: '辅数量', width: 60, align: 'right'},
            {field: 'produceddate', title: '生产日期', width: 90, align: 'right'},
            {field: 'batchno', title: '批次号', width: 90, align: 'right'},
            {
                field: 'overnum', title: '主单位余数', width: 100, hidden: true,
                formatter: function (value, row, index) {
                    return formatterBigNumNoLen(value);
                }
            },
            {
                field: 'totalbox', title: '合计箱数', width: 70,
                formatter: function (value, row, index) {
                    return formatterBigNumNoLen(value);
                }
            },
            {
                field: 'price', title: '单价', width: 50,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'taxamount', title: '金额', width: 80,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {field: 'remark', title: '备注', width: 100},
            {field: 'seq', title: '序号', width: 100, hidden: true}
        ]]
    });//明细列表至此
    var page_url = "delivery/deliveryRejectbillAddPage.do";
    var page_type = '${type}';
    if (page_type == "edit") {
        page_url = "delivery/deliveryRejectbillEditPage.do?id=${id}";
    }
    $(function () {
        $("#delivery-panel-deliveryRejectbillPage").panel({
            href: page_url,
            cache: false,
            maximized: true,
            border: false,
            onLoad: function () {
                if (page_type == "edit") {
                    $("#delivery-deliveryRejectbill-storageid").focus();
                }
                else {
                    $("#delivery-deliveryRejectbill-supplierid").focus();
                }
            }
        });
        $("#delivery-buttons-deliveryRejectbillPage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/delivery/addDeliveryRejectbillPage.do">
                {
                    type: 'button-add',
                    handler: function () {
                        page_type = "add";
                        $("#delivery-panel-deliveryRejectbillPage").panel('refresh', 'delivery/deliveryRejectbillAddPage.do');
                    }
                },
                </security:authorize>
                <security:authorize url="/delivery/addDeliveryRejectbillSave.do">
                {
                    type: 'button-save',
                    handler: function () {
                        if (checkGoodsDetailEmpty()) {
                            $.messager.alert("提醒", "抱歉，请填写代配送销售退单商品信息");
                            return false;
                        }
                        $.messager.confirm("提醒", "确定保存该代配送销售退单信息？", function (r) {
                            if (r) {
                                $("#delivery-deliveryRejectbill-saveaudit").val("save");
                                var type = $("#delivery-buttons-deliveryRejectbillPage").buttonWidget("getOperType");
                                if (type == "add") {
                                    //暂存
                                    $("#delivery-form-deliveryRejectbillAddPage").attr("action", "delivery/addDeliveryRejectbillSave.do");
                                    $("#delivery-form-deliveryRejectbillAddPage").submit();
                                } else if (type == "edit") {
                                    $("#delivery-form-deliveryRejectbillAddPage").attr("action", "delivery/editDeliveryRejectbillSave.do");
                                    $("#delivery-form-deliveryRejectbillAddPage").submit();
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/delivery/addDeliveryRejectbillSaveAudit.do">
                {
                    type: 'button-saveaudit',
                    handler: function () {
                        if (checkGoodsDetailEmpty()) {
                            $.messager.alert("提醒", "抱歉，请填写代配送销售退单商品信息");
                            return false;
                        }
                        $.messager.confirm("提醒", "确定保存并审核该代配送销售退单信息？", function (r) {
                            if (r) {
                                $("#delivery-deliveryRejectbill-saveaudit").val("saveaudit");
                                var type = $("#delivery-buttons-deliveryRejectbillPage").buttonWidget("getOperType");
                                if (type == "add") {
                                    //暂存
                                    $("#delivery-form-deliveryRejectbillAddPage").attr("action", "delivery/addDeliveryRejectbillSave.do");
                                    $("#delivery-form-deliveryRejectbillAddPage").submit();
                                } else if (type == "edit") {
                                    $("#delivery-form-deliveryRejectbillAddPage").attr("action", "delivery/editDeliveryRejectbillSave.do");
                                    $("#delivery-form-deliveryRejectbillAddPage").submit();
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/delivery/deleteDeliveryRejectbill.do">
                {
                    type: 'button-delete',
                    handler: function () {
                        $.messager.confirm("提醒", "是否删除当前代配送销售退单？", function (r) {
                            if (r) {
                                var id = $("#delivery-hidden-billid").val();
                                if (id != "") {
                                    loading("删除中..");
                                    $.ajax({
                                        url: 'delivery/deleteDeliveryRejectbill.do?id=' + id,
                                        type: 'post',
                                        dataType: 'json',
                                        success: function (json) {
                                            loaded();
                                            if (true == json.lock) {
                                                $.messager.alert("提醒", "其他用户正在修改该数据");
                                                return false;
                                            }
                                            if (json.flag) {
                                                $.messager.alert("提醒", "删除成功");
                                                var data = $("#delivery-buttons-deliveryRejectbillPage").buttonWidget("removeData", '');
                                                console.log(data)
                                                if (data != null)
                                                    $("#delivery-panel-deliveryRejectbillPage").panel('refresh', 'delivery/deliveryRejectbillEditPage.do?id=' + data.id);
                                                else {
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
                <security:authorize url="/delivery/auditDeliveryRejectbill.do">
                {
                    type: 'button-audit',
                    handler: function () {
                        $.messager.confirm("提醒", "是否审核代配送销售退单？", function (r) {
                            if (r) {
                                var id = $("#delivery-hidden-billid").val();
                                if (id != "") {
                                    loading("审核中..");
                                    $.ajax({
                                        url: 'delivery/auditDeliveryRejectbill.do?id=' + id,
                                        type: 'post',
                                        dataType: 'json',
                                        success: function (json) {
                                            loaded();
                                            if (true == json.lock) {
                                                $.messager.alert("提醒", "其他用户正在修改该数据");
                                                return false;
                                            }
                                            if (json.flag) {
                                                $.messager.alert("提醒", "审核成功");
                                                $("#delivery-deliveryRejectbill-status-select").val("3");
                                                var d = new Date();
                                                var str = d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate();
                                                $("#delivery-deliveryRejectbill-businessdate").val(str);
                                                $("#delivery-buttons-deliveryRejectbillPage").buttonWidget("setDataID", {
                                                    id: id,
                                                    state: '3',
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
                <security:authorize url="/delivery/oppauditDeliveryRejectbill.do">
                {
                    type: 'button-oppaudit',
                    handler: function () {
                        $.messager.confirm("提醒", "是否反审代配送销售退单？", function (r) {
                            if (r) {
                                var id = $("#delivery-hidden-billid").val();
                                if (id != "") {
                                    loading("反审中..");
                                    $.ajax({
                                        url: 'delivery/oppauditDeliveryRejectbill.do?id=' + id,
                                        type: 'post',
                                        dataType: 'json',
                                        success: function (json) {
                                            loaded();
                                            if (true == json.lock) {
                                                $.messager.alert("提醒", "其他用户正在修改该数据");
                                                return false;
                                            }
                                            if (json.flag) {
                                                $.messager.alert("提醒", "反审成功");
                                                $("#delivery-panel-deliveryRejectbillPage").panel('refresh', 'delivery/showDeliveryRejectbillEditPage.do?id=' + id);
                                            } else {
                                                $.messager.alert("提醒", "反审失败");
                                            }
                                        },
                                        error: function () {
                                            loaded();
                                            $.messager.alert("错误", "反审失败");
                                        }
                                    });
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                {
                    type: 'button-back',
                    handler: function (data) {
                        $("#delivery-panel-deliveryRejectbillPage").panel('refresh', 'delivery/deliveryRejectbillEditPage.do?id=' + data.id);
                    }
                },
                {
                    type: 'button-next',
                    handler: function (data) {
                        $("#delivery-panel-deliveryRejectbillPage").panel('refresh', 'delivery/deliveryRejectbillEditPage.do?id=' + data.id);
                    }
                },
            ],
            buttons: [
                //打印
                <security:authorize url="/delivery/previewDeliveryAogorderPage.do">
                {
                    id: 'button-printview-rejectbill',
                    name: '打印预览',
                    iconCls: 'button-preview',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/delivery/printDeliveryAogorderPage.do">
                {
                    id: 'button-print-rejectbill',
                    name: '打印',
                    iconCls: 'button-print',
                    handler: function () {
                    }
                },
                </security:authorize>
            ],
            layoutid: 'delivery-layout-deliveryRejectbillPage',
            model: 'bill',
            type: 'view',
            tab: '代配送销售退单详情',
            taburl: '/delivery/showDeliveryRejectbillListPage.do',
            id: '${id}',
            datagrid: 'delivery-table-showDeliveryRejectbillList'
        });
    });
    //显示代配送销售退单明细添加页面
    function beginAddDetail() {
        //验证表单
        var flag = $("#delivery-form-deliveryRejectbillAddPage").form('validate');
        if (flag == false) {
            $.messager.alert("提醒", '请先完善代配送销售退单基本信息');
            return false;
        }
        var supplierid = $("#delivery-deliveryRejectbill-supplierid").widget("getValue");
        var customerid = $("#delivery-deliveryRejectbill-customerid").customerWidget("getValue");
        $('<div id="delivery-dialog-deliveryRejectbillAddPage-content"></div>').appendTo('#delivery-dialog-deliveryRejectbillAddPage');
        $('#delivery-dialog-deliveryRejectbillAddPage-content').dialog({
            title: '代配送销售退单明细添加',
            width: 680,
            height: 320,
            collapsible: false,
            minimizable: false,
            maximizable: true,
            resizable: true,
            closed: true,
            cache: false,
            modal: true,
            href: 'delivery/showDeliveryRejectbillDetailAddPage.do?supplierid=' + supplierid + "&customerid=" + customerid,
            onLoad: function () {
                $("#delivery-deliveryRejectbill-goodsid").focus();
            },
            onClose: function () {
                $('#delivery-dialog-deliveryRejectbillAddPage-content').dialog("destroy");
            }
        });
        $('#delivery-dialog-deliveryRejectbillAddPage-content').dialog("open");
    }
    //显示代配送销售退单明细修改页面
    function beginEditDetail() {
        //验证表单
        var flag = $("#delivery-form-deliveryRejectbillAddPage").form('validate');
        if (flag == false) {
            $.messager.alert("提醒", '请先选择供应商');
            $("#delivery-deliveryRejectbill-supplierid").focus();
            return false;
        }
        var row = $("#delivery-datagrid-deliveryRejectbillAddPage").datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        if (row.goodsid == undefined) {
            beginAddDetail();
        } else {
            $('<div id="delivery-dialog-deliveryRejectbillAddPage-content"></div>').appendTo('#delivery-dialog-deliveryRejectbillAddPage');
            $('#delivery-dialog-deliveryRejectbillAddPage-content').dialog({
                title: '代配送销售退单明细修改',
                width: 680,
                height: 320,
                collapsible: false,
                minimizable: false,
                maximizable: true,
                resizable: true,
                closed: true,
                cache: false,
                href: 'delivery/showDeliveryRejectbillDetailEditPage.do',
                modal: true,
                onLoad: function () {
                    $("#delivery-deliveryRejectbill-unitnum").focus();
                    $("#delivery-deliveryRejectbill-unitnum").select();
                },
                onClose: function () {
                    $('#delivery-dialog-deliveryRejectbillAddPage-content').dialog("destroy");
                }
            });
            $('#delivery-dialog-deliveryRejectbillAddPage-content').dialog("open");
        }
    }
    //保存代配送销售退单明细
    function addSaveDetail(goFlag) { //添加新数据确定后操作，
        var flag = $("#delivery-form-deliveryRejectbillDetailAddPage").form('validate');
        if (flag == false) {
            return false;
        }
        var form = $("#delivery-form-deliveryRejectbillDetailAddPage").serializeJSON();
        var widgetJson = $("#delivery-deliveryRejectbill-goodsid").storageGoodsWidget('getObject');
        //var id=$("#delivery-deliveryRejectbill-id").widget("getValue");
        var goodsInfo = {
            id: widgetJson.id,
            name: widgetJson.name,
            brandName: widgetJson.brandName,
            mainunitName: widgetJson.mainunitName,
            auxunitid: widgetJson.auxunitid,
            boxnum: widgetJson.boxnum,
            barcode: widgetJson.barcode
        };
        form.goodsInfo = goodsInfo;
        var rowIndex = 0;
        var rows = $("#delivery-datagrid-deliveryRejectbillAddPage").datagrid('getRows');
        var updateFlag = false;
        for (var i = 0; i < rows.length; i++) {
            var rowJson = rows[i];
// 			if(rowJson.goodsid==widgetJson.id){
// 				rowIndex = i;
// 				updateFlag = true;
// 				break;
// 			}
            if (rowJson.goodsid == undefined) {
                rowIndex = i;
                break;
            }
        }
        if (rowIndex == 0) {
            $("#delivery-deliveryRejectbill-outstorageid").widget('readonly1', true);
        }
        if (rowIndex == rows.length - 1) {
            $("#delivery-datagrid-deliveryRejectbillAddPage").datagrid('appendRow', {}); //如果是最后一行则添加一新行
        }
// 		if(updateFlag){
// 			$.messager.alert("提醒", "此商品已经添加！");
// 			return false;
// 		}else{
        $("#delivery-datagrid-deliveryRejectbillAddPage").datagrid('updateRow', {index: rowIndex, row: form}); //将数据更新到列表中
// 		}
        if (goFlag) { //go为true确定并继续添加一条
            var supplierid = $("#delivery-deliveryRejectbill-supplierid").widget("getValue");
            var url = 'delivery/showDeliveryRejectbillDetailAddPage.do?supplierid=' + supplierid;
            $("#delivery-dialog-deliveryRejectbillAddPage-content").dialog('refresh', url);
        }
        else { //否则直接关闭
            $("#delivery-dialog-deliveryRejectbillAddPage-content").dialog('destroy');
        }
        countTotal();
        disableChoiceWidget();//判断是否锁定供应商
    }
    //修改保存代配送销售退单明细
    function editSaveDetail(goFlag) {
        var flag = $("#delivery-form-deliveryRejectbillDetailAddPage").form('validate');
        if (flag == false) {
            return false;
        }
        var form = $("#delivery-form-deliveryRejectbillDetailAddPage").serializeJSON();
        var row = $("#delivery-datagrid-deliveryRejectbillAddPage").datagrid('getSelected');
        var rowIndex = $("#delivery-datagrid-deliveryRejectbillAddPage").datagrid('getRowIndex', row);
        form.goodsInfo = row.goodsInfo;
        $("#delivery-datagrid-deliveryRejectbillAddPage").datagrid('updateRow', {index: rowIndex, row: form}); //将数据更新到列表中
        $("#delivery-dialog-deliveryRejectbillAddPage-content").dialog('destroy');
        countTotal();
        disableChoiceWidget();//判断是否锁定供应商
    }
    //删除代配送销售退单明细
    function removeDetail() {
        var row = $("#delivery-datagrid-deliveryRejectbillAddPage").datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        $.messager.confirm("提醒", "确定删除该商品明细?", function (r) {
            if (r) {
                var rowIndex = $("#delivery-datagrid-deliveryRejectbillAddPage").datagrid('getRowIndex', row);
                $("#delivery-datagrid-deliveryRejectbillAddPage").datagrid('deleteRow', rowIndex);
                var rows = $("#delivery-datagrid-deliveryRejectbillAddPage").datagrid('getRows');
                var index = -1;
                for (var i = 0; i < rows.length; i++) {
                    if (rows[i].goodsid != undefined) {
                        index = i;
                        break;
                    }
                }
                if (index == -1) {
                    $("#delivery-deliveryRejectbill-supplierid").widget('readonly', false);
                }
            }
        });
        countTotal();
        disableChoiceWidget();//判断是否锁定供应商
    }
    function disableChoiceWidget() {
        var rows = $("#delivery-datagrid-deliveryRejectbillAddPage").datagrid('getRows');
        var count = 0;
        for (var i = 0; i < rows.length; i++) {
            if (rows[i].goodsid && rows[i].goodsid != "") {
                count++;
            }
        }
        if (count > 0) {
            $("#delivery-deliveryRejectbill-supplierid").supplierWidget("readonly", true);
        } else {
            $("#delivery-deliveryRejectbill-supplierid").supplierWidget("readonly", false);
        }
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
    $(document).keydown(function (event) {//alert(event.keyCode);
        switch (event.keyCode) {
            case 13: //Enter
                if (chooseNo == "deliveryRejectbill.remark") {
                    beginAddDetail();
                }
                break;
            case 27: //Esc
                $("#delivery-deliveryRejectbill-remark").focus();
                $("#delivery-dialog-deliveryRejectbillAddPage-content").dialog('close');
                break;
            case 65: //a
                $("#button-add").click();
                break;
            case 83: //s
                if (event.ctrlKey) {
                    $("#button-save").click();
                    return false;
                }
                break;
        }
    });
    $(document).bind('keydown', 'ctrl+enter', function () {
        $("#delivery-savegoon-deliveryRejectbillDetailAddPage").focus();
        $("#delivery-savegoon-deliveryRejectbillDetailAddPage").trigger("click");
        $("#delivery-savegoon-deliveryRejectbillDetailEditPage").focus();
        $("#delivery-savegoon-deliveryRejectbillDetailEditPage").trigger("click");
    });
    $(document).bind('keydown', '+', function () {
        $("#delivery-savegoon-deliveryRejectbillDetailAddPage").focus();
        $("#delivery-savegoon-deliveryRejectbillDetailEditPage").focus();
        setTimeout(function () {
            $("#delivery-savegoon-deliveryRejectbillDetailAddPage").trigger("click");
            $("#delivery-savegoon-deliveryRejectbillDetailEditPage").trigger("click");
        }, 300);
        return false;
    });
    function checkGoodsDetailEmpty() {
        var flag = true;
        var $table = $("#delivery-datagrid-deliveryRejectbillAddPage");
        var data = $table.datagrid('getRows');
        for (var i = 0; i < data.length; i++) {
            if (data[i].goodsid && data[i].goodsid != "") {
                flag = false;
                break;
            }
        }
        return flag;
    }
    function countTotal() { //计算合计
        var checkrows = $("#delivery-datagrid-deliveryRejectbillAddPage").datagrid('getChecked');
        var unitnum = 0;//合计数量
        var totalbox = 0;//合计箱数
        var taxamount = 0;//合计金额
        for (var i = 0; i < checkrows.length; i++) {
            unitnum += Number(checkrows[i].unitnum == undefined ? 0 : checkrows[i].unitnum);
            totalbox += Number(checkrows[i].totalbox == undefined ? 0 : checkrows[i].totalbox);
            taxamount += Number(checkrows[i].taxamount == undefined ? 0 : checkrows[i].taxamount);
        }
        totalbox = formatterMoney(totalbox);
        var foot = [{goodsid: '选中合计', unitnum: unitnum, taxamount: taxamount, auxnumdetail: totalbox + "箱"}];
        //合计
        var rows = $("#delivery-datagrid-deliveryRejectbillAddPage").datagrid('getRows');
        var unitnumSum = 0;
        var taxamountSum = 0;
        var totalboxSum = 0;
        for (var i = 0; i < rows.length; i++) {
            unitnumSum += Number(rows[i].unitnum == undefined ? 0 : rows[i].unitnum);
            taxamountSum += Number(rows[i].taxamount == undefined ? 0 : rows[i].taxamount);
            totalboxSum += Number(rows[i].totalbox == undefined ? 0 : rows[i].totalbox);
        }
        totalboxSum = formatterMoney(totalboxSum);
        var footSum = {goodsid: '合计', unitnum: unitnumSum, taxamount: taxamountSum, auxnumdetail: totalboxSum + "箱"};
        foot.push(footSum);
        $("#delivery-datagrid-deliveryRejectbillAddPage").datagrid('reloadFooter', foot);
    }
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "listPage-deliveryrejectbill-dialog-print",
            code: "delivery_rejectbill",
            url_preview: "print/delivery/rejectbillPrintView.do",
            url_print: "print/delivery/rejectbillPrint.do",
            btnPreview: "button-printview-rejectbill",
            btnPrint: "button-print-rejectbill",
            getData: function (tableId, printParam) {
                var id = $("#delivery-deliveryRejectbill-id").val();
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