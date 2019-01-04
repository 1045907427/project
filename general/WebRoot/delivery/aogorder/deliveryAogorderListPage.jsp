<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>代配送采购单</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
    <script type="text/javascript" src="js/jquery.excel.js"></script>
</head>
<body>
<table id="delivery-table-showDeliveryAogorderList"></table>
<div id="delivery-query-showDeliveryAogorderList" style="padding:0px;height:auto">
    <div class="buttonBG">
        <security:authorize url="/delivery/addDeliveryAogorderPage.do">
            <a href="javaScript:void(0);" id="delivery-add-addDeliveryAogorder" class="easyui-linkbutton button-list"
               data-options="plain:true,iconCls:'button-add'" title="新增">新增</a>
        </security:authorize>
        <security:authorize url="/delivery/editDeliveryAogorderPage.do">
            <a href="javaScript:void(0);" id="delivery-edit-editDeliveryAogorder" class="easyui-linkbutton button-list"
               data-options="plain:true,iconCls:'button-edit'" title="修改">修改</a>
        </security:authorize>
        <security:authorize url="/delivery/auditDeliveryAogorder.do">
            <a href="javaScript:void(0);" id="delivery-audit-auditDeliveryAogorder"
               class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-audit'" title="审核">审核</a>
        </security:authorize>
        <security:authorize url="/delivery/importDeliveryAogorderPage.do">
            <a href="javaScript:void(0);" id="delivery-in-inDeliveryAogorder" class="easyui-linkbutton button-list"
               data-options="plain:true,iconCls:'button-import'" title="导入">导入</a>
        </security:authorize>
        <security:authorize url="/delivery/exportDeliveryAogorderPage.do">
            <a href="javaScript:void(0);" id="delivery-out-outDeliveryAogorder" class="easyui-linkbutton button-list"
               data-options="plain:true,iconCls:'button-export'" title="导出">导出</a>
        </security:authorize>
        <security:authorize url="/delivery/previewDeliveryAogorderPage.do">
            <a href="javaScript:void(0);" id="delivery-preview-previewDeliveryAogorder"
               class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-preview'" title="打印预览">打印预览</a>
        </security:authorize>
        <security:authorize url="/delivery/printDeliveryAogorderPage.do">
            <a href="javaScript:void(0);" id="delivery-print-printDeliveryAogorder"
               class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-print'" title="打印">打印</a>
        </security:authorize>
    </div>
    <div>
        <form action="" id="delivery-form-showDeliveryAogorderList" method="post">
            <table class="querytable">
                <tr>
                    <td>编号：</td>
                    <td><input type="text" id="delivery-id-deliveryAogorderSourceQueryPage" name="id"
                               style="width:225px;"/></td>
                    <td>供应商：</td>
                    <td class="len165"><input type="text" id="delivery-supplierid-deliveryAogorderSourceQueryPage"
                                              name="supplierid" style="width:160px;"/></td>
                    <td class="len30 right">状态：</td>
                    <td>
                        <select id="delivery-status-deliveryAogorderSourceQueryPage" name="status" style="width:150px;">
                            <option></option>
                            <option value="2" selected="selected">保存</option>
                            <option value="3">审核通过</option>
                            <option value="4">关闭</option>
                        </select>
                    </td>
                    <td class="len30 right">打印状态：</td>
                    <td>
                        <select id="delivery-printstatus-deliveryAogorderSourceQueryPage" name="printstatus"
                                style="width:140px;">
                            <option selected="selected"></option>
                            <option value="1">未打印</option>
                            <option value="2">已打印</option>
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
                    <td class="len165"><input type="text" id="delivery-storageid-deliveryAogorderSourceQueryPage"
                                              style="width:165px;" name="storageid"/></td>
                    <td>商品名称：</td>
                    <td class="len165"><input type="text" id="delivery-goodsid-deliveryAogorderSourceQueryPage"
                                              style="width:150px;" name="goodsid"/></td>
                    <td colspan="2">
                        <a href="javaScript:void(0);" id="delivery-queay-deliveryAogorder" class="button-qr">查询</a>
                        <a href="javaScript:void(0);" id="delivery-reload-deliveryAogorder" class="button-qr">重置</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>

<script type="text/javascript">
    var initQueryJSON = $("#delivery-form-showDeliveryAogorderList").serializeJSON();
    //数据列表
    var deliveryAogorderJson = $("#delivery-table-showDeliveryAogorderList").createGridColumnLoad({
        name: '',
        frozenCol: [[
            {field: 'ck', checkbox: true, isShow: true}
        ]],
        commonCol: [[
            {field: 'id', title: '编号', width: 125, sortable: true},
            {field: 'businessdate', title: '业务日期', width: 80, sortable: true},
            {field: 'supplierid', title: '供应商编号', width: 80, sortable: true},
            {field: 'suppliername', title: '供应商', width: 160, sortable: true},
            {field: 'storagename', title: '仓库', width: 80, sortable: true},
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
            {field: 'printtimes', title: '打印次数', width: 80, sortable: true},
            {field: 'remark', title: '备注', width: 80, sortable: true}
        ]]
    });


    $(function () {
        $("#delivery-table-showDeliveryAogorderList").datagrid({
            authority: deliveryAogorderJson,
            frozenColumns: deliveryAogorderJson.frozen,
            columns: deliveryAogorderJson.common,
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
            url: 'delivery/showDeliveryAogorderList.do',
            queryParams: initQueryJSON,
            toolbar: '#delivery-query-showDeliveryAogorderList',
            onDblClickRow: function (rowIndex, rowData) {
                top.addOrUpdateTab('delivery/showDeliveryAogorderEditPage.do?id=' + rowData.id, "代配送采购单查看");
            }
        }).datagrid("columnMoving");
        //回车时间
        controlQueryAndResetByKey("delivery-queay-deliveryAogorder", "delivery-reload-deliveryAogorder");
        //查询
        $("#delivery-queay-deliveryAogorder").click(function () {
            var queryJSON = $("#delivery-form-showDeliveryAogorderList").serializeJSON();
            $("#delivery-table-showDeliveryAogorderList").datagrid("load", queryJSON);
        });
        //重置
        $("#delivery-reload-deliveryAogorder").click(function () {
            $("#delivery-supplierid-deliveryAogorderSourceQueryPage").supplierWidget("clear");
            $("#delivery-storageid-deliveryAogorderSourceQueryPage").widget("clear");
            $("#delivery-goodsid-deliveryAogorderSourceQueryPage").goodsWidget("clear");
            $("#delivery-form-showDeliveryAogorderList").form("reset");
            var queryJSON = $("#delivery-form-showDeliveryAogorderList").serializeJSON();
            $("#delivery-table-showDeliveryAogorderList").datagrid('load', queryJSON);
        });
        //新增
        $("#delivery-add-addDeliveryAogorder").click(function () {
            var url = "delivery/showDeliveryAogorderAddPage.do";
            top.addTab(url, '新增代配送采购单');
        });
        //修改
        $("#delivery-edit-editDeliveryAogorder").click(function () {
            var con = $("#delivery-table-showDeliveryAogorderList").datagrid('getSelected');
            if (null == con) {
                $.messager.alert("提醒", "请选择代配送采购单!");
                return false
            }
            var url = "delivery/showDeliveryAogorderEditPage.do?id=" + con.id;
            top.addTab(url, '修改代配送采购单');
        });
        //导入
        $("#delivery-in-inDeliveryAogorder").Excel('import', {
            url: 'delivery/importAogorder.do',
            type: 'importUserdefined',
            onClose: function () { //导入成功后窗口关闭时操作，
                $("#delivery-table-showDeliveryAogorderList").datagrid('reload');	//更新列表
            }
        });
        //导出
        $("#delivery-out-outDeliveryAogorder").Excel('export', {
            queryForm: "#delivery-form-showDeliveryAogorderList", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
            datagrid: "#delivery-table-showDeliveryAogorderList",
            type: 'exportUserdefined',
            name: '代配送采购单列表',
            url: 'delivery/exportAogorder.do'
        });
        //批量审核
        $("#delivery-audit-auditDeliveryAogorder").click(function () {
            var rows = $("#delivery-table-showDeliveryAogorderList").datagrid('getChecked');
            if (rows.length == 0) {
                $.messager.alert("提醒", "请选择代配送采购单!");
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
                        url: 'delivery/auditsDeliveryAogorder.do?ids=' + ids,
                        type: 'post',
                        dataType: 'json',
                        async: false,
                        success: function (json) {
                            $.messager.alert("提醒", json.msg);
                            var queryJSON = $("#delivery-form-showDeliveryAogorderList").serializeJSON();
                            $("#delivery-table-showDeliveryAogorderList").datagrid("load", queryJSON);

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
    //供应商选择
    $("#delivery-supplierid-deliveryAogorderSourceQueryPage").supplierWidget({
        onSelect: function (data) {
            $("#delivery-deliveryAogorder-storageid").widget('setValue', data.storageid);
        }
    });
    //仓库选择
    $("#delivery-storageid-deliveryAogorderSourceQueryPage").widget({
        referwid: 'RL_T_BASE_STORAGE_INFO',
        width: 160,
        col: 'storageid',
        singleSelect: true,
        initValue: ''
    });
    //商品名称选择
    $("#delivery-goodsid-deliveryAogorderSourceQueryPage").goodsWidget({
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
            id: "listPage-aogorder-dialog-print",
            code: "delivery_aogorder",
            tableId: "delivery-table-showDeliveryAogorderList",
            url_preview: "print/delivery/aogorderPrintView.do",
            url_print: "print/delivery/aogorderPrint.do",
            btnPreview: "delivery-preview-previewDeliveryAogorder",
            btnPrint: "delivery-print-printDeliveryAogorder"
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>