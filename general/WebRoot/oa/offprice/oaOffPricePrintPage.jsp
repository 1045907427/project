<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
      <title>打印</title>
      <script type="text/javascript" src="../../js/jquery-1.8.3.js" charset="UTF-8"></script>
      <link rel="stylesheet" href="../common/css/print.css" type="text/css"></link>
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
                <td style="width: 15%;border: none;"><u>&nbsp;<c:out value="${process.id }" />&nbsp;</u></td>
                <td style="width: 7%;border: none;">申请人：</td>
                <td style="width: 15%;border: none;"><u>&nbsp;<c:out value="${process.applyusername }" />&nbsp;</u></td>
                <td style="width: 9%;border: none;">申请时间：</td>
                <td style="width: 23%;border: none;"><u>&nbsp;<fmt:formatDate value="${process.applydate }" pattern="yyyy-MM-dd HH:mm:ss" type="date" dateStyle="full" />&nbsp;</u></td>
                <td style="width: 10%;border: none;">当前节点：</td>
                <td style="width: 16%;border: none;">
                    <c:choose>
                        <c:when test="${empty process.taskname}">
                            <u><i>&nbsp;已完结&nbsp;</i></u>
                        </c:when>
                        <c:otherwise>
                            <u>&nbsp;<c:out value="${process.taskname }" />&nbsp;</u>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </table>
        <div class="title"><c:out value="${process.definitionname }"/></div>
        <table>
            <tr>
                <th class="">客　　户</th>
                <td style="" colspan="4"><span>(<c:out value="${price.customerid}" />)<c:out value="${customer.name}" /></span></td>
                <th class="">开单时间</th>
                <td class="" colspan="3">
                    <span><c:out value="${price.pricebegindate}" /></span>～<span><c:out value="${price.priceenddate}" /></span>
                </td>
            </tr>
            <tr>
                <th class="">销售内勤</th>
                <td class=""><span><c:out value="${personnel.name}" /></span></td>
                <th class="">业务员</th>
                <td class="" colspan="2"><span><c:out value="${businessPersonnel.name}" /></span></td>
                <th>档　　期</th>
                <td colspan="2"><c:out value="${price.schedule}" /></td>
            </tr>
            <tr>
                <th class="">说　　明</th>
                <td colspan="8"><span><c:out value="${price.remark}" /></span></td>
            </tr>
            <tr>
                <th class="len80">商品编号</th>
                <th class="len220">商品名称</th>
                <th class="len120">条形码</th>
                <%--<th class="len70">进价</th>--%>
                <th class="len70">原价</th>
                <th class="len70">特价</th>
                <th class="len80">毛利率</th>
                <th class="len100">本次订<br/>单数量</th>
                <th class="len110">说明</th>
            </tr>
            <c:choose>
                <c:when test="${empty list}">
                    <tr>
                        <td colspan="8" align="center"><span>无</span></td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${list }" var="item" varStatus="status">
                        <tr>
                            <td><span><c:out value="${item.goodsid}"/></span></td>
                            <td><span><c:out value="${item.goodsname}"/></span></td>
                            <td><span><c:out value="${item.barcode}"/></span></td>
                            <%--<td><span precision="2"><c:out value="${item.buyprice}"/></span></td>--%>
                            <td><span precision="2"><c:out value="${item.oldprice}"/></span></td>
                            <td><span precision="2"><c:out value="${item.offprice}"/></span></td>
                            <td><span precision="2"><c:out value="${item.profitrate}"/></span>%</td>
                            <td><span><c:out value="${item.ordernum}"/></span></td>
                            <td><span><c:out value="${item.remark}"/></span></td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
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