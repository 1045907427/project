<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>联系人添加页面</title>
  </head>
  
  <body>
  	<form action="basefiles/addContacterSort.do" id="contacter-form-contacterSortAddPage" method="post">
  		<input type="hidden" name="addType" id="contacter-addType-contacterSortAddPage" />
  		<input type="hidden" name="parentId" id="contacter-parentId-contacterSortAddPage" value="<c:out value="${contacterSort.id }"></c:out>" />
  		<input type="hidden" name="parentName" id="contacter-parentName-contacterSortAddPage" value="<c:out value="${contacterSort.name }"></c:out>" />
	    <table cellpadding="2" cellspacing="2" border="0">
	    	<tr>
	    		<td>本级编号：</td>
	    		<td><input type="text" style="width: 200px;" class="easyui-validatebox" name="contacterSort.thisid" id="contacter-thisId-contacterSortAddPage" data-options="required:true,validType:['validLength[${len}]']"  /></td>
	    	</tr>
	    	<tr>
	    		<td>编号：</td>
	    		<td><input type="text" style="width: 200px;" class="easyui-validatebox" readonly="readonly" value="<c:out value="${contacterSort.id }"></c:out>" name="contacterSort.id" id="contacter-id-contacterSortAddPage" /></td>
	    	</tr>
	    	<tr>
	    		<td>本级名称：</td>
	    		<td><input type="text" style="width: 200px;" class="easyui-validatebox" name="contacterSort.thisname" id="contacter-thisName-contacterSortAddPage" required="true" validType="isRepeatName[20]"/></td>
	    	</tr>
	    	<tr>
	    		<td>名称：</td>
	    		<td><input type="text" style="width: 200px;" class="easyui-validatebox" readonly="readonly" value="<c:out value="${contacterSort.name }"></c:out>" name="contacterSort.name" id="contacter-name-contacterSortAddPage" /></td>
	    	</tr>
	    	<tr>
	    		<td>上级分类：</td>
	    		<td><input type="text" id="contacter-parent-contacterSortAddPage" value="<c:out value="${contacterSort.id }"></c:out>" name="contacterSort.pid" /></td>
	    	</tr>
	    	<tr>
	    		<td>备注：</td>
	    		<td><textarea style="width: 200px;height: 50px;" name="contacterSort.remark"></textarea></td>
	    	</tr>
	    	<tr>
	    		<td>状态：</td>
	    		<td><select class="easyui-combobox" disabled="disabled" style="width:206px;"  name="contacterSort.state">
	    			<option value="4">新增</option>
	    		</select></td>
	    	</tr>
	    </table>
    </form>
    <script type="text/javascript">
    	$(function(){
    		validLengthAndUsed('${len}', "basefiles/contacterSortNOUsed.do", $("#contacter-parentId-contacterSortAddPage").val(), '', "该编号已被使用，请另输编号！"); //验证输入长度
    		
    		$.extend($.fn.validatebox.defaults.rules, {
    			isRepeatName:{//true 不重复，false 重复
	   				validator:function(value,param){
	   					if(value.length <= param[0]){
	   						var ret = customerSort_ajaxContent({thisname:value},'basefiles/isRepeatContacterSortThisname.do');
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
    		
    		$("#contacter-parent-contacterSortAddPage").widget({
    			name:'t_base_linkman_sort',
				col:'pid',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:false,
    			onChecked: function(data, check){
    				if(check){
    					$("#contacter-id-contacterSortAddPage").val(data.id + $("#contacter-thisId-contacterSortAddPage").val());
    					if($("#contacter-thisName-contacterSortAddPage").val() != ""){
    						$("#contacter-name-contacterSortAddPage").val(data.name + '/' + $("#contacter-thisName-contacterSortAddPage").val());
    					}
    					else{
    						$("#contacter-name-contacterSortAddPage").val(data.name);
    					}
    					$("#contacter-parentId-contacterSortAddPage").val(data.id);
    					$("#contacter-parentName-contacterSortAddPage").val(data.name);
    					$("#contacter-thisId-contacterSort").val(data.id);
    					$("#contacter-parentId-contacterSort").val(data.pid);
    					var hasLevel = $("#contacter-hasLevel-contacterSort").val();
    					if((data.level+1)==hasLevel){
    						$.messager.alert("警告","该节点已为最大级次,不能再新增子节点!");
    						$("#contacter-buttons-contacterSort").buttonWidget("disableButton", "button-hold");
    						$("#contacter-buttons-contacterSort").buttonWidget("disableButton", "button-save");
    						return false;
    					}
    					else{
    						$("#contacter-buttons-contacterSort").buttonWidget("enableButton", "button-hold");
    						$("#contacter-buttons-contacterSort").buttonWidget("enableButton", "button-save");
    					}
    					validLengthAndUsed(contacterSort_lenArr[(data.level + 1)], "basefiles/contacterSortNOUsed.do", $("#contacter-parentId-contacterSortAddPage").val(), '', "该编号已被使用，请另输编号！"); //验证输入长度
    				}
    				else{
    					$("#contacter-id-contacterSortAddPage").val($("#contacter-thisId-contacterSortAddPage").val());
    					$("#contacter-name-contacterSortAddPage").val($("#contacter-thisName-contacterSortAddPage").val());
    					$("#contacter-parentId-contacterSortAddPage").val("");
    					$("#contacter-parentName-contacterSortAddPage").val("");
    					$("#contacter-thisId-contacterSort").val("");
    					$("#contacter-parentId-contacterSort").val("");
    					$("#contacter-buttons-contacterSort").buttonWidget("enableButton", "button-hold");
    					$("#contacter-buttons-contacterSort").buttonWidget("enableButton", "button-save");
    					validLengthAndUsed(contacterSort_lenArr[0], "basefiles/contacterSortNOUsed.do", $("#contacter-parentId-contacterSortAddPage").val(), '', "该编号已被使用，请另输编号！"); //验证输入长度
    				}
    			},
    			onClear: function(){
    				$("#contacter-id-contacterSortAddPage").val($("#contacter-thisId-contacterSortAddPage").val());
    				$("#contacter-name-contacterSortAddPage").val($("#contacter-thisName-contacterSortAddPage").val());
    				$("#contacter-parentId-contacterSortAddPage").val("");
    				$("#contacter-parentName-contacterSortAddPage").val("");
    				$("#contacter-thisId-contacterSort").val("");
    				$("#contacter-parentId-contacterSort").val("");
    				$("#contacter-buttons-contacterSort").buttonWidget("enableButton", "button-hold");
    				$("#contacter-buttons-contacterSort").buttonWidget("enableButton", "button-save");
    				validLengthAndUsed(contacterSort_lenArr[0], "basefiles/contacterSortNOUsed.do", $("#contacter-parentId-contacterSortAddPage").val(), '', "该编号已被使用，请另输编号！"); //验证输入长度
    			}
    		});
    		$("#contacter-thisId-contacterSortAddPage").change(function(){
    			$("#contacter-id-contacterSortAddPage").val($("#contacter-parentId-contacterSortAddPage").val() + $(this).val());
    		});
    		$("#contacter-thisName-contacterSortAddPage").change(function(){
    			var name = $("#contacter-parentName-contacterSortAddPage").val();
    			if(name == ""){
    				$("#contacter-name-contacterSortAddPage").val($(this).val());
    			}
    			else{
    				$("#contacter-name-contacterSortAddPage").val(name + '/' + $(this).val());	
    			}
    			if($(this).val() == ""){
    				$("#contacter-name-contacterSortAddPage").val(name);
    			}
    		});
			$("#contacter-buttons-contacterSort").buttonWidget("initButtonType", 'add');
    	});
    </script>
  </body>
</html>
