<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>交接单添加发货回单新增</title>
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
		<div class="easyui-layout" data-options="fit:true">
			<div data-options="region:'center',split:true,border:true">
				<table id="account-datagrid-receiptHandSourcePage"></table>
				<div id="account-toolbar-receiptHandSourcePage" style="padding:2px;height:auto">
					<form id="account-form-receiptHandSourcePage" method="post" action="">
						<table class="querytable">
			    			<tr>
			    				<td>日期：</td>
			    				<td><input type="text" name="businessdate1" style="width:90px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/> 
			    					到 <input type="text" name="businessdate2" style="width:90px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/></td>
			    				<td>单据编号：</td>
			    				<td><input type="text" name="billid" class="len150"/>
			    					<input type="hidden" name="receipthandid" value="${receipthandid }"/>
			    				</td>
			    				<td>单据类型：</td>
			    				<td><select name="billtype" style="width: 125px;">
			    					<option></option>
			    					<option value="1">销售发货回单</option>
			    					<option value="2">销售退货通知单</option>
			    					<option value="3">冲差单</option>
			    				</select></td>
			    			</tr>
			    			<tr>
			    				<td>客户名称：</td>
			    				<td><input type="text" id="account-customerid-receiptHandSourcePage" name="customerid" style="width: 205px;"/></td>
			    				<td>销售区域：</td>
			    				<td><input type="text" id="account-salesarea-receiptHandSourcePage" name="salesarea"/></td>
			    				<td>总店：</td>
			    				<td><input type="text" id="account-pcustomerid-receiptHandSourcePage" name="pcustomerid"/></td>
			    			</tr>
			    			<tr>
			    				<td>客户业务员：</td>
			    				<td><input type="text" id="account-salesuser-receiptHandSourcePage" name="salesuser"/></td>
			    				<td>收款人：</td>
			    				<td><input type="text" id="account-payee-receiptHandSourcePage" name="payeeid"/></td>
			    				<td colspan="2">
			    					<a href="javaScript:void(0);" id="account-query-receiptHandSourcePage" class="button-qr">查询</a>
									<a href="javaScript:void(0);" id="account-reload-receiptHandSourcePage" class="button-qr">重置</a>
			    				</td>
			    			</tr>
			    		</table>
					</form>
				</div>
			</div>
			<div data-options="region:'south',border:false">
				<div class="buttonDetailBG" style="text-align:right;">
		  			<input type="button" value="确 定" name="savenogo" id="account-save-receiptHandSourcePage" />
	  			</div>
			</div>
		</div>
		<script type="text/javascript">
			var RH_footerobject = null;
			var yearmonthobj = [];
			$(function(){
				var receiptHandJson = $("#account-datagrid-receiptHandSourcePage").createGridColumnLoad({
					frozenCol : [[{field:'idok',checkbox:true,isShow:true}]],
					commonCol : [[
						  {field:'id',title:'单据编号',width:130,sortable:true},
						  {field:'billtype',title:'单据类型',width:90,sortable:true,
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
						  {field:'saleorderid',title:'订单编号',width:130,sortable:true},
						  {field:'businessdate',title:'业务日期',width:80,sortable:true},
						  {field:'customerid',title:'客户编码',width:60,sortable:true},
						  {field:'customername',title:'客户名称',width:150,isShow:true},
						  {field:'salesdept',title:'销售部门',width:80,sortable:true,hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return rowData.salesdeptname;
				        	}
						  },
						  {field:'salesarea',title:'销售区域',width:70,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return rowData.salesareaname;
				        	}
						  },
						  {field:'salesuser',title:'客户业务员',width:70,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return rowData.salesusername;
				        	}
						  },
						  {field:'payeeid',title:'收款人',width:60,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return rowData.payeename;
				        	}
						  },
						  {field:'totalreceipttaxamount',title:'应收金额',width:80,align:'right',
	    						formatter:function(value,row,index){
					        		return formatterMoney(value);
						        }
						  },
						  {field:'billno',title:'来源编号',width:80,sortable:true},
						  {field:'status',title:'状态',width:60,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return getSysCodeName("status",value);
				        	}
						  },
						  {field:'indooruserid',title:'销售内勤',width:60,sortable:true,
						  	formatter:function(value,rowData,index){
				        		return rowData.indoorusername;
					        }
						  },
						  {field:'duefromdate',title:'应收日期',width:80,hidden:true,sortable:true},
						  {field:'stopusername',title:'中止人',width:80,hidden:true,sortable:true},
						  {field:'stoptime',title:'中止时间',width:80,hidden:true,sortable:true},
						  {field:'remark',title:'备注',width:80,sortable:true},
						  {field:'addusername',title:'制单人',width:60,sortable:true},
						  {field:'addtime',title:'制单时间',width:120,sortable:true}
			           ]]
				});
				
				//销售区域
				$("#account-salesarea-receiptHandSourcePage").widget({
					referwid:'RT_T_BASE_SALES_AREA',
					width:150,
	    			singleSelect:false,
	    			onlyLeafCheck:false
				});
				$("#account-datagrid-receiptHandSourcePage").datagrid({ 
			 		authority:receiptHandJson,
			 		frozenColumns: receiptHandJson.frozen,
					columns:receiptHandJson.common,
			 		fit:true,
			 		method:'post',
			 		rownumbers:true,
			 		pagination:true,
			 		idField:'id',
			 		singleSelect:false,
			 		checkOnSelect:true,
			 		selectOnCheck:true,
			 		showFooter: true,
			 		pageSize:500,
					pageList:[20,100,200,500,1000],
					view:groupview,
	                groupField:'yearmonth',
	                groupFormatter:function(value,rows){
	                	var yearmonthAmount = getYearMonthAmount(rows);
	                	yearmonthobj[value] = JSON.stringify(rows);
	                    return '<input type="checkbox" class="checkbox1 checkall" id="checkall-'+value+'" onclick="yearmonthcheckall(\''+value+'\')"/>'+'<label for="checkall-'+value+'" class="divtext">'+value + '['+rows.length+']  合计应收金额：' + Number(yearmonthAmount).toFixed(2)+'</label>';
	                },
					toolbar:'#account-toolbar-receiptHandSourcePage',
					onDblClickRow:function(rowIndex, rowData){
						top.addOrUpdateTab('sales/receiptPage.do?type=edit&id='+rowData.id, '销售发货回单查看');
					},
					onLoadSuccess:function(){
						var footerrows = $(this).datagrid('getFooterRows');
						if(null!=footerrows && footerrows.length>0){
							RH_footerobject = footerrows[0];
							countTotalAmount();
						}
			 		},
					onCheckAll:function(){
						$(".checkall").attr("checked", true);
						countTotalAmount();
					},
					onUncheckAll:function(){
						$(".checkall").attr("checked", false);
						countTotalAmount();
					},
					onCheck:function(rowIndex,rowData){
						isyearmonthcheckall(rowData.yearmonth);
						countTotalAmount();
					},
					onUncheck:function(rowIndex,rowData){
						isyearmonthcheckall(rowData.yearmonth);
						countTotalAmount();
					}
				}).datagrid("columnMoving");
				$("#account-customerid-receiptHandSourcePage").customerWidget({
					isdatasql:false
				});
				
				//总店
				$("#account-pcustomerid-receiptHandSourcePage").widget({
					referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
					width:125,
	    			singleSelect:true
				});
				//客户业务员
				$("#account-salesuser-receiptHandSourcePage").widget({
					referwid:'RL_T_BASE_PERSONNEL_CLIENTSELLER',
					width:205,
	    			singleSelect:true
				});
				//收款人
				$("#account-payee-receiptHandSourcePage").widget({
					referwid:'RL_T_BASE_CUSTOMER_PAYEE',
					width:150,
	    			singleSelect:true
				});
				
				//查询
				$("#account-query-receiptHandSourcePage").click(function(){
					var queryJSON = $("#account-form-receiptHandSourcePage").serializeJSON();
					queryJSON["receiptids"] = exsitreceiptids;
					$("#account-datagrid-receiptHandSourcePage").datagrid({
		      			url:'account/receipthand/getReceiptListForReceiptHand.do',
		      			pageNumber:1,
						queryParams:queryJSON
			       	}).datagrid("columnMoving");
				});
				//重置
				$("#account-reload-receiptHandSourcePage").click(function(){
					$("#account-customerid-receiptHandSourcePage").customerWidget('clear');
					$("#account-salesarea-receiptHandSourcePage").widget('clear');
					$("#account-pcustomerid-receiptHandSourcePage").widget('clear');
					$("#account-salesuser-receiptHandSourcePage").widget('clear');
					$("#account-payee-receiptHandSourcePage").widget('clear');
					$("#account-datagrid-receiptHandSourcePage").datagrid('clearChecked');
					$("#account-datagrid-receiptHandSourcePage").datagrid('clearSelections');
					$('#account-form-receiptHandSourcePage')[0].reset();
					$("#account-datagrid-receiptHandSourcePage").datagrid('loadData',{total:0,rows:[],footer:[]});
				});
			});
			//获取合计
	    	function countTotalAmount(){
	    		var rows =  $("#account-datagrid-receiptHandSourcePage").datagrid('getChecked');
	    		var totalreceipttaxamount = 0;
	    		for(var i=0;i<rows.length;i++){
	    			totalreceipttaxamount = Number(totalreceipttaxamount)+Number(rows[i].totalreceipttaxamount == undefined ? 0 : rows[i].totalreceipttaxamount);
	    		}
	    		var foot=[{id:'选中金额',totalreceipttaxamount:totalreceipttaxamount}];
	    		if(null!=RH_footerobject){
	           		foot.push(RH_footerobject);
	       		}
	    		$("#account-datagrid-receiptHandSourcePage").datagrid("reloadFooter",foot);
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
	    		var yearmonthjsonstr = yearmonthobj[yearmonth];
	    		if("" != yearmonthjsonstr){
		    		var rows = $.parseJSON(yearmonthjsonstr);
		    		if($("#checkall-"+yearmonth).is(":checked")){
		    			for(var i=0;i<rows.length;i++){
		    				var obj = rows[i];
		    				var index = $("#account-datagrid-receiptHandSourcePage").datagrid('getRowIndex',obj.id);
		    				$("#account-datagrid-receiptHandSourcePage").datagrid('checkRow',index);
		    			}
		    		}else{
		    			for(var i=0;i<rows.length;i++){
		    				var obj = rows[i];
		    				var index = $("#account-datagrid-receiptHandSourcePage").datagrid('getRowIndex',obj.id);
		    				$("#account-datagrid-receiptHandSourcePage").datagrid('uncheckRow',index);
		    			}
		    		}
	    		}
			}
			
			//勾选或不勾选记录时判断是否全选
			function isyearmonthcheckall(yearmonth){
				var yearmonthjsonstr = yearmonthobj[yearmonth];
	    		if("" != yearmonthjsonstr){
	    			var totalrowsnum = $.parseJSON(yearmonthjsonstr).length;
	    			var checkrows = $("#account-datagrid-receiptHandSourcePage").datagrid('getChecked');
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
		   	
		   	//添加选中的发货回单
		   	$("#account-save-receiptHandSourcePage").click(function(){
		   		var rows = $("#account-datagrid-receiptHandSourcePage").datagrid("getChecked");
		   		if(rows.length == 0){
		   			$.messager.alert("提醒","请勾选发货单！");
    				return false;
		   		}
		   		$.messager.confirm("警告","是否添加该发货回单?<br/>添加之后，客户明细列表内容会被刷新！",function(r){
		   			if(r){
		   				loading("添加中..");
		   				var sourceids = "";
		   				for(var i=0;i<rows.length;i++){
		   					if(sourceids == ""){
		   						sourceids = rows[i].id;
		   					}else{
		   						sourceids += "," + rows[i].id;
		   					}
		   				}
		   				
		   				$.ajax({
				            url :'account/receipthand/getReceiptHandDetailListMap.do',
				            type:'post',
				            data:{receipthandid:"${receipthandid}",sourceids:sourceids},
				            dataType:'json',
				            success:function(json){
								var newbillrows = [];
				            	var billrows = json.billList;
			    				if(billrows.length != 0){
			    					//删除空白行
									var oldbillrows = $dgBillList.datagrid('getRows');
									for(var i=0;i<oldbillrows.length;i++){
										if(oldbillrows[i].relatebillid==undefined)
										{
											$dgBillList.datagrid('deleteRow',i);
											i--;
										}
									}
			    					//插入选中行
			    					if('null' == relatebilladdid){
			    						relatebilladdid = "";
			    					}
			    					relatebillids = "${receiptids}";
					    			for(var i=0;i<billrows.length;i++){
					    				$dgBillList.datagrid('appendRow',billrows[i]);
					    				if(relatebillids == ""){
					    					relatebillids = billrows[i].relatebillid;
					    				}else{
					    					relatebillids += "," + billrows[i].relatebillid;
					    				}
					    				if(relatebilladdid == ""){
					    					relatebilladdid = billrows[i].relatebillid;
					    				}else{
					    					relatebilladdid += "," + billrows[i].relatebillid;
					    				}
					    			}
			    					setBillFooter();
			    					$('#account-dialog-receiptHandAddReceipt').dialog('close',true);
			    					loaded();
			    					var totalrows = $dgBillList.datagrid('getRows');
			    					if(totalrows.length<12){
			    						for(var i=totalrows.length;i<12;i++){
											$dgBillList.datagrid('appendRow',{});
										}
			    					}else{
			    						$dgBillList.datagrid('appendRow',{});
			    					}
			    					loadDgCustomerListadd();
			    				}
				            }
				        });
		   			}
		   		});
		   	});
		</script>
	</body>
</html>
