<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>

<body>
<table id="sales-reject-order-detail"></table>
<script type="text/javascript">
    $(function(){
        $("#sales-reject-order-detail").datagrid({
            columns:[[
                {field:'ck', checkbox:true},
                {field:'id', title:'明细编号', width:60,sortable:true,hidden:true},
                {field:'orderid', title:'订单编号', width:120,sortable:true},
                {field:'goodsid', title:'商品编码', width:70,sortable:true},
                {field:'goodsname', title:'商品名称', width:150},
                {field:'boxnum', title:'箱装量', width:60,align:'right',
                    formatter:function(value,row,index){
                        return formatterBigNum(value);
                    }
                },
                {field:'unitname', title:'单位', width:40},
                {field:'unitnum', title:'数量', width:60,align:'right',
                    formatter:function(value,row,index){
                        return formatterBigNum(value);
                    }
                },
                {field:'taxprice', title:'单价', width:60,align:'right',
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
                {field:'taxamount',title:'金额',width:60,align:'right',
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
                {field:'remark',title:'备注',width:80,align:'left'}
            ]],
            fit:true,
            method:'post',
            rownumbers:true,
            idField:'id',
            singleSelect:false,
            selectOnCheck:true,
            checkOnSelect:true,
            data: JSON.parse('${detailList}'),
            showFooter:true,
            onBeforeLoad:function(){
            },
            onLoadSuccess:function(){
                $(this).datagrid("resize");
                initSelect();
            },
            onCheckAll:function(rows){
                getTotalDetailAmount();
            },
            onUncheckAll:function(){
                $("#sales-datagrid-orderListPage").datagrid("reloadFooter",[{orderid:'选中合计',taxamount:0},{orderid:'合计',taxamount:${totalamount}}] );
            },
            onCheck:function(rowIndex,rowData){
                getTotalDetailAmount();
            },
            onUncheck:function(rowIndex,rowData){
                getTotalDetailAmount();
            }
        });
    });
    function initSelect(){
        var uncheckedstr = $("#sales-nochecked-detail-rejectbillDetailPage").val();
        var data = null;
        if(null!=uncheckedstr && uncheckedstr!=""){
            data = $.parseJSON(uncheckedstr);
        }else{
            data = [];
        }
        $("#sales-reject-order-detail").datagrid("checkAll");
        for(var j=0;j<data.length;j++){
            if(data[j].orderid=="${id}"){
                var rowIndex = $("#sales-reject-order-detail").datagrid("getRowIndex",data[j].id);
                $("#sales-reject-order-detail").datagrid("uncheckRow",rowIndex);
            }
        }

    }

    function getTotalDetailAmount(){
        var rows = $("#sales-reject-order-detail").datagrid("getChecked");
        var totalamount = 0;
        for(var i=0;i<rows.length;i++){
            totalamount = Number(totalamount) + Number(rows[i].taxamount);
        }
        if(rows.length>0){
            $("#sales-reject-order-detail").datagrid("reloadFooter",[{orderid:'选中合计',taxamount:totalamount},{orderid:'合计',taxamount:${totalamount}}] );
        }else{
            $("#sales-reject-order-detail").datagrid("reloadFooter",[{orderid:'选中合计',taxamount:0},{orderid:'合计',taxamount:${totalamount}}] );
        }
    }
    //获取未选中对象
    function addNocheckedDetail(){
        var rows = $("#sales-reject-order-detail").datagrid("getRows");
        var checkedRows = $("#sales-reject-order-detail").datagrid("getChecked");
        var unChecked = [];
        var unCheckAmount = 0;
        for(var i=0;i<rows.length;i++){
            var unflag = true;
            for(var j=0;j<checkedRows.length;j++){
                if(rows[i].id==checkedRows[j].id){
                    unflag = false;
                    break;
                }
            }
            if(unflag){
                var data = {orderid:rows[i].orderid,id:rows[i].id};
                unChecked.push(rows[i]);
                unCheckAmount = Number(unCheckAmount) + Number(rows[i].taxamount);
            }
        }
        var uncheckedstr = $("#sales-nochecked-detail-rejectbillDetailPage").val();
        var unCheckedArr = null;
        var data = [];
        if(null!=uncheckedstr && uncheckedstr!=""){
            unCheckedArr = $.parseJSON(uncheckedstr);
        }else{
            unCheckedArr = [];
        }
        for(var i=0;i<unCheckedArr.length;i++){
            var detail = unCheckedArr[i];
            if(unCheckedArr[i].orderid!="${id}"){
                data.push(unCheckedArr[i]);
            }
        }
        if(unChecked.length>0){
            for(var i=0;i<unChecked.length;i++){
                var object = {orderid:unChecked[i].orderid,id:unChecked[i].id};
                data.push(object);
            }
        }
        var invoiceamount = 0;
        for(var j=0;j<checkedRows.length;j++){
            invoiceamount = Number(invoiceamount)+Number(checkedRows[j].taxamount);
        }
        var rowIndex = $("#sales-datagrid-orderListPage").datagrid("getRowIndex","${id}");
        var datarows = $("#sales-datagrid-orderListPage").datagrid("getRows");
        var rowData = datarows[rowIndex];
        rowData.field05 = invoiceamount;
        $("#sales-datagrid-orderListPage").datagrid('updateRow',{index:rowIndex, row:rowData});
        $("#sales-nochecked-detail-rejectbillDetailPage").val(JSON.stringify(data));
        getTotalAmount();
        //单据未选择金额
        var uncheckAmountstr = $("#sales-checkedbill-uncheckamount-rejectbillDetailPage").val();
        var uncheckAmountArr = null;
        if(null!=uncheckAmountstr && uncheckAmountstr!=""){
            uncheckAmountArr = $.parseJSON(uncheckAmountstr);
        }else{
            uncheckAmountArr = [];
        }
        var uncheckamountflag = true;
        for(var i=0;i<uncheckAmountArr.length;i++){
            if(uncheckAmountArr[i].id=="${id}"){
                uncheckamountflag = false;
                uncheckAmountArr[i].uncheckamount = unCheckAmount;
                break;
            }
        }
        if(uncheckamountflag){
            var object = {id:"${id}",uncheckamount:unCheckAmount};
            uncheckAmountArr.push(object);
        }
        $("#sales-checkedbill-uncheckamount-rejectbillDetailPage").val(JSON.stringify(uncheckAmountArr));
        $("#sales-panel-selectDetailPage").dialog("close");
    }
</script>
</body>
</html>
