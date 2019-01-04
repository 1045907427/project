<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib prefix="process" uri="/tag/process" %>
<jsp:useBean id="today" class="java.util.Date" />
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
    <div class="limit-width2 align-center">
        <process:processHeader process="${process }" advice=""/>
        <table>
            <tr>
                <th class="len80 left">收款户名</th>
                <td class="len180"><span><c:out value="${pay.collectionname }"/></span></td>
                <th class="len80 left">开户银行</th>
                <td class="len230" colspan="3"><span><c:out value="${pay.collectionbank }"/></span></td>
                <th class="len80 left">银行账号</th>
                <td class="len180" colspan="2"><span><c:out value="${pay.collectionbankno }"/></span></td>
            </tr>
            <tr>
                <th class="left">付款金额</th>
                <td class=""><span precision="2"><c:out value="${pay.payamount }"/></span></td>
                <th class="left">大写金额</th>
                <td class="" colspan="3"><span><c:out value="${pay.upperpayamount }"/></span></td>
                <th class="left">付款银行</th>
                <td class="" colspan="2"><span><c:out value="${bank.name }"/></span></td>
            </tr>
            <tr>
                <th class="left">付款用途</th>
                <td class=""><span><c:out value="${pay.payuse }"/></span></td>
                <th class="left">费用科目</th>
                <td class="" colspan="3"><span><c:out value="${costsort.name }"/></span></td>
                <th class="left">所属部门</th>
                <td class="" colspan="2"><span><c:out value="${dept.name }"/></span></td>
            </tr>
            <tr>
                <th class="left">报销类型</th>
                <td class="">
                    <c:forEach items="${bxtype }" var="item">
                        <c:if test="${pay.applytype eq item.code}"><span><c:out value="${item.codename }"/></span></c:if>
                    </c:forEach>
                </td>
                <th class="left">发票金额</th>
                <td class="" colspan="3"><span precision="2"><c:out value="${pay.billamount }"/></span></td>
                <th class="left">分摊方式</th>
                <td class="" colspan="2">
                    <c:choose>
                        <c:when test="${pay.sharetype eq 1}">
                            <span>直接分摊</span>
                        </c:when>
                        <c:when test="${pay.sharetype eq 2}">
                            <span>内部分摊</span>
                        </c:when>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <th class="left">说&nbsp;&nbsp;明</th>
                <td class="" colspan="8"><span><c:out value="${pay.remark }"/></span></td>
            </tr>
            <tr>
                <th>物品编号</th>
                <th>物品名称</th>
                <th>领用部门</th>
                <th>单位</th>
                <th>数量</th>
                <th>单价</th>
                <th>金额</th>
                <th>是否固产</th>
                <th>有效期(年)</th>
            </tr>
            <c:forEach items="${list }" var="item" varStatus="status">
                <tr>
                    <td><span><c:out value="${item.itemid }"/></span></td>
                    <td><span><c:out value="${item.itemname }"/></span></td>
                    <td><span><c:out value="${item.applydetpid }"/></span></td>
                    <td><span><c:out value="${item.uintname }"/></span></td>
                    <td><span precision="0"><c:out value="${item.unitnum }"/></span></td>
                    <td><span precision="2"><c:out value="${item.price }"/></span></td>
                    <td><span precision="2"><c:out value="${item.amount }"/></span></td>
                    <td><span><c:out value="${item.isfix }"/></span></td>
                    <td><span><c:out value="${item.uselife }"/></span></td>
                </tr>
            </c:forEach>
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