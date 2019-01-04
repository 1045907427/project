<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/struts-tags" prefix="struts"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<jsp:useBean id="today" class="java.util.Date"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>货款支付申请单(手机)</title>
    <%@include file="/phone/common/include.jsp"%>
    <script type="text/javascript">

        $(function() {

        });

        /**
         * 提交表单
         * @param call
         * @param args
         */
        function workFormSubmit(call, args) {

            // 验证
            $('#oa-form-oaPurchasePayViewPage').validate({
                focusInvalid: true,
                debug: true
            });

            $("#oa-form-oaPurchasePayViewPage").submit();
            var flag = $('#oa-form-oaPurchasePayViewPage').validate().form();
            if(!flag) {

                return false;
            }

            $('#oa-form-oaPurchasePayViewPage').validate({
                debug: false
            });

            $('#oa-form-oaPurchasePayViewPage').submit(function(){

                $(this).ajaxSubmit({
                    type: 'post',
                    <c:choose>
                        <c:when test="${empty pay or empty pay.id}">
                            url: 'oa/purchasepay/addOaPurchasePay.do',
                        </c:when>
                        <c:otherwise>
                            url: 'oa/purchasepay/editOaPurchasePay.do',
                        </c:otherwise>
                    </c:choose>
                    success: function(data) {

                        var json = $.parseJSON(data);
                        if(json.flag) {

                            call(json.backid);
                        }
                    },
                    error: function(data) {

                        alertMsg('保存出错！');
                    }
                });

                return false; //此处必须返回false，阻止常规的form提交

            }).submit();
        }
    </script>
</head>
<body>
<div data-role="page" id="main">
    <div data-role="header" data-position="fixed" data-tap-toggle="false">
        <c:choose>
            <c:when test="${param.type eq 'handle'}">
                <h1>处理工作[${param.processid }]</h1>
            </c:when>
            <c:otherwise>
                <h1>查看工作[${param.processid }]</h1>
            </c:otherwise>
        </c:choose>
        <a href="javascript:backPrev();" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a>
    </div>
    <form action="" id="oa-form-oaPurchasePayViewPage" method="post">
        <input type="hidden" name="pay.id" id="oa-id-oaPurchasePayViewPage" value="${param.id }"/>
        <input type="hidden" name="pay.oaid" id="oa-oaid-oaPurchasePayViewPage" value="${param.processid }"/>
        <div class="ui-corner-all custom-corners">
            <div class="ui-bar ui-bar-b">
                <h1>费用信息</h1>
            </div>
            <div class="ui-body ui-body-a">
                <div class="ui-field-contain">
                    <label>申请日期
                        <c:choose>
                            <c:when test="${empty pay}">
                                <input type="text" class="dateISO" name="pay.businessdate" id="oa-businessdate-oaPurchasePayViewPage" value="<fmt:formatDate value="${today }" pattern="yyyy-MM-dd" type="date" dateStyle="long" />" readonly="readonly" maxlength="10"/>
                            </c:when>
                            <c:otherwise>
                                <input type="text" class="dateISO" name="pay.businessdate" id="oa-businessdate-oaPurchasePayViewPage" value="${pay.businessdate}" readonly="readonly" maxlength="10"/>
                            </c:otherwise>
                        </c:choose>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>付款日期
                        <input type="text" class="dateISO" name="pay.paydate" id="oa-paydate-oaPurchasePayViewPage" value="<c:out value='${pay.paydate }'/>" readonly="readonly" maxlength="10"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>申请OA号
                        <input type="text" class="" name="pay.relateoaid" id="oa-relateoaid-oaPurchasePayViewPage" value="<c:out value='${pay.relateoaid }'/>" readonly="readonly" maxlength="10"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>收款单位
                        <input type="text" class="" name="pay.receivername" id="oa-receivername-oaPurchasePayViewPage" value="<c:out value='${pay.receivername }'/>" readonly="readonly" maxlength="50"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>开户银行
                        <input type="text" class="" name="pay.receiverbank" id="oa-receiverbank-oaPurchasePayViewPage" value="<c:out value='${pay.receiverbank }'/>" readonly="readonly" maxlength="50"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>银行账号
                        <input type="text" class="" name="pay.receiverbankno" id="oa-receiverbankno-oaPurchasePayViewPage" value="<c:out value='${pay.receiverbankno }'/>" readonly="readonly" maxlength="50"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>大写金额
                        <input type="text" class="" name="pay.upperpayamount" id="oa-upperpayamount-oaPurchasePayViewPage" value="<c:out value='${pay.upperpayamount }'/>" readonly="readonly" maxlength="50"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>付款金额<span style="color: #F00;">*</span>
                        <input type="number" class="required" name="pay.payamount" id="oa-payamount-oaPurchasePayViewPage" value="<c:out value='${pay.payamount }'/>" readonly="readonly" maxlength="50"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>用途
                        <input type="text" class="" name="pay.usage" id="oa-usage-oaPurchasePayViewPage" value="<c:out value='${pay.usage }'/>" readonly="readonly" maxlength="50"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>付款银行
                        <input type="text" class="" name="pay.paybank2" id="oa-paybank2-oaPurchasePayViewPage" value="<c:if test='${not empty bank }'><c:out value='${bank.name }'/></c:if>" readonly="readonly"/>
                        <input type="hidden" name="pay.paybank" id="oa-paybank-oaPurchasePayViewPage" value="<c:out value='${pay.paybank }'/>">
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>发票金额
                        <input type="number" class="" name="pay.invoiceamount" id="oa-invoiceamount-oaPurchasePayViewPage" value="<c:out value='${pay.invoiceamount }'/>" readonly="readonly" maxlength="15"/>
                    </label>
                </div>

                <div class="ui-field-contain">
                    <label>备注
                        <textarea name="pay.remark" id="oa-remark-oaPurchasePayViewPage" readonly="readonly"><c:out value="${pay.remark }"/></textarea>
                    </label>
                </div>
            </div>
        </div>
    </form>
    <c:if test="${param.type eq 'handle'}">
        <div id="oa-footer-oaSupplierPayViewPage" data-role="footer" data-position="fixed">
            <c:choose>
                <c:when test="${empty param.taskid }">
                    <jsp:include page="/activiti/phone/mywork/workHandleFooterPage.jsp?oarequired=${param.oarequired }">
                        <jsp:param name="taskid" value="${process.taskid }"/>
                        <jsp:param name="id" value="${param.processid }"/>
                        <jsp:param name="sign" value="${param.sign }"/>
                    </jsp:include>
                </c:when>
                <c:otherwise>
                    <jsp:include page="/activiti/phone/mywork/workHandleFooterPage.jsp?oarequired=${param.oarequired }">
                        <jsp:param name="taskid" value="${param.taskid }"/>
                        <jsp:param name="id" value="${param.processid }"/>
                        <jsp:param name="sign" value="${param.sign }"/>
                    </jsp:include>
                </c:otherwise>
            </c:choose>
        </div>
    </c:if>
</div>
</body>
</html>
