<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>付款单合并确认</title>
  </head>
  
  <body>
   	<form action="account/payable/setPayOrderTransfer.do" method="post" id="account-form-PayOrder-transfer">
   		<table border="0" class="box_table">
   			<tr>
   				<td width="120">转出供应商名称:</td>
   				<td style="text-align: left;">
   					<input type="text" width="180" class="no_input" readonly="readonly" value="${supplierCapital.suppliername }"/>
   					<input type="hidden" name="transferOrder.outsupplierid" value="${supplierCapital.id }"/>
   				</td>
   				<td width="120">转出供应商剩余金额:</td>
   				<td>
   					<input type="text" id="account-transfer-remainderamount" class="easyui-numberbox no_input" data-options="precision:2" readonly="readonly" value="${supplierCapital.amount }"/>
   				</td>
     		</tr>
   			<tr>
   				<td width="120">转入供应商名称:</td>
   				<td style="text-align: left;">
   					<input id="account-transfer-supplierid" type="text" name="transferOrder.insupplierid"/>
   				</td>
   				<td width="120">转入供应商剩余金额:</td>
   				<td>
   					<input type="text" id="account-transferin-remainderamount" class="easyui-numberbox no_input" data-options="precision:2" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td width="120">转入金额:</td>
   				<td colspan="3" style="text-align: left;">
   					<input type="text" id="account-transferin-transferamount" name="transferOrder.transferamount" class="easyui-numberbox" data-options="precision:2,max:${supplierCapital.amount },required:true"/>
   				</td>
   			</tr>
			<tr>
				<td>备注</td>
				<td colspan="5"><input type="text" style="width: 99%;" name="transferOrder.remark"/></td>
			</tr>
   		</table>
   		<input type="hidden" name="ids" value="${ids}"/>
    </form>
   <script type="text/javascript">
   		$(function(){
   			$("#account-transfer-supplierid").widget({
   				referwid:'RL_T_BASE_BUY_SUPPLIER',
   				width:160,
				singleSelect:true,
				required:true,
				onSelect:function(data){
					$.ajax({   
			            url :'account/payable/getSupplierCapital.do?id='+data.id,
			            type:'post',
			            dataType:'json',
			            success:function(json){
			            	$("#account-transferin-remainderamount").val(formatterMoney(json.amount));
			            }
			        });
				}
   			});
   			
   			$("#account-form-PayOrder-transfer").form({  
			    onSubmit: function(){  
			    	var flag = $(this).form('validate');
			    	if(flag==false){
			    		return false;
			    	}
			    	loading("提交中..");
			    },  
			    success:function(data){
			    	//表单提交完成后 隐藏提交等待页面
			    	loaded();
			    	var json = $.parseJSON(data);
			    	if(json.flag){
			    		$.messager.alert("提醒","转账成功");
			    		var queryJSON = $("#account-form-query-PayOrderPage").serializeJSON();
			       		$("#account-buttons-PayOrderTransferPage").datagrid({
			       			url: 'account/payable/showPayOrderList.do',
							queryParams:queryJSON
			       		});
			       		$("#account-datagrid-supplierAccountPage").datagrid("reload");
			       		$("#account-dialog-transfer").dialog("close");
			       		$("#account-dialog-PayOrder-TransferSubmit").dialog("close");
			    	}else{
			    		$.messager.alert("提醒","转账失败");
			    	}
			    }  
			}); 
   		});
   		function transferSubmit(){
   			$.messager.confirm("提醒","是否进行付款单转账？",function(r){
				if(r){
					$("#account-form-PayOrder-transfer").submit();
				}
			});
   		}
   </script>
  </body>
</html>
