<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>任务列表</title>
    <%@include file="/include.jsp" %>  
  </head>
  
  <body>
    <table id="tasklist-table"></table>
    <div id="tasklist-tools" style="padding:0px;height:auto;">
        <div class="buttonBG">
            <a href="javaScript:void(0);" id="tasklist-add" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'">新增</a>
            <a href="javaScript:void(0);" id="tasklist-edit" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-edit'">修改</a>
            <a href="javaScript:void(0);" id="tasklist-delete" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-delete'">删除</a>
            <a href="javaScript:void(0);" id="tasklist-open" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-open'">启用</a>
            <a href="javaScript:void(0);" id="tasklist-close" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-close'">禁用</a>
            <br/>
        </div>
    	<form action="" id="tasklist-form-query" method="post" style="padding: 5px">
			任务名称:<input name="name" style="width:200px">
			类型:<select name="type" style="width:200px;">
				<option></option>
				<option value="1">业务</option>
				<option value="2">系统</option>
			</select>
			<a href="javaScript:void(0);" id="tasklist-queay" class="button-qr">查询</a>
			<a href="javaScript:void(0);" id="tasklist-reload" class="button-qr">重置</a>
		</form>
    </div>
    <div id="tasklist-dialog-add"></div>
    <div id="tasklist-dialog-edit"></div>
    <script type="text/javascript">
		$(function(){
			$('#tasklist-table').datagrid({ 
				columns:[[
							 {field:'name',title:'任务名称',width:150},
							 {field:'classpath',title:'任务执行类',width:200},
							 {field:'type',title:'类型',width:60,
							 	formatter:function(val){
					        		if(val=='1'){
					        			return '业务';
					        		}else if(val=='2'){
					        			return '系统';
					        		}
					        	}
							 },
							 {field:'state',title:'状态',width:60,
							 	formatter:function(val){
					        		if(val=='0'){
					        			return '禁用';
					        		}else if(val=='1'){
					        			return '启用';
					        		}else if(val=='2'){
					        			return '保存';
					        		}else if(val=='3'){
					        			return '暂存';
					        		}else if(val=='4'){
					        			return '新增';
					        		}
					        	}
							 },
							 {field:'addtime',title:'添加时间',width:120}
						]],
	  	 		fit:true,
	  	 		method:'post',
	  	 		title:'',
	  	 		rownumbers:true,
	  	 		pagination:true,
	  	 		idField:'id',
	  	 		singleSelect:true,
				toolbar:'#tasklist-tools',
				url:'system/task/showTaskList.do'
			});
			//显示任务添加页面
			$("#tasklist-add").click(function(){
				$('#tasklist-dialog-add').dialog({  
				    title: '任务清单新增',  
				    width: 450,  
				    height: 300,  
				    closed: true,  
				    cache: false,  
				    href: 'system/task/showTaskListAddPage.do',  
				    modal: true,
				    buttons:[
				    	{  
				    		id:'tasklist-save-addtask',
	                    	text:'保存',  
		                    iconCls:'button-save',
		                    plain:true
		                }
				    ]
				});
				$('#tasklist-dialog-add').dialog("open");
			});
			//显示任务清单修改页面
			$("#tasklist-edit").click(function(){
				var taskList = $("#tasklist-table").datagrid('getSelected');
		    	if(taskList==null){
		    		$.messager.alert("提醒","请选择任务清单！");
		    		return false;
		    	}
				$('#tasklist-dialog-edit').dialog({  
				    title: '任务清单修改',  
				    width: 450,  
				    height: 300,  
				    closed: true,  
				    cache: false,  
				    href: 'system/task/showTaskListEditPage.do?id='+taskList.id,  
				    modal: true,
				    buttons:[
				    	{  
				    		id:'tasklist-save-edittask',
	                    	text:'保存',  
		                    iconCls:'button-save',
		                    plain:true
		                }
				    ]
				});
				$('#tasklist-dialog-edit').dialog("open");
			});
			//任务清单删除
			$("#tasklist-delete").click(function(){
				var taskList = $("#tasklist-table").datagrid('getSelected');
		    	if(taskList==null){
		    		$.messager.alert("提醒","请选择任务清单！");
		    		return false;
		    	}
		    	$.messager.confirm("提醒", "是否删除任务清单?", function(r){
					if (r){
						$.ajax({   
				            url :'system/task/deleteTaskList.do?id='+taskList.id,
				            type:'post',
				            dataType:'json',
				            success:function(json){
				            	if(json.flag==true){
				            		$.messager.alert("提醒","删除成功！");
				            		$('#tasklist-table').datagrid("clearSelections");
				            		$('#tasklist-table').datagrid('reload');
				            	}else{
				            		$.messager.alert("提醒","删除失败！");
				            	}
				            }
				        });
					}
				});
			});
			//启用任务清单
			$("#tasklist-open").click(function(){
				var taskList = $("#tasklist-table").datagrid('getSelected');
		    	if(taskList==null){
		    		$.messager.alert("提醒","请选择任务清单！");
		    		return false;
		    	}
		    	if(taskList.state!='0' && taskList.state!='2'){
		    		$.messager.alert("提醒","只有处于保存与禁用状态的数据才能启用");
		    		return false;
		    	}
		    	$.messager.confirm("提醒", "是否启用任务清单?", function(r){
					if (r){
						$.ajax({   
				            url :'system/task/openTaskList.do?id='+taskList.id,
				            type:'post',
				            dataType:'json',
				            success:function(json){
				            	if(json.flag==true){
				            		$.messager.alert("提醒","启用成功！");
				            		$('#tasklist-table').datagrid('reload');
				            	}else{
				            		$.messager.alert("提醒","启用失败！");
				            	}
				            }
				        });
					}
				});
			});
			//禁用任务清单
			$("#tasklist-close").click(function(){
				var taskList = $("#tasklist-table").datagrid('getSelected');
		    	if(taskList==null){
		    		$.messager.alert("提醒","请选择任务清单！");
		    		return false;
		    	}
		    	if(taskList.state!='1'){
		    		$.messager.alert("提醒","只有处于启用状态的数据才能禁用");
		    		return false;
		    	}
		    	$.messager.confirm("提醒", "是否禁用任务清单?", function(r){
					if (r){
						$.ajax({   
				            url :'system/task/closeTaskList.do?id='+taskList.id,
				            type:'post',
				            dataType:'json',
				            success:function(json){
				            	if(json.flag==true){
				            		$.messager.alert("提醒","禁用成功！");
				            		$('#tasklist-table').datagrid('reload');
				            	}else{
				            		$.messager.alert("提醒","禁用失败！");
				            	}
				            }
				        });
					}
				});
			});
			
			//回车事件
			controlQueryAndResetByKey("tasklist-queay","tasklist-reload");
			
			//查询
			$("#tasklist-queay").click(function(){
	       		var queryJSON = $("#tasklist-form-query").serializeJSON();
	       		$("#tasklist-table").datagrid("load",queryJSON);
			});
			//重置
			$("#tasklist-reload").click(function(){
				$("#tasklist-form-query")[0].reset();
	       		$("#tasklist-table").datagrid("load",{});
			});
		});
    </script>
  </body>
</html>
