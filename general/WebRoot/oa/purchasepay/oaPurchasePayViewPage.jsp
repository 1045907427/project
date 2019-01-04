<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="/tag/process" prefix="process"%>
<jsp:useBean id="today" class="java.util.Date"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>行政采购付款申请单查看页面</title>
    <%@include file="/include.jsp" %>
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
                <form action="oa/purchasepay/addOaPurchasePay.do" id="oa-form-oaPurchasePayViewPage" method="post">
            </c:when>
            <c:otherwise>
                <form action="oa/purchasepay/editOaPurchasePay.do" id="oa-form-oaPurchasePayViewPage" method="post">
            </c:otherwise>
        </c:choose>
            <input type="hidden" name="pay.id" id="oa-id-oaPurchasePayViewPage" value="${pay.id }"/>
            <input type="hidden" name="pay.oaid" id="oa-oaid-oaPurchasePayViewPage" value="${param.processid }"/>
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
                                            <input class="easyui-validatebox len140 Wdate" name="pay.businessdate" id="oa-businessdate-oaPurchasePayViewPage" value="<fmt:formatDate value="${today }" pattern="yyyy-MM-dd" type="date" dateStyle="long" />" autocomplete="off" maxlength="10" readonly="readonly"/>
                                        </c:when>
                                        <c:otherwise>
                                            <input class="easyui-validatebox len140 Wdate" name="pay.businessdate" id="oa-businessdate-oaPurchasePayViewPage" value="<c:out value='${pay.businessdate }'/>" autocomplete="off" maxlength="10" readonly="readonly"/>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="len80 left">付款日期：</td>
                                <td class="len180"><input class="easyui-validatebox len140 Wdate" name="pay.paydate" id="oa-paydate-oaPurchasePayViewPage" value="<c:out value='${pay.paydate }'/>" autocomplete="off" maxlength="10" readonly="readonly"/></td>
                                <td class="len80 left">申请OA号：</td>
                                <td class="len180"><input class="easyui-textbox len140" name="pay.relateoaid" id="oa-relateoaid-oaPurchasePayViewPage" data-options="required:false,onChange:switchOaLink" value="<c:out value='${pay.relateoaid }'/>" autocomplete="off" maxlength="10" readonly="readonly"/><a id="oa-oalink-oaPurchasePayViewPage" href="javascript:void(0);" onclick="javascript:viewOaProcess(this.getAttribute('oaid'));">查看</a></td>
                            </tr>
                            <tr>
                                <td class="left">收款单位：</td>
                                <td class=""><input class="easyui-validatebox len140" name="pay.receivername" id="oa-receivername-oaPurchasePayViewPage" data-options="required:false" value="<c:out value='${pay.receivername }'/>" autocomplete="off" maxlength="50" readonly="readonly"/></td>
                                <td class="left">开户银行：</td>
                                <td class=""><input class="easyui-validatebox len140" name="pay.receiverbank" id="oa-receiverbank-oaPurchasePayViewPage" data-options="required:false" value="<c:out value='${pay.receiverbank }'/>" autocomplete="off" maxlength="50" readonly="readonly"/></td>
                                <td class="left">银行账号：</td>
                                <td class=""><input class="easyui-validatebox len140" name="pay.receiverbankno" id="oa-receiverbankno-oaPurchasePayViewPage" data-options="required:false" value="<c:out value='${pay.receiverbankno }'/>" autocomplete="off" maxlength="50" readonly="readonly"/></td>
                            </tr>
                            <tr>
                                <td class="left">大写金额：</td>
                                <td class=""><input class="easyui-validatebox" style="width: 140px;" name="pay.upperpayamount" id="oa-upperpayamount-oaPurchasePayViewPage" data-options="required:false" value="<c:out value='${pay.upperpayamount }'/>" autocomplete="off" maxlength="30" readonly="readonly"/>
                                <td class="left">付款金额：</td>
                                <td class=""><input class="easyui-numberbox" style="width: 140px;" name="pay.payamount" id="oa-payamount-oaPurchasePayViewPage" data-options="precision:2,required:true,max:9999999999,onChange:changeAmount2Upper" value="<c:out value='${pay.payamount }'/>" autocomplete="off" maxlength="18" readonly="readonly"/><span class="oa-item-required">*</span></td></td>
                                <td class="left">用　　途：</td>
                                <td class=""><input class="easyui-validatebox len140" name="pay.usage" id="oa-usage-oaPurchasePayViewPage" data-options="required:false" value="<c:out value='${pay.usage }'/>" autocomplete="off" maxlength="256" readonly="readonly"/></td>
                            </tr>
                            <tr>
                                <td class="left">付款银行：</td>
                                <td class=""><input class="easyui-validatebox len140" name="pay.paybank" id="oa-paybank-oaPurchasePayViewPage" data-options="required:true" value="<c:out value='${pay.paybank }'/>" autocomplete="off" maxlength="20" readonly="readonly"/></td>
                                <td class="left">发票金额：</td>
                                <td class=""><input class="easyui-numberbox" style="width: 140px;" name="pay.invoiceamount" id="oa-invoiceamount-oaPurchasePayViewPage" data-options="required:false,precision:2,max:9999999999" value="<c:out value='${pay.invoiceamount }'/>" autocomplete="off" maxlength="18" readonly="readonly"/></td>
                                <td class="left">备　　注：</td>
                                <td class=""><input class="easyui-validatebox len140" name="pay.remark" id="oa-remark-oaPurchasePayViewPage" data-options="false:true" value="<c:out value='${pay.remark }'/>" autocomplete="off" maxlength="150" readonly="readonly"/></td>
                            </tr>
                        </table>
                    </div>
                    <div class="easyui-panel" data-options="border:false">
                        <div id="oa-comments-oaPurchasePayViewPage">
                        </div>
                    </div>
                    <div style="border-top: 1px solid #AAA;">&nbsp;</div>
                    <div>
                        <div style="background: #EEE; font-weight: bold; padding: 7px;">附件</div>
                        <div id="oa-attach-oaPurchasePayViewPage" style="width: 730px;">
                        </div>
                    </div>
                    <div>
                        <div style="background: #EEE; font-weight: bold; padding: 7px;">审批信息</div>
                        <div id="oa-comments2-oaPurchasePayViewPage" style="width: 725px;">
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
<script type="text/javascript">
    <!--

    var $upperpayamount = $('#oa-upperpayamount-oaPurchasePayViewPage');
    var $comments = $('#oa-comments-oaPurchasePayViewPage');
    var $comments2 = $('#oa-comments2-oaPurchasePayViewPage');
    var $attach = $('#oa-attach-oaPurchasePayViewPage');

    var $id = $('#oa-id-oaPurchasePayViewPage');
    var $businessdate = $('#oa-businessdate-oaPurchasePayViewPage');
    var $remark = $('#oa-remark-oaPurchasePayViewPage');
    var $oaid = $('#oa-oaid-oaPurchasePayViewPage');
    var $paydate = $('#oa-paydate-oaPurchasePayViewPage');
    var $relateoaid = $('#oa-relateoaid-oaPurchasePayViewPage');
    var $receivername = $('#oa-receivername-oaPurchasePayViewPage');
    var $receiverbank = $('#oa-receiverbank-oaPurchasePayViewPage');
    var $receiverbankno = $('#oa-receiverbankno-oaPurchasePayViewPage');
    var $payamount = $('#oa-payamount-oaPurchasePayViewPage');
    var $upperpayamount = $('#oa-upperpayamount-oaPurchasePayViewPage');
    var $usage = $('#oa-usage-oaPurchasePayViewPage');
    var $paybank = $('#oa-paybank-oaPurchasePayViewPage');
    var $invoiceamount = $('#oa-invoiceamount-oaPurchasePayViewPage');

    var $oalink = $('#oa-oalink-oaPurchasePayViewPage');

    var $form = $('#oa-form-oaPurchasePayViewPage');

    $(function() {

        switchOaLink('${pay.relateoaid }');
    });

    -->
</script>
</body>
</html>