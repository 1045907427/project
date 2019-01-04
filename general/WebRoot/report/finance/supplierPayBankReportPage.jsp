<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>供应商收款分布统计报表</title>
    <%@include file="/include.jsp" %>   
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>  
  </head>
  
  <body>
    	<table id="report-datagrid-supplierPayBank"></table>
    	<div id="report-toolbar-supplierPayBank" style="padding: 0px">
            <div class="buttonBG">
                <security:authorize url="/report/finance/supplierPayBankExport.do">
                    <a href="javaScript:void(0);" id="report-buttons-supplierPayBankPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
                </security:authorize>
            </div>
    		<form action="" id="report-query-form-supplierPayBank" method="post">
    		<table>

    			<tr>
    				<td>业务日期:</td>
    				<td><input type="text" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
    				<td>供应商名称:</td>
    				<td><input type="text" id="report-query-supplierid" name="supplierid"/></td>
    				<td colspan="2">
    					<a href="javaScript:void(0);" id="report-queay-supplierPayBank" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="report-reload-supplierPayBank" class="button-qr">重置</a>

    				</td>
    			</tr>
    		</table>
    	</form>
    	</div>
    	<script type="text/javascript">
    		var initQueryJSON = $("#report-query-form-supplierPayBank").serializeJSON();
    		$(function(){
    			$("#report-datagrid-supplierPayBank").datagrid({ 
					columns:[[
								  {field:'supplierid',title:'供应商编号',width:80},
								  {field:'suppliername',title:'供应商名称',width:180},
								  {field:'amount',title:'付款总额',align:'right',resizable:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  }
								  <c:forEach items="${bankList }" var="list">
								  ,{field:'bank${list.id}',title:'${list.name}',align:'right',resizable:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  }
	    						  </c:forEach>
								  
					         ]],
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		pageSize:100,
		  	 		showFooter: true,
		  	 		singleSelect:true,
					toolbar:'#report-toolbar-supplierPayBank'
				});
				$("#report-query-supplierid").widget({
					referwid:'RL_T_BASE_BUY_SUPPLIER',
		    		width:180,
					singleSelect:true
				});
				
				//回车事件
				controlQueryAndResetByKey("report-queay-supplierPayBank","report-reload-supplierPayBank");
				
				//查询
				$("#report-queay-supplierPayBank").click(function(){
					//把form表单的name序列化成JSON对象
		      		var queryJSON = $("#report-query-form-supplierPayBank").serializeJSON();
		      		$("#report-datagrid-supplierPayBank").datagrid({
		      			url: 'report/finance/showSupplierPayBankListData.do',
						queryParams:queryJSON,
						pageNumber:1
		      		});
				});
				//重置
				$("#report-reload-supplierPayBank").click(function(){
					$("#report-query-supplierid").widget("clear");
					$("#report-query-form-supplierPayBank")[0].reset();
					var queryJSON = $("#report-query-form-supplierPayBank").serializeJSON();
		       		$("#report-datagrid-supplierPayBank").datagrid('loadData',{total:0,rows:[]});
				});
				
				$("#report-buttons-supplierPayBankPage").Excel('export',{
					queryForm: "#report-query-form-supplierPayBank", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:'供应商收款分布统计报表',
			 		url:'report/finance/exportSupplierPayBankData.do'
				});
				
    		});
    	</script>
  </body>
</html>
