<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>资金录入新增页面</title>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'center',border:true">
    		<div align="center">
    			<form action="" method="post" id="fundInput-form-add" style="padding: 10px;">
	    			<input type="hidden" id="journalsheet-hidden-supplierdeptid" name="fundInput.supplierdeptid" />
	    			<table cellpadding="2" cellspacing="2" border="0">
	    				<tr>
	    					<td width="60px" align="left">供应商:</td>
	    					<td colspan="3" align="left"><input id="journalsheet-widget-supplierid" type="text" name="fundInput.supplierid"  /></td>
	    				</tr>
	    				<tr>
	    					<td width="60px" align="left">所属部门</td>
	    					<td align="left"><input id="journalsheet-widget-supplierdeptid" disabled="disabled" type="text"/></td>
	    					<td width="60px" align="left">业务日期:</td>
	    					<td align="left"><input id="journalsheet-date-businesstime" type="text" name="fundInput.businessdate" class="easyui-validatebox" required="true" value="${busdate }" style="width: 150px" onclick="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd',minDate:'${mindate}',maxDate:'%y-%M-%ld'})"/></td>
	    				</tr>
	    				<tr>
	    					<td width="60px" align="left">预付金额:</td>
	    					<td align="left"><input id="journalsheet-number-advanceamount" name="fundInput.advanceamount" style="width: 150px" class="easyui-numberbox" data-options="precision:2,required:true,groupSeparator:',',"/></td>
	    					<td width="60px" align="left">库存金额:</td>
	    					<td align="left"><input id="journalsheet-number-stockamount" name="fundInput.stockamount" style="width: 150px" class="easyui-numberbox" data-options="precision:2,required:true,groupSeparator:',',"/></td>
	    				</tr>
	    				<tr>
	    					<td width="60px" align="left">应收金额:</td>
	    					<td align="left"><input id="journalsheet-number-receivableamount" name="fundInput.receivableamount" style="width: 150px" class="easyui-numberbox" data-options="precision:2,required:true,groupSeparator:',',"/></td>
	    					<td width="60px" align="left">代垫金额:</td>
	    					<td align="left"><input id="journalsheet-number-actingmatamount" name="fundInput.actingmatamount"  style="width: 150px"  class="easyui-numberbox" data-options="precision:2,required:true,groupSeparator:',',"/></td>
	    				</tr>
	    				<tr>
	    					<td width="60px" align="left">应付金额:</td>
	    					<td align="left"><input id="journalsheet-number-payableamount" name="fundInput.payableamount" style="width: 150px"/></td>
							<td width="60px" align="left">代垫未收:</td>
							<td align="left"><input id="journalsheet-number-norecactingmat" name="fundInput.norecactingmat" style="width: 150px" class="easyui-numberbox no_input" value="0" readonly="readonly" data-options="precision:2,required:true,groupSeparator:',',"/></td>
	    				</tr>
						<tr>
							<td width="60px" align="left">费用未付:</td>
							<td align="left"><input id="journalsheet-number-norecexpenses" name="fundInput.norecexpenses" style="width: 150px" class="easyui-numberbox" data-options="precision:2,required:true,groupSeparator:',',"/></td>
							<td width="60px" align="left">库存折差:</td>
							<td align="left"><input id="journalsheet-number-stockdiscount" name="fundInput.stockdiscount" style="width: 150px" class="easyui-numberbox" data-options="precision:2,required:true,groupSeparator:',',"/></td>
						</tr>
						<tr>
							<td width="60px" align="left">本期代垫:</td>
							<td align="left"><input id="journalsheet-number-currentactingmat" name="fundInput.currentactingmat" style="width: 150px" class="easyui-numberbox" data-options="precision:2,required:true,groupSeparator:',',"/></td>
							<td width="60px" align="left">汇款收回:</td>
							<td align="left"><input id="journalsheet-number-remittancerecovery" name="fundInput.remittancerecovery" style="width: 150px" class="easyui-numberbox" data-options="precision:2,required:true,groupSeparator:',',"/></td>
						</tr>
						<tr>
							<td width="60px" align="left">货补收回:</td>
							<td align="left"><input id="journalsheet-number-goodsrecovery" name="fundInput.goodsrecovery" style="width: 150px" class="easyui-numberbox" data-options="precision:2,required:true,groupSeparator:',',"/></td>
						</tr>
	    				<tr>
	    					<td width="60px" align="left">备注:</td>
	    					<td colspan="3" align="left">
	    						<textarea name="fundInput.remark" class="easyui-validatebox" validType="maxLen[200]" style="height:50px;width: 366px;"></textarea>
	    					</td>
	    				</tr>
	    			</table>
	    		</form>
    		</div>
    	</div>
    	<div data-options="region:'south'" style="height: 30px;border: 0px;">
    		<div align="right">
    			<a href="javaScript:void(0);" id="fundInput-save-saveMenu" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'" title="添加资金录入">确定</a>
    		</div>
    	</div>
    </div>
    <script type="text/javascript">
   
    	//检验输入值的最大长度
		$.extend($.fn.validatebox.defaults.rules, {
			maxLen:{
	  			validator : function(value,param) { 
		            return value.length <= param[0]; 
		        }, 
		        message : '最多可输入{0}个字符!' 
	  		}
		});

    	//添加资金录入
    	$("#fundInput-save-saveMenu").click(function(){
			var supplierid = $("#journalsheet-widget-supplierid").widget('getValue');
			var businessdate = $("#journalsheet-date-businesstime").val();
			var ret = fundInput_AjaxConn({supplierid:supplierid,businessdate:businessdate},"journalsheet/fundinput/checkFundInputByMap.do");
			var retjson = $.parseJSON(ret);
			if(retjson.flag){
				$.messager.alert("提醒","已存在该业务日期的供应商资金录入或业务日期小于最新日期，不允许新增!");
				return false;
			}
    		if(!$("#fundInput-form-add").form('validate')){
       			return false;
       		}
       		var ret = fundInput_AjaxConn($("#fundInput-form-add").serializeJSON(),'journalsheet/fundinput/addFundInput.do','提交中..');
        	var retJson = $.parseJSON(ret);
        	if(retJson.flag){
        		$.messager.alert("提醒","新增成功!");
        		$("#journalsheet-table-fundInput").datagrid('reload');
        		$('#fundInput-dialog-operate').dialog('close');
        	}
        	else{
        		$.messager.alert("提醒","新增失败!");
        	}
    	});
    	
    	$(function(){
    		//供应商
		  	$("#journalsheet-widget-supplierid").widget({
		  		width:372,
				referwid: 'RL_T_BASE_BUY_SUPPLIER_1',
				singleSelect:true,
				onlyLeafCheck:false,
				required:true,
		  		onSelect:function(data){
		  			$("#journalsheet-hidden-suppliername").val(data.name);
		  			if(data.buydeptid == undefined){
		  				$("#journalsheet-widget-supplierdeptid").widget("clear");
		  				$("#journalsheet-hidden-supplierdeptid").val("");
		  			}
		  			else{
		  				$("#journalsheet-hidden-supplierdeptid").val(data.buydeptid);
		  				$("#journalsheet-widget-supplierdeptid").widget("setValue",data.buydeptid);
		  			}

					getNumberBox("journalsheet-number-advanceamount").focus();
		  		}
			});

			//应付金额
			$("#journalsheet-number-payableamount").numberbox({
				precision:2,
				required:true,
				groupSeparator:',',
				onChange:function(newValue,oldValue){
					if(newValue > 0){
						$("#journalsheet-number-payableamount").numberbox('setValue',-newValue);
					}
				}
			});

			//所属部门
		  	$("#journalsheet-widget-supplierdeptid").widget({
		  		width:150,
				referwid: 'RL_T_BASE_DEPARTMENT_BUYER',
				singleSelect:true,
				required:true
			});
    	});
    </script>
  </body>
</html>
