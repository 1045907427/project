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
  <div class="limit-width align-center">
      <process:processHeader process="${process }" advice=""/>
      <table>
          <tr>
              <th class="left len100">客户编码</th>
              <td class="len180"><span><c:out value="${customer.customerid }"/></span></td>
              <th class="right len80">客户名称</th>
              <td class="len180"><span><c:out value="${customer.customername }"/></span></td>
              <th class="right len80">上级客户</th>
              <td class="len180"><span><c:out value="${pcustomer.name }"/></span></td>
          </tr>
          <tr>
              <th class="left">销售部门</th>
              <td class=""><span><c:out value="${salesdept.name }"/></span></td>
              <%--
              <th class="left">客户简称</th>
              <td class=""><span><c:out value="${customer.shortname }"/></span></td>
              --%>
              <th class="right">联系人</th>
              <td class=""><span><c:out value="${customer.contact }"/></span></td>
              <th class="right">联系电话</th>
              <td class=""><span><c:out value="${customer.mobile }"/></span></td>
          </tr>
          <tr>
              <th class="left">详细地址</th>
              <td class=""><span><c:out value="${customer.address }"/></span></td>
              <th class="right">注册资金</th>
              <td class=""><span precision="0"><c:out value="${customer.fund }"/></span></td>
              <th class="right">门店面积</th>
              <td class=""><span precision="0"><c:out value="${customer.storearea }"/></span></td>
          </tr>
          <tr>
              <th class="left">所属区域</th>
              <td class=""><span><c:out value="${salesArea.name }"/></span></td>
              <th class="right">所属分类</th>
              <td class=""><span><c:out value="${customerSort.name }"/></span></td>
              <th class="right">默认价格套</th>
              <td class="">
                  <c:forEach items="${priceList}" var="list">
                      <c:if test="${list.code == customer.pricesort}"><span><c:out value="${list.codename }"/></span></c:if>
                  </c:forEach>
              </td>
          </tr>
          <tr>
              <th class="left">默认客户业务员</th>
              <td class=""><span><c:out value="${salesUser.name }"/></span></td>
              <th class="right">收款人</th>
              <td class=""><span><c:out value="${payUser.name }"/></span></td>
              <th class="right">默认内勤</th>
              <td class=""><span><c:out value="${indoorUser.name }"/></span></td>
          </tr>
          <tr>
              <th class="left">促销分类</th>
              <td class="">
                  <c:forEach items="${promotionsortList }" var="list">
                      <c:if test="${list.code eq customer.promotionsort}"><span><c:out value="${list.codename }"/></span></c:if>
                  </c:forEach>
              </td>
              <th class="right">信用等级</th>
              <td class="">
                  <c:forEach items="${creditratingList }" var="list">
                      <c:if test="${list.code eq customer.creditrating}"><span><c:out value="${list.codename }"/></span></c:if>
                  </c:forEach>
              </td>
              <th class="right">信用额度</th>
              <td class=""><span precision="0"><c:out value="${customer.credit }"/></span></td>
          </tr>
          <tr>
              <th class="left">结算方式</th>
              <td class=""><span><c:out value="${settlement.name }"/></span></td>
              <th class="right">结算日</th>
              <td class="">
                  <c:forEach items="${dayList}" var="day">
                      <c:if test="${day eq customer.settleday}"><span><c:out value="${day }"/></span></c:if>
                  </c:forEach>
              </td>
              <th class="right">核销方式</th>
              <td class="">
                  <c:forEach items="${canceltypeList }" var="list">
                      <c:if test="${list.code eq customer.canceltype}"><span><c:out value="${list.codename }"/></span></c:if>
                  </c:forEach>
              </td>
          </tr>
          <tr>
              <th class="left">是否现款</th>
              <td class="">
                  <c:choose>
                      <c:when test="${customer.iscash eq 0}"><span>否</c:when>
                      <c:when test="${customer.iscash eq 1}"><span>是</c:when>
                  </c:choose>
              </td>
              <th class="right">是否账期</th>
              <td class="">
                  <c:choose>
                      <c:when test="${customer.islongterm eq 0}"><span>否</c:when>
                      <c:when test="${customer.islongterm eq 1}"><span>是</c:when>
                  </c:choose>
              </td>
              <td colspan="2">
                  <!--
                  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href="javascript:void(0);" id="work-allot-oacustomerViewPage" class="easyui-linkbutton" data-options="iconCls:'icon-reload'">分配品牌业务员</a>
                  -->
              </td>
          </tr>
          <tr>
              <th class="left">备注</th>
              <td colspan="5"><span><c:out value="${customer.remark }"/></span></td>
          </tr>
          <tr>
              <th colspan="6">谈判内容</th>
          </tr>
          <tr>
              <th>品牌编号</th>
              <th>品牌名称</th>
              <th>品牌商品数</th>
              <th>实际进场数</th>
              <th>阵列组数</th>
              <th>分摊费用</th>
          </tr>
          <c:choose>
              <c:when test="${empty list}">
                  <tr>
                      <td align="center" colspan="6"><span>无</span></td>
                  </tr>
              </c:when>
              <c:otherwise>
                  <c:forEach items="${list }" var="item" varStatus="status">
                      <tr>
                          <td><span><c:out value="${item.brandid }"/></span></td>
                          <td><span><c:out value="${item.brandname }"/></span></td>
                          <td><span><c:out value="${item.barcodenum }"/></span></td>
                          <td><span><c:out value="${item.realnum }"/></span></td>
                          <td><span><c:out value="${item.displaynum }"/></span></td>
                          <td align="right"><span precision="2"><c:out value="${item.cost }"/></span></td>
                      </tr>
                  </c:forEach>
              </c:otherwise>
          </c:choose>
          <tr>
              <th>客户要求</th>
              <td colspan="5"><span><c:out value="${customer.demand }"/></span></td>
          </tr>
          <tr>
              <th>谈判结果</th>
              <td colspan="5"><span><c:out value="${customer.talkresult }"/></span></td>
          </tr>
          <tr>
              <th>反馈情况</th>
              <td colspan="5"><span><c:out value="${customer.feedback }"/></span></td>
          </tr>
          <tr>
              <th>合同期限</th>
              <td colspan="5"><span><c:out value="${customer.pactdeadline }"/></span></td>
          </tr>
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
            //$(this).parent().css({'text-align': 'right'});
        });

    });
    -->
	</script>
  </body>
</html>