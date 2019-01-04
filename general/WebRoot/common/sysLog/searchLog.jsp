<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>查询系统日志</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
  		<table id="sysLog-table-showLogList"></table>
  		<div id="sysLog-query-showSysLogList" style="padding:5px;height:auto">
  			<form action="" id="sysLog-form-sysLogList" method="post">
	   			<table class="querytable">
	   				<tr>
	   					<td>开始时间:</td>
	   					<td><input id="begintime" name="begintime" class="Wdate" style="width:130px" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm',minDate:'2008-01-01',maxDate:'%y-%M-%ld'})"/></td>
	   					<td>结束时间：</td>
	   					<td><input id="endtime" name="endtime" class="Wdate" style="width:130px" onfocus="WdatePicker({firstDayOfWeek:1,dateFmt:'yyyy-MM-dd HH:mm',minDate:'2008-01-01',maxDate:'%y-%M-%ld'})" /></td>
	   					<td>姓名：</td>
	   					<td><input name="name" style="width:130px" /></td>
	   				</tr>
	   				<tr>
	   					<td>关键字:</td>
	   					<td><input name="keyname" style="width:130px" /></td>
	   					<td>日志内容:</td>
	   					<td colspan="3"><input name="content" style="width:415px" /></td>
	   				</tr>
	   				<tr>
	   					<td>日志类型:</td>
	   					<td><select name="type" style="width: 130px;">
		    				<option></option>
		    				<option value="0">其他操作</option>
		    				<option value="1">查询</option>
		    				<option value="2">新增</option>
		    				<option value="3">修改</option>
		    				<option value="4">删除</option>
		    				<option value="5">打印</option>
		    			</select></td>
		    			<td>IP地址:</td>
		    			<td><input name="ip" style="width:130px" /></td>
	   					<td colspan="3">
	   						<a href="javaScript:void(0);" id="sysLog-query-querySysLogList" class="button-qr">查询</a>
	    					<a href="javaScript:void(0);" id="sysLog-query-reloadSysLogList" class="button-qr">重置</a>
	   					</td>
	   				</tr>
	   			</table>
	  		</form>
  		</div>
  		<div id="sysLog-dialog-LogOper"></div>
        <div id="sysLog-dialog-LogDataOper"></div>
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
						{field:'content',title:'日志内容',width:300},  
						{field:'keyname',title:'关键字',width:200},
						{field:'type',title:'日志类型',width:80,
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
						{field:'addtime',title:'时间',width:130},
						{field:'ip',title:'ip地址',width:150}
					]],

					onDblClickRow:function(){
						var sysLog = $("#sysLog-table-showLogList").datagrid('getSelected');
                        if(sysLog.type != 3){
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

                        }else{

                            var url = "sysLog/showLogInfoData.do?id="+sysLog.id;
                            $('#sysLog-dialog-LogDataOper').dialog({
                                title: '系统用户日志数据',
                                width: 1200,
                                height: 450,
                                closed: false,
                                cache: false,
                                href: url,
                                modal: true
                            });
                        }
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
		       			url:'sysLog/showSearchSysLogList.do',
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
