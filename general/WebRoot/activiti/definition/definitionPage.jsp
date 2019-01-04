<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>流程定义管理</title>
    <%@include file="/include.jsp" %>
	  <style type="text/css">
		  tr[sign] {
			  background: #fff9be;
		  }
		  tr[sign]:hover {
			  background: #fff9af;
			  border: 1px solid #77a8cb;
		  }
	  </style>
  </head>
  
  <body>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'west',split:true" style="width:200px;" title="流程分类管理">
			<div id="activiti-typeTree-definitionPage" class="ztree"></div>
		</div>
		<div data-options="region:'center'" title="流程定义管理">
			<div class="easyui-panel" id="activiti-panel-definitionPage" data-options="fit:true,border:false,href:'act/definitionPage2.do'"></div>
		</div>
	</div>
	<div id="activiti-dialog-definitionPage"></div>
	<div class="easyui-menu" id="menu">
		<div data-options="iconCls:'button-add'">增加分类</div>
	</div>
	<div class="easyui-menu" id="menu2">
        <div data-options="iconCls:'button-add'">增加分类</div>
        <div data-options="iconCls:'button-edit'">修改分类</div>
        <div data-options="iconCls:'button-delete'">删除分类</div>
	</div>
	<script type="text/javascript">
		var rightClickId = "";
		$(function(){
			//流程分类树
			var definitionTypeTreeSetting = {
				view: {
					dblClickExpand: false,
					showLine: true,
					selectedMulti: false,
					showIcon:true,
					expandSpeed: ($.browser.msie && parseInt($.browser.version)<=6)?"":"fast"
				},
				async: {
					enable: true,
					url: "act/getDefinitionTypeTree.do",
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
						$("#activiti-panel-definitionPage").panel("refresh", "act/definitionPage2.do?type="+ treeNode.key);
					},
					onRightClick: function(event, treeId, treeNode){
						if(treeNode.id == ""){
							$("#menu").menu('show',{
								left:event.pageX,
								top:event.pageY
							});
							var item = $("#menu").menu('findItem', "增加分类");
							$(item.target).unbind('click').click(function(){
								addDefinitionType(treeNode.id);
								$("#menu").menu('hide');
							});
						}
						else{
							$("#menu2").menu('show',{
								left:event.pageX,
								top:event.pageY
							});
							var item = $("#menu2").menu('findItem', "增加分类");
							$(item.target).unbind('click').click(function(){
								addDefinitionType(treeNode.id);
								$("#menu2").menu('hide');
							});
							item = $("#menu2").menu('findItem', "修改分类");
							$(item.target).unbind('click').click(function(){
								editDefinitionType(treeNode.id);
								$("#menu2").menu('hide');
							});item = $("#menu2").menu('findItem', "删除分类");
							$(item.target).unbind('click').click(function(){
								deleteDefinitionType(treeNode.id);
								$("#menu2").menu('hide');
							});
						}
					}
				}
			};
			$.fn.zTree.init($("#activiti-typeTree-definitionPage"), definitionTypeTreeSetting, null);
			$("#activiti-dialog-definitionPage").dialog({
				width:350,
				height:220,
				closed:true,
				cache:false,
				modal:true,
				buttons:[
					{
						text:"确定",
						handler:function(){
							$("#activiti-form-definitionTypeAddPage").form({
							    onSubmit: function(){  
							    	var flag = $(this).form('validate');
						  		   	if(flag==false){
						  		   		return false;
						  		   	}  
						  		  	loading("提交中..");
						  		},  
						  		success:function(data){
						  		  	loaded();
						  		  	var json = $.parseJSON(data);
						  		    if(json.flag==true){
						  		      	$.messager.alert("提醒","保存成功");
						  		      	if(json.type == "add"){
							  		      	$("#activiti-dialog-definitionPage").dialog('close', true);
							  		      	var treeObj = $.fn.zTree.getZTreeObj("activiti-typeTree-definitionPage");
											var node = treeObj.getNodeByParam("id", json.node.pid, null);
				  		      				treeObj.addNodes(node, json.node); //增加子节点
			  		      				}
			  		      				else if(json.type == "edit"){
			  		      					$("#activiti-dialog-definitionPage").dialog('close', true);
			  		      					var treeObj = $.fn.zTree.getZTreeObj("activiti-typeTree-definitionPage");
											var node = treeObj.getNodeByParam("id", json.id, null);
											node.name = json.name;
				  		      				treeObj.updateNode(node); //更新子节点
			  		      				}
						  		    }
						  		    else{
						  		       	$.messager.alert("提醒","保存失败");
						  		    }
						  		}
					  		});
							$("#activiti-form-definitionTypeAddPage").submit();
						}
					},
					{
						text:"取消",
						handler:function(){
							$("#activiti-dialog-definitionPage").dialog('close', true);
						}
					}
				]
			});
		});
		function addDefinitionType(pid){
			rightClickId = pid;
			$("#activiti-dialog-definitionPage").dialog({
				title:'增加分类',
				href:'act/definitionTypeAddPage.do?pid='+ pid
			}).dialog('open');
		}
		function editDefinitionType(id){
			$("#activiti-dialog-definitionPage").dialog({
				title:'修改分类',
				href:'act/definitionTypeEditPage.do?id='+ id
			}).dialog('open');
		}
		function deleteDefinitionType(id){
			$.messager.confirm("提醒", "确定删除该流程分类吗？", function(r){
				if(r){
					$.ajax({
						url:'act/deleteDefinitionType.do',
						type:'post',
						dataType:'json',
						data:'id='+ id,
						success:function(json){
							if(json.flag == true){
								$.messager.alert("提醒", "删除成功");
								var treeObj = $.fn.zTree.getZTreeObj("activiti-typeTree-definitionPage");
								var node = treeObj.getNodeByParam("id", id, null);
								treeObj.removeNode(node);
							}
							else{
								$.messager.alert("提醒", "删除失败");
							}
						}
					});
				}
				else{
					$("#activiti-dialog-definitionPage").dialog('close', true);
				}
			});
		}
	</script>
  </body>
</html>
