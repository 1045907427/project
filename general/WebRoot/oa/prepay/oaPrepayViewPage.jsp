<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="/tag/process" prefix="process"%>
<jsp:useBean id="today" class="java.util.Date"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
      <title>预付款申请单</title>
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
				<c:when test="${empty prepay or empty prepay.id}">
					<form action="oa/oaprepay/addOaPrepay.do" id="oa-form-oaPrepayViewPage" method="post">
				</c:when>
				<c:otherwise>
					<form action="oa/oaprepay/editOaPrepay.do" id="oa-form-oaPrepayViewPage" method="post">
				</c:otherwise>
			</c:choose>
				<input type="hidden" name="prepay.id" id="oa-id-oaPrepayViewPage" value="${prepay.id }"/>
				<input type="hidden" name="prepay.billtype" id="oa-billtype-oaPrepayViewPage" value="2"/>
				<input type="hidden" name="prepay.payusername" id="oa-payusername-oaPrepayViewPage" value="${prepay.payusername }"/>
				<input type="hidden" name="prepay.oaid" id="oa-oaid-oaPrepayViewPage" value="${param.processid }"/>
				<div data-options="region:'center',border:false">
					<div style="margin: 20px auto; width: 790px; border: 1px solid #AAA;">
                        <process:definitionHeader process="${process}"/>
						<div class="easyui-panel" data-options="border:false" title="">
						<table>
							<tr>
								<td class="len90 left">付款部门：</td>
								<td class="len180"><input class="len140" name="prepay.paydeptid" id="oa-paydeptid-oaPrepayViewPage" value="${prepay.paydeptid }" autocomplete="off" maxlength="10"/></td>
								<td class="len80 right">付款人：</td>
								<td class="len180"><input class="easyui-validatebox len140" name="prepay.payuserid" id="oa-payuserid-oaPrepayViewPage" data-options="required:false" value="${prepay.payuserid }" autocomplete="off" maxlength="20"/></td>
								<td class="len80 right">付款日期：</td>
								<td class="len180"><input class="easyui-validatebox Wdate len140" name="prepay.businessdate" id="oa-businessdate-oaPrepayViewPage" onclick="" data-options="required:false" value="${prepay.businessdate }" autocomplete="off" maxlength="10" readonly="readonly"/></td>
							</tr>
							<tr>
								<td class="left">付款方式：</td>
								<td class="">
									<select class="easyui-validatebox len140" name="prepay.paytype" id="oa-paytype-oaPrepayViewPage" data-options="required:false" data="${prepay.paytype }" disabled="disabled">
										<option value="1">现金</option>
										<option value="2">转账</option>
									</select>
								</td>
								<td class="right">付款金额：</td>
								<td class=""><input class="easyui-numberbox len140" name="prepay.amount" id="oa-amount-oaPrepayViewPage" data-options="required:false,min:0,precision:2" value="${prepay.amount }" autocomplete="off" maxlength="15" readonly="readonly"/></td>
								<td class="right">大写金额：</td>
								<td class=""><input class="easyui-validatebox len140" name="prepay.upamount" id="oa-upamount-oaPrepayViewPage" data-options="required:false" value="${prepay.upamount }" autocomplete="off" maxlength="50" readonly="readonly"/></td>
							</tr>
							<tr>
								<td class="left">付款银行：</td>
								<td class=""><input class="easyui-validatebox len140" name="prepay.paybank" id="oa-paybank-oaPrepayViewPage" data-options="required:false" value="${prepay.paybank }" autocomplete="off" maxlength="15"/></td>
								<td class="right">借款类型：</td>
								<td class="">
									<select class="easyui-validatebox len140" name="prepay.loantype" id="oa-loantype-oaPrepayViewPage" data-options="required:false" data="${prepay.loantype }" disabled="disabled">
										<option></option>
										<c:forEach items="${loantype }" var="item">
											<option value="${item.code }"><c:out value="${item.codename }"></c:out></option>
										</c:forEach>
									</select>
								</td>
								<td class="right">收汇人：</td>
								<td class=""><input class="easyui-validatebox len140" name="prepay.collectusername" id="oa-collectusername-oaPrepayViewPage" data-options="required:false" value="${prepay.collectusername }" autocomplete="off" maxlength="30" readonly="readonly"/></td>
							</tr>
                            <tr>
                                <td class="left">收汇人银行：</td>
                                <td class=""><input class="easyui-validatebox len140" name="prepay.collectbank" id="oa-collectbank-oaPrepayViewPage" data-options="required:false" value="${prepay.collectbank }" autocomplete="off" maxlength="40" readonly="readonly"/></td>
                                <td class="right">收汇人账号：</td>
                                <td class=""><input class="easyui-validatebox len140" name="prepay.collectbankno" id="oa-collectbankno-oaPrepayViewPage" data-options="required:false" value="${prepay.collectbankno }" autocomplete="off" maxlength="40" readonly="readonly"/></td>
                            </tr>
							<tr>
								<td class="left">备注：</td>
								<td class="" colspan="5">
									<textarea style="width: 673px; height: 50px; resize: none;" maxlength="165" name="prepay.remark" id="oa-remark-oaPrepayViewPage" readonly="readonly"><c:out value="${prepay.remark }"></c:out></textarea>
								</td>
							</tr>
						</table>
					</div>
					<div class="easyui-panel" data-options="border:false">
						<div id="oa-comments2-oaPrepayViewPage" style="width: 760px;">
						</div>
					</div>
					<div style="border-top: 1px solid #AAA;">&nbsp;</div>
					<div>
						<div style="background: #EEE; font-weight: bold; padding: 7px;">附件</div>
						<div id="oa-attach-oaPrepayViewPage" style="width: 765px;">
						</div>
					</div>
					<!-- COMMENTS -->
					<div>
						<div style="background: #EEE; font-weight: bold; padding: 7px;">审批信息</div>
						<div id="oa-comments-oaPrepayViewPage" style="width: 760px;">
						</div>
					</div>
					</div>
				</div>
			</form>
		</div>
	</div>
    <script type="text/javascript">
    <!--
    
    var $paydeptid = $('#oa-paydeptid-oaPrepayViewPage');
    var $payuserid = $('#oa-payuserid-oaPrepayViewPage');
    var $paybank = $('#oa-paybank-oaPrepayViewPage');
    var $amount = $('#oa-amount-oaPrepayViewPage');
    var $upamount = $('#oa-upamount-oaPrepayViewPage');
    var $collectuserid = $('#oa-collectuserid-oaPrepayViewPage');
    var $payusername = $('#oa-payusername-oaPrepayViewPage');
    
    var $form = $('#oa-form-oaPrepayViewPage');
    var $comments = $('#oa-comments-oaPrepayViewPage');
    var $comments2 = $('#oa-comments2-oaPrepayViewPage');
    var $attach = $('#oa-attach-oaPrepayViewPage');
    
    $(function() {
    
    });

	-->
	</script>
  </body>
</html>