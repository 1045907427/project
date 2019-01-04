<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags"
	prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title>物流</title>
		<%@include file="/include.jsp"%>
	</head>

	<body>
    <div class="easyui-layout" data-options="fit:true,border:true">
        <div data-options="region:'north',border:false">
            <div class="buttonBG" id="lineInfo-button-div"></div>
            <input id="logistics-id-hdClassId" type="hidden" />
            <input id="logistics-pid-hdClassPid" type="hidden" />
        </div>
        <!--
    <div title="线路分类" data-options="region:'west',split:true" style="width:200px;">
        <div id="logistics-tree-Class" class="ztree"></div>
    </div>
     -->
        <div title="线路列表" data-options="region:'center'">
            <table id="logistics-table-logisticsLineInfoList"></table>
            <div id="logistics-query-showlineInfoList" style="padding:2px;height:auto">
                <form action="" id="logistics-form-lineInfoListQuery" method="post">
                    <table class="querytable">
                        <tr>
                            <td>
                                编号:
                            </td>
                            <td>
                                <input type="text" name="id" style="width: 100px" />
                                <input id="line-widget-id" name="defaultsort" type="hidden" />
                            </td>
                            <td>
                                名称:
                            </td>
                            <td>
                                <input id="line-input-name" type="text" name="name" style="width: 200px" />
                            </td>
                            <td colspan="2">
                                <a href="javaScript:void(0);" id="logistics-query-querylineInfoList" class="button-qr">查询</a>
                                <a href="javaScript:void(0);" id="logistics-query-reloadlineInfoList" class="button-qr">重置</a>
                                <span id="logisticsLineInfo-query-advanced"></span>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
    </div>


	<script type="text/javascript">
    
    var lineInfo_AjaxConn = function (Data, Action) {
		    var MyAjax = $.ajax({
		        type: 'post',
		        cache: false,
		        url: Action,
		        data: Data,
		        async: false,
		        ajaxSend:function(){
		        	loading("提交中..");
		        },
		        success:function(data){
		        	loaded();
		        }
		    })
		    return MyAjax.responseText;
		}
		
    
   		 //根据初始的列与用户保存的列生成以及字段权限生成新的列
	     var lineInfoListColJson=$("#logistics-table-logisticsLineInfoList").createGridColumnLoad({
	     	name:'base_logistics_line',
	     	frozenCol:[[]],
	     	commonCol:[[{field:'id',title:'编码',width:50,sortable:true},
	     				{field:'name',title:'线路名称',width:150,sortable:true},
	     				{field:'salesarea',title:'所属地区',width:95,sortable:true},
	     				{field:'totalcustomers',title:'区域客户家数',width:100,sortable:true
	     				},
	     				{field:'distance',title:'线路暂估路程',width:100,isShow:true,sortable:true
	     				},
						{field:'linetype',title:'线路复杂程度',width:100,sortable:true
						},
						{field:'carid',title:'默认车辆',width:80,sortable:true,
							formatter:function(val,rowData,rowIndex){
								return rowData.carname;
							}
						},
						{field:'carsubsidy',title:'出车补助',width:80,sortable:true,
							formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						}, 
						{field:'carallowance',title:'出车津贴',width:80,sortable:true,
							formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						},
						{field:'state',title:'状态',width:50,sortable:true,
	     					formatter:function(val,rowData,rowIndex){
								return rowData.stateName;
							}
	     				},
						{field:'remark',title:'备注',width:100,sortable:true},
						{field:'adduserid',title:'建档人用户编号',width:60,hidden:true,sortable:true},
						{field:'addusername',title:'建档人',width:60,hidden:true,sortable:true},
						{field:'adddeptid',title:'建档人部门编号',width:80,hidden:true,sortable:true},
						{field:'adddeptname',title:'建档部门',width:80,hidden:true,sortable:true},
						{field:'addtime',title:'建档时间',width:115,hidden:true,sortable:true},
						{field:'modifyuserid',title:'修改人用户编号',width:60,hidden:true,sortable:true},
						{field:'modifyusername',title:'修改人',width:60,hidden:true,sortable:true},
						{field:'modifytime',title:'最后修改时间',width:115,hidden:true,sortable:true},
						{field:'openuserid',title:'启用人用户编号',width:60,hidden:true,sortable:true},
						{field:'openusername',title:'启用人',width:60,hidden:true,sortable:true},
						{field:'opentime',title:'启用时间',width:115,hidden:true,sortable:true},
						{field:'closeuserid',title:'禁用人用户编号',width:60,hidden:true,sortable:true},
						{field:'closeusername',title:'禁用人',width:60,hidden:true,sortable:true},
						{field:'closetime',title:'禁用时间',width:115,hidden:true,sortable:true}
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
    	
    	var queryJSON = $("#logistics-form-lineInfoListQuery").serializeJSON();
	     	
    	$("#logistics-form-lineInfoListQuery").show();
    
   		$('#logistics-table-logisticsLineInfoList').datagrid({
 			authority:lineInfoListColJson,
 	 		frozenColumns:[[{field:'lineInfock',checkbox:true}]],
			columns:lineInfoListColJson.common,
		    fit:true,
			title:'',
			toolbar:'#logistics-query-showlineInfoList',
			method:'post',
			rownumbers:true,
			pagination:true,
			idField:'id',
			pageSize:100,
			singleSelect:false,
			checkOnSelect:true,
		 	selectOnCheck:true,
			queryParams:queryJSON,
			url:'basefiles/lineInfoListPage.do',
			onDblClickRow:function(rowIndex, rowData){
				<security:authorize url="/basefiles/lineInfoViewBtn.do">
					top.addOrUpdateTab('basefiles/showLineInfoPage.do?type=view&listType=${listtype}&id='+rowData.id+'&state='+rowData.state,'线路档案');
				</security:authorize>
			}
		}).datagrid("columnMoving");
    
    	//页面刷新
	   	function panelRefresh(url,title,type){
	   		$("#wares-div-lineInfo").panel({
				href:url,
				title:title,
			    cache:false,
			    maximized:true,
			    border:false,
			    loadingMessage:'数据加载中...'
			});
			$("#lineInfo-opera").attr("value",type);
	   	}
    
    	$(function(){
    	
    		$("#line-widget-id").widget({
     			width:120,
				name:'t_base_logistics_line',
				col:'id',
				singleSelect:true,
				onlyLeafCheck:false
     		});
    	
    		//回车事件
			controlQueryAndResetByKey("logistics-query-querylineInfoList","logistics-query-reloadlineInfoList");
			
			//查询
			$("#logistics-query-querylineInfoList").click(function(){
	      		var queryJSON = $("#logistics-form-lineInfoListQuery").serializeJSON();
	      		$("#logistics-table-logisticsLineInfoList").datagrid("load",queryJSON);
			});
			
			//重置按钮
			$("#logistics-query-reloadlineInfoList").click(function(){
				$("#logistics-form-lineInfoListQuery")[0].reset();
				$("#line-widget-id").widget('clear');
				$("#logistics-table-logisticsLineInfoList").datagrid("load",{});
				
			});
    	
			//加载按钮
			$("#lineInfo-button-div").buttonWidget({
				//初始默认按钮 根据type对应按钮事件
				initButton:[
					{},
					<security:authorize url="/basefiles/logisticsLineInfoAddBtn.do">
						{
							type:'button-add',
							handler:function(){
								top.addOrUpdateTab('basefiles/showLineInfoPage.do','线路档案','add');
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/logisticsLineInfoEditBtn.do">
			 			{
				 			type:'button-edit',
				 			handler:function(){
					 			var lineRow=$("#logistics-table-logisticsLineInfoList").datagrid('getSelected');
								if(lineRow == null){
									$.messager.alert("提醒","请选择线路!");
									return false;
								}
								var flag = isDoLockData(lineRow.id,"t_base_logistics_line");
				 				if(!flag){
				 					$.messager.alert("警告","该数据正在被其他人操作，暂不能修改！");
				 					return false;
				 				}
				 				top.addOrUpdateTab('basefiles/showLineInfoPage.do?type=edit&listType=${listtype}&id='+lineRow.id+'&state='+lineRow.state,'线路档案','edit');
				 			}
			 			},
		 			</security:authorize>
		 			/*
					<security:authorize url="/basefiles/logisticsLineInfoSaveBtn.do">
			 			{
				 			type:'button-save',
				 			handler:function(){
				 				
				 			}
			 			},
		 			</security:authorize>
		 			*/
					<security:authorize url="/basefiles/logisticsLineInfoDeleteBtn.do">
			 			{
				 			type:'button-delete',
				 			handler:function(){
					 			var lineRows = $("#logistics-table-logisticsLineInfoList").datagrid('getChecked');
								if(lineRows.length == 0){
									$.messager.alert("提醒","请选择线路!");
									return false;
								}
								
								for(var i=0;i<lineRows.length;i++){
									var flag = isLockData(lineRows[i].id,"t_base_logistics_line");
					 				if(flag){
					 					$.messager.alert("警告","该数据正在被其他人操作，暂不能删除！");
					 					return false;
					 				}
								}
				 				
				 				$.messager.confirm("提醒","是否确定删除线路档案?",function(r){
				 					if(r){
				 						var idStr = "";
										for(var i=0;i<lineRows.length;i++){
											idStr += lineRows[i].id + ",";
										}
				 						var ret = lineInfo_AjaxConn({idStr:idStr},'basefiles/deleteLineInfo.do');
										var retJSON = $.parseJSON(ret);
										if(retJSON.flag){
											$.messager.alert("提醒",""+retJSON.userNum+"条记录被引用,不允许删除;<br/>删除成功"+retJSON.num+"条记录;");
											if (top.$('#tt').tabs('exists','线路档案列表')){
							    				tabsWindow('线路档案列表').$("#logistics-table-logisticsLineInfoList").datagrid('reload');
							    			}
							    			//var object = $("#lineInfo-div-button").buttonWidget("removeData",id);
							    			//if(null != object){
							    			//	panelRefresh('basefiles/showLineInfoViewPage.do?id='+object.id,' 线路档案【查看】','view');
							    			//}
							    			//else{
							    				top.closeTab('线路档案');
							    			//}
										}
										else{
											$.messager.alert("提醒",""+retJSON.userNum+"条记录被引用,不允许删除;<br/>删除失败"+retJSON.num+"条记录;");
										}
				 					}
				 				});
				 			}
			 			},
		 			</security:authorize>
		 			<security:authorize url="/basefiles/logisticsLineInfoCopyBtn.do">
		 			{
			 			type:'button-copy',//复制
			 			handler:function(){
			 				var lineRow=$("#logistics-table-logisticsLineInfoList").datagrid('getSelected');
							if(lineRow == null){
								$.messager.alert("提醒","请选择线路!");
								return false;
							}
							top.addOrUpdateTab('basefiles/showLineInfoPage.do?type=copy&id='+lineRow.id,'线路档案','copy');
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/logisticsLineInfoEnableBtn.do">
			 			{
				 			type:'button-open',
				 			handler:function(){
				 				var lineRows = $("#logistics-table-logisticsLineInfoList").datagrid('getChecked');
								if(lineRows.length == 0){
									$.messager.alert("提醒","请勾选线路!");
									return false;
								}
								$.messager.confirm("提醒","是否确定启用线路档案?",function(r){
				 					if(r){
				 						var idStr = "";
										for(var i=0;i<lineRows.length;i++){
											idStr += lineRows[i].id + ",";
										}
										var ret = lineInfo_AjaxConn({idStr:idStr},'basefiles/enableLineInfos.do');
										var retJSON = $.parseJSON(ret);
										if(retJSON.flag){
											$.messager.alert("提醒","启用无效"+retJSON.invalidNum+"条记录;<br/>启用成功"+retJSON.num+"条记录;");
											$("#logistics-table-logisticsLineInfoList").datagrid('reload');
							    			
							    			//var id=$("#lineInfo-id-baseInfo").val();
							    			//panelRefresh('basefiles/showLineInfoViewPage.do?id='+id,'线路档案【查看】','view');
											//$("#wares-div-lineInfo").panel('refresh','basefiles/showLineInfoViewPage.do?id='+$("#lineInfo-id-baseInfo").val());
										}
										else{
											$.messager.alert("提醒","启用无效"+retJSON.invalidNum+"条记录;<br/>启用失败"+retJSON.num+"条记录;");
										}
				 					}
				 				});
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/logisticsLineInfoDisableBtn.do">
			 			{
				 			type:'button-close',
				 			handler:function(){
				 				var lineRows = $("#logistics-table-logisticsLineInfoList").datagrid('getChecked');
								if(lineRows.length == 0){
									$.messager.alert("提醒","请勾选线路!");
									return false;
								}
								$.messager.confirm("提醒","是否确定启用线路档案?",function(r){
				 					if(r){
				 						var idStr = "";
										for(var i=0;i<lineRows.length;i++){
											idStr += lineRows[i].id + ",";
										}
										var ret = lineInfo_AjaxConn({idStr:idStr},'basefiles/disableLineInfos.do');
										var retJSON = $.parseJSON(ret);
										if(retJSON.flag){
											$.messager.alert("提醒",""+retJSON.invalidNum+"条记录状态不允许禁用;<br/>禁用成功"+retJSON.num+"条记录;");
											$("#logistics-table-logisticsLineInfoList").datagrid('reload');
							    		}
										else{
											$.messager.alert("提醒",""+retJSON.invalidNum+"条记录状态不允许禁用;<br/>禁用失败"+retJSON.num+"条记录;");
										}
				 					}
				 				});
				 			}
			 			},
		 			</security:authorize>
		 			<security:authorize url="/basefiles/logisticsLineInfoImportBtn.do">
		 			{
			 			type:'button-import',//导入
			 			attr:{
			 				clazz: "logisticsService", //spring中注入的类名
					 		methodjson: {t_base_logistics_line:'addDRLogisticsLine',t_base_logistics_line_car:'addDRLogisticsLineCar',
					 					t_base_logistics_line_customer:'addDRLogisticsLineCustomer'}, //插入数据库的方法
					 		tnjson: {线路列表:'t_base_logistics_line',所属车辆:'t_base_logistics_line_car',线路客户:'t_base_logistics_line_customer'},//表名
				            module: 'basefiles', //模块名，
					 		pojojson: {t_base_logistics_line:'LogisticsLine',t_base_logistics_line_car:'LogisticsLineCar',
					 				t_base_logistics_line_customer:'LogisticsLineCustomer'},
							type:'importmore',
							importparam:'简化版:编码、名称、默认车辆必填',//参数描述
							majorkey:'id',
							childkey:'lineid',
							version:'1',
							maintn:'t_base_logistics_line',
							onClose: function(){ //导入成功后窗口关闭时操作，
						         $("#logistics-table-logisticsLineInfoList").datagrid('reload');	//更新列表	                                                                                        
							}
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/logisticsLineInfoExportBtn.do">
		 			{
			 			type:'button-export',//导出 
			 			attr:{
			 				datagrid: "#logistics-table-logisticsLineInfoList",
			 				queryForm: "#logistics-form-lineInfoListQuery", 
					 		tnstr:'t_base_logistics_line,t_base_logistics_line_car,t_base_logistics_line_customer',//表名
					 		tnjson: {t_base_logistics_line:'线路列表',t_base_logistics_line_car:'所属车辆',t_base_logistics_line_customer:'线路客户'},
					 		type:'exportmore',
					 		exportparam:'',//参数描述
					 		shortcutname:'goods',
					 		version:'1',//1不显示单选框，2显示单选框
					 		queryparam:'id,name',
					 		childkey:'lineid',
							maintn:'t_base_logistics_line',
					 		name:'线路档案列表'
			 			}
		 			},
		 			</security:authorize>
		 			{}
				],
				model:'base',
				type:'list',
				tname:'t_base_logistics_line'
			});
		});
		$("#lineInfo-button-div").buttonWidget("initButtonType","view");
    </script>
	</body>
</html>
