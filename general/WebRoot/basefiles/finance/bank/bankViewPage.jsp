<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>银行档案新增页面</title>
  </head>
  
  <body>
    <form action="" method="post" id="bank-form-add">
    	<input type="hidden" id="bank-oldid" value="<c:out value="${bank.id }"></c:out>" />
    	<table cellpadding="2px" cellspacing="2px" border="0px" style="padding: 20px;">
    		<tr>
    			<td style="width: 80px;" align="left">编码:</td>
    			<td align="left"><input id="finance-id-bank" type="text" name="bank.id" style="width:200px;" maxlength="20" value="<c:out value="${bank.id }"></c:out>" readonly="readonly"/></td>
    		</tr>
    		<tr>
    			<td align="left">银行名称:</td>
    			<td align="left"><input type="text" name="bank.name" style="width:200px;" maxlength="50" value="<c:out value="${bank.name }"></c:out>" readonly="readonly"/></td>
    		</tr>
    		<tr>
    			<td align="left">银行账户:</td>
    			<td align="left"><input type="text" name="bank.account" style="width:200px;" maxlength="50" value="<c:out value="${bank.account }"></c:out>" readonly="readonly"/></td>
    		</tr>
    		<tr>
    			<td align="left">开户日期:</td>
    			<td align="left">
    				<input type="text" name="bank.opendate" style="width:200px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'%y%M%d'})" value="${bank.opendate }" disabled="disabled"/> 
    			</td>
    		</tr>
    		<tr>
    			<td align="left">所属部门:</td>
    			<td align="left"><input type="text" name="bank.bankdeptid" id="bank-widget-bankdeptid" value="${bank.bankdeptid }" readonly="readonly"/></td>
    		</tr>
    		<tr>
    			<td align="left">备注:</td>
    			<td align="left"><textarea name="bank.remark" style="height: 100px;width: 200px;" readonly="readonly"><c:out value="${bank.remark }"></c:out></textarea></td>
    		</tr>
    		<tr>
    			<td align="left">状态:</td>
    			<td align="left"><select name="bank.state" style="width:206px;" disabled="disabled">
					<option value="0" <c:if test="${bank.state=='0'}">selected="selected"</c:if>>禁用</option>
					<option value="1" <c:if test="${bank.state=='1'}">selected="selected"</c:if>>启用</option>
					<option value="2" <c:if test="${bank.state=='2'}">selected="selected"</c:if>>保存</option>
					<option value="3" <c:if test="${bank.state=='3'}">selected="selected"</c:if>>暂存</option>
				</select></td>
    		</tr>
    	</table>
    </form>
    <script type="text/javascript">
    	$(function(){
    		$("#bank-button").buttonWidget("setDataID", {id:$("#bank-oldid").val(), state:'${bank.state}', type:'view'});
    		
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
