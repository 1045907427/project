<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/tag/process" prefix="process"%>
<jsp:useBean id="today" class="java.util.Date"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<title>品牌费用申请单（支付）处理页面</title>
  </head>
  <body>
	<div class="easyui-panel" data-options="fit:true,border:false">
		<div class="easyui-layout" data-options="fit:true,border:false">
			<c:choose>
				<c:when test="${empty brandfee or empty brandfee.id}">
					<form action="oa/brandfee/addBrandFee.do" id="oa-form-oaBrandFeeHandlePage" method="post">
				</c:when>
				<c:otherwise>
					<form action="oa/brandfee/editBrandFee.do" id="oa-form-oaBrandFeeHandlePage" method="post">
				</c:otherwise>
			</c:choose>
				<input type="hidden" name="brandfee.id" id="oa-id-oaBrandFeeHandlePage" value="${brandfee.id }"/>
				<input type="hidden" name="brandfee.oaid" id="oa-oaid-oaBrandFeeHandlePage" value="${param.processid }"/>
				<div data-options="region:'center',border:false">
					<div style="margin: 0px auto; width: 735px; border: 1px solid #AAA;">
						<process:definitionHeader process="${process}"/>
						<div class="easyui-panel" data-options="border:false">
							<table>
								<tr>
									<td class="left">供应商：</td>
									<td colspan="3"><input type="text" class="easyui-validatebox len394" name="brandfee.supplierid" id="oa-supplierid-oaBrandFeeHandlePage" value="${brandfee.supplierid }" text="${supplier.name }" autocomplete="off"/><span style="color: #F00">*</span></td>
									<td class="right">供应商确认：</td>
									<td><input type="text" class="easyui-validatebox len136" name="brandfee.supplierconfirm" id="oa-supplierconfirm-oaBrandFeeHandlePage" value="${brandfee.supplierconfirm }" autocomplete="off" maxlength="25"/></td>
								</tr>
								<tr>
									<td class="len80">部　门：</td>
									<td class="len150"><input type="text" class="easyui-validatebox" name="brandfee.deptid" id="oa-deptid-oaBrandFeeHandlePage" value="${brandfee.deptid }" autocomplete="off"/><span style="color: #F00">*</span></td>
									<td class="len80 right">品　牌：</td>
									<td class="len150"><input type="text" class="easyui-validatebox" name="brandfee.brandid" id="oa-brandid-oaBrandFeeHandlePage" value="${brandfee.brandid }" autocomplete="off"/><span style="color: #F00">*</span></td>
									<td class="len80 right">申请日期：</td>
									<td class="len150">
										<c:choose>
											<c:when test="${empty brandfee.businessdate}">
												<input type="text" class="easyui-validatebox len136 Wdate" name="brandfee.businessdate" id="oa-businessdate-oaBrandFeeHandlePage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" data-options="required:true" value="<fmt:formatDate value='${today }' pattern='yyyy-MM-dd' type='date' dateStyle='long' />" autocomplete="off"/><span style="color: #F00">*</span>
											</c:when>
											<c:otherwise>
												<input type="text" class="easyui-validatebox len136 Wdate" name="brandfee.businessdate" id="oa-businessdate-oaBrandFeeHandlePage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" data-options="required:true" value="${brandfee.businessdate }" autocomplete="off"/><span style="color: #F00">*</span>
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</table>
						</div>
						<!-- 费用明细一览 -->
						<div class="easyui-panel" data-options="border:true" style="height: 290px; border-top: solid 1px #aaa; border-bottom: solid 1px #aaa;">
							<input type="hidden" id="oa-brandfee-detaillist-oaBrandFeeHandlePage" name="detaillist"/>
							<table id="oa-datagrid-brandfee-oaBrandFeeHandlePage">
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
						<div class="easyui-panel" data-options="border:false">
							<table>
								<tr>
									<td class="len80 left">费用科目：</td>
									<td class="len150"><input type="text" class="easyui-validatebox len136" name="brandfee.expensesort" id="oa-expensesort-oaBrandFeeHandlePage" value="${brandfee.expensesort }" autocomplete="off"/><span style="color: #F00">*</span></td>
									<td class="len80 right">费用金额：</td>
									<td class="len150"><input type="text" class="easyui-numberbox len136" name="brandfee.payamount" id="oa-payamount-oaBrandFeeHandlePage" data-options="precision:2" value="${brandfee.payamount }" readonly="readonly" autocomplete="off"/></td>
									<td class="len80 right">银行名称：</td>
									<td class="len150"><input type="text" class="easyui-validatebox len136" name="brandfee.paybank" id="oa-paybank-oaBrandFeeHandlePage" value="${brandfee.paybank }" autocomplete="off"/></td>
								</tr>
								<tr>
									<td class="left">归还日期：</td>
									<td><input type="text" class="easyui-validatebox len136 Wdate" name="brandfee.returndate" id="oa-returndate-oaBrandFeeHandlePage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" data-options="required:false" value="${brandfee.returndate }" autocomplete="off"/></td>
									<td class="right">归还方式：</td>
									<td><input type="text" class="easyui-validatebox len136" name="brandfee.returnway" id="oa-returnway-oaBrandFeeHandlePage" value="${brandfee.returnway }" autocomplete="off" maxlength="25"/></td>
								</tr>
								<%--
								<tr>
									<td>说　　明：</td>
									<td colspan="5"><textarea name="brandfee.remark" id="oa-remark-oaBrandFeeHandlePage" style="resize: none; width: 606px; height: 50px;" maxlength="165"><c:out value="${brandfee.remark}"/></textarea></td>
								</tr>
								--%>
							</table>
						</div>
						<div style="border-top: 1px solid #AAA;">&nbsp;</div>
						<div>
							<div style="background: #EEE; font-weight: bold; padding: 7px;">附件</div>
							<div id="oa-attach-oaBrandFeeHandlePage" style="width: 730px;"></div>
						</div>
						<div>
							<div style="background: #EEE; font-weight: bold; padding: 7px;">审批信息</div>
							<div id="oa-comments-oaBrandFeeHandlePage" style="width: 725px;"></div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
	<script type="text/javascript">
	<!--

	var $supplierid = $('#oa-supplierid-oaBrandFeeHandlePage');
	var $deptid = $('#oa-deptid-oaBrandFeeHandlePage');
	var $brandid = $('#oa-brandid-oaBrandFeeHandlePage');
    var $detail = $('#oa-datagrid-brandfee-oaBrandFeeHandlePage');
    var $expensesort = $('#oa-expensesort-oaBrandFeeHandlePage');
    var $paybank = $('#oa-paybank-oaBrandFeeHandlePage');
    var $payamount = $('#oa-payamount-oaBrandFeeHandlePage');
//	var $expensesort = $('#oa-expensesort-oaBrandFeeHandlePage');
//	var $paybank = $('#oa-paybank-oaBrandFeeHandlePage');
	var $detaillist = $('#oa-brandfee-detaillist-oaBrandFeeHandlePage');
	var $form = $('#oa-form-oaBrandFeeHandlePage');

	var $attach = $('#oa-attach-oaBrandFeeHandlePage');
	var $comments = $('#oa-comments-oaBrandFeeHandlePage');
	-->
	</script>
  </body>
</html>