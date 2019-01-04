<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>采购区域添加页面</title>
  </head>
  
  <body>
  	<form action="basefiles/addBuyArea.do" id="buy-form-buyAreaAddPage" method="post">
  		<input type="hidden" name="addType" id="buy-addType-buyAreaAddPage" />
  		<input type="hidden" name="parentId" id="buy-parentId-buyAreaAddPage" value="<c:out value="${buyArea.id }"></c:out>" />
  		<input type="hidden" name="parentName" id="buy-parentName-buyAreaAddPage" value="<c:out value="${buyArea.name }"></c:out>" />
	    <div class="pageContent" style="width:500px;">
	    	<p>
	    		<label>本级编号：</label>
	    		<input type="text" class="easyui-validatebox" name="buyArea.thisid" id="buy-thisId-buyAreaAddPage" data-options="required:true,validType:['validLength[${len}]']" />
	    	</p>
	    	<p>
	    		<label>编号：</label>
	    		<input type="text" class="easyui-validatebox" readonly="readonly" value="<c:out value="${buyArea.id }"></c:out>" name="buyArea.id" id="buy-id-buyAreaAddPage" />
	    	</p>
	    	<p>
	    		<label>本级名称：</label>
	    		<input type="text" class="easyui-validatebox" name="buyArea.thisname" id="buy-thisName-buyAreaAddPage" required="true" validType="isRepeatName[20]"/>
	    	</p>
	    	<p>
	    		<label>名称：</label>
	    		<input type="text" class="easyui-validatebox" readonly="readonly" value="<c:out value="${buyArea.name }"></c:out>" name="buyArea.name" id="buy-name-buyAreaAddPage" />
	    	</p>
	    	<p>
	    		<label>上级分类：</label>
	    		<input type="text" id="buy-parent-buyAreaAddPage" value="<c:out value="${buyArea.id }"></c:out>" name="buyArea.pid" />
	    	</p>
	    	<p style="height:auto;width:auto;">
	    		<label>备注：</label>
	    		<textarea  style="width:195px;height:50px;"  name="buyArea.remark"></textarea>
	    	</p>
	    	<p>
	    		<label>状态：</label>
	    		<select class="easyui-combobox" disabled="disabled" style="width:200px;"  name="buyArea.state">
	    			<option value="4">新增</option>
	    		</select>
	    	</p>
	    </div>
    </form>
    <script type="text/javascript">
    	$(function(){
			$("#buy-buttons-buyArea").buttonWidget("initButtonType", 'add');
        	validLengthAndUsed('${len}', "basefiles/isBuyAreaIdExist.do", $("#buy-parentId-buyAreaAddPage").val(), '', "该编号已被使用，请另输编号！"); //验证输入长度
    		
    		$.extend($.fn.validatebox.defaults.rules, {
    			isRepeatName:{//true 不重复，false 重复
	   				validator:function(value,param){
	   					if(value.length <= param[0]){
	   						var ret = buyArea_ajaxContent({thisname:value},'basefiles/isRepeatBuyAreaThisname.do');
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
    		$("#buy-parent-buyAreaAddPage").widget({
    			name:'t_base_buy_area',
				col:'pid',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:false,
    			onChecked: function(data, check){
	    			if(check){
						$("#buy-id-buyAreaAddPage").val(data.id + $("#buy-thisId-buyAreaAddPage").val());
						if($("#buy-thisName-buyAreaAddPage").val() != ""){
							$("#buy-name-buyAreaAddPage").val(data.name + '/' + $("#buy-thisName-buyAreaAddPage").val());
						}
						else{
							$("#buy-name-buyAreaAddPage").val(data.name);
						}
						$("#buy-parentId-buyAreaAddPage").val(data.id);
						$("#buy-parentName-buyAreaAddPage").val(data.name);
						$("#buy-thisId-buyArea").val(data.id);
						$("#buy-parentId-buyArea").val(data.pid);
						var hasLevel = $("#buy-hasLevel-buyArea").val();
						if((data.level+1)==hasLevel){
							$.messager.alert("警告","该节点已为最大级次,不能再新增子节点!");
							$("#buy-buttons-buyArea").buttonWidget("disableButton", "button-hold");
							$("#buy-buttons-buyArea").buttonWidget("disableButton", "button-save");
							return false;
						}
						else{
							$("#buy-buttons-buyArea").buttonWidget("enableButton", "button-hold");
							$("#buy-buttons-buyArea").buttonWidget("enableButton", "button-save");
						}
						validLengthAndUsed(buyArea_lenArr[(data.level + 1)], "basefiles/isBuyAreaIdExist.do", $("#buy-parentId-buyAreaAddPage").val(), '', "该编号已被使用，请另输编号！"); //验证输入长度
					}
					else{
						$("#buy-id-buyAreaAddPage").val($("#buy-thisId-buyAreaAddPage").val());
						$("#buy-name-buyAreaAddPage").val($("#buy-thisName-buyAreaAddPage").val());
						$("#buy-parentId-buyAreaAddPage").val("");
						$("#buy-parentName-buyAreaAddPage").val("");
						$("#buy-thisId-buyArea").val("");
						$("#buy-parentId-buyArea").val("");
						$("#buy-buttons-buyArea").buttonWidget("enableButton", "button-hold");
						$("#buy-buttons-buyArea").buttonWidget("enableButton", "button-save");
						validLengthAndUsed(buyArea_lenArr[0], "basefiles/isBuyAreaIdExist.do", $("#buy-parentId-buyAreaAddPage").val(), '', "该编号已被使用，请另输编号！"); //验证输入长度
					}
	    		},
				onClear: function(){
					$("#buy-id-buyAreaAddPage").val($("#buy-thisId-buyAreaAddPage").val());
					$("#buy-name-buyAreaAddPage").val($("#buy-thisName-buyAreaAddPage").val());
					$("#buy-parentId-buyAreaAddPage").val("");
					$("#buy-parentName-buyAreaAddPage").val("");
					$("#buy-thisId-buyArea").val("");
					$("#buy-parentId-buyArea").val("");
					$("#buy-buttons-buyArea").buttonWidget("enableButton", "button-hold");
					$("#buy-buttons-buyArea").buttonWidget("enableButton", "button-save");
					validLengthAndUsed(buyArea_lenArr[0], "basefiles/isBuyAreaIdExist.do", $("#buy-parentId-buyAreaAddPage").val(), '', "该编号已被使用，请另输编号！"); //验证输入长度
				}
    		});
    		$("#buy-thisId-buyAreaAddPage").change(function(){
    			$("#buy-id-buyAreaAddPage").val($("#buy-parentId-buyAreaAddPage").val() + $(this).val());
    		});
    		$("#buy-thisName-buyAreaAddPage").change(function(){
    			var name = $("#buy-parentName-buyAreaAddPage").val();
    			if(name == ""){
    				$("#buy-name-buyAreaAddPage").val($(this).val());
    			}
    			else{
    				$("#buy-name-buyAreaAddPage").val(name + '/' + $(this).val());	
    			}
    			if($(this).val() == ""){
    				$("#buy-name-buyAreaAddPage").val(name);
    			}
    		});
    	});
    </script>
  </body>
</html>
