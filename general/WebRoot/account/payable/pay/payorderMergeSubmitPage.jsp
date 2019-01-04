<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>付款单合并确认</title>
  </head>
  
  <body>
   	<form action="account/payable/setPayorderMerge.do" method="post" id="account-form-payorder-merge">
   		<table class="tablenormal">
   			<tr>
   				<td colspan="6" style="text-align: center;font-weight: bold;">请选择合并到哪张付款单中</td>
   			</tr>
   			<tr>
   				<td>选择</td>
   				<td>付款单编号</td>
   				<td>付款金额</td>
   				<td>已核销金额</td>
   				<td>剩余金额</td>
   				<td>备注</td>
   			</tr>
  		<c:forEach items="${list }" var="list" varStatus="status">
  			<tr>
  				<td><input type="radio" name="orderid" value="${list.id}" <c:if test="${status.index==0}"> checked="checked"</c:if>/></td>
  				<td>${list.id}</td>
  				<td style="text-align: right;padding-right: 15px;">${list.amount}</td>
  				<td style="text-align: right;padding-right: 15px;">
  					${list.writeoffamount}
  				</td>
  				<td style="text-align: right;padding-right: 15px;">
  					${list.remainderamount}
  				</td>
  				<td>${list.remark}</td>
  			</tr>
		</c:forEach>
			<tr>
				<td></td>
				<td style="text-align: right;padding-right: 15px;">合计</td>
				<td style="text-align: right;padding-right: 15px;">${totalAmount }</td>
				<td style="text-align: right;padding-right: 15px;">${totalWriteoffamount }</td>
				<td style="text-align: right;padding-right: 15px;">${totalRemainderamount }</td>
				<td></td>
			</tr>
			<tr>
				<td>备注</td>
				<td colspan="5"><input type="text" style="width: 99%;" name="remark"/></td>
			</tr>
   		</table>
   		<input type="hidden" name="ids" value="${ids}"/>
    </form>
   </div>
   <script type="text/javascript">
   		$(function(){
   			$("#account-form-payorder-merge").form({  
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
			    		$.messager.alert("提醒","合并成功");
			    		var queryJSON = $("#account-form-query-payorderPage").serializeJSON();
			       		$("#account-buttons-payorderMergePage").datagrid({
			       			url: 'account/payable/showPayorderList.do',
							queryParams:queryJSON
			       		});
			       		$("#account-dialog-payorder-MergeSubmit").dialog("close");
			    	}else{
			    		$.messager.alert("提醒","合并失败");
			    	}
			    }  
			}); 
   		});
   		function mergeSubmit(){
   			var orderid = $('input[name="orderid"]:checked').val();
   			$.messager.confirm("提醒","是否付款单余额合并到<br/>付款单:"+orderid+"中？",function(r){
				if(r){
					$("#account-form-payorder-merge").submit();
				}
			});
   		}
   </script>
  </body>
</html>
