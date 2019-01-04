<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>采购入库单</title>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
<%--     	<div id="storage-purchaseEnter-sourcetype"  value="${purchaseEnter.sourcetype}" ></div> --%>
    	<form id="storage-form-purchaseEnterAdd" action="" method="post">
	    	<div data-options="region:'north',border:false" style="height: 140px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input class="len150 easyui-validatebox" name="purchaseEnter.id" readonly='readonly' value="${purchaseEnter.id}"/></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input type="text" id="storage-purchaseEnter-businessdate" class="len130" readonly='readonly' value="${purchaseEnter.businessdate }" name="purchaseEnter.businessdate" /></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165">
	    					<select id="storage-purchaseEnter-status" disabled="disabled" class="len136">
	    						<c:forEach items="${statusList }" var="list">
	    							<c:choose>
	    								<c:when test="${list.code == purchaseEnter.status}">
	    									<option value="${list.code }" selected="selected">${list.codename }</option>
	    								</c:when>
	    								<c:otherwise>
	    									<option value="${list.code }">${list.codename }</option>
	    								</c:otherwise>
	    							</c:choose>
	    						</c:forEach>
	    					</select>
	    					<input type="hidden" name="purchaseEnter.status" value="${purchaseEnter.status }"/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">供应商:</td>
	    				<td colspan="3" style="text-align: left">
	    					<input type="text" id="storage-purchaseEnter-supplierid" name="purchaseEnter.supplierid" style="width: 320px;" value="<c:out value="${purchaseEnter.suppliername }"></c:out>" readonly="readonly"/>
	    					<span id="purchase-supplier-showid-purchaseEnter" style="margin-left:5px;line-height:25px;">编号：${purchaseEnter.supplierid }</span>
	    				</td>
	    				<td class="len80 left">入库仓库：</td>
	    				<td>
	    					<input type="text" id="storage-purchaseEnter-storageid" class="len136" value="<c:out value="${purchaseEnter.storagename }"></c:out>" readonly="readonly"/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">采购部门：</td>
	    				<td>
	    					<input type="text" id="storage-purchaseEnter-buydeptid" name="purchaseEnter.buydeptid" class="len150" value="<c:out value="${purchaseEnter.buydeptname }"></c:out>" readonly="readonly"/>
	    				</td>
	    				<td class="len80 left">采购人员：</td>
	    				<td>
	    					<input type="text" id="storage-purchaseEnter-buyuserid" name="purchaseEnter.buyuserid" class="len130" value="<c:out value="${purchaseEnter.buyusername }"></c:out>" readonly="readonly"/>
	    				</td>
	    				<td class="len80 left">来源类型：</td>
	    				<td>
                            <input id="storage-purchaseEnter-sourcetype" type="text"  value="${purchaseEnter.sourcetype }"/>
	    					<input type="hidden" name="purchaseEnter.sourcetype" value="${purchaseEnter.sourcetype }"/>
	    					<input type="hidden" id="storage-purchaseEnter-sourceid" name="purchaseEnter.sourceid" value="${purchaseEnter.sourceid }"/>
	    				</td>
	    			</tr>
                    <tr><td>手工单号:</td>
                        <td><input id="storage-purchaseEnter-field04" type="text" name="purchaseEnter.field04"  style="width:150px" value="${purchaseEnter.field04 }"/></td>
                        <td class="len80 left">备注：</td>
                        <td colspan="3" style="text-align: left">
                            <input type="text" name="purchaseEnter.remark" style="width: 400px;" value="<c:out value="${purchaseEnter.remark }"></c:out>"/>
                        </td>
                    </tr>
	    			<tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<table id="storage-datagrid-purchaseEnterAddPage"></table>
	    	</div>
	    	<input type="hidden" id="storage-purchaseEnter-purchaseEnterDetail" name="detailJson"/>
	    </form>
	    <input type="hidden" id="storage-purchaseEnter-printtimes" value="${purchaseEnter.printtimes}" />		
    </div>
    <div id="storage-dialog-purchaseEnterAddPage"></div>
    <div id="storage-dialog-batchno-purchaseEnterAddPage"></div>
    <script type="text/javascript">
    	$(function(){
			$("#storage-datagrid-purchaseEnterAddPage").datagrid({ //采购入库单明细信息编辑
    			authority:tableColJson,
    			columns: tableColJson.common,
    			frozenColumns: tableColJson.frozen,
    			border: true,
    			rownumbers: true,
    			showFooter: true,
    			striped:true,
    			fit:true,
    			singleSelect: true,
    			data:JSON.parse('${detailList}'),
    			onLoadSuccess:function(data){
    				if(data.rows.length<10){
	            		var j = 10-data.rows.length;
	            		for(var i=0;i<j;i++){
	            			$("#storage-datagrid-purchaseEnterAddPage").datagrid('appendRow',{});
	            		}
   					}else{
   						$("#storage-datagrid-purchaseEnterAddPage").datagrid('appendRow',{});
   					}
   					countTotal();
    			}
    		}).datagrid('columnMoving');    		
    	});
    	$("#storage-purchaseEnter-sourcetype").widget({
                name:'t_storage_purchase_enter',
                col:'sourcetype',
                singleSelect:true,
                width:136,
                disabled:true
        });
    	
    	//控制按钮状态
    	$("#storage-buttons-purchaseEnterPage").buttonWidget("setDataID",{id:'${purchaseEnter.id}',state:'${purchaseEnter.status}',type:'view'});
    	$("#storage-hidden-billid").val("${purchaseEnter.id}");
    	<c:if test="${purchaseEnter.sourcetype=='0'}">
	    	$("#storage-buttons-purchaseEnterPage").buttonWidget("disableMenuItem","relation-upper-view");
	    </c:if>
	    <c:if test="${purchaseEnter.sourcetype!='0'}">
	    	$("#storage-buttons-purchaseEnterPage").buttonWidget("enableMenuItem","relation-upper-view");
	    </c:if>
	    <c:if test="${purchaseEnter.sourcetype==2}">
	    	$("#storage-buttons-purchaseEnterPage").buttonWidget("disableButton","button-oppaudit");
	    </c:if>
    </script>
  </body>
</html>
