<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>科目分类添加</title>
  </head> 
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
      <div data-options="region:'center',border:false">
	    <form action="basefiles/subject/addSubjectType.do" method="post" id="basefiles-form-addSubjectType">
	    	<table cellpadding="3" cellspacing="3" border="0">
		    	<tr>
   					<td width="90px" align="right">编码:</td>
   					<td align="left">
   						<input type="text" name="subject.id" value="${subject.id }" style="width:200px;" readonly="readonly" class="readonly"/>
   					</td>
 				</tr>
   				<tr>
   					<td width="90px" align="right">名称:</td>
   					<td align="left"><input type="text" name="subject.name" value="${subject.name}" style="width:200px;" readonly="readonly" class="readonly"/></td>
   				</tr>
   				<tr>
   					<td width="90px" align="right">代码:</td>
   					<td align="left"><input type="text" name="subject.typecode" value="${subject.typecode }" style="width:200px;" readonly="readonly" class="readonly"/></td>
   				</tr>
		    	<tr>
		    		<td align="right">状态:</td>
	    			<td align="left">	    				
		    			<select  style="width:200px;" disabled="disabled">
		    				<option value="0" <c:if test="${subject.state=='0' }">selected="selected"</c:if>>禁用</option>
                            <option value="1" <c:if test="${subject.state=='1' }">selected="selected"</c:if>>启用</option>
		    			</select>
	    			</td>
		    	</tr>
		    	<tr>
		    		<td align="right">备注:</td>
	    			<td align="left">
		    			<textarea rows="0" cols="0" name="subject.remark" style="width:200px;height:80px;" disabled="disabled">${subject.remark }</textarea>
	    			</td>
		    	</tr>
		    </table>
	    </form>
      </div>
  </div>
  </body>
</html>
