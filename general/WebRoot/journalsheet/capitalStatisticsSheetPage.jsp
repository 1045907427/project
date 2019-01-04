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
    	<div style="height: 60px;" id="journalsheet-button-QueryCapitalStatistics">
    		<form action="" id="journalsheet-form-QueryCapitalStatistics" method="post">
    			<table cellpadding="0" cellspacing="1" border="0">
    				<tr>
    					<td style="padding-left: 10px;">供&nbsp;应&nbsp;商:&nbsp;</td>
    					<td><input id="journalsheet-widget-supplierquery" name="supplierid" type="text"/></td>
    					<td style="padding-left: 10px;">所属部门:&nbsp;</td>
    					<td><input id="journalsheet-widget-supplierdeptquery" name="supplierdeptid" type="text"/></td>
    				</tr>
    				<tr>
    					<td style="padding-left: 10px;">业务日期:&nbsp;</td>
    					<td><input id="begintime" name="begintime" class="Wdate" style="width:100px;height: 20px;" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd',minDate:'1980-01-01',maxDate:'%y-%M-%ld'})"/></td>
    					<td>到&nbsp<input id="endtime" name="endtime" class="Wdate" style="width:100px;height: 20px;" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd',minDate:'1980-01-01',maxDate:'%y-%M-%ld'})" /></td>
    					<td style="padding-left: 10px;">
    						<a href="javaScript:void(0);" id="journalsheet-query-capitalStatisticsList" class="easyui-linkbutton" iconCls="icon-search" title="[Alt+Q]查询">查询</a>
	    					<a href="javaScript:void(0);" id="journalsheet-reload-capitalStatisticsList" class="easyui-linkbutton" iconCls="icon-reload" title="[Alt+R]重置">重置</a>
    					</td>
    				</tr>
    			</table>
    		</form>
    	</div>
    	<div data-options="region:'center',border:false" style="border: 0px;">
    		<table id="journalsheet-table-capitalStatisticslist"></table>
    	</div>
    </div>
    <script type="text/javascript">
    	//根据初始的列与用户保存的列生成以及字段权限生成新的列
    	var capitalinputListColJson=$("#journalsheet-table-capitalStatisticslist").createGridColumnLoad({
	     	name:'t_js_capitalinput',
	     	frozenCol:[[]],
	     	commonCol:[[
				{field:'supplierid',title:'供应商编码',width:70,sortable:true},
				{field:'suppliername',title:'供应商名称',width:350,sortable:true,isShow:true},
				{field:'supplierdeptid',title:'所属部门',width:70,sortable:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.supplierdeptName;
					}
				},
				{field:'businessdate',title:'业务日期',width:75,sortable:true},
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
				}
			]]
	     });
	     
		$(function(){
			//供应商
		  	$("#journalsheet-widget-supplierquery").widget({
		  		width:300,
				name:'t_js_capitalinput',
				col:'supplierid',
				singleSelect:true
			});
			$("#journalsheet-widget-supplierdeptquery").widget({
		  		width:150,
		  		name:'t_js_capitalinput',
				col:'supplierdeptid',
				singleSelect:true
			});
			
			//回车事件
			controlQueryAndResetByKey("journalsheet-query-capitalStatisticsList","journalsheet-reload-capitalStatisticsList");
			
			//查询
			$("#journalsheet-query-capitalStatisticsList").click(function(){
				//把form表单的name序列化成JSON对象
	      		var queryJSON = $("#journalsheet-form-QueryCapitalStatistics").serializeJSON();
	      		
	      		//调用datagrid本身的方法把JSON对象赋给queryParams 即可进行查询
	      		$("#journalsheet-table-capitalStatisticslist").treegrid({
	      			url:'journalsheet/statisticals/getCapitalStatisticsSheetList.do',
	      			pageNumber:1,
	      			queryParams:queryJSON
	      		}).datagrid("columnMoving");
			});
			$("#journalsheet-reload-capitalStatisticsList").click(function(){
				$("#journalsheet-widget-supplierquery").widget("clear");
				$("#journalsheet-widget-supplierdeptquery").widget("clear");
				$("#journalsheet-form-QueryCapitalStatistics")[0].reset();
	       		$("#journalsheet-table-capitalStatisticslist").treegrid('loadData',{total:0,rows:[]});
			});
			$("#journalsheet-table-capitalStatisticslist").treegrid({ 
     			authority:capitalinputListColJson,
	  	 		frozenColumns:[[]],
				columns:capitalinputListColJson.common,
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
			    toolbar:'#journalsheet-button-QueryCapitalStatistics',
			    onLoadSuccess:function(row, data){
			    	if(data.footer==null && !row){
			    		$("#journalsheet-table-capitalStatisticslist").treegrid("reloadFooter",{});
			    	}
			    },
			    onBeforeLoad: function(row,param){  
                    if (!row) {
                    	param.id = "";
                    }
                },
                onDblClickRow:function(row){
                	if(row.state=='closed'){
                		$("#journalsheet-table-capitalStatisticslist").treegrid("expand",row.id);
                	}else{
                		$("#journalsheet-table-capitalStatisticslist").treegrid("collapse",row.id);
                	}
                	
                }
			}).datagrid("columnMoving");
			
		});
    </script>
  </body>
</html>
