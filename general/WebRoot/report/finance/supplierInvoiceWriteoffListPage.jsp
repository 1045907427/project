<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>分供应商开票核销情况统计表</title>
    <%@include file="/include.jsp" %>  
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>   
  </head>
  
  <body>
    	<table id="report-datagrid-supplierInvoiceWriteoff"></table>
    	<div id="report-toolbar-supplierInvoiceWriteoff" style="padding: 0px">
            <div class="buttonBG">
                <security:authorize url="/report/finance/supplierInvoiceWriteoffExport.do">
                    <a href="javaScript:void(0);" id="report-buttons-supplierInvoiceWriteoffPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
                </security:authorize>
            </div>
    		<form action="" id="report-query-form-supplierInvoiceWriteoff" method="post">
    		<table>

    			<tr>
    				<td>业务日期:</td>
    				<td><input type="text" name="businessdate1" id="report-query-businessdate1" value="${firstDay }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" name="businessdate2" id="report-query-businessdate2"  value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
    				<!-- <td>开票日期:</td>
    				<td>
    					<input type="text" name="invoicedate1" id="report-query-businessdate1" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" name="invoicedate2" id="report-query-businessdate2" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" />
    				</td> -->
    				<td>供应商:</td>
    				<td><input type="text" id="report-query-supplierid" name="supplierid"/></td>
    			</tr>
    			<tr>
    				<td>采购部门:</td>
    				<td><input type="text" id="report-query-buydeptid" name="buydeptid"/></td>
    				<td colspan="2">
    					<a href="javaScript:void(0);" id="report-queay-supplierInvoiceWriteoff" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="report-reload-supplierInvoiceWriteoff" class="button-qr">重置</a>
    				</td> 
    			</tr>
    		</table>
    	</form>
    	</div>
    	<div id="report-dialog-supplierInvoiceWriteoffFlowDetail"></div>
    	<script type="text/javascript">
    		var initQueryJSON = $("#report-query-form-supplierInvoiceWriteoff").serializeJSON();
    		$(function(){
    			$("#report-datagrid-supplierInvoiceWriteoff").datagrid({ 
					columns:[[
						 {field:'supplierid',title:'供应商编号',width:80},
						  {field:'suppliername',title:'供应商名称',width:130},
						  {field:'buydeptid',title:'采购部门',width:80,
						  	formatter:function(value,rowData,rowIndex){
				        		return rowData.buydeptname;
				        	}
						  },
						  {field:'buyamount',title:'进货金额',resizable:true,sortable:true,align:'right',
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'returnamount',title:'退货金额',resizable:true,sortable:true,align:'right',
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'realpayamount',title:'采购总额',resizable:true,align:'right',sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'invoiceamount',title:'开票金额',resizable:true,align:'right',sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'prepayamount',title:'当前余额',resizable:true,align:'right',sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'payamount',title:'已核销金额',align:'right',resizable:true,isShow:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'unpayamount',title:'未核销金额',align:'right',resizable:true,isShow:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  //{field:'prepaybalance',title:'预付余额',align:'right',resizable:true,isShow:true,
						  //	formatter:function(value,rowData,rowIndex){
				          //		return formatterMoney(value);
				          //	}
						  //},
						  {field:'payableamount',title:'当前应付金额',align:'right',resizable:true,isShow:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'totalpayableamount',title:'应付总额',align:'right',resizable:true,hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				         		return formatterMoney(value);
				        	}
						  }
			         ]],
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		singleSelect:true,
		  	 		pageSize:100,
					toolbar:'#report-toolbar-supplierInvoiceWriteoff',
					onDblClickRow:function(rowIndex, rowData){
						var supplierid = rowData.supplierid;
						var suppliername = rowData.suppliername ||"";
						var businessdate1 = $("#report-query-businessdate1").val();
						var businessdate2 = $("#report-query-businessdate2").val();
						var url = 'report/finance/showSupplierPaymentFlowDetailPage.do?supplierid='+supplierid+'&businessdate1='+businessdate1+'&businessdate2='+businessdate2+'&suppliername='+suppliername;
						$("#report-dialog-supplierInvoiceWriteoffFlowDetail").dialog({
							title:'按供应商:['+rowData.suppliername+']统计',
				    		width:800,
				    		height:400,
				    		closed:true,
				    		modal:true,
				    		maximizable:true,
				    		cache:false,
				    		resizable:true,
				    		maximized:true,
						    href: url
						});
						$("#report-dialog-supplierInvoiceWriteoffFlowDetail").dialog("open");
				  }
				}).datagrid("columnMoving");
				$("#report-query-supplierid").widget({
					referwid:'RL_T_BASE_BUY_SUPPLIER',
		    		width:210,
					singleSelect:true
				});
				$("#report-query-buydeptid").widget({
					referwid:'RL_T_BASE_DEPARTMENT_BUYER',
		    		width:130,
					singleSelect:true
				});
				//回车事件
				controlQueryAndResetByKey("report-queay-supplierInvoiceWriteoff","report-reload-supplierInvoiceWriteoff");
				
				//查询
				$("#report-queay-supplierInvoiceWriteoff").click(function(){
					//把form表单的name序列化成JSON对象
		      		var queryJSON = $("#report-query-form-supplierInvoiceWriteoff").serializeJSON();
		      		$("#report-datagrid-supplierInvoiceWriteoff").datagrid({
		      			url: 'report/finance/getSupplierInvoiceWriteoffData.do',
						queryParams:queryJSON,
						pageNumber:1
		      		});
				});
				//重置
				$("#report-reload-supplierInvoiceWriteoff").click(function(){
					$("#report-query-supplierid").widget("clear");
					$("#report-query-buydeptid").widget("clear");
					$("#report-query-form-supplierInvoiceWriteoff")[0].reset();
					var queryJSON = $("#report-query-form-supplierInvoiceWriteoff").serializeJSON();
		       		$("#report-datagrid-supplierInvoiceWriteoff").datagrid('loadData',{total:0,rows:[]});
				});
				
				$("#report-buttons-supplierInvoiceWriteoffPage").Excel('export',{
					queryForm: "#report-query-form-supplierInvoiceWriteoff", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:'分供应商开票核销情况统计表',
			 		url:'report/finance/exportSupplierInvoiceWriteoffData.do'
				});
				
    		});
    	</script>
  </body>
</html>
