<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>银行档案新增页面</title>
  </head>
  
  <body>
  	<!-- 修改标识，判断有没有引用 -->
  	<input type="hidden" id="finance-editType-bank" value="${editFlag }" />
    <form action="" method="post" id="bank-form-add">
    	<input type="hidden" id="bank-oldid" name="bank.oldid" value="<c:out value="${bank.id }"></c:out>" />
    	<input type="hidden" id="bank-account" value="<c:out value="${bank.account }"></c:out>" />
    	<table cellpadding="2px" cellspacing="2px" border="0px" style="padding: 20px;">
    		<tr>
    			<td style="width: 80px;" align="left">编码:</td>
    			<td align="left"><input id="finance-id-bank" type="text" name="bank.id" class="easyui-validatebox" validType="validID[20]" required="true" <c:if test="${editFlag == false }">readonly="readonly"</c:if> style="width:200px;" maxlength="20" value="<c:out value="${bank.id }"></c:out>"/></td>
    		</tr>
    		<tr>
    			<td align="left">银行名称:</td>
    			<td align="left"><input type="text" name="bank.name" class="easyui-validatebox" required="true" style="width:200px;" maxlength="50" value="<c:out value="${bank.name }"></c:out>"/></td>
    		</tr>
    		<tr>
    			<td align="left">银行账户:</td>
    			<td align="left"><input type="text" name="bank.account" style="width:200px;" class="easyui-validatebox" validType="validAccount[50]" required="true" maxlength="50" value="<c:out value="${bank.account }"></c:out>"/></td>
    		</tr>
    		<tr>
    			<td align="left">开户日期:</td>
    			<td align="left">
    				<input type="text" name="bank.opendate" style="width:200px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'%y%M%d'})" value="${bank.opendate }"/> 
    			</td>
    		</tr>
    		<tr>
    			<td align="left">所属部门:</td>
    			<td align="left"><input type="text" name="bank.bankdeptid" id="bank-widget-bankdeptid" value="${bank.bankdeptid }"/></td>
    		</tr>
    		<tr>
    			<td align="left">备注:</td>
    			<td align="left"><textarea name="bank.remark" style="height: 100px;width: 200px;" class="easyui-validatebox" validType="maxLen[200]"><c:out value="${bank.remark }"></c:out></textarea></td>
    		</tr>
    		<tr>
    			<td align="left">状态:</td>
    			<td align="left"><select style="width:206px;" disabled="disabled">
					<option value="0" <c:if test="${bank.state=='0'}">selected="selected"</c:if>>禁用</option>
					<option value="1" <c:if test="${bank.state=='1'}">selected="selected"</c:if>>启用</option>
					<option value="2" <c:if test="${bank.state=='2'}">selected="selected"</c:if>>保存</option>
					<option value="3" <c:if test="${bank.state=='3'}">selected="selected"</c:if>>暂存</option>
				</select>
				<input type="hidden" name="bank.state" value="${bank.state }"/>
				</td>
    		</tr>
    	</table>
    </form>
    <script type="text/javascript">
    	//检验输入值的最大长度
		$.extend($.fn.validatebox.defaults.rules, {
			validID:{
				validator : function(value,param) {
					var reg=eval("/^[A-Za-z0-9]{0,"+param[0]+"}$/");
					if(reg.test(value)){
						if(value == $("#bank-oldid").val()){
							return true;
						}
						var ret = bank_ajax({id:value},'basefiles/finance/checkBankidUserd.do');
						var retJson = $.parseJSON(ret);
						if(retJson.flag){
							$.fn.validatebox.defaults.rules.validID.message = '编码已存在, 请重新输入!';
							return false;
						}
					}
					else{
						$.fn.validatebox.defaults.rules.validID.message = '最多可输入{0}个字符!';
						return false;
					}
		            return true;
		        }, 
		        message : '' 
			},
			validAccount:{
				validator : function(value,param) {
					if(value == $("#bank-account").val()){
						return true;
					}
					if(value.length <= param[0]){
						var ret = bank_ajax({account:value},'basefiles/finance/checkBandAccountUserd.do');
						var retJson = $.parseJSON(ret);
						if(retJson.flag){
							$.fn.validatebox.defaults.rules.validAccount.message = '银行账户重复, 请重新输入!';
							return false;
						}
					}
					else{
						$.fn.validatebox.defaults.rules.validAccount.message = '最多可输入{0}个字符!';
						return false;
					}
					return true;
		        }, 
		        message : '' 
			},
			maxLen:{
	  			validator : function(value,param) { 
		            return value.length <= param[0]; 
		        }, 
		        message : '最多可输入{0}个字符!' 
	  		}
		});
		
    	function editBank(type){
    		if(type == "save"){
    			if(!$("#bank-form-add").form('validate')){
    				return false;
    			}
    		}
    		loading("提交中..");
    		var ret = bank_ajax($("#bank-form-add").serializeJSON(),'basefiles/finance/editBank.do?type='+type);
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
    		$("#bank-button").buttonWidget("setDataID", {id:$("#bank-oldid").val(), state:'${bank.state}', type:'edit'});
    		
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
