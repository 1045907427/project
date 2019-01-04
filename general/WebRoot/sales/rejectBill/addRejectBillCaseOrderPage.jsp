<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>销售订单列表页面</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'center'">
        <div id="sales-queryDiv-orderListPage" style="padding:5px;height:auto">
            <form id="sales-queryForm-orderListPage">
                <input type="hidden" name="status" value="4"/>
                <table>
                    <tr>
                        <td>业务日期：</td>
                        <td><input type="text" name="businessdate" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="" /> 到 <input type="text" name="businessdate1" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
                        <td>编号：</td>
                        <td><input type="text" name="id" style="width: 150px;"/> </td>
                        <td>销售部门：</td>
                        <td><input type="text" id="sales-salesDept-orderListPage" name="salesdept"/></td>
                    </tr>
                    <tr>
                        <td>客户：</td>
                        <td><input type="text" id="sales-customer-orderListPage" style="width: 225px;" name="customerid" /></td>
                        <td colspan="2"></td>
                        <td colspan="2">
                            <a href="javaScript:void(0);" id="sales-queay-orderListPage" class="button-qr">查询</a>
                            <a href="javaScript:void(0);" id="sales-resetQueay-orderListPage" class="button-qr">重置</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <table id="sales-datagrid-orderListPage" data-options="border:false"></table>
    </div>
</div>
<input id="sales-nochecked-detail-rejectbillDetailPage" type="hidden"/>
<input id="sales-checkedbill-invoiceamount-rejectbillDetailPage" type="hidden"/>
<input id="sales-checkedbill-uncheckamount-rejectbillDetailPage" type="hidden"/>
<div id="sales-panel-rejectbillDetailPage"></div>
<div id="sales-panel-selectDetailPage"></div>
<script type="text/javascript">
    var initQueryJSON = $("#sales-queryForm-orderListPage").serializeJSON();
    $(function(){
        $("#sales-customer-orderListPage").customerWidget({ //客户参照窗口
            referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT_2',
            singleSelect:true,
            isdatasql:false,
            width:225,
            onlyLeafCheck:false
        });
        //销售部门控件
        $("#sales-salesDept-orderListPage").widget({
            referwid:'RL_T_BASE_DEPARTMENT_SELLER',
            singleSelect:true,
            width:150,
            onlyLeafCheck:false
        });

        $("#sales-queay-orderListPage").click(function(){
            var queryJSON = $("#sales-queryForm-orderListPage").serializeJSON();
            $("#sales-datagrid-orderListPage").datagrid({
                url: 'sales/getOrderList.do',
                pageNumber:1,
                queryParams:queryJSON
            }).datagrid("columnMoving");
        });
        $("#sales-resetQueay-orderListPage").click(function(){
            $("#sales-datagrid-orderListPage").datagrid('loadData',{total:0,rows:[],footer:[]});
            $("#sales-datagrid-orderListPage").datagrid('clearChecked');
            $("#sales-datagrid-orderListPage").datagrid('clearSelections');
            $("#sales-nochecked-detail-rejectbillDetailPage").val("");
            $("#sales-checkedbill-invoiceamount-rejectbillDetailPage").val("");
            $("#sales-checkedbill-uncheckamount-rejectbillDetailPage").val("");
            $("#sales-customer-orderListPage").customerWidget("clear");
            $("#sales-salesDept-orderListPage").widget('clear');
            $("#sales-queryForm-orderListPage").form("reset");
        });
        var orderListJson = $("#sales-datagrid-orderListPage").createGridColumnLoad({
            name :'t_sales_order',
            commonCol : [[
                {field:'id',title:'编号',width:110, align: 'left',sortable:true},
                {field:'businessdate',title:'业务日期',width:70,align:'left',sortable:true},
                {field:'customerid',title:'客户编码',width:60,align:'left',sortable:true},
                {field:'customername',title:'客户名称',width:180,align:'left',isShow:true},
                {field:'address',title:'客户地址',width:180,align:'left',isShow:true},
                {field:'salesdept',title:'销售部门',width:70,align:'left',sortable:true},
                {field:'salesuser',title:'客户业务员',width:70,align:'left',sortable:true},
                {field:'field01',title:'金额',width:80,align:'right',
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
                {field:'field02',title:'未税金额',width:80,align:'right',hidden:true,
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
                {field:'field03',title:'税额',width:80,align:'right',hidden:true,
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
                {field:'status',title:'状态',width:60,align:'left',sortable:true,
                    formatter:function(value,row,index){
                        return row.statusname;
                    }
                },
                {field:'addusername',title:'制单人',width:80,sortable:true},
                {field:'addtime',title:'制单时间',width:120,sortable:true},
                {field:'auditusername',title:'审核人',width:80,sortable:true,hidden:true},
                {field:'audittime',title:'审核时间',width:80,sortable:true,hidden:true},
                {field:'remark',title:'备注',width:100}
            ]]
        });
        $("#sales-datagrid-orderListPage").datagrid({
            authority:orderListJson,
            frozenColumns:orderListJson.frozen,
            columns:orderListJson.common,
            fit:true,
            title:"",
            method:'post',
            rownumbers:true,
            pagination:true,
            idField:'id',
            singleSelect:true,
            checkOnSelect:true,
            selectOnCheck:true,
            sortName:'addtime',
            sortOrder:'desc',
            pageSize:100,
            toolbar:'#sales-queryDiv-orderListPage',
            onClickRow:function(rowIndex, rowData){
                var rows = $(this).datagrid("getChecked");
                var checkFlag = true;
                for(var i=0;i<rows.length;i++){
                    var rowObject =rows[i];
                    if(rowObject.customerid!=rowData.customerid){
                        checkFlag = false;
                        break;
                    }
                }
                if(!checkFlag){
                    $(this).datagrid("uncheckRow",rowIndex);
                    return false;
                }
            },
            onCheckAll:function(rows){
                var checkFlag = true;
                var data = rows[0];
                for(var i=0;i<rows.length;i++){
                    var rowObject =rows[i];
                    if(rowObject.customerid!=data.customerid){
                        checkFlag = false;
                    }
                }
                if(!checkFlag){
                    $.messager.alert("提醒","请选择相同客户下的数据！");
                    $(this).datagrid("uncheckAll");
                    return false;
                }
                if(rows.length>=1){
                    getTotalAmount();
                }
                for(var i=0;i<rows.length;i++){
                    var rowData = rows[i];
                    if(rowData.field05==null || rowData.field05==0){
                        rowData.field05 = rowData.field04;
                    }
                    $(this).datagrid('updateRow',{index:i, row:rowData});
                }
            },
            onUncheckAll:function(rows){
                for(var i=0;i<rows.length;i++){
                    var rowData = rows[i];
                    rowData.field05 = null;
                }
                $(this).datagrid("loadData",rows);
            },
            onCheck:function(rowIndex,rowData){
                var rowArr = $(this).datagrid("getChecked");
                var checkFlag = true;
                for(var i=0;i<rowArr.length;i++){
                    var rowObject =rowArr[i];
                    if(rowObject.customerid!=rowData.customerid){
                        checkFlag = false;
                        break;
                    }
                }
                if(!checkFlag){
                    $.messager.alert("提醒","请选择相同客户下的数据！");
                    $(this).datagrid("uncheckRow",rowIndex );
                    return false;
                }else{
                    var uncheckAmountstr = $("#sales-checkedbill-uncheckamount-rejectbillDetailPage").val();
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
                    rowData.field05 = Number(rowData.field04)-Number(uncheckAmount);
                    $(this).datagrid('updateRow',{index:rowIndex, row:rowData});
                    getTotalAmount();
                }
            },
            onUncheck:function(rowIndex,rowData){
                var rowArr = $(this).datagrid("getChecked");
                var checkFlag = true;
                for(var i=0;i<rowArr.length;i++){
                    var rowObject =rowArr[i];
                    if(rowObject.customerid!=rowData.customerid){
                        checkFlag = false;
                        break;
                    }
                }
                if(checkFlag){
                    rowData.field05 = null;
                    $(this).datagrid('updateRow',{index:rowIndex, row:rowData});
                    getTotalAmount();
                }
            },
            onLoadSuccess:function(){
                getTotalAmount();
            }
        }).datagrid("columnMoving");
    });

    //显示要生成退货单据明细页面
    function showRejectBillDetail(){
        var rowArr = $("#sales-datagrid-orderListPage").datagrid("getChecked");
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
        var customername = rowArr[0].customername;
        var customerid = rowArr[0].customerid;
        var uncheckedjson = $("#sales-nochecked-detail-rejectbillDetailPage").val();
        loading("明细页面加载中..");
        $.ajax({
            url:'sales/showRejectBillDetailListPage.do',
            dataType:'html',
            type:'post',
            data:{id:ids,uncheckedjson:uncheckedjson,customerid:customerid,customername:customername},
            success:function(json){
                loaded();
                $('<div id="sales-panel-rejectbillDetailPage1"></div>').appendTo('#sales-panel-rejectbillDetailPage');
                $("#sales-panel-rejectbillDetailPage1").dialog({
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
                            text:'生成退货通知单',
                            handler:function(){
                                addRejectBillByRefer();
                            }
                        }
                    ],
                    onClose:function(){
                        $("#sales-panel-rejectbillDetailPage1").dialog('destroy');
                    }
                });
            },
            error:function(){
                loaded();
            }
        });
    }

    function showDetailListPage(id,rowIndex){
        var rowArr = $("#sales-datagrid-orderListPage").datagrid("getChecked");
        var rows = $("#sales-datagrid-orderListPage").datagrid("getRows");
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
            if(rowObject.customerid!=rowData.customerid){
                checkFlag = false;
                break;
            }
        }
        if(!checkFlag){
            $("#sales-datagrid-orderListPage").datagrid("uncheckRow",rowIndex);
            return false;
        }
        if(null!=rowData){
            var customername = rowData.customername;
            var customerid = rowData.customerid;
            $("#sales-panel-selectDetailPage").dialog({
                href:"sales/showSalesOrderDetailListPage.do?id="+rowData.id,
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
            $("#sales-datagrid-orderListPage").datagrid("checkRow",rowIndex);
        }
    }

    function getTotalAmount(){
        var rowArr = $("#sales-datagrid-orderListPage").datagrid("getChecked");
        var field01 = 0;
        var field02 = 0;
        var field03 = 0;
        var field04 = 0;
        var field05 = 0;
        for(var i=0;i<rowArr.length;i++){
            field01 = Number(field01)+Number(rowArr[i].field01);
            field02 = Number(field02)+Number(rowArr[i].field02);
            field03 = Number(field03) + Number(rowArr[i].field03);
            field04 = Number(field04) + Number(rowArr[i].field04);
            field05 = Number(field05) + Number(rowArr[i].field05);
        }
    }
</script>
</body>
</html>
