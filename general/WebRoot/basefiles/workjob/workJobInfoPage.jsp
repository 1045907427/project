<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>工作岗位详情页面</title>
  </head>
  
  <body>
   	<table>
   		<tr>
   			<td>编&nbsp;&nbsp;码:</td>
   			<td>
   				<input type="text" name="workJob.id" style="width:200px;" value="<c:out value="${workJob.id }"></c:out>" maxlength="20" readonly="readonly"/>
   				<input id="workjob-oldid" type="hidden" value="<c:out value="${workJob.id }"></c:out>"/>
   			</td>
   		</tr>
   		<tr>
   			<td>岗位名称:</td>
   			<td>
   				<input type="text" name="workJob.jobname"  style="width:200px;" value="<c:out value="${workJob.jobname }"></c:out>" maxlength="50" readonly="readonly"/>
   			</td>
   		</tr>
        <tr style="height: auto;">
            <td>拥有角色:</td>
            <td>
                <input id="workjob-roleid" type="text" name="workJob.roleList"  style="width: 200px;" value="${workJob.roleList}" disabled="disabled"/>
            </td>
        </tr>
        <tr>
            <td>所属部门:</td>
            <td>
                <input id="workjob-deptid" type="text" name="workJob.deptid" style="width: 200px;" value="<c:out value="${workJob.deptid }"></c:out>" disabled="disabled"/>
            </td>
        </tr>
   		<tr>
   			<td>状&nbsp;&nbsp;态:</td>
   			<td>
   				<select name="workJob.state" style="width:200px;" disabled="disabled">
					<option value="4" <c:if test="${workJob.state=='4'}">selected="selected"</c:if>>新增</option>
					<option value="3" <c:if test="${workJob.state=='3'}">selected="selected"</c:if>>暂存</option>
					<option value="2" <c:if test="${workJob.state=='2'}">selected="selected"</c:if>>保存</option>
					<option value="1" <c:if test="${workJob.state=='1'}">selected="selected"</c:if>>启用</option>
					<option value="0" <c:if test="${workJob.state=='0'}">selected="selected"</c:if>>禁用</option>
				</select>
   			</td>
   		</tr>
   		<tr style="height: auto;">
   			<td>备&nbsp;&nbsp;注:</td>
   			<td>
   				<textarea name="workJob.remark" style="height: 100px;width: 193px;" readonly="readonly"><c:out value="${workJob.remark}"></c:out></textarea>
   			</td>
   		</tr>
   	</table>
    <script type="text/javascript">
    	$(function(){
    		//控制按钮状态
				$("#workjob-button").buttonWidget("setDataID",{id:$("#workjob-oldid").val(),state:'${workJob.state}',type:'view'});
    		$("#workjob-deptid").widget({
    			name:'t_base_workjob',
    			col:'deptid',
    			singleSelect:true,
    			view:true
    		});
    		$("#workjob-roleid").widget({
    			referwid:'RL_T_AC_AUTHROITY',
    			singleSelect:false,
    			view:true
    		});
    	});
    </script>
  </body>
</html>
