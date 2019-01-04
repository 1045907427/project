<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>某来源单据明细页面</title>
  </head>
  
  <body>
    <table id="account-purchaseinvoice-sourcebill-detail"></table>
    <script type="text/javascript">
    	$(function(){
    		$("#account-purchaseinvoice-sourcebill-detail").datagrid({
    			columns:[[
    				{field:'ck', checkbox:true},
    				{field:'id', title:'明细编号', width:60,sortable:true,hidden:true},
    				{field:'orderid',title:'编号',width:130,isShow:true},
    				{field:'ordertype', title:'单据类型', width:100,sortable:true,
						formatter:function(value,row,index){
							if(value=='1'){
								return "采购进货单";
							}else if(value=='2'){
								return "采购退货通知单";
							}
				        }
					},
					{field:'goodsname', title:'商品名称', width:200,aliascol:'goodsid',
   						formatter:function(value,rowData,rowIndex){
				       		if(rowData.goodsInfo != null){
				       			return rowData.goodsInfo.name;
				       		}else{
				       			return "";
			       			}
				        }
   					},
   					{field:'boxnum', title:'箱装量', width:60,align:'right',
						formatter:function(value,row,index){
							if(null != row.goodsInfo && null != row.goodsInfo.boxnum){
								return formatterBigNumNoLen(row.goodsInfo.boxnum);
							}
				        }
					},
   					{field:'brandName', title:'商品品牌',width:80,aliascol:'goodsid',
   						formatter:function(value,rowData,rowIndex){
				       		if(rowData.goodsInfo != null){
				       			return rowData.goodsInfo.brandName;
				       		}else{
				       			return "";
				       		}
				        }
   					},
   					{field:'unitid', title:'主单位',width:50,
   						formatter: function(value,row,index){
							return row.unitname;
						}
					},
   					{field:'unitnum', title:'数量',width:60,align:'right',
   						formatter:function(value,row,index){
			        		return formatterBigNumNoLen(value);
				        }
			    	},
   					{field:'auxunitid', title:'辅单位',width:60,
   						formatter: function(value,row,index){
   							return row.auxunitname;
   						}
   					},
   					{field:'auxnumdetail', title:'辅数量',width:60,align:'right'},
   					{field:'taxprice', title:'含税单价',width:60,align:'right',
   						formatter:function(value,row,index){
			        		return formatterMoney(value);
				        }
			    	},
   					{field:'taxamount', title:'含税金额',resizable:true,align:'right',
   						formatter:function(value,row,index){
			        		return formatterMoney(value);
				        }
				    }
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
		 			$(this).datagrid("resize");
		 			initSelect();
		 		},
		 		onCheckAll:function(){
		 			getTotalDetailAmount();
		    	},
		    	onUncheckAll:function(){
		    		getTotalDetailAmount();
		    	},
		    	onCheck:function(){
		    		getTotalDetailAmount();
		    	},
		    	onUncheck:function(){
		    		getTotalDetailAmount();
		    	}
    		});
    	});
    	function initSelect(){
			var uncheckedstr = $("#account-nochecked-detail-selectBillDetailPage").val();
   			var data = null;
   			if(null != uncheckedstr &&　uncheckedstr != ""){
   				data = $.parseJSON(uncheckedstr);
   			}else{
   				data = [];
   			}
   			$("#account-purchaseinvoice-sourcebill-detail").datagrid("checkAll");
   			for(var j=0;j<data.length;j++){
				if(data[j].orderid=="${id}"){
					var rowIndex = $("#account-purchaseinvoice-sourcebill-detail").datagrid("getRowIndex",data[j].id);
					$("#account-purchaseinvoice-sourcebill-detail").datagrid("uncheckRow",Number(rowIndex));
				}
			}
		}
		function getTotalDetailAmount(){
			var rows = $("#account-purchaseinvoice-sourcebill-detail").datagrid("getChecked");
    		var totalamount = 0;
    		var unitnum = 0;
 			for(var i=0;i<rows.length;i++){
 				totalamount = Number(totalamount) + Number(rows[i].taxamount);
 				unitnum = Number(unitnum) + Number(rows[i].unitnum);
 			}
 			if(rows.length>0){
 				$("#account-purchaseinvoice-sourcebill-detail").datagrid("reloadFooter",[{orderid:'选中合计',unitnum:unitnum,taxamount:totalamount},{orderid:'合计',unitnum:${totalunitnum},taxamount:${totalamount}}] );
    		}else{
    			$("#account-purchaseinvoice-sourcebill-detail").datagrid("reloadFooter",[{orderid:'选中合计',unitnum:0,taxamount:0},{orderid:'合计',unitnum:${totalunitnum},taxamount:${totalamount}}] );
    		}
		}
		//获取未选中对象
		function addNocheckedDetail(){
			var rows = $("#account-purchaseinvoice-sourcebill-detail").datagrid("getRows");
    		var checkedRows = $("#account-purchaseinvoice-sourcebill-detail").datagrid("getChecked");
    		var unChecked = [];
    		var unCheckAmount = 0;
    		for(var i=0;i<rows.length;i++){
    			var unflag = true;
    			for(var j=0;j<checkedRows.length;j++){
    				if(rows[i].id==checkedRows[j].id){
    					unflag = false;
    					break;
    				}
    			}
    			if(unflag){
    				var data = {orderid:rows[i].orderid,id:rows[i].id};
    				unChecked.push(rows[i]);
    				unCheckAmount = Number(unCheckAmount) + Number(rows[i].taxamount);
    			}
    		}
    		var uncheckedstr = $("#account-nochecked-detail-selectBillDetailPage").val();
   			var unCheckedArr = null;
   			var data = [];
   			if(null!=uncheckedstr &&　uncheckedstr!=""){
   				unCheckedArr = $.parseJSON(uncheckedstr);
   			}else{
   				unCheckedArr = [];
   			}
   			for(var i=0;i<unCheckedArr.length;i++){
 				var detail = unCheckedArr[i];
 				if(unCheckedArr[i].orderid!="${id}"){
 					data.push(unCheckedArr[i]);
 				}
 			}
    		if(unChecked.length>0){
    			for(var i=0;i<unChecked.length;i++){
	   				var object = {orderid:unChecked[i].orderid,id:unChecked[i].id};
	   				data.push(object);
	   			}
    		}
    		var invoiceamount = 0;
   			for(var j=0;j<checkedRows.length;j++){
   				invoiceamount = Number(invoiceamount)+Number(checkedRows[j].taxamount);
   			}
   			var rowIndex = $("#account-datagrid-sourceBill").datagrid("getRowIndex","${id}");
   			var datarows = $("#account-datagrid-sourceBill").datagrid("getRows");
   			var rowData = datarows[rowIndex];
   			rowData.invoiceamount = invoiceamount;
	    	$("#account-datagrid-sourceBill").datagrid('updateRow',{index:rowIndex, row:rowData});
   			$("#account-nochecked-detail-selectBillDetailPage").val(JSON.stringify(data));
   			countTotalAmount();
    		//单据未选择金额
    		var uncheckAmountstr = $("#account-checkedbill-uncheckamount-selectBillDetailPage").val();
    		var uncheckAmountArr = null;
    		if(null!=uncheckAmountstr &&　uncheckAmountstr!=""){
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
    		$("#account-checkedbill-uncheckamount-selectBillDetailPage").val(JSON.stringify(uncheckAmountArr));
    		$("#account-panel-selectBillDetailPage").dialog("close");
		}
    </script>
  </body>
</html>
