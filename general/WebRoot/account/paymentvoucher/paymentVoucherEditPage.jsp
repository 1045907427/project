<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>交款单编辑</title>
    <%@include file="/include.jsp" %>
  </head>
  <body>  
  <div class="easyui-panel" title="" data-options="fit:true,border:false">
	  <div class="easyui-layout" data-options="fit:true">
	  	<form action="account/paymentvoucher/editPaymentVoucher.do" id="account-form-paymentVoucherAddPage" method="post">
	  		<input type="hidden" id="account-paymentVoucherAddPage-addType" name="addType"/>
	  		<input type="hidden" id="account-paymentVoucherAddPage-saveaudit" name="saveaudit"/>
	  		<div data-options="region:'north',border:false" style="height:70px;">
	  			<table style="border-collapse:collapse;" border="0" cellpadding="5" cellspacing="5">
					<tr>
						<td style="width:50px;">编号：</td>
						<td><input type="text" class="len150" value="${paymentVoucher.id }" readonly="readonly" /></td>
						<td style="width:80px;">业务日期：</td>
						<td><input type="text" id="account-paymentVoucherAddPage-businessdate" name="paymentVoucher.businessdate" value="${paymentVoucher.businessdate }" readonly="readonly" class="len150 easyui-validatebox" required="true" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/></td>
						<td>状态：</td>
				    	<td style="width:135px;">			    		
	    					<select id="account-paymentVoucherAddPage-status" disabled="disabled" class="len150">
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
				    	<td><input type="text" id="account-paymentVoucherAddPage-collectuser" name="paymentVoucher.collectuserid" value="${paymentVoucher.collectuserid }"/></td>
						<td>银行名称:</td>
						<td>
							<input id="account-paymentVoucherAddPage-bank" type="text" name="paymentVoucher.bank" value="${paymentVoucher.bank }" />
						</td>
						<td >备注：</td>
						<td>
							<input type="text" style="width:150px;" id="account-paymentVoucherAddPage-remark" name="paymentVoucher.remark" value="${paymentVoucher.remark }"/>
						</td>	
					</tr>
				</table>
	  		</div>
	  		<div data-options="region:'center',border:false">
	  			<table id="account-paymentVoucherAddPage-paymentVouchertable"></table>
				<input type="hidden" id="account-paymentVoucherAddPage-paymentVoucherDetails" name="paymentVoucherDetails" value="<c:out value="${paymentVoucherDetailList}"/>" />
	  		</div>
	  		<input type="hidden" name="paymentVoucher.id" value="${paymentVoucher.id }" />
	  	</form> 
	  </div>
  </div>
  <div id="account-Button-tableMenu" class="easyui-menu" style="width:120px;display: none;">
      <div id="account-tableMenu-itemAdd" iconCls="button-add">添加</div>
      <div id="account-tableMenu-itemEdit" iconCls="button-edit">编辑</div>
      <div id="account-tableMenu-itemDelete" iconCls="button-delete">删除</div>
  </div>
  <script type="text/javascript">
  	var editRowIndex = undefined;

	$("#account-paymentVoucherAddPage-collectuser").widget({
		name:'t_account_paymentvoucher',
		col:'collectuserid',
		singleSelect:true,
        required:true,
        width:150
    });
    $("#account-paymentVoucherAddPage-bank").widget({
        name:'t_account_collection_order',
        col:'bank',
        singleSelect:true,
        width:150,
        view:true
    });
  	$(document).ready(function(){
  		$("#account-buttons-paymentVoucherPage").buttonWidget("setDataID",  {id:'${paymentVoucher.id}',state:'${paymentVoucher.status}',type:'edit'});

		$("#account-buttons-paymentVoucherPage").buttonWidget("enableButton","button-print");
		$("#account-buttons-paymentVoucherPage").buttonWidget("enableButton","button-preview");
		
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
  	 			editRowIndex=rowIndex;
  	 			if(rowData.customerid && rowData.customerid!=""){
	 					orderDetailEditDialog(rowData);
  	 			}else{
 					orderDetailAddDialog();
  	 			}
        	},
  	 		onRowContextMenu:function(e, rowIndex, rowData){
  	 			e.preventDefault();
  	 			var $contextMenu=$('#account-Button-tableMenu');
  	 			$contextMenu.menu('show', {
					left : e.pageX,
					top : e.pageY
				});
  	 			$paymentVouchertable.datagrid('selectRow', rowIndex);
				editRowIndex=rowIndex;
                $contextMenu.menu('enableItem', '#account-tableMenu-itemDelete');
  	 		}
  	  	}).datagrid("columnMoving");

  		//添加按钮事件
		$("#account-tableMenu-itemAdd").unbind("click").bind("click",function(){
			if($("#account-Button-tableMenu").menu('getItem',this).disabled){
				return false;
			}
			orderDetailAddDialog();			
		});
  	  	//编辑按钮事件
		$("#account-tableMenu-itemEdit").unbind("click").bind("click",function(){
			if($("#account-Button-tableMenu").menu('getItem',this).disabled){
				return false;
			}
			var data=$("#account-paymentVoucherAddPage-paymentVouchertable").datagrid('getSelected');
			orderDetailEditDialog(data);			
		});
		$("#account-tableMenu-itemDelete").unbind("click").bind("click",function(){
			if($("#account-Button-tableMenu").menu('getItem',this).disabled){
				return false;
			}
			var dataRow=$paymentVouchertable.datagrid('getSelected');
			if(dataRow!=null){
				$.messager.confirm("提示","是否要删除选中的行？", function(r){
					if (r){
	        			if(dataRow!=null){
							var rowIndex =$paymentVouchertable.datagrid('getRowIndex',dataRow);
							$paymentVouchertable.datagrid('updateRow',{
								index:rowIndex,
								row:{}
							});
							$paymentVouchertable.datagrid('deleteRow', rowIndex);
							var rowlen=$paymentVouchertable.datagrid('getRows').length; 
							if(rowlen<15){
								$paymentVouchertable.datagrid('appendRow', {});
							}
							editRowIndex=undefined;
							
							$paymentVouchertable.datagrid('clearSelections');
							footerReCalc();
	        			}
					}
				});	
			}		
		});

		
  	});
  </script>
  </body>
</html>
