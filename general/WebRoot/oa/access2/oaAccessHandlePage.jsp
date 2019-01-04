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
		.len80 {
			width: 80px;
		}
		.len85 {
			width: 85px;
		}
		.len130 {
			width: 130px;
		}
		.len140 {
			width: 140px;
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
		<!-- <div class="buttonBG" id="oa-buttons-oaAccessHandlePage" style="height:26px;"></div> -->
		<div class="easyui-layout" data-options="fit:true,border:false">
			<form action="oa/hd/editOaAccess.do" id="oa-form-oaAccessHandlePage" method="post">
				<input type="hidden" name="access.id" id="oa-id-oaAccessHandlePage" value="${param.id }"/>
				<input type="hidden" name="access.oaid" id="oa-oaid-oaAccessHandlePage" value="${param.oaid }"/>
				<input type="hidden" name="goodsprice" id="oa-goodsprice-oaAccessHandlePage" value=""/>
				<input type="hidden" name="goodsamount" id="oa-goodsamount-oaAccessHandlePage" value=""/>
				<input type="hidden" name="branddiscount" id="oa-branddiscount-oaAccessHandlePage" value=""/>
				<div data-options="region:'center',border:false">
					<div style="border-right: 1px solid gray; width: 840px; margin-left: 10px;">
						<div class="easyui-panel" title="第一确认：工厂">
							<div class="easyui-panel" data-options="border:false">
								<table style="border-collapse:collapse;" border="0" cellpadding="2" cellspacing="2">
									<tr>
										<td class="left len80">供应商：</td>
										<td class="len200"><input class="easyui-validatebox len170" name="access.supplierid" value="${access.supplierid }" id="oa-supplierid-oaAccessHandlePage" autocomplete="off" data-options="required:true"/></td>
										<td class="left">计划时间段：</td>
										<td class="left len200">
											<input class="easyui-validatebox len80 Wdate" name="access.planbegindate" value="${access.planbegindate }" id="oa-planbegindate-oaAccessHandlePage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style=" margin-left: 0px;" autocomplete="off" data-options="required:true"/>～<input class="easyui-validatebox len80 Wdate" value="${access.planenddate }" name="access.planenddate" id="oa-planenddate-oaAccessHandlePage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style=" margin-left: 0px;" autocomplete="off" data-options="required:true"/>
										</td>
										<td class="left len80">确认单号：</td>
										<td class="len150"><input class="easyui-validatebox len130" name="access.confirmid" value="${access.confirmid }" id="oa-confirmid-oaAccessHandlePage" autocomplete="off" maxlength="20"/></td>
									</tr>
									<tr>
										<td class="left">申请通路费：</td>
										<td class="">
											<input class="easyui-validatebox len160" name="access.expensesort" id="oa-expensesort-oaAccessHandlePage" value="${access.expensesort }" autocomplete="off"/>
										</td>
										<td class="left">申请特价：</td>
										<td>
											<c:choose>
												<c:when test="${empty access.pricetype and empty access.expensesort}">
													<select name="access.pricetype" id="oa-pricetype-oaAccessHandlePage" class="easyui-validatebox len170" data-options="required:true,missingMessage:'通路费和特价至少应选择一个！'" data="${access.pricetype }">
												</c:when>
												<c:otherwise>
													<select name="access.pricetype" id="oa-pricetype-oaAccessHandlePage" class="easyui-validatebox len170" data-options="required:false,missingMessage:'通路费和特价至少应选择一个！'" data="${access.pricetype }">
												</c:otherwise>
											</c:choose>
												<option></option>
												<option value="1">补差特价</option>
												<option value="2">降价特价</option>
											</select>
										</td>
										<td class="left">补&nbsp;库&nbsp;存：</td>
										<td class="">
											<select name="access.isaddstorage" id="oa-isaddstorage-oaAccessHandlePage" class="len136" data="0">
												<option value="1">是</option>
												<option value="0" selected="selected">否</option>
											</select>
										</td>
									</tr>
									<tr>
										<td class="left">客户：</td>
										<td class=""><input class="easyui-validatebox len170" name="access.customerid" id="oa-customerid-oaAccessHandlePage" value="${access.customerid }" autocomplete="off" data-options="required:true"/></td>
										<td class="left">执行地点：</td>
										<td class="" colspan="3"><input class="easyui-validatebox" style="width: 419px;" name="access.executeaddr" id="oa-executeaddr-oaAccessHandlePage" value="${access.executeaddr }" autocomplete="off" data-options=""/></td>
									</tr>
								</table>
							</div>
							<!-- 商品明细一览 -->
							<div class="easyui-panel" data-options="border: false">
								<table id="oa-datagrid-goodsprice-oaAccessHandlePage">
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
										<td class="left len60">工厂金额：</td>
										<td class="len90"><input class="easyui-numberbox len80" name="access.factoryamount" id="oa-factoryamount-oaAccessHandlePage" value="${access.factoryamount }" autocomplete="off" data-options="required:false,precision:2,onChange:computeBranchaccount"/></td>
										<td class="left len60">自理金额：</td>
										<td class="len90"><input class="easyui-numberbox len80" name="access.myamount" id="oa-myamount-oaAccessHandlePage" value="${access.myamount }" autocomplete="off" data-options="precision:2"/></td>
										<td class="left len60">收回方式：</td>
										<td class="len90">
											<select class="easyui-validatebox len80" name="access.reimbursetype" id="oa-reimbursetype-oaAccessHandlePage" data="${access.reimbursetype }" data-options="required:false">
												<option></option>
												<c:forEach items="${typelist }" var="type">
													<option value="${type.code }"><c:out value="${type.codename }"></c:out></option> 
												</c:forEach>
											</select>
										</td>
										<td class="left len60">收回日期：</td>
										<td class="len90"><input class="easyui-validatebox Wdate len80" name="access.reimbursedate" value="${access.reimbursedate }" id="oa-reimbursedate-oaAccessHandlePage" onclick="WdatePicker({'dateFmt':'yyyy-MM'})" style=" margin-left: 0px;" autocomplete="off"/></td>
										<td class="left len60">支付日期：</td>
										<td class="len90"><input class="easyui-validatebox Wdate len80" name="access.paydate" value="${access.paydate }" id="oa-paydate-oaAccessHandlePage" onclick="WdatePicker({'dateFmt':'yyyy-MM'})" autocomplete="off"/></td>
									</tr>
									<tr>
										<td class="left">说明：</td>
										<td colspan="9">
											<textarea name="access.remark1" id="oa-remark1-oaAccessHandlePage" style="width: 708px; height: 50px;"><c:out value="${access.remark1 }" escapeXml="true"></c:out></textarea>
										</td>
									</tr>
								</table>
							</div>
							<div id="oa-container-comments1-oaAccessHandlePage" class="easyui-panel" data-options="border: false">
								<div id="oa-comments1-oaAccessHandlePage">
								</div>
							</div>
						</div>
						<div class="easyui-panel" title="第二确认：客户（品牌经理根据客户促销协议核对无误后确认提交）">
							<div class="easyui-panel" data-options="border: false">
								<table>
									<tr>
										<td class="left len60 condate">确认时间：</td>
										<td class="len180 condate">
											<input class="easyui-validatebox Wdate len80" name="access.conbegindate" id="oa-conbegindate-oaAccessHandlePage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style=" margin-left: 0px;" autocomplete="off" value="${access.conbegindate }"/>～<input class="easyui-validatebox Wdate len80" name="access.conenddate" id="oa-conenddate-oaAccessHandlePage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style=" margin-left: 0px;" autocomplete="off" value="${access.conenddate }"/>
										</td>
										<td class="left len90 comdate">降价设定时间：</td>
										<td class="len180 comdate">
											<input class="easyui-validatebox Wdate len80" name="access.combegindate" id="oa-combegindate-oaAccessHandlePage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style=" margin-left: 0px;" autocomplete="off" value="${access.combegindate }"/>～<input class="easyui-validatebox Wdate len80" name="access.comenddate" id="oa-comenddate-oaAccessHandlePage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style=" margin-left: 0px;" autocomplete="off" value="${access.comenddate }"/>
										</td>
									</tr>
									<tr>
										<td class="left">支付方式：</td>
										<td class="left">
											<select class="easyui-validatebox len60" name="access.paytype" id="oa-paytype-oaAccessHandlePage" data="${access.paytype }" data-options="">
												<option></option>
												<option value="1">折扣</option>
											</select>
										</td>
										<td class="left">自动分配：</td>
										<td class="left">
											<select class="easyui-validatebox len60" name="access.autoassign" id="oa-autoassign-oaAccessHandlePage" data="${access.autoassign }" data-options="">
												<option value="0">否</option>
												<option value="1">是</option>
											</select>
										</td>
										<td class="left">数量：</td>
										<td class="left">
											<c:choose>
												<c:when test="${not empty access.totalnum }">
													<input class="easyui-numberbox len170" name="access.totalnum" id="oa-totalnum-oaAccessHandlePage" value="${access.totalnum }" autocomplete="off" data-options="required:true, precision:0, "/>
												</c:when>
												<c:otherwise>
													<input class="easyui-numberbox len170" name="access.totalnum" id="oa-totalnum-oaAccessHandlePage" value="0" autocomplete="off" data-options="required:true, precision:0"/>
												</c:otherwise>
											</c:choose>
										</td>
										<td class="left">费用金额：</td>
										<td class="left">
											<c:choose>
												<c:when test="${not empty access.totalamount }">
													<input class="easyui-numberbox len170" name="access.totalamount" id="oa-totalamount-oaAccessHandlePage" value="${access.totalamount }" autocomplete="off" data-options="required:true, precision:2"/>
												</c:when>
												<c:otherwise>
													<input class="easyui-numberbox len170" name="access.totalamount" id="oa-totalamount-oaAccessHandlePage" value="0.00" autocomplete="off" data-options="required:true, precision:2"/>
												</c:otherwise>
											</c:choose>
										</td>
									</tr>
								</table>
							</div>
							<div class="easyui-panel" data-options="border: false">
								<table id="oa-datagrid-goodsamount-oaAccessHandlePage">
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
										<td><textarea name="access.remark2" id="oa-remark2-oaAccessHandlePage" style="width: 660px; height: 50px;"><c:out value="${access.remark2 }" escapeXml="true"></c:out></textarea></td>
									</tr>
								</table>
							</div>
							<div id="oa-container-comments2-oaAccessHandlePage" class="easyui-panel" data-options="border: false">
								<div id="oa-comments2-oaAccessHandlePage">
								</div>
							</div>
						</div>
						<div class="easyui-panel" title="第三确认：支付信息">
							<div class="easyui-panel" data-options="border: false">
								<table>
									<tr>
										<td class="len90 left">电脑冲差金额：</td>
										<td class="len100"><input class="easyui-numberbox len90" name="access.compdiscount" id="oa-compdiscount-oaAccessHandlePage" value="${access.compdiscount }" autocomplete="off" data-options="required:false,precision:2"/></td>
										<td class="len90 left">电脑降价金额：</td>
										<td class="len100"><input class="easyui-numberbox len90" name="access.comdownamount" id="oa-comdownamount-oaAccessHandlePage" value="${access.comdownamount }" autocomplete="off" data-options="required:false,precision:2"/></td>
										<td class="len80 left">支票金额：</td>
										<td class="len100"><input class="easyui-numberbox len90" name="access.payamount" id="oa-payamount-oaAccessHandlePage" value="${access.payamount }" autocomplete="off" data-options="required:false,precision:2,onChange:computeBranchaccount"/></td>
										<td class="len80 left">结算金额：</td>
										<td class="len100"><input class="easyui-numberbox len90" name="access.branchaccount" id="oa-branchaccount-oaAccessHandlePage" value="${access.branchaccount }" autocomplete="off" data-options="required:false,precision:2"/></td>
									</tr>
								</table>
							</div>
							<div id="oa-container-comments5-oaAccessHandlePage" class="easyui-panel" data-options="border: false">
								<div id="oa-comments5-oaAccessHandlePage">
								</div>
							</div>
							<div id="oa-invoice-oaAccessHandlePage" class="easyui-panel" data-options="border: false" title="支票支付">
								<table>
									<tr>
										<td class="left len85">收款单位编码：</td>
										<td class="len80"><input class="easyui-validatebox len80" name="invoice.companyid" id="oa-companyid-oaAccessHandlePage" value="${invoice.companyid }" autocomplete="off" data-options="required:true"/></td>
										<td class="left len60">收款单位：</td>
										<td class="len140"><input class="easyui-validatebox len130" name="invoice.companyname" id="oa-companyname-oaAccessHandlePage" value="${invoice.companyname }" autocomplete="off" data-options="required:true"/></td>
										<td class="left len60">开户银行：</td>
										<td class="len100"><input class="easyui-validatebox len90" name="invoice.bank" id="oa-bank-oaAccessHandlePage" value="${invoice.bank }" autocomplete="off" data-options="required:true"/></td>
										<td class="left len60">银行账号：</td>
										<td class="len136"><input class="easyui-validatebox len165" name="invoice.bankno" id="oa-bankno-oaAccessHandlePage" value="${invoice.bankno }" autocomplete="off" data-options="required:true"/></td>
									</tr>
									<tr>
										<td class="left">付款金额：</td>
										<td class=""><input class="easyui-numberbox len80" name="invoice.payamount" id="oa-payamount-oaAccessHandlePage" value="${invoice.payamount }" autocomplete="off" data-options="required:true,precision:2"/></td>
										<td class="left">金额大写：</td>
										<td class=""><input class="easyui-validatebox len130" name="invoice.amountwords" id="oa-amountwords-oaAccessHandlePage" value="${invoice.amountwords }" autocomplete="off" data-options="required:true"/></td>
										<td class="left">费用项目：</td>
										<td class=""><input class="easyui-validatebox len90" name="invoice.expensesort" id="oa-expensesort-oaAccessHandlePage" value="${invoice.expensesort }" autocomplete="off" data-options="required:true"/></td>
										<td class="left">发票种类：</td>
										<td class=""><input class="easyui-validatebox len165" name="invoice.invoicetype" id="oa-invoicetype-oaAccessHandlePage" value="${invoice.invoicetype }" autocomplete="off" data-options="required:true"/></td>
									</tr>
									<tr>
										<td class="left">到发票日期：</td>
										<td class=""><input class="easyui-validatebox Wdate len80" name="invoice.invoicedate" id="oa-invoicedate-oaAccessHandlePage" value="${invoice.invoicedate }" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style=" margin-left: 0px;" autocomplete="off"/></td>
										<td class="left">付款日期：</td>
										<td class=""><input class="easyui-validatebox Wdate len130" name="invoice.paydate" id="oa-paydate-oaAccessHandlePage" value="${invoice.paydate }" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style=" margin-left: 0px;" autocomplete="off"/></td>
									</tr>
								</table>
							</div>
							<div id="oa-container-comments4-oaAccessHandlePage" class="easyui-panel" data-options="border: false">
								<div id="oa-comments4-oaAccessHandlePage">
								</div>
							</div>
							<div id="oa-discount-oaAccessHandlePage" class="easyui-panel" data-options="border: false" title="票扣折让">
								<table id="oa-datagrid-branddiscount-oaAccessHandlePage">
									<tr><td></td></tr>
									<tr><td></td></tr>
									<tr><td></td></tr>
									<tr><td></td></tr>
									<tr><td></td></tr>
								</table>
							</div>
							<div id="oa-container-comments3-oaAccessHandlePage" class="easyui-panel" data-options="border: false">
								<div id="oa-comments3-oaAccessHandlePage">
								</div>
							</div>
							<div class="easyui-panel" data-options="border: false" title="本次活动结果分析">
								<table id="oa-result-oaAccessHandlePage">
									<tr></tr>
									<tr></tr>
									<tr></tr>
									<tr></tr>
									<tr></tr>
								</table>
							</div>
							<div class="easyui-panel" id="oa-attach-oaAccessHandlePage" data-options="border: false" title="附件">
							</div>
							<div class="easyui-panel" id="oa-comments-oaAccessHandlePage" data-options="border: false" title="审批信息">
							</div>
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
	var $goodsprice = $('#oa-datagrid-goodsprice-oaAccessHandlePage');
	var $goodsamount = $('#oa-datagrid-goodsamount-oaAccessHandlePage');
	var $branddiscount = $('#oa-datagrid-branddiscount-oaAccessHandlePage');
	var $result = $('#oa-result-oaAccessHandlePage');
	
	// 项目
	var $supplierid = $('#oa-supplierid-oaAccessHandlePage');
	var $expensesort = $('#oa-expensesort-oaAccessHandlePage');
	var $pricetype = $('#oa-pricetype-oaAccessHandlePage');
	var $customerid = $('#oa-customerid-oaAccessHandlePage');
	var $salesarea = $('#oa-salesarea-oaAccessHandlePage');
	var $totalamount = $('#oa-totalamount-oaAccessHandlePage');
	var $totalnum = $('#oa-totalnum-oaAccessHandlePage');
	var $payamount = $('#oa-payamount-oaAccessHandlePage');
	var $paytype = $('#oa-paytype-oaAccessHandlePage');
	var $executeaddr = $('#oa-executeaddr-oaAccessHandlePage');
	
	var $factoryamount = $('#oa-factoryamount-oaAccessHandlePage');	// 工厂金额
	var $myamount = $('#oa-myamount-oaAccessHandlePage');	// 自理金额
	// var $payamount = $('#oa-payamount-oaAccessHandlePage');
	var $branchaccount = $('#oa-branchaccount-oaAccessHandlePage');	// 结算金额
	
	var $planbegindate = $('#oa-planbegindate-oaAccessHandlePage');	// 计划开始时间
	var $planenddate = $('#oa-planenddate-oaAccessHandlePage');	// 计划结束时间
	var $conbegindate = $('#oa-conbegindate-oaAccessHandlePage');	// 确认开始时间
	var $conenddate = $('#oa-conenddate-oaAccessHandlePage');	// 确认结束时间
	var $combegindate = $('#oa-combegindate-oaAccessHandlePage');	// 电脑设定开始时间
	var $comenddate = $('#oa-comenddate-oaAccessHandlePage');	// 电脑设定结束时间
	
	var $goodspricelist = $('#oa-goodsprice-oaAccessHandlePage');
	var $goodsamountlist = $('#oa-goodsamount-oaAccessHandlePage');
	var $branddiscountlist = $('#oa-branddiscount-oaAccessHandlePage');
	
	var $accessform = $('#oa-form-oaAccessHandlePage');
		
	var $attach = $('#oa-attach-oaAccessHandlePage');
	var $comments = $('#oa-comments-oaAccessHandlePage');

	// 支票、折扣切换
	var $discount = $('#oa-discount-oaAccessHandlePage');
	var $invoice = $('#oa-invoice-oaAccessHandlePage');
				
	// 审批信息
	var $comment1 =	$('#oa-comments1-oaAccessHandlePage');
	var $comment2 =	$('#oa-comments2-oaAccessHandlePage');
	var $comment3 =	$('#oa-comments3-oaAccessHandlePage');
	var $comment4 =	$('#oa-comments4-oaAccessHandlePage');
	var $comment5 =	$('#oa-comments5-oaAccessHandlePage');
	var $comment1container = $('#oa-container-comments1-oaAccessHandlePage');
	var $comment2container = $('#oa-container-comments2-oaAccessHandlePage');
	var $comment3container = $('#oa-container-comments3-oaAccessHandlePage');
	var $comment4container = $('#oa-container-comments4-oaAccessHandlePage');
	var $comment5container = $('#oa-container-comments5-oaAccessHandlePage');
			
	// 银行
	//var $companyname = $('#oa-companyname-oaAccessHandlePage');
	//var $bank = $('#oa-bank-oaAccessHandlePage');
	//var $bankno = $('#oa-bankno-oaAccessHandlePage');
	
	var $companyname = $('#oa-companyname-oaAccessHandlePage');
	var $companyid = $('#oa-companyid-oaAccessHandlePage');
	var $bank = $('#oa-bank-oaAccessHandlePage');
	var $bankno = $('#oa-bankno-oaAccessHandlePage');
	var $payamount_i = $('#oa-payamount-oaAccessHandlePage[name="invoice.payamount"]');
	var $amountwords = $('#oa-amountwords-oaAccessHandlePage');
	var $expensesort_i = $('#oa-expensesort-oaAccessHandlePage[name="invoice.expensesort"]');
	var $invoicetype = $('#oa-invoicetype-oaAccessHandlePage');
	
	$(function(){

		
	});
	
	-->
	</script>
  </body>
</html>