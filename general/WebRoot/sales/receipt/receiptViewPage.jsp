<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售回单查看页面</title>
  </head>
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="sales-form-receiptAddPage" action="" method="post">
	    	<div data-options="region:'north',border:false" style="height:110px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input class="len150 easyui-validatebox" value="${receipt.id }" readonly="readonly" /></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input id="sales-businessdate-receiptAddPage" type="text" class="len130" value="${receipt.businessdate }" readonly="readonly" /></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165">
							<select disabled="disabled" class="len136">
	    						<c:forEach items="${status }" var="list">
	    							<c:choose>
	    								<c:when test="${list.code == receipt.status}">
	    									<option value="${list.code }" selected="selected">${list.codename }</option>
	    								</c:when>
	    								<c:otherwise>
	    									<option value="${list.code }">${list.codename }</option>
	    								</c:otherwise>
	    							</c:choose>
	    						</c:forEach>
	    					</select>
	    					<input id="sales-saleorderid-receiptAddPage" type="hidden" value="${receipt.saleorderid }"/>
						</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">客户：</td>
	    				<td colspan="3"><input type="text" value="<c:out value="${receipt.customername }"></c:out>" style="width: 300px;" readonly="readonly" /><span id="sales-customer-showid-receiptAddPage" style="margin-left:5px;line-height:25px;">编号：${receipt.customerid }</span>
                            <input type="hidden" id="sales-customer-receiptAddPage" value="${receipt.customerid }"/>
                        </td>
                        <td>销售订单：</td>
                        <td><input class="len136" value="${receipt.saleorderid }" readonly="readonly" title="${receipt.saleorderid }"/></td>
                    </tr>
	    			<tr>
	    				<td class="len80 left">销售部门：</td>
	    				<td><input id="sales-salesDept-receiptAddPage" class="len150" value="<c:out value="${receipt.salesdeptname }"></c:out>" readonly="readonly" /></td>
                        <td  class="len80 left">客户单号：</td>
                        <td><input type="text" name="receipt.sourceid" value="<c:out value="${receipt.sourceid }"></c:out>" class="len130"  /></td>
                        <td class="len80 left">备注：</td>
                        <td><input type="text" value="<c:out value="${receipt.remark }"></c:out>" style="width: 136px;" readonly="readonly" /></td>
	    			</tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<input type="hidden" name="goodsjson" id="sales-goodsJson-receiptAddPage" />
	    		<table id="sales-datagrid-receiptAddPage"></table>
	    	</div>
			<input type="hidden" id="sales-printtimes-receiptAddPage" value="${receipt.printtimes }"/>
			<input type="hidden" id="sales-printlimit-receiptAddPage" value="${printlimit }"/>
	    </form>
    </div>
    <a href="javaScript:void(0);" id="sales-export-receiptPage" style="display: none"></a>
    <script type="text/javascript">
    	$(function(){
    		$("#sales-datagrid-receiptAddPage").datagrid({ //销售商品行编辑
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
    				if(leng > 0){
    					$("#sales-parentid-receiptPage").val(rows[0].billno);
    				}
    				if(leng < 12){
    					for(var i=leng; i<12; i++){
    						$("#sales-datagrid-receiptAddPage").datagrid('appendRow',{});
    					}
    				}
    				countTotal();
    			},
                onSortColumn:function(sort, order){
                    var rows = $("#sales-datagrid-receiptAddPage").datagrid("getRows");
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
                    $("#sales-datagrid-receiptAddPage").datagrid("loadData",dataArr);
                    return false;
                }
    		}).datagrid('columnMoving');
    		$("#sales-buttons-receiptPage").buttonWidget("setDataID", {id:'${receipt.id}', state:'${receipt.status}', type:'view'});
    	});
    	<c:if test="${receipt.status!='2' }">
    		$("#sales-buttons-receiptPage").buttonWidget("disableButton", 'cancel-check');
    		$("#sales-buttons-receiptPage").buttonWidget("disableButton", 'relation-rejectbill');
    	</c:if>
    	<c:if test="${receipt.status=='2' }">
    		$("#sales-buttons-receiptPage").buttonWidget("enableButton", 'cancel-check');
    		$("#sales-buttons-receiptPage").buttonWidget("enableButton", 'relation-rejectbill');
    	</c:if>
        <c:choose>
            <c:when test="${ receipt.status=='3'&& receipt.isinvoice == '3'}">
                $("#sales-buttons-receiptPage").buttonWidget("enableButton", 'receipt-writeoff');
            </c:when>
            <c:otherwise>
                $("#sales-buttons-receiptPage").buttonWidget("disableButton", 'receipt-writeoff');
            </c:otherwise>
        </c:choose>
    	$("#sales-parentid-receiptPage").val("${receipt.billno}");
    </script>
  </body>
</html>
