<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>调拨入库单操作页面</title>
    <%@include file="/include.jsp" %>   
  </head>
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<div data-options="region:'north',border:false">
    		<div class="buttonBG" id="storage-buttons-allocateEnterPage" style="height:26px;"></div>
    	</div>
    	<div data-options="region:'center',border:false">
    		<div id="storage-panel-allocateEnterPage"></div>
    	</div>
    </div>
    <div id="storage-panel-relation-upper"></div>
    <div id="storage-panel-sourceQueryPage"></div>
    <div id="workflow-addidea-dialog-page"></div>
    <input type="hidden" id="storage-hidden-billid"/>
    <script type="text/javascript">
    	//根据初始的列与用户保存的列生成以及字段权限生成新的列
		var tableColJson = $("#storage-datagrid-allocateEnterAddPage").createGridColumnLoad({
			name :'t_storage_allocate_out_detail',
			frozenCol : [[]],
			commonCol : [[ 
							{field:'goodsid',title:'商品编码',width:80},
	    					{field:'goodsname', title:'商品名称', width:100,aliascol:'goodsid',
	    						formatter:function(value,rowData,rowIndex){
						       		if(rowData.goodsInfo != null){
						       			return rowData.goodsInfo.name;
						       		}else{
						       			return "";
					       			}
						        }
	    					},
	    					{field:'brandName', title:'商品品牌',width:80,aliascol:'goodsid',
	    						formatter:function(value,rowData,rowIndex){
						       		if(rowData.goodsInfo != null){
						       			return rowData.goodsInfo.brandName;
						       		}else{
						       			return "";
						       		}
						        }
	    					},
	    					{field:'model', title:'规格型号',width:80,aliascol:'goodsid',
	    						formatter:function(value,rowData,rowIndex){
						       		if(rowData.goodsInfo != null){
						       			return rowData.goodsInfo.model;
						       		}else{
						       			return "";
						       		}
						        }
	    					},
	    					{field:'barcode', title:'条形码',width:85,aliascol:'goodsid',
	    						formatter:function(value,rowData,rowIndex){
						       		if(rowData.goodsInfo != null){
						       			return rowData.goodsInfo.barcode;
						       		}else{
						       			return "";
						       		}
						        }
	    					},
	    					{field:'unitname', title:'计量单位',width:70},
	    					{field:'unitnum', title:'数量',width:80,align:'right',
	    						formatter:function(value,row,index){
					        		return formatterBigNumNoLen(value);
						        }
					    	},
	    					{field:'auxunitid', title:'辅单位',width:60,
	    						formatter: function(value,row,index){
	    							return row.auxunitname;
	    						}
	    					},
	    					{field:'auxnumdetail', title:'辅单位数量',width:90,align:'right'},
	    					{field:'taxprice', title:'含税单价',width:60,align:'right',
	    						formatter:function(value,row,index){
					        		return formatterMoney(value);
						        }
					    	},
	    					{field:'taxamount', title:'含税金额',width:60,align:'right',
	    						formatter:function(value,row,index){
					        		return formatterMoney(value);
						        }
						    },
						    {field:'notaxprice', title:'无税单价',width:60,align:'right',hidden:true,
	    						formatter:function(value,row,index){
					        		return formatterMoney(value);
						        }
					    	},
	    					{field:'notaxamount', title:'无税金额',width:60,align:'right',hidden:true,
	    						formatter:function(value,row,index){
					        		return formatterMoney(value);
						        }
						    },
						    {field:'taxtypename', title:'税种',width:60,aliascol:'taxtype',align:'right',hidden:true},
						    {field:'tax', title:'税额',width:60,align:'right',hidden:true,
	    						formatter:function(value,row,index){
					        		return formatterMoney(value);
						        }
						    },
						    {field:'storagelocationid', title:'所属库位',width:100,
						    	formatter:function(value,row,index){
					        		return row.storagelocationname;
						        }
						    },
						    {field:'batchno',title:'批次号',width:80},
							{field:'produceddate',title:'生产日期',width:80},
					        {field:'deadline',title:'有效截止日期',width:80},
						    {field:'remark', title:'备注',width:100}
						]]
		});
    	var page_url = "storage/allocateEnterAddPage.do";
    	var page_type = '${type}';
    	if(page_type == "view" || page_type=="handle"){
    		page_url = "storage/allocateEnterViewPage.do?id=${id}";
    	}else if(page_type == "edit"){
    		page_url = "storage/allocateEnterEditPage.do?id=${id}";
    	}
    	$(function(){
    		$("#storage-panel-allocateEnterPage").panel({
				href:page_url,
			    cache:false,
			    maximized:true,
			    border:false
			});
    		//按钮
			$("#storage-buttons-allocateEnterPage").buttonWidget({
				initButton:[
					{},
					<security:authorize url="/storage/allocateEnterEditPage.do">
					{
						type: 'button-edit',
						handler: function(){
							var id = $("#storage-hidden-billid").val();
							if(id!=null && id !=""){
								$("#storage-panel-allocateEnterPage").panel({
									href:'storage/allocateEnterEditPage.do?id='+id,
									title:'',
								    cache:false,
								    maximized:true,
								    border:false
								});
							}
						}
					},
					</security:authorize>
					<security:authorize url="/storage/editAllocateEnterHold.do">
					{
						type: 'button-hold',
						handler: function(){
							var type = $("#storage-buttons-allocateEnterPage").buttonWidget("getOperType");
			 				if(type=="add"){
			 					//暂存
			 					$("#storage-form-allocateEnterAdd").attr("action", "storage/addAllocateEnterHold.do");
			 					$("#storage-form-allocateEnterAdd").submit();
			 				}else if(type=="edit"){
			 					//暂存
			 					$("#storage-form-allocateEnterAdd").attr("action", "storage/editAllocateEnterHold.do");
			 					$("#storage-form-allocateEnterAdd").submit();
			 				}
						}
					},
					</security:authorize>
					<security:authorize url="/storage/editAllocateEnterSave.do">
					{
						type: 'button-save',
						handler: function(){
							var type = $("#storage-buttons-allocateEnterPage").buttonWidget("getOperType");
			 				if(type=="add"){
			 					//暂存
			 					$("#storage-form-allocateEnterAdd").attr("action", "storage/addAllocateEnterSave.do");
			 					$("#storage-form-allocateEnterAdd").submit();
			 				}else if(type=="edit"){
			 					$("#storage-form-allocateEnterAdd").attr("action", "storage/editAllocateEnterSave.do");
			 					$("#storage-form-allocateEnterAdd").submit();
			 				}
						}
					},
					</security:authorize>
					<security:authorize url="/storage/allocateEnterGiveUp.do">
					{
						type:'button-giveup',
						handler:function(){
							var type = $("#storage-buttons-allocateEnterPage").buttonWidget("getOperType");
    						if(type == "add"){
    							var currTitle = top.$('#tt').tabs('getSelected').panel('options').title;
    							top.$('#tt').tabs('close',currTitle);
    						}
    						else if(type == "edit"){
	    						var id = $("#storage-hidden-billid").val();
	    						if(id == ""){
	    							return false;
	    						}
	    						$("#storage-panel-allocateEnterPage").panel({
									href:'storage/allocateEnterViewPage.do?id='+id,
									title:'',
								    cache:false,
								    maximized:true,
								    border:false
								});
    						}
						}
					},
					</security:authorize>
					<security:authorize url="/storage/deleteAllocateEnter.do">
					{
						type: 'button-delete',
						handler: function(){
							$.messager.confirm("提醒","是否删除当前调拨入库单？",function(r){
								if(r){
									var id = $("#storage-hidden-billid").val();
									if(id!=""){
										loading("删除中..");
										$.ajax({   
								            url :'storage/deleteAllocateEnter.do?id='+id,
								            type:'post',
								            dataType:'json',
								            success:function(json){
								            	loaded();
								            	if(json.flag){
								            		var object = $("#storage-buttons-allocateEnterPage").buttonWidget("removeData",id);
								            		$.messager.alert("提醒", "删除成功");
								            		if(null!=object){
									            		$("#storage-panel-allocateEnterPage").panel({
															href:'storage/allocateEnterViewPage.do?id='+object.id,
															title:'',
														    cache:false,
														    maximized:true,
														    border:false
														});
													}else{
														$("#storage-panel-allocateEnterPage").panel({
															href:'storage/allocateEnterAddPage.do',
															title:'',
														    cache:false,
														    maximized:true,
														    border:false
														});
													}
								            	}else{
								            		$.messager.alert("提醒", "删除失败");
								            	}
								            }
								        });
									}
								}
							});
						}
					},
					</security:authorize>
					<security:authorize url="/storage/auditAllocateEnter.do">
					{
						type: 'button-audit',
						handler: function(){
							$.messager.confirm("提醒","是否审核调拨入库单？",function(r){
								if(r){
									var id = $("#storage-hidden-billid").val();
									if(id!=""){
										loading("审核中..");
										$.ajax({   
								            url :'storage/auditAllocateEnter.do?id='+id,
								            type:'post',
								            dataType:'json',
								            success:function(json){
								            	loaded();
								            	if(json.flag){
													$.messager.alert("提醒","审核成功,并且关闭该调拨入库单");
													$("#storage-panel-allocateEnterPage").panel({
														href:'storage/allocateEnterViewPage.do?id='+id,
														title:'',
													    cache:false,
													    maximized:true,
													    border:false
													});
								            	}else{
								            		$.messager.alert("提醒","审核失败<br/>"+json.msg);
								            	}
								            },
								            error:function(){
								            	loaded();
								            	$.messager.alert("错误","审核失败");
								            }
								        });
									}
								}
							});
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
																	            		$("#storage-panel-allocateEnterPage").panel({
																							href:'storage/allocateEnterViewPage.do?id='+json.id,
																							title:'',
																						    cache:false,
																						    maximized:true,
																						    border:false
																						});
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
							<security:authorize url="/storage/showAllocateEnterSourcePage.do">
							{
								type: 'relation-upper-view',
								handler: function(){
									var sourceid = $("#storage-allocateEnter-sourceid").val();
									if(null!=sourceid && sourceid!=""){
										top.addOrUpdateTab('storage/showAllocateOutViewPage.do?id='+sourceid, "调拨出库单查看");
									}
								}
							},
							</security:authorize>
							{}
						]
					},
					</security:authorize>
					<security:authorize url="/storage/allocateEnterWorkflow.do">
					{
						type: 'button-workflow',
						button:[
							{
								type: 'workflow-submit',
								handler: function(){
									$.messager.confirm("提醒","是否提交该调拨入库单信息到工作流?",function(r){
								  		if(r){
								   			var id = $("#storage-hidden-billid").val();
								  			if(id == ""){
								    			$.messager.alert("警告","没有需要提交工作流的信息!");
								    			return false;
								   			}
								   			loading("提交中..");
								  			$.ajax({
									   			url:'storage/submitAllocateEnterPageProcess.do',
									   			dataType:'json',
									   			type:'post',
									   			data:'id='+id,
									   			success:function(json){
									   				loaded();
									    			if(json.flag == true){
										    			$.messager.alert("提醒","提交成功!");
										    			$("#storage-panel-allocateEnterPage").panel("refresh");
									    			}
									    			else{
									    				$.messager.alert("提醒","提交失败!"+json.msg);
									    			}
								   				}
								  			});
								  		}
								  	});
								}
							},
							{
								type: 'workflow-addidea',
								handler: function(){
									var order_type = '${type}';
									if(order_type == "handle"){
										$("#workflow-addidea-dialog-page").dialog({
											title:'填写处理意见',
											width:450,
											height:300,
											closed:false,
											cache:false,
										    modal: true,
											href:'workflow/commentAddPage.do?id='+ handleWork_taskId
										});
									}
								}
							},
							{
								type: 'workflow-viewflow',
								handler: function(){
									var id = $("#storage-hidden-billid").val();
									if(id == ""){
										return false;
									}
									$("#workflow-addidea-dialog-page").dialog({
										title:'查看流程',
										width:600,
										height:450,
										closed:false,
										cache:false,
									    modal: true,
									    maximizable:true,
									    resizable:true,
										href:'workflow/commentListPage.do?id='+ id
									});
								}
							},
							{
								type: 'workflow-viewflow-pic',
								handler: function(){
									var id = $("#storage-hidden-billid").val();
									if(id == ""){
										return false;
									}
									$("#workflow-addidea-dialog-page").dialog({
										title:'查看流程',
										width:600,
										height:450,
										closed:false,
										cache:false,
									    modal: true,
									    maximizable:true,
									    resizable:true,
										href:'workflow/showDiagramPage.do?id='+ id
									});
								}
							},
							{
								type: 'workflow-recover',
								handler: function(){
								
								}
							}
						]
					},
					</security:authorize>
					<security:authorize url="/storage/allocateEnterBack.do">
					{
						type: 'button-back',
						handler: function(data){
							$("#storage-panel-allocateEnterPage").panel({
								href:'storage/allocateEnterViewPage.do?id='+data.id,
								title:'',
							    cache:false,
							    maximized:true,
							    border:false
							});
						}
					},
					</security:authorize>
					<security:authorize url="/storage/allocateEnterNext.do">
					{
						type: 'button-next',
						handler: function(data){
							$("#storage-panel-allocateEnterPage").panel({
								href:'storage/allocateEnterViewPage.do?id='+data.id,
								title:'',
							    cache:false,
							    maximized:true,
							    border:false
							});
						}
					},
					</security:authorize>
					<security:authorize url="/storage/allocateEnterPreview.do">
					{
						type: 'button-preview',
						handler: function(){
							
						}
					},
					</security:authorize>
					<security:authorize url="/storage/allocateEnterPrint.do">
					{
						type: 'button-print',
						handler: function(){
						
						}
					},
					</security:authorize>
					{}
				],
				model: 'bill',
				type: 'view',
				tab:'调拨入库单列表',
				taburl:'/storage/showAllocateEnterListPage.do',
				id:'${id}',
				datagrid:'storage-datagrid-allocateEnterPage'
			});
    	});
    	//显示盘点单明细修改页面
    	function beginEditDetail(){
    		//验证表单
			var flag = $("#storage-form-allocateEnterAdd").form('validate');
			if(flag==false){
				$.messager.alert("提醒",'请先选择出库仓库');
				$("#storage-allocateEnter-storageid").focus();
				return false;
			}
			var row = $("#storage-datagrid-allocateEnterAddPage").datagrid('getSelected');
    		if(row == null){
    			$.messager.alert("提醒", "请选择一条记录");
    			return false;
    		}
    		if(row.goodsid == undefined){
    			beginAddDetail();
    		}else{
    			$('<div id="storage-dialog-allocateEnterAddPage-content"></div>').appendTo('#storage-dialog-allocateEnterAddPage');
	    		$('#storage-dialog-allocateEnterAddPage-content').dialog({  
				    title: '调拨入库单明细修改',  
				    width: 680,  
				    height: 400,  
				    collapsible:false,
				    minimizable:false,
				    maximizable:true,
				    resizable:true,
				    closed: true,  
				    cache: false,  
				    href: 'storage/showAllocateEnterDetailEditPage.do',  
				    modal: true,
				    buttons:[
				    	{  
		                    text:'确定',  
		                    iconCls:'button-save',
		                    plain:true,
		                    handler:function(){  
		                    	editSaveDetail(false);
		                    }  
		                }
				    ],
				    onClose:function(){
				    	$('#storage-dialog-allocateEnterAddPage-content').dialog("destroy");
				    }
				});
				$('#storage-dialog-allocateEnterAddPage-content').dialog("open");
			}
    	}
    	//修改保存
    	function editSaveDetail(goFlag){
    		var flag = $("#storage-form-allocateEnterDetailAddPage").form('validate');
		  	if(flag==false){
		  		return false;
		  	}
    		var form = $("#storage-form-allocateEnterDetailAddPage").serializeJSON();
    		var row = $("#storage-datagrid-allocateEnterAddPage").datagrid('getSelected');
    		var rowIndex = $("#storage-datagrid-allocateEnterAddPage").datagrid('getRowIndex', row);
    		form.goodsInfo = row.goodsInfo;
    		$("#storage-datagrid-allocateEnterAddPage").datagrid('updateRow',{index:rowIndex, row:form}); //将数据更新到列表中
    		if(goFlag){ //go为true确定并继续添加一条
    			beginAddDetail();
    		}
    		else{ //否则直接关闭
    			$("#storage-dialog-allocateEnterAddPage-content").dialog('destroy');
    		}
    		countTotal();
    	}
    	//计算合计
    	function countTotal(){
    		var rows =  $("#storage-datagrid-allocateEnterAddPage").datagrid('getRows');
    		var countNum = 0;
    		var taxamount = 0;
    		var notaxamount = 0;
    		var tax = 0;
    		for(var i=0;i<rows.length;i++){
    			countNum = Number(countNum)+Number(rows[i].unitnum == undefined ? 0 : rows[i].unitnum);
    			taxamount = Number(taxamount)+Number(rows[i].taxamount == undefined ? 0 : rows[i].taxamount);
    			notaxamount = Number(notaxamount)+Number(rows[i].notaxamount == undefined ? 0 : rows[i].notaxamount);
    			tax = Number(tax)+Number(rows[i].tax == undefined ? 0 : rows[i].tax);
    		}
    		$("#storage-datagrid-allocateEnterAddPage").datagrid("reloadFooter",[{goodsid:'合计',unitnum:countNum,taxamount:taxamount,notaxamount:notaxamount,tax:tax}]);
    	}
    </script>
  </body>
</html>
