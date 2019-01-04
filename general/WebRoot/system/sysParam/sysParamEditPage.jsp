<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>系统参数修改</title>
  </head>
  
  <body>
  	<div class="easyui-layout" data-options="fit:true,border:true" id="sysParam-layout-addParam">
  		<div data-options="region:'center',split:true,border:false">
  			<form action="sysParam/editSysParam.do" method="post" id="sysCode-form-editParam">
		    	<input type="hidden" name="sysParam.paramid" value="${sysParam.paramid }"/>
		    	<table cellpadding="0" cellspacing="5" border="0" class="pageContent">
	    			<tr>
	    				<td><label>参数名称:</label></td>
	    				<td><input type="text" id="pname" name="sysParam.pname" value="${sysParam.pname }" class="easyui-validatebox" required="true" style="width:200px;" maxlength="50" readonly="readonly"/></td>
	    			</tr>
	    			<tr>
	    				<td><label>参数描述:</label></td>
	    				<td><textarea name="sysParam.description" class="easyui-validatebox" required="true" style="width:198px;height:35px;">${sysParam.description }</textarea></td>
	    			</tr>
	    			<tr>
	    				<td><label>参数值:</label></td>
	    				<td><input type='text' name='sysParam.pvalue' value='${sysParam.pvalue }' class='easyui-validatebox' required='true' style='width:200px;'/></td>
	    			</tr>
	    			<tr>
	    				<td><label>参数值描述:</label></td>
	    				<td><textarea name="sysParam.pvdescription" class="easyui-validatebox" required="true" style="width:198px;height:35px;">${sysParam.pvdescription }</textarea></td>
	    			</tr>
	    			<tr>
	    				<td><label>参数用途:</label></td>
	    				<td><textarea name="sysParam.puser" class="easyui-validatebox" required="true" style="width:198px;height:38px;">${sysParam.puser }</textarea></td>
	    			</tr>
	    			<tr>
		   				<td><label>模块名称:</label></td>
		   				<td><input type="text" name="sysParam.moduleid" id="sysParam-moduleid-widget" value="${sysParam.moduleid }"/></td>
		   			</tr>
	    			<tr>
	    				<td><label>状态:</label></td>
	    				<td><select name="sysParam.state" style="width:204px;">
	    				<option value="1" <c:if test="${sysParam.state=='1' }">selected="selected"</c:if> >启用</option>
	    				<option value="0" <c:if test="${sysParam.state=='0' }">selected="selected"</c:if> >停用</option>
	    				</select></td>
	    			</tr>
	    		</table>
		    </form>
  		</div>
  		<div data-options="region:'south',border:true" style="height: 30px;" align="right">
  			<a href="javaScript:void(0);" id="sysCode-save-editParam" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'">确定</a>
  		</div>
  	</div>
    <script type="text/javascript">
    	$(function(){
    		loadDropdown();
    		//验证模块ID是否存在
    		$.extend($.fn.validatebox.defaults.rules, {
    			moduleExist:{
    				validator:function(value){
    					var sTrue="true";
    					var ret="";
    					$.ajax({   
				            url :'sysParam/verifyCode.do?codename='+value,
				            type:'post',
				            async:false,
				            dataType:'json',
				            success:function(json){
				            	ret=json.flag;
				            }
				        });
				        return ret.toString()== sTrue.toString();
    				},
    				message:'该模块不存在，请在下列框中选择模块!'
    			}
    		});
    		//验证参数值格式
    		$.extend($.fn.validatebox.defaults.rules, {
    			pvalueVerify:{
    				validator:function(value){
    					var reg=/^[A-Za-z0-9]{1,10}$/  //匹配由数字和26个英文字母组成的字符串 
    					return reg.test(value);
    				},
    				message:'参数值格式不正确,请输入英文或数字,最多10位!'
    			}
    		});
    		$("#sysCode-form-editParam").form({
    			onSubmit: function(){
    				var flag = $(this).form('validate');
    				if(flag==false){
    					return false;
    				}
    			},
    			success: function(data){
    				var json=$.parseJSON(data);
    				if(json.flag == true){
    					$.messager.alert("提醒","修改成功！");
    					$("#sysCode-dialog-paramOper").dialog('close',true);
    					$("#sysCode-table-showParamList").datagrid('reload');
    				}else{
    					$.messager.alert("提醒","修改失败！");
    				}
    			}
    		});
    		$("#sysCode-save-editParam").click(function(){
    			$.messager.confirm("提醒","是否修改系统参数信息?", function(r){
    				if(r){
    					var ret=$("#sysCode-form-editParam").submit();
    				}
    			});
    		});
    	});
    </script>
  </body>
</html>
