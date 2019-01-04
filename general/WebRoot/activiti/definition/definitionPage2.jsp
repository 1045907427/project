<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>流程定义管理</title>
  </head>
  <body>
  	<div class="easyui-layout" data-options="fit:true,border:false">
    	<div data-options="region:'north',border:false">
    		<div class="buttonBG" id="activiti-buttons-definitionPage2" style="height:26px;">
                <a href="javascript:;" id="activiti-design-definitionPage2" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'">在线设计</a>
                <a href="javascript:;" id="activiti-edit-definitionPage2" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-edit'" title="修改后的流程需重新布署">修改设计</a>
                <a href="javascript:;" id="activiti-deploy-definitionPage2" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-import'" title="流程布署后才可使用">布署</a>
                <a href="javascript:;" id="activiti-setting-definitionPage2" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-setting'">设置</a>
                <security:authorize url="/act/deleteDefinition.do">
                    <a href="javascript:;" id="activiti-delete-definitionPage2" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-delete'">删除</a>
    			</security:authorize>
                <!-- <a href="javascript:;" id="activiti-open-definitionPage2" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-open'">启用</a>
    			<a href="javascript:;" id="activiti-close-definitionPage2" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-close'">禁用</a>
    			<a href="javascript:;" id="activiti-delete-definitionPage2" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-delete'">删除</a> -->
    		</div>
    		<div id="activiti-query-definitionPage2">
		    	<form>
		    	</form>
		    </div>
    	</div>
    	<div data-options="region:'center',border:false">
    		<div id="activiti-datagrid-definitionPage2"></div>
    	</div>
    </div>
  	<div id="activiti-dialog-definitionPage2"></div>
	<script type="text/javascript">
		$(function(){
			$("#activiti-datagrid-definitionPage2").datagrid({
				columns:[[
						{field:"id", title:"编号", width:40, hidden: true},
						{field:"name", title:"流程名称", width:200},
						{field:"unkey", title:"流程标识", width:170},
						{field:"type", title:"流程分类", width:60,
							formatter: function(value,row,index){
                                return value;
                                // BUG 3436
//								if(row.definitionType != null){
//									return row.definitionType.name;
//								}
							}
						},
						{field:"isdeploy", title:"布署状态", width:70,
							formatter: function(value,row,index){
								if(value == "0"){
									return "未布署";
								}
								else if(value == "1"){
									if(row.ismodify == "0"){
										return "<font color='green'><b>已布署</b></font>";
									}
									else if(row.ismodify == "1"){
										return "<font color='green'><b>已布署</b></font><img src='image/btn/info_w64.png' style='width:16px;height:16px;position:relative;top:4px;margin-left:2px;' title='有修改但未重新布署' />";
									}
								}
							}
						},
						{field:"state", title:"启用状态", width:60,
							formatter: function(value,row,index){
								if(value == "0"){
									return "<font color='red'>禁用</font>";
								}
								else if(value == "1"){
									return "<font color='green'><b>启用</b></font>";
								}
							}
						},
						{field:"version", title:"当前版本", width:60},
						{field:"formtype", title:"表单类型", width:60,
							formatter: function(value,row,index){
								if(value == "formkey"){
									return "在线表单";
								}
								else if(value == "business"){
									return "URL表单";
								}
							}
						},
						{field:"adddate", title:"创建时间", width:120}
					]],
				url:'act/getDefinitionList.do',
				queryParams:{type:"${type }"},
				fit:true,
				rownumbers:true,
				pagination:false,
		 		idField:'id',
		 		singleSelect:true,
				pageSize:10,
				pageList:[10,20,30,50]
			});
			
			$("#activiti-dialog-definitionPage2").dialog({
				title:'流程定义管理->设置',
				width:800,
				height:450,
				modal:true,
				closed:true,
				cache:false,
				maximizable:true,
				maximized:true,
				resizable:true
			});
			$("#activiti-design-definitionPage2").click(function(){
				loading("设计界面打开中...");
				$.ajax({
					url:"act/createNewModel.do",
					type:"post",
					dataType:"json",
					success:function(json){
						loadDesignPage(json.modelId);
					}
				});
			});
			$("#activiti-edit-definitionPage2").click(function(){
				var row = getSelectedRow();
				if(row == null) return;
				loading("设计界面打开中...");
				var id = row.modelid;
				loadDesignPage(id);
			});
			$("#activiti-deploy-definitionPage2").click(function(){
				var row = getSelectedRow();
				if(row == null) return;
				$.messager.confirm("提醒", "确定重新布署该流程？<br/>布署成功后请确认流程设置是否正确。", function(r){
					if(r){
						loading("布署中...");
						var key = row.unkey;
						$.ajax({
							url:'act/deployDefinition.do',
							dataType:'json',
							type:'post',
							data:'definitionkey='+ key,
							success:function(json){
								loaded();
								if(json.flag == true){
									$.messager.alert("提醒", "布署成功。<br/>请查看流程设置是否正确！");
									$("#activiti-datagrid-definitionPage2").datagrid("reload");
								} else{
									$.messager.alert("提醒", "布署失败！");
								}
							},
							error: function (XMLHttpRequest, textStatus, errorThrown) {
							    $.messager.alert('错误', '出错了！');
							    loaded();
                                console.console(textStatus);
                                console.console(errorThrown);
                            }
						});
					}
				});
			});
			$("#activiti-setting-definitionPage2").click(function(){
				var row = getSelectedRow();
				if(row == null) return;
				if(row.isdeploy != "1"){
				 	$.messager.alert("提醒", "流程未布署，不可进行设置。");
				 	return ;
				 }
				 $("#activiti-dialog-definitionPage2").dialog({
				 	title:"流程定义管理->设置->"+ row.name,
				 	href:'act/definitionSettingPage.do?definitionkey='+ row.unkey
				 });
				 $("#activiti-dialog-definitionPage2").dialog("open");
			});

            // 删除流程
            $('#activiti-delete-definitionPage2').click(function() {
                var row = getSelectedRow();
                if(row == null) {
                    return false;
                }

                $.ajax({
                    type: 'post',
                    dataType: 'json',
                    url: 'act/getWorkQueryList.do',
                    data: {definition: row.unkey},
                    success: function(data) {

                        if(data.total > 0) {

                            $.messager.alert('提醒', '该流程定义对应的工作已经存在，无法删除！');
                            return false;
                        }

                        $.messager.confirm('确认', '是否要删除该流程定义？删除后将无法恢复！', function(r) {

                            if(r) {
                                $.ajax({
                                    type: 'post',
                                    dataType: 'json',
                                    url: 'act/deleteDefinition.do',
                                    data: {modelid: row.modelid, unkey: row.unkey},
                                    success: function(json) {

                                        if(json.flag) {

                                            $.messager.alert('提醒', '删除成功。');
                                            $("#activiti-datagrid-definitionPage2").datagrid('reload');
                                            return false;
                                        }
                                        $.messager.alert('提醒', '删除失败！');
                                    },
                                    error: function(){}
                                });
                            }

                        });
                    },
                    error: function(){}
                });

            });
		});
		function getSelectedRow(){
			var row = $("#activiti-datagrid-definitionPage2").datagrid("getSelected");
			if(row == null){
				$.messager.alert("提醒", "请选择需操作的记录");
			}
			return row;
		}
		function loadDesignPage(id){
			window.open('act/modelDesignPage.do?modelId='+id,'模型设计');
			loaded();
		}
	</script>
  </body>
</html>
