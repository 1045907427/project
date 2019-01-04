<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/struts-tags" prefix="struts"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<jsp:useBean id="today" class="java.util.Date"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>客户费用申请单（账扣）(手机)</title>
    <style type="text/css">
        .warnning {
            color: #F00;
        }
    </style>
    <%@include file="/phone/common/include.jsp"%>
    <script type="text/javascript">

        var feeList = $.parseJSON('${list }');
        localStorage.setItem('details', '${list }');

        // 显示主页面时，reload明细数据
        $(document).on('pageshow', '#main', function(e) {

            refreshFeeList(e);

            numberPrecision();
        });

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

        function refreshFeeList(e){

            var feeList = $.parseJSON(localStorage.getItem("details"));

            var html = new Array();

            for(var i in feeList) {

                var feedetail = feeList[i];

                html.push('<li>');
                html.push('<a href="oa/customerfee/phone/oaCustomerFeeDetailPage.jsp?" onclick="javascript:localStorage.setItem(\'index\',' + i + ');" style="font-weight: 100;">');
                html.push('<table>');

                html.push('<tr>');
                html.push('<th>供应商:</th>');
                html.push('<td>' + feedetail.supplierid + ':' + feedetail.suppliername + '</span></td>');
                html.push('</tr>');

                html.push('<tr>');
                html.push('<th>部门:</th>');
                html.push('<td><span>'  + feedetail.deptid + ':' + feedetail.deptname + '</span></td>');
                html.push('</tr>');

                html.push('<tr>');
                html.push('<th>品牌:</th>');
                html.push('<td>' + feedetail.brandid + ':' + feedetail.brandname + '</span></td>');
                html.push('</tr>');

                html.push('<tr>');
                html.push('<th>申请事由:</th>');
                html.push('<td><span>' + feedetail.reason + '</span></td>');
                html.push('</tr>');

                html.push('<tr>');
                html.push('<th>工厂投入:</th>');
                html.push('<td><span precision="2">' + feedetail.factoryamount + '</span></td>');
                html.push('</tr>');

                html.push('<tr>');
                html.push('<th>自理:</th>');
                html.push('<td><span precision="2">' + feedetail.selfamount + '</span></td>');
                html.push('</tr>');

                html.push('<tr>');
                html.push('<th>品牌责任人:</th>');
                html.push('<td><span>' + feedetail.branduser + '</span></td>');
                html.push('</tr>');

                html.push('</table>');
                html.push('</a>');
                html.push('</li>');
            }

            $('#oa-detail-oaCustomerFeeHandlePage').find(':not(:eq(0))').remove();
            $('#oa-detail-oaCustomerFeeHandlePage').append(html.join(''));

            try {

                $('#oa-detail-oaCustomerFeeHandlePage').listview('refresh');
            } catch(e) {}

        }

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

        $(function() {

            // 客户
            $('#oa-customerid2-oaCustomerFeeHandlePage').on('click', function() {

                androidWidget({
                    type: 'widget',
                    referwid: 'RL_T_BASE_SALES_CUSTOMER_PARENT_2',
                    onSelect: 'selectCustomer'
                });
            }).on('change', function() {

                var v = $(this).val();
                $(this).attr('value', v);
                $('#oa-customerid-oaCustomerFeeHandlePage').attr('value', (v || ':').split(':')[0]);
            });

            // 申请日期
            $(document).on('click', '#oa-businessdate-oaCustomerFeeHandlePage', function() {

                androidDateWidget($('#oa-businessdate-oaCustomerFeeHandlePage').val() || new Date().Format('yyyy-MM-dd'), 'yyyy-MM-dd', 'selectBusinessdate');
            });

            // 费用科目
            $('#oa-expensesort2-oaCustomerFeeHandlePage').on('click', function() {

                androidWidget({
                    type: 'widget',
                    referwid: 'RT_T_BASE_FINANCE_EXPENSES_SORT',
                    onSelect: 'selectExpensesort'
                });
            }).on('change', function() {

                var v = $(this).val();
                $(this).attr('value', v);
                $('#oa-expensesort-oaCustomerFeeHandlePage').attr('value', (v || ':').split(':')[0]);
            });

            // 付款银行
            $('#oa-paybank2-oaCustomerFeeHandlePage').on('click', function() {

                androidWidget({
                    type: 'widget',
                    referwid: 'RL_T_BASE_FINANCE_BANK',
                    onSelect: 'selectBank'
                });
            }).on('change', function() {

                var v = $(this).val();
                $(this).attr('value', v);
                $('#oa-paybank-oaCustomerFeeHandlePage').attr('value', (v || ':').split(':')[0]);
            });

        });

        /**
         * 选择客户
         */
        function selectCustomer(data) {

            $('#oa-customerid2-oaCustomerFeeHandlePage').val(data.id + ':' + data.name);
            $('#oa-customerid-oaCustomerFeeHandlePage').val(data.id);

            $('#oa-customerid2-oaCustomerFeeHandlePage').blur();
            $('#oa-customerid2-oaCustomerFeeHandlePage').change();
        }

        /**
         * 选择申请日期
         */
        function selectBusinessdate(data) {

            $('#oa-businessdate-oaCustomerFeeHandlePage').val(data);
            $('#oa-businessdate-oaCustomerFeeHandlePage').blur();
        }

        /**
         * 选择付款银行
         */
        function selectBank(data) {

            $('#oa-paybank2-oaCustomerFeeHandlePage').val(data.id + ':' + data.name);
            $('#oa-paybank-oaCustomerFeeHandlePage').val(data.id);

            $('#oa-paybank2-oaCustomerFeeHandlePage').blur();
            $('#oa-paybank2-oaCustomerFeeHandlePage').change();
        }

        /**
         * 选择费用科目
         */
        function selectExpensesort(data) {

            $('#oa-expensesort2-oaCustomerFeeHandlePage').val(data.id + ':' + data.name);
            $('#oa-expensesort-oaCustomerFeeHandlePage').val(data.id);

            $('#oa-expensesort2-oaCustomerFeeHandlePage').blur();
            $('#oa-expensesort2-oaCustomerFeeHandlePage').change();
        }

        function removeDetail(){

            var index = localStorage.getItem('index');

            if(index == -1) {

                return true;
            }

            feeList.splice(index, 1);
            localStorage.setItem("details", JSON.stringify(feeList));
            return true;
        }

        function saveDetail(){

            $('#oa-form-oaCustomerFeeDetailPage').validate({
                focusInvalid: true,
                debug: true,
                rules: {
                }
            });

            var flag = $('#oa-form-oaCustomerFeeDetailPage').validate().form();

            if(!flag) {

                return flag;
            }

            var data = $('#oa-form-oaCustomerFeeDetailPage').serializeJSON();

            // 验证
            $("#oa-form-oaCustomerFeeDetailPage").submit();

            $('#oa-form-oaCustomerFeeDetailPage').form('clear');
            $('#oa-form-oaCustomerFeeDetailPage')[0].reset();
            var index = parseInt(localStorage.getItem('index'));

            data.factoryamount = formatterMoney(data.factoryamount);
            data.selfamount = formatterMoney(data.selfamount);
            if(index == -1) {
                feeList.push(data);
            } else {
                feeList[index] = data;
            }

            var payamount = 0;
            for(var i in feeList) {

                var fee = feeList[i];
                payamount = parseFloat(payamount) + parseFloat(fee.factoryamount) + parseFloat(fee.selfamount);
            }

            $('#oa-payamount-oaCustomerFeeHandlePage').val(formatterMoney(payamount));
            localStorage.setItem('details', JSON.stringify(feeList));
            localStorage.setItem('index', -1);

            return true;
        }

        /**
         * 提交表单
         * @param call
         * @param args
         */
        function workFormSubmit(call, args) {

            // 验证
            $('#oa-form-oaCustomerFeeHandlePage').validate({
                focusInvalid: true,
                debug: true
            });

            $("#oa-form-oaCustomerFeeHandlePage").submit();
            var flag = $('#oa-form-oaCustomerFeeHandlePage').validate().form();
            if(!flag) {

                return false;
            }

            $('#oa-form-oaCustomerFeeHandlePage').validate({
                debug: false
            });

            $('#oa-detaillist-oaCustomerFeeHandlePage').val(localStorage.getItem('details'));

            $('#oa-form-oaCustomerFeeHandlePage').submit(function(){

                $(this).ajaxSubmit({
                    type: 'post',
                    //beforeSubmit: showRequest,
                    <c:choose>
                        <c:when test="${empty customerfee or empty customerfee.id}">
                            url: 'oa/customerfee/addOaCustomerFee.do',
                        </c:when>
                        <c:otherwise>
                            url: 'oa/customerfee/editOaCustomerFee.do',
                        </c:otherwise>
                    </c:choose>
                    success: function(data) {

                        var json = $.parseJSON(data);
                        if(json.flag) {

                            call(json.backid);
                        }
                    },
                    error: function(data) {

                        alertMsg(data);
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
        <a href="javascript:backPrev();" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a>
        <h1>处理工作[${param.processid }]</h1>
        <%--<a href="javascript:location.href = location.href;" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-right ui-icon-refresh" style="border: 0px; background: #E9E9E9;">刷新</a>--%>
    </div>
    <form action="" id="oa-form-oaCustomerFeeHandlePage" method="post">
        <input type="hidden" name="customerfee.id" id="oa-id-oaCustomerFeeHandlePage" value="${param.id }"/>
        <input type="hidden" name="customerfee.oaid" id="oa-oaid-oaCustomerFeeHandlePage" value="${param.processid }"/>
        <input type="hidden" name="detaillist" id="oa-detaillist-oaCustomerFeeHandlePage"/>
        <div class="ui-corner-all custom-corners">
            <div class="ui-bar ui-bar-b">
                <h1>费用信息</h1>
            </div>
            <div class="ui-body ui-body-a">
                <div class="ui-field-contain">
                    <label>客户<span class="warnning">*</span>
                        <input type="text" class="required" name="customerfee.customerid2" id="oa-customerid2-oaCustomerFeeHandlePage" value="<c:if test='${not empty customer }'><c:out value='${customerfee.customerid }'/>:<c:out value='${customer.name }'/></c:if>" data-clear-btn="true" readonly="readonly"/>
                        <input type="hidden" name="customerfee.customerid" id="oa-customerid-oaCustomerFeeHandlePage" value="${customerfee.customerid }"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>申请日期<span class="warnning">*</span>
                        <c:choose>
                            <c:when test="${empty customerfee.businessdate}">
                                <input type="text" class="required" name="customerfee.businessdate" id="oa-businessdate-oaCustomerFeeHandlePage" value="<fmt:formatDate value='${today }' pattern='yyyy-MM-dd' type='date' dateStyle='long' />" data-clear-btn="true"/>
                            </c:when>
                            <c:otherwise>
                                <input type="text" class="required" name="customerfee.businessdate" id="oa-businessdate-oaCustomerFeeHandlePage" value="${customerfee.businessdate }" data-clear-btn="true"/>
                            </c:otherwise>
                        </c:choose>
                    </label>
                </div>

                <div class="ui-field-contain">
                    <a href="oa/customerfee/phone/oaCustomerFeeDetailPage.jsp" onclick="javascript:localStorage.setItem('index', -1)" class="ui-btn ui-btn-inline ui-icon-plus">添加</a>
                    <ul data-role="listview" id="oa-detail-oaCustomerFeeHandlePage" data-inset="false">
                        <li data-role="list-divider">费用明细</li>
                    </ul>
                </div>

                <div class="ui-field-contain">
                    <label>费用科目<span class="warnning">*</span>
                        <input type="text" class="required" name="customerfee.expensesort2" id="oa-expensesort2-oaCustomerFeeHandlePage" value="<c:if test='${not empty expensesort }'><c:out value='${customerfee.expensesort }'/>:<c:out value='${expensesort.name }'/></c:if>" data-clear-btn="true" readonly="readonly"/>
                        <input type="hidden" name="customerfee.expensesort" id="oa-expensesort-oaCustomerFeeHandlePage" value="${customerfee.expensesort }"/>
                    </label>
                </div>

                <div class="ui-field-contain">
                    <label>银行名称<span class="warnning">*</span>
                        <input type="text" class="required" name="customerfee.paybank2" id="oa-paybank2-oaCustomerFeeHandlePage" value="<c:if test='${not empty bank }'><c:out value='${customerfee.paybank }'/>:<c:out value='${bank.name }'/></c:if>" data-clear-btn="true" readonly="readonly"/>
                        <input type="hidden" name="customerfee.paybank" id="oa-paybank-oaCustomerFeeHandlePage" value="${customerfee.paybank }"/>
                    </label>
                </div>

                <div class="ui-field-contain">
                    <label>费用金额
                        <input type="number" name="customerfee.payamount" id="oa-payamount-oaCustomerFeeHandlePage" value="${customerfee.payamount }" readonly="readonly" data-clear-btn="false"/>
                    </label>
                </div>

                <div class="ui-field-contain">
                    <label>说明
                        <textarea name="customerfee.remark" id="oa-remark-oaCustomerFeeHandlePage" maxlength="150"><c:out value="${customerfee.remark }"/></textarea>
                    </label>
                </div>

            </div>
        </div>
    </form>
    <div id="oa-footer-oaCustomerFeeHandlePage" data-role="footer" data-position="fixed">
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
</div>
</body>
</html>
