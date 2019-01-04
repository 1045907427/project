<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="today" class="java.util.Date"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>分销规则新增</title>
    <jsp:include page="/include.jsp"/>
</head>
<body>
<div class="easyui-layout" data-options="fit:true,border:false">
    <div data-options="region:'north'">
        <div id="sales-div-distributionRuleAddPage" style="padding:0px; height:auto">
            <form id="sales-form-distributionRuleAddPage" action="basefiles/distribution/addDistributionRule.do" method="post">
                <input type="hidden" id="sales-customerid-distributionRuleAddPage" name="distributionRule.customerid"/>
                <input type="hidden" id="sales-pcustomerid-distributionRuleAddPage" name="distributionRule.pcustomerid"/>
                <input type="hidden" id="sales-customersort-distributionRuleAddPage" name="distributionRule.customersort"/>
                <input type="hidden" id="sales-promotionsort-distributionRuleAddPage" name="distributionRule.promotionsort"/>
                <input type="hidden" id="sales-salesarea-distributionRuleAddPage" name="distributionRule.salesarea"/>
                <input type="hidden" id="sales-creditrating-distributionRuleAddPage" name="distributionRule.creditrating"/>
                <input type="hidden" id="sales-canceltype-distributionRuleAddPage" name="distributionRule.canceltype"/>
                <input type="hidden" id="sales-detaillist-distributionRuleAddPage" name="detaillist"/>
                <table>
                    <tr>
                        <td class="len50 left">编　　号：</td>
                        <td class="left" style="width: 220px;"><input type="text" class="len150 easyui-validatebox" id="sales-id-distributionRuleAddPage" name="distributionRule.id" autocomplete="off" readonly="readonly" value="${distributionRule.id }"/></td>
                        <td class="len50 left">业务日期：</td>
                        <td class="left" style="width: 220px;"><input type="text" class="len150 easyui-validatebox Wdate" id="sales-businessdate-distributionRuleAddPage" name="distributionRule.businessdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" autocomplete="off" value="<fmt:formatDate value="${today }" pattern="yyyy-MM-dd" type="date" dateStyle="long" />"/></td>
                        <td class="len50 left">状　　态：</td>
                        <td class="left">
                            <select id="sales-state-distributionRuleListPage" name="distributionRule.state" class="len150">
                                <option value="2">新增</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>客 户 群：</td>
                        <td>
                            <select class="len150" id="sales-customertype-distributionRuleAddPage" name="distributionRule.customertype">
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
                        <td id="sales-widgetBox-distributionRuleAddPage">
                            <input type="text" class="len150" id="sales-widget-distributionRuleAddPage" name="widget" autocomplete="off" disabled="disabled"/>
                        </td>
                        <td>商品规则：</td>
                        <td>
                            <select id="sales-goodsruletype-distributionRuleAddPage" name="distributionRule.goodsruletype" class="len150">
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
                            <input type="text" id="sales-remark-distributionRuleAddPage" name="distributionRule.remark" value="<c:out value='${distributionRule.remark }'/>" style="width: 438px;" autocomplete="off"/>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <div data-options="region:'center'">
        <table id="sales-datagrid-distributionRuleAddPage" data-options="border:false"></table>
    </div>
</div>
<script type="text/javascript">

    <!--

    var $state = $('#sales-state-distributionRuleAddPage');
    var $customertype = $('#sales-customertype-distributionRuleAddPage');
    var $adduserid = $('#sales-adduserid-distributionRuleAddPage');
    var $datagrid = $('#sales-datagrid-distributionRuleAddPage');

    var $customerid = $('#sales-customerid-distributionRuleAddPage');
    var $pcustomerid = $('#sales-pcustomerid-distributionRuleAddPage');
    var $customersort = $('#sales-customersort-distributionRuleAddPage');
    var $promotionsort = $('#sales-promotionsort-distributionRuleAddPage');
    var $salesarea = $('#sales-salesarea-distributionRuleAddPage');
    var $creditrating = $('#sales-creditrating-distributionRuleAddPage');
    var $canceltype = $('#sales-canceltype-distributionRuleAddPage');

    var $widget = $('#sales-widget-distributionRuleAddPage');
    var $goodsruletype = $('#sales-goodsruletype-distributionRuleAddPage');
    var $widgetBox = $('#sales-widgetBox-distributionRuleAddPage');
    var $detaillist = $('#sales-detaillist-distributionRuleAddPage');
    var $form = $('#sales-form-distributionRuleAddPage');

    $(function () {

    });

    -->

</script>
</body>
</html>
