<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>出入库类型详情页面</title>
  </head>
  
  <body>
  	<input type="hidden" id="storageInout-oldid" value="<c:out value="${storageInout.id}"></c:out>"/>
  	<div class="pageContent" style="width:500px;">
	   	<p>
	   		<label style="text-align: right;">编码:</label>
	   		<input type="text" name="storageInout.id"  style="width:200px;" maxlength="20" value="<c:out value="${storageInout.id}"></c:out>" readonly="readonly"/>
	   	</p>
	   	<p>
	   		<label style="text-align: right;">名称:</label>
	   		<input type="text" name="storageInout.name" style="width:200px;" maxlength="50" value="<c:out value="${storageInout.name}"></c:out>" readonly="readonly"/>
	   	</p>
	   	<p>
	   		<label style="text-align: right;">类别:</label>
	   		<select name="storageInout.type" style="width:200px;" disabled="disabled">
				<option value="1" <c:if test="${storageInout.type=='1'}">selected="selected"</c:if>>入库</option>
				<option value="2" <c:if test="${storageInout.type=='2'}">selected="selected"</c:if>>出库</option>
			</select>
	   	</p>
	   	<p>
	   		<label style="text-align: right;">相关单位:</label>
	   		<select name="storageInout.referunit" style="width:200px;" disabled="disabled">
				<option <c:if test="${storageInout.referunit==null}">selected="selected"</c:if>></option>
				<option value="1" <c:if test="${storageInout.referunit=='1'}">selected="selected"</c:if>>供应商</option>
				<option value="2" <c:if test="${storageInout.referunit=='2'}">selected="selected"</c:if>>客户</option>
				<option value="3" <c:if test="${storageInout.referunit=='3'}">selected="selected"</c:if>>仓库</option>
				<option value="4" <c:if test="${storageInout.referunit=='4'}">selected="selected"</c:if>>部门</option>
			</select>
	   	</p>
   		<p style="height: auto;">
	   		<label style="text-align: right;">备注:</label>
	   		<textarea name="storageInout.remark" style="height: 100px;width: 200px;" readonly="readonly"><c:out value="${storageInout.remark}"></c:out></textarea>
	   	</p>
	   	<p>
	   		<label style="text-align: right;">状态:</label>
	   		<select name="storageInout.state" style="width:200px;" disabled="disabled">
				<option value="4" <c:if test="${storageInout.state=='4'}">selected="selected"</c:if>>新增</option>
				<option value="3" <c:if test="${storageInout.state=='3'}">selected="selected"</c:if>>暂存</option>
				<option value="2" <c:if test="${storageInout.state=='2'}">selected="selected"</c:if>>保存</option>
				<option value="1" <c:if test="${storageInout.state=='1'}">selected="selected"</c:if>>启用</option>
				<option value="0" <c:if test="${storageInout.state=='0'}">selected="selected"</c:if>>禁用</option>
			</select>
	   	</p>
	   	<p>
	   		<label style="text-align: right;">是否系统预制:</label>
	   		<select name="storageInout.issystem" style="width:200px;" disabled="disabled">
				<option value="1" <c:if test="${storageInout.issystem=='1'}">selected="selected"</c:if>>是</option>
				<option value="0" <c:if test="${storageInout.issystem=='0'}">selected="selected"</c:if>>否</option>
			</select>
	   	</p>
	</div>
	<script type="text/javascript">
		$("#storageInout-button").buttonWidget("setDataID", {id:$("#storageInout-oldid").val(), state:'${storageInout.state}',type:'view'});
	</script>
  </body>
</html>
