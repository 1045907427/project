<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>添加交款单</title>
    <%@include file="/include.jsp" %>
  </head>
  <body>
  <div class="easyui-panel" title="" data-options="fit:true,border:false">
	  <div class="easyui-layout" data-options="fit:true">
	  	<form action="account/paymentvoucher/addPaymentVoucher.do" id="account-form-paymentVoucherAddPage" method="post">
	  		<input type="hidden" id="account-paymentVoucherAddPage-addType" name="addType"/>
	  		<input type="hidden" id="account-paymentVoucherAddPage-saveaudit" name="saveaudit"/>
	  		<input type="hidden" id="account-paymentVoucherAddPage-referpage" value="0"/>
	  		<div data-options="region:'north',border:false" style="height:70px;">
	  			<table style="border-collapse:collapse;" border="0" cellpadding="5" cellspacing="5">
					<tr>
						<td style="width:50px;">编号：</td>
						<td ><input type="text" class="len150" readonly="readonly" /></td>
						<td style="width:80px;">业务日期：</td>
						<td ><input type="text" id="account-paymentVoucherAddPage-businessdate" name="paymentVoucher.businessdate" value='${busdate }' readonly="readonly" class="len150 easyui-validatebox" required="true" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"/></td>
						<td>状态：</td>
				    	<td style="width:135px;"><select disabled="disabled" class="len150" ><option>新增</option></select></td>
					</tr>					
					<tr>
						<td >交款人：</td>
				    	<td><input type="text" id="account-paymentVoucherAddPage-collectuser" name="paymentVoucher.collectuserid" class="len150" value="${collectuserid }" /></td>
						<td>银行名称:</td>
						<td>
							<input id="account-paymentVoucherAddPage-bank" type="text" name="paymentVoucher.bank"/>
						</td>
						<td >备注：</td>
						<td >
							<input type="text" style="width:150px;" id="account-paymentVoucherAddPage-remark" name="paymentVoucher.remark"/>
						</td>	
					</tr>
				</table>
	  		</div>
	  		<div data-options="region:'center',border:false">
	  			<table id="account-paymentVoucherAddPage-paymentVouchertable"></table>
				<input type="hidden" id="account-paymentVoucherAddPage-paymentVoucherDetails" name="paymentVoucherDetails"/>
	  		</div>
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
  	$(document).ready(function(){
  		$("#account-buttons-paymentVoucherPage").buttonWidget("initButtonType", 'add');
  		$("#account-buttons-paymentVoucherPage").buttonWidget("disableButton","button-print");
  		$("#account-buttons-paymentVoucherPage").buttonWidget("disableButton","button-preview");
  	  	var $paymentVouchertable=$("#account-paymentVoucherAddPage-paymentVouchertable");
  	  	$paymentVouchertable.datagrid({
	  	  	fit:true,
	  	  	striped:true,
	 		method:'post',
	 		rownumbers:true,
	 		//idField:'id',
	 		singleSelect:true,
	 		showFooter:true,
  	 		data:[{},{},{},{},{},{},{},{},{},{},{},{}],
  	 		authority:tableColJson,
  	 		frozenColumns: tableColJson.frozen,
			columns:tableColJson.common,
  	 		onLoadSuccess:function(data){
  	 			$paymentVouchertable.datagrid('reloadFooter',[
					{customerid: '合计', amount: '0'}
				]);
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
                $contextMenu.menu('enableItem', '#account-tableMenu-itemEdit');
                $contextMenu.menu('enableItem', '#account-tableMenu-itemInsert');
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
		$("#account-paymentVoucherAddPage-remark").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				var flag = $("#account-form-paymentVoucherAddPage").form('validate');
				if(flag==false){
					$.messager.alert("提醒",'请先完善交款单基本信息');
					return false;
				}else{
	   				$("#account-paymentVoucherAddPage-remark").blur();
	   				orderDetailAddDialog();
				}
			}
	    });
  	});
  </script>
  </body>
</html>
