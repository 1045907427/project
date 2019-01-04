<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <title>供应商应付款期初修改</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
  <div data-options="region:'center',border:false">
    <form action="" id="account-form-begindueadd" method="post">
      <table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
        <tr>
          <td class="len120 left">编号：</td>
          <td><input class="len130 easyui-validatebox" name="beginDue.id" value="${beginDue.id }" readonly="readonly"/></td>
          <td class="len120 left">业务日期：</td>
          <td><input type="text" id="account-begindue-businessdate" class="Wdate" style="width: 100px;" value="${beginDue.businessdate }" name="beginDue.businessdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/></td>
          <td class="len80 left">状态：</td>
          <td>
            <select disabled="disabled" class="len100">
              <c:forEach items="${statusList }" var="list">
                <c:choose>
                  <c:when test="${list.code == beginDue.status}">
                    <option value="${list.code }" selected="selected">${list.codename }</option>
                  </c:when>
                  <c:otherwise>
                    <option value="${list.code }">${list.codename }</option>
                  </c:otherwise>
                </c:choose>
              </c:forEach>
            </select>
            <input type="hidden" name="beginDue.status" value="${beginDue.status }"/>
          </td>
        </tr>
        <tr>
          <td class="len120 left">供应商：</td>
          <td colspan="3" style="text-align: left;">
            <input type="text" id="account-begindue-supplierid" name="beginDue.supplierid" style="width: 250px;" value="${beginDue.supplierid }" text="<c:out value="${beginDue.suppliername }"></c:out>"/>
            <span style="margin-left:5px;line-height:25px;">编码：${beginDue.supplierid }</span>
          </td>
          <td class="len80 left">金额：</td>
          <td >
            <input type="text" id="account-begindue-amount" name="beginDue.amount" class="len100" autocomplete="off" value="${beginDue.amount }"/>
          </td>
        </tr>
        <tr>
          <td class="len120 left">备注：</td>
          <td colspan="6" style="text-align: left">
            <input type="text" name="beginDue.remark" style="width: 569px;" value="<c:out value="${beginDue.remark }"></c:out>"/>
          </td>
        </tr>
        <tr>
      </table>
    </form>
  </div>
  <security:authorize url="/account/begindue/addBeginDueSave.do">
    <div data-options="region:'south',border:false">
      <div class="buttonDetailBG" style="text-align:right;">
        <input type="button" value="确定" name="savegoon" id="account-begindue-addbutton" />
      </div>
    </div>
  </security:authorize>
</div>
<script type="text/javascript">
  $(function(){
    $("#account-begindue-supplierid").supplierWidget({
      width:380,
      singleSelect:true,
      ishead:true,
      isall:true,
      isopen:true,
      required:true,
      onSelect:function(data){
        getNumberBox("account-begindue-amount").focus();
        getNumberBox("account-begindue-amount").select();
      }
    });
    $("#account-begindue-amount").numberbox({
      precision:2,
      required:true
    });
    $("#account-form-begindueadd").form({
      onSubmit: function(){
        var flag = $(this).form('validate');
        if(flag==false){
          return false;
        }
        loading("提交中..");
      },
      success:function(data){
        //表单提交完成后 隐藏提交等待页面
        loaded();
        var json = $.parseJSON(data);
        if(json.flag){
          $("#account-datagrid-BeginDuePage").datagrid("reload");
          $.messager.alert("提醒","保存成功");
          $('#account-panel-beigndue-addpage').dialog("close");
        }else{
          $.messager.alert("提醒","保存失败");
        }
      }
    });
    $("#account-begindue-addbutton").click(function(){
      $("#account-beginDue-addtype").val("save");
      $("#account-form-begindueadd").attr("action", "account/begindue/editBeginDueSave.do");
      $("#account-form-begindueadd").submit();
    });
    getNumberBox("account-begindue-amount").bind("keydown",function(event){
      //enter
      if(event.keyCode==13){
        $("#account-begindue-addbutton").focus();
      }
    });
  });

</script>
</body>
</html>
