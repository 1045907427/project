<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>

<body>
<table id="sales-datagrid-rejectbilldetail"></table>
<div id="sales-toolbar-datagrid-rejectbilldetail" style="padding-left: 10px;">
    <form action="" id="sales-form-rejectbill-order" method="post">
        <input type="hidden" value="${date }" name="rejectBill.businessdate" />
        <table  border="0">
            <tr>
                <td width="100">客户名称:</td>
                <td style="text-align: left;"><input type="text" id="select-customerid-rejectbillDetail" class="no_input" name="rejectBill.customerid" value="${customerid}" readonly="readonly"/></td>
                <td width="100">客户业务员:</td>
                <td><input type="text" id="sales-salesuser-rejectbillDetail" class="no_input" name="rejectBill.salesuser" value="${customerMap.salesuser}" readonly="readonly"/></td>
                <td width="100">销售部门:</td>
                <td><input type="text" id="sales-salesdept-rejectbillDetail" class="no_input" name="rejectBill.salesdept" value="${order.salesdept}"/></td>
            </tr>
            <tr>
                <td width="100">入库仓库：</td>
                <td style="text-align: left;"><input type="text" id="sales-storageid-rejectbillDetail" name="rejectBill.storageid" value="${order.storageid}"/></td>
                <td width="100">总金额:</td>
                <td><input type="text" id="select-amount-all" style="width: 130px;" class="no_input" readonly="readonly" value="${totalamount }"/></td>
                <td width="120">选中金额:</td>
                <td style="text-align: left;"><input type="text" id="select-amount" style="width: 130px;" class="no_input" readonly="readonly" value="${totalamount }"/></td>
            </tr>
        </table>
    </form>
</div>
<div id="rejectbillDetail-salesinvoice-old-list"></div>
<script type="text/javascript">
$(function(){
    //客户
    $("#select-customerid-rejectbillDetail").widget({
        referwid:'RL_T_BASE_SALES_CUSTOMER',
        singleSelect:true,
        width:150
    });
    //客户业务员
    $("#sales-salesuser-rejectbillDetail").widget({
        name:'t_sales_rejectbill',
        col:'salesuser',
        required:true,
        width:130,
        singleSelect:true
    });
    //销售部门
    $("#sales-salesdept-rejectbillDetail").widget({
        name:'t_sales_rejectbill',
        col:'salesdept',
       // required:true,
        width:130,
        singleSelect:true,
        setValueSelect:true,
        onSelect:function(data){
            if(null!=data.storageid){
                $("#sales-storageid-rejectbillDetail").widget("setValue",data.storageid);
            }else{
                $("#sales-storageid-rejectbillDetail").widget("clear");
            }
        }
    });
    //仓库
    $("#sales-storageid-rejectbillDetail").widget({
        referwid:'RL_T_BASE_STORAGE_INFO',
        width:150,
        singleSelect:true
    });

    $("#sales-datagrid-rejectbilldetail").datagrid({
        frozenColumns:[[
            {field:'ck',checkbox:true}
        ]],
        columns:[[
            {field:'orderid', title:'订单编号', width:120,sortable:true},
            {field:'goodsid',title:'商品编码',width:70,align:' left'},
            {field:'goodsname', title:'商品名称', width:220,align:'left',aliascol:'goodsid',
                formatter:function(value,rowData,rowIndex){
                    if(rowData.goodsInfo != null){
                        return rowData.goodsInfo.name;
                    }else{
                        return "";
                    }
                }
            },
            {field:'barcode', title:'条形码',width:90,align:'left',aliascol:'goodsid',
                formatter:function(value,rowData,rowIndex){
                    if(rowData.goodsInfo != null){
                        return rowData.goodsInfo.barcode;
                    }else{
                        return "";
                    }
                }
            },
            {field:'itemno', title:'商品货位',width:60,aliascol:'goodsid',
                formatter:function(value,rowData,rowIndex){
                    if(rowData.goodsInfo != null){
                        return rowData.goodsInfo.itemno;
                    }else{
                        return "";
                    }
                }
            },
            {field:'brandName', title:'商品品牌',width:80,align:'left',aliascol:'goodsid',hidden:true,
                formatter:function(value,rowData,rowIndex){
                    if(rowData.goodsInfo != null){
                        return rowData.goodsInfo.brandName;
                    }else{
                        return "";
                    }
                }
            },
            {field:'deliverytype', title:'商品类型',width:80,align:'left',hidden:true},
            {field:'boxnum', title:'箱装量',aliascol:'goodsid',width:45,align:'right',
                formatter:function(value,rowData,rowIndex){
                    if(rowData.goodsInfo != null){
                        return formatterBigNum(rowData.goodsInfo.boxnum);
                    }else{
                        return "";
                    }
                }
            },
            {field:'unitname', title:'单位',width:35,align:'left'},
            {field:'unitnum', title:'数量',width:60,align:'right',
                formatter:function(value,row,index){
                    return formatterBigNumNoLen(value);
                }
            },
            {field:'taxprice', title:'单价',width:60,align:'right',
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'boxprice', title:'箱价',aliascol:'goodsid',width:60,align:'right',
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'taxamount', title:'金额',width:60,align:'right',
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'taxtype', title:'税种',width:70,align:'left', hidden:true,
                formatter:function(value,row,index){
                    return row.taxtypename;
                }
            },
            {field:'notaxprice', title:'未税单价',width:80,align:'right', hidden:true,
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'notaxamount', title:'未税金额',width:80,align:'right', hidden:true,
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'tax', title:'税额',width:80,align:'right', hidden:true,
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'auxunitid', title:'辅单位',width:60,align:'right',hidden:true,
                formatter:function(value,row,index){
                    return row.auxunitname;
                }
            },
            {field:'auxnum', title:'辅单位主数量',width:60,align:'right',hidden:true,
                formatter:function(value,row,index){
                    return formatterNum(value);
                }
            },
            {field:'auxremainder', title:'辅单位余数',width:60,align:'right',hidden:true,
                formatter:function(value,row,index){
                    return formatterBigNumNoLen(value);
                }
            },
            {field:'auxnumdetail', title:'辅数量',width:60,align:'right'},
            {field:'remark', title:'备注',width:100,align:'left'}
        ]],
        fit:true,
        method:'post',
        rownumbers:true,
        idField:'id',
        singleSelect:false,
        selectOnCheck:true,
        checkOnSelect:true,
        toolbar:"#sales-toolbar-datagrid-rejectbilldetail",
        data: JSON.parse('${liststr}'),
        onBeforeLoad:function(){
        },
        onLoadSuccess:function(){
            $(this).datagrid("checkAll");
        },
        onCheckAll:function(rows){
            countTaxamount();
        },
        onUncheckAll:function(){
            countTaxamount();
        },
        onCheck:function(rowIndex,rowData){
            countTaxamount();
        },
        onUncheck:function(rowIndex,rowData){
            countTaxamount();
        }
    });
});

function countTaxamount(){
    var rows = $("#sales-datagrid-rejectbilldetail").datagrid('getChecked');
    if(rows.length != 0){
        var selecttaxamount = Number(0);
        for(var i=0;i<rows.length;i++){
            selecttaxamount = Number(selecttaxamount) + Number(rows[i].taxamount);
        }
        $("#select-amount").val(formatterMoney(selecttaxamount));
    }
}
//生成退货通知单
function addRejectBillByRefer(){
    if(!$("#sales-form-rejectbill-order").form('validate')){
        $.messager.alert("提醒","请输入必填项!");
        return false;
    }
    var rows = $("#sales-datagrid-rejectbilldetail").datagrid('getChecked');
    if(rows.length==0){
        $.messager.alert("提醒","请选择数据");
        return false;
    }
    var queryJSON = $("#sales-form-rejectbill-order").serializeJSON();
    queryJSON["goodsjson"] = JSON.stringify(rows);
    queryJSON["addType"] = "real";
    $.messager.confirm("提醒","是否确定生成销售退货通知单？",function(r){
        if(r){
            loading("提交中..");
            $.ajax({
                url :'sales/addRejectBill.do',
                type:'post',
                dataType:'json',
                data:queryJSON,
                success:function(json){
                    loaded();
                    if(json.flag){
                        top.addOrUpdateTab('sales/rejectBill.do?type=edit&id='+ json.backid, "退货通知单查看");
                        $("#sales-panel-rejectbillDetailPage1").dialog('close');
                        $("#sales-datagrid-orderListPage").datagrid('reload');
                        $.messager.alert("提醒","生成成功");
                    }else{
                        $.messager.alert("提醒","生成失败<br/>"+json.msg);
                    }
                },
                error:function(){
                    loaded();
                    $.messager.alert("错误","生成销售退货通知单出错");
                }
            });
        }
    });
}
</script>
</body>
</html>
