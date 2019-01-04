<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>采购退货出库单明细修改</title>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border:false">
		<form action="" method="post" id="storage-form-purchaseRejectOutDetailAddPage">
			<table  border="0" class="box_table">
				<tr>
					<td width="120">选择商品:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-purchaseRejectOut-goodsname" width="180" class="no_input" readonly="readonly"/>
						<input type="hidden" id="storage-purchaseRejectOut-goodsid" name="goodsid"/>
						<input type="hidden" id="storage-purchaseRejectOut-summarybatchid" name="summarybatchid">
					</td>
					<td>条形码:</td>
					<td>
						<input type="text" id="storage-purchaseRejectOut-goodsbarcode" class="no_input" readonly="readonly"/>
					</td>
					<!--	   				<td id="storage-purchaseRejectOut-loadInfo" colspan="2" style="text-align: left;"></td>-->
				</tr>
				<tr>
					<td>数量:</td>
					<td>
						<input type="text" id="storage-purchaseRejectOut-unitnum" name="unitnum" class="formaterNum easyui-validatebox" data-options="required:true,validType:['intOrFloatNum[${decimallen}]']"/>
					</td>
					<td>辅数量:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-purchaseRejectOut-unitnum-aux" name="auxnum" style="width:60px;" class="formaterNum easyui-validatebox" data-options="required:true,validType:'integer'"/>
						<span id="storage-purchaseRejectOut-auxunitname1" style="float: left;"></span>
						<input type="text" id="storage-purchaseRejectOut-unitnum-unit" name="auxremainder" style="width:60px;" class="formaterNum easyui-validatebox" data-options="required:true,validType:'intOrFloatNum[${decimallen}]'"/>
						<span id="storage-purchaseRejectOut-goodsunitname1" style="float: left;"></span>
						<input type="hidden" id="storage-purchaseRejectOut-auxunitnumdetail" name="auxnumdetail" class="no_input" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td width="120">商品品牌:</td>
					<td>
						<input type="text" id="storage-purchaseRejectOut-goodsbrandName" class="no_input" readonly="readonly"/>
					</td>
					<td width="120">规格型号:</td>
					<td>
						<input type="text" id="storage-purchaseRejectOut-goodsmodel" class="no_input" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>单位：</td>
					<td style="float: left;">
						<span style="float: left;">主：</span><input id="storage-purchaseRejectOut-goodsunitname" name="unitname" type="text" class="readonly2" style="width: 58px;" readonly="readonly" /><input id="storage-purchaseRejectOut-goodsunitid" type="hidden" name="unitid" />
						<span style="float: left;">辅：</span><input id="storage-purchaseRejectOut-auxunitname" name="auxunitname" type="text" class="readonly2" style="width: 58px;" readonly="readonly" /><input id="storage-purchaseRejectOut-auxunitid" type="hidden" name="auxunitid" />
					</td>
					<td>箱装量：</td>
					<td>
						<input name="boxnum" id="storage-purchaseRejectOut-boxnum" type="text" class="readonly" readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td <c:if test="${map.taxprice != 'taxprice'}"> style="display: none"</c:if>>含税单价:</td>
					<td style="float: left;<c:if test="${map.taxprice != 'taxprice'}"> display: none;</c:if>">
						<input type="text" id="storage-purchaseRejectOut-taxprice" name="taxprice" class="no_input" readonly="readonly">
					</td>
					<td <c:if test="${map.taxamount != 'taxamount'}"> style="display: none"</c:if>>含税金额:</td>
					<td  style="float: left;<c:if test="${map.taxamount != 'taxamount'}"> display: none;</c:if>">
						<input type="text" id="storage-purchaseRejectOut-taxamount" name="taxamount" class="no_input" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td <c:if test="${map.notaxprice != 'notaxprice'}"> style="display: none"</c:if>>未税单价:</td>
					<td style="float: left;<c:if test="${map.notaxprice != 'notaxprice'}"> display: none;</c:if>">
						<input type="text" id="storage-purchaseRejectOut-notaxprice" name="notaxprice" class="no_input" readonly="readonly"/>
					</td>
					<td <c:if test="${map.notaxamount != 'notaxamount'}"> style="display: none"</c:if>>未税金额:</td>
					<td  style="float: left;<c:if test="${map.notaxamount != 'notaxamount'}"> display: none;</c:if>">
						<input type="text" id="storage-purchaseRejectOut-notaxamount" name="notaxamount" class="no_input" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td <c:if test="${map.taxprice != 'taxprice'}"> style="display: none"</c:if>>含税箱价:</td>
					<td style="float: left;<c:if test="${map.taxprice != 'taxprice'}"> display: none;</c:if>">
						<input type="text" id="storage-purchaseRejectOut-boxprice" name="boxprice" class="no_input" readonly="readonly">
					</td>
					<td <c:if test="${map.notaxprice != 'notaxprice'}"> style="display: none"</c:if>>未税箱价:</td>
					<td style="float: left;<c:if test="${map.notaxprice != 'notaxprice'}"> display: none;</c:if>">
						<input type="text" id="storage-purchaseRejectOut-noboxprice" name="noboxprice" class="no_input" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>税种:</td>
					<td>
						<input type="text" id="storage-purchaseRejectOut-taxtypename" name="taxtypename" class="no_input" readonly="readonly"/>
						<input type="hidden" id="storage-purchaseRejectOut-taxtype" name="taxtype" class="no_input" readonly="readonly"/>
					</td>
					<td <c:if test="${map.tax != 'tax'}"> style="display: none"</c:if>>税额:</td>
					<td style="float: left;<c:if test="${map.tax != 'tax'}"> display: none;</c:if>">
						<input type="text" id="storage-purchaseRejectOut-tax" name="tax" class="no_input" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>批次号:</td>
					<td>
						<input type="text" id="storage-purchaseRejectOut-batchno" name="batchno" class="no_input" readonly="readonly"/>
					</td>
					<td>所属库位:</td>
					<td style="text-align: left;">
						<input type="hidden" id="storage-purchaseRejectOut-storagelocationid" name="storagelocationid" />
						<input type="text" id="storage-purchaseRejectOut-storagelocationname"  name="storagelocationname" class="no_input" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>生产日期:</td>
					<td>
						<input type="text" id="storage-purchaseRejectOut-produceddate" style="height: 20px;" name="produceddate" class="WdateRead" readonly="readonly"/>
					</td>
					<td>有效截止日期:</td>
					<td>
						<input type="text" id="storage-purchaseRejectOut-deadline" style="height: 20px;" name="deadline" class="WdateRead" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>备注:</td>
					<td colspan="3" style="text-align: left;">
						<input id="storage-purchaseRejectOut-remark" type="text" name="remark" style="width: 490px;" maxlength="200"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="buttonDetailBG" style="height:30px;text-align: right;">
			快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
			<input type="button" value="继续修改" name="savegoon" id="storage-savegoon-purchaseRejectOutDetailEditPage" />
		</div>
	</div>
</div>
<script type="text/javascript">
	//加载数据
	var object = $("#storage-datagrid-purchaseRejectOutAddPage").datagrid("getSelected");
	$("#storage-form-purchaseRejectOutDetailAddPage").form("load",object);
	$("#storage-purchaseRejectOut-goodsname").val(object.goodsInfo.name);
	$("#storage-purchaseRejectOut-goodsbrandName").val(object.goodsInfo.brandName);
	$("#storage-purchaseRejectOut-goodsmodel").val(object.goodsInfo.model);
	$("#storage-purchaseRejectOut-goodsbarcode").val(object.goodsInfo.barcode);
	$("#storage-purchaseRejectOut-goodsunitname1").html(object.unitname);
	$("#storage-purchaseRejectOut-auxunitname1").html(object.auxunitname);
	$("#storage-purchaseRejectOut-boxnum").val(formatterBigNumNoLen(object.goodsInfo.boxnum));
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
		$("#storage-purchaseRejectOut-taxamount").numberbox({
			precision:6,
			groupSeparator:','
		});
		$("#storage-purchaseRejectOut-notaxprice").numberbox({
			precision:6,
			groupSeparator:','
		});
		$("#storage-purchaseRejectOut-notaxamount").numberbox({
			precision:6,
			groupSeparator:','
		});
		$("#storage-purchaseRejectOut-tax").numberbox({
			precision:6,
			groupSeparator:','
		});
		$("#storage-purchaseRejectOut-deadline").click(function(){
			if(!$("#storage-purchaseRejectOut-batchno").hasClass("no_input")){
				WdatePicker({dateFmt:'yyyy-MM-dd'});
			}
		});
		$("#storage-purchaseRejectOut-produceddate").click(function(){
			if(!$("#storage-purchaseRejectOut-batchno").hasClass("no_input")){
				WdatePicker({dateFmt:'yyyy-MM-dd'});
			}
		});
		$("#storage-purchaseRejectOut-unitnum").change(function(){
			computNum();
		});
		$("#storage-purchaseRejectOut-unitnum-aux").change(function(){
			computNumByAux();
		});
		$("#storage-purchaseRejectOut-unitnum-unit").change(function(){
			computNumByAux();
		});
		$("#storage-purchaseRejectOut-unitnum").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#storage-purchaseRejectOut-unitnum").blur();
				$("#storage-purchaseRejectOut-unitnum-aux").focus();
				$("#storage-purchaseRejectOut-unitnum-aux").select();
			}
		});
		$("#storage-purchaseRejectOut-unitnum-aux").die("keydown").live("keydown",function(event){
			if(event.keyCode==13){
				$("#storage-purchaseRejectOut-unitnum-aux").blur();
				$("#storage-purchaseRejectOut-unitnum-unit").focus();
				$("#storage-purchaseRejectOut-unitnum-unit").select();
			}
		});
		$("#storage-purchaseRejectOut-unitnum-unit").die("keydown").live("keydown",function(event){
			if(event.keyCode==13){
				$("#storage-purchaseRejectOut-unitnum-unit").blur();
				//$("#storage-purchaseRejectOut-taxprice").focus();
				//$("#storage-purchaseRejectOut-taxprice").select();
				$("#storage-purchaseRejectOut-remark").focus();
				$("#storage-purchaseRejectOut-remark").select();
			}
		});
		$("#storage-purchaseRejectOut-taxprice").die("keydown").live("keydown",function(event){
			if(event.keyCode==13){
				$("#storage-purchaseRejectOut-taxprice").blur();
				$("#storage-purchaseRejectOut-remark").focus();
				$("#storage-purchaseRejectOut-remark").select();
			}
		});
		$("#storage-purchaseRejectOut-remark").die("keydown").live("keydown",function(event){
			if(event.keyCode==13){
				$("#storage-purchaseRejectOut-remark").blur();
				//$("#storage-purchaseRejectOut-taxprice").blur();
				$("#storage-savegoon-purchaseRejectOutDetailEditPage").focus();
			}
		});
		$("#storage-savegoon-purchaseRejectOutDetailEditPage").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				editSaveDetail(true);
			}
		});
		$("#storage-savegoon-purchaseRejectOutDetailEditPage").click(function(){
			editSaveDetail(true);
		});
	});
	//通过总数量 计算数量 金额换算
	function computNum(){
		var goodsid= $("#storage-purchaseRejectOut-goodsid").val();
		var auxunitid = $("#storage-purchaseRejectOut-auxunitid").val();
		var unitnum = $("#storage-purchaseRejectOut-unitnum").val();
		var taxprice = $("#storage-purchaseRejectOut-taxprice").val();
		var notaxprice = $("#storage-purchaseRejectOut-notaxprice").numberbox("getValue");
		var taxtype = $("#storage-purchaseRejectOut-taxtype").val();
		if(taxprice==null || taxprice=="" || notaxprice==null || notaxprice=="" || taxtype==null || taxtype==""){
			return false;
		}
		$("#storage-purchaseRejectOut-unitnum").addClass("inputload");
		$.ajax({
			url :'basefiles/computeGoodsByUnitnum.do',
			type:'post',
			data:{unitnum:unitnum,goodsid:goodsid,auxunitid:auxunitid,taxprice:taxprice,taxtype:taxtype,notaxprice:notaxprice},
			dataType:'json',
			async:false,
			success:function(json){
				$("#storage-purchaseRejectOut-taxamount").numberbox("setValue",json.taxamount);
				$("#storage-purchaseRejectOut-notaxamount").numberbox("setValue",json.notaxamount);
				$("#storage-purchaseRejectOut-tax").numberbox("setValue",json.tax);
				$("#storage-purchaseRejectOut-taxtypename").val(json.taxtypename);
				$("#storage-purchaseRejectOut-auxunitnumdetail").val(json.auxnumdetail);
				$("#storage-purchaseRejectOut-auxunitnum").val(json.auxnum);
				$("#storage-purchaseRejectOut-auxunitname").val(json.auxunitname);
				$("#storage-purchaseRejectOut-auxunitname1").html(json.auxunitname);
				$("#storage-purchaseRejectOut-goodsunitname").val(json.unitname);
				$("#storage-purchaseRejectOut-goodsunitname1").html(json.unitname);

				$("#storage-purchaseRejectOut-taxprice").val(json.taxprice);
				$("#storage-purchaseRejectOut-notaxprice").numberbox("setValue",json.notaxprice);
				$("#storage-purchaseRejectOut-notaxamount").numberbox("setValue",json.notaxamount);

				$("#storage-purchaseRejectOut-unitnum-aux").val(json.auxInteger);
				$("#storage-purchaseRejectOut-unitnum-unit").val(json.auxremainder);
				if(json.auxrate!=null){
					$("#storage-purchaseRejectOut-unitnum-unit").attr("max",json.auxrate-1);
				}
				$("#storage-purchaseRejectOut-unitnum").removeClass("inputload");
			}
		});
	}
	//通过辅单位数量
	function computNumByAux(){
		var goodsid= $("#storage-purchaseRejectOut-goodsid").val();
		var auxunitid = $("#storage-purchaseRejectOut-auxunitid").val();
		var taxprice = $("#storage-purchaseRejectOut-taxprice").val();
		var notaxprice = $("#storage-purchaseRejectOut-notaxprice").numberbox("getValue");
		var taxtype = $("#storage-purchaseRejectOut-taxtype").val();
		var auxInterger = $("#storage-purchaseRejectOut-unitnum-aux").val();
		var auxremainder = $("#storage-purchaseRejectOut-unitnum-unit").val();
		var auxmax = $("#storage-purchaseRejectOut-unitnum-unit").attr("max");
		if(Number(auxremainder)>Number(auxmax)){
			auxremainder = auxmax;
			$("#storage-purchaseRejectOut-unitnum-unit").val(auxremainder);
		}
		if(taxprice==null || taxprice=="" || notaxprice==null || notaxprice=="" || taxtype==null || taxtype==""){
			return false;
		}
		$("#storage-purchaseRejectOut-unitnum").addClass("inputload");
		$.ajax({
			url :'basefiles/computeGoodsByAuxnum.do',
			type:'post',
			data:{auxInterger:auxInterger,auxremainder:auxremainder,goodsid:goodsid,auxunitid:auxunitid,taxprice:taxprice,taxtype:taxtype,notaxprice:notaxprice},
			dataType:'json',
			async:false,
			success:function(json){
				$("#storage-purchaseRejectOut-taxamount").numberbox("setValue",json.taxamount);
				$("#storage-purchaseRejectOut-notaxamount").numberbox("setValue",json.notaxamount);
				$("#storage-purchaseRejectOut-tax").numberbox("setValue",json.tax);
				$("#storage-purchaseRejectOut-taxtypename").val(json.taxtypename);

				$("#storage-purchaseRejectOut-taxprice").val(json.taxprice);
				$("#storage-purchaseRejectOut-notaxprice").numberbox("setValue",json.notaxprice);
				$("#storage-purchaseRejectOut-notaxamount").numberbox("setValue",json.notaxamount);

				$("#storage-purchaseRejectOut-unitnum").val(json.mainnum);
				$("#storage-purchaseRejectOut-unitnum-aux").val(json.auxInterger);
				$("#storage-purchaseRejectOut-unitnum-unit").val(json.auxremainder);
				$("#storage-purchaseRejectOut-auxunitnumdetail").val(json.auxnumdetail);

				$("#storage-purchaseRejectOut-unitnum").removeClass("inputload");
			}
		});

	}
	//页面重置
	function otherEnterformReset(){
		$("#storage-form-purchaseRejectOutDetailAddPage").form("clear");
		$("#storage-purchaseRejectOut-auxunitname1").html("");
		$("#storage-purchaseRejectOut-goodsunitname1").html("");
		$("#storage-purchaseRejectOut-goodsid").focus();
	}
	<!--		function goodsNumControl(){-->
	<!--			var selectObject = $("#storage-datagrid-purchaseRejectOutAddPage").datagrid("getSelected");-->
	<!--			//控件采购退货的最大数量-->
		<!--			$.ajax({   -->
		<!--	            url :'storage/getStorageSummaryBatchInfo.do',-->
		<!--	            type:'post',-->
		<!--	            data:{summarybatchid:selectObject.summarybatchid},-->
		<!--	            dataType:'json',-->
		<!--	            success:function(json){-->
		<!--	            	if(json.storageSummaryBatch!=null){-->
		<!--	            		var unitname = $("#storage-purchaseRejectOut-goodsunitname").val();-->
		<!--	            		var status = $("#storage-purchaseRejectOut-status").val();-->
		<!--	            		var usablenum = json.storageSummaryBatch.usablenum;-->
	<!--	            		//当采购退货出库单处于保存状态 订单修改时-->
		<!--	            		if(selectObject.id!=null && status=='2'){-->
		<!--			            	$.ajax({   -->
		<!--					            url :'storage/getPurchaseRejectOutDetailInfo.do',-->
		<!--					            type:'post',-->
		<!--					            data:{id:selectObject.id},-->
		<!--					            dataType:'json',-->
		<!--					            success:function(data){-->
		<!--					            	maxnum = Number(data.detail.unitnum)+Number(usablenum);-->
	<!--					            	$("#storage-purchaseRejectOut-loadInfo").html("现存量：<font color='green'>"+json.storageSummaryBatch.existingnum+unitname+"</font>&nbsp;可用量：<font color='green'>"+ maxnum +unitname+"</font>");-->
		<!--					            }-->
		<!--					        });-->
		<!--		            	}else{-->
		<!--		            		maxnum = Number(object.unitnum)+Number(json.storageSummaryBatch.usablenum);-->
	<!--			            	$("#storage-purchaseRejectOut-loadInfo").html("现存量：<font color='green'>"+json.storageSummaryBatch.existingnum+unitname+"</font>&nbsp;可用量：<font color='green'>"+ json.storageSummaryBatch.usablenum +unitname+"</font>");-->
	<!--		            	}-->
	<!--	            	}-->
	<!--	            }-->
	<!--	        });-->
	<!--		}-->
	//goodsNumControl();
</script>
</body>
</html>
