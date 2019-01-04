<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>自定义表单</title>
  </head>
  
  <body>
	<div class="easyui-layout" data-options="fit:true" style="width:600px;">
		<div data-options="region:'west',split:true" style="width:150px;">
			<div id="activiti-typeTree-formSelectPage" class="ztree"></div>
		</div>
		<div data-options="region:'center'" style="width: 440px;">
            <div class="easyui-panel" style="width: 420px;" data-options="fit:true">
                <table id="activiti-datagrid-formSelectPage" style="width: 400px;">
                    <tr><td></td></tr>
                    <tr><td></td></tr>
                    <tr><td></td></tr>
                    <tr><td></td></tr>
                    <tr><td></td></tr>
                </table>
            </div>
		</div>
	</div>
	<script type="text/javascript">
		var rightClickId = "";
		$(function(){
			//流程分类树
			var formTypeTreeSetting = {
				view: {
					dblClickExpand: false,
					showLine: true,
					selectedMulti: false,
					showIcon:true,
					expandSpeed: ($.browser.msie && parseInt($.browser.version)<=6)?"":"fast"
				},
				async: {
					enable: true,
					url: "act/getFormTypeTree.do",
					autoParam: ["id", "pid", "name", "key"]
				},
				data: {
					key:{
						title:"name"
					},
					simpleData: {
						enable:true,
						idKey: "id",
						pIdKey: "pid",
						rootPId: null
					}
				},
				callback: {
					//点击树状菜单更新页面按钮列表
					beforeClick: function(treeId, treeNode) {
						$("#activiti-datagrid-formSelectPage").datagrid({
							queryParams:{type:treeNode.key}
						}).datagrid('reload');
					}
				}
			};
			$.fn.zTree.init($("#activiti-typeTree-formSelectPage"), formTypeTreeSetting, null);
			$("#activiti-datagrid-formSelectPage").datagrid({
				columns:[[
						{field:'ck',checkbox:true},
						{field:"id", title:"编号", width:60},
						{field:"name", title:"表单名称", width:200},
						{field:"unkey", title:"表单标识", width:150}
					]],
				url:'act/getFormList.do',
				fit:true,
				fitColumns:true,
				border:false,
				checkOnSelect:true,
				selectOnCheck:true,
				rownumbers:true,
				pagination:true,
		 		idField:'id',
		 		singleSelect:true
			});
		});
	</script>
  </body>
</html>
