<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>日志查询</title>
	<%@include file="/include.jsp"%>
	<style type="text/css">
		.len50 {
			width: 50px;
		}
		.len60 {
			width: 60px;
		}
	</style>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'center'">
			<table id="client-datagrid-offpriceLogListPage"></table>
			<div id="client-table-query-offPriceLogListPage" style="padding: 2px; height: auto">
				<form id="client-offPriceLog-form">
					<table>
						<tr>
							<td class="len80">操作日期：</td>
							<td>
								<input type="text" id="operatedatebegin" name="operatedatebegin" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /> 到 <input type="text" id="operatedateend" name="operatedateend" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" />
							</td>
							<td class="len80 right">特价编码：</td>
							<td>
								<input type="text" id="client-offpriceLog-offpriceid" name="offpriceid" style="width:150px;"/>
							</td>
							<td class="len50 right">门店：</td>
							<td class="len150">
								<input type="text" id="client-offpriceLog-storename" name="storeid" style="width:150px;"/>
							</td>
						</tr>
						<tr>
							<td>商品名称：</td>
							<td>
								<input type="text" id="client-offpriceLog-goodsid" name="goodsid" style="width:224px;"/>
							</td>
							<td colspan="2">
							</td>
							<td colspan="2">
								<a href="javaScript:void(0);" id="client-btn-offpriceLogQuery" class="button-qr">查询</a>
								<a href="javaScript:void(0);" id="client-btn-offpriceLogReset" class="button-qr">重置</a>
								<span id="storage-table-query-DeliveryOutListPage-advanced"></span>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript">
		$(function(){

			var LogPageQueryJson = $("#client-offPriceLog-form").serializeJSON();

			var offPriceLogJson=$("#client-datagrid-offpriceLogListPage").createGridColumnLoad({
				frozenCol : [[
					{field:'idok',checkbox:false,isShow:true}
				]],
				commonCol :[[
					{field:'id',title:'编号',width:120,hidden:true},
					{field:'offpriceid',title:'特价编码',width:120},
                    {field:'deptid',title:'部门编号',width:70},
                    {field:'deptname',title:'部门名称',width:120,aliascol: 'storeid'},
					{field:'goodsname',title:'商品名称',width:170},
					{field:'retailprice',title:'零售价',width:90},
                    {field:'begindateafter',title:'起始日期',width:150},
                    {field:'enddateafter',title:'终止日期',width:150},
                    {field:'begintimeafter',title:'起始时间',width:80},
                    {field:'endtimeafter',title:'终止时间',width:80},
					{field:'operateusername',title:'操作人',width:60},
					{field:'operatetime',title:'操作时间',width:105},
					{field:'operatetype',title:'操作类型',width:60,
						formatter:function(value,rowData,rowIndex){
							if(value=="1"){
								return "修改"
							}else if(value == "0"){
								return "新增"
							}
						}
					}
				]]
			});

			$("#client-datagrid-offpriceLogListPage").datagrid({
				fit:true,
				method:'post',
				rownumbers:true,
				pagination:true,
				idField:'id',
				toolbar:"#client-table-query-offPriceLogListPage",
				url:"client/offprice/getOffPriceLogList.do",
				queryParams:LogPageQueryJson,
				pageSize:200,
				frozenColumns: offPriceLogJson.frozen,
				columns:offPriceLogJson.common,
				singleSelect:true
			});

			//门店名称
			$("#client-offpriceLog-storename").widget({
				referwid :'RL_T_BASE_STORE_INFO',
				singleSelect:true,
				onlyLeafCheck:true,
				width: 160
			});

			//商品名称
			$("#client-offpriceLog-goodsid").goodsWidget({
			});

			$("#client-btn-offpriceLogQuery").click(function(){
				var queryJSON = $("#client-offPriceLog-form").serializeJSON();
				$('#client-datagrid-offpriceLogListPage').datagrid('load',queryJSON);
			});

			$("#client-btn-offpriceLogReset").click(function(){
				$("#client-offpriceLog-storename").widget('clear');
				$('#goodsid').goodsWidget('clear');
				$("#client-offpriceLog-goodsid").goodsWidget('clear');
				$('#client-offPriceLog-form')[0].reset();
				var queryJSON = $("#client-offPriceLog-form").serializeJSON();
				$("#client-datagrid-offpriceLogListPage").datagrid('clearChecked');
				$("#client-datagrid-offpriceLogListPage").datagrid('clearSelections');
				$('#client-datagrid-offpriceLogListPage').datagrid('load',queryJSON);
			})
		})

	</script>
</body>
</html>