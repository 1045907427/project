<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>工作管理</title>
    <%@include file="/include.jsp" %>
    <style type="text/css">
        .checkbox1{
            float:left;
            height: 22px;
            line-height: 22px;
        }
        .divtext{
            height:22px;
            line-height:22px;
            float:left;
            display: block;
        }
        .len40 {
            width: 40px;
        }
        .len70 {
            width: 70px;
        }
        .len210 {
            width: 210px;
        }
    </style>
  </head>
  <body>
  	<div class="easyui-panel" data-options="fit:true,border:false">
		<div id="activiti-datagrid-workManagePage"></div>
		<div id="activiti-form-workManagePage" style="padding:8px;">
		  	<form>
		  		<input type="hidden" value="0" name ="type"/>
		  		<table>
		  			<tr>
		  				<td class="len40">编号：</td>
                        <td class="len120"><input type="text" name="id" class="len100"/></td>
		  				<td class="len70 right">流程名称：</td>
		  				<td class="len150"><input type="text" id="activiti-definition-workManagePage" name="definitionkey"/></td>
		  				<td class="len40 right">标题：</td>
                        <td class="len200"><input type="text" name="title" class="len200"/></td>
		  			</tr>
                    <tr>
                        <td>状态：</td>
                        <td colspan="3">
                            <div>
                                <label class="divtext"><input type="checkbox" name="status" value="1" class="checkbox1"/>执行中</label>
                                <label class="divtext"><input type="checkbox" name="status" value="2" class="checkbox1"/>挂起</label>
                                <label class="divtext"><input type="checkbox" name="status" value="3" class="checkbox1"/>临时删除</label>
                                <label class="divtext"><input type="checkbox" name="status" value="9" class="checkbox1"/>结束</label>
                                <!--<label class="divtext"><input type="checkbox" name="status" value="0" class="checkbox1"/>作废</label>-->
                            </div>
                        </td>
                        <td colspan="2" align="right">
                            <div>
                                <a href="javascript:;" id="activiti-query-workManagePage" class="button-qr">查询</a>
                                <a href="javaScript:;" id="activiti-resetQueay-workManagePage" class="button-qr">重置</a>
                            </div>
                        </td>
                    </tr>
		  		</table>
		  	</form>
	  	</div>
	</div>
	<script type="text/javascript">
		$(function(){

			var processCols = $("#activiti-datagrid-workManagePage").createGridColumnLoad({
				name: 't_act_process',
				frozenCol: [[]],
				commonCol: [[
					{field:'id', title:'OA号', width:50},
					{field:'title', title:'标题', width:350,
						formatter: function(value,row,index){
							return "<a href='javascript:;' onclick=\"viewDetail('"+ row.taskid +"','"+ row.taskkey +"','"+ row.instanceid +"','"+ row.definitionkey +"')\">"+value+"</a>";
						}
					},
					{field:'definitionkey', title:'流程标识', width:142, hidden: true},
					{field:'definitionname', title:'流程名称', width:200},
					{field:'definitionid', title:'流程版本', width:60, hidden: true,
						formatter: function(value, row, index) {

							if((value || '') == '') {

								return '';
							}

							return (value + '').split(':')[1];
						}
					},
					{field:'applydate', title:'创建时间', width:120},
					{field:'taskname', title:'当前节点', width:120,
						formatter: function(value, row, index) {
							if(value == '' && row.status != '0') {
								return '<font color="#080">已结束</font>';
							}
							return value;
						}
					},
					{field:'status',title:'状态', width: 70, align: 'center',
						formatter: function(value,row,index){

							if(value == '1') {
								return '<span title="运行中。">运行中</span>';
							} else if(value == '2') {
								return '<span title="可重新激活。">挂起</span>';
							} else if(value == '3') {
								return '<span title="发起人临时删除，可由发起人重新恢复。">临时删除</span>';
							} else if(value == '0') {
								return '<font color="#f00">已作废</font>';
							} else if(value == '9' || row.isend == '1') {
								return '<font color="#080">已结束</font>';
							}
						}
					},
					{field:'operation', title: '操作', width: 80, align: 'center',aliascol:'id',
						formatter: function(value,row,index){

							value == row.status;

							var html = new Array();

							if(row.status == '1') {

								<security:authorize url="/act/suspendProcess.do">
								html.push('<a href="javascript:;" onclick="suspendProcess(' + row.id + ')" title="挂起后可以重新唤醒">挂起</a>');
								</security:authorize>

								if(row.candelete == '1') {
									<security:authorize url="/act/deleteProcess.do">
									html.push('<a href="javascript:;" onclick="deleteProcess(' + row.id + ')" title="销毁">销毁</a>');
									</security:authorize>
								}
							}

							if(row.status == '2') {

								<security:authorize url="/act/activateProcess.do">
								html.push('<a href="javascript:;" onclick="activateProcess(' + row.id + ')">激活</a>');
								</security:authorize>
							}

							if(row.status == '3') {

								if(row.candelete == '1') {
									<security:authorize url="/act/deleteProcess.do">
									html.push('<a href="javascript:;" onclick="deleteProcess(' + row.id + ')" title="销毁">销毁</a>');
									</security:authorize>
								}
							}

							if((row.status == '9' || row.isend == '1') && row.status != '0') {
								<security:authorize url="/act/rollbackProcess.do">
								//html.push('<a href="javascript:;" onclick="rollbackProcess(' + row.id + ')">作废</a>');
								</security:authorize>
							}

							return html.join('<span style="width: 10px;">　</span>');
						}
					}
				]]
			});

			$("#activiti-datagrid-workManagePage").datagrid({
				authority:processCols,
				frozenColumns: processCols.frozen,
				columns:processCols.common,
                queryParams:{type:'0'},
				fit:true,
				border:false,
				method:'post',
				rownumbers:true,
				pagination:true,
		 		singleSelect:true,
				toolbar:'#activiti-form-workManagePage',
				url:'act/getWorkList.do'
			});
			$("#activiti-query-workManagePage").click(function(){
				var queryJSON = $("#activiti-form-workManagePage form").serializeJSON();
	       		$("#activiti-datagrid-workManagePage").datagrid('load', queryJSON);
			});

			// 重置
			$("#activiti-resetQueay-workManagePage").click(function(){
        		$("#activiti-definition-workManagePage").widget('clear'); // 3284 瑞家&通用版：重置有问题
				$(':input','#activiti-form-workManagePage').not(':button,:submit,:reset,:hidden').val('');
				$("#activiti-datagrid-workManagePage").datagrid('load', {type:'0'});
			});

			$("#activiti-definition-workManagePage").widget({
    			referwid:'RT_T_ACT_DEFINATION',
    			singleSelect:true,
    			width:120,
    			onlyLeafCheck:true,
    			onChecked:function(data){
    				$("#activiti-key-workManagePage").val(data.unkey);
    			},
    			onClear:function(){
    				$("#activiti-key-workManagePage").val("");
    			}
    		});
		});
		function viewDetail(taskid, taskkey, instanceid, definitionkey){
			top.addOrUpdateTab("act/workViewPage.do?from=1&taskid="+ taskid+ "&taskkey="+ taskkey +"&instanceid="+ instanceid, "工作查看");
		}
		
		function deleteProcess(id) {
		
			$.ajax({
				dataType: 'json',
				type: 'post',
				data: {id: id, type: '1'},
				url:'act/selectProcess.do',
				success: function(json) {
				
					if(json.process.candelete == '0') {
					
						$.messager.confirm('警告', '该工作无法销毁，可能由于该工作已经生成表单数据！');
						$("#activiti-datagrid-workManagePage").datagrid('reload');
						return true;
					}
					
					//////
					// delete process
					$.messager.confirm("提醒", "是否要销毁该工作？<br/><font color=\"red\">销毁后，该工作将无法恢复！</font>", function(r){
						if(r) {
							
							loading('删除中...');
							
							$.ajax({
								dataType: 'json',
								data: {id: id, type: '1'},
								url:'act/deleteProcess.do',
								type: 'post',
								success: function(json) {
								
									loaded();
								
									if(!json.flag) {
										$.messager.alert("提醒", "删除失败！");
										return false;
									}
									
									$.messager.alert("提醒", "删除成功。");
									var queryJSON = $("#activiti-form-workManagePage form").serializeJSON();
					       			$("#activiti-datagrid-workManagePage").datagrid('load', queryJSON);
								},
								error: function() {
								
									loaded();
									$.messager.alert("提醒", "发生异常！<br/>如果一直出现该情况，请联系管理员。");
								}
							});
							
						}
					});
					//////
				},
				error: function(){}
			});
		
		}
		
		function suspendProcess(id) {
		
			$.messager.confirm("提醒", "是否要挂起该工作？<br/><font color=\"red\">被挂起的工作将无法进行审批操作！</font><br/><font color=\"black\">工作被挂起后，可以重新激活该工作。</font>", function(r){
				if(r) {
					
					loading('挂起中...');
					
					$.ajax({
						dataType: 'json',
						data: {id: id},
						url:'act/suspendProcess.do',
						type: 'post',
						success: function(json) {
						
							loaded();
						
							if(!json.flag) {
								$.messager.alert("提醒", "挂起失败！<br/>可能因为该工作已经结束或被删除。");
								return false;
							}
							
							$.messager.alert("提醒", "挂起成功。");
							var queryJSON = $("#activiti-form-workManagePage form").serializeJSON();
			       			$("#activiti-datagrid-workManagePage").datagrid('load', queryJSON);
						},
						error: function() {
						
							loaded();
							$.messager.alert("提醒", "发生异常！<br/>如果一直出现该情况，请联系管理员。");
						}
					});
					
				}
			});
		
		}
		
		function activateProcess(id) {
		
			$.messager.confirm("提醒", "是否要激活该工作？", function(r){
				if(r) {
					
					loading('激活中...');
					
					$.ajax({
						dataType: 'json',
						data: {id: id},
						url:'act/activateProcess.do',
						type: 'post',
						success: function(json) {
						
							loaded();
						
							if(!json.flag) {
								$.messager.alert("提醒", "激活失败！<br/>可能因为该工作已经结束或被删除。");
								return false;
							}
							
							$.messager.alert("提醒", "激活成功。");
							var queryJSON = $("#activiti-form-workManagePage form").serializeJSON();
			       			$("#activiti-datagrid-workManagePage").datagrid('load', queryJSON);
						},
						error: function() {
						
							loaded();
							$.messager.alert("提醒", "发生异常！<br/>如果一直出现该情况，请联系管理员。");
						}
					});
					
				}
			});
		}

    function rollbackProcess(id) {

        $.messager.confirm("提醒", "是否要作废该工作？</br><font color=\"red\">作废后所有生成的单据会被删除，且不能被还原，请谨慎操作！</font>", function(r){
            if(r) {

                loading('激活中...');

                $.ajax({
                    dataType: 'json',
                    data: {id: id},
                    url:'act/rollbackProcess.do',
                    type: 'post',
                    success: function(json) {

                        loaded();

                        if(!json.flag) {
                            $.messager.alert("提醒", "操作失败！");
                            return false;
                        }

                        $.messager.alert("提醒", "操作成功。");
                        var queryJSON = $("#activiti-form-workManagePage form").serializeJSON();
                        $("#activiti-datagrid-workManagePage").datagrid('load', queryJSON);
                    },
                    error: function() {

                        loaded();
                        $.messager.alert("提醒", "发生异常！<br/>如果一直出现该情况，请联系管理员。");
                    }
                });

            }
        });
    }
		
	</script>
  </body>
</html>
