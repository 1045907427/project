<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>工作岗位修改页面</title>
  </head>
  
  <body>
  <form action="" method="post" id="workjob-form-add">
   	<table>
   		<tr>
   			<td>编码:</td>
   			<td>
   				<input type="text" name="workJob.id"  class="easyui-validatebox" validType="validID[20]" required="true" style="width:200px;" value="" maxlength="20"/>
   			</td>
   		</tr>
   		<tr>
   			<td>工作岗位名称:</td>
   			<td>
   				<input type="text" name="workJob.jobname"  class="easyui-validatebox" required="true" style="width:200px;" validType="validName[50]" value="" maxlength="50"/>
   			</td>
   		</tr>
   		<tr>
   			<td>状态:</td>
   			<td>
   				<select style="width:200px;" disabled="disabled">
					<option value="4" selected="selected">新增</option>
					<option value="3">暂存</option>
					<option value="2">保存</option>
					<option value="1">启用</option>
					<option value="0">禁用</option>
				</select>
				<input type="hidden" name="workJob.state" value="${workJob.state}"/>
   			</td>
   		</tr>
   		<tr>
   			<td>所属部门:</td>
   			<td>
   				<input id="workjob-deptid" type="text" name="workJob.deptid" style="width: 200px;" value="<c:out value="${workJob.deptid }"></c:out>"/>
   			</td>
   		</tr>
   		<tr style="height: auto;">
   			<td>备注:</td>
   			<td>
   				<textarea name="workJob.remark" style="height: 100px;width: 200px;"><c:out value="${workJob.remark}"></c:out></textarea>
   			</td>
   		</tr>
   		<tr style="height: auto;">
   			<td>拥有角色:</td>
   			<td>
   				<input id="workjob-roleid" type="text" name="workJob.roleList"  style="width: 200px;" value="${workJob.roleList}"/>
   			</td>
   		</tr>
   	</table>
 </form>
    <script type="text/javascript">
    	$("#workjob-button").buttonWidget("initButtonType","add");
    	//检验输入值的最大长度
		$.extend($.fn.validatebox.defaults.rules, {
			validID:{
				validator : function(value,param) {
					var reg=eval("/^[A-Za-z0-9]{0,"+param[0]+"}$/");
					if(reg.test(value)){
						var ret = ajaxCall({id:value},'basefiles/checkWorkJobID.do');
						var retJson = $.parseJSON(ret);
						if(retJson.flag==false){
							$.fn.validatebox.defaults.rules.validID.message = '编码已存在, 请重新输入!';
							return false;
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
						var ret = ajaxCall({name:value},'basefiles/checkWorkJobName.do');
						var retJson = $.parseJSON(ret);
						if(retJson.flag==false){
							$.fn.validatebox.defaults.rules.validName.message = '名称重复, 请重新输入!';
							return false;
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
    		$("#workjob-form-add").form({  
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
			    		$.messager.alert("提醒",'新增成功');
			    		//按钮点击后 控制按钮状态显示
			    		$("#workjob-panel").panel({  
							fit:true, 
							title: '工作岗位详情',
							cache: false,
							href : "basefiles/showWorkJobInfo.do?id="+json.id
						});
						$('#workjob-table-list').datagrid("reload");
			    	}else{
			    		$.messager.alert("警告",'新增失败');
			    	}
			    }  
			}); 
    	});
    </script>
  </body>
</html>
