<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>表描述导入管理</title>
	</head>
	<body>		
		<div class="easyui-layout" data-options="fit:true">
			<div title="" data-options="region:'north'" style="padding:5px;height:30px;">
				<span>以下列表未曾导入过的表描述</span>
			</div>
			<div title="" data-options="region:'center'">
				<table id="sysDataDictionary-table-showTableInfoDBImportList"></table>
			</div>
			<div id="sysDataDictionary-query-showTableInfoDBImportList" data-options="region:'south'" style="height: 40px; ">
				<div class="buttonDivR">
					<a href="javaScript:void(0);" id="sysDataDictionary-reload-reloadTableInfoDBImportList"
						class="easyui-linkbutton" data-options="iconCls:'button-refresh'">刷新</a>
					<a href="javaScript:void(0);" id="sysDataDictionary-submit-importTableInfoList"
						class="easyui-linkbutton" data-options="iconCls:'button-import'">导入</a>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			$('#sysDataDictionary-table-showTableInfoDBImportList').datagrid( {
				fit : true,
				method : 'post',
				rownumbers : true,
				idField : 'tablename',
				singleSelect : false,
				checkOnSelect:true,
  	 			selectOnCheck:true,
				url : 'sysDataDictionary/showTableInfoDBImportList.do',
				columns : [ [ 
					{field : 'tableimports',checkbox : true}, 
					{field : 'tablename',title : '表名',width : 200} 
				] ]
			});
			$(document).ready(function(){
				$("#sysDataDictionary-submit-importTableInfoList").click(function(){
					try{
						var sdata = [];  
					    var rows = $('#sysDataDictionary-table-showTableInfoDBImportList').datagrid('getChecked');
					    if(rows.length>0){
							$.messager.confirm("提醒", "是否导入表描述?", function(r){
								if (r){
						            for(var i=0; i<rows.length; i++){  
						                var row = rows[i];
						                sdata.push(row.tablename);  
						            }
						            if(sdata.length>0){
							            $.ajax({   
								            url :'sysDataDictionary/addTableInfoDBImport.do',
								            type:'post',
								            dataType:'json',
								            data:{'selectedtables':sdata.join(',')},
								            beforeSend:function(XHR){
							    				loading("数据正在导入中，请稍候..");
								            },
								            success:function(json){
							    				loaded();
								            	if(json.flag==true){
								            		$.messager.alert("提醒","描述导入成功！");
								            		$('#sysDataDictionary-table-showTableInfoDBImportList').datagrid('reload');
								            		$('#sysDataDictionary-table-showTableInfoList').datagrid('reload');
								            	}else{
								            		$.messager.alert("提醒","描述导入失败！");
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
						$.messager.alert("异常","抱歉，系统异常，请重新打开"+e);
					}
				});
				
				$("#sysDataDictionary-reload-reloadTableInfoDBImportList").click(function(){
					try{
						var tableInfoDbImportTable=$('#sysDataDictionary-table-showTableInfoDBImportList');
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
