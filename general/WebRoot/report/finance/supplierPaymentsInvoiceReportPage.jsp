<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>供应商应付款动态表-开票对账明细</title>
    <%@include file="/include.jsp" %>   
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>
      <style>
          .checkbox1{
              float:left;
              height: 22px;
              line-height: 22px;
          }
          .divtext{
              height:22px;
              line-height:22px;
              float:left;
              display: block;
          }
      </style>
  </head>
  
  <body>
    	<table id="report-datagrid-supplierPaymentsInvoiceReportPage"></table>
    	<div id="report-toolbar-supplierPaymentsInvoiceReportPage" class="buttonBG">
    		<a href="javaScript:void(0);" id="report-advancedQuery-supplierPaymentsInvoiceReportPage" class="easyui-linkbutton" iconCls="button-commonquery" plain="true">查询</a>
    		<security:authorize url="/report/finance/exportSupplierPaymentsInvoiceReportData.do">
    			<a href="javaScript:void(0);" id="report-advancedQuery-exportSupplierPaymentsInvoiceReportData" class="easyui-linkbutton" iconCls="button-export" plain="true" title="[Alt+E]导出">导出</a>
    		</security:authorize>
    	</div>
    	<div style="display: none">
	    	<div id="report-dialog-advancedQueryPage">
	    		<form action="" method="post" id="report-form-advancedQuery">
			  		<table cellpadding="1" cellspacing="1" border="0" style="padding: 10px;">
		    			<%--<tr>
		    				<td>单据类型:</td>
		    				<td>
		    					<select id="report-billtype-advancedQuery" name="billtype" style="width:100px;">
		    						<option value=""></option>
		    						<option value="1">采购进货单</option>
		    						<option value="2">采购退货通知单</option>
		    					</select>
							</td>
		    			</tr> --%>
                            <tr>
                                <td>核&nbsp;&nbsp;销:</td>
                                <td>
                                    <select name="invoicestate" style="width:100px;">
                                        <option value=""></option>
                                        <option value="1">未完成</option>
                                        <option value="2">完成</option>
                                    </select>
                                </td>
                            </tr>
			  			<tr>
		    				<td>开票日期:</td>
		    				<td><input type="text" name="businessdate1" value="${firstDay }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>	    				
		    			</tr>
                            <tr>
                                <td>核销日期:</td>
                                <td><input type="text" name="invoicedate1" value="${firstDay }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" name="invoicedate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
                            </tr>
		    			<tr>
		    				<td>供 应 商:</td>
		    				<td><input id="report-supplier-advancedQuery" type="text" name="supplierid" style="width: 223px;"/></td>
		    			</tr>
		    			<tr>	    				
		    				<td>单据编号:</td>		
		    				<td><input type="text" name="id" style="width:223px;" /></td>
		    			</tr>


		    			<tr>	    				
		    				<td>制 单 人:</td>
		    				<td>
		    					<input id="report-adduserid-advancedQuery" type="text" name="adduserid" style="width: 223px;"/>
							</td>
		    			</tr>
		    			<tr>
		    				<td>小 计 列：</td>
		    				<td>
		    					<label class="divtext"><input type="checkbox" class="paygroupcols checkbox1" value="supplierid"/>供应商</label>
                                <label class="divtext"><input type="checkbox" class="paygroupcols checkbox1" value="buydeptid"/>采购部门</label>
                                <label class="divtext"><input type="checkbox" class="paygroupcols checkbox1" value="businessdate"/>业务日期</label>
		    					<input id="report-advancedQuery-groupcols" type="hidden" name="groupcols"/>
		    				</td>
		    			</tr>
			  		</table>
			  	</form>
	    	</div>
    		<div id="report-invoicelist-dialog"></div>
		</div>
    	<script type="text/javascript">
    		var initQueryJSON = $("#report-query-form-supplierPaymentsInvoiceReportPage").serializeJSON();

        	var SR_footerobject = null;
    		$(function(){
    			var tableColumnListJson = $("#report-datagrid-supplierPaymentsInvoiceReportPage").createGridColumnLoad({
					frozenCol : [[
				    			]],
					commonCol : [[
								  {field:'ck',checkbox:true},
								  {field:'businessdate',title:'开票日期',sortable:true,width:80},
								  {field:'id',title:'单据编号',sortable:true,width:120},
								  {field:'invoiceno',title:'发票号',sortable:true,width:110},
								  {field:'supplierid',title:'供应商编码',sortable:true,width:70},
								  {field:'suppliername',title:'供应商名称',sortable:true,width:250,isShow:true},
								  {field:'buydeptid',title:'采购部门',sortable:true,width:70,
									  formatter:function(value,rowData,rowIndex){
										if(rowData.buydeptname){
											return rowData.buydeptname;
										}
							          }
								  },
								  {field:'taxamount',title:'到货总金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
									  if(value!=0){
						        			return formatterMoney(value);
									  	}else{
										  	var num=Number(value);
										  	return num.toFixed(2);
									  	}
						        	}
								  },
								  {field:'notaxamount',title:'未税总金额',resizable:true,sortable:true,align:'right',hidden:true,
								  	formatter:function(value,rowData,rowIndex){
									  if(value!=0){
						        			return formatterMoney(value);
									  	}else{
										  	var num=Number(value);
										  	return num.toFixed(2);
									  	}
						        	}
								  },
								  {field:'invoiceamount',title:'发票总金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
									  	if(value!=0){
						        			return formatterMoney(value);
									  	}else{
										  	var num=Number(value);
										  	return num.toFixed(2);
									  	}
						        	}
								  },
								  {field:'writeoffamount',title:'核销总金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
									  	if(value!=0){
						        			return formatterMoney(value);
									  	}else{
										  	var num=Number(value);
										  	return num.toFixed(2);
									  	}
						        	}
								  },
								  {field:'tailamount',title:'尾差金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
									  	if(value!=0){
						        			return formatterMoney(value);
									  	}else{
										  	var num=Number(value);
										  	return num.toFixed(2);
									  	}
						        	}
								  },
								  {field:'pushamount',title:'冲差金额',resizable:true,sortable:true,align:'right',
									  	formatter:function(value,rowData,rowIndex){
										  	if(value!=0){
							        			return formatterMoney(value);
										  	}else{
											  	var num=Number(value);
											  	return num.toFixed(2);
										  	}
							        	}
									  },
								  {field:'writeoffdate',title:'核销日期',sortable:true,width:80}
					             ]]
				});
    			$("#report-datagrid-supplierPaymentsInvoiceReportPage").datagrid({ 
			 		authority:tableColumnListJson,
			 		frozenColumns: tableColumnListJson.frozen,
					columns:tableColumnListJson.common,
			 		method:'post',
		  	 		title:'',
		  	 		sortName:'businessdate',
		  	 		sortOrder:'asc',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		pageSize:100,
		  	 		singleSelect:false,
			 		checkOnSelect:true,
			 		selectOnCheck:true,
					toolbar:'#report-toolbar-supplierPaymentsInvoiceReportPage',
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
						var footerrows = $(this).datagrid('getFooterRows');
						if(null!=footerrows && footerrows.length>0){
							SR_footerobject = footerrows[0];
						}
					}
				}).datagrid("columnMoving");
				
    			$("#report-supplier-advancedQuery").widget({
					referwid:'RL_T_BASE_BUY_SUPPLIER',
		    		width:223,
					onlyLeafCheck:false,
					singleSelect:false
				});

        		$("#report-adduserid-advancedQuery").widget({ //制单人参照窗口
        			referwid:'RT_T_SYS_USER',
        			singleSelect:true,
        			width:223,
        			onlyLeafCheck:true
        		});
				//回车事件
				controlQueryAndResetByKey("report-advancedQuery-supplierPaymentsInvoiceReportPage","");

				$("#report-buttons-supplierPaymentsInvoiceReportPagePage").Excel('export',{
					queryForm: "#report-form-advancedQuery", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:'应付款开票对账明细',
			 		url:'report/finance/exportSupplierPaymentsInvoiceReportData.do'
				});
				
				//高级查询
				$("#report-advancedQuery-supplierPaymentsInvoiceReportPage").click(function(){
					$("#report-dialog-advancedQueryPage").dialog({
						maximizable:true,
						resizable:true,
						title: '单据对账明细查询',
					    width: 450,
					    height: 300,
					    closed: false,
					    cache: false,
					    modal: true,
					     buttons:[
							{
								text:'确定',
								handler:function(){
									searchAdvancedQueryForm();
								}
							},
							{
								text:'重置',
								handler:function(){
									$("#report-form-advancedQuery").form("reset");
									$("#report-advancedQuery-groupcols").val("");
							  		$("#report-supplier-advancedQuery").widget('clear');
							  		$("#report-adduserid-advancedQuery").widget('clear');

									$("#report-datagrid-supplierPaymentsInvoiceReportPage").datagrid('loadData',{total:0,rows:[],sortName:'',sortOrder:''});
								}
							}
							],
						onClose:function(){
						}
					});
				});
				$(".paygroupcols").click(function(){
					var cols = "";
					$("#report-advancedQuery-groupcols").val("");
					$(".paygroupcols").each(function(){
						if($(this).attr("checked")){
							if(cols==""){
								cols = $(this).val();
							}else{
								cols += ","+$(this).val();
							}
							$("#report-advancedQuery-groupcols").val(cols);
						}
					});
				});
    		});

    		function searchAdvancedQueryForm(){
				var gcols=$("#report-advancedQuery-groupcols").val();
      			var adQueryJSON = $("#report-form-advancedQuery").serializeJSON();
      			$("#report-datagrid-supplierPaymentsInvoiceReportPage").datagrid({
          			url:'report/finance/showSupplierPaymentsInvoiceReportData.do',
     				queryParams:adQueryJSON,
          			pageNumber:1
          		}).datagrid("columnMoving");
          		$("#report-advancedQuery-exportSupplierPaymentsInvoiceReportData").Excel('export',{
    				queryForm: "#report-form-advancedQuery",
    		 		type:'exportUserdefined',
    		 		name:'应付款开票对账明细',
    		 		url:'report/finance/exportSupplierPaymentsInvoiceReportData.do'
    			});
          		$("#report-dialog-advancedQueryPage").dialog('close');
      		}

        	//计算勾选销售发票合计
        	function countTotalAmount(){
        		var rows =  $("#report-datagrid-supplierPaymentsInvoiceReportPage").datagrid('getChecked');
        		if(null==rows || rows.length==0){
	    			var foot=[];
	    			if(null!=SR_footerobject){
		        		foot.push(SR_footerobject);
		    		}
	    			$("#report-datagrid-supplierPaymentsInvoiceReportPage").datagrid("reloadFooter",foot);
	        		return false;
	    		}
        		var taxamount = 0;
        		var notaxamount = 0;
        		var invoiceamount = 0;
        		var writeoffamount = 0;
        		var tailamount = 0;
        		var pushamount=0;
        		for(var i=0;i<rows.length;i++){
        			taxamount = Number(taxamount)+Number(rows[i].taxamount == undefined ? 0 : rows[i].taxamount);
        			notaxamount = Number(notaxamount)+Number(rows[i].notaxamount == undefined ? 0 : rows[i].notaxamount);
        			invoiceamount = Number(invoiceamount)+Number(rows[i].invoiceamount == undefined ? 0 : rows[i].invoiceamount);
        			writeoffamount = Number(writeoffamount)+Number(rows[i].writeoffamount == undefined ? 0 : rows[i].writeoffamount);
        			tailamount = Number(tailamount)+Number(rows[i].tailamount == undefined ? 0 : rows[i].tailamount);
        			pushamount = Number(pushamount)+Number(rows[i].pushamount == undefined ? 0 : rows[i].pushamount);
        		}
        		var foot = [{
   	    				id:'选中金额',
   	    				taxamount:taxamount,
   	    				notaxamount:notaxamount,
   	    				invoiceamount:invoiceamount,
   	    				writeoffamount:writeoffamount,
   	    				tailamount:tailamount,
   	    				pushamount : pushamount
       				}];
       			if(null!=SR_footerobject){
	        		foot.push(SR_footerobject);
	    		}
       			$("#report-datagrid-supplierPaymentsInvoiceReportPage").datagrid("reloadFooter",foot);
        	}
    	</script>
  </body>
</html>
