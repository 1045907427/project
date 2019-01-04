<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib prefix="process" uri="/tag/process" %>
<jsp:useBean id="today" class="java.util.Date"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
      <title>打印</title>
      <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.8.3.js" charset="UTF-8"></script>
      <link rel="stylesheet" href="<%=request.getContextPath()%>/oa/common/css/print.css" type="text/css"/>
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
      <c:set var="count" value="0"/>
      <c:set var="total" value="0"/>
      <process:processHeader process="${process}"/>
      <c:choose>
          <c:when test="${noaccess eq 1}">
              <table>
                  <tr>
                      <td colspan="3" class="left"><span class="bold">客户：</span><span class="content">（<c:out value="${push.customerid }"/>）<c:out value="${customer.name }"/></span></td>
                      <td colspan="2" class="left"><span class="bold">冲差类型：</span>
                          <c:choose>
                              <c:when test="${push.ptype eq 1}">
                                  <span class="content">冲差</span>
                              </c:when>
                              <c:when test="${push.ptype eq 2}">
                                  <span class="content">货补</span>
                              </c:when>
                          </c:choose></td>
                      <td colspan="1" class="left"><span class="bold">日期：</span><span class="content"><c:out value="${push.businessdate }"/></span></td>
                  </tr>
                  <tr>
                      <td class="center len160"><span class="bold">折差品牌</span></td>
                      <td class="center len130"><span class="bold">折让金额</span></td>
                      <td class="center len110"><span class="bold">费用科目</span></td>
                      <td class="center len150"><span class="bold">开始日期</span></td>
                      <td class="center len150"><span class="bold">结束日期</span></td>
                      <td class="center len190"><span class="bold">备注</span></td>
                  </tr>
                  <c:forEach items="${list }" var="item" varStatus="idx">
                      <c:set var="count" value="${count + 1}"/>
                      <c:set var="total" value="${total + item.amount}"/>
                      <tr>
                          <td><span><c:out value="${item.brandid }"/></span></td>
                          <td><span precision="2"><c:out value="${item.amount }"/></span></td>
                          <td><span><c:out value="${item.expensesort }"/></span></td>
                          <td><span><c:out value="${item.startdate }"/></span></td>
                          <td><span><c:out value="${item.enddate }"/></span></td>
                          <td><span><c:out value="${item.remark }"/></span></td>
                      </tr>
                  </c:forEach>
                  <c:forEach begin="${count }" end="4" varStatus="status" step="1">
                      <tr>
                          <td></td>
                          <td></td>
                          <td></td>
                          <td></td>
                          <td></td>
                          <td></td>
                      </tr>
                  </c:forEach>
                  <tr>
                      <th>合计</th>
                      <td><span precision="2"><c:out value="${total }"/></span></td>
                      <td colspan="4"></td>
                  </tr>
              </table>
          </c:when>
          <c:otherwise>
              <table>
                  <tr>
                      <th class="len80 left">客户</th>
                      <td class="len180"><span><c:out value="${customer.name }"/></span></td>
                      <th class="len80">冲差类型</th>
                      <td class="len180">
                          <c:choose>
                              <c:when test="${push.ptype eq 1}">
                                  <span>冲差</span>
                              </c:when>
                              <c:when test="${push.ptype eq 2}">
                                  <span>货补</span>
                              </c:when>
                          </c:choose>
                      </td>
                      <th class="len80">日期</th>
                      <td class="len180"><span><c:out value="${push.businessdate }"/></span></td>
                  </tr>
                  <tr>
                      <th colspan="2">折差品牌</th>
                      <th>折让金额</th>
                      <th>费用部门</th>
                      <th>已批OA号</th>
                      <th>备注</th>
                  </tr>
                  <c:forEach items="${list }" var="item" varStatus="idx">
                      <c:set var="count" value="${count + 1}"/>
                      <c:set var="total" value="${total + item.amount}"/>
                      <tr>
                          <td colspan="2"><span><c:out value="${item.brandid }"/></span></td>
                          <td><span precision="2"><c:out value="${item.amount }"/></span></td>
                          <td><span><c:out value="${item.deptid }"/></span></td>
                          <td><span><c:out value="${item.oaid }"/></span></td>
                          <td><span><c:out value="${item.remark }"/></span></td>
                      </tr>
                  </c:forEach>
                  <c:forEach begin="${count }" end="4" varStatus="status" step="1">
                      <tr>
                          <td colspan="2"></td>
                          <td></td>
                          <td></td>
                          <td></td>
                          <td></td>
                      </tr>
                  </c:forEach>
                  <tr>
                      <th colspan="2">合计</th>
                      <td><span precision="2"><c:out value="${total }"/></span></td>
                      <td colspan="3"></td>
                  </tr>
              </table>
              <div style="">&nbsp;</div>
              <div class="loading comment">正在获取审批信息...</div>
          </c:otherwise>
      </c:choose>

	</div>
	<script type="text/javascript">
	<!--
    $(function() {

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

    });

	-->
	</script>
  </body>
</html>