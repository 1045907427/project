<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>收款单列表页面</title>
    <%@include file="/include.jsp" %>   
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<div data-options="region:'north',border:false">
    		<div class="buttonBG" id="account-buttons-collectionOrderPage" style="height:26px;"></div>
    	</div>
    	<div data-options="region:'center'">
    		<table id="account-datagrid-collectionOrderPage" data-options="border:false"></table>
    	</div>
    </div>
    <div id="account-datagrid-toolbar-collectionOrderPage">
    	<form action="" id="account-form-query-collectionOrderPage" method="post">
    		<input id="account-query-customerid" type="hidden" name="customerid" style="width: 180px;" value="${othercustomer }"/>
    		<table class="querytable">
    			<tr>
    				<td>业务日期:</td>
    				<td><input type="text" name="businessdate1" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/> 到 <input type="text" name="businessdate2" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
    				 <td>银行名称:</td>
                    <td>
                        <input id="account-query-bank" type="text" name="bank" style="width:180px;"/>
                    </td>
                    <td>状态:</td>
                    <td><select name="isClose" style="width:165px;"><option></option><option value="2">保存</option><option value="0" selected="selected">未关闭</option><option value="1">已关闭</option></select></td>
                </tr>
    			<tr>
                    <td>审核时间:</td>
                    <td><input type="text" name="auditdate1" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/> 到 <input type="text" name="auditdate2" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
                    <td>编号:</td>
    				<td><input type="text" name="id" style="width: 180px;"/></td>
                    <td rowspan="3" colspan="2" class="tdbutton">
                        <a href="javaScript:void(0);" id="account-queay-collectionOrder" class="button-qr">查询</a>
                        <a href="javaScript:void(0);" id="account-reload-collectionOrder" class="button-qr">重置</a>
                        <span id="account-query-advanced-collectionOrder"></span>
                    </td>
    			</tr>
    		</table>
    	</form>
    </div>
    <div id="account-panel-collectionOrder-addpage"></div>
    <!-- <div id="account-panel-collectionOrder-viewpage"></div> -->
    <div id="account-dialog-writeoff-content"></div>
     <script type="text/javascript">
     	var initQueryJSON = $("#account-form-query-collectionOrderPage").serializeJSON();
     	var footerobject = null;
    	$(function(){
    		//按钮
			$("#account-buttons-collectionOrderPage").buttonWidget({
				initButton:[
					{},
					<security:authorize url="/account/receivable/collectionOrderAddPage.do">
						{
							type: 'button-add',
							handler: function(){
								$('#account-panel-collectionOrder-addpage').dialog({  
								    title: '收款单新增',  
								    width: 650,  
								    height: 300,  
								    collapsible:false,
								    minimizable:false,
								    maximizable:true,
								    resizable:true,
								    closed: true,  
								    cache: false,  
								    href: 'account/receivable/collectionOrderOtherAddPage.do',  
								    modal: true,
								    onLoad:function(){
								    	$("#account-collectionOrder-amount").focus();
								    }
								});
								$('#account-panel-collectionOrder-addpage').dialog("open");
								//top.addTab('account/receivable/showCollectionOrderAddPage.do', "收款单新增");
							}
						},
					</security:authorize>
					<security:authorize url="/account/receivable/collectionOrderEditPage.do">
						{
							type: 'button-edit',
							handler: function(){
								var con = $("#account-datagrid-collectionOrderPage").datagrid('getSelected');
								if(con == null){
									$.messager.alert("提醒","请选择一条记录");
									return false;
								}	
								if(con.status=='2'){
									$('#account-panel-collectionOrder-addpage').dialog({  
									    title: '收款单修改',  
									    width: 650,  
									    height: 300,  
									    collapsible:false,
									    minimizable:false,
									    maximizable:true,
									    resizable:true,
									    closed: true,  
									    cache: false,  
									    href: 'account/receivable/collectionOrderEditPage.do?id='+con.id,  
									    modal: true,
									    onLoad:function(){
									    	$("#account-collectionOrder-amount").focus();
									    }
									});
									$('#account-panel-collectionOrder-addpage').dialog("open");
								}else{
									$('#account-panel-collectionOrder-addpage').dialog({  
									    title: '收款单修改',  
									    width: 650,  
									    height: 300,  
									    collapsible:false,
									    minimizable:false,
									    maximizable:true,
									    resizable:true,
									    closed: true,  
									    cache: false,  
									    href: 'account/receivable/collectionOrderViewPage.do?id='+con.id,  
									    modal: true,
									    onLoad:function(){
									    	$("#account-collectionOrder-amount").focus();
									    }
									});
									$('#account-panel-collectionOrder-addpage').dialog("open");
									//$.messager.alert("提醒","该收款单不能修改");
								}
								
								//top.addTab('account/receivable/showCollectionOrderEditPage.do?id='+ con.id, "收款单修改");
							}
						},
					</security:authorize>
					<security:authorize url="/account/receivable/deleteCollectionOrder.do">
						{
							type: 'button-delete',
							handler: function(){
								$.messager.confirm("提醒","是否删除当前收款单？",function(r){
									if(r){
										var rows = $("#account-datagrid-collectionOrderPage").datagrid("getChecked");
										var ids = "";
										for(var i=0;i<rows.length;i++){
											if(ids==""){
												ids = rows[i].id;
											}else{
												ids += ","+ rows[i].id;
											}
										}
										if(ids!=""){
											loading("提交中..");
											$.ajax({   
									            url :'account/receivable/deleteMutCollectionOrder.do?ids='+ids,
									            type:'post',
									            dataType:'json',
									            success:function(json){
									            	loaded();
									            	if(json.flag){
									            		$.messager.alert("提醒", "操作成功</br>"+json.succssids+"</br>"+json.errorids);
									            		$("#account-datagrid-collectionOrderPage").datagrid("reload");
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
								$.messager.confirm("提醒","是否审核选中的收款单？",function(r){
									if(r){
										var rows = $("#account-datagrid-collectionOrderPage").datagrid("getChecked");
										var ids = "";
										for(var i=0;i<rows.length;i++){
											if(ids==""){
												ids = rows[i].id;
											}else{
												ids += ","+ rows[i].id;
											}
										}
										loading("审核中..");
										$.ajax({   
									            url :'account/receivable/auditMutCollectionOrder.do?ids='+ids,
									            type:'post',
									            dataType:'json',
									            success:function(json){
									            	loaded();
									            	if(json.flag){
									            		$.messager.alert("提醒", "操作成功</br>"+json.succssids+"</br>"+json.errorids);
									            		$("#account-datagrid-collectionOrderPage").datagrid("reload");
									            	}else{
									            		$.messager.alert("提醒", "审核失败");
									            	}
									            },
									            error:function(){
									            	$.messager.alert("错误", "审核出错");
									            	loaded();
									            }
									        });
									}
								});
							}
						},
					</security:authorize>
					<security:authorize url="/account/receivable/oppauditCollectionOrder.do">
						{
				 			type:'button-oppaudit',
				 			handler:function(){
                                var rows = $("#account-datagrid-collectionOrderPage").datagrid("getChecked");
                                if(rows.length == 0){
                                	$.messager.alert("提醒","请选择记录");
									return false;
                                }
                                var ids = "",unids = "";
                                for(var i=0;i<rows.length;i++){
                                    var flag = isDoneOppauditBillCaseAccounting(rows[i].businessdate);
                                    if(!flag){
                                        if(unids==""){
											unids = rows[i].id;
										}else{
											unids += ","+rows[i].id;
										}
                                    }else{
                                    	if(ids==""){
											ids = rows[i].id;
										}else{
											ids += ","+ rows[i].id;
										}
                                    }
                                }
                                if(unids != ""){
                                    $.messager.alert("提醒","编号："+unids+"的业务日期不在会计区间内或未设置会计区间,不允许反审!");
                                }
                                if(ids != ""){
	                                $.messager.confirm("提醒","是否反审收款单？",function(r){
										if(r){
											loading("反审中..");
											$.ajax({
									            url :'account/receivable/oppauditMutCollectionOrder.do?ids='+ids,
									            type:'post',
									            dataType:'json',
									            success:function(json){
									            	loaded();
									            	if(json.flag){
									            		$.messager.alert("提醒", "操作成功</br>"+json.succssids+"</br>"+json.errorids);
									            		$("#account-datagrid-collectionOrderPage").datagrid("reload");
									            		var unidsArr = unids.split(",");
									            		for(var i=0;i<unidsArr.length;i++){
									            			$("#account-datagrid-collectionOrderPage").datagrid("selectRecord",unidsArr[i]);
									            		}
									            	}else{
									            		$.messager.alert("提醒", json.errorids);
									            	}
									            },
									            error:function(){
									            	$.messager.alert("错误", "反审出错");
									            	loaded();
									            }
									        });
										}
									});
                                }
				 			}
			 			},
					</security:authorize>
					<security:authorize url="/account/receivable/showCollectionOrderViewPage.do">
						{
							type: 'button-view',
							handler: function(){
								var con = $("#account-datagrid-collectionOrderPage").datagrid('getSelected');
								if(con == null){
									$.messager.alert("提醒","请选择一条记录");
									return false;
								}	
								$('#account-panel-collectionOrder-addpage').dialog({  
								    title: '收款单查看',  
								    width: 650,  
								    height: 300,  
								    collapsible:false,
								    minimizable:false,
								    maximizable:true,
								    resizable:true,
								    closed: true,  
								    cache: false,  
								    href: 'account/receivable/collectionOrderViewPage.do?id='+con.id,  
								    modal: true
								});
								$('#account-panel-collectionOrder-addpage').dialog("open");
							}
						},
					</security:authorize>
					<%--<security:authorize url="/account/receivable/collectionOrderImport.do">--%>
						<%--{--%>
							<%--type: 'button-import',--%>
							<%--attr: {--%>

							<%--}--%>
						<%--},--%>
					<%--</security:authorize>--%>
					<%--<security:authorize url="/account/receivable/collectionOrderExport.do">--%>
						<%--{--%>
							<%--type: 'button-export',--%>
							<%--attr: {--%>
							<%----%>
							<%--}--%>
						<%--},--%>
					<%--</security:authorize>--%>
                    {
                        type: 'button-commonquery',
                        attr:{
                            //查询针对的表
                            name:'t_account_collection_order',
                            //查询针对的表格id
                            datagrid:'account-datagrid-collectionOrderPage',
                            plain:true
                        }
                    },
					{}
				],
				buttons:[
					<security:authorize url="/account/receivable/collectionOrderAssignCustomerPage.do">
						{
							id:'iscustomer-button',
							name:'指定客户',
							iconCls:'button-asscustomer',
							handler:function(){
								var con = $("#account-datagrid-collectionOrderPage").datagrid('getSelected');
								if(con == null){
									$.messager.alert("提醒","请选择一条记录");
									return false;
								}else if(con.customerid!="${othercustomer}"){
									$.messager.alert("提醒","该收款单不能指定客户。");
									return false;
								}else if(con.status!="3"){
									$.messager.alert("提醒","该收款单不是处于审核通过状态。不能指定客户。");
									return false;
								}
								$.messager.confirm("提醒","是否给收款单:"+con.id+"指定客户？",function(r){
									if(r){
										var id = con.id;
										if(id!=""){
											$('<div id="account-dialog-writeoff-content"></div>').appendTo('#account-dialog-writeoff');
											$("#account-dialog-writeoff-content").dialog({
												href:"account/receivable/collectionOrderAssignCustomerPage.do?id="+id,
												title:"收款单:"+con.id+"指定客户",
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
											    	$("#account-datagrid-collectionOrderPage").datagrid("reload");
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
				type: 'list',
				tname: 't_account_collection_order'
			});
			var tableColumnListJson = $("#account-datagrid-collectionOrderPage").createGridColumnLoad({
				name :'t_account_collection_order',
				frozenCol : [[
			    			]],
				commonCol : [[
							  {field:'ck',checkbox:true},
							  {field:'id',title:'编号',width:110,sortable:true},
							  {field:'businessdate',title:'业务日期',width:80,sortable:true},
							  {field:'customerid',title:'客户编码',width:60,sortable:true},
							  {field:'customername',title:'客户名称',width:150,isShow:true},
							  {field:'handlerid',title:'对方经手人',width:80,sortable:true,hidden:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return rowData.handlername;
					        	}
							  },
							  {field:'collectionuser',title:'收款人',width:60,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return rowData.collectionusername;
					        	}
							  },
							  {field:'amount',title:'收款金额',resizable:true,align:'right',sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'writeoffamount',title:'已核销金额',resizable:true,align:'right',sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'remainderamount',title:'未核销金额',resizable:true,align:'right',sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'bank',title:'银行名称',width:100,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
							  		if(value!=null &&value!=""){
					        			return rowData.bankname;
					        		}
					        	}
							  },
							  {field:'bankdeptid',title:'银行部门',width:100,hidden:true,
							  	formatter:function(value,rowData,rowIndex){
							  		if(value!=null &&value!=""){
					        			return rowData.bankdeptname;
					        		}
					        	}
							  },
							  {field:'addusername',title:'制单人',width:60,sortable:true},
							  {field:'addtime',title:'制单时间',width:100,sortable:true},
							  {field:'auditusername',title:'审核人',width:60,sortable:true},
							  {field:'audittime',title:'审核时间',width:100,sortable:true},
							  {field:'stopusername',title:'中止人',width:60,hidden:true,hidden:true},
							  {field:'stoptime',title:'中止时间',width:80,hidden:true,sortable:true,hidden:true},
							  {field:'status',title:'状态',width:60,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return getSysCodeName("status",value);
					        	}
							  },
							  {field:'remark',title:'备注',width:80,sortable:true}
				             ]]
			});
			$("#account-datagrid-collectionOrderPage").datagrid({ 
		 		authority:tableColumnListJson,
		 		frozenColumns: tableColumnListJson.frozen,
				columns:tableColumnListJson.common,
		 		fit:true,
		 		method:'post',
		 		rownumbers:true,
		 		pagination:true,
		 		idField:'id',
		 		sortName:'id',
		 		sortOrder:'desc',
		 		singleSelect:false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
		 		showFooter: true,
				url: 'account/receivable/showCollectionOrderList.do',
				queryParams:initQueryJSON,
				toolbar:'#account-datagrid-toolbar-collectionOrderPage',
				onDblClickRow:function(rowIndex, rowData){
					if(rowData.status=='2'){
						$('#account-panel-collectionOrder-addpage').dialog({  
						    title: '收款单修改',  
						    width: 650,  
						    height: 350,  
						    collapsible:false,
						    minimizable:false,
						    maximizable:true,
						    resizable:true,
						    closed: true,  
						    cache: false,  
						    href: 'account/receivable/collectionOrderEditPage.do?id='+rowData.id,  
						    modal: true,
						    onLoad:function(){
						    	$("#account-collectionOrder-amount").focus();
						    }
						});
						$('#account-panel-collectionOrder-addpage').dialog("open");
					}else{
						$('#account-panel-collectionOrder-addpage').dialog({  
						    title: '收款单查看',  
						    width: 650,  
						    height: 350,  
						    collapsible:false,
						    minimizable:false,
						    maximizable:true,
						    resizable:true,
						    closed: true,  
						    cache: false,  
						    href: 'account/receivable/collectionOrderViewPage.do?id='+rowData.id,  
						    modal: true
						});
					$('#account-panel-collectionOrder-addpage').dialog("open");
					}
				},
				onCheckAll:function(){
					countTotalAmount();
				},
				onUncheckAll:function(){
					countTotalAmount();
				},
				onCheck:function(){
					countTotalAmount();
				},
				onUncheck:function(){
					countTotalAmount();
				},
				onLoadSuccess: function(){
					var footerrows = $("#account-datagrid-collectionOrderPage").datagrid('getFooterRows');
					if(null!=footerrows && footerrows.length>0){
						footerobject = footerrows[0];
					}
				}
			}).datagrid("columnMoving");
			
			$("#account-query-bank").widget({
				name:'t_account_collection_order',
				col:'bank',
    			singleSelect:false,
    			width:180,
    			view:true
			});
			
			//通用查询组建调用
//			$("#account-query-advanced-collectionOrder").advancedQuery({
//				//查询针对的表
//		 		name:'t_account_collection_order',
//		 		//查询针对的表格id
//		 		datagrid:'account-datagrid-collectionOrderPage',
//		 		plain:true
//			});
			
			controlQueryAndResetByKey("account-queay-collectionOrder","account-reload-collectionOrder");
			
			//查询
			$("#account-queay-collectionOrder").click(function(){
				//把form表单的name序列化成JSON对象
	       		var queryJSON = $("#account-form-query-collectionOrderPage").serializeJSON();
	       		$("#account-datagrid-collectionOrderPage").datagrid("load",queryJSON);
			});
			//重置
			$("#account-reload-collectionOrder").click(function(){
				$("#account-query-bank").widget("clear");
				$("#account-form-query-collectionOrderPage")[0].reset();
				var queryJSON = $("#account-form-query-collectionOrderPage").serializeJSON();
				$("#account-datagrid-collectionOrderPage").datagrid("load",queryJSON);
	       		//$("#account-datagrid-collectionOrderPage").datagrid('loadData',{total:0,rows:[],footer:[]});
			});
    	});
    	function countTotalAmount(){
    		var rows =  $("#account-datagrid-collectionOrderPage").datagrid('getChecked');
    		var amount = 0;
    		var writeoffamount = 0;
    		var remainderamount = 0;
    		for(var i=0;i<rows.length;i++){
    			amount = Number(amount)+Number(rows[i].amount == undefined ? 0 : rows[i].amount);
    			writeoffamount = Number(writeoffamount)+Number(rows[i].writeoffamount == undefined ? 0 : rows[i].writeoffamount);
    			remainderamount = Number(remainderamount)+Number(rows[i].remainderamount == undefined ? 0 : rows[i].remainderamount);
    		}
    		if(null!=rows && rows.length>0){
    			var footerrows = [{id:'选中金额',amount:amount,writeoffamount:writeoffamount,remainderamount:remainderamount},footerobject];
    			$("#account-datagrid-collectionOrderPage").datagrid("reloadFooter",footerrows);
    		}else{
    			if(null!=footerobject){
    				$("#account-datagrid-collectionOrderPage").datagrid("reloadFooter",[footerobject]);
    			}
    		}
    	}
    </script>
  </body>
</html>
