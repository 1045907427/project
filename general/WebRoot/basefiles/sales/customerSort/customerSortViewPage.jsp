<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>客户分类查看页面</title>
  </head>
  
  <body>
  	<form action="" id="sales-form-customerSortAddPage" method="post">
  		<input type="hidden" id="customersort-oldid" value="<c:out value="${customerSort.id }"></c:out>" />
	    <div class="pageContent" style="width:500px;">
	    	<p>
	    		<label>本级编号：</label>
	    		<input type="text" class="easyui-validatebox" value="<c:out value="${customerSort.thisid }"></c:out>" readonly="readonly" />
	    	</p>
	    	<p>
	    		<label>编号：</label>
	    		<input type="text" class="easyui-validatebox" value="<c:out value="${customerSort.id }"></c:out>" readonly="readonly" />
	    	</p>
	    	<p>
	    		<label>本级名称：</label>
	    		<input type="text" class="easyui-validatebox" value="<c:out value="${customerSort.thisname }"></c:out>" readonly="readonly" />
	    	</p>
	    	<p>
	    		<label>名称：</label>
	    		<input type="text" class="easyui-validatebox" value="<c:out value="${customerSort.name }"></c:out>" readonly="readonly" />
	    	</p>
	    	<p>
	    		<label>上级分类：</label>
	    		<input type="text" id="sales-parent-customerSortAddPage" disabled="disabled" value="<c:out value="${customerSort.pid }"></c:out>" name="customerSort.pid" />
	    	</p>
	    	<p style="height:auto;width:auto;">
	    		<label>备注：</label>
	    		<textarea style="width:195px;height:50px;" name="customerSort.remark"><c:out value="${customerSort.remark }"></c:out></textarea>
	    	</p>
	    	<p>
	    		<label>状态：</label>
	    		<select class="easyui-combobox" disabled="disabled" style="width:200px;"  name="customerSort.state">
	    			<c:forEach items="${stateList }" var="list">
	    				<c:choose>
	    					<c:when test="${list.code == customerSort.state }">
	    						<option value="${list.code }" selected="selected">${list.codename }</option>
	    					</c:when>
	    					<c:otherwise>
	    						<option value="${list.code }">${list.codename }</option>
	    					</c:otherwise>
	    				</c:choose>
	    			</c:forEach>
	    		</select>
	    	</p>
	    </div>
    </form>
    <script type="text/javascript">
    	$(function(){
    		$("#sales-parent-customerSortAddPage").widget({
    			name:'t_base_sales_customersort',
				col:'pid',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:true,
    			view:true
    		});
			$("#sales-buttons-customerSort").buttonWidget("setDataID", {id:$("#customersort-oldid").val(), state:'${customerSort.state}', type:'view'});
			if('${customerSort.state}' != '1'){
                $("#sales-buttons-customerSort").buttonWidget('disableButton','button-add');
			}
    	});
    </script>
  </body>
</html>
