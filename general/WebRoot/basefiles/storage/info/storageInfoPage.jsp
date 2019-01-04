<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>仓库档案</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
  	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'north',split:false,border:false" style="height: 30px;overflow: hidden;">
   			<div id="storageInfo-button" class="buttonBG"></div>
    	</div>
    	<div title="仓库档案列表" data-options="region:'west',split:true" style="width:290px;">
            <table id="storageInfo-table-list"></table>
    	</div>
    	<div data-options="region:'center',split:true">
    		<div id="storageInfo-panel"></div>
   		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			var storageInfoCol=$("#storageInfo-table-list").createGridColumnLoad({
		     	name:'base_storage_info',
		     	frozenCol:[[]],
		     	commonCol:[[
		     				{field:'id',title:'编码',width:50,sortable:true},
		     				{field:'name',title:'名称',width:85,sortable:true},
		     				{field:'state',title:'状态',width:40,sortable:true,
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
		     				{field:'storagetypename',title:'仓库类型',width:60,sortable:true,aliascol:'storagetype'},
		     				{field:'moenytypename',title:'金额管理方式',width:100,hidden:true,sortable:true,aliascol:'moenytype'},
		     				{field:'pricetypename',title:'计价方式',width:100,hidden:true,sortable:true,aliascol:'pricetype'},
		     				{field:'isbatch',title:'是否批次管理',width:100,sortable:true,hidden:true,
		     					formatter:function(val){
					        		if(val=="1"){
					        			return "是";
					        		}else if(val=="0"){
					        			return "否";
					        		}
					        	}
		     				},
		     				{field:'isstoragelocation',title:'是否库位管理',width:100,sortable:true,hidden:true,
		     					formatter:function(val){
					        		if(val=="1"){
					        			return "是";
					        		}else if(val=="0"){
					        			return "否";
					        		}
					        	}
		     				},
		     				{field:'islosestorage',title:'是否允许负库存',width:100,sortable:true,hidden:true,
		     					formatter:function(val){
					        		if(val=="1"){
					        			return "是";
					        		}else if(val=="0"){
					        			return "否";
					        		}
					        	}
		     				},
		     				{field:'istotalcontrol',title:'是否参与总量控制',width:100,sortable:true,hidden:true,
		     					formatter:function(val){
					        		if(val=="1"){
					        			return "是";
					        		}else if(val=="0"){
					        			return "否";
					        		}
					        	}
		     				},
		     				{field:'issendusable',title:'是否允许超可用量发货',width:100,sortable:true,hidden:true,
		     					formatter:function(val){
					        		if(val=="1"){
					        			return "是";
					        		}else if(val=="0"){
					        			return "否";
					        		}
					        	}
		     				},
		     				{field:'isoutusable',title:'是否允许超可用量出库',width:100,sortable:true,hidden:true,
		     					formatter:function(val){
					        		if(val=="1"){
					        			return "是";
					        		}else if(val=="0"){
					        			return "否";
					        		}
					        	}
		     				},
		     				{field:'managername',title:'负责人',width:100,sortable:true,hidden:true,aliascol:'manageruserid'},
		     				{field:'telphone',title:'电话',width:100,sortable:true,hidden:true},
		     				{field:'addr',title:'地址',width:150,sortable:true,hidden:true},
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
		    $('#storageInfo-table-list').datagrid({
	  			authority:storageInfoCol,
	  	 		frozenColumns:storageInfoCol.frozen,
				columns:storageInfoCol.common,
			    fit:true, 
				method:'post',
				rownumbers:true,
				pagination:true,
				idField:'id',
				singleSelect:true,
				url:'basefiles/showStorageInfoDataList.do',
				onLoadSuccess:function(){
			    	var p = $('#storageInfo-table-list').datagrid('getPager');  
				    $(p).pagination({  
				        beforePageText: '',//页数文本框前显示的汉字  
				        afterPageText: '',  
				        displayMsg: ''
				    });
		    	},
		    	onClickRow:function(rowIndex, rowData){
		    		$("#storageInfo-panel").panel({  
						fit:true, 
						title: '仓库档案详情',
						cache: false,
						closed:true,
						href : "basefiles/showStorageInfoViewPage.do?id="+rowData.id
					});
					$("#storageInfo-panel").panel("open");
		    	}
			}).datagrid("columnMoving");
			$("#storageInfo-button").buttonWidget({
				//初始默认按钮 根据type对应按钮事件
				initButton:[
					{},
					<security:authorize url="/basefiles/showStorageInfoAddPage.do">
						{
							type:'button-add',
							handler:function(){
								$("#storageInfo-panel").panel({  
									fit:true, 
									title: '仓库档案新增',
									cache: false,
									closed:true,
									href : "basefiles/showStorageInfoAddPage.do"
								});
								$("#storageInfo-panel").panel("open");
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/showStorageInfoEditPage.do">
			 			{
				 			type:'button-edit',
				 			handler:function(){
				 				var storageInfo=$("#storageInfo-table-list").datagrid('getSelected');
                                if(null == storageInfo){
                                    $.messager.alert("提醒","请选中要修改的仓库档案!");
                                    return false;
                                }
				 				$.ajax({   
						            url :'system/lock/isDoLockData.do',
						            type:'post',
						            data:{id:storageInfo.id,tname:'t_base_storage_info'},
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
				 				$("#storageInfo-panel").panel({  
									fit:true, 
									title: '仓库档案修改',
									cache: false,
									closed:true,
									href : "basefiles/showStorageInfoEditPage.do?id="+storageInfo.id
								});
								$("#storageInfo-panel").panel("open");
				 				
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/addStorageInfoHold.do">
			 			{
			 				type:'button-hold',
			 				handler:function(){
				 				var type = $("#storageInfo-button").buttonWidget("getOperType");
				 				if(type=="add"){
				 					//暂存
				 					$("#storageInfo-form-add").attr("action", "basefiles/addStorageInfoHold.do");
				 					$("#storageInfo-form-add").submit();
				 				}else if(type=="edit"){
				 					//暂存
				 					$("#storageInfo-form-edit").attr("action", "basefiles/editStorageInfoHold.do");
				 					$("#storageInfo-form-edit").submit();
				 				}
				 			}
				 		},
			 		</security:authorize>
					<security:authorize url="/basefiles/addStorageInfoSave.do">
			 			{
				 			type:'button-save',
				 			handler:function(){
				 				var type = $("#storageInfo-button").buttonWidget("getOperType");
				 				if(type=="add"){
				 					//保存
				 					$("#storageInfo-form-add").attr("action", "basefiles/addStorageInfoSave.do");
				 					$("#storageInfo-form-add").submit();
				 				}else if(type=="edit"){
				 					//暂存
				 					$("#storageInfo-form-edit").attr("action", "basefiles/editStorageInfoSave.do");
				 					$("#storageInfo-form-edit").submit();
				 				}
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/deleteStorageInfo.do">
			 			{
				 			type:'button-delete',
				 			handler:function(){
				 				var storageInfo=$("#storageInfo-table-list").datagrid('getSelected');
                                if(null == storageInfo){
                                    $.messager.alert("提醒","请选中要删除的仓库档案!");
                                    return false;
                                }
                                var url = 'basefiles/deleteStorageInfo.do?id='+storageInfo.id;
				 				$.ajax({   
						            url :'system/lock/isLockData.do',
						            type:'post',
						            data:{id:storageInfo.id,tname:'t_base_storage_info'},
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
				 				$.messager.confirm("提醒", "是否删除该仓库档案?", function(r){
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
				 									$("#storageInfo-button").buttonWidget("initButtonType","list");
								            		$("#storageInfo-panel").panel("close");
								            		$('#storageInfo-table-list').datagrid('clearSelections');
								            		$('#storageInfo-table-list').datagrid('reload');
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
					<security:authorize url="/basefiles/openStorageInfo.do">
			 			{
				 			type:'button-open',
				 			handler:function(){
                                var storageInfo=$("#storageInfo-table-list").datagrid('getSelected');
                                if(null == storageInfo){
                                    $.messager.alert("提醒","请选中要启用的仓库档案!");
                                    return false;
                                }
				 				loading("启用中..");
				 				$.ajax({   
						            url :'basefiles/openStorageInfo.do?id='+storageInfo.id,
						            type:'post',
						            dataType:'json',
						            success:function(json){
						            	loaded();
						            	if(json.flag==true){
						            		$.messager.alert("提醒","启用成功！");
		 									$("#storageInfo-panel").panel({  
												fit:true, 
												title: '仓库档案详情',
												cache: false,
												closed:true,
												href : "basefiles/showStorageInfoViewPage.do?id="+storageInfo.id
											});
											$("#storageInfo-panel").panel("open");
						            		$('#storageInfo-table-list').datagrid('reload');
						            	}else{
					            			$.messager.alert("警告","启用失败！");
						            	}
						            }
						        });
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/closeStorageInfo.do">
			 			{
				 			type:'button-close',
				 			handler:function(){
				 				var storageInfo=$("#storageInfo-table-list").datagrid('getSelected');
                                if(null == storageInfo){
                                    $.messager.alert("提醒","请选择要禁用的仓库档案!");
                                    return false;
                                }
				 				loading("禁用中..");
				 				$.ajax({   
						            url :'basefiles/closeStorageInfo.do?id='+storageInfo.id,
						            type:'post',
						            dataType:'json',
						            success:function(json){
						            	loaded();
						            	if(json.flag==true){
						            		$.messager.alert("提醒","禁用成功！");
						            		$('#storageInfo-table-list').datagrid('reload');
						            		$("#storageInfo-panel").panel({  
												fit:true, 
												title: '仓库档案详情',
												cache: false,
												closed:true,
												href : "basefiles/showStorageInfoViewPage.do?id="+storageInfo.id
											});
											$("#storageInfo-panel").panel("open");
						            	}else{
					            			$.messager.alert("警告","禁用失败！");
						            	}
						            }
						        });
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/storageInfoImport.do">
			 			{
				 			type:'button-import',
				 			attr:{
				 				clazz: "storageService", //spring中注入的类名
						 		method: "addDRStorageInfo", //插入数据库的方法
						 		tn: "t_base_storage_info", //表名
					            module: 'basefiles', //模块名，
					            majorkey:'id',
						 		pojo: "StorageInfo", //实体类名，将和模块名组合成com.hd.agent.basefiles.model.MeteringUnit。
								onClose: function(){ //导入成功后窗口关闭时操作，
							         $("#storageInfo-table-list").datagrid('reload');	//更新列表	                                                                                        
								}
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/storageInfoExport.do">
			 			{
				 			type:'button-export',
				 			attr:{
							 	tn: 't_base_storage_info', //表名
							 	name:'仓库列表'
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/storageInfoGiveup.do">
			 			{
			 				type:'button-giveup',
			 				handler:function(){
				 				var type = $("#storageInfo-button").buttonWidget("getOperType");
				 				if(type=="add"){
				 					$("#storageInfo-button").buttonWidget("initButtonType","list");
				 					$("#storageInfo-panel").panel("close");
				 				}else if(type=="edit"){
				 					var storageInfo=$("#storageInfo-table-list").datagrid('getSelected');
				 					$.ajax({   
							            url :'system/lock/unLockData.do',
							            type:'post',
							            data:{id:storageInfo.id,tname:'t_base_storage_info'},
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
				 					$("#storageInfo-button").buttonWidget("initButtonType","view");
				 					$("#storageInfo-button").buttonWidget("setButtonType",storageInfo.state);
					 				$("#storageInfo-panel").panel({  
										fit:true, 
										title: '出入库类型详情',
										cache: false,
										closed:true,
										href : "basefiles/showStorageInfoViewPage.do?id="+storageInfo.id
									});
									$("#storageInfo-panel").panel("open");
				 				}
				 			}
			 			},
		 			</security:authorize>
		 			{}
				],
				model:'base',
				type:'list',
				tname:'t_base_storage_info'
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
