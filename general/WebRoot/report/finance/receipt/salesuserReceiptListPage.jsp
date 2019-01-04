<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>分客户业务员资金应收款情况表</title>
    <%@include file="/include.jsp" %>   
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>  
  </head>
  
  <body>
    	<table id="report-datagrid-salesuserReceipt"></table>
    	<div id="report-toolbar-salesuserReceipt" style="padding: 0px">
            <div class="buttonBG">
                <security:authorize url="/report/finance/salesuserReceiptExport.do">
                    <a href="javaScript:void(0);" id="report-buttons-salesuserReceiptPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
                </security:authorize>
            </div>
    		<form action="" id="report-query-form-salesuserReceipt" method="post">
    		<table>

    			<tr>
    				<td>业务日期:</td>
    				<td><input type="text" id="report-query-businessdate1" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" id="report-query-businessdate2" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
    				<td>客户业务员:</td>
    				<td><input type="text" id="report-query-salesuser" name="salesuser"/>
						<input type="hidden" name="groupcols" value="salesuser"/>
					</td>
    				<td colspan="2">
    					<a href="javaScript:void(0);" id="report-queay-salesuserReceipt" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="report-reload-salesuserReceipt" class="button-qr">重置</a>
    				</td>
    			</tr>
    		</table>
    	</form>
    	</div>
    	<div id="report-fundsCustomer-detail-dialog"></div>
    	<script type="text/javascript">
    		var initQueryJSON = $("#report-query-form-salesuserReceipt").serializeJSON();
    		$(function(){
				//全局导出
				$("#report-buttons-salesuserReceiptPage").click(function(){
					var queryJSON = $("#report-query-form-salesuserReceipt").serializeJSON();
					//获取排序规则
					var objecr  = $("#report-datagrid-salesuserReceipt").datagrid("options");
					if(null != objecr.sortName && null != objecr.sortOrder ){
						queryJSON["sort"] = objecr.sortName;
						queryJSON["order"] = objecr.sortOrder;
					}
					var queryParam = JSON.stringify(queryJSON);
					var url = "report/finance/exportBaseFinanceReceiptData.do";
					exportByAnalyse(queryParam,"分客户业务员资金应收款情况表","report-datagrid-salesuserReceipt",url);
				});

				var salesusertableColumnListJson = $("#report-datagrid-salesuserReceipt").createGridColumnLoad({
					frozenCol : [[
						{field:'idok',checkbox:true,isShow:true}
					]],
					commonCol : [[
						{field:'salesuser',title:'客户业务员',width:80,
							formatter:function(value,rowData,rowIndex){
								return rowData.salesusername;
							}
						},
						{field:'allunwithdrawnamount',title:'应收款总额',align:'right',resizable:true,isShow:true,sortable:true,
							formatter:function(value,rowData,rowIndex){
								if(value!=0){
									return '<a href="javascript:showDetail(\''+rowData.salesuser+'\',0);" style="text-decoration:none">'+formatterMoney(value)+'</a>';
								}else{
									return formatterMoney(value);
								}
							}
						},
						{field:'unauditamount',title:'未验收应收款',align:'right',resizable:true,isShow:true,sortable:true,
							formatter:function(value,rowData,rowIndex){
								if(value!=0){
									return '<a href="javascript:showDetail(\''+rowData.salesuser+'\',0);" style="text-decoration:none">'+formatterMoney(value)+'</a>';
								}else{
									return formatterMoney(value);
								}
							}
						},
						{field:'auditamount',title:'已验收应收款',align:'right',resizable:true,isShow:true,sortable:true,
							formatter:function(value,rowData,rowIndex){
								if(value!=0){
									return '<a href="javascript:showDetail(\''+rowData.salesuser+'\',1);" style="text-decoration:none">'+formatterMoney(value)+'</a>';
								}else{
									return formatterMoney(value);
								}
							}
						},
						{field:'rejectamount',title:'退货应收款',align:'right',resizable:true,isShow:true,sortable:true,
							formatter:function(value,rowData,rowIndex){
								if(value!=0){
									return '<a href="javascript:showDetail(\''+rowData.salesuser+'\',2);" style="text-decoration:none">'+formatterMoney(value)+'</a>';
								}else{
									return formatterMoney(value);
								}
							}
						},
						{field:'allpushbalanceamount',title:'冲差应收款',align:'right',resizable:true,isShow:true,sortable:true,
							formatter:function(value,rowData,rowIndex){
								if(value!=0){
									return '<a href="javascript:showDetail(\''+rowData.salesuser+'\',0);" style="text-decoration:none">'+formatterMoney(value)+'</a>';
								}else{
									return formatterMoney(value);
								}
							}
						}
					]]
				});

    			$("#report-datagrid-salesuserReceipt").datagrid({
					authority:salesusertableColumnListJson,
					frozenColumns: salesusertableColumnListJson.frozen,
					columns:salesusertableColumnListJson.common,
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		singleSelect:true,
		  	 		pageSize:100,
					toolbar:'#report-toolbar-salesuserReceipt'
				}).datagrid("columnMoving");
				$("#report-query-salesuser").widget({
					referwid:'RL_T_BASE_PERSONNEL_CLIENTSELLER',
		    		width:210,
					onlyLeafCheck:false,
					singleSelect:false
				});
				
				//回车事件
				controlQueryAndResetByKey("report-queay-salesuserReceipt","report-reload-salesuserReceipt");
				
				//查询
				$("#report-queay-salesuserReceipt").click(function(){
					//把form表单的name序列化成JSON对象
		      		var queryJSON = $("#report-query-form-salesuserReceipt").serializeJSON();
		      		$("#report-datagrid-salesuserReceipt").datagrid({
		      			url: 'report/finance/showBaseFinanceReceiptData.do',
		      			pageNumber:1,
						queryParams:queryJSON
		      		});
				});
				//重置
				$("#report-reload-salesuserReceipt").click(function(){
					$("#report-query-salesuser").widget("clear");
					$("#report-query-form-salesuserReceipt")[0].reset();
		       		$("#report-datagrid-salesuserReceipt").datagrid('loadData',{total:0,rows:[]});
				});
    		});
    		
    		function showDetail(salesuserid,type){
    			var businessdate1 = $("#report-query-businessdate1").val();
    			var businessdate2 = $("#report-query-businessdate2").val();
    			var url = 'report/finance/showFundsCustomerListPage.do?salesuserid='+salesuserid+'&type='+type+'&businessdate1='+businessdate1+'&businessdate2='+businessdate2;
    			$("#report-fundsCustomer-detail-dialog").dialog({
				    title: '分客户业务员销售回单列表',  
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
