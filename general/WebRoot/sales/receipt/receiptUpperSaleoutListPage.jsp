<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head>
  
  <body>
    <table id="receipt-Upper-SaleoutList"></table>
    <script type="text/javascript">
    	$(function(){
    		var saleoutJson = $("#receipt-Upper-SaleoutList").createGridColumnLoad({
				name :'storage_saleout',
				frozenCol : [[
			    			]],
				commonCol : [[
							  {field:'id',title:'编号',width:130,sortable:true},
							  {field:'saleorderid',title:'销售订单编号',width:130,sortable:true},
							  {field:'businessdate',title:'业务日期',width:80,sortable:true},
							  {field:'storageid',title:'出库仓库',width:80,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return rowData.storagename;
					        	}
							  },
							  {field:'customerid',title:'客户编码',width:60,sortable:true},
							  {field:'customername',title:'客户名称',width:150},
							  {field:'salesdept',title:'销售部门',width:80,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return rowData.salesdeptname;
					        	}
							  },
							  {field:'salesuser',title:'客户业务员',width:80,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return rowData.salesusername;
					        	}
							  },
							  {field:'sourcetype',title:'来源类型',width:90,sortable:true,hidden:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return getSysCodeName("saleout-sourcetype",value);
					        	}
							  },
							  {field:'sourceid',title:'来源编号',width:80,sortable:true},
							  {field:'status',title:'状态',width:60,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return getSysCodeName("status",value);
					        	}
							  },
							  {field:'addusername',title:'制单人',width:80,sortable:true},
							  {field:'addtime',title:'制单时间',width:120,sortable:true},
							  {field:'auditusername',title:'审核人',width:80,sortable:true,hidden:true},
							  {field:'audittime',title:'审核时间',width:80,sortable:true,hidden:true},
							  {field:'stopusername',title:'中止人',width:80,hidden:true,sortable:true},
							  {field:'stoptime',title:'中止时间',width:80,hidden:true,sortable:true},
							  {field:'printtip',title:'打印提示',width:60,align:'left',isShow:true,
		    						formatter:function(value,row,index){
					        			if(row.status=='3'){
						        			return "发货单";
					        			}else if(row.status=='2'){
						        			return "配货单";
					        			}
						        	}
						  	   },
							   {field:'remark',title:'备注',width:80,sortable:true},
							   {field:'printtimes',title:'发货单打印次数',width:100},
							   {field:'phprinttimes',title:'配货单打印次数',width:100}
				             ]]
			});
			$("#receipt-Upper-SaleoutList").datagrid({ 
		 		authority:saleoutJson,
		 		frozenColumns: saleoutJson.frozen,
				columns:saleoutJson.common,
		 		fit:true,
		 		method:'post',
		 		rownumbers:true,
		 		idField:'id',
		 		singleSelect:false,
		 		data:JSON.parse('${jsonList}'),
				onDblClickRow:function(rowIndex, rowData){
					var basePath = $("#basePath").attr("href");
					top.addOrUpdateTab(basePath+'storage/showSaleOutEditPage.do?id='+ rowData.id, "发货单查看");
				}
			}).datagrid("columnMoving");
    	});
    </script>
  </body>
</html>
