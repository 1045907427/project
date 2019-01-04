<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>启动工作流程页面-业务表单</title>
  </head>
  
  <body>
  	<style type="text/css">
  		.startWorkTable{width:100%;border-collapse:collapse;}
  		.startWorkTable td{border:1px solid #efefef;padding:3px;line-height:24px;}
  	</style>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center',border:false" style="padding:5px;">
			<form id="activiti-form-startWorkPage" action="act/startBusinessWork.do" method="post">
				<input type="hidden" name="process.businessid" value="${businessId }" />
				<input type="hidden" name="process.definitionkey" value="${definition.unkey }" />
				<input type="hidden" name="process.definitionname" value="${definition.name }" />
				<input type="hidden" name="listener" id="listenerImpl" />
				<input type="hidden" name="param" id="activitiParam" />
				<table class="startWorkTable">
					<tr>
						<td><input type="text" name="process.title" value="${title }" style="width:350px;height:30px;line-height:30px;text-indent:5px" /></td>
					</tr>
					<tr>
						<td>当前提交流程：${definition.name }</td>
					</tr>
					<tr>
						<td><input type="radio" name="acceptType" checked="checked" value="1" style="position:relative;top:3px;">使用默认接受人</input> &nbsp; <input type="radio" name="acceptType" value="2" style="position:relative;top:3px;">指定接受人</input></td>
					</tr>
					<tr>
						<td style="height:24px;">
							<div id="activiti-apply-startWorkPage" style="display:none;">
								选择人员：<input id="activiti-applyUser-startWorkPage" name="applyUser" />
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'south',border:false">
			<div class="buttonDivR">
	    			<a href="javascript:;" id="activiti-button-startWorkPage" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'">确定转交</a>
	    			<a href="javascript:;" id="activiti-button2-startWorkPage" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-quit'">取消转交</a>
	    		</div>
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			$("#activiti-applyUser-startWorkPage").widget({
    			referwid:'RT_T_SYS_USER',
    			singleSelect:true,
    			width:180,
    			onlyLeafCheck:true
    		});
			$("input:radio").click(function(){
				var type = $(this).val();
				if(type == "2"){
					$("#activiti-apply-startWorkPage").show();
				}
				else{
					$("#activiti-apply-startWorkPage").hide();
				}
			});
			$("#activiti-button-startWorkPage").click(function(){
				$.messager.confirm("提醒", "确定提交该信息到工作流？", function(r){
					if(r){
						$("#activiti-form-startWorkPage").submit();
					}
				});
			});
			$("#activiti-button2-startWorkPage").click(function(){
				$("#"+ data.dialog).dialog('close');
			});
			$("#activiti-form-startWorkPage").form({
				onSubmit: function(){  
		  		  	loading("提交中..");
		  		},  
		  		success:function(data){
		  		  	loaded();
		  		  	var json = $.parseJSON(data);
		  		  	if(json.flag == true){
		  		  		$.messager.alert("提醒", "启动成功");	
		  		  		j.onSuccess();	
		  		  	}
		  		  	else{
		  		  		$.messager.alert("提醒", "启动失败");	
		  		  	}
		  		}
			});
		});
		var j;
		function startWork(json){
			$("#listenerImpl").val(json.listener);
			$("#activitiParam").val(json.param);
			j = json;
		}
	</script>
  </body>
</html>
