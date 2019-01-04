<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>合同商品和客户商品页面</title>
    <%@include file="/include.jsp" %>
</head>

<body>

<div class="easyui-layout" title="" data-options="fit:true,border:true" id="sales-layout-goodsandpricecustomer">
    <div data-options="region:'west',border:false" title="客户列表" style="width:400px;">
        <div id="sales-button-custoemrlist" >
            <form action="" method="post" id="sales-form-custoemrlist">
                <table>
                    <tr>
                        <td style="width: 50px">编&nbsp;&nbsp;码:</td>
                        <td><input type="text" name="id" style="width: 110px"/></td>
                        <td>客户名称:</td>
                        <td><input type="text" name="name" style="width: 130px"/></td>
                    </tr>
                    <tr>
                        <td style="width: 50px">商&nbsp;&nbsp;品:</td>
                        <td colspan="3">
                            <input type="text" name="goodsid" id="sale-pricecustomer-goodswidget" style="width: 230px" />
                            <security:authorize url="/basefiles/goodsAndPriceCustmerMuEditBtn.do">
                                <a href="javaScript:void(0);" id="sales-editmore-goodsandpricecustmer" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-edit'" title="批量修改合同商品">批量修改</a>
                            </security:authorize>
                        </td>
                    </tr>
                    <tr>
                        <td style="width: 60px">客户分类:</td>
                        <td colspan="3"><input type="text" name="customersort" id="sale-customersort-customerlist"/>
                            <a href="javaScript:void(0);" id="sales-query-customerlist" class="button-qr">查询</a>
                            <a href="javaScript:void(0);" id="sales-reload-customerlist" class="button-qr">重置</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <table id="sales-table-customerlist"></table>
    </div>

    <div data-options="region:'center',border:false" style="border-left: solid 3px #b3cae9;">
        <div id="sales-tabs-goodsandpricecustmer" class="easyui-tabs" data-options="fit:true,border:false" style="position:relative">
            <div title="合同商品">
                <div class="easyui-layout" title="" data-options="fit:true,border:false">
                    <div data-options="region:'center',border:false">
                        <div id="sales-button-goodsandpricecustmer" style="padding:2px;height:auto">
                            <security:authorize url="/basefiles/goodsAndPriceCustmerAddBtn.do">
                                <a href="javaScript:void(0);" id="sales-add-goodsandpricecustmer" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'" title="新增合同商品">新增</a>
                            </security:authorize>
                            <security:authorize url="/basefiles/goodsAndPriceCustmerCopyBtn.do">
                                <a href="javaScript:void(0);" id="sales-copy-goodsandpricecustmer" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-copy'" title="复制合同商品">复制</a>
                            </security:authorize>
                            <security:authorize url="/basefiles/goodsAndPriceCustmerSaveBtn.do">
                                <a href="javaScript:void(0);" id="sales-save-goodsandpricecustmer" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'" title="保存合同商品">保存</a>
                            </security:authorize>
                            <security:authorize url="/basefiles/goodsAndPriceCustmerEditBtn.do">
                                <a href="javaScript:void(0);" id="sales-edit-goodsandpricecustmer" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-edit'" title="修改合同商品">修改</a>
                            </security:authorize>
                            <security:authorize url="/basefiles/goodsAndPriceCustmerDelBtn.do">
                                <a href="javaScript:void(0);" id="sales-delete-goodsandpricecustmer" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-delete'" title="删除合同商品">删除</a>
                            </security:authorize>
                            <security:authorize url="/basefiles/goodsAndPriceCustmerImportBtn.do">
                                <a href="javaScript:void(0);" id="sales-import-goodsandpricecustmer" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-import'" title="导入合同商品">导入</a>
                            </security:authorize>
                            <security:authorize url="/basefiles/goodsAndPriceCustmerExportBtn.do">
                                <a href="javaScript:void(0);" id="sales-export-goodsandpricecustmer" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-export'" title="导入合同商品">导出</a>
                            </security:authorize>
                            <form action="" method="post" id="sales-query-goodsandpricecustmer">
                                <input type="hidden" name="customerid" id="sales-customerid-goodsandpricecustmer"/>
                                <!-- <input type="hidden" name="goodsid" id="sales-goodsid-goodsandpricecustmer"/> -->
                                <table>
                                    <tr>
                                        <td>商品:</td>
                                        <td><input type="text" name="goodsid" id="sale-goodsandprice-goodsid" style="width: 160px;"/></td>
                                        <td>品牌:</td>
                                        <td><input type="text" name="brandid" id="sale-goodsandprice-brand"/></td>
                                    </tr>
                                    <tr>
                                        <td>商品分类:</td>
                                        <td><input type="text" name="goodssort" id="sale-goodsandprice-goodssort"/></td>
                                        <td align="right" colspan="4">
                                            <a href="javaScript:void(0);" id="sales-query-goodsandprice" class="button-qr">查询</a>
                                            <a href="javaScript:void(0);" id="sales-reload-goodsandprice" class="button-qr">重置</a>
                                        </td>
                                    </tr>
                                </table>
                            </form>
                        </div>
                        <table id="sales-table-goodsandpricecustmer"></table>
                    </div>
                </div>
            </div>
            <div title="客户商品">
                <div class="easyui-layout" title="" data-options="fit:true,border:false">
                    <div data-options="region:'center',border:false">
                        <div id="sales-button-goodscustmer" style="padding:2px;height:auto">
                            <form action="" method="post" id="sales-form-custoemrgoodslist">
                                <table class="querytable">
                                    <tr>
                                        <td>商品:</td>
                                        <td><input type="text" name="customergoodsid" id="sale-goodscustmer-goodswidget"/>
                                            <input type="hidden" name="customerid" id="sales-customerid-goodscustmer"/>
                                        </td>
                                        <td>品牌:</td>
                                        <td><input type="text" name="brandid" id="sale-goodscustmer-brand" width="130"/></td>
                                        <td>
                                            <a href="javaScript:void(0);" id="sales-query-goodscustmer" class="button-qr">查询</a>
                                            <a href="javaScript:void(0);" id="sales-reload-goodscustmer" class="button-qr">重置</a>
                                        </td>
                                    </tr>
                                </table>
                            </form>
                        </div>
                        <table id="sales-table-goodscustmerlist"></table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<div id="sales-dialog-goodsandpricecustmer"></div>
<div id="sales-dialog-goodsandpricecustmermore"></div>
<script type="text/javascript">
var pricecustmer_AjaxConn = function (Data, Action) {
    var MyAjax = $.ajax({
        type: 'post',
        cache: false,
        url: Action,
        data: Data,
        async: false,
        success:function(data){
            loaded();
        }
    })
    return MyAjax.responseText;
}
//结束行编辑
var $goodspriceList = $('#sales-table-goodsandpricecustmer');
var editIndex = undefined;
var editfiled = null;
function endEditing(field){
    if (editIndex == undefined){
        return true;
    }
    var row = $goodspriceList.datagrid('getRows')[editIndex];
    if(row != undefined){
        var shopid = row.shopid;
        var price = row.price;
        var ctcboxprice = row.ctcboxprice;
        var noprice = row.noprice;
        if(field == "shopid"){
            var ed = $goodspriceList.datagrid('getEditor', {index:editIndex,field:'shopid'});
            if(null != ed){
                shopid = $(ed.target).val();
                $goodspriceList.datagrid('endEdit', editIndex);
            }
        }else if(field == "price"){
            var ed = $goodspriceList.datagrid('getEditor', {index:editIndex,field:'price'});
            if(null != ed){
                price = getNumberBoxObject(ed.target).val();
                $goodspriceList.datagrid('endEdit', editIndex);
                if(parseInt(row.taxrate) != 0){
                    noprice = price/row.taxrate;
                    row.noprice = noprice;
                }
                ctcboxprice = price*row.goodsInfo.boxnum;
                row.ctcboxprice = ctcboxprice;
                $goodspriceList.datagrid('updateRow',{index:editIndex, row:row});
            }
        }else if(field == "ctcboxprice"){
            var ed = $goodspriceList.datagrid('getEditor', {index:editIndex,field:'ctcboxprice'});
            if(null != ed){
                ctcboxprice = getNumberBoxObject(ed.target).val();
                $goodspriceList.datagrid('endEdit', editIndex);
                if(parseInt(ctcboxprice) != 0){
                    if(parseInt(row.goodsInfo.boxnum) != 0){
                        price = ctcboxprice/row.goodsInfo.boxnum;
                        row.price = price;
                    }
                    if(parseInt(row.taxrate) != 0){
                        noprice = price/row.taxrate;
                        row.noprice = noprice;
                    }
                    $goodspriceList.datagrid('updateRow',{index:editIndex, row:row});
                }
            }
        }else if(field == "noprice"){
            var ed = $goodspriceList.datagrid('getEditor', {index:editIndex,field:'noprice'});
            if(null != ed){
                noprice = getNumberBoxObject(ed.target).val();
                $goodspriceList.datagrid('endEdit', editIndex);
                price = noprice*row.taxrate;
                row.price = price;
                ctcboxprice = price*row.goodsInfo.boxnum;
                row.ctcboxprice = ctcboxprice;
                $goodspriceList.datagrid('updateRow',{index:editIndex, row:row});
            }
        }
        if(row.initshopid != shopid || Number(row.initprice).toFixed(4) != Number(price).toFixed(4) || Number(row.initnoprice).toFixed(4) != Number(noprice).toFixed(4) || Number(row.initctcboxprice).toFixed(2) != Number(ctcboxprice).toFixed(2)){
            row.isedit = "1";
        }else{
            row.isedit = "0";
        }
        $goodspriceList.datagrid('updateRow',{index:editIndex, row:row});
        if(row.isedit == "1"){
            $goodspriceList.datagrid('checkRow',editIndex);
        }else{
            $goodspriceList.datagrid('uncheckRow',editIndex);
        }
        editIndex = undefined;
        return true;
    }else{
        editIndex = undefined;
        return false;
    }
}

function onClickCell(index, field){
    if (endEditing(editfiled)){
        editfiled = field;
        if(field == "shopid"){
            $goodspriceList.datagrid('selectRow', index).datagrid('editCell', {index:index,field:field});
            editIndex = index;
            var ed = $goodspriceList.datagrid('getEditor', {index:editIndex,field:'shopid'});
            if(null != ed){
                $(ed.target).focus();
                $(ed.target).select();
            }
        }else if(field == "price"){
            $goodspriceList.datagrid('selectRow', index).datagrid('editCell', {index:index,field:field});
            editIndex = index;
            var ed = $goodspriceList.datagrid('getEditor', {index:editIndex,field:'price'});
            if(null != ed){
                getNumberBoxObject(ed.target).focus();
                getNumberBoxObject(ed.target).select();
            }
        }else if(field == "ctcboxprice"){
            $goodspriceList.datagrid('selectRow', index).datagrid('editCell', {index:index,field:field});
            editIndex = index;
            var ed = $goodspriceList.datagrid('getEditor', {index:editIndex,field:'ctcboxprice'});
            if(null != ed){
                getNumberBoxObject(ed.target).focus();
                getNumberBoxObject(ed.target).select();
            }
        }else if(field == "noprice"){
            $goodspriceList.datagrid('selectRow', index).datagrid('editCell', {index:index,field:field});
            editIndex = index;
            var ed = $goodspriceList.datagrid('getEditor', {index:editIndex,field:'noprice'});
            if(null != ed){
                getNumberBoxObject(ed.target).focus();
                getNumberBoxObject(ed.target).select();
            }
        }
    }
}

$(function(){
    $("#sale-pricecustomer-goodswidget").goodsWidget({
        onSelect:function(data){
            $("#sales-goodsid-goodsandpricecustmer").val(data.id);
        },
        onClear:function(){
            $("#sales-goodsid-goodsandpricecustmer").val("");
        }
    });
    //客户分类
    $("#sale-customersort-customerlist").widget({
        referwid:'RT_T_BASE_SALES_CUSTOMERSORT',
        singleSelect:true,
        width:100,
        onlyLeafCheck:true
    });
    $("#sale-pricecustomer-customerwidget").widget({
        referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT_2',
        singleSelect:true,
        rows:20,
        width:'130',
        onlyLeafCheck:true,
        onSelect:function(data){
            $("#sale-pricecustomer-cusotmerid").attr("disabled","disabled");
        },
        onClear:function(){
            $("#sale-pricecustomer-cusotmerid").removeAttr("disabled");
        }
    });

    $("#sale-pricecustomer-cusotmerid").change(function(){
        if($("#sale-pricecustomer-cusotmerid").val() != ""){
            $("#sale-pricecustomer-customerwidget").widget('disable');
        }else{
            $("#sale-pricecustomer-customerwidget").widget('enable');
        }
    });

    //客户列表
    $('#sales-table-customerlist').datagrid({
        fit:true,
        title:'',
        method:'post',
        rownumbers:true,
        pagination:true,
        idField:'id',
        pageSize:100,
        singleSelect:true,
        toolbar:"#sales-button-custoemrlist",
        frozenColumns:[[{field:'ck',width:60,checkbox:true}]],
        columns:[[
            {field:'id',title:'编码',width:80,sortable:true},
            {field:'name',title:'客户名称',width:120,sortable:true},
            {field:'pid',title:'上级客户',width:120,sortable:true,
                formatter:function(val,rowData,rowIndex){
                    return rowData.pname;
                }
            }
        ]],
        onClickRow:function(rowIndex, rowData){
            $("#pricecustomer-pid-customerlist").val(rowData.pid);
            $("#sales-customerid-goodsandpricecustmer").val(rowData.id);
            $("#sales-customerid-goodscustmer").val(rowData.id);

            var json = [];
            json["customerid"] = rowData.id;
            json["goodsid"] = $("#sale-pricecustomer-goodswidget").goodsWidget('getValue');
            $('#sales-table-goodsandpricecustmer').datagrid('options').url = 'basefiles/getPriceListPage.do';
            $('#sales-table-goodsandpricecustmer').datagrid('load',json);

            var json2 = [];
            json2["customerid"] = rowData.id;
            $('#sales-table-goodscustmerlist').datagrid('options').url = 'basefiles/getGoodsListPage.do';
            $('#sales-table-goodscustmerlist').datagrid('load',json2);
        },
        onCheck:function(rowIndex,rowData){
            var customerrows = $(this).datagrid('getChecked');
            if(null != customerrows && customerrows.length > 1){
                if($("#sales-edit-goodsandpricecustmer").size()>0) {
                    $("#sales-edit-goodsandpricecustmer").linkbutton('disable');
                }
            }else{
                if($("#sales-edit-goodsandpricecustmer").size()>0) {
                    $("#sales-edit-goodsandpricecustmer").linkbutton('enable');
                }
            }
        },
        onUncheck:function(rowIndex,rowData) {
            var customerrows = $(this).datagrid('getChecked');
            if(null != customerrows && customerrows.length > 1){
                if($("#sales-edit-goodsandpricecustmer").size()>0) {
                    $("#sales-edit-goodsandpricecustmer").linkbutton('disable');
                }
            }else{
                if($("#sales-edit-goodsandpricecustmer").size()>0) {
                    $("#sales-edit-goodsandpricecustmer").linkbutton('enable');
                }
            }
        }
    });

    //回车事件
    controlQueryAndResetByKey("sales-query-customerlist","sales-reload-customerlist");

    //查询
    $("#sales-query-customerlist").click(function(){
        var queryJSON = $("#sales-form-custoemrlist").serializeJSON();
        $('#sales-table-customerlist').datagrid('options').url = 'basefiles/getCustomerListForPact.do?type=priceandgoods';
        $("#sales-table-customerlist").datagrid("load",queryJSON);
    });

    //重置
    $("#sales-reload-customerlist").click(function(){
        $("#sales-form-custoemrlist")[0].reset();
        $("#sale-pricecustomer-goodswidget").goodsWidget('clear');
        $("#sale-customersort-customerlist").widget('clear');
        $('#sales-table-customerlist').datagrid('loadData', { total: 0, rows: [] });

        //合同商品
        $("#sales-query-goodsandpricecustmer")[0].reset();
        $("#sale-goodsandprice-goodsid").goodsWidget('clear');
        $("#sale-goodsandprice-brand").widget('clear');
        $("#sale-goodsandprice-goodssort").widget('clear');
        $("#sales-customerid-goodsandpricecustmer").val("");
        $('#sales-table-goodsandpricecustmer').datagrid('loadData', { total: 0, rows: [] });

        //$("#sales-table-customerlist").datagrid("load",{});

        //客户合同
        $("#sales-form-custoemrgoodslist")[0].reset();
        $("#sale-goodscustmer-goodswidget").widget('clear');
        $("#sale-goodscustmer-brand").widget('clear');
        $("#sales-customerid-goodscustmer").val("");
        $('#sales-table-goodscustmerlist').datagrid('loadData', { total: 0, rows: [] });
    });

    /**----------------合同商品--------------------**/
        //合同商品商品
    $("#sale-goodsandprice-goodsid").goodsWidget({
        singleSelect:true,
        onlyLeafCheck:true,
        onSelect:function(data){
            if($("#sales-customerid-goodsandpricecustmer").val() == ""){
                $.messager.alert("提醒","请先选择客户!");
                $("#sale-goodsandprice-goodsid").goodsWidget('clear');
                return false;
            }
        }
    });
    //合同商品品牌
    $("#sale-goodsandprice-brand").widget({
        referwid:'RL_T_BASE_GOODS_BRAND',
        col:'branid',
        width:175,
        singleSelect:true,
        onlyLeafCheck:true,
        onSelect:function(data){
            if($("#sales-customerid-goodsandpricecustmer").val() == ""){
                $.messager.alert("提醒","请先选择客户!");
                $("#sale-goodsandprice-brand").widget('clear');
                return false;
            }
        }
    });

    //合同商品分类
    $("#sale-goodsandprice-goodssort").widget({
        referwid:'RL_T_BASE_GOODS_WARESCLASS',
        width:160,
        singleSelect:true,
        onlyLeafCheck:false,
        onSelect:function(data){
            if($("#sales-customerid-goodsandpricecustmer").val() == ""){
                $.messager.alert("提醒","请先选择客户!");
                $("#sale-goodsandprice-goodssort").widget('clear');
                return false;
            }
        }
    });

    //合同商品查询
    $("#sales-query-goodsandprice").click(function(){
        if($("#sales-customerid-goodsandpricecustmer").val() == ""){
            $.messager.alert("提醒","请先选择客户!");
            return false;
        }
        var queryJSON = $("#sales-query-goodsandpricecustmer").serializeJSON();
        $('#sales-table-goodsandpricecustmer').datagrid('options').url= 'basefiles/getPriceListPage.do';
        $("#sales-table-goodsandpricecustmer").datagrid("load",queryJSON);
    });

    //合同商品重置
    $("#sales-reload-goodsandprice").click(function(){
        $("#sales-query-goodsandpricecustmer")[0].reset();
        $("#sale-goodsandprice-brand").widget('clear');
        $("#sale-goodsandprice-goodsid").goodsWidget('clear');
        $("#sale-goodsandprice-goodssort").widget('clear');
        $('#sales-table-goodsandpricecustmer').datagrid('options').url= 'basefiles/getPriceListPage.do';
        var queryJSON = $("#sales-query-goodsandpricecustmer").serializeJSON();
        $("#sales-table-goodsandpricecustmer").datagrid("load",queryJSON);
    });

    //合同商品
    $('#sales-table-goodsandpricecustmer').datagrid({
        fit:true,
        title:'',
        method:'post',
        sortName:'goodsid',
        sortOrder:'asc',
        rownumbers:true,
        pagination:true,
        pageSize:100,
        singleSelect:true,
        toolbar:"#sales-button-goodsandpricecustmer",
        rowStyler: function(index,row){
            if (row.isedit == '1'){
                return 'background-color:rgb(190, 250, 241);';
            }
        },
        columns:[[
            {field:'ck',checkbox:true},
            {field:'id',title:'编码',hidden:true},
            {field:'goodsid',title:'商品编码',width:60},
            {field:'goodsname',title:'商品名称',width:210,isShow:true,
                formatter: function(value,row,index){
                    if(row.goodsInfo != null){
                        return row.goodsInfo.name;
                    }
                }
            },
            {field:'barcode',title:'条形码',width:90},
            {field:'shopid',title:'店内码',width:60,editor:'text',
                styler:function(value,row,index){
                    if(row.shopid != row.initshopid){
                        return 'background-color:yellow;';
                    }
                }
            },
            {field:'taxprice',title:'价格套价格',width:80,align:'right',
                formatter: function(value,row,index){
                    return formatterDefineMoney(value,4);
                }
            },
            {field:'price',title:'含税合同价',width:80,align:'right',
                formatter: function(value,row,index){
                    return formatterDefineMoney(value,4);
                },
                editor:{
                    type:'numberbox',
                    options:{
                        precision:6,
                        max:999999999999,
                        min:0
                    }
                },
                styler:function(value,row,index){
                    if(Number(row.price).toFixed(4)!=Number(row.initprice).toFixed(4)){
                        return 'background-color:yellow;';
                    }
                }
            },
            {field:'ctcboxprice',title:'合同箱价',width:80,align:'right',
                formatter: function(value,row,index){
                    return formatterDefineMoney(value,2);
                },
                editor:{
                    type:'numberbox',
                    options:{
                        precision:2,
                        max:999999999999,
                        min:0
                    }
                },
                styler:function(value,row,index){
                    if(Number(row.ctcboxprice).toFixed(2)!=Number(row.initctcboxprice).toFixed(2)){
                        return 'background-color:yellow;';
                    }
                }
            },
            {field:'noprice',title:'未税合同价',width:80,align:'right',
                formatter: function(value,row,index){
                    return formatterDefineMoney(value,4);
                },
                editor:{
                    type:'numberbox',
                    options:{
                        precision:6,
                        max:999999999999,
                        min:0
                    }
                },
                styler:function(value,row,index){
                    if(Number(row.noprice).toFixed(4)!=Number(row.initnoprice).toFixed(4)){
                        return 'background-color:yellow;';
                    }
                }
            },
            {field:'remark',title:'备注',width:100},
            {field:'isedit',title:'是否修改',width:60,hidden:true}
        ]],
        onClickCell: function(index, field, value){
            <security:authorize url="/basefiles/goodsAndPriceCustmerSaveBtn.do">
            onClickCell(index, field);
            </security:authorize>
        },
        onLoadSuccess:function(data){
            var customerrows = $("#sales-table-customerlist").datagrid('getChecked');
            if(null != customerrows && customerrows.length > 1){
                if($("#sales-edit-goodsandpricecustmer").size()>0) {
                    $("#sales-edit-goodsandpricecustmer").linkbutton('disable');
                }
            }else{
                if($("#sales-edit-goodsandpricecustmer").size()>0) {
                    $("#sales-edit-goodsandpricecustmer").linkbutton('enable');
                }
            }
        }
    });

    $(document).die("keydown").live("keydown",function(event){
        if(event.keyCode==13){
            $goodspriceList.datagrid('clearSelections');
            $goodspriceList.datagrid('selectRow',editIndex+1);
            onClickCell(editIndex+1, editfiled);
        }
    });

    /**-------------------客户商品-------------------------------**/
        //客户商品商品
    $("#sale-goodscustmer-goodswidget").widget({
        name:'t_base_sales_customer_goods',
        col:'goodsid',
        width:'160',
        singleSelect:true,
        onlyLeafCheck:true,
        onSelect:function(data){
            if($("#sales-customerid-goodscustmer").val() == ""){
                $.messager.alert("提醒","请先选择客户!");
                $("#sale-goodscustmer-goodswidget").widget('clear');
                return false;
            }
        }
    });
    //客户商品品牌
    $("#sale-goodscustmer-brand").widget({
        referwid:'RL_T_BASE_GOODS_BRAND',
        col:'branid',
        width:'120',
        singleSelect:true,
        onlyLeafCheck:true,
        onSelect:function(data){
            if($("#sales-customerid-goodscustmer").val() == ""){
                $.messager.alert("提醒","请先选择客户!");
                $("#sale-goodscustmer-brand").widget('clear');
                return false;
            }
        }
    });

    //客户商品查询
    $("#sales-query-goodscustmer").click(function(){
        if($("#sales-customerid-goodscustmer").val() == ""){
            $.messager.alert("提醒","请先选择客户!");
            return false;
        }
        var queryJSON = $("#sales-form-custoemrgoodslist").serializeJSON();
        $('#sales-table-goodscustmerlist').datagrid('options').url= 'basefiles/getGoodsListPage.do';
        $("#sales-table-goodscustmerlist").datagrid("load",queryJSON);
    });

    //客户商品重置
    $("#sales-reload-goodscustmer").click(function(){
        $("#sales-form-custoemrgoodslist")[0].reset();
        $("#sale-goodscustmer-goodswidget").widget('clear');
        $("#sale-goodscustmer-brand").widget('clear');
        $('#sales-table-goodscustmerlist').datagrid('options').url= 'basefiles/getGoodsListPage.do';
        var queryJSON = $("#sales-form-custoemrgoodslist").serializeJSON();
        $("#sales-table-goodscustmerlist").datagrid("load",queryJSON);
    });

    //客户商品
    $("#sales-table-goodscustmerlist").datagrid({
        fit:true,
        idField:'id',
        singleSelect:true,
        sortName:'goodsid',
        sortOrder:'asc',
        pagination:true,
        pageSize:100,
        rownumbers:true,
        toolbar:"#sales-button-goodscustmer",
        columns:[[
            {field:'id',hidden:true},
            {field:'goodsid',title:'商品编码',width:120},
            {field:'goodsname',title:'商品名称',width:210,isShow:true},
            {field:'price',title:'含税单价',width:120,align:'right',
                formatter: function(value,row,index){
                    return formatterDefineMoney(value,4);
                }
            },
            {field:'remark',title:'备注',width:120}
        ]]
    });

});


function sales_form_custoemrlist_savegoon_submit(){
    $("#sales-form-priceCustomer").form({
        onSubmit: function(){
            var flag = $(this).form('validate');
            if(flag==false){
                return false;
            }
            loading("提交中..");
        },
        success:function(data){
            loaded();
            var json = $.parseJSON(data);
            if(json.flag){
                $.messager.alert("提醒","保存成功");
                var queryJSON = $("#sales-query-goodsandpricecustmer").serializeJSON();
                $('#sales-table-goodsandpricecustmer').datagrid('reload',queryJSON);
                var row = $('#sales-table-customerlist').datagrid('getSelected');
                if(null == row){
                    $.messager.alert("提醒","请选择客户！");
                    return false;
                }
                $('#sales-dialog-goodsandpricecustmer1').dialog('refresh', 'basefiles/showCustomerPriceAndGoodsAddPage.do?customerid='+row.id);
            }
            else{
                $.messager.alert("提醒","保存失败");
            }
        }
    });
}

function sales_form_custoemrlist_save_submit(){
    $("#sales-form-priceCustomer").form({
        onSubmit: function(){
            loading("提交中..");
        },
        success:function(data){
            loaded();
            var json = $.parseJSON(data);
            if(json.flag){
                $.messager.alert("提醒","保存成功");
                var queryJSON = $("#sales-query-goodsandpricecustmer").serializeJSON();
                $('#sales-table-goodsandpricecustmer').datagrid('reload',queryJSON);
                $("#sales-dialog-goodsandpricecustmer1").dialog('close');
            }
            else{
                $.messager.alert("提醒","保存失败");
            }
        }
    });
}

function sales_form_custoemrmorelist_save_submit(){
    $("#sales-form-priceCustomerMore").form({
        onSubmit: function(){
            loading("提交中..");
        },
        success:function(data){
            loaded();
            var json = $.parseJSON(data);
            if(json.flag){
                $.messager.alert("提醒","保存成功");
                var queryJSON = $("#sales-query-goodsandpricecustmer").serializeJSON();
                queryJSON["goodsid"] = json.goodsid;
                $('#sales-table-goodsandpricecustmer').datagrid('reload',queryJSON);
                $("#sales-dialog-goodsandpricecustmermore1").dialog('close');
            }
            else{
                $.messager.alert("提醒","保存失败");
            }
        }
    });
}

//新增
$("#sales-add-goodsandpricecustmer").click(function(){
    var row = $('#sales-table-customerlist').datagrid('getSelected');
    if(null == row){
        $.messager.alert("提醒","请选择客户！");
        return false;
    }
    $('<div id="sales-dialog-goodsandpricecustmer1"></div>').appendTo('#sales-dialog-goodsandpricecustmer');
    $("#sales-dialog-goodsandpricecustmer1").dialog({
        maximizable:true,
        resizable:true,
        href:'basefiles/showCustomerPriceAndGoodsAddPage.do?customerid='+row.id,
        title:'合同商品【新增】',
        width: 410,
        height: 420,
        closed: false,
        cache: false,
        modal:true,
        buttons:[{
            text:'保存后继续新增',
            //plain:true,
            handler:function(){
                sales_form_custoemrlist_savegoon_submit();
                $("#sales-form-priceCustomer").submit();
            }
        },{
            text:'保存',
            //plain:true,
            handler:function(){
                sales_form_custoemrlist_save_submit();
                $("#sales-form-priceCustomer").submit();
            }
        }
        ],
        onClose:function(){
            $("#sales-dialog-goodsandpricecustmer1").dialog('destroy');
        }
    });
});

//复制
$("#sales-copy-goodsandpricecustmer").click(function(){
    if($(this).linkbutton('options').disabled){
        return false;
    }
    var row = $('#sales-table-customerlist').datagrid('getSelected');
    if(null == row){
        $.messager.alert("提醒","请选择客户！");
        return false;
    }
    var row2 = $('#sales-table-goodsandpricecustmer').datagrid('getChecked');
    if(row2.length == 0){
        $.messager.alert("提醒","请勾选合同商品！");
        return false;
    }
    var goodspriceids = "",goodsids = "";
    for(var i=0;i<row2.length;i++){
        if(goodspriceids == ""){
            goodspriceids = row2[i].id;
        }else{
            goodspriceids += "," + row2[i].id;
        }
        if(goodsids == ""){
            goodsids = row2[i].goodsid;
        }else{
            goodsids += "," + row2[i].goodsid;
        }
    }
    $('<div id="sales-dialog-goodsandpricecustmer1"></div>').appendTo('#sales-dialog-goodsandpricecustmer');
    $("#sales-dialog-goodsandpricecustmer1").dialog({
        maximizable:true,
        resizable:true,
        href:'basefiles/showCustomerPriceAndGoodsCopyPage.do?customerid='+row.id,
        title:'合同商品【复制】',
        width: 680,
        height: 450,
        closed: false,
        cache: false,
        modal:true,
        buttons:[{
            text:'确定',
            iconCls:'button-save',
            //plain:true,
            handler:function(){
                copyCustomerPrice(goodspriceids,goodsids);
            }
        }
        ],
        onClose:function(){
            $("#sales-dialog-goodsandpricecustmer1").dialog('destroy');
        }
    });
});

//保存
$("#sales-save-goodsandpricecustmer").click(function(){
    endEditing(editfiled);
    var rows = $goodspriceList.datagrid('getChecked');
    for(var i=0;i<rows.length;i++){
        if(rows[i].isedit != "1"){
            var index = $goodspriceList.datagrid('getRowIndex',rows[i]);
            $goodspriceList.datagrid('uncheckRow',index);
        }
    }
    rows = $goodspriceList.datagrid('getChecked');
    if(rows.length == 0){
        $.messager.alert("提醒","请勾选已修改的数据!");
        return false;
    }
    loading('提交中...');
    $.ajax({
        url :'basefiles/saveCustomerPrice.do',
        type:'post',
        dataType:'json',
        data:{rowsjsonstr:JSON.stringify(rows)},
        success:function(json){
            loaded();
            if(json.flag){
                editIndex = undefined;
                $.messager.alert("提醒","保存成功!");
                $goodspriceList.datagrid('reload');
            }else{
                $.messager.alert("提醒","保存失败!");
            }
        }
    });
});

//修改
$("#sales-edit-goodsandpricecustmer").click(function(){
    if($(this).linkbutton('options').disabled){
        return false;
    }
    var row = $('#sales-table-customerlist').datagrid('getSelected');
    if(null == row){
        $.messager.alert("提醒","请选择客户！");
        return false;
    }
    var row2 = $('#sales-table-goodsandpricecustmer').datagrid('getSelected');
    if(null == row2){
        $.messager.alert("提醒","请选择合同商品！");
        return false;
    }
    $('<div id="sales-dialog-goodsandpricecustmer1"></div>').appendTo('#sales-dialog-goodsandpricecustmer');
    $("#sales-dialog-goodsandpricecustmer1").dialog({
        maximizable:true,
        resizable:true,
        href:'basefiles/showCustomerPriceAndGoodsEditPage.do?customerid='+row.id+'&id='+row2.id,
        title:'合同商品【修改】',
        width: 410,
        height: 420,
        closed: false,
        cache: false,
        modal:true,
        buttons:[{
            text:'保存',
            //plain:true,
            handler:function(){
                sales_form_custoemrlist_save_submit();
                $("#sales-form-priceCustomer").submit();
            }
        }
        ],
        onClose:function(){
            $("#sales-dialog-goodsandpricecustmer1").dialog('destroy');
        }
    });
});

//批量修改
$("#sales-editmore-goodsandpricecustmer").click(function(){
    var goodsid = $("#sale-pricecustomer-goodswidget").goodsWidget('getValue');
    if(goodsid == ""){
        $("#sale-pricecustomer-goodswidget").focus();
        $.messager.alert("提醒","请选择商品！");
        return false;
    }
    /*var customersort = $("#sale-customersort-customerlist").widget("getValue");
     if(customersort == ""){
     $("#sale-customersort-customerlist").focus();
     $.messager.alert("提醒","请选择客户分类！");
     return false;
     }*/
    var rows = $('#sales-table-customerlist').datagrid('getChecked');
    if(rows.length == 0){
        $.messager.alert("提醒","请选择客户！");
        return false;
    }
    var customerids = "";
    for(var i=0;i<rows.length;i++){
        if(customerids == ""){
            customerids = rows[i].id;
        }else{
            customerids += "," + rows[i].id;
        }
    }
    $('<div id="sales-dialog-goodsandpricecustmermore1"></div>').appendTo('#sales-dialog-goodsandpricecustmermore');
    $("#sales-dialog-goodsandpricecustmermore1").dialog({
        maximizable:true,
        resizable:true,
        href:'basefiles/showCustomerPriceAndGoodsMoreEditPage.do?customerids='+customerids+'&goodsid='+goodsid,
        title:'合同商品【批量修改】',
        width: 330,
        height: 240,
        closed: false,
        cache: false,
        modal:true,
        buttons:[{
            text:'保存',
            //plain:true,
            handler:function(){
                sales_form_custoemrmorelist_save_submit();
                $("#sales-form-priceCustomerMore").submit();
            }
        }
        ],
        onClose:function(){
            $("#sales-dialog-goodsandpricecustmermore1").dialog('destroy');
        }
    });
});

//删除
$("#sales-delete-goodsandpricecustmer").click(function(){
    var row2 = $('#sales-table-goodsandpricecustmer').datagrid('getChecked');
    if(row2.length == 0){
        $.messager.alert("提醒","请勾选合同商品！");
        return false;
    }
    var idstr = "";
    for(var i=0;i<row2.length;i++){
        idstr += row2[i].id+",";
    }
    $.messager.confirm("提醒","是否删除合同商品?",function(r){
        if(r){
            loading("删除中..");
            $.ajax({
                url:'basefiles/deleteCustomerPrices.do?customerid='+$("#sales-customerid-goodsandpricecustmer").val(),
                data:'idstr='+idstr,
                dataType:'json',
                type:'post',
                success:function(json){
                    loaded();
                    if(json.flag){
                        $.messager.alert("提醒","删除成功!");
                        var queryJSON = $("#sales-query-goodsandpricecustmer").serializeJSON();
                        $("#sales-table-goodsandpricecustmer").datagrid("load",queryJSON);
                    }
                    else{
                        $.messager.alert("提醒","保存失败");
                    }
                }
            });
        }
    });
});

//导入
$("#sales-import-goodsandpricecustmer").Excel('import',{
    type:'importUserdefined',
    clazz: "salesService", //spring中注入的类名
    method: "addDRPriceCustomer", //插入数据库的方法
    module: 'basefiles', //模块名，
    pojo: "CustomerPrice", //实体类名，将和模块名组合成
    url:'basefiles/importPriceCustomerData.do',
    importparam:'客户编码、商品编码、含税合同价格或未税合同价格必输',
    onClose: function(){ //导入成功后窗口关闭时操作，
        $("#sales-table-goodsandpricecustmer").datagrid('clearSelections');
        $("#sales-table-goodsandpricecustmer").datagrid('reload');	//更新列表
    }
});

//导出
$("#sales-export-goodsandpricecustmer").Excel('export',{
    datagrid:"#sales-table-goodsandpricecustmer",
    queryForm: "#sales-query-goodsandpricecustmer", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
    type:'exportUserdefined',
    name:'合同商品列表',
    url:'basefiles/exportPriceCustomerData.do'
});
</script>
</body>
</html>

						