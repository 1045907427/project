<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.hd.agent.common.util.BillGoodsNumDecimalLenUtils" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="/tag/process" prefix="process"%>
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
    <div class="limit-width2 align-center" style="width: 706px;">
        <process:processHeader process="${process }" advice="" width="780px"/>
        <table>
            <tr>
                <td style="font-weight: 900; height: 40px; border-top: double 3px #000;" colspan="8">第一确认：工厂</td>
            </tr>
            <tr>
                <th>供应商</th>
                <td colspan="2"><span><c:out value="${supplier.name }"/></span></td>
                <th>计划时间段</th>
                <td colspan="2"><span><c:out value="${access.planbegindate }"/>～<span><c:out value="${access.planenddate }"/></span></td>
                <th>确认单号</th>
                <td><span><c:out value="${access.confirmid }"/></span></td>
            </tr>
            <tr>
                <th class="">申请通路费</th>
                <td class="" colspan="2"><span><c:out value="${expensesSort.name }"/></span></td>
                <th class="">申请特价</th>
                <td colspan="2">
                    <c:choose>
                        <c:when test="${access.pricetype eq 1}">
                            <span>补差特价</span>
                        </c:when>
                        <c:when test="${access.pricetype eq 2}">
                            <span>降价特价</span>
                        </c:when>
                    </c:choose>
                </td>
                <th class="">补库存</th>
                <td class="">
                    <c:choose>
                        <c:when test="${access.isaddstorage eq 1}">
                            <span>是</span>
                        </c:when>
                        <c:when test="${access.isaddstorage eq 0}">
                            <span>否</span>
                        </c:when>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <th class="">客户</th>
                <td class="" colspan="2"><span><c:out value="${customer.name }"/></span></td>
                <th class="">执行地点</th>
                <td class="" colspan="4"><span><c:out value="${access.executeaddr }"/></span></td>
            </tr>
            <tr>
                <th>商品</th>
                <th>进货价</th>
                <th>工厂让利</th>
                <th>自理</th>
                <th>原价</th>
                <th>现价</th>
                <th>毛利率</th>
                <th>门店出货</th>
            </tr>
            <c:choose>
                <c:when test="${empty pricelist}">
                    <tr>
                        <td colspan="8"><span>无</span></td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${pricelist}" var="item" varStatus="status">
                        <tr>
                            <td><span><c:out value="${item.goodsid }"/></span>：<span><c:out value="${item.goodsname }"/></span></td>
                            <td><span precision="2"><c:out value="${item.buyprice }"/></span></td>
                            <td><span precision="2"><c:out value="${item.factoryprice }"/></span></td>
                            <td><span precision="2"><c:out value="${item.myprice }"/></span></td>
                            <td><span precision="2"><c:out value="${item.oldprice }"/></span></td>
                            <td><span precision="2"><c:out value="${item.newprice }"/></span></td>
                            <td><span precision="2"><c:out value="${item.rate }"/></span>%</td>
                            <td><span><c:out value="${item.senddetail }"/></span></td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
            <tr>
                <td colspan="8" style="text-align: center;">
                    <span>
                        <b>工厂金额：</b><span precision="2"><c:out value="${access.factoryamount }"/></span>
                        <b>自理金额：</b><span precision="2"><c:out value="${access.myamount }"/></span>
                        <b>收回方式：</b>
                        <c:forEach items="${typelist }" var="type" varStatus="status">
                            <c:if test="${access.reimbursetype eq type.code}"><span><c:out value="${type.codename }"/></span></c:if>
                        </c:forEach>
                        <b>收回日期：</b><span><c:out value="${access.reimbursedate }"/></span>
                        <b>支付日期：</b><span><c:out value="${access.paydate }"/></span>
                    </span>
            </tr>
            <tr>
                <th>说明</th>
                <td colspan="7"><span><c:out value="${access.remark1 }"/></span></td>
            </tr>
            <tr>
                <td style="font-weight: 900; height: 40px; border-top: double 3px #000;" colspan="8">第二确认：客户（品牌经理根据客户促销协议核对无误确认提交）</td>
            </tr>
            <tr>
                <th>确认时间</th>
                <td colspan="2"><span><c:out value="${access.conbegindate }"/></span>～<span><c:out value="${access.conenddate }"/></span></td>
                <th>降价设定时间</th>
                <td colspan="2"><span><c:out value="${access.combegindate }"/></span>～<span><c:out value="${access.comenddate }"/></span></td>
                <th>支付方式</th>
                <td>
                    <c:choose>
                        <c:when test="${access.paytype eq 1}">
                            <span>折扣</span>
                        </c:when>
                        <c:when test="${access.paytype eq 2}">
                            <span>支票</span>
                        </c:when>
                        <c:when test="${access.paytype eq 3}">
                            <span>货补</span>
                        </c:when>
                        <c:when test="${access.paytype eq 4}">
                            <span>支票</span>
                        </c:when>
                    </c:choose>
                </td>
            </tr>
            <tr>
                <th>费用金额</th>
                <td colspan="2"><span precision="2"><c:out value="${access.totalamount }"/></span></td>
                <td colspan="5"></td>
            </tr>
            <tr>
                <th class="len80">商品名称</th>
                <th class="len80">单位差价</th>
                <th class="len80">数量</th>
                <th class="len100">辅数量(整)</th>
                <th class="len100">辅数量(余)</th>
                <th class="len80">差价金额</th>
                <th class="len80">ERP数量</th>
                <th class="len80">降价金额</th>
            </tr>
            <c:choose>
                <c:when test="${empty amountlist}">
                    <tr>
                        <td colspan="9"><span>无</span></td>
                    </tr>
                </c:when>
                <c:otherwise>
                    <c:forEach items="${amountlist}" var="item" varStatus="status">
                        <tr>
                            <td><span><c:out value="${item.goodsid }"/></span>：<span><c:out value="${item.goodsname }"/></span></td>
                            <td><span precision="2"><c:out value="${item.difprice }"/></span></td>
                            <td><span precision="<%=BillGoodsNumDecimalLenUtils.decimalLen %>"><c:out value="${item.unitnum }"/></span></td>
                            <td><span precision="0"><c:out value="${item.auxnum }"/></span></td>
                            <td><span precision="<%=BillGoodsNumDecimalLenUtils.decimalLen %>"><c:out value="${item.auxremainder }"/></span></td>
                            <td><span precision="0"><c:out value="${item.amount }"/></span></td>
                            <td><span precision="<%=BillGoodsNumDecimalLenUtils.decimalLen %>"><c:out value="${item.erpnum }"/></span></td>
                            <td><span precision="2"><c:out value="${item.downamount }"/></span></td>
                        </tr>
                    </c:forEach>
                </c:otherwise>
            </c:choose>
            <tr>
                <th>说明</th>
                <td colspan="7"><span><c:out value="${access.remark2 }"/></span></td>
            </tr>
            <tr>
                <td style="font-weight: 900; height: 40px; border-top: double 3px #000;" colspan="8">第三确认:支付信息</td>
            </tr>
            <tr>
                <th>电脑冲差<br/>金额</th>
                <td><span precision="2"><c:out value="${access.compdiscount }"/></span></td>
                <th>电脑降价<br/>金额</th>
                <td><span precision="2"><c:out value="${access.comdownamount }"/></span></td>
                <th>支票金额</th>
                <td><span precision="2"><c:out value="${access.branchaccount }"/></span></td>
                <th>结算金额</th>
                <td><span precision="2"><c:out value="${access.branchaccount }"/></span></td>
            </tr>
            <!--
            -->
        </table>
        <div style="">&nbsp;</div>
        <div class="loading comment">正在获取审批信息...</div>
	</div>
	<script type="text/javascript">
	<!--

    $(function() {

        $('span[precision]').each(function(index) {

            var text = $(this).text();
            text = (text == null || text == '') ? '0' : text;
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