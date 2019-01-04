<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售发货通知单查看</title>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="sales-form-dispatchBillAddPage" action="sales/addDispatchBill.do" method="post">
    		<input type="hidden" id="sales-addType-dispatchBillAddPage" name="addType" />
	    	<div data-options="region:'north',border:false" style="height:110px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input class="len150 easyui-validatebox" value="${bill.id }" readonly="readonly" /></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input id="sales-businessdate-dispatchBillAddPage" type="text" class="len150" value="${bill.businessdate }" readonly="readonly" /></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165">
	    					<select disabled="disabled" class="len150">
	    						<c:forEach items="${statusList }" var="list">
	    							<c:choose>
	    								<c:when test="${list.code == bill.status}">
	    									<option value="${list.code }" selected="selected">${list.codename }</option>
	    								</c:when>
	    								<c:otherwise>
	    									<option value="${list.code }">${list.codename }</option>
	    								</c:otherwise>
	    							</c:choose>
	    						</c:forEach>
	    					</select>
	    					<input type="hidden" id="sales-status-dispatchBillAddPage" value="${bill.status }"/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">客户：</td>
	    				<td colspan="3"><input type="text" id="sales-customer-dispatchBillAddPage" readonly="readonly" value="<c:out value="${bill.customername }"></c:out>" style="width: 300px;" /><span id="sales-customer-showid-dispatchBillAddPage" style="margin-left:5px;line-height:25px;">编号：${bill.customerid }</span></td>
                        <c:if test="${bill.source == '2' }">
                            <td class="len80 left">发货仓库：</td>
                            <td><input id="sales-storage-dispatchBillAddPage" class="len150" value="${bill.storageid }" readonly="readonly"/></td>
                        </c:if>
                        <c:if test="${bill.source != '2' }">
                            <td class="len80 left">发货仓库：</td>
                            <td><input id="sales-storage-dispatchBillAddPage" class="len150" value="${bill.storageid }" readonly="readonly"/></td>
                        </c:if>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">销售部门：</td>
	    				<td>
	    					<input type="text" class="len150" value="${bill.salesdeptname }" readonly="readonly"/>
	    					<input id="sales-salesDept-dispatchBillAddPage-hidden" type="hidden" name="dispatchBill.salesdept" value="${bill.salesdept}"/>
	    				</td>
	    				<td class="len80 left">客户业务员：</td>
	    				<td>
	    					<input type="text" class="len150" value="${bill.salesusername }" readonly="readonly"/>
	    					<input id="sales-salesMan-dispatchBillAddPage-hidden" type="hidden" name="dispatchBill.salesuser" value="${bill.salesuser}"/>
	    				</td>
                        <td class="len80 left">备注：</td>
                        <td colspan="3"><input type="text" value="<c:out value="${bill.remark }"></c:out>" class="len150" readonly="readonly"/></td>
	    			</tr>
	    		</table>
	    		<input type="hidden" id="sales-billno-dispatchBillAddPage" value="${bill.billno }"/>
	    		<input type="hidden" id="sales-printtimes-dispatchBillAddPage" value="${bill.printtimes }"/>
	    		<input type="hidden" id="sales-phprinttimes-dispatchBillAddPage" value="${bill.phprinttimes }"/>
	    		<input type="hidden" id="sales-printlimit-dispatchBillAddPage" value="${printlimit }"/>
	    		<input type="hidden" id="sales-fHPrintAfterSaleOutAudit-dispatchBillAddPage" value="${fHPrintAfterSaleOutAudit }"/>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<table id="sales-datagrid-dispatchBillAddPage"></table>
	    	</div>
    	</form>
    </div>
    <script type="text/javascript">
        $("#sales-buttons-dispatchBill").buttonWidget("enableButton",'storage-oweorder-button');
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
    			data: JSON.parse('${goodsJson}'),
    			onSortColumn:function(sort, order){
    				var rows = $("#sales-datagrid-dispatchBillAddPage").datagrid("getRows");
    				var dataArr = [];
    				for(var i=0;i<rows.length;i++){
    					if(rows[i].goodsid!=null && rows[i].goodsid!=""){
    						dataArr.push(rows[i]);
    					}
    				}
    				dataArr.sort(function(a,b){
    					if($.isNumeric(a[sort])){
	    					if(order=="asc"){
	    						return Number(a[sort])>Number(b[sort])?1:-1
	    					}else{
	    						return Number(a[sort])<Number(b[sort])?1:-1
	    					}
    					}else{
    						if(order=="asc"){
	    						return a[sort]>b[sort]?1:-1
	    					}else{
	    						return a[sort]<b[sort]?1:-1
	    					}
    					}
    				});
    				$("#sales-datagrid-dispatchBillAddPage").datagrid("loadData",dataArr);
    				return false;
    			},
    			onLoadSuccess: function(){
    				var rows = $("#sales-datagrid-dispatchBillAddPage").datagrid('getRows');
    				var leng = rows.length;
    				if(leng > 0){
    					$("#sales-parentid-dispatchBill").val(rows[0].billno);
    				}
    				if(leng < 12){
    					for(var i=leng; i<12; i++){
    						$("#sales-datagrid-dispatchBillAddPage").datagrid('appendRow',{});
    					}
    				}
    				countTotal();
    			}
    		}).datagrid('columnMoving');
    		$("#sales-storage-dispatchBillAddPage").widget({
    			referwid:'RL_T_BASE_STORAGE_INFO',
    			width:150,
				singleSelect:true
    		});
    		$("#sales-buttons-dispatchBill").buttonWidget("setDataID", {id:'${bill.id}', state:'${bill.status}', type:'view'});

    		$("#sales-buttons-dispatchBill").buttonWidget("disableMenuItem","button-printview-orderblank");
    		$("#sales-buttons-dispatchBill").buttonWidget("disableMenuItem","button-print-orderblank");
    		$("#sales-buttons-dispatchBill").buttonWidget("disableMenuItem","button-printview-DispatchBill");
    		$("#sales-buttons-dispatchBill").buttonWidget("disableMenuItem","button-print-DispatchBill");
    		$("#sales-buttons-dispatchBill").buttonWidget("disableMenuItem","button-printview-DeliveryOrder");
    		$("#sales-buttons-dispatchBill").buttonWidget("disableMenuItem","button-print-DeliveryOrder");

			<c:if test="${(bill.status=='3' or bill.status=='4') and (bill.phprinttimes =='0'  or '0'==printlimit) }">
				$("#sales-buttons-dispatchBill").buttonWidget("enableMenuItem","button-print-orderblank");
			</c:if>
			<c:choose>
				<c:when test="${fHPrintAfterSaleOutAudit=='1' }">
					<c:if test="${bill.status=='4' and (bill.printtimes == '0' or '0'==printlimit) }">
						$("#sales-buttons-dispatchBill").buttonWidget("enableMenuItem","button-print-DeliveryOrder");
						$("#sales-buttons-dispatchBill").buttonWidget("enableMenuItem","button-print-DispatchBill");
					</c:if>
				</c:when>
				<c:otherwise>
		    		<c:if test="${(bill.status=='3' or bill.status=='4') and (bill.printtimes == '0' or '0'==printlimit) }">
						$("#sales-buttons-dispatchBill").buttonWidget("enableMenuItem","button-print-DeliveryOrder");
						$("#sales-buttons-dispatchBill").buttonWidget("enableMenuItem","button-print-DispatchBill");
					</c:if>
				</c:otherwise>
			</c:choose>
			<c:if test="${(bill.status=='3' or bill.status=='4') }">
				$("#sales-buttons-dispatchBill").buttonWidget("enableMenuItem","button-printview-DeliveryOrder");
				$("#sales-buttons-dispatchBill").buttonWidget("enableMenuItem","button-printview-DispatchBill");
				$("#sales-buttons-dispatchBill").buttonWidget("enableMenuItem","button-printview-orderblank");
			</c:if>
			
    		<c:if test="${bill.status!='2' }">
	    		$("#sales-buttons-dispatchBill").buttonWidget("disableButton", 'storage-deploy-button');
	    	</c:if>
	    	<c:if test="${bill.status=='2' }">
	    		$("#sales-buttons-dispatchBill").buttonWidget("enableButton", 'storage-deploy-button');
	    	</c:if>
    	});
    </script>
  </body>
</html>
