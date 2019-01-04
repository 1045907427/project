<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>采购发票应付款冲差列表页面</title>
    <%@include file="/include.jsp" %>   
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<div data-options="region:'north',border:false">
    		<div class="buttonBG" id="account-buttons-purchaseInvoicePushPage" style="height:26px;"></div>
    	</div>
    	<div data-options="region:'center'">
    		<table id="account-datagrid-purchaseInvoicePushPage" data-options="border:false"></table>
    	</div>
    </div>
    <div id="account-datagrid-toolbar-purchaseInvoicePushPage">
    	<form action="" id="account-form-query-purchaseInvoicePushPage" method="post">
    		<table class="querytable">
    			<tr>
    				<td>业务日期:</td>
    				<td><input type="text" name="businessdate1" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /> 到 <input type="text" name="businessdate2" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
    				<td>冲差类型:</td>
    				<td>
    					<input id="account-query-pushtype" type="text" name="pushtype" style="width: 135px;"/>
    				</td>
                    <td>状态:</td>
                    <td><select name="isClose" style="width:163px;"><option></option><option value="0" selected="selected">未关闭</option><option value="1">已关闭</option></select></td>
                </tr>
    			<tr>
    				<td>供 应 商:</td>
    				<td><input id="account-query-supplierid" type="text" name="supplierid" style="width:225px;"/></td>
                    <td>采购发票号:</td>
                    <td><input type="text" name="invoiceid" style="width:135px;"/></td>
                    <td rowspan="3" colspan="2" class="tdbutton">
                        <a href="javaScript:void(0);" id="account-queay-purchaseInvoicePushPage" class="button-qr">查询</a>
                        <a href="javaScript:void(0);" id="account-reload-purchaseInvoicePushPage" class="button-qr">重置</a>
                        <%--<security:authorize url="/account/payable/purchaseInvoicePushExport.do">--%>
                        <%--<a href="javaScript:void(0);" id="report-button-purchaseInvoicePushExport" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>--%>
                        <%--</security:authorize>--%>
                        <span id="account-query-advanced-purchaseInvoicePushPage"></span>
                    </td>
    			</tr>

    		</table>
    	</form>
    </div>
    <div id="account-panel-purchaseInvoicePushPage-addpage"></div>
     <script type="text/javascript">
     	var initQueryJSON = $("#account-form-query-purchaseInvoicePushPage").serializeJSON();
    	$(function(){
    		//按钮
			$("#account-buttons-purchaseInvoicePushPage").buttonWidget({
				initButton:[
					{},
					<security:authorize url="/account/payable/deletePurchaseInvoicePush.do">
						{
							type: 'button-delete',
							handler: function(){
								var con = $("#account-datagrid-purchaseInvoicePushPage").datagrid('getSelected');
								if(con == null){
									$.messager.alert("提醒","请选择一条记录");
									return false;
								}	
								$.messager.confirm("提醒","是否删除该客户应收款冲差单？",function(r){
									if(r){
										loading("删除中..");
										$.ajax({   
								            url :'account/payable/deletePurchaseInvoicePush.do?id='+con.invoiceid,
								            type:'post',
								            dataType:'json',
								            success:function(json){
								            	loaded();
								            	if(json.flag){
								            		$.messager.alert("提醒","删除成功");
			    									$("#account-datagrid-purchaseInvoicePushPage").datagrid("reload");
								            	}else{
								            		$.messager.alert("提醒", "删除失败");
								            	}
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
                    <security:authorize url="/account/payable/purchaseInvoicePushExport.do">
                    {
                        type: 'button-export',
                        attr: {
                            queryForm: "#account-form-query-purchaseInvoicePushPage", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
                            type:'exportUserdefined',
                            name:'采购发票冲差列表',
                            url:'account/payable/exportPurchaseInvoicePushData.do'
                        }
                    },
                    </security:authorize>
                    {
                        type: 'button-commonquery',
                        attr:{
                            //查询针对的表
                            name:'t_account_purchase_invoice_push',
                            //查询针对的表格id
                            datagrid:'account-datagrid-purchaseInvoicePushPage'
                        }
                    },
		 			{}
				],
				model: 'bill',
				type: 'list',
				tname: 't_account_allocate_enter'
			});
			var purchaseInvoicePushPageJson = $("#account-datagrid-purchaseInvoicePushPage").createGridColumnLoad({
				name :'t_account_purchase_invoice_push',
				frozenCol : [[
			    			]],
				commonCol : [[
							  {field:'invoiceid',title:'采购发票号',width:125,sortable:true},
							  {field:'businessdate',title:'业务日期',width:80,sortable:true},
							  {field:'supplierid',title:'供应商编码',width:70,sortable:true},
							  {field:'suppliername',title:'供应商名称',width:100,isShow:true},
							  {field:'pushtype',title:'冲差类型',width:80,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
							  		return getSysCodeName("paypushtype",value);
					        	}
							  },
							  {field:'brand',title:'品牌名称',width:100,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return rowData.brandname;
					        	}
							  },
							  {field:'amount',title:'冲差金额',resizable:true,sortable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'addusername',title:'制单人',width:80,sortable:true,hidden:true},
							  {field:'addtime',title:'制单时间',width:80,sortable:true,hidden:true},
							  {field:'iswriteoff',title:'核销状态',width:60,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		if(value=='1'){
					        			return "已核销";
					        		}else{
					        			return "未核销";
					        		}
					        	}
							  },
							  {field:'status',title:'状态',width:60,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return getSysCodeName("status",value);
					        	}
							  },
							  {field:'remark',title:'备注',width:80,sortable:true}
				             ]]
			});
			$("#account-datagrid-purchaseInvoicePushPage").datagrid({ 
		 		authority:purchaseInvoicePushPageJson,
		 		frozenColumns: purchaseInvoicePushPageJson.frozen,
				columns:purchaseInvoicePushPageJson.common,
		 		fit:true,
		 		method:'post',
		 		rownumbers:true,
		 		pagination:true,
		 		idField:'id',
		 		sortName:'id',
		 		sortOrder:'desc',
		 		singleSelect:true,
				url: 'account/payable/showPurchaseInvoicePushList.do',
				queryParams:initQueryJSON,
                pageSize:100,
                showFooter:true,
				toolbar:'#account-datagrid-toolbar-purchaseInvoicePushPage',
				onClickRow:function(rowIndex, rowData){
					$("#account-buttons-purchaseInvoicePushPage").buttonWidget("setDataID",{id:rowData.id,state:rowData.status,type:'view'});
				}
			}).datagrid("columnMoving");
			$("#account-query-supplierid").supplierWidget({
				name:'t_account_customer_push_balance',
	    		width:225,
				col:'supplierid',
				view:true,
				singleSelect:true
			});
			$("#account-query-pushtype").widget({
    			name:'t_account_purchase_invoice_push',
	    		width:135,
				col:'pushtype',
				initSelectNull:true,
				singleSelect:true
    		});
			//通用查询组建调用
//			$("#account-query-advanced-purchaseInvoicePushPage").advancedQuery({
//				//查询针对的表
//		 		name:'t_account_purchase_invoice_push',
//		 		//查询针对的表格id
//		 		datagrid:'account-datagrid-purchaseInvoicePushPage'
//			});
			
			//回车事件
			controlQueryAndResetByKey("account-queay-purchaseInvoicePushPage","account-reload-purchaseInvoicePushPage");
			
			//查询
			$("#account-queay-purchaseInvoicePushPage").click(function(){
				//把form表单的name序列化成JSON对象
	       		var queryJSON = $("#account-form-query-purchaseInvoicePushPage").serializeJSON();
	       		$("#account-datagrid-purchaseInvoicePushPage").datagrid("load",queryJSON);
			});
			//重置
			$("#account-reload-purchaseInvoicePushPage").click(function(){
				$("#account-query-supplierid").supplierWidget("clear");
				$("#account-form-query-purchaseInvoicePushPage")[0].reset();
				var queryJSON = $("#account-form-query-purchaseInvoicePushPage").serializeJSON();
	       		$("#account-datagrid-purchaseInvoicePushPage").datagrid("load",queryJSON);
			});
			
			//导出
//			$("#report-button-purchaseInvoicePushExport").Excel('export',{
//				queryForm: "#account-form-query-purchaseInvoicePushPage", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
//		 		type:'exportUserdefined',
//		 		name:'采购发票冲差列表',
//		 		url:'account/payable/exportPurchaseInvoicePushData.do'
//			});
    	});
    </script>
  </body>
</html>
