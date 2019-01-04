<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>代垫录入详情页面</title>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'center',border:true">
    		<div align="center">
    			<form action="" method="post" id="reimburseInput-form-add" style="padding: 10px;">
	    			<input type="hidden" id="journalsheet-hidden-supplierdeptid" name="reimburseInput.supplierdeptid" value="${reimburseInput.supplierdeptid}"/>
	    			<input type="hidden" name="reimburseInput.id" value="${reimburseInput.id }" />
	    			<table cellpadding="2" cellspacing="2" border="0">
	    				<tr>
	    					<td width="80px" align="left">供应商:</td>
	    					<td colspan="3" align="left"><input id="journalsheet-widget-supplierid" disabled="disabled" type="text" style="width: 340px;" name="reimburseInput.supplierid"  /></td>
	    				</tr>
	    				<tr>
	    					<td width="80px" align="left">品牌名称:</td>
	    					<td align="left"><input id="journalsheet-widget-brandid" disabled="disabled" type="text" style="width: 120px;" name="reimburseInput.brandid"/></td>
	    					<td width="80px" align="left">所属部门:</td>
	    					<td align="left"><input id="journalsheet-widget-supplierdeptid" disabled="disabled" type="text" style="width: 120px;"/></td>
	    				</tr>
	    				<tr>
	    					<td width="80px" align="left">业务日期:</td>
	    					<td align="left"><input id="journalsheet-date-businesstime" type="text" name="reimburseInput.businessdate" disabled="disabled" value="${reimburseInput.businessdate }" style="width:120px" onclick="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd',minDate:'1958-01-01',maxDate:'%y-%M-%ld'})"/></td>
	    					<td width="80px" align="left">科目名称:</td>
	    					<td align="left"><input id="journalsheet-widget-subjectid" disabled="disabled" type="text" name="reimburseInput.subjectid" style="width: 120px" required="true"/></td>
	    				</tr>
	    				<tr>
	    					<td width="80px" align="left">计划收回方式:</td>
	    					<td align="left"><input id="journalsheet-widget-planreimbursetype" type="text" disabled="disabled" style="width: 120px;" name="reimburseInput.planreimbursetype"/></td>
	    					<td width="80px" align="left">计划金额:</td>
	    					<td align="left"><input id="journalsheet-number-planamount" type="text" readonly="readonly" style="width: 120px;" name="reimburseInput.planamount" value="${reimburseInput.planamount }" class="easyui-numberbox" data-options="precision:2,groupSeparator:','"/></td>
	    				</tr>
	    				<tr>
	    					<td width="80px" align="left">计划收回时间:</td>
	    					<td align="left"><input id="journalsheet-date-planreimbursetime" type="text" name="reimburseInput.planreimbursetime" value="${reimburseInput.planreimbursetime}" disabled="disabled" style="width:120px" onclick="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd',minDate:'1958-01-01',maxDate:'%y-%M-%ld'})"/></td>
	    					<td width="80px" align="left">收回方式:</td>
	    					<td align="left"><input id="journalsheet-widget-reimbursetype" disabled="disabled" type="text" style="width: 120px;" name="reimburseInput.reimbursetype"/></td>
	    				</tr>
	    				<tr>
	    					<td width="80px" align="left">收回金额:</td>
	    					<td align="left"><input type="text" style="width: 120px;" readonly="readonly" name="reimburseInput.takebackamount" value="${reimburseInput.takebackamount }" class="easyui-numberbox" data-options="precision:2,groupSeparator:','"/></td>
	    					<td width="80px" align="left">代垫金额:</td>
	    					<td align="left"><input type="text" style="width: 120px;" readonly="readonly" name="reimburseInput.actingmatamount" value="${reimburseInput.actingmatamount }" class="easyui-numberbox" data-options="precision:2,groupSeparator:','"/></td>
	    				</tr>
	    				<tr>
	    					<td width="80px" align="left">备注:</td>
	    					<td colspan="3" align="left">
	    						<textarea name="reimburseInput.remark" readonly="readonly" style="height:50px;width: 340px;">${reimburseInput.remark }</textarea>
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
				name:'t_js_reimburseinput',
				col:'supplierid',
				singleSelect:true,
				onlyLeafCheck:false,
				initValue:'${reimburseInput.supplierid}'
			});
			
			//科目编码
			$("#journalsheet-widget-subjectid").widget({
		  		width:120,
				name:'t_js_reimburseinput',
				col:'subjectid',
				singleSelect:true,
				initValue:'${reimburseInput.subjectid}'
			});
			
			//品牌名称
			$("#journalsheet-widget-brandid").widget({
		  		width:120,
				name:'t_js_reimburseinput',
				col:'brandid',
				initValue:'${reimburseInput.brandid}',
				singleSelect:true
			});
			//所属部门
		  	$("#journalsheet-widget-supplierdeptid").widget({
		  		width:120,
				name:'t_js_reimburseinput',
				col:'supplierdeptid',
				initValue:'${reimburseInput.supplierdeptid}',
				singleSelect:true
			});
			
			//收回方式
			$("#journalsheet-widget-reimbursetype").widget({
		  		width:120,
				name:'t_js_reimburseinput',
				col:'reimbursetype',
				initValue:'${reimburseInput.reimbursetype}',
				singleSelect:true,
				initSelectNull:true
			});
			
			//计划收回方式
			$("#journalsheet-widget-planreimbursetype").widget({
		  		width:120,
				name:'t_js_reimburseinput',
				col:'planreimbursetype',
				singleSelect:true,
				initValue:'${reimburseInput.planreimbursetype}',
				initSelectNull:true
			});
    	});
    </script>
  </body>
</html>
