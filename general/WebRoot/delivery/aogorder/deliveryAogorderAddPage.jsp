<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>新增代配送采购单</title>
</head>
<body>
    
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="delivery-form-deliveryAogorderAddPage" action="" method="post">
	    	<div data-options="region:'north',border:false" style="height: 100px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input id="delivery-deliveryAogorder-id" class="easyui-validatebox" name="deliveryAogorder.id" <c:if test="${autoCreate == true }">readonly='readonly'</c:if> <c:if test="${autoCreate == false }">required="required"</c:if> style="width:160px;"/></td>
	    				<td class="len80 right">业务日期：</td>
	    				<td class="len165"><input type="text" id="delivery-deliveryAogorder-businessdate"  onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${busdate }" name="deliveryAogorder.businessdate" style="width:160px;"/></td>
	    				<td class="len80 right">状态：</td>
	    				<td class="len165"><select id="delivery-deliveryAogorder-status-select" disabled="disabled" style="width:160px;">
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
	    					<input type="text" id="delivery-deliveryAogorder-supplierid" name="deliveryAogorder.supplierid" style="width:320px;" />
	    					<span id="delivery-deliveryAogorder-supplier" style="margin-left:5px;line-height:25px;"></span>
	    				</td>
	    				<td class="len80 right">到货仓库：</td>
	    				<td class="len165">
	    					<input type="text" id="delivery-deliveryAogorder-storageid"  name="deliveryAogorder.storageid" style="width:160px;"/>
	    				</td>
	    		    <tr>
	    				<td class="len80 left">备注：</td>
	    				<td colspan="5" style="text-align: left">
	    					<input type="text"  id="delivery-deliveryAogorder-remark" name="deliveryAogorder.remark" style="width: 690px;" onfocus="frm_focus('deliveryAogorder.remark');" onblur="frm_blur('deliveryAogorder.remark');"/>
	    				</td>
	    			</tr>
	    			<tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<table id="delivery-datagrid-deliveryAogorderAddPage"></table>
	    	</div>
	    	<input type="hidden" id="delivery-deliveryAogorder-deliveryAogorderDetail" name="detailJson"/>
	    	<input type="hidden" id="delivery-deliveryAogorder-saveaudit" name="saveaudit" value="save"/>
	    </form>
    </div>
    <div class="easyui-menu" id="delivery-contextMenu-deliveryAogorderAddPage" style="display: none;">
    	<div id="delivery-addRow-deliveryAogorderAddPage" data-options="iconCls:'button-add'">添加</div>
    	<div id="delivery-editRow-deliveryAogorderAddPage" data-options="iconCls:'button-edit'">编辑</div>
    	<div id="delivery-removeRow-deliveryAogorderAddPage" data-options="iconCls:'button-delete'">删除</div>
    </div>
    <div id="delivery-dialog-deliveryAogorderAddPage"></div>
    <script type="text/javascript">
        $("#delivery-deliveryAogorder-supplierid").focus();
    	$(function(){
    		
    		$("#delivery-buttons-deliveryAogorderPage").buttonWidget("initButtonType", 'add');
    		
    		$("#delivery-datagrid-deliveryAogorderAddPage").datagrid({
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
    				$("#delivery-datagrid-deliveryAogorderAddPage").datagrid('selectRow', rowIndex);
                    $("#delivery-contextMenu-deliveryAogorderAddPage").menu('show', {  
                        left:e.pageX,  
                        top:e.pageY  
                    });
    			},
    			onSortColumn:function(sort, order){
    				var rows = $("#delivery-datagrid-deliveryAogorderAddPage").datagrid("getRows");
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
    				$("#delivery-datagrid-deliveryAogorderAddPage").datagrid("loadData",dataArr);
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
    		$("#delivery-deliveryAogorder-supplierid").supplierWidget({
    			onSelect:function(data){
    				$("#delivery-deliveryAogorder-storageid").widget('setValue',data.storageid);
    				$("#delivery-deliveryAogorder-remark").focus();
    				$("#delivery-deliveryAogorder-supplier").html("  编号:" + data.id);
    			}
    		 
    		});
    		//仓库选择
    		$("#delivery-deliveryAogorder-storageid").widget({
    			referwid:'RL_T_BASE_STORAGE_INFO',
	    		width:160,
				col:'name',
				singleSelect:true,
				required:true,
				initValue:''
    		});
    		//代配送采购单明细添加
    		$("#delivery-addRow-deliveryAogorderAddPage").click(function(){
				var flag = $("#delivery-addRow-deliveryAogorderAddPage").menu('getItem','#delivery-addRow-deliveryAogorderAddPage').disabled;
				if(flag){
					return false;
				}
    			beginAddDetail();
    		});
    		//代配送采购单明细修改
    		$("#delivery-editRow-deliveryAogorderAddPage").click(function(){
    			var flag = $("#delivery-contextMenu-deliveryAogorderAddPage").menu('getItem','#delivery-editRow-deliveryAogorderAddPage').disabled;
				if(flag){
					return false;
				}
    			beginEditDetail();
    		});
    		//代配送采购单明细删除
    		$("#delivery-removeRow-deliveryAogorderAddPage").click(function(){
    			var flag = $("#delivery-contextMenu-deliveryAogorderAddPage").menu('getItem','#delivery-removeRow-deliveryAogorderAddPage').disabled;
				if(flag){
					return false;
				}
    			removeDetail();
    		});
    		//订单提交
    		$("#delivery-form-deliveryAogorderAddPage").form({  
			    onSubmit: function(){  
			    	var json = $("#delivery-datagrid-deliveryAogorderAddPage").datagrid('getRows');
		
					$("#delivery-deliveryAogorder-deliveryAogorderDetail").val(JSON.stringify(json));
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
			    		$("#delivery-deliveryAogorder-id").val(json.id);
			    		if(json.auditflag){
			    			$("#delivery-deliveryAogorder-status-select").val("3");
			    			var d = new Date();
							var str = d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
							$("#delivery-deliveryAogorder-businessdate").val(str);
			    			$.messager.alert("提醒","保存并审核成功");
			    			$("#delivery-buttons-deliveryAogorderPage").buttonWidget("setDataID",{id:'${deliveryAogorder.id}',state:'3',type:'view'});
			    		}else{
			    			$("#delivery-deliveryAogorder-status-select").val("2");
			    			$.messager.alert("提醒","保存成功"+json.msg);
			    			$("#delivery-buttons-deliveryAogorderPage").buttonWidget("setDataID",{id:'${deliveryAogorder.id}',state:'2',type:'edit'});
			    		}
			    		$("#delivery-buttons-deliveryAogorderPage").buttonWidget("addNewDataId", json.id);
			    		
			    	}else{
			    		$.messager.alert("提醒","保存失败."+json.msg);
			    	}
			    }  
			}); 
    	});
    </script>
</body>

</html>