<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>多日未进货客户统计表</title>
    <%@include file="/include.jsp" %>   
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>  
  </head>
  <body>
	<table id="report-datagrid-customerNotStockReportPage"></table>
	<div id="report-tool-customerNotStockReportPage" style="padding: 0px">
        <div class="buttonBG">
            <security:authorize url="/report/exception/customerNotStockReportExport.do">
                <a href="javaScript:void(0);" id="report-export-customerNotStockReportPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
            </security:authorize>
        </div>
		<form action="" id="report-form-customerNotStockReportPage" methos="post">
			<table>

				<tr>
					<td>未交易天数：</td>
					<td style="padding-right: 30px"><input class="easyui-numberbox" name="beginnum" style="width:60px;" data-options="min:0" /> 天
						  到 <input class="easyui-numberbox" name="endnum" style="width:60px;" data-options="min:0" />天 </td>
					<td>客户名称：</td>
					<td><input name="customerid" id="report-customer-customerNotStockReportPage" style="width: 150px;"/></td>
					</tr>
                <tr>
                    <td>客户业务员：</td>
					<td style="padding-right: 30px"><input id="report-salesuser-customerNotStockReportPage" name="salesuserid"/></td>
					<td colspan="3" style="padding-left: 10px">
						<a href="javaScript:void(0);" id="report-queay-customerNotStockReportPage" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="report-reload-customerNotStockReportPage" class="button-qr">重置</a>

					</td>
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript">
		var tableColumnListJson = $("#report-datagrid-customerNotStockReportPage").createGridColumnLoad({
			frozenCol : [[]],
			commonCol : [[
				{field:'id',title:'客户编码',width:80},
				{field:'name',title:'客户名称',width:300},
				{field:'salesusername',title:'默认业务员',width:100},
				{field:'newsalesdate',title:'最新销售日期',width:100},
				{field:'field01',title:'未发生交易天数',width:130}
	        ]]
		});
		$(function(){
			$("#report-customer-customerNotStockReportPage").customerWidget({});
			$("#report-salesuser-customerNotStockReportPage").widget({
				referwid:'RL_T_BASE_PERSONNEL_SELLER',
		    	width:175,
				singleSelect:false
			});
			$("#report-datagrid-customerNotStockReportPage").datagrid({ 
				authority:tableColumnListJson,
				frozenColumns: tableColumnListJson.frozen,
				columns:tableColumnListJson.common,
				method:'post',
		  	 	title:'',
		  	 	fit:true,
		  	 	rownumbers:true,
		  	 	pagination:true,
		  	 	showFooter: true,
		  	 	singleSelect:true,
		  	 	pageSize:100,
				idField:'id',  
				toolbar:'#report-tool-customerNotStockReportPage'
			}).datagrid("columnMoving");
			$("#report-queay-customerNotStockReportPage").click(function(){
				var queryJSON = $("#report-form-customerNotStockReportPage").serializeJSON();
		      	$("#report-datagrid-customerNotStockReportPage").datagrid({
		      		url:'report/exception/getCustomerNotStockReport.do',
		      		pageNumber:1,
   					queryParams:queryJSON
		      	}).datagrid("columnMoving");
			});
			$("#report-reload-customerNotStockReportPage").click(function(){
				$("#report-salesuser-customerNotStockReportPage").widget("clear");
				$("#report-customer-customerNotStockReportPage").customerWidget('clear');
				$("#report-form-customerNotStockReportPage").form("reset");
				//$("#report-brand-customerNotStockReportPage").widget("clear");
				//$("#report-goods-customerNotStockReportPage").goodsWidget("clear");
				$("#report-datagrid-customerNotStockReportPage").datagrid('reload', []);
			});
			$("#report-export-customerNotStockReportPage").Excel('export',{
				queryForm: "#report-form-customerNotStockReportPage",
				type:'exportUserdefined',
				name:'多日未进货客户统计表',
				url:'report/exception/exportCustomerNotStockReport.do'
			});
		});	
	</script>
  </body>
</html>
