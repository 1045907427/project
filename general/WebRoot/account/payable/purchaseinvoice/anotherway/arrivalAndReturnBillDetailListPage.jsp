<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>生成采购发票明细页面</title>
  </head>
  
  <body>
    <div style="padding-left: 10px;">
    	<input type="hidden" id="select-supplierid-arrivalAndreturn" value="${supplierid }"/>
    	<table  border="0">
    		<tr>
    			<td width="80px">供应商名称:</td>
    			<td style="text-align: left;">
    				<input type="text" style="width: 200px;" class="no_input" readonly="readonly" value="${suppliername }"/>
    			</td>
    			<td width="80px">总金额:</td>
    			<td>
    				<input type="text" id="select-amount-all" style="width: 120px;" class="no_input" readonly="readonly" value="${totalamount }"/>
    			</td>
    			<td width="20px"></td>
    			<td width="80px">选中金额:</td>
    			<td>
					<input type="text" id="select-amount" style="width: 120px;" class="no_input" readonly="readonly" value="${totalamount }"/>
				</td>
    		</tr>
    	</table>
    </div>
    <div id="arrivalAndReturn-detail-div" style="overflow: auto;padding-left: 10px;">
    	<table class="tableinvoice">
    		<tr>
    			<td width="20"></td>
	            <td width="20" align="center"><input type="checkbox" id="allCheckbox" checked="checked"/></td>
	            <td width="110">单据编号</td>
	            <td width="75">单据类型</td>
	            <td width="50">商品编码</td>
	            <td width="180">商品名称</td>
	            <td width="85">条形码</td>
	            <td width="40" align="right">数量</td>
	            <td width="40" align="right">单价</td>
	            <td width="50" align="right">金额</td>
				<td width="50" align="right">未税金额</td>
				<td width="40" align="right">税额</td>
	            <td width="40" align="right">辅数量</td>
    		</tr>
    		<c:forEach var="list" items="${list}" varStatus="status">
		    	<tr id="tr-${list.orderid}-${list.id}" class="detailtr select" value="${list.orderid}-${list.id}">
		    		<td width="20" class="trclick" value="${list.orderid}-${list.id}">${status.index+1}</td>
		            <td  width="20">
		            	<input type="checkbox" id="checkbox-${list.orderid}-${list.id}" class="purchaseinvoice-check" name="ids" value="${list.orderid}-${list.id}" checked="checked"/>
		            	<input type="hidden" id="ordertype-${list.orderid}-${list.id}" value="${list.ordertype}">
		            	<input type="hidden" id="detailid-${list.orderid}-${list.id}" value="${list.id}">
		            	<input type="hidden" id="orderid-${list.orderid}-${list.id}" value="${list.orderid}">
		            	<input type="hidden" id="taxamount-${list.orderid}-${list.id}" value="${list.taxamount}">
		            </td>
		            <td width="110" class="trclick" value="${list.orderid}-${list.id}">${list.orderid}</td>
		            <td width="75" class="trclick" value="${list.orderid}-${list.id}">
		            <c:if test="${list.ordertype=='1'}">采购进货单</c:if>
		    		<c:if test="${list.ordertype=='2'}">采购退货通知单</c:if>
					<c:if test="${list.ordertype=='3'}">应付款期初</c:if>
		    		</td>
		            <td width="50" class="trclick" value="${list.orderid}-${list.id}">${list.goodsid}</td>
		            <td width="180" class="trclick" value="${list.orderid}-${list.id}">${list.goodsInfo.name}</td>
		            <td width="85" class="trclick" value="${list.orderid}-${list.id}">${list.goodsInfo.barcode}</td>
		            <td width="40" class="trclick" value="${list.orderid}-${list.id}" align="right"><c:if test="${list.unitnum !=null }"><fmt:formatNumber value="${list.unitnum}" pattern="${pattern}"/></c:if></td>
		            <td width="40" class="trclick" value="${list.orderid}-${list.id}" align="right"><c:if test="${list.taxprice !=null}"><fmt:formatNumber value=" ${list.taxprice}" type="currency" pattern="0.00"/></c:if></td>
		            <td width="50" class="trclick" value="${list.orderid}-${list.id}" align="right"><fmt:formatNumber value="${list.taxamount}" type="currency" pattern="0.00"/></td>
					<td width="50" class="trclick" value="${list.orderid}-${list.id}" align="right"><fmt:formatNumber value="${list.notaxamount}" type="currency" pattern="0.00"/></td>
					<td width="40" class="trclick" value="${list.orderid}-${list.id}" align="right"><fmt:formatNumber value="${list.tax}" type="currency" pattern="0.00"/></td>
					<td width="40" class="trclick" value="${list.orderid}-${list.id}" align="right">${list.auxnumdetail}</td>
		        </tr>
	    	</c:forEach>
    	</table>
    </div>
    <div id="arrivalAndreturn-purchaseinvoice-old-list"></div>
    <script type="text/javascript">
    	$(function(){
    		$("#allCheckbox").click(function(){
				if($(this).attr("checked")){
					$(".purchaseinvoice-check").attr("checked",true);
					$("#select-amount").val($("#select-amount-all").val());
					$(".detailtr").addClass("select");
				}else{
					$(".purchaseinvoice-check").attr("checked",false);
					$(".detailtr").removeClass("select");
					$("#select-amount").val("0");
				}
			});
			$(".purchaseinvoice-check").click(function(){
				var id = $(this).val();
				if($(this).attr("checked")){
					$("#tr-"+id).addClass("select");
				}else{
					$("#tr-"+id).removeClass("select");
				}
				countTaxamount();
			});
			$(".tableinvoice .detailtr .trclick").click(function(){
				var id = $(this).attr("value");
				if($("#checkbox-"+id).attr("checked")){
					$("#checkbox-"+id).attr("checked",false);
					$("#tr-"+id).removeClass("select");
				}else{
					$("#checkbox-"+id).attr("checked",true);
					$("#tr-"+id).addClass("select");
				}
				countTaxamount();
			});
    	});
    	
    	function countTaxamount(){
    		var totaltaxamount = $("#select-amount-all").val();
			$("#select-amount").val(totaltaxamount);
			$(".purchaseinvoice-check").each(function () {
                if(!$(this).attr("checked")){
                	var id = $(this).val();
                	var taxamount = $("#taxamount-"+id).val();
                	totaltaxamount = Number(totaltaxamount) - Number(taxamount);
                	$("#select-amount").val(formatterMoney(totaltaxamount));
                }
            });
    	}
    	//生成采购发票
    	function addPurchaseInvoiceByRefer(){
    		var rows = [];
    		$(".purchaseinvoice-check").each(function () {
                if($(this).attr("checked")){
                	rows.push($(this).val());
                }
            });
			if(rows==null || rows.length==0){
				$.messager.alert("提醒","请选择数据");
				return false;
			}
			var ids = [];
			for(var i=0;i<rows.length;i++){
				var val = rows[i];
				var billtype = $("#ordertype-"+val).val();
				var billid = $("#orderid-"+val).val();
				var detailid = $("#detailid-"+val).val();
				var object = {billtype:billtype,billid:billid,detailid:detailid}
				ids.push(object);
			}
			$.messager.confirm("提醒","是否确定生成采购发票？",function(r){
				if(r){
					loading("提交中..");
					$.ajax({   
			            url :'account/payable/addPurchaseInvoiceByRefer.do',
			            type:'post',
			            dataType:'json',
			            data:{ids:JSON.stringify(ids)},
			            success:function(json){
			            	loaded();
			            	if(json.flag){
			            		$.messager.alert("提醒","生成成功");
			            		var title = parent.getNowTabTitle();
			            		var purchase_title = tabsWindowTitle('/account/payable/purchaseInvoiceListPage.do');
			            		if (top.$('#tt').tabs('exists',purchase_title)){
				    				var queryJSON = tabsWindow(purchase_title).$("#account-form-query-purchaseInvoicePage").serializeJSON();
				    				tabsWindow(purchase_title).$("#account-datagrid-purchaseInvoicePage").datagrid('load',queryJSON);
				    			}
				    			$("#goodsInfo-div-button").buttonWidget("addNewDataId",$("#goodsInfo-id-baseInfo").val());
			            		top.addOrUpdateTab('account/payable/showPurchaseInvoiceEditPage.do?id='+ json.id, "采购发票查看");
			            		top.closeTab(title);
							}else{
								$.messager.alert("提醒","生成失败<br/>"+json.msg);
							}
			            },
			            error:function(){
			            	loaded();
			            	$.messager.alert("错误","生成采购发票出错");
			            }
			        });
	        	}
	        });
    	}
    	
    	//追加采购发票
    	function showSupplieridPurchaseInvoiceList(supplierid){
    		$('<div id="arrivalAndreturn-purchaseinvoice-old-list-content"></div>').appendTo('#arrivalAndreturn-purchaseinvoice-old-list');
    		$("#arrivalAndreturn-purchaseinvoice-old-list-content").dialog({
				title:"供应商:"+supplierid+"，追加申请列表",
				width:600,
				height:300,
				closed:false,
				modal:true,
				cache:false,
				maximizable:true,
				resizable:true,
				href: 'account/payable/showPurchaseInvoiceListBySupplierPage.do?supplierid='+supplierid,  
			    buttons:[
			    		{
							text:'确认',
							handler:function(){
								var customerid = $("#select-customerid-receiptAndReject").val();
								var rowdata = $("#account-dialog-datagrid-salesInvoiceList-customer").datagrid("getSelected");
								if(null!=rowdata){
									$.messager.confirm("提醒","是否追加到该单据中？",function(r){
										if(r){
											addToSalesInvoiceByCustomer(customerid,rowdata.id);
										}
									});
								}else{
									$.messager.alert("错误","请先选择一条单据！");
								}
							}
						}
				],
				onClose:function(){
			    	$('#receiptAndreject-salesinvoice-old-list-content').dialog("destroy");
			    }
			});
    	}
    </script>
  </body>
</html>
