<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<title>工作委托修改页面</title>
	<%@include file="/include.jsp" %>   
  </head>
  <body>
  <style type="text/css">
      .delegateAddTable {
          border-collapse: collapse;
          border: 1px solid #aaaaaa;
          width: 500px;
          margin: 10px auto;}
      .delegateAddTable td{
          border: 1px solid #aaaaaa;
          padding: 5px;
      }
      .delegateAddTable td.title{
          background: #efefef;
          width: 80px;
          padding: 10px;
      }
      .len200 {
          width: 200px;
      }
      .len230 {
          width: 230px;
      }
      label > input[type=checkbox]{
          float:left;
          height: 12px;
          line-height: 15px;
      }
  </style>
	<form action="act/updateDelegate.do" method="post" id="activiti-form-delegateEditPage">
        <input type="hidden" name="delegate.id" id="activiti-id-delegateEditPage" value="${delegate.id }"/>
        <input type="hidden" name="delegate.definitionkey" id="activiti-definitionkey-delegateEditPage" value="${delegate.definitionkey }"/>
        <table class="delegateAddTable" cellspacing="0" cellpadding="0">
            <tr>
                <td class="title">　任　　务：</td>
                <td style="border-right: 0px;"><input type="text" class="easyui-validatebox len200" id="activiti-definitionkey2-delegateEditPage" readonly="readonly" value="${delegate.definitionkey }"/></td>
                <td style="border-left: 0px;"><label><input type="checkbox" id="activiti-all-delegateEditPage" value="0" disabled="disabled" <c:if test="${delegate.definitionkey eq '0'}">checked="checked"</c:if> />所有流程</label></td>
            </tr>
            <tr>
                <td class="title">　委 托 人：</td>
                <td colspan="2"><input class="easyui-validatebox len200" name="delegate.userid" id="activiti-userid-delegateEditPage" data-options="required:true" autocomplete="off" maxlength="50" value="${user.userid }" readonly="readonly"/></td>
            </tr>
            <tr>
                <td class="title">　被委托人：</td>
                <td colspan="2"><input class="easyui-validatebox len200" name="delegate.delegateuserid" id="activiti-delegateuserid-delegateEditPage" data-options="required:true" autocomplete="off" maxlength="50" value="${delegate.delegateuserid }"/></td>
            </tr>
            <tr>
                <td class="title len120" style="border-bottom: 0px;">　生效日期：</td>
                <td class="len230" style="border-right: 0px; border-bottom: 0px;"><input class="easyui-validatebox len200 Wdate" name="delegate.begindate" id="activiti-begindate-delegateEditPage" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd HH:mm:ss'})" data-options="required:true" autocomplete="off" value="${delegate.begindate }" <c:if test="${delegate.remain eq '1'}">disabled="disabled"</c:if>/></td>
                <td rowspan="2" style="border-left: 0px;"><label><input type="checkbox" name="delegate.remain" id="activiti-remain-delegateEditPage" value="1" <c:if test="${delegate.remain eq '1'}">checked="checked"</c:if>/>一直有效</label></td>
            </tr>
            <tr>
                <td class="title" style="border-top: 0px;">　截止日期：</td>
                <td style="border-right: 0px; border-top: 0px;"><input class="easyui-validatebox len200 Wdate" name="delegate.enddate" id="activiti-enddate-delegateEditPage" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd HH:mm:ss'})" data-options="required:true" autocomplete="off" value="${delegate.enddate }" <c:if test="${delegate.remain eq '1'}">disabled="disabled"</c:if>/></td>
            </tr>
            <security:authorize url="/act/updateDelegate.do">
                <tr>
                    <td colspan="3">
                        <div style="padding: 3px; text-align: center;">
                            <a href="javascript:;" id="activiti-save-delegateEditPage" class="easyui-linkbutton" data-options="iconCls:'button-save'">保存</a>
                            <a href="javascript:;" id="activiti-cancel-delegateEditPage" class="easyui-linkbutton" data-options="iconCls:'button-close'">取消修改</a>
                        </div>
                    </td>
                </tr>
            </security:authorize>
        </table>
		<%--<table class="delegateAddTable">--%>
			<%--<tr>--%>
				<%--<td class="td1">任务标识：</td>--%>
				<%--<td>--%>
					<%--<input type="text" class="easyui-validatebox" value="${definition.id }" id="activiti-combo-delegateEditPage" required="required" style="width:200px;"/>--%>
					<%--<input type="hidden" name="delegate.definitionkey" value="${delegate.definitionkey }" id="activiti-definitionkey-delegateEditPage" />--%>
					<%--<input type="hidden" name="delegate.id" value="${delegate.id }" />					--%>
				<%--</td>--%>
			<%--</tr>--%>
			<%--<tr>--%>
				<%--<td class="td1">被委托人：</td>--%>
				<%--<td><input type="text" name="delegate.delegateuserid" value="${delegate.delegateuserid }" id="activiti-delegate-delegateEditPage" class="easyui-validatebox" required="required"/></td>--%>
			<%--</tr>--%>
			<%--<tr>--%>
				<%--<td class="td1">生效日期：</td>--%>
				<%--<td><input type="text" class="Wdate" value="${delegate.begindate }" onfocus="WdatePicker({'minDate':'%Date'})" name="delegate.begindate" id="activiti-begindate-delegateEditPage" style="width:120px;"/> <input type="checkbox" <c:if test="${delegate.remain == 1 }">checked="checked"</c:if> style="width:auto;position:relative;top:3px;" name="delegate.remain" value="1" id="activiti-checkbox-delegateEditPage"/><label for="activiti-checkbox-delegateEditPage">一直有效</label></td>--%>
			<%--</tr>--%>
			<%--<tr>--%>
				<%--<td class="td1">终止日期：</td>--%>
				<%--<td><input type="text" class="Wdate" value="${delegate.begindate }" onfocus="WdatePicker({'minDate':$('#activiti-begindate-delegateEditPage').val()})" name="delegate.enddate" id="activiti-enddate-delegateEditPage" style="width:120px;"/></td>--%>
			<%--</tr>--%>
			<%--<tr>--%>
				<%--<td colspan="2">--%>
					<%--<div style="padding:3px;background:#efefef;text-align:center;">--%>
						<%--<a href="javascript:;" id="activiti-save-delegateEditPage" class="easyui-linkbutton" data-options="iconCls:'button-save'">保存</a>--%>
						<%--<a href="javascript:;" id="activiti-cancel-delegateEditPage" class="easyui-linkbutton" data-options="iconCls:'button-close'">取消修改</a>--%>
					<%--</div>--%>
				<%--</td>--%>
			<%--</tr>--%>
		<%--</table>--%>
	</form>
	<script type="text/javascript">
    	$(function(){
            $('#activiti-definitionkey2-delegateEditPage').widget({
                name:'t_act_delegate',
                col:'definitionkey',
                singleSelect:true,
                width:200,
                onlyLeafCheck:true,
                required: false,
                onChecked:function(data){
                    $('#activiti-definitionkey-delegateEditPage').val(data.id);
                }
            });

            $('#activiti-userid-delegateEditPage').widget({
                name: 't_act_delegate',
                col: 'userid',
                singleSelect: true,
                async: false,
                width: 200,
                readonly: true,
                onlyLeafCheck: true
            });

            $('#activiti-delegateuserid-delegateEditPage').widget({
                name: 't_act_delegate',
                col: 'delegateuserid',
                singleSelect: true,
                width: 200,
                param: [{field:'userid', op:'notequal', value:'${delegate.userid}'}],
                onlyLeafCheck: true,
                required: true
            });
            
     		$("#activiti-form-delegateEditPage").form({  
			    onSubmit: function(){  
			    	var flag = $(this).form('validate');
			    	if(flag==false){
			    		return false;
			    	}
			    	loading("提交中..");
			    },  
			    success:function(data){
			    	loaded();
			    	var json = $.parseJSON(data);
			        if(json.flag==true){
			        	$.messager.alert("提醒","保存成功");
			        	$("#activiti-datagrid-delegatePage").datagrid('reload');
						$("#activiti-panel-delegatePage").panel('refresh', 'act/delegateEditPage.do');
			        }
			        else{
			        	$.messager.alert("提醒","保存失败");
			        }
			    }  
			}); 
			$("#activiti-save-delegateEditPage").click(function(){
				$.messager.confirm("提醒", "是否保存该工作委托信息?", function(r){
					if (r){
						$("#activiti-form-delegateEditPage").submit();
					}
				});
			});
			$("#activiti-cancel-delegateEditPage").click(function(){
				$("#activiti-panel-delegatePage").panel('refresh', 'act/delegateAddPage.do');
			});

            // 一直有效
            $('#activiti-remain-delegateEditPage').off().click(function(e) {

                var checked = $(this).is(':checked');
                if(checked) {

                    $('#activiti-begindate-delegateEditPage').val('');
                    $('#activiti-enddate-delegateEditPage').val('');
                    $('#activiti-begindate-delegateEditPage').validatebox({required: false});
                    $('#activiti-enddate-delegateEditPage').validatebox({required: false});
                    $('#activiti-begindate-delegateEditPage').validatebox('validate');
                    $('#activiti-enddate-delegateEditPage').validatebox('validate');
                    $('#activiti-begindate-delegateEditPage').attr('disabled', 'disabled');
                    $('#activiti-enddate-delegateEditPage').attr('disabled', 'disabled');

                } else {

                    $('#activiti-begindate-delegateEditPage').removeAttr('disabled');
                    $('#activiti-enddate-delegateEditPage').removeAttr('disabled');
                    $('#activiti-begindate-delegateEditPage').validatebox({required: true});
                    $('#activiti-enddate-delegateEditPage').validatebox({required: true});
                    $('#activiti-begindate-delegateEditPage').validatebox('validate');
                    $('#activiti-enddate-delegateEditPage').validatebox('validate');
                }
            });
     	});
	</script>
  </body>
</html>