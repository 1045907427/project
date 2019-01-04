<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>收款单核销指定销售发票</title>
  </head>
  
  <body>
  	<div id="account-datagrid-toolbar-collectionOrderInfo">
   	<form action="" method="post" id="account-form-salesStatmentWriteoffPage">
   		<table  border="0">
   			<tr>
   				<td width="120">客户名称:</td>
   				<td style="text-align: left;">
   					<input type="text" width="180" class="no_input" readonly="readonly" value="${customerCapital.customername }"/>
   				</td>
   				<td width="120">剩余金额:</td>
   				<td>
   					<input type="text" id="account-writeoff-remainderamount" class="no_input" readonly="readonly" value="${customerCapital.amount }"/>
   				</td>
   				<td width="120">选中后剩余金额:</td>
   				<td>
   					<input type="text" id="account-writeoff-selectremainderamount" class="no_input" data-options="precision:2,groupSeparator:','" readonly="readonly" value="${collectionOrder.remainderamount }"/>
   				</td>
   			</tr>
   			<tr>
   				<td>发票号(以空格区分):</td>
   				<td colspan="5">
   					<input type="text" id="account-writeoff-invoiceno" style="width: 500px;"/>
   					<a href="javaScript:void(0);" id="account-writeoff-selectsalesInvoice" class="easyui-linkbutton" iconCls="icon-search" plain="true" title="选中销售发票">选中</a>
   				</td>
   			</tr>
   		</table>
    </form>
   </div>
   <table id="account-datagrid-salesInvoiceList"></table>
   <div id="account-dialog-writeoff-info"></div>
   <script type="text/javascript">
   		$(function(){
   			$("#account-writeoff-remainderamount").numberbox({
   				precision:2,
   				groupSeparator:','
   			});
   			$("#account-writeoff-selectremainderamount").numberbox({
   				precision:2,
   				groupSeparator:','
   			});
   			$("#account-datagrid-salesInvoiceList").datagrid({
   				columns:[[
	   						  {field:'ck',checkbox:true},
							  {field:'id',title:'编号',width:125,sortable:true},
							  {field:'billtype',title:'单据类型',width:60,isShow:true,
							  	formatter:function(value,rowData,rowIndex){
					        		if(value=='1'){
					        			return "销售发票"
					        		}else if(value=='2'){
					        			return "应收款冲差";
					        		}
					        	}
							  },
							  {field:'invoiceno',title:'发票号',width:80,sortable:true},
							  {field:'businessdate',title:'业务日期',width:80,sortable:true},
							  {field:'customerid',title:'客户编号',width:80,sortable:true},
							  {field:'customername',title:'客户名称',width:100,sortable:true},
							  {field:'handlerid',title:'对方经手人',width:80,sortable:true,hidden:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return rowData.handlername;
					        	}
							  },
							  {field:'taxamount',title:'金额',resizable:true,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'notaxamount',title:'未税总金额',resizable:true,sortable:true,align:'right',hidden:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'discountamount',title:'折扣总金额',resizable:true,sortable:true,align:'right',hidden:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'invoiceamount',title:'应收金额',resizable:true,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'writeoffamount',title:'核销总金额',resizable:true,sortable:true,align:'right',hidden:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'tailamount',title:'尾差金额',resizable:true,sortable:true,align:'right',hidden:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
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
							  {field:'remark',title:'备注',width:80,sortable:true}
			             ]],
		 		fit:true,
		 		method:'post',
		 		rownumbers:true,
		 		idField:'id',
		 		sortName:'id',
		 		sortOrder:'desc',
		 		singleSelect:false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
				url: 'account/receivable/showSalesInvoiceListByCustomerid.do?id=${customerCapital.id}',
				toolbar:'#account-datagrid-toolbar-collectionOrderInfo',
				onCheck:function(rowIndex,rowData){
					countCheckRow();
				},
				onUnselect:function(rowIndex,rowData){
					countCheckRow();
				},
				onCheckAll:function(rows){
					countCheckRow();
				},
				onUncheckAll:function(rows){
					countCheckRow();
				},
				onLoadSuccess:function(data){
					$("#account-datagrid-salesInvoiceList").datagrid("clearChecked");
					var invoiceid = "${invoiceid}";
					for(var j=0;j<data.rows.length;j++){
   						if(data.rows[j].id==invoiceid){
   							$("#account-datagrid-salesInvoiceList").datagrid("checkRow",j);
   							break;
   						}
   					}
				}
   			});
   			
   			$("#account-writeoff-selectsalesInvoice").click(function(){
   				var val = $("#account-writeoff-invoiceno").val();
   				var invoicenoArr = val.split(" ");
   				for(var i=0;i<invoicenoArr.length;i++){
   					var rows =  $("#account-datagrid-salesInvoiceList").datagrid("getRows");
   					for(var j=0;j<rows.length;j++){
   						if(rows[j].invoiceno==invoicenoArr[i]){
   							$("#account-datagrid-salesInvoiceList").datagrid("selectRow",j);
   							break;
   						}
   					}
   				}
   			});
   		});
   		function collectionOrderWriteOff(){
   			var rows = $("#account-datagrid-salesInvoiceList").datagrid("getChecked");
   			if(rows == null || rows.length==0){
				$.messager.alert("提醒","请选择销售发票核销");
				return false;
			}
   			var invoiceid = "";
   			var invoiceamount = 0;
   			for(var i=0;i<rows.length;i++){
   				if(invoiceid == ""){
   					invoiceid = rows[i].id;
   				}else{
   					invoiceid += ","+rows[i].id;
   				}
   				invoiceamount = Number(invoiceamount)+Number(rows[i].invoiceamount);
   			}
   			$('<div id="account-dialog-writeoff-info-content"></div>').appendTo('#account-dialog-writeoff-info');
   			$("#account-dialog-writeoff-info-content").dialog({
				href:"account/receivable/showWriteoffPage.do?customerid=${customerCapital.id}&invoiceid="+invoiceid,
				title:"确认核销",
				fit:true,
				modal:true,
				cache:false,
				maximizable:true,
				resizable:true,
			    buttons:[{
						text:'确定',
						handler:function(){
							writeOffSubmit();
						}
					}],
				onClose:function(){
			    	$('#account-dialog-writeoff-info-content').dialog("destroy");
			    }
			});
   		}
   		//统计选中的金额
   		function countCheckRow(){
   			var rows = $("#account-datagrid-salesInvoiceList").datagrid("getChecked");
   			var invoiceamount = 0;
   			for(var i=0;i<rows.length;i++){
   				invoiceamount = Number(invoiceamount)+Number(rows[i].invoiceamount);
   			}
			var remainderamount = $("#account-writeoff-remainderamount").numberbox("getValue");
			var lastamount = Number(remainderamount)-Number(invoiceamount);
			$("#account-writeoff-selectremainderamount").numberbox("setValue",lastamount);
			if(lastamount<0){
				$.messager.alert("提醒","余额不足");
			}
   		}
   </script>
  </body>
</html>
