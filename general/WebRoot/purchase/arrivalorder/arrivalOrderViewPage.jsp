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
	  	<form id="purchase-form-arrivalOrderAddPage" method="post">
	  		<input type="hidden" id="purchase-arrivalOrderAddPage-addType" name="addType"/>
	  		<input type="hidden" id="purchase-arrivalOrderAddPage-saveaudit" name="saveaudit"/>
	  		<div data-options="region:'north',border:false" style="height:110px;">
	  			<table style="border-collapse:collapse;" border="0" cellpadding="5" cellspacing="5">
					<tr>
						<td style="width:80px;">编号：</td>
						<td style="width:165px;"><input type="text" style="width:150px;" value="${arrivalOrder.id }" readonly="readonly" /></td>
						<td style="width:80px;">业务日期：</td>
						<td style="width:165px;"><input type="text" id="purchase-arrivalOrderAddPage-businessdate" name="arrivalOrder.businessdate" value="${arrivalOrder.businessdate }" style="width:130px;" readonly="readonly"/></td>
						<td style="width:80px;">状态：</td>
				    	<td style="width:165px;">			    		
	    					<select id="purchase-arrivalOrderAddPage-status"  disabled="disabled" class="len136">
	    						<c:forEach items="${statusList }" var="list">
	    							<c:choose>
	    								<c:when test="${list.code == arrivalOrder.status}">
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
						<td style="">供应商：</td>
						<td colspan="3"><input type="text" id="purchase-arrivalOrderAddPage-supplier" style="width:300px;" name="arrivalOrder.supplierid" value="<c:out value="${arrivalOrder.suppliername }"></c:out>" title="<c:out value="${arrivalOrder.suppliername }"></c:out>" readonly="readonly"/>
							<span id="purchase-supplier-showid-arrivalOrderAddPage" style="margin-left:5px;line-height:25px;">编号：${arrivalOrder.supplierid }</span>
						</td>
                        <td style="">入库仓库：</td>
                        <td><input type="text" id="purchase-arrivalOrderAddPage-storage" name="arrivalOrder.storageid" style="width:135px;" readonly="readonly"/></td>
					</tr>
					<tr>
						<td style="">采购部门：</td>
				    	<td>
				    		<select id="purchase-arrivalOrderAddPage-buydept" style="width:150px;" disabled="disabled">
					    		<option value=""></option>
					    		<c:forEach items="${buyDept}" var="list">
	    							<c:choose>
	    								<c:when test="${list.id == arrivalOrder.buydeptid}">
	    									<option value="${list.id }" selected="selected">${list.name }</option>
	    								</c:when>
	    								<c:otherwise>
	    									<option value="${list.id }">${list.name }</option>
	    								</c:otherwise>
	    							</c:choose>
	    						</c:forEach>
    						</select>
						</td>
						<td style="">采购员：</td>
						<td>
							<input type="text" id="purchase-arrivalOrderAddPage-buyuser" name="arrivalOrder.buyuserid" style="width:130px;" readonly="readonly" value="${arrivalOrder.buyuserid }" />
						</td>
                        <td>备注：</td>
                        <td>
                            <input type="text" style="width:135px;" name="arrivalOrder.remark" value="<c:out value="${arrivalOrder.remark}"></c:out>" readonly="readonly"/>
                        </td>
                    </tr>
				</table>
	  		</div>
	  		<div data-options="region:'center',border:false">
	  			<table id="purchase-arrivalOrderAddPage-arrivalOrdertable"></table>
				<input type="hidden" id="purchase-arrivalOrderAddPage-arrivalOrderDetails" name="arrivalOrderDetails"/>
	  		</div>
	  		<input type="hidden" name="arrivalOrder.id" value="${arrivalOrder.id }" />	
	  		<input type="hidden" id="purchase-arrivalOrderAddPage-printtimes" value="${arrivalOrder.printtimes}" />	
	  		<input type="hidden" id="purchase-arrivalOrderAddPage-printlimit" value="${printlimit}" />
	  		<input type="hidden" id="purchase-arrivalOrderAddPage-isrefer" value="${arrivalOrder.isrefer }" />	
	  	</form> 
	  </div>
  </div>
  <script type="text/javascript">
  
  	$(document).ready(function(){
		var updateflag=${updateflag};
		$("#button-relateMenu").menu('disableItem', '#purchase-change');
		$("#button-relateMenu").menu('disableItem', '#purchase-view');
		$("#button-relateMenu").menu('disableItem', '#purchase-cancaldispense');
		if(updateflag){
			$("#button-relateMenu").menu('enableItem', '#purchase-view');
			$("#button-relateMenu").menu('enableItem', '#purchase-cancaldispense');
		}

  		$("#purchase-buttons-arrivalOrderPage").buttonWidget("setDataID",  {id:'${arrivalOrder.id}',state:'${arrivalOrder.status}',type:'view'});
  		<c:if test="${arrivalOrder.status!='3' and arrivalOrder.status!='4'}">
			$("#purchase-buttons-arrivalOrderPage").buttonWidget("disableButton","button-print");
			$("#purchase-buttons-arrivalOrderPage").buttonWidget("disableButton","button-preview");
		</c:if>
  		<c:if test="${arrivalOrder.status=='3' or arrivalOrder.status=='4'}">
  			$("#purchase-buttons-arrivalOrderPage").buttonWidget("enableButton","button-print");
  			$("#purchase-buttons-arrivalOrderPage").buttonWidget("enableButton","button-preview");
  		</c:if>
  		<c:if test="${arrivalOrder.isrefer == '1'}">
			$("#purchase-buttons-arrivalOrderPage").buttonWidget("disableButton","button-delete"); 	
			$("#purchase-buttons-arrivalOrderPage").buttonWidget("disableButton","button-oppaudit");	
  		</c:if>
  	  	var $arrivalOrdertable=$("#purchase-arrivalOrderAddPage-arrivalOrdertable");
  	  	$arrivalOrdertable.datagrid({
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
		  		var dataRows=$arrivalOrdertable.datagrid('getRows');
		  		var rowlen=dataRows.length;
		  	  	if(rowlen<12){
			  	  	for(var i=rowlen;i<12;i++){
			  	  		$arrivalOrdertable.datagrid('appendRow', {});
			  	  	}
				}
  	 			footerReCalc();
  	 		},
        	onDblClickRow: function(rowIndex, rowData){ //选中行
  	 			if(rowData.goodsid && rowData.goodsid!=""){
  	 				orderDetailViewDialog(rowData);
  	 			}
        	},
            onSortColumn:function(sort, order){
                return detailOnSortColumn(sort,order);
            }
  	  	}).datagrid("columnMoving");

  	  $("#purchase-arrivalOrderAddPage-buyuser").widget({
			name:'t_base_buy_supplier',
			col:'buyuserid',
			width:130,
  			async:false,
			singleSelect:true,
			onlyLeafCheck:false
		});
		
		$("#purchase-arrivalOrderAddPage-storage").widget({
			name:'t_purchase_arrivalorder',
			col:'storageid',
			width:135,
			readonly:true,
			initValue:'${arrivalOrder.storageid}',
			singleSelect:true,
			onlyLeafCheck:true
		});
  	});
  </script>
  </body>
</html>

