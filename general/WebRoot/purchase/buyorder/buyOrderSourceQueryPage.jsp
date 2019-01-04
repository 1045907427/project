<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>采购订单参照上游单据查询页面</title>
    <%@include file="/include.jsp" %>  
  </head>
  
  <body>
  	<div class="pageContent">
    	<form id="purchase-form-buyOrderSourceQueryPage">
    		<input type="hidden" name="status" value="3" />
    		<p>
    			<label style="width:100px;">采购计划单编码：</label>
    			<input type="text" class="easyui-validatebox" id="purchase-orderId-buyOrderSourceQueryPage" name="id" style="width:150px;" />
    		</p>
    		<p>
    			<label style="width:100px;">采购员：</label>
    			<input type="text" class="easyui-validatebox" id="purchase-buyuserid-buyOrderSourceQueryPage" name="buyuserid" style="width:150px;" />
    		</p>
    		<p>
    			<label style="width:100px;">采购部门：</label>
    			<input type="text" class="easyui-validatebox" id="purchase-buydeptid-buyOrderSourceQueryPage" name="buydeptid" style="width:150px;" />
    		</p>
    		<p>
    			<label style="width:100px;">制单人：</label>
    			<input type="text" class="easyui-validatebox" id="purchase-adduser-buyOrderSourceQueryPage" name="adduserid" style="width:150px;" />
    		</p>
    		<p>
    			<label style="width:100px;">制单日期：</label>
    			<input type="text" class="easyui-validatebox" name="startaddtime" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /> 到 <input type="text" class="easyui-validatebox" name="endaddtime" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" />
    		</p>
    	</form>
    </div>
    <script type="text/javascript">
    	$(document).ready(function(){
    		$("#purchase-orderId-buyOrderSourceQueryPage").widget({
    			name:'t_purchase_buyorder_detail',
    			col:'billno',
    			singleSelect:true,
    			width:150
    		});
    		$("#purchase-buyuserid-buyOrderSourceQueryPage").widget({
    			name:'t_purchase_plannedorder',
    			col:'buyuserid',
    			singleSelect:true,
    			onlyLeafCheck:true,
    			width:150
    		});
    		$("#purchase-buydeptid-buyOrderSourceQueryPage").widget({
    			name:'t_purchase_plannedorder',
    			col:'buydeptid',
    			singleSelect:true,
    			width:150
    		});
    		$("#purchase-adduser-buyOrderSourceQueryPage").widget({
    			name:'t_purchase_plannedorder',
    			col:'adduserid',
    			singleSelect:true,
    			width:150
    		});
    	});
    </script>
  </body>
</html>
