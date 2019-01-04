<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>菜单管理</title>
	<%@include file="/include.jsp" %>   
  </head>
  
  <body>
    <div class="easyui-panel" data-options="fit:true">  
        <div class="easyui-layout" data-options="fit:true">  
            <div title="菜单列表" data-options="region:'west',split:false,border:false" style="width:250px;">
            	<div class="easyui-layout" data-options="fit:true">
            		<div data-options="region:'north'" style="height: 40px;">
            			<div class="buttonBG">
		            		<a href="javaScript:void(0);" id="accesscontrol-add-addMenu" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'">新增</a>
		            		<a href="javaScript:void(0);" id="accesscontrol-edit-editMenu" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-edit'">修改</a>
		            		<a href="javaScript:void(0);" id="accesscontrol-close-closeMenu" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-close'">停用</a>
                            <security:authorize url="/accesscontrol/showOperateHelpPage.do">
                            <a href="javaScript:void(0);" id="accesscontrol-close-help" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-view'">帮助</a>
                            </security:authorize>
		            	</div>
            		</div>
            		<div data-options="region:'center'" style="height: 40px;">
            			<div id="accesscontrol-tree-menu" class="ztree"></div>
            		</div>
            	</div>
            </div>  
            <div title="" data-options="region:'center'">
				<div class="easyui-layout" fit="true">
					<div title="按钮列表" data-options="region:'center',split:false,border:false">
						<div id="accesscontrol-toolbar-operateList" class="buttonBG">
							<a href="javaScript:void(0);" id="accesscontrol-add-addOperate" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'">新增</a>
							<a href="javaScript:void(0);" id="accesscontrol-edit-editOperate" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-edit'">修改</a>
							<a href="javaScript:void(0);" id="accesscontrol-open-openOperate" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-open'">启用</a>
							<a href="javaScript:void(0);" id="accesscontrol-close-closeOperate" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-close'">停用</a>
							<a href="javaScript:void(0);" id="accesscontrol-delete-deleteOperate" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-delete'">删除</a>
						</div>
						<table id="accesscontrol-table-operateList"></table>
					</div>
					<div title="停用菜单列表" data-options="region:'south',split:false,border:false" style="height: 250px;">
						<div id="accesscontrol-toolbar-menuList" class="buttonBG" style="height:60px !important;">
							<a href="javaScript:void(0);" id="accesscontrol-edit-editStopMenu" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-edit'">修改</a>
							<a href="javaScript:void(0);" id="accesscontrol-open-openMenu" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-open'">启用</a>
							<a href="javaScript:void(0);" id="accesscontrol-delete-deleteMenu" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-delete'">删除</a>
							<div>
								<form action="" id="accesscontrol-toolbar-menuList-form" method="post" style="padding-top: 2px;">
									<table>
										<tr>
											<td>菜单名称:</td>
											<td><input type="text" id="accesscontrol-toolbar-menuList-form-operatename" name="operatename" style="width:100px" /></td>
											<td>地址:</td>
											<td><input type="text" id="accesscontrol-toolbar-menuList-form-url" name="url" style="width:100px" /></td>
											<td>
												<a href="javaScript:void(0);" id="accesscontrol-toolbar-menuList-form-query" class="button-qr" style="width:60px">查询</a>
												<a href="javaScript:void(0);" id="accesscontrol-toolbar-menuList-form-reset" class="button-qr" style="width:60px">重置</a>
											</td>
										</tr>
									</table>
								</form>
							</div>
						</div>
						<table id="accesscontrol-table-menuList"></table>
					</div>
				</div>
			</div>  
        </div>  
        <div id="accesscontrol-dialog-operate"></div>
    </div>
    <script type="text/javascript">
    	var zTree;
		var treeSetting = {
			view: {
				dblClickExpand: false,
				showLine: true,
				selectedMulti: false,
				showIcon:true,
				expandSpeed: ($.browser.msie && parseInt($.browser.version)<=6)?"":"fast"
			},
			async: {
				enable: true,
				url: "accesscontrol/showMenuTree.do",
				autoParam: ["id","pId", "name"]
			},
            check:{
                enable: true  //设置是否显示checkbox复选框
            },
			data: {
				key:{
					title:"urlStr"
				},
				simpleData: {
					enable:true,
					idKey: "id",
					pIdKey: "pId",
					rootPId: ""
				}
			},
			callback: {
				//点击树状菜单更新页面按钮列表
				beforeClick: function(treeId, treeNode) {
					var zTree = $.fn.zTree.getZTreeObj("accesscontrol-tree-menu");
					var url = "accesscontrol/showOperListByMenu.do?operateid="+treeNode.id;
					$("#accesscontrol-table-operateList").datagrid('options').url=url;
					$('#accesscontrol-table-operateList').datagrid('reload');
					return true;
				}
			}
		};
		$(function(){
			$.fn.zTree.init($("#accesscontrol-tree-menu"), treeSetting,null);
			//停用菜单列表
	    	$('#accesscontrol-table-menuList').datagrid({ 
	  	 		fit:true,
	  	 		method:'post',
	  	 		rownumbers:false,
	  	 		idField:'id',
	  	 		singleSelect:true,
	  	 		toolbar:"#accesscontrol-toolbar-menuList",
	  	 		url:'accesscontrol/showMenuCloseList.do',
			    columns:[[  
			    	{field:'operatename',title:'菜单名称',width:130},  
			        {field:'pid',title:'父菜单',width:100}, 
			        {field:'url',title:'链接地址',width:300},  
			        {field:'seq',title:'顺序',width:100},
			        {field:'image',title:'图标',width:100,
			        	formatter:function(val){
			        		if(val!=null&&val!=''){
			        			return '<img src="'+val+'"/>';
			        		}else{
			        			return '';
			        		}
			        	}
			        },
			        {field:'state',title:'状态',width:100,
			        	formatter:function(val){
			        		if(val=='1'){
			        			return "启用";
			        		}else{
			        			return "停用";
			        		}
			        	}
			        }
			    ]]
			});
            //查询
            $("#accesscontrol-toolbar-menuList-form-query").click(function(){
                //把form表单的name序列化成JSON对象
                var queryJSON = $("#accesscontrol-toolbar-menuList-form").serializeJSON();
                $("#accesscontrol-table-menuList").datagrid("load",queryJSON);
            });
            //重置
            $('#accesscontrol-toolbar-menuList-form-reset').click(function(){
                $("#accesscontrol-toolbar-menuList-form")[0].reset();
                var queryJSON = $("#accesscontrol-toolbar-menuList-form").serializeJSON();
                $("#accesscontrol-table-menuList").datagrid("load",queryJSON);
            });
			//页面元素列表
			$('#accesscontrol-table-operateList').datagrid({ 
	  	 		fit:true,
	  	 		method:'post',
	  	 		rownumbers:false,
	  	 		idField:'operateid',
	  	 		singleSelect:false,
                checkOnSelect:true,
                selectOnCheck:true,
	  	 		toolbar:"#accesscontrol-toolbar-operateList",
			    columns:[[
                    {field:'idok',checkbox:true,isShow:true},
			    	{field:'operatename',title:'按钮名称',width:130},  
			        {field:'pid',title:'所属菜单',width:100,
			        	formatter:function(val){
			        		var zTree = $.fn.zTree.getZTreeObj("accesscontrol-tree-menu");
							var treeNote = zTree.getSelectedNodes();
							if(treeNote[0]==null||treeNote[0]==""){
								return "";
							}else{
								var name = treeNote[0].name.split('[');
								return name[0];
							}
			        	}
			        },
			        {field:'url',title:'链接地址',width:300},  
			        {field:'state',title:'状态',width:100,
			        	formatter:function(val){
			        		if(val=='1'){
			        			return "启用";
			        		}else{
			        			return "停用";
			        		}
			        	}
			        }
			    ]]
			});
			//显示菜单添加页面
			$("#accesscontrol-add-addMenu").click(function(){
				var zTree = $.fn.zTree.getZTreeObj("accesscontrol-tree-menu");
				var treeNote = zTree.getSelectedNodes();
				if(treeNote==null||treeNote==""){
					$.messager.alert("提醒","请选择菜单！");
					return false;
				}else{
					var pname = encodeURIComponent(treeNote[0].name, "UTF-8");
					var url = "accesscontrol/showMenuAddPage.do?pid="+treeNote[0].id+"&pname="+pname;
			    	$('#accesscontrol-dialog-operate').dialog({  
					    title: '菜单新增',  
					    width: 450,  
					    height: 350,  
					    closed: false,  
					    cache: false,  
					    href: url,  
					    modal: true,
					    buttons:[
					    	{  
					    		id:'accesscontrol-save-addMenu',
		                    	text:'保存',  
			                    iconCls:'button-save',
			                    plain:true
			                }
					    ]
					});
				}
			});
			//显示页面按钮添加页面
			$("#accesscontrol-add-addOperate").click(function(){
				var zTree = $.fn.zTree.getZTreeObj("accesscontrol-tree-menu");
				var treeNote = zTree.getSelectedNodes();
				if(treeNote==null||treeNote==""){
					$.messager.alert("提醒","请选择子菜单！");
					return false;
				}else{
					//if(treeNote[0].isParent){
					//	$.messager.alert("提醒","请选择子菜单！");
					//	return false;
					//}
					var url = "accesscontrol/showOperAddPage.do?pid="+treeNote[0].id;
			    	$('#accesscontrol-dialog-operate').dialog({  
					    title: '按钮新增',  
					    width: 450,  
					    height: 350,  
					    closed: false,  
					    cache: false,  
					    href: url,  
					    modal: true,
					    buttons:[
					    	{  
					    		id:'accesscontrol-save-addMenu',
		                    	text:'保存',  
			                    iconCls:'button-save',
			                    plain:true
			                }
					    ]
					});
				}
			});
			//显示页面按钮修改页面
			$("#accesscontrol-edit-editMenu").click(function(){
				var zTree = $.fn.zTree.getZTreeObj("accesscontrol-tree-menu");
				var treeNote = zTree.getSelectedNodes();
				if(treeNote==null||treeNote==""){
					$.messager.alert("提醒","请选择子菜单！");
					return false;
				}else{
					var url = "accesscontrol/showMenuEditPage.do?operateid="+treeNote[0].id;
			    	$('#accesscontrol-dialog-operate').dialog({  
					    title: '菜单修改',  
					    width: 450,  
					    height: 350,  
					    closed: false,  
					    cache: false,  
					    href: url,  
					    modal: true,
					    buttons:[
					    	{  
					    		id:'accesscontrol-edit-editMenuSubmit',
		                    	text:'保存',  
			                    iconCls:'button-save',
			                    plain:true
			                }
					    ]
					});
				}
			});
			//停用菜单修改
			$("#accesscontrol-edit-editStopMenu").click(function(){
				var operate = $("#accesscontrol-table-menuList").datagrid('getSelected');
		    	if(null==operate){
		    		$.messager.alert("提醒","请选择页面按钮！");
		    		return false;
		    	}else{
		    		var url = "accesscontrol/showMenuEditPage.do?operateid="+operate.operateid;
			    	$('#accesscontrol-dialog-operate').dialog({  
					    title: '停用菜单修改',  
					    width: 450,  
					    height: 350,  
					    closed: false,  
					    cache: false,  
					    href: url,  
					    modal: true,
					     buttons:[
					    	{  
					    		id:'accesscontrol-edit-editMenuSubmit',
		                    	text:'保存',  
			                    iconCls:'button-save',
			                    plain:true
			                }
					    ]
					});
		    	}
			});
			//显示页面按钮修改页面
			$("#accesscontrol-edit-editOperate").click(function(){
				var operate = $("#accesscontrol-table-operateList").datagrid('getSelected');
		    	if(null==operate){
		    		$.messager.alert("提醒","请选择页面按钮！");
		    		return false;
		    	}else{
		    		var url = "accesscontrol/showOperateEditPage.do?operateid="+operate.operateid;
			    	$('#accesscontrol-dialog-operate').dialog({  
					    title: '页面按钮修改',  
					    width: 450,  
					    height: 350,  
					    closed: false,  
					    cache: false,  
					    href: url,  
					    modal: true,
					    buttons:[
					    	{  
					    		id:'accesscontrol-edit-editMenuSubmit',
		                    	text:'保存',  
			                    iconCls:'button-save',
			                    plain:true
			                }
					    ]
					});
		    	}
			});
			//删除菜单
			$("#accesscontrol-delete-deleteMenu").click(function(){
				var operate = $("#accesscontrol-table-menuList").datagrid('getSelected');
		    	if(null==operate){
		    		$.messager.alert("提醒","请选择子菜单！");
		    		return false;
		    	}else{
		    		$.messager.confirm("提醒", "是否删除该子菜单?", function(r){
						if (r){
							var url = "accesscontrol/deleteOperate.do?operateid="+operate.operateid;
							$.ajax({   
					            url :url,
					            type:'post',
					            dataType:'json',
					            success:function(json){
					            	if(json.flag==true){
					            		$.messager.alert("提醒","删除成功！");
					            		$('#accesscontrol-table-menuList').datagrid('reload');
					            	}else{
					            		$.messager.alert("提醒","删除失败！");
					            	}
					            }
					        });
						}
					});
		    	}
			});
			//关闭菜单
			$("#accesscontrol-close-closeMenu").click(function(){
				var zTree = $.fn.zTree.getZTreeObj("accesscontrol-tree-menu");
//				var treeNote = zTree.getSelectedNodes();
                var treeNote = zTree.getCheckedNodes(true);
				if(treeNote[0]==null||treeNote[0]==""){
					$.messager.alert("提醒","请选择子菜单！");
		    		return false;
				}else{
                    var operateids = "";
                    for(var i in treeNote){
                        var halfCheck = treeNote[i].getCheckStatus();
                        if(!halfCheck.half){
                            if(operateids==""){
                                operateids += treeNote[i].id;
                            }else{
                                operateids += "," +treeNote[i].id;
                            }
						}
                    }
		    		var url = "accesscontrol/closeOperate.do?operateid="+operateids;
		    		$.ajax({
			            url :url,
			            type:'post',
			            dataType:'json',
			            success:function(json){
			            	if(json.flag==true){
			            		$.messager.alert("提醒","停用成功！");
			            		$.fn.zTree.init($("#accesscontrol-tree-menu"), treeSetting,null);
			            		$('#accesscontrol-table-menuList').datagrid('reload');
			            	}else{
			            		$.messager.alert("提醒","停用失败！");
			            	}
			            }
			        });
		    	}
			});
			$("#accesscontrol-close-help").click(function(){
                var zTree = $.fn.zTree.getZTreeObj("accesscontrol-tree-menu");
                var treeNote = zTree.getSelectedNodes();
                if(treeNote[0].children!=null){
                    $.messager.alert("提醒","请选择子菜单！");
                    return false;
                }else{
                    var operateid = treeNote[0].id;
                    top.addOrUpdateTab('accesscontrol/showOperateHelpPage.do?id='+operateid,"帮助文档编辑");
                }
            });
			//启用菜单
			$("#accesscontrol-open-openMenu").click(function(){
				var operate = $("#accesscontrol-table-menuList").datagrid('getSelected');
		    	if(null==operate){
		    		$.messager.alert("提醒","请选择子菜单！");
		    		return false;
		    	}else{
		    		var url = "accesscontrol/openOperate.do?operateid="+operate.operateid;
		    		$.ajax({   
			            url :url,
			            type:'post',
			            dataType:'json',
			            success:function(json){
			            	if(json.flag==true){
			            		$.messager.alert("提醒","启用成功！");
			            		$('#accesscontrol-table-menuList').datagrid('reload');
			            		$.fn.zTree.init($("#accesscontrol-tree-menu"), treeSetting,null);
			            	}else{
			            		$.messager.alert("提醒","启用失败！");
			            	}
			            }
			        });
		    	}
			});
			//启用页面按钮
			$("#accesscontrol-open-openOperate").click(function(){
				var datarow = $("#accesscontrol-table-operateList").datagrid('getChecked');
		    	if(null==datarow || datarow.length==0){
		    		$.messager.alert("提醒","请选择页面按钮！");
		    		return false;
		    	}
		    	var ids=[];
		    	for(var i=0;i<datarow.length;i++){
		    	    if(datarow[i].operateid!=null){
		    	        ids.push(datarow[i].operateid);
					}
				}
                var url = "accesscontrol/openOperateMulti.do";
                $.ajax({
                    url :url,
                    type:'post',
                    dataType:'json',
                    data:{operateidarrs:ids.join(",")},
                    success:function(json){
                        if(json.flag==true){
                            $.messager.alert("提醒","启用成功！");
                            $('#accesscontrol-table-operateList').datagrid('reload');
                        }else{
                            $.messager.alert("提醒","启用失败！");
                        }
                    }
                });
			});
			//停用页面按钮
			$("#accesscontrol-close-closeOperate").click(function(){
				var operate = $("#accesscontrol-table-operateList").datagrid('getSelected');
		    	if(null==operate){
		    		$.messager.alert("提醒","请选择页面按钮！");
		    		return false;
		    	}else{
		    		var url = "accesscontrol/closeOperate.do?operateid="+operate.operateid;
		    		$.ajax({   
			            url :url,
			            type:'post',
			            dataType:'json',
			            success:function(json){
			            	if(json.flag==true){
			            		$.messager.alert("提醒","停用成功！");
			            		$('#accesscontrol-table-operateList').datagrid('reload');
			            	}else{
			            		$.messager.alert("提醒","停用失败！");
			            	}
			            }
			        });
		    	}
			});
			//删除页面按钮
			$("#accesscontrol-delete-deleteOperate").click(function(){
				var operate = $("#accesscontrol-table-operateList").datagrid('getSelected');
		    	if(null==operate){
		    		$.messager.alert("提醒","请选择页面按钮！");
		    		return false;
		    	}else{
		    		$.messager.confirm("提醒", "是否删除该页面按钮?", function(r){
						if (r){
							var url = "accesscontrol/deleteOperate.do?operateid="+operate.operateid;
				    		$.ajax({   
					            url :url,
					            type:'post',
					            dataType:'json',
					            success:function(json){
					            	if(json.flag==true){
					            		$.messager.alert("提醒","删除成功！");
					            		$('#accesscontrol-table-operateList').datagrid('reload');
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
