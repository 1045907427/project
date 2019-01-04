<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>采购退货通知单明细添加</title>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border:false">
		<form action="" method="post" id="purchase-form-returnCheckOrderDetailEditPage">
			<table  border="0" class="box_table">
				<tr>
					<td width="120">商品：</td>
					<td style="text-align: left;">
						<input type="text" id="purchase-returnCheckOrderDetail-goodsname" width="180" class="no_input" readonly="readonly"/>
						<input type="hidden" id="purchase-returnCheckOrderDetail-goodsid" name="goodsid" width="170"/>
					</td>
					<td id="purchase-returnCheckOrderDetail-loadInfo" colspan="2" style="text-align: left;"></td>
				</tr>
				<tr>
					<td style="text-align: right;">辅数量：</td>
					<td style="text-align: left;">
						<input type="text" id="purchase-returnCheckOrderDetail-auxnum" name="auxnum" class="formaterNum easyui-validatebox readonly" validType="integer" data-options="required:true" style="width:60px; float:left;" readonly="readonly" />
						<span id="purchase-returnCheckOrderDetail-span-auxunitname" style="float: left;line-height:25px;">&nbsp;</span>
						<input type="text" id="purchase-returnCheckOrderDetail-unitnum-auxremainder" name="auxremainder" class="formaterNum easyui-validatebox readonly" required="true" style="width:60px;float:left;" readonly="readonly"/>
						<span id="purchase-returnCheckOrderDetail-span-unitname" style="float: left;line-height:25px;">&nbsp;</span>
						<input type="hidden" id="purchase-returnCheckOrderDetail-auxnumdetail" name="auxnumdetail" class="no_input" readonly="readonly"/>
					</td>
					<td style="text-align: right;">数量：</td>
					<td><input type="text" id="purchase-returnCheckOrderDetail-unitnum" name="unitnum" class="formaterNum easyui-validatebox readonly" required="true" readonly="readonly"/></td>
				</tr>
				<tr>
					<td style="text-align: right;">单位：</td>
					<td style="float: left;">
						<span style="float: left;">主：</span><input id="purchase-returnCheckOrderDetail-unitname" name="unitname" type="text" class="readonly2" style="width: 58px;" readonly="readonly" /><input id="purchase-returnCheckOrderDetail-unitid" type="hidden" name="unitid" />
						<span style="float: left;">辅：</span><input id="purchase-returnCheckOrderDetail-auxunitname" name="auxunitname" type="text" class="readonly2" style="width: 58px;" readonly="readonly" /><input id="purchase-returnCheckOrderDetail-auxunitid" type="hidden" name="auxunitid" />
					</td>
					<td style="text-align: right;">箱装量：</td>
					<td>
						<input id="purchase-returnCheckOrderDetail-boxnum" name="boxnum" type="text" class="readonly" readonly="readonly" />
					</td>
				</tr>
				<tr>
					<td>含税单价：</td>
					<td>
						<input type="text" id="purchase-returnCheckOrderDetail-taxprice" name="taxprice" class="easyui-validatebox <c:if test="${colMap.taxprice == null }">readonly</c:if>" <c:if test="${colMap.taxprice == null }"> precision="2" readonly="readonly"</c:if> required="required" validType="intOrFloat">
					</td>
					<td>含税金额：</td>
					<td>
						<input type="text" id="purchase-returnCheckOrderDetail-taxamount" name="taxamount" class="no_input" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>未税单价：</td>
					<td>
						<input type="text" id="purchase-returnCheckOrderDetail-notaxprice" name="notaxprice"  class="easyui-validatebox <c:if test="${colMap.taxprice == null }">readonly</c:if>" <c:if test="${colMap.taxprice == null }">readonly="readonly"</c:if> required="required" validType="intOrFloat"/>
					</td>
					<td>未税金额：</td>
					<td>
						<input type="text" id="purchase-returnCheckOrderDetail-notaxamount" name="notaxamount" class="no_input" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td style="text-align: right;">含税箱价：</td>
					<td><input type="text" id="purchase-returnCheckOrderDetail-boxprice" name="boxprice" class="easyui-validatebox <c:if test="${colMap.taxprice == null }">readonly</c:if>" <c:if test="${colMap.taxprice == null }">readonly="readonly"</c:if> required="required" validType="intOrFloat"/></td>
					<td style="text-align: right;">未税箱价：</td>
					<td><input type="text" id="purchase-returnCheckOrderDetail-noboxprice" name="noboxprice" readonly="readonly" class="no_input"/></td>
				</tr>
				<tr>
					<td width="120">商品品牌：</td>
					<td>
						<input type="text" id="purchase-returnCheckOrderDetail-goodsbrandName" class="no_input" readonly="readonly"/>
					</td>
					<td width="120">规格型号：</td>
					<td>
						<input type="text" id="purchase-returnCheckOrderDetail-goodsmodel" class="no_input" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>税种：</td>
					<td>
						<input type="text" id="purchase-returnCheckOrderDetail-taxtypename" name="taxtypename" class="no_input" readonly="readonly"/>
						<input type="hidden" id="purchase-returnCheckOrderDetail-taxtype" name="taxtype" class="no_input" readonly="readonly"/>
					</td>
					<td>税额：</td>
					<td>
						<input type="text" id="purchase-returnCheckOrderDetail-tax" name="tax" class="no_input" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>所属库位：</td>
					<td style="text-align: left;">
						<input type="text" id="purchase-returnCheckOrderDetail-storagelocationname" name="storagelocationname" class="no_input" readonly="readonly"/>
						<input type="hidden" id="purchase-returnCheckOrderDetail-storagelocationid"  name="storagelocationid"/>
					</td>
					<td>批次号：</td>
					<td>
						<input type="text" id="purchase-returnCheckOrderDetail-batchno" name="batchno" class="no_input" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>生产日期：</td>
					<td>
						<input type="text" id="purchase-returnCheckOrderDetail-produceddate" style="height: 20px;" name="produceddate" class="no_input" readonly="readonly"/>
					</td>
					<td>有效截止日期：</td>
					<td>
						<input type="text" id="purchase-returnCheckOrderDetail-deadline" style="height: 20px;" name="deadline" class="no_input" readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<td>备注：</td>
					<td colspan="3" style="text-align: left;">
						<input type="text" id="purchase-returnCheckOrderDetail-remark" name="remark" style="width: 488px;" maxlength="200"/>
					</td>
				</tr>
			</table>
			<%--<input type="hidden" name="boxnum" id="purchase-returnCheckOrderDetail-boxnum" />--%>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="buttonDetailBG" style="height:28px;text-align:right;">
			快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span><input type="button" value="确定" name="savenogo" id="purchase-returnCheckOrderDetailEditPage-editSave" />
		</div>
	</div>
</div>
<script type="text/javascript">
	//加载数据
	var object = $("#purchase-returnCheckOrderAddPage-returnCheckOrdertable").datagrid("getSelected");
	$("#purchase-form-returnCheckOrderDetailEditPage").form("load",object);
	$("#purchase-returnCheckOrderDetail-goodsname").val(object.goodsInfo.name);
	$("#purchase-returnCheckOrderDetail-goodsbrandName").val(object.goodsInfo.brandName);
	$("#purchase-returnCheckOrderDetail-goodsmodel").val(object.goodsInfo.model);
	$("#purchase-returnCheckOrderDetail-boxnum").val(formatterBigNum(object.goodsInfo.boxnum));

	//通过总数量 计算数量 金额换算
	function computeNum(){
		var goodsid= $("#purchase-returnCheckOrderDetail-goodsid").val();
		var auxunitid = $("#purchase-returnCheckOrderDetail-auxunitid").val();
		var unitnum = $("#purchase-returnCheckOrderDetail-unitnum").val();
		var taxprice = $("#purchase-returnCheckOrderDetail-taxprice").val();
		if(taxprice==null || taxprice==""){
			taxprice="0";
		}
		var notaxprice = $("#purchase-returnCheckOrderDetail-notaxprice").val();
		var taxtype = $("#purchase-returnCheckOrderDetail-taxtype").val();


		$("#purchase-returnCheckOrderDetail-auxnum").addClass("inputload");
		$("#purchase-returnCheckOrderDetail-auxremainder").addClass("inputload");
		$.ajax({
			url :'basefiles/computeGoodsByUnitnum.do',
			type:'post',
			data:{unitnum:unitnum,goodsid:goodsid,auxunitid:auxunitid,taxprice:taxprice,taxtype:taxtype,notaxprice:notaxprice},
			dataType:'json',
			async:false,
			success:function(json){
				$("#purchase-returnCheckOrderDetail-taxamount").val(json.taxamount);
				$("#purchase-returnCheckOrderDetail-notaxamount").val(json.notaxamount);
				$("#purchase-returnCheckOrderDetail-tax").val(json.tax);
				$("#purchase-returnCheckOrderDetail-taxtypename").val(json.taxtypename);
				$("#purchase-returnCheckOrderDetail-auxnumdetail").val(json.auxnumdetail);
				$("#purchase-returnCheckOrderDetail-auxunitnum").val(json.auxnum);
				$("#purchase-returnCheckOrderDetail-auxunitname").val(json.auxunitname);
				$("#purchase-returnCheckOrderDetail-span-auxunitname").html(json.auxunitname);
				$("#purchase-returnCheckOrderDetail-unitname").val(json.unitname);
				$("#purchase-returnCheckOrderDetail-span-unitname").html(json.unitname);

				$("#purchase-returnCheckOrderDetail-taxprice").val(json.taxprice);
				$("#purchase-returnCheckOrderDetail-notaxprice").val(json.notaxprice);
				$("#purchase-returnCheckOrderDetail-boxprice").val(json.boxprice);
				$("#purchase-returnCheckOrderDetail-noboxprice").val(json.noboxprice);

				$("#purchase-returnCheckOrderDetail-auxnum").val(json.auxInteger);
				$("#purchase-returnCheckOrderDetail-unitnum-auxremainder").val(json.auxremainder);
				if(json.auxrate!=null){
					$("#purchase-returnCheckOrderAddPag-unitnum-auxremainder").attr("max",json.auxrate-1);
				}
				$("#purchase-returnCheckOrderDetail-auxnum").removeClass("inputload");
				$("#purchase-returnCheckOrderDetail-auxremainder").removeClass("inputload");
			}
		});
	}
	//通过辅单位数量
	function computeNumByAux(){
		var goodsid= $("#purchase-returnCheckOrderDetail-goodsid").val();
		var auxunitid = $("#purchase-returnCheckOrderDetail-auxunitid").val();
		var taxprice = $("#purchase-returnCheckOrderDetail-taxprice").val();
		if(taxprice==null || taxprice==""){
			taxprice="0";
		}
		var notaxprice = $("#purchase-returnCheckOrderDetail-notaxprice").val();
		var taxtype = $("#purchase-returnCheckOrderDetail-taxtype").val();
		var auxInterger = $("#purchase-returnCheckOrderDetail-auxnum").val();
		var auxremainder = $("#purchase-returnCheckOrderDetail-unitnum-auxremainder").val();

		$("#purchase-returnCheckOrderDetail-auxnum").addClass("inputload");
		$("#purchase-returnCheckOrderDetail-auxremainder").addClass("inputload");
		$("#purchase-returnCheckOrderDetail-unitnum").addClass("inputload");
		$.ajax({
			url :'basefiles/computeGoodsByAuxnum.do',
			type:'post',
			data:{auxInterger:auxInterger,auxremainder:auxremainder,goodsid:goodsid,auxunitid:auxunitid,taxprice:taxprice,taxtype:taxtype,notaxprice:notaxprice},
			dataType:'json',
			async:false,
			success:function(json){
				$("#purchase-returnCheckOrderDetail-taxamount").val(json.taxamount);
				$("#purchase-returnCheckOrderDetail-notaxamount").val(json.notaxamount);
				$("#purchase-returnCheckOrderDetail-tax").val(json.tax);
				$("#purchase-returnCheckOrderDetail-taxtypename").val(json.taxtypename);

				$("#purchase-returnCheckOrderDetail-taxprice").val(json.taxprice);
				$("#purchase-returnCheckOrderDetail-notaxprice").val(json.notaxprice);
				$("#purchase-returnCheckOrderDetail-notaxamount").numberbox("setValue",json.notaxamount);

				$("#purchase-returnCheckOrderDetail-unitnum").val(json.mainnum);

				$("#purchase-returnCheckOrderDetail-auxnum").removeClass("inputload");
				$("#purchase-returnCheckOrderDetail-auxremainder").removeClass("inputload");
				$("#purchase-returnCheckOrderDetail-unitnum").removeClass("inputload");
			}
		});
		var auxunitname = $("#purchase-returnCheckOrderDetail-auxunitname").val();
		var unitname = $("#purchase-returnCheckOrderDetail-unitname").val();
		var auxdetail = auxInterger+auxunitname;
		if(auxremainder>0){
			auxdetail =auxdetail+ auxremainder+unitname;
		}
		$("#purchase-returnCheckOrderDetail-auxnumdetail").val(auxdetail);
	}
	function saveOrderDetail(){
		//$("#purchase-returnCheckOrderDetail-remark").focus();
		var flag=$("#purchase-form-returnCheckOrderDetailEditPage").form('validate');
		if(!flag){
			return false;
		}

		var formdata=$("#purchase-form-returnCheckOrderDetailEditPage").serializeJSON();

		if(formdata){
			$("#purchase-returnCheckOrderAddPage-returnCheckOrdertable").datagrid('updateRow',{
				index:editRowIndex,
				row:formdata
			});
			footerReCalc();
		}
		$("#purchase-returnCheckOrderAddPage-dialog-DetailOper-content").dialog("close");
	}
	function priceChange(type, id){ //1含税单价或2未税单价改变计算对应数据
		var price = $(id).val();
		var goodsid = $("#purchase-returnCheckOrderDetail-goodsid").val();
		if(goodsid==null || goodsid==""){
			return false;
		}
		var taxtype = $("#purchase-returnCheckOrderDetail-taxtype").val();
		var unitnum = $("#purchase-returnCheckOrderDetail-unitnum").val();
		var auxnum = $("#purchase-returnCheckOrderDetail-auxnum").val();
		$.ajax({
			url:'purchase/common/getAmountChanger.do',
			dataType:'json',
			async:false,
			type:'post',
			data:'type='+ type +'&price='+ price +'&taxtype='+ taxtype +'&unitnum='+ unitnum+ '&id='+ goodsid,
			success:function(json){
				$("#purchase-returnCheckOrderDetail-taxprice").val(json.taxPrice);
				$("#purchase-returnCheckOrderDetail-boxprice").val(json.boxprice);
				$("#purchase-returnCheckOrderDetail-noboxprice").val(json.noboxprice);
				$("#purchase-returnCheckOrderDetail-taxamount").val(json.taxAmount);
				$("#purchase-returnCheckOrderDetail-notaxprice").val(json.noTaxPrice);
				$("#purchase-returnCheckOrderDetail-notaxamount").val(json.noTaxAmount);
				$("#purchase-returnCheckOrderDetail-tax").val(json.tax);
			}
		});
	}
	function boxpriceChange(id){
        var $this = $(id);
        $this.css({'background':'url(image/loading.gif) right top no-repeat'});
		var price = $(id).val();
		var goodsid = $("#purchase-returnCheckOrderDetail-goodsid").val();
		if(goodsid==null || goodsid==""){
			return false;
		}
		var taxtype = $("#purchase-returnCheckOrderDetail-taxtype").val();
		var unitnum = $("#purchase-returnCheckOrderDetail-unitnum").val();
		var auxnum = $("#purchase-returnCheckOrderDetail-auxnum").val();
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
                $("#purchase-returnCheckOrderDetail-taxprice").val(json.taxprice);
                $("#purchase-returnCheckOrderDetail-noboxprice").val(json.noboxprice);
                $("#purchase-returnCheckOrderDetail-boxprice").val(json.boxprice);
                $("#purchase-returnCheckOrderDetail-taxamount").val(json.taxamount);
                $("#purchase-returnCheckOrderDetail-notaxprice").val(json.notaxprice);
                $("#purchase-returnCheckOrderDetail-notaxamount").val(json.notaxamount);
                $("#purchase-returnCheckOrderDetail-tax").val(json.tax);
                $this.css({'background':''});
			}
		});
	}
	$(function(){

//			$("#purchase-returnCheckOrderDetail-taxamount").numberbox({
//				precision:6,
//				groupSeparator:',',
//				required:true
//			});
//			$("#purchase-returnCheckOrderDetail-notaxamount").numberbox({
//				precision:6,
//				groupSeparator:',',
//				required:true
//			});
//			$("#purchase-returnCheckOrderDetail-tax").numberbox({
//				precision:6,
//				groupSeparator:',',
//				required:true
//			});
		$("#purchase-returnCheckOrderDetail-deadline").click(function(){
			if(!$("#purchase-returnCheckOrderDetail-batchno").hasClass("no_input")){
				WdatePicker({dateFmt:'yyyy-MM-dd'});
			}
		});
		$("#purchase-returnCheckOrderDetail-produceddate").click(function(){
			if(!$("#purchase-returnCheckOrderDetail-batchno").hasClass("no_input")){
				WdatePicker({dateFmt:'yyyy-MM-dd'});
			}
		});

		//控件采购退货的最大数量
		$.ajax({
			url :'storage/getStorageSummaryBatchInfo.do',
			type:'post',
			data:{summarybatchid:object.summarybatchid},
			dataType:'json',
			success:function(json){
				if(json.storageSummaryBatch!=null){
					var unitname = $("#purchase-returnCheckOrderDetail-unitname").val();
					var status = $("#purchase-returnCheckOrderDetail-status").val();
					var usablenum = json.storageSummaryBatch.usablenum;
					//当采购退货出库单处于保存状态 订单修改时
					if(object.id!=null && status=='2'){
						$.ajax({
							url :'storage/getPurchaseRejectOutDetailInfo.do',
							type:'post',
							data:{id:object.id},
							dataType:'json',
							success:function(data){
								var maxnum = Number(data.detail.unitnum)+Number(usablenum);
								$("#purchase-returnCheckOrderDetail-loadInfo").html("现存量：<font color='green'>"+json.storageSummaryBatch.existingnum+unitname+"</font>&nbsp;可用量：<font color='green'>"+ maxnum +unitname+"</font>");
								$("#purchase-returnCheckOrderDetail-unitnum").attr("max",maxnum);
							}
						});
					}else{
						var maxnum = Number(object.unitnum)+Number(json.storageSummaryBatch.usablenum);
						$("#purchase-returnCheckOrderDetail-loadInfo").html("现存量：<font color='green'>"+json.storageSummaryBatch.existingnum+unitname+"</font>&nbsp;可用量：<font color='green'>"+ json.storageSummaryBatch.usablenum +unitname+"</font>");
						$("#purchase-returnCheckOrderDetail-unitnum").attr("max", json.storageSummaryBatch.usablenum);

					}
				}
			}
		});

		$("#purchase-returnCheckOrderDetailEditPage-editSave").click(function(){
			saveOrderDetail();
		});

		<%--
          $("#purchase-returnCheckOrderDetail-unitnum").change(function(){
            if($(this).validatebox('isValid')){
                computeNum();
            }
        });
        $("#purchase-returnCheckOrderDetail-auxnum").change(function(){
            if($(this).validatebox('isValid')){
                computeNumByAux();
            }
        });
        $("#purchase-returnCheckOrderDetail-unitnum-auxremainder").change(function(){
            if($(this).validatebox('isValid')){
                computeNumByAux();
            }
        });
        --%>
		$("#purchase-returnCheckOrderDetail-taxprice").focus(function(){
			if(!$(this).hasClass("readonly")){
				$(this).select();
			}else{
				$("#purchase-returnCheckOrderDetail-notaxprice").focus();
			}
		});

		$("#purchase-returnCheckOrderDetail-taxprice").change(function(){
			if($(this).validatebox('isValid') && !$(this).hasClass("readonly")){
				priceChange("1",this);
			}
		});

		$("#purchase-returnCheckOrderDetail-notaxprice").change(function(){
			if($(this).validatebox('isValid')  && !$(this).hasClass("readonly") ){
				priceChange("2",this);
			}
		});
		$("#purchase-returnCheckOrderDetail-boxprice").change(function(){
			if($(this).validatebox('isValid')  && !$(this).hasClass("readonly") ){
				boxpriceChange(this);
			}
		});
		$("#purchase-returnCheckOrderDetail-notaxprice").focus(function(){
			if(!$(this).hasClass("readonly")){
				$(this).select();
			}else{
				$("#purchase-returnCheckOrderDetail-remark").focus();
				$("#purchase-returnCheckOrderDetail-remark").select();
			}
		});

		$("#purchase-returnCheckOrderDetail-auxnum").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#purchase-returnCheckOrderDetail-auxnum").blur();
				$("#purchase-returnCheckOrderDetail-unitnum-auxremainder").focus();
				$("#purchase-returnCheckOrderDetail-unitnum-auxremainder").select();
			}
		});
		$("#purchase-returnCheckOrderDetail-unitnum-auxremainder").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#purchase-returnCheckOrderDetail-unitnum-auxremainder").blur();
				$("#purchase-returnCheckOrderDetail-unitnum").focus();
				$("#purchase-returnCheckOrderDetail-unitnum").select();
			}
		});
		$("#purchase-returnCheckOrderDetail-unitnum").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#purchase-returnCheckOrderDetail-unitnum").blur();
				$("#purchase-returnCheckOrderDetail-taxprice").focus();
			}
		});
		$("#purchase-returnCheckOrderDetail-taxprice").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#purchase-returnCheckOrderDetail-taxprice").blur();
				$("#purchase-returnCheckOrderDetail-notaxprice").focus();
			}
		});
		$("#purchase-returnCheckOrderDetail-notaxprice").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#purchase-returnCheckOrderDetail-notaxprice").blur()
				$("#purchase-returnCheckOrderDetail-remark").focus();
				$("#purchase-returnCheckOrderDetail-remark").select();
			}
		});
		$("#purchase-returnCheckOrderDetail-remark").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#purchase-returnCheckOrderDetail-remark").blur();
				$("#purchase-returnCheckOrderDetailEditPage-editSave").focus();
			}
		});
		$("#purchase-returnCheckOrderDetailEditPage-editSave").die("keydown").live("keydown",function(event){
			if(event.keyCode==13){
				saveOrderDetail(false);
			}
		});
	});

</script>
</body>
</html>