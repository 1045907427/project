<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>资金统计报表</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true">
    	<div id="journalsheet-button-QueryFundStatistics" style="padding: 0px">
			<div class="buttonBG">
				<security:authorize url="/journalsheet/statisticals/fundStatisticsExport.do">
					<a href="javaScript:void(0);" id="journalsheet-buttons-fundStatisticsPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
				</security:authorize>
				<security:authorize url="/journalsheet/statisticals/fundStatisticsReset.do">
					<a href="javaScript:void(0);" id="journalsheet-reset-fundStatisticsPage" class="easyui-linkbutton" iconCls="button-reset" plain="true" title="重新生成报表">重新生成报表</a>
				</security:authorize>
			</div>
    		<form action="" id="journalsheet-form-QueryFundStatistics" method="post">
    			<table class="querytable">
    				<tr>
						<td>所属部门:</td>
						<td><input id="journalsheet-widget-supplierdeptquery" name="supplierdeptid" type="text"/></td>
    					<td>供应商:</td>
    					<td><input id="journalsheet-widget-supplierquery" name="supplierid" type="text"/></td>
    					<td>合计金额：</td>
    					<td>
    						<select name="type" style="width: 150px;">
    							<option></option>
    							<option value="1" selected="selected">不为零</option>
    							<option value="2">为零</option>
    						</select>
    					</td>
    				</tr>
    				<tr>
   						<td>查询版本:</td>
    					<td id="journalsheet-versiontd" colspan="3">
    						<select id="journalsheet-version" name="versionstr" style="width: 150px;">
    							<option value="0">当前数据</option>
    							<option value="1">历史数据</option>
    						</select>
    					</td>
    					<td id="journalsheet-date1" style="display: none;">业务日期:</td>
    					<td id="journalsheet-date2" style="display: none;">
							<input id="begintime" name="begintime" class="Wdate" style="width:100px;" onclick="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd',maxDate:'${today }'})" />
   							 到 <input id="endtime" name="endtime" class="Wdate" style="width:100px;" onclick="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd',maxDate:'${today }'})" />
   						</td>
    					<td colspan="3">
    						<a href="javaScript:void(0);" id="journalsheet-query-FundStatisticsList" class="button-qr">查询</a>
				    		<a href="javaScript:void(0);" id="journalsheet-reload-FundStatisticsList" class="button-qr">重置</a>
    					</td>
    				</tr>
    			</table>
    		</form>
    	</div>
    	<div data-options="region:'center',border:false" style="border: 0px;">
    		<table id="journalsheet-table-FundStatisticslist"></table>
    	</div>
    </div>
    <script type="text/javascript">
    	//根据初始的列与用户保存的列生成以及字段权限生成新的列
    	var FundinputListColJson=$("#journalsheet-table-FundStatisticslist").createGridColumnLoad({
	     	frozenCol:[[]],
	     	commonCol:[[
				{field:'businessdate',title:'业务日期',width:80,sortable:true},
				{field:'id',title:'编码',width:80,sortable:true,hidden:true},
				{field:'supplierid',title:'供应商编码',width:70,sortable:true},
				{field:'supplierName',title:'供应商名称',width:210,sortable:true},
				{field:'supplierdeptid',title:'所属部门',width:100,sortable:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.supplierdeptName;
					}
				},
				{field:'advanceamount',title:'预付金额',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'stockamount',title:'库存金额',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'receivableamount',title:'应收金额',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'actingmatamount',title:'代垫金额',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'payableamount',title:'应付金额',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'totalamount',title:'合计金额',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'stockdiscount',title:'库存折差',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'norecactingmat',title:'代垫未收',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'norecexpenses',title:'费用未付',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'sumamount',title:'累计金额',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'currentactingmat',title:'本期代垫',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'sumactingmat',title:'累计代垫',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'remittancerecovery',title:'汇款收回',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'goodsrecovery',title:'货补收回',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'sumreceivable',title:'累计已收',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'sumnorec',title:'累计未收',resizable:true,sortable:true,align:'right',
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
	     
		$(function(){
			//供应商
		  	$("#journalsheet-widget-supplierquery").widget({
		  		width:225,
				referwid: 'RL_T_BASE_BUY_SUPPLIER_1',
				singleSelect:true
			});
			$("#journalsheet-widget-supplierdeptquery").widget({
		  		width:150,
				referwid: 'RL_T_BASE_DEPARTMENT_BUYER',
				singleSelect:true
			});
			
			//查询
			$("#journalsheet-query-FundStatisticsList").click(function(){
				var version = $("#journalsheet-version").val();
				var date1 = $("#begintime").val();
				var date2 = $("#endtime").val();
				if(version=="1" && (date1 == "" && date2 == "")){
					$.messager.alert("提醒","查询历史数据，必须选择业务日期");
					return false;
				}
	      		var queryJSON = $("#journalsheet-form-QueryFundStatistics").serializeJSON();
	      		$("#journalsheet-table-FundStatisticslist").datagrid({
	      			url:'journalsheet/statisticals/getFundStatisticsSheetList.do',
	      			pageNumber:1,
	      			queryParams:queryJSON
	      		}).datagrid("columnMoving");
			}); 
			
			$("#journalsheet-reload-FundStatisticsList").click(function(){
				$("#journalsheet-widget-supplierquery").widget("clear");
				$("#journalsheet-widget-supplierdeptquery").widget("clear");
				$("#journalsheet-form-QueryFundStatistics")[0].reset();
	       		$("#journalsheet-table-FundStatisticslist").datagrid('loadData',{total:0,rows:[]});
			});
			
			$("#journalsheet-buttons-fundStatisticsPage").Excel('export',{
				queryForm: "#journalsheet-form-QueryFundStatistics", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
		 		type:'exportUserdefined',
		 		name:'资金统计报表',
		 		url:'journalsheet/statisticals/exportFundStatisticsData.do'
			});

			//重新生成报表
			$("#journalsheet-reset-fundStatisticsPage").click(function(){
				$.messager.confirm("提醒","是否确定重新生成报表?",function(r){
					if(r){
						loading("重新生成中..");
						$.ajax({
							url :'journalsheet/statisticals/doResetFundStatisticsSheetList.do',
							type:'post',
							dataType:'json',
							data:{idStr:idStr},
							success:function(retJSON){
								loaded();
								$.messager.alert("提醒","已重新生成！");
							}
						});
					}
				});
			});
			
			$("#journalsheet-table-FundStatisticslist").datagrid({
				authority:FundinputListColJson,
	  	 		frozenColumns:[[]],
				columns:FundinputListColJson.common,
	  	 		fit:true,
	  	 		method:'post',
	  	 		title:'',
	  	 		showFooter: true,
	  	 		rownumbers:true,
	  	 		sortName:'businessdate',
	  	 		sortOrder:'asc',
	  	 		pagination:true,
	  	 		singleSelect:true,
				pageSize:100,
				toolbar:'#journalsheet-button-QueryFundStatistics'
			}).datagrid("columnMoving");
			
			$("#journalsheet-version").change(function(){
				var val = $(this).val();
				if(val=='1'){
					$("#begintime").attr("disabled",false);
					$("#journalsheet-date1").show();
					$("#journalsheet-date2").show();
					$("#journalsheet-versiontd").attr("colspan",1);
				}else{
					$("#begintime").attr("disabled",true);
					$("#journalsheet-date1").hide();
					$("#journalsheet-date2").hide();
					$("#journalsheet-versiontd").attr("colspan",3);
				}
			});
		});
    </script>
  </body>
</html>
