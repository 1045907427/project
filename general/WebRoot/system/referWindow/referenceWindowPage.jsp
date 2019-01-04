<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>参照窗口</title>
    <%@include file="/include.jsp" %>   
  </head>
  
  <body>
  <div id="referWindow-form-query"style="height:auto;padding:0px">
      	<div class="buttonBG">
	    	<a href="javaScript:void(0);" id="referWindow-buton-add" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'">新增</a>
           	<a href="javaScript:void(0);" id="referWindow-buton-edit" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-edit'">修改</a>
            	<a href="javaScript:void(0);" id="referWindow-buton-delete" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-delete'">删除</a>
            	<a href="javaScript:void(0);" id="referWindow-buton-copy" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-copy'">复制</a>
           	<a href="javaScript:void(0);" id="referWindow-buton-enable" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-open'">启用</a>
            	<a href="javaScript:void(0);" id="referWindow-buton-disable" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-close'">禁用</a>
            	<a href="javaScript:void(0);" id="referWindow-buton-view" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-view'">查看</a>
	    </div>
        <form action="" method="post" id="referWindow-form" style="padding: 5px">
            窗口编号:<input type="text" name="id" style="width: 180px;"/>
            窗口名称:<input type="text" name="wname" style="width: 180px;"/>
            <a href="javaScript:void(0);" id="referWindow-query" class="button-qr">查询</a>
            <a href="javaScript:void(0);" id="referWindow-reset" class="button-qr">重置</a>
        </form>
  </div>
      	<table id="referWindow-table-list"></table>

    <div id="referWindow-window-view"></div>
    <div id="referWindow-window-add"></div>
    <div id="referWindow-window-edit"></div>
    <script type="text/javascript">
    	$(function(){
            $("#referWindow-table-list").datagrid({
                method:'post',
                columns:[[
                    {field:'id',title:'窗口编号',width:120},
                    {field:'wname',title:'窗口名称',width:150},
                    {field:'state',title:'状态',width:60,
                        formatter:function(val){
                            if(val=='4'){
                                return "新增";
                            }else if(val=='3'){
                                return "暂存";
                            }else if(val=='2'){
                                return "保存";
                            }else if(val=='1'){
                                return "启用";
                            }else if(val=='0'){
                                return "禁用";
                            }
                        }
                    },
//                    {field:'tables',title:'涉及到的表',width:150},
                    {field:'sqlstr',title:'基本SQL',width:400},
                    {field:'remark',title:'备注',width:150}
                ]],
                fit:true,
                rownumbers:true,
                pagination:true,
                idField:'id',
                singleSelect:true,
                toolbar:'#referWindow-form-query',
                url:'system/referWindow/showReferWindowList.do',
                onDblClickRow:function(rowIndex, rowData){
                    $('#referWindow-window-view').window({
                        title: rowData.wname,
                        width: 650,
                        height: 400,
                        closed: true,
                        cache: false,
                        href: 'system/referWindow/showReferWindow.do?id='+rowData.id,
                        modal: true
                    });
                    $('#referWindow-window-view').window("open");
                }
            });
    		//显示参照窗口添加页面
    		$("#referWindow-buton-add").click(function(){
				top.addTab('system/referWindow/showReferWindowAddPage.do','参照窗口新增');
    		});
    		//删除
    		$("#referWindow-buton-delete").click(function(){
    			var referWindow = $("#referWindow-table-list").datagrid('getSelected');
		    	if(referWindow==null){
		    		$.messager.alert("提醒","请选择参照窗口!");
		    		return false;
		    	}
		    	if(referWindow.state=="1"){
		    		$.messager.alert("提醒","该参照窗口处于启用状态不能删除!");
		    		return false;
		    	}
		    	$.messager.confirm("提醒", "是否删除参照窗口:"+referWindow.wname+"?", function(r){
					if (r){
				    	$.ajax({   
				            url :'system/referWindow/deleteReferWindow.do?id='+referWindow.id,
				            type:'post',
				            dataType:'json',
				            success:function(json){
				            	if(json.flag==true){
				            		$.messager.alert("提醒","删除成功！");
				            		$('#referWindow-table-list').datagrid('reload');
				            	}else{
				            		$.messager.alert("提醒","删除失败！");
				            	}
				            }
				        });
		        	}
		        });
    		});
    		//启用参照窗口
    		$("#referWindow-buton-enable").click(function(){
    			var referWindow = $("#referWindow-table-list").datagrid('getSelected');
		    	if(referWindow==null){
		    		$.messager.alert("提醒","请选择参照窗口!");
		    		return false;
		    	}
		    	if(referWindow.state!="0" && referWindow.state!="2"){
		    		$.messager.alert("提醒","只有在保存或者禁用状态下才能启用!");
		    		return false;
		    	}
		    	$.messager.confirm("提醒", "是否启用参照窗口:"+referWindow.wname+"?", function(r){
					if (r){
				    	$.ajax({   
				            url :'system/referWindow/openReferWindow.do?id='+referWindow.id,
				            type:'post',
				            dataType:'json',
				            success:function(json){
				            	if(json.flag==true){
				            		$.messager.alert("提醒","启用成功！");
				            		$('#referWindow-table-list').datagrid('reload');
				            	}else{
				            		$.messager.alert("提醒","启用失败！");
				            	}
				            }
				        });
		        	}
		        });
    		});
    		//禁用参照窗口
    		$("#referWindow-buton-disable").click(function(){
    			var referWindow = $("#referWindow-table-list").datagrid('getSelected');
		    	if(referWindow==null){
		    		$.messager.alert("提醒","请选择参照窗口!");
		    		return false;
		    	}
		    	if(referWindow.state!="1"){
		    		$.messager.alert("提醒","只有在启用状态下才能禁用用!");
		    		return false;
		    	}
		    	$.messager.confirm("提醒", "是否禁用参照窗口:"+referWindow.wname+"?", function(r){
					if (r){
				    	$.ajax({   
				            url :'system/referWindow/closeReferWindow.do?id='+referWindow.id,
				            type:'post',
				            dataType:'json',
				            success:function(json){
				            	if(json.flag==true){
				            		$.messager.alert("提醒","禁用成功！");
				            		$('#referWindow-table-list').datagrid('reload');
				            	}else{
				            		$.messager.alert("提醒","禁用失败！");
				            	}
				            }
				        });
		        	}
		        });
    		});
    		//查看数据窗口
    		$("#referWindow-buton-view").click(function(){
    			var referWindow = $("#referWindow-table-list").datagrid('getSelected');
		    	if(referWindow==null){
		    		$.messager.alert("提醒","请选择参照窗口!");
		    		return false;
		    	}
		    	$('#referWindow-window-view').window({  
				    title: referWindow.wname,  
				    width: 650,  
				    height: 400,  
				    closed: true,  
				    cache: false,  
				    href: 'system/referWindow/showReferWindow.do?id='+referWindow.id,  
				    modal: true
				});
				$('#referWindow-window-view').window("open");
    		});
    		//修改参照窗口
    		$("#referWindow-buton-edit").click(function(){
    			var referWindow = $("#referWindow-table-list").datagrid('getSelected');
		    	if(referWindow==null){
		    		$.messager.alert("提醒","请选择参照窗口!");
		    		return false;
		    	}
				top.addTab('system/referWindow/showReferWindowEditPage.do?id='+referWindow.id,'参照窗口修改');
    		});
    		$("#referWindow-buton-copy").click(function(){
    			var referWindow = $("#referWindow-table-list").datagrid('getSelected');
		    	if(referWindow==null){
		    		$.messager.alert("提醒","请选择参照窗口!");
		    		return false;
		    	}
				top.addTab('system/referWindow/showReferWindowCopyPage.do?id='+referWindow.id,'参照窗口复制');
        	});
        	$("#referWindow-query").click(function(){
        		//把form表单的name序列化成JSON对象
	       		var queryJSON = $("#referWindow-form").serializeJSON();
	       		$("#referWindow-table-list").datagrid("load",queryJSON);
        	});
        	$("#referWindow-reset").click(function(){
	       		$("#referWindow-form")[0].reset();
	       		$("#referWindow-table-list").datagrid("load",{});
        	});
    	});
    </script>
  </body>
</html>
