<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>客户档案添加页面</title>
  </head>
  
  <body>
  	<div id="customer-layout-detail" class="easyui-layout" data-options="fit:true,border:false"> 
		<form action="" id="sales-form-customerAddPage" method="post" style="width:100%;height:100%;">
	    	<input type="hidden" value="${customer.state }" id="customerShortcut-hd-state" />
	    	<input type="hidden" id="customer-oldid" value="<c:out value="${customer.id }"></c:out>" />
	    	<input type="hidden" id="customer-oldsalesdeptid" value="<c:out value="${customer.salesdeptid }"></c:out>"/>
	    	<div id="customer-layout-detail-north" data-options="region:'north',border:false" style="height:84px">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="2" cellspacing="2">
	    			<tr>
	    				<td class="len100 right">本级编码：</td>
	    				<td class="len165"><input class="easyui-validatebox" name="customer.thisid" value="<c:out value="${customer.thisid }"></c:out>" readonly="readonly" style="width: 130px;"/></td>
	    				<td class="len100 right">上级客户：</td>
	    				<td class="len165"><input class="easyui-validatebox" id="sales-parent-customerAddPage" value="<c:out value="${customer.pid }"></c:out>" disabled="disabled"/></td>
	    				<td class="len100 right">状态：</td>
	    				<td class="len165">
	    					<select disabled="disabled" class="len130">
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
			    		<td><input type="text" class="easyui-validatebox" readonly="readonly" value="<c:out value="${customer.id }"></c:out>" id="sales-id-customerAddPage" style="width: 130px;"/></td>
			    		<td class="right">名称：</td>
			    		<td colspan="3"><input class="easyui-validatebox" style="width:400px;" value="<c:out value="${customer.name }"></c:out>" readonly="readonly" /></td>
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
	    						<td align="left"><input class="easyui-validatebox" name="customer.shortname" value="<c:out value="${customer.shortname }"></c:out>" maxlength="15" readonly="readonly" style="width: 130px;"/></td>
	    						<td style="width: 110px;" align="right">助记码：</td>
	    						<td align="left"><input name="customer.shortcode" value="<c:out value="${customer.shortcode }"></c:out>" maxlength="10" readonly="readonly" style="width: 130px;"/></td>
	    						<td style="width: 110px;" align="right">是否总店：</td>
				    			<td align="left"><select id="sales-isparent-customerAddPage" name="customer.islast" class="len136" disabled="disabled">
						    		<option></option>
						    		<option <c:if test="${customer.islast == '0' }">selected="selected"</c:if> value="0">是</option>
    								<option <c:if test="${customer.islast == '1' }">selected="selected"</c:if> value="1">否</option>
						    	</select></td>
	    					</tr>
	    					<tr>
	    						<td align="right">名称拼音：</td>
								<td align="left"><input type="text" name="customer.pinyin" maxlength="20" value="<c:out value="${customer.pinyin }"></c:out>" readonly="readonly" style="width: 130px;"/></td>
	    						<td align="right">店号：</td>
	    						<td align="left"><input type="text" name="customer.shopno" value="<c:out value="${customer.shopno}"></c:out>" readonly="readonly" maxlength="20" style="width: 130px;"/></td>
	    						<td align="right">税号：</td>
	    						<td align="left"><input type="text" name="customer.taxno" value="<c:out value="${customer.taxno }"></c:out>" maxlength="30" readonly="readonly" style="width: 130px;"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right">开户银行：</td>
	    						<td align="left"><input type="text" name="customer.bank" value="<c:out value="${customer.bank }"></c:out>" maxlength="50" readonly="readonly" style="width: 130px;"/></td>
	    						<td align="right">开户账号：</td>
	    						<td align="left"><input type="text" name="customer.cardno" value="<c:out value="${customer.cardno }"></c:out>" maxlength="30" readonly="readonly" style="width: 130px;"/></td>
	    						<td align="right">开户名：</td>
	    						<td align="left"><input type="text" name="customer.caraccount" value="<c:out value="${customer.caraccount }"></c:out>" maxlength="20" readonly="readonly" style="width: 130px;"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right">注册资金：</td>
	    						<td align="left"><input type="text" name="customer.fund" value="${customer.fund }" class="easyui-numberbox" data-options="max:999999999999,precision:0" readonly="readonly" style="width: 130px;"/></td>
	    						<td align="right">客户开户日期：</td>
	    						<td align="left"><input type="text" name="customer.setupdate" value="${customer.setupdate }" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" disabled="disabled" style="width: 130px;"/></td>
	    						<td align="right">门店面积m<sup>2</sup>：</td>
	    						<td align="left"><input type="text" name="customer.storearea" value="${customer.storearea }" style="width: 130px" class="easyui-numberbox" data-options="max:999999999999,precision:0" readonly="readonly" style="width: 130px;"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right">法人代表：</td>
	    						<td align="left"><input type="text" name="customer.person" value="<c:out value="${customer.person }"></c:out>" maxlength="20" readonly="readonly" style="width: 130px;"/></td>
	    						<td align="right">法人代表电话：</td>
	    						<td align="left"><input type="text" name="customer.personmobile" value="<c:out value="${customer.personmobile }"></c:out>" maxlength="20" readonly="readonly" style="width: 130px;"/></td>
	    						<td align="right">法人身份证号码：</td>
	    						<td align="left"><input type="text" name="customer.personcard" value="<c:out value="${customer.personcard }"></c:out>" maxlength="20" readonly="readonly" style="width: 130px;"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right">公司属性：</td>
	    						<td align="left"><select name="customer.nature" class="len136" disabled="disabled">
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
	    						<td align="left"><select name="customer.iscash" class="len136" disabled="disabled">
						    		<option></option>
						    		<option <c:if test="${customer.iscash == '0'}">selected="selected"</c:if> value="0">否</option>
						    		<option <c:if test="${customer.iscash == '1'}">selected="selected"</c:if> value="1">是</option>
						    	</select></td>
	    						<td align="right">是否账期：</td>
	    						<td align="left"><select name="customer.islongterm" class="len136" disabled="disabled">
						    		<option></option>
						    		<option <c:if test="${customer.islongterm == '0'}">selected="selected"</c:if> value="0">否</option>
						    		<option <c:if test="${customer.islongterm == '1'}">selected="selected"</c:if> value="1">是</option>
						    	</select></td>
	    					</tr>
	    					<tr>
	    						<td align="right">公司电话：</td>
	    						<td align="left"><input type="text" name="customer.telphone" value="<c:out value="${customer.telphone }"></c:out>" maxlength="20" readonly="readonly" style="width: 130px;"/></td>
	    						<td align="right">邮编：</td>
	    						<td align="left"><input type="text" name="customer.zip" value="${customer.zip }" class="easyui-validatebox" maxlength="6" validType="zip" readonly="readonly" style="width: 130px;"/></td>
	    						<td align="right">邮箱：</td>
	    						<td align="left"><input type="text" name="customer.email" value="${customer.email }" class="easyui-validatebox" maxlength="100" validType="email" readonly="readonly" style="width: 130px;"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right">网址：</td>
	    						<td align="left"><input type="text" name="customer.website" value="<c:out value="${customer.website }"></c:out>" maxlength="100" readonly="readonly" style="width: 130px;"/></td>
	    						<td align="right">员工人数：</td>
	    						<td align="left"><input type="text" name="customer.staffnum" value="<c:out value="${customer.staffnum }"></c:out>" maxlength="25" readonly="readonly" style="width: 130px;"/></td>
	    						<td align="right">年营业额：</td>
	    						<td align="left"><input type="text" name="customer.turnoveryear" value="${customer.turnoveryear }" class="easyui-validatebox" maxlength="24" validType="intOrFloat" readonly="readonly" style="width: 130px;"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right">初次业务时间：</td>
	    						<td align="left"><input type="text" name="customer.firstbusinessdate" value="${customer.firstbusinessdate }" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"  disabled="disabled" style="width: 130px;"/></td>
	    						<td align="right">联系人：</td>
	    						<td align="left"><input type="text" name="customer.contactname" value="<c:out value="${customer.contactname }"></c:out>" maxlength="20" readonly="readonly" style="width: 130px;"/></td>
	    						<td align="right">联系人电话:</td>
	    						<td align="left"><input type="text" name="customer.mobile" value="<c:out value="${customer.mobile }"></c:out>" style="width: 130px;" readonly="readonly" style="width: 130px;"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right">传真：</td>
	    						<td align="left"><input type="text" name="customer.faxno" class="easyui-validatebox" value="${customer.faxno }" maxlength="20" validType="faxno" readonly="readonly" style="width: 130px;"/></td>
	    						<td align="right">是否连锁：</td>
						    	<td align="left"><select name="customer.ischain" class="len136" disabled="disabled">
						    		<option></option>
						    		<option <c:if test="${customer.ischain == '0'}">selected="selected"</c:if> value="0">否</option>
						    		<option <c:if test="${customer.ischain == '1'}">selected="selected"</c:if> value="1">是</option>
						    	</select></td>
	    						<td align="right">ABC等级：</td>
	    						<td align="left">
	    							<select name="customer.abclevel" class="len136" disabled="disabled">
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
						    	<td align="left"><input type="text" name="customer.countylevel" value="<c:out value="${customer.countylevel }"></c:out>" maxlength="20" style="width: 130px" readonly="readonly"/></td>
				    			<td align="right">乡镇：</td>
						    	<td align="left"><input type="text" name="customer.villagetown" value="<c:out value="${customer.villagetown }"></c:out>" maxlength="20" style="width: 130px" readonly="readonly"/></td>
				    			<td align="right">详细地址：</td>
						    	<td align="left"><input type="text" name="customer.address" value="<c:out value="${customer.address }"></c:out>" maxlength="100" style="width: 130px" readonly="readonly"/></td>
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
			    				</select></td>
	    						<td align="right">所属地区：</td>
	    						<td align="left"><input type="text" id="sales-area-customerAddPage" name="customer.salesarea" value="<c:out value="${customer.salesarea }"></c:out>" readonly="readonly"/></td>
	    						<td align="right">默认分类：</td>
	    						<td align="left"><input type="text" readonly="readonly" value="<c:out value="${sortName }"></c:out>" id="sales-sortName-customerAddPage" readonly="readonly"/>
	    							<input type="hidden" name="customer.customersort" value="<c:out value="${customer.customersort }"></c:out>" id="sales-sort-customerAddPage"/></td>
	    					</tr>
	    					<tr>
				    			<td align="right">对账人：</td>
				    			<td align="left"><input type="text" name="customer.checker" value="<c:out value="${customer.checker }"></c:out>" maxlength="20" style="width: 130px;" readonly="readonly"/></td>
				    			<td align="right">对账人电话：</td>
				    			<td align="left"><input type="text" name="customer.checkmobile" value="<c:out value="${customer.checkmobile }"></c:out>" maxlength="20" style="width: 130px;" readonly="readonly"/></td>
				    			<td align="right">对账人邮箱：</td>
				    			<td align="left"><input type="text" name="customer.checkemail" value="${customer.checkemail }" maxlength="50" style="width: 130px;" class="easyui-validatebox" validType="email" readonly="readonly"/></td>
				    		</tr>
				    		<tr>
				    			<td align="right">付款人：</td>
				    			<td align="left"><input type="text" name="customer.payer" value="<c:out value="${customer.payer }"></c:out>" maxlength="20" style="width: 130px;" readonly="readonly"/></td>
				    			<td align="right">付款人电话：</td>
				    			<td align="left"><input type="text" name="customer.payermobile" value="<c:out value="${customer.payermobile }"></c:out>" maxlength="20" style="width: 130px;" readonly="readonly"/></td>
				    			<td align="right">付款人邮箱：</td>
				    			<td align="left"><input type="text" name="customer.payeremail" value="${customer.payeremail }" maxlength="50" style="width: 130px;" class="easyui-validatebox" validType="email" readonly="readonly"/></td>
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
				    			<td align="right">备注：</td>
	    						<td align="left" colspan="4"><textarea style="width: 397px;" rows="1" name="customer.remark" readonly="readonly"><c:out value="${customer.remark }"></c:out></textarea></td>
				    		</tr>
	    				</table>
	    			</div>
	    			<div class="tagsDiv_item">
	    				<table style="border-collapse:collapse;" border="0" cellpadding="2" cellspacing="2">
	    					<tr>
	    						<td style="width: 110px;" align="right">信用额度：</td>
	    						<td align="left"><input type="text" name="customer.credit" value="${customer.credit }" class="easyui-numberbox" data-options="max:999999999999,precision:0" readonly="readonly" style="width: 130px;"/></td>
	    						<td style="width: 110px;" align="right">信用等级：</td>
	    						<td align="left"><select name="customer.creditrating" class="len136" disabled="disabled">
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
	    						<td align="left"><input type="text" name="customer.creditdate" value="<c:out value="${customer.creditdate }"></c:out>" style="width: 130px;"  readonly="readonly"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right">月销售指标：</td>
	    						<td align="left"><input type="text" name="customer.salesmonth" value="${customer.salesmonth }" class="easyui-numberbox" data-options="max:999999999999,precision:2" readonly="readonly" style="width: 130px;"/></td>
	    						<td align="right">对账日期：</td>
	    						<td align="left"><input type="text" name="customer.reconciliationdate" value="<c:out value="${customer.reconciliationdate }"></c:out>" style="width: 130px;" readonly="readonly"/></td>
	    						<td align="right">开票日期：</td>
	    						<td align="left"><input type="text" name="customer.billingdate" value="<c:out value="${customer.billingdate }"></c:out>" style="width: 130px;" readonly="readonly"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right">到款日期：</td>
	    						<td align="left"><input type="text" name="customer.arrivalamountdate" value="<c:out value="${customer.arrivalamountdate }"></c:out>" style="width: 130px;" readonly="readonly"/></td>
	    						<td align="right">票种：</td>
	    						<td align="left"><select name="customer.tickettype" class="len136" disabled="disabled">
	    								<option></option>
	    								<option <c:if test="${customer.tickettype == '1'}">selected="selected"</c:if> value="1">增值税发票</option>
	    								<option <c:if test="${customer.tickettype == '2'}">selected="selected"</c:if> value="2">普通发票</option>
	    						</select></td>
	    						<td align="right">目标销售：</td>
	    						<td align="left"><input type="text" name="customer.targetsales" value="${customer.targetsales }" class="easyui-numberbox" data-options="max:999999999999,precision:2" readonly="readonly" style="width: 130px;"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right">年返%：</td>
	    						<td align="left"><input type="text" name="customer.yearback" value="${customer.yearback }" class="easyui-numberbox" data-options="max:100,precision:2" readonly="readonly" style="width: 130px;"/></td>
	    						<td align="right">月返%：</td>
	    						<td align="left"><input type="text" name="customer.monthback" value="${customer.monthback }" class="easyui-numberbox" data-options="max:100,precision:2" readonly="readonly" style="width: 130px;"/></td>
	    						<td align="right">配送费：</td>
	    						<td align="left"><input type="text" name="customer.dispatchingamount" value="${customer.dispatchingamount }" class="easyui-numberbox" data-options="max:999999999999,precision:2" readonly="readonly" style="width: 130px;"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right">六节一庆：</td>
	    						<td align="left"><input type="text" name="customer.sixone" value="${customer.sixone }" class="easyui-numberbox" data-options="max:999999999999,precision:2" readonly="readonly" style="width: 130px;"/></td>
	    						<td align="right">是否结算：</td>
	    						<td align="left"><select name="customer.settlement" class="len136" disabled="disabled">
	    								<option></option>
	    								<option <c:if test="${customer.settlement == '1'}">selected="selected"</c:if> value="1">是</option>
	    								<option <c:if test="${customer.settlement == '0'}">selected="selected"</c:if> value="0">否</option>
	    						</select></td>
	    						<td align="right">结算方式：</td>
	    						<td align="left"><input name="customer.settletype" value="<c:out value="${customer.settletype }"></c:out>" id="sales-settletype-customerAddPage" readonly="readonly"/></td>
	    					</tr>
	    					<tr>
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
	    						<td align="right">是否开票：</td>
	    						<td align="left"><select name="customer.billing" class="len136" disabled="disabled">
	    								<option></option>
	    								<option <c:if test="${customer.billing == '1'}">selected="selected"</c:if> value="1">是</option>
	    								<option <c:if test="${customer.billing == '0'}">selected="selected"</c:if> value="0">否</option>
	    						</select></td>
	    						<td align="right">应收依据：</td>
	    						<td align="left"><select name="customer.billtype" class="len136" disabled="disabled">
	    								<option></option>
	    								<option <c:if test="${customer.billtype == '0'}">selected="selected"</c:if> value="0">发票</option>
	    								<option <c:if test="${customer.billtype == '1'}">selected="selected"</c:if> value="1">发货</option>
	    								<option <c:if test="${customer.billtype == '2'}">selected="selected"</c:if> value="2">发票+发货</option>
	    						</select></td>
	    					</tr>
	    					<tr>
	    						<td align="right">超账期控制：</td>
	    						<td align="left"><select id="customerShortcut-select-overcontrol" name="customer.overcontrol" class="len136" onchange="selectControl()"  disabled="disabled">
	    								<option></option>
	    								<option <c:if test="${customer.overcontrol == '1'}">selected="selected"</c:if> value="1">是</option>
	    								<option <c:if test="${customer.overcontrol == '0'}">selected="selected"</c:if> value="0">否</option>
	    						</select></td>
	    						<td align="right">超账期宽限天数：</td>
	    						<td align="left"><input type="text" id="customerShortcut-input-overgracedate" value="${customer.overgracedate }" class="easyui-validatebox" maxlength="3" validType="integer" onchange="changeValue(this.value)" readonly="readonly" style="width: 130px;"/>
	    							<input type="hidden" id="customer-hd-overgracedate" name="customer.overgracedate" value="${customer.overgracedate }"/>
	    						</td>
	    						<td align="right">支付方式：</td>
	    						<td align="left"><input name="customer.paytype" value="<c:out value="${customer.paytype }"></c:out>" id="sales-paytype-customerAddPage" readonly="readonly"/></td>
	    					</tr>
	    					<tr>
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
	    						</select></td>
	    						<td align="right">默认销售部门：</td>
	    						<td align="left"><input name="customer.salesdeptid" value="<c:out value="${customer.salesdeptid }"></c:out>" id="sales-salesdeptid-customerAddPage" readonly="readonly"/></td>
	    						<td align="right">默认业务员：</td>
	    						<td align="left"><input name="customer.salesuserid" value="<c:out value="${customer.salesuserid }"></c:out>" id="sales-salesuserid-customerAddPage" readonly="readonly"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right">默认价格套：</td>
	    						<td align="left"><select name="customer.pricesort" class="len136" disabled="disabled">
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
	    						<td align="left"><input name="customer.tallyuserid" value="<c:out value="${customer.tallyuserid }"></c:out>" id="sales-tallyuserid-customerAddPage" readonly="readonly"/></td>
	    						<td align="right" style="display:none;">对应供应商：</td>
	    						<td style="display:none;"><input type="text" name="customer.supplier" id="sales-supplier-customerAddPage" readonly="readonly"/></td>
	    						<td align="right">默认内勤：</td>
	    						<td align="left"><input name="customer.indoorstaff" value="<c:out value="${customer.indoorstaff }"></c:out>" id="sales-indoorstaff-customerAddPage" readonly="readonly"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right">收款人：</td>
	    						<td><input type="text" name="customer.payeeid" value="<c:out value="${customer.payeeid }"></c:out>" id="sales-payeeid-customerAddPage" readonly="readonly"/></td>
	    						<td align="right">计划毛利率%：</td>
	    						<td align="left" colspan="3"><input type="text" name="customer.margin" value="${customer.margin }" class="easyui-validatebox" maxlength="5" validType="intOrFloat" readonly="readonly" style="width: 130px;"/></td>
	    					</tr>
	    				</table>
	    				<div style="margin-top:10px;margin-left:10px;">
	    					<div class="easyui-panel" title="最新动态" style="height:160px;width:800px;">
		    					<table style="border-collapse:collapse;" border="0" cellpadding="2" cellspacing="2">
		    						<tr>
		    							<td align="right">累计销售金额：</td>
		    							<td align="left"><input type="text" readonly="readonly" value="${customer.allsalessum }" style="width: 130px;"/></td>
		    							<td align="right">累计收款金额：</td>
		    							<td align="left"><input type="text" readonly="readonly" value="${customer.allcollectionsum }" style="width: 130px;"/></td>
		    							<td align="right">其他应付金额：</td>
		    							<td align="left"><input type="text" readonly="readonly" value="${customer.otherpayablesum }" style="width: 130px;"/></td>
		    						</tr>
		    						<tr>
		    							<td align="right">本年累计销售额：</td>
		    							<td align="left"><input type="text" readonly="readonly" value="${customer.allsalessumyear }" style="width: 130px;"/></td>
		    							<td align="right">本年累计收款额：</td>
		    							<td align="left"><input type="text" readonly="readonly" value="${customer.allcollectionsumyear }" /></td>
		    							<td align="right">应收余额：</td>
		    							<td align="left"><input type="text" readonly="readonly" value="${customer.duefromsum }" style="width: 130px;"/></td>
		    						</tr>
		    						<tr>
		    							<td align="right">最新销售日期：</td>
		    							<td align="left"><input type="text" readonly="readonly" value="${customer.newsalesdate }" style="width: 130px;"/></td>
		    							<td align="right">最新销售金额：</td>
		    							<td align="left"><input type="text" readonly="readonly" value="${customer.newsalessum }" style="width: 130px;"/></td>
		    							<td align="right">最新收款日期：</td>
		    							<td align="left"><input type="text" readonly="readonly" value="${customer.newcollectdate }" style="width: 130px;"/></td>
		    						</tr>
		    						<tr>
		    							<td align="right">最新收款金额：</td>
		    							<td align="left"><input type="text" readonly="readonly" value="${customer.newcollectsum }" style="width: 130px;"/></td>
		    							<td align="right">前30日销售金额：</td>
		    							<td align="left"><input type="text" readonly="readonly" value="${customer.salessummonth }" style="width: 130px;"/></td>
		    							<td align="right">禁用日期：</td>
		    							<td align="left"><input type="text" readonly="readonly" value="<fmt:formatDate value="${customer.closetime }" pattern="yyyy-MM-dd"/>" style="width: 130px;"/></td>
		    						</tr>
		    					</table>
		    				</div>
	    				</div>
	    			</div>
	    			<div class="tagsDiv_item">
	    				<table id="sales-contacter-customerAddPage">
	    					<thead>
	    						<tr>
	    							<th data-options="field:'id',width:80">编号</th>
	    							<th data-options="field:'name',width:80">姓名</th>
	    							<th data-options="field:'state',width:80">状态</th>
	    							<th data-options="field:'isdefault',width:100">默认联系人</th>
	    							<th data-options="field:'tel',width:80">电话</th>
	    							<th data-options="field:'fax',width:80">传真</th>
	    							<th data-options="field:'mobile',width:80">手机号码</th>
	    							<th data-options="field:'email',width:120">邮箱</th>
	    							<th data-options="field:'job',width:100">职务名称</th>
	    						</tr>
	    					</thead>
	    					<tbody>
	    						<c:forEach items="${contacterList }" var="list">
	    						<tr>
	    							<td>${list.id }</td>
	    							<td>${list.name }</td>
	    							<td>${list.state }</td>
	    							<td>
	    								<select disabled="disabled" style="width:90px;">
	    									<option value="0" <c:if test="${list.isdefault == 0 }">selected="selected"</c:if>>否</option>
	    									<option value="1" <c:if test="${list.isdefault == 1 }">selected="selected"</c:if>>是</option>
	    								</select>
	    							</td>
	    							<td>${list.tel }</td>
	    							<td>${list.fax }</td>
	    							<td>${list.mobile }</td>
	    							<td>${list.email }</td>
	    							<td>${list.job }</td>
	    						</tr>
	    						</c:forEach>
	    					</tbody>
	    				</table>
	    			</div>
	    			<div class="tagsDiv_item">
	    				<table id="sales-sortList-customerAddPage"> 
	    					<thead>
	    						<tr>
	    							<th data-options="field:'sortid',width:120">客户档案分类</th>
	    							<th data-options="field:'defaultsort',width:100">是否默认分类</th>
	    							<th data-options="field:'remark',width:280">备注</th>
	    						</tr>
	    					</thead>
	    					<tbody>
	    						<c:forEach items="${customer.sortList }" var="list">
	    						<tr>
	    							<td>${list.sortname }</td>
	    							<td>
	    								<c:if test="${list.defaultsort == 0 }">否</c:if>
	    								<c:if test="${list.defaultsort == 1 }">是</c:if>
	    							</td>
	    							<td>${list.remark }</td>
	    						</tr>
	    						</c:forEach>
	    					</tbody>
	    				</table>
	    			</div>
	    			<div class="tagsDiv_item">
	    				<table style="border-collapse:collapse;" border="0" cellpadding="2" cellspacing="2" id="sales-field-customerAddPage">
	    					<tr>
	    						<td style="width: 110px;" align="right"><span class="field-customer" rel="field01"></span></td>
	    						<td align="left"><input type="text" name="customer.field01" value="<c:out value="${customer.field01 }"></c:out>" readonly="readonly" style="width: 130px;"/></td>
	    						<td style="width: 110px;" align="right"><span class="field-customer" rel="field02"></span></td>
	    						<td align="left"><input type="text" name="customer.field02" value="<c:out value="${customer.field02 }"></c:out>" readonly="readonly" style="width: 130px;"/></td>
	    						<td style="width: 110px;" align="right"><span class="field-customer" rel="field03"></span></td>
	    						<td align="left"><input type="text" name="customer.field03" value="<c:out value="${customer.field03 }"></c:out>" readonly="readonly" style="width: 130px;"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right"><span class="field-customer" rel="field04"></span></td>
	    						<td align="left"><input type="text" name="customer.field04" value="<c:out value="${customer.field04 }"></c:out>" readonly="readonly" style="width: 130px;"/></td>
	    						<td align="right"><span class="field-customer" rel="field05"></span></td>
	    						<td align="left"><input type="text" name="customer.field05" value="<c:out value="${customer.field05 }"></c:out>" readonly="readonly" style="width: 130px;"/></td>
	    						<td align="right"><span class="field-customer" rel="field06"></span></td>
	    						<td align="left"><input type="text" name="customer.field06" value="<c:out value="${customer.field06 }"></c:out>" readonly="readonly" style="width: 130px;"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right"><span class="field-customer" rel="field07"></span></td>
	    						<td align="left"><input type="text" name="customer.field07" value="<c:out value="${customer.field07 }"></c:out>" readonly="readonly" style="width: 130px;"/></td>
	    						<td align="right"><span class="field-customer" rel="field08"></span></td>
	    						<td align="left"><input type="text" name="customer.field08" value="<c:out value="${customer.field08 }"></c:out>" readonly="readonly" style="width: 130px;"/></td>
	    						<td align="right"><span class="field-customer" rel="field09"></span></td>
	    						<td align="left"><input type="text" name="customer.field09" value="<c:out value="${customer.field09 }"></c:out>" readonly="readonly" style="width: 130px;"/></td>
	    					</tr>
	    					<tr>
	    						<td align="right"><span class="field-customer" rel="field10"></span></td>
	    						<td align="left" colspan="5"><textarea style="width: 640px;" rows="3" name="customer.field10" disabled="disabled"><c:out value="${customer.field10 }"></c:out></textarea></td>
	    					</tr>
	    					<tr>
	    						<td align="right"><span class="field-customer" rel="field11"></span></td>
	    						<td align="left" colspan="5"><textarea style="width: 640px;" rows="3" name="customer.field11" disabled="disabled"><c:out value="${customer.field11 }"></c:out></textarea></td>
	    					</tr>
	    					<tr>
	    						<td align="right"><span class="field-customer" rel="field12"></span></td>
	    						<td align="left" colspan="5"><textarea style="width: 640px;" rows="3" name="customer.field12" disabled="disabled"><c:out value="${customer.field12 }"></c:out></textarea></td>
	    					</tr>
	    				</table>
	    			</div>
	    		</div>
	    	</div>
  		</form>
	</div>
   	<script type="text/javascript">
   		$(function(){
    		$("#sales-parent-customerAddPage").widget({ //上级分类参照窗口
    			name:'t_base_sales_customer',
				col:'pid',
    			singleSelect:true,
    			width:130,
    			onlyLeafCheck:false,
    			view: true
    		});
    		$("#sales-area-customerAddPage").widget({ //销售区域参照窗口
    			name:'t_base_sales_customer',
				col:'salesarea',
    			singleSelect:true,
    			width:128,
    			onlyLeafCheck:true
    		});
    		$("#sales-settletype-customerAddPage").widget({ //结算方式参照窗口
    			name:'t_base_sales_customer',
				col:'settletype',
    			singleSelect:true,
    			width:130,
    			onlyLeafCheck:false
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
    			onlyLeafCheck:false
    		});
    		$("#sales-salesuserid-customerAddPage").widget({ //客户业务员参照窗口
				name:'t_base_sales_customer',
				col:'salesuserid',
				singleSelect:true,
				width:130,
				onlyLeafCheck:true,
				param:[{field:'deptid',op:'equal',value:$("#customer-oldsalesdeptid").val()}]
			});
    		$("#sales-tallyuserid-customerAddPage").widget({ //理货员参照窗口
    			name:'t_base_sales_customer',
				col:'tallyuserid',
    			singleSelect:true,
    			width:130,
    			onlyLeafCheck:false
    		});
    		
    		$("#sales-indoorstaff-customerAddPage").widget({ //销售内勤参照窗口
    			name:'t_base_sales_customer',
				col:'indoorstaff',
    			singleSelect:true,
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
			$("#sales-buttons-customer").buttonWidget("setDataID", {id:$("#customer-oldid").val(), state:'${customer.state}', type:'view'}); //按钮风格传入数据编号与状态及风格
			if('${customer.state}' != '1'){ //如果该数据状态不为启用，则不能添加子节点，新增按钮变灰
				$("#button-add").linkbutton("disable");
			}
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
    		$(".tags li").click(function(){ //选项选择事件
				var index = $(this).index();
				$(".tags li").removeClass("selectTag").eq(index).addClass("selectTag");
				$(".tagsDiv .tagsDiv_item").hide().eq(index).show();
				if(index == 2){
					var height = $(window).height()-119;
					if(!$("#sales-contacter-customerAddPage").hasClass("create-datagrid")){
						$("#sales-contacter-customerAddPage").datagrid({
							height:height,
							border:false,
							idField:'id',
							singleSelect:true,
							rownumbers:true
						});
						$("#sales-contacter-customerAddPage").addClass("create-datagrid");
					}
				}
				if(index == 3){
					var height = $(window).height()-119;
					if(!$("#sales-sortList-customerAddPage").hasClass("create-datagrid")){
						$("#sales-sortList-customerAddPage").datagrid({
							height:height,
							border:false,
							idField:'id',
							singleSelect:true,
							rownumbers:true
						});
						$("#sales-sortList-customerAddPage").addClass("create-datagrid")
					}
				}
			});
   		});
   	</script>
  </body>
</html>
