<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  	<title>商品明细添加页面</title>
	<%@include file="/include.jsp" %>
  </head>

  <body id="oa-body-oaGoodsPriceDetailAddPage">
  	<div class="easyui-layout" data-options="fit:true">
  		<div data-options="region:'center',border:false">
	  		<form id="oa-form-oaGoodsPriceDetailAddPage">
				<input type="hidden" name="id" id="id" value="${index }"/>

                <input type="hidden" name="unitid" id="unitid"  />
                <input type="hidden" name="brandid" id="brandid"  />
                <input type="hidden" name="goodssort" id="goodssort" value=""/>
                <input type="hidden" name="auxunitid" id="auxunitid"  value=""/>
                <input type="hidden" name="auxunitname" id="auxunitname"  value=""/>
                <input type="hidden" name="storageid" id="storageid" value=""/>
                <input type="hidden" name="boxnum" id="boxnum"  value=""/>
                <input type="hidden" name="taxtype" id="taxtype" value=""/>
                <input type="hidden" name="totalvolume" id="totalvolume"  value=""/>
                <input type="hidden" name="totalweight" id="totalweight" value=""/>
                <input type="hidden" name="glength" id="glength" value=""/>
                <input type="hidden" name="gwidth" id="gwidth" value=""/>
                <input type="hidden" name="gheight" id="gheight"  value=""/>

                <table style="border-collapse:collapse;" border="0" cellpadding="2" cellspacing="2">
					<tr><td>&nbsp;</td><td></td><td></td><td></td></tr>
		  			<tr>
		  				<td class="right len120">商品名称：</td>
		  				<td class="len150"><input class="easyui-validatebox len136" style="margin-left: 0px;" id="oa-goodsid-oaGoodsPriceDetailAddPage"  data-options="required:true"/><font color="red">*</font>
                        <input type="hidden" id="goodsname" name="goodsname"/>
                        </td>
		  				<td class="right len100">商品编号：</td>
		  				<td class="len165"><input class="easyui-validatebox len136 no_input" readonly style="margin-left: 0px;" name="goodsid" id="oa-goodsname-oaGoodsPriceDetailAddPage"/></td>
		  			</tr>
		  			<tr>
                        <td class="right">条形码：</td>
		  				<td><input class="easyui-validatebox len136 no_input" style="margin-left: 0px;" readonly="readonly" name="barcode" id="oa-barcode-oaGoodsPriceDetailAddPage" data-options="required:true"/></td>
		  			    <td class="right">单位：</td>
                           <td> <input class="easyui-validatebox len136 no_input" style="margin-left: 0px;" readonly="readonly" name="unitname" id="oa-auxunitid-oaGoodsPriceDetailAddPage"  /></td>
		  			</tr>
		  			<tr>
                        <td class="right">采购价(原)：</td>
                        <td><input class="easyui-numberbox  len136 no_input" readonly style="margin-left: 0px; width: 136px;" name="oldbuytaxprice" id="oa-oldbuytaxprice-oaGoodsPriceDetailAddPage" data-options="required:true, min:0, precision:6"/></td>
                        <td class="right">采购价(现)：</td>
		  				<td><input class="len136" style="margin-left: 0px; width: 136px;"  name="newbuytaxprice" id="oa-newbuytaxprice-oaGoodsPriceDetailAddPage" data-options="required:true, min:0, precision:6"/><font color="red">*</font></td>
		  			</tr>
		  			<tr>
                        <td class="right">基准销售价(原)：</td>
                        <td><input class="easyui-numberbox len136 no_input" readonly style="margin-left: 0px; width: 136px;" name="oldbasesaleprice" id="oa-oldbasesaleprice-oaGoodsPriceDetailAddPage" data-options="required:true, min:0, precision:6"/></td>
                        <td class="right">基准销售价(现)：</td>
		  				<td><input class="easyui-numberbox len136" style="margin-left: 0px; width: 136px;"  name="newbasesaleprice" id="oa-newbasesaleprice-oaGoodsPriceDetailAddPage" data-options="required:true, min:0, precision:6"/><font color="red">*</font></td>
		  		    </tr>
		  			<tr style="display: none">
                        <td class="right">核算成本价(原)：</td>
                        <td><input class="easyui-numberbox  len136 no_input" readonly style="margin-left: 0px; width: 136px;" name="oldcostaccountprice" id="oa-oldcostaccountprice-oaGoodsPriceDetailAddPage" data-options="min:0, precision:6"/></td>
                        <td class="right">核算成本价(现)：</td>
                        <td><input class="easyui-numberbox  len136" style="margin-left: 0px; width: 136px;"  name="newcostaccountprice" id="oa-newcostaccountprice-oaGoodsPriceDetailAddPage" data-options="min:0, precision:6"/><font color="red">*</font></td>
                    </tr>
                    <c:forEach items="${list }" var="item" varStatus="idx">
                        <tr>
                            <td class="right"><c:out value="${item.codename }(原)"></c:out>：</td>
                            <td><input class="easyui-numberbox len136 no_input" readonly style="margin-left: 0px; width: 136px;"  onfocus="this.select()" name="oldprice${idx.index + 1}" id="oa-oldprice${idx.index + 1}-oaGoodsPriceDetailAddPage" data-options="required:true, min:0, precision:6"/><font color="red">*</font></td>
                            <td class="right"><c:out value="${item.codename }(现)"></c:out>：</td>
                            <td><input class="easyui-numberbox len136" style="margin-left: 0px; width: 136px;" onfocus="this.select()"  name="newprice${idx.index + 1}" id="oa-newprice${idx.index + 1}-oaGoodsPriceDetailAddPage" data-options="required:true, min:0, precision:6"/><font color="red">*</font></td>
                        </tr>
                    </c:forEach>
		  			<tr>
		  				<td class="right">备注：</td>
		  				<td colspan="3"><input class="easyui-validatebox" style="margin-left: 0px;width: 395px" name="remark" id="oa-remark-oaGoodsPriceDetailAddPage" maxlength="100"/></td>
		  			</tr>
		  		</table>
	  		</form>
	  	</div>
  		<div data-options="region:'south',border:false">
  			<div class="buttonDetailBG" style="gheight:26px;text-align:right;">
  				快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
	  			<input type="button" value="继续添加" name="savegoon" id="oa-savegoon-oaGoodsPriceDetailAddPage" />
  			</div>
  		</div>
  	</div>
	<script type="text/javascript">

    var pricelength = ${pricelength};
	var defaulttaxname = '';
	var defaultAuxunitname = '';


    $.parser.onComplete = function(context){

        <c:forEach items="${list }" var="item" varStatus="idx">
        if(rowinfo != null){
            var cost = rowinfo.oldcostaccountprice;
            if(rowinfo.oldcostaccountprice == 0.00){
                cost = rowinfo.oldbuytaxprice;
            }
            var price = rowinfo.oldprice${idx.index + 1};
            var profit = formatterMoney(price - cost, 2);
            var rate = formatterMoney(profit * 100 / price, 2);
            if(price != undefined){
                $("#oa-oldprice${idx.index + 1}-oaGoodsPriceDetailAddPage").parent()/*.next()*/.append('<span class="oldtempSpan oldprofit${idx.index + 1}" ><br/><font color="green">毛利率：' + rate + '%</font></span>');
            }
        }

        </c:forEach>

        //商品
        $("#oa-goodsid-oaGoodsPriceDetailAddPage").goodsWidget({
            name: 't_base_goods_info',
            col: 'goodsid',
            param:[	 {field:'defaultsupplier',op:'equal',value:'${supplierid}'}],
            width: 136,
            required: true,
            onSelect: function(data){
                $("#goodsname").val(data.name);
                $("#brandid").val(data.brand);
                $("#storageid").val(data.storageid);
                $("#goodssort").val(data.goodstype);
                $("#auxunitid").val(data.auxunitid);
                $("#auxunitname").val(data.auxunitname);
                $("#boxnum").val(data.boxnum);
                $("#taxtype").val(data.taxtype);
                $("#totalvolume").val(data.totalvolume);
                $("#totalweight").val(data.totalweight);
                $("#glength").val(data.glength);
                $("#gwidth").val(data.gwidth);
                $("#gheight").val(data.gheight);
                $("#unitid").val(data.mainunit);
                $("#oa-goodsname-oaGoodsPriceDetailAddPage").val(data.id);
                $("#oa-barcode-oaGoodsPriceDetailAddPage").val(data.barcode);
                $("#oa-auxunitid-oaGoodsPriceDetailAddPage").val(data.mainunitName);
                $("#oa-oldbuytaxprice-oaGoodsPriceDetailAddPage").numberbox('setValue',data.highestbuyprice);//最高采购价
                $("#oa-oldbasesaleprice-oaGoodsPriceDetailAddPage").numberbox('setValue',data.basesaleprice);
                if(data.costaccountprice == null){
                    $("#oa-oldcostaccountprice-oaGoodsPriceDetailAddPage").numberbox('setValue',0.00);//核销成本价
                    $("#oa-newcostaccountprice-oaGoodsPriceDetailAddPage").numberbox('setValue',0.00);//核销成本价(现）
                }else{
                    $("#oa-oldcostaccountprice-oaGoodsPriceDetailAddPage").numberbox('setValue',data.costaccountprice);//核销成本价
                    $("#oa-newcostaccountprice-oaGoodsPriceDetailAddPage").numberbox('setValue',data.costaccountprice);//核销成本价(现）
                }
                $("#oa-newbuytaxprice-oaGoodsPriceDetailAddPage").numberbox('setValue',data.highestbuyprice);//最高采购价(现）
                $("#oa-newbasesaleprice-oaGoodsPriceDetailAddPage").numberbox('setValue',data.basesaleprice);
                $.ajax({
                    url:'oa/common/selectPriceListByGoodsid.do',
                    dataType:'json',
                    type:'post',
                    data:'id='+ data.id,
                    success:function(json){
                        $('.tempSpan').remove();
                        $('.oldtempSpan').remove();
                        for(var i = 0 ;i< json.priceList.length ;i++){
                            var price = json.priceList[i].taxprice;
                            var index = i + 1;
                            var cost = data.costaccountprice ;
                            if(data.costaccountprice == '' || data.costaccountprice == '0.00' || data.costaccountprice == null){
                                cost = data.highestbuyprice ;
                            }
                            if(price != '' && cost != '') {
                                //var profit = formatterMoney(price - cost, 2);
                                var rate = formatterMoney((price - cost) * 100 / price, 2);
                                $("#oa-oldprice"+index+"-oaGoodsPriceDetailAddPage").next().next().after(
                                                '<span class = "oldtempSpan " name="oldprofit' + (i + 1) + '"  ><br/><font color="green">毛利率：' + rate + '%</font></span>');
                                $("#oa-newprice"+index+"-oaGoodsPriceDetailAddPage").next().next().after(
                                                '<span class = "tempSpan profit' + (i + 1) + '" name="newprofit' + (i + 1) + '" ><br/><font color="green">毛利率：' + rate + '%</font></span>');

                            }else{
                                $("#oa-oldprice"+index+"-oaGoodsPriceDetailAddPage").next().next().after(
                                                '<span class = "oldtempSpan" name="oldprofit' + (i + 1) + '" ><br/><font color="green">毛利率：' + 0 + '%</font></span>');
                                $("#oa-newprice"+index+"-oaGoodsPriceDetailAddPage").next().next().after(
                                                '<span class = "tempSpanprofit' + (i + 1) + '" name="newprofit' + (i + 1) + '"><br/><font color="green">毛利率：' + 0 + '%</font></span>');
                            }
                            $("#oa-oldprice"+index+"-oaGoodsPriceDetailAddPage").numberbox('setValue',price);
                            $("#oa-newprice"+index+"-oaGoodsPriceDetailAddPage").numberbox('setValue',price);

                        }
                        if(pricelength > json.priceList.length ){
                            for(var i = 1 ;i<=pricelength -json.priceList.length;i++){
                                var index = i+json.priceList.length;
                                $("#oa-oldprice"+index+"-oaGoodsPriceDetailAddPage").numberbox('setValue',0);
                                $("#oa-newprice"+index+"-oaGoodsPriceDetailAddPage").numberbox('setValue',0);
                                $("#oa-oldprice"+index+"-oaGoodsPriceDetailAddPage").next().next().after(
                                                '<span class = "oldtempSpan" name="oldprofit' + index + '" ><br/><font color="green">毛利率：' + 0 + '%</font></span>');
                                $("#oa-newprice"+index+"-oaGoodsPriceDetailAddPage").next().next().after(
                                                '<span class = "tempSpan profit' + index + '" name="newprofit' + index + '"><br/><font color="green">毛利率：' + 0 + '%</font></span>');

                            }
                        }
                    }
                });
                getNumberBox("oa-newbuytaxprice-oaGoodsPriceDetailAddPage").focus();
                getNumberBox("oa-newbuytaxprice-oaGoodsPriceDetailAddPage").select();
            }
        });

        <c:forEach items="${list }" var="item" varStatus="idx">
        //价格套改变
        $("#oa-newprice${idx.index + 1}-oaGoodsPriceDetailAddPage").numberbox({
            onChange: function (newValue, oldValue) {
                var cost = $("#oa-newcostaccountprice-oaGoodsPriceDetailAddPage").numberbox('getValue');
                if(cost != 0.00){
                    if(newValue != 0.00){
                        var price = newValue;
                        //var profit = formatterMoney(price - cost, 2);
                        var rate = formatterMoney((price - cost) * 100 / price, 2);
                        $('.profit${idx.index + 1}').html('<br/><font color="green">毛利率：' + rate + '%</font>');
                    }
                }else{
                    cost = $("#oa-newbuytaxprice-oaGoodsPriceDetailAddPage").numberbox('getValue');
                    if(newValue != 0.00){
                        var price = newValue;
                        //var profit = formatterMoney(price - cost, 2);
                        var rate = formatterMoney((price - cost) * 100 / price, 2);
                        $('.profit${idx.index + 1}').html('<br/><font color="green">毛利率：' + rate + '%</font>');
                    }
                }
            }
        });

        </c:forEach>
        //最高采购价改变
        $("#oa-newbuytaxprice-oaGoodsPriceDetailAddPage").numberbox({
            onChange:function(newValue,oldValue){
                $('.tempSpan').remove();
               var cost = $("#oa-newcostaccountprice-oaGoodsPriceDetailAddPage").numberbox('getValue');
               if(cost == 0.00){
                   cost = newValue;
                   for(var i = 0 ;i<pricelength;i++){
                       //var price = $("#oa-oldprice"+(i+1)+"-oaGoodsPriceDetailAddPage").val();
                       var price = $("#oa-newprice"+(i+1)+"-oaGoodsPriceDetailAddPage").numberbox('getValue');
                       //var profit = formatterMoney(price - cost, 2);
                       var rate = formatterMoney((price - cost) * 100 / price, 2);
                       if(isFinite(rate)){
                           $("#oa-newprice"+(i + 1)+"-oaGoodsPriceDetailAddPage").next().next().after(
                                           '<span class = "tempSpan profit' + (i + 1) + '" name="newprofit' + (i + 1) + '" ><br/><font color="green">毛利率：' + rate + '%</font></span>');

                       }else{
                           $("#oa-newprice"+(i + 1)+"-oaGoodsPriceDetailAddPage").next().next().after(
                                           '<span class = "tempSpan profit' + (i + 1) + '" name="newprofit' + (i + 1) + '" ><br/><font color="green">毛利率：' + 0.00 + '%</font></span>');
                       }
                   }
               }
            }
        });
        //核销成本价改变
        $("#oa-newcostaccountprice-oaGoodsPriceDetailAddPage").numberbox({
            onChange:function(newValue,oldValue){
              var cost = newValue;
                if(cost == 0.00){
                    cost = $("#oa-newbuytaxprice-oaGoodsPriceDetailAddPage").numberbox('getValue');
                }
              for(var i = 0 ;i<pricelength;i++){
                  var price = $("#oa-newprice"+(i+1)+"-oaGoodsPriceDetailAddPage").val();
                  //var profit = formatterMoney(price - cost, 2);
                  var rate = formatterMoney((price - cost) * 100 / price, 2);
                  $("#oa-newprofit"+(i+1)+"-oaGoodsPriceDetailAddPage").numberbox('setValue',rate);
              }
            }
        });
        $("#oa-savegoon-oagoodsDetailAddPage").click(function(){
            addDetail(true);
        });
        //最高采购价
        getNumberBox("oa-newbuytaxprice-oaGoodsPriceDetailAddPage").bind("keydown", function(event){
            //enter
            if(event.keyCode==13){
                getNumberBox("oa-newbasesaleprice-oaGoodsPriceDetailAddPage").focus();
                getNumberBox("oa-newbasesaleprice-oaGoodsPriceDetailAddPage").select();
            }
        });
        //基准销售价
        $("#oa-newbasesaleprice-oaGoodsPriceDetailAddPage").textbox('textbox').keydown(function(event){
            //enter
            if(event.keyCode==13){
                $("#oa-newbasesaleprice-oaGoodsPriceDetailAddPage").textbox('textbox').blur();
                $("#oa-newprice1-oaGoodsPriceDetailAddPage").textbox('textbox').focus();
                $("#oa-newprice1-oaGoodsPriceDetailAddPage").textbox('textbox').select();
            }
        });

        <c:forEach items="${list }" var="item" varStatus="idx">
            // $("#oa-newprice1-oaGoodsPriceDetailAddPage").focus();
            $("#oa-newprice${idx.index + 1}-oaGoodsPriceDetailAddPage").textbox('textbox').keydown(function(event){
                //enter
                if(event.keyCode==13){
                    if(${idx.last}){
                        $("#oa-remark-oaGoodsPriceDetailAddPage").focus();

                    }else{
                        $("#oa-newprice${idx.index + 1}-oaGoodsPriceDetailAddPage").textbox('textbox').blur();
                        $("#oa-newprice${idx.index + 2}-oaGoodsPriceDetailAddPage").textbox('textbox').focus();
                        $("#oa-newprice${idx.index + 2}-oaGoodsPriceDetailAddPage").textbox('textbox').select();
                    }

                }
            });
        </c:forEach>

        // 备注
        $("#oa-remark-oaGoodsPriceDetailAddPage").die("keydown").live("keydown",function(event){
            //enter
            if(event.keyCode==13){
                $("#oa-remark-oaGoodsPriceDetailAddPage").blur();
                $('.tempSpan').remove();
                $('.oldtempSpan').remove();
                addDetail(true);
            }
        });

        $("#oa-savegoon-oaGoodsPriceDetailAddPage").click(function(){
            $('.tempSpan').remove();
            $('.oldtempSpan').remove();
            addDetail(true);
        });


    };



	</script>
  </body>
</html>