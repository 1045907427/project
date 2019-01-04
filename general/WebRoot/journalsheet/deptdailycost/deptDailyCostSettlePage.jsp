<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>部门日常费用结算</title>
  </head>
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
  	<div data-options="region:'center',border:false">
  		<form action="journalsheet/deptdailycost/updateDeptDailyCostSettle.do" id="deptDailyCost-form-settle" method="post">
   		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
   			<tr>
   				<td style="width: 50px;">年度：</td>
   				<td style="width: 80px;">
   					<input id="deptDailyCost-settle-year" class="easyui-numberspinner" style="width:80px;" name="year" required="required" data-options="min:2000" value="${year }">
   				</td>
   				<td style="width: 50px;">月份：</td>
   				<td>
   					<input id="deptDailyCost-settle-month" class="easyui-numberspinner" style="width:80px;" name="month" required="required" data-options="min:1,max:12" value="${month }">
   				</td>
   			</tr>
   			<tr>
   				<td style="width: 50px;">说明：</td>
   				<td colspan="3">
   					（1）结算后，各部门的日常费用将会按照各供应商的销售金额，分摊到每个供应商去。<br/>
   					（2）该月份的日常费用将不能操作。
   				</td>
   			</tr>
   		</table>
   	</form>
   	</div>
  		<div data-options="region:'south',border:false">
  			<div class="buttonDetailBG" style="height:28px;text-align:right;">
  				<input type="button" value="确定" name="savegoon"  id="deptDailyCost-detail-addbutton" />
  			</div>
  		</div>
  	</div>
    <script type="text/javascript">
    	$(function(){
    		$("#deptDailyCost-form-settle").form({  
			    onSubmit: function(){  
			    	var flag = $(this).form('validate');
			    	if(flag==false){
			    		return false;
			    	}
			    	loading("提交中..");
			    },  
			    success:function(data){
			    	//表单提交完成后 隐藏提交等待页面
			    	loaded();
			    	var json = $.parseJSON(data);
			    	if(json.flag){
			    		$.messager.alert("提醒","结算成功");
			    	}else{
			    		$.messager.alert("提醒","结算失败");
			    	}
			    }  
			}); 
			$("#deptDailyCost-detail-addbutton").click(function(){
				var year = $("#deptDailyCost-settle-year").val();
				var month = $("#deptDailyCost-settle-month").val();
				$.messager.confirm("提醒","是否对"+year+"年"+month+"月份的日常费用进行结算？",function(r){
					if(r){
						$("#deptDailyCost-form-settle").submit();
					}
				});
			});
    	});
    </script>
  </body>
</html>
