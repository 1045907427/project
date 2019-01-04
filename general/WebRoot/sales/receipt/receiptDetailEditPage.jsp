<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>销售发货回单商品详细信息新增页面</title>
</head>
<body>
<form id="sales-form-receiptDetailEditPage">
	<table cellpadding="5" cellspacing="5">
		<tr>
			<td class="len80">选择商品：</td>
			<td><input id="sales-goodsId-receiptDetailAddPage" name="goodsname" class="len150" readonly="readonly" /><input type="hidden" name="goodsid" /></td>
			<td id="sales-loading-receiptDetailAddPage" colspan="2"></td>
		</tr>
		<tr>
			<td>数量：</td>
			<td><input class="formaterNum len150 easyui-validatebox" name="unitnum" id="sales-unitnum-receiptDetailAddPage" required="required" validType="intOrFloatNum[${decimallen}]" /></td>
			<td>客户接收数量：</td>
			<td><input class="formaterNum len150 easyui-validatebox" name="receiptnum" data-options="required:true,validType:['intOrFloatNum[${decimallen}]','receiptMax']" /></td>
		</tr>
		<tr>
			<td>辅数量：</td>
			<td><input class="len150 readonly" readonly="readonly" name="auxnumdetail" /><input type="hidden" name="auxnum" /></td>
			<td>条形码：</td>
			<td><input class="len150 readonly" readonly="readonly" name="barcode" /></td>
		</tr>
		<tr>
			<td>主单位：</td>
			<td><input name="unitname" readonly="readonly" class="len150 readonly" /><input type="hidden" name="unitid" /></td>
			<td>辅单位：</td>
			<td><input name="auxunitname" readonly="readonly" class="len150 readonly" /><input type="hidden" name="auxunitid" /></td>
		</tr>
		<tr>
			<td>规格型号：</td>
			<td><input name="model" readonly="readonly" class="len150 readonly" /></td>
			<td>商品品牌：</td>
			<td><input name="brandName" readonly="readonly" class="len150 readonly" /></td>
		</tr>
		<tr>
			<td>含税单价：</td>
			<td><input class="len150 easyui-validatebox" name="taxprice" id="sales-taxprice-receiptDetailAddPage" required="required" validType="intOrFloat" /> </td>
			<td>含税金额：</td>
			<td><input class="len150 readonly easyui-numberbox" id="sales-taxamount-receiptDetailAddPage" data-options="precision:6,groupSeparator:','" readonly="readonly" name="taxamount" /></td>
		</tr>
		<tr>
			<td>未税单价：</td>
			<td><input class="len150 easyui-validatebox" name="notaxprice" id="sales-notaxprice-receiptDetailAddPage" required="required" validType="intOrFloat" /> </td>
			<td>未税金额：</td>
			<td><input class="len150 readonly easyui-numberbox" id="sales-notaxamount-receiptDetailAddPage" readonly="readonly" name="notaxamount" data-options="precision:6,groupSeparator:','" /></td>
		</tr>
		<tr>
			<td>税种：</td>
			<td><input class="len150 readonly" name="taxtypename" /><input type="hidden" name="taxtype" /> </td>
			<td>税额：</td>
			<td><input class="len150 readonly easyui-numberbox" id="sales-tax-receiptDetailAddPage" readonly="readonly" name="tax" data-options="precision:6,groupSeparator:','" /></td>
		</tr>
		<tr>
			<td>交货日期：</td>
			<td><input type="text" class="len150" name="deliverydate" value="${date }" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" /></td>
			<td>库位：</td>
			<td>
				<input type="text" id="sales-storageLocation-receiptDetailAddPage" name="storagelocation" class="len150" />
				<input type="hidden" id="sales-storageLocationName-receiptDetailAddPage" name="storagelocationname" />
			</td>
		</tr>
		<tr>
			<td>备注：</td>
			<td colspan="3"><input type="text" style="width:420px;" name="remark"  /></td>
		</tr>
	</table>
</form>
<script type="text/javascript">
	$(function(){
		$("#sales-storageLocation-receiptDetailAddPage").widget({
			name:'t_sales_receipt_detail',
			col:'storagelocation',
			singleSelect:true,
			width:150,
			onlyLeafCheck:true,
			onChecked:function(data,checked){
				if(checked){
					$("#sales-storageLocationName-receiptDetailAddPage").val(data.name);
				}
				else{
					$("#sales-storageLocationName-receiptDetailAddPage").val("");
				}
			},
			onClear:function(){
				$("#sales-storageLocationName-receiptDetailAddPage").val("");
			}
		});
		$("#sales-unitnum-receiptDetailAddPage").change(function(){
			receiptMax(parseInt($(this).val()));
			unitnumChange();
		});
		$("#sales-taxprice-receiptDetailAddPage").change(function(){
			priceChange("1", '#sales-taxprice-receiptDetailAddPage');
		});
		$("#sales-notaxprice-receiptDetailAddPage").change(function(){
			priceChange("2", '#sales-notaxprice-receiptDetailAddPage');
		});
	});
	function unitnumChange(){ //数量改变方法
		var $this = $("#sales-unitnum-receiptDetailAddPage").css({'background':'url(image/loading.gif) right top no-repeat'});
		var goodsId = $("input[name=goodsid]").val();
		var num = $("#sales-unitnum-receiptDetailAddPage").val();
		var aid = $("input[name=auxunitid]").val();
		var taxprice = $("#sales-taxprice-receiptDetailAddPage").val();
		var notaxprice = $("#sales-notaxprice-receiptDetailAddPage").val();
		var taxtype = $("input[name=taxtype]").val();
		$.ajax({
			url:'sales/getAuxUnitNum.do',
			dataType:'json',
			type:'post',
			async:false,
			data:'id='+ goodsId +'&num='+ num +'&aid='+ aid +'&tp='+ taxprice +'&ntp='+ notaxprice +'&taxtype='+ taxtype,
			success:function(json){
				$("input[name=auxnumdetail]").val(json.auxnumdetail);
				$("input[name=auxnum]").val(json.auxnum);
				$("#sales-taxamount-receiptDetailAddPage").numberbox('setValue',json.taxAmount);
				$("#sales-notaxamount-receiptDetailAddPage").numberbox('setValue',json.noTaxAmount);
				$("#sales-tax-receiptDetailAddPage").numberbox('setValue',json.tax);
				$this.css({'background':''});
			}
		});
	}
	function priceChange(type, id){ //1含税单价或2未税单价改变计算对应数据
		var $this = $(id).css({'background':'url(image/loading.gif) right top no-repeat'});
		var price = $(id).val();
		var taxtype = $("input[name=taxtype]").val();
		var num = $("#sales-unitnum-receiptDetailAddPage").val();
		$.ajax({
			url:'sales/getAmountChanger.do',
			dataType:'json',
			async:false,
			type:'post',
			data:'type='+ type +'&price='+ price +'&taxtype='+ taxtype +'&num='+ num,
			success:function(json){
				$("#sales-taxprice-receiptDetailAddPage").val(json.taxPrice);
				$("#sales-taxamount-receiptDetailAddPage").numberbox('setValue',json.taxAmount);
				$("#sales-notaxprice-receiptDetailAddPage").val(json.noTaxPrice);
				$("#sales-notaxamount-receiptDetailAddPage").numberbox('setValue',json.noTaxAmount);
				$("#sales-tax-receiptDetailAddPage").numberbox('setValue',json.tax);
				$this.css({'background':''});
			}
		});
	}
</script>
</body>
</html>
