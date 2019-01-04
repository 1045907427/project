<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>按品牌分客户,分商品统计页面</title>
    <%@include file="/include.jsp" %> 
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>    
  </head>
  
  <body>
  	<form action="" id="report-query-form-fundsBrandDetail" method="post">
		<input type="hidden" name="brandid" value="${brandid}"/>
		<input type="hidden" name="businessdate1" value="${businessdate1}"/>
		<input type="hidden" name="businessdate2" value="${businessdate2}"/>
	</form>
  	<div id="report-tab-fundsBrandDetail" class="buttonBG">
		<security:authorize url="/report/finance/fundsReturnDataDetailExport.do">
			<a href="javaScript:void(0);" id="report-export-fundsBrandDetail" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
		</security:authorize>
	</div>
	<div id="tt" class="easyui-tabs" data-options="fit:true">
	    <div title="分客户统计" style="padding:2px;">
	       <table id="report-datagrid-fundsBrandDetail-customer"></table>
	    </div>
	    <div title="分商品统计" style="padding:2px;">
	        <table id="report-datagrid-fundsBrandDetail-goods"></table>
	    </div>
	</div>
	<div id="report-fundsCustomer1-detail-dialog"></div>
    	<script type="text/javascript">
    		$('#tt').tabs({
				tools:'#report-tab-fundsBrandDetail'
			});
    		var initQueryJSON = $("#report-query-form-fundsBrandDetail").serializeJSON();
    		$(function(){
    			$("#report-datagrid-fundsBrandDetail-customer").datagrid({ 
					columns:[[
						  {field:'customerid',title:'客户编码',width:60},
						  {field:'customername',title:'客户名称',width:210},
						  {field:'pcustomerid',title:'总店名称',sortable:true,width:60,
						  	formatter:function(value,rowData,rowIndex){
				        		return rowData.pcustomername;
				        	}
						  },
						  {field:'salesarea',title:'所属区域',sortable:true,width:60,hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return rowData.salesareaname;
				        	}
						  },
						  {field:'salesdept',title:'所属部门',sortable:true,width:60,hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return rowData.salesdeptname;
				        	}
						  },
						  {field:'sendamount',title:'发货金额',width:60,sortable:true,align:'right',
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'directreturnamount',title:'直退金额',width:60,sortable:true,align:'right',
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'checkreturnamount',title:'售后退货金额',width:80,sortable:true,align:'right',
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'returntaxamount',title:'退货金额',width:60,sortable:true,align:'right',
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'pushbalanceamount',title:'冲差金额',width:60,sortable:true,align:'right',
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'saleamount',title:'销售总额',width:60,align:'right',sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'salecostamount',title:'销售成本',width:60,align:'right',
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'salemarginamount',title:'销售毛利额',width:70,align:'right',sortable:true,
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
						  {field:'withdrawnamount',title:'回笼金额',align:'right',width:60,isShow:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'costwriteoffamount',title:'回笼成本',align:'right',width:60,isShow:true,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'withdrawnmarginamount',title:'回笼毛利额',align:'right',width:70,isShow:true,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'writeoffrate',title:'回笼毛利率',align:'right',width:70,isShow:true,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		if(null != value && "" != value){
						  			return formatterMoney(value)+"%";
						  		}
				        	}
						  },
						  {field:'allunwithdrawnamount',title:'应收款总额',align:'right',width:70,isShow:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'unauditamount',title:'未验收应收款',align:'right',width:80,isShow:true,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'auditamount',title:'已验收应收款',align:'right',width:80,isShow:true,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'rejectamount',title:'退货应收款',align:'right',width:70,isShow:true,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'allpushbalanceamount',title:'冲差应收款',align:'right',width:70,isShow:true,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  }
			         ]],
			 		method:'post',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		pageSize:100,
		  	 		singleSelect:true,
					url: 'report/finance/showBaseSalesWithdrawnData.do?groupcols=customerid',
					queryParams:initQueryJSON
				}).datagrid("columnMoving");
				$("#report-datagrid-fundsBrandDetail-goods").datagrid({ 
					columns:[[
					  {field:'goodsid',title:'商品编号',sortable:true,width:60},
					  {field:'goodsname',title:'商品名称',width:250},
					  {field:'barcode',title:'条形码',sortable:true,width:90},
					  {field:'brandname',title:'品牌名称',width:60},
					  {field:'unitid',title:'主单位',width:60,hidden:true,
					  	formatter:function(value,rowData,rowIndex){
			        		return rowData.unitname;
			        	}
					  },
					  {field:'sendamount',title:'发货金额',width:60,sortable:true,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  {field:'directreturnamount',title:'直退金额',width:60,sortable:true,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  {field:'checkreturnamount',title:'售后退货金额',width:80,sortable:true,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  {field:'returntaxamount',title:'退货金额',width:60,sortable:true,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  {field:'pushbalanceamount',title:'冲差金额',width:60,sortable:true,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  {field:'saleamount',title:'销售总额',width:60,align:'right',sortable:true,
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  {field:'salecostamount',title:'销售成本',width:60,align:'right',
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  {field:'salemarginamount',title:'销售毛利额',width:70,align:'right',sortable:true,
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
					  {field:'withdrawnamount',title:'回笼金额',align:'right',width:60,isShow:true,
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  {field:'costwriteoffamount',title:'回笼成本',align:'right',width:60,isShow:true,sortable:true,
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  {field:'withdrawnmarginamount',title:'回笼毛利额',align:'right',width:70,isShow:true,sortable:true,
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  {field:'writeoffrate',title:'回笼毛利率',align:'right',width:70,isShow:true,sortable:true,
					  	formatter:function(value,rowData,rowIndex){
			        		if(null != value && "" != value){
					  			return formatterMoney(value)+"%";
					  		}
			        	}
					  },
					  {field:'allunwithdrawnamount',title:'应收款总额',align:'right',width:70,isShow:true,
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  {field:'unauditamount',title:'未验收应收款',align:'right',width:80,isShow:true,sortable:true,
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  {field:'auditamount',title:'已验收应收款',align:'right',width:80,isShow:true,sortable:true,
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  {field:'rejectamount',title:'退货应收款',align:'right',width:70,isShow:true,sortable:true,
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  {field:'allpushbalanceamount',title:'冲差应收款',align:'right',width:70,isShow:true,sortable:true,
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  }
					]],
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		singleSelect:true,
		  	 		pageSize:100,
					url: 'report/finance/showBaseSalesWithdrawnData.do?groupcols=goodsid',
					queryParams:initQueryJSON
				}).datagrid("columnMoving");
				
				$("#report-export-fundsBrandDetail").Excel('export',{
					queryForm: "#report-query-form-fundsBrandDetail", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:'按品牌：[${brandname}]统计表',
			 		url:'report/finance/exportFundsReturnDetailData.do?groupcols=customerid;goodsid'
				});
    		});
    	</script>
  </body>
</html>
