<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <%@include file="/include.jsp" %> 
    <title>修改密码</title>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<div data-options="region:'center',split:false,border:false,fit:true" style="padding: 15px;">
	    	<form action="accesscontrol/modifySysUserPwd.do" method="post" id="sysUser-form-modifyPwd">
	    		<table cellpadding="2" cellspacing="2" border="0">
	    			<tr>
	    				<td>输入原密码：</td>
	    				<td><input id="sysUser-pwd-oldPwd" type="password" name="user.password" class="easyui-validatebox" required="true" style="width: 150px"/>
	    					<input id="sysUser-hidden-userid" name="user.userid" type="hidden" value="${user.userid }"/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td>输入新密码：</td>
	    				<td><input id="sysUser-pwd-newPwd" type="password" name="user.newpassword" class="easyui-validatebox" required="true" validType="safepass" style="width: 150px"/></td>
	    			</tr>
	    			<tr>
	    				<td>再输入新密码：</td>
	    				<td><input id="sysUser-pwd-againPwd" type="password" class="easyui-validatebox" required="true" validType="againPassword" style="width: 150px"/></td>
	    			</tr>
	    		</table>
	    	</form>
    	</div>
    	<div data-options="region:'south'" style="text-align: right;height: 28px">
    		<a href="javaScript:void(0);" id="sysUser-modify-modifyPwdMenu" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'" title="修改密码">修改</a>
    	</div>
    </div>
    <script type="text/javascript">
    var sysUserModifyPwd_AjaxConn = function (Data, Action) {
	    var MyAjax = $.ajax({
	        type: 'post',
	        cache: false,
	        url: Action,
	        data: Data,
	        async: false
	    })
	    return MyAjax.responseText;
	}
    	$.extend($.fn.validatebox.defaults.rules, {
   			againPassword:{
   				validator : function(value,param) {
   					return value == $("#sysUser-pwd-newPwd").val();
		        }, 
		        message : '两次输入的密码不一致!' 
   			}
    	});
    	
    	//修改密码
    	$("#sysUser-modify-modifyPwdMenu").click(function(){
    		$.messager.confirm('提醒','确定修改吗?',function(r){
    			if(r){
    				if($("#sysUser-form-modifyPwd").form('validate')){
    					var ret = sysUserModifyPwd_AjaxConn($("#sysUser-form-modifyPwd").serializeJSON(),'accesscontrol/modifySysUserPwd.do');
    					var retJSON = $.parseJSON(ret);
    					if(retJSON.flag){
				    		$("#sysUser-dialog-pwdOperate").dialog('close',true);
				    		alert("密码修改成功,请重新登录!");
				    		var url = "logout.do";
							$.ajax( {
								url : url,
								type : 'post',
								dataType : 'json',
								success : function(json) {
									if (json.flag == true) {
										location.href = "logout_safe";
									}
								}
							});
    					}
    					else{
    						$.messager.alert("提醒",''+retJSON.Mes+'密码修改失败!');
    					}
    				}
    			}
    		});
		});
    </script>
  </body>
</html>
