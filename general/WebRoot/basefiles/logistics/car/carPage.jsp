<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>车辆首页</title>
		<%@include file="/include.jsp"%>
	</head>
	<body>
		<div class="easyui-layout" data-options="fit:true"
			id="logistics-layout-car">
			<div data-options="region:'north'"
				style="height: 30px; overflow: hidden">
				<div class="buttonBG" id="car-button-layout"></div>
			</div>
			<div data-options="region:'west',split:true"
				style="width: 580px; height: auto">
				<div id="car-query-showCarList">
					<form action="" id="car-form-carListQuery" method="post">
						<table cellpadding="1" cellspacing="0" border="0">
							<tr>
								<td>
									编码:
								</td>
								<td>
									<input id="car-widget-id" type="text" name="id"
										style="width: 100px" />
								</td>
								<td>
									名称:
								</td>
								<td>
									<input id="car-widget-name" type="text" name="name"
										style="width: 100px" />
								</td>
								<td>
									<a href="javaScript:void(0);" id="car-query-queryCarList" class="button-qr">查询</a>
									<a href="javaScript:void(0);" id="car-query-reloadCarlList"class="button-qr">重置</a>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<table id="car-table-list"></table>
			</div>
			<div data-options="region:'center',split:true,border:true">
				<div id="car-div-carInfo"></div>
			</div>
		</div>
		<script type="text/javascript">
    	var car_AjaxConn = function (Data, Action) {
		    var MyAjax = $.ajax({
		        type: 'post',
		        cache: false,
		        url: Action,
		        data: Data,
		        async: false,
		        success:function(data){
		        	loaded();
		        }
		    })
		    return MyAjax.responseText;
		}
		
		function refreshLayout(title, url,type){
    		$("#logistics-layout-car").layout('remove','center').layout('add',{
				region: 'center',  
			    title: title,
			    href:url
			});
			$("#car-opera").attr("value",type);
    	}
		//根据初始的列与用户保存的列生成以及字段权限生成新的列
	     var carListColJson=$("#car-table-list").createGridColumnLoad({
	     	name:'base_logistics_car',
	     	frozenCol:[[]],
	     	commonCol:[[
				{field:'id',title:'编码',width:60,sortable:true},
				{field:'name',title:'名称',width:80,sortable:true},  
				{field:'state',title:'状态',width:60,sortable:true,
					formatter:function(val,rowData,rowIndex){
							return rowData.stateName;
						}
				},
				{field:'weight',title:'装载限重',resizable:true,sortable:true,
					formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
				},
				{field:'boxnum',title:'装载箱数',resizable:true,sortable:true,
					formatter:function(value,rowData,rowIndex){
				        		return formatterNum(value);
				        	}
				},
				{field:'volume',title:'装载体积',resizable:true,sortable:true,
					formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value,4);
				        	}
				},
				{field:'driverid',title:'默认司机',width:60,sortable:true,
					formatter:function(val,rowData,rowIndex){
							return rowData.driverName;
						}
				},
				{field:'followid',title:'默认跟车',width:60,sortable:true,
					formatter:function(val,rowData,rowIndex){
							return rowData.followName;
						}
				},
				{field:'remark',title:'备注',width:100,sortable:true}
			]]
	     });
	     
	    //判断是否加锁
		function isLockData(id,tablename){
			var flag = false;
			$.ajax({
	            url :'system/lock/isLockData.do',
	            type:'post',
	            data:{id:id,tname:tablename},
	            dataType:'json',
	            async: false,
	            success:function(json){
	            	flag = json.flag
	            }
	        });
	        return flag;
		}
	    
	    //加锁
	    function isDoLockData(id,tablename){
	    	var flag = false;
	    	$.ajax({   
	            url :'system/lock/isDoLockData.do',
	            type:'post',
	            data:{id:id,tname:tablename},
	            dataType:'json',
	            async: false,
	            success:function(json){
	            	flag = json.flag
	            }
	        });
	        return flag;
	    }
     	$(function(){
     	
     		$("#car-widget-id").widget({
     			width:120,
				name:'t_base_logistics_car',
				col:'id',
				singleSelect:true,
				onlyLeafCheck:false
     		});
     	
     		$("#car-button-layout").buttonWidget({
     			//初始默认按钮 根据type对应按钮事件
				initButton:[
					{},
					<security:authorize url="/basefiles/carAddBtn.do">
					{
						type:'button-add',//新增 
						handler:function(){
							refreshLayout('车辆档案【新增】','basefiles/showCarAddPage.do','add');
						}
					},
					</security:authorize>
					<security:authorize url="/basefiles/carEditBtn.do">
		 			{
			 			type:'button-edit',//修改 
			 			handler:function(){
							var car=$("#car-table-list").datagrid('getSelected');
				  			if(car==null){
				  				$.messager.alert("提醒","请选择相应的车辆档案!");
				  				return false;
				  			}
				  			var flag = isDoLockData(car.id,"t_base_logistics_car");
							if(!flag){
								$.messager.alert("警告","该数据正在被其他人操作，暂不能修改！");
								return false;
							}
							refreshLayout('车辆档案【修改】','basefiles/showCarEditPage.do?id='+car.id,'edit');
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/carHoldBtn.do">
		 			{
		 				type:'button-hold',//暂存
		 				handler:function(){
			 				//获取当前按钮操作的状态。add表示正处于新增页面风格edit表示正处于修改页面风格
			 				var type = $("#car-button-layout").buttonWidget("getOperType");
			 				if(type=="add"){
					    		$.messager.confirm("提醒","是否暂存新增车辆档案?",function(r){
			 						if(r){
					 					addCar("hold");//暂存新增车辆档案
			 						}
			 					});
			 				}
			 				else{
			 					$.messager.confirm("提醒","是否暂存修改车辆档案?",function(r){
			 						if(r){
										editCar("hold");//暂存修改车辆档案
									}
			 					});
			 				}
			 			}
			 		},
			 		</security:authorize>
					<security:authorize url="/basefiles/carSaveBtn.do">
		 			{
			 			type:'button-save',//保存
			 			handler:function(){
			 				//获取当前按钮操作的状态。add表示正处于新增页面风格edit表示正处于修改页面风格
			 				var type = $("#car-button-layout").buttonWidget("getOperType");
			 				if(type=="add"){
			 					$.messager.confirm("提醒","是否保存新增车辆档案?",function(r){
			 						if(r){
			 							addCar("save");//保存新增车辆档案
			 						}
			 					});
			 				}
			 				else{
			 					$.messager.confirm("提醒","是否保存修改车辆档案?",function(r){
			 						if(r){
			 							editCar("save");//保存修改车辆档案
			 						}
			 					});
			 				}
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/carDelBtn.do">
		 			{
			 			type:'button-delete',//删除
			 			handler:function(){
			 				var idStr = "";
			 				var cars=$("#car-table-list").datagrid('getChecked');
				  			if(cars.length==0){
				  				$.messager.alert("提醒","请勾选相应的车辆档案!");
				  				return false;
				  			}
				  			for(var i=0;i<cars.length;i++){
		  							idStr += cars[i].id + ",";
		  					}
				  			$.messager.confirm("提醒","是否确认删除车辆档案?",function(r){
				  				if(r){
				  					var ret = car_AjaxConn({idStr:idStr},'basefiles/deleteCar.do');
									var retJSON = $.parseJSON(ret);
									if(retJSON.flag){
										$.messager.alert("提醒",""+retJSON.userNum+"条记录被引用,不允许删除;<br/>删除成功"+retJSON.num+"条记录;");
									}
									else{
										$.messager.alert("提醒",""+retJSON.userNum+"条记录被引用,不允许删除;<br/>删除失败"+retJSON.num+"条记录;");
									}
									$("#logistics-layout-car").layout('remove','center');
									$("#car-table-list").datagrid('reload');
									$("#car-table-list").datagrid('clearChecked');
				  				}
				  			});
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/carCopyBtn.do">
		 			{
			 			type:'button-copy',//复制
			 			handler:function(){
			 				var car=$("#car-table-list").datagrid('getSelected');
				  			if(car==null){
				  				$.messager.alert("提醒","请选择相应的车辆档案!");
				  				return false;
				  			}
				  			refreshLayout('车辆档案【新增】','basefiles/showCarCopyPage.do?id='+car.id,'add');
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/carEnableBtn.do">
		 			{
			 			type:'button-open',//启用 
			 			handler:function(){
			 				var cars=$("#car-table-list").datagrid('getChecked');
			 				var car=$("#car-table-list").datagrid('getSelected');
				  			if(cars.length==0){
				  				$.messager.alert("提醒","请勾选相应的车辆档案!");
				  				return false;
				  			}
				  			$.messager.confirm("提醒","是否确认启用车辆档案?",function(r){
				  				if(r){
				  					var idStr = "";
				  					for(var i=0;i<cars.length;i++){
				  						idStr += cars[i].id + ",";
				  					}
					  				var ret = car_AjaxConn({idStr:idStr},'basefiles/enableCar.do');
									var retJSON = $.parseJSON(ret);
									if(retJSON.flag){
										$.messager.alert("提醒","启用无效"+retJSON.invalidNum+"条记录;<br/>启用成功"+retJSON.num+"条记录;");
										$("#car-button-layout").buttonWidget('initButtonType','multipleList');
										$("#car-table-list").datagrid('reload');
										$("#car-table-list").datagrid('clearChecked');
										$("#car-table-list").datagrid('clearSelections');
										if(car != null){
											refreshLayout('车辆档案【查看】','basefiles/showCarViewPage.do?id='+car.id,'view');
										}
									}
									else{
										$.messager.alert("提醒","启用无效"+retJSON.invalidNum+"条记录;<br/>启用失败"+retJSON.num+"条记录;");
									}
				  				}
				  			});
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/carDisableBtn.do">
		 			{
			 			type:'button-close',//禁用 
			 			handler:function(){
			 				var cars=$("#car-table-list").datagrid('getChecked');
			 				var car=$("#car-table-list").datagrid('getSelected');
				  			if(cars.length==0){
				  				$.messager.alert("提醒","请勾选相应的车辆档案!");
				  				return false;
				  			}
				  			$.messager.confirm("提醒","是否确认禁用车辆档案?",function(r){
				  				if(r){
				  					var idStr = "";
				  					for(var i=0;i<cars.length;i++){
				  						idStr += cars[i].id + ",";
				  					}
				  					var ret = car_AjaxConn({idStr:idStr},'basefiles/disableCar.do');
									var retJSON = $.parseJSON(ret);
									if(retJSON.flag){
										$.messager.alert("提醒",""+retJSON.invalidNum+"条记录状态不允许禁用;<br/>禁用成功"+retJSON.num+"条记录;");
										$("#car-button-layout").buttonWidget('initButtonType','multipleList');
										$("#car-table-list").datagrid('reload');
										$("#car-table-list").datagrid('clearChecked');
										$("#car-table-list").datagrid('clearSelections');
										if(car != null){
											refreshLayout('车辆档案【查看】','basefiles/showCarViewPage.do?id='+car.id,'view');
										}
									}
									else{
										$.messager.alert("提醒",""+retJSON.invalidNum+"条记录状态不允许禁用;<br/>禁用失败"+retJSON.num+"条记录;");
									}
				  				}
				  			});
			 			}
		 			},
		 			</security:authorize>
		 			<security:authorize url="/basefiles/carImportBtn.do">
		 			{
			 			type:'button-import',//导入
			 			attr:{
			 				clazz: "logisticsService",
					 		method: "addDRCar",
					 		tn: "t_base_logistics_car", 
				            module: 'basefiles',
					 		majorkey:'id',
					 		pojo: "LogisticsCar",
							onClose: function(){
						         $("#car-table-list").datagrid('reload');	//更新列表	                                                                          
							}
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/carExportBtn.do">
		 			{
			 			type:'button-export',//导出 
			 			attr:{
							queryForm: "#car-form-carListQuery",
					 		tn: 't_base_logistics_car',
					 		name:'车辆档案列表'
						}
		 			},
		 			</security:authorize>
					{}
	 			],
	 			model:'base',
				type:'multipleList',
				tab:'车辆档案列表',
				datagrid:'car-table-list',
				tname:'t_base_logistics_car',
				id:''
     		});
     		
     		$("#car-table-list").datagrid({ 
     			authority:carListColJson,
	  	 		frozenColumns:[[{field:'ck',checkbox:true}]],
				columns:carListColJson.common,
	  	 		fit:true,
	  	 		method:'post',
	  	 		title:'车辆档案列表',
	  	 		rownumbers:true,
	  	 		pagination:true,
	  	 		idField:'id',
	  	 		singleSelect:false,
	  	 		toolbar:'#car-query-showCarList',
			    url:'basefiles/getCarListPage.do',
		    	onSelect:function(rowIndex, rowData){
		    		$(this).datagrid('checkRow',rowIndex);
		    		refreshLayout('车辆档案【查看】','basefiles/showCarViewPage.do?id='+rowData.id,'view');
		    	},
		    	onUnselect:function(rowIndex, rowData){
		    		$(this).datagrid('uncheckRow',rowIndex);
		    		$("#logistics-layout-car").layout('remove','center');
		    		$("#car-button-layout").buttonWidget("initButtonType","multipleList");
		    		$("#car-button-layout").buttonWidget("disableButton","button-edit");
		    		$("#car-button-layout").buttonWidget("disableButton","button-hold");
		    		$("#car-button-layout").buttonWidget("disableButton","button-save");
		    		$("#car-button-layout").buttonWidget("disableButton","button-giveup");
		    		$("#car-button-layout").buttonWidget("disableButton","button-copy");
		    	},
		    	onLoadSuccess: function () {
	                $("#car-table-list").datagrid('selectRecord',$("#wares-input-carId").val());
	            }
			}).datagrid("columnMoving");
			
			//回车事件
			controlQueryAndResetByKey("car-query-queryCarList","car-query-reloadCarlList");
			
			//查询
			$("#car-query-queryCarList").click(function(){
	      		var queryJSON = $("#car-form-carListQuery").serializeJSON();
	      		$("#car-table-list").datagrid("load",queryJSON);
			});
			
			//重置按钮
			$("#car-query-reloadCarlList").click(function(){
				$("#car-form-carListQuery")[0].reset();
				$("#car-widget-id").widget('clear');
				$("#car-table-list").datagrid("load",{});
				
			});
     	});
    </script>
	</body>
</html>
