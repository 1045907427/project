<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>客户应收款冲差列表页面</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>

<body>
<security:authorize url="/account/receivable/customerPushBanlanceHoldSave.do">
    <input type="hidden" id="account-btn-permission-hold" value="1"/>
</security:authorize>
<security:authorize url="/account/receivable/customerPushBanlanceSave.do">
    <input type="hidden" id="account-btn-permission" value="1"/>
</security:authorize>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="account-buttons-customerPushBanlancePage" style="height:26px;"></div>
    </div>
    <div data-options="region:'center'">
        <table id="account-datagrid-customerPushBanlancePage" data-options="border:false"></table>
    </div>
</div>
<div id="account-datagrid-toolbar-customerPushBanlancePage">
    <form action="" id="account-form-query-customerPushBanlancePage" method="post">
        <input type="hidden" name="ids" id="account-datagrid-checked-ids"/>
        <table class="querytable">
            <tr>
                <td>业务日期:</td>
                <td><input type="text" name="businessdate1" style="width:100px;" class="Wdate"
                           onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/> 到 <input type="text" name="businessdate2"
                                                                                      class="Wdate" style="width:100px;"
                                                                                      onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/>
                </td>
                <td>编号:</td>
                <td><input type="text" name="id" style="width: 180px;" value="${id }"/></td>
                <td>状态:</td>
                <td><select id="select-status-customerPushBanlance" name="status" style="width:165px;">
                    <option></option>
                    <c:choose>
                        <c:when test="${pushstatus == 1}">
                            <option value='1' selected='selected'>暂存</option>
                            <option value='2'>保存</option>
                        </c:when>
                        <c:otherwise>
                            <option value='2' selected='selected'>保存</option>
                        </c:otherwise>
                    </c:choose>
                    <option value="3" <c:if test="${status == '3'}">selected='selected'</c:if>>审核通过</option>
                    <option value="4" <c:if test="${status == '4'}">selected='selected'</c:if>>关闭</option>
                </select>
                </td>
            </tr>
            <tr>
                <td>客户:</td>
                <td><input id="account-query-customerid" type="text" name="customerid" style="width: 225px;"/></td>
                <td>单据类型:</td>
                <td>
                    <select name="isinvoice" style="width: 180px;">
                        <option></option>
                        <option value="0">正常冲差</option>
                        <option value="1">发票冲差</option>
                        <option value="2">回单冲差</option>
                    </select>
                </td>
                <td>品牌:</td>
                <td>
                    <input id="account-query-brandid" type="text" name="brandid" style="width: 165px;"/>
                </td>
            </tr>
            <tr>
                <td>备注:</td>
                <td>
                    <input id="account-query-remark" type="text" name="remark" style="width: 225px;"/>
                </td>
                <td>费用科目:</td>
                <td>
                    <input id="account-query-subject" type="text" name="subject" style="width: 180px;"/>
                </td>
                <td colspan="2">
                    <a href="javaScript:void(0);" id="account-queay-customerPushBanlance" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="account-reload-customerPushBanlance" class="button-qr">重置</a>
                    <span id="account-query-advanced-customerPushBanlance"></span>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="account-panel-customerPushBanlance-addpage"></div>
<script type="text/javascript">
    var customerPushBanlance_AjaxConn = function (Data, Action) {
        var MyAjax = $.ajax({
            type: 'post',
            cache: false,
            url: Action,
            data: Data,
            async: false
        })
        return MyAjax.responseText;
    }

    //根据数据冲差金额获取其未税金额、税额
    function getPushBanlanceNoTaxAmount() {
        var amount = $("#account-customerPushBanlance-amount").numberbox('getValue');
        var defaulttaxtype = $("#account-customerPushBanlance-defaulttaxtype").widget('getValue');
        var ret = customerPushBanlance_AjaxConn({
            amount: amount,
            defaulttaxtype: defaulttaxtype
        }, 'account/receivable/getPushBanlanceNoTaxAmount.do');
        var retjson = $.parseJSON(ret);
        $("#account-customerPushBanlance-notaxamount").numberbox('setValue', retjson.notaxamount);
        $("#account-customerPushBanlance-tax").numberbox('setValue', retjson.tax);
    }

    var initQueryJSON = $("#account-form-query-customerPushBanlancePage").serializeJSON();
    var footerobject = null;
    $(function () {
        //按钮
        $("#account-buttons-customerPushBanlancePage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/account/receivable/showCustomerPushBanlanceAddPage.do">
                {
                    type: 'button-add',
                    handler: function () {
                        $('#account-panel-customerPushBanlance-addpage').dialog({
                            title: '客户应收款冲差新增',
                            width: 400,
                            height: 400,
                            collapsible: false,
                            minimizable: false,
                            maximizable: true,
                            resizable: true,
                            closed: true,
                            cache: false,
                            href: 'account/receivable/showCustomerPushBanlanceAddPage.do',
                            modal: true,
                            onLoad: function () {
                                $("#account-customerPushBanlance-customerid").focus();
                            }
                        });
                        $('#account-panel-customerPushBanlance-addpage').dialog("open");
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receivable/showCustomerPushBanlanceEditPage.do">
                {
                    type: 'button-edit',
                    handler: function () {
                        var con = $("#account-datagrid-customerPushBanlancePage").datagrid('getSelected');
                        if (con == null) {
                            $.messager.alert("提醒", "请选择一条记录");
                            return false;
                        }
                        $('#account-panel-customerPushBanlance-addpage').dialog({
                            title: '客户应收款冲差修改',
                            width: 400,
                            height: 400,
                            collapsible: false,
                            minimizable: false,
                            maximizable: true,
                            resizable: true,
                            closed: true,
                            cache: false,
                            href: 'account/receivable/showCustomerPushBanlanceEditPage.do?id=' + con.id,
                            modal: true,
                            onLoad: function () {
                                $("#account-customerPushBanlance-customerid").focus();
                            }
                        });
                        $('#account-panel-customerPushBanlance-addpage').dialog("open");
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receivable/deleteCustomerPushBanlance.do">
                {
                    type: 'button-delete',
                    handler: function () {
                        var rows = $("#account-datagrid-customerPushBanlancePage").datagrid('getChecked');
                        if (rows == null) {
                            $.messager.alert("提醒", "请选择记录");
                            return false;
                        }
                        var deleteFlag = true;
                        var ids = "";
                        for (var i = 0; i < rows.length; i++) {
                            if (rows[i].isinvoice != '0') {
                                deleteFlag = false;
                                break;
                            } else {
                                if (ids == "") {
                                    ids = rows[i].id;
                                } else {
                                    ids += "," + rows[i].id;
                                }
                            }
                        }
                        if (!deleteFlag) {
                            $.messager.alert("提醒", "该冲差单不能删除。只有保存状态的正常冲差单才能删除。");
                            return false;
                        }
                        $.messager.confirm("提醒", "是否删除该客户应收款冲差单？", function (r) {
                            if (r) {
                                $.ajax({
                                    url: 'account/receivable/deleteCustomerPushBanlance.do?ids=' + ids,
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (json) {
                                        if (json.flag) {
                                            var msg = "";
                                            if (json.successids != null && json.successids != "") {
                                                msg = "成功编号:" + json.successids;
                                            }
                                            if (json.errorids != null && json.errorids != "") {
                                                msg += "失败编号:" + json.errorids;
                                            }
                                            $.messager.alert("提醒", "删除成功。" + msg);
                                            $("#account-datagrid-customerPushBanlancePage").datagrid("reload");
                                        } else {
                                            $.messager.alert("提醒", "删除失败");
                                        }
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receivable/auditCustomerPushBanlance.do">
                {
                    type: 'button-audit',
                    handler: function () {
                        var rows = $("#account-datagrid-customerPushBanlancePage").datagrid('getChecked');
                        if (rows == null) {
                            $.messager.alert("提醒", "请选择记录");
                            return false;
                        }
                        var deleteFlag = true;
                        var ids = "";
                        for (var i = 0; i < rows.length; i++) {
                            if (rows[i].isinvoice != '0') {
                                deleteFlag = false;
                                break;
                            } else {
                                if (ids == "") {
                                    ids = rows[i].id;
                                } else {
                                    ids += "," + rows[i].id;
                                }
                            }
                        }
                        if (!deleteFlag) {
                            $.messager.alert("提醒", "该冲差单不能审核。只有正常冲差单才能审核。");
                            return false;
                        }
                        $.messager.confirm("提醒", "是否审核应收款冲差单？", function (r) {
                            if (r) {
                                $.ajax({
                                    url: 'account/receivable/auditCustomerPushBanlance.do?ids=' + ids,
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (json) {
                                        if (json.flag) {
                                            var msg = "";
                                            if (json.successids != null && json.successids != "") {
                                                msg = "成功编号:" + json.successids;
                                            }
                                            if (json.errorids != null && json.errorids != "") {
                                                msg += "失败编号:" + json.errorids;
                                            }
                                            $.messager.alert("提醒", "审核成功。" + msg);
                                            $("#account-datagrid-customerPushBanlancePage").datagrid("reload");
                                        } else {
                                            $.messager.alert("提醒", "审核失败");
                                        }
                                    },
                                    error: function () {
                                        $.messager.alert("错误", "审核出错");
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receivable/oppauditCustomerPushBanlance.do">
                {
                    type: 'button-oppaudit',
                    handler: function () {
                        var rows = $("#account-datagrid-customerPushBanlancePage").datagrid('getChecked');
                        if (rows.length == 0) {
                            $.messager.alert("提醒", "请选择记录");
                            return false;
                        }
                        var deleteFlag = true;
                        var ids = "", unids = "";
                        for (var i = 0; i < rows.length; i++) {
                            var flag = isDoneOppauditBillCaseAccounting(rows[i].businessdate);
                            if (!flag) {
                                if (unids == "") {
                                    unids = rows[i].id;
                                } else {
                                    unids += "," + rows[i].id;
                                }
                            } else {
                                if (rows[i].isinvoice == '0' && (rows[i].isrefer == '0' && rows[i].isinvoicebill == '0')) {
                                    if (ids == "") {
                                        ids = rows[i].id;
                                    } else {
                                        ids += "," + rows[i].id;
                                    }
                                } else {
                                    deleteFlag = false;
                                    break;
                                }
                            }

                        }
                        if (unids != "") {
                            $.messager.alert("提醒", "编号：" + unids + "的业务日期不在会计区间内或未设置会计区间,不允许反审!");
                        }
                        if (!deleteFlag) {
                            $.messager.alert("提醒", "该冲差单不能反审。只有未开票的正常冲差单才能反审。");
                            return false;
                        }
                        if (ids != "") {
                            $.messager.confirm("提醒", "是否反审应收款冲差单？", function (r) {
                                if (r) {
                                    $.ajax({
                                        url: 'account/receivable/oppauditCustomerPushBanlance.do?ids=' + ids,
                                        type: 'post',
                                        dataType: 'json',
                                        success: function (json) {
                                            if (json.flag) {
                                                var msg = "";
                                                if (json.successids != null && json.successids != "") {
                                                    msg = "成功编号:" + json.successids;
                                                    var sucidarr = json.successids.split(",");
                                                    for (var i = 0; i < sucidarr.length; i++) {

                                                    }
                                                }
                                                if (json.errorids != null && json.errorids != "") {
                                                    msg += "失败编号:" + json.errorids;
                                                }
                                                $.messager.alert("提醒", "反审成功。" + msg);
                                                $("#account-datagrid-customerPushBanlancePage").datagrid("reload");
                                                var unidsArr = unids.split(",");
                                                for (var i = 0; i < unidsArr.length; i++) {
                                                    $("#account-datagrid-customerPushBanlancePage").datagrid("selectRecord", unidsArr[i]);
                                                }
                                            } else {
                                                $.messager.alert("提醒", "反审失败");
                                            }
                                        }
                                    });
                                }
                            });
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receivable/exportCustomerPushBanlance.do">
                {
                    type: 'button-export',
                    attr: {
                        queryForm: "#account-form-query-customerPushBanlancePage",
                        type: 'exportUserdefined',
                        name: '客户冲差单',
                        url: 'account/receivable/exportCustomerPushBanlance.do'
                    }
                },
                </security:authorize>
                {
                    type: 'button-commonquery',
                    attr: {
                        //查询针对的表
                        name: 't_account_customer_push_balance',
                        //查询针对的表格id
                        datagrid: 'account-datagrid-customerPushBanlancePage',
                        plain: true
                    }
                },
                {}
            ],
            buttons: [
                <security:authorize url="/account/receivable/customerPushBanlanceaddMorePage.do">
                {
                    id: 'customerPushBanlance-button-addmore',
                    name: '批量添加',
                    iconCls: 'button-add',
                    handler: function () {
                        top.addTab('account/receivable/showCustomerPushBanlancePage.do?type=add', "客户应收款冲差批量新增");
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receivable/customerPushBanlancePrintView.do">
                {
                    id: 'customerPushBanlance-button-preview',
                    name: '打印预览',
                    iconCls: 'button-preview',
                    handler: function () {
                        //printViewHandle({});
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receivable/customerPushBanlancePrint.do">
                {
                    id: 'customerPushBanlance-button-print',
                    name: '打印',
                    iconCls: 'button-print',
                    handler: function () {
                        //printHandle({});
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receivable/hdcustomerPushBanlancePrintView.do">
                {
                    id: 'hdcustomerPushBanlance-button-preview',
                    name: '支持回单冲差打印预览',
                    iconCls: 'button-preview',
                    handler: function () {
                        //printViewHandle({querypushtype:'2'});
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receivable/hdcustomerPushBanlancePrint.do">
                {
                    id: 'hdcustomerPushBanlance-button-print',
                    name: '支持回单冲差打印',
                    iconCls: 'button-print',
                    handler: function () {
                        //printHandle({querypushtype:'2'});
                    }
                },
                </security:authorize>
                {}
            ],
            model: 'bill',
            type: 'list',
            tname: 't_account_allocate_enter'
        });

        var customerPushBanlanceJson = $("#account-datagrid-customerPushBanlancePage").createGridColumnLoad({
            name: 't_account_customer_push_balance',
            frozenCol: [[
                {field: 'idok', checkbox: true, isShow: true}
            ]],
            commonCol: [[
                {field: 'id', title: '编号', width: 115, sortable: true},
                {field: 'businessdate', title: '业务日期', width: 80, sortable: true},
                {field: 'customerid', title: '客户编码', width: 60, sortable: true},
                {field: 'customername', title: '客户名称', width: 100, isShow: true},
                {
                    field: 'pcustomerid', title: '总店', width: 80, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        if (value != rowData.customerid) {
                            return rowData.pcustomername;
                        }
                    }
                },
                {
                    field: 'salesdept', title: '销售部门', width: 80, sortable: true, hidden: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.salesdeptname;
                    }
                },
                {
                    field: 'salesuser', title: '客户业务员', width: 80, sortable: true, hidden: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.salesusername;
                    }
                },
                {
                    field: 'isinvoice', title: '单据类型', width: 60, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.isinvoicename;
                    }
                },
                {field: 'vouchertimes', title: '生成凭证次数', align: 'center', width:80},
                {
                    field: 'invoiceid', title: '相关单据号', width: 120, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        if (rowData.isinvoice == "1") {
                            return '<a href="javascript:showInvoice(\'' + value + '\')">' + value + '</a>';
                        } else if (rowData.isinvoice == "2") {
                            return '<a href="javascript:showReceipt(\'' + value + '\')">' + value + '</a>';
                        }
                    }
                },
                {
                    field: 'pushtype', title: '冲差类型', width: 60, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.pushtypename;
                    }
                },
                {
                    field: 'brandid', title: '品牌名称', width: 60, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.brandname;
                    }
                },
                {
                    field: 'defaulttaxtype', title: '默认税种', width: 60, sortable: true, hidden: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.defaulttaxtypename;
                    }
                },
                {
                    field: 'subject', title: '费用科目', width: 60, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.subjectname;
                    }
                },
                {
                    field: 'amount', title: '冲差金额', resizable: true, sortable: true, align: 'right',
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'notaxamount',
                    title: '冲差未税金额',
                    resizable: true,
                    sortable: true,
                    align: 'right',
                    hidden: true,
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'tax', title: '税额', resizable: true, sortable: true, align: 'right', hidden: true,
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'writeoffamount',
                    title: '核销金额',
                    resizable: true,
                    aliascol: 'amount',
                    sortable: true,
                    align: 'right',
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'tailamount',
                    title: '尾差金额',
                    resizable: true,
                    aliascol: 'amount',
                    sortable: true,
                    align: 'right',
                    formatter: function (value, rowData, rowIndex) {
                        return formatterMoney(value);
                    }
                },
                {field: 'addusername', title: '制单人', width: 80, sortable: true, hidden: true},
                {field: 'addtime', title: '制单时间', width: 80, sortable: true, hidden: true},
                {field: 'auditusername', title: '审核人', width: 80, hidden: true, sortable: true},
                {field: 'audittime', title: '审核时间', width: 80, hidden: true, sortable: true},
                {
                    field: 'status', title: '状态', width: 60, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return getSysCodeName("status", value);
                    }
                },
                {
                    field: 'isrefer', title: '抽单状态', width: 60, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.isrefername;
                    }
                },
                {
                    field: 'isinvoicebill', title: '开票状态', width: 60, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.isinvoicebillname;
                    }
                },
                {
                    field: 'iswriteoff', title: '核销状态', width: 60, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.iswriteoffname;
                    }
                },
                {field: 'writeoffdate', title: '核销日期', width: 80, sortable: true},
                {field: 'printtimes', title: '打印次数', width: 80},
                {field: 'remark', title: '备注', width: 80, sortable: true}
            ]]
        });
        $("#account-datagrid-customerPushBanlancePage").datagrid({
            authority: customerPushBanlanceJson,
            frozenColumns: customerPushBanlanceJson.frozen,
            columns: customerPushBanlanceJson.common,
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
            url: 'account/receivable/showCustomerPushBanlanceList.do',
            queryParams: initQueryJSON,
            toolbar: '#account-datagrid-toolbar-customerPushBanlancePage',
            onClickRow: function (rowIndex, rowData) {
                $("#account-buttons-customerPushBanlancePage").buttonWidget("setDataID", {
                    id: rowData.id,
                    state: rowData.status,
                    type: 'view'
                });
            },
            onDblClickRow: function (rowIndex, rowData) {
                $('#account-panel-customerPushBanlance-addpage').dialog({
                    title: '客户应收款冲差修改',
                    width: 400,
                    height: 400,
                    collapsible: false,
                    minimizable: false,
                    maximizable: true,
                    resizable: true,
                    closed: true,
                    cache: false,
                    href: 'account/receivable/showCustomerPushBanlanceEditPage.do?id=' + rowData.id,
                    modal: true,
                    onLoad: function () {
                        if (("3" == rowData.status || "4" == rowData.status) || "0" != rowData.isinvoice) {
                            $("#customerPushBalance-remark").focus();
                        } else {
                            $("#account-customerPushBanlance-customerid").focus();
                        }
                    }
                });
                $('#account-panel-customerPushBanlance-addpage').dialog("open");
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
                var footerrows = $("#account-datagrid-customerPushBanlancePage").datagrid('getFooterRows');
                if (null != footerrows && footerrows.length > 0) {
                    footerobject = footerrows[0];
                    countTotalAmount();
                }
            }
        }).datagrid("columnMoving");
        $("#account-query-customerid").customerWidget({
            name: 't_account_customer_push_balance',
            width: 225,
            col: 'customerid',
            isdatasql: false,
            view: true,
            singleSelect: true
        });
        $("#account-query-brandid").widget({
            name: 't_account_customer_push_balance',
            width: 165,
            col: 'brandid',
            view: true,
            singleSelect: true
        });
        $("#account-query-subject").widget({
            referwid: 'RT_T_BASE_FINANCE_EXPENSES_SORT',
            view: true,
            width: 180,
            onlyLeafCheck: false,
            singleSelect: false
        });
        //通用查询组建调用
//			$("#account-query-advanced-customerPushBanlance").advancedQuery({
//				//查询针对的表
//		 		name:'t_account_customer_push_balance',
//		 		//查询针对的表格id
//		 		datagrid:'account-datagrid-customerPushBanlancePage',
//		 		plain:true
//			});

        //回车事件
        controlQueryAndResetByKey("account-queay-customerPushBanlance", "account-reload-customerPushBanlance");

        //查询
        $("#account-queay-customerPushBanlance").click(function () {
            //把form表单的name序列化成JSON对象
            var queryJSON = $("#account-form-query-customerPushBanlancePage").serializeJSON();
            $("#account-datagrid-customerPushBanlancePage").datagrid("load", queryJSON);
        });
        //重置
        $("#account-reload-customerPushBanlance").click(function () {
            $("#account-query-customerid").customerWidget("clear");
            $("#account-query-brandid").widget("clear");
            $("#account-query-subject").widget('clear');
            $("#account-datagrid-checked-ids").val("");
            $("#account-form-query-customerPushBanlancePage")[0].reset();
            var queryJSON = $("#account-form-query-customerPushBanlancePage").serializeJSON();
            $("#account-datagrid-customerPushBanlancePage").datagrid("load", queryJSON);
        });
    });
    function showInvoice(id) {
        top.addOrUpdateTab('account/receivable/showSalesInvoiceEditPage.do?id=' + id, "销售核销查看");
    }
    function showReceipt(id) {
        top.addTab('sales/receiptPage.do?type=edit&id=' + id, '销售发货回单查看');
    }
    function countTotalAmount() {
        var rows = $("#account-datagrid-customerPushBanlancePage").datagrid('getChecked');
        var amount = 0;
        var writeoffamount = 0;
        var tailamount = 0;
        var ids = "";
        for (var i = 0; i < rows.length; i++) {
            amount = Number(amount) + Number(rows[i].amount == undefined ? 0 : rows[i].amount);
            writeoffamount = Number(writeoffamount) + Number(rows[i].writeoffamount == undefined ? 0 : rows[i].writeoffamount);
            tailamount = Number(tailamount) + Number(rows[i].tailamount == undefined ? 0 : rows[i].tailamount);

            if ("" == ids) {
                ids = rows[i].id;
            } else {
                ids += "," + rows[i].id;
            }
        }
        $("#account-datagrid-checked-ids").val(ids);
        var footerrows = [{id: '选中金额', amount: amount, writeoffamount: writeoffamount, tailamount: tailamount}];
        if (null != footerobject) {
            footerrows.push(footerobject);
        }
        $("#account-datagrid-customerPushBanlancePage").datagrid("reloadFooter", footerrows);
    }
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "listPage-dialog-customerPushBanlance",
            code: "account_customerpushbanlance",
            tableId: "account-datagrid-customerPushBanlancePage",
            url_preview: "print/account/customerPushBanlancePrintView.do",
            url_print: "print/account/customerPushBanlancePrint.do",
            btnPreview: "customerPushBanlance-button-preview",
            btnPrint: "customerPushBanlance-button-print",
            printlimit: "${printlimit}",
            getData: getData
        });
        //支持回单冲差打印
        AgReportPrint.init({
            id: "listPage-dialog-hdcustomerPushBanlance",
            code: [
                { codename:"account_customerpushbanlance", labelname:"通用模板", templetid:"templetid" },
                { codename:"account_hdcustomerpushbanlance", labelname:"回单模板", templetid:"hdtempletid" }
            ],
            tableId: "account-datagrid-customerPushBanlancePage",
            url_preview: "print/account/customerPushBanlancePrintView.do",
            url_print: "print/account/customerPushBanlancePrint.do",
            btnPreview: "hdcustomerPushBanlance-button-preview",
            btnPrint: "hdcustomerPushBanlance-button-print",
            printlimit: "${printlimit}",
            exPrintParam: {querypushtype: '2'},
            getData: getData
        });
        function getData(tableId, printParam) {
            var data = $("#" + tableId).datagrid('getChecked');
            if (data == null || data.length == 0) {
                $.messager.alert("提醒", "请选择一条记录");
                return false;
            }
            for (var i = 0; i < data.length; i++) {
                if (!(data[i].status == '3' || data[i].status == '4')) {
                    $.messager.alert("提醒", "状态审核通过或关闭情况，才能打印预览");
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
