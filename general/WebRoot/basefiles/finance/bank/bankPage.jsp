<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>银行档案</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
  	<input type="hidden" id="bank-opera"/>
  	<div class="easyui-layout" data-options="fit:true,border:false" id="finance-layout-bank">
		<div data-options="region:'north',split:false" style="height: 30px;overflow: hidden">
   			<div class="buttonBG" id="bank-button"></div>
    	</div>
    	<div title="银行档案列表" data-options="region:'west',split:true" style="width:550px;">
            <table id="bank-table-list"></table>
            <div id="finance-toolbar-bank">
            	<form action="" method="post" id="finance-form-bank">
	            	<table cellpadding="2" cellspacing="0" border="0">
	            		<tr>
	            			<td>编&nbsp;&nbsp;码:</td>
	            			<td><input type="text" name="id" style="width: 100px;"/></td>
	            			<td>银行名称:</td>
	            			<td><input type="text" name="name" style="width: 100px;"/></td>
	            			<td>银行账户:</td>
	            			<td><input type="text" name="account" style="width: 100px;"/></td>
	            		</tr>
	            		<tr>
	            			<td>银行部门:</td>
	            			<td><input type="text" name="bankdeptid" id="bank-query-bankdeptid"/></td>
	            			<td colspan="4" style="padding-left: 120px">
	            				<a href="javaScript:void(0);" id="finance-queay-bank" class="button-qr">查询</a>
								<a href="javaScript:void(0);" id="finance-reload-bank" class="button-qr">重置</a>
	            			</td>
	            		</tr>
	            	</table>
	           	</form>
            </div>
    	</div>
    	<div data-options="region:'center',split:true">
    		<div id="bank-panel"></div>
   		</div>
	</div>
	<script type="text/javascript">
		function refreshLayout(title, url,type){
    		$("#finance-layout-bank").layout('remove','center').layout('add',{
				region: 'center',  
			    title: title,
			    href:url
			});
			$("#bank-opera").val(type);
    	}
    	//ajax调用
		var bank_ajax = function (Data, Action) {
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
						var ret = bank_ajax({id:value},'basefiles/finance/checkBankidUserd.do');
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
			validAccount:{
				validator : function(value,param) {
					if(value.length <= param[0]){
						var ret = bank_ajax({account:value},'basefiles/finance/checkBandAccountUserd.do');
						var retJson = $.parseJSON(ret);
						if(retJson.flag){
							$.fn.validatebox.defaults.rules.validAccount.message = '银行账户重复, 请重新输入!';
							return false;
						}
					}
					else{
						$.fn.validatebox.defaults.rules.validAccount.message = '最多可输入{0}个字符!';
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
			var bankCol=$("#bank-table-list").createGridColumnLoad({
		     	name:'base_finance_bank',
		     	frozenCol:[[]],
		     	commonCol:[[
	   				{field:'id',title:'编码',width:50,sortable:true},
	   				{field:'name',title:'银行名称',width:120,sortable:true},
	   				{field:'account',title:'银行账户',width:120,sortable:true},
	   				{field:'bankdeptid',title:'银行部门',width:100,
	   					formatter:function(val,rowData,rowIndex){
							return rowData.bankdeptname;
						}
	   				},
	   				{field:'accountingsubject',title:'开户日期',width:60,hidden:true,sortable:true},
	   				{field:'state',title:'状态',width:50,sortable:true,
	   					formatter:function(val,rowData,rowIndex){
							return rowData.stateName;
						}
	   				},
	   				{field:'remark',title:'备注',width:100,hidden:true,sortable:true}
		     	]]
		     });
		    $('#bank-table-list').datagrid({
	  			authority:bankCol,
	  	 		frozenColumns:bankCol.frozen,
				columns:bankCol.common,
			    fit:true, 
				method:'post',
				rownumbers:true,
				pagination:true,
				pageSize:50,
				idField:'id',
				toolbar:'#finance-toolbar-bank',
				singleSelect:true,
				url:'basefiles/finance/getBankListPage.do',
		    	onClickRow:function(rowIndex, rowData){
		    		refreshLayout("银行档案【详情】",'basefiles/finance/showBankViewPage.do?id='+rowData.id,'view');
		    	}
			}).datagrid("columnMoving");
			
			$("#bank-query-bankdeptid").widget({
				referwid:'RT_T_SYS_DEPT',
    			width:100,
				singleSelect:true
			});
			
			//查询
			$("#finance-queay-bank").click(function(){
	       		var queryJSON = $("#finance-form-bank").serializeJSON();
	       		$("#bank-table-list").datagrid("load",queryJSON);
			});
			//重置
			$("#finance-reload-bank").click(function(){
				$("#bank-query-bankdeptid").widget("clear");
				$("#finance-form-bank")[0].reset();
				var queryJSON = $("#finance-form-bank").serializeJSON();
				$("#bank-table-list").datagrid("load",queryJSON);
			});
			
			$("#bank-button").buttonWidget({
				//初始默认按钮 根据type对应按钮事件
				initButton:[
					{},
					<security:authorize url="/basefiles/finance/bankAddBtn.do">
						{
							type:'button-add',
							handler:function(){
								refreshLayout("银行档案【新增】",'basefiles/finance/showBankAddPage.do','add');
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/finance/bankEditBtn.do">
			 			{
				 			type:'button-edit',
				 			handler:function(){
				 				var bank=$("#bank-table-list").datagrid('getSelected');
				 				$.ajax({   
						            url :'system/lock/isDoLockData.do',
						            type:'post',
						            data:{id:bank.id,tname:'t_base_finance_bank'},
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
				 				refreshLayout("银行档案【修改】",'basefiles/finance/showBankEditPage.do?id='+bank.id,'edit');
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/finance/bankHoldBtn.do">
			 			{
			 				type:'button-hold',
			 				handler:function(){
				 				var type = $("#bank-opera").val();
				 				if(type=="add"){
				 					addBank("hold");
				 				}else if(type=="edit"){
				 					editBank("hold");
				 				}
				 			}
				 		},
			 		</security:authorize>
					<security:authorize url="/basefiles/finance/bankSaveBtn.do">
			 			{
				 			type:'button-save',
				 			handler:function(){
				 				var type = $("#bank-opera").val();
				 				if(type=="add"){
				 					addBank("save");
				 				}else if(type=="edit"){
				 					editBank("save");
				 				}
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/finance/bankDelBtn.do">
			 			{
				 			type:'button-delete',
				 			handler:function(){
				 				var flag;
				 				var bank=$("#bank-table-list").datagrid('getSelected');
				 				var url = 'basefiles/finance/deleteBank.do?id='+bank.id;
				 				$.ajax({
						            url :'system/lock/isLockData.do',
						            type:'post',
						            data:{id:bank.id,tname:'t_base_finance_bank'},
						            dataType:'json',
						            async: false,
						            success:function(json){
						            	flag = json.flag
						            }
						        });
				 				if(flag){
				 					$.messager.alert("警告","该数据正在被其他人操作，暂不能删除!");
				 					return false;
				 				}
				 				//if("1" == bank.state){
				 				//	$.messager.alert("提示","启用状态不能删除!");
				 				//	return false;
				 				//}
				 				$.messager.confirm("提醒", "是否删除该银行档案?", function(r){
									if (r){
										loading("删除中..");
										$.ajax({   
								            url :url,
								            type:'post',
								            dataType:'json',
								            success:function(json){
								            	loaded();
								            	if(json.flag){
								            		$.messager.alert("提醒","删除成功！");
				 									$("#bank-button").buttonWidget("initButtonType","list");
								            		$("#finance-layout-bank").layout('remove','center');
								            		$('#bank-table-list').datagrid('clearSelections');
								            		$('#bank-table-list').datagrid('reload');
								            	}else{
								            		if(json.delFlag){
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
					<security:authorize url="/basefiles/finance/bankOpenBtn.do">
			 			{
				 			type:'button-open',
				 			handler:function(){
				 				var bank=$("#bank-table-list").datagrid('getSelected');
				 				loading("启用中..");
				 				$.ajax({   
						            url :'basefiles/finance/openBank.do?id='+bank.id,
						            type:'post',
						            dataType:'json',
						            success:function(json){
						            	loaded();
						            	if(json.flag==true){
						            		$.messager.alert("提醒","启用成功！");
						            		refreshLayout("银行档案详情】",'basefiles/finance/showBankViewPage.do?id='+bank.id,'view');
						            		$('#bank-table-list').datagrid('reload');
						            	}else{
					            			$.messager.alert("警告","启用失败！");
						            	}
						            }
						        });
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/finance/bankCloseBtn.do">
			 			{
				 			type:'button-close',
				 			handler:function(){
				 				var bank=$("#bank-table-list").datagrid('getSelected');
				 				loading("禁用中..");
				 				$.ajax({   
						            url :'basefiles/finance/closeBank.do?id='+bank.id,
						            type:'post',
						            dataType:'json',
						            success:function(json){
						            	loaded();
						            	if(json.flag==true){
						            		$.messager.alert("提醒","禁用成功！");
						            		refreshLayout("银行档案详情】",'basefiles/finance/showBankViewPage.do?id='+bank.id,'view');
						            		$('#bank-table-list').datagrid('reload');
						            	}else{
					            			$.messager.alert("警告","禁用失败！");
						            	}
						            }
						        });
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/finance/bankImportBtn.do">
			 			{
				 			type:'button-import',
				 			attr:{
				 				clazz: "financeService", //spring中注入的类名
						 		method: "addDRbank", //插入数据库的方法
						 		tn: "t_base_finance_bank", //表名
					            module: 'basefiles', //模块名，
					            majorkey:'id',
						 		pojo: "Bank", //实体类名，将和模块名组合成com.hd.agent.basefiles.model.bank。
								onClose: function(){ //导入成功后窗口关闭时操作，
							         $("#bank-table-list").datagrid('reload');	//更新列表	                                                                                        
								}
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/finance/bankExportBtn.do">
			 			{
				 			type:'button-export',
				 			attr:{
							 	tn: 't_base_finance_bank', //表名
							 	name:'银行档案列表'
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/finance/bankGiveupBtn.do">
			 			{
			 				type:'button-giveup',
			 				handler:function(){
				 				var type = $("#bank-opera").val();
				 				if(type=="add"){
				 					$("#bank-button").buttonWidget("initButtonType","list");
				 					$("#finance-layout-bank").layout('remove','center');
				 				}else if(type=="edit"){
				 					var bank=$("#bank-table-list").datagrid('getSelected');
				 					$.ajax({   
							            url :'system/lock/unLockData.do',
							            type:'post',
							            data:{id:bank.id,tname:'t_base_finance_bank'},
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
					 				refreshLayout("银行档案【详情】",'basefiles/finance/showBankViewPage.do?id='+bank.id,'view');
				 				}
				 			}
			 			},
		 			</security:authorize>
		 			{}
				],
				model:'base',
				type:'list',
				tname:'t_base_finance_bank'
			});
		});
	</script>
  </body>
</html>
