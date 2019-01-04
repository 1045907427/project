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
					<form action="oa/brandfee/addBrandFee.do" id="oa-form-oaBrandFeeViewPage" method="post">
				</c:when>
				<c:otherwise>
					<form action="oa/brandfee/editBrandFee.do" id="oa-form-oaBrandFeeViewPage" method="post">
				</c:otherwise>
			</c:choose>
				<input type="hidden" name="brandfee.id" id="oa-id-oaBrandFeeViewPage" value="${brandfee.id }"/>
				<input type="hidden" name="brandfee.oaid" id="oa-oaid-oaBrandFeeViewPage" value="${param.processid }"/>
				<div data-options="region:'center',border:false">
					<div style="margin: 0px auto; width: 735px; border: 1px solid #AAA;">
						<process:definitionHeader process="${process}"/>
						<div class="easyui-panel" data-options="border:false">
							<table>
								<tr>
									<td class="left">供应商：</td>
									<td colspan="3"><input type="text" class="easyui-validatebox len394" name="brandfee.supplierid" id="oa-supplierid-oaBrandFeeViewPage" value="${brandfee.supplierid }" text="${supplier.name }" autocomplete="off" readonly="readonly"/></td>
									<td class="right">供应商确认：</td>
									<td><input type="text" class="easyui-validatebox len136" name="brandfee.supplierconfirm" id="oa-supplierconfirm-oaBrandFeeViewPage" value="${brandfee.supplierconfirm }" autocomplete="off" readonly="readonly"/></td>
								</tr>
								<tr>
									<td class="len80">部　门：</td>
									<td class="len150"><input type="text" class="easyui-validatebox" name="brandfee.deptid" id="oa-deptid-oaBrandFeeViewPage" value="${brandfee.deptid }" autocomplete="off" readonly="readonly"/></td>
									<td class="len80 right">品　牌：</td>
									<td class="len150"><input type="text" class="easyui-validatebox" name="brandfee.brandid" id="oa-brandid-oaBrandFeeViewPage" value="${brandfee.brandid }" autocomplete="off" readonly="readonly"/></td>
									<td class="len80 right">申请日期：</td>
									<td class="len150"><input type="text" class="easyui-validatebox len136 Wdate" name="brandfee.businessdate" id="oa-businessdate-oaBrandFeeViewPage" data-options="required:true" value="${brandfee.businessdate }" autocomplete="off" readonly="readonly"/></td>
								</tr>
							</table>
						</div>
						<!-- 费用明细一览 -->
						<div class="easyui-panel" data-options="border:true" style="height: 290px; border-top: solid 1px #aaa; border-bottom: solid 1px #aaa;">
							<input type="hidden" id="oa-brandfee-detaillist-oaBrandFeeViewPage" name="detaillist"/>
							<table id="oa-datagrid-brandfee-oaBrandFeeViewPage">
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
									<td class="len150"><input type="text" class="easyui-validatebox len136" name="brandfee.expensesort" id="oa-expensesort-oaBrandFeeViewPage" value="${brandfee.expensesort }" autocomplete="off" readonly="readonly"/></td>
									<td class="len80 right">费用金额：</td>
									<td class="len150"><input type="text" class="easyui-numberbox len136" name="brandfee.payamount" id="oa-payamount-oaBrandFeeViewPage" data-options="precision:2" value="${brandfee.payamount }" readonly="readonly" autocomplete="off"/></td>
									<td class="len80 right">银行名称：</td>
									<td class="len150"><input type="text" class="easyui-validatebox len136" name="brandfee.paybank" id="oa-paybank-oaBrandFeeViewPage" value="${brandfee.paybank }" autocomplete="off" readonly="readonly"/></td>
								</tr>
								<tr>
									<td class="left">归还日期：</td>
									<td><input type="text" class="easyui-validatebox len136 Wdate" name="brandfee.returndate" id="oa-returndate-oaBrandFeeViewPage" data-options="required:false" value="${brandfee.returndate }" autocomplete="off" readonly="readonly"/></td>
									<td class="right">归还方式：</td>
									<td><input type="text" class="easyui-validatebox len136" name="brandfee.returnway" id="oa-returnway-oaBrandFeeViewPage" value="${brandfee.returnway }" autocomplete="off" maxlength="25" readonly="readonly"/></td>
								</tr>
								<%--
								<tr>
									<td>说　　明：</td>
									<td colspan="5"><textarea name="brandfee.remark" id="oa-remark-oaBrandFeeViewPage" style="resize: none; width: 606px; height: 50px;" maxlength="165"><c:out value="${brandfee.remark}"/></textarea></td>
								</tr>
								--%>
							</table>
						</div>
						<div style="border-top: 1px solid #AAA;">&nbsp;</div>
						<div>
							<div style="background: #EEE; font-weight: bold; padding: 7px;">附件</div>
							<div id="oa-attach-oaBrandFeeViewPage" style="width: 730px;"></div>
						</div>
						<div>
							<div style="background: #EEE; font-weight: bold; padding: 7px;">审批信息</div>
							<div id="oa-comments-oaBrandFeeViewPage" style="width: 725px;"></div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
	<script type="text/javascript">
	<!--

	var $supplierid = $('#oa-supplierid-oaBrandFeeViewPage');
	var $deptid = $('#oa-deptid-oaBrandFeeViewPage');
	var $brandid = $('#oa-brandid-oaBrandFeeViewPage');
    var $detail = $('#oa-datagrid-brandfee-oaBrandFeeViewPage');
    var $expensesort = $('#oa-expensesort-oaBrandFeeViewPage');
    var $paybank = $('#oa-paybank-oaBrandFeeViewPage');
    var $payamount = $('#oa-payamount-oaBrandFeeViewPage');
//	var $expensesort = $('#oa-expensesort-oaBrandFeeViewPage');
//	var $paybank = $('#oa-paybank-oaBrandFeeViewPage');
	var $detaillist = $('#oa-brandfee-detaillist-oaBrandFeeViewPage');
	var $form = $('#oa-form-oaBrandFeeViewPage');

	var $attach = $('#oa-attach-oaBrandFeeViewPage');
	var $comments = $('#oa-comments-oaBrandFeeViewPage');
	-->
	</script>
  </body>
</html>