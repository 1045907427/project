<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售单据核对添加页面</title>
  </head>
  
  <body>
  	<div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'center',border:true">
  			<form id="account-form-billcheckadd" action="account/receivable/addSalesBillCheck.do" method="post">
  				<input type="hidden" id="account-addtype" value="save" />
		    	<table cellpadding="1" cellspacing="1" border="0">
		    		<tr>
		    			<td>客户名称:</td>
		    			<td><input type="text" id="account-customer-billcheck" name="salesBillCheck.customerid" value="${customerid }"/></td>
		    		</tr>
		    		<tr>
		    			<td>业务日期:</td>
		    			<td><input type="text" name="salesBillCheck.businessdate" value="${businessdate }" style="width: 200px;" readonly="readonly"/></td>
		    		</tr>
		    		<tr>
		    			<td>销售金额:</td>
		    			<td><input type="text" id="account-salesamount-billcheck" name="salesBillCheck.salesamount" class="easyui-numberbox" data-options="required:true,precision:${amounlen },groupSeparator:','" style="width: 200px;"/></td>
		    		</tr>
		    		<tr>
		    			<td>单据数:</td>
		    			<td><input type="text" id="account-billnums-billcheck" name="salesBillCheck.billnums" class="easyui-numberbox" data-options="required:true,precision:0,groupSeparator:','" style="width: 200px;"/></td>
		    		</tr>
		    		<tr>
		    			<td>备注:</td>
		    			<td><textarea type="text" id="account-remark-billcheck" name="salesBillCheck.remark" style="width: 200px;" rows="3" onfocus="this.select();frm_focus('remark');" onblur="frm_blur('remark');"></textarea></td>
		    		</tr>
		    	</table>
		    </form>
  		</div>
	    <div data-options="region:'south'">
    		<div class="buttonDetailBG" style="height:30px;text-align:right;">
	  			<input type="button" value="继续添加" name="savegoon" id="account-savegoon-billcheck" />
    		</div>
    	</div>
  	</div>
  	<script type="text/javascript">
  		$(function(){
  			$("#account-customer-billcheck").widget({
  				referwid:'RL_T_BASE_SALES_CUSTOMER',
				width:200,
				readonly:true,
				onlyLeafCheck:false,
				singleSelect:true
  			});

	    	$("#account-savegoon-billcheck").click(function(){
  				if(!$("#account-form-billcheckadd").form('validate')){
     				return false;
     			}
     			$("#account-addtype").val("savegoon");
				$("#account-form-billcheckadd").submit();
	    	});
	    	
	    	$("#account-form-billcheckadd").form({  
			    onSubmit: function(){  
			    	loading("数据处理中..");
			    },  
			    success:function(data){
			    	//表单提交完成后 隐藏提交等待页面
			    	loaded();
			    	var json = $.parseJSON(data);
			    	if(json.flag){
				        var row = $("#account-datagrid-billcheck").datagrid('getSelected');
  						if(null != row){
  							row.inputsalesamount = json.inputsalesamount;
	  						row.inputbillnums = json.inputbillnums;
	  						row.remark = json.remark;
	  						row.eqflag = json.eqflag;
					        var index = $("#account-datagrid-billcheck").datagrid('getRowIndex',row);
					        $("#account-datagrid-billcheck").datagrid('updateRow',{index: index,row:row});
					        $.messager.alert("提醒","编辑成功!");
					        if($("#account-addtype").val() == "save"){
					        	$('#account-dialog-billcheck').dialog('close',true);
					        }else if($("#account-addtype").val() == "savegoon"){
					        	var businessdate = $("#businessdate1").val();
					        	var row = $("#account-datagrid-billcheck").datagrid('getSelected');
					    		var nextindex = $("#account-datagrid-billcheck").datagrid('getRowIndex',row) + Number(1);
					    		$("#account-datagrid-billcheck").datagrid('selectRow',nextindex);
					    		$("#account-datagrid-billcheck").datagrid('unselectRow',index);
					    		var nextrow = $("#account-datagrid-billcheck").datagrid('getSelected');
					        	var url = 'account/receivable/showSalesBillCheckInfoPage.do?customerid='+nextrow.customerid+'&businessdate='+businessdate;
								$("#account-dialog-billcheck").dialog({
									title: '销售单据核对编辑',  
						    		width: 300,  
								    height: 250,  
								    closed: false,  
									cache: false,
								    href: url,
								    modal: true,
								    onLoad:function(data){
										keydownNumberBox();
								    }
								});
					        }
  						}
			        }else{
			        	$.messager.alert("提醒","编辑失败!");
			        }
			    }  
			});
  		});
  	</script>
  </body>
</html>
