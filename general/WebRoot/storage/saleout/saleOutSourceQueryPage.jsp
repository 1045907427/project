<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>发货单单参照上游单据查询页面</title>
  </head>
  
  <body>
    <div class="pageContent">
    	<form id="storage-form-query-dispatchBill">
    		<input type="hidden" name="status" value="3" />
    		<input type="hidden" name="isrefer" value="0" />
    		<p>
    			<label>发货通知单编号：</label>
    			<input type="text" id="storage-dispatchBillid-saleOutSourceQueryPage" name="id" />
    		</p>
    		<p>
    			<label>客户：</label>
    			<input type="text" id="storage-customer-saleOutSourceQueryPage" name="customerid" />
    		</p>
    		<p>
    			<label>制单人：</label>
    			<input type="text" id="storage-adduser-saleOutSourceQueryPage" name="adduserid" />
    		</p>
    		<p>
    			<label>客户业务员：</label>
    			<input type="text" id="storage-salesuser-saleOutSourceQueryPage" name="salesuser" />
    		</p>
    		<p>
    			<label>业务日期：</label>
    			<input type="text" name="businessdate" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /> 到 <input type="text" name="businessdate1" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" />
    		</p>
    	</form>
    </div>
    <script type="text/javascript">
    	$(function(){
    		$("#storage-dispatchBillid-saleOutSourceQueryPage").widget({ //订单编码参照窗口
    			referwid:'RT_T_SALES_DISPATCHBILL_ID',
    			singleSelect:true,
    			width:150,
    			onlyLeafCheck:true
    		});
    		$("#storage-customer-saleOutSourceQueryPage").widget({ //客户参照窗口
    			name:'t_storage_saleout',
				col:'customerid',
    			singleSelect:true,
    			width:150,
    			onlyLeafCheck:true
    		});
    		$("#storage-adduser-saleOutSourceQueryPage").widget({ //制单人参照窗口
    			name:'t_storage_saleout',
				col:'adduserid',
    			singleSelect:true,
    			width:150,
    			onlyLeafCheck:true
    		});
    		$("#storage-salesuser-saleOutSourceQueryPage").widget({ //客户业务员参照窗口
    			name:'t_storage_saleout',
    			col:'salesuser',
    			singleSelect:true,
    			width:150,
    			onlyLeafCheck:true
    		});
    	});
    </script>
  </body>
</html>
