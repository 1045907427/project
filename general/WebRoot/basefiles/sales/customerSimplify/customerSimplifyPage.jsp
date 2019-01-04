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
  	<input type="hidden" id="customer-opera"/>
	<input type="hidden" id="sales-thisId-customer" />
	<input type="hidden" id="sales-parentId-customer" />
	<input type="hidden" id="sales-isParent-customer" />
	<input type="hidden" id="sales-state-customer" />
	<input type="hidden" id="sales-level-customer" />
	<input type="hidden" id="sales-pagetype-customer" />
    <input type="hidden" id="sales-backid-customer" value="<c:out value="${id }"></c:out>" />
    <input type="hidden" id="customer-area" value="<c:out value="${area }"></c:out>" />
    <input type="hidden" id="customer-sort" value="<c:out value="${sort }"></c:out>" />
	<input type="hidden" id="sales-hasLevel-customer" value="${len }" />
	<input type="hidden" id="sales-leaveLen-customer" value="${lenStr }" />
	<div class="easyui-layout" data-options="fit:true,border:true">
    	<div data-options="region:'north',split:false,border:false" style="height: 30px;overflow: hidden">
    		<div class="buttonBG" id="sales-buttons-customer"></div>
    	</div>
    	<div data-options="region:'center'" data-options="border:false">
    		<div class="easyui-panel" data-options="fit:true" id="sales-panel-customer"></div>
    	</div>
    </div>
    <input type="hidden" id="sales-customerids-allotPSNCustomer"/>
	<div id="sales-dialog-allotPSNCustomer"></div>
    <script type="text/javascript">
    	var customerlist_title = tabsWindowTitle('/basefiles/showCustomerSimplifyListPage.do');
    	var customer_AjaxConn = function (Data, Action) {
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
    	
    	var customer_lenArr = $("#sales-leaveLen-customer").val().split(',');
    	var customer_url = "basefiles/showCustomerSimplifyAddPage.do?area="+$("#customer-area").val()+"&sort="+$("#customer-sort").val();
    	var customer_type = '${type}';
    	var customer_title = "客户档案【新增】";
    	if(customer_type == "view"){
    		customer_url = "basefiles/showCustomerSimplifyViewPage.do?id="+encodeURIComponent($("#sales-backid-customer").val());
    		customer_title = "客户档案【查看】";
    	}
    	if(customer_type == "edit"){
    		customer_url = "basefiles/showCustomerSimplifyEditPage.do?id="+encodeURIComponent($("#sales-backid-customer").val());
    		customer_title = "客户档案【修改】";
    	}
    	if(customer_type == "copy"){
    		customer_url = "basefiles/showCustomerSimplifyCopyPage.do?id="+encodeURIComponent($("#sales-backid-customer").val());
    		customer_title = "客户档案【复制】";
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
				    		var data=customer_ajaxContent({id:(id+value)},url);
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
				$("#customerShortcut-input-overgracedate").val("");
				changeValue("");
				$("#customerShortcut-input-overgracedate").attr("disabled","disabled");
			}
			else{
				$("#customerShortcut-input-overgracedate").removeAttr("disabled");
			}
		}
		
		function changeValue(val){
			$("#customer-hd-overgracedate").val(val);
		}
    	//页面刷新
	   	function panelRefresh(url,title,type){
	   		$("#sales-panel-customer").panel({
				href:url,
				title:title,
			    cache:false,
			    maximized:true,
			    border:false,
			    loadingMessage:'数据加载中...'
			});
			$("#customer-opera").attr("value",type);
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
    		$("#sales-panel-customer").panel({
			    href:customer_url,
				title:customer_title,
			    cache:false,
			    maximized:true,
			    border:false,
			    close:true,
			    loadingMessage:'数据加载中...'
			});
			$("#customer-opera").attr("value","${type}");
    		//按钮
			$("#sales-buttons-customer").buttonWidget({
				initButton:[
					{},
					<security:authorize url="/basefiles/customerSimplifyAdd.do">
						{
							type: 'button-add',
							handler: function(){
								panelRefresh('basefiles/showCustomerSimplifyAddPage.do','客户档案【新增】','add');
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/customerSimplifyEdit.do">
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
								panelRefresh("basefiles/showCustomerSimplifyEditPage.do?id="+ encodeURIComponent(id),'客户档案【修改】','edit');
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/customerSimplifySave.do">
						{
							type: "button-save",
							handler: function(){
								if(!$("#customersimplify-form-add").form('validate')){
				     				return false;
				     			}
								$.messager.confirm("提醒","是否保存客户?",function(r){
									if(r){
										var type = $("#customer-opera").val();
										if(type != "edit"){
											$("#sales-state-customerAddPage").val("2");
										}
										customerSort_realSave_form_submit();
										$("#customersimplify-form-add").submit();
									}
								});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/customerSimplifySaveopen.do">
						{
							type: "button-saveopen",
							handler: function(){
								if(!$("#customersimplify-form-add").form('validate')){
				     				return false;
				     			}
								$.messager.confirm("提醒","是否保存并启用客户?",function(r){
									if(r){
										$("#sales-state-customerAddPage").val("1");
										customerSort_realSave_form_submit();
										$("#customersimplify-form-add").submit();
									}
								});
							}
						},
					</security:authorize>
					/*<security:authorize url="/basefiles/customerSimplifyGiveUp.do">
						{
							type:'button-giveup',
							handler:function(){
								var type = $("#sales-buttons-customer").buttonWidget("getOperType");
	    						if(type == "add"){
	    							var currTitle = top.$('#tt').tabs('getSelected').panel('options').title;
	    							top.closeTab(currTitle);
	    						}
	    						else if(type == "edit"){
		    						var id = $("#sales-id-customerAddPage").val();
		    						if(id == ""){
		    							return false;
		    						}
	    							$("#sales-panel-customer").panel('refresh', 'basefiles/customerViewPage.do?id='+ id);
	    						}
							}
						},
					</security:authorize>*/
					<security:authorize url="/basefiles/customerSimplifyDelete.do">
						{
							type: 'button-delete',
							handler: function(){
								var id = $("#sales-id-customerAddPage").val();
								if(id == ""){
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
								  				if(json.delFlag){
								  					$.messager.alert("提醒","该信息已被其他信息引用，无法删除！");
								  					return false;
								  				}
								  				if(json.flag==true){
								  		        	$.messager.alert("提醒","删除成功");
													if (top.$('#tt').tabs('exists',customerlist_title)){
							   							var queryJSON = tabsWindow(customerlist_title).$("#sales-queryForm-customerListPage").serializeJSON();
									    				tabsWindow(customerlist_title).$("#sales-datagrid-customerListPage").datagrid("load",queryJSON);
									    			}
									    			var data = $("#sales-buttons-customer").buttonWidget("removeData", id);
									    			if(null != data){
									    				panelRefresh('basefiles/showCustomerSimplifyViewPage.do?id='+encodeURIComponent(data.id),'客户档案【详情】','view');
									    			}
									    			else{
									    				top.closeTab(customerlist_title);
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
					<security:authorize url="/basefiles/customerSimplifyCopy.do">
						{
							type: "button-copy",
							handler: function(){
								var id = $("#sales-id-customerAddPage").val();
								if(id == ""){
									return false;
								}
								$("#sales-pagetype-customer").val("copy");
								panelRefresh("basefiles/showCustomerSimplifyCopyPage.do?id="+ encodeURIComponent(id),'客户档案【复制】','copy');
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/customerSimplifyOpen.do">
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
								  		        	if (top.$('#tt').tabs('exists',customerlist_title)){
							   							var queryJSON = tabsWindow(customerlist_title).$("#sales-queryForm-customerListPage").serializeJSON();
									    				tabsWindow(customerlist_title).$("#sales-datagrid-customerListPage").datagrid("load",queryJSON);
									    			}
								  		        	panelRefresh('basefiles/showCustomerSimplifyViewPage.do?id='+encodeURIComponent(id),'客户档案【详情】','view');
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
					<security:authorize url="/basefiles/customerSimplifyClose.do">
						{
							type: "button-close",
							handler: function(){
								var id = $("#sales-id-customerAddPage").val();
								if(id == ""){
									return false;
								}
								//禁用该客户档案，将禁用该节点下所有客户档案，是否禁用
								$.messager.confirm("提醒","是否禁用该客户档案？",function(r){
						  			if(r){
						  				loading("禁用中..");
							  			$.ajax({
								  			url:'basefiles/closeCustomer.do',
											data:{id:id},
								  			dataType:'json',
								  			type:'post',
								  			success:function(json){
								  				loaded();
								  		        $.messager.alert("提醒","禁用成功数："+ json.successNum + "<br />禁用失败数："+ json.failureNum + "<br />不可禁用数："+ json.notAllowNum);
								  		        if (top.$('#tt').tabs('exists',customerlist_title)){
						   							var queryJSON = tabsWindow(customerlist_title).$("#sales-queryForm-customerListPage").serializeJSON();
								    				tabsWindow(customerlist_title).$("#sales-datagrid-customerListPage").datagrid("load",queryJSON);
								    			}
							  		        	panelRefresh('basefiles/showCustomerSimplifyViewPage.do?id='+encodeURIComponent(id),'客户档案【详情】','view');
								  			}
							  			});
						  			}
						  		});
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/customerSimplifyBack.do">
						{
							type: 'button-back',
							handler: function(data){
								$("#sales-id-customerAddPage").val(data.id);
								panelRefresh('basefiles/showCustomerSimplifyViewPage.do?id='+encodeURIComponent(data.id),'客户档案【详情】','view');
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/customerSimplifyNext.do">
						{
							type: 'button-next',
							handler: function(data){
								$("#sales-id-customerAddPage").val(data.id);
								panelRefresh('basefiles/showCustomerSimplifyViewPage.do?id='+encodeURIComponent(data.id),'客户档案【详情】','view');
							}
						},
					</security:authorize>
					/*<security:authorize url="/basefiles/customerSimplifyPreviewBtn.do">
						{
							type: "button-preview",
							handler: function(){
								
							}
						},
					</security:authorize>
					<security:authorize url="/basefiles/customerSimplifyPrintBtn.do">
						{
							type: "button-print",
							handler: function(){
								
							}
						},
					</security:authorize>*/
					{}
				],
				buttons:[
					{},
					<security:authorize url="/basefiles/allotPSNCustomerSimplifyBtn.do">
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
					{}
				],
				model: 'base',
				type: 'view',
				tname: 't_base_sales_customer',
				taburl: '/basefiles/showCustomerSimplifyListPage.do',
				datagrid: 'sales-datagrid-customerListPage',
				id: $("#sales-backid-customer").val()
			});
    	});
    	function customerSort_realSave_form_submit(){
    		$("#customersimplify-form-add").form({
			    onSubmit: function(){  
		  		  	loading("提交中..");
		  		},  
		  		success:function(data){
		  		  	loaded();
		  		  	var json = $.parseJSON(data);
		  		    if(json.flag){
   						if (top.$('#tt').tabs('exists',customerlist_title)){
   							//var queryJSON = tabsWindow(customerlist_title).$("#sales-queryForm-customerListPage").serializeJSON();
		    				tabsWindow(customerlist_title).$("#sales-datagrid-customerListPage").datagrid("reload");
		    			}
		    			if("${type}" == "add"){
		    				$("#sales-buttons-customer").buttonWidget("addNewDataId",$("#sales-id-customerAddPage").val());
		    			}
						panelRefresh('basefiles/showCustomerSimplifyViewPage.do?id='+encodeURIComponent($("#sales-id-customerAddPage").val()),'客户档案【详情】','view');
						$.messager.alert("提醒","保存成功!");
					}else{
						$.messager.alert("提醒","保存失败!");
					}
		  		}
		  	});
    	}
    	
    </script>
  </body>
</html>
