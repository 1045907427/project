<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="/tag/process" prefix="process"%>
<jsp:useBean id="today" class="java.util.Date"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<title>费用冲差支付申请单工作处理页面</title>
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
					<form action="oa/expensepush/addOaExpensePush.do" id="oa-form-oaExpensePushHandlePage" method="post">
				</c:when>
				<c:otherwise>
					<form action="oa/expensepush/editOaExpensePush.do" id="oa-form-oaExpensePushHandlePage" method="post">
				</c:otherwise>
			</c:choose>
				<input type="hidden" id="oa-customerid-hidden-oaExpensePushHandlePage" value="${push.customerid }"/>
				<input type="hidden" name="push.id" id="oa-id-oaExpensePushHandlePage" value="${push.id }"/>
				<input type="hidden" name="push.oaid" id="oa-oaid-oaExpensePushHandlePage" value="${param.processid }"/>
				<input type="hidden" name="push.salesdeptid" id="oa-salesdeptid-oaExpensePushHandlePage" value="${push.salesdeptid }"/>
				<div data-options="region:'center',border:false">
					<div style="margin: 0px auto; width: 870px; border: 1px solid #AAA;">
						<process:definitionHeader process="${process}"/>
						<div class="easyui-panel" data-options="border:false">
							<table>
								<tr>
									<td class="len50 left">客&nbsp;&nbsp;户：</td>
									<td class="len250"><input class="easyui-validatebox len230" name="push.customerid" id="oa-customerid-oaExpensePushHandlePage" data-options="required:true" value="${push.customerid }" autocomplete="off" maxlength="20"/><font color="#F00">*</font></td>
									<td class="len80 right">支付类型：</td>
									<td class="len160">
										<select class="easyui-validatebox len140" name="push.ptype" id="oa-ptype-oaExpensePushHandlePage" data-options="required:true" data="${push.ptype }" autocomplete="off">
											<option value="1">冲差</option>
											<option value="2">货补</option>
										</select>
									</td>
									<td class="len50 right">日期：</td>
									<td class="len160">
										<c:choose>
											<c:when test="${empty push }">
												<input class="easyui-validatebox len130 Wdate" name="push.businessdate" id="oa-businessdate-oaCustomerPayHandlePage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" data-options="required:false" value="<fmt:formatDate value="${today }" pattern="yyyy-MM-dd" type="date" dateStyle="long" />" autocomplete="off"/>
											</c:when>
											<c:otherwise>
												<input class="easyui-validatebox len130 Wdate" name="push.businessdate" id="oa-businessdate-oaCustomerPayHandlePage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" data-options="required:false" value="${push.businessdate }" autocomplete="off"/>
											</c:otherwise>
										</c:choose>
									</td>
								</tr>
							</table>
						</div>
						<div class="easyui-panel" data-options="border:false" style="height: 327px; border-top: solid 1px #aaa; border-bottom: solid 1px #aaa;">
							<input type="hidden" id="oa-detaillist-oaExpensePushHandlePage" name="detaillist"/>
							<table id="oa-datatrid-expensepushdetaill-oaExpensePushHandlePage">
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
							<div id="oa-comments-oaExpensePushHandlePage">
							</div>
						</div>
						<div style="border-top: 1px solid #AAA;">&nbsp;</div>
						<div>
							<div style="background: #EEE; font-weight: bold; padding: 7px;">附件</div>
							<div id="oa-attach-oaExpensePushHandlePage" style="width: 845px;">
							</div>
						</div>
						<div>
							<div style="background: #EEE; font-weight: bold; padding: 7px;">审批信息</div>
							<div id="oa-comments2-oaExpensePushHandlePage" style="width: 840px;">
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>
	<script type="text/javascript">
	<!--

	var $businessdate = $('#oa-businessdate-oaExpensePushHandlePage');
	var $customerid = $('#oa-customerid-oaExpensePushHandlePage');
	var $ptype = $('#oa-ptype-oaExpensePushHandlePage');
	var $salesdeptid = $('#oa-salesdeptid-oaExpensePushHandlePage');
	var $detail = $('#oa-datatrid-expensepushdetaill-oaExpensePushHandlePage');

	var $comments = $('#oa-comments-oaExpensePushHandlePage');
	var $comments2 = $('#oa-comments2-oaExpensePushHandlePage');
	var $attach = $('#oa-attach-oaExpensePushHandlePage');
	var $form = $('#oa-form-oaExpensePushHandlePage');
	
	var $detaillist = $('#oa-detaillist-oaExpensePushHandlePage');

	-->
	</script>
  </body>
</html>