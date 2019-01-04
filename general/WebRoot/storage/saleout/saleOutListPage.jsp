<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>发货单列表页面</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>

<body>
<%
    boolean printAuth = false;
    boolean phprintAuth = false;
%>
<security:authorize url="/storage/storageOrderblankPrint.do">
    <%
        phprintAuth = true;
    %>
</security:authorize>
<security:authorize url="/storage/storageDeliveryOrderPrint.do">
    <% printAuth = true; %>
</security:authorize>

<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="storage-buttons-saleoutPage"></div>
        <a style="display: none" href="javaScript:void(0);" id="storage-btn-exportSaleoutDetailData" style="display: none">导出数据</a>
    </div>
    <div data-options="region:'center'">
        <table id="storage-datagrid-saleoutPage" data-options="border:false"></table>
        <div id="storage-datagrid-toolbar-saleoutPage" style="padding:2px;height:auto">
            <form action="" id="storage-form-query-saleoutPage" method="post">
                <input type="hidden" id="storage-query-selectids" name="selectids"/>
                <table class="querytable">
                    <tr>
                        <td>业务日期:</td>
                        <td class="tdinput"><input type="text" name="businessdate1" style="width:100px;" class="Wdate"
                                                   onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/>
                            到 <input type="text" name="businessdate2" class="Wdate" style="width:100px;"
                                     onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/></td>
                        <td>编&nbsp;&nbsp;号:</td>
                        <td class="tdinput"><input type="text" name="id" style="width:150px;"/></td>
                        <td>销售订单编号:</td>
                        <td class="tdinput"><input type="text" name="saleorderid" style="width: 150px;"/></td>
                    </tr>
                    <tr>
                        <td>商品名称:</td>
                        <td class="tdinput"><input id="storage-query-goodsid" type="text" name="goodsid"
                                                   style="width: 225px;"/></td>
                        <td>状&nbsp;&nbsp;态:</td>
                        <td class="tdinput">
                            <select id="sales-status-saleoutPage" name="status" style="width:150px;">
                                <option></option>
                                <option value="2" selected="selected">保存</option>
                                <option value="3">审核通过</option>
                                <option value="4">关闭</option>
                            </select>
                            <span id="sales-printtip-saleoutPage"></span>
                        </td>
                        <td>出库仓库:</td>
                        <td class="tdinput">
                            <select class="len150" name="storageid" id="storage-query-storageid">
                                <option></option>
                                <c:forEach items="${storageList}" var="list">
                                    <option value="${list.id }">${list.name }</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>客&nbsp;&nbsp;户:</td>
                        <td><input id="storage-query-customerid" type="text" name="customerid" style="width: 225px;"/>
                        </td>
                        <td>打印状态：</td>
                        <td>
                            <select name="printsign" style="width:150px;">
                                <option></option>
                                <option value="1">未打印</option>
                                <option value="4">已打印</option>
                            </select>
                            <input type="hidden" name="queryprinttimes" value="0"/>
                        </td>
                        <td>是否大单发货:</td>
                        <td><select name="isbigsaleout" style="width: 150px;">
                            <option selected="selected"></option>
                            <option value="0">否</option>
                            <option value="1">是</option>
                        </select>
                        </td>


                        <!--     				<td>配货打印次数:</td> -->
                        <!-- 					<td>							 -->
                        <!-- 						<select id="sales-phprintsign-saleoutPage" name="phprintsign" style="width:75px;"> -->
                        <!-- 							<option></option> -->
                        <!-- 							<option value="1">等于</option> -->
                        <!-- 							<option value="2">小于</option> -->
                        <!-- 							<option value="3">小于等于</option> -->
                        <!-- 							<option value="4">大于</option> -->
                        <!-- 							<option value="5">大于等于</option>								 -->
                        <!-- 						</select> -->
                        <!-- 						<input type="text" id="sales-phprinttimes-saleoutPage" name="queryphprinttimes" style="width:60px;"/> -->
                        <!-- 					</td> -->
                    </tr>
                    <tr>
                        <td colspan="4"></td>
                        <td colspan="2">
                            <a href="javaScript:void(0);" id="storage-queay-saleout" class="button-qr">查询</a>
                            <a href="javaScript:void(0);" id="storage-reload-saleout" class="button-qr">重置</a>
                            <span id="storage-query-advanced-saleout"></span>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
</div>
<div id="storage-dialog-saleOutListPage" class="easyui-dialog" data-options="closed:true"></div>
<div id="storage-addVoucher-dialog"></div>
<div style="display:none">
    <%--通用 --%>
    <div id="listPage-Orderblank-dialog-print">
        <form action="" id="listPage-Orderblank-dialog-print-form" method="post">
            <table>
                <tr>
                    <td>打印模板：</td>
                    <td>
                        <select id="listPage-Orderblank-dialog-print-templet" name="templetid">
                        </select>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <div id="listPage-DeliveryOrder-dialog-print">
        <form action="" id="listPage-DeliveryOrder-dialog-print-form" method="post">
            <table>
                <tr>
                    <td>打印模板：</td>
                    <td>
                        <select id="listPage-DeliveryOrder-dialog-print-templet" name="templetid">
                        </select>
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>
<script type="text/javascript">
    var initQueryJSON = $("#storage-form-query-saleoutPage").serializeJSON();
    function confirmStorageWidget() {
        $("#storage-hidden-storager").widget({
            referwid: 'RL_T_BASE_PERSONNEL_STORAGER',
            width: 160,
            col: 'name',
            singleSelect: true,
            initValue: '${storager}',
        });
    }
    $(function () {
        //按钮
        $("#storage-buttons-saleoutPage").buttonWidget({
            initButton: [
                {},
                <security:authorize url="/storage/showSaleOutViewPage.do">
                {
                    type: 'button-view',
                    handler: function () {
                        var con = $("#storage-datagrid-saleoutPage").datagrid('getSelected');
                        if (con == null) {
                            $.messager.alert("提醒", "请选择一条记录");
                            return false;
                        }
                        top.addOrUpdateTab('storage/showSaleOutEditPage.do?id=' + con.id, "发货单查看");
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/exportSaleOutList.do">
                {
                    type: 'button-export',
                    attr: {
                        queryForm: "#storage-form-query-saleoutPage",
                        type: 'exportUserdefined',
                        name: '发货单列表',
                        url: 'storage/exportSaleOutList.do'
                    }
                },
                </security:authorize>
                {
                    type: 'button-commonquery',
                    attr: {
                        //查询针对的表
                        name: 't_storage_saleout',
                        //查询针对的表格id
                        datagrid: 'storage-datagrid-saleoutPage',
                        plain: true
                    }
                },
                {}
            ],
            buttons: [
                <security:authorize url="/storage/exportSaleOutDetailList.do">
                {
                    id: 'button-detailexport',
                    name: '明细导出',
                    iconCls: 'button-export',
                    handler: function () {
                        $("#storage-btn-exportSaleoutDetailData").Excel('export', {
                            queryForm: "#storage-form-query-saleoutPage",
                            type: 'exportUserdefined',
                            name: '发货单列表',
                            url: 'storage/exportSaleOutDetailList.do'
                        });
                        $("#storage-btn-exportSaleoutDetailData").trigger("click");
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/auditMultiSaleout.do">
                {
                    id: 'button-mulitAudit',
                    name: '批量审核',
                    iconCls: 'button-audit',
                    handler: function () {
                        var fn = function (r) {
                            if (r) {
                                var rows = $("#storage-datagrid-saleoutPage").datagrid('getChecked');
                                if (rows.length == 0) {
                                    $.messager.alert("提醒", "请先选择订单！");
                                    return false;
                                }
                                var ids = "", unids = "";
                                for (var i = 0; i < rows.length; i++) {
                                    if (rows[i].isbigsaleout == "0") {
                                        ids += rows[i].id + ',';
                                    } else if (rows[i].isbigsaleout == "1") {
                                        if (unids == "") {
                                            unids = rows[i].id;
                                        } else {
                                            unids += "," + rows[i].id;
                                        }
                                    }
                                }
                                var storager = $("#storage-hidden-storager").widget("getValue");
                                loading("审核中..");
                                $.ajax({
                                    url: 'storage/auditMultiSaleout.do?ids=' + ids + '&storager=' + storager,
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (json) {
                                        //表单提交完成后 隐藏提交等待页面
                                        loaded();
                                        var msg = "";
                                        if (unids != "") {
                                            msg = "发货单编号：" + unids + "已生成大单发货单，不允许审核；"
                                        }
                                        if (json.flag) {
                                            if (msg == "") {
                                                msg = "审核成功：" + json.success + "&nbsp;审核失败：" + json.failure + "</br>" + json.msg;
                                            } else {
                                                msg += "<br>" + "审核成功：" + json.success + "&nbsp;审核失败：" + json.failure + "</br>" + json.msg;
                                            }
                                        } else {
                                            if (msg == "") {
                                                msg = "审核出错";
                                            } else {
                                                msg += "<br>" + "审核出错";
                                            }
                                        }
                                        $.messager.alert("提醒", msg);
                                        $('#storage-dialog-saleOutListPage-content').dialog("destroy");
                                        $("#storage-datagrid-saleoutPage").datagrid('reload');
                                    },
                                    error: function () {
                                        loaded();
                                        $.messager.alert("错误", "审核失败");
                                        $('#storage-dialog-saleOutListPage-content').dialog("destroy");
                                        $("#storage-datagrid-saleoutPage").datagrid('reload');
                                    }
                                });
                            }
                        };
                        $.messager.confirm({
                            title: '批量审核发货单',
                            onOpen: function () {
                                confirmStorageWidget()
                            },
                            msg: "是否审核所选发货单？</br><span style=\"float: left;\">选择发货人:</span><input  id=\"storage-hidden-storager\" name=\"storager\"/>",
                            fn: fn
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/editDispatchUser.do">
                {
                    id: 'button-editDispatchUser',
                    name: '批量修改发货人',
                    iconCls: 'button-edit',
                    handler: function () {
                        var fn = function (r) {
                            if (r) {
                                var rows = $("#storage-datagrid-saleoutPage").datagrid('getChecked');
                                if (rows.length == 0) {
                                    $.messager.alert("提醒", "请先选择订单！");
                                    return false;
                                }
                                var ids = "";
                                for (var i = 0; i < rows.length; i++) {
                                    ids += rows[i].id + ',';
                                }
                                var storager = $("#storage-hidden-storager").widget("getValue");
                                loading("修改..");
                                $.ajax({
                                    data: {ids: ids, storager: storager},
                                    url: 'storage/editDispatchUser.do',
                                    type: 'post',
                                    dataType: 'json',
                                    success: function (json) {
                                        //表单提交完成后 隐藏提交等待页面
                                        loaded();
                                        $.messager.alert("提醒", json.msg);
                                        $('#storage-dialog-saleOutListPage-content').dialog("destroy");
                                        $("#storage-datagrid-saleoutPage").datagrid('reload');
                                    },
                                    error: function () {
                                        loaded();
                                        $.messager.alert("错误", "修改失败");
                                        $('#storage-dialog-saleOutListPage-content').dialog("destroy");
                                        $("#storage-datagrid-saleoutPage").datagrid('reload');
                                    }
                                });
                            }
                        };
                        $.messager.confirm({
                            title: '批量修改发货人',
                            onOpen: function () {
                                confirmStorageWidget()
                            },
                            msg: "是否修改发货人？</br><span style=\"float: left;\">选择发货人:</span><input  id=\"storage-hidden-storager\" name=\"storager\"/>",
                            fn: fn
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/clearSaleoutCheckuser.do">
                {
                    id: 'button-clear',
                    name: '清除核对人',
                    iconCls: 'button-delete',
                    handler: function () {
                        var rows = $("#storage-datagrid-saleoutPage").datagrid('getChecked');
                        if (rows.length == 0) {
                            $.messager.alert("提醒", "请选择发货单。");
                            return false;
                        }
                        $.messager.confirm("提醒", "确定清除这些发货单的核对人，清除核对人后，扫描枪中其他人员也能同步这些发货单？", function (r) {
                            if (r) {
                                var ids = "";
                                for (var i = 0; i < rows.length; i++) {
                                    ids += rows[i].id + ',';
                                }
                                loading("审核中..");
                                $.ajax({
                                    url: 'storage/clearSaleoutCheckuser.do',
                                    dataType: 'json',
                                    type: 'post',
                                    data: {ids: ids},
                                    success: function (json) {
                                        loaded();
                                        if (json.flag == true) {
                                            $.messager.alert("提醒", "清除成功：" + json.success + "&nbsp清除失败：" + json.failure + "<br/>" + json.msg);
                                            $("#storage-datagrid-saleoutPage").datagrid('reload');
                                        }
                                        else {
                                            $.messager.alert("提醒", "清除出错");
                                        }
                                    },
                                    error: function () {
                                        loaded();
                                        $.messager.alert("错误", "清除出错");
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/storageOrderblankPrintView.do">
                {
                    id: 'button-printview-orderblank',
                    name: '配货单打印预览',
                    iconCls: 'button-preview',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/storageOrderblankPrint.do">
                {
                    id: 'button-print-orderblank',
                    name: '配货单打印',
                    iconCls: 'button-print',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/storageDeliveryOrderPrintView.do">
                {
                    id: 'button-printview-DeliveryOrder',
                    name: '发货单打印预览',
                    iconCls: 'button-preview',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/storage/storageDeliveryOrderPrint.do">
                {
                    id: 'button-print-DeliveryOrder',
                    name: '发货单打印',
                    iconCls: 'button-print',
                    handler: function () {
                    }
                },
                </security:authorize>
                <security:authorize url="/erpconnect/addSalesOutVoucher.do">
                {
                    id: 'button-voucher',
                    name: '生成未税凭证',
                    iconCls: 'button-audit',
                    handler: function () {
                        var rows = $("#storage-datagrid-saleoutPage").datagrid('getChecked');
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
                        $("#storage-addVoucher-dialog").dialog({
                            title: '发货单凭证',
                            width: 400,
                            height: 260,
                            closed: false,
                            modal: true,
                            cache: false,
                            href: 'erpconnect/showSalesOutVoucherPage.do',
                            onLoad: function () {
                                $("#salesOutVoucher-ids").val(ids);
                            }
                        });
                    }
                },
                </security:authorize>
                {}
            ],
            model: 'bill',
            type: 'list',
            tname: 't_sales_order'
        });

        var saleoutJson = $("#storage-datagrid-saleoutPage").createGridColumnLoad({
            name: 'storage_saleout',
            frozenCol: [[
                {field: 'idok', checkbox: true, isShow: true}
            ]],
            commonCol: [[
                {field: 'id', title: '编号', width: 130, sortable: true},
                {field: 'saleorderid', title: '销售订单编号', width: 130, sortable: true},
                {field: 'businessdate', title: '业务日期', width: 80, sortable: true},
                {
                    field: 'storageid', title: '出库仓库', width: 80, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.storagename;
                    }
                },
                {field: 'customerid', title: '客户编码', width: 60, sortable: true},
                {field: 'customername', title: '客户名称', width: 150, isShow: true},
                {
                    field: 'salesdept', title: '销售部门', width: 80, sortable: true, hidden: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.salesdeptname;
                    }
                },
                {
                    field: 'salesuser', title: '客户业务员', width: 70, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return rowData.salesusername;
                    }
                },
                {
                    field: 'sendamount', title: '发货出库金额', width: 80, align: 'right',
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'sendnotaxamount', title: '发货出库未税金额', width: 80, align: 'right', hidden: true,
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'sourcetype', title: '来源类型', width: 90, sortable: true, hidden: true,
                    formatter: function (value, rowData, rowIndex) {
                        return getSysCodeName("saleout-sourcetype", value);
                    }
                },
                {field: 'sourceid', title: '来源编号', width: 80, sortable: true},
                {
                    field: 'isinvoicebill', title: '开票状态', width: 60, sortable: true,
                    formatter: function (value, row, index) {
                        if (value == "0") {
                            return "未开票";
                        } else if (value == "1") {
                            return "已开票";
                        } else if (value == "4") {
                            return "开票中";
                        }
                    }
                },
                {
                    field: 'status', title: '状态', width: 60, sortable: true,
                    formatter: function (value, rowData, rowIndex) {
                        return getSysCodeName("status", value);
                    }
                },
                {
                    field: 'indooruserid', title: '销售内勤', width: 60, sortable: true,
                    formatter: function (value, rowData, index) {
                        return rowData.indoorusername;
                    }
                },
                {field: 'duefromdate', title: '应收日期', width: 80, hidden: true, sortable: true},
                {field: 'isdelivery', title: '是否配送', width: 80, hidden: true, sortable: true,
                    formatter: function (value, rowData, index) {
                        if(value==0){
                            return "未配货";
                        }else if(value==1){
                            return "配货中";
                        }else if(value==2){
                            return "已配货";
                        }else if(value==3){
                            return "配货完毕";
                        }
                    }
                },
                {field: 'vouchertimes', title: '生成凭证次数', align: 'center', width:80},
                {field: 'auditusername', title: '审核人', width: 60, sortable: true},
                {field: 'audittime', title: '审核时间', width: 120, sortable: true},
                {field: 'stopusername', title: '中止人', width: 80, hidden: true, sortable: true},
                {field: 'stoptime', title: '中止时间', width: 80, hidden: true, sortable: true},
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
                    field: 'printtimes', title: '打印次数', width: 80,
                    formatter: function (value, rowData, index) {
                        if (value == -99) {
                            return "";
                        } else {
                            return value;
                        }
                    }
                },
                {field: 'printdatetime', title: '打印时间', width: 130},
                {field: 'phprinttimes', title: '配货打印次数', width: 80, hidden: true},
                {field: 'phprintdatetime', title: '配货打印时间', width: 130,hidden:true},
                {field: 'remark', title: '备注', width: 80, sortable: true},
                {field: 'addusername', title: '制单人', width: 60, sortable: true},
                {field: 'addtime', title: '制单时间', width: 120, sortable: true},
                {
                    field: 'ischeck', title: '是否核对', width: 60, hidden: true, sortable: true,
                    formatter: function (value, rowData, index) {
                        if (value == '0') {
                            return "未核对";
                        } else if (value == '1') {
                            return "已核对";
                        }
                    }
                },
                {field: 'checkusername', title: '核对人', width: 60, sortable: true},
                {field: 'checktime', title: '核对时间', width: 120, hidden: true, sortable: true},
                {
                    field: 'isbigsaleout', title: '是否大单发货', width: 60, hidden: true, sortable: true, isShow: true,
                    formatter: function (value, rowData, index) {
                        if (value == '0') {
                            return "否";
                        } else if (value == '1') {
                            return "是";
                        }
                    }
                },
                {field: 'storagername', title: '发货人', width: 80, sortable: true, isShow: true},
            ]]
        });
        $("#storage-datagrid-saleoutPage").datagrid({
            authority: saleoutJson,
            frozenColumns: saleoutJson.frozen,
            columns: saleoutJson.common,
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
            url: 'storage/showSaleOutList.do',
            queryParams: initQueryJSON,
            toolbar: '#storage-datagrid-toolbar-saleoutPage',
            onDblClickRow: function (rowIndex, rowData) {
                if ("0" == rowData.isbigsaleout) {
                    top.addOrUpdateTab('storage/showSaleOutEditPage.do?id=' + rowData.id, "发货单查看");
                } else {
                    top.addOrUpdateTab('storage/showSaleOutViewPage.do?id=' + rowData.id, "发货单查看");
                }
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

        $("#storage-query-customerid").customerWidget({
            isdatasql: false
        });
        $("#storage-query-goodsid").goodsWidget({
            singleSelect: true,
            isHiddenUsenum: true
        });
        $("#sales-saleoutPage-storager").widget({
            referwid: 'RL_T_BASE_PERSONNEL_STORAGER',
            width: 160,
            col: 'name',
            singleSelect: true,
        });

        //通用查询组建调用
//			$("#storage-query-advanced-saleout").advancedQuery({
//				//查询针对的表
//		 		name:'t_storage_saleout',
//		 		//查询针对的表格id
//		 		datagrid:'storage-datagrid-saleoutPage',
//		 		plain:true
//			});

        //回车事件
        controlQueryAndResetByKey("storage-queay-saleout", "storage-reload-saleout");

        //查询
        $("#storage-queay-saleout").click(function () {
            $("#storage-query-selectids").val('');
            //把form表单的name序列化成JSON对象
            var queryJSON = $("#storage-form-query-saleoutPage").serializeJSON();
            $("#storage-datagrid-saleoutPage").datagrid("load", queryJSON);
        });
        //重置
        $("#storage-reload-saleout").click(function () {
            $("#storage-query-selectids").val('');
            $("#storage-query-customerid").customerWidget("clear");
            $("#storage-query-goodsid").goodsWidget("clear");
            $("#storage-form-query-saleoutPage").form("reset");
            var queryJSON = $("#storage-form-query-saleoutPage").serializeJSON();
            $("#storage-datagrid-saleoutPage").datagrid("load", queryJSON);
        });
        ////修改按钮。。。。。。
        $("#storage-buttons-saleoutPage").buttonWidget('disableButton', '');
    });


    function countTotalAmount() {
        var rows = $("#storage-datagrid-saleoutPage").datagrid('getChecked');
        var ids="";
        var sendamount = 0;
        var sendnotaxamount = 0;
        for (var i = 0; i < rows.length; i++) {
            sendamount = Number(sendamount) + Number(rows[i].sendamount == undefined ? 0 : rows[i].sendamount);
            sendnotaxamount = Number(sendnotaxamount) + Number(rows[i].sendnotaxamount == undefined ? 0 : rows[i].sendnotaxamount);
            if(ids==''){
                ids=rows[i].id;
            }else{
                ids+=","+rows[i].id;
            }
        }
        $("#storage-query-selectids").val(ids);
        $("#storage-datagrid-saleoutPage").datagrid("reloadFooter", [{
            id: '选中金额',
            sendamount: sendamount,
            sendnotaxamount: sendnotaxamount,
            printtimes: -99
        }]);
    }

    if ($("#sales-status-saleoutPage").val() == "2") {
        $("#sales-status-saleoutPage").trigger("change");
    }
    $("#sales-phprintsign-saleoutPage").change(function () {
        if ($(this).val() == "") {
            $("#sales-phprinttimes-saleoutPage").val("");
        }
    });
    $("#sales-printsign-saleoutPage").change(function () {
        if ($(this).val() == "") {
            $("#sales-printtimes-saleoutPage").val("");
        }
    });
</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        var $grid = $("#storage-datagrid-saleoutPage");

        function onPrintSuccess(option) {
            var dataList = $grid.datagrid("getChecked");
            if (dataList == null || dataList.length == 0) {
                $.messager.alert("提醒", "请选择至少一条记录");
                return false;
            }
            var isblank = false;
            $.each(option.code, function (i, item) {
                //配置打印次数更新
                if (item.codename == "storage_orderblank")
                    isblank = true;
            });
            for (var i = 0; i < dataList.length; i++) {
                if (isblank) {
                    if (dataList[i].phprinttimes && !isNaN(dataList[i].phprinttimes)) {
                        dataList[i].phprinttimes = dataList[i].phprinttimes + 1;
                    } else {
                        dataList[i].phprinttimes = 1;
                    }
                } else {
                    if (dataList[i].printtimes && !isNaN(dataList[i].printtimes)) {
                        dataList[i].printtimes = dataList[i].printtimes + 1;
                    } else {
                        dataList[i].printtimes = 1;
                    }
                }
                var rowIndex = $grid.datagrid("getRowIndex", dataList[i].id);
                $grid.datagrid('updateRow', {index: rowIndex, row: dataList[i]});
            }
        }

        //配货单打印
        AgReportPrint.init({
            id: "orderblank-dialog-print",
            code: "storage_orderblank",
            url_preview: "print/storage/storageOrderblankPrintView.do",
            url_print: "print/storage/storageOrderblankPrint.do",
            btnPreview: "button-printview-orderblank",
            btnPrint: "button-print-orderblank",
            printlimit: "${printlimit}",
            getData: function (tableId, printParam) {
                var data = $grid.datagrid('getChecked');
                if (data == null || data.length == 0) {
                    $.messager.alert("提醒", "请选择至少一条记录");
                    return false;
                }
                var idarray = [];
                var printIds = [];
                for (var i = 0; i < data.length; i++) {
                    idarray.push(data[i].id);
                    if (data[i].phprinttimes > 0) {
                        printIds.push(data[i].id);
                    }
                }
                printParam.idarrs = idarray.join(",");
                printParam.printIds = printIds;
                return true;
            },
            onPrintSuccess: onPrintSuccess
        });
        //库位套打
        AgReportPrint.init({
            id: "deliveryorder-dialog-print",
            code: "storage_deliveryorder",
            url_preview: "print/storage/storageDeliveryOrderPrintView.do",
            url_print: "print/storage/storageDeliveryOrderPrint.do",
            btnPreview: "button-printview-DeliveryOrder",
            btnPrint: "button-print-DeliveryOrder",
            printlimit: "${printlimit}",
            getData: function (tableId, printParam) {
                var data = $grid.datagrid('getChecked');
                if (data == null || data.length == 0) {
                    $.messager.alert("提醒", "请选择至少一条记录");
                    return false;
                }
                var idarray = [];
                var printIds = [];
                var printlimit = "${printlimit}";
                var fHPrintAfterSaleOutAudit = "${fHPrintAfterSaleOutAudit}";
                for (var i = 0; i < data.length; i++) {
                    if (fHPrintAfterSaleOutAudit == "1") {
                        if (data[i].status != '3' && data[i].status != '4') {
                            $.messager.alert("提醒", "抱歉，审核通过或关闭状态，才能打印发货单。不可打印单据编号：" + data[i].id);
                            return false;
                        }
                    }
                    idarray.push(data[i].id);
                    if (data[i].printtimes > 0) {
                        printIds.push(data[i].id);
                    }
                }
                printParam.idarrs = idarray.join(",");
                printParam.printIds = printIds;
                return true;
            },
            onPrintSuccess: onPrintSuccess
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
