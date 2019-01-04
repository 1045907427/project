<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>仓库类型添加页面</title>
  </head>
  
  <body>
  	<input id="storageType-oldid" type="hidden" value="<c:out value="${storageType.id}"></c:out>"/>
  	<div class="pageContent" style="width:500px;">
	   	<p>
	   		<label style="text-align: right;">编码:</label>
	   		<input type="text" name="storageType.id" style="width:200px;" maxlength="20" value="<c:out value="${storageType.id }"></c:out>" readonly="readonly"/>
	   	</p>
	   	<p>
	   		<label style="text-align: right;">名称:</label>
	   		<input type="text" name="storageType.name" style="width:200px;" maxlength="50" value="<c:out value="${storageType.name }"></c:out>" readonly="readonly"/>
	   	</p>
	   	<p style="height: auto;">
	   		<label style="text-align: right;">备注:</label>
	   		<textarea name="storageType.remark" style="height: 100px;width: 200px;" readonly="readonly"><c:out value="${storageType.remark }"></c:out></textarea>
	   	</p>
	   	<p>
	   		<label style="text-align: right;">状态:</label>
	   		<select name="storageType.state" style="width:200px;" disabled="disabled">
				<option value="4" <c:if test="${storageType.state=='4'}">selected="selected"</c:if>>新增</option>
				<option value="3" <c:if test="${storageType.state=='3'}">selected="selected"</c:if>>暂存</option>
				<option value="2" <c:if test="${storageType.state=='2'}">selected="selected"</c:if>>保存</option>
				<option value="1" <c:if test="${storageType.state=='1'}">selected="selected"</c:if>>启用</option>
				<option value="0" <c:if test="${storageType.state=='0'}">selected="selected"</c:if>>禁用</option>
			</select>
	   	</p>
	</div>
	<script type="text/javascript">
		$("#storageType-button").buttonWidget("setDataID", {id:$("#storageType-oldid").val(), state:'${storageType.state}',type:'view'});
	</script>
  </body>
</html>
