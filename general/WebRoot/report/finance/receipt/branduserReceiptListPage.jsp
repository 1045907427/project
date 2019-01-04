<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>分品牌业务员资金应收款情况表</title>
    <%@include file="/include.jsp" %>  
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>   
  </head>
  
  <body>
    	<table id="report-datagrid-branduserReceipt"></table>
    	<div id="report-toolbar-branduserReceipt" style="padding: 0px">
            <div class="buttonBG">
                <security:authorize url="/report/finance/branduserReceiptExport.do">
                    <a href="javaScript:void(0);" id="report-buttons-branduserReceiptPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="全局导出">全局导出</a>
                </security:authorize>
            </div>
    		<form action="" id="report-query-form-branduserReceipt" method="post">
    		<table>

    			<tr>
    				<td>业务日期:</td>
    				<td><input type="text" id="report-query-businessdate1" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" id="report-query-businessdate2" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
    				<td>品牌业务员:</td>
    				<td><input type="text" id="report-query-banduserid" name="branduser"/>
						<input type="hidden" name="groupcols" value="branduser"/>
					</td>
    				<td colspan="2">
    					<a href="javaScript:void(0);" id="report-queay-branduserReceipt" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="report-reload-branduserReceipt" class="button-qr">重置</a>
    				</td>
    			</tr>
    		</table>
    	</form>
    	</div>
    	<div id="report-fundsBrand-detail-dialog"></div>
    	<div id="report-dialog-branduserReceiptDetail"></div>
    	<script type="text/javascript">
    		var initQueryJSON = $("#report-query-form-branduserReceipt").serializeJSON();
    		$(function(){
				//全局导出
				$("#report-buttons-branduserReceiptPage").click(function(){
					var queryJSON = $("#report-query-form-branduserReceipt").serializeJSON();
					//获取排序规则
					var objecr  = $("#report-datagrid-branduserReceipt").datagrid("options");
					if(null != objecr.sortName && null != objecr.sortOrder ){
						queryJSON["sort"] = objecr.sortName;
						queryJSON["order"] = objecr.sortOrder;
					}
					var queryParam = JSON.stringify(queryJSON);
					var url = "report/finance/exportBaseFinanceReceiptData.do";
					exportByAnalyse(queryParam,"分品牌业务员资金应收款情况表","report-datagrid-branduserReceipt",url);
				});

				var brandusertableColumnListJson = $("#report-datagrid-branduserReceipt").createGridColumnLoad({
					frozenCol : [[
						{field:'idok',checkbox:true,isShow:true}
					]],
					commonCol : [[
						{field:'branduser',title:'品牌业务员',width:70,
							formatter:function(value,rowData,rowIndex){
								return rowData.brandusername;
							}
						},
						{field:'branddept',title:'品牌部门',width:80,hidden:true,
							formatter:function(value,rowData,rowIndex){
								return rowData.branddeptname;
							}
						},
						{field:'allunwithdrawnamount',title:'应收款总额',align:'right',resizable:true,isShow:true,
							formatter:function(value,rowData,rowIndex){
								if("QC" != rowData.branduser && value!=0){
									return '<a href="javascript:showDetail(\''+rowData.branduser+'\',2);" style="text-decoration:none">'+formatterMoney(value)+'</a>';
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

    			$("#report-datagrid-branduserReceipt").datagrid({
					authority:brandusertableColumnListJson,
					frozenColumns: brandusertableColumnListJson.frozen,
					columns:brandusertableColumnListJson.common,
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		pageSize:100,
		  	 		singleSelect:true,
					toolbar:'#report-toolbar-branduserReceipt'
				}).datagrid("columnMoving");
				$("#report-query-banduserid").widget({
					referwid:'RL_T_BASE_PERSONNEL_BRANDSELLER',
		    		width:130,
					singleSelect:true
				});
				
				//回车事件
				controlQueryAndResetByKey("report-queay-branduserReceipt","report-reload-branduserReceipt");
				
				//查询
				$("#report-queay-branduserReceipt").click(function(){
					//把form表单的name序列化成JSON对象
		      		var queryJSON = $("#report-query-form-branduserReceipt").serializeJSON();
		      		$("#report-datagrid-branduserReceipt").datagrid({
		      			url: 'report/finance/showBaseFinanceReceiptData.do',
		      			pageNumber:1,
						queryParams:queryJSON
		      		});
				});
				//重置
				$("#report-reload-branduserReceipt").click(function(){
					$("#report-query-banduserid").widget("clear");
					$("#report-query-form-branduserReceipt").form("reset");
					var queryJSON = $("#report-query-form-branduserReceipt").serializeJSON();
		       		$("#report-datagrid-branduserReceipt").datagrid('loadData',{total:0,rows:[]});
				});
    		});
    		
    		function showDetail(branduser,type){
    			var businessdate1 = $("#report-query-businessdate1").val();
    			var businessdate2 = $("#report-query-businessdate2").val();
    			var url = 'report/finance/showFundsBrandListPage.do?branduser='+branduser+'&type='+type+'&businessdate1='+businessdate1+'&businessdate2='+businessdate2;
    			$("#report-fundsBrand-detail-dialog").dialog({
				    title: '分品牌业务员销售回单列表',  
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
