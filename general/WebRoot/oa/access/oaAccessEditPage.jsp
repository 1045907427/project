<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<title>费用通路单申请单审批页面</title>
	<%@include file="/include.jsp" %>
  </head>
  
  <body>
	<style type="text/css">
		.len20 {
			width: 20px;
		}
		.len30 {
			width: 30px;
		}
		.len40 {
			width: 40px;
		}
		.len50 {
			width: 50px;
		}
		.len60 {
			width: 60px;
		}
		.len70 {
			width: 70px;
		}
		.len85 {
			width: 85px;
		}
		.len170 {
			width: 173px;
		}
		.len200 {
			width: 200px;
		}
		.len230 {
			width: 230px;
		}
		select {
			margin-left: 0px;
		}
		input[type=text] {
			margin-left: 0px;
		}
	</style>
	<div class="easyui-panel" data-options="fit:true,border:false">
		<!-- <div class="buttonBG" id="oa-buttons-oaAccessEditPage" style="height:26px;"></div> -->
		<div class="easyui-layout" data-options="fit:true,border:false">
			<form action="oa/editOaAccess.do" id="oa-form-oaAccessEditPage" method="post">
				<input type="hidden" name="access.id" id="oa-id-oaAccessEditPage" value="${param.id }"/>
				<input type="hidden" name="goodsprice" id="oa-goodsprice-oaAccessEditPage" value=""/>
				<input type="hidden" name="goodsamount" id="oa-goodsamount-oaAccessEditPage" value=""/>
				<input type="hidden" name="branddiscount" id="oa-branddiscount-oaAccessEditPage" value=""/>
				<div data-options="region:'center',border:false">
					<div class="easyui-panel" title="第一确认：工厂">
						<div class="easyui-panel" data-options="border:false">
							<table style="border-collapse:collapse;" border="0" cellpadding="2" cellspacing="2">
								<tr>
									<td class="left len80">供应商：</td>
									<td class="len180"><input class="easyui-validatebox len170" name="access.supplierid" value="${access.supplierid }" id="oa-supplierid-oaAccessEditPage" autocomplete="off" data-options="required:true"/></td>
									<td class="left">计划时间段：</td>
									<td class="left len180">
										<input class="easyui-validatebox len80 Wdate" name="access.planbegindate" value="${access.planbegindate }" id="oa-planbegindate-oaAccessEditPage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style=" margin-left: 0px;" autocomplete="off" data-options="required:true"/>～<input class="easyui-validatebox len80 Wdate" name="access.planenddate" value="${access.planenddate }" id="oa-planenddate-oaAccessEditPage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style=" margin-left: 0px;" autocomplete="off" data-options="required:true"/>
									</td>
									<td class="left len80">确认单号：</td>
									<td class="len150"><input class="easyui-validatebox len130" name="access.confirmid" value="${access.confirmid }" id="oa-confirmid-oaAccessEditPage" autocomplete="off" maxlength="20"/></td>
								</tr>
								<tr>
									<td class="left">申请通路费：</td>
									<td class="">
										<input class="easyui-validatebox len170" name="access.expensesort" id="oa-expensesort-oaAccessEditPage" value="${access.expensesort }" autocomplete="off"/>
									</td>
									<td class="left">申请特价：</td>
									<td>
										<select name="access.pricetype" id="oa-pricetype-oaAccessEditPage" class="easyui-validatebox len136" data-options="required:true,missingMessage:'通路费和特价必须选择一个！'" data="${access.pricetype }">
											<option></option>
											<option value="1">补差特价</option>
											<option value="2">降价特价</option>
										</select>
									</td>
									<td class="left">补&nbsp;库&nbsp;存：</td>
									<td class="">
										<select name="access.isaddstorage" id="oa-isaddstorage-oaAccessEditPage" class="len136" data="${access.isaddstorage }">
											<option value="1">是</option>
											<option value="0" selected="selected">否</option>
										</select>
									</td>
								</tr>
								<tr>
									<td class="left">客户：</td>
									<td class=""><input class="easyui-validatebox len130" name="access.customerid" id="oa-customerid-oaAccessEditPage" value="${access.customerid }" autocomplete="off" data-options="required:true"/></td>
									<td class="left">执行地点：</td>
									<td class=""><input class="easyui-validatebox len130" name="access.executeaddr" id="oa-executeaddr-oaAccessEditPage" value="${access.executeaddr }" autocomplete="off" data-options=""/></td>
								</tr>
							</table>
						</div>
						<!-- 商品明细一览 -->
						<div class="easyui-panel" data-options="border: false">
							<table id="oa-datagrid-goodsprice-oaAccessEditPage">
								<tr><td></td></tr>
								<tr><td></td></tr>
								<tr><td></td></tr>
								<tr><td></td></tr>
								<tr><td></td></tr>
							</table>
						</div>
						<div class="easyui-panel" data-options="border: false">
							<table>
								<tr>
									<td class="left len70">工厂金额：</td>
									<td class="len100"><input class="easyui-numberbox len90" name="access.factoryamount" id="oa-factoryamount-oaAccessEditPage" value="${access.factoryamount }" autocomplete="off" data-options="required:true,min:0,precision:2"/></td>
									<td class="left len70">自理金额：</td>
									<td class="len100"><input class="easyui-numberbox len90" name="access.myamount" id="oa-myamount-oaAccessEditPage" value="${access.myamount }" autocomplete="off" data-options="min:0,precision:2"/></td>
									<td class="left len70">收回方式：</td>
									<td class="len100">
										<select class="easyui-validatebox len90" name="access.reimbursetype" id="oa-reimbursetype-oaAccessEditPage" data="${access.reimbursetype }" data-options="required:true">
											<option></option>
											<c:forEach items="${typelist }" var="type">
												<option value="${type.code }"><c:out value="${type.codename }"></c:out></option> 
											</c:forEach>
										</select>
									</td>
									<td class="left len70">收回日期：</td>
									<td class="len100"><input class="easyui-validatebox Wdate len90" name="access.reimbursedate" value="${access.reimbursedate }" id="oa-reimbursedate-oaAccessEditPage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style=" margin-left: 0px;" autocomplete="off"/></td>
								</tr>
								<tr>
									<td class="left">说明：</td>
									<td colspan="7">
										<textarea name="access.remark1" id="oa-remark1-oaAccessEditPage" style="width: 640px; height: 50px;"><c:out value="${access.remark1 }" escapeXml="true"></c:out></textarea>
									</td>
								</tr>
							</table>
						</div>
						<div id="oa-container-comments1-oaAccessEditPage" class="easyui-panel" data-options="border: false" title="审批信息">
							<div id="oa-comments1-oaAccessEditPage">
							</div>
						</div>
					</div>
					<div class="easyui-panel" title="第二确认：客户（品牌经理根据客户促销协议核对无误后确认提交）">
						<div class="easyui-panel" data-options="border: false">
							<table>
								<tr>
									<td class="left len60">确认项目：</td>
									<td class="left len85"><input id="oa-confirmoption-oaAccessEditPage" class="easyui-validatebox len80" autocomplete="off" disabled="disabled"/></td>
									<td class="left len60">补&nbsp;库&nbsp;存：</td>
									<td class="left len50"><input id="oa-comfirmaddstorage-oaAccessEditPage" class="easyui-validatebox len50" autocomplete="off" disabled="disabled"/></td>
									<td class="left len60">确认时间：</td>
									<td class="len180">
										<input class="easyui-validatebox Wdate len80" name="access.conbegindate" id="oa-conbegindate-oaAccessEditPage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style=" margin-left: 0px;" autocomplete="off" value="${access.conbegindate }"/>～<input class="easyui-validatebox Wdate len80" name="access.conenddate" id="oa-conenddate-oaAccessEditPage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style=" margin-left: 0px;" autocomplete="off" value="${access.conenddate }"/>
									</td>
									<td class="left len90">降价设定时间：</td>
									<td class="len180">
										<input class="easyui-validatebox Wdate len80" name="access.combegindate" id="oa-combegindate-oaAccessEditPage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style=" margin-left: 0px;" autocomplete="off" value="${access.combegindate }"/>～<input class="easyui-validatebox Wdate len80" name="access.comenddate" id="oa-comenddate-oaAccessEditPage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style=" margin-left: 0px;" autocomplete="off" value="${access.comenddate }"/>
									</td>
								</tr>
								<tr>
									<td class="left">支付日期：</td>
									<td><input class="easyui-validatebox Wdate len80" name="access.paydate" id="oa-paydate-oaAccessEditPage" value="${access.paydate }" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" autocomplete="off" data-options="required:true"/></td>
									<td class="left">支付方式：</td>
									<td class="left">
										<select class="easyui-validatebox len50" name="access.paytype" id="oa-paytype-oaAccessEditPage" data="${access.paytype }" data-options="">
											<option></option>
											<option value="1">折扣</option>
											<option value="2">支票</option>
										</select>
									</td>
									<td class="left">总&nbsp;数&nbsp;量：</td>
									<td><input class="easyui-numberbox len170" name="access.totalnum" id="oa-totalnum-oaAccessEditPage" value="${access.totalnum }" autocomplete="off" data-options="required:true, precision:0, min: 0"/></td>
									<td class="left">费用金额：</td>
									<td><input class="easyui-numberbox len170" name="access.totalamount" id="oa-totalamount-oaAccessEditPage" value="${access.totalamount }" autocomplete="off" data-options="required:true, precision:2"/></td>
								</tr>
								<!--  
								<tr>
								</tr>
								-->
							</table>
						</div>
						<div class="easyui-panel" data-options="border: false">
							<table id="oa-datagrid-goodsamount-oaAccessEditPage">
								<tr><td></td></tr>
								<tr><td></td></tr>
								<tr><td></td></tr>
								<tr><td></td></tr>
								<tr><td></td></tr>
							</table>
						</div>
						<div class="easyui-panel" data-options="border: false">
							<table>
								<tr>
									<td class="left len90">说明：</td>
									<td><textarea name="access.remark2" id="oa-remark2-oaAccessEditPage" style="width: 660px; height: 50px;"><c:out value="${access.remark2 }" escapeXml="true"></c:out></textarea></td>
								</tr>
							</table>
						</div>
						<div id="oa-container-comments2-oaAccessEditPage" class="easyui-panel" data-options="border: false" title="审批信息">
							<div id="oa-comments2-oaAccessEditPage">
							</div>
						</div>
					</div>
					<div class="easyui-panel" title="第三确认：支付信息">
						<div class="easyui-panel" data-options="border: false">
							<table>
								<tr>
									<td class="len90 left">电脑冲差金额：</td>
									<td class="len100"><input class="easyui-numberbox len90" name="access.compdiscount" id="oa-compdiscount-oaAccessEditPage" value="${access.compdiscount }" autocomplete="off" data-options="required:true,min:0,precision:2"/></td>
									<td class="len90 left">电脑降价金额：</td>
									<td class="len100"><input class="easyui-numberbox len90" name="access.comdownamount" id="oa-comdownamount-oaAccessEditPage" value="${access.comdownamount }" autocomplete="off" data-options="required:true,min:0,precision:2"/></td>
									<td class="len80 left">支票金额：</td>
									<td class="len100"><input class="easyui-numberbox len90" name="access.payamount" id="oa-payamount-oaAccessEditPage" value="${access.payamount }" autocomplete="off" data-options="required:true,min:0,precision:2"/></td>
									<td class="len80 left">结算金额：</td>
									<td class="len100"><input class="easyui-numberbox len90" name="access.branchaccount" id="oa-branchaccount-oaAccessEditPage" value="${access.branchaccount }" autocomplete="off" data-options="required:true,min:0,precision:2"/></td>
								</tr>
							</table>
						</div>
						<div id="oa-container-comments5-oaAccessEditPage" class="easyui-panel" data-options="border: false" title="审批信息">
							<div id="oa-comments5-oaAccessEditPage">
							</div>
						</div>
						<div id="oa-discount-oaAccessEditPage" class="easyui-panel" data-options="border: false" title="票扣折让">
							<table id="oa-datagrid-branddiscount-oaAccessEditPage">
								<tr><td></td></tr>
								<tr><td></td></tr>
								<tr><td></td></tr>
								<tr><td></td></tr>
								<tr><td></td></tr>
							</table>
						</div>
						<div id="oa-container-comments3-oaAccessEditPage" class="easyui-panel" data-options="border: false" title="审批信息">
							<div id="oa-comments3-oaAccessEditPage">
							</div>
						</div>
						<div id="oa-invoice-oaAccessEditPage" class="easyui-panel" data-options="border: false" title="支票支付">
							<table>
								<tr>
									<td class="left len80">收款单位：</td>
									<td class="len180"><input class="easyui-validatebox len165" name="invoice.companyname" id="oa-companyname-oaAccessEditPage" value="${invoice.companyname }" autocomplete="off" data-options="required:true"/></td>
									<td class="left len80">单位编码：</td>
									<td class="len150"><input class="easyui-validatebox len130" name="invoice.companyid" id="oa-companyid-oaAccessEditPage" value="${invoice.companyid }" autocomplete="off" data-options="required:true"/></td>
									<td class="left len80">开户银行：</td>
									<td class="len136"><input class="easyui-validatebox len130" name="invoice.bank" id="oa-bank-oaAccessEditPage" value="${invoice.bank }" autocomplete="off" data-options="required:true"/></td>
								</tr>
								<tr>
									<td class="left">银行账号：</td>
									<td class=""><input class="easyui-validatebox len165" name="invoice.bankno" id="oa-bankno-oaAccessEditPage" value="${invoice.bankno }" autocomplete="off" data-options="required:true"/></td>
									<td class="left">付款金额：</td>
									<td class=""><input class="easyui-numberbox len130" name="invoice.payamount" id="oa-payamount-oaAccessEditPage" value="${invoice.payamount }" autocomplete="off" data-options="required:true,min:0,precision:2"/></td>
									<td class="left">金额大写：</td>
									<td class=""><input class="easyui-validatebox len130" name="invoice.amountwords" id="oa-amountwords-oaAccessEditPage" value="${invoice.amountwords }" autocomplete="off" data-options="required:true"/></td>
								</tr>
								<tr>
									<td class="left">费用项目：</td>
									<td class=""><input class="easyui-validatebox len165" name="invoice.expensesort" id="oa-expensesort-oaAccessEditPage" value="${invoice.expensesort }" autocomplete="off" data-options="required:true"/></td>
									<td class="left">发票种类：</td>
									<td class=""><input class="easyui-validatebox len130" name="invoice.invoicetype" id="oa-invoicetype-oaAccessEditPage" value="${invoice.invoicetype }" autocomplete="off" data-options="required:true"/></td>
									<td class="left">到发票日期：</td>
									<td class=""><input class="easyui-validatebox Wdate len130" name="invoice.invoicedate" id="oa-invoicedate-oaAccessEditPage" value="${invoice.invoicedate }" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style=" margin-left: 0px;" autocomplete="off"/></td>
								</tr>
								<tr>
									<td class="left">付款日期：</td>
									<td class=""><input class="easyui-validatebox Wdate len100" name="invoice.paydate" id="oa-paydate-oaAccessEditPage" value="${invoice.paydate }" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style=" margin-left: 0px;" autocomplete="off"/></td>
								</tr>
							</table>
						</div>
						<div id="oa-container-comments4-oaAccessEditPage" class="easyui-panel" data-options="border: false" title="审批信息">
							<div id="oa-comments4-oaAccessEditPage">
							</div>
						</div>
						<div class="easyui-panel" data-options="border: false" title="本次活动结果分析">
							<table id="oa-result-oaAccessEditPage">
								<tr></tr>
								<tr></tr>
								<tr></tr>
								<tr></tr>
								<tr></tr>
							</table>
						</div>
						<div class="easyui-panel" id="oa-attach-oaAccessEditPage" data-options="border: false" title="附件">
						</div>
						<div class="easyui-panel" id="oa-comments-oaAccessEditPage" data-options="border: false" title="审批信息">
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
	<script type="text/javascript">
	<!--
	
	// 项目
	// DataGrid定义
	var $goodsprice = $('#oa-datagrid-goodsprice-oaAccessEditPage');
	var $goodsamount = $('#oa-datagrid-goodsamount-oaAccessEditPage');
	var $branddiscount = $('#oa-datagrid-branddiscount-oaAccessEditPage');
	var $result = $('#oa-result-oaAccessEditPage');
	
	// 项目
	var $supplierid = $('#oa-supplierid-oaAccessEditPage');
	var $expensesort = $('#oa-expensesort-oaAccessEditPage');
	var $pricetype = $('#oa-pricetype-oaAccessEditPage');
	var $customerid = $('#oa-customerid-oaAccessEditPage');
	var $salesarea = $('#oa-salesarea-oaAccessEditPage');
	var $totalamount = $('#oa-totalamount-oaAccessEditPage');
	var $totalnum = $('#oa-totalnum-oaAccessEditPage');
	var $payamount = $('#oa-payamount-oaAccessEditPage');
	var $paytype = $('#oa-paytype-oaAccessEditPage');
	
	var $goodspricelist = $('#oa-goodsprice-oaAccessEditPage');
	var $goodsamountlist = $('#oa-goodsamount-oaAccessEditPage');
	var $branddiscountlist = $('#oa-branddiscount-oaAccessEditPage');
	
	var $accessform = $('#oa-form-oaAccessEditPage');
		
	var $attach = $('#oa-attach-oaAccessEditPage');
	var $comments = $('#oa-comments-oaAccessEditPage');
	
	var $confirmoption = $('#oa-confirmoption-oaAccessEditPage');
	var $comfirmaddstorage = $('#oa-comfirmaddstorage-oaAccessEditPage');
	var $isaddstorage = $('#oa-isaddstorage-oaAccessEditPage');
		
	// 支票、折扣切换
	var $discount = $('#oa-discount-oaAccessEditPage');
	var $invoice = $('#oa-invoice-oaAccessEditPage');
				
	// 审批信息
	var $comment1 =	$('#oa-comments1-oaAccessEditPage');
	var $comment2 =	$('#oa-comments2-oaAccessEditPage');
	var $comment3 =	$('#oa-comments3-oaAccessEditPage');
	var $comment4 =	$('#oa-comments4-oaAccessEditPage');
	var $comment5 =	$('#oa-comments5-oaAccessEditPage');
	var $comment1container = $('#oa-container-comments1-oaAccessEditPage');
	var $comment2container = $('#oa-container-comments2-oaAccessEditPage');
	var $comment3container = $('#oa-container-comments3-oaAccessEditPage');
	var $comment4container = $('#oa-container-comments4-oaAccessEditPage');
	var $comment5container = $('#oa-container-comments5-oaAccessEditPage');
		
	// 银行
	var $companyname = $('#oa-companyname-oaAccessEditPage');
	var $bank = $('#oa-bank-oaAccessEditPage');
	var $bankno = $('#oa-bankno-oaAccessEditPage');
	
	$(function(){

		
	});
	
	-->
	</script>
  </body>
</html>