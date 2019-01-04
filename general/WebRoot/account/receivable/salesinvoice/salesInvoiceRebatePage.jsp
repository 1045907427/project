<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>销售发票明细添加</title>
  </head>
  
  <body>
   	<form action="" method="post" id="account-form-salesInvoiceRebateAddPage">
   		<table  border="0" >
   			<tr>
   				<td>总金额:</td>
   				<td>
   					<input id="account-invoice-input-taxamount" type="text" style="width: 200px;" class="easyui-numberbox" data-options="min:0,precision:2,required: true" value="${salesInvoice.taxamount }" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td>返利比率:</td>
   				<td>
   					<input id="account-invoice-input-rebate" type="text" style="width: 200px;" name="rebate" class="easyui-numberbox" data-options="min:0,max:100,precision:2,required: true"/>%
   				</td>
   			</tr>
   			<tr>
   				<td>返利金额:</td>
   				<td>
   					<input id="account-invoice-input-amount" type="text" style="width:200px;" name="amount" class="easyui-numberbox" data-options="min:0,max:${salesInvoice.taxamount },precision:2,required: true"/>
   				</td>
   			</tr>
   			<tr>
   				<td>费用科目:</td>
   				<td>
   					<input id="account-rebate-subject" name="subject" style="width: 200px;" value="${customerPushBalance.subject }"/>
   				</td>
   			</tr>
   			<tr>
   				<td>备注:</td>
   				<td>
   					<textarea id="account-invoice-input-remark" style="width: 200px;height: 80px;" name="remark"></textarea>
   				</td>
   			</tr>
   		</table>
    </form>
   <script type="text/javascript">
   		var changeFlag = true;
   		$(function(){
   			$("#account-rebate-subject").widget({
    			name:'t_account_customer_push_balance',
	    		width:200,
				col:'subject',
				singleSelect:true
    		});
   			$("#account-invoice-input-rebate").numberbox({
   				onChange:function(newValue,oldValue){
   					if(newValue!=oldValue && changeFlag){
   						var taxamount = $("#account-invoice-input-taxamount").numberbox("getValue");
   	   					var amount = taxamount*newValue/100;
   	   					changeFlag = false;
   	   					$("#account-invoice-input-amount").numberbox("setValue",amount);
   					}else{
   						changeFlag = true;
   					}
   				}
   			});
   			$("#account-invoice-input-amount").numberbox({
   				onChange:function(newValue,oldValue){
   					if(newValue!=oldValue && changeFlag){
	   					var taxamount = $("#account-invoice-input-taxamount").numberbox("getValue");
	   					var rate = (newValue/taxamount)*100;
	   					changeFlag = false;
	   					$("#account-invoice-input-rebate").numberbox("setValue",rate);
   					}else{
   						changeFlag = true;
   					}
   				}
   			});
   		});
   </script>
  </body>
</html>
