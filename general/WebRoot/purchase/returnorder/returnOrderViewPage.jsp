<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>查看</title>
    <%@include file="/include.jsp" %>
  </head>
  <body>  
  <div class="easyui-panel" data-options="fit:true,border:false">
	  <div class="easyui-layout" data-options="fit:true">
	  	<form id="purchase-form-returnOrderAddPage" method="post">
	  		<input type="hidden" id="purchase-returnOrderAddPage-addType" name="addType"/>
	  		<input type="hidden" id="purchase-returnOrderAddPage-saveaudit" name="saveaudit"/>
	  		<div data-options="region:'north',border:false" style="height:100px;">
	  			<table style="border-collapse:collapse;" border="0" cellpadding="5" cellspacing="5">
					<tr>
						<td style="width:80px;">编号：</td>
						<td style="width:165px;"><input type="text" class="len150" value="${returnOrder.id }" readonly="readonly" /></td>
						<td style="width:80px;">业务日期：</td>
						<td style="width:165px;"><input type="text" class="len150" id="purchase-returnOrderAddPage-businessdate" name="returnOrder.businessdate" value="${returnOrder.businessdate }" readonly="readonly"/></td>
						<td style="width:80px;">状态：</td>
				    	<td style="width:165px;">			    		
	    					<select id="purchase-returnOrderAddPage-status" disabled="disabled" class="len150">
	    						<c:forEach items="${statusList }" var="list">
	    							<c:choose>
	    								<c:when test="${list.code == returnOrder.status}">
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
						<td>供应商：</td>
						<td colspan="3"><input type="text" id="purchase-returnOrderAddPage-supplier" style="width:320px;" name="returnOrder.supplierid" value="${returnOrder.supplierid }"  title="<c:out value="${returnOrder.suppliername}"></c:out>" text="<c:out value="${returnOrder.suppliername}"></c:out>" readonly="readonly"/>
							<span id="purchase-supplier-showid-returnOrderAddPage" style="margin-left:5px;line-height:25px;">编号：${returnOrder.supplierid }</span>
						</td>
                        <td>退货仓库：</td>
                        <td><input type="text" id="purchase-returnOrderViewPage-storage" name="returnOrder.storageid" readonly="readonly"/></td>
					</tr>
					<tr>
						<td>采购部门：</td>
				    	<td>
				    		<select id="purchase-returnOrderAddPage-buydept" class="len150" disabled="disabled">
					    		<option value=""></option>
					    		<c:forEach items="${buyDept}" var="list">
	    							<c:choose>
	    								<c:when test="${list.id == returnOrder.buydeptid}">
	    									<option value="${list.id }" selected="selected">${list.name }</option>
	    								</c:when>
	    								<c:otherwise>
	    									<option value="${list.id }">${list.name }</option>
	    								</c:otherwise>
	    							</c:choose>
	    						</c:forEach>
    						</select>
						</td>
						<td>采购员：</td>
						<td>
							<input type="text" id="purchase-returnOrderAddPage-buyuser" name="returnOrder.buyuserid" readonly="readonly" value="${ returnOrder.buyuserid }" />
						</td>
                        <td>备注：</td>
                        <td>
                            <input type="text" style="width:150px;" name="returnOrder.remark" value="<c:out value="${returnOrder.remark}"></c:out>" readonly="readonly"/>
                        </td>
					</tr>
				</table>
	  		</div>
	  		<div data-options="region:'center',border:false">
	  			<table id="purchase-returnOrderAddPage-returnOrdertable"></table>
				<input type="hidden" id="purchase-returnOrderAddPage-returnOrderDetails" name="returnOrderDetails"/>
	  		</div>
	  		<input type="hidden" name="returnOrder.id" value="${returnOrder.id }" />
    		<input type="hidden" id="purchase-returnOrderAddPage-printtimes" value="${returnOrder.printtimes}" />		
	  	</form> 
	  </div>
  </div>
  <script type="text/javascript">
  	$(document).ready(function(){
  		$("#purchase-buttons-returnOrderPage").buttonWidget("setDataID",  {id:'${returnOrder.id}',state:'${returnOrder.status}',type:'view'});

  		<c:if test="${returnOrder.ckstatus == '1'}">
			$("#purchase-buttons-returnOrderPage").buttonWidget("disableButton","button-delete"); 	
			$("#purchase-buttons-returnOrderPage").buttonWidget("disableButton","button-oppaudit");	
		</c:if>
  	  	var $returnOrdertable=$("#purchase-returnOrderAddPage-returnOrdertable");
  	  	$returnOrdertable.datagrid({
	  	  	fit:true,
	  	  	striped:true,
	 		method:'post',
	 		rownumbers:true,
	 		//idField:'id',
	 		singleSelect:true,
	 		showFooter:true,
  	 		data: JSON.parse('${goodsDataList}'),
  	 		authority:tableColJson,
  	 		frozenColumns: tableColJson.frozen,
			columns:tableColJson.common,
  	 		onLoadSuccess:function(){
		  		var dataRows=$returnOrdertable.datagrid('getRows');
		  		var rowlen=dataRows.length;
		  	  	if(rowlen<12){
			  	  	for(var i=rowlen;i<12;i++){
			  	  		$returnOrdertable.datagrid('appendRow', {});
			  	  	}
				}
  	 			$returnOrdertable.datagrid('reloadFooter',[
					{goodsid: '合计', amount: '0',taxprice:'0',notaxprice:'0',notaxamount:'0',taxamount:'0',tax:'0'}
				]);
  	 			footerReCalc();
  	 		},
        	onDblClickRow: function(rowIndex, rowData){ //选中行
        	},
            onSortColumn:function(sort, order){
                return detailOnSortColumn(sort,order);
            }
  	  	}).datagrid("columnMoving");


  	  $("#purchase-returnOrderAddPage-supplier").supplierWidget({ 
		});
		

		$("#purchase-returnOrderAddPage-buyuser").widget({
			name:'t_base_buy_supplier',
			col:'buyuserid',
			width:150,
  			async:false,
			singleSelect:true,
			onlyLeafCheck:false
		});
		
		$("#purchase-returnOrderViewPage-storage").widget({
			name:'t_purchase_returnorder',
			col:'storageid',
			width:150,
			readonly:true,
			initValue:'${returnOrder.storageid}',
			singleSelect:true,
			onlyLeafCheck:true
		});
  	});
  </script>
  </body>
</html>

