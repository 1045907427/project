<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>客户发票对账单明细</title>
    <%@include file="/include.jsp" %>   
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>  
  </head>
  
  <body>
    	<table id="report-datagrid-wrieteAccountDetail"></table>
    	<div id="report-toolbar-wrieteAccountDetail"  style="padding: 0px" >
            <div class="buttonBG">
                <security:authorize url="/report/finance/wrieteAccountDetailExport.do">
                    <a href="javaScript:void(0);" id="report-buttons-wrieteAccountDetailPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
                </security:authorize>
            </div>
    		<form action="" id="report-query-form-wrieteAccountDetail" method="post">
    		<table class="querytable">
    			<tr>
    				<td>业务日期:</td>
    				<td><input type="text" id="report-query-businessdate1" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" id="report-query-businessdate2" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
    				<td>单据编号:</td>
    				<td><input type="text" style="width: 140px;" name="billid"/></td>
    				<td>单据类型:</td>
    				<td>
    					<select name="billtype" style="width: 130px;">
    						<option></option>
    						<option value="1">销售发货回单</option>
    						<option value="2">销售退货通知单</option>
    						<option value="3">冲差单</option>
    					</select>
    				</td>
    			</tr>
    			<tr>
    				<td>核销日期:</td>
    				<td><input type="text" id="report-query-writeoffdate1" name="writeoffdate1" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" id="report-query-writeoffdate2" name="writeoffdate2"  class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
    				<td>核销编号:</td>
    				<td><input type="text" style="width: 140px;" name="id"/></td>
    				<td>发 票 号:</td>
    				<td><input type="text" style="width: 130px;" name="invoiceid"/></td>
    			</tr>
    			<tr>
    				<td>总店名称:</td>
    				<td><input type="text" id="report-query-pcustomerid" name="pcustomerid"/></td>
                    <td>客户名称:</td>
                    <td><input type="text" id="report-query-customerid" name="customerid"/></td>
    				<td colspan="2">
    					<a href="javaScript:void(0);" id="report-queay-wrieteAccountDetail" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="report-reload-wrieteAccountDetail" class="button-qr">重置</a>
    				</td>
    			</tr>
    		</table>
    	</form>
    	</div>
    	<div id="report-invoicelist-dialog"></div>
    	<div id="account-panel-customerPushBanlance"></div>
    	<script type="text/javascript">
    		var initQueryJSON = $("#report-query-form-wrieteAccountDetail").serializeJSON();
    		$(function(){
    			var tableColumnListJson = $("#report-datagrid-wrieteAccountDetail").createGridColumnLoad({
					frozenCol : [[
				    			]],
					commonCol : [[
									{field:'billtype',title:'单据类型',width:80,sortable:true,
								  	formatter:function(value,rowData,rowIndex){
								  		if(value=="1"){
								  			return "销售发货回单";
								  		}else if(value=="2"){
								  			return "销售退货通知单";
								  		}else if(value=="3"){
								  			return "发票冲差单";
								  		}else if(value=="4"){
								  			return "正常冲差单";
								  		}
						        	}
								  },
								  {field:'billid',title:'单据编号',width:130,sortable:true},
								  {field:'id',title:'核销编号',width:130,sortable:true},
								  {field:'invoiceno',title:'发票号',width:100,sortable:true},
								  {field:'businessdate',title:'业务日期',width:80,sortable:true},
								  {field:'customerid',title:'客户编码',width:60,sortable:true},
							      {field:'customername',title:'客户简称',width:130,isShow:true},
								  {field:'pcustomerid',title:'总店名称',width:60,sortable:true,
								  	formatter:function(value,rowData,rowIndex){
								  		if(rowData.customerid!=rowData.pcustomerid){
						        			return rowData.pcustomername;
						        		}else{
						        			return "";
						        		}
						        	}
								  },  
								  {field:'goodsid',title:'商品编码',width:60,sortable:true},
								  {field:'goodsname',title:'商品名称',width:220},
								  {field:'barcode',title:'条形码',sortable:true,width:90},
								  {field:'taxamount',title:'应收金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'writeoffamount',title:'核销金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'writeoffdate',title:'核销日期',width:80},
								  {field:'writeoffusername',title:'核销人',width:80}
					             ]]
				});
    			$("#report-datagrid-wrieteAccountDetail").datagrid({ 
    				authority:tableColumnListJson,
			 		frozenColumns: tableColumnListJson.frozen,
					columns:tableColumnListJson.common,
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		singleSelect:true,
		  	 		pageSize:100,
					sortName:'businessdate',
					sortOrder:'asc',
					toolbar:'#report-toolbar-wrieteAccountDetail'
				}).datagrid("columnMoving");
				$("#report-query-customerid").widget({
					referwid:'RL_T_BASE_SALES_CUSTOMER',
		    		width:140,
					singleSelect:true
				});
				$("#report-query-pcustomerid").widget({
					referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
		    		width:225,
					singleSelect:true
				});

				//回车事件
				controlQueryAndResetByKey("report-queay-wrieteAccountDetail","report-reload-wrieteAccountDetail");
				
				//查询
				$("#report-queay-wrieteAccountDetail").click(function(){
					//把form表单的name序列化成JSON对象
		      		var queryJSON = $("#report-query-form-wrieteAccountDetail").serializeJSON();
		      		$("#report-datagrid-wrieteAccountDetail").datagrid({
		      			url: 'report/finance/showWriteAccountDetailData.do',
						queryParams:queryJSON,
		      			pageNumber:1
		      		}).datagrid("columnMoving");
				});
				//重置
				$("#report-reload-wrieteAccountDetail").click(function(){
					$("#report-query-customerid").widget("clear");
					$("#report-query-pcustomerid").widget("clear");
					$("#report-query-form-wrieteAccountDetail").form("reset");
					var queryJSON = $("#report-query-form-wrieteAccountDetail").serializeJSON();
		       		$("#report-datagrid-wrieteAccountDetail").datagrid({
		      			url: 'report/finance/showWriteAccountDetailData.do',
						queryParams:queryJSON,
		      			pageNumber:1
		      		}).datagrid("columnMoving");
				});

				$("#report-buttons-wrieteAccountDetailPage").Excel('export',{
					queryForm: "#report-query-form-wrieteAccountDetail", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:'客户开票对账明细',
			 		url:'report/finance/exportWrieteAccountDetailData.do'
				});
    		});
    	</script>
  </body>
</html>
