<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>付款单操作页面</title>
    <%@include file="/include.jsp" %>   
  </head>
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<div data-options="region:'north',border:false">
    		<div class="buttonBG" id="account-buttons-payorderPage" style="height:26px;"></div>
    	</div>
    	<div data-options="region:'center',border:false">
    		<div id="account-panel-payorderPage"></div>
    	</div>
    </div>
    <div id="account-panel-relation-upper"></div>
    <div id="account-panel-sourceQueryPage"></div>
    <div id="account-dialog-writeoff"></div>
    <div id="workflow-addidea-dialog-page"></div>
    <input type="hidden" id="account-hidden-billid"/>
    <script type="text/javascript">
    	var page_url = "account/payable/payorderAddPage.do";
    	var page_type = '${type}';
    	if(page_type == "view" || page_type=="handle"){
    		page_url = "account/payable/payorderViewPage.do?id=${id}";
    	}else if(page_type == "edit"){
    		page_url = "account/payable/payorderEditPage.do?id=${id}";
    	}
    	$(function(){
    		$("#account-panel-payorderPage").panel({
				href:page_url,
			    cache:false,
			    maximized:true,
			    border:false
			});
    		//按钮
			$("#account-buttons-payorderPage").buttonWidget({
				initButton:[
					{},
					<security:authorize url="/account/payable/payorderAddPage.do">
						{
							type: 'button-add',
							handler: function(){
								$("#account-panel-payorderPage").panel({
									href:'account/payable/payorderAddPage.do',
									title:'',
								    cache:false,
								    maximized:true,
								    border:false
								});
							}
						},
					</security:authorize>
					<security:authorize url="/account/payable/payorderEditPage.do">
						{
							type: 'button-edit',
							handler: function(){
								var id = $("#account-hidden-billid").val();
								if(id!=null && id !=""){
									$("#account-panel-payorderPage").panel({
										href:'account/payable/payorderEditPage.do?id='+id,
										title:'',
									    cache:false,
									    maximized:true,
									    border:false
									});
								}
							}
						},
					</security:authorize>
					<security:authorize url="/account/payable/addpayorderHold.do">
						{
							type: 'button-hold',
							handler: function(){
								$.messager.confirm("提醒","确定暂存该付款单信息？",function(r){
									if(r){
										var type = $("#account-buttons-payorderPage").buttonWidget("getOperType");
						 				if(type=="add"){
						 					//暂存
						 					$("#account-form-payorderAdd").attr("action", "account/payable/addPayorder.do?type=hold");
						 					$("#account-form-payorderAdd").submit();
						 				}else if(type=="edit"){
						 					//暂存
						 					$("#account-form-payorderAdd").attr("action", "account/payable/editPayorder.do?type=hold");
						 					$("#account-form-payorderAdd").submit();
						 				}
					 				}
								});
							}
						},
					</security:authorize>
					<security:authorize url="/account/payable/addpayorderSave.do">
						{
							type: 'button-save',
							handler: function(){
								$.messager.confirm("提醒","确定保存该付款单信息？",function(r){
									if(r){
										var type = $("#account-buttons-payorderPage").buttonWidget("getOperType");
						 				if(type=="add"){
						 					$("#account-form-payorderAdd").attr("action", "account/payable/addPayorder.do?type=save");
						 					$("#account-form-payorderAdd").submit();
						 				}else if(type=="edit"){
						 					$("#account-form-payorderAdd").attr("action", "account/payable/editPayorder.do?type=save");
						 					$("#account-form-payorderAdd").submit();
						 				}
									}
								});
							}
						},
					</security:authorize>
					<security:authorize url="/account/payable/payorderGiveUp.do">
						{
							type:'button-giveup',
							handler:function(){
								var type = $("#account-buttons-payorderPage").buttonWidget("getOperType");
	    						if(type == "add"){
	    							var currTitle = top.$('#tt').tabs('getSelected').panel('options').title;
	    							top.closeTab(currTitle);
	    						}
	    						else if(type == "edit"){
		    						var id = $("#account-hidden-billid").val();
		    						if(id == ""){
		    							return false;
		    						}
		    						$("#account-panel-payorderPage").panel({
										href:'account/payable/payorderViewPage.do?id='+id,
										title:'',
									    cache:false,
									    maximized:true,
									    border:false
									});
	    						}
							}
						},
					</security:authorize>
					<security:authorize url="/account/payable/deletePayorder.do">
						{
							type: 'button-delete',
							handler: function(){
								$.messager.confirm("提醒","是否删除当前付款单？",function(r){
									if(r){
										var id = $("#account-hidden-billid").val();
										if(id!=""){
											loaded("删除中..");
											$.ajax({   
									            url :'account/payable/deletePayorder.do?id='+id,
									            type:'post',
									            dataType:'json',
									            success:function(json){
									            	loaded();
									            	if(json.flag){
									            		var object = $("#account-buttons-payorderPage").buttonWidget("removeData",id);
									            		$.messager.alert("提醒", "删除成功");
									            		if(null!=object){
										            		$("#account-panel-payorderPage").panel({
																href:'account/payable/payorderViewPage.do?id='+object.id,
																title:'',
															    cache:false,
															    maximized:true,
															    border:false
															});
														}else{
															parent.closeNowTab();
														}
									            	}else{
									            		$.messager.alert("提醒", "删除失败");
									            	}
									            }
									        });
										}
									}
								});
							}
						},
					</security:authorize>
					<security:authorize url="/account/payable/auditPayorder.do">
						{
							type: 'button-audit',
							handler: function(){
								$.messager.confirm("提醒","是否审核付款单？",function(r){
									if(r){
										var id = $("#account-hidden-billid").val();
										if(id!=""){
											loading("审核中..");
											$.ajax({   
									            url :'account/payable/auditPayorder.do?id='+id,
									            type:'post',
									            dataType:'json',
									            success:function(json){
									            	loaded();
									            	if(json.flag){
														$.messager.alert("提醒","审核成功");
														$("#account-panel-payorderPage").panel({
															href:'account/payable/payorderViewPage.do?id='+id,
															title:'付款单查看',
														    cache:false,
														    maximized:true,
														    border:false
														});
									            	}else{
									            		$.messager.alert("提醒","审核失败");
									            	}
									            },
									            error:function(){
									            	$.messager.alert("错误","审核失败");
									            }
									        });
										}
									}
								});
							}
						},
					</security:authorize>
					<security:authorize url="/account/payable/oppauditPayorder.do">
						{
				 			type:'button-oppaudit',
				 			handler:function(){
				 				$.messager.confirm("提醒","是否反审付款单？",function(r){
									if(r){
										var id = $("#account-hidden-billid").val();
										if(id!=""){
											loading("反审中..");
											$.ajax({
									            url :'account/payable/oppauditPayorder.do?id='+id,
									            type:'post',
									            dataType:'json',
									            success:function(json){
									            	loaded();
									            	if(json.flag){
														$.messager.alert("提醒","反审成功");
														$("#account-panel-payorderPage").panel({
															href:'account/payable/payorderViewPage.do?id='+id,
															title:'付款单查看',
														    cache:false,
														    maximized:true,
														    border:false
														});
									            	}else{
									            		$.messager.alert("提醒","反审失败");
									            	}
									            },
									            error:function(){
									            	$.messager.alert("错误","反审失败");
									            }
									        });
										}
									}
								});
				 			}
			 			},
					</security:authorize>
		 			<security:authorize url="/account/payable/payorderWorkflow.do">
						{
							type: 'button-workflow',
							button:[
								{
									type: 'workflow-submit',
									handler: function(){
										$.messager.confirm("提醒","是否提交该付款单信息到工作流?",function(r){
									  		if(r){
									   			var id = $("#account-hidden-billid").val();
									  			if(id == ""){
									    			$.messager.alert("警告","没有需要提交工作流的信息!");
									    			return false;
									   			}
									   			loading("提交中..");
									  			$.ajax({
										   			url:'account/payable/submitPayorderPageProcess.do',
										   			dataType:'json',
										   			type:'post',
										   			data:'id='+id,
										   			success:function(json){
										   				loaded();
										    			if(json.flag == true){
											    			$.messager.alert("提醒","提交成功!");
											    			$("#account-panel-payorderPage").panel("refresh");
										    			}
										    			else{
										    				$.messager.alert("提醒","提交失败!");
										    			}
									   				}
									  			});
									  		}
									  	});
									}
								},
								{
									type: 'workflow-addidea',
									handler: function(){
										var order_type = '${type}';
										if(order_type == "handle"){
											$("#workflow-addidea-dialog-page").dialog({
												title:'填写处理意见',
												width:450,
												height:300,
												closed:false,
												cache:false,
											    modal: true,
												href:'workflow/commentAddPage.do?id='+ handleWork_taskId
											});
										}
									}
								},
								{
									type: 'workflow-viewflow',
									handler: function(){
										var id = $("#account-hidden-billid").val();
										if(id == ""){
											return false;
										}
										$("#workflow-addidea-dialog-page").dialog({
											title:'查看流程',
											width:600,
											height:450,
											closed:false,
											cache:false,
										    modal: true,
										    maximizable:true,
										    resizable:true,
											href:'workflow/commentListPage.do?id='+ id
										});
									}
								},
								{
									type: 'workflow-viewflow-pic',
									handler: function(){
										var id = $("#account-hidden-billid").val();
										if(id == ""){
											return false;
										}
										$("#workflow-addidea-dialog-page").dialog({
											title:'查看流程图',
											width:600,
											height:450,
											closed:false,
											cache:false,
										    modal: true,
										    maximizable:true,
										    resizable:true,
											href:'workflow/showDiagramPage.do?id='+ id
										});
									}
								},
								{
									type: 'workflow-recover',
									handler: function(){
									
									}
								}
							]
						},
					</security:authorize>
					<security:authorize url="/account/payable/payorderBackPage.do">
						{
							type: 'button-back',
							handler: function(data){
								$("#account-panel-payorderPage").panel({
									href:'account/payable/payorderViewPage.do?id='+data.id,
									title:'',
								    cache:false,
								    maximized:true,
								    border:false
								});
							}
						},
					</security:authorize>
					<security:authorize url="/account/payable/payorderNextPage.do">
						{
							type: 'button-next',
							handler: function(data){
								$("#account-panel-payorderPage").panel({
									href:'account/payable/payorderViewPage.do?id='+data.id,
									title:'',
								    cache:false,
								    maximized:true,
								    border:false
								});
							}
						},
					</security:authorize>
					<security:authorize url="/account/payable/payorderPreviewBtn.do">
						{
							type: 'button-preview',
							handler: function(){
								
							}
						},
					</security:authorize>
					<security:authorize url="/account/payable/payorderPrintBtn.do">
						{
							type: 'button-print',
							handler: function(){
							
							}
						},
					</security:authorize>
					{}
				],
				model: 'bill',
				type: 'view',
				tab:'付款单列表',
				taburl:'/account/payable/showPayorderListPage.do',
				id:'${id}',
				datagrid:'account-datagrid-payorderPage'
			});
    	});
    </script>
  </body>
</html>
