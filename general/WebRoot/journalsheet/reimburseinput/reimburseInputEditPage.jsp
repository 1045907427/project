<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>代垫录入修改页面</title>
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
	    					<td colspan="3" align="left"><input id="journalsheet-widget-supplierid" type="text" style="width: 340px;" name="reimburseInput.supplierid"  /></td>
	    				</tr>
	    				<tr>
	    					<td width="80px" align="left">品牌名称:</td>
	    					<td align="left"><input id="journalsheet-widget-brandid" type="text" style="width: 120px;" name="reimburseInput.brandid"/></td>
	    					<td width="80px" align="left">所属部门:</td>
	    					<td align="left"><input id="journalsheet-widget-supplierdeptid" disabled="disabled" type="text" style="width: 120px;"/></td>
	    				</tr>
	    				<tr>
	    					<td width="80px" align="left">业务日期:</td>
	    					<td align="left"><input id="journalsheet-date-businesstime" type="text" name="reimburseInput.businessdate" class="easyui-validatebox" required="true" value="${reimburseInput.businessdate }" style="width:120px" onclick="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd',minDate:'1958-01-01',maxDate:'%y-%M-%ld'})"/></td>
	    					<td width="80px" align="left">科目名称:</td>
	    					<td align="left"><input id="journalsheet-widget-subjectid" type="text" name="reimburseInput.subjectid" style="width: 120px" required="true"/></td>
	    				</tr>
	    				<tr>
	    					<td width="80px" align="left">计划收回方式:</td>
	    					<td align="left"><input id="journalsheet-widget-planreimbursetype" type="text" style="width: 120px;" name="reimburseInput.planreimbursetype"/></td>
	    					<td width="80px" align="left">计划金额:</td>
	    					<td align="left"><input id="journalsheet-number-planamount" type="text" style="width: 120px;" name="reimburseInput.planamount" value="${reimburseInput.planamount }" class="easyui-numberbox" data-options="precision:2,groupSeparator:','"/></td>
	    				</tr>
	    				<tr>
	    					<td width="80px" align="left">计划收回时间:</td>
	    					<td align="left"><input id="journalsheet-date-planreimbursetime" type="text" name="reimburseInput.planreimbursetime" value="${reimburseInput.planreimbursetime }" style="width:120px" onclick="WdatePicker({onpicked:pickedFuc,firstDayOfWeek:1,dateFmt:'yyyy-MM-dd',minDate:'1958-01-01',maxDate:'%y-%M-%ld'})"/></td>
	    					<td width="80px" align="left">收回方式:</td>
	    					<td align="left"><input id="journalsheet-widget-reimbursetype" type="text" style="width: 120px;" name="reimburseInput.reimbursetype"/></td>
	    				</tr>
	    				<tr>
	    					<td width="80px" align="left">收回金额:</td>
	    					<td align="left"><input id="journalsheet-number-takebackamount" type="text" style="width: 120px;" name="reimburseInput.takebackamount" value="${reimburseInput.takebackamount }" class="easyui-numberbox" data-options="precision:2,groupSeparator:','"/></td>
	    					<td width="80px" align="left">代垫金额:</td>
	    					<td align="left"><input id="journalsheet-number-actingmatamount" type="text" style="width: 120px;" name="reimburseInput.actingmatamount" value="${reimburseInput.actingmatamount }" class="easyui-numberbox" data-options="precision:2,groupSeparator:','"/></td>
	    				</tr>
	    				<tr>
	    					<td width="80px" align="left">备注:</td>
	    					<td colspan="3" align="left">
	    						<textarea name="reimburseInput.remark" class="easyui-validatebox" validType="maxLen[200]" style="height:50px;width: 340px;">${reimburseInput.remark }</textarea>
	    					</td>
	    				</tr>
	    			</table>
	    		</form>
    		</div>
    	</div>
    	<div data-options="region:'south'" style="height: 30px;border: 0px;">
    		<div align="right">
    			<a href="javaScript:void(0);" id="reimburseInput-save-saveMenu" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'" title="添加代垫录入">确定</a>
    		</div>
    	</div>
    </div>
    <script type="text/javascript">
    	function pickedFuc(){
			var planamount = $("#journalsheet-number-planamount").numberbox('getValue');//计划收回方式
			if("" == planamount){
				$.messager.alert("提醒","计划金额为空,无需填写该数据!");
				$("#journalsheet-date-planreimbursetime").val("");
			}
		}
    	//检验输入值的最大长度
		$.extend($.fn.validatebox.defaults.rules, {
			maxLen:{
	  			validator : function(value,param) { 
		            return value.length <= param[0]; 
		        }, 
		        message : '最多可输入{0}个字符!' 
	  		}
		});
    	
    	//修改代垫录入
    	$("#reimburseInput-save-saveMenu").click(function(){
    		if(!$("#reimburseInput-form-add").form('validate')){
       			return false;
       		}
       		var planreimbursetype = $("#journalsheet-widget-planreimbursetype").widget('getValue');//计划收回方式
       		var reimbursetype = $("#journalsheet-widget-reimbursetype").widget('getValue');//收回方式
       		var planamount = $("#journalsheet-number-planamount").numberbox('getValue');//计划金额
    		var takebackamount = $("#journalsheet-number-takebackamount").numberbox('getValue');//收回金额
    		var actingmatamount = $("#journalsheet-number-actingmatamount").numberbox('getValue');//代垫金额
       		if("" != planreimbursetype){
    			if(0 == planamount){
    				$.messager.alert("提醒","请输入计划金额!");
    				return false;
    			}
    		}
    		if("" != reimbursetype){
    			if(0 == takebackamount){
    				$.messager.alert("提醒","请输入收回金额!");
    				return false;
    			}
    		}
    		if(0 != planamount){
    			if("" == planreimbursetype){
    				$.messager.alert("提醒","请选择计划收回方式!");
    				return false;
    			}
    		}
    		if(0 != takebackamount){
    			if("" == reimbursetype){
    				$.messager.alert("提醒","请选择收回方式!");
    				return false;
    			}
    		}
    		if(0 == planamount && 0 == takebackamount && 0 == actingmatamount){
    			$.messager.alert("提醒","请在计划金额、收回金额、代垫金额中至少输入一个!");
    			return false;
    		}
       		var ret = reimburseInput_AjaxConn($("#reimburseInput-form-add").serializeJSON(),'journalsheet/reimburseInput/editReimburseInput.do','提交中..');
        	var retJson = $.parseJSON(ret);
        	if(retJson.flag){
        		$.messager.alert("提醒","修改成功!");
        		$("#journalsheet-table-reimburseInput").datagrid('reload');
        		$('#reimburseInput-dialog-operate').dialog('close');
        	}
        	else{
        		$.messager.alert("提醒","修改失败!");
        	}
    	});
    	
    	$(function(){
    		//供应商
		  	$("#journalsheet-widget-supplierid").widget({
		  		width:300,
				name:'t_js_reimburseinput',
				col:'supplierid',
				singleSelect:true,
				onlyLeafCheck:false,
				required:true,
				initValue:'${reimburseInput.supplierid}',
		  		onSelect:function(data){
		  			if(data.buydeptid == undefined){
		  				$("#journalsheet-widget-supplierdeptid").widget("clear");
		  				$("#journalsheet-hidden-supplierdeptid").val("");
		  			}
		  			else{
		  				$("#journalsheet-hidden-supplierdeptid").val(data.buydeptid);
		  				$("#journalsheet-widget-supplierdeptid").widget("setValue",data.buydeptid);
		  			}
		  			
		  			//品牌名称
					$("#journalsheet-widget-brandid").widget({
				  		param:[{field:'supplierid',op:'equal',value:$("#journalsheet-widget-supplierid").widget('getValue')}],
				  		width:120,
						name:'t_js_reimburseinput',
						col:'brandid',
						singleSelect:true,
						required:true
					});
		  		}
			});
			
			//科目编码
			$("#journalsheet-widget-subjectid").widget({
		  		width:120,
				name:'t_js_reimburseinput',
				col:'subjectid',
				singleSelect:true,
				initValue:'${reimburseInput.subjectid}',
				required:true
			});
			
			//品牌名称
			$("#journalsheet-widget-brandid").widget({
		  		param:[{field:'supplierid',op:'equal',value:$("#journalsheet-widget-supplierid").widget('getValue')}],
		  		width:120,
				name:'t_js_reimburseinput',
				col:'brandid',
				initValue:'${reimburseInput.brandid}',
				singleSelect:true,
				required:true
			});
			//所属部门
		  	$("#journalsheet-widget-supplierdeptid").widget({
		  		width:120,
				name:'t_js_reimburseinput',
				col:'supplierdeptid',
				initValue:'${reimburseInput.supplierdeptid}',
				singleSelect:true,
				required:true
			});
			
			//收回方式
			$("#journalsheet-widget-reimbursetype").widget({
		  		width:120,
				name:'t_js_reimburseinput',
				col:'reimbursetype',
				initValue:'${reimburseInput.reimbursetype}',
				singleSelect:true,
				required:true,
				initSelectNull:true
			});
			
			//计划收回方式
			$("#journalsheet-widget-planreimbursetype").widget({
		  		width:120,
				name:'t_js_reimburseinput',
				col:'planreimbursetype',
				singleSelect:true,
				required:true,
				initValue:'${reimburseInput.planreimbursetype}',
				initSelectNull:true
			});
    	});
    </script>
  </body>
</html>
