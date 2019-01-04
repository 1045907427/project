<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>供应商分类添加页面</title>
  </head>
  
  <body>
  	<form action="" id="buy-form-buySupplierSortAddPage" method="post">
  		<input type="hidden" id="buySupplierSort-oldid" value="<c:out value="${buySupplierSort.id }"></c:out>" />
  		<table cellpadding="2" cellspacing="2" border="0">
  			<tr>
  				<td>本级编号：</td>
  				<td><input type="text" style="width: 200px;" class="easyui-validatebox" value="<c:out value="${buySupplierSort.thisid }"></c:out>" readonly="readonly" /></td>
  			</tr>
  			<tr>
  				<td>编号：</td>
  				<td><input type="text" style="width: 200px;" class="easyui-validatebox" value="<c:out value="${buySupplierSort.id }"></c:out>" readonly="readonly" /></td>
  			</tr>
  			<tr>
  				<td>本级名称：</td>
  				<td><input type="text" style="width: 200px;" class="easyui-validatebox" value="<c:out value="${buySupplierSort.thisname }"></c:out>" readonly="readonly" /></td>
  			</tr>
  			<tr>
  				<td>名称：</td>
  				<td><input type="text" style="width: 200px;" class="easyui-validatebox" value="<c:out value="${buySupplierSort.name }"></c:out>" readonly="readonly" /></td>
  			</tr>
  			<tr>
  				<td>上级分类：</td>
  				<td><input type="text" id="buy-parent-buySupplierSortAddPage" disabled="disabled" value="<c:out value="${buySupplierSort.pid }"></c:out>" name="buySupplierSort.pid" /></td>
  			</tr>
  			<tr>
  				<td>备注：</td>
  				<td><textarea rows="3" cols="26"  style="width: 195px;" name="buySupplierSort.remark"><c:out value="${buySupplierSort.remark }"></c:out></textarea></td>
  			</tr>
  			<tr>
  				<td>状态：</td>
  				<td><select class="easyui-combobox" disabled="disabled" style="width:200px;"  name="buySupplierSort.state">
	    			<c:forEach items="${stateList }" var="list">
	    				<c:choose>
	    					<c:when test="${list.code == buySupplierSort.state }">
	    						<option value="${list.code }" selected="selected">${list.codename }</option>
	    					</c:when>
	    					<c:otherwise>
	    						<option value="${list.code }">${list.codename }</option>
	    					</c:otherwise>
	    				</c:choose>
	    			</c:forEach>
	    		</select></td>
  			</tr>
  		</table>
    </form>
    <script type="text/javascript">
    	$(function(){
    		$("#buy-parent-buySupplierSortAddPage").widget({
    			name:'t_base_buy_supplier_sort',
				col:'pid',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:true
    		});
			$("#buy-buttons-buySupplierSort").buttonWidget("setDataID", {id:$("#buySupplierSort-oldid").val(),state:'${buySupplierSort.state}',type:'view'});
			if('${buySupplierSort.state}' != '1'){
                $("#buy-buttons-buySupplierSort").buttonWidget('disableButton','button-add');
			}
    	});
    </script>
  </body>
</html>
