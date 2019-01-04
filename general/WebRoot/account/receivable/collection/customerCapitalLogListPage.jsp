<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>客户资金流水列表页面</title>
    <%@include file="/include.jsp" %>   
  </head>
  
  <body>
	<table id="account-datagrid-customerCapitalLogPage" data-options="border:false"></table>
    <div id="account-datagrid-toolbar-customerCapitalLogPage">
    	<form action="" id="account-form-query-customerCapitalLogPage" method="post">
    		<table>
    			<tr>
    				<td>对应单据号:</td>
    				<td><input type="text" name="billid" style="width: 180px;"/></td>
    				<td>
    					<a href="javaScript:void(0);" id="account-queay-customerCapitalLog" class="button-qr">查询</a>
						<a href="javaScript:void(0);" id="account-reload-customerCapitalLog" class="button-qr">重置</a>
    				</td>
    			</tr>
    		</table>
    	</form>
    </div>
    <div id="account-dialog-customerCapitalLogPage"></div>
     <script type="text/javascript">
    	$(function(){
			$("#account-datagrid-customerCapitalLogPage").datagrid({ 
				columns:[[
						  {field:'billid',title:'对应单据编号',width:130},
						  {field:'billtype',title:'单据类型',width:70,
						  	formatter:function(value,rowData,rowIndex){
						  		if(value=="1"){
						  			return "收款单";
						  		}else if(value=="2"){
						  			return "销售发票";
						  		}else if(value=="3"){
						  			return "转账";
						  		}else if(value=="4"){
						  			return "冲差单";
						  		}
				        	}
						  },
						  {field:'prtype',title:'类型',width:70,
						  	formatter:function(value,rowData,rowIndex){
						  		if(value=="1"){
						  			return "收款";
						  		}else if(value=="2"){
						  			return "核销";
						  		}else if(value=="3"){
						  			return "转入";
						  		}else if(value=="4"){
						  			return "转出";
						  		}
				        	}
						  },
						  {field:'incomeamount',title:'收款金额',resizable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'payamount',title:'核销金额',resizable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'balanceamount',title:'余额',resizable:true,
						  	formatter:function(value,rowData,rowIndex){
				        		return formatterMoney(value);
				        	}
						  },
						  {field:'remark',title:'备注',width:80},
						  {field:'addtime',title:'添加时间',width:80}
			             ]],
		 		fit:true,
		 		method:'post',
		 		rownumbers:true,
		 		pagination:true,
		 		idField:'id',
		 		sortName:'id',
		 		sortOrder:'desc',
		 		singleSelect:true,
				url: 'account/receivable/showCustomerCapitalLogList.do?customerid=${id}',
				toolbar:'#account-datagrid-toolbar-customerCapitalLogPage'
			});
			
			//通用查询组建调用
			$("#account-query-advanced-customerCapitalLog").advancedQuery({
				//查询针对的表
		 		name:'t_base_sales_customer',
		 		//查询针对的表格id
		 		datagrid:'account-datagrid-customerCapitalLogPage',
		 		plain:true
			});
			
			//回车事件
			controlQueryAndResetByKey("account-queay-customerCapitalLog","account-reload-customerCapitalLog");
			
			//查询
			$("#account-queay-customerCapitalLog").click(function(){
				//把form表单的name序列化成JSON对象
	       		var queryJSON = $("#account-form-query-customerCapitalLogPage").serializeJSON();
	       		$("#account-datagrid-customerCapitalLogPage").datagrid("load",queryJSON);
			});
			//重置
			$("#account-reload-customerCapitalLog").click(function(){
				$("#account-form-query-customerCapitalLogPage")[0].reset();
	       		$("#account-datagrid-customerCapitalLogPage").datagrid("load",{});
			});
    	});
    </script>
  </body>
</html>
