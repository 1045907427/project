<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>调账单</title>
  </head>
  
  <body>
  	<form action="" method="post" id="storage-form-id-adjustmentsPage">
  		<input type="hidden" name="id" value="${adjustments.id }"/>
  	</form>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<div data-options="region:'north',border:false" style="height:100px;">
    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
    			<tr>
    				<td class="len80 left">编号：</td>
    				<td class="len165"><input class="len150 easyui-validatebox" name="adjustments.id" readonly='readonly' value="${adjustments.id}" /></td>
    				<td class="len80 left">业务日期：</td>
    				<td class="len165"><input type="text" class="len150"  value="${adjustments.businessdate}" name="adjustments.businessdate" readonly="readonly"/></td>
    				<td class="len80 left">状态：</td>
    				<td class="len165">
    					<select id="storage-adjustments-status" disabled="disabled" class="len150">
    						<c:forEach items="${statusList }" var="list">
    							<c:choose>
    								<c:when test="${list.code == adjustments.status}">
    									<option value="${list.code }" selected="selected">${list.codename }</option>
    								</c:when>
    								<c:otherwise>
    									<option value="${list.code }">${list.codename }</option>
    								</c:otherwise>
    							</c:choose>
    						</c:forEach>
    					</select>
    					<input type="hidden" name="adjustments.status" value="${adjustments.status }"/>
    				</td>
    			</tr>
    			<tr>
    				<td class="len80 left">调账仓库：</td>
    				<td><input type="text" id="storage-adjustments-storageid" name="adjustments.storageid" class="len150" required="required" value="${adjustments.storageid }" readonly="readonly"/></td>
    				<td>盘点单号:</td>
    				<td>
    					<input type="text" class="len150" value="${adjustments.sourceid }" readonly="readonly"/>
    				</td>
    				<td class="len80 left">单据类型：</td>
    				<td>
    					<select class="len150" disabled="disabled">
    						<option value="1" <c:if test="${adjustments.billtype=='1' }">selected="selected"</c:if>>报溢调账单</option>
    						<option value="2" <c:if test="${adjustments.billtype=='2' }">selected="selected"</c:if>>报损调账单</option>
    					</select>
    					<input type="hidden" name="adjustments.billtype" value="${adjustments.billtype}"/>
    				</td>
    			</tr>
    			<tr>
    				<td class="len80 left">备注：</td>
    				<td colspan="5">
    					<input type="text" name="adjustments.remark" style="width:680px;" value="<c:out value="${adjustments.remark }"></c:out>" readonly="readonly"/>
    				</td>
    			</tr>
    		</table>
    	</div>
    	<div data-options="region:'center',border:false">
    		<table id="storage-datagrid-adjustmentsAddPage"></table>
    	</div>
    	<input type="hidden" id="storage-adjustments-detail" name="checkListDetailJson"/>
    	<input type="hidden" id="storage-adjustments-printtimes" value="${adjustments.printtimes}" />
    </div>
    <div id="storage-dialog-checkListAddPage"></div>
    <script type="text/javascript">
    	$(function(){
    		$("#storage-datagrid-adjustmentsAddPage").datagrid({ //销售商品明细信息编辑
    			authority:tableColJson,
    			columns: tableColJson.common,
    			frozenColumns: tableColJson.frozen,
    			border: true,
    			rownumbers: true,
    			showFooter: true,
    			striped:true,
    			fit:true,
    			singleSelect: true,
    			data: JSON.parse('${adjustmentsDetailList}'),
    			onLoadSuccess:function(data){
    				if(data.rows.length<10){
	            		var j = 10-data.rows.length;
	            		for(var i=0;i<j;i++){
	            			$("#storage-datagrid-adjustmentsAddPage").datagrid('appendRow',{});
	            		}
   					}else{
   						$("#storage-datagrid-adjustmentsAddPage").datagrid('appendRow',{});
   					}
    				countTotal();
    			}
    		}).datagrid('columnMoving');
    		$("#storage-adjustments-storageid").widget({
    			name:'t_storage_adjustments',
	    		width:150,
				col:'storageid',
				singleSelect:true
    		});
    		$("#storage-adjustments-sourcetype").widget({
    			name:'t_storage_adjustments',
	    		width:150,
				col:'sourcetype',
				singleSelect:true,
				disabled:true
    		});
    		//查看盘点单
    		$("#storage-adjustments-sourcetype-view").click(function(){
    			var checkListid = "${adjustments.sourceid}";
    			top.addOrUpdateTab('storage/showCheckListViewPage.do?id='+ checkListid, "盘点单");
    		});		
    	});
    	//控制按钮状态
    	$("#storage-buttons-adjustmentsPage").buttonWidget("setDataID",{id:'${adjustments.id}',state:'${adjustments.status}',type:'view'});
    	$("#storage-hidden-billid").val("${adjustments.id}");
    	<c:if test="${adjustments.sourcetype=='1'}">
	    	$("#storage-buttons-purchaseEnterPage").buttonWidget("disableMenuItem","relation-upper-view");
	    </c:if>
	    <c:if test="${purchaseEnter.sourcetype!='1'}">
	    	$("#storage-buttons-purchaseEnterPage").buttonWidget("enableMenuItem","relation-upper-view");
	    </c:if>
        $("#storage-buttons-purchaseEnterPage").buttonWidget("enableButton","button-oppaudit");
    </script>
  </body>
</html>
