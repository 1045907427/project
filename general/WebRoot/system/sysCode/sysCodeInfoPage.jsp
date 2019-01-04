<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>代码修改</title>
  </head>
  
  <body>
    	<div class="pageContent">
    		<p>
    			<label>代码:</label>
    			<input type="text" name="sysCode.code" readonly="readonly" value="${sysCode.code }" class="easyui-validatebox" required="true" style="width:200px;"/>
    		</p>
    		<p>
    			<label>代码名称:</label>
    			<input type="text" name="sysCode.codename" readonly="readonly" value="${sysCode.codename }" class="easyui-validatebox" required="true" style="width:200px;"/>
    		</p>
			<p>
				<label>代码值:</label>
				<input type="text" name="sysCode.codevalue" readonly="readonly" value="${sysCode.codevalue }" style="width:200px;"/>
			</p>
    		<p>
    			<label>代码类型:</label>
    			<input type="text" name="sysCode.type" readonly="readonly" value="${sysCode.type }" class="easyui-validatebox" required="true" style="width:200px;"/>
    		</p>
    		<p>
    			<label>代码类型名称:</label>
    			<input type="text" name="sysCode.typename" readonly="readonly" value="${sysCode.typename }" class="easyui-validatebox" required="true" style="width:200px;"/>
    		</p>
    		<p>
    			<label>排序:</label>
    			<input type="text" name="sysCode.seq" readonly="readonly" value="${sysCode.seq }" class="easyui-validatebox" required="true" style="width:200px;"/>
    		</p>
    		<p>
    			<label>状态:</label>
    			<select name="sysCode.state" style="width:200px;" disabled="disabled">
    				<option value="1" <c:if test="${sysCode.state=='1' }">selected="selected"</c:if> >有效</option>
    				<option value="0" <c:if test="${sysCode.state=='0' }">selected="selected"</c:if> >无效</option>
    			</select>
    		</p>
    	</div>
  </body>
</html>
