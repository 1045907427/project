<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>还货单列表页面</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>

<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="storage-buttons-lendPage" style="height:26px;"></div>
    </div>
    <div data-options="region:'center'">
        <table id="storage-datagrid-lendPage" data-options="border:false"></table>
    </div>
</div>
<div id="storage-datagrid-toolbar-lendPage">
    <form action="" id="storage-form-query-lendPage" method="post">
        <input type="hidden" name="billtype" value="2"/>
        <table class="querytable">
            <tr>
                <td>业务日期:</td>
                <td><input type="text" name="businessdate1" style="width:100px;" class="Wdate"
                           onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/>
                    到 <input type="text" name="businessdate2" class="Wdate" style="width:100px;"
                             onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/></td>
                </td>
                <td>编&nbsp;&nbsp;号:</td>
                <td><input type="text" name="id" style="width: 150px;"/></td>
                </td>
            </tr>
            <tr>
                <td>还货仓库:</td>
                <td>
                    <input type="text" name="storageid" id="storage-storageid-widget"/>
                <td>状&nbsp;&nbsp;态:</td>
                <td><select name="status" style="width:150px;">
                    <option></option>
                    <option value="2" selected="selected">保存</option>
                    <option value="4">关闭</option>
                </select></td>
            </tr>
            <tr>
                <td>相关部门:</td>
                <td>
                    <input id="storage-otherEnter-deptid" name="deptid" style="width: 225px;"/>
                <td>打印状态:</td>
                <td>
                    <select name="printsign" style="width:150px;">
                        <option></option>
                        <option value="1">未打印</option>
                        <%-- 瑞家特别
                        <option value="2">小于</option>
                        <option value="3">小于等于</option>
                         --%>
                        <option value="4">已打印</option>
                        <%-- 瑞家特别
                        <option value="5">大于等于</option>
                         --%>
                    </select>
                    <input type="hidden" name="queryprinttimes" value="0" style="width:80px;"/>
                </td>
                <td colspan="2">
                    <a href="javaScript:void(0);" id="storage-queay-lend" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="storage-reload-lend" class="button-qr">重置</a>
                    <span id="storage-query-advanced-lend"></span>
                </td>
            </tr>
        </table>
    </form>
</div>
<script type="text/javascript">
    var initQueryJSON = $("#storage-form-query-lendPage").serializeJSON();
    $(function () {
        //按钮
        $("#storage-buttons-lendPage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/storage/lendAddPage.do">
                {
                    type: 'button-add',
                    handler: function () {
                        top.addTab('storage/showLendAddPage.do?billtype=2', "还货单新增");
                    },
                    url: '/storage/lendAddPage.do?billtype=2'
                },
                </security:authorize>
                <security:authorize url="/storage/lendEditPage.do">
                <!--{-->
                <!--	type: 'button-edit',-->
                <!--	handler: function(){-->
                <!--		var con = $("#storage-datagrid-lendPage").datagrid('getSelected');-->
                <!--		if(con == null){-->
                <!--			$.messager.alert("提醒","请选择一条记录");-->
                <!--			return false;-->
                <!--		}	-->
                <!--		top.addTab('storage/showLendEditPage.do?id='+ con.id, "还货单修改");-->
                <!--	},-->
                <!--	url:'/storage/lendEditPage.do'-->
                <!--},-->
                </security:authorize>
                <security:authorize url="/storage/showLendViewPage.do">
                {
                    type: 'button-view',
                    handler: function () {
                        var con = $("#storage-datagrid-lendPage").datagrid('getSelected');
                        if (con == null) {
                            $.messager.alert("提醒", "请选择一条记录");
                            return false;
                        }
                        top.addOrUpdateTab('storage/showLendEditPage.do?billtype=2&id=' + con.id, "还货单查看");
                    },
                    url: '/storage/showLendViewPage.do'
                },
                </security:authorize>
                <security:authorize url="/storage/lendImport.do">
                {
                    type: 'button-import',
                    attr: {
                        type: 'importUserdefined',
                        url: 'storage/lendImport.do?billtype=2',
                        importparam: '必填项：入货仓编码，还货人类型，还货人编号，商品编码，数量。<br/>选填项：单价，金额',
                        onClose: function () { //导入成功后窗口关闭时操作，
                            $("#storage-datagrid-lendPage").datagrid('reload');	//更新列表
                        }
                    },
                },
                </security:authorize>
                <security:authorize url="/storage/lendExport.do">
                {
                    type: 'button-export',
                    attr: {
                        queryForm: "#storage-form-query-lendPage",
                        datagrid: "#storage-datagrid-lendPage",
                        type: "exportUserdefined",
                        name: "还货单",
                        url: 'storage/lendExport.do'
                    }
                },
                </security:authorize>
                {
                    type: 'button-commonquery',
                    attr: {
                        //查询针对的表
                        name: 't_storage_lend',
                        //查询针对的表格id
                        datagrid: 'storage-datagrid-lendPage',
                        plain: true
                    }
                },
                {}
            ],
            buttons: [
                <security:authorize url="/storage/lendPrintViewBtn.do">
                {
                    id: 'button-printview-order',
                    name: '打印预览',
                    iconCls: 'button-preview',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/lendPrintBtn.do">
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
            tname: 't_storage_lend'
        });
        var tableColumnListJson = $("#storage-datagrid-lendPage").createGridColumnLoad({
            name: 't_storage_lend',
            frozenCol: [[
                {field: 'idok', checkbox: true, isShow: true}
            ]],
            commonCol: [[
                {field: 'id', title: '编号', width: 130, sortable: true},
                {field: 'businessdate', title: '业务日期', width: 80, sortable: true},
                {
                    field: 'storageid', title: '还货仓库', width: 80, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.storagename;
                    }
                },
                {
                    field: 'lendtype', title: '还货类型', width: 80, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        if (rowData.lendtype == '1') {
                            return '供应商';
                        } else {
                            return '客户';
                        }
                    }
                },
                {
                    field: 'lendid', title: '还货人', width: 100, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.lendname;
                    }
                },
                {
                    field: 'deptid', title: '相关部门', width: 100, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.deptname;
                    }
                },
                {
                    field: 'status', title: '状态', width: 60, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return getSysCodeName("status", value);
                    }
                },
                <%-- 打印提示特别 --%>
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
                {field: 'stopusername', title: '中止人', width: 80, hidden: true},
                {field: 'stoptime', title: '中止时间', width: 80, hidden: true, sortable: true},
                {field: 'remark', title: '备注', width: 80, sortable: true}
            ]]
        });
        $("#storage-datagrid-lendPage").datagrid({
            authority: tableColumnListJson,
            frozenColumns: tableColumnListJson.frozen,
            columns: tableColumnListJson.common,
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
            url: 'storage/showLendList.do',
            queryParams: initQueryJSON,
            toolbar: '#storage-datagrid-toolbar-lendPage',
            onDblClickRow: function (rowIndex, rowData) {
                top.addOrUpdateTab('storage/showLendEditPage.do?billtype=2&id=' + rowData.id, "还货单查看");
            }
        }).datagrid("columnMoving");

        //入库仓库
        $("#storage-storageid-widget").widget({
            width: 225,
            referwid: 'RL_T_BASE_STORAGE_INFO',
            singleSelect: true,
            onlyLeafCheck: false
        });
        //部门控件
        $("#storage-otherEnter-deptid").widget({
            name: 't_storage_lend',
            col: 'deptid',
            singleSelect: true,
            isdatasql: false,
            width: 225,
            onlyLeafCheck: false
        });

        //通用查询组建调用
//			$("#storage-query-advanced-lend").advancedQuery({
//				//查询针对的表
//		 		name:'t_storage_other_enter',
//		 		//查询针对的表格id
//		 		datagrid:'storage-datagrid-lendPage',
//		 		plain:true
//			});

        //回车事件
        controlQueryAndResetByKey("storage-queay-lend", "storage-reload-lend");

        //查询
        $("#storage-queay-lend").click(function () {
            //把form表单的name序列化成JSON对象
            var queryJSON = $("#storage-form-query-lendPage").serializeJSON();
            $("#storage-datagrid-lendPage").datagrid("load", queryJSON);
        });
        //重置
        $("#storage-reload-lend").click(function () {
            $("#storage-storageid-widget").widget('clear');
            $("#storage-otherEnter-deptid").widget('clear');
            $("#storage-form-query-lendPage").form("reset");
            var queryJSON = $("#storage-form-query-lendPage").serializeJSON();
            $("#storage-datagrid-lendPage").datagrid("load", queryJSON);
        });
    });
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "otherenter-dialog-print",
            code: "storage_lendenter",
            tableId: "storage-datagrid-lendPage",
            url_preview: "print/storage/lendPrintView.do?billtype=2",
            url_print: "print/storage/lendPrint.do?billtype=2",
            btnPreview: "button-printview-order",
            btnPrint: "button-print-order",
            printlimit: "${printlimit}"
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
