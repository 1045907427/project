<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>采购发票明细添加</title>
  </head>
  
  <body>
   	<form action="" method="post" id="account-form-purchaseInvoiceDetailAddPage">
   		<table  border="0" class="box_table">
   			<tr>
   				<td width="120">商品:</td>
   				<td style="text-align: left;">
   					<input type="text" id="account-purchaseInvoice-goodsname" width="180" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="account-purchaseInvoice-goodsid" name="goodsid" width="170"/>
   				</td>
   				<td width="120">来源单据编号:</td>
   				<td style="text-align: left;">
   					<input type="text" id="account-purchaseInvoice-billsourceid" name="sourceid" width="180" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="account-purchaseInvoice-sourcedetailid" name="sourcedetailid"/>
   				</td>
     		</tr>
   			<tr>
   				<td width="120">商品品牌:</td>
   				<td>
   					<input type="text" id="account-purchaseInvoice-goodsbrandName" class="no_input" readonly="readonly"/>
   				</td>
   				<td width="120">规格型号:</td>
   				<td>
   					<input type="text" id="account-purchaseInvoice-goodsmodel" class="no_input" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td>主单位:</td>
   				<td>
   					<input type="text" id="account-purchaseInvoice-goodsunitname" name="unitname" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="account-purchaseInvoice-goodsunitid" name="unitid"/>
   				</td>
   				<td>辅单位:</td>
   				<td>
   					<input type="text" id="account-purchaseInvoice-auxunitname" name="auxunitname" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="account-purchaseInvoice-auxunitid" name="auxunitid"/>
   				</td>
   			</tr>
   			<tr>
   				<td>数量:</td>
   				<td style="text-align: left">
   					<input type="text" id="account-purchaseInvoice-unitnum" name="unitnum" class="no_input"/>
   				</td>
   				<td>辅数量:</td>
   				<td>
   					<input type="text" id="account-purchaseInvoice-auxunitnumdetail" name="auxnumdetail" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="account-purchaseInvoice-auxunitnum" name="auxnum"/>
   				</td>
   			</tr>
   			<tr>
   				<td>含税单价:</td>
   				<td style="text-align: left">
   					<input type="text" id="account-purchaseInvoice-taxprice" name="taxprice" class="no_input" readonly="readonly">
   				</td>
   				<td>含税金额:</td>
   				<td  style="text-align: left">
   					<input type="text" id="account-purchaseInvoice-taxamount" name="taxamount" class="no_input" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td>未税单价:</td>
   				<td style="text-align: left">
   					<input type="text" id="account-purchaseInvoice-notaxprice" name="notaxprice" class="no_input" readonly="readonly"/>
   				</td>
   				<td>未税金额:</td>
   				<td style="text-align: left">
   					<input type="text" id="account-purchaseInvoice-notaxamount" name="notaxamount" class="no_input" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td>税种:</td>
   				<td>
   					<input type="text" id="account-purchaseInvoice-taxtypename" name="taxtypename" class="no_input" readonly="readonly"/>
   					<input type="hidden" id="account-purchaseInvoice-taxtype" name="taxtype" class="no_input" readonly="readonly"/>
   				</td>
   				<td>税额:</td>
   				<td style="text-align: left">
   					<input type="text" id="account-purchaseInvoice-tax" name="tax" class="no_input" readonly="readonly"/>
   				</td>
   			</tr>
   			<tr>
   				<td>备注:</td>
   				<td colspan="3" style="text-align: left;">
   					<input type="text" name="remark" style="width: 487px;" maxlength="200"/>
   				</td>
   			</tr>
   		</table>
   		
    </form>
   <script type="text/javascript">
   		var purchaseInvoice_AjaxConn = function (Data, Action) {
		    var MyAjax = $.ajax({
		        type: 'post',
		        cache: false,
		        url: Action,
		        data: Data,
		        async: false
		    })
		    return MyAjax.responseText;
		}
   		//加载数据
		var object = $("#account-datagrid-purchaseInvoiceAddPage").datagrid("getSelected");
		$("#account-form-purchaseInvoiceDetailAddPage").form("load",object);
		$("#account-purchaseInvoice-billsourceid").val(object.sourceid);
		$("#account-purchaseInvoice-goodsname").val(object.goodsname);
		$("#account-purchaseInvoice-goodsbrandName").val(object.brandname);
		$("#account-purchaseInvoice-goodsmodel").val(object.model);
		$.extend($.fn.validatebox.defaults.rules, {
			checkUnitNum:{
				validator:function(value,param){
					var orderid = $("#account-purchaseInvoice-billsourceid").val();
					var goodsid = $("#account-purchaseInvoice-goodsid").val();
					var billid = $("#account-purchaseInvoice-id").val();
					var sourceid = $("#account-purchaseInvoice-billsourceid").val();
		    		var sourcedetailid = $("#account-purchaseInvoice-sourcedetailid").val();
					var ret=purchaseInvoice_AjaxConn({unitnum:value,goodsid:goodsid,billid:billid,sourceid:sourceid,sourcedetailid:sourcedetailid},'account/payable/checkUnitNum.do');
					var json = $.parseJSON(ret);
					return json.flag;
				},
				message:'数量不能大于未开票数量!'
			}
		});
		
		$(function(){
			$("#account-purchaseInvoice-unitnum").numberbox({
                precision:${decimallen},
				validType:"checkUnitNum",
				onChange:function(newValue,oldValue){
					if("2" == object.sourcetype && newValue > 0){
						$("#account-purchaseInvoice-unitnum").numberbox("setValue",-newValue);
					}
	    			unitnumChange(object.sourcetype);
				}
			});
			$("#account-purchaseInvoice-taxprice").numberbox({
				precision:6,
				groupSeparator:','
			});
			$("#account-purchaseInvoice-taxamount").numberbox({
				precision:2,
				groupSeparator:','
			});
			$("#account-purchaseInvoice-notaxprice").numberbox({
				precision:6,
				groupSeparator:','
			});
			$("#account-purchaseInvoice-notaxamount").numberbox({
				precision:2,
				groupSeparator:','
			});
			$("#account-purchaseInvoice-tax").numberbox({
				precision:2,
				groupSeparator:','
			});
			$("#account-purchaseInvoice-discountamount").numberbox({
				precision:2,
				groupSeparator:','
			});
		});
		
		var isdiscount = $("#account-purchaseInvoice-isdiscount").val();
		if(isdiscount=="0"){
			$("#account-purchaseInvoice-discountamount").addClass("no_input");
			$("#account-purchaseInvoice-discountamount").attr("readonly","readonly");
		}
		
		function unitnumChange(type){ //数量改变方法
    		var $this = $("#account-purchaseInvoice-unitnum").css({'background':'url(image/loading.gif) right top no-repeat'});
    		var billid = $("#account-purchaseInvoice-id").val();
    		var goodsId = $("input[name=goodsid]").val();
    		var unitnum = $("input[name=unitnum]").val();
    		var auxnum = $("input[name=auxnum]").val();
    		var aid = $("input[name=auxunitid]").val();
    		var taxprice = $("#account-purchaseInvoice-taxprice").val();
    		var notaxprice = $("#account-purchaseInvoice-notaxprice").val();
    		var taxtype = $("input[name=taxtype]").val();
    		var sourceid = $("#account-purchaseInvoice-billsourceid").val();
    		var sourcedetailid = $("#account-purchaseInvoice-sourcedetailid").val();
    		$.ajax({
    			url:'account/payable/getUnitnumChange.do',
    			dataType:'json',
    			type:'post',
    			async:false,
    			data:{'id': goodsId ,'unitnum': unitnum ,'tp': taxprice ,
        				'notp': notaxprice ,'auxnum': auxnum ,'taxtype': taxtype,
        				'type': type ,'billid': billid,'sourceid':sourceid,'sourcedetailid':sourcedetailid},
    			success:function(json){
   					$("#account-purchaseInvoice-taxamount").numberbox('setValue',json.taxAmount);
    				$("#account-purchaseInvoice-notaxamount").numberbox('setValue',json.noTaxAmount);
    				$("#account-purchaseInvoice-tax").numberbox('setValue',json.tax);
    				$("input[name=auxnum]").val(json.auxnum);
    				$("#account-purchaseInvoice-auxunitnumdetail").val(json.auxnumdetail);
    				$this.css({'background':''});
    			}
    		});
    	}
    	function unitnumTriggerChange() {
            unitnumChange(object.sourcetype);
        }
   </script>
  </body>
</html>
