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
	    	<div data-options="region:'north',border:false" style="height:140px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input id="sales-form-id-dispatchBillAddPage" class="len150 easyui-validatebox" name="dispatchBill.id" <c:if test="${autoCreate == true }">readonly='readonly'</c:if> <c:if test="${autoCreate == false }">required="required"</c:if> /></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input type="text" class="len150 Wdate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" value="${date }" name="dispatchBill.businessdate" /></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165"><select id="sales-status-dispatchBillAddPage" class="len150" name="dispatchBill.status" disabled="disabled">
	    						<c:forEach items="${statusList }" var="list">
	    							<c:choose>
	    								<c:when test="${list.code == '0'}">
	    									<option value="${list.code }" selected="selected">${list.codename }</option>
	    								</c:when>
	    								<c:otherwise>
	    									<option value="${list.code }">${list.codename }</option>
	    								</c:otherwise>
	    							</c:choose>
	    						</c:forEach>
	    					</select></td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">客户：</td>
	    				<td colspan="3"><input type="text" id="sales-customer-dispatchBillAddPage" name="dispatchBill.customerid" style="width: 300px;"/><span id="sales-customer-showid-dispatchBillAddPage" style="margin-left:5px;line-height:25px;"></span></td>
	    				<%--<td class="len80 left">对方经手人：</td>--%>
	    				<%--<td><select id="sales-handler-dispatchBillAddPage" class="len136" name="dispatchBill.handlerid" >--%>
	    						<%----%>
	    					<%--</select>--%>
	    				<%--</td>--%>
                        <td class="len80 left">销售部门：</td>
                        <td><select id="sales-salesDept-dispatchBillAddPage" class="len150" name="dispatchBill.salesdept">
                            <option value=""></option>
                            <c:forEach items="${salesDept}" var="list">
                                <option value="${list.id }">${list.name }</option>
                            </c:forEach>
                        </select>
                        </td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">客户业务员：</td>
	    				<td><select id="sales-salesMan-dispatchBillAddPage" class="len150" name="dispatchBill.salesuser">
	    					</select>
	    				</td>
	    				<%--<td class="len80 left">来源类型：</td>--%>
	    				<%--<td>--%>
	    					<%--<select class="len130" name="dispatchBill.source" disabled="disabled">--%>
	    						<%--<option value="0">无</option>--%>
	    						<%--<option value="1">销售订单</option>--%>
	    					<%--</select>--%>
	    				<%--</td>--%>
                        <td class="len80 left">备注：</td>
                        <td colspan="3"><input type="text" name="dispatchBill.remark" style="width: 400px;" onfocus="frm_focus('dispatchBill.remark');" onblur="frm_blur('dispatchBill.remark');" /></td>
	    			</tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<input type="hidden" name="goodsjson" id="sales-goodsJson-dispatchBillAddPage" />
	    		<input id="sales-saveaudit-dispatchBillAddPage" type="hidden" name="saveaudit"/>
	    		<table id="sales-datagrid-dispatchBillAddPage"></table>
	    	</div>
    	</form>
    </div>
    <div class="easyui-menu" id="sales-contextMenu-dispatchBillAddPage" style="display: none;">
    	<div id="sales-addRow-dispatchBillAddDiscountPage" data-options="iconCls:'button-add'">添加折扣</div>
    	<div id="sales-addRow-dispatchBillAddPage" data-options="iconCls:'button-add'">添加</div>
    	<div id="sales-editRow-dispatchBillAddPage" data-options="iconCls:'button-edit'">编辑</div>
    	<div id="sales-removeRow-dispatchBillAddPage" data-options="iconCls:'button-delete'">删除</div>
    </div>
    <div id="sales-dialog-dispatchBillAddPage" class="easyui-dialog" data-options="closed:true"></div>
    <script type="text/javascript">
    	$(function(){
    		$("#sales-datagrid-dispatchBillAddPage").datagrid({ //销售商品明细信息编辑
    			authority:wareListJson,
    			columns: wareListJson.common,
    			frozenColumns: wareListJson.frozen,
    			border: true,
    			fit:true,
    			rownumbers: true,
    			showFooter: true,
    			striped:true,
    			singleSelect: true,
    			onSortColumn:function(sort, order){
    				var rows = $("#sales-datagrid-dispatchBillAddPage").datagrid("getRows");
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
    				$("#sales-datagrid-dispatchBillAddPage").datagrid("loadData",dataArr);
    				return false;
    			},
    			onLoadSuccess: function(){
    				var rows = $("#sales-datagrid-dispatchBillAddPage").datagrid('getRows');
    				var leng = rows.length;
    				if(leng < 12){
    					for(var i=leng; i<12; i++){
    						$("#sales-datagrid-dispatchBillAddPage").datagrid('appendRow',{});
    					}
    				}
    				else{
    					$("#sales-datagrid-dispatchBillAddPage").datagrid('appendRow',{});
    				}
    				countTotal();
    			},
    			onRowContextMenu: function(e, rowIndex, rowData){
    				e.preventDefault();
    				$wareList.datagrid('selectRow', rowIndex);
                    $("#sales-contextMenu-dispatchBillAddPage").menu('show', {  
                        left:e.pageX,  
                        top:e.pageY  
                    });
    			},
    			onDblClickRow: function(rowIndex, rowData){
    				if(rowData.goodsid == undefined){
    					beginAddDetail();
    				}
    				else{
    					if(rowData.isdiscount=='1'){
    						beginEditDetailDiscount();
    					}else{
    						beginEditDetail();
    					}
    				}
    			}
    		}).datagrid("loadData", {'total':12,'rows':[{},{},{},{},{},{},{},{},{},{},{},{} ],'footer':[{goodsid:'合计', deliverytype: '0'}]}).datagrid('columnMoving');
    		$("#sales-customer-dispatchBillAddPage").customerWidget({ //客户参照窗口
    			name:'t_sales_dispatchbill',
				col:'customerid',
    			singleSelect:true,
    			width:300,
    			onlyLeafCheck:false,
    			required:true,
    			onSelect:function(data){
    				$("#sales-customer-showid-dispatchBillAddPage").text("编号："+ data.id);
    				$("#sales-salesDept-dispatchBillAddPage").val(data.salesdeptid);
	    			$.getJSON('basefiles/getPersonnelListByDeptid.do', {deptid: data.salesdeptid}, function(json){
	    				if(json.length>0){
	    					$("#sales-salesMan-dispatchBillAddPage").html("");
	    					$("#sales-salesMan-dispatchBillAddPage").append("<option value=''></option>");
	    					for(var i=0;i<json.length;i++){
	    						$("#sales-salesMan-dispatchBillAddPage").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
	    					}
			    			$("#sales-salesMan-dispatchBillAddPage").val(data.salesuserid);
	    				}	
	    			});
	    			$.getJSON('basefiles/getContacterBy.do', {type:"1", id:data.id}, function(json){
	    				if(json.length>0){
	    					$("#sales-handler-dispatchBillAddPage").html("");
	    					$("#sales-handler-dispatchBillAddPage").append("<option value=''></option>");
	    					for(var i=0;i<json.length;i++){
	    						$("#sales-handler-dispatchBillAddPage").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
	    					}
	    					$("#sales-handler-dispatchBillAddPage").val(data.contact);
	    				}
	    			});
    				$("input[name='dispatchBill.remark']").focus();
    			},
    			onClear:function(){
    				$("#sales-customer-showid-dispatchBillAddPage").text("");
    				$("#sales-handler-dispatchBillAddPage").val('');
			    	$("#sales-salesDept-dispatchBillAddPage").val('');
			    	$("#sales-salesMan-dispatchBillAddPage").val('');
    			}
    		});
    		$("#sales-salesDept-dispatchBillAddPage").change(function(){
    			var v = $(this).val();
    			$.getJSON('basefiles/getPersonnelListByDeptid.do', {deptid: v}, function(json){
	    			if(json.length>0){
	    				$("#sales-salesMan-dispatchBillAddPage").html("");
	    				$("#sales-salesMan-dispatchBillAddPage").append("<option value=''></option>");
	    				for(var i=0;i<json.length;i++){
	    					$("#sales-salesMan-dispatchBillAddPage").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
	    				}
	    			}	
	    		});
    		});
    		//折扣添加页面
    		$("#sales-addRow-dispatchBillAddDiscountPage").click(function(){
    			beginAddDiscountDetail();
    		});
    		$("#sales-addRow-dispatchBillAddPage").click(function(){
    			beginAddDetail();
    		});
    		$("#sales-editRow-dispatchBillAddPage").click(function(){
    			beginEditDetail();
    		});
    		$("#sales-removeRow-dispatchBillAddPage").click(function(){
    			removeDetail();
    		});
    		$("#sales-buttons-dispatchBill").buttonWidget("initButtonType", 'add');
    		$("#sales-buttons-dispatchBill").buttonWidget("disableButton","button-print");
			$("#sales-buttons-dispatchBill").buttonWidget("disableButton","button-preview");
    		
    	});
    	var $wareList = $("#sales-datagrid-dispatchBillAddPage"); //商品datagrid的div对象
    </script>
  </body>
</html>
