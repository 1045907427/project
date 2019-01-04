<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>销售订货单商品详细信息新增页面</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
        <form id="sales-form-orderGoodsDetailAddPage">
            <table cellpadding="5" cellspacing="2">
                <tr>
                    <td class="len80">选择商品：</td>
                    <td>
                        <input id="sales-goodsId-orderGoodsDetailAddPage" class="len150" />
                        <input type="hidden" name="goodsid" />
                        <input type="hidden" name="brandid" />
                        <input id="sales-usablenum-orderGoodsDetailAddPage" type="hidden" name="usablenum" value="0"/>
                        <input id="sales-shopid-orderGoodsDetailAddPage" type="hidden" name="shopid"/>
                    </td>
                    <td id="sales-loading-orderGoodsDetailAddPage" colspan="2"></td>
                </tr>
                <tr>
                    <td>数量：</td>
                    <td><input class="easyui-validatebox len150 goodsNum" value="0" name="unitnum" onfocus="this.select();frm_focus('unitnum');" onblur="frm_blur('unitnum');" data-options="required:true,validType:'intOrFloatNum[${decimallen}]'" readonly="readonly" /></td>
                    <td>辅数量：</td>
                    <td><input name="auxnum" class="easyui-validatebox goodsNum" value="0" style="width:60px;" onfocus="this.select();frm_focus('auxnum');" onblur="frm_blur('auxnum');" data-options="validType:'integer'" readonly="readonly" /><span id="sales-auxunitname-orderGoodsDetailAddPage"></span>
                        <input name="overnum" class="easyui-validatebox goodsNum" value="0" style="width:60px;" onfocus="this.select();frm_focus('overnum');" onblur="frm_blur('overnum');" data-options="validType:'intOrFloatNum[${decimallen}]'" readonly="readonly" /><span id="sales-unitname-orderGoodsDetailAddPage"></span>
                    </td>
                </tr>
                <tr>
                    <td>含税单价：</td>
                    <td><input type="text" class="len150 easyui-validatebox <c:if test="${colMap.taxprice == null }">readonly</c:if>" name="taxprice" onfocus="this.select();frm_focus('taxprice');" onblur="frm_blur('taxprice');" id="sales-taxprice-orderGoodsDetailAddPage" required="required" validType="intOrFloat" <c:if test="${colMap.taxprice == null }">readonly="readonly"</c:if> /> </td>
                    <td>含税金额：</td>
                    <td><input type="text" class="len150 easyui-validatebox <c:if test="${colMap.taxamount == null }">readonly </c:if>" id="sales-taxamount-orderGoodsDetailAddPage" name="taxamount" validType="intOrFloat" required="required" <c:if test="${colMap.taxamount == null }">readonly="readonly"</c:if> /></td>
                </tr>
                <tr>
                    <td>含税箱价：</td>
                    <td><input type="text" class="len150 easyui-validatebox <c:if test="${colMap.taxprice == null }">readonly</c:if>" name="boxprice" onfocus="this.select();frm_focus('boxprice');" onblur="frm_blur('boxprice');" id="sales-boxprice-orderGoodsDetailAddPage" required="required" validType="intOrFloat" <c:if test="${colMap.taxprice == null }">readonly="readonly"</c:if> /> </td>
                    <td>箱装量：</td>
                    <td><input type="text" name="boxnum" id="sales-boxnum-orderGoodsDetailAddPage" type="text" class="len150 readonly" readonly="readonly" /></td>
                </tr>
                <tr>
                    <td>未税单价：</td>
                    <td><input type="text" class="len150 easyui-validatebox <c:if test="${colMap.notaxprice == null }">readonly </c:if>" name="notaxprice" id="sales-notaxprice-orderGoodsDetailAddPage" required="required" validType="intOrFloat" <c:if test="${colMap.notaxprice == null }">readonly="readonly"</c:if>/> </td>
                    <td>未税金额：</td>
                    <td><input type="text" class="len150 easyui-validatebox <c:if test="${colMap.notaxamount == null }">readonly </c:if>" id="sales-notaxamount-orderGoodsDetailAddPage" name="notaxamount" required="required" <c:if test="${colMap.notaxamount == null }">readonly="readonly"</c:if>  validType="intOrFloat"/></td>
                </tr>
                <tr>
                    <td>税种：</td>
                    <td><input class="len150 readonly" readonly="readonly" name="taxtypename" /><input type="hidden" name="taxtype" /> </td>
                    <td>税额：</td>
                    <td><input class="len150 readonly no_input easyui-numberbox" id="sales-tax-orderGoodsDetailAddPage" readonly="readonly" name="tax" data-options="precision:6,groupSeparator:','" /></td>
                </tr>
                <tr>
                    <td>单位：</td>
                    <td>
                        主：<input name="unitname" type="text" class="readonly2" style="width: 40px;" readonly="readonly" /><input type="hidden" name="unitid" />
                        辅：<input name="auxunitname" type="text" class="readonly2" style="width: 40px;" readonly="readonly" /><input type="hidden" name="auxunitid" /></td>
                    <td>条形码：</td>
                    <td><input class="len150 readonly" readonly="readonly" name="barcode" /></td>
                </tr>
                <tr>
                    <td>批次号：</td>
                    <td>
                        <input id="sales-batchno-orderGoodsDetailAddPage" type="text" class="len150 "  name="batchno"/>
                        <input id="sales-summarybatchid-orderGoodsDetailAddPage" type="hidden" name="summarybatchid" />
                    </td>
                    <td>所属仓库：</td>
                    <td>
                        <input id="sales-storagelocationname-orderGoodsDetailAddPage" type="hidden"/>
                        <input id="sales-storageid-orderGoodsDetailAddPage" type="text" disabled="disabled" name="storageid" />
                        <input id="sales-storagename-orderGoodsDetailAddPage" type="hidden" class="len150 readonly" readonly="readonly" name="storagename" />
                        <input id="sales-storagelocationid-orderGoodsDetailAddPage" type="hidden" name="storagelocationid" />
                    </td>
                </tr>
                <tr style="display: none">
                    <td>生产日期：</td>
                    <td><input id="sales-produceddate-orderGoodsDetailAddPage" type="text" class="len150 readonly" readonly="readonly" name="produceddate" /> </td>
                    <td>截止日期：</td>
                    <td><input id="sales-deadline-orderGoodsDetailAddPage" type="text" class="len150 readonly no_input"  readonly="readonly" name="deadline"/></td>
                </tr>
                <tr style="display: none">
                    <td>重量：</td>
                    <td><input id="sales-totalboxweight-orderGoodsDetailAddPage" name="totalboxweight" type="text" class="len130 readonly" readonly="readonly" /><span>千克</span></td>
                    <td>体积：</td>
                    <td><input id="sales-totalboxvolume-orderGoodsDetailAddPage" name="totalboxvolume" type="text" class="len120 readonly no_input"  readonly="readonly"/><span>立方米</span></td>
                </tr>
                <tr>
                    <td>商品类型：</td>
                    <td>
                        <select id="sales-deliverytype-orderGoodsDetailAddPage" name="deliverytype" style="width: 150px;">
                            <option value="0" selected="selected">正常商品</option>
                            <option value="1">赠品</option>
                        </select>
                    </td>
                    <td>备注：</td>
                    <td><input type="text" style="width: 150px;" name="remark" id="remark"
                               onfocus="frm_focus('remark');" onblur="frm_blur('remark');" /></td>
                </tr>
            </table>
            <input id="sales-total-orderGoodsDetailAddPage" type="hidden" name="total" />
            <input type="hidden" name="sunitname" />
            <input type="hidden" name="oldprice" />
            <input type="hidden" name="buyprice" />
            <input type="hidden" id="back-taxprice" />
            <input type="hidden" name="lowestsaleprice" />
            <input type="hidden" name="basesaleprice" />
            <input type="hidden" name="isdiscount" value="0"/>
            <input type="hidden" id="sales-groupid-orderGoodsDetailAddPage" name="groupid" />
            <input type="hidden" id="sales-totalbox-orderGoodsDetailAddPage" name="totalbox" />
            <input type="hidden" id="sales-hasFree-orderGoodsDetailAddPage" />
            <input type="hidden" id="sales-saveDetailFlag-orderGoodsDetailAddPage" />
        </form>
    </div>
    <div data-options="region:'south',border:false">
        <div class="buttonDetailBG" style="height:30px;text-align:right;">
            快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
            <input type="button" name="savegoon" id="sales-savegoon-orderGoodsDetailAddPage" value="继续添加"/>
        </div>
    </div>
</div>

<script type="text/javascript">

    var deliverydate = "${date }";
    var presentByZero = '${presentByZero}';
    var storageid = $("#sales-storageid-orderGoodsAddPage").widget("getValue");
    var customerid = $("#sales-customer-orderGoodsAddPage").customerWidget("getValue");
    var storageParam = null;
    if(storageid!=null && storageid!=""){
        storageParam = [{field:'id',op:'equal',value:storageid}];
    }
    $(function(){
        $("#sales-goodsId-orderGoodsDetailAddPage").goodsWidget({
            name:'t_sales_order_detail',
            col:'goodsid',
            width:150,
            required:true,
            storageid:storageid,
            customerid:customerid,
            distribution: true,
            canBuySale:"2",
            onSelect: function(data){
                order_taxpricechange = "0";
                //正常商品
                if(data.ptype == undefined || data.ptype=='0' || data.ptype==null){
                    $("#sales-shopid-orderGoodsDetailAddPage").val(data.shopid);
                    $("input[name=goodsid]").val(data.id);
                    $("input[name=brandid]").val(data.brand);
                    $("input[name=barcode]").val(data.barcode);
                    $("input[name=boxnum]").val(data.boxnum);
                    $("input[name=lowestsaleprice]").val(data.lowestsaleprice);
                    $("input[name=basesaleprice]").val(data.basesaleprice);
                    $("#sales-loading-orderGoodsDetailAddPage").addClass("img-loading");
                    var deliverytype =  $("#sales-deliverytype-orderGoodsDetailAddPage").val();
                    var date = $("input[name='saleorder.businessdate']").val();
                    var isbatch = data.isbatch;
                    $.ajax({
                        url:'sales/getGoodsDetail.do',
                        dataType:'json',
                        type:'post',
                        async:false,
                        data:{id:data.id,cid:'${customerId}',data:date,storageid:storageid},
                        success:function(json){
                            if(isbatch=="1"){
                                $("#sales-loading-orderGoodsDetailAddPage").removeClass("img-loading").html("(批次商品)商品编码：<font color='green'>"+json.detail.goodsInfo.id+"</font>&nbsp;可用量(共)：<font color='green'>"+ json.total + json.unitname +"</font>");
                            }else{
                                $("#sales-loading-orderGoodsDetailAddPage").removeClass("img-loading").html("商品编码：<font color='green'>"+json.detail.goodsInfo.id+"</font>&nbsp;可用量：<font color='green'>"+ json.total + json.unitname +"</font>");
                            }
                            $("input[name=unitid]").val(json.detail.goodsInfo.mainunit);
                            $("input[name=unitname]").val(json.detail.goodsInfo.mainunitName);
                            $("#sales-unitname-orderGoodsDetailAddPage").text(json.detail.goodsInfo.mainunitName);
                            $("input[name=auxunitid]").val(json.detail.auxunitid);
                            $("input[name=buyprice]").val(json.detail.goodsInfo.highestbuyprice);
                            $("input[name=auxunitname]").val(json.detail.auxunitname);
                            $("#sales-auxunitname-orderGoodsDetailAddPage").text(json.detail.auxunitname);
                            $("input[name=taxtype]").val(json.detail.taxtype);
                            $("input[name=taxtypename]").val(json.detail.taxtypename);
                            $("input[name=taxprice]").val(json.detail.taxprice);

                            //赠品取0单价
                            if(deliverytype=='1' && presentByZero==0){
                                $("#sales-taxprice-orderGoodsDetailAddPage").val(0);
                                $("#sales-boxprice-orderGoodsDetailAddPage").val(0);
                                $("#sales-taxamount-orderGoodsDetailAddPage").val(0);
                                $("#sales-notaxprice-orderGoodsDetailAddPage").val(0);
                                $("#sales-notaxamount-orderGoodsDetailAddPage").val(0);
                                $("#sales-tax-orderGoodsDetailAddPage").numberbox('setValue',0);
                                $("input[name=oldprice]").val(0);
                                order_taxpricechange = "1";
                            }else{
                                $("#sales-taxprice-orderGoodsDetailAddPage").val(json.detail.taxprice);
                                $("#sales-boxprice-orderGoodsDetailAddPage").val(json.detail.boxprice);
                                $("#sales-taxamount-orderGoodsDetailAddPage").val(json.detail.taxamount);
                                $("#sales-notaxprice-orderGoodsDetailAddPage").val(json.detail.notaxprice);
                                $("#sales-notaxamount-orderGoodsDetailAddPage").val(json.detail.notaxamount);
                                $("#sales-tax-orderGoodsDetailAddPage").numberbox('setValue',json.detail.tax);
                                $("input[name=oldprice]").val(json.detail.taxprice);
                            }
                            $("input[name=remark]").val(json.detail.remark);
                            $("input[name=total]").val(json.total);
                            $("input[name=sunitname]").val(json.unitname);
                            if(json.total!=null && json.total!='' && json.total!='未知'){
                                $("input[name=usablenum]").val(json.total);
                            }else{
                                $("input[name=usablenum]").val(0);
                            }
                            $(".goodsNum").val(0).removeAttr("readonly");
                            $("input[name=unitnum]").focus();
                        }
                    });
                    $("#sales-batchno-orderGoodsDetailAddPage").widget("clear");
                    $("#sales-summarybatchid-orderGoodsDetailAddPage").val("");
                    $("#sales-storageid-orderGoodsDetailAddPage").widget("clear");
                    $("#sales-storagename-orderGoodsDetailAddPage").val("");
                    $("#sales-storagelocationname-orderGoodsDetailAddPage").val("");
                    $("#sales-storagelocationid-orderGoodsDetailAddPage").val("");
                    $("#sales-produceddate-orderGoodsDetailAddPage").val("");
                    $("#sales-deadline-orderGoodsDetailAddPage").val("");
                    ;
                    //判断商品是否批次管理 1是
                    if(data.isbatch=='1'){
                        var param = null;
                        if(storageid!=null && storageid!=""){
                            param = [{field:'goodsid',op:'equal',value:data.id},
                                {field:'storageid',op:'equal',value:storageid}];
                        }else{
                            param = [{field:'goodsid',op:'equal',value:data.id}];
                        }
                        $("#sales-storageid-orderGoodsDetailAddPage").widget("readonly",true);
                        $("#sales-batchno-orderGoodsDetailAddPage").widget({
                            referwid:'RL_T_STORAGE_BATCH_LIST',
                            param:param,
                            width:150,
                            disabled:false,
                            singleSelect:true,
                            onSelect: function(obj){
                                $("#sales-summarybatchid-orderGoodsDetailAddPage").val(obj.id);
                                $("#sales-storageid-orderGoodsDetailAddPage").widget("setValue",obj.storageid);
                                $("#sales-storagename-orderGoodsDetailAddPage").val(obj.storagename);
                                $("#sales-storagelocationname-orderGoodsDetailAddPage").val(obj.storagelocationname);
                                $("#sales-storagelocationid-orderGoodsDetailAddPage").val(obj.storagelocationid);
                                $("#sales-produceddate-orderGoodsDetailAddPage").val(obj.produceddate);
                                $("#sales-deadline-orderGoodsDetailAddPage").val(obj.deadline);
                                $("#sales-loading-orderGoodsDetailAddPage").removeClass("img-loading").html("商品编码：<font color='green'>"+obj.goodsid+"</font>&nbsp;可用量：<font color='green'>"+ obj.usablenum + obj.unitname +"</font>");
                                if(obj.usablenum!=null && obj.usablenum!=''){
                                    $("input[name=usablenum]").val(obj.usablenum);
                                    $("input[name=total]").val(obj.usablenum);
                                }else{
                                    $("input[name=usablenum]").val(0);
                                }
                            },
                            onClear:function(){
                                $("#sales-summarybatchid-orderGoodsDetailAddPage").val("");
                                $("#sales-storageid-orderGoodsDetailAddPage").widget("clear");
                                $("#sales-storagename-orderGoodsDetailAddPage").val("");
                                $("#sales-storagelocationname-orderGoodsDetailAddPage").val("");
                                $("#sales-storagelocationid-orderGoodsDetailAddPage").val("");
                                $("#sales-produceddate-orderGoodsDetailAddPage").val("");
                                $("#sales-deadline-orderGoodsDetailAddPage").val("");
                            }
                        });
                        $("#sales-batchno-orderGoodsDetailAddPage").widget("enable");
                    }else{
                        $("#sales-storageid-orderGoodsDetailAddPage").widget("readonly",false);
                        $("#sales-batchno-orderGoodsDetailAddPage").widget("disable");
                    }
                }else if(data.ptype=='1' || data.ptype=='2'){
                    //买赠
                    showPromotionPage(data.id,data.ptype);
                }
            },
            onClear:function(){
                order_taxpricechange = "0";
                $("#sales-storageid-orderGoodsDetailAddPage").widget("readonly",false);
                $(".goodsNum").val("0").attr("readonly", "readonly");
                $("#sales-batchno-orderGoodsDetailAddPage").widget("clear");
                $("#sales-storageid-orderGoodsDetailAddPage").widget("clear");
                $("#sales-storagename-orderGoodsDetailAddPage").val("");
                $("#sales-summarybatchid-orderGoodsDetailAddPage").val("");
                $("#sales-storagelocationname-orderGoodsDetailAddPage").val("");
                $("#sales-storagelocationid-orderGoodsDetailAddPage").val("");
                $("#sales-produceddate-orderGoodsDetailAddPage").val("");
                $("#sales-deadline-orderGoodsDetailAddPage").val("");
            }
        });
        $("#sales-batchno-orderGoodsDetailAddPage").widget({
            referwid:'RL_T_STORAGE_BATCH_LIST',
            width:150,
            singleSelect:true,
            disabled:true
        });
        $("#sales-storageid-orderGoodsDetailAddPage").widget({
            referwid:'RL_T_BASE_STORAGE_INFO',
            width:150,
            param:storageParam,
            singleSelect:true,
            onSelect:function(data){
                $("#sales-storagename-orderGoodsDetailAddPage").val(data.name);
            },
            onClear:function(){
                $("#sales-storagename-orderGoodsDetailAddPage").val("");
            }
        });
        $("#sales-savegoon-orderGoodsDetailAddPage").click(function(){
            var hasFree = $("#sales-hasFree-orderGoodsDetailAddPage").val();
            var orderid = $("#sales-id-orderGoodsAddPage").val();
            if(hasFree=="1"){
                $("#sales-saveDetailFlag-orderGoodsDetailAddPage").val("1");
                $.messager.confirm("提醒","该商品符合满赠条件，是否赠送?",function(r){
                    if(r){
                        $("#sales-saveDetailFlag-orderGoodsDetailAddPage").val("2");
                        var customerid = $("#sales-customer-orderGoodsAddPage").customerWidget("getValue");
                        var goodsid = $("#sales-goodsId-orderGoodsDetailAddPage").goodsWidget("getValue");
                        var num  = $("input[name=unitnum]").val();
                        showFullFreePage(orderid,customerid,goodsid,num,"add");
                    }else{
                        $("#sales-saveDetailFlag-orderGoodsDetailAddPage").val("2");
                        var groupid = $("#sales-groupid-orderGoodsDetailAddPage").val();
                        if(groupid!=null && groupid!=""){
                            addSaveDetail(true,groupid);
                        }else{
                            addSaveDetail(true);
                        }

                    }
                });
            }else{
                var groupid = $("#sales-groupid-orderGoodsDetailAddPage").val();
                if(groupid!=null && groupid!=""){
                    addSaveDetail(true,groupid);
                }else{
                    addSaveDetail(true);
                }
            }
        });
        $("input[name=unitnum]").change(function(){
            unitnumChange(1);
        });
        $("input[name=auxnum]").change(function(){
            unitnumChange(2);
        });
        $("input[name=overnum]").change(function(){
            unitnumChange(2);
        });
        $("#sales-taxprice-orderGoodsDetailAddPage").change(function(){
            priceChange("1", '#sales-taxprice-orderGoodsDetailAddPage');
        });
        $("#sales-boxprice-orderGoodsDetailAddPage").change(function(){
            boxpriceChange();
        });
        $("#sales-notaxprice-orderGoodsDetailAddPage").change(function(){
            priceChange("2", '#sales-notaxprice-orderGoodsDetailAddPage');
        });
        $("#sales-taxamount-orderGoodsDetailAddPage").change(function(){
            amountChange("1", '#sales-taxamount-orderGoodsDetailAddPage');
        });
        $("#sales-notaxamount-orderGoodsDetailAddPage").change(function(){
            amountChange("2", '#sales-notaxamount-orderGoodsDetailAddPage');
        });

        //商品类型变化
        $("#sales-deliverytype-orderGoodsDetailAddPage").change(function(){
            var a =  $("#sales-deliverytype-orderGoodsDetailAddPage").val();
            if(a == 1){
                $("#remark").val("赠品");
                if(presentByZero == 0){
                    $("#sales-taxprice-orderGoodsDetailAddPage").val(0);
                    priceChange("1", '#sales-taxprice-orderGoodsDetailAddPage');
                }
            }else{
                $("#remark").val("");
                var goodsid =  $("#sales-goodsId-orderGoodsDetailAddPage").goodsWidget("getValue");
                var customerid = $("#sales-customer-orderGoodsAddPage").customerWidget("getValue");
                var date =  $("#sales-businessdate-orderGoodsAddPage").val();
                $.ajax({
                    url: 'sales/getGoodsDetail.do',
                    dataType: 'json',
                    type: 'post',
                    data:'id='+ goodsid +'&cid='+customerid+'&date='+ date,
                    success: function (json) {
                        price = json.detail.taxprice;
                        $("#sales-taxprice-orderGoodsDetailAddPage").val(price);
                        priceChange("1", '#sales-taxprice-orderGoodsDetailAddPage');
                    }
                });
            }
        });

    });
    function unitnumChange(type){ //数量改变方法
        var orderid = $("#sales-id-orderGoodsAddPage").val();
        var $this = $("#sales-unitnum-orderGoodsDetailAddPage").css({'background':'url(image/loading.gif) right top no-repeat'});
        var goodsId = $("input[name=goodsid]").val();
        var unitnum = $("input[name=unitnum]").val();
        var auxnum = $("input[name=auxnum]").val();
        var overnum = $("input[name=overnum]").val();
        var customerId = "${customerId}";
        var date = $("input[name='saleorder.businessdate']").val();
        var price = $("#sales-taxprice-orderGoodsDetailAddPage").val();
        var url = "";
        var data = "";

        if(type == 1){
            url = "sales/getAuxUnitNumAndPrice.do"
            data = {id:goodsId,unitnum:unitnum,cid:customerId,date:date,free:"1",orderid:orderid,price:price,taxpricechange:order_taxpricechange};
        }
        else if(type == 2){
            url = "sales/getUnitNumAndPrice.do"
            data = {id:goodsId,auxnum:auxnum,overnum:overnum,cid:customerId,date:date,free:"1",orderid:orderid,price:price,taxpricechange:order_taxpricechange};
        }
        $.ajax({
            url:url,
            dataType:'json',
            type:'post',
            async:false,
            data:data,
            success:function(json){
                $("#sales-taxamount-orderGoodsDetailAddPage").val(json.taxamount);
                $("#sales-notaxamount-orderGoodsDetailAddPage").val(json.notaxamount);
                $("#sales-taxprice-orderGoodsDetailAddPage").val(json.taxprice);
                $("#sales-boxprice-orderGoodsDetailAddPage").val(json.boxprice);
                $("#sales-notaxprice-orderGoodsDetailAddPage").val(json.notaxprice);
                $("#sales-totalboxweight-orderGoodsDetailAddPage").val(json.totalboxweight);
                $("#sales-totalboxvolume-orderGoodsDetailAddPage").val(json.totalboxvolume);
                $("#sales-tax-orderGoodsDetailAddPage").numberbox('setValue',json.tax);
                $("input[name=auxnum]").val(json.auxnum);
                $("input[name=overnum]").val(json.overnum);
                $("input[name=unitnum]").val(json.unitnum);
                var remark = $("input[name=remark]").val();
                if(json.remark != null && json.remark != ""){
                    if(remark == ""){
                        $("input[name=remark]").val(json.remark);
                    }else if(remark != json.remark){
                        $("input[name=remark]").val(json.remark +" "+remark);
                    }
                }

                if(json.groupid!=null &&json.groupid!=""){
                    $("#sales-groupid-orderGoodsDetailAddPage").val(json.groupid);
                }else{
                    $("#sales-groupid-orderGoodsDetailAddPage").val("");
                }
                $("input[name=oldprice]").val(json.taxprice);
                $("input[name=totalbox]").val(json.totalbox);
                //判断是否符合满赠条件
                if(json.hasFree=='1'){
                    $("#sales-hasFree-orderGoodsDetailAddPage").val("1");
                }else{
                    $("#sales-hasFree-orderGoodsDetailAddPage").val("0");
                }

                $this.css({'background':''});
            }
        });
    }
    function priceChange(type, id){ //1含税单价或2未税单价改变计算对应数据
        var $this = $(id).css({'background':'url(image/loading.gif) right top no-repeat'});
        var price = $(id).val();
        var goodsId = $("input[name=goodsid]").val();
        var taxtype = $("input[name=taxtype]").val();
        var unitnum = $("input[name=unitnum]").val();
        var auxnum = $("input[name=auxnum]").val();
        $.ajax({
            url:'sales/getAmountChanger.do',
            dataType:'json',
            async:false,
            type:'post',
            data:{type:type,price:price,taxtype:taxtype,unitnum:unitnum,id:goodsId},
            success:function(json){
                $("#sales-taxprice-orderGoodsDetailAddPage").val(json.taxPrice);
                $("#sales-boxprice-orderGoodsDetailAddPage").val(json.boxPrice);
                $("#sales-taxamount-orderGoodsDetailAddPage").val(json.taxAmount);
                $("#sales-notaxprice-orderGoodsDetailAddPage").val(json.noTaxPrice);
                $("#sales-notaxamount-orderGoodsDetailAddPage").val(json.noTaxAmount);
                $("#sales-tax-orderGoodsDetailAddPage").numberbox('setValue',json.tax);
                $this.css({'background':''});
                var groupid = $("#sales-groupid-orderGoodsDetailAddPage").val();
                var remark = $("input[name=remark]").val();
                if(groupid != null && groupid != ""){
                    $("#sales-groupid-orderGoodsDetailAddPage").val("");
                    $("input[name=remark]").val(remark);
                }
                order_taxpricechange = "1";
            }
        });
    }
    function amountChange(type, id){ //1含税金额或2未税金额改变计算对应数据
        var $this = $(id).css({'background':'url(image/loading.gif) right top no-repeat'});
        var amount = $(id).val();
        var goodsId = $("input[name=goodsid]").val();
        var taxtype = $("input[name=taxtype]").val();
        var unitnum = $("input[name=unitnum]").val();
        var auxnum = $("input[name=auxnum]").val();
        $.ajax({
            url:'sales/getAmountChangeByType.do',
            dataType:'json',
            async:false,
            type:'post',
            data:{type:type,amount:amount,taxtype:taxtype,unitnum:unitnum,id:goodsId},
            success:function(json){
                $("#sales-taxprice-orderGoodsDetailAddPage").val(json.taxPrice);
                $("#sales-boxprice-orderGoodsDetailAddPage").val(json.boxPrice);
                $("#sales-taxamount-orderGoodsDetailAddPage").val(json.taxAmount);
                $("#sales-notaxprice-orderGoodsDetailAddPage").val(json.noTaxPrice);
                $("#sales-notaxamount-orderGoodsDetailAddPage").val(json.noTaxAmount);
                $("#sales-tax-orderGoodsDetailAddPage").numberbox('setValue',json.tax);
                $this.css({'background':''});
            }
        });
    }
    function boxpriceChange(){
        var $this = $("#sales-boxprice-orderGoodsDetailAddPage").css({'background':'url(image/loading.gif) right top no-repeat'});
        var price = $("#sales-boxprice-orderGoodsDetailAddPage").val();
        var goodsId = $("input[name=goodsid]").val();
        var taxtype = $("input[name=taxtype]").val();
        var unitnum = $("input[name=unitnum]").val();
        var auxnum = $("input[name=auxnum]").val();
        $.ajax({
            url:'sales/getAmountChangerByBoxprice.do',
            dataType:'json',
            async:false,
            type:'post',
            data:{boxprice:price,taxtype:taxtype,unitnum:unitnum,id:goodsId},
            success:function(json){
                $("#sales-taxprice-orderGoodsDetailAddPage").val(json.taxPrice);
                $("#sales-boxprice-orderGoodsDetailAddPage").val(json.boxPrice);
                $("#sales-taxamount-orderGoodsDetailAddPage").val(json.taxAmount);
                $("#sales-notaxprice-orderGoodsDetailAddPage").val(json.noTaxPrice);
                $("#sales-notaxamount-orderGoodsDetailAddPage").val(json.noTaxAmount);
                $("#sales-tax-orderGoodsDetailAddPage").numberbox('setValue',json.tax);
                $this.css({'background':''});
            }
        });
    }
</script>
</body>
</html>
