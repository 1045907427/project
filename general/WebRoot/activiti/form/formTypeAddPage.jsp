<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>表单分类添加页面</title>
  </head>
  <body>
  	<style type="text/css">
		.content{padding:5px;}
		.content p{clear:both;line-height:22px;}
		.content p label{width:80px;float:left;text-align:right;}
	</style>
  	<form id="activiti-form-formTypeAddPage" method="post" action="act/addFormType.do">
		<div class="content">
			<p>
				<label>父节点：</label>
				<span>${name }</span>
				<input type="hidden" name="formType.pid" value="${pid }" />
			</p>
			<p>
				<label>*节点名称：</label>
				<input class="easyui-validatebox" name="formType.name" data-options="required:true" />
			</p>
			<p>
				<label>*节点Key：</label>
				<input class="easyui-validatebox" name="formType.unkey" data-options="required:true" />
			</p>
		</div>
	</form>
  </body>
</html>
