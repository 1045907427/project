<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>客户应收款期初修改</title>
  </head>
  <body>
  <div class="easyui-layout" data-options="fit:true">
  	<div data-options="region:'center',border:false">
  	<form action="" id="account-form-beginAmountAdd" method="post">
   		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
   			<tr>
   				<td class="len120 left">编号：</td>
   				<td><input class="len130 easyui-validatebox" name="beginAmount.id" value="${beginAmount.id }" readonly="readonly"/></td>
   				<td class="len120 left">业务日期：</td>
   				<td><input type="text" id="account-beginAmount-businessdate" class="Wdate" style="width: 100px;" value="${beginAmount.businessdate }" name="beginAmount.businessdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/></td>
   				<td class="len80 left">状态：</td>
   				<td>
   					<select disabled="disabled" class="len100">
   						<c:forEach items="${statusList }" var="list">
   							<c:choose>
   								<c:when test="${list.code == beginAmount.status}">
   									<option value="${list.code }" selected="selected">${list.codename }</option>
   								</c:when>
   								<c:otherwise>
   									<option value="${list.code }">${list.codename }</option>
   								</c:otherwise>
   							</c:choose>
   						</c:forEach>
   					</select>
   					<input type="hidden" name="beginAmount.status" value="${beginAmount.status }"/>
   				</td>
   			</tr>
   			<tr>
   				<td class="len120 left">客户：</td>
   				<td colspan="3" style="text-align: left;">
   					<input type="text" id="account-beginAmount-customerid" name="beginAmount.customerid" style="width: 230px;" value="${beginAmount.customerid }" text="<c:out value="${beginAmount.customername }"></c:out>" readonly="readonly"/>
   					<span style="margin-left:5px;line-height:25px;">编码：${beginAmount.customerid }</span>
   				</td>
   				<td>金额：</td>
   				<td >
   					<input type="text" id="account-beginAmount-amount" name="beginAmount.amount" class="len100" autocomplete="off" value="${beginAmount.amount }" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td class="len120 left">应收日期：</td>
   				<td style="text-align: left;">
   					<input type="text" name="beginAmount.duefromdate" class="Wdate" style="width: 130px;" value="${beginAmount.duefromdate}" readonly="readonly"/>
   				</td>
   				<td class="len120 left">备注：</td>
   				<td colspan="3" style="text-align: left">
   					<input name="beginAmount.remark" style="width: 300px;" value="<c:out value="${beginAmount.remark }"></c:out>" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   		</table>
   	</form>
   	</div>
  	</div>
    <script type="text/javascript">
    	$(function(){
    		$("#account-beginAmount-customerid").customerWidget({
	    		width:380,
				col:'customerid',
				singleSelect:true,
				ishead:true,
				isall:true,
				isopen:true,
				required:true,
				onSelect:function(data){
		  			$("#account-beginAmount-amount").focus();
		  			$("#account-beginAmount-amount").select();
				},
				onClear:function(){
  					$("#account-beginAmount-collectionuser").widget('clear');
				}
    		});
    		$("#account-beginAmount-amount").numberbox({
    			precision:2,
				required:true
    		});
    	});
    	
    </script>
  </body>
</html>
