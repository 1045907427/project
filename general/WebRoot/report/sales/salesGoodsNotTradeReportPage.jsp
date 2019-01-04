<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>客户未交易商品报表</title>
    <%@include file="/include.jsp" %> 
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>    
  </head>  
  <body>
  	<table id="report-datagrid-salesGoodsNotTradeReport"></table>
  	<div id="report-toolbar-salesGoodsNotTradeReport" style="padding: 0px">
        <div class="buttonBG">
            <security:authorize url="/report/sales/exportSalesGoodsNotTradeReportData.do">
                <a href="javaScript:void(0);" id="report-export-salesGoodsNotTradeReportBtn" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-export'" >全局导出</a>
                <a href="javaScript:void(0);" id="report-export-salesGoodsNotTradeReport" style="display: none">全局导出</a>
            </security:authorize>
            <div id="dialog-autoexport"></div>
        </div>
	  	<form action="" method="post" id="report-form-salesGoodsNotTradeReport">
			<table>

				<tr>
					<td>客&nbsp;&nbsp;户:</td>
					<td><input type="text" id="report-salesGoodsNotTradeReport-customerid" name="customerid" style="width:210px;" /></td>
					<td>商品:</td>
					<td><input type="text" name="goodsid" id="report-salesGoodsNotTradeReport-goodsid" style="width:210px;" /></td>
				</tr>
				<tr>
					<td>品牌名称:</td>
    				<td><input type="text" id="report-salesGoodsNotTradeReport-brandid" name="brandid" style="width:210px;" /></td>	
					<td></td>
                    <td>
						<a href="javaScript:void(0);" id="report-query-salesGoodsNotTradeReport" class="button-qr">查询</a>
	 					<a href="javaScript:void(0);" id="report-reload-salesGoodsNotTradeReport" class="button-qr">重置</a>

					</td>
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript">
		var initQueryJSON = $("#report-datagrid-salesGoodsNotTradeReport").serializeJSON();
		$(function(){

			var tableColumnListJson = $("#report-datagrid-salesGoodsNotTradeReport").createGridColumnLoad({
				frozenCol : [[]],
				commonCol : [[
					{field:'id',hidden:true},
					{field:'customerid',title:'客户编号',width:100,sortable:true},
					{field:'customername',title:'客户名称',width:150,isShow:true},
					{field:'goodsid',title:'商品编码',width:60,sortable:true},
					{field:'goodsname',title:'商品名称',width:210,isShow:true},
					{field:'barcode',title:'条形码',width:90},
					{field:'price',title:'含税单价',width:80,align:'right',
						formatter: function(value,row,index){
							return formatterMoney(value);
						}
					}
		         ]]
			});
			$("#report-datagrid-salesGoodsNotTradeReport").datagrid({ 
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
				toolbar:'#report-toolbar-salesGoodsNotTradeReport',
			});
			$("#report-salesGoodsNotTradeReport-goodsid").goodsWidget({
				width:210,
				onlyLeafCheck:false,
				singleSelect:false
			});
			$("#report-salesGoodsNotTradeReport-customerid").customerWidget({
	   			singleSelect:false,
	   			width:'210',
	   			required:false
	  		});
			$("#report-salesGoodsNotTradeReport-brandid").widget({
				referwid:'RL_T_BASE_GOODS_BRAND',
	    		width:210,
				singleSelect:true
			});
			$("#report-query-salesGoodsNotTradeReport").click(function(){
				var customer=$("#report-salesGoodsNotTradeReport-customerid").widget('getValue');
				var goodsid=$("#report-salesGoodsNotTradeReport-goodsid").goodsWidget('getValue');
				if((null == customer || "" == customer) && (null==goodsid || "" == goodsid)){
					$.messager.alert("提醒","抱歉，查询条件商品、客户必填一项");
					return false;				
				}
				
				var queryJSON = $("#report-form-salesGoodsNotTradeReport").serializeJSON();
	      		$("#report-datagrid-salesGoodsNotTradeReport").datagrid({
	      			url: 'report/sales/showSalesGoodsNotTradeReportData.do',
	      			pageNumber:1,
					queryParams:queryJSON
	      		}).datagrid("columnMoving");
			});
			//重置
			$("#report-reload-salesGoodsNotTradeReport").click(function(){
				
				$("#report-form-salesGoodsNotTradeReport")[0].reset();
				
				$("#report-salesGoodsNotTradeReport-goodsid").goodsWidget('clear');
				$("#report-salesGoodsNotTradeReport-customerid").customerWidget('clear');
				$("#report-salesGoodsNotTradeReport-brandid").widget('clear');
				
				$('#report-datagrid-salesGoodsNotTradeReport').datagrid('loadData', { total: 0, rows: [] });
			});
            //全局导出方法
            $("#report-export-salesGoodsNotTradeReport").click(function(){
                var queryJSON = $("#report-form-salesGoodsNotTradeReport").serializeJSON();
                //获取排序规则
                var objecr  = $("#report-datagrid-salesGoodsNotTradeReport").datagrid("options");
                if(null != objecr.sortName && null != objecr.sortOrder ){
                    queryJSON["sort"] = objecr.sortName;
                    queryJSON["order"] = objecr.sortOrder;
                }
                var queryParam = JSON.stringify(queryJSON);
                var url = "report/sales/exportSalesGoodsNotTradeReportData.do";
                exportByAnalyse(queryParam,"客户未交易商品报表","report-datagrid-salesGoodsNotTradeReport",url);
            });
	  		//回车事件
			controlQueryAndResetByKey("report-query-salesGoodsNotTradeReport","report-reload-salesGoodsNotTradeReport");
			<security:authorize url="/report/sales/exportSalesGoodsNotTradeReportData.do">

				$("#report-export-salesGoodsNotTradeReportBtn").click(function(){
					var customer=$("#report-salesGoodsNotTradeReport-customerid").widget('getValue');
					var goodsid=$("#report-salesGoodsNotTradeReport-goodsid").goodsWidget('getValue');
					if((null == customer || "" == customer) && (null==goodsid || "" == goodsid)){
						$.messager.alert("提醒","抱歉，查询条件商品、客户必填一项");
						return false;				
					}
					$("#report-export-salesGoodsNotTradeReport").trigger("click");
				});
			</security:authorize>
		});
	</script>
  </body>
</html>