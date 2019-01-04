<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>字段权限添加页面</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true">  
        <div  data-options="region:'north',border:true" style="height: 120px;">
       	<form action="accesscontrol/addAccessColumn.do" method="post" id="accesscontrol-form-addAccessColumn${type}">
           	<div style="padding:10px 5px;">
			<p>
				<label>表资源:</label>
				<input id="accesscontrol-select-tableList${type}" class="easyui-combobox"  style="width:400px" name="accessColumn.tablename" />
				<input type="hidden" id="accesscontrol-hidden-tabledescname${type}" name="accessColumn.tabledescname">
			</p>
			<p>
				<label>描&nbsp;&nbsp;述:</label>
				<input type="text" name="accessColumn.description" style="width:400px;"/>
				<input type="hidden" id="accesscontrol-hidden-collist${type}" name="accessColumn.collist">
				<input type="hidden" id="accesscontrol-hidden-editcollist${type}" name="accessColumn.editcollist">
				<input type="hidden" name="authorityid" value="${authorityid }">
			</p>
			</div>
		</form>
	    </div>
	    <div data-options="region:'center',border:true">
    		<table id="accesscontrol-table-accessColumnAddList${type}"></table>
	    </div>
	    <div data-options="region:'south',border:true" style="height: 40px;">
	    	<div class="buttonDivR">
	    		<a href="javaScript:void(0);" id="accesscontrol-button-saveAccessColumn${type}" class="easyui-linkbutton" data-options="plain:true,iconCls:'button-save'">保存</a>
	    	</div>
	    </div>
	 </div>
	 <script type="text/javascript">
	 	$("#accesscontrol-select-tableList${type}").combobox({  
		    url:'accesscontrol/getUnAddTableList.do?authorityid=${authorityid }',  
		    valueField:'id',  
		    textField:'name',
		    onSelect:function(record){
		    	$("#accesscontrol-hidden-tabledescname${type}").attr("value",record.name);
		    	var columns = [];
		    	var rowJson = "{";
		    	var rowJsonEdit = "{";
		    	var fieldtype = {field:'columntype',title:'权限类别',width:80};
           		columns.push(fieldtype);
           		rowJson += '"columntype":"查看"';
           		rowJsonEdit += '"columntype":"编辑"';
		    	$.ajax({   
		            url :'common/getTableColList.do?tablename='+record.id+'&usecolprivilege=0',
		            type:'post',
		            dataType:'json',
		            async:false,
		            success:function(json){
		            	if(json!=null){
			            	for(var i=0;i<json.length;i++){
			            		var field = {field:json[i].id,title:json[i].name,align:'center',width:80,
			            						formatter:function(val,row,rowIndex){
			            							return '<input type="checkbox" name="checkbox'+rowIndex+'" disabled="disabled" checked="checked" value="'+val+'"/>';
			            						}
			            					};
			            		columns.push(field);
		            			rowJson += ',"'+json[i].id+'":"'+json[i].id+'"';
		            			rowJsonEdit += ',"'+json[i].id+'":"'+json[i].id+'"';
			            	}
		            	}
		            }
		        });
		        $.ajax({   
		            url :'common/getTableColList.do?tablename='+record.id+'&usecolprivilege=1',
		            type:'post',
		            dataType:'json',
		            async:false,
		            success:function(json){
		            	if(json!=null){
			            	for(var i=0;i<json.length;i++){
			            		var dgg =  json[i].id;
			            		var field = {field:json[i].id,title:json[i].name,align:'center',width:80,
			            						formatter:function(val,row,rowIndex){
			            							return '<input type="checkbox" name="checkbox'+rowIndex+'" value="'+val+'"/>';
			            						}
			            					
			            					};
			            		columns.push(field);
		            			rowJson += ',"'+json[i].id+'":"'+json[i].id+'"';
		            			rowJsonEdit += ',"'+json[i].id+'":"'+json[i].id+'"';
			            	}
		            	}
		            }
		        });
		        rowJson +="}";
		        rowJsonEdit += "}";
		        $("#accesscontrol-table-accessColumnAddList${type}").html("");
            	$("#accesscontrol-table-accessColumnAddList${type}").datagrid({ 
		  	 		fit:true,
		  	 		method:'post',
		  	 		singleSelect:true,
		  	 		title:'可访问字段',
				    columns:[columns]
				});
				var rows =  $("#accesscontrol-table-accessColumnAddList${type}").datagrid('getRows');
				if(rows.length>0){
					$("#accesscontrol-table-accessColumnAddList${type}").datagrid('deleteRow',0);
					$("#accesscontrol-table-accessColumnAddList${type}").datagrid('deleteRow',0);
				}
				$("#accesscontrol-table-accessColumnAddList${type}").datagrid('appendRow',$.parseJSON(rowJson));
				$("#accesscontrol-table-accessColumnAddList${type}").datagrid('appendRow',$.parseJSON(rowJsonEdit));
				
		    } 
		});
		$(function(){
			$("#accesscontrol-button-saveAccessColumn${type}").click(function(){
				var tableid = $('#accesscontrol-select-tableList${type}').combobox('getValue');
				if(tableid==null||tableid==""){
					$.messager.alert("提醒","请选择表资源");
					return false;
				}
				$.messager.confirm("提醒", "是否添加字段权限?", function(r){
					if (r){
						var str = "";
						$("input[name='checkbox0']:checkbox").each(function(){ 
			                if($(this).attr("checked")){
			                    str += $(this).val()+","
			                }
			            })
			            var editStr = "";
			            $("input[name='checkbox1']:checkbox").each(function(){ 
			                if($(this).attr("checked")){
			                    editStr += $(this).val()+","
			                }
			            })
			            str = str.substr(0,str.length-1);
			            editStr = editStr.substr(0,editStr.length-1);
						$("#accesscontrol-hidden-collist${type}").attr("value",str);
						$("#accesscontrol-hidden-editcollist${type}").attr("value",editStr);
						$("#accesscontrol-form-addAccessColumn${type}").submit();
					}
				});
			});
			$("#accesscontrol-form-addAccessColumn${type}").form({  
			    onSubmit: function(){  
			    	var flag = $(this).form('validate');
			    	if(flag==false){
			    		return false;
			    	}
			    },  
			    success:function(data){
			    	//转为json对象
			    	var json = $.parseJSON(data);
			        if(json.flag==true){
			        	$.messager.alert("提醒","添加成功");
			        	<c:if test="${type=='add'}">
			        	$("#accesscontrol-addAuthority-addColumnList").datagrid("reload");
			        	$("#accesscontrol-dialog-addAccessColumn").window('close',true);
			        	</c:if>
			        	<c:if test="${type=='edit'}">
			        	$("#accesscontrol-table-columnAccess").datagrid("reload");
			        	$("#accesscontrol-window-addColumn").window('close',true);
			        	</c:if>
			        }else{
			        	$.messager.alert("提醒","添加失败");
			        }
			    }  
			}); 
		});
	 </script>
	 
  </body>
</html>
