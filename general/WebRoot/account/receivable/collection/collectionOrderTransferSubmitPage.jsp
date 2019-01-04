<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>收款单合并确认</title>
  </head>
  
  <body>
   	<form action="account/receivable/setCollectionOrderTransfer.do" method="post" id="account-form-collectionOrder-transfer">
   		<table border="0" class="box_table">
   			<tr>
   				<td width="120">转出客户名称:</td>
   				<td style="text-align: left;">
   					<input type="text" width="180" class="no_input" readonly="readonly" value="${customerCapital.customername }"/>
   					<input type="hidden" name="transferOrder.outcustomerid" value="${customerCapital.id }"/>
   				</td>
   				<td width="120">转入客户名称:</td>
   				<td style="text-align: left;">
   					<input id="account-transfer-customerid" type="text" name="transferOrder.incustomerid"/>
   				</td>
     		</tr>
   			<tr>
   				<td width="120">转出客户剩余金额:</td>
   				<td>
   					<input type="text" id="account-transfer-remainderamount" class="no_input" data-options="precision:2" readonly="readonly" value="${customerCapital.amount }"/>
   				</td>
   				
   				<td width="120">转入客户剩余金额:</td>
   				<td>
   					<input type="text" id="account-transferin-remainderamount" class="no_input" data-options="precision:2" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td width="120">转出收款单:</td>
   				<td>
   					<input type="text" id="account-transfer-out-collectionorder"  name="transferOrder.outorderid"/>
   				</td>
                <td width="120">转出金额:</td>
                <td>
                    <input type="text" id="account-transferin-transferamount" name="transferOrder.transferamount"  data-options="precision:2,min:0,max:${customerCapital.amount },required:true"/>
                </td>
   			</tr>
			<tr>
				<td>备注</td>
				<td colspan="5"><input type="text" style="width: 94%;" name="transferOrder.remark"/></td>
			</tr>
   		</table>
   		<input type="hidden" name="ids" value="${ids}"/>
    </form>
   <script type="text/javascript">
   		$(function(){
   			$("#account-transfer-customerid").customerWidget({
   				width:160,
				singleSelect:true,
				required:true,
				isall:true,
				onSelect:function(data){
					$.ajax({   
			            url :'account/receivable/getCustomerCapital.do?id='+data.id,
			            type:'post',
			            dataType:'json',
			            success:function(json){
			            	$("#account-transferin-remainderamount").val(json.camount);
			            }
			        });
				}
   			});
   			$("#account-transfer-out-collectionorder").widget({
   				referwid:'RL_T_ACCOUNT_COLLECTION_ORDER',
   				width:165,
				singleSelect:true,
				required:true,
				param:[{field:'customerid',op:'equal',value:'${customerCapital.id}'}],
				onSelect:function(data){
//					$("#account-transferin-transferamount").numberbox({
//						required:true,
//						precision:2,
//						max:data.remainderamount,
//						min:0
//					});
					$("#account-transferin-transferamount").val(data.remainderamount);
				}
   			});
   			$("#account-form-collectionOrder-transfer").form({  
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
			    		var queryJSON = $("#account-form-query-collectionOrderPage").serializeJSON();
			       		$("#account-datagrid-customerAccountPage").datagrid("reload");
			       		$("#account-dialog-transfer").dialog("close");
			    	}else{
			    		$.messager.alert("提醒","转账失败");
			    	}
			    }  
			}); 
   		});
   		function transferSubmit(){
   			$.messager.confirm("提醒","是否进行收款单转账？",function(r){
				if(r){
					$("#account-form-collectionOrder-transfer").submit();
				}
			});
   		}
   </script>
  </body>
</html>
