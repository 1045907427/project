<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>

<body>

<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border:false">
		<form id="purchase-form-plannedOrderDetailEditPage" method="post">
			<table style="border-collapse:collapse;" border="0" cellpadding="5" cellspacing="5">
				<tr>
					<td style="text-align: right;">商品：</td>
					<td>
						<input type="text" id="purchase-plannedOrderDetail-goodsname" name="name" readonly="readonly" style="width:150px;border:1px solid #B3ADAB; background-color: #EBEBE4;"/>
						<input type="hidden" name="goodsid"/>
					</td>
					<td style="text-align: right;">商品编号：</td>
					<td>
						<input type="text" id="purchase-plannedOrderDetail-goodsid" readonly="readonly" style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;"/>
					</td>
				</tr>
				<tr>
					<td style="text-align: right;">辅数量：</td>
					<td>
						<input type="text" id="purchase-plannedOrderDetail-auxnum" name="auxnum" class="formaterNum easyui-validatebox" validType="integer" data-options="required:true" style="width:60px; float:left;" />
						<span id="purchase-plannedOrderDetail-span-auxunitname" style="float: left;line-height:25px;">&nbsp;</span>
						<input type="text" id="purchase-plannedOrderDetail-unitnum-auxremainder" name="auxremainder" class="formaterNum easyui-validatebox" validType="intOrFloatNum[${decimallen}]" required="true" style="width:60px;float:left;"/>
						<span id="purchase-plannedOrderDetail-span-unitname" style="float: left;line-height:25px;">&nbsp;</span>
						<input type="hidden" id="purchase-plannedOrderDetail-auxnumdetail" name="auxnumdetail" class="no_input" readonly="readonly"/>
					</td>
					<td style="text-align: right;">数量：</td>
					<td><input type="text" id="purchase-plannedOrderDetail-unitnum" name="unitnum" class="formaterNum easyui-validatebox" validType="intOrFloatNum[${decimallen}]" data-options="required:true" style="width:150px;"/></td>
				</tr>
				<tr>
					<td style="text-align: right;">单位：</td>
					<td style="float: left;">
						主：<input id="purchase-plannedOrderDetail-unitname" name="unitname" type="text" class="readonly2" style="width: 48px;" readonly="readonly" /><input id="purchase-plannedOrderDetail-unitid" type="hidden" name="unitid" />
						辅：<input id="purchase-plannedOrderDetail-auxunitname" name="auxunitname" type="text" class="readonly2" style="width: 48px;" readonly="readonly" /><input id="purchase-plannedOrderDetail-auxunitid" type="hidden" name="auxunitid" />
					</td>
					<td style="text-align: right;">箱装量：</td>
					<td>
						<input id="purchase-plannedOrderDetail-boxnum" type="text" class="len150 readonly" readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td style="text-align: right;">商品品牌：</td>
					<td><input type="text" id="purchase-plannedOrderDetail-brand" name="brandName" readonly="readonly" style="width:150px;border:1px solid #B3ADAB; background-color: #EBEBE4;"/></td>
					<td style="text-align: right;">商品规格：</td>
					<td><input type="text" id="purchase-plannedOrderDetail-model" name="model" readonly="readonly" style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;" /></td>
				</tr>
				<tr>
					<td style="text-align: right;">税种：</td>
					<td>
						<input type="text" id="purchase-plannedOrderDetail-taxname" name="taxtypename" readonly="readonly"  style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;"/>
						<input type="hidden" id="purchase-plannedOrderDetail-taxtype" name="taxtype" />
					</td>
					<td style="text-align: right;">税额：</td>
					<td><input type="text" id="purchase-plannedOrderDetail-tax" name="tax" class="easyui-numberbox" data-options="precision:6" readonly="readonly" style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;"/></td>
				</tr>
				<tr>
					<td style="text-align: right;">含税单价：</td>
					<td><input type="text" id="purchase-plannedOrderDetail-taxprice" name="taxprice" class="easyui-validatebox <c:if test="${colMap.taxprice == null }">readonly</c:if>"  required="required" validType="intOrFloat"  <c:if test="${colMap.taxprice == null }">readonly="readonly"</c:if> style="width:150px;"/></td>
					<td style="text-align: right;">含税金额：</td>
					<td><input type="text" id="purchase-plannedOrderDetail-taxamount" name="taxamount" class="easyui-validatebox <c:if test="${colMap.taxamount == null }">readonly</c:if>"  required="required" validType="intOrFloat"  <c:if test="${colMap.taxamount == null }">readonly="readonly"</c:if> style="width:150px;"/></td>
				</tr>
				<tr>
					<td style="text-align: right;">未税单价：</td>
					<td><input type="text" id="purchase-plannedOrderDetail-notaxprice" name="notaxprice" class="easyui-validatebox <c:if test="${colMap.notaxprice == null }">readonly</c:if>"  required="required" validType="intOrFloat"  <c:if test="${colMap.notaxprice == null }">readonly="readonly"</c:if> style="width:150px;"/></td>
					<td style="text-align: right;">未税金额：</td>
					<td><input type="text" id="purchase-plannedOrderDetail-notaxamount" name="notaxamount"class="easyui-validatebox <c:if test="${colMap.taxamount == null }">readonly</c:if>"  required="required" validType="intOrFloat"  <c:if test="${colMap.taxamount == null }">readonly="readonly"</c:if> style="width:150px;"/></td>
				</tr>
				<tr>
					<td style="text-align: right;">含税箱价：</td>
					<td><input type="text" id="purchase-plannedOrderDetail-boxprice" name="boxprice" class="easyui-validatebox <c:if test="${colMap.notaxprice == null }">readonly</c:if>"  required="required" validType="intOrFloat"  <c:if test="${colMap.taxprice == null }">readonly="readonly"</c:if> style="width:150px;"/></td>
					<td style="text-align: right;">未税箱价：</td>
					<td><input type="text" id="purchase-plannedOrderDetail-noboxprice" name="noboxprice" class="easyui-validatebox" <c:if test="${colMap.notaxprice == null }">readonly="readonly"</c:if>required="required" validType="intOrFloat"  <c:if test="${colMap.taxprice == null }">readonly="readonly"</c:if> style="width:150px; border:1px solid #B3ADAB;"/></td>
				</tr>
				<tr>
					<td style="text-align: right;">条形码：</td>
					<td><input type="text" id="purchase-plannedOrderDetail-barcode" name="barcode" readonly="readonly" style="width:150px;  border:1px solid #B3ADAB; background-color: #EBEBE4;"/></td>
					<td style="text-align: right;">要求到货日期：</td>
					<td><input type="text" id="purchase-plannedOrderDetail-arrivedate" name="arrivedate" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})"  class="Wdate" style="width:150px; " tabindex="3"/></td>
				</tr>
				<tr>
					<td style="text-align: right;">备注：</td>
					<td colspan="3">
						<input type="text" id="purchase-plannedOrderDetail-remark" name="remark" style="width:405px;" tabindex="4"/>
					</td>
				</tr>
			</table>

			<input type="hidden" id="purchase-plannedOrderDetail-id" name="id" />
			<input type="hidden" name="goodsfield01" />
			<input type="hidden" name="goodsfield02" />
			<input type="hidden" name="goodsfield03" />
			<input type="hidden" name="goodsfield04" />
			<input type="hidden" name="goodsfield05" />
			<input type="hidden" name="goodsfield06" />
			<input type="hidden" name="goodsfield07" />
			<input type="hidden" name="goodsfield08" />
			<input type="hidden" name="goodsfield09" />
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="buttonDetailBG" style="text-align:right;">
			快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span><input type="button" value="确 定" name="savenogo" id="purchase-plannedOrderDetailEditPage-editSave" />
		</div>
	</div>
</div>
<script type="text/javascript">
	//通过总数量 计算数量 金额换算
	function computeNum(){
		var goodsid= $("#purchase-plannedOrderDetail-goodsid").val();
		if(goodsid==""){
			return false;
		}
		var auxunitid = $("#purchase-plannedOrderDetail-auxunitid").val();
		var unitnum = $("#purchase-plannedOrderDetail-unitnum").val();
		var taxprice = $("#purchase-plannedOrderDetail-taxprice").val();
		if(taxprice==null || taxprice==""){
			taxprice="0";
		}
		var notaxprice = $("#purchase-plannedOrderDetail-notaxprice").val();
		var taxtype = $("#purchase-plannedOrderDetail-taxtype").val();

		$("#purchase-plannedOrderDetail-auxnum").addClass("inputload");
		$("#purchase-plannedOrderDetail-auxremainder").addClass("inputload");
		$.ajax({
			url :'basefiles/computeGoodsByUnitnum.do',
			type:'post',
			data:{unitnum:unitnum,goodsid:goodsid,auxunitid:auxunitid,taxprice:taxprice,taxtype:taxtype,notaxprice:notaxprice},
			dataType:'json',
			async:false,
			success:function(json){
				$("#purchase-plannedOrderDetail-taxamount").val(json.taxamount);
				$("#purchase-plannedOrderDetail-notaxamount").val(json.notaxamount);
				$("#purchase-plannedOrderDetail-tax").numberbox("setValue",json.tax);
				$("#purchase-plannedOrderDetail-taxtypename").val(json.taxtypename);
				$("#purchase-plannedOrderDetail-auxnumdetail").val(json.auxnumdetail);
				$("#purchase-plannedOrderDetail-auxunitnum").val(json.auxnum);
				$("#purchase-plannedOrderDetail-auxunitname").val(json.auxunitname);
				$("#purchase-plannedOrderDetail-span-auxunitname").html(json.auxunitname);
				$("#purchase-plannedOrderDetail-unitname").val(json.unitname);
				$("#purchase-plannedOrderDetail-span-unitname").html(json.unitname);

				$("#purchase-plannedOrderDetail-notaxamount").val(json.notaxamount);

				$("#purchase-plannedOrderDetail-auxnum").val(json.auxInteger);
				$("#purchase-plannedOrderDetail-unitnum-auxremainder").val(json.auxremainder);
				if(json.auxrate!=null){
					$("#purchase-plannedOrderDetail-unitnum-auxremainder").attr("max",json.auxrate-1);
				}
				$("#purchase-plannedOrderDetail-auxnum").removeClass("inputload");
				$("#purchase-plannedOrderDetail-auxremainder").removeClass("inputload");
			}
		});
	}
	//通过辅单位数量
	function computeNumByAux(){
		var goodsid= $("#purchase-plannedOrderDetail-goodsid").val();
		var auxunitid = $("#purchase-plannedOrderDetail-auxunitid").val();
		var taxprice = $("#purchase-plannedOrderDetail-taxprice").val();
		if(taxprice==null || taxprice==""){
			taxprice="0";
		}
		var notaxprice = $("#purchase-plannedOrderDetail-notaxprice").val();
		var taxtype = $("#purchase-plannedOrderDetail-taxtype").val();
		var auxInterger = $("#purchase-plannedOrderDetail-auxnum").val();
		var auxremainder = $("#purchase-plannedOrderDetail-unitnum-auxremainder").val();

		$("#purchase-plannedOrderDetail-auxnum").addClass("inputload");
		$("#purchase-plannedOrderDetail-auxremainder").addClass("inputload");
		$("#purchase-plannedOrderDetail-unitnum").addClass("inputload");
		$.ajax({
			url :'basefiles/computeGoodsByAuxnum.do',
			type:'post',
			data:{auxInterger:auxInterger,auxremainder:auxremainder,goodsid:goodsid,auxunitid:auxunitid,taxprice:taxprice,taxtype:taxtype,notaxprice:notaxprice},
			dataType:'json',
			async:false,
			success:function(json){
				$("#purchase-plannedOrderDetail-taxamount").val(json.taxamount);
				$("#purchase-plannedOrderDetail-notaxamount").val(json.notaxamount);
				$("#purchase-plannedOrderDetail-tax").numberbox("setValue",json.tax);
				$("#purchase-plannedOrderDetail-taxtypename").val(json.taxtypename);

				$("#purchase-plannedOrderDetail-taxprice").val(json.taxprice);
				$("#purchase-plannedOrderDetail-notaxprice").val(json.notaxprice);
				$("#purchase-plannedOrderDetail-notaxamount").val(json.notaxamount);

				$("#purchase-plannedOrderDetail-unitnum").val(json.mainnum);
				$("#purchase-plannedOrderDetail-auxnum").val(json.auxInterger);
				$("#purchase-plannedOrderDetail-unitnum-auxremainder").val(json.auxremainder);
				$("#purchase-plannedOrderDetail-auxnumdetail").val(json.auxnumdetail);

				$("#purchase-plannedOrderDetail-auxnum").removeClass("inputload");
				$("#purchase-plannedOrderDetail-auxremainder").removeClass("inputload");
				$("#purchase-plannedOrderDetail-unitnum").removeClass("inputload");
			}
		});

	}

	function saveOrderDetail(){
		$("#purchase-plannedOrderDetail-remark").focus();
		var flag=$("#purchase-form-plannedOrderDetailEditPage").form('validate');
		if(!flag){
			return false;
		}

		var formdata=$("#purchase-form-plannedOrderDetailEditPage").serializeJSON();
		if(formdata){
			$("#purchase-plannedOrderAddPage-plannedOrdertable").datagrid('updateRow',{
				index:editRowIndex,
				row:formdata
			});
			footerReCalc();
		}
		$("#purchase-plannedOrderAddPage-dialog-DetailOper-content").dialog("close");
	}
	function priceChange(type, id){ //1含税单价或2未税单价改变计算对应数据
		var $this = $(id).css({'background':'url(image/loading.gif) right top no-repeat'});
		var price = $(id).val();
		var goodsid = $("#purchase-plannedOrderDetail-goodsid").val();
		if(goodsid==null || goodsid==""){
			return false;
		}
		var taxtype = $("#purchase-plannedOrderDetail-taxtype").val();
		var unitnum = $("#purchase-plannedOrderDetail-unitnum").val();
		var auxnum = $("#purchase-plannedOrderDetail-auxnum").val();
		$.ajax({
			url:'purchase/common/getAmountChanger.do',
			dataType:'json',
			async:false,
			type:'post',
			data:'type='+ type +'&price='+ price +'&taxtype='+ taxtype +'&unitnum='+ unitnum+ '&id='+ goodsid,
			success:function(json){
				$("#purchase-plannedOrderDetail-taxprice").val(json.taxPrice);
				$("#purchase-plannedOrderDetail-boxprice").val(json.boxprice);
				$("#purchase-plannedOrderDetail-noboxprice").val(json.noboxprice);
				$("#purchase-plannedOrderDetail-taxamount").val(json.taxAmount);
				$("#purchase-plannedOrderDetail-notaxprice").val(json.noTaxPrice);
				$("#purchase-plannedOrderDetail-notaxamount").val(json.noTaxAmount);
				$("#purchase-plannedOrderDetail-tax").numberbox('setValue',json.tax);
				$this.css({'background':''});
			}
		});
	}
	function boxpriceChange(id,type){//type:1含税箱价 2未税箱价
		var $this = $(id);
		$this.css({'background':'url(image/loading.gif) right top no-repeat'});
		var price = $(id).val();
		var goodsid = $("#purchase-plannedOrderDetail-goodsid").val();
		if(goodsid==null || goodsid==""){
			return false;
		}
		var taxtype = $("#purchase-plannedOrderDetail-taxtype").val();
		var unitnum = $("#purchase-plannedOrderDetail-unitnum").val();
		var auxnum = $("#purchase-plannedOrderDetail-auxnum").val();
		$.ajax({
			url:'purchase/common/getAmountChangerByBoxprice.do',
			dataType:'json',
			async:false,
			type:'post',
            data:{
                "price": price ,
                "taxtype": taxtype ,
                "unitnum": unitnum,
                "id": goodsid,
                "type": type
            },
			success:function(json){
				$("#purchase-plannedOrderDetail-taxprice").val(json.taxprice);
				$("#purchase-plannedOrderDetail-noboxprice").val(json.noboxprice);
				$("#purchase-plannedOrderDetail-boxprice").val(json.boxprice);
				$("#purchase-plannedOrderDetail-taxamount").val(json.taxamount);
				$("#purchase-plannedOrderDetail-notaxprice").val(json.notaxprice);
				$("#purchase-plannedOrderDetail-notaxamount").val(json.notaxamount);
				$("#purchase-plannedOrderDetail-tax").numberbox('setValue',json.tax);
				$this.css({'background':''});
			}
		});
	}
	function amountChange(type, id){ //1含税金额或2未税金额改变计算对应数据
		var $this = $(id).css({'background':'url(image/loading.gif) right top no-repeat'});
		var price = $(id).val();
		var goodsid = $("#purchase-plannedOrderDetail-goodsid").val();
		if(goodsid==null || goodsid==""){
			return false;
		}
		var taxtype = $("#purchase-plannedOrderDetail-taxtype").val();
		var unitnum = $("#purchase-plannedOrderDetail-unitnum").val();
		var auxnum = $("#purchase-plannedOrderDetail-auxnum").val();
		$.ajax({
			url:'purchase/common/getPriceChanger.do',
			dataType:'json',
			async:false,
			type:'post',
			data:'type='+ type +'&amount='+ price +'&taxtype='+ taxtype +'&unitnum='+ unitnum+ '&id='+ goodsid,
			success:function(json){
				$("#purchase-plannedOrderDetail-taxprice").val(json.taxPrice);
				$("#purchase-plannedOrderDetail-boxprice").val(json.boxprice);
				$("#purchase-plannedOrderDetail-noboxprice").val(json.noboxprice);
				$("#purchase-plannedOrderDetail-taxamount").val(json.taxAmount);
				$("#purchase-plannedOrderDetail-notaxprice").val(json.noTaxPrice);
				$("#purchase-plannedOrderDetail-notaxamount").val(json.noTaxAmount);
				$("#purchase-plannedOrderDetail-tax").numberbox('setValue',json.tax);
				$this.css({'background':''});
			}
		});
	}
	$(document).ready(function(){
		$("#purchase-plannedOrderDetail-arrivedate").click(function(){
			if(!$("#purchase-plannedOrderDetail-arrivedate").hasClass("WdateRead")){
				WdatePicker({dateFmt:'yyyy-MM-dd'});
			}
		});

		$("#purchase-plannedOrderDetail-taxprice").focus(function(){
			if(!$(this).hasClass("readonly")){
				$(this).select();
			}else{
				$("#purchase-plannedOrderDetail-taxamount").focus();
			}
		});

		$("#purchase-plannedOrderDetail-taxprice").change(function(){
			if($(this).validatebox('isValid') && !$(this).hasClass("readonly")){
				priceChange("1",this);
			}
		});

		$("#purchase-plannedOrderDetail-taxamount").focus(function(){
			if(!$(this).hasClass("readonly")){
				$(this).select();
			}else{
				$("#purchase-plannedOrderDetail-notaxprice").focus();
			}
		});


		$("#purchase-plannedOrderDetail-taxamount").change(function(){
			if($(this).validatebox('isValid')  && !$(this).hasClass("readonly") ){
				amountChange("1",this);
			}
		});

		$("#purchase-plannedOrderDetail-notaxprice").focus(function(){
			if(!$(this).hasClass("readonly")){
				$(this).select();
			}else{
				$("#purchase-plannedOrderDetail-notaxamount").focus();
			}
		});
		$("#purchase-plannedOrderDetail-notaxprice").change(function(){
			if($(this).validatebox('isValid')  && !$(this).hasClass("readonly") ){
				priceChange("2",this);
			}
		});

		$("#purchase-plannedOrderDetail-notaxamount").focus(function(){
			if(!$(this).hasClass("readonly")){
				$(this).select();
			}else{
				$("#purchase-plannedOrderDetail-boxprice").focus();
			}
		});

		$("#purchase-plannedOrderDetail-notaxamount").change(function(){
			if($(this).validatebox('isValid')  && !$(this).hasClass("readonly") ){
				amountChange("2",this);
			}
		});

		$("#purchase-plannedOrderDetail-boxprice").focus(function(){
			if(!$(this).hasClass("readonly")){
				$(this).select();
			}else{
				$("#purchase-plannedOrderDetail-noboxprice").focus();
				$("#purchase-plannedOrderDetail-noboxprice").select();
			}
		});

		$("#purchase-plannedOrderDetail-boxprice").change(function(){
			if($(this).validatebox('isValid')  && !$(this).hasClass("readonly") ){
				boxpriceChange(this,"1");
			}
		});

		$("#purchase-plannedOrderDetail-noboxprice").focus(function(){
			if(!$(this).hasClass("readonly")){
				$(this).select();
			}else{
				$("#purchase-plannedOrderDetail-remark").focus();
				$("#purchase-plannedOrderDetail-remark").select();
			}
		});

		$("#purchase-plannedOrderDetail-noboxprice").change(function(){
			if($(this).validatebox('isValid')  && !$(this).hasClass("readonly") ){
				boxpriceChange(this,"2");
			}
		});

		$("#purchase-plannedOrderDetail-unitnum").change(function(){
			if($(this).validatebox('isValid')){
				computeNum();
			}
		});
		$("#purchase-plannedOrderDetail-auxnum").change(function(){
			if($(this).validatebox('isValid')){
				computeNumByAux();
			}
		});
		$("#purchase-plannedOrderDetail-unitnum-auxremainder").change(function(){
			if($(this).validatebox('isValid')){
				computeNumByAux();
			}
		});

		$("#purchase-plannedOrderDetailEditPage-editSave").click(function(){
			saveOrderDetail();
		});

		$("#purchase-plannedOrderDetail-auxnum").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#purchase-plannedOrderDetail-auxnum").blur();
				$("#purchase-plannedOrderDetail-unitnum-auxremainder").focus();
				$("#purchase-plannedOrderDetail-unitnum-auxremainder").select();
			}
		});
		$("#purchase-plannedOrderDetail-unitnum-auxremainder").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#purchase-plannedOrderDetail-unitnum-auxremainder").blur();
				$("#purchase-plannedOrderDetail-unitnum").focus();
				$("#purchase-plannedOrderDetail-unitnum").select();
			}
		});
		$("#purchase-plannedOrderDetail-unitnum").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#purchase-plannedOrderDetail-unitnum").blur();
				$("#purchase-plannedOrderDetail-taxprice").focus();
			}
		});
		$("#purchase-plannedOrderDetail-taxprice").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#purchase-plannedOrderDetail-taxprice").blur();
				$("#purchase-plannedOrderDetail-taxamount").focus();
			}
		});
		$("#purchase-plannedOrderDetail-taxamount").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#purchase-plannedOrderDetail-taxamount").blur()
				$("#purchase-plannedOrderDetail-notaxprice").focus();
			}
		});
		$("#purchase-plannedOrderDetail-notaxprice").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#purchase-plannedOrderDetail-notaxprice").blur()
				$("#purchase-plannedOrderDetail-remark").focus();
				$("#purchase-plannedOrderDetail-remark").select();
			}
		});
		$("#purchase-plannedOrderDetail-notaxamount").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#purchase-plannedOrderDetail-notaxamount").blur()
				$("#purchase-plannedOrderDetail-boxprice").focus();
			}
		});
		$("#purchase-plannedOrderDetail-boxprice").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#purchase-plannedOrderDetail-boxprice").blur();
				$("#purchase-plannedOrderDetail-remark").focus();
				$("#purchase-plannedOrderDetail-remark").select();
			}
		});
		$("#purchase-plannedOrderDetail-remark").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#purchase-plannedOrderDetail-remark").blur();
				$("#purchase-plannedOrderDetailEditPage-editSave").focus();
			}
		});
		$("#purchase-plannedOrderDetailEditPage-editSave").die("keydown").live("keydown",function(event){
			if(event.keyCode==13){
				saveOrderDetail();
			}
		});
	});
</script>
</body>
</html>
