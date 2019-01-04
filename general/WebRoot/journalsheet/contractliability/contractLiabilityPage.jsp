<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>每日进销存汇总统计报表</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>
    <style>
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
    </style>
</head>

<body>
<table id="report-datagrid-contractLiabilityPage"></table>
<div id="report-toolbar-contractLiabilityPage" style="padding:0px;height:auto">
    <%--<div class="buttonBG">--%>

    <%--</div>--%>
    <form action="" id="report-query-form-contractLiabilityPage" method="post">
        <table class="querytable">
            <tr>
                <td>月份:</td>
                <td><input type="text" id="report-query-month" name="month" value="${month }" style="width:150px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM'})" />
                </td>
                <td>客户:</td>
                <td><input type="text" id="report-query-customer" name="customerid"/></td>
                <td>供应商:</td>
                <td><input type="text" id="report-query-supplierid" name="supplierid"/></td>
            </tr>
            <tr>
                <td>销售部门:</td>
                <td><input type="text" id="report-query-salesdeptid" name="salesdeptid"/></td>
                <td>客户业务员:</td>
                <td><input type="text" id="report-query-salesuserid" name="salesuserid"/></td>
                <td colspan="2">
                    <a href="javaScript:void(0);" id="report-queay-contractLiabilityPage" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="report-reload-contractLiabilityPage" class="button-qr">重置</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="contract-dialog-contractLiabilityPage"></div>
<script type="text/javascript">


    $(function(){
        $("#report-datagrid-contractLiabilityPage").treegrid({
            columns:[[
                {field:'name',title:'客户名称',width:150,sortable:true
                },
                {field:'totalamount',title:'权责金额',width:90,align:'right',
                    formatter:function(value,rowData,rowIndex){
                        if("1"==rowData.type){
                            return formatterMoney(value);
                        }else{
                            return "<font color='blue' style='cursor: pointer;' >" + formatterMoney(value) + "</font>";
                        }
                    }
                },
            ]],
            method:'post',
            title:'',
            fit:true,
            rownumbers:true,
            idField:'rid',
            treeField:'name',
            pagination:true,
            showFooter: true,
            pageSize:100,
            singleSelect:true,
            checkOnSelect:true,
            selectOnCheck:true,
            toolbar:'#report-toolbar-contractLiabilityPage',
        });
        //客户
        $("#report-query-customer").widget({
            referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT_2',
            width:150,
            singleSelect:false
        });
        $("#report-query-supplierid").widget({
            referwid:'RL_T_BASE_BUY_SUPPLIER',
            width:150,
            singleSelect:false,
        });
        $("#report-query-salesdeptid").widget({
            referwid:'RL_T_BASE_DEPARTMENT_SELLER',
            width:150,
            singleSelect:false
        });
        $("#report-query-salesuserid").widget({
            referwid:'RL_T_BASE_PERSONNEL_SELLER',
            width:150,
            singleSelect:false,
        });

        //回车事件
        controlQueryAndResetByKey("report-queay-contractLiabilityPage","report-reload-contractLiabilityPage");

        //查询
        $("#report-queay-contractLiabilityPage").click(function(){
            var month = $("#report-query-month").val();
            if(""==month){
                $.messager.alert("提醒","请选择月份!");
                return false;
            }
            var queryJSON = $("#report-query-form-contractLiabilityPage").serializeJSON();
            $("#report-datagrid-contractLiabilityPage").treegrid({
                url: 'journalsheet/contractreport/showContractLiabilityData.do',
                pageNumber:1,
                queryParams:queryJSON
            });
        });
        //重置
        $("#report-reload-contractLiabilityPage").click(function(){
            $("#report-query-customer").widget('clear');
            $("#report-query-form-contractLiabilityPage").form("reset");
            var queryJSON = $("#report-query-form-contractLiabilityPage").serializeJSON();
            $("#report-datagrid-contractLiabilityPage").treegrid('loadData',{total:0,rows:[],footer:[]});
        });

        $(document).die("keydown").live("keydown",function(event){
            if(event.keyCode==13){
                $("#report-datagrid-contractLiabilityPage").treegrid('clearSelections');
                $("#report-datagrid-contractLiabilityPage").treegrid('selectRow',editIndex+1);
                onClickCell(editIndex+1, editfiled);
            }
        });

    });

    function refresh(){
        var queryJSON = $("#report-query-form-contractLiabilityPage").serializeJSON();
        $("#report-datagrid-contractLiabilityPage").treegrid({
            url: 'journalsheet/contractreport/showContractLiabilityData.do',
            pageNumber:1,
            queryParams:queryJSON
        });
    }
</script>
</body>
</html>
