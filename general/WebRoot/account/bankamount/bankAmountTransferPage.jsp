<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>银行账户转账页面</title>
  </head>
  <body>
	<div class="easyui-layout" data-options="fit:true,border:false">
    	<div data-options="region:'center'">
   			<div id="bankamount-form-transferdiv">
				<form action="account/bankamount/addBankAmountTransfer.do" id="bankamountlog-form-ListQuery" method="post">
					<table cellpadding="0" cellspacing="1" border="0">
						<tr>
							<td>转出银行：</td>
							<td>
								<input id="bankamount-form-outbankid" type="text" name="outbankid"/>
							</td>
						</tr>
						<tr>
							<td>账户金额：</td>
							<td>
								<input id="bankamount-form-outbankamount" type="text" style="width: 200px;" readonly="readonly"/>
							</td>
						</tr>
						<tr>
							<td>转入银行：</td>
							<td>
								<input id="bankamount-form-inbankid" type="text" name="inbankid"/>
							</td>
						</tr>
						<tr>
							<td>账户金额：</td>
							<td>
								<input id="bankamount-form-inbankamount" type="text" style="width: 200px;" readonly="readonly"/>
							</td>
						</tr>
						<tr>
							<td>金额：</td>
							<td>
								<input id="bankamount-form-amount" type="text" name="amount" style="width: 200px;" autocomplete="off"/>
							</td>
						</tr>
					</table>
				</form>
			</div>
    	</div>
    	<div data-options="region:'south',border:false">
  			<div class="buttonBG" style="height:26px;text-align:right;">
	  			<input type="button" value="确定" name="savegoon" id="bankamount-button-transferdiv" />
  			</div>
  		</div>
    </div>
    <script type="text/javascript">
		$(function(){
			$("#bankamountlog-form-ListQuery").form({  
			    onSubmit: function(){  
			    	var flag = $(this).form('validate');
			    	if(flag==false){
			    		return false;
			    	}
			    	loading("提交中..");
			    },  
			    success:function(data){
			    	loaded();
			    	var json = $.parseJSON(data);
			    	if(json.flag){
			    		$.messager.alert("提醒","转账成功");
			    		$("#bankamount-table-detail").datagrid("reload");
			    		$('#bankamount-dialog-transfer').dialog("close");
			    	}else{
			    		$.messager.alert("提醒","转账失败");
			    	}
			    }  
			}); 
			$("#bankamount-form-outbankid").widget({
     			referwid:'RL_T_BASE_FINANCE_BANK',
    			width:200,
				singleSelect:true,
				onlyLeafCheck:false,
				required:true,
				onSelect:function(data){
					var inbankid = $("#bankamount-form-inbankid").widget("getValue");
					if(data.id==inbankid){
						$.messager.alert("提醒","转出银行与转入银行一致，请重新选择");
						$("#bankamount-form-inbankid").widget("clear");
						$("#bankamount-form-outbankid").widget("clear");
						$("#bankamount-form-outbankid").focus();
						$("#bankamount-form-outbankamount").numberbox("setValue",0);
						$("#bankamount-form-inbankamount").numberbox("setValue",0);
					}else{
						$.ajax({   
				            url :'account/bankamount/getBankAmountInfo.do?id='+data.id,
				            type:'post',
				            dataType:'json',
				            success:function(json){
				            	$("#bankamount-form-outbankamount").numberbox("setValue",json.amount);
				            },
				            error:function(){
				            }
				        });
						$("#bankamount-form-inbankid").focus();
					}
				}
     		});
			$("#bankamount-form-inbankid").widget({
     			referwid:'RL_T_BASE_FINANCE_BANK',
    			width:200,
				singleSelect:true,
				onlyLeafCheck:false,
				required:true,
				onSelect:function(data){
					var outbankid = $("#bankamount-form-outbankid").widget("getValue");
					if(data.id==outbankid){
						$.messager.alert("提醒","转出银行与转入银行一致，请重新选择");
						$("#bankamount-form-inbankid").widget("clear");
						$("#bankamount-form-outbankid").widget("clear");
						$("#bankamount-form-outbankid").focus();
						$("#bankamount-form-outbankamount").numberbox("setValue",0);
						$("#bankamount-form-inbankamount").numberbox("setValue",0);
					}else{
						$.ajax({   
				            url :'account/bankamount/getBankAmountInfo.do?id='+data.id,
				            type:'post',
				            dataType:'json',
				            success:function(json){
				            	$("#bankamount-form-inbankamount").numberbox("setValue",json.amount);
				            },
				            error:function(){
				            }
				        });
						$("#bankamount-form-amount").focus();
					}
				}
     		});
			$("#bankamount-form-amount").numberbox({
				precision:2
			});
			$("#bankamount-form-outbankamount").numberbox({
				precision:2
			});
			$("#bankamount-form-inbankamount").numberbox({
				precision:2
			});
			$("#bankamount-button-transferdiv").click(function(){
				$("#bankamountlog-form-ListQuery").submit();
			});
		});
    </script>
  </body>
</html>
