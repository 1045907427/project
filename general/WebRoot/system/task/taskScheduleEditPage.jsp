<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>任务计划安排添加</title>

  </head>
  
  <body>
    <form action="system/task/editTaskSchedule.do" method="post" id="taskSchedule-form-edit">
	   	<div class="pageContent">
			<p>
				<label>任务名称:</label>
				<input type="hidden" id="taskSchedule-edit-taskid" name="taskSchedule.taskid" value="${taskSchedule.taskid }"/>
				<input type="hidden" id="taskSchedule-edit-classpath" name="taskSchedule.classpath" value="${taskSchedule.classpath }"/>
				<input type="hidden" id="taskSchedule-edit-taskname" name="taskSchedule.taskname" value="${taskSchedule.taskname}"/>
				<input type="text" id="taskSchedule-edit-taskList" name="taskSchedule.tasklistid" required="true" style="width:200px;"/>
			</p>
			<p>
				<label>任务组:</label>
				<input type="text" name="taskSchedule.team" value="${taskSchedule.team}" class="easyui-validatebox" required="true"/>
			</p>
			<p>
				<label>执行类型:</label>
				<select name="taskSchedule.type" style="width: 200px;">
					<option value="1" <c:if test="${taskSchedule.type=='1'}">selected="selected"</c:if>>单次执行</option>
					<option value="2" <c:if test="${taskSchedule.type=='2'}">selected="selected"</c:if>>循环执行</option>
				</select>
			</p>
			<p>
				<label>触发时间:</label>
				<input type="text" name="taskSchedule.triggertime" value="${taskSchedule.triggertime }"/>
			</p>
			<p>
				<label>是否预警</label>
				<select id="taskSchedule-isalert" name="taskSchedule.isalert">
					<option value="0" <c:if test="${taskSchedule.isalert==0}">selected</c:if> >否</option>
					<option value="1" <c:if test="${taskSchedule.isalert==1}">selected</c:if>>是</option>
				</select>
			</p>
			<p class="isshow">
				<label>预警地址:</label>
				<input type="text" name="taskSchedule.alerturl" id="taskSchedule-alerturl" value="${taskSchedule.alerturl }"/>
			</p>
			<p class="isshow">
				<label>指定角色:</label>
				<input type="text" name="taskSchedule.sendroleid" value="${taskSchedule.sendroleid }" id="taskSchedule-sendroleid-edit"/>
			</p>
			<p class="isshow">
				<label>指定用户:</label>
				<input type="text" name="taskSchedule.senduserid" value="${taskSchedule.senduserid }" id="taskSchedule-senduserid-edit" />
			</p>
	    </div>
    </form>
    <script type="text/javascript">
    	$(function(){
    		$("#taskSchedule-edit-taskList").combogrid({
           		 panelWidth:400,
           		 idField:'id',
           		 textField:'name',
           		 rownumbers:true,
           		 columns:[[  
					        {field:'id',title:'任务清单编号',width:80},  
					        {field:'name',title:'任务名称',width:100},
							{field:'classpath',title:'任务执行类',width:150},
							{field:'type',title:'类型',width:60,
							 	formatter:function(val){
					        		if(val=='1'){
					        			return '业务';
					        		}else if(val=='2'){
					        			return '系统';
					        		}
					        	}
							 }
					     ]],
           		 url:'system/task/getTaskListData.do',
           		 onSelect:function(rowIndex, rowData){
           		 	$("#taskSchedule-edit-taskname").attr("value",rowData.name);
           		 	$("#taskSchedule-edit-classpath").attr("value",rowData.classpath);
           		 },
           		 onLoadSuccess:function(){
           		 	$("#taskSchedule-edit-taskList").combogrid("setValue",${taskSchedule.tasklistid});
           		 }
           	});
           	$("#taskSchedule-form-edit").form({  
			    onSubmit: function(){  
			    	var flag = $(this).form('validate');
			    	if(flag==false){
			    		return false;
			    	}
					var isalert=$("#taskSchedule-isalert").val();
					if(isalert=='1'){
						var roleid=$("#taskSchedule-sendroleid-edit").widget('getValue');
						var userid=$("#taskSchedule-senduserid-edit").widget('getValue');
						if(roleid==''&&userid==''){
							$.messager.alert("提醒",'请选择指定人员或指定角色');
							return ;
						}
					}
			    	loading("提交中..");
			    },  
			    success:function(data){
			    	//表单提交完成后 隐藏提交等待页面
			    	loaded();
			    	var json = $.parseJSON(data);
			    	if(json.flag==true){
			    		$.messager.alert("提醒",'保存成功');
			    		$("#taskSchedule-dialog-edit").dialog('close',true);
		    			$("#taskSchedule-table").datagrid('reload');
			    	}else{
			    		$.messager.alert("提醒",'保存失败');
			    	}
			    }  
			}); 
			//保存
    		$("#taskSchedule-editTaskSchedule-save").click(function(){
    			$.messager.confirm("提醒", "是否保存修改任务计划?", function(r){
					if (r){
						$("#taskSchedule-form-edit").submit();
					}
				});
    		});
			$("#taskSchedule-sendroleid-edit").widget({
				referwid:'RL_T_AC_AUTHORITY',
				width:'200',
				singleSelect:false
			})
			$("#taskSchedule-senduserid-edit").widget({
				referwid:'RT_T_SYS_USER',
				width:'200',
				singleSelect:false
			})

			var value='${taskSchedule.isalert}';
			console.log(value);
			if(value=='0'){
				$("#taskSchedule-alerturl").val('');
				$("#taskSchedule-sendroleid-edit").widget('clear');
				$("#taskSchedule-senduserid-edit").widget('clear');
				$(".isshow").hide();
			}else if(value=='1'){
				$(".isshow").show();
			}

			$("#taskSchedule-isalert").change(function(){
				var value=$("#taskSchedule-isalert").val();
				if(value=='0'){
					$("#taskSchedule-alerturl").val('');
					$("#taskSchedule-sendroleid-edit").widget('clear');
					$("#taskSchedule-senduserid-edit").widget('clear');
					$(".isshow").hide();
				}else if(value=='1'){
					$(".isshow").show();
				}
			});
    	});
    </script>
  </body>
</html>
