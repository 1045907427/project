<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>表描述导入管理</title>
	</head>
	<body>		
		<div class="easyui-layout" data-options="fit:true">
			<div title="" data-options="region:'north'" style="padding:5px;height:30px;">
				<span>以下为  ${tablename } 表中未曾导入过的表结构信息</span>
			</div>
			<div title="" data-options="region:'center'">
				<table id="sysDataDictionary-table-showTableColumnDBImportList"></table>
			</div>
			<div id="sysDataDictionary-query-showTableColumnDBImportList" data-options="region:'south'" style="height: 40px; ">
				<div class="buttonDivR">
					<a href="javaScript:void(0);" id="sysDataDictionary-reload-reloadTableColumnDBImportList" class="easyui-linkbutton" data-options="iconCls:'button-refresh'">刷新</a>
					<a href="javaScript:void(0);" id="sysDataDictionary-submit-importTableColumnList" class="easyui-linkbutton" data-options="iconCls:'button-import'">导入</a>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			$('#sysDataDictionary-table-showTableColumnDBImportList').datagrid({
					fit : true,
					method : 'post',
					rownumbers : true,
					idField : 'columnname',
					singleSelect : false,
					checkOnSelect:true,
  	 				selectOnCheck:true,
					url : 'sysDataDictionary/showTableColumnDBImportList.do?tablename=${tablename }',
					columns : [[ 
						{field : 'columnsCK',checkbox : true}, 
						{field : 'columnname',title : '字段名',width : 200},
						{field : 'colchnname',title : '字段描述名',width : 200},
						{field : 'coldatatype',title : '数据类型',width : 100},
						{field : 'colwidth',title : '字段长度',width : 100},
						{field : 'usepk',title : '是否主键',width : 100,
							formatter:function(val){
		  						if(val=='1'){
		  							return '是';
		  						}
		  						else
		  						{
		  							return '否';
		  						}
		  					}
		  				}
					]]
			});
			$(document).ready(function(){
				$("#sysDataDictionary-submit-importTableColumnList").click(function(){
					try{
						var sdata = [];  
					    var rows = $('#sysDataDictionary-table-showTableColumnDBImportList').datagrid('getChecked');
					    if(rows.length>0){					    	
			            	var tablename='${tablename }' ||"";
					    	if(tablename==""){
					    		$.message.alert("错误","未能找到表名，请刷新后再尝试！");
					    		return false;
					    	}
							$.messager.confirm("提醒", "是否导入 "+tablename+" 表结构?", function(r){
								if (r){
						            for(var i=0; i<rows.length; i++){  
						                var row = rows[i];
						                sdata.push(row.columnname);  
						            }
						            if(sdata.length>0){
							            $.ajax({   
								            url :'sysDataDictionary/addTableColumnDBImport.do',
								            type:'post',
								            dataType:'json',
								            data:{'tablename':tablename,'selectedcolumns':sdata.join(',')},
								            success:function(json){
								            	if(json.flag==true){
								            		$.messager.alert("提醒","表结构导入成功！");
								            		$('#sysDataDictionary-table-showTableColumnDBImportList').datagrid('reload');
								            		$('#${divid}').datagrid('reload');
								            	}else{
								            		$.messager.alert("提醒",(json.msg ||"表结构导入失败！"));
								            	}
								            }
								        });
							        }else{							        	
										$.messager.alert("提醒","请选择您要导入的表");
							        }
								}
							});
						}else{
							$.messager.alert("提醒","请选择您要导入的表");
						}
					}catch(e){
						$.messager.alert("异常","抱歉，系统异常，请重新打开");
					}
				});
				
				$("#sysDataDictionary-reload-reloadTableColumnDBImportList").click(function(){
					try{
						var tableInfoDbImportTable=$('#sysDataDictionary-table-showTableColumnDBImportList');
						var rows = tableInfoDbImportTable.datagrid('getSelections');
		        		tableInfoDbImportTable.datagrid('loadData', { 'total': 0, 'rows': [] });
			        	tableInfoDbImportTable.datagrid('uncheckAll');
			        	tableInfoDbImportTable.datagrid('checkAll');
						tableInfoDbImportTable.datagrid('reload');
					}catch(e){
					}
				});
			});
		</script>
	</body>
</html>
