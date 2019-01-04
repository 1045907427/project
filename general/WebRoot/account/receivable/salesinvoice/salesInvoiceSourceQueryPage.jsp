<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售发票单参照上游单据查询页面</title>
  </head>
  
  <body>
    <div class="pageContent">
    	<form id="account-form-query-dispatchBill">
    		<input type="hidden" name="status" value="3" />
    		<input type="hidden" name="isinvoice" value="3" />
    		<p>
    			<label>单据类型：</label>
    			<select id="account-sourcetype-salesInvoiceSourceQueryPage" name="sourcetype">
    				<option></option>
    				<option value="1">销售发货回单</option>
    				<option value="2">销售退货通知单</option>
    			</select>
    		</p>
    		<p>
    			<label>客户名称：</label>
    			<input type="text" id="account-customerid-salesInvoiceSourceQueryPage" name="customerid" />
    		</p>
    		<p>
    			<label>总店名称：</label>
    			<input type="text" id="account-pcustomerid-salesInvoiceSourceQueryPage" name="pcustomerid" />
    		</p>
    		<p>
    			<label>销售部门：</label>
    			<input type="text" id="account-salesdept-salesInvoiceSourceQueryPage" name="salesdept" />
    		</p>
    		<p>
    			<label>客户业务员：</label>
    			<input type="text" id="account-salesuser-salesInvoiceSourceQueryPage" name="salesuser" />
    		</p>
    		<p>
    			<label>制单人：</label>
    			<input type="text" id="account-adduser-salesInvoiceSourceQueryPage" name="adduserid" />
    		</p>
    		<p>
    			<label>业务日期：</label>
    			<input type="text" name="businessdate1" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /> 到 <input type="text" name="businessdate2" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" />
    		</p>
    		<p>
    			<label>应收日期：</label>
    			<input type="text" name="duefromdate1" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /> 到 <input type="text" name="duefromdate2" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" />
    		</p>
    	</form>
    </div>
    <script type="text/javascript">
    	$(function(){
    		$("#account-customerid-salesInvoiceSourceQueryPage").widget({ 
    			referwid:'RL_T_BASE_SALES_CUSTOMER',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:true
    		});
    		$("#account-pcustomerid-salesInvoiceSourceQueryPage").widget({ 
    			referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:true
    		});
    		$("#account-salesuser-salesInvoiceSourceQueryPage").widget({
    			name:'t_account_sales_invoice',
				col:'salesuser',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:true
    		});
    		$("#account-salesdept-salesInvoiceSourceQueryPage").widget({
    			name:'t_account_sales_invoice',
				col:'salesdept',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:true
    		});
    		$("#account-adduser-salesInvoiceSourceQueryPage").widget({ //制单人参照窗口
    			name:'t_account_sales_invoice',
				col:'adduserid',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:true
    		});
    	});
    	//查询
    	function sourceQuery(){
    		var sourcetype = $("#account-sourcetype-salesInvoiceSourceQueryPage").val();
			var queryJSON = $("#account-form-query-dispatchBill").serializeJSON();
			$("#account-panel-relation-upper").dialog('close', true);
			var type = $("#account-buttons-salesInvoicePage").buttonWidget("getOperType");
			var title = "";
			if(sourcetype=="2"){
				title="销售退货通知单";
			}else if(sourcetype=="1"){
				title = "销售发货回单";
			}else{
				title = "销售发货回单和销售退货通知单";
			}
			$("#account-panel-sourceQueryPage").dialog({
				title:title,
				fit:true,
				closed:false,
				modal:true,
				cache:false,
				maximizable:true,
				resizable:true,
				href:'account/receivable/showSalesInvoiceSourceListPage.do?sourcetype='+sourcetype,
				buttons:[{
							text:'确定',
							handler: function(){
								showSalesInvoiceDetail();
								//addSalesInvoiceByRefer();
							}
					  }],
				onLoad:function(){
					var url = "";
					var detailUrl = "";
					url = 'sales/getReceiptAndRejectBillList.do';
					$("#account-orderDatagrid-dispatchBillSourcePage").datagrid({ 
						columns:[[
									{field:'ck', checkbox:true},
									{field:'id', title:'编号', width:130,sortable:true},
									{field:'billtype', title:'单据类型', width:100,sortable:true,
										formatter:function(value,row,index){
											if(value=='1'){
												return "销售发货回单";
											}else if(value=='2'){
												return "销售退货通知单";
											}
								        }
									},
									{field:'businessdate', title:'业务日期', width:70,sortable:true},
									{field:'customerid',title:'客户编码',width:60,align:'left'},
									{field:'customername',title:'客户名称',width:100,align:'left',sortable:true,isShow:true},
									{field:'headcustomername',title:'总店名称',width:100,align:'left'},
									{field:'handlerid',title:'对方经手人',width:80,align:'left'},
									{field:'salesdept',title:'销售部门',width:80,align:'left'},
									{field:'salesuser',title:'客户业务员',width:70,align:'left'},
									{field:'totaltaxamount',title:'含税金额',resizable:true,align:'right',
				    						formatter:function(value,row,index){
								        		return formatterMoney(value);
									        }
									  },
									  {field:'totalnotaxamount',title:'未税金额',resizable:true,align:'right',hidden:true,
				    						formatter:function(value,row,index){
								        		return formatterMoney(value);
									        }
									  },
									  {field:'totaltax',title:'税额',resizable:true,align:'right',hidden:true,
				    						formatter:function(value,row,index){
								        		return formatterMoney(value);
									        }
									  },
									{field:'duefromdate',title:'应收日期',width:80,align:'left'},
									{field:'addusername',title:'制单人',width:60,align:'left'},
									{field:'remark',title:'备注',width:80,align:'left'}
								]],
				 		fit:true,
				 		method:'post',
				 		rownumbers:true,
				 		pagination:true,
				 		idField:'id',
				 		singleSelect:false,
				 		selectOnCheck:true,
				 		checkOnSelect:true,
						sortName:'businessdate',
						sortOrder:'asc',
					   	url:url,
					   	queryParams: queryJSON,
					   	onBeforeLoad:function(){
					   	},
					   	onClickRow:function(rowIndex, rowData){
					   		var rowArr = $("#account-orderDatagrid-dispatchBillSourcePage").datagrid("getChecked");
							var checkFlag = true;
							for(var i=0;i<rowArr.length;i++){
								var rowObject =rowArr[i];
								if(rowObject.headcustomerid!=rowData.headcustomerid){
									checkFlag = false;
									break;
								}
							}
							if(!checkFlag){
								$("#account-orderDatagrid-dispatchBillSourcePage").datagrid("uncheckRow",rowIndex);
								return false;
							}
					   	},
				    	onCheckAll:function(rows){
				    		var checkFlag = true;
				    		var data = rows[0];
				    		for(var i=0;i<rows.length;i++){
								var rowObject =rows[i];
								if(rowObject.headcustomerid!=data.headcustomerid){
									checkFlag = false;
								}
							}
							if(!checkFlag){
								$.messager.alert("提醒","请选择相同总店下的数据！");
								$("#account-orderDatagrid-dispatchBillSourcePage").datagrid("uncheckAll");
								return false;
							}
				    	},
				    	onUncheckAll:function(){
				    		$("#account-customerid-dispatchBillSourcePage").val("");
				    		$("#account-selectamount-dispatchBillSourcePage").val("");
				    		$("#account-amount-dispatchBillSourcePage").val("");
				    		$("#account-customerid-dispatchBillSourcePage option").remove();
				    	},
				    	onCheck:function(rowIndex,rowData){
				    		var rowArr = $("#account-orderDatagrid-dispatchBillSourcePage").datagrid("getChecked");
							var checkFlag = true;
							for(var i=0;i<rowArr.length;i++){
								var rowObject =rowArr[i];
								if(rowObject.headcustomerid!=rowData.headcustomerid){
									checkFlag = false;
									break;
								}
							}
							if(!checkFlag){
								$.messager.alert("提醒","请选择相同客户（总店）下的数据！");
								$("#account-orderDatagrid-dispatchBillSourcePage").datagrid("uncheckRow",rowIndex );
								return false;
							}else{
					    		var selectAmount = $("#account-selectamount-dispatchBillSourcePage").val();
					    		if(selectAmount==null || selectAmount==""){
					    			selectAmount = 0;
					    		}
					    		selectAmount = Number(selectAmount) + Number(rowData.totaltaxamount);
					    		$("#account-selectamount-dispatchBillSourcePage").val(selectAmount.toFixed(2) );
					    		var headcustomername = $("#account-customerid-dispatchBillSourcePage").val();
					    		if($("#account-customerid-dispatchBillSourcePage option[value='"+rowData.headcustomerid+"']").length==0){
					    			$("#account-customerid-dispatchBillSourcePage").append("<option value='"+rowData.headcustomerid+"'>"+rowData.headcustomername+"</option>");
					    		}
					    		if($("#account-customerid-dispatchBillSourcePage option[value='"+rowData.customerid+"']").length==0){
					    			$("#account-customerid-dispatchBillSourcePage").append("<option value='"+rowData.customerid+"'>"+rowData.customername+"</option>");
					    		}
					    		if(headcustomername==null || headcustomername==""){
					    			$("#account-hidden-pcustomerid-dispatchBillSourcePage").val(rowData.headcustomerid);
					    			getCustomerCapital(rowData.headcustomerid);
					    		}
				    		}
				    	},
				    	onUncheck:function(rowIndex,rowData){
				    		var rowArr = $("#account-orderDatagrid-dispatchBillSourcePage").datagrid("getChecked");
							var checkFlag = true;
							for(var i=0;i<rowArr.length;i++){
								var rowObject =rowArr[i];
								if(rowObject.headcustomerid!=rowData.headcustomerid){
									checkFlag = false;
									break;
								}
							}
							if(checkFlag){
					    		var selectAmount = $("#account-selectamount-dispatchBillSourcePage").val();
					    		if(selectAmount==null || selectAmount==""){
					    			selectAmount = 0;
					    		}
					    		selectAmount = Number(selectAmount) -Number(rowData.totaltaxamount);
					    		$("#account-selectamount-dispatchBillSourcePage").val(selectAmount.toFixed(2) );
					    		if($("#account-customerid-dispatchBillSourcePage option[value='"+rowData.customerid+"']")!=null){
					    			$("#account-customerid-dispatchBillSourcePage option[value='"+rowData.customerid+"']").remove();
					    		}
					    		var rowArr = $("#account-orderDatagrid-dispatchBillSourcePage").datagrid("getChecked");
					    		if(rowArr.length==0){
					    			$("#account-customerid-dispatchBillSourcePage option[value='"+rowData.headcustomerid+"']").remove();
					    			$("#account-selectamount-dispatchBillSourcePage").val("");
					    			$("#account-hidden-pcustomerid-dispatchBillSourcePage").val("");
					    			$("#account-amount-dispatchBillSourcePage").val("");
					    		}
				    		}
				    	}
					});
				}
			});
    	}
    	//获取客户资金情况
    	function getCustomerCapital(customerid){
    		$.ajax({   
	            url :'account/receivable/getCustomerCapital.do?id='+customerid,
	            type:'post',
	            dataType:'json',
	            success:function(json){
	            	$("#account-amount-dispatchBillSourcePage").val(formatterMoney(json.amount));
	            }
	        });
    	}
    	function showSalesInvoiceDetail(){
    		var rowArr = $("#account-orderDatagrid-dispatchBillSourcePage").datagrid("getChecked");
    		var ids = null;
			for(var i=0;i<rowArr.length;i++){
				if(ids==null){
					ids = rowArr[i].id;
				}else{
					ids +=","+ rowArr[i].id;
				}
			}
			var customername = $("#account-customerid-dispatchBillSourcePage").find('option:selected').text();
			$("#account-panel-salesinvoiceDetailPage").dialog({
				href:"sales/showReceiptAndRejectBillDetailList.do?id="+ids+"&customername="+customername,
				title:"客户:"+customername+"，商品明细列表",
			    fit:true,
				closed:false,
				modal:true,
				cache:false,
				maximizable:true,
				resizable:true,
			    buttons:[{
							text:'生成销售核销',
							handler:function(){
								addSalesInvoiceByRefer();
							}
						}]
			});
    	}
    </script>
  </body>
</html>
