<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>出入库类型详情页面</title>
  </head>
  
  <body>
  	<form action="" method="post" id="storageInout-form-add">
  	<div class="pageContent" style="width:500px;">
	    	<p>
	    		<label style="text-align: right;">编码:</label>
	    		<input type="text" name="storageInout.id" class="easyui-validatebox" validType="validID[20]" required="true" style="width:200px;" maxlength="20"/>
	    	</p>
	    	<p>
	    		<label style="text-align: right;">名称:</label>
	    		<input type="text" name="storageInout.name" class="easyui-validatebox" required="true" style="width:200px;" validType="validName[50]" maxlength="50"/>
	    	</p>
	    	<p>
	    		<label style="text-align: right;">类别:</label>
	    		<select name="storageInout.type" style="width:200px;">
					<option value="1" <c:if test="${storageInout.type=='1'}">selected="selected"</c:if>>入库</option>
					<option value="2" <c:if test="${storageInout.type=='2'}">selected="selected"</c:if>>出库</option>
				</select>
	    	</p>
	    	<p>
	    		<label style="text-align: right;">相关单位:</label>
	    		<select name="storageInout.referunit" style="width:200px;">
   					<option <c:if test="${storageInout.referunit==null}">selected="selected"</c:if>></option>
					<option value="1" <c:if test="${storageInout.referunit=='1'}">selected="selected"</c:if>>供应商</option>
					<option value="2" <c:if test="${storageInout.referunit=='2'}">selected="selected"</c:if>>客户</option>
					<option value="3" <c:if test="${storageInout.referunit=='3'}">selected="selected"</c:if>>仓库</option>
					<option value="4" <c:if test="${storageInout.referunit=='4'}">selected="selected"</c:if>>部门</option>
				</select>
	    	</p>
	    	<p style="height: auto;">
	    		<label style="text-align: right;">备注:</label>
	    		<textarea name="storageInout.remark" style="height: 100px;width: 200px;">${storageInout.remark}</textarea>
	    	</p>
	    	<p>
	    		<label style="text-align: right;">状态:</label>
	    		<select name="storageInout.state" style="width:200px;" disabled="disabled">
					<option value="4" selected="selected">新增</option>
					<option value="3">暂存</option>
					<option value="2">保存</option>
					<option value="1">启用</option>
					<option value="0">禁用</option>
				</select>
	    	</p>
	    	<p>
	    		<label style="text-align: right;">是否系统预制:</label>
	    		<select name="storageInout.issystem" style="width:200px;">
					<option value="1" <c:if test="${storageInout.issystem=='1'}">selected="selected"</c:if>>是</option>
					<option value="0" <c:if test="${storageInout.issystem=='0'}">selected="selected"</c:if>>否</option>
				</select>
	    	</p>
	    </div>
   	</form>
   	<script type="text/javascript">
    	//检验输入值的最大长度
		$.extend($.fn.validatebox.defaults.rules, {
			validID:{
				validator : function(value,param) {
					var reg=eval("/^[A-Za-z0-9]{0,"+param[0]+"}$/");
					if(reg.test(value)){
						var ret = ajaxCall({id:value},'basefiles/checkStorageInoutID.do');
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
						var ret = ajaxCall({name:value},'basefiles/checkStorageInoutName.do');
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
    		$("#storageInout-form-add").form({  
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
			    		$("#storageInout-panel").panel({  
							fit:true, 
							title: '出入库类型详情',
							cache: false,
							href : "basefiles/showStorageInoutInfo.do?id="+json.id
						});
						$('#storageInout-table-list').datagrid("reload");
			    	}else{
			    		$.messager.alert("警告",'新增失败');
			    	}
			    }  
			}); 
    	});
    </script>
  </body>
</html>
