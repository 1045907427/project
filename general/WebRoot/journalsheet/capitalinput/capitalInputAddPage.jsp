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
    			<form action="" method="post" id="capitalInput-form-add" style="padding: 10px;">
	    			<input type="hidden" id="journalsheet-hidden-suppliername" name="capitalInput.suppliername"/>
	    			<input type="hidden" id="journalsheet-hidden-subjectname" name="capitalInput.subjectname"/>
	    			<input type="hidden" id="journalsheet-hidden-supplierdeptid" name="capitalInput.supplierdeptid" />
	    			<table cellpadding="2" cellspacing="2" border="0">
	    				<tr>
	    					<td width="60px" align="left">供应商:</td>
	    					<td colspan="3" align="left"><input id="journalsheet-widget-supplierid" type="text" style="width: 340px;" name="capitalInput.supplierid"  /></td>
	    				</tr>
	    				<tr>
	    					<td width="60px" align="left">所属部门</td>
	    					<td align="left"><input id="journalsheet-widget-supplierdeptid" disabled="disabled" type="text" style="width: 120px;"/></td>
	    					<td width="60px" align="left">业务日期:</td>
	    					<td align="left"><input id="journalsheet-date-businesstime" type="text" name="capitalInput.businessdate" class="easyui-validatebox" required="true" value="${busdate }" style="width:120px" onclick="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd',minDate:'1958-01-01',maxDate:'%y-%M-%ld'})"/></td>
	    				</tr>
	    				<tr>
	    					<td width="60px" align="left">科目名称:</td>
	    					<td align="left"><input id="journalsheet-widget-subjectid" name="capitalInput.subjectid" style="width: 120px" required="true"/></td>
	    					<td width="60px" align="left">科目费用:</td>
	    					<td align="left"><input id="journalsheet-number-subjectexpenses" type="text" style="width: 120px;" name="capitalInput.subjectexpenses" /></td>
	    				</tr>
	    				<tr>
	    					<td width="60px" align="left">备注:</td>
	    					<td colspan="3" align="left">
	    						<textarea name="capitalInput.remark" class="easyui-validatebox" validType="maxLen[200]" style="height:50px;width: 340px;"></textarea>
	    					</td>
	    				</tr>
	    			</table>
	    		</form>
    		</div>
    	</div>
    	<div data-options="region:'south'" style="height: 30px;border: 0px;">
    		<div align="right">
    			<a href="javaScript:void(0);" id="capitalInput-save-saveMenu" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'" title="添加资金录入">确定</a>
    			<a href="javaScript:void(0);" id="capitalInput-saveAgain-saveMenu" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'" title="继续添加资金录入">继续添加</a>
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
    	$("#capitalInput-save-saveMenu").click(function(){
    		if(!$("#capitalInput-form-add").form('validate')){
       			return false;
       		}
       		var ret = capitalInput_AjaxConn($("#capitalInput-form-add").serializeJSON(),'journalsheet/capitalinput/addCapitalInput.do','提交中..');
        	var retJson = $.parseJSON(ret);
        	if(retJson.flag){
        		$.messager.alert("提醒","新增成功!");
        		$("#journalsheet-table-capitalInput").datagrid('reload');
        		$('#capitalInput-dialog-operate').dialog('close');
        	}
        	else{
        		$.messager.alert("提醒","添加失败!");
        	}
    	});
    	//继续添加资金录入
    	$("#capitalInput-saveAgain-saveMenu").click(function(){
    		if(!$("#capitalInput-form-add").form('validate')){
       			return false;
       		}
       		var ret = capitalInput_AjaxConn($("#capitalInput-form-add").serializeJSON(),'journalsheet/capitalinput/addCapitalInput.do','提交中..');
        	var retJson = $.parseJSON(ret);
        	if(retJson.flag){
        		$.messager.alert("提醒","新增成功!");
        		$("#journalsheet-table-capitalInput").datagrid('reload');
        		$(':input','#capitalInput-form-add').not(':button,:submit,:reset').val('');
        		$("#journalsheet-date-businesstime").val("${date}");
        	}
        	else{
        		$.messager.alert("提醒","新增失败!");
        	}
    	});
    	
    	$(function(){
    		//供应商
		  	$("#journalsheet-widget-supplierid").widget({
		  		width:300,
				name:'t_js_capitalinput',
				col:'supplierid',
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
		  		}
			});
			
			//资金科目编码
			$("#journalsheet-widget-subjectid").widget({
		  		width:120,
				name:'t_js_capitalinput',
				col:'subjectid',
				singleSelect:true,
				required:true,
		  		onSelect:function(data){
		  			if("5" == data.code){
		  				var subjectexpenses = $("#journalsheet-number-subjectexpenses").numberbox('getValue');
		  				if("" != subjectexpenses){
		  					if(subjectexpenses > 0){
		  						$("#journalsheet-number-subjectexpenses").numberbox('setValue',-subjectexpenses);
		  					}
		  				}
		  			}
		  			$("#journalsheet-hidden-subjectname").val(data.codename);
		  		}
			});
			
			//资金科目费用
			$("#journalsheet-number-subjectexpenses").numberbox({
				precision:2,
				required:true,
				groupSeparator:',',
				onChange:function(newValue,oldValue){
					var subjectid = $("#journalsheet-widget-subjectid").widget('getValue');
					if(subjectid == "5"){
						if(newValue > 0){
							$("#journalsheet-number-subjectexpenses").numberbox('setValue',-newValue);
						}
					}
				}
			});
			
			//所属部门
		  	$("#journalsheet-widget-supplierdeptid").widget({
		  		width:120,
				name:'t_js_capitalinput',
				col:'supplierdeptid',
				singleSelect:true,
				required:true
			});
    	});
    </script>
  </body>
</html>
