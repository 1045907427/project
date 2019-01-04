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
   	<form action="" method="post" id="workCanlendar-form-add">
   	<div class="pageContent">
		<p>
			<label>编码:</label>
			<input type="text" id="workCanlendar-id" name="workCanlendar.id" value="<c:out value="${workCanlendar.id }"></c:out>" class="easyui-validatebox" data-options="validType:'workCalendarIdCheck'" maxlength="20" required="true" style="width:200px;"/>
		</p>
		<p>
			<label>名称:</label>
			<input type="text" id="workCanlendar-name" name="workCanlendar.name" value="<c:out value="${workCanlendar.name }"></c:out>" class="easyui-validatebox" required="true" style="width:200px;" maxlength="50"/>
		</p>
		<p>
			<label>状态:</label>
			<select name="workCanlendar.state" style="width: 200px;" disabled="disabled">
				<option value="4">新增</option>
				<option value="3">暂存</option>
				<option value="2">保存</option>
				<option value="1">启用</option>
				<option value="0">禁用</option>
			</select>
		</p>
		<p>
			<label>备注:</label>
			<input type="text" id="workCanlendar-remark" name="workCanlendar.remark" value="<c:out value="${workCanlendar.remark }"></c:out>" style="width:200px;" maxlength="50"/>
		</p>
		<input type="hidden" id="workCalendar-restDays" name="restDays" value="${restDays }"/>
		<input type="hidden" id="workCalendar-year" name="year" value="${year }"/>
	</div>
	</form>
	<form action="basefiles/addWorkCalendar.do" method="post" id="workCanlendar-form-quickSet">
		<input type="hidden" id="workCalendar-quickSet-id" name="workCanlendar.id" value="<c:out value="${workCanlendar.id }"></c:out>"/>
		<input type="hidden" id="workCalendar-quickSet-name" name="workCanlendar.name" value="<c:out value="${workCanlendar.name }"></c:out>"/>
		<input type="hidden" id="workCalendar-quickSet-remark" name="workCanlendar.remark" value="<c:out value="${workCanlendar.remark }"></c:out>"/>
		<input type="hidden" id="workCalendar-quickSet-restDays" name="restDays" value="${restDays }"/>
		<input type="hidden" id="workCalendar-quickSet-year" name="year" value="${year }"/>
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
				$('#workCanlendar-form-quickSet').attr('action','basefiles/showWorkCanlendarAddPage.do');
				$('#workCanlendar-form-quickSet').submit();}" value="${year }"/></td>
			<td><input type="button" id="quickSet" value="快速设置" onClick="javaScript:quickSet();" /></td>
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
		$.extend($.fn.validatebox.defaults.rules, {
			workCalendarIdCheck:{  
		        validator: function (value, param) {  
		    		var flag = false;
		    		var strFlag = false;
		    		var pattern=/[ `~!@#$%^&()_+<>?:"{},.\/;'[\]]/im;  
			        if(pattern.test(value)){  
			            strFlag = false;     
			        }else{
			        	strFlag = true;
			        }
		    		if(param&&value==param[0]){
		    			flag = true;
		    		}else{
			        	$.ajax({   
				            url :'basefiles/checkWorkCalendarId.do?id='+value,
				            type:'post',
				            dataType:'json',
				            async: false,
				            success:function(json){
				            	flag =  json.flag;
				            }
				        });
		    		}
			        return flag&&strFlag;
		        },  
		        message:'编码重复或者有非法字符串，请重新输入!'  
		    } 
		});	 
	 
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
				setTimeout(function(){
					WdatePicker( {
						eCont : id,
						skin:'work',
						minDate : minDate,
						maxDate : maxDate,
						specialDates:days,
						onpicked : function(datedp) {
							var dateid = datedp.eCont.id;
							setWorkCanlendar(dateid,datedp);
						}
					});
				},1);
			}else{
				setTimeout(function(){
					WdatePicker( {
						eCont : id,
						skin:'work',
						minDate : minDate,
						maxDate : maxDate,
						onpicked : function(datedp) {
							var dateid = datedp.eCont.id;
							setWorkCanlendar(dateid,datedp);
						}
					});
				},1);
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
					maxDate : year+'-'+i+'-%ld',
					onpicked : function(dp) {
						var id = dp.eCont.id;
						setWorkCanlendar(id,dp);
					}
				});
			}
		}
		function workCanlendarQuickSet(type,weeks,beginDate,endDate){
			loading("设置中..");
			$.ajax({   
	            url :'basefiles/workCanlendarQuickSet.do',
	            type:'post',
	            data:'weeks='+weeks+'&beginDate='+beginDate+'&endDate='+endDate,
	            dataType:'json',
	            async:false,
	            success:function(json){
	            	loaded();
	            	//设置休息日
	            	if(type=="1"){
						for ( var key in json) {
							var date = json[key];
							for(var i=0;i<date.length;i++){
								addRestDate(date[i]);
							}
						}
					}else{
						//取消休息日
						for ( var key in json) {
							var date = json[key];
							for(var i=0;i<date.length;i++){
								deleteRestDate(date[i]);
							}
						}
					}
				}
			});
			
			$("#workCalendar-quickSet-restDays").attr("value",restDate);
			$("#workCalendar-quickSet-id").attr("value",$("#workCanlendar-id").val());
			$("#workCalendar-quickSet-name").attr("value",$("#workCanlendar-name").val());
			$("#workCalendar-quickSet-remark").attr("value",$("#workCanlendar-remark").val());
			$("#workCalendar-quickSet-year").attr("value",$("#workCanlendar-year").val());
			
			$("#workCalendar-restDays").attr("value",restDate);
			$("#workCanlendar-form-quickSet").attr("action","basefiles/showWorkCanlendarAddPage.do");
			$("#workCanlendar-form-quickSet").submit();
		}
		var msg = "";
		//工作日历保存
		function workCalendarSubmit(){
			msg = "保存";
			$("#workCanlendar-form-add").attr("action","basefiles/addWorkCalendar.do");
			$("#workCanlendar-form-add").submit();
		}
		//工作日历暂存
		function workCalendarHoldSubmit(){
			msg = "暂存";
			$("#workCanlendar-form-add").attr("action","basefiles/addWorkCalendarHold.do");
			$("#workCanlendar-form-add").submit();
		}
		function quickSet(){
			var year = $("#quickSetYear").numberspinner('getValue');
			parent.workCanlendarQuickSet(year);
			
		}
		$("#workCanlendar-form-add").form({  
		    onSubmit: function(){  
		    	var flag = $(this).form('validate');
		    	if(flag==false){
		    		return false;
		    	}
		    	parent.loading("提交中..");
		    },  
		    success:function(data){
		    	//表单提交完成后 隐藏提交等待页面
		    	parent.loaded();
		    	//转为json对象
		    	var json = $.parseJSON(data);
		    	if(json.flag){
		    		parent.$.messager.alert("提醒",msg+"成功！");
		    		parent.$('#workCanlendar-table-list').datagrid('reload');
		    		location.reload();
		    	}else{
		    		parent.$.messager.alert("提醒",msg+"失败！");
		    	}
		    }  
		}); 
</script>
  </body>
</html>
