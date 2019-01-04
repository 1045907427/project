<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<jsp:useBean id="today" class="java.util.Date"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>销售对账单明细</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
    <style type="text/css">
        .checkbox1{
            float:left;
            height: 22px;
            line-height: 22px;
        }
        .divtext{
            height:22px;
            line-height:22px;
            float:left;
            display: block;
        }
        .len193 {
            width: 193px;
        }
    </style>
</head>

<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center'" style="">
        <div id="report-div-salesBillStatementDetailPage" style="padding:0px;">
            <security:authorize url="/report/sales/exportSalesBillStatementDetailData.do">
                <a href="javascript:void(0);" id="report-export-salesBillStatementDetailPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
            </security:authorize>
            <security:authorize url="/report/sales/printSalesBillStatementDetailData.do">
                <a href="javascript:void(0);" id="report-print-salesBillStatementDetailPage" class="easyui-linkbutton" iconCls="button-print" plain="true" title="打印">打印</a>
            </security:authorize>
            <form action="" id="report-form-salesBillStatementDetailPage" method="post">
                <table cellpadding="2" style="margin-left:5px; margin-top: 5px;">
                    <tr>
                        <td>对账日期：</td>
                        <td><input type="text" name="begindate" value="${param.begindate }" style="width:87px;" class="Wdate" readonly="readonly"/> 到 <input type="text" name="enddate" class="Wdate" style="width:87px;" value="${param.enddate }" readonly="readonly"/></td>
                        <td>客　　户：</td>
                        <td colspan="3"><input type="text" class="len193" name="customerid" id="report-customerid-salesBillStatementDetailPage" value="${param.customerid }" style="width: 383px;"/></td>
                    </tr>
                    <tr>
                        <td>销售金额：</td>
                        <td><input type="text" class="len193 easyui-numberbox" name="salesamount" id="report-salesamount-salesBillStatementDetailPage" readonly="readonly" data-options="precision:2"/></td>
                        <td>退货金额：</td>
                        <td><input type="text" class="len150 easyui-numberbox" name="rejectamount" id="report-rejectamount-salesBillStatementDetailPage" readonly="readonly" data-options="precision:2"/></td>
                        <td>冲差金额：</td>
                        <td><input type="text" class="len150 easyui-numberbox" name="pushbalanceamount" id="report-pushbalanceamount-salesBillStatementDetailPage" readonly="readonly" data-options="precision:2"/></td>
                    </tr>
                    <tr>
                        <td>销售单据数：</td>
                        <td><input type="text" class="len193 easyui-numberbox" name="salesbillamount" id="report-salesbillamount-salesBillStatementDetailPage" readonly="readonly" data-options="precision:0"/></td>
                        <td>退货单据数：</td>
                        <td><input type="text" class="len150 easyui-numberbox" name="rejectbillamount" id="report-rejectbillamount-salesBillStatementDetailPage" readonly="readonly" data-options="precision:0"/></td>
                        <td>冲差单据数：</td>
                        <td><input type="text" class="len150 easyui-numberbox" name="pushbalancebillamount" id="report-pushbalancebillamount-salesBillStatementDetailPage" readonly="readonly" data-options="precision:0"/></td>
                    </tr>
                    <tr>
                        <td>实收金额：</td>
                        <td><input type="text" class="len193 easyui-numberbox" name="receiveamount" id="report-receiveamount-salesBillStatementDetailPage" readonly="readonly" data-options="precision:2"/></td>
                    </tr>
                </table>
            </form>
        </div>
        <table id="report-datagrid-salesBillStatementDetailPage"></table>
    </div>
</div>
<script type="text/javascript">

    var queryJSON = {};

    $(function(){

        $("#report-print-salesBillStatementDetailPage").click(function(){
            var queryJSON = $("#report-form-salesBillStatementDetailPage").serializeJSON();
            var rows = $('#report-datagrid-salesBillStatementDetailPage').datagrid('getChecked');
            var printMethod = function (param) {
                $.AgReportPrint(AgReportPrint.printViewToolType,
                    {
                        webpath:'<%=basePath %>',
                        url:'print/report/printSalesBillStatement.do',
                        urlparam: param
                    }
                );
            };

            if(rows.length == 0) {
                $.messager.confirm('提醒', '确定要打印全部单据？', function (e) {
                    if(e) {
                        printMethod(queryJSON);
                    }
                })
            } else {
                var orderids = new Array();
                var rejectids = new Array();
                var pushbalanceids = new Array();
                rows.map(function (item) {
                    if(item.billtype == 1) {
                        orderids.push(item.billid);
                    } else if(item.billtype == 2) {
                        rejectids.push(item.billid);
                    } else if(item.billtype == 3) {
                        pushbalanceids.push(item.billid);
                    }
                });

                queryJSON.orderids = orderids.join(',');
                queryJSON.rejectids = rejectids.join(',');
                queryJSON.pushbalanceids = pushbalanceids.join(',');

                $.messager.confirm('提醒', '是否要打印选中的单据？', function (e) {
                    if(e) {
                        printMethod(queryJSON);
                    }
                })
            }
            return false;

        });

        $('#report-export-salesBillStatementDetailPage').click(function(){
            var queryJSON = $('#report-form-salesBillStatementDetailPage').serializeJSON();
            var queryParam = JSON.stringify(queryJSON);
            var url = 'report/sales/exportSalesBillStatementDetailData.do';
            exportByAnalyse(queryParam, '客户对账单汇总明细', 'report-datagrid-salesBillStatementDetailPage', url);
        });

        var tableColumnListJson = $('#report-datagrid-salesBillStatementDetailPage').createGridColumnLoad({
            frozenCol : [[
            ]],
            commonCol : [[
                {field:'businessdate',title:'日期',width:90,sortable:true},
                {field:'billtypename',title:'单据类型',width: 90
                },
                {field:'billid',title:'单据编号',width: 160,sortable:false},
                {field:'salesamount',title:'销售金额',width:110,align:'right',sortable:true,
                    formatter:function(value, row, index){
                        return formatterMoney(value);
                    }
                },
                {field:'rejectamount',title:'退货金额',width:110,align:'right',sortable:true,
                    formatter:function(value, row, index){
                        return formatterMoney(value);
                    }
                },
                {field:'pushbalanceamount',title:'冲差金额',width:110,align:'right',sortable:true,
                    formatter:function(value, row, index){
                        return formatterMoney(value);
                    }
                }
            ]]
        });

        // 客户
        $('#report-customerid-salesBillStatementDetailPage').widget({
            referwid: 'RL_T_BASE_SALES_CUSTOMER',
            singleSelect: false,
            width: 383,
            onlyLeafCheck: true,
            readonly: true
        });

        $('#report-datagrid-salesBillStatementDetailPage').datagrid({
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
            sortName: 'businessdate',
            sortOrder: 'asc',
            pageSize: 100,
            url: 'report/sales/getSalesBillStatementDetailPageData.do',
            queryParams: $('#report-form-salesBillStatementDetailPage').serializeJSON(),
            toolbar: '#report-div-salesBillStatementDetailPage',
            onLoadSuccess: function (data) {
                var sumData = data.footer[0];
                $('#report-salesamount-salesBillStatementDetailPage').numberbox('setValue', sumData.salesamount);
                $('#report-rejectamount-salesBillStatementDetailPage').numberbox('setValue', sumData.rejectamount);
                $('#report-pushbalanceamount-salesBillStatementDetailPage').numberbox('setValue', sumData.pushbalanceamount);
                $('#report-salesbillamount-salesBillStatementDetailPage').numberbox('setValue', sumData.salesbillamount);
                $('#report-rejectbillamount-salesBillStatementDetailPage').numberbox('setValue', sumData.rejectbillamount);
                $('#report-pushbalancebillamount-salesBillStatementDetailPage').numberbox('setValue', sumData.pushbalancebillamount);

                var receiveamount = parseFloat(sumData.salesamount) - parseFloat(sumData.rejectamount) + parseFloat(sumData.pushbalanceamount);
                $('#report-receiveamount-salesBillStatementDetailPage').numberbox('setValue', receiveamount);
            }
        }).datagrid('columnMoving');

    });

</script>
</body>
</html>
