<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>代配送采购退单操作</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>
<body>
<div id="delivery-layout-deliveryAogreturnPage" class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false">
        <%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
        <div class="buttonBG" id="delivery-buttons-deliveryAogreturnPage" style="height:26px;"></div>
    </div>
    <div data-options="region:'center',border:false">
        <div id="delivery-panel-deliveryAogreturnPage"></div>
    </div>
</div>
<div id="delivery-panel-relation-upper"></div>
<div id="delivery-panel-sourceQueryPage"></div>
<div id="workflow-addidea-dialog-page"></div>
<input type="hidden" id="delivery-hidden-billid"/>
<script type="text/javascript">
    //明细列表
    var tableColJson = $("#delivery-datagrid-deliveryAogreturnAddPage").createGridColumnLoad({
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
            {field: 'auxunitname', title: '辅单位名称', width: 60, hidden: true,},
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
    var page_url = "delivery/deliveryAogreturnAddPage.do";
    var page_type = '${type}';
    if (page_type == "edit") {
        page_url = "delivery/deliveryAogreturnEditPage.do?id=${id}";
    }
    $(function () {
        $("#delivery-panel-deliveryAogreturnPage").panel({
            href: page_url,
            cache: false,
            maximized: true,
            border: false,
            onLoad: function () {
                if (page_type == "edit") {
                    $("#delivery-deliveryAogreturn-storageid").focus();
                }
                else {
                    $("#delivery-deliveryAogreturn-supplierid").focus();
                }
            }
        });
        $("#delivery-buttons-deliveryAogreturnPage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/delivery/addDeliveryAogreturnPage.do">
                {
                    type: 'button-add',
                    handler: function () {
                        page_type = "add";
                        $("#delivery-panel-deliveryAogreturnPage").panel('refresh', 'delivery/deliveryAogreturnAddPage.do');
                    }
                },
                </security:authorize>
                <security:authorize url="/delivery/addDeliveryAogreturnSave.do">
                {
                    type: 'button-save',
                    handler: function () {
                        if (checkGoodsDetailEmpty()) {
                            $.messager.alert("提醒", "抱歉，请填写代配送采购退单商品信息");
                            return false;
                        }
                        $.messager.confirm("提醒", "确定保存该代配送采购退单信息？", function (r) {
                            if (r) {
                                $("#delivery-deliveryAogreturn-saveaudit").val("save");
                                var type = $("#delivery-buttons-deliveryAogreturnPage").buttonWidget("getOperType");
                                if (type == "add") {
                                    //暂存
                                    $("#delivery-form-deliveryAogreturnAddPage").attr("action", "delivery/addDeliveryAogreturnSave.do");
                                    $("#delivery-form-deliveryAogreturnAddPage").submit();
                                } else if (type == "edit") {
                                    $("#delivery-form-deliveryAogreturnAddPage").attr("action", "delivery/editDeliveryAogreturnSave.do");
                                    $("#delivery-form-deliveryAogreturnAddPage").submit();
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/delivery/addDeliveryAogreturnSaveAudit.do">
                {
                    type: 'button-saveaudit',
                    handler: function () {
                        if (checkGoodsDetailEmpty()) {
                            $.messager.alert("提醒", "抱歉，请填写代配送采购退单商品信息");
                            return false;
                        }
                        $.messager.confirm("提醒", "确定保存并审核该代配送采购退单信息？", function (r) {
                            if (r) {
                                $("#delivery-deliveryAogreturn-saveaudit").val("saveaudit");
                                var type = $("#delivery-buttons-deliveryAogreturnPage").buttonWidget("getOperType");
                                if (type == "add") {
                                    //暂存
                                    $("#delivery-form-deliveryAogreturnAddPage").attr("action", "delivery/addDeliveryAogreturnSave.do");
                                    $("#delivery-form-deliveryAogreturnAddPage").submit();
                                } else if (type == "edit") {
                                    $("#delivery-form-deliveryAogreturnAddPage").attr("action", "delivery/editDeliveryAogreturnSave.do");
                                    $("#delivery-form-deliveryAogreturnAddPage").submit();
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/delivery/deleteDeliveryAogreturn.do">
                {
                    type: 'button-delete',
                    handler: function () {
                        $.messager.confirm("提醒", "是否删除当前代配送采购退单？", function (r) {
                            if (r) {
                                var id = $("#delivery-deliveryAogreturn-id").val();
                                if (id != "") {
                                    loading("删除中..");
                                    $.ajax({
                                        url: 'delivery/deleteDeliveryAogreturn.do?id=' + id,
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
                                                var data = $("#delivery-buttons-deliveryAogreturnPage").buttonWidget("removeData", '');
                                                if (data != null)
                                                    $("#delivery-panel-deliveryAogreturnPage").panel('refresh', 'delivery/deliveryAogreturnEditPage.do?id=' + data.id);
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
                <security:authorize url="/delivery/auditDeliveryAogreturn.do">
                {
                    type: 'button-audit',
                    handler: function () {
                        $.messager.confirm("提醒", "是否审核代配送采购退单？", function (r) {
                            if (r) {
                                var id = $("#delivery-deliveryAogreturn-id").val();
                                if (id != "") {
                                    loading("审核中..");
                                    $.ajax({
                                        url: 'delivery/auditDeliveryAogreturn.do?id=' + id,
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
                                                $("#delivery-deliveryAogreturn-status-select").val("3");
                                                $("#delivery-buttons-deliveryAogreturnPage").buttonWidget("setDataID", {
                                                    id: id,
                                                    state: '3',
                                                    type: 'view'
                                                });
                                            } else {
                                                $.messager.alert("提醒", "审核失败" + json.msg);
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
                <security:authorize url="/delivery/oppauditDeliveryAogreturn.do">
                {
                    type: 'button-oppaudit',
                    handler: function () {
                        $.messager.confirm("提醒", "是否反审代配送采购退单？", function (r) {
                            if (r) {
                                var id = $("#delivery-deliveryAogreturn-id").val();
                                if (id != "") {
                                    loading("反审中..");
                                    $.ajax({
                                        url: 'delivery/oppauditDeliveryAogreturn.do?id=' + id,
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
                                                $("#delivery-panel-deliveryAogreturnPage").panel('refresh', 'delivery/showDeliveryAogreturnEditPage.do?id=' + id);
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
                        $("#delivery-panel-deliveryAogreturnPage").panel('refresh', 'delivery/deliveryAogreturnEditPage.do?id=' + data.id);
                    }
                },
                {
                    type: 'button-next',
                    handler: function (data) {
                        $("#delivery-panel-deliveryAogreturnPage").panel('refresh', 'delivery/deliveryAogreturnEditPage.do?id=' + data.id);
                    }
                },
            ],
            buttons: [
                //打印
                <security:authorize url="/delivery/previewDeliveryAogorderPage.do">
                {
                    id: 'button-printview-aogreturn',
                    name: '打印预览',
                    iconCls: 'button-preview',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/delivery/printDeliveryAogorderPage.do">
                {
                    id: 'button-print-aogreturn',
                    name: '打印',
                    iconCls: 'button-print',
                    handler: function () {
                    }
                },
                </security:authorize>
            ],
            layoutid: 'delivery-layout-deliveryAogreturnPage',
            model: 'bill',
            type: 'view',
            tab: '代配送采购退单详情',
            taburl: '/delivery/showDeliveryAogreturnListPage.do',
            id: '${id}',
            datagrid: 'delivery-table-showDeliveryAogreturnList'
        });
    });
    //显示代配送采购退单明细添加页面
    function beginAddDetail() {
        //验证表单
        var flag = $("#delivery-form-deliveryAogreturnAddPage").form('validate');
        if (flag == false) {
            $.messager.alert("提醒", '请先完善代配送采购退单基本信息');
            return false;
        }
        var supplierid = $("#delivery-deliveryAogreturn-supplierid").widget("getValue");
        $('<div id="delivery-dialog-deliveryAogreturnAddPage-content"></div>').appendTo('#delivery-dialog-deliveryAogreturnAddPage');
        $('#delivery-dialog-deliveryAogreturnAddPage-content').dialog({
            title: '代配送采购退单明细添加',
            width: 680,
            height: 320,
            collapsible: false,
            minimizable: false,
            maximizable: true,
            resizable: true,
            closed: true,
            cache: false,
            modal: true,
            href: 'delivery/showDeliveryAogreturnDetailAddPage.do?supplierid=' + supplierid,
            onLoad: function () {
                $("#delivery-deliveryAogreturn-goodsid").focus();
            },
            onClose: function () {
                $('#delivery-dialog-deliveryAogreturnAddPage-content').dialog("destroy");
            }
        });
        $('#delivery-dialog-deliveryAogreturnAddPage-content').dialog("open");
    }
    //显示代配送采购退单明细修改页面
    function beginEditDetail() {
        //验证表单
        var flag = $("#delivery-form-deliveryAogreturnAddPage").form('validate');
        if (flag == false) {
            $.messager.alert("提醒", '请先选择供应商');
            $("#delivery-deliveryAogreturn-supplierid").focus();
            return false;
        }
        var row = $("#delivery-datagrid-deliveryAogreturnAddPage").datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        if (row.goodsid == undefined) {
            beginAddDetail();
        } else {
            $('<div id="delivery-dialog-deliveryAogreturnAddPage-content"></div>').appendTo('#delivery-dialog-deliveryAogreturnAddPage');
            $('#delivery-dialog-deliveryAogreturnAddPage-content').dialog({
                title: '代配送采购退单明细修改',
                width: 680,
                height: 320,
                collapsible: false,
                minimizable: false,
                maximizable: true,
                resizable: true,
                closed: true,
                cache: false,
                href: 'delivery/showDeliveryAogreturnDetailEditPage.do',
                modal: true,
                onLoad: function () {
                    $("#delivery-deliveryAogreturn-unitnum-aux").focus();
                    $("#delivery-deliveryAogreturn-unitnum-aux").select();
                },
                onClose: function () {
                    $('#delivery-dialog-deliveryAogreturnAddPage-content').dialog("destroy");
                }
            });
            $('#delivery-dialog-deliveryAogreturnAddPage-content').dialog("open");
        }
    }
    //保存代配送采购退单明细
    function addSaveDetail(goFlag) { //添加新数据确定后操作，
        var flag = $("#delivery-form-deliveryAogreturnDetailAddPage").form('validate');
        if (flag == false) {
            return false;
        }
        var form = $("#delivery-form-deliveryAogreturnDetailAddPage").serializeJSON();
        var widgetJson = $("#delivery-deliveryAogreturn-goodsid").storageGoodsWidget('getObject');
        //var id=$("#delivery-deliveryAogreturn-id").widget("getValue");
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
        var rows = $("#delivery-datagrid-deliveryAogreturnAddPage").datagrid('getRows');
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
            $("#delivery-deliveryAogreturn-outstorageid").widget('readonly1', true);
        }
        if (rowIndex == rows.length - 1) {
            $("#delivery-datagrid-deliveryAogreturnAddPage").datagrid('appendRow', {}); //如果是最后一行则添加一新行
        }
// 		if(updateFlag){
// 			$.messager.alert("提醒", "此商品已经添加！");
// 			return false;
// 		}else{
        $("#delivery-datagrid-deliveryAogreturnAddPage").datagrid('updateRow', {index: rowIndex, row: form}); //将数据更新到列表中
// 		}
        if (goFlag) { //go为true确定并继续添加一条
            var supplierid = $("#delivery-deliveryAogreturn-supplierid").widget("getValue");
            var url = 'delivery/showDeliveryAogreturnDetailAddPage.do?supplierid=' + supplierid;
            $("#delivery-dialog-deliveryAogreturnAddPage-content").dialog('refresh', url);
        }
        else { //否则直接关闭
            $("#delivery-dialog-deliveryAogreturnAddPage-content").dialog('destroy');
        }
        countTotal();
        disableChoiceWidget();//判断是否锁定供应商
    }
    //修改保存代配送采购退单明细
    function editSaveDetail(goFlag) {
        var flag = $("#delivery-form-deliveryAogreturnDetailAddPage").form('validate');
        if (flag == false) {
            return false;
        }
        var form = $("#delivery-form-deliveryAogreturnDetailAddPage").serializeJSON();
        var row = $("#delivery-datagrid-deliveryAogreturnAddPage").datagrid('getSelected');
        var rowIndex = $("#delivery-datagrid-deliveryAogreturnAddPage").datagrid('getRowIndex', row);
        form.goodsInfo = row.goodsInfo;
        $("#delivery-datagrid-deliveryAogreturnAddPage").datagrid('updateRow', {index: rowIndex, row: form}); //将数据更新到列表中
        $("#delivery-dialog-deliveryAogreturnAddPage-content").dialog('destroy');
        countTotal();
        disableChoiceWidget();//判断是否锁定供应商
    }
    //删除代配送采购退单明细
    function removeDetail() {
        var row = $("#delivery-datagrid-deliveryAogreturnAddPage").datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        $.messager.confirm("提醒", "确定删除该商品明细?", function (r) {
            if (r) {
                var rowIndex = $("#delivery-datagrid-deliveryAogreturnAddPage").datagrid('getRowIndex', row);
                $("#delivery-datagrid-deliveryAogreturnAddPage").datagrid('deleteRow', rowIndex);
                var rows = $("#delivery-datagrid-deliveryAogreturnAddPage").datagrid('getRows');
                var index = -1;
                for (var i = 0; i < rows.length; i++) {
                    if (rows[i].goodsid != undefined) {
                        index = i;
                        break;
                    }
                }
                if (index == -1) {
                    $("#delivery-deliveryAogreturn-supplierid").widget('readonly', false);
                }
            }
        });
        countTotal();
        disableChoiceWidget();//判断是否锁定供应商
    }
    function disableChoiceWidget() {
        var rows = $("#delivery-datagrid-deliveryAogreturnAddPage").datagrid('getRows');
        var count = 0;
        for (var i = 0; i < rows.length; i++) {
            if (rows[i].goodsid && rows[i].goodsid != "") {
                count++;
            }
        }
        if (count > 0) {
            $("#delivery-deliveryAogreturn-supplierid").supplierWidget("readonly", true);
        } else {
            $("#delivery-deliveryAogreturn-supplierid").supplierWidget("readonly", false);
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
                if (chooseNo == "deliveryAogreturn.remark") {
                    beginAddDetail();
                }
                break;
            case 27: //Esc
                $("#delivery-deliveryAogreturn-remark").focus();
                $("#delivery-dialog-deliveryAogreturnAddPage-content").dialog('close');
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
        $("#delivery-savegoon-deliveryAogreturnDetailAddPage").focus();
        $("#delivery-savegoon-deliveryAogreturnDetailAddPage").trigger("click");
        $("#delivery-savegoon-deliveryAogreturnDetailEditPage").focus();
        $("#delivery-savegoon-deliveryAogreturnDetailEditPage").trigger("click");
    });
    $(document).bind('keydown', '+', function () {
        $("#delivery-savegoon-deliveryAogreturnDetailAddPage").focus();
        $("#delivery-savegoon-deliveryAogreturnDetailEditPage").focus();
        setTimeout(function () {
            $("#delivery-savegoon-deliveryAogreturnDetailAddPage").trigger("click");
            $("#delivery-savegoon-deliveryAogreturnDetailEditPage").trigger("click");
        }, 300);
        return false;
    });
    function checkGoodsDetailEmpty() {
        var flag = true;
        var $table = $("#delivery-datagrid-deliveryAogreturnAddPage");
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
        var checkrows = $("#delivery-datagrid-deliveryAogreturnAddPage").datagrid('getChecked');
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
        var rows = $("#delivery-datagrid-deliveryAogreturnAddPage").datagrid('getRows');
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
        $("#delivery-datagrid-deliveryAogreturnAddPage").datagrid('reloadFooter', foot);
    }
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "delivery_aogreturn-dialog-print",
            code: "delivery_aogreturn",
            url_preview: "print/delivery/aogreturnPrintView.do",
            url_print: "print/delivery/aogreturnPrint.do",
            btnPreview: "button-printview-aogreturn",
            btnPrint: "button-print-aogreturn",
            getData: function (tableId, printParam) {
                var id = $("#delivery-deliveryAogreturn-id").val();
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