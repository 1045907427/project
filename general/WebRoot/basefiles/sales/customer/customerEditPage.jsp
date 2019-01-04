<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>客户档案添加页面</title>
  </head>
  
  <body>
  	<input type="hidden" id="customer-index-priceList" />
  	<div id="customer-layout-detail" class="easyui-layout" data-options="fit:true,border:false">
	  	<form action="basefiles/updateCustomer.do" id="sales-form-customerAddPage" method="post" style="width:100%;height:100%;">
	  		<input type="hidden" name="addType" id="sales-addType-customerAddPage" />
	  		<input type="hidden" name="parentId" id="sales-parentId-customerAddPage"/>
	  		<input type="hidden" id="customer-oldid" name="customer.oldid" value="<c:out value="${customer.id }"></c:out>" />
	  		<input type="hidden" id="customer-thisid" value="<c:out value="${customer.thisid }"></c:out>"/>
	  		<input type="hidden" name="sortEdit" id="sales-sortEdit-customerAddPage" value="0" /> <!-- 判断对应分类有没有修改 -->
	  		<input type="hidden" name="customer.delPriceList" id="sales-delPriceList-customerAddPage"/>
	  		<!-- 修改标识，判断有没有引用 -->
	  		<input type="hidden" id="sales-editType-customerAddPage" value="${editFlag }" />
	  		<input type="hidden" name="customer.salesdeptname" value="<c:out value="${customer.salesdeptname }"></c:out>" id="sales-salesdeptname-customerAddPage"/>
	  		<input type="hidden" name="customer.salesusername" value="<c:out value="${customer.salesusername }"></c:out>" id="sales-salesusername-customerAddPage"/>
	    	<input type="hidden" name="customer.tallyusername" value="<c:out value="${customer.tallyusername }"></c:out>" id="sales-tallyusername-customerAddPage" />
	    	<input type="hidden" id="sales-state-customerAddPage" name="customer.state" value="${customer.state}"/>
	    	<input type="hidden" name="customer.oldsalesarea" value="<c:out value="${customer.salesarea }"></c:out>"/>
	    	<input type="hidden" id="customer-oldsalesdeptid" name="customer.oldsalesdeptid" value="<c:out value="${customer.salesdeptid }"></c:out>"/>
	    	<input type="hidden" id="customer-oldsalesuserid" name="customer.oldsalesuserid" value="<c:out value="${customer.salesuserid }"></c:out>"/>
	    	<input type="hidden" name="customer.oldindoorstaff" value="<c:out value="${customer.indoorstaff }"></c:out>"/>
	    	<input type="hidden" name="customer.oldcustomersort" value="<c:out value="${customer.customersort }"></c:out>"/>
	    	<input type="hidden" value="${customer.state }" id="customerShortcut-hd-state" />
	    	<input type="hidden" id="sales-button-clicktype" value="0"/>
	    	<div id="customer-layout-detail-north" data-options="region:'north',border:false" style="height:84px">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="2" cellspacing="2">
	    			<tr>
	    				<td class="len100 right">本级编码：</td>
	    				<td class="len165"><input class="easyui-validatebox" name="customer.thisid" value="<c:out value="${customer.thisid }"></c:out>" id="sales-thisId-customerAddPage" <c:if test="${editFlag == false }">readonly="readonly"</c:if> data-options="required:true,validType:'validLength'" style="width: 130px"/></td>
	    				<td class="len100 right">上级客户：</td>
	    				<td class="len165"><input class="easyui-validatebox" id="sales-parent-customerAddPage" value="<c:out value="${customer.pid }"></c:out>" name="customer.pid" /></td>
	    				<td class="len100 right">状态：</td>
	    				<td class="len165">
	    					<select class="len136" disabled="disabled">
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
	    					</select>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="right">编码：</td>
	    				<td><input class="easyui-validatebox" value="<c:out value="${customer.id }"></c:out>" readonly="readonly" name="customer.id" id="sales-id-customerAddPage" style="width: 130px"/></td>
	    				<td class="right">名称：</td>
	    				<td colspan="3"><input class="easyui-validatebox" value="<c:out value="${customer.name }"></c:out>" style="width:400px;" name="customer.name" required="required" maxlength="50"/></td>
	    			</tr>
	    		</table>
	    		<ul class="tags">
	    			<li class="selectTag"><a href="javascript:;">基本信息</a></li>
	    			<li><a href="javascript:;">控制信息</a></li>
	    			<li><a href="javascript:;">联系人信息</a></li>
	    			<li><a href="javascript:;">对应分类</a></li>
	    			<li><a href="javascript:;">自定义信息</a></li>
	    		</ul>
	    	</div>
	    	<div id="customer-layout-detail-center" data-options="region:'center',border:false">
	    		<div class="tagsDiv">
	    			<div class="tagsDiv_item" style="display:block;">
	    				<table style="border-collapse:collapse;" border="0" cellpadding="2" cellspacing="2">
	    					<tr>
	    						<td style="width: 110px;" align="right">简称：</td>
	    						<td align="left"><input type="text" name="customer.shortname" value="<c:out value="${customer.shortname }"></c:out>" maxlength="15" class="easyui-validatebox" data-options="required:true" style="width: 130px"/></td>
	    						<td style="width: 110px;" align="right">助记码：</td>
	    						<td align="left"><input type="text" name="customer.shortcode" value="<c:out value="${customer.shortcode }"></c:out>" maxlength="10" class="easyui-validatebox" data-options="required:true" style="width: 130px"/></td>
	    						<td style="width: 110px;" align="right">是否总店：</td>
				    			<td align="left"><select id="sales-isparent-customerAddPage" name="customer.islast" class="len136 easyui-validatebox" data-options="required:true">
						    		<option></option>
						    		<option <c:if test="${customer.islast == '0' }">selected="selected"</c:if> value="0">是</option>
	   								<option <c:if test="${customer.islast == '1' }">selected="selected"</c:if> value="1">否</option>
						    	</select></td>
	    					</tr>
	    					<tr>
	    						<td align="right">名称拼音：</td>
								<td align="left"><input type="text" name="customer.pinyin" maxlength="20" value="<c:out value="${customer.pinyin }"></c:out>" style="width: 130px"/></td>
	    						<td align="right">店号：</td>
	    						<td align="left"><input type="text" name="customer.shopno" value="<c:out value="${customer.shopno}"></c:out>" maxlength="20" style="width: 130px"/></td>
	    						<td align="right">税号：</td>
	    						<td align="left"><input type="text" name="customer.taxno" value="<c:out value="${customer.taxno }"></c:out>" maxlength="30" style="width: 130px"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right">开户银行：</td>
	    						<td align="left"><input type="text" name="customer.bank" value="<c:out value="${customer.bank }"></c:out>" maxlength="50" style="width: 130px"/></td>
	    						<td align="right">开户账号：</td>
	    						<td align="left"><input type="text" name="customer.cardno" value="<c:out value="${customer.cardno }"></c:out>" maxlength="30" style="width: 130px"/></td>
	    						<td align="right">开户名：</td>
	    						<td align="left"><input type="text" name="customer.caraccount" value="<c:out value="${customer.caraccount }"></c:out>" maxlength="20" style="width: 130px"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right">注册资金：</td>
	    						<td align="left"><input type="text" name="customer.fund" value="${customer.fund }" class="easyui-numberbox" data-options="max:999999999999,precision:0" style="width: 130px"/></td>
	    						<td align="right">客户开户日期：</td>
	    						<td align="left"><input type="text" name="customer.setupdate" value="${customer.setupdate }" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style="width: 130px"/></td>
	    						<td align="right">门店面积m<sup>2</sup>：</td>
	    						<td align="left"><input type="text" name="customer.storearea" value="${customer.storearea }" class="easyui-numberbox" data-options="max:999999999999,precision:0" style="width: 130px"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right">法人代表：</td>
	    						<td align="left"><input type="text" name="customer.person" value="<c:out value="${customer.person }"></c:out>" maxlength="20" style="width: 130px"/></td>
	    						<td align="right">法人代表电话：</td>
	    						<td align="left"><input type="text" name="customer.personmobile" value="<c:out value="${customer.personmobile }"></c:out>" maxlength="20" style="width: 130px"/></td>
	    						<td align="right">法人身份证号码：</td>
	    						<td align="left"><input type="text" name="customer.personcard" value="<c:out value="${customer.personcard }"></c:out>" maxlength="20" style="width: 130px"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right">公司属性：</td>
	    						<td align="left"><select name="customer.nature" class="len136">
	    								<option></option>
	    								<c:forEach items="${natureList }" var="list">
				    						<c:choose>
					    						<c:when test="${customer.nature == list.code}">
					    						<option value="${list.code }" selected="selected">${list.codename }</option>
					    						</c:when>
					    						<c:otherwise>
					    						<option value="${list.code }">${list.codename }</option>
					    						</c:otherwise>
				    						</c:choose>
			    						</c:forEach>
	    						</select></td>
	    						<td align="right">是否现款：</td>
	    						<td align="left"><select name="customer.iscash" class="len136 easyui-validatebox" data-options="required:true">
						    		<option></option>
						    		<option <c:if test="${customer.iscash == '0'}">selected="selected"</c:if> value="0">否</option>
						    		<option <c:if test="${customer.iscash == '1'}">selected="selected"</c:if> value="1">是</option>
						    	</select></td>
	    						<td align="right">是否账期：</td>
	    						<td align="left"><select name="customer.islongterm" class="len136 easyui-validatebox" data-options="required:true">
						    		<option></option>
						    		<option <c:if test="${customer.islongterm == '0'}">selected="selected"</c:if> value="0">否</option>
						    		<option <c:if test="${customer.islongterm == '1'}">selected="selected"</c:if> value="1">是</option>
						    	</select></td>
	    					</tr>
	    					<tr>
	    						<td align="right">公司电话：</td>
	    						<td align="left"><input type="text" name="customer.telphone" value="<c:out value="${customer.telphone }"></c:out>" maxlength="20" style="width: 130px"/></td>
	    						<td align="right">邮编：</td>
	    						<td align="left"><input type="text" name="customer.zip" value="${customer.zip }" class="easyui-validatebox" maxlength="6" validType="zip" style="width: 130px"/></td>
	    						<td align="right">邮箱：</td>
	    						<td align="left"><input type="text" name="customer.email" value="${customer.email }" class="easyui-validatebox" maxlength="100" validType="email" style="width: 130px"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right">网址：</td>
	    						<td align="left"><input type="text" name="customer.website" value="<c:out value="${customer.website }"></c:out>" maxlength="100" style="width: 130px"/></td>
	    						<td align="right">员工人数：</td>
	    						<td align="left"><input type="text" name="customer.staffnum" value="<c:out value="${customer.staffnum }"></c:out>" maxlength="25" style="width: 130px"/></td>
	    						<td align="right">年营业额：</td>
	    						<td align="left"><input type="text" name="customer.turnoveryear" value="${customer.turnoveryear }" class="easyui-validatebox" maxlength="24" validType="intOrFloat" style="width: 130px"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right">初次业务时间：</td>
	    						<td align="left"><input type="text" name="customer.firstbusinessdate" value="${customer.firstbusinessdate }" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style="width: 130px"/></td>
	    						<td align="right">联系人：</td>
	    						<td align="left"><input type="text" id="sales-contacterName-customerAddPage" name="customer.contactname" value="<c:out value="${customer.contactname }"></c:out>" maxlength="20" style="width: 130px"/>
	    							<input type="hidden" id="sales-contacterId-customerAddPage" name="customer.contact" value="<c:out value="${customer.contact}"></c:out>"/>
	    						</td>
	    						<td align="right">联系人电话:</td>
	    						<td align="left"><input type="text" name="customer.mobile" id="sales-contacterMobile-customerAddPage" value="<c:out value="${customer.mobile }"></c:out>" style="width: 130px;"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right">传真：</td>
	    						<td align="left"><input type="text" name="customer.faxno" class="easyui-validatebox" value="${customer.faxno }" maxlength="20" validType="faxno" style="width: 130px"/></td>
	    						<td align="right">是否连锁：</td>
						    	<td align="left"><select name="customer.ischain" class="len136">
						    		<option></option>
						    		<option <c:if test="${customer.ischain == '0'}">selected="selected"</c:if> value="0">否</option>
						    		<option <c:if test="${customer.ischain == '1'}">selected="selected"</c:if> value="1">是</option>
						    	</select></td>
	    						<td align="right">ABC等级：</td>
	    						<td align="left">
	    							<select name="customer.abclevel" class="len136" data-options="required:true">
	    								<option></option>
	    								<option <c:if test="${customer.abclevel == 'A'}">selected="selected"</c:if> value="A">A</option>
	    								<option <c:if test="${customer.abclevel == 'B'}">selected="selected"</c:if> value="B">B</option>
	    								<option <c:if test="${customer.abclevel == 'C'}">selected="selected"</c:if> value="C">C</option>
	    								<option <c:if test="${customer.abclevel == 'D'}">selected="selected"</c:if> value="D">D</option>
	    							</select>
	    						</td>
	    					</tr>
	    					<tr>
	    						<td align="right">县级市:</td>
						    	<td align="left"><input type="text" name="customer.countylevel" value="<c:out value="${customer.countylevel }"></c:out>" maxlength="20" style="width: 130px"/></td>
				    			<td align="right">乡镇：</td>
						    	<td align="left"><input type="text" name="customer.villagetown" value="<c:out value="${customer.villagetown }"></c:out>" maxlength="20" style="width: 130px"/></td>
				    			<td align="right">详细地址：</td>
						    	<td align="left"><input type="text" name="customer.address" value="<c:out value="${customer.address }"></c:out>" maxlength="100" style="width: 130px"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right">促销分类：</td>
	    						<td align="left"><select name="customer.promotionsort" class="len136 easyui-validatebox" data-options="required:true">
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
			    				</select></td>
	    						<td align="right">所属区域：</td>
	    						<td align="left"><input type="text" id="sales-area-customerAddPage" name="customer.salesarea" value="<c:out value="${customer.salesarea }"></c:out>"/></td>
	    						<td align="right">默认分类：</td>
	    						<td align="left"><input type="text" readonly="readonly" value="<c:out value="${sortName }"></c:out>" id="sales-sortName-customerAddPage" class="easyui-validatebox" data-options="required:true"/>
	    							<input type="hidden" name="customer.customersort" value="<c:out value="${customer.customersort }"></c:out>" id="sales-sort-customerAddPage" /></td>
	    					</tr>
	    					<tr>
				    			<td align="right">对账人：</td>
				    			<td align="left"><input type="text" name="customer.checker" value="<c:out value="${customer.checker }"></c:out>" maxlength="20" style="width: 130px;"/></td>
				    			<td align="right">对账人电话：</td>
				    			<td align="left"><input type="text" name="customer.checkmobile" value="<c:out value="${customer.checkmobile }"></c:out>" maxlength="20" style="width: 130px;"/></td>
				    			<td align="right">对账人邮箱：</td>
				    			<td align="left"><input type="text" name="customer.checkemail" value="${customer.checkemail }" maxlength="50" style="width: 130px;" class="easyui-validatebox" validType="email"/></td>
				    		</tr>
				    		<tr>
				    			<td align="right">付款人：</td>
				    			<td align="left"><input type="text" name="customer.payer" value="<c:out value="${customer.payer }"></c:out>" maxlength="20" style="width: 130px;"/></td>
				    			<td align="right">付款人电话：</td>
				    			<td align="left"><input type="text" name="customer.payermobile" value="<c:out value="${customer.payermobile }"></c:out>" maxlength="20" style="width: 130px;"/></td>
				    			<td align="right">付款人邮箱：</td>
				    			<td align="left"><input type="text" name="customer.payeremail" value="${customer.payeremail }" maxlength="50" style="width: 130px;" class="easyui-validatebox" validType="email"/></td>
				    		</tr>
				    		<tr>
				    			<td align="right">店长：</td>
				    			<td align="left"><input type="text" name="customer.shopmanager" value="<c:out value="${customer.shopmanager }"></c:out>" maxlength="20" style="width: 130px;"/></td>
				    			<td align="right">店长联系电话：</td>
				    			<td align="left"><input type="text" name="customer.shopmanagermobile" value="<c:out value="${customer.shopmanagermobile }"></c:out>" maxlength="20" style="width: 130px;"/></td>
				    			<td align="right">收货人：</td>
				    			<td align="left"><input type="text" name="customer.gsreceipter" value="<c:out value="${customer.gsreceipter }"></c:out>" maxlength="20" style="width: 130px;"/></td>
				    		</tr>
				    		<tr>
				    			<td align="right">收货人联系电话：</td>
				    			<td align="left"><input type="text" name="customer.gsreceiptermobile" value="<c:out value="${customer.gsreceiptermobile }"></c:out>" maxlength="20" style="width: 130px;"/></td>
				    			<td align="right">备注：</td>
	    						<td align="left" colspan="4"><textarea style="width: 397px;" rows="1" name="customer.remark"><c:out value="${customer.remark }"></c:out></textarea></td>
				    		</tr>
	    				</table>
	    			</div>
	    			<div class="tagsDiv_item">
	    				<table style="border-collapse:collapse;" border="0" cellpadding="2" cellspacing="2">
	    					<tr>
	    						<td style="width: 110px;" align="right">信用额度：</td>
	    						<td align="left"><input type="text" name="customer.credit" value="${customer.credit }" class="easyui-numberbox" data-options="max:999999999999,precision:0,required:true" style="width: 130px"/></td>
	    						<td style="width: 110px;" align="right">信用等级：</td>
	    						<td align="left"><select name="customer.creditrating" class="len136 easyui-validatebox" data-options="required:true">
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
	    						</select></td>
	    						<td style="width: 110px;" align="right">信用期限：</td>
	    						<td align="left"><input type="text" name="customer.creditdate" value="<c:out value="${customer.creditdate }"></c:out>" style="width: 130px;"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right">月销售指标：</td>
	    						<td align="left"><input type="text" name="customer.salesmonth" value="${customer.salesmonth }" class="easyui-numberbox" data-options="max:999999999999,precision:2" style="width: 130px"/></td>
	    						<td align="right">对账日期：</td>
	    						<td align="left"><input type="text" name="customer.reconciliationdate" value="<c:out value="${customer.reconciliationdate }"></c:out>" style="width: 130px;"/></td>
	    						<td align="right">开票日期：</td>
	    						<td align="left"><input type="text" name="customer.billingdate" value="<c:out value="${customer.billingdate }"></c:out>" style="width: 130px;"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right">到款日期：</td>
	    						<td align="left"><input type="text" name="customer.arrivalamountdate" value="<c:out value="${customer.arrivalamountdate }"></c:out>" style="width: 130px;"/></td>
	    						<td align="right">票种：</td>
	    						<td align="left"><select name="customer.tickettype" class="len136">
	    								<option></option>
	    								<option <c:if test="${customer.tickettype == '1'}">selected="selected"</c:if> value="1">增值税发票</option>
	    								<option <c:if test="${customer.tickettype == '2'}">selected="selected"</c:if> value="2">普通发票</option>
	    						</select></td>
	    						<td align="right">目标销售：</td>
	    						<td align="left"><input type="text" name="customer.targetsales" value="${customer.targetsales }" class="easyui-numberbox" data-options="max:999999999999,precision:2" style="width: 130px"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right">年返%：</td>
	    						<td align="left"><input type="text" name="customer.yearback" value="${customer.yearback }" class="easyui-numberbox" data-options="max:100,precision:2" style="width: 130px"/></td>
	    						<td align="right">月返%：</td>
	    						<td align="left"><input type="text" name="customer.monthback" value="${customer.monthback }" class="easyui-numberbox" data-options="max:100,precision:2" style="width: 130px"/></td>
	    						<td align="right">配送费：</td>
	    						<td align="left"><input type="text" name="customer.dispatchingamount" value="${customer.dispatchingamount }" class="easyui-numberbox" data-options="max:999999999999,precision:2" style="width: 130px"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right">六节一庆：</td>
	    						<td align="left"><input type="text" name="customer.sixone" value="${customer.sixone }" class="easyui-numberbox" data-options="max:999999999999,precision:2" style="width: 130px"/></td>
	    						<td align="right">是否结算：</td>
	    						<td align="left"><select name="customer.settlement" class="len136">
	    								<option></option>
	    								<option <c:if test="${customer.settlement == '1'}">selected="selected"</c:if> value="1">是</option>
	    								<option <c:if test="${customer.settlement == '0'}">selected="selected"</c:if> value="0">否</option>
	    						</select></td>
	    						<td align="right">结算方式：</td>
	    						<td align="left"><input name="customer.settletype" value="<c:out value="${customer.settletype }"></c:out>" id="sales-settletype-customerAddPage" /></td>
	    					</tr>
	    					<tr>
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
	    						<td align="right">是否开票：</td>
	    						<td align="left"><select name="customer.billing" class="len136">
	    								<option></option>
	    								<option <c:if test="${customer.billing == '1'}">selected="selected"</c:if> value="1">是</option>
	    								<option <c:if test="${customer.billing == '0'}">selected="selected"</c:if> value="0">否</option>
	    						</select></td>
	    						<td align="right">应收依据：</td>
	    						<td align="left"><select name="customer.billtype" class="len136">
	    								<option></option>
	    								<option <c:if test="${customer.billtype == '0'}">selected="selected"</c:if> value="0">发票</option>
	    								<option <c:if test="${customer.billtype == '1'}">selected="selected"</c:if> value="1">发货</option>
	    								<option <c:if test="${customer.billtype == '2'}">selected="selected"</c:if> value="2">发票+发货</option>
	    						</select></td>
	    					</tr>
	    					<tr>
	    						<td align="right">超账期控制：</td>
	    						<td align="left"><select id="customerShortcut-select-overcontrol" name="customer.overcontrol" class="len136 easyui-validatebox" data-options="required:true" onchange="selectControl()">
	    								<option></option>
	    								<option <c:if test="${customer.overcontrol == '1'}">selected="selected"</c:if> value="1">是</option>
	    								<option <c:if test="${customer.overcontrol == '0'}">selected="selected"</c:if> value="0">否</option>
	    						</select></td>
	    						<td align="right">超账期宽限天数：</td>
	    						<td align="left"><input type="text" id="customerShortcut-input-overgracedate" value="${customer.overgracedate }" <c:if test="${customer.overcontrol == '0'}">disabled="disabled"</c:if> class="easyui-validatebox" maxlength="3" validType="integer" onchange="changeValue(this.value)" style="width: 130px"/>
	    							<input type="hidden" id="customer-hd-overgracedate" name="customer.overgracedate" value="${customer.overgracedate }"/>
	    						</td>
	    						<td align="right">支付方式：</td>
	    						<td align="left"><input name="customer.paytype" value="<c:out value="${customer.paytype }"></c:out>" id="sales-paytype-customerAddPage" /></td>
	    					</tr>
	    					<tr>
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
	    						</select></td>
	    						<td align="right">默认销售部门：</td>
	    						<td align="left"><input name="customer.salesdeptid" value="<c:out value="${customer.salesdeptid }"></c:out>" id="sales-salesdeptid-customerAddPage" /></td>
	    						<td align="right">默认业务员：</td>
	    						<td align="left"><input name="customer.salesuserid" value="<c:out value="${customer.salesuserid }"></c:out>" id="sales-salesuserid-customerAddPage" /></td>
	    					</tr>
	    					<tr>
	    						<td align="right">默认价格套：</td>
	    						<td align="left"><select name="customer.pricesort" class="len136" data-options="required:true">
	    							<option></option>
	    							<c:forEach items="${priceList}" var="list">
		    							<c:choose>
		    								<c:when test="${list.code == customer.pricesort}">
		    								<option value="${list.code }" selected="selected">${list.codename }</option>
		    								</c:when>
		    								<c:otherwise>
		    								<option value="${list.code }">${list.codename }</option>
		    								</c:otherwise>
		    							</c:choose>
	    							</c:forEach>
	    						</select></td>
	    						<td align="right">默认理货员：</td>
	    						<td align="left"><input name="customer.tallyuserid" value="<c:out value="${customer.tallyuserid }"></c:out>" id="sales-tallyuserid-customerAddPage" /></td>
	    						<td align="right" style="display:none;">对应供应商：</td>
	    						<td style="display:none;"><input type="text" name="customer.supplier" id="sales-supplier-customerAddPage" /></td>
	    						<td align="right">默认内勤：</td>
	    						<td align="left"><input name="customer.indoorstaff" value="<c:out value="${customer.indoorstaff }"></c:out>" id="sales-indoorstaff-customerAddPage" /></td>
	    					</tr>
	    					<tr>
	    						<td align="right">收款人：</td>
	    						<td><input type="text" name="customer.payeeid" value="<c:out value="${customer.payeeid }"></c:out>" id="sales-payeeid-customerAddPage" /></td>
	    						<td align="right">计划毛利率%：</td>
	    						<td align="left" colspan="3"><input type="text" name="customer.margin" value="${customer.margin }" class="easyui-validatebox" maxlength="5" validType="intOrFloat" style="width: 130px"/></td>
	    					</tr>
	    				</table>
	    				<div style="margin-top:10px;margin-left:10px;">
	    					<div class="easyui-panel" title="最新动态" style="height:160px;width:800px;">
		    					<table style="border-collapse:collapse;" border="0" cellpadding="2" cellspacing="2">
		    						<tr>
		    							<td align="right">累计销售金额：</td>
		    							<td align="left"><input type="text" readonly="readonly" value="${customer.allsalessum }" style="width: 130px"/></td>
		    							<td align="right">累计收款金额：</td>
		    							<td align="left"><input type="text" readonly="readonly" value="${customer.allcollectionsum }" style="width: 130px"/></td>
		    							<td align="right">其他应付金额：</td>
		    							<td align="left"><input type="text" readonly="readonly" value="${customer.otherpayablesum }" style="width: 130px"/></td>
		    						</tr>
		    						<tr>
		    							<td align="right">本年累计销售额：</td>
		    							<td align="left"><input type="text" readonly="readonly" value="${customer.allsalessumyear }" style="width: 130px"/></td>
		    							<td align="right">本年累计收款额：</td>
		    							<td align="left"><input type="text" readonly="readonly" value="${customer.allcollectionsumyear }" style="width: 130px"/></td>
		    							<td align="right">应收余额：</td>
		    							<td align="left"><input type="text" readonly="readonly" value="${customer.duefromsum }" style="width: 130px"/></td>
		    						</tr>
		    						<tr>
		    							<td align="right">最新销售日期：</td>
		    							<td align="left"><input type="text" readonly="readonly" value="${customer.newsalesdate }" style="width: 130px"/></td>
		    							<td align="right">最新销售金额：</td>
		    							<td align="left"><input type="text" readonly="readonly" value="${customer.newsalessum }" style="width: 130px"/></td>
		    							<td align="right">最新收款日期：</td>
		    							<td align="left"><input type="text" readonly="readonly" value="${customer.newcollectdate }" style="width: 130px"/></td>
		    						</tr>
		    						<tr>
		    							<td align="right">最新收款金额：</td>
		    							<td align="left"><input type="text" readonly="readonly" value="${customer.newcollectsum }" style="width: 130px"/></td>
		    							<td align="right">前30日销售金额：</td>
		    							<td align="left"><input type="text" readonly="readonly" value="${customer.salessummonth }" style="width: 130px"/></td>
		    							<td align="right">禁用日期：</td>
		    							<td align="left"><input type="text" readonly="readonly" value="<fmt:formatDate value="${customer.closetime }" pattern="yyyy-MM-dd"/>" style="width: 130px"/></td>
		    						</tr>
		    					</table>
		    				</div>
	    				</div>
	    			</div>
	    			<div class="tagsDiv_item">
		    			<table id="sales-contacter-customerAddPage"></table>
	    			</div>
	    			<div class="tagsDiv_item">
	    				<table id="sales-sortList-customerAddPage"></table>
	    			</div>
	    			<div class="tagsDiv_item">
	    				<table style="border-collapse:collapse;" border="0" cellpadding="2" cellspacing="2" id="sales-field-customerAddPage">
	    					<tr>
	    						<td style="width: 110px;" align="right"><span class="field-customer" rel="field01"></span></td>
	    						<td align="left"><input type="text" name="customer.field01" value="<c:out value="${customer.field01 }"></c:out>" maxlength="50" style="width: 130px"/></td>
	    						<td style="width: 110px;" align="right"><span class="field-customer" rel="field02"></span></td>
	    						<td align="left"><input type="text" name="customer.field02" value="<c:out value="${customer.field02 }"></c:out>" maxlength="50" style="width: 130px"/></td>
	    						<td style="width: 110px;" align="right"><span class="field-customer" rel="field03"></span></td>
	    						<td align="left"><input type="text" name="customer.field03" value="<c:out value="${customer.field03 }"></c:out>" maxlength="50" style="width: 130px"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right"><span class="field-customer" rel="field04"></span></td>
	    						<td align="left"><input type="text" name="customer.field04" value="<c:out value="${customer.field04 }"></c:out>" maxlength="50" style="width: 130px"/></td>
	    						<td align="right"><span class="field-customer" rel="field05"></span></td>
	    						<td align="left"><input type="text" name="customer.field05" value="<c:out value="${customer.field05 }"></c:out>" maxlength="50" style="width: 130px"/></td>
	    						<td align="right"><span class="field-customer" rel="field06"></span></td>
	    						<td align="left"><input type="text" name="customer.field06" value="<c:out value="${customer.field06 }"></c:out>" maxlength="50" style="width: 130px"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right"><span class="field-customer" rel="field07"></span></td>
	    						<td align="left"><input type="text" name="customer.field07" value="<c:out value="${customer.field07 }"></c:out>" maxlength="50" style="width: 130px"/></td>
	    						<td align="right"><span class="field-customer" rel="field08"></span></td>
	    						<td align="left"><input type="text" name="customer.field08" value="<c:out value="${customer.field08 }"></c:out>" maxlength="50" style="width: 130px"/></td>
	    						<td align="right"><span class="field-customer" rel="field09"></span></td>
	    						<td align="left"><input type="text" name="customer.field09" value="<c:out value="${customer.field09 }"></c:out>" maxlength="50" style="width: 130px"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right"><span class="field-customer" rel="field10"></span></td>
	    						<td align="left" colspan="5"><textarea style="width: 640px;" rows="3" name="customer.field10"><c:out value="${customer.field10 }"></c:out></textarea></td>
	    					</tr>
	    					<tr>
	    						<td align="right"><span class="field-customer" rel="field11"></span></td>
	    						<td align="left" colspan="5"><textarea style="width: 640px;" rows="3" name="customer.field11"><c:out value="${customer.field11 }"></c:out></textarea></td>
	    					</tr>
	    					<tr>
	    						<td align="right"><span class="field-customer" rel="field12"></span></td>
	    						<td align="left" colspan="5"><textarea style="width: 640px;" rows="3" name="customer.field12"><c:out value="${customer.field12 }"></c:out></textarea></td>
	    					</tr>
	    				</table>
	    			</div>
	    		</div>
			</div>
   		</form>
	</div>
   	<script type="text/javascript">
   		$(function(){
   			//selectControl();
    		validLengthAndUsed('${len}', "basefiles/customerNOUsed.do", $("#sales-parentId-customerAddPage").val(), $("#customer-thisid").val(), "该编号已被使用，请另输编号！"); //验证输入长度
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
   						$("#sales-buttons-customer").buttonWidget("disableButton", "button-hold");
   						$("#sales-buttons-customer").buttonWidget("disableButton", "button-save");
   						return false;
   					}
   					else{
   						$("#sales-buttons-customer").buttonWidget("enableButton", "button-hold");
   						$("#sales-buttons-customer").buttonWidget("enableButton", "button-save");
   					}
   					validLengthAndUsed(customer_lenArr[(data.level + 1)], "basefiles/customerNOUsed.do", $("#sales-parentId-customerAddPage").val(), $("#customer-thisid").val(), "该编号已被使用，请另输编号！"); //验证输入长度
    			},
    			onClear: function(){
    				//$("#sales-id-customerAddPage").val($("#sales-thisId-customerAddPage").val());
    				$("#sales-parentId-customerAddPage").val("");
    				$("#sales-thisId-customer").val("");
    				$("#sales-parentId-customer").val("");
    				$("#sales-buttons-customer").buttonWidget("enableButton", "button-hold");
    				$("#sales-buttons-customer").buttonWidget("enableButton", "button-save");
    				validLengthAndUsed(customer_lenArr[0], "basefiles/customerNOUsed.do", $("#sales-parentId-customerAddPage").val(), $("#customer-thisid").val(), "该编号已被使用，请另输编号！"); //验证输入长度
    			}
    		});
    		$("#sales-area-customerAddPage").widget({ //销售区域参照窗口
    			name:'t_base_sales_customer',
				col:'salesarea',
    			singleSelect:true,
    			required:true,
    			width:128,
    			onlyLeafCheck:true
    		});
    		$("#sales-settletype-customerAddPage").widget({ //结算方式参照窗口
    			name:'t_base_sales_customer',
				col:'settletype',
				required:true,
    			singleSelect:true,
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
    		$("#sales-paytype-customerAddPage").widget({ //支付方式参照窗口
    			name:'t_base_sales_customer',
				col:'paytype',
    			singleSelect:true,
    			width:130,
    			onlyLeafCheck:false
    		});
    		$("#sales-salesdeptid-customerAddPage").widget({ //销售部门参照窗口
    			name:'t_base_sales_customer',
				col:'salesdeptid',
    			singleSelect:true,
    			width:130,
    			onlyLeafCheck:false,
    			setValueSelect:false,
    			onSelect:function(data){
    				$("#sales-salesdeptname-customerAddPage").val(data.name);
    				if(data.id != $("#customer-oldsalesdeptid").val()){
    					$("#sales-salesuserid-customerAddPage").widget({ //客户业务员参照窗口
				    		name:'t_base_sales_customer',
				    		col:'salesuserid',
				    		singleSelect:true,
				    		width:130,
				    		required:true,
				    		onlyLeafCheck:true,
				    		param:[{field:'deptid',op:'equal',value:data.id}],
				    		onSelect:function(data){
				    			$("#sales-salesusername-customerAddPage").val(data.name);
				    		},
				    		onClear:function(){
				    			$("#sales-salesusername-customerAddPage").val("");
				    		}
				    	});
    				}
    			},
    			onClear:function(){
    				$("#sales-salesuserid-customerAddPage").widget({ //客户业务员参照窗口
			    		name:'t_base_sales_customer',
			    		col:'salesuserid',
			    		singleSelect:true,
			    		width:130,
			    		required:true,
			    		onlyLeafCheck:true,
			    		onSelect:function(data){
		    				$("#sales-salesusername-customerAddPage").val(data.name);
		    			},
			    		onClear:function(){
			    			$("#sales-salesusername-customerAddPage").val("");
			    		}
			    	});
    			}
    		});
    		$("#sales-salesuserid-customerAddPage").widget({ //客户业务员参照窗口
				name:'t_base_sales_customer',
				col:'salesuserid',
				singleSelect:true,
				width:130,
				required:true,
				onlyLeafCheck:true,
				initValue:$("#customer-oldsalesuserid").val(),
				onSelect:function(data){
	    			$("#sales-salesusername-customerAddPage").val(data.name);
	    		},
	    		onClear:function(){
	    			$("#sales-salesusername-customerAddPage").val("");
	    		}
			});
    		$("#sales-tallyuserid-customerAddPage").widget({ //理货员参照窗口
    			name:'t_base_sales_customer',
				col:'tallyuserid',
    			singleSelect:true,
    			width:130,
    			onlyLeafCheck:false,
    			onSelect:function(data){
	    			$("#sales-tallyusername-customerAddPage").val(data.name);
	    		}
    		});
    		
    		$("#sales-indoorstaff-customerAddPage").widget({ //销售内勤参照窗口
    			name:'t_base_sales_customer',
				col:'indoorstaff',
    			singleSelect:true,
    			required:true,
    			width:130,
    			onlyLeafCheck:false
    		});
    		
    		$("#sales-payeeid-customerAddPage").widget({ 
    			name:'t_base_sales_customer',
				col:'payeeid',
    			singleSelect:true,
    			width:130,
    			onlyLeafCheck:false
    		});
    		
    		$("#sales-supplier-customerAddPage").widget({ //供应商参照窗口
    			name:'t_base_sales_customer',
				col:'supplier',
    			singleSelect:true,
    			width:130,
    			onlyLeafCheck:false
    		});
    		$("#sales-thisId-customerAddPage").change(function(){ //本级编码改变更改编码
    			$("#sales-id-customerAddPage").val($(this).val());
    		});
    		$("#sales-buttons-customer").buttonWidget("setDataID", {id:$("#customer-oldid").val(),state:'${customer.state}',type:'edit'}); //按钮风格传入数据编号和状态及风格
			$.ajax({ //获取自定义字段的名称，数据来源数据字典
				url:'basefiles/customerDIYFieldName.do',
				dataType:'json',
				type:'post',
				success:function(json){
					$("#sales-field-customerAddPage").find(".field-customer").each(function(){
						var rel = $(this).attr("rel");
						$(this).text(json[rel] + "：");
					});
				}
			});
    		$("#sales-sortName-customerAddPage").dblclick(function(){ //选择对应分类选项
    			selectTags(3);
    		});	
    		$("#sales-contacterName-customerAddPage").dblclick(function(){ //选择联系人选项
    			selectTags(2);
    		});
    		$(".tags li").click(function(){ //选项选择事件
				var index = $(this).index();
				$(".tags li").removeClass("selectTag").eq(index).addClass("selectTag");
				$(".tagsDiv .tagsDiv_item").hide().eq(index).show();
				if(index == 2){
					var height = $(window).height()-119;
					if(!$("#sales-contacter-customerAddPage").hasClass("create-datagrid")){
						$("#sales-contacter-customerAddPage").datagrid({ //联系人列表
							height:height,
							columns:[[
										{field:'id', title:'编码', width:80,
											formatter: function(value,row,index){
												return value+ "<input type='hidden' name='contacterId' value='"+value+"' />"
											}
										},
										{field:'name', title:'姓名', width:80},
										{field:'state', title:'状态', width:80},
										{field:'isdefault', title:'默认联系人', width:100,
											formatter: function(value,row,index){
												return "<select style='width:80px;' onchange=\"changeContacterDefault(this,'"+row.id+"','"+row.name+"')\" class='contacter-isdefault' name='contacterIsdefault'><option value='0' "+(value=="0"?"selected='selected'":"")+">否</option><option value='1' "+(value=="1"?"selected='selected'":"")+">是<option></select>"
											}
										},
										{field:'tel', title:'电话', width:80},
										{field:'fax', title:'传真', width:80},
										{field:'mobile', title:'手机号码', width:80},
										{field:'email', title:'邮箱', width:80},
										{field:'job', title:'职务名称', width:80},
									]],						
							idField:'id',
					 		singleSelect:true,
					 		rownumbers:true,
					 		border:false,
					 		url:"basefiles/getContacterListCustomer.do?type=1&id="+$("#customer-oldid").val()
						});
						$("#sales-contacter-customerAddPage").addClass("create-datagrid");
					}
				}
				if(index == 3){
					var height = $(window).height()-146;
					if(!$sortList.hasClass("create-datagrid")){
						$sortList.datagrid({ //客户分类行编辑
							height:height,
							border:false,
							idField:'sortid',
							singleSelect:true,
							rownumbers:true,
							url:"basefiles/getCustomerAndSortList.do?id="+$("#customer-oldid").val(),
							columns:[[
										{field:'sortid',title:'客户档案分类',width:120,
											formatter: function(value,row,index){
												return row.sortname + "<input type='hidden' value='"+value+"' name='customerAndSort.sortid' />" + "<input type='hidden' value='"+row.sortname+"' name='customerAndSort.sortname' />";
											},
											editor:{
												type:'comborefer',
												options:{
													name:'t_base_sales_customer',
													col:'customersort',
													singleSelect:true
												}
											}
										},
										{field:'defaultsort',title:'是否默认分类',width:120,
											formatter: function(value,row,index){
												if(value == "0"){
													return "否 <input type='hidden' value='"+value+"' name='customerAndSort.defaultsort' />";
												}
												if(value == "1"){
													return "是 <input type='hidden' value='"+value+"' name='customerAndSort.defaultsort' />";
												}
											},
											editor:{
												type:'defaultSelect',
												options:{
													valueField:'value',  
					                                textField:'text',  
													classid:'customerSortDefault',
					                                data:[
					                                		{value:'1',text:'是'},
					                                		{value:'0',text:'否'}
					                                	],
													onChange:function(newValue){
														
													}
												}
											}
										},
										{field:'remark',title:'备注',width:280,
											formatter: function(value, row, index){
												if(null == value){
													value = "";
												}
												return value + "<input type='hidden' value='"+value+"' name='customerAndSort.remark' />";
											},
											editor:{
												type:'validatebox'
											}
										}
									]],
							toolbar : [{
				                text : "添加", //添加新行
				                iconCls : "button-add",
				                handler : function() {
				                	if(endEditing()){
				                		if(disabledSelectAdd()){
				                			$sortList.datagrid('appendRow',{defaultsort:'disabled'});
				                		}
				                		else{
					                		$sortList.datagrid('appendRow',{defaultsort:'0'});
					                	}  
						                editIndex = $sortList.datagrid('getRows').length-1;  
						                $sortList.datagrid('selectRow', editIndex).datagrid('beginEdit', editIndex);
					                }
				                }
				        	},
				        	{
				        		text: "确定", //确定行编辑完成
				        		iconCls: "button-save",
				        		handler: function(){
				        			if(endEditing()){return}
				        			if(!$sortList.datagrid('validateRow', editIndex)){return}
				        			var ed = $sortList.datagrid('getEditor', {index:editIndex,field:'sortid'});
						            var sortname = $(ed.target).widget("getText");
						   			$sortList.datagrid('getRows')[editIndex]['sortname'] = sortname;
						   			$sortList.datagrid('endEdit', editIndex);
						   			var data = $sortList.datagrid('getRows');
									var defaultIndex = -1;
									for(var i=0;i<data.length;i++){
										if(data[i]['defaultsort'] == "1"){
											defaultIndex = i;
											$("#sales-sortName-customerAddPage").val(data[i]['sortname']);
    										$("#sales-sort-customerAddPage").val(data[i]['sortid']);
										}
									}
									if(defaultIndex == -1){
										$("#sales-sortName-customerAddPage").val("");
    									$("#sales-sort-customerAddPage").val("");
									}
						   			editIndex = undefined;
						   			$sortList.datagrid('clearSelections');
				        		}
				        	},
				        	{
				        		text: "修改", //修改选中行
				        		iconCls: "button-edit",
				        		handler: function(){
				        			var row = $sortList.datagrid('getSelected');
						            editIndex = $sortList.datagrid('getRowIndex', row);
				        			if(endEditing()){return}
				        			$("#customer-index-sortList").val(editIndex);
				        			var rowData = $sortList.datagrid('getSelected', editIndex);
						   			$sortList.datagrid('beginEdit', editIndex);
				        			disabledSelectEdit(editIndex);
				        		}
				        	},
				        	{
				        		text: "删除", //删除选中行
				        		iconCls: "button-delete",
				        		handler: function(){
				        			var df = false;
				        			var row = $sortList.datagrid('getSelected');
				        			if(row.defaultsort == "1"){
				        				df = true;
				        			}
						            editIndex = $sortList.datagrid('getRowIndex', row);
						            $sortList.datagrid('cancelEdit', editIndex).datagrid('deleteRow', editIndex);
						   			if(df){
						   				$("#sales-sortName-customerAddPage").val("");
    									$("#sales-sort-customerAddPage").val("");
						   			}
						   			$sortList.datagrid('clearSelections');
						   			editIndex = undefined;
				        		}
				        	}],
				        	onClickRow: function(rowIndex, rowData){
				        		if(!$sortList.datagrid('validateRow', editIndex)){
				        			$sortList.datagrid('selectRow',editIndex);
				        			return false;
				        		}
				        		var index = parseInt($("#customer-index-sortList").val());
			   					if(!endEditing() && $sortList.datagrid('validateRow', index)){
			   						var ed = $sortList.datagrid('getEditor', {index:editIndex,field:'sortid'});
						            var sortname = $(ed.target).widget("getText");
						   			$sortList.datagrid('getRows')[editIndex]['sortname'] = sortname;
						   			$sortList.datagrid('endEdit', editIndex);
						   			var data = $sortList.datagrid('getRows');
									var defaultIndex = -1;
									for(var i=0;i<data.length;i++){
										if(data[i]['defaultsort'] == "1"){
											defaultIndex = i;
											$("#sales-sortName-customerAddPage").val(data[i]['sortname']);
    										$("#sales-sort-customerAddPage").val(data[i]['sortid']);
										}
									}
									if(defaultIndex == -1){
										$("#sales-sortName-customerAddPage").val("");
    									$("#sales-sort-customerAddPage").val("");
									}
			   					}
			   					$sortList.datagrid('endEdit', editIndex);
						   		editIndex = undefined;
				        	}
						});
						$sortList.addClass("create-datagrid")
					}
				}
			});
   		});
		var editIndex = undefined;
		var priceEditIndex = undefined;
		var $sortList = $("#sales-sortList-customerAddPage"); //分类datagrid的div对象
		var $priceList = $("#sales-priceList-customerAddPage");//合同价datagrid的对象
		function endEditing(){
	  		if (editIndex == undefined){
	  			return true
	  		}
	  		else{
	  			return false;
	  		}
		}
		function priceEndEditing(){
			if (priceEditIndex == undefined){
	  			return true
	  		}
	  		else{
	  			return false;
	  		}
		}
		function disabledSelectAdd(){ //添加行时判断默认分类选项是否可用
			var data = $sortList.datagrid('getRows');
			if(data.length == 0){return false}
			for(var i=0;i<data.length;i++){
				if(data[i]['defaultsort'] == "1"){
					return true;
				}
			}
			return false;
		}
		function disabledSelectEdit(editIndex){ //修改行时判断默认分类选项是否可用
			var data = $sortList.datagrid('getRows');
			if(data[editIndex]['defaultsort'] == "1"){
				$(".customerSortDefault").removeAttr("disabled");
			}
			else{
				var bl = false;
				for(var i=0;i<data.length;i++){
					if(data[i]['defaultsort'] == "1"){
						bl = true;
					}
				}
				if(bl){
					$(".customerSortDefault").attr("disabled", "disabled");
				}
				else{
					$(".customerSortDefault").removeAttr("disabled");
				}
			}
		}
   		function selectTags(index){ //选择第index个选项
   			$(".tags li").eq(index).click();
   		}
   		function changeContacterDefault(obj, id, name){ //设置默认联系人
    		if($(obj).val() == "1"){
    			$(".contacter-isdefault").val('0');
    			$(obj).val("1");
    			$("#sales-contacterName-customerAddPage").val(name);
    			$("#sales-contacterId-customerAddPage").val(id);
    			var row = $("#sales-contacter-customerAddPage").datagrid('getSelected');
    			$("#sales-contacterMobile-customerAddPage").val(row.mobile);
    		}
    		else{
    			$("#sales-contacterName-customerAddPage").val("");
    			$("#sales-contacterId-customerAddPage").val("");
    			$("#sales-contacterMobile-customerAddPage").val("");
    		}
    	};
   	</script>
  </body>
</html>
