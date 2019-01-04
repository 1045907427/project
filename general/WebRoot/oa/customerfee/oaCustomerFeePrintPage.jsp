<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="/tag/process" prefix="process"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
      <title>打印</title>
      <script type="text/javascript" src="../../js/jquery-1.8.3.js" charset="UTF-8"></script>
      <link rel="stylesheet" href="../common/css/print.css" type="text/css"/>
      <script type="text/javascript" src="<%=request.getContextPath()%>/oa/common/js/oaprint.js" charset="UTF-8"></script>
      <style type="text/css">
          table {
              border-collapse: collapse;
              border: 1px solid #000;
          }
          table th,td {
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
  <c:set var="count" value="0"/>
    <div class="limit-width2 align-center">
        <process:processHeader process="${process }" advice=""/>
        <table>
            <tr>
                <td colspan="4"><span class="bold">客户名称：</span><span class="content">（<c:out value="${customerfee.customerid }"/>）<c:out value="${customer.name }"/></span></td>
                <td colspan="3" class="left"><span class="bold">申请日期：</span><span class="content"><c:out value="${customerfee.businessdate }"/></span></td>
            </tr>
            <tr>
                <th class="len240" rowspan="2">供应商</th>
                <th class="len90" rowspan="2">部门</th>
                <th class="len90" rowspan="2">品牌</th>
                <th class="len150" rowspan="2">申请事由</th>
                <th colspan="2">金额（元）</th>
                <th class="len90" rowspan="2">品牌责任人</th>
            </tr>
            <tr>
                <th class="len90">工厂投入</th>
                <th class="len80">自理</th>
            </tr>
            <c:forEach items="${list }" var="item" varStatus="status">
                <c:set var="count" value="${count + 1}"/>
                <tr>
                    <td><span class="content"><c:out value="${item.suppliername }"/></span></td>
                    <td><span class="content"><c:out value="${item.deptname }"/></span></td>
                    <td><span class="content"><c:out value="${item.brandname }"/></span></td>
                    <td><span class="content"><c:out value="${item.reason }"/></span></td>
                    <td class="right"><span precision="2" class="content"><c:out value="${item.factoryamount }"/></span></td>
                    <td class="right"><span precision="2" class="content"><c:out value="${item.selfamount }"/></span></td>
                    <td><span class="content"><c:out value="${item.branduser }"/></span></td>
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
                    <td></td>
                </tr>
            </c:forEach>
            <tr>
                <td><span class="bold">费用科目：</span><span class="content"><c:out value="${expensesSort.thisname}"/></span></td>
                <td style="border-right: 0px;"><span class="bold">银行名称：</span></td>
                <td colspan="2" style="border-left: 0px;"><span class="content"><c:out value="${bank.name}"/></span></td>
                <td colspan="1" style="border-left: 0px; border-right: 0px;"><span class="bold">费用金额：</span></td>
                <td colspan="2" style="border-left: 0px;"><span precision="2" class="content"><c:out value="${customerfee.payamount }"/></span></td>
            </tr>
            <tr>
                <td colspan="2">分公司审核1</td>
                <td colspan="2">分公司审核2</td>
                <td colspan="3">分公司审核3</td>
            </tr>
            <tr>
                <td colspan="2">申请人：<c:out value="${comments.apply}"/></td>
                <td colspan="2">财务审核：<c:out value="${comments.financialManager}"/></td>
                <td colspan="3">会计审核：<c:out value="${comments.accountant}"/></td>
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
            $(this).parent().css({'text-align': 'right'});
        });

    });

	-->
	</script>
  </body>
</html>