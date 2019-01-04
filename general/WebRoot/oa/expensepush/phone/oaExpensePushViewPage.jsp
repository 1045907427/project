
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.hd.agent.common.util.BillGoodsNumDecimalLenUtils" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>费用冲差支付申请单(手机)</title>
    <%@include file="/phone/common/include.jsp"%>
</head>
<body>
<script type="text/javascript">
    var pushList =  $.parseJSON('${pushJSON }');
    localStorage.setItem("pushList", '${pushJSON }');

    // 显示主页面时，reload明细数据
    $(document).on('pageshow', '#main', function(e) {

        refreshPushList(e);

        numberPrecision();
    });

    function refreshPushList(e){

        var pushList = $.parseJSON(localStorage.getItem("pushList"));

        var html = new Array();

        for(var i in pushList) {

            var pushdetail = pushList[i];

            html.push('<li>');
            <%--html.push('<a href="oa/expensepush/phone/oaExpensePushDetailPage.jsp?oarequired=${param.oarequired }&noaccess=${param.noaccess }&dept=${param.dept }&buyprice=${param.buyprice }" onclick="javascript:localStorage.setItem(\'index\',' + i + ');" style="font-weight: 100;">');--%>
            html.push('<table>');

            html.push('<tr>');
            html.push('<th>折差品牌:</th>');
            html.push('<td>' + pushdetail.brandid + ':' + pushdetail.brandname + '</span></td>');
            html.push('</tr>');

            html.push('<tr>');
            html.push('<th>冲差类型:</th>');
            html.push('<td>' + pushdetail.pushtype + ':' + pushdetail.pushtypename + '</span></td>');
            html.push('</tr>');

            html.push('<tr>');
            html.push('<th>折让金额:</th>');
            html.push('<td><span precision="2">' + pushdetail.amount + '</span></td>');
            html.push('</tr>');

            <c:choose>
            <c:when test="${param.noaccess eq 1}">

            html.push('<tr>');
            html.push('<th>费用科目:</th>');
            html.push('<td><span>'  +  pushdetail.expensesortname + '</span></td>');
            html.push('</tr>');

            html.push('<tr>');
            html.push('<th>开始日期:</th>');
            html.push('<td><span>'  + pushdetail.startdate + '</span></td>');
            html.push('</tr>');

            html.push('<tr>');
            html.push('<th>结束日期:</th>');
            html.push('<td><span>'  + pushdetail.enddate + '</span></td>');
            html.push('</tr>');

            </c:when>
            <c:otherwise>

            html.push('<tr>');
            html.push('<th>费用部门:</th>');
            html.push('<td><span>'  + pushdetail.deptid + ':' + pushdetail.deptname + '</span></td>');
            html.push('</tr>');

            html.push('<tr>');
            html.push('<th>已批OA号:</th>');
            html.push('<td><span>' + pushdetail.oaid + '</span></td>');
            html.push('</tr>');

            </c:otherwise>
            </c:choose>

            if(pushdetail.goodsid) {

                html.push('<tr>');
                html.push('<th>商品:</th>');
                html.push('<td>' + pushdetail.goodsid + ':' + pushdetail.goodsname + '</span></td>');
                html.push('</tr>');

                html.push('<tr>');
                html.push('<th>数量:</th>');
                html.push('<td><span precision="<%=BillGoodsNumDecimalLenUtils.decimalLen %>">' + pushdetail.unitnum + '</span></td>');
                html.push('</tr>');

                html.push('<tr>');
                html.push('<th>原价:</th>');
                html.push('<td><span precision="2">' + pushdetail.oldprice + '</span></td>');
                html.push('</tr>');

                html.push('<tr>');
                html.push('<th>现价:</th>');
                html.push('<td><span precision="2">' + pushdetail.newprice + '</span></td>');
                html.push('</tr>');

                <c:if test="${param.buyprice eq '1'}">
                html.push('<tr>');
                html.push('<th>采购价:</th>');
                html.push('<td><span precision="2">' + pushdetail.buyprice + '</span></td>');
                html.push('</tr>');
                </c:if>
            }

            html.push('<tr>');
            html.push('<th>备注:</th>');
            html.push('<td><span>' + pushdetail.remark + '</span></td>');
            html.push('</tr>');

            html.push('</table>');
//            html.push('</a>');
            html.push('</li>');
        }

        $('#oa-pushdetail-oaExpensePushViewPage').find(':not(:eq(0))').remove();
        $('#oa-pushdetail-oaExpensePushViewPage').append(html.join(''));

        try {

            $('#oa-pushdetail-oaExpensePushViewPage').listview('refresh');
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

    /**
     * 提交表单
     * @param call
     * @param args
     * @returns {boolean}
     */
    function workFormSubmit(call, args) {

        // 验证
        $('#oa-form-oaExpensePushViewPage').validate({
            focusInvalid: true,
            debug: true
        });

        $("#oa-form-oaExpensePushViewPage").submit();
        var flag = $('#oa-form-oaExpensePushViewPage').validate().form();
        if(!flag) {

            return false;
        }

        $('#oa-form-oaExpensePushViewPage').validate({
            debug: false
        });

        $('#oa-pushList-oaExpensePushViewPage').val(JSON.stringify(pushList));

        $('#oa-form-oaExpensePushViewPage').submit(function(){

            $(this).ajaxSubmit({
                type: 'post',
                <c:choose>
                    <c:when test="${empty push or empty push.id}">
                        url:'oa/expensepush/addOaExpensePush.do',
                    </c:when>
                    <c:otherwise>
                        url:'oa/expensepush/editOaExpensePush.do',
                    </c:otherwise>
                </c:choose>
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

        {
            var ptype = '${param.ptype }';  // 冲差类型
            if(ptype) {
                var $ptype = $('#oa-ptype-oaExpensePushViewPage');
                var currentPtypeVal = $('#oa-ptype-oaExpensePushViewPage option[selected]').attr('value');
                var ptypeArr = ptype.split('');

                var newPtypeArray = new Array();
                for(var i in ptypeArr) {
                    newPtypeArray[ptypeArr[i]] = ptypeArr[i];
                }

                $ptype.html('');
                if(newPtypeArray[1]) {
                    $ptype.append('<option value="1">冲差</option>');
                }
                if(newPtypeArray[2]) {
                    $ptype.append('<option value="2">货补</option>');
                }
                if(currentPtypeVal) {
                    $('#oa-ptype-oaExpensePushViewPage').val(currentPtypeVal);
                }
                $ptype.selectmenu('refresh', true);
            }
        }

    }
</script>
<div data-role="page" id="main">
    <div data-role="header" data-position="fixed" data-tap-toggle="false">
        <h1>工作查看[${param.processid }]</h1>
        <a href="javascript:backPrev();" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a>
    </div>
    <form action="" id="oa-form-oaExpensePushViewPage" method="post">

        <input type="hidden" name="detaillist" id="oa-pushList-oaExpensePushViewPage" value=""/>
        <input type="hidden" name="push.id" id="oa-id-oaExpensePushViewPage" value="${param.id }"/>
        <input type="hidden" name="push.oaid" id="oaExpensePushViewPage" value="${param.processid }"/>

        <div class="ui-corner-all custom-corners">
            <div class="ui-body ui-body-a">
                <div class="ui-field-contain">
                    <label>客户&nbsp;<font color="#F00">*</font>
                        <c:choose>
                            <c:when test="${not empty push and not empty push.customerid and not empty customer}">
                                <input type="text" name="push.customer" id="oa-customer-oaExpensePushViewPage" value="${customer.id }:${customer.name }" readonly="readonly" data-clear-btn="false"/>
                            </c:when>
                            <c:otherwise>
                                <input type="text"  name="push.customer" id="oa-customer-oaExpensePushViewPage" value="" readonly="readonly" data-clear-btn="false"/>
                            </c:otherwise>
                        </c:choose>
                        <input type="hidden" class="required" name="push.customerid" id="oa-customerid-oaExpensePushViewPage" value="${customer.id }" readonly="readonly"/>
                    </label>
                </div>

                <div class="ui-field-contain">
                    <label class="select">冲差类型&nbsp;<font color="#F00">*</font>
                        <select sdata="${push.ptype}" name="push.ptype" id="oa-ptype-oaExpensePushViewPage">
                            <option value="1" <c:if test="${push.ptype eq '1'}">selected="selected"</c:if> >冲差</option>
                            <option value="2" <c:if test="${push.ptype eq '2'}">selected="selected"</c:if> >货补</option>
                        </select>
                    </label>
                </div>

                <div class="ui-field-contain">
                    <label>日期&nbsp;<font color="#F00">*</font>
                        <input type="text" name="push.businessdate" value="${push.businessdate}" id="oa-businessdate-oaExpensePushViewPage" readonly="readonly" />
                    </label>
                </div>

                <div class="ui-field-contain">
                    <ul data-role="listview" id="oa-pushdetail-oaExpensePushViewPage" data-inset="false">
                        <li data-role="list-divider">价格明细</li>
                    </ul>
                </div>
            </div>
        </div>
    </form>
    <c:if test="${param.type eq 'handle'}">
        <div id="oa-footer-oaExpensePushViewPage" data-role="footer" data-position="fixed">
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
