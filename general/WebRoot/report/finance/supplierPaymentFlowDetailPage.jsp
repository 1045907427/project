<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>按供应商付款流水页面</title>
    <%@include file="/include.jsp" %>
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>     
  </head>
  
  <body>
  	<form action="" id="report-query-form-supplierPaymentFlowDetail" method="post">
		<input type="hidden" name="supplierid" value="${supplierid}"/>
		<input type="hidden" name="suppliername" value="${suppliername}"/>
		<input type="hidden" name="businessdate1" value="${businessdate1}"/>
		<input type="hidden" name="businessdate2" value="${businessdate2}"/>
	</form>
	<div id="report-toolbar-supplierPaymentFlowDetail" class="buttonBG">
		<security:authorize url="/report/finance/supplierPaymentsExport.do">
			<a href="javaScript:void(0);" id="report-export-supplierPaymentFlowDetail" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
		</security:authorize>
	</div>
	<div id="tt" class="easyui-tabs" data-options="fit:true">
		<div title="应付款流水明细" style="padding:2px;">
			<table id="report-datagrid-supplierPaymentFlowDetail-flow"></table>
		</div>
		<div title="应付款明细" style="padding:2px;">
			<table id="report-datagrid-supplierPaymentFlowDetail"></table>
		</div>
	</div>
	<script type="text/javascript">
		$('#tt').tabs({
			tools:'#report-toolbar-supplierPaymentFlowDetail'
		});
		var initQueryJSON = $("#report-query-form-supplierPaymentFlowDetail").serializeJSON();
		$(function(){
			//应付款流水明细
			$("#report-datagrid-supplierPaymentFlowDetail-flow").datagrid({
				columns:[[
					{field:'businessdate',title:'业务日期',width:100},
					{field:'billtype',title:'单据类型',width:80,
						formatter:function(value,rowData,rowIndex){
							if(value=='1'){
								return "采购入库单";
							}else if(value=='2'){
								return "采购退货出库单";
							}
						}
					},
					{field:'id',title:'单据编号',width:130},
					{field:'goodsid',title:'商品编码',width:80},
					{field:'goodsname',title:'商品名称',width:120},
					{field:'unitname',title:'单位',width:40},
					{field:'auxunitnumdetail',title:'辅数量',width:80,align:'right'},
					{field:'unitnum',title:'数量',width:60,align:'right',
						formatter:function(value,rowData,rowIndex){
							return formatterBigNumNoLen(value);
						}
					},
					{field:'price',title:'单价',width:60,align:'right',
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},
					{field:'taxamount',title:'金额',resizable:true,align:'right',
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
						}
					},
					{field:'isinvoice',title:'开票状态',align:'right',width:80,
						formatter:function(value,rowData,rowIndex){
							if(rowData.isinvoice!=null){
								if(rowData.isinvoice=='1'){
									return "已开票";
								}else if(rowData.isinvoice=='0'){
									return "未开票";
								}
							}
						}
					},
					{field:'iswriteoff',title:'核销状态',align:'right',width:80,isShow:true,
						formatter:function(value,rowData,rowIndex){
							if(rowData.iswriteoff!=null){
								if(rowData.iswriteoff=='1'){
									return "已核销";
								}else if(rowData.iswriteoff=='0'){
									return "未核销";
								}
							}
						}
					},
					{field:'remark',title:'备注',align:'right',width:80}
				]],
				method:'post',
				fit:true,
				rownumbers:true,
				singleSelect:true,
				pageSize:100,
				pagination:true,
				showFooter: true,
				url:'report/finance/showSupplierPaysFlowListData.do',
				queryParams:initQueryJSON
			}).datagrid("columnMoving");

			//应付款明细
			$("#report-datagrid-supplierPaymentFlowDetail").datagrid({
				columns:[[
					{field:'businessdate',title:'业务日期',width:100},
					{field:'billtype',title:'单据类型',width:100,
						formatter:function(value,rowData,rowIndex){
							return rowData.billtypename;
						}
					},
					{field:'id',title:'单据编号',width:130},
					{field:'initunpayamount',title:'期初金额',resizable:true,sortable:true,align:'right',
						formatter:function(value,rowData,rowIndex){
							return formatterMoney(value);
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
					{field:'pushbalanceamount',title:'冲差金额',resizable:true,align:'right',sortable:true,
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
					{field:'remark',title:'备注',align:'right',width:80}
				]],
				method:'post',
				fit:true,
				rownumbers:true,
				singleSelect:true,
				pageSize:100,
				pagination:true,
				showFooter: true,
				url:'report/finance/getSupplierPaymentDetailList.do',
				queryParams:initQueryJSON
			}).datagrid("columnMoving");

			$("#report-export-supplierPaymentFlowDetail").Excel('export',{
				queryForm: "#report-query-form-supplierPaymentFlowDetail",
				type:'exportUserdefined',
				name:'按供应商：[${suppliername}]统计',
				url:'report/finance/exportSupplierPaymentDetailReportData.do'
			});
		});
	</script>
  </body>
</html>
