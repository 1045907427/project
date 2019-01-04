<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>开票抽单页面</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="account-buttons-salesInvoicePage"></div>
    </div>
    <div data-options="region:'center'">
        <table id="account-datagrid-salesInvoicePage"></table>
        <div id="account-datagrid-toolbar-salesInvoicePage" style="padding:2px;height:auto">
            <form action="" id="account-form-query-salesInvoicePage" method="post">
                <table class="querytable">
                    <tr>
                        <td>业务日期:</td>
                        <td><input type="text" name="businessdate1" style="width:100px;" class="Wdate"
                                   onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/>
                            到 <input type="text" name="businessdate2" class="Wdate" style="width:100px;"
                                     onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/>
                        </td>
                        <td>单据编号:</td>
                        <td><input type="text" name="billid" style="width: 150px;"/></td>
                        <td>发 票 号:</td>
                        <td><input type="text" name="invoiceno" style="width: 150px;"/></td>
                    </tr>
                    <tr>
                        <td>客&nbsp;&nbsp;户:</td>
                        <td><input id="account-query-customerid" type="text" name="chlidcustomerid"
                                   style="width: 225px;"/></td>
                        <td>总店:</td>
                        <td><input id="account-query-headcustomerid" type="text" name="pcustomerid"/></td>
                        <td>状态:</td>
                        <td><select id="account-queay-salesInvoice-status" name="status" style="width:150px;">
                            <option></option>
                            <option value="2" selected="selected">保存</option>
                            <option value="3">审核通过</option>
                            <option value="4">关闭</option>
                        </select></td>
                    </tr>
                    <tr>
                        <td>开票状态:</td>
                        <td><select name="isinvoicebill" style="width:225px;">
                            <option></option>
                            <option value="0">未开票</option>
                            <option value="1">已开票</option>
                        </select></td>
                        <td>销售区域:</td>
                        <td><input id="account-query-salesarea" type="text" name="salesarea"/></td>
                        <td>订单/退货单编号:</td>
                        <td><input id="account-query-stockid" type="text" name="stockid" class="len150"/></td>
                        <td colspan="2" style="padding-left: 20px">
                            <a href="javaScript:void(0);" id="account-queay-salesInvoice" class="button-qr">查询</a>
                            <a href="javaScript:void(0);" id="account-reload-salesInvoice" class="button-qr">重置</a>
                            <%--<span id="account-query-advanced-salesInvoice"></span>--%>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <div id="account-panel-relation-upper"></div>
    <div id="account-panel-sourceQueryPage"></div>
    <div id="account-dialog-writeoff"></div>
</div>
<div style="display:none">
    <div id="account-invoice-dialog-print">
        <form action="" method="post" id="account-invoice-dialog-print-form">
            <table>
                <tr id="account-invoice-dialog-printtemplet-tr" style="display:none">
                    <td>打印模板：</td>
                    <td>
                        <select id="account-invoice-dialog-printtemplet-id" name="templetid">
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>负商品是否转为折扣:</td>
                    <td>
                        <select name="neggoodstodiscount" style="width:60px">
                            <option value="0">否</option>
                            <option value="1">是</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>非商品(折扣、冲差)是否转为折扣:</td>
                    <td>
                        <select name="zkgoodstodiscount" style="width:60px">
                            <option value="0">否</option>
                            <option value="1" selected="selected">是</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>折扣合并:</td>
                    <td>
                        <select name="isShowDiscountSum" style="width:60px">
                            <option value="0">否</option>
                            <option value="1">是</option>
                        </select>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<script type="text/javascript">
    var initQueryJSON = $("#account-form-query-salesInvoicePage").serializeJSON();
    var SI_footerobject = null;

    //加锁
    function isDoLockData(id, tablename) {
        var flag = false;
        $.ajax({
            url: 'system/lock/isDoLockData.do',
            type: 'post',
            data: {id: id, tname: tablename},
            dataType: 'json',
            async: false,
            success: function (json) {
                flag = json.flag
            }
        });
        return flag;
    }

    $(function () {
        //按钮
        $("#account-buttons-salesInvoicePage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/account/receivable/showSalesInvoiceAddPage.do">
                {
                    type: 'button-add',
                    handler: function () {
                        top.addOrUpdateTab('account/receivable/showSalesInvoiceAddPage.do', "销售核销");
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receivable/salesInvoiceEditPage.do">
                <!--{-->
                <!--	type: 'button-edit',-->
                <!--	handler: function(){-->
                <!--		var con = $("#account-datagrid-salesInvoicePage").datagrid('getSelected');-->
                <!--		if(con == null){-->
                <!--			$.messager.alert("提醒","请选择一条记录");-->
                <!--			return false;-->
                <!--		}	-->
                <!--		top.addOrUpdateTab('account/receivable/showSalesInvoiceEditPage.do?id='+ con.id, "销售发票修改");-->
                <!--	}-->
                <!--},-->
                </security:authorize>
                <security:authorize url="/account/receivable/showSalesInvoiceViewPage.do">
                {
                    type: 'button-view',
                    handler: function () {
                        var con = $("#account-datagrid-salesInvoicePage").datagrid('getSelected');
                        if (con == null) {
                            $.messager.alert("提醒", "请选择一条记录");
                            return false;
                        }
                        var flag = isDoLockData(con.id, "t_account_sales_invoice");
                        if (!flag) {
                            $.messager.alert("警告", "该数据正在被其他人操作，暂不能操作!");
                            return false;
                        }
                        top.addOrUpdateTab('account/receivable/showSalesInvoiceEditPage.do?id=' + con.id, "销售核销查看");
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receivable/salesInvoiceExport.do">
                {
                    type: 'button-export',
                    attr: {}
                },
                </security:authorize>
                {
                    type: 'button-commonquery',
                    attr: {
                        //查询针对的表
                        name: 't_account_sales_invoice',
                        plain: true,
                        //查询针对的表格id
                        datagrid: 'account-datagrid-salesInvoicePage'

                    }
                },
                {}
            ],
            buttons: [
                {},
                <security:authorize url="/account/receivable/salesInvoiceCancel.do">
                {
                    id: 'salesInvoice-cancel-button',
                    name: '回退',
                    iconCls: 'button-back',
                    handler: function () {
                        $.messager.confirm("提醒", "是否回退当前选中发票？", function (r) {
                            if (r) {
                                var rows = $("#account-datagrid-salesInvoicePage").datagrid("getChecked");
                                if (null == rows || rows.length == 0) {
                                    $.messager.alert("提醒", "请选择销售核销！");
                                    return false;
                                }
                                var ids = "";
                                for (var i = 0; i < rows.length; i++) {
                                    if (ids == "") {
                                        ids = rows[i].id;
                                    } else {
                                        ids += "," + rows[i].id;
                                    }
                                }
                                $.ajax({
                                    url: 'account/receivable/salesInvoiceCancel.do?ids=' + ids,
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag) {
                                            if (json.succssids != "") {
                                                $.messager.alert("提醒", "回退成功</br>" + json.succssids + "</br>" + json.errorids);
                                            } else {
                                                $.messager.alert("提醒", "回退失败</br>" + json.succssids + "</br>" + json.errorids);
                                            }
                                            $("#account-datagrid-salesInvoicePage").datagrid("reload");
                                        } else {
                                            $.messager.alert("提醒", "回退失败。只有保存状态的单据才能回退！");
                                        }
                                    },
                                    error: function () {
                                        loaded();
                                        $.messager.alert("错误", "回退出错");
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receivable/salesInvoiceApplyInvoice.do">
                {
                    id: 'apply-writeoff-button',
                    name: '申请开票',
                    iconCls: 'button-audit',
                    handler: function () {
                        var rows = $("#account-datagrid-salesInvoicePage").datagrid("getChecked");
                        if (rows.length == 0) {
                            $.messager.alert("提醒", "请选择销售核销！");
                            return false;
                        }
                        var ids = "";
                        for (var i = 0; i < rows.length; i++) {
                            var obj = rows[i];
                            if (ids == "") {
                                ids = rows[i].id;
                            } else {
                                ids += "," + rows[i].id;
                            }
                        }
                        $.ajax({
                            url: 'account/receivable/checkSalesInvoiceCanMuApplyInvoice.do?ids=' + ids,
                            type: 'post',
                            dataType: 'json',
                            success: function (json) {
                                var msg = "", doids = "";
                                if (json.msg != "") {
                                    msg = json.msg + "<br>确定申请开票？";
                                } else {
                                    msg = "确定申请开票？";
                                }
                                if (json.doids == "") {
                                    $.messager.alert("提醒", json.msg + "没有符合可申请开票的销售核销！");
                                    return false;
                                }
                                $.messager.confirm("提醒", msg, function (r) {
                                    if (r) {
                                        loading('申请开票中...');
                                        $.ajax({
                                            url: 'account/receivable/doSalesInvoiceMuApplyInvoice.do?ids=' + json.doids,
                                            type: 'post',
                                            dataType: 'json',
                                            success: function (json) {
                                                loaded();
                                                var msg = "";
                                                if (json.sucNum != 0) {
                                                    msg = "" + json.sucNum + "条开票申请开票成功;</br>生成销售开票：" + json.salesinvoicebillids;
                                                }
                                                if (json.failids != "") {
                                                    msg = msg + "" + json.failids + "申请开票失败。";
                                                }
//                                                if (json.msg != "") {
//                                                    msg = msg + "<br>" + json.msg;
//                                                }
                                                $.messager.alert("提醒", msg);
                                                $("#account-datagrid-salesInvoiceBillPage").datagrid("reload");
                                            },
                                            error: function () {
                                                loaded();
                                                $.messager.alert("错误", "申请开票出错");
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receivable/showReceiptUnWriteOffPage.do">
                {
                    id: 'button-viewreceipt',
                    name: '查看回单',
                    iconCls: 'button-view',
                    handler: function () {
                        var rows = $("#account-datagrid-salesInvoicePage").datagrid('getChecked');
                        if (rows.length != 1) {
                            $.messager.alert("提醒", "请勾选一条销售核销发票!");
                            return false;
                        }
                        if (rows[0].id != "") {
                            $("#account-dialog-writeoff").dialog({
                                href: "account/receivable/showReceiptUnWriteOffPage.do?invoiceid=" + rows[0].id,
                                title: "未核销回单列表",
                                fit: true,
                                modal: true,
                                cache: false,
                                maximizable: true,
                                resizable: true,
                                cache: false,
                                modal: true
                            });
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receivable/salesInvoicePreview.do">
                {
                    id: 'print-preview-button',
                    name: '打印预览',
                    iconCls: 'button-preview',
                    handler: function () {
                        //orderPrintViewFunc();
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receivable/salesInvoicePrint.do">
                {
                    id: 'print-bill-button',
                    name: '打印',
                    iconCls: 'button-print',
                    handler: function () {
                        //orderPrintFunc();
                    }
                },
                </security:authorize>
                {}
            ],
            model: 'bill',
            type: 'list',
            tname: 't_account_allocate_enter'
        });
        var salesInvoiceJson = $("#account-datagrid-salesInvoicePage").createGridColumnLoad({
            name: 't_account_sales_invoice',
            frozenCol: [[
                {field: 'idok', checkbox: true, isShow: true}
            ]],
            commonCol: [[
                {field: 'id', title: '编号', width: 125, sortable: true},
                {field: 'invoiceno', title: '发票号', width: 80, sortable: true},
                {field: 'businessdate', title: '业务日期', width: 80, sortable: true},
                {field: 'customerid', title: '客户编码', width: 60, sortable: true},
                {field: 'customername', title: '客户名称', width: 100, isShow: true},
                {
                    field: 'chlidcustomername', title: '门店客户名称', width: 100, isShow: true, hidden: true,
                    formatter: function (value, rowData, rowIndex) {
                        return "<span title=\"" + rowData.chlidcustomername + "\">" + rowData.chlidcustomername + "</span>";
                    }
                },
                {
                    field: 'handlerid', title: '对方经手人', width: 80, sortable: true, hidden: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.handlername;
                    }
                },
                {
                    field: 'salesdept', title: '销售部门', width: 100, sortable: true, hidden: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.salesdeptname;
                    }
                },
                {
                    field: 'salesuser', title: '客户业务员', width: 80, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.salesusername;
                    }
                },
                {field: 'sourceid', title: '来源单据编号', width: 130, sortable: true},
                /**
                 {field:'isdiscount',title:'是否折扣',width:60,sortable:true,
                     formatter:function(value,rowData,rowIndex){
                       return getSysCodeName("yesorno",value);
                   }
                 },**/
                {
                    field: 'customeramount',
                    title: '账户余额',
                    resizable: true,
                    sortable: false,
                    align: 'right',
                    isShow: true,
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'taxamount', title: '含税总金额', resizable: true, sortable: true, align: 'right', hidden: true,
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'notaxamount', title: '未税总金额', resizable: true, sortable: true, align: 'right', hidden: true,
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'invoiceamount', title: '应收总金额', resizable: true, sortable: true, align: 'right',
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'writeoffamount', title: '核销总金额', resizable: true, sortable: true, align: 'right',
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'tailamount', title: '尾差金额', resizable: true, sortable: true, align: 'right',
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                {field: 'writeoffusername', title: '核销人', sortable: true, width: 80},
                {field: 'writeoffdate', title: '核销日期', width: 80, sortable: true},
                {field: 'addusername', title: '制单人', width: 80, sortable: true, hidden: true},
                {field: 'addtime', title: '制单时间', width: 80, sortable: true, hidden: true},
                {field: 'auditusername', title: '审核人', width: 80, hidden: true, sortable: true},
                {field: 'audittime', title: '审核时间', width: 80, hidden: true, sortable: true},
                {field: 'stopusername', title: '中止人', width: 80, hidden: true, sortable: true},
                {field: 'stoptime', title: '中止时间', width: 80, hidden: true, sortable: true},
                {
                    field: 'isinvoicebill', title: '开票状态', width: 60, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        if (value == '0') {
                            return "未开票";
                        } else if (value == '1') {
                            return "已开票";
                        }
                    }
                },
                {
                    field: 'iswriteoff', title: '核销状态', width: 60, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        if (value == '0') {
                            return "未核销";
                        } else if (value == '1') {
                            return "已核销";
                        }
                    }
                },
                {
                    field: 'status', title: '状态', width: 60, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return getSysCodeName("status", value);
                    }
                },
                {field: 'vouchertimes', title: '生成凭证次数', align: 'center', width:80},
                {
                    field: 'printtip', title: '打印提示', width: 60, align: 'left', isShow: true,
                    formatter: function (value, row, index) {
                        if (row.status == '3') {
                            return "可打印";
                        }
                    }
                },
                {field: 'printtimes', title: '打印次数', align: 'center', width: 60},
                {field: 'remark', title: '备注', width: 80, sortable: true}
            ]]
        });
        $("#account-datagrid-salesInvoicePage").datagrid({
            authority: salesInvoiceJson,
            frozenColumns: salesInvoiceJson.frozen,
            columns: salesInvoiceJson.common,
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
            url: 'account/receivable/showSalesInvoiceList.do',
            queryParams: initQueryJSON,
            toolbar: '#account-datagrid-toolbar-salesInvoicePage',
            onDblClickRow: function (rowIndex, rowData) {
                var flag = isDoLockData(rowData.id, "t_account_sales_invoice");
                if (!flag) {
                    $.messager.alert("警告", "该数据正在被其他人操作，暂不能操作!");
                    return false;
                }
                top.addOrUpdateTab('account/receivable/showSalesInvoiceEditPage.do?id=' + rowData.id, "销售核销查看");
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
            },
            onLoadSuccess: function () {
                var footerrows = $(this).datagrid('getFooterRows');
                if (null != footerrows && footerrows.length > 0) {
                    SI_footerobject = footerrows[0];
                    countTotalAmount();
                }
            }
        }).datagrid("columnMoving");
        $("#account-query-customerid").customerWidget({
            width: 228,
            isall: true,
            isdatasql: false,
            singleSelect: true
        });
        $("#account-query-headcustomerid").widget({
            referwid: 'RL_T_BASE_SALES_CUSTOMER_PARENT',
            width: 150,
            view: true,
            singleSelect: true
        });
        $("#account-query-salesarea").widget({
            referwid: 'RT_T_BASE_SALES_AREA',
            width: 150,
            view: true,
            singleSelect: false,
            onlyLeafCheck: false
        });
        //通用查询组建调用
//			$("#account-query-advanced-salesInvoice").advancedQuery({
//				//查询针对的表
//		 		name:'t_account_sales_invoice',
//		 		//查询针对的表格id
//		 		datagrid:'account-datagrid-salesInvoicePage',
//		 		plain:true
//			});

        //回车事件
        controlQueryAndResetByKey("account-queay-salesInvoice", "account-reload-salesInvoice");

        //查询
        $("#account-queay-salesInvoice").click(function () {
            //把form表单的name序列化成JSON对象
            var queryJSON = $("#account-form-query-salesInvoicePage").serializeJSON();
            $("#account-datagrid-salesInvoicePage").datagrid("load", queryJSON);
        });
        //重置
        $("#account-reload-salesInvoice").click(function () {
            $("#account-query-headcustomerid").widget("clear");
            $("#account-query-salesarea").widget("clear");
            $("#account-query-customerid").customerWidget("clear");
            $("#account-form-query-salesInvoicePage")[0].reset();
            var queryJSON = $("#account-form-query-salesInvoicePage").serializeJSON();
            $("#account-datagrid-salesInvoicePage").datagrid("load", queryJSON);
        });
    });

    //计算勾选销售发票合计
    function countTotalAmount() {
        var rows = $("#account-datagrid-salesInvoicePage").datagrid('getChecked');
        var customeramount = 0, taxamount = 0, notaxamount = 0, discountamount = 0, invoiceamount = 0, writeoffamount = 0, tailamount = 0;
        for (var i = 0; i < rows.length; i++) {
            taxamount = Number(taxamount) + Number(rows[i].taxamount == undefined ? 0 : rows[i].taxamount);
            notaxamount = Number(notaxamount) + Number(rows[i].notaxamount == undefined ? 0 : rows[i].notaxamount);
            discountamount = Number(discountamount) + Number(rows[i].discountamount == undefined ? 0 : rows[i].discountamount);
            invoiceamount = Number(invoiceamount) + Number(rows[i].invoiceamount == undefined ? 0 : rows[i].invoiceamount);
            writeoffamount = Number(writeoffamount) + Number(rows[i].writeoffamount == undefined ? 0 : rows[i].writeoffamount);
            tailamount = Number(tailamount) + Number(rows[i].tailamount == undefined ? 0 : rows[i].tailamount);
        }
        var foot = [
            {
                id: '选中金额',
                taxamount: taxamount,
                notaxamount: notaxamount,
                discountamount: discountamount,
                invoiceamount: invoiceamount,
                writeoffamount: writeoffamount,
                tailamount: tailamount
            }
        ];
        if (null != SI_footerobject) {
            foot.push(SI_footerobject);
        }
        $("#account-datagrid-salesInvoicePage").datagrid("reloadFooter", foot);
    }

    function reloadSalesInvoiceList() {
        //把form表单的name序列化成JSON对象
        var status = $("#account-queay-salesInvoice-status").val();
        $("#account-queay-salesInvoice-status").val(status);
        var queryJSON = $("#account-form-query-salesInvoicePage").serializeJSON();
        $("#account-datagrid-salesInvoicePage").datagrid("load", queryJSON);
    }
</script>
<%--打印开始 --%>
<script type="text/javascript">
    function isCanPrintData(invoiceidarrs) {
        var result = {'flag': true, 'msg': ''};

        loading("系统判断选择单据是否可以打印...");
        $.ajax({
            url: 'account/receivable/salesInvoiceDetailTaxtypeCount.do',
            type: 'post',
            data: {invoiceidarrs: invoiceidarrs},
            dataType: 'json',
            async: false,
            success: function (data) {
                loaded();
                var flag = true;
                var msgsb = [];
                if (data == null || data.length == 0) {
                    return result;
                }
                var msgid = 1;
                for (var i = 0; i < data.length; i++) {
                    if (data[i].taxtypecount > 1) {
                        flag = false;
                        msgsb.push(msgid + "）单据" + data[i].billid + "明细中有不同税种");
                        msgid = msgid + 1;
                    }
                }
                if (!flag) {
                    result = {'flag': flag, 'msg': msgsb.join('<br/>')};
                }
            },
            error: function () {
                loaded();
            }
        });
        return result;
    }
    $(function () {
        if (AgReportPrint.isShowPrintTempletManualSelect("sales_invoice")) {
            $("#account-invoice-dialog-printtemplet-tr").show();
            //初始化打印对话框
            AgReportPrint.createPrintTempletSelectOption('account-invoice-dialog-printtemplet-id', 'sales_invoice');
        }
        //打印
        AgReportPrint.init({
            id: "account-invoice-dialog-print",
            code: "sales_invoice",
            tableId: "account-datagrid-salesInvoicePage",
            url_preview: "print/account/salesInvoicePrintView.do",
            url_print: "print/account/salesInvoicePrint.do",
            btnPreview: "print-preview-button",
            btnPrint: "print-bill-button",
            getData: getData
        });
        function getData(tableId, printParam) {
            var data = $("#" + tableId).datagrid('getChecked');
            if (data == null || data.length == 0) {
                $.messager.alert("提醒", "请选择一条记录");
                return false;
            }
            var idarrs = [];
            for (var i = 0; i < data.length; i++) {
                if (data[i].status != '3' && data[i].status != '4') {
                    $.messager.alert("提醒", data[i].id + "此发票清单不可打印预览");
                    return false;
                }
                idarrs.push(data[i].id);
            }
            var canprintdata = isCanPrintData(idarrs.join(','));
            if (canprintdata != null && canprintdata.flag == false && canprintdata.msg != "") {
                $.messager.alert('提醒', canprintdata.msg);
                return false;
            }
            return data;
        }
    });
</script>
<%--打印结束 --%>
</body>
</html>
