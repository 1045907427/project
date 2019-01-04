<%@ page language="java" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>调价商品添加</title>
</head>

<body>
<div class="easyui-layout" data-options="fit:true">
	<div data-options="region:'center',border:false">
		<form action="" method="post" id="goods-form-adjustMultiPriceDetailEditPage">
			<table  border="0" class="box_table">
				<tr>
					<td width="120">选择商品:</td>
					<td style="text-align: left;">
						<input type="text" id="goods-adjustMultiPrice-goodsid" name="goodsid" readonly="readonly" class="no_input" style="width:150px;" />
						<input type="hidden" id="goods-adjustMultiPrice-goodsname" name="goodsname" style="width:150px;" />
					</td>
					<td colspan="2" style="text-align: left;"><span id="goods-loading-adjustMultiPriceDetailEditPage" ></span></td>
				</tr>
				<tr>
					<td>原始采购价:</td>
					<td style="text-align: left;">
						<input type="text" id="goods-adjustMultiPrice-oldbuyprice" name="oldbuyprice" style="width:150px;" class="no_input" readonly="readonly" />
					</td>
					<td>调整采购价:</td>
					<td >
						<input type="text" id="goods-adjustMultiPrice-newbuyprice" name="newbuyprice" class="easyui-validatebox len150" data-options="required:true,validType:'intOrFloat'"/>
					</td>
				</tr>
				<tr>
					<td>原始销售价:</td>
					<td style="text-align: left;">
						<input type="text" id="goods-adjustMultiPrice-oldsalesprice" name="oldsalesprice" style="width:150px;" class="no_input" readonly="readonly" />
					</td>
					<td>调整销售价:</td>
					<td >
						<input type="text" id="goods-adjustMultiPrice-newsalesprice" name="newsalesprice" class="easyui-validatebox len150" data-options="required:true,validType:'intOrFloat'"/>
					</td>
				</tr>
				<c:if test="${colMap.price1 != null}">
					<tr>
						<td>1号价:</td>
						<td style="text-align: left;">
							<input type="text" id="goods-adjustMultiPrice-oldprice1" name="oldprice1" style="width:150px;" class="no_input" readonly="readonly" />
						</td>
						<td>调整1号价:</td>
						<td >
							<input type="text" id="goods-adjustMultiPrice-newprice1" name="newprice1" class="easyui-validatebox len150" data-options="required:true,validType:'intOrFloat'"/>
						</td>
					</tr>
				</c:if>
				<c:if test="${colMap.price2 != null}">
					<tr>
						<td>2号价:</td>
						<td style="text-align: left;">
							<input type="text" id="goods-adjustMultiPrice-oldprice2" name="oldprice2" style="width:150px;" class="no_input" readonly="readonly" />
						</td>
						<td>调整2号价:</td>
						<td >
							<input type="text" id="goods-adjustMultiPrice-newprice2" name="newprice2" class="easyui-validatebox len150" data-options="required:true,validType:'intOrFloat'"/>
						</td>
					</tr>
				</c:if>
				<c:if test="${colMap.price3 != null}">
					<tr>
						<td>3号价:</td>
						<td style="text-align: left;">
							<input type="text" id="goods-adjustMultiPrice-oldprice3" name="oldprice3" style="width:150px;" class="no_input" readonly="readonly" />
						</td>
						<td>调整3号价:</td>
						<td >
							<input type="text" id="goods-adjustMultiPrice-newprice3" name="newprice3" class="easyui-validatebox len150" data-options="required:true,validType:'intOrFloat'"/>
						</td>
					</tr>
				</c:if>
				<c:if test="${colMap.price4 != null}">
					<tr>
						<td>4号价:</td>
						<td style="text-align: left;">
							<input type="text" id="goods-adjustMultiPrice-oldprice4" name="oldprice4" style="width:150px;" class="no_input" readonly="readonly" />
						</td>
						<td>调整4号价:</td>
						<td >
							<input type="text" id="goods-adjustMultiPrice-newprice4" name="newprice4" class="easyui-validatebox len150" data-options="required:true,validType:'intOrFloat'"/>
						</td>
					</tr>
				</c:if>
				<c:if test="${colMap.price5 != null}">
					<tr>
						<td>5号价:</td>
						<td style="text-align: left;">
							<input type="text" id="goods-adjustMultiPrice-oldprice5" name="oldprice5" style="width:150px;" class="no_input" readonly="readonly" />
						</td>
						<td>调整5号价:</td>
						<td >
							<input type="text" id="goods-adjustMultiPrice-newprice5" name="newprice5" class="easyui-validatebox len150" data-options="required:true,validType:'intOrFloat'"/>
						</td>
					</tr>
				</c:if>
				<c:if test="${colMap.price6 != null}">
					<tr>
						<td>6号价:</td>
						<td style="text-align: left;">
							<input type="text" id="goods-adjustMultiPrice-oldprice6" name="oldprice6" style="width:150px;" class="no_input" readonly="readonly" />
						</td>
						<td>调整6号价:</td>
						<td >
							<input type="text" id="goods-adjustMultiPrice-newprice6" name="newprice6" class="easyui-validatebox len150" data-options="required:true,validType:'intOrFloat'"/>
						</td>
					</tr>
				</c:if>
				<c:if test="${colMap.price7 != null}">
					<tr>
						<td>7号价:</td>
						<td style="text-align: left;">
							<input type="text" id="goods-adjustMultiPrice-oldprice7" name="oldprice7" style="width:150px;" class="no_input" readonly="readonly" />
						</td>
						<td>调整7号价:</td>
						<td >
							<input type="text" id="goods-adjustMultiPrice-newprice7" name="newprice7" class="easyui-validatebox len150" data-options="required:true,validType:'intOrFloat'"/>
						</td>
					</tr>
				</c:if>
				<c:if test="${colMap.price8 != null}">
					<tr>
						<td>8号价:</td>
						<td style="text-align: left;">
							<input type="text" id="goods-adjustMultiPrice-oldprice8" name="oldprice8" style="width:150px;" class="no_input" readonly="readonly" />
						</td>
						<td>调整8号价:</td>
						<td >
							<input type="text" id="goods-adjustMultiPrice-newprice8" name="newprice8" class="easyui-validatebox len150" data-options="required:true,validType:'intOrFloat'"/>
						</td>
					</tr>
				</c:if>
				<c:if test="${colMap.price9 != null}">
					<tr>
						<td>9号价:</td>
						<td style="text-align: left;">
							<input type="text" id="goods-adjustMultiPrice-oldprice9" name="oldprice9" style="width:150px;" class="no_input" readonly="readonly" />
						</td>
						<td>调整9号价:</td>
						<td>
							<input type="text" id="goods-adjustMultiPrice-newprice9" name="newprice9" class="easyui-validatebox len150" data-options="required:true,validType:'intOrFloat'"/>
						</td>
					</tr>
				</c:if>
				<c:if test="${colMap.price10 != null}">
					<tr>
						<td>10号价:</td>
						<td style="text-align: left;">
							<input type="text" id="goods-adjustMultiPrice-oldprice10" name="oldprice10" style="width:150px;" class="no_input" readonly="readonly" />
						</td>
						<td>调整10号价:</td>
						<td >
							<input type="text" id="goods-adjustMultiPrice-newprice10" name="newprice10" class="easyui-validatebox len150" data-options="required:true,validType:'intOrFloat'"/>
						</td>
					</tr>
				</c:if>
				<tr>
                    <td>条形码:</td>
                    <td>
                        <input type="text" id="goods-adjustMultiPrice-barcode"  name="barcode" style="width:150px;" class="no_input" readonly="readonly" />
                    </td>
					<td>备注:</td>
					<td>
						<input type="text" id="goods-adjustMultiPriceDetail-remark" name="remark" class="len150" />
					</td>

				</tr>
			</table>
		</form>
	</div>

	<div data-options="region:'south',border:false">
		<div class="buttonDetailBG" style="height:30px;text-align: right;">
			快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
			<input type="button" value="确定" name="savegoon" id="goods-savegoon-adjustMultiPriceDetailEditPage" />
		</div>
	</div>
</div>
<script type="text/javascript">

	//加载数据
	var object=$("#goods-datagrid-adjustMultiPriceAddPage").datagrid("getSelected");
	$("#goods-adjustMultiPrice-goods").val(object.goodsname);
	$("#goods-adjustMultiPrice-goodsid").val(object.goodsid);
	$("#goods-adjustMultiPrice-goodsname").val(object.goodsname);
	$("#goods-loading-adjustMultiPriceDetailAddPage").html("商品编码:<font color='green'>" + object.goodsid + "</font>");

	$("#goods-adjustMultiPrice-oldbuyprice").val(formatterDefineMoney(object.oldbuyprice,6));
	$("#goods-adjustMultiPrice-newbuyprice").val(formatterDefineMoney(object.newbuyprice,6));
	$("#goods-adjustMultiPrice-oldsalesprice").val(formatterDefineMoney(object.oldsalesprice,6));
	$("#goods-adjustMultiPrice-newsalesprice").val(formatterDefineMoney(object.newsalesprice,6));
	$("#goods-adjustMultiPrice-oldprice1").val(formatterDefineMoney(object.oldprice1,6));
	$("#goods-adjustMultiPrice-newprice1").val(formatterDefineMoney(object.newprice1,6));
	$("#goods-adjustMultiPrice-oldprice2").val(formatterDefineMoney(object.oldprice2,6));
	$("#goods-adjustMultiPrice-newprice2").val(formatterDefineMoney(object.newprice2,6));
	$("#goods-adjustMultiPrice-oldprice3").val(formatterDefineMoney(object.oldprice3,6));
	$("#goods-adjustMultiPrice-newprice3").val(formatterDefineMoney(object.newprice3,6));
	$("#goods-adjustMultiPrice-oldprice4").val(formatterDefineMoney(object.oldprice4,6));
	$("#goods-adjustMultiPrice-newprice4").val(formatterDefineMoney(object.newprice4,6));
	$("#goods-adjustMultiPrice-oldprice5").val(formatterDefineMoney(object.oldprice5,6));
	$("#goods-adjustMultiPrice-newprice5").val(formatterDefineMoney(object.newprice5,6));
	$("#goods-adjustMultiPrice-oldprice6").val(formatterDefineMoney(object.oldprice6,6));
	$("#goods-adjustMultiPrice-newprice6").val(formatterDefineMoney(object.newprice6,6));
	$("#goods-adjustMultiPrice-oldprice7").val(formatterDefineMoney(object.oldprice7,6));
	$("#goods-adjustMultiPrice-newprice7").val(formatterDefineMoney(object.newprice7,6));
	$("#goods-adjustMultiPrice-oldprice8").val(formatterDefineMoney(object.oldprice8,6));
	$("#goods-adjustMultiPrice-newprice8").val(formatterDefineMoney(object.newprice8,6));
	$("#goods-adjustMultiPrice-oldprice9").val(formatterDefineMoney(object.oldprice9,6));
	$("#goods-adjustMultiPrice-newprice9").val(formatterDefineMoney(object.newprice9,6));
	$("#goods-adjustMultiPrice-oldprice10").val(formatterDefineMoney(object.oldprice10,6));
	$("#goods-adjustMultiPrice-newprice10").val(formatterDefineMoney(object.newprice10,6));

	$("#goods-adjustMultiPrice-rate").val(formatterMoney(object.rate));
	$("#goods-adjustMultiPrice-rate1").val(formatterMoney(object.rate));
	$("#goods-adjustMultiPrice-boxnum").val(formatterBigNumNoLen(object.boxnum));
	$("#goods-adjustMultiPrice-goodsbrandName").val(object.goodsbrandName);
	$("#goods-adjustMultiPrice-barcode").val(object.barcode);
	$("#goods-adjustMultiPrice-remark").val(object.remark);


	$(function() {

		//采购价调整
		$("#goods-adjustMultiPrice-newbuyprice").change(function(){
			if($(this).val()!=""){
				var newprice=$(this).val();
				$("#goods-adjustMultiPrice-newbuyprice").val(formatterDefineMoney(newprice,6));
			}else{
				$("#goods-adjustMultiPrice-newbuyprice").focus();
			}
		});
		//销售价调整
		$("#goods-adjustMultiPrice-newsalesprice").change(function(){
			if($(this).val()!=""){
				var newprice=$(this).val();
				$("#goods-adjustMultiPrice-newsalesprice").val(formatterDefineMoney(newprice,6));
			}
			else{
				$("#goods-adjustMultiPrice-newsalesprice").focus();
			}
		});
		//价格套调整
		$("#goods-adjustMultiPrice-newprice1").change(function(){
			if($(this).val()!=""){
				var nowprice=$(this).val();
				$("#goods-adjustMultiPrice-newprice1").val(formatterDefineMoney(nowprice,6));
			}else{
				$(this).focus();
			}
		});
		$("#goods-adjustMultiPrice-newprice2").change(function(){
			if($(this).val()!=""){
				var nowprice=$(this).val();
				$("#goods-adjustMultiPrice-newprice2").val(formatterDefineMoney(nowprice,6));
			}else{
				$(this).focus();
			}
		});
		$("#goods-adjustMultiPrice-newprice3").change(function(){
			if($(this).val()!=""){
				var nowprice=$(this).val();
				$("#goods-adjustMultiPrice-newprice3").val(formatterDefineMoney(nowprice,6));
			}else{
				$(this).focus();
			}
		});
		$("#goods-adjustMultiPrice-newprice4").change(function(){
			if($(this).val()!=""){
				var nowprice=$(this).val();
				$("#goods-adjustMultiPrice-newprice4").val(formatterDefineMoney(nowprice,6));
			}else{
				$(this).focus();
			}
		});
		$("#goods-adjustMultiPrice-newprice5").change(function(){
			if($(this).val()!=""){
				var nowprice=$(this).val();
				$("#goods-adjustMultiPrice-newprice5").val(formatterDefineMoney(nowprice,6));
			}else{
				$(this).focus();
			}
		});
		$("#goods-adjustMultiPrice-newprice6").change(function(){
			if($(this).val()!=""){
				var nowprice=$(this).val();
				$("#goods-adjustMultiPrice-newprice6").val(formatterDefineMoney(nowprice,6));
			}else{
				$(this).focus();
			}
		});
		$("#goods-adjustMultiPrice-newprice7").change(function(){
			if($(this).val()!=""){
				var nowprice=$(this).val();
				$("#goods-adjustMultiPrice-newprice7").val(formatterDefineMoney(nowprice,6));
			}else{
				$(this).focus();
			}
		});
		$("#goods-adjustMultiPrice-newprice8").change(function(){
			if($(this).val()!=""){
				var nowprice=$(this).val();
				$("#goods-adjustMultiPrice-newprice8").val(formatterDefineMoney(nowprice,6));
			}else{
				$(this).focus();
			}
		});
		$("#goods-adjustMultiPrice-newprice9").change(function(){
			if($(this).val()!=""){
				var nowprice=$(this).val();
				$("#goods-adjustMultiPrice-newprice9").val(formatterDefineMoney(nowprice,6));
			}else{
				$(this).focus();
			}
		});
		$("#goods-adjustMultiPrice-newprice10").change(function(){
			if($(this).val()!=""){
				var nowprice=$(this).val();
				$("#goods-adjustMultiPrice-newprice10").val(formatterDefineMoney(nowprice,6));
			}else{
				$(this).focus();
			}
		});

		$("#goods-savegoon-adjustMultiPriceDetailEditPage").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				editSaveDetail(true);
			}
		});
		//确定
		$("#goods-savegoon-adjustMultiPriceDetailEditPage").click(function(){
			editSaveDetail(true);
		});


	});




</script>
</body>
</html>
