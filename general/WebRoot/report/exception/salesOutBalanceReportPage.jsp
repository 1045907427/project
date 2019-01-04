<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>发货差额统计表</title>
    <%@include file="/include.jsp" %>   
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>  
  </head>
  <body>
	<div id="report-tool-saleoutBalanceReportPage" style="padding: 0px">
        <div  class="buttonBG">
            <security:authorize url="/report/exception/exportSalesOutBalanceReportData.do">
                <a href="javaScript:void(0);" id="report-export-saleoutBalanceReportPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
            </security:authorize>
        </div>
		<form action="" id="report-form-saleoutBalanceReportPage" methos="post">
			<table class="querytable">
				<tr>
					<td>业务日期：</td>
					<td><input type="text" name="begindate" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${firstDay }"/> 到 <input type="text" name="enddate" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${today }"/></td>
					<td>客户名称：</td>
					<td><input type="text" name="customerid" style="width: 200px;" id="report-customer-saleoutBalanceReportPage" /></td>
				</tr>
				<tr>
					<td>商品名称:</td>
					<td>
						<input id="report-goodsid-saleoutBalanceReportPage" style="width: 225px;" type="text" name="goodsid"/>
					</td>
					<td>品牌名称:</td>
					<td>
						<input id="report-brandid-saleoutBalanceReportPage" style="width: 130px;" type="text" name="brandid"/>
					</td>
				</tr>
				<tr>
					<td>品牌部门:</td>
					<td>
						<input id="report-branddept-saleoutBalanceReportPage" style="width: 225px;" type="text" name="branddept"/>
					</td>
					<td>品牌业务员:</td>
					<td>
						<input id="report-branduser-saleoutBalanceReportPage" style="width: 200px;" type="text" name="branduser"/>
					</td>
					<td colspan="2">
						<a href="javaScript:void(0);" id="report-queay-saleoutBalanceReportPage" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="report-reload-saleoutBalanceReportPage" class="button-qr">重置</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<table id="report-datagrid-saleoutBalanceReportPage"></table>
	<script type="text/javascript">
		$(function(){
			var tableColumnListJson = $("#report-datagrid-saleoutBalanceReportPage").createGridColumnLoad({
				frozenCol : [[]],
				commonCol : [[
					{field:'id',title:'发货通知单编号',width:120,sortable:true},
					{field:'billno',title:'订单编号',width:110,sortable:true},
					{field:'businessdate',title:'业务日期',width:70,sortable:true},
					{field:'customerid',title:'客户编号',width:60,sortable:true},
					{field:'customername',title:'客户名称',width:100},
					{field:'goodsid',title:'商品编码',width:60,sortable:true},
					{field:'goodsname',title:'商品名称',width:120},
					{field:'branddept',title:'品牌部门',width:70,sortable:true,
						formatter:function(value,rowData,rowIndex){
			        		return rowData.branddeptname;
			        	}
					},
					{field:'branduser',title:'品牌业务员',width:60,sortable:true,
						formatter:function(value,rowData,rowIndex){
			        		return rowData.brandusername;
			        	}
					},
					{field:'taxprice',title:'单价',width:40,align:'right',sortable:true,
						formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					},
					{field:'costprice',title:'成本价',width:40,hidden:true,align:'right',sortable:true,
						formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					},
					{field:'unitnum',title:'发货数量',width:60,align:'right',sortable:true,
						formatter:function(value,rowData,rowIndex){
			        		return formatterBigNumNoLen(value);
			        	}
					},
					{field:'taxamount',title:'发货金额',width:60,align:'right',sortable:true,
						formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					},
					{field:'outnummain',title:'实际出库数量',width:60,align:'right',sortable:true,
						formatter:function(value,rowData,rowIndex){
			        		if(value!=0){
			        			return formatterBigNumNoLen(value);
			        		}else{
			        			return 0;
			        		}
			        	}
					},
					{field:'outamounttax',title:'实际出库金额',width:60,align:'right',sortable:true,
						formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					},
					{field:'balancenum',title:'数量差',width:60,align:'right',sortable:true,
						formatter:function(value,rowData,rowIndex){
			        		return formatterBigNumNoLen(value);
			        	}
					},
					{field:'balanceamount',title:'差额',width:60,align:'right',sortable:true,
						formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					}
		        ]]
			});
			$("#report-datagrid-saleoutBalanceReportPage").datagrid({ 
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
				toolbar:'#report-tool-saleoutBalanceReportPage'
			}).datagrid("columnMoving");
			$("#report-goodsid-saleoutBalanceReportPage").goodsWidget({
				width:200,
    			required:false
			});
			$("#report-customer-saleoutBalanceReportPage").customerWidget({});
			$("#report-brandid-saleoutBalanceReportPage").widget({
				referwid:'RL_T_BASE_GOODS_BRAND',
		    	width:200,
				singleSelect:true
			});
			$("#report-branddept-saleoutBalanceReportPage").widget({
				referwid:'RL_T_BASE_DEPARTMENT_BUYER',
		    	width:225,
				singleSelect:true
			});
			$("#report-branduser-saleoutBalanceReportPage").widget({
				referwid:'RL_T_BASE_PERSONNEL_BRANDSELLER',
		    	width:200,
				singleSelect:true
			});
			
			$("#report-queay-saleoutBalanceReportPage").click(function(){
				var queryJSON = $("#report-form-saleoutBalanceReportPage").serializeJSON();
		      	$("#report-datagrid-saleoutBalanceReportPage").datagrid({
		      		url:'report/exception/showSalesOutBalanceReportData.do',
		      		pageNumber:1,
   					queryParams:queryJSON
		      	}).datagrid("columnMoving");
			});
			$("#report-reload-saleoutBalanceReportPage").click(function(){
				$("#report-form-saleoutBalanceReportPage").form("reset");
				$("#report-brandid-saleoutBalanceReportPage").widget("clear");
				$("#report-branddept-saleoutBalanceReportPage").widget("clear");
				$("#report-branduser-saleoutBalanceReportPage").widget("clear");
				$("#report-goodsid-saleoutBalanceReportPage").goodsWidget("clear");
				$("#report-customer-saleoutBalanceReportPage").customerWidget("clear");
				$("#report-datagrid-saleoutBalanceReportPage").datagrid('loadData',{total:0,rows:[]});
			});
			$("#report-export-saleoutBalanceReportPage").Excel('export',{
				queryForm: "#report-form-saleoutBalanceReportPage",
				type:'exportUserdefined',
				name:'发货差额统计报表',
				url:'report/exception/exportSalesOutBalanceReportData.do'
			});
		});	
	</script>
  </body>
</html>
