<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>供应商档案</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
  	<input type="hidden" id="buy-buySupplierListPage-sortTree-sort"/>
  	<input type="hidden" id="buy-buySupplierListPage-areaTree-area"/>
  	<div class="easyui-layout" title="" data-options="fit:true" id="buy-layout-buyArea">
    	<div data-options="region:'north',border:false" style="height:30px;overflow: hidden">
    		<div class="buttonBG" id="buy-buttons-buySupplierList"></div>
    	</div>
    	<div data-options="region:'west',border:false,split:true" title="供应商分类及采购区域" style="width:180px;">
    		<div id="buy-buySupplierPage-treeTabs" class="easyui-tabs" data-options="fit:true,border:false">
    			<div title="供应商分类" data-options="fit:true">
	    			<div id="buy-buySupplierListPage-sortTree" class="ztree"></div>
		   			<input type="hidden" id="buy-buySupplierListPage-hid-sortid"/>
	    		</div>
	    		<div title="采购区域" data-options="fit:true">
	    			<div id="buy-buySupplierListPage-areaTree" class="ztree"></div>
		   			<input type="hidden" id="buy-buySupplierListPage-hid-areaid"/>
	    		</div>
    		</div>
    	</div>
    	<div data-options="region:'center',border:false" title="">
    		<div id="buy-query-showBuySupplierList">
	    		<form action="" id="buy-form-buySupplierListQuery" method="post" style="padding-left: 5px; padding-top: 2px;">
		    		编码:<input name="id" style="width:100px" />
		   			名称:<input name="name" style="width:120px" />
		   			<input type="hidden" name="buyarea" id="buy-hdbuyarea"/>
		   			<input type="hidden" name="suppliersort" id="buy-hdsuppliersort"/>
		    		<a href="javaScript:void(0);" id="buy-query-queryBuySupplierList" class="button-qr">查询</a>
		    		<a href="javaScript:void(0);" id="buy-query-reloadBuySupplierList" class="button-qr">重置</a>
	    			<span id="buy-query-buySupplier-advanced"></span>
	    		</form>
	    	</div>
    		<table id="buy-buySupplierListPage-table"></table>
    	</div>
    </div>
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
	    
    	$(document).ready(function(){

			//树型
			var buySupplierSortTreeSetting = {
				view: {
					dblClickExpand: false,
					showLine: true,
					selectedMulti: false,
					showIcon:true,
					expandSpeed: ($.browser.msie && parseInt($.browser.version)<=6)?"":"fast"
				},
				async: {
					enable: true,
					url: "basefiles/getBuySupplierSortTree.do",
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
						$("#buy-buySupplierListPage-sortTree-sort").val(treeNode.id);
						$("#buy-buySupplierListPage-table").datagrid({
				   			url:'basefiles/showBuySupplierPageList.do?sortid='+ treeNode.id
			       		});
			       		$("#buy-buySupplierListPage-hid-sortid").val(treeNode.id);
			       		$("#buy-buySupplierListPage-hid-areaid").val("");
			       		$("#buy-hdsuppliersort").val(treeNode.id);
						$("#buy-hdbuyarea").val("");
						var zTree = $.fn.zTree.getZTreeObj("buy-buySupplierListPage-sortTree");
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
			$.fn.zTree.init($("#buy-buySupplierListPage-sortTree"), buySupplierSortTreeSetting,null);
			
    		//树型
			var buyAreaTreeSetting = {
				view: {
					dblClickExpand: false,
					showLine: true,
					selectedMulti: false,
					showIcon:true,
					expandSpeed: ($.browser.msie && parseInt($.browser.version)<=6)?"":"fast"
				},
				async: {
					enable: true,
					url: "basefiles/getBuyAreaTree.do",
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
						$("#buy-buySupplierListPage-areaTree-area").val(treeNode.id);
						$("#buy-buySupplierListPage-table").datagrid({
				   			url:'basefiles/showBuySupplierPageList.do?areaid='+treeNode.id
			       		});
			       		$("#buy-buySupplierListPage-hid-sortid").val("");
			       		$("#buy-buySupplierListPage-hid-areaid").val(treeNode.id);
			       		$("#buy-hdsuppliersort").val("");
						$("#buy-hdbuyarea").val(treeNode.id);
					}
				}
			};
			$.fn.zTree.init($("#buy-buySupplierListPage-areaTree"), buyAreaTreeSetting,null);

			var buySupplierListJson = $("#buy-buySupplierListPage-table").createGridColumnLoad({
				name:'base_buy_supplier',
				frozenCol : [[{field:'idck',checkbox:true,isShow:true}
    			]],
    			commonCol :[[
		 		    {field:'idck',checkbox:true},
					{field:'id',title:'编码',sortable:true,width:40},
					{field:'name',title:'名称',sortable:true,width:120},
					{field:'spell',title:'助记符',sortable:true,width:60},
					{field:'shortname',title:'简称',sortable:true,width:80},
					{field:'telphone',title:'供应商电话',sortable:true,width:80},
					{field:'faxno',title:'供应商传真',sortable:true,width:70},
					{field:'address',title:'详细地址',sortable:true,width:100},
					{field:'filiale',title:'所属部门',width:70,
     					formatter:function(val,rowData,rowIndex){
							return rowData.filialename;
						}
					},
					{field:'buydeptid',title:'采购部门',width:80,
     					formatter:function(val,rowData,rowIndex){
							return rowData.buydeptname;
						}
					},
					{field:'buyuserid',title:'采购员',width:80,
     					formatter:function(val,rowData,rowIndex){
							return rowData.buyusername;
						}
					},
					{field:'buyusermobile',title:'采购员联系电话',sortable:true,width:100},
					{field:'settletype',title:'结算方式',width:80,
     					formatter:function(val,rowData,rowIndex){
							return rowData.settletypename;
						}
					},
					{field:'storageid',title:'对应仓库',width:80,
     					formatter:function(val,rowData,rowIndex){
							return rowData.storagename;
						}
					},
					{field:'contactname',title:'业务联系人',sortable:true,width:80},
					{field:'contactmobile',title:'业务联系人电话',sortable:true,width:100},
					{field:'state',title:'状态',width:60,
     					formatter:function(val,rowData,rowIndex){
							return rowData.statename;
						}
					}
    			]]
			});
			$("#buy-buySupplierListPage-table").datagrid({
				fit:true,
		 		title:"供应商列表合同版",
		 		method:'post',
		 		rownumbers:true,
		 		pagination:true,
		 		idField:'id',
		 		singleSelect:true,
				authority : buySupplierListJson,
		 		frozenColumns: buySupplierListJson.frozen,
				columns:buySupplierListJson.common,
				toolbar:'#buy-query-showBuySupplierList',
				url:'basefiles/showBuySupplierPageList.do',
				onDblClickRow:function(index, data){
					top.addOrUpdateTab('basefiles/buySupplierPage.do?type=view&id='+ encodeURIComponent(data.id), "供应商档案查看");
		    	}
			}).datagrid("columnMoving");
			
    		//按钮
    		$("#buy-buttons-buySupplierList").buttonWidget({
    			initButton:[
    				{},
    				<security:authorize url="/basefiles/buySupplierAddBtn.do">
						{
							type: 'button-add',
							handler: function(){
								var sortid = $("#buy-sortTree-buySupplierSort-sortid").val();
								var areaid = $("#buy-areaTree-buyArea-areaid").val();
								top.addOrUpdateTab('basefiles/buySupplierPage.do?sortid='+sortid +"&areaid="+areaid, "供应商档案新增");
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/buySupplierEditBtn.do">
						{
							type: 'button-edit',
							handler: function(){
								var datarow = $("#buy-buySupplierListPage-table").datagrid('getSelected');
								if(datarow == null || datarow.length==0){
									$.messager.alert("提醒","请选择一条记录");
									return false;
								}
								var flag = isDoLockData(datarow.id,"t_base_buy_supplier");
				 				if(!flag){
				 					$.messager.alert("警告","该数据正在被其他人操作，暂不能修改！");
				 					return false;
				 				}
								top.addOrUpdateTab('basefiles/buySupplierPage.do?type=edit&id='+encodeURIComponent(datarow.id), "供应商档案修改");
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/buySupplierDeleteBtn.do">
						{
							type: 'button-delete',
							handler: function(){
								var datarow = $("#buy-buySupplierListPage-table").datagrid('getChecked');
								if(datarow == null || datarow.length==0){
									$.messager.alert("提醒","请选择要删除的供应商信息");
									return false;
								}	
								$.messager.confirm("提醒", "确定删除选中供应商信息?", function(r){
									if (r){
										var id = "";
										for(var i = 0; i<datarow.length; i++){
											id += datarow[i].id + ',';
										}
										loading("删除中..");
								    	$.ajax({   
								            url :'basefiles/deleteMultiBuySupplier.do',
											data:{id:id},
								            type:'post',
								            dataType:'json',
								            success:function(json){
								            	loaded();
									            $.messager.alert("提醒", "删除成功数："+ json.isuccess +"<br />删除失败数："+ json.ifailure + "<br />不允许删除数："+ json.inohandle);
								            	$('#buy-buySupplierListPage-table').datagrid('reload');
								            }
								        });	
									}
								});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/buySupplierCopyBtn.do">
						{
							type:'button-copy',
							handler:function(){
								var datarow = $("#buy-buySupplierListPage-table").datagrid('getSelected');
								if(datarow == null || datarow.length==0){
									$.messager.alert("提醒","请选择一条记录");
									return false;
								}
								top.addOrUpdateTab('basefiles/buySupplierPage.do?type=copy&id='+encodeURIComponent(datarow.id), "供应商档案复制");
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/buySupplierViewBtn.do">
						{
							type: 'button-view',
							handler: function(){
								var datarow = $("#buy-buySupplierListPage-table").datagrid('getSelected');
								if(datarow == null || datarow.length==0){
									$.messager.alert("提醒","请选择一条记录");
									return false;
								}	
								top.addOrUpdateTab('basefiles/buySupplierPage.do?type=view&id='+ encodeURIComponent(datarow.id), "供应商档案查看");
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/buySupplierOpenBtn.do">
						{
							type: 'button-open',
							handler: function(){
								var datarow = $("#buy-buySupplierListPage-table").datagrid('getChecked');
								if(datarow == null || datarow.length==0){
									$.messager.alert("提醒","请勾选要启用的供应商信息");
									return false;
								}	
								$.messager.confirm("提醒", "确定启用选中供应商信息?", function(r){
									if (r){
										var id = "";
										for(var i = 0; i<datarow.length; i++){
											id += datarow[i].id + ',';
										}
										loading("启用中..");
								    	$.ajax({   
								            url :'basefiles/openMultiBuySupplier.do',
											data:{ids:id},
								            type:'post',
								            dataType:'json',
								            success:function(json){
								            	loaded();
									            $.messager.alert("提醒", "启用成功数："+ json.isuccess +"<br />启用失败数："+ json.ifailure + "<br />不允许启用数："+ json.inohandle);
								            	$('#buy-buySupplierListPage-table').datagrid('reload');
								            }
								        });	
									}
								});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/buySupplierCloseBtn.do">
						{
							type: 'button-close',
							handler: function(){
								var datarow = $("#buy-buySupplierListPage-table").datagrid('getChecked');
								if(datarow == null || datarow.length==0){
									$.messager.alert("提醒","请勾选要禁用的供应商信息");
									return false;
								}	
								$.messager.confirm("提醒", "确定禁用选中供应商档案信息?", function(r){
									if (r){
										var id = "";
										for(var i = 0; i<datarow.length; i++){
											id += datarow[i].id + ',';
										}
										loading("禁用中..");
								    	$.ajax({   
								            url :'basefiles/closeMultiBuySupplier.do',
											data:{ids:id},
								            type:'post',
								            dataType:'json',
								            success:function(json){
								            	loaded();
												$.messager.alert("提醒", "禁用成功数："+ json.isuccess +"<br />禁用失败数："+ json.ifailure + "<br />不允许禁用数："+ json.inohandle);
								            	$('#buy-buySupplierListPage-table').datagrid('reload');
								            }
								        });	
									}
								});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/buySupplierImportBtn.do">
						{
	    					type:'button-import',
	    					attr:{
	    						clazz: "buyService",
						 		methodjson: {t_base_buy_supplier:'addImportBuySupplier',t_base_buy_supplier_detailsort:'addDRSupplierDS'}, //插入数据库的方法
						 		tnjson: {供应商列表:'t_base_buy_supplier',供应商所属分类:'t_base_buy_supplier_detailsort'},//表名
					            module: 'basefiles', //模块名，
						 		pojojson: {t_base_buy_supplier:'BuySupplier',t_base_buy_supplier_detailsort:'BuySupplierDetailsort'}, //实体类名，将和模块名组合成com.hd.agent.basefiles.model.GoodsInfo。
								type:'importmore',
								majorkey:'id',
								version:'2',
								childkey:'supplierid',
								shortcutname:'supplier',
								maintn:'t_base_buy_supplier',
						 		onClose:function(){
						 			$("#buy-buySupplierListPage-table").datagrid('reload');
						 		}
	    					}
	    				},
					</security:authorize>
    				<security:authorize url="/basefiles/buySupplierExportBtn.do">
						{
	    					type:'button-export',
	    					attr:{
	    						datagrid: "#buy-buySupplierListPage-table",
	    						queryForm: "#buy-form-buySupplierListQuery",
						 		tnstr:'t_base_buy_supplier,t_base_buy_supplier_detailsort',//表名
						 		tnjson: {t_base_buy_supplier:'供应商列表',t_base_buy_supplier_detailsort:'供应商所属分类'},
						 		type:'exportmore',
						 		version:'2',
						 		childkey:'supplierid',
						 		sort:'buyarea,suppliersort',
						 		shortcutname:'supplier',
						 		queryparam:'id,name',//查询字段
								maintn:'t_base_buy_supplier',
						 		name: '供应商档案管理'
	    					}
	    				},
					</security:authorize>
    				{}
				],
				//buttons:[
    			//	{},
    			//	<security:authorize url="/basefiles/buySupplierConvertmodeBtn.do">
				//		{
				//			id:'convertmode',
				//			name:'模式转换',
				//			iconCls:'icon-reload',
				//			handler:function(){
				//				top.addOrUpdateTab('basefiles/showBuySupplierShortcutPage.do','供应商档案列表');
				//			}
				//		},
				//	</security:authorize>
				//	{}
				//],
				model:'base',
				type:'multipleList',
				tname: 't_base_buy_supplier'
			});
			
			//回车事件
			controlQueryAndResetByKey("buy-query-queryBuySupplierList","buy-query-reloadBuySupplierList");
			
    		//查询
			$("#buy-query-queryBuySupplierList").click(function(){
	      		var queryJSON = $("#buy-form-buySupplierListQuery").serializeJSON();
	      		if(typeof(queryJSON)=="object"){
		      		queryJSON["sortid"]=$("#buy-buySupplierListPage-hid-sortid").val();
		      		queryJSON["areaid"]=$("#buy-buySupplierListPage-hid-sortid").val();
	      		}
	      		$("#buy-buySupplierListPage-table").datagrid("load",queryJSON);
			});
			
			//重置按钮
			$("#buy-query-reloadBuySupplierList").click(function(){
				$("#buy-form-buySupplierListQuery")[0].reset();
				var data={};
				data["sortid"]=$("#buy-buySupplierListPage-hid-sortid").val();
				data["areaid"]=$("#buy-buySupplierListPage-hid-areaid").val();
				$("#buy-buySupplierListPage-table").datagrid("load",data);
				
			});

			//通用查询组建调用
			$("#buy-query-buySupplier-advanced").advancedQuery({
				//查询针对的表
		 		name:'base_buy_supplier',
		 		//查询针对的表格id
		 		datagrid:'buy-buySupplierListPage-table'
			});
    	});
    </script>
  </body>
</html>
