<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>费用分类新增页面</title>
  </head>
  
  <body>
  	<form action="" id="finance-form-expensesSortAddPage" method="post">
  		<input type="hidden" name="parentId" id="finance-parentId-expensesSortAddPage" value="<c:out value="${expensesSort.id }"></c:out>" />
  		<input type="hidden" name="parentName" id="finance-parentName-expensesSortAddPage" value="<c:out value="${expensesSort.name }"></c:out>" />
	    <div class="pageContent" style="width:500px;">
	    	<p>
	    		<label>本级编号：</label>
	    		<input class="easyui-validatebox" name="expensesSort.thisid" id="finance-thisId-expensesSortAddPage" data-options="required:true,validType:['validLength[${len}]']"  />
	    	</p>
	    	<p>
	    		<label>编号：</label>
	    		<input type="text" class="easyui-validatebox" readonly="readonly" value="<c:out value="${expensesSort.id }"></c:out>" name="expensesSort.id" id="finance-id-expensesSortAddPage" />
	    	</p>
	    	<p>
	    		<label>本级名称：</label>
	    		<input type="text" class="easyui-validatebox" name="expensesSort.thisname" id="finance-thisName-expensesSortAddPage" required="required" validType="validUsed[50]"/>
	    	</p>
	    	<p>
	    		<label>名称：</label>
	    		<input type="text" class="easyui-validatebox" readonly="readonly" value="<c:out value="${expensesSort.name }"></c:out>" name="expensesSort.name" id="finance-name-expensesSortAddPage" />
	    	</p>
	    	<p>
	    		<label>上级分类：</label>
	    		<input type="text" id="finance-parent-expensesSortAddPage" value="<c:out value="${expensesSort.id }"></c:out>" name="expensesSort.pid" />
	    	</p>
	    	<p>
	    		<label>对应会计科目：</label>
	    		<input type="text" id="finance-accountingsubject-expensesSortAddPage" name="expensesSort.accountingsubject" />
	    	</p>
	    	<p style="height:auto;width:auto;">
	    		<label>备注：</label>
	    		<textarea rows="3" cols="50" class="easyui-validatebox" style="width: 195px;" name="expensesSort.remark" validType="maxLen[200]"></textarea>
	    	</p>
	    	<p>
	    		<label>状态：</label>
	    		<select class="easyui-combobox" disabled="disabled" style="width:200px;"  name="expensesSort.state">
	    			<option value="4">新增</option>
	    		</select>
	    	</p>
	    </div>
    </form>
    <script type="text/javascript">
    	//加载下拉框
    	loaddropdown();
    	
    	maxLen();
    
    	function addExpensesSort(type){
    		if(type == "save"){
        		if(!$("#finance-form-expensesSortAddPage").form('validate')){
        			return false;
        		}
        	}
        	var ret = expensesSort_ajaxContent($("#finance-form-expensesSortAddPage").serializeJSON(),'basefiles/finance/addExpensesSort.do?type='+type);
        	var retJson = $.parseJSON(ret);
        	if(retJson.flag){
        		if(type == "save"){
        			$.messager.alert("提醒","保存成功");
        		}
        		else{
        			$.messager.alert("提醒","暂存成功");
        		}
        		var id = $("#finance-thisId-expensesSort").val();
	  		    var pid = $("#finance-parentId-expensesSort").val();
	  		    var treeObj = $.fn.zTree.getZTreeObj("finance-Tree-expensesSort");
	  		    var node = treeObj.getNodeByParam("id", id, null);
  		      	treeObj.addNodes(node, retJson.node); //增加子节点
	  		    var snode = treeObj.getNodeByParam("id", retJson.node.id, null);
	  		    treeObj.selectNode(snode, false); //选中节点
	  		    zTreeBeforeClick("finance-Tree-expensesSort", snode); //执行点击事件
	  		    refreshLayout("任务【详情】",'basefiles/finance/expensesSortViewPage.do?id='+$("#finance-id-expensesSortAddPage").val());
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
    	
    	validUsed('basefiles/finance/isUsedExpensesSortName.do','','','该名称已被使用,请另输入!');
    	
    	$(function(){
    		validLengthAndUsed('${len}', "basefiles/finance/isUsedExpensesSortID.do", $("#finance-parentId-expensesSortAddPage").val(), '', "该编号已被使用，请另输编号！"); //验证输入长度
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
    					validLengthAndUsed(expensesSort_lenArr[(data.level + 1)], "basefiles/finance/isUsedExpensesSortID.do", $("#finance-parentId-expensesSortAddPage").val(), '', "该编号已被使用，请另输编号！"); //验证输入长度
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
    					validLengthAndUsed(expensesSort_lenArr[0], "basefiles/finance/isUsedExpensesSortID.do", $("#finance-parentId-expensesSortAddPage").val(), '', "该编号已被使用，请另输编号！"); //验证输入长度
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
    				validLengthAndUsed(expensesSort_lenArr[0], "basefiles/finance/isUsedExpensesSortID.do", $("#finance-parentId-expensesSortAddPage").val(), '', "该编号已被使用，请另输编号！"); //验证输入长度
    			}
    		});
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
			$("#finance-buttons-expensesSort").buttonWidget("initButtonType", 'add');
    	});
    </script>
  </body>
</html>
