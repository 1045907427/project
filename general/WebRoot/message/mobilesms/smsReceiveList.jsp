<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    
    <title>已发短信管理</title>    
	<%@include file="/include.jsp" %>
  	<script type="text/javascript" src="js/linq.min.js"></script> 	
	<link rel="stylesheet" href="css/icon-extend.css" type="text/css"></link>
  </head>
  
  <body>
    <table id="mobileSms-table-smsReceiveList"></table>
  	<div id="mobileSms-query-smsReceiveList" style="padding:5px;height:auto">
		<div>
			<form action="" id="mobileSms-form-smsReceiveList" method="post">
                <security:authorize url="/message/mobileSms/smsSendPage.do">
                    <a href="javaScript:void(0);" id="mobileSms-smsReceiveList-btn-and-sendSms" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-mobilemess'">发送手机短信</a>
                </security:authorize>
                <span id="mobileSms-queay-querySmsReceiveList-advanced"></span>
				短信内容:<input name="content" style="width:120px">
				开始时间:<input name="starttime" style="width:100px"  readonly="readonly" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
				结束时间:<input name="endtime" style="width:100px"  readonly="readonly" onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
				<a href="javaScript:void(0);" id="mobileSms-queay-querySmsReceiveList" class="button-qr">查询</a>
				<a href="javaScript:void(0);" id="mobileSms-queay-reloadSmsReceiveList" class="button-qr">重置</a>
			</form>
		</div>
		<div>

		</div>
	</div>
	<div id="mobileSms-dialog-showSmsReceiveUserList" class="easyui-dialog" closed="true"></div>
	<div id="mobileSms_window_sendSmsOper" class="easyui-window" closed="true"></div>	
	<script type="text/javascript">
 		$(document).ready(function(){ 			
 			$("#mobileSms-queay-querySmsReceiveList-advanced").advancedQuery({
		 		name:'msg_mobilesms',
		 		datagrid:'mobileSms-table-smsReceiveList'
			});
 			$("#mobileSms-table-smsReceiveList").datagrid({	
 				fit:true,
            	striped: true,
	  	 		method:'post',
	  	 		title:'已发送',
	  	 		rownumbers:true,
	  	 		pagination:true,
	  	 		singleSelect:true,
				toolbar:'#mobileSms-query-smsReceiveList',
			    url:'message/mobileSms/showReceiveMobileSmsPageList.do',  
			    columns:[[  
					{field : 'idck',checkbox : true},
			        {field:'addusername',title:'发送人',width:120},  
			        {field:'content',title:'内容',width:400},  
			        {field:'sendtime',title:'发送时间',width:150}
			    ]]
 			});
 			
 			$("#mobileSms-queay-querySmsReceiveList").click(function(){
 				//查询参数直接添加在url中         
	       		var queryJSON = $("#mobileSms-form-smsReceiveList").serializeJSON();
	       		
 				$('#mobileSms-table-smsReceiveList').datagrid('load',queryJSON);
 			});
 			$("#mobileSms-queay-reloadSmsReceiveList").click(function(){
				$("#mobileSms-form-smsReceiveList")[0].reset();
 				$('#mobileSms-table-smsReceiveList').datagrid('load', {});
 			});
 			
 			
			$("#mobileSms-smsReceiveList-btn-and-sendSms").click(function(){
				var url="message/mobileSms/smsSendPage.do?smssendpageid=mobileSms_window_sendSmsOper";
				$smsReceiveOper=$("#mobileSms_window_sendSmsOper");
				$smsReceiveOper.window({
					title:'手机短信发送',
				    width: 700,  
				    height: 450,
		            top:($(window).height() - 450) * 0.5, 
		            left:($(window).width() - 700) * 0.5, 
				    closed: true,  
				    cache: false, 
				    href:url,
				    modal: true
				});
				$smsReceiveOper.window("open");
			});	
 		});
 	</script>
  </body>
</html>
