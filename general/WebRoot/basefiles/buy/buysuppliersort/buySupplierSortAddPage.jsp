<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>供应商分类添加页面</title>
  </head>
  
  <body>
  	<form action="basefiles/addBuySupplierSort.do" id="buy-form-buySupplierSortAddPage" method="post">
  		<input type="hidden" name="addType" id="buy-addType-buySupplierSortAddPage" />
  		<input type="hidden" name="parentId" id="buy-parentId-buySupplierSortAddPage" value="<c:out value="${buySupplierSort.id }"></c:out>" />
  		<input type="hidden" name="parentName" id="buy-parentName-buySupplierSortAddPage" value="<c:out value="${buySupplierSort.name }"></c:out>" />
	    <table cellpadding="2" cellspacing="2" border="0">
	    	<tr>
	    		<td>本级编号：</td>
	    		<td><input type="text" style="width: 200px;" class="easyui-validatebox" name="buySupplierSort.thisid" id="buy-thisId-buySupplierSortAddPage" data-options="required:true,validType:['validLength[${len}]']"/></td>
	    	</tr>
	    	<tr>
	    		<td>编号：</td>
	    		<td><input type="text" style="width: 200px;" class="easyui-validatebox" readonly="readonly" value="<c:out value="${buySupplierSort.id }"></c:out>" name="buySupplierSort.id" id="buy-id-buySupplierSortAddPage"/></td>
	    	</tr>
	    	<tr>
	    		<td>本级名称：</td>
	    		<td><input type="text" style="width: 200px;" class="easyui-validatebox" name="buySupplierSort.thisname" id="buy-thisName-buySupplierSortAddPage" required="true" validType="isRepeatName[20]"/></td>
	    	</tr>
	    	<tr>
	    		<td>名称：</td>
	    		<td><input type="text" style="width: 200px;" class="easyui-validatebox" readonly="readonly" value="<c:out value="${buySupplierSort.name }"></c:out>" name="buySupplierSort.name" id="buy-name-buySupplierSortAddPage"/></td>
	    	</tr>
	    	<tr>
	    		<td>上级分类：</td>
	    		<td><input type="text" id="buy-parent-buySupplierSortAddPage" value="<c:out value="${buySupplierSort.id }"></c:out>" name="buySupplierSort.pid"/></td>
	    	</tr>
	    	<tr>
	    		<td>备注：</td>
	    		<td><textarea rows="3" cols="26" style="width: 195px;"  name="buySupplierSort.remark"></textarea></td>
	    	</tr>
	    	<tr>
	    		<td>状态：</td>
	    		<td>
	    			<select class="easyui-combobox" disabled="disabled" style="width:200px;"  name="buySupplierSort.state">
		    			<option value="4">新增</option>
		    		</select>
	    		</td>
	    	</tr>
	    </table>
    </form>
    <script type="text/javascript">
    	$(function(){
			$("#buy-buttons-buySupplierSort").buttonWidget("initButtonType", 'add');
    		validLengthAndUsed('${len}', "basefiles/isBuySupplierSortIdExist.do", $("#buy-parentId-buySupplierSortAddPage").val(), '', "该编号已被使用，请另输编号！"); //验证输入长度
    		
    		$.extend($.fn.validatebox.defaults.rules, {
    			isRepeatName:{//true 不重复，false 重复
	   				validator:function(value,param){
	   					if(value.length <= param[0]){
	   						var ret = buySupplierSort_ajaxContent({thisname:value},'basefiles/isRepeatSupplierSortThisname.do');
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
    		
    		$("#buy-parent-buySupplierSortAddPage").widget({
    			name:'t_base_buy_supplier_sort',
				col:'pid',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:false,
    			onChecked: function(data, check){
	    			if(check){
						$("#buy-id-buySupplierSortAddPage").val(data.id + $("#buy-thisId-buySupplierSortAddPage").val());
						if($("#buy-thisName-buySupplierSortAddPage").val() != ""){
							$("#buy-name-buySupplierSortAddPage").val(data.name + '/' + $("#buy-thisName-buySupplierSortAddPage").val());
						}
						else{
							$("#buy-name-buySupplierSortAddPage").val(data.name);
						}
						$("#buy-parentId-buySupplierSortAddPage").val(data.id);
						$("#buy-parentName-buySupplierSortAddPage").val(data.name);
						$("#buy-thisId-buySupplierSort").val(data.id);
						$("#buy-parentId-buySupplierSort").val(data.pid);
						var hasLevel = $("#buy-hasLevel-buySupplierSort").val();
						if((data.level+1)==hasLevel){
							$.messager.alert("警告","该节点已为最大级次,不能再新增子节点!");
							$("#buy-buttons-buySupplierSort").buttonWidget("disableButton", "button-hold");
							$("#buy-buttons-buySupplierSort").buttonWidget("disableButton", "button-save");
							return false;
						}
						else{
							$("#buy-buttons-buySupplierSort").buttonWidget("enableButton", "button-hold");
							$("#buy-buttons-buySupplierSort").buttonWidget("enableButton", "button-save");
						}
						validLengthAndUsed(buySupplierSort_lenArr[(data.level + 1)], "basefiles/isBuySupplierSortIdExist.do", $("#buy-parentId-buySupplierSortAddPage").val(), '', "该编号已被使用，请另输编号！"); //验证输入长度
					}
					else{
						$("#buy-id-buySupplierSortAddPage").val($("#buy-thisId-buySupplierSortAddPage").val());
						$("#buy-name-buySupplierSortAddPage").val($("#buy-thisName-buySupplierSortAddPage").val());
						$("#buy-parentId-buySupplierSortAddPage").val("");
						$("#buy-parentName-buySupplierSortAddPage").val("");
						$("#buy-thisId-buySupplierSort").val("");
						$("#buy-parentId-buySupplierSort").val("");
						$("#buy-buttons-buySupplierSort").buttonWidget("enableButton", "button-hold");
						$("#buy-buttons-buySupplierSort").buttonWidget("enableButton", "button-save");
						validLengthAndUsed(buySupplierSort_lenArr[0], "basefiles/isBuySupplierSortIdExist.do", $("#buy-parentId-buySupplierSortAddPage").val(), '', "该编号已被使用，请另输编号！"); //验证输入长度
					}
	    		},
				onClear: function(){
					$("#buy-id-buySupplierSortAddPage").val($("#buy-thisId-buySupplierSortAddPage").val());
					$("#buy-name-buySupplierSortAddPage").val($("#buy-thisName-buySupplierSortAddPage").val());
					$("#buy-parentId-buySupplierSortAddPage").val("");
					$("#buy-parentName-buySupplierSortAddPage").val("");
					$("#buy-thisId-buySupplierSort").val("");
					$("#buy-parentId-buySupplierSort").val("");
					$("#buy-buttons-buySupplierSort").buttonWidget("enableButton", "button-hold");
					$("#buy-buttons-buySupplierSort").buttonWidget("enableButton", "button-save");
					validLengthAndUsed(buySupplierSort_lenArr[0], "basefiles/isBuySupplierSortIdExist.do", $("#buy-parentId-buySupplierSortAddPage").val(), '', "该编号已被使用，请另输编号！"); //验证输入长度
				}
			});
			$("#buy-thisId-buySupplierSortAddPage").change(function(){
				$("#buy-id-buySupplierSortAddPage").val($("#buy-parentId-buySupplierSortAddPage").val() + $(this).val());
			});
			$("#buy-thisName-buySupplierSortAddPage").change(function(){
				var name = $("#buy-parentName-buySupplierSortAddPage").val();
				if(name == ""){
					$("#buy-name-buySupplierSortAddPage").val($(this).val());
				}
				else{
					$("#buy-name-buySupplierSortAddPage").val(name + '/' + $(this).val());	
				}
				if($(this).val() == ""){
					$("#buy-name-buySupplierSortAddPage").val(name);
				}
			});
		});
    </script>
  </body>
</html>
