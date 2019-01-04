<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>我的工作-全部工作</title>
    <%@include file="/include.jsp" %>   
  </head>
  
  <body>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'west',split:true" style="width:200px;" title="工作流程">
			<div id="activiti-typeTree-myWorkPage11" class="ztree"></div>
		</div>
		<div data-options="region:'center'">
			<div class="easyui-panel" id="activiti-panel-myWorkPage11" data-options="fit:true,border:false,href:'act/workStatisticsPage.do'"></div>
		</div>
	</div>
	<div id="activiti-dialog-myWorkPage11"></div>
	<script type="text/javascript">
	
		$(function() {
	
			//流程分类树
			var formTypeTreeSetting = {
				view: {
					dblClickExpand:false,
					showLine: true,
					selectedMulti: false,
					showIcon:true,
					expandSpeed: ($.browser.msie && parseInt($.browser.version)<=6)?"":"fast"
				},
				async: {
					enable: true,
					url: "act/getDefinitionTree.do",
					autoParam: ["id", "pid", "name", "unkey", "isparent"]
				},
				data: {
					key:{
						title:"name"
					},
					simpleData: {
						enable:true,
						idKey: "id",
						pIdKey: "pid",
						rootPId: ""
					}
				},
				callback: {
					//点击树状菜单更新页面按钮列表
					beforeClick: function(treeId, treeNode) {
						var zTree = $.fn.zTree.getZTreeObj("activiti-typeTree-myWorkPage11");
						if(treeNode.isparent == "1"){
							zTree.expandNode(treeNode);
							return ;
						}
						$("#activiti-panel-myWorkPage11").panel("refresh", "act/workStatisticsPage.do?key="+ treeNode.unkey);
					}
				}
			};
			$.fn.zTree.init($("#activiti-typeTree-myWorkPage11"), formTypeTreeSetting, null);
	
		});
	
	</script>
  </body>
</html>
