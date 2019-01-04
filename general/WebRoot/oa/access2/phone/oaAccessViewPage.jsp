<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.hd.agent.common.util.BillGoodsNumDecimalLenUtils" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="struts"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>特价通路申请单(手机)</title>
    <%@include file="/phone/common/include.jsp"%>
    <script type="text/javascript">

        var prices = $.parseJSON('${pricesJSON }');
        var amounts = $.parseJSON('${amountsJSON }');
        localStorage.setItem("prices", '${pricesJSON }');
        localStorage.setItem("amounts", '${amountsJSON }');

        // 显示主页面时，reload明细数据
        $(document).on('pageshow', '#main', function(e) {

            refreshPrices(e);
            refreshAmounts(e);

            numberPrecision();
        });
        //$(document).on('pageshow', '#main', refreshPrices);

        /*
        * 刷新价格明细
        */
        function refreshPrices(e) {

            var prices = $.parseJSON(localStorage.getItem("prices"));

            var html = new Array();

            for(var i in prices) {

                var price = prices[i];

                html.push('<li>');
//                html.push('<a href="#oa-pricedetail-oaAccessPriceDetailPage" onclick="javascript:localStorage.setItem(\'index\',' + i + ');" style="font-weight: 100;">');
                html.push('<table>');

                html.push('<tr>');
                html.push('<th>商品:</th>');
                html.push('<td>' + price.goodsid + ':' + price.goodsname + '</span></td>');
                html.push('</tr>');

                html.push('<tr>');
                html.push('<th>进货价:</th>');
                html.push('<td><span precision="2">' + price.buyprice + '</span></td>');
                html.push('</tr>');

                html.push('<tr>');
                html.push('<th>工厂让利:</th>');
                html.push('<td><span precision="2">' + price.factoryprice + '</span></td>');
                html.push('</tr>');

                html.push('<tr>');
                html.push('<th>自理:</th>');
                html.push('<td><span precision="2">' + price.myprice + '</span></td>');
                html.push('</tr>');

                html.push('<tr>');
                html.push('<th>原价:</th>');
                html.push('<td><span precision="2">' + price.oldprice + '</span></td>');
                html.push('</tr>');

                html.push('<tr>');
                html.push('<th>现价:</th>');
                html.push('<td><span precision="2">' + price.newprice + '</span></td>');
                html.push('</tr>');

                html.push('<tr>');
                html.push('<th>毛利率:</th>');
                html.push('<td><span precision="2">' + price.rate + '</span></td>');
                html.push('</tr>');

                html.push('<tr>');
                html.push('<th>门店出货:</th>');
                html.push('<td><span>' + price.senddetail + '</span></td>');
                html.push('</tr>');

                html.push('</table>');
//                html.push('</a>');
                html.push('</li>');
            }

            $('#oa-pricedetail-oaAccessViewPage').find(':not(:eq(0))').remove();
            $('#oa-pricedetail-oaAccessViewPage').append(html.join(''));

            try {

                $('#oa-pricedetail-oaAccessViewPage').listview('refresh');
            } catch(e) {}

        }

        /**
         * 刷新数量明细
         */
        function refreshAmounts(e) {

            var amounts = $.parseJSON(localStorage.getItem("amounts"));

            var html = new Array();

            for(var i in amounts) {

                var amount = amounts[i];

                html.push('<li>');
//                html.push('<a href="#oa-amountdetail-oaAccessAmountDetailPage" onclick="javascript:localStorage.setItem(\'index\',' + i + ');" style="font-weight: 100;">');
                html.push('<table>');

                html.push('<tr>');
                html.push('<th>商品:</th>');
                html.push('<td>' + amount.goodsid + ':' + amount.goodsname + '</span></td>');
                html.push('</tr>');

                html.push('<tr>');
                html.push('<th>单位差价:</th>');
                html.push('<td><span precision="2">' + amount.difprice + '</span></td>');
                html.push('</tr>');

                html.push('<tr>');
                html.push('<th>数量:</th>');
                html.push('<td><span precision="<%=BillGoodsNumDecimalLenUtils.decimalLen %>">' + amount.unitnum + '</span></td>');
                html.push('</tr>');

                html.push('<tr>');
                html.push('<th>差价金额:</th>');
                html.push('<td><span precision="2">' + amount.amount + '</span></td>');
                html.push('</tr>');

                html.push('<tr>');
                html.push('<th>ERP数量:</th>');
                html.push('<td><span precision="<%=BillGoodsNumDecimalLenUtils.decimalLen %>">' + amount.erpnum + '</span></td>');
                html.push('</tr>');

                html.push('<tr>');
                html.push('<th>降价金额:</th>');
                html.push('<td><span precision="2">' + amount.downamount + '</span></td>');
                html.push('</tr>');

                html.push('</table>');
//                html.push('</a>');
                html.push('</li>');
            }

            $('#oa-amountdetail-oaAccessViewPage').find(':not(:eq(0))').remove();
            $('#oa-amountdetail-oaAccessViewPage').append(html.join(''));

            try {

                $('#oa-amountdetail-oaAccessViewPage').listview('refresh');
            } catch(e) {}
            numberPrecision();
        }

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

        $(function() {

        });

        /**
        * 小数位截取
         */
        function numberPrecision() {

            $('span[precision]').each(function(index) {

                var text = $(this).text();

                var precision = $(this).attr('precision');

                if(text != undefined && text != '' && text != null) {

                    text = parseFloat(text).toFixed(parseInt(precision));
                }

                $(this).text(text);
            });
        }

        /**
        * 提交表单
        * @param call
        * @param args
        * @returns {boolean}
         */
        function workFormSubmit(call, args) {

            // 验证
            $('#oa-form-oaAccessViewPage').validate({
                focusInvalid: true,
                debug:true
            });

            $("#oa-form-oaAccessViewPage").submit();
            var flag = $('#oa-form-oaAccessViewPage').valid();
            if(!flag) {

                return false;
            }

            $('#oa-form-oaAccessViewPage').validate({
                debug: false
            });

            $('#oa-goodsprice-oaAccessViewPage').val(JSON.stringify(prices));
            $('#oa-goodsamount-oaAccessViewPage').val(JSON.stringify(amounts));

            $('#oa-form-oaAccessViewPage').submit(function(){

                $(this).ajaxSubmit({
                    type: 'post',
                    url: 'oa/hd/editOaAccess.do',
                    //beforeSubmit: showRequest,
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
        <form action="" id="oa-form-oaAccessViewPage" method="post">
            <input type="hidden" name="goodsprice" id="oa-goodsprice-oaAccessViewPage" value=""/>
            <input type="hidden" name="goodsamount" id="oa-goodsamount-oaAccessViewPage" value=""/>
            <input type="hidden" name="access.id" id="oa-id-oaAccessViewPage" value="${param.id }"/>
            <input type="hidden" name="access.oaid" id="oa-oaid-oaAccessViewPage" value="${param.processid }"/>
            <div class="ui-corner-all custom-corners">
                <div class="ui-bar ui-bar-b">
                    <h1>第一确认</h1>
                </div>
                <div class="ui-body ui-body-a">
                    <div class="ui-field-contain">
                        <label>供应商
                            <c:choose>
                                <c:when test="${not empty access and not empty access.supplierid}">
                                    <input type="text" class="required" name="access.supplierid2" id="oa-supplierid2-oaAccessViewPage" value="${access.supplierid }:${supplier.name }" readonly="readonly"/>
                                </c:when>
                                <c:otherwise>
                                    <input type="text" class="required" name="access.supplierid2" id="oa-supplierid2-oaAccessViewPage" value="" readonly="readonly"/>
                                </c:otherwise>
                            </c:choose>
                            <input type="hidden" class="required" name="access.supplierid" id="oa-supplierid-oaAccessViewPage" value="${access.supplierid }" readonly="readonly"/>
                        </label>
                    </div>
                    <div class="ui-field-contain">
                        <label>时间段
                            <input type="text" class="required dateISO" name="access.planbegindate" id="oa-planbegindate-oaAccessViewPage" value="${access.planbegindate }" readonly="readonly"/>
                            <input type="text" class="required dateISO" name="access.planenddate" id="oa-planenddate-oaAccessViewPage" value="${access.planenddate }" readonly="readonly"/>
                        </label>
                    </div>
                    <div class="ui-field-contain">
                        <label>确认单号
                            <input type="text" class="number" name="access.confirmid" id="oa-confirmid-oaAccessViewPage" value="${access.confirmid }" readonly="readonly"/>
                        </label>
                    </div>
                    <div class="ui-field-contain">
                        <label>申请通路费
                            <c:choose>
                                <c:when test="${not empty access and not empty access.expensesort}">
                                    <input type="text" name="access.expensesort2" id="oa-expensesort2-oaAccessViewPage" value="${expensesort.id }:${expensesort.name }" readonly="readonly"/>
                                </c:when>
                                <c:otherwise>
                                    <input type="text" class="required" name="access.expensesort2" id="oa-expensesort2-oaAccessViewPage" value="" readonly="readonly"/>
                                </c:otherwise>
                            </c:choose>
                            <input type="hidden" name="access.expensesort" id="oa-expensesort-oaAccessViewPage" value="${access.expensesort }" readonly="readonly"/>
                        </label>
                    </div>
                    <div class="ui-field-contain">
                        <label class="select">申请特价
                            <select sdata="${access.pricetype }" disabled="disabled">
                                <option value=""></option>
                                <option value="1">补差特价</option>
                                <option value="2">降价特价</option>
                            </select>
                            <input type="hidden" name="access.pricetype" id="oa-pricetype-oaAccessViewPage" value="${access.pricetype }"/>
                        </label>
                    </div>
                    <div class="ui-field-contain">
                        <label class="select">是否补库存
                            <select sdata="${access.isaddstorage }" disabled="disabled">
                                <option value="0">否</option>
                                <option value="1">是</option>
                            </select>
                            <input type="hidden" name="access.isaddstorage" id="oa-isaddstorage-oaAccessViewPage" value="${access.isaddstorage }"/>
                        </label>
                    </div>
                    <div class="ui-field-contain">
                        <label>客户
                            <c:choose>
                                <c:when test="${not empty access and not empty access.customerid }">
                                    <input type="text" class="required" name="access.customerid2" id="oa-customerid2-oaAccessViewPage" value="${access.customerid }:${customer.name }" readonly="readonly"/>
                                </c:when>
                                <c:otherwise>
                                    <input type="text" class="required" name="access.customerid2" id="oa-customerid2-oaAccessViewPage" value="" readonly="readonly"/>
                                </c:otherwise>
                            </c:choose>
                            <input type="hidden" class="required" name="access.customerid" id="oa-customerid-oaAccessViewPage" value="${access.customerid }" readonly="readonly"/>
                        </label>
                    </div>
                    <div class="ui-field-contain">
                        <label>执行地点
                            <input type="text" name="access.executeaddr" id="oa-executeaddr-oaAccessViewPage" value="${access.executeaddr }" readonly="readonly"/>
                        </label>
                    </div>
                    <div class="ui-field-contain">
                        <ul data-role="listview" id="oa-pricedetail-oaAccessViewPage" data-inset="false">
                            <li data-role="list-divider">价格明细</li>
                        </ul>
                    </div>
                    <%--
                    <div class="ui-field-contain">
                        <label>工厂金额
                            <input type="number" name="access.factoryamount" id="oa-factoryamount-oaAccessViewPage" value="${access.factoryamount }" readonly="readonly"/>
                        </label>
                    </div>
                    --%>
                    <div class="ui-field-contain">
                        <label>自理金额
                            <input type="number" name="access.myamount" id="oa-myamount-oaAccessViewPage" value="${access.myamount }" readonly="readonly"/>
                        </label>
                    </div>
                    <div class="ui-field-contain">
                        <label>收回方式
                            <select sdata="${access.reimbursetype }" disabled="disabled">
                                <option></option>
                                <c:forEach items="${typelist }" var="type">
                                    <option value="${type.code }"><c:out value="${type.codename }"></c:out></option>
                                </c:forEach>
                            </select>
                            <input type="hidden" name="access.reimbursetype" id="oa-reimbursetype-oaAccessViewPage" value="${access.reimbursetype }"/>
                        </label>
                    </div>
                    <div class="ui-field-contain">
                        <label>收回日期
                            <input type="text" name="access.reimbursedate" id="oa-reimbursedate-oaAccessViewPage" value="${access.reimbursedate }" data-clear-btn="false" readonly="readonly"/>
                        </label>
                    </div>
                    <div class="ui-field-contain">
                        <label>说明
                            <textarea name="access.remark1" id="oa-remark1-oaAccessViewPage" readonly="readonly"><c:out value="${access.remark1}"/></textarea>
                        </label>
                    </div>
                </div>
            </div>
            <div class="ui-corner-all custom-corners">
                <div class="ui-bar ui-bar-b">
                    <h1>第二确认</h1>
                </div>
                <div class="ui-body ui-body-a">
                    <div class="ui-field-contain">
                        <label>确认时间
                            <input type="text" class="dateISO" name="access.conbegindate" id="oa-conbegindate-oaAccessViewPage" value="${access.conbegindate }" readonly="readonly"/>
                            <input type="text" class="dateISO" name="access.conenddate" id="oa-conenddate-oaAccessViewPage" value="${access.conenddate }" readonly="readonly"/>
                        </label>
                    </div>
                    <div class="ui-field-contain">
                        <label>降价设定时间
                            <input type="text" class="dateISO" name="access.combegindate" id="oa-combegindate-oaAccessViewPage" value="${access.combegindate }" readonly="readonly"/>
                            <input type="text" class="dateISO" name="access.comenddate" id="oa-comenddate-oaAccessViewPage" value="${access.comenddate }" readonly="readonly"/>
                        </label>
                    </div>
                    <label class="select">支付方式
                        <select sdata="${access.paytype }" disabled="disabled">
                            <option></option>
                            <option value="1">折扣</option>
                            <option value="2">支票</option>
                        </select>
                        <input type="hidden" name="access.paytype" id="oa-paytype-oaAccessViewPage" value="${access.paytype }"/>
                    </label>
                    <div class="ui-field-contain">
                        <label>费用金额
                            <input type="number" name="access.totalamount" id="oa-totalamount-oaAccessViewPage" value="${access.totalamount }" readonly="readonly"/>
                        </label>
                    </div>
                    <%--
                    <div class="ui-field-contain">
                        <label>数量
                            <input type="number" name="access.totalnum" id="oa-totalnum-oaAccessViewPage" value="${access.totalnum }" data-clear-btn="false" readonly="readonly"/>
                        </label>
                    </div>
                    --%>
                    <div class="ui-field-contain">
                        <label>支付日期
                            <input type="text" name="access.paydate" id="oa-paydate-oaAccessViewPage" value="${access.paydate }" readonly="readonly"/>
                        </label>
                    </div>
                    <div class="ui-field-contain">
                        <ul data-role="listview" id="oa-amountdetail-oaAccessViewPage" data-inset="false">
                            <li data-role="list-divider">数量明细</li>
                        </ul>
                    </div>
                    <div class="ui-field-contain">
                        <label>说明
                            <textarea name="access.remark2" id="oa-remark2-oaAccessViewPage" readonly="readonly"><c:out value="${access.remark2}"/></textarea>
                        </label>
                    </div>
                </div>
            </div>
            <div class="ui-corner-all custom-corners">
                <div class="ui-bar ui-bar-b">
                    <h1>第三确认</h1>
                </div>
                <div class="ui-body ui-body-a">
                    <div class="ui-field-contain">
                        <label>电脑冲差金额
                            <input type="number" name="access.compdiscount" id="oa-compdiscount-oaAccessViewPage" value="${access.compdiscount }" readonly="readonly"/>
                        </label>
                    </div>
                    <div class="ui-field-contain">
                        <label>电脑降价金额
                            <input type="number" name="access.comdownamount" id="oa-comdownamount-oaAccessViewPage" value="${access.comdownamount }" readonly="readonly"/>
                        </label>
                    </div>
                    <div class="ui-field-contain">
                        <label>支票金额
                            <input type="number" name="access.payamount" id="oa-payamount-oaAccessViewPage" value="${access.payamount }" readonly="readonly"/>
                        </label>
                    </div>
                    <div class="ui-field-contain">
                        <label>结算金额
                            <input type="number" name="access.branchaccount" id="oa-branchaccount-oaAccessViewPage" value="${access.branchaccount }" readonly="readonly"/>
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
