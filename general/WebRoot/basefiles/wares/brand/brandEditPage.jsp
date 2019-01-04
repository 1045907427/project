<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>商品品牌修改页面</title>
  </head>
  
  <body>
   	<form action="" method="post" id="wares-form-brandEdit">
    	<table cellpadding="2" cellspacing="2" border="0">
    		<tr>
    			<td>编码:</td>
    			<td>
    				<input id="wares-input-brandId" name="brand.id" type="text" value="<c:out value="${brand.id}"></c:out>" <c:if test="${editFlag == false}">readonly="readonly"</c:if> class="easyui-validatebox" validType="validID" style="width: 200px;" maxlength="20" required="true"/>
    				<input id="wares-hidden-editType" name="type" type="hidden"/>
    				<input name="brand.state" type="hidden" value="${brand.state }"/>
    				<input id="brand-oldId" name="brand.oldId" type="hidden" value="<c:out value="${brand.id }"></c:out>"/>
    				<input id="brand-name" type="hidden" value="<c:out value="${brand.name }"></c:out>"/>
    				<input name="brand.olddeptid" type="hidden" value="<c:out value="${brand.deptid }"></c:out>"/>
    				<input id="brand-oldsupplierid" name="brand.oldsupplierid" type="hidden" value="<c:out value="${brand.supplierid }"></c:out>"/>
    				<input name="brand.editSupplier" id="wares-hidden-editSupplier" type="hidden"/>
    			</td>
    		</tr>
    		<tr>
    			<td>名称:</td>
    			<td><input id="wares-input-brandname" name="brand.name" type="text" value="<c:out value="${brand.name }"></c:out>" class="easyui-validatebox" validType="validName[20]" style="width: 200px;" required="true"/></td>
    		</tr>
    		<tr>
    			<td>所属部门:</td>
    			<td><input id="wares-widget-Dept" required="true" name="brand.deptid" value="<c:out value="${brand.deptid}"></c:out>"/></td>
    		</tr>
    		<tr>
    			<td>所属供应商:</td>
    			<td><input type="text" id="wares-widget-Supplier" name="brand.supplierid" value="<c:out value="${brand.supplierid }"></c:out>"/></td>
    		</tr>
            <tr>
                <td>默认税种:</td>
                <td><input id="wares-widget-defaulttaxtype" name="brand.defaulttaxtype" type="text" value="<c:out value="${brand.defaulttaxtype }"></c:out>"/></td>
            </tr>
    		<tr>
    			<td>毛利率%:</td>
    			<td><input id="wares-numberbox-margin" name="brand.margin" type="text" value="${brand.margin }" class="easyui-numberbox" style="width: 200px;"/></td>
    		</tr>
			<c:if test="${useHTKPExport=='1'}">
				<tr>
					<td style="width: 60px;">金税簇编码:</td>
					<td><input id="wares-input-jsclusterid" name="brand.jsclusterid" value="${brand.jsclusterid}" class="easyui-validatebox" validType="maxLen[20]" style="width: 200px;"/>
					</td>
				</tr>
			</c:if>
    		<tr>
    			<td>备注:</td>
    			<td><textarea name="brand.remark" class="easyui-validatebox" validType="maxLen[200]" style="height:80px;width: 195px;overflow: hidden"><c:out value="${brand.remark}"></c:out></textarea></td>
    		</tr>
    		<tr>
    			<td>状态:</td>
    			<td><input id="common-combobox-state" value="${brand.state }" type="text" disabled="disabled" style="width: 200px" /></td>
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
						if($("#brand-oldId").val() == $("#wares-input-brandId").val()){
							return true;
						}
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
						if($("#brand-name").val() == $("#wares-input-brandname").val()){
							return true;
						}
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
		
		//修改商品品牌 
		function editBrand(type){
			$("#wares-hidden-editType").val(type);
			if(type == "save"){
				if(!$("#wares-form-brandEdit").form('validate')){
					return false;
				}
			}
			var ret = brand_AjaxConn($("#wares-form-brandEdit").serializeJSON(),'basefiles/editBrand.do');
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
			$("#brand-button-layout").buttonWidget("setDataID",{id:$("#brand-oldId").val(),state:"${brand.state}",type:"edit"});

            loadWidgetMethod();

    	});
    </script>
  </body>
</html>
