<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>销售订单商品详细信息新增页面</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border:false">
		<form id="sales-form-billDetailEditPage">
			<table cellpadding="5" cellspacing="2">
				<tr>
					<td class="len80">选择商品：</td>
					<td><input id="sales-goodsId-billDetailAddPage" name="goodsname" readonly="readonly" class="len150 readonly" /><input type="hidden" name="goodsid" /></td>
					<td id="sales-loading-billDetailAddPage" colspan="2"></td>
				</tr>
				<tr>
					<td>数量</td>
					<td><input class="formaterNum easyui-validatebox len150" value="0" name="unitnum" onfocus="this.select();frm_focus('unitnum');" onblur="frm_blur('unitnum');" data-options="required:true,validType:'intOrFloatNum[${decimallen}]'" /></td>
					<td>辅数量：</td>
					<td><input name="auxnum" class="formaterNum easyui-validatebox" value="0" style="width:60px;" onfocus="this.select();frm_focus('auxnum');" onblur="frm_blur('auxnum');" data-options="validType:'integer'" /><span id="sales-auxunitname-billDetailAddPage"></span>
						<input name="overnum" class="formaterNum easyui-validatebox" value="0" style="width:60px;" onfocus="this.select();frm_focus('overnum');" onblur="frm_blur('overnum');" data-options="validType:'intOrFloatNum[${decimallen}]'" /><span id="sales-unitname-billDetailAddPage"></span>
					</td>
				</tr>
				<tr>
					<td>单位：</td>
					<td>
						主：<input name="unitname" type="text" class="readonly2" style="width: 40px;" readonly="readonly" /><input type="hidden" name="unitid" />
						辅：<input name="auxunitname" type="text" class="readonly2" style="width: 40px;" readonly="readonly" /><input type="hidden" name="auxunitid" /></td>
					<td>条形码：</td>
					<td><input class="len150 readonly" readonly="readonly" name="barcode" /></td>
				</tr>
				<tr>
					<td>含税单价：</td>
					<td><input class="len150 easyui-validatebox <c:if test="${colMap.taxprice == null }">readonly</c:if>" name="taxprice" onfocus="this.select();frm_focus('taxprice');" onblur="frm_blur('taxprice');" id="sales-taxprice-billDetailAddPage" required="required" validType="intOrFloat" <c:if test="${colMap.taxprice == null }">readonly="readonly"</c:if> /> </td>
					<td>含税金额：</td>
					<td><input class="len150 easyui-numberbox no_input" id="sales-taxamount-billDetailAddPage" data-options="precision:6,groupSeparator:','" readonly="readonly" name="taxamount" /></td>
				</tr>
				<tr>
					<td>未税单价：</td>
					<td><input class="len150 easyui-validatebox readonly" name="notaxprice" id="sales-notaxprice-billDetailAddPage" required="required" validType="intOrFloat" readonly="readonly" /></td>
					<td>未税金额：</td>
					<td><input class="len150 easyui-numberbox no_input" id="sales-notaxamount-billDetailAddPage" readonly="readonly" name="notaxamount" data-options="precision:6,groupSeparator:','" /></td>
				</tr>
				<tr>
					<td>含税箱价：</td>
					<td><input id="sales-boxprice-billDetailAddPage" class="len150 easyui-validatebox <c:if test="${colMap.taxprice == null }">readonly</c:if>" name="boxprice"  required="required" validType="intOrFloat" <c:if test="${colMap.taxprice == null }">readonly="readonly"</c:if> /> </td>
					<td>箱装量：</td>
					<td><input id="sales-boxnum-billDetailAddPage"  class="len150 readonly" readonly="readonly" /></td>
				</tr>
				<tr>
					<td>税种：</td>
					<td><input class="len150 readonly" name="taxtypename" /><input type="hidden" name="taxtype" /> </td>
					<td>税额：</td>
					<td><input class="len150 easyui-numberbox no_input" id="sales-tax-billDetailAddPage" readonly="readonly" name="tax" data-options="precision:6,groupSeparator:','" /></td>
				</tr>
				<tr>
					<td>商品品牌：</td>
					<td><input name="brandName" readonly="readonly" class="len150 readonly" /></td>
					<td>指定仓库：</td>
					<td>
						<input name="storageid" class="len150" id="sales-storage-billDetailAddPage" />
						<input type="hidden" name="storagename" id="sales-storagename-billDetailAddPage" />
					</td>
				</tr>
				<tr>
					<td>批次号：</td>
					<td>
						<input id="sales-batchno-billDetailAddPage" type="text" class="len150 "  name="batchno"/>
						<input id="sales-summarybatchid-billDetailAddPage" type="hidden" name="summarybatchid" />
						<input type="hidden" id="sales-isbatch-billDetailAddPage" value="${isbatch}"/>
					</td>
					<td>所属库位：</td>
					<td>
						<input id="sales-storagelocationname-billDetailAddPage" type="text" class="len150 readonly" readonly="readonly"/>
						<input id="sales-storagelocationid-billDetailAddPage" type="hidden" name="storagelocationid" />
					</td>
				</tr>
				<tr>
					<td>生产日期：</td>
					<td><input id="sales-produceddate-billDetailAddPage" type="text" class="len150 readonly" readonly="readonly" name="produceddate" /> </td>
					<td>截止日期：</td>
					<td><input id="sales-deadline-billDetailAddPage" type="text" class="len150 readonly no_input"  readonly="readonly" name="deadline"/></td>
				</tr>
				<tr>
					<td>备注：</td>
					<td colspan="3"><input id="sales-remark-billDetailAddPage" type="text" style="width: 400px;" name="remark" onfocus="frm_focus('remark');" onblur="frm_blur('remark');" /></td>
				</tr>
			</table>
			<input type="hidden" id="sales-groupid-billDetailAddPage" name="groupid" />
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="buttonDetailBG" style="height:30px;text-align:right;">
			快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
			<input type="button" value="确 定" name="savegoon" id="sales-savegoon-billDetailAddPage" />
		</div>
	</div>

</div>
<script type="text/javascript">
	var storageid = $("#sales-storage-dispatchBillAddPage").widget("getValue");
	var storageParam = null;
	if(storageid!=null && storageid!=""){
		storageParam = [{field:'id',op:'equal',value:storageid}];
	}
	$(function(){
		$("#sales-savegoon-billDetailAddPage").click(function(){
			editSaveDetail(true);
		});
		$("input[name=unitnum]").change(function(){
			unitnumChange(1);
		});
		$("input[name=auxnum]").change(function(){
			unitnumChange(2);
		});
		$("input[name=overnum]").change(function(){
			unitnumChange(2);
		});
		$("#sales-taxprice-billDetailAddPage").change(function(){
			priceChange("1", '#sales-taxprice-billDetailAddPage');
		});
		$("#sales-notaxprice-billDetailAddPage").change(function(){
			priceChange("2", '#sales-notaxprice-billDetailAddPage');
		});
		$("#sales-boxprice-billDetailAddPage").change(function(){
			boxpriceChange('#sales-boxprice-billDetailAddPage');
		});
		$("#sales-storage-billDetailAddPage").widget({
			name:'t_sales_dispatchbill_detail',
			col:'storageid',
			param:storageParam,
			width:150,
			onSelect: function(data){
				$("#sales-storagename-billDetailAddPage").val(data.name);
			},
			onClear: function(){
				$("#sales-storagename-billDetailAddPage").val("");
			}
		});
	});
	function unitnumChange(type){ //数量改变方法
		var $this = $("#sales-unitnum-billDetailAddPage").css({'background':'url(image/loading.gif) right top no-repeat'});
		var goodsId = $("input[name=goodsid]").val();
		var unitnum = $("input[name=unitnum]").val();
		var auxnum = $("input[name=auxnum]").val();
		var overnum = $("input[name=overnum]").val();
		var customerId = "${customerId}";
		var date = $("input[name='dispatchBill.businessdate']").val();
		var price = $("#sales-taxprice-billDetailAddPage").val();
		var url = "";
		var data = "";
		if(type == 1){
			url = "sales/getAuxUnitNumAndPrice.do";
			data = {id:goodsId,unitnum:unitnum,cid:customerId,date:date,price:price,taxpricechange:dispatch_taxpricechange};
		}
		else if(type == 2){
			url = "sales/getUnitNumAndPrice.do";
			data = {id:goodsId,auxnum:auxnum,overnum:overnum,cid:customerId,date:date,price:price,taxpricechange:dispatch_taxpricechange};
		}
		$.ajax({
			url:url,
			dataType:'json',
			type:'post',
			async:false,
			data:data,
			success:function(json){
				$("#sales-taxamount-billDetailAddPage").numberbox('setValue',json.taxamount);
				$("#sales-notaxamount-billDetailAddPage").numberbox('setValue',json.notaxamount);
				$("#sales-taxprice-billDetailAddPage").val(json.taxprice);
				$("#sales-notaxprice-billDetailAddPage").val(json.notaxprice);
				$("#sales-tax-billDetailAddPage").numberbox('setValue',json.tax);
				$("input[name=auxnum]").val(json.auxnum);
				$("input[name=overnum]").val(json.overnum);
				$("input[name=unitnum]").val(json.unitnum);
				var remark = $("input[name=remark]").val();
				if(json.remark != null && json.remark != ""){
					if(remark == ""){
						$("input[name=remark]").val(json.remark);
					}else if(remark != json.remark){
						$("input[name=remark]").val(json.remark +" "+remark);
					}
				}else{
					$("input[name=remark]").val(json.remark);
				}
				if(json.groupid!=null &&json.groupid!=""){
					$("#sales-groupid-billDetailAddPage").val(json.groupid);
				}else{
					$("#sales-groupid-billDetailAddPage").val("");
				}
				$this.css({'background':''});

			}
		});
	}
	function priceChange(type, id){ //1含税单价或2未税单价改变计算对应数据
		var $this = $(id).css({'background':'url(image/loading.gif) right top no-repeat'});
		var price = $(id).val();
		var goodsId = $("input[name=goodsid]").val();
		var taxtype = $("input[name=taxtype]").val();
		var unitnum = $("input[name=unitnum]").val();
		$.ajax({
			url:'sales/getAmountChanger.do',
			dataType:'json',
			async:false,
			type:'post',
			data:'type='+ type +'&price='+ price +'&taxtype='+ taxtype +'&unitnum='+ unitnum+ '&id='+ goodsId,
			success:function(json){
				$("#sales-taxprice-billDetailAddPage").val(json.taxPrice);
				$("#sales-boxprice-billDetailAddPage").val(json.boxPrice);
				$("#sales-taxamount-billDetailAddPage").numberbox('setValue',json.taxAmount);
				$("#sales-notaxprice-billDetailAddPage").val(json.noTaxPrice);
				$("#sales-notaxamount-billDetailAddPage").numberbox('setValue',json.noTaxAmount);
				$("#sales-tax-billDetailAddPage").numberbox('setValue',json.tax);
				$this.css({'background':''});
				var groupid = $("#sales-groupid-billDetailAddPage").val();
				if(groupid != null && groupid != ""){
					$("#sales-groupid-billDetailAddPage").val("");
					$("input[name=remark]").val("");
				}
				dispatch_taxpricechange = "1";
			}
		});
	}
	function boxpriceChange(id){
		var $this = $(id).css({'background':'url(image/loading.gif) right top no-repeat'});
		var price = $(id).val();
		var goodsId = $("input[name=goodsid]").val();
		var taxtype = $("input[name=taxtype]").val();
		var unitnum = $("input[name=unitnum]").val();
		$.ajax({
			url:'sales/getAmountChangerByBoxprice.do',
			dataType:'json',
			async:false,
			type:'post',
			data:'boxprice='+ price +'&taxtype='+ taxtype +'&unitnum='+ unitnum+ '&id='+ goodsId,
			success:function(json){
				$("#sales-taxprice-billDetailAddPage").val(json.taxPrice);
				$("#sales-boxprice-billDetailAddPage").val(json.boxPrice);
				$("#sales-taxamount-billDetailAddPage").numberbox('setValue',json.taxAmount);
				$("#sales-notaxprice-billDetailAddPage").val(json.noTaxPrice);
				$("#sales-notaxamount-billDetailAddPage").numberbox('setValue',json.noTaxAmount);
				$("#sales-tax-billDetailAddPage").numberbox('setValue',json.tax);
				$this.css({'background':''});
			}
		});
	}
</script>
</body>
</html>
