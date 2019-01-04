<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>费用汇总统计报表</title>
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
<table id="report-datagrid-contractReportPage"></table>
<div id="report-toolbar-contractReportPage" style="padding:0px;height:auto">
    <div class="buttonBG">
        <security:authorize url="/journalsheet/contractreport/addContractReport.do">
            <a href="javaScript:void(0);" id="report-buttons-add-contractReportPage" class="easyui-linkbutton" iconCls="button-add" plain="true" title="生成客户合同费用">生成客户合同费用</a>
        </security:authorize>
        <security:authorize url="/journalsheet/contractreport/auditContractReport.do">
            <a href="javaScript:void(0);" id="report-buttons-audit-contractReportPage" class="easyui-linkbutton" iconCls="button-audit" plain="true" title="确认合同费用">确认合同费用</a>
        </security:authorize>
        <security:authorize url="/journalsheet/contractreport/oppauditContractReport.do">
            <a href="javaScript:void(0);" id="report-buttons-oppaudit-contractReportPage" class="easyui-linkbutton" iconCls="button-audit" plain="true" title="取消确认合同费用">取消确认合同费用</a>
        </security:authorize>
        <security:authorize url="/journalsheet/contractreport/CreatPayableAndMatcost.do">
            <a href="javaScript:void(0);" id="report-buttons-audit1-contractReportPage" class="easyui-linkbutton" iconCls="button-audit" plain="true" title="生成客户应付费用和代垫费用">生成客户应付费用和代垫费用</a>
        </security:authorize>
    </div>
    <form action="" id="report-query-form-contractReportPage" method="post">
        <table class="querytable">
            <tr>
                <td>月份:</td>
                <td><input type="text" id="report-query-month" name="month" value="${month }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM'})" />
                </td>
                <td>客户:</td>
                <td><input type="text" id="report-query-customer" name="customerid"/></td>
                <td colspan="4">
                </td>
                <td colspan="2">
                    <a href="javaScript:void(0);" id="report-queay-contractReportPage" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="report-reload-contractReportPage" class="button-qr">重置</a>

                </td>
            </tr>
        </table>
    </form>
</div>
<div id="contract-dialog-contractReportPage"></div>
<script type="text/javascript">


    $(function(){

        $("#report-datagrid-contractReportPage").datagrid({
            columns:[[
                {field:'month',title:'月份',width:70},
                {field:'customerid',title:'客户名称',width:90,sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        return rowData.customername;
                    }
                },
//                {field:'deptid',title:'部门名称',width:70,sortable:true,
//                    formatter:function(value,rowData,rowIndex){
//                        return rowData.deptname;
//                    }
//                },
                {field:'saleamount',title:'销售额',width:90,sortable:true},
                {field:'storenum',title:'门店数量',width:90},
                {field:'newstorenum',title:'新门店数量',width:90},
                {field:'newgoodsnum',title:'新品数量',width:90,align:'right'},
                {field:'costamount',title:'费用金额',width:90,align:'right'},
                {field:'matcostsamount',title:'代垫金额',width:90,align:'right'},
                {field:'selfamount',title:'自理金额',width:90,align:'right'},
//                {field:'rate',title:'百分比',width:90,align:'right'},
                {field:'state',title:'状态',width:70,sortable:true,
                    formatter:function(value,rowData,rowIndex){
                        if("0" == value){
                            return "未确认";
                        }else if("1" == value){
                            return "已确认";
                        }else if("2" == value){
                            return "已生成";
                        }else{
                            return "";
                        }
                    }
                },
            ]],
            method:'post',
            title:'',
            fit:true,
            rownumbers:true,
            pagination:true,
            showFooter: true,
            pageSize:100,
            singleSelect:true,
            checkOnSelect:true,
            selectOnCheck:true,
            toolbar:'#report-toolbar-contractReportPage',
            onDblClickRow: function(rowIndex, rowData){
                if(null == rowData.state){
                    return false;
                }
                showDetailPage(rowData.month,rowData.customerid,rowData.state);

            },
        }).datagrid("columnMoving");
        $("#report-query-goodsid").goodsWidget({
            isHiddenUsenum:true
        });
        //品牌
        $("#report-query-brandid").widget({
            referwid:'RL_T_BASE_GOODS_BRAND',
            width:180,
            singleSelect:false
        });
        //仓库
        $("#report-query-storageid").widget({
            referwid:'RL_T_BASE_STORAGE_INFO',
            width:150,
            singleSelect:false
        });
        //品牌部门
        $("#report-query-branddept").widget({
            referwid:'RL_T_BASE_DEPARTMENT_BUYER',
            width:150,
            singleSelect:true
        });
        //客户
        $("#report-query-customer").widget({
            referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT_2',
            width:180,
            singleSelect:false
        });

        //回车事件
        controlQueryAndResetByKey("report-queay-contractReportPage","report-reload-contractReportPage");

        //查询
        $("#report-queay-contractReportPage").click(function(){
            var month = $("#report-query-month").val();
            if(""==month){
                $.messager.alert("提醒","请选择月份!");
                return false;
            }
            var queryJSON = $("#report-query-form-contractReportPage").serializeJSON();
            $("#report-datagrid-contractReportPage").datagrid({
                url: 'journalsheet/contractreport/showContractReportData.do',
                pageNumber:1,
                queryParams:queryJSON
            });
        });
        //重置
        $("#report-reload-contractReportPage").click(function(){
            $("#report-query-customer").widget('clear');
            $("#report-query-form-contractReportPage").form("reset");
            var queryJSON = $("#report-query-form-contractReportPage").serializeJSON();
            $("#report-datagrid-contractReportPage").datagrid('loadData',{total:0,rows:[],footer:[]});
        });

        $(document).die("keydown").live("keydown",function(event){
            if(event.keyCode==13){
                $("#report-datagrid-contractReportPage").datagrid('clearSelections');
                $("#report-datagrid-contractReportPage").datagrid('selectRow',editIndex+1);
                onClickCell(editIndex+1, editfiled);
            }
        });
        //显示生成合同费用界面
        $("#report-buttons-add-contractReportPage").click(function(){
            $('<div id="contract-dialog-contractReportPage-content"></div>').appendTo('#contract-dialog-contractReportPage');
            $('#contract-dialog-contractReportPage-content').dialog({
                title: '生成合同费用',
                width: 900,
                height: 500,
                collapsible:false,
                minimizable:false,
                maximizable:true,
                resizable:true,
                closed: true,
                cache: false,
                modal: true,
                href: 'journalsheet/contractreport/showContractReportAddPage.do',
                onLoad:function(){
                },
                onClose:function(){
                    $("#contract-dialog-contractReportPage-content").dialog("destroy");
                }
            });
            $('#contract-dialog-contractReportPage-content').dialog("open");
        });

        //确认合同费用
        $("#report-buttons-audit-contractReportPage").click(function(){
            var month = "";
            var rows = $("#report-datagrid-contractReportPage").datagrid('getChecked');
            if(rows.length == 0){
                $.messager.alert("提醒","请勾选数据!");
                return false;
            }
            var customerids = "";
            for (var i = 0; i < rows.length; i++) {
                if (customerids == "") {
                    customerids = rows[i].customerid;
                } else {
                    customerids += "," + rows[i].customerid;
                }
                month = rows[i].month;
            }
            loading('提交中...');
            $.ajax({
                url :'journalsheet/contractreport/auditContractReport.do?customerids=' + customerids+'&month='+month,
                type:'post',
                dataType:'json',
                success:function(json){
                    loaded();
                    if(json.flag){
                        editIndex = undefined;
                        $.messager.alert("提醒","确认成功!");
                        $("#report-datagrid-contractReportPage").datagrid('reload');
                    }else{
                        $.messager.alert("提醒","确认失败!");
                    }
                }
            });
        });
        //取消确认确认合同费用
        $("#report-buttons-oppaudit-contractReportPage").click(function(){
            var month = "";
            var rows = $("#report-datagrid-contractReportPage").datagrid('getChecked');
            if(rows.length == 0){
                $.messager.alert("提醒","请勾选数据!");
                return false;
            }
            var customerids = "";
            for (var i = 0; i < rows.length; i++) {
                if (customerids == "") {
                    customerids = rows[i].customerid;
                } else {
                    customerids += "," + rows[i].customerid;
                }
                month = rows[i].month;
            }
            loading('提交中...');
            $.ajax({
                url :'journalsheet/contractreport/oppauditContractReport.do?customerids=' + customerids+'&month='+month,
                type:'post',
                dataType:'json',
                success:function(json){
                    loaded();
                    if(json.flag){
                        editIndex = undefined;
                        $.messager.alert("提醒","取消确认成功!");
                        $("#report-datagrid-contractReportPage").datagrid('reload');
                    }else{
                        $.messager.alert("提醒","取消确认失败!");
                    }
                }
            });
        });
        //生成客户应付费用和代垫费用
        $("#report-buttons-audit1-contractReportPage").click(function(){
            var month = "";
            var rows = $("#report-datagrid-contractReportPage").datagrid('getChecked');
            if(rows.length == 0){
                $.messager.alert("提醒","请勾选数据!");
                return false;
            }
            var customerids = "";
            for (var i = 0; i < rows.length; i++) {
                if (customerids == "") {
                    customerids = rows[i].customerid;
                } else {
                    customerids += "," + rows[i].customerid;
                }
                month = rows[i].month;
            }
            loading('提交中...');
            $.ajax({
                url :'journalsheet/contractreport/CreatPayableAndMatcost.do?customerids=' + customerids+'&month='+month,
                type:'post',
                dataType:'json',
                success:function(json){
                    loaded();
                    if(json.flag){
                        editIndex = undefined;
                        $.messager.alert("提醒","生成成功!");
                        $("#report-datagrid-contractReportPage").datagrid('reload');
                    }else{
                        $.messager.alert("提醒","生成失败!");
                    }
                }
            });
        });
    });

    function  showDetailPage(month,customerid,state) {
        var url = "journalsheet/contractreport/showContractReportDetailPage.do?month="+month+"&customerid="+customerid+"&state="+state;
        top.addTab(url, '客户合同统计明细');
    }

    function refresh(){
        var queryJSON = $("#report-query-form-contractReportPage").serializeJSON();
        $("#report-datagrid-contractReportPage").datagrid({
            url: 'journalsheet/contractreport/showContractReportData.do',
            pageNumber:1,
            queryParams:queryJSON
        });
    }
</script>
</body>
</html>
