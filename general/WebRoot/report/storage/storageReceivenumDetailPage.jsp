<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>发货单参照上游单据列表页面</title>
  </head>
  
  <body>
	<table id="report-table-receivenumDetailPage"></table>
	<script type="text/javascript">
		$(function(){
			$("#report-table-receivenumDetailPage").datagrid({
				columns:[[	
						{field:'billid',title:'单据编号',width:120,align:'left',
							formatter:function(value,rowData,rowIndex){
								  if(rowData.goodsid!=null && rowData.goodsid!=""){
									  if(rowData.billmodel=='stockInit'){
										  return value;
									  }else if(rowData.billmodel=='purchaseEnter'){
										  return "<a href='javascript:showPurchaseEnterView(\""+value+"\");' >"+value+"</a>";
									  }else if(rowData.billmodel=='saleout'){
							    		  return "<a href='javascript:showSaleoutView(\""+value+"\");' >"+value+"</a>";
									  }else if(rowData.billmodel=='storageOtherOut'){
							    		  return "<a href='javascript:showStorageOtherOutView(\""+value+"\");' >"+value+"</a>";
									  }else if(rowData.billmodel=='saleRejectEnter'){
							    		  return "<a href='javascript:showSaleRejectEnterView(\""+value+"\");' >"+value+"</a>";
									  }else if(rowData.billmodel=='storageOtherEnter'){
							    		  return "<a href='javascript:showStorageOtherEnterView(\""+value+"\");' >"+value+"</a>";
									  }else if(rowData.billmodel=='allocateOut'){
							    		  return "<a href='javascript:showAllocateOutView(\""+value+"\");' >"+value+"</a>";
									  }else if(rowData.billmodel=='purchaseOut'){
							    		  return "<a href='javascript:showPurchaseRejectOutView(\""+value+"\");' >"+value+"</a>";
									  }else if(rowData.billmodel=='adjustments'){
							    		  return "<a href='javascript:showAdjustmentsView(\""+value+"\");' >"+value+"</a>";
									  }else if(rowData.billmodel=='storageDeliveryEnter'){
							    		  return "<a href='javascript:showStorageDeliveryEnterView(\""+value+"\");' >"+value+"</a>";
									  }else if(rowData.billmodel=='StorageDeliveryOut'){
							    		  return "<a href='javascript:showStorageDeliveryOutView(\""+value+"\");' >"+value+"</a>";
									  }
								  }else{
								      return value;
								  }
							  }  	
						},
						{field:'billmodelname',title:'单据类型',width:95,align:'left'},
						{field:'goodsid',title:'商品编码',width:60,align:'left',hidden:true},
    					{field:'goodsname', title:'商品名称', width:150,align:'left',hidden:true},
    					{field:'barcode', title:'条形码', width:100,align:'left',hidden:true},
    					{field:'brandname', title:'商品品牌',width:80,align:'left',hidden:true},
    					{field:'boxnum', title:'箱装量',width:40,align:'right',
    						formatter:function(value,row,index){
				        		return formatterBigNumNoLen(value);
					        }
    					},
    					{field:'unitname', title:'单位',width:35,align:'left'},
    					{field:'receivenum', title:'数量',width:60,align:'right',
    						formatter:function(value,row,index){
				        		return formatterBigNumNoLen(value);
					        }
				    	},
				    	{field:'receivenumdetail', title:'辅数量',width:60,align:'left'},
    					{field:'addtime', title:'添加时间',width:140,align:'right',
				    		 formatter: function(dateValue,row,index){
							  	 if(dateValue!=undefined){
							  		 var newstr=dateValue.replace("T"," ");
							  		 return newstr;
							     }
							     return " ";
							  }   
				    	},
    					{field:'remark', title:'备注',width:100,align:'left'}	
				]],
		 		fit:true,
		 		method:'post',
		 		rownumbers:true,
		 		idField:'id',
		 		singleSelect:true,
				url: 'report/storage/showReceivenumDetailList.do?goodsid=${goodsid}&storageid=${storageid}&businessdate1=${businessdate1}&businessdate2=${businessdate2}',
			});
		});
	</script>
  </body>
</html>
