<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>/系统参数/采购信息</title>
    <%@include file="/include.jsp" %>
    
  </head>
  
  <body>
					<form action="" method="post" id="system-form-syscodedetail">
						<table cellpadding="5px" cellspacing="5px" style="padding-top: 5px;padding-left: 5px;">
							<tr>
								<td align="right" style="width: 100px" id='ARRIVEDAYS'>生成到货日期：</td>
								<td>
									<input type="text" style="width: 180px" name='ARRIVEDAYS'>
								</td>
								
								<td align="right" style="width: 100px" id = 'IsPurchaseGoodsRepeat'>商品是否重复：</td>
								<td>
									<select style="width: 180px" name='IsPurchaseGoodsRepeat'>
										<option value='1'>重复</option>
										<option value='0'>不重复</option>
									</select>
								</td>
								
								<td align="right" style="width: 100px" id = 'PurchaseCGJHFXBPageDateSet'>同前日期设置：</td>
								<td>
									<select style="width: 180px" name='PurchaseCGJHFXBPageDateSet'>
										<option value='1'>同期时间与前期间隔一年</option>
										<option value='2'>同期时间与前期间隔一个月</option>
									</select>
								</td>
								
							</tr>
							
							
							<tr>
								<td align="right" style="width: 100px" id = 'PurchasePriceType'>采购取价方式：</td>
								<td>
									<select style="width: 180px" name='PurchasePriceType'>
										<option value='1'>最高采购价</option>
										<option value='2'>最新采购价 </option>
									</select>
								</td>
								<td align="right" style="width: 100px" id = 'ArrivalOrderAutoAuditByPurchaseEnter'>采购进货审核：</td>
								<td>
									<select style="width: 180px" name='ArrivalOrderAutoAuditByPurchaseEnter'>
										<option value='1'>手动审核</option>
										<option value='2'>自动审核 </option>
									</select>
								</td>
								
								<td align="right" style="width: 100px" id = 'IsPurchaseInvoiceHoldStatus'>发票是否暂存：</td>
								<td>
									<select style="width: 180px" name='IsPurchaseInvoiceHoldStatus'>
						 				<option value='1'>是</option>
										<option value='0'>否</option>
									</select>
								</td>
							</tr>
							
							<tr>
								<td align="right" style="width: 100px" id = 'IsAutoPurchaseReturnCheck'>退货自动验收：</td>
								<td>
									<select style="width: 180px" name='IsAutoPurchaseReturnCheck'>
						 				<option value='1'>是</option>
										<option value='0'>否</option>
									</select>
								</td>
							</tr>
							
						</table>
					</form>
	<script>
			$(function(){
			
				loadDetailData(3)
     			
			})
					
					
	</script>
  </body>
</html>
