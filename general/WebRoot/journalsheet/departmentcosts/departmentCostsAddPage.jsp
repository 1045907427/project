<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>费用新增页面</title>
  </head>
  <body>
    <div class="easyui-layout" data-options="fit:true">
    	<div data-options="region:'center',border:true" style="height: 50px;border: 0px;">
    		<form action="" method="post" id="departmentCosts-form-add-calculate" style="padding: 5px;">
    		<table cellpadding="2" cellspacing="2" border="0">
   				<tr>
   					<td width="70px" align="left">业务日期:</td>
   					<td align="left"><input id="departmentCosts-form-add-date-businesstime" type="text" name="businessdate" class="easyui-validatebox" required="true" value="${yearmonth }" style="width:120px" onclick="WdatePicker({dateFmt:'yyyy-MM',minDate:'1958-01',maxDate:'${yearmonth }'})"/></td>
   					<td width="70px" align="left">所属部门:</td>
	    			<td align="left"><input id="departmentCosts-form-add-widget-deptid" name="deptid" type="text" style="width: 120px;"/></td>
   				</tr>
	    	</table>
	    	</form>
    	</div>    	
    	<div data-options="region:'south'" style="height: 30px;">
    		<div align="right">
    			<a href="javaScript:void(0);" id="departmentCosts-btn-calculate" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'" title="新增">新增并初始化</a>
    		</div>
    	</div>
    </div>
    <script type="text/javascript">

    	$("#departmentCosts-btn-calculate").click(function(){
			var flag=$("#departmentCosts-form-add-calculate").form('validate') ;
			if(flag==false){
				return false;
			}
       		var formData=$("#departmentCosts-form-add-calculate").serializeJSON();
       		
       		loading('正在努力初始化计算中，请耐心等待..');
       		try{
	        	$.ajax({
			        type: 'post',
			        cache: false,
			        url: 'journalsheet/costsFee/addDepartmentCosts.do',
			        data: formData,
			        dataType:'json',
			        success:function(data){
			        	loaded();
		        		if(data.flag){
		            		$.messager.alert("提醒","新增并初始化成功!");
		            		$("#departmentCosts-query-List").trigger('click');
		            		$('#departmentCosts-dialog-add-operate').dialog('close');
		            	}
		            	else{
		                	if(data.msg){
		            			$.messager.alert("提醒","新增并初始化失败!"+data.msg);
		                	}else{
		            			$.messager.alert("提醒","新增并初始化失败!");
		                	}
		            	}
			        }
			    });  
       		}catch(ex){
	        	loaded();           		
       		}      	
    	});
    	
    	$(function(){    		
			//所属部门
		  	$("#departmentCosts-form-add-widget-deptid").widget({
		  		width:120,
				name:'t_js_departmentcosts',
				col:'deptid',
				singleSelect:true,
				onlyLeafCheck:false,
				onlyParentCheck:true,
				required:true
			});
			<c:if test="${!empty(deptParentId)}">
				$("#departmentCosts-form-add-widget-deptid").widget("setValue",${deptParentId});
			</c:if>
    	});
    	
    </script>
  </body>
</html>
