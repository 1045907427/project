<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>仓库类型添加页面</title>
  </head>
  
  <body>
    <form action="" method="post" id="storageType-form-add">
    	<div class="pageContent" style="width:500px;">
		   	<p>
		   		<label style="text-align: right;">编码:</label>
		   		<input type="text" name="storageType.id" class="easyui-validatebox" validType="validID[20]" required="true" style="width:200px;" maxlength="20"/>
		   	</p>
		   	<p>
		   		<label style="text-align: right;">名称:</label>
		   		<input type="text" name="storageType.name" class="easyui-validatebox" required="true" style="width:200px;" validType="validName[50]" maxlength="50"/>
		   	</p>
		   	<p style="height: auto;">
		   		<label style="text-align: right;">备注:</label>
		   		<textarea name="storageType.remark" style="height: 100px;width: 200px;"></textarea>
		   	</p>
		   	<p>
		   		<label style="text-align: right;">状态:</label>
		   		<select name="storageType.state" style="width:200px;" disabled="disabled">
					<option value="4" selected="selected">新增</option>
				</select>
		   	</p>
		</div>
    </form>
    <script type="text/javascript">
    	$("#storageType-button").buttonWidget("initButtonType","add");
    	
    	//检验输入值的最大长度
		$.extend($.fn.validatebox.defaults.rules, {
			validID:{
				validator : function(value,param) {
					var reg=eval("/^[A-Za-z0-9]{0,"+param[0]+"}$/");
					if(reg.test(value)){
						var ret = ajaxCall({id:value},'basefiles/checkStorageTypeID.do');
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
						var ret = ajaxCall({name:value},'basefiles/checkStorageTypeName.do');
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
    		$("#storageType-form-add").form({  
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
			    		$("#storageType-panel").panel({  
							fit:true, 
							title: '仓库类型详情',
							cache: false,
							href : "basefiles/showStorageTypeInfo.do?id="+json.id
						});
						$('#storageType-table-list').datagrid("reload");
			    	}else{
			    		$.messager.alert("警告",'新增失败');
			    	}
			    }  
			}); 
    	});
    </script>
  </body>
</html>
