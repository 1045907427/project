<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>销售发票新增</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'north',border:false">
            <div class="buttonBG">
                <a href="javaScript:void(0);" id="account-export-salesInvoiceAdd" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
                <a href="javaScript:void(0);" id="account-add-salesInvoiceAdd" class="easyui-linkbutton" plain="true" iconCls="button-add" >申请抽单</a>
            </div>
        </div>
        <div data-options="region:'center'">
            <table id="account-orderDatagrid-dispatchBillSourcePage"></table>
            <div id="account-toolbar-query-salesInvoiceSouceBill" style="padding:2px;height:auto">
                <form id="account-form-query-salesInvoiceSouceBill">
                    <table class="querytable">
                        <tr>
                            <td>业务日期：</td>
                            <td>
                                <input type="text" name="businessdate1" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /> 到 <input type="text" name="businessdate2" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" />
                            </td>
                            <td>品牌名称：</td>
                            <td>
                                <input type="text" id="account-brand-salesInvoiceSourceQueryPage" name="brandid" style="width: 150px;"/>
                            </td>

                            <td>总店名称：</td>
                            <td>
                                <input type="text" id="account-pcustomerid-salesInvoiceSourceQueryPage" name="pcustomerid" />
                            </td>
                        </tr>
                        <tr>
                            <td>订单日期：</td>
                            <td>
                                <input type="text" name="orderdate1" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /> 到 <input type="text" name="orderdate2" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" />
                            </td>
                            <td>单据类型：</td>
                            <td>
                                <select id="account-sourcetype-salesInvoiceSourceQueryPage" name="sourcetype" style="width: 150px;">
                                    <option></option>
                                    <option value="1">销售发货回单</option>
                                    <option value="2">销售退货通知单</option>
                                    <option value="3">冲差单</option>
                                    <option value="4">应收款期初</option>
                                </select>
                            </td>
                            <td>单据编号：</td>
                            <td><input type="text" style="width: 150px;" name="id"/></td>
                        </tr>
                        <tr>
                            <td>客户名称：</td>
                            <td>
                                <input type="text" id="account-customerid-salesInvoiceSourceQueryPage" name="customerid" style="width: 225px"/>
                            </td>
                            <td>客户单号:</td>
                            <td><input type="text" name="sourceid" style="width: 150px" /></td>
                            <td>订单编号:</td>
                            <td><input type="text" name="saleorderid" style="width: 150px" /></td>
                        </tr>
						<tr>
							<td>销售部门：</td>
							<td>
								<input type="text" id="account-salesdept-salesInvoiceSourceQueryPage" name="salesdept" style="width: 225px"/>
							</td>
							<td>客户业务员：</td>
							<td>
								<input type="text" id="account-salesuser-salesInvoiceSourceQueryPage" name="salesuser" style="width: 150px"/>
							</td>
							<td colspan="2" style="padding-left: 10px">
								<!--  					<input type="hidden" id="account-selectid-salesInvoiceSourceQueryPage" name="selectid"/>-->
								<a href="javaScript:void(0);" id="account-queay-salesInvoiceAdd" class="button-qr">查询</a>
								<a href="javaScript:void(0);" id="account-reload-salesInvoiceAdd" class="button-qr">重置</a>

							</td>
						</tr>
                    </table>
                </form>
            </div>
            <div id="account-panel-salesinvoiceDetailPage"></div>
            <div id="account-panel-selectDetailPage"></div>
        </div>
    </div>
    <input id="account-nochecked-detail-salesinvoiceDetailPage" type="hidden"/>
    <input id="account-checkedbill-invoiceamount-salesinvoiceDetailPage" type="hidden"/>
    <input id="account-checkedbill-uncheckamount-salesinvoiceDetailPage" type="hidden"/>
  	<script type="text/javascript">
  		var totalFooter = {};
		$(function(){
			var salesInvoiceJson = $("#account-orderDatagrid-dispatchBillSourcePage").createGridColumnLoad({
				frozenCol : [[]],
				commonCol : [[
							{field:'ck', checkbox:true},
							{field:'id', title:'编号', width:130,sortable:true,
								formatter:function(value,row,index){
									if(null!=value){
										if(value!="选中合计" && value!="合计"){
											return '<a href="javascript:showDetailListPage(\''+value+'\','+index+')">'+value+'</a>';
										}else{
											return value;
										}
									}
						        }
							},
							{field:'billtype', title:'单据类型', width:100,sortable:true,
								formatter:function(value,row,index){
									if(value=='1'){
										return "销售发货回单";
									}else if(value=='2'){
										return "销售退货通知单";
									}else if(value=='3'){
										return "冲差单";
									}else if(value=='4'){
										return "应收款期初";
									}
						        }
							},
							{field:'businessdate', title:'业务日期', width:70,sortable:true},
							{field:'orderid', title:'订单编号', width:130,sortable:true},
                            {field:'sourceid', title:'客户单号', width:130,sortable:true},
							{field:'orderdate', title:'订单日期', width:70,sortable:true},
							{field:'customerid',title:'客户编码',width:60,align:'left',sortable:true},
							{field:'customername',title:'客户名称',width:100,align:'left'},
							{field:'headcustomername',title:'总店名称',width:100,align:'left'},
							{field:'handlerid',title:'对方经手人',width:80,align:'left',hidden:true},
							{field:'totaltaxamount',title:'总金额',width:60,align:'right',sortable:true,
		    						formatter:function(value,row,index){
						        		return formatterMoney(value);
							        }
							},
							{field:'uninvoiceamount',title:'未申请金额',width:80,align:'right',sortable:true,
		    						formatter:function(value,row,index){
						        		return formatterMoney(value);
							        }
							},
							{field:'invoiceamount',title:'选中申请金额',width:80,align:'right',
		    						formatter:function(value,row,index){
						        		return formatterMoney(value);
							        }
							},
							{field:'duefromdate',title:'应收日期',width:70,align:'left',sortable:true},
							{field:'salesdept',title:'销售部门',width:80,align:'left'},
							{field:'salesuser',title:'客户业务员',width:70,align:'left'},
							{field:'addusername',title:'制单人',width:60,align:'left'},
							{field:'remark',title:'备注',width:80,align:'left'}
						]]
			});
			$("#account-orderDatagrid-dispatchBillSourcePage").datagrid({ 
				authority:salesInvoiceJson,
		 		frozenColumns: salesInvoiceJson.frozen,
				columns:salesInvoiceJson.common,
		 		fit:true,
		 		method:'post',
		 		rownumbers:true,
		 		pagination:true,
		 		idField:'id',
		 		singleSelect:false,
		 		selectOnCheck:true,
		 		checkOnSelect:true,
				sortName:'customerid,orderdate',
				sortOrder:'asc',
				pageSize:1000,
				pageList:[20,100,200,500,1000],
				showFooter:true,
				toolbar:'#account-toolbar-query-salesInvoiceSouceBill',
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
					if(rows.length>=1){
						getCustomerCapital(rows[0].customerid,rows[0].headcustomerid);
					}
					for(var i=0;i<rows.length;i++){
						var rowData = rows[i];
						if(rowData.invoiceamount==null || rowData.invoiceamount==0){
							rowData.invoiceamount = rowData.uninvoiceamount;
						}
		    			$("#account-orderDatagrid-dispatchBillSourcePage").datagrid('updateRow',{index:i, row:rowData});
					}
		    		//$("#account-orderDatagrid-dispatchBillSourcePage").datagrid("clearChecked");
		    	},
		    	onUncheckAll:function(rows){
		    		for(var i=0;i<rows.length;i++){
						var rowData = rows[i];
						rowData.invoiceamount = null;
					}
					$("#account-orderDatagrid-dispatchBillSourcePage").datagrid("loadData",rows);
		    		$("#account-orderDatagrid-dispatchBillSourcePage").datagrid("reloadFooter",[{id:'选中合计',customername:'',headcustomername:'',totaltaxamount:0,uninvoiceamount:0},totalFooter] );
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
						var uncheckAmountstr = $("#account-checkedbill-uncheckamount-salesinvoiceDetailPage").val();
    					var uncheckAmountArr = null;
    					var uncheckAmount = 0;
    					if(null!=uncheckAmountstr && uncheckAmountstr!=""){
			   				uncheckAmountArr = $.parseJSON(uncheckAmountstr);
			   			}else{
			   				uncheckAmountArr = [];
			   			}
    					for(var i=0;i<uncheckAmountArr.length;i++){
			    			if(uncheckAmountArr[i].id==rowData.id){
			    				uncheckAmount = uncheckAmountArr[i].uncheckamount;
			    				break;
			    			}
			    		}
						rowData.invoiceamount = Number(rowData.uninvoiceamount)-Number(uncheckAmount);
		    			$("#account-orderDatagrid-dispatchBillSourcePage").datagrid('updateRow',{index:rowIndex, row:rowData});
						getCustomerCapital(rowData.customerid,rowData.headcustomerid);
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
		    			rowData.invoiceamount = null;
		    			$("#account-orderDatagrid-dispatchBillSourcePage").datagrid('updateRow',{index:rowIndex, row:rowData});
						getCustomerCapital(rowData.customerid,rowData.headcustomerid);
		    		}
		    	},
		    	onLoadSuccess:function(){
		    		var footerrows = $(this).datagrid('getFooterRows');
					if(null!=footerrows && footerrows.length>0){
						if(footerrows[0]!=null &&footerrows[0].id=="合计"){
							totalFooter = footerrows[0];
						}
					}
		    	}
			}).datagrid("columnMoving");
			$("#account-customerid-salesInvoiceSourceQueryPage").customerWidget({
				referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT_2',
				singleSelect:true,
    			width:225,
				isall: true
    		});
    		$("#account-pcustomerid-salesInvoiceSourceQueryPage").widget({ 
    			referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
    			singleSelect:true,
    			width:150,
    			onlyLeafCheck:true
    		});
    		$("#account-brand-salesInvoiceSourceQueryPage").widget({ 
    			referwid:'RL_T_BASE_GOODS_BRAND',
    			singleSelect:false,
    			width:150,
    			onlyLeafCheck:true
    		});
			$("#account-salesdept-salesInvoiceSourceQueryPage").widget({
				referwid:'RL_T_BASE_DEPARTMENT_SELLER',
				width:225,
				singleSelect:true
			})
			$("#account-salesuser-salesInvoiceSourceQueryPage").widget({
				referwid:'RL_T_BASE_PERSONNEL_CLIENTSELLER',
				width:150,
				singleSelect:true
			})
			$("#account-queay-salesInvoiceAdd").click(function(){
				$("#account-orderDatagrid-dispatchBillSourcePage").datagrid('loadData',{total:0,rows:[],footer:[totalFooter]});
				$("#account-orderDatagrid-dispatchBillSourcePage").datagrid('clearChecked');
				$("#account-orderDatagrid-dispatchBillSourcePage").datagrid('clearSelections');
				var queryJSON = $("#account-form-query-salesInvoiceSouceBill").serializeJSON();
		       	$("#account-orderDatagrid-dispatchBillSourcePage").datagrid({
		       		url: 'sales/getReceiptAndRejectBillList.do',
	      			pageNumber:1,
					queryParams:queryJSON
		       	}).datagrid("columnMoving");
			});
			//导出
			$("#account-export-salesInvoiceAdd").Excel('export',{
				queryForm: "#account-form-query-salesInvoiceSouceBill",
				datagrid: "#account-orderDatagrid-dispatchBillSourcePage",
		 		type:'exportUserdefined',
		 		name:'申请抽单列表',
		 		url:'sales/exportReceiptAndRejectBillList.do'
			});
			$("#account-customerid-salesInvoiceDetailSourceQueryPage").widget({ 
    			referwid:'RL_T_BASE_SALES_CUSTOMER',
    			singleSelect:true,
    			width:150,
    			onlyLeafCheck:true
    		});
    		$("#account-pcustomerid-salesInvoiceDetailSourceQueryPage").widget({ 
    			referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
    			singleSelect:true,
    			width:150,
    			onlyLeafCheck:true
    		});
			$("#account-add-salesInvoiceAdd").click(function(){
				showSalesInvoiceDetail();
			});
			$("#account-reload-salesInvoiceAdd").click(function(){

				$("#account-orderDatagrid-dispatchBillSourcePage").datagrid('loadData',{total:0,rows:[],footer:[]});
				$("#account-orderDatagrid-dispatchBillSourcePage").datagrid('clearChecked');
				$("#account-orderDatagrid-dispatchBillSourcePage").datagrid('clearSelections');
				$("#account-nochecked-detail-salesinvoiceDetailPage").val("");
                $("input[name = id]").val("");
                $("input[name = saleorderid]").val("");
                $("#account-sourcetype-salesInvoiceSourceQueryPage").val("");
				$("#account-checkedbill-invoiceamount-salesinvoiceDetailPage").val("");
				$("#account-checkedbill-uncheckamount-salesinvoiceDetailPage").val("");
				$("#account-customerid-salesInvoiceSourceQueryPage").customerWidget("clear");
    			$("#account-pcustomerid-salesInvoiceSourceQueryPage").widget("clear");
				$("#account-brand-salesInvoiceSourceQueryPage").widget('clear');
				$("#account-salesuser-salesInvoiceSourceQueryPage").widget('clear');
				$("#account-salesdept-salesInvoiceSourceQueryPage").widget('clear');
				$("#account-form-query-salesInvoiceSouceBill")[0].reset();
			});
		});
		//获取客户资金情况
    	function getCustomerCapital(customerid,pcustomerid){
    		if(customerid!=pcustomerid){
    			$.ajax({   
		            url :'account/receivable/getCustomerCapital.do?id='+customerid+"&pcustomerid="+pcustomerid,
		            type:'post',
		            dataType:'json',
		            success:function(json){
		            	var rowArr = $("#account-orderDatagrid-dispatchBillSourcePage").datagrid("getChecked");
		            	var uninvoiceamount = 0;
		            	var invoiceamount = 0;
		            	var totaltaxamount = 0;
						for(var i=0;i<rowArr.length;i++){
							uninvoiceamount = Number(uninvoiceamount)+Number(rowArr[i].uninvoiceamount);
							totaltaxamount = Number(totaltaxamount)+Number(rowArr[i].totaltaxamount);
							if(null!=invoiceamount){
								invoiceamount = Number(invoiceamount) + Number(rowArr[i].invoiceamount);
							}
						}
						if(rowArr.length==0){
							$("#account-orderDatagrid-dispatchBillSourcePage").datagrid("reloadFooter",[totalFooter] );
						}else{
							$("#account-orderDatagrid-dispatchBillSourcePage").datagrid("reloadFooter",[{id:'选中合计',businessdate:customerid+'余额',customerid:formatterMoney(json.camount),customername:'总店余额',headcustomername:formatterMoney(json.amount),totaltaxamount:totaltaxamount,uninvoiceamount:uninvoiceamount.toFixed(2),invoiceamount:invoiceamount.toFixed(2)},totalFooter] );
		           		}
		            }
		        });
    		}else{
    			$.ajax({   
		            url :'account/receivable/getCustomerCapital.do?id='+customerid,
		            type:'post',
		            dataType:'json',
		            success:function(json){
		            	var rowArr = $("#account-orderDatagrid-dispatchBillSourcePage").datagrid("getChecked");
		            	var uninvoiceamount = 0;
		            	var totaltaxamount = 0;
		            	var invoiceamount = 0;
						for(var i=0;i<rowArr.length;i++){
							uninvoiceamount = Number(uninvoiceamount)+Number(rowArr[i].uninvoiceamount);
							totaltaxamount = Number(totaltaxamount)+Number(rowArr[i].totaltaxamount);
							if(null!=invoiceamount){
								invoiceamount = Number(invoiceamount) + Number(rowArr[i].invoiceamount);
							}
						}
						if(rowArr.length==0){
							$("#account-orderDatagrid-dispatchBillSourcePage").datagrid("reloadFooter",[totalFooter] );
						}else{
							$("#account-orderDatagrid-dispatchBillSourcePage").datagrid("reloadFooter",[{id:'选中合计',customername:'账户余额',headcustomername:formatterMoney(json.camount),totaltaxamount:totaltaxamount,uninvoiceamount:uninvoiceamount.toFixed(2),invoiceamount:invoiceamount.toFixed(2)},totalFooter] );
		           		}
		            }
		        });
    		}
    		
    	}
    	function showSalesInvoiceDetail(){
    		var rowArr = $("#account-orderDatagrid-dispatchBillSourcePage").datagrid("getChecked");
    		var ids = null;
    		if(rowArr.length==0){
    			$.messager.alert("提醒","请选择数据");
    			return false;
    		}
			for(var i=0;i<rowArr.length;i++){
				if(ids==null){
					ids = rowArr[i].id;
				}else{
					ids +=","+ rowArr[i].id;
				}
			}
			var customername = rowArr[0].headcustomername;
			if(null==customername){
				customername = rowArr[0].customername;
			}
			var customerid = rowArr[0].headcustomerid;
			if(null==customerid){
				customerid = rowArr[0].customerid;
			}
			var brandid = $("#account-brand-salesInvoiceSourceQueryPage").widget("getValue");
			var uncheckedjson = $("#account-nochecked-detail-salesinvoiceDetailPage").val();
			loading("明细页面加载中..");
			$.ajax({
				url:'sales/showReceiptAndRejectBillDetailList.do',
				dataType:'html',
				type:'post',
				data:{id:ids,uncheckedjson:uncheckedjson,customerid:customerid,customername:customername,brandid:brandid},
				success:function(json){
					loaded();
                    $('<div id="account-panel-salesinvoiceDetailPage1"></div>').appendTo('#account-panel-salesinvoiceDetailPage');
					$("#account-panel-salesinvoiceDetailPage1").dialog({
						title:"客户:"+customerid+customername+"，商品明细列表",
					    fit:true,
						closed:false,
						modal:true,
						cache:false,
						maximizable:true,
						resizable:true,
						content:json,
					    buttons:[
					    		{
									text:'追加到申请单',
									handler:function(){
										var customerid = $("#select-customerid-receiptAndReject").val();
										showCustomerSalesInvoiceList(customerid);
									}
								},
					    		{
									text:'生成申请单',
									handler:function(){
										addSalesInvoiceByRefer();
									}
								}
                                <security:authorize url="/account/receivable/showSalesInvoiceWriteoffPage.do">
                                ,
								{
									text:'核销',
									handler:function(){
										addSalesInvoiceWithWriteOff();
									}
								}
                                </security:authorize>
						],
                        onClose:function(){
                            $("#account-panel-salesinvoiceDetailPage1").dialog('destroy');
                        }
					});
				},
				error:function(){
					loaded();
				}
			});
			
    	}
    	function getBillDetailAmount(){
    		var rows = $("#account-orderDatagrid-detailSourcePage").datagrid("getChecked");
    		var totalamount = 0;
 			for(var i=0;i<rows.length;i++){
 				totalamount = Number(totalamount) + Number(rows[i].taxamount);
 			}
 			if(rows.length>0){
 				$("#account-orderDatagrid-detailSourcePage").datagrid("reloadFooter",[{billid:'选中合计',taxamount:totalamount},totalFooter] );
    		}else{
    			$("#account-orderDatagrid-detailSourcePage").datagrid("reloadFooter",[{billid:'选中合计',taxamount:0},totalFooter] );
    		}
    	}
    	function showDetailListPage(id,rowIndex){
    		var rowArr = $("#account-orderDatagrid-dispatchBillSourcePage").datagrid("getChecked");
    		var rows = $("#account-orderDatagrid-dispatchBillSourcePage").datagrid("getRows");
			var rowData = null;
			for(var i=0;i<rows.length;i++){
				if(rows[i].id==id){
					rowData = rows[i];
					break;
				}
			}
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
			if(null!=rowData){
				var customername = rowData.headcustomername;
				if(customername==null){
					customername = rowData.customername;
				}
				var customerid = rowData.headcustomerid;
				if(customerid==null){
					customerid = rowData.customerid;
				}
				var brandid = $("#account-brand-salesInvoiceSourceQueryPage").widget("getValue");
				$("#account-panel-selectDetailPage").dialog({
					href:"sales/showReceiptOrRejectBillDetailListPage.do?id="+rowData.id+"&billtype="+rowData.billtype+"&brandid="+brandid,
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
				$("#account-orderDatagrid-dispatchBillSourcePage").datagrid("checkRow",rowIndex);
			}
    	}
  	</script>
  </body>
</html>
