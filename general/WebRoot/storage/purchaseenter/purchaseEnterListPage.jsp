<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>采购入库单列表页面</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>

<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="storage-buttons-purchaseEnterPage" style="height:26px;"></div>
    </div>
    <div data-options="region:'center'">
        <table id="storage-datagrid-purchaseEnterPage" data-options="border:false"></table>
    </div>
</div>
<div id="storage-datagrid-toolbar-purchaseEnterPage">
    <form action="" id="storage-form-query-purchaseEnterPage" method="post">
        <table class="querytable">
            <tr>
                <td>编号:</td>
                <td><input type="text" name="id" style="width: 180px;"/></td>
                <td>业务日期:</td>
                <td><input type="text" name="businessdate1" style="width:100px;" class="Wdate"
                           onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/>
                    到 <input type="text" name="businessdate2" class="Wdate" style="width:100px;"
                             onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/></td>
                <td>状态:</td>
                <td>
                    <select name="status" style="width:168px;">
                        <option></option>
                        <option value="2" selected="selected">保存</option>
                        <option value="3">审核通过</option>
                        <option value="4">关闭</option>
                    </select>
                </td>
                <td>入库仓库:</td>
                <td>
                    <input type="text" id="storage-query-storageid" name="storageid"/>
                </td>
            </tr>
            <tr>
                <td>供应商:</td>
                <td><input id="storage-query-supplierid" type="text" name="supplierid" style="width: 180px;"/></td>
                <td>采购部门:</td>
                <td>
                    <input type="text" id="storage-query-buydeptid" name="buydeptid"/>
                </td>
                <td>来源类型:</td>
                <td>
                    <input type="text" id="storage-query-sourcetype" name="sourcetype"/>
                </td>
            </tr>
            <tr>

                <td>打印状态:</td>
                <td class="tdinput">
                    <select name="printsign" style="width:180px;">
                        <option></option>
                        <option value="1">未打印</option>
                        <option value="4">已打印</option>
                    </select>
                    <input type="hidden" name="queryprinttimes" value="0"/>
                </td>
                <td>商品名称:</td>
                <td>
                    <input id="storage-query-goodsid" type="text" name="goodsid" style="width: 225px"/>
                </td>
                <td>手工单号:</td>
                <td>
                    <input type="text" id="storage-query-field04" name="field04" style="width: 168px"/>
                    <span id="storage-query-advanced-purchaseEnter"></span>
                </td>
                <td colspan="2">
                    <a href="javaScript:void(0);" id="storage-queay-purchaseEnter" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="storage-reload-purchaseEnter" class="button-qr">重置</a>
                </td>
            </tr>

        </table>
    </form>
</div>
<script type="text/javascript">
    var SR_footerobject = null;
    var initQueryJSON = $("#storage-form-query-purchaseEnterPage").serializeJSON();
    $(function () {
        //按钮
        $("#storage-buttons-purchaseEnterPage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/storage/purchaseEnterAddPage.do">
                {
                    type: 'button-add',
                    handler: function () {
                        top.addOrUpdateTab('storage/showPurchaseEnterAddPage.do', "采购入库单新增");
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/purchaseEnterViewPage.do">
                {
                    type: 'button-view',
                    handler: function () {
                        var con = $("#storage-datagrid-purchaseEnterPage").datagrid('getSelected');
                        if (con == null) {
                            $.messager.alert("提醒", "请选择一条记录");
                            return false;
                        }
                        top.addOrUpdateTab('storage/showPurchaseEnterEditPage.do?id=' + con.id, "采购入库单查看");
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/purchaseEnterPrintView.do">
                {
                    type: 'button-preview',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/purchaseEnterPrint.do">
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
                        name: 't_storage_purchase_enter',
                        //查询针对的表格id
                        datagrid: 'storage-datagrid-purchaseEnterPage',
                        plain: true
                    }
                },
                {}
            ],
            buttons: [
                <security:authorize url="/storage/purchaseEnterMultiauditBtn.do">
                {
                    id: 'button-audit-multiply',
                    name: '批量审核',
                    iconCls: 'button-audit',
                    handler: function () {
                        var rows = $("#storage-datagrid-purchaseEnterPage").datagrid('getChecked');
                        if (rows.length == 0) {
                            $.messager.alert("提醒", "请选中需要审核的记录。");
                            return false;
                        }
                        $.messager.confirm("提醒", "是否批量审核选中的采购入库单信息？", function (r) {
                            if (r) {
                                var ids = "";
                                for (var i = 0; i < rows.length; i++) {
                                    ids += rows[i].id + ',';
                                }
                                $.ajax({
                                    url: 'storage/auditMultiPurchaseEnter.do',
                                    dataType: 'json',
                                    type: 'post',
                                    data: {ids: ids},
                                    success: function (json) {
                                        loaded();
                                        if (json.flag == true) {
                                            $.messager.alert("提醒", "审核成功：" + json.success + "&nbsp;审核失败：" + json.failure + "<br/>" + json.msg);
                                            $("#storage-datagrid-purchaseEnterPage").datagrid('reload');
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
                <security:authorize url="/storage/exportPurchaseEnterBtn.do">
                {
                    id: 'button-export-list',
                    name: '全局导出',
                    iconCls: 'button-export',
                    handler: function () {
                        var queryJSON = $("#storage-form-query-purchaseEnterPage").serializeJSON();
                        //获取排序规则
                        var objecr = $("#storage-datagrid-purchaseEnterPage").datagrid("options");
                        if (null != objecr.sortName && null != objecr.sortOrder) {
                            queryJSON["sort"] = objecr.sortName;
                            queryJSON["order"] = objecr.sortOrder;
                        }
                        var queryParam = JSON.stringify(queryJSON);
                        var url = "storage/exportPurchaseEnterList.do";
                        exportByAnalyse(queryParam, "采购入库单", "storage-datagrid-purchaseEnterPage", url);
                    }
                },
                </security:authorize>
            ],
            model: 'bill',
            type: 'list',
            tname: 't_storage_purchase_enter'
        });
        var checkListJson = $("#storage-datagrid-purchaseEnterPage").createGridColumnLoad({
            //name :'storage_purchase_enter',
            frozenCol: [[
                {field: 'idok', checkbox: true, isShow: true}
            ]],
            commonCol: [[
                {field: 'id', title: '编号', width: 130, sortable: true},
                {field: 'businessdate', title: '业务日期', width: 80, sortable: true},
                {field: 'supplierid', title: '供应商编码', width: 70, sortable: true},
                {field: 'suppliername', title: '供应商名称', width: 150, isShow: true},
                {
                    field: 'buydeptid', title: '采购部门', width: 100, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.buydeptname;
                    }
                },
                {
                    field: 'buyuserid', title: '采购人员', width: 80,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.buyusername;
                    }
                },
                {
                    field: 'storageid', title: '入库仓库', width: 80, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.storagename;
                    }
                },
                <c:if test="${access.taxamount == 'taxamount'}">
                {
                    field: 'money', title: '金额', width: 80, isShow: true,
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                </c:if>
                {
                    field: 'sourcetype', title: '来源类型', width: 80, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return getSysCodeName("purchaseEnter-sourcetype", value);
                    }
                },
                {field: 'sourceid', title: '来源单据编号', width: 130, sortable: true},
                {
                    field: 'status', title: '状态', width: 60,
                    formatter: function (value, rowData, rowIndex) {
                        return getSysCodeName("status", value);
                    }
                },
                {field: 'addusername', title: '制单人', width: 80},
                {field: 'addtime', title: '制单时间', width: 120},
                {field: 'auditusername', title: '审核人', width: 80, hidden: true},
                {field: 'audittime', title: '审核时间', width: 80, hidden: true},
                {field: 'stopusername', title: '中止人', width: 80, hidden: true},
                {field: 'stoptime', title: '中止时间', width: 80, hidden: true},
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
                {
                    field: 'printtimes', title: '打印次数', align: 'center', width: 60,
                    formatter: function (value, rowData, index) {
                        if (value == -99) {
                            return "";
                        } else {
                            return value;
                        }
                    }
                },
                {field: 'remark', title: '备注', width: 80}
            ]]
        });
        $("#storage-datagrid-purchaseEnterPage").datagrid({
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
            singleSelect: false,
            checkOnSelect: true,
            selectOnCheck: true,
            showFooter: true,
            pageSize: 100,
            url: 'storage/showPurchaseEnterList.do',
            queryParams: initQueryJSON,
            toolbar: '#storage-datagrid-toolbar-purchaseEnterPage',
            onLoadSuccess: function () {
                var footerrows = $(this).datagrid('getFooterRows');
                if (null != footerrows && footerrows.length > 0) {
                    SR_footerobject = footerrows[0];
                    countTotalAmount();
                }
            },
            onDblClickRow: function (rowIndex, rowData) {
                top.addOrUpdateTab('storage/showPurchaseEnterEditPage.do?id=' + rowData.id, "采购入库单查看");
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
        //商品名称
        $("#storage-query-goodsid").goodsWidget({
            singleSelect: true,
            isHiddenUsenum: true
        });
        $("#storage-query-storageid").widget({
            name: 't_storage_purchase_enter',
            col: 'storageid',
            singleSelect: false,
            initSelectNull: true,
            width: 150
        });
        //采购部门
        $("#storage-query-buydeptid").widget({ //采购部门
            referwid: 'RL_T_BASE_DEPARTMENT_BUYER',
            singleSelect: true,
            width: 225,
            onlyLeafCheck: false
        });
        $("#storage-query-sourcetype").widget({
            name: 't_storage_purchase_enter',
            col: 'sourcetype',
            singleSelect: true,
            initSelectNull: true,
            width: 168
        });
        //查询
        $("#storage-queay-purchaseEnter").click(function () {
            var queryJSON = $("#storage-form-query-purchaseEnterPage").serializeJSON();
            $("#storage-datagrid-purchaseEnterPage").datagrid("load", queryJSON);
        });
        //重置
        $("#storage-reload-purchaseEnter").click(function () {
            $("#storage-query-storageid").widget("clear");
            $("#storage-query-buydeptid").widget('clear');
            $("#storage-query-supplierid").supplierWidget("clear");
            $("#storage-query-goodsid").goodsWidget('clear');
            $("#storage-form-query-purchaseEnterPage").form("reset");
            var queryJSON = $("#storage-form-query-purchaseEnterPage").serializeJSON();
            $("#storage-datagrid-purchaseEnterPage").datagrid("load", queryJSON);
        });
    });

    function countTotalAmount() {
        var taxamount = 0;
        var rows = $("#storage-datagrid-purchaseEnterPage").datagrid('getChecked');
        for (var i = 0; i < rows.length; i++) {
            taxamount = Number(taxamount) + Number(rows[i].money == undefined ? 0 : rows[i].money);
        }
        var obj = {id: '选中合计', money: taxamount};
        var foot = [];
        foot.push(obj);
        if (null != SR_footerobject) {
            foot.push(SR_footerobject);
        }
        $("#storage-datagrid-purchaseEnterPage").datagrid("reloadFooter", foot);
    }

</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "purchaseenter-dialog-print",
            code: "storage_purchaseenter",
            tableId: "storage-datagrid-purchaseEnterPage",
            url_preview: "print/storage/purchaseEnterPrintView.do",
            url_print: "print/storage/purchaseEnterPrint.do",
            btnPreview: "button-preview",
            btnPrint: "button-print",
            printlimit: "${printlimit}"
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
