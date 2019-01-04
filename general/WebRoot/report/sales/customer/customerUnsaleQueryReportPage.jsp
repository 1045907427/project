<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>客户未销售查询报表</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>
</head>
<body>
<table id="report-datagrid-customerUnsaleQuery"></table>
<div id="report-toolbar-customerUnsaleQuery" style="padding: 0px">
    <div class="buttonBG">
        <security:authorize url="/report/sales/exportcustomerUnsaleQueryReportData.do">
            <a href="javaScript:void(0);" id="report-buttons-customerUnsaleQuery" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
        </security:authorize>
    </div>
    <form action="" id="report-query-form-customerUnsaleQuery" method="post">
        <table class="querytable">
            <tr>
                <td>业务日期：</td>
                <td><input type="text" name="businessdate1" value="${date}" style="width:90px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到
                    <input type="text" name="businessdate2" value="${today}" style="width:90px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
                <td>销售区域：</td>
                <td><input type="text" id="report-query-salesarea" name="salesarea"/></td>
                <td>客户分类：</td>
                <td><input type="text" id="report-query-customersort" name="customersort"/></td>
            </tr>
            <tr>
                <td>客户名称：</td>
                <td><input type="text" id="report-query-customerid" name="customerid"/></td>
                <td>客户业务员：</td>
                <td><input type="text" id="report-query-salesuser" name="salesuser"/></td>
                <td>客户联系人：</td>
                <td><input type="text" name="contact" class="len150"/></td>
            </tr>
            <tr>
                <td>交易情况：</td>
                <td><select name="ismarkets" style="width: 205px;">
                    <option></option>
                    <option value="0">未交易</option>
                    <option value="1">已交易</option>
                </select> </td>
                <td colspan="2"></td>
                <td colspan="2">
                    <a href="javaScript:void(0);" id="report-queay-customerUnsaleQuery" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="report-reload-customerUnsaleQuery" class="button-qr">重置</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<script type="text/javascript">
    var CUQ_footerobject  = null;
    var initQueryJSON = $("#report-query-form-customerUnsaleQuery").serializeJSON();
    $(function(){
        var CUQ_col_json = $("#report-datagrid-customerUnsaleQuery").createGridColumnLoad({
            frozenCol : [[
                {field:'idok',checkbox:true,isShow:true}
            ]],
            commonCol : [[
                {field:'customerid',title:'客户编号',sortable:true,width:60},
                {field:'customername',title:'客户名称',width:210},
                {field:'salesarea',title:'销售区域',width:70,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.salesareaname;
                    }
                },
                {field:'customersort',title:'客户分类',sortable:true,width:80,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.customersortname;
                    }
                },
                {field:'salesuser',title:'客户业务员',sortable:true,width:80,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.salesusername;
                    }
                },
                {field:'contact',title:'客户联系人',width:70},
                {field:'mobile',title:'联系电话',width:80},
                {field:'demandamount',title:'要货金额',resizable:true,align:'right',sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'orderamount',title:'订单金额',resizable:true,align:'right',sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'saleoutamount',title:'发货单金额',resizable:true,align:'right',sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                },
                {field:'saleamount',title:'销售金额',resizable:true,align:'right',sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        return formatterMoney(value);
                    }
                }
            ]]
        });

        $("#report-datagrid-customerUnsaleQuery").datagrid({
            authority:CUQ_col_json,
            frozenColumns: CUQ_col_json.frozen,
            columns:CUQ_col_json.common,
            method:'post',
            fit:true,
            rownumbers:true,
            pagination:true,
            showFooter: true,
            singleSelect:false,
            checkOnSelect:true,
            selectOnCheck:true,
            pageSize:100,
            toolbar:'#report-toolbar-customerUnsaleQuery',
            onLoadSuccess:function(){
                var footerrows = $(this).datagrid('getFooterRows');
                if(null!=footerrows && footerrows.length>0){
                    CUQ_footerobject = footerrows[0];
                    countTotalAmount();
                }
            },
            onCheckAll:function(){
                countTotalAmount();
            },
            onUncheckAll:function(){
                countTotalAmount();
            },
            onCheck:function(){
                countTotalAmount();
            },
            onUncheck:function(){
                countTotalAmount();
            }
        }).datagrid("columnMoving");

        //销售区域
        $("#report-query-salesarea").widget({
            referwid:'RT_T_BASE_SALES_AREA',
            singleSelect:true,
            width:'150'
        });
        //客户分类
        $("#report-query-customersort").widget({
            referwid:'RT_T_BASE_SALES_CUSTOMERSORT',
            singleSelect:true,
            width:'150',
            onlyLeafCheck:false
        });
        //客户业务员
        $("#report-query-salesuser").widget({
            referwid:'RL_T_BASE_PERSONNEL_CLIENTSELLER',
            singleSelect:true,
            width:'150',
            onlyLeafCheck:false
        });
        //客户名称
        $("#report-query-customerid").widget({
            referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT_2',
            singleSelect:true,
            width:'205'
        });

        //查询
        $("#report-queay-customerUnsaleQuery").click(function(){
            var queryJSON = $("#report-query-form-customerUnsaleQuery").serializeJSON();
            $("#report-datagrid-customerUnsaleQuery").datagrid({
                url: 'report/sales/showCustomerUnsaleQueryReportData.do',
                pageNumber:1,
                queryParams:queryJSON
            });
        });
        //重置
        $("#report-reload-customerUnsaleQuery").click(function(){
            $("#report-query-salesarea").widget("clear");
            $("#report-query-customersort").widget("clear");
            $("#report-query-customerid").widget("clear");
            $("#report-query-salesuser").widget("clear");
            $("#report-query-form-customerUnsaleQuery").form("reset");
            $("#report-datagrid-customerUnsaleQuery").datagrid('loadData',{total:0,rows:[]});
        });

        $("#report-buttons-customerUnsaleQuery").Excel('export',{
            queryForm: "#report-query-form-customerUnsaleQuery", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
            type:'exportUserdefined',
            name:'客户交易情况报表',
            url:'report/sales/exportCustomerUnsaleQueryReportData.do'
        });
    });

    function countTotalAmount(){
        var rows =  $("#report-datagrid-customerUnsaleQuery").datagrid('getChecked');
        var demandamount = 0;
        var orderamount = 0;
        var saleoutamount = 0;
        var saleamount = 0;
        for(var i=0;i<rows.length;i++){
            demandamount = Number(demandamount)+Number(rows[i].demandamount == undefined ? 0 : rows[i].demandamount);
            orderamount = Number(orderamount)+Number(rows[i].orderamount == undefined ? 0 : rows[i].orderamount);
            saleoutamount = Number(saleoutamount)+Number(rows[i].saleoutamount == undefined ? 0 : rows[i].saleoutamount);
            saleamount = Number(saleamount)+Number(rows[i].saleamount == undefined ? 0 : rows[i].saleamount);
        }
        var foot = [{customerid:'选中合计',demandamount:demandamount,orderamount:orderamount,saleoutamount:saleoutamount,saleamount:saleamount}];
        if(null!=CUQ_footerobject){
            foot.push(CUQ_footerobject);
        }
        $("#report-datagrid-customerUnsaleQuery").datagrid("reloadFooter",foot);
    }
</script>
</body>
</html>
