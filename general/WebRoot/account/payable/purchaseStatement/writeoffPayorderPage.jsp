<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>付款单核销指定采购发票</title>
  </head>
  
  <body>
  	<div id="account-datagrid-toolbar-payorderInfo">
   	<form action="" method="post" id="account-form-purchaseStatementWriteoffPage">
   		<table  border="0">
   			<tr>
   				<td width="120">供应商名称:</td>
   				<td>
   					<input type="text" width="210" class="no_input" readonly="readonly" value="${supplierCapital.suppliername }"/>
   				</td>
   				<td width="120">剩余金额:</td>
   				<td>
   					<input type="text" id="account-writeoff-remainderamount" class="no_input" readonly="readonly" value="${supplierCapital.amount }"/>
   				</td>
   				<td width="120">选中后剩余金额:</td>
   				<td>
   					<input type="text" id="account-writeoff-selectremainderamount" class="no_input" readonly="readonly" value="${payorder.remainderamount }"/>
   				</td>
   			</tr>
   			<tr>
   				<td>发票号(以空格区分):</td>
   				<td colspan="5">
   					<input type="text" id="account-writeoff-invoiceno" style="width: 500px;"/>
   					<a href="javaScript:void(0);" id="account-writeoff-selectsalesInvoice" class="easyui-linkbutton" iconCls="button-commonquery" plain="true" title="选中采购发票">选中</a>
   				</td>
   			</tr>
   		</table>
    </form>
   </div>
   <table id="account-datagrid-purchaseInvoiceList"></table>
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
   		
   			$("#account-datagrid-purchaseInvoiceList").datagrid({
   				columns:[[
   						  {field:'ck',checkbox:true},
						  {field:'id',title:'编号',width:135,sortable:true},
						  {field:'invoiceno',title:'发票号',width:100,sortable:true},
						  {field:'businessdate',title:'业务日期',width:80,sortable:true},
						  {field:'supplierid',title:'供应商编码',width:70,sortable:true},
						  {field:'suppliername',title:'供应商名称',width:100,sortable:true,isShow:true},
						  {field:'handlerid',title:'对方经手人',width:80,sortable:true,hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return rowData.handlername;
				        	}
						  },
						  {field:'buydept',title:'采购部门',width:100,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return rowData.buydeptname;
				        	}
						  },
						  {field:'buyuser',title:'采购员',width:80,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return rowData.buyusername;
				        	}
						  },
						  {field:'isdiscount',title:'是否折扣',width:60,sortable:true,hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return getSysCodeName("yesorno",value);
				        	}
						  },
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
						  {field:'invoiceamount',title:'应付总金额',resizable:true,sortable:true,align:'right',
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
						  {field:'remark',title:'备注',width:80,sortable:true}
		             ]],
		 		fit:true,
		 		method:'post',
		 		rownumbers:true,
		 		idField:'id',
		 		singleSelect:false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
				url: 'account/payable/showPurchaseInvoiceListBySupplier.do?id=${supplierCapital.id}',
				toolbar:'#account-datagrid-toolbar-payorderInfo',
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
				onLoadSuccess:function(){
					$("#account-datagrid-purchaseInvoiceList").datagrid("clearChecked");
                    initCheckRows();
				}
   			});

   			$("#account-writeoff-selectsalesInvoice").click(function(){
   				var val = $("#account-writeoff-invoiceno").val();
   				var invoicenoArr = val.split(" ");
   				for(var i=0;i<invoicenoArr.length;i++){
   					var rows =  $("#account-datagrid-purchaseInvoiceList").datagrid("getRows");
   					for(var j=0;j<rows.length;j++){
   						if(rows[j].invoiceno==invoicenoArr[i]){
   							$("#account-datagrid-purchaseInvoiceList").datagrid("selectRow",j);
   							break;
   						}
   					}
   				}
   			});
   		});
   		function payorderWriteOff(){
   			var payorderid = $("#account-writeoff-payorderid").val();
   			var rows = $("#account-datagrid-purchaseInvoiceList").datagrid("getChecked");
   			if(rows == null || rows.length==0){
				$.messager.alert("提醒","请勾选采购发票核销");
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
   				invoiceamount = formatterMoney(Number(invoiceamount)+Number(rows[i].invoiceamount));
   			}
   			var remainderamount = formatterMoney($("#account-writeoff-remainderamount").numberbox("getValue"));
			if(Number(remainderamount)<Number(invoiceamount)){
   				$.messager.alert("提醒","付款单余额不足");
   				//return false;
   			}
   			$('<div id="account-dialog-writeoff-info-content"></div>').appendTo('#account-dialog-writeoff-info');
   			$("#account-dialog-writeoff-info-content").dialog({
				href:"account/payable/showWriteoffPage.do?supplierid=${supplierCapital.id}&invoiceid="+invoiceid,
				title:"确认核销",
			    width:800,
			    height:400,
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
   			var rows = $("#account-datagrid-purchaseInvoiceList").datagrid("getChecked");
   			var invoiceamount = 0;
   			for(var i=0;i<rows.length;i++){
   				invoiceamount = formatterMoney(Number(invoiceamount)+Number(rows[i].invoiceamount));
   			}
			var remainderamount = formatterMoney($("#account-writeoff-remainderamount").numberbox("getValue"));
			var lastamount = Number(remainderamount)-Number(invoiceamount);
			$("#account-writeoff-selectremainderamount").numberbox("setValue",lastamount);
			if(lastamount<0){
				$.messager.alert("提醒","余额不足");
			}
   		}

        //初始化成功后勾选当前的采购发票
       function initCheckRows(){
           var rows = $("#account-datagrid-purchaseInvoiceList").datagrid("getRows");
            for(var i=0;i<rows.length;i++){
                var obj = rows[i];
                if("${invoiceid}" == obj.id){
                    var index = $("#account-datagrid-purchaseInvoiceList").datagrid("getRowIndex",obj);
                    $("#account-datagrid-purchaseInvoiceList").datagrid("checkRow",index);
                }
            }
       }
   </script>
  </body>
</html>
