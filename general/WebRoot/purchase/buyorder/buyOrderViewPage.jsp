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
	  	<form action="purchase/buyorder/addBuyOrder.do" id="purchase-form-buyOrderAddPage" method="post">
	  		<input type="hidden" id="purchase-buyOrderAddPage-addType" name="addType"/>
	  		<div data-options="region:'north',border:false" style="height:130px;">
	  			<table style="border-collapse:collapse;" border="0" cellpadding="4" cellspacing="4">
					<tr>
						<td style="width:80px;">编号：</td>
						<td style="width:165px;"><input type="text" class="len150" value="${buyOrder.id }" readonly="readonly" /></td>
						<td style="width:80px;">业务日期：</td>
						<td style="width:165px;"><input type="text" class="len150" id="purchase-buyOrderAddPage-businessdate" name="buyOrder.businessdate" value="${buyOrder.businessdate }" readonly="readonly"/></td>
						<td style="width:80px;">状态：</td>
				    	<td style="width:165px;">			    		
	    					<select id="purchase-buyOrderAddPage-status" disabled="disabled" class="len150">
	    						<c:forEach items="${statusList }" var="list">
	    							<c:choose>
	    								<c:when test="${list.code == buyOrder.status}">
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
						<td colspan="3"><input type="text" id="purchase-buyOrderAddPage-supplier" style="width:300px;" value="<c:out value="${buyOrder.suppliername }"></c:out>"  title="<c:out value="${buyOrder.suppliername }"></c:out>" readonly="readonly"/>
							<span id="purchase-supplier-showid-buyOrderAddPage" style="margin-left:5px;line-height:25px;">编号：${buyOrder.supplierid }</span>
						</td>
                        <td style="width:60px;">订单追加：</td>
                        <td>
                            <select  class="len150"name="buyOrder.orderappend" disabled="disabled">
                                <option value="0" <c:if test="${buyOrder.orderappend=='0' }">selected="selected"</c:if>>不追加</option>
                                <option value="1" <c:if test="${buyOrder.orderappend=='1' }">selected="selected"</c:if>>追加</option>
                            </select>
                        </td>
                    </tr>
					<tr>
						<td style="">采购部门：</td>
				    	<td>
				    		<select id="purchase-buyOrderAddPage-buydept"  class="len150" disabled="disabled">
					    		<option value=""></option>
					    		<c:forEach items="${buyDept}" var="list">
	    							<c:choose>
	    								<c:when test="${list.id == buyOrder.buydeptid}">
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
							<input type="text" id="purchase-buyOrderAddPage-buyuser" name="buyOrder.buyuserid" readonly="readonly" value="${buyOrder.buyuserid }" />
						</td>
                        <td style="">入库仓库：</td>
                        <td><input type="text" id="purchase-buyOrderAddPage-storage" name="buyOrder.storageid" value="${buyOrder.storageid }" readonly="readonly"/></td>
					</tr>
                    <tr>
                        <td style="width:60px;">备注：</td>
                        <td colspan="3">
                            <input type="text" style="width:412px;" name="buyOrder.remark" value="${buyOrder.remark}"/>
                        </td>
                        <td>到货日期：</td>
                        <td>
                            <input type="text" class="len150" id="purchase-buyOrderAddPage-arrivedate" name="buyOrder.arrivedate" value="${buyOrder.arrivedate }" readonly="readonly"/>
                        </td>
                    </tr>
				</table>
	  		</div>
	  		<div data-options="region:'center',border:false">
	  			<table id="purchase-buyOrderAddPage-buyOrdertable"></table>
				<input type="hidden" id="purchase-buyOrderAddPage-buyOrderDetails" name="buyOrderDetails"/>
	  		</div>
	  		<input type="hidden" name="buyOrder.id" value="${buyOrder.id }" />
	  		<input type="hidden" id="purchase-buyOrderAddPage-field08" name="buyOrder.field08" value='${buyOrder.field08 }' />	
    		<input type="hidden" id="purchase-buyOrderAddPage-printtimes" value="${buyOrder.printtimes}" />	
	  	</form> 
	  </div>
  </div>
  <script type="text/javascript">
  	$(document).ready(function(){
  		$("#purchase-buttons-buyOrderPage").buttonWidget("setDataID",  {id:'${buyOrder.id}',state:'${buyOrder.status}',type:'view'});
  	  	var $buyOrdertable=$("#purchase-buyOrderAddPage-buyOrdertable");
  	  	$buyOrdertable.datagrid({
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
		  		var dataRows=$buyOrdertable.datagrid('getRows');
		  		var rowlen=dataRows.length;
		  	  	if(rowlen<12){
			  	  	for(var i=rowlen;i<12;i++){
			  	  		$buyOrdertable.datagrid('appendRow', {});
			  	  	}
				}
  	 			$buyOrdertable.datagrid('reloadFooter',[
					{goodsid: '合计', amount: '0',taxprice:'0',notaxprice:'0',notaxamount:'0',taxamount:'0',tax:'0'}
				]);
  	 			footerReCalc();
  	 		},
        	onDblClickRow: function(rowIndex, rowData){ //选中行
  	 			if(rowData.goodsid && rowData.goodsid!=""){
  	 				//orderDetailViewDialog(rowData);
  	 			}
        	},
            onSortColumn:function(sort, order){
                return detailOnSortColumn(sort,order);
            }
  	  	}).datagrid("columnMoving");

		$("#purchase-buyOrderAddPage-buyuser").widget({
			name:'t_base_buy_supplier',
			col:'buyuserid',
			width:150,
    		async:false,
			singleSelect:true,
			onlyLeafCheck:false
		});
		
		$.getJSON('basefiles/getContacterBy.do', {type:"2", id:"${buyOrder.supplierid}"}, function(json){
			if(json.length>0){
				$("#purchase-buyOrderAddPage-handler").html("");
				$("#purchase-buyOrderAddPage-handler").append("<option value=''></option>");
				for(var i=0;i<json.length;i++){
					$("#purchase-buyOrderAddPage-handler").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
				}
				$("#purchase-buyOrderAddPage-handler").val("${buyOrder.handlerid}");
			}
		});


		$("#purchase-buyOrderAddPage-supplier").widget({ 
			name:'t_purchase_buyorder',
			col:'supplierid',
			width:320,
			readonly:true,
			singleSelect:true,
			onlyLeafCheck:true,
			initValue:'${buyOrder.supplierid}'
		});	
		
		$("#purchase-buyOrderAddPage-storage").widget({ 
			name:'t_purchase_buyorder',
			col:'storageid',
			width:150,
			readonly:true,
			initValue:'${buyOrder.storageid}',
			singleSelect:true,
			onlyLeafCheck:true
		});
  	});
  </script>
  </body>
</html>
