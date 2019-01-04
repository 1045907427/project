<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<jsp:useBean id="today" class="java.util.Date"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>客户应付费用列表</title>
    <%@include file="/include.jsp" %>
  </head>
  <%
  	boolean isEdit=false;
  %>
  <security:authorize url="/journalsheet/customercost/customercostEditBtn.do">
  	<% isEdit=true; %>
  </security:authorize>
  <body>
    <div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'center',border:false" style="padding: 0px;">
    		<div id="customerCostPayable-detail-tableBtn" style="padding: 0px">
                <div class="buttonBG" id="customerCostPayable-detail-button-payable"></div>
	   			<form action="" id="customerCostPayable-detail-form-ListQuery" method="post">
	    			<table class="querytable">
	    				<tr>
	    					<td>业务日期：</td>
	    					<td>
	    						<input type="text" name="businessdate" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="<fmt:formatDate value='${today }' pattern='yyyy-MM-dd' type='date' dateStyle='long' />" />到<input type="text" name="businessdate1" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${businessdate1 }"/>
	    					</td>
	    					<td>费用类别：</td>
	    					<td>
	    						<input id="customerCostPayable-detail-query-expensesort" type="text" name="expensesort" style="width: 130px;"/>
	    					</td>
	    					<td>OA编号：</td>
	    					<td>
	    						<input id="customerCostPayable-detail-query-oaid" type="text" name="oaid" style="width: 130px;"/>
	    					</td>
	    				</tr>
	    				<tr>
	    					<td align="right">客&nbsp;&nbsp;户：</td>
	    					<td>
	    						<input id="customerCostPayable-detail-query-customerid" type="text" name="customerid" style="width: 212px;"/>
	    					</td>
	    					<td align="right">供 应 商：</td>
	    					<td colspan="3">
	    						<input id="customerCostPayable-detail-query-supplierid" type="text" name="supplierid" style="width: 328px;"/>
	    					</td>
	    					<td>
	    						支付类型：	    						
	    						<select id="customerCostPayable-detail-query-paytype" name="paytype" style="width:130px;">
		    						<option value="">全部</option>
		    						<option value="1">支付</option>
		    						<option value="2">冲差</option>
		    					</select>
	    					</td>
	    				</tr>
	    				<tr>
	    					<td>来源类型：</td>
	                    	<td>
	                    		<select id="customerCostPayable-detail-query-sourcefrom" name="sourcefrom" style="width:212px">
	                    			<option value="">全部</option>
	                    			<option value="0">手工录入</option>
	                    			<option value="1">代垫</option>
	                    			<option value="11">费用冲差支付单</option>
	                    			<option value="12">通路单 </option>
	                    			<option value="13">客户费用申请单 </option>
	                    			<option value="14">客户费用批量支付申请单 </option>
									<option value="15">客户费用申请单（账扣） </option>
									<option value="17">品牌费用申请单（支付） </option>
									<option value="20">代垫费用申请单</option>
	                    		</select>
	                    	</td>
	    					<td>是否期初：</td>
	    					<td>
	    						<select id="customerCostPayable-detail-query-isbegin" name="isbegin" style="width:130px;">
		    						<option value="">全部</option>
		    						<option value="1">是</option>
		    						<option value="2">否</option>
		    					</select>
	    					</td>  
	                        <!--
	    					<td>申请部门：</td>
	    					<td>
	    						<input id="customerCostPayable-detail-query-applydeptid" type="text" name="applydeptid" style="width: 130px;"/>
	    					</td>
	    					-->
	    					<td>是否红冲：</td>
	    					<td>
	    						<select id="customerCostPayable-detail-query-ishcflag" name="ishcflag" style="width:130px;">
		    						<option value="">全部</option>
		    						<option value="1">是</option>
		    						<option value="2">否</option>
		    					</select>
	    					</td>
	                    </tr>
	                    <tr>
	                    	<%--
	                    	<td>来源单据：</td>
	                    	<td>
	                    		<input type="text" name="billno" style="width: 180px;"/>
	                    	</td> --%> 
	    					<td align="right">申 请 人：</td>
	    					<td>
	    						<input id="customerCostPayable-detail-query-applyuserid" type="text" name="applyuserid" style="width: 212px;"/>
	    					</td>
	    					<td>单据类型：</td>
	    					<td>
	    						<select name="billtype" style="width:130px;">
		    						<option value="">全部</option>
		    						<option value="1">借</option>
		    						<option value="2">贷</option>
		    					</select>
	    					</td>  
	    					<td>快捷过滤：</td>
	    					<td>
	    						<select id="customerCostPayable-detail-query-fastfilter" name="fastfilter" style="width:130px;">
		    						<option value="">全部</option>
		    						<option value="1">可红冲</option>
		    						<option value="2">可撤销红冲</option>
		    						<option value="3">可编辑删除</option>
		    					</select>
	    					</td> 
	    					<td>
	    						<a href="javaScript:void(0);" id="customerCostPayable-detail-query-btn-List" class="button-qr">查询</a>
	                            <a href="javaScript:void(0);" id="customerCostPayable-detail-query-btn-reloadList" class="button-qr">重置</a>
	    					</td>
	                    </tr>
	    			</table>
	    		</form>
	   		</div>
	   		<table id="customerCostPayable-detail-table"></table>	   		
    	</div>
    </div>
    <div style="display:none">
    	<div id="customerCostPayable-dialog-detail"></div>
    	<div id="customerCostPayableInit-dialog-detail"></div>
    	<a href="javaScript:void(0);" id="customerCostPayable-detail-buttons-exportclick" style="display: none"title="导出">导出</a>
		<a href="javaScript:void(0);" id="customerCostPayable-detail-buttons-importclick" style="display: none"title="导入">导入</a>
    </div>
	<div id="customerCostPayable-account-dialog"></div>
    <style>
    	a.acinview{
    		color:#00f;
    		text-decoration: underline;
    		cursor: pointer;
    	}
    </style>
    <script type="text/javascript">
  //根据初始的列与用户保存的列生成以及字段权限生成新的列
	var footerobject = null;
	var initQueryJSON =  $("#customerCostPayable-detail-form-ListQuery").serializeJSON();
     $(function(){
    	 var customercostdetailListColJson=$("#customerCostPayable-detail-table").createGridColumnLoad({
				frozenCol : [[
								{field:'ck',checkbox:true,isShow:true}
  				]],
    	     	commonCol:[[
    				{field:'id',title:'单据编号',width:130,sortable:true},
    				{field:'oaid',title:'OA编号',width:70,sortable:true,
                        formatter: function(value,row,index){

                            if(value != undefined 
                            		&& value != null 
                            		&& value != '' 
                            		&& value != '合计' 
                            		&& value != '选中金额') {

                                return '<a href="javascript:void(0);" onclick="viewOa(' + value + ')">' + value + '</a>';
                            }

                            return value;
                        }
                    },
    				{field:'businessdate',title:'业务日期',width:80,sortable:true},
    				{field:'customerid',title:'客户编码',width:70,isShow:true},
    				{field:'customername',title:'客户名称',width:150,isShow:true},
    				{field:'expensesortname',title:'费用类别',width:100,isShow:true},
    				{field:'supplierid',title:'供应商编码',width:70,isShow:true},
    				{field:'suppliername',title:'供应商名称',width:200,isShow:true},
                    {field:'bankid',title:'银行',width:100,isShow:true,
                        formatter:function(val, row, index){
                        	return row.bankname;
                        }
					},
    				{field:'lendamount',title:'借',width:80,sortable:true,align:'right',
    					formatter:function(val,rowData,rowIndex){
    						if(val != "" && val != null){
    							return formatterMoney(val);
    						}
    						else{
    							return "";
    						}
    					}
    				},
    				{field:'payamount',title:'贷',width:80,sortable:true,align:'right',
    					formatter:function(val,rowData,rowIndex){
    						if(val != "" && val != null){
    							return formatterMoney(val);
    						}
    						else{
    							return "";
    						}
    					}
    				},
    				{field:'hcflag',title:'是否红冲',width:60,sortable:true,
    					formatter:function(val,rowData,rowIndex){
    						if(val=='0'){
    							return "否";
    						}else if(val=='1' || val=='2'){
    							return "是";
    						}
    					}
    				},
    				{field:'isbegin',title:'是否期初',width:60,sortable:true,
    					formatter:function(val,rowData,rowIndex){
    						if(val=='0'){
    							return "否";
    						}else if(val=='1'){
    							return "是";
    						}
    					}
    				},
					{field: 'vouchertimes', title: '生成凭证次数', align: 'center', width: 80},
    				{field:'remark',title:'备注',width:100,isShow:true},
    				{field:'relateoaid',title:'关联OA号',width:70,isShow:true,
                        formatter: function(value,row,index){

                            if(value != undefined 
                            		&& value != null 
                            		&& value != '' 
                            		&& value != '合计'
                                    && value != '选中金额'
                                    && value != '余额') {

                                return '<a href="javascript:void(0);" onclick="viewOa(' + value + ')">' + value + '</a>';
                            }

                            return value;
                        }
                    },
    				{field:'applyusername',title:'申请人',width:80,isShow:true},
    				/*{field:'applydeptname',title:'申请部门',width:80,isShow:true},*/
    				{field:'addtime',title:'申请时间',width:130,isShow:true,sortable:true},
    				{field:'billtype',title:'单据类型',resizable:true,
    					formatter:function(val,rowData,rowIndex){
    						if(val=="1"){
    							return "借";
    						}else if(val=="2"){
    							return "贷";
    						}
    					}
    				},
    				{field:'paytype',title:'支付类型',resizable:true,
    					formatter:function(val,rowData,rowIndex){
    						if(val=="1"){
    							return "支付";
    						}else if(val=="2"){
    							return "冲差";
    						}
    					}
    				},
    				{field:'sourcefrom',title:'数据来源',resizable:true,width:80,
    					formatter:function(val,rowData,rowIndex){
    						if(val=="0"){
    							return "手工录入";
    						}else if(val=="1"){
    							return "代垫";
    						}else if(val=="11"){
    							return "费用冲差支付单";
    						}else if(val=="12"){
    							return "通路单";
    						}else if(val=="13"){
    							return "客户费用申请单";
    						}else if(val=="14"){
    							return "客户费用批量支付申请单";
    						}else if(val=="15"){
								return "客户费用申请单（账扣）";
							}else if(val=="16"){
								return "客户费用申请单（账扣）";
							}else if(val=="17"){
								return "品牌费用申请单（支付）";
							}else if(val=="18"){
								return "品牌费用申请单（支付）";
							} else if(val=="19"){
								return "费用冲差支付单";
							} else if(val=="20"){
                                return "代垫费用申请单";
                            } else if(val=="21"){
                                return "代垫费用申请单";
                            }
    					}
    				},
    				{field:'billno',title:'来源单据号',resizable:true},
    				{field:'hcreferid',title:'红冲关联单据号',resizable:true,
    					formatter:function(val,rowData,rowIndex){
    						if(val!=null && val!=""){
    							return "<a class=\"acinview\" herf=\"javascript:void(0);\" onclick=\"javascript:showCustomerCostPayableView('"+val+"')\">"+val+"</a>";
    						}
    					}
    				}
    			]]
    	     });
    	 $("#customerCostPayable-detail-table").datagrid({
				authority:customercostdetailListColJson,
	  	 		frozenColumns:customercostdetailListColJson.frozen,
				columns:customercostdetailListColJson.common,
	  	 		fit:true,
	  	 		method:'post',
	  	 		title:'客户应付费用列表',
	  	 		showFooter: true,
	  	 		rownumbers:true,
	  	 		sortOrder:'asc',
	  	 		sortName:'addtime',
	  	 		pagination:true,
	  	 		idField:'id',
	  	 		singleSelect:false,
	  	 		checkOnSelect:true,
				selectOnCheck:true,
				pageSize:200,
				//url:'journalsheet/customercost/showCustomerCostPayableDetailList.do',
				queryParams:initQueryJSON,
				toolbar:'#customerCostPayable-detail-tableBtn',
				onLoadSuccess: function(){
					var footerrows = $(this).datagrid('getFooterRows');
					if(null!=footerrows && footerrows.length>0){
						footerobject = footerrows[0];
						countTotalAmount();
					}
				},
		 		rowStyler:function(index,row){
		 			if(row.hcflag && row.hcflag=='1'){
		 				return "color:#f00";
		 			}
		 			if(row.hcflag && row.hcflag=='2'){
		 				return "color:#00f";
		 			}
		 		},
			    onSelect:function(rowIndex, rowData){
			    	if(rowData.sourcefrom=='0' && rowData.isbegin=='0'){
			    		$("#customerCostPayable-detail-button-payable").buttonWidget("enableButton", 'button-id-edit');
			    		$("#customerCostPayable-detail-button-payable").buttonWidget("enableButton", 'button-id-delete');
			    	}else{		    		
			    		$("#customerCostPayable-detail-button-payable").buttonWidget("disableButton", 'button-id-edit');
			    		$("#customerCostPayable-detail-button-payable").buttonWidget("disableButton", 'button-id-delete');
			    	}
			    	if(rowData.sourcefrom!='11' && rowData.isbegin=='0' && rowData.billtype=='2'){
			    		if(rowData.hcflag=='1' || rowData.hcflag=='2'){
				    		$("#customerCostPayable-detail-button-payable").buttonWidget("disableButton", 'button-id-addhc');
				    		$("#customerCostPayable-detail-button-payable").buttonWidget("enableButton", 'button-id-removehc');			    		
				    	}else{	
				    		$("#customerCostPayable-detail-button-payable").buttonWidget("enableButton", 'button-id-addhc');
				    		$("#customerCostPayable-detail-button-payable").buttonWidget("disableButton", 'button-id-removehc');					    		
				    	}
			    	}else{
			    		$("#customerCostPayable-detail-button-payable").buttonWidget("disableButton", 'button-id-addhc');
			    		$("#customerCostPayable-detail-button-payable").buttonWidget("disableButton", 'button-id-removehc');	
			    	} 
			    },
		    	onDblClickRow:function(rowIndex, rowData){
		    		var title="客户应付费用";
		    		if(rowData.hcflag=='1' || rowData.hcflag=='2'){
		    			title="客户应付费用红冲";
		    		}
			    	<%if(isEdit){%>
			    		if(rowData.sourcefrom=='0' 
			    				&& rowData.isbegin=='0' 
			    				&& rowData.hcflag!='1' 
			    				&& rowData.hcflag!='2'){
			    			customerCostPayableAEVDailog("客户应付费用【修改】", 'journalsheet/customercost/showCustomerCostPayableEditPage.do?id='+rowData.id);
			    		}else{
			    			customerCostPayableAEVDailog(title+"【详情】", 'journalsheet/customercost/showCustomerCostPayableViewPage.do?id='+rowData.id);
			    		}
	     			<% }else { %>
	     				customerCostPayableAEVDailog(title+"【详情】", 'journalsheet/customercost/showCustomerCostPayableViewPage.do?id='+rowData.id);
	     			<%}%>
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
    	 
    	 
    	 $("#customerCostPayable-detail-button-payable").buttonWidget({
  			//初始默认按钮 根据type对应按钮事件
				initButton:[
					{}
	 			],
				buttons:[
					<security:authorize url="/journalsheet/customercost/customercostAddBtn.do">
					{
						id:'button-id-add',
						name:'新增 ',
						iconCls:'button-add',
						handler:function(){
							customerCostPayableAEVDailog('客户应付费用【新增】', 'journalsheet/customercost/showCustomerCostPayableAddPage.do');
						}
					},
					</security:authorize>
					<security:authorize url="/journalsheet/customercost/customercostEditBtn.do">
					{
						id:'button-id-edit',
						name:'修改 ',
						iconCls:'button-edit',
						handler:function(){
							var customercost=$("#customerCostPayable-detail-table").datagrid('getSelected');
							if(customercost==null){
								$.messager.alert("提醒","请选择相应的客户应付费用!");
								return false;
							}
							
							/*
							var flag = isDoLockData(customercost.id,"t_js_customercost_payable");
							if(!flag){
								$.messager.alert("警告","该数据正在被其他人操作，暂不能修改！");
								return false;
							}
							*/
							if(customercost.hcflag=='1' || customercost.hcflag=='2'){
								$.messager.alert("提醒","抱歉，红冲不能修改");
								return false;
							}

							if(customercost.sourcefrom !='0'){
								$.messager.alert("提醒","只有手工录入才可以修改");
								return false;
							}

							if(customercost.isbegin !='0'){
								$.messager.alert("提醒","期初数据不能修改，请在期初列表里修改");
								return false;
							}
							customerCostPayableAEVDailog("客户应付费用【修改】", 'journalsheet/customercost/showCustomerCostPayableEditPage.do?id='+customercost.id);
						}
					},
					</security:authorize>
					<security:authorize url="/journalsheet/customercost/customercostAddHChongBtn.do">
					{
						id:'button-id-addhc',
						name:'红冲 ',
						iconCls:'button-add',
						handler:function(){
							var url='journalsheet/customercost/showCustomerCostPayableHCAddPage.do';
							var dataRow=$("#customerCostPayable-detail-table").datagrid('getSelected');
							if(dataRow==null || dataRow.id==null || $.trim(dataRow.id)==""){
								$.messager.alert("提醒","请选择相应的客户应付费用!");
								return false;
							}
							if(dataRow.billtype!='2'){
								$.messager.alert("提醒","抱歉，单据类型为贷的客户应付费用才能红冲。");
								return false;
							}
							if(dataRow.sourcefrom=='11'){
								$.messager.alert("提醒","抱歉，来源类型为费用冲差支付单不能被红冲。");
								return false;
							}
							if(dataRow.hcflag!=null && ($.trim(dataRow.hcflag) =="1" || $.trim(dataRow.hcflag) =="2")){
								$.messager.alert("提醒","抱歉，你选择的"+dataRow.id+" 已经为红冲。<br/>请选择相应的客户应付费用。");
								return false;
							}
							customerCostPayableAEVDailog('客户应付费用红冲【新增】', url=url+"?id="+dataRow.id);
						}
					},
					</security:authorize>
					<security:authorize url="/journalsheet/customercost/customercostRemoveHChongBtn.do">
					{
						id:'button-id-removehc',
						name:'撤销红冲 ',
						iconCls:'button-oppaudit',
						handler:function(){
							var dataRow=$("#customerCostPayable-detail-table").datagrid('getSelected');
							if(dataRow==null || dataRow.id==null || $.trim(dataRow.id)==""){
								$.messager.alert("提醒","请选择相应的红冲!");
								return false;
							}
							if(dataRow.billtype!='2'){
								$.messager.alert("提醒","抱歉，单据类型为贷的客户应付费用才能撤销红冲。");
								return false;
							}
							if(dataRow.hcflag !="1" && dataRow.hcflag !="2"){
								$.messager.alert("提醒","抱歉，你选择的"+dataRow.id+" 不为红冲单据。<br/>请选择相应的红冲单据。");
								return false;
							}
							$.messager.confirm("提醒","是否撤销客户应付费用红冲?",function(r){
								if(r){
									$.ajax({   
							            url :'journalsheet/customercost/removeCustomerCostPayableHC.do',
							            type:'post',
							            dataType:'json',
							            data:{id:dataRow.id},
							            success:function(json){
							            	if(json.flag==true){
	           				            		$.messager.alert("提醒","撤销客户应付费用红冲成功!");
	           				            		$("#customerCostPayable-detail-query-btn-List").trigger('click');
	           				            	}
	           				            	else{
	           				                	if(json.msg){
	           				            			$.messager.alert("提醒","撤销客户应付费用红冲失败!"+json.msg);
	           				                	}else{
	           				            			$.messager.alert("提醒","撤销客户应付费用红冲失败!");
	           				                	}
	           				            	}
							            }
									});
								}
							});
						}
					},
					</security:authorize>
					<security:authorize url="/journalsheet/customercost/customercostDelBtn.do">
					{
						id:'button-id-delete',
						name:'删除',
						iconCls:'button-delete',
						handler:function(){
							var rows =  $("#customerCostPayable-detail-table").datagrid('getChecked');
							if(rows==null || rows.length==0){
								$.messager.alert("提醒","请选择相应的客户应付费用!");
								return false;
							}
							var idarrs=new Array();
							var errorIdarr=new Array();
							if(null !=rows && rows.length>0){
					    		for(var i=0;i<rows.length;i++){
					    			if(rows[i].id && rows[i].id!=""){
						    			if(rows[i].sourcefrom=='0' && rows[i].isbegin=='0' && rows[i].hcflag=='0' ){								    		
									    	idarrs.push(rows[i].id);
						    			}else{
						    				errorIdarr.push(rows[i].id);
						    			}
						    		}
					    		}
							}
							if(errorIdarr.length>0){
								$.messager.alert("提醒","手工录入且不为期初且未红冲的数据才能删除，<br/>下列不能被删除："+errorIdarr.join(","));
								return false;
							}
							$.messager.confirm("提醒","是否确认删除客户应付费用?",function(r){
								if(r){
				  				loading();
				  				$.ajax({
							        type: 'post',
							        cache: false,
							        url: 'journalsheet/customercost/deleteCustomerCostPayableMore.do',
							        data: {idarrs:idarrs.join(",")},
									dataType:'json',
							        success:function(json){
							        	loaded();
							        	if(json.flag==true){
						  					$.messager.alert("提醒", "删除成功数："+ json.isuccess +"<br />删除失败数："+ json.ifailure );							  					
											$("#customerCostPayable-detail-table").datagrid('reload');
											$("#customerCostPayable-detail-table").datagrid('clearSelections');	
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
					<security:authorize url="/journalsheet/customercost/customercostExportBtn.do">
					{
						id:'button-export-excel',
						name:'导出',
						iconCls:'button-export',
						handler: function(){
							var rows =  $("#customerCostPayable-detail-table").datagrid('getChecked');

							//查询参数直接添加在url中         
				    		var idarrs=new Array();
				    		if(null !=rows && rows.length>0){
					    		for(var i=0;i<rows.length;i++){
						    		if(rows[i].id && rows[i].id!=""){
							    		idarrs.push(rows[i].id);
						    		}
					    		}
				    		}
							$("#customerCostPayable-detail-buttons-exportclick").Excel('export',{
								queryForm: "#customerCostPayable-detail-form-ListQuery", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
						 		type:'exportUserdefined',
						 		name:'客户应付费用列表',
						 		fieldParam:{idarrs:idarrs.join(",")},
						 		url:'journalsheet/customercost/exportCustomerCostPayableData.do'
							});
							$("#customerCostPayable-detail-buttons-exportclick").trigger("click");
						}
					},
					</security:authorize>
					<security:authorize url="/journalsheet/customercost/customercostImportBtn.do">
					{
						id:'button-import-excel',
						name:'导入',
						iconCls:'button-import',
						handler: function(){
							var importparam="批量导入客户应付费用表<br/>";
							importparam=importparam+"<a href=\"basefiles/exceltemplet/CustomerCostPayableTempletSample.xls\">"+"点击下载导入模板样式</a><br/><br/>";
							$("#customerCostPayable-detail-buttons-importclick").Excel('import',{
								type:'importUserdefined',
								version:'1',
								importparam:importparam,
								url:'journalsheet/customercost/importCustomerCostPayableData.do',
								onClose: function(){ //导入成功后窗口关闭时操作，
									$("#customerCostPayable-detail-table").datagrid('reload');
									$("#customerCostPayable-detail-table").datagrid('clearSelections');
								}
							});
							$("#customerCostPayable-detail-buttons-importclick").trigger("click");
						}
					},
					</security:authorize>
					<security:authorize url="/erpconnect/addCustomerCostPayableAccountVouch.do">
					{
						id: 'button-account',
						name: '生成凭证',
						iconCls: 'button-audit',
						handler: function () {
							var rows = $("#customerCostPayable-detail-table").datagrid('getChecked');
							if (rows == null || rows.length == 0) {
								$.messager.alert("提醒", "请选择至少一条记录");
								return false;
							}
							var ids = "";
							for (var i = 0; i < rows.length; i++) {
								if(rows[i].billtype=='1'){
									$.messager.alert("提醒", "请选择类型为贷的单据");
									return false;
								}
								if (i == 0) {
									ids = rows[i].id;
								} else {
									ids += "," + rows[i].id;
								}
							}
							$("#customerCostPayable-account-dialog").dialog({
								title: '客户费用凭证',
								width: 400,
								height: 260,
								closed: false,
								modal: true,
								cache: false,
								href: 'erpconnect/showCustomerCostPayableAccountVouchPage.do',
								onLoad: function () {
									$("#customerCostPayable-ids").val(ids);
								}
							});
						}
					},
					</security:authorize>
					{}		
				],
	 			model:'bill',
				type:'list',
				datagrid:'customerCostPayable-detail-table',
				tname:'t_js_customercost_payable',
				id:''
  		});
    	 
    	 
    	 $("#customerCostPayable-detail-query-expensesort").widget({
    		 referwid:'RT_T_BASE_FINANCE_EXPENSES_SORT_1',
    		 width:130,
    		 singleSelect:true
    	 });
    	 $("#customerCostPayable-detail-query-applyuserid").widget({
    		 referwid:'RT_T_SYS_USER',
    		 width:212,
    		 singleSelect:true
    	 });
    	 $("#customerCostPayable-detail-query-applydeptid").widget({
    		 referwid:'RT_T_SYS_DEPT',
    		 width:130,
    		 onlyLeafCheck:false,
    		 singleSelect:true
    	 });
    	 $("#customerCostPayable-detail-query-supplierid").supplierWidget({
    		 singleSelect:true,
 			 width:328
    	 });
    	 $("#customerCostPayable-detail-query-customerid").customerWidget({
    		 singleSelect:true,
    		 isall:true,
 			 width:200
    	 });
    	 $("#customerCostPayable-detail-query-btn-List").click(function(){
    		//高级查询
   			//var queryJSON = $("#customerCostPayable-detail-form-ListQuery").serializeJSON();
   			//$("#customerCostPayable-detail-table").datagrid('load', queryJSON);
   			
   			//把form表单的name序列化成JSON对象
      		var queryJSON = $("#customerCostPayable-detail-form-ListQuery").serializeJSON();
      		//调用datagrid本身的方法把JSON对象赋给queryParams 即可进行查询
      		$("#customerCostPayable-detail-table").datagrid({
      			url: 'journalsheet/customercost/showCustomerCostPayableDetailList.do',
      			pageNumber:1,
				queryParams:queryJSON
      		}).datagrid("columnMoving");
    	 });
    	 $("#customerCostPayable-detail-query-btn-reloadList").click(function(){
    		 $("#customerCostPayable-detail-query-expensesort").widget("clear");
        	 $("#customerCostPayable-detail-query-applyuserid").widget("clear");
        	 $("#customerCostPayable-detail-query-supplierid").supplierWidget('clear');
        	 $("#customerCostPayable-detail-query-customerid").customerWidget('clear');
    		 $("#customerCostPayable-detail-form-ListQuery").form("reset");
			 var queryJSON = $("#customerCostPayable-detail-form-ListQuery").serializeJSON();
			 $("#customerCostPayable-detail-table").datagrid('loadData',{total:0,rows:[]});
    	 });
    	 
    	 $("#customerCostPayable-detail-query-fastfilter").change(function(){
    		var val=$(this).val() || "";
    		if(val!=""){
    			$("#customerCostPayable-detail-query-sourcefrom").val("");
    			$("#customerCostPayable-detail-query-isbegin").val("");
    			$("#customerCostPayable-detail-query-ishcflag").val("");
    		}
    	 });
     });
     function countTotalAmount(){
 		var rows =  $("#customerCostPayable-detail-table").datagrid('getChecked');
 		var lendamount = 0;
 		var payamount = 0;
 		for(var i=0;i<rows.length;i++){
 			lendamount = Number(lendamount)+Number(rows[i].lendamount == undefined ? 0 : rows[i].lendamount);
 			payamount = Number(payamount)+Number(rows[i].payamount == undefined ? 0 : rows[i].payamount);
 		}
 		var footerrows = [{oaid:'选中金额',lendamount:lendamount,payamount:payamount}];
 		if(null != footerobject){
 			footerrows.push(footerobject);
 		}
 		$("#customerCostPayable-detail-table").datagrid("reloadFooter",footerrows);
 	}

        function viewOa(id) {

            top.addTab('act/workViewPage.do?processid=' + id, '工作查看');
        }
        
		function customerCostPayableAEVDailog(title, url,onLoadFunc){
	    	
	    	$('<div id="customerCostPayable-dialog-detail-content"></div>').appendTo('#customerCostPayable-dialog-detail');
	   		$('#customerCostPayable-dialog-detail-content').dialog({  
			    title: title,  
			    width: 490,
			    height: 330,
			    closed: true,
			    cache: false,  
			    href: url,
				maximizable:true,
				resizable:true,  
			    modal: true, 
			    onLoad:function(){
			    	if(onLoadFunc!=null && typeof(onLoadFunc) == "function" ){
			    		onLoadFunc();
			    	}
	   			},
	    		onClose:function(){
			    	$('#customerCostPayable-dialog-detail-content').dialog("destroy");
			    }
			});
			$('#customerCostPayable-dialog-detail-content').dialog('open');
	   	}
	    
	    function closeCustomerCostPayableAEVDailog(){
	    	var $detailOperDialog=$("#customerCostPayable-dialog-detail-content");
  			if($detailOperDialog.size()>0){
  				try{
  					$detailOperDialog.dialog("close");
  				}catch(e){
  					
  				}
  			}	
	    }

    	var customerCostPayable_AjaxConn = function (Data, Action, Str) {
    		if(null != Str && "" != Str){
    			loading(Str);
    		}
		   var MyAjax = $.ajax({
		        type: 'post',
		        cache: false,
		        url: Action,
		        data: Data,
		        async: false,
		        success:function(data){
		        	loaded();
		        }
		    });
		    return MyAjax.responseText;
		}
     	function showCustomerCostPayableView(id){
     		customerCostPayableAEVDailog("客户应付费用"+id+"【详情】", 'journalsheet/customercost/showCustomerCostPayableViewPage.do?id='+id);
     	}
    </script>
  </body>
</html>
