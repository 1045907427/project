<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>调拨通知单</title>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="storage-form-allocateNoticeAdd" action="" method="post">
	    	<div data-options="region:'north',border:false" style="height: 135px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input class="len150 easyui-validatebox" name="allocateNotice.id" <c:if test="${autoCreate == true }">readonly='readonly'</c:if> <c:if test="${autoCreate == false }">required="required"</c:if> /></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input type="text" id="storage-allocateNotice-businessdate" class="len150" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${busdate }" name="allocateNotice.businessdate" /></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165"><select disabled="disabled" class="len150"><option>新增</option></select></td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">调出仓库：</td>
	    				<td>
	    					<input type="text" id="storage-allocateNotice-outstorageid" name="allocateNotice.outstorageid" class="len150" />
							<input type="hidden" id="storage-allocateNotice-outisaloneaccount"/>
	    				</td>
						<td class="len80 left">调入仓库:</td>
						<td>
							<input type="text" id="storage-allocateNotice-enterstorageid" name="allocateNotice.enterstorageid" class="len150"/>
							<input type="hidden" id="storage-allocateNotice-enterisaloneaccount"/>
						</td>
						<td class="len80 left" <c:if test="${isAllocateShowBilltype=='0'}">style="display: none;" </c:if>>调拨类型：</td>
						<td <c:if test="${isAllocateShowBilltype=='0'}">style="display: none"</c:if>>
							<select class="len150" id="storage-allocateNotice-billtype" >
								<option value="1" selected>成本调拨</option>
								<option value="2" >异价调拨</option>
							</select>
							<input type="hidden" id="storage-allocateNotice-billtype-hidden" name="allocateNotice.billtype" />
						</td>
	    			</tr>
					<tr>
						<td class="len80 left">调出部门：</td>
						<td>
							<input type="text" id="storage-allocateNotice-outdeptid" name="allocateNotice.outdeptid" class="len150" />
						</td>
						<td class="len80 left">调入部门:</td>
						<td>
							<input type="text" id="storage-allocateNotice-enterdeptid" name="allocateNotice.enterdeptid" class="len150"/>
						</td>
					</tr>
	    			<tr>
	    				<td class="len80 left">备注：</td>
	    				<td colspan="5" style="text-align: left">
	    					<input type="text" name="allocateNotice.remark" style="width: 680px;"/>
	    				</td>
	    			</tr>
	    			<tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<table id="storage-datagrid-allocateNoticeAddPage"></table>
	    	</div>
	    	<input type="hidden" id="storage-allocateNotice-allocateNoticeDetail" name="detailJson"/>
	    	<input type="hidden" id="storage-allocateNotice-saveaudit" name="saveaudit" value="save"/>
	    </form>
    </div>
    <div class="easyui-menu" id="storage-contextMenu-allocateNoticeAddPage" style="display: none;">
    	<div id="storage-addRow-allocateNoticeAddPage" data-options="iconCls:'button-add'">添加</div>
		<security:authorize url="/storage/showAllocateNoticeDetailAddByBrandAndSortPage.do">
			<div id="storage-addDetailByBrandAndSort-allocateNoticeAddPage" data-options="iconCls:'button-add'">批量添加商品</div>
		</security:authorize>
    	<div id="storage-editRow-allocateNoticeAddPage" data-options="iconCls:'button-edit'">编辑</div>
    	<div id="storage-removeRow-allocateNoticeAddPage" data-options="iconCls:'button-delete'">删除</div>
    </div>
    <div id="storage-dialog-allocateNoticeAddPage"></div>
    <script type="text/javascript">
    	$(function(){
			$("#storage-datagrid-allocateNoticeAddPage").datagrid({ //采购入库单明细信息编辑
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
    				$("#storage-datagrid-allocateNoticeAddPage").datagrid('selectRow', rowIndex);
                    $("#storage-contextMenu-allocateNoticeAddPage").menu('show', {  
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
    		$("#storage-allocateNotice-outstorageid").widget({
    			name:'t_storage_allocate_notice',
	    		width:150,
				col:'outstorageid',
				singleSelect:true,
				required:true,
				onSelect:function(data){
					$("#storage-allocateNotice-outisaloneaccount").val(data.isaloneaccount);
					$("#storage-allocateNotice-enterstorageid").widget({
		    			name:'t_storage_allocate_notice',
			    		width:150,
						col:'enterstorageid',
						singleSelect:true,
						required:true,
						onSelect:function(data){
							$("#storage-allocateNotice-enterisaloneaccount").val(data.isaloneaccount);
						},
						onClear:function(){
							$("#storage-allocateNotice-enterisaloneaccount").val('');
						}
		    		});
				}
    		});
			$("#storage-allocateNotice-outdeptid").widget({
				referwid:'RL_T_BASE_DEPATMENT',
				width:150,
				singleSelect:true
			})
			$("#storage-allocateNotice-enterdeptid").widget({
				referwid:'RL_T_BASE_DEPATMENT',
				width:150,
				singleSelect:true
			})
    		
    		//采购入库单明细添加
    		$("#storage-addRow-allocateNoticeAddPage").click(function(){
				var flag = $("#storage-contextMenu-allocateNoticeAddPage").menu('getItem','#storage-addRow-allocateNoticeAddPage').disabled;
				if(flag){
					return false;
				}
    			beginAddDetail();
    		});
            //采购入库单明细批量添加
            $("#storage-addDetailByBrandAndSort-allocateNoticeAddPage").click(function(){
                var flag = $("#storage-contextMenu-allocateNoticeAddPage").menu('getItem','#storage-addRow-allocateNoticeAddPage').disabled;
                if(flag){
                    return false;
                }
                beginAddDetailByBrandAndSort();
            });
    		//采购入库单明细修改
    		$("#storage-editRow-allocateNoticeAddPage").click(function(){
    			var flag = $("#storage-contextMenu-allocateNoticeAddPage").menu('getItem','#storage-editRow-allocateNoticeAddPage').disabled;
				if(flag){
					return false;
				}
    			beginEditDetail();
    		});
    		//采购入库单明细删除
    		$("#storage-removeRow-allocateNoticeAddPage").click(function(){
    			var flag = $("#storage-contextMenu-allocateNoticeAddPage").menu('getItem','#storage-removeRow-allocateNoticeAddPage').disabled;
				if(flag){
					return false;
				}
    			removeDetail();
    		});
    		$("#storage-form-allocateNoticeAdd").form({  
			    onSubmit: function(){
					var billtype=$("#storage-allocateNotice-billtype").val();
					$("#storage-allocateNotice-billtype-hidden").val(billtype);
			    	var json = $("#storage-datagrid-allocateNoticeAddPage").datagrid('getRows');
					$("#storage-allocateNotice-allocateNoticeDetail").val(JSON.stringify(json));
			    	var flag = $(this).form('validate');
			    	if(flag==false){
			    		return false;
			    	}
					if('2'==billtype){
						var outdeptid=$("#storage-allocateNotice-outdeptid").widget('getValue');
						var enterdeptid=$("#storage-allocateNotice-enterdeptid").widget('getValue');
						if(outdeptid==''){
							$.messager.alert("提醒","请选择调出部门.");
							$("#storage-allocateNotice-outdeptid").focus();
							return false;
						}
						if(enterdeptid==''){
							$.messager.alert("提醒","请选择调入部门.");
							$("#storage-allocateNotice-enterdeptid").focus();
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
			    			$.messager.alert("提醒","保存并审核成功：生成调拨单："+json.downid);
			    		}else{
			    			if(json.msg!=null){
			    				$.messager.alert("提醒","保存成功.<br/>"+json.msg);
			    			}else{
			    				$.messager.alert("提醒","保存成功.");
			    			}
			    		}
			    		$("#storage-buttons-allocateNoticePage").buttonWidget("addNewDataId", json.id);
			    		$("#storage-panel-allocateNoticePage").panel({
							href:"storage/allocateNoticeEditPage.do?id="+json.id,
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
    	$("#storage-buttons-allocateNoticePage").buttonWidget("initButtonType","add");
    	$("#storage-hidden-billid").val("");
    </script>
  </body>
</html>
