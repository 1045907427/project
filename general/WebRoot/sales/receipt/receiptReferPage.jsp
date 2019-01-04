<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售发货回单参照新增</title>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="sales-form-receiptAddPage" action="sales/addReceipt.do" method="post">
    		<input type="hidden" id="sales-addType-receiptAddPage" name="addType" />
    		<input type="hidden" name="receipt.billno" value="${saleout.id }" />
    		<input type="hidden" name="receipt.duefromdate" value="${duefromdate }" />
	    	<div data-options="region:'north',border:false" style="height:140px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input class="len130 easyui-validatebox" name="receipt.id" <c:if test="${autoCreate == true }">readonly='readonly'</c:if> <c:if test="${autoCreate == false }">required="required"</c:if> /></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input type="text" class="len130" name="receipt.businessdate" value="${saleout.businessdate }" readonly="readonly" /></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165"><select disabled="disabled" class="len136"><option>新增</option></select></td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">客户：</td>
	    				<td colspan="3"><input type="text" id="sales-customer-receiptAddPage" name="receipt.customerid" value="${saleout.customerid }" text="${saleout.customername}" readonly="readonly" style="width: 400px;" required="required" /></td>
	    				<td class="len80 left">对方经手人：</td>
	    				<td><input id="sales-handler-receiptAddPage" class="len130" name="receipt.handlerid" readonly="readonly" value="${saleout.handlerid }" />
	    				</td>
	    				
	    			</tr>
	    			<tr>
	    				<td class="len80 left">发货仓库：</td>
	    				<td><input id="sales-storage-receiptAddPage" class="len130" name="receipt.storageid" value="${saleout.storageid }" readonly="readonly"/></td>
	    				<td class="len80 left">销售部门：</td>
	    				<td><input id="sales-salesDept-receiptAddPage" class="len130" name="receipt.salesdept" value="${saleout.salesdept }" readonly="readonly" /></td>
	    				<td class="len80 left">客户业务员：</td>
	    				<td><input id="sales-salesMan-receiptAddPage" class="len130" name="receipt.salesuser" value="${saleout.salesuser }" readonly="readonly" /></td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">来源类型：</td>
	    				<td>
	    					<select class="len130 easyui-combobox" name="receipt.source" disabled="disabled">
	    						<option value="0">无</option>
	    						<option value="1" selected="selected">发货单</option>
	    					</select>
	    				</td>
	    				<td class="len80 left">备注：</td>
	    				<td colspan="3"><input type="text" name="receipt.remark" style="width: 400px;" value="${saleout.remark }" /></td>
	    			</tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<input type="hidden" name="goodsjson" id="sales-goodsJson-receiptAddPage" />
	    		<table id="sales-datagrid-receiptAddPage"></table>
	    	</div>
    	</form>
    </div>
    <div id="sales-dialog-receiptAddPage" class="easyui-dialog" data-options="closed:true"></div>
    <script type="text/javascript">
    	$(function(){
    		$("#sales-customer-receiptAddPage").customerWidget({ //客户参照窗口
    			name:'t_sales_receipt',
				col:'customerid',
    			singleSelect:true,
    			onlyLeafCheck:false
    		});
    		$("#sales-handler-receiptAddPage").widget({ //对方经手人参照窗口
	    		name:'t_sales_receipt',
				col:'handlerid',
			 	singleSelect:true,
			 	width:130,
				onlyLeafCheck:true,
				param:[{field:'customer',op:'equal',value:"${bill.customerid}"}]
	    	});
    		$("#sales-salesDept-receiptAddPage").widget({ //销售部门参照窗口
    			name:'t_sales_receipt',
    			col:'salesdept',
    			singleSelect:true,
    			width:130,
    			onlyLeafCheck:true
    		});
    		$("#sales-salesMan-receiptAddPage").widget({ //客户业务员参照窗口
    			name:'t_sales_receipt',
    			col:'salesuser',
    			singleSelect:true,
    			width:130,
    			onlyLeafCheck:true
    		});
    		$("#sales-settletype-receiptAddPage").widget({ //结算方式参照窗口
    			name:'t_sales_receipt',
    			col:'settletype',
    			singleSelect:true,
    			width:130,
    			onlyLeafCheck:true
    		});
    		$("#sales-paytype-receiptAddPage").widget({ //支付方式参照窗口
    			name:'t_sales_receipt',
    			col:'paytype',
    			singleSelect:true,
    			width:130,
    			onlyLeafCheck:true
    		});
    		$("#sales-storage-receiptAddPage").widget({ //发货仓库参照窗口
    			name:'t_sales_receipt',
    			col:'storageid',
    			singleSelect:true,
    			width:130,
    			onlyLeafCheck:true
    		});	
    		$("#sales-buttons-receiptPage").buttonWidget("initButtonType", 'add');
    		$("#sales-datagrid-receiptAddPage").datagrid({ //销售商品明细信息编辑
    			authority:wareListJson,
    			columns: wareListJson.common,
    			frozenColumns: wareListJson.frozen,
    			border: true,
    			fit:true,
    			rownumbers: true,
    			showFooter: true,
    			striped:true,
    			singleSelect: false,
    			checkOnSelect:true,
    			selectOnCheck:true,
    			data: JSON.parse('${goodsJson}'),
    			onLoadSuccess: function(){
    				var rows = $("#sales-datagrid-receiptAddPage").datagrid('getRows');
    				var leng = rows.length;
    				if(leng < 12){
    					for(var i=leng; i<12; i++){
    						$("#sales-datagrid-receiptAddPage").datagrid('appendRow',{});
    					}
    				}
    				countTotal();
    			}
    		}).datagrid('columnMoving');
    		$.extend($.fn.datagrid.methods, {  //扩展单元格编辑
	            editCell: function(jq,param){  
	                return jq.each(function(){  
	                    var opts = $(this).datagrid('options');  
	                    var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));  
	                    for(var i=0; i<fields.length; i++){  
	                        var col = $(this).datagrid('getColumnOption', fields[i]);  
	                        col.editor1 = col.editor;  
	                        if (fields[i] != param.field){  
	                            col.editor = null;  
	                        }  
	                    }  
	                    $(this).datagrid('beginEdit', param.index);  
	                    for(var i=0; i<fields.length; i++){  
	                        var col = $(this).datagrid('getColumnOption', fields[i]);  
	                        col.editor = col.editor1;  
	                    }  
	                });  
	            }  
	        });  
    	});
    	var $wareList = $("#sales-datagrid-receiptAddPage"); //商品datagrid的div对象
    	var editIndex = undefined;
    	function endEditing(){  
            if (editIndex == undefined){return true}  
            if ($wareList.datagrid('validateRow', editIndex)){  
                $wareList.datagrid('endEdit', editIndex);  
                editIndex = undefined;  
                return true;  
            } else {  
                return false;  
            }  
        }  
    </script>
  </body>
</html>
