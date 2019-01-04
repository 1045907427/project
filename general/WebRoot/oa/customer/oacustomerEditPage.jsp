<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<title>新客户联络票编辑页面</title>
	<%@include file="/include.jsp" %>
  </head>
  
  <body>
	<div class="easyui-panel" data-options="fit:true,border:false">
		<!-- <div class="buttonBG" id="oa-buttons-oacustomerEditPage" style="height:26px;"></div> -->
		<div class="easyui-layout" data-options="fit:true,border:false">
			<form action="oa/editOaCustomer.do" id="oa-form-oacustomerEditPage">
				<input type="hidden" name="type" id="type" value="<c:out value='${type }' escapeXml='true'></c:out>"/>
				<input type="hidden" name="customer.id" id="id" value="<c:out value='${customer.id }' escapeXml='true'></c:out>"/>
				<div data-options="region:'center',border:false">
					<div class="easyui-panel baseinfo-oacustomer" title="客户基本信息">
						<table style="border-collapse:collapse;" border="0" cellpadding="2" cellspacing="2">
							<tr>
								<td class="left len100">客户编码：</td>
								<td class="len180"><input class="easyui-validatebox" style="margin-left: 0px;" name="customer.customerid" id="oa-customerid-oacustomerEditPage" value="<c:out value='${customer.customerid }' escapeXml='true'></c:out>" data-options="required:true,validType:['validLength[${len }]']" autocomplete="off" maxlength="20"/></td>
								<td class="right len80">客户名称：</td>
								<td class="len180"><input class="easyui-validatebox" style="margin-left: 0px;" name="customer.customername" id="oa-name-oacustomerEditPage" value="<c:out value='${customer.customername }' escapeXml='true'></c:out>" autocomplete="off" data-options="required:true" maxlength="100"/></td>
								<td class="right len80">助记符：</td>
								<td class="len180"><input class="easyui-validatebox" style="margin-left: 0px;" name="customer.shortcode" id="oa-shortcode-oacustomerEditPage" value="<c:out value='${customer.shortcode }' escapeXml='true'></c:out>" autocomplete="off" data-options="required:true" maxlength="20"/></td>
							</tr>
							<tr>
								<td class="left">客户简称：</td>
								<td class=""><input class="easyui-validatebox" style="margin-left: 0px;" name="customer.shortname" id="oa-shortname-oacustomerEditPage" value="<c:out value='${customer.shortname }' escapeXml='true'></c:out>" autocomplete="off" data-options="required:true" maxlength="50"/></td>
								<td class="right">联系人：</td>
								<td class=""><input class="easyui-validatebox" style="margin-left: 0px;" name="customer.contact" id="oa-contact-oacustomerEditPage" value="<c:out value='${customer.contact }' escapeXml='true'></c:out>" autocomplete="off" data-options="required:true" maxlength="20"/></td>
								<td class="right">联系电话：</td>
								<td class=""><input class="easyui-validatebox" style="margin-left: 0px;" name="customer.mobile" id="oa-mobile-oacustomerEditPage" value="<c:out value='${customer.mobile }' escapeXml='true'></c:out>" autocomplete="off" data-options="required:true" maxlength="50"/></td>
							</tr>
							<tr>
								<td class="left">详细地址：</td>
								<td class=""><input class="easyui-validatebox" style="margin-left: 0px;" name="customer.address" id="oa-address-oacustomerEditPage"value="<c:out value='${customer.address }' escapeXml='true'></c:out>" autocomplete="off" data-options="required:true" maxlength="100"/></td>
								<td class="right">注册资金：</td>
								<td class=""><input class="easyui-validatebox easyui-numberbox" style="margin-left: 0px;" data-options="max:999999999999,precision:0" name="customer.fund" id="oa-fund-oacustomerEditPage" value="<c:out value='${customer.fund }' escapeXml='true'></c:out>" autocomplete="off"/></td>
								<td class="right">门店面积：</td>
								<td class=""><input class="easyui-validatebox easyui-numberbox" style="margin-left: 0px;" data-options="max:999999999999,precision:0" name="customer.storearea" id="oa-storearea-oacustomerEditPage" value="<c:out value='${customer.storearea }' escapeXml='true'></c:out>" autocomplete="off"/></td>
							</tr>
							<tr>
								<td class="left">所属区域：</td>
								<td class=""><input class="easyui-validatebox" style="margin-left: 0px;" name="customer.salesarea" id="oa-salesarea-oacustomerEditPage" value="<c:out value='${customer.salesarea }' escapeXml='true'></c:out>"/></td>
								<td class="right">所属分类：</td>
								<td class=""><input class="easyui-validatebox" style="margin-left: 0px;" name="customer.customersort" id="oa-customersort-oacustomerEditPage" value="<c:out value='${customer.customersort }' escapeXml='true'></c:out>"/></td>
								<td class="right">默认价格套：</td>
								<td class="">
									<select name="customer.pricesort" class="easyui-validatebox len130" style="margin-left: 0px;" data-options="required:true">
										<option></option>
										<c:forEach items="${priceList}" var="list">
											<c:choose>
												<c:when test="${list.code == customer.pricesort }"><option value="<c:out value='${list.code }' escapeXml='true'></c:out>" selected="selected">${list.codename }</option></c:when>
												<c:otherwise><option value="<c:out value='${list.code }' escapeXml='true'></c:out>">${list.codename }</option></c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td class="left">默认业务员：</td>
								<td class=""><input class="easyui-validatebox" style="margin-left: 0px;" name="customer.salesuserid" id="oa-salesuserid-oacustomerEditPage" value="<c:out value='${customer.salesuserid }' escapeXml='true'></c:out>"/></td>
								<td class="right">收&nbsp;款&nbsp;人：</td>
								<td class=""><input class="easyui-validatebox" style="margin-left: 0px;" name="customer.payeeid" id="oa-payeeid-oacustomerEditPage" value="<c:out value='${customer.payeeid }' escapeXml='true'></c:out>"/></td>
								<td class="right">默认内勤：</td>
								<td class=""><input class="easyui-validatebox" style="margin-left: 0px;" name="customer.indoorstaff" id="oa-indoorstaff-oacustomerEditPage" value="<c:out value='${customer.indoorstaff }' escapeXml='true'></c:out>"/></td>
							</tr>
							<tr>
								<td class="left">促销分类：</td>
								<td class="">
									<select name="customer.promotionsort" class="easyui-validatebox len130" style="margin-left: 0px;" id="oa-promotionsort-oacustomerEditPage" data-options="required:true">
										<option></option>
										<c:forEach items="${promotionsortList }" var="list">
											<c:choose>
												<c:when test="${list.code == customer.promotionsort}"><option value="<c:out value='${list.code }' escapeXml='true'></c:out>" selected="selected">${list.codename }</option></c:when>
												<c:otherwise><option value="<c:out value='${list.code }' escapeXml='true'></c:out>">${list.codename }</option></c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</td>
								<td class="right">信用等级：</td>
								<td class="">
									<select name="customer.creditrating" class="easyui-validatebox len130" style="margin-left: 0px;" id="oa-creditrating-oacustomerEditPage" data-options="required:true">
										<option></option>
										<c:forEach items="${creditratingList }" var="list">
											<c:choose>
												<c:when test="${list.code == customer.creditrating}"><option value="<c:out value='${list.code }' escapeXml='true'></c:out>" selected="selected">${list.codename }</option></c:when>
												<c:otherwise><option value="<c:out value='${list.code }' escapeXml='true'></c:out>">${list.codename }</option></c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</td>
								<td class="right">信用额度：</td>
								<td class=""><input class="easyui-validatebox easyui-numberbox" style="margin-left: 0px;" data-options="max:999999999999,precision:0,required:true" name="customer.credit" id="oa-credit-oacustomerEditPage" value="<c:out value='${customer.credit }' escapeXml='true'></c:out>"/></td>
							</tr>
							<tr>
								<td class="left">结算方式：</td>
								<td class=""><input class="easyui-validatebox" style="margin-left: 0px;" name="customer.settletype" id="oa-settletype-oacustomerEditPage" value="<c:out value='${customer.settletype }' escapeXml='true'></c:out>"/></td>
								<td class="right">结算日：</td>
								<td class="">
									<select id="oa-settleday-oacustomerEditPage" name="customer.settleday" class="easyui-validatebox len130" style="margin-left: 0px;">
										<option></option>
										<c:forEach items="${dayList}" var="day">
											<c:choose>
												<c:when test="${day == customer.settleday}"><option value="<c:out value='${day }' escapeXml='true'></c:out>" selected="selected">${day }</option></c:when>
												<c:otherwise><option value="<c:out value='${day }' escapeXml='true'></c:out>">${day }</option></c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</td>
								<td class="right">核销方式：</td>
								<td class="">
									<select name="customer.canceltype" id="oa-canceltype-oacustomerEditPage" class="easyui-validatebox len130" style="margin-left: 0px;" data-options="required:true">
										<option></option>
										<c:forEach items="${canceltypeList }" var="list">
											<c:choose>
												<c:when test="${list.code == customer.canceltype}"><option value="<c:out value='${list.code }' escapeXml='true'></c:out>" selected="selected">${list.codename }</option></c:when>
												<c:otherwise><option value="<c:out value='${list.code }' escapeXml='true'></c:out>">${list.codename }</option></c:otherwise>
											</c:choose>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td class="left">是否现款：</td>
								<td class="">
									<select name="customer.iscash" id="oa-iscash-oacustomerEditPage" class="easyui-validatebox len130" style="margin-left: 0px;">
										<option></option>
										<option value="0" <c:if test="${customer.iscash == '0' }">selected="selected"</c:if>>否</option>
										<option value="1" <c:if test="${customer.iscash == '1' }">selected="selected"</c:if>>是</option>
									</select>
								</td>
								<td class="right">是否账期：</td>
								<td class="">
									<select name="customer.islongterm" id="oa-islongterm-oacustomerEditPage" class="easyui-validatebox len130" style="margin-left: 0px;">
										<option></option>
										<option value="0" <c:if test="${customer.islongterm == '0' }">selected="selected"</c:if>>否</option>
										<option value="1" <c:if test="${customer.islongterm == '1' }">selected="selected"</c:if>>是</option>
									</select>
								</td>
								<td colspan="2">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" id="work-allot-oacustomerEditPage" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" >分配品牌业务员</a>
								</td>
							</tr>
							<tr>
								<td class="left">备&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
								<td colspan="5"><input class="easyui-validatebox" style="width: 660px; margin-left: 0px;" name="customer.remark" id="oa-remark-oacustomerEditPage" value="<c:out value='${customer.remark }' escapeXml='true'></c:out>" autocomplete="off"/></td>
							</tr>
						</table>
					</div>
					<div class="easyui-panel baseinfo-oacustomer" title="谈判内容">
						<input type="hidden" name="customerBrandJSON" id="oa-customerBrandJSON-oacustomerEditPage"/>
						<table id="oa-datagrid-oacustomerEditPage">
							<tr></tr>
							<tr></tr>
							<tr></tr>
							<tr></tr>
							<tr></tr>
							<tr></tr>
							<tr></tr>
							<tr></tr>
							<tr></tr>
							<tr></tr>
						</table>
					</div>
					<div class="easyui-panel baseinfo-oacustomer" title="最终结果">
						<table style="border-collapse:collapse;" border="0" cellpadding="2" cellspacing="2">
							<tr>
								<td class="len90 left">客户要求：</td>
								<td class="left" style="width: 600px;"><textarea style="width:650px; height: 40px;" name="customer.demand" id="oa-demand-oacustomerEditPage" autocomplete="off"><c:out value='${customer.demand }' escapeXml='true'></c:out></textarea></td>
							</tr>
							<tr>
								<td class="len90 left">谈判结果：</td>
								<td class="left" style="width: 600px;"><textarea style="width:650px; height: 40px;" name="customer.talkresult" id="oa-talkresult-oacustomerEditPage" autocomplete="off"><c:out value='${customer.talkresult }' escapeXml='true'></c:out></textarea></td>
							</tr>
							<tr>
								<td class="len90 left">反馈情况：</td>
								<td class="left" style="width: 600px;"><textarea style="width:650px; height: 40px;" name="customer.feedback" id="oa-feedback-oacustomerEditPage" autocomplete="off"><c:out value='${customer.feedback }' escapeXml='true'></c:out></textarea></td>
							</tr>
							<tr>
								<td class="len90 left">合同期限：</td>
								<td class="left" style="width: 600px;"><input class="easyui-validatebox Wdate" name="customer.pactdeadline" id="oa-pactdeadline-oacustomerEditPage" value="<c:out value='${customer.pactdeadline }' escapeXml='true'></c:out>" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style=" margin-left: 0px;" autocomplete="off"/></td>
							</tr>
						</table>
					</div>

				</div>
			</form>
		</div>
	</div>
	<script type="text/javascript">
<!--
	$(function(){
		validLengthAndUsed('2', "oa/checkCustomerUsed.do", '', '', "该编号已被使用，请另输编号！");
	});

	var $line = $("#oa-datagrid-oacustomerEditPage");
	var editItem  = $('#oa-editRow-oacustomerPage');
				
	var $oa_salesarea = $("#oa-salesarea-oacustomerEditPage");
	var $oa_payeeid = $('#oa-payeeid-oacustomerEditPage');
	var $oa_customersort = $('#oa-customersort-oacustomerEditPage');
	var $oa_salesuserid = $("#oa-salesuserid-oacustomerEditPage");
	var $oa_indoorstaff = $("#oa-indoorstaff-oacustomerEditPage");
	var $oa_settletype = $("#oa-settletype-oacustomerEditPage");
	var $oa_settleday = $("#oa-settleday-oacustomerEditPage");
	var $oa_workAllot = $('#work-allot-oacustomerEditPage');
	var $oa_datagrid = $('#oa-datagrid-oacustomerEditPage');
			
	var $oa_customerid  = $('#oa-id-oacustomerEditPage');
	
-->
	</script>
  </body>
</html>