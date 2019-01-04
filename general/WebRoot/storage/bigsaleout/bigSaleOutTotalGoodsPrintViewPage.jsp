<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <title>整件分拣&分拣单打印</title>
  <%@include file="/include.jsp"%>
  <style type="text/css">
    .head{
      font-size:30px;
      align:center;
      font-weight:bold;
    }
    .body{
      font-size:20px;
    }
    .style{
      font-size:25px;
    }
    .style1{
      border-bottom-style: none;
      border-top-style:none;
      border-right-style:none;
    }
    .font18{
      font-size:20px;
    }
    .font15{
      font-size:24px;
    }
    .fixed_div{
      position:fixed;
    }
  </style>
</head>
<body>
<div class="fixed_div">
  <input type="button" id="bigsaleout-button-totalgoods-print" value="打印" title="打印"/>
</div>
<object classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" id="web" name="web">
  <div align="center" style="font-size: x-large">
    <c:forEach var="list" items="${goodsList}" varStatus="status">
      <c:choose>
        <c:when test="${(status.index)%(zjpagesize) == 0}">
          <c:if test="${status.index != 0}">
            <div style="page-break-after: always;"></div>
          </c:if>
          <div style="height: 25px;"></div>
          <div class="head">整件分拣</div>
          <div>
            <table cellpadding="0" cellspacing="0" border="0" style="padding: 1px;width: 1070px;">
              <tr>
                <td style="width: 60px;" class="body">排单号：</td>
                <td style="width: 120px;"><label class="body">${id }</label></td>
                <td style="width: 60px;" class="body">理货人：</td>
                <td style="width: 100px;"><label class="body"></label></td>
                <c:choose>
                  <c:when test="${showFJBill=='1'}">
                    <td style="width: 40px;" class="body">份数：</td>
                    <td style="width: 50px;"><label class="body">${billnum }</label></td>
                  </c:when>
                  <c:otherwise>
                    <td style="width: 40px;" class="body">&nbsp;</td>
                    <td style="width: 50px;"><label class="body">&nbsp;</label></td>
                  </c:otherwise>
                </c:choose>
                <td style="width: 40px;" class="body">备注：</td>
                <td style="width: 200px;"><label class="body">${remark }</label></td>
              </tr>
            </table>
          </div>
          <div>
            <table cellpadding="0" cellspacing="0" border="1" style="width: 1070px;">
              <tr>
                <td class="style1" style="width: 50px;"align="center"><label class="style">序号</label></td>
                <td class="style1" style="width: 110px;"align="left"><label class="style">货号</label></td>
                <td class="style1" style="width: 100px;"align="left"><label class="style">货位</label></td>
                <td class="style1" align="left"><label class="style">货品名称</label></td>
                <td class="style1" style="width: 160px;" align="left"><label class="style">条形码</label></td>
                <td class="style1" style="width: 100px;" align="left"><label class="style">规格</label></td>
                <td class="style1" style="width: 120px;" align="left"><label class="style">描述</label></td>
              </tr>
            </table>
            <table cellpadding="0" cellspacing="0" border="1" style="width: 1070px;border-top-style: none">
              <c:choose>
                <c:when test="${null != list.goodsid and '' != list.goodsid}">
                  <tr>
                    <td class="style1" style="width: 50px;"align="center"><label class="font15"><c:if test="${list.goodsid != 'sum'}">${status.index+1}</c:if></label></td>
                    <td class="style1" style="width: 110px;"align="left"><label class="font15"><c:if test="${list.goodsid != 'sum'}">${list.goodsid}</c:if></label></td>
                    <td class="style1" style="width: 100px;"align="left"><label class="font15"><c:if test="${!empty(list.goodsInfo.itemno) }">${list.goodsInfo.itemno}</c:if></label></td>
                    <td class="style1" align="left"><label class="font15">${list.goodsInfo.name}</label></td>
                    <td class="style1" style="width: 160px;" align="left"><label class="font15">${list.goodsInfo.barcode}</label></td>
                    <td class="style1" style="width: 100px;" align="left"><label class="font15">${list.goodsInfo.model}</label></td>
                    <td class="style1" style="width: 120px;" align="left"><label class="style">${list.auxnumdetail}</label></td>
                  </tr>
                </c:when>
                <c:otherwise>
                  <tr>
                    <td class="style1" style="width: 50px;"align="center"><label class="font15"></label></td>
                    <td class="style1" style="width: 110px;"align="left"><label class="font15"></label></td>
                    <td class="style1" style="width: 100px;"align="left"><label class="font15"></label></td>
                    <td class="style1" align="left"><label class="font15">（空白）</label></td>
                    <td class="style1" style="width: 160px;" align="left"></td>
                    <td class="style1" style="width: 100px;" align="left"></td>
                    <td class="style1" style="width: 120px;" align="left"></td>
                  </tr>
                </c:otherwise>
              </c:choose>
            </table>
          </div>
        </c:when>
        <c:otherwise>
          <div>
            <table cellpadding="0" cellspacing="0" border="1" style="width: 1070px;border-top-style: none">
              <c:choose>
                <c:when test="${null != list.goodsid and '' != list.goodsid}">
                  <tr>
                    <td class="style1" style="width: 50px;"align="center"><label class="font15"><c:if test="${list.goodsid != 'sum'}">${status.index+1}</c:if></label></td>
                    <td class="style1" style="width: 110px;"align="left"><label class="font15"><c:if test="${list.goodsid != 'sum'}">${list.goodsid}</c:if></label></td>
                    <td class="style1" style="width: 100px;"align="left"><label class="font15"><c:if test="${!empty(list.goodsInfo.itemno) }">${list.goodsInfo.itemno}</c:if></label></td>
                    <td class="style1" align="left"><label class="font15">${list.goodsInfo.name}</label></td>
                    <td class="style1" style="width: 160px;" align="left"><label class="font15">${list.goodsInfo.barcode}</label></td>
                    <td class="style1" style="width: 100px;" align="left"><label class="font15">${list.goodsInfo.model}</label></td>
                    <td class="style1" style="width: 120px;" align="left"><label class="style">${list.auxnumdetail}</label></td>
                  </tr>
                </c:when>
                <c:otherwise>
                  <tr>
                    <td class="style1" style="width: 50px;"align="center"><label class="font15"></label></td>
                    <td class="style1" style="width: 110px;"align="left"><label class="font15"></label></td>
                    <td class="style1" style="width: 100px;"align="left"><label class="font15"></label></td>
                    <td class="style1" align="left"><label class="font15">（空白）</label></td>
                    <td class="style1" style="width: 160px;" align="left"></td>
                    <td class="style1" style="width: 100px;" align="left"></td>
                    <td class="style1" style="width: 120px;" align="left"></td>
                  </tr>
                </c:otherwise>
              </c:choose>
            </table>
          </div>
        </c:otherwise>
      </c:choose>
    </c:forEach>
  </div>

  <c:if test="${showFJBill=='1'}">
  <!-- 分拣单 -->
  <div style="page-break-after: always;"></div>
  <div align="center" style="font-size: x-large">
    <c:forEach var="m" items="${billmap}" varStatus="status">
      <div style="height: 20px;"></div>
      <div class="head">分拣单</div>
      <div style="border:1px solid black;width: 1070px;">
        <table cellpadding="0" cellspacing="0" border="0" style="width: 1070px;">
          <tr>
            <td style="width: 40px;" class="body">客户：</td>
            <td style="width: 230px;"><label class="body">${m.key.customerid}  ${m.key.customername}</label></td>
            <td style="width: 40px;" class="body">日期：</td>
            <td style="width: 100px;"><label class="body">${m.key.businessdate}</label></td>
            <td style="width: 60px;" class="body">订单编号：</td>
            <td style="width: 100px;"><label class="body">${m.key.saleorderid }</label></td>
          </tr>
          <tr>
            <td colspan="6">
              <table cellpadding="0" cellspacing="0" border="1" style="width: 1070px;">
                <tr>
                  <td style="width: 50px;"align="center"><label class="font15">序号</label></td>
                  <td style="width: 120px;"align="center"><label class="font15">商品编码</label></td>
                  <td align="center"><label class="font15">商品名称</label></td>
                  <td style="width: 180px;" align="center"><label class="font15">条码</label></td>
                  <td style="width: 50px;" align="center"><label class="font15">包装</label></td>
                  <td style="width: 80px;" align="center"><label class="font15">数量</label></td>
                  <td style="width: 130px;" align="center"><label class="font15">箱数</label></td>
                  <td style="width: 120px;" align="center"><label class="font15">备注</label></td>
                </tr>
                <c:forEach items="${m.value}" var="detail" varStatus="detailstatus">
                  <tr>
                    <td style="width: 50px;"align="center"><label class="font15"><c:if test="${detail.goodsid != 'sum'}">${detailstatus.index+1}</c:if></label></td>
                    <td style="width: 120px;"align="left"><label class="font15"><c:if test="${detail.goodsid != 'sum'}">${detail.goodsid}</c:if></label></td>
                    <td align="left"><label class="font15">${detail.goodsInfo.name}</label></td>
                    <td style="width: 180px;" align="left"><label class="font15">${detail.goodsInfo.barcode}</label></td>
                    <td style="width: 50px;" align="right"><label class="font15"><c:if test="${detail.goodsInfo.boxnum!=null && detail.goodsInfo.boxnum >0}"><fmt:formatNumber value="${detail.goodsInfo.boxnum}" pattern="${pattern}"/></c:if></label></td>
                    <td style="width: 80px;" align="right"><label class="font15"><c:if test="${detail.unitnum!=null && detail.unitnum >0}"><fmt:formatNumber value="${detail.unitnum}" pattern="${pattern}"/></c:if></label></td>
                    <td style="width: 130px;" align="right"><label class="font15">${detail.auxnumdetail}</label></td>
                    <td style="width: 120px;" align="left"><label class="font15">${detail.remark}</label></td>
                  </tr>
                </c:forEach>
              </table>
            </td>
          </tr>
        </table>
      </div>
    </c:forEach>
  </div>
  </c:if>
</object>
<script type="text/javascript">
    //打印
    $("#bigsaleout-button-totalgoods-print").click(function(){
        $.messager.confirm('提醒','确定打印?',function(r){
            if (r){
                document.getElementById("bigsaleout-button-totalgoods-print").style.display = "none";
                window.print();
                $.ajax({
                    url :'storage/updateBigSaleoutPrintNum.do',
                    data:{id:"${id }"},
                    type:'post',
                    dataType:'json',
                    error:function(){
                        $.messager.alert("错误","打印出错");
                    }
                });
            }else{
                window.close();
            }
        });
    });
</script>
</body>
</html>