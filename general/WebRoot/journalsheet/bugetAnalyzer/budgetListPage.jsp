<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>预算分析页面</title>
    <%@include file="/include.jsp"%>
  </head>
  
  <body>
   		<div class="easyui-layout" data-options="fit:true">
   		
			<div data-options="region:'north',border:false">
				<div class="buttonBG" id="journalsheet-buttons-budgetListPage"></div>
			</div>
			
			<div data-options="region:'center'">
				<table id="journalsheet-table-budgetListPage"></table>  <!-- datagrid -->
				<div id="journalsheet-table-query-budgetListPage" style="padding: 2px; height: auto"> <!-- tollbar -->
					<form action="" id="journalsheet-form-budgetListPage" method="post">
							<table class="querytable">
				    			<tr>			    			
				    				<td>录入年月:</td>
				    				<td class="tdinput" >
				    					<input id="journalsheet-budgetListPage-month" type="text" required='required' name="yearMonth" style="width:150px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM'})" value="${current}"/>
									<td>预算类型:</td>
				    				<td class="tdinput" >
		                                <select id="journalsheet-budgetListPage-bugettype" name = "bugettype" class="len150 " >
					    				   <option value=""></option>
						    				<c:forEach items="${budgettypeList}" var="budget">
												<option value="${budget.code}">${budget.codename}</option>
											</c:forEach>
					    				</select>	
	                                </td>
	                                
	                                
	                                <td>状态:</td>
				    				<td class="tdinput" >
				    					<select id="journalsheet-budgetListPage-state"  name="state" style="width: 150px">
						    				<option value = '' ></option>
						    				<option value = '0'>禁用</option>
						    				<option value = '1'>启用</option>
				    					</select>
	                                </td>							    							    							    				
				    			</tr>
				    			
				    			<tr>			    			
				    				<td>品牌:</td>
				    				<td class="tdinput" >
	                                    <input type="text" id="journalsheet-budgetListPage-brand" name="brand" style="width:150px;"/></td>							
									
									<td>部门:</td>
				    				<td class="tdinput" >
	                                    <input type="text" id="journalsheet-budgetListPage-deptid" name="deptid" style="width:150px;"/> 
	                                </td>	
	                                
	                                <td>供应商:</td>
				    				<td class="tdinput" >
	                                    <input type="text" id="journalsheet-budgetListPage-supplierid" name="supplierid" style="width:150px;"/> 
	                                </td>							    							    							    				
				    			</tr>
				    			
				    			<tr>
			    				<td  colspan="4"></td>
                                <td colspan="2">
                                    <a href="javaScript:void(0);" id="journalsheet-btn-budgetListPage" class="button-qr">查询</a>
                                    <a href="javaScript:void(0);" id="journalsheet-budgetListPage" class="button-qr">重置</a>
                                    <span id="storage-table-query-distributeRejectListPage-advanced"></span>
                                </td>
			    			</tr>
				    		</table>
					</form>
				</div>
			</div>
			
			<div id = "journalsheet-budgetListPage-dialog">
		</div>
   <script type="text/javascript">
   	$(function(){
   		var initQueryJSON = $("#journalsheet-form-budgetListPage").serializeJSON();
   		
   		var listJson=$("#journalsheet-table-budgetListPage").createGridColumnLoad({
				frozenCol : [[
						{field:'idok',checkbox:true,isShow:true}
    			]],
    			
    			commonCol :[[
						{field:'budgetid',title:'预算编号',width:120},
						{field:'brandname',title:'品牌',width:70},
						{field:'deptname',title:'部门',width:70},
						{field:'suppliername',title:'供应商',width:140},
						{field:'yearMonth',title:'年月',width:80,align:'right'},
						{field:'budgetnum',title:'预算金额',width:100,align:'right',
							formatter:function(value,row,index){
						        		return formatterMoney(value);
							}
						},
						{field:'bugettype',title:'预算类型',width:100,
							formatter: function(value,row,index){
								return getSysCodeName('budgettype',value);
							}
						},
						{field:'state',title:'状态',width:120,
							formatter:function(value,row,index){
								if(value =='0'){
									return "禁用"
								}else{
									return "启用"
								}
						     }
						},
						{field:'addusername',title:'录入人',width:120},
						{field:'addtime',title:'录入时间',width:140},
						{field:'remark',title:'备注',width:120}
    												
    			]]
			});
   		
   		
   		$("#journalsheet-table-budgetListPage").datagrid({
				fit:true,
		 		method:'post',
		 		rownumbers:true,
		 		pagination:true,
		 		idField:'id',
		 		singleSelect:false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
		 		showFooter: true,
				toolbar:'#journalsheet-table-query-budgetListPage',
		 		url:"journalsheet/bugetAnalyzer/firstpageData.do",
				queryParams:initQueryJSON,
                pageSize:100,
				authority : listJson,
		 		frozenColumns: listJson.frozen,
				columns:listJson.common,
				onDblClickRow:function(rowIndex, rowData){
					//有修改权限
					<security:authorize url="/journalsheet/bugetAnalyzer/bugetAnalyzerEdit.do">
						addOrEditDialog(rowData.budgetid,"edit")
						return false;
					</security:authorize>
						addOrEditDialog(rowData.budgetid,"view")
					
				}
		});
   		
   		
   		
   		$("#journalsheet-buttons-budgetListPage").buttonWidget({
				initButton:[  
				    {},       
				    <security:authorize url="/journalsheet/bugetAnalyzer/bugetAnalyzerAdd.do">
					{
						type:'button-add',
						handler: function(){
							addOrEditDialog()
						},
					},
					</security:authorize>
					<security:authorize url="/journalsheet/bugetAnalyzer/bugetAnalyzerEdit.do">
					{
						type:'button-edit',
						handler: function(){
							var rowData = $("#journalsheet-table-budgetListPage").datagrid('getSelected');
							if(rowData == null){
								$.messager.alert("提醒","请选择一条记录");
								return false;
							}
							addOrEditDialog(rowData.budgetid,"edit")
						},
					},
					</security:authorize>
					<security:authorize url="/journalsheet/bugetAnalyzer/deleteBugetAnalyzer.do">
					{
						type: 'button-delete',
						handler: function(){
							var rows = $("#journalsheet-table-budgetListPage").datagrid('getChecked');
							if(rows.length == 0){
								$.messager.alert("提醒","请至少勾选一条记录");
								return false;
							}
							
							$.messager.confirm("提醒","确定删除选中的预算分析?",function(r){
								if(r){
									var	ids="";
									for(var i=0;i<rows.length;i++){
										if(rows[i].state == '1'){
			                				$.messager.alert("提醒","抱歉,只能删除禁用的预算分析")
			                				return false;
			                			}
				                		if(ids == ""){
				                			ids = rows[i].budgetid ;
				                		}else{
				                			ids += "," + rows[i].budgetid ;
				                		}
							        }
									loading("删除中..");	
									$.ajax({
							            url :'journalsheet/bugetAnalyzer/deleteBugetAnalyzer.do?ids='+ids,
							            type:'post',
							            dataType:'json',
							            success:function(json){
							            	loaded();
							            	if(json.flag){
							            		$.messager.alert("提醒", "删除成功");
							            		$("#journalsheet-table-budgetListPage").datagrid('reload');
							            	}else{
							            		$.messager.alert("提醒", json.msg);
							            	}
							            },
							            error:function(){
							            	loaded();
							            	$.messager.alert("错误","删除出错");
							            }
							        });
									
									
								}
							});
								
							}
							
					},
					</security:authorize>
					{}
					
				],
				buttons:[
					{},
					<security:authorize url="/journalsheet/bugetAnalyzer/enableBugetAnalyzer.do">
					{
							id: 'button-budget-enable',
							name:'启用',
							iconCls:'button-audit',
							handler: function(){
								var rows = $("#journalsheet-table-budgetListPage").datagrid('getChecked');
								if(rows.length == 0){
									$.messager.alert("提醒","请选中需要启用的预算分析");
									return false;
								}
								for(var i=0;i<rows.length;i++){
									var state=rows[i].state;
									if(state == 1){
										$.messager.alert("提醒","部分选中预算分析已经启用");
										return false;
									}
								}
								$.messager.confirm("提醒","确定启用这些预算分析？",function(r){
									if(r){
											var idarrs=new Array();
											for(var i=0; i<rows.length; i++){
													idarrs.push(rows[i].budgetid);
											}
											var ids = idarrs.join(",")
											loading("启用中..");
											$.ajax({
								   				url:'journalsheet/bugetAnalyzer/enableBugetAnalyzer.do?ids='+ids,
								   				dataType:'json',
								   				type:'post',
								   				data:{idarrs:idarrs.join(",")},
								   				success:function(json){
								   					loaded();
													if(json.flag){
														$.messager.alert("提醒","启用成功");
														$("#journalsheet-table-budgetListPage").datagrid('reload');
													}else{
														$.messager.alert("提醒","启用出错");											
													}
								   				},
								   				error:function(){
								   					loaded();
								   					$.messager.alert("错误","启用出错");
								   				}
								  			});
										}
									});
								}
					},
					</security:authorize>
					<security:authorize url="/journalsheet/bugetAnalyzer/diableBugetAnalyzer.do">
					{
							id: 'button-budget-disable',
							name:'禁用',
							iconCls:'button-audit',
							handler: function(){
								var rows = $("#journalsheet-table-budgetListPage").datagrid('getChecked');
								if(rows.length == 0){
									$.messager.alert("提醒","请选中需要禁用的预算分析");
									return false;
								}
								for(var i=0;i<rows.length;i++){
									var state=rows[i].state;
									if(state == 0){
										$.messager.alert("提醒","部分选中预算分析已经禁用");
										return false;
									}
								}
								$.messager.confirm("提醒","确定禁用这些预算分析？",function(r){
									if(r){
											var idarrs=new Array();
											for(var i=0; i<rows.length; i++){
													idarrs.push(rows[i].budgetid);
											}
											var ids = idarrs.join(",")
											loading("禁用中..");
											$.ajax({
								   				url:'journalsheet/bugetAnalyzer/diableBugetAnalyzer.do?ids='+ids,
								   				dataType:'json',
								   				type:'post',
								   				data:{idarrs:idarrs.join(",")},
								   				success:function(json){
								   					loaded();
													if(json.flag){
														$.messager.alert("提醒","禁用成功");
														$("#journalsheet-table-budgetListPage").datagrid('reload');
													}else{
														$.messager.alert("提醒","禁用出错");											
													}
								   				},
								   				error:function(){
								   					loaded();
								   					$.messager.alert("错误","禁用出错");
								   				}
								  			});
										}
									});
								}
					},
					</security:authorize>
					{},
				],
				model:'bill',
				type:'list',
				datagrid:'journalsheet-table-budgetListPage',
				tname:'t_js_budget'
			});
   		
   	
   	
	   	//品牌名称
	     $("#journalsheet-budgetListPage-brand").widget({
	   			width:150,
				name:'t_base_goods_brand',
				col:'id',
				singleSelect:true,
				onlyLeafCheck:false,
	   	 });
	     
	     
	     //供应商
	  	$("#journalsheet-budgetListPage-supplierid").supplierWidget({ 
			name:'t_purchase_buyorder',
			col:'supplierid',
			singleSelect:true,
			onlyLeafCheck:true,
			onSelect:function(data){
			}
		});
		//部门
		$("#journalsheet-budgetListPage-deptid").widget({ 
    			name:'t_sales_order',
				col:'salesdept',
    			singleSelect:true,
    			width:150,
    			onlyLeafCheck:false
    	});
		
		
	 	//查询
		$("#journalsheet-btn-budgetListPage").click(function(){
			$("#journalsheet-table-budgetListPage").datagrid('clearChecked');
			$("#journalsheet-table-budgetListPage").datagrid('clearSelections');
      		var queryJSON = $("#journalsheet-form-budgetListPage").serializeJSON();;
			$('#journalsheet-table-budgetListPage').datagrid('load',queryJSON);			
		});
		//重置
		$("#journalsheet-budgetListPage").click(function(){
			$("#journalsheet-table-budgetListPage").datagrid('clearChecked');
			$("#journalsheet-table-budgetListPage").datagrid('clearSelections');
			
			$("#journalsheet-budgetListPage-brand").widget('clear');
			$("#journalsheet-budgetListPage-supplierid").supplierWidget('clear');
			$("#journalsheet-budgetListPage-deptid").widget('clear')
			$("#journalsheet-form-budgetListPage")[0].reset();
      		var queryJSON = $("#journalsheet-form-budgetListPage").serializeJSON();;
			$('#journalsheet-table-budgetListPage').datagrid('load',queryJSON);		
		});
		
		
		
		
		
		
		
   	
   	})
   	
   	 //新增或者修改Dialog
   	 
	 function addOrEditDialog(id,type){
			var $DetailOper=$("#journalsheet-budgetListPage-dialog");
			parent.$.dialog=$DetailOper;
			parent.$.dg=$("#journalsheet-table-budgetListPage")
			var href = "journalsheet/bugetAnalyzer/showaddOrEditPage.do"
			var title = "预算明细录入";
			if(id&&type){
				//修改
				href = href +"?id="+id+"&type="+type
				title = "预算明细修改"
				if(type=="view"){
					title = "预算明细查看"
				}
			}
			$DetailOper.dialog({
				title:title,
			    width: 540,  
			    height: 320,
			    closed: true,  
			    cache: false, 
			    modal: true,
			    resizable:true,
			    href:href,
			    onLoad:function(){
			    }
			});
			$DetailOper.dialog("open");
     }
     
     
    
     
     
     
     
     
     
     
     
     
   </script>
   
  </body>
</html>
