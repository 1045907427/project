<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>数据异常规则定义</title>
	<%@include file="/include.jsp" %>  
	<script type="text/javascript" src="js/datagrid-detailview.js"></script>
  </head>
  
  <body>
      <table id="dataexception-table-list"></table>
      <div id="dataexception-query-showDataexceptionList" style="padding:5px;height:auto">
   		<div>
			<form action="" id="dataexception-form-query" method="post">
				单据名称:<input name="name" style="width:120px">
				<a href="javaScript:void(0);" id="dataexception-queay-queryList" class="button-qr">查询</a>
				<a href="javaScript:void(0);" id="dataexception-queay-reloadList" class="buton-qr">重置</a>
			</form>
			<div>
				<a href="javaScript:void(0);" id="dataexception-button-add" class="easyui-linkbutton" iconCls="button-add" plain="true">新增</a>
				<a href="javaScript:void(0);" id="dataexception-button-edit" class="easyui-linkbutton" iconCls="button-edit" plain="true">修改</a>
				<a href="javaScript:void(0);" id="dataexception-button-remove" class="easyui-linkbutton" iconCls="button-delete" plain="true">删除</a>
				<a href="javaScript:void(0);" id="dataexception-button-openDataexception" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-open'">启用</a>
            	<a href="javaScript:void(0);" id="dataexception-button-closeDataexception" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-close'">停用</a>
			</div>
		</div>
      </div>
      <div id="dataexception-window-addDataException"></div>
      <div id="dataexception-window-editDataException"></div>
      <div id="dataexception-window-addDataExceptionOperate"></div>
      <script type="text/javascript">
	      	$('#dataexception-table-list').datagrid({  
	      		method:'post',
  	 			rownumbers:true,
      			title:'',
      			fit:true,
	  	 		pagination:true,
	  	 		singleSelect:true,
				url:'system/dataexception/showDataExceptionList.do',
				toolbar:'#dataexception-query-showDataexceptionList',
      			columns:[[
      				{field:'name',title:'单据名称',width:130},
      				{field:'mtable',title:'主表名',width:80},
      				{field:'mcolumn',title:'主表字段',width:80},
      				{field:'type',title:'校验取值类型',width:100,
      					formatter:function(val){
				        		if(val=='1'){
				        			return '固定值';
				        		}else if(val=='2'){
				        			return '其他表';
				        		}
				        	}
      				},
      				{field:'val',title:'固定值',width:80},
      				{field:'rtable',title:'对应表表名',width:100},
      				{field:'rcolumn',title:'对应表字段',width:100},
      				{field:'mrelatecolumn',title:'主表关联字段',width:100},
      				{field:'rrelatecolumn',title:'对应表关联字段',width:100},
      				{field:'normalup',title:'正常范围上限%',width:100},
      				{field:'normaldown',title:'正常范围下限%',width:100},
      				{field:'exceptionup',title:'异常范围上限%',width:100},
      				{field:'exceptiondown',title:'异常范围下限%',width:100},
      				{field:'exremind',title:'异常提醒内容',width:100},
      				{field:'state',title:'状态',width:100,
      					formatter:function(val){
			        		if(val=='1'){
			        			return '启用';
			        		}else if(val=='0'){
			        			return '停用';
			        		}
			        	}
      				}
      			]],
                view: detailview,  
                detailFormatter:function(index,row){ 
                    return '<div style="padding:2px"><table id="dataexception-ddv-' + index + '"></table>'+
                    		'<div id="dataexception-ddv-oper-'+index+'"><a href="javaScript:void(0);" class="easyui-linkbutton operateButton" iconCls="button-add" plain="true" pid="'+row.id+'" pname="'+row.name+'" divid="dataexception-ddv-'+index+'">新增</a>'+
                    		'<a href="javaScript:void(0);" class="easyui-linkbutton operateDeleteButton" iconCls="button-delete" plain="true" pid="'+row.id+'" pname="'+row.name+'" divid="dataexception-ddv-'+index+'">删除</a></div></div>';
                },  
                onExpandRow: function(index,row){  
                    $('#dataexception-ddv-'+index).datagrid({ 
                    	title:'对应功能及操作', 
                        url:'system/dataexception/showDataExceptionOperateList.do?dataexceptionid='+row.id,  
                        toolbar:'#dataexception-ddv-oper-'+index,
                        singleSelect:true,  
                        rownumbers:true, 
                        width:550, 
                        height:'auto',  
                        columns:[[  
                            {field:'menu',title:'功能',width:100},  
                            {field:'button',title:'操作',width:100},
                            {field:'url',title:'URL地址',width:250} 
                        ]],  
                        onResize:function(){  
                            $('#dataexception-table-list').datagrid('fixDetailRowHeight',index);  
                        },  
                        onLoadSuccess:function(){  
                            setTimeout(function(){  
                                $('#dataexception-table-list').datagrid('fixDetailRowHeight',index);  
                            },0);  
                        }  
                    });  
                    $.parser.parse("#dataexception-ddv-oper-"+index);
                    $('#dataexception-table-list').datagrid('fixDetailRowHeight',index);  
                }  
            });  
            $(function(){
            	//显示数据异常规则添加页面
            	$("#dataexception-button-add").click(function(){
            		$("#dataexception-window-addDataException").window({  
					    title: '数据异常规则新增',  
					    width: 800,  
					    height: 400,  
					    closed: true,  
					    cache: false,  
					    href: 'system/dataexception/showDataExceptionAddPage.do',  
					    modal: true
					});
					$("#dataexception-window-addDataException").window("open");
            	});
            	
            	//回车事件
				controlQueryAndResetByKey("dataexception-queay-queryList","dataexception-queay-reloadList");
            	
            	//查询
            	$("#dataexception-queay-queryList").click(function(){
            		//把form表单的name序列化成JSON对象
		       		var queryJSON = $("#dataexception-form-query").serializeJSON();
		       		$("#dataexception-table-list").datagrid("load",queryJSON);
            	});
            	//重置
            	$("#dataexception-queay-reloadList").click(function(){
            		$("#dataexception-form-query")[0].reset();
		       		$("#dataexception-table-list").datagrid("load",{});
            	});
            	$(".operateButton").live("click",function(){
            		var id = $(this).attr("pid");
            		var name = $(this).attr("pname");
            		var divid = $(this).attr("divid");
            		$("#dataexception-window-addDataExceptionOperate").dialog({
            			title: name+'对应功能操作新增',  
					    width: 400,  
					    height: 300,  
					    closed: true,  
					    cache: false,  
					    href: 'system/dataexception/showDataExceptionOperateAddPage.do?pid='+id+'&divid='+divid,  
					    modal: true
            		});
            		$("#dataexception-window-addDataExceptionOperate").dialog("open");
            	});
            	//删除数据异常规则下的对应功能
            	$(".operateDeleteButton").live("click",function(){
            		var id = $(this).attr("pid");
            		var divid = $(this).attr("divid");
            		var data = $("#"+divid).datagrid('getSelected');
            		$.messager.confirm("提醒", "是否删除数据异常规则下的对应操作功能?", function(r){
						if (r){
					    	if(data==null){
					    		$.messager.alert("提醒","请选择功能操作！");
					    		return false;
					    	}
		            		$.ajax({   
					            url :'system/dataexception/deleteDataExceptionOperate.do?id='+data.id,
					            type:'post',
					            dataType:'json',
					            success:function(json){
					            	if(json.flag==true){
					            		$.messager.alert("提醒","删除成功！");
					            		$('#'+divid).datagrid('reload');
					            	}else{
					            		$.messager.alert("提醒","删除失败！");
					            	}
					            }
					        });
					    }
					});
            	});
            	//删除数据异常规则
            	$("#dataexception-button-remove").click(function(){
            		$.messager.confirm("提醒", "是否删除数据异常规则?", function(r){
						if (r){
		            		var data = $("#dataexception-table-list").datagrid('getSelected');
					    	if(data==null){
					    		$.messager.alert("提醒","请选择数据异常规则！");
					    		return false;
					    	}
					    	$.ajax({   
					            url :'system/dataexception/deleteDataException.do?id='+data.id,
					            type:'post',
					            dataType:'json',
					            success:function(json){
					            	if(json.flag==true){
					            		$.messager.alert("提醒","删除成功！");
					            		$('#dataexception-table-list').datagrid('reload');
					            	}else{
					            		$.messager.alert("提醒","删除失败！");
					            	}
					            }
					        });
					   }
					});
            	});
            	//显示数据异常规则修改页面
            	$("#dataexception-button-edit").click(function(){
            		var data = $("#dataexception-table-list").datagrid('getSelected');
            		if(data==null){
			    		$.messager.alert("提醒","请选择数据异常规则！");
			    		return false;
			    	}
			    	var url = "system/dataexception/showDataExceptionEditPage.do?id="+data.id;
			    	$("#dataexception-window-editDataException").window({  
					    title: '数据异常规则修改',  
					    width: 800,  
					    height: 400,  
					    closed: true,  
					    cache: false,  
					    href: url,  
					    modal: true
					});
					$("#dataexception-window-editDataException").window("open");
            	});
            	//启用数据异常规则
            	$("#dataexception-button-openDataexception").click(function(){
            		$.messager.confirm("提醒", "是否启用数据异常规则?", function(r){
						if (r){
		            		var data = $("#dataexception-table-list").datagrid('getSelected');
					    	if(data==null){
					    		$.messager.alert("提醒","请选择数据异常规则！");
					    		return false;
					    	}
					    	$.ajax({   
					            url :'system/dataexception/openDataException.do?id='+data.id,
					            type:'post',
					            dataType:'json',
					            success:function(json){
					            	if(json.flag==true){
					            		$.messager.alert("提醒","启用成功！");
					            		$('#dataexception-table-list').datagrid('reload');
					            	}else{
					            		$.messager.alert("提醒","启用失败！");
					            	}
					            }
					        });
			    	 	}
					});
            	});
            	//停用数据异常规则
            	$("#dataexception-button-closeDataexception").click(function(){
            		$.messager.confirm("提醒", "是否停用数据异常规则?", function(r){
						if (r){
		            		var data = $("#dataexception-table-list").datagrid('getSelected');
					    	if(data==null){
					    		$.messager.alert("提醒","请选择数据异常规则！");
					    		return false;
					    	}
					    	$.ajax({   
					            url :'system/dataexception/closeDataException.do?id='+data.id,
					            type:'post',
					            dataType:'json',
					            success:function(json){
					            	if(json.flag==true){
					            		$.messager.alert("提醒","停用成功！");
					            		$('#dataexception-table-list').datagrid('reload');
					            	}else{
					            		$.messager.alert("提醒","停用失败！");
					            	}
					            }
					        });
			    	 	}
					});
            	});
            });
      </script>
  </body>
</html>
