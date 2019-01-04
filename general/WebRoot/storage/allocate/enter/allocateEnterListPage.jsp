<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>调拨入库单列表页面</title>
    <%@include file="/include.jsp" %>   
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<div data-options="region:'north',border:false">
    		<div class="buttonBG" id="storage-buttons-allocateEnterPage" style="height:26px;"></div>
    	</div>
    	<div data-options="region:'center'">
    		<table id="storage-datagrid-allocateEnterPage" data-options="border:false"></table>
    	</div>
    </div>
    <div id="storage-datagrid-toolbar-allocateEnterPage">
    	<form action="" id="storage-form-query-allocateEnterPage" method="post">
    		<table>
    			<tr>
    				<td>编号:</td>
    				<td><input type="text" name="id" style="width: 180px;"/></td>
    				<td>调出仓库:</td>
    				<td><input id="storage-query-outstorageid" type="text" name="outstorageid" style="width: 180px;"/></td>
    				<td>调入仓库:</td>
    				<td><input id="storage-query-enterstorageid" type="text" name="enterstorageid" style="width: 180px;"/></td>
    			</tr>
    			<tr>
    				<td>业务日期:</td>
    				<td><input type="text" name="businessdate1" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /> 到 <input type="text" name="businessdate2" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
    				<td colspan="2">
    					<a href="javaScript:void(0);" id="storage-queay-allocateEnter" iconCls="icon-search" >查询</a>
						<a href="javaScript:void(0);" id="storage-reload-allocateEnter" iconCls="icon-search">重置</a>
						<span id="storage-query-advanced-allocateEnter"></span>
    				</td>
    			</tr>
    		</table>
    	</form>
    </div>
    <div id="storage-panel-relation-upper"></div>
    <div id="storage-panel-sourceQueryPage"></div>
     <script type="text/javascript">
    	$(function(){
    		//按钮
			$("#storage-buttons-allocateEnterPage").buttonWidget({
				initButton:[
					{},
					<security:authorize url="/storage/allocateEnterEditPage.do">
					{
						type: 'button-edit',
						handler: function(){
							var con = $("#storage-datagrid-allocateEnterPage").datagrid('getSelected');
							if(con == null){
								$.messager.alert("提醒","请选择一条记录");
								return false;
							}	
							top.addOrUpdateTab('storage/showAllocateEnterEditPage.do?id='+ con.id, "调拨入库单");
						}
					},
					</security:authorize>
					<security:authorize url="/storage/allocateEnterViewPage.do">
					{
						type: 'button-view',
						handler: function(){
							var con = $("#storage-datagrid-allocateEnterPage").datagrid('getSelected');
							if(con == null){
								$.messager.alert("提醒","请选择一条记录");
								return false;
							}	
							top.addOrUpdateTab('storage/showAllocateEnterViewPage.do?id='+ con.id, "调拨入库单");
						}
					},
					</security:authorize>
					<security:authorize url="/storage/allocateEnterRelation.do">
					{
						type: 'button-relation',
						button:[
							{},
							<security:authorize url="/storage/showAllocateEnterRelationUpperPage.do">
							{
								type: 'relation-upper',
								handler: function(){
									$("#storage-panel-relation-upper").dialog({
										href:"storage/showAllocateEnterRelationUpperPage.do",
										title:"上游单据查询",
									    closed:false,
										modal:true,
										cache:false,
									    width:500,
									    height:300,
									    buttons:[{
													text:'查询',
													handler:function(){
														var queryJSON = $("#storage-form-query-dispatchBill").serializeJSON();
														$("#storage-panel-relation-upper").dialog('close', true);
														var type = $("#storage-buttons-purchaseRejectOutPage").buttonWidget("getOperType");
														$("#storage-panel-sourceQueryPage").dialog({
															title:'调拨出库单列表',
															fit:true,
															closed:false,
															modal:true,
															cache:false,
															maximizable:true,
															resizable:true,
															href:'storage/showAllocateEnterSourceListPage.do',
															buttons:[{
						    											text:'确定',
																		handler: function(){
																			var dispatchbill = $("#storage-orderDatagrid-dispatchBillSourcePage").datagrid('getSelected');
																			if(dispatchbill == null){
																				$.messager.alert("提醒","请选择一条订单记录");
																				return false;
																			}
																			$("#storage-panel-sourceQueryPage").dialog('close', true);
																			loading("提交中..");
																			$.ajax({   
																	            url :'storage/addAllocateEnterByRefer.do',
																	            type:'post',
																	            dataType:'json',
																	            data:{id:dispatchbill.id},
																	            success:function(json){
																	            	loaded();
																	            	if(json.flag){
																	            		$.messager.alert("提醒","生成成功");
																	            		top.addOrUpdateTab('storage/showAllocateEnterViewPage.do?id='+ json.id, "调拨入库单");
																					}else{
																						$.messager.alert("提醒","生成失败");
																					}
																	            }
																	        });
																		}
																  }],
															onLoad:function(){
																$("#storage-orderDatagrid-dispatchBillSourcePage").datagrid({ 
																	columns:[[
																				{field:'id',title:'编号',width:125,sortable:true},
																				  {field:'businessdate',title:'业务日期',width:80,sortable:true},
																				  {field:'outstorageid',title:'调出仓库',width:80,sortable:true,
																				  	formatter:function(value,rowData,rowIndex){
																		        		return rowData.outstoragename;
																		        	}
																				  },
																				  {field:'enterstorageid',title:'调入仓库',width:80,sortable:true,
																				  	formatter:function(value,rowData,rowIndex){
																		        		return rowData.enterstoragename;
																		        	}
																				  },
																				  {field:'sourcetype',title:'来源类型',width:90,sortable:true,
																				  	formatter:function(value,rowData,rowIndex){
																		        		return getSysCodeName("allocateNotice-sourcetype",value);
																		        	}
																				  },
																				  {field:'addusername',title:'制单人',width:80,sortable:true},
																				  {field:'addtime',title:'制单时间',width:80,sortable:true},
																				  {field:'auditusername',title:'审核人',width:80,sortable:true},
																				  {field:'audittime',title:'审核时间',width:80,sortable:true},
																				  {field:'stopusername',title:'中止人',width:80,hidden:true,sortable:true},
																				  {field:'stoptime',title:'中止时间',width:80,hidden:true,sortable:true},
																				  {field:'status',title:'状态',width:60,sortable:true,
																				  	formatter:function(value,rowData,rowIndex){
																		        		return getSysCodeName("status",value);
																		        	}
																				  },
																				  {field:'remark',title:'备注',width:80,sortable:true}
																			]],
															 		fit:true,
															 		method:'post',
															 		rownumbers:true,
															 		pagination:true,
															 		idField:'id',
															 		singleSelect:true,
																	fitColumns:true,
																   	url:'storage/showAllocateOutList.do',
																   	queryParams: queryJSON,
																    onClickRow:function(index, data){
																		$("#storage-detailDatagrid-dispatchBillSourcePage").datagrid({
																			url:'storage/showAllocateOutDetailList.do',
																  			queryParams:{
																  				id: data.id
																  			}
																		});
															    	}
																});
															}
														});
													}
												}]
									});
								}
							},
							</security:authorize>
							{}
						]
					},
					</security:authorize>
					<security:authorize url="/storage/allocateEnterImport.do">
					{
						type: 'button-import',
						attr: {
						
						}
					},
					</security:authorize>
					<security:authorize url="/storage/allocateEnterExport.do">
					{
						type: 'button-export',
						attr: {
						
						}
					},
					</security:authorize>
					{}
				],
				model: 'bill',
				type: 'list',
				tname: 't_storage_allocate_enter'
			});
			var allocateEnterJson = $("#storage-datagrid-allocateEnterPage").createGridColumnLoad({
				name :'t_storage_allocate_enter',
				frozenCol : [[
			    			]],
				commonCol : [[
							  {field:'id',title:'编号',width:125,sortable:true},
							  {field:'businessdate',title:'业务日期',width:80,sortable:true},
							  {field:'enterstorageid',title:'调入仓库',width:80,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return rowData.enterstoragename;
					        	}
							  },
							  {field:'outstorageid',title:'调出仓库',width:80,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return rowData.outstoragename;
					        	}
							  },
							  {field:'sourcetype',title:'来源类型',width:90,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return getSysCodeName("allocateEnter-sourcetype",value);
					        	}
							  },
							  {field:'sourceid',title:'来源单据编号',width:130,sortable:true},
							  {field:'addusername',title:'制单人',width:80,sortable:true},
							  {field:'addtime',title:'制单时间',width:80,sortable:true},
							  {field:'auditusername',title:'审核人',width:80,sortable:true},
							  {field:'audittime',title:'审核时间',width:80,sortable:true},
							  {field:'stopusername',title:'中止人',width:80,hidden:true,sortable:true},
							  {field:'stoptime',title:'中止时间',width:80,hidden:true,sortable:true},
							  {field:'status',title:'状态',width:60,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return getSysCodeName("status",value);
					        	}
							  },
							  {field:'remark',title:'备注',width:80,sortable:true}
				             ]]
			});
			$("#storage-datagrid-allocateEnterPage").datagrid({ 
		 		authority:allocateEnterJson,
		 		frozenColumns: allocateEnterJson.frozen,
				columns:allocateEnterJson.common,
		 		fit:true,
		 		title:"调拨入库单列表",
		 		method:'post',
		 		rownumbers:true,
		 		pagination:true,
		 		idField:'id',
		 		sortName:'id',
		 		sortOrder:'desc',
		 		singleSelect:true,
				url: 'storage/showAllocateEnterList.do',
				toolbar:'#storage-datagrid-toolbar-allocateEnterPage',
				onDblClickRow:function(rowIndex, rowData){
					top.addOrUpdateTab('storage/showAllocateEnterViewPage.do?id='+ rowData.id, "调拨入库单");
				}
			}).datagrid("columnMoving");
			$("#storage-query-enterstorageid").widget({
				name:'t_storage_allocate_enter',
	    		width:180,
				col:'enterstorageid',
				view:true,
				singleSelect:true
			});
			$("#storage-query-outstorageid").widget({
				name:'t_storage_allocate_enter',
	    		width:180,
				col:'outstorageid',
				view:true,
				singleSelect:true
			});
			//通用查询组建调用
			$("#storage-query-advanced-allocateEnter").advancedQuery({
				//查询针对的表
		 		name:'t_storage_allocate_enter',
		 		//查询针对的表格id
		 		datagrid:'storage-datagrid-allocateEnterPage',
		 		plain:true
			});
			
			//回车事件
			controlQueryAndResetByKey("storage-queay-allocateEnter","storage-reload-allocateEnter");
			
			//查询
			$("#storage-queay-allocateEnter").click(function(){
				//把form表单的name序列化成JSON对象
	       		var queryJSON = $("#storage-form-query-allocateEnterPage").serializeJSON();
	       		$("#storage-datagrid-allocateEnterPage").datagrid("load",queryJSON);
			});
			//重置
			$("#storage-reload-allocateEnter").click(function(){
				$("#storage-query-outstorageid").widget("clear");
				$("#storage-query-enterstorageid").widget("clear");
				$("#storage-form-query-allocateEnterPage")[0].reset();
	       		$("#storage-datagrid-allocateEnterPage").datagrid("load",{});
			});
    	});
    </script>
  </body>
</html>
