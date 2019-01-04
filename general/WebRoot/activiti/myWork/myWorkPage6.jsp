<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
	  <title>我的工作-未接收</title>
	  <%@include file="/include.jsp" %>
	  <link rel="stylesheet" href="activiti/css/oastyle.css?v_=20170609" type="text/css">
  </head>
  
  <body>
	<div class="easyui-panel" data-options="fit:true,border:false">
		<div id="activiti-datagrid-myWorkPage"></div>
		<div id="activiti-form-myWorkPage" style="padding:8px;">
		  	<form>
		  		<input type="hidden" value="6" name="type" />
		  		<table>
		  			<tr>
		  				<td class="len40">OA号：</td>
                        <td><input type="text" name="id" class="len100"/></td>
		  				<td class="len70 right">流程名称：</td>
		  				<td style="width:160px;"><input type="text" class="150" id="activiti-definition-myWorkPage" name="definitionkey" value="${param.definitionkey }"/></td>
		  				<td class="len40">标题：</td>
                        <td style="width:210px;"><input type="text" name="title" style="width:200px;" /></td>
						<td >
							<a href="javascript:;" id="activiti-query-myWorkPage" class="button-qr">查询</a>
							<a href="javaScript:;" id="activiti-resetQueay-myWorkPage" class="button-qr">重置</a>
						</td>
		  			</tr>
		  		</table>
		  	</form>
	  	</div>
	</div>
	<div id="activiti-dialog-myWorkPage"></div>
	<script type="text/javascript">
		$(function(){
			$("#activiti-datagrid-myWorkPage").datagrid({
				columns:[[
					{field:'id', title:'OA号', width:50},
					{field:'title', title:'标题', width:330,
						formatter: function(value,row,index){
							return("<a href='javascript:;' class='oatitle' onclick=\"handleDetail('"+ row.taskid +"','"+ row.taskkey +"','"+ row.instanceid +"','"+ row.definitionkey +"')\">" + value + "</a>");
						}
					},
					{field:'definitionname', title:'流程名称', width:170},
					{field:'applyusername', title:'创建人', width:60},
					{field:'applydate', title:'创建时间', width:110},
					{field:'handtime', title:'转交时间', width:110, /*sortable: true,*/
						formatter: function(value, row, index) {

                            value = value + '';
							if(value == '0000000000000000000') {
								return '';
							}
							
							return value.substring(0, 16);
						}
					},
					{field:'taskname', title:'当前节点', width:120},
					{field:'isend', title:'', width:70, align:'center',
						formatter: function(value,row,index){
							
							var html = new Array();
							if(row.assignee == '${user.userid }' || row.sign) {
							
								html.push("<a href='javascript:;' class='oaoperate' onclick=\"handleDetail('"+ row.taskid +"','"+ row.taskkey +"','"+ row.instanceid +"','"+ row.definitionkey +"')\">主办</a>");
							}
							if(row.applyuserid == '${user.userid }' && !row.sign) {

								html.push("<a href='javascript:;' class='oaoperate' onclick=\"deleteProcess('" + row.id + "')\">删除</a>");
							}
							
							return html.join('<span style="width: 10px;">　</span>');
						}
					}
				]],
				queryParams:{type:'6', definitionkey: '${param.definitionkey }'},
				fit:true,
				border:false,
				method:'post',
				rownumbers:true,
				pagination:true,
		 		singleSelect:true,
				toolbar:'#activiti-form-myWorkPage',
				url:'act/getWorkList.do',
                pageSize: 100
			});
			
			$("#activiti-definition-myWorkPage").widget({
    			referwid:'RT_T_ACT_DEFINATION',
    			singleSelect:true,
    			width:145,
    			onlyLeafCheck:true,
    			onChecked:function(data){
    				$("#activiti-key-myWorkPage").val(data.unkey);
    			},
    			onClear:function(){
    				$("#activiti-key-myWorkPage").val("");
    			}
    		});
			$("#activiti-query-myWorkPage").click(function(){
				var queryJSON = $("#activiti-form-myWorkPage form").serializeJSON();
	       		$("#activiti-datagrid-myWorkPage").datagrid('load', queryJSON);
			});

			// 重置
			$("#activiti-resetQueay-myWorkPage").click(function(){
				$("#activiti-definition-myWorkPage").widget('clear'); // 3284 瑞家&通用版：重置有问题
				$(':input','#activiti-form-myWorkPage').not(':button,:submit,:reset,:hidden').val('');
				$("#activiti-datagrid-myWorkPage").datagrid('load', {type:'6'});
			});
			$("#activiti-dialog-myWorkPage").dialog({
				title:'直接审批',
				width:400,
				height:200,
				cache:false,
				modal:true,
				closed:true
			});
		});
		function handleDetail(taskid, taskkey, instanceid, definitionkey){
			top.addOrUpdateTab("act/workHandlePage.do?taskid="+ taskid+ "&taskkey="+ taskkey +"&instanceid="+ instanceid, "处理工作");
		}
		function viewDetail(taskid, taskkey, instanceid, definitionkey){
			top.addOrUpdateTab("act/workViewPage.do?from=1&taskid="+ taskid+ "&taskkey="+ taskkey +"&instanceid="+ instanceid, "工作查看");
		}
		function handler(id){
			$("#activiti-dialog-myWorkPage").dialog({
				href:'act/commentAddPage2.do?taskid='+ id
			});
			$("#activiti-dialog-myWorkPage").dialog('open');
		}
		// function backTask(id){
		function backTask(id, definitionkey, taskkey){

			$('#activiti-dialog-myWorkPage').dialog({
				title: '驳回工作',
				width: 400,
				height: 320,
				closed: false,
				cache: false,
				modal: true,
				href: 'act/workBackPage.do?definitionkey=' + definitionkey + '&taskkey=' + taskkey + '&id=' + id,
				onClose: function() {
					//$('#activiti-dialog-myWorkPage').dialog('destroy');
					//$('#activiti-dialog-myWorkPage').dialog("destroy");
				},
				onLoad: function() {
						
					$('#activiti-form-workBackPage').form({
						onSubmit: function() {
						
							var taskkey= $('input:radio[name="task"]:checked').val();
							
							if(taskkey == null) {
								$.messager.alert("提醒","请选择驳回至哪一节点！");
								return false;
							}
							
							loading('驳回中');
						},
						success: function(data) {
						
							loaded();
							var json = $.parseJSON(data);
							
							if(json.flag == true) {
			
								$.messager.alert("提醒","驳回成功。");
								$("#activiti-dialog-myWorkPage").dialog('close');
								$("#activiti-datagrid-myWorkPage").datagrid('reload');
								return false;
							} else {
							
								$.messager.alert("提醒","驳回失败！");
							}
						}
					});
				
				},
				buttons:[
					{
						text:'确定',
						iconCls:'button-save',
						handler:function(){
							$("#activiti-form-workBackPage").submit();
						}
					},
					{
						text:'取消',
						iconCls:'button-giveup',
						handler:function(){
							$("#activiti-dialog-myWorkPage").dialog('close');
						}
					}
				]
			});
			
			return false;
		
			$.messager.confirm("提醒", "确定驳回该流程？", function(r){
				if(r){
					loading("驳回中...");
					$.ajax({
						url:'act/backPrevWork.do',
						dataType:'json',
						type:'post',
						data:'id='+ id,
						success:function(json){
							loaded();
							if(json.flag == true){
							
								$.messager.alert("提醒", "驳回成功");
								$("#activiti-datagrid-myWorkPage").datagrid('reload');
							}
							else{
								$.messager.alert("提醒", "驳回失败");
							}
						}
					});
				}
			});
		}
		
		function deleteProcess(id) {
		
			$.messager.confirm("提醒", "是否删除该工作？", function(r){

                if(r) {

                    $.ajax({
                        dataType: 'json',
                        type: 'post',
                        data: {id: id},
                        url: 'act/deleteProcess2.do',
                        success: function(json) {

                            if(!json.flag) {
                                $.messager.alert("提醒", "删除失败！");
                                return false;
                            }

                            $.messager.alert("提醒", "删除成功。");
                            var queryJSON = $("#activiti-form-myWorkPage form").serializeJSON();
                            $("#activiti-datagrid-myWorkPage").datagrid('load', queryJSON);
                        },
                        error: function() {

                            $.messager.alert("提醒", "发生异常！<br/>如果一直出现该情况，请联系管理员。");
                        }
                    });
                }
			});
		}
		
	</script>
  </body>
</html>
