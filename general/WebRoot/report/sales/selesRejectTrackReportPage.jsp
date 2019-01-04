<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>退货追踪明细表</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>     
  </head>
  <body>
    <table id="report-datagrid-salesRejectTrack"></table>
    	<div id="report-toolbar-salesRejectTrack" style="padding: 0px">
            <div class="buttonBG">
                <a href="javaScript:void(0);" id="report-buttons-salesRejectTrackPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
            </div>
    		<form action="" id="report-query-form-salesRejectTrack" method="post">
    		<table class="querytable">

    			<tr>
    				<td>业务日期:</td>
    				<td><input type="text" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
    				<td colspan="2"></td>
    			</tr>
    			<tr>
    				<td>客户名称:</td>
    				<td><input type="text" id="report-query-customerid" name="customerid"/></td>
    				<td>总店名称:</td>
    				<td><input type="text" id="report-query-pcustomerid" name="pcustomerid"/></td>
    				<td colspan="2"></td>
    			</tr>
    			<tr>
    				<td>商品名称:</td>
    				<td><input type="text" id="report-query-goodsid" name="goodsid"/></td>
    				<td>品牌名称:</td>
    				<td><input type="text" id="report-query-brandid" name="brandid"/></td>
    				<td colspan="2">
    					<a href="javaScript:void(0);" id="report-queay-salesRejectTrack" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="report-reload-salesRejectTrack" class="button-qr">重置</a>
					</td>
    			</tr>
    		</table>
    	</form>
    	</div>
    	<script type="text/javascript">
    		var initQueryJSON = $("#report-query-form-salesRejectTrack").serializeJSON();
    		$(function(){
    			$("#report-datagrid-salesRejectTrack").datagrid({ 
					columns:[[
								  {field:'businessdate',title:'业务日期',width:80,sortable:true},
								  {field:'customerid',title:'客户编号',width:60,sortable:true},
								  {field:'customername',title:'客户名称',width:200},
								  {field:'goodsid',title:'商品编码',width:60,sortable:true},
								  {field:'goodsname',title:'商品名称',width:200},
								  {field:'barcode',title:'条形码',width:90},
								  {field:'brandname',title:'品牌名称',width:60},
								  {field:'unitname',title:'单位',width:40},
								  {field:'qnum',title:'退货数量',width:60,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterBigNumNoLen(value);
						        	}
								  },
								  {field:'qamount',title:'退货金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'tunitnum',title:'入库数量',width:60,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterBigNumNoLen(value);
						        	}
								  },
								  {field:'ttaxamount',title:'入库金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  }
					         ]],
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		singleSelect:true,
		  	 		sortName:'businessdate',
		  	 		sortOrder:'asc',
		  	 		pageSize:100,
					toolbar:'#report-toolbar-salesRejectTrack'
				}).datagrid("columnMoving");
				$("#report-query-customerid").widget({
					referwid:'RL_T_BASE_SALES_CUSTOMER',
		    		width:150,
					singleSelect:true
				});
				$("#report-query-pcustomerid").widget({
					referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
		    		width:150,
					singleSelect:true
				});
				$("#report-query-goodsid").widget({
					referwid:'RL_T_BASE_GOODS_INFO',
		    		width:150,
					singleSelect:true
				});
				$("#report-query-brandid").widget({
					referwid:'RL_T_BASE_GOODS_BRAND',
		    		width:150,
					singleSelect:true
				});
				//回车事件
				controlQueryAndResetByKey("report-queay-salesRejectTrack","report-reload-salesRejectTrack");
				
				//查询
				$("#report-queay-salesRejectTrack").click(function(){
					//把form表单的name序列化成JSON对象
		      		var queryJSON = $("#report-query-form-salesRejectTrack").serializeJSON();
		      		$("#report-datagrid-salesRejectTrack").datagrid({
		      			url:'report/sales/getSalesRejectTrackReportData.do',
		      			pageNumber:1,
   						queryParams:queryJSON
		      		}).datagrid("columnMoving");
				});
				//重置
				$("#report-reload-salesRejectTrack").click(function(){
					$("#report-query-customerid").widget("clear");
					$("#report-query-pcustomerid").widget("clear");
					$("#report-query-goodsid").widget("clear");
					$("#report-query-brandid").widget("clear");
					$("#report-query-form-salesRejectTrack")[0].reset();
		       		$("#report-datagrid-salesRejectTrack").datagrid('loadData',{total:0,rows:[]});
				});
				
				$("#report-buttons-salesRejectTrackPage").Excel('export',{
					queryForm: "#report-query-form-salesRejectTrack", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:'退货追踪明细表',
			 		url:'report/sales/exportSalesRejectTrackReportData.do'
				});
				
    		});
    	</script>
  </body>
</html>
