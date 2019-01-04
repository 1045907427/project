<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>销售退货入库单明细添加</title>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border:false">
		<form action="" method="post" id="storage-form-saleRejectEnterDetailAddPage">
			<table  border="0" class="box_table">
				<tr>
					<td width="120">选择商品:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-saleRejectEnter-goodsid" name="goodsid" width="180"/>
					</td>
					<td>条形码:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-saleRejectEnter-goodsbarcode" class="no_input" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>数量:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-saleRejectEnter-unitnum" name="unitnum" class="easyui-validatebox" data-options="required:true,validType:'intOrFloatNum[${decimallen}]'"/>
					</td>
					<td>辅数量:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-saleRejectEnter-unitnum-aux" name="auxnum" style="width:60px;" class="easyui-validatebox" data-options="required:true,validType:'integer'"/>
						<span id="storage-saleRejectEnter-auxunitname1" style="float: left;"></span>
						<input type="text" id="storage-saleRejectEnter-unitnum-unit" name="auxremainder" style="width:60px;" class="easyui-validatebox" data-options="required:true,validType:'intOrFloatNum[${decimallen}]'"/>
						<span id="storage-saleRejectEnter-goodsunitname1" style="float: left;"></span>
						<input type="hidden" id="storage-saleRejectEnter-auxunitnumdetail" name="auxnumdetail" class="no_input" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td width="120">商品品牌:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-saleRejectEnter-goodsbrandName" class="no_input" readonly="readonly"/>
					</td>
					<td width="120">规格型号:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-saleRejectEnter-goodsmodel" class="no_input" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>主单位:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-saleRejectEnter-goodsunitname" name="unitname" class="no_input" readonly="readonly"/>
						<input type="hidden" id="storage-saleRejectEnter-goodsunitid" name="unitid"/>
					</td>
					<td>辅单位:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-saleRejectEnter-auxunitname" name="auxunitname" class="no_input" readonly="readonly"/>
						<input type="hidden" id="storage-saleRejectEnter-auxunitid" name="auxunitid"/>
					</td>
				</tr>
				<tr>
					<td>含税单价:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-saleRejectEnter-taxprice" name="taxprice" class="easyui-validatebox" data-options="required:true,validType:'intOrFloat'">
					</td>
					<td>含税金额:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-saleRejectEnter-taxamount" name="taxamount" class="no_input" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>未税单价:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-saleRejectEnter-notaxprice" name="notaxprice" class="no_input" readonly="readonly"/>
					</td>
					<td>未税金额:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-saleRejectEnter-notaxamount" name="notaxamount" class="no_input" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>税种:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-saleRejectEnter-taxtypename" name="taxtypename" class="no_input" readonly="readonly"/>
						<input type="hidden" id="storage-saleRejectEnter-taxtype" name="taxtype" class="no_input" readonly="readonly"/>
					</td>
					<td>税额:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-saleRejectEnter-tax" name="tax" class="no_input" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>所属库位:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-saleRejectEnter-storagelocationid" name="storagelocationid" class="no_input" readonly="readonly"/>
						<input type="hidden" id="storage-saleRejectEnter-storagelocationname"  name="storagelocationname"/>
					</td>
					<td>批次号:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-saleRejectEnter-batchno" name="batchno" class="no_input" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>生产日期:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-saleRejectEnter-produceddate" style="height: 20px;" name="produceddate" class="no_input Wdate" readonly="readonly"/>
					</td>
					<td>有效截止日期:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-saleRejectEnter-deadline" style="height: 20px;" name="deadline" class="no_input Wdate" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>退货属性:</td>
					<td>
						<select id="storage-saleRejectEnter-rejectcategory" name="rejectcategory" style="width: 165px;">
							<c:forEach items="${rejectCategory }" var="category" varStatus="status">
								<option value="<c:out value="${category.code }"/>"><c:out value="${category.codename }"/></option>
							</c:forEach>
						</select>
					</td>
					<td>备注:</td>
					<td style="text-align: left;">
						<input id="storage-saleRejectEnter-remark" type="text" name="remark" style="width: 165px;" maxlength="200"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="buttonDetailBG" style="text-align: right;">
			快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
			<input type="button" value="继续添加" name="savegoon" id="storage-savegoon-saleRejectEnterDetailAddPage" />
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			$("#storage-saleRejectEnter-goodsid").goodsWidget({
				required:true,
				onSelect:function(data){
					$("#storage-saleRejectEnter-goodsbrandName").val(data.brandName);
					$("#storage-saleRejectEnter-goodsmodel").val(data.model);
					$("#storage-saleRejectEnter-goodsunitid").val(data.mainunit);
					$("#storage-saleRejectEnter-auxunitid").val(data.meteringunitid);
					$("#storage-saleRejectEnter-taxtype").val(data.defaulttaxtype);
					$("#storage-saleRejectEnter-goodsbarcode").val(data.barcode);
					//商品进行批次管理时
					if(data.isbatch=="1"){
						$("#storage-saleRejectEnter-storagelocationid").removeClass("no_input");
						$("#storage-saleRejectEnter-storagelocationid").removeAttr("readonly");
						$("#storage-saleRejectEnter-storagelocationid").widget("enable");
						if(null!=data.storagelocation && ""!=data.storagelocation){
							$("#storage-saleRejectEnter-storagelocationid").widget("setValue",data.storagelocation);
						}else{
							$("#storage-saleRejectEnter-storagelocationid").widget("clear");
						}
						$('#storage-saleRejectEnter-batchno').validatebox({required:true});
						$("#storage-saleRejectEnter-batchno").removeClass("no_input");
						$("#storage-saleRejectEnter-batchno").removeAttr("readonly");

						$('#storage-saleRejectEnter-produceddate').validatebox({required:true});
						$("#storage-saleRejectEnter-produceddate").removeClass("no_input");
						$("#storage-saleRejectEnter-produceddate").removeAttr("readonly");

						$('#storage-saleRejectEnter-deadline').validatebox({required:true});
						$("#storage-saleRejectEnter-deadline").removeClass("no_input");
						$("#storage-saleRejectEnter-deadline").removeAttr("readonly");

					}else if(data.isstoragelocation=='1'){
						//商品进行库位管理时
						$("#storage-saleRejectEnter-storagelocationid").removeClass("no_input");
						$("#storage-saleRejectEnter-storagelocationid").removeAttr("readonly");

						$("#storage-saleRejectEnter-storagelocationid").widget("enable");
						if(null!=data.storagelocation && ""!=data.storagelocation){
							$("#storage-saleRejectEnter-storagelocationid").widget("setValue",data.storagelocation);
						}else{
							$("#storage-saleRejectEnter-storagelocationid").widget("clear");
						}
						$('#storage-saleRejectEnter-storagelocationid').validatebox({required:true});

						$('#storage-saleRejectEnter-batchno').validatebox({required:false});
						$("#storage-saleRejectEnter-batchno").addClass("no_input");
						$("#storage-saleRejectEnter-batchno").attr("readonly","readonly");

						$('#storage-saleRejectEnter-produceddate').validatebox({required:false});
						$("#storage-saleRejectEnter-produceddate").addClass("no_input");
						$("#storage-saleRejectEnter-produceddate").attr("readonly","readonly");

						$('#storage-saleRejectEnter-deadline').validatebox({required:false});
						$("#storage-saleRejectEnter-deadline").addClass("no_input");
						$("#storage-saleRejectEnter-deadline").attr("readonly","readonly");
					}else{

						$("#storage-saleRejectEnter-storagelocationid").widget("clear");
						$("#storage-saleRejectEnter-storagelocationid").widget("disable");
						$("#storage-saleRejectEnter-storagelocationid").addClass("no_input");
						$("#storage-saleRejectEnter-storagelocationid").attr("readonly","readonly");

						$('#storage-saleRejectEnter-batchno').validatebox({required:false});
						$("#storage-saleRejectEnter-batchno").addClass("no_input");
						$("#storage-saleRejectEnter-batchno").attr("readonly","readonly");

						$('#storage-saleRejectEnter-produceddate').validatebox({required:false});
						$("#storage-saleRejectEnter-produceddate").addClass("no_input");
						$("#storage-saleRejectEnter-produceddate").attr("readonly","readonly");

						$('#storage-saleRejectEnter-deadline').validatebox({required:false});
						$("#storage-saleRejectEnter-deadline").addClass("no_input");
						$("#storage-saleRejectEnter-deadline").attr("readonly","readonly");
					}
					$("#storage-saleRejectEnter-unitnum").focus();
					$("#storage-saleRejectEnter-unitnum").select();
				}
			});
			$("#storage-saleRejectEnter-storagelocationid").widget({
				name:'t_storage_salereject_enter_detail',
				width:165,
				col:'storagelocationid',
				singleSelect:true,
				onSelect:function(data){
					$("#storage-saleRejectEnter-storagelocationname").val(data.name);
				},
				onClear:function(){
					$("#storage-saleRejectEnter-storagelocationname").val("");
				}
			});
			$("#storage-saleRejectEnter-taxprice").change(function(){
				computNum();
			});
			$("#storage-saleRejectEnter-taxamount").numberbox({
				precision:6,
				groupSeparator:','
			});
			$("#storage-saleRejectEnter-notaxprice").numberbox({
				precision:6,
				groupSeparator:','
			});
			$("#storage-saleRejectEnter-notaxamount").numberbox({
				precision:6,
				groupSeparator:','
			});
			$("#storage-saleRejectEnter-tax").numberbox({
				precision:6,
				groupSeparator:','
			});
			$("#storage-saleRejectEnter-deadline").click(function(){
				if(!$("#storage-saleRejectEnter-batchno").hasClass("no_input")){
					WdatePicker({dateFmt:'yyyy-MM-dd'});
				}
			});
			$("#storage-saleRejectEnter-produceddate").click(function(){
				if(!$("#storage-saleRejectEnter-batchno").hasClass("no_input")){
					WdatePicker({dateFmt:'yyyy-MM-dd',
						onpicked:function(dp){
							if(dp.el.id=="storage-saleRejectEnter-produceddate"){
								var produceddate = dp.cal.getDateStr();
								var goodsid = $("#storage-saleRejectEnter-goodsid").val();
								$.ajax({
									url :'storage/getBatchno.do',
									type:'post',
									data:{produceddate:produceddate,goodsid:goodsid},
									dataType:'json',
									async:false,
									success:function(json){
										$('#storage-saleRejectEnter-batchno').val(json.batchno);
										$('#storage-saleRejectEnter-deadline').val(json.deadline);
									}
								});
							}
						}
					});
				}
			});
			$("#storage-saleRejectEnter-unitnum").change(function(){
				computNum();
			});
			$("#storage-saleRejectEnter-unitnum-aux").change(function(){
				computNumByAux();
			});
			$("#storage-saleRejectEnter-unitnum-unit").change(function(){
				computNumByAux();
			});
			$("#storage-saleRejectEnter-unitnum").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					$("#storage-saleRejectEnter-unitnum").blur();
					$("#storage-saleRejectEnter-unitnum-aux").focus();
					$("#storage-saleRejectEnter-unitnum-aux").select();
				}
			});
			$("#storage-saleRejectEnter-unitnum-aux").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
					$("#storage-saleRejectEnter-unitnum-auxm").blur();
					$("#storage-saleRejectEnter-unitnum-unit").focus();
					$("#storage-saleRejectEnter-unitnum-unit").select();
				}
			});
			$("#storage-saleRejectEnter-unitnum-unit").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
					$("#storage-saleRejectEnter-unitnum-auxm").blur();
					$("#storage-saleRejectEnter-taxprice").focus();
					$("#storage-saleRejectEnter-taxprice").select();
				}
			});
			$("#storage-saleRejectEnter-taxprice").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
					$("#storage-saleRejectEnter-taxprice").blur();
					$("#storage-saleRejectEnter-remark").focus();
					$("#storage-saleRejectEnter-remark").select();
				}
			});
			$("#storage-saleRejectEnter-remark").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
					$("#storage-saleRejectEnter-taxprice").blur();
					$("#storage-savegoon-saleRejectEnterDetailAddPage").focus();
				}
			});
			$("#storage-savegoon-saleRejectEnterDetailAddPage").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					addSaveDetail(true);
				}
			});
			$("#storage-savegoon-saleRejectEnterDetailAddPage").click(function(){
				addSaveDetail(true);
			});
		});
		//通过总数量 计算数量 金额换算
		function computNum(){
			var goodsInfo= $("#storage-saleRejectEnter-goodsid").goodsWidget("getObject");
			if(null==goodsInfo){
				return false;
			}
			var auxunitid = $("#storage-saleRejectEnter-auxunitid").val();
			var unitnum = $("#storage-saleRejectEnter-unitnum").val();
			var taxprice = $("#storage-saleRejectEnter-taxprice").val();
			var notaxprice = $("#storage-saleRejectEnter-notaxprice").val();
			var taxtype = $("#storage-saleRejectEnter-taxtype").val();
			$("#storage-saleRejectEnter-unitnum").addClass("inputload");
			$.ajax({
				url :'basefiles/computeGoodsByUnitnum.do',
				type:'post',
				data:{unitnum:unitnum,goodsid:goodsInfo.id,auxunitid:auxunitid,taxprice:taxprice,taxtype:taxtype,notaxprice:notaxprice},
				dataType:'json',
				async:false,
				success:function(json){
					$("#storage-saleRejectEnter-taxamount").numberbox("setValue",json.taxamount);
					$("#storage-saleRejectEnter-notaxamount").numberbox("setValue",json.notaxamount);
					$("#storage-saleRejectEnter-tax").numberbox("setValue",json.tax);
					$("#storage-saleRejectEnter-taxtypename").val(json.taxtypename);
					$("#storage-saleRejectEnter-auxunitnumdetail").val(json.auxnumdetail);
					$("#storage-saleRejectEnter-auxunitnum").val(json.auxnum);
					$("#storage-saleRejectEnter-auxunitname").val(json.auxunitname);
					$("#storage-saleRejectEnter-auxunitname1").html(json.auxunitname);
					$("#storage-saleRejectEnter-goodsunitname").val(json.unitname);
					$("#storage-saleRejectEnter-goodsunitname1").html(json.unitname);

					$("#storage-saleRejectEnter-taxprice").val(json.taxprice);
					$("#storage-saleRejectEnter-notaxprice").numberbox("setValue",json.notaxprice);
					$("#storage-saleRejectEnter-notaxamount").numberbox("setValue",json.notaxamount);

					$("#storage-saleRejectEnter-unitnum-aux").val(json.auxInteger);
					$("#storage-saleRejectEnter-unitnum-unit").val(json.auxremainder);
					$("#storage-saleRejectEnter-unitnum").removeClass("inputload");
				}
			});
		}
		//通过辅单位数量
		function computNumByAux(){
			var goodsInfo= $("#storage-saleRejectEnter-goodsid").goodsWidget("getObject");
			if(null==goodsInfo){
				return false;
			}
			var auxunitid = $("#storage-saleRejectEnter-auxunitid").val();
			var taxprice = $("#storage-saleRejectEnter-taxprice").val();
			var notaxprice = $("#storage-saleRejectEnter-notaxprice").numberbox("getValue");
			var taxtype = $("#storage-saleRejectEnter-taxtype").val();
			var auxInterger = $("#storage-saleRejectEnter-unitnum-aux").val();
			var auxremainder = $("#storage-saleRejectEnter-unitnum-unit").val();
			var auxmax = $("#storage-saleRejectEnter-unitnum-unit").attr("max");
			$("#storage-saleRejectEnter-unitnum").addClass("inputload");
			$.ajax({
				url :'basefiles/computeGoodsByAuxnum.do',
				type:'post',
				data:{auxInterger:auxInterger,auxremainder:auxremainder,goodsid:goodsInfo.id,auxunitid:auxunitid,taxprice:taxprice,taxtype:taxtype,notaxprice:notaxprice},
				dataType:'json',
				async:false,
				success:function(json){
					$("#storage-saleRejectEnter-taxamount").numberbox("setValue",json.taxamount);
					$("#storage-saleRejectEnter-notaxamount").numberbox("setValue",json.notaxamount);
					$("#storage-saleRejectEnter-tax").numberbox("setValue",json.tax);
					$("#storage-saleRejectEnter-taxtypename").val(json.taxtypename);

					$("#storage-saleRejectEnter-taxprice").val(json.taxprice);
					$("#storage-saleRejectEnter-notaxprice").numberbox("setValue",json.notaxprice);
					$("#storage-saleRejectEnter-notaxamount").numberbox("setValue",json.notaxamount);

					$("#storage-saleRejectEnter-unitnum").val(json.mainnum);
					$("#storage-saleRejectEnter-unitnum-aux").val(json.auxInterger);
					$("#storage-saleRejectEnter-unitnum-unit").val(json.auxremainder);
					$("#storage-saleRejectEnter-auxunitnumdetail").val(json.auxnumdetail);

					$("#storage-saleRejectEnter-unitnum").removeClass("inputload");
				}
			});
		}
		//页面重置
		function otherEnterformReset(){
			$("#storage-form-saleRejectEnterDetailAddPage").form("clear");
			$("#storage-saleRejectEnter-auxunitname1").html("");
			$("#storage-saleRejectEnter-goodsunitname1").html("");
			$("#storage-saleRejectEnter-goodsid").focus();
		}
		//默认禁用所属库位
		$("#storage-saleRejectEnter-storagelocationid").widget("disable");
	</script>
</body>
</html>
