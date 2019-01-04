<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>客户费用合同条款</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<table id="contractCaluse-table"></table>

<div id="contractCaluse-toolbar" style="padding:0px;height:auto">
    <div class="buttonBG">
        <security:authorize url="/journalsheet/contractcaluse/showContractCaluseAddPage.do">
        <a href="javaScript:void(0);" id="contractCaluse-button-add" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'">新增</a>
        </security:authorize>
        <security:authorize url="/journalsheet/contractcaluse/deleteContractCaluse.do">
            <a href="javaScript:void(0);" id="contractCaluse-button-delete" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-delete'">删除</a>
        </security:authorize>
        <security:authorize url="/journalsheet/contractcaluse/openContractCaluse.do">
        <a href="javaScript:void(0);" id="contractCaluse-button-open" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-open'">启用</a>
        </security:authorize>
        <security:authorize url="/journalsheet/contractcaluse/closeContractCaluse.do">
        <a href="javaScript:void(0);" id="contractCaluse-button-close" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-close'">禁用</a>
        </security:authorize>
    </div>
    <form action="" method="post" id="contractCaluse-form">
        <table class="querytable">
            <tr>
                <td>条款编号:</td>
                <td>
                    <input type="text"  name="id" style="width:220px"/>
                </td>
                <td>条款名称:</td>
                <td>
                    <input type="text"  name="name" style="width:220px"/>
                </td>
                <td colspan="2">
                    <a href="javaScript:void(0);" id="contractCaluse-queay-queryList" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="contractCaluse-queay-reloadList" class="button-qr">重置</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<div id="contractCaluse-dialog"></div>
<script type="text/javascript">
    var initQueryJSON = $("#contractCaluse-form").serializeJSON();
    var actiontype="";
    var contractCaluseJson = $("#contractCaluse-table").createGridColumnLoad({
        name :'',
        frozenCol : [[
        ]],
        commonCol : [[
            {field: 'ck', checkbox: true},
            {field:'id',title:'条款编号',width:140,sortable:true},
            {field:'name',title:'条款名称',width:100,sortable:true},
            {field:'type',title:'条款类型',width:100,sortable:true,
                formatter: function (value, row, index) {
                    return row.typename;
                }
            },
            {field:'costtype',title:'费用类型',width:100,sortable:true,
                formatter: function (val, rowData, rowIndex) {
                    if ('0'==val) {
                        return "可变费用";
                    }else if('1'==val){
                        return "固定费用";
                    }else{
                        return "";
                    }
                }
            },
            {field:'sharetype',title:'分摊方式',width:100,sortable:true,
                formatter: function (val, rowData, rowIndex) {
                    if ('0'==val) {
                        return "一次性分摊";
                    }else if('1'==val){
                        return "分月平摊";
                    }else{
                        return "";
                    }
                }
            },
            {field:'returntype',title:'支付类型',width:100,sortable:true,
                formatter: function (val, rowData, rowIndex) {
                    if ('0'==val) {
                        return "月返";
                    }else if('1'==val){
                        return "季返";
                    }else if('2'==val){
                        return "年返";
                    }else{
                        return "";
                    }
                }
            },
            {field:'returnmonthnum',title:'支付月份',width:100,sortable:true},
            {field:'returnrequire',title:'支付要求',width:100,sortable:true,
                formatter: function (val, rowData, rowIndex) {
                    if ('0'==val) {
                        return "无要求";
                    }else if('1'==val){
                        return "销售达成";
                    }else{
                        return "";
                    }
                }
            },
            {field:'state',title:'状态',width:100,sortable:true,
                formatter: function (value, row, index) {
                    return row.statename;
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
        $("#contractCaluse-table").datagrid({
            authority:contractCaluseJson,
            frozenColumns: contractCaluseJson.frozen,
            columns:contractCaluseJson.common,
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
            url: 'journalsheet/contractcaluse/showContractCaluseListData.do',
            queryParams: initQueryJSON,
            toolbar:'#contractCaluse-toolbar',
            onDblClickRow: function(rowIndex, rowData){
                $(this).datagrid('clearSelections').datagrid('selectRow',rowIndex);
                if(rowData.id != undefined){
                    showContractCaluseEditPage();
                }
            },
        }).datagrid('columnMoving');

        $("#contractCaluse-addRow").click(function(){
            var flag = $("#contractCaluse-contextMenu").menu('getItem','#contractCaluse-addRow').disabled;
            if(flag){
                return false;
            }
            showContractCaluseAddPage();
        });
        $("#contractCaluse-editRow").click(function(){
            var flag = $("#contractCaluse-contextMenu").menu('getItem','#contractCaluse-editRow').disabled;
            if(flag){
                return false;
            }
            showContractCaluseEditPage();
        });
        $("#contractCaluse-removeRow").click(function(){
            var flag = $("#contractCaluse-contextMenu").menu('getItem','#contractCaluse-removeRow').disabled;
            if(flag){
                return false;
            }
            removeContractCaluse();
        });
    });

    //查询
    $("#contractCaluse-queay-queryList").click(function(){
        //把form表单的name序列化成JSON对象
        var queryJSON = $("#contractCaluse-form").serializeJSON();
        $("#contractCaluse-table").datagrid("load",queryJSON);
    });
    //重置
    $("#contractCaluse-queay-reloadList").click(function(){
        $("#contractCaluse-form")[0].reset();
        var queryJSON = $("#contractCaluse-form").serializeJSON();
        $("#contractCaluse-table").datagrid("load",queryJSON);
    });

    //新增
    $("#contractCaluse-button-add").click(function(){
        $('<div id="contractCaluse-dialog-content"></div>').appendTo('#contractCaluse-dialog');
        $('#contractCaluse-dialog-content').dialog({
            title: '新增客户费用合同条款',
            width: 700,
            height: 300,
            collapsible:false,
            minimizable:false,
            maximizable:true,
            resizable:true,
            closed: true,
            cache: false,
            modal: true,
            href: 'journalsheet/contractcaluse/showContractCaluseAddPage.do',
            onLoad:function(){
            },
            onClose:function(){
                $("#contractCaluse-dialog-content").dialog("destroy");
            }
        });
        $('#contractCaluse-dialog-content').dialog("open");
    });

    //删除
    $("#contractCaluse-button-delete").click(function(){
        var rows = $("#contractCaluse-table").datagrid('getChecked');
        if (rows.length == 0) {
            $.messager.alert("提醒", "请选中需要作废的记录。");
            return false;
        }
        var ids = "";
        for (var i = 0; i < rows.length; i++) {
            ids += rows[i].id + ',';
        }
        $.messager.confirm("提醒","确定删除该条款?",function(r){
            if (r) {
                loading("数据处理中..");
                $.ajax({
                    url:'journalsheet/contractcaluse/deleteContractCaluse.do',
                    dataType:'json',
                    type:'post',
                    data:{ids:ids},
                    success:function(json){
                        loaded();
                        $.messager.alert("提醒", json.msg);
                        refresh();
                    },
                    error:function(){
                        loaded();
                        $.messager.alert("提醒","删除失败。");
                    }
                });
            }
        });
    });

    //启用
    $("#contractCaluse-button-open").click(function(){
        var rows = $("#contractCaluse-table").datagrid('getChecked');
        if (rows.length == 0) {
            $.messager.alert("提醒", "请选中需要作废的记录。");
            return false;
        }
        var ids = "";
        for (var i = 0; i < rows.length; i++) {
            ids += rows[i].id + ',';
        }
        $.messager.confirm("提醒","确定启用该条款?",function(r){
            if (r) {
                loading("数据处理中..");
                $.ajax({
                    url: 'journalsheet/contractcaluse/openContractCaluse.do',
                    dataType: 'json',
                    type: 'post',
                    data: {ids: ids},
                    success: function (json) {
                        loaded();
                        $.messager.alert("提醒", json.msg);
                        refresh();
                    },
                    error: function () {
                        loaded();
                        $.messager.alert("提醒", "启用失败。");
                    }
                });
            }
        });
    });

    //禁用
    $("#contractCaluse-button-close").click(function(){
        var rows = $("#contractCaluse-table").datagrid('getChecked');
        if (rows.length == 0) {
            $.messager.alert("提醒", "请选中需要作废的记录。");
            return false;
        }
        var ids = "";
        for (var i = 0; i < rows.length; i++) {
            ids += rows[i].id + ',';
        }
        $.messager.confirm("提醒","确定禁用该条款?",function(r){
            if (r) {
                loading("数据处理中..");
                $.ajax({
                    url: 'journalsheet/contractcaluse/closeContractCaluse.do',
                    dataType: 'json',
                    type: 'post',
                    data: {ids: ids},
                    success: function (json) {
                        loaded();
                        $.messager.alert("提醒", json.msg);
                        refresh();
                    },
                    error: function () {
                        loaded();
                        $.messager.alert("提醒", "禁用失败。");
                    }
                });
            }
        });
    });
    function showContractCaluseEditPage(){
        var row = $("#contractCaluse-table").datagrid('getSelected');
        if(row == null){
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        if(row.id == undefined){
            showContractCaluseAddPage();
        }else{
            $('<div id="contractCaluse-dialog-content"></div>').appendTo('#contractCaluse-dialog');
            $('#contractCaluse-dialog-content').dialog({
                title: '修改客户费用合同条款',
                width: 680,
                height: 320,
                collapsible:false,
                minimizable:false,
                maximizable:true,
                resizable:true,
                closed: true,
                cache: false,
                href: 'journalsheet/contractcaluse/showContractCaluseEditPage.do?id='+row.id,
                modal: true,
                onLoad:function(){

                },
                onClose:function(){
                    $('#contractCaluse-dialog-content').dialog("destroy");
                }
            });
            $('#contractCaluse-dialog-content').dialog("open");
        }
    }

    function showContractCaluseViewPage(){
        var row = $("#contractCaluse-table").datagrid('getSelected');
        if(row == null){
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        if(row.name == undefined){
            showContractCaluseAddPage();
        }else{
            $('<div id="contractCaluse-dialog-content"></div>').appendTo('#contractCaluse-dialog');
            $('#contractCaluse-dialog-content').dialog({
                title: '查看客户费用合同条款',
                width: 800,
                height: 320,
                collapsible:false,
                minimizable:false,
                maximizable:true,
                resizable:true,
                closed: true,
                cache: false,
                href: 'journalsheet/contractcaluse/showContractCaluseViewPage.do',
                modal: true,
                onLoad:function(){

                },
                onClose:function(){
                    $('#contractCaluse-dialog-content').dialog("destroy");
                }
            });
            $('#contractCaluse-dialog-content').dialog("open");
        }
    }

    //保存客户费用合同条款
    function addContractCaluse(goFlag) { //添加新数据确定后操作，
        var flag = $("#contractCaluse-form-addContractCaluse").form('validate');
        if (flag == false) {
            $.messager.alert("提醒", '请先完善条款信息');
            return false;
        }
        var sharetype = $("#contractCaluse-sharetype-addContractCaluse").val();
        var returntype = $("#contractCaluse-returntype-addContractCaluse").val();
        var returnmonthnum = $("#contractCaluse-returnmonthnum-addContractCaluse").val();
        if ("0" == sharetype && ("1" == returntype || "2" == returntype) && (""==returnmonthnum || null == returnmonthnum)) {
            $.messager.alert("提醒", '一次性分摊为季返或者年返时请填写支付月份！');
            return false;
        }
        $("#contractCaluse-form-addContractCaluse").attr("action", "journalsheet/contractcaluse/addContractCaluse.do");
        $("#contractCaluse-form-addContractCaluse").submit();
    }

    function editContractCaluse(){
        var flag = $("#contractCaluse-form-addContractCaluse").form('validate');
        if(flag==false){
            $.messager.alert("提醒", '请先完善条款信息');
            return false;
        }
        var sharetype = $("#contractCaluse-sharetype-addContractCaluse").val();
        var returntype = $("#contractCaluse-returntype-addContractCaluse").val();
        var returnmonthnum = $("#contractCaluse-returnmonthnum-addContractCaluse").val();
        if ("0" == sharetype && ("1" == returntype || "2" == returntype) && (""==returnmonthnum || null == returnmonthnum)) {
            $.messager.alert("提醒", '一次性分摊为季返或者年返时请填写支付月份！');
            return false;
        }
        $("#contractCaluse-form-addContractCaluse").attr("action", "journalsheet/contractcaluse/editContractCaluse.do");
        $("#contractCaluse-form-addContractCaluse").submit();
    }

    function refresh(){
        var queryJSON = $("#contractCaluse-form").serializeJSON();
        $("#contractCaluse-table").datagrid("load",queryJSON);
    }
</script>
</body>
</html>
