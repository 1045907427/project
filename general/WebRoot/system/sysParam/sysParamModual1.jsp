<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
  <head>
    <title>/系统参数/基础信息</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
					<form action="" method="post" id="system-form-syscodedetail">
						<table cellpadding="5px" cellspacing="5px" style="padding-top: 5px;padding-left: 5px;">
							<tr>
								<td align="right" style="width: 100px" id='COMPANYNAME'>公司名称：</td>
								<td>
									<input type="text" style="width: 180px" name='COMPANYNAME'>
								</td>
								
								<td align="right" style="width: 100px" id = 'INVOICETITLE'>公司发票抬头：</td>
								<td>
									<input type="text" style="width: 180px" name='INVOICETITLE'>
								</td>
								
								<td align="right" style="width: 100px" id = 'SETTLETYPE'>默认结算方式：</td>
								<td>
									<input type="text" style="width: 180px" id='SETTLETYPENAME' name='SETTLETYPE'>
								</td>
								
								
							</tr>
							
							
							<tr>
								<td align="right" style="width: 100px" id = 'DEFAULTAXTYPE'>默认税种：</td>
								<td>
									<input type="text" style="width: 180px" id = 'DEFAULTAXTYPENAME' name='DEFAULTAXTYPE'>
								</td>
							
								<td align="right" style="width: 100px" id = 'amountDecimalsLength'>金额小数位：</td>
								<td>
									<input type="text" style="width: 180px" name='amountDecimalsLength'>
								</td>
								
								<td align="right" style="width: 100px" id = 'BrandUserRoleName'>品牌角色名称：</td>
								<td>
									<input type="text" style="width: 180px" id= 'BrandUserRoleNameNAME' name='BrandUserRoleName'>
								</td>
								
								
							</tr>
							
							
							<tr>
								<td align="right" style="width: 100px" id = 'NOREPEATEMPLOYE'>人员属性多选：</td>
								<td>
									<input type="text" style="width: 180px"   id= 'NOREPEATEMPLOYENAME' name='NOREPEATEMPLOYE'>
								</td>
							
								<td align="right" style="width:100px" id = 'DEFAULTAUXUNITID'>默认辅单位：</td>
								<td>
									<input type="text" style="width: 180px"  id= 'DEFAULTAUXUNITIDNAME' name='DEFAULTAUXUNITID'>
								</td>
								<td align="right" style="width: 100px" id = 'useEmailListUIType'>邮件列表显示：</td>
								<td>
									<select style="width: 180px" name='useEmailListUIType'>
										<option value='1'>普通列表</option>
										<option value='0'>带有预览的列表</option>
									</select>
								</td>
							</tr>
							
							<tr>
								<td align="right" style="width: 100px" id = 'isAutoCloseAccounting'>是否自动关账：</td>
								<td>
									<select style="width: 180px" name='isAutoCloseAccounting'>
										<option value='0'>否</option>
										<option value='1'>是</option>
									</select>
								</td>
								<td align="right" style="width: 100px" id = 'isOpenAccounting'>启用会计区间：</td>
								<td>
									<select style="width: 180px" name='isOpenAccounting'>
										<option value='1'>启用</option>
										<option value='0'>禁用</option>
									</select>
								</td>
								<td align="right" style="width: 100px" id = 'EMPLOYEEOVERTIMEPROCESSNAME'>加班流程名称：</td>
								<td>
									<input type="text" style="width: 180px" name='EMPLOYEEOVERTIMEPROCESSNAME'>
								</td>
								
							</tr>
							
							<tr>
								<td align="right" style="width: 100px" id = 'DDSZSRAmountUseHXOrSH'>代垫收支情况：</td>
								<td>
									<select style="width: 180px" name='DDSZSRAmountUseHXOrSH'>
										<option value='1'>代垫核销金额</option>
										<option value='2'>代垫收回金额</option>
									</select>
								</td>
								
								
								<td align="right" style="width: 100px" id = 'EMPLOYEERESTPROCESSNAME'>请假流程名称：</td>
								<td>
									<input type="text" style="width: 180px" name='EMPLOYEERESTPROCESSNAME'>
								</td>
								
								<td align="right" style="width: 100px" id = 'StorageInitPrice'>库存金额更新：</td>
								<td>
									<select style="width: 180px" name='StorageInitPrice'>
										<option value='0'>不更新</option>
										<option value='1'>更新</option>
									</select>
								</td>
							</tr>
							
							<tr>
								
								<td align="right" style="width: 100px" id = 'doEditCustomerTask'>历史单据更新：</td>
								<td>
									<select style="width: 180px" name='doEditCustomerTask'>
										<option value='0'>否</option>
										<option value='1'>是</option>
									</select>
								</td>
								
								
							</tr>
						</table>
					</form>
		<script>
		
			$(function(){
			
				loadDetailData(1)
				//默认结算方式
				$("#SETTLETYPENAME").widget({
		     			width:180,
						referwid:'RL_T_BASE_FINANCE_SETTLEMENT',
						singleSelect:true,
						onlyLeafCheck:false
     			});
     			//默认税种
     			$("#DEFAULTAXTYPENAME").widget({
		     			width:180,
						referwid:'RL_T_BASE_FINANCE_TAXTYPE',
						singleSelect:true,
						onlyLeafCheck:false
     			});
     			
     			//人员属性多选  系统编码
     			$("#NOREPEATEMPLOYENAME").widget({
		     			width:180,
						referwid:'RL_T_SYS_CODE_ENABLE',
						singleSelect:false,
						onlyLeafCheck:false,
						param:[{field:'type',op:'equal',value:'employetype'}]
     			});
     			
     			
     			//品牌角色名称
//      			$("#BrandUserRoleNameNAME").widget({
// 		     			width:180,
// 						referwid:'RL_T_AC_AUTHORITY',
// 						singleSelect:true,
// 						onlyLeafCheck:false
//      			});
     			
     			
     			//默认辅单位
     			$("#DEFAULTAUXUNITIDNAME").widget({
		     		width:180,
					referwid:'RL_T_BASE_GOODS_METERINGUNIT',
					singleSelect:true,
					onlyLeafCheck:false
     			});
     			
			})
			
			
			
		
		
		</script>			
  </body>
</html>
