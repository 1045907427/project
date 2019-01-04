<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>数据权限</title>
	<%@include file="/include.jsp" %> 
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true">  
        <div title="数据权限资源列表" data-options="region:'west'" style="width:300px;">
           	<div id="accesscontrol-toolbar-dataruleList" class="buttonBG">
           		<a href="javaScript:void(0);" id="accesscontrol-add-addDatarule" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-add'">新增</a>
           		<a href="javaScript:void(0);" id="accesscontrol-button-editDatarule" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-edit'">修改</a>
           		<a href="javaScript:void(0);" id="accesscontrol-delete-deleteDatarule" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-delete'">删除</a>
           	</div>
           	<table id="datarule-table-dataruleList"></table>
		</div>
		<div title="规则" data-options="region:'center'" style="padding: 2px;">
			<div id="datarule-div-ruleInfo"></div>
		</div>
	</div>
	<script type="text/javascript">
		$('#datarule-table-dataruleList').datagrid({ 
  	 		fit:true,
  	 		method:'post',
  	 		rownumbers:true,
  	 		idField:'dataid',
  	 		singleSelect:true,
  	 		toolbar:"#accesscontrol-toolbar-dataruleList",
  	 		url:'accesscontrol/showDataruleList.do',
		    columns:[[  
		    	{field:'dataname',title:'资源名称',width:150},  		        
				{field:'type',title:'类型',width:100,
		        	formatter:function(val){
		        		if(val=='1'){
		        			return "数据字典";
		        		}else{
		        			return "参照窗口";
		        		}
		        	}
		        }
		    ]],
		    onClickRow:function(rowIndex, rowData){
		    	$("#datarule-div-ruleInfo").queryRule({
					rules:rowData.rule,
					name:rowData.tablename,
					restype:rowData.type,
					type:'view'
				});
		    }
		});
		$(function(){
			//添加数据权限规则
			$("#accesscontrol-add-addDatarule").click(function(){
				top.addTab('accesscontrol/showDataruleAddPage.do','数据权限新增');
			});
			//删除数据权限规则
			$("#accesscontrol-delete-deleteDatarule").click(function(){
				var datarule = $("#datarule-table-dataruleList").datagrid('getSelected');
				if(datarule==null){
					$.messager.alert("提醒","请选择数据权限资源！");
		    		return false;
				}else{
					$.messager.confirm("提醒", "是否删除"+datarule.dataname+"数据权限?", function(r){
						if (r){
							var url = "accesscontrol/deleteDatarule.do?dataid="+datarule.dataid;
							$.ajax({   
					            url :url,
					            type:'post',
					            dataType:'json',
					            success:function(json){
					            	if(json.flag==true){
					            		$.messager.alert("提醒","删除成功！");
					            		$('#datarule-table-dataruleList').datagrid('reload');
					            		$("#datarule-div-ruleInfo").html("");
					            	}else{
					            		$.messager.alert("提醒","删除失败！");
					            	}
					            }
					        });
				        }
					});
				}
			});
			//显示数据规则修改页面
			$("#accesscontrol-button-editDatarule").click(function(){
				var datarule = $("#datarule-table-dataruleList").datagrid('getSelected');
                if(datarule==null){
                    $.messager.alert("提醒","请选择数据权限资源！");
                    return false;
                }else{
                    var url = "accesscontrol/showDataruleEditPage.do?dataid="+datarule.dataid;
                    top.addTab(url,'数据权限修改');
                }
				
			});
		});
		
	</script>
  </body>
</html>
