<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>客户应收款冲差添加页面</title>
  </head>
  
  <body>
   	<form action="account/receivable/addCustomerPushBanlanceBySalesInvoice.do" method="post" id="account-form-customerPushBanlance-add">
   		<table  border="0" style="width: 380px;">
   			<tr>
   				<td style="text-align: right;width: 100px;">冲差方式:</td>
   				<td style="text-align: left;">
   					<select id="account-customerPushBanlance-customertype" name="pushtype" style="width: 200px;">
   						<c:if test="${salesInvoice.customerid!=salesInvoice.chlidcustomerid}"><option value="1" >按总店冲差</option></c:if>
   						<option value="2">按门店冲差</option>
   					</select>
   				</td>
   			</tr>
   			<tr id="tr-account-customerPushBanlance-pcustomerid" <c:if test="${salesInvoice.customerid==salesInvoice.chlidcustomerid}">style="display: none;"</c:if>>
   				<td style="text-align: right;width: 100px;">总店:</td>
   				<td style="text-align: left;">
   					<select style="width: 200px;">
   						<c:forEach items="${customerArr}" var="list">
						<c:if test="${salesInvoice.customerid==list.id}"><option value="${list.id }">${list.name }</option></c:if>
   						</c:forEach>
   					</select>
   				</td>
   			</tr>
   			<tr id="tr-account-customerPushBanlance-customerid" <c:if test="${salesInvoice.customerid!=salesInvoice.chlidcustomerid}">style="display: none;"</c:if>>
   				<td style="text-align: right;width: 100px;">客户:</td>
   				<td style="text-align: left;">
   					<select id="account-customerPushBanlance-customerid" name="customerPushBalance.customerid" style="width: 200px;">
   						<c:forEach items="${customerArr}" var="list">
						<c:if test="${salesInvoice.customerid!=list.id || salesInvoice.customerid==salesInvoice.chlidcustomerid}"><option value="${list.id }">${list.name }</option></c:if>
   						</c:forEach>
   					</select>
   				</td>
   			</tr>
   			<tr>
   				<td style="text-align: right;width: 100px;">冲差类型:</td>
   				<td style="text-align: left;">
   					<select id="account-customerPushBanlance-pushtype" name="customerPushBalance.pushtype" style="width: 200px;">
						<c:forEach items="${pushList}" var="list">
							<option value="${list.code }">${list.codename }</option>
						</c:forEach>
   					</select>
   				</td>
   			</tr>
   			<tr>
   				<td style="text-align: right;width: 100px;">费用科目:</td>
   				<td style="text-align: left;">
   					<input id="account-customerPushBanlance-subject" name="customerPushBalance.subject" style="width: 200px;"/>
   				</td>
   			</tr>
   			<tr>
   				<td style="text-align: right;width: 100px;">商品品牌:</td>
   				<td style="text-align: left;">
   					<input id="account-customerPushBanlance-brand" name="customerPushBalance.brandid" style="width: 200px;"/>
   				</td>
   			</tr>
   			<tr>
   				<td style="text-align: right;width: 100px;">冲差金额:</td>
   				<td style="text-align: left;">
   					<input type="text" id="account-customerPushBanlance-amount" name="customerPushBalance.amount" style="width: 200px;"/>
   				</td>
   			</tr>
   			<tr>
   				<td style="text-align: right;width: 100px;">备注:</td>
   				<td style="text-align: left;">
   					<input type="text" name="customerPushBalance.remark" style="width: 200px;"/>
   				</td>
   			</tr>
   		</table>
   		<input type="hidden" name="invoiceid" value="${id}"/>
   		<input type="hidden" name="customerPushBalance.invoiceid" value="${id}"/>
   		<input type="hidden" value="${salesInvoice.businessdate}" name="customerPushBalance.businessdate" />
    </form>
    <script type="text/javascript">
    	var detailrows = $("#account-datagrid-salesInvoiceAddPage").datagrid('getRows');
   		var brandid = "";
   		for(var i=0; i<detailrows.length; i++){
   			var rowJson = detailrows[i];
   			if(rowJson.goodsInfo != undefined && (rowJson.isdiscount==null ||rowJson.isdiscount=='0')){
   				var brand= rowJson.goodsInfo.brand;
   				if(brand!=null && brand!=""){
	   				if(brandid.indexOf(brand)==-1){
	   					if(brandid==""){
	   						brandid = brand;
	   					}else{
		   					brandid += ","+brand;
		   				}
	   				}
   				}
   			}
   		}
    	$(function(){
    		$("#account-customerPushBanlance-customertype").change(function(){
    			var val = $(this).val();
    			if(val=="1"){
    				$("#tr-account-customerPushBanlance-pcustomerid").show();
    				$("#tr-account-customerPushBanlance-customerid").hide();
    			}else{
    				$("#tr-account-customerPushBanlance-pcustomerid").hide();
    				$("#tr-account-customerPushBanlance-customerid").show();
    			}
    		});
    		$("#account-customerPushBanlance-amount").numberbox({
    			precision:2,
				required:true
    		});
    		$("#account-customerPushBanlance-subject").widget({
    			name:'t_account_customer_push_balance',
	    		width:200,
				col:'subject',
				singleSelect:true
    		});
    		$("#account-customerPushBanlance-brand").widget({
    			name:'t_account_customer_push_balance',
	    		width:200,
				col:'brandid',
				param:[{field:'id',op:'in',value:brandid}],
				singleSelect:true,
				required:true
    		});
    		$("#account-form-customerPushBanlance-add").form({  
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
			    		$.messager.alert("提醒","冲差成功,生成冲差单："+json.pushid);
			    		$('#account-panel-customerPushBanlance-addpage').dialog("close");
			    		$("#account-panel-salesInvoicePage").panel("refresh");
			    	}else{
			    		$.messager.alert("提醒","冲差失败");
			    	}
			    }  
			}); 
    	});
    </script>
  </body>
</html>
