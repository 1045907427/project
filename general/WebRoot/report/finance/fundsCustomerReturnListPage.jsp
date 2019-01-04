<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>分客户资金回笼表</title>
    <%@include file="/include.jsp" %>  
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>   
  </head>
  
  <body>
    	<table id="report-datagrid-fundsCustomerReturn"></table>
    	<div id="report-toolbar-fundsCustomerReturn">
    		<form action="" id="report-query-form-fundsCustomerReturn" method="post">
	    		<table>
	    			<tr>
	    				<td>业务日期:</td>
	    				<td><input type="text" id="report-query-businessdate1" name="businessdate1" value="${today }" style="width:100px;" class="Wdate" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /> 到 <input type="text" id="report-query-businessdate2" name="businessdate2" value="${today }" class="Wdate" style="width:100px;" onclick="WdatePicker({'dateFmt':'yyyy-MM-dd',maxDate:'${today }'})" /></td>
	    				<td>客户名称:</td>
	    				<td><input type="text" id="report-query-customerid" name="customerid"/></td>
	    			</tr>
	    			<tr>
	    				<td>总店名称:</td>
	    				<td><input type="text" id="report-query-pcustomerid" name="pcustomerid"/></td>
	    				<td colspan="2">
	    					<a href="javaScript:void(0);" id="report-queay-fundsCustomerReturn" class="easyui-linkbutton" iconCls="icon-search" plain="true" title="[Alt+Q]查询">查询</a>
							<a href="javaScript:void(0);" id="report-reload-fundsCustomerReturn" class="easyui-linkbutton" iconCls="icon-reload" plain="true" title="[Alt+R]重置">重置</a>
	    					<security:authorize url="/report/finance/fundsCustomerReturnExport.do">
								<a href="javaScript:void(0);" id="report-buttons-fundsCustomerReturnPage" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
							</security:authorize>
	    				</td>
	    			</tr>
	    		</table>
	    	</form>
    	</div>
    	<div id="report-fundsCustomer-detail-dialog"></div>
    	<script type="text/javascript">
    		var initQueryJSON = $("#report-query-form-fundsCustomerReturn").serializeJSON();
			var SR_footerobject = null;
    		$(function(){
    			$("#report-datagrid-fundsCustomerReturn").datagrid({ 
					columns:[[
					          {field:'idok',checkbox:true,isShow:true},
								  {field:'customerid',title:'客户编码',width:60},
								  {field:'customername',title:'客户名称',width:210},
								  {field:'pcustomerid',title:'总店名称',width:60,sortable:true,
								  	formatter:function(value,rowData,rowIndex){
								  		if(rowData.customerid!=rowData.pcustomerid){
						        			return rowData.pcustomername;
						        		}else{
						        			return "";
						        		}
						        	}
								  },
								  {field:'salesarea',title:'销售区域',sortable:true,width:60,
								  	formatter:function(value,rowData,rowIndex){
						        		return rowData.salesareaname;
						        	}
								  },
								  {field:'sendamount',title:'发货金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'directreturnamount',title:'直退金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'checkreturnamount',title:'售后退货金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'returntaxamount',title:'退货金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'pushbalanceamount',title:'冲差金额',resizable:true,sortable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'saleamount',title:'销售总额',resizable:true,align:'right',sortable:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'salecostamount',title:'销售成本',resizable:true,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'salemarginamount',title:'销售毛利额',resizable:true,align:'right',sortable:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'salerate',title:'销售毛利率',width:70,align:'right',
								  	formatter:function(value,rowData,rowIndex){
						        		if(null != value && "" != value){
								  			return formatterMoney(value)+'%';
								  		}
						        	}
								  },
								  {field:'withdrawnamount',title:'回笼金额',align:'right',resizable:true,isShow:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'costwriteoffamount',title:'回笼成本',align:'right',resizable:true,isShow:true,sortable:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'withdrawnmarginamount',title:'回笼毛利额',align:'right',resizable:true,isShow:true,sortable:true,
								  	formatter:function(value,rowData,rowIndex){
						        		return formatterMoney(value);
						        	}
								  },
								  {field:'writeoffrate',title:'回笼毛利率',align:'right',width:70,isShow:true,sortable:true,
								  	formatter:function(value,rowData,rowIndex){
						        		if(null != value && "" != value){
								  			return formatterMoney(value)+'%';
								  		}
						        	}
								  },
								  {field:'allunwithdrawnamount',title:'应收款总额',align:'right',resizable:true,isShow:true,sortable:true,
								  	formatter:function(value,rowData,rowIndex){
								  		if(value!=0){
						        			return '<a href="javascript:showDetail(\''+rowData.customerid+'\',0);" style="text-decoration:none">'+formatterMoney(value)+'</a>';
						        		}else{
						        			return formatterMoney(value);
						        		}
						        	}
								  },
								  {field:'unauditamount',title:'未验收应收款',align:'right',resizable:true,isShow:true,sortable:true,
								  	formatter:function(value,rowData,rowIndex){
						        		if(value!=0){
						        			return '<a href="javascript:showDetail(\''+rowData.customerid+'\',0);" style="text-decoration:none">'+formatterMoney(value)+'</a>';
						        		}else{
						        			return formatterMoney(value);
						        		}
						        	}
								  },
								  {field:'auditamount',title:'已验收应收款',align:'right',resizable:true,isShow:true,sortable:true,
								  	formatter:function(value,rowData,rowIndex){
						        		if(value!=0){
						        			return '<a href="javascript:showDetail(\''+rowData.customerid+'\',1);" style="text-decoration:none">'+formatterMoney(value)+'</a>';
						        		}else{
						        			return formatterMoney(value);
						        		}
						        	}
								  },
								  {field:'rejectamount',title:'退货应收款',align:'right',resizable:true,isShow:true,sortable:true,
								  	formatter:function(value,rowData,rowIndex){
						        		if(value!=0){
						        			return '<a href="javascript:showDetail(\''+rowData.customerid+'\',2);" style="text-decoration:none">'+formatterMoney(value)+'</a>';
						        		}else{
						        			return formatterMoney(value);
						        		}
						        	}
								  },
								  {field:'allpushbalanceamount',title:'冲差应收款',align:'right',resizable:true,isShow:true,sortable:true,
								  	formatter:function(value,rowData,rowIndex){
						        		if(value!=0){
						        			return '<a href="javascript:showDetail(\''+rowData.customerid+'\',0);" style="text-decoration:none">'+formatterMoney(value)+'</a>';
						        		}else{
						        			return formatterMoney(value);
						        		}
						        	}
								  }
					         ]],
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
					toolbar:'#report-toolbar-fundsCustomerReturn',
					onDblClickRow:function(rowIndex, rowData){
						var customerid = rowData.customerid;
						var customername = rowData.customername;
						var businessdate1 = $("#report-query-businessdate1").val();
    					var businessdate2 = $("#report-query-businessdate2").val();
    					var url = 'report/finance/showFundsCustomerReturnDetailPage.do?customerid='+customerid+'&businessdate1='+businessdate1+'&businessdate2='+businessdate2+'&customername='+customername;
						$("#report-fundsCustomer-detail-dialog").dialog({
							title:'按客户:['+rowData.customername+']统计',
				    		width:800,
				    		height:400,
				    		closed:true,
				    		modal:true,
				    		maximizable:true,
				    		cache:false,
				    		resizable:true,
				    		maximized:true,
						    href: url
						});
						$("#report-fundsCustomer-detail-dialog").dialog("open");
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
				}).datagrid("tooltip");
				$("#report-query-customerid").widget({
					referwid:'RL_T_BASE_SALES_CUSTOMER',
		    		width:180,
					singleSelect:true
				});
				$("#report-query-pcustomerid").widget({
					referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT',
		    		width:180,
					singleSelect:true
				});
				
				//回车事件
				controlQueryAndResetByKey("report-queay-fundsCustomerReturn","report-reload-fundsCustomerReturn");
				
				//查询
				$("#report-queay-fundsCustomerReturn").click(function(){
					//把form表单的name序列化成JSON对象
		      		var queryJSON = $("#report-query-form-fundsCustomerReturn").serializeJSON();
		      		$("#report-datagrid-fundsCustomerReturn").datagrid({
		      			url: 'report/finance/showBaseSalesWithdrawnData.do?groupcols=customerid',
		      			pageNumber:1,
						queryParams:queryJSON
		      		});
				});
				//重置
				$("#report-reload-fundsCustomerReturn").click(function(){
					$("#report-query-customerid").widget("clear");
					$("#report-query-pcustomerid").widget("clear");
					$("#report-query-form-fundsCustomerReturn").form("reset");
					var queryJSON = $("#report-query-form-fundsCustomerReturn").serializeJSON();
		       		$("#report-datagrid-fundsCustomerReturn").datagrid('loadData',{total:0,rows:[]});
				});
				
				$("#report-buttons-fundsCustomerReturnPage").Excel('export',{
					queryForm: "#report-query-form-fundsCustomerReturn", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:'分客户资金回笼表',
			 		url:'report/finance/exportBaseSalesWithdrawnData.do?groupcols=customerid'
				});
    		});
    		
    		function showDetail(customerid,type){
    			var businessdate1 = $("#report-query-businessdate1").val();
    			var businessdate2 = $("#report-query-businessdate2").val();
    			var url = 'report/finance/showFundsCustomerListPage.do?customerid='+customerid+'&type='+type+'&businessdate1='+businessdate1+'&businessdate2='+businessdate2;
    			$("#report-fundsCustomer-detail-dialog").dialog({
				    title: '分客户销售回单列表',  
		    		width:800,
		    		height:400,
		    		closed:false,
		    		modal:true,
		    		maximizable:true,
		    		cache:false,
		    		resizable:true,
				    href: url
				});
    		}

    		function countTotalAmount(){
    			var rows =  $("#report-datagrid-fundsCustomerReturn").datagrid('getChecked');
        		if(null==rows || rows.length==0){
            		var foot=[];
	    			if(null!=SR_footerobject){
		        		foot.push(SR_footerobject);
		    		}
	    			$("#report-datagrid-fundsCustomerReturn").datagrid("reloadFooter",foot);
            		return false;
        		}
        		var sendamount = 0;
        		var directreturnamount = 0;
        		var checkreturnamount=0;
        		var returntaxamount = 0;
        		var pushbalanceamount =0;
        		var saleamount = 0;
        		var salecostamount = 0;
        		var salemarginamount =0;
        		var withdrawnamount = 0;
        		var costwriteoffamount = 0;
        		var withdrawnmarginamount = 0;
        		var allunwithdrawnamount = 0;
        		var unauditamount = 0;
        		var auditamount = 0;
        		var rejectamount = 0;
        		var allpushbalanceamount = 0;
        		for(var i=0;i<rows.length;i++){
        			sendamount = Number(sendamount)+Number(rows[i].sendamount == undefined ? 0 : rows[i].sendamount);
        			directreturnamount = Number(directreturnamount)+Number(rows[i].directreturnamount == undefined ? 0 : rows[i].directreturnamount);
        			checkreturnamount = Number(checkreturnamount)+Number(rows[i].checkreturnamountmount == undefined ? 0 : rows[i].checkreturnamount);
        			returntaxamount = Number(returntaxamount)+Number(rows[i].returntaxamountmount == undefined ? 0 : rows[i].returntaxamount);
        			pushbalanceamount = Number(pushbalanceamount)+Number(rows[i].pushbalanceamount == undefined ? 0 : rows[i].pushbalanceamount);
        			saleamount = Number(saleamount)+Number(rows[i].saleamount == undefined ? 0 : rows[i].saleamount);
        			salecostamount = Number(salecostamount)+Number(rows[i].salecostamount == undefined ? 0 : rows[i].salecostamount);
        			salemarginamount = Number(salemarginamount)+Number(rows[i].salemarginamount == undefined ? 0 : rows[i].salemarginamount);
        			withdrawnamount = Number(withdrawnamount)+Number(rows[i].withdrawnamount == undefined ? 0 : rows[i].withdrawnamount);
        			costwriteoffamount = Number(costwriteoffamount)+Number(rows[i].costwriteoffamount == undefined ? 0 : rows[i].costwriteoffamount);
        			withdrawnmarginamount = Number(withdrawnmarginamount)+Number(rows[i].withdrawnmarginamount == undefined ? 0 : rows[i].withdrawnmarginamount);
        			allunwithdrawnamount = Number(allunwithdrawnamount)+Number(rows[i].allunwithdrawnamount == undefined ? 0 : rows[i].allunwithdrawnamount);
        			unauditamount = Number(unauditamount)+Number(rows[i].unauditamount == undefined ? 0 : rows[i].unauditamount);
        			auditamount = Number(auditamount)+Number(rows[i].auditamount == undefined ? 0 : rows[i].auditamount);
        			rejectamount = Number(rejectamount)+Number(rows[i].rejectamount == undefined ? 0 : rows[i].rejectamount);
        			allpushbalanceamount = Number(allpushbalanceamount)+Number(rows[i].allpushbalanceamount == undefined ? 0 : rows[i].allpushbalanceamount);
        		}
        		var foot=[{customername:'选中合计',sendamount:sendamount,directreturnamount:directreturnamount,
            			checkreturnamount:checkreturnamount,returntaxamount:returntaxamount,pushbalanceamount:pushbalanceamount,
            			saleamount:saleamount,salecostamount:salecostamount,salemarginamount:salemarginamount,
            			withdrawnamount:withdrawnamount,costwriteoffamount:costwriteoffamount,withdrawnmarginamount:withdrawnmarginamount,
            			allunwithdrawnamount : allunwithdrawnamount , unauditamount : unauditamount,auditamount : auditamount,
            			rejectamount : rejectamount,allpushbalanceamount : allpushbalanceamount
            			
            			
            	}];
        		if(null!=SR_footerobject){
            		foot.push(SR_footerobject);
        		}
        		$("#report-datagrid-fundsCustomerReturn").datagrid("reloadFooter",foot);
    		}
    	</script>
  </body>
</html>
