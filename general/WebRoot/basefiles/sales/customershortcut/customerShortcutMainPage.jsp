<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>客户档案</title>
    <%@include file="/include.jsp" %>   
  </head>
  <body>
  	<input type="hidden" id="sales-leaveLen-customer" value="${lenStr }" />
  	<input type="hidden" id="sales-backid-customer" value="<c:out value="${id }"></c:out>" />
	<div class="easyui-layout" title="客户档案简化版" data-options="fit:true" id="sales-layout-customer">
		<div data-options="region:'north',border:false" style="height:30px;overflow: hidden">
	 		<div class="buttonBG" id="sales-buttons-customershortcut" ></div>
	   	</div>
	   	<div data-options="region:'center',border:false" >
	   		<div class="easyui-panel" data-options="fit:true" id="sales-panel-customershortcut">
	   	</div>
	</div>
	<input type="hidden" id="sales-customerids-allotPSNCustomer"/>
	<div id="sales-dialog-allotPSNCustomer"></div>
	<div id="sales-dialog-clearPSNCustomer"></div>
    <script type="text/javascript">
    	var customer_title = tabsWindowTitle('/basefiles/showCustomerShortcutPage.do');
    	
    	var customer_lenArr = $("#sales-leaveLen-customer").val().split(',');
    	function refreshPanel(url){ //更新panel
    		$("#sales-panel-customershortcut").panel('refresh', url);
    	}
    	
    	var customershortcut_type = "${type}";
    	var customershortcut_url = "basefiles/showCustomerShortcutAddPage.do";
    	if(customershortcut_type == "add"){
   			customershortcut_url = "basefiles/showCustomerShortcutAddPage.do";
   		}else if(customershortcut_type == "edit"){
   			customershortcut_url = "basefiles/showCustomerShortcutEditPage.do?id="+encodeURIComponent($("#sales-backid-customer").val());
   		}else if(customershortcut_type == "view"){
   			customershortcut_url = "basefiles/showCustomerShortcutViewPage.do?id="+encodeURIComponent($("#sales-backid-customer").val());
   		}else if(customershortcut_type == "copy"){
   			customershortcut_url = "basefiles/showCustomerShortcutCopyPage.do?id="+encodeURIComponent($("#sales-backid-customer").val());
   		}
   		
   		var customer_ajaxContent = function (param, url) { //同步ajax
		    var ajax = $.ajax({
		        type: 'post',
		        cache: false,
		        url: url,
		        data: param,
		        async: false
		    });
		    return ajax.responseText;
		}
   		
   		//验证长度且验证重复
    	function validLengthAndUsed(len, url, id, initValue, message){ //initValue：修改的时候有初始值，判断是否为初始值，是不进行重复验证，否则修改的时候会提醒初始值重复，这里是不需要验证的。
    		$.extend($.fn.validatebox.defaults.rules, {
				validLength:{
			    	validator:function(value){
			    		if(value == initValue){
  							return true;
  						}
			    		var reg=eval("/^[A-Za-z0-9]{0,20}$/");//正则表达式使用变量 
	  					if(reg.test(value) == true){
				    		var data=customer_ajaxContent({id:value},url);
		  					var json = $.parseJSON(data);
	    					if(json.flag == true){
		    					$.fn.validatebox.defaults.rules.validLength.message = message;
		    					return false;
		    				}else{
	    						return true;
		    				}
	    				}else{
	    					$.fn.validatebox.defaults.rules.validLength.message ='请输入不大于20位字符!';
	    					return false;
	    				}
			    	},
			    	message:''
			    }
			});
    	}
    	
    	function selectControl(){
			var v = $("#customerShortcut-select-overcontrol option:selected").val();
			if(v == "0"){
				$("#customerShortcut-input-overgracedate").numberbox("clear");
				changeValue("");
				$("#customerShortcut-input-overgracedate").numberbox("disable");
			}
			else{
				$("#customerShortcut-input-overgracedate").numberbox("enable");
			}
		}
		
		function changeValue(val){
			$("#customer-hd-overgracedate").val(val);
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
    		$("#sales-panel-customershortcut").panel({
				href:customershortcut_url,
			    cache:false,
			    maximized:true,
			    border:false
			});
    		$("#sales-buttons-customershortcut").buttonWidget({
				initButton:[
					{},
					<security:authorize url="/basefiles/customerAdd.do">
						{
							type: 'button-add',
							handler: function(){
								refreshPanel('basefiles/showCustomerShortcutAddPage.do?type="add"');
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/customerEdit.do">
						{
							type: "button-edit",
							handler: function(){
								var id = $("#sales-id-customerAddPage").val();
								if(id == ""){
									return false;
								}
								var flag = isDoLockData(id,"t_base_sales_customer");
				 				if(!flag){
				 					$.messager.alert("警告","该数据正在被其他人操作，暂不能修改！");
				 					return false;
				 				}
								refreshPanel('basefiles/showCustomerShortcutEditPage.do?id='+ encodeURIComponent(id));
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/customerSave.do">
						{
							type: "button-save",
							handler: function(){
								$("#sales-button-clicktype").val("0");
								$("#sales-save-customerShortcut").click();
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/customerSaveopen.do">
						{
							type: "button-saveopen",
							handler: function(){
								$("#sales-button-clicktype").val("1");
								$("#sales-save-customerShortcut").click();
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/customerGiveUp.do">
						{
							type:'button-giveup',
							handler:function(){
								var type = $("#sales-buttons-customershortcut").buttonWidget("getOperType");
	    						if(type == "add"){
	    							var currTitle = top.$('#tt').tabs('getSelected').panel('options').title;
	    							top.$('#tt').tabs('close',currTitle);
	    						}
	    						else if(type == "edit"){
		    						var id = $("#sales-backid-customer").val();
		    						if(id == ""){
		    							return false;
		    						}
		    						$("#sales-panel-customershortcut").panel('refresh', 'basefiles/showCustomerShortcutViewPage.do?id='+ encodeURIComponent(id));
	    						}
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/customerDelete.do">
						{
							type: 'button-delete',
							handler: function(){
								var id = $("#sales-id-customerAddPage").val();
								if(id == ""){
									return false;
								}
								var flag = isDoLockData(id,"t_base_sales_customer");
				 				if(!flag){
				 					$.messager.alert("警告","该数据正在被其他人操作，暂不能删除！");
				 					return false;
				 				}
								$.messager.confirm("提醒","是否删除该客户信息?",function(r){
						  			if(r){
						  				loading("删除中..");
							  			$.ajax({
								  			url:'basefiles/deleteCustomer.do',
								  			data:{id:id},
								  			dataType:'json',
								  			type:'post',
								  			success:function(json){
								  				loaded();
								  				if(json.delFlag==true){
								  					$.messager.alert("提醒","该信息已被其他信息引用，无法删除！");
								  					return false;
								  				}
								  				if(json.flag==true){
								  		        	$.messager.alert("提醒","删除成功");
								  		        	var data = $("#sales-buttons-customershortcut").buttonWidget("removeData", '');
													if(data != null){
														$("#sales-backid-customer").val(data.id);
														refreshPanel('basefiles/showCustomerShortcutViewPage.do?id='+ encodeURIComponent(data.id));
													}
													else{
														refreshPanel('basefiles/showCustomerShortcutAddPage.do');
													}
								  		        }
								  		        else{
								  		        	$.messager.alert("提醒","删除失败");
								  		        }
								  			}
							  			});
						  			}
						  		});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/customerCopy.do">
						{
							type: "button-copy",
							handler: function(){
								var id = $("#sales-id-customerAddPage").val();
								if(id == ""){
									return false;
								}
								refreshPanel("basefiles/showCustomerShortcutCopyPage.do?id="+ encodeURIComponent(id));
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/customerOpen.do">
						{
							type: "button-open",
							handler: function(){
								var id = $("#sales-id-customerAddPage").val();
								if(id == ""){
									return false;
								}
								$.messager.confirm("提醒","确定启用该客户档案?",function(r){
						  			if(r){
						  				loading("启用中..");
							  			$.ajax({
								  			url:'basefiles/openCustomer.do',
								  			data:{id:id},
								  			dataType:'json',
								  			type:'post',
								  			success:function(json){
								  				loaded();
								  				if(json.flag==true){
								  		        	$.messager.alert("提醒","启用成功");
								  		        	refreshPanel("basefiles/showCustomerShortcutViewPage.do?id="+ encodeURIComponent(id));
								  		        }
								  		        else{
								  		        	$.messager.alert("提醒","启用失败");
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
							type: "button-close",
							handler: function(){
								var id = $("#sales-id-customerAddPage").val();
								if(id == ""){
									return false;
								}
								$.messager.confirm("提醒","禁用该客户档案，将禁用该节点下所有客户档案，是否禁用？",function(r){
						  			if(r){
						  				loading("禁用中");
							  			$.ajax({
								  			url:'basefiles/closeCustomer.do',
								  			data:{id:id},
								  			dataType:'json',
								  			type:'post',
								  			success:function(json){
								  				loaded();
								  		        $.messager.alert("提醒","禁用成功数："+ json.successNum + "<br />禁用失败数："+ json.failureNum + "<br />不可禁用数："+ json.notAllowNum);
								  		        refreshPanel("basefiles/showCustomerShortcutViewPage.do?id="+ encodeURIComponent(id));
								  			}
							  			});
						  			}
						  		});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/customerBack.do">
						{
							type: 'button-back',
							handler: function(data){
								$("#sales-backid-customer").val(data.id);
								refreshPanel("basefiles/showCustomerShortcutViewPage.do?id="+ encodeURIComponent(data.id));
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/customerNext.do">
						{
							type: 'button-next',
							handler: function(data){
								$("#sales-backid-customer").val(data.id);
								refreshPanel("basefiles/showCustomerShortcutViewPage.do?id="+ encodeURIComponent(data.id));
							}
						},
					</security:authorize>
					{}
				],
				buttons:[
					{},
					<security:authorize url="/basefiles/allotPSNCustomerBtn.do">
						{
							id:'allotpsncustomer',
							name:'分配业务员',
							iconCls:'icon-reload',
							handler:function(){
								var id = $("#sales-id-customerAddPage").val();
								if(id == ""){
									$.messager.alert("提醒","无该客户!");
									return false;
								}
								var state = $("#customerShortcut-hd-state").val();
								if("1" != state){
									$.messager.alert("提醒","启用状态下才可分配,请启用该客户!");
									return false;
								}
								$("#sales-customerids-allotPSNCustomer").val(id);
								$('#sales-dialog-allotPSNCustomer').dialog({  
								    title: '分配业务员',  
								    width: 400,  
								    height: 280,  
								    closed: false,  
								    cache: false,
								    resizable:true,
								    href: 'basefiles/showAllotPSNCustomerPage.do',
									queryParams:{idStr:id},
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
							var id = $("#sales-id-customerAddPage").val();
							if(id == ""){
								$.messager.alert("提醒","无该客户!");
								return false;
							}
							var state = $("#customerShortcut-hd-state").val();
							if("1" != state){
								$.messager.alert("提醒","启用状态下才可清除,请启用该客户!");
								return false;
							}
							var idStr = id;
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
				model: 'base',
				type: 'view',
				tname: 't_base_sales_customer',
				taburl: '/basefiles/showCustomerShortcutPage.do',
				datagrid: 'sales-datagrid-customerShortcut',
				id: $("#sales-backid-customer").val()
			});
    	});
    </script>
  </body>
</html>
