<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>工作岗位</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
  	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'north',split:false,border:false" style="height: 30px;overflow: hidden">
   			<div class="buttonBG" id="workjob-button"></div>
    	</div>
    	<div title="工作岗位列表" data-options="region:'west',split:true" style="width:400px;">
            <table id="workjob-table-list"></table>
    	</div>
    	<div data-options="region:'center',split:true">
    		<div id="workjob-panel"></div>
   		</div>
	</div>
  <script type="text/javascript">
	$(function(){
		var workjobCol=$("#workjob-table-list").createGridColumnLoad({
	     	name:'base_workjob',
	     	frozenCol:[[]],
	     	commonCol:[[
	     				{field:'id',title:'编码',width:50,sortable:true},
	     				{field:'jobname',title:'岗位名称',width:120,sortable:true},
	     				{field:'deptid',title:'所属部门',width:120,sortable:true,
	     					formatter:function(val,rowData,rowIndex){
	     						return rowData.deptname;
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
	    $('#workjob-table-list').datagrid({
  			authority:workjobCol,
  	 		frozenColumns:workjobCol.frozen,
			columns:workjobCol.common,
		    fit:true, 
			method:'post',
			rownumbers:true,
			pagination:true,
			idField:'id',
			singleSelect:true,
			url:'basefiles/getWorkJobList.do',
			onLoadSuccess:function(){
		    	var p = $('#workjob-table-list').datagrid('getPager');  
			    $(p).pagination({  
			        beforePageText: '',//页数文本框前显示的汉字  
			        afterPageText: '',  
			        displayMsg: ''
			    });
	    	},
	    	onClickRow:function(rowIndex, rowData){
	    		$("#workjob-panel").panel({  
					fit:true, 
					title: '工作岗位详情',
					cache: false,
					closed:true,
					href : "basefiles/showWorkJobInfo.do?id="+rowData.id
				});
				$("#workjob-panel").panel("open");
				$("#workjob-button").buttonWidget("initButtonType","view");
				$("#workjob-button").buttonWidget("setDataID",{id:rowData.id,state:rowData.state});
	    	}
		}).datagrid("columnMoving");
		$("#workjob-button").buttonWidget({
			//初始默认按钮 根据type对应按钮事件
			initButton:[
				{},
				<security:authorize url="/basefiles/showWorkJobAddPage.do">
				{
					type:'button-add',
					handler:function(){
						$("#workjob-panel").panel({  
							fit:true, 
							title: '工作岗位新增',
							cache: false,
							closed:true,
							href : "basefiles/showWorkJobAddPage.do"
						});
						$("#workjob-panel").panel("open");
						//按钮点击后 控制按钮状态显示
					}
				},
				</security:authorize>
				<security:authorize url="/basefiles/showWorkJobEditPage.do">
	 			{
		 			type:'button-edit',
		 			handler:function(){
		 				var workjob=$("#workjob-table-list").datagrid('getSelected');
		 				var flag = false;
		 				$.ajax({   
				            url :'system/lock/isDoLockData.do',
				            type:'post',
				            data:{id:workjob.id,tname:'t_base_workjob'},
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
		 				$("#workjob-panel").panel({  
							fit:true, 
							title: '工作岗位修改',
							cache: false,
							closed:true,
							href : "basefiles/showWorkJobEditPage.do?id="+workjob.id
						});
						$("#workjob-panel").panel("open");
		 				//按钮点击后 控制按钮状态显示
		 			}
	 			},
	 			</security:authorize>
				<security:authorize url="/basefiles/addWorkJobHold.do">
	 			{
	 				type:'button-hold',
	 				handler:function(){
		 				var type = $("#workjob-button").buttonWidget("getOperType");
		 				if(type=="add"){
		 					//暂存
		 					$("#workjob-form-add").attr("action", "basefiles/addWorkJobHold.do");
		 					$("#workjob-form-add").submit();
		 				}else if(type=="edit"){
		 					//暂存
		 					$("#workjob-form-edit").attr("action", "basefiles/editWorkJobHold.do");
		 					$("#workjob-form-edit").submit();
		 				}
		 				//按钮点击后 控制按钮状态显示
		 			}
		 		},
		 		</security:authorize>
				<security:authorize url="/basefiles/addWorkJobSave.do">
	 			{
		 			type:'button-save',
		 			handler:function(){
		 				var type = $("#workjob-button").buttonWidget("getOperType");
		 				if(type=="add"){
		 					//保存
		 					$("#workjob-form-add").attr("action", "basefiles/addWorkJobSave.do");
		 					$("#workjob-form-add").submit();
		 				}else if(type=="edit"){
		 					//暂存
		 					$("#workjob-form-edit").attr("action", "basefiles/editWorkJobSave.do");
		 					$("#workjob-form-edit").submit();
		 				}
		 			}
	 			},
	 			</security:authorize>
				<security:authorize url="/basefiles/deleteWorkJob.do">
	 			{
		 			type:'button-delete',
		 			handler:function(){
		 				var workjob=$("#workjob-table-list").datagrid('getSelected');
		 				var url = 'basefiles/deleteWorkJob.do?id='+workjob.id;
		 				$.ajax({   
				            url :'system/lock/isLockData.do',
				            type:'post',
				            data:{id:workjob.id,tname:'t_base_workjob'},
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
		 				$.messager.confirm("提醒", "是否删除该岗位?", function(r){
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
		 									$("#workjob-button").buttonWidget("initButtonType","list");
						            		$("#workjob-panel").panel("close");
						            		$('#workjob-table-list').datagrid('clearSelections');
						            		$('#workjob-table-list').datagrid('reload');
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
				<security:authorize url="/basefiles/showWorkJobCopyPage.do">
	 			{
		 			type:'button-copy',
		 			handler:function(){
		 				var workjob=$("#workjob-table-list").datagrid('getSelected');
		 				$("#workjob-panel").panel({  
							fit:true, 
							title: '工作岗位新增',
							cache: false,
							closed:true,
							href : "basefiles/showWorkJobCopyPage.do?id="+workjob.id
						});
						$("#workjob-panel").panel("open");
		 			}
	 			},
	 			</security:authorize>
				<security:authorize url="/basefiles/openWorkJob.do">
	 			{
		 			type:'button-open',
		 			handler:function(){
		 				var workjob=$("#workjob-table-list").datagrid('getSelected');
		 				loading("启用中..");
		 				$.ajax({   
				            url :'basefiles/openWorkJob.do?id='+workjob.id,
				            type:'post',
				            dataType:'json',
				            success:function(json){
				            	loaded();
				            	if(json.flag==true){
				            		$.messager.alert("提醒","启用成功！");
				            		//按钮点击后 控制按钮状态显示
 									$("#workjob-button").buttonWidget("setButtonType","1");
 									
 									$("#workjob-panel").panel({  
										fit:true, 
										title: '工作岗位详情',
										cache: false,
										closed:true,
										href : "basefiles/showWorkJobInfo.do?id="+workjob.id
									});
									$("#workjob-panel").panel("open");
				            		$('#workjob-table-list').datagrid('reload');
				            	}else{
			            			$.messager.alert("警告","启用失败！");
				            	}
				            }
				        });
		 			}
	 			},
	 			</security:authorize>
				<security:authorize url="/basefiles/closeWorkJob.do">
	 			{
		 			type:'button-close',
		 			handler:function(){
		 				var workjob=$("#workjob-table-list").datagrid('getSelected');
		 				loading("禁用中..");
		 				$.ajax({   
				            url :'basefiles/closeWorkJob.do?id='+workjob.id,
				            type:'post',
				            dataType:'json',
				            success:function(json){
				            	loaded();
				            	if(json.flag==true){
				            		$.messager.alert("提醒","禁用成功！");
				            		//按钮点击后 控制按钮状态显示
 									$("#workjob-button").buttonWidget("setButtonType","0");
				            		$('#workjob-table-list').datagrid('reload');
				            		$("#workjob-panel").panel({  
										fit:true, 
										title: '工作岗位详情',
										cache: false,
										closed:true,
										href : "basefiles/showWorkJobInfo.do?id="+workjob.id
									});
									$("#workjob-panel").panel("open");
				            	}else{
			            			$.messager.alert("警告","禁用失败！");
				            	}
				            }
				        });
		 			}
	 			},
	 			</security:authorize>
				<security:authorize url="/basefiles/workJobImport.do">
	 			{
		 			type:'button-import',
		 			attr:{
		 				clazz: "workJobService", //spring中注入的类名
				 		method: "addDRWorkJob", //插入数据库的方法
				 		tn: "t_base_workjob", //表名
			            module: 'basefiles', //模块名，
				 		pojo: "WorkJob", //实体类名，将和模块名组合成com.hd.agent.basefiles.model.MeteringUnit。
						onClose: function(){ //导入成功后窗口关闭时操作，
					         $("#workjob-table-list").datagrid('reload');	//更新列表	                                                                                        
						}
		 			}
	 			},
	 			</security:authorize>
				<security:authorize url="/basefiles/workJobExport.do">
	 			{
		 			type:'button-export',
		 			attr:{
					 	tn: 't_base_workjob', //表名
					 	name:'工作岗位列表'
		 			}
	 			},
	 			</security:authorize>
				<security:authorize url="/basefiles/workjobpreview.do">
	 			{
		 			type:'button-preview',
		 			handler:function(){
		 				alert("打印预览");
		 			}
	 			},
	 			</security:authorize>
				<security:authorize url="/basefiles/workjobPrint.do">
	 			{
		 			type:'button-print',
		 			handler:function(){
		 				alert("打印");
		 			}
	 			},
	 			</security:authorize>
				<security:authorize url="/basefiles/workjobGiveup.do">
	 			{
	 				type:'button-giveup',
	 				handler:function(){
		 				var type = $("#workjob-button").buttonWidget("getOperType");
		 				if(type=="add"){
		 					$("#workjob-button").buttonWidget("initButtonType","list");
		 					$("#workjob-panel").panel("close");
		 				}else if(type=="edit"){
		 					var workjob=$("#workjob-table-list").datagrid('getSelected');
		 					$("#workjob-button").buttonWidget("initButtonType","view");
		 					$("#workjob-button").buttonWidget("setButtonType",workjob.state);
			 				$("#workjob-panel").panel({  
								fit:true, 
								title: '工作岗位详情',
								cache: false,
								closed:true,
								href : "basefiles/showWorkJobInfo.do?id="+workjob.id
							});
							$("#workjob-panel").panel("open");
		 				}
		 			}
	 			},
	 			</security:authorize>
				{}
			],
			model:'base',
			type:'list',
			tname:'t_base_workjob'
			
		});
	});
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
