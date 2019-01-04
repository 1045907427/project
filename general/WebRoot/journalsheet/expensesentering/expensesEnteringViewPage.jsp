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
	    					<td colspan="3" align="left"><input id="finance-widget-supplierid" disabled="disabled" type="text" style="width: 313px;" name="expensesEntering.supplierid" value="${expensesEntering.supplierid }"/></td>
	    				</tr>
	    				<tr>
	    					<td width="60px" align="left">所属部门</td>
	    					<td align="left"><input id="finance-widget-supplierdeptid" value="${expensesEntering.supplierdeptid }" disabled="disabled" type="text" style="width: 120px;"/></td>
	    					<td width="60px">业务日期:</td>
	    					<td align="left"><input id="finance-date-businesstime" type="text" name="expensesEntering.businessdate" disabled="disabled" value="${expensesEntering.businessdate }" class="easyui-validatebox" style="width:120px" onclick="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd',minDate:'1958-01-01',maxDate:'%y-%M-%ld'})"/></td>
	    				</tr>
	    				<tr>
	    					<td width="60px" align="left">科目名称:</td>
	    					<td align="left"><input id="finance-widget-subjectid" disabled="disabled"  name="expensesEntering.subjectid" value="${expensesEntering.subjectid }" style="width: 120px" required="true"/></td>
	    					<td width="60px" align="left">科目费用:</td>
	    					<td align="left"><input id="finance-number-subjectexpenses" readonly="readonly" type="text" style="width: 120px;" name="expensesEntering.subjectexpenses" value="${expensesEntering.subjectexpenses }" class="easyui-numberbox" data-options="precision:2,groupSeparator:','"/></td>
	    				</tr>
	    				<tr>
	    					<td width="60px" align="left">备注:</td>
	    					<td colspan="3" align="left">
	    						<textarea name="expensesEntering.remark" readonly="readonly" class="easyui-validatebox" validType="maxLen[200]" style="height:50px;width: 315px;">${expensesEntering.remark }</textarea>
	    					</td>
	    				</tr>
	    			</table>
	    		</form>
    		</div>
    	</div>
    </div>
    <script type="text/javascript">
    	$(function(){
    		//供应商
		  	$("#finance-widget-supplierid").widget({
		  		width:318,
				name:'t_finance_expensesentering',
				col:'supplierid',
				singleSelect:true,
				onlyLeafCheck:false,
				required:true,
				initValue:'${expensesEntering.supplierid}'
			});
			
			//科目编码
			$("#finance-widget-subjectid").widget({
		  		width:120,
				name:'t_finance_expensesentering',
				col:'subjectid',
				singleSelect:true,
				onlyLeafCheck:false,
				required:true,
				initValue:'${expensesEntering.subjectid}'
			});
			
			//所属部门
		  	$("#finance-widget-supplierdeptid").widget({
		  		width:120,
				name:'t_finance_expensesentering',
				col:'supplierdeptid',
				singleSelect:true,
				onlyLeafCheck:false,
				required:true,
				initValue:$("#finance-hidden-supplierdeptid").val()
			});
    		
    	});
    </script>
  </body>
</html>
