<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>按客户分品牌,分商品统计页面</title>
    <%@include file="/include.jsp" %> 
    <script type="text/javascript" src="js/reportDatagrid.js" charset="UTF-8"></script>    
  </head>
  
  <body>
  	<form action="" id="report-query-form-customerReceivablePastDue" method="post">
		<input type="hidden" name="customerid" value="${customerid}"/>
		<input type="hidden" name="ispastdue" value="${ispastdue}"/>
	</form>
  	<div id="report-tab-customerReceivablePastDue" class="buttonBG">
		<security:authorize url="/report/finance/customerRceivablePastDueExport.do">
			<a href="javaScript:void(0);" id="report-export-customerReceivablePastDue" class="easyui-linkbutton" iconCls="button-export" plain="true" title="导出">导出</a>
		</security:authorize>
	</div>
	<div id="tt" class="easyui-tabs" data-options="fit:true">
	    <div title="分品牌统计" style="padding:2px;">
	       <table id="report-datagrid-customerReceivablePastDue-brand"></table>
	    </div>
	    <div title="分商品统计" style="padding:2px;">
	        <table id="report-datagrid-customerReceivablePastDue-goods"></table>
	    </div>
	</div>
	<div id="report-fundsCustomer1-detail-dialog"></div>
    	<script type="text/javascript">
    		$('#tt').tabs({
				tools:'#report-tab-customerReceivablePastDue'
			});
    		var initQueryJSON = $("#report-query-form-customerReceivablePastDue").serializeJSON();
    		$(function(){
    			var brandTableColumnListJson = $("#report-datagrid-customerReceivablePastDue-brand").createGridColumnLoad({
    				frozenCol : [[]],
					commonCol : [[
					  {field:'brandname',title:'商品品牌',width:60},
					  {field:'branddeptname',title:'品牌部门',width:80},
					  {field:'saleamount',title:'应收款',align:'right',width:70,sortable:true,
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  {field:'unpassamount',title:'正常期金额',align:'right',width:70,sortable:true,
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  {field:'totalpassamount',title:'超账期金额',align:'right',width:70,sortable:true,
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
  					  {field:'returnamount',title:'退货金额',align:'right',width:60,sortable:true,hidden:true,
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  {field:'returnrate',title:'退货率',align:'right',width:60,sortable:true,hidden:true,
					  	formatter:function(value,rowData,rowIndex){
					  		if(value!=null && value!=""){
			        			return formatterMoney(value)+"%";
			        		}
			        	}
					  },
					  {field:'pushamount',title:'冲差金额',align:'right',width:60,sortable:true,hidden:true,
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  }
					]]
    			});
    			
    			var goodsTableColumnListJson = $("#report-datagrid-customerReceivablePastDue-goods").createGridColumnLoad({
    				frozenCol : [[]],
					commonCol : [[
					  {field:'goodsid',title:'商品编号',sortable:true,width:60},
					  {field:'goodsname',title:'商品名称',width:250},
					  {field:'brandname',title:'品牌名称',width:60},
					  {field:'saleamount',title:'应收款',align:'right',width:60,sortable:true,
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  {field:'unpassamount',title:'正常期金额',align:'right',width:70,sortable:true,
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  {field:'totalpassamount',title:'超账期金额',align:'right',width:70,sortable:true,
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
  					  {field:'returnamount',title:'退货金额',align:'right',width:60,sortable:true,hidden:true,
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  },
					  {field:'returnrate',title:'退货率',align:'right',width:60,sortable:true,hidden:true,
					  	formatter:function(value,rowData,rowIndex){
					  		if(value!=null && value!=""){
			        			return formatterMoney(value)+"%";
			        		}
			        	}
					  },
					  {field:'pushamount',title:'冲差金额',align:'right',width:60,sortable:true,hidden:true,
					  	formatter:function(value,rowData,rowIndex){
			        		return formatterMoney(value);
			        	}
					  }
					]]
    			});
    			
    			$("#report-datagrid-customerReceivablePastDue-brand").datagrid({ 
					authority:brandTableColumnListJson,
			 		frozenColumns: brandTableColumnListJson.frozen,
					columns:brandTableColumnListJson.common,
			 		method:'post',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		singleSelect:true,
					url: 'report/finance/showBaseReceivablePassDueListData.do?groupcols=brandid',
					queryParams:initQueryJSON
				}).datagrid("columnMoving");
				$("#report-datagrid-customerReceivablePastDue-goods").datagrid({ 
					authority:goodsTableColumnListJson,
			 		frozenColumns: goodsTableColumnListJson.frozen,
					columns:goodsTableColumnListJson.common,
			 		method:'post',
		  	 		title:'',
		  	 		fit:true,
		  	 		rownumbers:true,
		  	 		pagination:true,
		  	 		showFooter: true,
		  	 		singleSelect:true,
					url: 'report/finance/showBaseReceivablePassDueListData.do?groupcols=goodsid',
					queryParams:initQueryJSON
				}).datagrid("columnMoving");
				
				$("#report-export-customerReceivablePastDue").Excel('export',{
					queryForm: "#report-query-form-customerReceivablePastDue", //查询条件的form表单，导出时只用通过查询的条件，将通用查询放在form表单里面。
			 		type:'exportUserdefined',
			 		name:'按客户：[${customername}]统计表',
			 		url:'report/finance/exportReceivablePastDueDetailData.do?groupcols=brandid;goodsid'
				});
    		});
    	</script>
  </body>
</html>
