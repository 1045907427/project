<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>市场活动分类修改页面</title>
  </head>
  
  <body>
  	<form action="" id="finance-form-expensesSortEditPage" method="post">
  		<input type="hidden" name="parentId" id="finance-parentId-expensesSortAddPage" value="<c:out value="${expensesSort.pid }"></c:out>" />
  		<input type="hidden" name="parentName" id="finance-parentName-expensesSortAddPage" value="<c:out value="${parentName }"></c:out>" />
  		<input type="hidden" id="expensesSort-oldid" name="expensesSort.oldId" value="<c:out value="${expensesSort.id }"></c:out>" />
  		<input type="hidden" id="expensesSort-thisid" value="<c:out value="${expensesSort.thisid }"></c:out>" />
  		<input type="hidden" id="expensesSort-thisname" value="<c:out value="${expensesSort.thisname }"></c:out>" />
  		<input type="hidden" name="expensesSort.state" value="<c:out value="${expensesSort.state }"></c:out>" />
  		<!-- 修改标识，判断有没有引用 -->
  		<input type="hidden" id="finance-editType-expensesSortAddPage" value="${editFlag }" />
	    <div class="pageContent" style="width:500px;">
	    	<p>
	    		<label>本级编号：</label>
	    		<input type="text" class="easyui-validatebox" name="expensesSort.thisid" value="<c:out value="${expensesSort.thisid }"></c:out>" <c:if test="${editFlag == false }">readonly="readonly"</c:if> id="finance-thisId-expensesSortAddPage" data-options="required:true,validType:['validLength[${len}]']" />
	    	</p>
	    	<p>
	    		<label>编号：</label>
	    		<input type="text" class="easyui-validatebox" readonly="readonly" value="<c:out value="${expensesSort.id }"></c:out>" name="expensesSort.id" id="finance-id-expensesSortAddPage" />
	    	</p>
	    	<p>
	    		<label>本级名称：</label>
	    		<input type="text" class="easyui-validatebox" name="expensesSort.thisname" value="<c:out value="${expensesSort.thisname }"></c:out>" id="finance-thisName-expensesSortAddPage" required="required" validType="validUsed[50]"/>
	    	</p>
	    	<p>
	    		<label>名称：</label>
	    		<input type="text" class="easyui-validatebox" readonly="readonly" value="<c:out value="${expensesSort.name}"></c:out>" name="expensesSort.name" id="finance-name-expensesSortAddPage" />
	    	</p>
	    	<p>
	    		<label>上级分类：</label>
	    		<input type="text" id="finance-parent-expensesSortAddPage" value="<c:out value="${expensesSort.pid }"></c:out>" name="expensesSort.pid" <c:if test="${editFlag ==false}">readonly="readonly"</c:if> />
	    	</p>
	    	<p>
	    		<label>对应会计科目：</label>
	    		<input type="text" id="finance-accountingsubject-expensesSortAddPage" value="<c:out value="${expensesSort.accountingsubject }"></c:out>" name="expensesSort.accountingsubject" />
	    	</p>
	    	<p style="height:auto;width:auto;">
	    		<label>备注：</label>
	    		<textarea rows="3" cols="50" class="easyui-validatebox" style="width: 195px;" name="expensesSort.remark" validType="maxLen[200]"><c:out value="${expensesSort.remark }"></c:out></textarea>
	    	</p>
	    	<p>
	    		<label>状态：</label>
	    		<input id="common-combobox-state" value="${expensesSort.state }" disabled="disabled" class="easyui-combobox" style="width: 200px" />
	    	</p>
	    </div>
    </form>
    <script type="text/javascript">
    	//加载下拉框
    	loaddropdown();
		
		maxLen();
		
		function  editExpensesSort(type){
        	if(type == "save"){
        		if(!$("#finance-form-expensesSortEditPage").form('validate')){
        			return false;
        		}
        	}
        	loading("提交中..");
        	var ret = expensesSort_ajaxContent($("#finance-form-expensesSortEditPage").serializeJSON(),'basefiles/finance/editExpensesSort.do?type='+type);
        	var retJson = $.parseJSON(ret);
        	loaded();
        	if(retJson.flag){
        		if(type == "save"){
        			$.messager.alert("提醒","保存成功");
        		}
        		else{
        			$.messager.alert("提醒","暂存成功");
        		}
        		//更新所有子节点
       			var map = retJson.nodes;
       			var treeObj = $.fn.zTree.getZTreeObj("finance-Tree-expensesSort");
       			for(var key in map){
       				var object = map[key];
       				var node = treeObj.getNodeByParam("id", key, null);
       				node.id = object.id;
					node.text = object.text;
					node.parentid = object.parentid;
					node.state = object.state;
					treeObj.updateNode(node);
       			}
	  		    refreshLayout("市场活动【详情】",'basefiles/finance/expensesSortViewPage.do?id='+$("#finance-id-expensesSortAddPage").val());
        	}
        	else{
        		if(type == "save"){
        			$.messager.alert("提醒","保存失败");
        		}
        		else{
        			$.messager.alert("提醒","暂存失败");
        		}
        	}
        }
		
    	$(function(){
    		validLengthAndUsed('${len}', "basefiles/finance/isUsedExpensesSortID.do", $("#finance-parentId-expensesSortAddPage").val(), $("#expensesSort-thisid").val(), "该编号已被使用，请另输编号！"); //验证输入长度
    		validUsed('basefiles/finance/isUsedExpensesSortName.do','',$("#expensesSort-thisname").val(),'该名称已被使用,请另输入!');
    		$("#finance-parent-expensesSortAddPage").widget({
    			name:'t_base_finance_expenses_sort',
				col:'pid',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:false,
    			onChecked: function(data, check){
    				if(check){
    					$("#finance-id-expensesSortAddPage").val(data.id + $("#finance-thisId-expensesSortAddPage").val());
    					if($("#finance-thisName-expensesSortAddPage").val() != ""){
    						$("#finance-name-expensesSortAddPage").val(data.name + '/' + $("#finance-thisName-expensesSortAddPage").val());
    					}
    					else{
    						$("#finance-name-expensesSortAddPage").val(data.name);
    					}
    					$("#finance-parentId-expensesSortAddPage").val(data.id);
    					$("#finance-parentName-expensesSortAddPage").val(data.name);
    					$("#finance-thisId-expensesSort").val(data.id);
    					$("#finance-parentId-expensesSort").val(data.pid);
    					var hasLevel = $("#finance-hasLevel-expensesSort").val();
    					if((data.level+1)==hasLevel){
    						$.messager.alert("警告","该节点已为最大级次,不能再新增子节点!");
    						$("#finance-buttons-expensesSort").buttonWidget("disableButton", "button-hold");
    						$("#finance-buttons-expensesSort").buttonWidget("disableButton", "button-save");
    						return false;
    					}
    					else{
    						$("#finance-buttons-expensesSort").buttonWidget("enableButton", "button-hold");
    						$("#finance-buttons-expensesSort").buttonWidget("enableButton", "button-save");
    					}
    					validLengthAndUsed(expensesSort_lenArr[parseInt(data.level)], "basefiles/finance/isUsedExpensesSortID.do", $("#finance-parentId-expensesSortAddPage").val(), "该编号已被使用，请另输编号！"); //验证输入长度
    				}
    				else{
    					$("#finance-id-expensesSortAddPage").val($("#finance-thisId-expensesSortAddPage").val());
    					$("#finance-name-expensesSortAddPage").val($("#finance-thisName-expensesSortAddPage").val());
    					$("#finance-parentId-expensesSortAddPage").val("");
    					$("#finance-parentName-expensesSortAddPage").val("");
    					$("#finance-thisId-expensesSort").val("");
    					$("#finance-parentId-expensesSort").val("");
    					$("#finance-buttons-expensesSort").buttonWidget("enableButton", "button-hold");
    					$("#finance-buttons-expensesSort").buttonWidget("enableButton", "button-save");
    					validLengthAndUsed(expensesSort_lenArr[0], "basefiles/finance/isUsedExpensesSortID.do", $("#finance-parentId-expensesSortAddPage").val(), $("#expensesSort-thisid").val(), "该编号已被使用，请另输编号！"); //验证输入长度
    				}
    			},
    			onClear: function(){
    				$("#finance-id-expensesSortAddPage").val($("#finance-thisId-expensesSortAddPage").val());
    				$("#finance-name-expensesSortAddPage").val($("#finance-thisName-expensesSortAddPage").val());
    				$("#finance-parentId-expensesSortAddPage").val("");
    				$("#finance-parentName-expensesSortAddPage").val("");
    				$("#finance-thisId-expensesSort").val("");
    				$("#finance-parentId-expensesSort").val("");
    				$("#finance-buttons-expensesSort").buttonWidget("enableButton", "button-hold");
    				$("#finance-buttons-expensesSort").buttonWidget("enableButton", "button-save");
    				validLengthAndUsed(expensesSort_lenArr[0], "basefiles/finance/isUsedExpensesSortID.do", $("#finance-parentId-expensesSortAddPage").val(), $("#expensesSort-thisid").val(), "该编号已被使用，请另输编号！"); //验证输入长度
    			}
    		});
    		$("#finance-buttons-expensesSort").buttonWidget("setDataID", {id:$("#expensesSort-oldid").val(), state:'${expensesSort.state}', type:'edit'});
    		$("#finance-thisId-expensesSortAddPage").change(function(){
    			$("#finance-id-expensesSortAddPage").val($("#finance-parentId-expensesSortAddPage").val() + $(this).val());
    		});
    		$("#finance-thisName-expensesSortAddPage").change(function(){
    			var name = $("#finance-parentName-expensesSortAddPage").val();
    			if(name == ""){
    				$("#finance-name-expensesSortAddPage").val($(this).val());
    			}
    			else{
    				$("#finance-name-expensesSortAddPage").val(name + '/' + $(this).val());	
    			}
    			if($(this).val() == ""){
    				$("#finance-name-expensesSortAddPage").val(name);
    			}
    		});
    	});
    </script>
  </body>
</html>
