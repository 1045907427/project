<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@include file="/include.jsp" %>  
    <title>计量单位复制页面</title>
  </head>
  
 <body>
    <form action="" method="post" id="meteringUnit-form-copyMeteringUnit">
    	<table cellpadding="2" cellspacing="2" border="0">
    		<tr>
    			<td>编码:</td>
    			<td><input id="meteringUnit-input-id" name="meteringUnit.id" class="easyui-validatebox" validType="validID[20]" style="width: 200px;"/>
    				<input id="meteringUnit-hidden-state" type="hidden" name="meteringUnit.state"/>
    			</td>
    		</tr>
    		<tr>
    			<td>名称:</td>
    			<td><input name="meteringUnit.name" class="easyui-validatebox" validType="validName[20]" style="width: 200px;"/>
    			</td>
    		</tr>
    		<tr>
    			<td>备注:</td>
    			<td><textarea name="meteringUnit.remark" class="easyui-validatebox" validType="maxLen[200]" style="height:80px;width: 195px;overflow: hidden"><c:out value="${meteringUnit.remark }"></c:out></textarea></td>
    		</tr>
    		<tr>
    			<td>状态:</td>
    			<td><input id="common-combobox-state" value="4" disabled="disabled" class="easyui-combobox" style="width: 200px" /></td>
    		</tr>
    	</table>
    </form>
    <script type="text/javascript">
    	//检验输入值的最大长度
		$.extend($.fn.validatebox.defaults.rules, {
			validID:{
				validator : function(value,param) {
					var reg=eval("/^[A-Za-z0-9]{0,"+param[0]+"}$/");
					if(reg.test(value)){
						var ret = meteringUnit_AjaxConn({id:value},'basefiles/isRepeatMUID.do');
						var retJson = $.parseJSON(ret);
						if(retJson.flag){
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
						var ret = meteringUnit_AjaxConn({name:value},'basefiles/isRepeatMUName.do');
						var retJson = $.parseJSON(ret);
						if(retJson.flag){
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
		
		//暂存复制新增计量单位 
	function addMeteringUnitHold(){
		$("#meteringUnit-hidden-state").val(3);
		$("#meteringUnit-form-copyMeteringUnit").submit();
	}
	
	$("#meteringUnit-form-copyMeteringUnit").form({
		url:'basefiles/addMeteringUnitHold.do',
  		onSubmit: function(){
  			loading("新增暂存中..");
  		},
  		success:function(data){
  			loaded();
  			//$.parseJSON()解析JSON字符串 
  			var json = $.parseJSON(data);
  			if(json.flag){
  				$("#meteringUnit-button-layout").buttonWidget("addNewDataId",$("#meteringUnit-input-id").val());
  				$.messager.alert("提醒","新增暂存成功!");
  				$("#meteringUnit-table-list").datagrid('reload');
  				refreshLayout('计量单位【详情】', 'basefiles/showMeteringUnitInfoPage.do?id='+$("#meteringUnit-input-id").val());
				$("#meteringUnit-opera").attr("value","add");
  			}
  			else{
  				$.messager.alert("提醒","新增暂存失败!");
  			}
  		}
  	});
  	//保存复制新增计量单位 
  	function addMeteringUnitSave(){
  		if(!$("#meteringUnit-form-copyMeteringUnit").form('validate')){
			return false;
		}
		$("#meteringUnit-hidden-state").val(1);
		var ret = meteringUnit_AjaxConn($("#meteringUnit-form-copyMeteringUnit").serializeJSON(),'basefiles/addMeteringUnitSave.do');
		var retJson = $.parseJSON(ret);
		if(retJson.flag){
			$("#meteringUnit-button-layout").buttonWidget("addNewDataId","id"+meteringUnit_i++);
			$("#meteringUnit-table-list").datagrid('reload');
			refreshLayout('计量单位【详情】', 'basefiles/showMeteringUnitInfoPage.do?id='+$("#meteringUnit-input-id").val());
			$("#meteringUnit-opera").attr("value","add");
			$.messager.alert("提醒","新增保存成功!");
		}
		else{
			$.messager.alert("提醒","新增保存失败!");
		}
  	}
    	$(function(){
    		//状态
			$('#common-combobox-state').combobox({
			    url:'common/sysCodeList.do?type=state',   
			    valueField:'id',   
			    textField:'name'  
			});
			
			$("#meteringUnit-button-layout").buttonWidget("initButtonType","add");
    	});
    </script>
  </body>
</html>
