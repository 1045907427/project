<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>客户档案简化版列表</title>
    <%@include file="/include.jsp" %>   
  </head>
  <body>
  	<input type="hidden" id="customerShortcut-opera"/>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<div data-options="region:'north',border:false" style="height:30px;overflow: hidden">
    		<div class="buttonBG" id="sales-buttons-customerShortcut"></div>
    	</div>
    	<div data-options="region:'center',border:false" title="">
    		<div id="sales-queryDiv-customerShortcut" style="padding:5px;">
				<form id="sales-queryForm-customerShortcut">
					<input type="hidden" name="type" id="sales-type-customerShortcut" />
					<table cellpadding="1" cellspacing="0" border="0">
						<tr>
							<td>编号：</td>
							<td><input type="text" name="id" /></td>
							<td>客户名称：</td>
							<td><input type="text" name="name" /></td>
							<td>助记符：</td>
							<td><input type="text" name="shortcode" /></td>
						</tr>
						<tr>
							<td>区域：</td>
							<td><input type="text" name="salesarea" id="customerlist-widget-salesarea" /></td>
							<td colspan="4">
								<a href="javascript:;" class="button-qr" id="sales-queryBtn-customerShortcut" >查询</a>
				  				<a href="javaScript:;" class="button-qr" id="sales-resetQueryBtn-customerShortcut" >重置</a>
				  				<span id="sales-queryAdvanced-customerShortcut"></span>
							</td>
						</tr>
					</table>
				</form>
			</div>
    		<table id="sales-datagrid-customerShortcut" data-options="border:false"></table>
    	</div>
    </div>
    <input type="hidden" id="sales-customerids-allotPSNCustomer"/>
    <div id="sales-dialog-customerShortcut"></div>
    <div id="sales-dialog-customerEditMore"></div>
    <div id="sales-dialog-allotPSNCustomer"></div>
	<div id="sales-dialog-clearPSNCustomer"></div>
    <script type="text/javascript">
		function selectControl(){
			var v = $("#customerShortcut-select-overcontrol option:selected").val();
			if(v == "0"){
				$("#customerShortcut-input-overgracedate").val("");
				changeValue("");
				$("#customerShortcut-input-overgracedate").attr("disabled","disabled");
			}
			else{
				$("#customerShortcut-input-overgracedate").removeAttr("disabled");
			}
		}
		
		function refreshLayout(title, url,type){
			top.addOrUpdateTab(url, title);
			$("#customerShortcut-opera").attr("value",type);
    	}
    	
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
	    
    	$(function(){
    		//所属区域
		  	$("#customerlist-widget-salesarea").widget({
		  		width:130,
				name:'t_base_sales_customer',
				col:'salesarea',
				singleSelect:true,
				onlyLeafCheck:true
			});
    	
    		var customerListColJson = $("#sales-datagrid-customerShortcut").createGridColumnLoad({
    			name:'t_base_sales_customer',
		     	frozenCol : [[{field:'ck',checkbox:true,isShow:true}]],
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
		     				{field:'contactmobile',title:'联系人电话',sortable:true,width:80,isShow:true},
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
		     				{field:'state',title:'状态',width:40,sortable:true,
		     					formatter:function(value,row,index){
						        	return row.statename;
							    }
		     				}
		     			]]
    		});
    		$("#sales-datagrid-customerShortcut").datagrid({
    			authority:customerListColJson,
	  	 		frozenColumns:customerListColJson.frozen,
				columns:customerListColJson.common,
    			fit:true,
				title:'客户列表【简化版】',
				border:false,
				rownumbers:true,
				pagination:true,
				idField:'id',
				singleSelect:false,
				url:'basefiles/getCustomerList.do',
				toolbar:"#sales-queryDiv-customerShortcut",
				onDblClickRow:function(rowIndex, rowData){
					top.addOrUpdateTab('basefiles/showCustomerShortcutMainPage.do?type=view&id='+encodeURIComponent(rowData.id), "客户简化版【查看】");
				},
				onSelect:function(rowIndex, rowData){
					$(this).datagrid('checkRow',rowIndex);
				},
				onUnselect:function(rowIndex, rowData){
					$(this).datagrid('uncheckRow',rowIndex);
				}
    		}).datagrid("columnMoving");
			
			//按钮
			$("#sales-buttons-customerShortcut").buttonWidget({
				initButton:[
					{},
					<security:authorize url="/basefiles/customerAdd.do">
						{
							type: 'button-add',
							handler: function(){
								top.addOrUpdateTab('basefiles/showCustomerShortcutMainPage.do?type=add', "客户简化版【新增】");
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/customerEdit.do">
						{
							type: 'button-edit',
							handler: function(){
								var con = $("#sales-datagrid-customerShortcut").datagrid('getSelected');
								if(con == null){
									$.messager.alert("提醒","请选择一条记录");
									return false;
								}
								var flag = isDoLockData(con.id,"t_base_sales_customer");
				 				if(!flag){
				 					$.messager.alert("警告","该数据正在被其他人操作，暂不能修改！");
				 					return false;
				 				}
								top.addOrUpdateTab('basefiles/showCustomerShortcutMainPage.do?type=edit&id='+ encodeURIComponent(con.id), "客户简化版【修改】");
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/customerView.do">
						{
							type: 'button-view',
							handler: function(){
								var con = $("#sales-datagrid-customerShortcut").datagrid('getSelected');
								if(con == null){
									$.messager.alert("提醒","请选择一条记录");
									return false;
								}
								top.addOrUpdateTab('basefiles/showCustomerShortcutMainPage.do?type=view&id='+ encodeURIComponent(con.id), "客户简化版【查看】");
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/customerDelete.do">
						{
							type: 'button-delete',
							handler: function(){
								var datarow = $("#sales-datagrid-customerShortcut").datagrid('getChecked');
								if(datarow == null || datarow.length==0){
									$.messager.alert("提醒","请选择要删除的客户信息");
									return false;
								}	
								$.messager.confirm("提醒", "确定删除选中客户信息?", function(r){
									if (r){
										var id = "";
										for(var i = 0; i<datarow.length; i++){
											id += datarow[i].id + ',';
										}
										loading("删除中..");
								    	$.ajax({   
								            url :'basefiles/deleteMultiCustomer.do',
											data:{ids:id},
								            type:'post',
								            dataType:'json',
								            success:function(json){
								            	loaded();
									            $.messager.alert("提醒", "删除成功数："+ json.sucNum +"<br />删除失败数："+ json.failNum + "<br />不允许删除数："+ json.noHandleNum +"<br />被引用数："+ json.userNum +"<br />加锁数："+ json.lockNum);							            		
								            	var queryJSON = $("#sales-queryForm-customerShortcut").serializeJSON();
	       										$("#sales-datagrid-customerShortcut").datagrid('load', queryJSON);
	       										$("#sales-datagrid-customerShortcut").datagrid('clearSelections');
												$("#sales-datagrid-customerShortcut").datagrid('clearChecked');
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
								var con = $("#sales-datagrid-customerShortcut").datagrid('getSelected');
								if(con == null){
									$.messager.alert("提醒","请选择一条记录");
									return false;
								}
								top.addOrUpdateTab('basefiles/showCustomerShortcutMainPage.do?type=copy&id='+ encodeURIComponent(con.id),"客户简化版【新增】");
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/customerOpen.do">
						{
							type: 'button-open',
							handler: function(){
								var datarow = $("#sales-datagrid-customerShortcut").datagrid('getChecked');
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
									           		var queryJSON = $("#sales-queryForm-customerShortcut").serializeJSON();
		       										$("#sales-datagrid-customerShortcut").datagrid('load', queryJSON);
		       										$("#sales-datagrid-customerShortcut").datagrid('clearSelections');
													$("#sales-datagrid-customerShortcut").datagrid('clearChecked');
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
								var datarow = $("#sales-datagrid-customerShortcut").datagrid('getChecked');
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
													var queryJSON = $("#sales-queryForm-customerShortcut").serializeJSON();
		       										$("#sales-datagrid-customerShortcut").datagrid('load', queryJSON);
		       										$("#sales-datagrid-customerShortcut").datagrid('clearSelections');
													$("#sales-datagrid-customerShortcut").datagrid('clearChecked');
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
								shortcutname:'customer',
								childkey:'customerid',
								maintn:'t_base_sales_customer',
								importparam:'是否总店不能为空,否则将默认为门店',//参数描述
						 		onClose:function(){
						 			var queryJSON = $("#sales-queryForm-customerShortcut").serializeJSON();
	       							$("#sales-datagrid-customerShortcut").datagrid('load', queryJSON);
						 		}
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/customerExportBtn.do">
						{
	    					type:'button-export',
	    					attr:{
	    						datagrid: "#sales-datagrid-customerShortcut",
	    						queryForm: "#sales-queryForm-customerShortcut",
						 		tnstr:'t_base_sales_customer,t_base_sales_customer_sort,t_base_sales_customer_price',//表名
						 		tnjson: {t_base_sales_customer:'客户列表',t_base_sales_customer_sort:'对应分类',t_base_sales_customer_price:'合同商品'},
						 		type:'exportmore',
						 		version:'2',
						 		shortcutname:'customer',
						 		queryparam:'id,name,shortcode,salesarea',//查询字段
						 		maintn:'t_base_sales_customer',
						 		childkey:'customerid',
						 		exportparam:'是否门店字段设置导出,否则,导入时将默认为门店',//参数描述
						 		name: '客户档案管理'
	    					}
	    				},
    				</security:authorize>
					<security:authorize url="/basefiles/customerPreviewBtn.do">
	    				{
				 			type:'button-preview',//打印预览
				 			handler:function(){
				 				alert("import");
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/customerPrintBtn.do">
			 			{
				 			type:'button-print',//打印
				 			handler:function(){
				 				alert("print");
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
								var customerRows = $("#sales-datagrid-customerShortcut").datagrid('getChecked');
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
				 						var index = $("#sales-datagrid-customerShortcut").datagrid('getRowIndex',customerRows[i]);
				 						$("#sales-datagrid-customerShortcut").datagrid('uncheckRow',index);
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
								var customerRows = $("#sales-datagrid-customerShortcut").datagrid('getChecked');
				 				if(customerRows.length == 0){
				 					$.messager.alert("提醒","请勾选客户!");
									return false;
				 				}
				 				var idStr = "";
				 				for(var i=0;i<customerRows.length;i++){
				 					if("1" != customerRows[i].state){
				 						$.messager.alert("提醒","启用状态下才可分配!");
				 						$("#sales-datagrid-customerShortcut").datagrid('unselectRow',$("#sales-datagrid-customerShortcut").datagrid('getRowIndex',customerRows[i]));
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
							var customerRows = $("#sales-datagrid-customerShortcut").datagrid('getChecked');
							if(customerRows.length == 0){
								$.messager.alert("提醒","请勾选客户!");
								return false;
							}
							var idStr = "";
							for(var i=0;i<customerRows.length;i++){
								if("1" != customerRows[i].state){
									$.messager.alert("提醒","启用状态下才可清除!");
									$("#sales-datagrid-customerShortcut").datagrid('unselectRow',$("#sales-datagrid-customerShortcut").datagrid('getRowIndex',customerRows[i]));
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
			
			//通用查询组建调用
			$("#sales-queryAdvanced-customerShortcut").advancedQuery({
		 		name:'base_sales_customer',
		 		datagrid:'sales-datagrid-customerShortcut'
			});
			
			//回车事件
			controlQueryAndResetByKey("sales-queryBtn-customerShortcut","sales-resetQueryBtn-customerShortcut");
			
			$("#sales-queryBtn-customerShortcut").click(function(){
	       		var queryJSON = $("#sales-queryForm-customerShortcut").serializeJSON();
	       		$("#sales-datagrid-customerShortcut").datagrid('load', queryJSON);
			});
			$("#sales-resetQueryBtn-customerShortcut").click(function(){
				$("#sales-queryForm-customerShortcut")[0].reset();
				$("#customerlist-widget-salesarea").widget('clear');
				$("#sales-datagrid-customerShortcut").datagrid('load', {});
			});
    	});
    </script>
  </body>
</html>
