<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>代配送销售订单操作</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>
<body>
<div id="delivery-layout-deliveryOrderPage" class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false">
        <%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
        <div class="buttonBG" id="delivery-buttons-deliveryOrderPage" style="height:26px;"></div>
    </div>
    <div data-options="region:'center',border:false">
        <div id="delivery-panel-deliveryOrderPage"></div>
    </div>
</div>
<div id="delivery-panel-relation-upper"></div>
<div id="delivery-panel-sourceQueryPage"></div>
<div id="workflow-addidea-dialog-page"></div>
<input type="hidden" id="delivery-hidden-billid"/>
<script type="text/javascript">
    //明细列表
    var tableColJson = $("#delivery-datagrid-deliveryOrderAddPage").createGridColumnLoad({

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
            {field: 'auxunitname', title: '辅单位名称', width: 80, hidden: true,},
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
    var page_url = "delivery/deliveryOrderAddPage.do";
    var page_type = '${type}';
    if (page_type == "edit") {
        page_url = "delivery/deliveryOrderEditPage.do?id=${id}";
    }
    $(function () {
        $("#delivery-panel-deliveryOrderPage").panel({
            href: page_url,
            cache: false,
            maximized: true,
            border: false,
            onLoad: function () {
                if (page_type == "edit") {
                    $("#delivery-deliveryOrder-storageid").focus();
                }
                else {
                    $("#delivery-deliveryOrder-supplierid").focus();
                }
            }
        });
        $("#delivery-buttons-deliveryOrderPage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/delivery/addDeliveryOrderPage.do">
                {
                    type: 'button-add',
                    handler: function () {
                        page_type = "add";
                        $("#delivery-panel-deliveryOrderPage").panel('refresh', 'delivery/deliveryOrderAddPage.do');
                    }
                },
                </security:authorize>
                <security:authorize url="/delivery/addDeliveryOrderSave.do">
                {
                    type: 'button-save',
                    handler: function () {
                        if (checkGoodsDetailEmpty()) {
                            $.messager.alert("提醒", "抱歉，请填写代配送销售订单商品信息");
                            return false;
                        }
                        $.messager.confirm("提醒", "确定保存该代配送销售订单信息？", function (r) {
                            if (r) {
                                $("#delivery-deliveryOrder-saveaudit").val("save");
                                var type = $("#delivery-buttons-deliveryOrderPage").buttonWidget("getOperType");
                                if (type == "add") {
                                    //暂存
                                    $("#delivery-form-deliveryOrderAddPage").attr("action", "delivery/addDeliveryOrderSave.do");
                                    $("#delivery-form-deliveryOrderAddPage").submit();
                                } else if (type == "edit") {
                                    $("#delivery-form-deliveryOrderAddPage").attr("action", "delivery/editDeliveryOrderSave.do");
                                    $("#delivery-form-deliveryOrderAddPage").submit();
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/delivery/addDeliveryOrderSaveAudit.do">
                {
                    type: 'button-saveaudit',
                    handler: function () {
                        if (checkGoodsDetailEmpty()) {
                            $.messager.alert("提醒", "抱歉，请填写代配送销售订单商品信息");
                            return false;
                        }
                        $.messager.confirm("提醒", "确定保存并审核该代配送销售订单信息？", function (r) {
                            if (r) {

                                $("#delivery-deliveryOrder-saveaudit").val("saveaudit");
                                var type = $("#delivery-buttons-deliveryOrderPage").buttonWidget("getOperType");
                                if (type == "add") {
                                    //暂存
                                    $("#delivery-form-deliveryOrderAddPage").attr("action", "delivery/addDeliveryOrderSave.do");
                                    $("#delivery-form-deliveryOrderAddPage").submit();
                                } else if (type == "edit") {
                                    $("#delivery-form-deliveryOrderAddPage").attr("action", "delivery/editDeliveryOrderSave.do");
                                    $("#delivery-form-deliveryOrderAddPage").submit();
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/delivery/deleteDeliveryOrder.do">
                {
                    type: 'button-delete',
                    handler: function () {
                        $.messager.confirm("提醒", "是否删除当前代配送销售订单？", function (r) {
                            if (r) {
                                var id = $("#delivery-deliveryOrder-id").val();
                                if (id != "") {
                                    loading("删除中..");
                                    $.ajax({
                                        url: 'delivery/deleteDeliveryOrder.do?id=' + id,
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
                                                var data = $("#delivery-buttons-deliveryOrderPage").buttonWidget("removeData", '');
                                                if (data != null)
                                                    $("#delivery-panel-deliveryOrderPage").panel('refresh', 'delivery/deliveryOrderEditPage.do?id=' + data.id);
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
                <security:authorize url="/delivery/auditDeliveryOrder.do">
                {
                    type: 'button-audit',
                    handler: function () {
                        $.messager.confirm("提醒", "是否审核代配送销售订单？", function (r) {
                            if (r) {
                                var id = $("#delivery-deliveryOrder-id").val();
                                if (id != "") {
                                    loading("审核中..");
                                    $.ajax({
                                        url: 'delivery/auditDeliveryOrder.do?id=' + id,
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
                                                $("#delivery-deliveryOrder-status-select").val("3");
                                                var d = new Date();
                                                var str = d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate();
                                                $("#delivery-deliveryOrder-businessdate").val(str);
                                                $("#delivery-buttons-deliveryOrderPage").buttonWidget("setDataID", {
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
                <security:authorize url="/delivery/oppauditDeliveryOrder.do">
                {
                    type: 'button-oppaudit',
                    handler: function () {
                        $.messager.confirm("提醒", "是否反审代配送销售订单？", function (r) {
                            if (r) {
                                var id = $("#delivery-deliveryOrder-id").val();
                                if (id != "") {
                                    loading("反审中..");
                                    $.ajax({
                                        url: 'delivery/oppauditDeliveryOrder.do?id=' + id,
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
                                                $("#delivery-panel-deliveryOrderPage").panel('refresh', 'delivery/showDeliveryOrderEditPage.do?id=' + id);
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
                        $("#delivery-panel-deliveryOrderPage").panel('refresh', 'delivery/deliveryOrderEditPage.do?id=' + data.id);
                    }
                },
                {
                    type: 'button-next',
                    handler: function (data) {

                        $("#delivery-panel-deliveryOrderPage").panel('refresh', 'delivery/deliveryOrderEditPage.do?id=' + data.id);
                    }
                },
            ],
            buttons: [
                //打印
                <security:authorize url="/delivery/previewDeliveryAogorderPage.do">
                {
                    id: 'button-printview-order',
                    name: '打印预览',
                    iconCls: 'button-preview',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/delivery/printDeliveryAogorderPage.do">
                {
                    id: 'button-print-order',
                    name: '打印',
                    iconCls: 'button-print',
                    handler: function () {
                    }
                },
                </security:authorize>
            ],
            layoutid: 'delivery-layout-deliveryOrderPage',
            model: 'bill',
            type: 'view',
            tab: '代配送销售订单详情',
            taburl: '/delivery/showDeliveryOrderListPage.do',
            id: '${id}',
            datagrid: 'delivery-table-showDeliveryOrderList'
        });
    });
    //显示客户明细添加页面
    function beginAddDetail() {
        //验证表单
        var flag = $("#delivery-form-deliveryOrderAddPage").form('validate');
        if (flag == false) {
            $.messager.alert("提醒", '请先完善代配送销售订单基本信息');
            return false;
        }
        var storageid = $("#delivery-deliveryOrder-storageid").widget("getValue");
        var customerid = $("#delivery-deliveryOrder-customerid").customerWidget("getValue");

        $('<div id="delivery-dialog-deliveryOrderAddPage-content"></div>').appendTo('#delivery-dialog-deliveryOrderAddPage');
        $('#delivery-dialog-deliveryOrderAddPage-content').dialog({
            title: '代配送销售订单明细添加',
            width: 680,
            height: 320,
            collapsible: false,
            minimizable: false,
            maximizable: true,
            resizable: true,
            closed: true,
            cache: false,
            modal: true,
            href: 'delivery/showDeliveryOrderDetailAddPage.do?storageid=' + storageid + "&customerid=" + customerid,
            onLoad: function () {
                $("#delivery-deliveryOrder-goodsid").focus();
            },
            onClose: function () {
                $('#delivery-dialog-deliveryOrderAddPage-content').dialog("destroy");
            }
        });
        $('#delivery-dialog-deliveryOrderAddPage-content').dialog("open");
    }
    //显示代配送销售订单明细修改页面
    function beginEditDetail() {
        var row = $("#delivery-datagrid-deliveryOrderAddPage").datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        if (row.goodsid == undefined) {
            beginAddDetail();
        } else {
            $('<div id="delivery-dialog-deliveryOrderAddPage-content"></div>').appendTo('#delivery-dialog-deliveryOrderAddPage');
            $('#delivery-dialog-deliveryOrderAddPage-content').dialog({
                title: '代配送销售订单明细修改',
                width: 680,
                height: 320,
                collapsible: false,
                minimizable: false,
                maximizable: true,
                resizable: true,
                closed: true,
                cache: false,
                href: 'delivery/showDeliveryOrderDetailEditPage.do',
                modal: true,
                onLoad: function () {
                    $("#delivery-deliveryOrder-unitnum").focus();
                    $("#delivery-deliveryOrder-unitnum").select();
                },
                onClose: function () {
                    $('#delivery-dialog-deliveryOrderAddPage-content').dialog("destroy");
                }
            });
            $('#delivery-dialog-deliveryOrderAddPage-content').dialog("open");
        }
    }
    //保存代配送销售订单明细
    function addSaveDetail(goFlag) { //添加新数据确定后操作，
        var flag = $("#delivery-form-deliveryOrderDetailAddPage").form('validate');
        if (flag == false) {
            return false;
        }
        var form = $("#delivery-form-deliveryOrderDetailAddPage").serializeJSON();
        var widgetJson = $("#delivery-deliveryOrder-goodsid").storageGoodsWidget('getObject');
        //var id=$("#delivery-deliveryOrder-id").widget("getValue");
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
        var rows = $("#delivery-datagrid-deliveryOrderAddPage").datagrid('getRows');
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
            $("#delivery-deliveryOrder-outstorageid").widget('readonly1', true);
        }
        if (rowIndex == rows.length - 1) {
            $("#delivery-datagrid-deliveryOrderAddPage").datagrid('appendRow', {}); //如果是最后一行则添加一新行
        }
// 		if(updateFlag){
// 			$.messager.alert("提醒", "此商品已经添加！");
// 			return false;
// 		}else{
        $("#delivery-datagrid-deliveryOrderAddPage").datagrid('updateRow', {index: rowIndex, row: form}); //将数据更新到列表中
// 		}
        if (goFlag) { //go为true确定并继续添加一条
            var supplierid = $("#delivery-deliveryOrder-supplierid").widget("getValue");
            var url = 'delivery/showDeliveryOrderDetailAddPage.do?supplierid=' + supplierid;
            $("#delivery-dialog-deliveryOrderAddPage-content").dialog('refresh', url);
        }
        else { //否则直接关闭
            $("#delivery-dialog-deliveryOrderAddPage-content").dialog('destroy');
        }
        countTotal();
        disableChoiceWidget();//判断是否锁定供应商
    }
    //修改保存代配送销售订单明细
    function editSaveDetail(goFlag) {
        var flag = $("#delivery-form-deliveryOrderDetailAddPage").form('validate');
        if (flag == false) {
            return false;
        }
        var form = $("#delivery-form-deliveryOrderDetailAddPage").serializeJSON();
        var row = $("#delivery-datagrid-deliveryOrderAddPage").datagrid('getSelected');
        var rowIndex = $("#delivery-datagrid-deliveryOrderAddPage").datagrid('getRowIndex', row);
        form.goodsInfo = row.goodsInfo;
        $("#delivery-datagrid-deliveryOrderAddPage").datagrid('updateRow', {index: rowIndex, row: form}); //将数据更新到列表中
        $("#delivery-dialog-deliveryOrderAddPage-content").dialog('destroy');
        countTotal();
        disableChoiceWidget();//判断是否锁定供应商
    }
    //删除代配送销售订单明细
    function removeDetail() {
        var row = $("#delivery-datagrid-deliveryOrderAddPage").datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        $.messager.confirm("提醒", "确定删除该商品明细?", function (r) {
            if (r) {
                var rowIndex = $("#delivery-datagrid-deliveryOrderAddPage").datagrid('getRowIndex', row);
                $("#delivery-datagrid-deliveryOrderAddPage").datagrid('deleteRow', rowIndex);
                var rows = $("#delivery-datagrid-deliveryOrderAddPage").datagrid('getRows');
                var index = -1;
                for (var i = 0; i < rows.length; i++) {
                    if (rows[i].goodsid != undefined) {
                        index = i;
                        break;
                    }
                }
                if (index == -1) {
                    $("#delivery-deliveryOrder-supplierid").widget('readonly', false);
                }
            }
        });
        countTotal();
        disableChoiceWidget();//判断是否锁定供应商
    }
    function disableChoiceWidget() {
        var rows = $("#delivery-datagrid-deliveryOrderAddPage").datagrid('getRows');
        var count = 0;
        for (var i = 0; i < rows.length; i++) {
            if (rows[i].goodsid && rows[i].goodsid != "") {
                count++;
            }
        }
        if (count > 0) {
            $("#delivery-deliveryOrder-supplierid").supplierWidget("readonly", true);

        } else {
            $("#delivery-deliveryOrder-supplierid").supplierWidget("readonly", false);
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
                if (chooseNo == "deliveryOrder.remark") {

                    beginAddDetail();
                }
                break;
            case 27: //Esc
                $("#delivery-deliveryOrder-remark").focus();
                $("#delivery-dialog-deliveryOrderAddPage-content").dialog('close');
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
        $("#delivery-savegoon-deliveryOrderDetailAddPage").focus();
        $("#delivery-savegoon-deliveryOrderDetailAddPage").trigger("click");
        $("#delivery-savegoon-deliveryOrderDetailEditPage").focus();
        $("#delivery-savegoon-deliveryOrderDetailEditPage").trigger("click");
    });
    $(document).bind('keydown', '+', function () {
        $("#delivery-savegoon-deliveryOrderDetailAddPage").focus();
        $("#delivery-savegoon-deliveryOrderDetailEditPage").focus();
        setTimeout(function () {
            $("#delivery-savegoon-deliveryOrderDetailAddPage").trigger("click");
            $("#delivery-savegoon-deliveryOrderDetailEditPage").trigger("click");
        }, 300);
        return false;
    });
    function checkGoodsDetailEmpty() {
        var flag = true;
        var $table = $("#delivery-datagrid-deliveryOrderAddPage");
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
        var checkrows = $("#delivery-datagrid-deliveryOrderAddPage").datagrid('getChecked');
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
        var rows = $("#delivery-datagrid-deliveryOrderAddPage").datagrid('getRows');
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
        $("#delivery-datagrid-deliveryOrderAddPage").datagrid('reloadFooter', foot);
    }
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "deliveryorder-dialog-print",
            code: "delivery_order",
            url_preview: "print/delivery/orderPrintView.do",
            url_print: "print/delivery/orderPrint.do",
            btnPreview: "button-printview-order",
            btnPrint: "button-print-order",
            getData: function (tableId, printParam) {
                var id = $("#delivery-deliveryOrder-id").val();
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