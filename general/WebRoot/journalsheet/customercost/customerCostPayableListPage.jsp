<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>客户应付费用统计页面</title>
	<%@include file="/include.jsp" %>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
	<div id="customercost-table-payableBtn" style="padding-top: 0px;">
		<div class="buttonBG">
			<security:authorize url="/journalsheet/customercost/exportCustomerCostPayableListData.do">
				<a href="javaScript:void(0);" id="journalsheet-export-customerCostPayableListPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
			</security:authorize>
		</div>
		<form id="customercost-form-ListQuery" method="post">
			<input type="hidden" id="customercost-query-reporttype" name="reporttype" value="${param.reporttype }"/>
			<table class="querytable">
				<tr>
					<td>业务日期：</td>
					<td>
						<input type="text" id="customercost-query-begindate" name="begindate" class="Wdate len100" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="" />
						到 <input type="text" id="customercost-query-enddate" name="enddate" class="Wdate len100" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" />
					</td>
					<td>销售区域：</td>
					<td>
						<input id="customercost-query-salesarea" type="text" name="salesarea" style="width: 130px;"/>
					</td>
					<td>客户业务员：</td>
					<td>
						<input id="customercost-query-salesuser" type="text" name="salesuserid" style="width: 130px;"/>
					</td>
					<td style="width: 165px;"></td>
				</tr>
				<tr>
					<td>客&nbsp;&nbsp;户：</td>
					<td>
						<input id="customercost-query-customerid" type="text" name="customerid" style="width: 225px;"/>
					</td>
					<td>品牌部门：</td>
					<td>
						<input id="customercost-query-salesdept" type="text" name="branddeptid" style="width: 130px;"/>
					</td>
					<td>供&nbsp;应&nbsp;商：</td>
					<td>
						<input id="customercost-query-supplierid" type="text" name="supplierid" style="width: 130px;"/>
					</td>
				</tr>
				<tr>
					<td colspan="4">
						是否按总店合计：
						<select id="customercost-query-isPcustomer" name="isPcustomer" style="width: 190px;">
							<option value="1" selected="selected">是</option>
							<option value="0">否</option>
						</select>
					</td>
					<td colspan="2" style="text-align: right;">
						<a href="javaScript:void(0);" id="customercost-payable-query-List" class="button-qr" >查询</a>
						<a href="javaScript:void(0);" id="customercost-payable-query-reloadList" class="button-qr">重置</a>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:false" style="border: 0px;">
		<table id="customercost-table-payable"></table>
	</div>
</div>
<div id="customercost-table-payable-detail"></div>
<script type="text/javascript">
	//根据初始的列与用户保存的列生成以及字段权限生成新的列
	var footerobject = null;
	$(function(){

		<security:authorize url="/journalsheet/customercost/exportCustomerCostPayableListData.do">
		$("#journalsheet-export-customerCostPayableListPage").Excel('export',{
			queryForm: "#customercost-form-ListQuery", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			type: 'exportUserdefined',
			name: '客户应付费用报表',
			url: 'journalsheet/customercost/exportCustomerCostPayableListData.do'
		});
		</security:authorize>

		var customercostListColJson=$("#customercost-table-payable").createGridColumnLoad({
			resize:false,
			frozenCol:[[]],
			commonCol:[[
				{field:'ck',checkbox:true},
				{field:'customerid',title:'客户编码',width:70,sortable:true},
				{field:'customername',title:'客户名称',width:250,isShow:true},
				{field:'salesareaname',title:'销售区域',width:80,isShow:true},
				{field:'salesusername',title:'客户业务员',width:80,isShow:true},
				{field:'beginamount',title:'期初应付金额',width:100,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){

						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'lendamount',title:'本期应付(借)',width:100,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'payamount',title:'本期已付(贷)',width:100,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'endamount',title:'期末应付金额',width:100,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){

						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				}
			]]
		});
		$("#customercost-table-payable").datagrid({
			authority:customercostListColJson,
			frozenColumns:[[]],
			columns:customercostListColJson.common,
			fit:true,
			method:'post',
			title:'',
			showFooter: true,
			rownumbers:true,
			sortOrder:'asc',
			pagination:true,
			idField:'customerid',
			singleSelect:false,
			checkOnSelect:true,
			selectOnCheck:true,
			pageSize:100,
			toolbar:'#customercost-table-payableBtn',
			onDblClickRow:function(rowIndex, rowData){
				var begindate = $("#customercost-query-begindate").val();
				var enddate = $("#customercost-query-enddate").val();
				var supplierid = $("#customercost-query-supplierid").supplierWidget("getValue");
				var branddeptid = $("#customercost-query-salesdept").widget("getValue");
				var isPcustomer = $("#customercost-query-isPcustomer").val();
				$('#customercost-table-payable-detail').dialog({
					title: '客户应付费用明细',
					fit:true,
					collapsible:false,
					minimizable:false,
					maximizable:true,
					resizable:true,
					closed: true,
					cache: false,
					href: 'journalsheet/customercost/showCustomerCostPayableDetailListPage.do?reporttype=${param.reporttype }&customerid='+rowData.customerid+"&begindate="+begindate+"&enddate="+enddate+"&supplierid="+supplierid+"&branddeptid="+branddeptid+"&isPcustomer="+isPcustomer,
					modal: true
				});
				$('#customercost-table-payable-detail').dialog("open");
			},
			onLoadSuccess: function(){
				var footerrows = $(this).datagrid('getFooterRows');
				if(null!=footerrows && footerrows.length>0){
					footerobject = footerrows[0];
					countTotalAmount();
				}
			},
			onCheckAll:function(){
				countTotalAmount();
			},
			onUncheckAll:function(){
				countTotalAmount();
			},
			onCheck:function(){
				countTotalAmount();
			},
			onUncheck:function(){
				countTotalAmount();
			}
		}).datagrid("columnMoving");
		$("#customercost-query-supplierid").supplierWidget({
			singleSelect:true,
			width:130
		});
		$("#customercost-query-customerid").customerWidget({
			singleSelect:true,
			isall:true,
			width:225
		});
		$("#customercost-query-salesarea").widget({
			referwid:'RT_T_BASE_SALES_AREA',
			width:130,
			singleSelect:true
		});
		$("#customercost-query-salesuser").widget({
			referwid:'RL_T_BASE_PERSONNEL_CLIENTSELLER',
			width:130,
			singleSelect:true
		});
		$("#customercost-query-salesdept").widget({
			referwid:'RT_T_SYS_DEPT',
			width:130,
			onlyLeafCheck:false,
			singleSelect:true
		});
		$("#customercost-payable-query-List").click(function(){
			//高级查询
			var queryJSON = $("#customercost-form-ListQuery").serializeJSON();
			$("#customercost-table-payable").datagrid({
				url:'journalsheet/customercost/showCustomerCostPayableListData.do',
				pageNumber:1,
				queryParams:queryJSON
			}).datagrid("columnMoving");
		});
		$("#customercost-payable-query-reloadList").click(function(){
			$("#customercost-query-customerid").customerWidget("clear");
			$("#customercost-query-salesarea").widget("clear");
			$("#customercost-query-salesuser").widget("clear");
			$("#customercost-query-salesdept").widget("clear");
			$("#customercost-query-supplierid").supplierWidget("clear");
			$("#customercost-form-ListQuery").form("reset");
			var queryJSON = $("#customercost-form-ListQuery").serializeJSON();
			$("#customercost-table-payable").datagrid('loadData',{total:0,rows:[]});
		});
	});
	function countTotalAmount(){
		var rows =  $("#customercost-table-payable").datagrid('getChecked');
		var amount = 0;
		var lendamount = 0;
		var payamount = 0;
		var beginamount = 0;
		var endamount = 0;
		for(var i=0;i<rows.length;i++){
			lendamount = Number(lendamount)+Number(rows[i].lendamount == undefined ? 0 : rows[i].lendamount);
			payamount = Number(payamount)+Number(rows[i].payamount == undefined ? 0 : rows[i].payamount);
			amount = Number(amount)+Number(rows[i].amount == undefined ? 0 : rows[i].amount);
			beginamount = Number(beginamount)+Number(rows[i].beginamount || 0);
			endamount = Number(endamount)+Number(rows[i].endamount || 0);
		}
		var footerrows = [{customerid:'选中合计',lendamount:lendamount,payamount:payamount,amount:amount,beginamount:beginamount,endamount:endamount}];
		if(null != footerobject){
			footerrows.push(footerobject);
		}
		$("#customercost-table-payable").datagrid("reloadFooter",footerrows);
	}
</script>
</body>
</html>
