<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>调拨入库单</title>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="storage-form-allocateEnterAdd" action="" method="post">
	    	<div data-options="region:'north',border:false" style="height: 100px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input class="len130 easyui-validatebox" name="allocateEnter.id" <c:if test="${autoCreate == true }">readonly='readonly'</c:if> <c:if test="${autoCreate == false }">required="required"</c:if> /></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input type="text" id="storage-allocateEnter-businessdate" class="len130" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${busdate }" name="allocateEnter.businessdate" /></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165"><select disabled="disabled" class="len136"><option>新增</option></select></td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">调入仓库:</td>
	    				<td>
	    					<input type="text" id="storage-allocateEnter-enterstorageid" name="allocateEnter.enterstorageid" class="len130"/>
	    				</td>
	    				<td class="len80 left">调出仓库：</td>
	    				<td>
	    					<input type="text" id="storage-allocateEnter-outstorageid" name="allocateEnter.outstorageid" class="len130" />
	    				</td>
	    				<td class="len80 left">来源类型：</td>
	    				<td>
	    					<select class="len130" disabled="disabled">
	    						<option value="0">无</option>
	    					</select>
	    					<input type="hidden" name="allocateEnter.sourcetype" value="0"/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">备注：</td>
	    				<td colspan="5" style="text-align: left">
	    					<input type="text" name="allocateEnter.remark" style="width: 500px;"/>
	    				</td>
	    			</tr>
	    			<tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<table id="storage-datagrid-allocateEnterAddPage"></table>
	    	</div>
	    	<input type="hidden" id="storage-allocateEnter-allocateEnterDetail" name="detailJson"/>
	    </form>
    </div>
    <div class="easyui-menu" id="storage-contextMenu-allocateEnterAddPage" style="display: none;">
    	<div id="storage-addRow-allocateEnterAddPage" data-options="iconCls:'button-add'">添加</div>
    	<div id="storage-editRow-allocateEnterAddPage" data-options="iconCls:'button-edit'">编辑</div>
    	<div id="storage-removeRow-allocateEnterAddPage" data-options="iconCls:'button-delete'">删除</div>
    </div>
    <div id="storage-dialog-allocateEnterAddPage"></div>
    <script type="text/javascript">
    	$(function(){
			$("#storage-datagrid-allocateEnterAddPage").datagrid({ //采购入库单明细信息编辑
    			authority:tableColJson,
    			columns: tableColJson.common,
    			frozenColumns: tableColJson.frozen,
    			border: true,
    			rownumbers: true,
    			showFooter: true,
    			striped:true,
    			fit:true,
    			singleSelect: true,
    			data:{'total':10,'rows':[{},{},{},{},{},{},{},{},{},{}],'footer':[{goodsid:'合计'}]},
    			onRowContextMenu: function(e, rowIndex, rowData){
    				e.preventDefault();
    				$("#storage-datagrid-allocateEnterAddPage").datagrid('selectRow', rowIndex);
                    $("#storage-contextMenu-allocateEnterAddPage").menu('show', {  
                        left:e.pageX,  
                        top:e.pageY  
                    });
    			},
    			onDblClickRow: function(rowIndex, rowData){
    				if(rowData.goodsid == undefined){
    					beginAddDetail();
    				}
    				else{
    					beginEditDetail();
    				}
    			},
    			onLoadSuccess:function(){
    				countTotal();
    			}
    		}).datagrid('columnMoving');    		
    		$("#storage-allocateEnter-outstorageid").widget({
    			name:'t_storage_allocate_out',
	    		width:130,
				col:'outstorageid',
				singleSelect:true,
				required:true,
				onSelect:function(data){
					$("#storage-allocateEnter-enterstorageid").widget({
		    			name:'t_storage_allocate_out',
			    		width:130,
						col:'enterstorageid',
						singleSelect:true,
						param:[
							{field:'id',op:'notin',value:data.id},
						]
		    		});
				}
    		});
    		
    		//采购入库单明细添加
    		$("#storage-addRow-allocateEnterAddPage").click(function(){
				var flag = $("#storage-contextMenu-allocateEnterAddPage").menu('getItem','#storage-addRow-allocateEnterAddPage').disabled;
				if(flag){
					return false;
				}
    			beginAddDetail();
    		});
    		//采购入库单明细修改
    		$("#storage-editRow-allocateEnterAddPage").click(function(){
    			var flag = $("#storage-contextMenu-allocateEnterAddPage").menu('getItem','#storage-editRow-allocateEnterAddPage').disabled;
				if(flag){
					return false;
				}
    			beginEditDetail();
    		});
    		//采购入库单明细删除
    		$("#storage-removeRow-allocateEnterAddPage").click(function(){
    			var flag = $("#storage-contextMenu-allocateEnterAddPage").menu('getItem','#storage-removeRow-allocateEnterAddPage').disabled;
				if(flag){
					return false;
				}
    			removeDetail();
    		});
    		$("#storage-form-allocateEnterAdd").form({  
			    onSubmit: function(){  
			    	var json = $("#storage-datagrid-allocateEnterAddPage").datagrid('getRows');
					$("#storage-allocateEnter-allocateEnterDetail").val(JSON.stringify(json));
			    	var flag = $(this).form('validate');
			    	if(flag==false){
			    		return false;
			    	}
			    	loading("提交中..");
			    },  
			    success:function(data){
			    	//表单提交完成后 隐藏提交等待页面
			    	loaded();
			    	var json = $.parseJSON(data);
			    	if(json.flag){
			    		$.messager.alert("提醒","保存成功");
			    		$("#storage-buttons-allocateEnterPage").buttonWidget("addNewDataId", json.id);
			    		$("#storage-panel-allocateEnterPage").panel({
							href:"storage/allocateEnterViewPage.do?id="+json.id,
							title:'调拨入库单查看',
						    cache:false,
						    maximized:true,
						    border:false
						});
			    	}else{
			    		$.messager.alert("提醒","保存失败");
			    	}
			    }  
			}); 
    	});
    	
    	//控制按钮状态
    	$("#storage-buttons-allocateEnterPage").buttonWidget("initButtonType","add");
    	$("#storage-hidden-billid").val("");
    </script>
  </body>
</html>
