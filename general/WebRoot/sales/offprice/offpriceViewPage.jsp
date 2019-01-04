<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售特价调整单添加页面</title>
  </head>
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="sales-form-offpriceAddPage" action="sales/addOffprice.do" method="post">
    		<input type="hidden" id="sales-addType-offpriceAddPage" name="addType" />
	    	<div data-options="region:'north',border:false" style="height:100px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input class="len150 easyui-validatebox" value="${offprice.id }" readonly="readonly" /></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input type="text" class="len150" value="${offprice.businessdate }" readonly="readonly" /></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165">
							<select id="sales-status-offpriceAddPage" disabled="disabled" class="len150">
	    						<c:forEach items="${statusList }" var="list">
	    							<c:choose>
	    								<c:when test="${list.code == offprice.status}">
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
	    				<td class="len80 left">客户群：</td>
	    				<td>
                            <input type="text" id="sales-customertype-offpriceAddPage" name="offprice.customertype" value="${offprice.customertype}" disabled="disabled"/>
	    				</td>
	    				<td class="len80 left">客户群名称：</td>
	    				<td id="customertd"><input id="sales-customer-offpriceAddPage" value="${offprice.customerid }" disabled="disabled"/></td>
                        <td class="len80 left">档期：</td>
                        <td><input id="sales-schedule-offpriceAddPage" class="len150" name="offprice.schedule" readonly="readonly" value="<c:out value="${offprice.schedule}"></c:out>"/>
                            <input type="hidden" name="offprice.adduserid" value="${offprice.adduserid}"/>
                            <input type="hidden" name="offprice.applyuserid" value="${offprice.applyuserid}"/>
                        </td>
                    </tr>
                    <tr>
                        <td class="len80 left">生效日期：</td>
                        <td><input class="len150 easyui-validatebox" name="offprice.begindate" value="${offprice.begindate }" data-options="required:true" readonly="readonly"  /></td>
	    				<td class="len80 left">截止日期：</td>
	    				<td><input class="len150 easyui-validatebox" value="${offprice.enddate }" readonly="readonly" /></td>
	    				<td class="len80 left">备注：</td>
	    				<td colspan="3"><input type="text" value="<c:out value="${offprice.remark }"></c:out>" readonly="readonly" style="width:150px;" /></td>
	    			</tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
            	<input type="hidden" id="sales-printtimes-offpriceAddPage"  value="${offprice.printtimes}"/>
	    		<input type="hidden" name="goodsjson" id="sales-goodsJson-offpriceAddPage" />
	    		<table id="sales-datagrid-offpriceAddPage"></table>
	    	</div>
	    </form>
    </div>
    <script type="text/javascript">
    	$(function(){
    		$("#sales-datagrid-offpriceAddPage").datagrid({ //销售商品明细信息编辑
    			authority:wareListJson,
    			columns: wareListJson.common,
    			frozenColumns: wareListJson.frozen,
    			border: true,
    			fit: true,
    			rownumbers: true,
    			showFooter: true,
    			striped:true,
    			singleSelect: true,
    			data: JSON.parse('${goodsJson}'),
    			onLoadSuccess: function(){
    				var rows = $("#sales-datagrid-offpriceAddPage").datagrid('getRows');
    				var leng = rows.length;
    				if(leng < 12){
    					for(var i=leng; i<12; i++){
    						$("#sales-datagrid-offpriceAddPage").datagrid('appendRow',{});
    					}
    				}
    			}
    		}).datagrid('columnMoving');

            changeCustomerWidget("${offprice.customertype}","${offprice.customerid}","1");

            $("#sales-customertype-offpriceAddPage").widget({
                name:'t_sales_offprice',
                col:'customertype',
                singleSelect:false,
                width:150
            });

			$("#sales-buttons-offpricePage").buttonWidget("setDataID", {id:'${offprice.id}', state:'${offprice.status}', type:'view'});
			
			$("#sales-buttons-offpricePage").buttonWidget("disableMenuItem","button-print");
			<c:if test="${(offprice.status=='3' or offprice.status=='4') }">
				$("#sales-buttons-offpricePage").buttonWidget("enableMenuItem","button-print");
			</c:if>
    	});
    	var $wareList = $("#sales-datagrid-offpriceAddPage"); //商品datagrid的div对象
    </script>
  </body>
</html>
