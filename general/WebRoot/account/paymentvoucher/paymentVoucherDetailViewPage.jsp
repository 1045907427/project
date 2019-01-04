<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>编辑交款单明细</title>
  </head>
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
  		<div data-options="region:'center',border:false">
		  	<form id="account-form-paymentVoucherDetailViewPage" method="post">
		  		<table style="border-collapse:collapse;" border="0" cellpadding="5" cellspacing="5">
		  			<tr>
						<td style="height:10px;" colspan="2">&nbsp;</td>	
					</tr>
					<tr>
						<td style="text-align: right;width:80px;">客户：</td>
						<td>
							<input type="text" id="account-paymentVoucherDetail-customername" name="customername" readonly="readonly" style="width:250px;" tabindex="1"/>
						</td>		
					</tr>
					<tr>
						<td style="text-align: right;">金额：</td>
						<td><input type="text" id="account-paymentVoucherDetail-amount" name="amount" class="easyui-numberbox" data-options="disabled:true,precision:6" readonly="readonly" style="width:250px; border:1px solid #B3ADAB; background-color: #EBEBE4;" tabindex="2"/></td>
					</tr>
					<tr>
						<td style="text-align: right;">备注：</td>
						<td>
							<textarea rows="0" cols="0" id="account-paymentVoucherDetail-remark" name="remark" style="width:250px;height:60px;" readonly="readonly" ></textarea>
						</td>
					</tr>
				</table>
		  	</form>		  	
  		</div>
  	</div>
  </body>
</html>
