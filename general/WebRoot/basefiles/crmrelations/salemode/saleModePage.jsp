<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售方式</title>
    <%@include file="/include.jsp" %>  
  </head>
  
  <body>
  	<input type="hidden" id="salemode-opera"/>
	<input type="hidden" id="salemode-index-SMDList"/>
   <div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'north',border:true" style="height: 30px;overflow: hidden">
    		<div class="buttonBG" id="salemode-button-layout"></div>
    	</div>
    	<div data-options="region:'west',border:false,split:true" title="销售方式列表" style="width:300px;border: 0px;">
     		<table id="salemode-table-list"></table>
	    </div>
	    <div data-options="region:'center',border:true">
	    	<div id="salemode-div-salemodeInfo"></div>
		</div>
    </div>
    <script type="text/javascript">
    	var salemode_AjaxConn = function (Data, Action, Str) {
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
		//加载下拉框 
		function loadDropdown(){
			//状态
			$('#common-combobox-state').combobox({
			    url:'common/sysCodeList.do?type=state',   
			    valueField:'id',   
			    textField:'name'  
			});
		}
		//检验输入值的最大长度
		$.extend($.fn.validatebox.defaults.rules, {
			validID:{
				validator : function(value,param) {
					var reg=eval("/^[A-Za-z0-9]{0,"+param[0]+"}$/");
					if(reg.test(value)){
						var ret = salemode_AjaxConn({id:value},'basefiles/crmrelations/isRepeatSaleModeId.do');//true 重复，false 不重复
						var retJson = $.parseJSON(ret);
						if(retJson.flag){
							$.fn.validatebox.defaults.rules.validID.message = '编码已存在, 请重新输入!';
							return false;
						}
					}
					else{
						$.fn.validatebox.defaults.rules.validID.message = '最多可输入{0}个字符!';
						return false;
					}
		            return true;
		        }, 
		        message : '' 
			},
			validName:{
				validator : function(value,param) {
					if(value.length <= param[0]){
						var ret = salemode_AjaxConn({name:value},'basefiles/crmrelations/isRepeatSaleModeName.do');//true 重复，false 不重复
						var retJson = $.parseJSON(ret);
						if(retJson.flag){
							$.fn.validatebox.defaults.rules.validName.message = '名称重复, 请重新输入!';
							return false;
						}
					}
					else{
						$.fn.validatebox.defaults.rules.validName.message = '最多可输入{0}个字符!';
						return false;
					}
					return true;
		        }, 
		        message : '' 
			},
			maxLen:{
	  			validator : function(value,param) { 
		            return value.length <= param[0]; 
		        }, 
		        message : '最多可输入{0}个字符!' 
	  		},
	  		validStageCode:{
				validator : function(value,param) {
					var reg=eval("/^[A-Za-z0-9]{0,"+param[0]+"}$/");
					if(reg.test(value)){
						var salemodeid = $("#saleMode-id-hdSaleModeDetail").val();
						var ret = salemode_AjaxConn({code:value,salemodeid:salemodeid},'basefiles/crmrelations/isRepeatSatgeCode.do');//true 重复，false 不重复
						var retJson = $.parseJSON(ret);
						if(retJson.flag){
							$.fn.validatebox.defaults.rules.validStageCode.message = '编码已存在, 请重新输入!';
							return false;
						}
					}
					else{
						$.fn.validatebox.defaults.rules.validStageCode.message = '最多可输入{0}个字符!';
						return false;
					}
		            return true;
		        }, 
		        message : '' 
			},
			validStageName:{
				validator : function(value,param) {
					if(value.length <= param[0]){
						var salemodeid = $("#saleMode-id-hdSaleModeDetail").val();
						var ret = salemode_AjaxConn({name:value,salemodeid:salemodeid},'basefiles/crmrelations/isRepeatSatgeName.do');//true 重复，false 不重复
						var retJson = $.parseJSON(ret);
						if(retJson.flag){
							$.fn.validatebox.defaults.rules.validStageName.message = '名称重复, 请重新输入!';
							return false;
						}
					}
					else{
						$.fn.validatebox.defaults.rules.validStageName.message = '最多可输入{0}个字符!';
						return false;
					}
					return true;
		        }, 
		        message : '' 
			}
		});
		
    	//判断是否加锁
		function isLockData(id,tablename){
			var flag = false;
			$.ajax({
	            url :'system/lock/isLockData.do',
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
	    //根据初始的列与用户保存的列生成以及字段权限生成新的列
	     var salemodeListColJson=$("#salemode-table-list").createGridColumnLoad({
	     	name:'base_crm_salemode',
	     	frozenCol:[[]],
	     	commonCol:[[
				{field:'id',title:'编码',width:80,sortable:true},
				{field:'name',title:'名称',width:80,sortable:true},  
				{field:'state',title:'状态',width:60,sortable:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.stateName;
					}
				},
				{field:'remark',title:'备注',width:130,sortable:true},
				{field:'addusername',title:'建档人',width:80,sortable:true,hidden:true},
				{field:'adddeptname',title:'建档部门',width:80,sortable:true,hidden:true},
				{field:'addtime',title:'建档时间',width:80,sortable:true,hidden:true},
				{field:'modifyusername',title:'最后修改人',width:80,sortable:true,hidden:true},
				{field:'modifytime',title:'最后修改时间',width:80,sortable:true,hidden:true},
				{field:'openusername',title:'启用人',width:80,sortable:true,hidden:true},
				{field:'opentime',title:'启用时间',width:80,sortable:true,hidden:true},
				{field:'closeusername',title:'禁用人',width:80,sortable:true,hidden:true},
				{field:'closetime',title:'禁用时间',width:80,sortable:true,hidden:true}
			]]
	     });
		
		var saleModeDetail_editIndex = undefined;
			function saleModeDetail_endEditing(){
   			if (saleModeDetail_editIndex == undefined){
   				return true
   			}else{return false;}
   		}
	     //销售阶段列表
	     function saleModeDetail(type){
	     	var url = "basefiles/crmrelations/showSaleModeDetailList.do";
	     	if(type != "add"){
	     		url = "basefiles/crmrelations/showSaleModeDetailList.do?salemodeid="+$("#saleMode-id-hdSaleModeDetail").val();
	     	}
	     	$dgSaleModeDetailList = $("#saleMode-table-saleModeDetailList");
			$dgSaleModeDetailList.datagrid({
	   			method:'post',
	   			title:'',
	  			singleSelect:true,
	  			url:url,
	  			border:false,
	  			pageSize:4,
	   			columns:[[
	   				{field:'id',title:'编号',hidden:true},
	   				{field:'salemodeid',title:'销售方式编码',width:150,hidden:true},
	   				{field:'code',title:'销售阶段编码',width:80,align:'center',editor:{
	   					type:'validatebox',
	   					options:{
	   						validType:"validStageCode[20]",
	   						required:true
	   					}
	   				}},
	   				{field:'name',title:'销售阶段名称',width:100,align:'center',editor:{
	   					type:'validatebox',
	   					options:{
	   						validType:"validStageName[50]",
	   						required:true
	   					}
	   				}},
	  				{field:'stage',title:'阶段',width:120,align:'center',
	  					editor:{
	  						type:'selectboxText',
	  						options:{
	  							vals:'1,2,3,4',
		  						texts:'A-发现销售机会,B-处理交易,C-正在关闭,D-失去的交易',
		  						defaultChecked:'1',
		  						required:true
	  						}
	  					},
	  					formatter:function(val,rowData,rowIndex){
	  						switch(val){
	  							case '1':
	  								return "A-发现销售机会";
	  							case '2':
	  								return "B-处理交易";
	  							case '3':
	  								return "C-正在关闭";
	  							case '4':
	  								return "D-失去的交易";
	  						}
	  					}
	  				},
	  				{field:'probability',title:'成功的概率%',width:80,align:'right',editor:{type:'numberbox',options:{precision:2,max:99,min:0}},
	  					formatter:function(val,rowData,rowIndex){
	  						if(val != "" && val != null){
	  							return formatterMoney(val);
	  						}
	  					}
	  				},
	  				{field:'remark',title:'备注',width:150,align:'center',editor:'text'}
	   			]],
	   			toolbar : [{
		   				text : "添加",
		                iconCls : "button-add",
		                handler : function() {
		                	if(saleModeDetail_endEditing()){
		                		//var id = goodsInfo_getRandomid();
		                		$dgSaleModeDetailList.datagrid('appendRow', {});
			                	saleModeDetail_editIndex = $dgSaleModeDetailList.datagrid('getRows').length-1;
			                	$("#salemode-index-SMDList").val(saleModeDetail_editIndex);
			                	$dgSaleModeDetailList.datagrid('selectRow', saleModeDetail_editIndex).datagrid('beginEdit', saleModeDetail_editIndex);
		                	}
		                }
	  				},{
	  					text : "确定",
	                	iconCls : "button-save",
	                	handler : function() {
	                		var row = $dgSaleModeDetailList.datagrid('getSelected');
         					if (row == null) {
         						$.messager.alert("提醒","请选择行!");
         						return false;
         					}
	         				var index = $dgSaleModeDetailList.datagrid('getRowIndex', row);
	         				if($dgSaleModeDetailList.datagrid('validateRow', index)){
	         					saleModeDetail_editIndex = undefined;
					   			$dgSaleModeDetailList.datagrid('clearSelections');
					   			$dgSaleModeDetailList.datagrid('endEdit', index);
	         				}
	                	}
	  				},{
	  					text : "修改",
	                	iconCls : "button-edit",
	                	handler : function() {
	                		var row = $dgSaleModeDetailList.datagrid('getSelected');
	                		if (row == null) {
         						$.messager.alert("提醒","请选择行!");
         						return false;
         					}
			            	var rowIndex = $dgSaleModeDetailList.datagrid('getRowIndex', row);
		                	if(saleModeDetail_endEditing()){
			                	$("#salemode-index-SMDList").val(rowIndex);
			                	$("#saleMode-code-hdSaleModeDetail").val(row.code);
			                	$("#saleMode-name-hdSaleModeDetail").val(row.name);
								$dgSaleModeDetailList.datagrid('beginEdit', rowIndex);
								saleModeDetail_editIndex = rowIndex;
							}
	                	}
	  				},{
	  					text : "删除",
	                	iconCls : "button-delete",
	               		handler : function() {
	                		var row = $dgSaleModeDetailList.datagrid('getSelected');
	                		if (row == null) {
       							$.messager.alert("提醒","请选择行!");
       							return false;
       						}
			            	var rowIndex = $dgSaleModeDetailList.datagrid('getRowIndex', row);
			            	$dgSaleModeDetailList.datagrid('deleteRow', rowIndex);
			   				saleModeDetail_editIndex = undefined;
			   				$dgSaleModeDetailList.datagrid('clearSelections');
	                }
  				}],
  				onClickRow:function(rowIndex, rowData){
  					$("#saleMode-code-hdSaleModeDetail").val(rowData.code);
  					$("#saleMode-name-hdSaleModeDetail").val(rowData.name);
  					var index = $("#salemode-index-SMDList").val();
  					if(index != null && index != ""){
  						if(!saleModeDetail_endEditing()){
	   						$dgSaleModeDetailList.datagrid('selectRow',index);
	   					}
  					}
  				}
	   		});
	     }
	     //销售方式细节详情提交
   		function saleModeDetailJson(type){
   			if($dgSaleModeDetailList!=null){
   				var effectRow = new Object();
   				if(type == "copy"){
   					var rows = $dgSaleModeDetailList.datagrid('getRows');
	   					effectRow["insertedSMD"] = JSON.stringify(rows);
   				}
   				else{
   					if ($dgSaleModeDetailList.datagrid('getChanges').length) {
		   				var inserted = $dgSaleModeDetailList.datagrid('getChanges', "inserted");
		   				var updated = $dgSaleModeDetailList.datagrid('getChanges', "updated");
		   				var deleted = $dgSaleModeDetailList.datagrid('getChanges', "deleted");
		   				if (inserted.length) {
		                    effectRow["insertedSMD"] = JSON.stringify(inserted);
		                }
		                if (updated.length) {
		                    effectRow["updatedSMD"] = JSON.stringify(updated);
		                }
						if (deleted.length) {
		                    effectRow["deletedSMD"] = JSON.stringify(deleted);
		                }
		   			}
   				}
   				return  effectRow;
   			}
	        return null;
   		}	
    	$(function(){
    		$("#salemode-button-layout").buttonWidget({
     			//初始默认按钮 根据type对应按钮事件
				initButton:[
					{},
					<security:authorize url="/basefiles/crmrelations/salemodeAddBtn.do">
						{
							type:'button-add',//新增 
							handler:function(){
								var url = "basefiles/crmrelations/showSalemodeAddPage.do";
				     			$("#salemode-div-salemodeInfo").panel({  
								    cache:false,
								    closed:true,
								    title:'销售方式【新增】',
								    maximized:true,
								    href:url
								});
								$("#salemode-div-salemodeInfo").panel("open");
								$("#salemode-opera").attr("value","add");
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/crmrelations/salemodeEditBtn.do">
			 			{
				 			type:'button-edit',//修改 
				 			handler:function(){
								var salemode=$("#salemode-table-list").datagrid('getSelected');
					  			if(salemode==null){
					  				$.messager.alert("提醒","请选择相应的销售方式!");
					  				return false;
					  			}
					  			var flag = isDoLockData(salemode.id,"t_base_crm_salemode");
								if(!flag){
									$.messager.alert("警告","该数据正在被其他人操作，暂不能修改！");
									return false;
								}
				 				var url = "basefiles/crmrelations/showSalemodeEditPage.do?id="+salemode.id;
				     			$("#salemode-div-salemodeInfo").panel({  
								    cache:false,
								    closed:true,
								    title:'销售方式【修改】',
								    maximized:true,
								    href:url
								});
								$("#salemode-div-salemodeInfo").panel("open");
								$("#salemode-opera").attr("value","edit");
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/crmrelations/salemodeHoldBtn.do">
			 			{
			 				type:'button-hold',//暂存
			 				handler:function(){
			 					if(saleModeDetail_editIndex != undefined){
			 						$.messager.alert("提醒","请确定销售阶段信息!");
			 						return false;
			 					}
			 					if($("#saleMode-id-saleModeDetail").val() == ""){
			 						$.messager.alert("提醒","请编码不能为空!");
			 						return false;
			 					}
				 				//获取当前按钮操作的状态。add表示正处于新增页面风格edit表示正处于修改页面风格
				 				var type = $("#salemode-button-layout").buttonWidget("getOperType");
				 				if(type=="add"){
						    		$.messager.confirm("提醒","是否暂存新增销售方式?",function(r){
				 						if(r){
						 					addSaleMode("hold");//暂存新增销售方式
				 						}
				 					});
				 				}
				 				else{
				 					$.messager.confirm("提醒","是否暂存修改销售方式?",function(r){
				 						if(r){
											editSaleMode("hold");//暂存修改销售方式
										}
				 					});
				 				}
				 			}
				 		},
			 		</security:authorize>
					<security:authorize url="/basefiles/crmrelations/salemodeSaveBtn.do">
		 			{
			 			type:'button-save',//保存
			 			handler:function(){
			 				if(saleModeDetail_editIndex != undefined){
		 						$.messager.alert("提醒","请确定销售阶段信息!");
		 						return false;
		 					}
			 				//获取当前按钮操作的状态。add表示正处于新增页面风格edit表示正处于修改页面风格
			 				var type = $("#salemode-button-layout").buttonWidget("getOperType");
			 				if(type=="add"){
			 					$.messager.confirm("提醒","是否保存新增销售方式?",function(r){
			 						if(r){
			 							addSaleMode("save");//保存新增销售方式
			 						}
			 					});
			 				}
			 				else{
			 					$.messager.confirm("提醒","是否保存修改销售方式?",function(r){
			 						if(r){
			 							editSaleMode("save");//保存修改销售方式
			 						}
			 					});
			 				}
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/crmrelations/salemodeDelBtn.do">
			 			{
				 			type:'button-delete',//删除
				 			handler:function(){
				 				var salemode=$("#salemode-table-list").datagrid('getSelected');
					  			if(salemode==null){
					  				$.messager.alert("提醒","请选择相应的销售方式!");
					  				return false;
					  			}
					  			var flag = isLockData(salemode.id,"t_base_crm_salemode");
								if(flag){
									$.messager.alert("警告","该数据正在被其他人操作，暂不能删除！");
									return false;
								}
					  			$.messager.confirm("提醒","是否确认删除销售方式?",function(r){
					  				if(r){
					  					var ret = salemode_AjaxConn({id:salemode.id},'basefiles/crmrelations/deleteSaleMode.do','删除中..');
										var retJson = $.parseJSON(ret);
										if(retJson.userNum == 0){
											if(retJson.flag){
												$("#salemode-div-salemodeInfo").panel('close');
												$("#salemode-table-list").datagrid('reload');
												$("#salemode-table-list").datagrid('clearSelections');
												$.messager.alert("提醒","删除成功!");
												$("#salemode-button-layout").buttonWidget("setButtonType","0");
											}
											else{
												$.messager.alert("提醒","删除失败!");
											}
										}
										else{
											$.messager.alert("提醒","该数据已被引用，不允许删除！");
										}
					  				}
					  			});
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/crmrelations/salemodeCopyBtn.do">
			 			{
				 			type:'button-copy',//复制
				 			handler:function(){
				 				var salemode=$("#salemode-table-list").datagrid('getSelected');
					  			if(salemode==null){
					  				$.messager.alert("提醒","请选择相应的销售方式!");
					  				return false;
					  			}
				 				var url = "basefiles/crmrelations/showSaleModeCopyPage.do?id="+salemode.id;
				     			$("#salemode-div-salemodeInfo").panel({  
								    cache:false,
								    closed:true,
								    title:'销售方式【新增】',
								    maximized:true,
								    href:url
								});
								$("#salemode-div-salemodeInfo").panel("open");
								$("#salemode-opera").attr("value","add");
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/crmrelations/salemodeEnableBtn.do">
			 			{
				 			type:'button-open',//启用 
				 			handler:function(){
				 				var salemode=$("#salemode-table-list").datagrid('getSelected');
					  			if(salemode==null){
					  				$.messager.alert("提醒","请选择相应的销售方式!");
					  				return false;
					  			}
					  			$.messager.confirm("提醒","是否确认启用销售方式?",function(r){
					  				if(r){
						  				var ret = salemode_AjaxConn({id:salemode.id},'basefiles/crmrelations/enableSaleMode.do','启用中..');
										var retJson = $.parseJSON(ret);
										if(retJson.flag){
											$("#salemode-table-list").datagrid('reload');
											$("#salemode-div-salemodeInfo").panel('refresh','basefiles/crmrelations/showSaleModeViewPage.do?id='+salemode.id);
											$("#salemode-button-layout").buttonWidget("setButtonType","1");
											$.messager.alert("提醒","销售方式启用成功!");
										}
										else{
											$.messager.alert("提醒","销售方式启用失败!");
										}
					  				}
					  			});
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/crmrelations/salemodeDisableBtn.do">
			 			{
				 			type:'button-close',//禁用 
				 			handler:function(){
				 				var salemode=$("#salemode-table-list").datagrid('getSelected');
					  			if(salemode==null){
					  				$.messager.alert("提醒","请选择相应的销售方式!");
					  				return false;
					  			}
					  			$.messager.confirm("提醒","是否确认禁用销售方式?",function(r){
					  				if(r){
					  					var ret = salemode_AjaxConn({id:salemode.id},'basefiles/crmrelations/disableSaleMode.do','禁用中..');
										var retJson = $.parseJSON(ret);
										if(retJson.flag){
											$("#salemode-table-list").datagrid('reload');
											$("#salemode-div-salemodeInfo").panel('refresh','basefiles/crmrelations/showSaleModeViewPage.do?id='+salemode.id);
											$.messager.alert("提醒","销售方式禁用成功!");
											$("#salemode-button-layout").buttonWidget("setButtonType","0");
										}
										else{
											$.messager.alert("提醒",""+retJson.Mes+"销售方式禁用失败!");
										}
					  				}
					  			});
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/crmrelations/salemodeImportBtn.do">
			 			{
				 			type:'button-import',//导入
				 			attr:{
				 				clazz: "cmrService", //spring中注入的类名
						 		method: "addDRSaleMode", //插入数据库的方法
						 		tn: "t_base_crm_salemode", //表名
					            module: 'basefiles', //模块名，
						 		pojo: "SaleMode", //实体类名，将和模块名组合成com.hd.agent.basefiles.model.SaleMode。
								onClose: function(){ //导入成功后窗口关闭时操作，
							         //$("#personnel-table-personnelList").datagrid('clearSelections');	  
							         $("#salemode-table-list").datagrid('reload');	//更新列表	                                                                          
								}
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/crmrelations/salemodeExportBtn.do">
			 			{
				 			type:'button-export',//导出 
				 			attr:{
								queryForm: "", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
						 		tn: 't_base_crm_salemode', //表名
						 		name:'销售方式列表'
							}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/crmrelations/salemodePrintViewBtn.do">
			 			{
				 			type:'button-preview',//打印预览
				 			handler:function(){
				 				alert("print");
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/crmrelations/salemodePrintBtn.do">
			 			{
				 			type:'button-print',//打印 
				 			handler:function(){
				 				alert("print");
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/crmrelations/salemodeGiveupBtn.do">
						{
			 				type:'button-giveup',//放弃 
			 				handler:function(){
				 				var type = $("#salemode-button-layout").buttonWidget("getOperType");
				 				saleModeDetail_editIndex = undefined;
				 				if(type=="add"){
				 					$("#salemode-button-layout").buttonWidget("initButtonType","list");
				 					$("#salemode-div-salemodeInfo").panel("close");
				 				}else if(type=="edit"){
				 					var salemode=$("#salemode-table-list").datagrid('getSelected');
				 					var id = salemode.id;
				 					var state = salemode.state;
				 					$.ajax({   
							            url :'system/lock/unLockData.do',
							            type:'post',
							            data:{id:id,tname:'t_base_crm_salemode'},
							            dataType:'json',
							            async: false,
							            success:function(json){
							            	flag = json.flag
							            }
							        });
					 				if(!flag){
					 					$.messager.alert("警告","该数据正在被其他人操作，暂不能修改！");
					 					return false;
					 				}
				 					//var WCjob=document.getElementById("waresClass-iframe").contentWindow.$("#waresClass-table-list").datagrid('getSelected');
				 					$("#salemode-button-layout").buttonWidget("initButtonType","view");
				 					$("#salemode-button-layout").buttonWidget("setButtonType",state);
					 				$("#salemode-div-salemodeInfo").panel({  
										fit:true, 
										title: '销售方式【详情】',
										cache: false,
										closed:true,
										href : 'basefiles/crmrelations/showSaleModeViewPage.do?id='+id
									});
									$("#salemode-div-salemodeInfo").panel("open");
									$("#salemode-opera").attr("value","view");
				 				}
				 			}
			 			},
			 		</security:authorize>
		 			{}
	 			],
	 			model:'base',
				type:'list',
				tname:'t_base_crm_salemode',
				id:''
     		});
     		$("#salemode-table-list").datagrid({ 
     			authority:salemodeListColJson,
	  	 		frozenColumns:[[]],
				columns:salemodeListColJson.common,
	  	 		fit:true,
	  	 		method:'post',
	  	 		title:'',
	  	 		rownumbers:true,
	  	 		pagination:true,
	  	 		idField:'id',
	  	 		singleSelect:true,
			    url:'basefiles/crmrelations/showSaleModeListPage.do',
			    onLoadSuccess:function(){
			    	var p = $('#salemode-table-list').datagrid('getPager');  
				    $(p).pagination({  
				        //pageSize: 20,//每页显示的记录条数，默认为10  
				        beforePageText: '',//页数文本框前显示的汉字  
				        afterPageText: '',  
				        //showPageList:false,
				        displayMsg: ''
				    });
				   //$("#salemode-button-layout").buttonWidget('disableButton','button-add');
		    	},
		    	onClickRow:function(rowIndex, rowData){
		    		var url = "basefiles/crmrelations/showSaleModeViewPage.do?id="+rowData.id;
	     			$("#salemode-div-salemodeInfo").panel({  
					    cache:false,
					    closed:true,
					    title:'销售方式【详情】',
					    maximized:true,
					    href:url
					});
					$("#salemode-div-salemodeInfo").panel("open");
					$("#salemode-opera").attr("value","view");
					$("#salemode-button-layout").buttonWidget("setDataID",{id:rowData.id,state:rowData.state,type:'view'});
		    	}
			}).datagrid("columnMoving");
    	});
    </script>
  </body>
</html>
