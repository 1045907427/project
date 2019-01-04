<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售发货通知单参照上游单据页面</title>
  </head>
  
  <body>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',split:true,border:false" style="height:260px">
			<table id="sales-orderDatagrid-dispatchBillSourcePage"></table>
		</div>
		<div data-options="region:'center',border:false">
			<table id="sales-detailDatagrid-dispatchBillSourcePage"></table>
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			$("#sales-detailDatagrid-dispatchBillSourcePage").datagrid({
				columns:[[	
						{field:'goodsid',title:'商品编码',width:120,align:'left'},
    					{field:'goodsname', title:'商品名称', width:100,align:'left',
    						formatter:function(value,rowData,rowIndex){
					       		if(rowData.goodsInfo != null){
					       			return rowData.goodsInfo.name;
					       		}else{
					       			return "";
				       			}
					        }
    					},
    					{field:'model', title:'规格型号',width:80,align:'left',
    						formatter:function(value,rowData,rowIndex){
					       		if(rowData.goodsInfo != null){
					       			return rowData.goodsInfo.model;
					       		}else{
					       			return "";
					       		}
					        }
    					},
    					{field:'brandName', title:'商品品牌',width:80,align:'left',
    						formatter:function(value,rowData,rowIndex){
					       		if(rowData.goodsInfo != null){
					       			return rowData.goodsInfo.brandName;
					       		}else{
					       			return "";
					       		}
					        }
    					},
    					{field:'unitname', title:'计量单位',width:70,align:'left'},
    					{field:'unitnum', title:'数量',width:60,align:'right',
    						formatter:function(value,row,index){
				        		return formatterMoney(value);
					        }
				    	},
    					{field:'auxunit', title:'辅单位',width:60,align:'left',
    						formatter: function(value,row,index){
    							return row.auxunitname;
    						}
    					},
    					{field:'auxnumdetail', title:'辅单位数量',width:80,align:'right'},
    					{field:'deliverydate', title:'交货日期',width:80,align:'left'},
    					{field:'remark', title:'备注',width:100,align:'left'}	
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
