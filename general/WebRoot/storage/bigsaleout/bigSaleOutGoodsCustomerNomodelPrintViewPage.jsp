<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <title>按商品分客户数（无规格）打印</title>
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
<!-- 按商品分客户数 -->
<div class="fixed_div">
  <input type="button" id="bigsaleout-button-goodscustomer-print" value="打印" title="打印"/>
</div>
<object classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" id="web" name="web">
  <div align="center">
    <c:forEach var="list" items="${goodsCustomerList}" varStatus="status">
      <c:choose>
        <c:when test="${(status.index)%16 == 0}">
          <c:if test="${status.index != 0}">
            <div style="page-break-after: always;"></div>
          </c:if>
          <div style="height: 25px;"></div>
          <div class="head">按商品分客户</div>
          <div>
            <table cellpadding="0" cellspacing="0" border="0" style="padding: 1px;width: 800px;">
              <tr>
                <td style="width: 60px;" class="body">排单号：</td>
                <td style="width: 200px;"><label class="body">${id }</label></td>
                <td style="width: 60px;" class="body">理货人：</td>
                <td style="width: 100px;"><label class="body"></label></td>
                <td style="width: 60px;" class="body">份数：</td>
                <td style="width: 60px;"><label class="body">${billnum }</label></td>
              </tr>
            </table>
          </div>
          <div>
            <table cellpadding="0" cellspacing="0" border="1" style="width: 1060px;">
              <tr>
                <td class="style1" style="width: 50px;"align="center"><label class="style">序号</label></td>
                <td class="style1" style="width: 100px;"align="left"><label class="style">货号</label></td>
                <td class="style1" style="width: 170px;" align="left"><label class="style">货品名称</label></td>
                <td class="style1" style="width: 170px;" align="left"><label class="style">条形码</label></td>
                <td class="style1" style="width: 70px;" align="left"><label class="style">客编</label></td>
                <td class="style1" align="left"><label class="style">客户名称</label></td>
                <td class="style1" style="width: 80px;" align="center"><label class="style">箱装量</label></td>
                <td class="style1" style="width: 160px;" align="center"><label class="style">描述</label></td>
              </tr>
            </table>
            <table cellpadding="0" cellspacing="0" border="1" style="width: 1060px;border-top-style: none">
              <c:if test="${null != list.goodsid and '' != list.goodsid}">
                <tr>
                  <td class="style1" style="width: 50px;"align="center"><label class="font15"><c:if test="${list.goodsid != 'sum'}">${status.index+1}</c:if></label></td>
                  <td class="style1" style="width: 100px;"align="left"><label class="font15"><c:if test="${list.goodsid != 'sum'}">${list.goodsid}</c:if></label></td>
                  <td class="style1" style="width: 170px;" align="left"><label class="font15">${list.goodsInfo.name}</label></td>
                  <td class="style1" style="width: 170px;" align="left"><label class="font15">${list.goodsInfo.barcode}</label></td>
                  <td class="style1" style="width: 70px;" align="left"><label class="font15">${list.customerid}</label></td>
                  <td class="style1" align="left"><label class="font15">${list.customername}</label></td>
                  <td class="style1" style="width: 80px;" align="center"><label class="font15"><c:if test="${list.goodsInfo.boxnum!=null && list.goodsInfo.boxnum >0}"><fmt:formatNumber value="${list.goodsInfo.boxnum}" pattern="${pattern}"/>*1</c:if></label></td>
                  <td class="style1" style="width: 160px;" align="center">
                    <label class="font15">
                        ${list.auxnumdetail}
                    </label>
                  </td>
                </tr>
              </c:if>
            </table>
          </div>
        </c:when>
        <c:otherwise>
          <div>
            <table cellpadding="0" cellspacing="0" border="1" style="width: 1060px;border-top-style: none">
              <c:if test="${null != list.goodsid and '' != list.goodsid}">
                <tr>
                  <td class="style1" style="width: 50px;"align="center"><label class="font15"><c:if test="${list.goodsid != 'sum'}">${status.index+1}</c:if></label></td>
                  <td class="style1" style="width: 100px;"align="left"><label class="font15"><c:if test="${list.goodsid != 'sum'}">${list.goodsid}</c:if></label></td>
                  <td class="style1" style="width: 170px;"  align="left"><label class="font15">${list.goodsInfo.name}</label></td>
                  <td class="style1" style="width: 170px;" align="left"><label class="font15">${list.goodsInfo.barcode}</label></td>
                  <td class="style1" style="width: 70px;" align="left"><label class="font15">${list.customerid}</label></td>
                  <td class="style1" align="left"><label class="font15">${list.customername}</label></td>
                  <td class="style1" style="width: 80px;" align="center"><label class="font15"><c:if test="${list.goodsInfo.boxnum!=null && list.goodsInfo.boxnum >0}"><fmt:formatNumber value="${list.goodsInfo.boxnum}" pattern="${pattern}"/>*1</c:if></label></td>
                  <td class="style1" style="width: 160px;" align="center">
                    <label class="font15">
                        ${list.auxnumdetail}
                    </label>
                  </td>
                </tr>
              </c:if>
            </table>
          </div>
        </c:otherwise>
      </c:choose>
    </c:forEach>
  </div>
</object>
<script type="text/javascript">
  //打印
  $("#bigsaleout-button-goodscustomer-print").click(function(){
    $.messager.confirm('提醒','确定打印?',function(r){
      if (r){
        document.getElementById("bigsaleout-button-goodscustomer-print").style.display = "none";
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
