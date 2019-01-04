<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>零售订单查看页面</title>
  </head>
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="sales-form-orderAddPage" action="" method="post">
	    	<div data-options="region:'north',border:false" style="height:135px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input type="text" class="len150" readonly="readonly" value="${order.id }" /></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input type="text" id="sales-businessdate-orderAddPage" class="len150" readonly="readonly" value="${order.businessdate }" /></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165">
	    					<select id="sales-status-orderAddPage" disabled="disabled" class="len150">
	    						<c:forEach items="${statusList }" var="list">
	    							<c:choose>
	    								<c:when test="${list.code == order.status}">
	    									<option value="${list.code }" selected="selected">${list.codename }</option>
	    								</c:when>
	    								<c:otherwise>
	    									<option value="${list.code }">${list.codename }</option>
	    								</c:otherwise>
	    							</c:choose>
	    						</c:forEach>
	    						<option value="9" <c:if test="${order.status=='9'}">selected="selected"</c:if>>作废</option>
	    					</select>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">客户：</td>
	    				<td colspan="3"><input type="text" id="sales-customer-orderAddPage" readonly="readonly" text="<c:out value="${order.customername }"></c:out>" value="${order.customerid }" style="width: 300px;"/><span id="sales-customer-showid-orderAddPage" style="margin-left:5px;line-height:25px;">编号：${order.customerid }</span></td>
                        <td class="len80 left">仓库：</td>
                        <td><input readonly="readonly" class="len150" value="<c:out value="${order.storagename }"></c:out>" /></td>
                    </tr>
	    			<tr>
	    				<td class="len80 left">销售部门：</td>
	    				<td><select id="sales-salesDept-orderAddPage" class="len150" disabled="disabled">
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
						</td>
	    				<td class="len80 left">客户业务员：</td>
	    				<td><select id="sales-salesMan-orderAddPage" class="len150" disabled="disabled" /></td>
						<td class="len80 left">车销人员：</td>
						<td><input id="sales-caruser-orderAddPage" class="len150" disabled="disabled" value="${order.caruser}"/></td>
	    			</tr>
					<tr>
						<td class="len80 left">备注：</td>
						<td colspan="5"><input type="text" readonly="readonly" value="<c:out value="${order.remark }"></c:out>" style="width:680px;" /></td>
					</tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<table id="sales-datagrid-orderAddPage"></table>
	    	</div>
	    </form>
    </div>
    <script type="text/javascript">
    	$(function(){
    		$("#sales-datagrid-orderAddPage").datagrid({ //销售商品行编辑
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
    			onLoadSuccess: function(){
    				var rows = $("#sales-datagrid-orderAddPage").datagrid('getRows');
    				var leng = rows.length;
    				if(leng < 12){
    					for(var i=leng; i<12; i++){
    						$("#sales-datagrid-orderAddPage").datagrid('appendRow',{});
    					}
    				}
    				countTotal();
    			}
    		}).datagrid('columnMoving');
    		$("#sales-customer-orderAddPage").customerWidget({ //客户参照窗口
    			name:'t_sales_order',
				col:'customerid',
    			singleSelect:true,
    			width:300
    		});
			$("#sales-caruser-orderAddPage").widget({
				referwid:'RL_T_BASE_PERSONNEL_CARUSER',
				width:150,
				required:true,
				singleSelect:true
			});
    		$.getJSON('basefiles/getPersonnelListByDeptid.do', {deptid: ""}, function(json){
	    		if(json.length>0){
	    			$("#sales-salesMan-orderAddPage").html("");
	    			$("#sales-salesMan-orderAddPage").append("<option value=''></option>");
	    			for(var i=0;i<json.length;i++){
	    				$("#sales-salesMan-orderAddPage").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
	    			}
			  			$("#sales-salesMan-orderAddPage").val("${order.salesuser }");
	    		}	
	    	});
	    	$.getJSON('basefiles/getContacterBy.do', {type:"1", id:"${order.customerid}"}, function(json){
	    		if(json.length>0){
	    			$("#sales-handler-orderAddPage").html("");
	    			$("#sales-handler-orderAddPage").append("<option value=''></option>");
	    			for(var i=0;i<json.length;i++){
	    				$("#sales-handler-orderAddPage").append("<option value='"+json[i].id+"'>"+json[i].name+"</option>");
	    			}
	    			$("#sales-handler-orderAddPage").val("${order.handlerid }");
	    		}
	    	});
    		$("#sales-buttons-orderPage").buttonWidget("setDataID", {id:'${order.id}', state:'${order.status}', type:'view'});
    		<c:if test="${order.status=='9'}">
    			$("#sales-buttons-orderPage").buttonWidget("disableButton", 'button-audit');
    		</c:if>
    		<c:if test="${order.status!='2'}">
    			$("#sales-buttons-orderPage").buttonWidget("disableButton", 'button-auditDemand');
    		</c:if>
    		<c:if test="${order.status=='4'}">
    			$("#sales-buttons-orderPage").buttonWidget("enableButton", 'button-oppaudit');
    		</c:if>
    	});
    </script>
  </body>
</html>
