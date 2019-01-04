<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>合同商品修改页面</title>
  </head>
  
  <body>
  	<form action="basefiles/editCustomerPriceAndGoodsMore.do" method="post" id="sales-form-priceCustomerMore">
    	<input type="hidden" name="customerids" value="${customerids }"/>
    	<input type="hidden" name="goodsid" value="<c:out value="${goodsid }"></c:out>"/>
    	<input type="hidden" id="sales-taxrate-priceCustomerMore" value="${taxrate }"/>
    	<table cellpadding="2" cellspacing="2" border="0">
    		<tr>
    			<td>商品编码:</td>
    			<td><input type="text" value="<c:out value="${goodsid }"></c:out>" style="width: 200px;" disabled="disabled"/></td>
    		</tr>
    		<tr>
    			<td>商品名称:</td>
    			<td><input type="text" value="<c:out value="${goodsInfo.name }"></c:out>" disabled="disabled" style="width: 200px;"/></td>
    		</tr>
    		<tr>
    			<td>条形码:</td>
    			<td><input type="text" value="<c:out value="${goodsInfo.barcode }"></c:out>" style="width: 200px;" disabled="disabled"/>
    			</td>
    		</tr>
    		<tr>
    			<td>含税合同价:</td>
    			<td><input type="text" id="sales-price-priceCustomer" name="customerPrice.price" style="width: 200px;"/></td>
    		</tr>
    		<tr>
    			<td>未税合同价:</td>
    			<td><input type="text" id="sales-noprice-priceCustomer" name="customerPrice.noprice" style="width: 200px;"/></td>
    		</tr>
    	</table>
    </form>
    <script type="text/javascript">
    $(function(){
        $("#sales-price-priceCustomer").numberbox({
            precision:6,
            groupSeparator:',',
            onChange:function(newValue,oldValue){
                var taxrate = $("#sales-taxrate-priceCustomerMore").val();
                if(Number(taxrate) != 0){
                    $("#sales-noprice-priceCustomer").numberbox('setValue',newValue/Number(taxrate));
                }
            }
        });

        $("#sales-noprice-priceCustomer").numberbox({
            precision:6,
            groupSeparator:',',
            onChange:function(newValue,oldValue){
                var taxrate = $("#sales-taxrate-priceCustomerMore").val();
                $("#sales-price-priceCustomer").numberbox('setValue',newValue*Number(taxrate));
            }
        });
    });
    </script>
  </body>
</html>
