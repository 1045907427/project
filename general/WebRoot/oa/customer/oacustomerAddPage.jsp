<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<title>新客户登录票添加页面</title>
	<%@include file="/include.jsp" %>
  </head>
  
  <body>
	<div class="easyui-panel" data-options="fit:true,border:false">
		<!-- <div class="buttonBG" id="oa-buttons-oacustomerAddPage" style="height:26px;"></div> -->
		<div class="easyui-layout" data-options="fit:true,border:false">
			<form action="oa/addOaCustomer.do" id="oa-form-oacustomerAddPage" method="post">
				<input type="hidden" name="type" value="add"/>
				<input type="hidden" name="id" id="id"/>
				<div data-options="region:'center',border:false">
					<input type="hidden" name="customer.status" value="2"/>
					<div class="easyui-panel baseinfo-oacustomer" title="客户基本信息">
						<table style="border-collapse:collapse;" border="0" cellpadding="2" cellspacing="2">
							<tr>
								<td class="left len80">客户编码：</td>
								<td class="len180"><input class="easyui-validatebox" style="margin-left: 0px;" name="customer.customerid" id="oa-customerid-oacustomerAddPage" data-options="required:true,validType:['validLength[${len }]']" autocomplete="off" maxlength="20"/></td>
								<td class="right len80">客户名称：</td>
								<td class="len180"><input class="easyui-validatebox" style="margin-left: 0px;" name="customer.customername" id="oa-name-oacustomerAddPage" autocomplete="off" data-options="required:true" maxlength="100"/></td>
								<td class="right len80">助记符：</td>
								<td class="len180"><input class="easyui-validatebox" style="margin-left: 0px;" name="customer.shortcode" id="oa-shortcode-oacustomerAddPage" autocomplete="off" data-options="required:true" maxlength="20"/></td>
							</tr>
							<tr>
								<td class="left">客户简称：</td>
								<td class=""><input class="easyui-validatebox" style="margin-left: 0px;" name="customer.shortname" id="oa-shortname-oacustomerAddPage" autocomplete="off" data-options="required:true" maxlength="50"/></td>
								<td class="right">联系人：</td>
								<td class=""><input class="easyui-validatebox" style="margin-left: 0px;" name="customer.contact" id="oa-contact-oacustomerAddPage" autocomplete="off" data-options="required:true" maxlength="20"/></td>
								<td class="right">联系电话：</td>
								<td class=""><input class="easyui-validatebox" style="margin-left: 0px;" name="customer.mobile" id="oa-mobile-oacustomerAddPage" autocomplete="off" data-options="required:true" maxlength="50"/></td>
							</tr>
							<tr>
								<td class="left">详细地址：</td>
								<td class=""><input class="easyui-validatebox" style="margin-left: 0px;" name="customer.address" id="oa-address-oacustomerAddPage" autocomplete="off" data-options="required:true" maxlength="100"/></td>
								<td class="right">注册资金：</td>
								<td class=""><input class="easyui-validatebox easyui-numberbox" style="margin-left: 0px;" data-options="max:999999999999,precision:0" name="customer.fund" id="oa-fund-oacustomerAddPage" autocomplete="off"/></td>
								<td class="right">门店面积：</td>
								<td class=""><input class="easyui-validatebox easyui-numberbox" style="margin-left: 0px;" data-options="max:999999999999,precision:0" name="customer.storearea" id="oa-storearea-oacustomerAddPage" autocomplete="off"/></td>
							</tr>
							<tr>
								<td class="left">所属区域：</td>
								<td class=""><input class="easyui-validatebox" style="margin-left: 0px;" name="customer.salesarea" id="oa-salesarea-oacustomerAddPage"/></td>
								<td class="right">所属分类：</td>
								<td class=""><input class="easyui-validatebox" style="margin-left: 0px;" name="customer.customersort" id="oa-customersort-oacustomerAddPage"/></td>
								<td class="right">默认价格套：</td>
								<td class="">
									<select name="customer.pricesort" class="easyui-validatebox len130" style="margin-left: 0px;" data-options="required:true">
										<option></option>
										<c:forEach items="${priceList}" var="list">
											<option value="${list.code }">${list.codename }</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td class="left">默认业务员：</td>
								<td class=""><input class="easyui-validatebox" style="margin-left: 0px;" name="customer.salesuserid" id="oa-salesuserid-oacustomerAddPage"/></td>
								<td class="right">收&nbsp;款&nbsp;人：</td>
								<td class=""><input class="easyui-validatebox" style="margin-left: 0px;" name="customer.payeeid" id="oa-payeeid-oacustomerAddPage"/></td>
								<td class="right">默认内勤：</td>
								<td class=""><input class="easyui-validatebox" style="margin-left: 0px;" name="customer.indoorstaff" id="oa-indoorstaff-oacustomerAddPage"/></td>
							</tr>
							<tr>
								<td class="left">促销分类：</td>
								<td class="">
									<select name="customer.promotionsort" id="oa-promotionsort-oacustomerAddPage" class="easyui-validatebox len130" style="margin-left: 0px;" data-options="required:true">
										<option></option>
										<c:forEach items="${promotionsortList }" var="list">
											<option value="${list.code }">${list.codename }</option>
										</c:forEach>
									</select>
								</td>
								<td class="right">信用等级：</td>
								<td class="">
									<select name="customer.creditrating" id="oa-creditrating-oacustomerAddPage" class="easyui-validatebox len130" style="margin-left: 0px;" data-options="required:true">
										<option></option>
										<c:forEach items="${creditratingList }" var="list">
											<option value="${list.code }">${list.codename }</option>
										</c:forEach>
									</select>
								</td>
								<td class="right">信用额度：</td>
								<td class=""><input class="easyui-validatebox easyui-numberbox" name="customer.credit" id="oa-credit-oacustomerAddPage" data-options="max:999999999999,precision:0,required:true" style="margin-left: 0px;"/></td>
							</tr>
							<tr>
								<td class="left">结算方式：</td>
								<td class=""><input class="easyui-validatebox len160" name="customer.settletype" id="oa-settletype-oacustomerAddPage" style="margin-left: 0px;"/></td>
								<td class="right">结算日：</td>
								<td class="">
									<select id="oa-settleday-oacustomerAddPage" name="customer.settleday" class="easyui-validatebox len130" style="margin-left: 0px;">
										<option></option>
										<c:forEach items="${dayList}" var="day">
											<option value="${day }">${day }</option>
										</c:forEach>
									</select>
								</td>
								<td class="right">核销方式：</td>
								<td class="">
									<select name="customer.canceltype" id="oa-canceltype-oacustomerAddPage" class="easyui-validatebox len130" style="margin-left: 0px;" data-options="required:true">
										<option></option>
										<c:forEach items="${canceltypeList }" var="list">
											<option value="${list.code }">${list.codename }</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<td class="left">是否现款：</td>
								<td class="">
									<select name="customer.iscash" id="oa-iscash-oacustomerAddPage" class="easyui-validatebox len130" style="margin-left: 0px;">
										<option></option>
										<option value="0">否</option>
										<option value="1">是</option>
									</select>
								</td>
								<td class="right">是否账期：</td>
								<td class="">
									<select name="customer.islongterm" id="oa-islongterm-oacustomerAddPage" class="easyui-validatebox len130" style="margin-left: 0px;">
										<option></option>
										<option value="0">否</option>
										<option value="1">是</option>
									</select>
								</td>
								<td colspan="2">
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" id="work-allot-oacustomerAddPage" class="easyui-linkbutton" data-options="iconCls:'icon-reload'" >分配品牌业务员</a>
								</td>
							</tr>
							<tr>
								<td class="left">备&nbsp;&nbsp;&nbsp;&nbsp;注：</td>
								<td colspan="5"><input class="easyui-validatebox" style="width: 660px; margin-left: 0px;" name="customer.remark" id="oa-remark-oacustomerAddPage" autocomplete="off"/></td>
							</tr>
						</table>
					</div>
					<div class="easyui-panel baseinfo-oacustomer" title="谈判内容">
						<input type="hidden" name="customerBrandJSON" id="oa-customerBrandJSON-oacustomerAddPage"/>
						<table id="oa-datagrid-oacustomerAddPage">
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
								<td class="left" style="width: 600px;"><textarea style="width:650px; height: 40px;" name="customer.demand" id="oa-demand-oacustomerAddPage" autocomplete="off"></textarea></td>
							</tr>
							<tr>
								<td class="len90 left">谈判结果：</td>
								<td class="left" style="width: 600px;"><textarea style="width:650px; height: 40px;" name="customer.talkresult" id="oa-talkresult-oacustomerAddPage" autocomplete="off"></textarea></td>
							</tr>
							<tr>
								<td class="len90 left">反馈情况：</td>
								<td class="left" style="width: 600px;"><textarea style="width:650px; height: 40px;" name="customer.feedback" id="oa-feedback-oacustomerAddPage" autocomplete="off"></textarea></td>
							</tr>
							<tr>
								<td class="len90 left">合同期限：</td>
								<td class="left" style="width: 600px;"><input class="easyui-validatebox Wdate" name="customer.pactdeadline" id="oa-pactdeadline-oacustomerAddPage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style=" margin-left: 0px;" autocomplete="off"/></td>
							</tr>
						</table>
					</div>
				</div>
				<input type="hidden" id="oa-allotpersonids-oacustomerAddPage" name="allotpersonids"/>
				<input type="hidden" id="oa-allotcompanies-oacustomerAddPage" name="allotcompanies"/>
			</form>
		</div>
	</div>
	<script type="text/javascript">
<!--
	$(function(){
		validLengthAndUsed('2', "oa/checkCustomerUsed.do", '', '', "该编号已被使用，请另输编号！");
	});
	
	var $line = $("#oa-datagrid-oacustomerAddPage");
	var editItem = $('#oa-editRow-oacustomerPage');
			
	var $oa_salesarea = $("#oa-salesarea-oacustomerAddPage");
	var $oa_payeeid = $('#oa-payeeid-oacustomerAddPage');
	var $oa_customersort = $('#oa-customersort-oacustomerAddPage');
	var $oa_salesuserid = $("#oa-salesuserid-oacustomerAddPage");
	var $oa_indoorstaff = $("#oa-indoorstaff-oacustomerAddPage");
	var $oa_settletype = $("#oa-settletype-oacustomerAddPage");
	var $oa_settleday = $("#oa-settleday-oacustomerAddPage");
	var $oa_workAllot = $('#work-allot-oacustomerAddPage');
	var $oa_datagrid = $('#oa-datagrid-oacustomerAddPage');
			
	var $oa_customerid  = $('#oa-id-oacustomerAddPage');
-->
	</script>
  </body>
</html>