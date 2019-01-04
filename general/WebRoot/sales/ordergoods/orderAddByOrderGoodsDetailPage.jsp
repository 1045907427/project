<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>销售订货单明细列表页面</title>
    <%@include file="/include.jsp" %>

</head>
<body>
<%--<div id="sales-queryDiv-orderAddByOrderGoodsDetailPage" style="height:auto;padding:0px">--%>
    <%--<div class="buttonBG" id="sales-buttons-orderAddByOrderGoodsDetailPage"></div>--%>
    <%--<form id="sales-queryForm-orderAddByOrderGoodsDetailPage" method="post">--%>

    <%--</form>--%>
<%--</div>--%>
<div id="sales-orderAddByOrderGoodsDetailPage-layout" class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center'">
        <table id="sales-datagrid-orderAddByOrderGoodsDetailPage" data-options="border:false"></table>
    </div>
</div>


<script type="text/javascript">
    var editfiled = null;
    var editIndex = undefined;
    var $goodslist = $('#sales-datagrid-orderAddByOrderGoodsDetailPage');
    function endEditing(){
        if (editIndex == undefined){
            return true
        }
        var row = $goodslist.datagrid('getRows')[editIndex];
        if(editfiled=='unitnum'){
            var unitnum = row.unitnum;
            var ed = $goodslist.datagrid('getEditor', {index:editIndex,field:"unitnum"});
            var newnum=0;
            if(null != ed){
                getNumberBoxObject(ed.target).focus();
                newnum=getNumberBoxObject(ed.target).val();
            }
            if(isNaN(newnum)){
                newnum=0;
            }
            if(parseFloat(newnum)>parseFloat(row.notorderunitnum)){
                $.messager.alert("提醒", "商品数量不能大于未生成订单的数量"+parseFloat(row.notorderunitnum));
                return;
            }
            if(undefined != newnum && "" != newnum){
                var num=parseFloat(newnum)%parseFloat(row.goodsInfo.boxnum);
                var box=parseInt(newnum/row.goodsInfo.boxnum);
                row.notorderbox=box;
                row.notorderovernum=num;
                $goodslist.datagrid('endEdit', editIndex);
                unitnumChange(row);
            }else{
                return false;
            }
        }else if(editfiled=='notorderbox'){
            var ed = $goodslist.datagrid('getEditor', {index:editIndex,field:"notorderbox"});
            var newaddbox=0;
            if(null != ed){
                getNumberBoxObject(ed.target).focus();
                newaddbox=getNumberBoxObject(ed.target).val();
            }
            var totalnum=parseFloat(row.goodsInfo.boxnum)*parseFloat(newaddbox)+parseFloat(row.notorderovernum);
            if(parseFloat(totalnum)>parseFloat(row.notorderunitnum)){
                $.messager.alert("提醒", "商品数量不能大于初始数量"+parseFloat(row.notorderunitnum));
                return;
            }
            if(undefined != newaddbox ){
                $goodslist.datagrid('endEdit', editIndex);
                if("" != newaddbox){
                    row.unitnum=totalnum;
                    unitnumChange(row);
                }

            }else{
                return false;
            }
        }else if(editfiled=='notorderovernum'){
            var ed = $goodslist.datagrid('getEditor', {index:editIndex,field:"notorderovernum"});
            var newaddnum=0;
            if(null != ed){
                getNumberBoxObject(ed.target).focus();
                newaddnum=getNumberBoxObject(ed.target).val();
            }
            if(isNaN(newaddnum)){
                newaddnum=0;
            }
            var notorderbox=row.notorderbox==''?0:row.notorderbox;
            var totalnum=parseFloat(row.goodsInfo.boxnum)*parseFloat(notorderbox)+parseFloat(newaddnum);

            if(parseFloat(totalnum)>parseFloat(row.notorderunitnum)){
                $.messager.alert("提醒", "商品数量不能大于初始数量"+parseFloat(row.notorderunitnum));
                return;
            }
            if(undefined != newaddnum ){
                $goodslist.datagrid('endEdit', editIndex);
                if("" != newaddnum){
                    if(parseFloat(newaddnum)>=parseFloat(row.goodsInfo.boxnum)){
                        var num=parseFloat(newaddnum)%parseFloat(row.goodsInfo.boxnum);
                        var box=parseInt(newaddnum/row.goodsInfo.boxnum);
                        row.notorderbox=parseFloat(notorderbox)+parseFloat(box);
                        row.notorderovernum=num;
                    }
                    row.unitnum=totalnum;
                    unitnumChange(row);
                }
            }else{
                return false;
            }
        }
//        if(row.unitnum != unitnum){
//            row.isedit = "1";
//        }else{
//            row.isedit = "0";
//        }
        $goodslist.datagrid('updateRow',{index:editIndex, row:row});
        if(row.isedit == "1"){
            $goodslist.datagrid('checkRow',editIndex);
        }else{
            $goodslist.datagrid('uncheckRow',editIndex);
        }
        editIndex = undefined;
        return true;
    }
    function onClickRow(index, field){
//        if (endEditing()){
//            editIndex = index;
//            $goodslist.datagrid('beginEdit', editIndex);
//            var ed = $goodslist.datagrid('getEditor', {index:editIndex,field:"unitnum"});
//            if(null != ed){
//                getNumberBoxObject(ed.target).focus();
//            }
//        }

        if (endEditing()){
            editIndex = index;
            if(field=='unitnum' || field=='notorderbox'||field=='notorderovernum'){
                editfiled=field;
            }
            $goodslist.datagrid('selectRow', index).datagrid('editCell', {index:index,field:editfiled})
            var ed = $goodslist.datagrid('getEditor', {index:editIndex,field:editfiled});
            if(null != ed){
                getNumberBoxObject(ed.target).focus();
            }
        }
    }
    var initQueryJSON = $("#sales-queryForm-orderAddByOrderGoodsDetailPage").serializeJSON();
    $(function () {
        $("#sales-datagrid-orderAddByOrderGoodsDetailPage").datagrid({
            fit: true,
            method: 'post',
            rownumbers: true,
            pagination: false,
            idField: 'id',
            singleSelect:false,
            checkOnSelect:true,
            selectOnCheck:true,
            sortName: 'addtime',
            sortOrder: 'desc',
//            url: 'sales/getOrderGoodsList.do',
            data: JSON.parse('${goodsJson}'),
            queryParams: initQueryJSON,
            frozenColumns:[[{field: 'id', title: '明细编号', hidden: true}]],
            columns:[[
                {field: 'ck', checkbox: true},
                {field: 'goodsid', title: '商品编码', width: 70, align: ' left', sortable: true},
                {
                    field: 'goodsname', title: '商品名称', width: 250, align: 'left', aliascol: 'goodsid',
                    formatter: function (value, rowData, rowIndex) {
                        if (rowData.goodsInfo != null) {
                            if (rowData.isdiscount == '1') {
                                return "（折扣）" + rowData.goodsInfo.name;
                            } else if (rowData.isdiscount == '2') {
                                return "（折扣）" + rowData.goodsInfo.name;
                            } else {
                                if (rowData.deliverytype == '1') {
                                    return "<font color='blue'>&nbsp;赠 </font>" + rowData.goodsInfo.name;
                                } else if (rowData.deliverytype == '2') {
                                    return "<font color='blue'>&nbsp;捆绑 </font>" + rowData.goodsInfo.name;
                                } else {
                                    return '<a id="sales-historyprice-orderpage" gid="' + rowData.goodsid + '" >' + rowData.goodsInfo.name + '</a>';
                                }
                            }
                        }else if (rowData.goodsname) {
                            return rowData.goodsname;
                        }else if (rowData.financeinfo) {
                            return rowData.financeinfo;
                        } else {
                            return '<a id="sales-historyprice-orderpage" gid="' + rowData.goodsid + '" ></a>';
                        }
                    }
                },
                {
                    field: 'spell', title: '助记符', width: 80, aliascol: 'goodsid', hidden: true, align: 'left',
                    formatter: function (value, rowData, rowIndex) {
                        if (rowData.goodsInfo != undefined) {
                            return rowData.goodsInfo.spell;
                        } else {
                            return "";
                        }
                    }
                },
                {field: 'shopid', title: '店内码', width: 80, aliascol: 'goodsid', hidden: true, align: 'left'},
                {
                    field: 'barcode', title: '条形码', width: 90, align: 'left', aliascol: 'goodsid',
                    formatter: function (value, row, rowIndex) {
                        if (row.isdiscount != '1' && row.goodsInfo != null) {
                            return row.goodsInfo.barcode;
                        } else {
                            return "";
                        }
                    }
                },
                {
                    field: 'brandName', title: '商品品牌', width: 60, align: 'left', aliascol: 'goodsid', hidden: true,
                    formatter: function (value, rowData, rowIndex) {
                        if (rowData.goodsInfo != null) {
                            return rowData.goodsInfo.brandName;
                        } else {
                            return "";
                        }
                    }
                },
                {
                    field: 'boxnum', title: '箱装量', aliascol: 'goodsid', width: 45, align: 'right',
                    formatter: function (value, row, rowIndex) {
                        if (row.isdiscount != '1' && row.goodsInfo != null) {
                            return formatterBigNumNoLen(row.goodsInfo.boxnum);
                        } else {
                            return "";
                        }
                    }
                },
                {field: 'unitname', title: '单位', width: 35, align: 'left'},
                {
                    field: 'usablenum', title: '可用量', width: 60, align: 'right', isShow: true, sortable: true,
                    formatter: function (value, row, index) {
                        if (((row.isdiscount == '0' || row.isdiscount == '') && row.goodsInfo != null) || row.goodsid == "选中合计" || row.goodsid == "合计") {
                            return formatterBigNumNoLen(value);
                        } else {
                            return "";
                        }
                    }
                },
//                {
//                    field: 'maxnum', title: '订货数量', width: 60, align: 'right', sortable: true,
//                    formatter: function (value, row, index) {
//                        return formatterBigNumNoLen(row.unitnum);
//                    }
//                },
                {
                    field: 'unitnum', title: '数量', width: 60, align: 'right', sortable: true,
                    editor:{
                        type:'numberbox',
                        options:{
                            min: 0,
                            precision: 0
                        }
                    },
                    formatter: function (value, row, index) {
                        return formatterBigNumNoLen1(value);
                    },
                    styler: function (value, row, index) {
//                        var status = $("#sales-customer-status").val();
//                        if (row.goodsid != null && row.goodsid != "合计" && row.goodsid != "选中合计" && (status == null || status == '1' || status == "2") && Number(row.usablenum) < Number(value)) {
//                            return 'background-color:red;';
//                        }
                    }
                },
                {
                    field: 'notorderbox', title: '箱数', width: 60, align: 'right', sortable: true,
                    editor:{
                        type:'numberbox',
                        options:{
                            min: 0,
                            precision: 0
                        }
                    },
                    formatter: function (value, row, index) {
                        return formatterBigNumNoLen1(value);
                    },
                },
                {
                    field: 'notorderovernum', title: '个数', width: 60, align: 'right', sortable: true,
                    editor:{
                        type:'numberbox',
                        options:{
                            min: 0,
                            precision: 0
                        }
                    },
                    formatter: function (value, row, index) {
                        return formatterBigNumNoLen(row.notorderovernum);
                    },
                },
//                {field: 'notorderbox', title: '未生成订单箱数', width: 35, align: 'left',hidden:true},
                {field: 'notorderunitnum', title: '未生成订单数量', width: 35, align: 'left',hidden:true},
                {
                    field: 'referenceprice', title: '参考价', width: 60, align: 'right', isShow: true, sortable: true,
                    formatter: function (value, row, index) {
                        if (row.isdiscount == '0' ) {
                            return '<a id="sales-historyprice-orderpage" gid="' + row.goodsid + '" >' + formatterMoney(value) + '</a>';
                        }else{
                            return '';
                        }

                    }
                },
                {
                    field: 'taxprice', title: '单价', width: 60, align: 'right', sortable: true,
                    formatter: function (value, row, index) {
                        if (row.isdiscount != '1' && row.isdiscount != '2') {
                            if (parseFloat(value) > parseFloat(row.oldprice)) {
                                if (parseFloat(value) < parseFloat(row.lowestsaleprice)) {
                                    return "<font color='blue' style='cursor: pointer;' title='原价:" + formatterMoney(row.oldprice) + ",最低销售价:" + formatterMoney(row.lowestsaleprice) + "'>" + formatterMoney(value) + "</font></a>";
                                } else {
                                    return "<font color='blue' style='cursor: pointer;' title='原价:" + formatterMoney(row.oldprice) + "'>" + formatterMoney(value) + "</font>";
                                }
                            }
                            else if (parseFloat(value) < parseFloat(row.oldprice)) {
                                if (parseFloat(value) < parseFloat(row.lowestsaleprice)) {
                                    return "<font color='red' style='cursor: pointer;' title='原价:" + formatterMoney(row.oldprice) + ",最低销售价:" + formatterMoney(row.lowestsaleprice) + "'>" + formatterMoney(value) + "</font>";
                                } else {
                                    return "<font color='red' style='cursor: pointer;' title='原价:" + formatterMoney(row.oldprice) + "'>" + formatterMoney(value) + "</font>";
                                }
                            }
                            else {
                                if (parseFloat(value) < parseFloat(row.lowestsaleprice)) {
                                    return "<font style='cursor: pointer;' title='最低销售价:" + formatterMoney(row.lowestsaleprice) + "'>" + formatterMoney(value) + "</font>";
                                } else {
                                    if (parseFloat(value) != parseFloat(row.demandprice) && parseFloat(row.demandprice) > 0) {
                                        return "<font  style='cursor: pointer;' title='要货价:" + formatterMoney(row.demandprice) + "'>" + formatterMoney(value) + "</font>";
                                    } else {
                                        return formatterMoney(value);
                                    }
                                }
                            }
                        } else {
                            return "";
                        }
                    },
                    styler: function (value, row, index) {
                        if (row.isdiscount != '1' && row.isdiscount != '2') {
                            if (parseFloat(value) < parseFloat(row.lowestsaleprice)) {
                                return 'background-color:yellow;';
                            } else {
                                if (parseFloat(value) != parseFloat(row.demandprice) && parseFloat(row.demandprice) > 0) {
                                    return 'background-color:#F4AE8A;';
                                }
                            }
                        }
                    }
                },

                {
                    field: 'boxprice', title: '箱价', width: 60, aliascol: 'taxprice', align: 'right',
                    formatter: function (value, row, index) {
                        if (row.isdiscount != '1' && row.isdiscount != '2') {
                            return formatterMoney(value);
                        } else {
                            return "";
                        }
                    }
                },
                {
                    field: 'taxamount', title: '金额', width: 60, align: 'right', sortable: true,
                    formatter: function (value, row, index) {
                        return formatterMoney(value);
                    }
                },

                {field: 'auxnumdetail', title: '辅数量', width: 60, align: 'right'},
                {field: 'remark', title: '备注', width: 200, align: 'left'}
            ]],
            onClickCell: function(index, field, value){
                endEditing();
            },
            onDblClickCell: function(index, field, value){
                onClickRow(index, field);
            }
        });
    });
    function unitnumChange(row){ //数量改变方法
        var orderid = '${orderGoods.id}';
        var goodsId = row.goodsid;
        var unitnum = row.unitnum;
        if(isNaN(unitnum)){
            unitnum=0;
        }
        var customerId = "${orderGoods.customerid}";
        var date = "${orderGoods.businessdate}";
        var price = row.taxprice;

        var url = "sales/getAuxUnitNumAndPrice.do"
        var data = {id:goodsId,unitnum:unitnum,cid:customerId,date:date,free:"1",orderid:orderid,price:price,taxpricechange:1};

        $.ajax({
            url:url,
            dataType:'json',
            type:'post',
            async:false,
            data:data,
            success:function(json){
                row.taxamount=json.taxamount;
                row.notaxamount=json.notaxamount;
                row.tax=json.tax;
                row.auxnum=json.auxnum;
                row.overnum=json.overnum;
                row.unitnum=json.unitnum;
                row.auxnumdetail=row.auxnum + row.auxunitname + row.overnum + row.unitname
                $goodslist.datagrid('updateRow',{index:editIndex, row:row});

            }
        });
    }
    function addOrder(){
        var rows = $("#sales-datagrid-orderAddByOrderGoodsDetailPage").datagrid('getChecked');
        if(rows.length==0){
            $.messager.alert("提醒", "请选择要生成订单的数据");
            return;
        }
        for(var i=0;i<rows.length;i++){
            if(rows[i].unitnum==0||rows[i].unitnum==''){
                $.messager.alert("提醒", "要生成订单的商品数量不能为空");
                return;
            }
        }

        $.ajax({
            url:"sales/addOrderByOrderGoodsBill.do",
            dataType:'json',
            type:'post',
            async:false,
            data:{
                id:'${orderGoods.id}',
                goodsJson:JSON.stringify(rows)
            },
            success:function(json){
                if(json.flag){
                    $.messager.alert("提醒", "生成订单"+json.id+"成功");
                    $("#sales-datagrid-orderAddByOrderGoodsPage").datagrid('reload');
//                    $("#sales-panel-orderGoodsPage").panel('refresh', 'sales/orderGoodsEditPage.do?id='+$("#sales-id-orderGoodsAddPage").val());//'sales/orderViewPage.do?id='+ json.backid);
                    $('#sales-panel-orderAddDetailPage1').dialog("close");
                }else{
                    $.messager.alert("提醒", "生成订单失败");
                }
            }
        })
    }
</script>
</body>
</html>
