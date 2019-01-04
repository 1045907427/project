<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib prefix="process" uri="/tag/process" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
      <title>打印</title>
      <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.8.3.js" charset="UTF-8"></script>
      <link rel="stylesheet" href="<%=request.getContextPath()%>/oa/common/css/print.css" type="text/css"></link>
      <script type="text/javascript" src="<%=request.getContextPath()%>/oa/common/js/oaprint.js" charset="UTF-8"></script>
      <style type="text/css">
          table {
              border-collapse: collapse;
              border: 1px solid #000;
          }
          table th,td{
              border: 1px solid #000;
          }
      </style>
      <style type="text/css" media="print">
          .noprint {
              display: none;
          }
      </style>
  </head>
  <body>
    <div class="limit-width2 align-center">
        <process:processHeader process="${process }" advice=""/>
        <table>
            <tr>
                <th class="len80 left">已批OA号</th>
                <td class="len200" colspan="2"><span><c:out value="${customerpay.relateoaid }"/></span></td>
                <th class="len90 right">所属供应商</th>
                <td class="len180" colspan="2"><span><c:out value="${supplier.name }"/></span></td>
                <!--
                <th class="len80 right">所属部门</th>
                <td class="len180"><span><c:out value="${dept.name }"/></span></td>
                -->
                <th class="len80 right">客　　户</th>
                <td class="len180"><span><c:out value="${customer.name }"/></span></td>
            </tr>
            <tr>
                <th class="left">客户名称</th>
                <td class="" colspan="2"><span><c:out value="${customer2.name }"/></span></td>
                <th class="right">客户银行</th>
                <td class="" colspan="2"><span><c:out value="${customerpay.collectionbank }"/></span></td>
                <th class="right">客户帐号</th>
                <td class=""><span><c:out value="${customerpay.collectionbankno }"/></span></td>
            </tr>
            <tr>
                <th class="left">付款金额</th>
                <td class="" colspan="2"><span precision="2"><c:out value="${customerpay.payamount }"/></span></td>
                <th class="right">大写金额</th>
                <td class="" colspan="4"><span><c:out value="${customerpay.upperpayamount }"/></span></td>
            </tr>
            <tr>
                <th class="left">付款银行</th>
                <td class="" colspan="2"><span><c:out value="${bank.name }"/></span></td>
                <th class="right">付款用途</th>
                <td class="" colspan="2">
                    <c:forEach items="${paytype }" var="pay" varStatus="status">
                        <c:if test="${pay.code eq customerpay.payuse }"><c:out value="${pay.codename }"/></c:if>
                    </c:forEach>
                </td>
                <th class="right">费用科目</th>
                <td class=""><span><c:out value="${expensesSort.name }"/></span></td>
            </tr>
            <tr>
                <th class="left">发票种类</th>
                <td class="" colspan="2">
                    <c:forEach items="${invoicetype }" var="invoice">
                        <c:if test="${invoice.code eq customerpay.billtype}"><span><c:out value="${invoice.codename }"/></span></c:if>
                    </c:forEach>
                </td>
                <th class="right">到票时间</th>
                <td class="" colspan="2"><span><c:out value="${customerpay.billdate }"/></span></td>
                <th class="right">发票金额</th>
                <td class=""><span precision="2"><c:out value="${customerpay.billamount }"/></span></td>
            </tr>
            <tr>
                <th class="left">付款日期</th>
                <td class="" colspan="2"><span><c:out value="${customerpay.paydate }"/></span></td>
                <th class="right">摊销时间</th>
                <td class="" colspan="4">
                    <span><c:out value="${customerpay.sharebegindate }"/>～<span><c:out value="${customerpay.shareenddate }"/></span>
                </td>
            </tr>
            <tr>
                <th>说明</th>
                <td colspan="7"><span><c:out value="${customerpay.remark }"/></span></td>
            </tr>
            <tr>
                <th>品牌</th>
                <th>费用科目</th>
                <th>执行时间</th>
                <th>费用部门</th>
                <th>费用金额</th>
                <th>销售金额</th>
                <th>费比</th>
                <th>备注</th>
            </tr>
            <c:choose>
                <c:when test="${empty list}">
                    <tr><td colspan="8" align="center"><span>无</span></td></tr>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${list }" var="item" varStatus="status">
                        <tr>
                            <td><span><c:out value="${item.brandid }"/></span></td>
                            <td><span><c:out value="${item.expensesort }"/></span></td>
                            <td><span><c:out value="${item.executedate }"/></span></td>
                            <td><span><c:out value="${item.deptid }"/></span></td>
                            <td><span precision="2"><c:out value="${item.amount }"/></span></td>
                            <td><span precision="2"><c:out value="${item.salesamount }"/></span></td>
                            <td class="right">
                                <c:choose>
                                    <c:when test="${empty item.rate}"><span>-</span></c:when>
                                    <c:otherwise><span precision="2"><c:out value="${item.rate }"/></span>%</c:otherwise>
                                </c:choose>
                            </td>
                            <td><span><c:out value="${item.remark }"/></span></td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </table>
        <div style="">&nbsp;</div>
        <div class="loading comment">正在获取审批信息...</div>
	</div>
	<script type="text/javascript">
	<!--
    $(function() {

        $('span[precision]').each(function(index) {

            var text = $(this).text();
            var precision = $(this).attr('precision');

            if(text != undefined && text != '' && text != null) {

                text = parseFloat(text).toFixed(parseInt(precision));
            }

            $(this).text(text);
            $(this).parent().css({'text-align': 'right'});
        });

    });

	-->
	</script>
  </body>
</html>