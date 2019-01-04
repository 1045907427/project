<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>客户应付费用期初列表</title>
    <%@include file="/include.jsp" %>
  </head>
  <body>
  <div class="easyui-layout" data-options="fit:true">
      <div data-options="region:'north',border:false">
          <div class="buttonBG" id="customerCostPayableInit-buttons-detaillist"></div>
      </div>
      <div data-options="region:'center'">
          <table id="customerCostPayableInit-table-detail"></table>
          <div id="customerCostPayableInit-form-div" style="padding:2px;height:auto">
              <form action="" id="customerCostPayableInit-form-ListQuery" method="post">
                  <table class="querytable">
                      <tr>
                          <td>业务日期：</td>
                          <td>
                              <input type="text" name="businessdate" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${day1 }"/> 到 <input type="text" name="businessdate1" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${day2 }"/>
                          </td>
                          <td>销售区域：</td>
                          <td>
                              <input type="text" id="customerCostPayableInit-form-salesarea" name="salesarea"/>
                          </td>
                      </tr>
                      <tr>
                          <td>客户名称：</td>
                          <td>
                              <input id="customerCostPayableInit-form-customerid" name="customerid" type="text" style="width: 225px;"/>
                          </td>
                          <td>费用分类：</td>
                          <td>
                              <input type="text" id="customerCostPayableInit-form-expensesort" name="expensesort"/>
                          </td>
                          <td colspan="2">
                              <a href="javaScript:void(0);" id="departmentCosts-query-List" class="button-qr">查询</a>
                              <a href="javaScript:void(0);" id="departmentCosts-query-reloadList" class="button-qr">重置</a>
                              <a href="javaScript:void(0);" id="departmentCosts-export-List" style="display: none"title="导出">导出</a>
                              <a href="javaScript:void(0);" id="departmentCosts-import-List" style="display: none"title="导入">导入</a>
                          </td>
                      </tr>
                  </table>
              </form>
          </div>
          <div id="customerCostPayableInit-dialog-detail"></div>
      </div>
  </div>
    <script type="text/javascript">
    	var footerobject = null;
    	var initQueryJSON =  $("#customerCostPayableInit-form-ListQuery").serializeJSON();
     	$(function(){
     		//根据初始的列与用户保存的列生成以及字段权限生成新的列
        	var tableColJson=$("#customerCostPayableInit-table-detail").createGridColumnLoad({
    	     	frozenCol:[[
    				{field:'ck',checkbox:true,isShow:true}
    	     	]],
    	     	commonCol:[[
    	    		{field:'id',title:'编码',width:130,sortable:true},
    	     		{field:'businessdate',title:'业务日期',width:80,sortable:true},
    				{field:'customerid',title:'客户编号',width:80,sortable:true},
    				{field:'customername',title:'客户名称',width:200,sortable:true},
    				{field:'salesareaname',title:'销售区域',width:80},
    				{field:'expensesort',title:'费用分类',width:80,sortable:true,
    					formatter:function(val,rowData,rowIndex){
    						return rowData.expensesortname;
    					}
    				},
    				{field:'amount',title:'应付金额',width:80,sortable:true,align:'right',
    					formatter:function(val,rowData,rowIndex){
    						if(val != "" && val != null){
    							return formatterMoney(val);
    						}
    						else{
    							return "0.00";
    						}
    					}
    				},
    				{field:'applyusername',title:'制单人',width:80,sortable:true},
    				{field:'addtime',title:'制单时间',width:130,sortable:true,
    					formatter:function(val,rowData,rowIndex){
    						if(val){
    							return val.replace(/[tT]/," ");
    						}
    					}
    				},
    				{field:'remark',title:'备注',width:100}
    			]]
    	     });
        	$("#customerCostPayableInit-buttons-detaillist").buttonWidget({
    			initButton:[
    				{},
    				<security:authorize url="/journalsheet/customercost/showCustomerCostPayableInitAddPage.do">
    				{
    					type: 'button-add',
    					handler: function(){
    						$('#customerCostPayableInit-dialog-detail').dialog({  
    						    title: '客户应付费用期初新增',  
    						    width: 650,  
    						    height: 310,  
    						    collapsible:false,
    						    minimizable:false,
    						    maximizable:true,
    						    resizable:true,
    						    closed: true,  
    						    cache: false,  
    						    href: 'journalsheet/customercost/showCustomerCostPayableInitAddPage.do',  
    						    modal: true,
    						    onLoad:function(){
    						    	$("#customerCostPayableInit-detail-customerid").focus();
    						    }
    						});
    						$('#customerCostPayableInit-dialog-detail').dialog("open");
    					}
    				},
    				</security:authorize>
    				<security:authorize url="/journalsheet/customercost/showCustomerCostPayableInitViewPage.do">
    				{
    					type: 'button-view',
    					handler: function(){
    						var con = $("#customerCostPayableInit-table-detail").datagrid('getSelected');
    						if(con == null){
    							$.messager.alert("提醒","请选择一条记录");
    							return false;
    						}	
    						$('#customerCostPayableInit-dialog-detail').dialog({  
    						    title: '客户应付费用期初',  
    						    width: 650,  
    						    height: 310,  
    						    collapsible:false,
    						    minimizable:false,
    						    maximizable:true,
    						    resizable:true,
    						    closed: true,  
    						    cache: false,  
    						    href: 'journalsheet/customercost/showCustomerCostPayableInitViewPage.do?id='+con.id,  
    						    modal: true
    						});
    						$('#customerCostPayableInit-dialog-detail').dialog("open");
    					}
    				},
    				</security:authorize>
    				<security:authorize url="/journalsheet/customercost/showCustomerCostPayableInitEditPage.do">
    				{
    					type: 'button-edit',
    					handler: function(){
    						var con = $("#customerCostPayableInit-table-detail").datagrid('getSelected');
    						if(con == null){
    							$.messager.alert("提醒","请选择一条记录");
    							return false;
    						}	
    						$('#customerCostPayableInit-dialog-detail').dialog({  
    						    title: '客户应付费用期初',  
    						    width: 650,  
    						    height: 310,  
    						    collapsible:false,
    						    minimizable:false,
    						    maximizable:true,
    						    resizable:true,
    						    closed: true,  
    						    cache: false,  
    						    href: 'journalsheet/customercost/showCustomerCostPayableInitEditPage.do?id='+con.id,  
    						    modal: true,
                                onLoad:function(){
                                    $("#customerCostPayableInit-detail-customerid").focus();
                                    $("#customerCostPayableInit-detail-customerid").select();
                                }
    						});
    						$('#customerCostPayableInit-dialog-detail').dialog("open");
    					}
    				},
    				</security:authorize>
    				<security:authorize url="/journalsheet/customercost/deleteCustomerCostPayableInit.do">
    					{
    						type: 'button-delete',
    						handler: function(){
    							$.messager.confirm("提醒","是否删除当前客户应付费用期初？",function(r){
    								if(r){
    									var rows = $("#customerCostPayableInit-table-detail").datagrid("getChecked");
    									var ids = "";
    									for(var i=0;i<rows.length;i++){
    										if(ids==""){
    											ids = rows[i].id;
    										}else{
    											ids += ","+ rows[i].id;
    										}
    									}
    									if(ids!=""){
    										loading("提交中..");
    										$.ajax({   
    								            url :'journalsheet/customercost/deleteCustomerCostPayableInit.do?ids='+ids,
    								            type:'post',
    								            dataType:'json',
    								            success:function(json){
    								            	loaded();
    								            	if(json.flag){
    								            		$.messager.alert("提醒", "删除成功！</br>"+json.succssids+"</br>"+json.errorids);
    								            		$("#customerCostPayableInit-table-detail").datagrid("reload");
    								            	}else{
    								            		$.messager.alert("提醒", "删除失败！");
    								            	}
    								            },
    								            error:function(){
    								            	$.messager.alert("错误", "删除出错！");
    								            	loaded();
    								            }
    								        });
    									}
    								}
    							});
    						}
    					},
    				</security:authorize>
    				{}
    			],
    			buttons:[
						<security:authorize url="/journalsheet/customercost/importCustomerCostPayableInitList.do">
							{
								id:'button-import-excel',
								name:'导入',
								iconCls:'button-import',
								handler: function(){
									$("#departmentCosts-import-List").Excel('import',{
										type:'importUserdefined',
							 			url:'journalsheet/customercost/importCustomerCostPayableInitList.do',
										onClose: function(){ //导入成功后窗口关闭时操作，
									        var queryJSON = $("#customerCostPayableInit-form-ListQuery").serializeJSON();
									        $("#customerCostPayableInit-table-detail").datagrid("load",queryJSON);
										}
									});
									$("#departmentCosts-import-List").trigger("click");
								}				
							},
							</security:authorize>					
							<security:authorize url="/journalsheet/customercost/exportCustomerCostPayableInitList.do">
							{
								id:'button-export-excel',
								name:'导出',
								iconCls:'button-export',
								handler: function(){
									$("#departmentCosts-export-List").Excel('export',{
										queryForm: "#customerCostPayableInit-form-ListQuery",
								 		type:'exportUserdefined',
								 		name:'客户应付费用期初',
								 		url:'journalsheet/customercost/exportCustomerCostPayableInitList.do'
									});
									$("#departmentCosts-export-List").trigger("click");
								}
							},
							</security:authorize>
    			         {}],
    			model: 'bill',
    			type: 'list',
    			tname: 't_js_customercost_payable'
    		});
     		$("#customerCostPayableInit-table-detail").datagrid({ 
     			authority:tableColJson,
	  	 		frozenColumns:tableColJson.frozen,
				columns:tableColJson.common,
	  	 		fit:true,
	  	 		method:'post',
	  	 		showFooter: true,
	  	 		rownumbers:true,
	  	 		sortName:'businessdate',
	  	 		sortOrder:'asc',
	  	 		pagination:true,
		 		idField:'id',
	  	 		singleSelect:false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
				pageSize:20,
				queryParams:initQueryJSON,
				toolbar:'#customerCostPayableInit-form-div',
				url: 'journalsheet/customercost/showCustomerCostPayableInitList.do',
		    	onDblClickRow:function(rowIndex, rowData){
		    		$('#customerCostPayableInit-dialog-detail').dialog({  
					    title: '客户应付费用期初',  
					    width: 650,  
					    height: 310,  
					    collapsible:false,
					    minimizable:false,
					    maximizable:true,
					    resizable:true,
					    closed: true,  
					    cache: false,  
					    href: 'journalsheet/customercost/showCustomerCostPayableInitViewPage.do?id='+rowData.id,  
					    modal: true
					});
					$('#customerCostPayableInit-dialog-detail').dialog("open");
		    	},
				onLoadSuccess:function(){
					var footerrows = $(this).datagrid('getFooterRows');
					if(null!=footerrows && footerrows.length>0){
						footerobject = footerrows[0];
					}
		 		},
				onCheckAll:function(){
		 			deptCostsCountTotalAmount();
				},
				onUncheckAll:function(){
					deptCostsCountTotalAmount();
				},
				onCheck:function(){
					deptCostsCountTotalAmount();
				},
				onUncheck:function(){
					deptCostsCountTotalAmount();
				}
			}).datagrid("columnMoving");
     		$("#customerCostPayableInit-form-customerid").customerWidget({
    			width:225,
    			isall:true,
				singleSelect:true,
				onlyLeafCheck:false
     		});
     		$("#customerCostPayableInit-form-expensesort").widget({
     			 referwid:'RT_T_BASE_FINANCE_EXPENSES_SORT_1',
	       		 width:150,
	       		 onlyLeafCheck:false,
	       		 singleSelect:true
     		});
     		$("#customerCostPayableInit-form-salesarea").widget({
     			referwid:'RT_T_BASE_SALES_AREA',
	       		width:150,
	       		onlyLeafCheck:false,
	       		singleSelect:true
     		});
     		$("#departmentCosts-query-List").click(function(){
     			//把form表单的name序列化成JSON对象
	       		var queryJSON = $("#customerCostPayableInit-form-ListQuery").serializeJSON();
	       		$("#customerCostPayableInit-table-detail").datagrid("load",queryJSON);
     		});
     		
     		$("#departmentCosts-query-reloadList").click(function(){
     			$("#customerCostPayableInit-form-salesarea").widget("clear");
     			$("#customerCostPayableInit-form-expensesort").widget("clear");
     			$("#customerCostPayableInit-form-customerid").customerWidget("clear");
     			$("#customerCostPayableInit-form-ListQuery")[0].reset();
				var queryJSON =  $("#customerCostPayableInit-form-ListQuery").serializeJSON();
				$("#customerCostPayableInit-table-detail").datagrid("load",queryJSON);
     		});
     		
     	});
     	function deptCostsCountTotalAmount(){
     		var rows =  $("#customerCostPayableInit-table-detail").datagrid('getChecked');
     		var amount = 0;
     		for(var i=0;i<rows.length;i++){
  				amount = Number(amount)+Number(rows[i].amount == undefined ? 0 : rows[i].amount);
    		}
    		var foot = [
   				{
   					businessdate:'选中金额',
    				amount:amount
   				}
   			];
   			if(null!=footerobject){
        		foot.push(footerobject);
    		}
   			$("#customerCostPayableInit-table-detail").datagrid("reloadFooter",foot);
     	}
    </script>
  </body>
</html>
