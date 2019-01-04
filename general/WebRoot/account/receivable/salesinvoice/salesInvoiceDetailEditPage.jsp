<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>销售发票明细添加</title>
  </head>
  
  <body>
   	<form action="" method="post" id="account-form-salesInvoiceDetailAddPage">
   		<table  border="0" class="box_table">
   			<tr>
   				<td width="120">商品:</td>
   				<td style="text-align: left;">
   					<input type="text" id="account-salesInvoice-goodsname" width="180" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="account-salesInvoice-goodsid" name="goodsid" width="170"/>
   				</td>
   				<td width="120">来源单据编号:</td>
   				<td style="text-align: left;">
   					<input type="text" id="account-salesInvoice-sourceid" name="sourceid" width="180" class="no_input" readonly="readonly"/>
   				</td>
     		</tr>
   			<tr>
   				<td width="120">商品品牌:</td>
   				<td>
   					<input type="text" id="account-salesInvoice-goodsbrandName" class="no_input" readonly="readonly"/>
   				</td>
   				<td width="120">规格型号:</td>
   				<td>
   					<input type="text" id="account-salesInvoice-goodsmodel" class="no_input" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td>主单位:</td>
   				<td>
   					<input type="text" id="account-salesInvoice-goodsunitname" name="unitname" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="account-salesInvoice-goodsunitid" name="unitid"/>
   				</td>
   				<td>辅单位:</td>
   				<td>
   					<input type="text" id="account-salesInvoice-auxunitname" name="auxunitname" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="account-salesInvoice-auxunitid" name="auxunitid"/>
   				</td>
   			</tr>
   			<tr>
   				<td>数量:</td>
   				<td>
   					<input type="text" id="account-salesInvoice-unitnum" name="unitnum" class="no_input" readonly="readonly"/>
   				</td>
   				<td>辅数量:</td>
   				<td>
   					<input type="text" id="account-salesInvoice-auxunitnumdetail" name="auxnumdetail" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="account-salesInvoice-auxunitnum" name="auxnum"/>
   				</td>
   			</tr>
   			<tr>
   				<td>含税单价:</td>
   				<td>
   					<input type="text" id="account-salesInvoice-taxprice" name="taxprice" class="no_input" readonly="readonly">
   				</td>
   				<td>含税金额:</td>
   				<td>
   					<input type="text" id="account-salesInvoice-taxamount" name="taxamount" class="no_input" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td>未税单价:</td>
   				<td>
   					<input type="text" id="account-salesInvoice-notaxprice" name="notaxprice" class="no_input" readonly="readonly"/>
   				</td>
   				<td>未税金额:</td>
   				<td>
   					<input type="text" id="account-salesInvoice-notaxamount" name="notaxamount" class="no_input" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td>应收日期:</td>
   				<td colspan="3">
   					<input type="text" id="account-salesInvoice-receivedate" name="receivedate" class="no_input" readonly="readonly"/>
   				</td>
   				<!-- 
   				<td>折扣后金额:</td>
   				<td>
   					<input type="text" id="account-salesInvoice-discountamount" name="discountamount"/>
   				</td> -->
   			</tr>
   			<tr>
   				<td>税种:</td>
   				<td>
   					<input type="text" id="account-salesInvoice-taxtypename" name="taxtypename" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="account-salesInvoice-taxtype" name="taxtype" class="no_input" readonly="readonly"/>
   				</td>
   				<td>税额:</td>
   				<td>
   					<input type="text" id="account-salesInvoice-tax" name="tax" class="no_input" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td>备注:</td>
   				<td colspan="3" style="text-align: left;">
   					<input type="text" name="remark" style="width: 400px;" maxlength="200"/>
   				</td>
   			</tr>
   		</table>
   		
    </form>
   <script type="text/javascript">
   		//加载数据
		var object = $("#account-datagrid-salesInvoiceAddPage").datagrid("getSelected");
		$("#account-form-salesInvoiceDetailAddPage").form("load",object);
		$("#account-salesInvoice-goodsname").val(object.goodsInfo.name);
		$("#account-salesInvoice-goodsbrandName").val(object.goodsInfo.brandName);
		$("#account-salesInvoice-goodsmodel").val(object.goodsInfo.model);
		
		$(function(){
			$("#account-salesInvoice-taxprice").numberbox({
				precision:2,
				groupSeparator:','
			});
			$("#account-salesInvoice-taxamount").numberbox({
				precision:2,
				groupSeparator:','
			});
			$("#account-salesInvoice-notaxprice").numberbox({
				precision:2,
				groupSeparator:','
			});
			$("#account-salesInvoice-notaxamount").numberbox({
				precision:2,
				groupSeparator:','
			});
			$("#account-salesInvoice-tax").numberbox({
				precision:2,
				groupSeparator:','
			});
			$("#account-salesInvoice-discountamount").numberbox({
				precision:2,
				groupSeparator:','
			});
		});
		
		var isdiscount = $("#account-salesInvoice-isdiscount").val();
		if(isdiscount=="0"){
			$("#account-salesInvoice-discountamount").addClass("no_input");
			$("#account-salesInvoice-discountamount").attr("readonly","readonly");
		}
   </script>
  </body>
</html>
