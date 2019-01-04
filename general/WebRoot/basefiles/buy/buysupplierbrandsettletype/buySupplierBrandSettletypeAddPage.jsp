<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>供应商品牌结算方式新增页面</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'north',border:false" style="height: 50px;">
        <form action="" method="post" id="sales-form-supplierBrandSettletypeAdd">
            <table cellpadding="2" cellspacing="2" border="0">
                <tr>
                    <td>供应商：</td>
                    <td><input type="text" id="sales-supplierid-supplierBrandSettletypeAdd" name="buySupplierBrandSettletype.supplierid"/></td>
                    <td>结算方式：</td>
                    <td>
                        <input type="text" id="sales-settletype-supplierBrandSettletypeAdd" name="buySupplierBrandSettletype.settletype"/>
                        <input type="hidden" id="sales-brandids-supplierBrandSettletypeAdd" name="buySupplierBrandSettletype.brandids"/>
                    </td>
                    <td>每月结算日：</td>
                    <td><input id="sales-settleday-supplierBrandSettletypeAdd" name="buySupplierBrandSettletype.settleday" style="width: 128px;"></td>
                </tr>
            </table>
            <hr/>
        </form>
    </div>
    <div data-options="region:'center',border:false">
        <table id="sales-table-supplierBrandSettletypeAdd"></table>
    </div>
    <div data-options="region:'south',border:false">
        <div class="buttonDetailBG" style="text-align:right;">
            <input type="button" value="保存并继续新增" id="sales-savegoon-supplierBrandSettletypeAdd" />
            <input type="button" value="保存" id="sales-save-supplierBrandSettletypeAdd" />
        </div>
    </div>
</div>
<div id="sales-querybutton-supplierBrandSettletypeAdd" style="padding: 0px;">
    <form action=""  method="post" id="sales-form-queryBrand">
        <input type="hidden" name="supplierid" />
        <table cellpadding="2" cellspacing="2" border="0">
            <tr>
                <td>品牌：</td>
                <td> <input type="text" id="sales-brand-queryBrand" name="id"/> </td>
                <td>品牌部门：</td>
                <td> <input type="text" id="sales-deptid-queryBrand" name="deptid"/> </td>
                <td>
                    <a href="javaScript:void(0);" id="sales-form-queryBrand-querybutton" class="button-qr">查询</a>
                    <a href="javaScript:void(0);" id="sales-form-queryBrand-reloadbutton" class="button-qr">重置</a>
                </td>
            </tr>
        </table>
    </form>
</div>
<script type="text/javascript">
    //保存并继续新增
    function savegoon(){
        loading("提交中..");
        var rows = $("#sales-table-supplierBrandSettletypeAdd").datagrid('getChecked');
        var brandids = "";
        for(var i=0;i<rows.length;i++){
            if(brandids==''){
                brandids = rows[i].id;
            }else{
                brandids += ","+rows[i].id;
            }
        }
        $("#sales-brandids-supplierBrandSettletypeAdd").val(brandids);
        var queryJSON = $("#sales-form-supplierBrandSettletypeAdd").serializeJSON();
        loading('提交中...');
        $.ajax({
            url :'basefiles/addSupplierBrandSettletype.do',
            type:'post',
            dataType:'json',
            data:queryJSON,
            success:function(json){
                loaded();
                if(json.flag){
                    $.messager.alert("提醒","保存成功");
                    $("input[name='supplierid']").val('');
                    $("#sales-form-queryBrand")[0].reset();
                    $("#sales-brand-queryBrand").widget('clear');
                    $("#sales-deptid-queryBrand").widget('clear');

                    var query = $("#sales-form-queryBrand").serializeJSON();
                    $("#sales-table-supplierBrandSettletypeAdd").datagrid('load',query);

                    $("#sales-form-supplierBrandSettletypeAdd")[0].reset();

                    $("#sales-customerid-supplierBrandSettletypeAdd").widget('clear');
                    $("#buy-table-supplierlist-settletype").datagrid("reload");
                    $('#buy-table-brandsettletypelist').datagrid('loadData', { total: 0, rows: [] });
                }else{
                    $.messager.alert("提醒","保存失败");
                }
            }
        });
    }

    //新增
    function save(){
        var rows = $("#sales-table-supplierBrandSettletypeAdd").datagrid('getChecked');
        var brandids = "";
        for(var i=0;i<rows.length;i++){
            if(brandids==''){
                brandids = rows[i].id;
            }else{
                brandids += ","+rows[i].id;
            }
        }
        $("#sales-brandids-supplierBrandSettletypeAdd").val(brandids);
        var queryJSON = $("#sales-form-supplierBrandSettletypeAdd").serializeJSON();
        loading('提交中...');
        $.ajax({
            url :'basefiles/addSupplierBrandSettletype.do',
            type:'post',
            dataType:'json',
            data:queryJSON,
            success:function(json){
                loaded();
                if(json.flag){
                    $.messager.alert("提醒","保存成功");
                    $("#buy-div-supplierBrandSettletype").dialog("close");

                    $("#buy-table-supplierlist-settletype").datagrid("reload");
                    $('#buy-table-brandsettletypelist').datagrid('loadData', { total: 0, rows: [] });
                }else{
                    $.messager.alert("提醒","保存失败");
                }
            }
        });
    }
    $(function(){
        $("#sales-table-supplierBrandSettletypeAdd").datagrid({
            fit:true,
            method:'post',
            sortName:'id',
            sortOrder:'asc',
            rownumbers:true,
            singleSelect:false,
            checkOnSelect:true,
            selectOnCheck:true,
            pagination:true,
            pageSize:500,
            toolbar:"#sales-querybutton-supplierBrandSettletypeAdd",
            url:'basefiles/getBrandListPage.do',
            columns:[[
                {field:'ck',checkbox:true},
                {field:'id',title:'品牌编码'},
                {field:'name',title:'品牌名称',width:80},
                {field:'deptid',title:'所属部门',width:80,sortable:true,
                    formatter:function(val,rowData,rowIndex){
                        return rowData.deptName;
                    }
                },
                {field:'supplierid',title:'所属供应商',width:200,sortable:true,
                    formatter:function(val,rowData,rowIndex){
                        return rowData.supplierName;
                    }
                },
                {field:'remark',title:'备注',width:100}
            ]]
        });

        $("#sales-supplierid-supplierBrandSettletypeAdd").widget({ //供应商参照窗口
            referwid:'RL_T_BASE_BUY_SUPPLIER',
            singleSelect:true,
            width:154,
            required:true,
            onlyLeafCheck:false,
            onSelect:function(data){
                $("input[name='supplierid']").val(data.id);
                $("#sales-form-queryBrand-querybutton").click();
            },
            onClear:function(){
                $("input[name='supplierid']").val('');
                $("#sales-form-queryBrand-querybutton").click();
            }
        });
        $("#sales-deptid-queryBrand").widget({
            referwid:'RL_T_BASE_DEPARTMENT_BUYER',
            singleSelect:true,
            width:130 ,
            onlyLeafCheck:false
        });

        //结算方式
        $("#sales-settletype-supplierBrandSettletypeAdd").widget({ //结算方式参照窗口
            referwid:'RL_T_BASE_FINANCE_SETTLEMENT',
            singleSelect:true,
            width:142,
            required:true,
            onlyLeafCheck:false,
            onSelect:function(data){
                if(data.type == '1'){//月结
                    $("#sales-settleday-supplierBrandSettletypeAdd").numberspinner({
                        disabled:false,
                        precision:0,
                        max:31,
                        min:1,
                        required:true
                    });
                }else{
                    $("#sales-settleday-supplierBrandSettletypeAdd").numberspinner({
                        disabled:true,
                        precision:0,
                        max:31,
                        min:1,
                        required:false
                    });
                }
            }
        });

        $("#sales-brand-queryBrand").widget({
            referwid:'RL_T_BASE_GOODS_BRAND',
            singleSelect:true,
            width:130 ,
            onlyLeafCheck:false
        });

        $("#sales-form-queryBrand-querybutton").click(function(){
            var queryJSON = $("#sales-form-queryBrand").serializeJSON();
            $("#sales-table-supplierBrandSettletypeAdd").datagrid("load",queryJSON);
        });
        $("#sales-form-queryBrand-reloadbutton").click(function(){
//            $("input[name='supplierid']").val('');
            $("#sales-brand-queryBrand").widget("clear");
            $("#sales-deptid-queryBrand").widget("clear");
//            $("#sales-supplierid-queryBrand").widget("clear");
            $("#sales-form-queryBrand").form("reset");
            var queryJSON = $("#sales-form-queryBrand").serializeJSON();
            $("#sales-table-supplierBrandSettletypeAdd").datagrid("load",queryJSON);
        });

        //保存并继续新增
        $("#sales-savegoon-supplierBrandSettletypeAdd").click(function(){
            if(!$("#sales-form-supplierBrandSettletypeAdd").form('validate')){
                $.messager.alert('提醒',"有必填项未填写!");
                return false;
            }
            $.messager.confirm("提醒","是否保存?",function(r){
                if(r){
                    savegoon();
                }
            });
        });
        //新增
        $("#sales-save-supplierBrandSettletypeAdd").click(function(){
            if(!$("#sales-form-supplierBrandSettletypeAdd").form('validate')){
                $.messager.alert('提醒',"有必填项未填写!");
                return false;
            }
            $.messager.confirm("提醒","是否保存?",function(r){
                if(r){
                    save();
                }
            });
        });
    });
</script>
</body>
</html>
