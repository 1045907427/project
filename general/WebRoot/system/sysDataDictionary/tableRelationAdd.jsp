<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>添加表关联信息</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
	</head>

	<body>
		<form action="sysDataDictionary/tableRelationAdd.do" method="post"
			id="sysDataDictionary-form-addTableRelation">
			<div class="pageContent">
				<p>
					<label>
						基础表名:
					</label>
					<input id="sysDataDictionary-form-addTableRelation-maintablename"
						type="text" name="tableRelation.maintablename"
						style="width: 200px;"/>
				</p>
				<p>
					<label>
						基础表字段:
					</label>
					<input id="sysDataDictionary-form-addTableRelation-maincolumnname"
						type="text" name="tableRelation.maincolumnname"
						class="easyui-combogrid"
						style="width: 200px;" autocomplete="off" />
				</p>
<!--				<p>-->
<!--					<label>-->
<!--						基础表显示列名:-->
<!--					</label>-->
<!--					<input id="sysDataDictionary-form-addTableRelation-maintitlecolname"-->
<!--						type="text" name="tableRelation.maintitlecolname"-->
<!--						class="easyui-combogrid"-->
<!--						style="width: 200px;" autocomplete="off" />-->
<!--				</p>-->
				<p>
					<label>
						引用表名:
					</label>
					<input id="sysDataDictionary-form-addTableRelation-tablename"
						type="text" name="tableRelation.tablename"
						style="width: 200px;"/>
				</p>
				<p>
					<label>
						引用表字段:
					</label>
					<input id="sysDataDictionary-form-addTableRelation-columnname"
						type="text" name="tableRelation.columnname"
						class="easyui-combogrid"
						style="width: 200px;" autocomplete="off" />
				</p>
				<p>
					<label>
						表功能描述:
					</label>
					<input type="text" name="tableRelation.tabledescription"
						class="easyui-validatebox" required="true" style="width: 200px;"
						autocomplete="off" />
				</p>
				<p>
					<label>
						创建方式:
					</label>
					<select name="tableRelation.createmethod">
						<option value="1">
							预制
						</option>
						<option value="2">
							自建
						</option>
					</select>
				</p>
				<p>
					<label>
						删除校验:
					</label>
					<select name="tableRelation.deleteverify">
						<option value="0">
							否
						</option>
						<option value="1" selected="selected">
							是
						</option>
					</select>
				</p>
				<p>
					<label>
						级联替换:
					</label>
					<select name="tableRelation.cascadechange" disabled="disabled">
						<option value="0" selected="selected">
							否
						</option>
						<option value="1">
							是
						</option>
					</select>
				</p>
			</div>
		</form>
		<script type="text/javascript">
	$(function() {		
		$("#sysDataDictionary-form-addTableRelation-maintablename").combogrid({  
		    url:'sysDataDictionary/showTableInfoAllList.do', 
		    panelWidth:400,  
  	 		rownumbers:true,
		    idField:'tablename',  
		    textField:'tabledescname', 
		    columns:[[  
		        {field:'tablename',title:'表名',width:120},  
		        {field:'tabledescname',title:'描述名',width:120},
		        {field:'state',title:'使用状态',width:60,
			        formatter:function(val){
	  						if(val=='1'){
	  							return '启用';
	  						}
	  						else
	  						{
	  							return '停用';
	  						}
	  			}}
		    ]],
		    filter:function(q,row){
       		 	var id = row.tablename;
       		 	var text = row.tabledescname;
       		 	if(id.indexOf(q)==0 || text.indexOf(q)==0){
       		 		return true;
       		 	}else{
       		 		return false;
       		 	}
       		 },
		    onSelect:function(owIndex, rowData){
		    	var url = 'common/getTableColList.do?tablename='+rowData.tablename; 
	            $("#sysDataDictionary-form-addTableRelation-maincolumnname").combogrid('setValue','');
	            $("#sysDataDictionary-form-addTableRelation-maincolumnname").combogrid({'url':url});
//	            $("#sysDataDictionary-form-addTableRelation-maintitlecolname").combogrid('setValue','');
//	            $("#sysDataDictionary-form-addTableRelation-maintitlecolname").combogrid({'url':url});
		    }
		}); 
		$("#sysDataDictionary-form-addTableRelation-maincolumnname").combogrid({  
		    panelWidth:320,  
		    idField:'id',  
		    textField:'name', 
		    columns:[[  
		        {field:'id',title:'字段名',width:100},  
		        {field:'name',title:'字段描述名',width:100},  
		        {field:'datatype',title:'数据类型',width:80}
		    ]], 
		    filter:function(q,row){
       		 	var id = row.id;
       		 	var text = row.name;
       		 	if(id.indexOf(q)==0 || text.indexOf(q)==0){
       		 		return true;
       		 	}else{
       		 		return false;
       		 	}
       		 } 
		});
//		$("#sysDataDictionary-form-addTableRelation-maintitlecolname").combogrid({
//		    panelWidth:320,
//		    idField:'id',
//		    textField:'name',
//		    columns:[[
//		        {field:'id',title:'字段名',width:100},
//		        {field:'name',title:'字段描述名',width:100},
//		        {field:'datatype',title:'数据类型',width:80}
//		    ]],
//		    filter:function(q,row){
//       		 	var id = row.id;
//       		 	var text = row.name;
//       		 	if(id.indexOf(q)==0 || text.indexOf(q)==0){
//       		 		return true;
//       		 	}else{
//       		 		return false;
//       		 	}
//       		 }
//		});
		$("#sysDataDictionary-form-addTableRelation-tablename").combogrid({  
		    url:'sysDataDictionary/showTableInfoAllList.do', 
		    panelWidth:400,  
  	 		rownumbers:true,
		    idField:'tablename',  
		    textField:'tabledescname', 
		    columns:[[  
		        {field:'tablename',title:'表名',width:120},  
		        {field:'tabledescname',title:'描述名',width:120},
		        {field:'state',title:'使用状态',width:60,
			        formatter:function(val){
	  						if(val=='1'){
	  							return '启用';
	  						}
	  						else
	  						{
	  							return '停用';
	  						}
	  			}}
		    ]],
		    filter:function(q,row){
       		 	var id = row.tablename;
       		 	var text = row.tabledescname;
       		 	if(id.indexOf(q)==0 || text.indexOf(q)==0){
       		 		return true;
       		 	}else{
       		 		return false;
       		 	}
       		 } ,
		    onSelect:function(owIndex, rowData){
		    	var url = 'common/getTableColList.do?tablename='+rowData.tablename; 
	            $("#sysDataDictionary-form-addTableRelation-columnname").combogrid('setValue','');
	            $("#sysDataDictionary-form-addTableRelation-columnname").combogrid({'url':url});
		    } 
		}); 
		$("#sysDataDictionary-form-addTableRelation-columnname").combogrid({  
		    panelWidth:320,  
		    idField:'id',  
		    textField:'name',  
		    columns:[[  
		        {field:'id',title:'字段名',width:100},  
		        {field:'name',title:'字段描述名',width:100},  
		        {field:'datatype',title:'数据类型',width:80}
		    ]] , 
		    filter:function(q,row){
       		 	var id = row.id;
       		 	var text = row.name;
       		 	if(id.indexOf(q)==0 || text.indexOf(q)==0){
       		 		return true;
       		 	}else{
       		 		return false;
       		 	}
       		}
		});
		
		$("#sysDataDictionary-form-addTableRelation").form({
					onSubmit : function() {
						var flag = $(this).form('validate');
						if (flag == false) {
							return false;
						}
					},
					success : function(data) {
						//$.parseJSON()解析JSON字符串 
						var json = $.parseJSON(data);
						if (json.flag == true) {
							$.messager.alert("提醒", "添加成功!");
							$("#sysDataDictionary-dialog-tableRelationOper").dialog('close', true);
							$("#sysDataDictionary-table-showTableRelationList").datagrid('reload');
						} else {
							$.messager.alert("提醒", (json.msg || "添加失败！"));
						}
					}
		});
		$("#sysDataDictionary-save-addTableRelation").click(function() {
			$.messager.confirm("提醒", "是否添加表关联信息?", function(r) {
				if (r) {
					try{
				    	var tablename=$.trim($("#sysDataDictionary-form-addTableRelation :input[name='tableRelation.tablename']").val() || "");
				    	var columnname=$.trim($("#sysDataDictionary-form-addTableRelation :input[name='tableRelation.columnname']").val() || "");
				    	if(tablename==""){
				    		$.messager.alert("错误","请填写从属表名称");
				    		return false;
				    	}
				    	if(columnname==""){
				    		$.messager.alert("错误","请填写从属表字段名称");
				    		return false;
				    	}
				    	$.ajax({   
				            url :'sysDataDictionary/existsTableRelationBySubNames.do',
				            type:'post',
				            dataType:'json',
				            data:{
				            	'tablename':tablename,
				            	'columnname':columnname},
				            success:function(json){
				            	if(json.flag==true){
				            		$.messager.alert("提醒","抱歉，该从表名与从表字段名已经存在！");
				            		return false;
				            	}else{		            		
									$("#sysDataDictionary-form-addTableRelation").submit();
				            	}
				            }
				        });
			    		
			        }catch(e){
						$("#sysDataDictionary-form-addTableRelation").submit();
			        }
				}
			});
		});
	});
</script>
	</body>
</html>
