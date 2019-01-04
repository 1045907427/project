<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>流程日志查询</title>
    <%@include file="/include.jsp" %>
	<style type="text/css">
		.pageContent label {
			float: left;
			width: 120px;
			padding: 0 5px;
			line-height: 21px;
			font-size: 12px;
			text-align: right;
		}
	</style>
</head>
<body>
	<div class="easyui-panel" title="流程日志 &nbsp;&nbsp; <a href='javascript:;' id='activiti-back-logQueryPage'>返回</a>" id="activiti-listPanel-logQueryPage" data-options="fit:true,closed:true,border:false">
		<table id="activiti-table-logQueryPage"></table>
	</div>
	<div class="easyui-panel" title="流程日志查询" id="activiti-queryPanel-logQueryPage" data-options="fit:true,border:false">
		<form action="" method="post" id="activiti-form-logQueryPage">
			<div style="width:500px;margin:50px auto;border:1px solid #aaaaaa;padding:15px;">
				<div class="pageContent">
					<p>
						<label>选择流程：</label>
						<input type="text" id="activiti-selectProcess-logQueryPage" name="key" autocomplete="off"/>
						<input type="hidden" id="activiti-key-logQueryPage" name="definitionkey" />
					</p>
					<p>
						<label>流程号：</label>
						<input name="processid" type="text" autocomplete="off"/>
					</p>
					<p>
						<label>工作名称：</label>
						<input name="title" type="text" autocomplete="off"/>
					</p>
					<p>
						<label>发起人：</label>
						<input id="activiti-assignee-logQueryPage" name="applyuserid" autocomplete="off"/>
					</p>
					<p>
						<label>办理人：</label>
						<input id="activiti-handle-logQueryPage" name="assigneeid" autocomplete="off"/>
					</p>
					<p>
						<label>IP地址：</label>
						<input name="ip" type="text" autocomplete="off"/>
					</p>
					<p style="width:auto;">
						<label>发生时间范围：</label>
						<input style="width:140px" name="time1" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd HH:mm:ss'})" class="Wdate len150" autocomplete="off"/> 到 <input style="width: 140px" name="time2" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd HH:mm:ss'})" class="Wdate" autocomplete="off"/>
					</p>
					<p>
						<label>日志类别：</label>
						<select name="type" style="width:200px;">
							<option value="">所有日志</option>
							<option value="1">转交日志</option>
							<option value="2">修改日志</option>
						</select>
					</p>
				</div>
				<div class="buttondiv">
					<a href="javascript:;" id="activiti-query-logQueryPage" class="button-qr">查询</a>
					<a href="javaScript:;" id="activiti-reset-logQueryPage" class="button-qr">重置</a>
				 </div>
			</div>
		</form>
	</div>
	<script type="text/javascript">
		$(function(){
			$("#activiti-assignee-logQueryPage").widget({
    			referwid:'RT_T_SYS_USER',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:true
    		});	
			$("#activiti-handle-logQueryPage").widget({
				referwid:'RT_T_SYS_USER',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:true
			});
			var queryLogListColJson = $("#activiti-table-logQueryPage").createGridColumnLoad({
				name :'t_act_log',
				commonCol : [[
							  {field:'processid',title:'OA编号',/*width:60*/formatter: function(value, row, index) {return value + '&nbsp;';}},
							  {field:'title',title:'工作名称',/*width:300,*/formatter: function(value, row, index) {
							  	
							  	if(row.instanceid == null || row.instanceid == '') {
							  		return "<a href='javascript:;' onclick=\"return false;\" title=\"该工作已不存在\">"+value+"</a>" + '&nbsp;';;
							  	}
							  	
							  	return "<a href='javascript:;' onclick=\"viewDetail('"+ row.taskid +"','"+ row.taskkey +"','"+ row.instanceid +"','"+ row.definitionkey +"')\">"+value+"</a>" + '&nbsp;';;
							  	//return '<a href="">' + value + '</a>'
							  }},
							  {field:'taskname',title:'步骤'/*,width:150*/,formatter: function(value, row, index) {return value + '&nbsp;';}},
							  {field:'applyusername',title:'发起人'/*,width:80*/,formatter: function(value, row, index) {return value + '&nbsp;';}},
							  {field:'assigneename',title:'处理人'/*,width:80*/,formatter: function(value, row, index) {return value + '&nbsp;';}},
							  {field:'ip',title:'IP地址'/*,width:120*/,formatter: function(value, row, index) {return value + '&nbsp;';}},
							  {field:'content',title:'内容'/*,width:250*/,formatter: function(value, row, index) {return value + '&nbsp;';}},
							  {field:'adddate',title:'日志时间'/*, width:110*/,formatter: function(value, row, index) {return value + '&nbsp;';}},
							  {field:'device',title:'终端'/*,width:250*/,formatter: function(value, row, index) {
								  if(value == '1') {
									  return '网页';
								  } else if(value == '2') {
									  return '手机';
								  }
							  }}
							]]
			});
	  	 	$("#activiti-table-logQueryPage").datagrid({ 
	  	 		authority:queryLogListColJson,
	  	 		frozenColumns: queryLogListColJson.frozen,
				columns:queryLogListColJson.common,
	  	 		fit:true,
	  	 		method:'post',
	  	 		rownumbers:true,
	  	 		pagination:true,
	  	 		idField:'id',
	  	 		singleSelect:true
			}).datagrid("columnMoving");
			
			//回车事件
			controlQueryAndResetByKey("activiti-query-logQueryPage","activiti-reset-logQueryPage");
			
	  	 	$("#activiti-query-logQueryPage").click(function(){
	       		var queryJSON = $("#activiti-form-logQueryPage").serializeJSON();
	       		$("#activiti-listPanel-logQueryPage").panel('open');
	       		$("#activiti-queryPanel-logQueryPage").panel('close');
	       		$("#activiti-table-logQueryPage").datagrid({
		   			url:'act/getLogQueryList.do',
		   			queryParams:queryJSON
	       		});
			});
	  	 	$("#activiti-reset-logQueryPage").click(function(){
                $("#activiti-assignee-logQueryPage").widget('clear');
                $("#activiti-handle-logQueryPage").widget('clear');
                $("#activiti-selectProcess-logQueryPage").widget('clear');
				$("#activiti-form-logQueryPage")[0].reset();
			});
	  	 	$("#activiti-back-logQueryPage").click(function(){
	  	 		$("#activiti-listPanel-logQueryPage").panel('close');
	       		$("#activiti-queryPanel-logQueryPage").panel('open');
	  	 	});
	  	 	$("#activiti-selectProcess-logQueryPage").widget({
    			referwid:'RT_T_ACT_DEFINATION',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:true,
    			onChecked:function(data){
    				$("#activiti-key-logQueryPage").val(data.id);
    			},
    			onClear:function(){
    				$("#activiti-key-logQueryPage").val("");
    			}
    		});
		});
		function viewDetail(taskid, taskkey, instanceid, definitionkey){
			top.addOrUpdateTab("act/workViewPage.do?taskid="+ taskid+ "&taskkey="+ taskkey +"&instanceid="+ instanceid, "工作查看");
		}
	</script>
</body>
</html>