<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售发票参照上游单据列表页面</title>
  </head>
  
  <body>
	<div class="easyui-layout" data-options="fit:true">
		<div data-options="region:'north',split:true,border:false" style="height: 35px;">
			<table>
				<tr>
					<td>客户名称：</td>
					<td>
						<select id="account-customerid-dispatchBillSourcePage" style="width: 200px;">
						</select>
						<input id="account-hidden-customerid-dispatchBillSourcePage" type="hidden"/>
						<input id="account-hidden-pcustomerid-dispatchBillSourcePage" type="hidden"/>
					</td>
					<td>客户账户余额:</td>
					<td>
						<input id="account-amount-dispatchBillSourcePage" type="text" style="width: 100px;" readonly="readonly"/>
					</td>
					<td>选中金额:</td>
					<td>
						<input id="account-selectamount-dispatchBillSourcePage" type="text" style="width: 100px;" readonly="readonly"/>
					</td>
				</tr>
			</table>
		</div>
		<div data-options="region:'center',split:true,border:false">
			<table id="account-orderDatagrid-dispatchBillSourcePage"></table>
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			$("#account-customerid-dispatchBillSourcePage").change(function(){
				var val = $(this).val();
				getCustomerCapital(val);
				var rowArr = $("#account-orderDatagrid-dispatchBillSourcePage").datagrid("getChecked");
			});
		});
		
	</script>
  </body>
</html>
