<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>采购退货出库单参照上游单据查询页面</title>
  </head>
  
  <body>
    <div class="pageContent">
    	<form id="storage-form-query-dispatchBill">
    		<input type="hidden" name="status" value="3" />
    		<input type="hidden" name="isrefer" value="0" />
    		<p>
    			<label>采购退货单编号：</label>
    			<input type="text" id="storage-dispatchBillid-purchaseRejectOutSourceQueryPage" name="id" />
    		</p>
    		<p>
    			<label>供应商：</label>
    			<input type="text" id="storage-supplier-purchaseRejectOutSourceQueryPage" name="supplierid" />
    		</p>
    		<p>
    			<label>所属部门：</label>
    			<input type="text" id="storage-buydeptid-purchaseRejectOutSourceQueryPage" name="buydeptid" />
    		</p>
    		<p>
    			<label>采购员：</label>
    			<input type="text" id="storage-buyuserid-purchaseRejectOutSourceQueryPage" name="buyuserid" />
    		</p>
    		<p>
    			<label>制单人：</label>
    			<input type="text" id="storage-adduser-purchaseRejectOutSourceQueryPage" name="adduserid" />
    		</p>
    		<p>
    			<label>制单日期：</label>
    			<input type="text" name="addtime" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /> 到 <input type="text" name="addtime1" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" />
    		</p>
    	</form>
    </div>
    <script type="text/javascript">
    	$(function(){
    		$("#storage-dispatchBillid-purchaseRejectOutSourceQueryPage").widget({ //订单编码参照窗口
    			referwid:'RT_T_PURCHASE_RETURNORDER',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:true
    		});
    		$("#storage-supplier-purchaseRejectOutSourceQueryPage").widget({ //供应商参照窗口
    			name:'t_storage_purchasereject_out',
				col:'supplierid',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:true
    		});
    		$("#storage-buydeptid-purchaseRejectOutSourceQueryPage").widget({ //所属部门参照窗口
    			name:'t_storage_purchasereject_out',
				col:'buydeptid',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:true
    		});
    		$("#storage-adduser-purchaseRejectOutSourceQueryPage").widget({ //制单人参照窗口
    			name:'t_storage_purchasereject_out',
				col:'adduserid',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:true
    		});
    		$("#storage-buyuserid-purchaseRejectOutSourceQueryPage").widget({ //采购员参照窗口
    			name:'t_storage_purchasereject_out',
    			col:'buyuserid',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:true
    		});
    	});
    </script>
  </body>
</html>
