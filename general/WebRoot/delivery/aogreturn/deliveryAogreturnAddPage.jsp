<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>新增代配送采购退单</title>
</head>
<body>
    
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="delivery-form-deliveryAogreturnAddPage" action="" method="post">
	    	<div data-options="region:'north',border:false" style="height: 100px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input id="delivery-deliveryAogreturn-id" class="easyui-validatebox" name="deliveryAogreturn.id" <c:if test="${autoCreate == true }">readonly='readonly'</c:if> <c:if test="${autoCreate == false }">required="required"</c:if> style="width:160px;"/></td>
	    				<td class="len80 right">业务日期：</td>
	    				<td class="len165"><input type="text" id="delivery-deliveryAogreturn-businessdate" style="width:160px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${busdate }" name="deliveryAogreturn.businessdate" /></td>
	    				<td class="len80 right">状态：</td>
	    				<td class="len165"><select id="delivery-deliveryAogreturn-status-select" disabled="disabled" style="width:160px;">
    	                        <option ></option>
    	                        <option value="0" selected="selected">新增</option>
    	                        <option value="2">保存</option>
    	                        <option value="3">审核通过</option>
    	                        <option value="4">关闭</option>
    	                    </select></td>
	    			</tr>
	    			<tr>
	    			    <td>供应商：</td>
	    				<td colspan="3" style="text-align: left">
	    					<input type="text" id="delivery-deliveryAogreturn-supplierid" name="deliveryAogreturn.supplierid" style="width:320px;" />
	    				    <span id="delivery-deliveryAogreturn-supplier" style="margin-left:5px;line-height:25px;"></span>
	    				</td>
	    				<td class="len80 right">退货仓库：</td>
	    				<td class="len165">
	    					<input type="text" id="delivery-deliveryAogreturn-storageid" name="deliveryAogreturn.storageid" style="width:160px;"/>
	    				</td>
	    		    <tr>
	    				<td class="len80 left">备注：</td>
	    				<td colspan="5" style="text-align: left">
	    					<input type="text" id="delivery-deliveryAogreturn-remark" name="deliveryAogreturn.remark" style="width: 690px;" onfocus="frm_focus('deliveryAogreturn.remark');" onblur="frm_blur('deliveryAogreturn.remark');"/>
	    				</td>
	    			</tr>
	    			<tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<table id="delivery-datagrid-deliveryAogreturnAddPage"></table>
	    	</div>
	    	<input type="hidden" id="delivery-deliveryAogreturn-deliveryAogreturnDetail" name="detailJson"/>
	    	<input type="hidden" id="delivery-deliveryAogreturn-saveaudit" name="saveaudit" value="save"/>
	    </form>
    </div>
    <div class="easyui-menu" id="delivery-contextMenu-deliveryAogreturnAddPage" style="display: none;">
    	<div id="delivery-addRow-deliveryAogreturnAddPage" data-options="iconCls:'button-add'">添加</div>
    	<div id="delivery-editRow-deliveryAogreturnAddPage" data-options="iconCls:'button-edit'">编辑</div>
    	<div id="delivery-removeRow-deliveryAogreturnAddPage" data-options="iconCls:'button-delete'">删除</div>
    </div>
    <div id="delivery-dialog-deliveryAogreturnAddPage"></div>
    <script type="text/javascript">
    	$(function(){
    		$("#delivery-buttons-deliveryAogreturnPage").buttonWidget("initButtonType", 'add');
    		
    		$("#delivery-datagrid-deliveryAogreturnAddPage").datagrid({
    			authority:tableColJson,
    			columns: tableColJson.common,
    			frozenColumns: tableColJson.frozen,
    			border: true,
    			rownumbers: true,
    			showFooter: true,
    			striped:true,
    			fit:true,
    			singleSelect: false,
    			checkOnSelect:true,
    	 		selectOnCheck:true,
    			onRowContextMenu: function(e, rowIndex, rowData){
    				e.preventDefault();
    				$("#delivery-datagrid-deliveryAogreturnAddPage").datagrid('selectRow', rowIndex);
                    $("#delivery-contextMenu-deliveryAogreturnAddPage").menu('show', {  
                        left:e.pageX,  
                        top:e.pageY  
                    });
    			},
    			onSortColumn:function(sort, order){
    				var rows = $("#delivery-datagrid-deliveryAogreturnAddPage").datagrid("getRows");
    				var dataArr = [];
    				for(var i=0;i<rows.length;i++){
    					if(rows[i].goodsid!=null && rows[i].goodsid!=""){
    						dataArr.push(rows[i]);
    					}
    				}
    				dataArr.sort(function(a,b){
    					if(order=="asc"){
    						return a[sort]>b[sort]?1:-1
    					}else{
    						return a[sort]<b[sort]?1:-1
    					}
    				});
    				$("#delivery-datagrid-deliveryAogreturnAddPage").datagrid("loadData",dataArr);
    				return false;
    			},
    			onLoadSuccess: function(data){
    				if(data.rows.length<12){
    					var j = 12-data.rows.length;
	            		for(var i=0;i<j;i++){
	            			$(this).datagrid('appendRow',{});
	            		}
    				}else{
    					$(this).datagrid('appendRow',{});
    				}
    				countTotal();
    			},
    			onCheckAll:function(){
					countTotal();
				},
				onUncheckAll:function(){
					countTotal();
				},
				onCheck:function(){
					countTotal();
				},
				onUncheck:function(){
					countTotal();
				},
    			onDblClickRow: function(rowIndex, rowData){
    				$(this).datagrid('clearSelections').datagrid('selectRow',rowIndex);
    				if(rowData.goodsid == undefined){
    					beginAddDetail();
    				}
    				else{
    					beginEditDetail();
    				}
    			},
    			
    		}).datagrid("loadData", {'total':12,'rows':[{},{},{},{},{},{},{},{},{},{},{},{} ],'footer':[{goodsid:'合计'}]}).datagrid('columnMoving'); 
    		//供应商选择
    		$("#delivery-deliveryAogreturn-supplierid").supplierWidget({
    			onSelect:function(data){
    				$("#delivery-deliveryAogreturn-storageid").widget('setValue',data.storageid);
    				$("#delivery-deliveryAogreturn-remark").focus();
    				$("#delivery-deliveryAogreturn-supplier").html("  编号:" + data.id);
    			}
    		});
    		//仓库选择
    		$("#delivery-deliveryAogreturn-storageid").widget({
    			referwid:'RL_T_BASE_STORAGE_INFO',
	    		width:160,
				col:'name',
				singleSelect:true,
				required:true,
				initValue:''
    		});
    		//代配送采购退单明细添加
    		$("#delivery-addRow-deliveryAogreturnAddPage").click(function(){
				var flag = $("#delivery-addRow-deliveryAogreturnAddPage").menu('getItem','#delivery-addRow-deliveryAogreturnAddPage').disabled;
				if(flag){
					return false;
				}
    			beginAddDetail();
    		});
    		//代配送采购退单明细修改
    		$("#delivery-editRow-deliveryAogreturnAddPage").click(function(){
    			var flag = $("#delivery-contextMenu-deliveryAogreturnAddPage").menu('getItem','#delivery-editRow-deliveryAogreturnAddPage').disabled;
				if(flag){
					return false;
				}
    			beginEditDetail();
    		});
    		//代配送采购退单明细删除
    		$("#delivery-removeRow-deliveryAogreturnAddPage").click(function(){
    			var flag = $("#delivery-contextMenu-deliveryAogreturnAddPage").menu('getItem','#delivery-removeRow-deliveryAogreturnAddPage').disabled;
				if(flag){
					return false;
				}
    			removeDetail();
    		});
    		//订单提交
    		$("#delivery-form-deliveryAogreturnAddPage").form({  
			    onSubmit: function(){  
			    	var json = $("#delivery-datagrid-deliveryAogreturnAddPage").datagrid('getRows');
		
					$("#delivery-deliveryAogreturn-deliveryAogreturnDetail").val(JSON.stringify(json));
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
			    	$("#delivery-deliveryAogreturn-id").val(json.id);
			    	if(json.flag){
			    		if(json.auditflag){
			    			$.messager.alert("提醒","保存并审核成功");
			    			$("#delivery-deliveryAogreturn-status-select").val("3");
			    			var d = new Date();
							var str = d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
							$("#delivery-deliveryAogreturn-businessdate").val(str);
			    			$("#delivery-buttons-deliveryAogreturnPage").buttonWidget("setDataID",{id:'${deliveryAogreturn.id}',state:'3',type:'view'});
			    		}
			    		else if(json.auditflag==null){
			    			$("#delivery-deliveryAogreturn-status-select").val("2");
			    			$.messager.alert("提醒","保存成功");
			    			$("#delivery-buttons-deliveryAogreturnPage").buttonWidget("setDataID",{id:'${deliveryAogreturn.id}',state:'2',type:'edit'});
			    		}
			    		else{
			    			$("#delivery-deliveryAogreturn-status-select").val("2");
			    			$.messager.alert("提醒","保存成功,审核失败."+json.msg);
			    			$("#delivery-buttons-deliveryAogreturnPage").buttonWidget("setDataID",{id:'${deliveryAogreturn.id}',state:'2',type:'edit'});
			    		}
			    		$("#delivery-buttons-deliveryAogreturnPage").buttonWidget("addNewDataId", json.id);
			    		
			    	}else{
			    		$.messager.alert("提醒","保存失败."+json.msg);
			    	}
			    }  
			}); 
    	});
    </script>
</body>

</html>