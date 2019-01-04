<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<html>
<head>
	<title>采购退货出库单明细添加</title>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border:false">
		<form action="" method="post" id="purchase-form-returnOrderDetailAddPage">
			<table  border="0" class="box_table">
				<tr>
					<td></td>
					<td></td>
					<td colspan="2" id="purchase-returnOrderDetail-loadInfo" style="text-align: left;">&nbsp;</td>
				</tr>
				<tr>
					<td width="120">选择商品：</td>
					<td style="text-align: left;">
						<input type="text" id="purchase-returnOrderDetail-goodsid" name="goodsid" width="170"/>
					</td>
					<td>批次号：</td>
					<td>
						<input type="text" id="purchase-returnOrderDetail-batchno" name="batchno" class="no_input" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td style="text-align: right;">辅数量：</td>
					<td style="text-align: left;">
						<input type="text" id="purchase-returnOrderDetail-auxnum" name="auxnum" value="0" class="easyui-validatebox" validType="integer" data-options="required:true" style="width:60px; float:left;" />
						<span id="purchase-returnOrderDetail-span-auxunitname" style="float: left;line-height:25px;">&nbsp;</span>
						<input type="text" id="purchase-returnOrderDetail-unitnum-auxremainder" name="auxremainder" value="0" class="easyui-validatebox" validType="intOrFloatNum[${decimallen}]" required="true" style="width:60px;float:left;"/>
						<span id="purchase-returnOrderDetail-span-unitname" style="float: left;line-height:25px;">&nbsp;</span>
						<input type="hidden" id="purchase-returnOrderDetail-auxnumdetail" name="auxnumdetail" class="no_input" readonly="readonly"/>
					</td>
					<td style="text-align: right;">数量：</td>
					<td><input type="text" id="purchase-returnOrderDetail-unitnum" name="unitnum" value="0" class="easyui-validatebox" validType="intOrFloatNum[${decimallen}]" required="true" />
						<input type="hidden" id="purchase-returnOrderDetail-existingnum" name="existingnum">
					</td>
				</tr>
				<tr>
					<td style="text-align: right;">单位：</td>
					<td style="float: left;">
						<span style="float: left;">主：</span><input id="purchase-returnOrderDetail-unitname" name="unitname" type="text" class="readonly2" style="width: 58px;" readonly="readonly" /><input id="purchase-returnOrderDetail-unitid" type="hidden" name="unitid" />
						<span style="float: left;">辅：</span><input id="purchase-returnOrderDetail-auxunitname" name="auxunitname" type="text" class="readonly2" style="width: 58px;" readonly="readonly" /><input id="purchase-returnOrderDetail-auxunitid" type="hidden" name="auxunitid" />
					</td>
					<td style="text-align: right;">箱装量：</td>
					<td>
						<input id="purchase-returnOrderDetail-boxnum" name="boxnum" type="text" class="len150 readonly" readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td>含税单价：</td>
					<td style="text-align: left;">
						<input type="text" id="purchase-returnOrderDetail-taxprice" name="taxprice" class="easyui-validatebox <c:if test="${colMap.taxprice == null }">readonly</c:if> " <c:if test="${colMap.taxprice == null }">readonly="readonly"</c:if> required="required" validType="intOrFloat" >
					</td>
					<td>含税金额：</td>
					<td>
						<input type="text" id="purchase-returnOrderDetail-taxamount" name="taxamount" class="no_input easyui-validatebox" readonly="readonly" required="required" validType="intOrFloat" />
					</td>
				</tr>
				<tr>
					<td>未税单价：</td>
					<td>
						<input type="text" id="purchase-returnOrderDetail-notaxprice" name="notaxprice" class="easyui-validatebox <c:if test="${colMap.taxprice == null }">readonly</c:if>" <c:if test="${colMap.taxprice == null }">readonly="readonly"</c:if>  required="required" validType="intOrFloat" />
					</td>
					<td>未税金额：</td>
					<td>
						<input type="text" id="purchase-returnOrderDetail-notaxamount" name="notaxamount" class="no_input easyui-validatebox" readonly="readonly" required="required" validType="intOrFloat"  />
					</td>
				</tr>
				<tr>
					<td style="text-align: right;">含税箱价：</td>
					<td><input type="text" id="purchase-returnOrderDetail-boxprice" name="boxprice" class="easyui-validatebox <c:if test="${colMap.taxprice == null }">readonly</c:if>" <c:if test="${colMap.taxprice == null }">readonly="readonly"</c:if> required="required" validType="intOrFloat"/></td>
					<td style="text-align: right;">未税箱价：</td>
					<td><input type="text" id="purchase-returnOrderDetail-noboxprice" name="noboxprice" readonly="readonly" style="border:1px solid #B3ADAB; background-color: #EBEBE4;"/></td>
				</tr>
				<tr>
					<td width="120">商品品牌：</td>
					<td>
						<input type="text" id="purchase-returnOrderDetail-goodsbrandName" class="no_input" readonly="readonly"/>
					</td>
					<td width="120">条形码：</td>
					<td>
						<input type="text" id="purchase-returnOrderDetail-barcode" class="no_input" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>税种：</td>
					<td>
						<input type="text" id="purchase-returnOrderDetail-taxtypename" name="taxtypename" class="no_input" readonly="readonly"/>
						<input type="hidden" id="purchase-returnOrderDetail-taxtype" name="taxtype" class="no_input" readonly="readonly"/>
					</td>
					<td>税额：</td>
					<td>
						<input type="text" id="purchase-returnOrderDetail-tax" name="tax" class="no_input" readonly="readonly" data-option="precision:6,groupSeparator:',',required:true"/>
					</td>
				</tr>
				<tr>
					<td width="120">规格型号：</td>
					<td>
						<input type="text" id="purchase-returnOrderDetail-goodsmodel" class="no_input" readonly="readonly"/>
					</td>
					<td>所属库位：</td>
					<td style="text-align: left;">
						<input type="text" id="purchase-returnOrderDetail-storagelocationname" name="storagelocationname" class="no_input" readonly="readonly"/>
						<input type="hidden" id="purchase-returnOrderDetail-storagelocationid"  name="storagelocationid"/>
						<input type="hidden" id="purchase-returnOrderDetail-detail-storageid"  name="storageid"/>
						<input type="hidden" id="purchase-returnOrderDetail-detail-summarybatchid"  name="summarybatchid"/>
					</td>
				</tr>
				<tr>
					<td>生产日期：</td>
					<td>
						<input type="text" id="purchase-returnOrderDetail-produceddate" style="height: 20px;" name="produceddate" class="no_input" readonly="readonly"/>
					</td>
					<td>截止日期：</td>
					<td>
						<input type="text" id="purchase-returnOrderDetail-deadline" style="height: 20px;" name="deadline" class="no_input" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>备注：</td>
					<td colspan="3" style="text-align: left;">
						<input type="text" name="remark" id="purchase-returnOrderDetail-remark" style="width: 488px;" maxlength="200"/>
					</td>
				</tr>
			</table>
			<input type="hidden" name="highestbuyprice" id="purchase-returnOderDeail-highestbuyprice">
			<input type="hidden" name="newbuyprice" id="purchase-returnOderDeail-newbuyprice">
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="buttonDetailBG" style="height:30px;text-align:right;">
			快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span><input type="button" value="继续添加" name="savegoon" id="purchase-returnOrderDetailAddPage-addSaveGoOn" />
			<input type="button" value="确 定" name="savenogo" id="purchase-returnOrderDetailAddPage-addSave" />
		</div>
	</div>
</div>
<script type="text/javascript">
	//控制各批次的商品 只能添加一条
	var summarybatchid = "";
	var storageid = $("#purchase-returnOrderAddPage-storage").widget("getValue");
	var detailrows = $("#purchase-returnOrderAddPage-returnOrdertable").datagrid('getRows');
	for(var i=0; i<detailrows.length; i++){
		var rowJson = detailrows[i];
		if(rowJson.goodsid != undefined){
			if(summarybatchid==""){
				if(rowJson.summarybatchid!=null && rowJson.summarybatchid!=""){
					summarybatchid = rowJson.summarybatchid;
				}
			}else{
				if(rowJson.summarybatchid!=null && rowJson.summarybatchid!=""){
					summarybatchid += ","+rowJson.summarybatchid;
				}
			}
		}
	}

<security:authorize url="/purchase/returnorder/rejectReturnEditTaxamount.do">
	$("#purchase-returnOrderDetail-taxamount").removeAttr("readonly");
	$("#purchase-returnOrderDetail-taxamount").removeClass("no_input");
	$("#purchase-returnOrderDetail-taxamount").change(function () {
		var value = $(this).val();
		amountChange("1",value);
	});


	$("#purchase-returnOrderDetail-notaxamount").removeAttr("readonly");
	$("#purchase-returnOrderDetail-notaxamount").removeClass("no_input");
	$("#purchase-returnOrderDetail-notaxamount").change(function () {
		var value = $(this).val();
		amountChange("2",value);
	});

	function amountChange(type, amount){ //1含税金额或2未税金额改变计算对应数据
		var goodsId = $("input[name=goodsid]").val();
		var taxtype = $("input[name=taxtype]").val();
		var unitnum = $("input[name=unitnum]").val();
		var auxnum = $("input[name=auxnum]").val();
		$.ajax({
		url:'sales/getAmountChangeByType.do',
		dataType:'json',
		async:false,
		type:'post',
		data:{type:type,amount:amount,taxtype:taxtype,unitnum:unitnum,id:goodsId},
		success:function(json){
			$("#purchase-returnOrderDetail-taxprice").val(json.taxPrice);
			$("#purchase-returnOrderDetail-boxprice").val(json.boxPrice);
            $("#purchase-returnOrderDetail-noboxprice").val(json.noboxPrice);
			$("#purchase-returnOrderDetail-taxamount").val(json.taxAmount);
			$("#purchase-returnOrderDetail-notaxprice").val(json.noTaxPrice);
			$("#purchase-returnOrderDetail-notaxamount").val(json.noTaxAmount);
			$("#purchase-returnOrderDetail-tax").val(json.tax);
			}
		});
	}
</security:authorize>


	function detailRemoteCompleteOrder(){
		var goodsInfo= $("#purchase-returnOrderDetail-goodsid").goodsWidget("getObject");
		var unitnum = $("#purchase-returnOrderDetail-unitnum").val();
		var taxprice = $("#purchase-returnOrderDetail-taxprice").val();
		var notaxprice = $("#purchase-returnOrderDetail-notaxprice").val();
		var taxtype = $("#purchase-returnOrderDetail-taxtype").val();
		if(null==goodsInfo){
			return false;
		}
		$("#purchase-returnOrderDetail-auxnum").addClass("inputload");
		$("#purchase-returnOrderDetail-auxremainder").addClass("inputload");
		$("#purchase-returnOrderDetail-unitnum").addClass("inputload");
		try{
			$.ajax({
				url :'storage/computePurchaseRejectOutDetailNum.do',
				type:'post',
				data:{unitnum:unitnum,goodsid:goodsInfo.goodsid,taxprice:taxprice,taxtype:taxtype,notaxprice:notaxprice},
				dataType:'json',
				async:false,
				success:function(json){
					$("#purchase-returnOrderDetail-taxamount").val(json.taxamount);
					$("#purchase-returnOrderDetail-notaxamount").val(json.notaxamount);
					$("#purchase-returnOrderDetail-tax").val(json.tax);
					$("#purchase-returnOrderDetail-taxtypename").val(json.taxtypename);
					$("#purchase-returnOrderDetail-auxunitid").val(json.auxunitid);
					$("#purchase-returnOrderDetail-auxnumdetail").val(json.auxnumdetail);
					$("#purchase-returnOrderDetail-auxunitnum").val(json.auxnum);
					$("#purchase-returnOrderDetail-auxunitname").val(json.auxunitname);

					$("#purchase-returnOrderDetail-notaxprice").numberbox("setValue",json.notaxprice);
					$("#purchase-returnOrderDetail-auxnum").val(json.auxInteger);
					$("#purchase-returnOrderDetail-unitnum-auxremainder").val(json.auxremainder);
					if(json.auxrate!=null){
						$("#purchase-returnOrderDetail-unitnum-auxremainder").attr("max",json.auxrate-1);
					}

					$("#purchase-returnOrderDetail-auxnum").addClass("inputload");
					$("#purchase-returnOrderDetail-auxremainder").addClass("inputload");
					$("#purchase-returnOrderDetail-unitnum").addClass("inputload");
				}
			});
		}catch(e){
		}
	}
	function orderDetailAddSaveGoOnDialog(){
		var $DetailOper=$("#purchase-returnOrderAddPage-dialog-DetailOper-content");
		if($DetailOper.size()>0){
			$DetailOper.dialog('close');
		}
		$('<div id="purchase-returnOrderAddPage-dialog-DetailOper-content"></div>').appendTo('#purchase-returnOrderAddPage-dialog-DetailOper');
		$DetailOper=$("#purchase-returnOrderAddPage-dialog-DetailOper-content");
		$DetailOper.dialog({
			title:'商品信息新增(按ESC退出)',
			width: 680,
			height: 420,
			closed: true,
			cache: false,
			modal: true,
			maximizable:true,
			href:"purchase/returnorder/returnOrderDetailAddPage.do?supplierid=${supplierid}&storageid=${storageid}",
			onLoad:function(){
				$("#purchase-returnOrderDetail-goodsid").focus();
			},
			onClose:function(){
				$DetailOper.dialog("destroy");
			}
		});
		$DetailOper.dialog("open");
	}
	//通过总数量 计算数量 金额换算
	function computeNum(){
		var goodsid= $("#purchase-returnOrderDetail-goodsid").goodsWidget("getValue");
		if(null==goodsid || goodsid==""){
			return false;
		}
		var auxunitid = $("#purchase-returnOrderDetail-auxunitid").val();
		var unitnum = $("#purchase-returnOrderDetail-unitnum").val();
		var taxprice = $("#purchase-returnOrderDetail-taxprice").val();
		if(taxprice==null || taxprice==""){
			taxprice="0";
		}
		var notaxprice = $("#purchase-returnOrderDetail-notaxprice").val();
		var taxtype = $("#purchase-returnOrderDetail-taxtype").val();


		$("#purchase-returnOrderDetail-auxnum").addClass("inputload");
		$("#purchase-returnOrderDetail-auxremainder").addClass("inputload");
		$.ajax({
			url :'basefiles/computeGoodsByUnitnum.do',
			type:'post',
			data:{unitnum:unitnum,goodsid:goodsid,auxunitid:auxunitid,taxprice:taxprice,taxtype:taxtype,notaxprice:notaxprice},
			dataType:'json',
			async:false,
			success:function(json){
				$("#purchase-returnOrderDetail-taxamount").val(json.taxamount);
				$("#purchase-returnOrderDetail-notaxamount").val(json.notaxamount);
				$("#purchase-returnOrderDetail-tax").val(json.tax);
				$("#purchase-returnOrderDetail-taxtypename").val(json.taxtypename);
				$("#purchase-returnOrderDetail-auxnumdetail").val(json.auxnumdetail);
				$("#purchase-returnOrderDetail-auxunitnum").val(json.auxnum);
				$("#purchase-returnOrderDetail-auxunitname").val(json.auxunitname);
				$("#purchase-returnOrderDetail-span-auxunitname").html(json.auxunitname);
				$("#purchase-returnOrderDetail-unitname").val(json.unitname);
				$("#purchase-returnOrderDetail-span-unitname").html(json.unitname);

				$("#purchase-returnOrderDetail-taxprice").val(json.taxprice);
				$("#purchase-returnOrderDetail-notaxprice").val(json.notaxprice);
				$("#purchase-returnOrderDetail-boxprice").val(json.boxprice);
				$("#purchase-returnOrderDetail-noboxprice").val(json.noboxprice);

				$("#purchase-returnOrderDetail-auxnum").val(json.auxInteger);
				$("#purchase-returnOrderDetail-unitnum-auxremainder").val(json.auxremainder);
				if(json.auxrate!=null){
					$("#purchase-returnOrderAddPag-unitnum-auxremainder").attr("max",json.auxrate-1);
				}
				$("#purchase-returnOrderDetail-auxnum").removeClass("inputload");
				$("#purchase-returnOrderDetail-auxremainder").removeClass("inputload");
			}
		});
	}
	//通过辅单位数量
	function computeNumByAux(){
		var goodsid= $("#purchase-returnOrderDetail-goodsid").goodsWidget("getValue");
		var auxunitid = $("#purchase-returnOrderDetail-auxunitid").val();
		var taxprice = $("#purchase-returnOrderDetail-taxprice").val();
		if(taxprice==null || taxprice==""){
			taxprice="0";
		}
		var notaxprice = $("#purchase-returnOrderDetail-notaxprice").val();
		var taxtype = $("#purchase-returnOrderDetail-taxtype").val();
		var auxInterger = $("#purchase-returnOrderDetail-auxnum").val();
		var auxremainder = $("#purchase-returnOrderDetail-unitnum-auxremainder").val();

		$("#purchase-returnOrderDetail-auxnum").addClass("inputload");
		$("#purchase-returnOrderDetail-auxremainder").addClass("inputload");
		$("#purchase-returnOrderDetail-unitnum").addClass("inputload");
		$.ajax({
			url :'basefiles/computeGoodsByAuxnum.do',
			type:'post',
			data:{auxInterger:auxInterger,auxremainder:auxremainder,goodsid:goodsid,auxunitid:auxunitid,taxprice:taxprice,taxtype:taxtype,notaxprice:notaxprice},
			dataType:'json',
			async:false,
			success:function(json){
				$("#purchase-returnOrderDetail-taxamount").val(json.taxamount);
				$("#purchase-returnOrderDetail-notaxamount").val(json.notaxamount);
				$("#purchase-returnOrderDetail-tax").val(json.tax);
				$("#purchase-returnOrderDetail-taxtypename").val(json.taxtypename);

				$("#purchase-returnOrderDetail-taxprice").val(json.taxprice);
				$("#purchase-returnOrderDetail-notaxprice").val(json.notaxprice);

				$("#purchase-returnOrderDetail-unitnum").val(json.mainnum);
				$("#purchase-returnOrderDetail-auxnum").val(json.auxInterger);
				$("#purchase-returnOrderDetail-unitnum-auxremainder").val(json.auxremainder);
				$("#purchase-returnOrderDetail-auxnumdetail").val(json.auxnumdetail);

				$("#purchase-returnOrderDetail-auxnum").removeClass("inputload");
				$("#purchase-returnOrderDetail-auxremainder").removeClass("inputload");
				$("#purchase-returnOrderDetail-unitnum").removeClass("inputload");
			}
		});
	}
	function saveOrderDetail(isGoOn){
		$("#purchase-returnOrderDetail-remark").focus();
		var flag=$("#purchase-form-returnOrderDetailAddPage").form('validate');
		if(!flag){
			return false;
		}
		var goodsid=$("#purchase-returnOrderDetail-goodsid").goodsWidget('getValue');
		if(goodsid==null || goodsid==""){
			$.messager.alert("提醒","抱歉，请选择商品！");
			return false;
		}
		var formdata=$("#purchase-form-returnOrderDetailAddPage").serializeJSON();
		var addflag = true;
		var data=$("#purchase-returnOrderAddPage-returnOrdertable").datagrid('getRows');
		if(data!=null && data.length>0){
			for(var i=0;i<data.length;i++){
				if(data[i].goodsid && data[i].goodsid==goodsid && data[i].summarybatchid==formdata.summarybatchid && formdata.summarybatchid!=null){
					addflag=false;
					break;
				}
			}
		}
		if(!addflag){
			$.messager.alert("提醒","抱歉，采购退货通知单中已经存在该商品！");
			return false;
		}

		var unitnum=$("#purchase-returnOrderDetail-unitnum").val();
		if(unitnum==null || unitnum=="" || unitnum==0){
			$.messager.alert("提醒","抱歉，请填写数量！");
			return false;
		}
		var usablenum = $("#purchase-returnOrderDetail-existingnum").val();
		if(Number(unitnum) > Number(usablenum)){
			$.messager.alert("提醒","该商品可用量不足");
			$("#purchase-returnOrderDetail-unitnum").focus();
			return false;
		}

		if(formdata){
			var widgetJson = $("#purchase-returnOrderDetail-goodsid").goodsWidget('getObject');
			var goodsInfo = {name:widgetJson.name,brandName:widgetJson.brandName,
				model:widgetJson.model,barcode:widgetJson.barcode,boxnum:widgetJson.boxnum};
			formdata.goodsInfo = goodsInfo;
			var index=getAddRowIndex();
			$("#purchase-returnOrderAddPage-returnOrdertable").datagrid('updateRow',{
				index:index,
				row:formdata
			});
			footerReCalc();
			if(index>=14){
				var rows=$("#purchase-returnOrderAddPage-returnOrdertable").datagrid('getRows');
				if(index == rows.length - 1){
					$("#purchase-returnOrderAddPage-returnOrdertable").datagrid('appendRow',{});
				}
			}
		}
		if(isGoOn){
			orderDetailAddSaveGoOnDialog();
		}else{
			$("#purchase-returnOrderAddPage-dialog-DetailOper-content").dialog("close");
		}
	}
	function priceChange(type, id){ //1含税单价或2未税单价改变计算对应数据
		var price = $(id).val();
		var goodsid = $("#purchase-returnOrderDetail-goodsid").goodsWidget("getValue");
		if(goodsid==null || goodsid==""){
			return false;
		}
		priceHighTip();

		var taxtype = $("#purchase-returnOrderDetail-taxtype").val();
		var unitnum = $("#purchase-returnOrderDetail-unitnum").val();
		var auxnum = $("#purchase-returnOrderDetail-auxnum").val();
		$.ajax({
			url:'purchase/common/getAmountChanger.do',
			dataType:'json',
			async:false,
			type:'post',
			data:'type='+ type +'&price='+ price +'&taxtype='+ taxtype +'&unitnum='+ unitnum+ '&id='+ goodsid,
			success:function(json){
				$("#purchase-returnOrderDetail-taxprice").val(json.taxPrice);
				$("#purchase-returnOrderDetail-boxprice").val(json.boxprice);
				$("#purchase-returnOrderDetail-noboxprice").val(json.noboxprice);
				$("#purchase-returnOrderDetail-taxamount").val(json.taxAmount);
				$("#purchase-returnOrderDetail-notaxprice").val(json.noTaxPrice);
				$("#purchase-returnOrderDetail-notaxamount").val(json.noTaxAmount);
				$("#purchase-returnOrderDetail-tax").val(json.tax);
			}
		});
	}

	function priceHighTip(){
		var highestbuyprice = $("#purchase-returnOderDeail-highestbuyprice").val();

		var newbuyprice = $("#purchase-returnOrderDetail-taxprice").val();
		if(highestbuyprice < newbuyprice){
			$("#temp").remove();
			$("#purchase-returnOrderDetail-taxprice")/*.next().next()*/.after(
					'<div  id="temp"><br/><span class="tempspan"><font color="red">注意：该商品的采购价为'+highestbuyprice+'</font></span><div>'
			);
			$("#purchase-returnOrderDetail-taxprice").focus();

		}else{
			$("#temp").remove();
		}
	}
	function boxpriceChange(id){
        var $this = $(id);
        $this.css({'background':'url(image/loading.gif) right top no-repeat'});
		var price = $(id).val();
		var goodsid = $("#purchase-returnOrderDetail-goodsid").goodsWidget("getValue");
		if(goodsid==null || goodsid==""){
			return false;
		}
		var taxtype = $("#purchase-returnOrderDetail-taxtype").val();
		var unitnum = $("#purchase-returnOrderDetail-unitnum").val();
		var auxnum = $("#purchase-returnOrderDetail-auxnum").val();
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
                $("#purchase-returnOrderDetail-taxprice").val(json.taxprice);
                $("#purchase-returnOrderDetail-noboxprice").val(json.noboxprice);
                $("#purchase-returnOrderDetail-boxprice").val(json.boxprice);
                $("#purchase-returnOrderDetail-taxamount").val(json.taxamount);
                $("#purchase-returnOrderDetail-notaxprice").val(json.notaxprice);
                $("#purchase-returnOrderDetail-notaxamount").val(json.notaxamount);
                $("#purchase-returnOrderDetail-tax").val(json.tax);
				$this.css({'background':''});
			}
		});
	}
	$(function(){
		$("#purchase-returnOrderDetail-goodsid").goodsWidget({
			width:130,
			singleSelect:true,
			required:true,
			storageid:storageid,
			queryAllBySupplier:'${supplierid}',
			onSelect:function(data){
				$("#temp").remove();
				$("#purchase-returnOrderDetail-goodsbrandName").val(data.brandName);
				$("#purchase-returnOrderDetail-goodsmodel").val(data.model);
				$("#purchase-returnOrderDetail-barcode").val(data.barcode);
				$("#purchase-returnOrderDetail-taxtype").val(data.defaulttaxtype);
				$("#purchase-returnOrderDetail-unitid").val(data.mainunit);
				$("#purchase-returnOrderDetail-unitname").val(data.mainunitName);
				$("#purchase-returnOrderDetail-span-unitname").html(data.mainunitName);
				$("#purchase-returnOrderDetail-auxunitid").val(data.auxunitid)
				$("#purchase-returnOrderDetail-auxunitname").val(data.auxunitname);
				$("#purchase-returnOrderDetail-span-auxunitname").html(data.auxunitname);
				$("#purchase-returnOrderDetail-boxnum").val(data.boxnum);
				$("#purchase-returnOderDeail-newbuyprice").val(data.newbuyprice);
				$("#purchase-returnOderDeail-highestbuyprice").val(data.highestbuyprice);
				<c:choose>
				<c:when test="${purchasePriceType == 1}">
				$("#purchase-returnOrderDetail-taxprice").val(data.highestbuyprice);
				</c:when>
				<c:when test="${purchasePriceType == 2}">
				$("#purchase-returnOrderDetail-taxprice").val(data.newbuyprice);
				</c:when>
				<c:otherwise>
				$("#purchase-returnOrderDetail-taxprice").val(data.newbuyprice);
				</c:otherwise>
				</c:choose>
				priceHighTip();
				$("#purchase-returnOrderDetail-batchno").widget("clear");
				if(data.isbatch=="1"){
					$("#purchase-returnOrderDetail-batchno").widget("enable");
					$("#purchase-returnOrderDetail-unitnum").val(0);
					$("#purchase-returnOrderDetail-batchno").attr("readonly",false);
					$("#purchase-returnOrderDetail-batchno").removeClass("no_input");
					var storageid = $("#purchase-returnOrderAddPage-storage").widget("getValue");
					var param = null;
					if(storageid!=null && storageid!=""){
						param = [{field:'goodsid',op:'equal',value:data.id},
							{field:'storageid',op:'equal',value:storageid}];
					}else{
						param = [{field:'goodsid',op:'equal',value:data.id}];
					}
					$("#purchase-returnOrderDetail-batchno").widget({
						referwid:'RL_T_STORAGE_BATCH_LIST',
						param:param,
						width:165,
						required:true,
						singleSelect:true,
						onSelect: function(obj){
							$("#purchase-returnOrderDetail-detail-summarybatchid").val(obj.id);
							$("#purchase-returnOrderDetail-detail-storageid").val(obj.storageid);
							$("#purchase-returnOrderDetail-detail-storagename").val(obj.storagename);
							$("#purchase-returnOrderDetail-storagelocationname").val(obj.storagelocationname);
							$("#purchase-returnOrderDetail-storagelocationid").val(obj.storagelocationid);
							$("#purchase-returnOrderDetail-produceddate").val(obj.produceddate);
							$("#purchase-returnOrderDetail-deadline").val(obj.deadline);

							$("#purchase-returnOrderDetail-existingnum").val(obj.usablenum);
							$("#purchase-returnOrderDetail-unitnum").attr("usablenum",obj.usablenum);
							$("#purchase-returnOrderDetail-unitnum").val(obj.usablenum);
							$("#purchase-returnOrderDetail-loadInfo").html("现存量：<font color='green'>"+obj.existingnum+obj.unitname+"</font>&nbsp;可用量：<font color='green'>"+ (obj.usablenum!=null?obj.usablenum:0) +obj.unitname+"</font>");
							priceHighTip();
							computeNum();
							$("#purchase-returnOrderDetail-auxnum").focus();
							$("#purchase-returnOrderDetail-auxnum").select();
						},
						onClear:function(){
							$("#purchase-returnOrderDetail-loadInfo").html("&nbsp;");
							$("#purchase-returnOrderDetail-existingnum").val(0);
							$("#purchase-returnOrderDetail-detail-summarybatchid").val("");
							$("#purchase-returnOrderDetail-detail-storageid").val("");
							$("#purchase-returnOrderDetail-detail-storagename").val("");
							$("#purchase-returnOrderDetail-storagelocationname").val("");
							$("#purchase-returnOrderDetail-storagelocationid").val("");
							$("#purchase-returnOrderDetail-produceddate").val("");
							$("#purchase-returnOrderDetail-deadline").val("");
						}
					});
					$("#purchase-returnOrderDetail-batchno").focus();
				}else{
					$("#purchase-returnOrderDetail-loadInfo").html("现存量：<font color='green'>"+data.highestinventory+data.mainunitName+"</font>&nbsp;可用量：<font color='green'>"+ data.newinventory +data.mainunitName+"</font>");
					$("#purchase-returnOrderDetail-batchno").widget("disable");
					$("#purchase-returnOrderDetail-existingnum").val(data.newinventory);
					$("#purchase-returnOrderDetail-unitnum").attr("usablenum",data.newinventory);
					$("#purchase-returnOrderDetail-unitnum").val(data.newinventory);
					computeNum();
					$("#purchase-returnOrderDetail-auxnum").focus();
					$("#purchase-returnOrderDetail-auxnum").select();
				}
			},
			onClear:function(){
				$("#purchase-returnOrderDetail-loadInfo").html("&nbsp;");
				$("#purchase-returnOrderDetail-batchno").widget("clear");
				$("#purchase-returnOrderDetail-detail-summarybatchid").val("");
				$("#purchase-returnOrderDetail-detail-storageid").val("");
				$("#purchase-returnOrderDetail-detail-storagename").val("");
				$("#purchase-returnOrderDetail-storagelocationname").val("");
				$("#purchase-returnOrderDetail-storagelocationid").val("");
				$("#purchase-returnOrderDetail-produceddate").val("");
				$("#purchase-returnOrderDetail-deadline").val("");
			}
		});
		$("#purchase-returnOrderDetail-batchno").widget({
			referwid:'RL_T_STORAGE_BATCH_LIST',
			width:165,
			disabled:true,
			singleSelect:true
		});

		$("#purchase-returnOrderDetail-unitnum").change(function(){
			if($(this).validatebox('isValid')){
				computeNum();
			}
		});
		$("#purchase-returnOrderDetail-auxnum").change(function(){
			if($(this).validatebox('isValid')){
				computeNumByAux();
			}
		});
		$("#purchase-returnOrderDetail-unitnum-auxremainder").change(function(){
			if($(this).validatebox('isValid')){
				computeNumByAux();
			}
		});
		$("#purchase-returnOrderDetail-taxprice").focus(function(){
			if(!$(this).hasClass("readonly")){
				$(this).select();
			}else{
				$("#purchase-returnOrderDetail-notaxprice").focus();
			}
		});

		$("#purchase-returnOrderDetail-taxprice").change(function(){
			if($(this).validatebox('isValid') && !$(this).hasClass("readonly")){
				priceChange("1",this);
			}
		});

		$("#purchase-returnOrderDetail-notaxprice").change(function(){
			if($(this).validatebox('isValid')  && !$(this).hasClass("readonly") ){
				priceChange("2",this);
			}
		});
		$("#purchase-returnOrderDetail-boxprice").change(function(){
			if($(this).validatebox('isValid')  && !$(this).hasClass("readonly") ){
				boxpriceChange(this);
			}
		});
		$("#purchase-returnOrderDetail-notaxprice").focus(function(){
			if(!$(this).hasClass("readonly")){
				$(this).select();
			}else{
				$("#purchase-returnOrderDetail-remark").focus();
				$("#purchase-returnOrderDetail-remark").select();
			}
		});

		$("#purchase-returnOrderDetailAddPage-addSave").click(function(){
			saveOrderDetail(false);
		});

		$("#purchase-returnOrderDetailAddPage-addSaveGoOn").click(function(){
			saveOrderDetail(true);
		});

		$("#purchase-returnOrderDetail-auxnum").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#purchase-returnOrderDetail-auxnum").blur();
				$("#purchase-returnOrderDetail-unitnum-auxremainder").focus();
				$("#purchase-returnOrderDetail-unitnum-auxremainder").select();
			}
		});
		$("#purchase-returnOrderDetail-unitnum-auxremainder").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#purchase-returnOrderDetail-unitnum-auxremainder").blur();
				$("#purchase-returnOrderDetail-unitnum").focus();
				$("#purchase-returnOrderDetail-unitnum").select();
			}
		});
		$("#purchase-returnOrderDetail-unitnum").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#purchase-returnOrderDetail-unitnum").blur();
				$("#purchase-returnOrderDetail-taxprice").focus();
			}
		});
		$("#purchase-returnOrderDetail-taxprice").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#purchase-returnOrderDetail-taxprice").blur();
				$("#purchase-returnOrderDetail-notaxprice").focus();
			}
		});
		$("#purchase-returnOrderDetail-notaxprice").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#purchase-returnOrderDetail-notaxprice").blur()
				$("#purchase-returnOrderDetail-remark").focus();
				$("#purchase-returnOrderDetail-remark").select();
			}
		});
		$("#purchase-returnOrderDetail-remark").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#purchase-returnOrderDetail-remark").blur();
				$("#purchase-returnOrderDetailAddPage-addSaveGoOn").focus();
			}
		});
		$("#purchase-returnOrderDetailAddPage-addSaveGoOn").die("keydown").live("keydown",function(event){
			if(event.keyCode==13){
				saveOrderDetail(true);
			}
		});

	});
</script>
</body>
</html>
