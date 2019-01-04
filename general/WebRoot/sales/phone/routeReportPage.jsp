<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>行程报表</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
  	<div class="easyui-layout" data-options="fit:true,border:false">
  		<div data-options="region:'north',split:false,border:false" style="height: 62px;overflow: hidden">
  			<form id="sales-form-routeReportPage" method="post">
	  			<table>
                    <tr>
                        <security:authorize url="/report/sales/baseSalesReportExport.do">
                            <a href="javaScript:void(0);" id="sales-export-routeReportPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
                        </security:authorize>
                    </tr>
	  				<tr>
	  					<td>日期：</td>
	  					<td><input id="businessdate"  class="Wdate" name="businessdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM',maxDate:'${today }',onpicked:panelRefresh})" value="${today }"/></td>
	  					<td>业务员：</td>
	  					<td><input id="sales-saler-routeReportPage" name="userid" /></td>
	  					<td>
	  						<a href="javaScript:void(0);" id="sales-query-routeReportPage" class="button-qr">查询</a>
							<a href="javaScript:void(0);" id="sales-reload-routeReportPage" class="button-qr">重置</a>
	  					</td>
	  				</tr>
	  			</table>
	  		</form>
  		</div>
  		<div data-options="region:'center'" style="border: 0px;">
 			<div class="easyui-panel" data-options="fit:true" id="sales-pannel-routeReportPage"></div>
 		</div>
  	</div>
    <script type="text/javascript">
    	//页面刷新
	   	function panelRefresh(businessdate){
	   		var businessdate = $("#businessdate").val();
	   		$("#sales-pannel-routeReportPage").panel({
				href:'sales/showRouteDetailReportPage.do?businessdate='+businessdate,
				title:'',
			    cache:false,
			    maximized:true,
			    border:false,
			    loadingMessage:'数据加载中...'
			});
	   	}
	   	
    	$(function(){
    		$("#sales-saler-routeReportPage").widget({
				name:'t_phone_route_distance',
    			col:'userid',
		    	width:130,
				singleSelect:true
			});
			
			$("#sales-query-routeReportPage").click(function(){
				var queryJSON = $("#sales-form-routeReportPage").serializeJSON();
	      		$("#sales-datagrid-routeReportPage").datagrid({
	      			url: 'sales/getRouteReportList.do',
	      			pageNumber:1,
					queryParams:queryJSON
	      		});
			});
			$("#sales-reload-routeReportPage").click(function(){
				$("#sales-saler-routeReportPage").widget('clear');
				$("#sales-form-routeReportPage").form("reset");
				panelRefresh();
				$("#sales-datagrid-routeReportPage").datagrid('loadData', []);
			});
			
			//导出
			$("#sales-export-routeReportPage").Excel('export',{
				queryForm: "#sales-form-routeReportPage",
		 		type:'exportUserdefined',
		 		name:'每日行程报表',
		 		url:'sales/exportRouteReportData.do'
			});
			
			panelRefresh();
    	});
    </script>
  </body>
</html>
