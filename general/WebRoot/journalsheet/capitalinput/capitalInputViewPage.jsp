<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>资金录入详情页面</title>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'center',border:true">
    		<div align="center">
    			<form action="" method="post" id="capitalInput-form-add" style="padding: 10px;">
	    			<input type="hidden" id="journalsheet-hidden-suppliername" name="capitalInput.suppliername"/>
	    			<input type="hidden" id="journalsheet-hidden-subjectname" name="capitalInput.subjectname"/>
	    			<input type="hidden" id="journalsheet-hidden-supplierdeptid" name="capitalInput.supplierdeptid" />
	    			<table cellpadding="2" cellspacing="2" border="0">
	    				<tr>
	    					<td width="60px" align="left">供应商:</td>
	    					<td colspan="3" align="left"><input id="journalsheet-widget-supplierid" disabled="disabled" type="text" style="width: 340px;" name="capitalInput.supplierid" value="${capitalInput.supplierid }"/></td>
	    				</tr>
	    				<tr>
	    					<td width="60px" align="left">所属部门</td>
	    					<td align="left"><input id="journalsheet-widget-supplierdeptid" value="${capitalInput.supplierdeptid }" disabled="disabled" type="text" style="width: 120px;"/></td>
	    					<td width="60px" align="left">业务日期:</td>
	    					<td align="left"><input id="journalsheet-date-businesstime" type="text" name="capitalInput.businessdate" disabled="disabled" value="${capitalInput.businessdate }" class="easyui-validatebox" style="width:120px" onclick="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd',minDate:'1958-01-01',maxDate:'%y-%M-%ld'})"/></td>
	    				</tr>
	    				<tr>
	    					<td width="60px" align="left">科目名称:</td>
	    					<td align="left"><input id="journalsheet-widget-subjectid" disabled="disabled"  name="capitalInput.subjectid" value="${capitalInput.subjectid }" style="width: 120px" required="true"/></td>
	    					<td width="60px" align="left">科目费用:</td>
	    					<td align="left"><input id="journalsheet-number-subjectexpenses" readonly="readonly" type="text" style="width: 120px;" name="capitalInput.subjectexpenses" value="${capitalInput.subjectexpenses }" class="easyui-numberbox" data-options="precision:2,groupSeparator:','"/></td>
	    				</tr>
	    				<tr>
	    					<td width="60px" align="left">备注:</td>
	    					<td colspan="3" align="left">
	    						<textarea name="capitalInput.remark" readonly="readonly" class="easyui-validatebox" validType="maxLen[200]" style="height:50px;width: 340px;">${capitalInput.remark }</textarea>
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
		  	$("#journalsheet-widget-supplierid").widget({
		  		width:300,
				name:'t_js_capitalinput',
				col:'supplierid',
				singleSelect:true,
				onlyLeafCheck:false,
				initValue:'${capitalInput.supplierid}'
			});
			
			//科目编码
			$("#journalsheet-widget-subjectid").widget({
		  		width:120,
				name:'t_js_capitalinput',
				col:'subjectid',
				singleSelect:true,
				onlyLeafCheck:false,
				initValue:'${capitalInput.subjectid}'
			});
			
			//所属部门
		  	$("#journalsheet-widget-supplierdeptid").widget({
		  		width:120,
				name:'t_js_capitalinput',
				col:'supplierdeptid',
				singleSelect:true,
				onlyLeafCheck:false,
				initValue:$("#journalsheet-hidden-supplierdeptid").val()
			});
    		
    	});
    </script>
  </body>
</html>
