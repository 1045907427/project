<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>表数据关联管理</title>
    <%@include file="/include.jsp" %> 
  </head>
  
  <body>
  	 <script type="text/javascript">
		$(function(){
			$('#sysDataDictionary-table-showTableRelationList').datagrid({ 
	  	 		fit:true,
	  	 		method:'post',
	  	 		title:'',
	  	 		rownumbers:true,
	  	 		pagination:true,
	  	 		idField:'id',
	  	 		singleSelect:true,
	  	 		sortName:'maintablename',
	  	 		sortOrder:'asc',
				toolbar:'#sysDataDictionary-query-showTableRelationList',
			    url:'sysDataDictionary/showTableRelationPageList.do',  
			    columns:[[  
			        {field:'maintablename',title:'基础表名',width:200,sortable:true},  
			        {field:'maincolumnname',title:'基础表字段名',width:100,sortable:true},  
			        //{field:'maintitlecolname',title:'基础表显示字段名',width:100,sortable:true},
			        {field:'tablename',title:'引用表表名',width:200,sortable:true},  
			        {field:'columnname',title:'引用表字段名',width:100,sortable:true}, 
			        {field:'tabledescription',title:'表功能描述'},  
			        {field:'createmethod',title:'创建方式',width:50,
				        formatter:function(val){
		  						if(val=='1'){
		  							return '预制';
		  						}
		  						else
		  						{
		  							return '自建';
		  						}
		  			}},
			        {field:'deleteverify',title:'删除校验',width:50,
				        formatter:function(val){
		  						if(val=='1'){
		  							return '是';
		  						}
		  						else
		  						{
		  							return '否';
		  						}
		  			}},
			        {field:'cascadechange',title:'级联替换',width:50,
				        formatter:function(val){
		  						if(val=='1'){
		  							return '是';
		  						}
		  						else
		  						{
		  							return '否';
		  						}
		  			}},
			        {field:'adddate',title:'建立日期',width:120,sortable:true},
			        {field:'modifydate',title:'修改日期',width:120,sortable:true}
			    ]]
			}); 
			
			//回车事件
			controlQueryAndResetByKey("sysDataDictionary-queay-queryTableRelationList","sysDataDictionary-queay-reloadTableRelationList");
			
			//查询
			$("#sysDataDictionary-queay-queryTableRelationList").click(function(){
	        	//把form表单的name序列化成JSON对象
	       		var queryJSON = $("#sysDataDictionary-form-tableRelationList").serializeJSON();
	       		//调用datagrid本身的方法把JSON对象赋给queryParams 即可进行查询
	       		$("#sysDataDictionary-table-showTableRelationList").datagrid("load",queryJSON);
	       		
			});
			//重置
			$("#sysDataDictionary-queay-reloadTableRelationList").click(function(){
				$("#sysDataDictionary-form-tableRelationList")[0].reset();
    			//调用datagrid本身的方法把JSON对象赋给queryParams 即可进行查询
	       		$("#sysDataDictionary-table-showTableRelationList").datagrid("load",{});
			});
			//显示级联关系页面
			$("#sysDataDictionary-add-addTableRelation").click(function(){
		    	var $tableRelationOper=$('#sysDataDictionary-dialog-tableRelationOper');
				$tableRelationOper.dialog({  
				    title: '级联关系新增',  
				    width: 400,  
				    height: 400,  
				    closed: true,  
				    cache: false,  
				    href: 'sysDataDictionary/showTableRelationAddPage.do',  
				    modal: true,
				    buttons:[
				    	{  
				    		id:'sysDataDictionary-save-addTableRelation',
	                    	text:'保存',  
		                    iconCls:'button-save',
		                    plain:true
		                }
				    ]
				});
				$tableRelationOper.dialog("open");
			});
			//显示修改页面
			$("#sysDataDictionary-edit-editTableRelation").click(function(){
				var tableRelation = $("#sysDataDictionary-table-showTableRelationList").datagrid('getSelected');
		    	if(tableRelation==null){
		    		$.messager.alert("提醒","请选择！");
		    		return false;
		    	}
		    	var url = "sysDataDictionary/showTableRelationEditPage.do?id="+tableRelation.id;
		    	var $tableRelationOper=$('#sysDataDictionary-dialog-tableRelationOper');
		    	$tableRelationOper.dialog({  
				    title: '级联关系修改',  
				    width: 400,  
				    height: 400,  
				    closed: true,  
				    cache: false,  
				    href: url,  
				    modal: true,
				    buttons:[
				    	{  
				    		id:'sysDataDictionary-save-editTableRelation',
	                    	text:'保存',  
		                    iconCls:'button-save',
		                    plain:true
		                }
				    ]
				});
				$tableRelationOper.dialog("open");
			});
			//删除
			$("#sysDataDictionary-remove-removeTableRelation").click(function(){
				var $tableRelationList=$("#sysDataDictionary-table-showTableRelationList");
				var tableRelation = $tableRelationList.datagrid('getSelected');
		    	if(tableRelation==null){
		    		$.messager.alert("提醒","请选择表关联信息！");
		    		return false;
		    	}
			$.messager.confirm("提醒", "是否删除表关联信息?", function(r) {
				if (r) {
			    	$.ajax({   
			            url :'sysDataDictionary/deleteTableRelation.do?id='+tableRelation.id,
			            type:'post',
			            dataType:'json',
			            success:function(json){
			            	if(json.flag==true){
			            		$.messager.alert("提醒","删除成功！");
			            		$tableRelationList.datagrid('reload');
			            	}else{
			            		$.messager.alert("提醒","删除失败！");
			            	}
			            }
			        });
		        }});
			});
		});
  	 </script>
     <table id="sysDataDictionary-table-showTableRelationList"></table>
     <div id="sysDataDictionary-query-showTableRelationList" style="padding-top:0px;padding-bottom:5px;height:auto">
         <div class="buttonBG">
             <a href="javaScript:void(0);" id="sysDataDictionary-add-addTableRelation" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'">新增</a>
             <a href="javaScript:void(0);" id="sysDataDictionary-edit-editTableRelation" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-edit'">修改</a>
             <a href="javaScript:void(0);" id="sysDataDictionary-remove-removeTableRelation" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-delete'">删除</a>
         </div>
		<div>
			<form action="" id="sysDataDictionary-form-tableRelationList" method="post">
				主表名:<input name="maintablename" style="width:120px">
				主表字段名:<input name="maincolumnname" style="width:120px">
				从属表名:<input name="tablename" style="width:120px">
				从属表字段名:<input name="columnname" style="width:120px">
				<a href="javaScript:void(0);" id="sysDataDictionary-queay-queryTableRelationList" class="button-qr">查询</a>
				<a href="javaScript:void(0);" id="sysDataDictionary-queay-reloadTableRelationList" class="button-qr">重置</a>
			</form>
		</div>

	</div>
	<div id="sysDataDictionary-dialog-tableRelationOper"></div>
  </body>
</html>
