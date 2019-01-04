<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>发货单参照上游单据列表页面</title>
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
    					{field:'unitname', title:'主单位',width:50,align:'left'},
    					{field:'unitnum', title:'数量',width:60,align:'right',
    						formatter:function(value,row,index){
				        		return formatterBigNumNoLen(value);
					        }
				    	},
    					{field:'auxunit', title:'辅单位',width:50,align:'left',
    						formatter: function(value,row,index){
    							return row.auxunitname;
    						}
    					},
    					{field:'auxnumdetail', title:'辅数量',width:60,align:'right'},
    					{field:'deliverydate', title:'交货日期',width:80,align:'left'},
    					{field:'deliverytype', title:'发货类型',width:80,align:'left',
    						formatter:function(value,rowData,rowIndex){
    							if(rowData.goodsid != undefined){ 
    								if(value == undefined || value == '' || value == null){
    									return '普通';
    								}
	    							else{
	    								return getSysCodeName('deliverytype', value);
	    							}
    							}
    						}
    					},
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
