<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>销售退货通知单页面</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>
<body>
<input type="hidden" id="sales-backid-rejectBill" value="${id }"/>
<input type="hidden" id="sales-parentid-rejectBill"/><!-- 参照上游单据的编码 -->
<div id="sales-layout-rejectBill" class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="sales-buttons-rejectBill" style="height:26px;"></div>
    </div>
    <div data-options="region:'center',border:false">
        <div id="sales-panel-rejectBill"></div>
        <div id="rejectBill-goods-history-price"></div>
    </div>
</div>
<div style="display: none;">
    <div class="easyui-dialog" id="sales-sourceQueryDialog-rejectBill" data-options="closed:true"></div>
    <div class="easyui-dialog" id="sales-sourceDialog-rejectBill" data-options="closed:true"></div>
    <div class="easyui-dialog" id="sales-dialog-rejectBill" closed="true"></div>
    <div id="sales-dialog-split"></div>
</div>
<script type="text/javascript">
    receipt_url = "sales/rejectBillCheckEditPage.do?id=${id}";
    var wareListJson = $("#sales-datagrid-rejectBillAddPage").createGridColumnLoad({ //以下为商品明细datagrid字段
        //name:'t_sales_rejectbill_detail',
        frozenCol: [[]],
        commonCol: [[
            {field: 'id', checkbox: true},
            {field: 'goodsid', title: '商品编码', width: 70, align: ' left'},
            {
                field: 'goodsname', title: '商品名称', width: 220, align: 'left', aliascol: 'goodsid',
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        if (rowData.deliverytype == '1') {
                            return "<font color='blue'>&nbsp;赠 </font>" + rowData.goodsInfo.name;
                        } else if (rowData.deliverytype == '2') {
                            return "<font color='blue'>&nbsp;捆绑 </font>" + rowData.goodsInfo.name;
                        } else {
                            return rowData.goodsInfo.name;
                        }
                    } else {
                        return "";
                    }
                }
            },
            {
                field: 'barcode', title: '条形码', width: 90, align: 'left', aliascol: 'goodsid',
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        return rowData.goodsInfo.barcode;
                    } else {
                        return "";
                    }
                }
            },
            {
                field: 'itemno', title: '商品货位', width: 60, aliascol: 'goodsid',
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        return rowData.goodsInfo.itemno;
                    } else {
                        return "";
                    }
                }
            },
            {
                field: 'brandName', title: '商品品牌', width: 80, align: 'left', aliascol: 'goodsid', hidden: true,
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        return rowData.goodsInfo.brandName;
                    } else {
                        return "";
                    }
                }
            },
            {
                field: 'boxnum', title: '箱装量', aliascol: 'goodsid', width: 45, align: 'right',
                formatter: function (value, rowData, rowIndex) {
                    if (rowData.goodsInfo != null) {
                        return formatterBigNumNoLen(rowData.goodsInfo.boxnum);
                    } else {
                        return "";
                    }
                }
            },
            {field: 'unitname', title: '单位', width: 35, align: 'left'},
            {
                field: 'unitnum', title: '数量', width: 60, align: 'right',
                formatter: function (value, row, index) {
                    return formatterBigNumNoLen(value);
                }
            },
            {
                field: 'taxprice', title: '单价', width: 60, align: 'right',
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                },
                editor: {
                    type: 'numberbox',
                    options: {
                        precision: 6
                    }
                }
            },
            <security:authorize url="/sales/receiptBillCheckBuyprice.do">
            {
                field: 'buyprice', title: '采购价', width: 60, align: 'right', sortable: true, hidden: true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            </security:authorize>
            {
                field: 'boxprice', title: '箱价', aliascol: 'goodsid', width: 60, align: 'right',
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                },
                editor: {
                    type: 'numberbox',
                    options: {
                        precision: 2
                    }
                }
            },
            {
                field: 'taxamount', title: '金额', width: 60, align: 'right',
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                },
                editor: {
                    type: 'numberbox',
                    options: {
                        precision: 2
                    }
                }
            },
            {
                field: 'taxtype', title: '税种', width: 70, align: 'left', hidden: true,
                formatter: function (value, row, index) {
                    return row.taxtypename;
                }
            },
            {
                field: 'notaxprice', title: '未税单价', width: 80, align: 'right', hidden: true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'notaxamount', title: '未税金额', width: 80, align: 'right', hidden: true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {
                field: 'tax', title: '税额', width: 80, align: 'right', hidden: true,
                formatter: function (value, row, index) {
                    return formatterMoney(value);
                }
            },
            {field: 'auxnumdetail', title: '辅数量', width: 60, align: 'right'},
            {
                field: 'rejectcategory', title: '退货属性', width: 80, align: 'left', hidden: true,
                formatter: function (value, row, index) {
                    return getSysCodeName('rejectCategory', value);
                },
                editor: {
                    type: 'defaultSelect',
                    options: {
                        valueField: 'value',
                        textField: 'text',
                        data: [
                            <c:forEach items="${rejectCategory }" var="category" varStatus="status">
                            {
                                value: '<c:out value="${category.code }"/>',
                                text: '<c:out value="${category.codename }"/>'
                            }<c:if test="${not status.last}">, </c:if>
                            </c:forEach>
                        ]
                    }
                }
            },
            {field: 'remark', title: '备注', width: 100, align: 'left'}
        ]]
    });

    //历史价格查看
    function showHistoryGoodsPrice() {
        var row = $("#sales-datagrid-rejectBillAddPage").datagrid('getSelected');
        if (row == null) {
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        var businessdate = $("#sales-businessdate-rejectBillAddPage").val();
        var customerid = $("#sales-customer-showid-hidden-dispatchBillAddPage").val();
        var customername = $("#sales-customer-rejectBillAddPage").customerWidget('getText');
        var goodsid = row.goodsid;
        var goodsname = row.goodsInfo.name;
        $("#rejectBill-goods-history-price").dialog({
            title: '客户[' + customerid + '] 商品[' + goodsid + ']' + goodsname + ' 历史价格表',
            width: 600,
            height: 400,
            closed: false,
            modal: true,
            cache: false,
            maximizable: true,
            resizable: true,
            href: 'sales/showRejectBillHistoryGoodsPricePage.do',
            queryParams: {customerid: customerid, goodsid: goodsid, businessdate: businessdate}
        });
    }

    $(function () {
        $("#sales-panel-rejectBill").panel({
            href: receipt_url,
            cache: false,
            maximized: true,
            border: false,
            onLoad: function () {
                $("#sales-customer-rejectBillAddPage").focus();
            }
        });
        //按钮
        $("#sales-buttons-rejectBill").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/sales/rejectBillBack.do">
                {
                    type: 'button-back',
                    handler: function (data) {
                        $("#sales-backid-rejectBill").val(data.id);
                        refreshPanel('sales/rejectBillCheckEditPage.do?id=' + data.id);
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/rejectBillNext.do">
                {
                    type: 'button-next',
                    handler: function (data) {
                        $("#sales-backid-rejectBill").val(data.id);
                        refreshPanel('sales/rejectBillCheckEditPage.do?id=' + data.id);
                    }
                },
                </security:authorize>
                {}
            ],
            buttons: [
                {},
                <security:authorize url="/sales/updateRejectBillCheck.do">
                {
                    id: 'button-savecheck',
                    name: '保存验收',
                    iconCls: 'button-save',
                    handler: function () {
                        var json = $("#sales-datagrid-rejectBillAddPage").datagrid('getRows');
                        $("#sales-goodsJson-rejectBillAddPage").val(JSON.stringify(json));
                        $.messager.confirm('确认', '是否保存并验收当前单据?', function (r) {
                            if (r) {
                                $("#sales-form-check-rejectBillAddPage").submit();
                            }
                        });

                    }
                },
                </security:authorize>
                <security:authorize url="/sales/updateRejectBillCheckCancel.do">
                {
                    id: 'button-checkcancel',
                    name: '取消验收',
                    iconCls: 'icon-removeCheck',
                    handler: function () {
                        var id = $("#sales-id-rejectBillAddPage").val();
                        $.messager.confirm('确认', '是否取消验收当前单据?', function (r) {
                            if (r) {
                                loading("操作中..");
                                $.ajax({
                                    url: 'sales/updateRejectBillCheckCancel.do',
                                    dataType: 'json',
                                    type: 'post',
                                    data: 'id=' + id,
                                    success: function (json) {
                                        loaded();
                                        if (json.flag == true) {
                                            $.messager.alert("提醒", "操作成功");
                                            refreshPanel('sales/rejectBillCheckEditPage.do?id=' + id);
                                        }
                                        else {
                                            $.messager.alert("提醒", json.msg);
                                        }
                                    },
                                    error: function () {
                                        loaded();
                                        $.messager.alert("错误", "操作出错");
                                    }
                                });
                            }
                        });

                    }
                },
                </security:authorize>
                <security:authorize url="/sales/rejectBillSplitPage.do">
                {
                    id: 'button-split',
                    name: '拆分',
                    iconCls: 'button-add',
                    handler: function () {
                        var id = $("#sales-backid-rejectBill").val();
                        if (id == "") {
                            return false;
                        }
                        $("#sales-dialog-split").dialog({
                            title: '单据拆分',
                            fit: true,
                            closed: false,
                            cache: false,
                            modal: true,
                            href: 'sales/rejectBillSplitPage.do?id=' + id,
                            buttons: [
                                {
                                    text: '确定',
                                    handler: function () {
                                        //判断单据数量与拆分数量是否相等，若相等则不能拆分
                                        var rows = $("#sales-datagrid-rejectBillSplitPage").datagrid('getChecked');
                                        if (rows.length == 0) {
                                            $.messager.alert("提醒", "请勾选要拆分的单据!");
                                            return false;
                                        }
                                        var goodsids = "";
                                        var loadrows = $("#sales-datagrid-rejectBillSplitPage").datagrid('getRows');
                                        if (rows.length == loadrows.length) {
                                            for (var i = 0; i < rows.length; i++) {
                                                if (rows[i].unitnum == rows[i].splitnum) {
                                                    var rowindex = $("#sales-datagrid-rejectBillSplitPage").datagrid('getRowIndex', rows[i]);
                                                    $("#sales-datagrid-rejectBillSplitPage").datagrid('uncheckRow', rowindex);
                                                    if (goodsids == "") {
                                                        goodsids = rows[i].goodsid;
                                                    } else {
                                                        goodsids += "," + rows[i].goodsid;
                                                    }
                                                }
                                            }
                                        }
                                        if (goodsids != "") {
                                            $.messager.alert("提醒", "商品编码:" + goodsids + "的单据数量与拆分数量相等的单据,不能拆分!");
                                            return false;
                                        }
                                        $.messager.confirm('确认', '是否根据选中行拆分当前退货通知单，生成新的单据?', function (r) {
                                            if (r) {
                                                $("#sales-form-rejectBillSplitPage").submit();
                                            }
                                        });
                                    }
                                }
                            ]
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/rejectBillCheckPrintView.do">
                {
                    id: 'button-printview-rejectBillCheck',
                    name: '打印预览',
                    iconCls: 'button-preview',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/rejectBillCheckPrint.do">
                {
                    id: 'button-print-rejectBillCheck',
                    name: '打印',
                    iconCls: 'button-print',
                    handler: function () {
                    }
                },
                </security:authorize>
                {}
            ],
            layoutid: 'sales-layout-rejectBill',
            model: 'bill',
            type: 'view',
            tab: '销售退货验收列表',
            taburl: '/sales/rejectBillCheckListPage.do',
            id: '${id}',
            datagrid: 'sales-datagrid-rejectBillCheckListPage'
        });
    });
    function refreshPanel(url) { //更新panel
        $("#sales-panel-rejectBill").panel('refresh', url);
    }
    function countTotal() { //计算合计
        var rows = $("#sales-datagrid-rejectBillAddPage").datagrid('getRows');
        var leng = rows.length;
        var unitnum = 0;
        var taxamount = 0;
        var notaxamount = 0;
        var tax = 0;
        for (var i = 0; i < leng; i++) {
            unitnum += Number(rows[i].unitnum == undefined ? 0 : rows[i].unitnum);
            taxamount += Number(rows[i].taxamount == undefined ? 0 : rows[i].taxamount);
            notaxamount += Number(rows[i].notaxamount == undefined ? 0 : rows[i].notaxamount);
            tax += Number(rows[i].tax == undefined ? 0 : rows[i].tax);
        }
        $("#sales-datagrid-rejectBillAddPage").datagrid('reloadFooter', [{
            goodsid: '合计',
            unitnum: unitnum,
            taxamount: taxamount,
            notaxamount: notaxamount,
            tax: tax
        }]);
    }
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "rejectBillCheckListPage-dialog-print",
            code: "sales_rejectbillcheck",
            url_preview: "print/sales/rejectBillCheckPrintView.do",
            url_print: "print/sales/rejectBillCheckPrint.do",
            btnPreview: "button-printview-rejectBillCheck",
            btnPrint: "button-print-rejectBillCheck",
            printlimit: "${printlimit}",
            getData: function (tableId, printParam) {
                var id = $("#sales-backid-rejectBill").val() || "";
                if (id == "") {
                    $.messager.alert("警告", "找不到要打印的信息!");
                    return false;
                }
                var isinvoice = $("#sales-isinvoice-rejectBillAddPage").val() || "";

                if (isinvoice != '1' && isinvoice != '2' && isinvoice != '3' && isinvoice != '4' && isinvoice != '5') {
                    $.messager.alert("提醒", "抱歉，验收状态下才能打印");
                    return false;
                }

                printParam.idarrs = id;

                var printtimes = $("#sales-ysprinttimes-offpriceAddPage").val() || 0;
                if (printtimes != "0")
                    printParam.printIds = [id];
                return true;
            }
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
