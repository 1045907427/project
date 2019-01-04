<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>角色添加</title>
	<%@include file="/include.jsp" %>   
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true">  
        <div  data-options="region:'north'" style="height: 55px;">
       	<form action="accesscontrol/editAuthority.do" method="post" id="accesscontrol-form-editAuthority${type}">
           	<div class="pageContent">
			<p>
				<label>角色名称:</label>
				<input type="text" name="authority.authorityname" class="easyui-validatebox" validType="validRolename" required="true" style="width:200px;" value="${authority.authorityname }"/>
				<input id="accesscontrol-authority_oldrolename" type="hidden" value="${authority.authorityname }"/>
			</p>
			<p>
				<label>角色描述:</label>
				<input type="text" name="authority.description" style="width:200px;" value="${authority.description }"/>
			</p>
			</div>
			<input type="hidden" name="authority.authorityid" value="${authority.authorityid}"/>
			<input type="hidden" id="accesscontrol-editAuthority-operates${type}" name="operates"/>
			<input type="hidden" id="accesscontrol-editAuthority-datarules${type}" name="datarules"/>
		</form>
	    </div>
	    <div data-options="region:'center'">
	    	<div class="easyui-layout" data-options="fit:true">
	    		<div title="菜单与按钮权限" data-options="region:'west'" style="width: 250px;">
	    			<div class="buttonBG">
	    				<a href="javaScript:void(0);" id="accesscontrol-editTree-expandTree${type}" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'">展开</a>
	    				<a href="javaScript:void(0);" id="accesscontrol-editTree-collapseTree${type}" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'">折叠</a>
	    			</div>
	    			<div id="accesscontrol-tree-editAuthorityMenu${type}" class="ztree"></div>
	    		</div>
	    		<div data-options="region:'center'">
	    			<div class="easyui-layout" data-options="fit:true">
	    				<div title="字段权限" data-options="region:'center'">
	    				<c:if test="${type!='view'}">
	    					<div class="buttonBG" id="accessControl-column-toolbar${type}">
			    				<a href="javaScript:void(0);" id="accesscontrol-add-addColumn${type}" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'">添加</a>
			    				<a href="javaScript:void(0);" id="accesscontrol-delete-column${type}" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-delete'">删除</a>

			    			</div>
			    		</c:if>
			    			<table id="accesscontrol-table-columnAccess${type}"></table>
	    				</div>
	    			</div>
	    		</div>
	    	</div>
	    </div>
	    <c:if test="${type!='view'}">
	    <div data-options="region:'south'" style="height: 40px;">
	    	<div class="buttonDivR">
	    		<a href="javaScript:void(0);" id="accesscontrol-button-saveEditAuthority${type}" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'">保存</a>
	    	</div>
	    </div>
	    </c:if>
	    <div id="accesscontrol-window-addColumn${type}"></div>
	 </div>
	 <script type="text/javascript">
	 	$.extend($.fn.validatebox.defaults.rules, {
			validRolename:{
				validator: function (value, param) {  
		    		var flag = false;
		    		if(param&&value==param[0]){
		    			flag = true;
		    		}else{
		    			var oldname = $("#accesscontrol-authority_oldrolename").val();
						if(oldname!=value){
							$.ajax({   
					            url :'accesscontrol/checkAuthorityName.do?name='+value,
					            type:'post',
					            dataType:'json',
					            async: false,
					            success:function(json){
					            	flag =  json.flag;
					            }
					        });
						}else{
							return true;
						}
		    		}
			        return flag;
		        },  
		        message:'角色名称已重复，请重新输入!'
			}
		});
	 	var alias = "${authority.alias }";
		var editAuthoritytreeSetting${type} = {
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
				url: "accesscontrol/showOperateCheckedTree.do?authorityid=${authority.authorityid}&view=${type}",
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
		$(document).ready(function() {
			$.fn.zTree.init($("#accesscontrol-tree-editAuthorityMenu${type}"), editAuthoritytreeSetting${type},null);
			//展开全部节点
			$("#accesscontrol-editTree-expandTree${type}").bind("click", {type:"expandAll"}, expandNode);
			//折叠全部节点
			$("#accesscontrol-editTree-collapseTree${type}").bind("click", {type:"collapseAll"}, expandNode);
		});
		/**
         * zTree树节点展开或者折叠
         * @param e
         * @return
         */
        function expandNode(e) {
            var zTree = $.fn.zTree.getZTreeObj("accesscontrol-tree-editAuthorityMenu${type}");
            var type = e.data.type;
            var nodes = zTree.getSelectedNodes();
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
		$("#accesscontrol-table-columnAccess${type}").datagrid({ 
  	 		fit:true,
  	 		method:'post',
  	 		rownumbers:true,
  	 		singleSelect:true,
  	 		url:'accesscontrol/showAuthorityColumnList.do?authorityid=${authority.authorityid}',
			toolbar:'#accessControl-column-toolbar${type}',
		    columns:[[{field:'tabledescname',title:'表名',width:100},
				      {field:'collistname',title:'可访问字段',width:300},
				      {field:'editcollistname',title:'可编辑字段',width:300}
		    		]]
		});
		$(function(){
			//表单提交
			$("#accesscontrol-form-editAuthority${type}").form({  
			    onSubmit: function(){  
			    	var flag = $(this).form('validate');
			    	if(flag==false){
			    		return false;
			    	}
                    loading("提交中..");
			    },  
			    success:function(data){
                    loaded();
			    	//转为json对象
			    	var json = $.parseJSON(data);
			        if(json.flag==true){
			        	$.messager.alert("提醒","修改成功");
			        	tabsWindowURL("/accesscontrol/showRolePage.do").$('#accesscontrol-table-authorityList').datagrid('clearSelections');
			        	tabsWindowURL("/accesscontrol/showRolePage.do").$("#accesscontrol-table-authorityList").datagrid('reload');
			        	
			        	var url = "accesscontrol/showAuthorityEditPage.do?authorityid=${authority.authorityid}&type=view";
			        	tabsWindowURL("/accesscontrol/showRolePage.do").$("#accesscontrol-div-authorityInfo").panel({  
						    href:url,
						    cache:false,
						    maximized:true
						});
			        	top.closeTab("角色修改");
			        	
			        }else{
			        	$.messager.alert("提醒","修改失败");
			        }
			    }  
			}); 
			//保存提交
			$("#accesscontrol-button-saveEditAuthority${type}").click(function(){
				$.messager.confirm("提醒", "是否修改角色?", function(r){
					if (r){
						//获取菜单按钮列表
						var treeObj = $.fn.zTree.getZTreeObj("accesscontrol-tree-editAuthorityMenu${type}");
						var nodes = treeObj.getCheckedNodes(true);
						var operates = "";
						for(var i in nodes){
							if(i==0){
								 operates += nodes[i].id;
							}else{
								operates += "," +nodes[i].id;
							}
						   
						}
						$("#accesscontrol-editAuthority-operates${type}").attr("value",operates);
				        $("#accesscontrol-form-editAuthority${type}").submit();
					}
				});
			});
			//显示字段权限添加页面
			$("#accesscontrol-add-addColumn${type}").click(function(){
				var url = "accesscontrol/showAccessColumnAddPage.do?authorityid=${authority.authorityid}&type=edit";
				$('#accesscontrol-window-addColumn${type}').window({  
				    title: '字段权限添加',  
				    width: 1000,  
				    height: 500,  
				    closed: true,  
				    cache: false,  
				    modal: true
				});
				$("#accesscontrol-window-addColumn${type}").window("open");
				$("#accesscontrol-window-addColumn${type}").window("refresh",url);
			});
			//字段权限删除
			$("#accesscontrol-delete-column${type}").click(function(){
				var column = $("#accesscontrol-table-columnAccess${type}").datagrid('getSelected');
				if(column==null){
					$.messager.alert("提醒","请选择字段权限！");
		    		return false;
				}else{
					$.messager.confirm("提醒", "是否删除字段权限?", function(r){
						if (r){
							var url = "accesscontrol/deleteAccessColumn.do?authorityid=${authority.authorityid}&columnid="+column.columnid;
							$.ajax({   
					            url :url,
					            type:'post',
					            async:false,
					            success:function(data){
					            	var json = $.parseJSON(data)
					            	if(json.flag==true){
					            		$.messager.alert("提醒","删除成功！");
					            		$('#accesscontrol-table-columnAccess').datagrid('reload');
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
