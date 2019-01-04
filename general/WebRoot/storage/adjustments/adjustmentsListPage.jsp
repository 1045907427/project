<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>报溢调账单列表页面</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="storage-buttons-adjustmentsPage"></div>
    </div>
    <div data-options="region:'center'">
        <table id="storage-datagrid-adjustmentsPage"></table>
        <div id="storage-datagrid-toolbar-adjustmentsPage" style="padding:2px;height:auto">
            <form action="" id="storage-form-query-adjustmentsPage" method="post">
                <table class="querytable">
                    <tr>
                        <td>编&nbsp;&nbsp;号:</td>
                        <td><input type="text" name="id" style="width:225px;"/></td>
                        <td>调账仓库:</td>
                        <td><input id="storage-query-storageid" type="text" name="storageid"/></td>
                        <td>状&nbsp;&nbsp;态:</td>
                        <td>
                            <select name="status" style="width:150px;">
                                <option></option>
                                <option value="2" selected="selected">保存</option>
                                <option value="4">关闭</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>业务日期:</td>
                        <td><input type="text" name="businessdate1" style="width:100px;" class="Wdate"
                                   onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/>
                            到 <input type="text" name="businessdate2" class="Wdate" style="width:100px;"
                                     onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/></td>
                        <td>打印状态:</td>
                        <td>
                            <select name="printsign" style="width:150px;">
                                <option></option>
                                <option value="1">未打印</option>
                                <%-- 特别
                                <option value="2">小于</option>
                                <option value="3">小于等于</option>
                                 --%>
                                <option value="4">已打印</option>
                                <%-- 特别
                                <option value="5">大于等于</option>
                                 --%>
                            </select>
                            <input type="hidden" name="queryprinttimes" value="0"/>
                        </td>
                        <td colspan="2">
                            <input type="hidden" name="billtype" value="1"/>
                            <a href="javaScript:void(0);" id="storage-queay-adjustments" class="button-qr">查询</a>
                            <a href="javaScript:void(0);" id="storage-reload-adjustments" class="button-qr">重置</a>
                            <span id="storage-query-advanced-adjustments"></span>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>
<div id="adjustments-account-dialog"></div>
<script type="text/javascript">
    var initQueryJSON = $("#storage-form-query-adjustmentsPage").serializeJSON();
    $(function () {
        //按钮
        $("#storage-buttons-adjustmentsPage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/storage/adjustmentsAddPage.do">
                {
                    type: 'button-add',
                    handler: function () {
                        top.addTab('storage/showAdjustmentsAddPage.do', "调账单新增");
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/adjustmentsEditPage.do">
                <!--{-->
                <!--	type: 'button-edit',-->
                <!--	handler: function(){-->
                <!--		var con = $("#storage-datagrid-adjustmentsPage").datagrid('getSelected');-->
                <!--		if(con == null){-->
                <!--			$.messager.alert("提醒","请选择一条记录");-->
                <!--			return false;-->
                <!--		}	-->
                <!--		top.addTab('storage/showAdjustmentsEditPage.do?id='+ con.id, "调账单修改");-->
                <!--	}-->
                <!--},-->
                </security:authorize>
                <security:authorize url="/storage/adjustmentsViewPage.do">
                {
                    type: 'button-view',
                    handler: function () {
                        var con = $("#storage-datagrid-adjustmentsPage").datagrid('getSelected');
                        if (con == null) {
                            $.messager.alert("提醒", "请选择一条记录");
                            return false;
                        }
                        top.addOrUpdateTab('storage/showAdjustmentsEditPage.do?id=' + con.id, "调账单查看");
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/adjustmentsImport.do">
                {
                    type: 'button-import',
                    attr: {}
                },
                </security:authorize>
                <security:authorize url="/storage/adjustmentsExport.do">
                {
                    type: 'button-export',
                    attr: {
                        datagrid: "#storage-datagrid-adjustmentsPage",
                        queryForm: "#storage-form-query-adjustmentsPage",
                        type: 'exportUserdefined',
                        name: '调账单',
                        url: 'storage/getAdjustmentListExport.do'
                    }
                },
                </security:authorize>
                {
                    type: 'button-commonquery',
                    attr: {
                        //查询针对的表
                        name: 't_storage_adjustments',
                        //查询针对的表格id
                        datagrid: 'storage-datagrid-adjustmentsPage',
                        plain: true
                    }
                },
                {}
            ],
            buttons: [
                <security:authorize url="/erpconnect/addAdjustmentsVouch.do">
                {
                    id: 'button-account',
                    name: '生成凭证',
                    iconCls: 'button-audit',
                    handler: function () {
                        var rows = $("#storage-datagrid-adjustmentsPage").datagrid('getChecked');
                        if (rows == null || rows.length == 0) {
                            $.messager.alert("提醒", "请选择至少一条记录");
                            return false;
                        }
                        var ids = "";
                        for (var i = 0; i < rows.length; i++) {
                            if(rows[i].status!='3'&& rows[i].status!='4'){
                                $.messager.alert("提醒", "请选择审核通过和关闭状态的单据");
                                return false;
                            }
                            if (i == 0) {
                                ids = rows[i].id;
                            } else {
                                ids += "," + rows[i].id;
                            }
                        }
                        $("#adjustments-account-dialog").dialog({
                            title: '报溢调账单凭证',
                            width: 400,
                            height: 260,
                            closed: false,
                            modal: true,
                            cache: false,
                            href: 'erpconnect/showAdjustmentsVouchPage.do',
                            onLoad: function () {
                                $("#adjustments-ids").val(ids);
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/adjustmentsPrintViewBtn.do">
                {
                    id: 'button-printview-order',
                    name: '打印预览',
                    iconCls: 'button-preview',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/adjustmentsPrintBtn.do">
                {
                    id: 'button-print-order',
                    name: '打印',
                    iconCls: 'button-print',
                    handler: function () {
                    }
                },
                </security:authorize>
                {}
            ],
            model: 'bill',
            type: 'list',
            tname: 't_sales_order'
        });
        var checkListJson = $("#storage-datagrid-adjustmentsPage").createGridColumnLoad({
            name: 'storage_adjustments',
            frozenCol: [[
                {field: 'idok', checkbox: true, isShow: true}
            ]],
            commonCol: [[
                {field: 'id', title: '编号', width: 130, sortable: true},
                {field: 'sourceid', title: '盘点单号', width: 130, sortable: true},
                {field: 'businessdate', title: '业务日期', width: 80, sortable: true},
                {
                    field: 'storageid', title: '调账仓库', width: 80, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.storagename;
                    }
                },
                {
                    field: 'status', title: '状态', width: 80, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return getSysCodeName("status", value);
                    }
                },
                {field: 'vouchertimes', title: '生成凭证次数', align: 'center', width: 80},
                <%-- 瑞家打印提示特别 --%>
                {
                    field: 'printstate', title: '打印状态', width: 80, isShow: true,
                    formatter: function (value, rowData, index) {
                        if (rowData.printtimes > 0) {
                            return "已打印";
                        } else {
                            return "未打印";
                        }
                    }
                },
                {field: 'printtimes', title: '打印次数', width: 80, hidden: true},
                {field: 'addusername', title: '制单人', width: 80, sortable: true},
                {field: 'addtime', title: '制单时间', width: 120, sortable: true},
                {field: 'auditusername', title: '审核人', width: 80, sortable: true},
                {field: 'audittime', title: '审核时间', width: 120, sortable: true},
                {field: 'stopusername', title: '中止人', width: 80, hidden: true, sortable: true},
                {field: 'stoptime', title: '中止时间', width: 80, hidden: true, sortable: true},
                {field: 'remark', title: '备注', width: 80, sortable: true}
            ]]
        });
        $("#storage-datagrid-adjustmentsPage").datagrid({
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
            checkOnSelect: true,
            selectOnCheck: true,
            url: 'storage/showAdjustmentsList.do',
            queryParams: initQueryJSON,
            toolbar: '#storage-datagrid-toolbar-adjustmentsPage',
            onDblClickRow: function (rowIndex, rowData) {
                top.addOrUpdateTab('storage/showAdjustmentsEditPage.do?id=' + rowData.id, "报溢调账单查看");
            }
        }).datagrid("columnMoving");
        $("#storage-query-storageid").widget({
            name: 't_storage_adjustments',
            col: 'storageid',
            singleSelect: true,
            width: 150,
            view: true,
            onlyLeafCheck: true
        });
        //通用查询组建调用
//			$("#storage-query-advanced-adjustments").advancedQuery({
//				//查询针对的表
//		 		name:'t_storage_adjustments',
//		 		//查询针对的表格id
//		 		datagrid:'storage-datagrid-adjustmentsPage',
//		 		plain:true
//			});

        //回车事件
        controlQueryAndResetByKey("storage-queay-adjustments", "storage-reload-adjustments");

        //查询
        $("#storage-queay-adjustments").click(function () {
            //把form表单的name序列化成JSON对象
            var queryJSON = $("#storage-form-query-adjustmentsPage").serializeJSON();
            $("#storage-datagrid-adjustmentsPage").datagrid("load", queryJSON);
        });
        //重置
        $("#storage-reload-adjustments").click(function () {
            $("#storage-query-storageid").widget("clear");
            $("#storage-form-query-adjustmentsPage")[0].reset();
            var queryJSON = $("#storage-form-query-adjustmentsPage").serializeJSON();
            $("#storage-datagrid-adjustmentsPage").datagrid("load", queryJSON);
        });
    });
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "adjustmentsPage-dialog-print",
            code: "storage_adjustments",
            tableId: "storage-datagrid-adjustmentsPage",
            url_preview: "print/storage/adjustmentsPrintView.do",
            url_print: "print/storage/adjustmentsPrint.do",
            btnPreview: "button-printview-order",
            btnPrint: "button-print-order",
            printlimit: "${printlimit}"
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
