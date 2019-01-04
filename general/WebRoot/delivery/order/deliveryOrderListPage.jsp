<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>供应商代配送代配送销售订单</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
    <script type="text/javascript" src="js/jquery.excel.js"></script>
</head>
<body>
<table id="delivery-table-showDeliveryOrderList"></table>
<div id="delivery-query-showDeliveryOrderList" style="padding:0px;height:auto">
    <div class="buttonBG">
        <security:authorize url="/delivery/addDeliveryOrderPage.do">
            <a href="javaScript:void(0);" id="delivery-add-addDeliveryOrder" class="easyui-linkbutton button-list"
               data-options="plain:true,iconCls:'button-add'" title="新增">新增</a>
        </security:authorize>
        <security:authorize url="/delivery/editDeliveryOrderPage.do">
            <a href="javaScript:void(0);" id="delivery-edit-editDeliveryOrder" class="easyui-linkbutton button-list"
               data-options="plain:true,iconCls:'button-edit'" title="修改">修改</a>
        </security:authorize>
        <security:authorize url="/delivery/auditDeliveryOrder.do">
            <a href="javaScript:void(0);" id="delivery-audit-auditDeliveryOrder" class="easyui-linkbutton button-list"
               data-options="plain:true,iconCls:'button-audit'" title="审核">审核</a>
        </security:authorize>
        <security:authorize url="/delivery/importDeliveryOrderPage.do">
            <a href="javaScript:void(0);" id="delivery-in-inDeliveryOrder" class="easyui-linkbutton button-list"
               data-options="plain:true,iconCls:'button-import'" title="导入">导入</a>
        </security:authorize>
        <security:authorize url="/delivery/importDeliveryOrderPage.do">
            <a href="javaScript:void(0);" id="delivery-modelin-inDeliveryOrder" class="easyui-linkbutton button-list"
               data-options="plain:true,iconCls:'button-import'" title="模板导入">模板导入</a>
        </security:authorize>
        <security:authorize url="/delivery/exportDeliveryOrderPage.do">
            <a href="javaScript:void(0);" id="delivery-out-outDeliveryOrder" class="easyui-linkbutton button-list"
               data-options="plain:true,iconCls:'button-export'" title="导出">导出</a>
        </security:authorize>
        <security:authorize url="/delivery/exportDeliveryOrderPage.do">
            <a href="javaScript:void(0);" id="delivery-orderout-orderoutDeliveryOrder"
               class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-export'" title="按单据导出">按单据导出</a>
        </security:authorize>
        <security:authorize url="/delivery/previewDeliveryOrderPage.do">
            <a href="javaScript:void(0);" id="btn-previewDeliveryOrder"
               class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-preview'" title="打印预览">打印预览</a>
        </security:authorize>
        <security:authorize url="/delivery/printDeliveryOrderPage.do">
            <a href="javaScript:void(0);" id="btn-printDeliveryOrder" class="easyui-linkbutton button-list"
               data-options="plain:true,iconCls:'button-print'" title="打印">打印</a>
        </security:authorize>
    </div>
    <div>
        <form action="" id="delivery-form-showDeliveryOrderList" method="post">
            <table class="querytable">
                <tr>
                    <td>编号：</td>
                    <td><input type="text" id="delivery-id-deliveryOrderSourceQueryPage" name="id"
                               style="width: 220px;"/></td>
                    <td>客户：</td>
                    <td class="len165"><input type="text" id="delivery-customerid-deliveryOrderSourceQueryPage"
                                              name="customerid" style="width: 160px;"/></td>
                    <td>状态：</td>
                    <td>
                        <select name="status" style="width:150px;">
                            <option></option>
                            <option value="2" selected="selected">保存</option>
                            <option value="3">审核通过</option>
                            <option value="4">关闭</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>业务日期：</td>
                    <td><input type="text" name="businessdate1" style="width:100px;" class="Wdate"
                               onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${firstday }"/>
                        到 <input type="text" name="businessdate2" style="width:100px;" class="Wdate"
                                 onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/>
                    </td>
                    <td>仓库：</td>
                    <td class="len165"><input type="text" id="delivery-storageid-deliveryOrderSourceQueryPage"
                                              style="width:165px;" name="storageid"/></td>
                    <td>商品名称：</td>
                    <td class="len165"><input type="text" id="delivery-goodsid-deliveryOrderSourceQueryPage"
                                              style="width:150px;" name="goodsid"/></td>
                </tr>
                <tr>
                    <td>打印状态：</td>
                    <td>
                        <select name="printstatus" style="width:220px;">
                            <option selected="selected"></option>
                            <option value="1">未打印</option>
                            <option value="2">已打印</option>
                        </select>
                    </td>
                    <td>客户单号：</td>
                    <td>
                        <input type="text" id="delivery-sourceid-deliveryOrderSourceQueryPage" style="width:160px;"
                               name="sourceid"/>
                    </td>
                    <td colspan="2">
                        <a href="javaScript:void(0);" id="delivery-queay-deliveryOrder" class="button-qr">查询</a>
                        <a href="javaScript:void(0);" id="delivery-reload-deliveryOrder" class="button-qr">重置</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<div id="delivery-import-dialog"></div>
<script type="text/javascript">
    var initQueryJSON = $("#delivery-form-showDeliveryOrderList").serializeJSON();
    //数据列表
    var deliveryOrderJson = $("#delivery-table-showDeliveryOrderList").createGridColumnLoad({
        name: '',
        frozenCol: [[
            {field: 'ck', checkbox: true, isShow: true}
        ]],
        commonCol: [[
            {field: 'id', title: '编号', width: 125, sortable: true},
            {field: 'businessdate', title: '业务日期', width: 80, sortable: true},
            {field: 'supplierid', title: '供应商编号', width: 80, sortable: true, hidden: true},
            {field: 'suppliername', title: '供应商', width: 80, sortable: true, hidden: true},
            {field: 'storagename', title: '仓库', width: 80, sortable: true},
            {field: 'customerid', title: '客户编号', width: 80, sortable: true},
            {field: 'customername', title: '客户', width: 140, sortable: true},
            {field: 'sourceid', title: '客户单号', width: 80, sortable: true},
            {field: 'storageid', title: '仓库编号', width: 80, sortable: true, hidden: true},
            {
                field: 'totalvolume', title: '总体积', width: 60, sortable: true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'totalweight', title: '总重量', width: 60, sortable: true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'totalbox', title: '总箱数', width: 60, sortable: true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'totalamount', title: '总金额', width: 60, sortable: true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'status', title: '状态', width: 60, sortable: true,
                formatter: function (value, rowData, rowIndex) {
                    return getSysCodeName("status", value);
                }
            },
            {field: 'addusername', title: '制单人', width: 60, sortable: true},
            {field: 'adddeptname', title: '制单人部门名称', width: 100, sortable: true},
            {
                field: 'addtime', title: '制单时间', width: 130, sortable: true,
                formatter: function (dateValue, row, index) {
                    if (dateValue != undefined) {
                        var newstr = dateValue.replace("T", " ");
                        return newstr;
                    }
                    return " ";
                }
            },
            {field: 'auditusername', title: '审核人', width: 60, sortable: true},
            {
                field: 'printstate', title: '打印状态', width: 80, isShow: true,
                formatter: function (value, rowData, index) {
                    if (rowData.printtimes > 0) {
                        return "已打印";
                    } else if (rowData.printtimes == null) {
                        return "";
                    } else {
                        return "未打印";
                    }
                }
            },
            {field: 'printtimes', title: '打印次数', width: 80, sortable: true, hidden: true},
            {field: 'remark', title: '备注', width: 80, sortable: true},
        ]]
    });

    $(function () {
        $("#delivery-table-showDeliveryOrderList").datagrid({
            authority: deliveryOrderJson,
            frozenColumns: deliveryOrderJson.frozen,
            columns: deliveryOrderJson.common,
            fit: true,
            title: "",
            method: 'post',
            rownumbers: true,
            pagination: true,
            idField: 'id',
            sortName: 'id',
            sortOrder: 'desc',
            singleSelect: false,
            checkOnSelect: true,
            selectOnCheck: true,
            showFooter: false,
            pageSize: 100,
            url: 'delivery/showDeliveryOrderList.do',
            queryParams: initQueryJSON,
            toolbar: '#delivery-query-showDeliveryOrderList',
            onDblClickRow: function (rowIndex, rowData) {
                top.addOrUpdateTab('delivery/showDeliveryOrderEditPage.do?id=' + rowData.id, "代配送销售订单查看");
            }
        }).datagrid("columnMoving");
        //回车时间
        controlQueryAndResetByKey("delivery-queay-deliveryOrder", "delivery-reload-deliveryOrder");
        //查询
        $("#delivery-queay-deliveryOrder").click(function () {
            var queryJSON = $("#delivery-form-showDeliveryOrderList").serializeJSON();
            $("#delivery-table-showDeliveryOrderList").datagrid("load", queryJSON);
        });
        //新增
        $("#delivery-add-addDeliveryOrder").click(function () {
            var url = "delivery/showDeliveryOrderAddPage.do";
            top.addTab(url, '新增代配送销售订单');
        });
        //重置
        $("#delivery-reload-deliveryOrder").click(function () {
            $("#delivery-supplierid-deliveryOrderSourceQueryPage").supplierWidget("clear");
            $("#delivery-storageid-deliveryOrderSourceQueryPage").widget("clear");
            $("#delivery-goodsid-deliveryOrderSourceQueryPage").goodsWidget("clear");
            $("#delivery-form-showDeliveryOrderList").form("reset");
            var queryJSON = $("#delivery-form-showDeliveryOrderList").serializeJSON();
            $("#delivery-table-showDeliveryOrderList").datagrid('load', queryJSON);
        });
        //修改
        $("#delivery-edit-editDeliveryOrder").click(function () {
            var con = $("#delivery-table-showDeliveryOrderList").datagrid('getSelected');
            if (null == con) {
                $.messager.alert("提醒", "请选择代配送销售订单!");
                return false
            }
            var url = "delivery/showDeliveryOrderEditPage.do?id=" + con.id;
            top.addTab(url, '修改代配送销售订单');
        });
        //导入
        $("#delivery-in-inDeliveryOrder").Excel('import', {
            url: 'delivery/importOrder.do',
            type: 'importUserdefined',
            onClose: function () { //导入成功后窗口关闭时操作，
                $("#delivery-table-showDeliveryOrderList").datagrid('reload');	//更新列表	                                                                                        
            }
        });
        //模板导入
        $("#delivery-modelin-inDeliveryOrder").click(function () {
            $("#delivery-import-dialog").dialog({
                href: 'delivery/showModelOrderParamPage.do',
                width: 400,
                height: 300,
                title: '模板文件上传',
                colsed: false,
                cache: false,
                modal: true
            });
        });
        //导出
        $("#delivery-out-outDeliveryOrder").Excel('export', {
            queryForm: "#delivery-form-showDeliveryOrderList", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
            datagrid: "#delivery-table-showDeliveryOrderList",
            type: 'exportUserdefined',
            name: '代配送销售订单列表',
            url: 'delivery/exportOrder.do'
        });
        //按单据导出
        $("#delivery-orderout-orderoutDeliveryOrder").Excel('export', {
            queryForm: "#delivery-form-showDeliveryOrderList", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
            datagrid: "#delivery-table-showDeliveryOrderList",
            type: 'exportUserdefined',
            name: '代配送销售订单列表',
            url: 'delivery/exportOrderList.do'
        });
        //批量审核
        $("#delivery-audit-auditDeliveryOrder").click(function () {
            var rows = $("#delivery-table-showDeliveryOrderList").datagrid('getChecked');
            if (rows.length == 0) {
                $.messager.alert("提醒", "请选择代配送销售订单!");
                return false
            }
            var ids = "";
            for (var i = 0; i < rows.length; i++) {
                if (ids == "") {
                    ids = rows[i].id;
                } else {
                    ids += "," + rows[i].id;
                }
            }
            $.messager.confirm('提醒', '确定要审核吗?', function (r) {
                if (r) {
                    $.ajax({
                        url: 'delivery/auditsDeliveryOrder.do?ids=' + ids,
                        type: 'post',
                        dataType: 'json',
                        async: false,
                        success: function (json) {
                            $.messager.alert("提醒", json.msg);
                            var queryJSON = $("#delivery-form-showDeliveryOrderList").serializeJSON();
                            $("#delivery-table-showDeliveryOrderList").datagrid("load", queryJSON);
                        },
                        error: function () {
                            loaded();
                            $.messager.alert("错误", "审核失败");
                        }
                    });
                }
            });
        });
    });
    //客户选择
    $("#delivery-customerid-deliveryOrderSourceQueryPage").customerWidget({});
    //仓库选择
    $("#delivery-storageid-deliveryOrderSourceQueryPage").widget({
        referwid: 'RL_T_BASE_STORAGE_INFO',
        width: 160,
        col: 'storageid',
        singleSelect: true,
        initValue: ''
    });
    //商品名称选择
    $("#delivery-goodsid-deliveryOrderSourceQueryPage").goodsWidget({
        col: 'id',
        singleSelect: true,
        width: 150,
        canBuySale: '1'
    });
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "listPage-deliveryorder-dialog-print",
            code: "delivery_order",
            tableId: "delivery-table-showDeliveryOrderList",
            url_preview: "print/delivery/orderPrintView.do",
            url_print: "print/delivery/orderPrint.do",
            btnPreview: "btn-previewDeliveryOrder",
            btnPrint: "btn-printDeliveryOrder"
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>