<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>任务调度计划</title>
	 <%@include file="/include.jsp" %>  
  </head>
  
  <body>
    <table id="taskSchedule-table"></table>
    <div id="taskSchedule-tools" style="padding:0px;height:auto;">
        <div class="buttonBG">
            <a href="javaScript:void(0);" id="taskSchedule-add" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'">新增</a>
            <a href="javaScript:void(0);" id="taskSchedule-edit" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-edit'">修改</a>
            <a href="javaScript:void(0);" id="taskSchedule-open" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-open'">启动</a>
            <a href="javaScript:void(0);" id="taskSchedule-pause" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-pause'">暂停</a>
            <a href="javaScript:void(0);" id="taskSchedule-cancel" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-close'">停止</a>
            <a href="javaScript:void(0);" id="taskSchedule-remove" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-delete'">删除</a>
            <a href="javaScript:void(0);" id="taskSchedule-viewLogs" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-view'">查看日志</a>
            <a href="javaScript:void(0);" id="taskSchedule-run" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-open'">立即执行</a>
            <br/>
        </div>
    	<form action="" id="taskSchedule-form-query" method="post" style="padding: 5px">
            任务名称:<input name="taskname" style="width:200px">
            类型:<select name="type" style="width:200px;">
				<option></option>
				<option value="1">单次执行</option>
				<option value="2">循环执行</option>
			</select>
			状态:<select name="state" style="width: 100px;">
					<option value="9"></option>
					<option value="1" selected="selected">启动</option>
					<option value="2">暂停</option>
					<option value="3">停止</option>
					<option value="0">执行完毕</option>
				</select>
			<a href="javaScript:void(0);" id="taskSchedule-queay" class="button-qr">查询</a>
			<a href="javaScript:void(0);" id="taskSchedule-reload" class="button-qr">重置</a>
		</form>
    </div>
    <div id="taskSchedule-dialog-add"></div>
    <div id="taskSchedule-dialog-edit"></div>
    <div id="taskSchedule-dialog-logs"></div>
    <script type="text/javascript">
    	$(function(){
    		$('#taskSchedule-table').datagrid({ 
				columns:[[
                     {field:'ck',checkbox:true},
					 {field:'taskid',title:'任务编号',width:120},
					 {field:'team',title:'任务组',width:100},
					 {field:'taskname',title:'任务名称',width:200},
					 {field:'classpath',title:'执行类',width:200,sortable:true},
					 {field:'type',title:'类型',width:100,sortable:true,
					 	formatter:function(val){
					 		if(val=='1'){
					 			return "单次执行";
					 		}else if(val=='2'){
					 			return "循环执行";
					 		}
					 	}
					 },
					 {field:'triggertime',title:'触发时间',width:120},
					 {field:'state',title:'状态',width:60,sortable:true,
					 	formatter:function(val){
					 		if(val=='1'){
					 			return "启动";
					 		}else if(val=='2'){
					 			return "暂停";
					 		}else if(val=='3'){
					 			return "停止";
					 		}else if(val=='0'){
					 			return "执行完毕";
					 		}
					 	}
					 },
					 {field:'times',title:'执行次数',width:60,sortable:true},
					 {field:'addtime',title:'任务添加时间',width:120,sortable:true},
					 {field:'roottaskid',title:'根级任务编号',width:120,
						 styler:function(value,rowData,rowIndex){
							 if(value==rowData.taskid){
								 return "background-color:#FFC0CB;font-weight:bold;";
							 }
						 }
					 }
				]],
	  	 		fit:true,
	  	 		method:'post',
	  	 		rownumbers:true,
	  	 		pagination:true,
	  	 		idField:'taskid',
	  	 		singleSelect:false,
                checkOnSelect:true,
                selectOnCheck:true,
	  	 		sortOrder:'desc',
	  	 		sortName:'addtime',
				toolbar:'#taskSchedule-tools',
				url:'system/task/showTaskScheduleList.do',
				onDblClickRow:function(rowIndex, rowData){
					$('#taskSchedule-dialog-logs').dialog({  
					    title: '任务执行日志',  
					    width: 500,  
					    height: 300,  
					    closed: true,  
					    cache: false,  
					    href: 'system/task/showTaskScheduleLogsPage.do?taskid='+rowData.taskid+'&team='+rowData.team,  
					    modal: true
					});
					$('#taskSchedule-dialog-logs').dialog("open");
				}
			});
			//添加任务计划安排
			$("#taskSchedule-add").click(function(){
				$('#taskSchedule-dialog-add').dialog({  
				    title: '任务计划新增',  
				    width: 450,  
				    height: 360,
				    closed: true,  
				    cache: false,  
				    href: 'system/task/showTaskScheduleAddPage.do',  
				    modal: true,
				    buttons:[
				    	{  
				    		id:'taskSchedule-addTaskSchedule-save',
	                    	text:'保存',  
		                    iconCls:'button-save',
		                    plain:true
		                }
				    ]
				});
				$('#taskSchedule-dialog-add').dialog("open");
			});
			//启动任务计划
			$("#taskSchedule-open").click(function(){
				var rows = $('#taskSchedule-table').datagrid('getChecked');
                if(rows.length == 0){
                    $.messager.alert("提醒","请选中记录。");
                    return false;
                }
                var ids = "";
                for(var i=0; i<rows.length; i++){
                    ids += rows[i].taskid+"@"+ rows[i].team + ',';
                    if(rows[i].state=='1'){
                        $.messager.alert("提醒","请选择停止或暂停状态的任务！");
                        return false;
                    }
                }
		    	$.messager.confirm("提醒", "是否启动任务计划?", function(r){
					if (r){
						$.ajax({   
				            url :'system/task/startTaskSchedule.do',
				            type:'post',
                            data:{ids:ids},
				            dataType:'json',
				            success:function(json){
				            	if(json.flag==true){
				            		$.messager.alert("提醒","启动成功！");
				            		$('#taskSchedule-table').datagrid('reload');
				            	}else{
				            		$.messager.alert("提醒","启动失败！");
				            	}
				            }
				        });
					}
				});
			});
			//暂停任务计划
			$("#taskSchedule-pause").click(function(){
                var rows = $('#taskSchedule-table').datagrid('getChecked');
                if(rows.length == 0){
                    $.messager.alert("提醒","请选中记录。");
                    return false;
                }
                var ids = "";
                for(var i=0; i<rows.length; i++){
                    ids += rows[i].taskid+"@"+ rows[i].team + ',';
                    if(rows[i].state!='1'){
                        $.messager.alert("提醒","请选择启动状态的任务！");
                        return false;
                    }
                }
		    	$.messager.confirm("提醒", "是否暂停任务计划?", function(r){
					if (r){
						$.ajax({   
				            url :'system/task/pauseTaskSchedule.do',
				            type:'post',
				            dataType:'json',
                            data:{ids:ids},
				            success:function(json){
				            	if(json.flag==true){
				            		$.messager.alert("提醒","暂停成功！"+json.msg);
				            		$('#taskSchedule-table').datagrid('reload');
				            	}else{
				            		$.messager.alert("提醒","暂停失败！");
				            	}
				            }
				        });
					}
				});
			});
			//停止任务计划
			$("#taskSchedule-cancel").click(function(){
                var rows = $('#taskSchedule-table').datagrid('getChecked');
                if(rows.length == 0){
                    $.messager.alert("提醒","请选中记录。");
                    return false;
                }
                var ids = "";
                for(var i=0; i<rows.length; i++){
                    ids += rows[i].taskid+"@"+ rows[i].team + ',';
                    if(rows[i].state!='1'){
                        $.messager.alert("提醒","请选择启动状态的任务！");
                        return false;
                    }
                }
		    	$.messager.confirm("提醒", "是否停止任务计划?", function(r){
					if (r){
						$.ajax({   
				            url :'system/task/cancelTaskSchedule.do',
                            data:{ids:ids},
				            type:'post',
				            dataType:'json',
				            success:function(json){
				            	if(json.flag==true){
				            		$.messager.alert("提醒","停止成功！"+json.msg);
				            		$('#taskSchedule-table').datagrid('reload');
				            	}else{
				            		$.messager.alert("提醒","停止失败！");
				            	}
				            }
				        });
					}
				});
			});
			//删除
			$("#taskSchedule-remove").click(function(){
                var rows = $('#taskSchedule-table').datagrid('getChecked');
                if(rows.length == 0){
                    $.messager.alert("提醒","请选中记录。");
                    return false;
                }
                var ids = "";
                for(var i=0; i<rows.length; i++){
                    ids += rows[i].taskid+"@"+ rows[i].team + ',';
                }
		    	$.messager.confirm("提醒", "是否删除任务计划?", function(r){
					if (r){
						$.ajax({   
				            url :'system/task/deleteTaskSchedule.do',
                            data:{ids:ids},
				            type:'post',
				            dataType:'json',
				            success:function(json){
				            	if(json.flag==true){
				            		$.messager.alert("提醒","删除成功！"+json.msg);
				            		$('#taskSchedule-table').datagrid('reload');
				            	}else{
				            		$.messager.alert("提醒","删除失败！"+json.msg);
				            	}
				            }
				        });
					}
				});
			});
			//显示任务计划修改页面
			$("#taskSchedule-edit").click(function(){
				var taskSchedule = $("#taskSchedule-table").datagrid('getSelected');
		    	if(taskSchedule==null){
		    		$.messager.alert("提醒","请选择任务计划！");
		    		return false;
		    	}
		    	if(taskSchedule.state!='3'){
		    		$.messager.alert("提醒","只有停止中的任务才能修改!");
		    		return false;
		    	}
		    	$('#taskSchedule-dialog-edit').dialog({  
				    title: '任务计划修改',  
				    width: 450,  
				    height: 360,
				    closed: true,  
				    cache: false,  
				    href: 'system/task/showTaskScheduleEditPage.do?taskid='+taskSchedule.taskid+'&team='+taskSchedule.team,  
				    modal: true,
				    buttons:[
				    	{  
				    		id:'taskSchedule-editTaskSchedule-save',
	                    	text:'保存',  
		                    iconCls:'button-save',
		                    plain:true
		                }
				    ]
				});
				$('#taskSchedule-dialog-edit').dialog("open");
			});
			//查看日志
			$("#taskSchedule-viewLogs").click(function(){
				var taskSchedule = $("#taskSchedule-table").datagrid('getSelected');
		    	if(taskSchedule==null){
		    		$.messager.alert("提醒","请选择任务计划！");
		    		return false;
		    	}
		    	$('#taskSchedule-dialog-logs').dialog({  
				    title: '任务执行日志',  
				    width: 500,  
				    height: 300,  
				    closed: true,  
				    cache: false,  
				    href: 'system/task/showTaskScheduleLogsPage.do?taskid='+taskSchedule.taskid+'&team='+taskSchedule.team,  
				    modal: true
				});
				$('#taskSchedule-dialog-logs').dialog("open");
			});
			$("#taskSchedule-run").click(function(){
                var rows = $('#taskSchedule-table').datagrid('getChecked');
                if(rows.length >1 ){
                    $.messager.alert("提醒","请选中一条记录。");
                    return false;
                }
                var taskSchedule = $("#taskSchedule-table").datagrid('getSelected');
                if(taskSchedule==null){
                    $.messager.alert("提醒","请选择任务计划！");
                    return false;
                }
                if(taskSchedule.state!='1' && taskSchedule.state!='2'){
                    $.messager.alert("提醒","该任务计划已停止或者执行完毕!");
                    return false;
                }
                $.messager.confirm("提醒", "是否请求任务计划立即执行?", function(r){
                    if (r){
                        $.ajax({
                            url :'system/task/runTaskSchedule.do?taskid='+taskSchedule.taskid+'&team='+taskSchedule.team,
                            type:'post',
                            dataType:'json',
                            success:function(json){
                                if(json.flag==true){
                                    $.messager.alert("提醒","请求成功！");
                                    $('#taskSchedule-table').datagrid('reload');
                                }else{
                                    $.messager.alert("提醒","请求失败！");
                                }
                            }
                        });
                    }
                });
            });
			$("#taskSchedule-queay").click(function(){
				//把form表单的name序列化成JSON对象
	       		var queryJSON = $("#taskSchedule-form-query").serializeJSON();
	       		$("#taskSchedule-table").datagrid("load",queryJSON);
			});
			$("#taskSchedule-reload").click(function(){
				//把form表单的name序列化成JSON对象
	       		$("#taskSchedule-form-query")[0].reset();
	       		$("#taskSchedule-table").datagrid("load",{});
			});
    	});
    </script>
  </body>
</html>
