<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>系统参数添加</title>
  </head> 
  
  <body>
  	<div class="easyui-layout" data-options="fit:true,border:true" id="sysParam-layout-addParam">
  		<div data-options="region:'center',split:true,border:false">
  			<form action="sysParam/addSysParam.do" method="post" id="sysCode-form-addParam">
	  			<table cellpadding="0" cellspacing="4" border="0" class="pageContent">
	    			<tr>
	    				<td><label>参数名称:</label></td>
	    				<td><input type="text" id="pname" name="sysParam.pname" class="easyui-validatebox" required="true" validType="paramExist" style="width:200px;" maxlength="50"/></td>
	    			</tr>
	    			<tr>
	    				<td style=""><label>参数描述:</label></td>
	    				<td><textarea rows="" cols="" name="sysParam.description" class="easyui-validatebox" required="true" style="width:198px;height:35px;"></textarea></td>
	    			</tr>
	    			<tr>
	    				<td><label>参数值:</label></td>
	    				<td><input type="text" name="sysParam.pvalue" class="easyui-validatebox" required="true" style="width:200px;"/></td>
	    			</tr>
	    			<tr>
	    				<td><label>参数值描述:</label></td>
	    				<td><textarea name="sysParam.pvdescription" class="easyui-validatebox" required="true" style="width:198px;height:35px;"></textarea></td>
	    			</tr>
	    			<tr>
	    				<td><label>参数用途:</label></td>
	    				<td><textarea name="sysParam.puser" class="easyui-validatebox" required="true" style="width:198px;height:38px;"></textarea></td>
	    			</tr>
	    			<tr>
	    				<td><label>模块名称:</label></td>
	    				<td><input type="text" name="sysParam.moduleid" id="sysParam-moduleid-widget" value="${moduleid }"/></td>
	    			</tr>
	    			<tr>
	    				<td><label>状态:</label></td>
	    				<td><select name="sysParam.state" style="width:204px;">
		    				<option value="1" selected="selected">启用</option>
		    				<option value="0">停用</option>
	    				</select></td>
	    			</tr>
	    		</table>
  			</form>
  		</div>
  		<div data-options="region:'south',border:true" style="height: 30px;" align="right">
  			<a href="javaScript:void(0);" id="sysCode-save-addParam" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'">确定</a>
  		</div>
  	</div>
    <script type="text/javascript">
    	$(function(){
    		loadDropdown();
    		//验证参数名是否存在
    		$.extend($.fn.validatebox.defaults.rules, {
    			paramExist:{
    				validator:function(value){
    					var ret;
    					var reg=/^[A-Za-z0-9]{1,50}$/  //匹配由数字和26个英文字母组成的字符串 
    					if(reg.test(value)){
    						$.ajax({   
					            url :'sysParam/searchPname.do?pname='+value,
					            type:'post',
					            async:false,
					            dataType:'json',
					            success:function(json){
					            	ret = json.flag;
					            }
					        });
					        if(!ret){
					        	$.fn.validatebox.defaults.rules.paramExist.message = '该参数名称已存在!';
					        }
					        else{
					        	return true;
					        }
    					}
    					else{
    						$.fn.validatebox.defaults.rules.paramExist.message = '参数名称格式不正确,请输入英文或数字,最多50位!';
    					}
    				},
    				message:''
    			}
    		});
    		//验证模块ID是否存在
    		$.extend($.fn.validatebox.defaults.rules, {
    			moduleExist:{
    				validator:function(value){
    					$.ajax({   
				            url :'sysParam/verifyCode.do?codename='+value,
				            type:'post',
				            async:false,
				            dataType:'json',
				            success:function(json){
				            	return json.flag;
				            }
				        });
    				},
    				message:'该模块不存在，请在下列框中选择模块!'
    			}
    		});
    		//验证参数值格式 
    		//$.extend($.fn.validatebox.defaults.rules, {
    		//	pvalueVerify:{
    		//		validator:function(value){
    		//			var reg=/^[A-Za-z0-9]{1,10}$/  //匹配由数字和26个英文字母组成的字符串 
    		//			return reg.test(value);
    		//		},
    		//		message:'参数值格式不正确,请输入英文或数字,最多10位!'
    		//	}
    		//});
    		$("#sysCode-form-addParam").form({
    			onSubmit: function(){
    				var flag = $(this).form('validate');
    				if(flag==false){
    					return false;
    				}
    			},
    			success:function(data){
    				//$.parseJSON()解析JSON字符串 
    				var json = $.parseJSON(data);
    				if(json.flag==true){
    					$.messager.alert("提醒","添加成功!");
    					$("#sysCode-dialog-paramOper").dialog('close',true);
    					$("#sysCode-table-showParamList").datagrid('reload');
    				}
    				else{
    					$.messager.alert("提醒","添加失败!");
    				}
    			}
    		});
    		$("#sysCode-save-addParam").click(function(){
    			$.messager.confirm("提醒","是否添加参数信息?",function(r){
    				if(r){
    					$("#sysCode-form-addParam").submit();
    				}
    			});
    		});
    	});
    </script>
  </body>
</html>
