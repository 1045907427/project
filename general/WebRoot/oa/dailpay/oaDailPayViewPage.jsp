<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="/tag/process" prefix="process"%>
<jsp:useBean id="today" class="java.util.Date" />
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  	<title>日常费用支付申请单</title>
    <%@include file="/include.jsp" %>
  </head>
  <body>
	<div class="easyui-panel" data-options="fit:true,border:false">
		<div class="easyui-layout" data-options="fit:true,border:false">
			<c:choose>
				<c:when test="${empty pay or empty pay.id}">
					<form action="oa/addOaDailPay.do" id="oa-form-oaDailPayViewPage" method="post">
				</c:when>
				<c:otherwise>
					<form action="oa/editOaDailPay.do" id="oa-form-oaDailPayViewPage" method="post">
				</c:otherwise>
			</c:choose>
				<input type="hidden" name="pay.id" id="oa-id-oaDailPayViewPage" value="${pay.id }"/>
				<input type="hidden" name="pay.oaid" id="oa-oaid-oaDailPayViewPage" value="${param.processid }"/>
				<input type="hidden" name="pay.applyusername" id="oa-applyusername-oaDailPayViewPage" value="${pay.applyusername }"/>
				<div data-options="region:'center',border:false">
					<div style="margin: 20px auto; width: 790px; border: 1px solid #AAA;">
                        <process:definitionHeader process="${process}"/>
                        <div class="easyui-panel" data-options="border:false">
                            <input type="hidden" name="paydetail" id="oa-detaillist-oaDailPayViewPage"/>
                            <table id="oa-datagrid-oaDailPayViewPage">
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
                        <div data-options="region:'center',border:false">
                            <table>
                                <tr>
                                    <td class="len80 left">收款户名：</td>
                                    <td class="len180"><input class="easyui-validatebox len140" name="pay.collectionname" id="oa-collectionname-oaDailPayViewPage" data-options="required:false" value="${pay.collectionname }" autocomplete="off" maxlength="60" readonly="readonly"/></td>
                                    <td class="len80 left">开户银行：</td>
                                    <td class="len180"><input class="easyui-validatebox len140" name="pay.collectionbank" id="oa-collectionbank-oaDailPayViewPage" data-options="required:false" value="${pay.collectionbank }" autocomplete="off" maxlength="60" readonly="readonly"/></td>
                                    <td class="len80 left">银行账号：</td>
                                    <td class="len180"><input class="easyui-validatebox len140" name="pay.collectionbankno" id="oa-collectionbankno-oaDailPayViewPage" data-options="required:false" value="${pay.collectionbankno }" autocomplete="off" maxlength="50" readonly="readonly"/></td>
                                </tr>
                                <tr>
                                    <!--
                                    <td class="left">付款日期：</td>
                                    <td class=""><input class="easyui-validatebox len140 Wdate" name="pay.paydate" id="oa-paydate-oaDailPayViewPage" onclick="" data-options="required:false" value="${pay.paydate }" autocomplete="off" maxlength="10" readonly="readonly"/></td>
                                    -->
                                    <td class="left">付款金额：</td>
                                    <td class=""><input class="easyui-numberbox len140" name="pay.payamount" id="oa-payamount-oaDailPayViewPage" data-options="required:false,min:0,precision:2" value="${pay.payamount }" autocomplete="off" maxlength="60" readonly="readonly"/></td>
                                    <td class="left">大写金额：</td>
                                    <td class=""><input class="easyui-validatebox len140" name="pay.upperpayamount" id="oa-upperpayamount-oaDailPayViewPage" data-options="required:false" value="${pay.upperpayamount }" autocomplete="off" maxlength="50" readonly="readonly"/></td>
                                    <td class="left">付款银行：</td>
                                    <td class=""><input class="easyui-validatebox len140" name="pay.paybank" id="oa-paybank-oaDailPayViewPage" data-options="required:false" value="${pay.paybank }" autocomplete="off" maxlength="10"/></td>
                                </tr>
                                <tr>
                                    <td class="left">付款用途：</td>
                                    <td class=""><input class="easyui-validatebox len140" name="pay.payuse" id="oa-payuse-oaDailPayViewPage" data-options="required:false" value="${pay.payuse }" autocomplete="off" maxlength="100" readonly="readonly"/></td>
                                    <td class="left">费用科目：</td>
                                    <td class=""><input class="easyui-validatebox len140" name="pay.costsort" id="oa-costsort-oaDailPayViewPage" data-options="required:false" value="${pay.costsort }" autocomplete="off" maxlength="20"/></td>
                                    <td class="left">所属部门：</td>
                                    <td class=""><input class="easyui-validatebox len140" name="pay.applydeptid" id="oa-applydeptid-oaDailPayViewPage" data-options="required:false" value="${pay.applydeptid }" autocomplete="off" maxlength="20"/></td>
                                </tr>
                                <tr>
                                    <td class="left">报销类型：</td>
                                    <td class="">
                                        <select class="easyui-validatebox len140" name="pay.applytype" id="oa-applytype-oaDailPayViewPage" data-options="required:false" data="${pay.applytype }" disabled="disabled">
                                            <option></option>
                                            <c:forEach items="${bxtype }" var="item">
                                                <option value="${item.code }"><c:out value="${item.codename }"></c:out></option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td class="left">发票金额：</td>
                                    <td class=""><input class="easyui-numberbox len140" name="pay.billamount" id="oa-billamount-oaDailPayViewPage" data-options="required:false,min:0,precision:2" value="${pay.billamount }" autocomplete="off" maxlength="15" readonly="readonly"/></td>
                                    <td class="left">分摊方式：</td>
                                    <td class="">
                                        <select class="easyui-validatebox len140" name="pay.sharetype" id="oa-sharetype-oaDailPayViewPage" data-options="required:false" data="${pay.sharetype }" disabled="disabled">
                                            <option value="1">直接分摊</option>
                                            <option value="2">内部分摊</option>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="left">说&nbsp;&nbsp;明：</td>
                                    <td class="" colspan="5"><textarea style="width: 670px; height: 50px; resize: none;" maxlength="165" name="pay.remark" id="oa-remark-oaDailPayViewPage" readonly="readonly"><c:out value="${pay.remark }"></c:out></textarea></td>
                                </tr>
                            </table>
                        </div>
                        <div class="easyui-panel" data-options="border:false">
                            <div id="oa-comments-oaDailPayViewPage">
                            </div>
                        </div>
                        <div style="border-top: 1px solid #AAA;">&nbsp;</div>
                        <div>
                            <div style="background: #EEE; font-weight: bold; padding: 7px;">附件</div>
                            <div id="oa-attach-oaDailPayViewPage" style="width: 765px;">
                            </div>
                        </div>
                        <div>
                            <div style="background: #EEE; font-weight: bold; padding: 7px;">审批信息</div>
                            <div id="oa-comments2-oaDailPayViewPage" style="width: 760px;">
                            </div>
                        </div>
					</div>
				</div>
			</form>
		</div>
	</div>
    <script type="text/javascript">
    <!--
    
    var $paydetail = $('#oa-datagrid-oaDailPayViewPage');
    var $payamount = $('#oa-payamount-oaDailPayViewPage');
    var $upperpayamount = $('#oa-upperpayamount-oaDailPayViewPage');
    var $paybank = $('#oa-paybank-oaDailPayViewPage');
    var $costsort = $('#oa-costsort-oaDailPayViewPage');
    var $applyuserid = $('#oa-applyuserid-oaDailPayViewPage');
    var $applydeptid = $('#oa-applydeptid-oaDailPayViewPage');
    var $comments = $('#oa-comments-oaDailPayViewPage');
    var $comments2 = $('#oa-comments2-oaDailPayViewPage');
    
    var $detaillist = $('#oa-detaillist-oaDailPayViewPage');
    var $attach = $('#oa-attach-oaDailPayViewPage');
    
    var $form = $('#oa-form-oaDailPayViewPage');
    
    $(function() {
    
    });

	-->
	</script>
  </body>
</html>