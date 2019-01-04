<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>客户关系复制</title>
  </head>
  
  <body>
    <form action="basefiles/addCustomerSort.do" id="sales-form-customerSortAddPage" method="post">
  		<input type="hidden" name="addType" id="sales-addType-customerSortAddPage" />
  		<input type="hidden" name="parentId" id="sales-parentId-customerSortAddPage" value="<c:out value="${customerSort.pid }"></c:out>" />
  		<input type="hidden" name="parentName" id="sales-parentName-customerSortAddPage" value="<c:out value="${parentName }"></c:out>" />
  		<input type="hidden" id="customersort-pid" value="<c:out value="${customerSort.pid }"></c:out>" />
	    <div class="pageContent" style="width:500px;">
	    	<p>
	    		<label>本级编号：</label>
	    		<input type="text" class="easyui-validatebox" name="customerSort.thisid" id="sales-thisId-customerSortAddPage" required="required" />
	    	</p>
	    	<p>
	    		<label>编号：</label>
	    		<input type="text" class="easyui-validatebox" readonly="readonly" value="<c:out value="${customerSort.pid }"></c:out>" name="customerSort.id" id="sales-id-customerSortAddPage" />
	    	</p>
	    	<p>
	    		<label>本级名称：</label>
	    		<input type="text" class="easyui-validatebox" name="customerSort.thisname" id="sales-thisName-customerSortAddPage" required="true" validType="isRepeatName[20]"/>
	    	</p>
	    	<p>
	    		<label>名称：</label>
	    		<input type="text" class="easyui-validatebox" readonly="readonly" value="<c:out value="${parentName }"></c:out>" name="customerSort.name" id="sales-name-customerSortAddPage" />
	    	</p>
	    	<p>
	    		<label>上级分类：</label>
	    		<input type="text" id="sales-parent-customerSortAddPage" value="<c:out value="${customerSort.pid }"></c:out>" name="customerSort.pid" />
	    	</p>
	    	<p style="height:auto;width:auto;">
	    		<label>备注：</label>
	    		<textarea style="width:195px;height:50px;" name="customerSort.remark"><c:out value="${customerSort.remark }"></c:out></textarea>
	    	</p>
	    	<p>
	    		<label>状态：</label>
	    		<select class="easyui-combobox" disabled="disabled" style="width:200px;"  name="customerSort.state">
	    			<option value="4">新增</option>
	    		</select>
	    	</p>
	    </div>
    </form>
    <script type="text/javascript">
    	$(function(){
    		validLengthAndUsed('${len}', "basefiles/customerSortNOUsed.do", $("#sales-parentId-customerSortAddPage").val(), '', "该编号已被使用，请另输编号！"); //验证输入长度
    		
    		$.extend($.fn.validatebox.defaults.rules, {
    			isRepeatName:{//true 不重复，false 重复
	   				validator:function(value,param){
	   					if(value.length <= param[0]){
	   						var ret = customerSort_ajaxContent({thisname:value},'basefiles/isRepeatCustomerSortThisname.do');
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
    		
    		$("#sales-thisId-customerSort").val($("#customersort-pid").val());
    		$("#sales-parent-customerSortAddPage").widget({
    			name:'t_base_sales_customersort',
				col:'pid',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:false,
    			onChecked: function(data, check){
    				if(check){
    					$("#sales-id-customerSortAddPage").val(data.id + $("#sales-thisId-customerSortAddPage").val());
    					$("#sales-name-customerSortAddPage").val(data.name + '/' + $("#sales-thisName-customerSortAddPage").val());
    					$("#sales-parentId-customerSortAddPage").val(data.id);
    					$("#sales-parentName-customerSortAddPage").val(data.name);
    					$("#sales-thisId-customerSort").val(data.id);
    					$("sales-parentId-customerSort").val(data.pid);
    				}
    				else{
    					$("#sales-id-customerSortAddPage").val($("#sales-thisId-customerSortAddPage").val());
    					$("#sales-name-customerSortAddPage").val($("#sales-thisName-customerSortAddPage").val());
    					$("#sales-parentId-customerSortAddPage").val("");
    					$("#sales-parentName-customerSortAddPage").val("");
    					$("#sales-thisId-customerSort").val("");
    					$("sales-parentId-customerSort").val("");
    				}
    			}
    		});
    		$("#sales-thisId-customerSortAddPage").change(function(){
    			$("#sales-id-customerSortAddPage").val($("#sales-parentId-customerSortAddPage").val() + $(this).val());
    		});
    		$("#sales-thisName-customerSortAddPage").change(function(){
    			var name = $("#sales-parentName-customerSortAddPage").val();
    			if(name == ""){
    				$("#sales-name-customerSortAddPage").val($(this).val());
    			}
    			else{
    				$("#sales-name-customerSortAddPage").val(name + '/' + $(this).val());	
    			}
    			if($(this).val() == ""){
    				$("#sales-name-customerSortAddPage").val(name);
    			}
    		});
			$("#sales-buttons-customerSort").buttonWidget("initButtonType", 'add');
    	});
    </script>
  </body>
</html>
