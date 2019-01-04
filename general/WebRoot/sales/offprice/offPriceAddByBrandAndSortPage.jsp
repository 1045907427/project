<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>销售订单商品批量添加</title>
</head>
<body>
<table id="sales-datagrid-offPriceAddByBrandAndSort"></table>
<div id="sales-queryDiv-offPriceAddByBrandAndSort">
    <form id="sales-queryform-offPriceAddByBrandAndSort">
        <table cellpadding="0" cellspacing="0" border="0">
            <tr>
                <td class="len80 right">编码/条形码：</td>
                <td><input type="text" name="id" style="width: 150px;" /></td>
                <td class="len80 right">品牌：</td>
                <td><input type="text" name="brand" id="sales-brand-offPriceAddByBrandAndSort" style="width: 150px;" /></td>
                <td class="len80 right">商品分类：</td>
                <td><input type="text" name="defaultsort" id="sales-defaultsort-offPriceAddByBrandAndSort" style="width: 150px;" /></td>
                <td class="len120 right"><a href="javascript:;" class="button-qr" id="sales-query-offPriceAddByBrandAndSort">查询</a></td>
                <td class="len120 right"><a href="javascript:;" class="button-qr" id="sales-save-offPriceAddByBrandAndSort">保存</a></td>
            </tr>
        </table>
        <input type="hidden" id="sales-detailJson-offPriceAddByBrandAndSort" name="detailJson" />
    </form>
</div>
<script type="text/javascript">

$offPriceBatch = $("#sales-datagrid-offPriceAddByBrandAndSort");

var editIndex = undefined;
var thisIndex = undefined;
var editfiled = null;
var nextfiled = null;

$(function() {
    $("#sales-brand-offPriceAddByBrandAndSort").widget({
        width : 120,
        name : 't_base_goods_brand',
        col : 'id',
        singleSelect : false,
        onlyLeafCheck : false
    });
    $("#sales-defaultsort-offPriceAddByBrandAndSort").widget({
        width : 120,
        referwid : 'RL_T_BASE_GOODS_WARESCLASS',
        col : 'id',
        singleSelect : false,
        onlyLeafCheck : false
    });
    var tableColJson = $("#sales-datagrid-offPriceAddByBrandAndSort").createGridColumnLoad({
                frozenCol : [ [
                    {field : 'ck',checkbox : true}
                ] ],
                commonCol : [ [
                    {field : 'goodsid',title : '商品编码',width : 70,align : ' left',sortable : true},
                    {field : 'goodsname',title : '商品名称',width : 250,align : 'left',
                        formatter : function(value,rowData, rowIndex) {
                            if (rowData.goodsInfo != null) {
                                return rowData.goodsInfo.name;
                            } else {
                                return "";
                            }
                        }
                    },
                    {field : 'barcode',title : '条形码',width : 90,align : 'left',
                        formatter : function(value,rowData, rowIndex) {
                            if (rowData.goodsInfo != null) {
                                return rowData.goodsInfo.barcode;
                            } else {
                                return "";
                            }
                        }
                    },
                    {field : 'brandName',title : '商品品牌',width : 60,align : 'left',aliascol : 'goodsid',hidden : true,
                        formatter : function(value,rowData, rowIndex) {
                            if (rowData.goodsInfo != null) {
                                return rowData.goodsInfo.brandName;
                            } else {
                                return "";
                            }
                        }
                    },
                    {field : 'boxnum',title : '箱装量',width : 45,align : 'right',
                        formatter : function(value,rowData, rowIndex) {
                            if (rowData.goodsInfo != null) {
                                return rowData.goodsInfo.boxnum;
                            } else {
                                return "";
                            }
                        }
                    },
                    {field : 'unitname',title : '单位',width : 35,align : 'left',
                        formatter : function(value,rowData, rowIndex) {
                            if (rowData.goodsInfo != null) {
                                return rowData.goodsInfo.mainunitName;
                            } else {
                                return "";
                            }
                        }
                    },
                    {field : 'oldprice',title : '原价',width : 60,align : 'right',
                        formatter : function(value,rowData, rowIndex) {
                            if (rowData.goodsInfo != null) {
                                return rowData.goodsInfo.basesaleprice;
                            } else {
                                return "";
                            }
                        }
                    },
                    {field : 'offprice',title : '特价',width : 50,align : 'left',editor:'textbox',
                        formatter : function(value, row,index) {
                            return formatterMoney(value);
                        }
                    },
                    {field : 'lownum',title : '数量下限',width : 60,align : 'right',sortable : true,
                        editor:{
                            type:'numberbox',
                            options:{
                                precision:${decimallen}
                            }
                        },
                        formatter : function(value, row,index) {
                            if(value == ""){
                                return 0;
                            }else{
                                return formatterBigNumNoLen(value);
                            }
                        }
                    },
                    {field : 'upnum',title : '数量上限',width : 60,align : 'right',sortable : true,
                        editor:{
                            type:'numberbox',
                            options:{
                                precision:${decimallen}
                            }
                        },
                        formatter:function(value,rowData,rowIndex){
                            return formatterBigNumNoLen(value);
                        }
                    },
                    {field : 'remark',title : '备注',width : 200,align : 'left',editor:'textbox'}
                ]]
            });

    $("#sales-datagrid-offPriceAddByBrandAndSort").datagrid({
        authority : tableColJson,
        frozenColumns : tableColJson.frozen,
        columns : tableColJson.common,
        fit : true,
        title : "",
        method : 'post',
        rownumbers : true,
        pagination : true,
        sortName : 'goodsid',
        sortOrder : 'desc',
        singleSelect : false,
        checkOnSelect : true,
        selectOnCheck : true,
        showFooter : false,
        pageSize : 100,
        toolbar : "#sales-queryDiv-offPriceAddByBrandAndSort",
        onDblClickRow:function(rowIndex, rowData){

        },
        onClickRow:function(rowIndex, rowData){
            if(rowData.offprice != null && rowData.offprice != ""){
                $(this).datagrid('checkRow',rowIndex);
            }else{
                $(this).datagrid('uncheckRow',rowIndex);
                $(this).datagrid('unselectRow',rowIndex);
            }
        },
        onClickCell:function(index, field, value){
            onClickCell(index, field);

            thisIndex = index;
        },
        onLoadSuccess:function(data){
            editIndex = undefined;
            thisIndex = undefined;
            editfiled = null;
            nextfiled = null;
        }
    }).datagrid('columnMoving');

    function endEditing(field){
        if (editIndex == undefined){
            return true;
        }
        if ($("#sales-datagrid-offPriceAddByBrandAndSort").datagrid('validateRow', editIndex)){
            var ed = $("#sales-datagrid-offPriceAddByBrandAndSort").datagrid('getEditor', {index:editIndex,field:field});
            var edObj=getNumberBoxObject(ed.target);
            if(null==edObj){
                return false;
            }else{
                $("#sales-datagrid-offPriceAddByBrandAndSort").datagrid('endEdit', editIndex);
                var row = $("#sales-datagrid-offPriceAddByBrandAndSort").datagrid('getRows')[editIndex];
                if(field == "offprice"){
                    if(row.offprice == "" || row.offprice == null){
                        $("#sales-datagrid-offPriceAddByBrandAndSort").datagrid('uncheckRow',editIndex);
                        $("#sales-datagrid-offPriceAddByBrandAndSort").datagrid('unselectRow',editIndex);
                    }
                }else if(field == "lownum"){
                    if(row.lownum == "" || row.lownum == null){
                        $("#sales-datagrid-offPriceAddByBrandAndSort").datagrid('uncheckRow',editIndex);
                        $("#sales-datagrid-offPriceAddByBrandAndSort").datagrid('unselectRow',editIndex);
                    }

                }else if(field == "upnum"){
                    if(row.upnum == "" || row.upnum == null){
                        $("#sales-datagrid-offPriceAddByBrandAndSort").datagrid('uncheckRow',editIndex);
                        $("#sales-datagrid-offPriceAddByBrandAndSort").datagrid('unselectRow',editIndex);
                    }

                }else if(field == "remark" ){
                    if(row.remark == "" || row.remark == null){
                        $("#sales-datagrid-offPriceAddByBrandAndSort").datagrid('uncheckRow',editIndex);
                        $("#sales-datagrid-offPriceAddByBrandAndSort").datagrid('unselectRow',editIndex);
                    }

                }
                editIndex = undefined;
                return true ;
            }
        }else{
            return false;
        }

    }

    function onClickCell(index, field){
        if (endEditing(editfiled)){
            var row = $("#sales-datagrid-offPriceAddByBrandAndSort").datagrid('getRows')[index];
            if(row.goodsid == undefined){
                return false;
            }
            editfiled = field;
            if(field == "offprice"){
                nextLineCell(index,"offprice");

            }else if(field == "lownum"){
                nextLineCell(index,"lownum")

            }else if(field == "upnum"){
                nextLineCell(index,"upnum");

            }else if(field == "remark" ){
                nextLineCell(index,"remark");

            }
        }
    }

    function nextLineCell(index,field){
        nextfiled = field;
        $("#sales-datagrid-offPriceAddByBrandAndSort").datagrid('selectRow', index).datagrid('editCell', {index:index,field:field});
        editIndex = index;
        thisIndex = index;
        var ed = $("#sales-datagrid-offPriceAddByBrandAndSort").datagrid('getEditor', {index:editIndex,field:field});
        var obj=getNumberBoxObject(ed.target);
        if(null==obj){
            return false;
        }
        obj.focus();
        obj.select();
        obj.die("keyup").bind("keyup",function(e){
            var e = e || event,
                    keycode = e.which || e.keyCode;
            if (keycode==13 || keycode==38 || keycode==40) {
                if(editfiled!=null){
                    endEditing(editfiled);
                }
            }
        });
    }

    function moveToNextRow(){
        var datarow=$("#sales-datagrid-offPriceAddByBrandAndSort").datagrid('getRows');
        if(null!=datarow && (thisIndex+1) < datarow.length){
            if(editIndex == null){
                onClickCell(thisIndex+1, nextfiled);
            }
        }
    }

    function moveToPrevRow(){
        if((thisIndex-1) >0){
            if(editIndex == null){
                onClickCell(thisIndex-1,nextfiled);
            }
        }
    }
    $(document).bind('keyup', 'up',function (event){
        moveToPrevRow();
        return false;
    });
    $(document).bind('keyup', 'down',function (event){
        moveToNextRow();
        return false;
    });
    $(document).bind('keyup', 'enter',function (event){
        moveToNextRow();
        return false;
    });

    //查询
    $("#sales-query-offPriceAddByBrandAndSort").click(function() {

        var queryJSON = $("#sales-queryform-offPriceAddByBrandAndSort").serializeJSON();
        $('#sales-datagrid-offPriceAddByBrandAndSort').datagrid('options').url = 'sales/getOffPriceByBrandAndSort.do';
        $("#sales-datagrid-offPriceAddByBrandAndSort").datagrid('load', queryJSON);
    });

        //保存
    $("#sales-save-offPriceAddByBrandAndSort").click(function() {

        var rows = $offPriceBatch.datagrid('getChecked');console.info(rows);
        if(rows.length > 0){
            if(editIndex == undefined){
                editIndex = $offPriceBatch.datagrid('getRowIndex',rows[0]);

                if(rows[0].offprice == ""|| rows[0].offprice == undefined){
                    $offPriceBatch.datagrid('unselectRow',editIndex);
                }else{
                    $offPriceBatch.datagrid('checkRow',editIndex);
                }
            }else{
                $offPriceBatch.datagrid('endEdit',editIndex);
                var row = $offPriceBatch.datagrid('getRows')[editIndex];

                if(row.offprice == ""|| row.offprice == undefined){
                    $offPriceBatch.datagrid('unselectRow',editIndex);
                }else{
                    $offPriceBatch.datagrid('checkRow',editIndex);
                }
            }
            rows = sortRow(rows);
            for(var i = 0 ;i<rows.length ; i++ ){
                var row = rows[i];

                var rowIndex = 0;
                var updateFlag = false;
                var offrows = $("#sales-datagrid-offpriceAddPage").datagrid('getRows');
                for(var j = 0 ; j < offrows.length ; j++){
                    if(row.goodsid == offrows[j].goodsid){
                        rowIndex = j ;
                        updateFlag = true;
                        break;
                    }
                    if(offrows[j].goodsid == undefined){
                        rowIndex = j;
                        break;
                    }
                }

                if(rowIndex == offrows.length - 1){
                    $("#sales-datagrid-offpriceAddPage").datagrid('appendRow',{}); //如果是最后一行则添加一新行
                }

                if(updateFlag){
                    var r =confirm("商品编号："+row.goodsid+"已存在，是否替换？");
                    if(r){
                        $("#sales-datagrid-offpriceAddPage").datagrid('updateRow',{index:rowIndex, row:row}); //将数据更新到列表中
                    }
                }else{
                    $("#sales-datagrid-offpriceAddPage").datagrid('updateRow',{index:rowIndex, row:row});
                }
            }
            $("#sales-dialog-offpriceAddPage-content").window('close',true);
        }else{
            alert("请选择一条记录");
        }


    });

    //对批量添加的商品进行排序
    function sortRow(rows){
        var arrRow = new Array();
        var arr = new Array();
        for(var i = 0 ;i<rows.length ; i++ ){
            arr[i] = rows[i].goodsid;
        }
        arr = arr.sort(function(a,b){
            //从小到大排序
            return a>b?1:-1;
        });
        for(var i=0;i<arr.length;i++){
            for(var j = 0 ; j<rows.length ;j ++){
                if(arr[i] == rows[j].goodsid){
                    arrRow[i] = rows[j];
                }
            }
        }
        return arrRow;
    }


});


</script>
</body>
</html>
