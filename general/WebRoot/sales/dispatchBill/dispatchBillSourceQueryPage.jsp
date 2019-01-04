<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售发货通知单参照上游单据查询页面</title>
  </head>
  
  <body>
  	<style type="text/css">
  		.pageContent input{width:150px;}
  	</style>
    <div class="pageContent">
    	<form id="sales-form-dispatchBillSourceQueryPage">
    		<input type="hidden" name="status" value="3" />
    		<input type="hidden" name="isrefer" value="0" />
    		<p>
    			<label>订单编码：</label>
    			<input type="text" class="easyui-validatebox" id="sales-orderId-dispatchBillSourceQueryPage" name="id" />
    		</p>
    		<p>
    			<label>客户：</label>
    			<input type="text" class="easyui-validatebox" id="sales-customer-dispatchBillSourceQueryPage" name="customerid" />
    		</p>
    		<p>
    			<label>制单人：</label>
    			<input type="text" class="easyui-validatebox" id="sales-adduser-dispatchBillSourceQueryPage" name="adduserid" />
    		</p>
    		<p>
    			<label>客户业务员：</label>
    			<input type="text" class="easyui-validatebox" id="sales-salesuser-dispatchBillSourceQueryPage" name="salesuser" />
    		</p>
    		<p>
    			<label>制单日期：</label>
    			<input type="text" class="easyui-validatebox" name="addtime" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /> 到 <input type="text" class="easyui-validatebox" name="addtime1" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" />
    		</p>
    	</form>
    </div>
    <script type="text/javascript">
    	$(function(){
    		$("#sales-orderId-dispatchBillSourceQueryPage").widget({ //订单编码参照窗口
    			referwid:'RT_T_SALES_ORDER_ID',
    			singleSelect:true,
    			width:150,
    			onlyLeafCheck:true
    		});
    		$("#sales-customer-dispatchBillSourceQueryPage").widget({ //客户参照窗口
    			name:'t_sales_order',
				col:'customerid',
    			singleSelect:true,
    			width:150,
    			onlyLeafCheck:true
    		});
    		$("#sales-adduser-dispatchBillSourceQueryPage").widget({ //制单人参照窗口
    			name:'t_sales_order',
				col:'adduserid',
    			singleSelect:true,
    			width:150,
    			onlyLeafCheck:true
    		});
    		$("#sales-salesuser-dispatchBillSourceQueryPage").widget({ //客户业务员参照窗口
    			name:'t_sales_order',
    			col:'salesuser',
    			singleSelect:true,
    			width:150,
    			onlyLeafCheck:true
    		});
    	});
    </script>
  </body>
</html>
