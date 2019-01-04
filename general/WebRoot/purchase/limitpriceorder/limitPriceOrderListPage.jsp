<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>采购</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
  	<div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'north',border:false">
    		<div class="buttonBG" id="purchase-buttons-limitPriceOrderListPage"></div>
    	</div>
    	<div data-options="region:'center'">
    		<table id="purchase-table-limitPriceOrderListPage"></table>
    		<div id="purchase-table-query-limitPriceOrderListPage" style="padding:2px;height:auto">
				<div>
					<form action="" id="purchase-form-limitPriceOrderListPage" method="post">
						<table>
			    			<tr>
			    				<td>业务日期:</td>
			    				<td><input type="text" name="businessdatestart" style="width:80px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /> 到 <input type="text" name="businessdateend" class="Wdate" style="width:80px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
			    				<td>编号:</td>
			    				<td><input type="text" name="id" style="width: 150px;"/></td>
			    				<td>状态:</td>
			    				<td>
			    					<select name="isClose" style="width:100px;"><option></option><option value="0" selected="selected">保存</option><option value="1">审核通过</option><option value="2">关闭</option></select>
			    				</td>
			    			</tr>
			    			<tr>
			    				<td>生效日期:</td>
			    				<td><input type="text" name="effectstartdatest" style="width:80px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /> 到 <input type="text" name="effectenddateed" class="Wdate" style="width:80px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
			    				<td colspan="2">
			    					<a href="javaScript:void(0);" id="purchase-btn-queryLimitPriceOrderListPage" class="easyui-linkbutton" iconCls="icon-search" plain="true" title="[Alt+Q]查询">查询</a>
									<a href="javaScript:void(0);" id="purchase-btn-reloadLimitPriceOrderListPage" class="easyui-linkbutton" iconCls="icon-reload" plain="true" title="[Alt+R]重置">重置</a>
									<span id="purchase-table-query-limitPriceOrderListPage-advanced"></span>
			    				</td>
			    			</tr>
			    		</table>
					</form>
				</div>
			</div>
    	</div>
    </div>
    
    <script type="text/javascript">
    	$(document).ready(function(){
    		var initQueryJSON = $("#purchase-form-limitPriceOrderListPage").serializeJSON();
    		var limitPriceOrderListJson = $("#purchase-table-limitPriceOrderListPage").createGridColumnLoad({
				name:'purchase_limitPriceorder',
				frozenCol : [[
    			]],
    			commonCol :[[
 								{field:'id',title:'编号',width:120,sortable:true},
    							{field:'businessdate',title:'业务日期',width:70,sortable:true},
    							{field:'applydeptid',title:'申请部门',width:100,
    								formatter: function(value,row,index){
    									return row.applydeptname;
    								}
    							},
    							{field:'applyuserid',title:'申请人',width:100,
    								formatter: function(value,row,index){
										return row.applyusername;
									}
								},
    							{field:'effectstartdate',title:'开始生效日期',width:90},
    							{field:'effectenddate',title:'有效截止日期',width:90},
    							{field:'addusername',title:'制单人',width:80},
    							{field:'adddeptname',title:'制单人部门',width:100,hidden:true},
    							{field:'modifyusername',title:'修改人',width:120,hidden:true},
    							{field:'auditusername',title:'审核人',width:100,hidden:true},
    							{field:'audittime',title:'审核时间',width:100,hidden:true,sortable:true},
    							{field:'status',title:'状态',width:100,
    								formatter: function(value,row,index){
										return getSysCodeName('status',value);
									}
								},    							    							
    							{field:'remark',title:'备注',width:180},
    							{field:'addtime',title:'制单时间',width:120,sortable:true},
    							{field:'printtimes',title:'打印次数',width:80}
    			]]
			});
			$("#purchase-table-limitPriceOrderListPage").datagrid({
				fit:true,
		 		method:'post',
		 		rownumbers:true,
		 		pagination:true,
		 		idField:'id',
		 		singleSelect:true,
				toolbar:'#purchase-table-query-limitPriceOrderListPage',
		 		url:"purchase/limitpriceorder/showLimitPriceOrderPageList.do",
				queryParams:initQueryJSON,
		 		sortName:'addtime',
		 		sortOrder:'desc',
				authority : limitPriceOrderListJson,
		 		frozenColumns: limitPriceOrderListJson.frozen,
				columns:limitPriceOrderListJson.common,
				onDblClickRow:function(index, data){
					top.addOrUpdateTab('purchase/limitpriceorder/limitPriceOrderPage.do?type=edit&id='+ data.id, "采购调价单查看");
		    	}
			}).datagrid("columnMoving");

			//按钮
			$("#purchase-buttons-limitPriceOrderListPage").buttonWidget({
				initButton:[
					{},
					<security:authorize url="/purchase/limitpriceorder/limitPriceOrderAddBtn.do">
					{
						type:'button-add',
						handler: function(){
							top.addOrUpdateTab('purchase/limitpriceorder/limitPriceOrderPage.do','采购调价单新增');
						}
					},
					</security:authorize>
					<security:authorize url="/purchase/limitpriceorder/limitPriceOrderEditBtn.do">
					{
						type:'button-edit',
						handler: function(){
							var datarow = $("#purchase-table-limitPriceOrderListPage").datagrid('getSelected');
							if(datarow==null ||  datarow.id ==null){
			  		        	$.messager.alert("提醒","请选择要修改的采购调价单");
								return false;
							}
							top.addOrUpdateTab('purchase/limitpriceorder/limitPriceOrderPage.do?type=edit&id='+datarow.id,'采购调价单修改');
						}
					},
					</security:authorize>
					<security:authorize url="/purchase/limitpriceorder/limitPriceOrderViewBtn.do">
					{
						type:'button-view',
						handler: function(){
							var datarow = $("#purchase-table-limitPriceOrderListPage").datagrid('getSelected');
							if(datarow==null ||  datarow.id ==null){
			  		        	$.messager.alert("提醒","请选择要查看的采购调价单");
								return false;
							}
							top.addOrUpdateTab('purchase/limitpriceorder/limitPriceOrderPage.do?type=edit&id='+datarow.id,'采购调价单查看');
						}
					},
					</security:authorize>
					<security:authorize url="/purchase/limitpriceorder/limitPriceOrderImport.do">
					{
						type: 'button-import',
						attr: {
						
						}
					},
					</security:authorize>
					<security:authorize url="/purchase/limitpriceorder/limitPriceOrderExport.do">
					{
						type: 'button-export',
						attr: {
						
						}
					},
					</security:authorize>
					{}
				],
				model:'bill',
				type:'list',
				datagrid:'purchase-table-limitPriceOrderListPage',
				tname:'t_purchase_limitPriceorder'
			});

 			$("#purchase-table-query-limitPriceOrderListPage-advanced").advancedQuery({
		 		name:'purchase_limitPriceorder',
		 		plain:true,
		 		datagrid:'purchase-table-limitPriceOrderListPage'
			});
			
			//回车事件
			controlQueryAndResetByKey("purchase-btn-queryLimitPriceOrderListPage","purchase-btn-reloadLimitPriceOrderListPage");
			
			$("#purchase-btn-queryLimitPriceOrderListPage").click(function(){
				//查询参数直接添加在url中         
       			var queryJSON = $("#purchase-form-limitPriceOrderListPage").serializeJSON();					
 				$('#purchase-table-limitPriceOrderListPage').datagrid('load',queryJSON);				
			});
			$("#purchase-btn-reloadLimitPriceOrderListPage").click(function(){
				$("#purchase-form-limitPriceOrderListPage")[0].reset();
       			var queryJSON = $("#purchase-form-limitPriceOrderListPage").serializeJSON();					
 				$('#purchase-table-limitPriceOrderListPage').datagrid('load',queryJSON);	
			});
			$("#purchase-limitPriceOrderListPage-applyuser").widget({ 
				name:'t_purchase_limitpriceorder',
				col:'applyuserid',
				width:130,
				required:true,
				singleSelect:true,
				onlyLeafCheck:true,
				onSelect: function(data){
				}
			});	
    	});
    </script>
  </body>
</html>
