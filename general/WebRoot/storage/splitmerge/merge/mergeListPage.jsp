<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>组装单列表页面</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>
<body>
<style type="text/css">
    .len70 {
        width: 70px;
    }
    .len90 {
        width: 90px;
    }
    .len200 {
        width: 200px;
    }
    .len220 {
        width: 220px;
    }
</style>
<div class="easyui-layout" data-options="fit:true" style="padding: 0px;">
    <div data-options="region:'center'">
        <div id="storage-toolbar-mergeListPage" style="padding:0px;height:auto">
            <div class="buttonBG" id="storage-buttons-mergeListPage"></div>
            <form id="storage-form-mergeListPage">
                <input type="hidden" id="storage-billtype-mergeListPage" name="billtype" value="2"/>
                <table>
                    <tr>
                        <td class="len70 left">组装单号：</td>
                        <td class="len200 left"><input type="text" id="storage-id-mergeListPage" name="id"
                                                       class="len180"></td>
                        <td class="len70 right">业务日期：</td>
                        <td class="len200 left">
                            <input type="text" class="len90 Wdate" id="storage-startdate-mergeListPage" name="startdate"
                                   onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})">
                            到<input type="text" class="len90 Wdate" id="storage-enddate-mergeListPage" name="enddate"
                                    onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})">
                        </td>
                        <td class="len70 right">单据状态：</td>
                        <td class="len150 left">
                            <select id="storage-status-mergeListPage" name="status" class="len150">
                                <option></option>
                                <option value="2" selected="selected">保存</option>
                                <option value="3">已审核</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <!--
                        <td class="left">商　　品：</td>
                        <td class="left"><input type="text" id="storage-goodsid-mergeListPage" name="goodsid" class="len180"></td>
                        <td class="right">供应商：</td>
                        <td class="left"><input type="text" id="storage-supplierid-mergeListPage" name="supplierid" style="width: 192px;"></td>
                        -->
                        <td colspan="6" align="right">
                            <a href="javascript:void(0);" id="storage-queay-mergeListPage" class="button-qr">查询</a>
                            <a href="javaScript:void(0);" id="storage-reset-mergeListPage" class="button-qr">重置</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <table id="storage-datagrid-mergeListPage"></table>
    </div>
</div>
<script type="text/javascript">
    $(function () {
        // 商品
        $('#storage-goodsid-mergeListPage').goodsWidget({});
        // 供应商
        $('#storage-supplierid-mergeListPage').supplierWidget({});
        // 按钮
        $('#storage-buttons-mergeListPage').buttonWidget({
            initButton: [
                <security:authorize url="/storage/splitmerge/addMerge.do">
                {
                    type: 'button-add',
                    handler: function () {
                        top.addTab('storage/splitmerge/mergePage.do?type=add', '组装单新增');
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/splitmerge/auditMerge.do">
                /*{
                 type: 'button-audit',
                 handler: function() {
                 var row = $('#storage-datagrid-mergeListPage').datagrid('getSelected');
                 if(row == null) {
                 $.messager.alert('提醒', '请选择要审核的记录！');
                 return false;
                 }
                 loading('审核中...');
                 $.ajax({
                 type: 'post',
                 url: 'storage/splitmerge/auditMerge.do',
                 data: {id: row.id},
                 dataType: 'json',
                 success: function(json) {
                 loaded();
                 if(json.flag) {
                 $.messager.alert('提醒', '审核成功。');
                 $('#storage-datagrid-mergeListPage').datagrid('reload');
                 return true;
                 }
                 $.messager.alert('提醒', json.msg || '审核失败！');
                 return true;
                 }
                 });
                 }
                 },*/
                </security:authorize>
                <security:authorize url="/storage/splitmerge/deleteMerge.do">
                {
                    type: 'button-delete',
                    handler: function () {
                        var row = $('#storage-datagrid-mergeListPage').datagrid('getSelected');
                        if (row == null) {
                            $.messager.alert('提醒', '请选择要删除的记录！');
                            return false;
                        }
                        $.messager.confirm('提醒', '是否删除该记录？', function (r) {
                            if (r) {
                                loading('删除中...');
                                $.ajax({
                                    type: 'post',
                                    url: 'storage/splitmerge/deleteMerge.do',
                                    data: {id: row.id},
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag) {
                                            $.messager.alert('提醒', '删除成功。');
                                            $('#storage-datagrid-mergeListPage').datagrid('reload');
                                            return true;
                                        }
                                        $.messager.alert('提醒', json.msg || '删除失败！');
                                        return true;
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/splitmerge/previewMerge.do">
                {
                    type: 'button-preview',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/splitmerge/printMerge.do">
                {
                    type: 'button-print',
                    handler: function () {
                    }
                },
                </security:authorize>
                {}
            ],
            buttons: [{}],
            model: 'bill',
            type: 'list'
        });
        // datagrid项目
        var cols = $('#storage-datagrid-mergeListPage').createGridColumnLoad({
            frozenCol: [[
                {field: 'id', title: '单据编号', width: 120, hidden: false}
            ]],
            commonCol: [[
                {field: 'businessdate', title: '业务日期', width: 80},
                /*{field: 'supplierid', title: '供应商编码', width: 80},
                 {field: 'suppliername', title: '供应商名称', width: 180},*/
                {field: 'goodsid', title: '商品编码', width: 80},
                {field: 'goodsname', title: '商品名称', width: 250},
                {
                    field: 'status', title: '状态', width: 70,
                    formatter: function (value, row, index) {
                        if ((value || '') == '') {
                            return '';
                        }
                        return getSysCodeName('status', value);
                    }
                },
                {field: 'addusername', title: '制单人', width: 65, sortable: true},
                {field: 'addtime', title: '制单时间', width: 130, sortable: true},
                {field: 'remark', title: '备注', width: 250},
                {field: 'printtimes', title: '打印次数', width: 50, hidden: true}
            ]]
        });
        // datagrid
        $('#storage-datagrid-mergeListPage').datagrid({
            authority: cols,
            frozenColumns: cols.frozen,
            columns: cols.common,
            fit: true,
            title: '',
            method: 'post',
            rownumbers: true,
            pagination: true,
            idField: 'id',
            singleSelect: true,
            url: 'storage/splitmerge/selectSplitMergeList.do',
            queryParams: $('#storage-form-mergeListPage').serializeJSON(),
            toolbar: '#storage-toolbar-mergeListPage',
            onDblClickRow: function (index, row) {
                if (row.status == '2') {
                    <security:authorize url="/storage/splitmerge/editMerge.do">
                    top.addTab('storage/splitmerge/mergePage.do?type=edit&id=' + row.id, '组装单查看');
                    </security:authorize>
                } else if (row.status == '3') {
                    top.addTab('storage/splitmerge/mergePage.do?type=view&id=' + row.id, '组装单查看');
                }
            }
        }).datagrid("columnMoving");
        // 查询
        $('#storage-queay-mergeListPage').on('click', function (e) {
            var param = $('#storage-form-mergeListPage').serializeJSON();
            $("#storage-datagrid-mergeListPage").datagrid({
                url: 'storage/splitmerge/selectSplitMergeList.do',
                queryParams: param
            });
        });
        // 重置
        $('#storage-reset-mergeListPage').on('click', function (e) {
            $('#storage-goodsid-mergeListPage').goodsWidget('clear');
            $('#storage-supplierid-mergeListPage').supplierWidget('clear');
            $('#storage-form-mergeListPage')[0].reset();
            var param = $('#storage-form-mergeListPage').serializeJSON();
            $("#storage-datagrid-mergeListPage").datagrid({
                url: 'storage/splitmerge/selectSplitMergeList.do',
                queryParams: param
            });
        });
    });
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "merge-dialog-print",
            code: "storage_merge",
            tableId: "storage-datagrid-mergeListPage",
            url_preview: "print/storage/splitmerge/splitMergePrintView.do",
            url_print: "print/storage/splitmerge/splitMergePrint.do",
            btnPreview: "button-preview",
            btnPrint: "button-print",
            getData: function(tableId, printParam) {
                var data = $("#"+tableId).datagrid("getSelections");
                if (data.length == 0) {
                    $.messager.alert('提醒', '请选择至少一条记录！');
                    return false;
                }
                return data;
            }
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
