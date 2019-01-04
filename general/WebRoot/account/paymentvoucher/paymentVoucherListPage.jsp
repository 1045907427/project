<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>交款单列表</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="account-buttons-paymentVoucherListPage"></div>
    </div>
    <div data-options="region:'center'">
        <table id="account-table-paymentVoucherListPage"></table>
        <div id="account-table-query-paymentVoucherListPage" style="padding:2px;height:auto">
            <div>
                <form action="" id="account-form-paymentVoucherListPage" method="post">
                    <table class="querytable">
                        <tr>
                            <td>业务日期:</td>
                            <td><input type="text" id="account-paymentVoucherListPage-businessdatestart"
                                       name="businessdatestart" style="width:100px;" class="Wdate"
                                       onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/>
                                到 <input type="text" id="account-paymentVoucherListPage-businessdateend" name="businessdateend" class="Wdate"
                                         style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/>
                            </td>
                            <td>编号:</td>
                            <td><input type="text" name="id" style="width: 150px;"/></td>
                            <td>状态:</td>
                            <td>
                                <select id="account-paymentVoucherListPage-isAudit" name="isAudit" style="width:120px;">
                                    <option></option>
                                    <option value="1" selected="selected">未审核</option>
                                    <option value="2">已审核</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>交款人:</td>
                            <td><input id="account-paymentVoucherListPage-collectuser" type="text" name="collectuserid"
                                       style="width: 225px;"/></td>
                            <td>金额:</td>
                            <td>
                                <input id="account-paymentVoucherListPage-amount1" name="amount1" style="width: 66px;"
                                       class="easyui-numberbox" data-options="precision:2"/> -
                                <input id="account-paymentVoucherListPage-amount2" name="amount2" style="width: 66px;"
                                       class="easyui-numberbox" data-options="precision:2"/>
                            </td>
                            <td>打印状态:</td>
                            <td>
                                <select name="printsign" style="width:120px;">
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
                            <td colspan="2"></td>
                            <td colspan="2"></td>
                            <td colspan="2" class="tdbutton">
                                <a href="javaScript:void(0);" id="account-btn-queryPaymentVoucherListPage"
                                   class="button-qr">查询</a>
                                <a href="javaScript:void(0);" id="account-btn-reloadPaymentVoucherListPage"
                                   class="button-qr">重置</a>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    var authorprint = 0;
    <security:authorize url="/account/paymentvoucher/auditPaymentVoucher.do">
    authorprint = 1;
    </security:authorize>

    var SR_footerobject = null;
    $(document).ready(function () {
        var initQueryJSON = $("#account-form-paymentVoucherListPage").serializeJSON();
        var paymentVoucherListJson = $("#account-table-paymentVoucherListPage").createGridColumnLoad({
            name: 'account_paymentvoucher',
            frozenCol: [[
                {field: 'idok', checkbox: true, isShow: true}
            ]],
            commonCol: [[
                {field: 'id', title: '编号', width: 135, sortable: true},
                {field: 'businessdate', title: '业务日期', width: 70, sortable: true},
                {
                    field: 'totalamount', title: '金额', width: 80, align: 'right',
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {field: 'collectusername', title: '交款人', width: 80},
                {field: 'bank', title: '银行名称', width: 80, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.bankname;
                    }},
                {field: 'addusername', title: '制单人', width: 80, hidden: true},
                {field: 'adddeptname', title: '制单人部门', width: 100, hidden: true},
                {field: 'addtime', title: '制单时间', width: 120, sortable: true},
                {field: 'modifyusername', title: '修改人', width: 120, hidden: true},
                {field: 'modifytime', title: '制单时间', width: 120, sortable: true, hidden: true},
                {field: 'auditusername', title: '审核人', width: 100, hidden: true},
                {field: 'audittime', title: '审核时间', width: 100, hidden: true},
                {
                    field: 'status', title: '状态', width: 60,
                    formatter: function (value, row, index) {
                        return getSysCodeName('status', value);
                    }
                },
                {
                    field: 'printstate', title: '打印状态', width: 80, isShow: true,
                    formatter: function (value, rowData, index) {
                        if (rowData.printtimes > 0) {
                            return "已打印";
                        } else if (rowData.printtimes == -99 || rowData.printtimes == null) {
                            return "";
                        } else {
                            return "未打印";
                        }
                    }
                },
                {field: 'printtimes', title: '打印次数', align: 'center', width: 60},
                {field: 'remark', title: '备注', width: 200}
            ]]
        });
        $("#account-table-paymentVoucherListPage").datagrid({
            fit: true,
            method: 'post',
            rownumbers: true,
            pagination: true,
            idField: 'id',
            singleSelect: false,
            checkOnSelect: true,
            selectOnCheck: true,
            showFooter: true,
            sortName: 'addtime',
            sortOrder: 'desc,id desc',
            toolbar: '#account-table-query-paymentVoucherListPage',
            url: "account/paymentvoucher/showPaymentVoucherPageList.do",
            queryParams: initQueryJSON,
            pageSize: 100,
            authority: paymentVoucherListJson,
            frozenColumns: paymentVoucherListJson.frozen,
            columns: paymentVoucherListJson.common,
            onLoadSuccess: function () {
                var footerrows = $(this).datagrid('getFooterRows');
                if (null != footerrows && footerrows.length > 0) {
                    SR_footerobject = footerrows[0];
                    countTotalAmount();
                }
            },
            onDblClickRow: function (index, data) {
                top.addOrUpdateTab('account/paymentvoucher/paymentVoucherPage.do?type=edit&id=' + data.id, "交款单查看");
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

        //按钮
        $("#account-buttons-paymentVoucherListPage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/account/paymentvoucher/paymentVoucherAddBtn.do">
                {
                    type: 'button-add',
                    handler: function () {
                        top.addOrUpdateTab('account/paymentvoucher/paymentVoucherPage.do', '交款单新增');
                    }
                },
                </security:authorize>
                <security:authorize url="/account/paymentvoucher/paymentVoucherEditBtn.do">
                {
                    type: 'button-edit',
                    handler: function () {
                        var datarow = $("#account-table-paymentVoucherListPage").datagrid('getSelected');
                        if (datarow == null || datarow.id == null) {
                            $.messager.alert("提醒", "请选择要修改的交款单");
                            return false;
                        }
                        top.addOrUpdateTab('account/paymentvoucher/paymentVoucherPage.do?type=edit&id=' + datarow.id, '交款单修改');
                    }
                },
                </security:authorize>
                <security:authorize url="/account/paymentvoucher/paymentVoucherViewBtn.do">
                {
                    type: 'button-view',
                    handler: function () {
                        var datarow = $("#account-table-paymentVoucherListPage").datagrid('getSelected');
                        if (datarow == null || datarow.id == null) {
                            $.messager.alert("提醒", "请选择要查看的交款单");
                            return false;
                        }
                        top.addOrUpdateTab('account/paymentvoucher/paymentVoucherPage.do?type=edit&id=' + datarow.id, '交款单查看');
                    }
                },
                </security:authorize>
                <%--
                <security:authorize url="/account/paymentvoucher/paymentVoucherImport.do">
                {
                    type: 'button-import',
                    attr: {

                    }
                },
                </security:authorize>
                <security:authorize url="/account/paymentvoucher/paymentVoucherExport.do">
                {
                    type: 'button-export',
                    attr: {

                    }
                },
                </security:authorize>
                --%>
                <security:authorize url="/account/paymentvoucher/paymentVoucherPrintViewBtn.do">
                {
                    type: 'button-preview',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/account/paymentvoucher/paymentVoucherPrintBtn.do">
                {
                    type: 'button-print',
                    handler: function () {
                    }
                },
                </security:authorize>
                {}
            ],
            model: 'bill',
            type: 'list',
            datagrid: 'account-table-paymentVoucherListPage',
            tname: 't_account_paymentvoucher'
        });


        $("#account-table-query-paymentVoucherListPage-advanced").advancedQuery({
            name: 'account_paymentvoucher',
            plain: true,
            datagrid: 'account-table-paymentVoucherListPage'
        });

        //回车事件
        controlQueryAndResetByKey("account-btn-queryPaymentVoucherListPage", "account-btn-reloadPaymentVoucherListPage");

        $("#account-btn-queryPaymentVoucherListPage").click(function () {
            //查询参数直接添加在url中
            var queryJSON = $("#account-form-paymentVoucherListPage").serializeJSON();
            $('#account-table-paymentVoucherListPage').datagrid('load', queryJSON);
        });
        $("#account-btn-reloadPaymentVoucherListPage").click(function () {
            $("#account-form-paymentVoucherListPage")[0].reset();
            $("#account-paymentVoucherListPage-collectuser").widget("clear");
            var queryJSON = $("#account-form-paymentVoucherListPage").serializeJSON();
            $('#account-table-paymentVoucherListPage').datagrid('load', queryJSON);
        });

        $("#account-paymentVoucherListPage-collectuser").widget({
            name: 't_account_paymentvoucher',
            col: 'collectuserid',
            singleSelect: true,
            width: 225,
            onlyLeafCheck: true,
            rows: 100
        });

        $("#account-paymentVoucherListPage-salesdeptid").widget({
            referwid: 'RT_T_SYS_DEPT',
            singleSelect: false,
            width: 225,
            onlyLeafCheck: false,
            view: true
        });
    });
    function countTotalAmount() {
        var rows = $("#account-table-paymentVoucherListPage").datagrid('getChecked');
        var totalamount = 0;
        for (var i = 0; i < rows.length; i++) {
            totalamount = Number(totalamount) + Number(rows[i].totalamount == undefined ? 0 : rows[i].totalamount);
        }
        var foot = [{id: '选中金额', totalamount: totalamount, status: null}];
        if (null != SR_footerobject) {
            foot.push(SR_footerobject);
        }
        $("#account-table-paymentVoucherListPage").datagrid("reloadFooter", foot);
    }
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "listPage-PaymentVoucher-dialog-print",
            code: "account_paymentvoucher",
            tableId: "account-table-paymentVoucherListPage",
            url_preview: "print/account/paymentVoucherPrintView.do",
            url_print: "print/account/paymentVoucherPrint.do",
            btnPreview: "button-preview",
            btnPrint: "button-print",
            getData: getData
        });
        function getData(tableId, printParam) {
            var data = $("#" + tableId).datagrid('getChecked');
            if (data == null || data.length == 0) {
                $.messager.alert("提醒", "请选择一条记录");
                return false;
            }
            for (var i = 0; i < data.length; i++) {
                if (!(data[i].status=='1' || data[i].status=='2' || authorprint == 1)) {
                    $.messager.alert("提醒", data[i].id + "此交款单不可打印");
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
