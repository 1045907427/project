<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>客户应收款动态表</title>
    <%@include file="/include.jsp" %> 
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>    
  </head>
  
  <body>
    	<table id="report-datagrid-receivableDynamic"></table>
    	<div id="report-toolbar-receivableDynamic-toolbar" class="buttonBG" style="padding: 0px">
    		<a href="javaScript:void(0);" id="report-button-queryreceivableDynamic" class="easyui-linkbutton" iconCls="button-commonquery" plain="true" title="查询">查询</a>
    		<security:authorize url="/report/finance/receivableDynamicExport.do">
				<a href="javaScript:void(0);" id="report-buttons-receivableDynamicPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
			</security:authorize>
    	</div>
    	<div style="display: none">
    	<div id="report-toolbar-receivableDynamic">
    		<form action="" id="report-query-form-receivableDynamic" method="post">
    		<table>
    			<tr>
    				<td>客户名称:</td>
    				<td><input type="text" id="report-query-customerid" name="customerid"/></td>
                    <td>销售区域:</td>
                    <td>
                        <input type="text" id="report-query-salesarea" name="salesarea"/>
                    </td>
                </tr>
                <tr>
    				<td>总店名称:</td>
    				<td><input type="text" id="report-query-pcustomerid" name="pcustomerid"/></td>
                    <td>是否核销:</td>
                    <td>
                        <select name="iswrite" style="width: 130px;">
                            <option></option>
                            <option value="1">是</option>
                            <option value="0">否</option>
                        </select>
                    </td>
    			</tr>
    			<tr>
    				<td>订单日期:</td>
    				<td><input type="text" id="report-query-orderdate1" name="orderdate1" value="" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" id="report-query-orderdate2" name="orderdate2" value=""  class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
                    <td>是否验收:</td>
                    <td>
                        <select name="ischeck" style="width: 130px;">
                            <option></option>
                            <option value="1">是</option>
                            <option value="0">否</option>
                        </select>
                    </td>
                    </tr>
                <tr>
                    <td>出入库日期:</td>
    				<td><input type="text" id="report-query-businessdate1" name="businessdate1" value="" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" id="report-query-businessdate2" name="businessdate2" value=""  class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
                    <td>是否开票:</td>
                    <td>
                        <select name="isinvoicebill" style="width: 130px;">
                            <option></option>
                            <option value="1">是</option>
                            <option value="0">否</option>
                        </select>
                    </td>
                </tr>
    			<tr>
    				<td>验收日期:</td>
    				<td><input type="text" id="report-query-checkdate1" name="checkdate1" value="" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" id="report-query-checkdate2" name="checkdate2" value=""  class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
                    <td>订单编号:</td>
                    <td>
                        <input type="text" style="width: 130px;" name="saleorderid"/>
                    </td>
                </tr>
                <tr>
                    <td>开票日期:</td>
    				<td><input type="text" id="report-query-invoicedate1" name="invoicedate1" value="" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" id="report-query-invoicedate2" name="invoicedate2" value=""  class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
                    <td>发货单编号:</td>
                    <td>
                        <input type="text" style="width: 130px;" name="id"/>
                    </td>
                </tr>
    			<tr>
    				<td>核销日期:</td>
    				<td><input type="text" id="report-query-writeoffdate1" name="writeoffdate1" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" id="report-query-writeoffdate2" name="writeoffdate2" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
                    <td>回单编号:</td>
                    <td>
                        <input type="text" style="width: 130px;" name="receiptid"/>
                    </td>
                </tr>
				<tr>
					<td>应收日期:</td>
					<td><input type="text" id="report-query-duefromdate1" name="duefromdate1" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" id="report-query-duefromdate2" name="duefromdate2" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
					<td>发票号:</td>
					<td>
						<input type="text" style="width: 130px;" name="invoiceid"/>
					</td>
				</tr>
                <tr>
                    <td>单据类型:</td>
                    <td>
                        <select id="report-query-billtype" name="billtype" style="width: 225px;" class="easyui-combobox" data-options="multiple:true,onLoadSuccess:function(){$(this).combobox('clear');}">
                            <option value="1">发货单</option>
                            <option value="2">直退退货单</option>
                            <option value="3">售后退货单</option>
                            <option value="4">正常冲差单</option>
                            <option value="5">发票冲差单</option>
                            <option value="6">回单冲差单</option>
							<option value="7">应收款期初</option>
                        </select>
                    </td>
					<td>统计方式:</td>
					<td>
						<select name="reporttype" style="width: 130px;">
							<option value="1">销售订单</option>
							<option value="2">发货单</option>
						</select>
					</td>
    			<tr>
    				<td>客户业务员:</td>
    				<td>
    					<input type="text" id="report-query-salesuser" name="salesuser"/>
    				</td>
                    <td>客户单号:</td>
                    <td>
                        <input type="text" id="report-query-customerSourceid" name="sourceid"  style="width: 130px;" />
                    </td>
    			</tr>

    		</table>
    	</form>
    	</div>
    	</div>
    	<div id="report-invoicelist-dialog"></div>
    	<div id="account-panel-customerPushBanlance"></div>
		<div id="account-panel-beginamount"></div>
    	<script type="text/javascript">
            var receivableDynamic_AjaxConn = function (Data, Action) {
                var MyAjax = $.ajax({
                    type: 'post',
                    cache: false,
                    url: Action,
                    data: Data,
                    async: false
                })
                return MyAjax.responseText;
            }

    		var initQueryJSON = $("#report-query-form-receivableDynamic").serializeJSON();
    		var RD_footerobject = null;
    		$(function(){
    			var tableColumnListJson = $("#report-datagrid-receivableDynamic").createGridColumnLoad({
					frozenCol : [[
					]],
					commonCol : [[
						{field:'billtype',title:'单据类型',width:80,sortable:true,
							formatter:function(value,rowData,rowIndex){
								return rowData.billtypename;
							}
						},
						{field:'id',title:'单据编号',width:130,sortable:true,
							formatter:function(value,rowData,rowIndex){
								if(value!=null && value!=""){
									if(rowData.billtype=='1'){
										var ids = value.split(",");
										var str = "";
										for(var i=0;i<ids.length;i++){
											str +='<a href="javascript:showSaleout(\''+ids[i]+'\')">'+ids[i]+'</a> ';
										}
										return str;
									}else if(rowData.billtype=='2'){
										return '<a href="javascript:showSaleRejectEnter(\''+value+'\')">'+value+'</a>';
									}else if(rowData.billtype=='3'){
										return '<a href="javascript:showSaleRejectEnter(\''+value+'\')">'+value+'</a>';
									}else if(rowData.billtype=='4'){
										return '<a href="javascript:showCustomerPush(\''+value+'\')">'+value+'</a>';
									}else if(rowData.billtype=='5'){
										return '<a href="javascript:showCustomerPush(\''+value+'\')">'+value+'</a>';
									}else if(rowData.billtype=='6'){
										return '<a href="javascript:showCustomerPush(\''+value+'\')">'+value+'</a>';
									}else if(rowData.billtype=='7'){
										return '<a href="javascript:showCustomerBeginAmount(\''+value+'\')">'+value+'</a>';
									}
								}else{
									return "";
								}
							}
						},
						{field:'duefromdate',title:'应收日期',width:80,sortable:true},
						{field:'orderdate',title:'订单日期',width:80,sortable:true},
						{field:'saleorderid',title:'订单编号',width:110,sortable:true,
							formatter:function(value,rowData,rowIndex){
								if(value!=null && value!=""){
									return '<a href="javascript:showSaleorder(\''+value+'\')">'+value+'</a>';
								}else{
									return "";
								}
							}
						},
                        {field:'sourceid',title:'客户单号',width:110,sortable:true},
						{field:'customerid',title:'客户编码',width:60,sortable:true},
						{field:'customername',title:'客户简称',width:130,sortable:true,isShow:true},
						{field:'salesarea',title:'销售区域',width:75,sortable:true,
							formatter:function(value,rowData,rowIndex){
								if(rowData.customerid!=rowData.pcustomerid){
									return rowData.salesareaname;
								}else{
									return "";
								}
							}
						},
						{field:'salesuser',title:'客户业务员',width:75,sortable:true,
							formatter:function(value,rowData,rowIndex){
								if(rowData.customerid!=rowData.pcustomerid){
									return rowData.salesusername;
								}else{
									return "";
								}
							}
						},
						{field:'pcustomerid',title:'总店名称',width:130,sortable:true,hidden:true,
							formatter:function(value,rowData,rowIndex){
								if(rowData.customerid!=rowData.pcustomerid){
									return rowData.pcustomername;
								}else{
									return "";
								}
							}
						},
						{field:'initsendamount',title:'发货金额',resizable:true,sortable:true,align:'right',
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							}
						},
						<c:if test="${map.initsendcostamount == 'initsendcostamount'}">
						{field:'initsendcostamount',title:'成本金额',resizable:true,sortable:true,align:'right',
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							}
						},
						</c:if>
						{field:'businessdate',title:'出入库日期',width:80,sortable:true},
						{field:'sendamount',title:'发货出库金额',resizable:true,sortable:true,align:'right',
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							}
						},
						{field:'receiptid',title:'回单编号',width:130,sortable:true,
							formatter:function(value,rowData,rowIndex){
								if(value!=null && value!=""){
									return '<a href="javascript:showReceipt(\''+value+'\')">'+value+'</a>';
								}else{
									return "";
								}
							}
						},
						{field:'receiptremark',title:'回单备注',resizable:true},
						{field:'checkamount',title:'验收金额',resizable:true,sortable:true,align:'right',
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							}
						},
						{field:'checkdate',title:'验收日期',width:80},
						{field:'invoicebillamount',title:'开票金额',resizable:true,sortable:true,align:'right',
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							}
						},
						{field:'uninvoicebillamount',title:'未开票金额',resizable:true,sortable:true,align:'right',
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							}
						},
						{field:'invoicebillnotaxamount',title:'开票未税金额',resizable:true,sortable:true,align:'right',hidden:true,
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							}
						},
						{field:'uninvoicebillnotaxamount',title:'未开票未税金额',resizable:true,sortable:true,align:'right',hidden:true,
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							}
						},
						{field:'invoiceid',title:'发票号',width:130,sortable:true,
							formatter:function(value,rowData,rowIndex){
								if(value!=null && value!=""){
									return '<a href="javascript:showSaleInvoice(\''+value+'\')">'+value+'</a>';
								}else{
									return "";
								}
							}
						},
						{field:'invoicebilldate',title:'开票日期',width:80,sortable:true},
						{field:'writeoffamount',title:'核销金额',resizable:true,align:'right',sortable:true,
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							}
						},
						{field:'unwriteoffamount',title:'未核销金额',align:'right',resizable:true,isShow:true,sortable:true,
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							}
						},
						{field:'writeoffnotaxamount',title:'核销未税金额',resizable:true,align:'right',sortable:true,hidden:true,
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							}
						},
						{field:'unwriteoffnotaxamount',title:'未核销未税金额',align:'right',resizable:true,isShow:true,sortable:true,hidden:true,
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							}
						},
						{field:'writeoffdate',title:'最近核销日期',width:80,sortable:true},
						{field:'audittime',title:'单据审核时间',width:100,sortable:true}
					]]
				});
    			$("#report-datagrid-receivableDynamic").datagrid({ 
					authority:tableColumnListJson,
			 		frozenColumns: tableColumnListJson.frozen,
					columns:tableColumnListJson.common,
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		idField:'id',
		  	 		singleSelect:false,
			 		checkOnSelect:true,
			 		selectOnCheck:true,
		  	 		pageSize:100,
					sortName:'businessdate',
					sortOrder:'asc',
					toolbar:'#report-toolbar-receivableDynamic-toolbar',
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
				$("#report-query-customerid").widget({
					referwid:'RL_T_BASE_SALES_CUSTOMER',
		    		width:225,
					singleSelect:true
				});
				$("#report-query-pcustomerid").widget({
					referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
		    		width:225,
                    allSelect:true,
					singleSelect:true
				});
				$("#report-query-salesarea").widget({
					referwid:'RT_T_BASE_SALES_AREA',
		    		width:130,
		    		onlyLeafCheck:false,
                    onlyParentCheck:false,
					singleSelect:true
				});
				$("#report-query-salesuser").widget({
					referwid:'RL_T_BASE_PERSONNEL_CLIENTSELLER',
		    		width:225,
					singleSelect:true
				});
				//回车事件
				controlQueryAndResetByKey("report-queay-receivableDynamic","report-reload-receivableDynamic");
				
				//查询
				$("#report-queay-receivableDynamic").click(function(){
					//把form表单的name序列化成JSON对象
		      		var queryJSON = $("#report-query-form-receivableDynamic").serializeJSON();
		      		$("#report-datagrid-receivableDynamic").datagrid({
		      			url: 'report/finance/showCustomerReceivableDynamicData.do',
						queryParams:queryJSON,
		      			pageNumber:1
		      		}).datagrid("columnMoving");
				});
				//重置
				$("#report-reload-receivableDynamic").click(function(){
					$("#report-query-customerid").widget("clear");
					$("#report-query-pcustomerid").widget("clear");
					$("#report-query-form-receivableDynamic").form("reset");
					var queryJSON = $("#report-query-form-receivableDynamic").serializeJSON();
		      		$("#report-datagrid-receivableDynamic").datagrid('loadData',{total:0,rows:[]});
				});
				
				$("#report-buttons-receivableDynamicPage").Excel('export',{
					queryForm: "#report-query-form-receivableDynamic", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:'客户应收款动态表',
			 		url:'report/finance/exportReceivableDynamicData.do'
				});
				
				$("#report-button-queryreceivableDynamic").click(function(){
					$("#report-toolbar-receivableDynamic").dialog({
						maximizable:true,
						resizable:true,
						title: '查询',
					    width: 600,
					    height: 400,
					    closed: false,
					    cache: false,
					    modal: true,
					     buttons:[
							{
								text:'确定',
								handler:function(){
									var queryJSON = $("#report-query-form-receivableDynamic").serializeJSON();
						      		$("#report-datagrid-receivableDynamic").datagrid({
						      			url: 'report/finance/showCustomerReceivableDynamicData.do',
										queryParams:queryJSON,
						      			pageNumber:1
						      		}).datagrid("columnMoving");
						      		$("#report-toolbar-receivableDynamic").dialog("close");
								}
							},
							{
								text:'重置',
								handler:function(){
									$("#report-query-customerid").widget("clear");
									$("#report-query-pcustomerid").widget("clear");
                                    $("#report-query-salesarea").widget("clear");
                                    $("#report-query-salesuser").widget("clear");
									$("#report-query-form-receivableDynamic").form("reset");
									$("#report-query-billtype").combobox('clear');
									var queryJSON = $("#report-query-form-receivableDynamic").serializeJSON();
						      		$("#report-datagrid-receivableDynamic").datagrid('loadData',{total:0,rows:[]});
								}
							}
							],
						onClose:function(){
						}
					});
				});
    		});
    		
    		//计算勾选应收款单据动态表合计
	    	function countTotalAmount(){
	    		var rows =  $("#report-datagrid-receivableDynamic").datagrid('getChecked');
	    		var initsendamount = 0,initsendcostamount = 0,sendamount = 0,checkamount = 0,invoiceamount = 0,uninvoiceamount = 0;
	    		var invoicenotaxamount = 0,uninvoicenotaxamount= 0,writeoffamount = 0,unwriteoffamount = 0,writeoffnotaxamount = 0,unwriteoffnotaxamount = 0;
	    		for(var i=0;i<rows.length;i++){
	    			initsendamount = Number(initsendamount)+Number(rows[i].initsendamount == undefined ? 0 : rows[i].initsendamount);
	    			initsendcostamount = Number(initsendcostamount)+Number(rows[i].initsendcostamount == undefined ? 0 : rows[i].initsendcostamount);
	    			sendamount = Number(sendamount)+Number(rows[i].sendamount == undefined ? 0 : rows[i].sendamount);
	    			checkamount = Number(checkamount)+Number(rows[i].checkamount == undefined ? 0 : rows[i].checkamount);
	    			invoiceamount = Number(invoiceamount)+Number(rows[i].invoiceamount == undefined ? 0 : rows[i].invoiceamount);
	    			uninvoiceamount = Number(uninvoiceamount)+Number(rows[i].uninvoiceamount == undefined ? 0 : rows[i].uninvoiceamount);
	    			invoicenotaxamount = Number(invoicenotaxamount)+Number(rows[i].invoicenotaxamount == undefined ? 0 : rows[i].invoicenotaxamount);
	    			uninvoicenotaxamount = Number(uninvoicenotaxamount)+Number(rows[i].uninvoicenotaxamount == undefined ? 0 : rows[i].uninvoicenotaxamount);
	    			writeoffamount = Number(writeoffamount)+Number(rows[i].writeoffamount == undefined ? 0 : rows[i].writeoffamount);
	    			unwriteoffamount = Number(unwriteoffamount)+Number(rows[i].unwriteoffamount == undefined ? 0 : rows[i].unwriteoffamount);
	    			writeoffnotaxamount = Number(writeoffnotaxamount)+Number(rows[i].writeoffnotaxamount == undefined ? 0 : rows[i].writeoffnotaxamount);
	    			unwriteoffnotaxamount = Number(unwriteoffnotaxamount)+Number(rows[i].unwriteoffnotaxamount == undefined ? 0 : rows[i].unwriteoffnotaxamount);
	    		}
	    		if(null!=rows && rows.length>0){
	    			var footerrows = [
	    				{
		    				customername:'选中金额',
		    				initsendamount:initsendamount,
		    				initsendcostamount:initsendcostamount,
		    				sendamount:sendamount,
		    				checkamount:checkamount,
		    				invoiceamount:invoiceamount,
		    				uninvoiceamount:uninvoiceamount,
		    				invoicenotaxamount:invoicenotaxamount,
		    				uninvoicenotaxamount:uninvoicenotaxamount,
		    				writeoffamount:writeoffamount,
		    				unwriteoffamount:unwriteoffamount,
		    				writeoffnotaxamount:writeoffnotaxamount,
		    				unwriteoffnotaxamount:unwriteoffnotaxamount
	    				},
	    				RD_footerobject
	    			];
	    			$("#report-datagrid-receivableDynamic").datagrid("reloadFooter",footerrows);
	    		}else{
	    			if(null!=RD_footerobject){
	    				$("#report-datagrid-receivableDynamic").datagrid("reloadFooter",[RD_footerobject]);
	    			}
	    		}
	    	}
    		
    		function showSaleout(id){
    			top.addOrUpdateTab('storage/showSaleOutEditPage.do?id='+ id, "发货单查看");
    		}
    		function showSaleorder(id){
                var ret = receivableDynamic_AjaxConn({id:id},'sales/checkSourceBillType.do');
                var retjson = $.parseJSON(ret);
                if("1" == retjson.billtype){
                    top.addOrUpdateTab('sales/orderPage.do?type=edit&id='+id, '销售订单查看');
                }else if("2" == retjson.billtype){
                    top.addOrUpdateTab('sales/rejectBill.do?type=edit&id='+id, '退货通知单查看');
                }else if("3" == retjson.billtype){
                    top.addOrUpdateTab('sales/receiptPage.do?type=edit&id='+id, '销售发货回单查看');
                }

    		}
    		function showReceipt(id){
    			top.addTab('sales/receiptPage.do?type=edit&id='+id, '销售发货回单查看');
    		}
    		function showSaleInvoice(id){
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
				    href: 'account/receivable/showSalesInvoiceBillListPageByIds.do?id='+id,
				    modal: true
				});
    		}
    		function showSaleRejectEnter(id){
    			top.addOrUpdateTab('storage/showSaleRejectEnterEditPage.do?id='+ id, "销售退货入库单查看");
    		}
    		function showCustomerPush(id){
    			$('#account-panel-customerPushBanlance').dialog({  
				    title: '客户应收款冲差查看',  
				    width: 400,  
				    height: 350,  
				    closed: false,  
				    cache: false,  
				    href: 'account/receivable/showCustomerPushBanlanceViewPage.do?id='+id,  
				    modal: true
				});
    		}
			function showCustomerBeginAmount(id){
				$('#account-panel-beginamount').dialog({
					title: '应收款期初查看',
					width: 650,
					height: 310,
					collapsible:false,
					minimizable:false,
					maximizable:true,
					resizable:true,
					closed: true,
					cache: false,
					href: 'account/beginamount/beignAmountViewPage.do?id='+id,
					modal: true
				});
				$('#account-panel-beginamount').dialog("open");
			}
    	</script>
  </body>
</html>
