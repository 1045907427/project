<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    
    <title>用户选择页面</title>

  </head>  
  <body>
  	
		<div class="easyui-panel"  fit="true">
			<div class="easyui-layout" fit="true">
				<div title="" data-options="region:'north',split:false"
					style="height:64px;line-height:40px;padding:10px;">
					<label>用户姓名：</label><input type="text" name="username" style="width:250px;" />
					<a href="javaScript:void(0);" id="accesscontrol-userChooser-queay-userinfo" class="easyui-linkbutton" iconCls="icon-search">查询</a>
				</div>
				<div title="" data-options="region:'west',split:false" style="width:200px;">
					<div class="easyui-accordion">
						<div id="accesscontrol-userChooser-querypanel-bydep" title="按部门选择" data-options="iconCls:'icon-extend-depusr'" style="overflow:auto;">  
						</div>
						<div id="accesscontrol-userChooser-querypanel-byrole" title="角色选择" data-options="iconCls:'icon-extend-group16'" style="overflow:auto;">  
						</div>
						<div id="accesscontrol-userChooser-querypanel-online" title="在线人员" data-options="iconCls:'icon-extend-user16'" style="overflow:auto;">  
						</div>
					</div>
				</div>
				<div title="" data-options="region:'center',split:false" >
					<div class="easyui-layout" fit="true">				
						<div title="" data-options="region:'west',split:false" style="width:220px;">
							<table class="easyui-datagrid" data-options="fitColumns:true,singleSelect:true" id="accesscontrol-userChooser-table-showUserList"></table>
						</div>		
						<div title="" data-options="region:'center',split:false" style="width:55px; vertical-align: middle; position:relative; ">
							<div style="position: absolute;top:150px;">
							<button id="accesscontrol-userChooser-btn-addRows">加</button>
							<button id="accesscontrol-userChooser-btn-delRows">删</button>
							</div>
						</div>
						<div title="" data-options="region:'east',split:false" style="width:185px;">
							<table  class="easyui-datagrid" data-options="fitColumns:true,singleSelect:true" id="accesscontrol-userChooser-table-showUserChoosedList"></table>
						</div>
					</div> 
				</div>
				<div  title="" data-options="region:'south',split:false"
					style="height:35px;line-height:25px;padding:2px; text-align: right;">
					<input type="hidden" id="accesscontrol-userChooser-input-valueKeyId" value="${requestMap.valueKeyId }"/>
					<input type="hidden" id="accesscontrol-userChooser-input-nameKeyId" value="${requestMap.nameKeyId }"/>
					<input type="hidden" id="accesscontrol-userChooser-input-checkedData" value="${requestMap.checkedData }"/>
					<a href="javaScript:void(0);" id="accesscontrol-userChooser-btn-add-saveSelectedUser" class="easyui-linkbutton" data-options="iconCls:'button-save'">保存</a>
				</div>
			</div>
		</div>
		<script type="text/javascript">
			$(document).ready(function(){				
				$("#accesscontrol-userChooser-table-showUserList").datagrid( {
						title:'可选用户',
						fit : true,
			            striped: true,
			  	 		method:'post',
			  	 		rownumbers:true,
			  	 		pagination:true,
						singleSelect : false,
						url : 'common/getSysUserList.do',
						columns : [ [ 
							{field : 'userselects',checkbox : true}, 
							{field : 'name',title : '姓名',width : 150} 
						] ],
						onLoadSuccess:function(){
					    	var p = $('#accesscontrol-userChooser-table-showUserList').datagrid('getPager');  
						    $(p).pagination({  
						        pageSize: 10,//每页显示的记录条数，默认为10  
						        beforePageText: '',//页数文本框前显示的汉字  
						        afterPageText: '',  
						        showPageList:false,
						        displayMsg: ''
						    });
				    	}				
				});
				$("#accesscontrol-userChooser-table-showUserChoosedList").datagrid({
					title:'已选用户(双击删除)',
					fit : true,
		            striped: true,
		  	 		rownumbers:true,
					singleSelect : false,
					columns : [ [ 
						{field : 'userselects',checkbox : true}, 
						{field : 'name',title : '姓名',width : 120} 
					] ],
			    	onDblClickRow:function(rowIndex, rowData){
			    		var $showUserChoosedList=$("#accesscontrol-userChooser-table-showUserChoosedList");
			    		$showUserChoosedList.datagrid('deleteRow',rowIndex);
			    	}
				});
				$("#accesscontrol-userChooser-btn-addRows").click(function(){
					$showUserList=$('#accesscontrol-userChooser-table-showUserList');
					var srowsdata=$showUserList.datagrid('getChecked');
					var $showUserChoosedList=$("#accesscontrol-userChooser-table-showUserChoosedList");
					var drowsdata=$showUserChoosedList.datagrid('getRows');
					jQuery.extend(drowsdata,srowsdata);
					$showUserChoosedList.datagrid('loadData',drowsdata);
					$showUserChoosedList.datagrid('selectAll');
					$showUserList.datagrid('clearSelections');
				});
				$("#accesscontrol-userChooser-btn-delRows").click(function(){
					var $showUserChoosedList=$("#accesscontrol-userChooser-table-showUserChoosedList");
					var alldata=$showUserChoosedList.datagrid('getData');
					var checkdata=$showUserChoosedList.datagrid('getChecked');
					if(checkdata.length==0){
						$.messager.alert('提醒','您还未选中已选的用户');
					}else{
						 for(var i=0;i<alldata.rows.length;i++)
			            {
			               for(var j=0;j<checkdata.length;j++)
			               {
			                 if(alldata.rows[i].userid == checkdata[j].userid)
			                 {
			                    $showUserChoosedList.datagrid('deleteRow',i);
			                 }
			               }
			            }
					}
				});
				$("#accesscontrol-userChooser-btn-add-saveSelectedUser").click(function(){
					var valueKeyId=$("#accesscontrol-userChooser-input-valueKeyId").val()||"";
					var nameKeyId=$("#accesscontrol-userChooser-input-nameKeyId").val()||"";
					if(valueKeyId==""){
						$.messager.alert('提醒','未找到相关参数，请重新打开');
						return false;
					}
					var datarows=$("#accesscontrol-userChooser-table-showUserChoosedList").datagrid('getChecked');
					if(datarows.length==0){
						$.messager.alert('提醒','您还未选择用户');
						return false;
					}
					var idarr=[];
					var namearr=[];
					for(var i=0;i<datarows.length;i++){
						idarr.push(datarows[i].userid);
						namearr.push(datarows[i].name);
					}
					$("#"+valueKeyId).val(idarr.join(','));
					$("#"+nameKeyId).val(namearr.join(','));
						
					$.messager.alert('提醒','保存成功！');			
				});
			});
		</script>
  </body>
</html>
