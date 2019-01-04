<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>联系人列表</title>
    <%@include file="/include.jsp" %>  
  </head>
  
  <body>
    <input type="hidden" id="contacter-thisId-contacterListPage" />
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<div data-options="region:'north',border:false" style="height:30px;overflow: hidden;">
    		<div class="buttonBG" id="contacter-buttons-contacterListPage"></div>
    	</div>
    	<div data-options="region:'west',split:true" style="width:200px;">
    		<div class="ztree" id="contacter-sortTree-contacterListPage"></div>
    	</div>
    	<div data-options="region:'center'">
    		<div id="contacter-queryDiv-contacterListPage" style="padding:5px;">
    			<form id="contacter-queryForm-contacterListPage">
    				<table cellpadding="1" cellspacing="" border="0">
    					<tr>
    						<td>姓名：</td>
    						<td><input type="text" name="name" style="width: 146px;" /></td>
    						<td>所属客户：</td>
    						<td><input type="text" name="customer" style="width: 150px;" id="contacter-cusotmer"/></td>
    					</tr>
    					<tr>
    						<td>所属供应商：</td>
    						<td><input type="text" name="supplier" style="width: 150px;" id="contacter-supplier"/></td>
    						<td colspan="2">
    							<input type="hidden" id="contacter-sort-contacterListPage" name="linkmansort" />
    							<a href="javascript:;" class="easyui-linkbutton" id="contacter-queryBtn-contacterListPage" data-options="iconCls:'icon-search'" title="[Alt+Q]查询">查询</a>
				  				<a href="javaScript:;" id="contacter-resetQueryBtn-contacterListPage" class="easyui-linkbutton" iconCls="icon-reload" title="[Alt+R]重置">重置</a>
				  				<span id="contacter-queryAdvanced-contacterListPage"></span>
    						</td>
    					</tr>
    				</table>
    				
    				
    			</form>
    		</div>
    		<table id="contacter-datagrid-contacterListPage" data-options="border:false"></table>
    	</div>
    </div>
    <script type="text/javascript">
    	$(function(){
    		//联系人分类树型结构
			var contacterTreeSetting = {
				view: {
					dblClickExpand: false,
					showLine: true,
					selectedMulti: false,
					showIcon:true,
					expandSpeed: ($.browser.msie && parseInt($.browser.version)<=6)?"":"fast"
				},
				async: {
					enable: true,
					url: "basefiles/getContacterSortTree.do?type=1",
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
						$("#contacter-thisId-contacterListPage").val(treeNode.id);
						$("#contacter-sort-contacterListPage").val(treeNode.id);
						$("#contacter-queryBtn-contacterListPage").click();
						var zTree = $.fn.zTree.getZTreeObj("contacter-sortTree-contacterListPage");
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
			$.fn.zTree.init($("#contacter-sortTree-contacterListPage"), contacterTreeSetting,null);
			//通用查询组建调用
			$("#contacter-queryAdvanced-contacterListPage").advancedQuery({
				//查询针对的表
		 		name:'base_linkman_info',
		 		//查询针对的表格id
		 		datagrid:'contacter-datagrid-contacterListPage'
			});
			
			//回车事件
			controlQueryAndResetByKey("contacter-queryBtn-contacterListPage","contacter-resetQueryBtn-contacterListPage");
			
			$("#contacter-queryBtn-contacterListPage").click(function(){
	       		var queryJSON = $("#contacter-queryForm-contacterListPage").serializeJSON();
	       		$("#contacter-datagrid-contacterListPage").datagrid('load', queryJSON);
			});
			$("#contacter-resetQueryBtn-contacterListPage").click(function(){
				$("#contacter-queryForm-contacterListPage")[0].reset();
				$("#contacter-cusotmer").customerWidget('clear');
				$("#contacter-supplier").supplierWidget('clear');
				$("#contacter-datagrid-contacterListPage").datagrid('load', {});
			});
			//按钮
			$("#contacter-buttons-contacterListPage").buttonWidget({
				initButton:[
					{},
    				<security:authorize url="/basefiles/contacterAdd.do">
					{
						type: 'button-add',
						handler: function(){
							var thisId = $("#contacter-thisId-contacterListPage").val();
							top.addOrUpdateTab('basefiles/contacterPage.do?sort='+ thisId, "联系人新增");
						}
					},
					</security:authorize>
    				<security:authorize url="/basefiles/contacterEdit.do">
					{
						type: 'button-edit',
						handler: function(){
							var con = $("#contacter-datagrid-contacterListPage").datagrid('getSelected');
							if(con == null){
								$.messager.alert("提醒","请选择一条记录");
								return false;
							}	
							top.addOrUpdateTab('basefiles/contacterPage.do?type=edit&id='+ con.id, "联系人修改");
						}
					},
					</security:authorize>
    				<security:authorize url="/basefiles/contacterDelete.do">
					{
						type: 'button-delete',
						handler: function(){
							var con = $("#contacter-datagrid-contacterListPage").datagrid('getChecked');
							if(con.length<1){
								$.messager.alert("提醒","请选择一条记录");
								return false;
							}	
							$.messager.confirm("提醒", "确定删除选中联系人信息?", function(r){
								if (r){
									var id = "";
									for(var i = 0; i<con.length; i++){
										id += con[i].id + ',';
									}
									loading("删除中..");
							    	$.ajax({   
							            url :'basefiles/deleteMultiContacter.do?id='+ id,
							            type:'post',
							            dataType:'json',
							            success:function(json){
							            	loaded();
							            	$.messager.alert("提醒", "删除成功数："+ json.snum +"<br />删除失败数："+ json.fnum + "<br />不允许删除数："+ json.nnum);
							            	$('#contacter-datagrid-contacterListPage').datagrid('reload');
											$("#contacter-datagrid-contacterListPage").datagrid('clearSelections');
							            }
							        });	
								}
							});
						}
					},
					</security:authorize>
    				<security:authorize url="/basefiles/contacterView.do">
					{
						type: 'button-view',
						handler: function(){
							var con = $("#contacter-datagrid-contacterListPage").datagrid('getSelections');
							if(con.length<1){
								$.messager.alert("提醒","请选择一条记录");
								return false;
							}
							top.addOrUpdateTab('basefiles/contacterPage.do?type=view&id='+ con[0].id, "联系人查看");
						}
					},
					</security:authorize>
    				<security:authorize url="/basefiles/contacterOpen.do">
					{
						type: 'button-open',
						handler: function(){
							var con = $("#contacter-datagrid-contacterListPage").datagrid('getChecked');
							if(con.length<1){
								$.messager.alert("提醒","请选择一条记录");
								return false;
							}	
							$.messager.confirm("提醒", "确定启用选中联系人信息?", function(r){
								if (r){
									var id = "";
									for(var i = 0; i<con.length; i++){
										id += con[i].id + ',';
									}
									loading("启用中..");
							    	$.ajax({   
							            url :'basefiles/openMultiContacter.do?id='+ id,
							            type:'post',
							            dataType:'json',
							            success:function(json){
							            	loaded();
							            	$.messager.alert("提醒", "启用成功数："+ json.snum +"<br />启用失败数："+ json.fnum + "<br />不允许启用数："+ json.nnum);
							            	$('#contacter-datagrid-contacterListPage').datagrid('reload');
							            }
							        });	
								}
							});
						}
					},
					</security:authorize>
    				<security:authorize url="/basefiles/contacterClose.do">
					{
						type: 'button-close',
						handler: function(){
							var con = $("#contacter-datagrid-contacterListPage").datagrid('getChecked');
							if(con.length<1){
								$.messager.alert("提醒","请选择一条记录");
								return false;
							}	
							$.messager.confirm("提醒", "确定禁用选中联系人信息?", function(r){
								if (r){
									var id = "";
									for(var i = 0; i<con.length; i++){
										id += con[i].id + ',';
									}
									loading("禁用中..");
							    	$.ajax({   
							            url :'basefiles/closeMultiContacter.do?id='+ id,
							            type:'post',
							            dataType:'json',
							            success:function(json){
							            	loaded();
							            	$.messager.alert("提醒", "禁用成功数："+ json.snum +"<br />禁用失败数："+ json.fnum + "<br />不允许禁用数："+ json.nnum);
							            	$('#contacter-datagrid-contacterListPage').datagrid('reload');
							            }
							        });	
								}
							});
						}
					},
					</security:authorize>
    				<security:authorize url="/basefiles/contacterImport.do">
    				{
    					type:'button-import',
    					attr:{
    						clazz: "contacterService",
					 		methodjson: {t_base_linkman_info:'addDRContancter',t_base_linkman_info_sort:'addDRContacterAndSort'}, //插入数据库的方法
					 		tnjson: {联系人档案:'t_base_linkman_info',联系人所属分类:'t_base_linkman_info_sort'},//表名
				            module: 'basefiles', //模块名，
					 		pojojson: {t_base_linkman_info:'Contacter',t_base_linkman_info_sort:'ContacterAndSort'}, //实体类名，将和模块名组合成com.hd.agent.basefiles.model.GoodsInfo。
							type:'importmore',
							majorkey:'id',
							version:'1',
							childkey:'linkmanid',
							maintn:'t_base_linkman_info',
					 		onClose:function(){
					 			$("#contacter-datagrid-contacterListPage").datagrid('reload');
					 		}
    					},
    					url:''
    				},
    				</security:authorize>
    				<security:authorize url="/basefiles/contacterExport.do">
    				{
    					type:'button-export',
    					attr:{
    						datagrid: "#contacter-datagrid-contacterListPage",
					 		queryForm: "#contacter-queryForm-contacterListPage", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
					 		tnstr:'t_base_linkman_info,t_base_linkman_info_sort',//表名
					 		tnjson: {t_base_linkman_info:'联系人档案',t_base_linkman_info_sort:'联系人所属分类'},
					 		type:'exportmore',
					 		version:'1',
					 		queryparam:'name,linkmansort,customer,supplier',//查询字段
					 		childkey:'linkmanid',
							maintn:'t_base_linkman_info',
					 		name: '联系人管理'
    					}
    				},
    				</security:authorize>
    				<security:authorize url="/basefiles/contacterPreview.do">
    				{
    					type:'button-preview'
    				},
    				</security:authorize>
    				<security:authorize url="/basefiles/contacterPrint.do">
    				{
    					type:'button-print'
    				},
    				</security:authorize>
    				{}
				],
				model:'base',
				type:'multipleList',
				tname: 't_base_linkman_info'
			});
			
			//所属客户
			$("#contacter-cusotmer").customerWidget({});
			//所属供应商
			$("#contacter-supplier").supplierWidget({});
			var contacterListJson = $("#contacter-datagrid-contacterListPage").createGridColumnLoad({
				name :'t_base_linkman_info',
				frozenCol : [[]],
				commonCol : [[{field:'id',title:'编号',width:60},  
				              {field:'name',title:'姓名',width:80},
				              {field:'tel',title:'电话',width:80},
				              {field:'fax',title:'传真',width:80},
				              {field:'mobile',title:'手机号码',width:80},
				              {field:'email',title:'邮箱',width:120},
				              {field:'linkmansort',title:'所属分类',width:80},
				              {field:'customer',title:'所属客户',width:120,
				              	formatter:function(val,rowData,rowIndex){
									return rowData.customername;
								}
				              },
				              {field:'supplier',title:'所属供应商',width:120,
				              	formatter:function(val,rowData,rowIndex){
									return rowData.suppliername;
								}
				              },
				              {field:'state',title:'状态',width:50}
				              ]]
			});
			$("#contacter-datagrid-contacterListPage").datagrid({ 
		 		authority:contacterListJson,
		 		frozenColumns: [[{field:'contactercheck',checkbox:true}]],
				columns:contacterListJson.common,
		 		fit:true,
		 		fitColumns:true,
		 		title:"联系人列表",
		 		method:'post',
		 		rownumbers:true,
		 		pagination:true,
		 		idField:'id',
		 		singleSelect:true,
				url:'basefiles/getContacterList.do',
				toolbar:'#contacter-queryDiv-contacterListPage',
			    onDblClickRow:function(index, data){
					if (top.$('#tt').tabs('exists','联系人查看')){
						top.closeTab('联系人查看');
					}
					top.addOrUpdateTab('basefiles/contacterPage.do?type=view&id='+data.id, '联系人查看');
		    	}
			}).datagrid("columnMoving");
    	});
    </script>
  </body>
</html>
