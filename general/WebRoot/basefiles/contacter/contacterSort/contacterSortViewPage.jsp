<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>联系人分类查看页面</title>
  </head>
  
  <body>
  	<form action="" id="contacter-form-contacterSortAddPage" method="post">
  		<input type="hidden" id="contacterSort-oldid" value="<c:out value="${contacterSort.id }"></c:out>"/>
	    <table cellpadding="2" cellspacing="2" border="0">
	    	<tr>
	    		<td>本级编号：</td>
	    		<td><input type="text" style="width: 200px;" class="easyui-validatebox" value="<c:out value="${contacterSort.thisid }"></c:out>" readonly="readonly" /></td>
	    	</tr>
	    	<tr>
	    		<td>编号：</td>
	    		<td><input type="text" style="width: 200px;" class="easyui-validatebox" value="<c:out value="${contacterSort.id }"></c:out>" readonly="readonly" /></td>
	    	</tr>
	    	<tr>
	    		<td>本级名称：</td>
	    		<td><input type="text" style="width: 200px;" class="easyui-validatebox" value="<c:out value="${contacterSort.thisname }"></c:out>" readonly="readonly" /></td>
	    	</tr>
	    	<tr>
	    		<td>名称：</td>
	    		<td><input type="text" style="width: 200px;" class="easyui-validatebox" value="<c:out value="${contacterSort.name }"></c:out>" readonly="readonly" /></td>
	    	</tr>
	    	<tr>
	    		<td>上级分类：</td>
	    		<td><input type="text" id="contacter-parent-contacterSortAddPage" disabled="disabled" value="<c:out value="${contacterSort.pid }"></c:out>" name="contacterSort.pid" /></td>
	    	</tr>
	    	<tr>
	    		<td>备注：</td>
	    		<td><textarea style="width: 200px;height: 50px;" name="contacterSort.remark"><c:out value="${contacterSort.remark }"></c:out></textarea></td>
	    	</tr>
	    	<tr>
	    		<td>状态：</td>
	    		<td><select class="easyui-combobox" disabled="disabled" style="width:206px;"  name="contacterSort.state">
	    			<c:forEach items="${stateList }" var="list">
	    				<c:choose>
	    					<c:when test="${list.code == contacterSort.state }">
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
    		$("#contacter-parent-contacterSortAddPage").widget({
    			name:'t_base_linkman_sort',
				col:'pid',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:true,
    			view:true
    		});
			$("#contacter-buttons-contacterSort").buttonWidget("setDataID", {id:$("#contacterSort-oldid").val(), state:'${contacterSort.state}', type:'view'});
			if('${contacterSort.state}' != '1'){
				$("#button-add").linkbutton("disable");
			}
    	});
    </script>
  </body>
</html>
