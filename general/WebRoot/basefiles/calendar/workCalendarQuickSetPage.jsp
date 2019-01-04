<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>工作日历快速设置页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true">  
	    <div data-options="region:'center'">
	    	<div class="easyui-layout" data-options="fit:true">
	    		<div data-options="region:'north'" style="height: 150px;">
	    			<div title="设置" class="easyui-panel" data-options="fit:true" style="padding: 20px 10px 20px 10px;">
	    				<input type="radio" name="type" value="1" checked="checked"/>设置休息日<br/>
	    				<input type="radio" name="type" value="0"/>取消休息日
	    			</div>
	    		</div>
	    		<div title="日期" data-options="region:'center'" style="padding: 10px 10px 10px 10px;">
	    			起始日期:
	    			<select id="workCanlendar-quickSet-beginDate" name="beginDate" style="width: 150px;">
	    				<option value="${year }-01-01" selected="selected">${year }-01-01</option>
	    				<option value="${year }-02-01">${year }-02-01</option>
	    				<option value="${year }-03-01">${year }-03-01</option>
	    				<option value="${year }-04-01">${year }-04-01</option>
	    				<option value="${year }-05-01">${year }-05-01</option>
	    				<option value="${year }-06-01">${year }-06-01</option>
	    				<option value="${year }-07-01">${year }-07-01</option>
	    				<option value="${year }-08-01">${year }-08-01</option>
	    				<option value="${year }-09-01">${year }-09-01</option>
	    				<option value="${year }-10-01">${year }-10-01</option>
	    				<option value="${year }-11-01">${year }-11-01</option>
	    				<option value="${year }-12-01">${year }-12-01</option>
	    			</select>
	    			<br/>
	    			结束日期:
	    			<select id="workCanlendar-quickSet-endDate" name="endDate" style="width: 150px;">
	    				<option value="${year }-01-31" selected="selected">${year }-01-31</option>
	    				<c:if test="${isLeapYear==true}">
	    				<option value="${year }-02-29">${year }-02-29</option>
	    				</c:if>
	    				<c:if test="${isLeapYear==false}">
	    				<option value="${year }-02-28">${year }-02-28</option>
	    				</c:if>
	    				<option value="${year }-03-31">${year }-03-31</option>
	    				<option value="${year }-04-30">${year }-04-30</option>
	    				<option value="${year }-05-31">${year }-05-31</option>
	    				<option value="${year }-06-30">${year }-06-30</option>
	    				<option value="${year }-07-31">${year }-07-31</option>
	    				<option value="${year }-08-31">${year }-08-31</option>
	    				<option value="${year }-09-30">${year }-09-30</option>
	    				<option value="${year }-10-31">${year }-10-31</option>
	    				<option value="${year }-11-30">${year }-11-30</option>
	    				<option value="${year }-12-31" selected="selected">${year }-12-31</option>
	    			</select>
	    		</div>
	    	</div>
	    </div>
	    <div data-options="region:'east'" style="width: 150px;padding-left: 20px;">
	    	<br/><br/>
	    	<input type="checkbox" name="week" value="2">星期一<br/><br/>
	    	<input type="checkbox" name="week" value="3">星期二<br/><br/>
	    	<input type="checkbox" name="week" value="4">星期三<br/><br/>
	    	<input type="checkbox" name="week" value="5">星期四<br/><br/>
	    	<input type="checkbox" name="week" value="6">星期五<br/><br/>
	    	<input type="checkbox" name="week" value="7">星期六<br/><br/>
	    	<input type="checkbox" name="week" value="1">星期日<br/>
	    </div>
	    <div data-options="region:'south'" style="height: 40px;">
	    	<div style="text-align: right;padding: 4px 5px 4px 0;">
	    		<a href="javaScript:void(0);" id="workCanlendar-buton-quickSet" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'">确定</a>
	    	</div>
	    </div>
	 </div>
	 <script type="text/javascript">
	 	$(function(){
	 		$("#workCanlendar-buton-quickSet").click(function(){
	 			var type = $('input[name="type"]:checked').val();
	 			var weeks = [];
	 			$('input[name="week"]:checked').each(function(){    
				   	weeks.push($(this).val());    
			    });
			    var beginDate = $("#workCanlendar-quickSet-beginDate").val();
			    var endDate = $("#workCanlendar-quickSet-endDate").val();
	 			$("#workCanlendar-iframe")[0].contentWindow.workCanlendarQuickSet(type,weeks,beginDate,endDate);
	 			$('#workCanlendar-dialog-quickset').dialog('close',true);
	 		});
	 	});
	 </script>
  </body>
</html>
