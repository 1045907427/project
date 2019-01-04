<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <title>销售开票</title>
  </head>
  
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="account-form-salesInvoiceBillAdd" action="" method="post">
	    	<div data-options="region:'north',border:false,split:true" style="height: 150px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="2px" cellspacing="2px">
	    			<tr>
	    				<td class="len100 left">编号：</td>
	    				<td class="len165"><input class="len150 easyui-validatebox" name="salesInvoiceBill.id" value="${salesInvoiceBill.id }" readonly="readonly"/></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input type="text" id="account-salesInvoiceBill-businessdate" class="len150" value="${salesInvoiceBill.businessdate }" name="salesInvoiceBill.businessdate" readonly="readonly"/></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165">
	    					<select disabled="disabled" class="len150">
    						<c:forEach items="${statusList }" var="list">
    							<c:choose>
    								<c:when test="${list.code == salesInvoiceBill.status}">
    									<option value="${list.code }" selected="selected">${list.codename }</option>
    								</c:when>
    								<c:otherwise>
    									<option value="${list.code }">${list.codename }</option>
    								</c:otherwise>
    							</c:choose>
    						</c:forEach>
    						</select>
    						<input type="hidden" id="account-salesInvoiceBill-status" name="salesInvoiceBill.status" value="${salesInvoiceBill.status }" readonly="readonly"/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len100 left">客户名称(总店):</td>
	    				<td colspan="3">
	    					<input type="text" id="account-salesInvoiceBill-customerid" name="salesInvoiceBill.customerid" style="width: 330px;" value="${salesInvoiceBill.customerid }" text="<c:out value="${salesInvoiceBill.customername}"></c:out>" readonly="readonly"/>
	    					<span style="margin-left:5px;line-height:25px;">编码：${salesInvoiceBill.customerid }</span>
	    				</td>
                        <td class="len100 left">开票类型:</td>
                        <td>
                            <select id="account-salesInvoiceBill-billtype" class="len150" disabled="disabled">
                                <option value="1" <c:if test="${salesInvoiceBill.billtype == '1'}">selected="selected"</c:if>>正常开票</option>
                                <option value="2" <c:if test="${salesInvoiceBill.billtype == '2'}">selected="selected"</c:if>>预开票</option>
                            </select>
                        </td>
						<input type="hidden" id="account-salesInvoiceBill-hid-billtype" value="${salesInvoiceBill.billtype }"/>
    					<input type="hidden" id="account-salesInvoiceBill-sourceid" name="salesInvoiceBill.sourceid" value="${salesInvoiceBill.sourceid }"/>
	    			</tr>
	    			<tr>
	    				<td class="len100 left">开票客户名称:</td>
	    				<td colspan="3">
	    					<input type="text" id="account-salesInvoiceBill-invoicecustomername" name="salesInvoiceBill.invoicecustomername" style="width: 330px;" value="<c:out value="${salesInvoiceBill.invoicecustomername }"></c:out>" readonly="readonly"/><span style="margin-left:5px;line-height:25px;">编码：${salesInvoiceBill.invoicecustomerid }</span>
	    				</td>
						<td class="len80 left">核销状态:</td>
						<td>
							<select name="salesInvoiceBill.iswriteoff" class="len150" disabled="disabled">
								<option value="0" <c:if test="${salesInvoiceBill.iswriteoff=='0'}">selected="selected"</c:if>>未核销</option>
								<option value="1" <c:if test="${salesInvoiceBill.iswriteoff=='1'}">selected="selected"</c:if>>已核销</option>
							</select>
						</td>
	    				<td class="len80 left" style="display: none">客户业务员:</td>
	    				<td style="display: none">
	    					<input type="text" id="account-salesInvoiceBill-salesuser" class="len150" value="<c:out value="${salesInvoiceBill.salesusername }"></c:out>" readonly="readonly"/>
	    					<input type="hidden"  name="salesInvoiceBill.salesuser" class="len150" value="${salesInvoiceBill.salesuser }" readonly="readonly"/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len100 left">发票类型:</td>
	    				<td>
	    					<select name="salesInvoiceBill.invoicetype" class="len150" disabled="disabled">
	    						<option value=""></option>
	    						<c:forEach items="${invoicetype }" var="list">
								<option value="${list.code }" <c:if test="${list.code == salesInvoiceBill.invoicetype}">selected="selected"</c:if>>${list.codename }</option>
	    						</c:forEach>
	    					</select>
	    				</td>
	    				<td class="len80 left">发票号:</td>
	    				<td>
	    					<input type="text" name="salesInvoiceBill.invoiceno" class="len150" value="${salesInvoiceBill.invoiceno }" readonly="readonly"/>
	    				</td>
	    				<td class="len80 left">发票代码:</td>
	    				<td>
	    					<input type="text" name="salesInvoiceBill.invoicecode" class="len150" value="${salesInvoiceBill.invoicecode }" readonly="readonly"/>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td>账户余额:</td>
	    				<td>
	    					<input type="text" class="easyui-numberbox len150" data-options="min:0,precision:2" value="${salesInvoiceBill.customeramount}" readonly="readonly"/>
	    				</td>
                        <td class="len100 left">销售部门:</td>
                        <td>
                            <select id="storage-otherEnter-deptid" class="len150" disabled="disabled">
                                <option value=""></option>
                                <c:forEach items="${deptList }" var="list">
                                    <option value="${list.id }" <c:if test="${list.id == salesInvoiceBill.salesdept}">selected="selected"</c:if>>${list.name }</option>
                                </c:forEach>
                            </select>
                            <input type="hidden" id="account-salesInvoiceBill-salesdept" name="salesInvoiceBill.salesdept" class="len150" value="${salesInvoiceBill.salesdept }" readonly="readonly"/>
                        </td>
	    				<td class="len100 left">备注：</td>
	    				<td style="text-align: left">
	    					<input type="text" name="salesInvoiceBill.remark" style="width: 150px;" value="<c:out value="${salesInvoiceBill.remark }"></c:out>" readonly="readonly"/>
	    				</td>
	    			</tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false,split:true">
	    		<table id="account-datagrid-salesInvoiceBillAddPage"></table>
	    	</div>
	    	<input type="hidden" id="account-salesInvoiceBill-salesInvoiceBillDetail" name="detailJson"/>
	  		<input type="hidden" id="account-salesInvoiceBill-printtimes" value="${salesInvoiceBill.printtimes}" />	
	  		<input type="hidden" id="account-salesInvoiceBill-printlimit" value="${printlimit}" />
			<input type="hidden" id="account-salesInvoiceBill-jxexporttimes" value="${salesInvoiceBill.jxexporttimes}" />
	    </form>
    </div>
    <div id="account-dialog-salesInvoiceBillAddPage"></div>
    <script type="text/javascript">
		var SIBD_footerobject = null;
    	$(function(){
			$("#account-datagrid-salesInvoiceBillAddPage").datagrid({ //采购入库单明细信息编辑
    			authority:tableColJson,
    			columns: tableColJson.common,
    			frozenColumns: tableColJson.frozen,
    			border: true,
    			rownumbers: true,
    			showFooter: true,
    			striped:true,
    			fit:true,
    			singleSelect: true,
				url:'account/receivable/getSalesInvoiceBillDetailList.do?id=${salesInvoiceBill.id }',
				pagination:true,
				pageSize:500,
    			onLoadSuccess:function(data){
    				if(data.rows.length<10){
	            		var j = 10-data.rows.length;
	            		for(var i=0;i<j;i++){
	            			$("#account-datagrid-salesInvoiceBillAddPage").datagrid('appendRow',{});
	            		}
   					}
					var footerrows = $(this).datagrid('getFooterRows');
					if(null!=footerrows && footerrows.length>0){
						SIBD_footerobject = footerrows[0];
						countTotal();
					}
    			}
    		}).datagrid('columnMoving'); 
    		$("#account-salesInvoiceBill-customerid").customerWidget({
    			name:'t_account_sales_invoice',
	    		width:330,
				col:'customerid',
				singleSelect:true,
				required:true
    		});
    	});
    	
    	//控制按钮状态
    	$("#account-buttons-salesInvoiceBillPage").buttonWidget("setDataID",{id:'${salesInvoiceBill.id}',state:'${salesInvoiceBill.status}',type:'view'});
    	$("#account-hidden-billid").val("${salesInvoiceBill.id}");
    	$("#account-buttons-salesInvoiceBillPage").buttonWidget("disableButton", 'button-cancel');
		$("#account-buttons-salesInvoiceBillPage").buttonWidget("disableMenuItem","mbutton-print");
		$("#account-buttons-salesInvoiceBillPage").buttonWidget("disableMenuItem","mbutton-preview");
		$("#account-buttons-salesInvoiceBillPage").buttonWidget("disableMenuItem","print-blance-button");
		$("#account-buttons-salesInvoiceBillPage").buttonWidget("disableMenuItem","printview-blance-button");
    	<c:if test="${salesInvoiceBill.status!='2' }">
    		$("#account-buttons-salesInvoiceBillPage").buttonWidget("disableMenuItem", 'button-push');
    		$("#account-buttons-salesInvoiceBillPage").buttonWidget("disableMenuItem", 'button-rebate');
    	</c:if>
    	<c:if test="${salesInvoiceBill.status=='2' }">
    		$("#account-buttons-salesInvoiceBillPage").buttonWidget("enableMenuItem", 'button-push');
    		$("#account-buttons-salesInvoiceBillPage").buttonWidget("enableMenuItem", 'button-rebate');
    		$("#account-buttons-salesInvoiceBillPage").buttonWidget("disableButton", 'button-cancel');
    	</c:if>
    	<c:if test="${salesInvoiceBill.status=='3'  or salesInvoiceBill.status=='4'}">
    		$("#account-buttons-salesInvoiceBillPage").buttonWidget("enableButton", 'button-cancel');
			$("#account-buttons-salesInvoiceBillPage").buttonWidget("enableMenuItem","mbutton-print");
			$("#account-buttons-salesInvoiceBillPage").buttonWidget("enableMenuItem","mbutton-preview");
			<c:if test="${hasblance=='1'}">
				$("#account-buttons-salesInvoiceBillPage").buttonWidget("enableMenuItem","print-blance-button");
				$("#account-buttons-salesInvoiceBillPage").buttonWidget("enableMenuItem","printview-blance-button");
			</c:if>
    	</c:if>
    	<c:if test="${salesInvoiceBill.status!='3' }">
    		$("#account-buttons-salesInvoiceBillPage").buttonWidget("disableButton", 'button-cancel');
    	</c:if>
    	<c:if test="${salesInvoiceBill.status=='5' }">
    		$("#account-buttons-salesInvoiceBillPage").buttonWidget("enableButton", 'button-delete');
    	</c:if>
    </script>
  </body>
</html>
