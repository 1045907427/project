<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>预开票新增</title>
    <%@include file="/include.jsp" %>
</head>

<body>
<table id="account-billDatagrid-saleoutSourcePage"></table>
<div id="account-toolbar-query-salesAdvanceBillSouceBill" style="padding: 0px">
    <div class="buttonBG">
        <a href="javaScript:void(0);" id="account-export-salesAdvanceBillAdd" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
        <a href="javaScript:void(0);" id="account-add-salesAdvanceBillAdd" class="easyui-linkbutton" iconCls="button-add" plain="true" title="申请预开票">申请预开票</a>
    </div>
    <form id="account-form-query-salesAdvanceBillSouceBill">
        <table class="querytable">
            <tr>
                <td>业务日期：</td>
                <td>
                    <input type="text" name="businessdate1" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /> 到 <input type="text" name="businessdate2" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" />
                </td>
                <td>客户名称：</td>
                <td>
                    <input type="text" id="account-customerid-salesAdvanceBillSourceQueryPage" style="width: 150px" name="customerid" />
                </td>
                <td>总店名称：</td>
                <td>
                    <input type="text" id="account-pcustomerid-salesAdvanceBillSourceQueryPage" name="pcustomerid" />
                </td>
            </tr>
            <tr>
                <td>订单日期：</td>
                <td>
                    <input type="text" name="orderdate1" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /> 到 <input type="text" name="orderdate2" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" />
                </td>
                <td>单据编号：</td>
                <td><input type="text" style="width: 150px;" name="id"/></td>
                <td>品牌名称：</td>
                <td><input type="text" id="account-brand-salesAdvanceBillSourceQueryPage" name="brandid"/></td>
            </tr>
            <tr>
                <td>税种：</td>
                <td><input type="text" style="width: 123px;" name="taxtype" id="account-taxtype-salesInvoiceBillSourceQueryPage"/></td>
                <td colspan="2"></td>
                <td colspan="2">
                    <a href="javaScript:void(0);" id="account-query-salesAdvanceBillAdd" class="button-qr" plain="true" title="查询">查询</a>
                    <a href="javaScript:void(0);" id="account-reload-salesAdvanceBillAdd" class="button-qr" plain="true" title="重置">重置</a>

                </td>
            </tr>
        </table>
    </form>
</div>
<input id="account-nochecked-detail-salesAdvanceBillDetailPage" type="hidden"/>
<input id="account-checkedbill-invoiceamount-salesAdvanceBillDetailPage" type="hidden"/>
<input id="account-checkedbill-uncheckamount-salesAdvanceBillDetailPage" type="hidden"/>
<div id="account-panel-salesAdvanceBillDetailPage"></div>
<div id="account-panel-selectDetailPage"></div>
<script type="text/javascript">
var totalFooter = {};
$(function(){
    var salesAdvancebillJson = $("#account-billDatagrid-saleoutSourcePage").createGridColumnLoad({
        frozenCol : [[]],
        commonCol : [[
            {field:'ck', checkbox:true},
            {field:'id', title:'编号', width:130,sortable:true,
                formatter:function(value,row,index){
                    if(null!=value){
                        if(value!="选中合计" && value!="合计"){
                            return '<a href="javascript:showDetailListPage(\''+value+'\','+index+')">'+value+'</a>';
                        }else{
                            return value;
                        }
                    }
                }
            },
            {field:'businessdate', title:'业务日期', width:70,sortable:true},
            {field:'orderid', title:'订单编号', width:130,sortable:true},
            {field:'orderdate', title:'订单日期', width:70,sortable:true},
            {field:'customerid',title:'客户编码',width:60,align:'left',sortable:true},
            {field:'customername',title:'客户名称',width:100,align:'left'},
            {field:'headcustomername',title:'总店名称',width:100,align:'left'},
            {field:'totaltaxamount',title:'总金额',width:60,align:'right',sortable:true,
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'uninvoiceamount',title:'未开票金额',width:80,align:'right',sortable:true,
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'invoiceamount',title:'选中开票金额',width:80,align:'right',
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'duefromdate',title:'应收日期',width:70,align:'left',sortable:true},
            {field:'salesdept',title:'销售部门',width:80,align:'left'},
            {field:'salesuser',title:'客户业务员',width:70,align:'left'},
            {field:'addusername',title:'制单人',width:60,align:'left'},
            {field:'remark',title:'备注',width:80,align:'left'}
        ]]
    });
    $("#account-billDatagrid-saleoutSourcePage").datagrid({
        authority:salesAdvancebillJson,
        frozenColumns: salesAdvancebillJson.frozen,
        columns:salesAdvancebillJson.common,
        fit:true,
        method:'post',
        rownumbers:true,
        pagination:true,
        idField:'id',
        singleSelect:false,
        selectOnCheck:true,
        checkOnSelect:true,
        sortName:'customerid,orderdate',
        sortOrder:'asc',
        pageSize:1000,
        pageList:[20,100,200,500,1000],
        showFooter:true,
        toolbar:'#account-toolbar-query-salesAdvanceBillSouceBill',
        onBeforeLoad:function(){
        },
        onClickRow:function(rowIndex, rowData){
            var rowArr = $("#account-billDatagrid-saleoutSourcePage").datagrid("getChecked");
            var checkFlag = true;
            for(var i=0;i<rowArr.length;i++){
                var rowObject =rowArr[i];
                if(rowObject.headcustomerid!=rowData.headcustomerid){
                    checkFlag = false;
                    break;
                }
            }
            if(!checkFlag){
                $("#account-billDatagrid-saleoutSourcePage").datagrid("uncheckRow",rowIndex);
                return false;
            }
        },
        onCheckAll:function(rows){
            var checkFlag = true;
            var data = rows[0];
            for(var i=0;i<rows.length;i++){
                var rowObject =rows[i];
                if(rowObject.headcustomerid!=data.headcustomerid){
                    checkFlag = false;
                }
            }
            if(!checkFlag){
                $.messager.alert("提醒","请选择相同总店下的数据！");
                $("#account-billDatagrid-saleoutSourcePage").datagrid("uncheckAll");
                return false;
            }
            if(rows.length>=1){
                getCustomerCapital(rows[0].customerid,rows[0].headcustomerid);
            }
            for(var i=0;i<rows.length;i++){
                var rowData = rows[i];
                if(rowData.invoiceamount==null || rowData.invoiceamount==0){
                    rowData.invoiceamount = rowData.uninvoiceamount;
                }
                $("#account-billDatagrid-saleoutSourcePage").datagrid('updateRow',{index:i, row:rowData});
            }
            //$("#account-billDatagrid-saleoutSourcePage").datagrid("clearChecked");
        },
        onUncheckAll:function(rows){
            for(var i=0;i<rows.length;i++){
                var rowData = rows[i];
                rowData.invoiceamount = null;
            }
            $("#account-billDatagrid-saleoutSourcePage").datagrid("loadData",rows);
            $("#account-billDatagrid-saleoutSourcePage").datagrid("reloadFooter",[{id:'选中合计',customername:'',headcustomername:'',totaltaxamount:0,uninvoiceamount:0},totalFooter] );
        },
        onCheck:function(rowIndex,rowData){
            var rowArr = $("#account-billDatagrid-saleoutSourcePage").datagrid("getChecked");
            var checkFlag = true;
            for(var i=0;i<rowArr.length;i++){
                var rowObject =rowArr[i];
                if(rowObject.headcustomerid!=rowData.headcustomerid){
                    checkFlag = false;
                    break;
                }
            }
            if(!checkFlag){
                $.messager.alert("提醒","请选择相同客户（总店）下的数据！");
                $("#account-billDatagrid-saleoutSourcePage").datagrid("uncheckRow",rowIndex );
                return false;
            }else{
                var uncheckAmountstr = $("#account-checkedbill-uncheckamount-salesAdvanceBillDetailPage").val();
                var uncheckAmountArr = null;
                var uncheckAmount = 0;
                if(null!=uncheckAmountstr && uncheckAmountstr!=""){
                    uncheckAmountArr = $.parseJSON(uncheckAmountstr);
                }else{
                    uncheckAmountArr = [];
                }
                for(var i=0;i<uncheckAmountArr.length;i++){
                    if(uncheckAmountArr[i].id==rowData.id){
                        uncheckAmount = uncheckAmountArr[i].uncheckamount;
                        break;
                    }
                }
                rowData.invoiceamount = Number(rowData.uninvoiceamount)-Number(uncheckAmount);
                $("#account-billDatagrid-saleoutSourcePage").datagrid('updateRow',{index:rowIndex, row:rowData});
                getCustomerCapital(rowData.customerid,rowData.headcustomerid);
            }
        },
        onUncheck:function(rowIndex,rowData){
            var rowArr = $("#account-billDatagrid-saleoutSourcePage").datagrid("getChecked");
            var checkFlag = true;
            for(var i=0;i<rowArr.length;i++){
                var rowObject =rowArr[i];
                if(rowObject.headcustomerid!=rowData.headcustomerid){
                    checkFlag = false;
                    break;
                }
            }
            if(checkFlag){
                rowData.invoiceamount = null;
                $("#account-billDatagrid-saleoutSourcePage").datagrid('updateRow',{index:rowIndex, row:rowData});
                getCustomerCapital(rowData.customerid,rowData.headcustomerid);
            }
        },
        onLoadSuccess:function(){
            var footerrows = $(this).datagrid('getFooterRows');
            if(null!=footerrows && footerrows.length>0){
                if(footerrows[0]!=null &&footerrows[0].id=="合计"){
                    totalFooter = footerrows[0];
                }
            }
        }
    }).datagrid("columnMoving");
    $("#account-customerid-salesAdvanceBillSourceQueryPage").customerWidget({
        singleSelect:true,
        isdatasql:false,
        width:150,
        onlyLeafCheck:true
    });
    $("#account-pcustomerid-salesAdvanceBillSourceQueryPage").widget({
        referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
        singleSelect:true,
        width:120,
        onlyLeafCheck:true
    });
    $("#account-brand-salesAdvanceBillSourceQueryPage").widget({
        referwid:'RL_T_BASE_GOODS_BRAND',
        singleSelect:true,
        width:120,
        onlyLeafCheck:true
    });
    $("#account-query-salesAdvanceBillAdd").click(function(){
        $("#account-billDatagrid-saleoutSourcePage").datagrid('loadData',{total:0,rows:[],footer:[totalFooter]});
        $("#account-billDatagrid-saleoutSourcePage").datagrid('clearChecked');
        $("#account-billDatagrid-saleoutSourcePage").datagrid('clearSelections');
        var queryJSON = $("#account-form-query-salesAdvanceBillSouceBill").serializeJSON();
        $("#account-billDatagrid-saleoutSourcePage").datagrid({
            url: 'storage/getSaleoutListForAdvanceBill.do',
            pageNumber:1,
            queryParams:queryJSON
        }).datagrid("columnMoving");
    });
    //导出
    $("#account-export-salesAdvanceBillAdd").Excel('export',{
        queryForm: "#account-form-query-salesAdvanceBillSouceBill",
        type:'exportUserdefined',
        name:'预开票列表',
        url:'sales/exportSaleoutBillList.do'
    });
    $("#account-customerid-salesAdvanceDetailSourceQueryPage").widget({
        referwid:'RL_T_BASE_SALES_CUSTOMER',
        singleSelect:true,
        width:150,
        onlyLeafCheck:true
    });
    $("#account-pcustomerid-salesAdvanceDetailSourceQueryPage").widget({
        referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
        singleSelect:true,
        width:150,
        onlyLeafCheck:true
    });

    //默认税种
    $("#account-taxtype-salesInvoiceBillSourceQueryPage").widget({
        width: 220,
        referwid:'RL_T_BASE_FINANCE_TAXTYPE',
        singleSelect: true,
        required: true,
        onlyLeafCheck: false
    });
    $("#account-add-salesAdvanceBillAdd").click(function(){
        showSalesAdvanceBillDetail();
    });
    $("#account-reload-salesAdvanceBillAdd").click(function(){
        $("#account-billDatagrid-saleoutSourcePage").datagrid('loadData',{total:0,rows:[],footer:[]});
        $("#account-billDatagrid-saleoutSourcePage").datagrid('clearChecked');
        $("#account-billDatagrid-saleoutSourcePage").datagrid('clearSelections');
        $("#account-nochecked-detail-salesAdvanceBillDetailPage").val("");
        $("#account-checkedbill-invoiceamount-salesAdvanceBillDetailPage").val("");
        $("#account-checkedbill-uncheckamount-salesAdvanceBillDetailPage").val("");
        $("#account-customerid-salesAdvanceBillSourceQueryPage").customerWidget("clear");
        $("#account-pcustomerid-salesAdvanceBillSourceQueryPage").widget("clear");
        $("#account-form-query-salesAdvanceBillSouceBill")[0].reset();
    });
});
//获取客户资金情况
function getCustomerCapital(customerid,pcustomerid){
    if(customerid!=pcustomerid){
        $.ajax({
            url :'account/receivable/getCustomerCapital.do?id='+customerid+"&pcustomerid="+pcustomerid,
            type:'post',
            dataType:'json',
            success:function(json){
                var rowArr = $("#account-billDatagrid-saleoutSourcePage").datagrid("getChecked");
                var uninvoiceamount = 0;
                var invoiceamount = 0;
                var totaltaxamount = 0;
                for(var i=0;i<rowArr.length;i++){
                    uninvoiceamount = Number(uninvoiceamount)+Number(rowArr[i].uninvoiceamount);
                    totaltaxamount = Number(totaltaxamount)+Number(rowArr[i].totaltaxamount);
                    if(null!=invoiceamount){
                        invoiceamount = Number(invoiceamount) + Number(rowArr[i].invoiceamount);
                    }
                }
                if(rowArr.length==0){
                    $("#account-billDatagrid-saleoutSourcePage").datagrid("reloadFooter",[totalFooter] );
                }else{
                    $("#account-billDatagrid-saleoutSourcePage").datagrid("reloadFooter",[{id:'选中合计',businessdate:customerid+'余额',customerid:formatterMoney(json.camount),customername:'总店余额',headcustomername:formatterMoney(json.amount),totaltaxamount:totaltaxamount,uninvoiceamount:uninvoiceamount.toFixed(2),invoiceamount:invoiceamount.toFixed(2)},totalFooter] );
                }
            }
        });
    }else{
        $.ajax({
            url :'account/receivable/getCustomerCapital.do?id='+customerid,
            type:'post',
            dataType:'json',
            success:function(json){
                var rowArr = $("#account-billDatagrid-saleoutSourcePage").datagrid("getChecked");
                var uninvoiceamount = 0;
                var totaltaxamount = 0;
                var invoiceamount = 0;
                for(var i=0;i<rowArr.length;i++){
                    uninvoiceamount = Number(uninvoiceamount)+Number(rowArr[i].uninvoiceamount);
                    totaltaxamount = Number(totaltaxamount)+Number(rowArr[i].totaltaxamount);
                    if(null!=invoiceamount){
                        invoiceamount = Number(invoiceamount) + Number(rowArr[i].invoiceamount);
                    }
                }
                if(rowArr.length==0){
                    $("#account-billDatagrid-saleoutSourcePage").datagrid("reloadFooter",[totalFooter] );
                }else{
                    $("#account-billDatagrid-saleoutSourcePage").datagrid("reloadFooter",[{id:'选中合计',customername:'账户余额',headcustomername:formatterMoney(json.camount),totaltaxamount:totaltaxamount,uninvoiceamount:uninvoiceamount.toFixed(2),invoiceamount:invoiceamount.toFixed(2)},totalFooter] );
                }
            }
        });
    }

}
function showSalesAdvanceBillDetail(){
    var rowArr = $("#account-billDatagrid-saleoutSourcePage").datagrid("getChecked");
    var ids = null;
    if(rowArr.length==0){
        $.messager.alert("提醒","请选择数据");
        return false;
    }
    for(var i=0;i<rowArr.length;i++){
        if(ids==null){
            ids = rowArr[i].id;
        }else{
            ids +=","+ rowArr[i].id;
        }
    }
    var customername = rowArr[0].headcustomername;
    if(null==customername){
        customername = rowArr[0].customername;
    }
    var customerid = rowArr[0].headcustomerid;
    if(null==customerid){
        customerid = rowArr[0].customerid;
    }
    var taxtype=$("#account-taxtype-salesInvoiceBillSourceQueryPage").widget("getValue") || "";
    var brandid = $("#account-brand-salesAdvanceBillSourceQueryPage").widget("getValue");
    var uncheckedjson = $("#account-nochecked-detail-salesAdvanceBillDetailPage").val();
    loading("明细页面加载中..");
    $.ajax({
        url:'storage/showAdvanceBillDetailBySaleout.do',
        dataType:'html',
        type:'post',
        data:{
            id:ids,
            uncheckedjson:uncheckedjson,
            customerid:customerid,
            customername:customername,
            brandid:brandid,
            taxtype:taxtype
        },
        success:function(json){
            loaded();
            $("#account-panel-salesAdvanceBillDetailPage").dialog({
                title:"客户:"+customerid+customername+"，商品明细列表",
                fit:true,
                closed:false,
                modal:true,
                cache:false,
                maximizable:true,
                resizable:true,
                content:json,
                buttons:[
                    {
                        text:'追加开票',
                        handler:function(){
                            var customerid = $("#select-customerid-saleout").val();
                            showCustomerSalesAdvanceBillList(customerid);
                        }
                    },
                    {
                        text:'开票',
                        handler:function(){
                            addSalesAdvanceBillByRefer();
                        }
                    }
                ]
            });
        },
        error:function(){
            loaded();
        }
    });

}
function getBillDetailAmount(){
    var rows = $("#account-orderDatagrid-detailSourcePage").datagrid("getChecked");
    var totalamount = 0;
    for(var i=0;i<rows.length;i++){
        totalamount = Number(totalamount) + Number(rows[i].taxamount);
    }
    if(rows.length>0){
        $("#account-orderDatagrid-detailSourcePage").datagrid("reloadFooter",[{billid:'选中合计',taxamount:totalamount},totalFooter] );
    }else{
        $("#account-orderDatagrid-detailSourcePage").datagrid("reloadFooter",[{billid:'选中合计',taxamount:0},totalFooter] );
    }
}
function showDetailListPage(id,rowIndex){
    var rowArr = $("#account-billDatagrid-saleoutSourcePage").datagrid("getChecked");
    var rows = $("#account-billDatagrid-saleoutSourcePage").datagrid("getRows");
    var rowData = null;
    for(var i=0;i<rows.length;i++){
        if(rows[i].id==id){
            rowData = rows[i];
            break;
        }
    }
    var checkFlag = true;
    for(var i=0;i<rowArr.length;i++){
        var rowObject =rowArr[i];
        if(rowObject.headcustomerid!=rowData.headcustomerid){
            checkFlag = false;
            break;
        }
    }
    if(!checkFlag){
        $("#account-billDatagrid-saleoutSourcePage").datagrid("uncheckRow",rowIndex);
        return false;
    }
    if(null!=rowData){
        var customername = rowData.headcustomername;
        if(customername==null){
            customername = rowData.customername;
        }
        var customerid = rowData.headcustomerid;
        if(customerid==null){
            customerid = rowData.customerid;
        }
        var brandid = $("#account-brand-salesAdvanceBillSourceQueryPage").widget("getValue");
        $("#account-panel-selectDetailPage").dialog({
            href:"storage/showSaleoutDetailListForAdvanceBillPage.do?id="+rowData.id+"&brandid="+brandid,
            title:"客户:"+customerid+customername+",单据明细列表",
            fit:true,
            closed:false,
            modal:true,
            cache:false,
            maximizable:true,
            resizable:true,
            buttons:[{
                text:'确认',
                handler:function(){
                    addNocheckedDetail();
                }
            }]
        });
        $("#account-billDatagrid-saleoutSourcePage").datagrid("checkRow",rowIndex);
    }
}
</script>
</body>
</html>
