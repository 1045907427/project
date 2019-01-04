<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>系统参数信息详情</title>
  </head>
  
  <body>
  <script type="text/javascript">
  </script>
  	<table cellpadding="0" cellspacing="5" border="0" class="pageContent">
    	<tr>
    		<td><label>参数名称:</label></td>
    		<td><input type="text" name="sysParam.pname" value="${sysParam.pname }" readonly="readonly" class="easyui-validatebox" style="width:200px;"/></td>
    	</tr>
    	<tr>
    		<td style=""><label>参数描述:</label></td>
    		<td><textarea name="sysParam.description" class="easyui-validatebox" readonly="readonly" style="width:198px;height:35px;">${sysParam.description }</textarea></td>
    	</tr>
    	<tr>
    		<td><label>参数值:</label></td>
    		<td><input type="text" name="sysParam.pvalue" value="${sysParam.pvalue }" readonly="readonly" class="easyui-validatebox" style="width:200px;"/></td>
    	</tr>
    	<tr>
    		<td><label>参数值描述:</label></td>
    		<td><textarea name="sysParam.pvdescription" class="easyui-validatebox" readonly="readonly" style="width:198px;height:35px;">${sysParam.pvdescription }</textarea></td>
    	</tr>
    	<tr>
    		<td><label>参数用途:</label></td>
    		<td><textarea name="sysParam.puser" class="easyui-validatebox" readonly="readonly" style="width:198px;height:38px;">${sysParam.puser }</textarea></td>
    	</tr>
    	<tr>
			<td><label>模块名称:</label></td>
			<td><input type="text" name="sysParam.moduleid" id="sysParam-moduleid-widget" value="${sysParam.moduleid }" readonly="readonly"/></td>
		</tr>
    	<tr>
    		<td><label>状态:</label></td>
    		<td><select name="sysParam.state" style="width:204px;" disabled="disabled" >
    		<option value="1" <c:if test="${sysParam.state=='1' }">selected="selected"</c:if> >启用</option>
    		<option value="0" <c:if test="${sysParam.state=='0' }">selected="selected"</c:if> >停用</option>
    		</select></td>
    	</tr>
    </table>
    <script type="text/javascript">
    	$(function(){
    		loadDropdown();
    	});
    </script>
  </body>
</html>
