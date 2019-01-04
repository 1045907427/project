<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  	<title>商品明细添加页面</title>
	<%@include file="/include.jsp" %>
  </head>
  
  <body id="oa-body-oaGoodsPriceDetailEditPage">
  	<div class="easyui-layout" data-options="fit:true">
  		<div data-options="region:'center',border:false">
	  		<form id="oa-form-oaGoodsPriceDetailEditPage">

				<input type="hidden" name="id" id="id" value="${param.index }"/>
                <input type="hidden" name="supplierid" id="supplierid" value="${supplierid }"/>

		  		<table style="border-collapse:collapse;" border="0" cellpadding="2" cellspacing="2">
					<tr><td>&nbsp;</td><td></td><td></td><td></td></tr>
		  			<tr>
		  				<td class="right len120">商品名称：</td>
		  				<td class="len150">
                            <input  class="easyui-validatebox len136" readonly style="margin-left: 0px;" name="goodsname" id="oa-goodsname-oaGoodsPriceDetailEditPage" /><font color="red">*</font>

                        </td>
		  				<td class="right len100">商品编号：</td>
		  				<td class="len165"><input class="easyui-validatebox len136 no_input" readonly style="margin-left: 0px;" name="goodsid" id="oa-goodsid-oaGoodsPriceDetailEditPage"/></td>
		  			</tr>
		  			<tr>
                        <td class="right">条形码：</td>
		  				<td><input class="easyui-validatebox len136 no_input" style="margin-left: 0px;" readonly="readonly" name="barcode" id="oa-barcode-oaGoodsPriceDetailEditPage" data-options="required:true"/></td>
		  			    <td class="right">单位：</td>
		  				<td><input class="easyui-validatebox len136 no_input" style="margin-left: 0px;" readonly name="unitname" id="oa-auxunitid-oaGoodsPriceDetailEditPage" data-options="required:true" value="${defaultAuxunitid }"/></td>
		  			</tr>
		  			<tr>
                        <td class="right">采购价(原)：</td>
                        <td><input class="easyui-numberbox  len136 no_input" readonly style="margin-left: 0px;" name="oldbuytaxprice" id="oa-oldbuytaxprice-oaGoodsPriceDetailEditPage" data-options="required:true, min:0, precision:6"/></td>
                        <td class="right">采购价(现)：</td>
		  				<td><input class="len136" style="margin-left: 0px;" name="newbuytaxprice" id="oa-newbuytaxprice-oaGoodsPriceDetailEditPage" data-options="required:true, min:0, precision:6"/><font color="red">*</font></td>
		  			</tr>
		  			<tr>
                        <td class="right">基准销售价(原)：</td>
                        <td><input class="easyui-numberbox len136 no_input" readonly style="margin-left: 0px;" name="oldbasesaleprice" id="oa-oldbasesaleprice-oaGoodsPriceDetailEditPage" data-options="required:true, min:0, precision:6"/></td>
                        <td class="right">基准销售价(现)：</td>
		  				<td><input class="easyui-numberbox len136"  style="margin-left: 0px;" name="newbasesaleprice" id="oa-newbasesaleprice-oaGoodsPriceDetailEditPage" data-options="required:true, min:0, precision:6"/><font color="red">*</font></td>
		  		    </tr>
		  			<tr style="display: none">
                        <td class="right">核算成本价(原)：</td>
                        <td><input class="easyui-numberbox len136 no_input" style="margin-left: 0px;" name="oldcostaccountprice" id="oa-oldcostaccountprice-oaGoodsPriceDetailEditPage" data-options="min:0, precision:6"/></td>
                        <td class="right">核算成本价(现)：</td>
                        <td><input class="easyui-numberbox  len136"  style="margin-left: 0px;" name="newcostaccountprice" id="oa-newcostaccountprice-oaGoodsPriceDetailEditPage" data-options="min:0, precision:6"/><font color="red">*</font></td>
                    </tr>
                    <c:forEach items="${list }" var="item" varStatus="idx">
                        <tr>
                            <td class="right"><c:out value="${item.codename }(原)"></c:out>：</td>
                            <td><input class="easyui-numberbox len136 no_input" readonly style="margin-left: 0px;"  onfocus="this.select()" name="oldprice${idx.index + 1}" id="oa-oldprice${idx.index + 1}-oaGoodsPriceDetailEditPage" data-options="required:true, min:0, precision:6"/><font color="red">*</font></td>
                            <td class="right"><c:out value="${item.codename }(现)"></c:out>：</td>
                            <td><input class="easyui-numberbox len136" style="margin-left: 0px;" onfocus="this.select()"  name="newprice${idx.index + 1}" id="oa-newprice${idx.index + 1}-oaGoodsPriceDetailEditPage" data-options="required:true, min:0, precision:6"/><font color="red">*</font></td>
                        </tr>
                    </c:forEach>
		  			<tr>
		  				<td class="right">备注：</td>
		  				<td colspan="3"><input class="easyui-validatebox" style="margin-left: 0px;width: 395px" onfocus="this.select()" name="remark" id="oa-remark-oaGoodsPriceDetailEditPage" maxlength="100"/></td>
		  			</tr>
		  		</table>
	  		</form>
	  	</div>
  		<div data-options="region:'south',border:false">
  			<div class="buttonDetailBG" style="gheight:26px;text-align:right;">
  				快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
	  			<input type="button" value="确定" name="savegoon" id="oa-savegoon-oaGoodsPriceDetailEditPage" />
  			</div>
  		</div>
  	</div>
	<script type="text/javascript">

	var defaulttaxname = '';
	var defaultAuxunitname = '';
    var pricelength = '${pricelength}';


    $.parser.onComplete = function(context){

       $("#oa-goodsname-oaGoodsPriceDetailEditPage").attr('text', goodsname);


        <c:forEach items="${list }" var="item" varStatus="idx">
        var cost = rowinfo.oldcostaccountprice;
        if(rowinfo.oldcostaccountprice == 0.00){
            cost = rowinfo.oldbuytaxprice;
        }
        var price = rowinfo.oldprice${idx.index + 1};
        if(price == 0.00000000){
            $("#oa-oldprice${idx.index + 1}-oaGoodsPriceDetailEditPage").next().next().after('<span class="tempSpan1 oldprofit${idx.index + 1}" ><br/><font color="green">毛利率：' + 0 + '%</font></span>');
        }else{
            var profit = formatterMoney(price - cost, 2);
            var rate = formatterMoney(profit * 100 / price, 2);
            $("#oa-oldprice${idx.index + 1}-oaGoodsPriceDetailEditPage").next().next().after('<span class="tempSpan1 oldprofit${idx.index + 1}" ><br/><font color="green">毛利率：' + rate + '%</font></span>');
        }

        </c:forEach>
//        getNumberBox("oa-newbuytaxprice-oaGoodsPriceDetailEditPage").focus();
//        getNumberBox("oa-newbuytaxprice-oaGoodsPriceDetailEditPage").select();

        //商品
        $("#oa-goodsname-oaGoodsPriceDetailEditPage").goodsWidget({
            name: 't_base_goods_info',
            col: 'goodsid',
            param:[	 {field:'defaultsupplier',op:'equal',value:'${supplierid}'}],
            width: 136,
            required: true,
            onSelect: function(data){
                $("#oa-goodsid-oaGoodsPriceDetailEditPage").val(data.goodsid);
                $("#oa-barcode-oaGoodsPriceDetailEditPage").val(data.barcode);
                $("#oa-auxunitid-oaGoodsPriceDetailEditPage").val(data.mainunitName);
                $("#oa-oldbuytaxprice-oaGoodsPriceDetailEditPage").numberbox('setValue',data.highestbuyprice);//最高采购价
                $("#oa-oldbasesaleprice-oaGoodsPriceDetailEditPage").numberbox('setValue',data.basesaleprice);
                $("#oa-oldcostaccountprice-oaGoodsPriceDetailEditPage").numberbox('setValue',data.costaccountprice);//核销成本价
                $("#oa-newbuytaxprice-oaGoodsPriceDetailEditPage").numberbox('setValue',data.highestbuyprice);//最高采购价(现）
                $("#oa-newbasesaleprice-oaGoodsPriceDetailEditPage").numberbox('setValue',data.basesaleprice);
                $("#oa-newcostaccountprice-oaGoodsPriceDetailEditPage").numberbox('setValue',data.costaccountprice);//核销成本价(现）
                $.ajax({
                    url:'oa/common/selectPriceListByGoodsid.do',
                    dataType:'json',
                    type:'post',
                    data:'id='+ data.id,
                    success:function(json){
                        for(var i = 0 ;i< json.priceList.length ;i++){
                            var price = json.priceList[i].taxprice;
                            var index = i + 1;
                            var cost = data.costaccountprice ;
                            if(data.costaccountprice == '' || data.costaccountprice == '0.00'){
                                cost = data.highestbuyprice  ;
                            }
                            if(price != '' && cost != '') {
                                //var profit = formatterMoney(price - cost, 2);
                                var rate = formatterMoney((price - cost) * 100 / price, 2);
                                $("#oa-oldprice"+index+"-oaGoodsPriceDetailEditPage").next().next().after(
                                                '<span class = "tempSpan oldprofit' + (i + 1) + ' " name="oldprofit' + (i + 1) + '"  ><br/><font color="green">毛利率：' + rate + '%</font></span>');
                                $("#oa-newprice"+index+"-oaGoodsPriceDetailEditPage").next().next().after(
                                                '<span class = "tempSpan newprofit' + (i + 1) + '" name="newprofit' + (i + 1) + '" ><br/><font color="green">毛利率：' + rate + '%</font></span>');

                            }else{
                                $("#oa-oldprice"+index+"-oaGoodsPriceDetailEditPage").next().next().after(
                                                '<span class = "tempSpan oldprofit' + (i + 1) + '"  name="oldprofit' + (i + 1) + '"  ><br/><font color="green">毛利率：' + 0 + '%</font></span>');
                                $("#oa-newprice"+index+"-oaGoodsPriceDetailEditPage").next().next().after(
                                                '<span class = "tempSpan newprofit' + (i + 1) + '" name="newprofit' + (i + 1) + '" ><br/><font color="green">毛利率：' + 0 + '%</font></span>');
                            }
                            $("#oa-oldprice"+index+"-oaGoodsPriceDetailEditPage").numberbox('setValue',price);
                            $("#oa-newprice"+index+"-oaGoodsPriceDetailEditPage").numberbox('setValue',price);

                        }
                    }

                });
                getNumberBox("oa-newbuytaxprice-oaGoodsPriceDetailEditPage").focus();
                getNumberBox("oa-newbuytaxprice-oaGoodsPriceDetailEditPage").select();
            }
        });

        <c:forEach items="${list }" var="item" varStatus="idx">
            //价格套改变
            $("#oa-newprice${idx.index + 1}-oaGoodsPriceDetailEditPage").numberbox({
                onChange: function (newValue, oldValue) {
                    var cost = $("#oa-newcostaccountprice-oaGoodsPriceDetailEditPage").numberbox('getValue');
                    if(cost != 0.00){
                        if(newValue != 0.00) {
                            var price = newValue;
                            //var profit = formatterMoney(price - cost, 2);
                            var rate = formatterMoney((price - cost) * 100 / price, 2);
                            $('.newprofit${idx.index + 1}').html('<br/><font color="green">毛利率：' + rate + '%</font>');
                        }
                    }else{
                        cost = $("#oa-newbuytaxprice-oaGoodsPriceDetailEditPage").numberbox('getValue');
                        if(newValue != 0.00){
                            var price = newValue;
                            //var profit = formatterMoney(price - cost, 2);
                            var rate = formatterMoney((price - cost) * 100 / price, 2);
                            $('.newprofit${idx.index + 1}').html('<br/><font color="green">毛利率：' + rate + '%</font>');
                        }
                    }
                }
            });

        </c:forEach>


        //最高采购价改变
        $("#oa-newbuytaxprice-oaGoodsPriceDetailEditPage").numberbox({
            onChange:function(newValue,oldValue){
                $('.tempSpan').remove();
                var cost = $("#oa-newcostaccountprice-oaGoodsPriceDetailEditPage").numberbox('getValue');
                if(cost == 0.00){
                    cost = newValue;
                    for(var i = 0 ;i<pricelength;i++){
                        var price = $("#oa-newprice"+(i+1)+"-oaGoodsPriceDetailEditPage").numberbox('getValue');
                        //var profit = formatterMoney(price - cost, 2);
                        var rate = formatterMoney((price - cost) * 100 / price, 2);
                        if(isFinite(rate)){
                            $("#oa-newprice"+(i + 1)+"-oaGoodsPriceDetailEditPage").next().next().after(
                                            '<span class = "tempSpan newprofit' + (i + 1) + '" name="newprofit' + (i + 1) + '" ><br/><font color="green">毛利率：' + rate + '%</font></span>');
                        }else{
                            $("#oa-newprice"+(i + 1)+"-oaGoodsPriceDetailEditPage").next().next().after(
                                            '<span class = "tempSpan newprofit' + (i + 1) + '" name="newprofit' + (i + 1) + '" ><br/><font color="green">毛利率：' + 0 + '%</font></span>');
                        }
                    }
                }
            }
        });
        //核算成本价改变
        $("#oa-newcostaccountprice-oaGoodsPriceDetailEditPage").numberbox({
            onChange:function(newValue,oldValue){
                var cost = newValue;
                if(cost == 0.00){
                    cost = $("#oa-newbuytaxprice-oaGoodsPriceDetailEditPage").numberbox('getValue');
                }
                for(var i = 0 ;i<pricelength;i++){
                    var price = $("#oa-newprice"+(i+1)+"-oaGoodsPriceDetailEditPage").val();
                    if(price != 0.00){
                        //var profit = formatterMoney(price - cost, 2);
                        var rate = formatterMoney((price - cost) * 100 / price, 2);
                        $("#oa-newprofit"+(i+1)+"-oaGoodsPriceDetailEditPage").numberbox('setValue',rate);
                    }
                }
            }
        });

        //最高采购价
        getNumberBox("oa-newbuytaxprice-oaGoodsPriceDetailEditPage").bind("keydown", function(event){
            //enter
            if(event.keyCode==13){
                getNumberBox("oa-newbasesaleprice-oaGoodsPriceDetailEditPage").focus();
                getNumberBox("oa-newbasesaleprice-oaGoodsPriceDetailEditPage").select();
            }
        });
        //基准销售价
        $("#oa-newbasesaleprice-oaGoodsPriceDetailEditPage").textbox('textbox').keydown(function(event){
            //enter
            if(event.keyCode==13){
                $("#oa-newbasesaleprice-oaGoodsPriceDetailEditPage").textbox('textbox').blur();
                $("#oa-newprice1-oaGoodsPriceDetailEditPage").textbox('textbox').focus();
                $("#oa-newprice1-oaGoodsPriceDetailEditPage").textbox('textbox').select();
            }
        });
        <c:forEach items="${list }" var="item" varStatus="idx">
        // $("#oa-newprice1-oaGoodsPriceDetailEditPage").focus();
        $("#oa-newprice${idx.index + 1}-oaGoodsPriceDetailEditPage").textbox('textbox').keydown(function(event){
            //enter
            if(event.keyCode==13){
                if(${idx.last}){
                    $("#oa-remark-oaGoodsPriceDetailEditPage").focus();

                }else{
                    $("#oa-newprice${idx.index + 1}-oaGoodsPriceDetailEditPage").textbox('textbox').blur();
                    $("#oa-newprice${idx.index + 2}-oaGoodsPriceDetailEditPage").textbox('textbox').focus();
                    $("#oa-newprice${idx.index + 2}-oaGoodsPriceDetailEditPage").textbox('textbox').select();
                }

            }
        });
        </c:forEach>




        // 备注
		$("#oa-remark-oaGoodsPriceDetailEditPage").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#oa-remark-oaGoodsPriceDetailEditPage").blur();
                $('.tempSpan').remove();
                editDetail(true);
			}
		});

   		$("#oa-savegoon-oaGoodsPriceDetailEditPage").click(function(){
            $('.tempSpan').remove();
 			editDetail(true);
  		});
        
		
	};





	</script>
  </body>
</html>