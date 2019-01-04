<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>销售订单商品详细信息新增页面</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
        <form id="crm-form-terminalOrderDetailAddPage">
            <table cellpadding="5" cellspacing="2">
                <tr>
                    <td class="len80">选择商品：</td>
                    <td>
                        <input id="crm-goodsId-terminalOrderDetailAddPage" class="len150" />
                        <input type="hidden" name="goodsid" />
                        <input type="hidden" name="brandid" />
                        <input id="crm-usablenum-terminalOrderDetailAddPage" type="hidden" name="usablenum" value="0"/>
                    </td>
                    <td id="crm-loading-terminalOrderDetailAddPage" colspan="2"></td>
                </tr>
                <tr>
                    <td>数量：</td>
                    <td><input class="easyui-validatebox len150 goodsNum" value="0" name="unitnum"/></td>
                    <td>辅数量：</td>
                    <td><input name="auxnum" class="easyui-validatebox goodsNum" value="0" style="width:60px;" readonly="readonly" /><span id="crm-auxunitname-terminalOrderDetailAddPage"></span>
                        <input name="overnum" class="easyui-validatebox goodsNum" value="0" style="width:60px;" readonly="readonly" /><span id="crm-unitname-terminalOrderDetailAddPage"></span>
                    </td>
                </tr>
                <tr>
                    <td>含税单价：</td>
                    <td><input type="text" class="len150 easyui-validatebox" name="taxprice" id="crm-taxprice-terminalOrderDetailAddPage" required="required" validType="intOrFloat" /> </td>
                    <td>含税金额：</td>
                    <td><input type="text" class="len150 easyui-validatebox" id="crm-taxamount-terminalOrderDetailAddPage" name="taxamount" validType="intOrFloat"/></td>
                </tr>
                <tr>
                    <td>含税箱价：</td>
                    <td><input type="text" class="len150 easyui-validatebox" name="boxprice" id="crm-boxprice-terminalOrderDetailAddPage" required="required" validType="intOrFloat" /> </td>
                    <td>箱装量：</td>
                    <td><input type="text" name="boxnum" id="crm-boxnum-terminalOrderDetailAddPage" type="text" class="len150 readonly"/></td>
                </tr>
                <tr>
                    <td>未税单价：</td>
                    <td><input type="text" class="len150 easyui-validatebox" name="notaxprice" id="crm-notaxprice-terminalOrderDetailAddPage" required="required" validType="intOrFloat"/> </td>
                    <td>未税金额：</td>
                    <td><input type="text" class="len150 easyui-validatebox" id="crm-notaxamount-terminalOrderDetailAddPage" name="notaxamount" required="required"  validType="intOrFloat"/></td>
                </tr>
                <tr>
                    <td>税种：</td>
                    <td><input class="len150 readonly" readonly="readonly" name="taxtypename" /><input type="hidden" name="taxtype" /> </td>
                    <td>税额：</td>
                    <td><input class="len150 readonly no_input easyui-numberbox" id="crm-tax-terminalOrderDetailAddPage" readonly="readonly" name="tax" data-options="precision:6,groupSeparator:','" /></td>
                </tr>
                <tr>
                    <td>单位：</td>
                    <td>
                        主：<input name="unitname" type="text" class="readonly2" style="width: 40px;" readonly="readonly" /><input type="hidden" name="unitid" />
                        辅：<input name="auxunitname" type="text" class="readonly2" style="width: 40px;" readonly="readonly" /><input type="hidden" name="auxunitid" /></td>
                    <td>条形码：</td>
                    <td><input class="len150 readonly" readonly="readonly" name="barcode" /></td>
                </tr>
                <tr style="display: none">
                    <td>重量：</td>
                    <td><input id="crm-totalboxweight-terminalOrderDetailAddPage" name="totalboxweight" type="text" class="len130 readonly" readonly="readonly" /><span>千克</span></td>
                    <td>体积：</td>
                    <td><input id="crm-totalboxvolume-terminalOrderDetailAddPage" name="totalboxvolume" type="text" class="len120 readonly no_input"  readonly="readonly"/><span>立方米</span></td>
                </tr>
                <tr>
                    <td>备注：</td>
                    <td colspan="3"><input type="text" style="width: 385px;" name="remark" id="remark" /></td>
                </tr>
            </table>
            <input type="hidden" id="crm-groupid-terminalOrderDetailAddPage" name="groupid" />
        </form>
    </div>
    <div data-options="region:'south',border:false">
        <div class="buttonDetailBG" style="height:30px;text-align:right;">
            快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
            <input type="button" name="savegoon" id="crm-savegoon-terminalOrderDetailAddPage" value="继续添加"/>
        </div>
    </div>
</div>

<script type="text/javascript">
    var customerid = $("#crm-customer-terminalSalesOrderAddPage").customerWidget("getValue");
    $(function(){
        $("#crm-goodsId-terminalOrderDetailAddPage").goodsWidget({
            name:'t_crm_sales_order_detail',
            col:'goodsid',
            width:150,
            required:true,
            isPromotion:false,
            customerid:customerid,
            canBuySale:"2",
            onSelect: function(data){
                terminal_taxpricechange = "0";
                $("input[name=goodsid]").val(data.id);
                $("input[name=brandid]").val(data.brand);
                $("input[name=barcode]").val(data.barcode);
                $("input[name=boxnum]").val(data.boxnum);
                $("input[name=lowestsaleprice]").val(data.lowestsaleprice);
                $("input[name=basesaleprice]").val(data.basesaleprice);
                var date = $("input[name='CrmSalesOrder.businessdate']").val();
                $("#crm-loading-terminalOrderDetailAddPage").addClass("img-loading");
                $.ajax({
                    url:'sales/getGoodsDetail.do',
                    dataType:'json',
                    type:'post',
                    async:false,
                    data:{id:data.id,cid:customerid,data:date},
                    success:function(json){
                        $("#crm-loading-terminalOrderDetailAddPage").removeClass("img-loading").html("商品编码：<font color='green'>"+json.detail.goodsInfo.id);
                        $("input[name=unitid]").val(json.detail.goodsInfo.mainunit);
                        $("input[name=unitname]").val(json.detail.goodsInfo.mainunitName);
                        $("#crm-unitname-terminalOrderDetailAddPage").text(json.detail.goodsInfo.mainunitName);
                        $("input[name=auxunitid]").val(json.detail.auxunitid);
                        $("input[name=buyprice]").val(json.detail.goodsInfo.highestbuyprice);
                        $("input[name=auxunitname]").val(json.detail.auxunitname);
                        $("#crm-auxunitname-terminalOrderDetailAddPage").text(json.detail.auxunitname);
                        $("input[name=taxtype]").val(json.detail.taxtype);
                        $("input[name=taxtypename]").val(json.detail.taxtypename);
                        $("input[name=taxprice]").val(json.detail.taxprice);
                        $("#crm-taxprice-terminalOrderDetailAddPage").val(json.detail.taxprice);
                        $("#crm-boxprice-terminalOrderDetailAddPage").val(json.detail.boxprice);
                        $("#crm-taxamount-terminalOrderDetailAddPage").val(json.detail.taxamount);
                        $("#crm-notaxprice-terminalOrderDetailAddPage").val(json.detail.notaxprice);
                        $("#crm-notaxamount-terminalOrderDetailAddPage").val(json.detail.notaxamount);
                        $("#crm-tax-terminalOrderDetailAddPage").numberbox('setValue',json.detail.tax);
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
                terminal_taxpricechange = "0";
            }
        });

        $("#crm-savegoon-terminalOrderDetailAddPage").click(function(){
            addSaveDetail(true);
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
        $("#crm-taxprice-terminalOrderDetailAddPage").change(function(){
            priceChange("1", '#crm-taxprice-terminalOrderDetailAddPage');
        });
        $("#crm-boxprice-terminalOrderDetailAddPage").change(function(){
            boxpriceChange();
        });
        $("#crm-notaxprice-terminalOrderDetailAddPage").change(function(){
            priceChange("2", '#crm-notaxprice-terminalOrderDetailAddPage');
        });
        $("#crm-taxamount-terminalOrderDetailAddPage").change(function(){
            amountChange("1", '#crm-taxamount-terminalOrderDetailAddPage');
        });
        $("#crm-notaxamount-terminalOrderDetailAddPage").change(function(){
            amountChange("2", '#crm-notaxamount-terminalOrderDetailAddPage');
        });
        
    });
    function unitnumChange(type){ //数量改变方法
        var orderid = $("#crm-id-terminalSalesOrderAddPage").val();
        var $this = $("#crm-unitnum-terminalOrderDetailAddPage").css({'background':'url(image/loading.gif) right top no-repeat'});
        var goodsId = $("input[name=goodsid]").val();
        var unitnum = $("input[name=unitnum]").val();
        var auxnum = $("input[name=auxnum]").val();
        var overnum = $("input[name=overnum]").val();
        var customerId = "${customerId}";
        var date = $("input[name='crmSalesOrder.businessdate']").val();
        var price = $("#crm-taxprice-terminalOrderDetailAddPage").val();
        var url = "";
        var data = "";
        if(type == 1){
            url = "sales/getAuxUnitNumAndPrice.do"
            data = {id:goodsId,unitnum:unitnum,cid:customerId,date:date,free:"1",orderid:orderid,price:price,taxpricechange:terminal_taxpricechange};
        }
        else if(type == 2){
            url = "sales/getUnitNumAndPrice.do"
            data = {id:goodsId,auxnum:auxnum,overnum:overnum,cid:customerId,date:date,free:"1",orderid:orderid,price:price,taxpricechange:terminal_taxpricechange};
        }
        $.ajax({
            url:url,
            dataType:'json',
            type:'post',
            async:false,
            data:data,
            success:function(json){
                $("#crm-taxamount-terminalOrderDetailAddPage").val(json.taxamount);
                $("#crm-notaxamount-terminalOrderDetailAddPage").val(json.notaxamount);
                $("#crm-taxprice-terminalOrderDetailAddPage").val(json.taxprice);
                $("#crm-boxprice-terminalOrderDetailAddPage").val(json.boxprice);
                $("#crm-notaxprice-terminalOrderDetailAddPage").val(json.notaxprice);
                $("#crm-totalboxweight-terminalOrderDetailAddPage").val(json.totalboxweight);
                $("#crm-totalboxvolume-terminalOrderDetailAddPage").val(json.totalboxvolume);
                $("#crm-tax-terminalOrderDetailAddPage").numberbox('setValue',json.tax);
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
                if(json.groupid!=null &&json.groupid!=""){
                    $("#crm-groupid-terminalOrderDetailAddPage").val(json.groupid);
                }else{
                    $("#crm-groupid-terminalOrderDetailAddPage").val("");
                }
                $("input[name=oldprice]").val(json.taxprice);
                $("input[name=totalbox]").val(json.totalbox);
                //判断是否符合满赠条件
                if(json.hasFree=='1'){
                    $("#crm-hasFree-terminalOrderDetailAddPage").val("1");
                }else{
                    $("#crm-hasFree-terminalOrderDetailAddPage").val("0");
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
                $("#crm-taxprice-terminalOrderDetailAddPage").val(json.taxPrice);
                $("#crm-boxprice-terminalOrderDetailAddPage").val(json.boxPrice);
                $("#crm-taxamount-terminalOrderDetailAddPage").val(json.taxAmount);
                $("#crm-notaxprice-terminalOrderDetailAddPage").val(json.noTaxPrice);
                $("#crm-notaxamount-terminalOrderDetailAddPage").val(json.noTaxAmount);
                $("#crm-tax-terminalOrderDetailAddPage").numberbox('setValue',json.tax);
                $this.css({'background':''});
                var groupid = $("#crm-groupid-terminalOrderDetailAddPage").val();
                if(groupid != null && groupid != ""){
                    $("#crm-groupid-terminalOrderDetailAddPage").val("");
                    $("input[name=remark]").val("");
                }
                terminal_taxpricechange = "1";
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
                $("#crm-taxprice-terminalOrderDetailAddPage").val(json.taxPrice);
                $("#crm-boxprice-terminalOrderDetailAddPage").val(json.boxPrice);
                $("#crm-taxamount-terminalOrderDetailAddPage").val(json.taxAmount);
                $("#crm-notaxprice-terminalOrderDetailAddPage").val(json.noTaxPrice);
                $("#crm-notaxamount-terminalOrderDetailAddPage").val(json.noTaxAmount);
                $("#crm-tax-terminalOrderDetailAddPage").numberbox('setValue',json.tax);
                $this.css({'background':''});
            }
        });
    }
    function boxpriceChange(){
        var $this = $("#crm-boxprice-terminalOrderDetailAddPage").css({'background':'url(image/loading.gif) right top no-repeat'});
        var price = $("#crm-boxprice-terminalOrderDetailAddPage").val();
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
                $("#crm-taxprice-terminalOrderDetailAddPage").val(json.taxPrice);
                $("#crm-boxprice-terminalOrderDetailAddPage").val(json.boxPrice);
                $("#crm-taxamount-terminalOrderDetailAddPage").val(json.taxAmount);
                $("#crm-notaxprice-terminalOrderDetailAddPage").val(json.noTaxPrice);
                $("#crm-notaxamount-terminalOrderDetailAddPage").val(json.noTaxAmount);
                $("#crm-tax-terminalOrderDetailAddPage").numberbox('setValue',json.tax);
                $this.css({'background':''});
            }
        });
    }
    
    
    
</script>
</body>
</html>
