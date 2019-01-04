<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>收款单列表页面</title>
    <%@include file="/include.jsp" %>   
  </head>
  
  <body>
    <div id="account-datagrid-toolbar-collectionOrderPage">
    	<form action="" id="account-form-query-collectionOrderPage" method="post">
    		<table>
    			<tr>
    				<td>编号:</td>
    				<td>
    					<input type="text" name="billid" style="width: 180px;"/>
    					<input id="account-query-customerid" type="hidden" name="customerid" value="${customerid }"/>
    				</td>
    				<td>业务日期:</td>
    				<td><input type="text" name="businessdate1" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /> 到 <input type="text" name="businessdate2" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
    				<td colspan="4">
    					<a href="javaScript:void(0);" id="account-queay-collectionOrder" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="account-reload-collectionOrder" class="button-qr">重置</a>
						<span id="account-query-advanced-collectionOrder"></span>
    				</td>
    			</tr>
    		</table>
    	</form>
    </div>
    <table id="account-datagrid-statementPage" data-options="border:false"></table>
     <script type="text/javascript">
     	var intiQueryJSON = $("#account-form-query-collectionOrderPage").serializeJSON();
    	$(function(){
			//对账单列表
			$("#account-datagrid-statementPage").datagrid({
				columns:[[
							  {field:'billtype',title:'单据类型',width:80,
							  	formatter:function(value,rowData,rowIndex){
							  		if(value=="1"){
							  			return "销售发票";
							  		}else if(value=="2"){
							  			return "转账";
							  		}else if(value=="3"){
							  			return "冲差单";
							  		}else if(value=="4"){
							  			return "收款单";
							  		}
					        	}
							  },
							  {field:'billid',title:'单据编号',width:140},
							  {field:'businessdate',title:'业务日期',width:80},
							  {field:'amount',title:'收款金额',resizable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'billamount',title:'单据金额',resizable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'writeoffamount',title:'核销金额',align:'right',resizable:true,
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'tailamount',title:'尾差金额',resizable:true,align:'right',
							  	formatter:function(value,rowData,rowIndex){
					        		return formatterMoney(value);
					        	}
							  },
							  {field:'remark',title:'备注',width:120},
							  {field:'addusername',title:'操作人',width:80},
							  {field:'addtime',title:'操作时间',width:130}
				             ]],
				fit:true,
		 		method:'post',
		 		rownumbers:true,
		 		pagination:true,
				url: 'account/receivable/showSalesStatementList.do',
				queryParams:intiQueryJSON,
				toolbar:'#account-datagrid-toolbar-collectionOrderPage'
			});
			//通用查询组建调用
			$("#account-query-advanced-collectionOrder").advancedQuery({
				//查询针对的表
		 		name:'t_account_collection_order',
		 		//查询针对的表格id
		 		datagrid:'account-datagrid-collectionOrderPage',
		 		plain:true
			});
			
			//回车事件
			controlQueryAndResetByKey("account-queay-collectionOrder","account-reload-collectionOrder");
			//查询
			$("#account-queay-collectionOrder").click(function(){
				//把form表单的name序列化成JSON对象
	       		var queryJSON = $("#account-form-query-collectionOrderPage").serializeJSON();
	       		$("#account-datagrid-statementPage").datagrid("load",queryJSON);
			});
			//重置
			$("#account-reload-collectionOrder").click(function(){
				$("#account-form-query-collectionOrderPage")[0].reset();
				var queryJSON = $("#account-form-query-collectionOrderPage").serializeJSON();
	       		$("#account-datagrid-statementPage").datagrid("load",queryJSON);
			});
    	});
    </script>
  </body>
</html>
