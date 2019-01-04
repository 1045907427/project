<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<title>待办工作</title>
  </head>
  <body>
	<style type="text/css">
		.to_do_work_panel a{color:#555555;text-decoration:none;}
		.to_do_work_panel a:hover{color:#555555;text-decoration:underline;}
	</style>
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'north',border:false" style="height:23px;line-height:23px;">
			<div style="padding:0 8px">
				<a href="javascript:;" style="color:red;" class="to_do_work">待办工作(${count })</a>
			</div>
		</div>
		<div data-options="region:'center',border:false" style="margin-bottom:5px;">
			<div style="padding:0 8px;">
				<div class="easyui-panel to_do_work_panel" id="to_do_work_panel" data-options="fit:true,border:false">
					<ul style="margin:0;padding:0;">
						<c:forEach var="item" items="${list }">
							<li style="margin:0;padding:0;line-height:23px;">
								[${item.definitionname}]
								<a href="javascript:;" onclick="handleDetail('${item.taskid }', '${item.taskkey }', '${item.instanceid }');">${item.title }
								[编号 ${item.id }]
								${item.taskname }</a>
							</li>
						</c:forEach>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			$(".to_do_work").click(function(){
				var index = $(".to_do_work").index(this);
				$(".to_do_work").css("color","").eq(index).css("color", "red");
				$(".to_do_work_panel").hide().eq(index).show();
			});
		});
		function handleDetail(taskid, taskkey, instanceid, definitionkey){
			top.addOrUpdateTab("act/workHandlePage.do?taskid="+ taskid+ "&taskkey="+ taskkey +"&instanceid="+ instanceid, "处理工作");
		};
	</script>
  </body>
</html>