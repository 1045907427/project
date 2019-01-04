<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<jsp:useBean id="today" class="java.util.Date"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>销售对账单</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
    <style type="text/css">
        .len193 {
            width: 193px;
        }
    </style>
</head>

<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center'" style="">
        <div id="report-div-salesBillStatementPage" style="padding:0px;">
            <div id="report-buttons-salesBillStatementPage" class="buttonBG">
                <security:authorize url="/report/sales/exportSalesBillStatementData.do">
                    <a href="javascript:void(0);" id="report-export-salesBillStatementPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
                </security:authorize>
                <security:authorize url="/report/sales/printviewSalesBillStatementData.do">
                    <%--<a href="javascript:void(0);" id="report-printview-salesBillStatementPage" class="easyui-linkbutton" iconCls="button-print" plain="true" title="打印">打印预览</a>--%>
                </security:authorize>
                <security:authorize url="/report/sales/printSalesBillStatementData.do">
                    <%--<a href="javascript:void(0);" id="report-print-salesBillStatementPage" class="easyui-linkbutton" iconCls="button-print" plain="true" title="打印">打印</a>--%>
                </security:authorize>
            </div>
            <form action="" id="report-form-salesBillStatementPage" method="post">
                <table cellpadding="2" style="margin-left:5px;margin-top: 5px;">
                    <tr>
                        <td>业务日期：</td>
                        <td><input type="text" name="begindate" value="<fmt:formatDate value="${today }" pattern="yyyy-MM-01" type="date" dateStyle="long" />" style="width:87px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /> 到 <input type="text" name="enddate" class="Wdate" style="width:87px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
                        <td>客　　户：</td>
                        <td><input type="text" class="len193" name="customerid" id="report-customerid-salesBillStatementPage"/></td>
                        <td>总　　店：</td>
                        <td><input type="text" class="len150" name="pcustomerid" id="report-pcustomerid-salesBillStatementPage"/></td>
                    </tr>
                    <tr>
                        <td>销售区域：</td>
                        <td><input type="text" class="len193" name="salesarea" id="report-salesarea-salesBillStatementPage"/></td>
                        <td>客户分类：</td>
                        <td><input type="text" class="len193" name="customersort" id="report-customersort-salesBillStatementPage"/></td>
                        <td>客户业务员：</td>
                        <td><input type="text" class="len150" name="salesuser" id="report-salesuser-salesBillStatementPage"/></td>
                    </tr>
                    <tr>
                        <td>销售部门：</td>
                        <td><input type="text" class="len193" name="salesdept" id="report-salesdept-salesBillStatementPage"/></td>
                        <td></td>
                        <td></td>
                        <td colspan="2" class="right">
                            <a href="javascript:void(0);" id="report-queay-salesBillStatementPage" class="button-qr">查询</a>
                            <a href="javaScript:void(0);" id="report-reset-salesBillStatementPage" class="button-qr">重置</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <table id="report-datagrid-salesBillStatementPage"></table>
    </div>
</div>
<script type="text/javascript">

    var queryJSON = {};

    $(function(){

        $('#report-export-salesBillStatementPage').click(function(){
            var queryJSON = $('#report-form-salesBillStatementPage').serializeJSON();
            var queryParam = JSON.stringify(queryJSON);
            var url = 'report/sales/exportSalesBillStatementData.do';
            exportByAnalyse(queryParam, '客户对账单汇总报表', 'report-datagrid-salesBillStatementPage', url);
        });

        // 查询
        $('#report-queay-salesBillStatementPage').click(function(){
            queryJSON = $('#report-form-salesBillStatementPage').serializeJSON();
            $('#report-datagrid-salesBillStatementPage').datagrid('load', queryJSON);
        });

        var tableColumnListJson = $('#report-datagrid-salesBillStatementPage').createGridColumnLoad({
            frozenCol : [[
                {field:'idok',checkbox:true,isShow:true}
            ]],
            commonCol : [[
                {field:'customerid',title:'客户编号',sortable:true,width:60,sortable:true},
                {field:'customername',title:'客户名称',width:210},
                {field:'pcustomerid',title:'总店编码',sortable:true,width:80,hidden:false},
                {field:'pcustomername',title:'总店名称',width:100,hidden:false},
                {field:'salesdept',title:'销售部门',sortable:true,width:80,hidden:false,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.salesdeptname;
                    }
                },
                {field:'salesuser',title:'客户业务员',sortable:false,width:70,hidden:false,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.salesusername;
                    }
                },
                {field:'customersort',title:'客户分类',sortable:false,width:60,hidden:false,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.customersortname;
                    }
                },
                {field:'salesarea',title:'销售区域',width:80,hidden:false,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.salesareaname;
                    }
                },
//                {field:'salesbillnum',title:'销售单数',width:80,align:'right'},
                {field:'salesamount',title:'销售金额',width:110,align:'right',sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
//                {field:'rejectbillnum',title:'退货单数',width:80,align:'right'},
                {field:'rejectamount',title:'退货金额',width:110,align:'right',sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'pushbalanceamount',title:'冲差金额',width:110,align:'right',sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                }
            ]]
        });

        // 客户
        $('#report-customerid-salesBillStatementPage').widget({
            referwid:'RL_T_BASE_SALES_CUSTOMER',
            singleSelect:false,
            width:200,
            onlyLeafCheck:true
        });

        // 总店
        $('#report-pcustomerid-salesBillStatementPage').widget({
            referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
            singleSelect:false,
            width:150,
            onlyLeafCheck:true
        });

        //品牌
        $('#report-brandid-salesBillStatementPage').widget({
            referwid:'RL_T_BASE_GOODS_BRAND',
            singleSelect:false,
            width:200,
            onlyLeafCheck:true
        });

        // 商品
        $('#report-goodsid-salesBillStatementPage').widget({
            referwid:'RL_T_BASE_GOODS_INFO',
            singleSelect:false,
            width:200,
            onlyLeafCheck:true
        });

        // 商品分类
        $('#report-goodssort-salesBillStatementPage').widget({
            referwid:'RL_T_BASE_GOODS_WARESCLASS',
            singleSelect:false,
            width:200,
            onlyLeafCheck:false
        });

        // 销售区域
        $('#report-salesarea-salesBillStatementPage').widget({
            referwid:'RT_T_BASE_SALES_AREA',
            singleSelect:false,
            width:193,
            onlyLeafCheck:false
        });

        // 客户分类
        $('#report-customersort-salesBillStatementPage').widget({
            referwid:'RT_T_BASE_SALES_CUSTOMERSORT',
            singleSelect:false,
            width:200,
            onlyLeafCheck:false
        });

        //品牌业务员
        $('#report-branduser-salesBillStatementPage').widget({
            referwid:'RL_T_BASE_PERSONNEL_BRANDSELLER',
            singleSelect:false,
            width:200,
            onlyLeafCheck:true
        });

        // 客户业务员
        $('#report-salesuser-salesBillStatementPage').widget({
            referwid:'RL_T_BASE_PERSONNEL_CLIENTSELLER',
            singleSelect:false,
            width:150,
            onlyLeafCheck:false
        });

        // 销售部门
        $('#report-salesdept-salesBillStatementPage').widget({
            referwid:'RL_T_BASE_DEPARTMENT_SELLER',
            width:193,
            singleSelect:false,
            onlyLeafCheck:true
        });

        //仓库
        $('#report-storageid-salesBillStatementPage').widget({
            referwid:'RL_T_BASE_STORAGE_INFO',
            singleSelect:false,
            width:200,
            onlyLeafCheck:true
        });

        queryJSON = $('#report-form-salesBillStatementPage').serializeJSON();
        $('#report-datagrid-salesBillStatementPage').datagrid({
            authority: tableColumnListJson,
            frozenColumns: tableColumnListJson.frozen,
            columns: tableColumnListJson.common,
            method: 'post',
            fit: true,
            rownumbers: true,
            pagination: true,
            showFooter: true,
            singleSelect: false,
            checkOnSelect: true,
            selectOnCheck: true,
            pageSize: 100,
            url: 'report/sales/getSalesBillStatementPageData.do',
            queryParams: queryJSON,
            toolbar: '#report-div-salesBillStatementPage',
            onDblClickRow: function (index, row) {

                var begindate = queryJSON.begindate || '';
                var enddate = queryJSON.enddate || '';
                var customerid = row.customerid || '';

                top.addOrUpdateTab('report/sales/salesBillStatementDetailPage.do?customerid=' + customerid + '&begindate=' + begindate + '&enddate=' + enddate, '对账单明细');

            }
        }).datagrid('columnMoving');

    });

</script>
<%--打印开始 --%>
<script type="text/javascript">
    $(function () {
        //该页面未知
        AgReportPrint.init({
            id: "bankwrite-dialog-print",
            code: "finance_bankwrite",
            queryForm: "report-form-salesBillStatementPage",
            url_preview: "print/report/financeBankWriteReportPrintView.do",
            url_print: "print/report/financeBankWriteReportPrint.do",
            btnPreview: "report-printview-salesBillStatementPage",
            btnPrint: "report-print-salesBillStatementPage"
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
