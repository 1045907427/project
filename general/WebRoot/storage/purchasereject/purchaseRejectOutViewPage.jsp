<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>采购退货出库单单新增页面</title>
  </head>  
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="storage-form-purchaseRejectOutAdd"  method="post">
	    	<div data-options="region:'north',border:false" style="height:140px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input class="len150 easyui-validatebox" name="purchaseRejectOut.id" value="${purchaseRejectOut.id}" readonly="readonly"/></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input type="text" class="len150" value="${purchaseRejectOut.businessdate }" name="purchaseRejectOut.businessdate" readonly="readonly"/></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165">
	    					<select id="storage-purchaseRejectOut-status-select" disabled="disabled" class="len136">
    						<c:forEach items="${statusList }" var="list">
    							<c:choose>
    								<c:when test="${list.code == purchaseRejectOut.status}">
    									<option value="${list.code }" selected="selected">${list.codename }</option>
    								</c:when>
    								<c:otherwise>
    									<option value="${list.code }">${list.codename }</option>
    								</c:otherwise>
    							</c:choose>
    						</c:forEach>
    					</select>
    					<input type="hidden" name="purchaseRejectOut.status" value="${purchaseRejectOut.status }"/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">供应商：</td>
	    				<td colspan="3" style="text-align: left;"><input type="text" id="storage-purchaseRejectOut-supplierid" name="purchaseRejectOut.supplierid" value="<c:out value="${purchaseRejectOut.suppliername }"></c:out>" style="width: 320px;" readonly="readonly"/>
	    					<span id="purchase-supplier-showid-purchaseRejectOut" style="margin-left:5px;line-height:25px;">编号：${purchaseRejectOut.supplierid}</span>
	    				</td>
                        <td class="len80 left">来源类型：</td>
                        <td>
                            <input id="storage-purchaseRejectOut-sourcetype" type="text" value="${purchaseRejectOut.sourcetype }"/>
                            <input type="hidden" name="purchaseRejectOut.sourcetype" value="${purchaseRejectOut.sourcetype }"/>
                            <input type="hidden" id="storage-purchaseRejectOut-sourceid" name="purchaseRejectOut.sourceid" value="${purchaseRejectOut.sourceid }"/>
                        </td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">退货仓库：</td>
	    				<td><input id="storage-purchaseRejectOut-storageid" class="len150" name="purchaseRejectOut.storageid" value="<c:out value="${purchaseRejectOut.storagename }"></c:out>" readonly="readonly"/></td>
	    				<td class="len80 left">采购部门：</td>
	    				<td><input id="storage-purchaseRejectOut-buydeptid"  class="len150" name="purchaseRejectOut.buydeptid" value="<c:out value="${purchaseRejectOut.buydeptname }"></c:out>" readonly="readonly"/></td>
	    				<td class="len80 left">采购员：</td>
	    				<td><input id="storage-purchaseRejectOut-buyuserid" style="width: 135px" name="purchaseRejectOut.buyuserid" value="<c:out value="${purchaseRejectOut.buyusername }"></c:out>" readonly="readonly"/></td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">备注：</td>
	    				<td colspan="5" style="text-align: left;"><input type="text" name="purchaseRejectOut.remark" style="width: 665px;" value="<c:out value="${purchaseRejectOut.remark }"></c:out>" readonly="readonly"/></td>
	    			</tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<input type="hidden" name="detailJson" id="storage-purchaseEnter-purchaseRejectOutDetail" />
	    		<table id="storage-datagrid-purchaseRejectOutAddPage"></table>
	    	</div>
	    	
	    	<input type="hidden" id="storage-printtimes-purchaseRejectOutAddPage" value="${purchaseRejectOut.printtimes }"/>
	    	<input type="hidden" id="storage-printlimit-purchaseRejectOutAddPage" value="${printlimit }"/>
    	<input type="hidden" id="storage-ischeck-purchaseRejectOutAddPage" value="${purchaseRejectOut.ischeck }"/>
	    </form>
    </div>
    <div id="storage-dialog-purchaseRejectOutAddPage"></div>
    <script type="text/javascript">
    	$(function(){
    		
    		$("#storage-datagrid-purchaseRejectOutAddPage").datagrid({ //销售商品明细信息编辑
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
	            			$("#storage-datagrid-purchaseRejectOutAddPage").datagrid('appendRow',{});
	            		}
   					}else{
   						$("#storage-datagrid-saleRejectEnterAddPage").datagrid('appendRow',{});
   					}
    				countTotal();
    			}
    		}).datagrid('columnMoving');
            $("#storage-purchaseRejectOut-sourcetype").widget({
                name:'t_storage_purchasereject_out',
                col:'sourcetype',
                singleSelect:true,
                width:136,
                disabled:true
            });
    	});
    	//控制按钮状态
    	$("#storage-buttons-purchaseRejectOutPage").buttonWidget("setDataID",{id:'${purchaseRejectOut.id}',state:'${purchaseRejectOut.status}',type:'view'});
    	$("#storage-hidden-billid").val("${purchaseRejectOut.id}");
    	<c:if test="${purchaseRejectOut.sourcetype=='0'}">
	    	$("#storage-buttons-purchaseRejectOutPage").buttonWidget("disableMenuItem","relation-upper-view");
	    </c:if>
	    <c:if test="${purchaseRejectOut.sourcetype!='0'}">
	    	$("#storage-buttons-purchaseRejectOutPage").buttonWidget("enableMenuItem","relation-upper-view");
	    </c:if>
	    <c:if test="${purchaseRejectOut.status !='3' and purchaseRejectOut.status !='4' }">
		    $("#storage-buttons-purchaseRejectOutPage").buttonWidget("enableButton","button-preview");
		    $("#storage-buttons-purchaseRejectOutPage").buttonWidget("enableButton","button-print");
	    </c:if>

        <c:choose>
           	<c:when test="${purchaseRejectOut.status =='3' or purchaseRejectOut.status =='4' }">
			    $("#storage-buttons-purchaseRejectOutPage").buttonWidget("enableButton","button-preview");
			    $("#storage-buttons-purchaseRejectOutPage").buttonWidget("enableButton","button-print");
           	</c:when>
           	<c:otherwise>           		
			    $("#storage-buttons-purchaseRejectOutPage").buttonWidget("disableButton","button-preview");
			    $("#storage-buttons-purchaseRejectOutPage").buttonWidget("disableButton","button-print");
           	</c:otherwise>
         </c:choose>       
    </script>
  </body>
</html>
