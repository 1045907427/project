<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>供应商应付款动态表-单据对账明细</title>
    <%@include file="/include.jsp" %>   
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>  
  </head>
  
  <body>
    	<table id="report-datagrid-supplierPaymentsBillDetailPage"></table>
    	<div id="report-toolbar-supplierPaymentsBillDetailPage" class="buttonBG">
    		<a href="javaScript:void(0);" id="report-advancedQuery-supplierPaymentsBillDetailPage" class="easyui-linkbutton" iconCls="button-commonquery" plain="true" title="[Alt+Q]查询">查询</a>
    		<security:authorize url="/report/finance/exportSupplierPaymentsBillDetailData.do">
    			<a href="javaScript:void(0);" id="report-advancedQuery-exportSupplierPaymentsBillDetailData" class="easyui-linkbutton" iconCls="button-export" plain="true" title="[Alt+E]导出">导出</a>
    		</security:authorize>
    	</div>
    	<div style="display: none">
	    	<div id="report-dialog-advancedQueryPage">
	    		<form action="" method="post" id="report-form-advancedQuery">
			  		<table cellpadding="1" cellspacing="1" border="0" style="padding: 10px;">
		    			<tr>
		    				<td>单据类型:</td>
		    				<td>
		    					<select id="report-billtype-advancedQuery" name="billtype" style="width:100px;">
		    						<option value=""></option>
		    						<option value="1">采购进货单</option>
		    						<option value="2">采购退货通知单</option>
									<option value="3">供应商应付款期初</option>
		    					</select>
							</td>
		    			</tr>
                        <tr>
                            <td>开&nbsp;&nbsp;票:</td>
                            <td>
                                <select name="invoicestate" style="width:100px;">
                                    <option value=""></option>
                                    <option value="0">未完成</option>
                                    <option value="1">完成</option>
                                </select>
                            </td>
                        </tr>
			  			<tr>
		    				<td>业务日期:</td>
		    				<td><input type="text" name="businessdate1" value="${firstDay }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>	    				
		    			</tr>
		    			<tr>
		    				<td>供 应 商:</td>
		    				<td><input id="report-supplier-advancedQuery" type="text" name="supplierid" style="width: 225px;"/></td>
		    			</tr>
		    			<tr>	    				
		    				<td>单据编号:</td>		
		    				<td><input type="text" name="id" style="width:225px;" /></td>
		    			</tr>

		    			<tr>	    				
		    				<td>制 单 人:</td>
		    				<td>
		    					<input id="report-adduserid-advancedQuery" type="text" name="adduserid" style="width: 225px;"/>
							</td>
		    			</tr>
		    			<tr>
		    				<td>小 计 列：</td>
                            <td>
                                <label class="divtext"><input type="checkbox" class="paygroupcols checkbox1" value="supplierid"/>供应商</label>
                                <label class="divtext"><input type="checkbox" class="paygroupcols checkbox1" value="buydeptid"/>采购部门</label>
                                <label class="divtext"><input type="checkbox" class="paygroupcols checkbox1" value="businessdate"/>业务日期</label>
                                <input id="report-advancedQuery-groupcols" type="hidden" name="groupcols"/>
                            </td>
		    			</tr>
			  		</table>
			  	</form>
	    	</div>
    		<div id="report-invoicelist-dialog"></div>
		</div>
		<div id="account-panel-beigndue"></div>
    	<script type="text/javascript">
    		var RD_footerobject = null;
    		$(function(){
    			var tableColumnListJson = $("#report-datagrid-supplierPaymentsBillDetailPage").createGridColumnLoad({
					frozenCol : [[
				    			]],
					commonCol : [[
								  {field:'billtype',title:'单据类型',sortable:true,width:120,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.billtypename;
						        	}
								  },
								  {field:'businessdate',title:'业务日期',sortable:true,width:80},
								  {field:'id',title:'单据编号',sortable:true,width:125,
									 formatter:function(value,rowData,rowIndex){
										 if(value!="" && value!=null){
											 if(rowData.billtype=='1'){
												 return '<a href="javascript:showArrivalOrder(\''+value+'\')">'+value+'</a>';
											 }else if(rowData.billtype=='2'){
												 return '<a href="javascript:showReturnOrder(\''+value+'\')">'+value+'</a>';
											 }else if(rowData.billtype=='3'){
												 return '<a href="javascript:showBeginDue(\''+value+'\')">'+value+'</a>';
											 }else{
												 return value;
											 }
										 }
								  	 }
								  },
								  {field:'buyorderid',title:'订单编号',sortable:true,width:125,
										 formatter:function(value,rowData,rowIndex){
										 if(value!="" && value!=null){
											 return '<a href="javascript:showBuyOrder(\''+value+'\')">'+value+'</a>';
										 }
								  	 }
								  },
								  {field:'supplierid',title:'供应商编码',sortable:true,width:70},
								  {field:'suppliername',title:'供应商名称',sortable:true,width:250,isShow:true},
								  {field:'buydeptid',title:'采购部门',sortable:true,width:70,
									  formatter:function(value,rowData,rowIndex){
										if(rowData.buydeptname){
											return rowData.buydeptname;
										}
							          }
								  },
								  {field:'buyamount',title:'采购金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
									  if(value!=0){
						        			return formatterMoney(value);
									  	}else{
										  	var num=Number(value);
										  	return num.toFixed(2);
									  	}
						        	}
								  },
								  {field:'invoiceamount',title:'开票金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
									  	if(value!=0){
										  	if(rowData.id!=null && rowData.id !="" && rowData.id !="小计"){
								  				return '<a href="javascript:showPurchaseInvoice(\''+rowData.id+'\')">'+formatterMoney(value)+'</a>';
										  	}else{
										  		return formatterMoney(value);
										  	}
									  	}else{
										  	var num=Number(value);
										  	return num.toFixed(2);
									  	}
						        	}
								  },
								  {field:'uninvoiceamount',title:'未开票金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
									  	if(value!=0){
						        			return formatterMoney(value);
									  	}else{
										  	var num=Number(value);
										  	return num.toFixed(2);
									  	}
						        	}
								  },
								  {field:'invoicedate',title:'开票日期',sortable:true,width:80},
								  {field:'payamount',title:'已核销金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
									  	if(value!=0){
						        			return formatterMoney(value);
									  	}else{
										  	var num=Number(value);
										  	return num.toFixed(2);
									  	}
						        	}
								  },
								  {field:'unpayamount',title:'未核销金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
									  	if(value!=0){
						        			return formatterMoney(value);
									  	}else{
										  	var num=Number(value);
										  	return num.toFixed(2);
									  	}
						        	}
								  }
					             ]]
				});
    			$("#report-datagrid-supplierPaymentsBillDetailPage").datagrid({ 
			 		authority:tableColumnListJson,
			 		frozenColumns: tableColumnListJson.frozen,
					columns:tableColumnListJson.common,
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		pageSize:100,
		  	 		idField:'id',
		  	 		singleSelect:false,
			 		checkOnSelect:true,
			 		selectOnCheck:true,
					toolbar:'#report-toolbar-supplierPaymentsBillDetailPage',
					onCheckAll:function(){
						countTotalAmount();
					},
					onUncheckAll:function(){
						countTotalAmount();
					},
					onCheck:function(){
						countTotalAmount();
					},
					onUncheck:function(){
						countTotalAmount();
					},
					onLoadSuccess: function(){
						var footerrows = $(this).datagrid('getFooterRows');
						if(null!=footerrows && footerrows.length>0){
							RD_footerobject = footerrows[0];
						}
					}
				}).datagrid("columnMoving");
				
    			$("#report-supplier-advancedQuery").widget({
					referwid:'RL_T_BASE_BUY_SUPPLIER',
		    		width:225,
					onlyLeafCheck:false,
					singleSelect:false
				});

        		$("#report-adduserid-advancedQuery").widget({ //制单人参照窗口
        			referwid:'RT_T_SYS_USER',
        			singleSelect:true,
        			width:225,
        			onlyLeafCheck:true
        		});
				//回车事件
				controlQueryAndResetByKey("report-advancedQuery-supplierPaymentsBillDetailPage","");

				
				$("#report-buttons-supplierPaymentsBillDetailPagePage").Excel('export',{
					queryForm: "#report-form-advancedQuery", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:'单据对账明细报表',
			 		url:'report/finance/exportSupplierPaymentsBillDetailData.do'
				});
				
				//高级查询
				$("#report-advancedQuery-supplierPaymentsBillDetailPage").click(function(){
					$("#report-dialog-advancedQueryPage").dialog({
						maximizable:true,
						resizable:true,
						title: '单据对账明细查询',
					    width: 450,
					    height: 300,
					    closed: false,
					    cache: false,
					    modal: true,
					     buttons:[
							{
								text:'确定',
								handler:function(){
									searchAdvancedQueryForm();
								}
							},
							{
								text:'重置',
								handler:function(){
									$("#report-form-advancedQuery").form("reset");
									$("#report-advancedQuery-groupcols").val("");
							  		$("#report-supplier-advancedQuery").widget('clear');
							  		$("#report-adduserid-advancedQuery").widget('clear');

									$("#report-datagrid-supplierPaymentsBillDetailPage").datagrid('loadData',{total:0,rows:[],sortName:'',sortOrder:''});
								}
							}
							],
						onClose:function(){
						}
					});
				});
				$(".costsgroupcols").click(function(){
					var cols = "";
					$(".costsgroupcols").each(function(){
						if($(this).attr("checked")){
							if(cols==""){
								cols = $(this).val();
							}else{
								cols += ","+$(this).val();
							}
							$("#deptDailyCost-query-groupcols").val(cols);
						}
					});
				});

				$(".paygroupcols").click(function(){
					var cols = "";
					$("#report-advancedQuery-groupcols").val("");
					$(".paygroupcols").each(function(){
						if($(this).attr("checked")){
							if(cols==""){
								cols = $(this).val();
							}else{
								cols += ","+$(this).val();
							}
							$("#report-advancedQuery-groupcols").val(cols);
						}
					});
				});
    		});

    		function searchAdvancedQueryForm(){
				var gcols=$("#report-advancedQuery-groupcols").val();
      			var adQueryJSON = $("#report-form-advancedQuery").serializeJSON();
      			$("#report-datagrid-supplierPaymentsBillDetailPage").datagrid({
          			url:'report/finance/showSupplierPaymentsBillDetailData.do',
     				queryParams:adQueryJSON,
          			pageNumber:1,
          			sortName:'',
          			sortOrder:''
          		}).datagrid("columnMoving");
          		$("#report-advancedQuery-exportSupplierPaymentsBillDetailData").Excel('export',{
    				queryForm: "#report-form-advancedQuery",
    		 		type:'exportUserdefined',
    		 		name:'单据对账明细报表',
    		 		url:'report/finance/exportSupplierPaymentsBillDetailData.do'
    			});
          		$("#report-dialog-advancedQueryPage").dialog('close');
      		}
      		function showArrivalOrder(id){
    			top.addOrUpdateTab('purchase/arrivalorder/arrivalOrderPage.do?type=view&id='+ id, "采购进货单查看");          		
      		}
      		function showReturnOrder(id){
    			top.addOrUpdateTab('purchase/returnorder/returnOrderPage.do?type=view&id='+ id, "采购退货通知单查看");  
      		}
			function showBeginDue(id){
				$('#account-panel-beigndue').dialog({
					title: '应付款期初查看',
					width: 650,
					height: 310,
					collapsible:false,
					minimizable:false,
					maximizable:true,
					resizable:true,
					closed: true,
					cache: false,
					href: 'account/begindue/showBeginDueViewPage.do?id='+id,
					modal: true
				});
				$('#account-panel-beigndue').dialog("open");
			}
      		function showBuyOrder(id){
    			top.addOrUpdateTab('purchase/buyorder/buyOrderPage.do?type=view&id='+ id, "采购订单查看");  
      		}
    		function showPurchaseInvoice(id){
    			$("#report-invoicelist-dialog").dialog({  
				    title: '发票列表',  
				    width: 680,  
				    height: 300,  
				    collapsible:false,
				    minimizable:false,
				    maximizable:true,
				    resizable:true,
				    closed: false,  
				    cache: false,  
				    href: 'account/payable/showPurchaseInvoiceListPageBySourceid.do?sourceid='+id,  
				    modal: true
				});
    		}
    		
    		//计算勾选应收款单据动态表合计
	    	function countTotalAmount(){
	    		var rows =  $("#report-datagrid-supplierPaymentsBillDetailPage").datagrid('getChecked');
	    		var buyamount = 0,invoiceamount = 0,uninvoiceamount = 0,payamount = 0,unpayamount = 0;
	    		for(var i=0;i<rows.length;i++){
	    			buyamount = Number(buyamount)+Number(rows[i].buyamount == undefined ? 0 : rows[i].buyamount);
	    			invoiceamount = Number(invoiceamount)+Number(rows[i].invoiceamount == undefined ? 0 : rows[i].invoiceamount);
	    			uninvoiceamount = Number(uninvoiceamount)+Number(rows[i].uninvoiceamount == undefined ? 0 : rows[i].uninvoiceamount);
	    			payamount = Number(payamount)+Number(rows[i].payamount == undefined ? 0 : rows[i].payamount);
	    			unpayamount = Number(unpayamount)+Number(rows[i].unpayamount == undefined ? 0 : rows[i].unpayamount);
	    		}
	    		if(null!=rows && rows.length>0){
	    			var footerrows = [
	    				{
		    				suppliername:'选中金额',
		    				buyamount:buyamount,
		    				invoiceamount:invoiceamount,
		    				uninvoiceamount:uninvoiceamount,
		    				payamount:payamount,
		    				unpayamount:unpayamount
	    				},
	    				RD_footerobject
	    			];
	    			$("#report-datagrid-supplierPaymentsBillDetailPage").datagrid("reloadFooter",footerrows);
	    		}else{
	    			if(null!=RD_footerobject){
	    				$("#report-datagrid-supplierPaymentsBillDetailPage").datagrid("reloadFooter",[RD_footerobject]);
	    			}
	    		}
	    	}
    	</script>
  </body>
</html>
