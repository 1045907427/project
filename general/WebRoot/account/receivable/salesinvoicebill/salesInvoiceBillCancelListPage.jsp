<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售开票回退列表页面</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<div data-options="region:'north',border:false">
    		<div class="buttonBG" id="account-buttons-salesInvoiceBillPage" style="height:26px;"></div>
    	</div>
    	<div data-options="region:'center'">
    		<table id="account-datagrid-salesInvoiceBillPage" data-options="border:false"></table>
    	</div>
    </div>
    <div id="account-datagrid-toolbar-salesInvoiceBillPage">
    	<form action="" id="account-form-query-salesInvoiceBillPage" method="post">
    		<table class="querytable">
    			<tr>
    				<td>业务日期:</td>
    				<td><input type="text" name="businessdate1" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /> 到 <input type="text" name="businessdate2" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
    				<td>编&nbsp;号:</td>
    				<td><input type="text" name="id" style="width: 140px;"/></td>
    			</tr>
    			<tr>
    				<td>客&nbsp;&nbsp;户:</td>
    				<td><input id="account-query-customerid" type="text" name="chlidcustomerid" style="width: 225px;"/></td>
    				<td>总&nbsp;店:</td>
    				<td><input id="account-query-headcustomerid" type="text" name="pcustomerid"/></td>
    				</tr>
    			<tr>
                    <td>销售部门:</td>
                    <td><input id="account-query-salesdept" type="text" name="salesdept" style="width: 225px;"/></td>
                    <td>发票号:</td>
                    <td><input type="text" name="invoiceno" style="width: 140px;"/></td>
    				<td colspan="2">
                        <input type="hidden" name="status" value="5"/>
                        <a href="javaScript:void(0);" id="account-queay-salesInvoiceBill" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="account-reload-salesInvoiceBill" class="button-qr">重置</a>
						<%--<span id="account-query-advanced-salesInvoiceBill"></span>--%>
    				</td>
    			</tr>
    		</table>
    	</form>
    </div>    
    <div style="display:none">
	    <div id="account-invoice-dialog-printview" class="easyui-dialog" data-options="closed:true">
	    	<table>
	    			<tr>
	    				<td>是否负值转为折扣:</td>
	    				<td>
	    					<select id="printview-discount">
	    						<option value="0">否</option>
	    						<option value="1">是</option>
	    					</select>
	    				</td>
	    			</tr>
	    	</table>
	    </div>
	    <div id="account-invoice-dialog-print" class="easyui-dialog" data-options="closed:true">
	    	<table>
	    			<tr>
	    				<td>是否负值转为折扣:</td>
	    				<td>
	    					<select id="print-discount">
	    						<option value="0">否</option>
	    						<option value="1">是</option>
	    					</select>
	    				</td>
	    			</tr>
	    	</table>
	    </div>
    </div>
    <div id="account-panel-relation-upper"></div>
    <div id="account-panel-sourceQueryPage"></div>
     <script type="text/javascript">
     	var initQueryJSON = $("#account-form-query-salesInvoiceBillPage").serializeJSON();
    	$(function(){
    		//按钮
			$("#account-buttons-salesInvoiceBillPage").buttonWidget({
				initButton:[
					{},
					<security:authorize url="/account/receivable/showSalesInvoiceBillViewPage.do">
						{
							type: 'button-view',
							handler: function(){
								var con = $("#account-datagrid-salesInvoiceBillPage").datagrid('getSelected');
								if(con == null){
									$.messager.alert("提醒","请选择一条记录");
									return false;
								}	
								top.addOrUpdateTab('account/receivable/showSalesInvoiceBillEditPage.do?id='+ con.id, "销售开票查看");
							}
						},
					</security:authorize>
					<security:authorize url="/account/receivable/salesInvoiceBillDelBtn.do">
						{
							type: 'button-delete',
							handler: function(){
								var rows = $("#account-datagrid-salesInvoiceBillPage").datagrid('getChecked');
								if(rows.length == 0){
									$.messager.alert("提醒","请勾选要删除的记录!");
									return false;
								}
								$.messager.confirm("提醒","确定删除？",function(r){
									if(r){
										var ids = "";
										for(var i=0;i<rows.length;i++){
											if(ids == ""){
												ids = rows[i].id;
											}else{
												ids += "," + rows[i].id;
											}
										}
										$.ajax({   
								            url :'account/receivable/salesInvoiceBillMutiDelete.do?ids='+ids,
								            type:'post',
								            dataType:'json',
								            success:function(json){
								            	loaded();
								            	var msg = "";
								            	if(json.sucids != ""){
								            		msg = "删除开票"+json.sucids+"成功!";
								            	}
								            	if(json.unsucids != ""){
								            		if(msg == ""){
								            			msg = "删除开票"+json.unsucids+"失败";
								            		}else{
								            			msg += "<br />" + "删除开票"+json.unsucids+"失败";
								            		}
								            	}
								            	if(msg != ""){
								            		$.messager.alert("提醒",msg);
								            	}
								            	$("#account-datagrid-salesInvoiceBillPage").datagrid("reload");
								            },
								            error:function(){
								            	loaded();
								            	$.messager.alert("错误", "删除出错");
								            }
								        });
									}
								});
							}
						},
					</security:authorize>
                    {
                        type: 'button-commonquery',
                        attr:{
                            name:'t_account_sales_invoicebill',
                            datagrid:'account-datagrid-salesInvoiceBillPage',
                            plain:true
                        }
                    },

                    {}
				],
				model: 'bill',
				type: 'list',
				tname: 't_account_allocate_enter'
			});
			var salesInvoiceBillJson = $("#account-datagrid-salesInvoiceBillPage").createGridColumnLoad({
				name :'t_account_sales_invoice',
				frozenCol : [[
							  {field:'idok',checkbox:true,isShow:true}
			    			]],
				commonCol : [[
							  {field:'id',title:'编号',width:125,sortable:true},
							  {field:'invoiceno',title:'发票号',width:80,sortable:true},
							  {field:'businessdate',title:'业务日期',width:80,sortable:true},
							  {field:'customerid',title:'客户编码',width:60,sortable:true},
							  {field:'customername',title:'客户名称(总店)',width:100,isShow:true},
							  {field:'chlidcustomername',title:'门店客户名称',width:100,isShow:true,hidden:true,
								  formatter:function(value,rowData,rowIndex){
					        		return "<span title=\""+rowData.chlidcustomername+"\">"+rowData.chlidcustomername+"</span>";
					        	  }  
							  },
							  {field:'handlerid',title:'对方经手人',width:80,sortable:true,hidden:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return rowData.handlername;
					        	}
							  },
							  {field:'salesdept',title:'销售部门',width:100,sortable:true,hidden:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return rowData.salesdeptname;
					        	}
							  },
							  {field:'salesuser',title:'客户业务员',width:80,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return rowData.salesusername;
					        	}
							  },
							  {field:'sourceid',title:'来源单据编号',width:130,sortable:true},
							  /**
							  {field:'isdiscount',title:'是否折扣',width:60,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return getSysCodeName("yesorno",value);
					        	}
							  },**/
//							  {field:'customeramount',title:'账户余额',resizable:true,sortable:true,align:'right',isShow:true,
//							  	formatter:function(value,rowData,rowIndex){
//					        		return formatterMoney(value);
//					        	}
//							  },
							  {field:'taxamount',title:'含税总金额',resizable:true,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'notaxamount',title:'未税总金额',width:70,sortable:true,align:'right',hidden:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'addusername',title:'制单人',width:80,sortable:true,hidden:true},
							  {field:'addtime',title:'制单时间',width:80,sortable:true,hidden:true},
							  {field:'stopusername',title:'中止人',width:80,hidden:true,sortable:true},
							  {field:'stoptime',title:'中止时间',width:80,hidden:true,sortable:true},
							  {field:'status',title:'状态',width:60,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return getSysCodeName("status",value);
					        	}
							  },
							  {field:'printtip',title:'打印提示',width:60,align:'left',isShow:true,
		    						formatter:function(value,row,index){
					        			if(row.status=='3'){
						        			return "可打印";
					        			}
						        	}
						  	   },
							   {field:'printtimes',title:'打印次数',align:'center',width:60},
							  {field:'remark',title:'备注',width:80,sortable:true}
				             ]]
			});
			$("#account-datagrid-salesInvoiceBillPage").datagrid({ 
		 		authority:salesInvoiceBillJson,
		 		frozenColumns: salesInvoiceBillJson.frozen,
				columns:salesInvoiceBillJson.common,
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
				url: 'account/receivable/getSalesInvoiceBillData.do',
				queryParams:initQueryJSON,
				toolbar:'#account-datagrid-toolbar-salesInvoiceBillPage',
				onDblClickRow:function(rowIndex, rowData){
					top.addOrUpdateTab('account/receivable/showSalesInvoiceBillEditPage.do?id='+ rowData.id, "销售开票查看");
				}
			}).datagrid("columnMoving");
			$("#account-query-customerid").customerWidget({
				isall:true,
				isdatasql:false,
				singleSelect:true
			});
			$("#account-query-headcustomerid").widget({
				referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
	    		width:140,
				view:true,
				singleSelect:true
			});
			$("#account-query-salesdept").widget({
				name:'t_account_sales_invoice',
	    		width:225,
				col:'salesdept',
				view:true,
				singleSelect:true
			});
			//通用查询组建调用
			$("#account-query-advanced-salesInvoiceBill").advancedQuery({
		 		name:'t_account_sales_invoicebill',
		 		datagrid:'account-datagrid-salesInvoiceBillPage',
		 		plain:true
			});
			
			//回车事件
			controlQueryAndResetByKey("account-queay-salesInvoiceBill","account-reload-salesInvoiceBill");
			
			//查询
			$("#account-queay-salesInvoiceBill").click(function(){
	       		var queryJSON = $("#account-form-query-salesInvoiceBillPage").serializeJSON();
	       		$("#account-datagrid-salesInvoiceBillPage").datagrid("load",queryJSON);
			});
			//重置
			$("#account-reload-salesInvoiceBill").click(function(){
				$("#account-query-customerid").customerWidget("clear");
				$("#account-query-salesdept").widget("clear");
                $("#account-query-headcustomerid").widget('clear');
				$("#account-form-query-salesInvoiceBillPage")[0].reset();
				var queryJSON = $("#account-form-query-salesInvoiceBillPage").serializeJSON();
	       		$("#account-datagrid-salesInvoiceBillPage").datagrid("load",queryJSON);
			});
    	});
    	function reloadSalesInvoiceBillList(){
    		$("#account-queay-salesInvoiceBill-status").val("2");
       		var queryJSON = $("#account-form-query-salesInvoiceBillPage").serializeJSON();
       		$("#account-datagrid-salesInvoiceBillPage").datagrid("load",queryJSON);
    	}
    </script>
  </body>
</html>
