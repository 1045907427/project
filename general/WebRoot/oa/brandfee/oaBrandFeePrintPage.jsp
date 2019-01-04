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
                <td><span class="bold">供应商</span></td>
                <td colspan="3"><span class="content"><c:out value="${supplier.name }"/></span></td>
                <td><span class="bold">供应商确认</span></td>
                <td><span class="content" ><c:out value="${brandfee.supplierconfirm }"/></span></td>
            </tr>
            <tr>
                <td class="len90"><span class="bold">部门</span></td>
                <td class="len160"><span class="content"><c:out value="${dept.name }"/></span></td>
                <td class="len90"><span class="bold">品牌</span></td>
                <td class="len160"><span class="content"><c:out value="${brand.name }"/></span></td>
                <td class="len110"><span class="bold">申请日期</span></td>
                <td class="len190"><span class="content"><c:out value="${brandfee.businessdate }"/></span></td>
            </tr>
            <tr>
                <td colspan="2"><span class="bold">申请事由</span></td>
                <td><span class="bold">工厂投入</span></td>
                <td colspan="2"><span class="bold">客户名称</span></td>
                <td><span class="bold">备注</span></td>
            </tr>
            <c:forEach items="${list }" var="item" varStatus="status">
                <c:set var="count" value="${count + 1}"/>
                <tr>
                    <td colspan="2"><span class="content"><c:out value="${item.reason }"/></span></td>
                    <td><span precision="2" class="content"><c:out value="${item.factoryamount }"/></span></td>
                    <td colspan="2"><span class="content">(<c:out value="${item.customerid }"/>)<c:out value="${item.customername }"/></span></td>
                    <td><span class="content"><c:out value="${item.remark }"/></span></td>
                </tr>
            </c:forEach>
            <c:forEach begin="${count }" end="4" varStatus="status" step="1">
                <tr>
                    <td colspan="2"></td>
                    <td></td>
                    <td colspan="2"></td>
                    <td></td>
                </tr>
            </c:forEach>
            <tr>
                <td colspan="2"><span class="bold">费用科目：</span><span class="content"><c:out value="${expensesSort.thisname}"/></span></td>
                <td style="border-right: 0px;"><span class="bold">费用金额：</span></td>
                <td colspan="1" style="border-left: 0px;"><span precision="2" class="content"><fmt:formatNumber value="${brandfee.payamount + 0.00001}" pattern="#0.00"/></span></td>
                <td colspan="2"><span class="bold">付款银行：</span><span class="content"><c:out value="${bank.name}"/></span></td>
            </tr>
            <tr>
                <td colspan="2"><span class="bold">归还日期：</span><span class="content"><c:out value="${brandfee.returndate}"/></span></td>
                <td style="border-right: 0px;"><span class="bold">归还方式：</span></td>
                <td colspan="1" style="border-left: 0px;"><span class="content"><c:out value="${brandfee.returnway}"/></span></td>
                <td colspan="2">经手人：<c:out value="${comments.apply}"/></td>
            </tr>
            <tr>
                <td colspan="2">分公司审核：<c:out value="${comments.branch}"/></td>
                <td colspan="2">财务审核：<c:out value="${comments.financial}"/></td>
                <td colspan="3">总经理审批：<c:out value="${comments.general}"/></td>
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