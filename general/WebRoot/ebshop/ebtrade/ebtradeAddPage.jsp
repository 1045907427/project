<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>补发单页面</title>
</head>
<body>
<form id="ebshop-form-ebtradeAddPage" action="ebtrade/addEbtrade.do" method="post">
    <div class="easyui-accordion" data-options="border:false,multiple:true">
        <div id="base" title="" data-options="collapsed:false,collapsible:false">
            <table style="border-collapse:collapse;" border="0" cellpadding="3px" cellspacing="3px">
                <tr>
                    <td class="len110 left">编号：</td>
                    <td class="len165">
                        <input type="text" id="ebshop-id-ebtradeAddPage" class="len150 no_input" readonly="readonly" name="ebTrade.id" />
                        <input type="hidden" name="ebTrade.reissueid" value="${ebTrade.id}">
                    </td>
                    <td class="len110 left">电商类型：</td>
                    <td class="len165">
                        <select name="ebTrade.etype" style="width: 150px;" class="no_input">
                            <option value="BF">补发单</option>
                        </select>
                    </td>
                    <td class="len110 left">本地订单状态：</td>
                    <td>
                        <select id="ebshop-state-ebtradeAddPage" name="ebTrade.state" class="len150">
                            <option value="0">未审核</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="len110 left">收货人：</td>
                    <td><input type="text" id="ebshop-receiverName-ebtradeAddPage" class="len150" name="ebTrade.receiverName" value="${ebTrade.receiverName }" /></td>
                    <td class="len110 left">收货人国籍：</td>
                    <td><input type="text" id="ebshop-receiverCountry-ebtradeAddPage" class="len150" name="ebTrade.receiverCountry" value="${ebTrade.receiverCountry }" /></td>
                    <td class="len110 left">省名称：</td>
                    <td><input type="text" id="ebshop-receiverState-ebtradeAddPage" class="len150" name="ebTrade.receiverState" value="${ebTrade.receiverState }" /></td>
                </tr>
                <tr>
                    <td class="len110 left">市名称：</td>
                    <td><input type="text" id="ebshop-receiverCity-ebtradeAddPage" class="len150" name="ebTrade.receiverCity" value="${ebTrade.receiverCity }" /></td>
                    <td class="len110 left">区名称：</td>
                    <td><input type="text" id="ebshop-receiverDistrict-ebtradeAddPage" class="len150" name="ebTrade.receiverDistrict" value="${ebTrade.receiverDistrict }" /></td>
                    <td class="len110 left">街道\镇名称：</td>
                    <td><input type="text" id="ebshop-receiverTown-ebtradeAddPage" class="len150" name="ebTrade.receiverTown" value="${ebTrade.receiverTown }" /></td>
                </tr>
                <tr>
                    <td class="len110 left">详细地址：</td>
                    <td><input type="text" id="ebshop-receiverAddress-ebtradeAddPage" class="len150" name="ebTrade.receiverAddress" value="${ebTrade.receiverAddress }" /></td>
                    <td class="len110 left">邮编：</td>
                    <td><input type="text" id="ebshop-receiverZip-ebtradeAddPage" class="len150" name="ebTrade.receiverZip" value="${ebTrade.receiverZip }" /></td>
                    <td class="len110 left">手机号码：</td>
                    <td><input type="text" id="ebshop-receiverMobile-ebtradeAddPage" class="len150" name="ebTrade.receiverMobile" value="${ebTrade.receiverMobile }" /></td>
                </tr>
                <tr>
                    <td class="len110 left">电话号码：</td>
                    <td><input type="text" id="ebshop-receiverPhone-ebtradeAddPage" class="len150" name="ebTrade.receiverPhone" value="${ebTrade.receiverPhone }" /></td>
                    <td class="len110 left">物流运单号：</td>
                    <td><input type="text" id="ebshop-waybillCode-ebtradeAddPage" class="len150 no_input" readonly="readonly" name="ebTrade.waybillCode"/></td>
                    <td class="len110 left">大笔头信息：</td>
                    <td><input type="text" id="ebshop-shortAddress-ebtradeAddPage" readonly="readonly" class="len150 no_input" name="ebTrade.shortAddress"/></td>
                </tr>
            </table>
        </div>
        <div id="more" title="更多">
            <table style="border-collapse:collapse;" border="0" cellpadding="3px" cellspacing="3px">
                <tr>
                    <td class="len110 left">订单交易号：</td>
                    <td><input type="text" id="ebshop-tid-ebtradeAddPage" class="len150 no_input" readonly="readonly" name="ebTrade.tid" value="${ebTrade.tid }" /></td>
                    <td class="len110 left">卖家昵称：</td>
                    <td><input type="text" id="ebshop-sellerNick-ebtradeAddPage" class="no_input" readonly="readonly" name="ebTrade.sellerNick" value="${ebTrade.sellerNick }" /></td>
                    <td class="len110 left">买家昵称：</td>
                    <td><input type="text" id="ebshop-buyerNick-ebtradeAddPage" class="len150 no_input" readonly="readonly" name="ebTrade.buyerNick" value="${ebTrade.buyerNick }" /></td>
                </tr>
                <tr>
                    <td class="len110 left">物流公司：</td>
                    <td>
                        <select name="ebTrade.logisticsCode" style="width: 150px;">
                            <option></option>
                            <c:forEach items="${logisticsList }" var="list">
                                <option value="${list.code }" <c:if test="${ebTrade.logisticsCode == list.code}">selected="selected"</c:if>>${list.codename }</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td class="len110 left">物流方式：</td>
                    <td>
                        <select id="ebshop-shippingType-ebtradeAddPage" name="ebTrade.shippingType" class="len150">
                            <option></option>
                            <c:forEach items="${shippingtypeList }" var="list">
                                <option value="${list.code }" <c:if test="${ebTrade.shippingType == list.code}">selected="selected" </c:if>>${list.codename }</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td class="len110 left">配送时间：</td>
                    <td class="len165"><input id="ebshop-appointdate-ebtradeAddPage" type="text" name="ebTrade.appointdate" <c:if test="${ebTrade.appointdate != '25' && ebTrade.appointdate != '26'}">disabled="disabled"</c:if> class="Wdate len150" onclick="WdatePicker({'dateFmt':'yyyy年M月d日'})" /> </td>
                </tr>
                <tr>
                    <td class="len110 left">付款方式：</td>
                    <td>
                        <select id="ebshop-ebpaytype-ebtradeAddPage" class="len150 combobox">
                            <option></option>
                            <c:forEach items="${ebpaytypeList }" var="list">
                                <option value="${list.code }" <c:if test="${ebTrade.ebpaytype == list.code}">selected="selected" </c:if>>${list.codename }</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td class="len110 left">保价金额：</td>
                    <td><input type="text" id="ebshop-insure-ebtradeAddPage" class="len150 easyui-numberbox" data-options="min:0,precision:2" name="ebTrade.insure" value="${ebTrade.insure }" /></td>
                    <td class="len110 left">商品总金额：</td>
                    <td class="len165"><input type="text" id="ebshop-totalFee-ebtradeAddPage" readonly="readonly" class="len150 no_input easyui-numberbox" data-options="min:0,precision:2" name="ebTrade.totalFee"/></td>
                </tr>
                <tr>
                    <td class="len110 left">优惠金额：</td>
                    <td class="len165"><input type="text" id="ebshop-discountFee-ebtradeAddPage" class="len150 easyui-numberbox" data-options="min:0,precision:2" name="ebTrade.discountFee"/></td>
                    <td class="len110 left">实付金额：</td>
                    <td class="len165"><input type="text" id="ebshop-payment-ebtradeAddPage" class="len150 easyui-numberbox" data-options="min:0,precision:2" name="ebTrade.payment"/></td>
                    <td class="len110 left">卖家手工调整金额：</td>
                    <td><input type="text" id="ebshop-adjustFee-ebtradeAddPage" class="len150 easyui-numberbox" data-options="min:0,precision:2" name="ebTrade.adjustFee"/></td>
                </tr>
                <tr>
                    <td class="len110 left">信用卡支付金额：</td>
                    <td><input type="text" id="ebshop-creditCardFee-ebtradeAddPage" class="len150 easyui-numberbox" data-options="min:0,precision:2" name="ebTrade.creditCardFee"/></td>
                    <td class="len110 left">邮费：</td>
                    <td><input type="text" id="ebshop-postFee-ebtradeAddPage" class="len150 easyui-numberbox" data-options="min:0,precision:2" name="ebTrade.postFee"/></td>
                    <td class="len110 left">是否拆单：</td>
                    <td>
                        <select id="ebshop-isSplit-ebtradeAddPage" name="ebTrade.isSplit" class="len150">
                            <option value="0">否</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="len110 left">包裹重量g：</td>
                    <td class="len165"><input type="text" id="ebshop-weight-ebtradeAddPage" class="len150 easyui-numberbox" data-options="min:0,precision:4" name="ebTrade.weight"/></td>
                    <td class="len110 left">包裹体积cm³：</td>
                    <td class="len165"><input type="text" id="ebshop-volume-ebtradeAddPage" class="len150 easyui-numberbox" data-options="min:0,precision:4" name="ebTrade.volume"/></td>
                    <td class="len110 left">是否已合单：</td>
                    <td>
                        <select id="ebshop-ismerge-ebtradeAddPage" disabled="disabled" class="len150">
                            <option value="0">否</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td class="len110 left">是否有买家留言：</td>
                    <td>
                        <select id="ebshop-hasBuyerMessage-ebtradeAddPage" name="ebTrade.hasBuyerMessage" class="len150">
                            <option></option>
                            <option value="0" selected="selected">没有</option>
                            <option value="1">有</option>
                        </select>
                    </td>
                    <td class="len110 left">买家留言：</td>
                    <td colspan="3"><input type="text" id="ebshop-buyerMessage-ebtradeAddPage" style="width: 437px;" name="ebTrade.buyerMessage"/></td>
                </tr>
                <tr>
                    <td class="len110 left">销售订单编号：</td>
                    <td><input type="text" id="ebshop-orderid-ebtradeAddPage" readonly="readonly" class="len150 no_input" name="ebTrade.orderid" value="${ebTrade.orderid }" /></td>
                    <td class="len110 left">卖家备注：</td>
                    <td colspan="3"><input type="text" id="ebshop-sellerMemo-ebtradeAddPage" style="width: 437px;" name="ebTrade.sellerMemo"/></td>
                </tr>
                <tr>
                    <td class="len110 left">发票类型：</td>
                    <td>
                        <select id="ebshop-invoiceKind-ebtradeAddPage" name="ebTrade.invoiceKind" class="len150">
                            <option></option>
                            <option value="0" selected="selected">不需要发票</option>
                            <option value="1">电子发票</option>
                            <option value="2">纸质发票</option>
                        </select>
                    </td>
                    <td class="len110 left">发票抬头：</td>
                    <td><input type="text" id="ebshop-invoiceName-ebtradeAddPage" readonly="readonly" class="len150 no_input" name="ebTrade.invoiceName"/></td>
                </tr>
            </table>
        </div>
        <div title="" data-options="collapsed:false,collapsible:false">
            <ul class="tags">
                <li id="firstli" class="selectTag">
                    <a href="javascript:void(0)">商品明细</a>
                </li>
            </ul>
            <div class="tagsDiv">
                <div class="tagsDiv_item">
                    <input type="hidden" name="goodsjson" id="ebshop-goodsJson-ebtradeAddPage" />
                    <table id="ebshop-datagrid-ebtradeAddPage"></table>
                </div>
            </div>
        </div>
    </div>
</form>
<div class="easyui-menu" id="ebshop-contextMenu-ebtradeAddPage" style="display: none;">
    <div id="ebshop-addRow-ebtradeAddPage" data-options="iconCls:'button-add'">添加</div>
    <div id="ebshop-editRow-ebtradeAddPage" data-options="iconCls:'button-edit'">编辑</div>
    <div id="ebshop-removeRow-ebtradeAddPage" data-options="iconCls:'button-delete'">删除</div>
</div>
<div id="ebshop-dialog-ebtradeAddPage" class="easyui-dialog" data-options="closed:true"></div>
<script type="text/javascript">
    var $dgDetailList = $("#ebshop-datagrid-ebtradeAddPage");

    $(function(){

        loadwidgetdown();

        if("${ebTrade.state}" == "0" || "${ebTrade.state}" == "1"){
            $("#ebshop-buttons-ebtradePage").buttonWidget('enableButton','button-save');
        }else{
            $("#ebshop-buttons-ebtradePage").buttonWidget('disableButton','button-save');
        }

        $(".tags").find("li").click(function(){
            var height = getHeight();
            var index = $(this).index();
            $(".tags li").removeClass("selectTag").eq(index).addClass("selectTag");
            $(".tagsDiv .tagsDiv_item").hide().eq(index).show();
            if(index == 0){
                if(!$dgDetailList.hasClass("create-datagrid")){
                    $dgDetailList.datagrid({ //销售商品明细信息编辑
                        authority:detailTableColJson,
                        columns: detailTableColJson.common,
                        frozenColumns: detailTableColJson.frozen,
                        border: true,
                        height:height,
                        rownumbers: true,
                        showFooter: true,
                        striped:true,
                        singleSelect: true,
                        checkOnSelect:true,
                        selectOnCheck:true,
                        onRowContextMenu: function(e, rowIndex, rowData){
                            e.preventDefault();
                            $(this).datagrid('selectRow', rowIndex);
                            $("#ebshop-contextMenu-ebtradeAddPage").menu('show', {
                                left:e.pageX,
                                top:e.pageY
                            });
                        },
                        onSortColumn:function(sort, order){
                            var rows = $(this).datagrid("getRows");
                            var dataArr = [];
                            for(var i=0;i<rows.length;i++){
                                if(rows[i].goodsid!=null && rows[i].goodsid!=""){
                                    dataArr.push(rows[i]);
                                }
                            }
                            dataArr.sort(function(a,b){
                                if($.isNumeric(a[sort])){
                                    if(order=="asc"){
                                        return Number(a[sort])>Number(b[sort])?1:-1
                                    }else{
                                        return Number(a[sort])<Number(b[sort])?1:-1
                                    }
                                }else{
                                    if(order=="asc"){
                                        return a[sort]>b[sort]?1:-1
                                    }else{
                                        return a[sort]<b[sort]?1:-1
                                    }
                                }
                            });
                            $(this).datagrid("loadData",dataArr);
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
                            countTotalAdd();
                        },
                        onDblClickRow: function(rowIndex, rowData){
                            $(this).datagrid('clearSelections').datagrid('selectRow',rowIndex);
                            if(rowData.goodsid == undefined){
                                beginAddDetail();
                            }else{
                                beginEditDetail(rowData);
                            }
                        }
                    }).datagrid("loadData", {'total':11,'rows':[{},{},{},{},{},{},{},{},{},{},{} ]}).datagrid('columnMoving');
                    $dgDetailList.addClass("create-datagrid");
                }
            }
        });
        setTimeout(function(){
            $("#firstli").click();
        },50);

        //添加
        $("#ebshop-addRow-ebtradeAddPage").die("click").live("click",function(){
            beginAddDetail();
        });
        //编辑
        $("#ebshop-editRow-ebtradeAddPage").die("click").live("click",function(){
            var row = $dgDetailList.datagrid('getSelected');
            if(row.goodsid != undefined){
                beginEditDetail(row);
            }
        });
        //删除
        $("#ebshop-removeRow-ebtradeAddPage").die("click").live("click",function(){
            removeDetail();
        });

        $("#ebshop-form-ebtradeAddPage").form({
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
                    $("#sales-backid-orderPage").val(json.backid);
                    $.messager.alert("提醒","保存成功。");
                    if(json.type == "add"){
                        $("#ebshop-buttons-ebtradePage").buttonWidget("addNewDataId", json.backid);
                    }
                    $("#ebshop-panel-ebtradePage").panel('refresh', 'ebtrade/showEbtradeEditPage.do?id='+json.backid);//'sales/orderViewPage.do?id='+ json.backid);
                }
                else{
                    $.messager.alert("提醒","保存失败");
                }
            }
        });
    });
</script>
</body>
</html>
