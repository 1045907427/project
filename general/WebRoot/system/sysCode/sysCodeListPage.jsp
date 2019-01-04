<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>公共代码列表</title>
    <%@include file="/include.jsp" %> 
  </head>
  
  <body>
  	<table id="sysCode-table-showCodeList"></table>
   	<div id="sysCode-query-showCodeList" style="padding:0px;height:auto">
        <div class="buttonBG">
            <a href="javaScript:void(0);" id="sysCode-add-addCode" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-add'">新增</a>
            <a href="javaScript:void(0);" id="sysCode-edit-editCode" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-edit'">修改</a>
            <a href="javaScript:void(0);" id="sysCode-delete-deleteCode" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-delete'">删除</a>
            <a href="javaScript:void(0);" id="sysCode-enable-enableCode" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-open'">启用</a>
            <a href="javaScript:void(0);" id="sysCode-disable-disableCode" class="easyui-linkbutton button-list" data-options="plain:true,iconCls:'button-close'">禁用</a>
        </div>
		<form action="" id="sysCode-form-sysCodeList" method="post" style="padding-top: 2px;">
			<table class="querytable">
				<tr>
					<td>代码类型名称:</td>
					<td><input type="text" name="typename" style="width:120px" /></td>
					<td>代码类型:</td>
					<td><input type="text" id="sysCode-combobox-typesList" name="type" style="width:100px" /></td>
					<td colspan="2"></td>
				</tr>
				<tr>
					<td>代码值:</td>
					<td><input type="text" name="codevalue" style="width:120px" /></td>
					<td>状态:</td>
					<td><select name="state" style="width:200px">
		   				<option></option>
		   				<option value="1">启用</option>
		   				<option value="0">禁用</option>
		   			</select></td>
		   			<td>
		   				<a href="javaScript:void(0);" id="sysCode-query-querySysCodeList" class="button-qr">查询</a>
    					<a href="javaScript:void(0);" id="sysCode-query-reloadSysCodeList" class="button-qr">重置</a>
		   			</td>
				</tr>
			</table>
  		</form>
   	</div>
   	<div id="sysCode-dialog-codeOper" ></div>
   	<script type="text/javascript">
   		$(function(){
   			$("#sysCode-table-showCodeList").datagrid({
	   			fit:true,
	   			method:'post',
	   			title:'',
	   			rownumbers:true,
	  			pagination:true,
	  			idField:'code',
	  			singleSelect:true,
	  			toolbar:'#sysCode-query-showCodeList',
	  			url:'sysCode/showSysCodeList.do',
	  			columns:[[
	  				{field:'code',title:'代码',width:130},
	  				{field:'codename',title:'代码名称',width:150},
					{field:'codevalue',title:'代码值',width:150},
	  				{field:'type',title:'代码类型',width:130,sortable:true},
	  				{field:'typename',title:'代码类型名称',width:150},
	  				{field:'seq',title:'排序',width:130,sortable:true},
	  				{field:'state',title:'状态',width:100,
	  					formatter:function(val){
	  						if(val=='1'){
	  							return '启用';
	  						}
	  						else
	  						{
	  							return '禁用';
	  						}
	  					}
	  				}
	  			]],
	  			onDblClickRow:function(){
	  				var sysCode=$("#sysCode-table-showCodeList").datagrid('getSelected');
	  				var url="sysCode/showSysCodeInfo.do?code="+sysCode.code+"&type="+sysCode.type;
	  				$("#sysCode-dialog-codeOper").dialog({
	  					title:'代码详情',
	  					width:420,
	  					height:300,
	  					closed:false,
	  					cache:false,
	  					href:url,
	  					modal:true
	  				});
	  			}
	   		});
   			//装载组合下拉框
//			$("#sysCode-combobox-typesList").combogrid({
//         		 width:200,
//         		 panelWidth:400,
//           		 idField:'type',
//           		 textField:'typename',
//           		 rownumbers:true,
//           		 filter:function(q,row){
//           		 	var id = row.type;
//           		 	var text = row.typename;
//           		 	if(id.indexOf(q)==0 || text.indexOf(q)==0){
//           		 		return true;
//           		 	}else{
//           		 		return false;
//           		 	}
//           		 },
//			     columns:[[
//			        {field:'type',title:'编码类型',width:150},
//			        {field:'typename',title:'编码类型名称',width:200}
//			     ]],
//   				url:'sysCode/showSysCodeTypes.do'
//			});

            $("#sysCode-combobox-typesList").widget({
                referwid:'RL_T_SYS_CODE',
                singleSelect:true,
                width:200
            });
   			
   			//回车事件
			controlQueryAndResetByKey("sysCode-query-querySysCodeList","sysCode-query-reloadSysCodeList");
   			
   			//查询
  			$("#sysCode-query-querySysCodeList").click(function(){
  				//把form表单的name序列化成JSON对象
	       		var queryJSON = $("#sysCode-form-sysCodeList").serializeJSON();
	       		$("#sysCode-table-showCodeList").datagrid("load",queryJSON);
  			});
  			//重置
  			$('#sysCode-query-reloadSysCodeList').click(function(){
  				$("#sysCode-form-sysCodeList")[0].reset();
	       		$("#sysCode-table-showCodeList").datagrid("load",{});
  			});
  			//显示代码添加页面
  			$("#sysCode-add-addCode").click(function(){
  				$("#sysCode-dialog-codeOper").dialog({
  					title:'代码添加',
  					width:420,
  					height:300,
  					closed:false,
  					cache:false,
  					href:'sysCode/showSysCodeAddPage.do',
  					modal:true
  				});
  			});
  			//显示模块修改页面
  			$("#sysCode-edit-editCode").click(function(){
  				var sysCode=$("#sysCode-table-showCodeList").datagrid('getSelected');
  				if(sysCode==null){
  					$.messager.alert("提醒","请选择代码!");
  					return false;
  				}
  				var url="sysCode/showSysCodeEditPage.do?code="+sysCode.code+"&type="+sysCode.type;
  				$("#sysCode-dialog-codeOper").dialog({
  					title:'代码修改',
  					width:420,
  					height:300,
  					closed:false,
  					cache:false,
  					href:url,
  					modal:true
  				});
  			});
  			//删除按钮
  			$("#sysCode-delete-deleteCode").click(function(){
  				var sysCode=$("#sysCode-table-showCodeList").datagrid('getSelected');
  				if(sysCode==null){
  					$.messager.alert("提醒","请选择代码!");
  					return false;
  				}
  				if(sysCode.state == "1"){
  					$.messager.alert("提醒","启用状态不能删除!");
  					return false;
  				}
  				$.messager.confirm("提醒","确定要删除吗?",function(r){
  					if(r){
  						$.ajax({
		  					url:'sysCode/deleteSysCode.do?code='+sysCode.code+"&type="+sysCode.type+"&state="+sysCode.state,
		  					type:'post',
		  					dataType:'json',
		  					success:function(json){
		  						if(json.flag==true){
		  							$.messager.alert("提醒","代码删除成功!");
		  							$("#sysCode-table-showCodeList").datagrid("reload");
		  						}else{
		  							$.messager.alert("提醒","代码删除失败 !");
		  						}
		  					}
		  				});
  					}
  				});
  			});
  			
  			//启用按钮
  			$("#sysCode-enable-enableCode").click(function(){
  				var sysCode=$("#sysCode-table-showCodeList").datagrid('getSelected');
  				if(sysCode==null){
  					$.messager.alert("提醒","请选择代码!");
  					return false;
  				}
  				if(sysCode.state == "1"){
  					$.messager.alert("提醒","启用状态不能启用!");
  					return false;
  				}
  				$.ajax({
  					url:'sysCode/enableSysCode.do?code='+sysCode.code+"&type="+sysCode.type,
  					type:'post',
  					dataType:'json',
  					success:function(json){
  						if(json.flag==true){
  							$.messager.alert("提醒","代码启用成功!");
  							$("#sysCode-table-showCodeList").datagrid('reload');
  						}else{
  							$.messager.alert("提醒","代码启用失败 !");
  						}
  					}
  				});
  			});
  			
  			//禁用按钮
  			$("#sysCode-disable-disableCode").click(function(){
  				var sysCode=$("#sysCode-table-showCodeList").datagrid('getSelected');
  				if(sysCode==null){
  					$.messager.alert("提醒","请选择代码!");
  					return false;
  				}
  				if(sysCode.state == "0"){
  					$.messager.alert("提醒","禁用状态不能禁用!");
  					return false;
  				}
  				$.ajax({
					url:'sysCode/disableSysCode.do?code='+sysCode.code+"&type="+sysCode.type,
					type:'post',
					dataType:'json',
					success:function(json){
						if(json.flag==true){
							$.messager.alert("提醒","代码禁用成功!");
							$("#sysCode-table-showCodeList").datagrid('reload');
						}else{
							$.messager.alert("提醒","代码禁用失败 !");
						}
					}
				});
  			});
   		});
   	</script>
  </body>
</html>
