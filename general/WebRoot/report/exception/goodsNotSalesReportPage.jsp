<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>多日未销售商品统计页面</title>
    <%@include file="/include.jsp" %> 
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>    
  </head>
  <body>
	<table id="report-datagrid-goodsNotSalesReportPage"></table>
	<div id="report-tool-goodsNotSalesReportPage" style="padding: 0px">
        <div class="buttonBG">
            <security:authorize url="/report/exception/goodsNotSalesReportExport.do">
                <a href="javaScript:void(0);" id="report-export-goodsNotSalesReportPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
            </security:authorize>
        </div>
		<form action="" id="report-form-goodsNotSalesReportPage" methos="post">
			<table class="querytable">

				<tr>
                    <td>品牌名称：</td>
                    <td><input name="brandid" id="report-brand-goodsNotSalesReportPage" /></td>
					<td>未销售天数：</td>
					<td><input class="easyui-numberbox" name="beginnum" style="width:70px;" data-options="min:0" /> 天 到 <input class="easyui-numberbox" name="endnum" style="width:60px;" data-options="min:0" /> 天</td>
				    <td>数量区间：</td>
                    <td><input class="easyui-numberbox" name="unitnum1" style="width:70px;" data-options="min:0" />-<input class="easyui-numberbox" name="unitnum2" style="width:60px;" data-options="min:0" /> </td>
                </tr>
				<tr>
					<td>商品名称：</td>
					<td><input name="goodsid" id="report-goods-goodsNotSalesReportPage" style="width: 200px;"/></td>
                    <td>仓&nbsp;&nbsp;&nbsp;库：</td>
                    <td><input name="storageid" id="report-storageid-goodsNotSalesReportPage" style="width: 194px;"/></td>
                    <td colspan="2" style="padding-left: 10px">
						<a href="javaScript:void(0);" id="report-queay-goodsNotSalesReportPage" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="report-reload-goodsNotSalesReportPage" class="button-qr">重置</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
    <div id="report-detailDialog-goodsNotSalesReportPage"></div>
	<script type="text/javascript">
		var tableColumnListJson = $("#report-datagrid-goodsNotSalesReportPage").createGridColumnLoad({
			frozenCol : [[]],
			commonCol : [[
				{field:'id',title:'商品编号',width:80},
				{field:'name',title:'商品名称',width:300},
				{field:'barcode',title:'条形码',sortable:true,width:90},
				{field:'brandName',title:'商品品牌',width:120},
				//{field:'defaultsalerName',title:'默认业务员',width:100},
                {field:'field02',title:'数量',width:50,
                    formatter:function(value,row,index){
                        return formatterBigNumNoLen(value);
                    },
                },
                {field:'field03',title:'成本金额',width:60},
				{field:'newsaledate',title:'最新销售日期',width:100},
				{field:'field01',title:'未发生交易天数',width:130}
	        ]]
		});
		$(function(){
			$("#report-brand-goodsNotSalesReportPage").widget({
				referwid:'RL_T_BASE_GOODS_BRAND',
		    	width:200,
				singleSelect:true
			});

            $("#report-storageid-goodsNotSalesReportPage").widget({
                referwid:'RL_T_BASE_STORAGE_INFO',
                width:194,
                singleSelect:true
            });

			$("#report-goods-goodsNotSalesReportPage").goodsWidget({});
			$("#report-datagrid-goodsNotSalesReportPage").datagrid({ 
				authority:tableColumnListJson,
				frozenColumns: tableColumnListJson.frozen,
				columns:tableColumnListJson.common,
				method:'post',
		  	 	title:'',
		  	 	fit:true,
		  	 	rownumbers:true,
		  	 	pagination:true,
		  	 	pageSize:100,
		  	 	showFooter: true,
		  	 	singleSelect:true,
				idField:'id',  
				toolbar:'#report-tool-goodsNotSalesReportPage',
                onDblClickRow:function(index,data){
                    var storageid = $("#report-storageid-goodsNotSalesReportPage").widget('getValue');
                    $("#report-detailDialog-goodsNotSalesReportPage").dialog({
                        title: '商品:'+data.id+"["+data.name+"]库存信息明细",
                        width: 400,
                        height: 300,
                        closed: false,
                        cache: false,
                        modal: true,
                        href:'report/exception/showGoodsNotSalesInStoragePage.do?id='+data.id+'&storageid='+storageid,

                    });

                }
			}).datagrid("columnMoving");
			$("#report-queay-goodsNotSalesReportPage").click(function(){
				var queryJSON = $("#report-form-goodsNotSalesReportPage").serializeJSON();
		      	$("#report-datagrid-goodsNotSalesReportPage").datagrid({
		      		url:'report/exception/getGoodsNotSalesReport.do',
		      		pageNumber:1,
   					queryParams:queryJSON
		      	}).datagrid("columnMoving");
			});
			$("#report-reload-goodsNotSalesReportPage").click(function(){
				$("#report-form-goodsNotSalesReportPage").form("reset");
				$("#report-brand-goodsNotSalesReportPage").widget("clear");
                $("#report-storageid-goodsNotSalesReportPage").widget('clear');
				$("#report-goods-goodsNotSalesReportPage").goodsWidget("clear");
				$("#report-datagrid-goodsNotSalesReportPage").datagrid('reload', []);
			});
			$("#report-export-goodsNotSalesReportPage").Excel('export',{
				queryForm: "#report-form-goodsNotSalesReportPage",
				type:'exportUserdefined',
				name:'多日未销售商品统计表',
				url:'report/exception/exportGoodsNotSalesReport.do'
			});
		});
	</script>
  </body>
</html>
