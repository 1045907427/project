<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>

<body>
<table id="account-salesadvancebill-saleout-detail"></table>
<script type="text/javascript">
    $(function(){
        $("#account-salesadvancebill-saleout-detail").datagrid({
            columns:[[
                {field:'ck', checkbox:true},
                {field:'id', title:'明细编号', width:60,sortable:true,hidden:true},
                {field:'billid', title:'编号', width:130,sortable:true},
                {field:'goodsid', title:'商品编码', width:70,sortable:true},
                {field:'goodsname', title:'商品名称', width:150},
                {field:'boxnum', title:'箱装量', width:60,align:'right',
                    formatter:function(value,row,index){
                        return formatterBigNumNoLen(value);
                    }
                },
                {field:'unitname', title:'单位', width:40},
                {field:'unitnum', title:'数量', width:60,align:'right',
                    formatter:function(value,row,index){
                        if(row.isdiscount=='0'){
                            return formatterBigNumNoLen(value);
                        }
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
                $("#account-salesadvancebill-saleout-detail").datagrid("resize");
                initSelect();
            },
            onCheckAll:function(rows){
                getTotalDetailAmount();
            },
            onUncheckAll:function(){
                $("#account-billDatagrid-saleoutSourcePage").datagrid("reloadFooter",[{billid:'选中合计',taxamount:0},{billid:'合计',taxamount:${totalamount}}] );
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
        var uncheckedstr = $("#account-nochecked-detail-salesAdvanceBillDetailPage").val();
        var data = null;
        if(null!=uncheckedstr &&　uncheckedstr!=""){
            data = $.parseJSON(uncheckedstr);
        }else{
            data = [];
        }
        $("#account-salesadvancebill-saleout-detail").datagrid("checkAll");
        for(var j=0;j<data.length;j++){
            if(data[j].billid=="${id}"){
                var rowIndex = $("#account-salesadvancebill-saleout-detail").datagrid("getRowIndex",data[j].id);
                $("#account-salesadvancebill-saleout-detail").datagrid("uncheckRow",rowIndex);
            }
        }

    }

    function getTotalDetailAmount(){
        var rows = $("#account-salesadvancebill-saleout-detail").datagrid("getChecked");
        var totalamount = 0;
        for(var i=0;i<rows.length;i++){
            totalamount = Number(totalamount) + Number(rows[i].taxamount);
        }
        if(rows.length>0){
            $("#account-salesadvancebill-saleout-detail").datagrid("reloadFooter",[{billid:'选中合计',taxamount:totalamount},{billid:'合计',taxamount:${totalamount}}] );
        }else{
            $("#account-salesadvancebill-saleout-detail").datagrid("reloadFooter",[{billid:'选中合计',taxamount:0},{billid:'合计',taxamount:${totalamount}}] );
        }
    }
    //获取未选中对象
    function addNocheckedDetail(){
        var rows = $("#account-salesadvancebill-saleout-detail").datagrid("getRows");
        var checkedRows = $("#account-salesadvancebill-saleout-detail").datagrid("getChecked");
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
                var data = {billid:rows[i].billid,id:rows[i].id};
                unChecked.push(rows[i]);
                unCheckAmount = Number(unCheckAmount) + Number(rows[i].taxamount);
            }
        }
        var uncheckedstr = $("#account-nochecked-detail-salesAdvanceBillDetailPage").val();
        var unCheckedArr = null;
        var data = [];
        if(null!=uncheckedstr &&　uncheckedstr!=""){
            unCheckedArr = $.parseJSON(uncheckedstr);
        }else{
            unCheckedArr = [];
        }
        for(var i=0;i<unCheckedArr.length;i++){
            var detail = unCheckedArr[i];
            if(unCheckedArr[i].billid!="${id}"){
                data.push(unCheckedArr[i]);
            }
        }
        if(unChecked.length>0){
            for(var i=0;i<unChecked.length;i++){
                var object = {billid:unChecked[i].billid,id:unChecked[i].id};
                data.push(object);
            }
        }
        var invoiceamount = 0;
        for(var j=0;j<checkedRows.length;j++){
            invoiceamount = Number(invoiceamount)+Number(checkedRows[j].taxamount);
        }
        var rowIndex = $("#account-billDatagrid-saleoutSourcePage").datagrid("getRowIndex","${id}");
        var datarows = $("#account-billDatagrid-saleoutSourcePage").datagrid("getRows");
        var rowData = datarows[rowIndex];
        rowData.invoiceamount = invoiceamount;
        $("#account-billDatagrid-saleoutSourcePage").datagrid('updateRow',{index:rowIndex, row:rowData});
        $("#account-nochecked-detail-salesAdvanceBillDetailPage").val(JSON.stringify(data));
        getCustomerCapital(rowData.customerid,rowData.headcustomerid);
        //单据未选择金额
        var uncheckAmountstr = $("#account-checkedbill-uncheckamount-salesAdvanceBillDetailPage").val();
        var uncheckAmountArr = null;
        if(null!=uncheckAmountstr &&　uncheckAmountstr!=""){
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
        $("#account-checkedbill-uncheckamount-salesAdvanceBillDetailPage").val(JSON.stringify(uncheckAmountArr));
        $("#account-panel-selectDetailPage").dialog("close");
    }
</script>
</body>
</html>
