<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="/tag/process" prefix="process"%>
<jsp:useBean id="today" class="java.util.Date"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<title>费用冲差支付申请单工作查看页面</title>
	<%@include file="/include.jsp" %>
  </head>
  
  <body>
	<style type="text/css">
		.len50{
			width: 60px;
		}
		.len230{
			width: 230px;
		}
		.len250{
			width: 250px;
		}
	</style>
	<div class="easyui-panel" data-options="fit:true,border:false">
		<div class="easyui-layout" data-options="fit:true,border:false">
			<c:choose>
				<c:when test="${empty push or empty push.id}">
					<form action="oa/expensepush/addOaExpensePush.do" id="oa-form-oaExpensePushViewPage" method="post">
				</c:when>
				<c:otherwise>
					<form action="oa/expensepush/editOaExpensePush.do" id="oa-form-oaExpensePushViewPage" method="post">
				</c:otherwise>
			</c:choose>
				<input type="hidden" id="oa-customerid-hidden-oaExpensePushViewPage" value="${push.customerid }"/>
				<input type="hidden" name="push.id" id="oa-id-oaExpensePushViewPage" value="${push.id }"/>
				<input type="hidden" name="push.oaid" id="oa-oaid-oaExpensePushViewPage" value="${param.processid }"/>
				<input type="hidden" name="push.salesdeptid" id="oa-salesdeptid-oaExpensePushViewPage" value="${push.salesdeptid }"/>
				<div data-options="region:'center',border:false">
					<div style="margin: 0px auto; width: 790px; border: 1px solid #AAA;">
						<process:definitionHeader process="${process}"/>
						<div class="easyui-panel" data-options="border:false">
							<table>
								<tr>
									<td class="len50 left">客&nbsp;&nbsp;户：</td>
									<td class="len250"><input class="easyui-validatebox len230" name="push.customerid" id="oa-customerid-oaExpensePushViewPage" data-options="required:true" value="${push.customerid }" autocomplete="off" maxlength="20"/><font color="#F00">*</font></td>
									<td class="len80 right">支付类型：</td>
									<td class="len160">
										<select class="easyui-validatebox len140" name="push.ptype" id="oa-ptype-oaExpensePushViewPage" data-options="required:true" data="${push.ptype }" autocomplete="off" disabled="disabled">
											<option value="1">冲差</option>
											<option value="2">货补</option>
										</select>
									</td>
									<td class="len50 right">日期：</td>
									<td class="len160"><input class="easyui-validatebox len130 Wdate" name="push.businessdate" id="oa-businessdate-oaCustomerPayHandlePage" onclick="" data-options="required:false" value="${push.businessdate }" autocomplete="off" readonly="readonly"/></td>
								</tr>
							</table>
						</div>
						<div class="easyui-panel" data-options="border:false" style="height: 277px; border-top: solid 1px #aaa; border-bottom: solid 1px #aaa;">
							<input type="hidden" id="oa-detaillist-oaExpensePushViewPage" name="detaillist"/>
							<table id="oa-datatrid-expensepushdetaill-oaExpensePushViewPage">
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
							<div id="oa-comments-oaExpensePushViewPage">
							</div>
						</div>
						<div style="border-top: 1px solid #AAA;">&nbsp;</div>
						<div>
							<div style="background: #EEE; font-weight: bold; padding: 7px;">附件</div>
							<div id="oa-attach-oaExpensePushViewPage" style="width: 765px;">
							</div>
						</div>
						<div>
							<div style="background: #EEE; font-weight: bold; padding: 7px;">审批信息</div>
							<div id="oa-comments2-oaExpensePushViewPage" style="width: 760px;">
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
	<script type="text/javascript">
	<!--

	var $businessdate = $('#oa-businessdate-oaExpensePushViewPage');
	var $customerid = $('#oa-customerid-oaExpensePushViewPage');
	var $ptype = $('#oa-ptype-oaExpensePushViewPage');
	var $salesdeptid = $('#oa-salesdeptid-oaExpensePushViewPage');
	var $detail = $('#oa-datatrid-expensepushdetaill-oaExpensePushViewPage');

	var $comments = $('#oa-comments-oaExpensePushViewPage');
	var $comments2 = $('#oa-comments2-oaExpensePushViewPage');
	var $attach = $('#oa-attach-oaExpensePushViewPage');
	var $form = $('#oa-form-oaExpensePushViewPage');
	
	var $detaillist = $('#oa-detaillist-oaExpensePushViewPage');

	-->
	</script>
  </body>
</html>