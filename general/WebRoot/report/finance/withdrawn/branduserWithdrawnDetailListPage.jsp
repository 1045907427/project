<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>按品牌业务员分品牌,分客户，分品牌分客户统计页面</title>
    <%@include file="/include.jsp" %> 
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>    
  </head>
  
  <body>
  	<form action="" id="report-query-form-branduserWithdrawnDetail" method="post">
		<input type="hidden" name="branduser" value="${branduser}"/>
		<input type="hidden" name="businessdate1" value="${businessdate1}"/>
		<input type="hidden" name="businessdate2" value="${businessdate2}"/>
	</form>
  	<div id="report-tab-branduserWithdrawnDetail" class="buttonBG">
		<security:authorize url="/report/finance/fundsBranduserReturnExport.do">
			<a href="javaScript:void(0);" id="report-export-branduserWithdrawnDetail" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
		</security:authorize>
	</div>
	<div id="tt" class="easyui-tabs" data-options="fit:true">
	    <div title="分品牌统计" style="padding:2px;">
	       <table id="report-datagrid-branduserWithdrawnDetail"></table>
	    </div>
	    <div title="分客户统计" style="padding:2px;">
	        <table id="report-datagrid-brandUserDetail-customer"></table>
	    </div>
	    <div title="分品牌分客户统计" style="padding:2px;">
	      <table id="report-datagrid-brandUserDetail-brandAndCustomer"></table>
	    </div>
	</div>
    	<script type="text/javascript">
    		$('#tt').tabs({
				tools:'#report-tab-branduserWithdrawnDetail'
			});
    		var initQueryJSON = $("#report-query-form-branduserWithdrawnDetail").serializeJSON();
    		$(function(){
    			$("#report-datagrid-branduserWithdrawnDetail").datagrid({ 
					columns:[[
						  {field:'brandname',title:'商品品牌',width:60},
						  {field:'branddeptname',title:'品牌部门',width:80},
						  {field:'withdrawnamount',title:'回笼金额',align:'right',width:60,isShow:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  }
						  <c:if test="${map.costwriteoffamount == 'costwriteoffamount'}">
							  ,
							  {field:'costwriteoffamount',title:'回笼成本',align:'right',width:60,isShow:true,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  }
						  </c:if>
						  <c:if test="${map.writeoffmarginamount == 'writeoffmarginamount'}">
							  ,
							  {field:'writeoffmarginamount',title:'回笼毛利额',align:'right',width:70,isShow:true,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  }
						  </c:if>
						  <c:if test="${map.writeoffrate == 'writeoffrate'}">
							  ,
							  {field:'writeoffrate',title:'回笼毛利率',align:'right',width:70,isShow:true,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		if(null != value && "" != value){
							  			return formatterMoney(value)+"%";
							  		}
					        	}
							  }
						  </c:if>
			         ]],
			 		method:'post',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		singleSelect:true,
		  	 		pageSize:100,
					toolbar:'#report-tab-branduserWithdrawnDetail',
					url: 'report/finance/showBaseFinanceDrawnData.do?groupcols=brandid',
					queryParams:initQueryJSON
				}).datagrid("columnMoving");
				$("#report-datagrid-brandUserDetail-customer").datagrid({ 
					columns:[[
					  {field:'customerid',title:'客户编号',sortable:true,width:60},
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
					  //{field:'branddeptname',title:'品牌部门',sortable:true,width:80},
					  {field:'withdrawnamount',title:'回笼金额',align:'right',width:60,isShow:true,
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  }
					  <c:if test="${map.costwriteoffamount == 'costwriteoffamount'}">
						  ,
						  {field:'costwriteoffamount',title:'回笼成本',align:'right',width:60,isShow:true,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  }
					  </c:if>
					  <c:if test="${map.writeoffmarginamount == 'writeoffmarginamount'}">
						  ,
						  {field:'writeoffmarginamount',title:'回笼毛利额',align:'right',width:70,isShow:true,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  }
					  </c:if>
					  <c:if test="${map.writeoffrate == 'writeoffrate'}">
						  ,
						  {field:'writeoffrate',title:'回笼毛利率',align:'right',width:70,isShow:true,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		if(null != value && "" != value){
						  			return formatterMoney(value)+"%";
						  		}
				        	}
						  }
					  </c:if>
					]],
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		singleSelect:true,
		  	 		pageSize:100,
					url: 'report/finance/showBaseFinanceDrawnData.do?groupcols=customerid',
					queryParams:initQueryJSON
				}).datagrid("columnMoving");
				$("#report-datagrid-brandUserDetail-brandAndCustomer").datagrid({ 
					columns:[[
						  {field:'brandname',title:'商品品牌',width:60},
						  {field:'branddeptname',title:'品牌部门',width:80},
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
						  {field:'withdrawnamount',title:'回笼金额',align:'right',width:60,isShow:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  }
						  <c:if test="${map.costwriteoffamount == 'costwriteoffamount'}">
							  ,
							  {field:'costwriteoffamount',title:'回笼成本',align:'right',width:60,isShow:true,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  }
						  </c:if>
						  <c:if test="${map.writeoffmarginamount == 'writeoffmarginamount'}">
							  ,
							  {field:'writeoffmarginamount',title:'回笼毛利额',align:'right',width:70,isShow:true,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  }
						  </c:if>
						  <c:if test="${map.writeoffrate == 'writeoffrate'}">
							  ,
							  {field:'writeoffrate',title:'回笼毛利率',align:'right',width:70,isShow:true,sortable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		if(null != value && "" != value){
							  			return formatterMoney(value)+"%";
							  		}
					        	}
							  }
						  </c:if>
					]],
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		pageSize:100,
		  	 		singleSelect:true,
					url: 'report/finance/showBaseFinanceDrawnData.do?groupcols=brandid,customerid',
					queryParams:initQueryJSON
				}).datagrid("columnMoving");
				
				$("#report-export-branduserWithdrawnDetail").Excel('export',{
					queryForm: "#report-query-form-branduserWithdrawnDetail", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:'按品牌业务员：[${brandusername}]统计表',
			 		url:'report/finance/exportFinanceWithdrawnDetailReportData.do?groupcols=brandid;customerid;brandid,customerid'
				});
    		});
    	</script>
  </body>
</html>
