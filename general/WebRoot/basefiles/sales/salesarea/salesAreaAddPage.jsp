<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售区域添加页面</title>
  </head>
  
  <body>
  	<form action="basefiles/addSalesArea.do" id="sales-form-salesAreaAddPage" method="post">
  		<input type="hidden" name="addType" id="sales-addType-salesAreaAddPage" />
  		<input type="hidden" name="parentId" id="sales-parentId-salesAreaAddPage" value="<c:out value="${salesArea.id }"></c:out>" />
  		<input type="hidden" name="parentName" id="sales-parentName-salesAreaAddPage" value="<c:out value="${salesArea.name }"></c:out>" />
	    <div class="pageContent" style="width:500px;">
	    	<p>
	    		<label>本级编号：</label>
	    		<input class="easyui-validatebox" name="salesArea.thisid" id="sales-thisId-salesAreaAddPage" data-options="required:true,validType:['validLength[${len}]']"  />
	    	</p>
	    	<p>
	    		<label>编号：</label>
	    		<input type="text" class="easyui-validatebox" readonly="readonly" value="<c:out value="${salesArea.id }"></c:out>" name="salesArea.id" id="sales-id-salesAreaAddPage" />
	    	</p>
	    	<p>
	    		<label>本级名称：</label>
	    		<input type="text" class="easyui-validatebox" name="salesArea.thisname" id="sales-thisName-salesAreaAddPage" required="true" validType="isRepeatName[20]"/>
	    	</p>
	    	<p>
	    		<label>名称：</label>
	    		<input type="text" class="easyui-validatebox" readonly="readonly" value="<c:out value="${salesArea.name }"></c:out>" name="salesArea.name" id="sales-name-salesAreaAddPage" />
	    	</p>
	    	<p>
	    		<label>上级分类：</label>
	    		<input type="text" id="sales-parent-salesAreaAddPage" value="<c:out value="${salesArea.id }"></c:out>" name="salesArea.pid" />
	    	</p>
	    	<p style="height:auto;width:auto;">
	    		<label>备注：</label>
	    		<textarea style="width:195px;height:50px;" name="salesArea.remark"></textarea>
	    	</p>
	    	<p>
	    		<label>状态：</label>
	    		<select class="easyui-combobox" disabled="disabled" style="width:200px;"  name="salesArea.state">
	    			<option value="4">新增</option>
	    		</select>
	    	</p>
	    </div>
    </form>
    <script type="text/javascript">
    	$(function(){
    		validLengthAndUsed('${len}', "basefiles/salesAreaNOUsed.do", $("#sales-parentId-salesAreaAddPage").val(), '', "该编号已被使用，请另输编号！"); //验证输入长度
    		
    		$.extend($.fn.validatebox.defaults.rules, {
    			isRepeatName:{//true 不重复，false 重复
	   				validator:function(value,param){
	   					if(value.length <= param[0]){
	   						var ret = salesArea_ajaxContent({thisname:value},'basefiles/isRepeatSalesAreaThisname.do');
	   						var retJson = $.parseJSON(ret);
	   						if(!retJson.flag){
	   							$.fn.validatebox.defaults.rules.isRepeatName.message ='名称重复,请修改!';
	   							return false;
	   						}
	   						else{return true;}
	   					}
	   					else{
	   						$.fn.validatebox.defaults.rules.isRepeatName.message ='请输入{0}个字符!';
	   						return false;
	   					}
	   				},
	   				message:''
	   			}
    		});
    		
    		$("#sales-parent-salesAreaAddPage").widget({
    			name:'t_base_sales_area',
				col:'pid',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:false,
    			onChecked: function(data, check){
    				if(check){
    					$("#sales-id-salesAreaAddPage").val(data.id + $("#sales-thisId-salesAreaAddPage").val());
    					if($("#sales-thisName-salesAreaAddPage").val() != ""){
    						$("#sales-name-salesAreaAddPage").val(data.name + '/' + $("#sales-thisName-salesAreaAddPage").val());
    					}
    					else{
    						$("#sales-name-salesAreaAddPage").val(data.name);
    					}
    					$("#sales-parentId-salesAreaAddPage").val(data.id);
    					$("#sales-parentName-salesAreaAddPage").val(data.name);
    					$("#sales-thisId-salesArea").val(data.id);
    					$("#sales-parentId-salesArea").val(data.pid);
    					var hasLevel = $("#sales-hasLevel-salesArea").val();
    					if((data.level+1)==hasLevel){
    						$.messager.alert("警告","该节点已为最大级次,不能再新增子节点!");
    						$("#sales-buttons-salesArea").buttonWidget("disableButton", "button-hold");
    						$("#sales-buttons-salesArea").buttonWidget("disableButton", "button-save");
    						return false;
    					}
    					else{
    						$("#sales-buttons-salesArea").buttonWidget("enableButton", "button-hold");
    						$("#sales-buttons-salesArea").buttonWidget("enableButton", "button-save");
    					}
    					validLengthAndUsed(salesArea_lenArr[(data.level + 1)], "basefiles/salesAreaNOUsed.do", $("#sales-parentId-salesAreaAddPage").val(), '', "该编号已被使用，请另输编号！"); //验证输入长度
    				}
    				else{
    					$("#sales-id-salesAreaAddPage").val($("#sales-thisId-salesAreaAddPage").val());
    					$("#sales-name-salesAreaAddPage").val($("#sales-thisName-salesAreaAddPage").val());
    					$("#sales-parentId-salesAreaAddPage").val("");
    					$("#sales-parentName-salesAreaAddPage").val("");
    					$("#sales-thisId-salesArea").val("");
    					$("#sales-parentId-salesArea").val("");
    					$("#sales-buttons-salesArea").buttonWidget("enableButton", "button-hold");
    					$("#sales-buttons-salesArea").buttonWidget("enableButton", "button-save");
    					validLengthAndUsed(salesArea_lenArr[0], "basefiles/salesAreaNOUsed.do", $("#sales-parentId-salesAreaAddPage").val(), '', "该编号已被使用，请另输编号！"); //验证输入长度
    				}
    			},
    			onClear: function(){
    				$("#sales-id-salesAreaAddPage").val($("#sales-thisId-salesAreaAddPage").val());
    				$("#sales-name-salesAreaAddPage").val($("#sales-thisName-salesAreaAddPage").val());
    				$("#sales-parentId-salesAreaAddPage").val("");
    				$("#sales-parentName-salesAreaAddPage").val("");
    				$("#sales-thisId-salesArea").val("");
    				$("#sales-parentId-salesArea").val("");
    				$("#sales-buttons-salesArea").buttonWidget("enableButton", "button-hold");
    				$("#sales-buttons-salesArea").buttonWidget("enableButton", "button-save");
    				validLengthAndUsed(salesArea_lenArr[0], "basefiles/salesAreaNOUsed.do", $("#sales-parentId-salesAreaAddPage").val(), '', "该编号已被使用，请另输编号！"); //验证输入长度
    			}
    		});
    		$("#sales-thisId-salesAreaAddPage").change(function(){
    			$("#sales-id-salesAreaAddPage").val($("#sales-parentId-salesAreaAddPage").val() + $(this).val());
    		});
    		$("#sales-thisName-salesAreaAddPage").change(function(){
    			var name = $("#sales-parentName-salesAreaAddPage").val();
    			if(name == ""){
    				$("#sales-name-salesAreaAddPage").val($(this).val());
    			}
    			else{
    				$("#sales-name-salesAreaAddPage").val(name + '/' + $(this).val());	
    			}
    			if($(this).val() == ""){
    				$("#sales-name-salesAreaAddPage").val(name);
    			}
    		});
			$("#sales-buttons-salesArea").buttonWidget("initButtonType", 'add');
    	});
    </script>
  </body>
</html>
