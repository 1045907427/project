<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>其他出库单明细修改</title>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border:false">
		<form action="" method="post" id="storage-form-storageOtherOutDetailAddPage">
			<table  border="0" class="box_table">
				<tr>
					<td></td>
					<td></td>
					<td colspan="2" id="storage-storageOtherOut-loadInfo" style="text-align: left;">&nbsp;</td>
				</tr>
				<tr>
					<td width="120">选择商品:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-storageOtherOut-goodsname" width="180" class="no_input" readonly="readonly"/>
						<input type="hidden" id="storage-storageOtherOut-goodsid" name="goodsid"/>
						<input type="hidden" id="storage-storageOtherOut-summarybatchid" name="summarybatchid">
					</td>
					<td>批次号:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-storageOtherOut-batchno" name="batchno"/>
						<input type="hidden" id="storage-storageOtherOut-isbatch" value="${isbatch}"/>
					</td>
				</tr>
				<tr>
					<td>数量:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-storageOtherOut-unitnum" name="unitnum" class="formaterNum easyui-validatebox" data-options="required:true,validType:['intOrFloatNum[${decimallen}]','max']"/>
					</td>
					<td width="120">辅数量:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-storageOtherOut-unitnum-aux" name="auxnum" style="width:70px;" class="formaterNum easyui-validatebox" data-options="required:true,validType:'integer'"/>
						<span id="storage-storageOtherOut-auxunitname1" style="float: left;"></span>
						<input type="text" id="storage-storageOtherOut-unitnum-unit" name="auxremainder" style="width:70px;" class="formaterNum easyui-validatebox" data-options="required:true,validType:'intOrFloatNum[${decimallen}]'"/>
						<span id="storage-storageOtherOut-goodsunitname1" style="float: left;"></span>
						<input type="hidden" id="storage-storageOtherOut-auxunitnumdetail" name="auxnumdetail" class="no_input" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>条形码:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-storageOtherOut-goodsbarcode" class="no_input" readonly="readonly"/>
					</td>
					<td>箱装量:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-storageOtherOut-boxnum" class="no_input" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>商品品牌:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-storageOtherOut-goodsbrandName" class="no_input" readonly="readonly"/>
					</td>
					<td>规格型号:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-storageOtherOut-goodsmodel" class="no_input" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>主单位:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-storageOtherOut-goodsunitname" name="unitname" class="no_input" readonly="readonly"/>
						<input type="hidden" id="storage-storageOtherOut-goodsunitid" name="unitid"/>
					</td>
					<td>辅单位:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-storageOtherOut-auxunitname" name="auxunitname" class="no_input" readonly="readonly"/>
						<input type="hidden" id="storage-storageOtherOut-auxunitid" name="auxunitid"/>
					</td>
				</tr>

				<tr class="hiddenclass">
					<td>含税单价:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-storageOtherOut-taxprice" name="taxprice">
					</td>
					<td>含税金额:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-storageOtherOut-taxamount" name="taxamount" class="no_input" readonly="readonly"/>
					</td>
				</tr>
				<tr class="hiddenclass">
					<td>未税单价:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-storageOtherOut-notaxprice" name="notaxprice" class="no_input" readonly="readonly"/>
					</td>
					<td>未税金额:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-storageOtherOut-notaxamount" name="notaxamount" class="no_input" readonly="readonly"/>
					</td>
				</tr>

				<tr>
					<td>税种:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-storageOtherOut-taxtypename" name="taxtypename" class="no_input" readonly="readonly"/>
						<input type="hidden" id="storage-storageOtherOut-taxtype" name="taxtype" class="no_input" readonly="readonly"/>
					</td>
					<td class="hiddenclass">税额:</td>
					<td style="text-align: left;" class="hiddenclass">
						<input type="text" id="storage-storageOtherOut-tax" name="tax" class="no_input" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>所属库位:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-storageOtherOut-storagelocationid" name="storagelocationid" readonly="readonly" disabled="disabled"/>
						<input type="hidden" id="storage-storageOtherOut-storagelocationname"  name="storagelocationname"/>
					</td>

				</tr>
				<tr>
					<td>生产日期:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-storageOtherOut-produceddate" style="height: 20px;" name="produceddate" class="WdateRead" readonly="readonly"/>
					</td>
					<td>截止日期:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-storageOtherOut-deadline" style="height: 20px;" name="deadline" class="WdateRead" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>备注:</td>
					<td colspan="3" style="text-align: left;">
						<input id="storage-storageOtherOut-remark" type="text" name="remark" style="width: 488px;" maxlength="200"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="buttonDetailBG" style="height:30px;text-align: right;">
			快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
			<input type="button" value="继续修改" name="savegoon" id="storage-savegoon-storageOtherOutDetailEditPage" />
		</div>
	</div>
</div>
<script type="text/javascript">
    $(".hiddenclass").hide();
    <security:authorize url="/storage/storageOtherOutShowAmount.do">
    $(".hiddenclass").show();
    </security:authorize>
	//加载数据
	var object = $("#storage-datagrid-storageOtherOutAddPage").datagrid("getSelected");
	$("#storage-form-storageOtherOutDetailAddPage").form("load",object);
	$("#storage-storageOtherOut-boxnum").val(object.goodsInfo.boxnum);
	$("#storage-storageOtherOut-goodsname").val(object.goodsInfo.name);
	$("#storage-storageOtherOut-goodsbrandName").val(object.goodsInfo.brandName);
	$("#storage-storageOtherOut-goodsmodel").val(object.goodsInfo.model);
	$("#storage-storageOtherOut-goodsbarcode").val(object.goodsInfo.barcode);
	$("#storage-storageOtherOut-boxnum").val(object.goodsInfo.boxnum);
	$("#storage-storageOtherOut-goodsunitname1").html(object.unitname);
	$("#storage-storageOtherOut-auxunitname1").html(object.auxunitname);
	$("#storage-storageOtherOut-taxtypename").val(object.goodsInfo.defaulttaxtypeName);
	var maxnum = 0;
	$(function(){
		$.extend($.fn.validatebox.defaults.rules, {
			max: {
				validator: function (value) {
					if(value > maxnum){
						return false;
					}else{
						return true;
					}
				},
				message:'数量超过可用量'
			}
		});
		$("#storage-storageOtherOut-storagelocationid").widget({
			name:'t_storage_salereject_enter_detail',
			width:165,
			col:'storagelocationid',
			singleSelect:true,
			onSelect:function(data){
				$("#storage-storageOtherOut-storagelocationname").val(data.name);
			},
			onClear:function(){
				$("#storage-storageOtherOut-storagelocationname").val("");
			}
		});
		$("#storage-storageOtherOut-taxprice").change(function(){
			computNum();
		});
		$("#storage-storageOtherOut-taxamount").numberbox({
			precision:2,
			groupSeparator:','
		});
		$("#storage-storageOtherOut-notaxprice").numberbox({
			precision:2,
			groupSeparator:','
		});
		$("#storage-storageOtherOut-notaxamount").numberbox({
			precision:2,
			groupSeparator:','
		});
		$("#storage-storageOtherOut-tax").numberbox({
			precision:2,
			groupSeparator:','
		});
		$("#storage-storageOtherOut-deadline").click(function(){
			if(!$("#storage-storageOtherOut-batchno").hasClass("no_input")){
				WdatePicker({dateFmt:'yyyy-MM-dd'});
			}
		});
		$("#storage-storageOtherOut-produceddate").click(function(){
			if(!$("#storage-storageOtherOut-batchno").hasClass("no_input")){
				WdatePicker({dateFmt:'yyyy-MM-dd'});
			}
		});
		$("#storage-storageOtherOut-unitnum").change(function(){
			computNum();
		});
		$("#storage-storageOtherOut-unitnum-aux").change(function(){
			computNumByAux();
		});
		$("#storage-storageOtherOut-unitnum-unit").change(function(){
			computNumByAux();
		});
		$("#storage-storageOtherOut-unitnum").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#storage-storageOtherOut-unitnum").blur();
				$("#storage-storageOtherOut-unitnum-aux").focus();
				$("#storage-storageOtherOut-unitnum-aux").select();
			}
		});
		$("#storage-storageOtherOut-unitnum-aux").die("keydown").live("keydown",function(event){
			if(event.keyCode==13){
				$("#storage-storageOtherOut-unitnum-auxm").blur();
				$("#storage-storageOtherOut-unitnum-unit").focus();
				$("#storage-storageOtherOut-unitnum-unit").select();
			}
		});
		$("#storage-storageOtherOut-unitnum-unit").die("keydown").live("keydown",function(event){
			if(event.keyCode==13){
				$("#storage-storageOtherOut-unitnum-auxm").blur();
				$("#storage-storageOtherOut-taxprice").focus();
				$("#storage-storageOtherOut-taxprice").select();
			}
		});
		$("#storage-storageOtherOut-taxprice").die("keydown").live("keydown",function(event){
			if(event.keyCode==13){
				$("#storage-storageOtherOut-taxprice").blur();
				$("#storage-storageOtherOut-remark").focus();
				$("#storage-storageOtherOut-remark").select();
			}
		});
		$("#storage-storageOtherOut-remark").die("keydown").live("keydown",function(event){
			if(event.keyCode==13){
				$("#storage-storageOtherOut-taxprice").blur();
				$("#storage-savegoon-storageOtherOutDetailEditPage").focus();
			}
		});
		$("#storage-savegoon-storageOtherOutDetailEditPage").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				editSaveDetail(true);
			}
		});
		$("#storage-savegoon-storageOtherOutDetailEditPage").click(function(){
			editSaveDetail(true);
		});
	});
	//通过总数量 计算数量 金额换算
	function computNum(){
		var goodsid= $("#storage-storageOtherOut-goodsid").val();
		var auxunitid = $("#storage-storageOtherOut-auxunitid").val();
		var unitnum = $("#storage-storageOtherOut-unitnum").val();
		var taxprice = $("#storage-storageOtherOut-taxprice").val();
		var notaxprice = $("#storage-storageOtherOut-notaxprice").numberbox("getValue");
		var taxtype = $("#storage-storageOtherOut-taxtype").val();
		if(taxprice==null || taxprice==""){
			return false;
		}
		$("#storage-storageOtherOut-unitnum").addClass("inputload");
		$.ajax({
			url :'basefiles/computeGoodsByUnitnum.do',
			type:'post',
			data:{unitnum:unitnum,goodsid:goodsid,auxunitid:auxunitid,taxprice:taxprice,taxtype:taxtype,notaxprice:notaxprice},
			dataType:'json',
			async:false,
			success:function(json){
				$("#storage-storageOtherOut-taxamount").numberbox("setValue",json.taxamount);
				$("#storage-storageOtherOut-notaxamount").numberbox("setValue",json.notaxamount);
				$("#storage-storageOtherOut-tax").numberbox("setValue",json.tax);
				$("#storage-storageOtherOut-taxtypename").val(json.taxtypename);
				$("#storage-storageOtherOut-auxunitnumdetail").val(json.auxnumdetail);
				$("#storage-storageOtherOut-auxunitnum").val(json.auxnum);
				$("#storage-storageOtherOut-auxunitname").val(json.auxunitname);
				$("#storage-storageOtherOut-auxunitname1").html(json.auxunitname);
				$("#storage-storageOtherOut-goodsunitname").val(json.unitname);
				$("#storage-storageOtherOut-goodsunitname1").html(json.unitname);

				$("#storage-storageOtherOut-taxprice").val(json.taxprice);
				$("#storage-storageOtherOut-notaxprice").numberbox("setValue",json.notaxprice);
				$("#storage-storageOtherOut-notaxamount").numberbox("setValue",json.notaxamount);

				$("#storage-storageOtherOut-unitnum-aux").val(json.auxInteger);
				$("#storage-storageOtherOut-unitnum-unit").val(json.auxremainder);
				if(json.auxrate!=null){
					$("#storage-storageOtherOut-unitnum-unit").attr("max",json.auxrate-1);
				}
				$("#storage-storageOtherOut-unitnum").removeClass("inputload");
			}
		});
	}
	//通过辅单位数量
	function computNumByAux(){
		var goodsid= $("#storage-storageOtherOut-goodsid").val();
		var auxunitid = $("#storage-storageOtherOut-auxunitid").val();
		var taxprice = $("#storage-storageOtherOut-taxprice").val();
		var notaxprice = $("#storage-storageOtherOut-notaxprice").numberbox("getValue");
		var taxtype = $("#storage-storageOtherOut-taxtype").val();
		var auxInterger = $("#storage-storageOtherOut-unitnum-aux").val();
		var auxremainder = $("#storage-storageOtherOut-unitnum-unit").val();
//			var auxmax = $("#storage-storageOtherOut-unitnum-unit").attr("max");
//			if(Number(auxremainder)>Number(auxmax)){
//				auxremainder = auxmax;
//				$("#storage-storageOtherOut-unitnum-unit").val(auxremainder);
//			}
		if(taxprice==null || taxprice=="" || notaxprice==null || notaxprice=="" || taxtype==null || taxtype==""){
			return false;
		}
		$("#storage-storageOtherOut-unitnum").addClass("inputload");
		$.ajax({
			url :'basefiles/computeGoodsByAuxnum.do',
			type:'post',
			data:{auxInterger:auxInterger,auxremainder:auxremainder,goodsid:goodsid,auxunitid:auxunitid,taxprice:taxprice,taxtype:taxtype,notaxprice:notaxprice},
			dataType:'json',
			async:false,
			success:function(json){
				$("#storage-storageOtherOut-taxamount").numberbox("setValue",json.taxamount);
				$("#storage-storageOtherOut-notaxamount").numberbox("setValue",json.notaxamount);
				$("#storage-storageOtherOut-tax").numberbox("setValue",json.tax);
				$("#storage-storageOtherOut-taxtypename").val(json.taxtypename);

				$("#storage-storageOtherOut-taxprice").val(json.taxprice);
				$("#storage-storageOtherOut-notaxprice").numberbox("setValue",json.notaxprice);
				$("#storage-storageOtherOut-notaxamount").numberbox("setValue",json.notaxamount);

				$("#storage-storageOtherOut-unitnum").val(json.mainnum);
				$("#storage-storageOtherOut-unitnum-aux").val(json.auxInterger);
				$("#storage-storageOtherOut-unitnum-unit").val(json.auxremainder);
				$("#storage-storageOtherOut-auxunitnumdetail").val(json.auxnumdetail);

				$("#storage-storageOtherOut-unitnum").removeClass("inputload");
			}
		});

	}
	//页面重置
	function otherEnterformReset(){
		$("#storage-form-storageOtherOutDetailAddPage").form("clear");
		$("#storage-storageOtherOut-auxunitname1").html("");
		$("#storage-storageOtherOut-goodsunitname1").html("");
		$("#storage-storageOtherOut-goodsid").focus();
	}
	function goodsNumControl(){
		var selectObject = $("#storage-datagrid-storageOtherOutAddPage").datagrid("getSelected");
		var storageInfo = $("#storage-storageOtherOut-storageid").widget('getObject');
		var isbatch = $("#storage-storageOtherOut-isbatch").val();
		if(isbatch=="1" && selectObject.summarybatchid!=null && selectObject.summarybatchid!=""){
			//控件其他出库的最大数量
			$.ajax({
				url :'storage/getStorageSummaryBatchInfo.do',
				type:'post',
				data:{summarybatchid:selectObject.summarybatchid},
				dataType:'json',
				success:function(json){
					if(json.storageSummaryBatch!=null){
						var unitname = $("#storage-storageOtherOut-goodsunitname").val();
						var status = $("#storage-storageOtherOut-status").val();
						var usablenum = json.storageSummaryBatch.usablenum;
						if(selectObject.id!=null && status=='2'){
							$.ajax({
								url :'storage/getStorageOtherOutDetailInfo.do',
								type:'post',
								data:{id:selectObject.id},
								dataType:'json',
								success:function(data){
									maxnum = Number(data.detail.unitnum)+Number(usablenum);
									$("#storage-storageOtherOut-loadInfo").html("现存量：<font color='green'>"+json.storageSummaryBatch.existingnum+unitname+"</font>&nbsp;可用量：<font color='green'>"+ maxnum +unitname+"</font>");
								}
							});
						}else{
							maxnum = Number(object.unitnum)+Number(json.storageSummaryBatch.usablenum);
							$("#storage-storageOtherOut-loadInfo").html("现存量：<font color='green'>"+json.storageSummaryBatch.existingnum+unitname+"</font>&nbsp;可用量：<font color='green'>"+ json.storageSummaryBatch.usablenum +unitname+"</font>");
						}
					}
				}
			});
		}else{
			var storageid = $("#storage-storageOtherOut-storageid").widget("getValue");
			//控件其他出库的最大数量
			$.ajax({
				url :'storage/getStorageSummarySumByGoodsid.do',
				type:'post',
				data:{goodsid:selectObject.goodsid,storageid:storageid},
				dataType:'json',
				success:function(json){
					if(json.storageSummary!=null){
						var unitname = $("#storage-storageOtherOut-goodsunitname").val();
						var status = $("#storage-storageOtherOut-status").val();
						var usablenum = json.storageSummary.usablenum;
						if(selectObject.id!=null && status=='2'){
							$.ajax({
								url :'storage/getStorageOtherOutDetailInfo.do',
								type:'post',
								data:{id:selectObject.id},
								dataType:'json',
								success:function(data){
									maxnum = Number(data.detail.unitnum)+Number(usablenum);
									$("#storage-storageOtherOut-loadInfo").html("现存量：<font color='green'>"+json.storageSummary.existingnum+unitname+"</font>&nbsp;可用量：<font color='green'>"+ maxnum +unitname+"</font>");
								}
							});
						}else{
							maxnum = Number(object.unitnum)+Number(json.storageSummary.usablenum);
							$("#storage-storageOtherOut-loadInfo").html("现存量：<font color='green'>"+json.storageSummary.existingnum+unitname+"</font>&nbsp;可用量：<font color='green'>"+ json.storageSummary.usablenum +unitname+"</font>");
						}
					}
				}
			});
		}

	}
	goodsNumControl();
</script>
</body>
</html>
