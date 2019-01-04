<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>日常费用录入列表</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="deptdailycost-buttons-detaillist"></div>
    </div>
    <div data-options="region:'center'">
        <table id="deptdailycostListPage-table-detail"></table>
        <div id="deptdailycostListPage-form-div" style="padding:2px;height:auto">
            <form action="" id="deptdailycostListPage-form-ListQuery" method="post">
                <table class="querytable">
                    <tr>
                        <td>业务日期：</td>
                        <td>
                            <input type="text" name="businessdate1" style="width:100px;" class="Wdate"
                                   onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${today1 }"/> 到 <input
                                type="text" name="businessdate2" class="Wdate" style="width:100px;"
                                onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${today2 }"/>
                        </td>
                        <td>开单日期：</td>
                        <td>
                            <input type="text" id="deptdailycostListPage-form-billtime" name="billtime"
                                   style="width:150px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"
                                   value=""/>
                        </td>
                        <td>费用科目：</td>
                        <td>
                            <input id="deptdailycostListPage-form-costsort" name="costsortlike" type="text"
                                   style="width: 150px;"/>
                        </td>
                    </tr>
                    <tr>
                        <td>供应商:</td>
                        <td>
                            <input id="deptdailycostListPage-form-supplier" type="text" name="supplierid"
                                   style="width: 190px;"/>
                            <label style="display: inline-block; vertical-align: middle;  ">
                                <input type="checkbox" id="deptdailycostListPage-form-supplier-empty"
                                       name="emptysupplier" value="1"
                                       style="margin:0 3px;vertical-align: middle; "/><span
                                    style="cursor:pointer;">选择空</span>
                            </label>
                        </td>
                        <td>所属部门：</td>
                        <td>
                            <input id="deptdailycostListPage-form-deptid" name="deptid" type="text"
                                   style="width: 150px;"/>
                        </td>
                        <td>品牌：</td>
                        <td>
                            <input id="deptdailycostListPage-form-brandid" type="text" name="brandid"
                                   style="width: 150px;"/>
                        </td>
                    </tr>
                    <tr>
                        <td>状态：</td>
                        <td>
                            <select name="status" style="width: 225px;">
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
                        <td>客户业务员：</td>
                        <td>
                            <input id="deptdailycostListPage-form-salesuser" type="text" name="salesuser"
                                   style="width: 150px;"/>
                        </td>
                        <td colspan="2" style="padding-left: 25px">
                            <a href="javaScript:void(0);" id="departmentCosts-query-List" class="button-qr">查询</a>
                            <a href="javaScript:void(0);" id="departmentCosts-query-reloadList" class="button-qr">重置</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>
<div id="deptdailycost-dialog-detail1"></div>
<div id="deptdailycost-dialog-settlement"></div>
<div id="deptdailycost-dialog-voucher"></div>
<script type="text/javascript">
    //根据初始的列与用户保存的列生成以及字段权限生成新的列
    var tableColJson = $("#deptdailycostListPage-table-detail").createGridColumnLoad({
        name: 't_js_dept_dailycost',
        frozenCol: [[
            {field: 'ck', checkbox: true, isShow: true}
        ]],
        commonCol: [[
            {field: 'id', title: '编码', width: 130, sortable: true},
            {
                field: 'biltime', title: '开单日期', width: 80, isShow: true,
                formatter: function (val, rowData, rowIndex) {
                    if (rowData.addtime != null) {
                        var tmpArr = rowData.addtime.split(' ');
                        if (tmpArr.length > 0) {
                            return tmpArr[0];
                        }
                        return "";
                    }
                }
            },
            {field: 'businessdate', title: '业务日期', width: 80, sortable: true},
            // {field:'oaid',title:'OA编号',width:80,sortable:true},
            {field: 'supplierid', title: '供应商编码', width: 70},
            {field: 'suppliername', title: '供应商名称', width: 100, isShow: true},
            {
                field: 'bankid', title: '银行名称', width: 100,
                formatter: function (value, rowData, rowIndex) {
                    return rowData.bankname;
                }
            },
            {
                field: 'salesuser', title: '人员', hidden: true, width: 70, isShow: true,
                formatter: function (value, rowData, rowIndex) {
                    return rowData.salesusername;
                }
            },
            {
                field: 'deptid', title: '所属部门', width: 100, sortable: true,
                formatter: function (val, rowData, rowIndex) {
                    return rowData.deptname;
                }
            },
            {
                field: 'costsort', title: '费用科目', width: 100, sortable: true,
                formatter: function (val, rowData, rowIndex) {
                    return rowData.costsortname;
                }
            },
            {
                field: 'brandid', title: '品牌名称', width: 60, sortable: true,
                formatter: function (val, rowData, rowIndex) {
                    return rowData.brandname;
                }
            },
            {
                field: 'unitnum', title: '数量', width: 80, sortable: true, align: 'right',
                formatter: function (val, rowData, rowIndex) {
                    if (val != "" && val != null) {
                        return formatterBigNumNoLen(val);
                    }
                    else {
                        return "0.00";
                    }
                }
            },
            {
                field: 'taxprice', title: '单价', width: 80, sortable: true, align: 'right',
                formatter: function (val, rowData, rowIndex) {
                    if (val != "" && val != null) {
                        return formatterMoney(val, 4);
                    } else if (rowData.id == '合 计' || rowData.id == '选中合计') {
                        return "";
                    }
                    else {
                        return "0.0000";
                    }
                }
            },
            {
                field: 'amount', title: '金额', width: 80, sortable: true, align: 'right',
                formatter: function (val, rowData, rowIndex) {
                    if (val != "" && val != null) {
                        return formatterMoney(val);
                    } else {
                        return "0.00";
                    }
                }
            },
            {
                field: 'status', title: '状态', width: 70, sortable: true,
                formatter: function (val, rowData, rowIndex) {
                    if (val == '2') {
                        return "保存";
                    } else if (val == '3') {
                        return "审核通过";
                    } else if (val == '4') {
                        return "关闭";
                    }
                }
            },
            {field: 'vouchertimes', title: '生成凭证次数', align: 'center', width: 80},
            {field: 'addusername', title: '制单人', width: 80},
            {field: 'addtime', title: '制单时间', width: 130, sortable: true},
            {field: 'modifyusername', title: '修改人', width: 80, hidden: true},
            {field: 'modifytime', title: '修改时间', width: 130, hidden: true},
            {field: 'auditusername', title: '审核人', width: 80, sortable: true},
            {field: 'audittime', title: '审核时间', width: 130, sortable: true},
            {field: 'remark', title: '备注', width: 100},
            {
                field: 'printstate', title: '打印状态', width: 80, isShow: true,
                formatter: function (value, rowData, index) {
                    if (rowData.printtimes > 0) {
                        return "已打印";
                    } else if (rowData.printtimes == null) {
                        return "";
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
    $("#deptdailycost-buttons-detaillist").buttonWidget({
        initButton: [
            {},
            <security:authorize url="/journalsheet/deptdailycost/addDeptDailyCost.do">
            {
                type: 'button-add',
                handler: function () {
                    $('<div id="deptdailycost-dialog-detail"></div>').appendTo('#deptdailycost-dialog-detail1');
                    $('#deptdailycost-dialog-detail').dialog({
                        title: '日常费用录入【新增】',
                        width: 750,
                        height: 300,
                        collapsible: false,
                        minimizable: false,
                        maximizable: true,
                        resizable: true,
                        closed: true,
                        cache: false,
                        href: 'journalsheet/deptdailycost/showDeptDailyCostAddPage.do',
                        modal: true,
                        onLoad: function () {
                            $("#deptDailyCost-detail-supplier").focus();
                        },
                        onClose: function () {
                            $('#deptdailycost-dialog-detail').dialog("destroy");
                        }
                    });
                    $('#deptdailycost-dialog-detail').dialog("open");
                }
            },
            </security:authorize>
            <security:authorize url="/journalsheet/deptdailycost/showDeptDailyCostViewPage.do">
            {
                type: 'button-view',
                handler: function () {
                    var con = $("#deptdailycostListPage-table-detail").datagrid('getSelected');
                    if (con == null) {
                        $.messager.alert("提醒", "请选择一条记录");
                        return false;
                    }
                    $('<div id="deptdailycost-dialog-detail"></div>').appendTo('#deptdailycost-dialog-detail1');
                    $('#deptdailycost-dialog-detail').dialog({
                        title: '日常费用录入【详情】',
                        width: 750,
                        height: 300,
                        collapsible: false,
                        minimizable: false,
                        maximizable: true,
                        resizable: true,
                        closed: true,
                        cache: false,
                        href: 'journalsheet/deptdailycost/showDeptDailyCostViewPage.do?id=' + con.id,
                        modal: true,
                        onClose: function () {
                            $('#deptdailycost-dialog-detail').dialog("destroy");
                        }
                    });
                    $('#deptdailycost-dialog-detail').dialog("open");
                }
            },
            </security:authorize>
            <security:authorize url="/journalsheet/deptdailycost/showDeptDailyCostEditPage.do">
            {
                type: 'button-edit',
                handler: function () {
                    var con = $("#deptdailycostListPage-table-detail").datagrid('getSelected');
                    if (con == null) {
                        $.messager.alert("提醒", "请选择一条记录");
                        return false;
                    }
                    $('<div id="deptdailycost-dialog-detail"></div>').appendTo('#deptdailycost-dialog-detail1');
                    $('#deptdailycost-dialog-detail').dialog({
                        title: '日常费用录入【详情】',
                        width: 750,
                        height: 300,
                        collapsible: false,
                        minimizable: false,
                        maximizable: true,
                        resizable: true,
                        closed: true,
                        cache: false,
                        href: 'journalsheet/deptdailycost/showDeptDailyCostInfoPage.do?id=' + con.id,
                        modal: true,
                        onClose: function () {
                            $('#deptdailycost-dialog-detail').dialog("destroy");
                        }
                    });
                    $('#deptdailycost-dialog-detail').dialog("open");
                }
            },
            </security:authorize>
            <security:authorize url="/journalsheet/deptdailycost/deleteDeptDailyCost.do">
            {
                type: 'button-delete',
                handler: function () {
                    $.messager.confirm("提醒", "是否删除当前日常费用录入？", function (r) {
                        if (r) {
                            var rows = $("#deptdailycostListPage-table-detail").datagrid("getChecked");
                            var ids = "";
                            for (var i = 0; i < rows.length; i++) {
                                if (ids == "") {
                                    ids = rows[i].id;
                                } else {
                                    ids += "," + rows[i].id;
                                }
                            }
                            if (ids != "") {
                                loading("提交中..");
                                $.ajax({
                                    url: 'journalsheet/deptdailycost/deleteDeptDailyCost.do?ids=' + ids,
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag) {
                                            $.messager.alert("提醒", "删除成功</br>" + json.succssids + "</br>" + json.errorids);
                                            $("#deptdailycostListPage-table-detail").datagrid("reload");
                                        } else {
                                            $.messager.alert("提醒", "删除失败");
                                        }
                                    },
                                    error: function () {
                                        $.messager.alert("错误", "删除出错");
                                        loaded();
                                    }
                                });
                            }
                        }
                    });
                }
            },
            </security:authorize>
            <security:authorize url="/journalsheet/deptdailycost/auditDeptDailyCost.do">
            {
                type: 'button-audit',
                handler: function () {
                    $.messager.confirm("提醒", "是否审核选中的日常费用录入？", function (r) {
                        if (r) {
                            var rows = $("#deptdailycostListPage-table-detail").datagrid("getChecked");
                            var ids = "";
                            for (var i = 0; i < rows.length; i++) {
                                if (ids == "") {
                                    ids = rows[i].id;
                                } else {
                                    ids += "," + rows[i].id;
                                }
                            }
                            loading("审核中..");
                            $.ajax({
                                url: 'journalsheet/deptdailycost/auditDeptDailyCost.do?ids=' + ids,
                                type: 'post',
                                dataType: 'json',
                                success: function (json) {
                                    loaded();
                                    if (json.flag) {
                                        $.messager.alert("提醒", "审核成功</br>" + json.succssids + "</br>" + json.errorids);
                                        $("#deptdailycostListPage-table-detail").datagrid("reload");
                                    } else {
                                        $.messager.alert("提醒", "审核失败");
                                    }
                                },
                                error: function () {
                                    $.messager.alert("错误", "审核出错");
                                    loaded();
                                }
                            });
                        }
                    });
                }
            },
            </security:authorize>
            <security:authorize url="/journalsheet/deptdailycost/oppauditDeptDailyCost.do">
            {
                type: 'button-oppaudit',
                handler: function () {
                    var rows = $("#deptdailycostListPage-table-detail").datagrid("getChecked");
                    if (rows.length == 0) {
                        $.messager.alert("提醒", "请选择要反审的记录!");
                        return false;
                    }
                    $.messager.confirm("提醒", "是否反审日常费用录入？", function (r) {
                        if (r) {
                            var ids = "";
                            for (var i = 0; i < rows.length; i++) {
                                if (ids == "") {
                                    ids = rows[i].id;
                                } else {
                                    ids += "," + rows[i].id;
                                }
                            }
                            if (ids != "") {
                                loading("反审中..");
                                $.ajax({
                                    url: 'journalsheet/deptdailycost/oppauditDeptDailyCost.do?ids=' + ids,
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag) {
                                            $.messager.alert("提醒", "反审成功</br>" + json.succssids + "</br>" + json.errorids);
                                            $("#deptdailycostListPage-table-detail").datagrid("reload");
                                        } else {
                                            $.messager.alert("提醒", "反审失败");
                                        }
                                    },
                                    error: function () {
                                        $.messager.alert("错误", "反审出错");
                                        loaded();
                                    }
                                });
                            }
                        }
                    });
                }
            },
            </security:authorize>
            <security:authorize url="/journalsheet/deptdailycost/deptDailyCostExport.do">
            {
                type: 'button-export',
                attr: {
                    queryForm: "#deptdailycostListPage-form-ListQuery",
                    datagrid: "#deptdailycostListPage-table-detail",
                    type: "exportUserdefined",
                    name: "日常费用录入列表",
                    url: 'journalsheet/deptdailycost/deptDailyCostExport.do'
                },
            },
            </security:authorize>
            {}
        ],
        buttons: [
            <security:authorize url="/journalsheet/deptdailycost/updateDeptDailyCostSettle.do">
            {
                id: 'button-settlement',
                name: '结算',
                iconCls: 'button-writeoff',
                handler: function () {
                    $("#deptdailycost-dialog-settlement").dialog({
                        title: '日常费用录入【结算】',
                        width: 400,
                        height: 250,
                        collapsible: false,
                        minimizable: false,
                        maximizable: false,
                        resizable: true,
                        closed: true,
                        cache: false,
                        href: 'journalsheet/deptdailycost/showDeptDailyCostSettlePage.do',
                        modal: true
                    });
                    $("#deptdailycost-dialog-settlement").dialog("open");
                }
            },
            </security:authorize>
            <security:authorize url="/erpconnect/addExpensesVouch.do">
            {
                id: 'expensesentering-account',
                name: '生成凭证',
                iconCls: 'button-audit',
                handler: function () {
                    var rows = $("#deptdailycostListPage-table-detail").datagrid('getChecked');
                    var supplierids = "";
                    if (rows == null || rows.length == 0) {
                        $.messager.alert("提醒", "请选择至少一条记录");
                        return false;
                    }
                    var ids = "";
                    for (var i = 0; i < rows.length; i++) {
                        if ("3" != rows[i].status && "4" != rows[i].status) {
                            $.messager.alert("提醒", "请选择审核或关闭状态记录!");
                            return false;
                        }
                        if (i == 0) {
                            ids = rows[i].id;
                        } else {
                            ids += "," + rows[i].id;
                        }
                    }
                    $("#deptdailycost-dialog-voucher").dialog({
                        title: '费用支付凭证',
                        width: 400,
                        height: 260,
                        closed: false,
                        modal: true,
                        cache: false,
                        href: 'erpconnect/showExpensesVouchPage.do?voucherType=2',
                        onLoad: function () {
                            $("#expensesVouch-ids").val(ids);
                        }
                    });
                }
            },
            </security:authorize>
            <security:authorize url="/journalsheet/deptdailycost/deptDailyCostPrintViewBtn.do">
            {
                id: 'button-printview-order',
                name: '打印预览',
                iconCls: 'button-preview',
                handler: function () {
                }
            },
            </security:authorize>
            <security:authorize url="/journalsheet/deptdailycost/deptDailyCostPrintBtn.do">
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
        tname: 't_js_dept_dailycost'
    });

    var initQueryJSON = $("#deptdailycostListPage-form-ListQuery").serializeJSON();
    $(function () {
        $("#deptdailycostListPage-table-detail").datagrid({
            authority: tableColJson,
            frozenColumns: tableColJson.frozen,
            columns: tableColJson.common,
            fit: true,
            method: 'post',
            showFooter: true,
            rownumbers: true,
            sortName: 'id',
            sortOrder: 'desc',
            pagination: true,
            idField: 'id',
            singleSelect: false,
            checkOnSelect: true,
            selectOnCheck: true,
            pageSize: 100,
            queryParams: initQueryJSON,
            toolbar: '#deptdailycostListPage-form-div',
            url: 'journalsheet/deptdailycost/showDeptDailyCostList.do',
            onDblClickRow: function (rowIndex, rowData) {
                $('<div id="deptdailycost-dialog-detail"></div>').appendTo('#deptdailycost-dialog-detail1');
                $('#deptdailycost-dialog-detail').dialog({
                    title: '日常费用录入【详情】',
                    width: 750,
                    height: 300,
                    collapsible: false,
                    minimizable: false,
                    maximizable: true,
                    resizable: true,
                    closed: true,
                    cache: false,
                    href: 'journalsheet/deptdailycost/showDeptDailyCostInfoPage.do?id=' + rowData.id,
                    modal: true,
                    onClose: function () {
                        $('#deptdailycost-dialog-detail').dialog("destroy");
                    }
                });
                $('#deptdailycost-dialog-detail').dialog("open");
            },
            onLoadSuccess: function () {
                var footerrows = $(this).datagrid('getFooterRows');
                if (null != footerrows && footerrows.length > 0) {
                    listFooterobject = footerrows[0];
                }
            },
            onCheckAll: function () {
                deptCostsCountTotalAmount();
            },
            onUncheckAll: function () {
                deptCostsCountTotalAmount();
            },
            onCheck: function () {
                deptCostsCountTotalAmount();
            },
            onUncheck: function () {
                deptCostsCountTotalAmount();
            }
        }).datagrid("columnMoving");

        $("#deptdailycostListPage-form-supplier").widget({
            referwid: 'RL_T_BASE_BUY_SUPPLIER',
            width: 190,
            singleSelect: true,
            onlyLeafCheck: true,
            onSelect: function () {
                $("#deptdailycostListPage-form-supplier-empty").prop({checked: false});
            }
        });
        $("#deptdailycostListPage-form-salesuser").widget({
            referwid: 'RL_T_BASE_PERSONNEL',
            width: 150,
            singleSelect: false,
            onlyLeafCheck: true
        });

        $("#deptdailycostListPage-form-deptid").widget({
            referwid: 'RT_T_SYS_DEPT',
            width: 150,
            singleSelect: true,
            onlyLeafCheck: false
        });

        $("#deptdailycostListPage-form-costsort").widget({
            referwid: 'RL_T_JS_DEPARTMENTCOSTS_SUBJECT',
            width: 150,
            singleSelect: true,
            onlyLeafCheck: false,
            onSelect: function () {
            }
        });
        //品牌
        $("#deptdailycostListPage-form-brandid").widget({
            referwid: 'RL_T_BASE_GOODS_BRAND',
            singleSelect: true,
            width: 150,
            onlyLeafCheck: true,
            onSelect: function () {
            }
        });

        $("#departmentCosts-query-List").click(function () {
            //把form表单的name序列化成JSON对象
            var queryJSON = $("#deptdailycostListPage-form-ListQuery").serializeJSON();
            $("#deptdailycostListPage-table-detail").datagrid("load", queryJSON);
        });

        $("#departmentCosts-query-reloadList").click(function () {
            $("#deptdailycostListPage-form-deptid").widget("clear");
            $("#deptdailycostListPage-form-costsort").widget("clear");
            $("#deptdailycostListPage-form-ListQuery")[0].reset();
            var queryJSON = $("#deptdailycostListPage-form-ListQuery").serializeJSON();
            $("#deptdailycostListPage-table-detail").datagrid("load", queryJSON);
        });
        $("#deptdailycostListPage-form-supplier-empty").click(function () {
            if ($(this).prop("checked")) {
                $("#deptdailycostListPage-form-supplier").widget("clear");
            } else {
                $("#deptdailycostListPage-form-supplier").widget("clear");
            }
        });
    });
    var listFooterobject = null;
    function deptCostsCountTotalAmount() {
        var rows = $("#deptdailycostListPage-table-detail").datagrid('getChecked');
        if (null == rows || rows.length == 0) {
            var foot = [];
            if (null != listFooterobject) {
                foot.push({id: '选中合计', amount: 0.00, unitnum: 0.00});
                foot.push(listFooterobject);
            }
            $("#deptdailycostListPage-table-detail").datagrid("reloadFooter", foot);
            return false;
        }
        var amount = 0;
        var unitnum = 0;
        for (var i = 0; i < rows.length; i++) {
            amount = Number(amount) + Number(rows[i].amount == undefined ? 0 : rows[i].amount);
            unitnum = Number(unitnum) + Number(rows[i].unitnum == undefined ? 0 : rows[i].unitnum);
        }
        var foot = [{id: '选中合计', amount: amount, unitnum: unitnum}];
        if (null != listFooterobject) {
            foot.push(listFooterobject);
        }
        $("#deptdailycostListPage-table-detail").datagrid("reloadFooter", foot);
    }

    function computeAmountNumChange(type) {
        if (type == null) {
            return;
        }
        var unitnum = $("#deptDailyCost-detail-unitnum").val() || 0;
        var taxprice = $("#deptDailyCost-detail-taxprice").val() || 0;
        var amount = $("#deptDailyCost-detail-amount").val() || 0;


        $("#deptDailyCost-detail-unitnum").addClass("inputload");
        $("#deptDailyCost-detail-taxprice").addClass("inputload");
        $("#deptDailyCost-detail-amount").addClass("inputload");

        if (type == "1") {
            $("#deptDailyCost-detail-amount").removeClass("inputload");
        } else if (type == "2") {
            $("#deptDailyCost-detail-unitnum").removeClass("inputload");
        } else if (type == "3") {
            $("#deptDailyCost-detail-taxprice").removeClass("inputload");
        }

        $.ajax({
            url: 'journalsheet/deptdailycost/deptDailyCostAmountNumChange.do',
            type: 'post',
            data: {type: type, unitnum: unitnum, taxprice: taxprice, amount: amount},
            dataType: 'json',
            async: false,
            success: function (json) {
                $("#deptDailyCost-detail-unitnum").val(json.unitnum);
                $("#deptDailyCost-detail-taxprice").val(json.taxprice);
                $("#deptDailyCost-detail-amount").val(json.amount);

                $("#deptDailyCost-detail-unitnum").removeClass("inputload");
                $("#deptDailyCost-detail-taxprice").removeClass("inputload");
                $("#deptDailyCost-detail-amount").removeClass("inputload");
            },
            error: function () {
                $("#deptDailyCost-detail-unitnum").removeClass("inputload");
                $("#deptDailyCost-detail-taxprice").removeClass("inputload");
                $("#deptDailyCost-detail-amount").removeClass("inputload");
            }
        });
    }
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "listPage-deptdailycost-dialog-print",
            code: "journalsheet_deptdailycost",
            tableId: "deptdailycostListPage-table-detail",
            url_preview: "print/journalsheet/deptDailyCostPrintView.do",
            url_print: "print/journalsheet/deptDailyCostPrint.do",
            btnPreview: "button-printview-order",
            btnPrint: "button-print-order",
            getData: function (tableId, printParam) {
                var data = $("#" + tableId).datagrid('getChecked');
                if (data == null || data.length == 0) {
                    $.messager.alert("提醒", "请选择至少一条记录");
                    return false;
                }
                var idarray = [];
                var printArr = [];
                var errBDArr = [];
                var errSupplierArr = [];
                var errorArr = [];
                var ydprintArr = [];
                var printtip = false;
                var billtime = null;
                var supplier = null;
                for (var i = 0; i < data.length; i++) {
                    if (data[i].status != '3' && data[i].status != '4') {
                        errorArr.push(data[i].id);
                        continue;
                    }
                    var curbtime = "";
                    var cursupplier = "";
                    if (data[i].addtime != null) {
                        var tmpArr = data[i].addtime.split(' ');
                        if (tmpArr.length > 0) {
                            curbtime = tmpArr[0];
                        }
                    }
                    if (billtime == null) {
                        billtime = curbtime;
                    } else if (billtime != curbtime) {
                        errBDArr.push(data[i].id);
                        continue;
                    }
                    cursupplier = data[i].supplierid || "";
                    if (supplier == null) {
                        supplier = cursupplier;
                    } else if (supplier != cursupplier) {
                        errSupplierArr.push(data[i].id);
                        continue;
                    }
                    idarray.push(data[i].id);
                    if (data[i].printtimes > 0) {
                        ydprintArr.push(data[i].id);
                        printtip = true;
                    }
                    printArr.push(data[i]);
                }
                var msgArr = [];

                if (errorArr.length > 0 || errBDArr.length > 0 || errSupplierArr.length > 0) {
                    msgArr.push("抱歉，系统不能打印。问题如下：<br/>");
                    if (errorArr.length > 0) {
                        msgArr.push("保存状态下不能打印，单据号：");
                        msgArr.push(errorArr.join(","));
                        msgArr.push("<br/>");
                    }
                    if (errBDArr.length > 0) {
                        msgArr.push("开单日期不一致，单据号：");
                        msgArr.push(errBDArr.join(","));
                        msgArr.push("<br/>");
                    }
                    if (errSupplierArr.length > 0) {
                        msgArr.push("供应商不一致，单据号：");
                        msgArr.push(errSupplierArr.join(","));
                        msgArr.push("<br/>");
                    }
                    $.messager.alert("提醒", msgArr.join(''));
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
