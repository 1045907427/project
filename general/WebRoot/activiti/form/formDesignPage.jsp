<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>表单设计页面</title>
    <%@include file="/include.jsp" %>   
  	<script type="text/javascript" src="js/kindeditor/kindeditor-min.js"></script> 
  	<script type="text/javascript" src="js/kindeditor/lang/zh_CN.js"></script> 
  	<script type="text/javascript" src="js/kindeditor/extends/lang-extends.js"></script> 
  	<link type="text/css" rel="stylesheet" href="js/kindeditor/extends/kind-extends.css"></link>
  </head>
  <body>
  	<link type="text/css" rel="stylesheet" href="js/kindeditor/extends/kind-extends.css"></link>
  	<style type="text/css">
  		.formDesignTable{border-collapse:collapse;border:1px solid #7babcf;width:100%;}
  		.formDesignTable td{border:1px solid #7babcf;line-height:28px;padding-left:3px;}
  		.formDesignTable td.td1{background:#ebf5ff;width:10%;text-align:right;}
  	</style>
  	<div class="easyui-panel" data-options="fit:true,border:false" style="padding:5px;">
  	<div class="easyui-layout" data-options="fit:true" style="padding:5px;">
		<form id="activiti-form-formDesignPage" action="act/addForm.do" method="post">
  		<div data-options="region:'north',border:false">
  			<div style="border-bottom:1px solid #7babcf;margin-bottom:5px;padding-bottom:5px;">
		  		<a class="easyui-linkbutton" id="activiti-save-formDesignPage" data-options="plain:true,iconCls:'button-save'">保存</a>
		  		<a class="easyui-linkbutton" id="activiti-view-formDesignPage" data-options="plain:true,iconCls:'button-view'">预览</a>
		  		<a class="easyui-linkbutton" id="activiti-close-formDesignPage" data-options="plain:true,iconCls:'button-close'">关闭</a>
		  	</div>
				<input type="hidden" value="${form.unkey }" name="form.unkey" />
				<div>
					<table class="formDesignTable">
						<tr>
							<td class="td1">表单分类：</td>
							<td style="width:15%">
								<select name="form.type">
									<c:forEach items="${list }" var="list">
										<c:choose>
											<c:when test="${list.unkey == form.type }">
												<option value="${list.unkey }" selected="selected">${list.name }</option>
											</c:when>
											<c:otherwise>
												<option value="${list.unkey }">${list.name }</option>
											</c:otherwise>
										</c:choose>
									</c:forEach>
								</select>
							</td>
							<td class="td1">表单标题：</td>
							<td style="width:25%"><input class="easyui-validatebox" name="form.name" value="${form.name }" style="width:90%" data-options="required:true" /></td>
							<td class="td1">描述：</td>
							<td style="width:30%"><input class="easyui-validatebox" name="form.intro" value="${form.intro }" style="width:90%" /></td>
						</tr>
						</tr>
					</table>
				</div>
  		</div>
  		<div data-options="region:'center',border:false" style="padding-top:5px;">
  			<div class="easyui-panel" data-options="fit:true" id="form_design_panel" style="padding:5px;">
  				<textarea style="width:100%;height:100%;" name="form.detail" id="form_design_editor">${content }</textarea>
  			</div>
  		</div>
		</form>
  	</div>
  	</div>
  	<script type="text/javascript">
  		$(function(){
  			var editor;
  			var options = {
  				height:$("#form_design_editor").height(),
  				filterMode:false,
  				zIndex:8997,
  				syncType:"form",
				items:[
						'source', '|', 'undo', 'redo', '|', 'cut', 'copy', 'paste', 'plainpaste', 'wordpaste', 'selectall', '|',
						 'justifyleft', 'justifycenter', 'justifyright', 'justifyfull', 'indent','outdent', '|', 
						 'fontname', 'fontsize', 'forecolor', 'hilitcolor', 'bold', 'italic', 'underline', '/', 
						 'table', '|', 'zdiv', 'zinputtext', 'zmultitext', 'zselect', 'zcheckbox', 'zradio', 'zdictionary', 'zusers', 'zdepart', 'zmacro'
					],
				afterChange: function (e) {
					this.sync();
				}    
  			}
			KindEditor.ready(function(K) {
                editor = K.create('#form_design_editor', options);
       		});
       		$("#activiti-save-formDesignPage").click(function(){
       			var flag = $("#activiti-form-formDesignPage").form("validate");
       			if(flag == false) return ;
       			$("#activiti-form-formDesignPage").submit();
       		});
       		$("#activiti-view-formDesignPage").click(function(){
       			runCode(editor.html());
       		});
       		$("#activiti-close-formDesignPage").click(function(){
       			window.close();
       		});
       		$("#activiti-form-formDesignPage").form({
				onSubmit: function(){  
		  		  	loading("提交中..");
		  		},  
		  		success:function(data){
		  		  	loaded();
		  		  	var json = $.parseJSON(data);
		  		  	if(json.flag == true){
		  		  		$.messager.alert("提醒", "添加成功");	
		  		  		if("${form.id }" == ""){
		  		  			top.location = "act/formDesignPage.do?key=${form.unkey}";
		  		  		}
		  		  	}
		  		  	else{
		  		  		$.messager.alert("提醒", "添加失败");	
		  		  	}
		  		}
			});
  		});
  		function runCode(html){ 
			window.open('act/formPreviewPage.do?key=${form.unkey}', "表单预览");
		}
  	</script>
  </body>
</html>
