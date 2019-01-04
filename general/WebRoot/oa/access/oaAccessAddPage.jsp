<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<title>费用通路单申请单添加页面</title>
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
		<!-- <div class="buttonBG" id="oa-buttons-oaAccessAddPage" style="height:26px;"></div> -->
		<div class="easyui-layout" data-options="fit:true,border:false">
			<form action="oa/addOaAccess.do" id="oa-form-oaAccessAddPage" method="post">
				<input type="hidden" name="goodsprice" id="oa-goodsprice-oaAccessAddPage" value=""/>
				<input type="hidden" name="goodsamount" id="oa-goodsamount-oaAccessAddPage" value=""/>
				<input type="hidden" name="branddiscount" id="oa-branddiscount-oaAccessAddPage" value=""/>
				<div data-options="region:'center',border:false">
					<div class="easyui-panel" title="第一确认：工厂">
						<div class="easyui-panel" data-options="border:false">
							<table style="border-collapse:collapse;" border="0" cellpadding="2" cellspacing="2">
								<tr>
									<td class="left len80">供应商：</td>
									<td class="len180"><input class="easyui-validatebox len170" name="access.supplierid" value="${access.supplierid }" id="oa-supplierid-oaAccessAddPage" autocomplete="off" data-options="required:true"/></td>
									<td class="left">计划时间段：</td>
									<td class="left len180">
										<input class="easyui-validatebox len80 Wdate" name="access.planbegindate" value="${access.planbegindate }" id="oa-planbegindate-oaAccessAddPage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style=" margin-left: 0px;" autocomplete="off" data-options="required:true"/>～<input class="easyui-validatebox len80 Wdate" name="access.planenddate" value="${access.planenddate }" id="oa-planenddate-oaAccessAddPage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style=" margin-left: 0px;" autocomplete="off" data-options="required:true"/>
									</td>
									<td class="left len80">确认单号：</td>
									<td class="len150"><input class="easyui-validatebox len130" name="access.confirmid" value="${access.confirmid }" id="oa-confirmid-oaAccessAddPage" autocomplete="off" maxlength="20"/></td>
								</tr>
								<tr>
									<td class="left">申请通路费：</td>
									<td class="">
										<input class="easyui-validatebox len170" name="access.expensesort" id="oa-expensesort-oaAccessAddPage" value="${access.expensesort }" autocomplete="off"/>
									</td>
									<td class="left">申请特价：</td>
									<td>
										<select name="access.pricetype" id="oa-pricetype-oaAccessAddPage" class="easyui-validatebox len136" data-options="required:true,missingMessage:'通路费和特价必须选择一个！'" value="${access.pricetype }">
											<option></option>
											<option value="1">补差特价</option>
											<option value="2">降价特价</option>
										</select>
									</td>
									<td class="left">补&nbsp;库&nbsp;存：</td>
									<td class="">
										<select name="access.isaddstorage" id="oa-isaddstorage-oaAccessAddPage" class="len136" data="0">
											<option value="1">是</option>
											<option value="0" selected="selected">否</option>
										</select>
									</td>
								</tr>
								<tr>
									<td class="left">客户：</td>
									<td class=""><input class="easyui-validatebox len130" name="access.customerid" id="oa-customerid-oaAccessAddPage" value="${access.customerid }" autocomplete="off" data-options="required:true"/></td>
									<td class="left">执行地点：</td>
									<td class=""><input class="easyui-validatebox len130" name="access.executeaddr" id="oa-executeaddr-oaAccessAddPage" value="${access.executeaddr }" autocomplete="off" data-options=""/></td>
								</tr>
							</table>
						</div>
						<!-- 商品明细一览 -->
						<div class="easyui-panel" data-options="border: false">
							<table id="oa-datagrid-goodsprice-oaAccessAddPage">
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
									<td class="len100"><input class="easyui-numberbox len90" name="access.factoryamount" id="oa-factoryamount-oaAccessAddPage" value="${access.factoryamount }" autocomplete="off" data-options="required:true,min:0,precision:2"/></td>
									<td class="left len70">自理金额：</td>
									<td class="len100"><input class="easyui-numberbox len90" name="access.myamount" id="oa-myamount-oaAccessAddPage" value="${access.myamount }" autocomplete="off" data-options="min:0,precision:2"/></td>
									<td class="left len70">收回方式：</td>
									<td class="len100">
										<select class="easyui-validatebox len90" name="access.reimbursetype" id="oa-reimbursetype-oaAccessAddPage" data="${access.reimbursetype }" data-options="required:true">
											<option></option>
											<c:forEach items="${typelist }" var="type">
												<option value="${type.code }"><c:out value="${type.codename }"></c:out></option> 
											</c:forEach>
										</select>
									</td>
									<td class="left len70">收回日期：</td>
									<td class="len100"><input class="easyui-validatebox Wdate len90" name="access.reimbursedate" value="${access.reimbursedate }" id="oa-reimbursedate-oaAccessAddPage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style=" margin-left: 0px;" autocomplete="off"/></td>
								</tr>
								<tr>
									<td class="left">说明：</td>
									<td colspan="7">
										<textarea name="access.remark1" id="oa-remark1-oaAccessAddPage" style="width: 640px; height: 50px;"><c:out value="${access.remark1 }" escapeXml="true"></c:out></textarea>
									</td>
								</tr>
							</table>
						</div>
						<div id="oa-container-comments1-oaAccessAddPage" class="easyui-panel" data-options="border: false" title="审批信息">
							<div id="oa-comments1-oaAccessAddPage">
							</div>
						</div>
					</div>
					<div class="easyui-panel" title="第二确认：客户（品牌经理根据客户促销协议核对无误后确认提交）">
						<div class="easyui-panel" data-options="border: false">
							<table>
								<tr>
									<td class="left len60">确认项目：</td>
									<td class="left len85"><input id="oa-confirmoption-oaAccessAddPage" class="easyui-validatebox len80" autocomplete="off" disabled="disabled"/></td>
									<td class="left len60">补&nbsp;库&nbsp;存：</td>
									<td class="left len50"><input id="oa-comfirmaddstorage-oaAccessAddPage" class="easyui-validatebox len50" autocomplete="off" disabled="disabled"/></td>
									<td class="left len60">确认时间：</td>
									<td class="len180">
										<input class="easyui-validatebox Wdate len80" name="access.conbegindate" id="oa-conbegindate-oaAccessAddPage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style=" margin-left: 0px;" autocomplete="off" value="${access.conbegindate }"/>～<input class="easyui-validatebox Wdate len80" name="access.conenddate" id="oa-conenddate-oaAccessAddPage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style=" margin-left: 0px;" autocomplete="off" value="${access.conenddate }"/>
									</td>
									<td class="left len90">降价设定时间：</td>
									<td class="len180">
										<input class="easyui-validatebox Wdate len80" name="access.combegindate" id="oa-combegindate-oaAccessAddPage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style=" margin-left: 0px;" autocomplete="off" value="${access.combegindate }"/>～<input class="easyui-validatebox Wdate len80" name="access.comenddate" id="oa-comenddate-oaAccessAddPage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style=" margin-left: 0px;" autocomplete="off" value="${access.comenddate }"/>
									</td>
								</tr>
								<tr>
									<td class="left">支付日期：</td>
									<td><input class="easyui-validatebox Wdate len80" name="access.paydate" id="oa-paydate-oaAccessAddPage" value="${access.paydate }" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" autocomplete="off" data-options="required:true"/></td>
									<td class="left">支付方式：</td>
									<td class="left">
										<select class="easyui-validatebox len50" name="access.paytype" id="oa-paytype-oaAccessAddPage" data="${access.paytype }" data-options="">
											<option></option>
											<option value="1">折扣</option>
											<option value="2">支票</option>
										</select>
									</td>
									<td class="left">总&nbsp;数&nbsp;量：</td>
									<td><input class="easyui-numberbox len170" name="access.totalnum" id="oa-totalnum-oaAccessAddPage" value="${access.totalnum }" autocomplete="off" data-options="required:true, precision:0, min: 0"/></td>
									<td class="left">费用金额：</td>
									<td><input class="easyui-numberbox len170" name="access.totalamount" id="oa-totalamount-oaAccessAddPage" value="${access.totalamount }" autocomplete="off" data-options="required:true, precision:2"/></td>
								</tr>
								<!--  
								<tr>
								</tr>
								-->
							</table>
						</div>
						<div class="easyui-panel" data-options="border: false">
							<table id="oa-datagrid-goodsamount-oaAccessAddPage">
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
									<td><textarea name="access.remark2" id="oa-remark2-oaAccessAddPage" style="width: 660px; height: 50px;"><c:out value="${access.remark2 }" escapeXml="true"></c:out></textarea></td>
								</tr>
							</table>
						</div>
						<div id="oa-container-comments2-oaAccessAddPage" class="easyui-panel" data-options="border: false" title="审批信息">
							<div id="oa-comments2-oaAccessAddPage">
							</div>
						</div>
					</div>
					<div class="easyui-panel" title="第三确认：支付信息">
						<div class="easyui-panel" data-options="border: false">
							<table>
								<tr>
									<td class="len90 left">电脑冲差金额：</td>
									<td class="len100"><input class="easyui-numberbox len90" name="access.compdiscount" id="oa-compdiscount-oaAccessAddPage" value="${access.compdiscount }" autocomplete="off" data-options="required:true,min:0,precision:2"/></td>
									<td class="len90 left">电脑降价金额：</td>
									<td class="len100"><input class="easyui-numberbox len90" name="access.comdownamount" id="oa-comdownamount-oaAccessAddPage" value="${access.comdownamount }" autocomplete="off" data-options="required:true,min:0,precision:2"/></td>
									<td class="len80 left">支票金额：</td>
									<td class="len100"><input class="easyui-numberbox len90" name="access.payamount" id="oa-payamount-oaAccessAddPage" value="${access.payamount }" autocomplete="off" data-options="required:true,min:0,precision:2"/></td>
									<td class="len80 left">结算金额：</td>
									<td class="len100"><input class="easyui-numberbox len90" name="access.branchaccount" id="oa-branchaccount-oaAccessAddPage" value="${access.branchaccount }" autocomplete="off" data-options="required:true,min:0,precision:2"/></td>
								</tr>
							</table>
						</div>
						<div id="oa-container-comments5-oaAccessAddPage" class="easyui-panel" data-options="border: false" title="审批信息">
							<div id="oa-comments5-oaAccessAddPage">
							</div>
						</div>
						<div id="oa-discount-oaAccessAddPage" class="easyui-panel" data-options="border: false" title="票扣折让">
							<table id="oa-datagrid-branddiscount-oaAccessAddPage">
								<tr><td></td></tr>
								<tr><td></td></tr>
								<tr><td></td></tr>
								<tr><td></td></tr>
								<tr><td></td></tr>
							</table>
						</div>
						<div id="oa-container-comments3-oaAccessAddPage" class="easyui-panel" data-options="border: false" title="审批信息">
							<div id="oa-comments3-oaAccessAddPage">
							</div>
						</div>
						<div id="oa-invoice-oaAccessAddPage" class="easyui-panel" data-options="border: false" title="支票支付">
							<table>
								<tr>
									<td class="left len80">收款单位：</td>
									<td class="len180"><input class="easyui-validatebox len165" name="invoice.companyname" id="oa-companyname-oaAccessAddPage" value="${invoice.companyname }" autocomplete="off" data-options="required:true"/></td>
									<td class="left len80">单位编码：</td>
									<td class="len150"><input class="easyui-validatebox len130" name="invoice.companyid" id="oa-companyid-oaAccessAddPage" value="${invoice.companyid }" autocomplete="off" data-options="required:true"/></td>
									<td class="left len80">开户银行：</td>
									<td class="len136"><input class="easyui-validatebox len130" name="invoice.bank" id="oa-bank-oaAccessAddPage" value="${invoice.bank }" autocomplete="off" data-options="required:true"/></td>
								</tr>
								<tr>
									<td class="left">银行账号：</td>
									<td class=""><input class="easyui-validatebox len165" name="invoice.bankno" id="oa-bankno-oaAccessAddPage" value="${invoice.bankno }" autocomplete="off" data-options="required:true"/></td>
									<td class="left">付款金额：</td>
									<td class=""><input class="easyui-numberbox len130" name="invoice.payamount" id="oa-payamount-oaAccessAddPage" value="${invoice.payamount }" autocomplete="off" data-options="required:true,min:0,precision:2"/></td>
									<td class="left">金额大写：</td>
									<td class=""><input class="easyui-validatebox len130" name="invoice.amountwords" id="oa-amountwords-oaAccessAddPage" value="${invoice.amountwords }" autocomplete="off" data-options="required:true"/></td>
								</tr>
								<tr>
									<td class="left">费用项目：</td>
									<td class=""><input class="easyui-validatebox len165" name="invoice.expensesort" id="oa-expensesort-oaAccessAddPage" value="${invoice.expensesort }" autocomplete="off" data-options="required:true"/></td>
									<td class="left">发票种类：</td>
									<td class=""><input class="easyui-validatebox len130" name="invoice.invoicetype" id="oa-invoicetype-oaAccessAddPage" value="${invoice.invoicetype }" autocomplete="off" data-options="required:true"/></td>
									<td class="left">到发票日期：</td>
									<td class=""><input class="easyui-validatebox Wdate len130" name="invoice.invoicedate" id="oa-invoicedate-oaAccessAddPage" value="${invoice.invoicedate }" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style=" margin-left: 0px;" autocomplete="off"/></td>
								</tr>
								<tr>
									<td class="left">付款日期：</td>
									<td class=""><input class="easyui-validatebox Wdate len100" name="invoice.paydate" id="oa-paydate-oaAccessAddPage" value="${invoice.paydate }" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style=" margin-left: 0px;" autocomplete="off"/></td>
								</tr>
							</table>
						</div>
						<div id="oa-container-comments4-oaAccessAddPage" class="easyui-panel" data-options="border: false" title="审批信息">
							<div id="oa-comments4-oaAccessAddPage">
							</div>
						</div>
						<div class="easyui-panel" data-options="border: false" title="本次活动结果分析">
							<table id="oa-result-oaAccessAddPage">
								<tr></tr>
								<tr></tr>
								<tr></tr>
								<tr></tr>
								<tr></tr>
							</table>
						</div>
						<div class="easyui-panel" id="oa-attach-oaAccessAddPage" data-options="border: false;iconCls:button-file" title="附件">
						</div>
						<div class="easyui-panel" id="oa-comments-oaAccessAddPage" data-options="border: false" title="审批信息">
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
	var $goodsprice = $('#oa-datagrid-goodsprice-oaAccessAddPage');
	var $goodsamount = $('#oa-datagrid-goodsamount-oaAccessAddPage');
	var $branddiscount = $('#oa-datagrid-branddiscount-oaAccessAddPage');
	var $result = $('#oa-result-oaAccessAddPage');
	
	// 项目
	var $supplierid = $('#oa-supplierid-oaAccessAddPage');
	var $expensesort = $('#oa-expensesort-oaAccessAddPage');
	var $pricetype = $('#oa-pricetype-oaAccessAddPage');
	var $customerid = $('#oa-customerid-oaAccessAddPage');
	var $salesarea = $('#oa-salesarea-oaAccessAddPage');
	var $totalamount = $('#oa-totalamount-oaAccessAddPage');
	var $totalnum = $('#oa-totalnum-oaAccessAddPage');
	var $payamount = $('#oa-payamount-oaAccessAddPage');
	var $paytype = $('#oa-paytype-oaAccessAddPage');
	
	var $goodspricelist = $('#oa-goodsprice-oaAccessAddPage');
	var $goodsamountlist = $('#oa-goodsamount-oaAccessAddPage');
	var $branddiscountlist = $('#oa-branddiscount-oaAccessAddPage');
	
	var $accessform = $('#oa-form-oaAccessAddPage');
		
	var $attach = $('#oa-attach-oaAccessAddPage');
	var $comments = $('#oa-comments-oaAccessAddPage');
	
	var $confirmoption = $('#oa-confirmoption-oaAccessAddPage');
	var $comfirmaddstorage = $('#oa-comfirmaddstorage-oaAccessAddPage');
	var $isaddstorage = $('#oa-isaddstorage-oaAccessAddPage');
		
	// 支票、折扣切换
	var $discount = $('#oa-discount-oaAccessAddPage');
	var $invoice = $('#oa-invoice-oaAccessAddPage');
				
	// 审批信息
	var $comment1 =	$('#oa-comments1-oaAccessAddPage');
	var $comment2 =	$('#oa-comments2-oaAccessAddPage');
	var $comment3 =	$('#oa-comments3-oaAccessAddPage');
	var $comment4 =	$('#oa-comments4-oaAccessAddPage');
	var $comment5 =	$('#oa-comments5-oaAccessAddPage');
	var $comment1container = $('#oa-container-comments1-oaAccessAddPage');
	var $comment2container = $('#oa-container-comments2-oaAccessAddPage');
	var $comment3container = $('#oa-container-comments3-oaAccessAddPage');
	var $comment4container = $('#oa-container-comments4-oaAccessAddPage');
	var $comment5container = $('#oa-container-comments5-oaAccessAddPage');
	
	// 银行
	var $companyname = $('#oa-companyname-oaAccessAddPage');
	var $bank = $('#oa-bank-oaAccessAddPage');
	var $bankno = $('#oa-bankno-oaAccessAddPage');
	
	$(function(){

		
	});
	
	-->
	</script>
  </body>
</html>