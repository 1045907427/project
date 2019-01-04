<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>按部门分客户,分品牌统计页面</title>
    <%@include file="/include.jsp" %>   
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>  
  </head>
  
  <body>
  	<form action="" id="report-query-form-salesdeptWithdrawnDetail" method="post">
		<input type="hidden" name="salesdept" value="${salesdept}"/>
		<input type="hidden" name="businessdate1" value="${businessdate1}"/>
		<input type="hidden" name="businessdate2" value="${businessdate2}"/>
	</form>
  	<div id="report-tab-salesdeptWithdrawnDetail" class="buttonBG">
		<security:authorize url="/report/finance/fundsReturnDataDetailExport.do">
			<a href="javaScript:void(0);" id="report-export-salesdeptWithdrawnDetail" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
		</security:authorize>
	</div>
	<div id="tt" class="easyui-tabs" data-options="fit:true">
	    <div title="分客户统计" style="padding:2px;">
	       <table id="report-datagrid-salesdeptWithdrawnDetail-customer"></table>
	    </div>
	    <div title="分品牌统计" style="padding:2px;">
	        <table id="report-datagrid-salesdeptWithdrawnDetail-brand"></table>
	    </div>
	</div>
	<div id="report-fundsCustomer1-detail-dialog"></div>
    	<script type="text/javascript">
    		$('#tt').tabs({
				tools:'#report-tab-salesdeptWithdrawnDetail'
			});
    		var initQueryJSON = $("#report-query-form-salesdeptWithdrawnDetail").serializeJSON();
    		$(function(){
    			$("#report-datagrid-salesdeptWithdrawnDetail-customer").datagrid({ 
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
					url: 'report/finance/showBaseFinanceDrawnData.do?groupcols=customerid',
					queryParams:initQueryJSON
				}).datagrid("columnMoving");
				$("#report-datagrid-salesdeptWithdrawnDetail-brand").datagrid({ 
					columns:[[
					  {field:'brandname',title:'商品品牌',width:60},
					  {field:'branddeptname',title:'品牌部门',width:80},
					  {field:'unitid',title:'主单位',width:60,hidden:true,
					  	formatter:function(value,rowData,rowIndex){
			        		return rowData.unitname;
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
		  	 		singleSelect:true,
					url: 'report/finance/showBaseFinanceDrawnData.do?groupcols=brandid',
					queryParams:initQueryJSON
				}).datagrid("columnMoving");
				
				$("#report-export-salesdeptWithdrawnDetail").Excel('export',{
					queryForm: "#report-query-form-salesdeptWithdrawnDetail", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:'按部门：[${salesdeptname}]统计表',
			 		url:'report/finance/exportFinanceWithdrawnDetailReportData.do?groupcols=customerid;brandid'
				});
    		});
    	</script>
  </body>
</html>
