<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售方式修改页面</title>
  </head>
  
  <body class="easyui-layout">
     <div region="north" data-options="border:true" style="height:50px;padding: 10px;">
     	<div style="border: 1px black solid;">
     		<form action="" method="post" id="salemode-form-edit">
	     		<table cellpadding="2" cellspacing="2" border="0px">
		    		<tr>
		    			<td width="80px" align="right">编码:</td>
		    			<td><input id="saleMode-id-saleModeDetail" type="text" name="saleMode.id" style="width: 120px" class="easyui-validatebox" validType="validID[20]" required="true" <c:if test="${showMap.id!=null}">value="<c:out value="${saleMode.id}"></c:out>"</c:if>/>
		    				<input type="hidden" id="saleMode-oldid" name="saleMode.oldId" value="<c:out value="${saleMode.id }"></c:out>" />
		    				<input type="hidden" id="saleMode-name" value="<c:out value="${saleMode.name }"></c:out>" />
		    				<input type="hidden" name="saleMode.state" value="${saleMode.state}"/>
		    				<input id="saleMode-id-hdSaleModeDetail" type="hidden" value="<c:out value="${saleMode.id }"></c:out>"/>
		    				<input id="saleMode-code-hdSaleModeDetail" type="hidden"/>
		    				<input id="saleMode-name-hdSaleModeDetail" type="hidden"/>
		    			</td>
		    			<td width="80px" align="right">名称:</td>
		    			<td><input id="saleMode-name-saleModeDetail" type="text" name="saleMode.name" style="width: 120px" class="easyui-validatebox" validType="validName[20]" required="true" <c:if test="${showMap.id!=null}">value="<c:out value="${saleMode.name}"></c:out>"</c:if>/></td>
		    			<td width="80px" align="right">状态:</td>
		    			<td><input id="common-combobox-state" type="text" value="${saleMode.state }" disabled="disabled" class="easyui-combobox" style="width: 120px" /></td>
		    		</tr>
		    		<tr>
		    			<td width="80px" align="right">备注:</td>
		    			<td colspan="5">
		    				<input type="text" class="easyui-validatebox" name="saleMode.remark" validType="maxLen[200]" style="width: 650px;" <c:if test="${showMap.id!=null}">value="<c:out value="${saleMode.remark}"></c:out>"</c:if>/>
		    			</td>
		    		</tr>
		    	</table>
     		</form>
    	</div>
    </div>  
    <div region="center" data-options="fit:true,border:false" style="border: 0px;padding: 10px;">
    	<table id="saleMode-table-saleModeDetailList"></table>
    </div>
    <script type="text/javascript">
    	//检验输入值的最大长度
		$.extend($.fn.validatebox.defaults.rules, {
			validID:{
				validator : function(value,param) {
					var reg=eval("/^[A-Za-z0-9]{0,"+param[0]+"}$/");
					if(reg.test(value)){
						if($("#saleMode-oldid").val() == $("#saleMode-id-saleModeDetail").val()){
							return true;
						}
						var ret = salemode_AjaxConn({id:value},'basefiles/crmrelations/isRepeatSaleModeId.do','提交中..');//true 重复，false 不重复
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
						if($("#saleMode-name").val() == $("#saleMode-name-saleModeDetail").val()){
							return true;
						}
						var ret =salemode_AjaxConn({name:value},'basefiles/crmrelations/isRepeatSaleModeName.do');//true 重复，false 不重复
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
	  		},
	  		validStageCode:{
				validator : function(value,param) {
					if(value == $("#saleMode-code-hdSaleModeDetail").val()){
						return true;
					}
					var reg=eval("/^[A-Za-z0-9]{0,"+param[0]+"}$/");
					if(reg.test(value)){
						var salemodeid = $("#saleMode-id-hdSaleModeDetail").val();
						var ret = salemode_AjaxConn({code:value,salemodeid:salemodeid},'basefiles/crmrelations/isRepeatSatgeCode.do');//true 重复，false 不重复
						var retJson = $.parseJSON(ret);
						if(retJson.flag){
							$.fn.validatebox.defaults.rules.validStageCode.message = '编码已存在, 请重新输入!';
							return false;
						}
					}
					else{
						$.fn.validatebox.defaults.rules.validStageCode.message = '最多可输入{0}个字符!';
						return false;
					}
		            return true;
		        }, 
		        message : '' 
			},
			validStageName:{
				validator : function(value,param) {
					if(value.length <= param[0]){
						if(value == $("#saleMode-name-hdSaleModeDetail").val()){
							return true;
						}
						var salemodeid = $("#saleMode-id-hdSaleModeDetail").val();
						var ret = salemode_AjaxConn({name:value,salemodeid:salemodeid},'basefiles/crmrelations/isRepeatSatgeName.do');//true 重复，false 不重复
						var retJson = $.parseJSON(ret);
						if(retJson.flag){
							$.fn.validatebox.defaults.rules.validStageName.message = '名称重复, 请重新输入!';
							return false;
						}
					}
					else{
						$.fn.validatebox.defaults.rules.validStageName.message = '最多可输入{0}个字符!';
						return false;
					}
					return true;
		        }, 
		        message : '' 
			}
		});
		
		var $saleMode_submit = $("#salemode-form-edit");//商品档案基本主信息 
    	//销售方式数据表单json对象集合
    	function saleMode_JSONs(){
    		var saleModeInfo = $saleMode_submit.serializeJSON();
    		var saleModeDetail = saleModeDetailJson("edit");
    		for(key in saleModeDetail){
				saleModeInfo[key] = saleModeDetail[key];
			};
			return saleModeInfo;
    	}
    	function editSaleMode(type){
    		if(type == "save"){
    			if(!$saleMode_submit.form('validate')){
    				return false;
    			}
    		}
    		var ret = salemode_AjaxConn(saleMode_JSONs(),'basefiles/crmrelations/editSaleMode.do?type='+type);
    		var retJson = $.parseJSON(ret);
    		if(retJson.flag){
    			$("#salemode-table-list").datagrid('reload');
    			$("#salemode-div-salemodeInfo").panel('refresh','basefiles/crmrelations/showSaleModeViewPage.do?id='+$("#saleMode-id-saleModeDetail").val());
    			if("save" == type ){
    				$.messager.alert("提醒","修改保存成功!");
    			}
    			else{
    				$.messager.alert("提醒","修改暂存成功!");
    			}
    		}
    		else{
    			if("save" == type ){
    				$.messager.alert("提醒","修改保存失败!");
    			}
    			else{
    				$.messager.alert("提醒","修改暂存失败!");
    			}
    		}
    	}
    	$(function(){
			loadDropdown();    	
    	 	$("#salemode-button-layout").buttonWidget("setDataID",{id:$("#saleMode-oldid").val(),state:"${saleMode.state}",type:'edit'});
    		saleModeDetail("edit");
    	});
    </script>
  </body>
</html>
