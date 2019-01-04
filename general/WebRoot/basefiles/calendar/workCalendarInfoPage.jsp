<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>工作日历添加页面</title>
	<%@include file="/include.jsp" %>  
  </head>
  
  <body>
  <div style="margin-left: 5px;">
   	<form action="addWorkCalendar.do" method="post" id="workCanlendar-form-add">
   	<div class="pageContent">
		<p>
			<label>编码:</label>
			<input type="text" id="workCanlendar-id" name="workCanlendar.id" value="<c:out value="${workCanlendar.id }"></c:out>" class="easyui-validatebox" required="true" style="width:200px;" readonly="readonly"/>
		</p>
		<p>
			<label>名称:</label>
			<input type="text" id="workCanlendar-name" name="workCanlendar.name" value="<c:out value="${workCanlendar.name }"></c:out>" class="easyui-validatebox" required="true" style="width:200px;" readonly="readonly"/>
		</p>
		<p>
			<label>状态:</label>
			<select name="workCanlendar.state" style="width: 200px;" disabled="disabled">
				<option value="4" <c:if test="${workCanlendar.state=='4'}">selected="selected"</c:if>>新增</option>
				<option value="3" <c:if test="${workCanlendar.state=='3'}">selected="selected"</c:if>>暂存</option>
				<option value="2" <c:if test="${workCanlendar.state=='2'}">selected="selected"</c:if>>保存</option>
				<option value="1" <c:if test="${workCanlendar.state=='1'}">selected="selected"</c:if>>启用</option>
				<option value="0" <c:if test="${workCanlendar.state=='0'}">selected="selected"</c:if>>禁用</option>
			</select>
		</p>
		<p>
			<label>备注:</label>
			<input type="text" id="workCanlendar-remark" name="workCanlendar.remark" value="<c:out value="${workCanlendar.remark }"></c:out>" style="width:200px;" readonly="readonly"/>
		</p>
		<input type="hidden" id="workCalendar-restDays" name="restDays" value="${restDays }"/>
		<input type="hidden" id="workCalendar-year" name="year" value="${year }"/>
		<input type="hidden" id="workCalendar-hidden-id" name="id" value="<c:out value="${workCanlendar.id }"></c:out>"/>
	</div>
	</form>
	<form action="addWorkCalendar.do" method="post" id="workCanlendar-form-quickSet">
		<input type="hidden" id="workCalendar-quickSet-id" name="workCanlendar.id" value="<c:out value="${workCanlendar.id }"></c:out>"/>
		<input type="hidden" id="workCalendar-quickSet-name" name="workCanlendar.name" value="<c:out value="${workCanlendar.name }"></c:out>"/>
		<input type="hidden" id="workCalendar-quickSet-remark" name="workCanlendar.remark" value="<c:out value="${workCanlendar.remark }"></c:out>"/>
		<input type="hidden" id="workCalendar-quickSet-restDays" name="restDays" value="${restDays }"/>
		<input type="hidden" id="workCalendar-quickSet-year" name="year" value="${year }"/>
		<input type="hidden" id="workCalendar-quickSet-hidden-id" name="id" value="<c:out value="${workCanlendar.id }"></c:out>"/>
	</form>
	<table>
		<tr>
			<td>年份:<input type="text" id="quickSetYear" name="year" class="easyui-numberspinner" data-options="min:2000,max:2100,required:true,
			onChange:function(value){
				var id = $('#workCanlendar-id').val();
				var name = $('#workCanlendar-name').val();
				var remark = $('#workCanlendar-remark').val();
				$('#workCalendar-quickSet-id').attr('value',id);
				$('#workCalendar-quickSet-name').attr('value',name);
				$('#workCalendar-quickSet-remark').attr('value',remark);
				$('#workCalendar-quickSet-year').attr('value',value);
				$('#workCalendar-quickSet-restDays').attr('value',restDate);
				$('#workCanlendar-form-quickSet').attr('action','basefiles/showWorkCanlendarInfo.do');
				$('#workCanlendar-form-quickSet').submit();}" value="${year }"/></td>
			<td></td>
		</tr>
	</table>
	<div>
   		<span id="workCanlendar-month1" style="width: 186px; height: 176px; "></span>
   		<span id="workCanlendar-month2" style="width: 186px; height: 176px; "></span>
   		<span id="workCanlendar-month3" style="width: 186px; height: 176px; "></span>
   		<span id="workCanlendar-month4" style="width: 186px; height: 176px; "></span>
   		<span id="workCanlendar-month5" style="width: 186px; height: 176px; "></span>
   		<span id="workCanlendar-month6" style="width: 186px; height: 176px; "></span>
   		<span id="workCanlendar-month7" style="width: 186px; height: 176px; "></span>
   		<span id="workCanlendar-month8" style="width: 186px; height: 176px; "></span>
   		<span id="workCanlendar-month9" style="width: 186px; height: 176px; "></span>
   		<span id="workCanlendar-month10" style="width: 186px; height: 176px; "></span>
   		<span id="workCanlendar-month11" style="width: 186px; height: 176px; "></span>
   		<span id="workCanlendar-month12" style="width: 186px; height: 176px; "></span>
   		<c:if test=""></c:if>
   	</div>
  </div>
	 <script type="text/javascript">
	 	$.parser.parse();
	 	var restDate = [];
	 	<c:if test="${restDays!=null && restDays!=''}">
	 	var restDateStr = "${restDays}";
	 	restDate = restDateStr.split(",");
	 	</c:if>
	 	//根据月份获取 休息日
	 	function getRestDateByMonth(month){
	 		var days = [];
	 		if(month&&month.length==1){
	 			month = "0"+month;
	 		}
	 		for(var i=0;i<restDate.length;i++){
	 			if(month==restDate[i].substr(5,2)){
	 				days.push(restDate[i]);
	 			}
	 		}
	 		return days;
	 	}
	 	//设置休息日
	 	function setRestDate(date){
	 		var flag = true;
	 		for(var i=0;i<restDate.length;i++){
	 			if(date==restDate[i]){
	 				restDate = restDate.slice(0,i).concat(restDate.slice(i+1,restDate.length));
	 				flag = false;
	 				break;
	 			}
	 		}
	 		if(flag){
	 			restDate.push(date);
	 		}
	 	}
	 	//添加休息日
	 	function addRestDate(date){
	 		var flag = true;
	 		for(var i=0;i<restDate.length;i++){
	 			if(date==restDate[i]){
	 				flag = false;
	 				break;
	 			}
	 		}
	 		if(flag){
	 			restDate.push(date);
	 		}
	 	}
	 	//删除休息日
	 	function deleteRestDate(date){
	 		for(var i=0;i<restDate.length;i++){
	 			if(date==restDate[i]){
	 				restDate = restDate.slice(0,i).concat(restDate.slice(i+1,restDate.length));
	 				flag = false;
	 				break;
	 			}
	 		}
	 	}
	 	//点击设置休息日
		function setWorkCanlendar(id,dp){
			var days = dp.specialDates;
			var date = dp.cal.getDateStr();
			var minDate = dp.minDate;
			var maxDate = dp.maxDate;
			var flag = false;
			var num = 0;
			if(days!=null){
				for(var i=0;i<days.length;i++){
					if(date==days[i]){
						flag = true;
						num = i;
						break;
					}
				}
			}else{
				days = new Array();
			}
			if(flag){
				days = days.slice(0,num).concat(days.slice(num+1,days.length));
			}else{
				days.push(date);
			}
			//设置休息日
			setRestDate(date);
			if(days.length>0){
				WdatePicker( {
					eCont : id,
					skin:'work',
					minDate : minDate,
					maxDate : maxDate,
					specialDates:days
				});
			}else{
				WdatePicker( {
					eCont : id,
					skin:'work',
					minDate : minDate,
					maxDate : maxDate
				});
			}
			$("#workCalendar-restDays").attr("value",restDate);
		}
		
		initWorkCanlendar("${year }");
		function initWorkCanlendar(year){
			for(var i=1;i<=12;i++){
				var days = getRestDateByMonth(i);
				if(days.length==0){
					days = null;
				}
				WdatePicker( {
					eCont : 'workCanlendar-month'+i,
					skin:'work',
					specialDates:days,
					minDate : year+'-'+i+'-01',
					maxDate : year+'-'+i+'-%ld'
				});
			}
		}
</script>
  </body>
</html>
