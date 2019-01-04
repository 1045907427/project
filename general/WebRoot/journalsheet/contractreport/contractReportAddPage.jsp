<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>生成客户合同费用</title>
</head>
<body>
<table id="contract-datagrid-addContractReprot"></table>
    <div id="contract-toolbar-addContractReprot" style="padding:0px;height:auto">
        <div class="buttonBG">
            <security:authorize url="/report/storage/contractReportPageMonthExport.do">
                <a href="javaScript:void(0);" id="contract-savegoon-addContractReprot" class="easyui-linkbutton" iconCls="button-add" plain="true" title="生成客户合同费用">生成客户合同费用</a>
            </security:authorize>
        </div>
        <form action="" method="post" id="contract-form-addContractReprot">
            <div data-options="region:'north',border:false" style="height:40px;">
                <table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
                    <tr>
                        <td class="len70 left">月份:</td>
                        <td style="text-align: left;">
                            <input class="len150 easyui-validatebox" id="contract-month-addContractReprot" name="month" value="${month }" data-options="required:true" onfocus="WdatePicker({'dateFmt':'yyyy-MM'})" />
                        </td>
                        <td class="len70 left">客户:</td>
                        <td style="text-align: left;">
                            <input type="text" id="contract-customer-addContractReprot" name="customerid" style="width: 150px"/>
                        </td>
                        <td class="len70 left">部门:</td>
                        <td style="text-align: left;">
                            <input type="text" id="contract-deptid-addContractReprot" name="deptid" style="width: 150px"/>
                        </td>
                        <td colspan="2">
                            <a href="javaScript:void(0);" id="report-queay-addContractReprot" class="button-qr">查询</a>
                            <a href="javaScript:void(0);" id="report-reload-addContractReprot" class="button-qr">重置</a>

                        </td>
                    </tr>
                </table>
            </div>
        </form>
    </div>
<script type="text/javascript">

    $(function() {

        $("#contract-datagrid-addContractReprot").datagrid({
            columns:[[
                {field: 'ck', checkbox: true},
//                {field:'contractid',title:'合同编号',sortable:true,width:90},
//                {field:'contractname',title:'合同名称',width:90,sortable:true},
                {field:'customerid',title:'客户编号',width:150,sortable:true},
                {field:'customername',title:'客户名称',width:150,sortable:true},
//                {field:'deptid',title:'部门名称',width:70,sortable:true,
//                    formatter:function(value,rowData,rowIndex){
//                        return rowData.deptname;
//                    }
//                },
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
            toolbar:'#contract-toolbar-addContractReprot',
        }).datagrid("columnMoving");

        //查询
        $("#report-queay-addContractReprot").click(function(){
            var month = $("#contract-month-addContractReprot").val();
            if(""==month){
                $.messager.alert("提醒", "请选择月份!");
                return false;
            }
            var queryJSON = $("#contract-form-addContractReprot").serializeJSON();
            $("#contract-datagrid-addContractReprot").datagrid({
                url: 'journalsheet/contractreport/showAddContractReportData.do',
                pageNumber:1,
                queryParams:queryJSON
            });
        });

        //重置
        $("#report-reload-addContractReprot").click(function(){
            $("#contract-customer-addContractReprot").widget('clear');
            $("#contract-form-addContractReprot").form("reset");
            var queryJSON = $("#contract-form-addContractReprot").serializeJSON();
            $("#contract-datagrid-addContractReprot").datagrid('loadData',{total:0,rows:[],footer:[]});
        });

        //添加
        $("#contract-savegoon-addContractReprot").click(function(){
            var rowdata = $("#contract-datagrid-addContractReprot").datagrid('getSelected');
            console.log(rowdata)
            if (null == rowdata) {
                $.messager.alert("提醒", "请选择客户费用合同!");
                return false;
            }
            var customerid = rowdata.customerid;
            $("#contract-form-addContractReprot").attr("action", "journalsheet/contractreport/addContractReport.do?customerid=" + customerid);
            $("#contract-form-addContractReprot").submit();
        });
        $("#contract-form-addContractReprot").form({
            onSubmit: function(){
                var flag = $(this).form('validate');
                if(flag==false){
                    return false;
                }
                loading("提交中..");
            },
            success:function(data){
                //表单提交完成后 隐藏提交等待页面
                loaded();
                //转为json对象
                var json = $.parseJSON(data);
                if(json.flag){
                    $.messager.alert("提醒",'生成成功！');
                    $("#contract-dialog-contractReportPage-content").dialog("destroy");
                    showDetailPage(json.month,json.customerid,"0");
                    refresh();

                }else{
                    $.messager.alert("提醒",'生成失败！'+json.msg);
                }
            }
        });
    })
    //客户
    $("#contract-customer-addContractReprot").widget({
        referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT_2',
        width:150,
        singleSelect:false
    });
    $("#contract-deptid-addContractReprot").widget({
        referwid:'RL_T_BASE_DEPARTMENT_1',
        width:150,
        singleSelect:true,
        onlyLeafCheck:false,
        view:true
    });
</script>
</body>
</html>
