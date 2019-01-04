<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>结算方式详情页面</title>
  </head>
  
  <body>
    <form action="" method="post">
    	<input type="hidden" id="payment-oldid" value="<c:out value="${payment.id }"></c:out>" />
    	<table cellpadding="2px" cellspacing="2px" border="0px" style="padding: 20px;">
    		<tr>
    			<td style="width: 80px;">编码:</td>
    			<td><input id="finance-id-payment" type="text" name="payment.id" readonly="readonly" value="<c:out value="${payment.id}"></c:out>" style="width:200px;"/></td>
    		</tr>
    		<tr>
    			<td align="left">名称:</td>
    			<td><input type="text" name="payment.name" readonly="readonly" value="<c:out value="${payment.name}"></c:out>" style="width:200px;"/></td>
    		</tr>
    		<tr>
    			<td align="left">关联银行:</td>
    			<td align="left"><select name="payment.relevancebank" style="width:206px;" disabled="disabled">
    				<option value="0" <c:if test="${payment.relevancebank=='0'}">selected="selected"</c:if>>否</option>
    				<option value="1" <c:if test="${payment.relevancebank=='1'}">selected="selected"</c:if>>是</option>
    			</select></td>
    		</tr>
    		<tr>
    			<td align="left">备注:</td>
    			<td align="left"><textarea name="payment.remark" style="height: 100px;width: 200px;" readonly="readonly" ><c:out value="${payment.remark}"></c:out></textarea></td>
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
    	$(function(){
    		$("#payment-button").buttonWidget("setDataID", {id:$("#payment-oldid").val(), state:'${payment.state}', type:'view'});
    	});
    </script>
  </body>
</html>
