<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>Internet 邮箱管理</title>
  </head>
  
  <body>  	 
     <table id="messageEmail-table-webMailConfigList"></table>
     <div id="messageEmail-query-showWebMailConfigList" style="padding:5px;height:auto">
		<div>
			<form action="" id="messageEmail-form-webMailConfigList" method="post">
				邮件:<input name="maintablename" style="width:120px">
				<a href="javaScript:void(0);" id="messageEmail-btn-queryWebMailConfigList" class="button-qr">查询</a>
				<a href="javaScript:void(0);" id="messageEmail-btn-reloadWebMailConfigList" class="button-qr">重置</a>
			</form>
		</div>
		<div>
			<a href="javaScript:void(0);" id="messageEmail-btn-addWebMailConfig" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'">添加</a>
			<a href="javaScript:void(0);" id="messageEmail-btn-editWebMailConfig" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-edit'">配置</a>
			<a href="javaScript:void(0);" id="messageEmail-btn-deleteWebMailConfig" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-delete'">删除</a>
		</div>
	</div>
	<div id="messageEmail-dialog-webMailConfigOper" class="easyui-dialog" closed="true"></div>
	
	<script type="text/javascript">
  	 	$('#messageEmail-table-webMailConfigList').datagrid({ 
  	 		fit:true,
            striped: true,
  	 		method:'post',
  	 		title:'Internet 邮箱管理',
  	 		rownumbers:true,
  	 		pagination:true,
  	 		idField:'id',
  	 		singleSelect:true,
			toolbar:'#messageEmail-query-showWebMailConfigList',
		    url:'message/email/showWebMailConfigPageList.do',  
		    columns:[[  
		        {field:'email',title:'邮件名称',width:120,sortable:true},  
		        {field:'popserver',title:'接收服务器(POP3)',width:120},  
		        {field:'popport',title:'接收服务器端口',width:120},
		        {field:'ispopssl',title:'接收服务器安全连接',width:120,
			        formatter:function(val){
  						if(val=='1'){
  							return '是';
  						}
  						else
  						{
  							return '否';
  						}
	  			}},  
		        {field:'smtpserver',title:'发送服务器(SMTP)',width:120}, 
		        {field:'smtpport',title:'发送服务器端口'},  
		        {field:'issmtpssl',title:'发送服务器安全连接',width:120,
			        formatter:function(val){
  						if(val=='1'){
  							return '是';
  						}
  						else
  						{
  							return '否';
  						}
	  			}},
		        {field:'emailuser',title:'登录账户',width:120},
		        {field:'issmtppass',title:'SMTP身份验证',width:80,
			        formatter:function(val){
  						if(val=='1'){
  							return '是';
  						}
  						else
  						{
  							return '否';
  						}
	  			}}, 
		        {field:'autorecvflag',title:'自动收取外部邮件',width:80,
			        formatter:function(val){
  						if(val=='1'){
  							return '是';
  						}
  						else
  						{
  							return '否';
  						}
	  			}}, 
		        {field:'isdefault',title:'默认邮件',width:80,
			        formatter:function(val){
  						if(val=='1'){
  							return '是';
  						}
  						else
  						{
  							return '否';
  						}
	  			}}, 
		        {field:'isrecvdel',title:'收取后删除',width:80,
			        formatter:function(val){
  						if(val=='1'){
  							return '是';
  						}
  						else
  						{
  							return '否';
  						}
	  			}}, 
		        {field:'isrecvmsg',title:'收取新邮件短信提醒',width:80,
			        formatter:function(val){
  						if(val=='1'){
  							return '是';
  						}
  						else
  						{
  							return '否';
  						}
	  			}},
		        {field:'addtime',title:'建立日期',width:120}
		    ]],
		    sortName:'addtime',
		    sortOrder:'desc'
		});  
		$(document).ready(function(){
			//查询
			$("#messageEmail-btn-queryWebMailConfigList").click(function(){
				//查询参数直接添加在url中         
				var queryJSON=$("#messageEmail-form-webMailConfigList").serializeJSON();
				//重新赋值url 属性
	        	$("#messageEmail-table-webMailConfigList").datagrid('load',queryJSON);
			});
			//重置
			$("#messageEmail-btn-reloadWebMailConfigList").click(function(){
				$("#messageEmail-form-webMailConfigList")[0].reset();
    			//重新赋值url 属性
				$("#messageEmail-table-webMailConfigList").datagrid('load', {});
			});
			//显示添加页面
			$("#messageEmail-btn-addWebMailConfig").click(function(){
		    	var $webMailConfigOper=$('#messageEmail-dialog-webMailConfigOper');
				$webMailConfigOper.dialog({  
				    title: '添加',  
				    width: 600,  
				    height: 450,
				    closed: true,  
				    cache: false,  
				    href: 'message/email/showWebMailConfigAddPage.do',  
				    modal: true
				});
				$webMailConfigOper.dialog("open");
			});
			//显示修改页面
			$("#messageEmail-btn-editWebMailConfig").click(function(){
				var dataRow = $("#messageEmail-table-webMailConfigList").datagrid('getSelected');
		    	if(dataRow==null){
		    		$.messager.alert("提醒","请选择！");
		    		return false;
		    	}
		    	var url = "message/email/showWebMailConfigEditPage.do?id="+dataRow.id;
		    	var $webMailConfigOper=$('#messageEmail-dialog-webMailConfigOper');
		    	$webMailConfigOper.dialog({  
				    title: '编辑',   
				    width: 600,  
				    height: 450,
				    closed: true,  
				    cache: false,  
				    href: url,  
				    modal: true
				});
				$webMailConfigOper.dialog("open");
			});
			//删除
			$("#messageEmail-btn-deleteWebMailConfig").click(function(){
				var $webMailConfigList=$("#messageEmail-table-webMailConfigList");
				var dataRow = $webMailConfigList.datagrid('getSelected');
		    	if(dataRow==null){
		    		$.messager.alert("提醒","请选择配置信息！");
		    		return false;
		    	}
				$.messager.confirm("提醒", "是否删除配置信息?", function(r) {
				if (r) {
			    	$.ajax({   
			            url :'message/email/deleteWebMailConfig.do?id='+dataRow.id,
			            type:'post',
			            dataType:'json',
			            success:function(json){
			            	if(json.flag==true){
			            		$.messager.alert("提醒","删除成功！");
			            		$webMailConfigList.datagrid('reload');
			            	}else{
			            		$.messager.alert("提醒","删除失败！");
			            	}
			            }
			        });
		        }});
			});
		});
  	 </script>
  </body>
</html>
