<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>采购进货单列表</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="purchase-buttons-arrivalOrderListPage"></div>
        <a href="javaScript:void(0);" id="purchase-btn-exportArrivalOrderListPage-excel" style="display: none">导出数据</a>
    </div>
    <div data-options="region:'center'">
        <table id="purchase-table-arrivalOrderListPage"></table>
        <div id="purchase-table-query-arrivalOrderListPage" style="padding:2px;height:auto">
            <div>
                <form action="" id="purchase-form-arrivalOrderListPage" method="post">
                    <table class="querytable">
                        <tr>
                            <td>业务日期:</td>
                            <td class="tdinput">
                                <input type="text" id="purchase-arrivalOrderListPage-businessdatestart"
                                       name="businessdatestart" style="width:100px;" class="Wdate"
                                       onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/> 到 <input type="text"
                                                                                                  id="purchase-arrivalOrderListPage-businessdateend"
                                                                                                  name="businessdateend"
                                                                                                  class="Wdate"
                                                                                                  style="width:100px;"
                                                                                                  onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/>
                            </td>
                            <td>入库仓库:</td>
                            <td class="tdinput">
                                <input type="text" name="storageid" id="purchase-arrivalOrderListPage-buydept"
                                       style="width:180px;"/>
                                <!--
                                <select class="len136" name="storageid" id="purchase-arrivalOrderListPage-buydept" style="width:150px;" >
                                    <option></option>
                                    <c:forEach items="${storageList}" var="list">
                                        <option value="${list.id }">${list.name }</option>
                                    </c:forEach>
                                </select> -->
                            </td>
                            <td>编号:</td>
                            <td><input type="text" name="id" style="width:165px;"/></td>
                        </tr>
                        <tr>
                            <td>采购部门:</td>
                            <td class="tdinput">
                                <input type="text" id="purchase-arrivalOrderListPage-buydeptid" name="buydeptid"/></td>
                            <td>商品名称:</td>
                            <td class="tdinput">
                                <input id="purchase-arrivalOrderListPage-goodsid" type="text" name="goodsid"
                                       style="width: 180px;"/></td>
                            <td>状态:</td>
                            <td>
                                <select id="purchase-arrivalOrderListPage-isAudit" name="isAudit" style="width:165px;">
                                    <option></option>
                                    <option value="1" selected="selected">未审核</option>
                                    <option value="2">已审核</option>
                                </select>
                            </td>
                        </tr>
                        <tr>
                            <td>供 应 商:</td>
                            <td class="tdinput">
                                <input id="purchase-arrivalOrderListPage-supplier" type="text" name="supplierid"
                                       style="width: 225px;"/></td>
                            <td>打印状态:</td>
                            <td class="tdinput">
                                <select name="printsign" style="width:180px;">
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
                            <td rowspan="3" colspan="2" class="tdbutton">
                                <a href="javaScript:void(0);" id="purchase-btn-queryArrivalOrderListPage"
                                   class="button-qr">查询</a>
                                <a href="javaScript:void(0);" id="purchase-btn-reloadArrivalOrderListPage"
                                   class="button-qr">重置</a>
                                <span id="purchase-table-query-arrivalOrderListPage-advanced"></span>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
    </div>
</div>
<div id="purchase-account-dialog"></div>
<script type="text/javascript">
    var SR_footerobject = null;
    $(document).ready(function () {
        var initQueryJSON = $("#purchase-form-arrivalOrderListPage").serializeJSON();
        var arrivalOrderListJson = $("#purchase-table-arrivalOrderListPage").createGridColumnLoad({
//            name: 'purchase_arrivalorder',
            frozenCol: [[
                {field: 'idok', checkbox: true, isShow: true}
            ]],
            commonCol: [[
                {field: 'id', title: '编号', width: 150, sortable: true},
                {field: 'businessdate', title: '业务日期', width: 80, sortable: true},
                {field: 'supplierid', title: '供应商编码', width: 80, sortable: true},
                {field: 'suppliername', title: '供应商名称', width: 150, isShow: true},
                {
                    field: 'handlerid', title: '对方联系人', width: 80,
                    formatter: function (value, row, index) {
                        return row.handlername;
                    }
                },
                {
                    field: 'buydeptid', title: '采购部门', width: 80,
                    formatter: function (value, row, index) {
                        return row.buydeptname;
                    }
                },
                {
                    field: 'field01', title: '金额', width: 80, align: 'right',
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'oldtaxamount', title: '分摊前金额', width: 80, align: 'right',hidden: true,
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
                    field: 'oldnotaxamount', title: '分摊前未税金额', width: 80, align: 'right', hidden: true,
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
                    field: 'oldtax', title: '分摊前税额', width: 80, align: 'right', hidden: true,
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {field: 'changeamount', title: '费用金额', width: 80, align: 'right',hidden:true,
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'source', title: '来源类型', width: 80,
                    formatter: function (value, row, index) {
                        if (value == '1') {
                            return "采购入库单";
                        } else if (value == " ") {
                            return "";
                        } else if (value == "-999") {
                            return "";
                        } else if (value == "2") {
                            return "代配送"
                        }
                        else {
                            return "无";
                        }
                    }
                },
                {field: 'billno', title: '来源编号', width: 130, align: 'left', sortable: true},
                {field: 'vouchertimes', title: '生成凭证次数', align: 'center', width:80},
                {field: 'addusername', title: '制单人', width: 80},
                {field: 'adddeptname', title: '制单人部门', width: 100, hidden: true},
                {field: 'addtime', title: '制单时间', width: 120, sortable: true, hidden: true},
                {field: 'modifyusername', title: '修改人', width: 120, hidden: true},
                {field: 'auditusername', title: '审核人', width: 100, hidden: true},
                {field: 'audittime', title: '审核时间', width: 100, hidden: true},
                {
                    field: 'status', title: '状态', width: 60,
                    formatter: function (value, row, index) {
                        return getSysCodeName('status', value);
                    }
                },
                {
                    field: 'isinvoice', title: '发票状态', width: 65,
                    formatter: function (value, row, index) {
                        if (value == null || value == "" || value == "0") {
                            if (row.isrefer == '1') {
                                return "发票未审核";
                            } else {
                                return "未开票";
                            }
                        } else if (value == "1") {
                            return "已开票";
                        } else if (value == "2") {
                            return "核销";
                        } else if (value == "3") {
                            return "开票中";
                        } else if (value == "-999") {
                            return "";
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
                {field: 'printtimes', title: '打印次数', align: 'center', width: 60,
                    formatter: function (value, rowData, index) {
                        if (value == -99) {
                            return "";
                        } else {
                            return value;
                        }
                    }
                },
                {field: 'remark', title: '备注', width: 100}
            ]]
        });
        $("#purchase-table-arrivalOrderListPage").datagrid({
            fit: true,
            method: 'post',
            rownumbers: true,
            pagination: true,
            idField: 'id',
            singleSelect: false,
            checkOnSelect: true,
            selectOnCheck: true,
            showFooter: true,
            toolbar: '#purchase-table-query-arrivalOrderListPage',
            url: "purchase/arrivalorder/showArrivalOrderPageList.do",
            queryParams: initQueryJSON,
            sortName: 'businessdate',
            sortOrder: 'desc',
            pageSize: 100,
            authority: arrivalOrderListJson,
            frozenColumns: arrivalOrderListJson.frozen,
            columns: arrivalOrderListJson.common,
            onLoadSuccess: function () {
                var footerrows = $(this).datagrid('getFooterRows');
                if (null != footerrows && footerrows.length > 0) {
                    SR_footerobject = footerrows[0];
                    if (SR_footerobject != null) {
                        SR_footerobject.printtimes = -99;
                    }
                    countTotalAmount();
                }
            },
            onDblClickRow: function (index, data) {
                top.addOrUpdateTab('purchase/arrivalorder/arrivalOrderPage.do?type=edit&id=' + data.id, "采购进货单查看");
            },
            onCheckAll: function (rows) {
                countTotalAmount();
            },
            onUncheckAll: function (rows) {
                countTotalAmount();
            },
            onCheck: function (index, row) {
                countTotalAmount();
            },
            onUncheck: function (index, row) {
                countTotalAmount();
            }
        }).datagrid("columnMoving");

        //采购部门控件
        $("#purchase-arrivalOrderListPage-buydeptid").widget({
            referwid: 'RL_T_BASE_DEPARTMENT_BUYER',
            singleSelect: true,
            width: 225,
            onlyLeafCheck: false
        });
        //按钮
        $("#purchase-buttons-arrivalOrderListPage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/purchase/arrivalorder/arrivalOrderAddBtn.do">
                {
                    type: 'button-add',
                    handler: function () {
                        top.addOrUpdateTab('purchase/arrivalorder/arrivalOrderPage.do', '采购进货单新增');
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/arrivalorder/arrivalOrderEditBtn.do">
                {
                    type: 'button-edit',
                    handler: function () {
                        var datarow = $("#purchase-table-arrivalOrderListPage").datagrid('getSelected');
                        if (datarow == null || datarow.id == null) {
                            $.messager.alert("提醒", "请选择要修改的采购进货单");
                            return false;
                        }
                        top.addOrUpdateTab('purchase/arrivalorder/arrivalOrderPage.do?type=edit&id=' + datarow.id, '采购进货单修改');
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/arrivalorder/arrivalOrderViewBtn.do">
                {
                    type: 'button-view',
                    handler: function () {
                        var datarow = $("#purchase-table-arrivalOrderListPage").datagrid('getSelected');
                        if (datarow == null || datarow.id == null) {
                            $.messager.alert("提醒", "请选择要查看的采购进货单");
                            return false;
                        }
                        top.addOrUpdateTab('purchase/arrivalorder/arrivalOrderPage.do?type=edit&id=' + datarow.id, '采购进货单查看');
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/arrivalorder/arrivalOrderExport.do">
                {
                    type: 'button-export',
                    attr: {
                        datagrid: "#purchase-table-arrivalOrderListPage",
                        queryForm: "#purchase-form-arrivalOrderListPage",
                        type: 'exportUserdefined',
                        name: '采购进货单列表',
                        url: 'purchase/arrivalorder/exportArrivalOrderData.do'
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/arrivalorder/arrivalOrderPrintView.do">
                {
                    type: 'button-preview',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/arrivalorder/arrivalOrderPrint.do">
                {
                    type: 'button-print',
                    handler: function () {
                    }
                },
                </security:authorize>
                {
                    type: 'button-commonquery',
                    attr: {
                        name: 'purchase_arrivalorder',
                        plain: true,
                        datagrid: 'purchase-table-arrivalOrderListPage'
                    }
                },
                {}
            ],
            buttons: [
                <security:authorize url="/purchase/arrivalorder/exportArrivalOrderPageList.do">
                {
                    id: 'purchase-btn-exportArrivalOrderListPage',
                    name: '限制导出',
                    iconCls: 'button-export',
                    handler: function () {
                        var businessdatestart = $.trim($("#purchase-arrivalOrderListPage-businessdatestart").val() || "");
                        var businessdateend = $.trim($("#purchase-arrivalOrderListPage-businessdateend").val() || "");
                        var isclose = $.trim($("#purchase-arrivalOrderListPage-isAudit").val() || "");
                        //鸿都版本
                        if (isclose != "2") {
                            $.messager.alert('提醒', '抱歉，导出数据时，状态必须为已审核。');
                            $("#purchase-btn-exportArrivalOrderListPage-excel").unbind("click");
                            return false;
                        }
                        //鸿都版本
                        $("#purchase-btn-exportArrivalOrderListPage-excel").Excel('export', {
                            queryForm: "#purchase-form-arrivalOrderListPage",
                            type: 'exportUserdefined',
                            name: '采购进货单列表',
                            url: 'purchase/arrivalorder/exportArrivalOrderData.do'
                        });
                        $("#purchase-btn-exportArrivalOrderListPage-excel").trigger("click");
                    }
                },
                </security:authorize>
                <security:authorize url="/purchase/arrivalorder/batchAuditArrivalOrderList.do">
                {
                    id: 'purchase-btn-batchAudit',
                    name: '批量审核',
                    iconCls: 'button-audit',
                    handler: function () {
                        var data = $("#purchase-table-arrivalOrderListPage").datagrid('getChecked');
                        if (data == null || data.length == 0) {
                            $.messager.alert("提醒", "请选择至少一条记录");
                            return false;
                        }
                        var idarray = [];
                        for (var i = 0; i < data.length; i++) {
                            idarray.push(data[i].id);
                        }
                        $.messager.confirm("提醒", "是否审核选中的采购进货单信息？", function (r) {
                            if (r) {
                                loading("审核中..");
                                $.ajax({
                                    url: 'purchase/arrivalorder/batchAuditArrivalOrder.do?ids=' + idarray,
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (json) {
                                        loaded();
                                        if (json.flag) {
                                            $.messager.alert("提醒", "审核成功:" + json.success + "<br/>审核失败：" + json.failure);
                                            $('#purchase-table-arrivalOrderListPage').datagrid('reload');
                                        } else {
                                            $.messager.alert("提醒", "审核失败:" + json.failure);
                                        }
                                    },
                                    error: function () {
                                        loaded();
                                        $.messager.alert("错误", "审核失败");
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/erpconnect/addPurchaseAccountVouch.do">
                {
                    id: 'purchase-account',
                    name: '生成凭证',
                    iconCls: 'button-audit',
                    handler: function () {
                        var rows = $("#purchase-table-arrivalOrderListPage").datagrid('getChecked');
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

                        $("#purchase-account-dialog").dialog({
                            title: '采购凭证',
                            width: 400,
                            height: 260,
                            closed: false,
                            modal: true,
                            cache: false,
                            href: 'erpconnect/showPurchaseAccountVouchPage.do',
                            onLoad: function () {
                                $("#purchaseAccount-ids").val(ids);
                            }
                        });
                    }
                }
                </security:authorize>
            ],
            model: 'bill',
            type: 'list',
            datagrid: 'purchase-table-arrivalOrderListPage',
            tname: 't_purchase_arrivalorder'

        });

        //仓库
        $("#purchase-arrivalOrderListPage-buydept").widget({
            width: 180,
            referwid: 'RL_T_BASE_STORAGE_INFO',
            singleSelect: true,
            onlyLeafCheck: false
        });


        $("#purchase-arrivalOrderListPage-supplier").supplierWidget({
            name: 't_purchase_arrivalorder',
            col: 'supplierid',
            width: 225,
            singleSelect: true,
            onlyLeafCheck: true,
            onSelect: function (data) {

            }
        });
        $("#purchase-btn-queryArrivalOrderListPage").click(function () {
            //查询参数直接添加在url中
            var queryJSON = $("#purchase-form-arrivalOrderListPage").serializeJSON();
            $('#purchase-table-arrivalOrderListPage').datagrid('load', queryJSON);
        });
        $("#purchase-btn-reloadArrivalOrderListPage").click(function () {
            $("#purchase-form-arrivalOrderListPage")[0].reset();
            $("#purchase-arrivalOrderListPage-buydeptid").widget('clear');
            $("#purchase-arrivalOrderListPage-buydept").widget('clear');
            $("#purchase-arrivalOrderListPage-goodsid").goodsWidget('clear');
            $("#purchase-arrivalOrderListPage-supplier").supplierWidget("clear");
            var queryJSON = $("#purchase-form-arrivalOrderListPage").serializeJSON();
            $('#purchase-table-arrivalOrderListPage').datagrid('load', queryJSON);
        });

        $("#purchase-arrivalOrderListPage-goodsid").goodsWidget({
            singleSelect: true,
            isHiddenUsenum: true
        });
    });
    function countTotalAmount() {
        var rows = $("#purchase-table-arrivalOrderListPage").datagrid('getChecked');
        var taxamount = 0;
        var notaxamount = 0;
        var tax = 0;
        for (var i = 0; i < rows.length; i++) {
            taxamount = Number(taxamount) + Number(rows[i].field01 == undefined ? 0 : rows[i].field01);
            notaxamount = Number(notaxamount) + Number(rows[i].field02 == undefined ? 0 : rows[i].field02);
            tax = Number(tax) + Number(rows[i].field03 == undefined ? 0 : rows[i].field03);
        }
        var foot = [{
            id: '选中金额',
            field01: taxamount,
            field02: notaxamount,
            field03: tax,
            source: " ",
            isinvoice: " ",
            printtimes: -99
        }];
        if (null != SR_footerobject) {
            foot.push(SR_footerobject);
        }
        $("#purchase-table-arrivalOrderListPage").datagrid("reloadFooter", foot);
    }
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //打印
        AgReportPrint.init({
            id: "listPage-ArrivalOrder-dialog-print",
            code: "purchase_arrialorder",
            tableId: "purchase-table-arrivalOrderListPage",
            url_preview: "print/purchase/arrivalOrderPrintView.do",
            url_print: "print/purchase/arrivalOrderPrint.do",
            btnPreview: "button-preview",
            btnPrint: "button-print",
            printlimit: "${printlimit}",
            getData: function (tableId, printParam) {
                var data = $("#" + tableId).datagrid('getChecked');
                if (data == null || data.length == 0) {
                    $.messager.alert("提醒", "请选择至少一条记录");
                    return false;
                }
                for (var i = 0; i < data.length; i++) {
                    if (data[i].status != '3' && data[i].status != '4') {
                        $.messager.alert("提醒", data[i].id + "此采购进货单不可打印");
                        return false;
                    }
                }
                return data;
            }
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
