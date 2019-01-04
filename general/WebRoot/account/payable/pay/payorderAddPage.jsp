<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>付款单</title>
  </head>
  
  <body>
  	<div class="easyui-layout" data-options="fit:true">
	  	<div data-options="region:'center',border:false">
	  		<form action="" id="account-form-payorderAdd" method="post">
		   		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
		   			<tr>
		   				<td class="len60 left">编号:</td>
		   				<td><input class="len130 easyui-validatebox" name="payorder.id" <c:if test="${autoCreate == true }">readonly='readonly'</c:if> <c:if test="${autoCreate == false }">required="required"</c:if> /></td>
		   				<td class="len60 left">业务日期:</td>
		   				<td><input type="text" id="account-payorder-businessdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" class="len130" value="${busdate }" name="payorder.businessdate" /></td>
		   				<td class="len60 left">状&nbsp;&nbsp;态:</td>
		   				<td><select disabled="disabled" class="len130"><option>新增</option></select></td>
		   			</tr>
		   			<tr>
		   				<td>供应商:</td>
		   				<td colspan="3" style="text-align: left;"><input type="text" id="account-payorder-supplierid" name="payorder.supplierid" style="width: 250px;"/>
		   					<span id="account-payorder-supplierid-span" style="margin-left:5px;line-height:25px;"></span>
		   				</td>
                        <td>付款类型:</td>
                        <td><input type="text" id="account-payorder-paytype" name="payorder.paytype" class="len130"/></td>
		   			</tr>
		   			<tr>
		   				<td>银行名称:</td>
		   				<td class="selectBank">
		   					<input id="account-payorder-bank" type="text" name="payorder.bank"/>
		   				</td>
		   				<td>是否预付:</td>
		   				<td>
		   					<select name="payorder.prepay" class="len130">
		   						<option value="1">是</option>
		   						<option value="0" selected="selected">否</option>
		   					</select>
		   				</td>
		   			</tr>
		   			<tr>
		   				<td>采购部门:</td>
		   				<td>
		   					<input type="text" id="account-payorder-buydept" name="payorder.buydeptid" class="len130"/>
		   				</td>
		   				<td>采购员:</td>
		   				<td style="text-align: left">
		   					<input type="text" id="account-payorder-buyuser" name="payorder.buyuserid" class="len130"/>
		   				</td>
		   				<td>付款金额:</td>
		   				<td >
		   					<input type="text" id="account-payorder-amount" name="payorder.amount" class="len130"/>
		   				</td>
		   			</tr>
		   			<!--<tr>
		   				<td>已核销金额:</td>
		   				<td><input type="text" class="len130" readonly="readonly"/></td>
		   				<td>剩余金额:</td>
		   				<td><input type="text" class="len130" readonly="readonly"/></td>
		   			</tr>-->
		   			<tr>
		   				<td>备注:</td>
		   				<td colspan="5" style="text-align: left">
		   					<textarea style="width: 533px;height: 60px;" name="payorder.remark"></textarea>
		   					<input type="hidden" id="account-payorder-addtype" value="save"/>
		   				</td>
		   			</tr>
		   			<tr>
		   		</table>
		   	</form>
	  	</div>
	  	<div data-options="region:'south',border:false">
  			<div class="buttonDetailBG" style="height:30px;text-align:right;">
  				<input type="button" value="确定" name="savegoon" id="account-payorder-addbutton" />
	  			<input type="button" value="继续添加" name="savegoon" id="account-payorder-addgobutton" />
  			</div>
  		</div>
  	</div>
    <script type="text/javascript">
    	$(function(){
    		$("#account-payorder-supplierid").supplierWidget({
    			name:'t_account_purchase_payorder',
	    		width:210,
				col:'supplierid',
				singleSelect:true,
				required:true,
				onSelect:function(data){
					$("#account-payorder-supplierid-span").html("编码:"+data.id);
					$("#account-payorder-buyuser").widget({
		    			name:'t_account_purchase_payorder',
		    			col:'buyuserid',
		    			singleSelect:true,
		    			width:130,
		    			onlyLeafCheck:true,
		    			async:false,
		    			param:[{field:'deptid',op:'equal',value:data.buydeptid}]
		    		});
		    		$("#account-payorder-buyuser").widget('clear');
		    		$("#account-payorder-buydept").widget('clear');
		  			$("#account-payorder-buydept").widget('setValue', data.buydeptid);
		  			$("#account-payorder-buyuser").widget('setValue', data.buyuserid);
				},
				onClear:function(){
					$("#account-payorder-buydept").widget('clear');
  					$("#account-payorder-buyuser").widget('clear');
				}
    		});
    		$("#account-payorder-paytype").widget({
    			name:'t_account_purchase_payorder',
	    		width:130,
				col:'paytype',
				singleSelect:true,
				required:true
    		});
    		$("#account-payorder-bank").widget({
				name:'t_account_purchase_payorder',
	    		width:130,
				col:'bank',
				disable:true,
				singleSelect:true,
				required:true
			});
    		$("#account-payorder-buydept").widget({
    			name:'t_account_purchase_payorder',
	    		width:130,
				col:'buydeptid',
				singleSelect:true,
				setValueSelect:false,
				onSelect:function(data){
					$("#account-payorder-buyuser").widget({
		    			name:'t_account_purchase_payorder',
			    		width:130,
						col:'buyuserid',
						singleSelect:true,
						async:false,
						param:[{field:'deptid',op:'equal',value:data.id}]
					});
					$("#account-payorder-buyuser").widget("clear");
				}
    		});
    		$("#account-payorder-paytype").widget({
    			name:'t_account_purchase_payorder',
	    		width:130,
				col:'paytype',
				singleSelect:true
    		});
    		$("#account-payorder-amount").numberbox({
    			precision:2,
				groupSeparator:',',
				required:true
    		});
    		$("#account-form-payorderAdd").form({  
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
			    		$("#account-datagrid-payorderPage").datagrid("reload");
			    		var savetype = $("#account-payorder-addtype").val();
			    		if(savetype=="saveadd"){
			    			$("#account-payorder-supplierid").supplierWidget("clear");
			    			$("#account-form-payorderAdd").form("reset");
			    			$("#account-payorder-supplierid-span").html("");
			    			$("#account-payorder-supplierid").focus();
			    		}else{
			    			$('#account-panel-payorder-addpage').dialog("close");
			    		}
			    	}else{
			    		$.messager.alert("提醒","保存失败");
			    	}
			    }  
			}); 
    	});
    	
    	$("#account-payorder-addbutton").click(function(){
			$("#account-payorder-addtype").val("save");
			$("#account-form-payorderAdd").attr("action", "account/payable/addPayorder.do");
			$("#account-form-payorderAdd").submit();
		});
		$("#account-payorder-addgobutton").click(function(){
			$("#account-payorder-addtype").val("saveadd");
			$("#account-form-payorderAdd").attr("action", "account/payable/addPayorder.do");
			$("#account-form-payorderAdd").submit();
		});
    	
    	//$("#account-buttons-payorderPage").buttonWidget("initButtonType", 'add');
    	//$("#account-buttons-payorderPage").buttonWidget("disableButton", 'writeoff-button');
    	
    </script>
  </body>
</html>
