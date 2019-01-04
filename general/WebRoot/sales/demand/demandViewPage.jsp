<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>要货申请单查看页面</title>
  </head>
  <body>
	<div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="sales-form-demandViewPage" action="" method="post">
	    	<div data-options="region:'north',border:false" style="height:110px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input type="text" class="len130" readonly="readonly" value="${demand.id }" /></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input type="text" class="len130" readonly="readonly" value="${demand.businessdate }" /></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165">
	    					<select id="sales-status-demandViewPage" disabled="disabled" class="len136">
	    						<option value="0" <c:if test="${demand.status == 0 }">selected="selected"</c:if>>未生成订单</option>
	    						<option value="1" <c:if test="${demand.status == 1 }">selected="selected"</c:if>>已生成订单</option>
	    					</select>
	    				</td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">客户：</td>
	    				<td colspan="3"><input type="text" id="sales-customer-demandViewPage" readonly="readonly" text="${demand.customername }" value="${demand.customerid }" style="width: 300px;"/><span id="sales-customer-showid-orderAddPage" style="margin-left:5px;line-height:25px;">编号：${demand.customerid }</span></td>
	    				<%--<td class="len80 left">对方经手人：</td>--%>
	    				<%--<td><select id="sales-handler-demandViewPage" class="len136" disabled="disabled" /></td>--%>
                        <td class="len80 left">销售部门：</td>
                        <td>
							<input id="sales-salesDept-demandViewPage" class="len136" value="${demand.salesdept}"/>
                        </td>
	    			</tr>
	    			<tr>
	    				<td class="len80 left">客户业务员：</td>
	    				<td>
							<input id="sales-salesMan-demandViewPage" class="len136" value="${demand.salesuser}"/>
						</td>
                        <td class="len80 left">备注：</td>
                        <td colspan="3"><input type="text" readonly="readonly" value="<c:out value="${demand.remark }"></c:out>" style="width:400px;" /></td>
	    			</tr>
	    		</table>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<table id="sales-datagrid-demandViewPage"></table>
	    	</div>
	    </form>
    </div>
    <script type="text/javascript">
    	$(function(){
    		$("#sales-datagrid-demandViewPage").datagrid({ //销售商品行编辑
    			columns: wareListJson,
    			border: true,
    			fit:true,
    			rownumbers: true,
    			showFooter: true,
    			striped:true,
    			singleSelect: true,
    			data: JSON.parse('${goodsJson}'),
    			onLoadSuccess: function(){
    				var rows = $("#sales-datagrid-demandViewPage").datagrid('getRows');
    				var leng = rows.length;
    				if(leng < 12){
    					for(var i=leng; i<12; i++){
    						$("#sales-datagrid-demandViewPage").datagrid('appendRow',{});
    					}
    				}
    				groupGoods();
    				countTotal();
    			}
    		}).datagrid('columnMoving');
    		$("#sales-customer-demandViewPage").customerWidget({ //客户参照窗口
    			name:'t_sales_demand',
				col:'customerid',
    			singleSelect:true,
    			width:300
    		});
    		$("#sales-salesDept-demandViewPage").widget({
    		    referwid:'RL_T_BASE_DEPARTMENT_SELLER',
    			width:136,
    			readonly:true,
				singleSelect:true
			});
			$("#sales-salesMan-demandViewPage").widget({
			    referwid:'RL_T_BASE_PERSONNEL_SELLER',
				width:150,
				readonly:true,
				singleSelect:true,
				required:true
			});
    		$("#sales-buttons-demandPage").buttonWidget("setDataID", {id:'${demand.id}', state:'${demand.status}', type:'view'});
    	});
    </script>
  </body>
</html>
