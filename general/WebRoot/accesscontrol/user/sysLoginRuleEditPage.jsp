<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>登录规则修改</title>
  </head>
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
  		<div data-options="region:'center',border:false">
  			<form action="accesscontrol/updateSysLoginRule.do" method="post" id="sysUser-form-edit-usercontrolList">
		  		<table  border="0">
		  			<tr>
		  				<td width="120" style="text-align: right;">姓名:</td>
		  				<td style="text-align: left;">
		  					<input type="text" value="${sysLoginRule.name }" readonly="readonly" style="width: 150px;">
		  				</td>
		  			</tr>
		  			<tr>
		  				<td width="120" style="text-align: right;">系统登录规则:</td>
		  				<td style="text-align: left;">
		  					<select id="sysuser-loginrule-logintype" name="sysLoginRule.logintype" style="width: 150px;">
		  						<option value="1" <c:if test="${sysLoginRule.logintype=='1' }">selected="selected"</c:if>>内网登录</option>
		  						<option value="2" <c:if test="${sysLoginRule.logintype=='2' }">selected="selected"</c:if>>允许外网登录</option>
		  						<option value="3" <c:if test="${sysLoginRule.logintype=='3' }">selected="selected"</c:if>>指定IP地址登录</option>
		  					</select>
		  				</td>
		  			</tr>
		  			<tr id="sysuser-loginrule-iplocation-tr" <c:if test="${sysLoginRule.logintype!='1' }">style="display: none;"</c:if>>
		  				<td style="text-align: right;">IP地址段：</td>
		  				<td style="text-align: left;">
		  					<input type="text" class="easyui-validatebox" validType="ip" style="width: 150px;" name="ip1" value="${ip1 }" autocomplete="off"/> 到 <input type="text" class="easyui-validatebox" validType="ip" style="width: 150px;" name="ip2" value="${ip2 }" autocomplete="off"/>
		  				</td>
		  			</tr>
		  			<tr id="sysuser-loginrule-ip-tr" <c:if test="${sysLoginRule.logintype!='3' }">style="display: none;"</c:if>>
		  				<td style="text-align: right;">IP地址：</td>
		  				<td style="text-align: left;">
		  					<input type="text" class="easyui-validatebox" validType="ip" style="width: 150px;" name="ip" value="${ip }" autocomplete="off"/>
		  				</td>
		  			</tr>
		  			<tr>
		  				<td style="text-align: right;">手机登录规则:</td>
		  				<td>
		  					<select name="sysLoginRule.ptype" style="width: 150px;">
		  						<option value="1" <c:if test="${sysLoginRule.ptype=='1' }">selected="selected"</c:if>>不限制手机</option>
		  						<option value="2" <c:if test="${sysLoginRule.ptype=='2' }">selected="selected"</c:if>>绑定手机SID</option>
		  					</select>
		  				</td>
		  				<input type="hidden" id="sysUser-userid-edit-usercontrolList" name="sysLoginRule.userid" value="${sysLoginRule.userid}">
		  			</tr>
		  			<tr>
		  				<td style="text-align: right;">手机SID:</td>
		  				<td colspan="3" style="text-align: left;">
		  					<input type="text" name="sysLoginRule.psid" style="width: 350px;" value="${sysLoginRule.psid }">
		  				</td>
		  			</tr>
		  			<tr>
		  				<td colspan="2" style="text-align: left;">
		  					注：<br/>
		  					1.内网登录需要填写IP地址段，不填写默认192.168.*.*IP地址都可以登录。<br/>
		  					2.选择指定IP地址登录将默认指定用户最近一次登录的IP地址<br/>
		  					3.绑定手机SID，将使用用户最近一次登录的手机SID
		  				</td>
		  			</tr>
		  		</table>
		   </form>
	    </div>
  		<div data-options="region:'south',border:false">
  			<div class="buttonDetailBG" style="height:30px;text-align: right;">
	  			<input type="button" value="确 定" name="savegoon" id="sysUser-form-editbutton-usercontrolList" />
  			</div>
  		</div>
  	</div>
   <script type="text/javascript">
		$(function(){
			$("#sysUser-form-edit-usercontrolList").form({  
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
			    		$.messager.alert("提醒",'修改成功！');
			    		$("#sysuser-accesscontrol-rule-table").datagrid("reload");
			    		$('#sysuser-accesscontrol-rule-detail').dialog("close");
			    	}else{
			    		$.messager.alert("提醒",'修改失败！');
			    	}
			    }  
			});
			$("#sysUser-form-editbutton-usercontrolList").click(function(){
				$.messager.confirm("提醒", "是否修改用户登录规则?", function(r){
					if (r){
						$("#sysUser-form-edit-usercontrolList").submit();
					}
				});
			});
			$("#sysuser-loginrule-logintype").change(function(){
				var logintype = $(this).val();
				if(logintype=="1"){
					$("#sysuser-loginrule-iplocation-tr").show();
					$("#sysuser-loginrule-ip-tr").hide();
				}else if(logintype=="2"){
					$("#sysuser-loginrule-iplocation-tr").hide();
					$("#sysuser-loginrule-ip-tr").hide();
				}else if(logintype=="3"){
					$("#sysuser-loginrule-iplocation-tr").hide();
					$("#sysuser-loginrule-ip-tr").show();
				}
				
			});
		});
   </script>
  </body>
</html>
