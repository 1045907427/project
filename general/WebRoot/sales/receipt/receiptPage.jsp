<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>销售发货回单页面</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<input type="hidden" id="sales-backid-receiptPage" value="${id }" />
<input type="hidden" id="sales-parentid-receiptPage" /><!-- 参照上游单据的编码 -->
<div id="sales-layout-receiptPage" class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="sales-buttons-receiptPage" style="height:26px;"></div>
    </div>
    <div data-options="region:'center',border:false">
        <div id="sales-panel-receiptPage">
        </div>
    </div>
</div>
<div id="sales-sourceQueryDialog-receiptPage" ></div>
<div id="sales-sourceDialog-receiptPage" ></div>
<div id="sales-dialog-receiptPage"></div>
<div id="sales-dialog-reletionRejectPage"></div>
<div id="sales-dialog-reletionRejectPage-upp"></div>
<div id="sales-dialog-receiptRejectBillIdPage"></div>
<div id="sales-dialog-receiptWriteoff"></div>
<div id="account-panel-collectionOrder-addauditpage"></div>
<script type="text/javascript">
    var receipt_url = "sales/receiptAddPage.do";
    var receipt_type = '${type}';
    if(receipt_type == "view" || receipt_type == "show" || receipt_type == "handle"){
        receipt_url = "sales/receiptViewPage.do?id=${id}";
    }
    if(receipt_type == "edit"){
        receipt_url = "sales/receiptEditPage.do?id=${id}";
    }
    var wareListJson = $("#sales-datagrid-receiptAddPage").createGridColumnLoad({ //以下为商品明细datagrid字段
        //name:'t_sales_receipt_detail',
        frozenCol : [[
            {field:'ck',checkbox:true},
        ]],
        commonCol: [[
            {field:'goodsid',title:'商品编码',width:80,align:' left',sortable: true},
            {field:'goodsname', title:'商品名称', width:220,align:'left',aliascol:'goodsid',
                formatter:function(value,rowData,rowIndex){
                    if(rowData.goodsInfo != null){
                        if(rowData.isdiscount=='1'){
                            return "（折扣）"+rowData.goodsInfo.name;
                        }else if(rowData.isdiscount=='2'){
                            return "（折扣）"+rowData.goodsInfo.name;
                        }else{
                            if(rowData.deliverytype=='1'){
                                return "<font color='blue'>&nbsp;赠 </font>"+rowData.goodsInfo.name;
                            }else if(rowData.deliverytype=='2'){
                                return "<font color='blue'>&nbsp;捆绑 </font>"+rowData.goodsInfo.name;
                            }else{
                                return rowData.goodsInfo.name;
                            }
                        }
                    }else{
                        return "";
                    }
                }
            },
            {field:'barcode', title:'条形码',width:90,align:'left',aliascol:'goodsid',
                formatter:function(value,rowData,rowIndex){
                    if(rowData.isdiscount!='1' && rowData.goodsInfo != null){
                        return rowData.goodsInfo.barcode;
                    }else{
                        return "";
                    }
                }
            },
            {field:'spell', title:'助记符',width:90,align:'left',aliascol:'goodsid',
                formatter:function(value,rowData,rowIndex){
                    if(rowData.goodsInfo != null){
                        return rowData.goodsInfo.spell;
                    }else{
                        return "";
                    }
                }
            },
            {field:'brandid', title:'品牌编码',width:80,align:'left',aliascol:'goodsid',sortable: true,hidden:true,
                formatter:function(value,rowData,rowIndex){
                    if(rowData.goodsInfo != null){
                        return rowData.brandid;
                    }else{
                        return "";
                    }
                }
            },
            {field:'brandName', title:'品牌名称',width:80,align:'left',aliascol:'goodsid',hidden:true,
                formatter:function(value,rowData,rowIndex){
                    if(rowData.goodsInfo != null){
                        return rowData.goodsInfo.brandName;
                    }else{
                        return "";
                    }
                }
            },
            {field:'boxnum', title:'箱装量',aliascol:'goodsid',width:45,align:'right',
                formatter:function(value,rowData,rowIndex){
                    if(rowData.isdiscount!='1' && rowData.goodsInfo != null){
                        return formatterBigNumNoLen(rowData.goodsInfo.boxnum);
                    }else{
                        return "";
                    }
                }
            },
            {field:'unitname', title:'单位',width:35,align:'left'},
            {field:'inittaxprice', title:'初始单价',hidden:true,width:70,align:'right',
                formatter:function(value,row,index){
                    return formatterMoney(value);
                },
            },
            {field:'taxprice', title:'单价',width:70,align:'right',
                formatter:function(value,row,index){
                    if(row.isdiscount!='1' && row.isdiscount!='2'){
                        if(parseFloat(value) > parseFloat(row.inittaxprice)){
                            return "<font color='blue' style='cursor: pointer;' title='原价:"+formatterMoney(row.inittaxprice)+"'>"+ formatterMoney(value)+ "</font>";
                        }else if(parseFloat(value) < parseFloat(row.inittaxprice)){
                            return "<font color='red' style='cursor: pointer;' title='原价:"+formatterMoney(row.inittaxprice)+"'>"+ formatterMoney(value)+ "</font>";
                        }else{
                            return formatterMoney(value);
                        }
                    }
                },
                editor:{
                    type:'numberbox',
                    options:{
                        precision:6
                    }
                }
            },
            <security:authorize url="/sales/receiptbuyprice.do">
            {field:'buyprice', title:'采购价',width:60,align:'right',sortable:true,hidden:true,
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            </security:authorize>
            {field:'boxprice', title:'箱价',aliascol:'taxprice',width:70,align:'right',
                formatter:function(value,row,index){
                    if(row.isdiscount!='1' && row.isdiscount!='2'){
                        if(parseFloat(row.taxprice) > parseFloat(row.inittaxprice)){
                            return "<font color='blue' style='cursor: pointer;'>"+ formatterMoney(value)+ "</font>";
                        }else if(parseFloat(row.taxprice) < parseFloat(row.inittaxprice)){
                            return "<font color='red' style='cursor: pointer;'>"+ formatterMoney(value)+ "</font>";
                        }else{
                            return formatterMoney(value);
                        }
                    }
                },
                editor:{
                    type:'numberbox',
                    options:{
                        precision:2
                    }
                }
            },
            {field:'unitnum', title:'发货数量',width:60,align:'right',
                formatter:function(value,row,index){
                    return formatterBigNumNoLen(value);
                }
            },
            {field:'taxamount', title:'发货金额',width:80,align:'right',
                formatter:function(value,row,index){
                    if(row.discountamount>0){
                        return '<span style="color: blue;cursor: pointer;" title="该商品有折扣，折扣金额为：'+formatterMoney(row.discountamount)+'">'+formatterMoney(value)+'</span>';
                    }else{
                        return formatterMoney(value);
                    }
                }
            },
            {field:'receiptnum', title:'客户接收数量',width:80,align:'right',
                formatter:function(value,row,index){
                    if(row.isdiscount!='1' && row.isdiscount!='2'){
                        if(row.goodsInfo != null &&(row.receiptnum-row.unitnum!=0) && row.receiptnum!=null){
                            return '<span style="color: blue;cursor: pointer;" title="'+row.remark+'">'+formatterBigNumNoLen(value)+'</span>';
                        }else{
                            return formatterBigNumNoLen(value);
                        }
                    }
                },
                editor:{
                    type:'numberbox',
                    options:{
                        min:0,
                        precision:0
                    }
                },
                styler:function(value,row,index){
                    if(row.goodsInfo != null && (row.receiptnum-row.unitnum!=0) && row.receiptnum!=null){
                        return 'background-color:yellow;';
                    }
                }
            },
            {field:'receipttaxamount', title:'应收金额',width:80,align:'right',
                formatter:function(value,row,index){
                    return formatterMoney(value);
                },
                editor:{
                    type:'numberbox',
                    options:{
                        precision:2
                    }
                }
            },
            {field:'taxtype', title:'税种',width:70,align:'left',hidden:true,
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
            {field:'tax', title:'税额',width:80,align:'right',hidden:true,
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'receiptnotaxprice', title:'应收未税单价',width:85,align:'right',hidden:true,
                formatter:function(value,row,index){
                    if(row.isdiscount!='1' && row.isdiscount!='2'){
                        if(parseFloat(value) > parseFloat(row.notaxprice)){
                            return "<font color='blue' style='cursor: pointer;' title='原价:"+formatterMoney(row.notaxprice)+"'>"+ formatterMoney(value)+ "</font>";
                        }else if(parseFloat(value) < parseFloat(row.notaxprice)){
                            return "<font color='red' style='cursor: pointer;' title='原价:"+formatterMoney(row.notaxprice)+"'>"+ formatterMoney(value)+ "</font>";
                        }else{
                            return formatterMoney(value);
                        }
                    }
                },
                editor:{
                    type:'numberbox',
                    options:{
                        precision:6
                    }
                }
            },
            {field:'receiptnotaxamount', title:'应收未税金额',width:80,align:'right', hidden:true,
                formatter:function(value,row,index){
                    return formatterMoney(value);
                },
                editor:{
                    type:'numberbox',
                    options:{
                        precision:2
                    }
                }
            },
            {field:'auxnumdetail', title:'辅数量',width:80,align:'right'},
            {field:'storagelocationid', title:'所属库位',width:80,align:'left',hidden:true,
                formatter:function(value,rowData,rowIndex){
                    return rowData.storagelocationname;
                }
            },
            {field:'batchno', title:'批次号',width:80,align:'left'},
            {field:'produceddate', title:'生产日期',width:80,align:'left'},
            {field:'remark', title:'备注',width:200,align:'left',
                formatter:function(value,row,index){
                    if(value!=null){
                        return '<span style="cursor: pointer;" title="'+row.remark+'">'+row.remark+'</span>';
                    }
                }
            }
        ]]
    });

    //直接核销
    function directWriteoff(ids,customerid){
        $.messager.confirm("提醒","确定核销该销售回单？",function(r){
            if(r){
                loading("核销中..");
                $.ajax({
                    url:'sales/writeoffSalesReceipt.do',
                    dataType:'json',
                    type:'post',
                    data:'ids='+ids+'&customerid='+customerid,
                    success:function(json){
                        loaded();
                        if(json.flag){
                            $.messager.alert("提醒","核销成功!<br>生成销售核销编号："+json.salesInvoiceid);
                            $("#sales-panel-receiptPage").panel('refresh');
                        }else{
                            $.messager.alert("提醒","核销失败!");
                        }
                    },
                    error:function(){
                        loaded();
                        $.messager.alert("错误","核销出错!");
                    }
                });
            }
        });
    }
    //选择收款单
    function unDirectWriteoff(ids,customerid){
        $('<div id="sales-dialog-receiptWriteoff1"></div>').appendTo('#sales-dialog-receiptWriteoff');
        $("#sales-dialog-receiptWriteoff1").dialog({
            title:'关联收款单',
            width:845,
            height:450,
            cache:false,
            modal: true,
            maximizable:true,
            resizable:true,
            href:'account/receivable/showCollectionOrderListCaseReceiptPage.do?ids='+ids+'&customerid='+ customerid,
            onClose:function(){
                $('#sales-dialog-receiptWriteoff1').dialog("destroy");
            }
        });
    }
    $(function(){
        $("#sales-panel-receiptPage").panel({
            href:receipt_url,
            cache:false,
            maximized:true,
            border:false
        });
        //按钮
        $("#sales-buttons-receiptPage").buttonWidget({
            initButton:[
                {},
                <security:authorize url="/sales/receiptHold.do">
                {
                    type: 'button-hold',
                    handler: function(){
                        endEditing(editfiled);
                        $.messager.confirm("提醒","确定暂存该回单信息？",function(r){
                            if(r){
                                $("#sales-addType-receiptAddPage").val("temp");
                                var json = $("#sales-datagrid-receiptAddPage").datagrid('getRows');
                                $("#sales-goodsJson-receiptAddPage").val(JSON.stringify(json));
                                receipt_tempSave_form_submit();
                                $("#sales-form-receiptAddPage").submit();
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/receiptSave.do">
                {
                    type: 'button-save',
                    handler: function(){
                        endEditing(editfiled);
                        loading("提交中..");
                        setTimeout(function(){
                            $("#sales-addType-receiptAddPage").val("real");
                            var json = $("#sales-datagrid-receiptAddPage").datagrid('getRows');
                            $("#sales-goodsJson-receiptAddPage").val(JSON.stringify(json));
                            receipt_realSave_form_submit();
                            $("#sales-form-receiptAddPage").submit();
                        },200);
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/receiptSaveAudit.do">
                {
                    type: 'button-saveaudit',
                    handler: function(){
                        endEditing(editfiled);
                        $.messager.confirm("提醒","确定保存并审核该回单信息？",function(r){
                            if(r){
                                $("#sales-saveaudit-receiptAddPage").val("saveaudit");
                                $("#sales-addType-receiptAddPage").val("real");
                                var json = $("#sales-datagrid-receiptAddPage").datagrid('getRows');
                                $("#sales-goodsJson-receiptAddPage").val(JSON.stringify(json));
                                receipt_realSave_form_submit();
                                $("#sales-form-receiptAddPage").submit();
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/receiptGiveup.do">
                {
                    type:'button-giveup',
                    handler:function(){
                        var type = $("#sales-buttons-receiptPage").buttonWidget("getOperType");
                        if(type == "add"){
                            var currTitle = top.$('#tt').tabs('getSelected').panel('options').title;
                            top.closeTab(currTitle);
                        }
                        else if(type == "edit"){
                            var id = $("#sales-backid-receiptPage").val();
                            if(id == ""){
                                return false;
                            }
                            refreshPanel('sales/receiptViewPage.do?id='+ id);
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/receiptDelete.do">
                {
                    type: 'button-delete',
                    handler: function(){
                        var id = $("#sales-backid-receiptPage").val();
                        if(id == ''){
                            return false;
                        }
                        $.messager.confirm("提醒","确定删除该订单信息？",function(r){
                            if(r){
                                loading("删除中..");
                                $.ajax({
                                    url:'sales/deleteReceipt.do',
                                    dataType:'json',
                                    type:'post',
                                    data:'id='+ id,
                                    success:function(json){
                                        loaded();
                                        if(json.delFlag == true){
                                            $.messager.alert("提醒","该信息已被其他信息引用，无法删除");
                                            return false;
                                        }
                                        if(json.flag == true){
                                            $.messager.alert("提醒","删除成功");
                                            var data = $("#sales-buttons-receiptPage").buttonWidget("removeData", '');
                                            if(data != null){
                                                $("#sales-backid-receiptPage").val(data.id);
                                                refreshPanel('sales/receiptEditPage.do?id='+ data.id);
                                            }
                                            else{
                                                parent.closeNowTab();
                                            }
                                        }
                                        else{
                                            $.messager.alert("提醒","删除失败");
                                        }
                                    },
                                    error:function(){
                                        loaded();
                                        $.messager.alert("错误","删除失败");
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/receiptAudit.do">
                {
                    type: 'button-audit',
                    handler: function(){
                        var id = $("#sales-backid-receiptPage").val();
                        if(id == ''){
                            return false;
                        }
                        $.messager.confirm("提醒","确定审核该回单信息？",function(r){
                            if(r){
                                loading("审核中..");
                                $.ajax({
                                    url:'sales/auditReceipt.do',
                                    dataType:'json',
                                    type:'post',
                                    data:'id='+ id +'&type=1',
                                    success:function(json){
                                        loaded();
                                        if(json.flag == true){
                                            if(json.billId == ""){
                                                $.messager.alert("提醒","审核成功");
                                            }
                                            else{
                                                $.messager.alert("提醒","审核并自动生成退货入库单成功，单据号为："+ json.billId);
                                            }
                                            $("#sales-status-receiptAddPage").val("3");
                                            $("#sales-buttons-receiptPage").buttonWidget("setDataID", {id:id, state:'3', type:'view'});
                                            //refreshPanel('sales/receiptViewPage.do?id='+ id);
                                        }
                                        else{
                                            $.messager.alert("提醒","审核失败，"+json.msg);
                                        }
                                    },
                                    error:function(){
                                        loaded();
                                        $.messager.alert("错误","审核失败");
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/receiptOppaudit.do">
                {
                    type: 'button-oppaudit',
                    handler: function(){
                        var id = $("#sales-backid-receiptPage").val();
                        if(id == ''){
                            return false;
                        }
                        var businessdate = $("#sales-businessdate-receiptAddPage").val();
                        var flag = isDoneOppauditBillCaseAccounting(businessdate);
                        if(!flag){
                            $.messager.alert("提醒","业务日期不在会计区间内或未设置会计区间,不允许反审!");
                            return false;
                        }
                        var auditflag = true;
                        <security:authorize url="/sales/receiptOppauditSupper.do">
                        auditflag = false;
                        </security:authorize>
                        if(auditflag){
                            var businessdate = $("#sales-businessdate-receiptAddPage").val();
                            if(businessdate != '${today}'){
                                $.messager.alert("提醒","回单不能反审，单据业务日期不是今天。需要有权限的人才能反审！");
                                return false;
                            }
                        }
                        $.messager.confirm("提醒","确定反审该回单信息？",function(r){
                            if(r){
                                loading("反审中..");
                                $.ajax({
                                    url:'sales/auditReceipt.do',
                                    dataType:'json',
                                    type:'post',
                                    data:'id='+ id +'&type=2',
                                    success:function(json){
                                        loaded();
                                        if(json.billArg == false){
                                            $.messager.alert("提醒","反审失败，下游单据已生成并审核，无法反审");
                                        }
                                        else{
                                            if(json.flag == true){
                                                $.messager.alert("提醒","反审成功");
                                                refreshPanel('sales/receiptEditPage.do?id='+ id);
                                            }
                                            else{
                                                if(json.invoiceflag==false){
                                                    $.messager.alert("提醒","已生成销售发票或销售开票不能反审！");
                                                }else{
                                                    $.messager.alert("提醒","反审失败。"+json.msg);
                                                }
                                            }
                                        }
                                    },
                                    error:function(){
                                        loaded();
                                        $.messager.alert("错误","反审失败");
                                    }
                                });
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/receiptRelation.do">
                {
                    type: 'button-relation',
                    button:[
                        {
                            type: 'relation-upper',
                            handler: function(){
                                $("#sales-sourceQueryDialog-receiptPage").dialog({ //查询参照上游
                                    title:'销售出库单查询',
                                    width:400,
                                    height:260,
                                    closed:false,
                                    modal:true,
                                    cache:false,
                                    href:'sales/receiptSourceQueryPage.do',
                                    buttons:[{
                                        text:'查询',
                                        handler:function(){
                                            var queryJSON = $("#sales-form-receiptSourceQueryPage").serializeJSON();
                                            $("#sales-sourceQueryDialog-receiptPage").dialog('close', true);
                                            $("#sales-sourceDialog-receiptPage").dialog({
                                                title:'发货单列表',
                                                fit:true,
                                                closed:false,
                                                modal:true,
                                                cache:false,
                                                maximizable:true,
                                                resizable:true,
                                                href:'sales/receiptSourcePage.do',
                                                buttons:[
                                                    {
                                                        text:'确定',
                                                        handler: function(){
                                                            $("#sales-sourceDialog-receiptPage").dialog('close', true);
                                                            var receipt = $("#sales-receiptDatagrid-receiptSourcePage").datagrid('getSelected');
                                                            if(receipt == null){
                                                                $.messager.alert("提醒","请选择一条订单记录");
                                                                return false;
                                                            }
                                                            $("#sales-parentid-receiptPage").val(receipt.id);
                                                            refreshPanel('sales/addReceiptRefer.do?id='+ receipt.id);
                                                        }
                                                    }
                                                ],
                                                onLoad: function(){
                                                    $("#sales-receiptDatagrid-receiptSourcePage").datagrid({
                                                        columns:[[
                                                            {field:'id', title:'编号', width:100},
                                                            {field:'businessdate', title:'业务日期', width:100},
                                                            {field:'customerid',title:'客户简称',width:100,align:'left',
                                                                formatter:function(value,rowData,rowIndex){
                                                                    return rowData.customername;
                                                                }
                                                            },
                                                            {field:'salesdept',title:'销售部门',width:120,align:'left',
                                                                formatter:function(value,rowData,rowIndex){
                                                                    return rowData.salesdeptname;
                                                                }
                                                            },
                                                            {field:'salesuser',title:'客户业务员',width:100,align:'left',
                                                                formatter:function(value,rowData,rowIndex){
                                                                    return rowData.salesusername;
                                                                }
                                                            },
                                                            {field:'addusername',title:'制单人',width:100,align:'left'},
                                                            {field:'remark',title:'备注',width:100,align:'left'}
                                                        ]],
                                                        fit:true,
                                                        method:'post',
                                                        rownumbers:true,
                                                        pagination:true,
                                                        idField:'id',
                                                        singleSelect:true,
                                                        fitColumns:true,
                                                        url:'storage/showSaleOutList.do',
                                                        queryParams: queryJSON,
                                                        onClickRow:function(index, data){
                                                            $("#sales-detailDatagrid-receiptSourcePage").datagrid({
                                                                url:'storage/getSaleOutDetailListByID.do',
                                                                queryParams:{
                                                                    id: data.id
                                                                }
                                                            });
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    }
                                    ]
                                });
                            }
                        },
                        {
                            type: 'relation-upper-view',
                            handler: function(){
                                var saleorderid = $("#sales-saleorderid-receiptAddPage").val();
                                if(saleorderid == ''){
                                    return false;
                                }
                                //var basePath = $("#basePath").attr("href");
                                //top.addOrUpdateTab(basePath+'storage/showSaleOutViewPage.do?id='+ parentid, '发货单');
                                $("#sales-dialog-reletionRejectPage-upp").dialog({
                                    title:'上游出库单列表',
                                    width:1200,
                                    height:450,
                                    closed:false,
                                    modal:true,
                                    cache:false,
                                    maximizable:true,
                                    resizable:true,
                                    href:'sales/showUpperSaleoutList.do?saleorderid='+saleorderid
                                });
                            }
                        }
                    ]
                },
                </security:authorize>
                <security:authorize url="/sales/receiptBack.do">
                {
                    type: 'button-back',
                    handler: function(data){
                        $("#sales-backid-receiptPage").val(data.id);
                        refreshPanel('sales/receiptEditPage.do?id='+ data.id);
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/receiptNext.do">
                {
                    type: 'button-next',
                    handler: function(data){
                        $("#sales-backid-receiptPage").val(data.id);
                        refreshPanel('sales/receiptEditPage.do?id='+ data.id);
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/receiptPreview.do">
                {
                    type: 'button-preview',
                    handler: function(){

                    }
                },
                </security:authorize>
                <security:authorize url="/sales/receiptPrint.do">
                {
                    type: 'button-print',
                    handler: function(){

                    }
                },
                </security:authorize>
                {}
            ],
            buttons:[
                {},
                <security:authorize url="/sales/receiptExport.do">
                {
                    id: 'button-export-detail',
                    name: '导出明细',
                    iconCls: 'button-export',
                    handler: function () {
                        var id = $("#sales-backid-receiptPage").val();
                        $("#sales-export-receiptPage").Excel('export',{
                            type:'exportUserdefined',
                            name:'销售发货回单',
                            fieldParam:{id:id},
                            url:'sales/exportReceipt.do'
                        });
                        $("#sales-export-receiptPage").trigger("click");
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/receiptCancelCheck.do">
                {
                    id:'cancel-check',
                    name:'取消验收',
                    iconCls:'icon-removeCheck',
                    handler:function(){
                        $.messager.confirm("提醒","是否取消回单验收？",function(r){
                            if(r){
                                var id = $("#sales-backid-receiptPage").val();
                                if(id!=""){
                                    loading("取消中..");
                                    $.ajax({
                                        url:'sales/receiptCancelCheck.do',
                                        dataType:'json',
                                        type:'post',
                                        data:{id:id},
                                        success:function(json){
                                            loaded();
                                            if(json.flag == true){
                                                $.messager.alert("提醒","取消回单验收成功!");
                                                $("#sales-panel-receiptPage").panel('refresh', 'sales/receiptEditPage.do?id='+ id);
                                            }
                                            else{
                                                $.messager.alert("提醒","取消回单验收失败!");
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/sales/receiptRelationRejectBillPage.do">
                <c:if test="${receiptAndRejectType=='1' }">
                {
                    id:'relation-rejectbill',
                    name:'关联销售退货通知单',
                    iconCls:'icon-relate',
                    handler:function(){
                        var id = $("#sales-backid-receiptPage").val();
                        $("#sales-dialog-reletionRejectPage").dialog({
                            title:'关联销售退货通知单',
                            fit:true,
                            cache:false,
                            modal: true,
                            maximizable:true,
                            resizable:true,
                            href:'sales/receiptRelationRejectBillPage.do?id='+ id,
                            buttons:[
                                {
                                    text:'确定',
                                    handler: function(){
                                        relationRejectSubmit();
                                    }
                                }
                            ]
                        });
                    }
                },
                </c:if>
                </security:authorize>
                <security:authorize url="/sales/showReceiptRejectBillIdPage.do">
                <c:if test="${receiptAndRejectType=='1' }">
                {
                    id:'relation-rejectbillid',
                    name:'查看关联的退货通知单',
                    iconCls:'icon-relate',
                    handler:function(){
                        var id = $("#sales-backid-receiptPage").val();
                        $("#sales-dialog-receiptRejectBillIdPage").dialog({
                            title:'查看关联的退货通知单',
                            fit:true,
                            cache:false,
                            modal: true,
                            maximizable:true,
                            resizable:true,
                            href:'sales/showReceiptRejectBillIdPage.do?id='+ id,
                            buttons:[
                                {
                                    text:'确定',
                                    handler: function(){
                                        $("#sales-dialog-receiptRejectBillIdPage").dialog("close");
                                    }
                                }
                            ]
                        });
                    }
                },
                </c:if>
                </security:authorize>
                <security:authorize url="/sales/receiptWriteoffBtn.do">
                {
                    id:'receipt-writeoff',
                    name:'核销',
                    iconCls:'button-writeoff',
                    handler:function(){
                        var id = $("#sales-backid-receiptPage").val();
                        var customerid = $("#sales-customer-receiptAddPage").val();
                        if("${isSalesReceiptDirectWriteoff}" == "1"){//直接核销
                            directWriteoff(id,customerid);
                        }else if("${isSalesReceiptDirectWriteoff}" == "0"){//选择收款单
                            unDirectWriteoff(id,customerid);
                        }
                    }
                },
                </security:authorize>
                {}
            ],
            layoutid:'sales-layout-receiptPage',
            model: 'bill',
            type: 'view',
            tab:'销售发货回单列表',
            taburl:'/sales/receiptListPage.do',
            id:'${id}',
            datagrid:'sales-datagrid-receiptListPage'
        });
        //enter建 编辑实际接收数量
        $(document).die("keydown").live("keydown",function(event){
            var type = $("#sales-buttons-receiptPage").buttonWidget("getOperType");
            if(type=="edit"){
                if(event.keyCode==13 && editIndex != undefined){
                    if($('#sales-datagrid-receiptAddPage').datagrid('validateRow', editIndex)){
                        var row = $wareList.datagrid('getRows')[editIndex+1];
                        if(row.isdiscount!='1' && row.isdiscount!='2'){
                            onClickCell(editIndex+1, editfiled);
                        }
                    }
                }
            }
        });
    });
    function refreshPanel(url){ //更新panel
        $("#sales-panel-receiptPage").panel('refresh', url);
    }
    function receipt_tempSave_form_submit(){ //暂存表单方法
        $("#sales-form-receiptAddPage").form({
            onSubmit: function(){
                loading("提交中..");
            },
            success:function(data){
                loaded();
                var json = $.parseJSON(data);
                if(json.lock == true){
                    $.messager.alert("提醒","其他用户正在修改该数据，无法修改");
                    return false;
                }
                if(json.flag==true){
                    $.messager.alert("提醒","暂存成功");
                    $("#sales-backid-receiptPage").val(json.backid);
                    if(json.type == "add"){
                        $("#sales-buttons-receiptPage").buttonWidget("addNewDataId", json.backid);
                    }
                    $("#sales-panel-receiptPage").panel('refresh', 'sales/receiptViewPage.do?id='+ json.backid);
                }
                else{
                    $.messager.alert("提醒","暂存失败");
                }
            }
        });
    }
    function receipt_realSave_form_submit(){ //保存表单方法
        $("#sales-form-receiptAddPage").form({
            onSubmit: function(){
                var flag = $(this).form('validate');
                if(flag==false){
                    return false;
                }
                loading("提交中..");
            },
            success:function(data){
                loaded();
                var json = $.parseJSON(data);
                if(json.lock == true){
                    $.messager.alert("提醒","其他用户正在修改该数据，无法修改");
                    return false;
                }
                if(json.flag==true){
                    var saveaudit = $("#sales-saveaudit-receiptAddPage").val();
                    if(saveaudit=="saveaudit"){
                        if(json.auditflag){
                            if(json.billId == ""){
                                $.messager.alert("提醒","保存审核成功");
                            }
                            else{
                                $.messager.alert("提醒","保存审核成功，并自动生成退货入库单成功，单据号为："+ json.billId);
                            }
                            $("#sales-status-receiptAddPage").val("3");
                            $("#sales-panel-receiptPage").panel('refresh');
                            //$("#sales-buttons-receiptPage").buttonWidget("setDataID", {id:json.backid, state:'3', type:'view'});
                        }else{
                            $.messager.alert("提醒","保存成功,审核失败。"+json.msg);
                        }
                    }else{
                        $.messager.alert("提醒","保存成功。");
                    }
                    $("#sales-backid-receiptPage").val(json.backid);
                    if(json.type == "add"){
                        $("#sales-buttons-receiptPage").buttonWidget("addNewDataId", json.backid);
                    }
                    //$("#sales-panel-receiptPage").panel('refresh', 'sales/receiptViewPage.do?id='+ json.backid);
                }
                else{
                    $.messager.alert("提醒","保存失败");
                }
            }
        });
    }
    function countTotal(){ //计算合计
        var rows = $("#sales-datagrid-receiptAddPage").datagrid('getRows');
        var leng = rows.length;
        var unitnum = 0;
        var taxamount = 0;
        var notaxamount = 0;
        var tax = 0;
        var receipttaxamount = 0;
        var receiptnum = 0;
        var receiptnotaxamount = 0;
        for(var i=0; i<leng; i++){
            unitnum = parseFloat(unitnum) + parseFloat(rows[i].unitnum || 0);
            taxamount = parseFloat(taxamount) + parseFloat(rows[i].taxamount || 0);
            notaxamount = parseFloat(notaxamount) + parseFloat(rows[i].notaxamount || 0);
            tax = parseFloat(tax) + parseFloat(rows[i].tax || 0);
            receiptnum = parseFloat(receiptnum) + parseFloat(rows[i].receiptnum || 0);
            receipttaxamount = parseFloat(receipttaxamount) + parseFloat(rows[i].receipttaxamount || 0);
            receiptnotaxamount = parseFloat(receiptnotaxamount) + parseFloat(rows[i].receiptnotaxamount || 0);
        }
        unitnum = unitnum.toFixed(6);
        taxamount = taxamount.toFixed(6);
        notaxamount = notaxamount.toFixed(6);
        tax = tax.toFixed(6);
        receiptnum = receiptnum.toFixed(6);
        receipttaxamount = receipttaxamount.toFixed(6);
        receiptnotaxamount = receiptnotaxamount.toFixed(6);
        $("#sales-datagrid-receiptAddPage").datagrid('reloadFooter',[{goodsid:'合计', unitnum: unitnum, taxamount: taxamount, notaxamount: notaxamount, tax: tax,receipttaxamount:receipttaxamount,receiptnum:receiptnum,receiptnotaxamount:receiptnotaxamount}]);
    }
    function customerCheck(){ //添加商品明细前确定客户已选
        var customer = $("#sales-customer-receiptAddPage-hidden").val();
        if(customer == ''){
            $.messager.alert("提醒","请先选择客户再进行添加商品信息");
            $("#sales-customer-receiptAddPage").focus();
            return false;
        }
        else{
            return customer;
        }
    }
    function beginAddDetail(){ //开始添加商品信息
        var customer = $("#sales-customer-receiptAddPage").val();
        if(customer == ''){
            $.messager.alert("提醒","请先选择客户再进行添加商品信息");
            $("#sales-customer-receiptAddPage").focus();
            return false;
        }
        $("#sales-dialog-receiptAddPage").dialog({ //弹出新添加窗口
            title:'商品信息(添加)',
            maximizable:true,
            width:600,
            height:450,
            closed:false,
            modal:true,
            cache:false,
            resizable:true,
            buttons:[{
                text:'确定',
                iconCls:'button-save',
                handler:function(){
                    addSaveDetail();
                }
            },{
                text:'继续添加',
                iconCls:'button-save',
                handler:function(){
                    addSaveDetail(true);
                }
            }],
            href:'sales/receiptDetailAddPage.do?cid='+ customer
        });
    }
    function addSaveDetail(go){ //添加新数据确定后操作，
        var flag = $("#sales-form-receiptDetailAddPage").form('validate');
        if(flag==false){
            return false;
        }
        var form = $("#sales-form-receiptDetailAddPage").serializeJSON();
        var goodsJson = $("#sales-goodsId-receiptDetailAddPage").widget('getObject');
        form.goodsInfo = goodsJson;
        var customer = $("#sales-customer-receiptAddPage-hidden").val();
        var rowIndex = 0;
        var rows = $wareList.datagrid('getRows');
        for(var i=0; i<rows.length; i++){
            var rowJson = rows[i];
            if(rowJson.goodsid == undefined){
                rowIndex = i;
                break;
            }
        }
        if(rowIndex == rows.length - 1){
            $wareList.datagrid('appendRow',{}); //如果是最后一行则添加一新行
        }
        $wareList.datagrid('updateRow',{index:rowIndex, row:form}); //将数据更新到列表中
        if(go){ //go为true确定并继续添加一条
            $("#sales-dialog-receiptAddPage").dialog('refresh', 'sales/receiptDetailAddPage.do?cid='+ customer)
        }
        else{ //否则直接关闭
            $("#sales-dialog-receiptAddPage").dialog('close', true)
        }
        $("#sales-customer-receiptAddPage").widget('readonly', true);
        countTotal(); //第添加一条商品明细计算一次合计
    }
    function beginEditDetail(rowData){ //开始修改商品信息
        var customer = $("#sales-customer-receiptAddPage").widget("getValue");
        if(customer == ''){
            $.messager.alert("提醒","请先选择客户再进行添加商品信息");
            $("#sales-customer-receiptAddPage").focus();
            return false;
        }
        var row = $wareList.datagrid('getSelected');
        if(row == null){
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        row.goodsname = row.goodsInfo.name;
        row.model = row.goodsInfo.model;
        row.brandName = row.goodsInfo.brandName;
        row.barcode = row.goodsInfo.barcode;
        var url = '';
        if(row.goodsid == undefined){
            beginAddDetail();
        }
        else{
            url = 'sales/receiptDetailEditPage.do'; //如果是修改页面，数据直接来源于datagrid中的json数据。
            $("#sales-dialog-receiptAddPage").dialog({ //弹出修改窗口
                title:'商品信息(修改)',
                maximizable:true,
                width:600,
                height:450,
                closed:false,
                modal:true,
                cache:false,
                resizable:true,
                buttons:[{
                    text:'确定',
                    iconCls:'button-save',
                    handler:function(){
                        editSaveDetail();
                    }
                },{
                    text:'确定并继续添加',
                    iconCls:'button-save',
                    handler:function(){
                        editSaveDetail(true);
                    }
                }],
                href:url,
                onLoad: function(){
                    $("#sales-form-receiptDetailEditPage").form('load', row);
                    $("#sales-storageLocation-receiptDetailAddPage").widget('setValue', row.storagelocation);
                    receiptMax(Number(row.unitnum));

                    formaterNumSubZeroAndDot();

                    $("#sales-form-receiptDetailEditPage").form('validate');
                }
            });
        }
    }
    function editSaveDetail(go){ //修改数据确定后操作，
        var flag = $("#sales-form-receiptDetailEditPage").form('validate');
        if(flag==false){
            return false;
        }
        var row = $wareList.datagrid('getSelected');
        var rowIndex = $wareList.datagrid('getRowIndex', row);
        var form = $("#sales-form-receiptDetailEditPage").serializeJSON();
        $wareList.datagrid('updateRow',{index:rowIndex, row:form}); //将数据更新到列表中
        if(go){ //go为true确定并继续添加一条
            beginAddDetail();
        }
        else{ //否则直接关闭
            $("#sales-dialog-receiptAddPage").dialog('close', true)
        }
        countTotal();
    }
    function removeDetail(){
        var row = $wareList.datagrid('getSelected');
        if(row == null){
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        $.messager.confirm("提醒","确定删除该商品明细?",function(r){
            if(r){
                var rowIndex = $wareList.datagrid('getRowIndex', row);
                $wareList.datagrid('deleteRow', rowIndex);
                countTotal();
                var rows = $wareList.datagrid('getRows');
                var index = -1;
                for(var i=0; i<rows.length; i++){
                    if(rows[i].goodsid != undefined){
                        index = i;
                        break;
                    }
                }
                if(index == -1){
                    $("#sales-customer-receiptAddPage").widget('readonly', false);
                }
            }
        });
    }
    function receiptMax(max){ //验证接收数最大值
        $.extend($.fn.validatebox.defaults.rules, {
            receiptMax:{
                validator:function(value){
                    return Number(value) <= max;
                },
                message:'请输入小于等于'+max+'的数字!'
            }
        });
    }

    //根据商品(编码或品牌)完成对应折扣
    function computeDiscount(brandid,goodsid,taxamount){
        $.ajax({
            url :'sales/computeDispatchBillDiscountTax.do',
            type:'post',
            data:{brandid:brandid,goodsid:goodsid,taxamount:taxamount},
            dataType:'json',
            async:false,
            success:function(json){
                $("#sales-receipt-notaxamount").numberbox("setValue",json.notaxamount);
                $("#sales-receipt-tax").val(json.tax);
                $("#sales-receipt-taxtype").val(json.taxtype);
                $("#sales-receipt-taxtypename").val(json.taxtypename);
            }
        });
    }

    function beginAddAlert(){
        var detailrows = $("#sales-datagrid-receiptAddPage").datagrid('getRows');
        var goodsid = "";
        for(var i=0; i<detailrows.length; i++){
            var rowJson = detailrows[i];
            if(!isObjectEmpty(rowJson)){
                if(rowJson.goodsid != undefined && (rowJson.isdiscount==null ||rowJson.isdiscount=='0')){
                    if(goodsid==""){
                        if(rowJson.goodsid!=null && rowJson.goodsid!=""){
                            goodsid = rowJson.goodsid;
                        }
                    }else{
                        if(rowJson.goodsid!=null && rowJson.goodsid!=""){
                            goodsid += ","+rowJson.goodsid;
                        }
                    }
                }
            }
        }
        if(goodsid==""){
            $.messager.alert("提醒","请先添加商品信息");
            return false;
        }else{
            return true;
        }
    }

    //显示品牌折扣添加页面
    function beginAddBrandDiscountDetail(){
        if(beginAddAlert()){
            $('<div id="sales-datagrid-receiptAddPage-content"></div>').appendTo('#sales-datagrid-receiptAddPage');
            $("#sales-datagrid-receiptAddPage-content").dialog({
                title: '品牌折扣添加',
                width: 430,
                height: 350,
                collapsible:false,
                minimizable:false,
                maximizable:true,
                resizable:true,
                closed: true,
                cache: false,
                href: 'sales/showReceiptBrandDiscountAddPage.do?receiptid='+encodeURIComponent($("#sales-id-receiptAddPage").val()),
                onClose:function(){
                    $("#sales-datagrid-receiptAddPage-content").dialog("destroy");
                },
                onLoad:function(){
                    $("#sales-receipt-brandid").focus();
                }
            });
            $("#sales-datagrid-receiptAddPage-content").dialog("open");
        }
    }

    //显示回单折扣添加页面
    function beginAddReceiptDiscountDetail(){
        if(beginAddAlert()){
            $('<div id="sales-datagrid-receiptAddPage-content"></div>').appendTo('#sales-datagrid-receiptAddPage');
            $("#sales-datagrid-receiptAddPage-content").dialog({
                title: '回单折扣添加',
                width: 300,
                height: 250,
                collapsible:false,
                minimizable:false,
                maximizable:true,
                resizable:true,
                closed: true,
                cache: false,
                href: 'sales/showReceiptDetailDiscountAddPage.do?receiptid='+encodeURIComponent($("#sales-id-receiptAddPage").val()),
                onClose:function(){
                    $("#sales-datagrid-receiptAddPage-content").dialog("destroy");
                },
                onLoad:function(){
                    //$("#sales-order-discount").next('span').find('input').focus()
                    getNumberBox("sales-receipt-discount").focus();
                }
            });
            $("#sales-datagrid-receiptAddPage-content").dialog("open");
        }
    }
</script>
</body>
</html>
