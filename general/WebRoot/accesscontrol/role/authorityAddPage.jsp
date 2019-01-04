<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>角色添加</title>
	<%@include file="/include.jsp" %>  
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true">  
        <div  data-options="region:'north'" style="height: 50px;">
       	<form action="accesscontrol/addAuthority.do" method="post" id="accesscontrol-form-addAuthority">
           	<div class="pageContent">
			<p>
				<label>角色名称:</label>
				<input type="text" name="authority.authorityname" class="easyui-validatebox" validType="validRolename" required="true" style="width:200px;" maxlength="50"/>
			</p>
			<p>
				<label>角色描述:</label>
				<input type="text" name="authority.description" style="width:200px;"/>
			</p>
			</div>
			<input type="hidden" id="accesscontrol-addAuthority-operates" name="operates"/>
			<input type="hidden" id="accesscontrol-addAuthority-datarules" name="datarules"/>
			<input type="hidden" id="accesscontrol-addAuthority-authorityid" name="authority.authorityid" value="${authorityid}"/>
		</form>
	    </div>
	    <div data-options="region:'center'">
	    	<div class="easyui-layout" data-options="fit:true">
	    		<div title="菜单与按钮权限" data-options="region:'west'" style="width: 250px;">
	    			<div class="buttonBG">
	    				<a href="javaScript:void(0);" id="accesscontrol-button-expandTree" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'">展开</a>
	    				<a href="javaScript:void(0);" id="accesscontrol-button-collapseTree" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'">折叠</a>
	    			</div>
	    			<div id="accesscontrol-tree-addAuthorityMenu" class="ztree"></div>
	    		</div>
	    		<div data-options="region:'center'">
	    			<div class="easyui-layout" data-options="fit:true">
	    				<div title="字段权限" data-options="region:'center'">
	    					<div id="accessControl-addAuthority-columntoolbar">
	    						<a href="javaScript:void(0);" id="accesscontrol-addAuthority-addColumn" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'">添加</a>
	    						<a href="javaScript:void(0);" id="accesscontrol-addAuthority-deleteColumn" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-delete'">删除</a>
	    					</div>
	    					<table id="accesscontrol-addAuthority-addColumnList"></table>
	    				</div>
	    			</div>
	    		</div>
	    	</div>
	    </div>
	    <div data-options="region:'south'" style="height: 40px;">
	    	<div class="buttonDivR">
	    		<a href="javaScript:void(0);" id="accesscontrol-button-saveAuthority" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'">保存</a>
	    	</div>
	    </div>
	    <div id="accesscontrol-dialog-addAccessColumn"></div>
	 </div>
	 <script type="text/javascript">
		$.extend($.fn.validatebox.defaults.rules, {
			validRolename:{
				validator: function (value, param) {  
		    		var flag = false;
		    		if(param&&value==param[0]){
		    			flag = true;
		    		}else{
			        	$.ajax({   
				            url :'accesscontrol/checkAuthorityName.do?name='+value,
				            type:'post',
				            dataType:'json',
				            async: false,
				            success:function(json){
				            	flag =  json.flag;
				            }
				        });
		    		}
			        return flag;
		        },  
		        message:'角色名称已重复，请重新输入!'
			}
		});
	 
	 	var addAuthorityzTree;
		var addAuthoritytreeSetting = {
			check: {
				enable: true,
				chkboxType:{ "Y" : "ps", "N" : "s" }
			},
			view: {
				dblClickExpand: false,
				showLine: true,
				selectedMulti: true,
				showIcon:true,
				expandSpeed: ($.browser.msie && parseInt($.browser.version)<=6)?"":"fast"
			},
			async: {
				enable: true,
				url: "accesscontrol/showOperateTree.do",
				autoParam: ["id","pId", "name"]
			},
			data: {
				simpleData: {
					enable:true,
					idKey: "id",
					pIdKey: "pId",
					rootPId: ""
				}
			}
		};
		/**
		 * zTree树节点展开或者折叠
		 * @param e
		 * @return
		 */
		function expandNode(e) {
			var zTree = $.fn.zTree.getZTreeObj("accesscontrol-tree-addAuthorityMenu"),
			type = e.data.type,
			nodes = zTree.getSelectedNodes();
			if (type.indexOf("All")<0 && nodes.length == 0) {
				alert("请先选择一个父节点");
			}
			if (type == "expandAll") {
				zTree.expandAll(true);
			} else if (type == "collapseAll") {
				zTree.expandAll(false);
			} else {
				var callbackFlag = $("#callbackTrigger").attr("checked");
				for (var i=0, l=nodes.length; i<l; i++) {
					zTree.setting.view.fontCss = {};
					if (type == "expand") {
						zTree.expandNode(nodes[i], true, null, null, callbackFlag);
					} else if (type == "collapse") {
						zTree.expandNode(nodes[i], false, null, null, callbackFlag);
					} else if (type == "toggle") {
						zTree.expandNode(nodes[i], null, null, null, callbackFlag);
					} else if (type == "expandSon") {
						zTree.expandNode(nodes[i], true, true, null, callbackFlag);
					} else if (type == "collapseSon") {
						zTree.expandNode(nodes[i], false, true, null, callbackFlag);
					}
				}
			}
		}
		
		$('#accesscontrol-addAuthority-addColumnList').datagrid({ 
  	 		fit:true,
  	 		method:'post',
  	 		rownumbers:true,
  	 		singleSelect:true,
  	 		url:'accesscontrol/showAuthorityColumnList.do?authorityid=${authorityid}',
			toolbar:'#accessControl-addAuthority-columntoolbar',
		    columns:[[
		    			{field:'tabledescname',title:'表名',width:100},
				        {field:'collist',title:'可访问字段',width:600}
		    		]]
		});
		$(document).ready(function() {
			$.fn.zTree.init($("#accesscontrol-tree-addAuthorityMenu"), addAuthoritytreeSetting,null);
			//展开全部节点
			$("#accesscontrol-button-expandTree").bind("click", {type:"expandAll"}, expandNode);
			//折叠全部节点
			$("#accesscontrol-button-collapseTree").bind("click", {type:"collapseAll"}, expandNode);
		});
		$(function(){
			$("#accesscontrol-form-addAuthority").form({  
			    onSubmit: function(){  
			    	var flag = $(this).form('validate');
			    	if(flag==false){
			    		return false;
			    	}
			    },  
			    success:function(data){
			    	//转为json对象
			    	var json = $.parseJSON(data);
			        if(json.flag==true){
			        	$.messager.alert("提醒","添加成功");
			        	tabsWindowURL("/accesscontrol/showRolePage.do").$('#accesscontrol-table-authorityList').datagrid('clearSelections');
			        	tabsWindowURL("/accesscontrol/showRolePage.do").$("#accesscontrol-table-authorityList").datagrid('reload');
			        	top.closeTab("角色新增");
			        }else{
			        	$.messager.alert("提醒","添加失败");
			        }
			    }  
			}); 
			//保存提交
			$("#accesscontrol-button-saveAuthority").click(function(){
				$.messager.confirm("提醒", "是否添加角色?", function(r){
					if (r){
						//获取菜单按钮列表
						var treeObj = $.fn.zTree.getZTreeObj("accesscontrol-tree-addAuthorityMenu");
						var nodes = treeObj.getCheckedNodes(true);
						var operates = "";
						for(var i in nodes){
							if(i==0){
								 operates += nodes[i].id;
							}else{
								operates += "," +nodes[i].id;
							}
						   
						}
						$("#accesscontrol-addAuthority-operates").attr("value",operates);
				        $("#accesscontrol-form-addAuthority").submit();
					}
				});
			});
			$("#accesscontrol-addAuthority-addColumn").click(function(){
				var authorityid = $("#accesscontrol-addAuthority-authorityid").val();
				var url = "accesscontrol/showAccessColumnAddPage.do?authorityid="+authorityid+"&type=add";
				$('#accesscontrol-dialog-addAccessColumn').window({  
				    title: '字段权限添加',  
				    width: 1000,  
				    height: 500,  
				    closed: true,  
				    cache: false,  
				    modal: true
				});
				$("#accesscontrol-dialog-addAccessColumn").window("open");
				$("#accesscontrol-dialog-addAccessColumn").window("refresh",url);
			});
			//删除字段权限
			$("#accesscontrol-addAuthority-deleteColumn").click(function(){
				var column = $("#accesscontrol-addAuthority-addColumnList").datagrid('getSelected');
				if(column==null){
					$.messager.alert("提醒","请选择字段权限！");
		    		return false;
				}else{
					$.messager.confirm("提醒", "是否删除字段权限?", function(r){
						if (r){
							var url = "accesscontrol/deleteAccessColumn.do?authorityid=${authorityid}&columnid="+column.columnid;
							$.ajax({   
					            url :url,
					            type:'post',
					            async:false,
					            success:function(data){
					            	var json = $.parseJSON(data)
					            	if(json.flag==true){
					            		$.messager.alert("提醒","删除成功！");
					            		$('#accesscontrol-addAuthority-addColumnList').datagrid('reload');
					            	}else{
					            		$.messager.alert("提醒","删除失败！");
					            	}
					            }
					        });
						}
					});
				}
			});
		});
	 </script>
  </body>
</html>
