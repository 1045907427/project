<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.hd.agent.common.util.BillGoodsNumDecimalLenUtils" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
  <head>
  	<title>商品明细编辑页面</title>
	<%@include file="/include.jsp" %>
  </head>
  
  <body id="oa-body-oagoodsDetailEditPage">
  	<div class="easyui-layout" data-options="fit:true">
  		<div data-options="region:'center',border:false">
	  		<form id="oa-form-oagoodsDetailEditPage">
				
				<input type="hidden" name="brandname" id="brandname" value=""/>
				<input type="hidden" name="goodssortname" id="goodssortname" value=""/>
				<input type="hidden" name="unitname" id="unitname" value=""/>
				<input type="hidden" name="auxunitname" id="auxunitname" value=""/>
				<input type="hidden" name="storagename" id="storagename" value=""/>
				<input type="hidden" name="taxname" id="taxname" value=""/>
				<!-- <input type="hidden" name="goodsid" id="goodsid" value=""/> -->
				
				<input type="hidden" id="goods-oldgoodsid" value=""/>
				<input type="hidden" id="goods-oldgoodsname" value=""/>
				<input type="hidden" id="goods-oldbarcode" value=""/>
				<input type="hidden" id="goods-oldboxbarcode" value=""/>

				<input type="hidden" name="id" id="id" value="${param.index }"/>

		  		<table style="border-collapse:collapse;" border="0" cellpadding="2" cellspacing="2">
					<tr><td>&nbsp;</td><td></td><td></td><td></td></tr>
		  			<tr>
		  				<td class="right len100">商品编号：</td>
		  				<td class="len200"><input class="easyui-validatebox len180" style="margin-left: 0px;" name="goodsid" id="oa-goodsid-oagoodsDetailEditPage" data-options="required:true" validType="validId[20]"/><font color="red">*</font></td>
		  				<td class="right len90">商品名称：</td>
		  				<td class="len185"><input class="easyui-validatebox len180" style="margin-left: 0px;" name="goodsname" id="oa-goodsname-oagoodsDetailEditPage" data-options="required:true" validType="validName[100]"/><font color="red">*</font></td>
		  			</tr>
		  			<tr>
		  				<td class="right">品牌名称：</td>
		  				<td><input class="easyui-validatebox len180" style="margin-left: 0px;" name="brandid" id="oa-brandid-oagoodsDetailEditPage" data-options="required:true"/><font color="red">*</font></td>
		  				<td class="right">条形码：</td>
		  				<td><input class="easyui-validatebox len180" style="margin-left: 0px;" name="barcode" id="oa-barcode-oagoodsDetailEditPage" data-options="required:true"/><font color="red">*</font></td>
		  			</tr>
		  			<tr>
		  				<td class="right">箱装条形码：</td>
		  				<td><input class="easyui-validatebox len180" style="margin-left: 0px;" name="boxbarcode" id="oa-boxbarcode-oagoodsDetailEditPage" /></td>
		  				<td class="right">商品分类：</td>
		  				<td><input class="easyui-validatebox len180" style="margin-left: 0px;" name="goodssort" id="oa-goodssort-oagoodsDetailEditPage" data-options="required:true"/></td>
		  			</tr>
					<tr>
						<td class="right">单位：</td>
						<td>
							<table>
								<tr>
									<td>主</td>
									<td>
										<input class="easyui-validatebox len180" style="margin-left: 0px;" name="unitid" id="oa-unitid-oagoodsDetailEditPage" data-options="required:true"/>
									</td>
									<td>辅</td>
									<td>
										<input class="easyui-validatebox len180" style="margin-left: 0px;" name="auxunitid" id="oa-auxunitid-oagoodsDetailEditPage" data-options="required:true"/>
										<font color="red">*</font>
									</td>
								</tr>
							</table>
						</td>
						<td class="right">规格型号：</td>
						<td><input class="easyui-validatebox len180" style="margin-left: 0px;" name="model" id="oa-model-oagoodsDetailEditPage" data-options="required:false" maxlength="66"/></td>
					</tr>
		  			<tr>
		  				<td class="right">箱装量：</td>
		  				<td><input class="easyui-numberbox len180" style="margin-left: 0px;" name="boxnum" id="oa-boxnum-oagoodsDetailEditPage" data-options="required:true, min:0, precision:<%=BillGoodsNumDecimalLenUtils.decimalLen %>"/><font color="red">*</font></td>
		  				<td class="right">箱重：</td>
		  				<td><input class="easyui-numberbox len180" style="margin-left: 0px;" name="totalweight" id="oa-totalweight-oagoodsDetailEditPage" data-options="required:true, min:0, precision:2"/><font color="red">*</font></td>
		  			</tr>
		  			<tr>
		  				<td class="right">长度(m)：</td>
		  				<td><input class="easyui-numberbox len180" style="margin-left: 0px;" name="glength" id="oa-glength-oagoodsDetailEditPage" data-options="min:0, precision:2"/></td>
		  				<td class="right">宽度(m)：</td>
		  				<td><input class="easyui-numberbox len180" style="margin-left: 0px;" name="gwidth" id="oa-gwidth-oagoodsDetailEditPage" data-options="min:0, precision:2"/></td>
		  			</tr>
		  			<tr>
		  				<td class="right">高度(m)：</td>
		  				<td><input class="easyui-numberbox len180" style="margin-left: 0px;" name="gheight" id="oa-gheight-oagoodsDetailEditPage" data-options="min:0, precision:2"/></td>
		  				<td class="right">箱体积(m<sup>3</sup>)：</td>
		  				<td><input class="easyui-numberbox len180" style="margin-left: 0px;" name="totalvolume" id="oa-totalvolume-oagoodsDetailEditPage" data-options="min:0, precision:6,required:true"/><font color="red">*</font></td>
		  			</tr>
		  			<tr>
		  				<td class="right">仓库：</td>
		  				<td><input class="easyui-validatebox len180" style="margin-left: 0px;" name="storageid" id="oa-storageid-oagoodsDetailEditPage" data-options="required:true"/><font color="red">*</font></td>
		  				<td class="right">最高采购价：</td>
		  				<td><input class="easyui-numberbox calProfit calProfit1 len180" style="margin-left: 0px;" name="buytaxprice" id="oa-buytaxprice-oagoodsDetailEditPage" data-options="required:true, min:0, precision:6"/><font color="red">*</font></td>
		  			</tr>
		  			<tr>
		  				<td class="right">基准销售价：</td>
		  				<td><input class="easyui-numberbox len180" style="margin-left: 0px;" name="basesaleprice" id="oa-basesaleprice-oagoodsDetailEditPage" data-options="required:true, min:0, precision:6"/><font color="red">*</font></td>
		  				<td class="right">税种：</td>
		  				<td><input class="easyui-validatebox len180" style="margin-left: 0px;" name="taxtype" id="oa-taxtype-oagoodsDetailEditPage" data-options="required:true"/><font color="red">*</font></td>
		  			</tr>
                    <tr>
                        <td class="right">产地：</td>
                        <td><input class="easyui-validatebox len180" style="margin-left: 0px;" name="productfield" id="oa-productfield-oagoodsDetailEditPage" maxlength="20"/></td>
						<td class="right">保质期：</td>
						<td>
							<input class="easyui-numberbox len130" style="margin-left: 0px;" name="shelflife" id="oa-shelflife-oagoodsDetailAddPage" data-options=""  maxlength="20"/>
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
						<td colspan="3"><input class="easyui-validatebox len180" style="margin-left: 0px; width: 478px;" name="remark" id="oa-remark-oagoodsDetailEditPage" maxlength="100"/></td>
						<td class="right nodisplay">核算成本价：</td>
						<td class="nodisplay"><input class="easyui-numberbox calProfit calProfit1 len180" style="margin-left: 0px;" name="costaccountprice" id="oa-costaccountprice-oagoodsDetailEditPage" data-options="min:0, precision:6"/></td>
					</tr>
                    <tr>
                        <c:forEach items="${priceList }" var="item" varStatus="idx">
                            <td class="right"><c:out value="${item.codename }"></c:out>：</td>
                            <td><input class="easyui-numberbox calProfit calProfit2 len180" style="margin-left: 0px;" name="price${idx.index + 1 }" id="oa-price${idx.index + 1 }-oagoodsDetailEditPage" data-options="required:true, min:0, precision:6, onChange:calProfit"/><font color="red">*</font></td>
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
	  			<input type="button" value="&nbsp;&nbsp;确定&nbsp;&nbsp;" name="savegoon" id="oa-savegoon-oagoodsDetailEditPage" />
  			</div>
  		</div>
  	</div>
	<script type="text/javascript">

	function setDVal($d) {

		if($d.children().length == 0) {
			if($d.is('input')) {
				var n = 'goodsDetailData.' + $d.attr('name');
				try {
					var v = eval(n);
					$d.val(v);
				} catch(e) {
					return false
				}
			}
			return false;
		}

		$d.children().each(function(){

			 setDVal($(this));
		});
	}

    $(function() {

        // input设置 goodsDetailData → form
        setDVal($('#oa-form-oagoodsDetailEditPage'));

        // 计算毛利
        calProfit();
    });

    $.parser.onComplete = function(context){

		// 品牌
		$("#oa-brandid-oagoodsDetailEditPage").widget({
			name: 't_oa_goods_detail',
			col: 'brandid',
			singleSelect:true,
			width: 180,
			required:true,
			onlyLeafCheck:true,
			onSelect: function(data) {
				$('#brandname').val(data.name);
				$("#oa-barcode-oagoodsDetailEditPage").focus();
			},
			onClear: function() {
				$('#brandname').val('');
			}
		});

		// 商品分类
		$("#oa-goodssort-oagoodsDetailEditPage").widget({
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
		$("#oa-unitid-oagoodsDetailEditPage").widget({
			name: 't_oa_goods_detail',
			col: 'unitid',
			singleSelect:true,
			width: 71,
			required:true,
			onlyLeafCheck:true,
			onSelect: function(data) {
				$('#unitname').val(data.name);
				
				$("#oa-auxunitid-oagoodsDetailEditPage").focus();
			},
			onClear: function() {
				$('#unitname').val('');
			}
		});

		// 辅单位
		$("#oa-auxunitid-oagoodsDetailEditPage").widget({
			name: 't_oa_goods_detail',
			col: 'auxunitid',
			singleSelect:true,
			width: 70,
			required:true,
			onlyLeafCheck:true,
			onSelect: function(data) {
				$('#auxunitname').val(data.name);
//				$("#oa-boxnum-oagoodsDetailEditPage").textbox('textbox').focus();
				$("#oa-model-oagoodsDetailEditPage")/*.textbox('textbox')*/.focus();
			},
			onClear: function() {
				$('#auxunitname').val('');
			}
		});

		// 仓库
		$("#oa-storageid-oagoodsDetailEditPage").widget({
			name: 't_oa_goods_detail',
			col: 'storageid',
			singleSelect:true,
			width: 180,
			required:true,
			onlyLeafCheck:true,
			onSelect: function(data) {
				$('#storagename').val(data.name);
				$("#oa-buytaxprice-oagoodsDetailEditPage").textbox('textbox').focus();
			},
			onClear: function() {
				$('#storagename').val('');
			}
		});

		// 税种
		$("#oa-taxtype-oagoodsDetailEditPage").widget({
			name: 't_oa_goods_detail',
			col: 'taxtype',
			singleSelect:true,
			width: 180,
			required:true,
			onlyLeafCheck:true,
			onSelect: function(data) {
				$('#taxname').val(data.name);
				// $('#oa-taxrate-oagoodsDetailEditPage').val(data.rate + '%');
				$("#oa-price1-oagoodsDetailEditPage").textbox('textbox').focus();
			},
			onClear: function() {
				$('#taxname').val('');
			}
		});

		// 商品编号
		$("#oa-goodsid-oagoodsDetailEditPage").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#oa-goodsid-oagoodsDetailEditPage").blur();
	   			$("#oa-goodsname-oagoodsDetailEditPage").focus();
			}
		});

		// 商品名称
		$("#oa-goodsname-oagoodsDetailEditPage").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#oa-goodsname-oagoodsDetailEditPage").blur();
	   			$("#oa-brandid-oagoodsDetailEditPage").focus();
			}
		});

		// 条形码
		$("#oa-barcode-oagoodsDetailEditPage").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#oa-barcode-oagoodsDetailEditPage").blur();
	   			$("#oa-boxbarcode-oagoodsDetailEditPage").focus();
			}
		});

		// 条形码
		$("#oa-boxbarcode-oagoodsDetailEditPage").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#oa-boxbarcode-oagoodsDetailEditPage").blur();
	   			$("#oa-goodssort-oagoodsDetailEditPage").focus();
			}
		});

		// 规格型号
		$("#oa-model-oagoodsDetailEditPage").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#oa-model-oagoodsDetailEditPage").blur();
				$("#oa-boxnum-oagoodsDetailEditPage").textbox('textbox').focus();
			}
		});

		// 箱装量
		$("#oa-boxnum-oagoodsDetailEditPage").textbox('textbox').keydown(function(event){
			//enter
			if(event.keyCode==13){
				$("#oa-boxnum-oagoodsDetailEditPage").textbox('textbox').blur();
	   			$("#oa-totalweight-oagoodsDetailEditPage").textbox('textbox').focus();
			}
		});

		// 箱重
		$("#oa-totalweight-oagoodsDetailEditPage").textbox('textbox').keydown(function(event){
			//enter
			if(event.keyCode==13){
				$("#oa-totalweight-oagoodsDetailEditPage").textbox('textbox').blur();
	   			$("#oa-glength-oagoodsDetailEditPage").textbox('textbox').focus();
			}
		});

		// 长
		$("#oa-glength-oagoodsDetailEditPage").textbox('textbox').keydown(function(event){
			//enter
			if(event.keyCode==13){
				$("#oa-glength-oagoodsDetailEditPage").textbox('textbox').blur();
	   			$("#oa-gwidth-oagoodsDetailEditPage").textbox('textbox').focus();
			}
		});

		// 宽
		$("#oa-gwidth-oagoodsDetailEditPage").textbox('textbox').keydown(function(event){
			//enter
			if(event.keyCode==13){
				$("#oa-gwidth-oagoodsDetailEditPage").textbox('textbox').blur();
	   			$("#oa-gheight-oagoodsDetailEditPage").textbox('textbox').focus();
			}
		});

		// 高
		$("#oa-gheight-oagoodsDetailEditPage").textbox('textbox').keydown(function(event){
			//enter
			if(event.keyCode==13){
				$("#oa-gheight-oagoodsDetailEditPage").textbox('textbox').blur();
	   			$("#oa-totalvolume-oagoodsDetailEditPage").textbox('textbox').focus();
			}
		});

		// 箱体积
		$("#oa-totalvolume-oagoodsDetailEditPage").textbox('textbox').keydown(function(event){
			//enter
			if(event.keyCode==13){
				$("#oa-totalvolume-oagoodsDetailEditPage").textbox('textbox').blur();
	   			$("#oa-storageid-oagoodsDetailEditPage").focus();
			}
		});

		// 最高采购价
		$("#oa-buytaxprice-oagoodsDetailEditPage").textbox('textbox').keydown(function(event){
			//enter
			if(event.keyCode==13){
				$("#oa-buytaxprice-oagoodsDetailEditPage").textbox('textbox').blur();
	   			$("#oa-basesaleprice-oagoodsDetailEditPage").textbox('textbox').focus();
			}
		});

		// 基准销售价
		$("#oa-basesaleprice-oagoodsDetailEditPage").textbox('textbox').keydown(function(event){
			//enter
			if(event.keyCode==13){
				$("#oa-basesaleprice-oagoodsDetailEditPage").textbox('textbox').blur();
	   			$("#oa-taxtype-oagoodsDetailEditPage").focus();
			}
		});

        <c:forEach items="${priceList }" var="item" varStatus="idx">
            // <c:out value="${item.codename }"/>
            // n号价
            $("#oa-price${idx.index + 1}-oagoodsDetailEditPage").textbox('textbox').keydown(function(event){
                //enter
                if(event.keyCode==13){
                    $("#oa-price${idx.index + 1}-oagoodsDetailEditPage").textbox('textbox').blur();
                    <c:choose>
                        <c:when test="${idx.last }">
                            $("#oa-remark-oagoodsDetailEditPage").focus();
                        </c:when>
                        <c:otherwise>
                            $("#oa-price${idx.index + 2}-oagoodsDetailEditPage").textbox('textbox').focus();
                        </c:otherwise>
                    </c:choose>
                }
            });
        </c:forEach>

		// 备注
		$("#oa-remark-oagoodsDetailEditPage").die("keydown").live("keydown",function(event){
			//enter
			if(event.keyCode==13){
				$("#oa-remark-oagoodsDetailEditPage").blur();
	   			//$("#oa-price1-oagoodsDetailEditPage").focus();
	   			editDetail(false);
			}
		});

   		$("#oa-savegoon-oagoodsDetailEditPage").click(function(){

 			editDetail(false);
  		});
		
		$('#oa-glength-oagoodsDetailEditPage').textbox('textbox').blur(calVolume);
		$('#oa-gwidth-oagoodsDetailEditPage').textbox('textbox').blur(calVolume);
		$('#oa-gheight-oagoodsDetailEditPage').textbox('textbox').blur(calVolume);
				
		// $('.calProfit').blur(calProfit);
		$('#oa-costaccountprice-oagoodsDetailEditPage').focus();
		$('#oa-costaccountprice-oagoodsDetailEditPage').blur();
	};

    /**
    * 计算体积
     */
	function calVolume() {
			
		setTimeout(function(){

			var glength = $('#oa-glength-oagoodsDetailEditPage').val();
			var gwidth  = $('#oa-gwidth-oagoodsDetailEditPage').val();
			var gheight = $('#oa-gheight-oagoodsDetailEditPage').val();
	
			var v = parseFloat(glength) * parseFloat(gwidth) * parseFloat(gheight);
	
			if(!isNaN(v)) {
				//$('#oa-totalvolume-oagoodsDetailEditPage').val(formatterMoney(v, 6));
				$("#oa-totalvolume-oagoodsDetailEditPage").numberbox('setValue', v);
			} else {
				//$('#oa-totalvolume-oagoodsDetailEditPage').val('');
				$("#oa-totalvolume-oagoodsDetailEditPage").numberbox('setValue', '');
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
				var cost1 = $('#oa-costaccountprice-oagoodsDetailEditPage').numberbox('getValue') || '0.00';
				var cost2 = $('#oa-buytaxprice-oagoodsDetailEditPage').numberbox('getValue') || '0.00';
				
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

	</script>
  </body>
</html>