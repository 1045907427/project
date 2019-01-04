<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售退货通知单修改页面</title>
  </head> 
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="sales-form-rejectBillAddPage" action="sales/addRejectBill.do" method="post">
    		<input type="hidden" id="sales-addType-rejectBillAddPage" name="addType" />
    		<input type="hidden" name="rejectBill.billno" value="${receipt.id }" />
	    	<div data-options="region:'north',border:false" style="height:140px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input class="len130 easyui-validatebox" name="rejectBill.id" <c:if test="${autoCreate == true }">readonly='readonly'</c:if> <c:if test="${autoCreate == false }">required="required"</c:if> /></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input type="text" class="len130" value="${receipt.businessdate }" name="rejectBill.businessdate" /></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165">
	    					<select disabled="disabled" class="len136"><option>新增</option></select>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">客户：</td>
	    				<td><input type="text" id="sales-customer-rejectBillAddPage" name="rejectBill.customerid" value="${receipt.customerid }" class="len130" readonly="readonly" /></td>
	    				<td class="len80 left">司机：</td>
	    				<td><input id="sales-driver-rejectBillAddPage" class="len130" name="rejectBill.driverid" /></td>
	    				<td class="len80 left">销售部门：</td>
	    				<td><input id="sales-salesDept-rejectBillAddPage" class="len130" name="rejectBill.salesdept" value="${receipt.salesdept }" readonly="readonly" /></td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">客户业务员：</td>
	    				<td><input id="sales-salesMan-rejectBillAddPage" class="len130" name="rejectBill.salesuser" value="${receipt.salesuser }" readonly="readonly" /></td>
	    				<td class="len80 left">结算方式：</td>
	    				<td><input class="len136" name="rejectBill.settletype" id="sales-settletype-rejectBillAddPage" value="${receipt.settletype }" readonly="readonly" /></td>
	    				<td class="len80 left">支付方式：</td>
	    				<td><input class="len136" name="rejectBill.paytype" id="sales-paytype-rejectBillAddPage" value="${receipt.paytype }" readonly="readonly" /></td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">来源类型：</td>
	    				<td>
	    					<select class="len130 easyui-combobox" name="rejectBill.source" disabled="disabled">
	    						<option value="0">无</option>
	    						<option value="1" selected="selected">销售发货回单</option>
	    					</select>
	    				</td>
	    				<td class="len80 left">入库仓库：</td>
	    				<td><input id="sales-storage-rejectBillAddPage" class="len130" name="rejectBill.storageid" value="${receipt.storageid }" /></td>
	    				<td class="len80 left">备注：</td>
	    				<td colspan="5"><input type="text" name="rejectBill.remark" class="len130" value="${receipt.remark }" /></td>
	    			</tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<input type="hidden" name="goodsjson" id="sales-goodsJson-rejectBillAddPage" />
	    		<table id="sales-datagrid-rejectBillAddPage"></table>
	    	</div>
	    	<div data-options="region:'south',border:false" style="height:70px;">
	    		<div>
		    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
		    			<tr>
		    				<td class="len80 left">审核人：</td>
		    				<td><input type="text" class="len130" readonly="readonly" /></td>
		    				<td class="len80 left">修改人：</td>
		    				<td><input type="text" class="len130" readonly="readonly" /></td>
		    				<td class="len80 left">制单人：</td>
		    				<td><input type="text" class="len130" readonly="readonly" value="${userName }" /></td>
		    			</tr>
		    			<tr>
		    				<td class="left">审核日期：</td>
		    				<td><input type="text" class="len130" readonly="readonly" /></td>
		    				<td class="left">修改日期：</td>
		    				<td><input type="text" class="len130" readonly="readonly" /></td>
		    				<td class="left">制单日期：</td>
		    				<td><input type="text" class="len130" value="${date }" readonly="readonly" /></td>
		    			</tr>
		    		</table>
	    		</div>
	    	</div>
	    </form>
    </div>
    <c:if test="${rejectBill.source == 0}">
    <div class="easyui-menu" id="sales-contextMenu-rejectBillAddPage">
    	<div id="sales-addRow-rejectBillAddPage" data-options="iconCls:'button-add'">添加</div>
    	<div id="sales-editRow-rejectBillAddPage" data-options="iconCls:'button-edit'">编辑</div>
    	<div id="sales-removeRow-rejectBillAddPage" data-options="iconCls:'button-delete'">删除</div>
    </div>
    <div id="sales-dialog-rejectBillAddPage" class="easyui-dialog" data-options="closed:true"></div>
    </c:if>
    <script type="text/javascript">
    	$(function(){
    		$("#sales-customer-rejectBillAddPage").widget({ //客户参照窗口
    			name:'t_sales_rejectbill',
				col:'customerid',
    			singleSelect:true,
    			width:130,
    			onlyLeafCheck:false,
    			onChecked:function(data, checked){
    				if(checked){
	    				$("#sales-salesMan-rejectBillAddPage").widget({ //客户业务员参照窗口
			    			name:'t_sales_rejectbill',
			    			col:'salesuser',
			    			singleSelect:true,
			    			width:130,
			    			onlyLeafCheck:true,
			    			param:[{field:'deptid',op:'equal',value:data.salesdeptid}]
			    		});
			    		$("#sales-salesMan-rejectBillAddPage").widget('setValue', data.salesuserid)
	    				$("#sales-salesDept-rejectBillAddPage").widget('setValue', data.salesdeptid);
	    				$("#sales-settletype-rejectBillAddPage").widget('setValue', data.settletype);
	    				$("#sales-paytype-rejectBillAddPage").widget('setValue', data.paytype);
    				}
    				else{
	    				$("#sales-salesMan-rejectBillAddPage").widget({ //客户业务员参照窗口
			    			name:'t_sales_rejectbill',
			    			col:'salesuser',
			    			singleSelect:true,
			    			width:130,
			    			onlyLeafCheck:true,
			    			param:[{field:'deptid',op:'equal',value:"0"}]
			    		});
			    		$("#sales-salesMan-rejectBillAddPage").widget('clear');
			    		$("#sales-salesDept-rejectBillAddPage").widget('clear');
    				}
    			},
    			onClear:function(){
	    			$("#sales-salesMan-rejectBillAddPage").widget({ //客户业务员参照窗口
			    		name:'t_sales_rejectbill',
			    		col:'salesuser',
			    		singleSelect:true,
			    		width:130,
			    		onlyLeafCheck:true,
			    		param:[{field:'deptid',op:'equal',value:"0"}]
			    	});
			    	$("#sales-salesMan-rejectBillAddPage").widget('clear');
			    	$("#sales-salesDept-rejectBillAddPage").widget('clear');
    			}
    		});
    		$("#sales-salesDept-rejectBillAddPage").widget({ //销售部门参照窗口
    			name:'t_sales_rejectbill',
    			col:'salesdept',
    			singleSelect:true,
    			width:130,
    			onlyLeafCheck:true,
    			onSelect:function(data){
    				$("#sales-salesMan-rejectBillAddPage").widget({ //客户业务员参照窗口
			    		name:'t_sales_rejectbill',
			    		col:'salesuser',
			    		singleSelect:true,
			    		width:130,
			    		onlyLeafCheck:true,
			    		param:[{field:'deptid',op:'equal',value:data.id}]
			    	});
    			}
    		});
    		$("#sales-driver-rejectBillAddPage").widget({
    			referwid:'RL_T_BASE_PERSONNEL_LOGISTICS',
    			width:130,
				singleSelect:true,
				onSelect:function(data){
					$("#sales-salesDept-rejectBillAddPage").focus();
				}
    		});
    		$("#sales-storage-rejectBillAddPage").widget({ //出库仓库参照窗口
    			name:'t_sales_rejectbill',
    			col:'storageid',
    			singleSelect:true,
    			width:130,
    			onlyLeafCheck:true
    		});
    		$("#sales-settletype-rejectBillAddPage").widget({ //结算方式参照窗口
    			name:'t_sales_rejectbill',
    			col:'settletype',
    			singleSelect:true,
    			width:130,
    			onlyLeafCheck:true
    		});
    		$("#sales-paytype-rejectBillAddPage").widget({ //支付方式参照窗口
    			name:'t_sales_rejectbill',
    			col:'paytype',
    			singleSelect:true,
    			width:130,
    			onlyLeafCheck:true
    		});
    		$("#sales-addRow-rejectBillAddPage").click(function(){
    			beginAddDetail();
    		});
    		$("#sales-editRow-rejectBillAddPage").click(function(){
    			beginEditDetail();
    		});
    		$("#sales-removeRow-rejectBillAddPage").click(function(){
    			removeDetail();
    		});
    		$("#sales-buttons-rejectBill").buttonWidget("setDataID", {id:'${rejectBill.id}', state:'${rejectBill.status}', type:'edit'});

    		$("#sales-buttons-rejectBill").buttonWidget("disableButton","button-print");
			$("#sales-buttons-rejectBill").buttonWidget("disableButton","button-preview");
			
    		$("#sales-datagrid-rejectBillAddPage").datagrid({ //销售商品明细信息编辑
    			authority:wareListJson,
    			columns: wareListJson.common,
    			frozenColumns: wareListJson.frozen,
    			border: true,
    			fit: true,
    			fitColumns:true,
    			rownumbers: true,
    			showFooter: true,
    			striped:true,
    			singleSelect: true,
    			data: JSON.parse('${goodsJson}'),
    			onLoadSuccess: function(){
    				var rows = $("#sales-datagrid-rejectBillAddPage").datagrid('getRows');
    				var leng = rows.length;
    				if(leng > 0){
    					$("#sales-parentid-rejectBill").val(rows[0].billno);
    				}
    				if(leng < 12){
    					for(var i=leng; i<12; i++){
    						$("#sales-datagrid-rejectBillAddPage").datagrid('appendRow',{});
    					}
    				}
    				countTotal();
    			},
    			onRowContextMenu: function(e, rowIndex, rowData){
    				e.preventDefault();
    				$wareList.datagrid('selectRow', rowIndex);
                    $("#sales-contextMenu-rejectBillAddPage").menu('show', {  
                        left:e.pageX,  
                        top:e.pageY  
                    });
    			},
    			onDblClickRow: function(rowIndex, rowData){
    				if('${rejectBill.source}' == '1'){
    					return false;
    				}
    				if(rowData.goodsid == undefined){
    					beginAddDetail();
    				}
    				else{
    					beginEditDetail();
    				}
    			}
    		}).datagrid('columnMoving');
    	});
    	var $wareList = $("#sales-datagrid-rejectBillAddPage"); //商品datagrid的div对象
    </script>
  </body>
</html>
