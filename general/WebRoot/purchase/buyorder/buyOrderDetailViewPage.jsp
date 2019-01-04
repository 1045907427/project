<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head>
  
  <body>
  	<form id="purchase-form-buyOrderDetailViewPage" method="post">
  		<table style="border-collapse:collapse;" border="0" cellpadding="5" cellspacing="5">
			<tr>
				<td style="text-align: right;">商品：</td>
				<td colspan="3">
					<input type="text" id="purchase-buyOrderDetail-goodsname"style="width:150px;border:1px solid #B3ADAB; background-color: #EBEBE4;" readonly="readonly" />
					<input type="hidden" name="goodsid"/>					
				</td>
				<td style="text-align: right;">商品编号：</td>
				<td>
					<input type="text" id="purchase-buyOrderDetail-goodsid" readonly="readonly" style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;"/>
				</td>				
			</tr>
			<tr>
				<td style="text-align: right;">辅数量：</td>
				<td>
					<input type="text" id="purchase-plannedOrderDetail-auxnum" name="auxnum" readyonly="readonly" style="width:60px; float:left;" />
  					<span id="purchase-plannedOrderDetail-span-auxunitname" style="float: left;line-height:25px;">&nbsp;</span>
  					<input type="text" id="purchase-plannedOrderDetail-unitnum-auxremainder" name="auxremainder" readyonly="readonly" style="width:60px;float:left;"/>
  					<span id="purchase-plannedOrderDetail-span-unitname" style="float: left;line-height:25px;">&nbsp;</span>
  					<input type="hidden" id="purchase-plannedOrderDetail-auxnumdetail" name="auxnumdetail" class="no_input" readonly="readonly"/>
				</td>
				<td style="text-align: right;">数量：</td>
				<td><input type="text" id="purchase-plannedOrderDetail-unitnum" name="unitnum" class="easyui-validatebox" validType="intOrFloatNum[${decimallen}]" data-options="required:true" style="width:150px;"/></td>
			</tr>
			<tr>
   				<td style="text-align: right;">单位：</td>
	    		<td style="float: left;">
	    			主：<input id="purchase-buyOrderDetail-auxunitname" name="unitname" type="text" class="readonly2" style="width: 48px;" readonly="readonly" /><input id="purchase-buyOrderDetail-auxunitid" type="hidden" name="unitid" />
	    			辅：<input id="purchase-buyOrderDetail-unitname" name="auxunitname" type="text" class="readonly2" style="width: 48px;" readonly="readonly" /><input id="purchase-buyOrderDetail-unitid" type="hidden" name="auxunitid" />
	    		</td>
	    		<td style="text-align: right;">箱装量：</td>
	    		<td>
	    			<input id="purchase-buyOrderDetail-boxnum" type="text" class="len150 readonly" readonly="readonly" />
	    		</td>
   			</tr>
			<tr>
				<td style="text-align: right;">商品品牌：</td>
				<td><input type="text" id="purchase-buyOrderDetail-brand" readonly="readonly" style="width:150px;border:1px solid #B3ADAB; background-color: #EBEBE4;"/></td>
				<td style="text-align: right;">商品规格：</td>
				<td><input type="text" id="purchase-buyOrderDetail-model" readonly="readonly" style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;" /></td>
			</tr>
			<tr>
				<td style="text-align: right;">税种：</td>
				<td>
					<input type="text" id="purchase-buyOrderDetail-taxtypename" name="taxtypename" readonly="readonly"  style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;"/>
				</td>
				<td style="text-align: right;">税额：</td>
				<td><input type="text" id="purchase-buyOrderDetail-tax" name="tax" class="easyui-numberbox" data-options="precision:6" readonly="readonly" style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;"/></td>
			</tr>
			<tr>
				<td style="text-align: right;">含税单价：</td>
				<td><input type="text" id="purchase-buyOrderDetail-taxprice" name="taxprice" readyonly="readonly" data-options="precision:6" style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;" /></td>
				<td style="text-align: right;">含税金额：</td>
				<td><input type="text" id="purchase-buyOrderDetail-taxamount" name="taxamount" readonly="readonly" data-options="precision:6" style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;"/></td>
			</tr>
			<tr>
				<td style="text-align: right;">未税单价：</td>
				<td><input type="text" id="purchase-buyOrderDetail-notaxprice" name="notaxprice" readonly="readonly" data-options="precision:6" style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;" /></td>
				<td style="text-align: right;">未税金额：</td>
				<td><input type="text" id="purchase-buyOrderDetail-notaxamount" name="notaxamount" readonly="readonly" data-options="precision:6" style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;"/></td>
			</tr>
			<tr>
				<td style="text-align: right;">含税箱价：</td>
				<td><input type="text" id="purchase-buyOrderDetail-boxprice" name="boxprice" readonly="readonly" style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;"/></td>
				<td style="text-align: right;">未税箱价：</td>
				<td><input type="text" id="purchase-buyOrderDetail-noboxprice" name="noboxprice" readonly="readonly" style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;"/></td>
			</tr>
			<tr>
				<td style="text-align: right;">条形码：</td>
				<td><input type="text" id="purchase-buyOrderDetail-barcode"  readonly="readonly" style="width:150px;  border:1px solid #B3ADAB; background-color: #EBEBE4;"/></td>
				<td style="text-align: right;">要求到货日期：</td>
				<td><input type="text" id="purchase-buyOrderDetail-arrivedate" name="arrivedate" readonly="readonly" style="width:150px;  border:1px solid #B3ADAB; background-color: #EBEBE4;"/></td>
			</tr>
			<tr>
				<td style="text-align: right;">备注：</td>
				<td colspan="3">
					<input type="text" name="remark" style="width:405px; border:1px solid #B3ADAB; background-color: #EBEBE4;" readonly="readonly"/>
				</td>
			</tr>
		</table>		
		<input type="hidden" id="purchase-buyOrderDetail-id" name="id" />
		<input type="hidden" name="goodsfield01" />
		<input type="hidden" name="goodsfield02" />
		<input type="hidden" name="goodsfield03" />
		<input type="hidden" name="goodsfield04" />
		<input type="hidden" name="goodsfield05" />
		<input type="hidden" name="goodsfield06" />
		<input type="hidden" name="goodsfield07" />
		<input type="hidden" name="goodsfield08" />
		<input type="hidden" name="goodsfield09" />
		<input type="hidden" id="purchase-buyOrderDetail-boxnum" name="boxnum" />
  	</form>
  </body>
</html>
