<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>销售退货通知单列表</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="sales-buttons-rejectBillListPage"></div>
    </div>
    <div data-options="region:'center'">
        <table id="sales-datagrid-rejectBillListPage"></table>
        <div id="sales-queryDiv-rejectBillListPage" style="padding:2px;height:auto">
            <form id="sales-queryForm-rejectBillListPage">
                <table class="querytable">
                    <tr>
                        <td>业务日期:</td>
                        <td><input type="text" name="businessdate" style="width:100px;" class="Wdate"
                                   onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/>
                            到 <input type="text" name="businessdate1" class="Wdate" style="width:100px;"
                                     onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/>
                        </td>
                        <!-- <td>销售部门：</td>
                        <td>
                            <select class="len136" name="salesdept" id="sales-salesDept-orderListPage"style="width:150px;" >
                                <option></option>
                                <c:forEach items="${salesDept}" var="list">
                                    <option value="${list.id }">${list.name }</option>
                                </c:forEach>
                            </select>
                        </td> -->
                        <td>入库仓库:</td>
                        <td>
                            <input type="text" id="sales-storageid-rejectBillListPage" class="len150" name="storageid"/>
                        </td>
                        <td>编号:</td>
                        <td><input type="text" name="id" style="width:165px;"/></td>
                        <td colspan="2"></td>
                    </tr>
                    <tr>
                        <td>商品档案:</td>
                        <td>
                            <input type="text" id="storage-goods-saleRejectEnterPage" name="goodsid"
                                   style="width: 225px;"/>
                        <td>退货类型:</td>
                        <td>
                            <select name="billtype" style="width:150px;">
                                <option></option>
                                <option value="1">直退退货</option>
                                <option value="2">售后退货</option>
                            </select>
                            <input type="hidden" name="source" value="9"/>
                        </td>
                        <td>状态:</td>
                        <td>
                            <select name="status" style="width:165px;">
                                <option></option>
                                <option value="2" selected="selected">保存</option>
                                <option value="3">审核通过</option>
                                <option value="4">已关闭</option>
                            </select>
                        </td>
                        <td colspan="2">
                        </td>
                    </tr>
                    <tr>
                        <td>客户单号:</td>
                        <td><input type="text"style="width: 225px;" name="sourceid"/></td>
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
                            <input type="hidden" name="queryprinttimes" value="0" style="width:60px;"/>
                        </td>
                        <td>客户:</td>
                        <td><input type="text" id="sales-customer-rejectBillListPage" style="width: 165px;"
                                   name="customerid"/></td>
                        </td>
                        <td colspan="2">
                            <a href="javascript:;" id="sales-queay-rejectBillListPage" class="button-qr">查询</a>
                            <a href="javaScript:;" id="sales-resetQueay-rejectBillListPage" class="button-qr">重置</a>
                            <span id="sales-queryAdvanced-rejectBillListPage"></span>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>
<div id="sales-dialog-addrejectbill-order"></div>
<div id="rejectBill-import-dialog"></div>
<script type="text/javascript">

    function confirmStorageWidget() {
        $("#sales-hidden-storager").widget({
            referwid: 'RL_T_BASE_PERSONNEL_STORAGER',
            width: 160,
            col: 'name',
            singleSelect: true,
            initValue: '${storager}',
        });
    }
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

    var initQueryJSON = $("#sales-queryForm-rejectBillListPage").serializeJSON();
    $(function () {
        $("#sales-customer-rejectBillListPage").customerWidget({ //客户参照窗口
            isdatasql: false
        });
        $("#storage-goods-saleRejectEnterPage").goodsWidget({
            singleSelect: true,
            isHiddenUsenum: true
        });
        //入库仓库
        $("#sales-storageid-rejectBillListPage").widget({
            width: 150,
            referwid: 'RL_T_BASE_STORAGE_INFO',
            singleSelect: true,
            onlyLeafCheck: false
        });

        //回车事件
        controlQueryAndResetByKey("sales-queay-rejectBillListPage", "sales-resetQueay-rejectBillListPage");

        $("#sales-queay-rejectBillListPage").click(function () {
            var queryJSON = $("#sales-queryForm-rejectBillListPage").serializeJSON();
            $("#sales-datagrid-rejectBillListPage").datagrid('load', queryJSON);
        });
        $("#sales-resetQueay-rejectBillListPage").click(function () {
            $("#sales-customer-rejectBillListPage").customerWidget("clear");
            $("#storage-goods-saleRejectEnterPage").goodsWidget("clear");
            $("#sales-storageid-rejectBillListPage").widget('clear');
            $("#sales-queryForm-rejectBillListPage").form("reset");
            var queryJSON = $("#sales-queryForm-rejectBillListPage").serializeJSON();
            $("#sales-datagrid-rejectBillListPage").datagrid('load', queryJSON);
        });
//          $("#sales-queryAdvanced-rejectBillListPage").advancedQuery({ //通用查询
//              //查询针对的表
//              name:'t_sales_rejectbill',
//              plain:true,
//              //查询针对的表格id
//              datagrid:'sales-datagrid-rejectBillListPage'
//          });
        //按钮
        $("#sales-buttons-rejectBillListPage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/sales/rejectBillAddPage.do">
                {
                    type: 'button-add',
                    handler: function () {
                        top.addTab('sales/rejectBill.do', "退货通知单新增");
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/rejectBillEditPage.do">
                <!--{-->
                <!--    type: 'button-edit',-->
                <!--    handler: function(){-->
                <!--        var con = $("#sales-datagrid-rejectBillListPage").datagrid('getSelected');-->
                <!--        if(con == null){-->
                <!--        $.messager.alert("提醒","请选择一条记录");-->
                <!--        return false;-->
                <!--        }-->
                <!--        top.addTab('sales/rejectBill.do?type=edit&id='+ con.id, "退货通知单修改");-->
                <!--    }-->
                <!--},-->
                </security:authorize>
                <security:authorize url="/sales/rejectBillViewPage.do">
                {
                    type: 'button-view',
                    handler: function () {
                        var con = $("#sales-datagrid-rejectBillListPage").datagrid('getSelected');
                        if (con == null) {
                            $.messager.alert("提醒", "请选择一条记录");
                            return false;
                        }
                        top.addTab('sales/rejectBill.do?type=edit&id=' + con.id, "退货通知单查看");
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/rejectBillPagePrintView.do">
                {
                    type: 'button-preview',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/rejectBillPagePrint.do">
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
                        name: 't_sales_rejectbill',
                        plain: true,
                        //查询针对的表格id
                        datagrid: 'sales-datagrid-rejectBillListPage'
                    }
                },
                <security:authorize url="/sales/rejectBillImport.do">
                {
                    type: 'button-import',
                    attr: {
                        url: 'sales/importRejectBill.do',
                        type: 'importUserdefined',
                        importparam: '销售退货通知单导入。客户编码、退货类型、商品编码、数量必填，司机、入库仓库选填，若数量为空，则默认为0。',
                        onClose: function () { //导入成功后窗口关闭时操作，
                            $("#sales-datagrid-rejectBillListPage").datagrid('reload');
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/rejectBilExport.do">
                {
                    type: 'button-export',
                    attr: {
                        queryForm: "#sales-queryForm-rejectBillListPage",
                        datagrid: "#sales-datagrid-rejectBillListPage",
                        type: 'exportUserdefined',
                        name: '退货通知单',
                        url: 'sales/exportRejectBill.do'
                    }
                },
                </security:authorize>
                {}
            ],
            buttons: [
                <security:authorize url="/sales/auditMultiRejectBill.do">
                {
                    id: 'button-mulitAudit',
                    name: '批量审核',
                    iconCls: 'button-audit',
                    handler: function () {
                        var fn = function (r) {
                            if (r) {
                                var rows = $("#sales-datagrid-rejectBillListPage").datagrid('getChecked');
                                if (rows.length == 0) {
                                    $.messager.alert("提醒", "请选中需要审核的记录。");
                                    return false;
                                }
                                var ids = "";
                                for (var i = 0; i < rows.length; i++) {
                                    ids += rows[i].id + ',';
                                }
                                var storager = $("#sales-hidden-storager").widget("getValue");
                                loading("审核中..");
                                $.ajax({
                                    url: 'sales/auditMultiRejectBill.do',
                                    dataType: 'json',
                                    type: 'post',
                                    data: {ids: ids, storager: storager},
                                    success: function (json) {
                                        loaded();
                                        if (json.flag == true) {
                                            $.messager.alert("提醒", "审核成功：" + json.success + "&nbsp;审核失败：" + json.failure + "<br/>" + json.msg);
                                            $("#sales-datagrid-rejectBillListPage").datagrid('reload');
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
                        };
                        $.messager.confirm({
                            title: '批量审核',
                            onOpen: function () {
                                confirmStorageWidget()
                            },
                            msg: "是否审核所选中的单据？</br><span style=\"float: left;\">选择收货人:</span><input  id=\"sales-hidden-storager\" name=\"storager\"/>",
                            fn: fn
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/addRejectBillCaseOrder.do">
                {
                    id: 'button-addrejectbill-order',
                    name: '订单退货',
                    iconCls: 'button-add',
                    handler: function () {
                        $('<div id="sales-dialog-addrejectbill-order1"></div>').appendTo("#sales-dialog-addrejectbill-order");
                        $("#sales-dialog-addrejectbill-order1").dialog({
                            title: '根据销售订单生成退货通知单',
                            fit: true,
                            closed: true,
                            modal: true,
                            maximizable: true,
                            cache: false,
                            resizable: true,
                            maximized: true,
                            href: 'sales/showAddRejectBillCaseOrderPage.do',
                            onClose: function () {
                                $('#sales-dialog-addrejectbill-order1').dialog("destroy");
                            },
                            buttons: [
                                {
                                    text: '确定',
                                    handler: function () {
                                        showRejectBillDetail();
                                    }

                                }
                            ]
                        });
                        $("#sales-dialog-addrejectbill-order1").dialog("open");
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/rejectBilModelImport.do">
                {
                    id: 'button-import-order',
                    name: '模板导入',
                    iconCls: 'button-import',
                    handler: function () {
                        $("#rejectBill-import-dialog").dialog({
                            href: 'sales/showRejectModelPage.do',
                            width: 400,
                            height: 250,
                            title: '模板文件上传',
                            colsed: false,
                            cache: false,
                            modal: true
                        });

                    }
                },
                </security:authorize>
                {}
            ],
            model: 'bill',
            type: 'list',
            tname: 't_sales_rejectbill'
        });
        var dispatchBillListJson = $("#sales-datagrid-rejectBillListPage").createGridColumnLoad({
            frozenCol: [[
                {field: 'idok', checkbox: true, isShow: true}
            ]],
            commonCol: [[{field: 'id', title: '编号', width: 120, align: 'left', sortable: true},
                {field: 'businessdate', title: '业务日期', width: 80, align: 'left', sortable: true},
                {field: 'customerid', title: '客户编码', width: 60, align: 'left', sortable: true},
                {field: 'customername', title: '客户名称', width: 100, align: 'left', isShow: true},
                {
                    field: 'billtype', title: '退货类型', width: 60, align: 'left', sortable: true,
                    formatter: function (value, row, index) {
                        if (value == "1") {
                            return "直退";
                        } else if (value == "2") {
                            return "售后退货";
                        }
                    }
                },
                {field: 'salesdept', title: '销售部门', width: 80, align: 'left', sortable: true},
                {field: 'salesuser', title: '客户业务员', width: 80, align: 'left', sortable: true},
                {
                    field: 'driverid', title: '司机', width: 80, align: 'left', sortable: true,
                    formatter: function (value, row, index) {
                        return row.drivername;
                    }
                },
                {
                    field: 'totaltaxamount', title: '金额', width: 80, align: 'right', isShow: true,
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {field: 'sourceid', title: '客户单号', width: 90, align: 'left', sortable: true},
                {
                    field: 'status', title: '状态', width: 60, align: 'left', sortable: true,
                    formatter: function (value, row, index) {
                        return getSysCodeName('status', value);
                    }
                },
                {
                    field: 'isinvoice', title: '抽单状态', width: 80, align: 'left', sortable: true,
                    formatter: function (value, row, index) {
                        if (value == "0") {
                            return "未申请";
                        }
                        else if (value == "1") {
                            return "已申请";
                        }
                        else if (value == "2") {
                            return "已核销";
                        }
                        else if (value == "3") {
                            return "未申请";
                        } else if (value == "4") {
                            return "申请中";
                        } else if (value == "5") {
                            return "部分核销";
                        }
                    }
                },
                {
                    field: 'isinvoicebill', title: '开票状态', width: 70, align: 'left', sortable: true,
                    formatter: function (value, row, index) {
                        if (value == "0") {
                            return "未开票";
                        } else if (value == "1") {
                            return "已开票";
                        } else if (value == "3") {
                            return "未开票";
                        } else if (value == "4") {
                            return "开票中";
                        }
                    }
                },
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
                    field: 'printtimes', title: '打印次数', width: 60, align: 'center',
                    formatter: function (value, rowData, index) {
                        if (value == -99) {
                            return "";
                        } else {
                            return value;
                        }
                    }
                },
                {
                    field: 'indooruserid', title: '销售内勤', width: 60, sortable: true,
                    formatter: function (value, rowData, index) {
                        return rowData.indoorusername;
                    }
                },
                {field: 'duefromdate', title: '应收日期', width: 80, align: 'left', sortable: true, hidden: true},
                {field: 'addusername', title: '制单人', width: 80, sortable: true},
                {field: 'addtime', title: '制单时间', width: 80, sortable: true, hidden: true},
                {field: 'auditusername', title: '审核人', width: 80, sortable: true, hidden: true},
                {field: 'audittime', title: '审核时间', width: 80, sortable: true, hidden: true},
                {field: 'remark', title: '备注', width: 100},
                {field: 'storagername', title: '收货人', width: 80, sortable: true, hidden: true},
            ]]
        });
        $("#sales-datagrid-rejectBillListPage").datagrid({
            authority: dispatchBillListJson,
            frozenColumns: dispatchBillListJson.frozen,
            columns: dispatchBillListJson.common,
            fit: true,
            title: "",
            method: 'post',
            rownumbers: true,
            pagination: true,
            idField: 'id',
            singleSelect: false,
            checkOnSelect: true,
            selectOnCheck: true,
            sortName: 'addtime',
            sortOrder: 'desc',
            showFooter: true,
            //fitColumns:true,
            url: 'sales/getRejectBillList.do',
            queryParams: initQueryJSON,
            toolbar: '#sales-queryDiv-rejectBillListPage',
            onDblClickRow: function (index, data) {
                var flag = isDoLockData(data.id, "t_sales_rejectbill");
                if (!flag) {
                    $.messager.alert("警告", "该数据正在被其他人操作，暂不能操作！");
                    return false;
                }
                top.addOrUpdateTab('sales/rejectBill.do?type=edit&id=' + data.id, '退货通知单查看');
            },
            onCheck: function (rowIndex, rowData) {
                countTotalAmount();
            },
            onUncheck: function (rowIndex, rowData) {
                countTotalAmount();
            },
            onCheckAll: function (rows) {
                countTotalAmount();
            },
            onUncheckAll: function (rows) {
                countTotalAmount();
            }
        }).datagrid("columnMoving");
    });
    function countTotalAmount() {
        var rows = $("#sales-datagrid-rejectBillListPage").datagrid('getChecked');
        var totaltaxamount = 0;
        for (var i = 0; i < rows.length; i++) {
            totaltaxamount = Number(totaltaxamount) + Number(rows[i].totaltaxamount == undefined ? 0 : rows[i].totaltaxamount);
        }
        var footerrows = [{id: '选中金额', totaltaxamount: totaltaxamount, printtimes: -99}];
        $("#sales-datagrid-rejectBillListPage").datagrid("reloadFooter", footerrows);
    }
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "rejectBillListPage-dialog-print",
            code: "sales_rejectbill",
            tableId: "sales-datagrid-rejectBillListPage",
            url_preview: "print/sales/rejectBillPrintView.do",
            url_print: "print/sales/rejectBillPrint.do",
            btnPreview: "button-preview",
            btnPrint: "button-print",
            printlimit: "${printlimit}"
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
