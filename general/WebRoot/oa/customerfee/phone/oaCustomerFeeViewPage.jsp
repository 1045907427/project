<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/struts-tags" prefix="struts"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>客户费用申请单（账扣）(手机)</title>
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
//                html.push('<a href="oa/customerfee/phone/oaCustomerFeeDetailPage.jsp?" onclick="javascript:localStorage.setItem(\'index\',' + i + ');" style="font-weight: 100;">');
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
//                html.push('</a>');
                html.push('</li>');
            }

            $('#oa-detail-oaCustomerFeeViewPage').find(':not(:eq(0))').remove();
            $('#oa-detail-oaCustomerFeeViewPage').append(html.join(''));

            try {

                $('#oa-detail-oaCustomerFeeViewPage').listview('refresh');
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
            $('#oa-customerid2-oaCustomerFeeViewPage').on('click', function() {

                androidWidget({
                    type: 'widget',
                    referwid: 'RL_T_BASE_SALES_CUSTOMER_PARENT_2',
                    onSelect: 'selectCustomer'
                });
            }).on('change', function() {

                var v = $(this).val();
                $(this).attr('value', v);
                $('#oa-customerid-oaCustomerFeeViewPage').attr('value', (v || ':').split(':')[0]);
            });

            // 申请日期
            $(document).on('click', '#oa-businessdate-oaCustomerFeeViewPage', function() {

                androidDateWidget($('#oa-businessdate-oaCustomerFeeViewPage').val() || new Date().Format('yyyy-MM-dd'), 'yyyy-MM-dd', 'selectBusinessdate');
            });

            // 费用科目
            $('#oa-expensesort2-oaCustomerFeeViewPage').on('click', function() {

                androidWidget({
                    type: 'widget',
                    referwid: 'RT_T_BASE_FINANCE_EXPENSES_SORT',
                    onSelect: 'selectExpensesort'
                });
            }).on('change', function() {

                var v = $(this).val();
                $(this).attr('value', v);
                $('#oa-expensesort-oaCustomerFeeViewPage').attr('value', (v || ':').split(':')[0]);
            });

            // 付款银行
            $('#oa-paybank2-oaCustomerFeeViewPage').on('click', function() {

                androidWidget({
                    type: 'widget',
                    referwid: 'RL_T_BASE_FINANCE_BANK',
                    onSelect: 'selectBank'
                });
            }).on('change', function() {

                var v = $(this).val();
                $(this).attr('value', v);
                $('#oa-paybank-oaCustomerFeeViewPage').attr('value', (v || ':').split(':')[0]);
            });

        });

        /**
         * 提交表单
         * @param call
         * @param args
         */
        function workFormSubmit(call, args) {

            // 验证
            $('#oa-form-oaCustomerFeeViewPage').validate({
                focusInvalid: true,
                debug: true
            });

            $("#oa-form-oaCustomerFeeViewPage").submit();
            var flag = $('#oa-form-oaCustomerFeeViewPage').validate().form();
            if(!flag) {

                return false;
            }

            $('#oa-form-oaCustomerFeeViewPage').validate({
                debug: false
            });

            $('#oa-detaillist-oaCustomerFeeViewPage').val(localStorage.getItem('details'));

            $('#oa-form-oaCustomerFeeViewPage').submit(function(){

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
    <form action="" id="oa-form-oaCustomerFeeViewPage" method="post">
        <input type="hidden" name="customerfee.id" id="oa-id-oaCustomerFeeViewPage" value="${param.id }"/>
        <input type="hidden" name="customerfee.oaid" id="oa-oaid-oaCustomerFeeViewPage" value="${param.processid }"/>
        <input type="hidden" name="detaillist" id="oa-detaillist-oaCustomerFeeViewPage"/>
        <div class="ui-corner-all custom-corners">
            <div class="ui-bar ui-bar-b">
                <h1>费用信息</h1>
            </div>
            <div class="ui-body ui-body-a">
                <div class="ui-field-contain">
                    <label>客户
                        <input type="text" class="required" name="customerfee.customerid2" id="oa-customerid2-oaCustomerFeeViewPage" value="<c:if test='${not empty customer }'><c:out value='${customerfee.customerid }'/>:<c:out value='${customer.name }'/></c:if>" data-clear-btn="false" readonly="readonly"/>
                        <input type="hidden" name="customerfee.customerid" id="oa-customerid-oaCustomerFeeViewPage" value="${customerfee.customerid }"/>
                    </label>
                </div>
                <div class="ui-field-contain">
                    <label>申请日期
                        <input type="text" name="customerfee.businessdate" id="oa-businessdate-oaCustomerFeeViewPage" value="${customerfee.businessdate }" data-clear-btn="false" readonly="readonly"/>
                    </label>
                </div>

                <div class="ui-field-contain">
                    <ul data-role="listview" id="oa-detail-oaCustomerFeeViewPage" data-inset="false">
                        <li data-role="list-divider">费用明细</li>
                    </ul>
                </div>

                <div class="ui-field-contain">
                    <label>费用科目
                        <input type="text" class="required" name="customerfee.expensesort2" id="oa-expensesort2-oaCustomerFeeViewPage" value="<c:if test='${not empty expensesort }'><c:out value='${customerfee.expensesort }'/>:<c:out value='${expensesort.name }'/></c:if>" data-clear-btn="false" readonly="readonly"/>
                        <input type="hidden" name="customerfee.expensesort" id="oa-expensesort-oaCustomerFeeViewPage" value="${customerfee.expensesort }"/>
                    </label>
                </div>

                <div class="ui-field-contain">
                    <label>银行名称
                        <input type="text" name="customerfee.paybank2" id="oa-paybank2-oaCustomerFeeViewPage" value="<c:if test='${not empty bank }'><c:out value='${customerfee.paybank }'/>:<c:out value='${bank.name }'/></c:if>" data-clear-btn="false" readonly="readonly"/>
                        <input type="hidden" name="customerfee.paybank" id="oa-paybank-oaCustomerFeeViewPage" value="${customerfee.paybank }"/>
                    </label>
                </div>

                <div class="ui-field-contain">
                    <label>费用金额
                        <input type="number" name="customerfee.payamount" id="oa-payamount-oaCustomerFeeViewPage" value="${customerfee.payamount }" readonly="readonly" data-clear-btn="false"/>
                    </label>
                </div>

                <div class="ui-field-contain">
                    <label>说明
                        <textarea name="customerfee.remark" id="oa-remark-oaCustomerFeeViewPage" maxlength="150" readonly="readonly"><c:out value="${customerfee.remark }"/></textarea>
                    </label>
                </div>

            </div>
        </div>
    </form>
    <c:if test="${param.type eq 'handle'}">
        <div id="oa-footer-oaCustomerFeeViewPage" data-role="footer" data-position="fixed">
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
