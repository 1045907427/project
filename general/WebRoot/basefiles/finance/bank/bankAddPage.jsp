<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>银行档案新增页面</title>
  </head>
  
  <body>
    <form action="" method="post" id="bank-form-add">
    	<table cellpadding="2px" cellspacing="2px" border="0px" style="padding: 20px;">
    		<tr>
    			<td style="width: 80px;" align="left">编码:</td>
    			<td align="left"><input id="finance-id-bank" type="text" name="bank.id" class="easyui-validatebox" validType="validID[20]" required="true" style="width:200px;" maxlength="20"/></td>
    		</tr>
    		<tr>
    			<td align="left">银行名称:</td>
    			<td align="left"><input type="text" name="bank.name" class="easyui-validatebox" required="true" style="width:200px;" maxlength="50"/></td>
    		</tr>
    		<tr>
    			<td align="left">银行账户:</td>
    			<td align="left"><input type="text" name="bank.account" style="width:200px;" class="easyui-validatebox" validType="validAccount[50]" required="true" maxlength="50"/></td>
    		</tr>
    		<tr>
    			<td align="left">开户日期:</td>
    			<td align="left">
    				<input type="text" name="bank.opendate" style="width:200px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'%y%M%d'})" /> 
    			</td>
    		</tr>
    		<tr>
    			<td align="left">所属部门:</td>
    			<td align="left"><input type="text" name="bank.bankdeptid" id="bank-widget-bankdeptid"/></td>
    		</tr>
    		<tr>
    			<td align="left">备注:</td>
    			<td align="left"><textarea name="bank.remark" style="height: 100px;width: 200px;" class="easyui-validatebox" validType="maxLen[200]"></textarea></td>
    		</tr>
    		<tr>
    			<td align="left">状态:</td>
    			<td align="left"><select name="bank.state" style="width:206px;" disabled="disabled">
					<option value="4" selected="selected">新增</option>
				</select></td>
    		</tr>
    	</table>
    </form>
    <script type="text/javascript">
    	function addBank(type){
    		if(type == "save"){
    			if(!$("#bank-form-add").form('validate')){
    				return false;
    			}
    		}
    		var ret = bank_ajax($("#bank-form-add").serializeJSON(),'basefiles/finance/addBank.do?type='+type);
    		var retJson = $.parseJSON(ret);
    		if(retJson.flag){
    			$("#bank-table-list").datagrid('reload');
    			refreshLayout("银行档案【详情】",'basefiles/finance/showBankViewPage.do?id='+$("#finance-id-bank").val(),'view');
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
    		$("#bank-button").buttonWidget("initButtonType","add");
    		
    		//所属部门
    		$("#bank-widget-bankdeptid").widget({
    			referwid:'RT_T_SYS_DEPT',
    			width:200,
				singleSelect:true,
				onlyLeafCheck:false
    		});
    	});
    </script>
  </body>
</html>
