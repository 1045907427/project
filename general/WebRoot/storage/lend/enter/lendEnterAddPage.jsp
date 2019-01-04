<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>还货单新增页面</title>
  </head>  
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="storage-form-lendAdd"  method="post">
	    	<div data-options="region:'north',border:false" style="height:105px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input class="easyui-validatebox" style="width: 135px" name="lend.id" <c:if test="${autoCreate == true }">readonly='readonly'</c:if> <c:if test="${autoCreate == false }">required="required"</c:if> /></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input type="text" class="len130" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${busdate }" name="lend.businessdate" /></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165"><select disabled="disabled" class="len136"><option>新增</option></select></td>
	    			</tr>
	    			<tr>
						<td class="len80 left">还货人类型：</td>
						<td>
							<input type="text" id="storage-lend-lendtype" name="lend.lendtype"/>
						</td>
						<td class="len80 left">还货人：</td>
						<td colspan="3" id="lendp">
							<input id="storage-lend-lendid" name="lend.lendid" style="width: 400px"/>
						</td>
	    			</tr>
	    			<tr>
						<td class="len80 left">入货仓库：</td>
						<td>
							<input type="text" id="storage-lend-storageid" name="lend.storageid"/>
						</td>
						<td class="len80 left">相关部门：</td>
						<td>
							<input id="storage-lend-deptid" name="lend.deptid"/>
						</td>
						<td class="len80 left">备注：</td>
						<td style="text-align: left;"><input type="text" style="width: 136px" name="lend.remark"/></td>
	    			</tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<input type="hidden" name="detailJson" id="storage-lendEnter-lendDetail" />
	    		<input type="hidden" name="saveaudit" id="storage-purchaseEnter-saveaudit" value="save"/>
	    		<table id="storage-datagrid-lendAddPage"></table>
	    	</div>
	    </form>
    </div>
    <div class="easyui-menu" id="sales-contextMenu-lendAddPage" style="display: none;">
    	<div id="sales-addRow-lendAddPage" data-options="iconCls:'button-add'">添加</div>
    	<div id="sales-editRow-lendAddPage" data-options="iconCls:'button-edit'">编辑</div>
    	<div id="sales-removeRow-lendAddPage" data-options="iconCls:'button-delete'">删除</div>
    </div>
    <div id="storage-dialog-lendAddPage"></div>
    <script type="text/javascript">
    	$(function(){
    		$("#storage-datagrid-lendAddPage").datagrid({ //销售商品明细信息编辑
    			columns: tableColJson,
    			border: true,
    			fit: true,
    			rownumbers: true,
    			showFooter: true,
    			striped:true,
    			singleSelect: true,
    			data:{'total':10,'rows':[{},{},{},{},{},{},{},{},{},{}],'footer':[{goodsid:'合计'}]},
    			onRowContextMenu: function(e, rowIndex, rowData){
    				e.preventDefault();
    				$("#storage-datagrid-lendAddPage").datagrid('selectRow', rowIndex);
                    $("#sales-contextMenu-lendAddPage").menu('show', {
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
    			}
    		}).datagrid('columnMoving');
            //入库仓库
            $("#storage-lend-storageid").widget({
                width:136,
                referwid:'RL_T_BASE_STORAGE_INFO',
                singleSelect:true,
                onlyLeafCheck:false,
                required:true
            });

			//部门控件
			$("#storage-lend-deptid").widget({
				name: 't_storage_lend',
				col: 'deptid',
				singleSelect: true,
				isdatasql: false,
				width: 136,
				onlyLeafCheck: false
			});

			$('#storage-lend-lendtype').widget({
				width:136,
				name: 't_storage_lend',
				col: 'lendtype',
				singleSelect:true,
				onlyLeafCheck:false,
				onSelect : function(data){
					$("#lendp").empty();
					var tdstr = '<input type="text" id="storage-lend-lendid" name="lend.lendid" style="width: 400px"/>';
					$(tdstr).appendTo("#lendp");
					if (data.id == 1) {
						$("#storage-lend-lendid").widget({
							width: 400,
							referwid:'RL_T_BASE_BUY_SUPPLIER',
							singleSelect:true,
							onlyLeafCheck:false,
							required:true
						})
					} else if (data.id == 2) {
						$("#storage-lend-lendid").widget({
							width: 400,
							referwid:'RL_T_BASE_SALES_CUSTOMER_PARENT_2',
							singleSelect:true,
							onlyLeafCheck:false,
							required:true
						})
					}
				}
			});

			$("#storage-lend-lendid").widget({
				width: 400,
				referwid:'RL_T_BASE_BUY_SUPPLIER',
				singleSelect:true,
				onlyLeafCheck:false,
				required:true
			});

    		$("#sales-addRow-lendAddPage").click(function(){
    			beginAddDetail();
    		});
    		$("#sales-editRow-lendAddPage").click(function(){
    			beginEditDetail();
    		});
    		$("#sales-removeRow-lendAddPage").click(function(){
    			removeDetail();
    		});
    		$("#storage-form-lendAdd").form({
			    onSubmit: function(){  
			    	var json = $("#storage-datagrid-lendAddPage").datagrid('getRows');
					$("#storage-lendEnter-lendDetail").val(JSON.stringify(json));
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
			    		if(json.auditflag){
			    			$.messager.alert("提醒",json.msg);
			    		}else{
			    			$.messager.alert("提醒",json.msg);
			    		}
			    		$("#storage-buttons-lendPage").buttonWidget("addNewDataId", json.id);
			    		$("#storage-panel-lendPage").panel({
							href:"storage/lendEditPage.do?billtype=2&id="+json.id,
						    cache:false,
						    maximized:true,
						    border:false
						});
			    	}else{
			    		$.messager.alert("提醒","保存失败</br>"+json.msg);
			    	}
			    }  
			}); 
    		$("#storage-buttons-lendPage").buttonWidget("initButtonType", 'add');
    	});
    </script>
  </body>
</html>
