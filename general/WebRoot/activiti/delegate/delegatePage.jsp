<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>工作委托-委托规则-我的委托记录</title>
    <%@include file="/include.jsp" %> 
  </head>
  
  <body>
	<div class="easyui-layout" data-options="fit:true">
        <div data-options="region:'north',border:false" style="height:268px;" title="添加工作流自动委托规则（提前委托）">
            <div id="activiti-panel-delegatePage"></div>
        </div>
		<div data-options="region:'center',border:false">
			<div id="activiti-datagrid-delegatePage"></div>
			<div id="activiti-tool-delegatePage">
				<select class="delegateType" id="activiti-select-delegatePage">
					<option value="0">委托状态</option>
					<option value="1">被委托状态</option>
				</select>
                <security:authorize url="/act/deleteDelegate.do">
                    <a class="easyui-linkbutton" id="activiti-edit-delegatePage" data-options="plain:true,iconCls:'button-edit'">修改</a>
                </security:authorize>
                <security:authorize url="/act/addDelegate.do">
                    <a class="easyui-linkbutton" id="activiti-delete-delegatePage" data-options="plain:true,iconCls:'button-delete'">删除</a>
                </security:authorize>
            </div>
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			$("#activiti-panel-delegatePage").panel({
				fit:true,
				href:'act/delegateAddPage.do'
			});
			$("#activiti-edit-delegatePage").click(function(){
				var row = $("#activiti-datagrid-delegatePage").datagrid('getSelected');
				if(row == null){
					$.messager.alert("提醒", "请选择需要修改的记录！");
					return false;
				}
				$("#activiti-panel-delegatePage").panel('refresh', 'act/delegateEditPage.do?id='+ row.id);
			});
			$("#activiti-delete-delegatePage").click(function(){
				var row = $("#activiti-datagrid-delegatePage").datagrid('getSelected');
				if(row == null){
					$.messager.alert("提醒", "请选择需要删除的记录！");
					return false;
				}
				$.messager.confirm("提醒", "确定删除该委托规则？", function(r){
					if(r){
						$.ajax({
                            type: 'post',
							url: 'act/deleteDelegate.do',
                            data: {id: row.id},
							dataType: 'json',
							success: function(json){
								if(json.flag == true){
									$.messager.alert("提醒", "删除成功！");
									$("#activiti-datagrid-delegatePage").datagrid('reload');
								}
								else{
									$.messager.alert("提醒", "删除失败！");
								}
							}
						});
					}
				});
			});
			$("#activiti-select-delegatePage").change(function(){

                var v = $(this).val();

                $("#activiti-datagrid-delegatePage").datagrid('showColumn', 'username');
                $("#activiti-datagrid-delegatePage").datagrid('showColumn', 'delegateusername');

				if(v == "0"){
					$("#activiti-datagrid-delegatePage").datagrid({
						columns:delegateJson,
						queryParams:{type:$(this).val()}
					});
				}
				else if(v == "1"){
					$("#activiti-datagrid-delegatePage").datagrid({
						columns:delegateJson,
						queryParams:{type:$(this).val()}
					});
				}
			});
			var delegateJson = [[
				{field:'id', title:'编号', width: 80, align:'center'},
				{field:'definitionname', title:'流程名称', width: 300,
                    formatter: function(value, row, index){

                        if(row.definitionkey == '0') {
                            return "全部流程";
                        }
                        return value;
                    }
                },
                {field:'username', title:'委托人', width: 80},
                {field:'delegateusername', title:'被委托人', width: 80},
				{field:'begindate', title:'有效期', width: 280},
				{field:'status', title:'状态', width: 80, align:'center'}
			]];

			$("#activiti-datagrid-delegatePage").datagrid({
				columns:delegateJson,
				title:"我的工作委托",
				fit:true,
				border:false,
				method:'post',
				queryParams:{type:'0'},
				rownumbers:true,
				pagination:true,
		 		singleSelect:true,
				pageSize:10,
				url:'act/getDelegateList.do',
				toolbar:'#activiti-tool-delegatePage'
			});	
		});
	</script>
  </body>
</html>
