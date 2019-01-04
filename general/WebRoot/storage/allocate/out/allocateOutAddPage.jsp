<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>调拨单</title>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="storage-form-allocateOutAdd" action="" method="post">
	    	<div data-options="region:'north',border:false" style="height: 140px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input class="easyui-validatebox" style="width: 160px" name="allocateOut.id" <c:if test="${autoCreate == true }">readonly='readonly'</c:if> <c:if test="${autoCreate == false }">required="required"</c:if> /></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input type="text" id="storage-allocateOut-businessdate" class="len130" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${busdate }" name="allocateOut.businessdate" /></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165"><select disabled="disabled" class="len136"><option>新增</option></select></td>
	    			</tr>
					<tr>
						<td class="len80 left">调出仓库：</td>
						<td>
							<input type="text" id="storage-allocateOut-outstorageid" name="allocateOut.outstorageid" class="len136" />
							<input type="hidden" id="storage-allocateOut-outisaloneaccount"/>
						</td>
						<td class="len80 left">调入仓库:</td>
						<td>
							<input type="text" id="storage-allocateOut-enterstorageid" name="allocateOut.enterstorageid" class="len130"/>
							<input type="hidden" id="storage-allocateOut-enterisaloneaccount"/>
						</td>
						<td class="len80 left">来源类型：</td>
						<td>
							<select style="width: 136px" id="storage-allocateOut-sourcetype" disabled="disabled">
								<option value="0">无</option>
							</select>
							<input type="hidden" name="allocateOut.sourcetype" value="0"/>
						</td>
					</tr>
					<tr>
						<td class="len80 left">调出部门：</td>
						<td>
							<input type="text" id="storage-allocateOut-outdeptid" name="allocateOut.outdeptid" class="len150" />
						</td>

						<td class="len80 left">调入部门:</td>
						<td>
							<input type="text" id="storage-allocateOut-enterdeptid" name="allocateOut.enterdeptid" class="len136"/>
						</td>
						<td class="len80 left" <c:if test="${isAllocateShowBilltype=='0'}">style="display: none;" </c:if>>调拨类型：</td>
						<td <c:if test="${isAllocateShowBilltype=='0'}">style="display: none;" </c:if>>
							<select style="width: 136px" id="storage-allocateOut-billtype" >
								<option value="1" selected>成本调拨</option>
								<option value="2" >异价调拨</option>
							</select>
							<input type="hidden" id="storage-allocateOut-billtype-hidden" name="allocateOut.billtype" />
						</td>
					</tr>
	    			<tr>
	    				<td class="len80 left">备注：</td>
	    				<td colspan="5" style="text-align: left">
	    					<input type="text" name="allocateOut.remark" style="width: 677px;"/>
	    				</td>
	    			</tr>
	    			<tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<table id="storage-datagrid-allocateOutAddPage"></table>
	    	</div>
	    	<input type="hidden" id="storage-allocateOut-allocateOutDetail" name="detailJson"/>
	    	<input type="hidden" id="storage-allocateOut-saveaudit" name="saveaudit" value="save"/>
	    </form>
    </div>
    <div class="easyui-menu" id="storage-contextMenu-allocateOutAddPage" style="display: none;">
    	<div id="storage-addRow-allocateOutAddPage" data-options="iconCls:'button-add'">添加</div>
    	<div id="storage-editRow-allocateOutAddPage" data-options="iconCls:'button-edit'">编辑</div>
    	<div id="storage-removeRow-allocateOutAddPage" data-options="iconCls:'button-delete'">删除</div>
    </div>
    <div id="storage-dialog-allocateOutAddPage"></div>
    <script type="text/javascript">
    	$(function(){
			$("#storage-datagrid-allocateOutAddPage").datagrid({ //采购入库单明细信息编辑
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
    				$("#storage-datagrid-allocateOutAddPage").datagrid('selectRow', rowIndex);
                    $("#storage-contextMenu-allocateOutAddPage").menu('show', {  
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
    		$("#storage-allocateOut-outstorageid").widget({
    			name:'t_storage_allocate_out',
	    		width:160,
				col:'outstorageid',
				singleSelect:true,
				required:true,
				onSelect:function(data){
					$("#storage-allocateOut-outisaloneaccount").val(data.isaloneaccount);
					$("#storage-allocateOut-enterstorageid").widget({
		    			name:'t_storage_allocate_out',
			    		width:130,
						col:'enterstorageid',
						singleSelect:true,
						required:true,
						onSelect:function(data){
							$("#storage-allocateOut-enterisaloneaccount").val(data.isaloneaccount);
						},
						onClear:function(){
							$("#storage-allocateOut-enterisaloneaccount").val('');
						}
		    		});
				},
				onClear:function(){
					$("#storage-allocateOut-outisaloneaccount").val('');
				}
    		});

			$("#storage-allocateOut-outdeptid").widget({
				referwid:'RL_T_BASE_DEPATMENT',
				width:160,
				singleSelect:true
			})
			$("#storage-allocateOut-enterdeptid").widget({
				referwid:'RL_T_BASE_DEPATMENT',
				width:130,
				singleSelect:true
			})
    		
    		//采购入库单明细添加
    		$("#storage-addRow-allocateOutAddPage").click(function(){
				var flag = $("#storage-contextMenu-allocateOutAddPage").menu('getItem','#storage-addRow-allocateOutAddPage').disabled;
				if(flag){
					return false;
				}
    			beginAddDetail();
    		});
    		//采购入库单明细修改
    		$("#storage-editRow-allocateOutAddPage").click(function(){
    			var flag = $("#storage-contextMenu-allocateOutAddPage").menu('getItem','#storage-editRow-allocateOutAddPage').disabled;
				if(flag){
					return false;
				}
    			beginEditDetail();
    		});
    		//采购入库单明细删除
    		$("#storage-removeRow-allocateOutAddPage").click(function(){
    			var flag = $("#storage-contextMenu-allocateOutAddPage").menu('getItem','#storage-removeRow-allocateOutAddPage').disabled;
				if(flag){
					return false;
				}
    			removeDetail();
    		});
    		$("#storage-form-allocateOutAdd").form({  
			    onSubmit: function(){
					var billtype=$("#storage-allocateOut-billtype").val();
					$("#storage-allocateOut-billtype-hidden").val(billtype);
			    	var json = $("#storage-datagrid-allocateOutAddPage").datagrid('getRows');
					$("#storage-allocateOut-allocateOutDetail").val(JSON.stringify(json));
			    	var flag = $(this).form('validate');
			    	if(flag==false){
			    		return false;
			    	}
					if('2'==billtype){
						var outdeptid=$("#storage-allocateOut-outdeptid").widget('getValue');
						var enterdeptid=$("#storage-allocateOut-enterdeptid").widget('getValue');
						if(outdeptid==''){
							$.messager.alert("提醒","请选择出库部门.");
							$("#storage-allocateOut-outdeptid").focus();
							return false;
						}
						if(enterdeptid==''){
							$.messager.alert("提醒","请选择调入部门.");
							$("#storage-allocateOut-enterdeptid").focus();
							return false;
						}
					}
			    	loading("提交中..");
			    },  
			    success:function(data){
			    	//表单提交完成后 隐藏提交等待页面
			    	loaded();
			    	var json = $.parseJSON(data);
			    	if(json.flag){
			    		if(json.auditflag){
			    			$.messager.alert("提醒","保存并审核成功");
			    		}else{
			    			$.messager.alert("提醒","保存成功"+json.msg);
			    		}
			    		$("#storage-buttons-allocateOutPage").buttonWidget("addNewDataId", json.id);
			    		$("#storage-panel-allocateOutPage").panel({
							href:"storage/allocateOutEditPage.do?id="+json.id,
						    cache:false,
						    maximized:true,
						    border:false
						});
			    	}else{
			    		$.messager.alert("提醒","保存失败."+json.msg);
			    	}
			    }  
			}); 
    	});
    	
    	//控制按钮状态
    	$("#storage-buttons-allocateOutPage").buttonWidget("initButtonType","add");
    	$("#storage-buttons-allocateOutPage").buttonWidget("disableButton","button-print");
    	$("#storage-buttons-allocateOutPage").buttonWidget("disableButton","button-preview");
    	$("#storage-hidden-billid").val("");


		$("#storage-buttons-allocateOutPage").buttonWidget("disableMenuItem","storage-out-audit");
		$("#storage-buttons-allocateOutPage").buttonWidget("disableMenuItem","storage-out-oppaudit");
		$("#storage-buttons-allocateOutPage").buttonWidget("disableMenuItem","storage-enter-audit");
    </script>
  </body>
</html>
