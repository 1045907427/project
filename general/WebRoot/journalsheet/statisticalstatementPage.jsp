<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>货款统计报表</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true">
    	<div id="finance-button-QueryStatistics">
    		<form action="" id="finance-form-QueryStatistics" method="post">
    			<table>
    				<tr>
    					<td>供应商:</td>
    					<td><input id="finance-widget-supplierquery" name="supplierid" type="text"/></td>
    					<td>所属部门:</td>
    					<td><input id="finance-widget-supplierdeptquery" name="supplierdeptid" type="text"/></td>
    				</tr>
    				<tr>
    					<td>业务日期:</td>
    					<td><input id="begintime" name="begintime" class="Wdate" style="width:80px;" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd',minDate:'1980-01-01',maxDate:'%y-%M-%ld'})"/>
   							到<input id="endtime" name="endtime" class="Wdate" style="width:80px;" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd',minDate:'1980-01-01',maxDate:'%y-%M-%ld'})" />
    					</td>
    					<!-- <td>查询版本:</td>
    					<td>
    						<select id="query-version-select" name="versionstr" style="width: 100px;">
    							<option value="0">当前数据</option>
    							<option value="1">历史数据</option>
    						</select>
    						<input type="hidden" id="query-version-hidden"/>
    					</td> -->
    					<td colspan="2">
    						<a href="javaScript:void(0);" id="finance-query-statisticsList" class="easyui-linkbutton" iconCls="icon-search" plain="true" title="[Alt+Q]查询">查询</a>
				    		<a href="javaScript:void(0);" id="finance-reload-statisticsList" class="easyui-linkbutton" iconCls="icon-reload" plain="true" title="[Alt+R]重置">重置</a>
			    			<security:authorize url="/journalsheet/statisticals/statisticsExport.do">
								<a href="javaScript:void(0);" id="journalsheet-buttons-statisticalsPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
							</security:authorize>
    					</td>
    				</tr>
    			</table>
    		</form>
    	</div>
    	<div data-options="region:'center',border:false" style="border: 0px;">
    		<table id="finance-table-statisticslist"></table>
    	</div>
    </div>
    <div id="finance-dialog-statisticslist"></div>
    <script type="text/javascript">
    	//根据初始的列与用户保存的列生成以及字段权限生成新的列
    	var expensesEnteringListColJson=$("#finance-table-statisticslist").createGridColumnLoad({
	     	name:'t_finance_expensesentering',
	     	frozenCol:[[]],
	     	commonCol:[[
				{field:'supplierid',title:'供应商编码',width:70,sortable:true},
				{field:'suppliername',title:'供应商名称',width:210},
				{field:'supplierdeptid',title:'所属部门',width:70,sortable:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.supplierdeptName;
					}
				},
				<c:forEach var="list" items="${codeList}">
	   			{field:'subjectid${list.code}',title:'${list.codename}',resizable:true,align:'right',isShow:true,
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}else{
							return "0.00";
						}
					}
				},
    			</c:forEach>
				{field:'lastAmount',title:'合计金额',resizable:true,align:'right',isShow:true,
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}else{
							return "0.00";
						}
					}
				},
				{field:'approvalAmount',title:'核准金额',resizable:true,align:'right',isShow:true,
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}else{
							return "0.00";
						}
					}
				},
				{field:'realatamount',title:'实际占用金额',resizable:true,align:'right',isShow:true,
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}else{
							return "0.00";
						}
					}
				},
				{field:'settleamount',title:'结算金额',resizable:true,align:'right',isShow:true,
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}else{
							return "0.00";
						}
					}
				}
			]]
	     });
	     
		$(function(){
			//供应商
		  	$("#finance-widget-supplierquery").widget({
		  		width:180,
				name:'t_finance_expensesentering',
				col:'supplierid',
				singleSelect:true
			});
			$("#finance-widget-supplierdeptquery").widget({
		  		width:120,
		  		name:'t_finance_expensesentering',
				col:'supplierdeptid',
				singleSelect:true
			});
			
			//回车事件
			controlQueryAndResetByKey("finance-query-statisticsList","finance-reload-statisticsList");
			
			//查询
			$("#finance-query-statisticsList").click(function(){
				//把form表单的name序列化成JSON对象
	      		var queryJSON = $("#finance-form-QueryStatistics").serializeJSON();
	      		var version = $("#query-version-select").val();
	      		$("#query-version-hidden").val(version);
	      		//调用datagrid本身的方法把JSON对象赋给queryParams 即可进行查询
	      		$("#finance-table-statisticslist").datagrid({
	      			url:'journalsheet/statisticals/getStatisticslist.do',
	      			pageNumber:1,
	      			queryParams:queryJSON
	      		}).datagrid("columnMoving");
			});
			//重置
			$("#finance-reload-statisticsList").click(function(){
				$("#finance-widget-supplierquery").widget("clear");
				$("#finance-widget-supplierdeptquery").widget("clear");
				$("#finance-form-QueryStatistics")[0].reset();
	       		$("#finance-table-statisticslist").datagrid('loadData',{total:0,rows:[]});
			});
			
			$("#journalsheet-buttons-statisticalsPage").Excel('export',{
				queryForm: "#finance-form-QueryStatistics", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
		 		type:'exportUserdefined',
		 		name:'货款统计报表',
		 		url:'journalsheet/statisticals/exportStatisticsData.do'
			});
			
			$("#finance-table-statisticslist").datagrid({ 
     			authority:expensesEnteringListColJson,
	  	 		frozenColumns:[[]],
				columns:expensesEnteringListColJson.common,
	  	 		method:'post',
	  	 		title:'',
	  	 		rownumbers:true,
	  	 		fit:true,
	  	 		pagination:true,
	  	 		showFooter: true,
	  	 		singleSelect:true,
				idField:'id',  
			    toolbar:'#finance-button-QueryStatistics',
			    onDblClickRow:function(rowIndex, rowData){
			    	var version = $("#query-version-hidden").val();
			    	$("#finance-dialog-statisticslist").dialog({
						title:"货款明细列表",
			    		width:800,
			    		height:400,
			    		closed:false,
			    		modal:true,
			    		maximizable:true,
			    		cache:false,
			    		resizable:true,
					    href: 'journalsheet/statisticals/showStatisticsDetaillistPage.do?id='+rowData.id+'&version='+version
			    	});
			    }
			}).datagrid("columnMoving");
			
		});
    </script>
  </body>
</html>
