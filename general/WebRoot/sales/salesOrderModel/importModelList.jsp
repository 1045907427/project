<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>销售订单模板管理页面</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/uploadify/jquery.uploadify.js"></script>
    <link rel="stylesheet" href="css/icon-extend.css" type="text/css"></link>
    <link rel="stylesheet" href="js/uploadify/uploadify.css" type="text/css"></link>
</head>
<body>
    <div id="saleOrderModel-table-importModelBtn" style="padding: 0px">
        <div class="buttonBG" id="saleOrderModel-button-importModel"></div>
        <form id="saleOrderModel-form-importModelListQuery" method="post">
            <table>
                <tr>
                    <td style="padding-left: 10px;">客户分配类型:&nbsp;</td>
                    <td>
                        <select name="ctype" style="width:150px;"id="saleOrderModel-ctype-importModelListQuery" >
                            <option value=""></option>
                            <option value="1">指定客户编号</option>
                            <option value="2">指定总店按店号分配</option>
                            <option value="3">按客户助记码分配</option>
                            <option value="4">按客户名称分配</option>
                            <option value="5">按客户简称分配</option>
                            <option value="6">按客户地址分配</option>
                        </select>
                    </td>
                    <td style="padding-left: 10px;">取价方式:&nbsp;</td>
                    <td>
                        <select name="pricetype" style="width:100px;" id="saleOrderModel-pricetype-importModelListQuery">
                            <option value=""></option>
                            <option value="1">取导入时模板价格</option>
                            <option value="0">取系统价格</option>
                        </select>
                    </td>
                    <td style="padding-left: 10px;">描述名:&nbsp;</td>
                    <td><input name="name"  type="text" id="saleOrderModel-name-importModelListQuery" style="width: 145px;"/></td>
                    <td>
                </tr>
                <tr>
                    <td style="padding-left: 10px;">商品导入类型:&nbsp;</td>
                    <td>
                        <select name="gtype" style="width:150px;"id="saleOrderModel-gtype-importModelListQuery" >
                            <option value=""></option>
                            <option value="1">按条形码导入</option>
                            <option value="2">按店内码导入</option>
                            <option value="3">按助记符导入</option>
                            <option value="4">按编码导入</option>
                        </select>
                    </td>
                    <td style="padding-left: 10px;">状&nbsp;&nbsp;态:&nbsp;</td>
                    <td>
                        <select name="state" style="width:100px;" id="saleOrderModel-state-importModelListQuery">
                            <option></option>
                            <option value="1"  selected="selected">启用</option>
                            <option value="0">禁用</option>
                        </select>
                    </td>
                    <td width="80px" align="left">模板类型:</td>
                    <td align="left">
                        <select name="modeltype" style="width: 145px;" id="importModel-modeltype-importModelListQuery" >
                            <option value=""></option>
                            <option value="1">销售订单模板</option>
                            <option value="2">代配送模板</option>
                            <option value="3">客户销量模板</option>
                            <option value="4">客户库存模板</option>
                            <option value="5">销售退货通知单</option>
                            <option value="6">采购退货通知单</option>
                        </select>
                    </td>
                    <td colspan="2"  style="padding-left: 10px;">
                        <a href="javaScript:void(0);" id="saleOrderModel-query-importModelList" class="button-qr">查询</a>
                        <a href="javaScript:void(0);" id="saleOrderModel-query-importModel-reloadList" class="button-qr">重置</a>
                    </td>
                </tr>
            </table>
        </form>
    </div>
    <table id="saleOrderModel-table-importModel"></table>
    <div style="display:none">
        <div id="saleOrderModel-dialog-add-operate"></div>
        <div id="saleOrderModel-dialog-edit-operate"></div>
        <div id="saleOrderModel-dialog-view-operate"></div>
        <div id="saleOrderModelDetail-dialog-operate"></div>
    </div>

<script type="text/javascript">
    $(function(){
        var initQueryJSON = $("#saleOrderModel-table-importModel").serializeJSON();
        $("#saleOrderModel-button-importModel").buttonWidget({
            initButton:[
                {}
            ],
            buttons:[
                <security:authorize url="/sales/importAddModel.do">
                {
                    id:'button-id-add',
                    name:'新增 ',
                    iconCls:'button-add',
                    handler:function(){
                        //saleOrderModelAddDialog('订单模板【新增】', 'sales/import/showImportModelAddPage.do');
                        top.addTab('sales/import/showImportAddPage.do', "订单模板新增");
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/importEditModel.do">
                {
                    id:'button-id-edit',
                    name:'修改 ',
                    iconCls:'button-edit',
                    handler:function(){
                        var dataRow=$("#saleOrderModel-table-importModel").datagrid('getSelected');
                        if(dataRow==null){
                            $.messager.alert("提醒","请选择相应的模板信息!");
                            return false;
                        }
                        saleOrderModelEditDialog('订单模板【修改】', 'sales/import/showImportModelEditPage.do?id='+dataRow.id);
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/importDeleteModel.do">
                {
                    id:'button-id-delete',
                    name:'删除',
                    iconCls:'button-delete',
                    handler:function(){
                        var dataRow=$("#saleOrderModel-table-importModel").datagrid('getSelected');
                        if(dataRow==null){
                            $.messager.alert("提醒","请选择一条模板记录!");
                            return false;
                        }
//                        if(dataRow.filepath != ""){
//                            $.messager.alert("提醒","抱歉，指定模板文件后不能删除!");
//                            return false;
//                        }
                        $.messager.confirm("提醒","是否删除该模板信息？",function(r){
                            if(r){
                                loading("删除中..");
                                $.ajax({
                                    url :'sales/import/deleteImportModel.do?id='+ dataRow.id,
                                    type:'post',
                                    dataType:'json',
                                    success:function(json){
                                        loaded();
                                        if(json.flag){
                                            $.messager.alert("提醒","删除成功");
                                            $("#saleOrderModel-query-importModelList").trigger("click");
                                        }else{
                                            if(json.msg){
                                                $.messager.alert("提醒","删除失败!"+json.msg);
                                            }else{
                                                $.messager.alert("提醒","删除失败!");
                                            }
                                        }
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/importEnableModel.do">
                {
                    id:'button-id-enable',
                    name:'启用 ',
                    iconCls:'button-open',
                    handler:function(){
                        var dataRow=$("#saleOrderModel-table-importModel").datagrid('getSelected');
                        if(dataRow==null){
                            $.messager.alert("提醒","请选择相应的模板文件!");
                            return false;
                        }
                        if(dataRow.state=='1'){
                            $.messager.alert("提醒","抱歉，启用的模板不能被启用!");
                            return false;
                        }
                        $.messager.confirm("提醒","是否启用该模板文件？",function(r){
                            if(r){
                                loading("启用中..");
                                $.ajax({
                                    url :'sales/import/enableImportModel.do?id='+ dataRow.id,
                                    type:'post',
                                    dataType:'json',
                                    success:function(json){
                                        loaded();
                                        if(json.flag){
                                            $.messager.alert("提醒","启用成功");
                                            $("#saleOrderModel-query-importModelList").trigger("click");
                                        }else{
                                            if(json.msg){
                                                $.messager.alert("提醒","启用失败!"+json.msg);
                                            }else{
                                                $.messager.alert("提醒","启用失败!");
                                            }
                                        }
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/importCloseModel.do">
                {
                    id:'button-id-disable',
                    name:'禁用 ',
                    iconCls:'button-close',
                    handler:function(){
                        var dataRow=$("#saleOrderModel-table-importModel").datagrid('getSelected');
                        if(dataRow==null){
                            $.messager.alert("提醒","请选择相应的模板文件!");
                            return false;
                        }
                        if(dataRow.state=='0'){
                            $.messager.alert("提醒","抱歉，禁用的模板不能被禁用!");
                            return false;
                        }
                        $.messager.confirm("提醒","是否禁用该模板文件？",function(r){
                            if(r){
                                loading("禁用中..");
                                $.ajax({
                                    url :'sales/import/disableImportModel.do?id='+ dataRow.id,
                                    type:'post',
                                    dataType:'json',
                                    success:function(json){
                                        loaded();
                                        if(json.flag){
                                            $.messager.alert("提醒","禁用成功");
                                            $("#saleOrderModel-query-importModelList").trigger("click");
                                        }else{
                                            if(json.msg){
                                                $.messager.alert("提醒","禁用失败!"+json.msg);
                                            }else{
                                                $.messager.alert("提醒","禁用失败!");
                                            }
                                        }
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                {}
            ],
            model:'base',
            type:'list',
            datagrid:'saleOrderModel-table-importModel',
            tname:'t_sales_import_set'
        });

        function saleOrderModelAddDialog(title, url){
            $('#saleOrderModel-dialog-add-operate').dialog({
                title: title,
                width: 400,
                height: 450,
                closed: false,
                cache: false,
                href: url,
                maximizable:true,
                resizable:true,
                modal: true,
                onLoad:function(){
                }
            });
            $('#saleOrderModel-dialog-add-operate').dialog('open');
        }

        function saleOrderModelEditDialog(title, url){
            $('#saleOrderModel-dialog-edit-operate').dialog({
                title: title,
                width: 500,
                height: 450,
                closed: true,
                cache: false,
                href: url,
                maximizable:true,
                resizable:true,
                modal: true,
                onLoad:function(){
                }
            });
            $('#saleOrderModel-dialog-edit-operate').dialog('open');
        }

        function saleOrderModelViewDialog(title, url){
            $('#saleOrderModel-dialog-view-operate').dialog({
                title: title,
                width: 500,
                height: 450,
                closed: true,
                cache: false,
                href: url,
                maximizable:true,
                resizable:true,
                modal: true,
                onLoad:function(){
                }
            });
            $('#saleOrderModel-dialog-view-operate').dialog('open');
        }

        var importModelJson = $("#saleOrderModel-table-importModel").createGridColumnLoad({
            frozenCol : [[{field:'idok',checkbox:true,isShow:true},]],
            commonCol :[[
                    {field:'id',title:'序号',width:50,sortable:true,hidden:true},
                    {field:'name',title:'订单模板描述名',width:120},
                    {field:'url',title:'订单模版url',width:200 },
                    {field: 'modeltype', title: '模板类型', width: 90,
                        formatter: function (value, rowData, rowIndex) {
                            if (value == 1) {
                                return "销售订单模板";
                            } else if (value == 2) {
                                return "代配送模板";
                            }else if (value == 3){
                                return "客户销量模板";
                            }else if (value == 4){
                                return "客户库存模板";
                            }else if (value == 5){
                                return "销售退货通知单";
                            }else if(value == 6){
                                return "采购退货通知单";
                            }
                        }
                    },
                    {field:'ctype',title:'客户分配类型',width:120,
                        formatter:function(value,rowData,rowIndex){
                            if(value==1){
                                return "指定客户编号";
                            }else if(value==2){
                                return "指定总店按店号分配";
                            }else if(value==3){
                                return "按客户助记码导入";
                            }else if(value==4){
                                return "按客户名称导入";
                            }else if(value==5){
                                return "按客户简称导入";
                            }else if(value==6){
                                return "按客户地址导入";
                            }else if(value==7){
                                return "按客户编码导入";
                            }else{
                                return "读取文件客户";
                            }
                        }
                    },
                    {field:'gtype',title:'商品导入类型',width:100,
                        formatter:function(value,rowData,rowIndex){
                            if(value==1){
                                return "按条形码导入";
                            }else if(value==2){
                                return "按店内码导入";
                            }else if(value==3){
                                return "按助记符导入";
                            }else if(value==4){
                                return "按编码导入";
                            }else{
                                return "" ;
                            }
                        }
                    },
                    {field:'busid',title:'客户编码',width:80 },
                    {field:'remark',title:'自适应客户类型',hidden:true,width:150 },
                    {field:'pricetype', title: '取价方式', width: 100,
                        formatter: function (value, rowData, rowIndex) {
                            if(value=="1"){
                                return "取导入时模板价格";
                            }else{
                                return "取系统价格";
                            }
                        }
                    },
                    {field:'filepath',title:'模版路径（相对路径）',width:200,hidden:true},
                    {field:'state',title:'状态',width:35,
                        formatter:function(value,rowData,rowIndex){
                            if(value=="1"){
                                return "启用";
                            }else{
                                return "禁用";
                            }
                        }
                    },
                    {field:'addusername',title:'创建人',width:100,hidden:true},
                    {field:'addtime',title:'创建时间',width:120},
                    {field:'modifyusername',title:'修改人',width:100,hidden:true},
                    {field:'modifytime',title:'修改时间',width:80}

                ]]
        });

        $("#saleOrderModel-query-importModelList").click(function(){
            var QueryJSON = $("#saleOrderModel-form-importModelListQuery").serializeJSON();
            $("#saleOrderModel-table-importModel").datagrid('load', QueryJSON);
        });

        var initQueryJSON = $("#saleOrderModel-form-importModelListQuery").serializeJSON();
        $("#saleOrderModel-query-importModel-reloadList").click(function(){
            $("#saleOrderModel-ctype-importModelListQuery").val("");
            $("#saleOrderModel-gtype-importModelListQuery").val("");
            $("#saleOrderModel-state-importModelListQuery").val("1");
            $("#saleOrderModel-name-importModelListQuery").val("");
            $("#importModel-modeltype-importModelListQuery").val("");
            $("#saleOrderModel-table-importModel").datagrid('load', initQueryJSON);
        });

        $("#saleOrderModel-table-importModel").datagrid({
            authority:importModelJson,
            frozenColumns:importModelJson.frozen,
            columns:importModelJson.common,
            fit:true,
            method:'post',
            rownumbers:true,
            pagination:true,
            idField:'id',
            singleSelect:true,
            checkOnSelect:true,
            selectOnCheck:true,
            queryParams:initQueryJSON,
            toolbar:'#saleOrderModel-table-importModelBtn',
            url: 'sales/import/showImportModelData.do',
            onDblClickRow:function(index, dataRow){
                saleOrderModelViewDialog('订单模板【查看】', 'sales/import/showImportModelViewPage.do?id='+dataRow.id);
            }
        }).datagrid("columnMoving");



    });

</script>
</body>
</html>
