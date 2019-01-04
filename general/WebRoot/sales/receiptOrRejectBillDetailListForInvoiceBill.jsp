<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
  <head>
  </head>
  
  <body>
	<table id="account-salesinvoicebill-receiptOrReject-detail"></table>
	<script type="text/javascript">
		$(function(){
			$("#account-salesinvoicebill-receiptOrReject-detail").datagrid({
				columns:[[
							{field:'ck', checkbox:true},
							{field:'id', title:'明细编号', width:60,sortable:true,hidden:true},
							{field:'billid', title:'编号', width:130,sortable:true},
							{field:'billtype', title:'单据类型', width:100,sortable:true,
								formatter:function(value,row,index){
									if(value=='1'){
										return "销售发货回单";
									}else if(value=='2'){
										return "销售退货通知单";
									}else if(value=='3'){
                                        return "冲差单";
                                    }
						        }
							},
							{field:'goodsid', title:'商品编码', width:70,sortable:true},
							{field:'goodsname', title:'商品名称', width:150},
							{field:'boxnum', title:'箱装量', width:60,align:'right',
								formatter:function(value,row,index){
					        		return formatterBigNumNoLen(value);
						        }
							},
							{field:'unitname', title:'单位', width:40},
							{field:'unitnum', title:'数量', width:60,align:'right',
								formatter:function(value,row,index){
									if(row.isdiscount=='0'){
					        			return formatterBigNumNoLen(value);
					        		}
						        }
							},
							{field:'taxprice', title:'单价', width:60,align:'right',
								formatter:function(value,row,index){
					        		return formatterMoney(value);
						        }
							},
							{field:'taxamount',title:'金额',width:60,align:'right',
	    						formatter:function(value,row,index){
					        		return formatterMoney(value);
						        }
							},
							{field:'taxtypename',title:'税种',width:50,align:'left',
	    						formatter:function(value,row,index){
					        		if(row.taxtype!=null && row.taxtype!=""){
					        			return value;
					        		}
						        }
							},
							{field:'remark',title:'备注',width:80,align:'left'}
						]],
		 		fit:true,
		 		method:'post',
		 		rownumbers:true,
		 		idField:'id',
		 		singleSelect:false,
		 		selectOnCheck:true,
		 		checkOnSelect:true,
		 		data: JSON.parse('${detailList}'),
		 		showFooter:true,
		 		onBeforeLoad:function(){
		 		},
		 		onLoadSuccess:function(){
		 			$("#account-salesinvoicebill-receiptOrReject-detail").datagrid("resize");
		 			initSelect();
		 		},
		 		onCheckAll:function(rows){
		 			getTotalDetailAmount();
		    	},
		    	onUncheckAll:function(){
		    		$("#account-bill-orderDatagrid-dispatchBillSourcePage").datagrid("reloadFooter",[{billid:'选中合计',taxamount:0},{billid:'合计',taxamount:${totalamount}}] );
		    	},
		    	onCheck:function(rowIndex,rowData){
		    		getTotalDetailAmount();
		    	},
		    	onUncheck:function(rowIndex,rowData){
		    		getTotalDetailAmount();
		    	}
			});
		});
		function initSelect(){
			var uncheckedstr = $("#account-nochecked-detail-salesinvoicebillDetailPage").val();
   			var data = null;
   			if(null!=uncheckedstr && uncheckedstr!=""){
   				data = $.parseJSON(uncheckedstr);
   			}else{
   				data = [];
   			}
   			$("#account-salesinvoicebill-receiptOrReject-detail").datagrid("checkAll");
   			for(var j=0;j<data.length;j++){
				if(data[j].billid=="${id}"){
					var rowIndex = $("#account-salesinvoicebill-receiptOrReject-detail").datagrid("getRowIndex",data[j].id);
					$("#account-salesinvoicebill-receiptOrReject-detail").datagrid("uncheckRow",rowIndex);
				}
			}
			
		}
		function appendSalesInvoiceDetail(){
    		var rows = $("#account-salesinvoicebill-receiptOrReject-detail").datagrid("getChecked");
    		var olddetailRows = $("#account-orderDatagrid-detailSourcePage").datagrid("getRows");
    		$("#account-orderDatagrid-detailSourcePage").datagrid("clearChecked");
			$("#account-orderDatagrid-detailSourcePage").datagrid("loadData",{total:0,rows:[],footer:[]});
    		for(var i=0;i<olddetailRows.length;i++){
    			var detail = olddetailRows[i];
 				if(detail.billid=='${id}'){
 				}else{
 					$("#account-orderDatagrid-detailSourcePage").datagrid("appendRow",detail);
 				}
    		}
    		for(var i=0;i<rows.length;i++){
    			$("#account-orderDatagrid-detailSourcePage").datagrid("appendRow",rows[i]);
    		}
    		$.messager.alert("提醒","成功添加到明细列表！");
    		$("#account-orderDatagrid-detailSourcePage").datagrid("checkAll");
    		getBillDetailAmount();
    		$('#tt').tabs('select', "明细列表");
    		$("#account-panel-selectDetailPage").dialog("close");
    	}
    	function getTotalDetailAmount(){
    		var rows = $("#account-salesinvoicebill-receiptOrReject-detail").datagrid("getChecked");
    		var totalamount = 0;
 			for(var i=0;i<rows.length;i++){
 				totalamount = Number(totalamount) + Number(rows[i].taxamount);
 			}
 			if(rows.length>0){
 				$("#account-salesinvoicebill-receiptOrReject-detail").datagrid("reloadFooter",[{billid:'选中合计',taxamount:totalamount},{billid:'合计',taxamount:${totalamount}}] );
    		}else{
    			$("#account-salesinvoicebill-receiptOrReject-detail").datagrid("reloadFooter",[{billid:'选中合计',taxamount:0},{billid:'合计',taxamount:${totalamount}}] );
    		}
    	}
    	//获取未选中对象
    	function addNocheckedDetail(){
    		var rows = $("#account-salesinvoicebill-receiptOrReject-detail").datagrid("getRows");
    		var checkedRows = $("#account-salesinvoicebill-receiptOrReject-detail").datagrid("getChecked");
    		var unChecked = [];
    		var unCheckAmount = 0;

			var taxtype="";
			var taxtypeErrorMsg=new Array();
			for(var i=0;i<checkedRows.length;i++){
				var theTaxtype= checkedRows[i].taxtype;
				if(taxtype==""){
					if(theTaxtype==""){
						$.messager.alert("提醒","选中的第一行明细税种未知");
						return false;
					}
					taxtype=theTaxtype;
				}else if(taxtype!=theTaxtype){
					taxtypeErrorMsg.push("行："+(i+1)+"税种与选中的第一行不相同");
				}
			}
			if(taxtypeErrorMsg.length>0){
				$.messager.alert("提醒","请选择相同税种的明细。<br/>"+taxtypeErrorMsg.join('<br/>'));
				return false;
			}
			
    		for(var i=0;i<rows.length;i++){
    			var unflag = true;
    			for(var j=0;j<checkedRows.length;j++){
    				if(rows[i].id==checkedRows[j].id){
    					unflag = false;
    					break;
    				}
    			}
    			if(unflag){
    				var data = {billid:rows[i].billid,id:rows[i].id};
    				unChecked.push(rows[i]);
    				unCheckAmount = Number(unCheckAmount) + Number(rows[i].taxamount);
    			}
    		}
    		var uncheckedstr = $("#account-nochecked-detail-salesinvoicebillDetailPage").val();
   			var unCheckedArr = null;
   			var data = [];
   			if(null!=uncheckedstr && uncheckedstr!=""){
   				unCheckedArr = $.parseJSON(uncheckedstr);
   			}else{
   				unCheckedArr = [];
   			}
   			for(var i=0;i<unCheckedArr.length;i++){
 				var detail = unCheckedArr[i];
 				if(unCheckedArr[i].billid!="${id}"){
 					data.push(unCheckedArr[i]);
 				}
 			}
    		if(unChecked.length>0){
    			for(var i=0;i<unChecked.length;i++){
	   				var object = {billid:unChecked[i].billid,id:unChecked[i].id};
	   				data.push(object);
	   			}
    		}
    		var invoiceamount = 0;
   			for(var j=0;j<checkedRows.length;j++){
   				invoiceamount = Number(invoiceamount)+Number(checkedRows[j].taxamount);
   			}
   			var rowIndex = $("#account-bill-orderDatagrid-dispatchBillSourcePage").datagrid("getRowIndex","${id}");
   			var datarows = $("#account-bill-orderDatagrid-dispatchBillSourcePage").datagrid("getRows");
   			var rowData = datarows[rowIndex];
   			rowData.invoiceamount = invoiceamount;
	    	$("#account-bill-orderDatagrid-dispatchBillSourcePage").datagrid('updateRow',{index:rowIndex, row:rowData});
   			$("#account-nochecked-detail-salesinvoicebillDetailPage").val(JSON.stringify(data));
   			getCustomerCapital(rowData.customerid,rowData.headcustomerid);
    		//单据未选择金额
    		var uncheckAmountstr = $("#account-checkedbill-uncheckamount-salesinvoicebillDetailPage").val();
    		var uncheckAmountArr = null;
    		if(null!=uncheckAmountstr && uncheckAmountstr!=""){
   				uncheckAmountArr = $.parseJSON(uncheckAmountstr);
   			}else{
   				uncheckAmountArr = [];
   			}
    		var uncheckamountflag = true;
    		for(var i=0;i<uncheckAmountArr.length;i++){
    			if(uncheckAmountArr[i].id=="${id}"){
    				uncheckamountflag = false;
    				uncheckAmountArr[i].uncheckamount = unCheckAmount;
    				break;
    			}
    		}
    		if(uncheckamountflag){
    			var object = {id:"${id}",uncheckamount:unCheckAmount};
    			uncheckAmountArr.push(object);
    		}
    		$("#account-checkedbill-uncheckamount-salesinvoicebillDetailPage").val(JSON.stringify(uncheckAmountArr));
    		$("#account-panel-selectDetailPage").dialog("close");
    	}
	</script>
  </body>
</html>
