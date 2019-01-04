<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>采购退货通知单明细添加</title>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border:false">
		<form action="" method="post" id="purchase-form-returnOrderDetailEditPage">
			<table  border="0" class="box_table">
				<tr>
					<td></td>
					<td></td>
					<td colspan="2" id="purchase-returnOrderDetail-loadInfo" style="text-align: left;">&nbsp;</td>
				</tr>
				<tr>
					<td width="120">商品：</td>
					<td style="text-align: left;">
						<input type="text" id="purchase-returnOrderDetail-goodsname" width="180" class="no_input" readonly="readonly"/>
						<input type="hidden" id="purchase-returnOrderDetail-goodsid" name="goodsid" width="170"/>
					</td>
					<td>批次号：</td>
					<td>
						<input type="text" id="purchase-returnOrderDetail-batchno" name="batchno" class="no_input" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td style="text-align: right;">辅数量：</td>
					<td style="text-align: left;">
						<input type="text" id="purchase-returnOrderDetail-auxnum" name="auxnum" class="formaterNum easyui-validatebox" validType="integer" data-options="required:true" style="width:60px; float:left;" />
						<span id="purchase-returnOrderDetail-span-auxunitname" style="float: left;line-height:25px;">&nbsp;</span>
						<input type="text" id="purchase-returnOrderDetail-unitnum-auxremainder" name="auxremainder" class="formaterNum easyui-validatebox" validType="intOrFloatNum[${decimallen}]" required="true" style="width:60px;float:left;"/>
						<span id="purchase-returnOrderDetail-span-unitname" style="float: left;line-height:25px;">&nbsp;</span>
						<input type="hidden" id="purchase-returnOrderDetail-auxnumdetail" name="auxnumdetail" class="no_input" readonly="readonly"/>
					</td>
					<td style="text-align: right;">数量：</td>
					<td><input type="text" id="purchase-returnOrderDetail-unitnum" name="unitnum" class="formaterNum easyui-validatebox" validType="intOrFloatNum[${decimallen}]" required="true" />
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
						<input id="purchase-returnOrderDetail-boxnum" name="boxnum" type="text" class="readonly" readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td>含税单价：</td>
					<td  style="text-align: left;">
						<input type="text" id="purchase-returnOrderDetail-taxprice" name="taxprice" class="easyui-validatebox <c:if test="${colMap.taxprice == null }">readonly</c:if>" <c:if test="${colMap.taxprice == null }">readonly="readonly"</c:if> required="required" validType="intOrFloat"/>
					</td>
					<td>含税金额：</td>
					<td>
						<input type="text" id="purchase-returnOrderDetail-taxamount" name="taxamount" class="no_input easyui-validatebox" readonly="readonly" required="required" validType="intOrFloat"/>
					</td>
				</tr>
				<tr>
					<td>未税单价：</td>
					<td>
						<input type="text" id="purchase-returnOrderDetail-notaxprice" name="notaxprice"  class="easyui-validatebox <c:if test="${colMap.taxprice == null }">readonly</c:if>" <c:if test="${colMap.taxprice == null }">readonly="readonly"</c:if> required="required" validType="intOrFloat"/>
						<input type="hidden" name="highestbuyprice" id="purchase-returnOderDeail-highestbuyprice">
					</td>
					<td>未税金额：</td>
					<td>
						<input type="text" id="purchase-returnOrderDetail-notaxamount" name="notaxamount" class="no_input easyui-validatebox" readonly="readonly" required="required" validType="intOrFloat" />
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
						<input type="text" id="purchase-returnOrderDetail-tax" name="tax" class="no_input" readonly="readonly easyui-validatebox" required="required" validType="intOrFloat" />
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
					</td>
				</tr>
				<tr>
					<td>生产日期：</td>
					<td>
						<input type="text" id="purchase-returnOrderDetail-produceddate" style="height: 20px;" name="produceddate" class="no_input" readonly="readonly"/>
					</td>
					<td>有效截止日期：</td>
					<td>
						<input type="text" id="purchase-returnOrderDetail-deadline" style="height: 20px;" name="deadline" class="no_input" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>备注：</td>
					<td colspan="3" style="text-align: left;">
						<input type="text" id="purchase-returnOrderDetail-remark" name="remark" style="width: 488px;" maxlength="200"/>
					</td>
				</tr>
			</table>
			<input type="hidden" id="purchase-returnOrderDetail-isbatch" value="${isbatch}"/>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="buttonDetailBG" style="height:30px;text-align:right;">
			快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
			<input type="button" value="确 定" name="savenogo" id="purchase-returnOrderDetailEditPage-editSave" />
		</div>
	</div>
</div>
<script type="text/javascript">
	//加载数据
	var object = $("#purchase-returnOrderAddPage-returnOrdertable").datagrid("getSelected");
	var existingnum = object.usablenum;
	var numdetailHtml = "";
	$("#purchase-form-returnOrderDetailEditPage").form("load",object);
	$("#purchase-returnOrderDetail-goodsname").val(object.goodsInfo.name);
	$("#purchase-returnOrderDetail-goodsbrandName").val(object.goodsInfo.brandName);
	$("#purchase-returnOrderDetail-goodsmodel").val(object.goodsInfo.model);
	$("#purchase-returnOrderDetail-barcode").val(object.goodsInfo.barcode);
	$("#purchase-returnOrderDetail-boxnum").val(formatterBigNum(object.goodsInfo.boxnum));


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


    //通过总数量 计算数量 金额换算
	function computeNum(){
		var goodsid= $("#purchase-returnOrderDetail-goodsid").val();
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
				$("#purchase-returnOrderDetail-notaxamount").val(json.notaxamount);

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
		var goodsid= $("#purchase-returnOrderDetail-goodsid").val();
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
				$("#purchase-returnOrderDetail-notaxamount").val(json.notaxamount);

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
	function saveOrderDetail(){
		$("#purchase-returnOrderDetail-remark").focus();
		var flag=$("#purchase-form-returnOrderDetailEditPage").form('validate');
		if(!flag){
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
		var formdata=$("#purchase-form-returnOrderDetailEditPage").serializeJSON();

		if(formdata){
			$("#purchase-returnOrderAddPage-returnOrdertable").datagrid('updateRow',{
				index:editRowIndex,
				row:formdata
			});
			footerReCalc();
		}
		$("#purchase-returnOrderAddPage-dialog-DetailOper-content").dialog("close");
	}
	function priceChange(type, id){ //1含税单价或2未税单价改变计算对应数据
		var $this = $(id).css({'background':'url(image/loading.gif) right top no-repeat'});
		var price = $(id).val();
		var goodsid = $("#purchase-returnOrderDetail-goodsid").val();
		var highestbuyprice = $("#purchase-returnOderDeail-highestbuyprice").val();
		if(highestbuyprice < price){
			$("#temp").remove();
			$("#purchase-returnOrderDetail-taxprice")/*.next().next()*/.after(
					'<div  id="temp"><br/><span class="tempspan"><font color="red">注意：该商品的最高采购价为'+highestbuyprice+'</font></span><div>'
			);
			$("#purchase-returnOrderDetail-taxprice").focus();

		}else{
			$("#temp").remove();
		}
		if(goodsid==null || goodsid==""){
			return false;
		}
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
				$this.css({'background':''});
			}
		});
	}
	function boxpriceChange(id){
        var $this = $(id);
        $this.css({'background':'url(image/loading.gif) right top no-repeat'});
		var price = $(id).val();
		var goodsid = $("#purchase-returnOrderDetail-goodsid").val();
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
		$("#purchase-returnOrderDetailEditPage-editSave").click(function(){
			saveOrderDetail();
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
				$("#purchase-returnOrderDetailEditPage-editSave").focus();
			}
		});
		$("#purchase-returnOrderDetailEditPage-editSave").die("keydown").live("keydown",function(event){
			if(event.keyCode==13){
				saveOrderDetail(false);
			}
		});
	});
	function goodsNumControl(){
		var isbatch = $("#purchase-returnOrderDetail-isbatch").val();
		if(isbatch=="1"){
			$.ajax({
				url :'storage/getStorageSummaryBatchInfo.do',
				type:'post',
				data:{summarybatchid:object.summarybatchid},
				dataType:'json',
				success:function(json){
					if(json.storageSummaryBatch!=null){
						var unitname = $("#purchase-returnOrderDetail-unitname").val();
						var status = $("#purchase-returnOrderAddPage-status").val();
						var usablenum = json.storageSummaryBatch.usablenum;
						var maxnum = Number(object.unitnum)+Number(json.storageSummaryBatch.usablenum);
						numdetailHtml = "现存量：<font color='green'>"+json.storageSummaryBatch.existingnum+unitname+"</font>&nbsp;可用量：<font color='green'>"+ (json.storageSummaryBatch.usablenum!=null?json.storageSummaryBatch.usablenum:0) +unitname+"</font>";
						$("#purchase-returnOrderDetail-loadInfo").html(numdetailHtml);
						$("#purchase-returnOrderDetail-existingnum").val(json.storageSummaryBatch.usablenum);
						$("#purchase-returnOrderDetail-unitnum").attr("max", json.storageSummaryBatch.usablenum);
					}
				}
			});
		}else{
			var storageid = $("#purchase-returnOrderAddPage-storage").widget("getValue");
			//控件其他出库的最大数量
			$.ajax({
				url :'storage/getStorageSummarySumByGoodsid.do',
				type:'post',
				data:{goodsid:object.goodsid,storageid:storageid},
				dataType:'json',
				success:function(json){
					if(json.storageSummary!=null){
						var unitname = $("#purchase-returnOrderDetail-unitname").val();
						var status = $("#purchase-returnOrderAddPage-status").val();
						var usablenum = json.storageSummary.usablenum;
						var maxnum = Number(object.unitnum)+Number(json.storageSummary.usablenum);
						numdetailHtml = "现存量：<font color='green'>"+json.storageSummary.existingnum+unitname+"</font>&nbsp;可用量：<font color='green'>"+ json.storageSummary.usablenum +unitname+"</font>";
						$("#purchase-returnOrderDetail-loadInfo").html(numdetailHtml);
						$("#purchase-returnOrderDetail-existingnum").val(json.storageSummary.usablenum);
						$("#purchase-returnOrderDetail-unitnum").attr("max", json.storageSummary.usablenum);

					}
				}
			});
		}
	}
	goodsNumControl();
</script>
</body>
</html>