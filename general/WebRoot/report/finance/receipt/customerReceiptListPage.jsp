<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>分客户单资金应收款情况表</title>
    <%@include file="/include.jsp" %>  
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>   
  </head>
  
  <body>
    	<table id="report-datagrid-customerReceipt"></table>
    	<div id="report-toolbar-customerReceipt" style="padding: 0px">
            <div class="buttonBG">
                <security:authorize url="/report/finance/customerReceiptExport.do">
                    <a href="javaScript:void(0);" id="report-buttons-customerReceiptPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
                </security:authorize>
            </div>
    		<form action="" id="report-query-form-customerReceipt" method="post">
	    		<table class="querytable">

	    			<tr>
	    				<td>业务日期:</td>
    					<td><input type="text" id="report-query-businessdate1" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" id="report-query-businessdate2" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
	    				<td>客户名称:</td>
	    				<td><input type="text" id="report-query-customerid" name="customerid"/></td>
	    			</tr>
	    			<tr>
	    				<td>总店名称:</td>
	    				<td><input type="text" id="report-query-pcustomerid" name="pcustomerid"/>
							<input type="hidden" name="groupcols" value="customerid"/>
						</td>
	    				<td></td>
                        <td>
	    					<a href="javaScript:void(0);" id="report-queay-customerReceipt" class="button-qr">查询</a>
							<a href="javaScript:void(0);" id="report-reload-customerReceipt" class="button-qr">重置</a>

	    				</td>
	    			</tr>
	    		</table>
	    	</form>
    	</div>
    	<div id="report-fundsCustomer-detail-dialog"></div>
    	<script type="text/javascript">
    		var initQueryJSON = $("#report-query-form-customerReceipt").serializeJSON();
    		$(function(){
				//全局导出
				$("#report-buttons-customerReceiptPage").click(function(){
					var queryJSON = $("#report-query-form-customerReceipt").serializeJSON();
					//获取排序规则
					var objecr  = $("#report-datagrid-customerReceipt").datagrid("options");
					if(null != objecr.sortName && null != objecr.sortOrder ){
						queryJSON["sort"] = objecr.sortName;
						queryJSON["order"] = objecr.sortOrder;
					}
					var queryParam = JSON.stringify(queryJSON);
					var url = "report/finance/exportBaseFinanceReceiptData.do";
					exportByAnalyse(queryParam,"分客户单资金应收款情况表","report-datagrid-customerReceipt",url);
				});

				var customertableColumnListJson = $("#report-datagrid-customerReceipt").createGridColumnLoad({
					frozenCol : [[
						{field:'idok',checkbox:true,isShow:true}
					]],
					commonCol : [[
						{field:'customerid',title:'客户编码',width:60},
						{field:'customername',title:'客户名称',width:210},
						{field:'pcustomerid',title:'总店名称',width:60,sortable:true,
							formatter:function(value,rowData,rowIndex){
								if(rowData.customerid!=rowData.pcustomerid){
									return rowData.pcustomername;
								}else{
									return "";
								}
							}
						},
						{field:'salesarea',title:'销售区域',sortable:true,width:80,
							formatter:function(value,rowData,rowIndex){
								return rowData.salesareaname;
							}
						},
						{field:'allunwithdrawnamount',title:'应收款总额',align:'right',resizable:true,isShow:true,sortable:true,
							formatter:function(value,rowData,rowIndex){
								if(value!=0){
									return '<a href="javascript:showDetail(\''+rowData.customerid+'\',0);" style="text-decoration:none">'+formatterMoney(value)+'</a>';
								}else{
									return formatterMoney(value);
								}
							}
						},
						{field:'unauditamount',title:'未验收应收款',align:'right',resizable:true,isShow:true,sortable:true,
							formatter:function(value,rowData,rowIndex){
								if(value!=0){
									return '<a href="javascript:showDetail(\''+rowData.customerid+'\',0);" style="text-decoration:none">'+formatterMoney(value)+'</a>';
								}else{
									return formatterMoney(value);
								}
							}
						},
						{field:'auditamount',title:'已验收应收款',align:'right',resizable:true,isShow:true,sortable:true,
							formatter:function(value,rowData,rowIndex){
								if(value!=0){
									return '<a href="javascript:showDetail(\''+rowData.customerid+'\',1);" style="text-decoration:none">'+formatterMoney(value)+'</a>';
								}else{
									return formatterMoney(value);
								}
							}
						},
						{field:'rejectamount',title:'退货应收款',align:'right',resizable:true,isShow:true,sortable:true,
							formatter:function(value,rowData,rowIndex){
								if(value!=0){
									return '<a href="javascript:showDetail(\''+rowData.customerid+'\',2);" style="text-decoration:none">'+formatterMoney(value)+'</a>';
								}else{
									return formatterMoney(value);
								}
							}
						},
						{field:'allpushbalanceamount',title:'冲差应收款',align:'right',resizable:true,isShow:true,sortable:true,
							formatter:function(value,rowData,rowIndex){
								if(value!=0){
									return '<a href="javascript:showDetail(\''+rowData.customerid+'\',0);" style="text-decoration:none">'+formatterMoney(value)+'</a>';
								}else{
									return formatterMoney(value);
								}
							}
						},
						{field:'customeramount',title:'客户余额',align:'right',resizable:true,isShow:true,
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							}
						}
					]]
				});

    			$("#report-datagrid-customerReceipt").datagrid({
					authority:customertableColumnListJson,
					frozenColumns: customertableColumnListJson.frozen,
					columns:customertableColumnListJson.common,
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		singleSelect:true,
		  	 		pageSize:100,
					toolbar:'#report-toolbar-customerReceipt'
				}).datagrid("tooltip");
				$("#report-query-customerid").widget({
					referwid:'RL_T_BASE_SALES_CUSTOMER',
		    		width:205,
					singleSelect:true
				});
				$("#report-query-pcustomerid").widget({
					referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
		    		width:225,
					singleSelect:true
				});
				
				//回车事件
				controlQueryAndResetByKey("report-queay-customerReceipt","report-reload-customerReceipt");
				
				//查询
				$("#report-queay-customerReceipt").click(function(){
					//把form表单的name序列化成JSON对象
		      		var queryJSON = $("#report-query-form-customerReceipt").serializeJSON();
		      		$("#report-datagrid-customerReceipt").datagrid({
		      			url: 'report/finance/showBaseFinanceReceiptData.do',
		      			pageNumber:1,
						queryParams:queryJSON
		      		});
				});
				//重置
				$("#report-reload-customerReceipt").click(function(){
					$("#report-query-customerid").widget("clear");
					$("#report-query-pcustomerid").widget("clear");
					$("#report-query-form-customerReceipt").form("reset");
					var queryJSON = $("#report-query-form-customerReceipt").serializeJSON();
		       		$("#report-datagrid-customerReceipt").datagrid('loadData',{total:0,rows:[]});
				});
    		});
    		
    		function showDetail(customerid,type){
    			var businessdate1 = $("#report-query-businessdate1").val();
    			var businessdate2 = $("#report-query-businessdate2").val();
    			var url = 'report/finance/showFundsCustomerListPage.do?customerid='+customerid+'&type='+type+'&businessdate1='+businessdate1+'&businessdate2='+businessdate2;
    			$("#report-fundsCustomer-detail-dialog").dialog({
				    title: '分客户资金应收明细列表',
		    		width:800,
		    		height:400,
		    		closed:false,
		    		modal:true,
		    		maximizable:true,
		    		cache:false,
		    		resizable:true,
				    href: url
				});
    		}
    	</script>
  </body>
</html>
