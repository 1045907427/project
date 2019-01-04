<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head>
  
  <body>
  	<form id="purchase-form-arrivalOrderDetailViewPage" method="post">
  		<table style="border-collapse:collapse;" border="0" cellpadding="5" cellspacing="5">
			<tr>
				<td style="text-align: right;">商品：</td>
				<td>
					<input type="text" id="purchase-arrivalOrderDetail-goodsname" name="name" style="width:150px;border:1px solid #B3ADAB; background-color: #EBEBE4;" readonly="readonly" />
					<input type="hidden" name="goodsid"/>	
				</td>
				<td style="text-align: right;">商品编号：</td>
				<td>
					<input type="text" id="purchase-arrivalOrderDetail-goodsid" readonly="readonly" style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;"/>
				</td>					
			</tr>
			<tr>
				<td>辅数量：</td>
	   			<td style="text-align: left;">
   					<input type="text" id="purchase-arrivalOrderDetail-auxnum" name="auxnum" readonly="readonly" style="width:60px; float:left;border:1px solid #B3ADAB; background-color: #EBEBE4;" />
   					<span id="purchase-arrivalOrderDetail-span-auxunitname" style="float: left;line-height:25px;">&nbsp;</span>
   					<input type="text" id="purchase-arrivalOrderDetail-unitnum-auxremainder" name="auxremainder" readonly="readonly" style="width:60px;float:left;border:1px solid #B3ADAB; background-color: #EBEBE4;"/>
   					<span id="purchase-arrivalOrderDetail-span-unitname" style="float: left;line-height:25px;">&nbsp;</span>
   					<input type="hidden" id="purchase-arrivalOrderDetail-auxnumdetail" name="auxnumdetail" class="no_input" readonly="readonly"/>
	   			</td>
				<td style="text-align: right;">数量：</td>
				<td><input type="text" id="purchase-arrivalOrderDetail-unitnum" name="unitnum" readonly="readonly" style="width:150px;border:1px solid #B3ADAB; background-color: #EBEBE4;" /></td>				
			</tr>
			<tr>
				<td style="text-align: right;">辅计量：</td>
				<td><input type="text" id="purchase-arrivalOrderDetail-auxunitname" name="auxunitname" readonly="readonly" style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;"/><input type="hidden" id="purchase-arrivalOrderDetail-auxunitid" name="auxunitid"  style="width:150px;"/></td>
				<td style="text-align: right;">主单位：</td>
				<td><input type="text" id="purchase-arrivalOrderDetail-unitname" name="unitname" readonly="readonly"  style="width:150px;border:1px solid #B3ADAB; background-color: #EBEBE4;"/><input type="hidden" id="purchase-arrivalOrderDetail-unitid" name="unitid"  style="width:150px;"/></td>				
			</tr>
			<tr>
				<td style="text-align: right;">商品品牌：</td>
				<td><input type="text" id="purchase-arrivalOrderDetail-brand" name="brandName" readonly="readonly" style="width:150px;border:1px solid #B3ADAB; background-color: #EBEBE4;"/></td>
				<td style="text-align: right;">商品规格：</td>
				<td><input type="text" id="purchase-arrivalOrderDetail-model" name="model" readonly="readonly" style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;" /></td>
			</tr>
			<tr>
				<td style="text-align: right;">税种：</td>
				<td>
					<input type="text" id="purchase-arrivalOrderDetail-taxtypename" name="taxtypename" readonly="readonly"  style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;"/>
				</td>
				<td style="text-align: right;">税额：</td>
				<td><input type="text" id="purchase-arrivalOrderDetail-tax" name="tax" class="easyui-numberbox" data-options="precision:6" readonly="readonly" style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;"/></td>
			</tr>
			<tr>
				<td style="text-align: right;">含税单价：</td>
				<td><input type="text" id="purchase-arrivalOrderDetail-taxprice" name="taxprice" readonly="readonly" data-options="precision:6"  style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;" /></td>
				<td style="text-align: right;">含税金额：</td>
				<td><input type="text" id="purchase-arrivalOrderDetail-taxamount" name="taxamount" readonly="readonly" data-options="precision:6" style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;"/></td>
			</tr>
			<tr>
				<td style="text-align: right;">未税单价：</td>
				<td><input type="text" id="purchase-arrivalOrderDetail-notaxprice" name="notaxprice" readonly="readonly" data-options="precision:6" style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;" /></td>
				<td style="text-align: right;">未税金额：</td>
				<td><input type="text" id="purchase-arrivalOrderDetail-notaxamount" name="notaxamount" readonly="readonly" data-options="precision:6" style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;"/></td>
			</tr>
			<tr>
				<td style="text-align: right;">条形码：</td>
				<td><input type="text" id="purchase-arrivalOrderDetail-barcode" name="barcode" readonly="readonly" style="width:150px;  border:1px solid #B3ADAB; background-color: #EBEBE4;"/></td>
				<td style="text-align: right;">批次号：</td>
				<td><input type="text" id="purchase-arrivalOrderDetail-batchno" name="batchno" readonly="readonly" style="width:150px;  border:1px solid #B3ADAB; background-color: #EBEBE4;" /></td>
			</tr>
			<tr>
				<td style="text-align: right;">生产日期：</td>
				<td><input type="text" id="purchase-arrivalOrderDetail-produceddate" name="produceddate" readonly="readonly" style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;" tabindex="4"/></td>
				<td style="text-align: right;">有效截止日期：</td>
				<td><input type="text" id="purchase-arrivalOrderDetail-deadline" name="deadline" readonly="readonly" style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;" tabindex="5"/></td>
			</tr>
			<tr>
				<td style="text-align: right;">备注：</td>
				<td colspan="3">
					<input type="text" id="purchase-arrivalOrderDetail-remark" name="remark" style="width:420px; border:1px solid #B3ADAB; background-color: #EBEBE4;" readonly="readonly"/>
				</td>
			</tr>
		</table>		
		<input type="hidden" id="purchase-arrivalOrderDetail-id" name="id" />
		<input type="hidden" name="goodsfield01" />
		<input type="hidden" name="goodsfield02" />
		<input type="hidden" name="goodsfield03" />
		<input type="hidden" name="goodsfield04" />
		<input type="hidden" name="goodsfield05" />
		<input type="hidden" name="goodsfield06" />
		<input type="hidden" name="goodsfield07" />
		<input type="hidden" name="goodsfield08" />
		<input type="hidden" name="goodsfield09" />
  	</form>

  </body>
</html>
