<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>合同商品修改页面</title>
  </head>
  
  <body>
  	<form action="basefiles/updateCustomerPrice.do" method="post" id="sales-form-priceCustomer">
    	<input type="hidden" id="sales-taxrate-priceCustomer" name="customerPrice.taxrate" value="${customerPrice.taxrate }"/>
    	<input type="hidden" id="customerPrice-goodsid" value="<c:out value="${customerPrice.goodsid }"></c:out>"/>
        <input type="hidden" id="sales-boxnum-priceCustomer" value="${boxnum}"/>
        <table cellpadding="2" cellspacing="2" border="0" style="padding-left: 20px;padding-top: 10px;">
    		<tr>
    			<td width="100px">客户名称:</td>
    			<td><input type="text" id="sales-customerWidget-priceCustomer" name="customerPrice.customerid" value="<c:out value="${customerid }"></c:out>" text="<c:out value="${customername }"></c:out>" style="width: 200px;" readonly="readonly"/>
    				<input type="hidden" name="customerPrice.id" value="<c:out value="${customerPrice.id }"></c:out>"/>
    			</td>
    		</tr>
    		<tr>
    			<td>商品编码:</td>
    			<td><input type="text" id="sales-goodsid-priceCustomer" value="<c:out value="${customerPrice.goodsid }"></c:out>" style="width: 200px;" disabled="disabled"/></td>
    		</tr>
    		<tr>
    			<td>商品名称:</td>
    			<td><input type="text" id="sales-goodsWidget-priceCustomer" name="customerPrice.goodsid" value="<c:out value="${customerPrice.goodsid }"></c:out>" text="<c:out value="${goodsname }"></c:out>" style="width: 200px;"/></td>
    		</tr>
    		<tr>
    			<td>条形码:</td>
    			<td><input type="text" id="sales-barcode-priceCustomer" value="<c:out value="${customerPrice.barcode }"></c:out>" style="width: 200px;" disabled="disabled"/>
    				<input type="hidden" id="sales-hdbarcode-priceCustomer" name="customerPrice.barcode" value="<c:out value="${customerPrice.barcode }"></c:out>"/>
    			</td>
    		</tr>
    		<tr>
    			<td>店内码:</td>
    			<td><input type="text" name="customerPrice.shopid" style="width: 200px;" value="<c:out value="${customerPrice.shopid }"></c:out>"/></td>
    		</tr>
    		<tr>
    			<td>价格套价格:</td>
    			<td><input type="text" id="sales-taxprice-priceCustomer" style="width: 200px;" value="${customerPrice.taxprice }" class="easyui-numberbox" data-options="precision:6,groupSeparator:','" disabled="disabled"/>
    				<input type="hidden" id="sales-hdtaxprice-priceCustomer" name="customerPrice.taxprice" value="${customerPrice.taxprice }"/>
    			</td>
    		</tr>
            <tr>
                <td>价格套箱价:</td>
                <td><input type="text" id="sales-boxprice-priceCustomer" style="width: 200px;" value="${customerPrice.boxprice }" class="easyui-numberbox" data-options="precision:2,groupSeparator:','" disabled="disabled"/>
                </td>
            </tr>
    		<tr>
    			<td>含税合同价:</td>
    			<td><input type="text" id="sales-price-priceCustomer" name="customerPrice.price" value="${customerPrice.price }" validType="intOrFloat" style="width: 200px;"/></td>
    		</tr>
            <tr>
                <td>合同箱价:</td>
                <td><input type="text" id="sales-ctcboxprice-priceCustomer" name="customerPrice.ctcboxprice" value="${customerPrice.ctcboxprice}" validType="intOrFloat" style="width: 200px;"/></td>
            </tr>
    		<tr>
    			<td>未税合同价:</td>
    			<td><input type="text" id="sales-noprice-priceCustomer" name="customerPrice.noprice" value="${customerPrice.noprice }" validType="intOrFloat" style="width: 200px;"/></td>
    		</tr>
    		<tr>
    			<td>备注:</td>
    			<td><textarea style="width: 200px;height: 48px;" name="customerPrice.remark"><c:out value="${customerPrice.remark }"></c:out></textarea></td>
    		</tr>
    	</table>
    </form>
    <script type="text/javascript">
    $(function(){
    	$("#sales-customerWidget-priceCustomer").customerWidget({
				required:true
		});
		$("#sales-goodsWidget-priceCustomer").goodsWidget({
			param:[
				{field:'id',op:'notin',value:'${goodsStr}'}
			],
			required:true,
			onSelect:function(data){
				if(data.id == $("#customerPrice-goodsid").val()){
					return false;
				}
				var ret = pricecustmer_AjaxConn({goodsid:data.id,pricesort:"${pricesort}"},'basefiles/getTaxPrice.do');
				var retjson = $.parseJSON(ret);
				$("#sales-goodsid-priceCustomer").val(data.id);
				$("#sales-barcode-priceCustomer").val(data.barcode);
				$("#sales-hdbarcode-priceCustomer").val(data.barcode);
				if(null != retjson){
					$("#sales-taxrate-priceCustomer").val(retjson.taxrate);
					if("" == "${pricesort}"){
						$("#sales-taxprice-priceCustomer").numberbox('setValue',data.basesaleprice);
						$("#sales-hdtaxprice-priceCustomer").val(data.basesaleprice);
                        $("#sales-boxprice-priceCustomer").numberbox('setValue',data.basesaleprice);
					}else{
						$("#sales-taxprice-priceCustomer").numberbox('setValue',retjson.taxprice);
						$("#sales-hdtaxprice-priceCustomer").val(retjson.taxprice);
                        $("#sales-boxprice-priceCustomer").numberbox('setValue',retjson.boxprice);
					}
					$("#sales-price-priceCustomer").val('');
					$("#sales-noprice-priceCustomer").val('');
                    $("#sales-ctcboxprice-priceCustomer").val('');
				}
			},
			onClear:function(){
				$("#sales-taxrate-priceCustomer").val("");
				$("#sales-goodsid-priceCustomer").val("");
				$("#sales-taxprice-priceCustomer").numberbox('clear');
                $("#sales-boxprice-priceCustomer").numberbox('clear');
				$("#sales-hdtaxprice-priceCustomer").val("");
				$("#sales-barcode-priceCustomer").val("");
				$("#sales-hdbarcode-priceCustomer").val("");
				$("#sales-price-priceCustomer").val('');
				$("#sales-noprice-priceCustomer").val('');
			}
		});

		//含税合同价
		$("#sales-price-priceCustomer").change(function(){
			priceChange("1","#sales-price-priceCustomer");
		});
		//未税合同价
		$("#sales-noprice-priceCustomer").change(function(){
			priceChange("2","#sales-noprice-priceCustomer");
		});
		//合同箱价
		$("#sales-ctcboxprice-priceCustomer").change(function(){
			priceChange("3","#sales-ctcboxprice-priceCustomer");
		});

		function priceChange(type, id){
			var price = $(id).val();
			var taxrate = $("#sales-taxrate-priceCustomer").val();
			var boxnum = $("#sales-boxnum-priceCustomer").val();
			$.ajax({
				url:'basefiles/getCustomerPriceChanger.do',
				dataType:'json',
				async:false,
				type:'post',
				data:{type:type,price:price,taxrate:taxrate,boxnum:boxnum},
				success:function(json){
					$("#sales-price-priceCustomer").val(json.taxprice);
					$("#sales-noprice-priceCustomer").val(json.notaxprice);
					$("#sales-ctcboxprice-priceCustomer").val(json.ctcboxprice);
				}
			});


		}



	});
    </script>
  </body>
</html>
