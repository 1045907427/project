<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
      <title>我的工作-已删除</title>
      <%@include file="/include.jsp" %>
      <link rel="stylesheet" href="activiti/css/oastyle.css?v_=20170609" type="text/css">
  </head>
  
  <body>
	<div class="easyui-panel" data-options="fit:true,border:false">
  		<div id="activiti-form-myWorkPage" style="padding:8px;">
	  		<form>
		  		<input type="hidden" value="9" name="type" />
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
							return "<a href='javascript:;' class='oatitle' onclick=\"viewDetail('"+ row.taskid +"','"+ row.taskkey +"','"+ row.instanceid +"','"+ row.definitionkey +"')\">"+value+"</a>";
						}
					},
					{field:'definitionname', title:'流程名称', width:200},
					{field:'applydate', title:'创建时间', width:120},
					// {field:'updatedate', title:'结束时间', width:120, hidden:true},
					{field:'taskname', title:'当前节点', width:120},
					{field:'status',title:'状态', width:60,
						formatter: function(value,row,index){
							if(value == '3') {
								return "<font color='red'>删除</font>";
							}
						}
					},
					{field:'operation',/*title:'操作', */width:60, align:'center',
						formatter: function(value,row,index){
							return '<a href ="javascript:void(0);" class="oaoperate" onclick="resumeProcess(' + row.id + ')">恢复</a>';
						}
					}
				]],
				queryParams:{type:'9', definitionkey: '${param.definitionkey }'},
				fit:true,
				border:false,
				method:'post',
				rownumbers:true,
				pagination:true,
		 		singleSelect:true,
				toolbar:'#activiti-form-myWorkPage',
				url:'act/getWorkList.do'
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
				$("#activiti-datagrid-myWorkPage").datagrid('load', {type:'9'});
			});
		});
		function viewDetail(taskid, taskkey, instanceid, definitionkey){
			top.addOrUpdateTab("act/workViewPage.do?taskid="+ taskid+ "&taskkey="+ taskkey +"&instanceid="+ instanceid, "工作查看");
		}
		
		function resumeProcess(id) {
		
			$.ajax({
				dataType: 'json',
				type: 'post',
				data: {id: id},
				url: 'act/recoverProcess.do',
				success: function(json) {
				
					if(!json.flag) {
						$.messager.alert("提醒", "恢复失败！");
						return false;
					}
					
					$.messager.alert("提醒", "恢复成功。");
					var queryJSON = $("#activiti-form-myWorkPage form").serializeJSON();
	       			$("#activiti-datagrid-myWorkPage").datagrid('load', queryJSON);
				},
				error: function() {
				
					$.messager.alert("提醒", "发生异常！<br/>如果一直出现该情况，请联系管理员。");
				}
			});
		}
	</script>
  </body>
</html>
