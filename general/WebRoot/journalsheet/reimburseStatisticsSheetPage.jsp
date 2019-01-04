<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>代垫统计报表</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true">
    	<div style="height: 60px;" id="journalsheet-button-QueryStatistics">
    		<form action="" id="journalsheet-form-QueryStatistics" method="post">
    			<table cellpadding="0" cellspacing="1" border="0">
    				<tr>
    					<td style="padding-left: 10px;">供&nbsp;应&nbsp;商:&nbsp;</td>
    					<td><input id="journalsheet-widget-supplierquery" name="supplierid" type="text"/></td>
    					<td style="padding-left: 10px;">所属部门:&nbsp;</td>
    					<td colspan="2"><input id="journalsheet-widget-supplierdeptquery" name="supplierdeptid" type="text" /></td>
    				</tr>
    				<tr>
    					<td style="padding-left: 10px;">业务日期:&nbsp;</td>
    					<td><input id="begintime" name="begintime" class="Wdate" style="width:100px;height: 20px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})"/>
  							到&nbsp<input id="endtime" name="endtime" class="Wdate" style="width:100px;height: 20px;" onclick="WdatePicker({dateFmt:'yyyy-MM-dd'})" />
    					</td>
    					<td style="padding-left: 10px;">品牌名称:&nbsp;</td>
    					<td><input id="journalsheet-widget-brandquery" name="brandid" type="text" /></td>
    					<td style="padding-left: 10px;" colspan="2">
    						<a href="javaScript:void(0);" id="reimburseInput-query-List" class="easyui-linkbutton" iconCls="icon-search" plain="true" title="[Alt+Q]查询">查询</a>
				    		<a href="javaScript:void(0);" id="reimburseInput-query-reloadList" class="easyui-linkbutton" iconCls="icon-reload" plain="true" title="[Alt+R]重置">重置</a>
			    			<security:authorize url="/journalsheet/statisticals/reimburseStatisticsExport.do">
								<a href="javaScript:void(0);" id="journalsheet-buttons-reimburseStatisticsPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
							</security:authorize>
    					</td>
    				</tr>
    			</table>
    		</form>
    	</div>
    	<div data-options="region:'center',border:false" style="border: 0px;">
    		<table id="journalsheet-table-statisticslist"></table>
    	</div>
    </div>
    <script type="text/javascript">
    	//根据初始的列与用户保存的列生成以及字段权限生成新的列
    	var reimburseInputListColJson=$("#journalsheet-table-statisticslist").createGridColumnLoad({
	     	name:'t_js_reimburseinput',
	     	frozenCol:[[]],
	     	commonCol:[[
	     		{field:'subjectid',title:'科目编码',width:80,sortable:true,hidden:true},
	     		{field:'suppliername',title:'供应商编码',width:70,sortable:true,isShow:true,
					formatter:function(val,rowData,rowIndex){
						if(rowData.isshow == "1"){
							return rowData.supplierid;
						}else if(rowData.isshow == "0"){
							return "";
						}
					}
				},
				{field:'supplierid',title:'供应商名称',width:210,sortable:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.suppliername;
					}
				},
				{field:'brandid',title:'品牌名称',width:60,sortable:true,hidden:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.brandName;
					}
				},
				{field:'supplierdeptid',title:'所属部门',width:70,sortable:true,hidden:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.supplierdeptName;
					}
				},
				{field:'businessdate',title:'业务日期',width:75,sortable:true,hidden:true},
				{field:'planamount',title:'计划金额',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}else{
							return "0.00";
						}
					}
				},
				{field:'planreimbursetype',title:'计划收回方式',width:85,sortable:true,hidden:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.planreimbursetypeName;
					}
				},
				{field:'planreimbursetime',title:'计划收回时间',width:85,sortable:true,hidden:true},
				{field:'takebackamount',title:'收回金额',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}else{
							return "0.00";
						}
					}
				},
				{field:'reimbursetype',title:'收回方式',width:80,sortable:true,hidden:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.reimbursetypeName;
					}
				},
				{field:'untakebackamount',title:'未收回金额',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}else{
							return "0.00";
						}
					}
				},
				{field:'actingmatamount',title:'代垫金额',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}else{
							return "0.00";
						}
					}
				},
				{field:'surplusamount',title:'盈余金额',resizable:true,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}else{
							return "0.00";
						}
					}
				}
			]]
	     });
	     
		$(function(){
			//供应商
		  	$("#journalsheet-widget-supplierquery").widget({
		  		width:300,
				name:'t_js_reimburseinput',
				col:'supplierid',
				singleSelect:true
			});
			//所属部门
			$("#journalsheet-widget-supplierdeptquery").widget({
		  		width:150,
		  		name:'t_js_reimburseinput',
				col:'supplierdeptid',
				singleSelect:true
			});
			//品牌名称
			$("#journalsheet-widget-brandquery").widget({
		  		width:150,
		  		name:'t_js_reimburseinput',
				col:'brandid',
				singleSelect:true
			});
			
			//回车事件
			controlQueryAndResetByKey("reimburseInput-query-List","reimburseInput-query-reloadList");
			
			//查询
			$("#reimburseInput-query-List").click(function(){
				//把form表单的name序列化成JSON对象
	      		var queryJSON = $("#journalsheet-form-QueryStatistics").serializeJSON();
	      		//调用datagrid本身的方法把JSON对象赋给queryParams 即可进行查询
	      		$("#journalsheet-table-statisticslist").treegrid({
	      			url:'journalsheet/statisticals/getreimburseStatisticsSheetList.do',
	      			pageNumber:1,
	      			queryParams:queryJSON
	      		}).datagrid("columnMoving");
			});
			//重置
			$("#reimburseInput-query-reloadList").click(function(){
				$("#journalsheet-widget-supplierquery").widget("clear");
				$("#journalsheet-widget-supplierdeptquery").widget("clear");
				$("#journalsheet-widget-brandquery").widget("clear");
				$("#journalsheet-form-QueryStatistics")[0].reset();
	       		$("#journalsheet-table-statisticslist").treegrid('loadData',{total:0,rows:[]});
			});
			
			$("#journalsheet-buttons-reimburseStatisticsPage").Excel('export',{
				queryForm: "#journalsheet-form-QueryStatistics", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
		 		type:'exportUserdefined',
		 		name:'代垫统计报表',
		 		url:'journalsheet/statisticals/exportReimburseStatisticsData.do'
			});
			
			$("#journalsheet-table-statisticslist").treegrid({ 
     			authority:reimburseInputListColJson,
	  	 		frozenColumns:[[]],
				columns:reimburseInputListColJson.common,
	  	 		method:'post',
	  	 		title:'',
	  	 		rownumbers:true,
	  	 		fit:true,
	  	 		pagination:true,
	  	 		showFooter: true,
	  	 		singleSelect:true,
				pageSize:20,
				pageList:[10,20,30,50,200],
				idField:'id',  
			    treeField:'supplierid',
			    toolbar:'#journalsheet-button-QueryStatistics',
			    onLoadSuccess:function(row, data){
			    	if(data.footer==null && !row){
			    		$("#journalsheet-table-statisticslist").treegrid("reloadFooter",{});
			    	}
			    },
			    onBeforeLoad: function(row,param){  
                    if (!row) {
                    	param.id = "";
                    }
                },
                onDblClickRow:function(row){
                	if(row.state=='closed'){
                		$("#journalsheet-table-statisticslist").treegrid("expand",row.id);
                	}else{
                		$("#journalsheet-table-statisticslist").treegrid("collapse",row.id);
                	}
                }
			}).datagrid("columnMoving");
		});
    </script>
  </body>
</html>
