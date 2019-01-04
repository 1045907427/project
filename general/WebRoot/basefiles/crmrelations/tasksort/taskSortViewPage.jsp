<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>任务分类复制页面</title>
  </head>
  
  <body>
    <form action="" id="taskSort-form-view" method="post">
  		<input type="hidden" id="taskSort-oldid" value="<c:out value="${taskSort.id }"></c:out>" />
  		<div class="pageContent" style="width:500px;">
	    	<p>
	    		<label>本级编号：</label>
	    		<input type="text" name="taskSort.thisid" value="<c:out value="${taskSort.thisid }"></c:out>" readonly="readonly"/>
	    	</p>
	    	<p>
	    		<label>编号：</label>
	    		<input type="text" readonly="readonly" value="<c:out value="${taskSort.id }"></c:out>" name="taskSort.id" />
	    	</p>
	    	<p>
	    		<label>本级名称：</label>
	    		<input type="text" name="taskSort.thisname" value="<c:out value="${taskSort.thisname }"></c:out>" readonly="readonly"/>
	    	</p>
	    	<p>
	    		<label>名称：</label>
	    		<input type="text" readonly="readonly" value="<c:out value="${taskSort.name}"></c:out>" name="taskSort.name" />
	    	</p>
	    	<p>
	    		<label>上级分类：</label>
	    		<input type="text" id="crmrelations-parent-taskSort" disabled="disabled" value="<c:out value="${taskSort.pid }"></c:out>" name="taskSort.pid" />
	    	</p>
	    	<p>
	    		<label>默认责任部门：</label>
	    		<input type="text" class="easyui-validatebox" id="crmrelations-defaultDept-taskSort" name="taskSort.defaultdeptid" value="<c:out value="${taskSort.defaultdeptid}"></c:out>" disabled="disabled"/>
	    	</p>
	    	<p>
	    		<label>默认责任人：</label>
	    		<input type="text" id="crmrelations-defaultUser-taskSort" name="taskSort.defaultuserid" value="<c:out value="${taskSort.defaultuserid}"></c:out>" disabled="disabled"/>
	    	</p>
	    	<p>
	    		<label>默认费用分类：</label>
	    		<input type="text" id="crmrelations-defaultexpenses-taskSort" name="taskSort.defaultexpensesid" value="<c:out value="${taskSort.defaultexpensesid}"></c:out>" disabled="disabled"/>
	    	</p>
	    	<p style="height:auto;width:auto;">
	    		<label>备注：</label>
	    		<textarea rows="3" cols="50" style="width: 200px;" name="taskSort.remark" readonly="readonly"><c:out value="${taskSort.remark}"></c:out></textarea>
	    	</p>
	    	<p>
	    		<label>状态：</label>
	    		<input id="common-combobox-state" value="${taskSort.state }" disabled="disabled" class="easyui-combobox" style="width: 206px" />
	    	</p>
	    </div>
    </form>
    <script type="text/javascript">
    	//加载下拉框
    	loadDropdown();
    	
    	$(function(){
    		$("#crmrelations-parent-taskSort").widget({
    			name:'t_base_crm_task_sort',
				col:'pid',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:true,
    			view:true
    		});
			$("#crmrelations-buttons-taskSort").buttonWidget("setDataID", {id:$("#taskSort-oldid").val(), state:'${taskSort.state}', type:'view'});
			if('${taskSort.state}' != '1'){
				$("#crmrelations-buttons-taskSort").buttonWidget('disableButton','button-add');
			}
    	});
    </script>
  </body>
</html>
