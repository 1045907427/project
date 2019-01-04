<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
    <title>报表</title>
    <%@include file="/phone/common/include.jsp"%>
</head>
<body>
<div data-role="page" id="activiti-main-workListPage1">
    <div data-role="header" data-position="fixed" data-tap-toggle="false">
        <h1>报表</h1>
        <a href="javascript:backMain();" class="allui-btn ui-corner- ui-btn-inline ui-btn-icon-left ui-icon-back" style="border: 0px; background: #E9E9E9;">返回</a>
        <%-- <a href="javascript:location.href=location.href;" class="ui-btn ui-corner-all ui-btn-inline ui-btn-icon-right ui-icon-refresh" style="border: 0px; background: #E9E9E9;">刷新</a> --%>
    </div>
    <div data-role="content">
        <security:authorize url="/phone/showSalesOrderTrackReportPage.do">
           <a href="phone/showSalesOrderTrackReportPage.do" class="ui-btn ui-corner-all" rel="external">订单追踪明细</a>
        </security:authorize>
        <security:authorize url="/phone/showBaseSalesReportPage.do">
        <a href="phone/showBaseSalesReportPage.do" class="ui-btn ui-corner-all" rel="external">销售情况统计</a>
        </security:authorize>
        <security:authorize url="/phone/showBaseFinanceDrawnPage.do">
        <a href="phone/showBaseFinanceDrawnPage.do" class="ui-btn ui-corner-all" rel="external">资金回笼统计</a>
        </security:authorize>
        <security:authorize url="/phone/showSalesDemandReportListPage.do">
        <a href="phone/showSalesDemandReportListPage.do" class="ui-btn ui-corner-all" rel="external">要货金额报表</a>
        </security:authorize>
        <security:authorize url="/phone/showSalesDemandBillReportPage.do">
            <a href="phone/showSalesDemandBillReportPage.do" class="ui-btn ui-corner-all" rel="external">单据追踪报表</a>
        </security:authorize>
        <security:authorize url="/phone/showSalesCustomerReportPage.do">
            <a href="phone/showSalesCustomerReportPage.do" class="ui-btn ui-corner-all" rel="external">分客户销售情况统计</a>
        </security:authorize>
        <security:authorize url="/phone/showGoodsOutReportPage.do">
            <a href="phone/showGoodsOutReportPage.do" class="ui-btn ui-corner-all" rel="external">缺货商品统计</a>
        </security:authorize>
        <security:authorize url="/phone/showBankAmountPage.do">
            <a href="phone/showBankAmountPage.do" class="ui-btn ui-corner-all" rel="external">银行账户余额列表</a>
        </security:authorize>
        <security:authorize url="/phone/showOrderGoodsReportPage.do">
            <a href="phone/showOrderGoodsReportPage.do" class="ui-btn ui-corner-all" rel="external">订货单报表</a>
        </security:authorize>
    </div>
</div>
</body>
</html>
