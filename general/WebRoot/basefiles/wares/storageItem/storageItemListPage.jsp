<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>仓库货位</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<div class="easyui-layout" title="" data-options="fit:true,border:true" id="basefiles-layout-customerBrandSettletype">
    <div data-options="region:'west',border:false" style="width:450px;">
        <div id="basefiles-button-storagelist" style="padding: 1px;">

            <form action="" method="post" id="basefiles-form-storagelist">
                <table>
                    <tr>
                        <td>所属仓库:</td>
                        <td><input type="text" id="basefiles-storageid-storageitem" name="id" /></td>
                        <td>商品:</td>
                        <td><input type="text" id="basefiles-goodsid-storageitem" name="goodsid" /></td>
                    </tr>
                    <tr>
                        <td colspan="3" style="text-align: right;">
                            <a href="javaScript:void(0);" id="basefiles-query-storagelist" class="button-qr" >查询</a>
                            <a href="javaScript:void(0);" id="basefiles-reload-storagelist" class="button-qr">重置</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <table id="basefiles-table-storageitemlist"></table>
    </div>
    <div data-options="region:'center',border:false">
        <table id="basefiles-table-storageitemgoodsList"></table>
        <div id="basefiles-button-brand-div" >
            <security:authorize url="/basefiles/storageItemGoodsAddBtn.do">
                <a href="javaScript:void(0);" id="basefiles-add-storageItemGoods" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'" title="新增">新增</a>
            </security:authorize>
            <security:authorize url="/basefiles/storageItemGoodsEditBtn.do">
                <a href="javaScript:void(0);" id="basefiles-edit-storageItemGoods" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-edit'" title="修改">修改</a>
            </security:authorize>
            <security:authorize url="/basefiles/storageItemGoodsDelBtn.do">
                <a href="javaScript:void(0);" id="basefiles-delete-storageItemGoods" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-delete'" title="删除">删除</a>
            </security:authorize>
            <security:authorize url="/basefiles/storageItemGoodsExportBtn.do">
                <a href="javaScript:void(0);" id="basefiles-export-storageItemGoods" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-export'" title="导出">导出</a>
            </security:authorize>
            <security:authorize url="/basefiles/storageItemGoodsImportBtn.do">
                <a href="javaScript:void(0);" id="basefiles-import-storageItemGoods" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-import'" title="导入">导入</a>
            </security:authorize>
            <form action="" method="post" id="basefiles-form-goodsform">
                <input type="hidden" name="storageid" id="basefiles-inout-exportstorageid"/>
                <table>
                    <tr>
                        <td>商品:</td>
                        <td><input type="text" id="basefiles-goodsid-goodsform" name="goodsid" /></td>
                        <td>货位:</td>
                        <td><input type="text" name="itemno" /></td>
                        <td colspan="3" style="text-align: right;">
                            <a href="javaScript:void(0);" id="basefiles-query-goodslist" class="button-qr" >查询</a>
                            <a href="javaScript:void(0);" id="basefiles-reload-goodslist" class="button-qr">重置</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>

    </div>
</div>
<div id="basefiles-dialog-storageItemAdd"></div>
<div id="basefiles-dialog-storageItemEdit"></div>
<script type="text/javascript">
    var $storageItemGoodsList = $('#basefiles-table-storageitemgoodsList');
    $(function(){
        //仓库货位列表
        $('#basefiles-table-storageitemlist').datagrid({
            fit:true,
            title:'仓库货位列表',
            method:'post',
            rownumbers:true,
            pagination:true,
            idField:'id',
            pageSize:100,
            singleSelect:true,
            checkOnSelect:true,
            selectOnCheck:true,
            toolbar:"#basefiles-button-storagelist",
            frozenColumns:[[{field:'ck',width:60,checkbox:true}]],
            columns:[[
                {field:'id',title:'仓库编码',width:70,sortable:true},
                {field:'name',title:'仓库名称',width:150,sortable:true},
                {field:'storagetypename',title:'仓库类型',width:70,sortable:true},
            ]],
            onClickRow:function(index,row){
                var storageid = row.id;
                $("#basefiles-inout-exportstorageid").val(storageid);
                $('#basefiles-table-storageitemgoodsList').datagrid({
                    pageNumber:1,
                    url: 'basefiles/showStorageItemGoodsList.do?storageid='+storageid
                });
            }
        });
        //商品列表
        $('#basefiles-table-storageitemgoodsList').datagrid({
            fit:true,
            title:'商品列表',
            method:'post',
            rownumbers:true,
            singleSelect:true,
            toolbar:"#basefiles-button-brand-div",
            columns:[[
                {field:'ck',checkbox:true},
                {field:'id',title:'编码',hidden:true},
                {field:'goodsid',title:'商品编码',width:80},
                {field:'goodsname',title:'商品名称',width:180},
                {field:'itemno',title:'货位名称',width:80},
                {field:'storageid',title:'仓库编码',width:80,hidden:true}
            ]],
            onDblClickRow: function(index, field, value){

            },
            onClickRow: function(index, field, value){

            }
        });

        //所属仓库
        $("#basefiles-storageid-storageitem").widget({
            referwid:'RL_T_BASE_STORAGE_INFO',
            singleSelect:true,
            width:110,
            onlyLeafCheck:true
        });
        $("#basefiles-goodsid-storageitem").widget({
            referwid:'RL_T_BASE_GOODS_INFO',
            singleSelect:true,
            width:110,
            onlyLeafCheck:false
        });

        $("#basefiles-goodsid-goodsform").widget({
            referwid:'RL_T_BASE_GOODS_INFO',
            singleSelect:true,
            width:110,
            onlyLeafCheck:false
        });
        $("#sale-brand-customerlist").widget({
            referwid:'RL_T_BASE_GOODS_BRAND',
            singleSelect:true,
            width:110,
            onlyLeafCheck:false
        });

        //查询
        $("#basefiles-query-storagelist").click(function(){
            var queryJSON = $("#basefiles-form-storagelist").serializeJSON();
            $('#basefiles-table-storageitemlist').datagrid('options').url = 'basefiles/getStorageList.do';
            $("#basefiles-table-storageitemlist").datagrid("load",queryJSON);
        });

        //重置
        $("#basefiles-reload-storagelist").click(function(){
            $("#basefiles-form-storagelist")[0].reset();
            $("#basefiles-goodsid-storageitem").widget('clear');
            $("#basefiles-storageid-storageitem").widget('clear');
            $('#basefiles-table-storageitemlist').datagrid('loadData', { total: 0, rows: [] });
        });

        //新增
        $("#basefiles-add-storageItemGoods").click(function(){
            var row=$("#basefiles-table-storageitemlist").datagrid("getSelected");
            if(row==null){
                $.messager.alert("提醒","请选择仓库！");
                return false;
            }
            $('<div id="basefiles-dialog-storageItemAdd1"></div>').appendTo('#basefiles-dialog-storageItemAdd');
            $('#basefiles-dialog-storageItemAdd1').dialog({
                title:'仓库货位新增',
                width:400,
                height:250,
                closed:false,
                cache:false,
                maximizable:true,
                resizable:true,
                queryParams:{storageid:row.id},
//                fit:true,
                href:'basefiles/showStorageItemGoodsAddPage.do',
                onClose:function(){
                    $("#basefiles-dialog-storageItemAdd1").dialog('destroy');
                }
            });
        });

        //修改
        $("#basefiles-edit-storageItemGoods").click(function(){
            var row=$("#basefiles-table-storageitemgoodsList").datagrid("getSelected");
            if(row==null){
                $.messager.alert("提醒","请选择商品货位！");
                return false;
            }
            $('<div id="basefiles-dialog-storageItemEdit1"></div>').appendTo('#basefiles-dialog-storageItemEdit');
            $('#basefiles-dialog-storageItemEdit1').dialog({
                title:'仓库货位修改',
                width:400,
                height:250,
                closed:false,
                cache:false,
                maximizable:true,
                resizable:true,
//                fit:true,
                href:'basefiles/showStorageItemGoodsEditPage.do?id='+row.id+"&storageid="+row.storageid+"&goodsid="+encodeURIComponent(row.goodsid),
                onClose:function(){
                    $("#basefiles-dialog-storageItemEdit1").dialog('destroy');
                }
            });
        });

        //删除
        $("#basefiles-delete-storageItemGoods").click(function(){
            var rows = $storageItemGoodsList.datagrid('getChecked');
            if(null == rows || rows.length == 0){
                $.messager.alert("提醒","请勾选要删除的仓库货位！");
                return false;
            }
            var idstr = "";
            var storageid=rows[0].storageid;
            for(var i=0;i<rows.length;i++){
                if(idstr == ""){
                    idstr = rows[i].goodsid;
                }else{
                    idstr += "," + rows[i].goodsid;
                }
            }
            $.messager.confirm("提醒","是否删除仓库货位?",function(r){
                if(r){
                    loading("删除中..");
                    $.ajax({
                        url:'basefiles/deleteStorageItemGoods.do',
                        data:{idstr:idstr,storageid:storageid},
                        dataType:'json',
                        type:'post',
                        success:function(json){
                            loaded();
                            $.messager.alert("提醒","删除成功");
                            $storageItemGoodsList.datagrid('reload');
                        }
                    });
                }
            });
        });

        //查询
        $("#basefiles-query-goodslist").click(function(){
            var queryJSON = $("#basefiles-form-goodsform").serializeJSON();
            $('#basefiles-table-storageitemgoodsList').datagrid('options').url = 'basefiles/showStorageItemGoodsList.do';
            $("#basefiles-table-storageitemgoodsList").datagrid("load",queryJSON);
        });

        //重置
        $("#basefiles-reload-goodslist").click(function(){
            $("#basefiles-form-goodsform")[0].reset();
            $("#basefiles-goodsid-goodsform").widget('clear');
            $('#basefiles-table-storageitemgoodsList').datagrid('loadData', { total: 0, rows: [] });
        });


        $("#basefiles-import-storageItemGoods").Excel('import',{
            type:'importUserdefined',
            url:'basefiles/importStorageItemGoods.do',
//            importparam:'客户编码、品牌编码、结算方式必输',
            onClose: function(){ //导入成功后窗口关闭时操作，
                $("#basefiles-table-storageitemgoodsList").datagrid("reload");
            }
        });

        //导出
        $("#basefiles-export-storageItemGoods").Excel('export',{
            queryForm: "#basefiles-form-goodsform", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
            type:'exportUserdefined',
            url:'basefiles/exportStorageItemGoods.do',
            name:'仓库商品货位列表'
        });


    });
</script>
</body>
</html>
