<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border:false">
		<form id="account-form-goldTaxCustomerInvoiceDetailEditPage" method="post">
			<table style="border-collapse:collapse;" border="0" cellpadding="5" cellspacing="5">
				<tr>
					<td style="text-align: right;">开票商品名称：</td>
					<td colspan="3"><input type="text" id="account-goldTaxCustomerInvoiceDetail-goodsname" name="goodsname"  style="width:300px;" class="readonly"  tabindex="1"  /></td>
				</tr>
				<tr id="account-goldTaxCustomerInvoiceDetail-sourcegoods-tr" style="display: none">
					<td style="text-align: right;">客户商品编码：</td>
					<td><input type="text" id="account-goldTaxCustomerInvoiceDetail-sourcegoodsid" name="sourcegoodsid" style="width:150px;" readonly="readonly" class="readonly"/></td>
					<td style="text-align: right;">客户商品名称：</td>
					<td><input type="text" id="account-goldTaxCustomerInvoiceDetail-sourcegoodsname" name="sourcegoodsname" style="width:150px;" readonly="readonly" class="readonly"/></td>
				</tr>
				<tr>
					<td style="text-align: right;">金税分类编码：</td>
					<td><input type="text" id="account-goldTaxCustomerInvoiceDetail-jstypeid" name="jstypeid" style="width:150px; " tabindex="2" class="readonly" readonly="readonly" /></td>
					<td style="text-align: right;">规格型号：</td>
					<td><input type="text" id="account-goldTaxCustomerInvoiceDetail-model" name="model" style="width:150px;"  tabindex="3" class="readonly" readonly="readonly" /></td>
				</tr>
				<tr>
					<td style="text-align: right;">计量单位：</td>
					<td>
						<input type="text" id="account-goldTaxCustomerInvoiceDetail-unitname" name="unitname" style="width:150px;"  class="readonly" tabindex="4" readonly="readonly" />
					</td>
					<td style="text-align: right;">数量：</td>
					<td>
						<input type="text" id="account-goldTaxCustomerInvoiceDetail-unitnum" name="unitnum" style="width:150px;"  class="readonly formaterNum" readonly="readonly"  tabindex="5" />
					</td>
				</tr>
				<tr>
					<td style="text-align: right;">含税单价：</td>
					<td><input type="text" id="account-goldTaxCustomerInvoiceDetail-taxprice" name="taxprice"  class="readonly formaterNum" readonly="readonly" style="width:150px;"  tabindex="6" /></td>
					<td style="text-align: right;">含税金额：</td>
					<td><input type="text" id="account-goldTaxCustomerInvoiceDetail-taxamount" name="taxamount" class="readonly formaterNum" readonly="readonly"  style="width:150px;" tabindex="7" /></td>
				</tr>
				<tr>
					<td style="text-align: right;">未税单价：</td>
					<td><input type="text" id="account-goldTaxCustomerInvoiceDetail-notaxprice" name="notaxprice" class="readonly formaterNum" readonly="readonly" style="width:150px;" tabindex="8" /></td>
					<td style="text-align: right;">未税金额：</td>
					<td>
						<input type="text" id="account-goldTaxCustomerInvoiceDetail-notaxamount" name="notaxamount" class="readonly formaterNum" readonly="readonly" style="width:150px;" tabindex="9" />
					</td>
				</tr>
				<tr>
					<td style="text-align: right;">税率：</td>
					<td>
						<input type="text" id="account-goldTaxCustomerInvoiceDetail-taxrate" name="taxrate" class="readonly formaterNum" readonly="readonly" style="width:150px;" tabindex="10" />
					</td>
					<td style="text-align: right;">税额：</td>
					<td><input type="text" id="account-goldTaxCustomerInvoiceDetail-tax" name="tax" class="easyui-numberbox" data-options="precision:2"  class="readonly" readonly="readonly" style="width:150px; "/></td>
				</tr>
				<tr>
					<td style="text-align: right;">免税标识：</td>
					<td>
						<select id="account-goldTaxCustomerInvoiceDetail-taxfreeflag" name="taxfreeflag" style="width:150px;" tabindex="11">
							<option value="0">其他税率</option>
							<option value="1">普通零税率</option>
							<option value="2">免税</option>
						</select>
					</td>
					<td style="text-align: right;">备注：</td>
					<td>
						<input type="text" id="account-goldTaxCustomerInvoiceDetail-remark" name="remark" style="width:150px;" tabindex="12"/>
					</td>
				</tr>
			</table>
			<input type="hidden" id="account-goldTaxCustomerInvoiceDetail-id" name="id" />
			<input type="hidden" id="account-goldTaxCustomerInvoiceDetail-sourcetype" name="sourcetype" value="0" />
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="buttonDetailBG" style="height:30px;text-align:right;">
			快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span><input type="button" value="确&nbsp;定" name="savenogo" id="account-goldTaxCustomerInvoiceDetailEditPage-editSave" />
		</div>
	</div>
</div>
<script type="text/javascript">

    $("#account-goldTaxCustomerInvoiceDetail-jstypeid").widget({
        referwid:'RL_T_BASE_JSTAXTYPECODE',
        singleSelect:true,
        width:'150',
        readonly:true,
        onSelect: function(data) {
            $("#account-goldTaxCustomerInvoiceDetail-model").focus();
            $("#account-goldTaxCustomerInvoiceDetail-model").select();
        }
    });
</script>
</body>
</html>
