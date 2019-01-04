<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
  </head>
  
  <body>
    <form action="system/task/editTaskList.do" method="post" id="tasklist-form-edit">
	   	<div class="pageContent">
			<p>
				<label>任务名称:</label>
				<input type="hidden" name="taskList.id" value="${taskList.id}"/>
				<input type="text" name="taskList.name" value="${taskList.name}" class="easyui-validatebox" required="true" style="width:200px;"/>
			</p>
			<p>
				<label>任务执行类:</label>
				<input type="text" name="taskList.classpath" value="${taskList.classpath }" class="easyui-validatebox" required="true" style="width:200px;" />
			</p>
			<p>
				<label>类型:</label>
				<select name="taskList.type" style="width:200px;">
					<option value="1" <c:if test="${taskList.type=='1'}">selected="selected"</c:if>>业务</option>
					<option value="2" <c:if test="${taskList.type=='2'}">selected="selected"</c:if>>系统</option>
				</select>
			</p>
			<p>
				<label>状态:</label>
				<input type="hidden" name="taskList.state" value="${taskList.state}"/>
				<select style="width:200px;" disabled="disabled">
					<option value="4" <c:if test="${taskList.state=='4'}">selected="selected"</c:if>>新增</option>
					<option value="3" <c:if test="${taskList.state=='3'}">selected="selected"</c:if>>暂存</option>
					<option value="2" <c:if test="${taskList.state=='2'}">selected="selected"</c:if>>保存</option>
					<option value="1" <c:if test="${taskList.state=='1'}">selected="selected"</c:if>>启用</option>
					<option value="0" <c:if test="${taskList.state=='0'}">selected="selected"</c:if>>禁用</option>
				</select>
			</p>
	    </div>
    </form>
    <script type="text/javascript">
    	$(function(){
    		$("#tasklist-form-edit").form({  
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
			    	if(json.flag==true){
			    		$.messager.alert("提醒",'保存成功');
			    		$("#tasklist-dialog-edit").dialog('close',true);
		    			$("#tasklist-table").datagrid('reload');
			    	}else{
			    		$.messager.alert("提醒",'保存失败');
			    	}
			    }  
			}); 
    		$("#tasklist-save-edittask").click(function(){
    			$.messager.confirm("提醒", "是否保存任务清单?", function(r){
					if (r){
						$("#tasklist-form-edit").submit();
					}
				});
    		});
    	});
    </script>
  </body>
</html>
