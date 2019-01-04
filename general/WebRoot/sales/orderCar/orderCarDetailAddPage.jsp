<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
    <title>零售订单详细信息新增页面</title>
  </head>
  <body>
  	<div class="easyui-layout" data-options="fit:true">
  		<div data-options="region:'center',border:false">
		  	<form id="sales-form-orderCarDetailAddPage">
			    <table cellpadding="5" cellspacing="5">
			    	<tr>
			    		<td class="len80">选择商品：</td>
			    		<td>
			    			<input id="sales-goodsId-orderCarDetailAddPage" class="len150" />
			    			<input type="hidden" name="goodsid" />
			    			<input type="hidden" name="usablenum"/>
							<input type="hidden" name="initprice"/>
			    		</td>
			    		<td id="sales-loading-orderCarDetailAddPage" colspan="2"></td>
			    	</tr>
			    	<tr>
			    		<td>数量</td>
			    		<td><input class="easyui-validatebox len150" value="0" name="unitnum" onfocus="this.select();frm_focus('unitnum');" onblur="frm_blur('unitnum');" data-options="required:true,validType:'intOrFloatNum[${decimallen}]'" /></td>
			    		<td>辅数量：</td>
			    		<td><input name="auxnum" class="easyui-validatebox" value="0" style="width:60px;" onfocus="this.select();frm_focus('auxnum');" onblur="frm_blur('auxnum');" data-options="validType:'integer'" /><span id="sales-auxunitname-orderCarDetailAddPage"></span>
					    	<input name="overnum" class="easyui-validatebox" value="0" style="width:60px;" onfocus="this.select();frm_focus('overnum');" onblur="frm_blur('overnum');" data-options="validType:'intOrFloatNum[${decimallen}]'" /><span id="sales-unitname-orderCarDetailAddPage"></span>
						</td>
			    	</tr>
			    	<tr>
			    		<td>主单位：</td>
			    		<td><input name="unitname" type="text" class="len150 readonly" readonly="readonly" /><input type="hidden" name="unitid" /></td>
			    		<td>辅单位：</td>
			    		<td><input name="auxunitname" type="text" class="len150 readonly" readonly="readonly" /><input type="hidden" name="auxunitid" /></td>
			    	</tr>
			    	<tr>
			    		<td>含税单价：</td>
			    		<td><input class="len150 easyui-validatebox <c:if test="${colMap.taxprice == null }">readonly</c:if>" name="taxprice" onfocus="this.select();frm_focus('taxprice');" onblur="frm_blur('taxprice');" id="sales-taxprice-orderCarDetailAddPage" required="required" validType="intOrFloat" <c:if test="${colMap.taxprice == null }">readonly="readonly"</c:if> /> </td>
			    		<td>含税金额：</td>
			    		<td><input class="len150 easyui-numberbox <c:if test="${colMap.taxamount == null }">readonly</c:if>" id="sales-taxamount-orderCarDetailAddPage" onfocus="this.select();frm_focus('taxamount');" data-options="precision:6,groupSeparator:','" name="taxamount" <c:if test="${colMap.taxamount == null }">readonly="readonly"</c:if> /></td>
			    	</tr>
					<tr>
						<td>箱装量：</td>
						<td><input type="text" name="boxnum" id="sales-boxnum-orderDetailAddPage" type="text" class="len150 readonly" readonly="readonly" /></td>
					</tr>
			    	<tr>
			    		<td>未税单价：</td>
			    		<td><input class="len150 easyui-validatebox readonly" name="notaxprice" id="sales-notaxprice-orderCarDetailAddPage" required="required" validType="intOrFloat" readonly="readonly" /> </td>
			    		<td>未税金额：</td>
			    		<td><input class="len150 readonly easyui-numberbox" id="sales-notaxamount-orderCarDetailAddPage" readonly="readonly" name="notaxamount" data-options="precision:6,groupSeparator:','" /></td>
			    	</tr>
			    	<tr>
			    		<td>税种：</td>
			    		<td><input class="len150 readonly" name="taxtypename" /><input type="hidden" name="taxtype" /> </td>
			    		<td>税额：</td>
			    		<td><input class="len150 readonly easyui-numberbox" id="sales-tax-orderCarDetailAddPage" readonly="readonly" name="tax" data-options="precision:6,groupSeparator:','" /></td>
			    	</tr>
			    	<tr>
			    		<td>商品品牌：</td>
			    		<td><input name="brandName" readonly="readonly" class="len150 readonly" /></td>
			    		<td>条形码：</td>
			    		<td><input class="len150 readonly" readonly="readonly" name="barcode" /></td>
			    	</tr>
			    	<tr>
			    		<td>备注：</td>
			    		<td colspan="3"><input id="sales-remark-orderCarDetailAddPage" type="text" style="width: 400px;" name="remark" onfocus="frm_focus('remark');" onblur="frm_blur('remark');" /></td>
			    	</tr>
			    </table>
			    <input type="hidden" name="total" />
			    <input type="hidden" name="sunitname" />
		    </form>
  		</div>
  		<div data-options="region:'south',border:false">
  			<div class="buttonDetailBG" style="height:30px;text-align:right;">
  				快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
	  			<input type="button" value="继续添加" name="savegoon" id="sales-savegoon-orderCarDetailAddPage" />
	  			<!-- <input type="button" value="确定" name="savenogo" /> -->
  			</div>
  		</div>
  	</div>
    <script type="text/javascript">
    	var outstorageid = $("#sales-storageid-orderAddPage").widget("getValue");
    	var date = '${date}';
    	var detailrows = $wareList.datagrid('getRows');
    	var goodsids = "";
   		for(var i=0; i<detailrows.length; i++){
   			var rowJson = detailrows[i];
   			if(rowJson.goodsid != undefined){
   				if(goodsids==""){
					goodsids = rowJson.goodsid;
   				}else{
  					goodsids += ","+rowJson.goodsid;
   				}
   			}
   		}
    	$(function(){
    		$("#sales-goodsId-orderCarDetailAddPage").storageGoodsWidget({
    			width:150,
    			required:true,
    			param:[
					{field:'storageid',op:'equal',value:outstorageid}
				],
    			onSelect: function(data){
    				$("input[name=goodsid]").val(data.goodsid);
    				$("input[name=brandName]").val(data.brandname);
    				$("input[name=barcode]").val(data.barcode);
					$("input[name=boxnum]").val(formatterBigNumNoLen(data.boxnum));
    				$("input[name=usablenum]").val(data.existingnum);
    				$("#sales-loading-orderCarDetailAddPage").addClass("img-loading");
    				$.ajax({
    					url:'sales/getGoodsDetail.do',
    					dataType:'json',
    					type:'post',
    					async:false,
    					data:'id='+ data.goodsid +'&cid=${customerId}&date='+ date,
    					success:function(json){
    						$("#sales-loading-orderCarDetailAddPage").removeClass("img-loading").html("商品编码：<font color='green'>"+json.detail.goodsInfo.id+"</font>&nbsp;可用量：<font color='green'>"+ data.existingnum + json.unitname +"</font>");
    						$("input[name=unitid]").val(json.detail.goodsInfo.mainunit);
    						$("input[name=unitname]").val(json.detail.goodsInfo.mainunitName);
    						$("#sales-unitname-orderCarDetailAddPage").text(json.detail.goodsInfo.mainunitName);
    						$("input[name=auxunitid]").val(json.detail.auxunitid);
    						$("input[name=auxunitname]").val(json.detail.auxunitname);
    						$("#sales-auxunitname-orderCarDetailAddPage").text(json.detail.auxunitname);
    						$("input[name=taxtype]").val(json.detail.taxtype);
    						$("input[name=taxtypename]").val(json.detail.taxtypename);
    						$("#sales-taxprice-orderCarDetailAddPage").val(json.detail.taxprice);
							$("input[name=initprice]").val(json.detail.taxprice);
							$("#sales-boxprice-orderDetailAddPage").val(json.detail.boxprice);
    						$("#sales-taxamount-orderCarDetailAddPage").numberbox('setValue',json.detail.taxamount);
    						$("#sales-notaxprice-orderCarDetailAddPage").val(json.detail.notaxprice);
    						$("#sales-notaxamount-orderCarDetailAddPage").numberbox('setValue',json.detail.notaxamount);
    						$("#sales-tax-orderCarDetailAddPage").numberbox('setValue',json.detail.tax);
    						$("input[name=remark]").val(json.detail.remark);
    						$("input[name=total]").val(json.total);
    						$("input[name=sunitname]").val(json.unitname);
    						$("input[name=unitnum]").focus();
    					}
    				});
    			}
    		});
    		$("#sales-savegoon-orderCarDetailAddPage").click(function(){
    			addSaveDetail(true);
    		});
    		$("input[name=unitnum]").change(function(){
    			unitnumChange(1);
    		});
    		$("input[name=auxnum]").change(function(){
    			unitnumChange(2);
    		});
    		$("input[name=overnum]").change(function(){
    			unitnumChange(2);
    		});
    		$("#sales-taxprice-orderCarDetailAddPage").change(function(){
    			 priceChange("1", '#sales-taxprice-orderCarDetailAddPage');
    		});
    		$("#sales-notaxprice-orderCarDetailAddPage").change(function(){
    			priceChange("2", '#sales-notaxprice-orderCarDetailAddPage');
    		});
    		$("#sales-taxamount-orderCarDetailAddPage").numberbox({
    			onChange:function(newValue,oldValue){
    				if(oldValue!=null){
			    		var taxtype = $("input[name=taxtype]").val();
			    		var unitnum = $("input[name=unitnum]").val();
			    		$.ajax({
			    			url:'sales/getTaxAmountChange.do',
			    			dataType:'json',
			    			async:false,
			    			type:'post',
			    			data:'&taxamount='+ newValue +'&taxtype='+ taxtype +'&unitnum='+ unitnum,
			    			success:function(json){
			    				$("#sales-taxprice-orderCarDetailAddPage").val(json.taxPrice);
			    				$("#sales-taxamount-orderCarDetailAddPage").numberbox('setValue',json.taxAmount);
			    				$("#sales-notaxprice-orderCarDetailAddPage").val(json.noTaxPrice);
			    				$("#sales-notaxamount-orderCarDetailAddPage").numberbox('setValue',json.noTaxAmount);
			    				$("#sales-tax-orderCarDetailAddPage").numberbox('setValue',json.tax);
			    			}
			    		});
		    		}
    			}
    		});
    	});
    	function unitnumChange(type){ //数量改变方法
    		var goodsId = $("input[name=goodsid]").val();
    		var unitnum = $("input[name=unitnum]").val();
    		var auxnum = $("input[name=auxnum]").val();
    		var overnum = $("input[name=overnum]").val();
    		var customerId = $("#sales-customer-orderAddPage").customerWidget("getValue");
    		var data = "";
			var url = "";
    		if(type == 1){
    			url = "sales/getAuxUnitNumAndPrice.do"
    			data = 'id='+ goodsId +'&unitnum='+ unitnum + '&cid='+ customerId + '&date='+ date;
    		}
    		else if(type == 2){
    			url = "sales/getUnitNumAndPrice.do"
    			data = 'id='+ goodsId +'&auxnum='+ auxnum + '&overnum='+ overnum+ '&cid='+ customerId + '&date='+ date;
    		}
    		$.ajax({
    			url:url,
    			dataType:'json',
    			type:'post',
    			async:false,
    			data:data,
    			success:function(json){
   					$("#sales-taxamount-orderCarDetailAddPage").numberbox('setValue',json.taxamount);
    				$("#sales-notaxamount-orderCarDetailAddPage").numberbox('setValue',json.notaxamount);
    				$("#sales-taxprice-orderCarDetailAddPage").val(json.taxprice);
    				$("#sales-notaxprice-orderCarDetailAddPage").val(json.notaxprice);
    				$("#sales-tax-orderCarDetailAddPage").numberbox('setValue',json.tax);
    				$("input[name=auxnum]").val(json.auxnum);
    				$("input[name=overnum]").val(json.overnum);
    				$("input[name=unitnum]").val(json.unitnum);
    				$("input[name=remark]").val(json.remark);
    			}
    		});
    	}
    	function priceChange(type, id){ //1含税单价或2未税单价改变计算对应数据
    		var $this = $(id).css({'background':'url(image/loading.gif) right top no-repeat'});
    		var price = $(id).val();
    		var goodsId = $("input[name=goodsid]").val();
    		var taxtype = $("input[name=taxtype]").val();
    		var unitnum = $("input[name=unitnum]").val();
    		$.ajax({
    			url:'sales/getAmountChanger.do',
    			dataType:'json',
    			async:false,
    			type:'post',
    			data:'type='+ type +'&price='+ price +'&taxtype='+ taxtype +'&unitnum='+ unitnum+ '&id='+ goodsId,
    			success:function(json){
    				$("#sales-taxprice-orderCarDetailAddPage").val(json.taxPrice);
    				$("#sales-taxamount-orderCarDetailAddPage").numberbox('setValue',json.taxAmount);
    				$("#sales-notaxprice-orderCarDetailAddPage").val(json.noTaxPrice);
    				$("#sales-notaxamount-orderCarDetailAddPage").numberbox('setValue',json.noTaxAmount);
    				$("#sales-tax-orderCarDetailAddPage").numberbox('setValue',json.tax);
    				$this.css({'background':''});
    			}
    		});
    	}
    </script>
  </body>
</html>
