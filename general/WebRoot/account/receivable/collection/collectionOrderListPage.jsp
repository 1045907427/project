<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>收款单列表页面</title>
    <%@include file="/include.jsp" %>
	  <%@include file="/printInclude.jsp" %>
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
    		<table class="querytable">
    			<tr>
					<td>单据编号:</td>
    				<td><input type="text" name="id" style="width: 225px;"/></td>
    				<td>客&nbsp;&nbsp;户:</td>
    				<td><input id="account-query-customerid" type="text" name="customerid" style="width: 180px;"/></td>
                    <td>销售部门:</td>
                    <td><input id="account-query-salesdeptid" type="text" name="salesdeptid" style="width: 150px;"/></td>
    			</tr>
    			<tr>
 					<td>业务日期:</td>
    				<td><input type="text" name="businessdate1" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${today }"/> 到 <input type="text" name="businessdate2" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
    				<td>状&nbsp;&nbsp;态:</td>
					<td><select name="status" style="width:180px;"><option></option><option value="2" selected="selected">保存</option><option value="3">审核通过</option><option value="4">关闭</option></select></td>
					<td>制&nbsp;单&nbsp;人:</td>
					<td><input id="account-query-adduserid" type="text" name="adduserid" style="width: 150px;"/></td>
				</tr>
    			<tr>
                    <td>审核时间:</td>
                    <td><input type="text" name="auditdate1" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/> 到 <input type="text" name="auditdate2" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
                    <td>银行名称:</td>
                    <td>
                        <input id="account-query-bank" type="text" name="bank" style="width: 180px;"/>
                    </td>
    				<td colspan="2">
    					<a href="javaScript:void(0);" id="account-queay-collectionOrder" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="account-reload-collectionOrder" class="button-qr">重置</a>
						<%--<security:authorize url="/account/receivable/exportCollectionOrderList.do">--%>
							<%--<a href="javaScript:void(0);" id="account-export-collectionOrder" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>--%>
						<%--</security:authorize>--%>
						<span id="account-query-advanced-collectionOrder"></span>
    				</td>
    			</tr>
    		</table>
    	</form>
    </div>
    <div id="account-panel-collectionOrder-addpage"></div>
    <!-- <div id="account-panel-collectionOrder-viewpage"></div> -->
    <div id="account-dialog-writeoff-content"></div>
    <div id="account-dialog-collectionOrder-MergeSubmit"></div>
    <div id="collection-account-dialog"></div>
     <script type="text/javascript">

         var authorprint = 0;
         <security:authorize url="/account/receivable/auditcollectionOrder.do">
         authorprint = 1;
         </security:authorize>

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
								    height: 310,  
								    collapsible:false,
								    minimizable:false,
								    resizable:true,
								    closed: true,  
								    cache: false,  
								    href: 'account/receivable/collectionOrderAddPage.do',  
								    modal: true,
								    onLoad:function(){
								    	$("#account-collectionOrder-customerid").focus();
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
									    height: 310,  
									    collapsible:false,
									    minimizable:false,
									    resizable:true,
									    closed: true,  
									    cache: false,  
									    href: 'account/receivable/collectionOrderEditPage.do?id='+con.id,  
									    modal: true,
									    onLoad:function(){
									    	$("#account-collectionOrder-customerid").focus();
									    }
									});
									$('#account-panel-collectionOrder-addpage').dialog("open");
								}else{
									$('#account-panel-collectionOrder-addpage').dialog({  
									    title: '收款单修改',  
									    width: 650,  
									    height: 310,  
									    collapsible:false,
									    minimizable:false,
									    resizable:true,
									    closed: true,  
									    cache: false,  
									    href: 'account/receivable/collectionOrderViewPage.do?id='+con.id,  
									    modal: true,
									    onLoad:function(){
									    	$("#account-collectionOrder-customerid").focus();
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
                                    if(rows[i].writeoffamount != 0.00){
                                        $.messager.alert("提醒","已核销金额不为零，不允许反审");
                                        return false;
                                    }
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
									            		$.messager.alert("提醒", "反审失败");
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
								    height: 310,  
								    collapsible:false,
								    minimizable:false,
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
					<security:authorize url="/account/receivable/collectionOrderImport.do">
						{
							type: 'button-import',
							attr: {
                                type:'importUserdefined',
                                queryForm: "#account-form-query-collectionOrderPage",//需要传入的条件 放在form表单内
                                url:'account/receivable/importCollectionOrder.do',
                                importparam:'收款单导入：收款金额、客户编号、银行名称必填。',
                                onClose: function(){ //导入成功后窗口关闭时操作，
                                    $("#account-datagrid-collectionOrderPage").datagrid("reload");
                                }
							}
						},
					</security:authorize>
					<security:authorize url="/account/receivable/exportCollectionOrderList.do">
						{
							type: 'button-export',
							attr: {
                                datagrid:"#account-datagrid-collectionOrderPage",
                                queryForm: "#account-form-query-collectionOrderPage", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
                                type:'exportUserdefined',
                                name:'收款单列表',
                                url:'account/receivable/exportCollectionOrderList.do'
							}
						},
					</security:authorize>

                    <security:authorize url="/account/receivable/collectionOrderPrintViewPage.do">
                    {
                        type: 'button-preview',
                        handler: function () {
                        }
                    },
                    </security:authorize>
                    <security:authorize url="/account/receivable/collectionOrderPrintPage.do">
                    {
                        type: 'button-print',
                        handler: function () {
                        }
                    },
                    </security:authorize>
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
					<security:authorize url="/account/receivable/auditSuperCollectionOrder.do">
						{
							id:'button-auditsuper',
							name:'超级审核',
							iconCls:'button-audit',
							handler:function(){
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
									            url :'account/receivable/auditSuperMutCollectionOrder.do',
									            type:'post',
									            dataType:'json',
									            data:{ids:ids},
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
					<security:authorize url="/account/receivable/collectionOrderAssignCustomerPage.do">
						{
							id:'iscustomer-button',
							name:'指定客户',
							iconCls:'button-customer',
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
					<security:authorize url="/account/receivable/setCollectionOrderMerge.do">
						{
							id:'merge-button',
							name:'合并',
							iconCls:'button-merge',
							handler:function(){
								var rows = $("#account-datagrid-collectionOrderPage").datagrid("getChecked");
								var ids = "";
								var flag = true;
								var customerid = null;
								for(var i=0;i<rows.length;i++){
									if(ids==""){
										ids = rows[i].id;
									}else{
										ids += ","+ rows[i].id;
									}
									if(null!=customerid && customerid!=rows[i].customerid){
										flag = false;
										break;
									}
									if(rows[i].status!='3'){
										flag =false;
										break;
									}
									customerid = rows[i].customerid;
								}
								if(!flag){
									$.messager.alert("提醒","只有相同客户下的审核通过的收款单才能合并");
									return false;
								}
                                if(customerid=="${othercustomer}") {
                                    $.messager.alert("提醒", "该客户的收款单不允许合并。");
                                    return false;
                                }
								if(flag){
									$.messager.confirm("提醒","是否合并收款单？",function(r){
									if(r){
										if(ids!=""){
											$("#account-dialog-collectionOrder-MergeSubmit").dialog({
												href:"account/receivable/showCollectionOrderMergeSubmitPage.do?ids="+ids,
												title:"收款单合并",
												width:600,
												height:400,
												modal:true,
												cache:false,
											    modal: true,
											    buttons:[{
														text:'确定',
														handler:function(){
															mergeSubmit();
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
							}
						},
					</security:authorize>
                    <security:authorize url="/erpconnect/addCollectionVouch.do">
                    {
                        id:'collection-account',
                        name:'生成凭证',
                        iconCls:'button-audit',
                        handler: function() {
                            var rows = $("#account-datagrid-collectionOrderPage").datagrid('getChecked');
                            if (rows == null || rows.length == 0) {
                                $.messager.alert("提醒", "请选择至少一条记录");
                                return false;
                            }
                            var ids = "";
                            for(var i=0;i<rows.length;i++){
                                if(rows[i].status != '4' && rows[i].status != '3' ){
                                    $.messager.alert("提醒", "请选择审核通过或关闭状态数据");
                                    return false;
                                }
                                if(i==0){
                                    ids = rows[i].id;
                                }else{
                                    ids += "," + rows[i].id;
                                }
                            }

                            $("#collection-account-dialog").dialog({
                                title:'收款支付凭证（收款单）',
                                width:400,
                                height:260,
                                closed:false,
                                modal:true,
                                cache:false,
                                href:'erpconnect/showCollectionVouchPage.do',
                                onLoad:function(){
                                    $("#collectionAccount-ids").val(ids);
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
                              {field:'salesdeptname',title:'销售部门',width:150,hidden:true,isShow:true},
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
					          {field:'vouchertimes', title: '生成凭证次数', align: 'center', width: 80},
							  {field:'addusername',title:'制单人',width:60,sortable:true},
							  {field:'addtime',title:'制单时间',width:100,sortable:true},
							  {field:'auditusername',title:'审核人',width:60,sortable:true},
							  {field:'audittime',title:'审核时间',width:100,sortable:true},
							  {field:'stopusername',title:'中止人',width:60,hidden:true},
							  {field:'stoptime',title:'中止时间',width:80,sortable:true,hidden:true},
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
		 		sortName:'businessdate',
		 		sortOrder:'desc',
		 		singleSelect:false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
		 		showFooter: true,
                pageSize:100,
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
						    resizable:true,
						    closed: true,  
						    cache: false,  
						    href: 'account/receivable/collectionOrderEditPage.do?id='+rowData.id,  
						    modal: true,
						    onLoad:function(){
						    	$("#account-collectionOrder-customerid").focus();
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
						    resizable:true,
						    closed: true,  
						    cache: false,  
						    href: 'account/receivable/collectionOrderViewPage.do?id='+rowData.id,  
						    modal: true
						});
					$('#account-panel-collectionOrder-addpage').dialog("open");
					}
				},
				onLoadSuccess: function(){
					var footerrows = $(this).datagrid('getFooterRows');
					if(null!=footerrows && footerrows.length>0){
						footerobject = footerrows[0];
						countTotalAmount();
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
				}
			}).datagrid("columnMoving");
			$("#account-query-salesdeptid").widget({
                referwid:'RT_T_SYS_DEPT',
                singleSelect:false,
                width:150,
                onlyLeafCheck:false,
                view:true
            });
			$("#account-query-customerid").widget({
				name:'t_account_collection_order',
				col:'customerid',
    			singleSelect:false,
    			width:180,
    			onlyLeafCheck:false,
    			view:true
			});
			$("#account-query-adduserid").widget({
                referwid:'RL_T_SYS_USER',
                singleSelect:false,
                width:150,
                onlyLeafCheck:false,
                view:true
            });
			$("#account-query-bank").widget({
				name:'t_account_collection_order',
				col:'bank',
    			singleSelect:false,
    			width:180,
    			view:true
			});
			//查询
			$("#account-queay-collectionOrder").click(function(){
				//把form表单的name序列化成JSON对象
	       		var queryJSON = $("#account-form-query-collectionOrderPage").serializeJSON();
	       		$("#account-datagrid-collectionOrderPage").datagrid("load",queryJSON);
			});
			//重置
			$("#account-reload-collectionOrder").click(function(){
                $("#account-query-salesdeptid").widget("clear");
				$("#account-query-customerid").widget("clear");
				$("#account-query-bank").widget("clear");
                $("#account-query-adduserid").widget("clear");
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
    		var footerrows = [{businessdate:'选中金额',amount:amount,writeoffamount:writeoffamount,remainderamount:remainderamount}];
    		if(null != footerobject){
    			footerrows.push(footerobject);
    		}
    		$("#account-datagrid-collectionOrderPage").datagrid("reloadFooter",footerrows);
    	}
    </script>

	<%--打印开始 --%>
	<script type="text/javascript">
        $(function () {
            //打印
            AgReportPrint.init({
                id: "listPage-CollectionOrder-dialog-print",
                code: "account_collectionorder",
                tableId: "account-datagrid-collectionOrderPage",
                url_preview: "print/account/collectionOrderPrintView.do",
                url_print: "print/account/collectionOrderPrint.do",
                btnPreview: "button-preview",
                btnPrint: "button-print",
                getData: getData
            });
            function getData(tableId, printParam) {
                var data = $("#" + tableId).datagrid('getChecked');
                if (data == null || data.length == 0) {
                    $.messager.alert("提醒", "请选择一条记录");
                    return false;
                }
                for (var i = 0; i < data.length; i++) {
                    if (!(data[i].status=='1' || data[i].status=='2' || authorprint == 1)) {
                        $.messager.alert("提醒", data[i].id + "此收款单不可打印");
                        return false;
                    }
                }
                return data;
            }
        });
	</script>
	<%--打印结束 --%>
  </body>
</html>
