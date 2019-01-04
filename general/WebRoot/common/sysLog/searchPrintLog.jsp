<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>查询系统日志</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
  		<table id="sysLog-table-showLogList"></table>
  		<div id="sysLog-query-showSysLogList" style="padding:5px;height:auto">
  			<form action="" id="sysLog-form-sysLogList" method="post">
	   			<table cellpadding="2" cellspacing="0" border="0">
	   				<tr>
	   					<td>开始时间:</td>
	   					<td><input id="begintime" name="begintime" class="Wdate" style="width:100px" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-M-d',minDate:'2008-01-01',maxDate:'%y-%M-%ld'})"/></td>
	   					<td>结束时间：</td>
	   					<td><input id="endtime" name="endtime" class="Wdate" style="width:100px" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-M-d',minDate:'2008-01-01',maxDate:'%y-%M-%ld'})" /></td>
	   					<td>姓名：</td>
	   					<td><input name="name" style="width:100px" /></td>
	   				</tr>
	   				<tr>
	   					<%-- 
	   					<td>关键字:</td>
	   					<td><input name="keyname" style="width:100px" /></td>
	   					 --%> 
	   					<td>日志内容：</td>
	   					<td colspan="5"><input name="content" style="width:420px" /></td>
	   				</tr>
	   				<tr>
	   					<td>日志类型:</td>
	   					<td><select name="type" style="width: 100px;">
		    				<option value="5">打印</option>
		    			</select></td>
		    			<td colspan="2">注意：打印次数会在回调时更新</td>
	   					<td colspan="3">
	   						<a href="javaScript:void(0);" id="sysLog-query-querySysLogList" class="easyui-linkbutton" iconCls="icon-search" title="[Alt+Q]查询">查询</a>
	    					<a href="javaScript:void(0);" id="sysLog-query-reloadSysLogList" class="easyui-linkbutton" iconCls="icon-reload" title="[Alt+R]重置">重置</a>
	   					</td>
	   				</tr>
	   			</table>
	  		</form>
  		</div>
  		<div id="sysLog-dialog-LogOper" class="easyui-dialog" closed="true"></div>
	  <script type="text/javascript"> 
			$(function(){
				$('#sysLog-table-showLogList').datagrid({ 
					fit:true,
					method:'post',
					title:'',
					rownumbers:true,
					pagination:true,
					idField:'id',
					singleSelect:true,
					sortName:'addtime',
					pageSize:200,
					sortOrder:'desc',
					toolbar:'#sysLog-query-showSysLogList',
					columns:[[    
						{field:'name',title:'姓名',width:100},
						{field:'content',title:'日志内容',width:450},  
						<%-- {field:'keyname',title:'关键字',width:200,hidden:true}, --%>
						{field:'type',title:'日志类型',width:80, hidden:true,
							formatter:function(val){
		  						switch(val){
		  							case '0':return '其他操作';
		  							case '1':return '查询';
		  							case '2':return '新增';
		  							case '3':return '修改';
		  							case '4':return '删除';
		  							case '5':return '打印';
		  						}
	  						}
						},    
						{field:'addtime',title:'时间',width:130}
						<%-- {field:'ip',title:'ip地址',width:150} --%>
					]],
					onDblClickRow:function(){
						/*
						var sysLog = $("#sysLog-table-showLogList").datagrid('getSelected');
						var url = "sysLog/showLogInfo.do?id="+sysLog.id;
						$('#sysLog-dialog-LogOper').dialog({  
							title: '系统用户日志详情',  
							width: 400,  
							height: 350,  
							closed: false,  
							cache: false,  
							href: url,  
							modal: true
						});
						*/
					}
				});
				
				//回车事件
				controlQueryAndResetByKey("sysLog-query-querySysLogList","sysLog-query-reloadSysLogList");
				
				//查询
				$("#sysLog-query-querySysLogList").click(function(){
					//查询参数直接添加在url中         
			       	var queryJSON = $("#sysLog-form-sysLogList").serializeJSON();
		       		//调用datagrid本身的方法把JSON对象赋给queryParams 即可进行查询
		       		$("#sysLog-table-showLogList").datagrid({
		       			url:'sysLog/showSearchSysPrintLogList.do',
		       			pageNumber:1,
		       			queryParams:queryJSON
		       		});
				});
				//重置
				$('#sysLog-query-reloadSysLogList').click(function() {  
		    		$("#sysLog-form-sysLogList")[0].reset();
		    		$("#sysLog-table-showLogList").datagrid('loadData',{total:0,rows:[]});
		    	});  
			});
	  </script>
  </body>
</html>
