<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <title>供应商应付款期初</title>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
  <div data-options="region:'center',border:false">
    <form action="" id="account-form-begindueadd" method="post">
      <table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
        <tr>
          <td class="len120 left">编号：</td>
          <td style="width: 100px;"><input style="width: 130px;" class="easyui-validatebox" name="beginDue.id" <c:if test="${autoCreate == true }">readonly='readonly'</c:if> <c:if test="${autoCreate == false }">required="required"</c:if> /></td>
          <td class="len120 left">业务日期：</td>
          <td style="width: 100px;"><input type="text" id="account-begindue-businessdate" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style="width: 130px;" value="${busdate }" name="beginDue.businessdate" /></td>
          <td class="len80 left">状态：</td>
          <td style="width: 100px;"><select disabled="disabled" style="width: 100px;"><option>新增</option></select></td>
        </tr>
        <tr>
          <td class="len120 left">供应商：</td>
          <td colspan="3" style="text-align: left;">
            <input type="text" id="account-begindue-supplierid" name="beginDue.supplierid" style="width: 250px;"/>
            <span id="account-begindue-supplierid-span" style="margin-left:5px;line-height:25px;"></span>
          </td>
          <td class="len80 left">金额：</td>
          <td >
            <input type="text" id="account-beigndue-amount" name="beginDue.amount" class="len100" autocomplete="off"/>
          </td>
        </tr>
        <tr>
          <td class="len120 left">备注：</td>
          <td colspan="6" style="text-align: left">
            <input type="text" name="beginDue.remark" style="width: 569px;"/>
            <input type="hidden" id="account-beigndue-addtype" value="save"/>
          </td>
        </tr>
        <tr>
      </table>
    </form>
  </div>
  <div data-options="region:'south',border:false">
    <div class="buttonDetailBG" style="text-align:right;">
      <security:authorize url="/account/begindue/addBeginDueSave.do">
        <input type="button" value="确定" name="savegoon" id="account-beigndue-addbutton" />
      </security:authorize>
      <security:authorize url="/account/begindue/addBeginDueSave.do">
        <input type="button" value="继续添加" name="savegoon" id="account-beigndue-addgobutton" />
      </security:authorize>
    </div>
  </div>
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
        $("#account-begindue-supplierid-span").html("编码:"+data.id);
        getNumberBox("account-beigndue-amount").focus();
      }
    });
    $("#account-beigndue-amount").numberbox({
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
          $.messager.alert("提醒","保存成功");
          $("#account-datagrid-BeginDuePage").datagrid("reload");
          var savetype = $("#account-beigndue-addtype").val();
          if(savetype=="saveadd"){
            $("#account-begindue-supplierid").supplierWidget("clear");
            $("#account-beigndue-amount").numberbox("clear");
            $("#account-form-begindueadd").form("reset");
            $("#account-begindue-supplierid-span").html("");
            $("#account-begindue-supplierid").focus();
          }else{
            $('#account-panel-beigndue-addpage').dialog("close");
          }
        }else{
          $.messager.alert("提醒","保存失败");
        }
      }
    });
    $("#account-beigndue-addbutton").click(function(){
      $("#account-beigndue-addtype").val("save");
      $("#account-form-begindueadd").attr("action", "account/begindue/addBeginDueSave.do");
      $("#account-form-begindueadd").submit();
    });
    $("#account-beigndue-addgobutton").click(function(){
      $("#account-beigndue-addtype").val("saveadd");
      $("#account-form-begindueadd").attr("action", "account/begindue/addBeginDueSave.do");
      $("#account-form-begindueadd").submit();
    });
    getNumberBox("account-beigndue-amount").bind("keydown",function(event){
      //enter
      if(event.keyCode==13){
        $("#account-beigndue-addgobutton").focus();
      }
    });
  });
</script>
</body>
</html>
