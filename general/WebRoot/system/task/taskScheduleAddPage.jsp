<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>任务计划安排添加</title>

  </head>
  
  <body>
    <form action="system/task/addTaskSchedule.do" method="post" id="taskSchedule-form-add">
	   	<div class="pageContent">
			<p>
				<label>任务名称:</label>
				<input type="hidden" id="taskSchedule-classpath" name="taskSchedule.classpath"/>
				<input type="hidden" id="taskSchedule-taskname" name="taskSchedule.taskname"/>
				<input type="text" id="taskSchedule-taskList" name="taskSchedule.tasklistid" required="true" style="width:200px;"/>
				<input type="hidden" id="taskSchedule-triggertime" name="taskSchedule.triggertime"/>
			</p>
			<p>
				<label>任务组:</label>
				<input type="text" name="taskSchedule.team" class="easyui-validatebox" required="true"/>
			</p>
			<p>
				<label>执行类型:</label>
				<select id="taskSchedule-type" name="taskSchedule.type" style="width: 200px;">
					<option value="1">单次执行</option>
					<option value="2">循环执行</option>
				</select>
			</p>
			<p id="taskSchedule-type-one">
				<label>触发时间:</label>
				<input type="text" id="trigger-time-one" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',minDate:'%y-%M-%d'})"/>
			</p>
			<p class="taskSchedule-type-more" style="display: none;">
				<label>触发频率:</label>
				<select id="taskSchedule-trigger" style="width: 80px;">
					<option value="min">按分钟</option>
					<option value="h">按小时</option>
					<option value="d" selected="selected">按天</option>
					<option value="w">按周</option>
					<option value="m">按月</option>
				</select>
				<span id="taskSchedule-trigger-min" class="trigger-rate" style="display: none;">每<input id="trigger-min" type="text" style="width: 50px;" value="1"/>分钟</span>
				<span id="taskSchedule-trigger-hour" class="trigger-rate" style="display: none;">每<input id="trigger-hour" type="text" style="width: 50px;" value="1"/>小时</span>
				<span id="taskSchedule-trigger-day" class="trigger-rate">每<input id="trigger-day" type="text" style="width: 50px;" value="1"/>天</span>
				<span id="taskSchedule-trigger-week" class="trigger-rate" style="display: none;"><select id="trigger-weekday" class="easyui-combobox" data-options="multiple:true">
						<option value="2">周一</option>
						<option value="3">周二</option>
						<option value="4">周三</option>
						<option value="5">周四</option>
						<option value="6">周五</option>
						<option value="7">周六</option>
						<option value="1">周日</option>
					  </select>
				</span>
				<span id="taskSchedule-trigger-month" class="trigger-rate" style="display: none;">
					每<input id="trigger-month" type="text" style="width: 40px;" value="1"/>月
					<select id="trigger-month-day" style="width: 80px;">
						<option value="1">1号</option>
						<option value="15">15号</option>
						<option value="L">最后一天</option>
					</select>
				</span>
			</p>
			<p class="taskSchedule-type-more" style="display: none;">
				<label>开始时间:</label>
				<input id="trigger-begin" type="text" onfocus="WdatePicker({dateFmt:'HH:mm'})"/>
			</p>
			<p>
				<label>是否预警</label>
				<select id="taskSchedule-isalert" name="taskSchedule.isalert">
					<option value="0" selected>否</option>
					<option value="1">是</option>
				</select>
			</p>
			<p class="isshow">
				<label>预警地址:</label>
				<input type="text" name="taskSchedule.alerturl" id="taskSchedule-alerturl" class="easyui-validatebox"/>
			</p>
			<p class="isshow">
				<label>指定角色:</label>
				<input type="text" name="taskSchedule.sendroleid" id="taskSchedule-sendroleid" />
			</p>
			<p class="isshow">
				<label>指定用户:</label>
				<input type="text" name="taskSchedule.senduserid" id="taskSchedule-senduserid"/>
			</p>
	    </div>
    </form>
    <script type="text/javascript">
    	$(function(){
    		$("#taskSchedule-taskList").combogrid({
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
           		 	$("#taskSchedule-taskname").attr("value",rowData.name);
           		 	$("#taskSchedule-classpath").attr("value",rowData.classpath);
           		 }
           	});
           	$("#taskSchedule-form-add").form({  
			    onSubmit: function(){  
			    	var flag = $(this).form('validate');
			    	if(flag==false){
			    		return false;
			    	}
					var isalert=$("#taskSchedule-isalert").val();
					if(isalert=='1'){
						var roleid=$("#taskSchedule-sendroleid").widget('getValue');
						var userid=$("#taskSchedule-senduserid").widget('getValue');
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
			    		$("#taskSchedule-dialog-add").dialog('close',true);
		    			$("#taskSchedule-table").datagrid('reload');
			    	}else{
			    		$.messager.alert("提醒",'保存失败');
			    	}
			    }  
			}); 
			//保存
    		$("#taskSchedule-addTaskSchedule-save").click(function(){
    			//获取触发时间quartz表达式
    			var type = $("#taskSchedule-type").val();
    			var cronExpression = "";
    			if(type=='1'){
    				var triggertime = $("#trigger-time-one").val();
    				var year = triggertime.substr(0,4);
    				var monthStr = triggertime.substr(5,2);
    				var dayStr = triggertime.substr(8,2);
    				var hourStr = triggertime.substr(11,2);
    				var minStr = triggertime.substr(14,2);
    				
    				var hour = Number(hourStr);
   					var min = Number(minStr);
   					var month = Number(monthStr);
   					var day = Number(dayStr);
   					
    				var con = '0 '+min+' '+hour+' '+day+' '+month+' ? '+year;
    				cronExpression = con;
    			}else{
    				var triggerType = $("#taskSchedule-trigger").val();
    				var begin = $("#trigger-begin").val();
   					var hourStr = begin.substr(0,2);
   					var minStr = begin.substr(3,2);
   					
   					var hour = Number(hourStr);
   					var min = Number(minStr);
   					if(triggerType=='min'){
   						var val = $("#trigger-min").val();
    					var con = '0 '+min+'/'+val+' '+hour+'/1 * * ? ';
    					cronExpression = con;
   					}else if(triggerType=='h'){
    					var val = $("#trigger-hour").val();
    					var con = '0 '+min+' '+hour+'/'+val+' * * ? ';
    					cronExpression = con;
    				}else if(triggerType=='d'){
    					var val = $("#trigger-day").val();
    					var con = '0 '+min+' '+hour+' 1/'+val+' * ? ';
    					cronExpression = con;
    				}else if(triggerType=='w'){
    					var val = $("#trigger-weekday").combobox("getValues");
    					var weekday = "";
    					for(var i=0;i<val.length;i++){
    						if(weekday==""){
    							weekday = val[i];
    						}else{
    							weekday += ","+val[i];
    						}
    					}
    					var con = '0 '+min+' '+hour+' ? * '+weekday+' * ';
    					cronExpression = con;
    				}else if(triggerType=='m'){
    					var month = $("#trigger-month").val();
    					var monthday = $("#trigger-month-day").val();
    					var con = '0 '+min+' '+hour+' '+monthday+' 1/'+month+' ? * ';
    					cronExpression = con;
    				}
    			}
    			$("#taskSchedule-triggertime").attr("value",cronExpression);

    			$.messager.confirm("提醒", "是否保存任务清单?", function(r){
					if (r){
						$("#taskSchedule-form-add").submit();
					}
				});
    		});
    		//执行类型变更
    		$("#taskSchedule-type").change(function(){
    			var type = $(this).val();
    			if(type=='2'){
    				$(".taskSchedule-type-more").show();
    				$("#taskSchedule-type-one").hide();
    			}else if(type=='1'){
    				$("#taskSchedule-type-one").show();
    				$(".taskSchedule-type-more").hide();
    			}
    		});
    		//触发频率变更
    		$("#taskSchedule-trigger").change(function(){
    			var type = $(this).val();
    			if(type=='min'){
    				$(".trigger-rate").hide();
    				$("#taskSchedule-trigger-min").show();
    			}else if(type=='h'){
    				$(".trigger-rate").hide();
    				$("#taskSchedule-trigger-hour").show();
    			}else if(type=='d'){
    				$(".trigger-rate").hide();
    				$("#taskSchedule-trigger-day").show();
    			}else if(type=='w'){
    				$(".trigger-rate").hide();
    				$("#taskSchedule-trigger-week").show();
    			}else if(type=='m'){
    				$(".trigger-rate").hide();
    				$("#taskSchedule-trigger-month").show();
    			}
    		});
			$("#taskSchedule-sendroleid").widget({
				referwid:'RL_T_AC_AUTHORITY',
				width:'200',
				singleSelect:false
			})
			$("#taskSchedule-senduserid").widget({
				referwid:'RT_T_SYS_USER',
				width:'200',
				singleSelect:false
			})
			var value=$("#taskSchedule-isalert").val();
			if(value=='0'){
				$(".isshow").hide();
			}else if(value=='1'){
				$(".isshow").show();
			}
			$("#taskSchedule-isalert").change(function(){
				var value=$("#taskSchedule-isalert").val();
				if(value=='0'){
					$("#taskSchedule-alerturl").val('');
					$("#taskSchedule-sendroleid").widget('clear');
					$("#taskSchedule-senduserid").widget('clear');
					$(".isshow").hide();
				}else if(value=='1'){
					$(".isshow").show();
				}
			});
    	});
    </script>
  </body>
</html>
