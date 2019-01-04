<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>/系统参数/库存信息</title>
    <%@include file="/include.jsp" %>
    
  </head>
  
  <body>
					<form action="" method="post" id="system-form-syscodedetail">
						<table cellpadding="5px" cellspacing="5px" style="padding-top: 5px;padding-left: 5px;">
							<tr>
								
								<td align="right" style="width: 140px" id = 'IsCheckListUseBatch'>盘点单是否使用批次盘点：</td>
								<td>
									<select style="width: 180px" name='IsCheckListUseBatch'>
						 				<option value='1'>是</option>
										<option value='0'>否</option>
									</select>
								</td>
								<td align="right" style="width: 140px" id = 'GoodsStorageDeployType'>商品发货选择仓库的方式：</td>
								<td>
									<select style="width: 180px" name='GoodsStorageDeployType'>
						 				<option value='1'>先按商品默认仓库发货，默认仓库不足发其他仓库</option>
										<option value='2'>按设置的默认仓库发货,默认仓库不足时，发其他仓库</option>
										<option value='3'>先按设置默认仓库发货，再发部门关联的仓库，最后发其他仓库</option>
									</select>
								</td>
								
								<td align="right" style="width: 140px" id= 'DefaultSendStorage'>默认发货仓，与参数GoodsStorageDeployType配合试用：</td>
								<td>
									<input type="text" style="width: 180px" id="DefaultSendStorageNAME" name='DefaultSendStorage'>
								</td>
							</tr>
							<th>物流奖金报表计算比例</th>
							<tr>
								<td align="right" style="width: 140px" id = 'LogisticsSalesSubsidy' title="司机：跟车">销售补助：</td>
								<td><input type="text" style="width: 180px" name="LogisticsSalesSubsidy" class="easyui-validatebox" data-options="validType:'numProportion'"/></td>
							</tr>
							<tr>
								<td align="right" style="width: 140px" id = 'LogisticsNightBonus' title="司机：跟车">晚上出车：</td>
								<td><input type="text" style="width: 180px" name="LogisticsNightBonus" class="easyui-validatebox" data-options="validType:'numProportion'"/></td>

								<td align="right" style="width: 140px" id = 'LogisticsSafeBonus' title="司机：跟车">安全奖金：</td>
								<td><input type="text" style="width: 180px" name="LogisticsSafeBonus" class="easyui-validatebox" data-options="validType:'numProportion'"/></td>

								<td align="right" style="width: 140px" id = 'LogisticsReceiptBonus' title="司机：跟车">回单奖金：</td>
								<td><input type="text" style="width: 180px" name="LogisticsReceiptBonus" class="easyui-validatebox" data-options="validType:'numProportion'"/></td>
							</tr>
							<tr>
								<td align="right" style="width: 140px" id = 'LogisticsOtherSubsidy' title="司机：跟车">其他补助：</td>
								<td><input type="text" style="width: 180px" name="LogisticsOtherSubsidy" class="easyui-validatebox" data-options="validType:'numProportion'"/></td>

								<td align="right" style="width: 140px" id = 'LogisticsCarAllowance' title="司机：跟车">出车津贴：</td>
								<td><input type="text" style="width: 180px" name="LogisticsCarAllowance" class="easyui-validatebox" data-options="validType:'numProportion'"/></td>

								<td align="right" style="width: 140px" id = 'LogisticsCarSubsidy' title="司机：跟车">出车补助：</td>
								<td><input type="text" style="width: 180px" name="LogisticsCarSubsidy" class="easyui-validatebox" data-options="validType:'numProportion'"/></td>
							</tr>
							<tr>
								<td align="right" style="width: 140px" id = 'LogisticsCustomerSubsidy' title="司机：跟车">家数补助：</td>
								<td><input type="text" style="width: 180px" name="LogisticsCustomerSubsidy" class="easyui-validatebox" data-options="validType:'numProportion'"/></td>

								<td align="right" style="width: 140px" id = 'LogisticsCollectionSubsidy' title="司机：跟车">收款补助：</td>
								<td><input type="text" style="width: 180px" name="LogisticsCollectionSubsidy" class="easyui-validatebox" data-options="validType:'numProportion'"/></td>

								<td align="right" style="width: 140px" id = 'LogisticsTruckSubsidy' title="司机：跟车">装车补助：</td>
								<td><input type="text" style="width: 180px" name="LogisticsTruckSubsidy" class="easyui-validatebox" data-options="validType:'numProportion'"/></td>
							</tr>
							<tr>
								<td align="right" style="width: 140px" id = 'collectionSurpassAmount'>超过收款总金额：</td>
								<td><input type="text" style="width: 180px" name='collectionSurpassAmount'></td>
								<td align="right" style="width: 140px" id = 'surpassRate'>超过率：</td>
								<td><input type="text" style="width: 180px" name='surpassRate'></td>
								<td align="right" style="width: 140px" id = 'unSurpassRate'>未超过率：</td>
								<td><input type="text" style="width: 180px" name='unSurpassRate'></td>
							</tr>
							<tr>
								<td align="right" style="width: 140px" id = 'salesSurpassAmount'>超过销售总金额：</td>
								<td><input type="text" style="width: 180px" name='salesSurpassAmount'></td>
								<td align="right" style="width: 140px" id = 'salesSurpassRate'>超过率：</td>
								<td><input type="text" style="width: 180px" name='salesSurpassRate'></td>
								<td align="right" style="width: 140px" id = 'salesUnSurpassRate'>未超过率：</td>
								<td><input type="text" style="width: 180px" name='salesUnSurpassRate'></td>
							</tr>
						</table>
					</form>
					
	<script>
			$(function(){
				loadDetailData(5)
				
				//默认发货仓，与参数GoodsStorageDeployType配合试用
				$("#DefaultSendStorageNAME").widget({
		     		width:180,
					referwid:'RL_T_BASE_STORAGE_INFO',
					singleSelect:false,
					onlyLeafCheck:false
     			});
				
				
			})
					
					
	</script>
  </body>
</html>
