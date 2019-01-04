<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>调价商品添加</title>
  </head>
  
  <body>  
  <div class="easyui-layout" data-options="fit:true">
 	<div data-options="region:'center',border:false">
   	<form action="" method="post" id="goods-form-adjustPriceDetailAddPage">
   		<table  border="0" class="box_table">
   			<tr>
   				<td width="120">选择商品:</td>
   				<td style="text-align: left;">
   					<input type="text" id="goods-adjustPrice-goodsid" name="goodsid" style="width:150px;" />
   					<input type="hidden" id="goods-adjustPrice-goodsname" name="goodsname" style="width:150px;" />
   				</td>
   				<td colspan="2" style="text-align: left;"><span id="goods-loading-adjustPriceDetailAddPage" ></span></td>
   			</tr>
   			<tr>
   			    <td>原价:</td>
   				<td style="text-align: left;">
   					<input type="text" id="goods-adjustPrice-oldprice" name="oldprice" style="width:150px;" class="no_input" readonly="readonly" />
   				</td>
   				<td>现价:</td>
   				<td >
   					<input type="text" id="goods-adjustPrice-nowprice" name="nowprice" style="width:150px;" data-options="required:true,validType:'intOrFloat'"/>
   				</td>
   			</tr>
   			<tr>
   			    <td>原箱价:</td>
   				<td style="text-align: left;">
   					<input type="text" id="goods-adjustPrice-oldboxprice" name="oldboxprice" style="width:150px;" class="no_input" readonly="readonly" />
   				</td>
   				<td>现箱价:</td>
   				<td >
   					<input type="text" id="goods-adjustPrice-nowboxprice" name="nowboxprice" style="width:150px;" data-options="required:true,validType:'intOrFloat'"/>
   				</td>
   			</tr>
   			<tr>
   			    <td>涨幅:</td>
   				<td>
   					<input type="text" id="goods-adjustPrice-rate"   style="width:150px;" class="no_input" readonly="readonly" />
   					<input type="hidden" id="goods-adjustPrice-rate1"  name="rate"  style="width:150px;" class="no_input" readonly="readonly" />
   				</td>
   				<td>箱装量:</td>
   				<td>
   					<input type="text" id="goods-adjustPrice-boxnum"  name="boxnum"   style="width:150px;" class="no_input" readonly="readonly" />
   				</td>
   			</tr>
   			<tr>
   			    <td>商品品牌:</td>
   				<td>
   					<input type="text" id="goods-adjustPrice-goodsbrandName" name="goodsbrandName" class="no_input" readonly="readonly"  style="width:150px;"/>
   				</td>
   				<td>条形码:</td>
   				<td>
   					<input type="text" id="goods-adjustPrice-barcode"  name="barcode" class="no_input" readonly="readonly"  style="width:150px;"/>
   				</td>
   			</tr>
   			<tr>
   			   <td>备注:</td>
   			    <td>
   			       <input type="text" id="goods-adjustPriceDetail-remark" name="remark"   style="width:150px;"/>
   			    </td>
   			</tr>
   		</table>
    </form>
    </div>
	<div data-options="region:'south',border:false">
		<div class="buttonDetailBG" style="height:30px;text-align: right;">
			快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
			<input type="button" value="继续添加" name="savegoon" id="goods-savegoon-adjustPriceDetailAddPage" />
		</div>
	</div>
   <script type="text/javascript">
   		$(function() {
   			var type = '${type}';
   			var goodsidlist = '${goodsidlist}';
   			var ids = "";
   			if(type=="4"||type=="3"){
   				if(goodsidlist!='')
   				    var selectType={field:'id',op:'in',value:goodsidlist};
   				else
   					var selectType={field:'id',op:'in',value:' '};
   			}else{
   				var selectType={field:'id',op:'notin',value:''};
   			}
   			var type=$("#goods-adjustPrice-type-select").val();
   			$("#goods-adjustPrice-goodsid").goodsWidget({
   				col: 'id',
   				singleSelect: true,
   				width: 150,
   				param: [
                        selectType
   				],
   				onSelect: function(data) {
   					if (data) {
   						$("#goods-adjustPrice-goodsname").val(data.name);
   						$("#goods-adjustPrice-goodsbrandName").val(data.brandName);
   						$("#goods-adjustPrice-barcode").val(data.barcode);
   						$("#goods-adjustPrice-boxnum").val(data.boxnum);
   						$("#goods-loading-adjustPriceDetailAddPage").html("商品编码:<font color='green'>" + data.id + "</font>");
   						if(type=="1"){
   							$("#goods-adjustPrice-oldprice").val(formatterDefineMoney(data.highestbuyprice,6));
   							var oldboxprice =data.highestbuyprice*data.boxnum;
   							$("#goods-adjustPrice-oldboxprice").val(formatterDefineMoney(oldboxprice,6));
   						}else if(type=="2"){
   							$("#goods-adjustPrice-oldprice").val(formatterDefineMoney(data.basesaleprice,6));
   							var oldboxprice =data.basesaleprice*data.boxnum;
   							$("#goods-adjustPrice-oldboxprice").val(formatterDefineMoney(oldboxprice,6));
   						}else if(type=="3"){
   							var code=$("#goods-pricelist-adjustPriceAddPage").widget("getValue");
   							getPriceDataByGoodsidAndCode(data.id,code);
   							var oldprice=$("#goods-adjustPrice-oldprice").val();
   							var oldboxprice =oldprice*data.boxnum;
   							$("#goods-adjustPrice-oldboxprice").val(formatterDefineMoney(oldboxprice,6));
   						}else if(type=="4"){
   							var customerid=$("#goods-customer-adjustPriceAddPage").widget("getValue");
   							getPriceDataByCustomerid(data.id,customerid);
   							var oldprice=$("#goods-adjustPrice-oldprice").val();
   							var oldboxprice =oldprice*data.boxnum;
   							$("#goods-adjustPrice-oldboxprice").val(formatterDefineMoney(oldboxprice,6));
   						}
   						$("#goods-adjustPrice-nowprice").focus();
   			   			$("#goods-adjustPrice-nowprice").select();
   					}
   				}
   			});
			
			$("#goods-adjustPrice-nowprice").change(function(){
				if($(this).val()!=""){
					var nowprice=$(this).val();
					 $("#goods-adjustPrice-nowprice").val(formatterDefineMoney(nowprice,6));
				    var boxnum=$("#goods-adjustPrice-boxnum").val();
				    var nowboxprice=nowprice*boxnum;
				    $("#goods-adjustPrice-nowboxprice").val(formatterDefineMoney(nowboxprice,6));
				    getrate();
				}
				else{
					$("#goods-adjustPrice-nowprice").val("0");
					$("#goods-adjustPrice-rate").val("-100");
					$("#goods-adjustPrice-nowboxprice").val("0");
				}
			});
			
			$("#goods-adjustPrice-nowboxprice").change(function(){
				if($(this).val()!=""){
					var nowboxprice=$(this).val();
					$("#goods-adjustPrice-nowboxprice").val(formatterDefineMoney(nowboxprice,6));
					var boxnum=$("#goods-adjustPrice-boxnum").val();
				    var nowprice=nowboxprice/boxnum;
				    $("#goods-adjustPrice-nowprice").val(formatterDefineMoney(nowprice,6));
				    getrate();
				}
				else{
					$("#goods-adjustPrice-nowprice").val("0");
					$("#goods-adjustPrice-rate").val("-100");
					$("#goods-adjustPrice-nowboxprice").val("0");
				}
			});
		    
			$("#goods-adjustPrice-nowprice").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					$("#goods-adjustPrice-nowprice").blur();
		   			$("#goods-adjustPrice-nowboxprice").focus();
		   			$("#goods-adjustPrice-nowboxprice").select();
				}
			});
			$("#goods-adjustPrice-nowboxprice").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					$("#goods-adjustPrice-nowboxprice").blur();
		   			$("#goods-adjustPriceDetail-remark").focus();
		   			$("#goods-adjustPriceDetail-remark").select();
				}
			});
			$("#goods-adjustPriceDetail-remark").die("keydown").live("keydown",function(event){
				if(event.keyCode==13){
					$("#goods-adjustPriceDetail-remark").blur();
					$("#goods-savegoon-adjustPriceDetailAddPage").focus();
				}
			});
			$("#goods-savegoon-adjustPriceDetailAddPage").die("keydown").live("keydown",function(event){
				//enter
				if(event.keyCode==13){
					    addSaveDetail(true);
				}
			});
		    $("#goods-savegoon-adjustPriceDetailAddPage").click(function(){
				    addSaveDetail(true);
		    });
		});
		
		//获取价格套价格
		function getPriceDataByGoodsidAndCode(goodsid,code){
			$.ajax({   
	            url :'basefiles/getPriceDataByGoodsidAndCode.do',
	            type:'post',
	            data:{goodsid:goodsid,code:code},
	            dataType:'json',
	            async:false,
	            success:function(json){
	            	$("#goods-adjustPrice-oldprice").val(formatterDefineMoney(json.price,6));
	            }
	        });
		}
		//获取合同价
		function getPriceDataByCustomerid(goodsid,customerid){
			$.ajax({   
	            url :'basefiles/getPriceDataByCustomerid.do',
	            type:'post',
	            data:{goodsid:goodsid,customerid:customerid},
	            dataType:'json',
	            async:false,
	            success:function(json){
	            	
	            	$("#goods-adjustPrice-oldprice").val(formatterDefineMoney(json.price,6));
	            }
	        });
		}
		//计算涨幅
		function getrate(){
			var oldPrice=$("#goods-adjustPrice-oldprice").val();
			var nowPrice=$("#goods-adjustPrice-nowprice").val();
			if(oldPrice==0){
				$("#goods-adjustPrice-rate").val(formatterMoney(0));
			}
			else{
				 var rate= (nowPrice-oldPrice)/oldPrice*100;
				 $("#goods-adjustPrice-rate1").val(formatterMoney(rate));
			     $("#goods-adjustPrice-rate").val(formatterMoney(rate)+"﹪");
			}
		}
   </script>
  </body>
</html>
