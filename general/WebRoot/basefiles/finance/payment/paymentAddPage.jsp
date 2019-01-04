<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>结算方式新增页面</title>
  </head>
  
  <body>
    <form action="" method="post" id="payment-form-add">
    	<table cellpadding="2px" cellspacing="2px" border="0px" style="padding: 20px;">
    		<tr>
    			<td style="width: 80px;" align="left">编码:</td>
    			<td align="left"><input id="finance-id-payment" type="text" name="payment.id" class="easyui-validatebox" validType="validID[20]" required="true" style="width:200px;" maxlength="20"/></td>
    		</tr>
    		<tr>
    			<td align="left">名称:</td>
    			<td align="left"><input type="text" name="payment.name" class="easyui-validatebox" required="true" style="width:200px;" validType="validName[50]" maxlength="50"/></td>
    		</tr>
    		<tr>
    			<td align="left">关联银行:</td>
    			<td align="left"><select name="payment.relevancebank" style="width:206px;">
    				<option value="0" selected="selected">否</option>
    				<option value="1">是</option>
    			</select></td>
    		</tr>
    		<tr>
    			<td align="left">备注:</td>
    			<td align="left"><textarea name="payment.remark" style="height: 100px;width: 200px;" class="easyui-validatebox" validType="maxLen[200]"></textarea></td>
    		</tr>
    		<tr>
    			<td align="left">状态:</td>
    			<td align="left"><select name="payment.state" style="width:206px;" disabled="disabled">
					<option value="4" selected="selected">新增</option>
				</select></td>
    		</tr>
    	</table>
    </form>
    <script type="text/javascript">
    	//新增销售方式
    	function addPayment(type){
    		if(type == "save"){
    			if(!$("#payment-form-add").form('validate')){
    				return false;
    			}
    		}
    		var ret = payment_ajax($("#payment-form-add").serializeJSON(),'basefiles/finance/addPayment.do?type='+type);
    		var retJson = $.parseJSON(ret);
    		if(retJson.flag){
    			$("#payment-table-list").datagrid('reload');
    			refreshLayout("支付方式【详情】",'basefiles/finance/paymentViewPage.do?id='+$("#finance-id-payment").val());
    			if("save" == type ){
    				$.messager.alert("提醒","保存成功!");
    			}
    			else{
    				$.messager.alert("提醒","暂存成功!");
    			}
    		}
    		else{
    			if("save" == type ){
    				$.messager.alert("提醒","保存失败!");
    			}
    			else{
    				$.messager.alert("提醒","暂存失败!");
    			}
    		}
    	}
    	
    	$(function(){
    		$("#payment-button").buttonWidget("initButtonType","add");
    		
    	});
    </script>
  </body>
</html>
