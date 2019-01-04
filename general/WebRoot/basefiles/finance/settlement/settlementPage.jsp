<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>结算方式</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
  	<input type="hidden" id="settlement-opera"/>
  	<div class="easyui-layout" data-options="fit:true,border:false" id="finance-layout-settlement">
		<div data-options="region:'north',split:false,border:false" style="height: 30px;overflow: hidden">
   			<div class="buttonBG" id="settlement-button"></div>
    	</div>
    	<div title="结算方式列表" data-options="region:'west',split:true" style="width:320px;">
            <table id="settlement-table-list"></table>
    	</div>
    	<div data-options="region:'center',split:true">
    		<div id="settlement-panel"></div>
   		</div>
	</div>
	<script type="text/javascript">
		function refreshLayout(title, url){
    		$("#finance-layout-settlement").layout('remove','center').layout('add',{
				region: 'center',  
			    title: title,
			    href:url
			});
    	}
    	//ajax调用
		var settlement_ajax = function (Data, Action) {
		    var ajax = $.ajax({
		        type: 'post',
		        cache: false,
		        url: Action,
		        data: Data,
		        async: false,
		        success:function(data){
		        	loaded();
		        }
		    })
		    return ajax.responseText;
		}
		
		//检验输入值的最大长度
		$.extend($.fn.validatebox.defaults.rules, {
			validID:{
				validator : function(value,param) {
					var reg=eval("/^[A-Za-z0-9]{0,"+param[0]+"}$/");
					if(reg.test(value)){
						var ret = settlement_ajax({id:value},'basefiles/finance/isUsedSettlementID.do');
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
						var ret = settlement_ajax({name:value},'basefiles/finance/isUsedSettlementName.do');
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
	  		}
		});
		
		$(function(){
			var settlementCol=$("#settlement-table-list").createGridColumnLoad({
	     	name:'base_finance_settlement',
	     	frozenCol:[[]],
	     	commonCol:[[
	     				{field:'id',title:'编码',width:50,sortable:true},
	     				{field:'name',title:'名称',width:90,sortable:true},
	     				{field:'type',title:'类型',width:40,sortable:true,
	     					formatter:function(val){
				        		if(val=="1"){
				        			return "月结";
				        		}else if(val=="2"){
				        			return "日结";
				        		}else if(val=="3"){
				        			return "现结";
				        		}else if(val=="4"){
				        			return "年结";
				        		}
				        	}
	     				},
	     				{field:'days',title:'天数',width:40,sortable:true,align:'right'},
	     				{field:'state',title:'状态',width:50,sortable:true,
	     					formatter:function(val,rowData,rowIndex){
								return rowData.stateName;
							}
	     				},
	     				{field:'remark',title:'备注',width:100,hidden:true,sortable:true},
						{field:'addusername',title:'建档人',width:60,hidden:true,sortable:true},
						{field:'adddeptname',title:'建档部门',width:80,hidden:true,sortable:true},
						{field:'addtime',title:'建档时间',width:115,hidden:true,sortable:true},
						{field:'modifyusername',title:'最后修改人',width:60,hidden:true,sortable:true},
						{field:'modifytime',title:'最后修改时间',width:115,hidden:true,sortable:true},
						{field:'openusername',title:'启用人',width:60,hidden:true,sortable:true},
						{field:'opentime',title:'启用时间',width:115,hidden:true,sortable:true},
						{field:'closeusername',title:'禁用人',width:60,hidden:true,sortable:true},
						{field:'closetime',title:'禁用时间',width:115,hidden:true,sortable:true}
		     	]]
	     });
	    $('#settlement-table-list').datagrid({
  			authority:settlementCol,
  	 		frozenColumns:settlementCol.frozen,
			columns:settlementCol.common,
		    fit:true, 
			method:'post',
			rownumbers:true,
			pagination:true,
			idField:'id',
			singleSelect:true,
			url:'basefiles/finance/getSettlementListPage.do',
			onLoadSuccess:function(){
		    	var p = $('#settlement-table-list').datagrid('getPager');  
			    $(p).pagination({  
			        beforePageText: '',//页数文本框前显示的汉字  
			        afterPageText: '',  
			        displayMsg: ''
			    });
	    	},
	    	onClickRow:function(rowIndex, rowData){
	    		refreshLayout("结算方式【详情】",'basefiles/finance/settlementViewPage.do?id='+rowData.id);
				$("#settlement-opera").val("view");
	    	}
		}).datagrid("columnMoving");
			$("#settlement-button").buttonWidget({
				//初始默认按钮 根据type对应按钮事件
				initButton:[
					{},
					<security:authorize url="/basefiles/finance/settlementAddBtn.do">
						{
							type:'button-add',
							handler:function(){
								refreshLayout("结算方式【新增】",'basefiles/finance/settlementAddPage.do');
								$("#settlement-opera").val("add");
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/finance/settlementEditBtn.do">
			 			{
				 			type:'button-edit',
				 			handler:function(){
				 				var settlement=$("#settlement-table-list").datagrid('getSelected');
				 				$.ajax({   
						            url :'system/lock/isDoLockData.do',
						            type:'post',
						            data:{id:settlement.id,tname:'t_base_finance_settlement'},
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
				 				refreshLayout("结算方式【修改】",'basefiles/finance/settlementEditPage.do?id='+settlement.id);
								$("#settlement-opera").val("edit");
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/finance/settlementHoldBtn.do">
			 			{
			 				type:'button-hold',
			 				handler:function(){
				 				var type = $("#settlement-opera").val();
				 				loading("提交中..");
				 				if(type=="add"){
				 					addSettlement("hold");
				 				}else if(type=="edit"){
				 					editSettlement("hold");
				 				}
				 			}
				 		},
			 		</security:authorize>
					<security:authorize url="/basefiles/finance/settlementSaveBtn.do">
			 			{
				 			type:'button-save',
				 			handler:function(){
				 				var type = $("#settlement-opera").val();
				 				if(type=="add"){
				 					addSettlement("save");
				 				}else if(type=="edit"){
				 					editSettlement("save");
				 				}
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/finance/settlementDelBtn.do">
			 			{
				 			type:'button-delete',
				 			handler:function(){
				 				var settlement=$("#settlement-table-list").datagrid('getSelected');
				 				var url = 'basefiles/finance/deleteSettlement.do?id='+settlement.id;
				 				$.ajax({
						            url :'system/lock/isLockData.do',
						            type:'post',
						            data:{id:settlement.id,tname:'t_base_finance_settlement'},
						            dataType:'json',
						            async: false,
						            success:function(json){
						            	flag = json.flag
						            }
						        });
				 				if(flag){
				 					$.messager.alert("警告","该数据正在被其他人操作，暂不能修改！");
				 					return false;
				 				}
				 				if("1" == settlement.state){
				 					$.messager.alert("提示","启用状态不能删除!");
				 					return false;
				 				}
				 				$.messager.confirm("提醒", "是否删除该结算方式?", function(r){
									if (r){
										loading("删除中..");
										$.ajax({   
								            url :url,
								            type:'post',
								            dataType:'json',
								            success:function(json){
								            	loaded();
								            	if(json.flag==true){
								            		$.messager.alert("提醒","删除成功！");
								            		//按钮点击后 控制按钮状态显示
				 									$("#settlement-button").buttonWidget("initButtonType","list");
								            		$("#finance-layout-settlement").layout('remove','center');
								            		$('#settlement-table-list').datagrid('clearSelections');
								            		$('#settlement-table-list').datagrid('reload');
								            	}else{
								            		if(json.delFlag==false){
								            			$.messager.alert("警告","该数据已被引用，不能删除！");
								            		}else{
								            			$.messager.alert("警告","删除失败！");
								            		}
								            	}
								            }
								        });
									}
								});
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/finance/settlementOpenBtn.do">
			 			{
				 			type:'button-open',
				 			handler:function(){
				 				var settlement=$("#settlement-table-list").datagrid('getSelected');
				 				loading("启用中..");
				 				$.ajax({   
						            url :'basefiles/finance/enableSettlement.do?id='+settlement.id,
						            type:'post',
						            dataType:'json',
						            success:function(json){
						            	loaded();
						            	if(json.flag==true){
						            		$.messager.alert("提醒","启用成功！");
						            		refreshLayout("结算方式详情】",'basefiles/finance/settlementViewPage.do?id='+settlement.id);
											$("#settlement-opera").val("view");
						            		$('#settlement-table-list').datagrid('reload');
						            	}else{
					            			$.messager.alert("警告","启用失败！");
						            	}
						            }
						        });
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/finance/settlementCloseBtn.do">
			 			{
				 			type:'button-close',
				 			handler:function(){
				 				var settlement=$("#settlement-table-list").datagrid('getSelected');
				 				loading("禁用中..");
				 				$.ajax({   
						            url :'basefiles/finance/disableSettlement.do?id='+settlement.id,
						            type:'post',
						            dataType:'json',
						            success:function(json){
						            	loaded();
						            	if(json.flag==true){
						            		$.messager.alert("提醒","禁用成功！");
						            		refreshLayout("结算方式详情】",'basefiles/finance/settlementViewPage.do?id='+settlement.id);
											$("#settlement-opera").val("veiw");
						            		$('#settlement-table-list').datagrid('reload');
						            	}else{
					            			$.messager.alert("警告","禁用失败！");
						            	}
						            }
						        });
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/finance/settlementImportBtn.do">
			 			{
				 			type:'button-import',
				 			attr:{
				 				clazz: "financeService", //spring中注入的类名
						 		method: "addDRSettlement", //插入数据库的方法
						 		tn: "t_base_finance_settlement", //表名
					            module: 'basefiles', //模块名，
					            majorkey:'id',
						 		pojo: "Settlement", //实体类名，将和模块名组合成com.hd.agent.basefiles.model.Settlement。
								onClose: function(){ //导入成功后窗口关闭时操作，
							         $("#settlement-table-list").datagrid('reload');	//更新列表	                                                                                        
								}
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/finance/settlementExportBtn.do">
			 			{
				 			type:'button-export',
				 			attr:{
							 	tn: 't_base_finance_settlement', //表名
							 	name:'结算方式列表'
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/finance/settlementGiveupBtn.do">
			 			{
			 				type:'button-giveup',
			 				handler:function(){
				 				var type = $("#settlement-opera").val();
				 				if(type=="add"){
				 					$("#settlement-button").buttonWidget("initButtonType","list");
				 					$("#finance-layout-settlement").layout('remove','center');
				 				}else if(type=="edit"){
				 					var settlement=$("#settlement-table-list").datagrid('getSelected');
				 					$.ajax({   
							            url :'system/lock/unLockData.do',
							            type:'post',
							            data:{id:settlement.id,tname:'t_base_finance_settlement'},
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
					 				refreshLayout("结算方式详情】",'basefiles/finance/settlementViewPage.do?id='+settlement.id);
									$("#settlement-opera").val("veiw");
				 				}
				 			}
			 			},
		 			</security:authorize>
		 			{}
				],
				model:'base',
				type:'list',
				tname:'t_base_storage_inout'
			});
		});
	</script>
  </body>
</html>
