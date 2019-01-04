<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib prefix="process" uri="/tag/process" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  	  <title>打印[货款支付申请单]</title>
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
                <th class="len80 left">供应商</th>
                <td class="len180"><span><c:out value="${supplier.name }"/></span></td>
                <th class="len80 left">收款银行</th>
                <td class="len180"><span><c:out value="${pay.collectionbank }"/></span></td>
                <th class="len80 left">银行账号</th>
                <td class="len180"><span><c:out value="${pay.collectionbankno }"/></span></td>
            </tr>
            <tr>
                <th class="left">付款金额</th>
                <td class=""><span precision="2"><c:out value="${pay.payamount }"/></span></td>
                <th class="left">大写金额</th>
                <td class=""><span><c:out value="${pay.upperpayamount }"/></span></td>
                <th class="left">付款日期</th>
                <td class=""><span><c:out value="${pay.paydate }"/></span></td>
            </tr>
            <tr>
                <th class="left">付款银行</th>
                <td class=""><span><c:out value="${bank.name }"/></span></td>
                <th class="left">到货金额</th>
                <td class=""><span precision="2"><c:out value="${pay.arrivalamount }"/></span></td>
                <th class="left">付款差额</th>
                <td class=""><span precision="2"><c:out value="${pay.paymargin }"/></span></td>
            </tr>
            <tr>
                <th class="left">到货日期</th>
                <td class=""><span><c:out value="${pay.arrivaldate }"/></span></td>
                <th class="left">发票金额</th>
                <td class=""><span precision="2"><c:out value="${pay.billamount }"/></span></td>
                <th class="left">收回费用</th>
                <td class=""><span precision="2"><c:out value="${pay.expenseamount }"/></span></td>
            </tr>
            <tr>
                <th class="left">抽单金额</th>
                <td class=""><span precision="2"><c:out value="${pay.writeoffamount }"/></span></td>
                <th class="left">抽单日期</th>
                <td class=""><span><c:out value="${pay.writeoffdate }"/></span></td>
                <th class="left">订单金额</th>
                <td class=""><span precision="2"><c:out value="${pay.orderamount }"/></span></td>
            </tr>
            <tr>
                <th class="left">预付金额</th>
                <td class=""><span precision="2"><c:out value="${pay.advanceamount }"/></span></td>
                <th class="left">库存金额</th>
                <td class=""><span precision="2"><c:out value="${pay.stockamount }"/></span></td>
                <th class="left">应收金额</th>
                <td class=""><span precision="2"><c:out value="${pay.receivableamount }"/></span></td>
            </tr>
            <tr>
                <th class="left">代垫金额</th>
                <td class=""><span precision="2"><c:out value="${pay.actingmatamount }"/></span></td>
                <th class="left">应付金额</th>
                <td class=""><span precision="2"><c:out value="${pay.payableamount }"/></span></td>
                <th class="left">合计占用</th>
                <td class=""><span precision="2"><c:out value="${pay.totalamount }"/></span></td>
            </tr>
            <tr>
                <th class="left">备注</th>
                <td class="" colspan="5"><span><c:out value="${pay.remark }"/></span></td>
            </tr>
        </table>
        <div style="">&nbsp;</div>
        <div class="loading comment">正在获取审批信息...</div>
        <script type="text/javascript">

        </script>
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