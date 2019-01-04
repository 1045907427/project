<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<head>
    <title>模拟销售修改界面</title>
</head>

<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <form action="" method="post" id="simSales-form-simSalesDataPage">
        <div data-options="region:'north',border:false" style="height: 60px;">
            <table class="querytable">
                <tr>
                    <td>业务日期:</td>
                    <td class="tdinput"><input type="text" name="businessdate" id="simSales-businessdate-simSalesDataPage"  style="width:220px;" value="${businessdate}" readonly="readonly"/> </td>
                    <td>销售对接方:</td>
                    <td><input type="text" id="simSales-outmarkid-simSalesDataEditPage" name="outmarkid" value="${outmarkid}" style="width:200px" readonly="readonly"/></td>
                    <td>销售客户:</td>
                    <td id="outcustomerid"><input type="text" id="simSales-outcustomerid-simSalesDataPage" name="outcustomerid" value="${outcustomerid}" style="width:150px" readonly="readonly"/></td>
                </tr>
                <tr>
                    <td>订单编号:</td>
                    <td><input type="text" id="simSales-billid-simSalesDataEditPage" name="billid" value="${billid}" style="width:220px" readonly="readonly"/></td>
                    <td>退货对接方:</td>
                    <td><input type="text" id="simSales-inmarkid-simSalesDataEditPage" name="inmarkid" value="${inmarkid}" style="width:200px" readonly="readonly"/></td>
                    <td>退货客户:</td>
                    <td id="incustomerid"><input type="text" id="simSales-incustomerid-simSalesDataEditPage" name="incustomerid" value="${incustomerid}" style="width:150px"  readonly="readonly"/></td>
                </tr>
            </table>
        </div>
        <div data-options="region:'center',border:false">
            <table id="simSales-datagrid-simSalesDataPage"></table>
        </div>
        <input type="hidden" id="simSales-dataDetail-simSalesDataEditPage" name="detailJson" />
        <input type="hidden" id="simSales-simSalesData-type" name="type" value="edit"/>
    </form>
</div>
<div class="easyui-menu" id="simSales-contextMenu-simSalesDataEditPage" style="display: none;">
    <div id="simSales-addRow-simSalesDataEditPage" data-options="iconCls:'button-add'">添加</div>
    <div id="simSales-editRow-simSalesDataEditPage" data-options="iconCls:'button-edit'">编辑</div>
    <div id="simSales-removeRow-simSalesDataEditPage" data-options="iconCls:'button-delete'">删除</div>
</div>
<div id="simSales-dialog-simSalesDataPage"></div>
</div>

<script type="text/javascript">
    var a='${detailList}';
    console.log(a)
    $(function(){
        $("#simSales-buttons-simSalesDataPage").buttonWidget("initButtonType", 'add');
        $("#simSales-datagrid-simSalesDataPage").datagrid({
            authority:tableColJson,
            columns: tableColJson.common,
            frozenColumns: tableColJson.frozen,
            border: true,
            rownumbers: true,
            showFooter: true,
            striped:true,
            fit:true,
            singleSelect: false,
            checkOnSelect:true,
            selectOnCheck:true,
            data:JSON.parse('${detailList}'),
            onRowContextMenu: function(e, rowIndex, rowData){
                e.preventDefault();
                $("#simSales-datagrid-simSalesDataPage").datagrid('selectRow', rowIndex);
                $("#simSales-contextMenu-simSalesDataEditPage").menu('show', {
                    left:e.pageX,
                    top:e.pageY
                });
            },
            onSortColumn:function(sort, order){
                var rows = $("#simSales-datagrid-simSalesDataPage").datagrid("getRows");
                var dataArr = [];
                for(var i=0;i<rows.length;i++){
                    if(rows[i].goodsid!=null && rows[i].goodsid!=""){
                        dataArr.push(rows[i]);
                    }
                }
                dataArr.sort(function(a,b){
                    if(order=="asc"){
                        return a[sort]>b[sort]?1:-1
                    }else{
                        return a[sort]<b[sort]?1:-1
                    }
                });
                $("#simSales-datagrid-simSalesDataPage").datagrid("loadData",dataArr);
                return false;
            },
            onLoadSuccess: function(data){
                if(data.rows.length<12){
                    var j = 12-data.rows.length;
                    for(var i=0;i<j;i++){
                        $(this).datagrid('appendRow',{});
                    }
                }else{
                    $(this).datagrid('appendRow',{});
                }
                countTotal();
            },
            onCheckAll:function(){
                countTotal();
            },
            onUncheckAll:function(){
                countTotal();
            },
            onCheck:function(){
                countTotal();
            },
            onUncheck:function(){
                countTotal();
            },
            onDblClickRow: function(rowIndex, rowData){
                $(this).datagrid('clearSelections').datagrid('selectRow',rowIndex);
                if(rowData.goodsid == undefined){
                    beginAddDetail();
                }
                else{
                    beginEditDetail();
                }
            },

        }).datagrid('columnMoving');

        $("#simSales-outcustomerid-simSalesDataEditPage").widget({
            referwid:'RL_T_BASE_SALES_CUSTOMER',
            width:150,
            singleSelect:true,
            onlyLeafCheck:true,
            required:true,
        });
        $("#simSales-incustomerid-simSalesDataEditPage").widget({
            referwid:'RL_T_BASE_SALES_CUSTOMER',
            width:150,
            singleSelect:true,
            onlyLeafCheck:true,
            required:true,
        });

        $("#simSales-outmarkid-simSalesDataEditPage").widget({
            referwid:'RL_T_BASE_DATACONFIG_INFO',
            width:200,
            singleSelect:true,
            onlyLeafCheck:true,
            required:true,
            onSelect: function(data) {
                outctypeids=data.ctypeids;
                outchangeCustomerWidget(data.customertype,"","0");
            }
        });
        $("#simSales-inmarkid-simSalesDataEditPage").widget({
            referwid:'RL_T_BASE_DATACONFIG_INFO',
            width:200,
            singleSelect:true,
            onlyLeafCheck:true,
            required:true,
            onSelect: function(data) {
                inctypeids=data.ctypeids;
                inchangeCustomerWidget(data.customertype,"","0");
            }
        });
        //明细添加
        $("#simSales-addRow-simSalesDataEditPage").click(function(){
            var flag = $("#simSales-addRow-simSalesDataEditPage").menu('getItem','#simSales-addRow-simSalesDataEditPage').disabled;
            if(flag){
                return false;
            }
            beginAddDetail();
        });
        //明细修改
        $("#simSales-editRow-simSalesDataEditPage").click(function(){
            var flag = $("#simSales-contextMenu-simSalesDataEditPage").menu('getItem','#simSales-editRow-simSalesDataEditPage').disabled;
            if(flag){
                return false;
            }
            beginEditDetail();
        });
        //明细删除
        $("#simSales-removeRow-simSalesDataEditPage").click(function(){
            var flag = $("#simSales-contextMenu-simSalesDataEditPage").menu('getItem','#simSales-removeRow-simSalesDataEditPage').disabled;
            if(flag){
                return false;
            }
            removeDetail();
        });
        //订单提交
        $("#simSales-form-simSalesDataPage").form({
            onSubmit: function(){
                var json = $("#simSales-datagrid-simSalesDataPage").datagrid('getRows');

                $("#simSales-dataDetail-simSalesDataEditPage").val(JSON.stringify(json));
                var flag = $(this).form('validate');
                if(flag==false){
                    return false;
                }

                loading("提交中..");
            },
            success:function(data){

                //表单提交完成后 隐藏提交等待页面
                loaded();
                var json = $.parseJSON(data);
                if(json.flag){
                    $.messager.alert("提醒","保存成功");
                    top.closeNowTab();
                }else{
                    $.messager.alert("提醒","保存失败.");
                }
            }
        });
    })

</script>
</body>
</html>