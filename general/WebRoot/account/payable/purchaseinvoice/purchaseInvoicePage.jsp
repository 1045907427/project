<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>采购发票操作页面</title>
    <%@include file="/include.jsp" %>
    <%@include file="/printInclude.jsp" %>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north',border:false">
        <div class="buttonBG" id="account-buttons-purchaseInvoicePage" style="height:26px;"></div>
    </div>
    <div data-options="region:'center',border:false">
        <div id="account-panel-purchaseInvoicePage"></div>
    </div>
</div>
<div id="account-panel-relation-upper"></div>
<div id="account-panel-sourceQueryPage"></div>
<div id="workflow-addidea-dialog-page"></div>
<input type="hidden" id="account-hidden-billid"/>
<div id="account-dialog-writeoff"></div>
<div id="account-dialog-push-purchaseInvoicePage"></div>
<script type="text/javascript">
    var purchaseInvoice_detail_editmap = null;

    var purchaseInvoice_AjaxConn = function (Data, Action) {
        var MyAjax = $.ajax({
            type: 'post',
            cache: false,
            url: Action,
            data: Data,
            async: true
        });
        return MyAjax.responseText;
    };
    //根据初始的列与用户保存的列生成以及字段权限生成新的列
    var tableColJson = $("#account-datagrid-group-purchaseInvoiceAddPage").createGridColumnLoad({
        name :'t_account_purchase_invoice_detail',
        frozenCol : [[]],
        commonCol : [[
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
            {field:'taxtypename', title:'税种',width:60,aliascol:'taxtype',hidden:true,align:'right'},
            {field:'tax', title:'税额',width:60,align:'right',
                formatter:function(value,row,index){
                    return formatterMoney(value);
                }
            },
            {field:'auxnumdetail', title:'辅数量',width:60,align:'right'},
            {field:'remark', title:'备注',width:400}
        ]]
    });
    var page_url = "";
    var page_type = '${type}';
    if(page_type == "view" || page_type=="handle"){
        page_url = "account/payable/purchaseInvoiceViewPage.do?id=${id}";
    }else if(page_type == "edit"){
        page_url = "account/payable/purchaseInvoiceEditPage.do?id=${id}";
    }else if(page_type == "add"){
        $("#account-panel-relation-upper").dialog({
            href:"account/payable/showPurchaseInvoiceRelationUpperPage.do",
            title:"上游单据查询",
            closed:false,
            modal:true,
            cache:false,
            width:500,
            height:300,
            buttons:[{
                text:'查询',
                handler:function(){
                    sourceQuery();
                }
            }]
        });
    }
    $(function(){
        $("#account-panel-purchaseInvoicePage").panel({
            href:page_url,
            cache:false,
            maximized:true,
            border:false
        });
        //按钮
        $("#account-buttons-purchaseInvoicePage").buttonWidget({
            initButton:[
                {},
                <security:authorize url="/account/payable/purchaseInvoiceAddPage.do">
                {
                    type: 'button-add',
                    handler: function(){
                        top.addOrUpdateTab('account/payable/showPurchaseInvoiceAnotherAddPage.do', "采购发票新增");
                    }
                },
                </security:authorize>
                <security:authorize url="/account/payable/editPurchaseInvoiceHold.do">
                {
                    type: 'button-hold',
                    handler: function(){
                        $.messager.confirm("提醒","确定暂存该采购发票信息？",function(r){
                            if(r){
                                var type = $("#account-buttons-purchaseInvoicePage").buttonWidget("getOperType");
                                if(type=="add"){
                                    //暂存
                                    $("#account-form-purchaseInvoiceAdd").attr("action", "account/payable/addPurchaseInvoiceHold.do");
                                    $("#account-form-purchaseInvoiceAdd").submit();
                                }else if(type=="edit"){
                                    //暂存
                                    $("#account-form-purchaseInvoiceAdd").attr("action", "account/payable/editPurchaseInvoice.do?type=hold");
                                    $("#account-form-purchaseInvoiceAdd").submit();
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/account/payable/editPurchaseInvoiceSave.do">
                {
                    type: 'button-save',
                    handler: function(){
                        $.messager.confirm("提醒","确定保存该采购发票信息？",function(r){
                            if(r){
                                var type = $("#account-buttons-purchaseInvoicePage").buttonWidget("getOperType");
                                if(type=="add"){
                                    //保存
                                    $("#account-form-purchaseInvoiceAdd").attr("action", "account/payable/addPurchaseInvoiceSave.do");
                                    $("#account-form-purchaseInvoiceAdd").submit();
                                }else if(type=="edit"){
                                    $("#account-purchaseInvoice-status").val("2");
                                    $("#account-form-purchaseInvoiceAdd").attr("action", "account/payable/editPurchaseInvoice.do?type=save");
                                    var rows = $("#account-datagrid-group-purchaseInvoiceAddPage").datagrid('getRows');
                                    $("#account-form-purchaseInvoiceAdd").submit();
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/account/payable/purchaseInvoiceGiveUp.do">
                {
                    type:'button-giveup',
                    handler:function(){
                        var type = $("#account-buttons-purchaseInvoicePage").buttonWidget("getOperType");
                        if(type == "add"){
                            var currTitle = top.$('#tt').tabs('getSelected').panel('options').title;
                            top.closeTab(currTitle);
                        }
                        else if(type == "edit"){
                            var id = $("#account-hidden-billid").val();
                            if(id == ""){
                                return false;
                            }
                            $("#account-panel-purchaseInvoicePage").panel({
                                href:'account/payable/purchaseInvoiceViewPage.do?id='+id,
                                title:'',
                                cache:false,
                                maximized:true,
                                border:false
                            });
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/account/payable/deletePurchaseInvoice.do">
                {
                    type: 'button-delete',
                    handler: function(){
                        $.messager.confirm("提醒","是否删除当前采购发票？",function(r){
                            if(r){
                                var id = $("#account-hidden-billid").val();
                                if(id!=""){
                                    loading("删除中..");
                                    $.ajax({
                                        url :'account/payable/deletePurchaseInvoice.do?id='+id,
                                        type:'post',
                                        dataType:'json',
                                        success:function(json){
                                            loaded();
                                            if(json.flag){
                                                var object = $("#account-buttons-purchaseInvoicePage").buttonWidget("removeData",id);
                                                $.messager.alert("提醒", "删除成功");
                                                if(null!=object){
                                                    $("#account-panel-purchaseInvoicePage").panel({
                                                        href:'account/payable/purchaseInvoiceViewPage.do?id='+object.id,
                                                        title:'',
                                                        cache:false,
                                                        maximized:true,
                                                        border:false
                                                    });
                                                }else{
                                                    parent.closeNowTab();
                                                }
                                            }else{
                                                $.messager.alert("提醒", "删除失败");
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/account/payable/auditPurchaseInvoice.do">
                {
                    type: 'button-audit',
                    handler: function(){
                        $.messager.confirm("提醒","是否审核采购发票？",function(r){
                            if(r){
                                var id = $("#account-hidden-billid").val();
                                if(id!=""){
                                    loading("审核中..");
                                    $.ajax({
                                        url :'account/payable/auditPurchaseInvoice.do?id='+id,
                                        type:'post',
                                        dataType:'json',
                                        success:function(json){
                                            loaded();
                                            if(json.flag){
                                                $.messager.alert("提醒","审核成功");
                                                $("#account-panel-purchaseInvoicePage").panel({
                                                    href:'account/payable/purchaseInvoiceViewPage.do?id='+id,
                                                    title:'',
                                                    cache:false,
                                                    maximized:true,
                                                    border:false
                                                });
                                                try{
                                                    tabsWindowURL("/account/payable/purchaseInvoiceListPage.do").$("#account-datagrid-purchaseInvoicePage").datagrid('reload');
                                                }catch(e){
                                                }
                                            }else{
                                                $.messager.alert("提醒","审核失败");
                                            }
                                        },
                                        error:function(){
                                            $.messager.alert("错误","审核失败");
                                        }
                                    });
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/account/payable/oppauditpurchaseInvoice.do">
                {
                    type:'button-oppaudit',
                    handler:function(){
                        var businessdate = $("#account-purchaseInvoice-businessdate").val();
                        var flag = isDoneOppauditBillCaseAccounting(businessdate);
                        if(!flag){
                            $.messager.alert("提醒","业务日期不在会计区间内或未设置会计区间,不允许反审!");
                            return false;
                        }
                        $.messager.confirm("提醒","是否反审采购发票？",function(r){
                            if(r){
                                var id = $("#account-hidden-billid").val();
                                if(id!=""){
                                    loading("反审中..");
                                    $.ajax({
                                        url :'account/payable/oppauditPurchaseInvoice.do?id='+id,
                                        type:'post',
                                        dataType:'json',
                                        success:function(json){
                                            loaded();
                                            if(json.flag){
                                                $.messager.alert("提醒","反审成功");
                                                $("#account-panel-purchaseInvoicePage").panel({
                                                    href:'account/payable/purchaseInvoiceEditPage.do?id='+id,
                                                    title:'',
                                                    cache:false,
                                                    maximized:true,
                                                    border:false
                                                });
                                                try{
                                                    tabsWindowURL("/account/payable/purchaseInvoiceListPage.do").$("#account-datagrid-purchaseInvoicePage").datagrid('reload');
                                                }catch(e){
                                                }
                                            }else{
                                                $.messager.alert("提醒","反审失败");
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/account/payable/showPurchaseInvoiceRelationPage.do">
                {
                    type: 'button-relation',
                    button:[
                        {},
                        <security:authorize url="/account/payable/showPurchaseInvoiceRelationUpperPage.do">
                        {
                            type: 'relation-upper',
                            handler: function(){
                                $("#account-panel-relation-upper").dialog({
                                    href:"account/payable/showPurchaseInvoiceRelationUpperPage.do",
                                    title:"上游单据查询",
                                    closed:false,
                                    modal:true,
                                    cache:false,
                                    width:500,
                                    height:300,
                                    buttons:[{
                                        text:'查询',
                                        handler:function(){
                                            sourceQuery();
                                        }
                                    }]
                                });
                            }
                        },
                        </security:authorize>
                        <security:authorize url="/account/payable/showPurchaseInvoiceSourcePage.do">
                        {
                            type: 'relation-upper-view',
                            handler: function(){
                                var sourcetype = $("#account-purchaseInvoice-sourcetype").val();
                                var sourceid = $("#account-purchaseInvoice-sourceid").val();
                                $("#account-panel-relation-upper").dialog({
                                    href:"account/payable/showPurchaseInvoiceSourceListReferPage.do?sourceid="+sourceid+"&sourcetype="+sourcetype,
                                    title:'采购发票来源单据',
                                    closed:false,
                                    modal:true,
                                    cache:false,
                                    width:700,
                                    height:350
                                });
                            }
                        },
                        </security:authorize>
                        {}
                    ]
                },
                </security:authorize>
                <security:authorize url="/account/payable/purchaseInvoiceWorkflow.do">
                {
                    type: 'button-workflow',
                    button:[
                        {
                            type: 'workflow-submit',
                            handler: function(){
                                $.messager.confirm("提醒","是否提交该采购发票信息到工作流?",function(r){
                                    if(r){
                                        var id = $("#account-hidden-billid").val();
                                        if(id == ""){
                                            $.messager.alert("警告","没有需要提交工作流的信息!");
                                            return false;
                                        }
                                        loading("提交中..");
                                        $.ajax({
                                            url:'account/payable/submitPurchaseInvoicePageProcess.do',
                                            dataType:'json',
                                            type:'post',
                                            data:'id='+id,
                                            success:function(json){
                                                loaded();
                                                if(json.flag == true){
                                                    $.messager.alert("提醒","提交成功!");
                                                    $("#account-panel-purchaseInvoicePage").panel("refresh");
                                                }
                                                else{
                                                    $.messager.alert("提醒","提交失败!");
                                                }
                                            }
                                        });
                                    }
                                });
                            }
                        },
                        {
                            type: 'workflow-addidea',
                            handler: function(){
                                var order_type = '${type}';
                                if(order_type == "handle"){
                                    $("#workflow-addidea-dialog-page").dialog({
                                        title:'填写处理意见',
                                        width:450,
                                        height:300,
                                        closed:false,
                                        cache:false,
                                        modal: true,
                                        href:'workflow/commentAddPage.do?id='+ handleWork_taskId
                                    });
                                }
                            }
                        },
                        {
                            type: 'workflow-viewflow',
                            handler: function(){
                                var id = $("#account-hidden-billid").val();
                                if(id == ""){
                                    return false;
                                }
                                $("#workflow-addidea-dialog-page").dialog({
                                    title:'查看流程',
                                    width:600,
                                    height:450,
                                    closed:false,
                                    cache:false,
                                    modal: true,
                                    maximizable:true,
                                    resizable:true,
                                    href:'workflow/commentListPage.do?id='+ id
                                });
                            }
                        },
                        {
                            type: 'workflow-viewflow-pic',
                            handler: function(){
                                var id = $("#account-hidden-billid").val();
                                if(id == ""){
                                    return false;
                                }
                                $("#workflow-addidea-dialog-page").dialog({
                                    title:'查看流程图',
                                    width:600,
                                    height:450,
                                    closed:false,
                                    cache:false,
                                    modal: true,
                                    maximizable:true,
                                    resizable:true,
                                    href:'workflow/showDiagramPage.do?id='+ id
                                });
                            }
                        },
                        {
                            type: 'workflow-recover',
                            handler: function(){

                            }
                        }
                    ]
                },
                </security:authorize>
                <security:authorize url="/account/payable/purchaseInvoiceBackPage.do">
                {
                    type: 'button-back',
                    handler: function(data){
                        $("#account-panel-purchaseInvoicePage").panel({
                            href:'account/payable/showPurchaseInvoiceEditPage.do?id='+data.id,
                            title:'',
                            cache:false,
                            maximized:true,
                            border:false
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/account/payable/purchaseInvoiceNextPage.do">
                {
                    type: 'button-next',
                    handler: function(data){
                        $("#account-panel-purchaseInvoicePage").panel({
                            href:'account/payable/showPurchaseInvoiceEditPage.do?id='+data.id,
                            title:'',
                            cache:false,
                            maximized:true,
                            border:false
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/account/payable/purchaseInvoicePreviewPage.do">
                {
                    type: 'button-preview',
                    handler: function(){
                    }
                },
                </security:authorize>
                <security:authorize url="/account/payable/purchaseInvoicePrintPage.do">
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
                <security:authorize url="/account/payable/showPurchaseInvoiceCancelPage.do">
                {
                    id:'button-cancel',
                    name:'核销',
                    iconCls:'icon-removed',
                    handler:function(){
                        $.messager.confirm("提醒","是否对当前采购发票进行核销？",function(r){
                            if(r){
                                var invoiceid = $("#account-hidden-billid").val();
                                var supplierid = $("#account-purchaseInvoice-supplierid").supplierWidget("getValue");
                                if(invoiceid!=""){
                                    $("#account-dialog-writeoff").dialog({
                                        href:"account/payable/showWriteoffPayorderPage.do?id="+supplierid+"&invoiceid="+invoiceid,
                                        title:"核销",
                                        fit:true,
                                        modal:true,
                                        cache:false,
                                        maximizable:true,
                                        resizable:true,
                                        cache: false,
                                        modal: true,
                                        buttons:[{
                                            text:'确定',
                                            handler:function(){
                                                payorderWriteOff();
                                            }
                                        }]
                                    });
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/account/payable/showPurchaseInvoicePushPage.do">
                {
                    id:'purchasepush-button',
                    name:'冲差',
                    iconCls:'icon-poorat',
                    handler:function(){

                        $.messager.confirm("提醒","是否对当前采购发票进行冲差?",function(r){
                            if(r){
                                var id = $("#account-purchaseInvoice-id").val();
                                $('#account-dialog-push-purchaseInvoicePage').dialog({
                                    title: '采购发票冲差',
                                    width: 400,
                                    height: 350,
                                    collapsible:false,
                                    minimizable:false,
                                    maximizable:true,
                                    resizable:true,
                                    closed: true,
                                    cache: false,
                                    href: 'account/payable/showPurchaseInvoicePushPage.do?id='+id,
                                    modal: true,
                                    buttons:[
                                        {
                                            text:'确定',
                                            iconCls:'button-save',
                                            plain:true,
                                            handler:function(){
                                                $.messager.confirm("提醒","是否对采购发票应付款进行冲差？",function(r){
                                                    if(r){
                                                        $("#account-form-purchaseInvoicePush").submit();
                                                    }
                                                });

                                            }
                                        }
                                    ]
                                });
                                $('#account-dialog-push-purchaseInvoicePage').dialog("open");
                            }
                        });
                    }
                },
                </security:authorize>
                <c:if test="${iswriteoff == '1'}">
                <security:authorize url="/account/payable/purchaseInvoiceUncancel.do">
                {
                    id:'button-uncancel',
                    name:'反核销',
                    iconCls:'button-getdown',
                    handler:function(){
                        var flag = isDoneOppauditBillCaseAccounting("${writeoffdate}");
                        if(!flag){
                            $.messager.alert("提醒","核销日期不在会计区间内或未设置会计区间,不允许反审!");
                            return false;
                        }
                        $.messager.confirm("提醒","是否对当前采购发票进行反核销？",function(r){
                            if(r){
                                var invoiceid = $("#account-hidden-billid").val();
                                if(invoiceid!=""){
                                    loading("反核销中...");
                                    $.ajax({
                                        url:'account/payable/uncancelPurchaseInvoice.do',
                                        dataType:'json',
                                        type:'post',
                                        data:'invoiceid='+invoiceid,
                                        success:function(json){
                                            loaded();
                                            if(json.flag){
                                                $.messager.alert("提醒","反核销成功!");
                                                $("#account-panel-purchaseInvoicePage").panel("refresh");
                                            }
                                            else{
                                                $.messager.alert("提醒","反核销失败!");
                                            }
                                        }
                                    });
                                }
                            }
                        });
                    }
                },
                </security:authorize>
                </c:if>
                {}
            ],
            model: 'bill',
            type: 'view',
            tab:'采购发票列表',
            taburl:'/account/payable/purchaseInvoiceListPage.do',
            id:'${id}',
            datagrid:'account-datagrid-purchaseInvoicePage'
        });
    });
    //显示采购发票明细列表根据商品编码
    function showPurchaseInvoiceDetailListByGoodsid(){
        var billid = $("#account-purchaseInvoice-id").val();
        var row = $("#account-datagrid-group-purchaseInvoiceAddPage").datagrid('getSelected');
        if(row == null){
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        $('<div id="account-dialog-goodsdetail-purchaseInvoiceAddPage-content"></div>').appendTo('#account-dialog-goodsdetail-purchaseInvoiceAddPage');
        $('#account-dialog-goodsdetail-purchaseInvoiceAddPage-content').dialog({
            title: '商品：'+row.goodsid+row.goodsname+' 明细详情',
            width: 950,
            height: 450,
            collapsible:false,
            minimizable:false,
            maximizable:true,
            resizable:true,
            closed: true,
            cache: false,
            href: 'account/payable/showPurchaseInvoiceDetailListByGoodsidPage.do',
            queryParams:{billid:billid,goodsid:row.goodsid},
            modal: true,
            buttons:[
                {
                    text:'确定',
                    iconCls:'button-save',
                    plain:true,
                    handler:function(){
                        virtualEditSaveDetal();
                    }
                }
            ],
            onClose:function(){
                $('#account-dialog-goodsdetail-purchaseInvoiceAddPage-content').dialog("destroy");
            }
        });
        $('#account-dialog-goodsdetail-purchaseInvoiceAddPage-content').dialog("open");
    }

    //虚拟保存修改过的商品明细
    function virtualEditSaveDetal(){
        var rows = $("#account-datagrid-purchaseInvoiceAddPage").datagrid('getRows');
        var updateRows = new Array();
        for(var j=0;j<rows.length;j++){
            if(!isObjectEmpty(rows[j])){
                if(rows[j].isedit == "1"){
                    updateRows.push(rows[j]);
                }
            }
        }
        purchaseInvoice_detail_editmap[rows[0].goodsid] = updateRows;

        $('#account-dialog-goodsdetail-purchaseInvoiceAddPage-content').dialog("close");
    }
    //删除冲差
    function removePushRow(){
        var id = $("#account-purchaseInvoice-id").val();
        var row = $("#account-datagrid-group-purchaseInvoiceAddPage").datagrid('getSelected');
        if(row == null){
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        loading("删除中...");
        if(row.sourcetype == "3"){
            $.ajax({
                type:'post',
                dataType:'json',
                data:'id='+id,
                url:"account/payable/deleteInvoiceBeginDueDetail.do",
                success:function(json){
                    loaded();
                    if(json.flag){
                        $("#account-panel-purchaseInvoicePage").panel("refresh");
                    }else{
                        $.messager.alert("提醒","应付款期初删除失败!");
                    }
                }
            });
        }else{
            $.ajax({
                type:'post',
                dataType:'json',
                data:'id='+row.field01,
                url:"account/payable/deleteInvoicePushDetail.do",
                success:function(json){
                    loaded();
                    if(json.flag){
                        $("#account-panel-purchaseInvoicePage").panel("refresh");
                    }else{
                        $.messager.alert("提醒","冲差删除失败!");
                    }
                }
            });
        }
    }
    //修改冲差
    function editPushRow(suppliername,supplierid){

        var id = $("#account-purchaseInvoice-id").val();
        var row = $("#account-datagrid-group-purchaseInvoiceAddPage").datagrid('getSelected');
        if(row == null){
            $.messager.alert("提醒", "请选择一条冲差记录");
            return false;
        }
        $('#account-dialog-push-purchaseInvoicePage').dialog({
            title: '采购发票冲差',
            width: 400,
            height: 350,
            collapsible:false,
            minimizable:false,
            maximizable:true,
            resizable:true,
            closed: true,
            cache: false,
            href: 'account/payable/showPurchaseInvoicePushEditPage.do?id='+row.field01,
            modal: true,
            buttons:[
                {
                    text:'确定',
                    iconCls:'button-save',
                    plain:true,
                    handler:function(){
                        $.messager.confirm("提醒","是否对采购发票应付款进行冲差？",function(r){
                            if(r){
                                $("#account-form-purchaseInvoicePush").submit();
                            }
                        });

                    }
                }
            ],
            onLoad:function(){
                getNumberBox("account-purchaseInvoicePush-amount").select();
                getNumberBox("account-purchaseInvoicePush-amount").focus();
            }
        });
        $('#account-dialog-push-purchaseInvoicePage').dialog("open");
    }

    //获取所有修改过的商品明细
    function getTotalUpdateDetailRows(){
        var updateRows = new Array();
        if(!isObjectEmpty(purchaseInvoice_detail_editmap)){
            $.map(purchaseInvoice_detail_editmap, function(rows){
                if(null != rows && rows.length != 0){
                    for(var i=0;i<rows.length;i++){
                        if(!isObjectEmpty(rows[i])){
                            updateRows.push(rows[i]);
                        }
                    }
                }
            });
        }
        return updateRows;
    }

    //显示盘点单明细修改页面
    function beginEditDetail(){
        var row = $("#account-datagrid-purchaseInvoiceAddPage").datagrid('getSelected');
        if(row == null){
            $.messager.alert("提醒", "请选择一条记录");
            return false;
        }
        //var type = $("#account-purchaseInvoice-sourcetype").val();
        $('<div id="account-dialog-purchaseInvoiceAddPage-content"></div>').appendTo('#account-dialog-purchaseInvoiceAddPage');
        $('#account-dialog-purchaseInvoiceAddPage-content').dialog({
            title: '采购发票明细修改',
            width: 680,
            height: 400,
            collapsible:false,
            minimizable:false,
            maximizable:true,
            resizable:true,
            closed: true,
            cache: false,
            href: 'account/payable/showPurchaseInvoiceDetailEditPage.do?type='+row.sourcetype,
            modal: true,
            buttons:[
                {
                    text:'确定',
                    iconCls:'button-save',
                    plain:true,
                    handler:function(){
                        setTimeout("editSaveDetail(false)",50);
                    }
                }
            ],
            onClose:function(){
                $('#account-dialog-purchaseInvoiceAddPage-content').dialog("destroy");
            },
            onLoad:function(){
                getNumberBox("account-purchaseInvoice-unitnum").focus();
                getNumberBox("account-purchaseInvoice-unitnum").select();
            }
        });
        $('#account-dialog-purchaseInvoiceAddPage-content').dialog("open");
    }
    //修改保存
    function editSaveDetail(goFlag) {
        //商品明细
        var flag = $("#account-form-purchaseInvoiceDetailAddPage").form('validate');
        if (flag == false) {
            return false;
        }
        try{
            unitnumTriggerChange();
        }catch(e){

        }
        var form = $("#account-form-purchaseInvoiceDetailAddPage").serializeJSON();
        var row = $("#account-datagrid-purchaseInvoiceAddPage").datagrid('getSelected');
        var rowIndex = $("#account-datagrid-purchaseInvoiceAddPage").datagrid('getRowIndex', row);
        form.goodsInfo = row.goodsInfo;
        if ((Number(row.initunitnum) != Number(form.unitnum)) || (row.initremark != form.remark)) {
            form.isedit = "1";
        } else {
            form.isedit = "0";
        }
        $("#account-datagrid-purchaseInvoiceAddPage").datagrid('updateRow', {index: rowIndex, row: form}); //将数据更新到列表中
        $("#account-dialog-purchaseInvoiceAddPage-content").dialog('close');
        countTotal("account-datagrid-purchaseInvoiceAddPage");

        //发票商品分组合计
        var footrows = $("#account-datagrid-purchaseInvoiceAddPage").datagrid("getFooterRows");

        var groupremark = "";
        var remarkrows =  $("#account-datagrid-purchaseInvoiceAddPage").datagrid('getRows');
        for(var i=0;i<remarkrows.length;++i){
            if(!isObjectEmpty(remarkrows[i]) && remarkrows[i].remark != "" && undefined != remarkrows[i].remark){
                if(groupremark == ""){
                    groupremark = remarkrows[i].remark;
                }else{
                    groupremark += ";" + remarkrows[i].remark;
                }
            }
        }
        var grouprow = $("#account-datagrid-group-purchaseInvoiceAddPage").datagrid("getSelected");
        //根据商品编码、数量计算未税金额等
        var ret = purchaseInvoice_AjaxConn({goodsid:grouprow.goodsid,price:grouprow.taxprice,unitnum:footrows[0].unitnum},'account/payable/countGoodsInfoNumber.do');
        if(grouprow.goodsid == row.goodsid){
            var retjson = $.parseJSON(ret);
            grouprow.unitnum = footrows[0].unitnum;
            grouprow.taxamount = footrows[0].taxamount;
            grouprow.notaxamount = footrows[0].notaxamount;
            grouprow.tax = footrows[0].tax;
            grouprow.auxnumdetail = retjson.auxnumdetail;
            if(null != groupremark){
                grouprow.remark = groupremark;
            }
            if((Number(grouprow.initunitnum) != Number(grouprow.unitnum)) || (grouprow.initremark != grouprow.remark)){
                grouprow.isedit = "1";
            }else{
                grouprow.isedit = "0";
            }
            var groupindex = $("#account-datagrid-group-purchaseInvoiceAddPage").datagrid('getRowIndex', grouprow);
            $("#account-datagrid-group-purchaseInvoiceAddPage").datagrid('updateRow',{index:groupindex, row:grouprow});

            countTotal("account-datagrid-group-purchaseInvoiceAddPage");
        }
    }
    //计算合计
    function countTotal(datagridID){
        var rows =  $("#"+datagridID+"").datagrid('getRows');
        var countNum = 0;
        var taxamount = 0;
        var notaxamount = 0;
        var tax = 0;
        for(var i=0;i<rows.length;++i){
            if(!isObjectEmpty(rows[i])){
                countNum = Number(countNum)+Number(rows[i].unitnum == undefined ? 0 : rows[i].unitnum);
                taxamount = Number(taxamount)+Number(rows[i].taxamount == undefined ? 0 : rows[i].taxamount);
                notaxamount = Number(notaxamount)+Number(rows[i].notaxamount == undefined ? 0 : rows[i].notaxamount);
                tax = Number(tax)+Number(rows[i].tax == undefined ? 0 : rows[i].tax);
                //discountamount = Number(discountamount)+Number(rows[i].discountamount == undefined ? 0 : rows[i].discountamount);
            }
        }
        $("#"+datagridID+"").datagrid("reloadFooter",[{goodsid:'合计',unitnum:countNum,taxamount:taxamount,notaxamount:notaxamount,tax:tax}]);
    }
</script>
<%--打印开始 --%>
<script type="text/javascript">
    function updateDataGridPrintimes(id){
        var thepage=tabsWindowURL("/account/payable/purchaseInvoiceListPage.do");
        if(thepage==null){
            return false;
        }
        var thegrid=thepage.$("#account-datagrid-purchaseInvoicePage");
        if(thegrid==null || thegrid.size()==0){
            return false;
        }

        if(id==null || id==""){
            thegrid.datagrid('reload');
            return false;
        }

        var datarow=thegrid.datagrid("getRows");
        if(null!=datarow && datarow.length>0){
            for(var i=0;i<datarow.length;i++){
                if(datarow[i].id==id){
                    var obj={}
                    if(datarow[i].printtimes && !isNaN(datarow[i].printtimes)){
                        datarow[i].printtimes=datarow[i].printtimes+1;
                    }else{
                        datarow[i].printtimes=1;
                    }
                    obj.printtimes=datarow[i].printtimes;
                    thegrid.datagrid('updateRow',{index:i,row:obj});
                    break;
                }
            }
        }
    }
    $(function () {
        //打印
        AgReportPrint.init({
            id: "PurchaseInvoice-dialog-print",
            code: "purchase_invoice",
            url_preview: "print/account/purchaseInvoicePrintView.do",
            url_print: "print/account/purchaseInvoicePrint.do",
            btnPreview: "button-preview",
            btnPrint: "button-print",
            printlimit:"${printlimit}",
            getData: function (tableId, printParam) {
                var id = $("#account-hidden-billid").val();
                if(id == ""){
                    $.messager.alert("警告","找不到要打印的信息!");
                    return false;
                }
                var status=$("#account-purchaseInvoice-status").val() || "";
                if(!(status=='3' || status=='4')){
                    $.messager.alert("提醒",id+"保存状态下单据不可打印");
                    return false;
                }
                printParam.idarrs = id;
                var printtimes=$("#sales-printtimes-offpriceAddPage").val() || 0;
                if(printtimes>0)
                    printParam.printIds = [id];
                return true;
            },
            onPrintSuccess:function(option){
                updateDataGridPrintimes($("#account-hidden-billid").val());
            }
        });
    });
</script>
<%--打印结束 --%>
</body>
</html>
