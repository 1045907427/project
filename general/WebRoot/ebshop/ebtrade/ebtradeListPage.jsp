<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>电商交易列表页面</title>
    <%@include file="/include.jsp" %>
</head>
<body>
<div id="show" style="display: none">显示</div>
<div class="easyui-layout" data-options="fit:true,border:true">
    <div data-options="region:'north',split:false,border:false">
        <div class="buttonBG" id="ebshop-buttons-ebtrageListPage" ></div>
    </div>
    <div data-options="region:'center'">
        <div id="ebshop-toobar-ebtrageListPage" style="padding:0px;">
            <form id="ebshop-queryForm-ebtrageListPage" method="post">
                <table class="querytable">
                    <tr>
                        <td>单据编号：</td>
                        <td class="tdinput"><input type="text" name="id" class="len150" /></td>
                        <td>昵称：</td>
                        <td><input type="text" name="nick" class="len150" /></td>
                        <td>电商类型：</td>
                        <td>
                            <select name="etype" style="width: 120px;">
                                <option></option>
                                <c:forEach items="${etypeList }" var="list">
                                    <option value="${list.code }">${list.codename }</option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>收货人：</td>
                        <td><input type="text" name="receiverName" class="len150" /></td>
                        <td>是否拆单：</td>
                        <td>
                            <select name="isSplit" class="len150">
                                <option></option>
                                <option value="0">否</option>
                                <option value="1">是</option>
                            </select>
                        </td>
                        <td>本地订单状态：</td>
                        <td>
                            <select name="state" class="len120">
                                <option></option>
                                <option value="0">未审核</option>
                                <option value="1">已审核</option>
                                <option value="2">已获取物流单号</option>
                                <option value="4">物流单据已打印</option>
                                <option value="5">已发货</option>
                                <option value="6">已生成发货单</option>
                                <option value="9">作废</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="4"></td>
                        <td colspan="2">
                            <a href="javascript:void(0);" id="ebshop-queay-ebtrageListPage" class="button-qr">查询</a>
                            <a href="javaScript:void(0);" id="ebshop-resetQueay-ebtrageListPage" class="button-qr">重置</a>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
        <table id="ebshop-datagrid-ebtrageListPage"></table>
    </div>
</div>
<div id="ebshop-ebtrade-dialog-split"></div>
<div id="ebshop-ebtrade-dialog-merge"></div>
<script type="text/javascript">
    var initQueryJSON = $("#ebshop-queryForm-ebtrageListPage").serializeJSON();

    var ebtrage_AjaxConn = function (Data, Action) {
        var MyAjax = $.ajax({
            type: 'post',
            cache: false,
            url: Action,
            data: Data,
            async: false
        })
        return MyAjax.responseText;
    }

    //执行合并操作
    function updateEbtradeMergeMethod(ids){
        loading("合并中..");
        $.ajax({
            url :'ebtrade/updateEbtradeMerge.do',
            type:'post',
            dataType:'json',
            data:{ids:ids},
            success:function(retJSON){
                loaded();
                if(retJSON.flag){
                    $.messager.alert("提醒","合并生成新的交易订单 编号:"+retJSON.newid);
                    var queryJSON = $("#ebshop-queryForm-ebtrageListPage").serializeJSON();
                    $("#ebshop-datagrid-ebtrageListPage").datagrid("load",queryJSON);
                    $("#ebshop-datagrid-ebtrageListPage").datagrid('clearSelections');
                    $("#ebshop-datagrid-ebtrageListPage").datagrid('clearChecked');
                }
                else{
                    if(!retJSON.doflag){
                        $.messager.alert("提醒","不允许合单！<br>原因：单据状态不为未审核状态或单据的收货人、电话、收获地址不一致！");
                    }else{
                        $.messager.alert("提醒","合并失败！");
                    }
                }
            },
            error: function(){
                $.messager.alert("提醒","合并出错！");
            }
        });
    }

    //执行合并操作
    function doEbtradeMerge(ids){
        var ret = ebtrage_AjaxConn({ids:ids},"ebtrade/doCheckHashReturnOrders.do");
        var retjson = $.parseJSON(ret);
        if(retjson.retids != ""){
            $.messager.confirm('确认','交易订单 编号：'+retjson.retids+'存在退货，确定合并?',function(r){
                if(r){
                    updateEbtradeMergeMethod(ids);
                }
            });
        }else{
            updateEbtradeMergeMethod(ids);
        }
    }
    $(function(){

        $("#ebshop-buttons-ebtrageListPage").buttonWidget({
            initButton:[
                {},
                <security:authorize url="/ebtrade/ebtradeAudit.do">
                {
                    type: 'button-audit',
                    handler: function(){
                        var rows = $("#ebshop-datagrid-ebtrageListPage").datagrid('getChecked');
                        if(rows.length == 0){
                            $.messager.alert("提醒","请勾选交易信息!");
                            return false;
                        }
                        var idStr = "",oppauditids = "",hasmsgids = "";
                        for(var i=0;i<rows.length;i++){
                            if(rows[i].state == "0"){
                                var id = rows[i].id;
                                if(idStr == ""){
                                    idStr = id;
                                }else{
                                    idStr += "," + id;
                                }
                                if(rows[i].hasBuyerMessage == "1"){
                                    if(hasmsgids == ""){
                                        hasmsgids = id;
                                    }else{
                                        hasmsgids += "," + id;
                                    }
                                }
                            }else{
                                if(oppauditids == ""){
                                    oppauditids = rows[i].id;
                                }else{
                                    oppauditids += "," + rows[i].id;;
                                }
                                var index = $("#ebshop-datagrid-ebtrageListPage").datagrid('getRowIndex',rows[i]);
                                $("#ebshop-datagrid-ebtrageListPage").datagrid('uncheckRow',index);
                            }
                        }
                        if(oppauditids != ""){
                            $.messager.alert("提醒","交易信息 编号：" + oppauditids + "不为未审核状态，不允许审核；");
                        }
                        if(hasmsgids != ""){
                            $.messager.alert("提醒","交易信息 编号：" + hasmsgids + "有买家留言！");
                        }
                        if(idStr != ""){
                            loading("审核中..");
                            $.ajax({
                                url :'ebtrade/auditEbtrade.do',
                                type:'post',
                                dataType:'json',
                                data:{idStr:idStr},
                                success:function(json){
                                    loaded();
                                    var msg = "";
                                    if(json.retmsg != ""){
                                        msg = json.retmsg;
                                    }
                                    if(json.sucids != ""){
                                        if(msg == ""){
                                            msg = "交易信息 编号：" + json.sucids + "审核交易信息成功；";
                                        }else{
                                            msg += "<br>" + "交易信息 编号：" + json.sucids + "审核交易信息成功；";
                                        }
                                    }
                                    if(json.unsucids != ""){
                                        if(msg == ""){
                                            msg = "交易信息 编号：" + json.unsucids + "审核交易信息失败；";
                                        }else{
                                            msg += "<br>" + "交易信息 编号：" + json.unsucids + "审核交易信息失败；";
                                        }
                                    }
                                    if(json.msg != ""){
                                        if(msg == ""){
                                            msg = json.msg;
                                        }else{
                                            msg += "<br>" + json.msg;
                                        }
                                    }
                                    if(msg != ""){
                                        $.messager.alert("提醒",msg);
                                        $("#ebshop-datagrid-ebtrageListPage").datagrid('reload');
                                    }
                                },
                                error:function(){
                                    loaded();
                                    $.messager.alert("提醒","审核出错！");
                                }
                            });
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/ebtrade/ebtradeOppaudit.do">
                {
                    type: 'button-oppaudit',
                    handler: function(){
                        var rows = $("#ebshop-datagrid-ebtrageListPage").datagrid('getChecked');
                        if(rows.length == 0){
                            $.messager.alert("提醒","请勾选交易信息!");
                            return false;
                        }
                        var idStr = "",unoppauditids = "";
                        for(var i=0;i<rows.length;i++){
                            if(rows[i].state == "1"){
                                var id = rows[i].id;
                                if(idStr == ""){
                                    idStr = id;
                                }else{
                                    idStr += "," + id;
                                }
                            }else{
                                if(unoppauditids == ""){
                                    unoppauditids = rows[i].id;
                                }else{
                                    unoppauditids += "," + rows[i].id;;
                                }
                                var index = $("#ebshop-datagrid-ebtrageListPage").datagrid('getRowIndex',rows[i]);
                                $("#ebshop-datagrid-ebtrageListPage").datagrid('uncheckRow',index);
                            }
                        }
                        if(unoppauditids != ""){
                            $.messager.alert("提醒","交易信息 编号：" + unoppauditids + "不为审核状态，不允许反审；");
                        }
                        if(idStr != ""){
                            loading("反审中..");
                            $.ajax({
                                url :'ebtrade/oppauditEbtrade.do',
                                type:'post',
                                dataType:'json',
                                data:{idStr:idStr},
                                success:function(json){
                                    loaded();
                                    var msg = "";
                                    if(json.retmsg != ""){
                                        msg = json.retmsg;
                                    }
                                    if(json.sucids != ""){
                                        if(msg == ""){
                                            msg = "交易信息 编号：" + json.sucids + "反审交易信息成功；";
                                        }else{
                                            msg += "<br>" + "交易信息 编号：" + json.sucids + "反审交易信息成功；";
                                        }
                                    }
                                    if(json.unsucids != ""){
                                        if(msg == ""){
                                            msg = "交易信息 编号：" + json.unsucids + "反审交易信息失败；";
                                        }else{
                                            msg += "<br>" + "交易信息 编号：" + json.unsucids + "反审交易信息失败；";
                                        }
                                    }
                                    if(msg != ""){
                                        $.messager.alert("提醒",msg);
                                        $("#ebshop-datagrid-ebtrageListPage").datagrid('reload');
                                    }
                                },
                                error:function(){
                                    loaded();
                                    $.messager.alert("提醒","反审出错！");
                                }
                            });
                        }
                    }
                },
                </security:authorize>
                {}
            ],
            buttons:[
                <security:authorize url="/ebtrade/getWaybillInfoBtn.do">
                {
                    id:'getWaybillInfo',
                    name:'申请物流面单',
                    iconCls:'button-audit',
                    handler:function(){
                        var rows = $("#ebshop-datagrid-ebtrageListPage").datagrid('getChecked');
                        if(rows.length == 0){
                            $.messager.alert("提醒","请勾选交易信息!");
                            return false;
                        }
                        var idStr = "",unids = "";
                        for(var i=0;i<rows.length;i++){
                            if(rows[i].state == "1"){
                                var id = rows[i].id;
                                if(idStr == ""){
                                    idStr = id;
                                }else{
                                    idStr += "," + id;
                                }
                            }else{
                                if(unids == ""){
                                    unids = rows[i].id;
                                }else{
                                    unids += "," + rows[i].id;;
                                }
                                var index = $("#ebshop-datagrid-ebtrageListPage").datagrid('getRowIndex',rows[i]);
                                $("#ebshop-datagrid-ebtrageListPage").datagrid('uncheckRow',index);
                            }
                        }
                        if(unids != ""){
                            $.messager.alert("提醒","交易信息 编号：" + unids + "不为审核状态，不允许申请物流面单；");
                        }
                        if(idStr != ""){
                            loading("申请中..");
                            $.ajax({
                                url :'ebtrade/getWaybillInfo.do',
                                type:'post',
                                dataType:'json',
                                data:{idStr:idStr},
                                success:function(json){
                                    loaded();
                                    var msg = "";
                                    if(undefined != json.sucids && json.sucids != ""){
                                        msg = "交易信息 编号：" + json.sucids + "申请成功；";
                                    }
                                    if(undefined != json.unsucmsg && json.unsucmsg != ""){
                                        if(msg == ""){
                                            msg = json.unsucmsg + "不允许申请；";
                                        }else{
                                            msg += "<br>" + json.unsucmsg +"不允许申请；";
                                        }
                                    }
                                    if(json.retmsg != ""){
                                        if(msg == ""){
                                            msg = json.retmsg;
                                        }else{
                                            msg += "<br>" + json.retmsg;
                                        }
                                    }
                                    if(msg != ""){
                                        $.messager.alert("提醒",msg);
                                        $("#ebshop-datagrid-ebtrageListPage").datagrid('reload');
                                    }
                                },
                                error:function(){
                                    loaded();
                                    $.messager.alert("提醒","申请物流面单出错！");
                                }
                            });
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/ebtrade/printWaybillInfoBtn.do">
                {
                    id:'printWaybillInfo',
                    name:'打印物流面单',
                    iconCls:'button-print',
                    handler:function(){
                        var rows = $("#ebshop-datagrid-ebtrageListPage").datagrid('getChecked');
                        if(rows.length == 0){
                            $.messager.alert("提醒","请勾选交易信息!");
                            return false;
                        }
                        var idStr = "";
                        for(var i=0;i<rows.length;i++){
                            var id = rows[i].id;
                            if(idStr == ""){
                                idStr = id;
                            }else{
                                idStr += "," + id;
                            }
                        }
                        $.ajax({
                            url :'ebtrade/checkWayBillPrintFlag.do',
                            type:'post',
                            dataType:'json',
                            data:{idStr:idStr},
                            success:function(json){
                                var obj = json.retobj;
                                if(!obj.flag){
                                    $.messager.alert('提醒','勾选的交易信息的打印状态未通过验证，不允许打印!');
                                }else{
                                    var retmsg = "",unsucids = "",sucids = "";
                                    var waybillArr = obj.items;
                                    for(var i=0;i<waybillArr.length;i++){
                                        var wayobj = waybillArr[i];
                                        if(!wayobj.flag){
                                            if(retmsg == ""){
                                                retmsg = "交易信息 编号："+wayobj.package_id+" "+wayobj.msg+ "，不允许打印；";
                                            }else{
                                                retmsg += "<br>" + "交易信息 编号："+wayobj.package_id+" "+wayobj.msg+ "，不允许打印；";
                                            }
                                            if(unsucids == ""){
                                                unsucids = wayobj.package_id;
                                            }else{
                                                unsucids += "," + wayobj.package_id;
                                            }
                                        }else{
                                            if(sucids == ""){
                                                sucids = wayobj.package_id;
                                            }else{
                                                sucids += "," + wayobj.package_id;
                                            }
                                        }
                                    }
                                    for(var i=0;i<rows.length;i++){
                                        var row = rows[i];
                                        if(unsucids.indexOf(row.id) != -1){
                                            var index = $("#ebshop-datagrid-ebtrageListPage").datagrid('getRowIndex',row);
                                            $("#ebshop-datagrid-ebtrageListPage").datagrid('uncheckRow',index);
                                        }
                                    }
                                    if(retmsg != ""){
                                        $.messager.alert("提醒",retmsg);
                                        return false;
                                    }else{
                                        $.messager.alert("提醒","允许打印！");
                                    }
                                }
                            },
                            error:function(){
                                loaded();
                                $.messager.alert("提醒","打印物流面单出错！");
                            }
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/ebtrade/cancelWayBillBtn.do">
                {
                    id:'cancelWayBill',
                    name:'取消物流信息',
                    iconCls:'button-oppaudit',
                    handler:function(){
                        var rows = $("#ebshop-datagrid-ebtrageListPage").datagrid('getChecked');
                        if(rows.length == 0){
                            $.messager.alert("提醒","请勾选交易信息!");
                            return false;
                        }
                        var idStr = "",uncancalids = "";
                        for(var i=0;i<rows.length;i++){
                            if(rows[i].state == "2"){
                                var id = rows[i].id;
                                if(idStr == ""){
                                    idStr = id;
                                }else{
                                    idStr += "," + id;
                                }
                            }else{
                                if(uncancalids == ""){
                                    uncancalids = rows[i].id;
                                }else{
                                    uncancalids += "," + rows[i].id;;
                                }
                                var index = $("#ebshop-datagrid-ebtrageListPage").datagrid('getRowIndex',rows[i]);
                                $("#ebshop-datagrid-ebtrageListPage").datagrid('uncheckRow',index);
                            }
                        }
                        if(uncancalids != ""){
                            $.messager.alert("提醒","交易信息 编号：" + uncancalids + "不为已获取物流单号状态下，不允许取消打印信息；");
                        }
                        if(idStr != ""){
                            loading("取消中..");
                            $.ajax({
                                url :'ebtrade/cancelWayBill.do',
                                type:'post',
                                dataType:'json',
                                data:{idStr:idStr},
                                success:function(json){
                                    loaded();
                                    var msg = "";
                                    if(undefined != json.sucids && json.sucids != ""){
                                        msg = "交易信息 编号：" + json.sucids + "取消物流面单打印信息成功；";
                                    }
                                    if(json.retmsg != ""){
                                        if(msg == ""){
                                            msg = json.retmsg;
                                        }else{
                                            msg += "<br>" + json.retmsg;
                                        }
                                    }
                                    if(msg != ""){
                                        $.messager.alert("提醒",msg);
                                        $("#ebshop-datagrid-ebtrageListPage").datagrid('reload');
                                    }
                                },
                                error:function(){
                                    loaded();
                                    $.messager.alert("提醒","取消物流面单出错！");
                                }
                            });
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/ebtrade/sendTradeConfirmBtn.do">
                {
                    id:'sendTradeConfirm',
                    name:'交易订单发货',
                    iconCls:'button-view',
                    handler:function(){
                        var rows = $("#ebshop-datagrid-ebtrageListPage").datagrid('getChecked');
                        if(rows.length == 0){
                            $.messager.alert("提醒","请勾选交易信息!");
                            return false;
                        }
                        var idStr = "",uncancalids = "";
                        for(var i=0;i<rows.length;i++){
                            if(rows[i].state != "0" && rows[i].state != "1"){
                                var id = rows[i].id;
                                if(idStr == ""){
                                    idStr = id;
                                }else{
                                    idStr += "," + id;
                                }
                            }else{
                                if(uncancalids == ""){
                                    uncancalids = rows[i].id;
                                }else{
                                    uncancalids += "," + rows[i].id;;
                                }
                                var index = $("#ebshop-datagrid-ebtrageListPage").datagrid('getRowIndex',rows[i]);
                                $("#ebshop-datagrid-ebtrageListPage").datagrid('uncheckRow',index);
                            }
                        }
                        if(uncancalids != ""){
                            $.messager.alert("提醒","交易信息 编号：" + uncancalids + "未获取物流单号，不允许交易订单发货；");
                        }
                        if(idStr != ""){
                            loading("发货中..");
                            $.ajax({
                                url :'ebtrade/sendTradeConfirm.do',
                                type:'post',
                                dataType:'json',
                                data:{idStr:idStr},
                                success:function(json){
                                    loaded();
                                    var msg = "";
                                    if(json.sucids != ""){
                                        msg = "交易信息 编号：" + json.sucids + "交易订单发货成功；";
                                    }
                                    if(json.retmsg != ""){
                                        if(msg == ""){
                                            msg = json.retmsg;
                                        }else{
                                            msg += "<br>" + json.retmsg;
                                        }
                                    }
                                    if(msg != ""){
                                        $.messager.alert("提醒",msg);
                                        $("#ebshop-datagrid-ebtrageListPage").datagrid('reload');
                                    }
                                },
                                error:function(){
                                    loaded();
                                    $.messager.alert("提醒","交易订单发货出错！");
                                }
                            });
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/ebtrade/addSaleOutBtn.do">
                {
                    id:'addSaleOut',
                    name:'生成销售发货单',
                    iconCls:'button-add',
                    handler:function(){
                        var rows = $("#ebshop-datagrid-ebtrageListPage").datagrid('getChecked');
                        if(rows.length == 0){
                            $.messager.alert("提醒","请勾选交易信息!");
                            return false;
                        }
                        var idStr = "",uncancalids = "";
                        for(var i=0;i<rows.length;i++){
                            if(rows[i].state != '0' && rows[i].state != '9' && rows[i].state != "6"){
                                if(idStr == ""){
                                    idStr = rows[i].id;
                                }else{
                                    idStr += "," + rows[i].id;
                                }
                            }else{
                                if(uncancalids == ""){
                                    uncancalids = rows[i].id;
                                }else{
                                    uncancalids += "," + rows[i].id;;
                                }
                                var index = $("#ebshop-datagrid-ebtrageListPage").datagrid('getRowIndex',rows[i]);
                                $("#ebshop-datagrid-ebtrageListPage").datagrid('uncheckRow',index);
                            }
                        }
                        if(uncancalids != ""){
                            $.messager.alert("提醒","交易信息 编号：" + uncancalids + "未审核、作废或已生成销售发货单状态不允许生成销售发货单!");
                        }
                        if(idStr != ""){
                            loading("生成中..");
                            $.ajax({
                                url :'ebtrade/addSaleOut.do',
                                type:'post',
                                dataType:'json',
                                data:{idStr:idStr},
                                success:function(json){
                                    loaded();
                                    $.messager.alert("提醒",json.msg);
                                    $("#ebshop-datagrid-ebtrageListPage").datagrid('reload');
                                },
                                error:function(){
                                    loaded();
                                    $.messager.alert("提醒","生成销售发货单出错！");
                                }
                            });
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/ebtrade/addReissueBillBtn.do">
                {
                    id:'addReissueBill',
                    name:'补发单',
                    iconCls:'button-add',
                    handler:function(){
                        var rows = $("#ebshop-datagrid-ebtrageListPage").datagrid('getChecked');
                        if(rows.length != 1){
                            $.messager.alert("提醒","请勾选一条交易信息!");
                            $("#ebshop-datagrid-ebtrageListPage").datagrid('clearChecked');
                            return false;
                        }
                        if(rows[0].isreissue == "1"){
                            $.messager.confirm("提醒","已生成过补发单，是否在生成?",function(r){
                                if(r){
                                    top.addOrUpdateTab('ebtrade/showEbtradePage.do?type=reissue&id='+rows[0].id, '补发单【新增】');
                                }
                            });
                        }else{
                            top.addOrUpdateTab('ebtrade/showEbtradePage.do?type=reissue&id='+rows[0].id, '补发单【新增】');
                        }
                    }
                },
                </security:authorize>
                <security:authorize url="/ebtrade/ebtrageSplitPage.do">
                {
                    id:'button-split',
                    name:'拆单',
                    iconCls:'button-add',
                    handler:function(){
                        var rows = $("#ebshop-datagrid-ebtrageListPage").datagrid('getChecked');
                        if(rows.length != 1){
                            $.messager.alert("提醒","请勾选一条交易信息!");
                            $("#ebshop-datagrid-ebtrageListPage").datagrid('clearChecked');
                            return false;
                        }
                        if(rows[0].state != "0"){
                            $.messager.alert("提醒","不为未审核状态的单据不允许拆分!");
                            $("#ebshop-datagrid-ebtrageListPage").datagrid('clearChecked');
                            return false;
                        }
                        $("#ebshop-ebtrade-dialog-split").dialog({
                            title:'电商交易订单拆分',
                            fit:true,
                            closed:false,
                            cache:false,
                            modal: true,
                            href:'ebtrade/showEbtradeSplitPage.do?id='+ rows[0].id,
                            buttons:[
                                {
                                    text:'确定',
                                    handler: function(){
                                        //判断单据数量与拆分数量是否相等，若相等则不能拆分
                                        var rows = $("#ebshop-datagrid-ebtradeSplitPage").datagrid('getChecked');
                                        if(rows.length == 0){
                                            $.messager.alert("提醒","请勾选要拆分的单据!");
                                            return false;
                                        }
                                        var outeriids = "";
                                        var loadrows = $("#ebshop-datagrid-ebtradeSplitPage").datagrid('getRows');
                                        if(rows.length == loadrows.length){
                                            for(var i=0;i<rows.length;i++){
                                                if(rows[i].num == rows[i].splitnum){
                                                    var rowindex = $("#ebshop-datagrid-ebtradeSplitPage").datagrid('getRowIndex',rows[i]);
                                                    $("#ebshop-datagrid-ebtradeSplitPage").datagrid('uncheckRow',rowindex);
                                                    if(outeriids == ""){
                                                        outeriids = rows[i].outerIid;
                                                    }else{
                                                        outeriids += "," + rows[i].outerIid;
                                                    }
                                                }
                                            }
                                        }
                                        if(outeriids != ""){
                                            $.messager.alert("提醒","商家外部编码:"+outeriids+"的购买数量与拆分数量相等的单据,不能拆分!");
                                            return false;
                                        }
                                        $.messager.confirm('确认','是否根据选中行拆分当前电商交易订单，生成新的单据?',function(r){
                                            if(r){
                                                $("#ebshop-form-ebtradeSplitPage").submit();
                                            }
                                        });
                                    }
                                }
                            ]
                        });
                    }
                },
                </security:authorize>
                <security:authorize url="/ebtrade/ebtrageSplitPage.do">
                {
                    id:'button-merge',
                    name:'合单',
                    iconCls:'button-add',
                    handler:function(){
                        var rows = $("#ebshop-datagrid-ebtrageListPage").datagrid('getChecked');
                        if(rows.length <= 1){
                            $.messager.alert("提醒","请勾选多条要合并的交易信息!");
                            $("#ebshop-datagrid-ebtrageListPage").datagrid('clearChecked');
                            return false;
                        }
                        var unstateids = "",mergepre = "",unsameids = "";
                        for(var i=0;i<rows.length;i++){
                            //判断单据是否为未审核状态，只允许未审核状态合并单据
                            if(rows[i].state != "0"){
                                if(unstateids == ""){
                                    unstateids = rows[i].id;
                                }else{
                                    unstateids += "," + rows[i].id;
                                }
                                $("#ebshop-datagrid-ebtrageListPage").datagrid('clearChecked');
//                                var index = $("#ebshop-datagrid-ebtrageListPage").datagrid('getRowIndex',rows[i]);
//                                $("#ebshop-datagrid-ebtrageListPage").datagrid('uncheckRow',index);
                            }else{
                                //判断选中合并单据的收货人、电话、收获地址是否一致，判断店铺（卖家昵称）是否一致，不一致需与客户确定，否者不允许合并单据
                                if("" == mergepre){
                                    mergepre = rows[i].mergepre;
                                }else if(mergepre != rows[i].mergepre){
                                    if(unsameids == ""){
                                        unsameids = rows[i].id;
                                    }else{
                                        unsameids += "," + rows[i].id;
                                    }
                                    $("#ebshop-datagrid-ebtrageListPage").datagrid('clearChecked');
//                                    var index = $("#ebshop-datagrid-ebtrageListPage").datagrid('getRowIndex',rows[i]);
//                                    $("#ebshop-datagrid-ebtrageListPage").datagrid('uncheckRow',index);
                                }
                            }
                        }
                        rows = $("#ebshop-datagrid-ebtrageListPage").datagrid('getChecked');
                        var sameSN = true;
                        var ids = "",sellerNick = "";
                        for(var i=0;i<rows.length;i++){
                            if("" == sellerNick){
                                sellerNick = rows[i].sellerNick;
                            }else if(sellerNick != rows[i].sellerNick){
                                sameSN = false;
                            }
                            if(ids == ""){
                                ids = rows[i].id;
                            }else{
                                ids += "," + rows[i].id;
                            }
                        }
                        var msg = "";
                        if("" != unstateids){
                            msg = "交易信息编码："+unstateids+"，不为未审核状态的单据不允许合单!";
                        }
                        if("" != unsameids){
                            if("" == msg){
                                msg = "单据的收货人、电话、收获地址不一致不允许合单!";
                            }else{
                                msg += "<br>" + "单据的收货人、电话、收获地址不一致不允许合单!";
                            }
                        }
                        if("" != msg){
                            $.messager.alert("提醒",msg);
                            return false;
                        }
                        if("" != ids){
                            if(!sameSN){
                                $.messager.confirm("提醒","店铺（卖家昵称）不一致，是否合并单据?",function(r){
                                    if(r){
                                        doEbtradeMerge(ids);
                                    }
                                });
                            }else{
                                doEbtradeMerge(ids);
                            }
                        }
                    }
                }
                </security:authorize>
            ],
            model: 'bill',
            type: 'list',
            tname: 't_eb_trade'
        });

        var eborderListJson = $("#ebshop-datagrid-ebtrageListPage").createGridColumnLoad({
            frozenCol : [[
                {field:'ck',checkbox:true},
                {field:'id',title:'编码',width:130, align: 'left',sortable:true,
                    formatter:function(value,row,index){
                        var msg = "";
                        if(row.hasBuyerMessage == "1"){
                            msg = "买家留言："+row.buyerMessage;
                        }
                        if(null != row.sellerMemo && "" != row.sellerMemo){
                            if(msg == ""){
                                msg = "卖家备注："+row.sellerMemo;
                            }else{
                                msg += "<br>" + "有卖家备注："+row.sellerMemo;
                            }
                        }
                        if(msg != ""){
                            return '<a href="javaScript:void(0);" title="'+msg+'" class="easyui-tooltip">'+value+'</a>';
                        }else{
                            return value;
                        }
                    }
                }
            ]],
            commonCol : [[
                {field:'tid',title:'订单交易号',width:110, align: 'left',sortable:true},
                {field:'etype',title:'电商类型',width:60, align: 'left',sortable:true,
                    formatter:function(value,row,index){
                        return row.etypename;
                    }
                },
                {field:'sellerNick',title:'卖家昵称',width:80, align: 'left',sortable:true,
                    formatter:function(value,row,index){
                        return row.sellerNickname;
                    }
                },
                {field:'buyerNick',title:'买家昵称',width:80, align: 'left',sortable:true},
                {field:'receiverName',title:'收货人',width:50, align: 'left'},
                {field:'address',title:'收货人地址',width:160, align: 'left'},
                {field:'receiverZip',title:'邮编',width:50, align: 'left'},
                {field:'contactway',title:'联系方式',width:80, align: 'left'},
                {field:'postFee',title:'邮费',width:40, align: 'right'},
                {field:'shippingType',title:'物流方式',width:60, align: 'left',
                    formatter:function(value,row,index){
                        return row.shippingTypename;
                    }
                },
                {field:'totalFee',title:'商品总金额',width:70, align: 'right'},
                {field:'discountFee',title:'优惠金额',width:60, align: 'right'},
                {field:'payment',title:'实付金额',width:60, align: 'right'},
                {field:'adjustFee',title:'卖家手工调整金额',width:100, align: 'right'},
                {field:'creditCardFee',title:'信用卡支付金额',width:100, align: 'right'},
                {field:'hasBuyerMessage',title:'是否有买家留言',width:90, align: 'left',
                    formatter:function(value,row,index){
                        if("0" == value){
                            return "没有";
                        }else if("1" == value){
                            return "有";
                        }
                    }
                },
                {field:'buyerMessage',title:'买家留言',width:60, align: 'left'},
                {field:'sellerMemo',title:'卖家备注',width:60, align: 'left'},
                {field:'status',title:'交易状态',width:70, align: 'left',
                    formatter:function(value,row,index){
                        return row.statusname;
                    }
                },
                {field:'state',title:'本地订单状态',width:80, align: 'left',
                    formatter:function(value,row,index){
                        return row.statename;
                    }
                },
                {field:'created',title:'创建时间',width:60, align: 'left'},
                {field:'modified',title:'修改时间',width:60, align: 'left',hidden:true},
                {field:'payTime',title:'付款时间',width:60, align: 'left'},
                {field:'audituserid',title:'审核人',width:60, align: 'left',
                    formatter:function(value,row,index){
                        return row.auditusername;
                    }
                },
                {field:'audittime',title:'审核时间',width:60, align: 'left'},
                {field:'invoiceKind',title:'发票类型',width:70, align: 'left',
                    formatter:function(value,row,index){
                        return row.invoiceKindname;
                    }
                },
                {field:'invoiceName',title:'发票抬头',width:60, align: 'left'},
                {field:'isSplit',title:'是否拆单',width:60, align: 'left',
                    formatter:function(value,row,index){
                        if("0" == value){
                            return "否";
                        }else if("1" == value){
                            return "是";
                        }
                    }
                },
                {field:'ismerge',title:'是否已合单',width:60, align: 'left',
                    formatter:function(value,row,index){
                        if("0" == value){
                            return "否";
                        }else if("1" == value){
                            return "是";
                        }
                    }
                },
                {field:'orderid',title:'订单编号',width:60, align: 'left'},
                {field:'logisticsCode',title:'物流公司',width:60, align: 'left',
                    formatter:function(value,row,index){
                        return row.logisticsCompany;
                    }
                },
                {field:'waybillCode',title:'物流运单号',width:70, align: 'left'},
                {field:'isreissue',title:'是否已补发',width:60, align: 'left',
                    formatter:function(value,row,index){
                        if("0" == value){
                            return "否";
                        }else if("1" == value){
                            return "是";
                        }
                    }
                }
            ]]
        });

        $("#ebshop-datagrid-ebtrageListPage").datagrid({
            authority:eborderListJson,
            frozenColumns:eborderListJson.frozen,
            columns:eborderListJson.common,
            fit:true,
            method:'post',
            rownumbers:true,
            pagination:true,
            singleSelect:false,
            checkOnSelect:true,
            selectOnCheck:true,
            pageSize:100,
            showFooter:true,
            sortName:'tid asc,ismain desc,id',
            url: 'ebtrade/getEbTradeList.do',
            queryParams:initQueryJSON,
            toolbar:'#ebshop-toobar-ebtrageListPage',
            rowStyler: function(index,row){
                if (row.etype == "BF"){
                    return 'background-color:#EEF9C4;';
                }
                if(row.isSplit == "1"){
                    if(row.ismain == "1"){
                        return "background-color:#CCFFE5;color:#CB00FB;";
                    }else{
                        return 'background-color:#CCFFE5;';
                    }
                }
            },
            onDblClickRow:function(index, data){
                if(data.state != "0" && data.state != "1"){
                    top.addOrUpdateTab('ebtrade/showEbtradePage.do?type=view&id='+data.id, '交易信息查看');
                }else{
                    top.addOrUpdateTab('ebtrade/showEbtradePage.do?type=edit&id='+data.id, '交易信息修改');
                }
            }
        }).datagrid("columnMoving");

        $("#ebshop-queay-ebtrageListPage").click(function(){
            var queryJSON = $("#ebshop-queryForm-ebtrageListPage").serializeJSON();
            $("#ebshop-datagrid-ebtrageListPage").datagrid('load', queryJSON);
        });
        $("#ebshop-resetQueay-ebtrageListPage").click(function(){
            $("#ebshop-queryForm-ebtrageListPage").form("reset");
            var queryJSON = $("#ebshop-queryForm-ebtrageListPage").serializeJSON();
            $("#ebshop-datagrid-ebtrageListPage").datagrid('load', queryJSON);
        });

    });
</script>
</body>
</html>
