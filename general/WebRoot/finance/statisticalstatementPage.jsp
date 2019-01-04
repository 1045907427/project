<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>统计报表</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'north'" style="height: 60px;">
    		<form action="" id="finance-form-QueryStatistics" method="post" style="padding: 5px;">
    			供&nbsp;应&nbsp;商:<input id="finance-widget-supplierquery" name="supplierid" type="text"/>
    			所属部门:<input id="finance-widget-supplierdeptquery" name="supplierdeptid" type="text"/><br />
    			业务日期：<input id="begintime" name="begintime" class="Wdate" style="width:100px;height: 20px;" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd',minDate:'1980-01-01',maxDate:'%y-%M-%ld'})"/>
   				到&nbsp<input id="endtime" name="endtime" class="Wdate" style="width:100px;height: 20px;" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd',minDate:'1980-01-01',maxDate:'%y-%M-%ld'})" />
	    		<a href="javaScript:void(0);" id="finance-query-statisticsList" class="easyui-linkbutton" iconCls="icon-search" title="查询">查询</a>
	    		<a href="javaScript:void(0);" id="finance-reload-statisticsList" class="easyui-linkbutton" iconCls="icon-reload" title="重置">重置</a>
    		</form>
    	</div>
    	<div data-options="region:'center',border:false" style="border: 0px;">
    		<table id="finance-table-statisticslist"></table>
    	</div>
    </div>
    <script type="text/javascript">
    	//根据初始的列与用户保存的列生成以及字段权限生成新的列
    	var expensesEnteringListColJson=$("#finance-table-statisticslist").createGridColumnLoad({
	     	name:'t_finance_expensesentering',
	     	frozenCol:[[]],
	     	commonCol:[[
				{field:'suppliername',title:'供应商编码',width:80,sortable:true,hidden:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.supplierid;
					}
				},
				{field:'supplierid',title:'供应商名称',width:120,sortable:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.suppliername;
					}
				},
				{field:'supplierdeptid',title:'所属部门',width:70,sortable:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.supplierdeptName;
					}
				},
				{field:'businessdate',title:'业务日期',width:75,sortable:true},
				<c:forEach var="list" items="${codeList}">
	   			{field:'subjectid${list.code}',title:'${list.codename}',width:90,align:'right',isShow:true,
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}else{
							return "0.00";
						}
					}
				},
    			</c:forEach>
				{field:'lastAmount',title:'合计金额',width:90,align:'right',isShow:true,
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}else{
							return "0.00";
						}
					}
				},
				{field:'approvalAmount',title:'核准金额',width:90,align:'right',isShow:true,
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}else{
							return "0.00";
						}
					}
				},
				{field:'realatamount',title:'实际占用金额',width:90,align:'right',isShow:true,
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}else{
							return "0.00";
						}
					}
				},
				{field:'settleamount',title:'结算金额',width:90,align:'right',isShow:true,
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
	     
	    //供应商
	  	$("#finance-widget-supplierquery").widget({
	  		width:300,
			name:'t_finance_expensesentering',
			col:'supplierid',
			singleSelect:true
		});
		$("#finance-widget-supplierdeptquery").widget({
	  		width:150,
	  		name:'t_finance_expensesentering',
			col:'supplierdeptid',
			singleSelect:true
		});
		$(function(){
			//查询
			$("#finance-query-statisticsList").click(function(){
				//把form表单的name序列化成JSON对象
	      		var queryJSON = $("#finance-form-QueryStatistics").serializeJSON();
	      		
	      		//调用datagrid本身的方法把JSON对象赋给queryParams 即可进行查询
	      		$("#finance-table-statisticslist").treegrid({pageNumber:1,queryParams:queryJSON}).datagrid("columnMoving");
			});
			$("#finance-reload-statisticsList").click(function(){
				$("#finance-widget-supplierquery").widget("clear");
				$("#finance-widget-supplierdeptquery").widget("clear");
				$("#finance-form-QueryStatistics")[0].reset();
	       		$("#finance-table-statisticslist").treegrid({pageNumber:1,queryParams:{}}).datagrid("columnMoving");
			});
			$("#finance-table-statisticslist").treegrid({ 
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
				pageSize:20,
				pageList:[10,20,30,50,200],
				idField:'id',  
			    treeField:'supplierid',
			    url:'basefiles/finance/getStatisticslist.do',
			    onLoadSuccess:function(row, data){
			    	if(data.footer==null && !row){
			    		$("#finance-table-statisticslist").treegrid("reloadFooter",{});
			    	}
			    },
			    onBeforeLoad: function(row,param){  
                    if (!row) {
                    	param.id = "";
                    }
                },
                onDblClickRow:function(row){
                	if(row.state=='closed'){
                		$("#finance-table-statisticslist").treegrid("expand",row.id);
                	}else{
                		$("#finance-table-statisticslist").treegrid("collapse",row.id);
                	}
                	
                }
			}).datagrid("columnMoving");
			
		});
    </script>
  </body>
</html>
