<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>采购入库单参照上游单据查询页面</title>
  </head>
  
  <body>
    <div class="pageContent">
    	<form id="storage-form-query-dispatchBill">
    		<input type="hidden" name="status" value="3" />
    		<p>
    			<label>采购订单编号：</label>
    			<input type="text" id="storage-dispatchBillid-purchaseEnterSourceQueryPage" name="id" />
    		</p>
    		<p>
    			<label>供应商：</label>
    			<input type="text" id="storage-supplier-purchaseEnterSourceQueryPage" name="supplierid" />
    		</p>
    		<p>
    			<label>所属部门：</label>
    			<input type="text" id="storage-buydeptid-purchaseEnterSourceQueryPage" name="buydeptid" />
    		</p>
    		<p>
    			<label>采购员：</label>
    			<input type="text" id="storage-buyuserid-purchaseEnterSourceQueryPage" name="buyuserid" />
    		</p>
    		<p>
    			<label>制单人：</label>
    			<input type="text" id="storage-adduser-purchaseEnterSourceQueryPage" name="adduserid" />
    		</p>
    		<p>
    			<label>制单日期：</label>
    			<input type="text" name="addtime" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /> 到 <input type="text" name="addtime1" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" />
    		</p>
    	</form>
    </div>
    <script type="text/javascript">
    	$(function(){
    		$("#storage-dispatchBillid-purchaseEnterSourceQueryPage").widget({ //订单编码参照窗口
    			referwid:'RT_T_PURCHASE_BUYORDER',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:true
    		});
    		$("#storage-supplier-purchaseEnterSourceQueryPage").widget({ //供应商参照窗口
    			name:'t_storage_purchase_enter',
				col:'supplierid',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:true
    		});
    		$("#storage-buydeptid-purchaseEnterSourceQueryPage").widget({ //所属部门参照窗口
    			name:'t_storage_purchase_enter',
				col:'buydeptid',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:true
    		});
    		$("#storage-adduser-purchaseEnterSourceQueryPage").widget({ //制单人参照窗口
    			name:'t_storage_purchase_enter',
				col:'adduserid',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:true
    		});
    		$("#storage-buyuserid-purchaseEnterSourceQueryPage").widget({ //采购员参照窗口
    			name:'t_storage_purchase_enter',
    			col:'buyuserid',
    			singleSelect:true,
    			width:200,
    			onlyLeafCheck:true
    		});
    	});
    </script>
  </body>
</html>
