<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>盘点单列表页面</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="storage-buttons-checkListPage"></div>
    </div>
    <div data-options="region:'center'">
        <table id="storage-datagrid-checkListPage"></table>
        <div id="storage-datagrid-toolbar-checkListPage" style="padding:2px;height:auto">
            <form action="" id="storage-form-query-checkListPage" method="post">
                <table class="querytable">
                    <tr>
                        <td>编&nbsp;&nbsp;号:</td>
                        <td><input type="text" name="id" style="width: 225px;"/></td>
                        <td>盘点单号:</td>
                        <td><input type="text" name="sourceid" style="width: 180px;"/></td>
                        <td>盘点仓库:</td>
                        <td>
                            <input type="text" id="storage-checkList-storageid" name="storageid"/>
                        </td>
                    </tr>
                    <tr>
                        <td>业务日期:</td>
                        <td><input type="text" name="businessdate1" style="width:100px;" class="Wdate" value="${firstday }"
                                   onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})"/>
                            到 <input type="text" name="businessdate2" class="Wdate" style="width:100px;" value="${today }"
                                     onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})"/>
                        </td>
                        <td>第几次:</td>
                        <td>
                            <input type="text" class="easyui-numberbox" name="checkno" style="width: 180px;"/>
                        </td>
                        <td>状&nbsp;&nbsp;态:</td>
                        <td>
                            <select name="status" style="width:150px;">
                                <option></option>
                                <option value="2" selected="selected">保存</option>
                                <option value="3">审核通过</option>
                                <option value="4">已关闭</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>商品名称:</td>
                        <td>
                            <input id="storage-checkList-goodsid" type="text" style="width:225px;" name="goodsid"/>
                        </td>
                        <td>盘点人:</td>
                        <td>
                            <input id="storage-checkList-checkuserid" type="text" style="width:180px;"
                                   name="checkuserid"/>
                        </td>
                        <td colspan="2" style="padding-left: 15px">
                            <a href="javaScript:void(0);" id="storage-queay-checkList" class="button-qr">查询</a>
                            <a href="javaScript:void(0);" id="storage-reload-checkList" class="button-qr">重置</a>
                            <span id="storage-query-advanced-checkList"></span>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>
<form id="storage-form-export-checkListPage">
    <input id="storage-export-id" type="hidden" name="id"/>
</form>
<div id="storage-dialog-dynamicCheckList"></div>
<script type="text/javascript">
    var initQueryJSON = $("#storage-form-query-checkListPage").serializeJSON();
    $(function () {
        //按钮
        $("#storage-buttons-checkListPage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/storage/checkListAddPage.do">
                {
                    type: 'button-add',
                    handler: function () {
                        top.addOrUpdateTab('storage/showCheckListAddPage.do', "盘点单新增");
                    },
                    url: '/storage/showCheckListAddPage.do'
                },
                </security:authorize>
                <security:authorize url="/storage/showCheckListViewPage.do">
                {
                    type: 'button-view',
                    handler: function () {
                        var con = $("#storage-datagrid-checkListPage").datagrid('getSelected');
                        if (con == null) {
                            $.messager.alert("提醒", "请选择一条记录");
                            return false;
                        }
                        top.addOrUpdateTab('storage/showCheckListEditPage.do?id=' + con.id, "盘点单查看");
                    },
                    url: '/storage/showCheckListViewPage.do'
                },
                </security:authorize>
                <security:authorize url="/storage/checkListExport.do">
                {
                    type: 'button-export',
                    attr: {
                        queryForm: "#storage-form-export-checkListPage", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
                        type: 'exportUserdefined',
                        name: '盘点单',
                        url: 'storage/exportCheckListDataOfTheStyle.do',
                        onBeforeExport: function () {
                            var row = $("#storage-datagrid-checkListPage").datagrid("getSelected");
                            if (row == null) {
                                $.messager.alert("提醒", "请选择盘点单");
                                return false;
                            } else {
                                $("#storage-export-id").val(row.id);
                            }
                        }
                    }
                },
                </security:authorize>
                {
                    type: 'button-commonquery',
                    attr: {
                        //查询针对的表
                        name: 't_storage_checklist',
                        //查询针对的表格id
                        datagrid: 'storage-datagrid-checkListPage',
                        plain: true
                    }
                },
                {}
            ],
            buttons: [
                {},
                <security:authorize url="/storage/addDynamicCheckList.do">
                {
                    id: 'dynamicCheckList',
                    name: '动态盘单',
                    iconCls: 'button-edit',
                    handler: function () {
                        $('#storage-dialog-dynamicCheckList').dialog({
                            title: '申请动态盘点页面',
                            width: 500,
                            height: 330,
                            closed: false,
                            cache: false,
                            href: 'storage/showDynamicCheckListPage.do',
                            modal: true
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/checkListPrintBtn.do">
                {
                    id: 'menuButton',
                    type: 'menu',
                    name: '打印',
                    iconCls: 'button-print',
                    button: [
                        <security:authorize url="/storage/checkListPrintViewBtn.do">
                        {
                            id: 'button-printview-checklist',
                            name: '盘点单打印预览',
                            iconCls: 'button-preview',
                            handler: function () {
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/storage/checkListPrintDoneBtn.do">
                        {
                            id: 'button-print-checklist',
                            name: '盘点单打印',
                            iconCls: 'button-print',
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
            model: 'bill',
            type: 'list',
            tname: 't_storage_checklist'
        });

        var checkListJson = $("#storage-datagrid-checkListPage").createGridColumnLoad({
            name: 't_storage_checklist',
            frozenCol: [[]],
            commonCol: [[
                {field: 'id', title: '编号', width: 130, sortable: true},
                {field: 'sourceid', title: '盘点单号', width: 130, sortable: true},
                {field: 'businessdate', title: '业务日期', width: 80, sortable: true},
                {
                    field: 'storageid', title: '所属仓库', width: 80, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.storagename;
                    }
                },
                {
                    field: 'checkuserid', title: '盘点人', width: 60, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.checkusername;
                    }
                },
                {
                    field: 'createtype', title: '生成方式', width: 70, sortable: true, hidden: true,
                    formatter: function (value, rowData, rowIndex) {
                        return getSysCodeName("createtype", value);
                    }
                },
                {field: 'checkno', title: '第几次盘点', width: 80, align: 'right', sortable: true},
                {
                    field: 'checknum', title: '盘点条数', width: 70, sortable: true, align: 'right',
                    formatter: function (value, rowData, rowIndex) {
                        return value + "条";
                    }
                },
                {
                    field: 'truenum', title: '盘点正确数', width: 80, sortable: true, align: 'right',
                    formatter: function (value, rowData, rowIndex) {
                        return value + "条";
                    }
                },
                {
                    field: 'isfinish', title: '盘点状态', width: 70, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        if (value == '1') {
                            return "盘点完成";
                        } else if (value == '0') {
                            return "盘点未完成";
                        }
                    }
                },
                {
                    field: 'istrue', title: '是否盘点正确', width: 80, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        if (value == '1') {
                            return "是";
                        } else if (value == '0') {
                            return "否";
                        }
                    }
                },
                {field: 'brandname', title: '品牌名称', width: 80, isShow: true},
                {
                    field: 'status', title: '状态', width: 80, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return getSysCodeName("status", value);
                    }
                },
                {field: 'printtimes', title: '打印次数', width: 80, sortable: true},
                {field: 'addusername', title: '制单人', width: 80, sortable: true},
                {field: 'addtime', title: '制单时间', width: 120, sortable: true},
                {field: 'auditusername', title: '审核人', width: 80, sortable: true, hidden: true},
                {field: 'audittime', title: '审核时间', width: 120, sortable: true, hidden: true},
                {field: 'remark', title: '备注', width: 80, sortable: true}
            ]]
        });
        $("#storage-datagrid-checkListPage").datagrid({
            authority: checkListJson,
            frozenColumns: checkListJson.frozen,
            columns: checkListJson.common,
            fit: true,
            method: 'post',
            rownumbers: true,
            pagination: true,
            idField: 'id',
            sortName: 'id',
            sortOrder: 'desc',
            singleSelect: true,
            sortName: 'addtime',
            sortOrder: 'desc',
            pageSize: 20,
            url: 'storage/showCheckListData.do',
            queryParams: initQueryJSON,
            toolbar: '#storage-datagrid-toolbar-checkListPage',
            onDblClickRow: function (rowIndex, rowData) {
                top.addOrUpdateTab('storage/showCheckListEditPage.do?id=' + rowData.id, "盘点单查看");
            }
        }).datagrid("columnMoving");

        $("#storage-checkList-goodsid").goodsWidget({});
        $("#storage-checkList-checkuserid").widget({
            referwid: 'RL_T_BASE_PERSONNEL_STORAGER',
            width: 180,
            singleSelect: true
        });
        $("#storage-checkList-storageid").widget({
            width: 150,
            referwid: 'RL_T_BASE_STORAGE_INFO',
            singleSelect: true,
            onlyLeafCheck: false
        });
        //查询
        $("#storage-queay-checkList").click(function () {
            var queryJSON = $("#storage-form-query-checkListPage").serializeJSON();
            $("#storage-datagrid-checkListPage").datagrid("load", queryJSON);
        });
        //重置
        $("#storage-reload-checkList").click(function () {
            $("#storage-checkList-storageid").widget('clear');
            $("#storage-checkList-goodsid").goodsWidget("clear");
            $("#storage-checkList-checkuserid").widget("clear");
            $("#storage-form-query-checkListPage").form("reset");
            var queryJSON = $("#storage-form-query-checkListPage").serializeJSON();
            $("#storage-datagrid-checkListPage").datagrid("load", queryJSON);
        });
    });
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "checklist-dialog-print",
            code: "storage_checklist",
            url_preview: "print/storage/checkListPrintView.do",
            url_print: "print/storage/checkListPrint.do",
            btnPreview: "button-printview-checklist",
            btnPrint: "button-print-checklist",
            printlimit: "${printlimit}",
            getData: function (tableId, printParam) {
                var data = $("#storage-datagrid-checkListPage").datagrid("getSelected");
                if (data == null || data.length == 0) {
                    $.messager.alert("提醒", "请选择至少一条记录");
                    return false;
                }
                printParam.id = data.id;
                if (data.printtimes > 0)
                    printParam.printIds = [data.id];
                return true;
            },
            onPrintSuccess:function(option) {
                var $grid = $("#storage-datagrid-checkListPage");
                var data = $grid.datagrid("getSelected");
                if (data.printtimes && !isNaN(data.printtimes)) {
                    data.printtimes = data.printtimes + 1;
                } else {
                    data.printtimes = 1;
                }
                var rowIndex = $grid.datagrid('getRowIndex', data.id);
                $grid.datagrid('updateRow', {index: rowIndex, row: data});
            }
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
