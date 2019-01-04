<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售回单列表页面</title>
  </head>
  <body>
    <table id="sales-datagrid-receiptUnWriteoffListPage"></table>
    <script type="text/javascript">
    	$(function(){
			var orderListJson = $("#sales-datagrid-receiptUnWriteoffListPage").createGridColumnLoad({
				name :'t_sales_receipt',
				frozenCol : [[
							  
			    			]],
				commonCol : [[{field:'id',title:'编号',width:120, align: 'left',sortable:true},
							  {field:'saleorderid',title:'销售订单编号',width:120, align: 'left',sortable:true},
							  {field:'businessdate',title:'业务日期',width:80,align:'left',sortable:true},
							  {field:'customerid',title:'客户编码',width:60,align:'left',sortable:true},
							  {field:'customername',title:'客户名称',width:100,align:'left',sortable:true,isShow:true},
							  {field:'handlerid',title:'对方经手人',width:70,align:'left'},
							  {field:'salesdept',title:'销售部门',width:80,align:'left',sortable:true},
							  {field:'salesuser',title:'客户业务员',width:80,align:'left',sortable:true},
							  {field:'totaltaxamount',title:'发货金额',width:80,align:'right',isShow:true,
		    						formatter:function(value,row,index){
						        		return formatterMoney(value);
							        }
							  },
							  {field:'totalreceipttaxamount',title:'应收金额',width:80,align:'right',isShow:true,
		    						formatter:function(value,row,index){
						        		return formatterMoney(value);
							        }
							  },
							  {field:'duefromdate',title:'应收日期',width:80,align:'left',sortable:true},
							  {field:'canceldate',title:'核销日期',width:80,align:'left',sortable:true},
							  {field:'status',title:'状态',width:60,align:'left',sortable:true,
							  		formatter:function(value,row,index){
						        		return getSysCodeName('status', value);
							        }
							  },
							  {field:'isinvoice',title:'抽单状态',width:70,align:'left',sortable:true,
							  		formatter:function(value,row,index){
						        		if(value == "0"){
						        			return "未申请";
						        		}
						        		else if(value == "1"){
						        			return "已申请";
						        		}
						        		else if(value == "2"){
						        			return "已核销";
						        		}
						        		else if(value == "3"){
						        			return "未申请";
						        		}else if(value == "4"){
						        			return "申请中";
						        		}else if(value == "5"){
						        			return "部分核销";
						        		}
							        }
							  },
							  {field:'source',title:'来源类型',width:80,align:'left',hidden:true,sortable:true,
							  	formatter:function(value,row,index){
							  		if(value == "1"){
							  			return "发货单";
							  		}
							  		else{
							  			return "无";
							  		}
							    }
							  },
							  {field:'billno',title:'来源编号',width:120,align:'left',sortable:true},
							  {field:'indooruserid',title:'销售内勤',width:60,sortable:true,
							  	formatter:function(value,rowData,index){
					        		return rowData.indoorusername;
						        }
							  },
							  {field:'addusername',title:'制单人',width:80,sortable:true},
							  {field:'addtime',title:'制单时间',width:80,sortable:true,hidden:true},
							  {field:'auditusername',title:'审核人',width:80,sortable:true,hidden:true},
							  {field:'audittime',title:'审核时间',width:80,sortable:true,hidden:true},
							  {field:'remark',title:'备注',width:100,align:'left'}
				              ]]
			});
			$("#sales-datagrid-receiptUnWriteoffListPage").datagrid({ 
		 		authority:orderListJson,
		 		frozenColumns:[[{field:'receiptcheck',checkbox:true}]],
				columns:orderListJson.common,
		 		fit:true,
		 		method:'post',
		 		rownumbers:true,
		 		idField:'id',
		 		singleSelect:false,
    			showFooter: true,
    			url:'account/receivable/showReceiptUnWriteOffData.do?invoiceid=${invoiceid}',
    			onLoadSuccess:function(data){
    				var receiptids = "${receiptids}";
    				var idsArr = receiptids.split(",");
    				for(var i=0;i<idsArr.length;i++){
    					var id = idsArr[i];
    					if(id.indexOf("CC")==-1){
	    					var rowIndex = $("#sales-datagrid-receiptUnWriteoffListPage").datagrid("getRowIndex",id);
	    					if(rowIndex>=0){
	    						$("#sales-datagrid-receiptUnWriteoffListPage").datagrid("checkRow",rowIndex);
	    						$("#sales-datagrid-receiptUnWriteoffListPage").datagrid("selectRow",rowIndex);
	    					}
    					}
    				}
        			$("input[name='receiptcheck']").each(function(index,el){
        			    el.disabled=true;   
        			});
				},
				onSelect:function(rowIndex, rowData){
					var receiptids = "${receiptids}";
    				var idsArr = receiptids.split(",");
    				var flag = false;
    				for(var i=0;i<idsArr.length;i++){
    					if(rowData.id==idsArr[i]){
    						flag = true;
    						rowIndex = $("#sales-datagrid-receiptUnWriteoffListPage").datagrid("getRowIndex",idsArr[i]);
    						break;
    					}
    				}
    				if(!flag){
    					$("#sales-datagrid-receiptUnWriteoffListPage").datagrid("unselectRow",rowIndex);
    				}
				},
				onUnselect:function(rowIndex, rowData){
					var receiptids = "${receiptids}";
    				var idsArr = receiptids.split(",");
    				var flag = false;
    				for(var i=0;i<idsArr.length;i++){
    					if(rowData.id==idsArr[i]){
    						flag = true;
    						rowIndex = $("#sales-datagrid-receiptUnWriteoffListPage").datagrid("getRowIndex",idsArr[i]);
    						break;
    					}
    				}
    				if(flag){
    					$("#sales-datagrid-receiptUnWriteoffListPage").datagrid("selectRow",rowIndex);
    				}
				},
				onCheckAll:function(){
					$("#sales-datagrid-receiptUnWriteoffListPage").datagrid("uncheckAll");
					var receiptids = "${receiptids}";
    				var idsArr = receiptids.split(",");
    				for(var i=0;i<idsArr.length;i++){
    					var id = idsArr[i];
    					var rowIndex = $("#sales-datagrid-receiptUnWriteoffListPage").datagrid("getRowIndex",id);
    					$("#sales-datagrid-receiptUnWriteoffListPage").datagrid("checkRow",rowIndex);
    					$("#sales-datagrid-receiptUnWriteoffListPage").datagrid("selectRow",rowIndex);
    				}
				}
			});
    	});
    </script>
  </body>
</html>
