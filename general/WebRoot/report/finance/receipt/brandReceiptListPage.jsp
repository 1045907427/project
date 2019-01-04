<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>分品牌资金应收款情况表</title>
    <%@include file="/include.jsp" %>  
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>   
  </head>
  
  <body>
    	<table id="report-datagrid-brandReceipt"></table>
    	<div id="report-toolbar-brandReceipt" style="padding: 0px">
            <div class="buttonBG">
                <security:authorize url="/report/finance/brandReceiptExport.do">
                    <a href="javaScript:void(0);" id="report-buttons-brandReceiptPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
                </security:authorize>
            </div>
    		<form action="" id="report-query-form-brandReceipt" method="post">
    		<table>
    			<tr>
    				<td>业务日期:</td>
    				<td><input type="text" id="report-query-businessdate1" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" id="report-query-businessdate2" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
    				<td>商品品牌:</td>
    				<td><input type="text" id="report-query-brandid" name="brandid"/>
						<input type="hidden" name="groupcols" value="brandid"/>
					</td>
    				<td colspan="2">
    					<a href="javaScript:void(0);" id="report-queay-brandReceipt" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="report-reload-brandReceipt" class="button-qr">重置</a>

    				</td>
    			</tr>
    		</table>
    	</form>
    	</div>
    	<div id="report-fundsBrand-detail-dialog"></div>
    	<div id="report-fundsBrandCustomerGoods-detail-dialog"></div>
    	<script type="text/javascript">
    		var initQueryJSON = $("#report-query-form-brandReceipt").serializeJSON();
    		$(function(){
				//全局导出
				$("#report-buttons-brandReceiptPage").click(function(){
					var queryJSON = $("#report-query-form-brandReceipt").serializeJSON();
					//获取排序规则
					var objecr  = $("#report-datagrid-brandReceipt").datagrid("options");
					if(null != objecr.sortName && null != objecr.sortOrder ){
						queryJSON["sort"] = objecr.sortName;
						queryJSON["order"] = objecr.sortOrder;
					}
					var queryParam = JSON.stringify(queryJSON);
					var url = "report/finance/exportBaseFinanceReceiptData.do";
					exportByAnalyse(queryParam,"分品牌资金应收款情况表","report-datagrid-brandReceipt",url);
				});

				var brandtableColumnListJson = $("#report-datagrid-brandReceipt").createGridColumnLoad({
					frozenCol : [[
						{field:'idok',checkbox:true,isShow:true}
					]],
					commonCol : [[
						{field:'brandid',title:'品牌名称',width:60,
							formatter:function(value,rowData,rowIndex){
								return rowData.brandname;
							}
						},
						{field:'allunwithdrawnamount',title:'应收款总额',align:'right',resizable:true,isShow:true,
							formatter:function(value,rowData,rowIndex){
								if("QC" != rowData.brandid && value!=0){
									return '<a href="javascript:showDetail(\''+rowData.brandid+'\',0);" style="text-decoration:none">'+formatterMoney(value)+'</a>';
								}else{
									return formatterMoney(value);
								}
							}
						},
						{field:'unauditamount',title:'未验收应收款',align:'right',resizable:true,isShow:true,sortable:true,
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							}
						},
						{field:'auditamount',title:'已验收应收款',align:'right',resizable:true,isShow:true,sortable:true,
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							}
						},
						{field:'rejectamount',title:'退货应收款',align:'right',resizable:true,isShow:true,sortable:true,
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							}
						},
						{field:'allpushbalanceamount',title:'冲差应收款',align:'right',resizable:true,isShow:true,sortable:true,
							formatter:function(value,rowData,rowIndex){
								return formatterMoney(value);
							}
						}
					]]
				});

    			$("#report-datagrid-brandReceipt").datagrid({
					authority:brandtableColumnListJson,
					frozenColumns: brandtableColumnListJson.frozen,
					columns:brandtableColumnListJson.common,
					method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		singleSelect:true,
		  	 		pageSize:100,
					toolbar:'#report-toolbar-brandReceipt'
				}).datagrid("columnMoving");
				
				$("#report-query-brandid").widget({
					referwid:'RL_T_BASE_GOODS_BRAND',
		    		width:210,
					singleSelect:false
				});
				
				//回车事件
				controlQueryAndResetByKey("report-queay-brandReceipt","report-reload-brandReceipt");
				
				//查询
				$("#report-queay-brandReceipt").click(function(){
					//把form表单的name序列化成JSON对象
		      		var queryJSON = $("#report-query-form-brandReceipt").serializeJSON();
		      		$("#report-datagrid-brandReceipt").datagrid({
		      			url: 'report/finance/showBaseFinanceReceiptData.do',
		      			pageNumber:1,
						queryParams:queryJSON
		      		});
				});
				//重置
				$("#report-reload-brandReceipt").click(function(){
					$("#report-query-brandid").widget("clear");
					$("#report-query-form-brandReceipt")[0].reset();
		       		$("#report-datagrid-brandReceipt").datagrid('loadData',{total:0,rows:[]});
				});
    		});
    		
    		function showDetail(brand,type){
    			var businessdate1 = $("#report-query-businessdate1").val();
    			var businessdate2 = $("#report-query-businessdate2").val();
    			var url = 'report/finance/showFundsBrandListPage.do?brand='+brand+'&type='+type+'&businessdate1='+businessdate1+'&businessdate2='+businessdate2;
    			$("#report-fundsBrand-detail-dialog").dialog({
				    title: '分品牌销售回单列表',  
		    		width:800,
		    		height:400,
		    		closed:false,
		    		modal:true,
		    		maximizable:true,
		    		cache:false,
		    		resizable:true,
				    href: url
				});
    		}
    	</script>
  </body>
</html>
