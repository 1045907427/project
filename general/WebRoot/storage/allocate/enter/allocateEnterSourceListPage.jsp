<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>调拨出库单参照上游单据列表页面</title>
  </head>
  
  <body>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',split:true,border:false" style="height:260px">
			<table id="storage-orderDatagrid-dispatchBillSourcePage"></table>
		</div>
		<div data-options="region:'center',border:false">
			<table id="storage-detailDatagrid-dispatchBillSourcePage"></table>
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			$("#storage-detailDatagrid-dispatchBillSourcePage").datagrid({
				columns:[[	
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
	    					{field:'taxprice', title:'含税单价',width:60,align:'right',hidden:true,
	    						formatter:function(value,row,index){
					        		return formatterMoney(value);
						        }
					    	},
	    					{field:'taxamount', title:'含税金额',width:60,align:'right',hidden:true,
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
				]],
		 		fit:true,
		 		method:'post',
		 		rownumbers:true,
		 		idField:'id',
		 		singleSelect:true,
				fitColumns:true
			});
		});
	</script>
  </body>
</html>
