<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>银行账户期初列表</title>
    <%@include file="/include.jsp" %>
  </head>
  <body>
	<div class="easyui-layout" data-options="fit:true,border:false">
    	<div data-options="region:'center'">
   			<div id="bankamountbegin-form-div" style="padding: 0px">
    			<div class="buttonBG" id="bankamountbegin-buttons-detaillist" style="height:26px;"></div>
				<form action="" id="bankamountbegin-form-ListQuery" method="post">
					<table cellpadding="0" cellspacing="1" border="0">
						<tr>
							<td>银行名称：</td>
							<td>
								<input id="bankamountbegin-form-bankid" name="bankid" type="text" style="width: 120px;"/>
							</td>
							<td>状态：</td>
							<td>
								<select name="status" style="width: 120px;">
									<option></option>
									<option value="2" selected="selected">保存</option>
									<option value="3">审核通过</option>
									<option value="4">关闭</option>
								</select>
							</td>
							<td colspan="2">
								<a href="javaScript:void(0);" id="departmentCosts-query-List" class="button-qr">查询</a>
		    					<a href="javaScript:void(0);" id="departmentCosts-query-reloadList" class="button-qr">重置</a>
							</td>
						</tr>
					</table>
				</form>
			</div>
    		<table id="bankamountbegin-table-detail"></table>
    	</div>
    </div>
    <div id="bankamountbegin-dialog-detail"></div>
    <div id="bankamountbegin-dialog-settlement"></div>
    <script type="text/javascript">
		//根据初始的列与用户保存的列生成以及字段权限生成新的列
    	var tableColJson=$("#bankamountbegin-table-detail").createGridColumnLoad({
    		name:'t_account_bankamount_begin',
	     	frozenCol:[[
				{field:'ck',checkbox:true,isShow:true}
	     	]],
	     	commonCol:[[
	    		{field:'id',title:'编码',width:130,sortable:true},
	     		{field:'businessdate',title:'业务日期',width:80,sortable:true},
				{field:'bankid',title:'银行名称',width:120,sortable:true,
					formatter:function(val,rowData,rowIndex){
						return rowData.bankname;
					}
				},
				{field:'amount',title:'期初金额',width:80,sortable:true,align:'right',
					formatter:function(val,rowData,rowIndex){
						if(val != "" && val != null){
							return formatterMoney(val);
						}
						else{
							return "0.00";
						}
					}
				},
				{field:'status',title:'状态',width:70,sortable:true,
					formatter:function(val,rowData,rowIndex){
						if(val=='2'){
							return "保存";
						}else if(val=='3'){
							return "审核通过";
						}else if(val=='4'){
							return "关闭";
						}
					}
				},
				{field:'addusername',title:'制单人',width:80,sortable:true},
				{field:'addtime',title:'制单时间',width:130,sortable:true,
					formatter:function(val,rowData,rowIndex){
						if(val){
							return val.replace(/[tT]/," ");
						}
					}
				},
				{field:'auditusername',title:'审核人',width:80,sortable:true},
				{field:'audittime',title:'审核时间',width:130,sortable:true},
				{field:'remark',title:'备注',width:100}
			]]
	     });
    	$("#bankamountbegin-buttons-detaillist").buttonWidget({
			initButton:[
				{},
				<security:authorize url="/account/bankamount/addBankAmountBegin.do">
				{
					type: 'button-add',
					handler: function(){
						$('#bankamountbegin-dialog-detail').dialog({  
						    title: '银行账户期初新增',  
						    width: 650,  
						    height: 310,  
						    collapsible:false,
						    minimizable:false,
						    maximizable:true,
						    resizable:true,
						    closed: true,  
						    cache: false,  
						    href: 'account/bankamount/showBankAmountBeginAddPage.do',  
						    modal: true,
						    onLoad:function(){
						    	
						    }
						});
						$('#bankamountbegin-dialog-detail').dialog("open");
					}
				},
				</security:authorize>
				<security:authorize url="/account/bankamount/showBankAmountBeginViewPage.do">
				{
					type: 'button-view',
					handler: function(){
						var con = $("#bankamountbegin-table-detail").datagrid('getSelected');
						if(con == null){
							$.messager.alert("提醒","请选择一条记录");
							return false;
						}	
						$('#bankamountbegin-dialog-detail').dialog({  
						    title: '银行账户期初',  
						    width: 650,  
						    height: 310,  
						    collapsible:false,
						    minimizable:false,
						    maximizable:true,
						    resizable:true,
						    closed: true,  
						    cache: false,  
						    href: 'account/bankamount/showBankAmountBeginViewPage.do?id='+con.id,  
						    modal: true
						});
						$('#bankamountbegin-dialog-detail').dialog("open");
					}
				},
				</security:authorize>
				<security:authorize url="/account/bankamount/showBankAmountBeginEditPage.do">
				{
					type: 'button-edit',
					handler: function(){
						var con = $("#bankamountbegin-table-detail").datagrid('getSelected');
						if(con == null){
							$.messager.alert("提醒","请选择一条记录");
							return false;
						}	
						$('#bankamountbegin-dialog-detail').dialog({  
						    title: '银行账户期初',  
						    width: 650,  
						    height: 310,  
						    collapsible:false,
						    minimizable:false,
						    maximizable:true,
						    resizable:true,
						    closed: true,  
						    cache: false,  
						    href: 'account/bankamount/showBankAmountBeginEditPage.do?id='+con.id,  
						    modal: true
						});
						$('#bankamountbegin-dialog-detail').dialog("open");
					}
				},
				</security:authorize>
				<security:authorize url="/account/bankamount/deleteBankAmountBegin.do">
					{
						type: 'button-delete',
						handler: function(){
							$.messager.confirm("提醒","是否删除当前银行账户期初？",function(r){
								if(r){
									var rows = $("#bankamountbegin-table-detail").datagrid("getChecked");
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
								            url :'account/bankamount/deleteBankAmountBegin.do?ids='+ids,
								            type:'post',
								            dataType:'json',
								            success:function(json){
								            	loaded();
								            	if(json.flag){
								            		$.messager.alert("提醒", "删除成功</br>"+json.succssids+"</br>"+json.errorids);
								            		$("#bankamountbegin-table-detail").datagrid("reload");
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
				<security:authorize url="/account/bankamount/auditBankAmountBegin.do">
					{
						type: 'button-audit',
						handler: function(){
							$.messager.confirm("提醒","是否审核选中的银行账户期初？",function(r){
								if(r){
									var rows = $("#bankamountbegin-table-detail").datagrid("getChecked");
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
								            url :'account/bankamount/auditBankAmountBegin.do?ids='+ids,
								            type:'post',
								            dataType:'json',
								            success:function(json){
								            	loaded();
								            	if(json.flag){
								            		$.messager.alert("提醒", "审核成功</br>"+json.succssids+"</br>"+json.errorids);
								            		$("#bankamountbegin-table-detail").datagrid("reload");
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
				<security:authorize url="/account/bankamount/oppauditBankAmountBegin.do">
					{
			 			type:'button-oppaudit',
			 			handler:function(){
			 				$.messager.confirm("提醒","是否反审银行账户期初？",function(r){
								if(r){
									var rows = $("#bankamountbegin-table-detail").datagrid("getChecked");
									var ids = "";
									for(var i=0;i<rows.length;i++){
										if(ids==""){
											ids = rows[i].id;
										}else{
											ids += ","+ rows[i].id;
										}
									}
									loading("反审中..");
									if(ids!=""){
										$.ajax({   
								            url :'account/bankamount/oppauditBankAmountBegin.do?ids='+ids,
								            type:'post',
								            dataType:'json',
								            success:function(json){
								            	loaded();
								            	if(json.flag){
								            		$.messager.alert("提醒", "反审成功</br>"+json.succssids+"</br>"+json.errorids);
								            		$("#bankamountbegin-table-detail").datagrid("reload");
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
								}
							});
			 			}
		 			},
				</security:authorize>
				{}
			],
			buttons:[
						<security:authorize url="/account/bankamount/closeBankamountbegin.do">
							{
								id:'button-closebill',
								name:'关闭',
								iconCls:'button-delete',
								handler:function(){
									$.messager.confirm("提醒","是否关闭银行账户期初？",function(r){
										if(r){
											var rows = $("#bankamountbegin-table-detail").datagrid("getChecked");
											var ids = "";
											for(var i=0;i<rows.length;i++){
												if(ids==""){
													ids = rows[i].id;
												}else{
													ids += ","+ rows[i].id;
												}
											}
											loading("关闭中..");
											if(ids!=""){
												$.ajax({   
										            url :'account/bankamount/closeBankAmountBegin.do?ids='+ids,
										            type:'post',
										            dataType:'json',
										            success:function(json){
										            	loaded();
										            	if(json.flag){
										            		$.messager.alert("提醒", "关闭成功</br>"+json.succssids+"</br>"+json.errorids);
										            		$("#bankamountbegin-table-detail").datagrid("reload");
										            	}else{
										            		$.messager.alert("提醒", "关闭失败");
										            	}
										            },
										            error:function(){
										            	$.messager.alert("错误", "关闭出错");
										            	loaded();
										            }
										        });
											}
										}
									});
								}
							}
						</security:authorize>
					],
			model: 'bill',
			type: 'list',
			tname: 't_account_bankamount_begin'
		});
    	
    	var initQueryJSON =  $("#bankamountbegin-form-ListQuery").serializeJSON();
     	$(function(){
     		$("#bankamountbegin-table-detail").datagrid({ 
     			authority:tableColJson,
	  	 		frozenColumns:tableColJson.frozen,
				columns:tableColJson.common,
	  	 		fit:true,
	  	 		method:'post',
	  	 		showFooter: true,
	  	 		rownumbers:true,
	  	 		sortName:'id',
	  	 		sortOrder:'desc',
	  	 		pagination:true,
		 		idField:'id',
	  	 		singleSelect:false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
				pageSize:20,
				queryParams:initQueryJSON,
				toolbar:'#bankamountbegin-form-div',
				url: 'account/bankamount/showBankAmountBeginList.do',
		    	onDblClickRow:function(rowIndex, rowData){
		    		$('#bankamountbegin-dialog-detail').dialog({  
					    title: '银行账户期初',  
					    width: 650,  
					    height: 310,  
					    collapsible:false,
					    minimizable:false,
					    maximizable:true,
					    resizable:true,
					    closed: true,  
					    cache: false,  
					    href: 'account/bankamount/showBankAmountBeginEditPage.do?id='+rowData.id,  
					    modal: true
					});
					$('#bankamountbegin-dialog-detail').dialog("open");
		    	},
				onLoadSuccess:function(){
					var footerrows = $(this).datagrid('getFooterRows');
					if(null!=footerrows && footerrows.length>0){
						footerobject = footerrows[0];
					}
		 		},
				onCheckAll:function(){
		 			deptCostsCountTotalAmount();
				},
				onUncheckAll:function(){
					deptCostsCountTotalAmount();
				},
				onCheck:function(){
					deptCostsCountTotalAmount();
				},
				onUncheck:function(){
					deptCostsCountTotalAmount();
				}
			}).datagrid("columnMoving");
     		
     		$("#bankamountbegin-form-bankid").widget({
     			referwid:'RL_T_BASE_FINANCE_BANK',
    			width:130,
				singleSelect:true,
				onlyLeafCheck:false
     		});
     		$("#departmentCosts-query-List").click(function(){
     			//把form表单的name序列化成JSON对象
	       		var queryJSON = $("#bankamountbegin-form-ListQuery").serializeJSON();
	       		$("#bankamountbegin-table-detail").datagrid("load",queryJSON);
     		});
     		
     		$("#departmentCosts-query-reloadList").click(function(){
     			$("#bankamountbegin-form-bankid").widget("clear");
     			$("#bankamountbegin-form-ListQuery")[0].reset();
				var queryJSON =  $("#bankamountbegin-form-ListQuery").serializeJSON();
				$("#bankamountbegin-table-detail").datagrid("load",queryJSON);
     		});
     	});
     	function deptCostsCountTotalAmount(){
     		
     	}
    </script>
  </body>
</html>
