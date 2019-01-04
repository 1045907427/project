<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售发货通知单新增</title>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="sales-form-dispatchBillAddPage" action="sales/addDispatchBill.do" method="post">
    		<input type="hidden" id="sales-addType-dispatchBillAddPage" name="addType" />
    		<input type="hidden" name="dispatchBill.billno" value="${order.id }" />
	    	<div data-options="region:'north',border:false" style="height:140px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input class="len130 easyui-validatebox" name="dispatchBill.id" <c:if test="${autoCreate == true }">readonly='readonly'</c:if> <c:if test="${autoCreate == false }">required="required"</c:if> /></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input type="text" class="len130" name="dispatchBill.businessdate" value="${order.businessdate }" readonly="readonly" /></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165"><select disabled="disabled" class="len136"><option>新增</option></select></td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">客户：</td>
	    				<td><input type="text" id="sales-customer-dispatchBillAddPage" name="dispatchBill.customerid" value="${order.customerid }" text="${order.customername }" readonly="readonly" /></td>
	    				<td class="len80 left">对方经手人：</td>
	    				<td><select id="sales-handler-dispatchBillAddPage" class="len136" name="dispatchBill.handlerid" disabled="disabled" >
	    					</select>
	    					<input type="hidden"  name="dispatchBill.handlerid" value="${order.handlerid }"/>
	    				</td>
	    				<td class="len80 left">销售部门：</td>
	    				<td><select id="sales-salesDept-dispatchBillAddPage" class="len136"  disabled="disabled">
	    						<option value=""></option>
	    						<c:forEach items="${salesDept}" var="list">
	    							<c:choose>
	    								<c:when test="${list.id == order.salesdept}">
	    									<option value="${list.id }" selected="selected">${list.name }</option>
	    								</c:when>
	    								<c:otherwise>
	    									<option value="${list.id }">${list.name }</option>
	    								</c:otherwise>
	    							</c:choose>
	    						</c:forEach>
	    					</select>
	    					<input type="hidden" name="dispatchBill.salesdept" value="${order.salesdept}"/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">客户业务员：</td>
	    				<td><select id="sales-salesMan-dispatchBillAddPage" class="len136"  disabled="disabled" >
	    					</select>
	    					<input type="hidden" name="dispatchBill.salesuser" value="${order.salesuser}"/>
	    				</td>
	    				<td class="len80 left">结算方式：</td>
	    				<td><select class="len136"  id="sales-settletype-dispatchBillAddPage" disabled="disabled" >
	    						<option value=""></option>
	    						<c:forEach items="${settletype}" var="list">
	    							<c:choose>
	    								<c:when test="${list.id == order.settletype}">
	    									<option value="${list.id }" selected="selected">${list.name }</option>
	    								</c:when>
	    								<c:otherwise>
	    									<option value="${list.id }">${list.name }</option>
	    								</c:otherwise>
	    							</c:choose>
	    						</c:forEach>
	    					</select>
	    					<input type="hidden" name="dispatchBill.settletype" value="${order.settletype}"/>
	    				</td>
	    				<td class="len80 left">支付方式：</td>
	    				<td><select class="len136" id="sales-paytype-dispatchBillAddPage" <c:if test="${bill.source == 1 }" >disabled="disabled"</c:if> >
	    						<option value=""></option>
	    						<c:forEach items="${paytype}" var="list">
	    							<c:choose>
	    								<c:when test="${list.id == order.paytype}">
	    									<option value="${list.id }" selected="selected">${list.name }</option>
	    								</c:when>
	    								<c:otherwise>
	    									<option value="${list.id }">${list.name }</option>
	    								</c:otherwise>
	    							</c:choose>
	    						</c:forEach>
	    					</select>
	    					<input id="sales-paytype-dispatchBillAddPage-hidden" type="hidden" name="dispatchBill.paytype" value="${order.paytype}"/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">来源类型：</td>
	    				<td>
	    					<select class="len130" name="dispatchBill.source" disabled="disabled">
	    						<option value="0">无</option>
	    						<option value="1" selected="selected">销售订单</option>
	    					</select>
	    					<input type="hidden" name="dispatchBill.source" value="1"/>
	    				</td>
	    				<td class="len80 left">备注：</td>
	    				<td colspan="3"><input type="text" name="dispatchBill.remark" style="width:392px;" value="${order.remark }" /></td>
	    			</tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<input type="hidden" name="goodsjson" id="sales-goodsJson-dispatchBillAddPage" />
	    		<table id="sales-datagrid-dispatchBillAddPage"></table>
	    	</div>
    	</form>
    </div>
    <div id="sales-dialog-dispatchBillAddPage" class="easyui-dialog" data-options="closed:true"></div>
    <script type="text/javascript">
    	$(function(){
    		$("#sales-customer-dispatchBillAddPage").customerWidget({ //客户参照窗口
    			name:'t_sales_dispatchbill',
				col:'customerid',
    			width:130,
    		});
    		$.getJSON('basefiles/getPersonnelListByDeptid.do', {deptid: "${order.salesdept}"}, function(json){
	    		if(json.length>0){
	    			$("#sales-salesMan-dispatchBillAddPage").html("");
	    			$("#sales-salesMan-dispatchBillAddPage").append("<option value=''></option>");
	    			for(var i=0;i<json.length;i++){
	    				$("#sales-salesMan-dispatchBillAddPage").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
	    			}
			    	$("#sales-salesMan-dispatchBillAddPage").val("${order.salesuser}");
	    		}	
	    	});
	    	$.getJSON('basefiles/getContacterBy.do', {type:"1", id:"${order.customerid}"}, function(json){
	    		if(json.length>0){
	    			$("#sales-handler-dispatchBillAddPage").html("");
	    			$("#sales-handler-dispatchBillAddPage").append("<option value=''></option>");
	    			for(var i=0;i<json.length;i++){
	    				$("#sales-handler-dispatchBillAddPage").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
	    			}
	    			$("#sales-handler-dispatchBillAddPage").val("${order.handlerid}");
	    		}
	    	});
	    	$("#sales-paytype-dispatchBillAddPage").change(function(){
	    		$("#sales-paytype-dispatchBillAddPage-hidden").val($(this).val());
	    	});
    		$("#sales-buttons-dispatchBill").buttonWidget("initButtonType", 'add');
    		$("#sales-buttons-dispatchBill").buttonWidget("disableButton","button-print");
			$("#sales-buttons-dispatchBill").buttonWidget("disableButton","button-preview");
    		$("#sales-datagrid-dispatchBillAddPage").datagrid({ //销售商品明细信息编辑
    			authority:wareListJson,
    			columns: wareListJson.common,
    			frozenColumns: wareListJson.frozen,
    			border: true,
    			fit:true,
    			fitColumns:true,
    			rownumbers: true,
    			showFooter: true,
    			striped:true,
    			singleSelect: true,
    			data: JSON.parse('${goodsJson}'),
    			onLoadSuccess: function(){
    				var rows = $("#sales-datagrid-dispatchBillAddPage").datagrid('getRows');
    				var leng = rows.length;
    				if(leng < 12){
    					for(var i=leng; i<12; i++){
    						$("#sales-datagrid-dispatchBillAddPage").datagrid('appendRow',{});
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
    	var $wareList = $("#sales-datagrid-dispatchBillAddPage"); //商品datagrid的div对象
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
