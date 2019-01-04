<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
  <title>按品牌分商品打印</title>
  <%@include file="/include.jsp"%>
  <style type="text/css">
    .head{
      font-size:30px;
      align:center;
      font-weight:bold;
    }
    .body{
      font-size:19px;
    }
    .style{
      font-size:20px;
    }
    .style1{
      border-bottom-style: none;
      border-top-style:none;
      border-right-style:none;
    }
    .font18{
      font-size:25px;
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
<!-- 按品牌分商品 -->
<div class="fixed_div">
  <input type="button" id="bigsaleout-button-brand-print" value="打印" title="打印"/>
</div>
<object classid="CLSID:8856F961-340A-11D0-A96B-00C04FD705A2" id="web" name="web">
  <div align="center">
    <c:forEach var="item" items="${brandDivMap}" varStatus="status">
      <c:if test="${status.index != 0}">
        <div style="page-break-after: always;"></div>
      </c:if>
      <div class="head">按品牌分商品</div>
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
      <div style="border:1px solid black;width: 1070px;">
        <table cellpadding="0" cellspacing="0" border="0" >
          <tr>
            <td style="width: 60px;"><label class="font18">品牌名称:</label></td>
            <td style="width: 80px;"><label class="font18">${item.key.brandname}</label></td>
            <td style="width: 50px;"><label class="font18">总箱数:</label></td>
            <td style="width: 60px;"><label class="font18">
              <c:if test="${item.key.auxnum != 0}">
                ${item.key.totalauxnumdetail}
              </c:if>
            </label></td>
            <td style="width: 40px;"><label class="font18">总个数:</label></td>
            <td style="width: 60px;"><label class="font18">
              <c:if test="${item.key.auxremainder != 0}">
                ${item.key.auxremainderdetail}
              </c:if>
            </label></td>
            <td style="width: 30px;"><label class="font18">小计:</label></td>
            <td style="width: 80px;"><label class="font18">${item.key.auxnumdetail}</label></td>
          </tr>
          <tr>
            <td colspan="8">
              <table cellpadding="0" cellspacing="0" border="1" style="width: 1070px;">
                <tr>
                  <td style="width: 50px;" align="center"><label class="font15">序号</label></td>
                  <td style="width: 120px;" align="left"><label class="font15">货号</label></td>
                  <td align="left"><label class="font15">货品名称</label></td>
                  <td style="width: 140px;" align="left"><label class="font15">条形码</label></td>
                  <td style="width: 100px;" align="left"><label class="font15">规格</label></td>
                  <td style="width: 140px;" align="left"><label class="font15">描述</label></td>
                </tr>
                <c:forEach var="list" items="${item.value}" varStatus="liststatus">
                  <tr>
                    <td style="width: 50px;"align="center"><label class="font15">${liststatus.index+1}</label></td>
                    <td style="width: 120px;" align="left"><label class="font15">${list.goodsInfo.id}</label></td>
                    <td align="left"><label class="font15">${list.goodsInfo.name}</label></td>
                    <td style="width: 140px;" align="left"><label class="font15">${list.goodsInfo.barcode}</label></td>
                    <td style="width: 100px;" align="left"><label class="font15">${list.goodsInfo.model}</label></td>
                    <td style="width: 140px;" align="left"><label class="font15">${list.auxnumdetail}</label></td>
                  </tr>
                </c:forEach>
              </table>
            </td>
          </tr>
        </table>
      </div>
    </c:forEach>
  </div>
</object>
<script type="text/javascript">
  //打印
  $("#bigsaleout-button-brand-print").click(function(){
    $.messager.confirm('提醒','确定打印?',function(r){
      if (r){
        document.getElementById("bigsaleout-button-brand-print").style.display = "none";
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
