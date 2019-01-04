<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>资金统计报表</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>  
  </head>
  
  <body>
   	<div id="report-button-QueryFundStatistics" style="padding: 0px">
        <div class="buttonBG">
            <security:authorize url="/report/storage/fundStatisticsExport.do">
                <a href="javaScript:void(0);" id="report-buttons-fundStatisticsPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
            </security:authorize>
        </div>
   		<form action="" id="report-form-QueryFundStatistics" method="post">
   			<table class="querytable">
   				<tr>
   					<td style="padding-left: 10px;">供应商:</td>
   					<td><input id="report-widget-supplierquery" style="width: 250px;" name="supplierid" type="text"/></td>
   					<td style="padding-left: 10px;">所属部门:&nbsp;</td>
   					<td><input id="report-widget-supplierdeptquery" name="branddept" type="text"/></td>
   					<td style="padding-left: 10px;" colspan="2">
   						<a href="javaScript:void(0);" id="report-query-capitalOccupy" class="button-qr">查询</a>
			    		<a href="javaScript:void(0);" id="report-reload-capitalOccupy" class="button-qr">重置</a>

   					</td>
   				</tr>
   			</table>
   		</form>
   	</div>
    <table id="report-table-capitalOccupy"></table>
    <script type="text/javascript">
    	var FundinputListColJson=$("#report-table-capitalOccupy").createGridColumnLoad({
	     	frozenCol:[[]],
	     	commonCol:[[
	     		{field:'supplierid',title:'供应商编号',width:80,sortable:true},
				{field:'suppliername',title:'供应商名称',width:150},
				{field:'branddept',title:'所属部门',width:100,sortable:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.branddeptname;
					}
				},
				{field:'prepayamount',title:'预付金额',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						return formatterMoney(val);
					}
				},
				{field:'storageamount',title:'库存金额',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						return formatterMoney(val);
					}
				},
				{field:'receivableamount',title:'应收金额',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						return formatterMoney(val);
					}
				},
				{field:'advanceamount',title:'代垫金额',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						return formatterMoney(val);
					}
				},
				{field:'payableamount',title:'应付金额',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						return formatterMoney(val);
					}
				},
				{field:'totalamount',title:'合计金额',resizable:true,sortable:true,align:'right',isShow:true,
					formatter:function(val,rowData,rowIndex){
						return formatterMoney(val);
					}
				}
			]]
	     });
	     
		$(function(){
			//供应商
		  	$("#report-widget-supplierquery").supplierWidget();
			$("#report-widget-supplierdeptquery").widget({
		  		width:'120',
		  		referwid:'RL_T_BASE_DEPARTMENT_BUYER',
				singleSelect:true
			});
			
			//回车事件
			controlQueryAndResetByKey("report-query-capitalOccupy","report-reload-capitalOccupy");
			
			//查询
			$("#report-query-capitalOccupy").click(function(){
				//把form表单的name序列化成JSON对象
	      		var queryJSON = $("#report-form-QueryFundStatistics").serializeJSON();
	      		
	      		//调用datagrid本身的方法把JSON对象赋给queryParams 即可进行查询
	      		$("#report-table-capitalOccupy").datagrid({
	      			url:'report/storage/showCapitalOccupyListData.do',
	      			pageNumber:1,
	      			queryParams:queryJSON
	      		}).datagrid("columnMoving");
			});
			
			$("#report-reload-capitalOccupy").click(function(){
				$("#report-widget-supplierquery").supplierWidget("clear");
				$("#report-widget-supplierdeptquery").widget("clear");
				$("#report-form-QueryFundStatistics")[0].reset();
	       		$("#report-table-capitalOccupy").datagrid('loadData',{total:0,rows:[]});
			});
			
			$("#report-buttons-fundStatisticsPage").Excel('export',{
				queryForm: "#report-form-QueryFundStatistics", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
		 		type:'exportUserdefined',
		 		name:'资金占用统计报表',
		 		url:'report/storage/exportCapitalOccupyListData.do'
			});
			
			$("#report-table-capitalOccupy").datagrid({
				authority:FundinputListColJson,
	  	 		frozenColumns:[[]],
				columns:FundinputListColJson.common,
	  	 		fit:true,
	  	 		method:'post',
	  	 		title:'',
	  	 		showFooter: true,
	  	 		rownumbers:true,
	  	 		sortName:'supplierid',
	  	 		sortOrder:'asc',
	  	 		idField:'supplierid',
	  	 		singleSelect:true,
				toolbar:'#report-button-QueryFundStatistics'
			}).datagrid("columnMoving");
			
		});
    </script>
  </body>
</html>
