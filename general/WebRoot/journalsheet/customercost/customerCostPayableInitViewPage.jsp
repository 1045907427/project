<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>客户应付费用新增</title>
  </head>
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
  	<div data-options="region:'center',border:false">
  		<form action="" id="customerCostPayableInit-form-detailEdit" method="post">
   		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
   			<tr>
   				<td>编号：</td>
   				<td><input type="text" class="len150" name="customerCostPayableInit.id" readonly="readonly" value="${customerCostPayableInit.id }"/></td>
   				<td>业务日期：</td>
   				<td><input type="text" class="len150" id="customerCostPayableInit-detail-businessdate" class="easyui-validatebox Wdate" readonly="readonly" value="${customerCostPayableInit.businessdate }" required="required" name="customerCostPayableInit.businessdate" /></td>
   			</tr>
   			<tr>
   				<td>客户名称：</td>
   				<td colspan="3">
   					<input type="text" id="customerCostPayableInit-detail-customerid" name="customerCostPayableInit.customerid" style="width: 280px;" value="${customerCostPayableInit.customerid }" text="${customerCostPayableInit.customername }" readonly="readonly"/>
   					<span id="customerCostPayableInit-detail-customerid-showid" style="margin-left:5px;line-height:25px;">编号：${customerCostPayableInit.customerid }</span>
   				</td>
   			</tr>
   			<tr>
   				<td>费用分类：</td>
   				<td>
   					<input type="text" id="customerCostPayableInit-detail-expensesort" name="customerCostPayableInit.expensesort" value="${customerCostPayableInit.expensesort }" readonly="readonly"/>
   				</td>
   				<td>金额：</td>
   				<td>
   					<input type="text" id="customerCostPayableInit-detail-amount" class="len150" name="customerCostPayableInit.amount" value="${customerCostPayableInit.amount }" autocomplete="off" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td class="len100 left">备注：</td>
   				<td colspan="3" style="text-align: left">
   					<textarea id="customerCostPayableInit-detail-remark" style="width: 380px;height: 50px;" name="customerCostPayableInit.remark" readonly="readonly"><c:out value="${customerCostPayableInit.remark }"></c:out></textarea>
   					<input type="hidden" id="customerCostPayableInit-detail-addtype" value="save"/>
   				</td>
   			</tr>
   		</table>
   	</form>
   	</div>
  	</div>
    <script type="text/javascript">
    	$(function(){
    		$("#customerCostPayableInit-detail-amount").numberbox({
    			precision:2,
				required:true
    		});
    		$("#customerCostPayableInit-detail-customerid").customerWidget({
    			isall:true,
				singleSelect:true,
				required:true,
				onSelect:function(data){
					$("#customerCostPayableInit-detail-customerid-showid").text("编号："+ data.id);
					$("#customerCostPayableInit-detail-expensesort").focus();
				}
    		});
    		$("#customerCostPayableInit-detail-expensesort").widget({
	   			 referwid:'RT_T_BASE_FINANCE_EXPENSES_SORT_1',
	       		 width:150,
	       		 onlyLeafCheck:false,
	       		 singleSelect:true,
				 onSelect:function(){
					$("#customerCostPayableInit-detail-amount").focus();
				 }
    		});
    		$("#customerCostPayableInit-form-detailEdit").form({  
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
			    		$.messager.alert("提醒","保存成功");
			    		$("#customerCostPayableInit-table-detail").datagrid("reload");
		    			$('#customerCostPayableInit-dialog-detail').dialog("close");
			    	}else{
			    		$.messager.alert("提醒","保存失败");
			    	}
			    }  
			}); 
			$("#customerCostPayableInit-detail-amount").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					$("#customerCostPayableInit-detail-addgobutton").focus();
				}
			});
    	});
    </script>
  </body>
</html>
