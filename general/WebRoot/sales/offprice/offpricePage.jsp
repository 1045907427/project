<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>销售特价调整页面</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>
<body>
<input type="hidden" id="sales-backid-offpricePage" value="${id }"/>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="sales-buttons-offpricePage" style="height:26px;"></div>
    </div>
    <div data-options="region:'center',border:false">
        <div id="sales-panel-offpricePage">
        </div>
    </div>
</div>
<%--<div id="sales-dialog-offpriceAddPage"></div>--%>
<script type="text/javascript">
    var ids = "";
    var offprice_url = "sales/offpriceAddPage.do";
    var offprice_type = '${type}';
    if (offprice_type == "view") {
        offprice_url = "sales/offpriceViewPage.do?id=${id}";
    }
    if (offprice_type == "edit") {
        offprice_url = "sales/offpriceEditPage.do?id=${id}";
    }
    function changeCustomerWidget(customertype, customerid, disabled) {
        $("#customertd").empty();
        var tdstr = "", disabledstr = "";
        if (disabled == "1") {
            disabledstr = 'disabled="disabled"';
        }
        if ("1" == customertype) {
            tdstr = '<input type="text" id="sales-customer-offpriceAddPage" name="offprice.customerid" value="' + customerid + '" ' + disabledstr + '/>';
        } else if ("2" == customertype) {
            tdstr = '<input type="text" id="sales-promotionsort-offpriceAddPage" name="offprice.customerid" value="' + customerid + '" ' + disabledstr + '/>';
        } else if ("3" == customertype) {
            tdstr = '<input type="text" id="sales-customersort-offpriceAddPage" name="offprice.customerid" value="' + customerid + '" ' + disabledstr + '/>';
        } else if ("4" == customertype) {
            tdstr = '<input type="text" id="sales-pricelist-offpriceAddPage" name="offprice.customerid" value="' + customerid + '" ' + disabledstr + '/>';
        } else if ("5" == customertype) {
            tdstr = '<input type="text" id="sales-salesarea-offpriceAddPage" name="offprice.customerid" value="' + customerid + '" ' + disabledstr + '/>';
        } else if ("6" == customertype) {
            tdstr = '<input type="text" id="sales-pcustomer-offpriceAddPage" name="offprice.customerid" value="' + customerid + '" ' + disabledstr + '/>';
        } else if ("7" == customertype) {
            tdstr = '<input type="text" id="sales-crenditrating-offpriceAddPage" name="offprice.customerid" value="' + customerid + '" ' + disabledstr + '/>';
        } else if ("8" == customertype) {
            tdstr = '<input type="text" id="sales-canceltype-offpriceAddPage" name="offprice.customerid" value="' + customerid + '" ' + disabledstr + '/>';
        } else if ("0" == customertype) {
            tdstr = '<input type="text" id="sales-canceltype-offpriceAddPage" name="offprice.customerid" class="no_input len150" readonly="readonly"' +
                ' value="' + customerid + '" ' + disabledstr + '/>';
        }
        $(tdstr).appendTo("#customertd");
        if ("1" == customertype) {
            $("#sales-customer-offpriceAddPage").widget({
                referwid: "RL_T_BASE_SALES_CUSTOMER",
                singleSelect: false,
                required: true,
                isPageReLoad: false,
                width: 150
            });
        } else if ("2" == customertype) {
            $("#sales-promotionsort-offpriceAddPage").widget({
                referwid: "RL_T_SYS_CODE_PROMOTIONSORT",
                singleSelect: false,
                required: false,
                width: 150
            });
        } else if ("3" == customertype) {
            $("#sales-customersort-offpriceAddPage").widget({
                referwid: "RT_T_BASE_SALES_CUSTOMERSORT",
                singleSelect: false,
                required:true,
                width: 150,
                treePName:false,
                onlyLeafCheck: false
            });
        } else if ("4" == customertype) {
            $("#sales-pricelist-offpriceAddPage").widget({
                referwid: "RL_T_SYS_CODE_PRICELIST",
                singleSelect: false,
                required: false,
                width: 150
            });
        } else if ("5" == customertype) {
            $("#sales-salesarea-offpriceAddPage").widget({
                referwid: "RT_T_BASE_SALES_AREA",
                singleSelect: false,
                onlyLeafCheck: false,
                treePName:false,
                required: true,
                width: 150
            });
        } else if ("6" == customertype) {
            $("#sales-pcustomer-offpriceAddPage").widget({
                referwid: "RL_T_BASE_SALES_CUSTOMER_PARENT",
                singleSelect: false,
                required: true,
                width: 150
            });
        } else if ("7" == customertype) {
            $("#sales-crenditrating-offpriceAddPage").widget({
                referwid: "RL_T_SYS_CODE_CREDITRATING",
                singleSelect: false,
                required: true,
                width: 150
            });
        } else if ("8" == customertype) {
            $("#sales-canceltype-offpriceAddPage").widget({
                referwid: "RL_T_SYS_CODE_CANCELTYPE",
                singleSelect: false,
                required: true,
                width: 150
            });
        }
    }

    var wareListJson = $("#sales-datagrid-offpriceAddPage").createGridColumnLoad({ //以下为商品明细datagrid字段
        //name:'t_sales_offprice_detail',
        frozenCol: [[
            {field: 'id', hidden: true}
        ]],
        commonCol: [[{
            field: 'goodsid', title: '商品编码', width: 60, align: ' left',
            formatter: function (value, row, index) {
                if (row.goodsInfo != undefined) {
                    return row.goodsInfo.id;
                }
                else {
                    return "";
                }
            }
        },
            {
                field: 'goodsname', title: '商品名称', width: 250, align: 'left', aliascol: 'goodsid',
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        return rowData.goodsInfo.name;
                    } else {
                        return "";
                    }
                }
            },
            {
                field: 'barcode', title: '条形码', width: 100, align: 'left', aliascol: 'goodsid',
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        return rowData.goodsInfo.barcode;
                    } else {
                        return "";
                    }
                }
            },
            {
                field: 'brandName', title: '商品品牌', width: 80, align: 'left', aliascol: 'goodsid', hidden: true,
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        return rowData.goodsInfo.brandName;
                    } else {
                        return "";
                    }
                }
            },
            {
                field: 'oldprice', title: '原价', width: 60, align: 'right',
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'offprice', title: '特价', width: 60, align: 'right',
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'lownum', title: '数量区间', width: 100,
                formatter: function (value, row, index) {
                    var s = "";
                    if (value == undefined && row.upnum == undefined) {
                        return "";
                    }
                    if (value != undefined) {
                        s += Number(value) + "-";
                    }
                    if (row.upnum != undefined && row.upnum != 0) {
                        s += Number(row.upnum);
                    }
                    else {
                        s += "";
                    }
                    return s;
                }
            },
            {
                field: 'mainunitName', title: '单位', width: 50, align: 'left', aliascol: 'goodsid',
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        return rowData.goodsInfo.mainunitName;
                    } else {
                        return "";
                    }
                }
            },
            {
                field: 'remark', title: '备注', width: 150, align: 'left',
                editor: {
                    type: 'validatebox',
                    options: {}
                }
            }
        ]]
    });
    $(function () {
        $("#sales-panel-offpricePage").panel({
            href: offprice_url,
            cache: false,
            maximized: true,
            border: false
        });
        //按钮
        $("#sales-buttons-offpricePage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/sales/offpriceAddPage.do">
                {
                    type: 'button-add',
                    handler: function () {
                        refreshPanel('sales/offpriceAddPage.do');
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/offpriceHold.do">
                {
                    type: 'button-hold',
                    handler: function () {
                        $.messager.confirm("提醒", "确定暂存该特价调整单信息？", function (r) {
                            if (r) {
                                $("#sales-addType-offpriceAddPage").val("temp");
                                var json = $("#sales-datagrid-offpriceAddPage").datagrid('getRows');
                                $("#sales-goodsJson-offpriceAddPage").val(JSON.stringify(json));
                                offprice_tempSave_form_submit();
                                $("#sales-form-offpriceAddPage").submit();
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/offpriceSave.do">
                {
                    type: 'button-save',
                    handler: function () {
                        $("#sales-addType-offpriceAddPage").val("real");
                        var json = $("#sales-datagrid-offpriceAddPage").datagrid('getRows');
//                            var rows = new Array();
//                            for(var i = 0 ;i<json.length;i++){
//                                if(json[i].)
//                            }
                        $("#sales-goodsJson-offpriceAddPage").val(JSON.stringify(json));
                        offprice_realSave_form_submit();
                        $("#sales-form-offpriceAddPage").submit();
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/offpriceSaveAudit.do">
                {
                    type: 'button-saveaudit',
                    handler: function () {
                        $.messager.confirm("提醒", "确定保存并审核特价调整单信息？", function (r) {
                            if (r) {
                                $("#sales-addType-offpriceAddPage").val("real");
                                $("#sales-saveaudit-offpriceAddPage").val("1");
                                var json = $("#sales-datagrid-offpriceAddPage").datagrid('getRows');
                                $("#sales-goodsJson-offpriceAddPage").val(JSON.stringify(json));
                                offprice_realSave_form_submit();
                                $("#sales-form-offpriceAddPage").submit();
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/offpriceGiveup.do">
                {
                    type: 'button-giveup',
                    handler: function () {
                        var type = $("#sales-buttons-offpricePage").buttonWidget("getOperType");
                        if (type == "add") {
                            var currTitle = top.$('#tt').tabs('getSelected').panel('options').title;
                            top.closeTab(currTitle);
                        }
                        else if (type == "edit") {
                            var id = $("#sales-backid-offpricePage").val();
                            if (id == "") {
                                return false;
                            }
                            $("#sales-panel-offpricePage").panel('refresh', 'sales/offpriceViewPage.do?id=' + id);
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/offpriceDelete.do">
                {
                    type: 'button-delete',
                    handler: function () {
                        var id = $("#sales-backid-offpricePage").val();
                        if (id == '') {
                            return false;
                        }
                        $.messager.confirm("提醒", "确定删除该特价调整单信息？", function (r) {
                            if (r) {
                                loading("删除中..");
                                $.ajax({
                                    url: 'sales/deleteOffprice.do',
                                    dataType: 'json',
                                    type: 'post',
                                    data: 'id=' + id,
                                    success: function (json) {
                                        loaded();
                                        if (json.delFlag == true) {
                                            $.messager.alert("提醒", "该信息已被其他信息引用，无法删除");
                                            return false;
                                        }
                                        if (json.flag == true) {
                                            $.messager.alert("提醒", "删除成功");
                                            var data = $("#sales-buttons-offpricePage").buttonWidget("removeData", '');
                                            if (data != null) {
                                                $("#sales-backid-offpricePage").val(data.id);
                                                refreshPanel('sales/offpriceEditPage.do?id=' + data.id);
                                            }
                                            else {
                                                parent.closeNowTab();
                                            }
                                        }
                                        else {
                                            $.messager.alert("提醒", "删除失败");
                                        }
                                    },
                                    error: function () {
                                        loaded();
                                        $.messager.alert("错误", "删除出错");
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/offpriceAudit.do">
                {
                    type: 'button-audit',
                    handler: function () {
                        var id = $("#sales-backid-offpricePage").val();
                        if (id == '') {
                            return false;
                        }
                        $.messager.confirm("提醒", "确定审核该特价调整单信息？", function (r) {
                            if (r) {
                                loading("审核中..");
                                $.ajax({
                                    url: 'sales/auditOffprice.do',
                                    dataType: 'json',
                                    type: 'post',
                                    data: 'id=' + id + '&type=1',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag == true) {
                                            $.messager.alert("提醒", "审核成功");
                                            $("#sales-panel-offpricePage").panel('refresh', 'sales/offpriceViewPage.do?id=' + id);
                                        }
                                        else {
                                            $.messager.alert("提醒", "审核失败");
                                        }
                                    },
                                    error: function () {
                                        loaded();
                                        $.messager.alert("错误", "审核出错");
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/offpriceOppaudit.do">
                {
                    type: 'button-oppaudit',
                    handler: function () {
                        var id = $("#sales-backid-offpricePage").val();
                        if (id == '') {
                            return false;
                        }
                        $.messager.confirm("提醒", "确定反审该特价调整单信息？", function (r) {
                            if (r) {
                                loading("反审中..");
                                $.ajax({
                                    url: 'sales/auditOffprice.do',
                                    dataType: 'json',
                                    type: 'post',
                                    data: 'id=' + id + '&type=2',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag == true) {
                                            $.messager.alert("提醒", "反审成功");
                                            $("#sales-panel-offpricePage").panel('refresh', 'sales/offpriceEditPage.do?id=' + id);
                                        }
                                        else {
                                            $.messager.alert("提醒", "反审失败");
                                        }
                                    },
                                    error: function () {
                                        loaded();
                                        $.messager.alert("错误", "反审出错");
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/offpriceWorkflow.do">
                {
                    type: 'button-workflow',
                    button: [
                        {
                            type: 'workflow-submit',
                            handler: function () {
                                $.messager.confirm("提醒", "是否提交该销售特价调整单信息到工作流?", function (r) {
                                    if (r) {
                                        var id = $("#sales-backid-offpricePage").val();
                                        if (id == "") {
                                            $.messager.alert("警告", "没有需要提交工作流的信息!");
                                            return false;
                                        }
                                        loading("提交中..");
                                        $.ajax({
                                            url: 'sales/submitOffpriceProcess.do',
                                            dataType: 'json',
                                            type: 'post',
                                            data: 'id=' + id,
                                            success: function (json) {
                                                loaded();
                                                if (json.flag == true) {
                                                    $.messager.alert("提醒", "提交成功!");
                                                    $("#sales-panel-offpricePage").panel("refresh");
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
                                if (offprice_type == "handle") {
                                    $("#sales-dialog-offpricePage").dialog({
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
                                var id = $("#sales-backid-offpricePage").val();
                                if (id == "") {
                                    return false;
                                }
                                $("#sales-dialog-offpricePage").dialog({
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
                                var id = $("#sales-backid-offpricePage").val();
                                if (id == "") {
                                    return false;
                                }
                                $("#sales-dialog-offpricePage").dialog({
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
                <security:authorize url="/sales/offpriceBack.do">
                {
                    type: 'button-back',
                    handler: function (data) {
                        $("#sales-backid-offpricePage").val(data.id);
                        refreshPanel('sales/offpriceEditPage.do?id=' + data.id);
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/offpriceNext.do">
                {
                    type: 'button-next',
                    handler: function (data) {
                        $("#sales-backid-offpricePage").val(data.id);
                        refreshPanel('sales/offpriceEditPage.do?id=' + data.id);
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/offpricePrintViewBtn.do">
                {
                    type: 'button-preview',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/offpricePrintBtn.do">
                {
                    type: 'button-print',
                    handler: function () {
                    }
                },
                </security:authorize>
                {}
            ],
            buttons: [
                <security:authorize url="/sales/offpriceUncancelBtn.do">
                <c:if test="${status == 5}">
                {
                    id: 'button-unCancel',
                    name: '取消作废',
                    iconCls: 'button-oppaudit',
                    handler: function () {
                        var id = $("#sales-backid-offpricePage").val();
                        if (id == '') {
                            return false;
                        }
                        $.messager.confirm("提醒", "确定取消作废该特价信息？", function (r) {
                            if (r) {
                                loading("取消作废中..");
                                $.ajax({
                                    url: 'sales/offpriceCancel.do',
                                    dataType: 'json',
                                    type: 'post',
                                    data: 'ids=' + id + '&operate=2',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag == true) {
                                            $.messager.alert("提醒", "取消作废成功。");
                                            $("#sales-panel-offpricePage").panel('refresh', 'sales/offpriceEditPage.do?id=' + id);
                                        }
                                        else {
                                            $.messager.alert("提醒", "取消作废失败<br/>" + json.msg);
                                        }
                                    },
                                    error: function () {
                                        loaded();
                                        $.messager.alert("错误", "取消作废出错");
                                    }
                                });
                            }

                        });

                    }
                },
                </c:if>
                </security:authorize>
            ],
            model: 'bill',
            type: 'view',
            tab: '特价调整单列表',
            taburl: '/sales/offpriceListPage.do',
            id: '${id}',
            datagrid: 'sales-datagrid-offpriceListPage'
        });
    });
    function refreshPanel(url) { //更新panel
        $("#sales-panel-offpricePage").panel('refresh', url);
    }
    function offprice_tempSave_form_submit() { //暂存表单方法
        $("#sales-form-offpriceAddPage").form({
            onSubmit: function () {
                loading("提交中..");
            },
            success: function (data) {
                loaded();
                var json = $.parseJSON(data);
                if (json.lock == true) {
                    $.messager.alert("提醒", "其他用户正在修改该数据，无法修改");
                    return false;
                }
                if (json.flag == true) {
                    $.messager.alert("提醒", "暂存成功");
                    $("#sales-backid-offpricePage").val(json.backid);
                    if (json.type == "add") {
                        $("#sales-buttons-offpricePage").buttonWidget("addNewDataId", json.backid);
                    }
                    $("#sales-panel-offpricePage").panel('refresh', 'sales/offpriceViewPage.do?id=' + json.backid);
                }
                else {
                    $.messager.alert("提醒", "暂存失败");
                }
            }
        });
    }
    function offprice_realSave_form_submit() { //保存表单方法
        $("#sales-form-offpriceAddPage").form({
            onSubmit: function () {
                var flag = $(this).form('validate');
                if (flag == false) {
                    return false;
                }
                loading("提交中..");
            },
            success: function (data) {
                loaded();
                var json = $.parseJSON(data);
                if (json.lock == true) {
                    $.messager.alert("提醒", "其他用户正在修改该数据，无法修改");
                    return false;
                } else if (json.cid != undefined) {
                    $.messager.alert("提醒", "当前客户数" + json.cid + "个，一般不超过20个，请修改选中的客户数量");
                    return false;
                } else if (json.flag == true) {
                    if ("1" == $("#sales-saveaudit-offpriceAddPage").val()) {
                        $.messager.alert("提醒", "保存审核成功！");
                    } else {
                        $.messager.alert("提醒", "保存成功！");
                    }
                    $("#sales-backid-offpricePage").val(json.backid);
                    if (json.type == "add") {
                        $("#sales-buttons-offpricePage").buttonWidget("addNewDataId", json.backid);
                    }
                    $("#sales-panel-offpricePage").panel('refresh', 'sales/offpriceEditPage.do?id=' + json.backid);
                } else {
                    $.messager.alert("提醒", "保存失败");
                }
            }
        });
    }
    function beginAddDetail() { //开始添加商品信息
        $('<div id="sales-dialog-offpriceAddPage-content"></div>').appendTo('#sales-dialog-offpriceAddPage');
        $("#sales-dialog-offpriceAddPage-content").dialog({ //弹出新添加窗口
            title: '商品特价信息添加(按ESC退出)',
            maximizable: true,
            width: 600,
            height: 400,
            closed: false,
            modal: true,
            cache: false,
            resizable: true,
            collapsible: false,
            href: 'sales/offpriceDetailAddPage.do',
            onLoad: function () {
                $("#sales-goodsId-offpriceDetailAddPage").focus();
            },
            onClose: function () {
                $("#sales-dialog-offpriceAddPage-content").dialog("destroy");
            }
        });
        // $("#sales-dialog-offpriceAddPage-content").dialog("open");
    }
    function addSaveDetail(go) { //添加新数据确定后操作，
        var flag = $("#sales-form-offpriceDetailAddPage").form('validate');
        if (flag == false) {
            return false;
        }
        var form = $("#sales-form-offpriceDetailAddPage").serializeJSON();
        var goodsJson = $("#sales-goodsId-offpriceDetailAddPage").goodsWidget('getObject');
        form.goodsInfo = goodsJson;
        var rows = $wareList.datagrid('getRows');
        var rowIndex = rows.length - 1;
        for (var i = 0; i < rows.length; i++) {
            var rowJson = rows[i];
            if (rowJson.goodsid == undefined) {
                rowIndex = i;
                break;
            }
        }
        if (rowIndex == rows.length - 1) {
            $wareList.datagrid('appendRow', {}); //如果是最后一行则添加一新行
        }
        $wareList.datagrid('updateRow', {index: rowIndex, row: form}); //将数据更新到列表中
        if (go) { //go为true确定并继续添加一条
            $("#sales-dialog-offpriceAddPage-content").dialog('close', true);
            beginAddDetail();
        }
        else { //否则直接关闭
            $("#sales-dialog-offpriceAddPage-content").dialog('close', true);
        }
    }
    function beginEditDetail(rowData) { //开始修改商品信息
        var row = $wareList.datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        if (row.upnum == 0) {
            row.upnum = null;
        }
        var url = '';
        if (row.goodsid == undefined) {
            beginAddDetail();
        }
        else {
            url = 'sales/offpriceDetailEditPage.do'; //如果是修改页面，数据直接来源于datagrid中的json数据。
            $('<div id="sales-dialog-offpriceAddPage-content"></div>').appendTo('#sales-dialog-offpriceAddPage');
            $("#sales-dialog-offpriceAddPage-content").dialog({ //弹出修改窗口
                title: '商品特价信息修改(按ESC退出)',
                maximizable: true,
                width: 600,
                height: 400,
                closed: false,
                modal: true,
                cache: false,
                resizable: true,
                href: url,
                onClose: function () {//数量区间上限不能为空，关闭时对其判断
                    var row = $wareList.datagrid('getSelected');
                    if (row.goodsid != undefined && row.upnum == null) {
                        row.upnum = 0;
                    }
                    $("#sales-dialog-offpriceAddPage-content").dialog("destroy");
                },
                onLoad: function () {
                    $("#sales-form-offpriceDetailAddPage").form('load', row);
                    $("#sales-idtip-offpriceDetailAddPage").text("商品编码：" + row.goodsid);
                    $("#sales-offprice-goodsname").val(row.goodsInfo.name);
                    $("#sales-offprice-brandname").val(row.goodsInfo.brandName);
                    $("#sales-offprice-barcode").val(row.goodsInfo.barcode);
                    $("#sales-offprice-unitname").val(row.goodsInfo.mainunitName);
                    $(".auxUnitName").text(row.goodsInfo.mainunitName);
                    $("#sales-lownum-offpriceDetailAddPage").focus();
                    $("#sales-lownum-offpriceDetailAddPage").select();
                }
            });
            //$("#sales-dialog-offpriceAddPage-content").dialog("open");
        }
    }
    function editSaveDetail(go) { //修改数据确定后操作，
        var flag = $("#sales-form-offpriceDetailAddPage").form('validate');
        if (flag == false) {
            return false;
        }
        var row = $wareList.datagrid('getSelected');
        var rowIndex = $wareList.datagrid('getRowIndex', row);
        var form = $("#sales-form-offpriceDetailAddPage").serializeJSON();
        $wareList.datagrid('updateRow', {index: rowIndex, row: form}); //将数据更新到列表中
        $("#sales-dialog-offpriceAddPage-content").dialog('close', true)
    }
    function removeDetail() {
        var row = $wareList.datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        $.messager.confirm("提醒", "确定删除该商品明细?", function (r) {
            if (r) {
                var rowIndex = $wareList.datagrid('getRowIndex', row);
                $wareList.datagrid('deleteRow', rowIndex);
                var rows = $wareList.datagrid('getRows');
                var index = -1;
                for (var i = 0; i < rows.length; i++) {
                    if (rows[i].goodsid != undefined) {
                        index = i;
                        break;
                    }
                }
                if (index == -1) {
                    //$("#sales-customer-orderAddPage").goodsWidget('readonly', false);
                }
            }
        });
    }
    $(function () {
        $(document).keydown(function (event) {//alert(event.keyCode);
            switch (event.keyCode) {
                case 27: //Esc
                    $("#sales-remark-offpriceDetailAddPage").focus();
                    $("#sales-dialog-offpriceAddPage-content").dialog('close');
                    break;
            }
        });
        $(document).bind('keydown', 'ctrl+enter', function () {
            $("#sales-remark-offpriceDetailAddPage").focus();
            $("#sales-savegoon-offpriceDetailAddPage").trigger("click");
            $("#sales-savegoon-offpriceDetailEditPage").trigger("click");
        });
        $(document).bind('keydown', '+', function () {
            $("#sales-remark-offpriceDetailAddPage").focus();
            setTimeout(function () {
                $("#sales-savegoon-offpriceDetailAddPage").trigger("click");
                $("#sales-savegoon-offpriceDetailEditPage").trigger("click");
            }, 100);
            return false;
        });
    });
    //批量添加商品
    function beginAddDetailByBrandAndSort() { //开始批量添加商品信息
        var customer = $("#sales-customer-offpriceAddPage").widget('getValue');
        if (customer == '') {
            $.messager.alert("提醒", "请先选择客户再进行添加商品信息");
            $("#sales-customer-offpriceAddPage").focus();
            return false;
        }

        $('<div id="sales-dialog-offpriceAddPage-content"></div>').appendTo('#sales-dialog-offpriceAddPage');
        $("#sales-dialog-offpriceAddPage-content").dialog({ //弹出新添加窗口
            title: '批量添加商品信息(按ESC退出)',
            maximizable: true,
            width: 1000,
            height: 450,
            closed: false,
            modal: true,
            cache: false,
            resizable: true,
            href: 'sales/offPriceAddByBrandAndSortPage.do',
            onClose: function () {
                $('#sales-dialog-offpriceAddPage-content').dialog("destroy");
            }
        });
    }
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "listPage-offprice-dialog-print",
            code: "sales_offprice",
            url_preview: "print/sales/salesOffpricePrintView.do",
            url_print: "print/sales/salesOffpricePrint.do",
            btnPreview: "button-preview",
            btnPrint: "button-print",
            printlimit: "${printlimit}",
            getData: function (tableId, printParam) {
                var id = $("#sales-backid-offpricePage").val();
                if (id == "") {
                    $.messager.alert("警告", "找不到要打印的信息!");
                    return false;
                }
                var status = $("#sales-status-offpriceAddPage").val() || "";
                if (!(status == '3' || status == '4')) {
                    $.messager.alert("提醒", id + "保存状态下单据不可打印");
                    return false;
                }
                printParam.idarrs = id;

                var printtimes = $("#sales-printtimes-offpriceAddPage").val() || 0;
                if (printtimes != "0")
                    printParam.printIds = [id];
                return true;
            }
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
