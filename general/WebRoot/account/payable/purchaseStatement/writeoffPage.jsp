<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>付款单核销指定采购发票</title>
  </head>
  
  <body>
   	<form action="account/payable/auditWriteoffPayorder.do" method="post" id="account-form-writeoff">
   		<table  border="0" class="box_table">
   			<tr>
   				<td width="120">供应商名称:</td>
   				<td style="text-align: left;">
   					<input type="hidden" name="supplierid" value="${supplierCapital.id}"/>
   					<input type="text" width="180" class="no_input" readonly="readonly" value="${supplierCapital.suppliername }"/>
   				</td>
   				<td width="120">剩余金额:</td>
   				<td>
   					<input type="text" id="account-auditwriteoff-remainderamount" class="easyui-numberbox no_input" data-options="precision:2" readonly="readonly" value="${supplierCapital.amount }"/>
   				</td>
     		</tr>
   			<tr>
   				<td width="120">核销后剩余金额:</td>
   				<td style="text-align: left;">
   					<input type="text" id="account-writeoff-last-remainderamount" class="easyui-numberbox no_input" data-options="precision:2" readonly="readonly" value="${supplierCapital.amount-invoiceAmount }"/>
   				</td>
   			</tr>
   		</table>
   		<table class="tablenormal">
   			<tr>
   				<td>采购发票编号</td>
   				<td>应付金额</td>
   				<td>核销金额</td>
   				<td>尾差金额</td>
   				<td>备注</td>
   			</tr>
  		<c:forEach items="${purchaseInvoiceList }" var="list" varStatus="status">
  			<tr>
  				<td>${list.id}
  					<input type="hidden" name="purchaseStatementList[${status.index }].billid" value="${list.id}"/>
  				</td>
  				<td><input type="text" id="${list.id}-amount" style="width: 100px;" class="easyui-numberbox" data-options="precision:2" value="${list.invoiceamount}" readonly="readonly"/></td>
  				<td>
  					<input type="text" name="purchaseStatementList[${status.index }].writeoffamount" id="${list.id}-invoiceamount" style="width: 100px;" class="easyui-numberbox invoiceamount" 
  					data-options='precision:2,required:true,max:${list.invoiceamount},onChange:function(newValue,oldValue){
  						var amount = $("#${list.id}-amount").numberbox("getValue");
  						$("#${list.id}-tailamount").numberbox("setValue",Number(amount)-Number(newValue));
  						countInvocieAmount();
  					}' value="${list.invoiceamount}"/>
  				</td>
  				<td>
  					<input type="text" name="purchaseStatementList[${status.index }].tailamount" id="${list.id}-tailamount" style="width: 100px;" class="easyui-numberbox tailamount" 
  					data-options='precision:2,required:true,onChange:function(newValue,oldValue){
  						var amount = $("#${list.id}-amount").numberbox("getValue");
  						$("#${list.id}-invoiceamount").numberbox("setValue",Number(amount)-Number(newValue));
  						countTailAmount();
  					}' value="0"/>
  				</td>
  				<td><input type="text" name="purchaseStatementList[${status.index }].remark" style="width: 100px;"/></td>
  			</tr>
		</c:forEach>
			<tr>
				<td style="text-align: right;padding-right: 15px;">合计</td>
				<td style="text-align: right;padding-right: 15px;">${invoiceAmount }</td>
				<td id="account-writeoff-invoicetotal" style="text-align: right;padding-right: 15px;">${invoiceAmount }</td>
				<td id="account-writeoff-tailtotal" style="text-align: right;padding-right: 15px;"></td>
				<td></td>
			</tr>
   		</table>
    </form>
   <script type="text/javascript">
   		var writeoff_AjaxConn = function (Data, Action) {
		    var MyAjax = $.ajax({
		        type: 'post',
		        cache: false,
		        url: Action,
		        data: Data,
		        async: false,
		        success:function(json){
            		loaded();
            	}
		    })
		    return MyAjax.responseText;
		}
   		$(function(){
   			$("#account-form-writeoff").form({  
			    onSubmit: function(){  
			    	var flag = $(this).form('validate');
			    	if(flag==false){
			    		return false;
			    	}
			    	var ret = writeoff_AjaxConn({id:'${supplierCapital.id}'},'account/payable/getSupplierCapital.do');
			    	var retjson = $.parseJSON(ret);
			    	var lastamount = Number(retjson.amount) - Number('${invoiceAmount}');
			    	if(Number(lastamount) < 0){
			    		$("#account-auditwriteoff-remainderamount").numberbox('setValue',retjson.amount);
			    		$("#account-writeoff-last-remainderamount").numberbox('setValue',lastamount);
			    		$.messager.alert("提醒","余额不足");
			    		return false;
			    	}
			    	loading("提交中..");
			    },  
			    success:function(data){
			    	//表单提交完成后 隐藏提交等待页面
			    	loaded();
			    	var json = $.parseJSON(data);
			    	if(json.flag){
			    		$.messager.alert("提醒","核销成功");
			    		$("#account-dialog-writeoff-info-content").dialog("close");
			    		$("#account-dialog-writeoff").dialog("close");
			    		//$("#account-datagrid-purchaseInvoiceList").datagrid("reload");
			    		if (top.$('#tt').tabs('exists','采购发票查看')){
			    			top.closeTab('采购发票查看');
		    			}

			    	}else{
			    		if(json.msg == undefined){
			    			$.messager.alert("提醒","核销失败");
			    		}else{
			    			$.messager.alert("提醒",json.msg);
			    		}
			    	}
			    }  
			}); 
   		});
   		function writeOffSubmit(){
   			var invoiceArr = $(".invoiceamount");
   			var invoiceTotal = Number(0);
   			$.each(invoiceArr,function(i){
	            var invoice= $(invoiceArr[i]).numberbox("getValue");
	            invoiceTotal = formatterMoney(Number(invoiceTotal)+Number(invoice));
	        }); 
	        var remainderamount = formatterMoney($("#account-auditwriteoff-remainderamount").numberbox("getValue"));
	        if(Number(remainderamount) < Number(invoiceTotal)){
	        	$.messager.alert("提醒","付款单余额不足");
	        	return false;
	        }
   			$.messager.confirm("提醒","是否确认核销？",function(r){
				if(r){
					$("#account-form-writeoff").submit();
				}
			});
   		}
   		function countInvocieAmount(){
   			var invoiceArr = $(".invoiceamount");
   			var invoiceTotal = 0;
   			$.each(invoiceArr,function(i){  
	            var invoice= $(invoiceArr[i]).numberbox("getValue");  
	            invoiceTotal = formatterMoney(Number(invoiceTotal)+Number(invoice));
	        }); 
	        var remainderamount = formatterMoney($("#account-auditwriteoff-remainderamount").numberbox("getValue"));
	        if(Number(remainderamount)<Number(invoiceTotal)){
	        	$.messager.alert("提醒","付款单余额不足");
	        }
	        $("#account-writeoff-invoicetotal").html(invoiceTotal);
   			var remainderamount = $("#account-auditwriteoff-remainderamount").numberbox("getValue");
	        $("#account-writeoff-last-remainderamount").numberbox("setValue",Number(remainderamount)-Number(invoiceTotal));
   		}
   		function countTailAmount(){
   			var tailArr = $(".tailamount");
   			var tailTotal = 0;
   			$.each(tailArr,function(i){  
	            var tail= $(tailArr[i]).numberbox("getValue");  
	            tailTotal = Number(tailTotal)+Number(tail);
	        }); 
	        $("#account-writeoff-tailtotal").html(formatterMoney(tailTotal));
   		}
   </script>
  </body>
</html>
