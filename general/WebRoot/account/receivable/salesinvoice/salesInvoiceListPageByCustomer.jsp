<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售发票列表页面</title>
  </head>
  
  <body>
    <table id="account-dialog-datagrid-salesInvoiceList-customer"></table>
     <script type="text/javascript">
    	$(function(){
			$("#account-dialog-datagrid-salesInvoiceList-customer").datagrid({ 
				columns:[[
							  {field:'ck',checkbox:true},
							  {field:'id',title:'编号',width:125,sortable:true},
							  {field:'invoiceno',title:'发票号',width:80,sortable:true},
							  {field:'businessdate',title:'业务日期',width:80,sortable:true},
							  {field:'chlidcustomerid',title:'客户编码',width:60,align:'left'},
							  {field:'chlidcustomername',title:'客户名称',width:100,isShow:true},
							  {field:'customerid',title:'总店客户',width:100,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return rowData.customername;
					        	}
							  },
							  {field:'handlerid',title:'对方经手人',width:80,sortable:true,hidden:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return rowData.handlername;
					        	}
							  },
							  {field:'salesdept',title:'销售部门',width:100,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return rowData.salesdeptname;
					        	}
							  },
							  {field:'salesuser',title:'客户业务员',width:80,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return rowData.salesusername;
					        	}
							  },
							  {field:'sourceid',title:'来源单据编号',width:130,sortable:true},
							  {field:'taxamount',title:'含税总金额',resizable:true,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'notaxamount',title:'未税总金额',resizable:true,sortable:true,align:'right',hidden:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'invoiceamount',title:'应收总金额',resizable:true,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'writeoffamount',title:'核销总金额',resizable:true,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'tailamount',title:'尾差金额',resizable:true,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'taxtypename',title:'税种',width:50},
							  {field:'addusername',title:'制单人',width:80,sortable:true,hidden:true},
							  {field:'addtime',title:'制单时间',width:80,sortable:true,hidden:true},
							  {field:'auditusername',title:'审核人',width:80,hidden:true,sortable:true},
							  {field:'audittime',title:'审核时间',width:80,hidden:true,sortable:true},
							  {field:'stopusername',title:'中止人',width:80,hidden:true,sortable:true},
							  {field:'stoptime',title:'中止时间',width:80,hidden:true,sortable:true},
							  {field:'applytype',title:'申请状态',width:60,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
							  		if(value=='1'){
							  			return "开票";
							  		}else if(value=='2'){
							  			return "核销";
							  		}else if(value=='3'){
							  			return "开票核销";
							  		}
					        	}
							  },
							  {field:'iswriteoff',title:'核销状态',width:60,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
							  		if(value=='0'){
							  			return "未核销";
							  		}else if(value=='1'){
							  			return "已核销";
							  		}
					        	}
							  },
							  {field:'status',title:'状态',width:60,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return getSysCodeName("status",value);
					        	}
							  },
							  {field:'remark',title:'备注',width:80,sortable:true}
				             ]],
				fit:true,
		 		method:'post',
		 		rownumbers:true,
		 		idField:'id',
		 		singleSelect:true,
		 		checkOnSelect:true,
				selectOnCheck:true,
		 		data:JSON.parse('${dataList}'),
				onDblClickRow:function(rowIndex, rowData){
					top.addOrUpdateTab('account/receivable/showSalesInvoiceEditPage.do?id='+ rowData.id, "销售核销查看");
				}
			}).datagrid("columnMoving");
    	});
    </script>
  </body>
</html>
