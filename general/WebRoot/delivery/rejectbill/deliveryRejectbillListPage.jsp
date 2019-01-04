<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>供应商代配送代配送销售退单</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
    <script type="text/javascript" src="js/jquery.excel.js"></script>
</head>
<body>
<table id="delivery-table-showDeliveryRejectbillList"></table>
<div id="delivery-query-showDeliveryRejectbillList" style="padding:0px;height:auto">
    <div class="buttonBG">
        <security:authorize url="/delivery/addDeliveryRejectbillPage.do">
            <a href="javaScript:void(0);" id="delivery-add-addDeliveryRejectbill" class="easyui-linkbutton button-list"
               data-options="plain:true,iconCls:'button-add'" title="新增">新增</a>
        </security:authorize>
        <security:authorize url="/delivery/editDeliveryRejectbillPage.do">
            <a href="javaScript:void(0);" id="delivery-edit-editDeliveryRejectbill"
               class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-edit'" title="修改">修改</a>
        </security:authorize>
        <security:authorize url="/delivery/auditDeliveryRejectbill.do">
            <a href="javaScript:void(0);" id="delivery-audit-auditDeliveryRejectbill"
               class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-audit'" title="审核">审核</a>
        </security:authorize>
        <security:authorize url="/delivery/importDeliveryRejectbillPage.do">
            <a href="javaScript:void(0);" id="delivery-in-inDeliveryRejectbill" class="easyui-linkbutton button-list"
               data-options="plain:true,iconCls:'button-import'" title="导入">导入</a>
        </security:authorize>
        <security:authorize url="/delivery/exportDeliveryRejectbillPage.do">
            <a href="javaScript:void(0);" id="delivery-out-outDeliveryRejectbill" class="easyui-linkbutton button-list"
               data-options="plain:true,iconCls:'button-export'" title="导出">导出</a>
        </security:authorize>
        <security:authorize url="/delivery/exportDeliveryRejectbillPage.do">
            <a href="javaScript:void(0);" id="delivery-orderout-orderoutDeliveryRejectbill"
               class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-export'" title="按单据导出">按单据导出</a>
        </security:authorize>
        <security:authorize url="/delivery/previewDeliveryRejectbillPage.do">
            <a href="javaScript:void(0);" id="btn-previewDeliveryRejectbill" class="easyui-linkbutton button-list"
               data-options="plain:true,iconCls:'button-preview'" title="打印预览">打印预览</a>
        </security:authorize>
        <security:authorize url="/delivery/printDeliveryRejectbillPage.do">
            <a href="javaScript:void(0);" id="btn-printDeliveryRejectbill" class="easyui-linkbutton button-list"
               data-options="plain:true,iconCls:'button-print'" title="打印">打印</a>
        </security:authorize>
    </div>
    <div>
        <form action="" id="delivery-form-showDeliveryRejectbillList" method="post">
            <table class="querytable">
                <tr>
                    <td>编号：</td>
                    <td><input type="text" id="delivery-id-deliveryRejectbillSourceQueryPage" name="id"
                               style="width: 220px;"/></td>
                    <td>客户：</td>
                    <td class="len165"><input type="text" id="delivery-customerid-deliveryRejectbillSourceQueryPage"
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
                    <td class="len165"><input type="text" id="delivery-storageid-deliveryRejectbillSourceQueryPage"
                                              style="width:165px;" name="storageid"/></td>
                    <td>商品名称：</td>
                    <td class="len165"><input type="text" id="delivery-goodsid-deliveryRejectbillSourceQueryPage"
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
                        <input type="text" id="delivery-sourceid-deliveryRejectbillSourceQueryPage" style="width:160px;"
                               name="sourceid"/>
                    </td>
                    <td colspan="2">
                        <a href="javaScript:void(0);" id="delivery-queay-deliveryRejectbill" class="button-qr">查询</a>
                        <a href="javaScript:void(0);" id="delivery-reload-deliveryRejectbill" class="button-qr">重置</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<script type="text/javascript">
    var initQueryJSON = $("#delivery-form-showDeliveryRejectbillList").serializeJSON();
    //数据列表
    var deliveryRejectbillJson = $("#delivery-table-showDeliveryRejectbillList").createGridColumnLoad({
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
            {field: 'sourceid', title: '客户单号', width: 140, sortable: true},
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
        $("#delivery-table-showDeliveryRejectbillList").datagrid({
            authority: deliveryRejectbillJson,
            frozenColumns: deliveryRejectbillJson.frozen,
            columns: deliveryRejectbillJson.common,
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
            url: 'delivery/showDeliveryRejectbillList.do',
            queryParams: initQueryJSON,
            toolbar: '#delivery-query-showDeliveryRejectbillList',
            onDblClickRow: function (rowIndex, rowData) {
                top.addOrUpdateTab('delivery/showDeliveryRejectbillEditPage.do?id=' + rowData.id, "代配送销售退单查看");
            }
        }).datagrid("columnMoving");
        //回车时间
        controlQueryAndResetByKey("delivery-queay-deliveryRejectbill", "delivery-reload-deliveryRejectbill");
        //查询
        $("#delivery-queay-deliveryRejectbill").click(function () {
            var queryJSON = $("#delivery-form-showDeliveryRejectbillList").serializeJSON();
            $("#delivery-table-showDeliveryRejectbillList").datagrid("load", queryJSON);
        });
        //新增
        $("#delivery-add-addDeliveryRejectbill").click(function () {
            var url = "delivery/showDeliveryRejectbillAddPage.do";
            top.addTab(url, '新增代配送销售退单');
        });
        //重置
        $("#delivery-reload-deliveryRejectbill").click(function () {
            $("#delivery-supplierid-deliveryRejectbillSourceQueryPage").supplierWidget("clear");
            $("#delivery-storageid-deliveryRejectbillSourceQueryPage").widget("clear");
            $("#delivery-goodsid-deliveryRejectbillSourceQueryPage").goodsWidget("clear");
            $("#delivery-form-showDeliveryRejectbillList").form("reset");
            var queryJSON = $("#delivery-form-showDeliveryRejectbillList").serializeJSON();
            $("#delivery-table-showDeliveryRejectbillList").datagrid('load', queryJSON);
        });
        //修改
        $("#delivery-edit-editDeliveryRejectbill").click(function () {
            var con = $("#delivery-table-showDeliveryRejectbillList").datagrid('getSelected');
            if (null == con) {
                $.messager.alert("提醒", "请选择代配送销售退单!");
                return false
            }
            var url = "delivery/showDeliveryRejectbillEditPage.do?id=" + con.id;
            top.addTab(url, '修改代配送销售退单');
        });
        //导入
        $("#delivery-in-inDeliveryRejectbill").Excel('import', {
            url: 'delivery/importRejectbill.do',
            type: 'importUserdefined',
            onClose: function () { //导入成功后窗口关闭时操作，
                $("#delivery-table-showDeliveryRejectbillList").datagrid('reload');	//更新列表
            }
        });
        //导出
        $("#delivery-out-outDeliveryRejectbill").Excel('export', {
            queryForm: "#delivery-form-showDeliveryRejectbillList", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
            datagrid: "#delivery-table-showDeliveryRejectbillList",
            type: 'exportUserdefined',
            name: '代配送销售退单列表',
            url: 'delivery/exportRejectbill.do'
        });
        //按单据导出
        $("#delivery-orderout-orderoutDeliveryRejectbill").Excel('export', {
            queryForm: "#delivery-form-showDeliveryRejectbillList", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
            datagrid: "#delivery-table-showDeliveryRejectbillList",
            type: 'exportUserdefined',
            name: '代配送销售退单列表',
            url: 'delivery/exportRejectbillList.do'
        });
        //批量审核
        $("#delivery-audit-auditDeliveryRejectbill").click(function () {
            var rows = $("#delivery-table-showDeliveryRejectbillList").datagrid('getChecked');
            if (rows.length == 0) {
                $.messager.alert("提醒", "请选择代配送销售退单!");
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
                        url: 'delivery/auditsDeliveryRejectbill.do?ids=' + ids,
                        type: 'post',
                        dataType: 'json',
                        async: false,
                        success: function (json) {
                            $.messager.alert("提醒", json.msg);
                            var queryJSON = $("#delivery-form-showDeliveryRejectbillList").serializeJSON();
                            $("#delivery-table-showDeliveryRejectbillList").datagrid("load", queryJSON);
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
    $("#delivery-customerid-deliveryRejectbillSourceQueryPage").customerWidget({});
    //仓库选择
    $("#delivery-storageid-deliveryRejectbillSourceQueryPage").widget({
        referwid: 'RL_T_BASE_STORAGE_INFO',
        width: 160,
        col: 'storageid',
        singleSelect: true,
        initValue: ''
    });
    //商品名称选择
    $("#delivery-goodsid-deliveryRejectbillSourceQueryPage").goodsWidget({
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
            id: "listPage-deliveryrejectbill-dialog-print",
            code: "delivery_rejectbill",
            tableId: "delivery-table-showDeliveryRejectbillList",
            url_preview: "print/delivery/rejectbillPrintView.do",
            url_print: "print/delivery/rejectbillPrint.do",
            btnPreview: "btn-previewDeliveryRejectbill",
            btnPrint: "btn-printDeliveryRejectbill"
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>