<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>采购订单列表</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="purchase-buttons-buyOrderListPage"></div>
    </div>
    <div data-options="region:'center'">
        <table id="purchase-table-buyOrderListPage"></table>
        <div id="purchase-table-query-buyOrderListPage" style="padding:2px;height:auto">
            <div>
                <form action="" id="purchase-form-buyOrderListPage" method="post">
                    <table class="querytable">
                        <tr>
                            <td>业务日期:</td>
                            <td><input type="text" name="businessdatestart" style="width:100px;" class="Wdate"
                                       onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/>
                                到 <input type="text" name="businessdateend" class="Wdate" style="width:100px;"
                                         onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/>
                            </td>
                            <td>编 号:</td>
                            <td><input type="text" name="id" style="width: 150px;"/></td>
                            <td>状 态:</td>
                            <td>
                                <select name="isClose" style="width:130px;">
                                    <option></option>
                                    <option value="0" selected="selected">保存</option>
                                    <option value="1">审核通过</option>
                                    <option value="2">关闭</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>供应商:</td>
                            <td><input id="purchase-buyOrderListPage-supplier" type="text" name="supplierid"
                                       style="width: 225px;"/></td>
                            <td>入库仓库:</td>
                            <td>
                                <input name="storageid" id="purchase-buyOrderListPage-storageid"/>
                            </td>
                            <td>打印状态:</td>
                            <td>
                                <select name="printsign" style="width:130px;">
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
                                <input type="hidden" name="queryprinttimes" value="0" style="width:80px;"/>
                            </td>
                        </tr>
                        <tr>
                            <td colspan="4"></td>
                            <td colspan="2">
                                <a href="javaScript:void(0);" id="purchase-btn-queryBuyOrderListPage" class="button-qr">查询</a>
                                <a href="javaScript:void(0);" id="purchase-btn-reloadBuyOrderListPage"
                                   class="button-qr">重置</a>
                                <%--<span id="purchase-table-query-buyOrderListPage-advanced"></span>--%>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
        <a href="javaScript:void(0);" id="report-export-buyOrderListPageDetail" style="display: none">导出</a>
    </div>
</div>

<script type="text/javascript">
    $(document).ready(function () {
        var initQueryJSON = $("#purchase-form-buyOrderListPage").serializeJSON();
        var buyOrderListJson = $("#purchase-table-buyOrderListPage").createGridColumnLoad({
            name: 'purchase_buyorder',
            frozenCol: [[
                {field: 'idok', checkbox: true, isShow: true}
            ]],
            commonCol: [[
                {field: 'id', title: '编号', width: 120, sortable: true},
                {field: 'businessdate', title: '业务日期', width: 70, sortable: true},
                {field: 'supplierid', title: '供应商编码', width: 70},
                {field: 'suppliername', title: '供应商名称', width: 100, isShow: true},
                {
                    field: 'handlerid', title: '对方联系人', width: 100,
                    formatter: function (value, row, index) {
                        return row.handlername;
                    }
                },
                {
                    field: 'buydeptid', title: '采购部门', width: 100,
                    formatter: function (value, row, index) {
                        return row.buydeptname;
                    }
                },
                {
                    field: 'source', title: '来源类型', width: 80,
                    formatter: function (value, row, index) {
                        if (value == '1') {
                            return "采购计划单";
                        } else {
                            return "无";
                        }
                    }
                },
                {
                    field: 'orderappend', title: '追加状态', width: 60, hidden: true,
                    formatter: function (value, row, index) {
                        if (value == '1') {
                            return "是";
                        } else {
                            return "否";
                        }
                    }
                },
                {field: 'billno', title: '来源编号', width: 120, align: 'left'},
                {
                    field: 'field01', title: '含税金额', width: 100, align: 'right',
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'field02', title: '未税金额', width: 100, align: 'right',
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'field03', title: '税额', width: 80, align: 'right',
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'totalboxvolume', title: '总体积(立方米)', width: 100, hidden: true,
                    formatter: function (value, row, index) {
                        return Number(value).toFixed(3);
                    }
                },
                {field: 'addusername', title: '制单人', width: 80},
                {field: 'addtime', title: '制单时间', width: 120, sortable: true, hidden: true},
                {field: 'adddeptname', title: '制单人部门', width: 100, hidden: true},
                {field: 'modifyusername', title: '修改人', width: 120, hidden: true},
                {field: 'auditusername', title: '审核人', width: 100, hidden: true},
                {field: 'audittime', title: '审核时间', width: 100, hidden: true},
                {
                    field: 'status', title: '状态', width: 80,
                    formatter: function (value, row, index) {
                        return getSysCodeName('status', value);
                    }
                },
                {field: 'storageid', title: '仓库名称', width: 100, isShow: true},
                {field: 'remark', title: '备注', width: 100},
                {
                    field: 'printstate', title: '打印状态', width: 80, isShow: true,
                    formatter: function (value, rowData, index) {
                        if (rowData.printtimes > 0) {
                            return "已打印";
                        } else if (rowData.printtimes == -99) {
                            return "";
                        } else {
                            return "未打印";
                        }
                    }
                },
                {field: 'printtimes', title: '打印次数', width: 80, hidden: true}
            ]]
        });
        $("#purchase-table-buyOrderListPage").datagrid({
            fit: true,
            method: 'post',
            rownumbers: true,
            pagination: true,
            idField: 'id',
            singleSelect: false,
            checkOnSelect: true,
            selectOnCheck: true,
            toolbar: '#purchase-table-query-buyOrderListPage',
            url: "purchase/buyorder/showBuyOrderPageList.do",
            queryParams: initQueryJSON,
            sortName: 'addtime',
            sortOrder: 'desc',
            authority: buyOrderListJson,
            frozenColumns: buyOrderListJson.frozen,
            columns: buyOrderListJson.common,
            onDblClickRow: function (index, data) {
                top.addOrUpdateTab('purchase/buyorder/buyOrderPage.do?type=edit&id=' + data.id, "采购订单查看");
            },
            onBeforeLoad: function () {

            }
        }).datagrid("columnMoving");

        //按钮
        $("#purchase-buttons-buyOrderListPage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/purchase/buyorder/buyOrderAddBtn.do">
                {
                    type: 'button-add',
                    handler: function () {
                        top.addOrUpdateTab('purchase/buyorder/buyOrderPage.do', '采购订单新增');
                    },
                    url: ''
                },
                </security:authorize>
                <security:authorize url="/purchase/buyorder/buyOrderEditBtn.do">
                {
                    type: 'button-edit',
                    handler: function () {
                        var datarow = $("#purchase-table-buyOrderListPage").datagrid('getSelected');
                        if (datarow == null || datarow.id == null) {
                            $.messager.alert("提醒", "请选择要修改的采购订单");
                            return false;
                        }
                        top.addOrUpdateTab('purchase/buyorder/buyOrderPage.do?type=edit&id=' + datarow.id, '采购订单修改');
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/buyorder/buyOrderViewBtn.do">
                {
                    type: 'button-view',
                    handler: function () {
                        var datarow = $("#purchase-table-buyOrderListPage").datagrid('getSelected');
                        if (datarow == null || datarow.id == null) {
                            $.messager.alert("提醒", "请选择要查看的采购订单");
                            return false;
                        }
                        top.addOrUpdateTab('purchase/buyorder/buyOrderPage.do?type=edit&id=' + datarow.id, '采购订单查看');
                    }
                },
                </security:authorize>
                {
                    type: 'button-commonquery',
                    attr: {
                        name: 'purchase_buyorder',
                        plain: true,
                        datagrid: 'purchase-table-buyOrderListPage'
                    }
                },
                {}
            ],
            buttons: [
                <security:authorize url="/purchase/buyorder/buyOrderCloseBtn.do">
                {
                    id: 'button-close-bill',
                    name: '关闭订单',
                    iconCls: 'button-close',
                    handler: function () {
                        var data = $("#purchase-table-buyOrderListPage").datagrid('getSelected');
                        if (data == null || data.id == null || data.id == "") {
                            $.messager.alert("提醒", "请选择相应的采购订单!");
                            return false;
                        }
                        if (data.status != '3') {
                            $.messager.alert("提醒", "只有审核通过状态下采购订单才能关闭");
                            return false;
                        }
                        $.messager.confirm("提醒", "采购订单关闭后不能再开启，确定要关闭？", function (r) {
                            if (r) {
                                loading("关闭中..");
                                $.ajax({
                                    url: 'purchase/buyorder/closeBuyOrder.do?id=' + data.id,
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag) {
                                            $.messager.alert("提醒", "关闭采购订单成功");
                                            $("#purchase-btn-queryBuyOrderListPage").trigger("click");
                                        } else {
                                            if (json.msg) {
                                                $.messager.alert("提醒", "关闭采购订单失败!" + json.msg);
                                            } else {
                                                $.messager.alert("提醒", "关闭采购订单失败!");
                                            }
                                        }
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/buyorder/multiplyuditBuyOrder.do">
                {
                    id: 'button-audit-multiply',
                    name: '批量审核',
                    iconCls: 'button-audit',
                    handler: function () {
                        var rows = $("#purchase-table-buyOrderListPage").datagrid('getChecked');
                        if (rows.length == 0) {
                            $.messager.alert("提醒", "请选中需要审核的记录。");
                            return false;
                        }
                        $.messager.confirm("提醒", "是否批量审核选中的采购订单信息？", function (r) {
                            if (r) {
                                var ids = "";
                                for (var i = 0; i < rows.length; i++) {
                                    ids += rows[i].id + ',';
                                }
                                $.ajax({
                                    url: 'purchase/buyorder/auditMultiBuyOrder.do',
                                    dataType: 'json',
                                    type: 'post',
                                    data: {ids: ids},
                                    success: function (json) {
                                        loaded();
                                        if (json.flag == true) {
                                            $.messager.alert("提醒", "审核成功：" + json.success + "&nbsp;审核失败：" + json.failure + "<br/>" + json.msg);
                                            $("#purchase-table-buyOrderListPage").datagrid('reload');
                                        }
                                        else {
                                            $.messager.alert("提醒", "审核出错");
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
                <security:authorize url="/purchase/buyorder/buyOrderExport.do">
                {
                    id: 'button-export-detail',
                    name: '导出明细',
                    iconCls: 'button-export',
                    handler: function () {
                        var rows = $("#purchase-table-buyOrderListPage").datagrid('getChecked');
                        var idsarr = new Array();
                        if (rows.length > 0) {
                            for (var i = 0; i < rows.length; i++) {
                                idsarr.push(rows[i].id);
                            }
                        }
                        $("#report-export-buyOrderListPageDetail").Excel('export', {
                            queryForm: "#purchase-form-buyOrderListPage",
                            type: 'exportUserdefined',
                            name: '采购订单明细',
                            fieldParam: {idarrs: idsarr.join(",")},
                            url: 'purchase/buyorder/exportBuyOrderDetailData.do'
                        });
                        $("#report-export-buyOrderListPageDetail").trigger("click");

                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/buyorder/buyOrderPrintViewBtn.do">
                {
                    id: 'button-printview-buyorder',
                    name: '打印预览',
                    iconCls: 'button-preview',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/buyorder/buyOrderPrintBtn.do">
                {
                    id: 'button-print-buyorder',
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
            datagrid: 'purchase-table-buyOrderListPage',
            tname: 't_purchase_buyorder'
        });

        //仓库
        $("#purchase-buyOrderListPage-storageid").widget({
            width: 150,
            referwid: 'RL_T_BASE_STORAGE_INFO',
            singleSelect: true,
            onlyLeafCheck: false
        });

        $("#purchase-buyOrderListPage-supplier").supplierWidget({
            name: 't_purchase_buyorder',
            col: 'supplierid',
            singleSelect: true,
            onlyLeafCheck: true,
            onSelect: function (data) {
            }
        });

// 			$("#purchase-table-query-buyOrderListPage-advanced").advancedQuery({
//		 		name:'purchase_buyorder',
//		 		plain:true,
//		 		datagrid:'purchase-table-buyOrderListPage'
//			});

        //回车事件
        controlQueryAndResetByKey("purchase-btn-queryBuyOrderListPage", "purchase-btn-reloadBuyOrderListPage");

        $("#purchase-btn-queryBuyOrderListPage").click(function () {
            //查询参数直接添加在url中
            var queryJSON = $("#purchase-form-buyOrderListPage").serializeJSON();
            $("#purchase-table-buyOrderListPage").datagrid('clearChecked');
            $("#purchase-table-buyOrderListPage").datagrid('clearSelections');
            $('#purchase-table-buyOrderListPage').datagrid('load', queryJSON);
        });
        $("#purchase-btn-reloadBuyOrderListPage").click(function () {
            $("#purchase-buyOrderListPage-storageid").widget('clear');
            $("#purchase-buyOrderListPage-supplier").supplierWidget('clear');
            $("#purchase-form-buyOrderListPage")[0].reset();
            var queryJSON = $("#purchase-form-buyOrderListPage").serializeJSON();
            $("#purchase-table-buyOrderListPage").datagrid('clearChecked');
            $("#purchase-table-buyOrderListPage").datagrid('clearSelections');
            $('#purchase-table-buyOrderListPage').datagrid('load', queryJSON);

        });
    });
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "listPage-BuyOrder-dialog-print",
            code: "purchase_buyorder",
            tableId: "purchase-table-buyOrderListPage",
            url_preview: "print/purchase/buyOrderPrintView.do",
            url_print: "print/purchase/buyOrderPrint.do",
            btnPreview: "button-printview-buyorder",
            btnPrint: "button-print-buyorder"
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
