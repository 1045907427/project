<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>客户销量明细报表</title>
    <%@include file="/include.jsp"%>
</head>
<body>
<table id="crm-table-terminalSalesOrderReportPage"></table>
<div id="crm-query-terminalSalesOrderReportPage" style="height: 85px;padding: 0px">
    <security:authorize url="/crm/report/exportTerminalSalesOrder.do">
        <a href="javaScript:void(0);" id="crm-export-terminalSalesOrderReportPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
    </security:authorize>
    <security:authorize url="/crm/report/updateTerminalSalesOrder.do">
        <a href="javaScript:void(0);" id="crm-update-terminalSalesOrderReportPage" class="easyui-linkbutton" iconCls="button-edit" plain="true" title="销量更新">销量更新</a>
    </security:authorize>
    <form action="" id="crm-form-terminalSalesOrderReportPage" method="post">
        <table class="querytable">
            <tr>
                <td>业务日期：</td>
                <td class="tdinput"><input type="text" name="businessdate" style="width:95px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${today }" />
                    到 <input type="text" name="businessdate1" style="width:95px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${today }"  />
                </td>
                <td>商&nbsp;&nbsp;品：</td>
                <td class="tdinput"><input type="text" name="goodsid" id="crm-goodsid-terminalSalesOrderReportPage" class="len160" /> </td>
                <%--<td>单据编号：</td>--%>
                <%--<td class="tdinput"><input type="text" name="id" style="width: 160px" /> </td>--%>
            </tr>
            <tr>
                <td>客&nbsp;&nbsp;户：</td>
                <td class="tdinput"><input type="text" id="crm-customer-terminalSalesOrderReportPage" style="width: 210px" name="customerid" /></td>
                <td>供应商：</td>
                <td class="tdinput"><input type="text" name="supplierid" id="crm-supplierid-terminalSalesOrderReportPage" class="len160" /> </td>
                <%--<td>销售部门：</td>--%>
                <%--<td class="tdinput">--%>
                    <%--<input type="text" id="crm-salesDept-terminalSalesOrderReportPage" name="salesdept" />--%>
                <%--</td>--%>
                <td colspan="2" class="tdbutton" style="padding-left: 5px">
                    <a href="javascript:;" id="crm-queay-terminalSalesOrderReportPage" class="button-qr">查询</a>
                    <a href="javaScript:;" id="crm-resetQueay-terminalSalesOrderReportPage" class="button-qr">重置</a>
                </td>
            </tr>

        </table>
    </form>

</div>
<div id="crm-importModel-dialog" ></div>
<div id="crm-updateSales-dialog"></div>
<script type="text/javascript">
    var initQueryJSON = $("#crm-form-terminalSalesOrderReportPage").serializeJSON();
    $(function(){
        //表头标题
        var crmTerminalJson = $("#crm-table-terminalSalesOrderReportPage").createGridColumnLoad({
            name :'t_crm_sales_order',
            frozenCol : [[{field:'ck',checkbox:true}]],
            commonCol : [[
                {field:'id',title:'编号',width:150, align: 'left',hidden:true},
                {field:'businessdate',title:'业务日期',width:80,align:'left',sortable:true},
                {field:'customerid',title:'客户编码',width:60,align:'left',sortable:true},
                {field:'customername',title:'客户名称',width:150,align:'left',isShow:true},
                {field:'supplierid',title:'供应商名称',sortable:true,width:200,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.suppliername;
                    }
                },
                {field:'handlerid',title:'对方经手人',width:80,align:'left',hidden:true},
                {field:'salesdeptname',title:'销售部门',width:100,align:'left',sortable:true},
                {field:'salesuser',title:'客户业务员',width:80,align:'left',sortable:true},
                {field:'goodsid',title:'商品编码',sortable:true,width:100},
                {field:'goodsname',title:'商品名称',width:200},
                {field:'spell',title:'助记符',width:80,hidden:true},
                {field:'barcode',title:'条形码',width:120,sortable:true,hidden:true},
                {field:'boxnum',title:'箱装量',width:60,
                    formatter:function(value,rowData,rowIndex){
                        return formatterBigNumNoLen(value);
                    }
                },
                {field:'brandid',title:'品牌名称',sortable:true,width:60,hidden:true,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.brandname;
                    }
                },
                {field:'taxprice',title:'单价',width:60,align:'right',sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'unitnum',title:'数量',width:50,
                    formatter:function(value,rowData,rowIndex){
                        return formatterBigNumNoLen(value);
                    }
                },
                {field:'totalbox',title:'总箱数',width:50,align:'right',
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
                {field:'taxamount',title:'金额',width:80,align:'right',
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
                {field:'notaxamount',title:'未税金额',width:80,align:'right',
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
                {field:'tax',title:'税额',width:80,align:'right',hidden:true,
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
                {field:'indooruserid',title:'销售内勤',width:80,sortable:true,
                    formatter:function(value,rowData,index){
                        return rowData.indoorusername;
                    }
                },
                {field:'sourceid',title:'来源单号/客户单号',width:120,sortable:true},
                {field:'boxweight',title:'总重量(千克)',width:80,hidden:true},
                {field:'boxvolume',title:'总体积(立方米)',width:100,hidden:true},
                {field:'remark',title:'备注',width:100},
            ]]
        });

        $("#crm-table-terminalSalesOrderReportPage").datagrid({
            authority: crmTerminalJson,
            frozenColumns: crmTerminalJson.frozen,
            columns: crmTerminalJson.common,
            fit: true,
            method: 'post',
            rownumbers: true,
            pagination: true,
            idField: 'id',
            pageSize: 20,
            pageList: [10, 20, 30, 50],
            checkOnSelect: true,
            selectOnCheck:true,
            queryParams:initQueryJSON,
            url: 'crm/dataport/getTerminalSalesOrderReportData.do',
            toolbar:'#crm-query-terminalSalesOrderReportPage'
        });

        //客户销量更新
        $("#crm-update-terminalSalesOrderReportPage").click(function(){
            getCrmSalesSync();
        });

        //全局导出
        $("#crm-export-terminalSalesOrderReportPage").click(function(){
            //封装查询条件
            var objecr  = $("#crm-table-terminalSalesOrderReportPage").datagrid("options");
            var queryParam = objecr.queryParams;
            if(null != objecr.sortName && null != objecr.sortOrder ){
                queryParam["sort"] = objecr.sortName;
                queryParam["order"] = objecr.sortOrder;
            }
            var queryParam = JSON.stringify(queryParam);
            var url = "crm/dataport/exportCrmSalesOrderData.do";
            exportByAnalyse(queryParam,"客户库存表","crm-table-terminalSalesOrderReportPage",url);
        });
        //查询
        $("#crm-queay-terminalSalesOrderReportPage").click(function(){
            var queryJSON = $("#crm-form-terminalSalesOrderReportPage").serializeJSON();
            $("#crm-table-terminalSalesOrderReportPage").datagrid('load', queryJSON);
        });
        //重置
        $("#crm-resetQueay-terminalSalesOrderReportPage").click(function(){
            $("#crm-customer-terminalSalesOrderReportPage").customerWidget("clear");
            $("#crm-salesDept-terminalSalesOrderReportPage").widget("clear");
            $("#crm-supplierid-terminalSalesOrderReportPage").widget("clear");
            $("#crm-goodsid-terminalSalesOrderReportPage").widget("clear");
            $("#crm-form-terminalSalesOrderReportPage").form("reset");
            var queryJSON = $("#crm-form-terminalSalesOrderReportPage").serializeJSON();
            $("#crm-table-terminalSalesOrderReportPage").datagrid('load', queryJSON);
        });
        //客户参照窗口
        $("#crm-customer-terminalSalesOrderReportPage").customerWidget({
            singleSelect:true,
            isdatasql:false,
            onlyLeafCheck:false
        });
        //销售部门控件
        $("#crm-salesDept-terminalSalesOrderReportPage").widget({
            referwid:'RL_T_BASE_DEPARTMENT_SELLER',
            singleSelect:true,
            width:160,
            onlyLeafCheck:false
        });
        //供应商
        $("#crm-supplierid-terminalSalesOrderReportPage").widget({
            referwid:'RL_T_BASE_BUY_SUPPLIER',
            singleSelect:true,
            width:160,
            onlyLeafCheck:false
        });
        //商品
        $("#crm-goodsid-terminalSalesOrderReportPage").widget({
            referwid:'RL_T_BASE_GOODS_INFO',
            singleSelect:true,
            width:160,
            onlyLeafCheck:false
        });
    });
    //客户销量同步
    function getCrmSalesSync(){

        $('<div id="crm-updateSales-dialog-content"></div>').appendTo('#crm-updateSales-dialog');
        $("#crm-updateSales-dialog-content").dialog({
            title:'客户销量更新',
            maximizable:true,
            width:300,
            height:250,
            closed:true,
            modal:true,
            cache:false,
            resizable:true,
            href:'crm/dataport/getCrmSalesSyncPage.do',
            onLoad:function(){
                $("#crm-customerid-customerSummaryPage").focus();
            }
        });
        $("#crm-updateSales-dialog-content").dialog('open');
    }



</script>
</body>
</html>
