<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>采购发票列表页面</title>
    <%@include file="/include.jsp" %>   
  </head>
  
  <body>
	<table id="account-datagrid-purchaseInvoicePage" data-options="border:false"></table>
	
     <script type="text/javascript">
     	var initQueryJSON = $("#account-form-query-purchaseInvoicePage").serializeJSON();
    	$(function(){
    		var orderListJson = $("#account-datagrid-purchaseInvoicePage").createGridColumnLoad({
				name :'t_purchase_arrivalorder',
				frozenCol : [[
			    			]],
				commonCol : [[{field:'id',title:'编号',width:120,sortable:true, align: 'left'},
							  {field:'businessdate',title:'业务日期',sortable:true,width:80,align:'left'},
							  {field:'supplierid',title:'供应商编码',width:70,sortable:true},
							  {field:'suppliername',title:'供应商名称',width:100,isShow:true},
							  {field:'handlerid',title:'对方经手人',width:100,align:'left',
							  		formatter:function(value,row,index){
						        		return row.handlername;
							        }
							  },
							  {field:'buydeptid',title:'采购部门',width:120,align:'left',
							  		formatter:function(value,row,index){
						        		return row.buydeptname;
							        }
							  },
							  {field:'buyuserid',title:'采购人员',width:100,align:'left',
							  		formatter:function(value,row,index){
						        		return row.buyusername;
							        }
							  },
							  {field:'field01',title:'含税金额',resizable:true,align:'right',
		    						formatter:function(value,row,index){
						        		return formatterMoney(value);
							        }
							  },
							  {field:'field02',title:'未税金额',resizable:true,align:'right',
		    						formatter:function(value,row,index){
						        		return formatterMoney(value);
							        }
							  },
							  {field:'field03',title:'税额',resizable:true,align:'right',
		    						formatter:function(value,row,index){
						        		return formatterMoney(value);
							        }
							  },
							  {field:'addusername',title:'制单人',width:70,align:'left'},
							  {field:'addtime',title:'制单时间',width:100,align:'left',hidden:true},
							  {field:'status',title:'状态',width:60,align:'left',
							  		formatter:function(value,row,index){
						        		return getSysCodeName('status', value);
							        }
							  },
							  {field:'isinvoice',title:'发票状态',width:70,align:'left',
							  		formatter:function(value,row,index){
						        		if(value == "0"){
						        			return "未开票";
						        		}
						        		else if(value == "1"){
						        			return "已开票";
						        		}
						        		else if(value == "2"){
						        			return "已核销";
						        		}
						        		else if(value == "3"){
						        			return "未开票";
						        		}
							        }
							  }
				 ]]
			});
			$("#account-datagrid-purchaseInvoicePage").datagrid({ 
		 		authority:orderListJson,
		 		frozenColumns: orderListJson.frozen,
				columns:orderListJson.common,
		 		fit:true,
		 		method:'post',
		 		rownumbers:true,
		 		idField:'id',
		 		singleSelect:true,
		 		selectOnCheck:true,
		 		//checkOnSelect:true,
				data: JSON.parse('${list}'),
				onDblClickRow:function(rowIndex, rowData){
					if("${sourcetype}"=="1"){
						top.addOrUpdateTab('purchase/arrivalorder/arrivalOrderPage.do?type=view&id='+ rowData.id, "采购进货单");
					}else if("${sourcetype}"=="2"){
						top.addOrUpdateTab('purchase/returnorder/returnOrderPage.do?type=view&id='+ rowData.id, "采购退货通知单");
					}
				}
			}).datagrid("columnMoving");
    	});
    </script>
  </body>
</html>
