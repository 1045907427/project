<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>借货单明细修改</title>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border:false">
		<form action="" method="post" id="storage-form-lendDetailAddPage">
			<table  border="0" class="box_table">
				<tr>
					<td></td>
					<td></td>
					<td colspan="2" id="storage-lend-loadInfo" style="text-align: left;">&nbsp;</td>
				</tr>
				<tr>
					<td width="120">选择商品:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-lend-goodsname" width="180" class="no_input" readonly="readonly"/>
						<input type="hidden" id="storage-lend-goodsid" name="goodsid"/>
						<input type="hidden" id="storage-lend-summarybatchid" name="summarybatchid">
					</td>
					<td>批次号:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-lend-batchno" name="batchno"/>
						<input type="hidden" id="storage-lend-isbatch" value="${isbatch}"/>
					</td>
				</tr>
				<tr>
					<td>数量:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-lend-unitnum" name="unitnum" class="formaterNum easyui-validatebox" data-options="required:true,validType:['intOrFloatNum[${decimallen}]','max','min']"/>
					</td>
					<td width="120">辅数量:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-lend-unitnum-aux" name="auxnum" style="width:70px;" class="formaterNum easyui-validatebox" data-options="required:true,validType:'integer'"/>
						<span id="storage-lend-auxunitname1" style="float: left;"></span>
						<input type="text" id="storage-lend-unitnum-unit" name="auxremainder" style="width:70px;" class="formaterNum easyui-validatebox" data-options="required:true,validType:'intOrFloatNum[${decimallen}]'"/>
						<span id="storage-lend-goodsunitname1" style="float: left;"></span>
						<input type="hidden" id="storage-lend-auxunitnumdetail" name="auxnumdetail" class="no_input" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>条形码:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-lend-goodsbarcode" class="no_input" readonly="readonly"/>
					</td>
					<td>箱装量:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-lend-boxnum" class="no_input" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>商品品牌:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-lend-goodsbrandName" class="no_input" readonly="readonly"/>
					</td>
					<td>规格型号:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-lend-goodsmodel" class="no_input" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>主单位:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-lend-goodsunitname" name="unitname" class="no_input" readonly="readonly"/>
						<input type="hidden" id="storage-lend-goodsunitid" name="unitid"/>
					</td>
					<td>辅单位:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-lend-auxunitname" name="auxunitname" class="no_input" readonly="readonly"/>
						<input type="hidden" id="storage-lend-auxunitid" name="auxunitid"/>
					</td>
				</tr>
				<tr>
					<td>含税单价:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-lend-taxprice" name="taxprice">
					</td>
					<td>含税金额:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-lend-taxamount" name="taxamount" class="no_input" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>未税单价:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-lend-notaxprice" name="notaxprice" class="no_input" readonly="readonly"/>
					</td>
					<td>未税金额:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-lend-notaxamount" name="notaxamount" class="no_input" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>税种:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-lend-taxtypename" name="taxtypename" class="no_input" readonly="readonly"/>
						<input type="hidden" id="storage-lend-taxtype" name="taxtype" class="no_input" readonly="readonly"/>
					</td>
					<td>税额:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-lend-tax" name="tax" class="no_input" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>所属库位:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-lend-storagelocationid" name="storagelocationid" readonly="readonly" disabled="disabled"/>
						<input type="hidden" id="storage-lend-storagelocationname"  name="storagelocationname"/>
					</td>

				</tr>
				<tr>
					<td>生产日期:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-lend-produceddate" style="height: 20px;" name="produceddate" class="WdateRead" readonly="readonly"/>
					</td>
					<td>截止日期:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-lend-deadline" style="height: 20px;" name="deadline" class="WdateRead" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>备注:</td>
					<td colspan="3" style="text-align: left;">
						<input id="storage-lend-remark" type="text" name="remark" style="width: 488px;" maxlength="200"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="buttonDetailBG" style="height:30px;text-align: right;">
			快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
			<input type="button" value="继续修改" name="savegoon" id="storage-savegoon-lendDetailEditPage" />
		</div>
	</div>
</div>
<script type="text/javascript">
	//加载数据
	var object = $("#storage-datagrid-lendAddPage").datagrid("getSelected");
	$("#storage-form-lendDetailAddPage").form("load",object);
	$("#storage-lend-boxnum").val(object.goodsInfo.boxnum);
	$("#storage-lend-goodsname").val(object.goodsInfo.name);
	$("#storage-lend-goodsbrandName").val(object.goodsInfo.brandName);
	$("#storage-lend-goodsmodel").val(object.goodsInfo.model);
	$("#storage-lend-goodsbarcode").val(object.goodsInfo.barcode);
	$("#storage-lend-boxnum").val(object.goodsInfo.boxnum);
	$("#storage-lend-goodsunitname1").html(object.unitname);
	$("#storage-lend-auxunitname1").html(object.auxunitname);
	$("#storage-lend-taxtypename").val(object.goodsInfo.defaulttaxtypeName);
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
			},
			min:{
				validator: function (value) {
					if(value == 0){
						return false;
					}else{
						return true;
					}
				},
				message:'数量不可为0'
			}
		});
		$("#storage-lend-storagelocationid").widget({
			name:'t_storage_salereject_enter_detail',
			width:165,
			col:'storagelocationid',
			singleSelect:true,
			onSelect:function(data){
				$("#storage-lend-storagelocationname").val(data.name);
			},
			onClear:function(){
				$("#storage-lend-storagelocationname").val("");
			}
		});
		$("#storage-lend-taxprice").change(function(){
			computNum();
		});
		$("#storage-lend-taxamount").numberbox({
			precision:2,
			groupSeparator:','
		});
		$("#storage-lend-notaxprice").numberbox({
			precision:2,
			groupSeparator:','
		});
		$("#storage-lend-notaxamount").numberbox({
			precision:2,
			groupSeparator:','
		});
		$("#storage-lend-tax").numberbox({
			precision:2,
			groupSeparator:','
		});
		$("#storage-lend-deadline").click(function(){
			if(!$("#storage-lend-batchno").hasClass("no_input")){
				WdatePicker({dateFmt:'yyyy-MM-dd'});
			}
		});
		$("#storage-lend-produceddate").click(function(){
			if(!$("#storage-lend-batchno").hasClass("no_input")){
				WdatePicker({dateFmt:'yyyy-MM-dd'});
			}
		});
		$("#storage-lend-unitnum").change(function(){
			computNum();
		});
		$("#storage-lend-unitnum-aux").change(function(){
			computNumByAux();
		});
		$("#storage-lend-unitnum-unit").change(function(){
			computNumByAux();
		});
		$("#storage-lend-unitnum").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#storage-lend-unitnum").blur();
				$("#storage-lend-unitnum-aux").focus();
				$("#storage-lend-unitnum-aux").select();
			}
		});
		$("#storage-lend-unitnum-aux").die("keydown").live("keydown",function(event){
			if(event.keyCode==13){
				$("#storage-lend-unitnum-auxm").blur();
				$("#storage-lend-unitnum-unit").focus();
				$("#storage-lend-unitnum-unit").select();
			}
		});
		$("#storage-lend-unitnum-unit").die("keydown").live("keydown",function(event){
			if(event.keyCode==13){
				$("#storage-lend-unitnum-auxm").blur();
				$("#storage-lend-taxprice").focus();
				$("#storage-lend-taxprice").select();
			}
		});
		$("#storage-lend-taxprice").die("keydown").live("keydown",function(event){
			if(event.keyCode==13){
				$("#storage-lend-taxprice").blur();
				$("#storage-lend-remark").focus();
				$("#storage-lend-remark").select();
			}
		});
		$("#storage-lend-remark").die("keydown").live("keydown",function(event){
			if(event.keyCode==13){
				$("#storage-lend-taxprice").blur();
				$("#storage-savegoon-lendDetailEditPage").focus();
			}
		});
		$("#storage-savegoon-lendDetailEditPage").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				editSaveDetail(true);
			}
		});
		$("#storage-savegoon-lendDetailEditPage").click(function(){
			editSaveDetail(true);
		});
	});
	//通过总数量 计算数量 金额换算
	function computNum(){
		var goodsid= $("#storage-lend-goodsid").val();
		var auxunitid = $("#storage-lend-auxunitid").val();
		var unitnum = $("#storage-lend-unitnum").val();
		var taxprice = $("#storage-lend-taxprice").val();
		var notaxprice = $("#storage-lend-notaxprice").numberbox("getValue");
		var taxtype = $("#storage-lend-taxtype").val();
		if(taxprice==null || taxprice==""){
			return false;
		}
		$("#storage-lend-unitnum").addClass("inputload");
		$.ajax({
			url :'basefiles/computeGoodsByUnitnum.do',
			type:'post',
			data:{unitnum:unitnum,goodsid:goodsid,auxunitid:auxunitid,taxprice:taxprice,taxtype:taxtype,notaxprice:notaxprice},
			dataType:'json',
			async:false,
			success:function(json){
				$("#storage-lend-taxamount").numberbox("setValue",json.taxamount);
				$("#storage-lend-notaxamount").numberbox("setValue",json.notaxamount);
				$("#storage-lend-tax").numberbox("setValue",json.tax);
				$("#storage-lend-taxtypename").val(json.taxtypename);
				$("#storage-lend-auxunitnumdetail").val(json.auxnumdetail);
				$("#storage-lend-auxunitnum").val(json.auxnum);
				$("#storage-lend-auxunitname").val(json.auxunitname);
				$("#storage-lend-auxunitname1").html(json.auxunitname);
				$("#storage-lend-goodsunitname").val(json.unitname);
				$("#storage-lend-goodsunitname1").html(json.unitname);

				$("#storage-lend-taxprice").val(json.taxprice);
				$("#storage-lend-notaxprice").numberbox("setValue",json.notaxprice);
				$("#storage-lend-notaxamount").numberbox("setValue",json.notaxamount);

				$("#storage-lend-unitnum-aux").val(json.auxInteger);
				$("#storage-lend-unitnum-unit").val(json.auxremainder);
				if(json.auxrate!=null){
					$("#storage-lend-unitnum-unit").attr("max",json.auxrate-1);
				}
				$("#storage-lend-unitnum").removeClass("inputload");
			}
		});
	}
	//通过辅单位数量
	function computNumByAux(){
		var goodsid= $("#storage-lend-goodsid").val();
		var auxunitid = $("#storage-lend-auxunitid").val();
		var taxprice = $("#storage-lend-taxprice").val();
		var notaxprice = $("#storage-lend-notaxprice").numberbox("getValue");
		var taxtype = $("#storage-lend-taxtype").val();
		var auxInterger = $("#storage-lend-unitnum-aux").val();
		var auxremainder = $("#storage-lend-unitnum-unit").val();
//			var auxmax = $("#storage-lend-unitnum-unit").attr("max");
//			if(Number(auxremainder)>Number(auxmax)){
//				auxremainder = auxmax;
//				$("#storage-lend-unitnum-unit").val(auxremainder);
//			}
		if(taxprice==null || taxprice=="" || notaxprice==null || notaxprice=="" || taxtype==null || taxtype==""){
			return false;
		}
		$("#storage-lend-unitnum").addClass("inputload");
		$.ajax({
			url :'basefiles/computeGoodsByAuxnum.do',
			type:'post',
			data:{auxInterger:auxInterger,auxremainder:auxremainder,goodsid:goodsid,auxunitid:auxunitid,taxprice:taxprice,taxtype:taxtype,notaxprice:notaxprice},
			dataType:'json',
			async:false,
			success:function(json){
				$("#storage-lend-taxamount").numberbox("setValue",json.taxamount);
				$("#storage-lend-notaxamount").numberbox("setValue",json.notaxamount);
				$("#storage-lend-tax").numberbox("setValue",json.tax);
				$("#storage-lend-taxtypename").val(json.taxtypename);

				$("#storage-lend-taxprice").val(json.taxprice);
				$("#storage-lend-notaxprice").numberbox("setValue",json.notaxprice);
				$("#storage-lend-notaxamount").numberbox("setValue",json.notaxamount);

				$("#storage-lend-unitnum").val(json.mainnum);
				$("#storage-lend-unitnum-aux").val(json.auxInterger);
				$("#storage-lend-unitnum-unit").val(json.auxremainder);
				$("#storage-lend-auxunitnumdetail").val(json.auxnumdetail);

				$("#storage-lend-unitnum").removeClass("inputload");
			}
		});

	}
	//页面重置
	function otherEnterformReset(){
		$("#storage-form-lendDetailAddPage").form("clear");
		$("#storage-lend-auxunitname1").html("");
		$("#storage-lend-goodsunitname1").html("");
		$("#storage-lend-goodsid").focus();
	}
	function goodsNumControl(){
		var selectObject = $("#storage-datagrid-lendAddPage").datagrid("getSelected");
		var storageInfo = $("#storage-lend-storageid").widget('getObject');
		var isbatch = $("#storage-lend-isbatch").val();
		if(isbatch=="1" && selectObject.summarybatchid!=null && selectObject.summarybatchid!=""){
			//控件借货的最大数量
			$.ajax({
				url :'storage/getStorageSummaryBatchInfo.do',
				type:'post',
				data:{summarybatchid:selectObject.summarybatchid},
				dataType:'json',
				success:function(json){
					if(json.storageSummaryBatch!=null){
						var unitname = $("#storage-lend-goodsunitname").val();
						var status = $("#storage-lend-status").val();
						var usablenum = json.storageSummaryBatch.usablenum;
						if(selectObject.id!=null && status=='2'){
							$.ajax({
								url :'storage/getStorageOtherOutDetailInfo.do',
								type:'post',
								data:{id:selectObject.id},
								dataType:'json',
								success:function(data){
									maxnum = Number(data.detail.unitnum)+Number(usablenum);
									$("#storage-lend-loadInfo").html("现存量：<font color='green'>"+json.storageSummaryBatch.existingnum+unitname+"</font>&nbsp;可用量：<font color='green'>"+ maxnum +unitname+"</font>");
								}
							});
						}else{
							maxnum = Number(object.unitnum)+Number(json.storageSummaryBatch.usablenum);
							$("#storage-lend-loadInfo").html("现存量：<font color='green'>"+json.storageSummaryBatch.existingnum+unitname+"</font>&nbsp;可用量：<font color='green'>"+ json.storageSummaryBatch.usablenum +unitname+"</font>");
						}
					}
				}
			});
		}else{
			var storageid = $("#storage-lend-storageid").widget("getValue");
			//控件借货的最大数量
			$.ajax({
				url :'storage/getStorageSummarySumByGoodsid.do',
				type:'post',
				data:{goodsid:selectObject.goodsid,storageid:storageid},
				dataType:'json',
				success:function(json){
					if(json.storageSummary!=null){
						var unitname = $("#storage-lend-goodsunitname").val();
						var status = $("#storage-lend-status").val();
						var usablenum = json.storageSummary.usablenum;
						if(selectObject.id!=null && status=='2'){
							$.ajax({
								url :'storage/getStorageOtherOutDetailInfo.do',
								type:'post',
								data:{id:selectObject.id},
								dataType:'json',
								success:function(data){
									maxnum = Number(data.detail.unitnum)+Number(usablenum);
									$("#storage-lend-loadInfo").html("现存量：<font color='green'>"+json.storageSummary.existingnum+unitname+"</font>&nbsp;可用量：<font color='green'>"+ maxnum +unitname+"</font>");
								}
							});
						}else{
							maxnum = Number(object.unitnum)+Number(json.storageSummary.usablenum);
							$("#storage-lend-loadInfo").html("现存量：<font color='green'>"+json.storageSummary.existingnum+unitname+"</font>&nbsp;可用量：<font color='green'>"+ json.storageSummary.usablenum +unitname+"</font>");
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
