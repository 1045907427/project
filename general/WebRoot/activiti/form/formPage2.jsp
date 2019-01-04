<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>流程定义管理</title>
  </head>
  <body>
  	<div class="easyui-layout" data-options="fit:true,border:false">
    	<div data-options="region:'north',border:false">
    		<div class="buttonBG" id="activiti-buttons-formPage2" style="height:26px;">
    			<a href="javascript:;" id="activiti-add-formPage2" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'">添加</a>
    			<a href="javascript:;" id="activiti-edit-formPage2" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-edit'">修改</a>
				<a href="javascript:;" id="activiti-delete-formPage2" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-delete'">删除</a>    			
    			<a href="javascript:;" id="activiti-view-formPage2" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-view'">预览</a>
    		</div>
    		<div id="activiti-query-formPage2">
		    	<form>
		    	</form>
		    </div>
    	</div>
    	<div data-options="region:'center',border:false">
    		<!-- <div id="activiti-datagrid-formPage2"></div> -->
            <table id="activiti-datagrid-formPage2"></table>
    	</div>
    </div>
    <div id="activiti-dialog-formPage2"></div>
    <div id="activiti-dialog2-formPage2"></div>
	<script type="text/javascript">
		$(function(){
			$("#activiti-datagrid-formPage2").datagrid({
				columns:[[
						{field:"id", title:"编号", width:60, hidden: true},
                        {field:"name", title:"表单名称", width:250},
                        {field:"unkey", title:"表单标识", width:120},
                        {field:"intro", title:"表单说明", width:250},
						{field:"type", title:"表单分类", width:80,
							formatter: function(value,row,index){
                                if(row.formType != null) {
                                    return row.formType.name;
                                }
							}
						},
						{field:"adddate", title:"创建时间", width:120}
					]],
				url:'act/getFormList.do',
				queryParams:{type:'${type }'},
				fit:true,
				rownumbers:true,
				pagination:false,
		 		idField:'id',
		 		singleSelect:true,
				onDblClickRow: function(rowIndex, rowData){
					$("#activiti-dialog-formPage2").dialog({
						title:'表单预览',
						maximized:true,
						href:'act/formPreviewPage.do?key='+ rowData.unkey
					});
				$("#activiti-dialog-formPage2").dialog('open');
				}
			});
			
			$("#activiti-dialog-formPage2").dialog({
				title:'新建表单',
				width:500,
				height:300,
				maximized:false,
				modal:true,
				closed:true,
				cache:false,
				maximizable:false,
				resizable:true
			});
			$("#activiti-add-formPage2").click(function(){
				$("#activiti-dialog-formPage2").dialog({
					href:'act/formAddPage.do',
					buttons:[
							{
								iconCls:'button-next',
								text:'下一步',
								handler:function(){
									var flag = $("#activiti-form-formAddPage").form("validate");
									if(flag == false) return ;
                                    var w = window.open('', 'form_design_window');
									$("#activiti-form-formAddPage").submit();
									$("#activiti-dialog-formPage2").dialog('close');
                                    w.focus();
								}
							}
						]
				});
				$("#activiti-dialog-formPage2").dialog('open');
			});
			$("#activiti-edit-formPage2").click(function(){
				var row = getSelectedRow();
				if(row == null) return;
				var w = window.open('act/formDesignPage2.do?key=' + row.unkey, 'form_design_window');
                w.focus();
			});
			$("#activiti-delete-formPage2").click(function(){
				var row = getSelectedRow();
				if(row == null) return;

                $.ajax({
                    type: 'post',
                    url: 'act/isFormReferencedByDefinition.do',
                    data: {formkey: row.unkey},
                    dataType: 'json',
                    success: function(json) {

                        if(json.count > 0) {
                            $.messager.alert('提醒', '该表单已被引用，无法删除。');
                            return false;
                        }

                        $.messager.confirm("提醒", "确定删除该表单？", function(r){
                            if(r){
                                loading("表单删除中...");
                                $.ajax({
                                    url:'act/deleteForm.do',
                                    dataType:'json',
                                    type:'post',
                                    data:{id: row.id, unkey: row.unkey},
                                    success:function(json){
                                        loaded();
                                        if(json.flag == true){
                                            $.messager.alert("提醒", "删除成功");
                                            $("#activiti-datagrid-formPage2").datagrid("reload");
                                        }
                                        else{
                                            $.messager.alert("提醒", "删除失败");
                                        }
                                    }
                                });
                            }
                        });
                    }
                });

			});
			$("#activiti-view-formPage2").click(function(){
				var row = getSelectedRow();
				if(row == null) return;
				$("#activiti-dialog2-formPage2").dialog({
					title:'表单预览',
					maximized:true,
                    width:500,
                    height:300,
                    modal:true,
                    closed:true,
                    cache:false,
                    maximizable:false,
                    resizable:true,
					href:'act/formPreviewPage.do?key=' + row.unkey + '&name=' + row.name + '&intro=' + row.intro
				});
				$("#activiti-dialog2-formPage2").dialog('open');
			});
		});
		function getSelectedRow(){
			var row = $("#activiti-datagrid-formPage2").datagrid("getSelected");
			if(row == null){
				$.messager.alert("提醒", "请选择需操作的记录");
			}
			return row;
		}
	</script>
  </body>
</html>
