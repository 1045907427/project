<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>按客户尾差统计报表</title>
    <%@include file="/include.jsp" %>   
  </head>
  
  <body>
    	<table id="report-datagrid-salesTailamount"></table>
    	<div id="report-toolbar-salesTailamount">
    		<form action="" id="report-query-form-salesTailamount" method="post">
	    		<table class="querytable">
                    <tr>
                        <security:authorize url="/report/sales/salesTailamountReportExport.do">
                            <a href="javaScript:void(0);" id="report-buttons-salesTailamountPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
                        </security:authorize>
                    </tr>
	    			<tr>
	    				<td>核销日期:</td>
	    				<td><input type="text" id="report-query-writeoffdate1" name="writeoffdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" id="report-query-writeoffdate2" name="writeoffdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
	    				<td>销售区域:</td>
	    				<td><input id="report-query-salesarea" type="text" name="salesarea" style="width: 130px;"/></td>
                        <td>客户名称:</td>
                        <td><input type="text" id="report-query-customerid" name="customerid" style="width: 150px;"/></td>
                        </tr>
	    			<tr>
                        <td>业务日期:</td>
                        <td><input type="text" id="report-query-businessdate1" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" id="report-query-businessdate2" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
                        <td>客户业务员:</td>
                        <td><input id="report-query-salesuser" type="text" name="salesuser" style="width: 130px;"/></td>
                        <td colspan="2"  class="tdbutton" >
                            <a href="javaScript:void(0);" id="report-queay-salesTailamount" class="button-qr">查询</a>
                            <a href="javaScript:void(0);" id="report-reload-salesTailamount" class="button-qr">重置</a>
                        </td>
                    </tr>
	    		</table>
	    	</form>
    	</div>
    	<div id="report-dialog-salesTailamountDetail"></div>
    	<script type="text/javascript">
    		var initQueryJSON = $("#report-query-form-salesTailamount").serializeJSON();
    		$(function(){
    			var tableColumnListJson = $("#report-datagrid-salesTailamount").createGridColumnLoad({
					frozenCol : [[
				    			]],
					commonCol : [[
								  {field:'customerid',title:'客户编号',sortable:true,width:60},
								  {field:'customername',title:'客户名称',width:210},
								  {field:'salesusername',title:'客户业务员',width:70},
								  {field:'salesareaname',title:'销售区域',width:70},
								  {field:'salesdept',title:'销售部门',sortable:true,width:70,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.salesdeptname;
						        	}
								  },
								  {field:'tailamount',title:'尾差金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  }
							 ]]
				});
    			$("#report-datagrid-salesTailamount").datagrid({ 
			 		authority:tableColumnListJson,
			 		frozenColumns: tableColumnListJson.frozen,
					columns:tableColumnListJson.common,
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		idField:'customerid',
		  	 		sortName:'customerid',
		  	 		sortOrder:'asc',
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		singleSelect:true,
		  	 		pageSize:100,
					toolbar:'#report-toolbar-salesTailamount'
				}).datagrid("columnMoving");
				$("#report-query-customerid").customerWidget({});
				
				$("#report-query-salesarea").widget({
					referwid:'RT_T_BASE_SALES_AREA',
					width:130,
					onlyLeafCheck:false,
					singleSelect:true
				});

				$("#report-query-salesuser").widget({
					referwid:'RL_T_BASE_PERSONNEL_CLIENTSELLER',
					width:130,
					singleSelect:true
				});
				
				//回车事件
				controlQueryAndResetByKey("report-queay-salesTailamount","report-reload-salesTailamount");
				
				//查询
				$("#report-queay-salesTailamount").click(function(){
					//把form表单的name序列化成JSON对象
		      		var queryJSON = $("#report-query-form-salesTailamount").serializeJSON();
		      		$("#report-datagrid-salesTailamount").datagrid({
		      			url: 'account/receivable/getTailamountReportPageData.do',
		      			pageNumber:1,
						queryParams:queryJSON
		      		});
				});
				//重置
				$("#report-reload-salesTailamount").click(function(){
					$("#report-query-customerid").customerWidget("clear");
					$("#report-query-salesarea").widget("clear");
					$("#report-query-salesuser").widget("clear");
					$("#report-query-form-salesTailamount").form("reset");
					var queryJSON = $("#report-query-form-salesTailamount").serializeJSON();
		       		$("#report-datagrid-salesTailamount").datagrid('loadData',{total:0,rows:[],footer:[]});
				});
				
				$("#report-buttons-salesTailamountPage").Excel('export',{
					queryForm: "#report-query-form-salesTailamount", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:'分客户尾差情况统计报表',
			 		url:'account/receivable/exportTailamountReportData.do'
				});
    		});
    	</script>
  </body>
</html>
