<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>客户对应商品数页面</title>
  </head>
  
  <body>
    <table id="bigsaleout-datagrid-customergodsnum"></table>
    <script type="text/javascript">
    	$(function(){
    		var goodsTableColJson = $("#bigsaleout-datagrid-customergodsnum").createGridColumnLoad({
				name :'storage_saleout_detail',
				frozenCol : [[]],
				commonCol : [[
						{field:'customerid',title:'客户编码',width:60},
	   					{field:'customername', title:'客户名称', width:220,isShow:true,
	   						formatter:function(value,rowData,rowIndex){
					       		return rowData.customername;
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
					    {field:'tax', title:'税额',width:60,align:'right',hidden:true,
	   						formatter:function(value,row,index){
				        		return formatterMoney(value);
					        }
					    },
					    {field:'auxnumdetail', title:'辅数量',width:60,align:'right'}
					]]
			});
    		$("#bigsaleout-datagrid-customergodsnum").datagrid({
	   			authority:goodsTableColJson,
    			columns: goodsTableColJson.common,
    			frozenColumns: goodsTableColJson.frozen,
	   			method:'post',
	   			title:'',
	   			fit:true,
	   			rownumbers:true,
	  			singleSelect:true,
	  			border:false,
	  			pagination:true,
	  			showFooter: true,
	  			pageSize:100,
	  			url:'storage/getBigSaleOutCustomerGoodsNumList.do?bigsaleoutid=${bigsaleoutid}&goodsid=${goodsid}'
	    	});
    	});
    </script>
  </body>
</html>
