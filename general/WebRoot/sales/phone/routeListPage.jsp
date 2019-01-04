<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>业务员行程距离查询</title>
  	<%@include file="/include.jsp" %>  
  </head>
  <body>
  	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',border:false">
			<div class="buttonBG" id="sales-buttons-routeListPage"></div>
		</div>
		
		<div data-options="region:'center'">
		
		  	<table id="sales-datagrid-routeListPage"></table>
		  	<div id="sales-toolbar-routeListPage">
	  		<form id="sales-form-routeListPage" method="post">
	  			<table>
	  				<tr>
	  					<td>日期：</td>
	  					<td><input type="text" name="adddate" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
	  					<td>业务员：</td>
	  					<td><input id="sales-saler-routeListPage" name="userid" /></td>
	  					<td>
	  						<a href="javaScript:void(0);" id="sales-query-routeListPage" class="button-qr">查询</a>
							<a href="javaScript:void(0);" id="sales-reload-routeListPage" class="button-qr">重置</a>
	  					</td>
	  				</tr>
	  			</table>
	  		</form>
  			</div>
  		</div>
  	</div>
  	
  	<div id = "sales-routeListPage-dialog">	
  	<script type="text/javascript">
  		var initQueryJSON = $("#sales-form-routeListPage").serializeJSON();
  		$(function(){
  			$("#sales-saler-routeListPage").widget({
				name:'t_phone_route_distance',
    			col:'userid',
		    	width:130,
				singleSelect:true
			});
			$("#sales-query-routeListPage").click(function(){
	       		var queryJSON = $("#sales-form-routeListPage").serializeJSON();
	       		$("#sales-datagrid-routeListPage").datagrid('load', queryJSON);
			});
			$("#sales-reload-routeListPage").click(function(){
				$("#sales-saler-routeListPage").widget("clear");
				$("#sales-form-routeListPage").form("reset");
				var queryJSON = $("#sales-form-routeListPage").serializeJSON();
				$("#sales-datagrid-routeListPage").datagrid('load', queryJSON);
			});
			$("#sales-datagrid-routeListPage").datagrid({
				columns:[[
					{field:'id',title:'编号',width:60},
					{field:'username', title:'业务员', width:100},
					{field:'adddate', title:'日期', width:100},
					{field:'distancedesc', title:'行程距离', width:150}
				]],
				method:'post',
	  	 		title:'',
	  	 		fit:true,
	  	 		rownumbers:true,
	  	 		pagination:true,
	  	 		showFooter: true,
	  	 		singleSelect:true,
	  	 		pageSize:20,
	  	 		sortName:'adddate',
				sortOrder:'desc',
	  	 		url: 'sales/getRouteList.do',
				queryParams:initQueryJSON,
				toolbar:'#sales-toolbar-routeListPage',
				onDblClickRow:function(rowIndex, data){
					top.addOrUpdateTab('sales/routePage.do?t='+data.adddate+'&u='+ data.userid, "路线查询");
				}
			});
			
			
			
			
			$("#sales-buttons-routeListPage").buttonWidget({
				
				initButton:[  
				    {},       
				   
					{
						type:'button-add',
						handler: function(){
							addRoutDialog()
						}
					},
					{}
				]	
		   	})
			
			
  		});
  		
  		 function addRoutDialog(id,type){
			var $diaLog=$("#sales-routeListPage-dialog");
			parent.$.dialog=$diaLog;
			parent.$.dg=$("#sales-datagrid-routeListPage")
			$diaLog.dialog({
				title:'行程新增',
			    width: 540,  
			    height: 250,
			    closed: true,  
			    cache: false, 
			    modal: true,
			    resizable:true,
			    href:'sales/showRouteAddPage.do',
			    onLoad:function(){
			    }
			});
			$diaLog.dialog("open");
     	}
  		
  		
  		
  		
  		
  		
  		
  		
  		
  	</script>
  </body>
</html>
