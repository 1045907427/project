<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="/tag/process" prefix="process"%>
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
					<form action="oa/addOaCustomerPay.do" id="oa-form-oaCustomerPayViewPage" method="post">
				</c:when>
				<c:otherwise>
					<form action="oa/editOaCustomerPay.do" id="oa-form-oaCustomerPayViewPage" method="post">
				</c:otherwise>
			</c:choose>
				<input type="hidden" id="oa-customerid-hidden-oaCustomerPayViewPage" value="${customerpay.customerid }"/>
				<input type="hidden" id="oa-sharebegindate-hidden-oaCustomerPayViewPage" value="${customerpay.sharebegindate }"/>
				<input type="hidden" id="oa-shareenddate-hidden-oaCustomerPayViewPage" value="${customerpay.shareenddate }"/>
				<input type="hidden" name="customerpay.id" id="oa-id-oaCustomerPayViewPage" value="${customerpay.id }"/>
				<input type="hidden" name="customerpay.oaid" id="oa-oaid-oaCustomerPayViewPage" value="${param.processid }"/>
				<div data-options="region:'center',border:false">
					<div style="margin: 20px auto; width: 790px; border: 1px solid #AAA;">
                        <process:definitionHeader process="${process}"/>
                        <div class="easyui-panel" data-options="border:false">
                            <table>
                                <tr>
                                    <td class="len80 left">已批OA号：</td>
                                    <td class="len180"><input class="easyui-numberbox len140" name="customerpay.relateoaid" id="oa-relateoaid-oaCustomerPayViewPage" data-options="required:false,min:0,precision:0,onChange:switchViewLink" value="${customerpay.relateoaid }" autocomplete="off" maxlength="10" readonly="readonly"/><a id="oa-link-oaCustomerPayViewPage" href="javascript:void(0);" onclick="viewOaAccess()">查看</a></td>
                                    <td class="len80 right">所属供应商：</td>
                                    <td class="len148"><input class="easyui-validatebox" style="width: 160px;" name="customerpay.supplierid" id="oa-supplierid-oaCustomerPayViewPage" data-options="" value="${customerpay.supplierid }" autocomplete="off"/></td>
                                    <td class="len80 right">所属部门：</td>
                                    <td class="len180">
                                        <c:choose>
                                            <c:when test="${empty customerpay }">
                                                <input class="easyui-validatebox len140" name="customerpay.deptid" id="oa-deptid-oaCustomerPayViewPage" data-options="required:false" value="${user.departmentid }" autocomplete="off" maxlength="20"/>
                                            </c:when>
                                            <c:otherwise>
                                                <input class="easyui-validatebox len140" name="customerpay.deptid" id="oa-deptid-oaCustomerPayViewPage" data-options="required:false" value="${customerpay.deptid }" autocomplete="off" maxlength="20"/>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="left">客户名称：</td>
                                    <td class=""><input class="easyui-validatebox len140" name="customerpay.customerid" id="oa-customerid-oaCustomerPayViewPage" data-options="required:false" value="${customerpay.customerid }" autocomplete="off" maxlength="20"/></td>
                                    <td class="right">客户银行：</td>
                                    <td class=""><input class="easyui-validatebox len140" name="customerpay.collectionbank" id="oa-collectionbank-oaCustomerPayViewPage" data-options="required:false" value="${customerpay.collectionbank }" autocomplete="off" maxlength="100" readonly="readonly"/></td>
                                    <td class="right">客户账号：</td>
                                    <td class=""><input class="easyui-validatebox len140" name="customerpay.collectionbankno" id="oa-collectionbankno-oaCustomerPayViewPage" data-options="required:false" value="${customerpay.collectionbankno }" autocomplete="off" maxlength="25" readonly="readonly"/></td>
                                </tr>
                                <tr>
                                    <td class="left">付款金额：</td>
                                    <td class=""><input class="easyui-numberbox len140" name="customerpay.payamount" id="oa-payamount-oaCustomerPayViewPage" data-options="required:false,min:0,precision:2" value="${customerpay.payamount }" autocomplete="off" maxlength="15" readonly="readonly"/></td>
                                    <td class="right">大写金额：</td>
                                    <td class="" colspan="3"><input class="easyui-validatebox" style="width: 375px;" name="customerpay.upperpayamount" id="oa-upperpayamount-oaCustomerPayViewPage" data-options="required:false" value="${customerpay.upperpayamount }" autocomplete="off" maxlength="50" readonly="readonly"/></td>
                                </tr>
                                <tr>
                                    <td class="left">付款银行：</td>
                                    <td class=""><input class="easyui-validatebox len140" name="customerpay.paybank" id="oa-paybank-oaCustomerPayViewPage" data-options="required:false" value="${customerpay.paybank }" autocomplete="off" maxlength="20"/></td>
                                    <td class="right">付款用途：</td>
                                    <td class="">
                                        <select class="easyui-validatebox" name="customerpay.payuse" id="oa-payuse-oaCustomerPayViewPage" data-options="required:false" data="${customerpay.payuse }" autocomplete="off" disabled="disabled">
                                            <option></option>
                                            <c:forEach items="${paytype }" var="pay">
                                                <option value="${pay.code }"><c:out value="${pay.codename }"></c:out></option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td class="right">费用科目：</td>
                                    <td class=""><input class="easyui-validatebox len140" name="customerpay.expensesort" id="oa-expensesort-oaCustomerPayViewPage" data-options="" value="${customerpay.expensesort }" autocomplete="off"/></td>
                                </tr>
                                <tr>
                                    <td class="left">发票种类：</td>
                                    <td class="">
                                        <select class="easyui-validatebox len140" name="customerpay.billtype" id="oa-billtype-oaCustomerPayViewPage" data-options="required:false" data="${customerpay.billtype }" autocomplete="off" disabled="disabled">
                                            <option></option>
                                            <c:forEach items="${invoicetype }" var="invoice">
                                                <option value="${invoice.code }"><c:out value="${invoice.codename }"></c:out></option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td class="right">到票时间：</td>
                                    <td class=""><input class="easyui-validatebox len140 Wdate" name="customerpay.billdate" id="oa-billdate-oaCustomerPayViewPage" onclick="" data-options="required:false" value="${customerpay.billdate }" autocomplete="off" readonly="readonly"/></td>
                                    <td class="right">发票金额：</td>
                                    <td class=""><input class="easyui-numberbox len140" name="customerpay.billamount" id="oa-billamount-oaCustomerPayViewPage" data-options="required:false,min:0,precision:2" value="${customerpay.billamount }" autocomplete="off" maxlength="15" readonly="readonly"/></td>
                                </tr>
                                <tr>
                                    <td class="left">付款日期：</td>
                                    <td class=""><input class="easyui-validatebox len140 Wdate" name="customerpay.paydate" id="oa-paydate-oaCustomerPayViewPage" onclick="" data-options="required:false" value="${customerpay.paydate }" autocomplete="off" readonly="readonly"/></td>
                                    <td class="right">摊销时间：</td>
                                    <td class="" colspan="3">
                                        <input class="easyui-validatebox len140 Wdate" name="customerpay.sharebegindate" id="oa-sharebegindate-oaCustomerPayViewPage" onclick="" data-options="required:false" value="${customerpay.sharebegindate }" autocomplete="off" readonly="readonly"/>～<input class="easyui-validatebox Wdate" name="customerpay.shareenddate" id="oa-shareenddate-oaCustomerPayViewPage" onclick="" data-options="required:false" value="${customerpay.shareenddate }" autocomplete="off" readonly="readonly"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>说明：</td>
                                    <td colspan="5"><textarea name="customerpay.remark" id="oa-remark-oaCustomerPayViewPage" style="resize: none; width: 642px; height: 50px;" maxlength="165" readonly="readonly"><c:out value="${customerpay.remark}"/></textarea></td>
                                </tr>
                            </table>
                        </div>
                        <div class="easyui-panel" data-options="border:false" style="height: 277px; border-top: solid 1px #aaa; border-bottom: solid 1px #aaa;">
                            <input type="hidden" id="oa-detaillist-oaCustomerPayViewPage" name="customerpaydetail"/>
                            <table id="oa-datatrid-customerpaydetail-oaCustomerPayViewPage">
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
                            <div id="oa-comments-oaCustomerPayViewPage">
                            </div>
                        </div>
                        <div style="border-top: 1px solid #AAA;">&nbsp;</div>
                        <div>
                            <div style="background: #DDD; font-weight: bold; padding: 7px;">附件</div>
                            <div id="oa-attach-oaCustomerPayViewPage" style="width: 765px;">
                            </div>
                        </div>
                        <div>
                            <div style="background: #DDD; font-weight: bold; padding: 7px;">审批信息</div>
                            <div id="oa-comments2-oaCustomerPayViewPage" style="width: 760px;">
                            </div>
                        </div>
					</div>
				</div>
			</form>
		</div>
	</div>
	<script type="text/javascript">
	<!--

	var $relateoaid = $('#oa-relateoaid-oaCustomerPayViewPage');
	var $customerid = $('#oa-customerid-oaCustomerPayViewPage');
	var $deptid = $('#oa-deptid-oaCustomerPayViewPage');
	var $paybank = $('#oa-paybank-oaCustomerPayViewPage');
	var $detaillist = $('#oa-detaillist-oaCustomerPayViewPage');
	var $expensesort = $('#oa-expensesort-oaCustomerPayViewPage');
	var $payamount = $('#oa-payamount-oaCustomerPayViewPage');
	var $upperpayamount = $('#oa-upperpayamount-oaCustomerPayViewPage');
	var $sharetype = $('#oa-sharetype-oaCustomerPayViewPage');
	var $sharebegindate = $('#oa-sharebegindate-oaCustomerPayViewPage');
	var $shareenddate = $('#oa-shareenddate-oaCustomerPayViewPage');
	var $supplierid = $('#oa-supplierid-oaCustomerPayViewPage');
	var $link = $('#oa-link-oaCustomerPayViewPage');
		
	var $collectionname = $('#oa-collectionname-oaCustomerPayViewPage');
	var $collectionbank = $('#oa-collectionbank-oaCustomerPayViewPage');
	var $collectionbankno = $('#oa-collectionbankno-oaCustomerPayViewPage');
	
	// hidden
	var $sharebegindate_hidden = $('#oa-sharebegindate-hidden-oaCustomerPayViewPage');
	var $shareenddate_hidden = $('#oa-shareenddate-hidden-oaCustomerPayViewPage');
	var $customerid_hidden = $('#oa-customerid-hidden-oaCustomerPayViewPage');

	var $comments = $('#oa-comments-oaCustomerPayViewPage');
	var $comments2 = $('#oa-comments2-oaCustomerPayViewPage');
	var $attach = $('#oa-attach-oaCustomerPayViewPage');
	var $form = $('#oa-form-oaCustomerPayViewPage');
	
	var $customerpaydetail = $('#oa-datatrid-customerpaydetail-oaCustomerPayViewPage');

	-->
	</script>
  </body>
</html>