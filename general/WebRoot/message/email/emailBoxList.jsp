<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>自定义邮箱管理</title>
  </head>
  
  <body>  	 
     <table id="messageEmail-table-emailBoxList"></table>
     <div id="messageEmail-query-showEmailBoxList" style="padding:5px;height:auto">
		<div>
			<form action="" id="messageEmail-form-emailBoxList" method="post">
				名称:<input name="title" style="width:120px">
				<a href="javaScript:void(0);" id="messageEmail-btn-queryEmailBoxList" class="button-qr">查询</a>
				<a href="javaScript:void(0);" id="messageEmail-btn-reloadEmailBoxList" class="button-qr">重置</a>
			</form>
		</div>
		<div>
			<a href="javaScript:void(0);" id="messageEmail-btn-addEmailBox" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'">添加</a>
			<a href="javaScript:void(0);" id="messageEmail-btn-editEmailBox" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-edit'">编辑</a>
			<a href="javaScript:void(0);" id="messageEmail-btn-deleteEmailBox" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-delete'">删除</a>
		</div>
	</div>
	<div id="messageEmail-dialog-emailBoxOper" class="easyui-dialog" closed="true"></div>
	
	<script type="text/javascript">
  	 	$('#messageEmail-table-emailBoxList').datagrid({ 
  	 		fit:true,
            striped: true,
  	 		method:'post',
  	 		title:'自定义邮箱管理',
  	 		rownumbers:true,
  	 		pagination:true,
  	 		idField:'id',
  	 		singleSelect:true,
			toolbar:'#messageEmail-query-showEmailBoxList',
		    url:'message/email/showEmailBoxPageList.do',  
		    columns:[[  
		        {field:'boxorderno',title:'序号',width:120,sortable:true},  
		        {field:'title',title:'名称',width:120},  
		        {field:'addtime',title:'添加时间',width:120}
		    ]],
		    sortName:'addtime',
		    sortOrder:'desc'
		});  
		$(document).ready(function(){
			//查询
			$("#messageEmail-btn-queryEmailBoxList").click(function(){
				//查询参数直接添加在url中         
				var $emailBoxList=$("#messageEmail-form-emailBoxList");
	       		var queryString = $emailBoxList.formSerialize();
	       		var url = "message/email/showEmailBoxPageList.do?"+queryString;
				//重新赋值url 属性
	        	$("#messageEmail-table-emailBoxList").datagrid({url:url});
			});
			//重置
			$("#messageEmail-btn-reloadEmailBoxList").click(function(){
				$("#messageEmail-form-emailBoxList")[0].reset();
    			//重新赋值url 属性
				var $emailBoxList=$("#messageEmail-table-emailBoxList");
	        	$emailBoxList.datagrid('loadData', { total: 0, rows: [] });
        		$emailBoxList.datagrid({'url':"message/email/showEmailBoxPageList.do"});
			});
			//显示添加页面
			$("#messageEmail-btn-addEmailBox").click(function(){
		    	var $emailBoxOper=$('#messageEmail-dialog-emailBoxOper');
				$emailBoxOper.dialog({  
				    title: '添加',  
				    width: 500,  
				    height: 250,  
				    closed: true,  
				    cache: false,  
				    href: 'message/email/showEmailBoxAddPage.do',  
				    modal: true
				});
				$emailBoxOper.dialog("open");
			});
			//显示修改页面
			$("#messageEmail-btn-editEmailBox").click(function(){
				var dataRow = $("#messageEmail-table-emailBoxList").datagrid('getSelected');
		    	if(dataRow==null){
		    		$.messager.alert("提醒","请选择！");
		    		return false;
		    	}
		    	var url = "message/email/showEmailBoxEditPage.do?id="+dataRow.id;
		    	var $emailBoxOper=$('#messageEmail-dialog-emailBoxOper');
		    	$emailBoxOper.dialog({  
				    title: '编辑',   
				    width: 500,  
				    height: 250, 
				    closed: true,  
				    cache: false,  
				    href: url,  
				    modal: true
				});
				$emailBoxOper.dialog("open");
			});
			//删除
			$("#messageEmail-btn-deleteEmailBox").click(function(){
				var $emailBoxList=$("#messageEmail-table-emailBoxList");
				var dataRow = $emailBoxList.datagrid('getSelected');
		    	if(dataRow==null){
		    		$.messager.alert("提醒","请选择邮箱信息！");
		    		return false;
		    	}
				$.messager.confirm("提醒", "是否删除自定义邮箱信息?", function(r) {
				if (r) {
			    	$.ajax({   
			            url :'message/email/deleteEmailBox.do?id='+dataRow.id,
			            type:'post',
			            dataType:'json',
			            success:function(json){
			            	if(json.flag==true){
			            		$.messager.alert("提醒","删除成功！");
			            		$emailBoxList.datagrid('reload');			            		
		    					try{
		    						emailPage_showMyEmailBoxList();
		    					}catch(e){
		    					}
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

