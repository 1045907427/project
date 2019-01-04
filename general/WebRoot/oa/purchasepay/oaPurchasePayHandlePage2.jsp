<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="/tag/process" prefix="process"%>
<jsp:useBean id="today" class="java.util.Date"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>行政采购付款申请单处理页面</title>
    <%@include file="/include.jsp" %>
    <%--
        付款银行必填
    --%>
</head>
<body>
<style type="text/css">
    .purchasepay-work-table tr {
        height: 32px;
    }
    .oa-item-required {
        color: #F00;
    }
</style>
<div class="easyui-panel" data-options="fit:true,border:false">
    <div class="easyui-layout" data-options="fit:true,border:false">
        <c:choose>
            <c:when test="${empty pay or empty pay.id}">
                <form action="oa/purchasepay/addOaPurchasePay.do" id="oa-form-oaPurchasePayHandlePage" method="post">
            </c:when>
            <c:otherwise>
                <form action="oa/purchasepay/editOaPurchasePay.do" id="oa-form-oaPurchasePayHandlePage" method="post">
            </c:otherwise>
        </c:choose>
            <input type="hidden" name="pay.id" id="oa-id-oaPurchasePayHandlePage" value="${pay.id }"/>
            <input type="hidden" name="pay.oaid" id="oa-oaid-oaPurchasePayHandlePage" value="${param.processid }"/>
            <div data-options="region:'center',border:false">
                <div style="margin: 20px auto; width: 760px; border: 1px solid #AAA;">
                    <process:definitionHeader process="${process}"/>
                    <div class="easyui-panel" data-options="border:false">
                        <table class="purchasepay-work-table">
                            <tr>
                                <td class="len80 left">申请日期：</td>
                                <td class="len180">
                                    <c:choose>
                                        <c:when test="${empty pay}">
                                            <input class="easyui-validatebox len140 Wdate" name="pay.businessdate" id="oa-businessdate-oaPurchasePayHandlePage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="<fmt:formatDate value="${today }" pattern="yyyy-MM-dd" type="date" dateStyle="long" />" autocomplete="off" maxlength="10"/>
                                        </c:when>
                                        <c:otherwise>
                                            <input class="easyui-validatebox len140 Wdate" name="pay.businessdate" id="oa-businessdate-oaPurchasePayHandlePage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="<c:out value='${pay.businessdate }'/>" autocomplete="off" maxlength="10"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="len80 left">付款日期：</td>
                                <td class="len180"><input class="easyui-validatebox len140 Wdate" name="pay.paydate" id="oa-paydate-oaPurchasePayHandlePage" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="<c:out value='${pay.paydate }'/>" autocomplete="off" maxlength="10"/></td>
                                <td class="len80 left">申请OA号：</td>
                                <td class="len180"><input class="easyui-textbox len140" name="pay.relateoaid" id="oa-relateoaid-oaPurchasePayHandlePage" data-options="required:false,onChange:switchOaLink" value="<c:out value='${pay.relateoaid }'/>" autocomplete="off" maxlength="10"/><a id="oa-oalink-oaPurchasePayHandlePage" href="javascript:void(0);" onclick="javascript:viewOaProcess(this.getAttribute('oaid'));">查看</a></td>
                            </tr>
                            <tr>
                                <td class="left">收款单位：</td>
                                <td class=""><input class="easyui-validatebox len140" name="pay.receivername" id="oa-receivername-oaPurchasePayHandlePage" data-options="required:false" value="<c:out value='${pay.receivername }'/>" autocomplete="off" maxlength="50"/></td>
                                <td class="left">开户银行：</td>
                                <td class=""><input class="easyui-validatebox len140" name="pay.receiverbank" id="oa-receiverbank-oaPurchasePayHandlePage" data-options="required:false" value="<c:out value='${pay.receiverbank }'/>" autocomplete="off" maxlength="50"/></td>
                                <td class="left">银行账号：</td>
                                <td class=""><input class="easyui-validatebox len140" name="pay.receiverbankno" id="oa-receiverbankno-oaPurchasePayHandlePage" data-options="required:false" value="<c:out value='${pay.receiverbankno }'/>" autocomplete="off" maxlength="50"/></td>
                            </tr>
                            <tr>
                                <td class="left">大写金额：</td>
                                <td class=""><input class="easyui-validatebox" style="width: 140px;" name="pay.upperpayamount" id="oa-upperpayamount-oaPurchasePayHandlePage" data-options="required:false" value="<c:out value='${pay.upperpayamount }'/>" autocomplete="off" maxlength="30"/>
                                <td class="left">付款金额：</td>
                                <td class=""><input class="easyui-numberbox" style="width: 140px;" name="pay.payamount" id="oa-payamount-oaPurchasePayHandlePage" data-options="precision:2,required:true,max:9999999999,onChange:changeAmount2Upper" value="<c:out value='${pay.payamount }'/>" autocomplete="off" maxlength="18"/><span class="oa-item-required">*</span></td></td>
                                <td class="left">用　　途：</td>
                                <td class=""><input class="easyui-validatebox len140" name="pay.usage" id="oa-usage-oaPurchasePayHandlePage" data-options="required:false" value="<c:out value='${pay.usage }'/>" autocomplete="off" maxlength="256"/></td>
                            </tr>
                            <tr>
                                <td class="left">付款银行：</td>
                                <td class=""><input class="easyui-validatebox len140" name="pay.paybank" id="oa-paybank-oaPurchasePayHandlePage" data-options="required:true" value="<c:out value='${pay.paybank }'/>" autocomplete="off" maxlength="20"/><span class="oa-item-required">*</span></td>
                                <td class="left">发票金额：</td>
                                <td class=""><input class="easyui-numberbox" style="width: 140px;" name="pay.invoiceamount" id="oa-invoiceamount-oaPurchasePayHandlePage" data-options="required:false,precision:2,max:9999999999" value="<c:out value='${pay.invoiceamount }'/>" autocomplete="off" maxlength="18"/></td>
                                <td class="left">备　　注：</td>
                                <td class=""><input class="easyui-validatebox len140" name="pay.remark" id="oa-remark-oaPurchasePayHandlePage" data-options="false:true" value="<c:out value='${pay.remark }'/>" autocomplete="off" maxlength="150"/></td>
                            </tr>
                        </table>
                    </div>
                    <div class="easyui-panel" data-options="border:false">
                        <div id="oa-comments-oaPurchasePayHandlePage">
                        </div>
                    </div>
                    <div style="border-top: 1px solid #AAA;">&nbsp;</div>
                    <div>
                        <div style="background: #EEE; font-weight: bold; padding: 7px;">附件</div>
                        <div id="oa-attach-oaPurchasePayHandlePage" style="width: 730px;"></div>
                    </div>
                    <div>
                        <div style="background: #EEE; font-weight: bold; padding: 7px;">审批信息</div>
                        <div id="oa-comments2-oaPurchasePayHandlePage" style="width: 725px;"></div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">
    <!--

    var $upperpayamount = $('#oa-upperpayamount-oaPurchasePayHandlePage');
    var $comments = $('#oa-comments-oaPurchasePayHandlePage');
    var $comments2 = $('#oa-comments2-oaPurchasePayHandlePage');
    var $attach = $('#oa-attach-oaPurchasePayHandlePage');

    var $id = $('#oa-id-oaPurchasePayHandlePage');
    var $businessdate = $('#oa-businessdate-oaPurchasePayHandlePage');
    var $remark = $('#oa-remark-oaPurchasePayHandlePage');
    var $oaid = $('#oa-oaid-oaPurchasePayHandlePage');
    var $paydate = $('#oa-paydate-oaPurchasePayHandlePage');
    var $relateoaid = $('#oa-relateoaid-oaPurchasePayHandlePage');
    var $receivername = $('#oa-receivername-oaPurchasePayHandlePage');
    var $receiverbank = $('#oa-receiverbank-oaPurchasePayHandlePage');
    var $receiverbankno = $('#oa-receiverbankno-oaPurchasePayHandlePage');
    var $payamount = $('#oa-payamount-oaPurchasePayHandlePage');
    var $upperpayamount = $('#oa-upperpayamount-oaPurchasePayHandlePage');
    var $usage = $('#oa-usage-oaPurchasePayHandlePage');
    var $paybank = $('#oa-paybank-oaPurchasePayHandlePage');
    var $invoiceamount = $('#oa-invoiceamount-oaPurchasePayHandlePage');

    var $oalink = $('#oa-oalink-oaPurchasePayHandlePage');

    var $form = $('#oa-form-oaPurchasePayHandlePage');

    $(function() {

        switchOaLink('${pay.relateoaid }');
    });

    -->
</script>
</body>
</html>