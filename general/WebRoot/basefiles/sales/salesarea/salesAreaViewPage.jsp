<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售区域添加页面</title>
  </head>
  
  <body>
  	<form action="basefiles/addSalesArea.do" id="sales-form-salesAreaAddPage" method="post">
  		<input type="hidden" id="salesArea-oldid" value="<c:out value="${salesArea.id }"></c:out>" />
	    <div class="pageContent" style="width:500px;">
	    	<p>
	    		<label>本级编号：</label>
	    		<input type="text" class="easyui-validatebox" value="<c:out value="${salesArea.thisid }"></c:out>" readonly="readonly" />
	    	</p>
	    	<p>
	    		<label>编号：</label>
	    		<input type="text" class="easyui-validatebox" value="<c:out value="${salesArea.id }"></c:out>" readonly="readonly" />
	    	</p>
	    	<p>
	    		<label>本级名称：</label>
	    		<input type="text" class="easyui-validatebox" value="<c:out value="${salesArea.thisname }"></c:out>" readonly="readonly" />
	    	</p>
	    	<p>
	    		<label>名称：</label>
	    		<input type="text" class="easyui-validatebox" value="<c:out value="${salesArea.name }"></c:out>" readonly="readonly" />
	    	</p>
	    	<p>
	    		<label>上级分类：</label>
	    		<input type="text" id="sales-parent-salesAreaAddPage" disabled="disabled" value="<c:out value="${salesArea.pid }"></c:out>" />
	    	</p>
	    	<p style="height:auto;width:auto;">
	    		<label>备注：</label>
	    		<textarea style="width:195px;height:50px;" name="salesArea.remark"><c:out value="${salesArea.remark }"></c:out></textarea>
	    	</p>
	    	<p>
	    		<label>状态：</label>
	    		<select class="easyui-combobox" disabled="disabled" style="width:200px;"  name="salesArea.state">
	    			<c:forEach items="${stateList }" var="list">
	    				<c:choose>
	    					<c:when test="${list.code == salesArea.state }">
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
    		$("#sales-parent-salesAreaAddPage").widget({
    			name:'t_base_sales_area',
				col:'pid',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:true,
    			view:true
    		});
			$("#sales-buttons-salesArea").buttonWidget("setDataID", {id:$("#salesArea-oldid").val(), state:'${salesArea.state}', type:'view'});
			if('${salesArea.state}' != '1'){
                $("#sales-buttons-salesArea").buttonWidget('disableButton','button-add');
			}
    	});
    </script>
  </body>
</html>
