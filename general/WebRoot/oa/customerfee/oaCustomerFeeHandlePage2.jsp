<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="/tag/process" prefix="process"%>
<jsp:useBean id="today" class="java.util.Date"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<title>客户费用申请单（账扣）处理页面</title>
  </head>
  <body>
	<div class="easyui-panel" data-options="fit:true,border:false">
		<div class="easyui-layout" data-options="fit:true,border:false">
			<c:choose>
				<c:when test="${empty customerfee or empty customerfee.id}">
					<form action="oa/customerfee/addOaCustomerFee.do" id="oa-form-oaCustomerFeeHandlePage" method="post">
				</c:when>
				<c:otherwise>
					<form action="oa/customerfee/editOaCustomerFee.do" id="oa-form-oaCustomerFeeHandlePage" method="post">
				</c:otherwise>
			</c:choose>
				<input type="hidden" name="customerfee.id" id="oa-id-oaCustomerFeeHandlePage" value="${customerfee.id }"/>
				<input type="hidden" name="customerfee.oaid" id="oa-oaid-oaCustomerFeeHandlePage" value="${param.processid }"/>
				<div data-options="region:'center',border:false">
					<div style="margin: 0px auto; width: 735px; border: 1px solid #AAA;">
                        <process:definitionHeader process="${process}"/>
                        <div class="easyui-panel" data-options="border:false">
                            <table>
                                <tr>
                                    <td class="len80 left">客户名称：</td>
                                    <td class="len400"><input class="easyui-validatebox len350" name="customerfee.customerid" id="oa-customerid-oaCustomerFeeHandlePage" value="${customerfee.customerid }" text="${customer.name }" autocomplete="off"/><font color="#F00">*</font></td>
                                    <td class="len80 right">申请日期：</td>
                                    <td class="len180">
                                        <c:choose>
                                            <c:when test="${empty customerfee.businessdate}">
                                                <input class="easyui-validatebox len150 Wdate" name="customerfee.businessdate" id="oa-businessdate-oaCustomerFeeHandlePage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" data-options="required:true" value="<fmt:formatDate value='${today }' pattern='yyyy-MM-dd' type='date' dateStyle='long' />" autocomplete="off"/><font color="#F00">*</font>
                                            </c:when>
                                            <c:otherwise>
                                                <input class="easyui-validatebox len150 Wdate" name="customerfee.businessdate" id="oa-businessdate-oaCustomerFeeHandlePage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" data-options="required:true" value="${customerfee.businessdate }" autocomplete="off"/><font color="#F00">*</font>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </table>
                        </div>
                        <!-- 费用明细一览 -->
                        <div class="easyui-panel" data-options="border:true" style="height: 310px; border-top: solid 1px #aaa; border-bottom: solid 1px #aaa;">
                            <input type="hidden" id="oa-customerfee-detaillist-oaCustomerFeeHandlePage" name="detaillist"/>
                            <table id="oa-datagrid-customerfee-oaCustomerFeeHandlePage">
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
                                    <td class="len165"><input class="easyui-validatebox len136" name="customerfee.expensesort" id="oa-expensesort-oaCustomerFeeHandlePage" value="${customerfee.expensesort }" autocomplete="off"/></td>
                                    <td class="len80 right">银行名称：</td>
                                    <td class="len165"><input class="easyui-validatebox len136" name="customerfee.paybank" id="oa-paybank-oaCustomerFeeHandlePage" value="${customerfee.paybank }" autocomplete="off"/></td>
                                    <td class="len80 right">费用金额：</td>
                                    <td class="len150"><input class="easyui-numberbox len130" name="customerfee.payamount" id="oa-payamount-oaCustomerFeeHandlePage" data-options="precision:2" value="${customerfee.payamount }" readonly="readonly" autocomplete="off"/></td>
                                </tr>
                                <tr>
                                    <td>说　　明：</td>
                                    <td colspan="5"><textarea name="customerfee.remark" id="oa-remark-oaCustomerFeeHandlePage" style="resize: none; width: 628px; height: 50px;" maxlength="150"><c:out value="${customerfee.remark}"/></textarea></td>
                                </tr>
                            </table>
                        </div>

                        <div style="border-top: 1px solid #AAA;">&nbsp;</div>
                        <div>
                            <div style="background: #EEE; font-weight: bold; padding: 7px;">附件</div>
                            <div id="oa-attach-oaCustomerFeeHandlePage" style="width: 730px;">
                            </div>
                        </div>
                        <div>
                            <div style="background: #EEE; font-weight: bold; padding: 7px;">审批信息</div>
                            <div id="oa-comments-oaCustomerFeeHandlePage" style="width: 725px;">
                            </div>
                        </div>
					</div>
				</div>
			</form>
		</div>
	</div>
	<script type="text/javascript">
	<!--

	var $customerid = $('#oa-customerid-oaCustomerFeeHandlePage');
	var $businessdate = $('#oa-businessdate-oaCustomerFeeHandlePage');
    var $detail = $('#oa-datagrid-customerfee-oaCustomerFeeHandlePage');
    var $expensesort = $('#oa-expensesort-oaCustomerFeeHandlePage');
    var $paybank = $('#oa-paybank-oaCustomerFeeHandlePage');
    var $payamount = $('#oa-payamount-oaCustomerFeeHandlePage');
//	var $expensesort = $('#oa-expensesort-oaCustomerFeeHandlePage');
//	var $paybank = $('#oa-paybank-oaCustomerFeeHandlePage');
	var $detaillist = $('#oa-customerfee-detaillist-oaCustomerFeeHandlePage');
	var $form = $('#oa-form-oaCustomerFeeHandlePage');

	var $attach = $('#oa-attach-oaCustomerFeeHandlePage');
	var $comments = $('#oa-comments-oaCustomerFeeHandlePage');
	-->
	</script>
  </body>
</html>