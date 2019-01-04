<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>采购发票单参照上游单据查询页面</title>
  </head>
  
  <body>
    <div class="pageContent">
    	<form id="account-form-query-dispatchBill">
    		<input type="hidden" name="shownotisinvoice" value="1" />
    		<p>
    			<label>来源类型：</label>
    			<select name="ordertype" id="account-sourcetype-purchaseInvoiceSourceQueryPage" style="width: 200px;" class="easyui-combobox">
    				<option value="3">全部</option>
    				<option value="1">采购进货单</option>
    				<option value="2">采购退货通知单</option>
    			</select>
    		</p>
    		<p>
    			<label>来源单据编号：</label>
    			<input type="text" id="account-id-purchaseInvoiceSourceQueryPage" name="id" />
    		</p>
    		<p>
    			<label>供应商名称：</label>
    			<input type="text" id="account-supplierid-purchaseInvoiceSourceQueryPage" name="supplierid" />
    		</p>
    		<p>
    			<label>采购部门：</label>
    			<input type="text" id="account-buydept-purchaseInvoiceSourceQueryPage" name="buydeptid" />
    		</p>
    		<p>
    			<label>采购员：</label>
    			<input type="text" id="account-buyuser-purchaseInvoiceSourceQueryPage" name="buyuserid" />
    		</p>
    		<p>
    			<label>业务日期：</label>
    			<input type="text" name="businessdatestart" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /> 到 <input type="text" name="businessdateend" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" />
    		</p>
    	</form>
    </div>
    <script type="text/javascript">
    	$(function(){
    		$("#account-supplierid-purchaseInvoiceSourceQueryPage").widget({ 
    			name:'t_account_purchase_invoice',
				col:'supplierid',
    			//referwid:'RL_T_BASE_purchase_CUSTOMER',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:true
    		});
    		$("#account-buyuser-purchaseInvoiceSourceQueryPage").widget({
    			name:'t_account_purchase_invoice',
				col:'buyuser',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:true
    		});
    		$("#account-buydept-purchaseInvoiceSourceQueryPage").widget({
    			name:'t_account_purchase_invoice',
				col:'buydept',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:true
    		});
    	});
    	//查询
    	function sourceQuery(){
    		var sourcetype = $("#account-sourcetype-purchaseInvoiceSourceQueryPage").combobox('getValue');
			var queryJSON = $("#account-form-query-dispatchBill").serializeJSON();
			$("#account-panel-relation-upper").dialog('close', true);
			var type = $("#account-buttons-purchaseInvoicePage").buttonWidget("getOperType");
			var title = "";
			if(sourcetype=="2"){
				title="采购退货通知单";
			}else if(sourcetype=="1"){
				title = "采购进货单";
			}
			else if(sourcetype=="3"){
				title = "采购单";
			}
			$("#account-panel-sourceQueryPage").dialog({
				title:title,
				fit:true,
				closed:false,
				modal:true,
				cache:false,
				maximizable:true,
				resizable:true,
				href:'account/payable/showPurchaseInvoiceSourceListPage.do?sourcetype='+sourcetype,
				buttons:[{
							text:'确定',
							handler: function(){
								addPurchaseInvoiceByRefer();
							}
					  }],
				onLoad:function(){
					var url = "";
					var detailUrl = "";
					//来源类型采购进货单
					if(sourcetype=='1'){
						url = 'account/payable/getPurchaseOrderList.do';
						detailUrl = "purchase/arrivalorder/showArrivalOrderDetailDownReferList.do";
					}else if(sourcetype=='2'){		//来源类型采购退货通知单
						url = 'account/payable/getPurchaseOrderList.do';
						detailUrl = "purchase/returnorder/showReturnOrderDetailReferList.do";
					}
					else if(sourcetype=='3'){
						url = 'account/payable/getPurchaseOrderList.do';
						detailUrl = 'account/payable/getPurchaseOrderDetailList.do';
					}
					$("#account-orderDatagrid-dispatchBillSourcePage").datagrid({ 
						columns:[[
									{field:'ck', checkbox:true},
									{field:'id', title:'编号',sortable:true, width:110},
									{field:'businessdate', title:'业务日期',sortable:true, width:90},
									{field:'ordertype',title:'订单类型',width:80,align:'left',isShow:true,
										formatter:function(value,rowData,rowIndex){
								       		return rowData.ordertypename;
								        }
									},
									{field:'supplierid',title:'供应商编码',width:70,sortable:true},
							  		{field:'suppliername',title:'供应商名称',width:210,isShow:true},
									{field:'handlerid',title:'对方经手人',width:80,align:'left',
										formatter:function(value,rowData,rowIndex){
								       		return rowData.handlername;
								        }
									},
									{field:'buydeptid',title:'采购部门',width:90,align:'left',
										formatter:function(value,rowData,rowIndex){
								       		return rowData.buydeptname;
								        }
									},
									{field:'buyuserid',title:'采购员',width:90,align:'left',
										formatter:function(value,rowData,rowIndex){
								       		return rowData.buyusername;
								        }
									},
									{field:'totalamount',title:'金额',resizable:true,align:'right',isShow:true,
										formatter:function(value,rowData,rowIndex){
							        		return formatterMoney(value);
							        	}
									},
									{field:'paydate',title:'应付日期',width:100,align:'left',hidden:true},
									{field:'addusername',title:'制单人',width:60,align:'left'},
									{field:'remark',title:'备注',width:100,align:'left'}
								]],
				 		fit:true,
				 		method:'post',
				 		rownumbers:true,
				 		pagination:true,
				 		idField:'id',
				 		singleSelect:false,
				 		selectOnCheck:true,
				 		checkOnSelect:true,
						fitColumns:true,
				 		showFooter: true,
					   	url:url,
					   	queryParams: queryJSON,
					    onCheck:function(index, data){
					    	var rowArr = $("#account-orderDatagrid-dispatchBillSourcePage").datagrid("getChecked");
							var checkFlag = true;
							for(var i=0;i<rowArr.length;i++){
								var rowObject =rowArr[i];
								if(rowObject.supplierid!=data.supplierid){
									checkFlag = false;
								}
							}
							var ids = null,aids = null,rids = null;
							if(!checkFlag){
								$.messager.alert("提醒","请选择相同供应商下的数据！");
								$("#account-orderDatagrid-dispatchBillSourcePage").datagrid("uncheckRow",index);
							}else{
								if('3' != sourcetype){
									for(var i=0;i<rowArr.length;i++){
										if(ids==null){
											ids = rowArr[i].id;
										}else{
											ids +=","+ rowArr[i].id;
										}
										$("#account-detailDatagrid-dispatchBillSourcePage").datagrid({
											url:detailUrl,
								  			queryParams:{
								  				orderidarrs:ids
								  			}
										});
									}
								}
								else{
									for(var i=0;i<rowArr.length;i++){
										if("1" == rowArr[i].ordertype){//采购进货单
											if(aids==null){
												aids = rowArr[i].id;
											}else{
												aids +=","+ rowArr[i].id;
											}
										}else if("2" == rowArr[i].ordertype){//采购退货通知单
											if(rids==null){
												rids = rowArr[i].id;
											}else{
												rids +=","+ rowArr[i].id;
											}
										}
										$("#account-detailDatagrid-dispatchBillSourcePage").datagrid({
											url:detailUrl,
								  			queryParams:{
								  				aorderidarrs:aids,
								  				rorderidarrs:rids
								  			}
										});
									}
								}
							}
							countTotalAmount();
				    	},
				    	onUncheck:function(rowIndex, rowData){
				    		var rowArr = $("#account-orderDatagrid-dispatchBillSourcePage").datagrid('getChecked');
				    		if(rowArr.length == 0){
				    			$("#account-detailDatagrid-dispatchBillSourcePage").datagrid('loadData',{"total":0,"rows":[]});
				    			$("#account-detailDatagrid-dispatchBillSourcePage").datagrid("reloadFooter",[{id:'选中金额',taxamount:0,notaxamount:0,tax:0,auxnumdetail:"0,0"}]);
				    		}
							var ids = null,aids = null,rids = null;
							if('3' != sourcetype){
								for(var i=0;i<rowArr.length;i++){
									if(ids==null){
										ids = rowArr[i].id;
									}else{
										ids +=","+ rowArr[i].id;
									}
									$("#account-detailDatagrid-dispatchBillSourcePage").datagrid({
										url:detailUrl,
							  			queryParams:{
							  				orderidarrs:ids
							  			}
									});
								}
							}
							else{
								for(var i=0;i<rowArr.length;i++){
									if("1" == rowArr[i].ordertype){//采购进货单
										if(aids==null){
											aids = rowArr[i].id;
										}else{
											aids +=","+ rowArr[i].id;
										}
									}else if("2" == rowArr[i].ordertype){//采购退货通知单
										if(rids==null){
											rids = rowArr[i].id;
										}else{
											rids +=","+ rowArr[i].id;
										}
									}
									$("#account-detailDatagrid-dispatchBillSourcePage").datagrid({
										url:detailUrl,
							  			queryParams:{
							  				aorderidarrs:aids,
							  				rorderidarrs:rids
							  			}
									});
								}
							}
				    		countTotalAmount();
				    	},
						onCheckAll:function(){
							countTotalAmount();
						},
						onUncheckAll:function(){
							countTotalAmount();
						}
					});
				}
			});
    	}    	
    	function countTotalAmount(){
    		var rows =  $("#account-orderDatagrid-dispatchBillSourcePage").datagrid('getChecked');
    		var totalamount = 0;
    		for(var i=0;i<rows.length;i++){
    			totalamount = Number(totalamount)+Number(rows[i].totalamount == undefined ? 0 : rows[i].totalamount);
    		}
    		$("#account-orderDatagrid-dispatchBillSourcePage").datagrid("reloadFooter",[{id:'选中金额',totalamount:totalamount}]);
    	}
    </script>
  </body>
</html>
