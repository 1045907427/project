<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>客户商品合同统计报表</title>
    <%@include file="/include.jsp" %>  
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>   
  </head>  
  <body>
  	<table id="report-datagrid-salesGoodsSandPriceReport"></table>
  	<div id="report-toolbar-salesGoodsSandPriceReport" style="padding: 0px">
        <div class="buttonBG">
            <security:authorize url="/report/sales/exportSalesGoodsSandPriceReportData.do">
                <a href="javaScript:void(0);" id="report-export-salesGoodsSandPriceReportBtn" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-export'">全局导出</a>
                <a href="javaScript:void(0);" id="report-export-salesGoodsSandPriceReport" style="display: none">全局导出</a>
            </security:authorize>
            <div id="dialog-autoexport"></div>
        </div>
	  	<form action="" method="post" id="report-form-salesGoodsSandPriceReport">
			<table>

				<tr>
					<td>客户:</td>
					<td><input type="text" id="report-salesGoodsSandPriceReport-customerid" name="customerid"/></td>
					<td>品牌名称:</td>
    				<td><input type="text" id="report-salesGoodsSandPriceReport-brandid" name="brandid"/></td>
				</tr>
				<tr>
					<td>商品:</td>
					<td><input type="text" name="goodsid" id="report-salesGoodsSandPriceReport-goodsid" style="width:180px;" /></td>
					<td></td>
                    <td>
						<a href="javaScript:void(0);" id="report-query-salesGoodsSandPriceReport" class="button-qr">查询</a>
	 					<a href="javaScript:void(0);" id="report-reload-salesGoodsSandPriceReport" class="button-qr">重置</a>

					</td>
				</tr>
			</table>
		</form>
	</div>
	<script type="text/javascript">

        var tableColumnListJson = $("#report-datagrid-salesGoodsSandPriceReport").createGridColumnLoad({
            frozenCol : [[]],
            commonCol : [[
                {field:'id',width:20,align:'left',hidden:true},
                {field:'customerid',title:'客户编号',width:100,align:'left',sortable:true},
                {field:'customername',title:'客户名称',width:150,align:'left',isShow:true},
                {field:'goodsid',title:'商品编码',width:80,align:'left',sortable:true},
                {field:'goodsname',title:'商品名称',width:210,align:'left',isShow:true},
                {field:'shopid',title:'店内码',width:70,align:'left'},
                {field:'barcode',title:'条形码',width:100,align:'left'},
                {field:'pricetype',title:'价格套',width:70,align:'left'},
                {field:'taxprice',title:'价格套价格',width:70,align:'left',
                    formatter: function(value,row,index){
                        return formatterDefineMoney(value,4);
                    }
                },
                {field:'price',title:'含税合同价',width:70,align:'left',sortable:true,
                    formatter: function(value,row,index){
                        return formatterDefineMoney(value,4);
                    }
                },
                {field:'newbuyprice',title:'成本价',width:70,align:'left',
                    formatter: function(value,row,index){
                        return formatterDefineMoney(value,4);
                    }
                },
                {field:'rate',title:'毛利率',width:70,align:'left',
                    formatter: function(value,row,index){
                        if(value != undefined){
                            return formatterDefineMoney(value,4)+"%";
                        }
                    }
                },
                {field:'noprice',title:'未税合同价',width:70,align:'left',sortable:true,
                    formatter: function(value,row,index){
                        return formatterDefineMoney(value,4);
                    }
                },
            ]]
        });

		$(function(){

			$("#report-datagrid-salesGoodsSandPriceReport").datagrid({ 
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
				toolbar:'#report-toolbar-salesGoodsSandPriceReport'
			});
			$("#report-salesGoodsSandPriceReport-goodsid").goodsWidget({
				onlyLeafCheck:false,
				singleSelect:false
			});
			$("#report-salesGoodsSandPriceReport-customerid").widget({
				referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT_1',
				width:180,
	   			singleSelect:false,
	   			required:false
	  		});
			$("#report-salesGoodsSandPriceReport-brandid").widget({
				referwid:'RL_T_BASE_GOODS_BRAND',
	    		width:205,
				singleSelect:true
			});
            //查询
			$("#report-query-salesGoodsSandPriceReport").click(function(){
				var goodsid=$("#report-salesGoodsSandPriceReport-goodsid").goodsWidget('getValue');
				var customer=$("#report-salesGoodsSandPriceReport-customerid").widget('getValue');
				var brandid=$("#report-salesGoodsSandPriceReport-brandid").widget("getValue");
				if((null==goodsid || "" == goodsid) && 
					(null == customer || "" == customer) &&
					(null==brandid || ""==brandid)
				){
					$.messager.alert("提醒","抱歉，查询条件商品、客户、品牌必填一项");
					return false;						
				}
				var queryJSON = $("#report-form-salesGoodsSandPriceReport").serializeJSON();
	      		$("#report-datagrid-salesGoodsSandPriceReport").datagrid({
	      			url: 'report/sales/showSalesGoodsSandPriceReportData.do',
	      			pageNumber:1,
					queryParams:queryJSON
	      		}).datagrid("columnMoving");
			});
			//重置
			$("#report-reload-salesGoodsSandPriceReport").click(function(){
				$("#report-form-salesGoodsSandPriceReport")[0].reset();
				
				$("#report-salesGoodsSandPriceReport-goodsid").goodsWidget('clear');
				$("#report-salesGoodsSandPriceReport-customerid").widget('clear');
				$("#report-salesGoodsSandPriceReport-brandid").widget('clear');
				
				$('#report-datagrid-salesGoodsSandPriceReport').datagrid('loadData', { total: 0, rows: [] });
			});
            //全局导出方法
            $("#report-export-salesGoodsSandPriceReport").click(function(){
                var queryJSON = $("#report-form-salesGoodsSandPriceReport").serializeJSON();
                //获取排序规则
                var objecr  = $("#report-datagrid-salesGoodsSandPriceReport").datagrid("options");
                if(null != objecr.sortName && null != objecr.sortOrder ){
                    queryJSON["sort"] = objecr.sortName;
                    queryJSON["order"] = objecr.sortOrder;
                }
                var queryParam = JSON.stringify(queryJSON);
                var url = "report/sales/exportSalesGoodsSandPriceReportData.do";
                exportByAnalyse(queryParam,"客户商品合同统计报表","report-datagrid-salesGoodsSandPriceReport",url);

            });
	  		//回车事件
			controlQueryAndResetByKey("report-query-salesGoodsSandPriceReport","report-reload-salesGoodsSandPriceReport");
			<security:authorize url="/report/sales/exportSalesGoodsSandPriceReportData.do">

				$("#report-export-salesGoodsSandPriceReportBtn").click(function(){
					var goodsid=$("#report-salesGoodsSandPriceReport-goodsid").goodsWidget('getValue');
					var customer=$("#report-salesGoodsSandPriceReport-customerid").widget('getValue');
					var brandid=$("#report-salesGoodsSandPriceReport-brandid").widget("getValue");
					if((null==goodsid || "" == goodsid) && 
						(null == customer || "" == customer) &&
						(null==brandid || ""==brandid)
					){
						$.messager.alert("提醒","抱歉，查询条件商品、客户、品牌必填一项");
						return false;						
					}
                    $("#report-export-salesGoodsSandPriceReport").trigger("click");

				});
			</security:authorize>
		});

	</script>
  </body>
</html>
