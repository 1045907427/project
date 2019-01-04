<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>流程历史版本页面</title>
  </head>
  <body>
  	<div id="activiti-datagrid-definitionHistoryListPage"></div>
  	<script type="text/javascript">
  		$(function(){
  			$("#activiti-datagrid-definitionHistoryListPage").datagrid({
  				columns:[[
  						{field:'id', title:"流程编号", width:120},
  						{field:'key', title:'流程标识', width:100},
  						{field:'name', title:'名称', width:180},
  						{field:'version', title:'版本', width:60},
  						{field:'deploymentId', title:'布署编号', width:80},
  						{field:'description', title:'流程描述', width:120},
  						{field:'current', title:'', width:60,
  							formatter: function(value,row,index){
  								if(value){
  									return "当前版本";
  								}
  								else{
  									return "<a href='javascript:;' onclick='enableDefinition(\""+row.id+"\")'><font color='blue'>启用</font></a>";
  								}
  							}
  						}
  					]],
  				url:"act/getDefinitionHistoryList.do?definitionkey=${definitionkey}",
  				fitColumns:true,
  				singleSelect:true
  			});	
  		});
  		function enableDefinition(id){
  			loading("版本启用中...");
  			$.ajax({
  				url:'act/enableDefinition.do',
  				type:'post',
  				dataType:'json',
  				data:'prodefid='+ id,
  				success:function(json){
  					loaded();
  					if(json.flag == true){
  						$("#activiti-datagrid-definitionHistoryListPage").datagrid('reload');
                        $.messager.alert('提醒', '版本启用成功。<br/><font color="red">请查看流程设置是否正确。</font>');
                        $("#activiti-dialog-definitionPage2").dialog("close");
  					}
  				}
  			});
  		}
  	</script>
  </body>
</html>
