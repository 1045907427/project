<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>出入库类型</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
  	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'north',split:false,border:false" style="height: 30px;overflow: hidden;">
   			<div id="storageInout-button" class="buttonBG"></div>
    	</div>
    	<div title="出入库类型列表" data-options="region:'west',split:true" style="width:400px;">
            <table id="storageInout-table-list"></table>
    	</div>
    	<div data-options="region:'center',split:true">
    		<div id="storageInout-panel"></div>
   		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			var storageInoutCol=$("#storageInout-table-list").createGridColumnLoad({
	     	name:'base_storage_inout',
	     	frozenCol:[[]],
	     	commonCol:[[
	     				{field:'id',title:'编码',width:50,sortable:true},
	     				{field:'name',title:'名称',width:120,sortable:true},
	     				{field:'type',title:'类型',width:60,sortable:true,
	     					formatter:function(val){
				        		if(val=="1"){
				        			return "入库";
				        		}else if(val=="2"){
				        			return "出库";
				        		}
				        	}
	     				},
	     				{field:'referunit',title:'相关单位',width:80,sortable:true,
	     					formatter:function(val){
				        		if(val=="1"){
				        			return "供应商";
				        		}else if(val=="2"){
				        			return "客户";
				        		}else if(val=="3"){
				        			return "仓库";
				        		}else if(val=="4"){
				        			return "部门";
				        		}
				        	}
	     				},
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
	     				{field:'issystem',title:'是否系统预制',width:80,sortable:true,
	     					formatter:function(val){
				        		if(val=="1"){
				        			return "是";
				        		}else if(val=="0"){
				        			return "否";
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
	    $('#storageInout-table-list').datagrid({
  			authority:storageInoutCol,
  	 		frozenColumns:storageInoutCol.frozen,
			columns:storageInoutCol.common,
		    fit:true, 
			method:'post',
			rownumbers:true,
			pagination:true,
			idField:'id',
			singleSelect:true,
			url:'basefiles/showStorageInoutList.do',
			onLoadSuccess:function(){
		    	var p = $('#storageInout-table-list').datagrid('getPager');  
			    $(p).pagination({  
			        beforePageText: '',//页数文本框前显示的汉字  
			        afterPageText: '',  
			        displayMsg: ''
			    });
	    	},
	    	onClickRow:function(rowIndex, rowData){
	    		$("#storageInout-panel").panel({  
					fit:true, 
					title: '出入库类型详情',
					cache: false,
					closed:true,
					href : "basefiles/showStorageInoutInfo.do?id="+rowData.id
				});
				$("#storageInout-panel").panel("open");
	    	}
		}).datagrid("columnMoving");
			$("#storageInout-button").buttonWidget({
				//初始默认按钮 根据type对应按钮事件
				initButton:[
					{},
					<security:authorize url="/basefiles/showStorageInoutAddPage.do">
						{
							type:'button-add',
							handler:function(){
								$("#storageInout-panel").panel({  
									fit:true, 
									title: '出入库类型新增',
									cache: false,
									closed:true,
									href : "basefiles/showStorageInoutAddPage.do"
								});
								$("#storageInout-panel").panel("open");
								//按钮点击后 控制按钮状态显示
				 				$("#storageInout-button").buttonWidget("initButtonType","add");
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/showStorageInoutEditPage.do">
			 			{
				 			type:'button-edit',
				 			handler:function(){
				 				var storageInout=$("#storageInout-table-list").datagrid('getSelected');
				 				$.ajax({   
						            url :'system/lock/isDoLockData.do',
						            type:'post',
						            data:{id:storageInout.id,tname:'t_base_storage_inout'},
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
				 				$("#storageInout-panel").panel({  
									fit:true, 
									title: '出入库类型修改',
									cache: false,
									closed:true,
									href : "basefiles/showStorageInoutEditPage.do?id="+storageInout.id
								});
								$("#storageInout-panel").panel("open");
				 				//按钮点击后 控制按钮状态显示
				 				$("#storageInout-button").buttonWidget("initButtonType","edit");
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/addStorageInoutHold.do">
			 			{
			 				type:'button-hold',
			 				handler:function(){
				 				var type = $("#storageInout-button").buttonWidget("getOperType");
				 				if(type=="add"){
				 					//暂存
				 					$("#storageInout-form-add").attr("action", "basefiles/addStorageInoutHold.do");
				 					$("#storageInout-form-add").submit();
				 				}else if(type=="edit"){
				 					//暂存
				 					$("#storageInout-form-edit").attr("action", "basefiles/editStorageInoutHold.do");
				 					$("#storageInout-form-edit").submit();
				 				}
				 			}
				 		},
			 		</security:authorize>
					<security:authorize url="/basefiles/addStorageInoutSave.do">
			 			{
				 			type:'button-save',
				 			handler:function(){
				 				var type = $("#storageInout-button").buttonWidget("getOperType");
				 				if(type=="add"){
				 					//保存
				 					$("#storageInout-form-add").attr("action", "basefiles/addStorageInoutSave.do");
				 					$("#storageInout-form-add").submit();
				 				}else if(type=="edit"){
				 					//暂存
				 					$("#storageInout-form-edit").attr("action", "basefiles/editStorageInoutSave.do");
				 					$("#storageInout-form-edit").submit();
				 				}
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/deleteStorageInout.do">
			 			{
				 			type:'button-delete',
				 			handler:function(){
				 				var storageInout=$("#storageInout-table-list").datagrid('getSelected');
				 				var url = 'basefiles/deleteStorageInout.do?id='+storageInout.id;
				 				$.ajax({   
						            url :'system/lock/isLockData.do',
						            type:'post',
						            data:{id:storageInout.id,tname:'t_base_storage_info'},
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
				 				$.messager.confirm("提醒", "是否删除该出入库类型?", function(r){
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
				 									$("#storageInout-button").buttonWidget("initButtonType","list");
								            		$("#storageInout-panel").panel("close");
								            		$('#storageInout-table-list').datagrid('clearSelections');
								            		$('#storageInout-table-list').datagrid('reload');
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
					<security:authorize url="/basefiles/openStorageInout.do">
			 			{
				 			type:'button-open',
				 			handler:function(){
				 				var storageInout=$("#storageInout-table-list").datagrid('getSelected');
				 				loading("启用中..");
				 				$.ajax({   
						            url :'basefiles/openStorageInout.do?id='+storageInout.id,
						            type:'post',
						            dataType:'json',
						            success:function(json){
						            	loaded();
						            	if(json.flag==true){
						            		$.messager.alert("提醒","启用成功！");
						            		//按钮点击后 控制按钮状态显示
		 									$("#storageInout-button").buttonWidget("setButtonType","1");
		 									$("#storageInout-panel").panel({  
												fit:true, 
												title: '出入库类型详情',
												cache: false,
												closed:true,
												href : "basefiles/showStorageInoutInfo.do?id="+storageInout.id
											});
											$("#storageInout-panel").panel("open");
						            		$('#storageInout-table-list').datagrid('reload');
						            	}else{
					            			$.messager.alert("警告","启用失败！");
						            	}
						            }
						        });
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/closeStorageInout.do">
			 			{
				 			type:'button-close',
				 			handler:function(){
				 				var storageInout=$("#storageInout-table-list").datagrid('getSelected');
				 				loading("禁用中..");
				 				$.ajax({   
						            url :'basefiles/closeStorageInout.do?id='+storageInout.id,
						            type:'post',
						            dataType:'json',
						            success:function(json){
						            	loaded();
						            	if(json.flag==true){
						            		$.messager.alert("提醒","禁用成功！");
						            		//按钮点击后 控制按钮状态显示
		 									$("#storageInout-button").buttonWidget("setButtonType","0");
						            		$('#storageInout-table-list').datagrid('reload');
						            		$("#storageInout-panel").panel({  
												fit:true, 
												title: '出入库类型详情',
												cache: false,
												closed:true,
												href : "basefiles/showStorageInoutInfo.do?id="+storageInout.id
											});
											$("#storageInout-panel").panel("open");
						            	}else{
					            			$.messager.alert("警告","禁用失败！");
						            	}
						            }
						        });
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/storageInoutImport.do">
			 			{
				 			type:'button-import',
				 			attr:{
				 				clazz: "storageService", //spring中注入的类名
						 		method: "addDRStorageInout", //插入数据库的方法
						 		tn: "t_base_storage_inout", //表名
					            module: 'basefiles', //模块名，
						 		pojo: "StorageInout", //实体类名，将和模块名组合成com.hd.agent.basefiles.model.MeteringUnit。
								onClose: function(){ //导入成功后窗口关闭时操作，
							         $("#storageInout-table-list").datagrid('reload');	//更新列表	                                                                                        
								}
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/storageInoutExport.do">
			 			{
				 			type:'button-export',
				 			attr:{
							 	tn: 't_base_storage_inout', //表名
							 	name:'出入库类型列表'
				 			}
			 			},	
		 			</security:authorize>
					<security:authorize url="/basefiles/storageInoutGiveup.do">
			 			{
			 				type:'button-giveup',
			 				handler:function(){
				 				var type = $("#storageInout-button").buttonWidget("getOperType");
				 				if(type=="add"){
				 					$("#storageInout-button").buttonWidget("initButtonType","list");
				 					$("#storageInout-panel").panel("close");
				 				}else if(type=="edit"){
				 					var storageInout=$("#storageInout-table-list").datagrid('getSelected');
				 					$.ajax({   
							            url :'system/lock/unLockData.do',
							            type:'post',
							            data:{id:storageInout.id,tname:'t_base_storage_inout'},
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
				 					$("#storageInout-button").buttonWidget("initButtonType","view");
				 					$("#storageInout-button").buttonWidget("setButtonType",storageInout.state);
					 				$("#storageInout-panel").panel({  
										fit:true, 
										title: '出入库类型详情',
										cache: false,
										closed:true,
										href : "basefiles/showStorageInoutInfo.do?id="+storageInout.id
									});
									$("#storageInout-panel").panel("open");
				 				}
				 			}
			 			},
		 			</security:authorize>
		 			{}
				],
				model:'base',
				type:'list',
				tname:'t_base_storage_inout'
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
