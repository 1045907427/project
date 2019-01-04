<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>客户库存明细报表</title>
    <%@include file="/include.jsp"%>
</head>
<body>
<table id="crm-table-customerSummaryReportPage"></table>
<div id="crm-query-customerSummaryReportPage" style="height:85px;padding: 0px">
    <security:authorize url="/crm/report/exportCustomerSummaryReport.do">
        <a href="javaScript:void(0);" id="crm-export-customerSummaryReportPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
    </security:authorize>
    <security:authorize url="/crm/report/updateCustomerSummaryReport.do">
        <a href="javaScript:void(0);" id="crm-update-customerSummaryReportPage" class="easyui-linkbutton" iconCls="button-edit" plain="true" title="库存更新">库存更新</a>
    </security:authorize>
    <security:authorize url="/crm/report/clearCustomerSummaryReport.do">
         <a href="javaScript:void(0);" id="crm-clear-customerSummaryReportPage" class="easyui-linkbutton" iconCls="button-delete" plain="true" title="库存清除">库存清除</a>
    </security:authorize>
    <form action="" id="crm-form-customerSummaryReportPage" method="post">
        <table class="querytable">
            <tr>
                <td>业务日期：</td>
                <td class="tdinput"><input type="text" name="businessdate" style="width:95px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" />
                    到 <input type="text" name="businessdate1" style="width:95px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" />
                </td>
                <td>商&nbsp;&nbsp;品：</td>
                <td class="tdinput"><input type="text" name="goodsid" id="crm-goodsid-customerSummaryReportPage" class="len160" /> </td>
                <%--<td>单据编号：</td>--%>
                <%--<td class="tdinput"><input type="text" name="id" style="width: 160px" /> </td>--%>
            </tr>
            <tr>
                <td>客&nbsp;&nbsp;户：</td>
                <td class="tdinput"><input type="text" id="crm-customer-customerSummaryReportPage" style="width: 210px" name="customerid" /></td>
                <td>供应商：</td>
                <td class="tdinput"><input type="text" name="supplierid" id="crm-supplierid-customerSummaryReportPage" class="len160" /> </td>
                <td colspan="2" class="tdbutton" style="padding-left: 5px">
                    <a href="javascript:;" id="crm-queay-customerSummaryReportPage" class="button-qr">查询</a>
                    <a href="javaScript:;" id="crm-resetQueay-customerSummaryReportPage" class="button-qr">重置</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="crm-importModel-dialog" ></div>
<div id="crm-updateSummary-dialog" ></div>
<div id="crm-clearSummary-dialog" ></div>
<div id="crm-goodsHistory-dialog"></div>
<script type="text/javascript">
    var footerobject  = null;
    var initQueryJSON = $("#crm-form-customerSummaryReportPage").serializeJSON();

    $(function(){
        //表头标题
        var crmTerminalJson = $("#crm-table-customerSummaryReportPage").createGridColumnLoad({
            name :'t_crm_customer_summary',
            commonCol : [[
//                {field:'id',title:'编号',width:150, align: 'left',hidden:true},
                {field:'businessdate',title:'业务日期',width:80,align:'left',sortable:true},
                {field:'customerid',title:'客户编码',width:70,align:'left',sortable:true},
                {field:'customername',title:'客户名称',width:150,align:'left',isShow:true},
                {field:'supplierid',title:'供应商名称',sortable:true,width:200,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.suppliername;
                    }
                },
                {field:'goodsid',title:'商品编码',sortable:true,width:100},
                {field:'goodsname',title:'商品名称',width:200},
                {field:'spell',title:'助记符',width:80,hidden:true},
                {field:'barcode',title:'条形码',width:120,sortable:true,hidden:true},
                {field:'boxnum',title:'箱装量',width:50,
                    formatter:function(value,rowData,rowIndex){
                        return formatterBigNumNoLen(value);
                    }
                },
                {field:'brandid',title:'品牌名称',sortable:true,width:60,hidden:true,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.brandname;
                    }
                },
//                {field:'taxprice',title:'单价',width:60,align:'right',sortable:true,
//                    formatter:function(value,rowData,rowIndex){
//                        return formatterMoney(value);
//                    }
//                },
                {field:'unitnum',title:'数量',width:60,
                    formatter:function(value,rowData,rowIndex){
                        if(value == 0){
                            return 0 ;
                        }else{
                            return formatterBigNumNoLen(value);
                        }
                    }
                },
                {field:'totalbox',title:'总箱数',width:60,align:'right',
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
//                {field:'taxamount',title:'金额',width:80,align:'right',
//                    formatter:function(value,row,index){
//                        return formatterMoney(value);
//                    }
//                },
                {field:'boxweight',title:'总重量(千克)',width:80,hidden:true},
                {field:'boxvolume',title:'总体积(立方米)',width:100,hidden:true},
                {field:'remark',title:'备注',width:80}
            ]]
        });
        //导出
        $("#crm-export-customerSummaryReportPage").click(function(){
            //封装查询条件
            var objecr  = $("#crm-table-customerSummaryReportPage").datagrid("options");
            var queryParam = objecr.queryParams;
            if(null != objecr.sortName && null != objecr.sortOrder ){
                queryParam["sort"] = objecr.sortName;
                queryParam["order"] = objecr.sortOrder;
            }
            var queryParam = JSON.stringify(queryParam);
            var url = "crm/dataport/exportCustomerStorageData.do";
            exportByAnalyse(queryParam,"客户库存明细报表","crm-table-customerSummaryReportPage",url);
        });
        //库存更新
        $("#crm-update-customerSummaryReportPage").click(function(){
            getCustomerSummary();
        });
        function getCustomerSummary(){
            $('<div id="crm-updateSummary-dialog-content"></div>').appendTo('#crm-updateSummary-dialog');
            $("#crm-updateSummary-dialog-content").dialog({
                title:'客户库存更新',
                maximizable:true,
                width:300,
                height:250,
                closed:true,
                modal:true,
                cache:false,
                resizable:true,
                href:'crm/dataport/getCustomerSummaryPage.do',
                onLoad:function(){
                    $("#crm-customerid-customerSummaryPage").focus();
                },
                onClose:function(){
                    $('#crm-updateSummary-dialog-content').dialog("destroy");
                }
            });
            $("#crm-updateSummary-dialog-content").dialog('open');
        }
        //库存清除
        $("#crm-clear-customerSummaryReportPage").click(function(){
            clearCustomerSummary();
        });
        function clearCustomerSummary(){
            $('<div id="crm-clearSummary-dialog-content"></div>').appendTo('#crm-clearSummary-dialog');
            $("#crm-clearSummary-dialog-content").dialog({
                title:'客户库存清除',
                maximizable:true,
                width:300,
                height:250,
                closed:true,
                modal:true,
                cache:false,
                resizable:true,
                href:'crm/dataport/getCustomerSummaryClearPage.do',
                onLoad:function(){
                    $("#crm-customerid-customerSummaryPage").focus();
                },
                onClose:function(){
                    $('#crm-clearSummary-dialog-content').dialog("destroy");
                }
            });
            $("#crm-clearSummary-dialog-content").dialog('open');
        }

        $("#crm-table-customerSummaryReportPage").datagrid({
            authority: crmTerminalJson,
            frozenColumns: crmTerminalJson.frozen,
            columns: crmTerminalJson.common,
            fit: true,
            method: 'post',
            rownumbers: true,
            pagination: true,
            showFooter: true,
            pageSize: 20,
            checkOnSelect: true,
            singleSelect:true,
            queryParams:initQueryJSON,
            url: 'crm/dataport/getCustomerSummaryData.do',
            toolbar:'#crm-query-customerSummaryReportPage',
            onLoadSuccess:function() {
                var footerrows = $(this).datagrid('getFooterRows');
                if (null != footerrows && footerrows.length > 0) {
                    footerobject = footerrows[0];
                }
            },
            onDblClickRow:function(){
                showGoodsHistorByCustomerid();
            }
        });
        //查询
        $("#crm-queay-customerSummaryReportPage").click(function(){
            var queryJSON = $("#crm-form-customerSummaryReportPage").serializeJSON();
            $("#crm-table-customerSummaryReportPage").datagrid('load', queryJSON);
        });
        //重置
        $("#crm-resetQueay-customerSummaryReportPage").click(function(){
            $("#crm-customer-customerSummaryReportPage").customerWidget("clear");
            $("#crm-supplierid-customerSummaryReportPage").widget("clear");
            $("#crm-goodsid-customerSummaryReportPage").widget("clear");
            $("#crm-form-customerSummaryReportPage").form("reset");
            var queryJSON = $("#crm-form-customerSummaryReportPage").serializeJSON();
            $("#crm-table-customerSummaryReportPage").datagrid('load', queryJSON);
        });
        //客户参照窗口
        $("#crm-customer-customerSummaryReportPage").customerWidget({
            singleSelect:false,
            isdatasql:false,
            onlyLeafCheck:false
        });
        //供应商
        $("#crm-supplierid-customerSummaryReportPage").widget({
            referwid:'RL_T_BASE_BUY_SUPPLIER',
            singleSelect:false,
            width:160,
            onlyLeafCheck:false
        });
        //商品
        $("#crm-goodsid-customerSummaryReportPage").widget({
            referwid:'RL_T_BASE_GOODS_INFO',
            singleSelect:false,
            width:160,
            onlyLeafCheck:false
        });
        //查看历史库存
        function showGoodsHistorByCustomerid(){
            var row = $("#crm-table-customerSummaryReportPage").datagrid('getSelected');
            if(row == null){
                $.messager.alert("提醒", "请选择一条记录");
                return false;
            }
            $("#crm-goodsHistory-dialog").dialog({
                title:'客户['+row.customerid+'] 商品['+row.goodsid+']'+row.goodsname+' 历史库存表',
                width:600,
                height:400,
                closed:false,
                modal:true,
                cache:false,
                maximizable:true,
                resizable:true,
                href:'crm/dataport/showCustomerSummaryHistory.do',
                queryParams:{customerid:row.customerid,goodsid:row.goodsid}
            });

        }

    });

</script>
</body>
</html>
