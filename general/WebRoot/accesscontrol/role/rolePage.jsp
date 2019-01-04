<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>角色管理</title>
	<%@include file="/include.jsp" %> 
  </head>
  
  <body>
     <div class="easyui-panel" data-options="fit:true" style="padding:2px;">
      	<div class="easyui-layout" data-options="fit:true">  
            <div title="角色列表" data-options="region:'west',split:false,border:false" style="width:350px;">
            	<div id="accesscontrol-toolbar-authorityList" class="buttonBG">
            		<a href="javaScript:void(0);" id="accesscontrol-add-addAuthority" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'">新增</a>
            		<a href="javaScript:void(0);" id="accesscontrol-button-editAuthority" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-edit'">修改</a>
            		<a href="javaScript:void(0);" id="accesscontrol-button-openAuthority" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-open'">启用</a>
            		<a href="javaScript:void(0);" id="accesscontrol-button-closeAuthority" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-close'">停用</a>
            		<a href="javaScript:void(0);" id="accesscontrol-button-deleteAuthority" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-delete'">删除</a>
            	</div>
		     	<table id="accesscontrol-table-authorityList"></table>
		    </div>
		    <div title="角色详情" data-options="region:'center',split:false,border:false">
		    	<div id="accesscontrol-div-authorityInfo" class="easyui-panel">
		    	</div>
			</div>
		  </div>
		  <div id="accesscontrol-dialog-roleDeploy"></div>
		  <div id="accesscontrol-dialog-role"></div>
		  <div id="accesscontrol-dialog-addRole"></div>
		  <div id="accesscontrol-dialog-editAuthority"></div>
     </div>
     <script type="text/javascript">
		$(function(){
			//加载权限列表
			$('#accesscontrol-table-authorityList').datagrid({ 
				columns:[[  
			        {field:'authorityname',title:'角色名称',width:200}, 
			        {field:'state',title:'状态',width:50,
			        	formatter:function(val){
			        		if(val=='1'){
			        			return "启用";
			        		}else{
			        			return "停用";
			        		}
			        	}
			        }
			    ]],
	  	 		fit:true,
	  	 		method:'post',
	  	 		rownumbers:true,
	  	 		idField:'authorityid',
	  	 		singleSelect:true,
	  	 		url:'accesscontrol/showAuthorityList.do',
	  	 		toolbar:"#accesscontrol-toolbar-authorityList",
			    onClickRow:function(rowIndex, rowData){
			    	var url = "accesscontrol/showAuthorityEditPage.do?authorityid="+rowData.authorityid+"&type=view";
					$("#accesscontrol-div-authorityInfo").panel({  
					    href:url,
					    cache:false,
					    maximized:true
					});
			    }
			});
			//添加新的权限
			$("#accesscontrol-add-addAuthority").click(function(){
				var url = "accesscontrol/showAuthorityAddPage.do";
				top.addTab("accesscontrol/showAuthorityAddPage.do","角色新增");
				
			});
			//权限停用
			$("#accesscontrol-button-closeAuthority").click(function(){
				var authority = $("#accesscontrol-table-authorityList").datagrid('getSelected');
				if(authority==null){
					$.messager.alert("提醒","请选择权限！");
		    		return false;
				}else{
					$.messager.confirm("提醒", "是否停用"+authority.authorityname+"权限?", function(r){
						if (r){
							var url = "accesscontrol/closeAuthority.do?authorityid="+authority.authorityid;
							$.ajax({   
					            url :url,
					            type:'post',
					            dataType:'json',
					            success:function(json){
					            	if(json.flag==true){
					            		$.messager.alert("提醒","停用成功！");
					            		$('#accesscontrol-table-authorityList').datagrid('reload');
					            	}else{
					            		$.messager.alert("提醒","停用失败！");
					            	}
					            }
					        });
				        }
					});
				}
			});
			//权限启用
			$("#accesscontrol-button-openAuthority").click(function(){
				var authority = $("#accesscontrol-table-authorityList").datagrid('getSelected');
				if(authority==null){
					$.messager.alert("提醒","请选择权限！");
		    		return false;
				}else{
					$.messager.confirm("提醒", "是否启用"+authority.authorityname+"权限?", function(r){
						if (r){
							var url = "accesscontrol/openAuthority.do?authorityid="+authority.authorityid;
							$.ajax({   
					            url :url,
					            type:'post',
					            dataType:'json',
					            success:function(json){
					            	if(json.flag==true){
					            		$.messager.alert("提醒","启用成功！");
					            		$('#accesscontrol-table-authorityList').datagrid('reload');
					            	}else{
					            		$.messager.alert("提醒","启用失败！");
					            	}
					            }
					        });
				        }
					});
				}
			});
			//删除权限
			$("#accesscontrol-button-deleteAuthority").click(function(){
				var authority = $("#accesscontrol-table-authorityList").datagrid('getSelected');
				if(authority==null){
					$.messager.alert("提醒","请选择权限！");
		    		return false;
				}else{
					$.messager.confirm("提醒", "是否删除"+authority.authorityname+"权限?", function(r){
						if (r){
							var url = "accesscontrol/deleteAuthority.do?authorityid="+authority.authorityid;
							$.ajax({   
					            url :url,
					            type:'post',
					            dataType:'json',
					            success:function(json){
                                    if(json.isrecommend ==  true){
                                        $.messager.alert("提醒","该角色被引用，不允许删除");
                                    }else if(json.flag == true ){
					            		$.messager.alert("提醒","删除成功！");
					            		$('#accesscontrol-table-authorityList').datagrid('reload');
					            	}else{
					            		$.messager.alert("提醒","删除失败！");
					            	}
					            }
					        });
				        }
					});
				}
			});
			//修改权限
			$("#accesscontrol-button-editAuthority").click(function(){
				var authority = $("#accesscontrol-table-authorityList").datagrid('getSelected');
				if(authority==null){
					$.messager.alert("提醒","请选择权限！");
		    		return false;
				}else{
					var url = "accesscontrol/showAuthorityEditPage.do?authorityid="+authority.authorityid;
					top.addTab(url,"角色修改");
				}
			});
		});
     </script>
  </body>
</html>
