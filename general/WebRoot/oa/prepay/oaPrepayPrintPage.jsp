<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<jsp:useBean id="today" class="java.util.Date"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
      <title>打印</title>
      <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.8.3.js" charset="UTF-8"></script>
      <link rel="stylesheet" href="<%=request.getContextPath()%>/oa/common/css/print.css" type="text/css"></link>
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
        <table style="width: 100%; border: none;" class="noprint">
            <tr>
                <td align="center" style="width: 100%; border: none; border-bottom: 1px solid #DDD; padding: 5px;">
                    <input type="button" onclick="print()" value="　打印　"/>
                </td>
            </tr>
        </table>
        <table style="width: 100%; border: none;" class="process-info">
            <tr>
                <td style="width: 5%;border: none;">OA号：</td>
                <td style="width: 23%;border: none;"><u>&nbsp;<c:out value="${process.id }" />&nbsp;</u></td>
                <td style="width: 7%;border: none;">申请人：</td>
                <td style="width: 23%;border: none;"><u>&nbsp;<c:out value="${process.applyusername }" />&nbsp;</u></td>
                <td style="width: 9%;border: none;">申请时间：</td>
                <td style="width: 23%;border: none;"><u>&nbsp;<fmt:formatDate value="${process.applydate }" pattern="yyyy-MM-dd HH:mm:ss" type="date" dateStyle="full" />&nbsp;</u></td>
            </tr>
        </table>
        <div class="title"><c:out value="${process.definitionname }"/></div>
        <table>
            <tr>
                <th class="len100">付款部门</th>
                <td class="len180"><span><c:out value="${payDept.name }"/></span></td>
                <th class="len100">付款人</th>
                <td class="len180"><span><c:out value="${payPersonnel.name }"/></span></td>
                <th class="len80">付款日期</th>
                <td class="len180"><span><c:out value="${prepay.businessdate }"/></span></td>
            </tr>
            <tr>
                <th class="">付款方式</th>
                <td class="">
                    <c:choose>
                        <c:when test="${prepay.paytype eq 1}">
                            <span>现金</span>
                        </c:when>
                        <c:when test="${prepay.paytype eq 2}">
                            <span>转账</span>
                        </c:when>
                        <c:otherwise></c:otherwise>
                    </c:choose>
                </td>
                <th class="">付款金额</th>
                <td class=""><span precision="2"><c:out value="${prepay.amount }"/></span></td>
                <th class="">大写金额</th>
                <td class=""><span><c:out value="${prepay.upamount }"/></span></td>
            </tr>
            <tr>
                <th class="">付款银行</th>
                <td class=""><span><c:out value="${bank.name }"/></span></td>
                <th class="">借款类型</th>
                <td class="">
                    <c:forEach items="${loantype }" var="item">
                        <c:if test="${item.code eq prepay.loantype }"><span><c:out value="${item.codename }"/></span></c:if>
                    </c:forEach>
                </td>
                <th class="">收款人</th>
                <td class=""><span><c:out value="${prepay.collectusername }"/></span></td>
            </tr>
            <tr>
                <th class="">收汇人银行</th>
                <td class=""><span><c:out value="${prepay.collectbank }"/></span></td>
                <th class="">收汇人账号</th>
                <td class="" colspan="3"><span><c:out value="${prepay.collectbankno }"/></span></td>
            </tr>
            <tr>
                <th class="">备注</th>
                <td class="" colspan="5"><span><c:out value="${prepay.remark }"/></span>
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