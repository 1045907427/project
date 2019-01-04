<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>销售退货货通知单商品详细信息新增页面</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
        <form id="ebshop-form-ebtradeDetailAddPage">
            <table cellpadding="5" cellspacing="5">
                <tr>
                    <td class="len80">选择商品：</td>
                    <td><input id="ebshop-goodsId-ebtradeDetailAddPage" name="goodsid" class="len150" />
                        <input type="hidden" name="goodsname" id="ebshop-goodsname-ebtradeDetailAddPage" />
                        <input type="hidden" name="grossweight" id="ebshop-grossweight-ebtradeDetailAddPage"/>
                        <input type="hidden" name="singlevolume" id="ebshop-singlevolume-ebtradeDetailAddPage"/>
                    </td>
                    <td id="ebshop-loading-ebtradeDetailAddPage" colspan="2"></td>
                </tr>
                <tr>
                    <td>数量</td>
                    <td><input id="ebshop-unitnum-ebtradeDetailAddPage" class="len150" value="0" name="unitnum"/></td>
                    <td>辅数量：</td>
                    <td><input id="ebshop-auxnum-ebtradeDetailAddPage" name="auxnum"  value="0" style="width:60px;"/><span id="ebshop-auxunitname-ebtradeDetailAddPage"></span>
                        <input id="ebshop-auxremainder-ebtradeDetailAddPage" name="auxremainder" value="0" style="width:60px;"/><span id="ebshop-unitname-ebtradeDetailAddPage"></span>
                        <input id="ebshop-auxnumdetail-ebtradeDetailAddPage" type="hidden" name="auxnumdetail" />
                    </td>
                </tr>
                <tr>
                    <td>单位：</td>
                    <td>
                        主：<input name="unitname" type="text" class="readonly2" style="width: 40px;" readonly="readonly" /><input type="hidden" name="unitid" />
                        辅：<input name="auxunitname" type="text" class="readonly2" style="width: 40px;" readonly="readonly" /><input type="hidden" name="auxunitid" /></td>
                    <td>商品品牌：</td>
                    <td><input type="text" id="ebshop-brandname-ebtradeDetailAddPage" name="brandname" readonly="readonly" class="len150 readonly" />
                        <input type="hidden" name="brandid" id="ebshop-brandid-ebtradeDetailAddPage" />
                    </td>
                </tr>
                <tr>
                    <td>含税单价：</td>
                    <td><input class="len150 no_input easyui-validatebox" name="taxprice" id="ebshop-taxprice-ebtradeDetailAddPage" required="required" validType="intOrFloat" readonly="readonly"/> </td>
                    <td>含税金额：</td>
                    <td><input class="len150 no_input easyui-numberbox" id="ebshop-taxamount-ebtradeDetailAddPage" data-options="precision:6,groupSeparator:','" readonly="readonly" name="taxamount" /></td>
                </tr>
                <tr>
                    <td>含税箱价：</td>
                    <td><input id="ebshop-boxprice-ebtradeDetailAddPage" class="len150 easyui-validatebox no_input" name="boxprice" required="required" validType="intOrFloat" readonly="readonly" /> </td>
                    <td>箱装量：</td>
                    <td><input name="boxnum" id="ebshop-boxnum-ebtradeDetailAddPage" type="text" class="len150 readonly" readonly="readonly" /></td>
                </tr>
                <tr>
                    <td>未税单价：</td>
                    <td><input class="len150 easyui-validatebox no_input" name="notaxprice" id="ebshop-notaxprice-ebtradeDetailAddPage" required="required" validType="intOrFloat" readonly="readonly"/> </td>
                    <td>未税金额：</td>
                    <td><input class="len150 no_input easyui-numberbox" id="ebshop-notaxamount-ebtradeDetailAddPage" readonly="readonly" name="notaxamount" data-options="precision:6,groupSeparator:','" /></td>
                </tr>
                <tr>
                    <td>税种：</td>
                    <td><input class="len150 readonly" name="taxtypename" /><input type="hidden" name="taxtype" /> </td>
                    <td>税额：</td>
                    <td><input class="len150 no_input easyui-numberbox" id="ebshop-tax-ebtradeDetailAddPage" readonly="readonly" name="tax" data-options="precision:6,groupSeparator:','" /></td>
                </tr>
                <tr>
                    <td>成本价：</td>
                    <td><input id="ebshop-costprice-ebtradeDetailAddPage" readonly="readonly" class="len150 readonly" name="costprice"/></td>
                    <td>箱数：</td>
                    <td><input id="ebshop-totalbox-ebtradeDetailAddPage" class="len150 easyui-numberbox no_input" data-options="precision:6,groupSeparator:','" readonly="readonly" name="totalbox"/></td>
                </tr>
                <tr>
                    <td>备注：</td>
                    <td colspan="3"><input id="ebshop-remark-ebtradeDetailAddPage" type="text" name="remark"  style="width: 390px;"/></td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'south',border:false">
        <div class="buttonDetailBG" style="height:30px;text-align:right;">
            快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
            <input type="button" value="继续添加" name="savegoon" id="ebshop-savegoon-ebtradeDetailAddPage" />
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function(){
        $("#ebshop-goodsId-ebtradeDetailAddPage").goodsWidget({
            singleSelect:true,
            required:true,
            onSelect: function(data){
                $("#ebshop-goodsname-ebtradeDetailAddPage").val(data.name);
                $("#ebshop-brandid-ebtradeDetailAddPage").val(data.brandid);
                $("#ebshop-brandname-ebtradeDetailAddPage").val(data.brandName);
                $("input[name=boxnum]").val(data.boxnum);
                $("#ebshop-loading-ebtradeDetailAddPage").addClass("img-loading");
                $.ajax({
                    url:'ebtrade/getGoodsDetail.do',
                    dataType:'json',
                    type:'post',
                    async:false,
                    data:{id:data.id},
                    success:function(json){
                        $("#ebshop-grossweight-ebtradeDetailAddPage").val(json.detail.goodsInfo.grossweight);
                        $("#ebshop-singlevolume-ebtradeDetailAddPage").val(json.detail.goodsInfo.singlevolume);
                        $("#ebshop-loading-ebtradeDetailAddPage").removeClass("img-loading").html("商品编码：<font color='green'>"+json.detail.goodsInfo.id+"</font>");
                        $("input[name=unitid]").val(json.detail.goodsInfo.mainunit);
                        $("input[name=unitname]").val(json.detail.goodsInfo.mainunitName);
                        $("#ebshop-unitname-ebtradeDetailAddPage").html(json.detail.goodsInfo.mainunitName);
                        $("input[name=unitnum]").val(json.detail.unitnum);
                        $("input[name=auxunitid]").val(json.detail.auxunitid);
                        $("input[name=auxunitname]").val(json.detail.auxunitname);
                        $("#ebshop-auxunitname-ebtradeDetailAddPage").html(json.detail.auxunitname);
                        $("input[name=auxnumdetail]").val(json.detail.auxnumdetail);
                        $("input[name=auxnum]").val(json.detail.auxnum);
                        $("input[name=taxtype]").val(json.detail.taxtype);
                        $("input[name=taxtypename]").val(json.detail.taxtypename);
                        $("#ebshop-taxprice-ebtradeDetailAddPage").val(json.detail.taxprice);
                        $("#ebshop-costprice-ebtradeDetailAddPage").val(json.detail.costprice);
                        $("#ebshop-boxprice-ebtradeDetailAddPage").val(json.detail.boxprice);
                        $("#ebshop-totalbox-ebtradeDetailAddPage").val(json.detail.totalbox);
                        $("#ebshop-notaxprice-ebtradeDetailAddPage").val(json.detail.notaxprice);
                        $("#ebshop-taxamount-ebtradeDetailAddPage").numberbox('setValue',json.detail.taxamount);
                        $("#ebshop-notaxamount-ebtradeDetailAddPage").numberbox('setValue',json.detail.notaxamount);
                        $("#ebshop-tax-ebtradeDetailAddPage").numberbox('setValue',json.detail.tax);
                        $("input[name=remark]").val(json.detail.remark);
                        $("#ebshop-unitnum-ebtradeDetailAddPage").focus();
                        $("#ebshop-unitnum-ebtradeDetailAddPage").select();
                    }
                });
            }
        });
        $("#ebshop-unitnum-ebtradeDetailAddPage").validatebox({
            required: true,
            validType: 'integer'
        });
        $("#ebshop-auxnum-ebtradeDetailAddPage").validatebox({
            required: true,
            validType: 'integer'
        });
        $("#ebshop-auxnum-ebtradeDetailAddPage").validatebox({
            required: true,
            validType: 'integer'
        });
        $("#ebshop-unitnum-ebtradeDetailAddPage").change(function(){
            unitnumChange(1);
        });
        $("#ebshop-auxnum-ebtradeDetailAddPage").change(function(){
            unitnumChange(2);
        });
        $("#ebshop-auxremainder-ebtradeDetailAddPage").change(function(){
            unitnumChange(2);
        });
        $("#ebshop-taxprice-ebtradeDetailAddPage").change(function(){
            priceChange("1", '#ebshop-taxprice-ebtradeDetailAddPage');
        });
        $("#ebshop-notaxprice-ebtradeDetailAddPage").change(function(){
            priceChange("2", '#ebshop-notaxprice-ebtradeDetailAddPage');
        });
        $("#ebshop-boxprice-ebtradeDetailAddPage").change(function(){
            boxpriceChange();
        });
        $("#ebshop-savegoon-ebtradeDetailAddPage").click(function(){
            addSaveDetail(true);
        });
        $("#ebshop-unitnum-ebtradeDetailAddPage").die("keydown").live("keydown",function(event){
            if(event.keyCode==13){
                $("#ebshop-auxnum-ebtradeDetailAddPage").focus();
                $("#ebshop-auxnum-ebtradeDetailAddPage").select();
            }
        });
        $("#ebshop-auxnum-ebtradeDetailAddPage").die("keydown").live("keydown",function(event){
            if(event.keyCode==13){
                $("#ebshop-auxremainder-ebtradeDetailAddPage").focus();
                $("#ebshop-auxremainder-ebtradeDetailAddPage").select();
            }
        });
        $("#ebshop-auxremainder-ebtradeDetailAddPage").die("keydown").live("keydown",function(event){
            if(event.keyCode==13){
                $("#ebshop-remark-ebtradeDetailAddPage").focus();
                $("#ebshop-remark-ebtradeDetailAddPage").select();
            }
        });
        $("#ebshop-remark-ebtradeDetailAddPage").die("keydown").live("keydown",function(event){
            if(event.keyCode==13){
                $("#ebshop-savegoon-ebtradeDetailAddPage").focus();
                $("#ebshop-savegoon-ebtradeDetailAddPage").select();
            }
        });
        $("#ebshop-savegoon-ebtradeDetailAddPage").die("keydown").live("keydown",function(event){
            if(event.keyCode==13){
                addSaveDetail(true);
            }
        });
        $("#ebshop-boxprice-ebtradeDetailAddPage").die("keydown").live("keydown",function(event){
            if(event.keyCode==13){
                $("#ebshop-remark-ebtradeDetailAddPage").focus();
                $("#ebshop-remark-ebtradeDetailAddPage").select();
            }
        });
    });
    function unitnumChange(type){ //数量改变方法
        var $this = $("#ebshop-unitnum-ebtradeDetailAddPage").css({'background':'url(image/loading.gif) right top no-repeat'});
        var goodsId = $("input[name=goodsid]").val();
        var unitnum = $("input[name=unitnum]").val();
        var auxnum = $("input[name=auxnum]").val();
        var auxremainder = $("input[name=auxremainder]").val();
        var aid = $("input[name=auxunitid]").val();
        var taxprice = $("#ebshop-taxprice-ebtradeDetailAddPage").val();
        var notaxprice = $("#ebshop-notaxprice-ebtradeDetailAddPage").val();
        var taxtype = $("input[name=taxtype]").val();
        $.ajax({
            url:'sales/getAuxUnitNum.do',
            dataType:'json',
            type:'post',
            async:false,
            data:'id='+ goodsId +'&unitnum='+ unitnum +'&aid='+ aid +'&tp='+ taxprice +'&auxnum='+ auxnum +'&taxtype='+ taxtype+ '&overnum='+ auxremainder+ '&type='+ type,
            success:function(json){
                $("input[name=auxnumdetail]").val(json.auxnumdetail);
                $("#ebshop-totalbox-ebtradeDetailAddPage").numberbox('setValue',json.totalbox);
                $("#ebshop-taxamount-ebtradeDetailAddPage").numberbox('setValue',json.taxAmount);
                $("#ebshop-notaxamount-ebtradeDetailAddPage").numberbox('setValue',json.noTaxAmount);
                $("#ebshop-tax-ebtradeDetailAddPage").numberbox('setValue',json.tax);
                if(type == 1){
                    $("input[name=auxnum]").val(json.auxnum);
                    $("input[name=auxremainder]").val(json.overnum);
                }
                if(type == 2){
                    $("input[name=unitnum]").val(json.unitnum);
                    $("input[name=auxnum]").val(json.auxnum);
                    $("input[name=auxremainder]").val(json.overnum);
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
            data:'type='+ type +'&price='+ price +'&taxtype='+ taxtype +'&unitnum='+ unitnum+ '&id='+ goodsId,
            success:function(json){
                $("#ebshop-taxprice-ebtradeDetailAddPage").val(json.taxPrice);
                $("#ebshop-boxprice-ebtradeDetailAddPage").val(json.boxPrice);
                $("#ebshop-taxamount-ebtradeDetailAddPage").numberbox('setValue',json.taxAmount);
                $("#ebshop-notaxprice-ebtradeDetailAddPage").val(json.noTaxPrice);
                $("#ebshop-notaxamount-ebtradeDetailAddPage").numberbox('setValue',json.noTaxAmount);
                $("#ebshop-tax-ebtradeDetailAddPage").numberbox('setValue',json.tax);
                $this.css({'background':''});
            }
        });
    }
    function boxpriceChange(){
        var $this = $("#ebshop-boxprice-ebtradeDetailAddPage").css({'background':'url(image/loading.gif) right top no-repeat'});
        var price = $("#ebshop-boxprice-ebtradeDetailAddPage").val();
        var goodsId = $("input[name=goodsid]").val();
        var taxtype = $("input[name=taxtype]").val();
        var unitnum = $("input[name=unitnum]").val();
        var auxnum = $("input[name=auxnum]").val();
        $.ajax({
            url:'sales/getAmountChangerByBoxprice.do',
            dataType:'json',
            async:false,
            type:'post',
            data:'&boxprice='+ price +'&taxtype='+ taxtype +'&unitnum='+ unitnum+ '&id='+ goodsId,
            success:function(json){
                $("#ebshop-taxprice-ebtradeDetailAddPage").val(json.taxPrice);
                $("#ebshop-boxprice-ebtradeDetailAddPage").val(json.boxPrice);
                $("#ebshop-taxamount-ebtradeDetailAddPage").numberbox('setValue',json.taxAmount);
                $("#ebshop-notaxprice-ebtradeDetailAddPage").val(json.noTaxPrice);
                $("#ebshop-notaxamount-ebtradeDetailAddPage").numberbox('setValue',json.noTaxAmount);
                $("#ebshop-tax-ebtradeDetailAddPage").numberbox('setValue',json.tax);
                $this.css({'background':''});
            }
        });
    }
</script>
</body>
</html>
