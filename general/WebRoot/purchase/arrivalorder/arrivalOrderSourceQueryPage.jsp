<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>采购进货单参照上游单据查询页面</title>
    <%@include file="/include.jsp" %>  
  </head>
  
  <body>
  	<div class="pageContent">
    	<form id="purchase-form-arrivalOrderSourceQueryPage">
    		<p>
    			<label style="width:80px;">订单编码：</label>
    			<input type="text" class="easyui-validatebox" id="purchase-orderId-arrivalOrderSourceQueryPage" name="id" style="width:150px;" />
    		</p>
    		<p>
    			<label style="width:80px;">采购员：</label>
    			<input type="text" class="easyui-validatebox" id="purchase-buyuserid-arrivalOrderSourceQueryPage" name="buyuserid" style="width:150px;" />
    		</p>
    		<p>
    			<label style="width:80px;">采购部门：</label>
    			<input type="text" class="easyui-validatebox" id="purchase-buydeptid-arrivalOrderSourceQueryPage" name="applydeptid" style="width:150px;" />
    		</p>
    		<p>
    			<label style="width:80px;">制单人：</label>
    			<input type="text" class="easyui-validatebox" id="purchase-adduser-arrivalOrderSourceQueryPage" name="adduserid" style="width:150px;" />
    		</p>
    		<p>
    			<label style="width:80px;">制单时间：</label>
    			<input type="text" class="easyui-validatebox" name="startaddtime" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /> 到 <input type="text" class="easyui-validatebox" name="endaddtime" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" />
    		</p>
    		<input type="hidden" name="isrefer" value="0"/>
    		<input type="hidden" name="status" value="3"/>
    	</form>
    </div>
    <script type="text/javascript">
    	$(document).ready(function(){
    		$("#purchase-orderId-arrivalOrderSourceQueryPage").widget({
    			referwid:'RL_T_STORAGE_PURCHASE_ENTER',
    			singleSelect:true,
    			width:150,
    			onlyLeafCheck:true
    		});
    		$("#purchase-buyuserid-arrivalOrderSourceQueryPage").widget({
    			name:'t_purchase_arrivalorder',
    			col:'buyuserid',
    			singleSelect:true,
    			onlyLeafCheck:true,
    			width:150
    		});
    		$("#purchase-buydeptid-arrivalOrderSourceQueryPage").widget({
    			name:'t_purchase_arrivalorder',
    			col:'buydeptid',
    			singleSelect:true,
    			onlyLeafCheck:true,
    			width:150
    		});
    		$("#purchase-adduser-arrivalOrderSourceQueryPage").widget({
    			name:'t_purchase_arrivalorder',
    			col:'adduserid',
    			singleSelect:true,
    			onlyLeafCheck:true,
    			width:150
    		});
    	});
    </script>
  </body>
</html>
