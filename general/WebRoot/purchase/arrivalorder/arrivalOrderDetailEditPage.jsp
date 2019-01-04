<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  </head>
  
  <body>
  <div class="easyui-layout" data-options="fit:true">
  		<div data-options="region:'center',border:false">
	  	<form id="purchase-form-arrivalOrderDetailEditPage" method="post">
	  		<table style="border-collapse:collapse;" border="0" cellpadding="5" cellspacing="5">
				<tr>
					<td style="text-align: right;">商品：</td>
					<td>
							<input type="text" id="purchase-arrivalOrderDetail-goodsname" name="name" style="width:150px;border:1px solid #B3ADAB; background-color: #EBEBE4;" readonly="readonly" />
					</td>
					<td style="text-align: right;">商品编号：</td>
					<td>
							<input type="text" id="purchase-arrivalOrderDetail-goodsid" name="goodsid" readonly="readonly" style="width:150px; border:1px solid #B3ADAB;  background-color: #EBEBE4;"/>
					</td>			
				</tr>
				<tr>
						<td style="text-align: right;">辅数量：</td>
		   			<td style="text-align: left;">
		   					<input type="text" id="purchase-arrivalOrderDetail-auxnum" class="formaterNum" name="auxnum" readonly="readonly" style="width:60px; float:left;border:1px solid #B3ADAB; background-color: #EBEBE4;" />
	   					<span id="purchase-arrivalOrderDetail-span-auxunitname" style="float: left;line-height:25px;">&nbsp;</span>
		   					<input type="text" id="purchase-arrivalOrderDetail-unitnum-auxremainder" class="formaterNum" name="auxremainder" readonly="readonly" style="width:60px;float:left;border:1px solid #B3ADAB; background-color: #EBEBE4;"/>
	   					<span id="purchase-arrivalOrderDetail-span-unitname" style="float: left;line-height:25px;">&nbsp;</span>
	   					<input type="hidden" id="purchase-arrivalOrderDetail-auxnumdetail" name="auxnumdetail" class="no_input" readonly="readonly"/>
		   			</td>
					<td style="text-align: right;">数量：</td>
						<td><input type="text" id="purchase-arrivalOrderDetail-unitnum" class="formaterNum" name="unitnum" readonly="readonly" style="width:150px;border:1px solid #B3ADAB; background-color: #EBEBE4;" /></td>
				</tr>
				<tr>
		   				<td style="text-align: right;">单位：</td>
			    		<td style="float: left;">
			    			主：<input id="purchase-arrivalOrderDetail-unitname" name="unitname" type="text" class="readonly2" style="width: 48px;" readonly="readonly" /><input id="purchase-arrivalOrderDetail-unitid" type="hidden" name="unitid" />
			    			辅：<input id="purchase-arrivalOrderDetail-auxunitname" name="auxunitname" type="text" class="readonly2" style="width: 48px;" readonly="readonly" /><input id="purchase-arrivalOrderDetail-auxunitid" type="hidden" name="auxunitid" />
			    		</td>
			    		<td style="text-align: right;">箱装量：</td>
			    		<td>
			    			<input id="purchase-arrivalOrderDetail-boxnum" type="text" class="len150 readonly" readonly="readonly" />
			    		</td>
		   			</tr>
				<tr>
					<td style="text-align: right;">商品品牌：</td>
					<td><input type="text" id="purchase-arrivalOrderDetail-brand" name="brandName" readonly="readonly" style="width:150px;border:1px solid #B3ADAB; background-color: #EBEBE4;"/></td>
					<td style="text-align: right;">商品规格：</td>
					<td><input type="text" id="purchase-arrivalOrderDetail-model" name="model" readonly="readonly" style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;" /></td>
				</tr>
				<tr>
					<td style="text-align: right;">税种：</td>
					<td>
							<input type="text" id="purchase-arrivalOrderDetail-taxtypename" name="taxtypename" readonly="readonly"  style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;"/>
						<input type="hidden" id="purchase-arrivalOrderDetail-taxtype" name="taxtype" />
					</td>
					<td style="text-align: right;">税额：</td>
						<td><input type="text" id="purchase-arrivalOrderDetail-tax" name="tax" class="easyui-numberbox" data-options="readonly:true,precision:6" readonly="readonly" style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;"/></td>
				</tr>
				<tr>
					<td style="text-align: right;">含税单价：</td>
					<td><input type="text" id="purchase-arrivalOrderDetail-taxprice" name="taxprice" class="easyui-validatebox <c:if test="${colMap.taxprice == null }">readonly</c:if>"  required="required" validType="intOrFloat"  <c:if test="${colMap.taxprice == null }">readonly="readonly"</c:if> style="width:150px;"/></td>
					<td style="text-align: right;">含税金额：</td>
						<td><input type="text" id="purchase-arrivalOrderDetail-taxamount" name="taxamount" class="easyui-validatebox <c:if test="${colMap.taxamount == null }">readonly</c:if>"  required="required" validType="intOrFloat"  <c:if test="${colMap.taxamount == null }">readonly="readonly"</c:if> style="width:150px;"/></td>
				</tr>
				<tr>
					<td style="text-align: right;">未税单价：</td>
					<td><input type="text" id="purchase-arrivalOrderDetail-notaxprice" name="notaxprice" class="easyui-validatebox <c:if test="${colMap.notaxprice == null }">readonly</c:if>"  required="required" validType="intOrFloat"  <c:if test="${colMap.notaxprice == null }">readonly="readonly"</c:if> style="width:150px;"/></td>
					<td style="text-align: right;">未税金额：</td>
						<td><input type="text" id="purchase-arrivalOrderDetail-notaxamount" name="notaxamount" class="easyui-validatebox <c:if test="${colMap.notaxamount == null }">readonly</c:if>"  required="required" validType="intOrFloat"  <c:if test="${colMap.taxamount == null }">readonly="readonly"</c:if> style="width:150px;"/></td>
				</tr>
				<tr>
						<td style="text-align: right;">含税箱价：</td>
						<td><input type="text" id="purchase-arrivalOrderDetail-boxprice" name="boxprice" class="easyui-validatebox <c:if test="${colMap.notaxprice == null }">readonly</c:if>"  required="required" validType="intOrFloat"  <c:if test="${colMap.taxprice == null }">readonly="readonly"</c:if> style="width:150px;"/></td>
						<td style="text-align: right;">未税箱价：</td>
						<td><input type="text" id="purchase-arrivalOrderDetail-noboxprice" name="noboxprice" readonly="readonly" style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;"/></td>
					</tr>
					<tr>
					<td style="text-align: right;">条形码：</td>
					<td><input type="text" id="purchase-arrivalOrderDetail-barcode" name="barcode" readonly="readonly" style="width:150px;  border:1px solid #B3ADAB; background-color: #EBEBE4;"/></td>
					<td style="text-align: right;">批次号：</td>
						<td><input type="text" id="purchase-arrivalOrderDetail-batchno" name="batchno" readonly="readonly" style="width:150px;  border:1px solid #B3ADAB; background-color: #EBEBE4;" /></td>
				</tr>
				<tr>
					<td style="text-align: right;">生产日期：</td>
						<td><input type="text" id="purchase-arrivalOrderDetail-produceddate" name="produceddate" readonly="readonly" style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;" tabindex="4"/></td>
					<td style="text-align: right;">有效截止日期：</td>
						<td><input type="text" id="purchase-arrivalOrderDetail-deadline" name="deadline" readonly="readonly" style="width:150px; border:1px solid #B3ADAB; background-color: #EBEBE4;" tabindex="5"/></td>
				</tr>
				<tr>
					<td style="text-align: right;">备注：</td>
					<td colspan="3">
							<input id="purchase-arrivalOrderDetail-remark" type="text" name="remark" style="width:420px; border:1px solid #B3ADAB; background-color: #EBEBE4;" readonly="readonly"/>
					</td>
				</tr>
			</table>
			<input type="hidden" id="purchase-arrivalOrderDetail-id" name="id" />
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
          <div class="buttonDetailBG" style="height:30px;text-align:right;">
	  			快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span><input type="button" value="确&nbsp;定" name="savenogo" id="purchase-arrivalOrderDetailEditPage-editSave" />
  			</div>
	  	</div>

  	<script type="text/javascript">

		function priceChange(type, id){ //1含税单价或2未税单价改变计算对应数据
			var $this = $(id).css({'background':'url(image/loading.gif) right top no-repeat'});
			var price = $(id).val();
			var goodsid = $("#purchase-arrivalOrderDetail-goodsid").val();
		  		if(goodsid==null || goodsid==""){
		  	  		return false;
		  		}
			var taxtype = $("#purchase-arrivalOrderDetail-taxtype").val();
			var unitnum = $("#purchase-arrivalOrderDetail-unitnum").val();
			var auxnum = $("#purchase-arrivalOrderDetail-auxnum").val();
			$.ajax({
				url:'purchase/common/getAmountChanger.do',
				dataType:'json',
				async:false,
				type:'post',
				data:'type='+ type +'&price='+ price +'&taxtype='+ taxtype +'&unitnum='+ unitnum+ '&id='+ goodsid,
				success:function(json){
					$("#purchase-arrivalOrderDetail-taxprice").val(json.taxPrice);
					$("#purchase-arrivalOrderDetail-boxprice").val(json.boxprice);
					$("#purchase-arrivalOrderDetail-noboxprice").val(json.noboxprice);
					$("#purchase-arrivalOrderDetail-taxamount").val(json.taxAmount);
    				$("#purchase-arrivalOrderDetail-notaxprice").val(json.noTaxPrice);
    				$("#purchase-arrivalOrderDetail-notaxamount").val(json.noTaxAmount);
    				$("#purchase-arrivalOrderDetail-tax").numberbox('setValue',json.tax);
    				$this.css({'background':''});
    			}
    		});
    	}
	function boxpriceChange(id){
			var $this = $(id).css({'background':'url(image/loading.gif) right top no-repeat'});
    		var boxprice = $(id).val();
    		var goodsid = $("#purchase-arrivalOrderDetail-goodsid").val();
  	  		if(goodsid==null || goodsid==""){
  	  	  		return false;
  	  		}
    		var taxtype = $("#purchase-arrivalOrderDetail-taxtype").val();
    		var unitnum = $("#purchase-arrivalOrderDetail-unitnum").val();
    		var auxnum = $("#purchase-arrivalOrderDetail-auxnum").val();
    		$.ajax({
    			url:'purchase/common/getAmountChangerByBoxprice.do',
    			dataType:'json',
    			async:false,
    			type:'post',
    			data:'&boxprice='+ boxprice +'&taxtype='+ taxtype +'&unitnum='+ unitnum+ '&id='+ goodsid,
    			success:function(json){
    				$("#purchase-arrivalOrderDetail-taxprice").val(json.taxPrice);
    				$("#purchase-arrivalOrderDetail-noboxprice").val(json.noboxPrice);
					$("#purchase-arrivalOrderDetail-taxamount").val(json.taxAmount);
					$("#purchase-arrivalOrderDetail-notaxprice").val(json.noTaxPrice);
					$("#purchase-arrivalOrderDetail-notaxamount").val(json.noTaxAmount);
					$("#purchase-arrivalOrderDetail-tax").numberbox('setValue',json.tax);
					$this.css({'background':''});
    			}
    		});
		}
		function amountChange(type, id){ //1含税金额或2未税金额改变计算对应数据
			var $this = $(id).css({'background':'url(image/loading.gif) right top no-repeat'});
			var price = $(id).val();
			var goodsid = $("#purchase-arrivalOrderDetail-goodsid").val();
			if(goodsid==null || goodsid==""){
				return false;
			}
			var taxtype = $("#purchase-arrivalOrderDetail-taxtype").val();
			var unitnum = $("#purchase-arrivalOrderDetail-unitnum").val();
			var auxnum = $("#purchase-arrivalOrderDetail-auxnum").val();
			$.ajax({
				url:'purchase/common/getPriceChanger.do',
				dataType:'json',
				async:false,
				type:'post',
				data:'type='+ type +'&amount='+ price +'&taxtype='+ taxtype +'&unitnum='+ unitnum+ '&id='+ goodsid,
				success:function(json){
					$("#purchase-arrivalOrderDetail-taxprice").val(json.taxPrice);
					$("#purchase-arrivalOrderDetail-boxprice").val(json.boxprice);
					$("#purchase-arrivalOrderDetail-noboxprice").val(json.noboxprice);
					$("#purchase-arrivalOrderDetail-taxamount").val(json.taxAmount);
					$("#purchase-arrivalOrderDetail-notaxprice").val(json.noTaxPrice);
					$("#purchase-arrivalOrderDetail-notaxamount").val(json.noTaxAmount);
					$("#purchase-arrivalOrderDetail-tax").numberbox('setValue',json.tax);
					$this.css({'background':''});
				}
			});
		}
		function saveOrderDetail(){
	    	$("#purchase-arrivalOrderDetail-remark").focus();
  			var flag=$("#purchase-form-arrivalOrderDetailEditPage").form('validate');
  			if(!flag){
	  			return false;
  			}

			var formdata=$("#purchase-form-arrivalOrderDetailEditPage").serializeJSON();
			//保存前判断单价*数量 是否跟金额一致
			var taxprice = formdata.taxamount/formdata.unitnum;
			if(Number(taxprice).toFixed(2) != Number(formdata.taxprice).toFixed(2)){
				$.messager.alert("提醒","单价*数量与金额不一致，无法保存！");
				return false;
			}
			if(formdata){
				$("#purchase-arrivalOrderAddPage-arrivalOrdertable").datagrid('updateRow',{
					index:editRowIndex,
					row:formdata
				});
				footerReCalc();
			}
  			$("#purchase-arrivalOrderAddPage-dialog-DetailOper-content").dialog("close");
		}

		$(document).ready(function(){
			$("#purchase-arrivalOrderDetail-taxprice").focus(function(){
        		if(!$(this).hasClass("readonly")){
            		$(this).select();
        		}else{
        			$("#purchase-arrivalOrderDetail-taxamount").focus();
        		}
    		});
    		
    		$("#purchase-arrivalOrderDetail-taxprice").change(function(){
        		if($(this).validatebox('isValid') && !$(this).hasClass("readonly")){
        			priceChange("1",this);
        		}
    		});

			$("#purchase-arrivalOrderDetail-taxamount").focus(function(){
        		if(!$(this).hasClass("readonly")){
            		$(this).select();
        		}else{
        			$("#purchase-arrivalOrderDetail-notaxprice").focus();
        		}
    		});

    		$("#purchase-arrivalOrderDetail-taxamount").change(function(){
        		if($(this).validatebox('isValid')  && !$(this).hasClass("readonly") ){
        			amountChange("1",this);
        		}
    		});

    		$("#purchase-arrivalOrderDetail-notaxprice").focus(function(){
        		if(!$(this).hasClass("readonly")){
            		$(this).select();
        		}else{
		   			$("#purchase-arrivalOrderDetail-notaxamount").focus();
        		}
    		});
    		$("#purchase-arrivalOrderDetail-notaxprice").change(function(){
        		if($(this).validatebox('isValid')  && !$(this).hasClass("readonly") ){
        			priceChange("2",this);
        		}
    		});
    		
    		$("#purchase-arrivalOrderDetail-notaxamount").focus(function(){
        		if(!$(this).hasClass("readonly")){
            		$(this).select();
        		}else{
        			$("#purchase-arrivalOrderDetail-boxprice").focus();
        		}
    		});

    		$("#purchase-arrivalOrderDetail-notaxamount").change(function(){
        		if($(this).validatebox('isValid')  && !$(this).hasClass("readonly") ){
        			amountChange("2",this);
        		}
    		});
    		
    		$("#purchase-arrivalOrderDetail-boxprice").focus(function(){
    			if(!$(this).hasClass("readonly")){
            		$(this).select();
        		}else{
					$("#purchase-arrivalOrderDetail-remark").focus();
		   			$("#purchase-arrivalOrderDetail-remark").select();
        		}
    		});
    		
			$("#purchase-arrivalOrderDetail-boxprice").change(function(){
				if($(this).validatebox('isValid')  && !$(this).hasClass("readonly") ){
					boxpriceChange(this);
        		}
			});
    		$("#purchase-arrivalOrderDetail-taxprice").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					$("#purchase-arrivalOrderDetail-taxprice").blur();
		   			$("#purchase-arrivalOrderDetail-taxamount").focus();
				}
		    });
	  		$("#purchase-arrivalOrderDetail-taxamount").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					$("#purchase-arrivalOrderDetail-taxamount").blur()
					$("#purchase-arrivalOrderDetail-notaxprice").focus();
				}
		    });
	  		$("#purchase-arrivalOrderDetail-notaxprice").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					$("#purchase-arrivalOrderDetail-notaxprice").blur();
		   			$("#purchase-arrivalOrderDetail-notaxamount").focus();
				}
		    });
	  		$("#purchase-arrivalOrderDetail-notaxamount").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					$("#purchase-arrivalOrderDetail-notaxamount").blur()
		   			$("#purchase-arrivalOrderDetail-boxprice").focus();
				}
		    });
			$("#purchase-arrivalOrderDetail-boxprice").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					$("#purchase-arrivalOrderDetail-boxprice").blur();
					$("#purchase-arrivalOrderDetail-remark").focus();
		   			$("#purchase-arrivalOrderDetail-remark").select();
				}
		    });
	  		$("#purchase-arrivalOrderDetail-remark").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
		   			$("#purchase-arrivalOrderDetail-remark").blur();
		   			$("#purchase-arrivalOrderDetailEditPage-editSave").focus();
				}
		    });
	  		$("#purchase-arrivalOrderDetailEditPage-editSave").die("keydown").live("keydown",function(event){
	  			if(event.keyCode==13){
	  				saveOrderDetail(false);
	  			}
	  		});
	  		$("#purchase-arrivalOrderDetailEditPage-editSave").click(function(){
				saveOrderDetail(false);		
	  		});
		});
	</script>
  </body>
</html>
