<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<html>
<head>
    <title>模拟销售新增界面</title>
</head>

<body>
    <div class="easyui-layout" data-options="fit:true,border:false">
        <form action="" method="post" id="simSales-form-simSalesDataPage">
            <div data-options="region:'north',border:false" style="height: 60px;">
                <table class="querytable">
                    <tr>
                        <td>业务日期:</td>
                        <td class="tdinput"><input type="text" name="businessdate" id="simSales-businessdate-simSalesDataPage" style="width:220px;" class="Wdate easyui-validatebox"  required="required" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" value="${today }" /> </td>
                        <td>销售对接方:</td>
                        <td><input type="text" id="simSales-outmarkid-simSalesDataAddPage" name="outmarkid" style="width:200px"/></td>
                        <td>销售客户:</td>
                        <td id="outcustomerid"><input type="text" id="simSales-outcustomerid-simSalesDataAddPage" name="outcustomerid" style="width:150px"  disabled="disabled"/></td>
                    </tr>
                    <tr>
                        <td>单据编号:</td>
                        <td><input type="text" id="simSales-billid-simSalesDataAddPage" name="billid" style="width:220px"/></td>
                        <td>退货对接方:</td>
                        <td><input type="text" id="simSales-inmarkid-simSalesDataAddPage" name="inmarkid" style="width:200px"/></td>
                        <td>退货客户:</td>
                        <td id="incustomerid"><input type="text" id="simSales-incustomerid-simSalesDataAddPage" name="incustomerid" style="width:150px"  disabled="disabled"/></td>
                    </tr>
                </table>
            </div>
            <div data-options="region:'center',border:false">
                <table id="simSales-datagrid-simSalesDataPage"></table>
            </div>
            <input type="hidden" id="simSales-dataDetail-simSalesDataAddPage" name="detailJson" />
            <input type="hidden" id="simSales-simSalesData-type" name="type" value="add"/>
        </form>
    </div>
    <div class="easyui-menu" id="simSales-contextMenu-simSalesDataAddPage" style="display: none;">
        <div id="simSales-addRow-simSalesDataAddPage" data-options="iconCls:'button-add'">添加</div>
        <div id="simSales-editRow-simSalesDataAddPage" data-options="iconCls:'button-edit'">编辑</div>
        <div id="simSales-removeRow-simSalesDataAddPage" data-options="iconCls:'button-delete'">删除</div>
    </div>
    <div id="simSales-dialog-simSalesDataPage"></div>
</div>

<script type="text/javascript">
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
            onRowContextMenu: function(e, rowIndex, rowData){
                e.preventDefault();
                $("#simSales-datagrid-simSalesDataPage").datagrid('selectRow', rowIndex);
                $("#simSales-contextMenu-simSalesDataAddPage").menu('show', {
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

        }).datagrid("loadData", {'total':12,'rows':[{},{},{},{},{},{},{},{},{},{},{},{} ],'footer':[{goodsid:'合计'}]}).datagrid('columnMoving');

        var inctypeids="";
        var outctypeids="";

        $("#simSales-outmarkid-simSalesDataAddPage").widget({
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
        $("#simSales-inmarkid-simSalesDataAddPage").widget({
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


        function outchangeCustomerWidget(sourcetype,sourceid,disabled){
            $("#outcustomerid").empty();
            var tdstr = "",disabledstr="";
            if(disabled == "1"){
                disabledstr = 'disabled="disabled"';
            }
            tdstr='<input type="text" id="simSales-outcustomerid-simSalesDataAddPage" name="outcustomerid" style="width: 150px" value="'+sourceid+'"  '+disabledstr+'/>';
            $(tdstr).appendTo("#outcustomerid");
            if("1" == sourcetype){
                $("#simSales-outcustomerid-simSalesDataAddPage").customerWidget({
                    width:150,
                    singleSelect:true,
                    onlyLeafCheck:true,
                    required:true,
                });
            }else if("2" == sourcetype){
                $("#simSales-outcustomerid-simSalesDataAddPage").customerWidget({
                    width:150,
                    singleSelect:true,
                    onlyLeafCheck:true,
                    required:true,
                    param:[	 {field:'salesdeptid',op:'in',value:outctypeids}],
                });
            }else if("3" == sourcetype){
                $("#simSales-outcustomerid-simSalesDataAddPage").customerWidget({
                    width:150,
                    singleSelect:true,
                    onlyLeafCheck:true,
                    required:true,
                    param:[	 {field:'salesarea',op:'in',value:outctypeids}],
                });
            }else if("4" == sourcetype){
                $("#simSales-outcustomerid-simSalesDataAddPage").customerWidget({
                    width:150,
                    singleSelect:true,
                    onlyLeafCheck:true,
                    required:true,
                    param:[	 {field:'customersort',op:'in',value:outctypeids}],
                });
            }
        }

        function inchangeCustomerWidget(sourcetype,sourceid,disabled){
            $("#incustomerid").empty();
            var tdstr = "",disabledstr="";
            if(disabled == "1"){
                disabledstr = 'disabled="disabled"';
            }
            tdstr='<input type="text" id="simSales-incustomerid-simSalesDataAddPage" name="incustomerid" style="width: 150px" value="'+sourceid+'"  '+disabledstr+'/>';
            $(tdstr).appendTo("#incustomerid");
            if("1" == sourcetype){
                $("#simSales-incustomerid-simSalesDataAddPage").customerWidget({
                    width:150,
                    singleSelect:true,
                    onlyLeafCheck:true,
                    required:true,
                });
            }else if("2" == sourcetype){
                $("#simSales-incustomerid-simSalesDataAddPage").customerWidget({
                    width:150,
                    singleSelect:true,
                    onlyLeafCheck:true,
                    required:true,
                    param:[	 {field:'salesdeptid',op:'in',value:inctypeids}],
                });
            }else if("3" == sourcetype){
                $("#simSales-incustomerid-simSalesDataAddPage").customerWidget({
                    width:150,
                    singleSelect:true,
                    onlyLeafCheck:true,
                    required:true,
                    param:[	 {field:'salesarea',op:'in',value:inctypeids}],
                });
            }else if("4" == sourcetype){
                $("#simSales-incustomerid-simSalesDataAddPage").customerWidget({
                    width:150,
                    singleSelect:true,
                    onlyLeafCheck:true,
                    required:true,
                    param:[	 {field:'customersort',op:'in',value:inctypeids}],
                });
            }
        }
        //明细添加
        $("#simSales-addRow-simSalesDataAddPage").click(function(){
            var flag = $("#simSales-addRow-simSalesDataAddPage").menu('getItem','#simSales-addRow-simSalesDataAddPage').disabled;
            if(flag){
                return false;
            }
            beginAddDetail();
        });
        //明细修改
        $("#simSales-editRow-simSalesDataAddPage").click(function(){
            var flag = $("#simSales-contextMenu-simSalesDataAddPage").menu('getItem','#simSales-editRow-simSalesDataAddPage').disabled;
            if(flag){
                return false;
            }
            beginEditDetail();
        });
        //明细删除
        $("#simSales-removeRow-simSalesDataAddPage").click(function(){
            var flag = $("#simSales-contextMenu-simSalesDataAddPage").menu('getItem','#simSales-removeRow-simSalesDataAddPage').disabled;
            if(flag){
                return false;
            }
            removeDetail();
        });
        //订单提交
        $("#simSales-form-simSalesDataPage").form({
            onSubmit: function(){
                var json = $("#simSales-datagrid-simSalesDataPage").datagrid('getRows');

                $("#simSales-dataDetail-simSalesDataAddPage").val(JSON.stringify(json));
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