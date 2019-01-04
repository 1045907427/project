<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="/tag/process" prefix="process"%>
<jsp:useBean id="today" class="java.util.Date"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<title>客户费用支付申请单</title>
	<%@include file="/include.jsp" %>
  </head>
  
  <body>
	<style type="text/css">
		select {
			width: 142px;
			margin-left: 0px;
		}
		.len110 {
			width: 110px;
		}
	</style>
	<div class="easyui-panel" data-options="fit:true,border:false">
		<div class="easyui-layout" data-options="fit:true,border:false">
			<c:choose>
				<c:when test="${empty customerpay or empty customerpay.id}">
					<form action="oa/addOaCustomerPay.do" id="oa-form-oaCustomerPayHandlePage" method="post">
				</c:when>
				<c:otherwise>
					<form action="oa/editOaCustomerPay.do" id="oa-form-oaCustomerPayHandlePage" method="post">
				</c:otherwise>
			</c:choose>
				<input type="hidden" id="oa-customerid-hidden-oaCustomerPayHandlePage" value="${customerpay.customerid }"/>
				<input type="hidden" id="oa-sharebegindate-hidden-oaCustomerPayHandlePage" value="${customerpay.sharebegindate }"/>
				<input type="hidden" id="oa-shareenddate-hidden-oaCustomerPayHandlePage" value="${customerpay.shareenddate }"/>
				<input type="hidden" name="customerpay.id" id="oa-id-oaCustomerPayHandlePage" value="${customerpay.id }"/>
				<input type="hidden" name="customerpay.oaid" id="oa-oaid-oaCustomerPayHandlePage" value="${param.processid }"/>
				<div data-options="region:'center',border:false">
					<div style="margin: 20px auto; width: 790px; border: 1px solid #AAA;">
                        <process:definitionHeader process="${process}"/>
                        <div class="easyui-panel" data-options="border:false">
                            <table>
                                <tr>
                                    <td class="len80 left">已批OA号：</td>
                                    <td class="len180"><input class="easyui-numberbox len140" name="customerpay.relateoaid" id="oa-relateoaid-oaCustomerPayHandlePage" data-options="required:false,min:0,precision:0,onChange:switchViewLink" value="${customerpay.relateoaid }" autocomplete="off" maxlength="10"/><a id="oa-link-oaCustomerPayHandlePage" href="javascript:void(0);" onclick="viewOaAccess()">查看</a></td>
                                    <td class="len80 right">所属供应商：</td>
                                    <td class="len148"><input class="easyui-validatebox len140" name="customerpay.supplierid" id="oa-supplierid-oaCustomerPayHandlePage" data-options="" value="${customerpay.supplierid }" autocomplete="off"/><font color="#F00">*</font></td>
                                    <td class="len80 right">所属部门：</td>
                                    <td class="len180">
                                        <c:choose>
                                            <c:when test="${empty customerpay }">
                                                <input class="easyui-validatebox len140" name="customerpay.deptid" id="oa-deptid-oaCustomerPayHandlePage" data-options="required:true" value="${user.departmentid }" autocomplete="off" maxlength="20"/><font color="#F00">*</font>
                                            </c:when>
                                            <c:otherwise>
                                                <input class="easyui-validatebox len140" name="customerpay.deptid" id="oa-deptid-oaCustomerPayHandlePage" data-options="required:true" value="${customerpay.deptid }" autocomplete="off" maxlength="20"/><font color="#F00">*</font>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="left">客户名称：</td>
                                    <td class=""><input class="easyui-validatebox len140" name="customerpay.customerid" id="oa-customerid-oaCustomerPayHandlePage" data-options="required:true" value="${customerpay.customerid }" autocomplete="off" maxlength="20"/><font color="#F00">*</font></td>
                                    <td class="right">客户银行：</td>
                                    <td class=""><input class="easyui-validatebox len140" name="customerpay.collectionbank" id="oa-collectionbank-oaCustomerPayHandlePage" data-options="required:false" value="${customerpay.collectionbank }" autocomplete="off" maxlength="100"/></td>
                                    <td class="right">客户账号：</td>
                                    <td class=""><input class="easyui-validatebox len140" name="customerpay.collectionbankno" id="oa-collectionbankno-oaCustomerPayHandlePage" data-options="required:false" value="${customerpay.collectionbankno }" autocomplete="off" maxlength="25"/></td>
                                </tr>
                                <tr>
                                    <td class="left">付款金额：</td>
                                    <td class=""><input class="easyui-numberbox len140" name="customerpay.payamount" id="oa-payamount-oaCustomerPayHandlePage" data-options="required:true,min:0,precision:2,onChange:upperPayamount" value="${customerpay.payamount }" autocomplete="off" maxlength="15"/><font color="#F00">*</font></td>
                                    <td class="right">大写金额：</td>
                                    <td class="" colspan="3"><input class="easyui-validatebox" style="width: 375px;" name="customerpay.upperpayamount" id="oa-upperpayamount-oaCustomerPayHandlePage" data-options="required:false" value="${customerpay.upperpayamount }" autocomplete="off" maxlength="50"/></td>
                                </tr>
                                <tr>
                                    <td class="left">付款银行：</td>
                                    <td class=""><input class="easyui-validatebox len140" name="customerpay.paybank" id="oa-paybank-oaCustomerPayHandlePage" data-options="required:true" value="${customerpay.paybank }" autocomplete="off" maxlength="20"/><font color="#F00">*</font></td>
                                    <td class="right">付款用途：</td>
                                    <td class="">
                                        <select class="easyui-validatebox" name="customerpay.payuse" id="oa-payuse-oaCustomerPayHandlePage" data-options="required:false" data="${customerpay.payuse }" autocomplete="off">
                                            <option></option>
                                            <c:forEach items="${paytype }" var="pay">
                                                <option value="${pay.code }"><c:out value="${pay.codename }"></c:out></option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td class="right">费用科目：</td>
                                    <td class=""><input class="easyui-validatebox len140" name="customerpay.expensesort" id="oa-expensesort-oaCustomerPayHandlePage" data-options="" value="${customerpay.expensesort }" autocomplete="off"/><font color="#F00">*</font></td>
                                </tr>
                                <tr>
                                    <td class="left">发票种类：</td>
                                    <td class="">
                                        <select class="easyui-validatebox len140" name="customerpay.billtype" id="oa-billtype-oaCustomerPayHandlePage" data-options="required:false" data="${customerpay.billtype }" autocomplete="off">
                                            <option></option>
                                            <c:forEach items="${invoicetype }" var="invoice">
                                                <option value="${invoice.code }"><c:out value="${invoice.codename }"></c:out></option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td class="right">到票时间：</td>
                                    <td class=""><input class="easyui-validatebox len140 Wdate" name="customerpay.billdate" id="oa-billdate-oaCustomerPayHandlePage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" data-options="required:false" value="${customerpay.billdate }" autocomplete="off"/></td>
                                    <td class="right">发票金额：</td>
                                    <td class=""><input class="easyui-numberbox len140" name="customerpay.billamount" id="oa-billamount-oaCustomerPayHandlePage" data-options="required:false,min:0,precision:2" value="${customerpay.billamount }" autocomplete="off" maxlength="15"/></td>
                                </tr>
                                <tr>
                                    <td class="left">付款日期：</td>
                                    <td class="">
                                        <c:choose>
                                            <c:when test="${empty customerpay }">
                                                <input class="easyui-validatebox len140 Wdate" name="customerpay.paydate" id="oa-paydate-oaCustomerPayHandlePage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" data-options="required:false" value="<fmt:formatDate value="${today }" pattern="yyyy-MM-dd" type="date" dateStyle="long" />" autocomplete="off"/>
                                            </c:when>
                                            <c:otherwise>
                                                <input class="easyui-validatebox len140 Wdate" name="customerpay.paydate" id="oa-paydate-oaCustomerPayHandlePage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" data-options="required:false" value="${customerpay.paydate }" autocomplete="off"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="right">摊销时间：</td>
                                    <td class="" colspan="3">
                                        <input class="easyui-validatebox len140 Wdate" name="customerpay.sharebegindate" id="oa-sharebegindate-oaCustomerPayHandlePage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" data-options="required:false" value="${customerpay.sharebegindate }" autocomplete="off"/>～<input class="easyui-validatebox Wdate" name="customerpay.shareenddate" id="oa-shareenddate-oaCustomerPayHandlePage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" data-options="required:false" value="${customerpay.shareenddate }" autocomplete="off"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>说明：</td>
                                    <td colspan="5"><textarea name="customerpay.remark" id="oa-remark-oaCustomerPayHandlePage" style="resize: none; width: 642px; height: 50px;" maxlength="165"><c:out value="${customerpay.remark}"/></textarea></td>
                                </tr>
                            </table>
                        </div>
                        <div class="easyui-panel" data-options="border:false" style="height: 327px; border-top: solid 1px #aaa; border-bottom: solid 1px #aaa;">
                            <input type="hidden" id="oa-detaillist-oaCustomerPayHandlePage" name="customerpaydetail"/>
                            <table id="oa-datatrid-customerpaydetail-oaCustomerPayHandlePage">
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
                            <div id="oa-comments-oaCustomerPayHandlePage">
                            </div>
                        </div>
                        <div style="border-top: 1px solid #AAA;">&nbsp;</div>
                        <div>
                            <div style="background: #EEE; font-weight: bold; padding: 7px;">附件</div>
                            <div id="oa-attach-oaCustomerPayHandlePage" style="width: 765px;">
                            </div>
                        </div>
                        <div>
                            <div style="background: #EEE; font-weight: bold; padding: 7px;">审批信息</div>
                            <div id="oa-comments2-oaCustomerPayHandlePage" style="width: 760px;">
                            </div>
                        </div>
					</div>
				</div>
			</form>
		</div>
	</div>
	<script type="text/javascript">
	<!--

	var $relateoaid = $('#oa-relateoaid-oaCustomerPayHandlePage');
	var $customerid = $('#oa-customerid-oaCustomerPayHandlePage');
	var $deptid = $('#oa-deptid-oaCustomerPayHandlePage');
	var $paybank = $('#oa-paybank-oaCustomerPayHandlePage');
	var $detaillist = $('#oa-detaillist-oaCustomerPayHandlePage');
	var $expensesort = $('#oa-expensesort-oaCustomerPayHandlePage');
	var $payamount = $('#oa-payamount-oaCustomerPayHandlePage');
	var $upperpayamount = $('#oa-upperpayamount-oaCustomerPayHandlePage');
	var $sharetype = $('#oa-sharetype-oaCustomerPayHandlePage');
	var $sharebegindate = $('#oa-sharebegindate-oaCustomerPayHandlePage');
	var $shareenddate = $('#oa-shareenddate-oaCustomerPayHandlePage');
	var $supplierid = $('#oa-supplierid-oaCustomerPayHandlePage');
	var $link = $('#oa-link-oaCustomerPayHandlePage');
		
	var $collectionname = $('#oa-collectionname-oaCustomerPayHandlePage');
	var $collectionbank = $('#oa-collectionbank-oaCustomerPayHandlePage');
	var $collectionbankno = $('#oa-collectionbankno-oaCustomerPayHandlePage');
	
	// hidden
	var $sharebegindate_hidden = $('#oa-sharebegindate-hidden-oaCustomerPayHandlePage');
	var $shareenddate_hidden = $('#oa-shareenddate-hidden-oaCustomerPayHandlePage');
	var $customerid_hidden = $('#oa-customerid-hidden-oaCustomerPayHandlePage');

	var $comments = $('#oa-comments-oaCustomerPayHandlePage');
	var $comments2 = $('#oa-comments2-oaCustomerPayHandlePage');
	var $attach = $('#oa-attach-oaCustomerPayHandlePage');
	var $form = $('#oa-form-oaCustomerPayHandlePage');
	
	var $customerpaydetail = $('#oa-datatrid-customerpaydetail-oaCustomerPayHandlePage');

	-->
	</script>
  </body>
</html>