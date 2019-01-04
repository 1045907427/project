<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="/tag/process" prefix="process"%>
<jsp:useBean id="today" class="java.util.Date"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  	<c:choose>
  		<c:when test="${param.billtype eq '1'}">
  			<title>个人借款申请单</title>
  		</c:when>
  		<c:otherwise>
  			<title>预付款申请单</title>
  		</c:otherwise>
  	</c:choose>
    <%@include file="/include.jsp" %>
  </head>
  <body>
  	<style type="text/css">
  		td { 
  			padding-top: 8px; 
  			padding-bottom: 6px; 
  		}
  	</style>
	<div class="easyui-panel" data-options="fit:true,border:false">
		<div class="easyui-layout" data-options="fit:true,border:false">
			<c:choose>
				<c:when test="${empty loan or empty loan.id}">
					<form action="oa/addOaPersonalLoan.do" id="oa-form-oaPersonalLoanHandlePage" method="post">
				</c:when>
				<c:otherwise>
					<form action="oa/editOaPersonalLoan.do" id="oa-form-oaPersonalLoanHandlePage" method="post">
				</c:otherwise>
			</c:choose>
				<input type="hidden" name="loan.id" id="oa-id-oaPersonalLoanHandlePage" value="${loan.id }"/>
				<input type="hidden" name="loan.billtype" id="oa-billtype-oaPersonalLoanHandlePage" value="1"/>
				<input type="hidden" name="loan.collectusername" id="oa-collectusername-oaPersonalLoanHandlePage" value="${loan.collectusername }"/>
				<input type="hidden" name="loan.payusername" id="oa-payusername-oaPersonalLoanHandlePage" value="${loan.payusername }"/>
				<input type="hidden" name="loan.oaid" id="oa-oaid-oaPersonalLoanHandlePage" value="${param.processid }"/>
				<div data-options="region:'center',border:false">
					<div style="margin: 20px auto; width: 790px; border: 1px solid #AAA;">
                        <process:definitionHeader process="${process}"/>
					  	<div class="easyui-panel" data-options="border:false" title="">
						<table>
							<tr>
								<td class="len80 left">付款部门：</td>
								<td class="len180"><input class="len140" name="loan.paydeptid" id="oa-paydeptid-oaPersonalLoanHandlePage" value="${loan.paydeptid }" autocomplete="off" maxlength="10"/><font color="#F00">*</font></td>
								<td class="len80 right">付款人：</td>
								<td class="len180"><input class="easyui-validatebox len140" name="loan.payuserid" id="oa-payuserid-oaPersonalLoanHandlePage" data-options="required:false" value="${loan.payuserid }" autocomplete="off" maxlength="20"/></td>
								<td class="len80 right">付款日期：</td>
								<td class="len180">
									<c:choose>
										<c:when test="${empty loan }">
											<input class="easyui-validatebox Wdate len140" name="loan.businessdate" id="oa-businessdate-oaPersonalLoanHandlePage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" data-options="required:true" value="<fmt:formatDate value="${today}" pattern="yyyy-MM-dd" type="date" dateStyle="long" />" autocomplete="off" maxlength="10"/><font color="#F00">*</font>
										</c:when>
										<c:otherwise>
											<input class="easyui-validatebox Wdate len140" name="loan.businessdate" id="oa-businessdate-oaPersonalLoanHandlePage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" data-options="required:true" value="${loan.businessdate }" autocomplete="off" maxlength="10"/><font color="#F00">*</font>
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
							<tr>
								<td class="left">付款方式：</td>
								<td class="">
									<select class="easyui-validatebox len140" name="loan.paytype" id="oa-paytype-oaPersonalLoanHandlePage" data-options="required:true" data="${loan.paytype }">
										<option value="1">现金</option>
										<option value="2">转账</option>
									</select>
								</td>
								<td class="right">付款金额：</td>
								<td class=""><input class="easyui-numberbox len140" name="loan.amount" id="oa-amount-oaPersonalLoanHandlePage" data-options="required:true,min:0,precision:2" value="${loan.amount }" autocomplete="off" maxlength="15"/><font color="#F00">*</font></td>
								<td class="right">大写金额：</td>
								<td class=""><input class="easyui-validatebox len140" name="loan.upamount" id="oa-upamount-oaPersonalLoanHandlePage" data-options="required:false" value="${loan.upamount }" autocomplete="off" maxlength="50"/></td>
							</tr>
							<tr>
								<td class="left">付款银行：</td>
								<td class=""><input class="easyui-validatebox len140" name="loan.paybank" id="oa-paybank-oaPersonalLoanHandlePage" data-options="required:true" value="${loan.paybank }" autocomplete="off" maxlength="15"/></td>
								<td class="right">借款类型：</td>
								<td class="">
									<select class="easyui-validatebox len140" name="loan.loantype" id="oa-loantype-oaPersonalLoanHandlePage" data-options="required:false" data="${loan.loantype }">
										<option></option>
										<c:forEach items="${loantype }" var="item">
											<option value="${item.code }"><c:out value="${item.codename }"></c:out></option>
										</c:forEach>
									</select>
								</td>
								<td class="right">收款人：</td>
								<td class=""><input class="easyui-validatebox len140" name="loan.collectuserid" id="oa-collectuserid-oaPersonalLoanHandlePage" data-options="required:true" value="${loan.collectuserid }" autocomplete="off" maxlength="20"/><font color="#F00">*</font></td>
							</tr>
							<tr>
								<td class="left">备注：</td>
								<td class="" colspan="5">
									<textarea style="width: 673px; height: 50px; resize: none;" maxlength="165" name="loan.remark" id="oa-remark-oaPersonalLoanHandlePage"><c:out value="${loan.remark }"></c:out></textarea>
								</td>
							</tr>
						</table>
					</div>
					<div class="easyui-panel" data-options="border:false">
						<div id="oa-comments2-oaPersonalLoanHandlePage" style="width: 760px;">
						</div>
					</div>
					<div style="border-top: 1px solid #AAA;">&nbsp;</div>
					<div>
						<div style="background: #EEE; font-weight: bold; padding: 7px;">附件</div>
						<div id="oa-attach-oaPersonalLoanHandlePage" style="width: 765px;">
						</div>
					</div>
					<!-- COMMENTS -->
					<div>
						<div style="background: #EEE; font-weight: bold; padding: 7px;">审批信息</div>
						<div id="oa-comments-oaPersonalLoanHandlePage" style="width: 760px;">
						</div>
					</div>
					</div>
				</div>
			</form>
		</div>
	</div>
    <script type="text/javascript">
    <!--
    
    var $paydeptid = $('#oa-paydeptid-oaPersonalLoanHandlePage');
    var $payuserid = $('#oa-payuserid-oaPersonalLoanHandlePage');
    var $paybank = $('#oa-paybank-oaPersonalLoanHandlePage');
    var $amount = $('#oa-amount-oaPersonalLoanHandlePage');
    var $upamount = $('#oa-upamount-oaPersonalLoanHandlePage');
    var $collectuserid = $('#oa-collectuserid-oaPersonalLoanHandlePage');
    var $collectusername = $('#oa-collectusername-oaPersonalLoanHandlePage');
    var $payusername = $('#oa-payusername-oaPersonalLoanHandlePage');
    
    var $form = $('#oa-form-oaPersonalLoanHandlePage');
    var $comments = $('#oa-comments-oaPersonalLoanHandlePage');
    var $comments2 = $('#oa-comments2-oaPersonalLoanHandlePage');
    var $attach = $('#oa-attach-oaPersonalLoanHandlePage');
    
    $(function() {
    
    });

	-->
	</script>
  </body>
</html>