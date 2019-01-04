<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>电商商品明细修改页面</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
    <div data-options="region:'center',border:false">
        <form id="ebshop-form-ebgoodsDetailAddPage">
            <table cellpadding="3" cellspacing="3">
                <tr>
                    <td class="len80">选择商品：</td>
                    <td><input id="ebshop-goodsid-ebgoodsDetailAddPage" name="goodsid" class="len150" /><input type="hidden" name="goodsname" /></td>
                    <td id="ebshop-loading-ebgoodsDetailAddPage" colspan="2"></td>
                </tr>
                <tr>
                    <td>数量：</td>
                    <td><input class="len150 easyui-validatebox" name="unitnum" id="ebshop-unitnum-ebgoodsDetailAddPage" required="required" validType="integer" /></td>
                    <td>是否主商品：</td>
                    <td>
                        <select id="ebshop-ismian-ebgoodsDetailAddPage" style="width: 150px;" name="ismian">
                            <option></option>
                            <option value="0" selected="selected">否</option>
                            <option value="1">是</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>辅数量：</td>
                    <td><input class="len150 readonly" readonly="readonly" name="auxnumdetail" /></td>
                    <td>占库存比重%：</td>
                    <td><input id="ebshop-rate-ebgoodsDetailAddPage" class="len150 easyui-validatebox" validType="integer" name="rate" /></td>
                </tr>
                <tr>
                    <td>主单位：</td>
                    <td><input name="unitname" readonly="readonly" class="len150 readonly" /><input type="hidden" name="unitid" /></td>
                    <td>辅单位：</td>
                    <td><input name="auxunitname" readonly="readonly" class="len150 readonly" /><input type="hidden" name="auxunitid" /></td>
                </tr>
                <tr>
                    <td>单价：</td>
                    <td><input class="len150 easyui-validatebox" name="price" id="ebshop-price-ebgoodsDetailAddPage" required="required" validType="intOrFloat" /> </td>
                    <td>金额：</td>
                    <td><input class="len150 no_input easyui-numberbox" id="ebshop-amount-ebgoodsDetailAddPage" data-options="precision:6,groupSeparator:','" readonly="readonly" name="amount" /></td>
                </tr>
                <tr>
                    <td>备注：</td>
                    <td colspan="3"><input type="text" id="ebshop-remark-ebgoodsDetailAddPage" style="width:396px;" name="remark"  /></td>
                </tr>
            </table>
        </form>
    </div>
    <div data-options="region:'south',border:false">
        <div class="buttonDetailBG" style="text-align:right;">
            快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
            <input type="button" id="ebshop-savegoon-ebgoodsDetailAddPage" value="确定"/>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(function(){
        $("#ebshop-goodsid-ebgoodsDetailAddPage").goodsWidget({
            singleSelect:true,
            width:150,
            onlyLeafCheck:true,
            onSelect: function(data){
                $("input[name=goodsname]").val(data.name);
                $("#ebshop-loading-ebgoodsDetailAddPage").addClass("img-loading");
                var ret = ebgoods_AjaxConn({goodsid:data.id},'ebgoods/getEbgoodsRowGoodsInfo.do');
                var json = $.parseJSON(ret);
                if(!isObjectEmpty(json)){
                    $("#ebshop-loading-ebgoodsDetailAddPage").removeClass("img-loading").html("商品编码：<font color='green'>"+json.goodsid+"</font>");
                    $("input[name=unitid]").val(json.unitid);
                    $("input[name=unitname]").val(json.unitname);
                    $("input[name=unitnum]").val(json.unitnum);
                    $("input[name=price]").val(json.price);
                    $("input[name=auxunitid]").val(json.auxunitid);
                    $("input[name=auxunitname]").val(json.auxunitname);
                    $("input[name=auxnumdetail]").val(json.auxnumdetail);
                    $("#ebshop-amount-ebgoodsDetailAddPage").numberbox('setValue',json.amount);
                    $("input[name=remark]").val(json.remark);
                    $("#ebshop-unitnum-ebgoodsDetailAddPage").focus();
                }
            }
        });
        $("#ebshop-unitnum-ebgoodsDetailAddPage").change(function(){
            unitnumChange();
        });
        $("#ebshop-price-ebgoodsDetailAddPage").change(function(){
            priceChange();
        });

        $("#ebshop-savegoon-ebgoodsDetailAddPage").click(function(){
            editEbgoodsDetail();
        });

        $("#ebshop-unitnum-ebgoodsDetailAddPage").die("keydown").live("keydown",function(event){
            if(event.keyCode==13){
                $("#ebshop-ismian-ebgoodsDetailAddPage").focus();
            }
        });

        $("#ebshop-ismian-ebgoodsDetailAddPage").die("keydown").live("keydown",function(event){
            if(event.keyCode==13){
                $("#ebshop-rate-ebgoodsDetailAddPage").focus();
                $("#ebshop-rate-ebgoodsDetailAddPage").select();
            }
        });

        $("#ebshop-rate-ebgoodsDetailAddPage").die("keydown").live("keydown",function(event){
            if(event.keyCode==13){
                $("#ebshop-price-ebgoodsDetailAddPage").focus();
                $("#ebshop-price-ebgoodsDetailAddPage").select();
            }
        });

        $("#ebshop-price-ebgoodsDetailAddPage").die("keydown").live("keydown",function(event){
            if(event.keyCode==13){
                $("#ebshop-remark-ebgoodsDetailAddPage").focus();
                $("#ebshop-remark-ebgoodsDetailAddPage").select();
            }
        });

        $("#ebshop-remark-ebgoodsDetailAddPage").die("keydown").live("keydown",function(event){
            if(event.keyCode==13){
                $("#ebshop-savegoon-ebgoodsDetailAddPage").click();
            }
        });
    });
</script>
</body>
</html>
