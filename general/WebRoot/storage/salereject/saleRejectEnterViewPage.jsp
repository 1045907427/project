<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售退货入库单新增页面</title>
  </head>  
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="storage-form-saleRejectEnterAdd"  method="post">
	    	<div data-options="region:'north',border:false" style="height:140px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input class="len150 easyui-validatebox" name="saleRejectEnter.id" value="${saleRejectEnter.id }"/></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input id="storage-saleRejectEnter-businessdate" type="text" class="len130" value="${saleRejectEnter.businessdate }" name="saleRejectEnter.businessdate" /></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165">
	    					<select id="storage-saleRejectEnter-status" disabled="disabled" class="len130">
	    						<c:forEach items="${statusList }" var="list">
	    							<c:choose>
	    								<c:when test="${list.code == saleRejectEnter.status}">
	    									<option value="${list.code }" selected="selected">${list.codename }</option>
	    								</c:when>
	    								<c:otherwise>
	    									<option value="${list.code }">${list.codename }</option>
	    								</c:otherwise>
	    							</c:choose>
	    						</c:forEach>
    						</select>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">客户：</td>
	    				<td colspan="3" style="text-align: left;"><input type="text" style="width: 300px;" id="storage-saleRejectEnter-customerid" name="saleRejectEnter.customerid" value="<c:out value="${saleRejectEnter.customername}"></c:out>" readonly="readonly"/>
	    					<span id="storage-supplier-showid-saleRejectEnter" style="margin-left:5px;line-height:25px;">编号：${saleRejectEnter.customerid}</span>
	    				</td>
	    				<td class="len80 left">司机：</td>
	    				<td><input class="len130" name="saleRejectEnter.driverid" value="<c:out value="${saleRejectEnter.drivername }"></c:out>" readonly="readonly"/></td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">入库仓库：</td>
	    				<td><input id="sales-storage-saleRejectEnterAddPage" class="len150" name="saleRejectEnter.storageid" value="<c:out value="${saleRejectEnter.storagename }"></c:out>" readonly="readonly"/></td>
	    				<td class="len80 left">销售部门：</td>
	    				<td><input id="sales-salesDept-saleRejectEnterAddPage"name="saleRejectEnter.salesdept" value="<c:out value="${saleRejectEnter.salesdeptname }"></c:out>" readonly="readonly"/></td>
	    				<td class="len80 left">客户业务员：</td>
	    				<td><input id="sales-salesMan-saleRejectEnterAddPage" class="len130" name="saleRejectEnter.salesuser" value="<c:out value="${saleRejectEnter.saleusername }"></c:out>" readonly="readonly"/></td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">退货类型：</td>
	    				<td>
	    					<select class="len150" disabled="disabled">
	    						<option value="0" <c:if test="${saleRejectEnter.sourcetype=='0'}">selected="selected"</c:if>>无</option>
	    						<option value="1" <c:if test="${saleRejectEnter.sourcetype=='1'}">selected="selected"</c:if>>售后退货</option>
	    						<option value="2" <c:if test="${saleRejectEnter.sourcetype=='2'}">selected="selected"</c:if>>直退退货</option>
	    					</select>
	    					<input type="hidden" id="storage-saleRejectEnter-sourcetype" name="saleRejectEnter.sourcetype" value="${saleRejectEnter.sourcetype}"/>
	    					<input type="hidden" id="storage-saleRejectEnter-sourceid" name="saleRejectEnter.sourceid" value="${saleRejectEnter.sourceid}"/>
	    				</td>
	    				<td class="len80 left">备注：</td>
	    				<td colspan="3" style="text-align: left;"><input type="text" name="saleRejectEnter.remark" style="width:395px;" value="<c:out value="${saleRejectEnter.remark }"></c:out>" readonly="readonly"/></td>
	    			</tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<input type="hidden" name="detailJson" id="storage-purchaseEnter-saleRejectEnterDetail" />
	    		<table id="storage-datagrid-saleRejectEnterAddPage"></table>
	    	</div>
    		<input type="hidden" id="storage-printtimes-saleRejectEnterAddPage" value="${saleRejectEnter.printtimes }"/>
    		<input type="hidden" id="storage-printlimit-saleRejectEnterAddPage" value="${printlimit }"/>
	    	<input type="hidden" id="storage-status-saleRejectEnterAddPage" value="${ saleRejectEnter.status}"/>
	    	<input type="hidden" id="storage-ischeck-saleRejectEnterAddPage" value="${ saleRejectEnter.ischeck}"/>
	    </form>
    </div>
    <div id="storage-dialog-saleRejectEnterAddPage"></div>
    <script type="text/javascript">
    	$(function(){
    		$("#storage-datagrid-saleRejectEnterAddPage").datagrid({ //销售商品明细信息编辑
    			authority:tableColJson,
    			columns: tableColJson.common,
    			frozenColumns: tableColJson.frozen,
    			border: true,
    			fit: true,
    			rownumbers: true,
    			showFooter: true,
    			striped:true,
    			singleSelect: true,
    			data:JSON.parse('${detailList}'),
    			onLoadSuccess:function(data){
    				if(data.rows.length<10){
	            		var j = 10-data.rows.length;
	            		for(var i=0;i<j;i++){
	            			$("#storage-datagrid-saleRejectEnterAddPage").datagrid('appendRow',{});
	            		}
   					}else{
   						$("#storage-datagrid-saleRejectEnterAddPage").datagrid('appendRow',{});
   					}
    				countTotal();
    			}
    		}).datagrid('columnMoving');
    	});
    	//控制按钮状态
    	$("#storage-buttons-saleRejectEnterPage").buttonWidget("setDataID",{id:'${saleRejectEnter.id}',state:'${saleRejectEnter.status}',type:'view'});
    	$("#storage-hidden-billid").val("${saleRejectEnter.id}");
    	<c:if test="${saleRejectEnter.sourcetype=='0'}">
	    	$("#storage-buttons-saleRejectEnterPage").buttonWidget("disableMenuItem","relation-upper-view");
	    </c:if>
	    <c:if test="${saleRejectEnter.sourcetype!='0'}">
	    	$("#storage-buttons-saleRejectEnterPage").buttonWidget("enableMenuItem","relation-upper-view");
	    </c:if>
	    $("#sales-contextMenu-saleRejectEnterAddPage").buttonWidget("disableButton", 'button-check');
	     <c:if test="${saleRejectEnter.status=='3' || saleRejectEnter.status=='4'}">
	    $("#sales-contextMenu-saleRejectEnterAddPage").buttonWidget("enableButton", 'button-oppaudit');
	    </c:if>
		<c:if test="${saleRejectEnter.status!='4' || saleRejectEnter.ischeck!='1' }">
			$("#sales-contextMenu-saleRejectEnterAddPage").buttonWidget("disableButton","button-print");
			$("#sales-contextMenu-saleRejectEnterAddPage").buttonWidget("disableButton","button-preview");
		</c:if>
	    <c:if test="${saleRejectEnter.status=='4' && saleRejectEnter.ischeck=='1' }">
			$("#sales-contextMenu-saleRejectEnterAddPage").buttonWidget("enableButton","button-print");
			$("#sales-contextMenu-saleRejectEnterAddPage").buttonWidget("enableButton","button-preview");
		</c:if>
    </script>
  </body>
</html>
