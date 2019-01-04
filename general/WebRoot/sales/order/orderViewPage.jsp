<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>销售订单查看页面</title>
  </head>
  <body>
    <div class="easyui-layout" data-options="fit:true,border:false">
    	<form id="sales-form-orderAddPage" action="" method="post">
	    	<div data-options="region:'north',border:false" style="height:140px;">
	    		<table style="border-collapse:collapse;" border="0" cellpadding="5px" cellspacing="5px">
	    			<tr>
	    				<td class="len80 left">编号：</td>
	    				<td class="len165"><input id="sales-id-orderAddPage" type="text" class="len130" readonly="readonly" value="${saleorder.id }" /></td>
	    				<td class="len80 left">业务日期：</td>
	    				<td class="len165"><input id="sales-businessdate-orderAddPage" type="text" class="len130" readonly="readonly" value="${saleorder.businessdate }" /></td>
	    				<td class="len80 left">状态：</td>
	    				<td class="len165">
	    					<select id="sales-customer-status" disabled="disabled" class="len136">
	    						<c:forEach items="${statusList }" var="list">
	    							<c:choose>
	    								<c:when test="${list.code == saleorder.status}">
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
	    				<td class="len80 left">客户：</td>
	    				<td colspan="3"><input type="text" id="sales-customer-orderAddPage" readonly="readonly" text="<c:out value="${saleorder.customername }"></c:out>" value="${saleorder.customerid }" style="width:300px;" />
                            <span id="sales-customer-showid-orderAddPage" style="margin-left:5px;line-height:25px;">
                                  编号：<a href="javascript:showCustomer('${saleorder.customerid }')">${saleorder.customerid }</a></span>
						<td>销售部门：</td>
						<td class="len150">
							<input id="sales-salesDept-orderAddPage" type="text" class="len136" name="saleorder.salesdept" value="${saleorder.salesdept }" readonly="readonly"/>
						</td>
                    </tr>
	    			<tr>
	    				<td class="len80 left">发货仓库：</td>
	    				<td>
	    				<input id="sales-storageid-orderAddPage" name="saleorder.storageid" value="${saleorder.storageid }"/>
	    				</td>
	    				<td class="len80 left">备注：</td>
	    				<td colspan="3"><input type="text" readonly="readonly" value="<c:out value="${saleorder.remark }"></c:out>" style="width:400px;" /></td>
	    			</tr>
	    		</table>
	    		<input type="hidden" id="sales-printtimes-orderAddPage" value="${saleorder.printtimes }"/>
	    		<input type="hidden" id="sales-phprinttimes-orderAddPage" value="${saleorder.phprinttimes }"/>
	    		<input type="hidden" id="sales-printlimit-orderAddPage" value="${printlimit }"/>
	    	</div>
	    	<div data-options="region:'center',border:false">
	    		<table id="sales-datagrid-orderAddPage"></table>
	    	</div>
	    </form>
    </div>
    <script type="text/javascript">
        var leftAmount = '${leftAmount}';
        var receivableAmount = '${receivableAmount}';
       $("#sales-buttons-orderPage").buttonWidget("enableButton",'order-oweorder-button');
    	$(function(){
    		$("#sales-datagrid-orderAddPage").datagrid({ //销售商品行编辑
    			authority:tableColJson,
    			columns: tableColJson.common,
    			frozenColumns: tableColJson.frozen,
    			border: true,
    			fit:true,
    			rownumbers: true,
    			showFooter: true,
    			striped:true,
    			singleSelect: false,
    			checkOnSelect:true,
    			selectOnCheck:true,
    			data: JSON.parse('${goodsJson}'),
    			onSortColumn:function(sort, order){
    				var rows = $("#sales-datagrid-orderAddPage").datagrid("getRows");
    				var dataArr = [];
    				for(var i=0;i<rows.length;i++){
    					if(rows[i].goodsid!=null && rows[i].goodsid!=""){
    						dataArr.push(rows[i]);
    					}
    				}
    				dataArr.sort(function(a,b){
    					if($.isNumeric(a[sort])){
	    					if(order=="asc"){
	    						return Number(a[sort])>Number(b[sort])?1:-1
	    					}else{
	    						return Number(a[sort])<Number(b[sort])?1:-1
	    					}
    					}else{
    						if(order=="asc"){
	    						return a[sort]>b[sort]?1:-1
	    					}else{
	    						return a[sort]<b[sort]?1:-1
	    					}
    					}
    				});
    				$("#sales-datagrid-orderAddPage").datagrid("loadData",dataArr);
    				return false;
    			},
    			onLoadSuccess: function(data){
    				if(data.rows.length<12){
    					var j = 12-data.rows.length;
	            		for(var i=0;i<j;i++){
	            			$(this).datagrid('appendRow',{});
	            		}
    				}else{
    					$(this).datagrid('appendRow',{});
    				}
    				groupGoods();
    				countTotal(leftAmount,receivableAmount);
    			},
    			onCheckAll:function(){
					countTotal(leftAmount,receivableAmount);
				},
				onUncheckAll:function(){
					countTotal(leftAmount,receivableAmount);
				},
				onCheck:function(){
					countTotal(leftAmount,receivableAmount);
				},
				onUncheck:function(){
					countTotal(leftAmount,receivableAmount);
				}
    		}).datagrid('columnMoving');
    		$("#sales-customer-orderAddPage").customerWidget({ //客户参照窗口
    			name:'t_sales_order',
				col:'customerid',
    			singleSelect:true,
    			width:300
    		});
    		$("#sales-storageid-orderAddPage").widget({
    			referwid:'RL_T_BASE_STORAGE_INFO',
    			width:130,
				singleSelect:true,
				readonly:true
    		});
    		$("#sales-salesDept-orderAddPage").widget({
    			name:'t_sales_order',
				col:'salesdept',
    			width:130,
				singleSelect:true
    		});
    		$("#sales-salesMan-orderAddPage").widget({
    			name:'t_sales_order',
				col:'salesuser',
				width:130,
				singleSelect:true
    		});
    		$("#sales-buttons-orderPage").buttonWidget("setDataID", {id:'${saleorder.id}', state:'${saleorder.status}', type:'view'});
    		if("${saleorder.status}" == "2"){
    			$("#button-invalid").linkbutton("enable");
    			$("#button-uninvalid").linkbutton("disable");
    			$("#sales-buttons-orderPage").buttonWidget("enableButton", 'button-deploy');
    		}
    		else if("${saleorder.status}" == "5"){
    			$("#button-invalid").linkbutton("disable");
    			$("#button-uninvalid").linkbutton("enable");
    			$("#sales-buttons-orderPage").buttonWidget("disableButton", 'button-deploy');
    		}
    		else{
    			$("#button-invalid").linkbutton("disable");
    			$("#button-uninvalid").linkbutton("disable");   
    			$("#sales-buttons-orderPage").buttonWidget("disableButton", 'button-deploy');
    		}
			<c:if test="${(saleorder.sourcetype=='3') }">
			$("#sales-buttons-orderPage").buttonWidget("disableButton",'button-deploy');
			</c:if>

				$("#sales-buttons-orderPage").buttonWidget("enableMenuItem","button-print-orderblank");
			    $("#sales-buttons-orderPage").buttonWidget("enableMenuItem","button-print-DispatchBill");
				$("#sales-buttons-orderPage").buttonWidget("enableMenuItem","button-printview-DispatchBill");
				$("#sales-buttons-orderPage").buttonWidget("enableMenuItem","button-printview-orderblank");
			
    		<c:if test="${saleorder.status!='2' }">
	    		$("#sales-buttons-orderPage").buttonWidget("disableButton", 'storage-deploy-button');
	    	</c:if>
	    	<c:if test="${saleorder.status=='2' }">
	    		$("#sales-buttons-orderPage").buttonWidget("enableButton", 'storage-deploy-button');
	    	</c:if>
    	});
        var $wareList = $("#sales-datagrid-orderAddPage");
    </script>
  </body>
</html>
