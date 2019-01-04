<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>大单发货单页面</title>
    <%@include file="/include.jsp"%>
	  <%@include file="/printInclude.jsp" %>
  </head>
  
  <body>
    <div id="storage-layout-bigSaleOutPage" class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'north',border:false">
			<div class="buttonBG" id="storage-buttons-bigSaleOutPage" style="height: 26px;"></div>
		</div>
		<div data-options="region:'center',border:false">
			<div class="easyui-panel" data-options="fit:true" id="storage-panel-bigSaleOutPage"></div>
		</div>
	</div>
	<div id="bigSaleOut-dialog-customergoodsnum"></div>
	<div id="bigSaleOut-dialog-saleout"></div>
	<div id="bigSaleOut-dialog-printview"></div>
	<input id="bigSaleOut-delete-button" value="0"/>
	<div style="display: none;">
		<div id="storage-bigSaleOutList-zjfj-dialog-print">
			<form action="" method="post" id="storage-bigSaleOutList-zjfj-dialog-print-form">
				<table>
					<tr id="storage-bigSaleOutList-zjfj-dialog-printtemplet-tr">
						<td>整件模板：</td>
						<td>
							<select id="storage-bigSaleOutList-zjfj-dialog-printtemplet-id" name="templetid">
							</select>
						</td>
					</tr>
					<tr id="storage-bigSaleOutList-zjfj-dialog-printtemplet-tr-fj">
						<td>分拣模板：</td>
						<td>
							<select id="storage-bigSaleOutList-zjfj-dialog-printtemplet-fjid" name="fjtempletid">
							</select>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<script type="text/javascript">
		var goodsTableColJson = $("#bigSaleOut-table-GoodsDetail").createGridColumnLoad({
			name :'storage_saleout_detail',
			frozenCol : [[]],
			commonCol : [[
					{field:'goodsid',title:'商品编码',width:60},
   					{field:'goodsname', title:'商品名称', width:220,aliascol:'goodsid',
   						formatter:function(value,rowData,rowIndex){
				       		if(rowData.goodsInfo != null){
				       			return '<a href="javascript:showCustomerGoodsNumView(\''+rowData.goodsid+'\',\''+rowData.goodsInfo.name+'\');">'+rowData.goodsInfo.name+'</a>';
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
   					{field:'itemno', title:'商品货位',width:60,aliascol:'goodsid',align:'center',
   						formatter:function(value,rowData,rowIndex){
				       		if(rowData.goodsInfo != null){
				       			return rowData.goodsInfo.itemno;
				       		}else{
				       			return "";
				       		}
				        }
   					},
   					{field:'brandName', title:'商品品牌',width:80,aliascol:'goodsid',hidden:true,
   						formatter:function(value,rowData,rowIndex){
				       		if(rowData.goodsInfo != null){
				       			return rowData.goodsInfo.brandName;
				       		}else{
				       			return "";
				       		}
				        }
   					},
   					{field:'boxnum', title:'箱装量',aliascol:'goodsid',width:50,align:'right',
   						formatter:function(value,rowData,rowIndex){
				       		if(rowData.goodsInfo != null){
				       			return formatterBigNumNoLen(rowData.goodsInfo.boxnum);
				       		}else{
				       			return "";
				       		}
				        }
   					},
   					{field:'unitname', title:'单位',width:35},
   					{field:'unitnum', title:'数量',width:60,align:'right',
   						formatter:function(value,row,index){
			        		return formatterBigNumNoLen(value);
				        }
			    	},
   					{field:'taxamount', title:'金额',width:60,align:'right',hidden:true,
   						formatter:function(value,row,index){
			        		return formatterMoney(value);
				        }
				    },
   					{field:'notaxamount', title:'未税金额',width:60,align:'right',hidden:true,
   						formatter:function(value,row,index){
			        		return formatterMoney(value);
				        }
				    },
				    {field:'taxtypename', title:'税种',width:60,align:'right',hidden:true},
				    {field:'tax', title:'税额',width:60,align:'right',hidden:true,
   						formatter:function(value,row,index){
			        		return formatterMoney(value);
				        }
				    },
				    {field:'auxnumdetail', title:'辅数量',width:60,align:'right'},
				    {field:'storagelocationname', title:'所属库位',width:100,aliascol:'storagelocationid',hidden:true},
				    {field:'batchno',title:'批次号',width:80},
					{field:'produceddate',title:'生产日期',width:80},
			        {field:'deadline',title:'有效截止日期',width:80,hidden:true},
				    {field:'remark', title:'备注',width:100,hidden:true}
				]]
		});
	
		var billTableColJson = $("#bigSaleOut-table-SourceBill").createGridColumnLoad({
    		name :'storage_saleout',
			frozenCol : [[{field:'ck',title:'',width:50,checkbox:true}]],
			commonCol : [[
				  {field:'id',title:'编号',width:130,sortable:true,
				  	formatter:function(value,rowData,rowIndex){
		        		return '<a href="javascript:showSaleOutView(\''+rowData.id+'\');">'+value+'</a>';
		        	}
				  },
				  {field:'saleorderid',title:'销售订单编号',width:120,sortable:true,
				  	formatter:function(value,rowData,rowIndex){
		        		return '<a href="javascript:showSaleOrderView(\''+rowData.saleorderid+'\');">'+value+'</a>';
		        	}
				  },
				  {field:'businessdate',title:'业务日期',width:80,sortable:true},
				  {field:'storageid',title:'出库仓库',width:80,sortable:true,
				  	formatter:function(value,rowData,rowIndex){
		        		return rowData.storagename;
		        	}
				  },
				  {field:'customerid',title:'客户编码',width:60,sortable:true},
				  {field:'customername',title:'客户名称',width:150,isShow:true},
				  {field:'salesdept',title:'销售部门',width:80,sortable:true,hidden:true,
				  	formatter:function(value,rowData,rowIndex){
		        		return rowData.salesdeptname;
		        	}
				  },
				  {field:'salesuser',title:'客户业务员',width:70,sortable:true,
				  	formatter:function(value,rowData,rowIndex){
		        		return rowData.salesusername;
		        	}
				  },
				  {field:'sendamount',title:'发货出库金额',width:80,align:'right',
	  						formatter:function(value,row,index){
			        		return formatterMoney(value);
				        }
				  },
				  {field:'sendnotaxamount',title:'发货出库未税金额',width:80,align:'right',hidden:true,
	  						formatter:function(value,row,index){
			        		return formatterMoney(value);
				        }
				  },
				  {field:'sourcetype',title:'来源类型',width:90,sortable:true,hidden:true,
				  	formatter:function(value,rowData,rowIndex){
		        		return getSysCodeName("saleout-sourcetype",value);
		        	}
				  },
				  {field:'sourceid',title:'来源编号',width:130,sortable:true,
				  	formatter:function(value,rowData,rowIndex){
		        		return '<a href="javascript:showDispatchbillidView(\''+rowData.sourceid+'\');">'+value+'</a>';
		        	}
				  },
				  {field:'status',title:'状态',width:60,sortable:true,
				  	formatter:function(value,rowData,rowIndex){
		        		return getSysCodeName("status",value);
		        	}
				  },
				  {field:'indooruserid',title:'销售内勤',width:60,hidden:true,sortable:true,
				  	formatter:function(value,rowData,index){
		        		return rowData.indoorusername;
			        }
				  },
				  {field:'duefromdate',title:'应收日期',width:80,hidden:true,sortable:true},
				  {field:'auditusername',title:'审核人',width:60,hidden:true,sortable:true},
				  {field:'audittime',title:'审核时间',width:120,hidden:true,sortable:true},
				  {field:'remark',title:'备注',width:80,sortable:true},
				  {field:'addusername',title:'制单人',width:60,hidden:true,sortable:true},
				  {field:'addtime',title:'制单时间',width:120,hidden:true,sortable:true}
             ]]
    	});
	
		var page_url = "";
    	var page_type = '${type}';
    	var page_title="大单发货单查看";
    	var title = tabsWindowTitle('/storage/showBigSaleOutListPage.do');
    	
    	function refreshPanel(id,type){
    		page_type=type;
    		if(page_type == "view"){
	    		page_url = "storage/showBigSaleOutViewPage.do?id="+id;
	    		page_title="大单发货单查看";
	    	}else if(page_type == "edit"){
	    		page_url = "storage/showBigSaleOutEditPage.do?id="+id;
	    		page_title="大单发货单查看";
	    	}
	    	
    		$("#storage-panel-bigSaleOutPage").panel({
				href:page_url,
			    cache:false,
			    maximized:true,
			    border:false
			    //title:page_title
			});
		}
		
		function getType(status){
			var type = "view";
			if("2" == status){
				type = "edit";
			}
			return type;
		}
	
		$(function(){
			refreshPanel('${id}','${type}');
			
			//按钮
			$("#storage-buttons-bigSaleOutPage").buttonWidget({
				initButton:[
					{},
					<security:authorize url="/storage/bigSaleOut/bigSaleOutSave.do">
			 			{
				 			type:'button-save',
				 			handler:function(){
				 				var editsaleoutid = "";
				 				if(null != $dgSourceBillList){
				 					var rows = $dgSourceBillList.datagrid("getRows");
					 				for(var i=0;i<rows.length;i++){
					 					if(editsaleoutid == ""){
					 						editsaleoutid = rows[i].id;
					 					}else{
					 						editsaleoutid += "," + rows[i].id;
					 					}
					 				}
				 				}
				 				var billid = $("#storage-bigSaleOut-id").val();
				 				var remark = $("#bigSaleOut-remark").val();
				 				$.messager.confirm("提醒","是否确定保存大单发货单?",function(r){
				 					if(r){
										loading("提交中..");
										$.ajax({
								            url :'storage/saveBigSaleOut.do',
								            data:{billid:billid,editsaleoutid:editsaleoutid,remark:remark,isdel:$("#bigSaleOut-delete-button").val()},
								            type:'post',
								            dataType:'json',
								            success:function(json){
								            	loaded();
								            	if(json.flag){
								            	    if (top.$('#tt').tabs('exists',title)){
									    				tabsWindow(title).$("#storage-datagrid-bigSaleOutListPage").datagrid('reload');
									    			}
								            	    refreshPanel(billid,'edit');
								            		$.messager.alert("提醒","保存成功!");
								            	}else{
								            		$.messager.alert("提醒","保存失败!");
								            	}
								            },
								            error:function(){
								            	loaded();
								            	$.messager.alert("错误","保存出错");
								            }
								        });
				 					}
				 				});
				 			}
				 		},
				 	</security:authorize>
					<security:authorize url="/storage/bigSaleOut/bigSaleOutDelete.do">
			 			{
				 			type:'button-delete',
				 			handler:function(){
				 				var id=$("#storage-bigSaleOut-id").val();
				 				$.messager.confirm("提醒","是否确定删除大单发货单?",function(r){
				 					if(r){
									loading("删除中..");
									$.ajax({
							            url :'storage/deleteBigSaleOuts.do?ids='+id,
							            type:'post',
							            dataType:'json',
							            success:function(json){
							            	loaded();
							            	if(json.msg != ""){
							            	    if (top.$('#tt').tabs('exists',title)){
								    				tabsWindow(title).$("#storage-datagrid-bigSaleOutListPage").datagrid('reload');
								    			}
								    			var data = $("#storage-buttons-bigSaleOutPage").buttonWidget("removeData",id);
								    			if(null != data){
								    				refreshPanel(data.id,getType(data.status));
								    			}else{
								    				top.closeNowTab();
								    			}
							            		$.messager.alert("提醒",json.msg);
							            	}
							            },
							            error:function(){
							            	loaded();
							            	$.messager.alert("错误","删除出错");
							            }
							        });
				 					}
				 				});
				 			}
				 		},
				 	</security:authorize>	
					<security:authorize url="/storage/bigSaleOut/bigSaleOutAudit.do">
					{
						type: 'button-audit',
						handler: function(){
							var id=$("#storage-bigSaleOut-id").val();
							$.messager.confirm("提醒","是否审核大单发货单？",function(r){
								if(r){
									var bigSaleOut = $("#storage-form-bigSaleOutAdd").serializeJSON();
									loading("审核中..");
									$.ajax({
							            url :'storage/auditBigSaleOuts.do?ids='+id,
							            data: bigSaleOut,
							            type:'post',
							            dataType:'json',
							            success:function(json){
							            	loaded();
							            	$.messager.alert("提醒",json.msg);
						            	    if (top.$('#tt').tabs('exists',title)){
							    				tabsWindow(title).$("#storage-datagrid-bigSaleOutListPage").datagrid('reload');
							    			}
						            	    refreshPanel(id,'view');
							            },
							            error:function(){
							            	loaded();
							            	$.messager.alert("错误","审核出错");
							            }
							        });
									
								}
							});
						}
					},
					</security:authorize>
					<security:authorize url="/storage/bigSaleOut/bigSaleOutOppaudit.do">
						{
				 			type:'button-oppaudit',
				 			handler:function(){
					 			var id=$("#storage-bigSaleOut-id").val();
								$.messager.confirm("提醒","是否反审大单发货单？",function(r){
									if(r){
										loading("反审中..");
										$.ajax({
								            url :'storage/oppauditBigSaleOut.do?id='+id,
								            type:'post',
								            dataType:'json',
								            success:function(json){
								            	loaded();
								            	if(json.flag){
								            	    $.messager.alert("提醒","反审成功");
								            	    if (top.$('#tt').tabs('exists',title)){
									    				tabsWindow(title).$("#storage-datagrid-bigSaleOutListPage").datagrid('reload');
									    			}
								            	    refreshPanel(id,'edit');
								            	}else{
								            		$.messager.alert("提醒","反审失败");
								            	}
								            },
								            error:function(){
								            	loaded();
								            	$.messager.alert("错误","反审出错");
								            }
								        });
									}
								});
				 			}
			 			},
					</security:authorize>
					<security:authorize url="/storage/bigSaleOut/bigSaleOutViewBackPage.do">
					{
						type: 'button-back',
						handler: function(data){
							refreshPanel(data.id,getType(data.status));
						}
					},
					</security:authorize>
					<security:authorize url="/storage/bigSaleOut/bigSaleOutViewNextPage.do">
					{
						type: 'button-next',
						handler: function(data){
							refreshPanel(data.id,getType(data.status));
						}
					},
					</security:authorize>
					
					{}
				],
				buttons:[
					{},
					<security:authorize url="/storage/bigSaleOut/bigSaleOutPrintView.do">
					{id:'printMenuButton',
						type:'menu',
						name:'打印预览',
						iconCls:'button-preview',
						button:[
                            <security:authorize url="/storage/bigSaleOut/bigSaleOutTotalGoodsPrintView.do">
                            {
                                id:'printview-totalgoods',
                                name:'整件分拣预览',
                                iconCls:'button-preview',
                                handler:function(){
                                }
                            },
                            </security:authorize>
                            <security:authorize url="/storage/bigSaleOut/bigSaleOutTotalGoodsPrint.do">
                            {
                                id:'print-totalgoods',
                                name:'整件分拣打印',
                                iconCls:'button-print',
                                handler:function(){
                                }
                            },
                            </security:authorize>
                            <security:authorize url="/storage/bigSaleOut/bigSaleOutTotalGoodsPrintViewForZJ.do">
                            {
                                id:'printview-totalgoods-zj',
                                name:'整件预览',
                                iconCls:'button-preview',
                                handler:function(){
                                }
                            },
                            </security:authorize>
                            <security:authorize url="/storage/bigSaleOut/bigSaleOutTotalGoodsPrintForZJ.do">
                            {
                                id:'print-totalgoods-zj',
                                name:'整件打印',
                                iconCls:'button-print',
                                handler:function(){
                                }
                            },
                            </security:authorize>
                            <security:authorize url="/storage/bigSaleOut/bigSaleOutTotalGoodsPrintViewForFJ.do">
                            {
                                id:'printview-totalgoods-fj',
                                name:'分拣预览',
                                iconCls:'button-preview',
                                handler:function(){
                                }
                            },
                            </security:authorize>
                            <security:authorize url="/storage/bigSaleOut/bigSaleOutTotalGoodsPrintForFJ.do">
                            {
                                id:'print-totalgoods-fj',
                                name:'分拣打印',
                                iconCls:'button-print',
                                handler:function(){
                                }
                            },
                            </security:authorize>
                            <security:authorize url="/storage/bigSaleOut/bigSaleOutGoodsCustomerPrintView.do">
                            {
                                id:'printview-goodscustomer',
                                name:'按商品分客户数预览',
                                iconCls:'button-preview',
                                handler:function(){
                                }
                            },
                            </security:authorize>
                            <security:authorize url="/storage/bigSaleOut/bigSaleOutGoodsCustomerPrint.do">
                            {
                                id:'print-goodscustomer',
                                name:'按商品分客户数打印',
                                iconCls:'button-print',
                                handler:function(){
                                }
                            },
                            </security:authorize>
                            <security:authorize url="/storage/bigSaleOut/bigSaleOutGoodsCustomerDivPrintView.do">
                            {
                                id:'printview-goodscustomerdiv',
                                name:'按商品分客户区块预览',
                                iconCls:'button-preview',
                                handler:function(){
                                }
                            },
                            </security:authorize>
                            <security:authorize url="/storage/bigSaleOut/bigSaleOutGoodsCustomerDivPrint.do">
                            {
                                id:'print-goodscustomerdiv',
                                name:'按商品分客户区块',
                                iconCls:'button-print',
                                handler:function(){
                                }
                            },
                            </security:authorize>
                            <security:authorize url="/storage/bigSaleOut/bigSaleOutBrandPrintView.do">
                            {
                                id:'printview-brand',
                                name:'按品牌分商品预览',
                                iconCls:'button-preview',
                                handler:function(){
                                }
                            },
                            </security:authorize>
                            <security:authorize url="/storage/bigSaleOut/bigSaleOutBrandPrint.do">
                            {
                                id:'print-brand',
                                name:'按品牌分商品打印',
                                iconCls:'button-print',
                                handler:function(){
                                }
                            },
                            </security:authorize>
						]
					},
					</security:authorize>
					<security:authorize url="/storage/bigSaleOut/bigSaleOutSaleout.do">
					{
						id:'button-saleout-button',
						name:'确认发货',
						iconCls:'button-audit',
						handler: function(){
							var id=$("#storage-bigSaleOut-id").val();
							$.messager.confirm("提醒","是否确认大单发货单已发货？",function(r){
								if(r){
									loading("提交中..");
									$.ajax({
							            url :'storage/doSaleoutBigSaleOuts.do?ids='+id,
							            type:'post',
							            dataType:'json',
							            success:function(json){
							            	loaded();
											if(json.flag){
												$.messager.alert("提醒","确认发货成功;");
												if (top.$('#tt').tabs('exists',title)){
													tabsWindow(title).$("#storage-datagrid-bigSaleOutListPage").datagrid('reload');
												}
												refreshPanel(id,'view');
											}else{
												$.messager.alert("提醒","确认发货失败;<br/>"+json.msg);
											}
							            },
							            error:function(XMLHttpRequest){
							            	loaded();
											$.messager.alert("提醒","确认发货出错或发货单据未全部审核通过，无法直接确认发货！");
							            }
							        });
									
								}
							});
						}
					},
					</security:authorize>
					<security:authorize url="/storage/bigSaleOut/cancelBigSaleOutSaleout.do">
					{
						id:'button-cancelsaleout-button',
						name:'作废大单发货',
						iconCls:'button-oppaudit',
						handler: function(){
							var id=$("#storage-bigSaleOut-id").val();
							$.messager.confirm("提醒","是否作废该大单发货？",function(r){
								if(r){
									loading("提交中..");
									$.ajax({
										url :'storage/cancelDoSaleoutBigSaleOuts.do?ids='+id,
										type:'post',
										dataType:'json',
										success:function(json){
											loaded();
											if(json.flag){
												$.messager.alert("提醒","作废大单发货成功");
												if (top.$('#tt').tabs('exists',title)){
													tabsWindow(title).$("#storage-datagrid-bigSaleOutListPage").datagrid('reload');
												}
												refreshPanel(id,'view');
											}else{
												var msg = "作废大单发货失败";
												if(json.retmsg != ""){
													msg = "作废大单发货失败,"+json.retmsg;
												}
												$.messager.alert("提醒",msg);
											}
										},
										error:function(){
											loaded();
											$.messager.alert("错误","作废大单发货出错");
										}
									});

								}
							});
						}
					},
					</security:authorize>
					{}
				],
				model: 'bill',
				type: '${type}',
				id:'${id}',
				taburl:'/storage/showBigSaleOutListPage.do',		//tab标签的url地址。
				datagrid:'storage-datagrid-bigSaleOutListPage'
			});
		});
		
		//显示客户对应商品数页面
		function showCustomerGoodsNumView(goodsid,goodsname){
			var bigsaleoutid = $("#storage-bigSaleOut-id").val();
			$('#bigSaleOut-dialog-customergoodsnum').dialog({  
			    title: '按商品'+goodsid+'：【'+goodsname+'】分客户明细',  
			    width: 600,
			    height: 380,
			    closed: false,
			    cache: false,
			    resizable: true,
			    maximizable:true,
			    href:'storage/showBigSaleOutCustomerGoodsNumPage.do?bigsaleoutid='+bigsaleoutid+'&goodsid='+goodsid,
			    modal: true
			});
		}
		//显示发货单据
		function showSaleOutView(saleoutid){
			top.addOrUpdateTab('storage/showSaleOutViewPage.do?id='+ saleoutid, "发货单查看");
		}
		//显示订单单据
		function showSaleOrderView(saleorderid){
			top.addOrUpdateTab('sales/orderPage.do?type=view&id='+ saleorderid, "销售订单查看");
		}
		//显示发货通知单单据
		function showDispatchbillidView(dispatchbillid){
			top.addOrUpdateTab('sales/dispatchBill.do?type=edit&id='+ dispatchbillid, "销售发货通知单查看");
		}
	</script>
	<%--打印开始 --%>
	<script type="text/javascript">
        $(function () {
            if (AgReportPrint.isShowPrintTempletManualSelect("storage_bigsaleout")) {
                //整件单模板
                var options={
                    renderTo:'storage-bigSaleOutList-zjfj-dialog-printtemplet-id',
                    code:'storage_bigsaleout',
                    codereqparam:{
                        mark:'整件单模板'
                    }
                };
                AgReportPrint.createPrintTempletSelectOptionByMap(options);

                //分拣单模板
                var options={
                    renderTo:'storage-bigSaleOutList-zjfj-dialog-printtemplet-fjid',
                    code:'storage_bigsaleout',
                    codereqparam:{
                        mark:'分拣单模板'
                    }
                };
                AgReportPrint.createPrintTempletSelectOptionByMap(options);
            }
            //打印整件&分拣
            AgReportPrint.init({
                id: "storage-bigSaleOutList-zjfj-dialog-print",
                code: "storage_bigsaleout",
                url_preview: "print/storage/storageBigSaleOutPrintView.do?printbilltype=zjfj",
                url_print: "print/storage/storageBigSaleOutPrint.do?printbilltype=zjfj",
                btnPreview: "printview-totalgoods",
                btnPrint: "print-totalgoods",
                getData: getData,
                onPrintSuccess: onPrintSuccess,
                printAfterHandler: printAfterHandler
            });
            //打印整件
            AgReportPrint.init({
                id: "storage-bigSaleOutList-zj-dialog-print",
                code: "storage_bigsaleout",
                codereqparam:{
                    mark:'整件单模板'
                },
                url_preview: "print/storage/storageBigSaleOutPrintView.do?printbilltype=zj",
                url_print: "print/storage/storageBigSaleOutPrint.do?printbilltype=zj",
                btnPreview: "printview-totalgoods-zj",
                btnPrint: "print-totalgoods-zj",
                getData: getData,
                onPrintSuccess: onPrintSuccess,
                printAfterHandler: printAfterHandler
            });
            //打印分拣
            AgReportPrint.init({
                id: "storage-bigSaleOutList-fj-dialog-print",
                code: "storage_bigsaleout",
                codereqparam:{
                    mark:'分拣单模板'
                },
                url_preview: "print/storage/storageBigSaleOutPrintView.do?printbilltype=fj",
                url_print: "print/storage/storageBigSaleOutPrint.do?printbilltype=fj",
                btnPreview: "printview-totalgoods-fj",
                btnPrint: "print-totalgoods-fj",
                getData: getData,
                onPrintSuccess: onPrintSuccess,
                printAfterHandler: printAfterHandler
            });
            //打印按商品分客户
            AgReportPrint.init({
                id: "storage-bigSaleOutList-customer-dialog-print",
                code: "storage_bigsaleout",
                codereqparam:{
                    mark:'按商品分客户模板'
                },
                url_preview: "print/storage/storageBigSaleOutPrintView.do?printbilltype=customer",
                url_print: "print/storage/storageBigSaleOutPrint.do?printbilltype=customer",
                btnPreview: "printview-goodscustomer",
                btnPrint: "print-goodscustomer",
                getData: getData,
                onPrintSuccess: onPrintSuccess,
                printAfterHandler: printAfterHandler
            });
            //打印按商品分客户区块
            AgReportPrint.init({
                id: "storage-bigSaleOutList-customerblock-dialog-print",
                code: "storage_bigsaleout",
                codereqparam:{
                    mark:'按商品分客户区块模板'
                },
                exPrintParam:{
                    jobtype:'2'
                },
                url_preview: "print/storage/storageBigSaleOutPrintView.do?printbilltype=customer",
                url_print: "print/storage/storageBigSaleOutPrint.do?printbilltype=customer",
                btnPreview: "printview-goodscustomerdiv",
                btnPrint: "print-goodscustomerdiv",
                getData: getData,
                onPrintSuccess: onPrintSuccess,
                printAfterHandler: printAfterHandler
            });
            //打印按品牌分商品
            AgReportPrint.init({
                id: "storage-bigSaleOutList-brand-dialog-print",
                code: "storage_bigsaleout",
                codereqparam:{
                    mark:'按品牌分商品模板'
                },
                url_preview: "print/storage/storageBigSaleOutPrintView.do?printbilltype=brand",
                url_print: "print/storage/storageBigSaleOutPrint.do?printbilltype=brand",
                btnPreview: "printview-brand",
                btnPrint: "print-brand",
                getData: getData,
                onPrintSuccess: onPrintSuccess,
                printAfterHandler: printAfterHandler
            });
            function getData(tableId, printParam) {
                var id = $("#storage-bigSaleOut-id").val();
                if (id == "") {
                    $.messager.alert("提醒", "请根据大单发货单不可打印");
                    return false;
                }
                printParam.idarrs = id;
                var printtimes = $("#storage-bigSaleOut-printtimes").val() || 0;
                if (printtimes > 0)
                    printParam.printIds = [id];
                return true;
            }
        });
        function onPrintSuccess(option) {
            var printtimes = $("#storage-bigSaleOut-printtimes").val() || 0;
            $("#storage-bigSaleOut-printtimes").val(printtimes+1);
            
            var thepage = tabsWindowURL("/storage/showBigSaleOutListPage.do");
            if (thepage == null) {
                return false;
            }
            var thegrid = thepage.$("#storage-datagrid-bigSaleOutListPage");
            if (thegrid == null || thegrid.size() == 0) {
                return false;
            }
            var dataList = thegrid.datagrid("getChecked");
            if (dataList == null || dataList.length == 0) {
                return false;
            }
            var datarow = thegrid.datagrid("getRows");
            if (null != datarow && datarow.length > 0) {
                for (var i = 0; i < datarow.length; i++) {
                    if (datarow[i].id == id) {
                        if (datarow[i].printtimes && !isNaN(datarow[i].printtimes)) {
                            datarow[i].printtimes = datarow[i].printtimes + 1;
                        } else {
                            datarow[i].printtimes = 1;
                        }
                        thegrid.datagrid('updateRow', {index: i, row: {printtimes: datarow[i].printtimes}});
                        break;
                    }
                }
            }
        }
        function printAfterHandler(option, printParam) {
        }
	</script>
	<%--打印结束 --%>
  </body>
</html>
