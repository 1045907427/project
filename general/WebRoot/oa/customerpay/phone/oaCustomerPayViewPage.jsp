<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="struts"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>客户费用支付申请单(手机)</title>
<%@include file="/phone/common/include.jsp"%>
<script type="text/javascript">

    $(document).on('pagebeforecreate', '#main', function() {

        // 初始化select数据
        $('select').each(function() {

            var sdata = $(this).attr('sdata');

            $(this).removeAttr('sdata');
            $(this).children().removeAttr('selected');

            $(this).children().each(function() {

                if($(this).attr('value') == sdata) {
                    $(this).attr('selected', 'selected');
                }
            });
        });
    });

    /**
     * 提交表单
     * @param call
     * @param args
     */
    function workFormSubmit(call, args) {

        // 验证
        $('#oa-form-oaCustomerPayViewPage').validate({
            focusInvalid: true,
            debug: true
        });

        $("#oa-form-oaCustomerPayViewPage").submit();
        var flag = $('#oa-form-oaCustomerPayViewPage').validate().form();
        if(!flag) {

            return false;
        }

        $('#oa-form-oaCustomerPayViewPage').validate({
            debug: false
        });

        $('#oa-customerpaydetail-oaCustomerPayViewPage').val(localStorage.getItem('details'));

        $('#oa-form-oaCustomerPayViewPage').submit(function(){

            $(this).ajaxSubmit({
                type: 'post',
                //beforeSubmit: showRequest,
                <c:choose>
                    <c:when test="${empty pay or empty pay.id}">
                        url: 'oa/addOaCustomerPay.do',
                    </c:when>
                    <c:otherwise>
                        url: 'oa/editOaCustomerPay.do',
                    </c:otherwise>
                </c:choose>
                success: function(data) {

                    var json = $.parseJSON(data);
                    if(json.flag) {

                        call(json.backid);
                    }
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
    <form action="" id="oa-form-oaCustomerPayViewPage" method="post">
        <input type="hidden" name="customerpay.id" id="oa-id-oaCustomerPayViewPage" value="${param.id }"/>
        <input type="hidden" name="customerpay.oaid" id="oa-oaid-oaCustomerPayViewPage" value="${param.processid }"/>
        <input type="hidden" name="customerpaydetail" id="oa-customerpaydetail-oaCustomerPayViewPage"/>
        <div class="ui-corner-all custom-corners">
            <div class="ui-bar ui-bar-b">
                <h1>费用信息</h1>
            </div>
            <div class="ui-body ui-body-a">
                <div class="ui-field-contain">
                    <label>已批OA号
                        <input type="number" precision="0" name="customerpay.relateoaid" id="oa-relateoaid-oaCustomerPayViewPage" value="${customerpay.relateoaid }" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>所属供应商<font color="#F00">*</font>
                        <input type="text" class="required" name="customerpay.supplierid2" id="oa-supplierid2-oaCustomerPayViewPage" value="<c:if test='${not empty supplier }'><c:out value='${customerpay.supplierid }'/>:<c:out value='${supplier.name }'/></c:if>" readonly="readonly" readonly="readonly"/>
                        <input type="hidden" name="customerpay.supplierid" id="oa-supplierid-oaCustomerPayViewPage" value="${customerpay.supplierid }">
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>所属部门<font color="#F00">*</font>
                        <input type="text" class="required" name="customerpay.deptid2" id="oa-deptid2-oaCustomerPayViewPage" value="<c:if test='${not empty dept }'><c:out value='${customerpay.deptid }'/>:<c:out value='${dept.name }'/></c:if>" readonly="readonly" readonly="readonly"/>
                        <input type="hidden" name="customerpay.deptid" id="oa-deptid-oaCustomerPayViewPage" value="${customerpay.deptid }" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>客户<font color="#F00">*</font>
                        <input type="text" class="required" id="oa-customerid2-oaCustomerPayViewPage" value="<c:if test='${not empty customer }'><c:out value='${customerpay.customerid }'/>:<c:out value='${customer.name }'/></c:if>" readonly="readonly" readonly="readonly"/>
                        <input type="hidden" name="customerpay.customerid" id="oa-customerid-oaCustomerPayViewPage" value="${customerpay.customerid }"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>客户银行
                        <input type="text" name="customerpay.collectionbank" id="oa-collectionbank-oaCustomerPayViewPage" value="${customerpay.collectionbank }" readonly="readonly" maxlength="66"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>客户账号
                        <input type="number" precision="0" name="customerpay.collectionbankno" id="oa-collectionbankno-oaCustomerPayViewPage" value="${customerpay.collectionbankno }" readonly="readonly" maxlength="25"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>付款金额<font color="#F00">*</font>
                        <input type="number" class="required" name="customerpay.payamount" id="oa-payamount-oaCustomerPayViewPage" value="${customerpay.payamount }" readonly="readonly" maxlength="15"/>
                        <input type="hidden" name="customerpay.upperpayamount" id="oa-upperpayamount-oaCustomerPayViewPage" value="${customerpay.upperpayamount }"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>付款银行
                        <input type="text" name="customerpay.paybank2" id="oa-paybank2-oaCustomerPayViewPage" value="<c:if test='${not empty bank }'><c:out value='${customerpay.paybank }'/>:<c:out value='${bank.name }'/></c:if>" data-clear-btn="false" readonly="readonly"/>
                        <input type="hidden" name="customerpay.paybank" id="oa-paybank-oaCustomerPayViewPage" value="${customerpay.paybank }"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>付款用途
                        <select sdata="${customerpay.payuse }" disabled="disabled">
                            <option></option>
                            <c:forEach items="${paytype }" var="pay">
                                <option value="${pay.code }"><c:out value="${pay.codename }"></c:out></option>
                            </c:forEach>
                        </select>
                        <input type="hidden" name="customerpay.payuse" id="oa-payuse-oaCustomerPayViewPage" value="${customerpay.payuse }"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>费用科目<font color="#F00">*</font>
                        <input type="text" class="required" name="customerpay.expensesort2" id="oa-expensesort2-oaCustomerPayViewPage" value="<c:if test='${not empty expensesort }'><c:out value='${customerpay.expensesort }'/>:<c:out value='${expensesort.name }'/></c:if>" readonly="readonly" readonly="readonly"/>
                        <input type="hidden" name="customerpay.expensesort" id="oa-expensesort-oaCustomerPayViewPage" value="${customerpay.expensesort }"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>发票种类
                        <select sdata="${customerpay.billtype }" disabled="disabled">
                            <option></option>
                            <c:forEach items="${invoicetype }" var="invoice">
                                <option value="${invoice.code }"><c:out value="${invoice.codename }"></c:out></option>
                            </c:forEach>
                        </select>
                        <input type="hidden" name="customerpay.billtype" id="oa-billtype-oaCustomerPayViewPage" value="${customerpay.billtype }"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>到票时间
                        <input type="text" name="customerpay.billdate" id="oa-billdate-oaCustomerPayViewPage" value="${customerpay.billdate }" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>发票金额
                        <input type="number" precision="2" name="customerpay.billamount" id="oa-billamount-oaCustomerPayViewPage" value="${customerpay.billamount }" readonly="readonly"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>付款日期
                        <input type="text" name="customerpay.paydate" id="oa-paydate-oaCustomerPayViewPage" value="${customerpay.paydate }" readonly="readonly"/>
                    </label>
                </div>
                <%--
                <div class="ui-field-contain">
                    <label>摊销时间
                        <input type="text" name="customerpay.sharebegindate" id="oa-sharebegindate-oaCustomerPayViewPage" value="${customerpay.sharebegindate }" readonly="readonly"/>
                        <input type="text" name="customerpay.shareenddate" id="oa-shareenddate-oaCustomerPayViewPage" value="${customerpay.shareenddate }" readonly="readonly"/>
                    </label>
                </div>
                --%>
                <input type="hidden" name="customerpay.sharebegindate" id="oa-sharebegindate-oaCustomerPayViewPage" value="${customerpay.sharebegindate }" readonly="readonly"/>
                <input type="hidden" name="customerpay.shareenddate" id="oa-shareenddate-oaCustomerPayViewPage" value="${customerpay.shareenddate }" readonly="readonly"/>
                <div class="ui-field-contain">
                    <label>说明
                        <textarea name="customerpay.remark" id="oa-remark-oaCustomerPayViewPage"><c:out value="${customerpay.remark }"/></textarea>
                    </label>
                </div>
            </div>
        </div>
    </form>
    <c:if test="${param.type eq 'handle'}">
        <div id="oa-footer-oaCustomerPayViewPage" data-role="footer" data-position="fixed">
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
