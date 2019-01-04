<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>自定义表单</title>
    <%@include file="/include.jsp" %>   
  </head>
  
  <body>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'west',split:true" style="width:200px;" title="工作流程">
			<div id="activiti-typeTree-newWorkPage" class="ztree"></div>
		</div>
		<div data-options="region:'center'">
			<div class="easyui-panel" id="activiti-panel-newWorkPage" data-options="fit:true,border:false,href:'act/newWorkPage2.do'"></div>
		</div>
	</div>
	<div id="activiti-dialog-newWorkPage"></div>
	<script type="text/javascript">
		var rightClickId = "";
		$(function(){
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
						var zTree = $.fn.zTree.getZTreeObj("activiti-typeTree-newWorkPage");
						if(treeNode.isparent == "1"){
							zTree.expandNode(treeNode);
							return ;
						}
						$("#activiti-panel-newWorkPage").panel("refresh", "act/newWorkGuidePage.do?definitionkey="+ treeNode.unkey);
					}
				}
			};
			$.fn.zTree.init($("#activiti-typeTree-newWorkPage"), formTypeTreeSetting, null);
			$("#activiti-dialog-newWorkPage").dialog({
				width:380,
				height:220,
				closed:true,
				cache:false,
				modal:true,
				buttons:[
					{
						text:"确定",
						handler:function(){
							$("#activiti-form-formTypeAddPage").form({
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
							  		      	$("#activiti-dialog-newWorkPage").dialog('close', true);
							  		      	var treeObj = $.fn.zTree.getZTreeObj("activiti-typeTree-newWorkPage");
											var node = treeObj.getNodeByParam("id", json.node.pid, null);
				  		      				treeObj.addNodes(node, json.node); //增加子节点
			  		      				}
			  		      				else if(json.type == "edit"){
			  		      					$("#activiti-dialog-newWorkPage").dialog('close', true);
			  		      					var treeObj = $.fn.zTree.getZTreeObj("activiti-typeTree-newWorkPage");
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
							$("#activiti-form-formTypeAddPage").submit();
						}
					},
					{
						text:"取消",
						handler:function(){
							$("#activiti-dialog-newWorkPage").dialog('close', true);
						}
					}
				]
			});
		});
		function addFormType(pid){
			rightClickId = pid;
			$("#activiti-dialog-newWorkPage").dialog({
				title:'增加分类',
				href:'act/formTypeAddPage.do?pid='+ pid
			}).dialog('open');
		}
		function editFormType(id){
			$("#activiti-dialog-newWorkPage").dialog({
				title:'修改分类',
				href:'act/formTypeEditPage.do?id='+ id
			}).dialog('open');
		}
		function deleteFormType(id){
			$.messager.confirm("提醒", "确定删除该表单分类吗？", function(r){
				if(r){
					$.ajax({
						url:'act/deleteFormType.do',
						type:'post',
						dataType:'json',
						data:'id='+ id,
						success:function(json){
							if(json.flag == true){
								$.messager.alert("提醒", "删除成功");
								var treeObj = $.fn.zTree.getZTreeObj("activiti-typeTree-newWorkPage");
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
					$("#activiti-dialog-newWorkPage").dialog('close', true);
				}
			});
		}
	</script>
  </body>
</html>
