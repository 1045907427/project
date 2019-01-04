<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>采购退货出库单列表页面</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>

<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="storage-buttons-purchaseRejectOutPage" style="height:26px;"></div>
    </div>
    <div data-options="region:'center'">
        <table id="storage-datagrid-purchaseRejectOutPage" data-options="border:false"></table>
    </div>
</div>
<div id="storage-datagrid-toolbar-purchaseRejectOutPage">
    <form action="" id="storage-form-query-purchaseRejectOutPage" method="post">
        <table class="querytable">
            <tr>
                <td>编号:</td>
                <td><input type="text" name="id" style="width: 225px;"/></td>
                <td>供应商:</td>
                <td><input id="storage-query-supplierid" type="text" name="supplierid" style="width: 180px;"/></td>
                <td>采购部门:</td>
                <td><input type="text" id="storage-query-buydeptid" name="buydeptid"/></td>
            </tr>
            <tr>
                <td>业务日期:</td>
                <td><input type="text" name="businessdate1" style="width:100px;" class="Wdate"
                           onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/>
                    到 <input type="text" name="businessdate2" class="Wdate" style="width:100px;"
                             onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/>
                </td>
                <td>状态:</td>
                <td>
                    <select name="status" style="width:180px;">
                        <option></option>
                        <option value="2" selected="selected">保存</option>
                        <option value="3">审核通过</option>
                        <option value="4">关闭</option>
                    </select>
                </td>
                <td>打印状态:</td>
                <td>
                    <select name="printsign" style="width:150px;">
                        <option></option>
                        <option value="1">未打印</option>
                        <option value="4">已打印</option>
                    </select>
                    <input type="hidden" name="queryprinttimes" value="0" style="width:80px;"/>
                </td>
            </tr>
            <tr>
                <td>来源单据号:
                </td>
                <td>
                    <input type="text" name="sourceid" style="width: 225px;"/>
                </td>
                <td>验收状态:</td>
                <td>
                    <select name="ischeck" style="width:180px;">
                        <option value=""></option>
                        <option value="0">未验收</option>
                        <option value="1">已验收</option>
                    </select>
                </td>
                <td colspan="2" style="padding-left: 12px">
                    <a href="javaScript:void(0);" id="storage-queay-purchaseRejectOut" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="storage-reload-purchaseRejectOut" class="button-qr">重置</a>
                    <span id="storage-query-advanced-purchaseRejectOut"></span>
                </td>
            </tr>
        </table>
    </form>
    <div id="purchaseReject-account-dialog"></div>
</div>
<div style="display:none">
    <%--通用 --%>
    <div id="listPage-PurchaseRejectOut-dialog-print">
        <form action="" id="listPage-PurchaseRejectOut-dialog-print-form" method="post">
            <table>
                <tr>
                    <td>打印模板：</td>
                    <td>
                        <select id="listPage-PurchaseRejectOut-dialog-print-templet" name="templetid">
                        </select>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<script type="text/javascript">
    var SR_footerobject = null;
    var initQueryJSON = $("#storage-form-query-purchaseRejectOutPage").serializeJSON();
    $(function () {
        //按钮
        $("#storage-buttons-purchaseRejectOutPage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/storage/purchaseRejectOutAddPage.do">
                {
                    type: 'button-add',
                    handler: function () {
                        top.addOrUpdateTab('storage/showPurchaseRejectOutAddPage.do', "采购退货出库单新增");
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/purchaseRejectOutViewPage.do">
                {
                    type: 'button-view',
                    handler: function () {
                        var con = $("#storage-datagrid-purchaseRejectOutPage").datagrid('getSelected');
                        if (con == null) {
                            $.messager.alert("提醒", "请选择一条记录");
                            return false;
                        }
                        top.addOrUpdateTab('storage/showPurchaseRejectOutEditPage.do?id=' + con.id, "采购退货出库单查看");
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/purchaseRejectOutImport.do">
                {
                    type: 'button-import',
                    attr: {}
                },
                </security:authorize>
                <security:authorize url="/storage/purchaseRejectOutExport.do">
                {
                    type: 'button-export',
                    attr: {
                        queryForm: "#storage-form-query-purchaseRejectOutPage",
                        type: 'exportUserdefined',
                        name: '采购退货出库单表',
                        url: 'storage/exportPurchaseRejectOutListData.do'
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/purchaseRejectOutPrintView.do">
                {
                    type: 'button-preview',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/purchaseRejectOutPrint.do">
                {
                    type: 'button-print',
                    handler: function () {
                    }
                },
                </security:authorize>
                {
                    type: 'button-commonquery',
                    attr: {
                        //查询针对的表
                        name: 't_storage_purchasereject_out',
                        //查询针对的表格id
                        datagrid: 'storage-datagrid-purchaseRejectOutPage',
                        plain: true
                    }
                },
                {}
            ],
            buttons: [
                <security:authorize url="/erpconnect/addPurchaseRejectOutAccountVouch.do">
                {
                    id: 'purchaseReject-account',
                    name: '生成凭证',
                    iconCls: 'button-audit',
                    handler: function () {
                        var rows = $("#storage-datagrid-purchaseRejectOutPage").datagrid('getChecked');
                        if (rows == null || rows.length == 0) {
                            $.messager.alert("提醒", "请选择至少一条记录");
                            return false;
                        }
                        var ids = "";
                        for (var i = 0; i < rows.length; i++) {
                            if (rows[i].status != '4') {
                                $.messager.alert("提醒", "请选择关闭状态数据");
                                return false;
                            }
                            if (i == 0) {
                                ids = rows[i].id;
                            } else {
                                ids += "," + rows[i].id;
                            }
                        }
                        $("#purchaseReject-account-dialog").dialog({
                            title: '采购退货凭证',
                            width: 400,
                            height: 260,
                            closed: false,
                            modal: true,
                            cache: false,
                            href: 'erpconnect/showPurchaseRejectAccountVouchPage.do',
                            onLoad: function () {
                                $("#purchaseRejectAccount-ids").val(ids);
                            }
                        });
                    }
                }
                </security:authorize>
            ],
            model: 'bill',
            type: 'list',
            tname: 'storage_purchasereject_out'
        });
        var purchaseRejectOutJson = $("#storage-datagrid-purchaseRejectOutPage").createGridColumnLoad({
            name: 'storage_purchasereject_out',
            frozenCol: [[
                {field: 'idok', checkbox: true, isShow: true}
            ]],
            commonCol: [[
                {field: 'id', title: '编号', width: 125, sortable: true},
                {field: 'businessdate', title: '业务日期', width: 80, sortable: true},
                {
                    field: 'storageid', title: '退货仓库', width: 80, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.storagename;
                    }
                },
                {field: 'supplierid', title: '供应商编码', width: 70, sortable: true},
                {field: 'suppliername', title: '供应商名称', width: 150, isShow: true},
                {
                    field: 'buydeptid', title: '采购部门', width: 80, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.buydeptname;
                    }
                },
                {
                    field: 'buyuserid', title: '采购员', width: 80, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.buyusername;
                    }
                },
                <c:if test="${map.taxamount == 'taxamount'}">
                {
                    field: 'taxamount', title: '含税金额', width: 60, isShow: true, align: 'right',
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                </c:if>
                <c:if test="${map.notaxamount == 'notaxamount'}">
                {
                    field: 'notaxamount', title: '未税金额', width: 60, isShow: true, align: 'right',
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                </c:if>
                {
                    field: 'sourcetype', title: '来源类型', width: 90, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return getSysCodeName("purchaseRejectOut-sourcetype", value);
                    }
                },
                {field: 'sourceid', title: '来源编号', width: 80, sortable: true},
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
                        } else if (rowData.printtimes == null) {
                            return "";
                        } else {
                            return "未打印";
                        }
                    }
                },
                {field: 'vouchertimes', title: '生成凭证次数', align: 'center', width: 80},
                {field: 'printtimes', title: '打印次数', width: 60, align: 'center', hidden: true},
                {field: 'addusername', title: '制单人', width: 80, sortable: true},
                {field: 'addtime', title: '制单时间', width: 120, sortable: true, hidden: true},
                {field: 'auditusername', title: '审核人', width: 80, sortable: true, hidden: true},
                {field: 'audittime', title: '审核时间', width: 120, sortable: true, hidden: true},
                {field: 'stopusername', title: '中止人', width: 80, hidden: true, sortable: true},
                {field: 'stoptime', title: '中止时间', width: 80, hidden: true, sortable: true},
                {
                    field: 'ischeck', title: '验收状态', width: 60,
                    formatter: function (value, row, index) {
                        if (value == "1") {
                            return "已验收";
                        } else if (value == "0") {
                            return "未验收";
                        }
                    }
                },
                {field: 'checkdate', title: '验收日期', width: 70},
                {field: 'checkusername', title: '验收人', width: 60, hidden: true},
                {field: 'remark', title: '备注', width: 80, sortable: true}
            ]]
        });
        $("#storage-datagrid-purchaseRejectOutPage").datagrid({
            authority: purchaseRejectOutJson,
            frozenColumns: purchaseRejectOutJson.frozen,
            columns: purchaseRejectOutJson.common,
            fit: true,
            method: 'post',
            rownumbers: true,
            pagination: true,
            idField: 'id',
            sortName: 'id',
            sortOrder: 'desc',
            singleSelect: false,
            checkOnSelect: true,
            selectOnCheck: true,
            showFooter: true,
            pageSize: 100,
            url: 'storage/showPurchaseRejectOutList.do',
            queryParams: initQueryJSON,
            toolbar: '#storage-datagrid-toolbar-purchaseRejectOutPage',
            onDblClickRow: function (rowIndex, rowData) {
                top.addOrUpdateTab('storage/showPurchaseRejectOutEditPage.do?id=' + rowData.id, "采购退货出库单查看");
            },
            onLoadSuccess: function () {
                var footerrows = $(this).datagrid('getFooterRows');
                if (null != footerrows && footerrows.length > 0) {
                    SR_footerobject = footerrows[0];
                    countTotalAmount();
                }
            },
            onCheckAll: function () {
                countTotalAmount();
            },
            onUncheckAll: function () {
                countTotalAmount();
            },
            onCheck: function () {
                countTotalAmount();
            },
            onUncheck: function () {
                countTotalAmount();
            }
        }).datagrid("columnMoving");
        $("#storage-query-supplierid").supplierWidget({});
        //采购部门
        $("#storage-query-buydeptid").widget({
            referwid: 'RL_T_BASE_DEPARTMENT_BUYER',
            singleSelect: true,
            width: 150,
            onlyLeafCheck: false
        });
        //查询
        $("#storage-queay-purchaseRejectOut").click(function () {
            //把form表单的name序列化成JSON对象
            var queryJSON = $("#storage-form-query-purchaseRejectOutPage").serializeJSON();
            $("#storage-datagrid-purchaseRejectOutPage").datagrid("load", queryJSON);
        });
        //重置
        $("#storage-reload-purchaseRejectOut").click(function () {
            $("#storage-query-buydeptid").widget('clear');
            $("#storage-query-supplierid").supplierWidget("clear");
            $("#storage-form-query-purchaseRejectOutPage").form("reset");
            var queryJSON = $("#storage-form-query-purchaseRejectOutPage").serializeJSON();
            $("#storage-datagrid-purchaseRejectOutPage").datagrid("load", queryJSON);
        });
    });
    function countTotalAmount() {
        var rows = $("#storage-datagrid-purchaseRejectOutPage").datagrid('getChecked');
        var taxamount = 0;
        var notaxamount = 0;
        for (var i = 0; i < rows.length; i++) {
            taxamount = Number(taxamount) + Number(rows[i].taxamount == undefined ? 0 : rows[i].taxamount);
            notaxamount = Number(notaxamount) + Number(rows[i].notaxamount == undefined ? 0 : rows[i].notaxamount);
        }
        var obj = {id: '选中金额', taxamount: taxamount, notaxamount: notaxamount};
        var foot = [];
        foot.push(obj);
        if (null != SR_footerobject) {
            foot.push(SR_footerobject);
        }
        $("#storage-datagrid-purchaseRejectOutPage").datagrid("reloadFooter", foot);
    }
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "purchaseRejectOutPage-dialog-print",
            code: "storage_purchasereject",
            tableId: "storage-datagrid-purchaseRejectOutPage",
            url_preview: "print/storage/purchaseRejectOutPrintView.do",
            url_print: "print/storage/purchaseRejectOutPrint.do",
            btnPreview: "button-preview",
            btnPrint: "button-print",
            printlimit: "${printlimit}",
            getData: function (tableId, printParam) {
                var data = $("#" + tableId).datagrid('getChecked');
                if (data == null || data.length == 0) {
                    $.messager.alert("提醒", "请选择至少一条记录");
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
