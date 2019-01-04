<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>新建工作引导页面</title>
  </head>
  
  <body>
	<style type="text/css">
		*{margin:0;padding:0;}
		.activity div{padding:5px;position:absolute;width:200px;line-height:22px;background:#FFFFA3;border:1px solid #F1D031;font-size:12px;color:#555555;display:none;z-index:999;}
		.activity div dd{clear:both;}
		.activity div dd span{width:60px;float:left;text-align:right;}
		#work-layout-showNewWorkPage p{clear:both;}
	</style>
	<div class="easyui-panel" title="新建工作流程" data-options="fit:true,border:false">
		<div class="easyui-layout" id="activiti-layout-newWorkGuidePage" data-options="fit:true">  
	        <div title="填写该工作的名称或文号" data-options="region:'north',border:false,collapsible:false" style="height:150px;">
	        	<!-- <form action="act/workHandlePage.do" id="activiti-form-newWorkGuidePage" method="post"> -->
	        	<form action="act/workHandlePage.do" id="activiti-form-newWorkGuidePage" method="post">
	           	<div class="pageContent">
	           		<div>
	           			<input type="hidden" name="definitionkey" value="${definition.unkey }" />
	           			<input type="text" name="title" id="title" style="width:400px; height: 25px; line-height:20px;padding:5px;border:1px solid #999999;padding-left: 50px;" value="${title }" maxlength="50" autocomplete="off"/>
	           			<input type="text" name="pretitle" style="width:55px; height: 25px; line-height:20px;padding:5px;border:1px solid #999999;BORDER-RIGHT-STYLE: none; margin-left: -406px; color: #999999;" value="{OA号}-" onfocus="javascript:$('#title').focus();"/>
	           		</div>
	           		<div class="clear"></div>
					<div class="buttondiv" style="text-align:left;">
						<div style="color: #F00; width: 260px; padding-bottom: 10px;">※工作名称（文号）一旦确定后将无法更改！</div>
						<a href="javascript:;" id="activiti-save-newWorkGuidePage" class="easyui-linkbutton" data-options="iconCls:'button-add'">新建并办理</a>
					 </div>
		         </div>
		         </form>
			</div>
			<div title="流程图及说明" data-options="region:'center'" style="padding: 2px;">
				<div id="activiti_image_newWorkGuidePage" style="position:relative;">
					<img src="image/loading.gif" style="vertical-align: top" /> 流程图加载中...
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			$("#activiti-save-newWorkGuidePage").click(function(){
				$("#activiti-form-newWorkGuidePage").submit();
			});
			$.ajax({
				url:'act/getDefinitionDiagram.do',
				type:'post',
				dataType:'json',
				data:'definitionkey=${definition.unkey}',
				success:function(json){
					$("#activiti_image_newWorkGuidePage").html("<img src='activiti/diagram/"+json.path+"' />");
				}
			});
	  	});
	</script>
  </body>
</html>
