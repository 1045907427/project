<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>收款单操作页面</title>
    <%@include file="/include.jsp" %>
      <%@include file="/printInclude.jsp" %>
  </head>
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<div data-options="region:'north',border:false">
    		<div class="buttonBG" id="account-buttons-collectionOrderPage" style="height:26px;"></div>
    	</div>
    	<div data-options="region:'center',border:false">
    		<div id="account-panel-collectionOrderPage"></div>
    	</div>
    </div>
    <div id="account-panel-relation-upper"></div>
    <div id="account-panel-sourceQueryPage"></div>
    <div id="account-dialog-writeoff"></div>
    <div id="workflow-addidea-dialog-page"></div>
    <input type="hidden" id="account-hidden-billid"/>
    <script type="text/javascript">
    	var page_url = "account/receivable/collectionOrderAddPage.do";
    	var page_type = '${type}';
    	if(page_type == "view" || page_type=="handle"){
    		page_url = "account/receivable/collectionOrderViewPage.do?id=${id}";
    	}else if(page_type == "edit"){
    		page_url = "account/receivable/collectionOrderEditPage.do?id=${id}";
    	}
    	$(function(){
    		$("#account-panel-collectionOrderPage").panel({
				href:page_url,
			    cache:false,
			    maximized:true,
			    border:false
			});
    		//按钮
			$("#account-buttons-collectionOrderPage").buttonWidget({
				initButton:[
					{},
					<security:authorize url="/account/receivable/collectionOrderAddPage.do">
						{
							type: 'button-add',
							handler: function(){
								$("#account-panel-collectionOrderPage").panel({
									href:'account/receivable/collectionOrderAddPage.do',
									title:'',
								    cache:false,
								    maximized:true,
								    border:false
								});
							}
						},
					</security:authorize>
					<security:authorize url="/account/receivable/collectionOrderEditPage.do">
						{
							type: 'button-edit',
							handler: function(){
								var id = $("#account-hidden-billid").val();
								if(id!=null && id !=""){
									$("#account-panel-collectionOrderPage").panel({
										href:'account/receivable/collectionOrderEditPage.do?id='+id,
										title:'',
									    cache:false,
									    maximized:true,
									    border:false
									});
								}
							}
						},
					</security:authorize>
					<security:authorize url="/account/receivable/addCollectionOrderHold.do">
						{
							type: 'button-hold',
							handler: function(){
								$.messager.confirm("提醒","确定暂存该收款单信息？",function(r){
									if(r){
										var type = $("#account-buttons-collectionOrderPage").buttonWidget("getOperType");
						 				if(type=="add"){
						 					//暂存
						 					$("#account-form-collectionOrderAdd").attr("action", "account/receivable/addCollectionOrderHold.do");
						 					$("#account-form-collectionOrderAdd").submit();
						 				}else if(type=="edit"){
						 					//暂存
						 					$("#account-form-collectionOrderAdd").attr("action", "account/receivable/editCollectionOrderHold.do");
						 					$("#account-form-collectionOrderAdd").submit();
						 				}
					 				}
								});
							}
						},
					</security:authorize>
					<security:authorize url="/account/receivable/addCollectionOrderSave.do">
						{
							type: 'button-save',
							handler: function(){
								$.messager.confirm("提醒","确定保存该收款单信息？",function(r){
									if(r){
										var type = $("#account-buttons-collectionOrderPage").buttonWidget("getOperType");
						 				if(type=="add"){
						 					//暂存
						 					$("#account-form-collectionOrderAdd").attr("action", "account/receivable/addCollectionOrderSave.do");
						 					$("#account-form-collectionOrderAdd").submit();
						 				}else if(type=="edit"){
						 					$("#account-form-collectionOrderAdd").attr("action", "account/receivable/editCollectionOrderSave.do");
						 					$("#account-form-collectionOrderAdd").submit();
						 				}
									}
								});
							}
						},
					</security:authorize>
					<security:authorize url="/account/receivable/collectionOrderGiveUp.do">
						{
							type:'button-giveup',
							handler:function(){
								var type = $("#account-buttons-collectionOrderPage").buttonWidget("getOperType");
	    						if(type == "add"){
	    							var currTitle = top.$('#tt').tabs('getSelected').panel('options').title;
	    							top.closeTab(currTitle);
	    						}
	    						else if(type == "edit"){
		    						var id = $("#account-hidden-billid").val();
		    						if(id == ""){
		    							return false;
		    						}
		    						$("#account-panel-collectionOrderPage").panel({
										href:'account/receivable/collectionOrderViewPage.do?id='+id,
										title:'',
									    cache:false,
									    maximized:true,
									    border:false
									});
	    						}
							}
						},
					</security:authorize>
					<security:authorize url="/account/receivable/deleteCollectionOrder.do">
						{
							type: 'button-delete',
							handler: function(){
								$.messager.confirm("提醒","是否删除当前收款单？",function(r){
									if(r){
										var id = $("#account-hidden-billid").val();
										if(id!=""){
											loading("提交中..");
											$.ajax({   
									            url :'account/receivable/deleteCollectionOrder.do?id='+id,
									            type:'post',
									            dataType:'json',
									            success:function(json){
									            	loaded();
									            	if(json.flag){
									            		var object = $("#account-buttons-collectionOrderPage").buttonWidget("removeData",id);
									            		$.messager.alert("提醒", "删除成功."+json.msg);
									            		if(null!=object){
										            		$("#account-panel-collectionOrderPage").panel({
																href:'account/receivable/collectionOrderViewPage.do?id='+object.id,
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
									            },
									            error:function(){
									            	$.messager.alert("错误", "删除出错");
									            	loaded();
									            }
									        });
										}
									}
								});
							}
						},
					</security:authorize>
					<security:authorize url="/account/receivable/auditcollectionOrder.do">
						{
							type: 'button-audit',
							handler: function(){
								$.messager.confirm("提醒","是否审核收款单？",function(r){
									if(r){
										var id = $("#account-hidden-billid").val();
										if(id!=""){
											loading("提交中..");
											$.ajax({   
									            url :'account/receivable/auditCollectionOrder.do?id='+id,
									            type:'post',
									            dataType:'json',
									            success:function(json){
									            	loaded();
									            	if(json.flag){
														$.messager.alert("提醒","审核成功.");
														$("#account-panel-collectionOrderPage").panel({
															href:'account/receivable/collectionOrderViewPage.do?id='+id,
															title:'',
														    cache:false,
														    maximized:true,
														    border:false
														});
									            	}else{
									            		$.messager.alert("提醒","审核失败");
									            	}
									            },
									            error:function(){
									            	loaded();
									            	$.messager.alert("错误","审核失败");
									            }
									        });
										}
									}
								});
							}
						},
					</security:authorize>
					<security:authorize url="/account/receivable/oppauditCollectionOrder.do">
						{
				 			type:'button-oppaudit',
				 			handler:function(){
				 				$.messager.confirm("提醒","是否反审收款单？",function(r){
									if(r){
										var id = $("#account-hidden-billid").val();
										if(id!=""){
											$.ajax({   
									            url :'account/receivable/oppauditCollectionOrder.do?id='+id,
									            type:'post',
									            dataType:'json',
									            success:function(json){
									            	if(json.flag){
														$.messager.alert("提醒","反审成功");
														$("#account-panel-collectionOrderPage").panel({
															href:'account/receivable/collectionOrderViewPage.do?id='+id,
															title:'',
														    cache:false,
														    maximized:true,
														    border:false
														});
									            	}else{
									            		$.messager.alert("提醒","反审失败<br/>"+json.msg);
									            	}
									            }
									        });
										}
									}
								});
				 			}
			 			},
					</security:authorize>
		 			<security:authorize url="/account/receivable/collectionOrderWorkflow.do">
						{
							type: 'button-workflow',
							button:[
								{
									type: 'workflow-submit',
									handler: function(){
										$.messager.confirm("提醒","是否提交该收款单信息到工作流?",function(r){
									  		if(r){
									   			var id = $("#account-hidden-billid").val();
									  			if(id == ""){
									    			$.messager.alert("警告","没有需要提交工作流的信息!");
									    			return false;
									   			}
									  			$.ajax({
										   			url:'account/receivable/submitCollectionOrderPageProcess.do',
										   			dataType:'json',
										   			type:'post',
										   			data:'id='+id,
										   			success:function(json){
										    			if(json.flag == true){
											    			$.messager.alert("提醒","提交成功!");
											    			$("#account-panel-collectionOrderPage").panel("refresh");
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
											title:'查看流程',
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
					<security:authorize url="/account/receivable/collectionOrderViewBackPage.do">
						{
							type: 'button-back',
							handler: function(data){
								$("#account-panel-collectionOrderPage").panel({
									href:'account/receivable/collectionOrderViewPage.do?id='+data.id,
									title:'',
								    cache:false,
								    maximized:true,
								    border:false
								});
							}
						},
					</security:authorize>
					<security:authorize url="/account/receivable/collectionOrderViewNextPage.do">
						{
							type: 'button-next',
							handler: function(data){
								$("#account-panel-collectionOrderPage").panel({
									href:'account/receivable/collectionOrderViewPage.do?id='+data.id,
									title:'',
								    cache:false,
								    maximized:true,
								    border:false
								});
							}
						},
					</security:authorize>
					<security:authorize url="/account/receivable/collectionOrderPreviewPage.do">
						{
							type: 'button-preview',
							handler: function(){
								
							}
						},
					</security:authorize>
					<security:authorize url="/account/receivable/collectionOrderPrintPage.do">
						{
							type: 'button-print',
							handler: function(){
							
							}
						},
					</security:authorize>
					{}
				],
				buttons:[
					{},
					<security:authorize url="/account/receivable/showWriteoffCollectionOrderPage.do">
<!--						{-->
<!--							id:'writeoff-button',-->
<!--							name:'核销',-->
<!--							iconCls:'icon-remove',-->
<!--							handler:function(){-->
<!--								$.messager.confirm("提醒","是否核销当前收款单？",function(r){-->
<!--									if(r){-->
<!--										var id = $("#account-hidden-billid").val();-->
<!--										if(id!=""){-->
<!--											$("#account-dialog-writeoff").dialog({-->
<!--												href:"account/receivable/showWriteoffCollectionOrderPage.do?id="+id,-->
<!--												title:"核销",-->
<!--											    fit:true,-->
<!--												modal:true,-->
<!--												cache:false,-->
<!--												maximizable:true,-->
<!--												resizable:true,-->
<!--											    cache: false,  -->
<!--											    modal: true,-->
<!--											    buttons:[{-->
<!--														text:'确定',-->
<!--														handler:function(){-->
<!--															collectionOrderWriteOff();-->
<!--														}-->
<!--													}]-->
<!--											});-->
<!--										}-->
<!--									}-->
<!--								});-->
<!--							}-->
<!--						},-->
					</security:authorize>
					<security:authorize url="/account/receivable/collectionOrderAssignCustomerPage.do">
						{
							id:'iscustomer-button',
							name:'指定客户',
							iconCls:'icon-remove',
							handler:function(){
								$.messager.confirm("提醒","是否给收款单指定客户？",function(r){
									if(r){
										var id = $("#account-hidden-billid").val();
										if(id!=""){
											$('<div id="account-dialog-writeoff-content"></div>').appendTo('#account-dialog-writeoff');
											$("#account-dialog-writeoff-content").dialog({
												href:"account/receivable/collectionOrderAssignCustomerPage.do?id="+id,
												title:"收款单指定客户",
												width:400,
												height:200,
												fit:true,
												modal:true,
												cache:false,
											    modal: true,
											    buttons:[{
														text:'确定',
														handler:function(){
															assignCustomer();
														}
													}],
												onClose:function(){
													var url = "account/receivable/showCollectionOrderViewPage.do?id="+id
													top.updateThisTab(url);
											    	$('#account-dialog-writeoff-content').dialog("destroy");
											    }
											});
										}
									}
								});
							}
						},
					</security:authorize>
					{}
				],
				model: 'bill',
				type: 'view',
				tab:'收款单列表',
				taburl:'/account/receivable/showCollectionOrderListPage.do',
				id:'${id}',
				datagrid:'account-datagrid-collectionOrderPage'
			});
    	});
    </script>

    <%--打印开始 --%>
    <script type="text/javascript">
        $(function () {
            //打印
            AgReportPrint.init({
                id: "listPage-CollectionOrder-dialog-print",
                code: "account_collectionorder",
                url_preview: "print/account/collectionOrderPrintView.do",
                url_print: "print/account/collectionOrderPrint.do",
                btnPreview: "button-preview",
                btnPrint: "button-print",
                getData: function (tableId, printParam) {
                    var id = $("#account-backid-collectionOrderAddPage").val();
                    if (id == "") {
                        $.messager.alert("警告", "找不到要打印的信息!");
                        return false;
                    }
                    var status = $("#account-collectionOrderAddPage-status").val() || "";
                    if (!(status == 1 || status == 2 || theAuthorprintFlag == 1)) {
                        $.messager.alert("提醒", "此收款单不可打印");
                        return false;
                    }
                    printParam.idarrs = id;
                    var printtimes = $("#account-collectionOrderAddPage-printtimes").val() || 0;
                    if (printtimes > 0)
                        printParam.printIds = [id];
                    return true;
                },
                onPrintSuccess: function (option) {
                    updateDataGridPrintimes($("#account-backid-collectionOrderAddPage").val());
                }
            });

        });
    </script>
    <%--打印结束 --%>
  </body>
</html>
