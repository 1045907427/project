<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>交接单新增页面</title>
    <%@include file="/include.jsp"%>
  </head>
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
  		<div data-options="region:'center',border:false">
		   	<form action="" method="post" id="storage-form-deliveryDetailAddPage">
		   		<input type="hidden" name="issaleout" value="0" />
		   		<table border="0" cellspacing="5px" cellpadding="5px">
		   			<tr>
		   				<td width="60">客户:</td>
		   				<td style="text-align: left;">
		   					<input type="text" id="storage-delivery-customerid" name="customerid" width="160"/>
		   				</td>
		   				<td>单据数:</td>
		   				<td>
		   					<input type="text" id="storage-delivery-billnums" name="billnums" style="width: 160px;" value="1"/>
		   				</td>
		   			</tr>
		   			<tr>
		   				<td width="60">销售额:</td>
		   				<td>
		   					<input type="text" style="width: 160px;"
								id="delivery-input-salesamount"
								name="salesamount"
								data-options="min:0,max:9999999999,precision:2"
								class="easyui-numberbox" value="0"
								 />
		   				</td>
		   				<td width="60">箱数:</td>
		   				<td>
		   					<input type="text" style="width: 160px;" id="delivery-input-boxnum" name="boxnum" data-options="min:0,max:9999999999,precision:2" class="easyui-numberbox" value="0"/>
		   				</td>
		   			</tr>
		   			<tr>
		   				<td width="60">收款金额:</td>
		   				<td >
		   					<input type="text" style="width: 160px;" id="delivery-input-collectionamount" name="collectionamount" data-options="min:0,max:9999999999,precision:2" class="easyui-numberbox" value="0" />
		   				</td>
		   				<td width="60">回单:</td>
		   				<td >
		   					<select id="delivery-input-isreceipt" type="text" style="width: 160px" name="isreceipt">
								<option value="0">否</option>
								<option value="1" selected="selected">是</option>
							</select>
		   				</td>
		   			</tr>
		   			<tr>
		   				<td>备注:</td>
		   				<td colspan="3" style="text-align: left;">
		   					<input type="text" name="remark" style="width: 410px;" maxlength="200"/>
		   				</td>
		   			</tr>
		   		</table>
		    </form>
		    </div>
	  		<div data-options="region:'south',border:false">
	  			<div class="buttonDetailBG" style="height:30px;text-align:right;">
		  			<input type="button" value="确 定" name="savegoon" id="storage-savegoon-billDetailAddDiscountPage" />
	  			</div>
	  		</div>
	  	</div>
   <script type="text/javascript">
   		$(function(){
   			$("#storage-delivery-customerid").widget({ 
    			referwid:'RL_T_BASE_SALES_CUSTOMER',
    			singleSelect:true,
    			width:160,
    			required:true,
    			onlyLeafCheck:true
    		});
   			
			$("#storage-savegoon-billDetailAddDiscountPage").click(function(){
				var flag = $("#storage-form-deliveryDetailAddPage").form('validate');
				if(flag==false){
			  		return false;
			  	}
				var json=$("#storage-form-deliveryDetailAddPage").serializeJSON();
				json['customername']=$("#storage-delivery-customerid").val();
				json['seq'] = parseInt(1000000*Math.random());
				var rows = $dgCustomerList.datagrid('getRows');
				var i;
           		for(i=0;i<rows.length;i++){
					if(rows[i].customerid==undefined)
						break;
				}
				if(i==rows.length){
					$dgCustomerList.datagrid('appendRow',json);
				}
				else{
					$dgCustomerList.datagrid('updateRow',{index:i,row:json});
				}
				if(i >= rows.length-1)
					$dgCustomerList.datagrid('appendRow',{});
				$('#delivery-dialog-customer').dialog('close',true);
				setCustomerFooter();

                $("#delivery-input-customernums").numberbox('setValue',Number($("#delivery-input-customernums").numberbox('getValue')) + 1);

				//addSaveDetailDiscount();
			});
			$("#storage-delivery-taxamount").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
		   			$("#storage-savegoon-billDetailAddDiscountPage").focus();
				}
			});
			$("#storage-savegoon-billDetailAddDiscountPage").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
		   			addSaveDetailDiscount();
				}
			});
		});
   </script>
  </body>
</html>
