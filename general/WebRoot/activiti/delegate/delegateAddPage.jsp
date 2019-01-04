<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	<title>工作委托添加页面</title>
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
	<form action="act/addDelegate.do" method="post" id="activiti-form-delegateAddPage">
        <input type="hidden" name="delegate.definitionkey" id="activiti-definitionkey-delegateAddPage"/>
        <table class="delegateAddTable" cellspacing="0" cellpadding="0">
            <tr>
                <td class="title">　任　　务：</td>
                <td style="border-right: 0px;"><input type="text" class="easyui-validatebox len200" id="activiti-definitionkey2-delegateAddPage" /></td>
                <td style="border-left: 0px;"><label><input type="checkbox" id="activiti-all-delegateAddPage" value="0" />所有流程</label></td>
            </tr>
            <tr>
                <td class="title">　委 托 人：</td>
                <td colspan="2"><input class="easyui-validatebox len200" name="delegate.userid" id="activiti-userid-delegateAddPage" data-options="required:true" autocomplete="off" maxlength="50" value="${user.userid }" readonly="readonly"/></td>
            </tr>
            <tr>
                <td class="title">　被委托人：</td>
                <td colspan="2"><input class="easyui-validatebox len200" name="delegate.delegateuserid" id="activiti-delegateuserid-delegateAddPage" data-options="required:true" autocomplete="off" maxlength="50"/></td>
            </tr>
            <tr>
                <td class="title len120" style="border-bottom: 0px;">　生效日期：</td>
                <td class="len230" style="border-right: 0px; border-bottom: 0px;"><input class="easyui-validatebox len200 Wdate" name="delegate.begindate" id="activiti-begindate-delegateAddPage" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd HH:mm:ss'})" data-options="required:true" autocomplete="off" /></td>
                <td rowspan="2" style="border-left: 0px;"><label><input type="checkbox" name="delegate.remain" id="activiti-remain-delegateAddPage" value="1" />一直有效</label></td>
            </tr>
            <tr>
                <td class="title" style="border-top: 0px;">　截止日期：</td>
                <td style="border-right: 0px; border-top: 0px;"><input class="easyui-validatebox len200 Wdate" name="delegate.enddate" id="activiti-enddate-delegateAddPage" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd HH:mm:ss'})" data-options="required:true" autocomplete="off" /></td>
            </tr>
            <security:authorize url="/act/addDelegate.do">
                <tr>
                    <td colspan="3">
                        <div style="padding: 3px; text-align: center;">
                            <a href="javascript:;" id="activiti-save-delegateAddPage" class="easyui-linkbutton" data-options="iconCls:'button-add'">添加</a>
                        </div>
                    </td>
                </tr>
            </security:authorize>
        </table>
		<%--<table class="delegateAddTable">--%>
			<%--<tr>--%>
				<%--<td class="td1">任务标识：</td>--%>
				<%--<td>--%>
					<%--<input type="text" class="easyui-validatebox" id="activiti-combo-delegateAddPage" required="required" style="width:200px;"/>--%>
					<%--<input type="hidden" name="delegate.definitionkey" id="activiti-definitionkey-delegateAddPage" />--%>
				<%--</td>--%>
			<%--</tr>--%>
			<%--<tr>--%>
				<%--<td class="td1">被委托人：</td>--%>
				<%--<td><input type="text" name="delegate.delegateuserid" id="activiti-delegate-delegateAddPage" class="easyui-validatebox" required="required"/></td>--%>
			<%--</tr>--%>
			<%--<tr>--%>
				<%--<td class="td1">生效日期：</td>--%>
				<%--<td><input type="text" class="Wdate" onfocus="WdatePicker({'minDate':'%Date'})" name="delegate.begindate" id="activiti-begindate-delegateAddPage" style="width:120px;"/> <input type="checkbox" style="width:auto;position:relative;top:3px;" name="delegate.remain" value="1" id="activiti-checkbox-delegateAddPage"/><label for="activiti-checkbox-delegateAddPage">一直有效</label></td>--%>
			<%--</tr>--%>
			<%--<tr>--%>
				<%--<td class="td1">终止日期：</td>--%>
				<%--<td><input type="text" class="Wdate" onfocus="WdatePicker({'minDate':$('#activiti-begindate-delegateAddPage').val()})" name="delegate.enddate" id="activiti-enddate-delegateAddPage" style="width:120px;"/></td>--%>
			<%--</tr>--%>
			<%--<tr>--%>
				<%--<td colspan="2">--%>
					<%--<div style="padding:3px;background:#efefef;text-align:center;">--%>
						<%--<a href="javascript:;" id="activiti-save-delegateAddPage" class="easyui-linkbutton" data-options="iconCls:'button-add'">添加</a>--%>
					<%--</div>--%>
				<%--</td>--%>
			<%--</tr>--%>
		<%--</table>--%>
	</form>
	<script type="text/javascript">
    	$(function(){
    		$('#activiti-definitionkey2-delegateAddPage').widget({
    			name:'t_act_delegate',
    			col:'definitionkey',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:true,
                required: true,
    			onChecked:function(data){
					$('#activiti-definitionkey-delegateAddPage').val(data.id);
    			}
    		});

            $('#activiti-userid-delegateAddPage').widget({
                name: 't_act_delegate',
                col: 'userid',
                singleSelect: true,
                async: false,
                width: 200,
                onlyLeafCheck: true
            });

			$('#activiti-delegateuserid-delegateAddPage').widget({
				name: 't_act_delegate',
    			col: 'delegateuserid',
    			singleSelect: true,
    			width: 200,
    			param: [{field:'userid', op:'notequal', value:'${user.userid}'}],
    			onlyLeafCheck: true,
                required: true
    		});
     		$('#activiti-form-delegateAddPage').form({
			    onSubmit: function(){  
			    	var flag = $(this).form('validate');
			    	if(flag == false){
			    		return false;
			    	}
			    	loading('提交中...');
			    },  
			    success:function(data){
			    	loaded();
			    	var json = $.parseJSON(data);
			        if(json.flag == true){

			        	$.messager.alert('提醒', '添加成功');
			        	$('#activiti-datagrid-delegatePage').datagrid('reload');
			        	$('#activiti-panel-delegatePage').panel('refresh');

                    } else{

			        	$.messager.alert('提醒', '添加失败');
			        }
			    }  
			}); 
			$('#activiti-save-delegateAddPage').click(function(){
				$.messager.confirm('提醒', '是否添加该工作委托信息?', function(r){
					if (r){
						$('#activiti-form-delegateAddPage').submit();
					}
				});
			});

            // 全部流程
            $('#activiti-all-delegateAddPage').off().click(function(e) {

                var checked = $(this).is(':checked');
                if(checked) {

                    $('#activiti-definitionkey2-delegateAddPage').validatebox({required: false});
                    $('#activiti-definitionkey2-delegateAddPage').validatebox('validate');
                    $('#activiti-definitionkey2-delegateAddPage').widget('clear');
                    $('#activiti-definitionkey2-delegateAddPage').widget('disable');

                    $('#activiti-definitionkey-delegateAddPage').val('0');

                } else {

                    $('#activiti-definitionkey2-delegateAddPage').widget('enable');
                    $('#activiti-definitionkey2-delegateAddPage').validatebox({required: true});
                    $('#activiti-definitionkey2-delegateAddPage').validatebox('validate');
                }
            });

            // 一直有效
            $('#activiti-remain-delegateAddPage').off().click(function(e) {

                var checked = $(this).is(':checked');
                if(checked) {

                    $('#activiti-begindate-delegateAddPage').val('');
                    $('#activiti-enddate-delegateAddPage').val('');
                    $('#activiti-begindate-delegateAddPage').validatebox({required: false});
                    $('#activiti-enddate-delegateAddPage').validatebox({required: false});
                    $('#activiti-begindate-delegateAddPage').validatebox('validate');
                    $('#activiti-enddate-delegateAddPage').validatebox('validate');
                    $('#activiti-begindate-delegateAddPage').attr('disabled', 'disabled');
                    $('#activiti-enddate-delegateAddPage').attr('disabled', 'disabled');

                } else {

                    $('#activiti-begindate-delegateAddPage').removeAttr('disabled');
                    $('#activiti-enddate-delegateAddPage').removeAttr('disabled');
                    $('#activiti-begindate-delegateAddPage').validatebox({required: true});
                    $('#activiti-enddate-delegateAddPage').validatebox({required: true});
                    $('#activiti-begindate-delegateAddPage').validatebox('validate');
                    $('#activiti-enddate-delegateAddPage').validatebox('validate');
                }
            });
     	});
	</script>
  </body>
</html>