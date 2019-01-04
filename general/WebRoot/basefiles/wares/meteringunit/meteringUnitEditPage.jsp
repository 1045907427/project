<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <%@include file="/include.jsp" %>  
    <title>计量单位修改页面</title>
  </head>
  
  <body>
    <body>
    <form action="" method="post" id="meteringUnit-form-editMeteringUnit">
    	<table cellpadding="2" cellspacing="2" border="0">
    		<tr>
    			<td>编码:</td>
    			<td><input id="meteringUnit-input-id" name="meteringUnit.id" disabled="disabled" value="<c:out value="${meteringUnit.id }"></c:out>" class="easyui-validatebox" validType="validID[20]" style="width: 200px;"/>
    				<input id="meteringUnit-hidden-state" type="hidden" name="meteringUnit.state"/>
    				<input id="meteringUnit-hidden-oldId" type="hidden" name="meteringUnit.oldId" value="<c:out value="${oldId }"></c:out>"/>
    				<input id="meteringUnit-hidden-hdLockFlag" type="hidden" value="${lockFlag }"/>
    				<input id="meteringUnit-name" type="hidden" value="<c:out value="${meteringUnit.name }"></c:out>"/>
    			</td>
    		</tr>
    		<tr>
    			<td>名称:</td>
    			<td><input id="meteringUnit-input-name" name="meteringUnit.name" value="<c:out value="${meteringUnit.name }"></c:out>" class="easyui-validatebox" validType="validName[20]" style="width: 200px;"/></td>
    		</tr>
    		<tr>
    			<td>备注:</td>
    			<td><textarea name="meteringUnit.remark" class="easyui-validatebox" validType="maxLen[200]" style="height:80px;width: 195px;overflow: hidden"><c:out value="${meteringUnit.remark }"></c:out></textarea></td>
    		</tr>
    		<tr>
    			<td>状态:</td>
    			<td><input id="common-combobox-state" value="${meteringUnit.state }" disabled="disabled" class="easyui-combobox" style="width: 200px" /></td>
    		</tr>
    	</table>
    </form>
    <script type="text/javascript">
    
	//检验输入值的最大长度
	$.extend($.fn.validatebox.defaults.rules, {
		validID:{
			validator : function(value,param) {
				if($("#meteringUnit-hidden-oldId").val() == $("#meteringUnit-input-id").val()){
					return true;
				}
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
				if($("#meteringUnit-name").val() == $("#meteringUnit-input-name").val()){
					return true;
				}
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
	
	//暂存修改计量单位 
	function editMeteringUnitHold(){
		$("#meteringUnit-hidden-state").val(3);
		var ret = meteringUnit_AjaxConn($("#meteringUnit-form-editMeteringUnit").serializeJSON(),'basefiles/editMeteringUnitHold.do');
		var retJson = $.parseJSON(ret);
		if(retJson.flag){
			$("#meteringUnit-table-list").datagrid('reload');
			refreshLayout('计量单位【详情】', 'basefiles/showMeteringUnitInfoPage.do?id='+$("#meteringUnit-input-id").val());
			$("#meteringUnit-opera").attr("value","view");
			$.messager.alert("提醒","计量单位修改暂存成功!");
		}
		else{
			$.messager.alert("提醒","计量单位修改暂存失败!");
		}
	}
	
	//保存修改计量单位 
	function editMeteringUnitSave(){
		if(!$("#meteringUnit-form-addMeteringUnit").form('validate')){
			return false;
		}
		if("${meteringUnit.state}" == "1"){
			$("#meteringUnit-hidden-state").val(1);
		}
		else{
			$("#meteringUnit-hidden-state").val(2);
		}
		var ret = meteringUnit_AjaxConn($("#meteringUnit-form-editMeteringUnit").serializeJSON(),'basefiles/editMeteringUnitSave.do');
		var retJson = $.parseJSON(ret);
		if(retJson.flag){
			$("#meteringUnit-table-list").datagrid('reload');
			refreshLayout('计量单位【详情】', 'basefiles/showMeteringUnitInfoPage.do?id='+$("#meteringUnit-input-id").val());
			$("#meteringUnit-opera").attr("value","view");
			$.messager.alert("提醒","计量单位修改保存成功!");
		}
		else{
			$.messager.alert("提醒","计量单位修改保存失败!");
		}
	}
    	$(function(){
    		//状态
			$('#common-combobox-state').combobox({
			    url:'common/sysCodeList.do?type=state',   
			    valueField:'id',   
			    textField:'name'  
			});
			//判断是否加锁 
			if("${lockFlag}" == "0"){//加锁
  				$.messager.alert("提醒","数据已加锁!");
  				return false;
  			}
  			
  			$("#meteringUnit-button-layout").buttonWidget("setDataID",{id:$("#meteringUnit-hidden-oldId").val(),state:"${meteringUnit.state}",type:"edit"});
    	});
    </script>
  </body>
  </body>
</html>
