<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>驳回工作</title>
	
  </head>
  <body>
  	<style type="text/css">
  		.workbacktable{width:100%;border-collapse:collapse;}
  		.workbacktable td{border:1px solid #aaaaaa;padding:3px;line-height:24px;}
		label > input[type=radio]{
			float:left;
			height: 18px;
			line-height: 18px;
		}
  	</style>
  	<div class="easyui-layout" data-options="fit:true">
	  	<div data-options="region:'center',border:false">
		  	<form id="activiti-form-workBackPage" action="act/backWork.do">
		  		<input type="hidden" name="id" value="${param.id }"/>
		  		<input type="hidden" name="taskkey" id="taskkey"/>
		  		
		  		<input type="hidden" name="comment.agree" value="0"/>
		  		<input type="hidden" name="comment.taskkey" value="<c:out value="${param.taskkey }" escapeXml="true"></c:out>"/>
		  		<input type="hidden" name="comment.taskname" value="<c:out value="${task.nameExpression.expressionText }" escapeXml="true"></c:out>"/>
				<input type="hidden" name="comment.instanceid" value="${process.instanceid }"/>
				<input type="hidden" name="comment.taskid" value="${process.taskid }"/>
				<input type="hidden" name="comment.id" value="${comment.id }"/>
		
				<table class="workbacktable">
					<tr>
						<td style="background:#efefef;font-weight:700;">请选择驳回至哪一步：</td>
					</tr>
					<c:forEach items="${list }" var="item">
						<tr>
							<td><label <c:if test="${item.sign}">style="color: #F00;" title="会签节点不允许驳回" </c:if> ><input type="radio" name="task" value="${item.taskkey }" <c:if test="${item.sign}">disabled="disabled"</c:if> />${item.taskname }</label></td>
						</tr>
					</c:forEach>
					<tr>
						<td style="background:#efefef;font-weight:700;">请输入意见：</td>
					</tr>
					<tr>
						<td><textarea style="width: 350px; height: 60px; resize: none;" name="comment.comment" ></textarea></td>
					</tr>
				</table>
			</form>
		</div>
		<div data-options="region:'south',border:false" style="height:40px;">
		   	<div class="buttonDivR">
	    		<span id="activiti-span1-workBackPage"><a href="javascript:;" id="activiti-button2-workBackPage" class="easyui-linkbutton" data-options="iconCls:'button-back'">驳回</a></span>
	    		<span id="activiti-span2-workBackPage"><a href="javascript:;" id="activiti-button3-workBackPage" class="easyui-linkbutton" data-options="iconCls:'button-close'">关闭</a></span>
	    	</div>
		</div>
	</div>
	<script type="text/javascript">
	
	$(function(){
		
		$('#activiti-span1-workBackPage').hide();
		
		$('input[type=radio]').unbind().click(function(){
		
			$('#activiti-span1-workBackPage').hide();
			$('#taskkey').val($(this).val());
			$('#activiti-span1-workBackPage').show();
		});
		
		$('input[type=radio]').last().click();
		
		$('#activiti-button2-workBackPage').click(function() {
			
			$('#activiti-form-workBackPage').submit();
		});
		
		$('#activiti-button3-workBackPage').click(function() {
			
			$("#activiti-dialog2-workHandlePage").dialog('close');
		});
	})
	
	</script>
  </body>
</html>
