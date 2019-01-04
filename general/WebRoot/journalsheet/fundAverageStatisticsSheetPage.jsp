<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>资金平均单价统计报表</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true">
    	<div id="journalsheet-button-QueryFundAverageStatistics" style="padding: 0px">
			<div class="buttonBG">
				<security:authorize url="/journalsheet/statisticals/fundAverageStatisticsExport.do">
					<a href="javaScript:void(0);" id="journalsheet-buttons-fundAverageStatisticsPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
				</security:authorize>
			</div>
    		<form action="" id="journalsheet-form-QueryFundAverageStatistics" method="post" >
    			<table class="querytable">
    				<tr>
    					<td>业务日期:</td>
    					<td><input id="begintime" name="begintime" class="Wdate" style="width:100px;" value="${firstDay}" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd',minDate:'1980-01-01',maxDate:'%y-%M-%ld'})"/>
    						 到 <input id="endtime" name="endtime" class="Wdate" style="width:100px;" value="${lastDay}" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd',minDate:'1980-01-01',maxDate:'%y-%M-%ld'})" />
    					</td>
    					<td>所属部门:</td>
    					<td><input id="journalsheet-widget-supplierdeptquery" name="supplierdeptid" type="text"/></td>
    				</tr>
    				<tr>
    					<td>供应商:</td>
    					<td><input id="journalsheet-widget-supplierquery" name="supplierid" type="text"/></td>
    					<td colspan="2">
    						<a href="javaScript:void(0);" id="journalsheet-query-FundAverageStatisticsList" class="button-qr">查询</a>
				    		<a href="javaScript:void(0);" id="journalsheet-reload-FundAverageStatisticsList" class="button-qr">重置</a>
    					</td>
    				</tr>
    			</table>
    		</form>
    	</div>
    	<div data-options="region:'center',border:false" style="border: 0px;">
    		<table id="journalsheet-table-FundAverageStatisticslist"></table>
    	</div>
    </div>
    <div id="journalsheet-dialog-averageDetail"></div>
    <script type="text/javascript">
    	//根据初始的列与用户保存的列生成以及字段权限生成新的列
    	var FundAverageStatisticsListColJson=$("#journalsheet-table-FundAverageStatisticslist").createGridColumnLoad({
	     	frozenCol:[[]],
	     	commonCol:[[
				{field:'id',title:'编码',width:80,sortable:true,hidden:true},
				{field:'supplierid',title:'供应商编码',width:70,sortable:true},
				{field:'supplierName',title:'供应商名称',width:210},
				{field:'supplierdeptid',title:'所属部门',width:100,sortable:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.supplierdeptName;
					}
				},
				//{field:'businessdate',title:'业务日期',width:80,sortable:true},
				{field:'advanceamount',title:'预付金额',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'stockamount',title:'库存金额',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'receivableamount',title:'应收金额',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'actingmatamount',title:'代垫金额',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'payableamount',title:'应付金额',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'totalamount',title:'合计金额',resizable:true,sortable:true,align:'right',isShow:true,
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'stockdiscount',title:'库存折差',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'norecactingmat',title:'代垫未收',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'norecexpenses',title:'费用未付',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'sumamount',title:'累计金额',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'currentactingmat',title:'本期代垫',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'sumactingmat',title:'累计代垫',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'remittancerecovery',title:'汇款收回',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'goodsrecovery',title:'货补收回',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'sumreceivable',title:'累计已收',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'sumnorec',title:'累计未收',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				}
			]]
	     });
	     
		$(function(){
			//供应商
		  	$("#journalsheet-widget-supplierquery").widget({
		  		width:225,
				referwid: 'RL_T_BASE_BUY_SUPPLIER_1',
				singleSelect:true
			});
			$("#journalsheet-widget-supplierdeptquery").widget({
		  		width:150,
				referwid: 'RL_T_BASE_DEPARTMENT_BUYER',
				singleSelect:true
			});
			
			//查询
			$("#journalsheet-query-FundAverageStatisticsList").click(function(){
	      		var queryJSON = $("#journalsheet-form-QueryFundAverageStatistics").serializeJSON();
	      		$("#journalsheet-table-FundAverageStatisticslist").datagrid({
	      			url:'journalsheet/statisticals/getFundAverageStatisticsSheetList.do',
	      			pageNumber:1,
	      			queryParams:queryJSON
	      		}).datagrid("columnMoving");
			});
			$("#journalsheet-reload-FundAverageStatisticsList").click(function(){
				$("#journalsheet-widget-supplierquery").widget("clear");
				$("#journalsheet-widget-supplierdeptquery").widget("clear");
				$("#journalsheet-form-QueryFundAverageStatistics")[0].reset();
				var queryJSON = $("#journalsheet-form-QueryFundAverageStatistics").serializeJSON();
	       		$("#journalsheet-table-FundAverageStatisticslist").datagrid('loadData',{total:0,rows:[]});
			});
			
			$("#journalsheet-buttons-fundAverageStatisticsPage").Excel('export',{
				queryForm: "#journalsheet-form-QueryFundAverageStatistics", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
		 		type:'exportUserdefined',
		 		name:'资金平均统计报表',
		 		url:'journalsheet/statisticals/exportFundAverageStatisticsData.do'
			});
			
			$("#journalsheet-table-FundAverageStatisticslist").datagrid({
				authority:FundAverageStatisticsListColJson,
	  	 		frozenColumns:[[]],
				columns:FundAverageStatisticsListColJson.common,
	  	 		fit:true,
	  	 		method:'post',
	  	 		title:'',
	  	 		showFooter: true,
	  	 		rownumbers:true,
	  	 		sortOrder:'asc',
	  	 		pagination:true,
	  	 		idField:'id',
	  	 		queryParams:{
					begintime: '${firstDay}',
					endtime: '${lastDay}'
				},
	  	 		singleSelect:true,
				pageSize:100,
				toolbar:'#journalsheet-button-QueryFundAverageStatistics',
			    onDblClickRow:function(rowIndex, rowData){
					var supplierid = rowData.supplierid;
					var suppliername = rowData.supplierName;
					var begintime = $("#begintime").val();
   					var endtime = $("#endtime").val();
   					var url = 'journalsheet/statisticals/fundAverageStatisticsSheetDetailPage.do?supplierid='+supplierid+'&begintime='+begintime+'&endtime='+endtime+'&suppliername='+suppliername;
					$("#journalsheet-dialog-averageDetail").dialog({
					    title: '供应商：['+rowData.supplierName+']资金平均统计报表',  
			    		width:800,
			    		height:400,
			    		closed:true,
			    		modal:true,
			    		maximizable:true,
			    		cache:false,
			    		resizable:true,
					    href: url
					});
					$("#journalsheet-dialog-averageDetail").dialog("open");
				}
			}).datagrid("columnMoving");
			
		});
    </script>
  </body>
</html>
