<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>客户档案新增快捷页面</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
  	<div class="easyui-layout" data-options="fit:true">
	    <form action="" method="post" id="sales-add-customerShortcut">
	    	<input type="hidden" name="parentId" id="sales-parentId-customerAddPage"/>
	    	<input type="hidden" id="customer-thisid" value="<c:out value="${customer.thisid }"></c:out>"/>
	    	<input type="hidden" id="customer-salesdeptid" value="<c:out value="${customer.salesdeptid }"></c:out>"/>
	    	<input type="hidden" id="customer-salesuserid" value="<c:out value="${customer.salesuserid }"></c:out>"/>
	    	<input type="hidden" name="customer.salesdeptname" value="<c:out value="${customer.salesdeptname }"></c:out>" id="customerShortcut-widget-salesdeptname"/>
		  	<input type="hidden" name="customer.salesusername" value="<c:out value="${customer.salesusername }"></c:out>" id="customerShortcut-widget-salesusername"/>
		    <input type="hidden" name="customer.tallyusername" value="<c:out value="${customer.tallyusername }"></c:out>" id="customerShortcut-widget-tallyusername" />
    		<input type="hidden" id="sales-state-customerAddPage" name="customer.state"/>
    		<input type="hidden" id="sales-button-clicktype" value="0"/>
    		<div data-options="region:'center'">
		    	<table cellpadding="1" cellspacing="1" border="0">
		    		<tr>
		    			<td width="110px" align="right">本级编码：</td>
	    				<td align="left"><input class="easyui-validatebox" name="customer.thisid" id="sales-thisId-customerAddPage" data-options="required:true,validType:'validLength'" style="width: 130px"/><font color="red">*</font></td>
	    				<td width="110px" align="right">上级客户：</td>
	    				<td align="left"><input class="easyui-validatebox" id="sales-parent-customerAddPage" value="<c:out value="${customer.pid }"></c:out>" name="customer.pid" /><font color="red">*</font></td>
		    			<td width="110px" align="right">状态：</td>
		    			<td align="left"><select style="width: 136px;" disabled="disabled">
		    				<option value="4" selected="selected">新增</option>
		    			</select></td>
		    		</tr>
		    		<tr>
		    			<td align="right">编码：</td>
			    		<td align="left"><input type="text" class="easyui-validatebox" readonly="readonly" name="customer.id" id="sales-id-customerAddPage" style="width: 130px"/></td>
		    			<td align="right">名称：</td>
			    		<td align="left" colspan="3"><input id="sales-name-customershortcut" name="customer.name" type="text" style="width: 400px" maxlength="100" class="easyui-validatebox" data-options="required:true" validType="validName[100]"/><font color="red">*</font></td>
		    		</tr>
		    		<tr>
		    			<td align="right">简称：</td>
		    			<td align="left"><input type="text" name="customer.shortname" style="width: 130px" maxlength="50" class="easyui-validatebox" data-options="required:true"/><font color="red">*</font></td>
		    			<td align="right">名称拼音：</td>
						<td align="left"><input type="text" name="customer.pinyin" maxlength="20" style="width: 130px"/></td>
		    			<td align="right">助记符：</td>
		    			<td align="left"><input name="customer.shortcode" type="text" style="width: 130px" maxlength="20" class="easyui-validatebox" data-options="required:true"/><font color="red">*</font></td>
		    		</tr>
		    		<tr>
		    			<td align="right">是否总店：</td>
		    			<td align="left"><select id="sales-isparent-customerAddPage" name="customer.islast" class="len136 easyui-validatebox" data-options="required:true">
				    		<option></option>
				    		<option <c:if test="${customer.islast == '0' }">selected="selected"</c:if> value="0">是</option>
				    		<option <c:if test="${customer.islast == '1' }">selected="selected"</c:if> value="1">否</option>
				    	</select></td>
		    			<td align="right">店号：</td>
					    <td align="left"><input type="text" name="customer.shopno" maxlength="20" style="width: 130px"/></td>
					    <td align="right">税号：</td>
				    	<td align="left"><input type="text" name="customer.taxno" maxlength="30" style="width: 130px"/></td>
		    		</tr>
		    		<tr>
				    	<td align="right">开户银行：</td>
						<td align="left"><input type="text" name="customer.bank" maxlength="50" style="width: 130px"/></td>
						<td align="right">开户账号：</td>
						<td align="left"><input type="text" name="customer.cardno" maxlength="30" style="width: 130px"/></td>
						<td align="right">开户名：</td>
	  					<td align="left"><input type="text" name="customer.caraccount" maxlength="30" style="width: 130px"/></td>
		    		</tr>
		    		<tr>
	  					<td align="right">注册资金：</td>
				    	<td align="left"><input type="text" name="customer.fund" style="width: 130px" class="easyui-numberbox" data-options="max:999999999999,precision:0"/></td>
		    			<td align="right">门店面积m<sup>2</sup>：</td>
				    	<td align="left"><input type="text" name="customer.storearea" style="width: 130px" class="easyui-numberbox" data-options="max:999999999999,precision:0"/></td>
		    			<td align="right">联系人：</td>
				    	<td align="left"><input type="text" name="customer.contactname" maxlength="20" style="width: 130px;"/><font color="red">*</font></td>
		    		</tr>
		    		<tr>
				    	<td align="right">联系人电话:</td>
				    	<td align="left"><input type="text" name="customer.mobile" style="width: 130px"/><font color="red">*</font></td>
		    			<td align="right">传真：</td>
				    	<td align="left"><input type="text" name="customer.faxno" class="easyui-validatebox" maxlength="20" validType="faxno" style="width: 130px"/></td>
		    			<td align="right">公司属性：</td>
				    	<td align="left"><select name="customer.nature" class="len136">
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
				    	<td align="left"><select name="customer.ischain" class="len136">
				    		<option></option>
				    		<option <c:if test="${customer.ischain == '0' }">selected="selected"</c:if> value="0">否</option>
				    		<option <c:if test="${customer.ischain == '1' }">selected="selected"</c:if> value="1">是</option>
				    	</select></td>
				    	<td align="right">ABC等级：</td>
						<td align="left">
							<select name="customer.abclevel" class="len136" data-options="required:true">
								<option></option>
								<option <c:if test="${customer.abclevel == 'A' }">selected="selected"</c:if> value="A">A</option>
								<option <c:if test="${customer.abclevel == 'B' }">selected="selected"</c:if> value="B">B</option>
								<option <c:if test="${customer.abclevel == 'C' }">selected="selected"</c:if> value="C">C</option>
								<option <c:if test="${customer.abclevel == 'D' }">selected="selected"</c:if> value="D">D</option>
							</select>
						</td>
						<td align="right">县级市：</td>
				    	<td align="left"><input type="text" name="customer.countylevel" value="<c:out value="${customer.countylevel }"></c:out>" maxlength="20" style="width: 130px"/></td>
		    		</tr>
		    		<tr>
				    	<td align="right">乡镇：</td>
				    	<td align="left"><input type="text" name="customer.villagetown" value="<c:out value="${customer.villagetown }"></c:out>" maxlength="20" style="width: 130px"/></td>
		    			<td align="right">详细地址：</td>
				    	<td align="left" colspan="3"><input type="text" name="customer.address" maxlength="100" style="width: 400px"/><font color="red">*</font></td>
		    		</tr>
		    		<tr>
				    	<td align="right">促销分类：</td>
	  						<td align="left">
	    					<select name="customer.promotionsort" class="len136 easyui-validatebox" data-options="required:true">
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
		    			<td align="left"><input id="customerShortcut-widget-salesarea" type="text" name="customer.salesarea" value="<c:out value="${customer.salesarea }"></c:out>" style="width: 130px"/><font color="red">*</font></td>
		    			<td align="right">所属分类：</td>
		    			<td align="left"><input id="customerShortcut-widget-customersort" type="text" name="customer.customersort" value="<c:out value="${customer.customersort }"></c:out>" style="width: 130px"/></td>
		    		</tr>
		    		<tr>
		    			<td align="right">对账人：</td>
		    			<td align="left"><input type="text" name="customer.checker" maxlength="20" style="width: 130px;"/></td>
		    			<td align="right">对账人电话：</td>
		    			<td align="left"><input type="text" name="customer.checkmobile" maxlength="20" style="width: 130px;"/></td>
		    			<td align="right">对账人邮箱：</td>
		    			<td align="left"><input type="text" name="customer.checkemail" maxlength="50" style="width: 130px;" class="easyui-validatebox" validType="email"/></td>
		    		</tr>
		    		<tr>
		    			<td align="right">付款人：</td>
		    			<td align="left"><input type="text" name="customer.payer" maxlength="20" style="width: 130px;"/></td>
		    			<td align="right">付款人电话：</td>
		    			<td align="left"><input type="text" name="customer.payermobile" maxlength="20" style="width: 130px;"/></td>
		    			<td align="right">付款人邮箱：</td>
		    			<td align="left"><input type="text" name="customer.payeremail" maxlength="50" style="width: 130px;" class="easyui-validatebox" validType="email"/></td>
		    		</tr>
		    		<tr>
		    			<td align="right">店长：</td>
		    			<td align="left"><input type="text" name="customer.shopmanager" maxlength="20" style="width: 130px;"/></td>
		    			<td align="right">店长联系电话：</td>
		    			<td align="left"><input type="text" name="customer.shopmanagermobile" maxlength="20" style="width: 130px;"/></td>
		    			<td align="right">收货人：</td>
		    			<td align="left"><input type="text" name="customer.gsreceipter" maxlength="20" style="width: 130px;"/></td>
		    		</tr>
		    		<tr>
		    			<td align="right">收货人联系电话：</td>
		    			<td align="left"><input type="text" name="customer.gsreceiptermobile" maxlength="20" style="width: 130px;"/></td>
		    			<td align="right">结算方式：</td>
		    			<td align="left"><input type="text" name="customer.settletype" value="<c:out value="${customer.settletype }"></c:out>" id="customerShortcut-widget-settletype" style="width: 130px"/></td>
		    			<td align="right">结算日：</td>
		    			<td align="left"><select id="customer-select-settleday" name="customer.settleday" class="len136 easyui-validatebox" <c:if test="${monthorday == '1' }">data-options="required:true"</c:if>>
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
				    	<td align="left"><input type="text" name="customer.paytype" value="<c:out value="${customer.paytype }"></c:out>" id="customerShortcut-widget-paytype" style="width: 130px"/></td>
		    			<td align="right">是否现款：</td>
				    	<td align="left"><select name="customer.iscash" class="len136 easyui-validatebox" data-options="required:true">
				    		<option></option>
				    		<option <c:if test="${customer.iscash == '0' }">selected="selected"</c:if> value="0">否</option>
				    		<option <c:if test="${customer.iscash == '1' }">selected="selected"</c:if> value="1">是</option>
				    	</select><font color="red">*</font></td>
				    	<td align="right">是否账期：</td>
				    	<td align="left"><select name="customer.islongterm" class="len136 easyui-validatebox" data-options="required:true">
				    		<option></option>
				    		<option <c:if test="${customer.islongterm == '0' }">selected="selected"</c:if> value="0">否</option>
				    		<option <c:if test="${customer.islongterm == '1' }">selected="selected"</c:if> value="1">是</option>
				    	</select><font color="red">*</font></td>
		    		</tr>
		    		<tr>
				    	<td align="right">超账期控制：</td>
						<td align="left"><select id="customerShortcut-select-overcontrol" name="customer.overcontrol" class="len136 easyui-validatebox" data-options="required:true" onchange="selectControl()">
								<option></option>
								<option <c:if test="${customer.overcontrol == '1'}">selected="selected"</c:if> value="1">是</option>
								<option <c:if test="${customer.overcontrol == '0'}">selected="selected"</c:if> value="0">否</option>
						</select><font color="red">*</font></td>
						<td align="right">超账期宽限天数：</td>
						<td align="left"><input type="text" id="customerShortcut-input-overgracedate" <c:if test="${customer.overcontrol == '0'}">disabled="disabled"</c:if> value="${customer.overgracedate }" class="easyui-numberbox" data-options="max:999,precision:0" onchange="changeValue(this.value)" style="width: 130px"/>
							<input type="hidden" id="customer-hd-overgracedate" name="customer.overgracedate" value="${customer.overgracedate }"/>
						</td>
	  					<td align="right">信用额度：</td>
				    	<td align="left"><input type="text" name="customer.credit" value="${customer.credit }" class="easyui-numberbox" data-options="max:999999999999,precision:0,required:true" style="width: 130px"/></td>
		    		</tr>
		    		<tr>
		    			<td align="right">信用等级：</td>
					    <td align="right"><select name="customer.creditrating" class="len136 easyui-validatebox" data-options="required:true">
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
				    	<td align="left"><input type="text" name="customer.reconciliationdate" value="<c:out value="${customer.reconciliationdate }"></c:out>" style="width: 130px"/></td>
		    			<td align="right">开票日期：</td>
				    	<td align="left"><input type="text" name="customer.billingdate" value="<c:out value="${customer.billingdate }"></c:out>" style="width: 130px"/></td>
		    		</tr>
		    		<tr>
		    			<td align="right">到款日期：</td>
				    	<td align="left"><input type="text" name="customer.arrivalamountdate" value="<c:out value="${customer.arrivalamountdate }"></c:out>" style="width: 130px"/></td>
		    			<td align="right">票种：</td>
				    	<td align="left"><select name="customer.tickettype" class="len136">
				    		<option></option>
				    		<option <c:if test="${customer.tickettype == '1' }">selected="selected"</c:if> value="1">增值税发票</option>
				    		<option <c:if test="${customer.tickettype == '2' }">selected="selected"</c:if> value="2">普通发票</option>
				    	</select></td>
				    	<td align="right">核销方式：</td>
		    			<td align="left"><select name="customer.canceltype" class="len136 easyui-validatebox" data-options="required:true">
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
		    			<td align="left"><input id="customerShortcut-widget-salesdeptid" name="customer.salesdeptid" value="<c:out value="${customer.salesdeptid }"></c:out>" type="text" style="width: 130px"/></td>
		    			<td align="right">默认业务员：</td>
		    			<td align="left"><input id="customerShortcut-widget-salesuserid" name="customer.salesuserid" value="<c:out value="${customer.salesuserid }"></c:out>" type="text" style="width: 130px"/><font color="red">*</font></td>
		    			<td align="right">默认理货员：</td>
		    			<td align="left"><input id="customerShortcut-widget-tallyuserid" name="customer.tallyuserid" value="<c:out value="${customer.tallyuserid }"></c:out>" type="text" style="width: 130px"/></td>
		    		</tr>
		    		<tr>
		    			<td align="right">默认内勤：</td>
		    			<td align="left"><input id="customerShortcut-widget-indoorstaff" name="customer.indoorstaff" value="<c:out value="${customer.indoorstaff }"></c:out>" type="text" style="width: 130px"/><font color="red">*</font></td>
		    			<td align="right">收款人：</td>
				    	<td align="left"><input id="customerShortcut-widget-payeeid" name="customer.payeeid" value="<c:out value="${customer.payeeid }"></c:out>" type="text" style="width: 130px"/></td>
		    			<td align="right">默认价格套：</td>
		    			<td align="left"><select name="customer.pricesort" class="len136" data-options="required:true">
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
		    			<td align="left" colspan="6"><textarea style="width: 650px;" rows="1" name="customer.remark"><c:out value="${customer.remark }"></c:out></textarea></td>
		    		</tr>
	    		</table>
	  		</div>
	    	<div data-options="region:'south'" style="height: 30px;" align="right">
				<a href="javaScript:void(0);" id="sales-save-customerShortcut" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'" title="保存">保存</a>
			</div> 
		 </form>
     </div>
     <script type="text/javascript">
     	$(function(){
     		$("#sales-buttons-customershortcut").buttonWidget("initButtonType", 'add');
     		//检验商品档案数据（唯一性，最大长度等）
			$.extend($.fn.validatebox.defaults.rules, {
	   			validName:{//名称唯一性,最大长度
	   				validator:function(value,param){
	   					if(value.length <= param[0]){
	   						var ret = customerShortcut_AjaxConn({name:value},'basefiles/customerNameNoUsed.do');//true 重复，false 不重复
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
	   				message:''
	   			}
			});
		
     		validLengthAndUsed('${len}', "basefiles/customerNOUsed.do", $("#sales-parentId-customerAddPage").val(), '', "该编号已被使用，请另输编号！"); //验证输入长度
    		$("#sales-parent-customerAddPage").widget({ //上级分类参照窗口
    			name:'t_base_sales_customer',
				col:'pid',
    			singleSelect:true,
    			width:130,
    			onlyLeafCheck:false,
    			onSelect: function(data){
    				//$("#sales-id-customerAddPage").val(data.id + $("#sales-thisId-customerAddPage").val());
   					$("#sales-parentId-customerAddPage").val(data.id);
   					$("#sales-thisId-customer").val(data.id);
   					$("#sales-parentId-customer").val(data.pid);
   					var hasLevel = $("#sales-hasLevel-customerSort").val();
   					if((data.level+1)==hasLevel){
   						$.messager.alert("警告","该节点已为最大级次,不能再新增子节点!");
   						$("#sales-buttons-customershortcut").buttonWidget("disableButton", "button-hold");
   						$("#sales-buttons-customershortcut").buttonWidget("disableButton", "button-save");
   						return false;
   					}
   					else{
   						$("#sales-buttons-customershortcut").buttonWidget("enableButton", "button-hold");
   						$("#sales-buttons-customershortcut").buttonWidget("enableButton", "button-save");
   					}
   					validLengthAndUsed(customer_lenArr[(data.level + 1)], "basefiles/customerNOUsed.do", $("#sales-parentId-customerAddPage").val(), $("#customer-thisid").val(), "该编号已被使用，请另输编号！"); //验证输入长度
    			},
    			onClear: function(){
    				//$("#sales-id-customerAddPage").val($("#sales-thisId-customerAddPage").val());
    				$("#sales-parentId-customerAddPage").val("");
    				$("#sales-thisId-customer").val("");
    				$("#sales-parentId-customer").val("");
    				$("#sales-buttons-customershortcut").buttonWidget("enableButton", "button-hold");
    				$("#sales-buttons-customershortcut").buttonWidget("enableButton", "button-save");
    				validLengthAndUsed(customer_lenArr[0], "basefiles/customerNOUsed.do", $("#sales-parentId-customerAddPage").val(), $("#customer-thisid").val(), "该编号已被使用，请另输编号！"); //验证输入长度
    			}
    		});
    		
    		$("#sales-thisId-customerAddPage").change(function(){ //本级编码改变更改编码
    			$("#sales-id-customerAddPage").val($(this).val());
    		});
    		
     		//所属区域
		  	$("#customerShortcut-widget-salesarea").widget({
		  		width:130,
				name:'t_base_sales_customer',
				col:'salesarea',
				singleSelect:true,
				required:true,
				onlyLeafCheck:true
			});
			
			//所属分类
			$("#customerShortcut-widget-customersort").widget({
		  		width:130,
				name:'t_base_sales_customer',
				col:'customersort',
				singleSelect:true,
				required:true,
				onlyLeafCheck:false
			});
			
			salesuserReferWindow($("#customer-salesdeptid").val());
			
			//默认销售部门
			$("#customerShortcut-widget-salesdeptid").widget({ 
    			name:'t_base_sales_customer',
    			col:'salesdeptid',
    			width:130,
    			singleSelect:true,
    			setValueSelect:false,
    			onlyLeafCheck:false,
    			onSelect: function(data){
    				$("#customerShortcut-widget-salesdeptname").val(data.name);
    				if(data && data.id){
    					salesuserReferWindow(data.id);
    					$("#customerShortcut-widget-salesuserid").widget('clear');
    				}else{
    					salesuserReferWindow(null,null);
    					$("#customerShortcut-widget-salesuserid").widget('clear');
    				}
    			},
    			onClear:function(){
    				$("#customerShortcut-widget-salesdeptname").val("");
    				salesuserReferWindow(null,null);
    				//$("#customerShortcut-widget-salesuserid").widget('clear');
    			}
    		});	
    		
    		$("#customerShortcut-widget-salesuserid").widget('setValue',$("#customer-salesuserid").val());
			
			//默认理货员
			$("#customerShortcut-widget-tallyuserid").widget({
		  		width:130,
				name:'t_base_sales_customer',
				col:'tallyuserid',
				singleSelect:true,
				onlyLeafCheck:false,
				onSelect:function(data){
					$("#customerShortcut-widget-tallyusername").val(data.name);
				},
				onClear:function(){
					$("#customerShortcut-widget-tallyusername").val("");
				}
			});
			//默认内勤
			$("#customerShortcut-widget-indoorstaff").widget({ //销售内勤参照窗口
    			name:'t_base_sales_customer',
				col:'indoorstaff',
    			singleSelect:true,
    			width:130,
    			required:true,
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
    		
			//对方联系人
			$("#customerShortcut-widget-contact").widget({
				width:130,
				name:'t_base_sales_customer',
				col:'contact',
				singleSelect:true,
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
    			required:true,
    			width:130,
    			onlyLeafCheck:false,
    			onSelect:function(data){
    				if(data.type == '1'){//月结
    					$("#customer-select-settleday").combobox({
    						required:true
    					});
    				}else{
    					$("#customer-select-settleday").combobox({
    						required:false
    					});
    				}
    			}
    		});
     		
     		//确定按钮
     		$("#sales-save-customerShortcut").click(function(){
     			if(!$("#sales-add-customerShortcut").form('validate')){
     				return false;
     			}
     			$.messager.confirm("提醒","是否新增客户?",function(r){
					if(r){
						var clicktype = $("#sales-button-clicktype").val();
						if("1" == clicktype){
							$("#sales-state-customerAddPage").val("1");
						}
						loading("提交中..");
 						var ret = customerShortcut_AjaxConn($("#sales-add-customerShortcut").serializeJSON(),'basefiles/addCustomerShortcut.do');
    					var retJson = $.parseJSON(ret);
    					if(retJson.flag){
							$.messager.alert("提醒","新增成功!");
							if (top.$('#tt').tabs('exists',customer_title)){
			    				//var queryJSON = tabsWindow(customer_title).$("#sales-queryForm-customerShortcut").serializeJSON();
			    				tabsWindow(customer_title).$("#sales-datagrid-customerShortcut").datagrid('reload');
			    			}
							$("#sales-panel-customershortcut").panel('refresh', 'basefiles/showCustomerShortcutViewPage.do?id='+$("#sales-id-customerAddPage").val());
						}
					}else{
						$("#sales-button-clicktype").val("0");
					}
				});
     		});
     	});
     	var customerShortcut_AjaxConn = function (Data, Action) {
		    var MyAjax = $.ajax({
		        type: 'post',
		        cache: false,
		        url: Action,
		        data: Data,
		        async: false,
		        success:function(data){
		        	loaded();
		        }
		    })
		    return MyAjax.responseText;
		}
     	function salesuserReferWindow(deptid,initVal){
    		//if(deptid==null){
    		//	deptid=0;
    		//}
    		$("#customerShortcut-widget-salesuserid").widget({
    			name:'t_base_sales_customer',
    			col:'salesuserid',
    			param:[{field:'deptid',op:'equal',value:deptid}],
    			width:130,
    			async:false,
    			singleSelect:true,
    			onlyLeafCheck:false,
    			required:true,
    			initValue:initVal,
    			onSelect:function(data){
    				$("#customerShortcut-widget-salesusername").val(data.name);
    			},
				onClear:function(){
					$("#customerShortcut-widget-salesusername").val("");
				}
    		});
    	}
     </script>
  </body>
</html>
