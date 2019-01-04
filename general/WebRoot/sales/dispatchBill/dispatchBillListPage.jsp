<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>销售发货通知单列表</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>
<body>
<%
    boolean showPrintOptions = false;
    boolean printOptionsOrderSeq = false;
%>
<security:authorize url="/storage/salesDeliveryOrderPrintOptions.do">
    <%
        showPrintOptions = true;
    %>
</security:authorize>
<security:authorize url="/storage/salesOrderPrintOptionsOrderSeq.do">
    <%
        printOptionsOrderSeq = true;
    %>
</security:authorize>

<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center'">
        <div id="sales-queryDiv-dispatchBillListPage" style="padding:0px;">
            <div class="buttonBG" id="sales-buttons-dispatchBillListPage" style="height:26px">
            </div>
            <form id="sales-queryForm-dispatchBillListPage">
                <table class="querytable">
                    <tr>
                        <td>编&nbsp;&nbsp;号:</td>
                        <td class="tdinput"><input type="text" name="id" style="width:225px;"/></td>
                        <td>销售订单编号:</td>
                        <td class="tdinput"><input type="text" name="billno" style="width:160px;"/></td>
                        <%--<td>销售部门：</td>
                        <td>
                            <select class="len136" name="salesdept" id="sales-salesDept-orderListPage"style="width:150px;" >
                                <option></option>
                                <c:forEach items="${salesDept}" var="list">
                                    <option value="${list.id }">${list.name }</option>
                                </c:forEach>
                            </select>
                        </td> --%>
                        <td>状态:</td>
                        <td class="tdinput">
                            <select id="sales-status-dispatchBillListPage" name="status" style="width:165px;">
                                <option></option>
                                <option value="2" selected="selected">保存</option>
                                <option value="3">审核通过</option>
                                <option value="4">关闭</option>
                            </select>
                            <span id="sales-printtip-dispatchBillListPage"></span>
                        </td>
                    </tr>
                    <tr>
                        <td>业务日期:</td>
                        <td class="tdinput"><input type="text" name="businessdate" style="width:100px;" class="Wdate"
                                                   onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/> 到 <input
                                type="text" name="businessdate1" class="Wdate" style="width:100px;"
                                onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/></td>
                        <td>配货打印状态:</td>
                        <td class="tdinput">
                            <select id="sales-phprintsign-dispatchBillListPage" name="phprintsign" style="width:160px;">
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
                            <input type="hidden" id="sales-phprinttimes-dispatchBillListPage" name="queryphprinttimes"
                                   value="0"/>
                        </td>
                    </tr>
                    <tr>
                        <td>客&nbsp;&nbsp;户:</td>
                        <td><input type="text" id="sales-customer-dispatchBillListPage" name="customerid"
                                   style="width: 225px"/></td>
                        <td>打印状态:</td>
                        <td>
                            <select id="sales-printsign-dispatchBillListPage" name="printsign" style="width:160px;">
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
                            <input type="hidden" id="sales-printtimes-dispatchBillListPage" name="queryprinttimes"
                                   value="0"/>
                        </td>
                        <td colspan="2" class="tdbutton">
                            <a href="javascript:;" id="sales-query-dispatchBillListPage" class="button-qr">查询</a>
                            <a href="javaScript:;" id="sales-resetQuery-dispatchBillListPage" class="button-qr">重置</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <table id="sales-datagrid-dispatchBillListPage" data-options="border:false"></table>
    </div>
</div>
<script type="text/javascript">
    var initQueryJSON = $("#sales-queryForm-dispatchBillListPage").serializeJSON();
    $(function () {
        $("#sales-customer-dispatchBillListPage").customerWidget({ //客户参照窗口
            width: 225,
            isdatasql: false
        });


        //回车事件
        //controlQueryAndResetByKey("sales-query-dispatchBillListPage","sales-resetQuery-dispatchBillListPage");

        $("#sales-query-dispatchBillListPage").click(function () {
            //高级查询
            var queryJSON = $("#sales-queryForm-dispatchBillListPage").serializeJSON();
            $("#sales-datagrid-dispatchBillListPage").datagrid('load', queryJSON);
            $("#sales-dialog-advancedQueryPage").dialog('close');
        });
        $("#sales-resetQuery-dispatchBillListPage").click(function () {
            $("#sales-customer-dispatchBillListPage").customerWidget("clear");
            $("#sales-queryForm-dispatchBillListPage").form("reset");
            var queryJSON = $("#sales-queryForm-dispatchBillListPage").serializeJSON();
            $("#sales-datagrid-dispatchBillListPage").datagrid('load', queryJSON);
        });
//            $("#sales-queryAdvanced-dispatchBillListPage").advancedQuery({ //通用查询
//                //查询针对的表
//                name:'t_sales_dispatchbill',
//                plain:true,
//                //查询针对的表格id
//                datagrid:'sales-datagrid-dispatchBillListPage'
//            });
        //按钮
        $("#sales-buttons-dispatchBillListPage").buttonWidget({
            initButton: [
                {},
                //通用查询
//                    {
//                        type: 'button-commonquery',
//                        attr:{
//                            //查询针对的表
//                            name:'t_sales_dispatchbill',
//                            plain:true,
//                            //查询针对的表格id
//                            datagrid:'sales-datagrid-dispatchBillListPage'
//                        }
//                    },
                <security:authorize url="/sales/dispatchBillAddPage.do">
                {
                    type: 'button-add',
                    handler: function () {
                        top.addTab('sales/dispatchBill.do', "销售发货通知单新增");
                    }
                },
                </security:authorize>
                <%--<security:authorize url="/sales/dispatchBillEditPage.do">--%>
                <!--					{-->
                <!--						type: 'button-edit',-->
                <!--						handler: function(){-->
                <!--							var con = $("#sales-datagrid-dispatchBillListPage").datagrid('getSelected');-->
                <!--							if(con == null){-->
                <!--								$.messager.alert("提醒","请选择一条记录");-->
                <!--								return false;-->
                <!--							}	-->
                <!--							top.addTab('sales/dispatchBill.do?type=edit&id='+ con.id, "销售发货通知单修改");-->
                <!--						}-->
                <!--					},-->
                <%--</security:authorize>--%>
                <security:authorize url="/sales/dispatchBillViewPage.do">
                {
                    type: 'button-view',
                    handler: function () {
                        var con = $("#sales-datagrid-dispatchBillListPage").datagrid('getSelected');
                        if (con == null) {
                            $.messager.alert("提醒", "请选择一条记录");
                            return false;
                        }
                        top.addTab('sales/dispatchBill.do?type=edit&id=' + con.id, "销售发货通知单查看");
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/dispatchBillImport.do">
                {
                    type: 'button-import',
                    attr: {}
                },
                </security:authorize>
                <security:authorize url="/sales/dispatchBillExport.do">
                {
                    type: 'button-export',
                    attr: {}
                },
                </security:authorize>
                {
                    type: 'button-commonquery',
                    attr: {
                        name: 't_sales_dispatchbill',
                        plain: true,
                        //查询针对的表格id
                        datagrid: 'sales-datagrid-dispatchBillListPage'
                    }
                },
                {}
            ],
            buttons: [
                <security:authorize url="/sales/dispatchBillPrintBtn.do">
                {
                    id: 'menuButton',
                    type: 'menu',
                    name: '打印',
                    iconCls: 'button-print',
                    button: [
                        <security:authorize url="/storage/salesDeliveryOrderPrintView.do">
                        {
                            id: 'button-printview-DeliveryOrder',
                            name: '库位套打预览',
                            iconCls: 'button-preview',
                            handler: function () {
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/storage/salesDeliveryOrderPrint.do">
                        {
                            id: 'button-print-DeliveryOrder',
                            name: '库位套打',
                            iconCls: 'button-print',
                            handler: function () {
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/storage/salesOrderblankPrintView.do">
                        {
                            id: 'button-printview-orderblank',
                            name: '配货打印预览',
                            iconCls: 'button-preview',
                            handler: function () {
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/storage/salesOrderblankPrint.do">
                        {
                            id: 'button-print-orderblank',
                            name: '配货打印',
                            iconCls: 'button-print',
                            handler: function () {
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/storage/salesDispatchBillPrintView.do">
                        {
                            id: 'button-printview-DispatchBill',
                            name: '订单套打预览',
                            iconCls: 'button-preview',
                            handler: function () {
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/storage/salesDispatchBillPrint.do">
                        {
                            id: 'button-print-DispatchBill',
                            name: '订单套打',
                            iconCls: 'button-print',
                            handler: function () {
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
            tname: 't_sales_dispatchbill'
        });
        var dispatchBillListJson = $("#sales-datagrid-dispatchBillListPage").createGridColumnLoad({
            name: 't_sales_dispatchbill',
            frozenCol: [[
                {field: 'idok', checkbox: true, isShow: true}
            ]],
            commonCol: [[{field: 'id', title: '编号', width: 130, align: 'left', sortable: true},
                {field: 'billno', title: '销售订单编号', width: 110, align: 'left', sortable: true},
                {field: 'businessdate', title: '业务日期', width: 80, align: 'left', sortable: true},
                {field: 'customerid', title: '客户编码', width: 60, align: 'left', sortable: true},
                {field: 'customername', title: '客户名称', width: 130, align: 'left', isShow: true},
                {field: 'handlerid', title: '对方经手人', width: 70, align: 'left'},
                {field: 'salesdept', title: '销售部门', width: 80, align: 'left', hidden: true},
                {field: 'salesuser', title: '客户业务员', width: 70, align: 'left', sortable: true},
                {
                    field: 'source', title: '来源类型', width: 80, align: 'left', hidden: true,
                    formatter: function (value, row, index) {
                        if (value == "1") {
                            return "销售订单";
                        } else if (value == "2") {
                            return "直营销售单";
                        }
                        else {
                            return "无";
                        }
                    }
                },
                {
                    field: 'field01', title: '金额', width: 70, align: 'right',
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'field02', title: '未税金额', width: 80, align: 'right', hidden: true,
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'field03', title: '税额', width: 80, align: 'right', hidden: true,
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'status', title: '状态', width: 60, align: 'left', sortable: true,
                    formatter: function (value, row, index) {
                        return row.statusname;
                    }
                },
                {
                    field: 'indooruserid', title: '销售内勤', width: 60, sortable: true,
                    formatter: function (value, rowData, index) {
                        return rowData.indoorusername;
                    }
                },
                {field: 'vouchertimes', title: '生成凭证次数', align: 'center', width:80},
                {field: 'addusername', title: '制单人', width: 60, sortable: true, hidden: true},
                {field: 'addtime', title: '制单时间', width: 120, sortable: true},
                {field: 'auditusername', title: '审核人', width: 60, sortable: true},
                {field: 'audittime', title: '审核时间', width: 120},
                {field: 'remark', title: '备注', width: 100},
                {field: 'printtimes', title: '打印次数', width: 60, hidden: true},
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
                {field: 'printdatetime', title: '打印时间', width: 130},
                {field: 'phprinttimes', title: '配货打印次数', width: 60, hidden: true},
                {
                    field: 'phprintstate', title: '配货打印状态', width: 80, isShow: true, hidden: true,
                    formatter: function (value, rowData, index) {
                        if (rowData.phprinttimes > 0) {
                            return "已打印";
                        } else if (rowData.phprinttimes == -99) {
                            return "";
                        } else {
                            return "未打印";
                        }
                    }
                    <security:authorize url="/storage/salesOrderblankPrint.do">
                    , hidden: false
                    </security:authorize>
                },
                {field: 'phprintdatetime', title: '配货打印时间', width: 130,hidden:true
                    <security:authorize url="/storage/salesOrderblankPrint.do">
                    , hidden: false
                    </security:authorize>
                }
            ]]
        });
        $("#sales-datagrid-dispatchBillListPage").datagrid({
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
            sortName: 'audittime',
            sortOrder: 'desc',
            //fitColumns:true,
            url: 'sales/getDispatchBillList.do',
            queryParams: initQueryJSON,
            toolbar: '#sales-queryDiv-dispatchBillListPage',
            onDblClickRow: function (index, data) {
                top.addOrUpdateTab('sales/dispatchBill.do?type=edit&id=' + data.id, '销售发货通知单查看');
            }
        }).datagrid("columnMoving");
    });

    $(document).bind('keydown', 'esc', function () {
        if ($("#sales-dialog-advancedQueryPage-opened").val() == "1") {
            $("#sales-dialog-advancedQueryPage").dialog("close");
        }
    });
    $(document).bind('keydown', 'alt+q', function () {
        if ($("#sales-dialog-advancedQueryPage-opened").val() == "1") {
            $("#sales-dialog-advancedQueryPage-ok").trigger("click");
        } else {
            $("#sales-dialog-advancedQueryPage-ok").trigger("click");
            $("#sales-query-dispatchBillListPage").trigger("click");
            $("#sales-dialog-advancedQueryPage-opened").val("1");
        }
        return false;
    });
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        var $grid = $("#sales-datagrid-dispatchBillListPage");
        var printLimit = "${printlimit}";
        function getData(tableId, printParam, isPrint) {
            var data = $grid.datagrid("getChecked");
            if (data == null || data.length == 0) {
                $.messager.alert("提醒", "请选择至少一条记录");
                return false;
            }
            var idarray = [];
            var printIds = [];
            var fHPrintAfterSaleOutAudit = "${fHPrintAfterSaleOutAudit}";
            for (var i = 0; i < data.length; i++) {
                if (data[i].billno && data[i].billno != "") {
                    if (fHPrintAfterSaleOutAudit == '1') {
                        if (data[i].status == '4') {
                            idarray.push(data[i].billno);
                        } else {
                            $.messager.alert("提醒", "抱歉，关闭状态下才能打印。<br/>" + data[i].id + "此销售发货通知单不能进行发货打印");
                            return false;
                        }
                    } else {
                        if (data[i].status == '3' || data[i].status == '4') {
                            idarray.push(data[i].billno);
                        } else {
                            $.messager.alert("提醒", data[i].id + "此销售发货通知单不能进行发货打印");
                            return false;
                        }
                    }
                } else {
                    $.messager.alert("提醒", data[i].id + "此销售发货通知单不能进行发货打印");
                    return false;
                }
                if (data[i].printtimes > 0) {
                    printIds.push(data[i].billno);
                }
            }
            if (isPrint) {
                printParam.saleidarrs = idarray.join(",");
            } else {
                printParam.saleidarrs = idarray[0];
            }
            printParam.printIds = printIds;
            return true;
        }

        function printSuccess(option) {
            var dataList = $grid.datagrid("getChecked");
            for (var i = 0; i < dataList.length; i++) {
                if (dataList[i].printtimes && !isNaN(dataList[i].printtimes)) {
                    dataList[i].printtimes = dataList[i].printtimes + 1;
                } else {
                    dataList[i].printtimes = 1;
                }
                var rowIndex = $grid.datagrid('getRowIndex', dataList[i].id);
                $grid.datagrid('updateRow', {index: rowIndex, row: dataList[i]});
            }
        }

        //库位套打
        AgReportPrint.init({
            id: "listPage-DeliveryOrder-dialog-print",
            code: "storage_deliveryorder",
            url_preview: "print/sales/salesDeliveryOrderPrintView.do",
            url_print: "print/sales/salesDeliveryOrderPrint.do",
            btnPreview: "button-printview-DeliveryOrder",
            btnPrint: "button-print-DeliveryOrder",
            printlimit: "${printlimit}",
            exPrintParam: {
                printOrder: 1
            },
            getData: getData,
            onPrintSuccess: printSuccess
        });
        //订单套打
        AgReportPrint.init({
            id: "listPage-dispatchbill-dialog-print",
            code: "storage_dispatchbill",
            url_preview: "print/sales/salesDispatchBillPrintView.do",
            url_print: "print/sales/salesDispatchBillPrint.do",
            btnPreview: "button-printview-DispatchBill",
            btnPrint: "button-print-DispatchBill",
            printlimit: printLimit,
            getData: getData,
            onPrintSuccess: printSuccess
        });
        //配货打印
        AgReportPrint.init({
            id: "listPage-orderblank-dialog-print",
            code: "storage_orderblank",
            url_preview: "print/sales/salesOrderblankPrintView.do",
            url_print: "print/sales/salesOrderblankPrint.do",
            btnPreview: "button-printview-orderblank",
            btnPrint: "button-print-orderblank",
            printlimit: printLimit,
            exPrintParam: {
                printOrder: 1
            },
            getData: function (tableId, printParam, isPrint) {
                var data = $grid.datagrid("getChecked");
                if (data == null || data.length == 0) {
                    $.messager.alert("提醒", "请选择至少一条记录");
                    return false;
                }
                var idarray = [];
                var printIds = [];
                for (var i = 0; i < data.length; i++) {
                    if (data[i].billno && data[i].billno != "") {
                        //状态不是审核通过或关闭
                        if (isPrint && !(data[i].status == '3' || data[i].status == '4')) {
                            $.messager.alert("提醒", data[i].id + "此销售通知单不可进行配货打印");
                            return false;
                        }
                        idarray.push(data[i].billno);
                    } else {
                        $.messager.alert("提醒", data[i].id + "此销售通知单不可进行配货打印");
                        return false;
                    }
                    if (data[i].phprinttimes > 0) {
                        printIds.push(data[i].id);
                    }
                }
                if (isPrint) {
                    printParam.saleidarrs = idarray.join(",");
                } else {
                    printParam.saleidarrs = idarray[0];
                }
                printParam.printIds = printIds;
                return true;
            },
            onPrintSuccess: function (option) {
                var dataList = $grid.datagrid("getChecked");
                for (var i = 0; i < dataList.length; i++) {
                    if (dataList[i].phprinttimes && !isNaN(dataList[i].phprinttimes)) {
                        dataList[i].phprinttimes = dataList[i].phprinttimes + 1;
                    } else {
                        dataList[i].phprinttimes = 1;
                    }
                    var rowIndex = $grid.datagrid('getRowIndex', dataList[i].id);
                    $grid.datagrid('updateRow', {index: rowIndex, row: dataList[i]});
                }
            }
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
