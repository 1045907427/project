<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>流程节点用户设置(审批)</title>
  </head>
  <body>
  	<div id="activiti-div-definitionUserSettingPage2">
  		<form action="">
  			<table cellpadding="0" cellspacing="0" class="userSettingTable" style="border-collapse:collapse;border:1px solid #7babcf;width:100%">
  				<tr>
  					<td style="width:150px;"><input type="checkbox" value="assignee" data="01" <c:if test="${task.rule == 'assignee' }">checked="checked"</c:if> class="userSettingCheckbox">指定人员</input></td>
  					<td>
  						<input class="userid" id="activiti-assignee-definitionUserSettingPage2" name="assignee" data="01" value="${task.user }" />
  					</td>
  				</tr>
  				<tr>
  					<td><input type="checkbox" value="departmentRole" data="02" <c:if test="${task.rule == 'departmentRole' }">checked="checked"</c:if> class="userSettingCheckbox">本部门指定角色</input></td>
  					<td>
  						<input class="roleid" id="activiti-role-definitionUserSettingPage2" data="02" value="<c:if test="${task.rule == 'departmentRole' }">${task.role }</c:if>" />
  					</td>
  				</tr>
  				<tr>
  					<td><input type="checkbox" value="department" data="03" <c:if test="${task.rule == 'department' }">checked="checked"</c:if> class="userSettingCheckbox">本部门所有人</input></td>
  					<td></td>
  				</tr>
  				<tr>
  					<td><input type="checkbox" value="oneRole" data="04" <c:if test="${task.rule == 'oneRole' }">checked="checked"</c:if> class="userSettingCheckbox">指定角色</input></td>
  					<td>
  						<input class="roleid" id="activiti-role2-definitionUserSettingPage2" data="04" value="<c:if test="${task.rule == 'oneRole' }">${task.role }</c:if>" />
  					</td>
  				</tr>
  				<tr>
  					<td><input type="checkbox" value="oneDepartment" data="05" <c:if test="${task.rule == 'oneDepartment' }">checked="checked"</c:if> class="userSettingCheckbox">指定部门</input></td>
  					<td>
  						<input class="deptid" id="activiti-department-definitionUserSettingPage2" data="05" value="<c:if test="${task.rule == 'oneDepartment' }">${task.dept }</c:if>" />
  					</td>
  				</tr>
  				<tr>
  					<td><input type="checkbox" value="oneDepartmentOneRole" data="06" <c:if test="${task.rule == 'oneDepartmentOneRole' }">checked="checked"</c:if> class="userSettingCheckbox">指定部门与角色</input></td>
  					<td>
  						<input class="deptid" id="activiti-department2-definitionUserSettingPage2" data="06" value="<c:if test="${task.rule == 'oneDepartmentOneRole' }">${task.dept }</c:if>" />
  						<input class="roleid" id="activiti-role3-definitionUserSettingPage2" data="06" value="<c:if test="${task.rule == 'oneDepartmentOneRole' }">${task.role }</c:if>" />
  					</td>
  				</tr>
  				<tr>
  					<td><input type="checkbox" value="onePost" data="07" <c:if test="${task.rule == 'onePost' }">checked="checked"</c:if> class="userSettingCheckbox">指定岗位</input></td>
  					<td>
  						<input class="postid" id="activiti-post-definitionUserSettingPage2" data="07" value="${task.post }" />
  					</td>
  				</tr>
  			</table>
  		</form>
  	</div>
  	<script type="text/javascript">
  		$(function(){
  		
  			// 清除所有checkbox设定
  			$('.userSettingCheckbox').removeAttr('checked');
  			// 选中对应规则的checkbox
  			$('.userSettingCheckbox[data=${rule }]').attr('checked', 'checked');

  			$('#activiti-div-definitionUserSettingPage2 input:not(:checkbox)').val('');
  			$('#activiti-div-definitionUserSettingPage2 input:not(:checkbox)[data=${rule }].userid').val('${userid }');
  			$('#activiti-div-definitionUserSettingPage2 input:not(:checkbox)[data=${rule }].roleid').val('${roleid }');
  			$('#activiti-div-definitionUserSettingPage2 input:not(:checkbox)[data=${rule }].deptid').val('${deptid }');
  			$('#activiti-div-definitionUserSettingPage2 input:not(:checkbox)[data=${rule }].postid').val('${postid }');
  		
  			$(".userSettingCheckbox").click(function(){
  				$(".userSettingCheckbox").not(this).attr("checked", false);
  			});
  			$("#activiti-assignee-definitionUserSettingPage2").widget({
  				referwid: 'RT_T_SYS_USER',
  				singleSelect:false,
    			width:180,
    			treePName:true,
    			onlyLeafCheck:false
  			});
  			$("#activiti-department-definitionUserSettingPage2").widget({
  				referwid: 'RT_T_SYS_DEPT',
  				singleSelect:true,
    			width:180,
    			treePName:true,
    			onlyLeafCheck:true
  			});
  			$("#activiti-department2-definitionUserSettingPage2").widget({
  				referwid: 'RT_T_SYS_DEPT',
  				singleSelect:true,
    			width:120,
    			treePName:true,
    			onlyLeafCheck:true
  			});
  			$("#activiti-role-definitionUserSettingPage2").widget({
  				referwid: 'RL_T_AC_AUTHROITY',
    			width:180
  			});
  			$("#activiti-role2-definitionUserSettingPage2").widget({
  				referwid: 'RL_T_AC_AUTHROITY',
    			width:180
  			});
  			$("#activiti-role3-definitionUserSettingPage2").widget({
  				referwid: 'RL_T_AC_AUTHROITY',
    			width:120
  			});
  			$("#activiti-post-definitionUserSettingPage2").widget({
  				referwid: 'RL_T_BASE_WORKJOB',
    			width:180
  			});
  		});
  	</script>
  </body>
</html>
