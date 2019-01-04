<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>资金平均单价统计报表</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
    <table id="journalsheet-table-FundAverageStatisticsDetailList"></table>
   	<div id="journalsheet-button-QueryFundAverageStatisticsDetail" style="padding: 0px">
		<div class="buttonBG">
			<security:authorize url="/journalsheet/statisticals/fundAverageStatisticsExport.do">
				<a href="javaScript:void(0);" id="journalsheet-buttons-fundAverageStatisticsDetailPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
			</security:authorize>
		</div>
		<form action="" id="journalsheet-form-QueryFundAverageStatisticsDetail" method="post">
   			<input type="hidden" name="supplierid" value="${supplierid }"/>
    		<input type="hidden" name="suppliername" value="${suppliername }"/>
    		<input type="hidden" name="begintime" value="${begintime }"/>
    		<input type="hidden" name="endtime" value="${endtime }"/>
   		</form>
   	</div>
    <script type="text/javascript">
    	var queryJSON = $("#journalsheet-form-QueryFundAverageStatisticsDetail").serializeJSON();
    	//根据初始的列与用户保存的列生成以及字段权限生成新的列
    	var FundAverageStatisticsListColJson=$("#journalsheet-table-FundAverageStatisticsDetailList").createGridColumnLoad({
	     	frozenCol:[[]],
	     	commonCol:[[
				{field:'id',title:'编码',width:80,sortable:true,hidden:true},
				{field:'supplierid',title:'供应商编码',width:70,sortable:true},
				{field:'supplierName',title:'供应商名称',width:210,sortable:true,isShow:true},
				{field:'supplierdeptid',title:'所属部门',width:100,sortable:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.supplierdeptName;
					}
				},
				{field:'businessdate',title:'业务日期',width:80,sortable:true},
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
				{field:'totalamount',title:'合计金额',resizable:true,sortable:true,align:'right',isShow:true,
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
			
			$("#journalsheet-buttons-fundAverageStatisticsDetailPage").Excel('export',{
				queryForm: "#journalsheet-form-QueryFundAverageStatisticsDetail", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
		 		type:'exportUserdefined',
		 		name:'供应商[${suppliername}]资金平均统计报表',
		 		url:'journalsheet/statisticals/exportFundAverageStatisticsDetailData.do'
			});
			
			$("#journalsheet-table-FundAverageStatisticsDetailList").datagrid({
				authority:FundAverageStatisticsListColJson,
	  	 		frozenColumns:[[]],
				columns:FundAverageStatisticsListColJson.common,
	  	 		fit:true,
	  	 		method:'post',
	  	 		title:'',
	  	 		showFooter: true,
	  	 		rownumbers:true,
	  	 		pagination:true,
	  	 		url:'journalsheet/statisticals/getFundAverageStatisticsSheetDetailList.do',
	  	 		queryParams:queryJSON,
	  	 		singleSelect:true,
				pageSize:100,
				toolbar:'#journalsheet-button-QueryFundAverageStatisticsDetail'
			}).datagrid("columnMoving");
			
		});
    </script>
  </body>
</html>
