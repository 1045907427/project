<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>/系统参数/销售信息</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
  
  	<script src="js/syscode.js"></script>
  	
					<form action="" method="post" id="system-form-syscodedetail">
						<table cellpadding="5px" cellspacing="5px" style="padding-top: 5px;padding-left: 5px;margin-bottom: 80px">
							<th>零售业务</th>
							<tr>
								<td align="right" style="width: 140px" id = 'OrderCarAuditAuto'>车销自动审核：</td>
								<td>
									<select style="width: 180px" name='OrderCarAuditAuto'>
										<option value='1'>是</option>
										<option value='0'>否</option>
									</select>
								</td>
							</tr>
							<th>销售业务</th>	
							<tr>
								<td align="right" style="width: 140px" id='IsDemandSplitByDept'>要货单拆分：</td>
								<td>
									<select style="width: 180px" name='IsDemandSplitByDept'>
										<option value='1'>是</option>
										<option value='0'>否</option>
									</select>
								</td>
								
								<td align="right" style="width: 140px" id = 'IsSalesGoodsRepeat'>订单商品重复：</td>
								<td>
									<select style="width: 180px" name='IsSalesGoodsRepeat'>
										<option value='1'>允许</option>
										<option value='0'>不允许</option>
									</select>
								</td>
								
								
								<td align="right" style="width: 140px" id = 'IsDispatchProcessUse'>销售发货通知：</td>
								<td>
									<select style="width: 180px" name='IsDispatchProcessUse'>
										<option value='1'>是</option>
										<option value='0'>否</option>
									</select>
								</td>
							</tr>
							
							<tr>
								<td align="right" style="width: 140px" id='checkOrderRepeatDays'>订单是否重复:</td>
								<td>
									<input type="text" style="width: 180px" name='checkOrderRepeatDays'>
								</td>
								
								<td align="right" style="width: 140px" id = 'IsZeroOrderAudit'>商品明细审核：</td>
								<td>
									<select style="width: 180px" name='IsZeroOrderAudit'>
										<option value='1'>是</option>
										<option value='0'>否</option>
									</select>
								</td>
								
								<td align="right" style="width: 140px" id = 'OrderDetailSortGoods'>商品明细排序：</td>
								<td>
									<select style="width: 180px" name='OrderDetailSortGoods'>
										<option value='1'>是</option>
										<option value='0'>否</option>
									</select>
								</td>
							</tr>
							
							<tr>
								<td align="right" style="width: 140px" id='isCashOrderAudit'>现结客户下单:</td>
								<td>
									<select style="width: 180px" name='isCashOrderAudit'>
										<option value='1'>是</option>
										<option value='0'>否</option>
									</select>
								</td>
								
								
								<td align="right" style="width: 140px" id = 'IsOpenOrderVersion'>订单修改记录：</td>
								<td>
									<select style="width: 180px" name='IsOpenOrderVersion'>
										<option value='1'>是</option>
										<option value='0'>否</option>
									</select>
								</td>
								
								<td align="right" style="width: 140px" id = 'IsReceiptAuditAuto'>回单自动审核：</td>
								<td>
									<select style="width: 180px" name='IsReceiptAuditAuto'>
										<option value='1'>是</option>
										<option value='0'>否</option>
									</select>
								</td>
							</tr>
							
							<tr>
								<td align="right" style="width: 140px" id='IsSalesReceiptDirectWriteoff'>回单自动核销:</td>
								<td>
									<select style="width: 180px" name='IsSalesReceiptDirectWriteoff'>
										<option value='1'>是</option>
										<option value='0'>否</option>
									</select>
								</td>
								
								
								
								
								<td align="right" style="width: 140px" id = 'IsOrderStorageSelect'>销售仓库必选：</td>
								<td>
									<select style="width: 180px" name='IsOrderStorageSelect'>
										<option value='1'>是</option>
										<option value='0'>否</option>
									</select>
								</td>
								
								<td align="right" style="width: 140px" id='ScanSaleOutType'>扫描枪发货审核:</td>
								<td>
									<select style="width: 180px" name='ScanSaleOutType'>
										<option value='1'>直接发货审核出库</option>
										<option value='2'>更新核对状态</option>
									</select>
								</td>
							</tr>
							
							<tr>
								<td align="right" style="width: 140px" id='IsDeployStorageGoodsByBarcode'>条码追加替换:</td>
								<td>
									<select style="width: 180px" name='IsDeployStorageGoodsByBarcode'>
										<option value='1'>允许</option>
										<option value='0'>不允许</option>
									</select>
								</td>
								
								<td align="right" style="width: 140px" id = 'isSystemPrice'>导入取价方式：</td>
								<td>
									<select style="width: 180px" name='isSystemPrice'>
										<option value='1'>取系统价格</option>
										<option value='0'>取导入时模板价格</option>
									</select>
								</td>
								
								<td align="right" style="width: 140px" id = 'presentByZero'>赠品价格取价：</td>
								<td>
									<select style="width: 180px" name='presentByZero'>
										<option value='1'>赠品价格取原价</option>
										<option value='0'>赠品单价为0</option>
									</select>
								</td>
							</tr>
							
							<th>退货业务</th>	
							<tr>
								<td align="right" style="width: 140px" id = 'SalesRejectCustomerGoodsPrice'>退货取价方式：</td>
								<td>
									<select style="width: 180px" name='SalesRejectCustomerGoodsPrice'>
										<option value='0'>取默认价格</option>
										<option value='1'>最近一次交易价格</option>
										<option value='2'>最近一段时间的交易最低价格</option>
									</select>
								</td>
							
								<td align="right" style="width: 140px" id = 'RejectCustomerGoodsPriceInMonth'>退货取价时间：</td>
								<td>
									<input type="text" style="width: 180px" name='RejectCustomerGoodsPriceInMonth'>
								</td>
								
								<td align="right" style="width: 140px" id='IsAutoRejectCheck'>退货自动验收:</td>
								<td>
									<select style="width: 180px" name='IsAutoRejectCheck'>
										<option value='1'>是</option>
										<option value='0'>否</option>
									</select>
								</td>
							</tr>
							
							<tr>
							
								<td align="right" style="width: 140px" id = 'ReceiptAndRejectType'>发货回单直退：</td>
								<td>
									<select style="width: 180px" name='ReceiptAndRejectType'>
										<option value='1'>回单关联退货通知单</option>
										<option value='2'>回单改数量自动生成退货通知单</option>
									</select>
								</td>
								
								<td align="right" style="width: 140px" id = 'StatusOfAddDeliverySourceList'>配送单来源状态：</td>
								<td>
									<input type="text"  id='StatusOfAddDeliverySourceListNAME' name='StatusOfAddDeliverySourceList'>
								</td>
							
								<td align="right" style="width: 140px" id = 'DeliveryPriority'>配单优先权：</td>
								<td>
									<select style="width: 180px" name='DeliveryPriority'>
										<option value='1'>重量</option>
										<option value='2'>箱数</option>
										<option value='3'>体积</option>
									</select>
								</td>
							</tr>
							
							<th>财务管理</th>
							
							
							
							<tr>
							
								<td align="right" style="width: 140px" id = 'RECEIVABLETYPE'>应收日期计算：</td>
								<td>
									<select style="width: 180px" name='RECEIVABLETYPE'>
										<option value='1'>按发货日期计算</option>
										<option value='2'>按回单审核日期计算</option>
									</select>
								</td>
								
								<td align="right" style="width: 140px" id = 'collectionOrderOtherCustomer'>收款单其他客户挂款：</td>
								<td>
									<input type="text" style="width: 180px" id= "collectionOrderOtherCustomerNAME" name='collectionOrderOtherCustomer'>
								</td>
							
								<td align="right" style="width: 140px" id = 'positeTailAmountLimit'>正数尾差限制：</td>
								<td>
									<input type="text" style="width: 180px" name='positeTailAmountLimit'>
								</td>
							</tr>
							
							<tr>
							
								<td align="right" style="width: 140px" id = 'negateTailAmountLimit'>负数尾差限制：</td>
								<td>
									<input type="text" style="width: 180px" name='negateTailAmountLimit'>
								</td>
								
								<td align="right" style="width: 140px" id = 'pushstatus'>冲差是否暂存：</td>
								<td>
									<select style="width: 180px" name='pushstatus'>
										<option value='1'>启用</option>
										<option value='0'>禁用</option>
									</select>
								</td>
							
							</tr>
							
							
							<th>其他管理</th>
							
							<tr>
							
								<td align="right" style="width: 140px" id = 'CustomerGoodsBeginDate'>商品计算日期：</td>
								<td>
									<input type="text" style="width: 180px" id='CustomerGoodsBeginDateNAME' name='CustomerGoodsBeginDate'  class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})">
								</td>
								
								<td align="right" style="width: 140px" id = 'pricenum'>价格套显示数量：</td>
								<td>
									<input type="text" style="width: 180px" name='pricenum'>
								</td>
							
								<td align="right" style="width: 140px" id = 'SupplierUserRoleName'>厂家角色名称：</td>
								<td>
									<input type="text" style="width: 180px" name='SupplierUserRoleName'>
								</td>
							</tr>
							
							<tr>
							
								<td align="right" style="width: 140px" id = 'DefaultBankID'>默认银行名称：</td>
								<td>
									<input type="text" style="width: 180px"  id= 'DefaultBankIDNAME' name='DefaultBankID'>
								</td>
								
								<td align="right" style="width: 140px" id = 'OpenDeptStorage'>部门仓库关联：</td>
								<td>
									<select style="width: 180px" name='OpenDeptStorage'>
										<option value='1'>是</option>
										<option value='0'>否</option>
									</select>
								</td>
							
								<td align="right" style="width: 140px" id = 'UNJIAIDS'>供应商销售配置：</td>
								<td>
									<input type="text" style="width: 180px" name='UNJIAIDS'>
								</td>
							</tr>
							
							<tr>
								<td align="right" style="width: 140px" id = 'unauditDays'>进销存数据计算：</td>
								<td>
									<input type="text" style="width: 180px" name='unauditDays'>
								</td>
							</tr>
							
							<tr>
								<td align="right" style="width: 140px" id = 'DELIVERYORDERPRICE'>代配送取价：</td>
								<td>
									<select style="width: 180px" name='DELIVERYORDERPRICE'>
										<option value='0'>基准销售价</option>
										<option value='1'>客户合同价</option>
									</select>
								</td>
							</tr>
								
							
						</table>
					</form>
					
	<script>
			$(function(){
				loadDetailData(4)
				
				//新增配送单时来源单据的状态
     			$("#StatusOfAddDeliverySourceListNAME").widget({
		     			width:180,
						referwid:'RL_T_SYS_CODE_ENABLE',
						singleSelect:false,
						onlyLeafCheck:false,
						param:[{field:'type',op:'equal',value:'status'}]
     			});
	            
				//收款单其他客户挂款
				$("#collectionOrderOtherCustomerNAME").widget({
		     		width:180,
					referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT_2',
					singleSelect:true,
					onlyLeafCheck:false
     			});
     			
     			//银行档案
     			$("#DefaultBankIDNAME").widget({
		     		width:180,
					referwid:'RL_T_BASE_FINANCE_BANK',
					singleSelect:true,
					onlyLeafCheck:false
     			});
			})
					
					
	</script>
  </body>
</html>
