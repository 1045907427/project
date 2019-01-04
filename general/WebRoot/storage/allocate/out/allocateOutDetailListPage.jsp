<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>调拨单明细列表查询页面</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false">
        <div class="buttonBG">
            <security:authorize url="/storage/allocateOutDetailExport.do">
                <a href="javaScript:void(0);" id="storage-buttons-allocateOutPage" class="easyui-linkbutton"
                   iconCls="button-export" plain="true" title="导出">导出</a>
            </security:authorize>
            <security:authorize url="/storage/allocateOutJournalPrintView.do">
                <a href="javaScript:void(0);" id="storage-buttons-allocateOutPreview" class="easyui-linkbutton"
                   iconCls="button-preview" plain="true" title="打印预览">打印预览</a>
            </security:authorize>
            <security:authorize url="/storage/allocateOutJournalPrint.do">
                <a href="javaScript:void(0);" id="storage-buttons-allocateOutPrint" class="easyui-linkbutton"
                   iconCls="button-print" plain="true" title="打印">打印</a>
            </security:authorize>
        </div>
    </div>
    <div data-options="region:'center'">
        <table id="storage-datagrid-allocateOutPage"></table>
        <div id="storage-datagrid-toolbar-allocateOutPage" style="padding:2px;height:auto">
            <form action="" id="storage-form-query-allocateOutPage" method="post">
                <table class="querytable">
                    <tr>
                        <td>业务日期:</td>
                        <td><input type="text" id="storage-query-businessdate" name="businessdate1" style="width:100px;"
                                   class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${today}"/> 到
                            <input id="storage-query-businessdate1" type="text" name="businessdate2" class="Wdate"
                                   style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"
                                   value="${today}"/></td>
                        <td>调出仓库:</td>
                        <td><input id="storage-query-outstorageid" type="text" name="outstorageid"/></td>
                        <td>编号:</td>
                        <td><input type="text" name="id" style="width: 165px;"/></td>
                        <td>品牌:</td>
                        <td><input id="storage-query-brandid" type="text" name="brandid"/></td>
                    </tr>
                    <tr>
                        <td>商品名称:</td>
                        <td><input id="storage-query-goodsid" type="text" name="goodsid" style="width: 225px;"/></td>
                        <td>调入仓库:</td>
                        <td><input id="storage-query-enterstorageid" type="text" name="enterstorageid"/></td>
                        <td>调拨类型</td>
                        <td>
                            <select name="billtype" style="width:165px;">
                                <option selected></option>
                                <option value="1">成本调拨</option>
                                <option value="2">异价调拨</option>
                            </select>
                        </td>
                        <td colspan="2">
                            <a href="javaScript:void(0);" id="storage-queay-allocateOut" class="button-qr">查询</a>
                            <a href="javaScript:void(0);" id="storage-reload-allocateOut" class="button-qr">重置</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
    var initQueryJSON = $("#storage-form-query-allocateOutPage").serializeJSON();
    $(function () {
        var allocateOutJson = $("#storage-datagrid-allocateOutPage").createGridColumnLoad({
            frozenCol: [[]],
            commonCol: [[
                {field: 'id', title: '编号', width: 125, sortable: true},
                {field: 'businessdate', title: '业务日期', width: 80, sortable: true},
                {
                    field: 'outstorageid', title: '调出仓库', width: 80, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.outstoragename;
                    }
                },
                {
                    field: 'enterstorageid', title: '调入仓库', width: 80, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.enterstoragename;
                    }
                },
                {field: 'goodsid', title: '商品编码', width: 60, sortable: true},
                {field: 'goodsname', title: '商品名称', width: 130, sortable: true},
                {field: 'barcode', title: '条形码', width: 90, sortable: true},
                {field: 'brandname', title: '品牌名称', width: 80, sortable: true},
                {field: 'model', title: '规格参数', width: 60, sortable: true, hidden: true},
                {field: 'unitname', title: '单位', width: 50, sortable: true},
                {field: 'auxunitname', title: '辅单位', width: 50, sortable: true},
                {field: 'auxunitnumdetail', title: '辅数量', width: 60, sortable: true, align: 'right'},
                {
                    field: 'unitnum', title: '数量', width: 50, sortable: true, align: 'right',
                    formatter: function (value, row, index) {
                        return formatterBigNumNoLen(value);
                    }
                },
                {
                    field: 'taxprice', title: '单价', width: 60, sortable: true, align: 'right',
                    formatter: function (value, row, index) {
                        if (value != "") {
                            return formatterMoney(value);
                        }
                    }
                },
                {
                    field: 'taxamount', title: '金额', resizable: true, sortable: true, align: 'right',
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'notaxamount', title: '未税金额', resizable: true, sortable: true, align: 'right',
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'diffamount', title: '调拨差额', resizable: true, sortable: true, align: 'right',
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {field: 'remark', title: '备注', width: 80, sortable: true}
            ]]
        });
        $("#storage-datagrid-allocateOutPage").datagrid({
            authority: allocateOutJson,
            frozenColumns: allocateOutJson.frozen,
            columns: allocateOutJson.common,
            fit: true,
            title: "",
            method: 'post',
            rownumbers: true,
            pagination: true,
            pageSize: 100,
            idField: 'id',
            singleSelect: true,
            url: 'storage/showAllocateOutDetailListQuery.do',
            queryParams: initQueryJSON,
            toolbar: '#storage-datagrid-toolbar-allocateOutPage',
            onDblClickRow: function (rowIndex, rowData) {
                if (rowData.id != "") {
                    top.addOrUpdateTab('storage/showAllocateOutViewPage.do?id=' + rowData.id, "调拨单");
                }
            }
        }).datagrid("columnMoving");
        $("#storage-query-goodsid").goodsWidget({});
        $("#storage-query-enterstorageid").widget({
            name: 't_storage_allocate_out',
            width: 150,
            col: 'enterstorageid',
            view: true,
            singleSelect: true
        });
        $("#storage-query-outstorageid").widget({
            name: 't_storage_allocate_out',
            width: 150,
            col: 'outstorageid',
            view: true,
            singleSelect: true
        });
        $("#storage-query-brandid").widget({
           referwid:'RL_T_BASE_GOODS_BRAND',
            width: 150,
            singleSelect: false
        });

        //回车事件
        controlQueryAndResetByKey("storage-queay-allocateOut", "storage-reload-allocateOut");

        //查询
        $("#storage-queay-allocateOut").click(function () {
            //把form表单的name序列化成JSON对象
            var queryJSON = $("#storage-form-query-allocateOutPage").serializeJSON();
            $("#storage-datagrid-allocateOutPage").datagrid("load", queryJSON);
        });
        //重置
        $("#storage-reload-allocateOut").click(function () {
            $("#storage-query-goodsid").goodsWidget("clear");
            $("#storage-query-outstorageid").widget("clear");
            $("#storage-query-enterstorageid").widget("clear");
            $("#storage-query-brandid").widget("clear");
            $("#storage-form-query-allocateOutPage")[0].reset();
            var queryJSON = $("#storage-form-query-allocateOutPage").serializeJSON();
            $("#storage-datagrid-allocateOutPage").datagrid("load", queryJSON);
        });

        $("#storage-buttons-allocateOutPage").Excel('export', {
            queryForm: "#storage-form-query-allocateOutPage", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
            type: 'exportUserdefined',
            name: '调拨单明细列表',
            url: 'storage/exportAllocateOutData.do'
        });
    });
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "allocateoutjournal-dialog-print",
            code: "storage_allocateoutjournal",
            queryForm: "storage-form-query-allocateOutPage",
            url_preview: "print/storage/allocateOutJournalPrintView.do",
            url_print: "print/storage/allocateOutJournalPrint.do",
            btnPreview: "storage-buttons-allocateOutPreview",
            btnPrint: "storage-buttons-allocateOutPrint",
            printlimit: "${printlimit}",
            getData: function (tableId, printParam) {
                var businessdate1 = $("#storage-query-businessdate1").val();
                var businessdate2 = $("#storage-query-businessdate2").val();
                if (businessdate1 == "" || businessdate2 == "") {
                    $.messager.alert("提醒", '请输入业务日期时间段以便打印');
                    return false;
                }
                return true;
            }
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
