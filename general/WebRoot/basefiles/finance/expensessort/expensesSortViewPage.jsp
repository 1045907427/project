<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>费用分类详情页面</title>
  </head>
  
  <body>
  	<form action="" id="finance-form-expensesSortViewPage" method="post">
  		<input type="hidden" id="expensesSort-oldid" value="<c:out value="${expensesSort.id }"></c:out>" />
	    <div class="pageContent" style="width:500px;">
	    	<p>
	    		<label>本级编号：</label>
	    		<input type="text" value="<c:out value="${expensesSort.thisid }"></c:out>" readonly="readonly" />
	    	</p>
	    	<p>
	    		<label>编号：</label>
	    		<input type="text" value="<c:out value="${expensesSort.id }"></c:out>" readonly="readonly" />
	    	</p>
	    	<p>
	    		<label>本级名称：</label>
	    		<input type="text" value="<c:out value="${expensesSort.thisname }"></c:out>" readonly="readonly" />
	    	</p>
	    	<p>
	    		<label>名称：</label>
	    		<input type="text" value="<c:out value="${expensesSort.name}"></c:out>" readonly="readonly" />
	    	</p>
	    	<p>
	    		<label>上级分类：</label>
	    		<input type="text" id="finance-parent-expensesSortAddPage" disabled="disabled" value="<c:out value="${expensesSort.pid }"></c:out>" />
	    	</p>
	    	<p>
	    		<label>对应会计科目：</label>
	    		<input type="text" id="finance-accountingsubject-expensesSortAddPage" disabled="disabled" value="<c:out value="${expensesSort.accountingsubject }"></c:out>" name="expensesSort.accountingsubject" />
	    	</p>
	    	<p style="height:auto;width:auto;">
	    		<label>备注：</label>
	    		<textarea rows="3" cols="50" style="width: 195px;" readonly="readonly" name="expensesSort.remark"><c:out value="${expensesSort.remark }"></c:out></textarea>
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
    	
    	$(function(){
    		$("#finance-parent-expensesSortAddPage").widget({
    			name:'t_base_finance_expenses_sort',
				col:'pid',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:true,
    			view:true
    		});
			$("#finance-buttons-expensesSort").buttonWidget("setDataID", {id:$("#expensesSort-oldid").val(), state:'${expensesSort.state}', type:'view'});
			if('${expensesSort.state}' != '1'){
				$("#finance-buttons-expensesSort").buttonWidget('disableButton','button-add');
			}
    	});
    </script>
  </body>
</html>
