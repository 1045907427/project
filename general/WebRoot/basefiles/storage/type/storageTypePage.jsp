<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>仓库类型</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
  	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'north',split:false,border:false" style="height: 30px;overflow: hidden;">
   			<div id="storageType-button" class="buttonBG"></div>
    	</div>
    	<div title="仓库类型列表" data-options="region:'west',split:true" style="width:300px;">
            <table id="storageType-table-list"></table>
    	</div>
    	<div data-options="region:'center',split:true">
    		<div id="storageType-panel"></div>
   		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			var storageTypeCol=$("#storageType-table-list").createGridColumnLoad({
	     	name:'base_storage_type',
	     	frozenCol:[[]],
	     	commonCol:[[
	     				{field:'id',title:'编码',width:50,sortable:true},
	     				{field:'name',title:'名称',width:150,sortable:true},
	     				{field:'state',title:'状态',width:50,sortable:true,
	     					formatter:function(val){
				        		if(val=="0"){
				        			return "禁用";
				        		}else if(val=="1"){
				        			return "启用";
				        		}else if(val=="2"){
				        			return "保存";
				        		}else if(val=="3"){
				        			return "暂存";
				        		}
				        	}
	     				},
	     				{field:'remark',title:'备注',width:100,hidden:true,sortable:true},
						{field:'addusername',title:'建档人',width:60,hidden:true,sortable:true},
						{field:'adddeptname',title:'建档部门',width:80,hidden:true,sortable:true},
						{field:'addtime',title:'建档时间',width:115,hidden:true,sortable:true},
						{field:'modifyusername',title:'最后修改人',width:60,hidden:true,sortable:true},
						{field:'modifytime',title:'最后修改时间',width:115,hidden:true,sortable:true},
						{field:'openusername',title:'启用人',width:60,hidden:true,sortable:true},
						{field:'opentime',title:'启用时间',width:115,hidden:true,sortable:true},
						{field:'closeusername',title:'禁用人',width:60,hidden:true,sortable:true},
						{field:'closetime',title:'禁用时间',width:115,hidden:true,sortable:true}
		     	]]
	     });
	    $('#storageType-table-list').datagrid({
  			authority:storageTypeCol,
  	 		frozenColumns:storageTypeCol.frozen,
			columns:storageTypeCol.common,
		    fit:true, 
			method:'post',
			rownumbers:true,
			pagination:true,
			idField:'id',
			singleSelect:true,
			url:'basefiles/showStorageTypeList.do',
			onLoadSuccess:function(){
		    	var p = $('#storageType-table-list').datagrid('getPager');  
			    $(p).pagination({  
			        beforePageText: '',//页数文本框前显示的汉字  
			        afterPageText: '',  
			        displayMsg: ''
			    });
	    	},
	    	onClickRow:function(rowIndex, rowData){
	    		$("#storageType-panel").panel({  
					fit:true, 
					title: '仓库类型详情',
					cache: false,
					closed:true,
					href : "basefiles/showStorageTypeInfo.do?id="+rowData.id
				});
				$("#storageType-panel").panel("open");
	    	}
		}).datagrid("columnMoving");
			$("#storageType-button").buttonWidget({
				//初始默认按钮 根据type对应按钮事件
				initButton:[
					{},
					<security:authorize url="/basefiles/showStorageTypeAddPage.do">
						{
							type:'button-add',
							handler:function(){
								$("#storageType-panel").panel({  
									fit:true, 
									title: '仓库类型新增',
									cache: false,
									closed:true,
									href : "basefiles/showStorageTypeAddPage.do"
								});
								$("#storageType-panel").panel("open");
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/showStorageTypeEditPage.do">
			 			{
				 			type:'button-edit',
				 			handler:function(){
				 				var storageType=$("#storageType-table-list").datagrid('getSelected');
				 				$.ajax({   
						            url :'system/lock/isDoLockData.do',
						            type:'post',
						            data:{id:storageType.id,tname:'t_base_storage_type'},
						            dataType:'json',
						            async: false,
						            success:function(json){
						            	flag = json.flag
						            }
						        });
				 				if(!flag){
				 					$.messager.alert("警告","该数据正在被其他人操作，暂不能修改！");
				 					return false;
				 				}
				 				$("#storageType-panel").panel({  
									fit:true, 
									title: '仓库类型修改',
									cache: false,
									closed:true,
									href : "basefiles/showStorageTypeEditPage.do?id="+storageType.id
								});
								$("#storageType-panel").panel("open");
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/addStorageTypeHold.do">
			 			{
			 				type:'button-hold',
			 				handler:function(){
				 				var type = $("#storageType-button").buttonWidget("getOperType");
				 				if(type=="add"){
				 					//暂存
				 					$("#storageType-form-add").attr("action", "basefiles/addStorageTypeHold.do");
				 					$("#storageType-form-add").submit();
				 				}else if(type=="edit"){
				 					//暂存
				 					$("#storageType-form-edit").attr("action", "basefiles/editStorageTypeHold.do");
				 					$("#storageType-form-edit").submit();
				 				}
				 			}
				 		},
			 		</security:authorize>
					<security:authorize url="/basefiles/addStorageTypeSave.do">
			 			{
				 			type:'button-save',
				 			handler:function(){
				 				var type = $("#storageType-button").buttonWidget("getOperType");
				 				if(type=="add"){
				 					//保存
				 					$("#storageType-form-add").attr("action", "basefiles/addStorageTypeSave.do");
				 					$("#storageType-form-add").submit();
				 				}else if(type=="edit"){
				 					//暂存
				 					$("#storageType-form-edit").attr("action", "basefiles/editStorageTypeSave.do");
				 					$("#storageType-form-edit").submit();
				 				}
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/deleteStorageType.do">
			 			{
				 			type:'button-delete',
				 			handler:function(){
				 				var storageType=$("#storageType-table-list").datagrid('getSelected');
				 				var url = 'basefiles/deleteStorageType.do?id='+storageType.id;
				 				var flag = true;
				 				$.ajax({   
						            url :'system/lock/isLockData.do',
						            type:'post',
						            data:{id:storageType.id,tname:'t_base_storage_type'},
						            dataType:'json',
						            async: false,
						            success:function(json){
						            	flag = json.flag
						            }
						        });
				 				if(flag){
				 					$.messager.alert("警告","该数据正在被其他人操作，暂不能修改！");
				 					return false;
				 				}
				 				$.messager.confirm("提醒", "是否删除该仓库类型?", function(r){
									if (r){
										loading("删除中..");
										$.ajax({   
								            url :url,
								            type:'post',
								            dataType:'json',
								            success:function(json){
								            	loaded();
								            	if(json.flag==true){
								            		$.messager.alert("提醒","删除成功！");
								            		//按钮点击后 控制按钮状态显示
				 									$("#storageType-button").buttonWidget("initButtonType","list");
								            		$("#storageType-panel").panel("close");
								            		$('#storageType-table-list').datagrid('clearSelections');
								            		$('#storageType-table-list').datagrid('reload');
								            	}else{
								            		if(json.delFlag==false){
								            			$.messager.alert("警告","该数据已被引用，不能删除！");
								            		}else{
								            			$.messager.alert("警告","删除失败！");
								            		}
								            	}
								            }
								        });
									}
								});
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/openStorageType.do">
			 			{
				 			type:'button-open',
				 			handler:function(){
				 				var storageType=$("#storageType-table-list").datagrid('getSelected');
				 				loading("启用中..");
				 				$.ajax({   
						            url :'basefiles/openStorageType.do?id='+storageType.id,
						            type:'post',
						            dataType:'json',
						            success:function(json){
						            	loaded();
						            	if(json.flag==true){
						            		$.messager.alert("提醒","启用成功！");
						            		//按钮点击后 控制按钮状态显示
		 									$("#storageType-button").buttonWidget("setButtonType","1");
		 									$("#storageType-panel").panel({  
												fit:true, 
												title: '仓库类型详情',
												cache: false,
												closed:true,
												href : "basefiles/showStorageTypeInfo.do?id="+storageType.id
											});
											$("#storageType-panel").panel("open");
						            		$('#storageType-table-list').datagrid('reload');
						            	}else{
					            			$.messager.alert("警告","启用失败！");
						            	}
						            }
						        });
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/closeStorageType.do">
			 			{
				 			type:'button-close',
				 			handler:function(){
				 				var storageType=$("#storageType-table-list").datagrid('getSelected');
				 				loading("禁用中..");
				 				$.ajax({   
						            url :'basefiles/closeStorageType.do?id='+storageType.id,
						            type:'post',
						            dataType:'json',
						            success:function(json){
						            	loaded();
						            	if(json.flag==true){
						            		$.messager.alert("提醒","禁用成功！");
						            		//按钮点击后 控制按钮状态显示
		 									$("#storageType-button").buttonWidget("setButtonType","0");
						            		$('#storageType-table-list').datagrid('reload');
						            		$("#storageType-panel").panel({  
												fit:true, 
												title: '仓库类型详情',
												cache: false,
												closed:true,
												href : "basefiles/showStorageTypeInfo.do?id="+storageType.id
											});
											$("#storageType-panel").panel("open");
						            	}else{
					            			$.messager.alert("警告","禁用失败！");
						            	}
						            }
						        });
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/storageTypeImport.do">
			 			{
				 			type:'button-import',
				 			attr:{
				 				clazz: "storageService", //spring中注入的类名
						 		method: "addDRStorageType", //插入数据库的方法
						 		tn: "t_base_storage_type", //表名
					            module: 'basefiles', //模块名，
						 		pojo: "StorageType", //实体类名，将和模块名组合成com.hd.agent.basefiles.model.MeteringUnit。
								onClose: function(){ //导入成功后窗口关闭时操作，
							         $("#storageType-table-list").datagrid('reload');	//更新列表	                                                                                        
								}
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/storageTypeExport.do">
			 			{
				 			type:'button-export',
				 			attr:{
							 	tn: 't_base_storage_type', //表名
							 	name:'仓库类型列表'
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/storageTypeGiveup.do">
			 			{
			 				type:'button-giveup',
			 				handler:function(){
				 				var type = $("#storageType-button").buttonWidget("getOperType");
				 				if(type=="add"){
				 					$("#storageType-button").buttonWidget("initButtonType","list");
				 					$("#storageType-panel").panel("close");
				 				}else if(type=="edit"){
				 					var storageType=$("#storageType-table-list").datagrid('getSelected');
				 					$.ajax({   
							            url :'system/lock/unLockData.do',
							            type:'post',
							            data:{id:storageType.id,tname:'t_base_storage_type'},
							            dataType:'json',
							            async: false,
							            success:function(json){
							            	flag = json.flag
							            }
							        });
					 				if(!flag){
					 					$.messager.alert("警告","该数据正在被其他人操作，暂不能修改！");
					 					return false;
					 				}	
				 					$("#storageType-button").buttonWidget("initButtonType","view");
				 					$("#storageType-button").buttonWidget("setButtonType",storageType.state);
					 				$("#storageType-panel").panel({  
										fit:true, 
										title: '仓库类型详情',
										cache: false,
										closed:true,
										href : "basefiles/showStorageTypeInfo.do?id="+storageType.id
									});
									$("#storageType-panel").panel("open");
				 				}
				 			}
			 			},
		 			</security:authorize>
		 			{}
				],
				model:'base',
				type:'list',
				tname:'t_base_storage_type'
			});
		});
		//ajax调用
		var ajaxCall = function (Data, Action) {
		    var ajax = $.ajax({
		        type: 'post',
		        cache: false,
		        url: Action,
		        data: Data,
		        async: false
		    })
		    return ajax.responseText;
		}
	</script>
  </body>
</html>
