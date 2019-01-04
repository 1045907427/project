<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>市场活动分类详情页面</title>
  </head>
  
  <body>
  	<form action="" id="CRM-form-marketActivitySortViewPage" method="post">
  		<input type="hidden" id="marketActivitySort-oldid" value="<c:out value="${marketActivitySort.id }"></c:out>" />
	    <div class="pageContent" style="width:500px;">
	    	<p>
	    		<label>本级编号：</label>
	    		<input type="text" value="<c:out value="${marketActivitySort.thisid }"></c:out>" readonly="readonly" />
	    	</p>
	    	<p>
	    		<label>编号：</label>
	    		<input type="text" value="<c:out value="${marketActivitySort.id }"></c:out>" readonly="readonly" />
	    	</p>
	    	<p>
	    		<label>本级名称：</label>
	    		<input type="text" value="<c:out value="${marketActivitySort.thisname }"></c:out>" readonly="readonly" />
	    	</p>
	    	<p>
	    		<label>名称：</label>
	    		<input type="text" value="<c:out value="${marketActivitySort.name}"></c:out>" readonly="readonly" />
	    	</p>
	    	<p>
	    		<label>上级分类：</label>
	    		<input type="text" id="CRM-parent-marketActivitySortAddPage" disabled="disabled" value="<c:out value="${marketActivitySort.pid }"></c:out>" />
	    	</p>
	    	<p style="height:auto;width:auto;">
	    		<label>备注：</label>
	    		<textarea rows="3" cols="50" style="width: 200px;" readonly="readonly" name="marketActivitySort.remark"><c:out value="${marketActivitySort.remark }"></c:out></textarea>
	    	</p>
	    	<p>
	    		<label>状态：</label>
	    		<input id="CRM-widget-state" disabled="disabled" style="width: 206px" />
	    	</p>
	    </div>
    </form>
    <script type="text/javascript">
    	//状态
		$('#CRM-widget-state').widget({
			width:206,
			initValue:'${marketActivitySort.state }',
			name:'t_base_crm_marketactivity_sort',
			col:'state',
			singleSelect:true
		});
		
		$("#CRM-parent-marketActivitySortAddPage").widget({
   			name:'t_base_crm_marketactivity_sort',
			col:'pid',
   			singleSelect:true,
   			width:200,
   			onlyLeafCheck:true,
   			view:true
   		});
    	$(function(){
			$("#CRM-buttons-marketActivitySort").buttonWidget("setDataID", {id:$("#marketActivitySort-oldid").val(), state:'${marketActivitySort.state}', type:'view'});
			if('${marketActivitySort.state}' != '1'){
				$("#CRM-buttons-marketActivitySort").buttonWidget('disableButton','button-add');
			}
    	});
    </script>
  </body>
</html>
