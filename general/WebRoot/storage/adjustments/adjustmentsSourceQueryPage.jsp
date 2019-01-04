<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>调账单参照上游单据查询页面</title>
  </head>
  
  <body>
    <div class="pageContent">
    	<form id="storage-form-query-dispatchBill">
    		<input type="hidden" name="status" value="3" />
    		<input type="hidden" name="isrefer" value="0" />
    		<p>
    			<label>订单编码：</label>
    			<input type="text" id="storage-dispatchBillid-adjustmentsSourceQueryPage" name="id" />
    		</p>
    		<p>
    			<label>调账仓库：</label>
    			<input type="text" id="storage-storageid-adjustmentsSourceQueryPage" name="storageid" />
    		</p>
    		<p>
    			<label>制单人：</label>
    			<input type="text" id="storage-adduser-adjustmentsSourceQueryPage" name="adduserid" />
    		</p>
    		<p>
    			<label>业务日期：</label>
    			<input type="text" name="businessdate1" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /> 到 <input type="text" name="businessdate2" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" />
    		</p>
    	</form>
    </div>
    <script type="text/javascript">
    	$(function(){
    		$("#storage-dispatchBillid-adjustmentsSourceQueryPage").widget({ //订单编码参照窗口
    			name:'t_storage_adjustments',
				col:'sourceid',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:true
    		});
    		$("#storage-storageid-adjustmentsSourceQueryPage").widget({ //供应商参照窗口
    			name:'t_storage_adjustments',
				col:'storageid',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:true
    		});
    		$("#storage-adduser-adjustmentsSourceQueryPage").widget({ //制单人参照窗口
    			name:'t_storage_purchasereject_out',
				col:'adduserid',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:true
    		});
    	});
    </script>
  </body>
</html>
