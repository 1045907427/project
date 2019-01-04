<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>调拨单参照上游单据查询页面</title>
  </head>
  
  <body>
    <div class="pageContent">
    	<form id="storage-form-query-dispatchBill">
    		<input type="hidden" name="status" value="3" />
    		<input type="hidden" name="isrefer" value="0" />
    		<p>
    			<label>调拨通知单编号：</label>
    			<input type="text" id="storage-dispatchBillid-purchaseRejectOutSourceQueryPage" name="id" />
    		</p>
    		<p>
    			<label>调出仓库：</label>
    			<input type="text" id="storage-outstorageid-purchaseRejectOutSourceQueryPage" name="outstorageid" />
    		</p>
    		<p>
    			<label>调入仓库：</label>
    			<input type="text" id="storage-enterstorageid-purchaseRejectOutSourceQueryPage" name="enterstorageid" />
    		</p>
    		<p>
    			<label>制单人：</label>
    			<input type="text" id="storage-adduser-purchaseRejectOutSourceQueryPage" name="adduserid" />
    		</p>
    		<p>
    			<label>业务日期：</label>
    			<input type="text" name="businessdate1" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /> 到 <input type="text" name="businessdate2" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" />
    		</p>
    	</form>
    </div>
    <script type="text/javascript">
    	$(function(){
    		$("#storage-dispatchBillid-purchaseRejectOutSourceQueryPage").widget({ //订单编码参照窗口
    			referwid:'RL_T_STORAGE_ALLOCATE_NOTICE',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:true
    		});
    		$("#storage-outstorageid-purchaseRejectOutSourceQueryPage").widget({ 
    			name:'t_storage_allocate_out',
				col:'outstorageid',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:true
    		});
    		$("#storage-enterstorageid-purchaseRejectOutSourceQueryPage").widget({ 
    			name:'t_storage_allocate_out',
				col:'enterstorageid',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:true
    		});
    		$("#storage-adduser-purchaseRejectOutSourceQueryPage").widget({ //制单人参照窗口
    			name:'t_storage_allocate_out',
				col:'adduserid',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:true
    		});
    	});
    </script>
  </body>
</html>
