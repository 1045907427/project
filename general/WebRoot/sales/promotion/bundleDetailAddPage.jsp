<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>捆绑促销单明细增加页面</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <form id="sales-form-bundlegroupDetail">
        <div data-options="region:'north'" style="height: 200px;">
            <table id="sales-groupDetail-donatedGoods" border="0" toolbar="#tb"></table>
            <div id="tb">
                <a class="easyui-linkbutton" iconCls="button-add" plain="true" onclick="addRow();">增加</a>
                <a class="easyui-linkbutton" iconCls="button-delete" plain="true" onclick="cutRow();">删除</a>
                <a class="easyui-linkbutton" iconCls="button-save" plain="true" onclick="saveRow();">保存</a>
            </div>
        </div>
        <div data-options="region:'center',border:false">
            <h1>&nbsp;套餐信息</h1>
            <table>
                <tr><td>促销编号:</td>
                    <td><input id="sales-groupDetail-groupid" value="${group.groupid}" name="groupid" type="text" class="len150" onfocus="isFocus();"/></td>
                    <td>促销名称:</td>
                    <td><input id="sales-groupDetail-groupName"  value="${group.groupname}"   name="groupname" type="text" class="len150" onfocus="isFocus();" />
                    <td id="promotionValidate"></td>
                    </td>
                </tr>
                <tr><td>套餐总价:</td>
                    <td><input id="sales-groupDetail-bundlePrice" value="${group.buygoodsid}" name="buygoodsid" type="text" class="readonly2 len150" readonly="readonly"   onclick="isFocus();"/></td>
                    <td>促销份数:</td>
                    <td><input id="sales-groupDetail-limitNum"  value="${group.limitnum}" name="limitnum" type="text" class="len150" />
                        <input type="hidden" id="sales-groupDetail-bundleOldPrice" /><%--套餐原价--%>
                    </td>
                    <td><input id="sales-groupDetail-unitNum" type="hidden"/></td>
                </tr>
                <tr>
                    <td>备&nbsp;&nbsp;注:</td>
                    <td colspan="3"><input id="sales-groupDetail-remark" value="${group.remark}" name="remark" type="text" style="width:356px"/></td>
                    <td><input type="hidden" id="oldprice" name="oldprice" /></td>
                    <td><input type="hidden" id="editIndex" name="editIndex" /></td>
                    <td><input type="hidden" id="jsonCount" name="count" /></td>
                </tr>
            </table>
        </div>
        <div data-options="region:'south',border:false">
            <div class="buttonDetailBG" style="height:30px;text-align:right;">
                <input type="button" value="继续添加" name="savegoon" id="sales-saveaddgood-groupDetailDetailEditPage" />
                <input type="button" value="确定" name="savegoon" id="sales-savegoon-groupDetailEditPage" />
            </div>
        </div>
    </form>
</div>
<script type="text/javascript">
    $('#sales-groupDetail-bundlePrice').numberbox({
        min:0,
        precision:2
    });
    var $EditTable = $("#sales-groupDetail-donatedGoods");
    $EditTable.datagrid({
        fit : true,
        striped : true,
        method : 'post',
        rownumbers : true,
        singleSelect : true,
        required: true,
        columns: [[
            {field:'goodsid',title:'商品编号',width:100,isShow:true},
            {field:'goodsname',title:'商品名称',width:190,
                editor:{ type:'goodswidget',
                    options:{
                        required:false,
                        onSelect:function(data){
                            var rows = $EditTable.datagrid('getRows');
                            var pei = donatedEditIndex();
                            var goodsid = data.id;
                            var goodsname = data.name;
                            var rows = $EditTable.datagrid('getRows');
                            for(var i = 0; i < rows.length; i++) {
                                if (pei == i) {
                                    continue;
                                }
                                var row = rows[i];
                                if (row.goodsid == goodsid) {
                                    $.messager.alert("提醒", "商品编号重复！");
                                    var ed = $EditTable.datagrid('getEditor', {index: pei, field: 'goodsid'});
                                    ed.target.widget('clear');
                                    return false;
                                }
                                if (row.goodsname == goodsname) {
                                    $.messager.alert("提醒", "商品名称重复！");
                                    return false;
                                }
                            }
                            var editor = $EditTable.datagrid('getEditor', {index:pei, field:'boxnum'});
                            $(editor.target).text(data.boxnum);
                            var ed = $EditTable.datagrid('getEditor', {index:pei, field:'unitprice'});
                            $(ed.target).text(data.basesaleprice);
                        }
                    }
                }
            },
            {field:'boxnum',title:'箱装量',width:50,isShow:true,
                editor: {
                    type: 'span',
                    options:{
                        precision:0
                    }
                }
            },
            {field:'unitprice',title:'参考单价',width:65,isShow:true,
                editor: {
                    type: 'span',
                    options:{
                        precision:6
                    }
                }
            },
            {field:'oldprice',title:'原价',width:50,hidden:true,
                editor: {
                    type: 'span'
                },
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'unitnum',title:'捆绑数量',width:60,isShow:true,
                formatter:function(value,row,index){
                    return formatterBigNumNoLen(value);
                },
                editor: {
                    type: 'numberbox',
                    options:{
                        required: true,
                        precision:${decimallen},
                        onChange: function(oldvalue,newvalue) {
                            if ((oldvalue != "" && newvalue != "") || (oldvalue == "" && newvalue != "")) {
                                saveRow();
                            }
                        }
                    }
                }

            },
            {field:'unitname',title:'单位',width:40,hidden:true},
            {field:'price',title:'促销单价',width:65,isShow:true,
                formatter:function(value,row,index){
                    return value;
                },
                editor: {
                    type: 'numberbox',
                    options:{
                        required:true,
                        precision:6,
                        onChange: function(oldvalue,newvalue) {
                            if ((oldvalue != "" && newvalue != "") || (oldvalue == "" && newvalue != "")) {
                                saveRow();
                            }
                        }
                    }
                }
            }

        ]],
        onClickRow:function(cindex, data){
            var Rows = $EditTable.datagrid('getRows');
            if(cindex >= 1){
                var editors = $EditTable.datagrid('getEditors', 0);
                if(editors == ""){
                    if(Rows[0].goodsid == "" || Rows[0].goodsid == undefined ){
                        $.messager.alert("提醒", "请从首行开始填写商品信息");
                        $EditTable.datagrid('selectRow',0);
                        return ;
                    }
                }
            }
            var pei = donatedEditIndex();
            if(pei != -1) {
                if(pei == cindex){
                    $EditTable.datagrid('endEdit', pei);
                }else{
                    $EditTable.datagrid('endEdit', pei);
                    $EditTable.datagrid('selectRow', pei);
                    var rowData = $EditTable.datagrid('getSelected');
                    if (rowData.goodsname != undefined && rowData.unitnum != undefined && rowData.price != undefined
                            && rowData.goodsname != "" ) {
                        getGiveGoodsInfo(pei, rowData.goodsname, rowData.unitnum+","+rowData.price);
//                        //获取修改行
//                        var realIndex = -1 ;
//                        for(var i = 0 ;i< Rows.length ;i++){
//                            if( Rows[i].goodsid == undefined || Rows[i].goodsid == "" ){
//                                realIndex = i;
//                                break;
//                            }
//                        }
//                        if(realIndex != cindex){
//                            $.messager.alert("提醒", "请按顺序填写商品信息");
//                            cindex = realIndex ;
//                        }
                        $EditTable.datagrid('beginEdit', cindex);
                        setTimeout(function () {
                            var ed = $EditTable.datagrid('getEditor', {index: cindex, field: 'goodsname'});
                            $(ed.target).goodsWidget('setValue', data.goodsid);
                            $(ed.target).goodsWidget('setText', data.goodsname);
                        }, 100);
                    }else{
                        $EditTable.datagrid('beginEdit', pei);
                    }
                }

            }else{
                if(pei == cindex){
                    $EditTable.datagrid('endEdit', pei);
                }else{
                    $EditTable.datagrid('beginEdit', cindex);
                    setTimeout(function () {
                        var ed = $EditTable.datagrid('getEditor', {index: cindex, field: 'goodsname'});
                        $(ed.target).goodsWidget('setValue', data.goodsid);
                        $(ed.target).goodsWidget('setText', data.goodsname);
                    }, 100);
                }

            }
        }
    }).datagrid("loadData", {'total':2,'rows':[{},{}]}).datagrid('columnMoving');

    function isFocus(){
        var pei = donatedEditIndex();
        if(pei != -1 ){
            saveRow();
        }
        var totalNum = "";
        var totalPrice = "";
        var totaloldprice = "";
        var oldPrice = "";
        var a = "";
        var bundleInfo = $EditTable.datagrid('getRows');
        for(var i = 0 ; a != undefined;i++){
            if(i+1 < bundleInfo.length){
                a = bundleInfo[i+1].goodsid;
            }else{
                a = undefined;
            }
            if(totalPrice == "" ){
                totalPrice = bundleInfo[i].price*Number(bundleInfo[i].unitnum);
                oldPrice = bundleInfo[i].oldprice;
                totaloldprice = oldPrice*Number(bundleInfo[i].unitnum);
            }else{
                totalPrice = Number(totalPrice) +  Number(bundleInfo[i].price)*Number(bundleInfo[i].unitnum);
                oldPrice = Number(oldPrice) +  Number(bundleInfo[i].oldprice);
                totaloldprice = Number(totaloldprice) +  bundleInfo[i].oldprice*Number(bundleInfo[i].unitnum);
            }
        }
        $("#sales-groupDetail-bundleOldPrice").val(totaloldprice);
        $("#sales-groupDetail-bundlePrice").numberbox('setValue',totalPrice);
        $("#oldprice").val(oldPrice);
    }

    function donatedEditIndex() {
        var rows = $EditTable.datagrid('getRows');
        for(var i = 0; i < rows.length; i++) {
            var editors = $EditTable.datagrid('getEditors', i);
            if(editors.length > 0) {
                return i;
            }
        }
        return -1;
    }
    function saveRow(){
        var rows = $EditTable.datagrid('getRows');
        for(var i = 0; i < rows.length; i++) {
            var editors = $EditTable.datagrid('getEditors', i);
            if(editors.length > 0) {
                $EditTable.datagrid('endEdit', i);
                $EditTable.datagrid('selectRow', i);
                var rowData = $EditTable.datagrid('getSelected');
                if(rowData.unitnum == ""){
                    $.messager.alert("提醒", "请填写赠送/捆绑数量");
                    $EditTable.datagrid('beginEdit', i);
                }else if(rowData.unitnum == undefined){
                    $EditTable.datagrid('beginEdit', i);
                }
                else {
                    var dataId = rowData.goodsname +":"+ rowData.price ;
                    getGiveGoodsInfo(i,dataId , rowData.unitnum);
//                    goodInfoEditIndex = undefined;
                }
            }

        }
    }



    $(function(){
        //页面加载完毕后失去焦点事件
        $('#sales-groupDetail-groupid').blur(function (){
            var groupid =$("#sales-groupDetail-groupid").val();
            $.ajax({
                url:'sales/auditbill.do',
                dataType:'json',
                data : 'groupid='+groupid,
                success: function(json){
                    if(json.flag && groupid != ""){
                        $("#promotionValidate").html("<font color='red'>该编号已存在</font>");
                    }else if(groupid != ""){
                        $("#promotionValidate").html("<font color='green'>促销编号命名成功</font>");
                    }
                }

            });
        });

        $("#sales-savegoon-groupDetailEditPage").click(function(){
            addSaveBundleDetail(true);
        });
        $("#sales-saveaddgood-groupDetailDetailEditPage").click(function(){
            addSaveBundleDetail(true);
            var billName = $("#sales-groupDetail-groupName").val();
            var limitnum = $("#sales-groupDetail-limitNum").val();
            if(billName != "" && limitnum != ""){
                var row = $wareList.datagrid('getSelected');
                var rowIndex = $wareList.datagrid('getRowIndex', row);
                beginAddGroup2(2,rowIndex);
            }

        });

    });
</script>
</body>
</html>