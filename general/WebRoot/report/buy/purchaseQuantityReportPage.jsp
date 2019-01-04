<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>采购进货箱数统计报表</title>
    <%@include file="/include.jsp" %>  
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>   
  </head>
  
  <body>
    	<table id="report-datagrid-purchaseQuantity"></table>
    	<div id="report-toolbar-purchaseQuantity" style="padding: 0px">
            <div class="buttonBG">
                <security:authorize url="/report/buy/purchaseQuantityReportExport.do">
                    <a href="javaScript:void(0);" id="report-buttons-purchaseQuantityPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
                </security:authorize>
            </div>
    		<form action="" id="report-query-form-purchaseQuantity" method="post">
	    		<table>
	    			<tr>
	    				<td>业务日期:</td>
	    				<td><input id="report-query-businessdate1" type="text" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 
	    					到 <input id="report-query-businessdate2" type="text" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
	    				<td>商品品牌:</td>
	    				<td><input type="text" id="report-query-brandid" name="brandid"/></td>
	    			</tr>
	    			<tr>
	    				<td>供应商名称:</td>
	    				<td><input type="text" id="report-query-buysupplierid" style="width: 225px;" name="supplierid"/></td>
	    				<td colspan="2">
	    					<a href="javaScript:void(0);" id="report-query-purchaseQuantity" class="button-qr">查询</a>
							<a href="javaScript:void(0);" id="report-reload-purchaseQuantity" class="button-qr">重置</a>

	    				</td>
	    			</tr>
	    		</table>
	    	</form>
    	</div>
    	<div id="report-dialog-purchaseQuantityDetail"></div>
    	<script type="text/javascript">
			var SR_footerobject  = null;
    		var initQueryJSON = $("#report-query-form-purchaseQuantity").serializeJSON();
    		$(function(){
    			var tableColumnListJson = $("#report-datagrid-purchaseQuantity").createGridColumnLoad({
					frozenCol : [[
						{field:'idok',checkbox:true,isShow:true}
					]],
					commonCol : [[
						  {field:'brandid',title:'品牌编号',width:60,align:'center'},
						  {field:'brandname',title:'商品品牌',width:130},
						  {field:'enternum',title:'进货总数量',width:80,sortable:true,align:'right'},
						  {field:'enterunitname',title:'进货单位',width:60,align:'center'},
						  {field:'totalweight',title:'进货总重量(kg)',resizable:true,sortable:true,align:'right'}
			         ]]
				});
				
				$("#report-datagrid-purchaseQuantity").datagrid({ 
			 		authority:tableColumnListJson,
			 		frozenColumns: tableColumnListJson.frozen,
					columns:tableColumnListJson.common,
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		pageSize:100,
		  	 		showFooter: true,
		  	 		singleSelect:false,
			 		checkOnSelect:true,
			 		selectOnCheck:true,
					toolbar:'#report-toolbar-purchaseQuantity',
					onDblClickRow:function(rowIndex, rowData){
						var brandid = rowData.brandid;
						var brandname = rowData.brandname;
						var businessdate1 = $("#report-query-businessdate1").val();
    					var businessdate2 = $("#report-query-businessdate2").val();
    					var url = 'report/buy/showBuySupplierReportDetailDataPage.do?brandid='+brandid+'&businessdate1='+businessdate1+'&businessdate2='+businessdate2+'&brandname='+brandname;
						$("#report-dialog-purchaseQuantityDetail").dialog({
						    title: '按品牌：['+rowData.brandname+'],分商品统计表',  
				    		width:800,
				    		height:400,
				    		closed:true,
				    		modal:true,
				    		maximizable:true,
				    		cache:false,
				    		resizable:true,
						    href: url
						});
						$("#report-dialog-purchaseQuantityDetail").dialog("open");
					},
					onLoadSuccess:function(){
						var footerrows = $(this).datagrid('getFooterRows');
						if(null!=footerrows && footerrows.length>0){
							SR_footerobject = footerrows[0];
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
				
				$("#report-query-brandid").widget({
					referwid:'RL_T_BASE_GOODS_BRAND',
		    		width:150,
					onlyLeafCheck:false,
					singleSelect:true
				});
				$("#report-query-buysupplierid").widget({
					name:'t_storage_purchase_enter',
		    		width:225,
					col:'supplierid',
					onlyLeafCheck:false,
					singleSelect:true
				});
				
				//回车事件
				controlQueryAndResetByKey("report-query-purchaseQuantity","report-reload-purchaseQuantity");
				
				//查询
				$("#report-query-purchaseQuantity").click(function(){
		      		var queryJSON = $("#report-query-form-purchaseQuantity").serializeJSON();
		      		$("#report-datagrid-purchaseQuantity").datagrid({
		      			url: 'report/buy/showPurchaseQuantityReportData.do',
		      			pageNumber:1,
						queryParams:queryJSON
		      		}).datagrid("columnMoving");
				});
				//重置
				$("#report-reload-purchaseQuantity").click(function(){
					$("#report-query-brandid").widget("clear");
					$("#report-query-form-purchaseQuantity")[0].reset();
					var queryJSON = $("#report-query-form-purchaseQuantity").serializeJSON();
		       		$("#report-datagrid-purchaseQuantity").datagrid('loadData',{total:0,rows:[]});
				});
				
				$("#report-buttons-purchaseQuantityPage").Excel('export',{
					queryForm: "#report-query-form-purchaseQuantity", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:'采购进货箱数统计报表',
			 		url:'report/buy/exportPurchaseQuantityReportData.do'
				});
				
    		});
    		function countTotalAmount(){
    			var rows =  $("#report-datagrid-purchaseQuantity").datagrid('getChecked');
    			var enternum = 0;
        		var totalweight = 0;
        		for(var i=0;i<rows.length;i++){
        			enternum = Number(enternum)+Number(rows[i].enternum == undefined ? 0 : rows[i].enternum);
        			totalweight = Number(totalweight)+Number(rows[i].totalweight == undefined ? 0 : rows[i].totalweight);       			
        		}
        		var foot=[{brandname:'选中合计',enternum:enternum,totalweight:formatterMoney(totalweight)
            			}];
        		if(null!=SR_footerobject){
            		foot.push(SR_footerobject);
        		}
        		$("#report-datagrid-purchaseQuantity").datagrid("reloadFooter",foot);
    		}
    	</script>
  </body>
</html>
