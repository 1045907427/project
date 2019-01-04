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
        <process:processHeader process="${process }" advice=""/>
        <table>
            <tr>
                <th class="len80 left">付款部门</th>
                <td class="len180"><span><c:out value="${payDept.name }"/></span></td>
                <th class="len80 left">付款日期</th>
                <td class="len180"><span><c:out value="${share.businessdate }"/></span></td>
                <th class="len80 left">费用科目</th>
                <td class="len180"><span><c:out value="${costsort.name }"/></span></td>
            </tr>
            <tr>
                <th class="left">金额</th>
                <td class=""><span precision="2"><c:out value="${share.amount }"/></span></td>
                <th class="left">大写金额</th>
                <td class=""><span><c:out value="${share.upamount }"/></span></td>
                <th class="left">收款部门</th>
                <td class=""><span><c:out value="${collectDept.name }"/></span></td>
            </tr>
            <tr>
                <th class="left">备注</th>
                <td class="" colspan="5"><span><c:out value="${share.remark }"/></span></td>
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