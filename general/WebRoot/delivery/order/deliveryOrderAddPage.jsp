<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<title>新增代配送销售订单</title>
</head>
<body>
    
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="delivery-form-deliveryOrderAddPage" action="" method="post">
	    	<div data-options="region:'north',border:false" style="height: 130px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input id="delivery-deliveryOrder-id" class="easyui-validatebox" name="deliveryOrder.id" <c:if test="${autoCreate == true }">readonly='readonly'</c:if> <c:if test="${autoCreate == false }">required="required"</c:if> style="width:160px;"/></td>
	    				<td class="len80 right">业务日期：</td>
	    				<td class="len165"><input type="text" id="delivery-deliveryOrder-businessdate" style="width:160px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${busdate }" name="deliveryOrder.businessdate" /></td>
	    				<td class="len80 right">状态：</td>
	    				<td class="len165"><select id="delivery-deliveryOrder-status-select" disabled="disabled" style="width:160px;">
    	                        <option ></option>
    	                        <option value="0" selected="selected">新增</option>
    	                        <option value="2">保存</option>
    	                        <option value="3">审核通过</option>
    	                        <option value="4">关闭</option>
    	                    </select></td>
	    			</tr>
	    			<tr>
	    			     <td class="len80 left">供应商：</td>
	    				<td colspan="3" style="text-align: left">
	    					<input type="text" id="delivery-deliveryOrder-supplierid" name="deliveryOrder.supplierid" style="width:320px;" />
	    					<span id="delivery-deliveryOrder-supplier" style="margin-left:5px;line-height:25px;"></span>
	    				</td>
	    				<td class="len80 right">仓库：</td>
	    				<td>
	    					<input type="text" id="delivery-deliveryOrder-storageid" name="deliveryOrder.storageid" style="width:160px;"/>
	    				</td>
	    		    </tr>
	    		    <tr>
	    				<td class="len80 left">客户编号：</td>
	    				<td class="len165">
	    					<input type="text" id="delivery-deliveryOrder-customerid" name="deliveryOrder.customerid"  style="width:160px;"/>
	    					<input type="hidden" id="delivery-deliveryOrder-pcustomerid" name="deliveryOrder.pcustomerid" class="len150" />
	    					<input type="hidden" id="delivery-deliveryOrder-customersort" name="deliveryOrder.customersort" class="len150" />
	    					<input type="hidden" id="delivery-deliveryOrder-deptid" name="deliveryOrder.deptid" class="len150" />
	    				
	    				</td>
	    				<td class="len80 right">客户名称：</td>
	    				<td class="len165"><input type="text" id="delivery-deliveryOrder-customername" name="deliveryOrder.customername" style="width:160px;"/></td>
	    				<td class="len80 right">客户单号：</td>
	    				<td>
	    					<input type="text" id="delivery-deliveryOrder-sourceid" name="deliveryOrder.sourceid" style="width:160px;" onfocus="frm_focus('deliveryOrder.sourceid');" onblur="frm_blur('deliveryOrder.sourceid');"/>
	    				</td>
	    			</tr>
					<tr>
						<td class="len80 left">备注：</td>
						<td colspan="5">
							<input type="text" id="delivery-deliveryOrder-remark" name="deliveryOrder.remark" style="width:690px;" onfocus="frm_focus('deliveryOrder.remark');" onblur="frm_blur('deliveryOrder.remark');"/>
						</td>
					</tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<table id="delivery-datagrid-deliveryOrderAddPage"></table>
	    	</div>
	    	<input type="hidden" id="delivery-deliveryOrder-deliveryOrderDetail" name="detailJson"/>
	    	<input type="hidden" id="delivery-deliveryOrder-saveaudit" name="saveaudit" value="save"/>
	    </form>
    </div>
    <div class="easyui-menu" id="delivery-contextMenu-deliveryOrderAddPage" style="display: none;">
    	<div id="delivery-addRow-deliveryOrderAddPage" data-options="iconCls:'button-add'">添加</div>
    	<div id="delivery-editRow-deliveryOrderAddPage" data-options="iconCls:'button-edit'">编辑</div>
    	<div id="delivery-removeRow-deliveryOrderAddPage" data-options="iconCls:'button-delete'">删除</div>
    </div>
    <div id="delivery-dialog-deliveryOrderAddPage"></div>
    <script type="text/javascript">
    	$(function(){
    		$("#delivery-buttons-deliveryOrderPage").buttonWidget("initButtonType", 'add');
    		
    		$("#delivery-datagrid-deliveryOrderAddPage").datagrid({
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
    				$("#delivery-datagrid-deliveryOrderAddPage").datagrid('selectRow', rowIndex);
                    $("#delivery-contextMenu-deliveryOrderAddPage").menu('show', {  
                        left:e.pageX,  
                        top:e.pageY  
                    });
    			},
    			onSortColumn:function(sort, order){
    				var rows = $("#delivery-datagrid-deliveryOrderAddPage").datagrid("getRows");
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
    				$("#delivery-datagrid-deliveryOrderAddPage").datagrid("loadData",dataArr);
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
    		
    		//仓库选择
    		$("#delivery-deliveryOrder-storageid").widget({
    			referwid:'RL_T_BASE_STORAGE_INFO',
	    		width:160,
				col:'name',
				singleSelect:true,
				required:true,
				initValue:'',
    		});
    		//客户选择
    		$("#delivery-deliveryOrder-customerid").customerWidget({
				onSelect:function(data){
					$("#delivery-deliveryOrder-customerid").val(data.id);
					$("#delivery-deliveryOrder-pcustomerid").val(data.pid);
					$("#delivery-deliveryOrder-customername").val(data.name);
					$("#delivery-deliveryOrder-customersort").val(data.customersort);
					$("#delivery-deliveryOrder-deptid").val(data.salesdeptid);
					$("#delivery-deliveryOrder-sourceid").focus();
				},
				required:true,
    		});
    		//供应商选择
    		$("#delivery-deliveryOrder-supplierid").supplierWidget({
    			width:160,
    			required:true,
    			onSelect:function(data){
    				$("#delivery-deliveryOrder-storageid").widget('setValue',data.storageid);
    				$("#delivery-deliveryOrder-customerid").focus();
    				$("#delivery-deliveryOrder-supplier").html("  编号:" + data.id);
    			}
    		 
    		});
    		//代配送销售订单明细添加
    		$("#delivery-addRow-deliveryOrderAddPage").click(function(){
				var flag = $("#delivery-addRow-deliveryOrderAddPage").menu('getItem','#delivery-addRow-deliveryOrderAddPage').disabled;
				if(flag){
					return false;
				}
    			beginAddDetail();
    		});
    		//代配送销售订单明细修改
    		$("#delivery-editRow-deliveryOrderAddPage").click(function(){
    			var flag = $("#delivery-contextMenu-deliveryOrderAddPage").menu('getItem','#delivery-editRow-deliveryOrderAddPage').disabled;
				if(flag){
					return false;
				}
    			beginEditDetail();
    		});
    		//代配送销售订单明细删除
    		$("#delivery-removeRow-deliveryOrderAddPage").click(function(){
    			var flag = $("#delivery-contextMenu-deliveryOrderAddPage").menu('getItem','#delivery-removeRow-deliveryOrderAddPage').disabled;
				if(flag){
					return false;
				}
    			removeDetail();
    		});
    		//订单提交
    		$("#delivery-form-deliveryOrderAddPage").form({  
			    onSubmit: function(){  
			    	var json = $("#delivery-datagrid-deliveryOrderAddPage").datagrid('getRows');
		
					$("#delivery-deliveryOrder-deliveryOrderDetail").val(JSON.stringify(json));
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
			    			$.messager.alert("提醒","保存并审核成功");
			    			$("#delivery-deliveryOrder-status-select").val("3");
			    			var d = new Date();
							var str = d.getFullYear()+"-"+(d.getMonth()+1)+"-"+d.getDate();
							$("#delivery-deliveryOrder-businessdate").val(str);
			    			$("#delivery-buttons-deliveryOrderPage").buttonWidget("setDataID",{id:'${deliveryOrder.id}',state:'3',type:'view'});
			    		}else if(json.auditflag==null){
			    			$("#delivery-deliveryOrder-status-select").val("2");
			    			$.messager.alert("提醒","保存成功"+json.msg);
			    			$("#delivery-buttons-deliveryOrderPage").buttonWidget("setDataID",{id:'${deliveryOrder.id}',state:'2',type:'edit'});
			    		}
			    		else{
			    			$("#delivery-deliveryOrder-status-select").val("2");
			    			$.messager.alert("提醒","保存成功,审核失败."+json.msg);
			    			$("#delivery-buttons-deliveryOrderPage").buttonWidget("setDataID",{id:'${deliveryOrder.id}',state:'2',type:'edit'});
			    		}
			    		$("#delivery-buttons-deliveryOrderPage").buttonWidget("addNewDataId", json.id);
			    		$("#delivery-panel-deliveryOrderPage").panel({
						//	href:"delivery/allocateOutEditPage.do?id="+json.id,
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
    </script>
</body>

</html>