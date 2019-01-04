<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>每日行程报表</title>
  </head>
  
  <body>
    <table id="sales-datagrid-routeReportPage"></table>
    <script type="text/javascript">
    	$(function(){
    		$("#sales-datagrid-routeReportPage").datagrid({
				columns:[[
					{field:'userid',title:'编码',width:40},
					{field:'username', title:'业务员', width:80}
					<c:if test="${daylist != '' && daylist != null}">
						<c:forEach items="${daylist}" var="list" varStatus="status">
						,
						{field:'${list}', title:'${status.index+1}号',resizable:true,
							formatter:function(value,rowData,rowIndex){
								if(undefined != value){
									return value;
								}
				        	}
						}
						</c:forEach>
					</c:if>
				]],
				method:'post',
	  	 		title:'',
	  	 		fit:true,
	  	 		rownumbers:true,
	  	 		singleSelect:true
			});
    	});
    </script>
  </body>
</html>
