<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>供应商品牌结算方式</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<div class="easyui-layout" title="" data-options="fit:true,border:true" id="buy-layout-supplierBrandSettletype">
    <div data-options="region:'west',border:false" style="width:550px;">
        <div id="buy-button-supplierlist-settletype" style="padding: 1px;">
            <form action="" method="post" id="buy-form-custoemrlist">
                <table>
                    <tr>
                        <td>编码:</td>
                        <td><input type="text" name="id" style="width: 110px"/></td>
                        <td>供应商名称:</td>
                        <td colspan="3"><input type="text" name="name" style="width: 278px" /></td>
                    </tr>
                    <tr>
                        <td>所属区域:</td>
                        <td><input type="text" id="sale-buyarea-supplierlist" name="buyarea" /></td>
                        <td>品牌名称:</td>
                        <td><input type="text" id="sale-brand-supplierlist" name="brandid" /></td>
                        <td>所属分类:</td>
                        <td><input type="text" name="suppliersort" id="sale-suppliersort-supplierlist"/></td>
                    </tr>
                    <tr>
                        <td colspan="3"></td>
                        <td colspan="3" style="text-align: right;">
                            <a href="javaScript:void(0);" id="buy-query-supplierlist" class="button-qr" >查询</a>
                            <a href="javaScript:void(0);" id="buy-reload-supplierlist" class="button-qr">重置</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <table id="buy-table-supplierlist-settletype"></table>
    </div>
    <div data-options="region:'center',border:false">
        <table id="buy-table-brandsettletypelist"></table>
        <div id="buy-button-brand-div" class="buttonBG">
            <security:authorize url="/buy/customerBrandSettletypeAddBtn.do">
                <a href="javaScript:void(0);" id="buy-add-supplierBrandSettletype" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'" title="新增">新增</a>
            </security:authorize>
            <security:authorize url="/buy/customerBrandSettletypeSaveBtn.do">
                <a href="javaScript:void(0);" id="buy-save-supplierBrandSettletype" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'" title="保存">保存</a>
            </security:authorize>
            <security:authorize url="/buy/customerBrandSettletypeDelBtn.do">
                <a href="javaScript:void(0);" id="buy-delete-supplierBrandSettletype" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-delete'" title="删除">删除</a>
            </security:authorize>
            <security:authorize url="/buy/customerBrandSettletypeImportBtn.do">
                <a href="javaScript:void(0);" id="buy-import-supplierBrandSettletype" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-import'" title="导入">导入</a>
            </security:authorize>
        </div>
    </div>
</div>
<div id="buy-div-supplierBrandSettletype"></div>
<script type="text/javascript">
    var $brandSettletypeList = $('#buy-table-brandsettletypelist');
    var editIndex = undefined,rowindex=undefined;
    var editfiled = null;
    var clicksupplierid = "";
    function endEditing(){
        if (editIndex == undefined){
            return true
        }
        var row = $brandSettletypeList.datagrid('getRows')[editIndex];
        var settletype = row.settletype;
        var settleday = row.settleday;

        var ed = $brandSettletypeList.datagrid('getEditor', {index:editIndex,field:"settletype"});
        if(null != ed){
            settletype = $(ed.target).widget("getValue");
            var settletypename = $(ed.target).widget("getText");
            $brandSettletypeList.datagrid('getRows')[editIndex]['settletypename'] = settletypename;
            $(ed.target).focus();
            $(ed.target).select();
        }
        var ed2 = $brandSettletypeList.datagrid('getEditor', {index:editIndex,field:"settleday"});
        if(null != ed2){
            settleday = $(ed2.target).numberspinner('getValue');
//      getNumberBoxObject(ed2.target).focus();
//      getNumberBoxObject(ed2.target).select();
        }

        if(undefined != settletype && "" != settletype){
            $brandSettletypeList.datagrid('endEdit', editIndex);
        }else{
            return false;
        }

        if(row.initsettletype != settletype || row.initsettleday != settleday){
            row.isedit = "1";
        }else{
            row.isedit = "0";
        }
        $brandSettletypeList.datagrid('updateRow',{index:editIndex, row:row});
        if(row.isedit == "1"){
            $brandSettletypeList.datagrid('checkRow',editIndex);
        }else{
            $brandSettletypeList.datagrid('uncheckRow',editIndex);
        }
        editIndex = undefined;
        return true;
    }

    function onClickRow(index){
        if (endEditing()){
            editIndex = index;
            rowindex=editIndex;
            $brandSettletypeList.datagrid('beginEdit', editIndex);
        }
    }

    $(function(){
        //供应商列表
        $('#buy-table-supplierlist-settletype').datagrid({
            fit:true,
            title:'供应商列表',
            method:'post',
            rownumbers:true,
            pagination:true,
            idField:'id',
            pageSize:100,
            singleSelect:true,
            checkOnSelect:true,
            selectOnCheck:true,
            toolbar:"#buy-button-supplierlist-settletype",
            frozenColumns:[[{field:'ck',width:60,checkbox:true}]],
            columns:[[
                {field:'id',title:'编码',width:70,sortable:true},
                {field:'name',title:'供应商名称',width:120,sortable:true},
                {field:'settletype',title:'结算方式',width:80,sortable:true,
                    formatter: function(value,row,index){
                        return row.settletypename;
                    }
                },
                {field:'settleday',title:'每月结算日',width:80,sortable:true}
            ]],
            onClickRow:function(index,row){
                var supplierid = row.id;
                clicksupplierid = row.id;
                $('#buy-table-brandsettletypelist').datagrid({
                    pageNumber:1,
                    url: 'basefiles/showSupplierBrandSettletypeList.do?supplierid='+supplierid
                });
            }
        });
        //品牌结算方式
        $('#buy-table-brandsettletypelist').datagrid({
            fit:true,
            title:'供应商列表',
            method:'post',
            sortName:'brandid',
            sortOrder:'asc',
            rownumbers:true,
            singleSelect:true,
            toolbar:"#buy-button-brand-div",
            columns:[[
                {field:'ck',checkbox:true},
                {field:'id',title:'编码',hidden:true},
                {field:'brandid',title:'品牌编码',width:80},
                {field:'brandname',title:'品牌名称',width:80},
                {field:'settletype',title:'结算方式',width:120,
                    formatter: function(value,row,index){
                        return row.settletypename;
                    },
                    editor:{
                        type:'comborefer',
                        options:{
                            referwid:'RL_T_BASE_FINANCE_SETTLEMENT',
                            singleSelect:true,
                            width:120,
                            required:true,
                            onlyLeafCheck:false,
                            onSelect: function (data) {
                                var row = $brandSettletypeList.datagrid('getSelected');
                                var ed2 = $brandSettletypeList.datagrid('getEditor', {index:editIndex,field:"settleday"});
                                if(null != ed2){
                                    var settleday = $(ed2.target).numberspinner("getValue");
                                    if(data.type == '2') {//日结
                                        $(ed2.target).numberspinner("clear");
                                    }else if(data.type == '1'){//月结
                                        if(data.id != row.initsettletype){
                                            $(ed2.target).numberspinner("setValue",data.days);
                                        }
                                    }
                                }
                            }
                        }
                    }
                },
                {field:'settleday',title:'每月结算日',width:90,
                    editor:{
                        type:'numberspinner',
                        options:{
                            required:false,
                            precision:0,
                            max:31,
                            min:1
                        }
                    }
                }
            ]],
            onDblClickRow: function(index, field, value){
                <security:authorize url="/buy/customerBrandSettletypeEditBtn.do">
                onClickRow(index);
                </security:authorize>
            },
            onClickRow: function(index, field, value){
                endEditing();
            }
        });

        $(document).die("keydown").live("keydown",function(event){
            if(event.ctrlKey){
                console.log(editIndex);
                editIndex=rowindex;
                $brandSettletypeList.datagrid('clearSelections');
                $brandSettletypeList.datagrid('selectRow',editIndex+1);
                onClickRow(editIndex+1);
            }
        });

        //客户分类
        $("#sale-suppliersort-supplierlist").widget({
            referwid:'RT_T_BASE_BUY_SUPPLIER_SORT',
            singleSelect:true,
            width:100,
            onlyLeafCheck:true
        });
        $("#sale-buyarea-supplierlist").widget({
            referwid:'RT_T_BASE_BUY_AREA',
            singleSelect:true,
            width:110,
            onlyLeafCheck:false
        });
        $("#sale-brand-supplierlist").widget({
            referwid:'RL_T_BASE_GOODS_BRAND',
            singleSelect:true,
            width:110,
            onlyLeafCheck:false
        });

        //查询
        $("#buy-query-supplierlist").click(function(){
            var queryJSON = $("#buy-form-custoemrlist").serializeJSON();
            $('#buy-table-supplierlist-settletype').datagrid('options').url = 'basefiles/getSupplierListForPact.do?type=settletype';
            $("#buy-table-supplierlist-settletype").datagrid("load",queryJSON);
        });

        //重置
        $("#buy-reload-supplierlist").click(function(){
            $("#buy-form-custoemrlist")[0].reset();
            $("#sale-suppliersort-supplierlist").widget('clear');
            $("#sale-buyarea-supplierlist").widget('clear');
            $("#sale-brand-supplierlist").widget('clear');
            $('#buy-table-supplierlist-settletype').datagrid('loadData', { total: 0, rows: [] });
        });

        //新增
        $("#buy-add-supplierBrandSettletype").click(function(){
            $("#buy-div-supplierBrandSettletype").dialog({
                title:'供应商品牌结算方式新增或覆盖',
                width:600,
                height:450,
                closed:false,
                modal:true,
                cache:false,
                maximizable:true,
                resizable:true,
                fit:true,
                href:'basefiles/showSupplierBrandSettletypeAddPage.do'
            });
        });

        //保存
        $("#buy-save-supplierBrandSettletype").click(function(){
            endEditing();
            var rows = $brandSettletypeList.datagrid('getChecked');
            for(var i=0;i<rows.length;i++){
                if(rows[i].isedit != "1"){
                    var index = $brandSettletypeList.datagrid('getRowIndex',rows[i]);
                    $brandSettletypeList.datagrid('uncheckRow',index);
                }
            }
            rows = $brandSettletypeList.datagrid('getChecked');
            if(rows.length == 0){
                $.messager.alert("提醒","请勾选已修改的数据!");
                return false;
            }
            loading('提交中...');
            $.ajax({
                url :'basefiles/editSupplierBrandSettletype.do',
                type:'post',
                dataType:'json',
                data:{rowsjsonstr:JSON.stringify(rows)},
                success:function(json){
                    loaded();
                    $.messager.alert("提醒",json.msg);
                    $brandSettletypeList.datagrid('reload');
                }
            });
        });

        //删除
        $("#buy-delete-supplierBrandSettletype").click(function(){
            var rows = $brandSettletypeList.datagrid('getChecked');
            if(null == rows || rows.length == 0){
                $.messager.alert("提醒","请勾选要删除的品牌结算方式！");
                return false;
            }
            var idstr = "";
            for(var i=0;i<rows.length;i++){
                if(idstr == ""){
                    idstr = rows[i].id;
                }else{
                    idstr += "," + rows[i].id;
                }
            }
            $.messager.confirm("提醒","是否删除品牌结算方式?",function(r){
                if(r){
                    loading("删除中..");
                    $.ajax({
                        url:'basefiles/deleteSupplierBrandSettletypes.do',
                        data:{idstr:idstr,supplierid:clicksupplierid},
                        dataType:'json',
                        type:'post',
                        success:function(json){
                            loaded();
                            $.messager.alert("提醒",json.msg);
                            $brandSettletypeList.datagrid('reload');
                        }
                    });
                }
            });
        });

        $("#buy-import-supplierBrandSettletype").Excel('import',{
            type:'importUserdefined',
            url:'basefiles/importSupplierBrandSettletype.do',
            importparam:'供应商编码、品牌编码、结算方式必输',
            onClose: function(){ //导入成功后窗口关闭时操作，
                $("#buy-table-brandsettletypelist").datagrid("reload");
            }
        });

    });
</script>
</body>
</html>
