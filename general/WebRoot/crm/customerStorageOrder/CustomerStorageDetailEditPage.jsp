<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>客户库存商品详细信息新增页面</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
        <form id="crm-form-customerStorageDetailAddPage">
            <table cellpadding="5" cellspacing="2">
                <tr>
                    <td class="len80">选择商品：</td>
                    <td>
                        <input id="crm-goodsId-customerStorageDetailAddPage" class="len150" />
                        <input type="hidden" name="goodsid" />
                        <input type="hidden" name="brandid" />
                        <input id="crm-usablenum-customerStorageDetailAddPage" type="hidden" name="usablenum" value="0"/>
                    </td>
                    <td id="crm-loading-customerStorageDetailAddPage" colspan="2"></td>
                </tr>
                <tr>
                    <td>数量：</td>
                    <td><input class="easyui-validatebox len150 formaterNum" value="0" name="unitnum" onfocus="this.select();frm_focus('unitnum');" onblur="frm_blur('unitnum');" /></td>
                    <td>辅数量：</td>
                    <td><input name="auxnum" onfocus="this.select();frm_focus('auxnum');" onblur="frm_blur('auxnum');" class="easyui-validatebox formaterNum" value="0" style="width:60px;" /><span id="crm-auxunitname-customerStorageDetailAddPage"></span>
                        <input name="overnum" onfocus="this.select();frm_focus('overnum');" onblur="frm_blur('overnum');" class="easyui-validatebox formaterNum" value="0" style="width:60px;"  /><span id="crm-unitname-customerStorageDetailAddPage"></span>
                    </td>
                </tr>
                <tr>
                    <td>零售单价：</td>
                    <td><input type="text" class="len150 easyui-validatebox" name="taxprice" onfocus="this.select();frm_focus('taxprice');" onblur="frm_blur('taxprice');" id="crm-taxprice-customerStorageDetailAddPage" required="required" validType="intOrFloat" /> </td>
                    <td>零售金额：</td>
                    <td><input type="text" class="len150 easyui-validatebox" id="crm-taxamount-customerStorageDetailAddPage" name="taxamount" onfocus="this.select();frm_focus('taxamount');" onblur="frm_blur('taxamount');" validType="intOrFloat" /></td>
                </tr>
                <tr style="display: none">
                    <td>含税箱价：</td>
                    <td><input type="text" class="len150 easyui-validatebox readonly" name="boxprice" onfocus="this.select();frm_focus('boxprice');" onblur="frm_blur('boxprice');" id="crm-boxprice-customerStorageDetailAddPage" required="required" validType="intOrFloat" readonly="readonly" /> </td>
                    <td>箱装量：</td>
                    <td><input type="text" name="boxnum" id="crm-boxnum-customerStorageDetailAddPage" type="text" class="len150 readonly"/></td>
                </tr>
                <tr>
                    <td>零售未税价：</td>
                    <td><input type="text" class="len150 easyui-validatebox " name="notaxprice" onfocus="this.select();frm_focus('notaxprice');" onblur="frm_blur('notaxprice');" id="crm-notaxprice-customerStorageDetailAddPage" required="required" validType="intOrFloat" /> </td>
                    <td>零售未税金额：</td>
                    <td><input type="text" class="len150 easyui-validatebox " id="crm-notaxamount-customerStorageDetailAddPage" name="notaxamount" onfocus="this.select();frm_focus('notaxamount');" onblur="frm_blur('notaxamount');" required="required" validType="intOrFloat"/></td>
                </tr>
                <tr>
                    <td>成本单价：</td>
                    <td><input type="text" class="len150 easyui-validatebox" name="costprice" onfocus="this.select();frm_focus('costprice');" onblur="frm_blur('costprice');" id="crm-costprice-customerStorageDetailAddPage" required="required" validType="intOrFloat" />
                    </td>
                    <td>成本未税金额：</td>
                    <td><input type="text" class="len150 easyui-validatebox " id="crm-costnotaxamount-customerStorageDetailAddPage" name="costnotaxamount" onfocus="this.select();frm_focus('costnotaxamount');" onblur="frm_blur('costnotaxamount');" required="required" validType="intOrFloat"/></td>
                </tr>
                <tr>
                    <td>成本金额：</td>
                    <td><input type="text" class="len150 easyui-validatebox " name="costtaxamount" onfocus="this.select();frm_focus('costtaxamount');" onblur="frm_blur('costtaxamount');" id="crm-costtaxamount-customerStorageDetailAddPage" required="required" validType="intOrFloat" /> </td>
                    <td>条形码：</td>
                    <td><input class="len150 readonly" readonly="readonly" name="barcode" /></td>
                </tr>
                <tr>
                    <td>税种：</td>
                    <td><input class="len150 readonly" readonly="readonly" name="taxtypename" /><input type="hidden" name="taxtype" /> </td>
                    <td>税额：</td>
                    <td><input class="len150 readonly no_input easyui-numberbox" id="crm-tax-customerStorageDetailAddPage" readonly="readonly" name="tax" data-options="precision:6,groupSeparator:','" /></td>
                </tr>
                <tr>
                    <td>单位：</td>
                    <td>
                        主：<input name="unitname" type="text" class="readonly2" style="width: 40px;" readonly="readonly" /><input type="hidden" name="unitid" />
                        辅：<input name="auxunitname" type="text" class="readonly2" style="width: 40px;" readonly="readonly" /><input type="hidden" name="auxunitid" /></td>
                    <td>备注：</td>
                    <td><input type="text" name="remark" onfocus="this.select();frm_focus('remark');" onblur="frm_blur('remark');" id="remark" /></td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'south',border:false">
        <div class="buttonDetailBG" style="height:30px;text-align:right;">
            快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
            <input type="button" name="savegoon" id="crm-save-customerStorageDetailAddPage" value="确定"/>
        </div>
    </div>
</div>

<script type="text/javascript">
    var customerid = $("#crm-customer-customerStorageOrderAddPage").customerWidget("getValue");
    $(function(){
        $("#crm-goodsId-customerStorageDetailAddPage").goodsWidget({
            name:'t_crm_customer_storage_detail',
            col:'goodsid',
            width:150,
            required:true,
            isPromotion:false,
            customerid:customerid,
            canBuySale:"2",
            onSelect: function(data){
                $("input[name=goodsid]").val(data.id);
                $("input[name=brandid]").val(data.brand);
                $("input[name=barcode]").val(data.barcode);
                $("input[name=boxnum]").val(data.boxnum);
                $("input[name=lowestsaleprice]").val(data.lowestsaleprice);
                $("input[name=basesaleprice]").val(data.basesaleprice);
                var date = $("input[name='customerStorageOrder.businessdate']").val();
                $("#crm-loading-customerStorageDetailAddPage").addClass("img-loading");
                $.ajax({
                    url:'sales/getGoodsDetail.do',
                    dataType:'json',
                    type:'post',
                    async:false,
                    data:{id:data.id,cid:customerid,date:date},
                    success:function(json){
                        $("#crm-loading-customerStorageDetailAddPage").removeClass("img-loading").html("商品编码：<font color='green'>"+json.detail.goodsInfo.id);
                        $("input[name=unitid]").val(json.detail.goodsInfo.mainunit);
                        $("input[name=unitname]").val(json.detail.goodsInfo.mainunitName);
                        $("#crm-unitname-customerStorageDetailAddPage").text(json.detail.goodsInfo.mainunitName);
                        $("input[name=auxunitid]").val(json.detail.auxunitid);
                        $("input[name=buyprice]").val(json.detail.goodsInfo.highestbuyprice);
                        $("input[name=auxunitname]").val(json.detail.auxunitname);
                        $("#crm-auxunitname-customerStorageDetailAddPage").text(json.detail.auxunitname);
                        $("input[name=taxtype]").val(json.detail.taxtype);
                        $("input[name=taxtypename]").val(json.detail.taxtypename);
                        $("input[name=taxprice]").val(json.detail.taxprice);
                        $("#crm-taxprice-customerStorageDetailAddPage").val(json.detail.taxprice);
                        $("#crm-boxprice-customerStorageDetailAddPage").val(json.detail.boxprice);
                        $("#crm-taxamount-customerStorageDetailAddPage").val(json.detail.taxamount);
                        $("#crm-notaxprice-customerStorageDetailAddPage").val(json.detail.notaxprice);
                        $("#crm-notaxamount-customerStorageDetailAddPage").val(json.detail.notaxamount);
                        $("#crm-costprice-customerStorageDetailAddPage").val(json.detail.goodsInfo.newstorageprice);

                        $("#crm-tax-customerStorageDetailAddPage").numberbox('setValue',json.detail.tax);
                        $("input[name=remark]").val(json.detail.remark);
                        $("input[name=total]").val(json.total);
                        $("input[name=sunitname]").val(json.unitname);
                        $("input[name=oldprice]").val(json.detail.taxprice);
                        if(json.total!=null && json.total!='' && json.total!='未知'){
                            $("input[name=usablenum]").val(json.total);
                        }else{
                            $("input[name=usablenum]").val(0);
                        }
                        if(json.total!=null && json.total!='' && json.total!='未知'){
                            $("input[name=usablenum]").val(json.total);
                        }else{
                            $("input[name=usablenum]").val(0);
                        }
                        $(".goodsNum").val(0).removeAttr("readonly");
                        $("input[name=unitnum]").focus();
                        $("input[name=unitnum]").select();
                    }
                });
            },
            onClear:function(){
            }
        });

        $("#crm-save-customerStorageDetailAddPage").click(function(){
            editSaveDetail();
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
        $("#crm-taxprice-customerStorageDetailAddPage").change(function(){
            priceChange("1", '#crm-taxprice-customerStorageDetailAddPage');
        });
        $("#crm-costprice-customerStorageDetailAddPage").change(function(){
            costpriceChange("1", '#crm-costprice-customerStorageDetailAddPage');
        });
        $("#crm-costtaxamount-customerStorageDetailAddPage").change(function(){
            costAmountChange("1", '#crm-costtaxamount-customerStorageDetailAddPage');
        });
        $("#crm-costnotaxamount-customerStorageDetailAddPage").change(function(){
            costAmountChange("2", '#crm-costnotaxamount-customerStorageDetailAddPage');
        });
        $("#crm-boxprice-customerStorageDetailAddPage").change(function(){
            boxpriceChange();
        });
        $("#crm-notaxprice-customerStorageDetailAddPage").change(function(){
            priceChange("2", '#crm-notaxprice-customerStorageDetailAddPage');
        });
        $("#crm-taxamount-customerStorageDetailAddPage").change(function(){
            amountChange("1", '#crm-taxamount-customerStorageDetailAddPage');
        });
        $("#crm-notaxamount-customerStorageDetailAddPage").change(function(){
            amountChange("2", '#crm-notaxamount-customerStorageDetailAddPage');
        });

    });
    function unitnumChange(type){ //数量改变方法
        var orderid = $("#crm-id-customerStorageOrderAddPage").val();
        var $this = $("#crm-unitnum-customerStorageDetailAddPage").css({'background':'url(image/loading.gif) right top no-repeat'});
        var goodsId = $("input[name=goodsid]").val();
        var unitnum = $("input[name=unitnum]").val();
        var auxnum = $("input[name=auxnum]").val();
        var overnum = $("input[name=overnum]").val();
        var date = $("input[name='customerStorageOrder.businessdate']").val();
        var price = $("#crm-taxprice-customerStorageDetailAddPage").val();
        var url = "";
        var data = "";
        if(type == 1){
            url = "sales/getAuxUnitNumAndPrice.do";
            data = {id:goodsId,unitnum:unitnum,cid:customerid,date:date,free:"1",orderid:orderid,price:price};
        }
        else if(type == 2){
            url = "sales/getUnitNumAndPrice.do";
            data = {id:goodsId,auxnum:auxnum,overnum:overnum,cid:customerid,date:date,free:"1",orderid:orderid,price:price};
        }
        $.ajax({
            url:url,
            dataType:'json',
            type:'post',
            async:false,
            data:data,
            success:function(json){
                $("#crm-taxamount-customerStorageDetailAddPage").val(json.taxamount);
                $("#crm-notaxamount-customerStorageDetailAddPage").val(json.notaxamount);
                $("#crm-taxprice-customerStorageDetailAddPage").val(json.taxprice);
                $("#crm-boxprice-customerStorageDetailAddPage").val(json.boxprice);
                $("#crm-notaxprice-customerStorageDetailAddPage").val(json.notaxprice);
                $("#crm-totalboxweight-customerStorageDetailAddPage").val(json.totalboxweight);
                $("#crm-totalboxvolume-customerStorageDetailAddPage").val(json.totalboxvolume);
                $("#crm-tax-customerStorageDetailAddPage").numberbox('setValue',json.tax);
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
                }else{
                    $("input[name=remark]").val(json.remark);
                }
                $("input[name=oldprice]").val(json.taxprice);
                $("input[name=totalbox]").val(json.totalbox);
                var costprice =  $("#crm-costprice-customerStorageDetailAddPage").val();
                var costtaxamount = Number(costprice) * Number(unitnum);
                costtaxamount = costtaxamount.toFixed(6);
                $("#crm-costtaxamount-customerStorageDetailAddPage").val(parseFloat(costtaxamount));
                var costnotaxamount = Number(costtaxamount)/Number(1+json.rate/100);
                costnotaxamount = costnotaxamount.toFixed(6);
                $("#crm-costnotaxamount-customerStorageDetailAddPage").val(parseFloat(costnotaxamount));


            }
        });
    }

    function priceChange(type, id){ //1含税单价或2未税单价改变计算对应数据
        var $this = $(id).css({'background':'url(image/loading.gif) right top no-repeat'});
        var price = $(id).val();
        var goodsId = $("input[name=goodsid]").val();
        var taxtype = $("input[name=taxtype]").val();
        var unitnum = $("input[name=unitnum]").val();
        $.ajax({
            url:'sales/getAmountChanger.do',
            dataType:'json',
            async:false,
            type:'post',
            data:{type:type,price:price,taxtype:taxtype,unitnum:unitnum,id:goodsId},
            success:function(json){
                $("#crm-taxprice-customerStorageDetailAddPage").val(json.taxPrice);
                $("#crm-boxprice-customerStorageDetailAddPage").val(json.boxPrice);
                $("#crm-taxamount-customerStorageDetailAddPage").val(json.taxAmount);
                $("#crm-notaxprice-customerStorageDetailAddPage").val(json.noTaxPrice);
                $("#crm-notaxamount-customerStorageDetailAddPage").val(json.noTaxAmount);
                $("#crm-tax-customerStorageDetailAddPage").numberbox('setValue',json.tax);
                $this.css({'background':''});

            }
        });
    }
    function amountChange(type, id){ //1含税金额或2未税金额改变计算对应数据
        var $this = $(id).css({'background':'url(image/loading.gif) right top no-repeat'});
        var amount = $(id).val();
        var goodsId = $("input[name=goodsid]").val();
        var taxtype = $("input[name=taxtype]").val();
        var unitnum = $("input[name=unitnum]").val();
        $.ajax({
            url:'sales/getAmountChangeByType.do',
            dataType:'json',
            async:false,
            type:'post',
            data:{type:type,amount:amount,taxtype:taxtype,unitnum:unitnum,id:goodsId},
            success:function(json){
                $("#crm-taxprice-customerStorageDetailAddPage").val(json.taxPrice);
                $("#crm-boxprice-customerStorageDetailAddPage").val(json.boxPrice);
                $("#crm-taxamount-customerStorageDetailAddPage").val(json.taxAmount);
                $("#crm-notaxprice-customerStorageDetailAddPage").val(json.noTaxPrice);
                $("#crm-notaxamount-customerStorageDetailAddPage").val(json.noTaxAmount);
                $("#crm-tax-customerStorageDetailAddPage").numberbox('setValue',json.tax);
                $this.css({'background':''});
            }
        });
    }
    function costpriceChange(type, id){ //1含税单价或2未税单价改变计算对应数据
        var $this = $(id).css({'background':'url(image/loading.gif) right top no-repeat'});
        var price = $(id).val();
        var goodsId = $("input[name=goodsid]").val();
        var taxtype = $("input[name=taxtype]").val();
        var unitnum = $("input[name=unitnum]").val();
        $.ajax({
            url:'sales/getAmountChanger.do',
            dataType:'json',
            async:false,
            type:'post',
            data:{type:type,price:price,taxtype:taxtype,unitnum:unitnum,id:goodsId},
            success:function(json){
                $("#crm-costprice-customerStorageDetailAddPage").val(json.taxPrice);
                $("#crm-costtaxamount-customerStorageDetailAddPage").val(json.taxAmount);
                $("#crm-costnotaxamount-customerStorageDetailAddPage").val(json.noTaxAmount);
                $this.css({'background':''});

            }
        });
    }

    function costAmountChange(type,id) { //1含税金额2未税金额
        var amount = $(id).val();
        var goodsId = $("input[name=goodsid]").val();
        var unitnum = $("input[name=unitnum]").val();
        var price = Number(amount)/Number(unitnum);
        price = price.toFixed(6);
        $("#crm-costprice-customerStorageDetailAddPage").val(price);
        var date = $("input[name='customerStorageOrder.businessdate']").val();
        $.ajax({
            url:"sales/getAuxUnitNumAndPrice.do",
            dataType:'json',
            type:'post',
            async:false,
            data:{id:goodsId,unitnum:unitnum,cid:customerid,date:date,price:price},
            success:function(json) {
                if("1" == type){
                    var costnotaxamount = Number(amount)/Number(1+json.rate/100);
                    costnotaxamount = costnotaxamount.toFixed(6);
                    $("#crm-costnotaxamount-customerStorageDetailAddPage").val(costnotaxamount);
                }else{
                    var costtaxamount = Number(amount)*Number(1+json.rate/100);
                    costtaxamount = costtaxamount.toFixed(6);
                    $("#crm-costtaxamount-customerStorageDetailAddPage").val(costtaxamount);
                }

            }
        });

    }


</script>
</body>
</html>