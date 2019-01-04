<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>客户档案列表</title>
    <%@include file="/include.jsp" %>   
  </head>
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<div data-options="region:'north',border:false" style="height:30px;overflow: hidden">
    		<div class="buttonBG" id="sales-buttons-customerListPage"></div>
    	</div>
    	<div data-options="region:'west',split:true" style="width:180px;">
    		<div class="easyui-tabs" data-options="fit:true,border:false">
    			<div title="区域">
    				<div class="ztree" id="sales-areaTree-customerListPage"></div>
    			</div>
    			<div title="分类">
    				<div class="ztree" id="sales-sortTree-customerListPage"></div>
    			</div>
    		</div>
    	</div>
    	<div data-options="region:'center',border:false" title="客户档案列表">
    		<div id="sales-queryDiv-customerListPage" style="padding:5px;">
				<form id="sales-queryForm-customerListPage">
					<input type="hidden" name="type" id="sales-type-customerListPage" />
					<input type="hidden" name="sortarea" id="sales-sortarea-customerListPage" />
					<table cellpadding="1" cellspacing="0" border="0">
						<tr>
							<td>编号：</td>
							<td><input type="text" name="id" /></td>
							<td>名称：</td>
							<td><input type="text" name="name" /></td>
							<td>助记符：</td>
							<td><input type="text" name="shortcode" /></td>
						</tr>
						<tr>
							<td>区域：</td>
							<td><input type="text" name="salesarea" id="customerlist-widget-salesarea" /></td>
							<td colspan="4">
								<a href="javascript:;" class="button-qr" id="sales-queryBtn-customerListPage" >查询</a>
				  				<a href="javaScript:;" id="sales-resetQueryBtn-customerListPage" class="button-qr">重置</a>
				  				<span id="sales-queryAdvanced-customerListPage"></span>
							</td>
						</tr>
					</table>
				</form>
			</div>
    		<table id="sales-datagrid-customerListPage" data-options="border:false"></table>
    	</div>
    </div>
    <input type="hidden" id="sales-customerids-allotPSNCustomer"/>
    <div id="sales-dialog-customerEditMore"></div>
    <div id="sales-dialog-allotPSNCustomer"></div>
	<div id="sales-dialog-clearPSNCustomer"></div>
    <script type="text/javascript">
    	//加锁
	    function isDoLockData(id,tablename){
	    	var flag = false;
	    	$.ajax({   
	            url :'system/lock/isDoLockData.do',
	            type:'post',
	            data:{id:id,tname:tablename},
	            dataType:'json',
	            async: false,
	            success:function(json){
	            	flag = json.flag
	            }
	        });
	        return flag;
	    }
	    
	    //所属区域
	    function salesareaFunc(){
		  	$("#customerlist-widget-salesarea").widget({
		  		width:130,
				name:'t_base_sales_customer',
				col:'salesarea',
				singleSelect:true,
				onlyLeafCheck:true,
				onSelect:function(data){
					$("#sales-type-customerListPage").val("0");
				},
				onClear:function(){
					var areaTree=$.fn.zTree.getZTreeObj("sales-areaTree-customerListPage");
					if(areaTree.getSelectedNodes().length != 0){
						$("#sales-type-customerListPage").val("1");
						$("#sales-sortarea-customerListPage").val(areaTree.getSelectedNodes()[0].id);
					}
					var sortTree=$.fn.zTree.getZTreeObj("sales-sortTree-customerListPage");
					if(sortTree.getSelectedNodes().length != 0){
						$("#sales-type-customerListPage").val("2");
						$("#sales-sortarea-customerListPage").val(sortTree.getSelectedNodes()[0].id);
					}
				}
			});
	    }
	    
    	$(function(){
    		salesareaFunc();
    		
    		var customerListColJson = $("#sales-datagrid-customerListPage").createGridColumnLoad({
    			name:'t_base_sales_customer',
		     	frozenCol:[[{field:'ck',checkbox:true}]],
		     	commonCol:[[{field:'id',title:'编码',sortable:true,width:60},
		     				{field:'name',title:'客户名称',sortable:true,width:180},
		     				{field:'pid',title:'上级客户',sortable:true,width:80,
		     					formatter:function(val,rowData,rowIndex){
									return rowData.pname;
								}
		     				},
		     				{field:'shortcode',title:'助记符',sortable:true,width:60},
		     				{field:'contact',title:'联系人',sortable:true,width:60,
		     					formatter:function(val,rowData,rowIndex){
									return rowData.contactname;
								}
		     				},
		     				{field:'mobile',title:'联系人电话',sortable:true,width:80,isShow:true},
		     				{field:'payeeid',title:'收款人',sortable:true,width:60,
		     					formatter:function(val,rowData,rowIndex){
									return rowData.payeename;
								}
		     				},
		     				{field:'address',title:'详细地址',sortable:true,width:200},
		     				{field:'settletype',title:'结算方式',sortable:true,width:80,
		     					formatter:function(val,rowData,rowIndex){
									return rowData.settletypename;
								}
		     				},
		     				{field:'settleday',title:'结算日',sortable:true,width:50},
		     				{field:'pricesort',title:'价格套',sortable:true,width:50,
		     					formatter:function(val,rowData,rowIndex){
									return rowData.pricesortname;
								}
		     				},
		     				{field:'promotionsort',title:'促销分类',sortable:true,width:60,
		     					formatter:function(val,rowData,rowIndex){
									return rowData.promotionsortname;
								}
		     				},
		     				{field:'salesarea',title:'所属区域',sortable:true,width:60,
		     					formatter:function(val,rowData,rowIndex){
									return rowData.salesareaname;
								}
		     				},
		     				{field:'customersort',title:'所属分类',sortable:true,width:80,
		     					formatter:function(val,rowData,rowIndex){
									return rowData.customersortname;
								}
		     				},
		     				{field:'salesuserid',title:'客户业务员',sortable:true,width:80,
		     					formatter:function(val,rowData,rowIndex){
									return rowData.salesusername;
								}
		     				},
		     				{field:'state',title:'状态',sortable:true,width:40,
		     					formatter:function(value,row,index){
						        	return row.statename;
							    }
		     				}
		     			]]
    		});
    		$("#sales-datagrid-customerListPage").datagrid({
    			authority:customerListColJson,
	  	 		frozenColumns:customerListColJson.frozen,
				columns:customerListColJson.common,
    			fit:true,
				title:'',
				border:false,
				rownumbers:true,
				pagination:true,
				pageSize:100,
				idField:'id',
				singleSelect:false,
				url:'basefiles/getCustomerList.do',
				toolbar:"#sales-queryDiv-customerListPage",
				onDblClickRow:function(rowIndex, rowData){
					top.addTab('basefiles/customerPage.do?type=view&id='+ encodeURIComponent(rowData.id), "客户档案查看");
				},
				onSelect:function(rowIndex, rowData){
					$(this).datagrid('checkRow',rowIndex);
				},
				onUnselect:function(rowIndex, rowData){
					$(this).datagrid('uncheckRow',rowIndex);
				}
    		}).datagrid("columnMoving");
    		var salseAreaTreeSetting = { //区域树
				view: {
					dblClickExpand: false,
					showLine: true,
					selectedMulti: false,
					showIcon:true,
					expandSpeed: ($.browser.msie && parseInt($.browser.version)<=6)?"":"fast"
				},
				async: {
					enable: true,
					url: "basefiles/getSalesAreaTree.do",
					autoParam: ["id","pId", "name","state"]
				},
				data: {
					key:{
						title:"name"
					},
					simpleData: {
						enable:true,
						idKey: "id",
						pIdKey: "pid",
						rootPId: null
					}
				},
				callback: {
					//点击树状菜单更新页面按钮列表
					beforeClick: function(treeId, treeNode) {
						if(treeNode.id == ""){
							$("#sales-type-customerListPage").val("0");
						}
						else{
							$("#sales-type-customerListPage").val("1");
						}
						$("#sales-sortarea-customerListPage").val(treeNode.id);
						//所属区域
					  	//$("#customerlist-widget-salesarea").widget({
					  	//	width:130,
						//	name:'t_base_sales_customer',
						//	col:'salesarea',
						//	singleSelect:true,
						//	param:[{field:'id',op:'startwith',value:treeNode.id}],
						//	onlyLeafCheck:true
						//});
						$.fn.zTree.getZTreeObj("sales-sortTree-customerListPage").refresh();
						$("#sales-queryBtn-customerListPage").click();
						var zTree = $.fn.zTree.getZTreeObj("sales-areaTree-customerListPage");
						if (treeNode.isParent) {
							if (treeNode.level == 0) {
								zTree.expandAll(false);
								zTree.expandNode(treeNode);
							} else {
								zTree.expandNode(treeNode);
							}
						}
						return true;
					}
				}
			};
			$.fn.zTree.init($("#sales-areaTree-customerListPage"), salseAreaTreeSetting,null);
    		var customerSortTreeSetting = { //分类树
				view: {
					dblClickExpand: false,
					showLine: true,
					selectedMulti: false,
					showIcon:true,
					expandSpeed: ($.browser.msie && parseInt($.browser.version)<=6)?"":"fast"
				},
				async: {
					enable: true,
					url: "basefiles/getCustomerSortTree.do",
					autoParam: ["id","pid", "name","state"]
				},
				data: {
					key:{
						title:"name"
					},
					simpleData: {
						enable:true,
						idKey: "id",
						pIdKey: "pid",
						rootPId: null
					}
				},
				callback: {
					//点击树状菜单更新页面按钮列表
					beforeClick: function(treeId, treeNode) {
						if(treeNode.id == ""){
							$("#sales-type-customerListPage").val("0");
						}
						else{
							$("#sales-type-customerListPage").val("2");
						}
						$("#sales-sortarea-customerListPage").val(treeNode.id);
						$.fn.zTree.getZTreeObj("sales-areaTree-customerListPage").refresh()
						$("#sales-queryBtn-customerListPage").click();
						var zTree = $.fn.zTree.getZTreeObj("sales-sortTree-customerListPage");
						if (treeNode.isParent) {
							if (treeNode.level == 0) {
								zTree.expandAll(false);
								zTree.expandNode(treeNode);
							} else {
								zTree.expandNode(treeNode);
							}
						}
						return true;
					}
				}
			};
			$.fn.zTree.init($("#sales-sortTree-customerListPage"), customerSortTreeSetting,null);
			//通用查询组建调用
			$("#sales-queryAdvanced-customerListPage").advancedQuery({
		 		name:'base_sales_customer',
		 		datagrid:'sales-datagrid-customerListPage'
			});
			
			//回车事件
			controlQueryAndResetByKey("sales-queryBtn-customerListPage","sales-resetQueryBtn-customerListPage");
			
			$("#sales-queryBtn-customerListPage").click(function(){
	       		var queryJSON = $("#sales-queryForm-customerListPage").serializeJSON();
	       		$("#sales-datagrid-customerListPage").datagrid('load', queryJSON);
			});
			$("#sales-resetQueryBtn-customerListPage").click(function(){
				$("#sales-queryForm-customerListPage")[0].reset();
				//salesareaFunc();
				$("#customerlist-widget-salesarea").widget('clear');
				$("#sales-type-customerListPage").val("0");
				$("#sales-sortarea-customerListPage").val("");
				var areaTree=$.fn.zTree.getZTreeObj("sales-areaTree-customerListPage");
				areaTree.refresh();
				var sortTree=$.fn.zTree.getZTreeObj("sales-sortTree-customerListPage");
				sortTree.refresh();
				$("#sales-datagrid-customerListPage").datagrid('load', {});
			});
			//按钮
			$("#sales-buttons-customerListPage").buttonWidget({
				initButton:[
					{},
					<security:authorize url="/basefiles/customerAdd.do">
						{
							type: 'button-add',
							handler: function(){
								top.addTab('basefiles/customerPage.do', "客户档案新增");
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/customerEdit.do">
						{
							type: 'button-edit',
							handler: function(){
								var con = $("#sales-datagrid-customerListPage").datagrid('getSelected');
								if(con == null){
									$.messager.alert("提醒","请选择一条记录");
									return false;
								}
								var flag = isDoLockData(con.id,"t_base_sales_customer");
				 				if(!flag){
				 					$.messager.alert("警告","该数据正在被其他人操作，暂不能修改！");
				 					return false;
				 				}
								top.addTab('basefiles/customerPage.do?type=edit&id='+ encodeURIComponent(con.id), "客户档案修改");
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/customerView.do">
						{
							type: 'button-view',
							handler: function(){
								var con = $("#sales-datagrid-customerListPage").datagrid('getSelected');
								if(con == null){
									$.messager.alert("提醒","请选择一条记录");
									return false;
								}
								top.addTab('basefiles/customerPage.do?type=view&id='+ encodeURIComponent(con.id), "客户档案查看");
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/customerDelete.do">
						{
							type: 'button-delete',
							handler: function(){
								var rows = $("#sales-datagrid-customerListPage").datagrid('getChecked');
								if(rows.length == 0){
									$.messager.alert("提醒","请选择客户!");
									return false;
								}
								var idStr = "";
								for(var i=0;i<rows.length;i++){
									idStr += rows[i].id + ",";
								}
								$.messager.confirm("提醒","是否删除客户档案?",function(r){
						  			if(r){
						  				loading("删除中..");
							  			$.ajax({
								  			url:'basefiles/deleteCustomerFromListPage.do',
								  			data:{idStr:idStr},
								  			dataType:'json',
								  			type:'post',
								  			success:function(json){
								  				loaded();
								  				$.messager.alert("提醒",""+json.useNum+"条记录被引用,不允许删除;<br/>"+json.lockNum+"条记录网络互斥,不允许删除;<br/>"+json.failNum+"条记录删除失败;<br/>"+json.sucNum+"条记录删除成功;");
												var queryJSON = $("#sales-queryForm-customerListPage").serializeJSON();
	       										$("#sales-datagrid-customerListPage").datagrid('load', queryJSON);
												$("#sales-datagrid-customerListPage").datagrid('clearSelections');
												$("#sales-datagrid-customerListPage").datagrid('clearChecked');
								  			}
							  			});
						  			}
						  		});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/customerCopy.do">
						{
							type: 'button-copy',
							handler: function(){
								var con = $("#sales-datagrid-customerListPage").datagrid('getSelected');
								if(con == null){
									$.messager.alert("提醒","请选择一条记录");
									return false;
								}
								top.addOrUpdateTab('basefiles/customerPage.do?type=copy&id='+ encodeURIComponent(con.id),"客户档案新增");
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/customerOpen.do">
						{
							type: 'button-open',
							handler: function(){
								var datarow = $("#sales-datagrid-customerListPage").datagrid('getChecked');
								if(datarow == null || datarow.length==0){
									$.messager.alert("提醒","请勾选要启用的客户信息");
									return false;
								}	
								$.messager.confirm("提醒", "确定启用选中客户信息?", function(r){
									if (r){
										var id = "";
										for(var i = 0; i<datarow.length; i++){
											id += datarow[i].id + ',';
										}
										loading("启用中..");
								    	$.ajax({   
								            url :'basefiles/openMultiCustomer.do',
											data:{ids:id},
											type:'post',
								            dataType:'json',
								            success:function(json){
								            	loaded();
									            if(json.flag){
								            		$.messager.alert("提醒", "启用成功数："+ json.sucNum +"<br />启用失败数："+ json.failNum + "<br />不允许启用数："+ json.noHandleNum);
									           		var queryJSON = $("#sales-queryForm-customerListPage").serializeJSON();
		       										$("#sales-datagrid-customerListPage").datagrid('load', queryJSON);
		       										$("#sales-datagrid-customerListPage").datagrid('clearSelections');
													$("#sales-datagrid-customerListPage").datagrid('clearChecked');
									            }else{
									            	$.messager.alert("提醒", "启用失败");
									            }
								            }
								        });	
									}
								});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/customerClose.do">
						{
							type: 'button-close',
							handler: function(){
								var datarow = $("#sales-datagrid-customerListPage").datagrid('getChecked');
								if(datarow == null || datarow.length==0){
									$.messager.alert("提醒","请勾选要禁用的客户信息");
									return false;
								}	
								$.messager.confirm("提醒", "确定禁用选中客户信息?", function(r){
									if (r){
										var id = "";
										for(var i = 0; i<datarow.length; i++){
											id += datarow[i].id + ',';
										}
										loading("禁用中..");
								    	$.ajax({   
								            url :'basefiles/closeMultiCustomer.do',
											data:{ids:id},
								            type:'post',
								            dataType:'json',
								            success:function(json){
								            	loaded();
												if(json.flag){
								            		$.messager.alert("提醒", "禁用成功数："+ json.sucNum +"<br />禁用失败数："+ json.failNum + "<br />不允许禁用数："+ json.noHandleNum);
													var queryJSON = $("#sales-queryForm-customerListPage").serializeJSON();
		       										$("#sales-datagrid-customerListPage").datagrid('load', queryJSON);
		       										$("#sales-datagrid-customerListPage").datagrid('clearSelections');
													$("#sales-datagrid-customerListPage").datagrid('clearChecked');
												}else{	
									            	$.messager.alert("提醒", "禁用失败");
												}
								            }
								        });	
									}
								});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/customerImportBtn.do">
						{
							type:"button-import",
							attr:{
								clazz: "salesService",
						 		methodjson: {t_base_sales_customer:'addDRCustomer',t_base_sales_customer_sort:'addDRCustomerAndSort',t_base_sales_customer_price:'addDRCustomerAndPrice'}, //插入数据库的方法
						 		tnjson: {客户列表:'t_base_sales_customer',对应分类:'t_base_sales_customer_sort',合同商品:'t_base_sales_customer_price'},//表名
					            module: 'basefiles', //模块名，
						 		pojojson: {t_base_sales_customer:'Customer',t_base_sales_customer_sort:'CustomerAndSort',t_base_sales_customer_price:'CustomerPrice'}, //实体类名
								type:'importmore',
								majorkey:'id',
								version:'2',
								childkey:'customerid',
								shortcutname:'customer',
								maintn:'t_base_sales_customer',
								importparam:'是否总店不能为空,否则将默认为门店',//参数描述
						 		onClose:function(){
						 			var queryJSON = $("#sales-queryForm-customerListPage").serializeJSON();
	       							$("#sales-datagrid-customerListPage").datagrid('load', queryJSON);
						 		}
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/customerExportBtn.do">
						{
	    					type:'button-export',
	    					attr:{
	    						datagrid: "#sales-datagrid-customerListPage",
	    						queryForm: "#sales-queryForm-customerListPage",
						 		tnstr:'t_base_sales_customer,t_base_sales_customer_sort,t_base_sales_customer_price',//表名
						 		tnjson: {t_base_sales_customer:'客户列表',t_base_sales_customer_sort:'对应分类',t_base_sales_customer_price:'合同商品'},
						 		type:'exportmore',
						 		version:'2',
						 		maintn:'t_base_sales_customer',
						 		shortcutname:'customer',
						 		queryparam:'id,name,shortcode,salesarea',//查询字段
						 		sort:'customersort,salesarea',
						 		childkey:'customerid',
						 		exportparam:'是否门店字段设置导出,否则,导入时将默认为门店',//参数描述
						 		name: '客户档案管理'
	    					}
	    				},
    				</security:authorize>
    				{}
				],
				buttons:[
					{},
					<security:authorize url="/basefiles/customerEditMoreBtn.do">
					{
						id:'editMore',
						name:'批量修改',
						iconCls:'button-edit',
						handler:function(){
							var customerRows = $("#sales-datagrid-customerListPage").datagrid('getChecked');
			 				if(customerRows.length == 0){
			 					$.messager.alert("提醒","请勾选客户!");
								return false;
			 				}
			 				var idStr = "",flagIdStr = "";
			 				var unInvNum = 0;
			 				for(var i=0;i<customerRows.length;i++){
			 					var id = customerRows[i].id;
			 					var flag = isDoLockData(id,"t_base_sales_customer");
			 					if(!flag){
			 						flagIdStr += id + ",";
			 						unInvNum++;
			 						var index = $("#sales-datagrid-customerListPage").datagrid('getRowIndex',customerRows[i]);
			 						$("#sales-datagrid-customerListPage").datagrid('uncheckRow',index);
			 					}
			 					else{
			 						idStr += id + ",";
			 					}
			 				}
			 				if(flagIdStr != ""){
			 					var unIds = flagIdStr.substring(0, flagIdStr.lastIndexOf(","));
			 					$.messager.alert("警告",""+unIds+"数据正在被其他人操作，暂不能修改！");
			 					return false;
			 				}
			 				$('#sales-dialog-customerEditMore').dialog({  
							    title: '批量修改客户信息',  
							    width: 550,  
							    height: 330,  
							    closed: false,  
							    cache: false,  
							    href: 'basefiles/customerMoreEditPage.do',
								queryParams:{idStr:idStr,unInvNum:unInvNum},
							    modal: true
							});
						}
					},
					</security:authorize>
					<security:authorize url="/basefiles/allotPSNCustomerBtn.do">
						{
							id:'allotpsncustomer',
							name:'分配业务员',
							iconCls:'icon-reload',
							handler:function(){
								var customerRows = $("#sales-datagrid-customerListPage").datagrid('getChecked');
				 				if(customerRows.length == 0){
				 					$.messager.alert("提醒","请勾选客户!");
									return false;
				 				}
				 				var idStr = "";
				 				for(var i=0;i<customerRows.length;i++){
				 					if("1" != customerRows[i].state){
				 						$.messager.alert("提醒","启用状态下才可分配!");
				 						$("#sales-datagrid-customerListPage").datagrid('unselectRow',$("#sales-datagrid-customerListPage").datagrid('getRowIndex',customerRows[i]));
										return false;
				 					}else{
										if(idStr == ""){
											idStr = customerRows[i].id;
										}else{
											idStr += "," + customerRows[i].id;
										}
									}
				 				}
				 				$("#sales-customerids-allotPSNCustomer").val(idStr);
								$('#sales-dialog-allotPSNCustomer').dialog({  
								    title: '分配业务员',  
								    width: 400,  
								    height: 280,  
								    closed: false,  
								    cache: false,
								    resizable:true,
								    href: 'basefiles/showAllotPSNCustomerPage.do',
									queryParams:{idStr:idStr},
								    modal: true,
								    buttons:[
								    	{  
						                    text:'确定',  
						                    iconCls:'button-save',
						                    plain:true,
						                    handler:function(){
						                    	var perids = getPersonidsValue();
						                    	var delperids = getDelPersonidsValue();
						                    	if("" == perids && "" == delperids){
						                    		$.messager.alert("提醒","请选择要分配的业务员!");
						                    		return false;
						                    	}
						                    	getCompanyValue();
						                    	getPersonidKeyInitPersonidVal();
						                    	allotCustomerToPsn_form_submit();
						                    	$("#sales-customer-allotCustomerToPsn").submit();
						                    }  
						                }
								    ]
								});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/clearPSNCustomerBtn.do">
					{
						id:'clearpsncustomer',
						name:'清除业务员',
						iconCls:'assign-user',
						handler:function(){
							var customerRows = $("#sales-datagrid-customerListPage").datagrid('getChecked');
							if(customerRows.length == 0){
								$.messager.alert("提醒","请勾选客户!");
								return false;
							}
							var idStr = "";
							for(var i=0;i<customerRows.length;i++){
								if("1" != customerRows[i].state){
									$.messager.alert("提醒","启用状态下才可清除!");
									$("#sales-datagrid-customerListPage").datagrid('unselectRow',$("#sales-datagrid-customerListPage").datagrid('getRowIndex',customerRows[i]));
									return false;
								}else{
									if(idStr == ""){
										idStr = customerRows[i].id;
									}else{
										idStr += "," + customerRows[i].id;
									}
								}
							}
							if(idStr != ""){
								$('#sales-dialog-clearPSNCustomer').dialog({
									title: '清除业务员',
									width: 300,
									height: 200,
									closed: false,
									cache: false,
									resizable:true,
									href: 'basefiles/showClearPSNCustomerPage.do',
									queryParams:{customerids:idStr},
									modal: true,
									buttons:[
										{
											text:'确定',
											iconCls:'button-save',
											plain:true,
											handler:function(){
												claerCustomerToPsn_form_submit();
												$("#sales-customer-clearCustomerToPsn").submit();
											}
										}
									]
								});
							}
						}
					},
					</security:authorize>
					{}
				],
				model:'base',
				type:'multipleList',
				tname: 't_base_sales_customer'
			});
    	});
    </script>
  </body>
</html>
