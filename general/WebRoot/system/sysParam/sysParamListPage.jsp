<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>公共代码列表</title>
    <%@include file="/include.jsp" %>
  </head>
  
  <body>
  	<div class="easyui-layout" data-options="fit:true,border:false">
  		<div title="模块分类" data-options="region:'west',split:true,border:false" style="width:300px;">
  			<table id="sysCode-table-module-showParamList"></table>
  			<div id="sysCode-module-query-showParamList">
  				<%--<form action="" id="sysCode-module-form-showParamList" method="post">--%>
  					<%--<table cellpadding="0" cellspacing="0" border="0">--%>
  						<%--<tr>--%>
  							<%--<td>模块名称:</td>--%>
  							<%--<td><input type="text" name="code"id="sysParam-moduleid-showParamList"/></td>--%>
  							<%--<td>--%>
  								<%--<a href="javaScript:void(0);" id="sysCode-module-querySysParamList" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-search'" title="查询">查询</a>--%>
		   						<%--<a href="javaScript:void(0);" id="sysCode-module-reloadSysParamList" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'" title="重置">重置</a>--%>
  							<%--</td>--%>
  						<%--</tr>--%>
  					<%--</table>--%>
  				<%--</form>--%>
  			</div>
	    </div>
	    <div title="系统参数列表" data-options="region:'center',border:false">
		   	<div id="sysCode-query-showParamList" style="padding-top: 0px;padding-bottom: 5px">
                <div class="buttonBG">
                    <a href="javaScript:void(0);" id="sysCode-add-addParam" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'" title="新增">新增</a>
                    <a href="javaScript:void(0);" id="sysCode-edit-editParam" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-edit'" title="修改">修改</a>
                    <a href="javaScript:void(0);" id="sysCode-enable-enableParam" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-open'" title="启用">启用</a>
                    <a href="javaScript:void(0);" id="sysCode-disable-disableParam" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-close'" title="禁用">禁用</a>
                </div>

		 		<form action="" id="sysCode-form-sysParamList" method="post">
		  			参数名称:<input name="pname" style="width:100px" />
                    参数描述:<input name="description" style="width: 100px"/>
		  			状态:<select name="state" style="width: 80px" >
		   					<option></option>
		   					<option value="1">启用</option>
		   					<option value="0">禁用</option>
		   				</select>
		  			<a href="javaScript:void(0);" id="sysCode-query-querySysParamList" class="button-qr">查询</a>
		   			<a href="javaScript:void(0);" id="sysCode-query-reloadSysParamList" class="button-qr">重置</a>
		 		</form>
		   	</div>
		   	<table id="sysCode-table-showParamList"></table>
	    </div>
  	</div>
  	<div id="sysCode-dialog-paramOper" ></div>
   	<script type="text/javascript">
   		function loadDropdown(){
   			//系统参数模块
   			$("#sysParam-moduleid-widget").widget({
   				width:204,
   				referwid:'RL_T_SYSPARAM_MODULE',
    			singleSelect:true,
             	required:true
   			});
   		}
   		
   		$(function(){
   			$("#sysParam-moduleid-showParamList").widget({
   				width:100,
   				referwid:'RL_T_SYSPARAM_MODULE',
    			singleSelect:true
   			});
   			//模块列表
   			$("#sysCode-table-module-showParamList").datagrid({
   				fit:true,
	   			method:'post',
	   			title:'',
	   			rownumbers:true,
	  			pagination:false,
	  			singleSelect:true,
	  			toolbar:'#sysCode-module-query-showParamList',
	  			url:'sysParam/getSysParamModuleList.do',
	  			columns:[[
	  				{field:'code',title:'模块编码',width:60},
	  				{field:'codename',title:'模块名称',width:70}
	  			]],
	  			onClickRow:function(rowIndex, rowData){
	  				var json = {};
	  				json['moduleid'] = rowData.code;
	        		$("#sysCode-table-showParamList").datagrid('load',json);
	  			}
   			});
   			
   			//查询
   			$("#sysCode-module-querySysParamList").click(function(){
   				var queryJSON = $("#sysCode-module-form-showParamList").serializeJSON();
	       		$("#sysCode-table-module-showParamList").datagrid("load",queryJSON);
   			});
   			//重置
   			$("#sysCode-module-reloadSysParamList").click(function(){
   				$("#sysParam-moduleid-showParamList").widget('clear');
   				$("#sysCode-module-form-showParamList")[0].reset();
	       		$("#sysCode-table-module-showParamList").datagrid("reload");
	       		//系统参数列表
	       		$("#sysCode-form-sysParamList")[0].reset();
	       		$("#sysCode-table-showParamList").datagrid("loadData",{ total: 0, rows: [] });
   			});
	   		
	   		$("#sysCode-table-showParamList").datagrid({
	   			fit:true,
	   			method:'post',
	   			title:'',
	   			rownumbers:true,
	  			pagination:false,
	  			idField:'paramid',
	  			singleSelect:true,
                url:'sysParam/showSysParamList.do',
	  			toolbar:'#sysCode-query-showParamList',
	  			columns:[[
	  				{field:'pname',title:'参数名称',width:250},
	  				{field:'description',title:'参数描述',width:200},
	  				{field:'pvalue',title:'参数值',width:100},
	  				{field:'pvdescription',title:'参数值描述',width:200},
	  				{field:'puser',title:'参数用途',width:100},
	  				{field:'state',title:'状态',width:80,
	  					formatter:function(val){
	  						if(val=='1'){
	  							return '启用';
	  						} else {
	  							return '禁用';
	  						}
	  					}
	  				}
	  			]],
	  			onDblClickRow:function(){
	  				var sysParam=$("#sysCode-table-showParamList").datagrid('getSelected');
	  				var url="sysParam/showSysParamInfo.do?paramid="+sysParam.paramid;
	  				$("#sysCode-dialog-paramOper").dialog({
	  					title:'系统参数详情',
	  					width:390,
	  					height:400,
	  					closed:false,
	  					cache:false,
	  					href:url,
	  					modal:true
	  				});
	  			}
	   		});
	   		
	   		//回车事件
			controlQueryAndResetByKey("sysCode-query-querySysParamList","sysCode-query-reloadSysParamList");
	   		
   			//查询
  			$("#sysCode-query-querySysParamList").click(function(){
	       		var queryJSON = $("#sysCode-form-sysParamList").serializeJSON();
	       		var moduleobj = $("#sysCode-table-module-showParamList").datagrid('getSelected');
  				if(null != moduleobj){
  					queryJSON['moduleid'] = moduleobj.code;
  				}
	       		$("#sysCode-table-showParamList").datagrid("load",queryJSON);
  			});
  			//重置
  			$('#sysCode-query-reloadSysParamList').click(function(){
  				$("#sysCode-form-sysParamList")[0].reset();
                $("#sysCode-table-module-showParamList").datagrid('clearSelections');
                var queryJSON = $("#sysCode-form-sysParamList").serializeJSON();
                $("#sysCode-table-showParamList").datagrid("load",queryJSON);
  			});
  			//显示代码添加页面
  			$("#sysCode-add-addParam").click(function(){
  				var moduleobj=$("#sysCode-table-module-showParamList").datagrid('getSelected');
  				var moduleid = "";
  				if(null != moduleobj){
  					moduleid = moduleobj.code;
  				}
  				$("#sysCode-dialog-paramOper").dialog({
  					title:'系统参数添加',
  					width:400,
  					height:390,
  					closed:false,
  					cache:false,
  					href:'sysParam/showSysParamAddPage.do?moduleid='+moduleid,
  					modal:true
  				});
  			});
  			//显示模块修改页面
  			$("#sysCode-edit-editParam").click(function(){
  				var sysParam=$("#sysCode-table-showParamList").datagrid('getSelected');
  				if(sysParam==null){
  					$.messager.alert("提醒","请选择参数!");
  					return false;
  				}
  				var url="sysParam/showSysParamEditPage.do?paramid="+sysParam.paramid;
  				$("#sysCode-dialog-paramOper").dialog({
  					title:'系统参数修改',
  					width:400,
  					height:400,
  					closed:false,
  					cache:false,
  					href:url,
  					modal:true
  				});
  			});
  			
  			//启用按钮
  			$("#sysCode-enable-enableParam").click(function(){
  				var sysParam=$("#sysCode-table-showParamList").datagrid('getSelected');
  				if(sysParam==null){
  					$.messager.alert("提醒","请选择参数!");
  					return false;
  				}
  				$.ajax({
  					url:'sysParam/enableSysParam.do?paramid='+sysParam.paramid,
  					type:'post',
  					dataType:'json',
  					success:function(json){
  						if(json.flag==true){
  							$.messager.alert("提醒","参数启用成功!");
  							$("#sysCode-table-showParamList").datagrid('reload');
  						}else{
  							$.messager.alert("提醒","参数启用失败 !");
  						}
  					}
  				});
  			});
  			
  			//禁用按钮
  			$("#sysCode-disable-disableParam").click(function(){
  				var sysParam=$("#sysCode-table-showParamList").datagrid('getSelected');
  				if(sysParam==null){
  					$.messager.alert("提醒","请选择参数!");
  					return false;
  				}
  				$.ajax({
  					url:'sysParam/disableSysParam.do?paramid='+sysParam.paramid,
  					type:'post',
  					dataType:'json',
  					success:function(json){
  						if(json.flag==true){
  							$.messager.alert("提醒","参数禁用成功!");
  							$("#sysCode-table-showParamList").datagrid('reload');
  						}else{
  							$.messager.alert("提醒","参数禁用失败 !");
  						}
  					}
  				});
  			});
   		});
   	</script>
  </body>
</html>
