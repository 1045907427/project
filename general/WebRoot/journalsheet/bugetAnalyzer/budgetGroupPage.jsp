<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>预算分析页面</title>
    <%@include file="/include.jsp"%>
  </head>
  
  <body>
   		<div class="easyui-layout" data-options="fit:true">
   		
			
			<div data-options="region:'center'">
				<table id="journalsheet-table-budgetGroupPage"></table>  <!-- datagrid -->
				<div id="journalsheet-table-query-budgetGroupPage" style="padding: 2px; height: auto"> <!-- tollbar -->
					<form action="" id="journalsheet-form-budgetGroupPage" method="post">
							<table class="querytable">
								<tr>			    			
				    				<td>品牌:</td>
				    				<td class="tdinput" >
	                                    <input type="text" id="journalsheet-budgetGroupPage-brand" name="brand" style="width:150px;"/></td>							
									
									<td>部门:</td>
				    				<td class="tdinput" >
	                                    <input type="text" id="journalsheet-budgetGroupPage-deptid" name="deptid" style="width:150px;"/> 
	                                </td>	
	                                
	                                <td>供应商:</td>
				    				<td class="tdinput" >
	                                    <input type="text" id="journalsheet-budgetGroupPage-supplierid" name="supplierid" style="width:150px;"/> 
	                                </td>							    							    							    				
				    			</tr>
							
							
				    			<tr>			    			
									<td>预算类型:</td>
				    				<td class="tdinput" >
		                                <select id="journalsheet-budgetGroupPage-bugettype" name = "bugettype" class="len150 " >
					    				   <option value=""></option>
						    				<c:forEach items="${budgettypeList}" var="budget">
												<option value="${budget.code}">${budget.codename}</option>
											</c:forEach>
					    				</select>	
	                                </td>
	                                <td>年份:</td>
				    				<td class="tdinput" >
				    					<input id="journalsheet-budgetListPage-month" type="text" required='required' name="year" style="width:150px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy'})" value="${current}"/>
	                                </td>	
	                                <td  colspan="2"></td>
	                                <td colspan="4">
	                                    <a href="javaScript:void(0);" id="journalsheet-btn-budgetGroupPage" class="button-qr">查询</a>
	                                    <a href="javaScript:void(0);" id="journalsheet-budgetGroupPage" class="button-qr">重置</a>
	                                    <span id="storage-table-query-distributeRejectListPage-advanced"></span>
	                                </td>
				    			</tr>
				    			
				    		</table>
					</form>
				</div>
			</div>
			
			<div id = "journalsheet-budgetGroupPage-dialog">
		</div>
   <script type="text/javascript">
   	$(function(){
   		var initQueryJSON = $("#journalsheet-form-budgetGroupPage").serializeJSON();
   		
   		var listJson=$("#journalsheet-table-budgetGroupPage").createGridColumnLoad({
				frozenCol : [[
						{field:'idok',checkbox:true,isShow:true}
    			]],
    			
    			commonCol :[[
						{field:'brandname',title:'品牌',width:70},
						{field:'deptname',title:'部门',width:70},
						{field:'suppliername',title:'供应商',width:140},
						{field:'bugettype',title:'预算类型',width:100,
							formatter: function(value,row,index){
								return getSysCodeName('budgettype',value);
							}
						},
						{field:'month01',title:'1月',width:80,align:'right',
							formatter:function(value,row,index){
								     return formatterMoney(value);
								}
						},
						{field:'month02',title:'2月',width:80,align:'right',
							formatter:function(value,row,index){
								     return formatterMoney(value);
								}
						},
						{field:'month03',title:'3月',width:80,align:'right',
							formatter:function(value,row,index){
								     return formatterMoney(value);
								}
						},
						{field:'month04',title:'4月',width:80,align:'right',
							formatter:function(value,row,index){
								     return formatterMoney(value);
								}
						},
						{field:'month05',title:'5月',width:80,align:'right',
							formatter:function(value,row,index){
								     return formatterMoney(value);
								}
						},
						{field:'month06',title:'6月',width:80,align:'right',
							formatter:function(value,row,index){
								     return formatterMoney(value);
								}
						},
						{field:'month07',title:'7月',width:80,align:'right',
							formatter:function(value,row,index){
								     return formatterMoney(value);
								}
						},
						{field:'month08',title:'8月',width:80,align:'right',
							formatter:function(value,row,index){
								     return formatterMoney(value);
								}
						},
						{field:'month09',title:'9月',width:80,align:'right',
							formatter:function(value,row,index){
								     return formatterMoney(value);
								}
						},
						{field:'month10',title:'10月',width:80,align:'right',
							formatter:function(value,row,index){
								     return formatterMoney(value);
								}
						},
						{field:'month11',title:'11月',width:80,align:'right',
								formatter:function(value,row,index){
								     return formatterMoney(value);
								}
						},
						{field:'month12',title:'12月',width:80,align:'right',
							formatter:function(value,row,index){
						     	return formatterMoney(value);
						}},
    			]]
			});
   		
   		
   		$("#journalsheet-table-budgetGroupPage").datagrid({
				fit:true,
		 		method:'post',
		 		rownumbers:true,
		 		pagination:true,
		 		idField:'id',
		 		singleSelect:false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
		 		showFooter: true,
				toolbar:'#journalsheet-table-query-budgetGroupPage',
				url:"journalsheet/bugetAnalyzerGroup/bugetAnalyzerGroupData.do",
				queryParams:initQueryJSON,
                pageSize:100,
				authority : listJson,
		 		frozenColumns: listJson.frozen,
				columns:listJson.common,
				onDblClickRow:function(rowIndex, rowData){
				
				}
		});
   		
   	
   	
	   	//品牌名称
	     $("#journalsheet-budgetGroupPage-brand").widget({
	   			width:150,
				name:'t_base_goods_brand',
				col:'id',
				singleSelect:true,
				onlyLeafCheck:false,
	   	 });
	     
	     
	     //供应商
	  	$("#journalsheet-budgetGroupPage-supplierid").supplierWidget({ 
			name:'t_purchase_buyorder',
			col:'supplierid',
			singleSelect:true,
			onlyLeafCheck:true,
			onSelect:function(data){
			}
		});
		//部门
		$("#journalsheet-budgetGroupPage-deptid").widget({ 
    			name:'t_sales_order',
				col:'salesdept',
    			singleSelect:true,
    			width:150,
    			onlyLeafCheck:false
    	});
		
		
	 	//查询
		$("#journalsheet-btn-budgetGroupPage").click(function(){
			$("#journalsheet-table-budgetGroupPage").datagrid('clearChecked');
			$("#journalsheet-table-budgetGroupPage").datagrid('clearSelections');
      		var queryJSON = $("#journalsheet-form-budgetGroupPage").serializeJSON();;
			$('#journalsheet-table-budgetGroupPage').datagrid('load',queryJSON);			
		});
		//重置
		$("#journalsheet-budgetGroupPage").click(function(){
			$("#journalsheet-table-budgetGroupPage").datagrid('clearChecked');
			$("#journalsheet-table-budgetGroupPage").datagrid('clearSelections');
			
			$("#journalsheet-budgetGroupPage-brand").widget('clear');
			$("#journalsheet-budgetGroupPage-supplierid").supplierWidget('clear');
			$("#journalsheet-budgetGroupPage-deptid").widget('clear')
			$("#journalsheet-form-budgetGroupPage")[0].reset();
      		var queryJSON = $("#journalsheet-form-budgetGroupPage").serializeJSON();;
			$('#journalsheet-table-budgetGroupPage').datagrid('load',queryJSON);		
		});
		
		
   	
   	})
     
     
   </script>
   
  </body>
</html>
