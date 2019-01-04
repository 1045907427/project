<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:useBean id="today" class="java.util.Date"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>分销规则查看</title>
    <jsp:include page="/include.jsp"/>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north'">
        <div id="sales-div-distributionRuleEditPage" style="padding:0px; height:auto">
            <form id="sales-form-distributionRuleEditPage" action="basefiles/distribution/editDistributionRule.do" method="post">
                <input type="hidden" id="sales-customerid-distributionRuleEditPage" name="distributionRule.customerid"/>
                <input type="hidden" id="sales-pcustomerid-distributionRuleEditPage" name="distributionRule.pcustomerid"/>
                <input type="hidden" id="sales-customersort-distributionRuleEditPage" name="distributionRule.customersort"/>
                <input type="hidden" id="sales-promotionsort-distributionRuleEditPage" name="distributionRule.promotionsort"/>
                <input type="hidden" id="sales-salesarea-distributionRuleEditPage" name="distributionRule.salesarea"/>
                <input type="hidden" id="sales-creditrating-distributionRuleEditPage" name="distributionRule.creditrating"/>
                <input type="hidden" id="sales-canceltype-distributionRuleEditPage" name="distributionRule.canceltype"/>
                <input type="hidden" id="sales-detaillist-distributionRuleEditPage" name="detaillist"/>
                <table>
                    <tr>
                        <td class="len50 left">编　　号：</td>
                        <td class="left" style="width: 220px;"><input type="text" class="len150 easyui-validatebox" id="sales-id-distributionRuleEditPage" name="distributionRule.id" autocomplete="off" readonly="readonly" value="${distributionRule.id }"/></td>
                        <td class="len50 left">业务日期：</td>
                        <td class="left" style="width: 220px;"><input type="text" class="len150 easyui-validatebox Wdate" id="sales-businessdate-distributionRuleEditPage" name="distributionRule.businessdate" autocomplete="off" value="${distributionRule.businessdate }" readonly="readonly"/></td>
                        <td class="len50 left">状　　态：</td>
                        <td class="left">
                            <select id="sales-state-distributionRuleListPage" name="distributionRule.state" class="len150" disabled="disabled">
                                <option></option>
                                <option value="0" <c:if test="${distributionRule.state eq '0' }">selected="selected"</c:if>>禁用</option>
                                <option value="1" <c:if test="${distributionRule.state eq '1' }">selected="selected"</c:if>>启用</option>
                                <option value="2" <c:if test="${distributionRule.state eq '2' }">selected="selected"</c:if>>保存</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>客 户 群：</td>
                        <td>
                            <select class="len150" id="sales-customertype-distributionRuleEditPage" name="distributionRule.customertype" disabled="disabled">
                                <option value="1" <c:if test="${distributionRule.customertype eq '1' }">selected="selected"</c:if>>单一客户</option>
                                <option value="2" <c:if test="${distributionRule.customertype eq '2' }">selected="selected"</c:if>>总店</option>
                                <option value="5" <c:if test="${distributionRule.customertype eq '5' }">selected="selected"</c:if>>销售区域</option>
                                <option value="3" <c:if test="${distributionRule.customertype eq '3' }">selected="selected"</c:if>>客户分类</option>
                                <option value="4" <c:if test="${distributionRule.customertype eq '4' }">selected="selected"</c:if>>促销分类</option>
                                <option value="6" <c:if test="${distributionRule.customertype eq '6' }">selected="selected"</c:if>>信用等级</option>
                                <option value="7" <c:if test="${distributionRule.customertype eq '7' }">selected="selected"</c:if>>核销方式</option>
                            </select>
                        </td>
                        <td>客户群名称：</td>
                        <td id="sales-widgetBox-distributionRuleEditPage">
                            <c:choose>
                                <c:when test="${not empty distributionRule.customerid}">
                                    <input type="text" class="len150" id="sales-widget-distributionRuleEditPage" name="widget" autocomplete="off" value="${distributionRule.customerid }" disabled="disabled"/>
                                </c:when>
                                <c:when test="${not empty distributionRule.pcustomerid}">
                                    <input type="text" class="len150" id="sales-widget-distributionRuleEditPage" name="widget" autocomplete="off" value="${distributionRule.pcustomerid }" disabled="disabled"/>
                                </c:when>
                                <c:when test="${not empty distributionRule.customersort}">
                                    <input type="text" class="len150" id="sales-widget-distributionRuleEditPage" name="widget" autocomplete="off" value="${distributionRule.customersort }" disabled="disabled"/>
                                </c:when>
                                <c:when test="${not empty distributionRule.promotionsort}">
                                    <input type="text" class="len150" id="sales-widget-distributionRuleEditPage" name="widget" autocomplete="off" value="${distributionRule.promotionsort }" disabled="disabled"/>
                                </c:when>
                                <c:when test="${not empty distributionRule.salesarea}">
                                    <input type="text" class="len150" id="sales-widget-distributionRuleEditPage" name="widget" autocomplete="off" value="${distributionRule.salesarea }" disabled="disabled"/>
                                </c:when>
                                <c:when test="${not empty distributionRule.creditrating}">
                                    <input type="text" class="len150" id="sales-widget-distributionRuleEditPage" name="widget" autocomplete="off" value="${distributionRule.creditrating }" disabled="disabled"/>
                                </c:when>
                                <c:when test="${not empty distributionRule.canceltype}">
                                    <input type="text" class="len150" id="sales-widget-distributionRuleEditPage" name="widget" autocomplete="off" value="${distributionRule.canceltype }" disabled="disabled"/>
                                </c:when>
                                <c:otherwise>
                                    <input type="text" class="len150" id="sales-widget-distributionRuleEditPage" name="widget" autocomplete="off" disabled="disabled"/>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>商品规则：</td>
                        <td>
                            <select id="sales-goodsruletype-distributionRuleEditPage" name="distributionRule.goodsruletype" class="len150" disabled="disabled">
                                <option value="1" <c:if test="${distributionRule.goodsruletype eq '1' }">selected="selected"</c:if>>按商品</option>
                                <option value="2" <c:if test="${distributionRule.goodsruletype eq '2' }">selected="selected"</c:if>>按品牌</option>
                                <option value="3" <c:if test="${distributionRule.goodsruletype eq '3' }">selected="selected"</c:if>>按商品分类</option>
                                <option value="4" <c:if test="${distributionRule.goodsruletype eq '4' }">selected="selected"</c:if>>按商品类型</option>
                                <option value="5" <c:if test="${distributionRule.goodsruletype eq '5' }">selected="selected"</c:if>>按供应商</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>是否可购：</td>
                        <td>
                            <select id="sales-canbuy-distributionRuleAddPage" name="distributionRule.canbuy" class="len150">
                                <option value="1" <c:if test="${distributionRule.canbuy eq '1' }">selected="selected"</c:if>>是</option>
                                <option value="0" <c:if test="${distributionRule.canbuy eq '0' }">selected="selected"</c:if>>否</option>
                            </select>
                        </td>
                        <td>备注：</td>
                        <td colspan="3">
                            <input type="text" id="sales-remark-distributionRuleEditPage" name="distributionRule.remark" value="<c:out value='${distributionRule.remark }'/>" style="width: 438px;" autocomplete="off" readonly="readonly"/>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <div data-options="region:'center'">
        <table id="sales-datagrid-distributionRuleEditPage" data-options="border:false"></table>
    </div>
</div>
<script type="text/javascript">

    <!--

    var $state = $('#sales-state-distributionRuleEditPage');
    var $customertype = $('#sales-customertype-distributionRuleEditPage');
    var $adduserid = $('#sales-adduserid-distributionRuleEditPage');
    var $datagrid = $('#sales-datagrid-distributionRuleEditPage');

    var $customerid = $('#sales-customerid-distributionRuleEditPage');
    var $pcustomerid = $('#sales-pcustomerid-distributionRuleEditPage');
    var $customersort = $('#sales-customersort-distributionRuleEditPage');
    var $promotionsort = $('#sales-promotionsort-distributionRuleEditPage');
    var $salesarea = $('#sales-salesarea-distributionRuleEditPage');
    var $creditrating = $('#sales-creditrating-distributionRuleEditPage');
    var $canceltype = $('#sales-canceltype-distributionRuleEditPage');

    var $widget = $('#sales-widget-distributionRuleEditPage');
    var $goodsruletype = $('#sales-goodsruletype-distributionRuleEditPage');
    var $widgetBox = $('#sales-widgetBox-distributionRuleEditPage');
    var $detaillist = $('#sales-detaillist-distributionRuleEditPage');
    var $form = $('#sales-form-distributionRuleEditPage');

    $(function () {

    });

    -->

</script>
</body>
</html>
