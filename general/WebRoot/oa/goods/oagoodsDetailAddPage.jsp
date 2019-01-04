<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.hd.agent.common.util.BillGoodsNumDecimalLenUtils" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  	<title>商品明细添加页面</title>
	<%@include file="/include.jsp" %>
  </head>
  
  <body id="oa-body-oagoodsDetailAddPage">
  	<div class="easyui-layout" data-options="fit:true">
  		<div data-options="region:'center',border:false">
	  		<form id="oa-form-oagoodsDetailAddPage">
				
				<input type="hidden" name="brandname" id="brandname" value=""/>
				<input type="hidden" name="goodssortname" id="goodssortname" value=""/>
				<input type="hidden" name="unitname" id="unitname" value=""/>
				<input type="hidden" name="auxunitname" id="auxunitname" value=""/>
				<input type="hidden" name="storagename" id="storagename" value=""/>
				<input type="hidden" name="taxname" id="taxname" value=""/>
				
				<input type="hidden" id="goods-oldgoodsid" value=""/>
				<input type="hidden" id="goods-oldgoodsname" value=""/>
				<input type="hidden" id="goods-oldbarcode" value=""/>
				<input type="hidden" id="goods-oldboxbarcode" value=""/>

				<input type="hidden" name="id" id="id" value="${param.index }"/>

		  		<table style="border-collapse:collapse;" border="0" cellpadding="2" cellspacing="2">
					<tr><td>&nbsp;</td><td></td><td></td><td></td></tr>
		  			<tr>
		  				<td class="right len100">商品编号：</td>
		  				<td class="len200"><input class="easyui-validatebox len180" style="margin-left: 0px;" name="goodsid" id="oa-goodsid-oagoodsDetailAddPage" validType="validId[20]" data-options="required:true"/><font color="red">*</font></td>
		  				<td class="right len90">商品名称：</td>
		  				<td class="len185"><input class="easyui-validatebox len180" style="margin-left: 0px;" name="goodsname" id="oa-goodsname-oagoodsDetailAddPage" validType="validName[100]" data-options="required:true"/><font color="red">*</font></td>
		  			</tr>
		  			<tr>
		  				<td class="right">品牌名称：</td>
		  				<td><input class="easyui-validatebox len180" style="margin-left: 0px;" name="brandid" id="oa-brandid-oagoodsDetailAddPage" data-options="required:true"/><font color="red">*</font></td>
		  				<td class="right">条形码：</td>
		  				<td><input class="easyui-validatebox len180" style="margin-left: 0px;" name="barcode" id="oa-barcode-oagoodsDetailAddPage" data-options="required:true"/><font color="red">*</font></td>
		  			</tr>
		  			<tr>
		  				<td class="right">箱装条形码：</td>
		  				<td><input class="easyui-validatebox len180" style="margin-left: 0px;" name="boxbarcode" id="oa-boxbarcode-oagoodsDetailAddPage" /></td>
		  				<td class="right">商品分类：</td>
		  				<td><input class="easyui-validatebox len180" style="margin-left: 0px;" name="goodssort" id="oa-goodssort-oagoodsDetailAddPage" data-options="required:true"/></td>
		  			</tr>
		  			<tr>
		  				<td class="right">单位：</td>
		  				<td>
							<table>
								<tr>
									<td>主</td>
									<td>
										<input class="easyui-validatebox len180" style="margin-left: 0px;" name="unitid" id="oa-unitid-oagoodsDetailAddPage" data-options="required:true"/>
									</td>
									<td>辅</td>
									<td>
										<input class="easyui-validatebox len180" style="margin-left: 0px;" name="auxunitid" id="oa-auxunitid-oagoodsDetailAddPage" data-options="required:true"/>
										<font color="red">*</font>
									</td>
								</tr>
							</table>
						</td>
		  				<td class="right">规格型号：</td>
						<td><input class="easyui-validatebox len180" style="margin-left: 0px;" name="model" id="oa-model-oagoodsDetailAddPage" data-options="required:false" maxlength="66"/></td>
		  			</tr>
		  			<tr>
		  				<td class="right">箱装量：</td>
		  				<td><input class="easyui-numberbox len180" style="margin-left: 0px;" name="boxnum" id="oa-boxnum-oagoodsDetailAddPage" data-options="required:true, min:0, precision:<%=BillGoodsNumDecimalLenUtils.decimalLen %>"/><font color="red">*</font></td>
		  				<td class="right">箱重：</td>
		  				<td><input class="easyui-numberbox len180" style="margin-left: 0px;" name="totalweight" id="oa-totalweight-oagoodsDetailAddPage" data-options="required:true, min:0, precision:2"/><font color="red">*</font></td>
		  			</tr>
		  			<tr>
		  				<td class="right">长度(m)：</td>
		  				<td><input class="easyui-numberbox len180" style="margin-left: 0px;" name="glength" id="oa-glength-oagoodsDetailAddPage" data-options="min:0, precision:2"/></td>
		  				<td class="right">宽度(m)：</td>
		  				<td><input class="easyui-numberbox len180" style="margin-left: 0px;" name="gwidth" id="oa-gwidth-oagoodsDetailAddPage" data-options="min:0, precision:2"/></td>
		  			</tr>
		  			<tr>
		  				<td class="right">高度(m)：</td>
		  				<td><input class="easyui-numberbox len180" style="margin-left: 0px;" name="gheight" id="oa-gheight-oagoodsDetailAddPage" data-options="min:0, precision:2"/></td>
		  				<td class="right">箱体积(m<sup>3</sup>)：</td>
		  				<td><input class="easyui-numberbox len180" style="margin-left: 0px;" name="totalvolume" id="oa-totalvolume-oagoodsDetailAddPage" data-options="min:0, precision:6,required:true"/><font color="red">*</font></td>
		  			</tr>
		  			<tr>
		  				<td class="right">仓库：</td>
		  				<td><input class="easyui-validatebox len180" style="margin-left: 0px;" name="storageid" id="oa-storageid-oagoodsDetailAddPage" data-options="required:true"/><font color="red">*</font></td>
		  				<td class="right">最高采购价：</td>
		  				<td><input class="easyui-numberbox calProfit calProfit1 len180" style="margin-left: 0px;" name="buytaxprice" id="oa-buytaxprice-oagoodsDetailAddPage" data-options="required:true, min:0, precision:6"/><font color="red">*</font></td>
		  			</tr>
		  			<tr>
		  				<td class="right">基准销售价：</td>
		  				<td><input class="easyui-numberbox len180" style="margin-left: 0px;" name="basesaleprice" id="oa-basesaleprice-oagoodsDetailAddPage" data-options="required:true, min:0, precision:6"/><font color="red">*</font></td>
		  				<td class="right">税种：</td>
		  				<td><input class="easyui-validatebo len180" style="margin-left: 0px;" name="taxtype" id="oa-taxtype-oagoodsDetailAddPage" value="${defaulttaxtype }" data-options="required:true"/><font color="red">*</font></td>
		  			</tr>
                    <tr>
                        <td class="right">产地：</td>
                        <td><input class="easyui-validatebox len180" style="margin-left: 0px;" name="productfield" id="oa-productfield-oagoodsDetailAddPage" data-options="" maxlength="20"/></td>
						<td class="right">保质期：</td>
						<td>
							<input class="easyui-numberbox len130" style="margin-left: 0px;" name="shelflife" id="oa-shelflife-oagoodsDetailAddPage" maxlength="20"/>
							<select name="shelflifeunit" style="width: 44px;">
								<option></option>
								<option value="1">天</option>
								<option value="2">周</option>
								<option value="3">月</option>
								<option value="4">年</option>
							</select>
						</td>
					</tr>
					<tr>
						<td class="right">备注：</td>
						<td colspan="3"><input class="easyui-validatebox len180" style="margin-left: 0px; width: 478px;" name="remark" id="oa-remark-oagoodsDetailAddPage" maxlength="100"/></td>
						<td class="right nodisplay">核算成本价：</td>
						<td class="nodisplay"><input class="easyui-numberbox calProfit calProfit1 len180" style="margin-left: 0px;" name="costaccountprice" id="oa-costaccountprice-oagoodsDetailAddPage" data-options="min:0, precision:6"/></td>
					</tr>
                    <tr>
                        <c:forEach items="${priceList }" var="item" varStatus="idx">
                            <td class="right"><c:out value="${item.codename }"></c:out>：</td>
                            <td><input class="easyui-numberbox calProfit calProfit2 len180" style="margin-left: 0px;" name="price${idx.index + 1 }" id="oa-price${idx.index + 1 }-oagoodsDetailAddPage" data-options="required:true, min:0, precision:6, onChange:calProfit"/><font color="red">*</font></td>
                            <c:if test="${idx.index % 2 == 1 }">
                                </tr>
                                <tr>
                            </c:if>
                        </c:forEach>
                    </tr>
		  		</table>
	  		</form>
	  	</div>
  		<div data-options="region:'south',border:false">
  			<div class="buttonDetailBG" style="text-align:right;">
  				快捷键<span style="font-weight: bold;color: red;">Ctrl+Enter</span>或者<span style="font-weight: bold;color: red;">+</span>
	  			<input type="button" value="继续添加" name="savegoon" id="oa-savegoon-oagoodsDetailAddPage" />
  			</div>
  		</div>
  	</div>
	<script type="text/javascript">

	var defaulttaxname = '';
	var defaultAuxunitname = '';

    $.parser.onComplete = function(context){

		// 品牌
		$("#oa-brandid-oagoodsDetailAddPage").widget({
			name: 't_oa_goods_detail',
			col: 'brandid',
			singleSelect:true,
			width: 180,
			required:true,
			onlyLeafCheck:true,
			onSelect: function(data) {
				$('#brandname').val(data.name);
				$("#oa-barcode-oagoodsDetailAddPage").focus();
			},
			onClear: function() {
				$('#brandname').val('');
			}
		});

		// 商品分类
		$("#oa-goodssort-oagoodsDetailAddPage").widget({
			name: 't_oa_goods_detail',
			col: 'goodssort',
			singleSelect:true,
			width: 180,
			required:true,
			onlyLeafCheck:true,
			onChecked: function(data) {
				$('#goodssortname').val(data.name);
			},
			onClear: function() {
				$('#goodssortname').val('');
			}
		});

		// 主单位
		$("#oa-unitid-oagoodsDetailAddPage").widget({
			name: 't_oa_goods_detail',
			col: 'unitid',
			singleSelect:true,
			width: 71,
			required:true,
			onlyLeafCheck:true,
			onSelect: function(data) {
				$('#unitname').val(data.name);
				
				$("#oa-auxunitid-oagoodsDetailAddPage").focus();
			},
			onClear: function() {
				$('#unitname').val('');
			}
		});

		// 辅单位
		$("#oa-auxunitid-oagoodsDetailAddPage").widget({
			name: 't_oa_goods_detail',
			col: 'auxunitid',
			singleSelect:true,
			width: 70,
			required:true,
			onlyLeafCheck:true,
			initValue: '${defaultAuxunitid }',
			setValueSelect:true,
			onSelect: function(data) {
				$('#auxunitname').val(data.name);
//				$("#oa-boxnum-oagoodsDetailAddPage").textbox('textbox').focus();
				$("#oa-model-oagoodsDetailAddPage")/*.textbox('textbox')*/.focus();
			},
			onClear: function() {
				$('#auxunitname').val('');
			},
			onLoadSuccess: function() {
				$("#oa-auxunitid-oagoodsDetailAddPage").widget('setValue', '${defaultAuxunitid }');
				var val = $("#oa-auxunitid-oagoodsDetailAddPage").widget('getText');
				$('#auxunitname').val(val);
				defaultAuxunitname = val;
			}
		});

		// 仓库
		$("#oa-storageid-oagoodsDetailAddPage").widget({
			name: 't_oa_goods_detail',
			col: 'storageid',
			singleSelect:true,
			width: 180,
			required:true,
			onlyLeafCheck:true,
			onSelect: function(data) {
				$('#storagename').val(data.name);
				$("#oa-buytaxprice-oagoodsDetailAddPage").textbox('textbox').focus();
			},
			onClear: function() {
				$('#storagename').val('');
			}
		});

		// 税种
		$("#oa-taxtype-oagoodsDetailAddPage").widget({
			name: 't_oa_goods_detail',
			col: 'taxtype',
			singleSelect:true,
			width: 180,
			required:true,
			onlyLeafCheck:true,
			setValueSelect:true,
			onSelect: function(data) {
				$('#taxname').val(data.name);
				//$('#oa-taxrate-oagoodsDetailAddPage').val(data.rate + '%');
				$("#oa-price1-oagoodsDetailAddPage").textbox('textbox').focus();
			},
			onClear: function() {
				$('#taxname').val('');
			},
			onLoadSuccess: function() {
				$("#oa-taxtype-oagoodsDetailAddPage").widget('setValue', '${defaulttaxtype }');
				var val = $("#oa-taxtype-oagoodsDetailAddPage").widget('getText');
				$('#taxname').val(val);
				defaulttaxname = val;
			}
		});

		// 商品编号
		$("#oa-goodsid-oagoodsDetailAddPage").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#oa-goodsid-oagoodsDetailAddPage").blur();
	   			$("#oa-goodsname-oagoodsDetailAddPage").focus();
			}
		});

		// 商品名称
		$("#oa-goodsname-oagoodsDetailAddPage").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#oa-goodsname-oagoodsDetailAddPage").blur();
	   			$("#oa-brandid-oagoodsDetailAddPage").focus();
			}
		});

		// 条形码
		$("#oa-barcode-oagoodsDetailAddPage").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#oa-barcode-oagoodsDetailAddPage").blur();
	   			$("#oa-boxbarcode-oagoodsDetailAddPage").focus();
			}
		});

		// 条形码
		$("#oa-boxbarcode-oagoodsDetailAddPage").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#oa-boxbarcode-oagoodsDetailAddPage").blur();
	   			$("#oa-goodssort-oagoodsDetailAddPage").focus();
			}
		});

		// 规格型号
		$("#oa-model-oagoodsDetailAddPage").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#oa-model-oagoodsDetailAddPage").blur();
				$("#oa-boxnum-oagoodsDetailAddPage").textbox('textbox').focus();
			}
		});

		// 箱装量
		$("#oa-boxnum-oagoodsDetailAddPage").textbox('textbox').keydown(function(event){
			//enter
			if(event.keyCode==13){
				$("#oa-boxnum-oagoodsDetailAddPage").textbox('textbox').blur();
	   			$("#oa-totalweight-oagoodsDetailAddPage").textbox('textbox').focus();
			}
		});

		// 箱重
		$("#oa-totalweight-oagoodsDetailAddPage").textbox('textbox').keydown(function(event){
			//enter
			if(event.keyCode==13){
				$("#oa-totalweight-oagoodsDetailAddPage").textbox('textbox').blur();
	   			$("#oa-glength-oagoodsDetailAddPage").textbox('textbox').focus();
			}
		});

		// 长
		$("#oa-glength-oagoodsDetailAddPage").textbox('textbox').keydown(function(event){
			//enter
			if(event.keyCode==13){
				$("#oa-glength-oagoodsDetailAddPage").textbox('textbox').blur();
	   			$("#oa-gwidth-oagoodsDetailAddPage").textbox('textbox').focus();
			}
		});

		// 宽
		$("#oa-gwidth-oagoodsDetailAddPage").textbox('textbox').keydown(function(event){
			//enter
			if(event.keyCode==13){
				$("#oa-gwidth-oagoodsDetailAddPage").textbox('textbox').blur();
	   			$("#oa-gheight-oagoodsDetailAddPage").textbox('textbox').focus();
			}
		});

		// 高
		$("#oa-gheight-oagoodsDetailAddPage").textbox('textbox').keydown(function(event){
			//enter
			if(event.keyCode==13){
				$("#oa-gheight-oagoodsDetailAddPage").textbox('textbox').blur();
	   			$("#oa-totalvolume-oagoodsDetailAddPage").textbox('textbox').focus();
			}
		});

		// 箱体积
		$("#oa-totalvolume-oagoodsDetailAddPage").textbox('textbox').keydown(function(event){
			//enter
			if(event.keyCode==13){
				$("#oa-totalvolume-oagoodsDetailAddPage").textbox('textbox').blur();
	   			$("#oa-storageid-oagoodsDetailAddPage").focus();
			}
		});

		// 最高采购价
		$("#oa-buytaxprice-oagoodsDetailAddPage").textbox('textbox').keydown(function(event){
			//enter
			if(event.keyCode==13){
				$("#oa-buytaxprice-oagoodsDetailAddPage").textbox('textbox').blur();
	   			$("#oa-basesaleprice-oagoodsDetailAddPage").textbox('textbox').focus();
			}
		});

		// 基准销售价
		$("#oa-basesaleprice-oagoodsDetailAddPage").textbox('textbox').keydown(function(event){
			//enter
			if(event.keyCode==13){
				$("#oa-basesaleprice-oagoodsDetailAddPage").textbox('textbox').blur();
	   			$("#oa-taxtype-oagoodsDetailAddPage").focus();
			}
		});
        <c:forEach items="${priceList }" var="item" varStatus="idx">
            // <c:out value="${item.codename }"/>
            // n号价
            $("#oa-price${idx.index + 1}-oagoodsDetailAddPage").textbox('textbox').keydown(function(event){
                //enter
                if(event.keyCode==13){
                    $("#oa-price${idx.index + 1}-oagoodsDetailAddPage").textbox('textbox').blur();
                    <c:choose>
                        <c:when test="${idx.last }">
                            $("#oa-remark-oagoodsDetailAddPage").focus();
                        </c:when>
                        <c:otherwise>
                            $("#oa-price${idx.index + 2}-oagoodsDetailAddPage").textbox('textbox').focus();
                        </c:otherwise>
                    </c:choose>
                }
            });
        </c:forEach>

		// 备注
		$("#oa-remark-oagoodsDetailAddPage").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#oa-remark-oagoodsDetailAddPage").blur();
	   			//$("#oa-price1-oagoodsDetailAddPage").focus();
	   			addDetail(true);
			}
		});

   		$("#oa-savegoon-oagoodsDetailAddPage").click(function(){
 			addDetail(true);
  		});
		
		$('#oa-glength-oagoodsDetailAddPage').textbox('textbox').blur(calVolume);
		$('#oa-gwidth-oagoodsDetailAddPage').textbox('textbox').blur(calVolume);
		$('#oa-gheight-oagoodsDetailAddPage').textbox('textbox').blur(calVolume);

	};

    /**
    * 计算体积
     */
	function calVolume() {
			
		setTimeout(function(){

			var glength = $('#oa-glength-oagoodsDetailAddPage').val();
			var gwidth  = $('#oa-gwidth-oagoodsDetailAddPage').val();
			var gheight = $('#oa-gheight-oagoodsDetailAddPage').val();
	
			var v = (parseFloat(glength) * 100) * (parseFloat(gwidth) * 100) * (parseFloat(gheight) * 100) / 1000000;
	
			if(!isNaN(v)) {
				//$('#oa-totalvolume-oagoodsDetailAddPage').val(formatterMoney(v, 6));
				$("#oa-totalvolume-oagoodsDetailAddPage").numberbox('setValue', v);
				
			} else {
				//$('#oa-totalvolume-oagoodsDetailAddPage').val('');
				$("#oa-totalvolume-oagoodsDetailAddPage").numberbox('setValue', '');
			}
		}, 100);
	}

    /**
    * 计算利润
     */
	function calProfit() {
	
		setTimeout(function() {

			$('.tempSpan').remove();
			$('.calProfit2').each(function() {
			
			
				var price = $(this).numberbox('getValue') || '0.00';
				var cost1 = $('#oa-costaccountprice-oagoodsDetailAddPage').numberbox('getValue') || '0.00';
				var cost2 = $('#oa-buytaxprice-oagoodsDetailAddPage').numberbox('getValue');
				
				var cost = cost2;
//				if(cost1 == '' || cost1 == '0.00') {
//					cost = cost2;
//				}
				
				if(price != '0.00' && cost != '') {
				
					var profit = formatterMoney(price - cost, 2);
					var rate = formatterMoney(profit * 100 / price, 2);
					$(this).next().next().after('<span class = "tempSpan"><br/><font color="green">毛利率：' + rate + '%</font></span>');

				} else {

					$(this).next().next().next().remove();
				}
			});
		}, 100)
	}

	var defaulttaxtype = '${defaulttaxtype }';
	var defaultAuxunitid = '${defaultAuxunitid }';

	</script>
  </body>
</html>