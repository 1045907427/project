<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>部门日常费用新增</title>
  </head>
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
  	<div data-options="region:'center',border:false">
  		<form action="" id="bankAmountOthers-form-detailAdd" method="post">
   		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
   			<tr>
   				<td>编号：</td>
   				<td><input style="width: 130px;" class="easyui-validatebox" name="bankAmountOthers.id" <c:if test="${autoCreate == true }">readonly='readonly'</c:if> <c:if test="${autoCreate == false }">required="required"</c:if> /></td>
   				<td>业务日期：</td>
   				<td><input type="text" id="bankAmountOthers-detail-businessdate" class="easyui-validatebox Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style="width: 130px;" value="${busdate }" required="required" name="bankAmountOthers.businessdate" /></td>
   				<td>状态：</td>
   				<td><select disabled="disabled" style="width: 130px;"><option>新增</option></select></td>
   			</tr>
   			<tr>
   				<td>银行名称：</td>
   				<td>
   					<input type="text" id="bankAmountOthers-detail-bank" name="bankAmountOthers.bankid"/>
   				</td>
   				<td>所属部门：</td>
   				<td>
   					<input type="text" id="bankAmountOthers-detail-deptid" name="bankAmountOthers.deptid"/>
   				</td>
   				<td>OA编号：</td>
   				<td>
   					<input type="text" name="bankAmountOthers.oaid" style="width: 130px;"/>
   				</td>
   			</tr>
   			<tr>
   				<td>单据类型：</td>
   				<td>
   					<input type="text" id="bankAmountOthers-detail-billtype" name="bankAmountOthers.billtype" style="width: 130px;"/>
   				</td>
   				<td>借贷：</td>
   				<td>
   					<select id="bankAmountOthers-detail-lendtype" name="bankAmountOthers.lendtype" style="width: 130px;">
   						<option value="1">借(收入)</option>
   						<option value="2" selected="selected">贷(支出)</option>
   					</select>
   				</td>
   				<td>单据编号：</td>
   				<td>
   					<input type="text" name="bankAmountOthers.billid" style="width: 130px;" disabled="disabled"/>
   				</td>
   			</tr>
   			<tr>
   				<td>金额：</td>
   				<td>
   					<input type="text" id="bankAmountOthers-detail-amount" style="width: 130px;" name="bankAmountOthers.amount" autocomplete="off"/>
   				</td>
   				<td>大写金额：</td>
   				<td colspan="3">
   					<input type="text" id="bankAmountOthers-detail-upamount" style="width: 385px;" name="bankAmountOthers.upamount" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td>对方名称：</td>
   				<td>
   					<input type="text" id="bankAmountOthers-detail-oppname" style="width: 130px;" name="bankAmountOthers.oppname"/>
   				</td>
   				<td>对方银行：</td>
   				<td>
   					<input type="text" id="bankAmountOthers-detail-oppbank" style="width: 130px;" name="bankAmountOthers.oppbank"/>
   				</td>
   				<td>对方银行账号：</td>
   				<td>
   					<input type="text" id="bankAmountOthers-detail-oppbankno" style="width: 130px;" name="bankAmountOthers.oppbankno"/>
   				</td>
   			</tr>
   			<tr>
   				<td>发票金额：</td>
   				<td>
   					<input type="text" id="bankAmountOthers-detail-invoiceamount" style="width: 130px;" name="bankAmountOthers.invoiceamount" autocomplete="off"/>
   				</td>
   				<td>发票号：</td>
   				<td>
   					<input type="text" id="bankAmountOthers-detail-invoiceid" style="width: 130px;" name="bankAmountOthers.invoiceid" autocomplete="off"/>
   				</td>
   				<td>发票日期：</td>
   				<td>
   					<input type="text"  class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" style="width: 130px;"  name="bankAmountOthers.invoicedate" />
   				</td>
   			</tr>
   			<tr>
   				<td>备注：</td>
   				<td colspan="5" style="text-align: left">
   					<textarea id="bankAmountOthers-detail-remark" style="width: 600px;height: 50px;" name="bankAmountOthers.remark" ></textarea>
   					<input type="hidden" id="bankAmountOthers-detail-addtype" value="save"/>
   				</td>
   			</tr>
   		</table>
   	</form>
   	</div>
  		<div data-options="region:'south',border:false">
  			<div class="buttonBG" style="height:26px;text-align:right;">
  				<input type="button" value="确定" name="savegoon" id="bankAmountOthers-detail-addbutton" />
	  			<input type="button" value="继续添加" name="savegoon" id="bankAmountOthers-detail-addgobutton" />
  			</div>
  		</div>
  	</div>
    <script type="text/javascript">
    	$(function(){
    		$("#bankAmountOthers-detail-amount").numberbox({
    			precision:2,
				required:true,
				onChange:function(newValue,oldValue){
					var upamount = AmountUnitCnChange(newValue);
					$("#bankAmountOthers-detail-upamount").val(upamount);
				}
    		});
    		$("#bankAmountOthers-detail-invoiceamount").numberbox({
    			precision:2
    		});
    		$("#bankAmountOthers-detail-billtype").widget({
    			name:'t_account_bankamount_others',
				col:'billtype',
    			width:130,
				singleSelect:true,
				required:true,
				initSelectNull:true
    		});
    		$("#bankAmountOthers-detail-bank").widget({
    			referwid:'RL_T_BASE_FINANCE_BANK',
    			width:130,
				singleSelect:true,
				required:true,
				onSelect:function(){
					$("#bankAmountOthers-detail-deptid").focus();
				}
    		});
    		$("#bankAmountOthers-detail-deptid").widget({
    			referwid:'RT_T_SYS_DEPT',
    			width:130,
				singleSelect:true,
				required:true,
				onlyLeafCheck:false,
				onSelect:function(){
					$("#bankAmountOthers-detail-amount").focus();
				}
    		});
    		$("#bankAmountOthers-detail-billtype").change(function(){
    			var value =$(this).val();
    			if(value=='3'||value=='4'||value=='5'||value=='6'||value=='7'){
    				$("#bankAmountOthers-detail-lendtype").val("2");
    			}
    		});
    		$("#bankAmountOthers-form-detailAdd").form({  
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
			    		$("#bankamountothers-table-detail").datagrid("reload");
			    		var savetype = $("#bankAmountOthers-detail-addtype").val();
			    		if(savetype=="saveadd"){
			    			$("#bankAmountOthers-detail-bank").widget("clear");
			    			$("#bankAmountOthers-detail-amount").numberbox("clear");
			    			$("#bankAmountOthers-detail-bank").focus();
			    		}else{
			    			$('#bankamountothers-dialog-detail').dialog("close");
			    		}
			    	}else{
			    		$.messager.alert("提醒","保存失败");
			    	}
			    }  
			}); 
			$("#bankAmountOthers-detail-addbutton").click(function(){
				$("#bankAmountOthers-detail-addtype").val("save");
				$("#bankAmountOthers-form-detailAdd").attr("action", "account/bankamount/addBankAmountOthers.do");
				$("#bankAmountOthers-form-detailAdd").submit();
			});
			$("#bankAmountOthers-detail-addgobutton").click(function(){
				$("#bankAmountOthers-detail-addtype").val("saveadd");
				$("#bankAmountOthers-form-detailAdd").attr("action", "account/bankamount/addBankAmountOthers.do");
				$("#bankAmountOthers-form-detailAdd").submit();
			});
			$("#bankAmountOthers-detail-amount").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					$("#bankAmountOthers-detail-addgobutton").focus();
				}
			});
    	});
    </script>
  </body>
</html>
