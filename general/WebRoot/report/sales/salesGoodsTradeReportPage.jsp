<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>客户交易商品报表</title>
    <%@include file="/include.jsp" %> 
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>    
  </head>  
  <body>
  	<table id="report-datagrid-salesGoodsTradeReport"></table>
  	<div id="report-toolbar-salesGoodsTradeReport" style="padding: 0px">
        <div class="buttonBG">
            <security:authorize url="/report/sales/exportSalesGoodsTradeReportData.do">
                <a href="javaScript:void(0);" id="report-export-salesGoodsTradeReportBtn" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-export'">全局导出</a>
                <a href="javaScript:void(0);" id="report-export-salesGoodsTradeReport" style="display: none">全局导出</a>
            </security:authorize>
            <div id="dialog-autoexport"></div>
        </div>
	  	<form action="" method="post" id="report-form-salesGoodsTradeReport">
			<table>
				<tr>
					<td>客&nbsp;&nbsp;户:</td>
					<td><input type="text" id="report-salesGoodsTradeReport-customerid" name="customerid" style="width:210px;" /></td>
					<td>商品:</td>
					<td><input type="text" name="goodsid" id="report-salesGoodsTradeReport-goodsid" style="width:205px;" /></td>
				</tr>
				<tr>
					<td>品牌名称:</td>
    				<td><input type="text" id="report-salesGoodsTradeReport-brandid" name="brandid" style="width:210px;" /></td>	
					<td></td>
                    <td>
						<a href="javaScript:void(0);" id="report-query-salesGoodsTradeReport" class="button-qr">查询</a>
	 					<a href="javaScript:void(0);" id="report-reload-salesGoodsTradeReport" class="button-qr">重置</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript">
		var initQueryJSON = $("#report-datagrid-salesGoodsTradeReport").serializeJSON();
		$(function(){
			var tableColumnListJson = $("#report-datagrid-salesGoodsTradeReport").createGridColumnLoad({
				frozenCol : [[]],
				commonCol : [[
					{field:'id',hidden:true},
					{field:'customerid',title:'客户编号',sortable:true },
					{field:'customername',title:'客户名称',width:150,isShow:true },
					{field:'goodsid',title:'商品编码',width:60,sortable:true},
					{field:'goodsname',title:'商品名称',width:210,isShow:true},
					{field:'boxnum',title:'箱装量',width:60,isShow:true},
					{field:'barcode',title:'条形码',width:90},
					{field:'price',title:'含税单价',width:80,align:'right',
						formatter: function(value,row,index){
							return formatterDefineMoney(value,4);
						}
					}
		         ]]
			});
			$("#report-datagrid-salesGoodsTradeReport").datagrid({ 
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
				toolbar:'#report-toolbar-salesGoodsTradeReport',
			});
			$("#report-salesGoodsTradeReport-goodsid").goodsWidget({
				width:205,
				onlyLeafCheck:false,
				singleSelect:false
			});
			$("#report-salesGoodsTradeReport-customerid").customerWidget({
	   			singleSelect:false,
	   			width:'210',
	   			required:false
	  		});
			$("#report-salesGoodsTradeReport-brandid").widget({
				referwid:'RL_T_BASE_GOODS_BRAND',
	    		width:210,
				singleSelect:true
			});
			$("#report-query-salesGoodsTradeReport").click(function(){
				var goodsid=$("#report-salesGoodsTradeReport-goodsid").goodsWidget('getValue');
				var customer=$("#report-salesGoodsTradeReport-customerid").customerWidget('getValue');
				var brandid=$("#report-salesGoodsTradeReport-brandid").widget("getValue");
				if((null==goodsid || "" == goodsid) && (null == customer || "" == customer) &&(null==brandid || ""==brandid) ){
					$.messager.alert("提醒","抱歉，查询条件商品、客户、品牌必填一项");
					return false;						
				}
				var queryJSON = $("#report-form-salesGoodsTradeReport").serializeJSON();
	      		$("#report-datagrid-salesGoodsTradeReport").datagrid({
	      			url: 'report/sales/showSalesGoodsTradeReportData.do',
	      			pageNumber:1,
					queryParams:queryJSON
	      		}).datagrid("columnMoving");
			});
			//重置
			$("#report-reload-salesGoodsTradeReport").click(function(){
				$("#report-form-salesGoodsTradeReport")[0].reset();
				
				$("#report-salesGoodsTradeReport-goodsid").goodsWidget('clear');
				$("#report-salesGoodsTradeReport-customerid").customerWidget('clear');
				
				$('#report-datagrid-salesGoodsTradeReport').datagrid('loadData', { total: 0, rows: [] });
			});
            //全局导出方法
            $("#report-export-salesGoodsTradeReport").click(function(){
                var queryJSON = $("#report-form-salesGoodsTradeReport").serializeJSON();
                //获取排序规则
                var objecr  = $("#report-datagrid-salesGoodsTradeReport").datagrid("options");
                if(null != objecr.sortName && null != objecr.sortOrder ){
                    queryJSON["sort"] = objecr.sortName;
                    queryJSON["order"] = objecr.sortOrder;
                }
                var queryParam = JSON.stringify(queryJSON);
                var url = "report/sales/exportSalesGoodsTradeReportData.do";
                exportByAnalyse(queryParam,"客户已交易商品报表","report-datagrid-salesGoodsTradeReport",url);
            });
            //回车事件
			controlQueryAndResetByKey("report-query-salesGoodsTradeReport","report-reload-salesGoodsTradeReport");
			<security:authorize url="/report/sales/exportSalesGoodsTradeReportData.do">

				$("#report-export-salesGoodsTradeReportBtn").click(function(){
					
					var goodsid=$("#report-salesGoodsTradeReport-goodsid").goodsWidget('getValue');
					var customer=$("#report-salesGoodsTradeReport-customerid").customerWidget('getValue');
					var brandid=$("#report-salesGoodsTradeReport-brandid").widget("getValue");
					if((null==goodsid || "" == goodsid) && (null == customer || "" == customer) &&(null==brandid || ""==brandid) ){
						$.messager.alert("提醒","抱歉，查询条件商品、客户、品牌必填一项");
						return false;						
					}					
					$("#report-export-salesGoodsTradeReport").trigger("click");
				});
			</security:authorize>
		});
	</script>
  </body>
</html>