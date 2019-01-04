<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>结算方式修改页面</title>
  </head>
  
  <body>
    <form action="" method="post" id="payment-form-edit">
    	<input type="hidden" id="payment-oldid" name="payment.oldId" value="<c:out value="${payment.id }"></c:out>" />
    	<input type="hidden" id="payment-name" value="<c:out value="${payment.name }"></c:out>" />
  		<input type="hidden" name="payment.state" value="${payment.state }" />
  		<!-- 修改标识，判断有没有引用 -->
  		<input type="hidden" id="finance-editType-payment" value="${editFlag }" />
  		<table cellpadding="2px" cellspacing="2px" border="0px" style="padding: 20px;">
    		<tr>
    			<td style="width: 80px;" align="left">编码:</td>
    			<td align="left"><input id="finance-id-payment" type="text" name="payment.id" value="<c:out value="${payment.id}"></c:out>" style="width:200px;" <c:if test="${editFlag == false }">readonly="readonly"</c:if> class="easyui-validatebox" validType="validID[20]" required="true" /></td>
    		</tr>
    		<tr>
    			<td align="left">名称:</td>
    			<td align="left"><input type="text" name="payment.name" value="<c:out value="${payment.name}"></c:out>" style="width:200px;" class="easyui-validatebox" required="true" validType="validName[50]" /></td>
    		</tr>
    		<tr>
    			<td align="left">关联银行:</td>
    			<td align="left"><select name="payment.relevancebank" style="width:206px;">
    				<option value="0" <c:if test="${payment.relevancebank=='0'}">selected="selected"</c:if>>否</option>
    				<option value="1" <c:if test="${payment.relevancebank=='1'}">selected="selected"</c:if>>是</option>
    			</select></td>
    		</tr>
    		<tr>
    			<td align="left">备注:</td>
    			<td align="left"><textarea name="payment.remark" style="height: 100px;width: 200px;" class="easyui-validatebox" validType="maxLen[200]"><c:out value="${payment.remark}"></c:out></textarea></td>
    		</tr>
    		<tr>
    			<td align="left">状态:</td>
    			<td align="left"><select name="payment.state" style="width:206px;" disabled="disabled">
    				<option value="0" <c:if test="${payment.state=='0'}">selected="selected"</c:if>>禁用</option>
    				<option value="1" <c:if test="${payment.state=='1'}">selected="selected"</c:if>>启用</option>
    				<option value="2" <c:if test="${payment.state=='2'}">selected="selected"</c:if>>保存</option>
    				<option value="3" <c:if test="${payment.state=='3'}">selected="selected"</c:if>>暂存</option>
    			</select></td>
    		</tr>
    	</table>
    </form>
    <script type="text/javascript">
		//检验输入值的最大长度
		$.extend($.fn.validatebox.defaults.rules, {
			validID:{
				validator : function(value,param) {
					if(value == $("#payment-oldid").val()){
						return true;
					}
					var reg=eval("/^[A-Za-z0-9]{0,"+param[0]+"}$/");
					if(reg.test(value)){
						var ret = payment_ajax({id:value},'basefiles/finance/isUsedPaymentID.do');
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
			validName:{
				validator : function(value,param) {
					if(value == $("#payment-name").val()){
						return true;
					}
					if(value.length <= param[0]){
						var ret = payment_ajax({name:value},'basefiles/finance/isUsedPaymentName.do');
						var retJson = $.parseJSON(ret);
						if(retJson.flag){
							$.fn.validatebox.defaults.rules.validName.message = '名称重复, 请重新输入!';
							return false;
						}
					}
					else{
						$.fn.validatebox.defaults.rules.validName.message = '最多可输入{0}个字符!';
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
		
		function  editPayment(type){
        	if(type == "save"){
        		if(!$("#payment-form-edit").form('validate')){
        			return false;
        		}
        	}
        	var ret = payment_ajax($("#payment-form-edit").serializeJSON(),'basefiles/finance/editPayment.do?type='+type);
        	var retJson = $.parseJSON(ret);
        	if(retJson.flag){
        		if(type == "save"){
        			$.messager.alert("提醒","保存成功");
        		}
        		else{
        			$.messager.alert("提醒","暂存成功");
        		}
        		$("#payment-table-list").datagrid('reload');
	  		    refreshLayout("结算方式【详情】",'basefiles/finance/paymentViewPage.do?id='+$("#finance-id-payment").val());
        	}
        	else{
        		if(type == "save"){
        			$.messager.alert("提醒","保存失败");
        		}
        		else{
        			$.messager.alert("提醒","暂存失败");
        		}
        	}
        }
		
    	$(function(){
    		$("#payment-button").buttonWidget("setDataID", {id:$("#payment-oldid").val(), state:'${payment.state}', type:'edit'});
    	});
    </script>
  </body>
</html>
