<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>品牌业务员考核新增页面</title>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'center',border:true">
    		<div align="center">
    			<form action="" method="post" id="branduserAssess-form-add" style="padding: 10px;">
	    			<input type="hidden" name="branduserAssess.id" value="${branduserAssess.id }" />
	    			<table cellpadding="2" cellspacing="2" border="0">
	    				<tr>
	    					<td width="80px" align="left">日期:</td>
	    					<td align="left"><input type="text" name="branduserAssess.businessdate" value="${branduserAssess.businessdate }" style="width:120px" onclick="WdatePicker({dateFmt:'yyyy-MM',minDate:'1958-01',maxDate:'%y-%M'})"/></td>
	    					<td width="80px" align="left">品牌业务员:</td>
	    					<td align="left"><input id="report-widget-branduser" type="text" style="width: 120px;" name="branduserAssess.branduser" value="${branduserAssess.branduser }"/></td>
	    				</tr>
	    				<tr>
	    					<td align="left">回笼目标金额:</td>
	    					<td align="left"><input type="text" name="branduserAssess.wdtargetamount" value="${branduserAssess.wdtargetamount }" style="width: 120px" class="easyui-numberbox" data-options="precision:2,groupSeparator:','"/></td>
	    					<td align="left">回笼奖金基数:</td>
	    					<td align="left">
	    						<select name="branduserAssess.wdbonusbase" style="width: 126px;">
		    						<c:forEach items="${wdbonusbaseList }" var="list">
					    				<c:choose>
					    					<c:when test="${list.code == branduserAssess.wdbonusbase}">
					    						<option value="${list.code }" selected="selected">${list.codename }</option>
					    					</c:when>
					    					<c:otherwise>
					    						<option value="${list.code }">${list.codename }</option>
					    					</c:otherwise>
					    				</c:choose>
					    			</c:forEach>
				    			</select>
	    					</td>
	    				</tr>
	    				<tr>
	    					<td align="left">kpi目标:</td>
	    					<td align="left"><input type="text" name="branduserAssess.kpitargetamount" value="${branduserAssess.kpitargetamount }" style="width: 120px" class="easyui-numberbox" data-options="precision:2,groupSeparator:','"/></td>
	    					<td align="left">实绩完成:</td>
	    					<td align="left"><input type="text" name="branduserAssess.realaccomplish" value="${branduserAssess.realaccomplish }" style="width: 120px" class="easyui-numberbox" data-options="precision:2,groupSeparator:','"/></td>
	    				</tr>
	    				<tr>
	    					<td align="left">kpi奖金金额:</td>
	    					<td align="left"><input type="text" name="branduserAssess.kpibonusamount" value="${branduserAssess.kpibonusamount }" style="width: 120px" class="easyui-numberbox" data-options="precision:2,groupSeparator:','"/></td>
	    					<td align="left">备注:</td>
	    					<td align="left">
	    						<textarea name="branduserAssess.remark" class="easyui-validatebox" validType="maxLen[200]" style="width: 120px;" >${branduserAssess.remark }</textarea>
	    					</td>
	    				</tr>
	    			</table>
	    		</form>
    		</div>
    	</div>
    	<div data-options="region:'south'" style="height: 30px;border: 0px;">
    		<div align="right">
    			<a href="javaScript:void(0);" id="branduserAssess-save-saveMenu" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'" title="修改品牌业务员考核">确定</a>
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
    
    	//添加品牌业务员考核
    	$("#branduserAssess-save-saveMenu").click(function(){
    		if(!$("#branduserAssess-form-add").form('validate')){
       			return false;
       		}
       		$.messager.confirm("提醒","是否修改品牌业务员考核?",function(r){
				if(r){
					loading("提交中..");
		       		var ret = branduserAssess_AjaxConn($("#branduserAssess-form-add").serializeJSON(),'report/finance/editBranduserAssess.do');
		        	var retJson = $.parseJSON(ret);
		        	if(retJson.flag){
		        		$.messager.alert("提醒","修改成功!");
		        		var initqueryJSON = $("#branduserAssess-form-ListQuery").serializeJSON();
		        		$("#report-table-branduserAssess").datagrid('load',initqueryJSON);
		        		$('#branduserAssess-dialog-operate').dialog('close');
		        	}
		        	else{
		        		$.messager.alert("提醒","修改失败!");
		        	}
       			}
        	});
    	});
    	$(function(){
			//品牌业务员
		  	$("#report-widget-branduser").widget({
		  		width:120,
				name:'t_report_branduser_assess',
				col:'branduser',
				singleSelect:true,
				readonly:true
			});
    	});
    </script>
  </body>
</html>
