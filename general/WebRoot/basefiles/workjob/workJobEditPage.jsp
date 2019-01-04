<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>工作岗位修改页面</title>
  </head>
  
  <body>
  <form action="basefiles/editWorkJobHold.do" method="post" id="workjob-form-edit">
   	<table>
   		<tr>
   			<td>编&nbsp;&nbsp;码:</td>
   			<td>
   				<input type="text" name="workJob.id" class="easyui-validatebox" validType="validID[20]" required="true" style="width:200px;" value="<c:out value="${workJob.id }"></c:out>" maxlength="20" <c:if test="${isQuoteDel==false}">readonly="readonly"</c:if>/>
   				<input id="workjob-oldid" type="hidden" name="workJob.oldid" value="<c:out value="${workJob.id }"></c:out>"/>
   			</td>
   		</tr>
   		<tr>
   			<td>岗位名称:</td>
   			<td>
   				<input type="text" name="workJob.jobname"  class="easyui-validatebox" required="true" style="width:200px;" validType="validName[50]" value="<c:out value="${workJob.jobname }"></c:out>" maxlength="50"/>
   				<input id="workjob-oldname" type="hidden" value="<c:out value="${workJob.jobname}"></c:out>"/>
   			</td>
   		</tr>
        <tr style="height: auto;">
            <td>拥有角色:</td>
            <td>
                <input id="workjob-roleid" type="text" name="workJob.roleList"  style="width: 200px;" value="${workJob.roleList}"/>
            </td>
        </tr>
        <tr>
            <td>所属部门:</td>
            <td>
                <input id="workjob-deptid" type="text" name="workJob.deptid" style="width: 200px;" value="<c:out value="${workJob.deptid }"></c:out>"/>
            </td>
        </tr>
   		<tr>
   			<td>状&nbsp;&nbsp;态:</td>
   			<td>
   				<select style="width:200px;" disabled="disabled">
					<option value="4" <c:if test="${workJob.state=='4'}">selected="selected"</c:if>>新增</option>
					<option value="3" <c:if test="${workJob.state=='3'}">selected="selected"</c:if>>暂存</option>
					<option value="2" <c:if test="${workJob.state=='2'}">selected="selected"</c:if>>保存</option>
					<option value="1" <c:if test="${workJob.state=='1'}">selected="selected"</c:if>>启用</option>
					<option value="0" <c:if test="${workJob.state=='0'}">selected="selected"</c:if>>禁用</option>
				</select>
				<input type="hidden" name="workJob.state" value="${workJob.state}"/>
   			</td>
   		</tr>
   		<tr style="height: auto;">
   			<td>备&nbsp;&nbsp;注:</td>
   			<td>
   				<textarea name="workJob.remark" style="height: 100px;width: 200px;" ><c:out value="${workJob.remark}"></c:out></textarea>
   			</td>
   		</tr>
   	</table>
 </form>
    <script type="text/javascript">
    	//控制按钮状态
		$("#workjob-button").buttonWidget("setDataID",{id:$("#workjob-oldid").val(),state:'${workJob.state}',type:'edit'});
    	//检验输入值的最大长度
		$.extend($.fn.validatebox.defaults.rules, {
			validID:{
				validator : function(value,param) {
					var reg=eval("/^[A-Za-z0-9]{0,"+param[0]+"}$/");
					if(reg.test(value)){
						var oldid = $("#workjob-oldid").val();
						if(oldid!=value){
							var ret = ajaxCall({id:value},'basefiles/checkWorkJobID.do');
							var retJson = $.parseJSON(ret);
							if(retJson.flag==false){
								$.fn.validatebox.defaults.rules.validID.message = '编码已存在, 请重新输入!';
								return false;
							}
						}
					}
					else{
						$.fn.validatebox.defaults.rules.validID.message = '最多可输入{0}个字符!';
						return false;
					}
		            return true;
		        }, 
		        message : '' 
			},
			validName:{
				validator : function(value,param) {
					if(value.length <= param[0]){
						var oldname = $("#workjob-oldname").val();
						if(oldname!=value){
							var ret = ajaxCall({name:value},'basefiles/checkWorkJobName.do');
							var retJson = $.parseJSON(ret);
							if(retJson.flag==false){
								$.fn.validatebox.defaults.rules.validName.message = '名称重复, 请重新输入!';
								return false;
							}
						}
					}
					else{
						$.fn.validatebox.defaults.rules.validName.message = '最多可输入{0}个字符!';
						return false;
					}
					return true;
		        }, 
		        message : '' 
			},
			maxLen:{
	  			validator : function(value,param) { 
		            return value.length <= param[0]; 
		        }, 
		        message : '最多可输入{0}个字符!' 
	  		}
		});
    	$(function(){
    		$("#workjob-deptid").widget({
    			name:'t_base_workjob',
    			col:'deptid',
    			singleSelect:true
    		});
    		$("#workjob-roleid").widget({
    			referwid:'RL_T_AC_AUTHROITY',
    			singleSelect:false
    		});
    		$("#workjob-form-edit").form({  
			    onSubmit: function(){  
			    	var flag = $(this).form('validate');
			    	if(flag==false){
			    		return false;
			    	}
			    	loading("提交中..");
			    },  
			    success:function(data){
			    	//表单提交完成后 隐藏提交等待页面
			    	loaded();
			    	//转为json对象
			    	var json = $.parseJSON(data);
			    	if(json.flag){
			    		$.messager.alert("提醒",'修改成功');
			    		//按钮点击后 控制按钮状态显示
			    		$("#workjob-panel").panel({  
							fit:true, 
							title: '工作岗位详情',
							cache: false,
							href : "basefiles/showWorkJobInfo.do?id="+json.id
						});
						$('#workjob-table-list').datagrid("reload");
			    	}else{
			    		if(json.lockFlag == false){
			    			$.messager.alert("警告",'该数据正在被其他人修改，暂不能操作');
			    		}else{
			    			$.messager.alert("警告",'修改失败');
			    		}
			    	}
			    }  
			}); 
    	});
    </script>
  </body>
</html>
