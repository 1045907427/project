<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>应收交接单新增页面</title>
    <%@include file="/include.jsp"%>
  </head>
  
  <body>
    <table id="account-datagrid-receipthand"></table>
    <div id="account-toolbar-receipthand" style="padding:0px;height:auto">
        <div class="buttonBG">
            <a href="javaScript:void(0);" id="account-add-receipthand" class="easyui-linkbutton" plain="true" iconCls="button-add" title="申请交接单">申请交接单</a>
        </div>
    	<form action="" id="account-form-receipthand">
    		<table class="querytable">
    			<tr>
    				<td>日期：</td>
    				<td><input type="text" name="businessdate1" style="width:90px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/>
    					到 <input type="text" name="businessdate2" style="width:90px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/></td>
    				<td>单据编号：</td>
    				<td><input type="text" name="billid" style="width: 130px;"/>
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
    				<td><input type="text" id="account-customerid-receipthand" name="customerid"/></td>
    				<td>总店：</td>
    				<td><input type="text" id="account-pcustomerid-receipthand" name="pcustomerid"/></td>
                    <td>收款人：</td>
                    <td><input type="text" id="account-payee-receipthand" name="payeeid"/></td>
    			</tr>
    			<tr>
    				<td>客户业务员：</td>
    				<td><input type="text" id="account-salesuser-receipthand" name="salesuser"/></td>
                    <td>销售区域：</td>
                    <td><input type="text" id="account-salesarea-receipthand" name="salesarea"/></td>
    				<td colspan="2">
    					<a href="javaScript:void(0);" id="account-query-receipthand" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="account-reload-receipthand" class="button-qr">重置</a>
    				</td>
    			</tr>
    		</table>
    	</form>
    </div>
    <input id="account-nochecked-detail-receipthand" type="hidden"/>
    <input id="account-checkedbill-uncheckamount-receipthand" type="hidden"/>
    <div id="account-panel-receiptlist-receipthand"></div>
    <div id="account-panel-receipthandDetailPage"></div>
    <script type="text/javascript">
    	var RH_footerobject = null;
    	var queryJSON = null;
    	$(function(){
    		var receiptHandJson = $("#account-datagrid-receipthand").createGridColumnLoad({
				frozenCol : [[{field:'idok',checkbox:true,isShow:true}]],
				commonCol : [[
					  {field:'customerid',title:'客户编码',width:60,sortable:true},
					  {field:'customername',title:'客户名称',width:250,isShow:true,
					  	formatter:function(value,row,index){
							if(null!=value){
								return '<a href="javascript:showReceiptListPage(\''+row.customerid+'\',\''+value+'\','+index+')">'+value+'</a>';
							}
				        }
					  },
					  {field:'pcustomerid',title:'总店',width:80,
					  	formatter:function(value,rowData,rowIndex){
			        		return rowData.pcustomername;
			        	}
					  },
					  {field:'salesdept',title:'销售部门',width:80,sortable:true,hidden:true,
					  	formatter:function(value,rowData,rowIndex){
			        		return rowData.salesdeptname;
			        	}
					  },
					  {field:'salesarea',title:'销售区域',width:60,sortable:true,
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
					  {field:'checkreceiptamount',title:'选中应收金额',width:80,align:'right',isShow:true,
    						formatter:function(value,row,index){
				        		return formatterMoney(value);
					        }
					  }
		           ]]
			});
			
			//销售区域
			$("#account-salesarea-receipthand").widget({
				referwid:'RT_T_BASE_SALES_AREA',
				width:130,
    			singleSelect:false,
    			onlyLeafCheck:false
			});
			$("#account-datagrid-receipthand").datagrid({ 
		 		authority:receiptHandJson,
		 		frozenColumns: receiptHandJson.frozen,
				columns:receiptHandJson.common,
		 		fit:true,
		 		method:'post',
		 		rownumbers:true,
		 		pagination:true,
		 		idField:'customerid',
		 		sortName:'customerid',
		 		sortOrder:'asc',
		 		singleSelect:false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
		 		showFooter: true,
		 		pageSize:200,
				pageList:[20,100,200,500,1000],
				toolbar:'#account-toolbar-receipthand',
				onBeforeLoad:function(){
				},
				onLoadSuccess:function(){
					var footerrows = $(this).datagrid('getFooterRows');
					if(null!=footerrows && footerrows.length>0){
						RH_footerobject = footerrows[0];
						countTotalAmount();
					}
		 		},
				onCheckAll:function(rows){
					for(var i=0;i<rows.length;i++){
						if(null == rows[i].checkreceiptamount || rows[i].checkreceiptamount == 0){
		    				rows[i].checkreceiptamount = rows[i].totalreceipttaxamount;
		    			}
		    			$(this).datagrid('updateRow',{index:i, row:rows[i]});
					}
					if(rows.length > 0){
						countTotalAmount();
					}
				},
				onUncheckAll:function(rows){
					for(var i=0;i<rows.length;i++){
						var rowData = rows[i];
						rowData.checkreceiptamount = null;
					}
					if(rows.length != 0){
						$(this).datagrid("loadData",rows);
						$(this).datagrid("reloadFooter",[{customerid:'选中合计',checkreceiptamount:0,totalreceipttaxamount:0},RH_footerobject] );
					}
				},
				onCheck:function(rowIndex,rowData){
					var uncheckAmountstr = $("#account-checkedbill-uncheckamount-receipthand").val();
   					var uncheckAmountArr = null;
   					var uncheckAmount = 0;
   					if(null!=uncheckAmountstr && uncheckAmountstr!=""){
		   				uncheckAmountArr = $.parseJSON(uncheckAmountstr);
		   			}else{
		   				uncheckAmountArr = [];
		   			}
   					for(var i=0;i<uncheckAmountArr.length;i++){
		    			if(uncheckAmountArr[i].customerid==rowData.customerid){
		    				uncheckAmount = uncheckAmountArr[i].uncheckamount;
		    				break;
		    			}
		    		}
					rowData.checkreceiptamount = Number(rowData.totalreceipttaxamount)-Number(uncheckAmount);
	    			$(this).datagrid('updateRow',{index:rowIndex, row:rowData});
					countTotalAmount();
				},
				onUncheck:function(rowIndex,rowData){
					rowData.checkreceiptamount = null;
		    		$(this).datagrid('updateRow',{index:rowIndex, row:rowData});
					countTotalAmount();
				}
			}).datagrid("columnMoving");
			$("#account-customerid-receipthand").widget({
				referwid:'RL_T_BASE_SALES_CUSTOMER',
				width:205,
    			singleSelect:false
			});
			//总店
			$("#account-pcustomerid-receipthand").widget({
				referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
				width:130,
    			singleSelect:true
			});
			//客户业务员
			$("#account-salesuser-receipthand").widget({
				referwid:'RL_T_BASE_PERSONNEL_CLIENTSELLER',
				width:205,
    			singleSelect:true
			});
			//收款人
			$("#account-payee-receipthand").widget({
				referwid:'RL_T_BASE_CUSTOMER_PAYEE',
				width:125,
    			singleSelect:true
			});
			
			//查询
			$("#account-query-receipthand").click(function(){
				queryJSON = $("#account-form-receipthand").serializeJSON();
				$("#account-datagrid-receipthand").datagrid({
		       		url: 'account/receipthand/getReceiptListGroupByCustomerForReceiptHand.do',
	      			pageNumber:1,
					queryParams:queryJSON
		       	}).datagrid("columnMoving");
		       	$("#account-datagrid-receipthand").datagrid('clearChecked');
				$("#account-datagrid-receipthand").datagrid('clearSelections');
			});
			//重置
			$("#account-reload-receipthand").click(function(){
				$("#account-customerid-receipthand").customerWidget('clear');
				$("#account-salesarea-receipthand").widget('clear');
				$("#account-pcustomerid-receipthand").widget('clear');
				$("#account-salesuser-receipthand").widget('clear');
				$("#account-payee-receipthand").widget('clear');
				$("#account-datagrid-receipthand").datagrid('clearChecked');
				$("#account-datagrid-receipthand").datagrid('clearSelections');
				$("#account-nochecked-detail-receipthand").val("");
				$("#account-checkedbill-uncheckamount-receipthand").val("");
				$('#account-form-receipthand')[0].reset();
				$("#account-datagrid-receipthand").datagrid('loadData',{total:0,rows:[],footer:[]});
			});
			
			//生成交接单
			$("#account-add-receipthand").click(function(){
				var rows = $("#account-datagrid-receipthand").datagrid("getChecked");
				if(rows.length == 0){
					$.messager.alert("提醒","请选择数据!");
					return false;
				}
				var customerids = null;
				for(var i=0;i<rows.length;i++){
					if(customerids==null){
						customerids = rows[i].customerid;
					}else{
						customerids +="," + rows[i].customerid;
					}
				}
				queryJSON['customerid'] = customerids;
				queryJSON['receiptids'] = $("#account-nochecked-detail-receipthand").val();
				loading("明细页面加载中..");
				$.ajax({
					url:"account/receipthand/showReceiptHandAddDetailPage.do",
					dataType:'html',
					type:'post',
					data:queryJSON,
					success:function(json){
						loaded();
						$("#account-panel-receipthandDetailPage").dialog({
							title:"已选单据明细列表",
						    fit:true,
							closed:false,
							modal:true,
							cache:false,
							maximizable:true,
							resizable:true,
							content:json,
						    buttons:[
						    		{
										text:'生成交接单',
										handler:function(){
											addReceiptHandByRefer();
										}
									}
							]
						});
					},
					error:function(){
						loaded();
					}
				});
			});
    	});
    	
    	function countTotalAmount(){
    		var rows =  $("#account-datagrid-receipthand").datagrid('getChecked');
    		var totalreceipttaxamount = 0;
    		var checkreceiptamount = 0;
    		for(var i=0;i<rows.length;i++){
    			totalreceipttaxamount = Number(totalreceipttaxamount)+Number(rows[i].totalreceipttaxamount == undefined ? 0 : rows[i].totalreceipttaxamount);
    			if(null != checkreceiptamount){
    				checkreceiptamount = Number(checkreceiptamount)+Number(rows[i].checkreceiptamount == undefined ? 0 : rows[i].checkreceiptamount);
    			}
    		}
    		var foot=[{customerid:'选中合计',totalreceipttaxamount:totalreceipttaxamount,checkreceiptamount:checkreceiptamount}];
    		if(null!=RH_footerobject){
           		foot.push(RH_footerobject);
       		}
    		$("#account-datagrid-receipthand").datagrid("reloadFooter",foot);
    	}
    	
    	function showReceiptListPage(customerid,customername,rowIndex){
			queryJSON['customerid'] = customerid;
    		$("#account-panel-receiptlist-receipthand").dialog({
				href:"account/receipthand/showReceiptListOfReceiptDetailPage.do?queryJSON="+JSON.stringify(queryJSON),
				title:"客户:"+customerid+customername+",单据明细列表",
			    fit:true,
				closed:false,
				modal:true,
				cache:false,
				maximizable:true,
				resizable:true,
			    buttons:[{
					text:'确认',
					handler:function(){
						addNocheckedDetail();
					}
				}]
			});
			$("#account-datagrid-receipthand").datagrid("checkRow",rowIndex);
    	}
    </script>
  </body>
</html>
