<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>回单交接列表页面</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="account-buttons-receipthandlist"></div>
    </div>
    <div data-options="region:'center'">
        <table id="account-datagrid-receipthandlist"></table>
        <div id="account-datagrid-toolbar-receipthandlist" style="padding:2px;height:auto">
            <form action="" id="account-form-query-receipthandlist" method="post">
                <table class="querytable">
                    <tr>
                        <td>交接日期：</td>
                        <td><input type="text" name="businessdate1" style="width:100px;" class="Wdate"
                                   onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/>
                            到 <input type="text" name="businessdate2" class="Wdate" style="width:100px;"
                                     onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/></td>
                        <td>编号：</td>
                        <td><input type="text" name="id" style="width: 150px;"/></td>
                        <td>领 单 人：</td>
                        <td><input type="text" id="account-handuserid-handuserid" name="handuserid"/></td>
                    </tr>
                    <tr>
                        <td>客户名称：</td>
                        <td><input type="text" id="account-customerid-receipthandlist" name="customerid"
                                   style="width: 225px;"/></td>
                        <td>总店：</td>
                        <td><input type="text" id="account-pcustomerid-receipthandlist" name="pcustomerid"/></td>
                        <td>销售区域：</td>
                        <td><input type="text" id="account-salesarea-receipthandlist" name="salesarea"/></td>
                    </tr>
                    <tr>
                        <td>客户业务员：</td>
                        <td><input type="text" id="account-salesuser-receipthandlist" name="salesuser"/></td>
                        <td>状态：</td>
                        <td><select name="status" style="width: 150px;">
                            <option></option>
                            <option value="2" selected="selected">保存</option>
                            <option value="3">审核通过</option>
                            <option value="4">关闭</option>
                        </select></td>
                        <td rowspan="3" colspan="2" class="tdbutton">
                            <a href="javaScript:void(0);" id="account-query-receipthandlist" class="button-qr">查询</a>
                            <a href="javaScript:void(0);" id="account-reload-receipthandlist" class="button-qr">重置</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript">
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

    var initQueryJSON = $("#account-form-query-receipthandlist").serializeJSON();
    var RH_footerobject = null;
    $(function () {
        //按钮
        $("#account-buttons-receipthandlist").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/account/receipthand/receiptHandAddBtn.do">
                {
                    type: 'button-add',
                    handler: function () {
                        top.addOrUpdateTab('/account/receipthand/showReceiptHandAddAnotherPage.do', "交接单新增");
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receipthand/receiptHandViewBtn.do">
                {
                    type: 'button-view',
                    handler: function () {
                        var rows = $("#account-datagrid-receipthandlist").datagrid('getChecked');
                        if (rows == null || rows.length != 1) {
                            $.messager.alert("提醒", "请勾选一条记录");
                            return false;
                        }
                        var type = 'edit';
                        var status = rows[0].status;
                        if (status == '2')
                            type = 'edit';
                        if (status == '3')
                            type = 'view';
                        if ('edit' == type) {
                            var flag = isDoLockData(rows[0].id, "t_account_receipt");
                            if (!flag) {
                                $.messager.alert("警告", "该数据正在被其他人操作，暂不能修改！");
                                return false;
                            }
                        }
                        top.addOrUpdateTab('account/receipthand/showReceiptHandPage.do?type=' + type + '&id=' + rows[0].id, "交接单查看");
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receipthand/receiptHandDeleteBtn.do">
                {
                    type: 'button-delete',
                    handler: function () {
                        var rows = $("#account-datagrid-receipthandlist").datagrid('getChecked');
                        if (rows.length == 0) {
                            $.messager.alert("提醒", "请至少勾选一条记录");
                            return false;
                        }

                        $.messager.confirm("提醒", "是否确定删除选中的交接单?", function (r) {
                            if (r) {
                                var ids = "";
                                for (var i = 0; i < rows.length; i++) {
                                    if (ids == "") {
                                        ids = rows[i].id;
                                    } else {
                                        ids += "," + rows[i].id;
                                    }
                                }
                                loading("删除中..");
                                $.ajax({
                                    url: 'account/receipthand/deleteReceiptHands.do?ids=' + ids,
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        $.messager.alert("提醒", "删除无效" + json.invalidNum + "条记录;<br/>删除成功" + json.sucnum + "条记录;<br/>" + json.msg);
                                        if (json.sucnum > 0) {
                                            $("#account-datagrid-receipthandlist").datagrid("reload");
                                        }
                                    },
                                    error: function () {
                                        loaded();
                                        $.messager.alert("错误", "删除出错");
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/account/receipthand/receiptHandAuditBtn.do">
                {
                    type: 'button-audit',
                    handler: function () {
                        var rows = $("#account-datagrid-receipthandlist").datagrid('getChecked');
                        if (rows == null) {
                            $.messager.alert("提醒", "请至少勾选一条记录");
                            return false;
                        }
                        $.messager.confirm("提醒", "是否审核选中的交接单？", function (r) {
                            if (r) {
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
                                    url: 'account/receipthand/auditReceiptHands.do?ids=' + ids,
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag) {
                                            $("#account-datagrid-receipthandlist").datagrid("reload");
                                            $.messager.alert("提醒", "审核无效" + json.invalidNum + "条记录;<br/>" + json.nohandusernum + "条记录无领单人,不予审核;</br>审核成功" + json.num + "条记录;");
                                        } else {
                                            $.messager.alert("提醒", "审核失败");
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
                {}
            ],
            buttons: [
                <security:authorize url="/account/receipthand/receiptHandBillPrint.do">
                {
                    id: 'menuButton',
                    type: 'menu',
                    name: '打印',
                    iconCls: 'button-preview',
                    button: [
                        <security:authorize url="/account/receipthand/receiptHandBillPrintViewBtn.do">
                        {
                            id: 'preview-bill-button',
                            name: '单据打印预览',
                            iconCls: 'button-preview',
                            handler: function () {
                                //orderPrintViewFunc();
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/account/receipthand/receiptHandBillPrintBtn.do">
                        {
                            id: 'print-bill-button',
                            name: '单据打印',
                            iconCls: 'button-print',
                            handler: function () {
                                //orderPrintFunc();
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/account/receipthand/receiptHandDetailPrintBtn.do">
                        {
                            id: 'preview-detail-button',
                            name: '明细打印预览',
                            iconCls: 'button-preview',
                            handler: function () {
                                //receiptHandDetailPrintView();
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/account/receipthand/receiptHandDetailPrintBtn.do">
                        {
                            id: 'print-detail-button',
                            name: '明细打印',
                            iconCls: 'button-print',
                            handler: function () {
                                //receiptHandDetailPrint();
                            }
                        },
                        </security:authorize>
                        {}
                    ]
                }
                </security:authorize>
            ],
            model: 'bill',
            type: 'list',
            tname: 't_account_receipt'
        });

        var receipthandJson = $("#account-datagrid-receipthandlist").createGridColumnLoad({
            name: 't_account_receipt',
            frozenCol: [[{field: 'ck', checkbox: true, isShow: true}]],
            commonCol: [[
                {field: 'id', title: '编号', width: 125, sortable: true},
                {field: 'businessdate', title: '交接日期', width: 80, sortable: true},
                {
                    field: 'handuserid', title: '领单人', width: 80, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.handusername;
                    }
                },
                {
                    field: 'handdeptid', title: '领单人部门', width: 80, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.handdeptname;
                    }
                },
                {
                    field: 'cnums', title: '客户家数', width: 60, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return formatterNum(value);
                    }
                },
                {
                    field: 'collectamount', title: '应收金额', resizable: true, sortable: true, align: 'right',
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
                {field: 'billnums', title: '单据数', width: 60, isShow: true},
                {field: 'printtimes', title: '打印次数', width: 60},
                {field: 'remark', title: '备注', width: 100, sortable: true}
            ]]
        });

        $("#account-datagrid-receipthandlist").datagrid({
            authority: receipthandJson,
            frozenColumns: receipthandJson.frozen,
            columns: receipthandJson.common,
            fit: true,
            title: "",
            method: 'post',
            rownumbers: true,
            pagination: true,
            idField: 'id',
            sortName: 'id',
            sortOrder: 'desc',
            pageSize: 100,
            pageList: [50, 100, 200, 500, 1000],
            singleSelect: false,
            checkOnSelect: true,
            selectOnCheck: true,
            showFooter: true,
            url: 'account/receipthand/getReceiptHandList.do',
            queryParams: initQueryJSON,
            toolbar: '#account-datagrid-toolbar-receipthandlist',
            onDblClickRow: function (rowIndex, rowData) {
                var type = 'edit';
                var status = rowData.status;
                if (status == '2')
                    type = 'edit';
                if (status == '3')
                    type = 'view';
                if ('edit' == type) {
                    var flag = isDoLockData(rowData.id, "t_account_receipt");
                    if (!flag) {
                        $.messager.alert("警告", "该数据正在被其他人操作，暂不能修改！");
                        return false;
                    }
                }
                top.addOrUpdateTab('account/receipthand/showReceiptHandPage.do?type=' + type + '&id=' + rowData.id, "交接单查看");
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
                    RH_footerobject = footerrows[0];
                    countTotalAmount();
                }
            }
        }).datagrid("columnMoving");
        //领单人
        $("#account-handuserid-handuserid").widget({
            name: 't_account_receipt',
            col: 'handuserid',
            width: 120,
            singleSelect: true
        });
        //客户
        $("#account-customerid-receipthandlist").customerWidget({
            isdatasql: false
        });
        //销售区域
        $("#account-salesarea-receipthandlist").widget({
            referwid: 'RT_T_BASE_SALES_AREA',
            width: 120,
            singleSelect: false,
            onlyLeafCheck: false
        });
        //总店
        $("#account-pcustomerid-receipthandlist").widget({
            referwid: 'RL_T_BASE_SALES_CUSTOMER_PARENT',
            width: 150,
            singleSelect: true
        });
        //客户业务员
        $("#account-salesuser-receipthandlist").widget({
            referwid: 'RL_T_BASE_PERSONNEL_CLIENTSELLER',
            width: 225,
            singleSelect: true
        });

        //查询
        $("#account-query-receipthandlist").click(function () {
            var queryJSON = $("#account-form-query-receipthandlist").serializeJSON();
            $("#account-datagrid-receipthandlist").datagrid("load", queryJSON);
        });
        //重置
        $("#account-reload-receipthandlist").click(function () {
            $("#account-handuserid-handuserid").widget("clear");
            $("#account-customerid-receipthandlist").customerWidget("clear");
            $("#account-salesarea-receipthandlist").widget("clear");
            $("#account-pcustomerid-receipthandlist").widget("clear");
            $("#account-salesuser-receipthandlist").widget("clear");
            $("#account-form-query-receipthandlist")[0].reset();
            var queryJSON = $("#account-form-query-receipthandlist").serializeJSON();
            $("#account-datagrid-receipthandlist").datagrid("load", queryJSON);
        });
    });

    //计算勾选交接单合计
    function countTotalAmount() {
        var rows = $("#account-datagrid-receipthandlist").datagrid('getChecked');
        var cnums = 0, totalamount = 0, collectamount = 0, uncollectamount = 0;
        for (var i = 0; i < rows.length; i++) {
            cnums = Number(cnums) + Number(rows[i].cnums == undefined ? 0 : rows[i].cnums);
            totalamount = Number(totalamount) + Number(rows[i].totalamount == undefined ? 0 : rows[i].totalamount);
            collectamount = Number(collectamount) + Number(rows[i].collectamount == undefined ? 0 : rows[i].collectamount);
            uncollectamount = Number(uncollectamount) + Number(rows[i].uncollectamount == undefined ? 0 : rows[i].uncollectamount);
        }
        var foot = [
            {
                id: '选中金额',
                cnums: cnums,
                totalamount: totalamount,
                collectamount: collectamount,
                uncollectamount: uncollectamount
            }
        ];
        if (null != RH_footerobject) {
            foot.push(RH_footerobject);
        }
        $("#account-datagrid-receipthandlist").datagrid("reloadFooter", foot);
    }
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //单据打印
        AgReportPrint.init({
            id: "listPage-ReceiptHand-dialog-print",
            code: "account_receipthand",
            tableId: "account-datagrid-receipthandlist",
            url_preview: "print/account/receiptHandPrintView.do",
            url_print: "print/account/receiptHandPrint.do",
            btnPreview: "preview-bill-button",
            btnPrint: "print-bill-button",
            single: true,
            getData: getData
        });
        //明细打印预览
        AgReportPrint.init({
            id: "listPage-ReceiptHandDetail-dialog-print",
            code: "account_receipthanddetail",
            tableId: "account-datagrid-receipthandlist",
            url_preview: "print/account/receiptHandDetailPrintView.do",
            url_print: "print/account/receiptHandDetailPrint.do",
            btnPreview: "preview-detail-button",
            btnPrint: "print-detail-button",
            single: true,
            getData: getData
        });
        function getData(tableId, printParam) {
            var data = $("#" + tableId).datagrid('getChecked');
            if (data == null || data.length == 0) {
                $.messager.alert("提醒", "请选择一条记录");
                return false;
            }
            for (var i = 0; i < data.length; i++) {
                if (data[0].status!='3') {
                    $.messager.alert("提醒", data[i].id + "此单非审核通过状态，不可打印");
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
