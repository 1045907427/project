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
      <script type="text/javascript" src="../js/jquery-1.8.3.js" charset="UTF-8"></script>
      <link rel="stylesheet" href="common/css/print.css" type="text/css"></link>
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
                <th class="len80 left">付款部门</th>
                <td class="len180"><span><c:out value="${payDept.name }"/></span></td>
                <th class="len80 right">付款人</th>
                <td class="len180"><span><c:out value="${payPersonnel.name }"/></span></td>
                <th class="len80 right">付款日期</th>
                <td class="len180"><span><c:out value="${loan.businessdate }"/></span></td>
            </tr>
            <tr>
                <th class="left">付款方式</th>
                <td class="">
                    <c:choose>
                        <c:when test="${loan.paytype eq 1}">
                            <span>现金</span>
                        </c:when>
                        <c:when test="${loan.paytype eq 2}">
                            <span>转账</span>
                        </c:when>
                        <c:otherwise></c:otherwise>
                    </c:choose>
                </td>
                <th class="right">付款金额</th>
                <td class=""><span precision="2"><c:out value="${loan.amount }"/></span></td>
                <th class="right">大写金额</th>
                <td class=""><span><c:out value="${loan.upamount }"/></span></td>
            </tr>
            <tr>
                <th class="left">付款银行</th>
                <td class=""><span><c:out value="${bank.name }"/></span></td>
                <th class="right">借款类型</th>
                <td class="">
                    <c:forEach items="${loantype }" var="item">
                        <c:if test="${item.code eq loan.loantype }"><span><c:out value="${item.codename }"/></span></c:if>
                    </c:forEach>
                </td>
                <th class="right">收款人</th>
                <td class=""><span><c:out value="${collectPersonnel.name }"/></span></td>
            </tr>
            <tr>
                <th class="left">备注</th>
                <td class="" colspan="5"><span><c:out value="${loan.remark }"/></span>
                </td>
            </tr>
        </table>
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