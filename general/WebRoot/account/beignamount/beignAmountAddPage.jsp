<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>客户应收款期初</title>
  </head>
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
  	<div data-options="region:'center',border:false">
  		<form action="" id="account-form-beignamountAdd" method="post">
   		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
   			<tr>
   				<td class="len120 left">编号：</td>
   				<td style="width: 100px;"><input style="width: 130px;" class="easyui-validatebox" name="beginAmount.id" <c:if test="${autoCreate == true }">readonly='readonly'</c:if> <c:if test="${autoCreate == false }">required="required"</c:if> /></td>
   				<td class="len120 left">业务日期：</td>
   				<td style="width: 100px;"><input type="text" id="account-beignamount-businessdate" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style="width: 130px;" value="${busdate }" name="beginAmount.businessdate" /></td>
   				<td class="len80 left">状态：</td>
   				<td style="width: 100px;"><select disabled="disabled" style="width: 100px;"><option>新增</option></select></td>
   			</tr>
   			<tr>
   				<td class="len120 left">客户：</td>
   				<td colspan="3" style="text-align: left;">
   					<input type="text" id="account-beignamount-customerid" name="beginAmount.customerid" style="width: 250px;"/>
   					<span id="account-beignamount-customerid-span" style="margin-left:5px;line-height:25px;"></span>
   				</td>
   				<td class="len80 left">金额：</td>
   				<td >
   					<input type="text" id="account-beignamount-amount" name="beginAmount.amount" class="len100" autocomplete="off"/>
   				</td>
   			</tr>
   			<tr>
   				<td class="len120 left">应收日期：</td>
   				<td style="text-align: left;">
   					<input type="text" name="beginAmount.duefromdate" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style="width: 130px;" />
   				</td>
   				<td class="len120 left">备注：</td>
   				<td colspan="3" style="text-align: left">
   					<input name="beginAmount.remark" style="width: 330px;"/>
   					<input type="hidden" id="account-beignamount-addtype" value="save"/>
   				</td>
   			</tr>
   			<tr>
   		</table>
   	</form>
   	</div>
  		<div data-options="region:'south',border:false">
  			<div class="buttonDetailBG" style="text-align:right;">
  				<security:authorize url="/account/beginamount/addBeginAmountSave.do">
	  				<input type="button" value="确定" name="savegoon" id="account-beignamount-addbutton" />
	  			</security:authorize>
	  			<security:authorize url="/account/beginamount/addBeginAmountSave.do">
		  			<input type="button" value="继续添加" name="savegoon" id="account-beignamount-addgobutton" />
		  		</security:authorize>
  			</div>
  		</div>
  	</div>
    <script type="text/javascript">
    	$(function(){
    		$("#account-beignamount-customerid").customerWidget({
	    		width:380,
				col:'customerid',
				singleSelect:true,
				ishead:true,
				isall:true,
				isopen:true,
				required:true,
				onSelect:function(data){
					$("#account-beignamount-customerid-span").html("编码:"+data.id);
					$("#account-beignamount-amount").focus();
				},
				onClear:function(){
  					$("#account-beignamount-collectionuser").widget('clear');
				}
    		});
    		$("#account-beignamount-amount").numberbox({
    			precision:2,
				required:true
    		});
    		$("#account-form-beignamountAdd").form({  
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
			    		$("#account-datagrid-beignAmountPage").datagrid("reload");
			    		var savetype = $("#account-beignamount-addtype").val();
			    		if(savetype=="saveadd"){
			    			$("#account-beignamount-customerid").customerWidget("clear");
			    			$("#account-beignamount-amount").numberbox("setValue",'');
			    			//$("#account-form-beignamountAdd").form("reset");
			    			$("#account-beignamount-customerid-span").html("");
			    			$("#account-beignamount-customerid").focus();
			    		}else{
			    			$('#account-panel-beignAmount-addpage').dialog("close");
			    		}
			    	}else{
			    		$.messager.alert("提醒","保存失败");
			    	}
			    }  
			}); 
			$("#account-beignamount-addbutton").click(function(){
				$("#account-beignamount-addtype").val("save");
				$("#account-form-beignamountAdd").attr("action", "account/beginamount/addBeginAmountSave.do");
				$("#account-form-beignamountAdd").submit();
			});
			$("#account-beignamount-addgobutton").click(function(){
				$("#account-beignamount-addtype").val("saveadd");
				$("#account-form-beignamountAdd").attr("action", "account/beginamount/addBeginAmountSave.do");
				$("#account-form-beignamountAdd").submit();
			});
			$("#account-beignamount-amount").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
		   			$("#account-beignamount-addgobutton").focus();
				}
			});
    	});
    </script>
  </body>
</html>
