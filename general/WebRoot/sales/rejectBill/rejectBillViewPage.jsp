<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售退货通知单查看页面</title>
  </head> 
  <body>
  <form action="" method="post" id="sales-form-id-rejectBillEditPage">
      <input type="hidden" name="id" value="${rejectBill.id }"/>
  </form>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="sales-form-rejectBillAddPage" action="" method="post">
    		<input type="hidden" id="sales-addType-rejectBillAddPage" name="addType" />
	    	<div data-options="region:'north',border:false" style="height:160px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input class="len150" value="${rejectBill.id }" readonly="readonly" /></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input type="text" id="sales-businessdate-rejectBillAddPage" class="len150" value="${rejectBill.businessdate }" readonly="readonly" /></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165">
	    					<select disabled="disabled" class="len150">
	    						<c:forEach items="${status }" var="list">
	    							<c:choose>
	    								<c:when test="${list.code == rejectBill.status}">
	    									<option value="${list.code }" selected="selected">${list.codename }</option>
	    								</c:when>
	    								<c:otherwise>
	    									<option value="${list.code }">${list.codename }</option>
	    								</c:otherwise>
	    							</c:choose>
	    						</c:forEach>
	    						<option value="9" <c:if test="${rejectBill.status=='9'}">selected="selected"</c:if>>已关闭</option>
	    					</select>
	    					<input type="hidden" id="sales-status-rejectBillAddPage" value="${rejectBill.status }"/>
						</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">客户：</td>
	    				<td colspan="3"><input type="text" id="sales-customer-rejectBillAddPage" value="<c:out value="${rejectBill.customername }"></c:out>" style="width: 300px;" readonly="readonly"/><span id="sales-customer-showid-dispatchBillAddPage" style="margin-left:5px;line-height:25px;">编号：${rejectBill.customerid }</span>
                            <input type="hidden" id="sales-customer-showid-hidden-dispatchBillAddPage" value="${rejectBill.customerid }"/>
                        </td>
	    				<td class="len80 left">司机：</td>
	    				<td><input style="width: 150px" value="<c:out value="${rejectBill.drivername }"></c:out>" readonly="readonly"/></td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">销售部门：</td>
	    				<td><input id="sales-salesDept-rejectBillAddPage" class="len150" value="<c:out value="${rejectBill.salesdeptname }"></c:out>" readonly="readonly"/></td>
	    				<td class="len80 left">客户业务员：</td>
	    				<td><input id="sales-salesMan-rejectBillAddPage" class="len150" value="<c:out value="${rejectBill.salesusername }"></c:out>" readonly="readonly"/></td>
	    				<td class="len80 left">退货类型：</td>
	    				<td>
	    					<select class="len150" name="rejectBill.billtype" disabled="disabled">
	    						<option value="1" <c:if test="${rejectBill.billtype=='1'}">selected="selected"</c:if>>直退</option>
	    						<option value="2" <c:if test="${rejectBill.billtype=='2'}">selected="selected"</c:if>>售后退货</option>
	    					</select>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">入库仓库：</td>
	    				<td><input id="sales-storage-rejectBillAddPage" class="len150" value="<c:out value="${rejectBill.storagename }"></c:out>" readonly="readonly" /></td>
						<td class="len80 left">客户单号：</td>
						<td><input id="sales-sourceid-rejectBillAddPage" type="text" name="rejectBill.sourceid" value="${rejectBill.sourceid}" style="width: 150px;" readonly="readonly" /></td>
	    			</tr>
					<tr>
						<td class="len80 left">备注：</td>
						<td colspan="5"><input type="text" name="rejectBill.remark" value="<c:out value="${rejectBill.remark }"></c:out>" style="width: 680px;"/></td>
					</tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<input type="hidden" name="goodsjson" id="sales-goodsJson-rejectBillAddPage" />
	    		<table id="sales-datagrid-rejectBillAddPage"></table>
	    	</div>	    	
	    	<input type="hidden" id="sales-printtimes-rejectBillAddPage" value="${rejectBill.printtimes }"/>
	    	<input type="hidden" id="sales-printlimit-rejectBillAddPage" value="${printlimit }"/>
	    </form>
    </div>
  <div class="easyui-menu" id="sales-contextMenu-rejectBillAddPage" style="display: none;">
      <div id="sales-history-price-rejectBillAddPage" data-options="iconCls:'button-view'">查看历史销售价</div>
  </div>
  <div id="rejectBill-goods-history-price"></div>
    <script type="text/javascript">
    	$(function(){
    		$("#sales-datagrid-rejectBillAddPage").datagrid({ //销售商品明细信息编辑
    			authority:wareListJson,
    			columns: wareListJson.common,
    			frozenColumns: wareListJson.frozen,
    			border: true,
    			fit: true,
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
                    $(this).datagrid('selectRow', rowIndex);
                    $("#sales-contextMenu-rejectBillAddPage").menu('show', {
                        left:e.pageX,
                        top:e.pageY
                    });
                }
    		}).datagrid('columnMoving');

            $("#sales-history-price-rejectBillAddPage").click(function(){
                showHistoryGoodsPrice();
            });

			//历史价格查看
			function showHistoryGoodsPrice(){
				var row = $("#sales-datagrid-rejectBillAddPage").datagrid('getSelected');
				if(row == null){
					$.messager.alert("提醒", "请选择一条记录");
					return false;
				}
				var businessdate = $("#sales-businessdate-rejectBillAddPage").val();
				var customerid = $("#sales-customer-showid-hidden-dispatchBillAddPage").val();
				var customername = $("#sales-customer-rejectBillAddPage").customerWidget('getText');
				var goodsid = row.goodsid;
				var goodsname = row.goodsInfo.name;
				$("#rejectBill-goods-history-price").dialog({
					title:'客户['+customerid+'] 商品['+goodsid+']'+goodsname+' 历史价格表',
					width:600,
					height:400,
					closed:false,
					modal:true,
					cache:false,
					maximizable:true,
					resizable:true,
					href:'sales/showRejectBillHistoryGoodsPricePage.do',
					queryParams:{customerid:customerid,goodsid:goodsid,businessdate:businessdate}
				});
			}

    		$("#sales-buttons-rejectBill").buttonWidget("setDataID", {id:'${rejectBill.id}', state:'${rejectBill.status}', type:'view'});
    		<c:if test="${rejectBill.status=='3' }">
				<c:if test="${rejectBill.billtype=='1'}">
				<c:if test="${rejectBill.receiptid==null || rejectBill.receiptid=='' }">
				$("#sales-buttons-rejectBill").buttonWidget("enableButton","button-split");
				</c:if>
				</c:if>
				<c:if test="${rejectBill.receiptid!=null && rejectBill.receiptid!='' }">
				$("#sales-buttons-rejectBill").buttonWidget("disableButton","button-split");
				</c:if>
			</c:if>
			<c:if test="${rejectBill.status!='3' }">
				$("#sales-buttons-rejectBill").buttonWidget("disableButton","button-split");
			</c:if>

            <c:if test="${rejectBill.source=='8' && rejectBill.status=='9' }">
            $("#sales-buttons-rejectBill").buttonWidget("setDataID", {id:'${rejectBill.id}', state:'4', type:'view'});
            $("#sales-buttons-rejectBill").buttonWidget("disableButton","button-audit-phone");
            </c:if>
            <c:if test="${rejectBill.source=='8' && rejectBill.status!='9' }">
            $("#sales-buttons-rejectBill").buttonWidget("setDataID", {id:'${rejectBill.id}', state:'2', type:'view'});
            $("#sales-buttons-rejectBill").buttonWidget("enableButton","button-audit-phone");
            </c:if>
			<c:choose>
		    	<c:when test="${rejectBill.source=='8' && rejectBill.status!='9' }">
					$("#sales-buttons-rejectBill").buttonWidget("disableButton","button-print-sales");
					$("#sales-buttons-rejectBill").buttonWidget("disableButton","button-printview-sales");
		    	</c:when>
		    	<c:otherwise>
					$("#sales-buttons-rejectBill").buttonWidget("enableButton","button-print-sales");
					$("#sales-buttons-rejectBill").buttonWidget("enableButton","button-printview-sales");		    		
		    	</c:otherwise>
		    </c:choose>
			<c:if test="${rejectBill.billtype!='1'}">
				$("#sales-buttons-rejectBill").buttonWidget("disableButton","button-split");
			</c:if>
    	});
    </script>
  </body>
</html>
