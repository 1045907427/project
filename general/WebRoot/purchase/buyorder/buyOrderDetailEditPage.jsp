<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border:false">
		<form id="purchase-form-buyOrderDetailEditPage" method="post">
			<table style="border-collapse:collapse;" border="0" cellpadding="5" cellspacing="5">
				<tr>
					<td style="text-align: right;">商品：</td>
					<td>
						<input type="text" id="purchase-buyOrderDetail-goodsname" readonly="readonly" style="width:150px;border:1px solid #B3ADAB; background-color: #EBEBE4;"/>
						<input type="hidden" name="goodsid"/>
					</td>
					<td style="text-align: right;">商品编号：</td>
					<td>
						<input type="text" id="purchase-buyOrderDetail-goodsid" readonly="readonly" style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;"/>
					</td>
				</tr>
				<tr>
					<td style="text-align: right;">辅数量：</td>
					<td style="text-align: left;">
						<input type="text" id="purchase-buyOrderDetail-auxnum" name="auxnum" class="formaterNum easyui-validatebox" validType="integer" data-options="required:true" style="width:60px; float:left;" />
						<span id="purchase-buyOrderDetail-span-auxunitname" style="float: left;line-height:25px;">&nbsp;</span>
						<input type="text" id="purchase-buyOrderDetail-unitnum-auxremainder" name="auxremainder" class="formaterNum easyui-validatebox" validType="intOrFloatNum[${decimallen}]" required="true" style="width:60px;float:left;"/>
						<span id="purchase-buyOrderDetail-span-unitname" style="float: left;line-height:25px;">&nbsp;</span>
						<input type="hidden" id="purchase-buyOrderDetail-auxnumdetail" name="auxnumdetail" class="no_input" readonly="readonly"/>
					</td>
					<td style="text-align: right;">数量：</td>
					<td><input type="text" id="purchase-buyOrderDetail-unitnum" name="unitnum" class="formaterNum easyui-validatebox" validType="intOrFloatNum[${decimallen}]" required="true"  style="width:150px;" /></td>
				</tr>
				<tr>
					<td style="text-align: right;">单位：</td>
					<td style="float: left;">
						主：<input id="purchase-buyOrderDetail-unitname" name="unitname" type="text" class="readonly2" style="width: 48px;" readonly="readonly" /><input id="purchase-buyOrderDetail-unitid" type="hidden" name="unitid" />
						辅：<input id="purchase-buyOrderDetail-auxunitname" name="auxunitname" type="text" class="readonly2" style="width: 48px;" readonly="readonly" /><input id="purchase-buyOrderDetail-auxunitid" type="hidden" name="auxunitid" />
					</td>
					<td style="text-align: right;">箱装量：</td>
					<td>
						<input id="purchase-buyOrderDetail-boxnum" type="text" class="len150 readonly" readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td style="text-align: right;">商品品牌：</td>
					<td><input type="text" id="purchase-buyOrderDetail-brand" readonly="readonly" style="width:150px;border:1px solid #B3ADAB; background-color: #EBEBE4;"/></td>
					<td style="text-align: right;">商品规格：</td>
					<td><input type="text" id="purchase-buyOrderDetail-model" readonly="readonly" style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;" /></td>
				</tr>
				<tr>
					<td style="text-align: right;">税种：</td>
					<td>
						<input type="text" id="purchase-buyOrderDetail-taxname" name="taxtypename" readonly="readonly"  style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;"/>
						<input type="hidden" id="purchase-buyOrderDetail-taxtype" name="taxtype" />
					</td>
					<td style="text-align: right;">税额：</td>
					<td><input type="text" id="purchase-buyOrderDetail-tax" name="tax" class="easyui-numberbox" data-options="precision:6" readonly="readonly" style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;"/></td>
				</tr>
				<tr>
					<td style="text-align: right;">含税单价：</td>
					<td><input type="text" id="purchase-buyOrderDetail-taxprice" name="taxprice" class="easyui-validatebox <c:if test="${colMap.taxprice == null }">readonly</c:if>"  required="required" validType="intOrFloat"  <c:if test="${colMap.taxprice == null }">readonly="readonly"</c:if> style="width:150px;"/></td>
					<td style="text-align: right;">含税金额：</td>
					<td><input type="text" id="purchase-buyOrderDetail-taxamount" name="taxamount"  class="easyui-validatebox <c:if test="${colMap.taxamount == null }">readonly</c:if>"  required="required" validType="intOrFloat"  <c:if test="${colMap.taxamount == null }">readonly="readonly"</c:if> style="width:150px;"/></td>
				</tr>
				<tr>
					<td style="text-align: right;">未税单价：</td>
					<td><input type="text" id="purchase-buyOrderDetail-notaxprice" name="notaxprice" class="easyui-validatebox <c:if test="${colMap.notaxprice == null }">readonly</c:if>"  required="required" validType="intOrFloat"  <c:if test="${colMap.notaxprice == null }">readonly="readonly"</c:if> style="width:150px;"/></td>
					<td style="text-align: right;">未税金额：</td>
					<td><input type="text" id="purchase-buyOrderDetail-notaxamount" name="notaxamount"class="easyui-validatebox <c:if test="${colMap.taxamount == null }">readonly</c:if>"  required="required" validType="intOrFloat"  <c:if test="${colMap.taxamount == null }">readonly="readonly"</c:if> style="width:150px;"/></td>
				</tr>
				<tr>
					<td style="text-align: right;">含税箱价：</td>
					<td><input type="text" id="purchase-buyOrderDetail-boxprice" name="boxprice" class="easyui-validatebox <c:if test="${colMap.notaxprice == null }">readonly</c:if>"  required="required" validType="intOrFloat"  <c:if test="${colMap.taxprice == null }">readonly="readonly"</c:if> style="width:150px;"/></td>
					<td style="text-align: right;">未税箱价：</td>
					<td><input type="text" id="purchase-buyOrderDetail-noboxprice" name="noboxprice" readonly="readonly" style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;"/></td>
				</tr>
				<tr>
					<td style="text-align: right;">条形码：</td>
					<td><input type="text" id="purchase-buyOrderDetail-barcode" readonly="readonly" style="width:150px;  border:1px solid #B3ADAB; background-color: #EBEBE4;"/></td>
					<td style="text-align: right;">要求到货日期：</td>
					<td><input type="text" id="purchase-buyOrderDetail-arrivedate" name="arrivedate" class="Wdate" style="width:150px;" onfocus="WdatePicker({'dateFmt':'yyyy-MM-dd'})" tabindex="3"/></td>
				</tr>
				<tr style="display: none">
					<td>重量：</td>
					<td><input id="purchase-buyOrderDetail-totalboxweight" name="totalboxweight" type="text" class="len130 readonly" readonly="readonly" /><span>千克</span></td>
					<td>体积：</td>
					<td><input id="purchase-buyOrderDetail-totalboxvolume" name="totalboxvolume" type="text" class="len120 readonly no_input"  readonly="readonly"/><span>立方米</span></td>
				</tr>
				<tr>
					<td style="text-align: right;">备注：</td>
					<td colspan="3">
						<input type="text" id="purchase-buyOrderDetail-remark" name="remark" style="width:405px;" tabindex="4"/>
					</td>
				</tr>
			</table>
			<input type="hidden" id="purchase-buyOrderDetailEdit-loadflag" value="1"/>
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
			快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span><input type="button" value="确 定" name="savenogo" id="purchase-buyOrderDetailEditPage-editSave" />
		</div>
	</div>
</div>
<script type="text/javascript">
	function detailRemoteCompleteOrder(){
		var taxprice=$("#purchase-buyOrderDetail-taxprice").val();
		var unitnum=$("#purchase-buyOrderDetail-unitnum").val();
		if(taxprice==null || taxprice=="" ){
			taxprice="0";
		}
		if(unitnum==null || unitnum==""){
			unitnum="0";
		}
		var goodsid = $("#purchase-buyOrderDetail-goodsid").val();
		if(goodsid==null || goodsid==""){
			return false;
		}
		var auxunitid=$("#purchase-buyOrderDetail-auxunitid").val();
		var taxtype=$("#purchase-buyOrderDetail-taxtype").val();

		$("#purchase-buyOrderDetail-auxnum").addClass("inputload");
		$("#purchase-buyOrderDetail-auxremainder").addClass("inputload");
		$("#purchase-buyOrderDetail-unitnum").addClass("inputload");
		try{
			$.ajax({
				url :'purchase/common/completeBuyDetailGoodsNoLimit.do',
				type:'post',
				dataType:'json',
				async: false,
				data:{goodsid:goodsid,auxunitid:auxunitid,taxprice:taxprice,unitnum:unitnum,taxtype:taxtype},
				success:function(json){
					$("#purchase-buyOrderDetail-taxname").val(json.taxtypename);
					$("#purchase-buyOrderDetail-auxunitname").val(json.auxunitname);
					$("#purchase-buyOrderDetail-auxnumdetail").val(json.auxnumdetail);
					$("#purchase-buyOrderDetail-auxnum").val(json.auxnum);
					$("#purchase-buyOrderDetail-notaxprice").val(json.notaxprice);
					$("#purchase-buyOrderDetail-tax").numberbox('setValue', json.tax);
					$("#purchase-buyOrderDetail-taxamount").val( json.taxamount);
					$("#purchase-buyOrderDetail-notaxamount").val( json.notaxamount);
					$("#purchase-buyOrderDetail-totalboxweight").val(json.totalboxweight);
					$("#purchase-buyOrderDetail-totalboxvolume").val(json.totalboxvolume);
					$("#purchase-buyOrderDetail-unitnum").attr("max",json.maxunitnum);

					$("#purchase-buyOrderDetail-auxnum").val(json.auxInteger);
					$("#purchase-buyOrderDetail-unitnum-auxremainder").val(json.auxremainder);
					if(json.auxrate!=null){
						$("#purchase-buyOrderDetail-unitnum-auxremainder").attr("max",json.auxrate-1);
					}

					$("#purchase-buyOrderDetail-auxnum").removeClass("inputload");
					$("#purchase-buyOrderDetail-auxremainder").removeClass("inputload");
					$("#purchase-buyOrderDetail-unitnum").removeClass("inputload");
				}
			});
		}catch(e){
		}
	}
	//通过总数量 计算数量 金额换算
	function computeNum(){
		var goodsid= $("#purchase-buyOrderDetail-goodsid").val();
		if(null==goodsid){
			return false;
		}
		var auxunitid = $("#purchase-buyOrderDetail-auxunitid").val();
		var unitnum = $("#purchase-buyOrderDetail-unitnum").val();
		var taxprice = $("#purchase-buyOrderDetail-taxprice").val();
		if(taxprice==null || taxprice==""){
			taxprice="0";
		}
		var notaxprice = $("#purchase-buyOrderDetail-notaxprice").val();
		var taxtype = $("#purchase-buyOrderDetail-taxtype").val();

		$("#purchase-buyOrderDetail-auxnum").addClass("inputload");
		$("#purchase-buyOrderDetail-auxremainder").addClass("inputload");
		$.ajax({
			url :'basefiles/computeGoodsByUnitnum.do',
			type:'post',
			data:{unitnum:unitnum,goodsid:goodsid,auxunitid:auxunitid,taxprice:taxprice,taxtype:taxtype,notaxprice:notaxprice},
			dataType:'json',
			async:false,
			success:function(json){
				$("#purchase-buyOrderDetail-taxamount").val(json.taxamount);
				$("#purchase-buyOrderDetail-notaxamount").val(json.notaxamount);
				$("#purchase-buyOrderDetail-tax").numberbox("setValue",json.tax);
				$("#purchase-buyOrderDetail-taxtypename").val(json.taxtypename);
				$("#purchase-buyOrderDetail-auxnumdetail").val(json.auxnumdetail);
				$("#purchase-buyOrderDetail-auxunitnum").val(json.auxnum);
				$("#purchase-buyOrderDetail-auxunitname").val(json.auxunitname);
				$("#purchase-buyOrderDetail-auxunitname1").html(json.auxunitname);
				$("#purchase-buyOrderDetail-goodsunitname").val(json.unitname);
				$("#purchase-buyOrderDetail-goodsunitname1").html(json.unitname);
				$("#purchase-buyOrderDetail-totalboxweight").val(json.totalboxweight);
				$("#purchase-buyOrderDetail-totalboxvolume").val(json.totalboxvolume);

				$("#purchase-buyOrderDetail-notaxprice").val(json.notaxprice);
				$("#purchase-buyOrderDetail-notaxamount").val(json.notaxamount);

				$("#purchase-buyOrderDetail-auxnum").val(json.auxInteger);
				$("#purchase-buyOrderDetail-unitnum-auxremainder").val(json.auxremainder);
				if(json.auxrate!=null){
					$("#purchase-buyOrderAddPag-unitnum-auxremainder").attr("max",json.auxrate-1);
				}

				$("#purchase-buyOrderDetail-auxnum").removeClass("inputload");
				$("#purchase-buyOrderDetail-auxremainder").removeClass("inputload");
			}
		});
	}
	//通过辅单位数量
	function computeNumByAux(){
		var goodsid= $("#purchase-buyOrderDetail-goodsid").val();
		var auxunitid = $("#purchase-buyOrderDetail-auxunitid").val();
		var taxprice = $("#purchase-buyOrderDetail-taxprice").val();
		if(taxprice==null || taxprice==""){
			taxprice="0";
		}
		var notaxprice = $("#purchase-buyOrderDetail-notaxprice").val();
		var taxtype = $("#purchase-buyOrderDetail-taxtype").val();
		var auxInterger = $("#purchase-buyOrderDetail-auxnum").val();
		var auxremainder = $("#purchase-buyOrderDetail-unitnum-auxremainder").val();

		$("#purchase-buyOrderDetail-auxnum").addClass("inputload");
		$("#purchase-buyOrderDetail-auxremainder").addClass("inputload");
		$("#purchase-buyOrderDetail-unitnum").addClass("inputload");
		$.ajax({
			url :'basefiles/computeGoodsByAuxnum.do',
			type:'post',
			data:{auxInterger:auxInterger,auxremainder:auxremainder,goodsid:goodsid,auxunitid:auxunitid,taxprice:taxprice,taxtype:taxtype,notaxprice:notaxprice},
			dataType:'json',
			async:false,
			success:function(json){
				$("#purchase-buyOrderDetail-taxamount").val(json.taxamount);
				$("#purchase-buyOrderDetail-notaxamount").val(json.notaxamount);
				$("#purchase-buyOrderDetail-tax").numberbox("setValue",json.tax);
				$("#purchase-buyOrderDetail-taxtypename").val(json.taxtypename);

				$("#purchase-buyOrderDetail-taxprice").val(json.taxprice);
				$("#purchase-buyOrderDetail-notaxprice").val(json.notaxprice);
				$("#purchase-buyOrderDetail-notaxamount").val(json.notaxamount);

				$("#purchase-buyOrderDetail-totalboxweight").val(json.totalboxweight);
				$("#purchase-buyOrderDetail-totalboxvolume").val(json.totalboxvolume);

				$("#purchase-buyOrderDetail-unitnum").val(json.mainnum);
				$("#purchase-buyOrderDetail-auxnum").val(json.auxInterger);
				$("#purchase-buyOrderDetail-unitnum-auxremainder").val(json.auxremainder);
				$("#purchase-buyOrderDetail-auxnumdetail").val(json.auxnumdetail);

				$("#purchase-buyOrderDetail-auxnum").removeClass("inputload");
				$("#purchase-buyOrderDetail-auxremainder").removeClass("inputload");
				$("#purchase-buyOrderDetail-unitnum").removeClass("inputload");
			}
		});

	}
	function saveOrderDetail(){
		$("#purchase-buyOrderDetail-remark").focus();
		var flag=$("#purchase-form-buyOrderDetailEditPage").form('validate');
		if(!flag){
			return false;
		}

		var formdata=$("#purchase-form-buyOrderDetailEditPage").serializeJSON();
		if(formdata){
			$("#purchase-buyOrderAddPage-buyOrdertable").datagrid('updateRow',{
				index:editRowIndex,
				row:formdata
			});
			footerReCalc();
		}
		$("#purchase-buyOrderAddPage-dialog-DetailOper-content").dialog("close");
	}
	function priceChange(type, id){ //1含税单价或2未税单价改变计算对应数据
		var $this = $(id).css({'background':'url(image/loading.gif) right top no-repeat'});
		var price = $(id).val();
		var goodsid = $("#purchase-buyOrderDetail-goodsid").val();
		if(goodsid==null || goodsid==""){
			return false;
		}
		var taxtype = $("#purchase-buyOrderDetail-taxtype").val();
		var unitnum = $("#purchase-buyOrderDetail-unitnum").val();
		var auxnum = $("#purchase-buyOrderDetail-auxnum").val();
		$.ajax({
			url:'purchase/common/getAmountChanger.do',
			dataType:'json',
			async:false,
			type:'post',
			data:'type='+ type +'&price='+ price +'&taxtype='+ taxtype +'&unitnum='+ unitnum+ '&id='+ goodsid,
			success:function(json){
				$("#purchase-buyOrderDetail-taxprice").val(json.taxPrice);
				$("#purchase-buyOrderDetail-boxprice").val(json.boxprice);
				$("#purchase-buyOrderDetail-noboxprice").val(json.noboxprice);
				$("#purchase-buyOrderDetail-taxamount").val(json.taxAmount);
				$("#purchase-buyOrderDetail-notaxprice").val(json.noTaxPrice);
				$("#purchase-buyOrderDetail-notaxamount").val(json.noTaxAmount);
				$("#purchase-buyOrderDetail-tax").numberbox('setValue',json.tax);
				$this.css({'background':''});
			}
		});
	}
	function boxpriceChange(id){
        var $this = $(id);
        $this.css({'background':'url(image/loading.gif) right top no-repeat'});
		var price = $(id).val();
		var goodsid = $("#purchase-buyOrderDetail-goodsid").val();
		if(goodsid==null || goodsid==""){
			return false;
		}
		var taxtype = $("#purchase-buyOrderDetail-taxtype").val();
		var unitnum = $("#purchase-buyOrderDetail-unitnum").val();
		var auxnum = $("#purchase-buyOrderDetail-auxnum").val();
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
                "type": "1"
            },
			success:function(json){
                $("#purchase-buyOrderDetail-taxprice").val(json.taxprice);
                $("#purchase-buyOrderDetail-noboxprice").val(json.noboxprice);
                $("#purchase-buyOrderDetail-boxprice").val(json.boxprice);
                $("#purchase-buyOrderDetail-taxamount").val(json.taxamount);
                $("#purchase-buyOrderDetail-notaxprice").val(json.notaxprice);
                $("#purchase-buyOrderDetail-notaxamount").val(json.notaxamount);
                $("#purchase-buyOrderDetail-tax").numberbox('setValue',json.tax);
				$this.css({'background':''});
			}
		});
	}
	function amountChange(type, id){ //1含税金额或2未税金额改变计算对应数据
		var $this = $(id).css({'background':'url(image/loading.gif) right top no-repeat'});
		var price = $(id).val();
		var goodsid = $("#purchase-buyOrderDetail-goodsid").val();
		if(goodsid==null || goodsid==""){
			return false;
		}
		var taxtype = $("#purchase-buyOrderDetail-taxtype").val();
		var unitnum = $("#purchase-buyOrderDetail-unitnum").val();
		var auxnum = $("#purchase-buyOrderDetail-auxnum").val();
		$.ajax({
			url:'purchase/common/getPriceChanger.do',
			dataType:'json',
			async:false,
			type:'post',
			data:'type='+ type +'&amount='+ price +'&taxtype='+ taxtype +'&unitnum='+ unitnum+ '&id='+ goodsid,
			success:function(json){
				$("#purchase-buyOrderDetail-taxprice").val(json.taxPrice);
				$("#purchase-buyOrderDetail-boxprice").val(json.boxprice);
				$("#purchase-buyOrderDetail-noboxprice").val(json.noboxprice);
				$("#purchase-buyOrderDetail-taxamount").val(json.taxAmount);
				$("#purchase-buyOrderDetail-notaxprice").val(json.noTaxPrice);
				$("#purchase-buyOrderDetail-notaxamount").val(json.noTaxAmount);
				$("#purchase-buyOrderDetail-tax").numberbox('setValue',json.tax);
				$this.css({'background':''});
			}
		});
	}
	$(document).ready(function(){
		$("#purchase-buyOrderDetail-arrivedate").click(function(){
			if(!$("#purchase-buyOrderDetail-arrivedate").hasClass("WdateRead")){
				WdatePicker({dateFmt:'yyyy-MM-dd'});
			}
		});

		$("#purchase-buyOrderDetail-unitnum").change(function(){
			if($(this).validatebox('isValid')){
				computeNum();
			}
		});
		$("#purchase-buyOrderDetail-auxnum").change(function(){
			if($(this).validatebox('isValid')){
				computeNumByAux();
			}
		});
		$("#purchase-buyOrderDetail-unitnum-auxremainder").change(function(){
			if($(this).validatebox('isValid')){
				computeNumByAux();
			}
		});

		$("#purchase-buyOrderDetail-taxprice").focus(function(){
			if(!$(this).hasClass("readonly")){
				$(this).select();
			}else{
				$("#purchase-buyOrderDetail-taxamount").focus();
			}
		});

		$("#purchase-buyOrderDetail-taxprice").change(function(){
			if($(this).validatebox('isValid') && !$(this).hasClass("readonly")){
				priceChange("1",this);
			}
		});

		$("#purchase-buyOrderDetail-taxamount").focus(function(){
			if(!$(this).hasClass("readonly")){
				$(this).select();
			}else{
				$("#purchase-buyOrderDetail-notaxprice").focus();
			}
		});


		$("#purchase-buyOrderDetail-taxamount").change(function(){
			if($(this).validatebox('isValid')  && !$(this).hasClass("readonly") ){
				amountChange("1",this);
			}
		});
		$("#purchase-buyOrderDetail-notaxprice").focus(function(){
			if(!$(this).hasClass("readonly")){
				$(this).select();
			}else{
				$("#purchase-buyOrderDetail-notaxamount").focus();
			}
		});

		$("#purchase-buyOrderDetail-notaxprice").change(function(){
			if($(this).validatebox('isValid')  && !$(this).hasClass("readonly") ){
				priceChange("2",this);
			}
		});

		$("#purchase-buyOrderDetail-notaxamount").focus(function(){
			if(!$(this).hasClass("readonly")){
				$(this).select();
			}else{
				$("#purchase-buyOrderDetail-boxprice").focus();
			}
		});

		$("#purchase-buyOrderDetail-notaxamount").change(function(){
			if($(this).validatebox('isValid')  && !$(this).hasClass("readonly") ){
				amountChange("2",this);
			}
		});

		$("#purchase-buyOrderDetail-boxprice").focus(function(){
			if(!$(this).hasClass("readonly")){
				$(this).select();
			}else{
				$("#purchase-buyOrderDetail-remark").focus();
				$("#purchase-buyOrderDetail-remark").select();
			}
		});

		$("#purchase-buyOrderDetail-boxprice").change(function(){
			if($(this).validatebox('isValid')  && !$(this).hasClass("readonly") ){
				boxpriceChange(this);
			}
		});


		$("#purchase-buyOrderDetail-auxnum").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#purchase-buyOrderDetail-auxnum").blur();
				$("#purchase-buyOrderDetail-unitnum-auxremainder").focus();
				$("#purchase-buyOrderDetail-unitnum-auxremainder").select();
			}
		});
		$("#purchase-buyOrderDetail-unitnum-auxremainder").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#purchase-buyOrderDetail-unitnum-auxremainder").blur();
				$("#purchase-buyOrderDetail-unitnum").focus();
				$("#purchase-buyOrderDetail-unitnum").select();
			}
		});
		$("#purchase-buyOrderDetail-unitnum").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#purchase-buyOrderDetail-unitnum").blur();
				$("#purchase-buyOrderDetail-taxprice").focus();
			}
		});
		$("#purchase-buyOrderDetail-taxprice").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#purchase-buyOrderDetail-taxprice").blur();
				$("#purchase-buyOrderDetail-taxamount").focus();
			}
		});
		$("#purchase-buyOrderDetail-taxamount").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#purchase-buyOrderDetail-taxamount").blur()
				$("#purchase-buyOrderDetail-notaxprice").focus();
			}
		});
		$("#purchase-buyOrderDetail-notaxprice").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#purchase-buyOrderDetail-notaxprice").blur();
				$("#purchase-buyOrderDetail-notaxamount").focus();
			}
		});
		$("#purchase-buyOrderDetail-notaxamount").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#purchase-buyOrderDetail-notaxamount").blur()
				$("#purchase-buyOrderDetail-boxprice").focus();
			}
		});
		$("#purchase-buyOrderDetail-boxprice").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#purchase-buyOrderDetail-boxprice").blur();
				$("#purchase-buyOrderDetail-remark").focus();
				$("#purchase-buyOrderDetail-remark").select();
			}
		});
		$("#purchase-buyOrderDetail-remark").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#purchase-buyOrderDetail-remark").blur();
				$("#purchase-buyOrderDetailEditPage-editSave").focus();
			}
		});
		$("#purchase-buyOrderDetailEditPage-editSave").die("keydown").live("keydown",function(event){
			if(event.keyCode==13){
				saveOrderDetail();
			}
		});
		$("#purchase-buyOrderDetailEditPage-editSave").click(function(){
			saveOrderDetail();
		});
	});
</script>
</body>
</html>
