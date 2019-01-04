<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售退货入库单参照上游单据查询页面</title>
  </head>
  
  <body>
    <div class="pageContent">
    	<form id="storage-form-query-dispatchBill">
    		<input type="hidden" name="status" value="3" />
    		<input type="hidden" name="isrefer" value="0" />
    		<p>
    			<label>来源类型：</label>
    			<select id="storage-dispatchBillid-sourcetype">
    				<option value="1">销售退货通知单</option>
    				<option value="2">销售发货回单</option>
    			</select>
    		</p>
    		<p>
    			<label>来源单据编号：</label>
    			<input type="text" id="storage-dispatchBillid-saleRejectEnterSourceQueryPage" name="id" />
    		</p>
    		<p>
    			<label>供应商：</label>
    			<input type="text" id="storage-customerid-saleRejectEnterSourceQueryPage" name="customerid" />
    		</p>
    		<p>
    			<label>销售部门：</label>
    			<input type="text" id="storage-salesdept-saleRejectEnterSourceQueryPage" name="salesdept" />
    		</p>
    		<p>
    			<label>客户业务员：</label>
    			<input type="text" id="storage-salesuser-saleRejectEnterSourceQueryPage" name="salesuser" />
    		</p>
    		<p>
    			<label>制单人：</label>
    			<input type="text" id="storage-adduser-saleRejectEnterSourceQueryPage" name="adduserid" />
    		</p>
    		<p>
    			<label>业务日期：</label>
    			<input type="text" name="businessdate" style="width:100px;height: 20px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /> 到 <input type="text" name="businessdate1" class="Wdate" style="width:100px;height: 20px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" />
    		</p>
    	</form>
    </div>
    <script type="text/javascript">
    	$(function(){
    		$("#storage-dispatchBillid-sourcetype").change(function(){
    			var val = $(this).val();
    			if(val=='1'){
    				$("#storage-dispatchBillid-saleRejectEnterSourceQueryPage").widget({
		    			referwid:'RL_T_SALES_REJECTBILL_ID',
		    			singleSelect:true,
		    			width:200,
		    			onlyLeafCheck:true
		    		});
    			}else{
    				$("#storage-dispatchBillid-saleRejectEnterSourceQueryPage").widget({
		    			referwid:'RT_T_SALES_RECEIPT_ID',
		    			singleSelect:true,
		    			width:200,
		    			onlyLeafCheck:true
		    		});
    			}
    		});
    		 //订单编码参照窗口
    		$("#storage-dispatchBillid-saleRejectEnterSourceQueryPage").widget({
    			referwid:'RL_T_SALES_REJECTBILL_ID',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:true
    		});
    		$("#storage-customerid-saleRejectEnterSourceQueryPage").widget({ //客户参照窗口
    			name:'t_storage_salereject_enter',
				col:'customerid',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:true
    		});
    		$("#storage-salesdept-saleRejectEnterSourceQueryPage").widget({ //所属部门参照窗口
    			name:'t_storage_salereject_enter',
				col:'salesdept',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:true
    		});
    		$("#storage-adduser-saleRejectEnterSourceQueryPage").widget({ //制单人参照窗口
    			name:'t_storage_salereject_enter',
				col:'adduserid',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:true
    		});
    		$("#storage-salesuser-saleRejectEnterSourceQueryPage").widget({ //客户业务员参照窗口
    			name:'t_storage_salereject_enter',
    			col:'salesuser',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:true
    		});
    	});
    </script>
  </body>
</html>
