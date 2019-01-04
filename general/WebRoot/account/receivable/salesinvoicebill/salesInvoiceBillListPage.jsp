<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>申请开票列表页面</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>

<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="account-buttons-salesInvoiceBillPage"></div>
    </div>
    <div data-options="region:'center'">
        <table id="account-datagrid-salesInvoiceBillPage"></table>
        <div id="account-datagrid-toolbar-salesInvoiceBillPage" style="padding:2px;height:auto">
            <form action="" id="account-form-query-salesInvoiceBillPage" method="post">
                <input type="hidden" name="selectids"/>
                <table class="querytable">
                    <tr>
                        <td>业务日期:</td>
                        <td><input type="text" name="businessdate1" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/>
                            到 <input type="text" name="businessdate2" class="Wdate" style="width:100px;"
                                     onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/>
                        </td>
                        <td>发票号:</td>
                        <td><input type="text" name="invoiceno" style="width: 150px;"/></td>
                        <td>单据编号:</td>
                        <td><input type="text" name="id" style="width: 128px;"/></td>
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
                    </tr>
                    <tr>
                        <td>客 户:</td>
                        <td><input id="account-query-customerid" type="text" name="chlidcustomerid"
                                   style="width:225px;"/></td>
                        <td>总 店:</td>
                        <td><input id="account-query-headcustomerid" type="text" name="pcustomerid"/></td>
                        <td>销售部门:</td>
                        <td><input id="account-query-salesdept" type="text" name="salesdept"/></td>
<c:if test="${useHTKPExport=='1'}">
                        <td>金税导出:</td>
                        <td>
                            <select name="jsexportsign" style="width:150px;">
                                <option></option>
                                <option value="1">未导出</option>
                                <%-- 特别
                                <option value="2">小于</option>
                                <option value="3">小于等于</option>
                                 --%>
                                <option value="4">已导出</option>
                                <%-- 特别
                                <option value="5">大于等于</option>
                                 --%>
                            </select>
                            <input type="hidden" name="queryjxexporttimes" value="0"/>
                        </td>
</c:if>
                    </tr>
                    <tr>
                        <td>开票类型:</td>
                        <td>
                            <select id="account-query-billtype" name="billtype" style="width: 225px">
                                <option></option>
                                <option value="1">正常开票</option>
                                <option value="2">预开票</option>
                            </select>
                        </td>
                        <td>状 态:</td>
                        <td>
                            <select id="account-queay-salesInvoice-status" name="status" style="width:150px;">
                                <option></option>
                                <option value="2" selected="selected">保存</option>
                                <option value="3">审核通过</option>
                                <option value="4">关闭</option>
                            </select>
                        </td>
                        <td>订单/退货单编号:</td>
                        <td><input id="account-query-stockid" type="text" name="stockid" style="width:128px;"/></td>
                        <td colspan="2">
                            <a href="javaScript:void(0);" id="account-queay-salesInvoiceBill" class="button-qr">查询</a>
                            <a href="javaScript:void(0);" id="account-reload-salesInvoiceBill" class="button-qr">重置</a>
                            <%--<span id="account-query-advanced-salesInvoiceBill"></span>--%>
                        </td>

                    </tr>
                </table>
            </form>
        </div>
    </div>
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
<div id="account-panel-relation-upper"></div>
<div id="account-panel-sourceQueryPage"></div>
<div id="account-dialog-writeoff"></div>
<div id="account-addVoucher-dialog"></div>
<script type="text/javascript">
    var initQueryJSON = $("#account-form-query-salesInvoiceBillPage").serializeJSON();
    var SI_footerobject = null;
    $(function () {
        //按钮
        $("#account-buttons-salesInvoiceBillPage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/account/receivable/salesInvoiceBillAddPage.do">
                {
                    type: 'button-add',
                    handler: function () {
                        top.addOrUpdateTab('account/receivable/showSalesInvoiceBillAddPage.do', "申请开票");
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receivable/showSalesInvoiceBillViewPage.do">
                {
                    type: 'button-view',
                    handler: function () {
                        var con = $("#account-datagrid-salesInvoiceBillPage").datagrid('getSelected');
                        if (con == null) {
                            $.messager.alert("提醒", "请选择一条记录");
                            return false;
                        }
                        top.addOrUpdateTab('account/receivable/showSalesInvoiceBillEditPage.do?id=' + con.id, "销售开票查看");
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receivable/exportSalesInvoiceBillData.do">
                {
                    type: 'button-export',
                    attr: {
                        datagrid:"#account-datagrid-salesInvoiceBillPage",
                        queryForm: "#account-form-query-salesInvoiceBillPage", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
                        type:'exportUserdefined',
                        name:'销售开票列表',
                        url:'account/receivable/exportSalesInvoiceBillData.do'
                    }
                },
                </security:authorize>
                {
                    type: 'button-commonquery',
                    attr: {
                        name: 't_account_sales_invoicebill',
                        datagrid: 'account-datagrid-salesInvoiceBillPage',
                        plain: true
                    }
                },
                {}
            ],
            buttons: [
                {},
                <security:authorize url="/account/receivable/salesAdvanceBillAddPage.do">
                {
                    id: 'salesAdvanceBill-add-button',
                    name: '预开票',
                    iconCls: 'button-add',
                    handler: function () {
                        top.addOrUpdateTab('account/receivable/showSalesAdvanceBillAddPage.do', "申请预开票");
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receivable/salesInvoiceBillCancel.do">
                {
                    id: 'salesInvoiceBill-cancel-button',
                    name: '回退',
                    iconCls: 'button-back',
                    handler: function () {
                        $.messager.confirm("提醒", "是否回退当前选中开票？", function (r) {
                            if (r) {
                                var rows = $("#account-datagrid-salesInvoiceBillPage").datagrid("getChecked");
                                if (null == rows || rows.length == 0) {
                                    $.messager.alert("提醒", "请选择销售开票！");
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
                                    url: 'account/receivable/salesInvoiceBillCancel.do?ids=' + ids,
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
                                            $("#account-datagrid-salesInvoiceBillPage").datagrid("reload");
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
                <security:authorize url="/account/receivable/salesInvoiceBillApplyWriteOff.do">
                {
                    id: 'apply-writeoff-button',
                    name: '申请核销',
                    iconCls: 'button-writeoff',
                    handler: function () {
                        var rows = $("#account-datagrid-salesInvoiceBillPage").datagrid("getChecked");
                        if (rows.length == 0) {
                            $.messager.alert("提醒", "请选择销售开票！");
                            return false;
                        }
                        var undonum = 0;
                        var ids = "";
                        for (var i = 0; i < rows.length; i++) {
                            var obj = rows[i];
                            if (obj.status != "3") {
                                undonum++;
                                var index = $("#account-datagrid-salesInvoiceBillPage").datagrid("getRowIndex", obj);
                                $("#account-datagrid-salesInvoiceBillPage").datagrid("uncheckRow", index);
                            } else {
                                if (ids == "") {
                                    ids = rows[i].id;
                                } else {
                                    ids += "," + rows[i].id;
                                }
                            }
                        }
                        if (undonum != 0) {
                            $.messager.alert("提醒", "只有审核通过的数据才能申请核销！");
                            return false;
                        }
                        if ("" == ids) {
                            $.messager.alert("提醒", "请选择销售开票！");
                            return false;
                        }
                        $.ajax({
                            url: 'account/receivable/checkSalesInvoiceBillCanMuApplyWriteOff.do?ids=' + ids,
                            type: 'post',
                            dataType: 'json',
                            success: function (json) {
                                //判断预开票是否可申请核销
                                if (json.unadvanceids != "") {
                                    var unadvanceidArr = new Array();
                                    unadvanceidArr = json.unadvanceids.split(",");
                                    for (i = 0; i < unadvanceidArr.length; i++) {
                                        var index = $("#account-datagrid-salesInvoiceBillPage").datagrid("getRowIndex", unadvanceidArr[i]);
                                        $("#account-datagrid-salesInvoiceBillPage").datagrid("uncheckRow", index);
                                    }
                                }

                                var msg = "";
                                if (json.msg != "") {
                                    msg = json.msg + "<br>确定申请核销？";
                                } else {
                                    msg = "确定申请核销？";
                                }
                                if (json.doids == "") {
                                    $.messager.alert("提醒", json.advancemsg + "<br>" + json.msg + "没有符合可申请核销的销售开票！");
                                    $("#account-datagrid-salesInvoiceBillPage").datagrid("clearChecked");
                                    return false;
                                } else {
                                    if (json.unadvanceids != "") {
                                        $.messager.alert("提醒", json.advancemsg);
                                    }
                                }
                                $.messager.confirm("提醒", msg, function (r) {
                                    if (r) {
                                        loading('申请核销中...');
                                        $.ajax({
                                            url: 'account/receivable/doSalesInvoiceBillMuApplyWriteOff.do?ids=' + json.doids,
                                            type: 'post',
                                            dataType: 'json',
                                            success: function (json) {
                                                loaded();
                                                var msg = "";
                                                if (json.sucNum != 0) {
                                                    msg = "" + json.sucNum + "条开票申请核销成功;</br>生成销售发票：" + json.salesinvoiceids;
                                                }
                                                if (json.failids != "") {
                                                    msg = msg + "" + json.failids + "申请核销失败。";
                                                }
                                                if (json.msg != "") {
                                                    msg += "</br>" + json.msg;
                                                }
                                                $.messager.alert("提醒", msg);
                                                $("#account-datagrid-salesInvoiceBillPage").datagrid("reload");
                                            },
                                            error: function () {
                                                loaded();
                                                $.messager.alert("错误", "申请核销出错");
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receivable/salesInvoiceBillPreview.do">
                {
                    id: 'print-preview-button',
                    name: '打印预览',
                    iconCls: 'button-preview',
                    handler: function () {
                        //orderPrintViewFunc();
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receivable/salesInvoiceBillPrint.do">
                {
                    id: 'print-bill-button',
                    name: '打印',
                    iconCls: 'button-print',
                    handler: function () {
                        //orderPrintFunc();
                    }
                },
                </security:authorize>
                <security:authorize url="/erpconnect/addSalesInvoiceBillVouch.do">
                {
                    id: 'button-taxvoucher',
                    name: '生成凭证',
                    iconCls: 'button-audit',
                    handler: function () {
                        var rows = $("#account-datagrid-salesInvoiceBillPage").datagrid('getChecked');
                        if (rows == null || rows.length == 0) {
                            $.messager.alert("提醒", "请选择至少一条记录");
                            return false;
                        }
                        var ids = "";
                        for (var i = 0; i < rows.length; i++) {
                            if (rows[i].status != '4' && rows[i].status != '3') {
                                $.messager.alert("提醒", "请选择已审核数据");
                                return false;
                            }
                            if (i == 0) {
                                ids = rows[i].id;
                            } else {
                                ids += "," + rows[i].id;
                            }
                        }
                        $("#account-addVoucher-dialog").dialog({
                            title: '申请开票凭证',
                            width: 400,
                            height: 320,
                            closed: false,
                            modal: true,
                            cache: false,
                            href: 'erpconnect/showSalesInvoiceBillVoucherPage.do',
                            onLoad: function () {
                                $("#Account-ids").val(ids);
                            }
                        });
                    }
                },
                </security:authorize>
                <%--<security:authorize url="/erpconnect/addSalesInvoiceBillNotaxVouch.do">--%>
                <%--{--%>
                    <%--id: 'button-notaxvoucher',--%>
                    <%--name: '生成未税凭证',--%>
                    <%--iconCls: 'button-audit',--%>
                    <%--handler: function () {--%>
                        <%--var rows = $("#account-datagrid-salesInvoiceBillPage").datagrid('getChecked');--%>
                        <%--if (rows == null || rows.length == 0) {--%>
                            <%--$.messager.alert("提醒", "请选择至少一条记录");--%>
                            <%--return false;--%>
                        <%--}--%>
                        <%--var ids = "";--%>
                        <%--for (var i = 0; i < rows.length; i++) {--%>
                            <%--if (rows[i].status != '4' && rows[i].status != '3') {--%>
                                <%--$.messager.alert("提醒", "请选择已审核数据");--%>
                                <%--return false;--%>
                            <%--}--%>
                            <%--if (i == 0) {--%>
                                <%--ids = rows[i].id;--%>
                            <%--} else {--%>
                                <%--ids += "," + rows[i].id;--%>
                            <%--}--%>
                        <%--}--%>
                        <%--$("#account-addVoucher-dialog").dialog({--%>
                            <%--title: '申请开票未税凭证',--%>
                            <%--width: 400,--%>
                            <%--height: 260,--%>
                            <%--closed: false,--%>
                            <%--modal: true,--%>
                            <%--cache: false,--%>
                            <%--href: 'erpconnect/showSalesInvoiceBillVoucherPage.do?type=notax',--%>
                            <%--onLoad: function () {--%>
                                <%--$("#Account-ids").val(ids);--%>
                            <%--}--%>
                        <%--});--%>
                    <%--}--%>
                <%--},--%>
                <%--</security:authorize>--%>
                <security:authorize url="/erpconnect/addSalesCostVouch.do">
                {
                    id: 'button-costvoucher',
                    name: '生成销售成本凭证',
                    iconCls: 'button-audit',
                    handler: function () {
                        var rows = $("#account-datagrid-salesInvoiceBillPage").datagrid('getChecked');
                        if (rows == null || rows.length == 0) {
                            $.messager.alert("提醒", "请选择至少一条记录");
                            return false;
                        }
                        var ids = "";
                        for (var i = 0; i < rows.length; i++) {
                            if (rows[i].status != '4' && rows[i].status != '3') {
                                $.messager.alert("提醒", "请选择已审核数据");
                                return false;
                            }
                            if (i == 0) {
                                ids = rows[i].id;
                            } else {
                                ids += "," + rows[i].id;
                            }
                        }
                        $("#account-addVoucher-dialog").dialog({
                            title: '申请开票销售成本凭证',
                            width: 400,
                            height: 260,
                            closed: false,
                            modal: true,
                            cache: false,
                            href: 'erpconnect/showSalesInvoiceBillVoucherPage.do?type=cost',
                            onLoad: function () {
                                $("#Account-ids").val(ids);
                            }
                        });
                    }
                },
                </security:authorize>
                {}
            ],
            model: 'bill',
            type: 'list',
            tname: 't_account_allocate_enter'
        });
        var salesInvoiceBillJson = $("#account-datagrid-salesInvoiceBillPage").createGridColumnLoad({
            name: 't_account_sales_invoicebill',
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
                {
                    field: 'billtype', title: '开票类型', width: 60, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        if ("1" == value) {
                            return "正常开票";
                        } else if ("2" == value) {
                            return "预开票";
                        }
                    }
                },
                {
                    field: 'iswriteoff', title: '核销状态', width: 60, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        if ("0" == value) {
                            return "未核销";
                        } else if ("1" == value) {
                            return "已核销";
                        }
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
                    field: 'taxamount', title: '含税总金额', resizable: true, sortable: true, align: 'right',
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
                    field: 'customeramount', title: '客户余额', resizable: true, align: 'right', isShow: true,
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                {field: 'addusername', title: '制单人', width: 80, sortable: true, hidden: true},
                {field: 'addtime', title: '制单时间', width: 80, sortable: true, hidden: true},
                {field: 'auditusername', title: '审核人', width: 80, hidden: true, sortable: true},
                {field: 'audittime', title: '审核时间', width: 80, hidden: true, sortable: true},
                {field: 'stopusername', title: '中止人', width: 80, hidden: true, sortable: true},
                {field: 'stoptime', title: '中止时间', width: 80, hidden: true, sortable: true},
                {
                    field: 'status', title: '状态', width: 60, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return getSysCodeName("status", value);
                    }
                },
                {field: 'vouchertimes', title: '生成凭证次数', align: 'center', width:80},
                {field: 'printtimes', title: '打印次数', align: 'center', width: 60},
<c:if test="${useHTKPExport=='1'}">
                {field:'jxexportstate',title:'导出状态',width:80,isShow:true,
                    formatter:function(value,rowData,index){
                        if(rowData.jxexporttimes>0){
                            return "已导出";
                        }else if(rowData.jxexporttimes==null){
                            return "";
                        }else{
                            return "未导出";
                        }
                    }
                },
                {field:'jxexporttimes',title:'导出次数',align:'center',width:60,
                    formatter:function(value,rowData,index){
                        if(value == null){
                            return "";
                        }else{
                            return value;
                        }
                    }
                },
</c:if>
                {field: 'remark', title: '备注', width: 80, sortable: true}
            ]]
        });
        $("#account-datagrid-salesInvoiceBillPage").datagrid({
            authority: salesInvoiceBillJson,
            frozenColumns: salesInvoiceBillJson.frozen,
            columns: salesInvoiceBillJson.common,
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
            url: 'account/receivable/getSalesInvoiceBillData.do',
            queryParams: initQueryJSON,
            toolbar: '#account-datagrid-toolbar-salesInvoiceBillPage',
            onDblClickRow: function (rowIndex, rowData) {
                top.addOrUpdateTab('account/receivable/showSalesInvoiceBillEditPage.do?id=' + rowData.id, "销售开票查看");
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
        $("#account-query-salesdept").widget({
            name: 't_account_sales_invoice',
            width: 128,
            col: 'salesdept',
            view: true,
            singleSelect: true
        });
        //通用查询组建调用
        $("#account-query-advanced-salesInvoiceBill").advancedQuery({
            name: 't_account_sales_invoicebill',
            datagrid: 'account-datagrid-salesInvoiceBillPage',
            plain: true
        });

        //回车事件
        controlQueryAndResetByKey("account-queay-salesInvoiceBill", "account-reload-salesInvoiceBill");

        //查询
        $("#account-queay-salesInvoiceBill").click(function () {
            $("input[name='selectids']").val("");
            var queryJSON = $("#account-form-query-salesInvoiceBillPage").serializeJSON();
            $("#account-datagrid-salesInvoiceBillPage").datagrid("load", queryJSON);
        });
        //重置
        $("#account-reload-salesInvoiceBill").click(function () {
            $("input[name='selectids']").val("");
            $("#account-query-headcustomerid").widget("clear");
            $("#account-query-customerid").customerWidget("clear");
            $("#account-query-salesdept").widget("clear");
            $("#account-form-query-salesInvoiceBillPage")[0].reset();
            var queryJSON = $("#account-form-query-salesInvoiceBillPage").serializeJSON();
            $("#account-datagrid-salesInvoiceBillPage").datagrid("load", queryJSON);
        });
    });

    //计算勾选销售开票合计
    function countTotalAmount() {
        var rows = $("#account-datagrid-salesInvoiceBillPage").datagrid('getChecked');
        var selectids="";
        for(var i=0;i<rows.length;i++){
            if(selectids==""){
                selectids=rows[i].id;
            }else{
                selectids+=","+rows[i].id;
            }
        }
        $("input[name='selectids']").val(selectids);

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
        $("#account-datagrid-salesInvoiceBillPage").datagrid("reloadFooter", foot);
    }

    function reloadSalesInvoiceList() {
        var status = $("#account-queay-salesInvoiceBill-status").val();
        $("#account-queay-salesInvoiceBill-status").val(status);
        var queryJSON = $("#account-form-query-salesInvoiceBillPage").serializeJSON();
        $("#account-datagrid-salesInvoiceBillPage").datagrid("load", queryJSON);
    }
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        if (AgReportPrint.isShowPrintTempletManualSelect("sales_invoicebill")) {
            $("#account-invoice-dialog-printtemplet-tr").show();
            //初始化打印对话框
            AgReportPrint.createPrintTempletSelectOption('account-invoice-dialog-printtemplet-id', 'sales_invoicebill');
        }
        //打印
        AgReportPrint.init({
            id: "account-invoice-dialog-print",
            code: "sales_invoicebill",
            tableId: "account-datagrid-salesInvoiceBillPage",
            url_preview: "print/account/salesInvoiceBillPrintView.do",
            url_print: "print/account/salesInvoiceBillPrint.do",
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
            for (var i = 0; i < data.length; i++) {
                if (data[i].status != '3' && data[i].status != '4') {
                    $.messager.alert("提醒", data[i].id + "此发票清单不可打印预览");
                    return false;
                }
            }
            return data;
        }
    });
</script>
<%--打印结束 --%>
</body>
</html>
