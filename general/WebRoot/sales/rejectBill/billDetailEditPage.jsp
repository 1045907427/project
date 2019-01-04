<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
	<title>退货通知单商品详细信息修改页面</title>
</head>
<body>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border:false">
		<form id="sales-form-billDetailEditPage">
			<table cellpadding="5" cellspacing="2">
				<tr>
					<td class="len80">选择商品：</td>
					<td colspan="3">
						<input id="sales-goodsname-billDetailAddPage" readonly="readonly" class="readonly" style="width: 400px;"/>
						<input id="sales-goodsname-hidden-billDetailAddPage" type="hidden" name="goodsid" />
					</td>
				</tr>
				<tr>
					<td>数量</td>
					<td><input id="sales-unitnum-billDetailAddPage" class="formaterNum easyui-validatebox len150" value="0" name="unitnum"  data-options="required:true,validType:'intOrFloatNum[${decimallen}]'" /></td>
					<td>辅数量：</td>
					<td><input id="sales-auxnum-billDetailAddPage" name="auxnum" class="formaterNum easyui-validatebox" value="0" style="width:60px;" data-options="validType:'integer'" /><span id="sales-auxunitname-billDetailAddPage"></span>
						<input id="sales-auxremainder-billDetailAddPage" name="auxremainder" class="formaterNum easyui-validatebox" value="0" style="width:60px;" data-options="validType:'intOrFloatNum[${decimallen}]'" /><span id="sales-unitname-billDetailAddPage"></span>
						<input id="sales-auxnumdetail-billDetailAddPage" type="hidden" name="auxnumdetail" />
					</td>
				</tr>
				<tr>
					<td>单位：</td>
					<td>
						主：<input name="unitname" type="text" class="readonly2" style="width: 40px;" readonly="readonly" /><input type="hidden" name="unitid" />
						辅：<input name="auxunitname" type="text" class="readonly2" style="width: 40px;" readonly="readonly" /><input type="hidden" name="auxunitid" />
					</td>
					<td>商品品牌：</td>
					<td><input id="sales-brandname-billDetailAddPage" readonly="readonly" class="len150 readonly" /></td>
				</tr>
				<tr>
					<td>含税单价：</td>
					<td>
						<input type="hidden" id="back-taxprice" />
						<input id="sales-taxprice-billDetailAddPage" class="len150 easyui-validatebox <c:if test="${colMap.taxprice == null }">readonly</c:if>" name="taxprice" required="required" validType="intOrFloat" <c:if test="${colMap.taxprice == null }">readonly="readonly"</c:if>/> </td>
					<td>含税金额：</td>
					<td><input class="len150 easyui-numberbox" id="sales-taxamount-billDetailAddPage" data-options="precision:6" readonly="readonly" name="taxamount" /></td>
				</tr>
				<tr>
					<td>含税箱价：</td>
					<td>
						<input id="sales-boxprice-billDetailAddPage" class="len150 easyui-validatebox <c:if test="${colMap.taxprice == null }">readonly</c:if>" name="boxprice" required="required" validType="intOrFloat" <c:if test="${colMap.taxprice == null }">readonly="readonly"</c:if> /> </td>
					<td>箱装量：</td>
					<td>
						<input name="boxnum" id="sales-boxnum-billDetailAddPage" type="text" class="len150 readonly" readonly="readonly" /></td>
				</tr>
				<tr>
					<td>未税单价：</td>
					<td><input class="len150 easyui-validatebox <c:if test="${colMap.notaxprice == null }">readonly</c:if>"
							   name="notaxprice" id="sales-notaxprice-billDetailAddPage" required="required" validType="intOrFloat" <c:if test="${colMap.notaxprice == null }">readonly="readonly"</c:if>/>
					</td>
					<td>未税金额：</td>
					<td><input class="len150 easyui-numberbox" id="sales-notaxamount-billDetailAddPage" readonly="readonly" name="notaxamount" data-options="precision:6" /></td>
				</tr>
				<tr>
					<td>税种：</td>
					<td><input class="len150 readonly" name="taxtypename" /><input type="hidden" name="taxtype" /> </td>
					<td>税额：</td>
					<td><input class="len150 readonly easyui-numberbox" id="sales-tax-billDetailAddPage" readonly="readonly" name="tax" data-options="precision:6,groupSeparator:','" /></td>
				</tr>
				<tr>
					<td>规格型号：</td>
					<td><input id="sales-model-billDetailAddPage" readonly="readonly" class="len150 readonly" /></td>
					<td>条形码：</td>
					<td><input id="sales-barcode-billDetailAddPage" class="len150 readonly" readonly="readonly"/></td>
				</tr>
				<tr>
					<td>批次号：</td>
					<td>
						<input id="sales-batchno-billDetailAddPage" type="text" class="len150 "  name="batchno"/>
						<input id="sales-summarybatchid-billDetailAddPage" type="hidden" name="summarybatchid" />
						<input type="hidden" id="sales-isbatch-billDetailAddPage" value="${isbatch}"/>
					</td>
					<td>所属仓库：</td>
					<td>
						<input id="sales-storagelocationname-billDetailAddPage" type="hidden"/>
						<input id="sales-storageid-billDetailAddPage" type="hidden" name="storageid" />
						<input id="sales-storagename-billDetailAddPage" type="text" class="len150 readonly" readonly="readonly" name="storagename" />
						<input id="sales-storagelocationid-billDetailAddPage" type="hidden" name="storagelocationid" />
					</td>
				</tr>
				<tr>
					<td>生产日期：</td>
					<td><input id="sales-produceddate-billDetailAddPage" type="text" class="WdateRead" readonly="readonly" name="produceddate" /> </td>
					<td>截止日期：</td>
					<td><input id="sales-deadline-billDetailAddPage" type="text" class="WdateRead"  readonly="readonly" name="deadline"/></td>
				</tr>
				<tr>
					<td>商品类型：</td>
					<td>
						<select id="sales-deliverytype-billDetailAddPage" name="deliverytype" style="width: 150px;">
							<option value="0">正常商品</option>
							<option value="1">赠品</option>
                            <option value="2">捆绑</option>
						</select>
					</td>
					<td>退货属性：</td>
					<td>
						<select id="sales-rejectcategory-billDetailAddPage" name="rejectcategory" style="width: 150px;">
							<c:forEach items="${rejectCategory }" var="category" varStatus="status">
								<option value="<c:out value="${category.code }"/>"><c:out value="${category.codename }"/></option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>备注：</td>
					<td colspan="3"><input id="sales-remark-billDetailAddPage" type="text" name="remark"  style="width: 392px;"/></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'south',border:false">
		<div class="buttonDetailBG" style="height:30px;text-align:right;">
			快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
			<input type="button" value="确 定" name="savegoon" id="sales-savegoon-billDetailEditPage" />
			<!-- <input type="button" value="确定" name="savenogo" /> -->
		</div>
	</div>
</div>
<script type="text/javascript">



	var storageid = $("#sales-storage-rejectBillAddPage").widget("getValue");
	var presentByZero = '${presentByZero}';
	$(function(){

        <security:authorize url="/storage/rejectBillEditTaxamount.do">
        $("#sales-taxamount-billDetailAddPage").removeAttr("readonly");
        $("#sales-taxamount-billDetailAddPage").numberbox({
            onChange:function(newValue,oldValue){
                if(oldValue != ""){
                    amountChange("1",newValue);
				}
            }
        });

        $("#sales-notaxamount-billDetailAddPage").removeAttr("readonly");
        $("#sales-notaxamount-billDetailAddPage").numberbox({
            onChange:function(newValue,oldValue){
                if(oldValue != ""){
                    amountChange("2",newValue);
                }
            }
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
                    $("#sales-taxprice-billDetailAddPage").val(json.taxPrice);
                    $("#sales-boxprice-billDetailAddPage").val(json.boxPrice);
                    $("#sales-taxamount-billDetailAddPage").numberbox('setValue',json.taxAmount);
                    $("#sales-notaxprice-billDetailAddPage").val(json.noTaxPrice);
                    $("#sales-notaxamount-billDetailAddPage").numberbox('setValue',json.noTaxAmount);
                    $("#sales-tax-billDetailAddPage").numberbox('setValue',json.tax);
                }
            });
        }

        </security:authorize>

		$("#sales-produceddate-billDetailAddPage").click(function(){
			if($("#sales-produceddate-billDetailAddPage").hasClass("Wdate")){
				WdatePicker({dateFmt:'yyyy-MM-dd',
					onpicked:function(dp){
						if(dp.el.id=="sales-produceddate-billDetailAddPage"){
							var produceddate = dp.cal.getDateStr();
							var goodsid = $("#sales-goodsname-hidden-billDetailAddPage").val();
							$.ajax({
								url :'storage/getBatchno.do',
								type:'post',
								data:{produceddate:produceddate,goodsid:goodsid},
								dataType:'json',
								async:false,
								success:function(json){
									$('#sales-deadline-billDetailAddPage').val(json.deadline);
								}
							});
						}
					}
				});
			}
		});
		$("#sales-deadline-billDetailAddPage").click(function(){
			if($("#sales-deadline-billDetailAddPage").hasClass("Wdate")){
				WdatePicker({dateFmt:'yyyy-MM-dd',
					onpicked:function(dp){
						if(dp.el.id=="sales-deadline-billDetailAddPage"){
							var deadline = dp.cal.getDateStr();
							var goodsid = $("#sales-goodsname-hidden-billDetailAddPage").val();
							$.ajax({
								url :'storage/getBatchnoByDeadline.do',
								type:'post',
								data:{deadline:deadline,goodsid:goodsid},
								dataType:'json',
								async:false,
								success:function(json){
									$("#sales-produceddate-billDetailAddPage").val(json.produceddate);
								}
							});
						}
					}
				});
			}
		});
		$("#sales-unitnum-billDetailAddPage").change(function(){
			unitnumChange(1);
		});
		$("#sales-auxnum-billDetailAddPage").change(function(){
			unitnumChange(2);
		});
		$("#sales-auxremainder-billDetailAddPage").change(function(){
			unitnumChange(2);
		});
		$("#sales-taxprice-billDetailAddPage").change(function(){
			priceChange("1", '#sales-taxprice-billDetailAddPage');
		});
		$("#sales-notaxprice-billDetailAddPage").change(function(){
			priceChange("2", '#sales-notaxprice-billDetailAddPage');
		});
		$("#sales-boxprice-billDetailAddPage").change(function(){
			boxpriceChange();
		});
		$("#sales-savegoon-billDetailEditPage").click(function(){
			editSaveDetail(true);
		});
		//商品类型变化
		$("#sales-deliverytype-billDetailAddPage").change(function(){
			var a =  $("#sales-deliverytype-billDetailAddPage").val();
			if(a == 1){
				$("#sales-remark-billDetailAddPage").val("赠品");
				if(presentByZero == 0){
					$("#sales-taxprice-billDetailAddPage").val(0);
					priceChange("1", '#sales-taxprice-billDetailAddPage');
				}
			}else{
				$("#sales-remark-billDetailAddPage").val("");
				var price =  $("#back-taxprice").val();
				if(price == 0.000000 || price == "" ){
					var goodsid = $("#sales-goodsname-hidden-billDetailAddPage").val();
					var customerid =  $("#sales-customer-rejectBillAddPage").val();
					var date =  $("#sales-businessdate-rejectBillAddPage").val();
					$.ajax({
						url: 'sales/getGoodsDetail.do',
						dataType: 'json',
						type: 'post',
						data:'id='+ goodsid +'&cid='+customerid+'&date='+ date+'&type=reject',
						success: function (json) {
							price = json.detail.taxprice;
							$("#sales-taxprice-billDetailAddPage").val(price);
							priceChange("1", '#sales-taxprice-billDetailAddPage');
						}
					});

				}else{
					$("#sales-taxprice-billDetailAddPage").val(price);
					priceChange("1", '#sales-taxprice-billDetailAddPage');
				}
			}
		});


		$("#sales-unitnum-billDetailAddPage").die("keydown").live("keydown",function(event){
			if(event.keyCode==13){
				$("#sales-auxnum-billDetailAddPage").focus();
				$("#sales-auxnum-billDetailAddPage").select();
			}
		});
		$("#sales-auxnum-billDetailAddPage").die("keydown").live("keydown",function(event){
			if(event.keyCode==13){
				$("#sales-auxremainder-billDetailAddPage").focus();
				$("#sales-auxremainder-billDetailAddPage").select();
			}
		});
		$("#sales-auxremainder-billDetailAddPage").die("keydown").live("keydown",function(event){
			if(event.keyCode==13){
				$("#sales-remark-billDetailAddPage").focus();
				$("#sales-remark-billDetailAddPage").select();
			}
		});
		$("#sales-remark-billDetailAddPage").die("keydown").live("keydown",function(event){
			if(event.keyCode==13){
				$("#sales-savegoon-billDetailEditPage").focus();
			}
		});
		$("#sales-savegoon-billDetailEditPage").die("keydown").live("keydown",function(event){
			if(event.keyCode==13){
				editSaveDetail(true);
			}
		});
		$("#sales-boxprice-billDetailAddPage").die("keydown").live("keydown",function(event){
			if(event.keyCode==13){
				$("#sales-remark-billDetailAddPage").focus();
				$("#sales-remark-billDetailAddPage").select();
			}
		});
	});
	function unitnumChange(type){ //数量改变方法
		var $this = $("#sales-unitnum-billDetailAddPage").css({'background':'url(image/loading.gif) right top no-repeat'});
		var goodsId = $("input[name=goodsid]").val();
		var unitnum = $("input[name=unitnum]").val();
		var auxnum = $("input[name=auxnum]").val();
		var auxremainder = $("input[name=auxremainder]").val();
		var aid = $("input[name=auxunitid]").val();
		var taxprice = $("#sales-taxprice-billDetailAddPage").val();
		var notaxprice = $("#sales-notaxprice-billDetailAddPage").val();
		var taxtype = $("input[name=taxtype]").val();
		$.ajax({
			url:'sales/getAuxUnitNum.do',
			dataType:'json',
			type:'post',
			async:false,
			data:'id='+ goodsId +'&unitnum='+ unitnum +'&aid='+ aid +'&tp='+ taxprice +'&auxnum='+ auxnum +'&taxtype='+ taxtype+ '&overnum='+ auxremainder+ '&type='+ type,
			success:function(json){
				$("input[name=auxnumdetail]").val(json.auxnumdetail);
				$("#sales-taxamount-billDetailAddPage").numberbox('setValue',json.taxAmount);
				$("#sales-notaxamount-billDetailAddPage").numberbox('setValue',json.noTaxAmount);
				$("#sales-tax-billDetailAddPage").numberbox('setValue',json.tax);
				if(type == 1){
					$("input[name=auxnum]").val(json.auxnum);
					$("input[name=auxremainder]").val(json.overnum);
				}
				if(type == 2){
					$("input[name=unitnum]").val(json.unitnum);
					$("input[name=auxnum]").val(json.auxnum);
					$("input[name=auxremainder]").val(json.overnum);
				}
				$this.css({'background':''});
			}
		});
	}
	function priceChange(type, id){ //1含税单价或2未税单价改变计算对应数据
		var $this = $(id).css({'background':'url(image/loading.gif) right top no-repeat'});
		var price = $(id).val();
		var goodsId = $("input[name=goodsid]").val();
		var taxtype = $("input[name=taxtype]").val();
		var unitnum = $("input[name=unitnum]").val();
		var auxnum = $("input[name=auxnum]").val();
		$.ajax({
			url:'sales/getAmountChanger.do',
			dataType:'json',
			async:false,
			type:'post',
			data:'type='+ type +'&price='+ price +'&taxtype='+ taxtype +'&unitnum='+ unitnum+ '&id='+ goodsId,
			success:function(json){
				$("#sales-taxprice-billDetailAddPage").val(json.taxPrice);
				$("#sales-boxprice-billDetailAddPage").val(json.boxPrice);
				$("#sales-taxamount-billDetailAddPage").numberbox('setValue',json.taxAmount);
				$("#sales-notaxprice-billDetailAddPage").val(json.noTaxPrice);
				$("#sales-notaxamount-billDetailAddPage").numberbox('setValue',json.noTaxAmount);
				$("#sales-tax-billDetailAddPage").numberbox('setValue',json.tax);
				$this.css({'background':''});
			}
		});
	}
	function boxpriceChange(){
		var $this = $("#sales-boxprice-billDetailAddPage").css({'background':'url(image/loading.gif) right top no-repeat'});
		var price = $("#sales-boxprice-billDetailAddPage").val();
		var goodsId = $("input[name=goodsid]").val();
		var taxtype = $("input[name=taxtype]").val();
		var unitnum = $("input[name=unitnum]").val();
		var auxnum = $("input[name=auxnum]").val();
		$.ajax({
			url:'sales/getAmountChangerByBoxprice.do',
			dataType:'json',
			async:false,
			type:'post',
			data:'&boxprice='+ price +'&taxtype='+ taxtype +'&unitnum='+ unitnum+ '&id='+ goodsId,
			success:function(json){
				$("#sales-taxprice-billDetailAddPage").val(json.taxPrice);
				$("#sales-boxprice-billDetailAddPage").val(json.boxPrice);
				$("#sales-taxamount-billDetailAddPage").numberbox('setValue',json.taxAmount);
				$("#sales-notaxprice-billDetailAddPage").val(json.noTaxPrice);
				$("#sales-notaxamount-billDetailAddPage").numberbox('setValue',json.noTaxAmount);
				$("#sales-tax-billDetailAddPage").numberbox('setValue',json.tax);
				$this.css({'background':''});
			}
		});
	}
</script>
</body>
</html>
