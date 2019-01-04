<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>计量单位首页</title>
    <%@include file="/include.jsp" %>   
    <script type="text/javascript" src="js/jquery.excel.js"></script>
  </head>
  
  <body>
    <div class="easyui-panel" data-options="fit:true">
      	<div class="easyui-layout" data-options="fit:true" id="wares-layout-meteringUnit">
      		<div data-options="region:'north'" style="height: 30px;overflow: hidden">
      			<div class="buttonBG" id="meteringUnit-button-layout"></div>
				<input type="hidden" id="meteringUnit-opera"/>
      		</div>
      		<div data-options="region:'west',split:true" style="width:409px;height: auto;">
				<div style="height: 1px;"></div>
		     	<table id="meteringUnit-table-list"></table>
		    </div>
		    <div data-options="region:'center',border:true">
		    	<div id="meteringUnit-div-meteringUnitInfo"></div>
			</div>
      	</div>
    </div>
     <script type="text/javascript">
     function refreshLayout(title, url){
   		$("#wares-layout-meteringUnit").layout('remove','center').layout('add',{
			region: 'center',  
		    title: title,
		    href:url
		});
   	}
    	
     var meteringUnit_AjaxConn = function (Data, Action) {
	    var MyAjax = $.ajax({
	        type: 'post',
	        cache: false,
	        url: Action,
	        data: Data,
	        async: false,
	        beforeSend: function(){ 
	        	loading("提交中..");
			}, 
	        success:function(data){
	        	loaded();
	        }
	    })
	    return MyAjax.responseText;
	}
	
	//根据初始的列与用户保存的列生成以及字段权限生成新的列
     var meteringUnitListColJson=$("#meteringUnit-table-list").createGridColumnLoad({
     	name:'base_goods_meteringunit',
     	frozenCol:[[]],
     	commonCol:[[
			{field:'id',title:'编码',width:100,sortable:true},
			{field:'name',title:'名称',width:100,sortable:true},  
			{field:'state',title:'状态',width:60,sortable:true,
				formatter:function(val,rowData,rowIndex){
					return rowData.stateName;
				}
			},
			{field:'remark',title:'备注',width:130,sortable:true},
			{field:'adduserid',title:'建档人',width:80,sortable:true,hidden:true},
			{field:'adddeptid',title:'建档部门',width:80,sortable:true,hidden:true},
			{field:'addtime',title:'建档时间',width:80,sortable:true,hidden:true},
			{field:'modifyuserid',title:'最后修改人',width:80,sortable:true,hidden:true},
			{field:'modifytime',title:'最后修改时间',width:80,sortable:true,hidden:true},
			{field:'openuserid',title:'启用人',width:80,sortable:true,hidden:true},
			{field:'opentime',title:'启用时间',width:80,sortable:true,hidden:true},
			{field:'closeuserid',title:'禁用人',width:80,sortable:true,hidden:true},
			{field:'closetime',title:'禁用时间',width:80,sortable:true,hidden:true}
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
     		$("#meteringUnit-button-layout").buttonWidget({
     			//初始默认按钮 根据type对应按钮事件
				initButton:[
					{},
					<security:authorize url="/basefiles/meteringUnitAddBtn.do">
					{
						type:'button-add',//新增 
						handler:function(){
							refreshLayout('计量单位【新增】', 'basefiles/showMeteringUnitAddPage.do');
							$("#meteringUnit-opera").attr("value","add");
						}
					},
					</security:authorize>
					<security:authorize url="/basefiles/meteringUnitEditBtn.do">
		 			{
			 			type:'button-edit',//修改 
			 			handler:function(){
							var meteringUnit=$("#meteringUnit-table-list").datagrid('getSelected');
				  			if(meteringUnit==null){
				  				$.messager.alert("提醒","请选择相应的计量单位!");
				  				return false;
				  			}
				  			var flag = isDoLockData(meteringUnit.id,"t_base_goods_meteringunit");
							if(!flag){
								$.messager.alert("警告","该数据正在被其他人操作，暂不能修改！");
								return false;
							}
							refreshLayout('计量单位【修改】', 'basefiles/showMeteringUnitEditPage.do?id='+meteringUnit.id);
							$("#meteringUnit-opera").attr("value","edit");
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/meteringUnitHoldBtn.do">
		 			{
		 				type:'button-hold',//暂存
		 				handler:function(){
			 				//获取当前按钮操作的状态。add表示正处于新增页面风格edit表示正处于修改页面风格
			 				var type = $("#meteringUnit-button-layout").buttonWidget("getOperType");
			 				if(type=="add"){
					    		$.messager.confirm("提醒","是否暂存新增计量单位?",function(r){
			 						if(r){
					 					addMeteringUnitHold();//暂存新增计量单位 
			 						}
			 					});
			 				}
			 				else{
			 					$.messager.confirm("提醒","是否暂存修改计量单位?",function(r){
			 						if(r){
										editMeteringUnitHold();//暂存修改计量单位
									}
			 					});
			 				}
			 			}
			 		},
			 		</security:authorize>
					<security:authorize url="/basefiles/meteringUnitSaveBtn.do">
		 			{
			 			type:'button-save',//保存
			 			handler:function(){
			 				//获取当前按钮操作的状态。add表示正处于新增页面风格edit表示正处于修改页面风格
			 				var type = $("#meteringUnit-button-layout").buttonWidget("getOperType");
			 				if(type=="add"){
			 					$.messager.confirm("提醒","是否保存新增计量单位?",function(r){
			 						if(r){
			 							addMeteringUnitSave();//保存新增计量单位
			 						}
			 					});
			 				}
			 				else{
			 					$.messager.confirm("提醒","是否保存修改计量单位?",function(r){
			 						if(r){
			 							editMeteringUnitSave();//保存修改计量单位 
			 						}
			 					});
			 				}
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/meteringUnitDelBtn.do">
		 			{
			 			type:'button-delete',//删除
			 			handler:function(){
			 				var meteringUnit=$("#meteringUnit-table-list").datagrid('getSelected');
				  			if(meteringUnit==null){
				  				$.messager.alert("提醒","请选择相应的计量单位!");
				  				return false;
				  			}
				  			var flag = isLockData(meteringUnit.id,"t_base_goods_meteringunit");
							if(flag){
								$.messager.alert("警告","该数据正在被其他人操作，暂不能删除！");
								return false;
							}
				  			$.messager.confirm("提醒","是否确认删除计量单位?",function(r){
				  				if(r){
				  					loading("删除中..");
				  					var ret = meteringUnit_AjaxConn({id:meteringUnit.id},'basefiles/deleteMeteringUnit.do');
									var retJson = $.parseJSON(ret);
									if(retJson.retFlag){
										$("#meteringUnit-table-list").datagrid('reload');
										$("#meteringUnit-table-list").datagrid('clearSelections');
										$.messager.alert("提醒","计量单位删除成功!");
										$("#wares-layout-meteringUnit").layout('remove','center');
									}
									else{
										$.messager.alert("提醒",""+retJson.Mes+"删除失败!");
									}
				  				}
				  			});
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/meteringUnitCopyBtn.do">
		 			{
			 			type:'button-copy',//复制
			 			handler:function(){
			 				var meteringUnit=$("#meteringUnit-table-list").datagrid('getSelected');
				  			if(meteringUnit==null){
				  				$.messager.alert("提醒","请选择相应的计量单位!");
				  				return false;
				  			}
				  			refreshLayout('计量单位【新增】', 'basefiles/showMeteringUnitCopyPage.do?id='+meteringUnit.id);
							$("#meteringUnit-opera").attr("value","add");
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/meteringUnitEnableBtn.do">
		 			{
			 			type:'button-open',//启用 
			 			handler:function(){
			 				var meteringUnit=$("#meteringUnit-table-list").datagrid('getSelected');
				  			if(meteringUnit==null){
				  				$.messager.alert("提醒","请选择相应的计量单位!");
				  				return false;
				  			}
				  			$.messager.confirm("提醒","是否确认启用计量单位?",function(r){
				  				if(r){
					  				var ret = meteringUnit_AjaxConn({id:meteringUnit.id},'basefiles/enableMeteringUnit.do');
									var retJson = $.parseJSON(ret);
									if(retJson.flag){
										$("#meteringUnit-table-list").datagrid('reload');
										$("#meteringUnit-div-meteringUnitInfo").panel('refresh','basefiles/showMeteringUnitInfoPage.do?id='+meteringUnit.id);
										$.messager.alert("提醒","计量单位启用成功!");
										$("#meteringUnit-button-layout").buttonWidget("setButtonType","1");
									}
									else{
										$.messager.alert("提醒","计量单位启用失败!");
									}
				  				}
				  			});
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/meteringUnitDisableBtn.do">
		 			{
			 			type:'button-close',//禁用 
			 			handler:function(){
			 				var meteringUnit=$("#meteringUnit-table-list").datagrid('getSelected');
				  			if(meteringUnit==null){
				  				$.messager.alert("提醒","请选择相应的计量单位!");
				  				return false;
				  			}
				  			$.messager.confirm("提醒","是否确认禁用计量单位?",function(r){
				  				if(r){
				  					var ret = meteringUnit_AjaxConn({id:meteringUnit.id},'basefiles/disableMeteringUnit.do');
									var retJson = $.parseJSON(ret);
									if(retJson.flag){
										$("#meteringUnit-table-list").datagrid('reload');
										$("#meteringUnit-div-meteringUnitInfo").panel('refresh','basefiles/showMeteringUnitInfoPage.do?id='+meteringUnit.id);
										$.messager.alert("提醒","计量单位禁用成功!");
										$("#meteringUnit-button-layout").buttonWidget("setButtonType","0");
									}
									else{
										$.messager.alert("提醒",""+retJson.Mes+"计量单位禁用失败!");
									}
				  				}
				  			});
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/meteringUnitImportBtn.do">
		 			{
			 			type:'button-import',//导入
			 			attr:{
			 				clazz: "goodsService", //spring中注入的类名
					 		method: "addDRMeteringUnitInfo", //插入数据库的方法
					 		tn: "t_base_goods_meteringunit", //表名
				            module: 'basefiles', //模块名，
				            majorkey:'id',
					 		pojo: "MeteringUnit", //实体类名，将和模块名组合成com.hd.agent.basefiles.model.WaresClass。
							onClose: function(){ //导入成功后窗口关闭时操作，
						         //$("#personnel-table-personnelList").datagrid('clearSelections');	  
						         $("#meteringUnit-table-list").datagrid('reload');	//更新列表	                                                                          
							}
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/meteringUnitExportBtn.do">
		 			{
			 			type:'button-export',//导出 
			 			attr:{
							queryForm: "", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
					 		tn: 't_base_goods_meteringunit', //表名
					 		name:'计量单位列表'
						}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/meteringUnitPrintBtn.do">
		 			{
			 			type:'button-preview',//打印预览
			 			handler:function(){
			 				alert("print");
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/meteringUnitPrintBtn.do">
		 			{
			 			type:'button-print',//打印 
			 			handler:function(){
			 				alert("print");
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/brandGiveupBtn.do">
		 			{
		 				type:'button-giveup',//放弃 
		 				handler:function(){
			 				var type = $("#meteringUnit-button-layout").buttonWidget("getOperType");
			 				if(type=="add"){
			 					$("#meteringUnit-button-layout").buttonWidget("initButtonType","list");
			 					$("#meteringUnit-div-meteringUnitInfo").panel("close");
			 				}else if(type=="edit"){
			 					var meteringUnit=$("#meteringUnit-table-list").datagrid('getSelected');
			 					var id = meteringUnit.id;
			 					var state = meteringUnit.state;
			 					$.ajax({   
						            url :'system/lock/unLockData.do',
						            type:'post',
						            data:{id:id,tname:'t_base_goods_meteringunit'},
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
			 					//var WCjob=document.getElementById("waresClass-iframe").contentWindow.$("#waresClass-table-list").datagrid('getSelected');
				 				refreshLayout('计量单位【详情】', 'basefiles/showMeteringUnitInfoPage.do?id='+id);
								$("#meteringUnit-opera").attr("value","view");
			 				}
			 			}
		 			},
		 			</security:authorize>
		 			{}
	 			],
	 			model:'base',
				type:'list',
				tname:'t_base_goods_meteringunit',
				id:''
     		});
     		
     		$("#meteringUnit-table-list").datagrid({ 
     			authority:meteringUnitListColJson,
	  	 		frozenColumns:[[]],
				columns:meteringUnitListColJson.common,
	  	 		fit:true,
	  	 		method:'post',
	  	 		title:'单位计量列表',
	  	 		rownumbers:true,
	  	 		pagination:true,
	  	 		idField:'id',
	  	 		singleSelect:true,
			    url:'basefiles/getMeteringUnitList.do',
		    	onClickRow:function(rowIndex, rowData){
		    		refreshLayout('计量单位【详情】', 'basefiles/showMeteringUnitInfoPage.do?id='+rowData.id);

					$("#meteringUnit-button-layout").buttonWidget("initButtonType","view");
					$("#meteringUnit-button-layout").buttonWidget("setDataID",{id:rowData.id,state:rowData.state,type:'view'});
					
		    	}
			}).datagrid("columnMoving");
     	});
     </script>
  </body>
</html>
