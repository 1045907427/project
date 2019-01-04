<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>银行账户借贷单</title>
    <%@include file="/include.jsp" %>
  </head>
  <body>
	<div class="easyui-layout" data-options="fit:true,border:false">
    	<div data-options="region:'center'">
   			<div id="bankamountothers-form-div" style="padding: 0px">
    			<div class="buttonBG" id="bankamountothers-buttons-detaillist" style="height:26px"></div>
				<form action="" id="bankamountothers-form-ListQuery" method="post">
					<table cellpadding="0" cellspacing="1" border="0">
						<tr>
							<td>业务日期：</td>
							<td>
								<input type="text" name="businessdate1" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${day1 }"/> 到 <input type="text" name="businessdate2" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${day2 }"/>
							</td>
                            <td>单据类型：</td>
                            <td>
                                <input id="bankAmountOthers-form-billtype" name="billtype" style="width: 130px;"/>
                            </td>
                            <td>状态：</td>
                            <td>
                                <select name="status" style="width: 130px;">
                                    <option></option>
                                    <option value="2" selected="selected">保存</option>
                                    <option value="3">审核通过</option>
                                    <option value="4">关闭</option>
                                </select>
                            </td>
						</tr>
						<tr>
                            <td>银行名称：</td>
                            <td>
                                <input id="bankamountothers-form-bankid" name="bankid" type="text" style="width: 225px;"/>
                            </td>
                            <td>所属部门：</td>
                            <td>
                                <input id="bankamountothers-form-deptid" name="deptid" type="text" style="width: 130px;"/>
                            </td>
							<td>OA编号：</td>
							<td>
								<input name="oaid" type="text" style="width: 130px;"/>
							</td>
						</tr>
						<tr>
							<td>单据编号：</td>
							<td>
								<input type="text" name="billid" style="width: 225px;"/>
							</td>
                            <td colspan="2"></td>
							<td colspan="2">
								<a href="javaScript:void(0);" id="departmentCosts-query-List" class="button-qr">查询</a>
		    					<a href="javaScript:void(0);" id="departmentCosts-query-reloadList" class="button-qr">重置</a>
							</td>
						</tr>
					</table>
				</form>
			</div>
    		<table id="bankamountothers-table-detail"></table>
    	</div>
    </div>
    <div id="bankamountothers-dialog-detail"></div>
    <div id="bankamountothers-dialog-settlement"></div>
    <script type="text/javascript">
   		var footerobject = null;
    	var initQueryJSON =  $("#bankamountothers-form-ListQuery").serializeJSON();
     	$(function(){
     		//根据初始的列与用户保存的列生成以及字段权限生成新的列
        	var tableColJson=$("#bankamountothers-table-detail").createGridColumnLoad({
        		name:'t_account_bankamount_others',
    	     	frozenCol:[[
    				{field:'ck',checkbox:true,isShow:true}
    	     	]],
    	     	commonCol:[[
    	    		{field:'id',title:'编号',width:130,sortable:true},
    	     		{field:'businessdate',title:'业务日期',width:80,sortable:true},
    				{field:'bankid',title:'银行名称',width:120,sortable:true,
    					formatter:function(val,rowData,rowIndex){
    						return rowData.bankname;
    					}
    				},
    				{field:'deptid',title:'所属部门',width:120,sortable:true,
    					formatter:function(val,rowData,rowIndex){
    						return rowData.deptname;
    					}
    				},
    				{field:'billtype',title:'单据类型',width:80,sortable:true,
    					formatter:function(val,rowData,rowIndex){
    						return getSysCodeName("bankAmountOthersBilltype",val);
    					}
    				},
    				{field:'billid',title:'单据编号',width:130,sortable:true},
    				{field:'oaid',title:'OA编号',width:80,sortable:true},
    				{field:'oppname',title:'对方名称',width:100,sortable:true},
    				{field:'amount1',title:'借',width:80,align:'right',isShow:true,
    					formatter:function(val,rowData,rowIndex){
    						if(rowData.amount != "" && rowData.amount != null && rowData.lendtype=='1'){
    							return formatterMoney(rowData.amount);
    						}
    						else{
    							if(val!=null){
    								return formatterMoney(val);;
    							}else{
    								return "";
    							}
    						}
    					}
    				},
    				{field:'amount2',title:'贷',width:80,align:'right',isShow:true,
    					formatter:function(val,rowData,rowIndex){
    						if(rowData.amount != "" && rowData.amount != null && rowData.lendtype=='2'){
    							return formatterMoney(rowData.amount);
    						}
    						else{
    							if(val!=null){
    								return formatterMoney(val);
    							}else{
    								return "";
    							}
    						}
    					}
    				},
    				{field:'amount',title:'余额',width:80,sortable:true,align:'right',
    					formatter:function(val,rowData,rowIndex){
    						if(val != "" && val != null){
    							if(rowData.lendtype=='1'){
    								return formatterMoney(val);
    							}else if(rowData.lendtype=='2'){
    								return formatterMoney(-val);
    							}else{
    								return formatterMoney(val);
    							}
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
    				{field:'addusername',title:'制单人',width:60,sortable:true},
    				{field:'addtime',title:'制单时间',width:130,sortable:true,
    					formatter:function(val,rowData,rowIndex){
    						if(val){
    							return val.replace(/[tT]/," ");
    						}
    					}
    				},
    				{field:'auditusername',title:'审核人',width:60,sortable:true},
    				{field:'audittime',title:'审核时间',width:130,sortable:true},
    				{field:'remark',title:'备注',width:100}
    			]]
    	     });
        	$("#bankamountothers-buttons-detaillist").buttonWidget({
    			initButton:[
    				{},
    				<security:authorize url="/account/bankamount/addBankAmountOthers.do">
    				{
    					type: 'button-add',
    					handler: function(){
    						$('#bankamountothers-dialog-detail').dialog({  
    						    title: '银行账户借贷新增',  
    						    width: 750,  
    						    height: 350,  
    						    collapsible:false,
    						    minimizable:false,
    						    maximizable:true,
    						    resizable:true,
    						    closed: true,  
    						    cache: false,  
    						    href: 'account/bankamount/showBankAmountOthersAddPage.do',  
    						    modal: true,
    						    onLoad:function(){
    						    	$("#bankAmountOthers-detail-bank").focus();
    						    }
    						});
    						$('#bankamountothers-dialog-detail').dialog("open");
    					}
    				},
    				</security:authorize>
    				<security:authorize url="/account/bankamount/showBankAmountOthersViewPage.do">
    				{
    					type: 'button-view',
    					handler: function(){
    						var con = $("#bankamountothers-table-detail").datagrid('getSelected');
    						if(con == null){
    							$.messager.alert("提醒","请选择一条记录");
    							return false;
    						}	
    						$('#bankamountothers-dialog-detail').dialog({  
    						    title: '银行账户借贷单',  
    						    width: 750,  
    						    height: 350,  
    						    collapsible:false,
    						    minimizable:false,
    						    maximizable:true,
    						    resizable:true,
    						    closed: true,  
    						    cache: false,  
    						    href: 'account/bankamount/showBankAmountOthersViewPage.do?id='+con.id,  
    						    modal: true
    						});
    						$('#bankamountothers-dialog-detail').dialog("open");
    					}
    				},
    				</security:authorize>
                    <security:authorize url="/account/bankamount/exportBankAmountOthers.do">
                    {
                        type: 'button-export',
                        attr: {
                            queryForm: "#bankamountothers-form-ListQuery", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
                            type:'exportUserdefined',
                            url:'account/bankamount/exportBankAmountOthers.do',
                            name:'银行账户借贷单列表'
                        }
                    },
                    </security:authorize>
    				<security:authorize url="/account/bankamount/showBankAmountOthersEditPage.do">
    				{
    					type: 'button-edit',
    					handler: function(){
    						var con = $("#bankamountothers-table-detail").datagrid('getSelected');
    						if(con == null){
    							$.messager.alert("提醒","请选择一条记录");
    							return false;
    						}	
    						$('#bankamountothers-dialog-detail').dialog({  
    						    title: '银行账户借贷单',  
    						    width: 750,  
    						    height: 350,
    						    collapsible:false,
    						    minimizable:false,
    						    maximizable:true,
    						    resizable:true,
    						    closed: true,  
    						    cache: false,  
    						    href: 'account/bankamount/showBankAmountOthersEditPage.do?id='+con.id,  
    						    modal: true
    						});
    						$('#bankamountothers-dialog-detail').dialog("open");
    					}
    				},
    				</security:authorize>
    				<security:authorize url="/account/bankamount/deleteBankAmountOthers.do">
    					{
    						type: 'button-delete',
    						handler: function(){
    							$.messager.confirm("提醒","是否删除当前银行账户借贷单？",function(r){
    								if(r){
    									var rows = $("#bankamountothers-table-detail").datagrid("getChecked");
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
    								            url :'account/bankamount/deleteBankAmountOthers.do?ids='+ids,
    								            type:'post',
    								            dataType:'json',
    								            success:function(json){
    								            	loaded();
    								            	if(json.flag){
    								            		$.messager.alert("提醒", "删除成功</br>"+json.succssids+"</br>"+json.errorids);
    								            		$("#bankamountothers-table-detail").datagrid("reload");
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
    				<security:authorize url="/account/bankamount/auditBankAmountOthers.do">
    					{
    						type: 'button-audit',
    						handler: function(){
    							$.messager.confirm("提醒","是否审核选中的银行账户借贷单？",function(r){
    								if(r){
    									var rows = $("#bankamountothers-table-detail").datagrid("getChecked");
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
    								            url :'account/bankamount/auditBankAmountOthers.do?ids='+ids,
    								            type:'post',
    								            dataType:'json',
    								            success:function(json){
    								            	loaded();
    								            	if(json.flag){
    								            		$.messager.alert("提醒", "审核成功</br>"+json.succssids+"</br>"+json.errorids);
    								            		$("#bankamountothers-table-detail").datagrid("reload");
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
    				<security:authorize url="/account/bankamount/oppauditBankAmountOthers.do">
    					{
    			 			type:'button-oppaudit',
    			 			handler:function(){
    			 				$.messager.confirm("提醒","是否反审银行账户借贷单？",function(r){
    								if(r){
    									var rows = $("#bankamountothers-table-detail").datagrid("getChecked");
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
    								            url :'account/bankamount/oppauditBankAmountOthers.do?ids='+ids,
    								            type:'post',
    								            dataType:'json',
    								            success:function(json){
    								            	loaded();
    								            	if(json.flag){
    								            		$.messager.alert("提醒", "反审成功</br>"+json.succssids+"</br>"+json.errorids);
    								            		$("#bankamountothers-table-detail").datagrid("reload");
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
    									$.messager.confirm("提醒","是否关闭银行账户借贷单？",function(r){
    										if(r){
    											var rows = $("#bankamountothers-table-detail").datagrid("getChecked");
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
    										            url :'account/bankamount/closeBankAmountOthers.do?ids='+ids,
    										            type:'post',
    										            dataType:'json',
    										            success:function(json){
    										            	loaded();
    										            	if(json.flag){
    										            		$.messager.alert("提醒", "关闭成功</br>"+json.succssids+"</br>"+json.errorids);
    										            		$("#bankamountothers-table-detail").datagrid("reload");
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
    			tname: 't_account_bankamount_others'
    		});
     		$("#bankamountothers-table-detail").datagrid({ 
     			authority:tableColJson,
	  	 		frozenColumns:tableColJson.frozen,
				columns:tableColJson.common,
	  	 		fit:true,
	  	 		method:'post',
	  	 		showFooter: true,
	  	 		rownumbers:true,
	  	 		sortName:'id',
	  	 		sortOrder:'asc',
	  	 		pagination:true,
		 		idField:'id',
	  	 		singleSelect:false,
		 		checkOnSelect:true,
		 		selectOnCheck:true,
				pageSize:20,
				queryParams:initQueryJSON,
				toolbar:'#bankamountothers-form-div',
				url: 'account/bankamount/showBankAmountOthersList.do',
		    	onDblClickRow:function(rowIndex, rowData){
		    		$('#bankamountothers-dialog-detail').dialog({  
					    title: '银行账户借贷单',  
					    width: 750,  
					    height: 350,  
					    collapsible:false,
					    minimizable:false,
					    maximizable:true,
					    resizable:true,
					    closed: true,  
					    cache: false,  
					    href: 'account/bankamount/showBankAmountOthersEditPage.do?id='+rowData.id,  
					    modal: true
					});
					$('#bankamountothers-dialog-detail').dialog("open");
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
     		$("#bankAmountOthers-form-billtype").widget({
    			name:'t_account_bankamount_others',
				col:'billtype',
    			width:130,
    			initSelectNull:true,
				singleSelect:true
    		});
     		$("#bankamountothers-form-bankid").widget({
     			referwid:'RL_T_BASE_FINANCE_BANK',
    			width:225,
				singleSelect:true,
				onlyLeafCheck:false
     		});
     		$("#bankamountothers-form-deptid").widget({
     			referwid:'RT_T_SYS_DEPT',
    			width:130,
				singleSelect:true,
				onlyLeafCheck:false
     		});
     		$("#departmentCosts-query-List").click(function(){
     			//把form表单的name序列化成JSON对象
	       		var queryJSON = $("#bankamountothers-form-ListQuery").serializeJSON();
	       		$("#bankamountothers-table-detail").datagrid("load",queryJSON);
     		});
     		
     		$("#departmentCosts-query-reloadList").click(function(){
     			$("#bankamountothers-form-bankid").widget("clear");
     			$("#bankamountothers-form-deptid").widget("clear");
     			$("#bankAmountOthers-form-billtype").widget("clear");
     			$("#bankamountothers-form-ListQuery")[0].reset();
				var queryJSON =  $("#bankamountothers-form-ListQuery").serializeJSON();
				$("#bankamountothers-table-detail").datagrid("load",queryJSON);
     		});
     	});
     	function deptCostsCountTotalAmount(){
     		var rows =  $("#bankamountothers-table-detail").datagrid('getChecked');
     		var amount = 0;
     		var amount1 = 0;
     		var amount2 = 0;
     		for(var i=0;i<rows.length;i++){
     			if(rows[i].lendtype=='1'){
     				amount = Number(amount)+Number(rows[i].amount == undefined ? 0 : rows[i].amount);
     				amount1 = Number(amount1)+Number(rows[i].amount == undefined ? 0 : rows[i].amount);
     			}else{
     				amount = Number(amount)-Number(rows[i].amount == undefined ? 0 : rows[i].amount);
     				amount2 = Number(amount2)+Number(rows[i].amount == undefined ? 0 : rows[i].amount);
     			}
    		}
    		var foot = [
   				{
   					bankname:'选中金额',
   					amount1:amount1,
   					amount2:amount2,
    				amount:amount
   				}
   			];
   			if(null!=footerobject){
        		foot.push(footerobject);
    		}
   			$("#bankamountothers-table-detail").datagrid("reloadFooter",foot);
     	}
    </script>
  </body>
</html>
