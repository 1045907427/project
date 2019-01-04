<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>资金录入详情页面</title>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'center',border:true">
    		<div align="center">
    			<form action="" method="post" id="fundInput-form-add" style="padding: 10px;">
	    			<input type="hidden" id="journalsheet-hidden-supplierdeptid" name="fundInput.supplierdeptid" />
	    			<table cellpadding="2" cellspacing="2" border="0">
	    				<tr>
	    					<td width="60px" align="left">供应商:</td>
	    					<td colspan="3" align="left"><input id="journalsheet-widget-supplierid" disabled="disabled" type="text" name="fundInput.supplierid" value="${fundInput.supplierid }"/></td>
	    				</tr>
	    				<tr>
	    					<td width="60px" align="left">所属部门</td>
	    					<td align="left"><input id="journalsheet-widget-supplierdeptid" value="${fundInput.supplierdeptid }" disabled="disabled" type="text"/></td>
	    					<td width="60px" align="left">业务日期:</td>
	    					<td align="left"><input id="journalsheet-date-businesstime" type="text" name="fundInput.businessdate" disabled="disabled" value="${fundInput.businessdate }" class="easyui-validatebox" style="width: 150px" onclick="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd',minDate:'1958-01-01',maxDate:'%y-%M-%ld'})"/></td>
	    				</tr>
	    				<tr>
	    					<td width="60px" align="left">预付金额:</td>
	    					<td align="left"><input name="fundInput.advanceamount" value="${fundInput.advanceamount }" style="width: 150px" disabled="disabled" class="easyui-numberbox" data-options="precision:2,required:true,groupSeparator:',',"/></td>
	    					<td width="60px" align="left">库存金额:</td>
	    					<td align="left"><input name="fundInput.stockamount" value="${fundInput.stockamount}" style="width: 150px" disabled="disabled" class="easyui-numberbox" data-options="precision:2,required:true,groupSeparator:',',"/></td>
	    				</tr>
	    				<tr>
	    					<td width="60px" align="left">应收金额:</td>
	    					<td align="left"><input name="fundInput.receivableamount" value="${fundInput.receivableamount}" style="width: 150px" disabled="disabled" class="easyui-numberbox" data-options="precision:2,required:true,groupSeparator:',',"/></td>
	    					<td width="60px" align="left">代垫金额:</td>
	    					<td align="left"><input name="fundInput.actingmatamount" value="${fundInput.actingmatamount}" style="width: 150px" disabled="disabled" class="easyui-numberbox" data-options="precision:2,required:true,groupSeparator:',',"/></td>
	    				</tr>
	    				<tr>
	    					<td width="60px" align="left">应付金额:</td>
	    					<td align="left"><input id="journalsheet-number-payableamount" value="${fundInput.payableamount}" name="fundInput.payableamount" style="width: 150px" disabled="disabled" class="easyui-numberbox" data-options="precision:2,required:true,groupSeparator:',',"/></td>
							<td width="60px" align="left">代垫未收:</td>
							<td align="left"><input id="journalsheet-number-norecactingmat" value="${fundInput.norecactingmat}" name="fundInput.norecactingmat" style="width: 150px" disabled="disabled" class="easyui-numberbox" data-options="precision:2,required:true,groupSeparator:',',"/></td>
						</tr>
						<tr>
							<td width="60px" align="left">费用未付:</td>
							<td align="left"><input id="journalsheet-number-norecexpenses" value="${fundInput.norecexpenses}" name="fundInput.norecexpenses" style="width: 150px" disabled="disabled" class="easyui-numberbox" data-options="precision:2,required:true,groupSeparator:',',"/></td>
							<td width="60px" align="left">库存折差:</td>
							<td align="left"><input id="journalsheet-number-stockdiscount" value="${fundInput.stockdiscount}" name="fundInput.stockdiscount" style="width: 150px" disabled="disabled" class="easyui-numberbox" data-options="precision:2,required:true,groupSeparator:',',"/></td>
						</tr>
						<tr>
							<td width="60px" align="left">本期代垫:</td>
							<td align="left"><input id="journalsheet-number-currentactingmat" value="${fundInput.currentactingmat}" name="fundInput.currentactingmat" style="width: 150px" disabled="disabled" class="easyui-numberbox" data-options="precision:2,required:true,groupSeparator:',',"/></td>
							<td width="60px" align="left">汇款收回:</td>
							<td align="left"><input id="journalsheet-number-remittancerecovery" value="${fundInput.remittancerecovery}" name="fundInput.remittancerecovery" style="width: 150px" disabled="disabled" class="easyui-numberbox" data-options="precision:2,required:true,groupSeparator:',',"/></td>
						</tr>
						<tr>
							<td width="60px" align="left">货补收回:</td>
							<td align="left"><input id="journalsheet-number-goodsrecovery" value="${fundInput.goodsrecovery}" name="fundInput.goodsrecovery" style="width: 150px" disabled="disabled" class="easyui-numberbox" data-options="precision:2,required:true,groupSeparator:',',"/></td>
						</tr>
	    				<tr>
	    					<td width="60px" align="left">备注:</td>
	    					<td colspan="3" align="left">
	    						<textarea name="fundInput.remark" readonly="readonly" class="easyui-validatebox" validType="maxLen[200]" style="height:50px;width: 366px;">${fundInput.remark }</textarea>
	    					</td>
	    				</tr>
	    			</table>
	    		</form>
    		</div>
    	</div>
    </div>
    <script type="text/javascript">
    	$(function(){
    		//供应商
		  	$("#journalsheet-widget-supplierid").widget({
		  		width:372,
				referwid: 'RL_T_BASE_BUY_SUPPLIER_1',
				singleSelect:true,
				onlyLeafCheck:false,
				initValue:'${fundInput.supplierid}'
			});
			
			//所属部门
		  	$("#journalsheet-widget-supplierdeptid").widget({
		  		width:150,
				referwid: 'RL_T_BASE_DEPARTMENT_BUYER',
				singleSelect:true,
				onlyLeafCheck:false
			});
    		
    	});
    </script>
  </body>
</html>
