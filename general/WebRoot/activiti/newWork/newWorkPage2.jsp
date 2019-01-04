<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>常用工作流</title>
  </head>
  <body>
  <link rel="stylesheet" href="activiti/css/oastyle.css?v_=20170609" type="text/css">
  	<style type="text/css">
    	.commonProcess{border-collapse:collapse;border:0px solid #9f9f9f;width:100%;}
    	.commonProcess tr{border-bottom:1px solid gainsboro;line-height:25px; }
    	.commonProcess tr th{border-right:0px solid gainsboro; padding-left: 5px;}
    	.commonProcess a{text-decoration:none;}
    	.commonProcess a:hover{text-decoration:underline;}
        .commonProcess td {padding-left: 5px;}
    </style>
    <div class="easyui-panel" data-options="fit:true,border:false" title="常用工作流程">
		<table class="commonProcess">
			<tr class="commonProcess_head" style="background:url(image/list_hd_bg.png) repeat-x;">
				<th style="width:200px;">流程</th>
				<th style="width:450px;">上次建立的工作</th>
				<th style="border-right:none;width:120px;text-align:center;">操作</th>
			</tr>
			<c:forEach var="item" items="${list }">
			<tr>
				<td><b>${item.definitionname }</b></td>
				<td>
					<a href="javascript:;" class="oatitle" onclick="viewDetail('','','${item.instanceid }','${item.definitionkey }')"><%--<b>流程号：${item.id }</b> &nbsp; --%>${item.title }</a><br />
					<fmt:formatDate value="${item.applydate }" pattern="yyyy-MM-dd HH:mm:ss" />
				</td>
				<td style="text-align:center;">
					<a href="javascript:;" onclick="start('${item.definitionkey }')" class="easyui-linkbutton">新建向导</a>
				</td>
			</tr>
			</c:forEach>
		</table>
	</div>
	<script type="text/javascript">
    	$(function(){
    		$(".commonProcess tr:odd").not(".commonProcess_head").css("background","#F8F8F8");
    	});
    	function start(definitionkey){
    		$("#activiti-panel-newWorkPage").panel("refresh", "act/newWorkGuidePage.do?definitionkey="+ definitionkey);
    	}
    	function viewDetail(taskid, taskkey, instanceid, definitionkey){
			top.addOrUpdateTab("act/workViewPage.do?taskid="+ taskid+ "&taskkey="+ taskkey +"&instanceid="+ instanceid, "工作查看");
		}
    </script>
  </body>
</html>
