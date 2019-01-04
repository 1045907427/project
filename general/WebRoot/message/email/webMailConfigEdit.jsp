<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>配置邮件账户</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">

  </head>
  	
  <body>
  		<div style="height:100%">
			<form action="message/email/editWebMailConfig.do" method="post" id="messageEmail-form-editWebMailConfig">
			<div class="pageContent" style="padding:5px; ">						
				<div style="hiehgt:30px;padding:5px 0;">
					<div style="float:left;width:130px;line-height:22px;">电子邮件地址：</div>
					<div style="float:left">
						<input type="text" id="messageEmail-form-editWebMailConfig-email" name="webMailConfig.email" value="${webMailConfig.email }" class="easyui-validatebox" required="true" style="width:350px;"/>
					</div>
					<div style="clear:both"></div>
				</div>
				<div style="hiehgt:auto;padding:5px 0; ">
					<div style="float:left;width:130px;line-height:22px;">接收服务器(POP3)：</div>
					<div style="float:left">
						<input type="text" id="messageEmail-form-editWebMailConfig-popserver" name="webMailConfig.popserver" value="${webMailConfig.popserver }" class="easyui-validatebox" required="true" style="width:200px;"/>
					</div>
					<div style="float:left">
						&nbsp;端口:<input type="text" id="messageEmail-form-editWebMailConfig-popport" name="webMailConfig.popport" value="${webMailConfig.popport }" class="easyui-validatebox" required="true" value="110" style="width:50px;"/>
					</div>
					<div style="clear:both;margin-left:130px;">
						<input type="checkbox" id="messageEmail-form-editWebMailConfig-ispopssl" name="webMailConfig.ispopssl" value="1" <c:if test="${webMailConfig.ispopssl == 1}"> checked="checked" </c:if> /><label  style="float:none;"  for="webMailConfig.ispopssl">此服务器要求安全连接(SSL)</label>
					</div>
					<div style="clear:both"></div>
				</div>
				<div style="hiehgt:auto;padding:5px 0;">
					<div style="float:left;width:130px;line-height:22px;">发送服务器(SMTP)：</div>
					<div style="float:left">
						<input type="text" id="messageEmail-form-editWebMailConfig-smtpserver" name="webMailConfig.smtpserver" value="${webMailConfig.smtpserver }" class="easyui-validatebox" required="true" style="width:200px;"/>
					</div>
					<div style="float:left">
						&nbsp;端口:<input type="text" id="messageEmail-form-editWebMailConfig-smtpport" name="webMailConfig.smtpport" value="${webMailConfig.smtpport }" class="easyui-validatebox" required="true" value="25" style="width:50px;"/>
					</div>
					<div style="clear:both;margin-left:130px;">
						<input type="checkbox" id="messageEmail-form-editWebMailConfig-issmtpssl" name="webMailConfig.issmtpssl" value="1" <c:if test="${webMailConfig.issmtpssl == 1}"> checked="checked" </c:if>/><label  style="float:none;"  for="webMailConfig.ispopssl">此服务器要求安全连接(SSL)</label>
					</div>
					<div style="clear:both"></div>
				</div>
				<div style="hiehgt:auto;padding:5px 0;">
					<div style="float:left;width:130px;line-height:22px;">登陆账户：</div>
					<div style="float:left">
						<input type="text" id="messageEmail-form-editWebMailConfig-emailuser" name="webMailConfig.emailuser" value="${webMailConfig.emailuser }" class="easyui-validatebox" required="true" style="width:150px;"/>
					</div>
					<div style="clear:both"></div>
				</div>
				<div style="hiehgt:auto;">
					<div style="float:left;width:130px;line-height:22px;">登录密码：</div>
					<div style="float:left">
						<input type="password" id="messageEmail-form-editWebMailConfig-emailpwd" name="webMailConfig.emailpwd" value="${webMailConfig.emailpwd }" class="easyui-validatebox" required="true" style="width:150px;"/>
					</div>
					<div style="clear:both"></div>
				</div>
				<div style="hiehgt:auto;padding:5px 0;">
					<div style="float:left;width:130px;line-height:22px;">SMTP需要身份验证：</div>
					<div style="float:left">
						<select id="messageEmail-form-editWebMailConfig-issmtppass" name="webMailConfig.issmtppass">
							<option value="1" <c:if test="${webMailConfig.issmtppass == 1}">selected="selected"</c:if> >是</option>
							<option value="0" <c:if test="${webMailConfig.issmtppass != 1}">selected="selected"</c:if> >否</option>
						</select>
					</div>
					<div style="clear:both"></div>
				</div>
				<div style="hiehgt:auto;padding:5px 0;">
					<div style="float:left;width:130px;line-height:22px;">自动收取外部邮件：</div>
					<div style="float:left">
						<select id="messageEmail-form-editWebMailConfig-autorecvflag" name="webMailConfig.autorecvflag">
							<option value="1" <c:if test="${webMailConfig.autorecvflag == 1}">selected="selected"</c:if>>是</option>
							<option value="0" <c:if test="${webMailConfig.autorecvflag != 1}">selected="selected"</c:if>>否</option>
						</select>
					</div>
					<div style="clear:both"></div>
				</div>
				<div style="hiehgt:auto;padding:5px 0;">
					<div style="float:left;width:130px;line-height:22px;">邮箱容量(MB)：</div>
					<div style="float:left">
						<input type="text" id="messageEmail-form-editWebMailConfig-quotalimit" name="webMailConfig.quotalimit" value="${webMailConfig.quotalimit }" class="easyui-validatebox" required="true" validatetype="number" style="width:50px;" maxlength="7"/>
					</div>
					<div style="clear:both"></div>
				</div>
				<div style="hiehgt:auto;padding:5px 0;">
					<div style="float:left;width:130px;line-height:22px;">默认邮箱：</div>
					<div style="float:left">
						<input type="checkbox" id="messageEmail-form-editWebMailConfig-isdefault" name="webMailConfig.isdefault" value="1" <c:if test="${webMailConfig.isdefault == 1}"> checked="checked" </c:if>/>
						<label style="float:none;" for="messageEmail-form-editWebMailConfig-isdefault">做为内部邮件外发默认邮箱</label>（必须设置账户密码）
					</div>
					<div style="clear:both"></div>
				</div>
				<div style="hiehgt:auto;padding:5px 0;">
					<div style="float:left;width:130px;line-height:22px;">收信删除：</div>
					<div style="float:left">
						<input type="checkbox" id="messageEmail-form-editWebMailConfig-isrecvdel" name="webMailConfig.isrecvdel" value="1" <c:if test="${webMailConfig.isrecvdel == 1}"> checked="checked" </c:if> />
						<label style="float:none;"  for="messageEmail-form-editWebMailConfig-quotalimit">收取邮件后从服务器上删除</label>
					</div>
					<div style="clear:both"></div>
				</div>
				<div style="hiehgt:auto;padding:5px 0;">
					<div style="float:left;width:130px;line-height:22px;">新邮件提醒：</div>
					<div style="float:left">
						<input type="checkbox" id="messageEmail-form-editWebMailConfig-isrecvmsg" name="webMailConfig.isrecvmsg" value="1" <c:if test="${webMailConfig.isrecvmsg == 1}" > checked="checked" </c:if> />
						<label style="float:none;"  for="messageEmail-form-editWebMailConfig-quotalimit">收到新邮件后使用内部短信提醒</label>
					</div>
					<div style="clear:both"></div>
				</div>
				<div style="height:auto;text-align:center;padding:5px 0;">
					<input type="hidden" name="webMailConfig.id" value="${webMailConfig.id }" />
		    		<a href="javaScript:void(0);" id="messageEmail-form-editWebMailConfig-btn-saveWebMailConfig" class="easyui-linkbutton" data-options="plain:false,iconCls:'button-save'">保存</a>
				</div>
				<div style="clear:both"></div>
			</div>
			</form>
		</div>
		<script type="text/javascript">
			$(document).ready(function(){
				$("#messageEmail-form-editWebMailConfig-btn-saveWebMailConfig").click(function(){
					$.messager.confirm("提醒","是否保存此Internet邮箱配置?",function(r){
	    				if(r){		    							
				    		$("#messageEmail-form-editWebMailConfig").submit();
	    				}
		    		});
				});
				$("#messageEmail-form-editWebMailConfig").form({
		    			onSubmit: function(){
		    				var flag = $(this).form('validate');
		    				if(flag==false){
		    					return false;
		    				}
		    			},
		    			success:function(data){
		    				//$.parseJSON()解析JSON字符串 
		    				var json=$.parseJSON(data);
		    				if(json.flag==true){
		    					$.messager.alert("提醒","编辑成功!");
		    					$("#messageEmail-table-webMailConfigList").datagrid('reload');
		    					$("#messageEmail-dialog-webMailConfigOper").dialog('close');
		    				}
		    				else{
		    					$.messager.alert("提醒",( json.msg|| "编辑失败！"));
		    				}
		    			}
		    	});
			});
		</script>
  </body>
</html>