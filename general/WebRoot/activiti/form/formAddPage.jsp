<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>表单添加页面</title>
  </head>
  <body>
  	<style type="text/css">
  		.formAddTable{border-collapse:collapse;border:1px solid #7babcf;width:100%;}
  		.formAddTable td{border:1px solid #7babcf;line-height:28px;padding-left:3px;}
  		.formAddTable td.td1{background:#ebf5ff;width:120px;text-align:right;}
  	</style>
	<form action="act/formDesignPage2.do" target="form_design_window" id="activiti-form-formAddPage" method="post" style="padding:5px;">
		<table cellpadding="0" cellspacing="0" class="formAddTable">
            <tr>
                <td class="td1">表单Key：</td>
                <td><input class="easyui-validatebox" name="form.unkey" required="true" style="width:200px;" /></td>
            </tr>
			<tr>
				<td class="td1">表单标题：</td>
				<td><input class="easyui-validatebox" name="form.name" required="true" style="width:200px;" /></td>
			</tr>
			<tr>
				<td class="td1">表单类型：</td>
				<td>
					<select name="form.type">
                        <option></option>
						<c:forEach items="${list }" var="list">
							<option value="${list.unkey }">${list.name }</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr>
				<td class="td1">表单描述：</td>
				<td><!--<textarea cols="40" rows="5" name="form.intro"></textarea>-->
                    <input class="easyui-validatebox" name="form.intro" style="width:200px;" />
                </td>
			</tr>
            <!--
			<tr>
				<td class="td1">模板：</td>
				<td>
					<select></select>
				</td>
			</tr>
			-->
		</table>
	</form>
  </body>
</html>
