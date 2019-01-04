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
  <div class="easyui-panel" title="" data-options="fit:true,border:false">
	  <div class="easyui-layout" data-options="fit:true">
	  	<form action="purchase/planorder/addPlannedOrder.do" id="purchase-form-plannedOrderAddPage" method="post">
	  		<input type="hidden" id="purchase-plannedOrderAddPage-addType" name="addType"/>
	  		<div data-options="region:'north',border:false" style="height:130px;">
	  			<table style="border-collapse:collapse;" border="0" cellpadding="4" cellspacing="4">
					<tr>
						<td style="width:60px;text-align: left;">编号：</td>
						<td style="width:165px;"><input type="text" class="len150" readonly="readonly" name="plannedOrder.id" value="${plannedOrder.id }" /></td>
						<td style="width:80px;text-align: left;">业务日期：</td>
						<td style="width:165px;"><input class="len150" id="purchase-plannedOrderAddPage-businessdate" type="text" value='${plannedOrder.businessdate }' readonly="readonly"/></td>
						<td style="width:80px;text-align: left;">状态：</td>
				    	<td style="width:165px;">			    		
	    					<select id="purchase-plannedOrderAddPage-status" disabled="disabled" class="len150">
	    						<c:forEach items="${statusList }" var="list">
	    							<c:choose>
	    								<c:when test="${list.code == plannedOrder.status}">
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
						<td colspan="3">
							<input type="text" id="purchase-plannedOrderAddPage-supplier" style="width:320px;" readonly="readonly" value="<c:out value="${plannedOrder.suppliername }"></c:out>" title="<c:out value="${plannedOrder.suppliername }"></c:out>"/>
							<span id="purchase-supplier-showid-plannedOrderAddPage" style="margin-left:5px;line-height:25px;">编号：${plannedOrder.supplierid }</span>
						</td>
                        <td style="">入库仓库：</td>
                        <td><input type="text" id="purchase-plannedOrderAddPage-storage" name="plannedOrder.storageid" value="${plannedOrder.storageid }" readonly="readonly" /></td>
					</tr>
					<tr>
						<td style="text-align: left;">采购部门：</td>
				    	<td>
				    		<select id="purchase-plannedOrderAddPage-buydept" class="len150" disabled="disabled">
				    			<option value=""></option>
	    						<c:forEach items="${buyDept}" var="list">
	    							<c:choose>
	    								<c:when test="${list.id == plannedOrder.buydeptid}">
	    									<option value="${list.id }" selected="selected">${list.name }</option>
	    								</c:when>
	    								<c:otherwise>
	    									<option value="${list.id }">${list.name }</option>
	    								</c:otherwise>
	    							</c:choose>
	    						</c:forEach>
				    		</select>
						</td>
						<td style="text-align: left;">采购人员：</td>
				    	<td>				    						    		
				    		<input type="text" id="purchase-plannedOrderAddPage-buyuser" name="plannedOrder.buyuserid" readonly="readonly" value="${ plannedOrder.buyuserid}" />
				    	</td>
				    	<td style="width:60px;">订单追加：</td>
						<td>						
	    					<select id="purchase-plannedOrderAddPage-orderappend" class="len150" disabled="disabled">
	    						<option value="0" <c:if test="${plannedOrder.orderappend=='0' }">selected="selected"</c:if>>不追加</option>
	    						<option value="1" <c:if test="${plannedOrder.orderappend=='1' }">selected="selected"</c:if>>追加</option>
	    					</select>
						</td>
					</tr>
					<tr>
                        <td style="width:60px;text-align: left;">备注：</td>
                        <td colspan="3">
                            <input type="text" style="width:412px;" name="plannedOrder.remark" value="<c:out value="${plannedOrder.remark }"></c:out>"/>
                        </td>
                        <td>到货日期：</td>
                        <td>
                            <input type="text" class="len150" id="purchase-plannedOrderAddPage-arrivedate" name="plannedOrder.arrivedate"
                                   value="${plannedOrder.arrivedate }" readonly="readonly" />
                        </td>
					</tr>
				</table>
	  		</div>
	  		<div data-options="region:'center',border:false">
	  			<table id="purchase-plannedOrderAddPage-plannedOrdertable"></table>
				<input type="hidden" id="purchase-plannedOrderAddPage-plannedOrderDetails" name="plannedOrderDetails"/>
	  		</div>
	  	</form>
	  	<input type="hidden" id="purchase-plannedOrderAddPage-formModStatus" value="0"/> 
	  		<input type="hidden" id="purchase-plannedOrderAddPage-field08" name="buyOrder.field08" value='${plannedOrder.field08 }' />
	  </div>
  </div>
  <script type="text/javascript">
  	
  	$(document).ready(function(){
  		$("#purchase-buttons-plannedOrderPage").buttonWidget("setDataID",  {id:'${plannedOrder.id}',state:'${plannedOrder.status}',type:'view'});
  	  	var $plannedOrdertable=$("#purchase-plannedOrderAddPage-plannedOrdertable");
  	  	$plannedOrdertable.datagrid({
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
	  	  		var $potable=$("#purchase-plannedOrderAddPage-plannedOrdertable");
	  	  		var dataRows=$potable.datagrid('getRows');
	  	  		var rowlen=dataRows.length;
		  	  	if(rowlen<12){
			  	  	for(var i=rowlen;i<12;i++){
						$plannedOrdertable.datagrid('appendRow', {});
			  	  	}
				}
  	  			footerReCalc();
  	 		},
            onSortColumn:function(sort, order){
                return detailOnSortColumn(sort,order);
            }
  	  	}).datagrid("columnMoving");


		$("#purchase-plannedOrderAddPage-buyuser").widget({
			name:'t_base_buy_supplier',
			col:'buyuserid',
			width:150,
    		async:false,
			singleSelect:true,
			onlyLeafCheck:false
		});
		
		$.getJSON('basefiles/getContacterBy.do', {type:"2", id:"${plannedOrder.supplierid}"}, function(json){
			if(json.length>0){
				$("#purchase-plannedOrderAddPage-handler").html("");
				$("#purchase-plannedOrderAddPage-handler").append("<option value=''></option>");
				for(var i=0;i<json.length;i++){
					$("#purchase-plannedOrderAddPage-handler").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
				}
				$("#purchase-plannedOrderAddPage-handler").val("${plannedOrder.handlerid}");
			}
		});
		$("#purchase-plannedOrderAddPage-supplier").widget({ 
			name:'t_purchase_buyorder',
			col:'supplierid',
			width:320,
			readonly:true,
			singleSelect:true,
			onlyLeafCheck:true,
			initValue:'${plannedOrder.supplierid}'
		});	
		$("#purchase-plannedOrderAddPage-storage").widget({ 
			name:'t_purchase_plannedorder',
			col:'storageid',
			width:150,
			readonly:true,
			initValue:'${plannedOrder.storageid}',
			singleSelect:true,
			onlyLeafCheck:true
		});
  	});
  </script>
  </body>
</html>
