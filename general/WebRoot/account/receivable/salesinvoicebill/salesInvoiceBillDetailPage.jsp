<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售发票列表页面</title>
  </head>
  
  <body>
	<div id="account-datagrid-toolbar-salesInvoiceDetailPage" style="padding:2px;height:auto">
		<a href="javaScript:void(0);" id="account-export-salesInvoiceDetailPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
	</div>
	<table id="account-datagrid-salesInvoiceDetailPage"></table>
     <script type="text/javascript">
     var salesInvoiceJson = $("#account-datagrid-salesInvoiceDetailPage").createGridColumnLoad({
			frozenCol : [[
		    			]],
			commonCol : [[{field:'billid',title:'销售发票编号',width:120, align: 'left'},
			          	  {field:'customerid',title:'客户编码',width:60,align:'left'},
					  	  {field:'customername',title:'客户名称',width:100,align:'left'},
						  {field:'businessdate',title:'业务日期',width:80,align:'left'},
						  {field:'sourcetype',title:'单据类型',width:80,align:'left',
							  formatter:function(value,row,index){
					        		if(value == "1"){
					        			return "销售回单";
					        		}
					        		else if(value == "2"){
					        			return "销售退货通知单";
					        		}
					        		else if(value == "3"){
					        			return "应收款冲差单";
					        		}else if(value == "4"){
                                        return "发货单";
                                    }
						        }
						  },
						  {field:'sourceid',title:'单据编号',width:120,align:'left'},
						  {field:'goodsid',title:'商品编码',width:60,align:'left'},
						  {field:'goodsname',title:'商品名称',width:150,align:'left',
							  formatter:function(value,rowData,rowIndex){
								  if(rowData.goodsInfo != null){
						       		return rowData.goodsInfo.name;
								  }
						      }
						  },
	    					{field:'boxnum', title:'箱装量',width:40,aliascol:'goodsid',hidden:true,
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
					    	{field:'taxprice', title:'单价',width:60,align:'right',
	    						formatter:function(value,row,index){
	    							if(row.isdiscount!='1' && row.isdiscount!='2'){
					        			return formatterMoney(value);
					        		}
						        }
					    	},
	    					{field:'taxamount', title:'金额',width:60,align:'right',
	    						formatter:function(value,row,index){
					        		return formatterMoney(value);
						        }
						    },
						    {field:'notaxprice', title:'未税单价',width:60,align:'right',
	    						formatter:function(value,row,index){
					        		return formatterMoney(value);
						        }
					    	},
	    					{field:'notaxamount', title:'未税金额',width:60,align:'right',
	    						formatter:function(value,row,index){
					        		return formatterMoney(value);
						        }
						    },
						    {field:'taxtypename', title:'税种',width:60,aliascol:'taxtype',hidden:true,align:'right'},
						    {field:'tax', title:'税额',width:60,align:'right',hidden:true,
	    						formatter:function(value,row,index){
					        		return formatterMoney(value);
						        }
						    },
						    {field:'auxnumdetail', title:'辅数量',width:60,align:'right'},
						    {field:'remark', title:'备注',width:120}
			              ]]
     		});
    	$(function(){
			$("#account-datagrid-salesInvoiceDetailPage").datagrid({ 
				authority:salesInvoiceJson,
		 		frozenColumns: salesInvoiceJson.frozen,
				columns:salesInvoiceJson.common,
		 		fit:true,
		 		method:'post',
		 		rownumbers:true,
		 		pagination:true,
		 		idField:'sourceid',
		 		singleSelect:true,
		 		showFooter: true,
				url: 'account/receivable/showSalesInvoiceBillDetailData.do?id=${id}',
				toolbar:'#account-datagrid-toolbar-salesInvoiceDetailPage',
				pageSize:500
			}).datagrid("columnMoving");
			$("#account-export-salesInvoiceDetailPage").Excel('export',{
		 		type:'exportUserdefined',
		 		name:'销售开票明细',
		 		url:'account/receivable/exportSalesInvoiceBillDetailData.do?id=${id}'
			});
    	});
    </script>
  </body>
</html>
