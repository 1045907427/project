<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>指定客户回单单据列表页面</title>
  </head>
  <style>
      .checkbox1{
          float:left;
          height: 22px;
          line-height: 22px;
      }
      .divtext{
          height:22px;
          line-height:22px;
          float:left;
          display: block;
      }
  </style>
  <body>
    <table id="account-datagrid-detail-receipthand"></table>
    <script type="text/javascript">
    	var yearmonthobj_detail = [];
    	$(function(){
			var receiptHandDetailJson = $("#account-datagrid-detail-receipthand").createGridColumnLoad({
				frozenCol : [[{field:'idok',checkbox:true,isShow:true}]],
				commonCol : [[
					  {field:'id',title:'编号',resizable:true,sortable:true},
					  {field:'billtype',title:'单据类型',resizable:true,sortable:true,
					  	formatter:function(value,rowData,rowIndex){
			        		if("1" == value){
			        			return "发货回单";
			        		}else if("2" == value){
			        			return "销售退货通知单";
			        		}else if("3" == value){
			        			return "冲差单";
			        		}
			        	}
					  },
					  {field:'saleorderid',title:'订单编号',resizable:true,sortable:true},
					  {field:'businessdate',title:'业务日期',resizable:true,sortable:true},
					  {field:'customerid',title:'客户编码',resizable:true,sortable:true},
					  {field:'customername',title:'客户名称',resizable:true,isShow:true},
					  {field:'salesdept',title:'销售部门',resizable:true,sortable:true,hidden:true,
					  	formatter:function(value,rowData,rowIndex){
			        		return rowData.salesdeptname;
			        	}
					  },
					  {field:'salesuser',title:'客户业务员',resizable:true,sortable:true,
					  	formatter:function(value,rowData,rowIndex){
			        		return rowData.salesusername;
			        	}
					  },
					  {field:'payeeid',title:'收款人',resizable:true,sortable:true,
					  	formatter:function(value,rowData,rowIndex){
			        		return rowData.payeename;
			        	}
					  },
					  {field:'totalreceipttaxamount',title:'应收金额',resizable:true,align:'right',
    						formatter:function(value,row,index){
				        		return formatterMoney(value);
					        }
					  },
					  {field:'billno',title:'来源编号',width:80,sortable:true},
					  {field:'status',title:'状态',resizable:true,sortable:true,
					  	formatter:function(value,rowData,rowIndex){
			        		return getSysCodeName("status",value);
			        	}
					  },
					  {field:'indooruserid',title:'销售内勤',resizable:true,sortable:true,
					  	formatter:function(value,rowData,index){
			        		return rowData.indoorusername;
				        }
					  },
					  {field:'duefromdate',title:'应收日期',width:80,hidden:true,sortable:true},
					  {field:'stopusername',title:'中止人',width:80,hidden:true},
					  {field:'stoptime',title:'中止时间',width:80,hidden:true},
					  {field:'remark',title:'备注',resizable:true},
					  {field:'addusername',title:'制单人',resizable:true},
					  {field:'addtime',title:'制单时间',resizable:true}
		           ]]
			});
			
			$("#account-datagrid-detail-receipthand").datagrid({ 
		 		authority:receiptHandDetailJson,
		 		frozenColumns: receiptHandDetailJson.frozen,
				columns:receiptHandDetailJson.common,
		 		fit:true,
		 		method:'post',
		 		rownumbers:true,
		 		idField:'id',
		 		singleSelect:false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
		 		showFooter: true,
		 		view:groupview,
                groupField:'yearmonth',
                groupFormatter:function(value,rows){
                	var yearmonthAmount = getYearMonthAmount(rows);
                	yearmonthobj_detail[value] = JSON.stringify(rows);
                    return '<input type="checkbox" class="checkbox1 checkall" id="checkall-'+value+'" checked="checked" onclick="yearmonthcheckall(\''+value+'\')"/>'+'<label for="checkall-'+value+'" class="divtext">'+value + '['+rows.length+']  合计应收金额：' + Number(yearmonthAmount).toFixed(2)+'</label>' ;
                },
		 		data: JSON.parse('${detailList}'),
		 		onBeforeLoad:function(){
		 		},
				onDblClickRow:function(rowIndex, rowData){
                    if(rowData.billtype == "1"){
                        top.addOrUpdateTab('sales/receiptPage.do?type=view&id='+rowData.id, '销售发货回单查看');
                    }else if(rowData.billtype == "2"){
                        top.addOrUpdateTab('sales/rejectBill.do?type=view&id='+rowData.id, '退货通知单查看');
                    }else if(rowData.billtype == "3"){
                        top.addOrUpdateTab('account/receivable/showCustomerPushBanlaceListPage.do?id='+rowData.id+'&status='+rowData.status, '客户应收款冲差');
                    }
				},
				onLoadSuccess:function(){
					$(this).datagrid("resize");
		 			initSelect();
		 		},
				onCheckAll:function(){
					$(".checkall").attr("checked", true);
					countTotalDetailAmount();
				},
				onUncheckAll:function(){
					$(".checkall").attr("checked", false);
					countTotalDetailAmount();
				},
				onCheck:function(rowIndex,rowData){
					isyearmonthcheckall(rowData.yearmonth);
					countTotalDetailAmount();
				},
				onUncheck:function(rowIndex,rowData){
					isyearmonthcheckall(rowData.yearmonth);
					countTotalDetailAmount();
				}
			}).datagrid("columnMoving");
			
    	});
    	//初始选中
    	function initSelect(){
    		var uncheckedstr = $("#account-nochecked-detail-receipthand").val();
    		var data = null;
    		if(null != uncheckedstr && uncheckedstr != ""){
    			data = uncheckedstr.substring(0,uncheckedstr.length-Number(1)).split(",");
    		}else{
    			data = [];
    		}
    		$("#account-datagrid-detail-receipthand").datagrid("checkAll");
    		for(var i=0;i<data.length;i++){
    			var index = $("#account-datagrid-detail-receipthand").datagrid("getRowIndex",data[i]);
    			if(index != -1){
    				$("#account-datagrid-detail-receipthand").datagrid("uncheckRow",index);
    			}
    		}
    	}
    	
    	function countTotalDetailAmount(){
    		var rows =  $("#account-datagrid-detail-receipthand").datagrid('getChecked');
    		var totalreceipttaxamount = 0;
    		for(var i=0;i<rows.length;i++){
    			totalreceipttaxamount = Number(totalreceipttaxamount)+Number(rows[i].totalreceipttaxamount == undefined ? 0 : rows[i].totalreceipttaxamount);
    		}
    		if(rows.length > 0){
    			$("#account-datagrid-detail-receipthand").datagrid("reloadFooter",[{id:'选中合计',totalreceipttaxamount:totalreceipttaxamount},{id:'合计',totalreceipttaxamount:${totalreceipttaxamount}}]);
    		}else{
    			$("#account-datagrid-detail-receipthand").datagrid("reloadFooter",[{id:'选中合计',totalreceipttaxamount:0},{id:'合计',totalreceipttaxamount:${totalreceipttaxamount}}]);
    		}
    	}
    	
    	//获取未选中对象
    	function addNocheckedDetail(){
    		var rows = $("#account-datagrid-detail-receipthand").datagrid("getRows");
    		var checkedRows = $("#account-datagrid-detail-receipthand").datagrid("getChecked");
    		var uncheckedstr = $("#account-nochecked-detail-receipthand").val();
    		var unCheckAmount = 0;
    		for(var i=0;i<checkedRows.length;i++){
    			if(uncheckedstr.indexOf(checkedRows[i].id) != -1){
    				uncheckedstr = uncheckedstr.replace(checkedRows[i].id+",","");
    			}
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
    				if(uncheckedstr.indexOf(rows[i].id) == -1){
    					uncheckedstr += rows[i].id + ",";
    				}
    				unCheckAmount = Number(unCheckAmount) + Number(rows[i].totalreceipttaxamount);
    			}
    		}
    		$("#account-nochecked-detail-receipthand").val(uncheckedstr);
    		
    		var checkreceiptamount = 0;
   			for(var j=0;j<checkedRows.length;j++){
   				checkreceiptamount = Number(checkreceiptamount)+Number(checkedRows[j].totalreceipttaxamount);
   			}
   			var rowIndex = $("#account-datagrid-receipthand").datagrid("getRowIndex","${customerid}");
   			var datarows = $("#account-datagrid-receipthand").datagrid("getRows");
   			var rowData = datarows[rowIndex];
   			rowData.checkreceiptamount = checkreceiptamount;
	    	$("#account-datagrid-receipthand").datagrid('updateRow',{index:rowIndex, row:rowData});
	    	countTotalAmount();
	    	
	    	//单据未选择金额
    		var uncheckAmountstr = $("#account-checkedbill-uncheckamount-receipthand").val();
    		var uncheckAmountArr = null;
    		if(null!=uncheckAmountstr &&　uncheckAmountstr!=""){
   				uncheckAmountArr = $.parseJSON(uncheckAmountstr);
   			}else{
   				uncheckAmountArr = [];
   			}
   			var uncheckamountflag = true;
   			for(var i=0;i<uncheckAmountArr.length;i++){
    			if(uncheckAmountArr[i].customerid=="${customerid}"){
    				uncheckamountflag = false;
    				uncheckAmountArr[i].uncheckamount = unCheckAmount;
    				break;
    			}
    		}
    		if(uncheckamountflag){
	    		var object = {customerid:"${customerid}",uncheckamount:unCheckAmount};
	    		uncheckAmountArr.push(object);
    		}
    		$("#account-checkedbill-uncheckamount-receipthand").val(JSON.stringify(uncheckAmountArr));
	    	$("#account-panel-receiptlist-receipthand").dialog("close");
    	}
    	
    	//分月应收金额合计
    	function getYearMonthAmount(rows){
    		var amountSum = 0;
    		for(var i=0;i<rows.length;i++){
    			amountSum = Number(amountSum) + Number(rows[i].totalreceipttaxamount);
    		}
    		return amountSum;
    	}
    	
    	//是否全选
    	function yearmonthcheckall(yearmonth){
    		var yearmonthjsonstr = yearmonthobj_detail[yearmonth];
    		if("" != yearmonthjsonstr){
	    		var rows = $.parseJSON(yearmonthjsonstr);
	    		if($("#checkall-"+yearmonth).is(":checked")){
	    			for(var i=0;i<rows.length;i++){
	    				var obj = rows[i];
	    				var index = $("#account-datagrid-detail-receipthand").datagrid('getRowIndex',obj.id);
	    				$("#account-datagrid-detail-receipthand").datagrid('checkRow',index);
	    			}
	    		}else{
	    			for(var i=0;i<rows.length;i++){
	    				var obj = rows[i];
	    				var index = $("#account-datagrid-detail-receipthand").datagrid('getRowIndex',obj.id);
	    				$("#account-datagrid-detail-receipthand").datagrid('uncheckRow',index);
	    			}
	    		}
    		}
		}
		
		//勾选或不勾选记录时判断是否全选
		function isyearmonthcheckall(yearmonth){
			var yearmonthjsonstr = yearmonthobj_detail[yearmonth];
    		if("" != yearmonthjsonstr){
    			var totalrowsnum = $.parseJSON(yearmonthjsonstr).length;
    			var checkrows = $("#account-datagrid-detail-receipthand").datagrid('getChecked');
    			var checknum = 0;
    			for(var i=0;i<checkrows.length;i++){
    				var obj = checkrows[i];
    				if(yearmonth == obj.yearmonth){
    					checknum++;
    				}
    			}
    			if(totalrowsnum == checknum){
    				$("#checkall-"+yearmonth).attr("checked", true);
    			}else{
    				$("#checkall-"+yearmonth).attr("checked", false);
    			}
    		}
		}
    </script>
  </body>
</html>
