<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>公共代码列表</title>
    <%@include file="/include.jsp" %> 
  </head>
  
  <body>
  	<table id="system-table-showUpdateDBList"></table>
   	<div id="system-query-showUpdateDBList" style="padding:0px;height:auto">
        <%--<div class="buttonBG">--%>
        <%--</div>--%>
		<form action="" id="system-form-sysUpdateDBList" method="post" style="padding-top: 2px;">
			<table class="querytable">
				<tr>
					<td>文件名称:</td>
					<td><input type="text" name="name" style="width:120px" /></td>
					<td>添加时间:</td>
					<td class="tdinput" >
						<input type="text" id="system-sysUpdateDBList-addtimestart" name="addtimestart" style="width:100px;" class="Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /> 到 <input type="text" id="system-sysUpdateDBList-addtimeend" name="addtimeend" class="Wdate" style="width:100px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" />
					</td>
		   			<td>
		   				<a href="javaScript:void(0);" id="system-query-querySysUpdateDBList" class="button-qr">查询</a>
    					<a href="javaScript:void(0);" id="system-query-reloadSysUpdateDBList" class="button-qr">重置</a>
		   			</td>
				</tr>
			</table>
  		</form>
   	</div>
   	<div id="system-dialog-codeOper" ></div>
   	<script type="text/javascript">
   		$(function(){
   			$("#system-table-showUpdateDBList").datagrid({
	   			fit:true,
	   			method:'post',
	   			title:'',
	   			rownumbers:true,
	  			pagination:true,
	  			idField:'id',
                sortName:'addtime',
                sortOrder:'desc',
	  			singleSelect:true,
	  			toolbar:'#system-query-showUpdateDBList',
	  			url:'system/updatedb/getSysUpdateDBPageListData.do',
	  			columns:[[
	  				{field:'id',title:'编号',width:80},
	  				{field:'name',title:'文件名称',width:300},
	  				{field:'addtime',title:'添加时间',width:130,sortable:true}
	  			]]
	   		});

   			//查询
  			$("#system-query-querySysUpdateDBList").click(function(){
  				//把form表单的name序列化成JSON对象
	       		var queryJSON = $("#system-form-sysUpdateDBList").serializeJSON();
	       		$("#system-table-showUpdateDBList").datagrid("load",queryJSON);
  			});
  			//重置
  			$('#system-query-reloadSysUpdateDBList').click(function(){
  				$("#system-form-sysUpdateDBList")[0].reset();
	       		$("#system-table-showUpdateDBList").datagrid("load",{});
  			});
   		});
   	</script>
  </body>
</html>
