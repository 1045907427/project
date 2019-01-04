<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>按客户业务员分客户统计页面</title>
    <%@include file="/include.jsp" %> 
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>    
  </head>
  
  <body>
  	<form action="" id="report-query-form-salesuserReceivablePastDue" method="post">
		<input type="hidden" name="salesuser" value="${salesuser}"/>
		<input type="hidden" name="ispastdue" value="${ispastdue}"/>
	</form>
  	<div id="report-tab-salesuserReceivablePastDue" class="buttonBG">
		<security:authorize url="/report/finance/salesUserReceivablePastDueExport.do">
			<a href="javaScript:void(0);" id="report-export-salesuserReceivablePastDue" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
		</security:authorize>
	</div>
	<div id="tt" class="easyui-tabs" data-options="fit:true">
	    <div title="分客户统计" style="padding:2px;">
	       <table id="report-datagrid-salesuserReceivablePastDue-customer"></table>
	    </div>
	</div>
	<div id="report-fundsCustomer1-detail-dialog"></div>
	<div id="report-salesuserReceivablePastDue-salesflow-dialog"></div>
    	<script type="text/javascript">
    		$('#tt').tabs({
				tools:'#report-tab-salesuserReceivablePastDue'
			});
    		var initQueryJSON = $("#report-query-form-salesuserReceivablePastDue").serializeJSON();
    		$(function(){
    			var customertableColumnListJson = $("#report-datagrid-salesuserReceivablePastDue-customer").createGridColumnLoad({
					frozenCol : [[
					]],
					commonCol : [[
						{field:'customerid',title:'客户编码',width:80},
						{field:'customername',title:'客户名称',width:210},
						{field:'settletypename',title:'结算方式',width:60},
						{field:'settleday',title:'结算日',width:50},
						{field:'payeeid',title:'收款人',width:60,
							formatter:function(value,rowData,rowIndex){
								return rowData.payeename;
							}
						},
						{field:'saleamount',title:'应收款',align:'right',width:80,sortable:true,
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							}
						},
						{field:'unpassamount',title:'正常期金额',align:'right',width:80,sortable:true,
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							}
						},
						{field:'totalpassamount',title:'超账期金额',align:'right',width:80,sortable:true,
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							},
							styler:function(value,rowData,rowIndex){
								return 'color:blue';
							}
						},
						<c:forEach items="${list }" var="list">
						{field:'passamount${list.seq}',title:'${list.detail}',align:'right',width:80,sortable:true,
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							}
						},
						</c:forEach>
						{field:'returnamount',title:'退货金额',align:'right',width:80,sortable:true,hidden:true,
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							}
						},
						{field:'returnrate',title:'退货率',align:'right',width:80,sortable:true,hidden:true,
							formatter:function(value,rowData,rowIndex){
								if(value!=null && value!=""){
									return formatterMoney(value)+"%";
								}
							}
						},
						{field:'pushamount',title:'冲差金额',align:'right',width:80,sortable:true,hidden:true,
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							}
						}
					]]
				});
    			
    			$("#report-datagrid-salesuserReceivablePastDue-customer").datagrid({
					authority:customertableColumnListJson,
			 		frozenColumns: customertableColumnListJson.frozen,
					columns:customertableColumnListJson.common,
			 		method:'post',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		singleSelect:true,
					url: 'report/finance/showBaseReceivablePassDueListData.do?groupcols=customerid',
					queryParams:initQueryJSON,
					onDblClickRow:function(rowIndex, rowData){
						var customerid = rowData.customerid;
						var customername = rowData.customername;
    					var urlDetail = 'report/finance/showSalesuserRPDSalesflowPage.do?customerid='+rowData.customerid+'&customername='+customername;
						$('<div id="report-salesuserReceivablePastDue-salesflow-dialog1"></div>').appendTo('#report-salesuserReceivablePastDue-salesflow-dialog');
						$("#report-salesuserReceivablePastDue-salesflow-dialog1").dialog({
							title:'按客户业务员:[${salesusername}],分客户:['+rowData.customername+']销售流水明细',
				    		width:800,
				    		height:400,
				    		closed:true,
				    		modal:true,
				    		maximizable:true,
				    		cache:false,
				    		resizable:true,
				    		maximized:true,
						    href: urlDetail,
						    onClose:function(){
						    	$('#report-salesuserReceivablePastDue-salesflow-dialog1').dialog("destroy");
						    }
						});
						$("#report-salesuserReceivablePastDue-salesflow-dialog1").dialog("open");
					},
				}).datagrid("columnMoving");
				
				$("#report-export-salesuserReceivablePastDue").Excel('export',{
					queryForm: "#report-query-form-salesuserReceivablePastDue", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:'按客户业务员：[${salesusername}]统计表',
			 		url:'report/finance/exportReceivablePastDueDetailData.do?groupcols=customerid;'
				});
    		});
    	</script>
  </body>
</html>
