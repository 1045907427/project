<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>商品品牌新增页面</title>
  </head>
  
  <body>
    <form action="" method="post" id="wares-form-brandAdd">
    	<table cellpadding="2" cellspacing="2" border="0">
    		<tr>
    			<td>编码:</td>
    			<td>
    				<input id="wares-input-brandId" name="brand.id" type="text" class="easyui-validatebox" validType="validID" style="width: 200px;" maxlength="20" required="true"/>
    				<input id="wares-hidden-addType" name="type" type="hidden"/>
    			</td>
    		</tr>
    		<tr>
    			<td>名称:</td>
    			<td><input name="brand.name" type="text" class="easyui-validatebox" validType="validName[20]" style="width: 200px;" required="true"/></td>
    		</tr>
    		<tr>
    			<td>所属部门:</td>
    			<td><input id="wares-widget-Dept" required="true" name="brand.deptid" value="<c:out value="${brand.deptid }"></c:out>"/></td>
    		</tr>
    		<tr>
    			<td>所属供应商:</td>
    			<td><input type="text" id="wares-widget-addSupplier" name="brand.supplierid"/></td>
    		</tr>
            <tr>
                <td>默认税种:</td>
                <td><input id="wares-widget-defaulttaxtype" name="brand.defaulttaxtype" type="text"/></td>
            </tr>
    		<tr>
    			<td>毛利率%:</td>
    			<td><input id="wares-numberbox-margin" name="brand.margin" type="text" class="easyui-numberbox" style="width: 200px;"/></td>
    		</tr>
			<c:if test="${useHTKPExport=='1'}">
				<tr>
					<td style="width: 60px;">金税簇编码:</td>
					<td><input id="wares-input-jsclusterid" name="brand.jsclusterid" class="easyui-validatebox" validType="maxLen[20]" style="width: 200px;"/>
					</td>
				</tr>
			</c:if>
    		<tr>
    			<td>备注:</td>
    			<td><textarea name="brand.remark" class="easyui-validatebox" validType="maxLen[200]" style="height:80px;width: 195px;overflow: hidden"></textarea></td>
    		</tr>
    		<tr>
    			<td>状态:</td>
    			<td><input id="common-combobox-state" type="text" value="4" disabled="disabled" class="easyui-combobox" style="width: 200px" /></td>
    		</tr>
    	</table>
    </form>
    <script type="text/javascript">
    	//检验输入值的最大长度
		$.extend($.fn.validatebox.defaults.rules, {
			validID:{
				validator : function(value,param) {
					var reg=eval("/^[\\w-]+$/");
					if(reg.test(value)){
						var ret = brand_AjaxConn({id:value},'basefiles/isRepeatBrandById.do');//true 重复，false 不重复
						var retJson = $.parseJSON(ret);
						if(retJson.flag){
							$.fn.validatebox.defaults.rules.validID.message = '编码已存在, 请重新输入!';
							return false;
						}
					}
					else{
						$.fn.validatebox.defaults.rules.validID.message = '格式错误!';
						return false;
					}
		            return true;
		        }, 
		        message : '' 
			},
			validName:{
				validator : function(value,param) {
					if(value.length <= param[0]){
						var ret = brand_AjaxConn({name:value},'basefiles/isRepeatBrandName.do');//true 重复，false 不重复
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

		//新增商品品牌
		function addBrand(type){
			$("#wares-hidden-addType").val(type);
			if(type == "save"){
				if(!$("#wares-form-brandAdd").form('validate')){
					return false;
				}
			}
			var ret = brand_AjaxConn($("#wares-form-brandAdd").serializeJSON(),'basefiles/addBrand.do');
			var retJson = $.parseJSON(ret);
			if(retJson.flag){
				refreshLayout('商品品牌【查看】','basefiles/showBrandInfoPage.do?id='+$("#wares-input-brandId").val(),'view');
				$("#brand-table-list").datagrid('reload');
				if(type == "save"){
					$.messager.alert("提醒","保存成功!");
				}
				else{
					$.messager.alert("提醒","暂存成功!");
				}
			}
			else{
				if(type == "save"){
					$.messager.alert("提醒","保存失败!");
				}
				else{
					$.messager.alert("提醒","暂存失败!");
				}
			}
		}
		
		$(function(){
			$("#brand-button-layout").buttonWidget("initButtonType", 'add');

            loadWidgetMethod();
    	});
    </script>
  </body>
</html>
