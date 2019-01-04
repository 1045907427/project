<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>客户销售情况流水表</title>
    <%@include file="/include.jsp" %>
	<script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>    
  </head>
  
  <body>
    	<table id="report-datagrid-customerSalesFlow"></table>
    	<form action="" id="report-query-form-customerSalesFlow" method="post">
   			<input type="hidden" name="customerid" value="${customerid }" />
   			<input type="hidden" name="writeoff1" value="1"/>
   		</form>
    	<div id="report-toolbar-customerSalesFlow" class="buttonBG">
    		<security:authorize url="/report/finance/salesUserReceivablePastDueExport.do">
				<a href="javaScript:void(0);" id="report-export-salesuserRPDSalesFlow" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
			</security:authorize>
    	</div>
    	<script type="text/javascript">
    		var initQueryJSON = $("#report-query-form-customerSalesFlow").serializeJSON();
    		$(function(){
    			var tableColumnListJson = $("#report-datagrid-customerSalesFlow").createGridColumnLoad({
					frozenCol : [[]],
					commonCol : [[
								  {field:'businessdate',title:'业务日期',width:80},
								  {field:'customerid',title:'客户编码',width:60},
								  {field:'customername',title:'客户名称',width:100},
								  {field:'salesareaname',title:'销售区域',width:100,hidden:true},
								  {field:'billtype',title:'单据类型',width:60,
								  	formatter:function(value,rowData,rowIndex){
						        		if(value=='1'){
						        			return "发货单";
						        		}else if(value=='2'){
						        			return "直退退货单";
						        		}else if(value=='3'){
						        			return "售后退货单";
						        		}else if(value=='4'){
						        			return "冲差单";
						        		}
						        	}
								  },
								  {field:'id',title:'单据编号',sortable:true,width:130},
								  {field:'goodsid',title:'商品编码',sortable:true,width:60},
								  {field:'goodsname',title:'商品名称',sortable:true,width:120},
								  {field:'barcode',title:'条形码',sortable:true,width:90},
								  {field:'unitname',title:'单位',width:40},
								  {field:'unitnum',title:'数量',width:60,align:'right',
								  	formatter:function(value,rowData,rowIndex){
								  		if(value!=null &&　value!=0){
								  			return formatterBigNumNoLen(value);
								  		}
						        	}
								  },
								  {field:'price',title:'单价',width:60,align:'right',
								  	formatter:function(value,rowData,rowIndex){
								  		if(rowData.billtype !="9" && rowData.billtype !="4" && rowData.isdiscount!="1" && rowData.isdiscount!="2"){
							        		if(parseFloat(value)>parseFloat(rowData.initprice)){
							        			return "<font color='blue' title='原价:"+formatterMoney(rowData.initprice)+"'>"+ formatterMoney(value)+ "</font>";
							        		}
							        		else if(parseFloat(value)<parseFloat(rowData.initprice)){
							        			return "<font color='red' title='原价:"+formatterMoney(rowData.initprice)+"'>"+ formatterMoney(value)+ "</font>";
							        		}
							        		else{
							        			return formatterMoney(value);
							        		}
						        		}
						        	}
								  },
								  {field:'taxamount',title:'金额',align:'right',resizable:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'auxnum',title:'箱数',align:'right',resizable:true,
								  	formatter:function(value,rowData,rowIndex){
								  		var str = "";
								  		if(rowData.unitnum>0){
									  		if(rowData.auxnum!=0){
									  			str = formatterNum(rowData.auxnum)+"箱";
									  		}
									  		if(rowData.auxremainder!=0){
									  			if(rowData.unitname!=null){
									  				str += formatterBigNumNoLen(rowData.auxremainder)+rowData.unitname;
									  			}else{
									  				str += formatterBigNumNoLen(rowData.auxremainder);
									  			}
									  		}
								  		}else if(rowData.unitnum<0){
								  			str = "-";
								  			if(rowData.auxnum!=0){
									  			str += formatterNum(-rowData.auxnum)+"箱";
									  		}
									  		if(rowData.auxremainder!=0){
									  			if(rowData.unitname!=null){
									  				str += formatterBigNumNoLen(-rowData.auxremainder)+rowData.unitname;
									  			}else{
									  				str += formatterBigNumNoLen(-rowData.auxremainder);
									  			}
									  		}
								  		}else if(rowData.unitnum==null){
								  			if(rowData.auxnum>0){
									  			str = formatterNum(rowData.auxnum)+"箱";
									  		}
									  		if(rowData.auxremainder>0){
									  			if(rowData.unitname!=null){
									  				str += formatterBigNumNoLen(rowData.auxremainder)+rowData.unitname;
									  			}else{
									  				str += formatterBigNumNoLen(rowData.auxremainder);
									  			}
									  		}
								  		}
								  		return str;
						        	}
								  },
								  <c:if test="${map.costprice == 'costprice'}">
								      {field:'costprice',title:'成本价',width:60,align:'right',hidden:true,
									  	formatter:function(value,rowData,rowIndex){
							        		return formatterMoney(value);
							        	}
									  },
								  </c:if>
								  <c:if test="${map.costamount == 'costamount'}">
									  {field:'costamount',title:'成本金额',width:80,align:'right',hidden:true,
									  	formatter:function(value,rowData,rowIndex){
							        		return formatterMoney(value);
							        	}
									  },
								  </c:if>
								  {field:'isinvoice',title:'开票状态',align:'right',width:60,
								  	formatter:function(value,rowData,rowIndex){
								  		if(value!=null){
									  		if(value=='0'){
									  			return "未开票";
									  		}else if(value=='1'){
									  			return "已开票";
									  		}
								  		}else{
								  			return "";
								  		}
						        	}
								  },
								  {field:'iswriteoff',title:'核销状态',align:'right',width:60,isShow:true,
								  	formatter:function(value,rowData,rowIndex){
						        		if(value=='1'){
								  			return "已核销";
								  		}else if(value=='0'){
								  			return "未核销";
								  		}
						        	}
								  },
								  {field:'invoicedate',title:'开票日期',width:80},
								  {field:'writeoffdate',title:'核销日期',width:80},
								  {field:'duefromdate',title:'应收日期',width:80},
								  {field:'remark',title:'备注',width:80}
								 
					         ]]
				});
    			$("#report-datagrid-customerSalesFlow").datagrid({ 
					authority:tableColumnListJson,
			 		frozenColumns: tableColumnListJson.frozen,
					columns:tableColumnListJson.common,
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		singleSelect:true,
		  	 		pageSize:100,
					toolbar:'#report-toolbar-customerSalesFlow',
					url:'report/finance/showCustomerSalesFlowList.do',
					queryParams:initQueryJSON,
					rowStyler:function(index,row){
						if(row.isultra == "1"){
							return 'background-color:#DFF1D1';
						}
					}
				}).datagrid("columnMoving");
				
				$("#report-export-salesuserRPDSalesFlow").Excel('export',{
					queryForm: "#report-query-form-customerSalesFlow", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:'按客户：[${customername}]销售情况流水表',
			 		url:'report/finance/exportCustomerSalesFlowData.do'
				});
				
    		});

    	</script>
  </body>
</html>
