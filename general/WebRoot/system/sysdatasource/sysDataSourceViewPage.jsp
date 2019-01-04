<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>数据源配置查看页面</title>
  </head>
  
  <body>
<div class="easyui-layout" data-options="fit:true">
      <div data-options="region:'center',border:false">
       		<div align="center" style="padding: 10px;">
			    <form id="system-form-addSysDataSource">
					<table cellpadding="3" cellspacing="3" border="0">
						<tr>
							<td align="right">名称:</td>
							<td align="left">
								<input type="text" name="sysDataSource.name" value="${sysDataSource.name}" id="system-SysDataSource-form-name" readonly="readonly" style="width:300px;" autocomplete="off" />
							</td>
						</tr>
						<tr>
							<td align="right">代码:</td>
							<td align="left">
								<input type="text" name="sysDataSource.code" value="${sysDataSource.code}" id="system-SysDataSource-form-code" readonly="readonly" style="width:300px;" autocomplete="off" />
							</td>
						</tr>
						<tr>
							<td align="right">JDBC类型:</td>
							<td align="left">
								<input type="text" name="sysDataSource.jdbctype" value="${sysDataSource.jdbctype}" id="system-SysDataSource-form-jdbctype" readonly="readonly" style="width:300px;" autocomplete="off" />
							</td>
						</tr>
						<tr>
							<td align="right">JDBC驱动:</td>
							<td align="left">
								<input type="text" name="sysDataSource.jdbcdriver" value="${sysDataSource.jdbcdriver}" id="system-SysDataSource-form-jdbcdriver" readonly="readonly" style="width:300px;" autocomplete="off" />
							</td>
						</tr>
						<tr>
							<td align="right">JDBC链接:</td>
							<td align="left">
								<input type="text" name="sysDataSource.jdbcurl" value="${sysDataSource.jdbcurl}" id="system-SysDataSource-form-jdbcurl" readonly="readonly" style="width:300px;" autocomplete="off" />
							</td>
						</tr>
						<tr>
							<td align="right">数据库名称:</td>
							<td align="left">
								<input type="text" name="sysDataSource.dbname" value="${sysDataSource.dbname}" id="system-SysDataSource-form-dbname" readonly="readonly" style="width:300px;" autocomplete="off" />
							</td>
						</tr>
						<tr>
							<td align="right">用户名:</td>
							<td align="left">
								<input type="text" name="sysDataSource.dbuser" value="${sysDataSource.dbuser}" id="system-SysDataSource-form-dbuser" readonly="readonly" style="width:300px;" autocomplete="off" />
							</td>
						</tr>
						<tr>
							<td align="right">密码:</td>
							<td align="left">
								已加密，不可见
							</td>
						</tr>
						<tr>
							<td align="right">状态:</td>
							<td align="left">
								<select style="width:300px;" disabled="disabled">
									<option value="1" <c:if test="${sysDataSource.state=='1' }">selected="selected"</c:if> >有效</option>
									<option value="0" <c:if test="${sysDataSource.state=='0' }">selected="selected"</c:if> >无效</option>
								</select>
							</td>
						</tr>
						<tr>
							<td align="right">备注:</td>
							<td align="left">
								<textarea rows="0" cols="0" name="sysDataSource.remark" style="width:300px;height:80px;" disabled="disabled">${sysDataSource.remark}</textarea>
							</td>
						</tr>
    				</table>
    				<input type="hidden" value="${sysDataSource.id }" />
			    </form>
	    	</div>
      </div>
  </div>
  </body>
</html>
