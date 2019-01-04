<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>查看</title>
    <%@include file="/include.jsp" %>
  </head>
  <body>  
  <div class="easyui-panel" title="" data-options="fit:true,border:false">
	  <div class="easyui-layout" data-options="fit:true">
	  	<form id="account-form-paymentVoucherAddPage" method="post">
	  		<input type="hidden" id="account-paymentVoucherAddPage-addType" name="addType"/>
	  		<input type="hidden" id="account-paymentVoucherAddPage-saveaudit" name="saveaudit"/>
	  		<div data-options="region:'north',border:false" style="height:70px;">
	  			<table style="border-collapse:collapse;" border="0" cellpadding="5" cellspacing="5">
					<tr>
						<td style="width:50px;">编号：</td>
						<td><input type="text" class="len150" value="${paymentVoucher.id }" readonly="readonly" /></td>
						<td style="width:80px;">业务日期：</td>
						<td><input type="text" name="paymentVoucher.businessdate" value="${paymentVoucher.businessdate }" class="len150" readonly="readonly"/></td>
						<td style="width:80px;">状态：</td>
				    	<td style="width:135px;">			    		
	    					<select id="account-paymentVoucherAddPage-status"  disabled="disabled" class="len150">
	    						<c:forEach items="${statusList }" var="list">
	    							<c:choose>
	    								<c:when test="${list.code == paymentVoucher.status}">
	    									<option value="${list.code }" selected="selected">${list.codename }</option>
	    								</c:when>
	    								<c:otherwise>
	    									<option value="${list.code }">${list.codename }</option>
	    								</c:otherwise>
	    							</c:choose>
	    						</c:forEach>
	    					</select>
						</td>
					</tr>
					<tr>
						<td >交款人：</td>
				    	<td><input type="text" id="account-paymentVoucherAddPage-collectusername" class="len150" value="${paymentVoucher.collectusername }" readonly="readonly" /></td>
						<td>银行名称:</td>
						<td>
							<input id="account-paymentVoucherAddPage-bank" type="text" name="paymentVoucher.bank" value="${paymentVoucher.bank }" readonly="readonly" />
						</td>
						<td >备注：</td>
						<td>
							<input type="text" style="width:150px;" id="account-paymentVoucherAddPage-remark" name="paymentVoucher.remark" value="${paymentVoucher.remark }" readonly="readonly" />
						</td>	
					</tr>
				</table>
	  		</div>
	  		<div data-options="region:'center',border:false">
	  			<table id="account-paymentVoucherAddPage-paymentVouchertable"></table>
				<input type="hidden" id="account-paymentVoucherAddPage-paymentVoucherDetails" name="paymentVoucherDetails"/>
	  		</div>
	  		<input type="hidden" name="paymentVoucher.id" value="${paymentVoucher.id }" />	
	  		<input type="hidden" id="account-paymentVoucherAddPage-printtimes" value="${paymentVoucher.printtimes}" />	
	  		<input type="hidden" id="account-paymentVoucherAddPage-printlimit" value="${printlimit}" />
	  	</form> 
	  </div>
  </div>
  <script type="text/javascript">

	$("#account-paymentVoucherAddPage-bank").widget({
	  name:'t_account_collection_order',
	  col:'bank',
	  singleSelect:true,
	  width:150,
	  view:true
	});

  	$(document).ready(function(){
  		$("#account-buttons-paymentVoucherPage").buttonWidget("setDataID",  {id:'${paymentVoucher.id}',state:'${paymentVoucher.status}',type:'view'});
		$("#account-buttons-paymentVoucherPage").buttonWidget("disableButton","button-print");
		$("#account-buttons-paymentVoucherPage").buttonWidget("disableButton","button-preview");
        $("#account-buttons-paymentVoucherPage").buttonWidget("enableButton","button-oppaudit");
		var paymentVoucherStatus="${paymentVoucher.status}";
		if(paymentVoucherStatus==1 || paymentVoucherStatus==2 || theAuthorprintFlag==1){
  			$("#account-buttons-paymentVoucherPage").buttonWidget("enableButton","button-print");
  			$("#account-buttons-paymentVoucherPage").buttonWidget("enableButton","button-preview");
  		}
  	  	var $paymentVouchertable=$("#account-paymentVoucherAddPage-paymentVouchertable");
  	  	$paymentVouchertable.datagrid({
	  	  	fit:true,
	  	  	striped:true,
	 		method:'post',
	 		rownumbers:true,
	 		//idField:'id',
	 		singleSelect:true,
	 		showFooter:true,
  	 		data: JSON.parse('${paymentVoucherDetailList}'),
  	 		authority:tableColJson,
  	 		frozenColumns: tableColJson.frozen,
			columns:tableColJson.common,
  	 		onLoadSuccess:function(data){
		  	  	if(data.total<12){
			  	  	for(var i=data.total;i<12;i++){
			  	  		$paymentVouchertable.datagrid('appendRow', {});
			  	  	}
				}else{
		  	  		$paymentVouchertable.datagrid('appendRow', {});					
				}
		  	  	
  	 			$paymentVouchertable.datagrid('reloadFooter',[
					{customerid: '合计', amount: '0'}
				]);
  	 			footerReCalc();
  	 		},
        	onDblClickRow: function(rowIndex, rowData){ //选中行
  	 			if(rowData.customerid && rowData.customerid!=""){
  	 				orderDetailViewDialog(rowData);
  	 			}
        	}
  	  	}).datagrid("columnMoving");
  	});
  </script>
  </body>
</html>

