<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>支付方式</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
  	<input type="hidden" id="payment-opera"/>
  	<div class="easyui-layout" data-options="fit:true,border:false" id="finance-layout-payment">
		<div data-options="region:'north',split:false" style="height: 30px;overflow: hidden">
   			<div class="buttonBG" id="payment-button"></div>
    	</div>
    	<div title="支付方式列表" data-options="region:'west',split:true" style="width:280px;">
            <table id="payment-table-list"></table>
    	</div>
    	<div data-options="region:'center',split:true">
    		<div id="payment-panel"></div>
   		</div>
	</div>
	<script type="text/javascript">
		function refreshLayout(title, url){
    		$("#finance-layout-payment").layout('remove','center').layout('add',{
				region: 'center',  
			    title: title,
			    href:url
			});
    	}
    	//ajax调用
		var payment_ajax = function (Data, Action) {
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
						var ret = payment_ajax({id:value},'basefiles/finance/isUsedPaymentID.do');
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
						var ret = payment_ajax({name:value},'basefiles/finance/isUsedPaymentName.do');
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
			var paymentCol=$("#payment-table-list").createGridColumnLoad({
	     	name:'base_finance_payment',
	     	frozenCol:[[]],
	     	commonCol:[[
	     				{field:'id',title:'编码',width:50,sortable:true},
	     				{field:'name',title:'名称',width:120,sortable:true},
	     				{field:'bankname',title:'开户银行',width:120,sortable:true},
	     				{field:'account',title:'账号',width:120,sortable:true},
	     				{field:'accountingsubject',title:'对应会计科目',width:60,sortable:true},
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
	    $('#payment-table-list').datagrid({
  			authority:paymentCol,
  	 		frozenColumns:paymentCol.frozen,
			columns:paymentCol.common,
		    fit:true, 
			method:'post',
			rownumbers:true,
			pagination:true,
			idField:'id',
			singleSelect:true,
			url:'basefiles/finance/getPaymentListPage.do',
			onLoadSuccess:function(){
		    	var p = $('#payment-table-list').datagrid('getPager');  
			    $(p).pagination({  
			        beforePageText: '',//页数文本框前显示的汉字  
			        afterPageText: '',  
			        displayMsg: ''
			    });
	    	},
	    	onClickRow:function(rowIndex, rowData){
	    		refreshLayout("支付方式【详情】",'basefiles/finance/paymentViewPage.do?id='+rowData.id);
				$("#payment-opera").val("view");
	    	}
		}).datagrid("columnMoving");
			$("#payment-button").buttonWidget({
				//初始默认按钮 根据type对应按钮事件
				initButton:[
					{},
					<security:authorize url="/basefiles/finance/paymentAddBtn.do">
						{
							type:'button-add',
							handler:function(){
								refreshLayout("支付方式【新增】",'basefiles/finance/paymentAddPage.do');
								$("#payment-opera").val("add");
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/finance/paymentEditBtn.do">
			 			{
				 			type:'button-edit',
				 			handler:function(){
				 				var payment=$("#payment-table-list").datagrid('getSelected');
				 				$.ajax({   
						            url :'system/lock/isDoLockData.do',
						            type:'post',
						            data:{id:payment.id,tname:'t_base_finance_payment'},
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
				 				refreshLayout("支付方式【修改】",'basefiles/finance/paymentEidtPage.do?id='+payment.id);
								$("#payment-opera").val("edit");
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/finance/paymentHoldBtn.do">
			 			{
			 				type:'button-hold',
			 				handler:function(){
				 				var type = $("#payment-opera").val();
				 				loading("提交中..");
				 				if(type=="add"){
				 					addPayment("hold");
				 				}else if(type=="edit"){
				 					editPayment("hold");
				 				}
				 			}
				 		},
			 		</security:authorize>
					<security:authorize url="/basefiles/finance/paymentSaveBtn.do">
		 			{
			 			type:'button-save',
			 			handler:function(){
			 				var type = $("#payment-opera").val();
			 				loading("提交中..");
			 				if(type=="add"){
			 					addPayment("save");
			 				}else if(type=="edit"){
			 					editPayment("save");
			 				}
			 			}
		 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/finance/paymentDelBtn.do">
			 			{
				 			type:'button-delete',
				 			handler:function(){
				 				var payment=$("#payment-table-list").datagrid('getSelected');
				 				var url = 'basefiles/finance/deletePayment.do?id='+payment.id;
				 				$.ajax({
						            url :'system/lock/isLockData.do',
						            type:'post',
						            data:{id:payment.id,tname:'t_base_finance_payment'},
						            dataType:'json',
						            async: false,
						            success:function(json){
						            	flag = json.flag
						            }
						        });
				 				if(flag){
				 					$.messager.alert("警告","该数据正在被其他人操作，暂不能修改!");
				 					return false;
				 				}
				 				if("1" == payment.state){
				 					$.messager.alert("提示","启用状态不能删除!");
				 					return false;
				 				}
				 				$.messager.confirm("提醒", "是否删除该支付方式?", function(r){
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
				 									$("#payment-button").buttonWidget("initButtonType","list");
								            		$("#finance-layout-payment").layout('remove','center');
								            		$('#payment-table-list').datagrid('clearSelections');
								            		$('#payment-table-list').datagrid('reload');
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
					<security:authorize url="/basefiles/finance/paymentOpenBtn.do">
			 			{
				 			type:'button-open',
				 			handler:function(){
				 				var payment=$("#payment-table-list").datagrid('getSelected');
				 				loading("启用中..");
				 				$.ajax({   
						            url :'basefiles/finance/enablePayment.do?id='+payment.id,
						            type:'post',
						            dataType:'json',
						            success:function(json){
						            	loaded();
						            	if(json.flag==true){
						            		$.messager.alert("提醒","启用成功！");
						            		refreshLayout("支付方式详情】",'basefiles/finance/paymentViewPage.do?id='+payment.id);
											$("#payment-opera").val("view");
						            		$('#payment-table-list').datagrid('reload');
						            	}else{
					            			$.messager.alert("警告","启用失败！");
						            	}
						            }
						        });
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/finance/paymentCloseBtn.do">
			 			{
				 			type:'button-close',
				 			handler:function(){
				 				var payment=$("#payment-table-list").datagrid('getSelected');
				 				loading("禁用中..");
				 				$.ajax({   
						            url :'basefiles/finance/disablePayment.do?id='+payment.id,
						            type:'post',
						            dataType:'json',
						            success:function(json){
						            	loaded();
						            	if(json.flag==true){
						            		$.messager.alert("提醒","禁用成功！");
						            		refreshLayout("支付方式详情】",'basefiles/finance/paymentViewPage.do?id='+payment.id);
											$("#payment-opera").val("veiw");
						            		$('#payment-table-list').datagrid('reload');
						            	}else{
					            			$.messager.alert("警告","禁用失败！");
						            	}
						            }
						        });
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/finance/paymentImportBtn.do">
			 			{
				 			type:'button-import',
				 			attr:{
				 				clazz: "financeService", //spring中注入的类名
						 		method: "addDRPayment", //插入数据库的方法
						 		tn: "t_base_finance_payment", //表名
					            module: 'basefiles', //模块名，
					            majorkey:'id',
						 		pojo: "Payment", //实体类名，将和模块名组合成com.hd.agent.basefiles.model.Payment。
								onClose: function(){ //导入成功后窗口关闭时操作，
							         $("#payment-table-list").datagrid('reload');	//更新列表	                                                                                        
								}
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/finance/paymentExportBtn.do">
			 			{
				 			type:'button-export',
				 			attr:{
							 	tn: 't_base_finance_payment', //表名
							 	name:'支付方式列表'
				 			}
			 			},
		 			</security:authorize>
					<security:authorize url="/basefiles/finance/paymentGiveupBtn.do">
			 			{
			 				type:'button-giveup',
			 				handler:function(){
				 				var type = $("#payment-opera").val();
				 				if(type=="add"){
				 					$("#payment-button").buttonWidget("initButtonType","list");
				 					$("#finance-layout-payment").layout('remove','center');
				 				}else if(type=="edit"){
				 					var payment=$("#payment-table-list").datagrid('getSelected');
				 					$.ajax({   
							            url :'system/lock/unLockData.do',
							            type:'post',
							            data:{id:payment.id,tname:'t_base_finance_payment'},
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
					 				refreshLayout("支付方式详情】",'basefiles/finance/paymentViewPage.do?id='+payment.id);
									$("#payment-opera").val("veiw");
				 				}
				 			}
			 			},
		 			</security:authorize>
					{}
				],
				model:'base',
				type:'list',
				tname:'t_base_finance_payment'
			});
		});
	</script>
  </body>
</html>
