<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>配送单页面</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>

<body>
<div id="storage-layout-deliveryPage" class="easyui-layout"
     data-options="fit:true,border:false">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="storage-buttons-deliveryPage" style="height: 26px;"></div>
    </div>
    <div data-options="region:'center',border:false">
        <div class="easyui-panel" data-options="fit:true" id="storage-panel-deliveryPage">
        </div>
    </div>
</div>
<div id="delivery-dialog-saleout"></div>
<div id="delivery-dialog-customer"></div>
<form action="" id="delivery-form-customerExport" method="post">
    <input id="delivery-id-export" type="hidden" name="deliveryid" value=""/>
</form>
<a href="javaScript:void(0);" id="report-buttons-export" style="display: none" title="导入">导入</a>
<script type="text/javascript">
    var customerTableColJson = $("#delivery-table-Customer").createGridColumnLoad({
        frozenCol: [[]],
        commonCol: [[
            {field: 'customerid', title: '客户编号', width: 80, sortable: true},
            {field: 'customername', title: '客户名称', width: 400, sortable: false,
                formatter: function (value, rowData, rowIndex) {
                    if(rowData.billtype == '3') {
                        return '[仓库]' + value;
                    }
                    return value;
                }
            },
            {
                field: 'billnums', title: '单据数', width: 60, sortable: true,
                editor: {
                    type: 'numberbox',
                    options: {
                        precision: 0,
                        max: 999999999999,
                        min: 0,
                        onChange: function (oldval, newval) {
                            if (newval != "" && newval != oldval) {
                                var select = $dgCustomerList.datagrid("getSelected");
                                if (select.isedit != "2" && select.issaleout == "1") {
                                    select.isedit = "1";
                                }
                            }
                        }
                    }
                },
                styler: function (value, row, index) {
                    if (row.isedit == "2") {
                        return 'color:red;';
                    }
                }
            },
            {
                field: 'salesamount', title: '销售额', width: 120, sortable: true,
                formatter: function (value, rowData, rowIndex) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'auxnum', title: '整', width: 80, sortable: false, hidden: true,
                formatter: function (value, rowData, rowIndex) {
                    return formatterNum(value);
                }
            },
            {
                field: 'auxremainder', title: '零', width: 80, sortable: false, hidden: true,
                formatter: function (value, rowData, rowIndex) {
                    return formatterBigNumNoLen(value);
                }
            },
            {
                field: 'weight', title: '重量', width: 80, sortable: true,
                editor: {
                    type: 'numberbox',
                    options: {
                        precision: 4,
                        max: 999999999999,
                        min: 0,
                        onChange: function (oldval, newval) {
                            if (newval != "" && newval != oldval) {
                                var select = $dgCustomerList.datagrid("getSelected");
                                if (select.isedit != "2" && select.issaleout == "1") {
                                    select.isedit = "1";
                                }
                            }
                        }
                    }
                },
                formatter: function (value, rowData, rowIndex) {
                    if (value == null)
                        return null;
                    return formatterMoney(value, 4) + " kg";
                },
                styler: function (value, row, index) {
                    if (row.isedit == "2") {
                        return 'color:red;';
                    }
                }
            },
            {
                field: 'volume', title: '体积', width: 80, sortable: true,
                editor: {
                    type: 'numberbox',
                    options: {
                        precision: 4,
                        max: 999999999999,
                        min: 0,
                        onChange: function (oldval, newval) {
                            if (newval != "" && newval != oldval) {
                                var select = $dgCustomerList.datagrid("getSelected");
                                if (select.isedit != "2" && select.issaleout == "1") {
                                    select.isedit = "1";
                                }
                            }
                        }
                    }
                },
                formatter: function (value, rowData, rowIndex) {
                    if (value == null)
                        return null;
                    return formatterMoney(value, 4) + " m³";
                },
                styler: function (value, row, index) {
                    if (row.isedit == "2") {
                        return 'color:red;';
                    }
                }
            },
            {
                field: 'boxnum', title: '箱数', width: 80, sortable: true,
                editor: {
                    type: 'numberbox',
                    options: {
                        precision: 2,
                        max: 999999999999,
                        min: 0,
                        onChange: function (oldval, newval) {
                            if (newval != "" && newval != oldval) {
                                var select = $dgCustomerList.datagrid("getSelected");
                                if (select.isedit != "2" && select.issaleout == "1") {
                                    select.isedit = "1";
                                }
                            }
                        }
                    }
                },
                formatter: function (value, rowData, rowIndex) {
                    return formatterBigNumNoLen(value);
                },
                styler: function (value, row, index) {
                    if (row.isedit == "2") {
                        return 'color:red;';
                    }
                }
            },
            {
                field: 'collectionamount', title: '收款金额', width: 80, sortable: true,
                editor: {
                    type: 'numberbox',
                    options: {
                        precision: 2,
                        max: 999999999999,
                        min: 0,
                        onChange: function (oldval, newval) {
                            if (newval != "" && newval != oldval) {
                                var select = $dgCustomerList.datagrid("getSelected");
                                if (select.isedit != "2" && select.issaleout == "1") {
                                    select.isedit = "1";
                                }
                            }
                        }
                    }
                },
                formatter: function (value, rowData, rowIndex) {
                    return formatterMoney(value);
                },
                styler: function (value, row, index) {
                    if (row.isedit == "2") {
                        return 'color:red;';
                    }
                }
            },
            {
                field: 'isreceipt', title: '回单', width: 80,//checkbox:true,
                editor: {
                    type: 'defaultSelect',
                    options: {
                        valueField: 'value',
                        textField: 'text',
                        classid: 'contacterSortDefault',
                        defaultval: '1',
                        data: [
                            {value: '0', text: '否'},
                            {value: '1', text: '是'}
                        ],
                        onChange: function (oldval, newval) {
                            if (newval != "" && newval != oldval) {
                                var select = $dgCustomerList.datagrid("getSelected");
                                if (select.isedit != "2" && select.issaleout == "1") {
                                    select.isedit = "1";
                                }
                            }
                        }
                    }
                },
                formatter: function (value, rowData, rowIndex) {
                    if (value == '0')return "否";
                    if (value == '1')return "是";
                },
                styler: function (value, row, index) {
                    if (row.isedit == "2") {
                        return 'color:red;';
                    }
                }
            },
            {field: 'remark', title: '备注', width: 120, sortable: false, editor: 'text'},
            {field: 'seq', title: '排序', width: 60, sortable: false, hidden: true},
            {
                field: 'issaleout', title: '是否关联发货', width: 80, hidden: true,
                formatter: function (value, rowData, rowIndex) {
                    if (value == '0')return "否";
                    if (value == '1')return "是";
                }
            }
        ]]
    });

    var saleoutTableColJson = $("#delivery-table-Saleout").createGridColumnLoad({
        frozenCol: [[{field: 'ck', checkbox: true}]],
        commonCol: [[
            {field: 'deliveryid', title: '配送单编号', width: 120, sortable: false, hidden: true},
            {field: 'saleoutid', title: '发货单编号', width: 140, sortable: false},
            {
                field: 'billtype', title: '来源单据类型', width: 80, hidden: true,
                formatter: function (value, rowData, rowIndex) {
                    if (value == '1') {
                        return "销售发货单";
                    } else if (value == '2') {
                        return "代配送出库单";
                    } else if (value == '3') {
                        return "调拨单";
                    }
                }
            },
            {field: 'orderid', title: '订单编号', width: 120, sortable: false},
            {field: 'businessdate', title: '业务日期', width: 100, sortable: false},
            {field: 'customerid', title: '客户编号', width: 80, sortable: false},
            {field: 'customername', title: '客户名称', width: 160, sortable: false,
                formatter: function (value, row, index) {
                    if(row.billtype == '3') {
                        return '[仓库]' + value;
                    }
                    return value;
                }
            },
            {
                field: 'salesamount', title: '发货金额', width: 120, sortable: false,
                formatter: function (value, rowData, rowIndex) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'weight', title: '商品重量', width: 80, sortable: false,
                formatter: function (value, rowData, rowIndex) {
                    if (value == null)
                        return null;
                    return formatterMoney(value, 4) + " kg";
                }
            },
            {
                field: 'volume', title: '商品体积', width: 80, sortable: false,
                formatter: function (value, rowData, rowIndex) {
                    if (value == null)
                        return null;
                    return formatterMoney(value, 4) + " m³";
                }
            },
            {
                field: 'boxnum', title: '箱数', width: 80, sortable: true,
                formatter: function (value, rowData, rowIndex) {
                    if (value == null)
                        return null;
                    return formatterBigNumNoLen(value);
                }
            },
            {field: 'salesusername', title: '客户业务员', width: 100, sortable: false}
        ]]
    });

    var page_url = "";
    var page_type = '${type}';
    var page_title = "配送单【查看】";
    var title = tabsWindowTitle('/storage/showDeliveryListPage.do');

    function refreshPanel(id, type) {
        $("#delivery-id-export").val(id);
        page_type = type;
        if (page_type == "view") {
            page_url = "storage/showDeliveryViewPage.do?id=" + id;
            page_title = "配送单【查看】";
        } else if (page_type == "edit") {
            page_url = "storage/showDeliveryEditPage.do?id=" + id;
            page_title = "配送单【修改】";
        } else if (page_type == "audit") {
            page_url = "storage/showDeliveryAuditPage.do?id=" + id;
            page_title = "配送单【审核】";
        }

        $("#storage-panel-deliveryPage").panel({
            href: page_url,
            cache: false,
            maximized: true,
            border: false
            //,
            //title:page_title
        });
    }

    function getType(status) {
        var type = 'view';
        if (status == '2')
            type = 'edit';
        if (status == '3')
            type = 'audit';
        return type;
    }

    function deliveryFormValide() {
        return $("#delivery-form-head").form('validate') && $("#delivery-form-other").form('validate')
    }

    function businessdateChange() {
        $.messager.confirm("提醒", "出车日期修改，是否修改装车次数？", function (r) {
            if (r) {
                $("#delivery-truck-numberbox").focus();
            }
        });
    }

    var delivery_AjaxConn = function (Data, Action) {
        var MyAjax = $.ajax({
            type: 'post',
            cache: false,
            url: Action,
            data: Data,
            async: false
        })
        return MyAjax.responseText;
    }

    $(function () {
        refreshPanel('${id}', '${type}');

        //按钮
        $("#storage-buttons-deliveryPage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/storage/delivery/deliverySave.do">
                {
                    type: 'button-save',
                    id: 'button-save',
                    handler: function () {
                        var businessdate = $("#delivery-businessdate-baseInfo").val();
                        if (businessdate == "") {
                            $.messager.alert("提醒", "出车日期不能为空!");
                            return false;
                        }
                        var driverid = $("#delivery-widget-driverid").widget('getValue');
                        var followid = $("#delivery-widget-followid").widget('getValue');
                        if (driverid != "" && followid != "") {
                            if (followid == driverid) {
                                $.messager.alert("提醒", "司机与跟车员不能为同一人!");
                                return false;
                            }
                        }
                        if (!deliveryFormValide()) {
                            return false;
                        }
                        var id = $("#delivery-id-baseInfo").val();
                        var truck = $("#delivery-truck-numberbox").numberbox('getValue');
                        if ("3" == $("#delivery-hidden-status").val() && oldtruck != truck) {
                            var carid = $("#delivery-widget-carid").widget("getValue");
                            var ret = delivery_AjaxConn({
                                id: id,
                                truck: truck,
                                carid: carid,
                                businessdate: businessdate
                            }, 'storage/isExistSameTruck.do');
                            var retjson = $.parseJSON(ret);
                            if (retjson.flag) {
                                $.messager.alert("提醒", "已存在相同出车日期、车号下的该装车次数！");
                                $("#delivery-truck-numberbox").numberbox('setValue', retjson.truck);
                                return false;
                            }
                        }
                        $.messager.confirm("提醒", "是否保存配送单？", function (r) {
                            if (r) {
                                var json = editDelivery();
                                if (json.flag) {
                                    if (top.$('#tt').tabs('exists', title)) {
                                        tabsWindow(title).$("#storage-datagrid-deliveryPage").datagrid('reload');
                                    }

                                    refreshPanel(id, getType(json.status));
                                    $.messager.alert("提醒", "保存成功!");
                                } else {
                                    $.messager.alert("提醒", "保存失败!");
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/delivery/deliverySaveAudit.do">
                {
                    type: 'button-saveaudit',
                    handler: function () {
                        var businessdate = $("#delivery-businessdate-baseInfo").val();
                        if (businessdate == "") {
                            $.messager.alert("提醒", "出车日期不能为空!");
                            return false;
                        }
                        var driverid = $("#delivery-widget-driverid").widget('getValue');
                        var followid = $("#delivery-widget-followid").widget('getValue');
                        if (driverid != "" && followid != "") {
                            if (followid == driverid) {
                                $.messager.alert("提醒", "司机与跟车员不能为同一人!");
                                return false;
                            }
                        }
                        if (!deliveryFormValide()) {
                            return false;
                        }
                        var id = $("#delivery-id-baseInfo").val();
                        var truck = $("#delivery-truck-numberbox").numberbox('getValue');
                        if ("3" == $("#delivery-hidden-status").val() && oldtruck != truck) {
                            var carid = $("#delivery-widget-carid").widget("getValue");
                            var ret = delivery_AjaxConn({
                                id: id,
                                truck: truck,
                                carid: carid,
                                businessdate: businessdate
                            }, 'storage/isExistSameTruck.do');
                            var retjson = $.parseJSON(ret);
                            if (retjson.flag) {
                                $.messager.alert("提醒", "已存在相同出车日期、车号下的该装车次数！");
                                $("#delivery-truck-numberbox").numberbox('setValue', retjson.truck);
                                return false;
                            }
                        }
                        $.messager.confirm("提醒", "是否保存并审核配送单？", function (r) {
                            if (r) {
                                var json = saveAuditDelivery();
                                if (json.flag) {
                                    if (top.$('#tt').tabs('exists', title)) {
                                        tabsWindow(title).$("#storage-datagrid-deliveryPage").datagrid('reload');
                                    }
                                    refreshPanel(id, 'audit');
                                    $.messager.alert("提醒", "保存并审核成功!");
                                } else {
                                    $.messager.alert("提醒", "保存并审核失败!");
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/delivery/deliveryDelete.do">
                {
                    type: 'button-delete',
                    handler: function () {
                        var id = $("#delivery-id-baseInfo").val();
                        $.messager.confirm("提醒", "是否确定删除配送单?", function (r) {
                            if (r) {
                                loading("删除中..");
                                $.ajax({
                                    url: 'storage/deleteDeliverys.do?ids=' + id,
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        $.messager.alert("提醒", "" + json.userNum + "条记录被引用,不允许删除;<br/>" + json.lockNum + "条记录网络互斥,不允许删除;<br/>" + json.invalidNum + "条记录无效,不允许删除;<br/>删除成功" + json.num + "条记录;");
                                        if (json.flag) {
                                            if (top.$('#tt').tabs('exists', title)) {
                                                tabsWindow(title).$("#storage-datagrid-deliveryPage").datagrid('reload');
                                            }
                                            var data = $("#storage-buttons-deliveryPage").buttonWidget("removeData", id);
                                            if (null != data) {
                                                refreshPanel(data.id, getType(data.status));
                                            } else {
                                                top.closeNowTab();
                                            }
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
                <security:authorize url="/storage/delivery/deliveryAudit.do">
                {
                    type: 'button-audit',
                    handler: function () {
                        var id = $("#delivery-id-baseInfo").val();
                        $.messager.confirm("提醒", "是否审核配送单？", function (r) {
                            if (r) {
                                loading("审核中..");
                                $.ajax({
                                    url: 'storage/auditDeliverys.do?ids=' + id,
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag) {
                                            $.messager.alert("提醒", "审核成功");
                                            if (top.$('#tt').tabs('exists', title)) {
                                                tabsWindow(title).$("#storage-datagrid-deliveryPage").datagrid('reload');
                                            }
                                            refreshPanel(id, 'audit');
                                        } else {
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
                <security:authorize url="/storage/delivery/deliveryOppaudit.do">
                {
                    type: 'button-oppaudit',
                    handler: function () {
                        var businessdate = $("#delivery-businessdate-baseInfo").val();
                        var flag = isDoneOppauditBillCaseAccounting(businessdate);
                        if (!flag) {
                            $.messager.alert("提醒", "出车日期不在会计区间内或未设置会计区间,不允许反审!");
                            return false;
                        }
                        var id = $("#delivery-id-baseInfo").val();
                        $.messager.confirm("提醒", "是否反审配送单？", function (r) {
                            if (r) {
                                loading("反审中..");
                                $.ajax({
                                    url: 'storage/oppauditDeliverys.do?ids=' + id,
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag) {
                                            $.messager.alert("提醒", "反审成功");
                                            if (top.$('#tt').tabs('exists', title)) {
                                                tabsWindow(title).$("#storage-datagrid-deliveryPage").datagrid('reload');
                                            }
                                            refreshPanel(id, 'edit');
                                        } else {
                                            $.messager.alert("提醒", "反审失败 " + json.msg);
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
                <security:authorize url="/storage/deliveryViewBackPage.do">
                {
                    type: 'button-back',
                    handler: function (data) {
                        refreshPanel(data.id, getType(data.status));
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/deliveryViewNextPage.do">
                {
                    type: 'button-next',
                    handler: function (data) {
                        refreshPanel(data.id, getType(data.status));
                    }
                },
                </security:authorize>

                {}
            ],
            buttons: [
                {},
                <security:authorize url="/storage/delivery/deliveryCheck.do">
                {
                    id: 'button-check',
                    iconCls: 'button-file',
                    name: '验收',
                    handler: function (data) {
                        if (!deliveryFormValide()) {
                            return false;
                        }
                        $.messager.confirm("提醒", "是否验收配送单？", function (r) {
                            if (r) {
                                checkDelivery();
                            }
                        });
                    }
                },
                </security:authorize>

                <security:authorize url="/storage/delivery/deliveryUnCheck.do">
                {
                    id: 'button-unCheck',
                    iconCls: 'button-file',
                    name: '反验收',
                    handler: function (data) {
                        $.messager.confirm("提醒", "是否反验收配送单？", function (r) {
                            if (r) {
                                unCheckDelivery();
                            }
                        });
                    }
                },
                </security:authorize>

                <security:authorize url="/storage/delivery/deliveryCustomerExport.do">
                {
                    id: 'button-customerExport',
                    iconCls: 'button-export',
                    name: '导出',
                    handler: function (data) {
                        $("#report-buttons-export").Excel('export', {
                            queryForm: "#delivery-form-customerExport", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
                            type: 'exportUserdefined',
                            name: '配送单-交接单',
                            url: 'storage/delivery/deliveryCustomerExport.do?'//id='+id;
                        });
                        $("#report-buttons-export").trigger("click");
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/delivery/deliveryPrintView.do">
                {
                    id: 'printMenuButton',
                    type: 'menu',
                    name: '打印',
                    iconCls: 'button-preview',
                    button: [
                        {},
                        <security:authorize url="/storage/delivery/deliveryCustomerPrintView.do">
                        {
                            id: 'print-preview-button',
                            name: '交接单明细打印预览',
                            iconCls: 'button-preview',
                            handler: function () {
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/storage/delivery/deliveryCustomerPrint.do">
                        {
                            id: 'print-bill-button',
                            name: '交接单明细打印',
                            iconCls: 'button-print',
                            handler: function () {
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/storage/delivery/deliveryBillPrintView.do">
                        {
                            id: 'print-preview-deliverybill-button',
                            name: '配送单明细打印预览',
                            iconCls: 'button-preview',
                            handler: function () {
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/storage/delivery/deliveryBillPrint.do">
                        {
                            id: 'print-deliverybill-button',
                            name: '配送单明细打印',
                            iconCls: 'button-preview',
                            handler: function () {
                            }
                        },
                        </security:authorize>
                        {}
                    ]
                },
                </security:authorize>
                {}
            ],
            //layoutid:'storage-layout-deliveryPage',
            model: 'bill',
            type: '${type}',
            id: '${id}',
            taburl: '/storage/showDeliveryListPage.do',		//tab标签的url地址。
            datagrid: 'storage-datagrid-deliveryPage'
        });
    });

    /**
     *
     */
    function resizeMapSize() {

        var height = $(window).height() - 170;
        if (height < 100) {
            height = 100;
        }
        $('[name=mapframe1]').css({height: height + 'px'});
    }
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        function getData(tableId, printParam) {
            var id = $("#delivery-id-baseInfo").val();
            printParam.idarrs = id;
            var printtimes = $("#deliver-printtimes-hidden").val();
            if (printtimes > 0)
                printParam.printIds = [id];
            return true;
        }

        function onPrintSuccess(option) {
            if (top.$('#tt').tabs('exists', title)) {
                var printtimes = $("#deliver-printtimes-hidden").val();
                printtimes = Number(printtimes) + 1;
                $("#deliver-printtimes-hidden").val(printtimes);

                var id = $("#delivery-id-baseInfo").val();
                var $grid = tabsWindow(title).$("#storage-datagrid-deliveryPage");
                var rowIndex = $grid.datagrid('getRowIndex', id);
                var row = $grid.datagrid('getRows')[rowIndex];
                row['printtimes'] = printtimes;
                $grid.datagrid('updateRow', {index: rowIndex, row: row});
            }
        }

        //交接单明细打印
        AgReportPrint.init({
            id: "logistics-dialog-print",
            code: "storage_logistics",
            url_preview: "print/storage/deliveryCustomerPrintView.do",
            url_print: "print/storage/deliveryCustomerPrint.do",
            btnPreview: "print-preview-button",
            btnPrint: "print-bill-button",
            getData: getData,
            onPrintSuccess: onPrintSuccess
        });
        //配送单明细打印
        AgReportPrint.init({
            id: "deliverybill-dialog-print",
            code: "storage_logistics_bill",
            url_preview: "print/storage/deliveryBillPrintView.do",
            url_print: "print/storage/deliveryBillPrint.do",
            btnPreview: "print-preview-deliverybill-button",
            btnPrint: "print-deliverybill-button",
            getData: getData,
            onPrintSuccess: onPrintSuccess
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
