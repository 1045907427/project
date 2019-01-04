<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>/系统参数/打印信息</title>
    <%@include file="/include.jsp" %>
    
  </head>
  
  <body>
					<form action="" method="post" id="system-form-syscodedetail">
						<table cellpadding="5px" cellspacing="5px" style="padding-top: 5px;padding-left: 5px;">
							<tr>
								<td align="right" style="width: 140px" id='PrintOnceLimitBILL'>单据打印次数限制：</td>
								<td>
									<input type="text" style="width: 180px" name='PrintOnceLimitBILL'>
								</td>
								
								<td align="right" style="width: 140px" id = 'printNoLimitBILL'>不限制打印次数：</td>
								<td>
									<input type="text" style="width: 180px" name='printNoLimitBILL'>
								</td>
								
								<td align="right" style="width: 140px" id = 'CompanyTelForSalesPrint'>销售单上的联系电话：</td>
								<td>
									<input type="text" style="width: 180px" name='CompanyTelForSalesPrint'>
								</td>
							</tr>
							
							<tr>
								<td align="right" style="width: 140px" id='printnum'>打印纸张明细条数：</td>
								<td>
									<input type="text" style="width: 180px" name='printnum'>
								</td>
								
								<td align="right" style="width: 140px" id = 'FHDPrintTimesSyncUpOrder'>发货单打印次数更新：</td>
								<td>
									<select style="width: 180px" name='FHDPrintTimesSyncUpOrder'>
										<option value='0'>否</option>
										<option value='1'>是</option>
									</select>
								</td>
								
								<td align="right" style="width: 140px" id = 'saleOrderPrintSplitType'>销售单据打印是否需要拆单：</td>
								<td>
									<select style="width: 180px" name='saleOrderPrintSplitType'>
										<option value='0'>普通</option>
										<option value='1'>按仓库整散分离</option>
										<option value='2'>按指定客户进行品牌拆单</option>
									</select>
								</td>
								
							</tr>
							
							<tr>
								<td align="right" style="width: 140px" id='orderIsShowWholeInMutiByStorage'>订单明细按整散分离分单：</td>
								<td>
									<input type="text" style="width: 180px" id='orderIsShowWholeInMutiByStorageNAME'name='orderIsShowWholeInMutiByStorage'>
								</td>
								
								<td align="right" style="width: 140px" id = 'orderIsShowWholeByStorage'>订单明细按整散分离不分单：</td>
								<td>
									<input type="text" style="width: 180px" id='orderIsShowWholeByStorageNAME'name='orderIsShowWholeByStorage'>
								</td>
								
								<td align="right" style="width: 140px" id = 'saleOrderPrintBrandSplitByCustomer'>指定客户按品牌拆分：</td>
								<td>
									<input type="text" style="width: 180px" id='saleOrderPrintBrandSplitByCustomerNAME'name='saleOrderPrintBrandSplitByCustomer'>
								</td>
							</tr>
							
							
							<tr>
								<td align="right" style="width: 140px" id='saleOrderFHPrintAfterSaleOutAudit'>发货单审核后打印：</td>
								<td>
									<select style="width: 180px" name='saleOrderFHPrintAfterSaleOutAudit'>
										<option value='0'>不需要</option>
										<option value='1'>需要</option>
									</select>
								</td>
								
								<td align="right" style="width: 140px" id = 'showSaleoutPrintOptions'>销售单打印选项：</td>
								<td>
									<select style="width: 180px" name='showSaleoutPrintOptions'>
										<option value='0'>不显示</option>
										<option value='1'>显示</option>
									</select>
								</td>
								
								<td align="right" style="width: 140px" id = 'showSaleTHTZDPrintOptions'>销售退货通知单打印选项：</td>
								<td>
									<select style="width: 180px" name='showSaleTHTZDPrintOptions'>
										<option value='0'>不显示</option>
										<option value='1'>显示</option>
									</select>
								</td>
								
								
							</tr>
							
							
							<tr>
								<td align="right" style="width: 140px" id = 'showSaleTHRKDPrintOptions'>销售退货入库单打印选项：</td>
								<td>
									<select style="width: 180px" name='showSaleTHRKDPrintOptions'>
										<option value='0'>不显示</option>
										<option value='1'>显示</option>
									</select>
								</td>
								<td align="right" style="width: 140px" id = 'isPrintShowCustomerYSKInfo'>销售单客户应收款信息：</td>
								<td>
									<select style="width: 180px" name='isPrintShowCustomerYSKInfo'>
										<option value='0'>不显示</option>
										<option value='1'>显示</option>
									</select>
								</td>
							
							</tr>
							
							
							
							
							
							
						</table>
					</form>
	<script>
			$(function(){
			
				loadDetailData(7)
				//订单明细按整散分离分单
				$("#orderIsShowWholeInMutiByStorageNAME").widget({
		     		width:180,
					referwid:'RL_T_BASE_STORAGE_INFO',
					singleSelect:false,
					onlyLeafCheck:false
     			});
				
				
				//订单明细按整散分离不分单
				$("#orderIsShowWholeByStorageNAME").widget({
		     		width:180,
					referwid:'RL_T_BASE_STORAGE_INFO',
					singleSelect:false,
					onlyLeafCheck:false
     			});
				
				//指定客户按品牌拆分
     			$("#saleOrderPrintBrandSplitByCustomerNAME").widget({
		     		width:180,
					referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT_2',
					singleSelect:false,
					onlyLeafCheck:false
     			});
			})
					
					
	</script>
					
					
					
  </body>
</html>
