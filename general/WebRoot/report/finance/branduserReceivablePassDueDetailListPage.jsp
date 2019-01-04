<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>按品牌业务员分品牌统计页面</title>
    <%@include file="/include.jsp" %>   
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>  
  </head>
  
  <body>
  	<form action="" id="report-query-form-rcblBranduserDetail" method="post">
		<input type="hidden" name="branduser" value="${branduser}"/>
		<input type="hidden" name="businessdate1" value="${businessdate1}"/>
		<input type="hidden" name="businessdate2" value="${businessdate2}"/>
		<input type="hidden" name="ispastdue" value="${ispastdue}"/>
	</form>
  	<div id="report-tab-rcblBranduserDetail" class="buttonBG">
		<security:authorize url="/report/finance/rcblBranduserReportExport.do">
			<a href="javaScript:void(0);" id="report-export-rcblBranduserDetail" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
		</security:authorize>
	</div>
	<div id="tt" class="easyui-tabs" data-options="fit:true">
	    <div title="分品牌统计" style="padding:2px;">
	       <table id="report-datagrid-rcblBranduserDetail"></table>
	    </div>
	    <div title="分客户统计" style="padding:2px;">
	        <table id="report-datagrid-brandUserDetail-customer"></table>
	    </div>
	    <div title="分客户分品牌统计" style="padding:2px;">
	      <table id="report-datagrid-brandUserDetail-brandAndCustomer"></table>
	    </div>
	</div>
    	<script type="text/javascript">
    		$('#tt').tabs({
				tools:'#report-tab-rcblBranduserDetail'
			});
    		var initQueryJSON = $("#report-query-form-rcblBranduserDetail").serializeJSON();
    		$(function(){
    			var rcblBranduserDetailtableColumnListJson = $("#report-datagrid-rcblBranduserDetail").createGridColumnLoad({
					frozenCol : [[]],
					commonCol : [[
						  {field:'brandname',title:'商品品牌',width:60},
						  {field:'branddeptname',title:'品牌部门',width:80},
						  {field:'saleamount',title:'应收款',align:'right',width:80,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'unpassamount',title:'正常期金额',align:'right',width:80,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'totalpassamount',title:'超账期金额',align:'right',width:80,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
			        			return formatterMoney(value);
				        	},
				        	styler:function(value,rowData,rowIndex){
				        		return 'color:blue';
				        	}
						  },
						  <c:forEach items="${list }" var="list">
						  {field:'passamount${list.seq}',title:'${list.detail}',align:'right',width:80,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
			        			return formatterMoney(value);
				        	}
						  },
	  					  </c:forEach>
	  					  {field:'returnamount',title:'退货金额',align:'right',width:80,sortable:true,hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'returnrate',title:'退货率',align:'right',width:80,sortable:true,hidden:true,
						  	formatter:function(value,rowData,rowIndex){
						  		if(value!=null && value!=""){
				        			return formatterMoney(value)+"%";
				        		}
				        	}
						  },
						  {field:'pushamount',title:'冲差金额',align:'right',width:80,sortable:true,hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  }
			         ]]
				});
				var customerbrandUserDetailtableColumnListJson = $("#report-datagrid-brandUserDetail-customer").createGridColumnLoad({
					frozenCol : [[]],
					commonCol : [[
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
						  {field:'deptid',title:'所属部门',sortable:true,width:60,hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return rowData.deptname;
				        	}
						  },
						  {field:'saleamount',title:'应收款',align:'right',width:80,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'unpassamount',title:'正常期金额',align:'right',width:80,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'totalpassamount',title:'超账期金额',align:'right',width:80,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
			        			return formatterMoney(value);
				        	}
						  },
						  <c:forEach items="${list }" var="list">
						  {field:'passamount${list.seq}',title:'${list.detail}',align:'right',width:80,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
			        			return formatterMoney(value);
				        	}
						  },
   						  </c:forEach>
   						  {field:'returnamount',title:'退货金额',align:'right',width:80,sortable:true,hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'returnrate',title:'退货率',align:'right',width:80,sortable:true,hidden:true,
						  	formatter:function(value,rowData,rowIndex){
						  		if(value!=null && value!=""){
				        			return formatterMoney(value)+"%";
				        		}
				        	}
						  },
						  {field:'pushamount',title:'冲差金额',align:'right',width:80,sortable:true,hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  }
			         ]]
				});
				var brandAndCustomerbrandUserDetailtableColumnListJson = $("#report-datagrid-brandUserDetail-brandAndCustomer").createGridColumnLoad({
					frozenCol : [[]],
					commonCol : [[
						  {field:'customerid',title:'客户编码',width:60},
						  {field:'customername',title:'客户名称',width:210},
						  {field:'pcustomerid',title:'总店名称',sortable:true,width:60,
						  	formatter:function(value,rowData,rowIndex){
				        		return rowData.pcustomername;
				        	}
						  },
						  {field:'brandname',title:'商品品牌',width:60},
						  {field:'branddeptname',title:'品牌部门',width:80},
						  {field:'salesarea',title:'所属区域',sortable:true,width:60,hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return rowData.salesareaname;
				        	}
						  },
						  {field:'deptid',title:'所属部门',sortable:true,width:60,hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return rowData.deptname;
				        	}
						  },
						  {field:'saleamount',title:'应收款',align:'right',width:80,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'unpassamount',title:'正常期金额',align:'right',width:80,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'totalpassamount',title:'超账期金额',align:'right',width:80,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
			        			return formatterMoney(value);
				        	}
						  },
						  <c:forEach items="${list }" var="list">
						  {field:'passamount${list.seq}',title:'${list.detail}',align:'right',width:80,sortable:true,
						  	formatter:function(value,rowData,rowIndex){
			        			return formatterMoney(value);
				        	}
						  },
   						  </c:forEach>
   						  {field:'returnamount',title:'退货金额',align:'right',width:80,sortable:true,hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'returnrate',title:'退货率',align:'right',width:80,sortable:true,hidden:true,
						  	formatter:function(value,rowData,rowIndex){
						  		if(value!=null && value!=""){
				        			return formatterMoney(value)+"%";
				        		}
				        	}
						  },
						  {field:'pushamount',title:'冲差金额',align:'right',width:80,sortable:true,hidden:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  }
			         ]]
				});
    			$("#report-datagrid-rcblBranduserDetail").datagrid({ 
					authority:rcblBranduserDetailtableColumnListJson,
			 		frozenColumns: rcblBranduserDetailtableColumnListJson.frozen,
					columns:rcblBranduserDetailtableColumnListJson.common,
			 		method:'post',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		singleSelect:true,
		  	 		pageSize:100,
					toolbar:'#report-tab-rcblBranduserDetail',
					url: 'report/finance/showBaseReceivablePassDueListData.do?groupcols=brandid',
					queryParams:initQueryJSON
				}).datagrid("columnMoving");
				$("#report-datagrid-brandUserDetail-customer").datagrid({ 
					authority:customerbrandUserDetailtableColumnListJson,
			 		frozenColumns: customerbrandUserDetailtableColumnListJson.frozen,
					columns:customerbrandUserDetailtableColumnListJson.common,
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		pageSize:100,
		  	 		singleSelect:true,
					url: 'report/finance/showBaseReceivablePassDueListData.do?groupcols=customerid',
					queryParams:initQueryJSON
				}).datagrid("columnMoving");
				$("#report-datagrid-brandUserDetail-brandAndCustomer").datagrid({ 
					authority:brandAndCustomerbrandUserDetailtableColumnListJson,
			 		frozenColumns: brandAndCustomerbrandUserDetailtableColumnListJson.frozen,
					columns:brandAndCustomerbrandUserDetailtableColumnListJson.common,
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		pageSize:100,
		  	 		singleSelect:true,
					url: 'report/finance/showBaseReceivablePassDueListData.do?groupcols=customerid,brandid',
					queryParams:initQueryJSON
				}).datagrid("columnMoving");
				
				$("#report-export-rcblBranduserDetail").Excel('export',{
					queryForm: "#report-query-form-rcblBranduserDetail", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:'按品牌业务员：[${brandusername}]统计表',
			 		url:'report/finance/exportReceivablePastDueDetailData.do?groupcols=brandid;customerid;customerid,brandid'
				});
    		});
    	</script>
  </body>
</html>
