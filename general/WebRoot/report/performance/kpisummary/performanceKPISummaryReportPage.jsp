<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>部门年度考核汇总报表页面</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>  
  </head>
  <body>
  	<div id="performance-table-kpiSummaryReportBtn" style="padding:2px;height:auto">
		<form action="" id="performance-form-kpiSummaryReportListQuery" method="post">
 			<table class="querytable">
                <tr>
                    <security:authorize url="/report/performance/exportPerformanceKPISummaryReportDataBtn.do">
                        <a href="javaScript:void(0);" id="performance-kpiSummaryReportList-export-btn" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
                        <a href="javaScript:void(0);" id="performance-kpiSummaryReportList-export-excel" style="display:none">导出</a>
                    </security:authorize>
                </tr>
 				<tr>
 					<td>年份:</td>
    				<td>
						<input id="performance-kpiSummaryReport-query-year" type="text" name="year" style="width:80px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy',maxDate:'${currentyear }'})" value="${currentyear }"/>
						月份:
						<select id="performance-kpiSummaryReport-query-month" name="month" style="width:48px;">
							<option value="">全部</option>
							<option value="1">1月</option>
							<option value="2">2月</option>
							<option value="3">3月</option>
							<option value="4">4月</option>
							<option value="5">5月</option>
							<option value="6">6月</option>
							<option value="7">7月</option>
							<option value="8">8月</option>
							<option value="9">9月</option>
							<option value="10">10月</option>
							<option value="11">11月</option>
							<option value="12">12月</option>
						</select>
    				</td>
 					<td>供应商:&nbsp;</td>
 					<td><input id="performance-widget-kpiSummaryReport-supplierid" name="supplierid" type="text" style="width: 150px;"/></td>
					<td></td>
 				</tr>
				<tr>
					<td style="width:80px">品牌部门:&nbsp;</td>
					<td>
						<input id="performance-widget-kpiSummaryReport-deptid" name="deptid" type="text" style="width: 170px;"/>
					</td>
					<td>销售部门:&nbsp;</td>
					<td><input id="performance-widget-kpiSummaryReport-salesdeptid" name="salesdeptid" type="text" style="width: 150px;"/></td>
					<td>
						<a href="javaScript:void(0);" id="performance-query-kpiSummaryReportList" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="performance-query-kpiSummaryReport-reloadList" class="button-qr">重置</a>
					</td>
				</tr>
 			</table>
 		</form>
	</div>
	
	<table id="performance-table-kpiSummaryReport"></table>
	<div style="display:none">
		<div id="performance-dialog-add-operate"></div>
		<div id="performance-dialog-edit-operate"></div>
		<div id="performance-dialog-view-operate"></div>
		<a href="javaScript:void(0);" id="performance-kpisummary-buttons-exportclick" style="display: none"title="导出">导出</a>
	</div>
   	 
    <script type="text/javascript">
		var footerobject=null;
		
		//根据初始的列与用户保存的列生成以及字段权限生成新的列
    	var costsFeeListColJson=$("#performance-table-kpiSummaryReport").createGridColumnLoad({
	     	//name:'t_report_performance_kpisummary',
	     	frozenCol:[[
				//{field:'idok',checkbox:true,isShow:true}
	     	]],
	     	commonCol:[[
				{field:'subject',title:'月份',isShow:true,
				 	formatter:function(value,rowData,rowIndex){
						return rowData.subjectname;
					}
				 },
				<c:forEach var="month" begin="1" end="12">
				 {field:'month_${month }',title:'${month}月',align:'right',resizable:true,width:75,
				 	formatter:function(value,rowData,rowIndex){
						//return formatterMoney(value);
						return value;
					}
				 },
				</c:forEach>
				{field:'totalamount',title:'合计',resizable:true,sortable:true,align:'right',isShow:true,
					formatter:function(value,rowData,rowIndex){
						//return formatterMoney(value);
						return value;
					}
				}			
			]]
	     });
	     
		$(function(){
		
			var initQueryJSON = $("#performance-form-kpiSummaryReportListQuery").serializeJSON()
     		$("#performance-table-kpiSummaryReport").datagrid({ 
     			authority:costsFeeListColJson,
	  	 		frozenColumns:costsFeeListColJson.frozen,
				columns:costsFeeListColJson.common,
	  	 		fit:true,
	  	 		method:'post',
	  	 		title:'',
	  	 		showFooter: true,
	  	 		rownumbers:true,
	  	 		sortName:'businessdate',
	  	 		sortOrder:'asc',
	  	 		pagination:true,
		 		idField:'id',
	  	 		singleSelect:true,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
		 		queryParams:initQueryJSON,
				pageSize:100,
				toolbar:'#performance-table-kpiSummaryReportBtn',
				loadMsg:'正在计算实时报表数据，请耐心等待！',
				//url: 'report/performance/showPerformanceKPISummaryReportData.do',
				onLoadSuccess:function(){
		 		}
			}).datagrid("columnMoving");
            $("#performance-widget-kpiSummaryReport-deptid").widget({
                width:170,
                name:'t_report_performance_kpisummary',
                col:'deptid',
                singleSelect:true,
                onlyLeafCheck:false
            });
            $("#performance-widget-kpiSummaryReport-salesdeptid").widget({
                width:150,
                referwid:'RL_T_BASE_DEPARTMENT_SELLER',
                singleSelect:true,
                onlyLeafCheck:false
            });

     		//查询
			$("#performance-query-kpiSummaryReportList").click(function(){
				var year=$("#performance-kpiSummaryReport-query-year").val()||"";
				if(null==year || ""==$.trim(year)){
					$.messager.alert("提醒","请选择要查询的年份!");
					return false;
				}
				var month=$("#performance-kpiSummaryReport-query-month").val() || "";
				for(var i=1; i<=12;i++){
					if(month!="" && 1<=month && month<=12){
						if(month==i){
					        $("#performance-table-kpiSummaryReport").datagrid('showColumn', "month_"+i);							
						}else{
					        $("#performance-table-kpiSummaryReport").datagrid('hideColumn', "month_"+i);							
						}
					}else{
				        $("#performance-table-kpiSummaryReport").datagrid('showColumn', "month_"+i);
					}
				}
	      		var queryJSON = $("#performance-form-kpiSummaryReportListQuery").serializeJSON();
	      		$("#performance-table-kpiSummaryReport").datagrid({
	      			url: 'report/performance/showPerformanceKPISummaryReportData.do',
	      			pageNumber:1,
					queryParams:queryJSON
	      		}).datagrid("columnMoving");
			});
			
			//重置按钮
			$("#performance-query-kpiSummaryReport-reloadList").click(function(){
				$("#performance-form-kpiSummaryReportListQuery")[0].reset();
				$("#performance-widget-kpiSummaryReport-deptid").widget('clear');
				$("#performance-widget-kpiSummaryReport-salesdeptid").widget('clear');
				$("#performance-kpiSummaryReport-query-groupcols").combobox('clear');
	      		$("#performance-table-kpiSummaryReport").datagrid('loadData',{total:0,rows:[]});
			});

			$("#performance-kpiSummaryReport-query-groupcols").combobox({
				multiple:true,
				editable:false
			});

			$("#performance-kpiSummaryReportList-export-btn").click(function(){
				var year=$("#performance-kpiSummaryReport-query-year").val()||"";
				if(null==year || ""==$.trim(year)){
					$.messager.alert("提醒","请选择要查询的年份!");
					return false;
				}
				var deptname=$("#performance-widget-kpiSummaryReport-deptid").widget("getText")|| "" ;
				var title="部门年度考核汇总报表";
				if($.trim(year)!=""){
					year=year+"年 ";
				}else{
					year="";
				}
				if($.trim(deptname)==""){
					deptname="";
				}
				title=year+$.trim(deptname)+title;
				$.messager.alert("提醒","部门年度考核汇总报表导出时，请耐心等待");
				$("#performance-kpiSummaryReportList-export-excel").Excel('export',{
					queryForm: "#performance-form-kpiSummaryReportListQuery", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:title,
			 		url:'report/performance/exportPerformanceKPISummaryReportData.do'
				});
				$("#performance-kpiSummaryReportList-export-excel").trigger("click");
			});
			

			$("#performance-widget-kpiSummaryReport-supplierid").supplierWidget({ 
				name:'t_purchase_buyorder',
				col:'supplierid',
				singleSelect:true,
				onlyLeafCheck:true
			});
		});
    </script>
  </body>
</html>
