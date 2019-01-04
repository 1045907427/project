<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>供应商档案</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
  	<input type="hidden" id="supplier-opera"/>
  	<input type="hidden" id="buy-buySupplierListPage-sortTree-sort"/>
  	<input type="hidden" id="buy-buySupplierListPage-areaTree-area"/>
  	<div class="easyui-layout" title="" data-options="fit:true" id="buy-layout-buyArea">
    	<div data-options="region:'north',border:false" style="height:30px;overflow: hidden">
    		<div class="buttonBG" id="buy-buttons-buySupplierList"></div>
    	</div>
    	<div data-options="region:'west',border:false,split:true" title="" style="width:130px;">
    		<div id="buy-buySupplierPage-treeTabs" class="easyui-tabs" data-options="fit:true,border:false">
    			<div title="区域" data-options="fit:true">
	    			<div id="buy-buySupplierListPage-areaTree" class="ztree"></div>
	    		</div>
    			<div title="分类" data-options="fit:true">
	    			<div id="buy-buySupplierListPage-sortTree" class="ztree"></div>
	    		</div>
    		</div>
    	</div>
    	<div data-options="region:'center'" title="">
    		<div id="buy-query-showBuySupplierList">
	    		<form action="" id="buy-form-buySupplierListQuery" method="post" style="padding-left: 5px; padding-top: 2px;">
		    		编码:<input type="text" name="id" style="width:100px" />
		   			名称:<input type="text" name="name" style="width:120px" />
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
    <div id="buy-dialog-supplierListPage"></div>
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
	    
	    var buySupplierShortcut_AjaxConn = function (Data, Action) {
		    var MyAjax = $.ajax({
		        type: 'post',
		        cache: false,
		        url: Action,
		        data: Data,
		        async: false,
		        success:function(data){
		        	loaded();
		        }
		    })
		    return MyAjax.responseText;
		}
		
		function buyuserReferWindow(deptid){
    		$("#buSupplierShortcut-widget-buyuserid").widget({
    			name:'t_base_buy_supplier',
    			col:'buyuserid',
    			width:130,
    			async:false,
    			<c:if test="${colMap.buyuserid == 'buyuserid' }">required:true,</c:if>
    			singleSelect:true,
    			onlyLeafCheck:false
    		});
    	}
    	
    	//所属部门
    	function filialeReferWindow(deptid){
    		$("#buSupplierShortcut-widget-filiale").widget({ 
    			name:'t_base_buy_supplier',
    			col:'filiale',
    			param:[{field:'id',op:'equal',value:deptid}],
    			width:130,
    			<c:if test="${colMap.filiale == 'filiale' }">required:true,</c:if>
    			singleSelect:true,
    			onlyLeafCheck:false
    		});
    	}
	    
	    //加载下拉框 
		function loadDropdown(){
			//默认区域
		  	$("#buy-buySupplierAddPage-buyarea").widget({
		  		width:130,
				referwid:'RT_T_BASE_BUY_AREA',
				singleSelect:true,
				onlyLeafCheck:false
			});
			//默认分类
			$("#buy-buySupplierAddPage-suppliersort").widget({
		  		width:130,
				referwid:'RT_T_BASE_BUY_SUPPLIER_SORT',
				singleSelect:true,
				onlyLeafCheck:false,
				onSelect:function(data){
					$("#buy-detailsort-buySupplierShortcut").val(data.id);
				}
			});
			
			//默认采购部门
			$("#buSupplierShortcut-widget-buydeptid").widget({ 
    			name:'t_base_buy_supplier',
    			col:'buydeptid',
    			width:130,
    			singleSelect:true,
    			onlyLeafCheck:false,
    			<c:if test="${colMap.buydeptid == 'buydeptid' }">required:true,</c:if>
    			onSelect: function(data){
    				if(data && data.id){
    					filialeReferWindow(data.pid);
    				}else{
    					filialeReferWindow(null);
    				}
    			},
    			onClear:function(){
    				filialeReferWindow(null);
    			}
    		});
    		buyuserReferWindow(null);
			
			$("#buSupplierShortcut-widget-filiale").widget({ 
    			name:'t_base_buy_supplier',
    			col:'filiale',
    			width:130,
    			<c:if test="${colMap.filiale == 'filiale' }">required:true,</c:if>
    			singleSelect:true,
    			onlyLeafCheck:false
    		});
			
			//对应仓库
			$("#buy-buySupplierAddPage-storageid").widget({
				width:130,
				name:'t_base_buy_supplier',
				col:'storageid',
				<c:if test="${colMap.storageid == 'storageid' }">required:true,</c:if>
				singleSelect:true,
				onlyLeafCheck:false
			});

			//结算方式
			$("#buySupplierShortcut-widget-settletype").widget({ //结算方式参照窗口
				name:'t_base_sales_customer',
				col:'settletype',
				singleSelect:true,
				width:130,
				onlyLeafCheck:false,
				onSelect:function(data){
					if(data.type == '1'){//月结
						$("#supplier-select-settleday").combobox({
							disabled:false,
							required:true
						});
					}else{
						$("#supplier-select-settleday").combobox({
							disabled:true,
							required:false
						});
					}
				}
			});
		}
	    
	    //检验商品档案数据（唯一性，最大长度等）
		$.extend($.fn.validatebox.defaults.rules, {
			validId:{//编号唯一性,最大长度
   				validator:function(value,param){
   					var reg=eval("/^[A-Za-z0-9]{0,"+param[0]+"}$/");
					if(reg.test(value)){
   						var ret = buySupplierShortcut_AjaxConn({id:value},'basefiles/isBuySupplierIdExist.do');//true 重复，false 不重复
   						var retJson = $.parseJSON(ret);
   						if(retJson.flag){
   							$.fn.validatebox.defaults.rules.validId.message = '编号重复, 请重新输入!';
   							return false;
   						}
   					}
   					else{
   						$.fn.validatebox.defaults.rules.validId.message = '请输入不少于{0}个字符!';
						return false;
   					}
   					return true;
   				},
   				message:''
   			},
   			validName:{//名称唯一性,最大长度
   				validator:function(value,param){
   					if(value.length <= param[0]){
   						var ret = buySupplierShortcut_AjaxConn({name:value},'basefiles/isBuySupplierNameExist.do');//true 重复，false 不重复
   						var retJson = $.parseJSON(ret);
   						if(retJson.flag){
   							$.fn.validatebox.defaults.rules.validName.message = '名称重复, 请重新输入!';
   							return false;
   						}
   					}
   					else{
   						$.fn.validatebox.defaults.rules.validName.message = '请输入不少于{0}个字符!';
						return false;
   					}
   					return true;
   				},
   				message:''
   			}
		});
	    
	    function refreshLayout(title, url,type){
			$('<div id="buy-dialog-supplierListPage1"></div>').appendTo('#buy-dialog-supplierListPage');
			$('#buy-dialog-supplierListPage1').dialog({
				maximizable:true,
				resizable:true,
			    title: title,  
			    width: 755,
			    height: 480,
			    closed: true,
			    cache: false,  
			    href: url,  
			    modal: true,
			    onClose:function(){
			    	$('#buy-dialog-supplierListPage1').dialog("destroy");
			    }
			});
			$("#buy-dialog-supplierListPage1").dialog("open");
			$("#supplier-opera").val(type);
    	}
	    
	    function supplier_save_form_submit(){
    		$("#buy-add-buySupplierShortcut").form({
			    onSubmit: function(){  
		  		  	loading("提交中..");
		  		},  
		  		success:function(data){
		  		  	loaded();
		  		  	var json = $.parseJSON(data);
		  		    if(json.flag){
   						var queryJSON = $("#buy-form-buySupplierListQuery").serializeJSON();
      					$("#buy-buySupplierListPage-table").datagrid("load",queryJSON);
						$("#buy-dialog-supplierListPage1").dialog('close');
						$.messager.alert("提醒","保存成功!");
					}else{
						$.messager.alert("提醒","保存失败!");
					}
		  		}
		  	});
    	}
	    
	    function supplier_savegoon_form_submit(){
    		$("#buy-add-buySupplierShortcut").form({
			    onSubmit: function(){  
		  		  	loading("提交中..");
		  		},  
		  		success:function(data){
		  		  	loaded();
		  		  	var json = $.parseJSON(data);
		  		    if(json.flag){
   						var queryJSON = $("#buy-form-buySupplierListQuery").serializeJSON();
      					$("#buy-buySupplierListPage-table").datagrid("load",queryJSON);
      					
      					var sortid = $("#buy-sortTree-buySupplierSort-sortid").val();
						var areaid = $("#buy-areaTree-buyArea-areaid").val();
						var url = 'basefiles/showBuySupplierShortcutAddPage.do?sortid='+sortid+"&areaid="+areaid;
						$('#buy-dialog-supplierListPage1').dialog('refresh', url);
						$.messager.alert("提醒","保存成功!");
					}else{
						$.messager.alert("提醒","保存失败!");
					}
		  		}
		  	});
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
			       		$("#buy-hdsuppliersort").val(treeNode.id);
						$("#buy-hdbuyarea").val("");
                        $("#buy-query-queryBuySupplierList").click();
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
			       		$("#buy-hdsuppliersort").val("");
			       		$("#buy-hdbuyarea").val(treeNode.id);
                        $("#buy-query-queryBuySupplierList").click();
                        var zTree = $.fn.zTree.getZTreeObj("buy-buySupplierListPage-areaTree");
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
			$.fn.zTree.init($("#buy-buySupplierListPage-areaTree"), buyAreaTreeSetting,null);

			var buySupplierListJson = $("#buy-buySupplierListPage-table").createGridColumnLoad({
				name:'base_buy_supplier',
				frozenCol : [[{field:'idck',checkbox:true,isShow:true}]],
    			commonCol :[[
		 		    {field:'idck',checkbox:true},
					{field:'id',title:'编码',sortable:true,width:40},
					{field:'name',title:'名称',sortable:true,width:120},
					{field:'spell',title:'助记符',sortable:true,width:60},
					{field:'shortname',title:'简称',sortable:true,width:80},
					{field:'telphone',title:'供应商电话',sortable:true,width:80},
					{field:'faxno',title:'供应商传真',sortable:true,width:70},
					{field:'address',title:'详细地址',sortable:true,width:100},
//					{field:'filiale',title:'所属部门',width:70,
//     					formatter:function(val,rowData,rowIndex){
//							return rowData.filialename;
//						}
//					},
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
		 		title:"供应商列表",
		 		method:'post',
		 		rownumbers:true,
		 		pagination:true,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
		 		singleSelect:false,
		 		idField:'id',
				authority : buySupplierListJson,
		 		frozenColumns: buySupplierListJson.frozen,
				columns:buySupplierListJson.common,
				toolbar:'#buy-query-showBuySupplierList',
				url:'basefiles/showBuySupplierPageList.do',
				onDblClickRow:function(index, data){
					<security:authorize url="/basefiles/buySupplierViewBtn.do">
						var url = 'basefiles/showBuySupplierShortcutViewPage.do?id='+encodeURIComponent(data.id);
						refreshLayout("供应商档案【查看】", url,"view");
					</security:authorize>
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
								var url = 'basefiles/showBuySupplierShortcutAddPage.do?sortid='+sortid+"&areaid="+areaid;

								refreshLayout("供应商档案【新增】", url,"add");
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
				 				var url = 'basefiles/showBuySupplierShortcutEditPage.do?id='+encodeURIComponent(datarow.id);
								refreshLayout("供应商档案【修改】", url,"edit");
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
								var datarow = $("#buy-buySupplierListPage-table").datagrid('getChecked');
								if(datarow == null || datarow.length==0){
									$.messager.alert("提醒","请选择一条记录");
									return false;
								}
								var url = 'basefiles/showBuySupplierShortcutCopyPage.do?id='+encodeURIComponent(datarow.id);
								refreshLayout("供应商档案【复制】", url,"add");
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
								var url = 'basefiles/showBuySupplierShortcutViewPage.do?id='+encodeURIComponent(datarow.id);
								refreshLayout("供应商档案【查看】", url,"view");
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
	    					type:'button-import',//导入
				 			attr:{
				 				type:'importUserdefined',
								importparam:'编码、名称必填',//参数描述
								version:'1',//导入页面显示哪个版本1：不显示，2：简化版或合同版，3：Excel文件或瑞家txt导入，4：Excel文件或三和txt导入
								url:'basefiles/importSupplierSimplifyListData.do',
								onClose: function(){ //导入成功后窗口关闭时操作，
							         $("#buy-buySupplierListPage-table").datagrid('reload');	//更新列表	                                                                                        
								}
				 			}
	    				},
					</security:authorize>
    				<security:authorize url="/basefiles/buySupplierExportBtn.do">
						{
	    					type:'button-export',//导出 
				 			attr:{
				 				datagrid: "#buy-buySupplierListPage-table",
	    						queryForm: "#buy-form-buySupplierListQuery",
						 		type:'exportUserdefined',
						 		name:'供应商档案列表',
						 		url:'basefiles/exportSupplierSimplifyListData.do'
				 			}
	    				},
					</security:authorize>
                    {
                        type: 'button-commonquery',
                        attr:{
                            name:'base_buy_supplier',
                            datagrid:'buy-buySupplierListPage-table',
                            plain:true
                        }
                    },
    				{}
				],
				model:'base',
				type:'multipleList',
				tname: 't_base_buy_supplier'
			});
			
			//回车事件
			controlQueryAndResetByKey("buy-query-queryBuySupplierList","buy-query-reloadBuySupplierList");
			
    		//查询
			$("#buy-query-queryBuySupplierList").click(function(){
	      		var queryJSON = $("#buy-form-buySupplierListQuery").serializeJSON();
	      		$("#buy-buySupplierListPage-table").datagrid("load",queryJSON);
			});
			
			//重置按钮
			$("#buy-query-reloadBuySupplierList").click(function(){
				$("#buy-form-buySupplierListQuery")[0].reset();
                $("#buy-hdsuppliersort").val("");
                $("#buy-hdbuyarea").val("");
                var areaTree=$.fn.zTree.getZTreeObj("buy-buySupplierListPage-areaTree");
                areaTree.refresh();
                var sortTree=$.fn.zTree.getZTreeObj("buy-buySupplierListPage-sortTree");
                sortTree.refresh();
				$("#buy-buySupplierListPage-table").datagrid('load',{});
				
			});

			//通用查询组建调用
//			$("#buy-query-buySupplier-advanced").advancedQuery({
//		 		name:'base_buy_supplier',
//		 		datagrid:'buy-buySupplierListPage-table'
//			});
    	});
    </script>
  </body>
</html>
