<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%
	boolean isnumLimit = false;
%>
<security:authorize url="/storage/purchaseEnterGreaterBuyOrderNum.do">
	<%
		isnumLimit = true;
	%>
</security:authorize>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>销售出库单明细修改</title>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border:false">
		<form action="" method="post" id="storage-form-purchaseEnterDetailEditPage" autocomplete="off">
			<table  border="0" class="box_table">
				<tr>
					<td width="120">商品:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-purchaseEnter-goodsname" width="180" class="no_input" readonly="readonly"/>
						<input type="hidden" id="storage-purchaseEnter-goodsid" name="goodsid" width="170"/>
					</td>
					<td></td>
					<td id="storage-purchaseEnter-loading" style="text-align: left;"></td>
				</tr>
				<tr>
					<td>辅数量:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-purchaseEnter-unitnum-aux" name="auxnum" style="width:60px;" class="formaterNum easyui-validatebox" data-options="required:true,validType:'integer'"/>
						<span id="storage-purchaseEnter-auxunitname1" style="float: left;"></span>
						<input type="text" id="storage-purchaseEnter-unitnum-unit" name="auxremainder" style="width:60px;" class="formaterNum easyui-validatebox" data-options="required:true,validType:'intOrFloatNum[${decimallen}]'"/>
						<span id="storage-purchaseEnter-goodsunitname1" style="float: left;"></span>
						<input type="hidden" id="storage-purchaseEnter-auxunitnumdetail" name="auxnumdetail" class="no_input" readonly="readonly"/>
					</td>
					<td>数量:</td>
					<td>
						<input type="text" id="storage-purchaseEnter-unitnum" name="unitnum" class="formaterNum easyui-validatebox" data-options="required:true,validType:['intOrFloatNum[${decimallen}]'<%if(!isnumLimit){%>,'max[${initnum}]'<%}%>]"/>
					</td>
				</tr>
				<tr>
					<td width="120">商品品牌:</td>
					<td>
						<input type="text" id="storage-purchaseEnter-goodsbrandName" class="no_input" readonly="readonly"/>
					</td>
					<td>条形码:</td>
					<td>
						<input type="text" id="storage-purchaseEnter-goodsbarcode" name="barcode"/>
					</td>
				</tr>
				<tr>
					<td>单位：</td>
					<td style="float: left;">
						<span style="float: left;">主：</span><input id="storage-purchaseEnter-goodsunitname" name="unitname" type="text" class="readonly2" style="width: 58px;" readonly="readonly" /><input id="storage-purchaseEnter-goodsunitid" type="hidden" name="unitid" />
						<span style="float: left;">辅：</span><input id="storage-purchaseEnter-auxunitname" name="auxunitname" type="text" class="readonly2" style="width: 58px;" readonly="readonly" /><input id="storage-purchaseEnter-auxunitid" type="hidden" name="auxunitid" />
					</td>
					<td>箱装量：</td>
					<td>
						<input name="boxnum" id="storage-purchaseEnter-boxnum" type="text" class="len150 readonly" readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td <c:if test="${map.taxprice != 'taxprice'}"> style="display: none"</c:if>>含税单价:</td>
					<td style="float: left;<c:if test="${map.taxprice != 'taxprice'}"> display: none;</c:if>">
						<input type="text" id="storage-purchaseEnter-taxprice" name="taxprice" class="no_input" readonly="readonly"/>
					</td>

					<td <c:if test="${map.taxamount != 'taxamount'}"> style="display: none"</c:if>>含税金额:</td>
					<td style="float: left;<c:if test="${map.taxamount != 'taxamount'}">display: none</c:if>">
						<input type="text" id="storage-purchaseEnter-taxamount" name="taxamount" class="no_input" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td <c:if test="${map.notaxprice != 'notaxprice'}"> style="display: none"</c:if>>未税单价:</td>
					<td style="float: left;<c:if test="${map.notaxprice != 'notaxprice'}">display: none</c:if>">
						<input type="text" id="storage-purchaseEnter-notaxprice" name="notaxprice" class="no_input" readonly="readonly"/>
					</td>

					<td <c:if test="${map.notaxamount != 'notaxamount'}"> style="display: none"</c:if>> 未税金额:</td>
					<td style="float: left;<c:if test="${map.notaxamount != 'notaxamount'}">display: none</c:if>">
						<input type="text" id="storage-purchaseEnter-notaxamount" name="notaxamount" class="no_input" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td <c:if test="${map.taxprice != 'taxprice'}"> style="display: none"</c:if>>含税箱价:</td>
					<td style="float: left;<c:if test="${map.taxprice != 'taxprice'}">display: none</c:if>">
						<input type="text" id="storage-purchaseEnter-boxprice" name="boxprice" class="no_input" readonly="readonly"/>
					</td>
					<td <c:if test="${map.notaxprice != 'notaxprice'}"> style="display: none"</c:if>>未税箱价:</td>
					<td style="float: left;<c:if test="${map.notaxprice != 'notaxprice'}">display: none</c:if>">
						<input type="text" id="storage-purchaseEnter-noxboxprice" name="noboxprice" class="no_input" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>税种:</td>
					<td>
						<input type="text" id="storage-purchaseEnter-taxtypename" name="taxtypename" class="no_input" readonly="readonly"/>
						<input type="hidden" id="storage-purchaseEnter-taxtype" name="taxtype" class="no_input" readonly="readonly"/>
					</td>
					<td <c:if test="${map.tax != 'tax'}"> style="display: none"</c:if>>税额:</td>
					<td style="float: left;<c:if test="${map.tax != 'tax'}">display: none"</c:if>">
					<input type="text" id="storage-purchaseEnter-tax" name="tax" class="no_input" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>生产日期:</td>
					<td>
						<input type="text" id="storage-purchaseEnter-produceddate" style="height: 20px;" name="produceddate" class="WdateRead" readonly="readonly"/>
					</td>
					<td>截止日期:</td>
					<td>
						<input type="text" id="storage-purchaseEnter-deadline" style="height: 20px;" name="deadline" class="WdateRead" readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td>批次号:</td>
					<td>
						<input type="text" id="storage-purchaseEnter-batchno" name="batchno" class="no_input" readonly="readonly"/>
					</td>
					<td>所属库位:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-purchaseEnter-storagelocationid" name="storagelocationid" />
						<input type="hidden" id="storage-purchaseEnter-storagelocationname"  name="storagelocationname"/>
					</td>
				</tr>
				<tr>
					<td>入库仓库:</td>
					<td style="text-align: left;">
						<input type="text" id="storage-purchaseEnter-detail-storageid"/>
						<input type="hidden" id="storage-purchaseEnter-detail-hidden-storageid" name="storageid"/>
						<input type="hidden" id="storage-purchaseEnter-detail-storagename"  name="storagename"/>
						<input type="hidden" id="storage-purchaseEnter-detail-initnum"  name="initnum"/>
					</td>
					<td width="120">实际到货日期:</td>
					<td>
						<input type="text" id="storage-purchaseEnter-arrivedate" name="arrivedate" style="height: 20px;" class="Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})"/>
					</td>
				</tr>
				<tr>
					<td>商品类型：</td>
					<td>
						<select id="storage-purchaseEnter-goodstype" style="width: 165px;" disabled="disabled" >
							<option value="1" selected="selected">赠品</option>
						</select>
						<input id="storage-purchaseEnter-hidden-goodstype" type="hidden" name="goodstype" value="1"/>
					</td>
					<td>备注:</td>
					<td style="text-align: left;">
						<input id="storage-purchaseEnter-remark" type="text" name="remark" style="width: 165px;" maxlength="200"/>
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="buttonDetailBG" style="height:30px;text-align:right;">
			<c:if test="${isbatch=='1'}">
				<input type="button" value="多批次入库" id="storage-batchbutton-purchaseEnterDetailEditPage"/>
			</c:if>
			快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
			<input type="button" value="确 定" name="savegoon" id="storage-savegoon-purchaseEnterDetailEditPage" />
		</div>
	</div>
</div>
<script type="text/javascript">
	var object = $("#storage-datagrid-purchaseEnterAddPage").datagrid("getSelected");
	//获取单据中的入库仓库
	var billStorageid = $("#storage-purchaseEnter-storageid").val();
	var storageFlag = true ;
	if(billStorageid==null || billStorageid==""){
		storageFlag = false;
		billStorageid = object.storageid;
	}
	$(function(){
		$("#storage-purchaseEnter-detail-storageid").widget({
			name:'t_storage_purchase_enter_detail',
			width:165,
			col:'storageid',
			singleSelect:true,
			initValue:billStorageid,
			required:true,
			disabled:storageFlag,
			onSelect :function(data){
				$("#storage-purchaseEnter-detail-hidden-storageid").val(data.id);
				$("#storage-purchaseEnter-detail-storagename").val(data.name);
			}
		});
		$("#storage-purchaseEnter-storagelocationid").widget({
			name:'t_storage_purchase_enter_detail',
			width:165,
			col:'storagelocationid',
			disabled:true,
			singleSelect:true
		});
		$("#storage-purchaseEnter-taxprice").numberbox({
			precision:6,
			groupSeparator:',',
			required:true
		});
		$("#storage-purchaseEnter-taxamount").numberbox({
			precision:6,
			groupSeparator:',',
			required:true
		});
		$("#storage-purchaseEnter-notaxprice").numberbox({
			precision:6,
			groupSeparator:',',
			required:true
		});
		$("#storage-purchaseEnter-notaxamount").numberbox({
			precision:6,
			groupSeparator:',',
			required:true
		});
		$("#storage-purchaseEnter-tax").numberbox({
			precision:6,
			groupSeparator:',',
			required:true
		});
		$("#storage-purchaseEnter-produceddate").click(function(){
			if(!$("#storage-purchaseEnter-batchno").hasClass("no_input")){
				WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d',
					onpicked:function(dp){
						if(dp.el.id=="storage-purchaseEnter-produceddate"){
							var produceddate = dp.cal.getDateStr();
							var goodsid = $("#storage-purchaseEnter-goodsid").val();
							$.ajax({
								url :'storage/getBatchno.do',
								type:'post',
								data:{produceddate:produceddate,goodsid:goodsid},
								dataType:'json',
								async:false,
								success:function(json){
									$("#storage-purchaseEnter-batchno").val(json.batchno);
									$("#storage-purchaseEnter-deadline").val(json.deadline);
								}
							});
						}
					}
				});
			}
		});
		$("#storage-purchaseEnter-deadline").click(function(){
			if(!$("#storage-purchaseEnter-batchno").hasClass("no_input")){
				WdatePicker({dateFmt:'yyyy-MM-dd',
					onpicked:function(dp){
						if(dp.el.id=="storage-purchaseEnter-deadline"){
							var deadline = dp.cal.getDateStr();
							var goodsid = $("#storage-purchaseEnter-goodsid").val();
							$.ajax({
								url :'storage/getBatchnoByDeadline.do',
								type:'post',
								data:{deadline:deadline,goodsid:goodsid},
								dataType:'json',
								async:false,
								success:function(json){
									$("#storage-purchaseEnter-batchno").val(json.batchno);
									$("#storage-purchaseEnter-produceddate").val(json.produceddate);
								}
							});
						}
					}
				});
			}
		});
		$("#storage-batchbutton-purchaseEnterDetailEditPage").click(function(){
			var flag = $("#storage-purchaseEnter-unitnum").validatebox('isValid');
			if(flag==false){
				$.messager.alert("提醒", "入库数量超过订单数量。");
				return false;
			}
			var goodsid = $("#storage-purchaseEnter-goodsid").val();
			$('<div id="storage-dialog-batchno-purchaseEnterAddPage-content"></div>').appendTo('#storage-dialog-batchno-purchaseEnterAddPage');
			var url = 'storage/showPurchaseEnterDetailBatchEditPage.do?goodsid='+goodsid;
			$('#storage-dialog-batchno-purchaseEnterAddPage-content').dialog({
				title: '采购入库单明细多批次修改',
				width: 780,
				height: 450,
				collapsible:false,
				minimizable:false,
				maximizable:true,
				resizable:true,
				closed: true,
				cache: false,
				href: url,
				modal: true,
				onClose:function(){
					$('#storage-dialog-batchno-purchaseEnterAddPage-content').dialog("destroy");
				},
				onLoad:function(){
					$("#storage-purchaseEnter-unitnum-aux").focus();
					$("#storage-purchaseEnter-unitnum-aux").select();
				}
			});
			$('#storage-dialog-batchno-purchaseEnterAddPage-content').dialog("open");
		});
		$("#storage-purchaseEnter-unitnum").change(function(){
			computNum();
		});
		$("#storage-purchaseEnter-unitnum-aux").change(function(){
			computNumByAux();
		});
		$("#storage-purchaseEnter-unitnum-unit").change(function(){
			computNumByAux();
		});
		$("#storage-savegoon-purchaseEnterDetailEditPage").click(function(){
			editSaveDetail(false);
		});
		$("#storage-purchaseEnter-unitnum").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#storage-purchaseEnter-remark").focus();
				$("#storage-purchaseEnter-remark").select();
			}
		});
		$("#storage-purchaseEnter-unitnum-aux").die("keydown").live("keydown",function(event){
			if(event.keyCode==13){
				$("#storage-purchaseEnter-unitnum-unit").focus();
				$("#storage-purchaseEnter-unitnum-unit").select();
			}
		});
		$("#storage-purchaseEnter-unitnum-unit").die("keydown").live("keydown",function(event){
			if(event.keyCode==13){
				$("#storage-purchaseEnter-unitnum").focus();
				$("#storage-purchaseEnter-unitnum").select();
			}
		});
		$("#storage-purchaseEnter-remark").die("keydown").live("keydown",function(event){
			if(event.keyCode==13){
				$("#storage-savegoon-purchaseEnterDetailEditPage").focus();
			}
		});
		$("#storage-savegoon-purchaseEnterDetailEditPage").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				editSaveDetail(false);
			}
		});

        //商品类型变化
        $("#storage-purchaseEnter-goodstype").change(function(){
            var goodstype =  $("#storage-purchaseEnter-goodstype").val();
            $("#storage-purchaseEnter-hidden-goodstype").val(goodstype);
            if(goodstype == 1){
                $("#storage-purchaseEnter-remark").val("赠品");
                $("#storage-purchaseEnter-taxprice").numberbox("setValue","0");
                computNum();
//					priceChange("1", '#sales-taxprice-orderDetailAddPage');
            }else{
                $("#storage-purchaseEnter-remark").val("");
                var goodsid =  $("#storage-purchaseEnter-goodsid").val();
                var date =  $("#sales-businessdate-orderAddPage").val();
                $.ajax({
                    url: 'storage/getGoodsDetail.do',
                    dataType: 'json',
                    type: 'post',
                    data:'id='+ goodsid ,
                    success: function (json) {
                        price = json.taxprice;
                        $("#storage-purchaseEnter-taxprice").numberbox("setValue",price);
                        computNum();
                    }
                });
            }
        });
	});
	//通过总数量 计算数量 金额换算
	function computNum(){
		var goodsid= $("#storage-purchaseEnter-goodsid").val();
		var auxunitid = $("#storage-purchaseEnter-auxunitid").val();
		var unitnum = $("#storage-purchaseEnter-unitnum").val();
		var taxprice = $("#storage-purchaseEnter-taxprice").val();
		var notaxprice = $("#storage-purchaseEnter-notaxprice").val();
		var taxtype = $("#storage-purchaseEnter-taxtype").val();
		$("#storage-purchaseEnter-unitnum").addClass("inputload");
		$.ajax({
			url :'basefiles/computeGoodsByUnitnum.do',
			type:'post',
			data:{unitnum:unitnum,goodsid:goodsid,auxunitid:auxunitid,taxprice:taxprice,taxtype:taxtype,notaxprice:notaxprice},
			dataType:'json',
			async:false,
			success:function(json){
				$("#storage-purchaseEnter-taxamount").numberbox("setValue",json.taxamount);
				$("#storage-purchaseEnter-notaxamount").numberbox("setValue",json.notaxamount);
				$("#storage-purchaseEnter-tax").numberbox("setValue",json.tax);
				$("#storage-purchaseEnter-taxtypename").val(json.taxtypename);
				$("#storage-purchaseEnter-auxunitnumdetail").val(json.auxnumdetail);
				$("#storage-purchaseEnter-auxunitnum").val(json.auxnum);
				$("#storage-purchaseEnter-auxunitname").val(json.auxunitname);
				$("#storage-purchaseEnter-auxunitname1").html(json.auxunitname);
				$("#storage-purchaseEnter-goodsunitname").val(json.unitname);
				$("#storage-purchaseEnter-goodsunitname1").html(json.unitname);

				$("#storage-purchaseEnter-taxprice").val(json.taxprice);
				$("#storage-purchaseEnter-notaxprice").numberbox("setValue",json.notaxprice);
                $("#storage-purchaseEnter-boxprice").val(json.boxprice);
                $("#storage-purchaseEnter-noxboxprice").val(json.noboxprice);
				$("#storage-purchaseEnter-notaxamount").numberbox("setValue",json.notaxamount);

				$("#storage-purchaseEnter-unitnum-aux").val(json.auxInteger);
				$("#storage-purchaseEnter-unitnum-unit").val(json.auxremainder);
				if(json.auxrate!=null){
					$("#storage-purchaseEnter-unitnum-unit").attr("max",json.auxrate-1);
				}
				$("#storage-purchaseEnter-unitnum").removeClass("inputload");
			}
		});
	}
	//通过辅单位数量
	function computNumByAux(){
		var goodsid= $("#storage-purchaseEnter-goodsid").val();
		var auxunitid = $("#storage-purchaseEnter-auxunitid").val();
		var taxprice = $("#storage-purchaseEnter-taxprice").val();
		var notaxprice = $("#storage-purchaseEnter-notaxprice").numberbox("getValue");
		var taxtype = $("#storage-purchaseEnter-taxtype").val();
		var auxInterger = $("#storage-purchaseEnter-unitnum-aux").val();
		var auxremainder = $("#storage-purchaseEnter-unitnum-unit").val();
		var auxmax = $("#storage-purchaseEnter-unitnum-unit").attr("max");
		if(Number(auxremainder)>Number(auxmax)){
			auxremainder = auxmax;
			$("#storage-purchaseEnter-unitnum-unit").val(auxremainder);
		}
		$("#storage-purchaseEnter-unitnum").addClass("inputload");
		$.ajax({
			url :'basefiles/computeGoodsByAuxnum.do',
			type:'post',
			data:{auxInterger:auxInterger,auxremainder:auxremainder,goodsid:goodsid,auxunitid:auxunitid,taxprice:taxprice,taxtype:taxtype,notaxprice:notaxprice},
			dataType:'json',
			async:false,
			success:function(json){
				$("#storage-purchaseEnter-taxamount").numberbox("setValue",json.taxamount);
				$("#storage-purchaseEnter-notaxamount").numberbox("setValue",json.notaxamount);
				$("#storage-purchaseEnter-tax").numberbox("setValue",json.tax);
				$("#storage-purchaseEnter-taxtypename").val(json.taxtypename);

				$("#storage-purchaseEnter-taxprice").val(json.taxprice);
				$("#storage-purchaseEnter-notaxprice").numberbox("setValue",json.notaxprice);
				$("#storage-purchaseEnter-notaxamount").numberbox("setValue",json.notaxamount);

				$("#storage-purchaseEnter-unitnum").val(json.mainnum);
				$("#storage-purchaseEnter-unitnum-aux").val(json.auxInterger);
				$("#storage-purchaseEnter-unitnum-unit").val(json.auxremainder);
				$("#storage-purchaseEnter-auxunitnumdetail").val(json.auxnumdetail);

				$("#storage-purchaseEnter-unitnum").removeClass("inputload");
			}
		});
	}
	var oldgoodsid = $("#storage-purchaseEnter-goodsid").val();
	var oldstorageid = $("#storage-purchaseEnter-detail-hidden-storageid").val();
	$("#storage-purchaseEnter-detail-storageid").widget("setValue",oldstorageid);
	<c:choose>
	<c:when test="${isbatch == '1'}">
	$("#storage-purchaseEnter-storagelocationid").widget("setValue",object.storagelocationid);
	$("#storage-purchaseEnter-storagelocationid").widget("enable");
	$('#storage-purchaseEnter-batchno').validatebox({required:true});
	$("#storage-purchaseEnter-batchno").removeClass("no_input");
	$('#storage-purchaseEnter-produceddate').validatebox({required:true});
	$("#storage-purchaseEnter-produceddate").removeClass("WdateRead");
	$("#storage-purchaseEnter-produceddate").addClass("Wdate");
	$("#storage-purchaseEnter-produceddate").removeAttr("readonly");
	$("#storage-purchaseEnter-deadline").removeClass("WdateRead");
	$("#storage-purchaseEnter-deadline").addClass("Wdate");
	$("#storage-purchaseEnter-deadline").removeAttr("readonly");
	</c:when>
	<c:otherwise>
	$("#storage-purchaseEnter-storagelocationid").widget("disable");

	$('#storage-purchaseEnter-batchno').validatebox({required:false});
	$("#storage-purchaseEnter-batchno").addClass("no_input");
	$("#storage-purchaseEnter-batchno").attr("readonly","readonly");

	$('#storage-purchaseEnter-produceddate').validatebox({required:false});
	$("#storage-purchaseEnter-produceddate").addClass("no_input");
	$("#storage-purchaseEnter-produceddate").attr("readonly","readonly");

	$('#storage-purchaseEnter-deadline').validatebox({required:false});
	$("#storage-purchaseEnter-deadline").addClass("no_input");
	$("#storage-purchaseEnter-deadline").attr("readonly","readonly");
	</c:otherwise>
	</c:choose>
</script>
</body>
</html>
