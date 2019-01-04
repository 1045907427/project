<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>任务添加页面</title>
  </head>
  
  <body>
    <form action="system/task/addTaskList.do" method="post" id="tasklist-form-add">
	   	<div class="pageContent">
			<p>
				<label>任务名称:</label>
				<input type="text" name="taskList.name" class="easyui-validatebox" required="true" style="width:200px;"/>
			</p>
			<p>
				<label>任务执行类:</label>
				<input type="text" name="taskList.classpath"  class="easyui-validatebox" required="true" style="width:200px;" />
			</p>
			<p>
				<label>类型:</label>
				<select name="taskList.type" style="width:200px;">
					<option value="1">业务</option>
					<option value="2">系统</option>
				</select>
			</p>
			<p>
				<label>状态:</label>
				<select name="taskList.state" style="width:200px;" disabled="disabled">
					<option value="4">新增</option>
				</select>
			</p>
	    </div>
    </form>
    <script type="text/javascript">
    	$(function(){
    		$("#tasklist-form-add").form({  
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
			    		$("#tasklist-dialog-add").dialog('close',true);
		    			$("#tasklist-table").datagrid('reload');
			    	}else{
			    		$.messager.alert("提醒",'保存失败');
			    	}
			    }  
			}); 
    		$("#tasklist-save-addtask").click(function(){
    			$.messager.confirm("提醒", "是否保存任务清单?", function(r){
					if (r){
						$("#tasklist-form-add").submit();
					}
				});
    		});
    	});
    </script>
  </body>
</html>
