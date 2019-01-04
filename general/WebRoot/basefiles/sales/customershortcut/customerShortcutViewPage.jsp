<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>客户档案查看快捷页面</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
  	<div class="easyui-layout" data-options="fit:true">
    	<form action="" method="post" id="sales-add-customerShortcut">
	    	<input type="hidden" name="customer.salesdeptname" value="<c:out value="${customer.salesdeptname }"></c:out>" id="customerShortcut-widget-salesdeptname"/>
		  	<input type="hidden" name="customer.salesusername" value="<c:out value="${customer.salesusername }"></c:out>" id="customerShortcut-widget-salesusername"/>
		    <input type="hidden" name="customer.tallyusername" value="<c:out value="${customer.tallyusername }"></c:out>" id="customerShortcut-widget-tallyusername" />
		    <input type="hidden" value="${customer.state }" id="customerShortcut-hd-state" />
	    	<div data-options="region:'center'">
	    	 <input type="hidden" id="customer-oldid" name="customer.oldid" value="<c:out value="${customer.id}"></c:out>"/>
	    	 <table cellpadding="1" cellspacing="1" border="0">
	    		<tr>
	    			<td width="110px" align="right">本级编码：</td>
    				<td align="left"><input class="easyui-validatebox" name="customer.thisid" value="<c:out value="${customer.thisid }"></c:out>" readonly="readonly" style="width: 130px;"/><font color="red">*</font></td>
    				<td width="110px" align="right">上级客户：</td>
    				<td align="left"><input class="easyui-validatebox" id="sales-parent-customerAddPage" value="<c:out value="${customer.pid }"></c:out>" disabled="disabled"/><font color="red">*</font></td>
	    			<td width="110px" align="right">状态：</td>
	    			<td align="left"><select style="width: 136px;" disabled="disabled">
   						<c:forEach items="${stateList }" var="list">
		    				<c:choose>
		    					<c:when test="${list.code == customer.state }">
		    						<option value="${list.code }" selected="selected">${list.codename }</option>
		    					</c:when>
		    					<c:otherwise>
		    						<option value="${list.code }">${list.codename }</option>
		    					</c:otherwise>
		    				</c:choose>
		    			</c:forEach>
	    			</select></td>
	    		</tr>
	    		<tr>
	    			<td align="right">编码：</td>
		    		<td align="left"><input id="sales-id-customerAddPage" class="easyui-validatebox" readonly="readonly" value="<c:out value="${customer.id }"></c:out>" style="width: 130px;"/></td>
	    			<td align="right">名称：</td>
		    		<td align="left" colspan="3"><input class="easyui-validatebox" style="width:387px;" value="<c:out value="${customer.name }"></c:out>" readonly="readonly" /><font color="red">*</font></td>
	    		</tr>
	    		<tr>
	    			<td align="right">简称：</td>
	    			<td align="left"><input type="text" name="customer.shortname" value="<c:out value="${customer.shortname }"></c:out>" style="width: 130px" maxlength="50" readonly="readonly"/><font color="red">*</font></td>
	    			<td align="right">名称拼音：</td>
					<td align="left"><input type="text" name="customer.pinyin" value="<c:out value="${customer.pinyin }"></c:out>" maxlength="20" readonly="readonly" style="width: 130px;"/></td>
	    			<td align="right">助记符：</td>
	    			<td align="left"><input name="customer.shortcode" value="<c:out value="${customer.shortcode }"></c:out>" type="text" style="width: 130px" maxlength="20" readonly="readonly"/><font color="red">*</font></td>
	    		</tr>
	    		<tr>
	    			<td align="right">是否总店：</td>
	    			<td align="left"><select id="sales-isparent-customerAddPage" name="customer.islast" class="len136" disabled="disabled">
			    		<option></option>
			    		<option <c:if test="${customer.islast == '0' }">selected="selected"</c:if> value="0">是</option>
			    		<option <c:if test="${customer.islast == '1' }">selected="selected"</c:if> value="1">否</option>
			    	</select></td>
	    			<td align="right">店号：</td>
				    <td align="left"><input type="text" name="customer.shopno" value="<c:out value="${customer.shopno }"></c:out>" readonly="readonly" maxlength="20" style="width: 130px;"/></td>
	    			<td align="right">税号：</td>
			    	<td align="left"><input type="text" name="customer.taxno" value="<c:out value="${customer.taxno }"></c:out>" maxlength="30" style="width: 130px" readonly="readonly"/></td>
	    		</tr>
	    		<tr>
			    	<td align="right">开户银行：</td>
					<td align="left"><input type="text" name="customer.bank" value="<c:out value="${customer.bank }"></c:out>" maxlength="50" style="width: 130px" readonly="readonly"/></td>
					<td align="right">开户账号：</td>
					<td align="left"><input type="text" name="customer.cardno" value="<c:out value="${customer.cardno }"></c:out>" maxlength="30" style="width: 130px" readonly="readonly"/></td>
	    			<td align="right">开户名：</td>
  					<td align="left"><input type="text" name="customer.caraccount" value="<c:out value="${customer.caraccount }"></c:out>" maxlength="30" style="width: 130px" readonly="readonly"/></td>
	    		</tr>
	    		<tr>
  					<td align="right">注册资金：</td>
			    	<td align="left"><input type="text" name="customer.fund" value="${customer.fund }" style="width: 130px" readonly="readonly" class="easyui-numberbox" data-options="max:999999999999,precision:0"/></td>
	    			<td align="right">门店面积m<sup>2</sup>：</td>
			    	<td align="left"><input type="text" name="customer.storearea" value="${customer.storearea }" style="width: 130px" readonly="readonly" class="easyui-numberbox" data-options="max:999999999999,precision:0"/></td>
	    			<td align="right">联系人：</td>
			    	<td align="left"><input type="text" name="customer.contactname" value="<c:out value="${customer.contactname }"></c:out>" maxlength="20" style="width: 130px;" readonly="readonly"/><font color="red">*</font></td>
	    		</tr>
	    		<tr>
			    	<td align="right">联系人电话：</td>
			    	<td align="left"><input type="text" name="customer.mobile" value="<c:out value="${customer.mobile }"></c:out>" style="width: 130px" readonly="readonly"/><font color="red">*</font></td>
	    			<td align="right">传真：</td>
			    	<td align="left"><input type="text" name="customer.faxno" value="<c:out value="${customer.faxno }"></c:out>" maxlength="20" style="width: 130px" readonly="readonly"/></td>
	    			<td align="right">公司属性：</td>
			    	<td align="left"><select name="customer.nature" class="len136" disabled="disabled">
			    		<option></option>
			    		<c:forEach items="${natureList }" var="list">
		    				<c:choose>
		    					<c:when test="${list.code == customer.nature }">
		    						<option value="${list.code }" selected="selected">${list.codename }</option>
		    					</c:when>
		    					<c:otherwise>
		    						<option value="${list.code }">${list.codename }</option>
		    					</c:otherwise>
		    				</c:choose>
		    			</c:forEach>
			    	</select></td>
	    		</tr>
	    		<tr>
			    	<td align="right">是否连锁：</td>
			    	<td align="left"><select name="customer.ischain" class="len136" disabled="disabled">
			    		<option></option>
			    		<option <c:if test="${customer.ischain == '0' }">selected="selected"</c:if> value="0">否</option>
			    		<option <c:if test="${customer.ischain == '1' }">selected="selected"</c:if> value="1">是</option>
			    	</select></td>
			    	<td align="right">ABC等级：</td>
					<td align="left">
						<select name="customer.abclevel" class="len136" disabled="disabled">
							<option></option>
							<option <c:if test="${customer.abclevel == 'A' }">selected="selected"</c:if> value="A">A</option>
							<option <c:if test="${customer.abclevel == 'B' }">selected="selected"</c:if> value="B">B</option>
							<option <c:if test="${customer.abclevel == 'C' }">selected="selected"</c:if> value="C">C</option>
							<option <c:if test="${customer.abclevel == 'D' }">selected="selected"</c:if> value="D">D</option>
						</select>
					</td>
	    			<td align="right">县级市：</td>
			    	<td align="left"><input type="text" name="customer.countylevel" value="<c:out value="${customer.countylevel }"></c:out>" maxlength="20" style="width: 130px" readonly="readonly"/></td>
	    		</tr>
	    		<tr>
			    	<td align="right">乡镇：</td>
			    	<td align="left"><input type="text" name="customer.villagetown" value="<c:out value="${customer.villagetown }"></c:out>" maxlength="20" style="width: 130px" readonly="readonly"/></td>
	    			<td align="right">详细地址：</td>
			    	<td align="left" colspan="3"><input type="text" name="customer.address" value="<c:out value="${customer.address }"></c:out>" maxlength="100" style="width: 387px" readonly="readonly"/><font color="red">*</font></td>
	    		</tr>
	    		<tr>
	    			<td align="right">促销分类：</td>
  					<td align="left"><select name="customer.promotionsort" class="len136" disabled="disabled">
    						<option></option>
    						<c:forEach items="${promotionsortList }" var="list">
			    				<c:choose>
			    					<c:when test="${list.code == customer.promotionsort }">
			    						<option value="${list.code }" selected="selected">${list.codename }</option>
			    					</c:when>
			    					<c:otherwise>
			    						<option value="${list.code }">${list.codename }</option>
			    					</c:otherwise>
			    				</c:choose>
			    			</c:forEach>
    					</select>
    				</td>
    				<td align="right">所属区域：</td>
	    			<td align="left"><input id="customerShortcut-widget-salesarea" type="text" name="customer.salesarea" value="<c:out value="${customer.salesarea }"></c:out>" style="width: 130px" readonly="readonly"/><font color="red">*</font></td>
	    			<td align="right">所属分类：</td>
	    			<td align="left"><input id="customerShortcut-widget-customersort" type="text" name="customer.customersort" value="<c:out value="${customer.customersort }"></c:out>" style="width: 130px" readonly="readonly"/></td>
	    		</tr>
	    		<tr>
	    			<td align="right">对账人：</td>
	    			<td align="left"><input type="text" name="customer.checker" value="<c:out value="${customer.checker }"></c:out>" maxlength="20" style="width: 130px;" readonly="readonly"/></td>
	    			<td align="right">对账人电话：</td>
	    			<td align="left"><input type="text" name="customer.checkmobile" value="<c:out value="${customer.checkmobile }"></c:out>" maxlength="20" style="width: 130px;" readonly="readonly"/></td>
	    			<td align="right">对账人邮箱：</td>
	    			<td align="left"><input type="text" name="customer.checkemail" value="<c:out value="${customer.checkemail }"></c:out>" maxlength="50" style="width: 130px;" readonly="readonly"/></td>
	    		</tr>
	    		<tr>
	    			<td align="right">付款人：</td>
	    			<td align="left"><input type="text" name="customer.payer" value="<c:out value="${customer.payer }"></c:out>" maxlength="20" style="width: 130px;" readonly="readonly"/></td>
	    			<td align="right">付款人电话：</td>
	    			<td align="left"><input type="text" name="customer.payermobile" value="<c:out value="${customer.payermobile }"></c:out>" maxlength="20" style="width: 130px;" readonly="readonly"/></td>
	    			<td align="right">付款人邮箱：</td>
	    			<td align="left"><input type="text" name="customer.payeremail" value="<c:out value="${customer.payeremail }"></c:out>" maxlength="50" style="width: 130px;" readonly="readonly"/></td>
	    		</tr>
	    		<tr>
	    			<td align="right">店长：</td>
	    			<td align="left"><input type="text" name="customer.shopmanager" value="<c:out value="${customer.shopmanager }"></c:out>" maxlength="20" style="width: 130px;" readonly="readonly"/></td>
	    			<td align="right">店长联系电话：</td>
	    			<td align="left"><input type="text" name="customer.shopmanagermobile" value="<c:out value="${customer.shopmanagermobile }"></c:out>" maxlength="20" style="width: 130px;" readonly="readonly"/></td>
	    			<td align="right">收货人：</td>
	    			<td align="left"><input type="text" name="customer.gsreceipter" value="<c:out value="${customer.gsreceipter }"></c:out>" maxlength="20" style="width: 130px;" readonly="readonly"/></td>
	    		</tr>
	    		<tr>
	    			<td align="right">收货人联系电话：</td>
	    			<td align="left"><input type="text" name="customer.gsreceiptermobile" value="<c:out value="${customer.gsreceiptermobile }"></c:out>" maxlength="20" style="width: 130px;" readonly="readonly"/></td>
	    			<td align="right">结算方式：</td>
	    			<td align="left"><input type="text" name="customer.settletype" value="<c:out value="${customer.settletype }"></c:out>" id="customerShortcut-widget-settletype" style="width: 130px" readonly="readonly"/></td>
	    			<td align="right">结算日：</td>
	    			<td align="left"><select name="customer.settleday" class="len136" disabled="disabled">
						<option></option>
						<c:forEach items="${dayList}" var="day">
							<c:choose>
							<c:when test="${day == customer.settleday}"><option value="${day }" selected="selected">${day }</option></c:when>
							<c:otherwise><option value="${day }">${day }</option></c:otherwise>
							</c:choose>
						</c:forEach>
					</select></td>
	    		</tr>
	    		<tr>
	    			<td align="right">支付方式：</td>
			    	<td align="left"><input type="text" name="customer.paytype" value="<c:out value="${customer.paytype }"></c:out>" id="customerShortcut-widget-paytype" style="width: 130px" readonly="readonly"/></td>
	    			<td align="right">是否现款：</td>
			    	<td align="left"><select name="customer.iscash" class="len136" disabled="disabled">
			    		<option></option>
			    		<option <c:if test="${customer.iscash == '0' }">selected="selected"</c:if> value="0">否</option>
			    		<option <c:if test="${customer.iscash == '1' }">selected="selected"</c:if> value="1">是</option>
			    	</select><font color="red">*</font></td>
			    	<td align="right">是否账期：</td>
			    	<td align="left"><select name="customer.islongterm" class="len136" disabled="disabled">
			    		<option></option>
			    		<option <c:if test="${customer.islongterm == '0' }">selected="selected"</c:if> value="0">否</option>
			    		<option <c:if test="${customer.islongterm == '1' }">selected="selected"</c:if> value="1">是</option>
			    	</select><font color="red">*</font></td>
	    		</tr>
	    		<tr>
	    			<td align="right">超账期控制：</td>
					<td align="left"><select id="customerShortcut-select-overcontrol" name="customer.overcontrol" class="len130" onchange="selectControl()" disabled="disabled">
							<option></option>
							<option <c:if test="${customer.overcontrol == '1'}">selected="selected"</c:if> value="1">是</option>
							<option <c:if test="${customer.overcontrol == '0'}">selected="selected"</c:if> value="0">否</option>
					</select><font color="red">*</font></td>
	    			<td align="right">超账期宽限天数：</td>
					<td align="left"><input type="text" id="customerShortcut-input-overgracedate" value="${customer.overgracedate }" class="easyui-numberbox" data-options="max:999,precision:0" onchange="changeValue(this.value)" readonly="readonly" style="width: 130px;"/>
						<input type="hidden" id="customer-hd-overgracedate" name="customer.overgracedate" value="${customer.overgracedate }"/>
					</td>
  					<td align="right">信用额度：</td>
			    	<td align="left"><input type="text" name="customer.credit" value="${customer.credit }" class="easyui-numberbox" data-options="max:999999999999,precision:0" style="width: 130px" readonly="readonly"/></td>
	    		</tr>
	    		<tr>
	    			<td align="right">信用等级：</td>
				    <td align="right"><select name="customer.creditrating" class="len136" disabled="disabled">
							<option></option>
							<c:forEach items="${creditratingList }" var="list">
				   				<c:choose>
				   					<c:when test="${list.code == customer.creditrating }">
				   						<option value="${list.code }" selected="selected">${list.codename }</option>
				   					</c:when>
				   					<c:otherwise>
				   						<option value="${list.code }">${list.codename }</option>
				   					</c:otherwise>
				   				</c:choose>
				   			</c:forEach>
					</select><font color="red">*</font></td>
	    			<td align="right">对账日期：</td>
			    	<td align="left"><input type="text" name="customer.reconciliationdate" value="<c:out value="${customer.reconciliationdate }"></c:out>" style="width: 130px" readonly="readonly"/></td>
	    			<td align="right">开票日期：</td>
			    	<td align="left"><input type="text" name="customer.billingdate" value="<c:out value="${customer.billingdate }"></c:out>" style="width: 130px" readonly="readonly"/></td>
	    		</tr>
	    		<tr>
	    			<td align="right">到款日期：</td>
			    	<td align="left"><input type="text" name="customer.arrivalamountdate" value="<c:out value="${customer.arrivalamountdate }"></c:out>" style="width: 130px" readonly="readonly"/></td>
	    			<td align="right">票种：</td>
			    	<td align="left"><select name="customer.tickettype" class="len136" disabled="disabled">
			    		<option></option>
			    		<option <c:if test="${customer.tickettype == '1' }">selected="selected"</c:if> value="1">增值税发票</option>
			    		<option <c:if test="${customer.tickettype == '2' }">selected="selected"</c:if> value="2">普通发票</option>
			    	</select></td>
			    	<td align="right">核销方式：</td>
	    			<td align="left"><select name="customer.canceltype" class="len136" disabled="disabled">
	    				<option></option>
						<c:forEach items="${canceltypeList }" var="list">
							<c:choose>
		    					<c:when test="${list.code == customer.canceltype }">
		    						<option value="${list.code }" selected="selected">${list.codename }</option>
		    					</c:when>
		    					<c:otherwise>
		    						<option value="${list.code }">${list.codename }</option>
		    					</c:otherwise>
		    				</c:choose>
		    			</c:forEach>
	    			</select><font color="red">*</font></td>
	    		</tr>
	    		<tr>
	    			<td align="right">默认销售部门：</td>
	    			<td align="left"><input id="customerShortcut-widget-salesdeptid" name="customer.salesdeptid" value="<c:out value="${customer.salesdeptid }"></c:out>" type="text" style="width: 130px" readonly="readonly"/></td>
	    			<td align="right">默认业务员：</td>
	    			<td align="left"><input id="customerShortcut-widget-salesuserid" name="customer.salesuserid" value="<c:out value="${customer.salesuserid }"></c:out>" type="text" style="width: 130px" readonly="readonly"/><font color="red">*</font></td>
	    			<td align="right">默认理货员：</td>
	    			<td align="left"><input id="customerShortcut-widget-tallyuserid" name="customer.tallyuserid" value="<c:out value="${customer.tallyuserid }"></c:out>" type="text" style="width: 130px" readonly="readonly"/></td>
	    		</tr>
	    		<tr>
	    			<td align="right">默认内勤：</td>
	    			<td align="left"><input id="customerShortcut-widget-indoorstaff" name="customer.indoorstaff" value="<c:out value="${customer.indoorstaff }"></c:out>" type="text" style="width: 130px" readonly="readonly"/><font color="red">*</font></td>
	    			<td align="right">收款人：</td>
				    <td align="left"><input id="customerShortcut-widget-payeeid" name="customer.payeeid" value="<c:out value="${customer.payeeid }"></c:out>" type="text" style="width: 130px" readonly="readonly"/></td>
	    			<td align="right">默认价格套：</td>
	    			<td align="left"><select name="customer.pricesort" class="len136" disabled="disabled">
						<option></option>
						<c:forEach items="${priceList }" var="list">
		    				<c:choose>
		    					<c:when test="${list.code == customer.pricesort }">
		    						<option value="${list.code }" selected="selected">${list.codename }</option>
		    					</c:when>
		    					<c:otherwise>
		    						<option value="${list.code }">${list.codename }</option>
		    					</c:otherwise>
		    				</c:choose>
		    			</c:forEach>
					</select><font color="red">*</font></td>
	    		</tr>
	    		<tr>
	    			<td align="right">备注：</td>
	    			<td align="left" colspan="6"><textarea style="width: 638px;" rows="1" name="customer.remark" readonly="readonly"><c:out value="${customer.remark }"></c:out></textarea></td>
	    		</tr>
	    	</table>
  		</div>
	</form>
     </div>
     <script type="text/javascript">
     	$(function(){
     		$("#sales-buttons-customershortcut").buttonWidget("setDataID", {id:$("#customer-oldid").val(),state:'${customer.state}',type:'view'});
     		//上级客户
			$("#sales-parent-customerAddPage").widget({ //上级分类参照窗口
    			name:'t_base_sales_customer',
				col:'pid',
    			singleSelect:true,
    			width:130,
    			onlyLeafCheck:false,
    			view: true
    		});
     		//所属区域
		  	$("#customerShortcut-widget-salesarea").widget({
		  		width:130,
				name:'t_base_sales_customer',
				col:'salesarea',
				singleSelect:true,
				onlyLeafCheck:true
			});
			
			//所属分类
			$("#customerShortcut-widget-customersort").widget({
		  		width:130,
				name:'t_base_sales_customer',
				col:'customersort',
				singleSelect:true,
				onlyLeafCheck:false
			});
			
			//默认销售部门
			$("#customerShortcut-widget-salesdeptid").widget({ 
    			name:'t_base_sales_customer',
    			col:'salesdeptid',
    			width:130,
    			singleSelect:true,
    			setValueSelect:false,
    			onlyLeafCheck:false
    		});	
    		
    		$("#customerShortcut-widget-salesuserid").widget({
    			name:'t_base_sales_customer',
    			col:'salesuserid',
    			width:130,
    			async:false,
    			singleSelect:true,
    			onlyLeafCheck:false
    		});
			
			//默认理货员
			$("#customerShortcut-widget-tallyuserid").widget({
		  		width:130,
				name:'t_base_sales_customer',
				col:'tallyuserid',
				singleSelect:true,
				onlyLeafCheck:false
			});
			
			//对方联系人
			$("#customerShortcut-widget-contact").widget({
				width:130,
				name:'t_base_sales_customer',
				col:'contact',
				singleSelect:true,
				onlyLeafCheck:false
			});
			//默认内勤
			$("#customerShortcut-widget-indoorstaff").widget({ //销售内勤参照窗口
    			name:'t_base_sales_customer',
				col:'indoorstaff',
    			singleSelect:true,
    			width:130,
    			onlyLeafCheck:false
    		});
    		
    		//收款人
    		$("#customerShortcut-widget-payeeid").widget({ 
    			name:'t_base_sales_customer',
				col:'payeeid',
    			singleSelect:true,
    			width:130,
    			onlyLeafCheck:false
    		});
    		
			//支付方式
			$("#customerShortcut-widget-paytype").widget({
				width:130,
				name:'t_base_sales_customer',
				col:'paytype',
				singleSelect:true,
				onlyLeafCheck:false
			});
			//结算方式
			$("#customerShortcut-widget-settletype").widget({ //结算方式参照窗口
    			name:'t_base_sales_customer',
				col:'settletype',
    			singleSelect:true,
    			width:130,
    			onlyLeafCheck:false
    		});
     	});
     </script>
  </body>
</html>
