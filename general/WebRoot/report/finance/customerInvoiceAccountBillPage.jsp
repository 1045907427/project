<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>客户发票对账单</title>
    <%@include file="/include.jsp" %>   
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>  
  </head>
  
  <body>
    	<table id="report-datagrid-invoiceAccountBill"></table>
    	<div id="report-toolbar-invoiceAccountBill" style="padding: 0px">
            <div class="buttonBG">
                <security:authorize url="/report/finance/invoiceAccountBillExport.do">
                    <a href="javaScript:void(0);" id="report-buttons-invoiceAccountBillPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
                </security:authorize>
            </div>
    		<form action="" id="report-query-form-invoiceAccountBill" method="post">
    		<table class="querytable">
    			<tr>
    				<td>业务日期:</td>
    				<td class="tdinput" ><input type="text" id="report-query-businessdate1" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" id="report-query-businessdate2" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
    				<td>编&nbsp;号:</td>
    				<td class="tdinput" ><input type="text" style="width: 140px;" name="id"/></td>
                    <td>发票号:</td>
                    <td class="tdinput" ><input type="text" style="width: 150px;" name="invoiceid"/></td>
    			</tr>
    			<tr>
    				<td>核销日期:</td>
    				<td class="tdinput" ><input type="text" id="report-query-writeoffdate1" name="writeoffdate1" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" id="report-query-writeoffdate2" name="writeoffdate2"  class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
<!--    				<td>单据类型:</td>-->
<!--    				<td>-->
<!--    					<select name="billtype" style="width: 130px;">-->
<!--    						<option></option>-->
<!--    						<option value="1">销售发票</option>-->
<!--    						<option value="2">正常冲差单</option>-->
<!--    					</select>-->
<!--    				</td>-->
    				<input type="hidden" name="billtype" value="1"/>
                    <td>客户名称:</td>
                    <td class="tdinput" ><input type="text" id="report-query-customerid" name="customerid"/></td>

                    <td colspan="2">
    					<a href="javaScript:void(0);" id="report-queay-invoiceAccountBill" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="report-reload-invoiceAccountBill" class="button-qr">重置</a>

    				</td>
    			</tr>
    		</table>
    	</form>
    	</div>
    	<div id="report-invoicelist-dialog"></div>
    	<div id="account-panel-customerPushBanlance"></div>
    	<div id="report-dialog-BillRelateCollection"></div>
    	<div id="account-panel-customerPushBanlance-addpage"></div>
    	<script type="text/javascript">
		var SR_footerobject  = null;
    		var initQueryJSON = $("#report-query-form-invoiceAccountBill").serializeJSON();
    		$(function(){
    			var tableColumnListJson = $("#report-datagrid-invoiceAccountBill").createGridColumnLoad({
					frozenCol : [[
									{field:'idok',checkbox:true,isShow:true}
				    			]],
					commonCol : [[
									{field:'billtype',title:'单据类型',width:80,sortable:true,
								  	formatter:function(value,rowData,rowIndex){
								  		if(value=="1"){
								  			return "销售发票";
								  		}else if(value=="2"){
								  			return "正常冲差单";
								  		}
						        	}
								  },
								  {field:'id',title:'编号',width:130,sortable:true,
								  	formatter:function(value,rowData,rowIndex){
								  		if(null!=value && rowData.customername != "合计" && rowData.customername != "选中合计"){
						        			return '<a href="javascript:showBillPage(\''+rowData.billtype+'\',\''+value+'\');" style="text-decoration:underline">'+value+'</a>';
						        		}
						        	}
								  },
								  {field:'invoiceno',title:'发票号',width:130,sortable:true},
								  {field:'businessdate',title:'业务日期',width:80,sortable:true},
								  {field:'customerid',title:'客户编码',width:60,sortable:true},
								  {field:'customername',title:'客户名称',width:130,isShow:true},
								  {field:'taxamount',title:'应收总金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'allwriteoffamount',title:'核销金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'writeoffamount',title:'实际核销金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'tailamount',title:'尾差金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'writeoffdate',title:'核销日期',sortable:true,width:80},
                                  {field:'writeoffusername',title:'核销人',sortable:true,width:80}
					             ]]
				});
    			$("#report-datagrid-invoiceAccountBill").datagrid({ 
					authority:tableColumnListJson,
			 		frozenColumns: tableColumnListJson.frozen,
					columns:tableColumnListJson.common,
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		singleSelect:false,
			 		checkOnSelect:true,
			 		selectOnCheck:true,
		  	 		pageSize:100,
					sortName:'businessdate',
					sortOrder:'asc',
					toolbar:'#report-toolbar-invoiceAccountBill',
					onDblClickRow:function(rowIndex, rowData){
					$("#report-dialog-BillRelateCollection").dialog({
						    title: '单据关联收款单明细',  
				    		width:800,
				    		height:400,
				    		closed:false,
				    		modal:true,
				    		maximizable:true,
				    		cache:false,
				    		resizable:true,
						    href: 'account/receivable/showBillRelateCollectionListPage.do?billid='+rowData.id+'&billtype='+rowData.billtype
						});
					},
					onLoadSuccess:function(){
						var footerrows = $(this).datagrid('getFooterRows');
						if(null!=footerrows && footerrows.length>0){
							SR_footerobject = footerrows[0];
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
				$("#report-query-customerid").widget({
					referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT_2',
		    		width:140,
					singleSelect:true
				});
				$("#report-query-pcustomerid").widget({
					referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
		    		width:180,
					singleSelect:true
				});
				
				//回车事件
				controlQueryAndResetByKey("report-queay-invoiceAccountBill","report-reload-invoiceAccountBill");
				
				//查询
				$("#report-queay-invoiceAccountBill").click(function(){
					//把form表单的name序列化成JSON对象
		      		var queryJSON = $("#report-query-form-invoiceAccountBill").serializeJSON();
		      		$("#report-datagrid-invoiceAccountBill").datagrid({
		      			url: 'report/finance/showCustomerInvoiceAccountBillData.do',
						queryParams:queryJSON,
		      			pageNumber:1
		      		}).datagrid("columnMoving");
				});
				//重置
				$("#report-reload-invoiceAccountBill").click(function(){
					$("#report-query-customerid").widget("clear");
					$("#report-query-form-invoiceAccountBill").form("reset");
					var queryJSON = $("#report-query-form-invoiceAccountBill").serializeJSON();
		       		$("#report-datagrid-invoiceAccountBill").datagrid({
		      			url: 'report/finance/showCustomerInvoiceAccountBillData.do',
						queryParams:queryJSON,
		      			pageNumber:1
		      		}).datagrid("columnMoving");
				});
				
				$("#report-buttons-invoiceAccountBillPage").Excel('export',{
					queryForm: "#report-query-form-invoiceAccountBill", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:'客户开票对账单',
			 		url:'report/finance/exportInvoiceAccountBillData.do'
				});
				
    		});
    		function countTotalAmount(){
    			var rows =  $("#report-datagrid-invoiceAccountBill").datagrid('getChecked');
        		if(null==rows || rows.length==0){
            		var foot=[];
	    			if(null!=SR_footerobject){
		        		foot.push(SR_footerobject);
		    		}
	    			$("#report-datagrid-invoiceAccountBill").datagrid("reloadFooter",foot);
            		return false;
        		}
    			var taxamount = 0;
        		var writeoffamount = 0;
        		var tailamount=0;
        		
        		for(var i=0;i<rows.length;i++){
        			taxamount = Number(taxamount)+Number(rows[i].taxamount == undefined ? 0 : rows[i].taxamount);
        			writeoffamount = Number(writeoffamount)+Number(rows[i].writeoffamount == undefined ? 0 : rows[i].writeoffamount);
        			tailamount = Number(tailamount)+Number(rows[i].tailamount == undefined ? 0 : rows[i].tailamount);
        		}
        		var foot=[{customername:'选中合计',bankname:'',taxamount:taxamount,writeoffamount:writeoffamount,tailamount:tailamount
            			}];
        		if(null!=SR_footerobject){
            		foot.push(SR_footerobject);
        		}
        		$("#report-datagrid-invoiceAccountBill").datagrid("reloadFooter",foot);
    		}
    		
    		function showBillPage(billtype,id){
    			if(billtype=="1"){
    				top.addOrUpdateTab('account/receivable/showSalesInvoiceViewPage.do?id='+ id, "销售核销查看");
		  		}else if(billtype=="2"){
		  			$('#account-panel-customerPushBanlance-addpage').dialog({  
					    title: '客户应收款冲差修改',  
					    width: 400,  
					    height: 350,  
					    collapsible:false,
					    minimizable:false,
					    maximizable:true,
					    resizable:true,
					    closed: true,  
					    cache: false,  
					    href: 'account/receivable/showCustomerPushBanlanceViewPage.do?id='+id,  
					    modal: true,
					    onLoad:function(){
					    	$("#account-customerPushBanlance-customerid").focus();
					    }
					});
					$('#account-panel-customerPushBanlance-addpage').dialog("open");
		  		}
    		}
    	</script>
  </body>
</html>
