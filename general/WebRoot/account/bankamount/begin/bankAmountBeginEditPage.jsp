<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>部门日常费用修改</title>
  </head>
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
  	<div data-options="region:'center',border:false">
  		<form action="" id="bankAmountBegin-form-detailAdd" method="post">
   		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
   			<tr>
   				<td>编号：</td>
   				<td><input style="width: 130px;" class="easyui-validatebox" name="bankAmountBegin.id" value="${bankAmountBegin.id }" readonly="readonly"/></td>
   				<td>业务日期：</td>
   				<td><input type="text" id="bankAmountBegin-detail-businessdate" class="easyui-validatebox Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style="width: 130px;" value="${bankAmountBegin.businessdate}" required="required" name="bankAmountBegin.businessdate" /></td>
   				<td>状态：</td>
   				<td>
   					<select disabled="disabled" style="width: 80px;">
   						<option <c:if test="${bankAmountBegin.status=='1'}">selected="selected"</c:if>>新增</option>
   						<option <c:if test="${bankAmountBegin.status=='2'}">selected="selected"</c:if>>保存</option>
   						<option <c:if test="${bankAmountBegin.status=='3'}">selected="selected"</c:if>>审核通过</option>
   						<option <c:if test="${bankAmountBegin.status=='4'}">selected="selected"</c:if>>关闭</option>
   					</select>
   				</td>
   			</tr>
   				<td>银行名称：</td>
   				<td>
   					<input type="text" id="bankAmountBegin-detail-bank" name="bankAmountBegin.bankid" value="${bankAmountBegin.bankid }"/>
   				</td>
   				<td>金额：</td>
   				<td colspan="3">
   					<input type="text" id="bankAmountBegin-detail-amount" style="width: 130px;" name="bankAmountBegin.amount" autocomplete="off" value="${bankAmountBegin.amount }"/>
   				</td>
   			</tr>
   			<tr>
   				<td class="len100 left">备注：</td>
   				<td colspan="5" style="text-align: left">
   					<textarea id="bankAmountBegin-detail-remark" style="width: 400px;height: 50px;" name="bankAmountBegin.remark" ><c:out value="${bankAmountBegin.remark }"></c:out></textarea>
   					<input type="hidden" id="bankAmountBegin-detail-addtype" value="save"/>
   				</td>
   			</tr>
   			<tr>
   		</table>
   	</form>
   	</div>
  		<div data-options="region:'south',border:false">
  			<div class="buttonBG" style="height:26px;text-align:right;">
  				<input type="button" value="确定" name="savegoon" id="bankAmountBegin-detail-addbutton" />
  			</div>
  		</div>
  	</div>
    <script type="text/javascript">
    	$(function(){
    		$("#bankAmountBegin-detail-amount").numberbox({
    			precision:2,
				required:true
    		});
    		$("#bankAmountBegin-detail-bank").widget({
    			referwid:'RL_T_BASE_FINANCE_BANK',
    			width:130,
				singleSelect:true,
				required:true,
				onSelect:function(){
					$("#bankAmountBegin-detail-amount").focus();
				}
    		});
    		$("#bankAmountBegin-form-detailAdd").form({  
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
			    		$("#bankamountbegin-table-detail").datagrid("reload");
			    		var savetype = $("#bankAmountBegin-detail-addtype").val();
			    		if(savetype=="saveadd"){
			    			$("#bankAmountBegin-detail-bank").widget("clear");
			    			$("#bankAmountBegin-detail-amount").numberbox("clear");
			    		}else{
			    			$('#bankamountbegin-dialog-detail').dialog("close");
			    		}
			    	}else{
			    		$.messager.alert("提醒","保存失败");
			    	}
			    }  
			}); 
			$("#bankAmountBegin-detail-addbutton").click(function(){
				$("#bankAmountBegin-detail-addtype").val("save");
				$("#bankAmountBegin-form-detailAdd").attr("action", "account/bankamount/editBankAmountBegin.do");
				$("#bankAmountBegin-form-detailAdd").submit();
			});
			$("#bankAmountBegin-detail-amount").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					$("#bankAmountBegin-detail-addgobutton").focus();
				}
			});
    	});
    </script>
  </body>
</html>
