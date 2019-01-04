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
   	<form action="" method="post" id="goods-form-adjustMultiPriceDetailAddPage">
   		<table  border="0" class="box_table">
   			<tr>
   				<td width="120">选择商品:</td>
   				<td style="text-align: left;">
   					<input type="text" id="goods-adjustMultiPrice-goodsid" name="goodsid" style="width:150px;" />
   					<input type="hidden" id="goods-adjustMultiPrice-goodsname" name="goodsname" style="width:150px;" />
   				</td>
   				<td colspan="2" style="text-align: left;"><span id="goods-loading-adjustMultiPriceDetailAddPage" ></span></td>
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
				<td >
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
					<input type="text" id="goods-adjustMultiPrice-barcode"  name="barcode" class="no_input" style="width:150px;" readonly="readonly" />
				</td>
				<td>备注:</td>
				<td>
					<input type="text" id="goods-adjustMultiPriceDetail-remark" name="remark"  class="len150"/>
				</td>
			</tr>
   		</table>
    </form>
    </div>

	<div data-options="region:'south',border:false">
		<div class="buttonDetailBG" style="height:30px;text-align: right;">
			快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
			<input type="button" value="继续添加" name="savegoon" id="goods-savegoon-adjustMultiPriceDetailAddPage" />
		</div>
	</div>
  </div>
   <script type="text/javascript">
   		$(function() {

   			$("#goods-adjustMultiPrice-goodsid").goodsWidget({
   				col: 'id',
   				singleSelect: true,
   				width: 150,
   				onSelect: function(data) {
   					if (data) {
   						$("#goods-adjustMultiPrice-goodsname").val(data.name);
   						$("#goods-adjustMultiPrice-barcode").val(data.barcode);
   						$("#goods-adjustMultiPrice-boxnum").val(data.boxnum);
   						$("#goods-loading-adjustMultiPriceDetailAddPage").html("商品编码:<font color='green'>" + data.id + "</font>");
						$("#goods-adjustMultiPrice-oldbuyprice").val(formatterDefineMoney(data.highestbuyprice,6));
						$("#goods-adjustMultiPrice-oldsalesprice").val(formatterDefineMoney(data.basesaleprice,6));
						$("#goods-adjustMultiPrice-newbuyprice").val(formatterDefineMoney(data.highestbuyprice,6));
						$("#goods-adjustMultiPrice-newsalesprice").val(formatterDefineMoney(data.basesaleprice,6));
						getPriceDataByGoodsidAndCode(data.id);
   						
   						$("#goods-adjustMultiPrice-newbuyprice").focus();
   			   			$("#goods-adjustMultiPrice-newbuyprice").select();
   					}
   				}
   			});
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
			//enter快捷键
//			$("#goods-adjustMultiPrice-newbuyprice").die("keydown").live("keydown",function(event){
//
//				if(event.keyCode==13){
//					$("#goods-adjustMultiPrice-newbuyprice").blur();
//					$("#goods-adjustMultiPrice-newsalesprice").focus();
//					$("#goods-adjustMultiPrice-newsalesprice").select();
//				}
//			});
//			$("#goods-adjustMultiPrice-newsalesprice").die("keydown").live("keydown",function(event){
//
//				if(event.keyCode==13){
//					$("#goods-adjustMultiPrice-newsalesprice").blur();
//					$("#goods-adjustMultiPrice-newprice1").focus();
//					$("#goods-adjustMultiPrice-newprice1").select();
//				}
//			});
//			$("#goods-adjustMultiPrice-newprice1").die("keydown").live("keydown",function(event){
//				if(event.keyCode==13){
//					$("#goods-adjustMultiPrice-newprice1").blur();
//					$("#goods-adjustMultiPrice-newprice2").focus();
//					$("#goods-adjustMultiPrice-newprice2").select();
//				}
//			});
//			$("#goods-adjustMultiPrice-newprice2").die("keydown").live("keydown",function(event){
//
//				if(event.keyCode==13){
//					$("#goods-adjustMultiPrice-newprice2").blur();
//					$("#goods-adjustMultiPrice-newprice3").focus();
//					$("#goods-adjustMultiPrice-newprice3").select();
//				}
//			});
//

			$("#goods-savegoon-adjustMultiPriceDetailAddPage").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					    addSaveDetail(true);
				}
			});
		    $("#goods-savegoon-adjustMultiPriceDetailAddPage").click(function(){
				    addSaveDetail(true);
		    });

		});
		
		//获取价格套价格
		function getPriceDataByGoodsidAndCode(goodsid){
			$.ajax({   
	            url :'basefiles/getPriceDataByGoodsidAndOpenCode.do',
	            type:'post',
	            data:{goodsid:goodsid},
	            dataType:'json',
	            async:false,
	            success:function(json){
					for(var index in json){
						$("#goods-adjustMultiPrice-oldprice"+index).val(formatterDefineMoney(json[index],6));
						$("#goods-adjustMultiPrice-newprice"+index).val(formatterDefineMoney(json[index],6));
					}

	            }
	        });
		}


   </script>
  </body>
</html>
