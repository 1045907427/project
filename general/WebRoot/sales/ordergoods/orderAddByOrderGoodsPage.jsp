<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>销售订单新增</title>
    <%@include file="/include.jsp" %>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false">
        <div class="buttonBG">
            <a href="javaScript:void(0);" id="sales-add-orderAdd" class="easyui-linkbutton" plain="true" iconCls="button-add" >生成订单</a>
        </div>
    </div>
    <div data-options="region:'center'">
        <table id="sales-datagrid-orderAddByOrderGoodsPage"></table>
        <div id="sales-toolbar-query-orderAddByOrderGoodsPage" style="padding:2px;height:auto">
            <form id="sales-form-query-orderAddByOrderGoodsPage">
                <table class="querytable">
                    <tr>
                        <td>业务日期：</td>
                        <td>
                            <input type="text" name="businessdate" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /> 到 <input type="text" name="businessdate1" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" />
                        </td>
                        <td>销售部门：</td>
                        <td>
                            <input type="text" id="sales-salesdept-orderAddByOrderGoodsPage" name="salesdept" style="width: 150px;"/>
                        </td>
                        <td>订货单编号:</td>
                        <td><input type="text" name="orderid" style="width: 150px" /></td>
                        <%--<td>总店名称：</td>--%>
                        <%--<td>--%>
                            <%--<input type="text" id="sales-pcustomerid-orderAddByOrderGoodsPage" name="pcustomerid" />--%>
                        <%--</td>--%>
                    </tr>
                    <tr>
                        <td>客户名称：</td>
                        <td>
                            <input type="text" id="sales-customerid-orderAddByOrderGoodsPage" name="customerid" style="width: 225px"/>
                        </td>
                        <td>客户业务员：</td>
                        <td>
                            <input type="text" id="sales-salesuser-orderAddByOrderGoodsPage" name="salesuser" style="width: 150px"/>
                        </td>

                        <td colspan="2" style="padding-left: 10px">
                            <!--  					<input type="hidden" id="sales-selectid-orderAddByOrderGoodsPage" name="selectid"/>-->
                            <a href="javaScript:void(0);" id="sales-queay-salesInvoiceAdd" class="button-qr">查询</a>
                            <a href="javaScript:void(0);" id="sales-reload-salesInvoiceAdd" class="button-qr">重置</a>

                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <div id="sales-panel-orderAddDetailPage"></div>
    </div>
</div>
<input id="sales-nochecked-detail-salesinvoiceDetailPage" type="hidden"/>
<input id="sales-checkedbill-invoiceamount-salesinvoiceDetailPage" type="hidden"/>
<input id="sales-checkedbill-uncheckamount-salesinvoiceDetailPage" type="hidden"/>
<script type="text/javascript">
    function isLockData(id, tname) {
        var flag = false;
        $.ajax({
            url: 'system/lock/unLockData.do',
            type: 'post',
            data: {id: id, tname: tname},
            dataType: 'json',
            async: false,
            success: function (json) {
                flag = json.flag
            }
        });
        return flag;
    }
    var totalFooter = {};
    var initQueryJSON = $("#sales-form-query-orderAddByOrderGoodsPage").serializeJSON();
    $(function(){
        var orderJson = $("#sales-datagrid-orderAddByOrderGoodsPage").createGridColumnLoad({
            frozenCol : [[]],
            commonCol : [[
                {field:'ck', checkbox:true},
                {field: 'id', title: '编号', width: 120, align: 'left', sortable: true},
                {field: 'businessdate', title: '业务日期', width: 80, align: 'left', sortable: true},
                {field: 'customerid', title: '客户编码', width: 60, align: 'left', sortable: true},
                {field: 'customername', title: '客户名称', width: 150, align: 'left', isShow: true},
                {field: 'handlerid', title: '对方经手人', width: 80, align: 'left'},
                {field: 'salesdept', title: '销售部门', width: 100, align: 'left', sortable: true},
                {field: 'salesuser', title: '客户业务员', width: 80, align: 'left', sortable: true},
                {
                    field: 'field01', title: '金额', width: 80, align: 'right',
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
                    field: 'ordertaxamount', title: '已生成订单金额', width: 80, align: 'right',
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'ordernotaxamount', title: '已生成订单未税金额', width: 80, align: 'right', hidden: true,
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'notordertaxamount', title: '未生成订单金额', width: 80, align: 'right',
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },
                {
                    field: 'notordernotaxamount', title: '未生成订单未税金额', width: 80, align: 'right', hidden: true,
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                }

            ]]
        });
        $("#sales-datagrid-orderAddByOrderGoodsPage").datagrid({
            authority:orderJson,
            frozenColumns: orderJson.frozen,
            columns:orderJson.common,
            fit:true,
            method:'post',
            rownumbers:true,
            pagination:true,
            idField:'id',
            singleSelect:true,
            selectOnCheck:true,
            checkOnSelect:true,
            pageSize:1000,
            pageList:[20,100,200,500,1000],
            showFooter:true,
            toolbar:'#sales-toolbar-query-orderAddByOrderGoodsPage',
            url: 'sales/getOrderGoodsListForAddOrder.do',
            queryParams: initQueryJSON,
            onLoadSuccess:function(){
                var footerrows = $(this).datagrid('getFooterRows');
                if(null!=footerrows && footerrows.length>0){
                    if(footerrows[0]!=null &&footerrows[0].id=="合计"){
                        totalFooter = footerrows[0];
                    }
                }
            }
        }).datagrid("columnMoving");
        $("#sales-customerid-orderAddByOrderGoodsPage").customerWidget({
            referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT_2',
            singleSelect:true,
            width:225,
            isall: true
        });

        $("#sales-salesdept-orderAddByOrderGoodsPage").widget({
            referwid:'RL_T_BASE_DEPARTMENT_SELLER',
            singleSelect:false,
            width:150,
            onlyLeafCheck:true
        });
        $("#sales-salesuser-orderAddByOrderGoodsPage").widget({
            referwid:'RL_T_BASE_PERSONNEL_SELLER',
            singleSelect:false,
            width:150,
            onlyLeafCheck:true
        });
        $("#sales-queay-salesInvoiceAdd").click(function(){
            $("#sales-datagrid-orderAddByOrderGoodsPage").datagrid('loadData',{total:0,rows:[],footer:[totalFooter]});
            $("#sales-datagrid-orderAddByOrderGoodsPage").datagrid('clearChecked');
            $("sales-datagrid-orderAddByOrderGoodsPage").datagrid('clearSelections');
            var queryJSON = $("#sales-form-query-orderAddByOrderGoodsPage").serializeJSON();
            $("#sales-datagrid-orderAddByOrderGoodsPage").datagrid({
                url: 'sales/getOrderGoodsListForAddOrder.do',
                pageNumber:1,
                queryParams:queryJSON
            }).datagrid("columnMoving");
        });
       
        $("#sales-customerid-salesInvoiceDetailSourceQueryPage").widget({
            referwid:'RL_T_BASE_SALES_CUSTOMER',
            singleSelect:true,
            width:150,
            onlyLeafCheck:true
        });
        $("#sales-pcustomerid-salesInvoiceDetailSourceQueryPage").widget({
            referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
            singleSelect:true,
            width:150,
            onlyLeafCheck:true
        });
        $("#sales-add-orderAdd").click(function(){
            showSalesInvoiceDetail();
        });
        $("#sales-reload-salesInvoiceAdd").click(function(){

            $("#sales-datagrid-orderAddByOrderGoodsPage").datagrid('loadData',{total:0,rows:[],footer:[]});
            $("#sales-datagrid-orderAddByOrderGoodsPage").datagrid('clearChecked');
            $("#sales-datagrid-orderAddByOrderGoodsPage").datagrid('clearSelections');
            $("#sales-nochecked-detail-salesinvoiceDetailPage").val("");
            $("input[name = id]").val("");
            $("input[name = saleorderid]").val("");
            $("#sales-sourcetype-orderAddByOrderGoodsPage").val("");
            $("#sales-checkedbill-invoiceamount-salesinvoiceDetailPage").val("");
            $("#sales-checkedbill-uncheckamount-salesinvoiceDetailPage").val("");
            $("#sales-customerid-orderAddByOrderGoodsPage").customerWidget("clear");

            $("#sales-salesuser-orderAddByOrderGoodsPage").widget('clear');
            $("#sales-salesdept-orderAddByOrderGoodsPage").widget('clear');
            $("#sales-form-query-orderAddByOrderGoodsPage")[0].reset();
        });
    });

    function showSalesInvoiceDetail(){
        var rowArr = $("#sales-datagrid-orderAddByOrderGoodsPage").datagrid("getSelected");
        if(rowArr==null){
            $.messager.alert("提醒","请选择数据");
            return false;
        }
        var flag = isLockData(rowArr.id, "t_sales_goodsorder");
        if (!flag) {
            $.messager.alert("警告", "该数据正在被其他人操作，暂不能修改！");
            return false;
        }

        $('<div id="sales-panel-orderAddDetailPage1"></div>').appendTo('#sales-panel-orderAddDetailPage');
        $("#sales-panel-orderAddDetailPage1").dialog({
            title: '生成销售订单',
            maximizable: true,
            width: 1000,
            height: 650,
            closed: false,
            modal: true,
            cache: false,
            resizable: true,
            href: 'sales/showOrderGoodsToOrderDataPage.do?id='+rowArr.id,
            buttons: [
                {
                    text: '生成销售订单',
                    handler: function () {
                        addOrder();
                    }
                }
            ],
            onClose: function () {
                $("#sales-panel-orderAddDetailPage1").dialog('destroy');
            }
        });


    }
</script>
</body>
</html>
