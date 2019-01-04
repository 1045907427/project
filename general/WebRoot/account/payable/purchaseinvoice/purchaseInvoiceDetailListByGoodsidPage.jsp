<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>显示采购发票明细列表根据商品编码</title>
</head>
<body>
<table id="account-datagrid-purchaseInvoiceAddPage"></table>
<%--<div class="easyui-menu" id="account-contextMenu-purchaseInvoiceDetailListByGoodsidPage">--%>
    <%--<div id="account-removeRow-purchaseInvoiceDetailListByGoodsidPage" data-options="iconCls:'button-delete'">删除</div>--%>
<%--</div>--%>
<script type="text/javascript">
    $(function(){
        //根据初始的列与用户保存的列生成以及字段权限生成新的列
        var detailListByGoodsidColJson = $("#account-datagrid-purchaseInvoiceAddPage").createGridColumnLoad({
            name :'t_account_purchase_invoice_detail',
            frozenCol : [[]],
            commonCol : [[
                {field:'sourceid',title:'来源单据编号',width:130},
                {field:'goodsid',title:'商品编码',width:80},
                {field:'goodsname', title:'商品名称', width:220,aliascol:'goodsid'},
                {field:'barcode', title:'条形码',width:85,aliascol:'goodsid'},
                {field:'brandname', title:'商品品牌',width:80,aliascol:'goodsid',hidden:true},
                {field:'model', title:'规格型号',width:80,aliascol:'goodsid',hidden:true},
                {field:'unitname', title:'单位',width:50},
                {field:'taxprice', title:'单价',width:60,align:'right',
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
                {field:'unitnum', title:'数量',width:60,align:'right',
                    formatter:function(value,row,index){
                        return formatterBigNumNoLen(value);
                    }
                },
                {field:'taxamount', title:'金额',width:60,align:'right',
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
                {field:'auxnumdetail', title:'辅数量',width:60,align:'right'},
                {field:'notaxprice', title:'未税单价',width:60,align:'right',
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
                {field:'notaxamount', title:'未税金额',width:60,align:'right',
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
                {field:'taxtypename', title:'税种',width:60,aliascol:'taxtype',align:'right'},
                {field:'tax', title:'税额',width:60,align:'right',
                    formatter:function(value,row,index){
                        return formatterMoney(value);
                    }
                },
                {field:'receivedate', title:'应收日期',width:80},
                {field:'remark', title:'备注',width:100}
            ]]
        });

        $("#account-datagrid-purchaseInvoiceAddPage").datagrid({ //采购进货单明细信息编辑
            authority:detailListByGoodsidColJson,
            columns: detailListByGoodsidColJson.common,
            frozenColumns: detailListByGoodsidColJson.frozen,
            border: true,
            rownumbers: true,
            showFooter: true,
            striped:true,
            fit:true,
            idField:"id",
            singleSelect: true,
            data:JSON.parse('${detailListByGoodsid}'),
            rowStyler: function(index,row){
                if (row.isedit == "1"){
                    return 'background-color:#FFF7CE;'; // return inline style
                }
            },
            onRowContextMenu: function(e, rowIndex, rowData){
                e.preventDefault();
                $(this).datagrid('selectRow', rowIndex);
                $("#account-contextMenu-purchaseInvoiceDetailListByGoodsidPage").menu('show', {
                    left:e.pageX,
                    top:e.pageY
                });
                //冲差单明细启用删除按钮 这里删除的功能代码已注释
                if(rowData.sourcetype != "") {
                    $("#account-contextMenu-purchaseInvoiceDetailListByGoodsidPage").menu('disableItem',
                            $("#account-removeRow-purchaseInvoiceDetailListByGoodsidPage"));
                }else{
                    $("#account-contextMenu-purchaseInvoiceDetailListByGoodsidPage").menu('enableItem',
                            $("#account-removeRow-purchaseInvoiceDetailListByGoodsidPage"));
                }
            },
            onDblClickRow:function(rowIndex, rowData){
                if(rowData.sourceid == undefined || rowData.auxnumdetail == ""){
                }
                else{
                    beginEditDetail();
                }
            },
            onLoadSuccess:function(data){
                if(data.rows.length<10){
                    var j = 10-data.rows.length;
                    for(var i=0;i<j;i++){
                        $(this).datagrid('appendRow',{});
                    }
                }else{
                    $(this).datagrid('appendRow',{});
                }
                countTotal("account-datagrid-purchaseInvoiceAddPage");
            },
            onBeforeLoad:function(param){
                var updateRows = getTotalUpdateDetailRows();
                if(null != updateRows && updateRows.length != 0){
                    for(var i=0;i<updateRows.length;i++){
                        var index = $(this).datagrid("getRowIndex",updateRows[i].id);
                        if(index != -1){
                            $(this).datagrid("updateRow",{index:index, row:updateRows[i]});
                        }
                    }
                }
            }
        });

    });

//    //冲差单明细删除按钮
//    $("#account-removeRow-purchaseInvoiceDetailListByGoodsidPage").click(function(){
//        var row = $("#account-datagrid-purchaseInvoiceAddPage").datagrid('getSelected');
//        var index = $("#account-datagrid-purchaseInvoiceAddPage").datagrid('getRowIndex', row);
//        if(row.auxnumdetail == "") {
//            $("#account-datagrid-purchaseInvoiceAddPage").datagrid('deleteRow', index);
//            var id = $("#account-purchaseInvoice-id").val();
//            $.ajax({
//                url:'account/payable/deletePurchaseInvoicePush.do?id='+id,
//                type:"post",
//                dataType:"json",
//                success:function(json){
//                    countTotal("account-datagrid-purchaseInvoiceAddPage");
//
//                    //发票商品分组合计
//                    var footrows = $("#account-datagrid-purchaseInvoiceAddPage").datagrid("getFooterRows");
//                    var grouprow = $("#account-datagrid-group-purchaseInvoiceAddPage").datagrid("getSelected");
//                    if(grouprow.goodsid == row.goodsid){
//                        grouprow.unitnum = footrows[0].unitnum;
//                        grouprow.taxamount = footrows[0].taxamount;
//                        grouprow.notaxamount = footrows[0].notaxamount;
//                        grouprow.tax = footrows[0].tax;
//                        var groupindex = $("#account-datagrid-group-purchaseInvoiceAddPage").datagrid('getRowIndex', grouprow);
//                        $("#account-datagrid-group-purchaseInvoiceAddPage").datagrid('updateRow',{index:groupindex, row:grouprow});
//
//                        countTotal("account-datagrid-group-purchaseInvoiceAddPage");
//                    }
//                }
//            })
//        }
//
//    });


</script>
</body>
</html>
