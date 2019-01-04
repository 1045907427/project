<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>商品分类列表页面</title>
    <%@include file="/include.jsp" %> 
  </head>
  
  <body>
  	<input type="hidden" id="wareClass-pstate"/>
  	<input type="hidden" id="waresClass-operaList"/>
   	<div id="waresClass-button-toolbarlist" style="padding:2px;height:auto">
   		<div id="waresClass-button-divList"></div>
   		<form action="" id="waresClass-form-waresClassListQuery" method="post" style="padding-left: 5px; padding-top: 2px;">
	   		编码:<input name="id" style="width:100px" />
	  		名称:<input name="name" style="width:120px" />
	   		<a href="javaScript:void(0);" id="waresClass-query-queryWaresClassList" class="button-qr" >查询</a>
	   		<a href="javaScript:void(0);" id="waresClass-query-reloadWaresClassList" class="button-qr">重置</a>
	  		<span id="waresClass-query-advanced"></span>
	  	</form>
   	</div>
  	<table id="waresClass-table-list"></table>
  	<script type="text/javascript">
  		var waresClass_AjaxConn = function (Data, Action, Str) {
  			if(null != Str && "" != Str){
  				loading(Str);
  			}
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
  		//根据初始的列与用户保存的列生成以及字段权限生成新的列
	     var waresClassListColJson=$("#waresClass-table-list").createGridColumnLoad({
	     	name:'base_goods_waresclass',
	     	frozenCol:[[]],
	     	commonCol:[[
				{field:'id',title:'编码',width:100,sortable:true},
				{field:'name',title:'名称',width:100,sortable:true},  
				{field:'state',title:'状态',width:60,sortable:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.stateName;
					}
				},
				{field:'thisid',title:'本级编码',width:100,sortable:true,hidden:true},  
				{field:'thisname',title:'本级名称',width:100,sortable:true},  
				{field:'leaf',title:'末级标志',width:100,sortable:true,
					formatter:function(val,rowData,rowIndex){
						if(val == 1){
							return "是";
						}
						else{
							return "否";
						}
					}
				},  
				{field:'remark',title:'备注',width:130,sortable:true},
				{field:'addusername',title:'建档人',width:80,sortable:true,hidden:true},
				{field:'adddeptidname',title:'建档部门',width:80,sortable:true,hidden:true},
				{field:'addtime',title:'建档时间',width:80,sortable:true,hidden:true},
				{field:'modifyusername',title:'最后修改人',width:80,sortable:true,hidden:true},
				{field:'modifytime',title:'最后修改时间',width:80,sortable:true,hidden:true},
				{field:'openusername',title:'启用人',width:80,sortable:true,hidden:true},
				{field:'opentime',title:'启用时间',width:80,sortable:true,hidden:true},
				{field:'closeusername',title:'禁用人',width:80,sortable:true,hidden:true},
				{field:'closetime',title:'禁用时间',width:80,sortable:true,hidden:true}
			]]
	     });
  		$(function(){
			//加载商品分类列表数据
			$("#waresClass-table-list").datagrid({ 
	    		authority:waresClassListColJson,
	  	 		frozenColumns:[[]],
				columns:waresClassListColJson.common,
	  	 		fit:true,
	  	 		method:'post',
	  	 		title:'',
	  	 		rownumbers:true,
	  	 		pagination:true,
	  	 		idField:'id',
	  	 		singleSelect:true,
			    url:'basefiles/getWaresClassListPage.do',
			    toolbar:"#waresClass-button-toolbarlist",
		    	onClickRow:function(rowIndex, rowData){
		    		var ret = waresClass_AjaxConn({pid:rowData.pid},'basefiles/getParentWaresClass.do');
		    		var retJson = $.parseJSON(ret);
		    		if(null != retJson){
		    			$("#wareClass-pstate").val(retJson.pWCInfo.state);
		    		}
					$("#waresClass-button-divList").buttonWidget("setDataID",{id:rowData.id,state:rowData.state,type:"view"});
		    	}
			}).datagrid("columnMoving");
			
  			$("#waresClass-button-divList").buttonWidget({
				//初始默认按钮 根据type对应按钮事件
				initButton:[
					{},
					<security:authorize url="/basefiles/waresClassDelBtn.do">
					{
						type:'button-delete',//删除
						handler:function(){
							var waresClass=$("#waresClass-table-list").datagrid('getSelected');
							if(waresClass == null){
								$.messager.alert("提醒","请选择要删除的数据!");
								return false;
							}
							var id = waresClass.id;
							var ret = waresClass_AjaxConn({id:id},'basefiles/isExistChildWCList.do','删除中..');
							var retJson = $.parseJSON(ret);
							if(retJson.flag){//true 存在，false 不存在
								$.messager.alert("提醒","下级中存在商品分类,请先删除下级商品分类!");
								return false;
							}
							else{
								$.messager.confirm("提醒","是否确定删除商品分类?",function(r){
									if(r){
										var jsonStr = waresClass_AjaxConn({id:id},'basefiles/deleteWaresClass.do','删除中..');
										var JSON = $.parseJSON(jsonStr);
										if(JSON.flag){//删除节点 
											$.messager.alert("提醒","删除成功");
											//$.fn.zTree.init($("#waresClass-tree-waresClass"), waresClassTreeSetting,null);
											$("#waresClass-table-list").datagrid('reload');
											$("#waresClass-table-list").datagrid('clearSelections');
											//var node = waresClassTree.getNodeByParam("id", id, null);
											//waresClassTree.removeNode(node); //删除子节点
										}
										else{
											$.messager.alert("提醒",JSON.Mes);
										}
									}
								});
							}
						}
					},
					</security:authorize>
					<security:authorize url="/basefiles/waresClassOpenBtn.do">
					{
						type:'button-open',//启用
						handler:function(){
							var waresClass=$("#waresClass-table-list").datagrid('getSelected');
							if(waresClass == null){
								$.messager.alert("提醒","请选择要启用的数据!");
								return false;
							}
							if(waresClass.state != "2" && waresClass.state != "0"){
								$.messager.alert("提醒","该状态下不允许启用!");
								return false;
							}
							if($("#wareClass-pstate").val() == "0"){
								$.messager.alert("提醒","上级节点为禁用状态，无法启用该节点！");
								return false;
							}
							$.messager.confirm("提醒","是否确定启用商品分类?",function(r){
								if(r){
									var id = waresClass.id;
									var ret = waresClass_AjaxConn({id:id},'basefiles/enableWaresClass.do','启用中..');
									var retJson = $.parseJSON(ret);
									if(retJson.flag){
										$.messager.alert("提醒","启用成功!");
										$("#waresClass-table-list").datagrid('reload');
									}
									else{
										$.messager.alert("提醒","启用失败!");
									}
								}
							});
						}
					},
					</security:authorize>
					<security:authorize url="/basefiles/waresClassCloseBtn.do">
					{
						type:'button-close',//禁用
						handler:function(){
							var waresClass=$("#waresClass-table-list").datagrid('getSelected');
							if(waresClass.state != "1"){
								$.messager.alert("提醒","该状态下不允许禁用!");
								return false;
							}
							var str = "是否确定禁用商品分类?";
							if(waresClass == null){
								$.messager.alert("提醒","请选择要禁用的数据!");
								return false;
							}
							if(waresClass.leaf == "0"){
								str = "是否确定禁用商品分类及其下级商品分类?";
							}
							$.messager.confirm("提醒",str,function(r){
								if(r){
									var id = waresClass.id;
									var ret = waresClass_AjaxConn({id:id},'basefiles/disableWaresClass.do','禁用中..');
									var retJson = $.parseJSON(ret);
									$.messager.alert("提醒","禁用成功数："+ retJson.successNum + "<br />禁用失败数："+ retJson.failureNum + "<br />不可禁用数："+ retJson.notAllowNum+"<br />被引用数："+ retJson.userNum);
									$("#waresClass-table-list").datagrid('reload');
									$("#waresClass-button-layoutTree").buttonWidget("setButtonType",0);
								}
							});
						}
					},
					</security:authorize>
					<security:authorize url="/basefiles/waresClassImportBtn.do">
					{
			 			type:'button-import',//导入
			 			attr:{
                            clazz: "goodsService",
                            method: "addDRGoodsSortExcel",
                            tn: "t_base_goods_waresclass",
                            module: 'basefiles',
                            pojo: "WaresClass",
							onClose: function(){
						         $("#waresClass-table-list").datagrid('reload');                                                                                     
							}
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/waresClassExportBtn.do">
		 			{
			 			type:'button-export',//导出 
			 			attr:{
			 				queryForm: "#waresClass-form-waresClassListQuery", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
					 		tn: 't_base_goods_waresclass',//表名
					 		name:'商品分类列表'
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/waresClassPrintViewBtn.do">
		 			{
			 			type:'button-preview',//打印预览 
			 			handler:function(){
			 				alert("import");
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/waresClassPrintBtn.do">
					{
						type:'button-print',//打印
						handler:function(){
							alert("add");
						}
					},
					</security:authorize>
					{}
				],
				model:'base',
				type:'list',
				//tab:'商品分类',
				datagrid:'waresClass-table-list',
				tname:'t_base_goods_waresclass',
				id:''
			});
			
			//回车事件
			controlQueryAndResetByKey("waresClass-query-queryWaresClassList","waresClass-query-reloadWaresClassList");
			
			//查询
			$("#waresClass-query-queryWaresClassList").click(function(){
				//把form表单的name序列化成JSON对象
	      		var queryJSON = $("#waresClass-form-waresClassListQuery").serializeJSON();
	      		//调用datagrid本身的方法把JSON对象赋给queryParams 即可进行查询
	      		$("#waresClass-table-list").datagrid("load",queryJSON);
			});
			
			//重置按钮
			$("#waresClass-query-reloadWaresClassList").click(function(){
				$("#waresClass-form-waresClassListQuery")[0].reset();
				$("#waresClass-table-list").datagrid("load",{});
				
			});	
  		});
  	</script>
  </body>
</html>
