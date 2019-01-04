<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>客户费用合同</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<table id="contract-table-contractListPage"></table>

<div id="contract-toolbar" style="padding:0px;height:auto">
    <div class="buttonBG">
        <security:authorize url="/journalsheet/contract/addContract.do">
            <a href="javaScript:void(0);" id="contract-button-add" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'" title="新增">新增</a>
        </security:authorize>
        <security:authorize url="/journalsheet/contract/editContract.do">
            <a href="javaScript:void(0);" id="contract-button-edit" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-edit'" title="修改">修改</a>
        </security:authorize>
        <security:authorize url="/journalsheet/contract/deleteContract.do">
            <a href="javaScript:void(0);" id="contract-button-delete" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-delete'" title="删除">删除</a>
        </security:authorize>
        <security:authorize url="/journalsheet/contract/auditContract.do">
            <a href="javaScript:void(0);" id="contract-button-audit" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-audit'" title="审核">审核</a>
        </security:authorize>
        <security:authorize url="/journalsheet/contract/oppauditContract.do">
            <a href="javaScript:void(0);" id="contract-button-oppaudit" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-oppaudit'" title="反审">反审</a>
        </security:authorize>
    </div>
    <form action="" method="post" id="contract-form-contractListPage">
        <table class="querytable">
            <tr>
                <td>单据编号:</td>
                <td>
                    <input type="text"  name="id" style="width:150px"/>
                </td>
                <td>合同编号:</td>
                <td>
                    <input type="text"  name="contractid" style="width:150px"/>
                </td>
                <td>合同名称:</td>
                <td>
                    <input type="text"  name="contractname" style="width:150px"/>
                </td>
            </tr>
            <tr>
                <td>客户名称:</td>
                <td>
                    <input type="text"  name="customerid" id="contract-customerid-contractListPage" style="width:150px"/>
                </td>
                <td colspan="2">
                </td>
                <td colspan="2">
                    <a href="javaScript:void(0);" id="contract-queay-queryList" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="contract-queay-reloadList" class="button-qr">重置</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="contract-dialog"></div>
<script type="text/javascript">
    var initQueryJSON = $("#contract-form-contractListPage").serializeJSON();
    var actiontype="";
    var contractJson = $("#contract-table-contractListPage").createGridColumnLoad({
        name :'',
        frozenCol : [[
            {field: 'ck', checkbox: true},
        ]],
        commonCol : [[
            {field:'id',title:'编号',width:150,sortable:true},
            {field:'contractid',title:'合同编号',width:125,sortable:true},
            {field:'contractname',title:'合同名称',width:150,sortable:true},
            {field:'customerid',title:'客户名称',width:100,sortable:true,
                formatter: function (value, row, index) {
                    return row.customername;
                }
            },
            {field:'customertype',title:'客户类型',width:100,sortable:true,
                formatter: function (value, row, index) {
                    if ('0'==value) {
                        return "总店";
                    }else if('1'==value){
                        return "门店";
                    }else{
                        return "";
                    }
                }
            },
            {field:'contacts',title:'联系人',width:100,sortable:true},
            {field:'contactstype',title:'联系方式',width:100,sortable:true},
            {field:'personnelid',title:'合同负责人',width:100,sortable:true,
                formatter: function (value, row, index) {
                    return row.personnelname;
                }
            },
            {field:'status',title:'状态',width:100,sortable:true,
                formatter: function (value, row, index) {
                    return row.statusname;
                }
            },
            {field:'remark',title:'备注',width:100,sortable:true},
            {field: 'addusername', title: '制单人', width: 60, sortable: true},
            {field: 'addtime', title: '制单时间', width: 130, sortable: true},
            {field: 'auditusername', title: '审核人', width: 80, sortable: true, hidden: true},
            {field: 'audittime', title: '审核时间', width: 80, sortable: true, hidden: true},
            {field: 'modifyusername', title: '修改人', width: 60, sortable: true, hidden: true},
            {field: 'modifytime', title: '修改时间', width: 130, sortable: true, hidden: true}
        ]]
    });


    $(function(){
        $("#contract-table-contractListPage").datagrid({
            authority:contractJson,
            frozenColumns: contractJson.frozen,
            columns:contractJson.common,
            border: true,
            rownumbers: true,
            pagination: true,
            showFooter: true,
            striped:true,
            fit:true,
            singleSelect: false,
            checkOnSelect:true,
            selectOnCheck:true,
            sortName: 'addtime',
            sortOrder: 'desc',
            url: 'journalsheet/contract/showContractListData.do',
            queryParams: initQueryJSON,
            toolbar:'#contract-toolbar',
            onDblClickRow: function(rowIndex, rowData){
                $(this).datagrid('clearSelections').datagrid('selectRow',rowIndex);
                if(rowData.id != undefined){
                    if("2"==rowData.status){
                        var url = "journalsheet/contract/showContractPage.do?type=edit&id="+rowData.id;
                        top.addTab(url, '修改客户费用合同');
                    }else{
                        var url = "journalsheet/contract/showContractPage.do?type=view&id="+rowData.id;
                        top.addTab(url, '查看客户费用合同');
                    }

                }
            },
        }).datagrid('columnMoving');

        $("#contract-addRow").click(function(){
            var flag = $("#contract-contextMenu").menu('getItem','#contract-addRow').disabled;
            if(flag){
                return false;
            }
            showContractAddPage();
        });
        $("#contract-editRow").click(function(){
            var flag = $("#contract-contextMenu").menu('getItem','#contract-editRow').disabled;
            if(flag){
                return false;
            }
            showContractEditPage();
        });
        $("#contract-removeRow").click(function(){
            var flag = $("#contract-contextMenu").menu('getItem','#contract-removeRow').disabled;
            if(flag){
                return false;
            }
            removeContract();
        });
    });

    //查询
    $("#contract-queay-queryList").click(function(){
        //把form表单的name序列化成JSON对象
        var queryJSON = $("#contract-form-contractListPage").serializeJSON();
        $("#contract-table-contractListPage").datagrid("load",queryJSON);
    });
    //重置
    $("#contract-queay-reloadList").click(function(){
        $("#contract-form-contractListPage")[0].reset();
        var queryJSON = $("#contract-form-contractListPage").serializeJSON();
        $("#contract-table-contractListPage").datagrid("load",queryJSON);
    });

    $("#contract-customerid-contractListPage").widget({
        referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT_2',
        width:150,
        singleSelect:true,
        onlyLeafCheck:false,
        view:true
    });

    //新增
    $("#contract-button-add").click(function(){
        var url = "journalsheet/contract/showContractPage.do?type=add";
        top.addTab(url, '新增客户费用合同');
    });

    //修改
    $("#contract-button-edit").click(function () {
        var con = $("#contract-table-contractListPage").datagrid('getSelected');
        if (null == con) {
            $.messager.alert("提醒", "请选择客户费用合同单!");
            return false
        }
        var url = "journalsheet/contract/showContractPage.do?type=edit&id="+con.id;
        top.addTab(url, '修改客户费用合同');
    });
    //删除
    $("#contract-button-delete").click(function(){
        var rows = $("#contract-table-contractListPage").datagrid('getChecked');
        if (rows.length == 0) {
            $.messager.alert("提醒", "请选择客户费用合同!");
            return false
        }
        var ids = "";
        for (var i = 0; i < rows.length; i++) {
            if (ids == "") {
                ids = rows[i].id;
            } else {
                ids += "," + rows[i].id;
            }
        }
        $.messager.confirm('提醒', '确定要删除吗?', function (r) {
            if (r) {
                $.ajax({
                    url: 'journalsheet/contract/deleteContract.do?ids=' + ids,
                    type: 'post',
                    dataType: 'json',
                    async: false,
                    success: function (json) {
                        $.messager.alert("提醒", json.msg);
                        var queryJSON = $("#contract-form-contractListPage").serializeJSON();
                        $("#contract-table-contractListPage").datagrid("load", queryJSON);
                    },
                    error: function () {
                        loaded();
                        $.messager.alert("错误", "删除出错");
                    }
                });
            }
        });
    });

    //审核
    $("#contract-button-audit").click(function(){
        var rows = $("#contract-table-contractListPage").datagrid('getChecked');
        if (rows.length == 0) {
            $.messager.alert("提醒", "请选择客户费用合同!");
            return false
        }
        var ids = "";
        for (var i = 0; i < rows.length; i++) {
            if (ids == "") {
                ids = rows[i].id;
            } else {
                ids += "," + rows[i].id;
            }
        }
        $.messager.confirm('提醒', '确定要审核吗?', function (r) {
            if (r) {
                $.ajax({
                    url: 'journalsheet/contract/auditContract.do?ids=' + ids,
                    type: 'post',
                    dataType: 'json',
                    async: false,
                    success: function (json) {
                        $.messager.alert("提醒", json.msg);
                        var queryJSON = $("#contract-form-contractListPage").serializeJSON();
                        $("#contract-table-contractListPage").datagrid("load", queryJSON);
                    },
                    error: function () {
                        loaded();
                        $.messager.alert("错误", "审核出错");
                    }
                });
            }
        });
    });

    //反审
    $("#contract-button-oppaudit").click(function(){
        var rows = $("#contract-table-contractListPage").datagrid('getChecked');
        if (rows.length == 0) {
            $.messager.alert("提醒", "请选择客户费用合同!");
            return false
        }
        var ids = "";
        for (var i = 0; i < rows.length; i++) {
            if (ids == "") {
                ids = rows[i].id;
            } else {
                ids += "," + rows[i].id;
            }
        }
        $.messager.confirm('提醒', '确定要反审吗?', function (r) {
            if (r) {
                $.ajax({
                    url: 'journalsheet/contract/oppauditContract.do?ids=' + ids,
                    type: 'post',
                    dataType: 'json',
                    async: false,
                    success: function (json) {
                        $.messager.alert("提醒", json.msg);
                        var queryJSON = $("#contract-form-contractListPage").serializeJSON();
                        $("#contract-table-contractListPage").datagrid("load", queryJSON);
                    },
                    error: function () {
                        loaded();
                        $.messager.alert("错误", "反审出错");
                    }
                });
            }
        });
    });

    function refresh(){
        var queryJSON = $("#contract-form-contractListPage").serializeJSON();
        $("#contract-table-contractListPage").datagrid("load",queryJSON);
    }
</script>
</body>
</html>
