<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>采购发票参照上游单据列表页面</title>
  </head>
  
  <body>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',split:true,border:false" style="height:260px">
			<table id="account-orderDatagrid-dispatchBillSourcePage"></table>
		</div>
		<div data-options="region:'center',border:false">
			<table id="account-detailDatagrid-dispatchBillSourcePage"></table>
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			<c:if test="${sourcetype=='2'}">
			$("#account-detailDatagrid-dispatchBillSourcePage").datagrid({
				columns:[[
						{field:'ck', checkbox:true},
						{field:'orderid',title:'单据编码',width:80,isShow:true},
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
    					{field:'barcode', title:'条形码',width:85,aliascol:'goodsid',hidden:true,
    						formatter:function(value,rowData,rowIndex){
					       		if(rowData.goodsInfo != null){
					       			return rowData.goodsInfo.barcode;
					       		}else{
					       			return "";
					       		}
					        }
    					},
    					{field:'unitid', title:'主单位',width:60,
    						formatter: function(value,row,index){
								return row.unitname;
							}
						},
    					{field:'unitnum', title:'数量',width:60,align:'right',
    						formatter:function(value,row,index){
				        		return formatterMoney(value);
					        }
				    	},
    					{field:'auxunitid', title:'辅单位',width:60,
    						formatter: function(value,row,index){
    							return row.auxunitname;
    						}
    					},
    					{field:'auxnumdetail', title:'辅数量',width:60,align:'right'},
    					{field:'taxprice', title:'含税单价',width:60,align:'right',
    						formatter:function(value,row,index){
				        		return formatterMoney(value);
					        }
				    	},
    					{field:'taxamount', title:'含税金额',resizable:true,align:'right',
    						formatter:function(value,row,index){
				        		return formatterMoney(value);
					        }
					    },
					    {field:'notaxprice', title:'未税单价',width:60,align:'right',hidden:true,
    						formatter:function(value,row,index){
				        		return formatterMoney(value);
					        }
				    	},
    					{field:'notaxamount', title:'未税金额',resizable:true,align:'right',hidden:true,
    						formatter:function(value,row,index){
				        		return formatterMoney(value);
					        }
					    },
					    {field:'taxtypename', title:'税种',width:60,align:'right',hidden:true},
					    {field:'tax', title:'税额',resizable:true,align:'right',hidden:true,
    						formatter:function(value,row,index){
				        		return formatterMoney(value);
					        }
					    },
					    {field:'storagelocationid', title:'所属库位',width:100,hidden:true,
					    	formatter:function(value,row,index){
				        		return row.storagelocationname;
					        }
					    },
					    {field:'batchno',title:'批次号',width:80,hidden:true},
						{field:'produceddate',title:'生产日期',width:80,hidden:true},
				        {field:'deadline',title:'有效截止日期',width:80,hidden:true},
					    {field:'remark', title:'备注',width:100},
						{field:'goodsfield01',title:'${gfieldMap.field01}',width:80,hidden:true,isShow:true,align:'center',
	    						formatter:function(value,rowData,rowIndex){
						       		if(rowData.goodsInfo != null){
						       			return rowData.goodsInfo.field01;
						       		}else{
						       			return value;
						       		}
						        }
						},
						{field:'goodsfield02',title:'${gfieldMap.field02}',width:80,hidden:true,isShow:true,align:'center',
	    						formatter:function(value,rowData,rowIndex){
						       		if(rowData.goodsInfo != null){
						       			return rowData.goodsInfo.field02;
						       		}else{
						       			return value;
						       		}
						        }
						},
						{field:'goodsfield03',title:'${gfieldMap.field03}',width:80,hidden:true,isShow:true,align:'center',
								formatter:function(value,rowData,rowIndex){
					       		if(rowData.goodsInfo != null){
					       			return rowData.goodsInfo.field03;
					       		}else{
					       			return value;
					       		}
					        }
						},
						{field:'goodsfield04',title:'${gfieldMap.field04}',width:80,hidden:true,isShow:true,align:'center',
								formatter:function(value,rowData,rowIndex){
						       		if(rowData.goodsInfo != null){
						       			return rowData.goodsInfo.field04;
						       		}else{
						       			return value;
						       		}
						        }
						},
						{field:'goodsfield05',title:'${gfieldMap.field05}',width:80,hidden:true,isShow:true,align:'center',
								formatter:function(value,rowData,rowIndex){
						       		if(rowData.goodsInfo != null){
						       			return rowData.goodsInfo.field05;
						       		}else{
						       			return value;
						       		}
						        }
						},
						{field:'goodsfield06',title:'${gfieldMap.field06}',width:80,hidden:true,isShow:true,align:'center',
								formatter:function(value,rowData,rowIndex){
					       		if(rowData.goodsInfo != null){
					       			return rowData.goodsInfo.field06;
					       		}else{
					       			return value;
					       		}
					        }
						},
						{field:'goodsfield07',title:'${gfieldMap.field07}',width:80,hidden:true,isShow:true,align:'center',
								formatter:function(value,rowData,rowIndex){
							   		if(rowData.goodsInfo != null){
							   			return rowData.goodsInfo.field07;
							   		}else{
							   			return value;
							   		}
							    }
						},
						{field:'goodsfield08',title:'${gfieldMap.field08}',width:80,hidden:true,isShow:true,align:'center',
								formatter:function(value,rowData,rowIndex){
									if(rowData.goodsInfo != null){
										return rowData.goodsInfo.barcode;
									}else{
										return value;
									}
								}
						},
						{field:'goodsfield09',title:'${gfieldMap.field09}',width:80,hidden:true,isShow:true,align:'center',
								formatter:function(value,rowData,rowIndex){
									if(rowData.goodsInfo != null){
										return rowData.goodsInfo.field09;
									}else{
										return value;
									}
								}
						}	
				]],
		 		fit:true,
		 		method:'post',
		 		rownumbers:true,
		 		idField:'id',
		 		singleSelect:false,
				fitColumns:true,
		 		showFooter: true,
				checkOnSelect:true,
			 	selectOnCheck:true,
				onCheckAll:function(){
					countDetailTotalAmount();
				},
				onUncheckAll:function(){
					countDetailTotalAmount();
				},
				onCheck:function(){
					countDetailTotalAmount();
				},
				onUncheck:function(){
					countDetailTotalAmount();
				}
			});
			</c:if>
			<c:if test="${sourcetype=='1'}">
			$("#account-detailDatagrid-dispatchBillSourcePage").datagrid({
				columns:[[
						{field:'ck', checkbox:true},
						{field:'orderid',title:'单据编码',width:80,sortable:true,isShow:true},
						{field:'goodsid',title:'商品编码',width:80,sortable:true,isShow:true},
 						{field:'goodsname',title:'商品名称',width:120,isShow:true,aliascol:'goodsname',
    						formatter:function(value,rowData,rowIndex){
					       		if(rowData.goodsInfo != null){
					       			return rowData.goodsInfo.name;
					       		}else{
					       			return value;
				       			}
					        }
				        },
				        {field:'brandname',title:'商品品牌',width:100,isShow:true,
    						formatter:function(value,rowData,rowIndex){
					       		if(rowData.goodsInfo != null){
					       			return rowData.goodsInfo.brandName;
					       		}else{
					       			return value;
					       		}
					        }
				        },
 						{field:'model',title:'规格型号',width:80,isShow:true,
    						formatter:function(value,rowData,rowIndex){
					       		if(rowData.goodsInfo != null){
					       			return rowData.goodsInfo.model;
					       		}else{
					       			return value;
					       		}
					        }
				        },
				        {field:'barcode',title:'条形码',width:80,isShow:true,hidden:true,
    						formatter:function(value,rowData,rowIndex){
					       		if(rowData.goodsInfo != null){
					       			return rowData.goodsInfo.barcode;
					       		}else{
					       			return value;
					       		}
					        }
						},
    					{field:'unitid', title:'主单位',width:60,
    						formatter: function(value,row,index){
								return row.unitname;
							}
						},
    					{field:'unitnum', title:'数量',width:80,align:'right',
    						formatter:function(value,row,index){
				        		return formatterMoney(value);
					        }
				    	},
    					{field:'auxunitid', title:'辅单位',width:60,
    						formatter: function(value,row,index){
    							return row.auxunitname;
    						}
    					},
						{field:'auxnumdetail',title:'辅数量',width:80,align:'right'},
						{field:'taxprice',title:'含税单价',width:80,align:'right',
							formatter: function(value,row,index){
				        		return formatterMoney(value);
				        	}
				        },
						{field:'taxamount',title:'含税金额',resizable:true,align:'right',
							formatter: function(value,row,index){
			        			return formatterMoney(value);
				        	}
				        },
						{field:'notaxprice',title:'无税单价',width:80,align:'right',
							formatter: function(value,row,index){
			        			return formatterMoney(value);
				        	}
				        },
						{field:'notaxamount',title:'无税金额',resizable:true,align:'right',
							formatter: function(value,row,index){
			        			return formatterMoney(value);
				        	}
				        },
						{field:'taxtypename',title:'税种',width:80},
						{field:'tax',title:'税额',resizable:true,align:'right',hidden:true,
							formatter: function(value,row,index){
			        			return formatterMoney(value);
		        			}
		        		},
						{field:'batchno',title:'批次号',hidden:true},
						{field:'produceddate',title:'生产日期',hidden:true},
						{field:'deadline',title:'有效截止日期',hidden:true},
						{field:'remark',title:'备注',width:80},
						{field:'goodsfield01',title:'${gfieldMap.field01}',width:80,hidden:true,isShow:true,
	    						formatter:function(value,rowData,rowIndex){
						       		if(rowData.goodsInfo != null){
						       			return rowData.goodsInfo.field01;
						       		}else{
						       			return value;
						       		}
						        }
						},
						{field:'goodsfield02',title:'${gfieldMap.field02}',width:80,hidden:true,isShow:true,
	    						formatter:function(value,rowData,rowIndex){
						       		if(rowData.goodsInfo != null){
						       			return rowData.goodsInfo.field02;
						       		}else{
						       			return value;
						       		}
						        }
						},
						{field:'goodsfield03',title:'${gfieldMap.field03}',width:80,hidden:true,isShow:true,
								formatter:function(value,rowData,rowIndex){
					       		if(rowData.goodsInfo != null){
					       			return rowData.goodsInfo.field03;
					       		}else{
					       			return value;
					       		}
					        }
						},
						{field:'goodsfield04',title:'${gfieldMap.field04}',width:80,hidden:true,isShow:true,
								formatter:function(value,rowData,rowIndex){
						       		if(rowData.goodsInfo != null){
						       			return rowData.goodsInfo.field04;
						       		}else{
						       			return value;
						       		}
						        }
						},
						{field:'goodsfield05',title:'${gfieldMap.field05}',width:80,hidden:true,isShow:true,
								formatter:function(value,rowData,rowIndex){
						       		if(rowData.goodsInfo != null){
						       			return rowData.goodsInfo.field05;
						       		}else{
						       			return value;
						       		}
						        }
						},
						{field:'goodsfield06',title:'${gfieldMap.field06}',width:80,hidden:true,isShow:true,
								formatter:function(value,rowData,rowIndex){
					       		if(rowData.goodsInfo != null){
					       			return rowData.goodsInfo.field06;
					       		}else{
					       			return value;
					       		}
					        }
						},
						{field:'goodsfield07',title:'${gfieldMap.field07}',width:80,hidden:true,isShow:true,
								formatter:function(value,rowData,rowIndex){
							   		if(rowData.goodsInfo != null){
							   			return rowData.goodsInfo.field07;
							   		}else{
							   			return value;
							   		}
							    }
						},
						{field:'goodsfield08',title:'${gfieldMap.field08}',width:80,hidden:true,isShow:true,
								formatter:function(value,rowData,rowIndex){
									if(rowData.goodsInfo != null){
										return rowData.goodsInfo.barcode;
									}else{
										return value;
									}
								}
						},
						{field:'goodsfield09',title:'${gfieldMap.field09}',width:80,hidden:true,isShow:true,
								formatter:function(value,rowData,rowIndex){
									if(rowData.goodsInfo != null){
										return rowData.goodsInfo.field09;
									}else{
										return value;
									}
								}
						}
				]],
		 		fit:true,
		 		method:'post',
		 		rownumbers:true,
		 		idField:'id',
		 		singleSelect:false,
				fitColumns:true,
		 		showFooter: true,
				checkOnSelect:true,
			 	selectOnCheck:true,
				onCheckAll:function(){
					countDetailTotalAmount();
				},
				onUncheckAll:function(){
					countDetailTotalAmount();
				},
				onCheck:function(){
					countDetailTotalAmount();
				},
				onUncheck:function(){
					countDetailTotalAmount();
				}
			});
			</c:if>
			<c:if test="${sourcetype=='3'}">
				$("#account-detailDatagrid-dispatchBillSourcePage").datagrid({
					columns:[[
							{field:'ck', checkbox:true},
							{field:'orderid',title:'单据编码',width:110,sortable:true,isShow:true},
							{field:'goodsid',title:'商品编码',width:60,sortable:true,isShow:true},
	 						{field:'goodsname',title:'商品名称',width:210,isShow:true,aliascol:'goodsname',
	    						formatter:function(value,rowData,rowIndex){
						       		if(rowData.goodsInfo != null){
						       			return rowData.goodsInfo.name;
						       		}else{
						       			return value;
					       			}
						        }
					        },
	 						{field:'model',title:'规格型号',width:60,isShow:true,
	    						formatter:function(value,rowData,rowIndex){
						       		if(rowData.goodsInfo != null){
						       			return rowData.goodsInfo.model;
						       		}else{
						       			return value;
						       		}
						        }
					        },
	 						{field:'brandname',title:'商品品牌',width:100,isShow:true,
	    						formatter:function(value,rowData,rowIndex){
						       		if(rowData.goodsInfo != null){
						       			return rowData.goodsInfo.brandName;
						       		}else{
						       			return value;
						       		}
						        }
					        },
	    					{field:'unitid', title:'主单位',width:60,
	    						formatter: function(value,row,index){
									return row.unitname;
								}
							},
	    					{field:'unitnum', title:'数量',width:80,align:'right',
	    						formatter:function(value,row,index){
					        		return formatterMoney(value);
						        }
					    	},
	    					{field:'auxunitid', title:'辅单位',width:60,
	    						formatter: function(value,row,index){
	    							return row.auxunitname;
	    						}
	    					},
							{field:'auxnumdetail',title:'辅数量',width:80,align:'right'},
							{field:'taxprice',title:'含税单价',width:80,align:'right',
								formatter: function(value,row,index){
					        		return formatterMoney(value);
					        	}
					        },
							{field:'taxamount',title:'含税金额',resizable:true,align:'right',
								formatter: function(value,row,index){
				        			return formatterMoney(value);
					        	}
					        },
							{field:'notaxprice',title:'无税单价',width:80,align:'right',
								formatter: function(value,row,index){
				        			return formatterMoney(value);
					        	}
					        },
							{field:'notaxamount',title:'无税金额',resizable:true,align:'right',
								formatter: function(value,row,index){
				        			return formatterMoney(value);
					        	}
					        },
							{field:'taxtypename',title:'税种',width:80},
							{field:'tax',title:'税额',resizable:true,align:'right',hidden:true,
								formatter: function(value,row,index){
				        			return formatterMoney(value);
			        			}
			        		},
			        		{field:'storagelocationid', title:'所属库位',width:100,hidden:true,
						    	formatter:function(value,row,index){
					        		return row.storagelocationname;
						        }
						    },
			        		{field:'barcode',title:'条形码',width:80,isShow:true,hidden:true,
	    						formatter:function(value,rowData,rowIndex){
						       		if(rowData.goodsInfo != null){
						       			return rowData.goodsInfo.barcode;
						       		}else{
						       			return value;
						       		}
						        }
							},
							{field:'batchno',title:'批次号',hidden:true},
							{field:'produceddate',title:'生产日期',hidden:true},
							{field:'deadline',title:'有效截止日期',hidden:true},
							{field:'remark',title:'备注',width:80},
							{field:'goodsfield01',title:'${gfieldMap.field01}',width:80,hidden:true,isShow:true,
		    						formatter:function(value,rowData,rowIndex){
							       		if(rowData.goodsInfo != null){
							       			return rowData.goodsInfo.field01;
							       		}else{
							       			return value;
							       		}
							        }
							},
							{field:'goodsfield02',title:'${gfieldMap.field02}',width:80,hidden:true,isShow:true,
		    						formatter:function(value,rowData,rowIndex){
							       		if(rowData.goodsInfo != null){
							       			return rowData.goodsInfo.field02;
							       		}else{
							       			return value;
							       		}
							        }
							},
							{field:'goodsfield03',title:'${gfieldMap.field03}',width:80,hidden:true,isShow:true,
									formatter:function(value,rowData,rowIndex){
						       		if(rowData.goodsInfo != null){
						       			return rowData.goodsInfo.field03;
						       		}else{
						       			return value;
						       		}
						        }
							},
							{field:'goodsfield04',title:'${gfieldMap.field04}',width:80,hidden:true,isShow:true,
									formatter:function(value,rowData,rowIndex){
							       		if(rowData.goodsInfo != null){
							       			return rowData.goodsInfo.field04;
							       		}else{
							       			return value;
							       		}
							        }
							},
							{field:'goodsfield05',title:'${gfieldMap.field05}',width:80,hidden:true,isShow:true,
									formatter:function(value,rowData,rowIndex){
							       		if(rowData.goodsInfo != null){
							       			return rowData.goodsInfo.field05;
							       		}else{
							       			return value;
							       		}
							        }
							},
							{field:'goodsfield06',title:'${gfieldMap.field06}',width:80,hidden:true,isShow:true,
									formatter:function(value,rowData,rowIndex){
						       		if(rowData.goodsInfo != null){
						       			return rowData.goodsInfo.field06;
						       		}else{
						       			return value;
						       		}
						        }
							},
							{field:'goodsfield07',title:'${gfieldMap.field07}',width:80,hidden:true,isShow:true,
									formatter:function(value,rowData,rowIndex){
								   		if(rowData.goodsInfo != null){
								   			return rowData.goodsInfo.field07;
								   		}else{
								   			return value;
								   		}
								    }
							},
							{field:'goodsfield08',title:'${gfieldMap.field08}',width:80,hidden:true,isShow:true,
									formatter:function(value,rowData,rowIndex){
										if(rowData.goodsInfo != null){
											return rowData.goodsInfo.barcode;
										}else{
											return value;
										}
									}
							},
							{field:'goodsfield09',title:'${gfieldMap.field09}',width:80,hidden:true,isShow:true,
									formatter:function(value,rowData,rowIndex){
										if(rowData.goodsInfo != null){
											return rowData.goodsInfo.field09;
										}else{
											return value;
										}
									}
							}
					]],
			 		fit:true,
			 		method:'post',
			 		rownumbers:true,
			 		idField:'id',
			 		singleSelect:false,
					fitColumns:true,
			 		showFooter: true,
			 		checkOnSelect:true,
			 		selectOnCheck:true,
					onCheckAll:function(){
						countDetailTotalAmount();
					},
					onUncheckAll:function(){
						countDetailTotalAmount();
					},
					onCheck:function(){
						countDetailTotalAmount();
					},
					onUncheck:function(){
						countDetailTotalAmount();
					}
				});
			</c:if>
		});
		function addPurchaseInvoiceByRefer(){
			var rows = $("#account-orderDatagrid-dispatchBillSourcePage").datagrid('getChecked');
			var detailRows = $("#account-detailDatagrid-dispatchBillSourcePage").datagrid('getChecked');
			if(rows==null || rows.length==0){
				$.messager.alert("提醒","请勾选采购订单");
				return false;
			}
			if(detailRows==null || detailRows.length==0){
				$.messager.alert("提醒","请勾选商品明细数据");
				return false;
			}
			var ids = [];
			for(var i=0;i<detailRows.length;i++){
				var id = detailRows[i].id;
				var billtype = detailRows[i].ordertype;
				var billid = detailRows[i].orderid;
				var object = {billtype:billtype,billid:billid,detailid:id}
				ids.push(object);
			}
			$.messager.confirm("提醒","是否确定生成采购发票？",function(r){
				if(r){
					loading("提交中..");
					$.ajax({
			            url :'account/payable/addPurchaseInvoiceByRefer.do',
			            type:'post',
			            dataType:'json',
			            data:{ids:JSON.stringify(ids)},
			            success:function(json){
			            	loaded();
			            	if(json.flag){
			            		$.messager.alert("提醒","生成成功");
			            		$("#account-panel-purchaseInvoicePage").panel({
									href:'account/payable/purchaseInvoiceEditPage.do?id='+json.id,
									title:'',
								    cache:false,
								    maximized:true,
								    border:false
								});
								$("#account-panel-sourceQueryPage").dialog('close', true);
							}else{
								$.messager.alert("提醒","生成失败<br/>"+json.msg);
							}
			            },
			            error:function(){
			            	loaded();
			            	$.messager.alert("错误","生成采购发票出错");
			            }
			        });
	        	}
			});
		}
    	function countDetailTotalAmount(){
    		var rows =  $("#account-detailDatagrid-dispatchBillSourcePage").datagrid('getChecked');
    		var taxamount = 0;
    		var notaxamount = 0;
    		var tax = 0;
    		var auxnum=0;
    		var auxnumdetail="";
    		var auxremainder=0;
    		for(var i=0;i<rows.length;i++){
    			taxamount = Number(taxamount)+Number(rows[i].taxamount == undefined ? 0 : rows[i].taxamount);
    			notaxamount = Number(notaxamount)+Number(rows[i].notaxamount == undefined ? 0 : rows[i].notaxamount);
    			tax = Number(tax)+Number(rows[i].tax == undefined ? 0 : rows[i].tax);
    			auxnum =Number(auxnum)+Number(rows[i].auxnum == undefined ? 0 : rows[i].auxnum);
    			auxremainder =Number(auxremainder)+Number(rows[i].auxremainder == undefined ? 0 : rows[i].auxremainder);
    		}
    		auxnumdetail=auxnum+","+auxremainder;
    		$("#account-detailDatagrid-dispatchBillSourcePage").datagrid("reloadFooter",[{goodsname:'选中金额',taxamount:taxamount,notaxamount:notaxamount,tax:tax,auxnumdetail:auxnumdetail}]);
    	}
	</script>
  </body>
</html>
