<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>费用录入新增页面</title>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'center',border:true">
    		<div align="center">
    			<form action="" method="post" id="expensesEntering-form-add" style="padding: 10px;">
	    			<input type="hidden" id="finance-hidden-suppliername" name="expensesEntering.suppliername"/>
	    			<input type="hidden" id="finance-hidden-subjectname" name="expensesEntering.subjectname"/>
	    			<input type="hidden" id="finance-hidden-supplierdeptid" name="expensesEntering.supplierdeptid" />
	    			<table cellpadding="2" cellspacing="2" border="0">
	    				<tr>
	    					<td width="60px" align="left">供应商:</td>
	    					<td colspan="3" align="left"><input id="finance-widget-supplierid" type="text" style="width: 318px;" name="expensesEntering.supplierid"  /></td>
	    				</tr>
	    				<tr>
	    					<td width="60px" align="left">所属部门:</td>
	    					<td align="left"><input id="finance-widget-supplierdeptid" disabled="disabled" type="text" style="width: 120px;"/></td>
	    					<td width="60px" align="left">业务日期:</td>
	    					<td align="left"><input id="finance-date-businesstime" type="text" name="expensesEntering.businessdate" class="easyui-validatebox" required="true" value="${busdate }" style="width:120px" onclick="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd',minDate:'1958-01-01',maxDate:'%y-%M-%ld'})"/></td>
	    				</tr>
	    				<tr>
	    					<td width="60px" align="left">科目名称:</td>
	    					<td align="left"><input id="finance-widget-subjectid" name="expensesEntering.subjectid" style="width: 120px" required="true"/></td>
	    					<td width="60px" align="left">科目费用:</td>
	    					<td align="left"><input id="finance-number-subjectexpenses" type="text" style="width: 120px;" name="expensesEntering.subjectexpenses" class="easyui-numberbox" required="true" data-options="precision:2,groupSeparator:','"/></td>
	    				</tr>
	    				<tr>
	    					<td width="60px" align="left">备注:</td>
	    					<td colspan="3" align="left">
	    						<textarea name="expensesEntering.remark" class="easyui-validatebox" validType="maxLen[200]" style="height:50px;width: 313px;"></textarea>
	    					</td>
	    				</tr>
	    			</table>
	    		</form>
    		</div>
    	</div>
    	<div data-options="region:'south'" style="height: 30px;border: 0px;">
    		<div align="right">
    			<a href="javaScript:void(0);" id="expensesEntering-save-saveMenu" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'" title="添加费用录入">确定</a>
    			<a href="javaScript:void(0);" id="expensesEntering-saveAgain-saveMenu" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'" title="继续添加费用录入">继续添加</a>
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
    
    	//添加费用录入
    	$("#expensesEntering-save-saveMenu").click(function(){
    		if(!$("#expensesEntering-form-add").form('validate')){
       			return false;
       		}
       		var ret = expensesEntering_AjaxConn($("#expensesEntering-form-add").serializeJSON(),'journalsheet/expensesEntering/addExpensesEntering.do','提交中..');
        	var retJson = $.parseJSON(ret);
        	if(retJson.flag){
        		$.messager.alert("提醒","新增成功!");
        		$("#finance-table-expensesEntering").datagrid('reload');
        		$('#expensesEntering-dialog-operate').dialog('close');
        	}
        	else{
        		$.messager.alert("提醒","新增失败!");
        	}
    	});
    	//继续添加费用录入
    	$("#expensesEntering-saveAgain-saveMenu").click(function(){
    		if(!$("#expensesEntering-form-add").form('validate')){
       			return false;
       		}
       		var ret = expensesEntering_AjaxConn($("#expensesEntering-form-add").serializeJSON(),'journalsheet/expensesEntering/addExpensesEntering.do','提交中..');
        	var retJson = $.parseJSON(ret);
        	if(retJson.flag){
        		$.messager.alert("提醒","新增成功!");
        		$("#finance-table-expensesEntering").datagrid('reload');
        		$("#finance-number-subjectexpenses").numberbox("clear");
        		$("#finance-widget-supplierid").supplierWidget('clear');
        		$("#finance-hidden-suppliername").val("");
        		$("#finance-hidden-supplierdeptid").val("");
        		//$("#expensesEntering-form-add")[0].reset();
        	}
        	else{
        		$.messager.alert("提醒","新增失败!");
        	}
    	});
    	
    	$(function(){
    		//供应商
		  	$("#finance-widget-supplierid").supplierWidget({
				required:true,
		  		onSelect:function(data){
		  			$("#finance-hidden-suppliername").val(data.name);
		  			if(data.buydeptid == undefined){
		  				$("#finance-widget-supplierdeptid").widget("clear");
		  				$("#finance-hidden-supplierdeptid").val("");
		  			}
		  			else{
		  				$("#finance-hidden-supplierdeptid").val(data.buydeptid);
		  				$("#finance-widget-supplierdeptid").widget("setValue",data.buydeptid);
		  			}
		  		}
			});
			
			//科目编码
			$("#finance-widget-subjectid").widget({
		  		width:120,
				name:'t_finance_expensesentering',
				col:'subjectid',
				singleSelect:true,
				required:true,
		  		onSelect:function(data){
		  			$("#finance-hidden-subjectname").val(data.codename);
		  		}
			});
			$("#finance-number-subjectexpenses").numberbox({
				required:true,
				precision:2,
				groupSeparator:','
			});
			//所属部门
		  	$("#finance-widget-supplierdeptid").widget({
		  		width:120,
				name:'t_finance_expensesentering',
				col:'supplierdeptid',
				singleSelect:true,
				required:true
			});
    	});
    </script>
  </body>
</html>
