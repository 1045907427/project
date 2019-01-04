<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>打印模板资源查看</title>
  </head>
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
      <div data-options="region:'center',border:false">
    	<table cellpadding="3" cellspacing="3" border="0">
			<tr>	    			
				<td width="100px" align="right">资源编号:</td>
				<td align="left">
					<input type="text" value="${printTempletResource.viewid }" readonly="readonly" style="width:200px"/>
				</td>
			</tr>
	    	<tr>
	    		<td width="100px" align="right">模板代码:</td>
	    		<td align="left">
					<div style="line-height:25px;" >${printTempletResource.code }</div>
					<div style="line-height:25px;" >${codename }</div>
    			</td>
    		</tr>
    		<tr>
	    		<td width="100px" align="right">资源名称:</td>
	    		<td align="left">
    				<input type="text" name="printTempletResource.name" readonly="readonly" value="${printTempletResource.name }" class="easyui-validatebox" required="true" style="width:200px;"/>
    			</td>
    		</tr>
			<tr>
				<td align="right">模板文件(jasper):</td>
				<td align="left">
					${printTempletResource.templetfile }
				</td>
			</tr>
			<tr>
				<td align="right">模板源文件(jrxml):</td>
				<td align="left">
					${printTempletResource.sourcefile }
				</td>
			</tr>
			<tr>
				<td align="right">纸张:</td>
				<td align="left">
					<div id="tplresource-form-papaersizeid-widget-uidiv">${papersizename }</div>
				</td>
			</tr>
    		<tr>
	    		<td align="right">状态:</td>
	    		<td align="left">    				
	    			<select style="width:200px;" disabled="disabled">
	    				<option value="1" <c:if test="${printTempletResource.state=='1' }">selected="selected"</c:if> >有效</option>
	    				<option value="0" <c:if test="${printTempletResource.state=='0' }">selected="selected"</c:if> >无效</option>
	    			</select>
    			</td>
    		</tr>
    		<tr>
	    		<td align="right">备注:</td>
	    		<td align="left">    				
	    			<textarea rows="0" cols="0" name="printTempletResource.remark" style="width:200px;height:80px;" readonly="readonly">${printTempletResource.remark }</textarea>
    			</td>
    		</tr>
    	</table>
    	</div>
    </div>
  </body>
</html>
