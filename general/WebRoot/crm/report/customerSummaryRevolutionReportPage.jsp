<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%@include file="/include.jsp" %>
    <title>客户库存周转天数报表</title>
</head>
<body>
<table id="crm-datagrid-customerSummaryRevolutionReportPage"></table>
<div id="crm-toolbar-customerSummaryRevolutionReportPage" style="padding:0px;height:90px">
    <div class="buttonBG">
        <security:authorize url="/crm/report/exportCustomerSummaryRevolutionReport.do">
            <a href="javaScript:void(0);" id="crm-buttons-customerSummaryRevolutionReportPagePage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
        </security:authorize>
    </div>
    <form action="" id="crm-query-form-customerSummaryRevolutionReportPage" method="post">
        <table class="querytable">
            <tr>
            <tr>
                <td>商&nbsp;品:</td>
                <td colspan="2"><input type="text" id="crm-query-goodsid" name="goodsid" style="width: 215px;"/></td>
                <td>客&nbsp;户:</td>
                <td>
                    <input type="text" id="crm-query-customerid" style="width: 170px;" name="customerid"/>
                </td>
                <td>周转天数:</td>
                <td>
                    <div style="float:left"><input type="text" class="easyui-numberbox" name="days1" style="width:50px;"/></div>
                    <div style="float:left;width:25px;text-align: center">--</div>
                    <div style="float:left"><input type="text" class="easyui-numberbox" name="days2" style="width:50px;"/></div>
                </td>
            </tr>
            <tr>
                <td>日&nbsp;期:</td>
                <td colspan="2">
                    <input type="text" name="businessdate" style="width:100px;" class="Wdate" readonly value="${preMonthLastDay}" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${preMonthLastDay }',isShowClear:false})" />
                    到<input type="text" name="businessdate1" class="Wdate" style="width:100px;" readonly value="${preMonthLastDay}" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${preMonthLastDay }',isShowClear:false})" />
                </td>
                <td>供 应 商:</td>
                <td>
                    <input type="text" id="crm-query-supplierid" style="width: 170px;" name="supplierid"/>
                </td>
                <td>品牌名称:</td>
                <td><input type="text" id="crm-query-brandid" name="brandid" style="width:125px;"/></td>
                <td colspan="2">
                    <a href="javaScript:void(0);" id="crm-queay-customerSummaryRevolutionReportPage" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="crm-reload-customerSummaryRevolutionReportPage" class="button-qr">重置</a>
                </td>
            </tr>
        </table>
        <div id="crm-dialog-salesAmountDetail"></div>
    </form>
</div>
<script type="text/javascript">
    var initQueryJSON = $("#crm-query-form-customerSummaryRevolutionReportPage").serializeJSON();
    var checkListJson = $("#crm-datagrid-customerSummaryRevolutionReportPage").createGridColumnLoad({
        commonCol : [[
            {field:'customerid',title:'客户编码',width:70},
            {field:'customername',title:'客户名称',width:170},
            {field:'goodsid',title:'商品编码',width:80},
            {field:'goodsname',title:'商品名称',width:180},
            {field:'barcode',title:'条形码',sortable:true,width:90},
            {field:'brandid',title:'品牌名称',width:60,sortable:true,
                formatter:function(value,rowData,rowIndex){
                    return rowData.brandname;
                }
            },
//            {field:'brandept',title:'品牌部门',width:70,sortable:true,
//                formatter:function(value,rowData,rowIndex){
//                    return rowData.branddeptname;
//                }
//            },
            {field:'supplierid',title:'供应商编号',width:70,sortable:true},
            {field:'suppliername',title:'供应商名称',width:150},
            {field:'unitnum',title:'库存数量',resizable:true,sortable:true,align:'right',
                formatter:function(value,rowData,rowIndex){
                    return formatterMoney(value);
                }
            },
//            {field:'taxamount',title:'库存金额',resizable:true,sortable:true,align:'right',
//                formatter:function(value,rowData,rowIndex){
//                    return formatterMoney(value);
//                }
//            },
            {field:'salesnum',title:'销售数量',resizable:true,sortable:true,align:'right',
                formatter:function(value,rowData,rowIndex){
                    return formatterMoney(value);
                }
            },
//            {field:'salesamount',title:'销售金额',resizable:true,sortable:true,align:'right',
//                formatter:function(value,rowData,rowIndex){
//                    return formatterMoney(value);
//                }
//            },
            {field:'days',title:'周转天数',width:60,align:'right',sortable:true,
                formatter:function(value,rowData,rowIndex){
                    return formatterMoney(value);
                }
            },
            {field:'salestype',title:'销售属性',resizable:true,sortable:true,align:'right'}
        ]]
    });

    $(function(){
        $("#crm-datagrid-customerSummaryRevolutionReportPage").datagrid({
            authority:checkListJson,
            frozenColumns: checkListJson.frozen,
            columns:checkListJson.common,
            method:'post',
            title:'',
            fit:true,
            rownumbers:true,
            pagination:true,
            showFooter: true,
            pageSize:100,
            singleSelect:true,
            toolbar:'#crm-toolbar-customerSummaryRevolutionReportPage'
        }).datagrid("columnMoving");
        $("#crm-query-goodsid").goodsWidget();
        $("#crm-query-brandid").widget({
            referwid:'RL_T_BASE_GOODS_BRAND',
            width:125,
            singleSelect:true
        });
        $("#crm-query-supplierid").supplierWidget();
        $("#crm-query-customerid").customerWidget();
        //回车事件
        controlQueryAndResetByKey("crm-queay-customerSummaryRevolutionReportPage","crm-reload-customerSummaryRevolutionReportPage");

        //查询
        $("#crm-queay-customerSummaryRevolutionReportPage").click(function(){
            //把form表单的name序列化成JSON对象
            var queryJSON = $("#crm-query-form-customerSummaryRevolutionReportPage").serializeJSON();
            $("#crm-datagrid-customerSummaryRevolutionReportPage").datagrid({
                url: 'crm/dataport/getCustomerSummaryRevolutionReportData.do',
                pageNumber:1,
                queryParams:queryJSON
            });
        });
        //重置
        $("#crm-reload-customerSummaryRevolutionReportPage").click(function(){
            $("#crm-query-goodsid").goodsWidget("clear");
            $("#crm-query-brandid").widget("clear");
            $("#crm-query-supplierid").supplierWidget("clear");
            $("#crm-query-form-customerSummaryRevolutionReportPage").form("reset");
            var queryJSON = $("#crm-query-form-customerSummaryRevolutionReportPage").serializeJSON();
            $("#crm-datagrid-customerSummaryRevolutionReportPage").datagrid('loadData',{total:0,rows:[]});
        });
        //全局导出
        $("#crm-buttons-customerSummaryRevolutionReportPagePage").click(function(){
            //封装查询条件
            var objecr  = $("#crm-datagrid-customerSummaryRevolutionReportPage").datagrid("options");
            var queryParam = objecr.queryParams;
            if(null != objecr.sortName && null != objecr.sortOrder ){
                queryParam["sort"] = objecr.sortName;
                queryParam["order"] = objecr.sortOrder;
            }
            var queryParam = JSON.stringify(queryParam);
            var url = "crm/dataport/exportCustomerSummaryRevolution.do";
            exportByAnalyse(queryParam,"客户库存周转天数报表","crm-datagrid-customerSummaryRevolutionReportPage",url);
        });

    });
</script>
</body>

</html>
