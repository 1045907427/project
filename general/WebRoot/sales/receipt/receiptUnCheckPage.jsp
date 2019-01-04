<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售发货回单页面</title>
    <%@include file="/include.jsp" %>   
  </head>  
  <body>
    <input type="hidden" id="sales-backid-receiptPage" value="${id }" />
    <input type="hidden" id="sales-parentid-receiptPage" /><!-- 参照上游单据的编码 -->
    <div id="sales-layout-receiptPage" class="easyui-layout" data-options="fit:true,border:false">
    	<div data-options="region:'north',border:false">
    		<div class="buttonBG" id="sales-buttons-receiptPage" style="height:26px;"></div>
    	</div>
    	<div data-options="region:'center',border:false">
    		<div id="sales-panel-receiptPage">
    		</div>
    	</div>
    </div>
    <div class="easyui-dialog" id="sales-sourceQueryDialog-receiptPage" data-options="closed:true"></div>
    <div class="easyui-dialog" id="sales-sourceDialog-receiptPage" data-options="closed:true"></div>
  	<div class="easyui-dialog" id="sales-dialog-receiptPage" closed="true"></div>
  	<div id="sales-dialog-reletionRejectPage"></div>
  	<div id="sales-dialog-reletionRejectPage-upp"></div>
    <script type="text/javascript">
    	var receipt_url = "sales/receiptAddPage.do";
   		receipt_url = "sales/receiptViewPage.do?id=${id}";
		var wareListJson = $("#sales-datagrid-receiptAddPage").createGridColumnLoad({ //以下为商品明细datagrid字段
    		name:'t_sales_receipt_detail',
    		frozenCol : [[
		    			]],
    		commonCol: [[
    					{field:'ck',checkbox:true},
    		            {field:'goodsid',title:'商品编码',width:60,align:' left'},
    					{field:'goodsname', title:'商品名称', width:220,align:'left',aliascol:'goodsid',
    						formatter:function(value,rowData,rowIndex){
					       		if(rowData.goodsInfo != null){
				       				return rowData.goodsInfo.name;
					       		}else{
					       			return "";
				       			}
					        }
    					},
    					{field:'barcode', title:'条形码',width:90,align:'left',aliascol:'goodsid',
    						formatter:function(value,rowData,rowIndex){
					       		if(rowData.goodsInfo != null){
					       			return rowData.goodsInfo.barcode;
					       		}else{
					       			return "";
					       		}
					        }
    					},
    					{field:'brandName', title:'商品品牌',width:80,align:'left',aliascol:'goodsid',hidden:true,
    						formatter:function(value,rowData,rowIndex){
					       		if(rowData.goodsInfo != null){
					       			return rowData.goodsInfo.brandName;
					       		}else{
					       			return "";
					       		}
					        }
    					},
    					{field:'boxnum', title:'箱装量',aliascol:'goodsid',width:45,align:'right',
    						formatter:function(value,rowData,rowIndex){
					       		if(rowData.goodsInfo != null){
					       			return formatterBigNumNoLen(rowData.goodsInfo.boxnum);
					       		}else{
					       			return "";
					       		}
					        }
    					},
    					{field:'unitname', title:'单位',width:35,align:'left'},
    					{field:'taxprice', title:'单价',width:60,align:'right',
    						formatter:function(value,row,index){
    							if(row.isdiscount!='1' && row.isdiscount!='2'){
	    							if(row.taxprice!=row.inittaxprice){
					        			return '<span style="color: blue;cursor: pointer;" title="初始单价为：'+row.inittaxprice+'">'+formatterMoney(value)+'</span>';
						        	}else{
						        		return formatterMoney(value);
						        	}
					        	}
					        },
					        editor:{
					        	type:'numberbox',
					        	options:{
					        		precision:6
					        	}
					        },
					        styler:function(value,row,index){
					        	if(row.taxprice!=row.inittaxprice){
					        		return 'background-color:yellow;';
					        	}
					        }
				    	},
    					{field:'unitnum', title:'发货数量',width:60,align:'right',
    						formatter:function(value,row,index){
				        		return formatterBigNumNoLen(value);
					        }
				    	},
    					{field:'taxamount', title:'发货金额',width:80,align:'right',
    						formatter:function(value,row,index){
    							if(row.discountamount>0){
    								return '<span style="color: blue;cursor: pointer;" title="该商品有折扣，折扣金额为：'+formatterMoney(row.discountamount)+'">'+formatterMoney(value)+'</span>';
    							}else{
				        			return formatterMoney(value);
				        		}
					        }
					    },
					    {field:'receiptnum', title:'客户接收数量',width:80,align:'right',
    						formatter:function(value,row,index){
    							if(row.isdiscount!='1' && row.isdiscount!='2'){
					        		if(row.goodsInfo != null &&(row.receiptnum-row.unitnum!=0) && row.receiptnum!=null){
						        		return '<span style="color: blue;cursor: pointer;" title="'+row.remark+'">'+formatterBigNumNoLen(value)+'</span>';
						        	}else{
						        		return formatterBigNumNoLen(value);
						        	}
					        	}
					        },
					        editor:{
					        	type:'numberbox',
					        	options:{
					        		validType:'receiptMax'
					        	}
					        },
					        styler:function(value,row,index){
					        	if(row.goodsInfo != null && (row.receiptnum-row.unitnum!=0) && row.receiptnum!=null){
					        		return 'background-color:yellow;';
					        	}
					        }
				    	},
					    {field:'receipttaxamount', title:'应收金额',width:80,align:'right',
    						formatter:function(value,row,index){
			        			return formatterMoney(value);
					        }
					    },
    					{field:'taxtype', title:'税种',width:70,align:'left',hidden:true,
    						formatter:function(value,row,index){
    							return row.taxtypename;
    						}
    					},
    					{field:'notaxprice', title:'未税单价',width:80,align:'right', hidden:true,
    						formatter:function(value,row,index){
				        		return formatterMoney(value);
					        }
					    },
    					{field:'notaxamount', title:'未税金额',width:80,align:'right', hidden:true,
    						formatter:function(value,row,index){
				        		return formatterMoney(value);
					        }
					    },
    					{field:'tax', title:'税额',width:80,align:'right',hidden:true,
    						formatter:function(value,row,index){
				        		return formatterMoney(value);
					        }
					    },
    					{field:'storagelocation', title:'库位',width:80,align:'left',hidden:true,
    						formatter:function(value,rowData,rowIndex){
					       		return rowData.storagelocationname;
					    	}
					    },
					    {field:'auxnumdetail', title:'辅数量',width:80,align:'right'},
    					{field:'remark', title:'备注',width:80,align:'left',
    						formatter:function(value,row,index){
    							if(value!=null){
   									return '<span style="cursor: pointer;" title="'+row.remark+'">'+row.remark+'</span>';
					        	}
					        }
    					},
    					{field:'rejectnummain', title:'已制退货通知单主单位数量', width:100, align:'right', hidden:true,
    						formatter:function(value,row,index){
				        		return formatterBigNumNoLen(value);
					        }
				    	},
    					{field:'rejectnumaux', title:'已制退货通知单辅单位数量', width:100, align:'right', hidden:true,
    						formatter:function(value,row,index){
				        		return formatterBigNumNoLen(value);
					        }
				    	},
    					{field:'norejectnummain', title:'未制退货通知单主单位数量', width:100, align:'right', hidden:true,
    						formatter:function(value,row,index){
				        		return formatterBigNumNoLen(value);
					        }
				    	},
    					{field:'norejectnumaux', title:'未制退货通知单辅单位数量', width:100, align:'right', hidden:true,
    						formatter:function(value,row,index){
				        		return formatterBigNumNoLen(value);
					        }
				    	},
    					{field:'rejectamountnotax', title:'已制退货通知单未税金额', width:100, align:'right', hidden:true,
    						formatter:function(value,row,index){
				        		return formatterMoney(value);
					        }
				    	},
    					{field:'rejectamounttax', title:'已制退货通知单含税金额', width:100, align:'right', hidden:true,
    						formatter:function(value,row,index){
				        		return formatterMoney(value);
					        }
				    	},
    					{field:'norejectamountnotax', title:'未制退货通知单未税金额', width:100, align:'right', hidden:true,
    						formatter:function(value,row,index){
				        		return formatterMoney(value);
					        }
				    	},
    					{field:'norejectamounttax', title:'未制退货通知单含税金额', width:100, align:'right', hidden:true,
    						formatter:function(value,row,index){
				        		return formatterMoney(value);
					        }
				    	}
    				  ]]
    	});
    	$(function(){
    		$("#sales-panel-receiptPage").panel({
				href:receipt_url,
			    cache:false,
			    maximized:true,
			    border:false
			});
			//按钮
			$("#sales-buttons-receiptPage").buttonWidget({
				initButton:[
					{
						type: 'button-relation',
						button:[
							{
								type: 'relation-upper-view',
								handler: function(){
									var saleorderid = $("#sales-saleorderid-receiptAddPage").val();
									if(saleorderid == ''){
										return false;
									}
									//var basePath = $("#basePath").attr("href");
									//top.addOrUpdateTab(basePath+'storage/showSaleOutViewPage.do?id='+ parentid, '发货单');
									$("#sales-dialog-reletionRejectPage-upp").dialog({
										title:'上游出库单列表',
										width:600,
										height:300,
										closed:false,
										modal:true,
										cache:false,
										maximizable:true,
										resizable:true,
										href:'sales/showUpperSaleoutList.do?saleorderid='+saleorderid
									});
								}
							}
						]
					},
					{
						type: 'button-back',
						handler: function(data){
							$("#sales-backid-receiptPage").val(data.id);
							refreshPanel('sales/receiptEditPage.do?id='+ data.id);
						}
					},
					{
						type: 'button-next',
						handler: function(data){
							$("#sales-backid-receiptPage").val(data.id);
							refreshPanel('sales/receiptEditPage.do?id='+ data.id);
						}
					}
				],
				layoutid:'sales-layout-receiptPage',
				model: 'bill',
				type: 'view',
				tab:'销售发货回单列表',
				taburl:'/sales/receiptListUnAuditPage.do',
				id:'${id}',
				datagrid:'sales-datagrid-receiptListPage'
			});
			//enter建 编辑实际接收数量
			$(document).die("keydown").live("keydown",function(event){
				var type = $("#sales-buttons-receiptPage").buttonWidget("getOperType");
				if(type=="edit"){
					if(event.keyCode==13){
						if($('#sales-datagrid-receiptAddPage').datagrid('validateRow', editIndex)){
							var row = $wareList.datagrid('getRows')[editIndex+1];
    						if(row.isdiscount!='1' && row.isdiscount!='2'){
	   							onClickCell(editIndex+1, editfiled);
	   						}
		    			}
					}
				}
			});
		});
    	function refreshPanel(url){ //更新panel
    		$("#sales-panel-receiptPage").panel('refresh', url);
    	}
		function countTotal(){ //计算合计
    		var rows = $("#sales-datagrid-receiptAddPage").datagrid('getRows');
    		var leng = rows.length;
    		var unitnum = 0;
    		var taxamount = 0;
    		var notaxamount = 0;
    		var tax = 0;
    		var receipttaxamount = 0;
    		var receiptnum = 0;
    		for(var i=0; i<leng; i++){
    			unitnum += Number(rows[i].unitnum == undefined ? 0 : rows[i].unitnum);
    			taxamount += Number(rows[i].taxamount == undefined ? 0 : rows[i].taxamount);
    			notaxamount += Number(rows[i].notaxamount == undefined ? 0 : rows[i].notaxamount);
    			tax += Number(rows[i].tax == undefined ? 0 : rows[i].tax);
    			receiptnum += Number(rows[i].receiptnum == undefined ? 0 : rows[i].receiptnum);
    			receipttaxamount += Number(rows[i].receipttaxamount == undefined ? 0 : rows[i].receipttaxamount);
    		}
    		$("#sales-datagrid-receiptAddPage").datagrid('reloadFooter',[{goodsid:'合计', unitnum: unitnum, taxamount: taxamount, notaxamount: notaxamount, tax: tax,receipttaxamount:receipttaxamount,receiptnum:receiptnum}]);
    	}
		function customerCheck(){ //添加商品明细前确定客户已选
			var customer = $("#sales-customer-receiptAddPage-hidden").val();
			if(customer == ''){
				$.messager.alert("提醒","请先选择客户再进行添加商品信息");
				$("#sales-customer-receiptAddPage").focus();
				return false;
			}
			else{
				return customer;
			}
		}
		function beginAddDetail(){ //开始添加商品信息
			var customer = $("#sales-customer-receiptAddPage").val();
			if(customer == ''){
				$.messager.alert("提醒","请先选择客户再进行添加商品信息");
				$("#sales-customer-receiptAddPage").focus();
				return false;
			}
	    	$("#sales-dialog-receiptAddPage").dialog({ //弹出新添加窗口
	    		title:'商品信息(添加)',
	    		maximizable:true,
	    		width:600,
	    		height:450,
	    		closed:false,
	    		modal:true,
	    		cache:false,
	    		resizable:true,
	    		buttons:[{
					text:'确定',
					iconCls:'button-save',
					handler:function(){
						addSaveDetail();
					}
				},{
					text:'继续添加',
					iconCls:'button-save',
					handler:function(){
						addSaveDetail(true);
					}
				}],
	    		href:'sales/receiptDetailAddPage.do?cid='+ customer
	    	});
		}
    	function addSaveDetail(go){ //添加新数据确定后操作，
    		var flag = $("#sales-form-receiptDetailAddPage").form('validate');
		  	if(flag==false){
		  		return false;
		  	}
    		var form = $("#sales-form-receiptDetailAddPage").serializeJSON();
    		var goodsJson = $("#sales-goodsId-receiptDetailAddPage").widget('getObject');
    		form.goodsInfo = goodsJson;
    		var customer = $("#sales-customer-receiptAddPage-hidden").val();
    		var rowIndex = 0;
    		var rows = $wareList.datagrid('getRows');
    		for(var i=0; i<rows.length; i++){
    			var rowJson = rows[i];
    			if(rowJson.goodsid == undefined){
    				rowIndex = i;
    				break;
    			}
    		}
    		if(rowIndex == rows.length - 1){
    			$wareList.datagrid('appendRow',{}); //如果是最后一行则添加一新行
    		}
    		$wareList.datagrid('updateRow',{index:rowIndex, row:form}); //将数据更新到列表中
    		if(go){ //go为true确定并继续添加一条
    			$("#sales-dialog-receiptAddPage").dialog('refresh', 'sales/receiptDetailAddPage.do?cid='+ customer)
    		}
    		else{ //否则直接关闭
    			$("#sales-dialog-receiptAddPage").dialog('close', true)
    		}
    		$("#sales-customer-receiptAddPage").widget('readonly', true);
    		countTotal(); //第添加一条商品明细计算一次合计
    	}
	</script>
  </body>
</html>
