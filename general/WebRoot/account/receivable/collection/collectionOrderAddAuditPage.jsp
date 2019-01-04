<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>收款单</title>
  </head>
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
  	<div data-options="region:'center',border:false">
  		<form action="" id="account-form-collectionOrderAdd" method="post">
   		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
   			<tr>
   				<td class="len100 left">编号：</td>
   				<td style="width: 100px;"><input style="width: 130px;" class="easyui-validatebox" name="collectionOrder.id" <c:if test="${autoCreate == true }">readonly='readonly'</c:if> <c:if test="${autoCreate == false }">required="required"</c:if> /></td>
   				<td class="len120 left">业务日期：</td>
   				<td style="width: 100px;"><input type="text" id="account-collectionOrder-businessdate" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style="width: 130px;" value="${date }" name="collectionOrder.businessdate" /></td>
   				<td class="len80 left">状态：</td>
   				<td style="width: 110px;"><select disabled="disabled" style="width: 100px;"><option>新增</option></select></td>
   			</tr>
   			<tr>
   				<td class="len100 left">客户：</td>
   				<td colspan="3" style="text-align: left;">
   					<input type="text" id="account-collectionOrder-customerid" name="collectionOrder.customerid" value="${customerid }" text="${customername }" style="width: 250px;"/>
   					<span id="account-collectionOrder-customerid-span" style="margin-left:5px;line-height:25px;"></span>
   					<input type="hidden" id="account-collectionOrder-handlerid" name="collectionOrder.handlerid" class="len130"/>
   				</td>
   				<td class="len80 left">收款人：</td>
   				<td style="text-align: left">
   					<input type="text" id="account-collectionOrder-collectionuser" name="collectionOrder.collectionuser" style="width: 100px;"/>
   				</td>
   			</tr>
   			<tr>
   				<td class="len80 left">收款金额：</td>
   				<td >
   					<input type="text" id="account-collectionOrder-amount" name="collectionOrder.amount" class="len130" autocomplete="off"/>
   				</td>
   				<td class="len80 left">收款类型：</td>
   				<td>
   					<input type="text" id="account-collectionOrder-collectiontype" name="collectionOrder.collectiontype"/>
   				</td>
   			</tr>
   			<tr>
   				<td>银行名称：</td>
   				<td>
   					<input type="text" id="account-collectionOrder-bank" name="collectionOrder.bank"/>
   				</td>
   				<td class="left">银行部门：</td>
   				<td colspan="3">
   					<input type="text" id="account-collectionOrder-bankdeptid" name="collectionOrder.bankdeptid"/>
   				</td>
   			</tr>
   			<tr>
   				<td class="len100 left">备注：</td>
   				<td colspan="5" style="text-align: left">
   					<textarea style="width: 400px;height: 50px;" name="collectionOrder.remark" ></textarea>
   				</td>
   			</tr>
   			<tr>
   		</table>
   	</form>
   	</div>
  		<div data-options="region:'south',border:false">
  			<div class="buttonDetailBG" style="height:30px;text-align:right;">
                <security:authorize url="/account/receivable/auditCustomerPushBanlance.do">
		  			<input type="button" value="保存并审核" name="savegoon" id="account-collectionOrder-addAuditbutton" />
		  		</security:authorize>
  			</div>
  		</div>
  	</div>
    <script type="text/javascript">
    	$(function(){
    		$("#account-collectionOrder-customerid").customerWidget({
    			name:'t_account_collection_order',
	    		width:380,
				col:'customerid',
				singleSelect:true,
				ishead:true,
				isall:true,
				isopen:true,
				required:true,
				onSelect:function(data){
					$("#account-collectionOrder-customerid-span").html("编码:"+data.id);
					$("#account-collectionOrder-handlerid").val(data.contact);
		    		$("#account-collectionOrder-collectionuser").widget("setValue",data.salesuserid);
		    		$("#account-collectionOrder-collectionuser").widget('clear');
					$("#account-collectionOrder-amount").focus();
				},
				onClear:function(){
  					$("#account-collectionOrder-collectionuser").widget('clear');
				}
    		});
    		$("#account-collectionOrder-collectionuser").widget({
    			name:'t_account_collection_order',
    			col:'collectionuser',
    			singleSelect:true,
    			width:100,
    			onlyLeafCheck:true
    		});
    		$("#account-collectionOrder-collectiontype").widget({
    			name:'t_account_collection_order',
	    		width:130,
				col:'collectiontype',
				singleSelect:true,
				required:true
    		});
    		$("#account-collectionOrder-amount").numberbox({
    			precision:2,
				required:true
    		});
    		$("#account-collectionOrder-bankdeptid").widget({
    			referwid:'RT_T_SYS_DEPT',
    			width:130,
				singleSelect:true,
				onlyLeafCheck:false
    		});
    		$("#account-collectionOrder-bank").widget({
    			referwid:'RL_T_BASE_FINANCE_BANK',
    			width:132,
				singleSelect:true,
				required:true,
				onSelect:function(data){
					if(null != data.bankdeptid && "" != data.bankdeptid){
						$("#account-collectionOrder-bankdeptid").widget('setValue',data.bankdeptid);
					}else{
						$("#account-collectionOrder-bankdeptid").widget('clear');
					}
					$("#account-collectionOrder-addgobutton").focus();
				}
    		});
    		$("#account-form-collectionOrderAdd").form({  
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
			    	if(json.addflag){
			    		if(json.auditflag){
				    		$.messager.alert("提醒","保存并审核成功");
				    		$('#account-panel-collectionOrder-addauditpage1').dialog("close");
                            if($("#account-dialog-collection-relate_invoice-content").size() > 0){
                                $("#account-dialog-collection-relate_invoice-content").dialog('refresh');
                            }
                            if($("#sales-dialog-receiptWriteoff1").size() > 0){
                                $("#sales-dialog-receiptWriteoff1").dialog('refresh');
                            }
				    	}else{
				    		$.messager.alert("提醒","保存成功,审核失败;<br>"+json.msg);
				    	}
			    	}else{
			    		$.messager.alert("提醒","保存失败!");
			    	}
			    }  
			}); 
			$("#account-collectionOrder-addAuditbutton").click(function(){
				$("#account-form-collectionOrderAdd").attr("action", "account/receivable/addCollectionOrderSaveAudit.do");
				$("#account-form-collectionOrderAdd").submit();
			});
			$("#account-collectionOrder-amount").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
		   			$("#account-collectionOrder-addgobutton").focus();
				}
			});
    	});
    </script>
  </body>
</html>
