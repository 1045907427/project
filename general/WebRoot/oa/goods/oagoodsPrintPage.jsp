<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.hd.agent.common.util.BillGoodsNumDecimalLenUtils" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
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
          *{
              font-size: 10.5pt;
          }
      </style>
      <style type="text/css" media="print">
          .noprint {
              display: none;
          }
      </style>
  </head>
  <body>
    <div class="limit-width3 align-center">
        <process:processHeader process="${process }" advice=""/>
        <table>
            <tr>
                <th class="" colspan="1">供应商</th>
                <td class="" colspan="4"><span><c:out value="${supplier.name }"/></span></td>
                <th class="" colspan="2">进场商家数</th>
                <td class="" colspan="<fmt:formatNumber type="number" value="${fn:length(pricelist) - 1}" maxFractionDigits="0"/>"><span precision="0"><c:out value="${goods.customernum }"/></span></td>
                <th class="" colspan="2">进场费用</th>
                <td class="" colspan="3"><span precision="2"><c:out value="${goods.costamount }"/></span></td>
            </tr>
            <tr>
                <th class="" colspan="1">备注</th>
                <td colspan="<fmt:formatNumber type="number" value="${fn:length(pricelist) + 10}" maxFractionDigits="0"/>"><span><c:out value="${goods.remark }"/></span></td>
            </tr>
            <tr>
                <th colspan="<fmt:formatNumber type="number" value="${fn:length(pricelist) + 11}" maxFractionDigits="0"/>">商品明细清单</th>
            </tr>
            <tr>
                <th class="len75" rowspan="2">商品编码</th>
                <th class="len90" rowspan="2">商品名称</th>
                <th class="len50" rowspan="2">品牌</th>
                <th class="len100" rowspan="2">条形码</th>
                <!-- <th class="len100">箱装条形码</th> -->
                <!-- <th class="len50">商品分类</th> -->
                <th class="len50" rowspan="2">单位</th>
                <!-- <th class="len50">辅单位</th> -->
                <th class="len50" rowspan="2">箱装量</th>
                <th class="len50" rowspan="2">箱重</th>
                <th class="len50" rowspan="2">箱体积</th>
                <th class="len50" rowspan="2">仓库</th>
                <th class="len50" rowspan="2">最高采购价</th>
                <!-- <th class="len50">基准销售价</th> -->
                <!-- <th class="len50">税种</th> -->
                <c:forEach items="${pricelist}" var="price" varStatus="status">
                    <th class="len50"><span><c:out value="${price.codename }"/></span></th>
                    <!--
                    <th class="len50"><span><c:out value="${price.codename }"/><br/>毛利率</span></th>
                    -->
                </c:forEach>
                <th class="len40" rowspan="2">备注</th>
                <tr>
                    <c:forEach items="${pricelist}" var="price" varStatus="status">
                        <th class=""><span>毛利率</span></th>
                    </c:forEach>
                </tr>
            <c:choose>
                <c:when test="${empty list}">
                    <td colspan="<fmt:formatNumber type="number" value="${fn:length(pricelist) + 11}" maxFractionDigits="0"/>" align="center"><span>无</span></td>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${list }" var="item" varStatus="status">
                        <tr>
                            <td rowspan="2"><span><c:out value="${item.goodsid }"/></span></td>
                            <td rowspan="2"><span><c:out value="${item.goodsname }"/></span></td>
                            <td rowspan="2"><span><c:out value="${item.brandid }"/></span></td>
                            <td rowspan="2"><span><c:out value="${item.barcode }"/></span></td>
                            <!-- <td><span><c:out value="${item.boxbarcode }"/></span></td> -->
                            <!-- <td><span><c:out value="${item.goodssort }"/></span></td> -->
                            <td rowspan="2"><span><c:out value="${item.unitname }"/></span></td>
                            <!-- <td><span><c:out value="${item.auxunitname }"/></span></td> -->
                            <td rowspan="2"><span precision="<%=BillGoodsNumDecimalLenUtils.decimalLen %>"><c:out value="${item.boxnum }"/></span></td>
                            <td rowspan="2"><span precision="6"><c:out value="${item.totalweight }"/></span></td>
                            <td rowspan="2"><span precision="6"><c:out value="${item.totalvolume }"/></span></td>
                            <td rowspan="2"><span><c:out value="${item.storageid }"/></span></td>
                            <td rowspan="2"><span precision="6"><c:out value="${item.buytaxprice }"/></span></td>
                            <!-- <td><span precision="2"><c:out value="${item.basesaleprice }"/></span></td> -->
                            <!-- <td><span><c:out value="${item.taxtype }"/></span></td> -->
                            <c:choose>
                                <c:when test="${fn:length(pricelist) ge 1}">
                                    <td><span precision="2"><c:out value="${item.price1 }"/></span></td>
                                    <!--
                                    <td><span profitrate="${item.price1 },${item.costaccountprice },${item.buytaxprice }">-</span></td>
                                    -->
                                </c:when>
                            </c:choose>
                            <c:choose>
                                <c:when test="${fn:length(pricelist) ge 2}">
                                    <td><span precision="6"><c:out value="${item.price2 }"/></span></td>
                            <!--
                                    <td><span profitrate="${item.price2 },${item.costaccountprice },${item.buytaxprice }">-</span></td>
                                    -->
                                </c:when>
                            </c:choose>
                            <c:if test="${fn:length(pricelist) gt 2}">
                                <c:choose>
                                    <c:when test="${fn:length(pricelist) ge 3}">
                                        <td><span precision="6"><c:out value="${item.price3 }"/></span></td>
                                    <!--
                                        <td><span profitrate="${item.price3 },${item.costaccountprice },${item.buytaxprice }">-</span></td>
                                    -->
                                    </c:when>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${fn:length(pricelist) ge 4}">
                                        <td><span precision="6"><c:out value="${item.price4 }"/></span></td>
                                    <!--
                                        <td><span profitrate="${item.price4 },${item.costaccountprice },${item.buytaxprice }">-</span></td>
                                    -->
                                    </c:when>
                                </c:choose>
                            </c:if>
                            <c:if test="${fn:length(pricelist) gt 4}">
                                <c:choose>
                                    <c:when test="${fn:length(pricelist) ge 5}">
                                        <td><span precision="6"><c:out value="${item.price5 }"/></span></td>
                                    <!--
                                        <td><span profitrate="${item.price5 },${item.costaccountprice },${item.buytaxprice }">-</span></td>
                                    -->
                                    </c:when>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${fn:length(pricelist) ge 6}">
                                        <td><span precision="6"><c:out value="${item.price6 }"/></span></td>
                                    <!--
                                        <td><span profitrate="${item.price6 },${item.costaccountprice },${item.buytaxprice }">-</span></td>
                                    -->
                                    </c:when>
                                </c:choose>
                            </c:if>
                            <c:if test="${fn:length(pricelist) gt 6}">
                                <c:choose>
                                    <c:when test="${fn:length(pricelist) ge 7}">
                                        <td><span precision="6"><c:out value="${item.price7 }"/></span></td>
                                    <!--
                                        <td><span profitrate="${item.price7 },${item.costaccountprice },${item.buytaxprice }">-</span></td>
                                    -->
                                    </c:when>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${fn:length(pricelist) ge 8}">
                                        <td><span precision="6"><c:out value="${item.price8 }"/></span></td>
                                    <!--
                                        <td><span profitrate="${item.price8 },${item.costaccountprice },${item.buytaxprice }">-</span></td>
                                        -->
                                    </c:when>
                                </c:choose>
                            </c:if>
                            <td rowspan="2"><span><c:out value="${item.remark }"/></span></td>
                        </tr>
                        <tr>
                            <c:choose>
                                <c:when test="${fn:length(pricelist) ge 1}">
                                    <td><span profitrate="${item.price1 },${item.costaccountprice },${item.buytaxprice }">-</span></td>
                                </c:when>
                            </c:choose>
                            <c:choose>
                                <c:when test="${fn:length(pricelist) ge 2}">
                                    <td><span profitrate="${item.price2 },${item.costaccountprice },${item.buytaxprice }">-</span></td>
                                </c:when>
                            </c:choose>
                            <c:if test="${fn:length(pricelist) gt 2}">
                                <c:choose>
                                    <c:when test="${fn:length(pricelist) ge 3}">
                                        <td><span profitrate="${item.price3 },${item.costaccountprice },${item.buytaxprice }">-</span></td>
                                    </c:when>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${fn:length(pricelist) ge 4}">
                                        <td><span profitrate="${item.price4 },${item.costaccountprice },${item.buytaxprice }">-</span></td>
                                    </c:when>
                                </c:choose>
                            </c:if>
                            <c:if test="${fn:length(pricelist) gt 4}">
                                <c:choose>
                                    <c:when test="${fn:length(pricelist) ge 5}">
                                        <td><span profitrate="${item.price5 },${item.costaccountprice },${item.buytaxprice }">-</span></td>
                                    </c:when>
                                </c:choose>
                                <c:choose>
                                    <c:when test="${fn:length(pricelist) ge 6}">
                                        <td><span profitrate="${item.price6 },${item.costaccountprice },${item.buytaxprice }">-</span></td>
                                    </c:when>
                                </c:choose>
                            </c:if>
                            <c:if test="${fn:length(pricelist) gt 6}">
                            <c:choose>
                                <c:when test="${fn:length(pricelist) ge 7}">
                                    <td><span profitrate="${item.price7 },${item.costaccountprice },${item.buytaxprice }">-</span></td>
                                </c:when>
                            </c:choose>
                            <c:choose>
                                <c:when test="${fn:length(pricelist) ge 8}">
                                    <td><span profitrate="${item.price8 },${item.costaccountprice },${item.buytaxprice }">-</span></td>
                                </c:when>
                            </c:choose>
                            </c:if>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
        </table>
        <div style="">&nbsp;</div>
        <div class="loading comment">正在获取审批信息...</div>
  	</div>
	<script type="text/javascript">

	$(function(){

        showComment();

        $('span[precision]').each(function(index) {

            var text = $(this).text();
            var precision = $(this).attr('precision');

            if(text != undefined && text != '' && text != null) {

                text = parseFloat(text).toFixed(parseInt(precision));
            }

            $(this).text(text);
            $(this).parent().css({'text-align': 'right'});
        });

        $('span[profitrate]').each(function(index) {

            var prices = $(this).attr('profitrate').split(',');
            var price = prices[0];
            var cost1 = prices[1];
            var cost2 = prices[2];

            if(cost1 == '' || parseInt(cost1) == 0) {

                cost1 = cost2;
            }

            var rate = ((parseFloat(price) - parseFloat(cost1)) * 100 / parseFloat(price)).toFixed(2);

            $(this).text(rate + '%');
            $(this).parent().css({'text-align': 'right'});
        });
	});
	</script>
  </body>
</html>