<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
      <title>我的工作-已办结</title>
      <%@include file="/include.jsp" %>
      <link rel="stylesheet" href="activiti/css/oastyle.css?v_=20170609" type="text/css">
  </head>
  
  <body>
	<div class="easyui-panel" data-options="fit:true,border:false">
  		<div id="activiti-form-myWorkPage" style="padding:8px;">
	  		<form>
		  		<input type="hidden" value="8" name="type" />
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
		<div id="activiti-datagrid-myWorkPage"></div>
	</div>
	<script type="text/javascript">
		$(function(){
			$("#activiti-datagrid-myWorkPage").datagrid({
				columns:[[
					{field:'id', title:'OA号', width:50},
					{field:'title', title:'标题', width:350,
						formatter: function(value,row,index){
							return "<a href='javascript:;' class='oatitle' class='oatitle' onclick=\"viewDetail('"+ row.taskid +"','"+ row.taskkey +"','"+ row.instanceid +"','"+ row.definitionkey +"')\">"+value+"</a>";
						}
					},
					{field:'definitionname', title:'流程名称', width:200},
					{field:'applydate', title:'创建时间', width:120},
					{field:'updatedate', title:'办结时间', width:120},
					{field:'taskname', title:'当前节点', width:120, hidden:true},
					{field:'status',title:'状态', width:60,
						formatter: function(value,row,index){
						
							if(row.status == '0' ) {
						
								return "<font color='red'>已作废</font>";
							}

                            if(row.status == '9' || row.isend == '1') {

                                return "<font color='green'>结束</font>";
                            }
							
							return "<font color='red'>正在运行</font>";
						}
					},
					{field:'cantakeback', width: 80, align:'center', 
						formatter: function(value,row,index){

							if(value == '1') {
							
								return '<a href="javascript:void(0);" class="oaoperate" onclick="takeTask(' + row.id + ', ' + row.taskid + ')">收回</a>'
							}

							return '<span title="已经结束 或者 当前节点和被处理节点相隔两个节点以上时无法收回！">无法收回</span>';
						}
					},
				]],
				queryParams:{type:'8', definitionkey: '${param.definitionkey }'},
				fit:true,
				border:false,
				method:'post',
				rownumbers:true,
				pagination:true,
		 		singleSelect:true,
				toolbar:'#activiti-form-myWorkPage'/*,
				url:'act/getWorkList.do'*/
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
                $("#activiti-datagrid-myWorkPage").datagrid({
                    url:'act/getWorkList.do',
                    queryParams: queryJSON
                });
	       		// $("#activiti-datagrid-myWorkPage").datagrid('load', queryJSON);
			});

			// 重置
			$("#activiti-resetQueay-myWorkPage").click(function(){
				$("#activiti-definition-myWorkPage").widget('clear'); // 3284 瑞家&通用版：重置有问题
				$(':input','#activiti-form-myWorkPage').not(':button,:submit,:reset,:hidden').val('');
				$("#activiti-datagrid-myWorkPage").datagrid('load', {type:'8'});
			});
		});
		function viewDetail(taskid, taskkey, instanceid, definitionkey){
			top.addOrUpdateTab("act/workViewPage.do?taskid="+ taskid+ "&taskkey="+ taskkey +"&instanceid="+ instanceid, "工作查看");
		}
		
		function checkProcessStatus(id, taskid) {

			$.ajax({
				type: 'post',
				url: 'act/selectProcess.do',
				data: {id: id},
				dataType: 'json',
				success: function(json) {

					var flag = true;
					var sync = true;

					var process = json.process;
					
					if(process == null) {
						flag = false;
					} else {
					
						var pstatus = json.process.status;
						var ptaskid = json.process.taskid;
						
						if(process == null || pstatus == '2' || pstatus == '3' || pstatus == '9' || pisend == '1') {
							
							flag = false;
						}
						
						if(taskid != taskid) {

							sync = false;
						}
					}
					if(!flag) {
						$.messager.alert("提醒", "无法对该工作进行操作！<br/>可能由于该工作已经结束，或处于挂起、删除状态。");
						return false;
					}
					if(!sync) {
						$.messager.alert("提醒", "无法对该工作进行操作！<br/>可能由于该工作已经发生流转。请刷新当前页面后再试。");
						return false;
					}
				
					// workFormSubmit(call);
					takeTask(id, taskid);
				},
				error: function() {

					$.messager.alert("警告", "验证失败！<br/>如果一直出现该提示，请联系管理员。");
				}
			});
		}
		
		function takeTask(id, taskid){
		
			$.messager.confirm("提醒", "确定收回该流程？", function(r){
				if(r){
					loading("收回中...");
					$.ajax({
						url:'act/takeBackWork.do',
						dataType:'json',
						type:'post',
						data: {id: id},
						success:function(json){
							loaded();
							if(json.flag == true){
								$.messager.alert("提醒", "收回成功");
								$("#activiti-datagrid-myWorkPage").datagrid('reload');
							}
							else{
								$.messager.alert("提醒", "收回失败");
							}
						},
						error: function() {
							loaded();
						}
					});
				}
			});
		}
		
	</script>
  </body>
</html>
